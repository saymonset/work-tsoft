/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.persistence.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.hibernate.type.TimestampType;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.util.DateUtils;
import com.indeval.portalinternacional.middleware.servicios.constantes.BeneficiariosConstantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.HistoricoBeneficiario;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaHistoricoBeneficiariosParam;
import com.indeval.portalinternacional.persistence.dao.HistoricoBeneficiarioDao;

@SuppressWarnings("unchecked")
public class HistoricoBeneficiarioDaoImpl extends BaseDaoHibernateImpl implements
        HistoricoBeneficiarioDao {

    /** Log de clase. */
	private static final Logger log = LoggerFactory.getLogger(HistoricoBeneficiario.class);

    /**
     * @see com.indeval.portalinternacional.persistence.dao.HistoricoBeneficiarioDao#findHistoricoBeneficiario(com.indeval.portalinternacional.middleware.servicios.vo.ConsultaHistoricoBeneficiariosParam)
     */
    public PaginaVO findHistoricoBeneficiario(final ConsultaHistoricoBeneficiariosParam param,
            final PaginaVO paginaVO) {
        log.debug("Entrando a HistoricoBenefVencidoDaoImpl.findHistoricoBenefVencido())");

        final StringBuilder sbCount = new StringBuilder();
        final List parametrosCount = new ArrayList();
        final List<Type> tiposCount = new ArrayList<Type>();
        sbCount.append("	SELECT DISTINCT hb.idHistoricoBeneficiario ");
        sbCount.append("		FROM " + HistoricoBeneficiario.class.getName() + " hb ");
        sbCount.append("			JOIN hb.statusAnterior sant ");
        sbCount.append("			JOIN hb.statusNuevo snue ");
        sbCount.append("			JOIN hb.beneficiario b ");
        sbCount.append("			JOIN b.tipoBeneficiario tb ");
        sbCount.append("			LEFT JOIN b.institucion i ");
        sbCount.append("			LEFT JOIN i.tipoInstitucion ti ");
        sbCount.append(" WHERE hb.idHistoricoBeneficiario is not null ");
        this.construyeFiltros(param, sbCount, parametrosCount, tiposCount);

        List listCount = (List) this.getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException,
                    SQLException {
                Query query = session.createQuery(sbCount.toString());
                query.setParameters(parametrosCount.toArray(new Object[0]),
                        tiposCount.toArray(new Type[0]));
                if (param.getListaBeneficiarios() != null
                        && !param.getListaBeneficiarios().isEmpty()) {
                    query.setParameterList("lista_benef", param.getListaBeneficiarios(),
                            new LongType());
                }
                return query.list();
            }
        });

        Long cantidad = 0l;
        if (listCount != null && !listCount.isEmpty()) {
            cantidad = Integer.valueOf(listCount.size()).longValue();
        }

        if (cantidad != null) {
            paginaVO.setTotalRegistros(cantidad.intValue());
        } else {
            paginaVO.setTotalRegistros(0);
        }

        log.info("Cantidad: [" + paginaVO.getTotalRegistros().intValue() + "]");

        if (paginaVO.getTotalRegistros() != null && paginaVO.getTotalRegistros().intValue() > 0) {
            final StringBuilder sb = new StringBuilder();
            final List parametros = new ArrayList();
            final List<Type> tipos = new ArrayList<Type>();
            final Integer offset = paginaVO.getOffset();
            final Integer registrosPorPagina = paginaVO.getRegistrosXPag();
            sb.append(" SELECT DISTINCT hb ");
            sb.append(" FROM " + HistoricoBeneficiario.class.getName() + " hb ");
            sb.append("		JOIN FETCH hb.statusAnterior sant ");
            sb.append("		JOIN FETCH hb.statusNuevo snue ");
            sb.append("		JOIN FETCH hb.beneficiario b ");
            sb.append("		JOIN FETCH b.tipoBeneficiario tb ");
            sb.append("		LEFT JOIN FETCH b.institucion i ");
            sb.append("		LEFT JOIN FETCH i.tipoInstitucion ti ");
            sb.append(" WHERE hb.idHistoricoBeneficiario is not null ");
            this.construyeFiltros(param, sb, parametros, tipos);
            sb.append(" ORDER BY hb.fechaAlta ASC");

            List listado = (List) this.getHibernateTemplate().execute(new HibernateCallback() {

                public Object doInHibernate(final Session session) throws HibernateException,
                        SQLException {
                    Query query = session.createQuery(sb.toString());
                    query.setParameters(parametros.toArray(new Object[0]),
                            tipos.toArray(new Type[0]));
                    if (registrosPorPagina != null
                            && registrosPorPagina.intValue() != PaginaVO.TODOS.intValue()) {
                        query.setFirstResult(offset.intValue());
                        query.setMaxResults(registrosPorPagina);
                    }
                    if (param.getListaBeneficiarios() != null
                            && !param.getListaBeneficiarios().isEmpty()) {
                        query.setParameterList("lista_benef", param.getListaBeneficiarios(),
                                new LongType());
                    }
                    return query.list();
                }
            });

            paginaVO.setRegistros(listado);
        } else {
            paginaVO.setRegistros(new ArrayList());
        }

        return paginaVO;
    }
    
	public List<HistoricoBeneficiario> findHistoricoBeneficiario(final Long beneficiarioID, final String tipoFormato) {
		final String consultaHistory = getQueryHistoryForBeneficiario(beneficiarioID, tipoFormato);
		
		log.info("beneficiarioID :: :: " + beneficiarioID);
		
		List<HistoricoBeneficiario> listHistory = (List<HistoricoBeneficiario>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(consultaHistory);
				query = setterParamsQueryHistoryForBeneficiario(beneficiarioID, tipoFormato, query);
				return query.list();
			}
		});
		
		return listHistory;
        
	}

    private void construyeFiltros(final ConsultaHistoricoBeneficiariosParam param,
            final StringBuilder sb, final List parametros, final List<Type> tipos) {
        if (param.getIdBeneficiario() != null && param.getIdBeneficiario().longValue() > 0) {
            sb.append(" AND b.idBeneficiario = ? ");
            parametros.add(param.getIdBeneficiario());
            tipos.add(new LongType());
        }

        if (param.getCustodio() != null && param.getCustodio().longValue() > 0) {
            sb.append(" AND b.idCuentaNombrada = ? ");
            parametros.add(param.getCustodio());
            tipos.add(new LongType());
        }

        if (param.getTipoBeneficiario() != null && param.getTipoBeneficiario().longValue() > 0) {
            sb.append(" AND tb.idTipoBeneficiario = ? ");
            parametros.add(param.getTipoBeneficiario());
            tipos.add(new LongType());
        }

        if (StringUtils.isNotBlank(param.getFormato()) && !param.getFormato().equals("-1")) {
            if (param.getFormato().equalsIgnoreCase("W8BEN")) {
                sb.append("	AND b.tipoFormato in (?,?,?) ");
                parametros.add(param.getFormato().trim().toUpperCase());
                tipos.add(new StringType());
                parametros.add("W8BEN2014");
                tipos.add(new StringType());
                parametros.add("W8BEN2017");
                tipos.add(new StringType());
            } else if (BeneficiariosConstantes.FORMATO_W8_IMY.equals(param.getFormato())) {
                sb.append("	AND b.tipoFormato in (?,?,?) ");
                parametros.add(param.getFormato().trim().toUpperCase());
                tipos.add(new StringType());
                parametros.add(BeneficiariosConstantes.FORMATO_W8_IMY2015);
                tipos.add(new StringType());
                parametros.add(BeneficiariosConstantes.FORMATO_W8_IMY2017);
                tipos.add(new StringType());
            } else if (BeneficiariosConstantes.FORMATO_W8_BEN_E.equals(param.getFormato())) {
                sb.append(" AND b.tipoFormato in (?,?) ");
                parametros.add(param.getFormato().trim().toUpperCase());
                tipos.add(new StringType());
                parametros.add(BeneficiariosConstantes.FORMATO_W8_BEN_E_2016);
                tipos.add(new StringType());
            } else {
                sb.append("	AND b.tipoFormato = ? ");
                parametros.add(param.getFormato().trim().toUpperCase());
                tipos.add(new StringType());

            }
        }

        if (param.getInstitucion() != null && param.getInstitucion().getIdInstitucion() != null
                && param.getInstitucion().getIdInstitucion().longValue() > 0) {
            sb.append(" AND i.idInstitucion = ? ");
            parametros.add(param.getInstitucion().getIdInstitucion());
            tipos.add(new LongType());
        }

        if (param.getStatusAnterior() != null && param.getStatusAnterior().longValue() > 0) {
            sb.append(" AND sant.idStatusBenef = ? ");
            parametros.add(param.getStatusAnterior());
            tipos.add(new LongType());
        }

        if (param.getStatusNuevo() != null && param.getStatusNuevo().longValue() > 0) {
            sb.append(" AND snue.idStatusBenef = ? ");
            parametros.add(param.getStatusNuevo());
            tipos.add(new LongType());
        }
        /*
         * if (StringUtils.isNotBlank(param.getNombreRazonSocial())) { sb.append(
         * "	AND (b.nombres || b.apellidoPaterno || b.apellidoMaterno LIKE ?  " );
         * parametros.add(param.getNombreRazonSocial().trim().toUpperCase()+"%"); tipos.add(new
         * StringType()); sb.append( "	OR b.razonSocial LIKE ?)  " );
         * parametros.add(param.getNombreRazonSocial().trim().toUpperCase()+"%"); tipos.add(new
         * StringType()); }
         */
        if (param.getFechaRegistroInicio() != null) {
            sb.append("	AND hb.fechaAlta >= ? ");
            parametros.add(DateUtils.preparaFechaConExtremoEnSegundos(
                    param.getFechaRegistroInicio(), true));
            tipos.add(new TimestampType());
        }

        if (param.getFechaRegistroFin() != null) {
            sb.append("	AND hb.fechaAlta <= ? ");
            parametros.add(DateUtils.preparaFechaConExtremoEnSegundos(param.getFechaRegistroFin(),
                    false));
            tipos.add(new TimestampType());
        }

        if (StringUtils.isNotBlank(param.getUoiNumber())) {
            sb.append(" AND b.uoiNumber = ? ");
            parametros.add(param.getUoiNumber().trim().toUpperCase());
            tipos.add(new StringType());
        }

        if (param.getListaBeneficiarios() != null && param.getListaBeneficiarios().size() > 0) {
            sb.append(" AND b.idBeneficiario in (:lista_benef) ");
        }
    }
    
	private String getQueryHistoryForBeneficiario(Long beneficiarioID, String tipoFormato){
		
        StringBuilder sb = new StringBuilder();

        sb.append(" SELECT DISTINCT hb ");
        sb.append(" FROM " + HistoricoBeneficiario.class.getName() + " hb ");
        sb.append("		JOIN FETCH hb.statusAnterior sant ");
        sb.append("		JOIN FETCH hb.statusNuevo snue ");
        sb.append("		JOIN FETCH hb.beneficiario b ");
        sb.append("		JOIN FETCH b.tipoBeneficiario tb ");
        sb.append("		LEFT JOIN FETCH b.institucion i ");
        sb.append("		LEFT JOIN FETCH i.tipoInstitucion ti ");
        sb.append(" WHERE hb.idHistoricoBeneficiario is not null ");
        
        if (beneficiarioID != null && beneficiarioID > 0) {
            sb.append(" AND b.idBeneficiario = :beneficiarioID ");
        }
        
        if (StringUtils.isNotBlank(tipoFormato) && !tipoFormato.equals("-1")) {
            if (tipoFormato.equalsIgnoreCase("W8BEN")) {
                sb.append("	AND b.tipoFormato in ('W8BEN', 'W8BEN2014', 'W8BEN2017') ");
            } else if (BeneficiariosConstantes.FORMATO_W8_IMY.equals(tipoFormato)) {
                sb.append("	AND b.tipoFormato in ('W8IMY', 'W8IMY2015', 'W8IMY2017') ");
            } else if (BeneficiariosConstantes.FORMATO_W8_BEN_E.equals(tipoFormato)) {
                sb.append(" AND b.tipoFormato in ('W8BENE', 'W8BENE2016') ");
            } else {
                sb.append("	AND b.tipoFormato = :tipoFormato ");

            }
        }
        
        sb.append(" ORDER BY hb.statusNuevo");

        log.debug("Query : [" + sb.toString() + "]");
        return sb.toString();
        
	}
	
	private Query setterParamsQueryHistoryForBeneficiario(Long beneficiarioID, String tipoFormato, Query query){
		
        if (beneficiarioID != null && beneficiarioID > 0) {
        	query.setParameter("beneficiarioID", beneficiarioID);
        }
        
        if (tipoFormato != null) {
        	query.setParameter("tipoFormato", tipoFormato);
        }
        
		log.info("queryFinal :: " + query.getQueryString());
        return query;
	}
}
