// Cambio Multidivisas
package com.indeval.portalinternacional.persistence.dao.impl;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.servicios.dto.HorariosCustodiosDto;
import com.indeval.portalinternacional.middleware.servicios.dto.diasIhabilesDivisas.DiasInhabilesDivisasDTO;
import com.indeval.portalinternacional.middleware.servicios.dto.diasIhabilesDivisas.HistoricoDiasInhabilesDivisasDTO;
import com.indeval.portalinternacional.middleware.servicios.modelo.DiasInhabilesDivisas;
import com.indeval.portalinternacional.middleware.servicios.modelo.HorariosCustodios;
import com.indeval.portalinternacional.middleware.servicios.modelo.MovimientoRetiroEfectivoInternacional;
import com.indeval.portalinternacional.middleware.servicios.modelo.REmisionBoveda;
import com.indeval.portalinternacional.middleware.servicios.vo.CriteriosConsultaHorariosCustodiosVO;
import com.indeval.portalinternacional.middleware.servicios.vo.HorariosCustodiosVO;
import com.indeval.portalinternacional.persistence.dao.DiasInhabilesDivisasDao;
import com.indeval.portalinternacional.persistence.util.DTOAssembler;
import org.hibernate.*;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.DateType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class DiasInhabilesDivisasDaoImpl extends BaseDaoHibernateImpl implements DiasInhabilesDivisasDao {
    private static final Logger log = LoggerFactory.getLogger(DiasInhabilesDivisasDaoImpl.class);
    Integer[] estadosValidos = {1, 2};


    @Override
    @SuppressWarnings("unchecked")
    public List<Date> getDiasInhabilesByIdDivisa(final Long idDivisa) {
        return (List<Date>) getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                Criteria criteria = session.createCriteria(DiasInhabilesDivisas.class);

                criteria.add(Restrictions.eq("idDivisa", idDivisa));

                criteria.setProjection(Projections.projectionList()
                        .add(Projections.property("diaInhabil")));

                List<Date> diasInhabiles = criteria.list();

                return diasInhabiles;
            }
        });
    }

    @Override
    public List<Integer> getAniosDisponibles() {
        final StringBuilder sb = new StringBuilder();

        sb.append(" SELECT DISTINCT(EXTRACT(YEAR FROM DID.diaInhabil))  AS anio ");
        sb.append(" FROM  DiasInhabilesDivisas DID");
        sb.append(" WHERE  DID.estatus IN (:estados)");
        sb.append(" ORDER BY 1 ASC ");

        log.debug("Query : [" + sb.toString() + "]");

        List<Integer> aniosDisponibles = (List<Integer>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createQuery(sb.toString());
                query.setParameterList("estados", estadosValidos);
                return query.list();
            }
        });

        log.debug("Años disponibles " + aniosDisponibles);

        return aniosDisponibles;
    }

    public PaginaVO getDiasInhabilesByIdHistorico(final Long idHistorico, final PaginaVO paginaVO) {
        log.debug("getDiasInhabilesByIdHistorico :: idHistorico [" + idHistorico + "]");
        final StringBuilder sb = new StringBuilder();
        sb.append(" SELECT DID.ID_DIAS_INHABILES idDiasInhabiles, DID.DIA_INHABIL diaInhabil, \n");
        sb.append("        EXTRACT(YEAR FROM DID.DIA_INHABIL) anio,\n");
        sb.append("        EXTRACT(MONTH FROM DID.DIA_INHABIL) mes, \n");
        sb.append("        EXTRACT(DAY FROM DID.DIA_INHABIL) dia, \n");
        sb.append("        DID.ID_DIVISA divisaId, D.CLAVE_ALFABETICA divisaClave, D.DESCRIPCION divisaDescripcion, \n");
        sb.append("        DID.CREADOR creador, DID.FECHA_CREACION fechaCreacion, \n");
        sb.append("        DID.FECHA_ULT_MODIFICACION fechaUltModificacion, \n");
        sb.append("        DID.ID_HISTORICO idHistoricoDiasInhabilesDivisas, DID.ESTATUS estatus \n");
        sb.append("        FROM C_DIAS_INHABILES_DIVISAS DID, C_DIVISA D \n");
        sb.append("        WHERE DID.ID_DIVISA = D.ID_DIVISA \n");
        sb.append("          AND DID.ID_HISTORICO = :idHistorico \n");
        if (paginaVO.getRegistrosXPag() != PaginaVO.TODOS) {
            sb.append("          AND DID.ESTATUS IN (1 , 2 ) \n");
        }


        List<DiasInhabilesDivisasDTO> resultados = (List<DiasInhabilesDivisasDTO>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                SQLQuery query = session.createSQLQuery(sb.toString());
                query.setCacheable(false);
                query.setParameter("idHistorico", idHistorico);

                LongType lt = new LongType();
                DateType dt = new DateType();
                StringType st = new StringType();
                IntegerType it = new IntegerType();

                query.addScalar("idDiasInhabiles", lt);
                query.addScalar("diaInhabil", dt);
                query.addScalar("anio", st);
                query.addScalar("mes", st);
                query.addScalar("dia", st);
                query.addScalar("divisaId", lt);
                query.addScalar("divisaClave", st);
                query.addScalar("divisaDescripcion", st);
                query.addScalar("creador", st);
                query.addScalar("fechaCreacion", dt);
                query.addScalar("fechaUltModificacion", dt);
                query.addScalar("idHistoricoDiasInhabilesDivisas", lt);
                query.addScalar("estatus", it);

                log.debug(query.toString());
                query.setResultTransformer(Transformers.aliasToBean(DiasInhabilesDivisasDTO.class));

                paginaVO.setRegistros(query.list());
                paginaVO.setTotalRegistros(paginaVO.getRegistros().size());

                if (paginaVO.getRegistrosXPag() != PaginaVO.TODOS) {
                    query.setFirstResult(paginaVO.getOffset());
                    query.setMaxResults(paginaVO.getRegistrosXPag());
                }

                return query.list();
            }
        });

        paginaVO.setRegistros(resultados);
        return paginaVO;
    }

    public void saveDiasInhabilesDivisas(DiasInhabilesDivisas diasInhabilesDivisas) {
        log.debug("Entrando a saveDiasInhabilesDivisas");
        try {
//			this.getHibernateTemplate().setAllowCreate(true);
//			this.getHibernateTemplate().setCheckWriteOperations(false);
            this.getHibernateTemplate().save(diasInhabilesDivisas);
            this.getHibernateTemplate().flush();
        } catch (Exception ex) {
            log.error("Error en saveMovimientoRetiroEfectivoInternacional", ex);
        }
    }

    @Override
    public void updateDiaInhabilDivisas(Long id, Integer cambioEstado) {
        log.debug("updateDiaInhabilDivisas " +
                ":: idDiaInhabilDivisas [" + id + "]" +
                ":: cambioEstado [" + cambioEstado + "]");
        DiasInhabilesDivisas diaInhabil = (DiasInhabilesDivisas) getByPk(DiasInhabilesDivisas.class, id);
        if (diaInhabil != null) {
            diaInhabil.setEstatus(cambioEstado);
            diaInhabil.setFechaUltModificacion(new Date());
            update(diaInhabil);
        }
    }
}
