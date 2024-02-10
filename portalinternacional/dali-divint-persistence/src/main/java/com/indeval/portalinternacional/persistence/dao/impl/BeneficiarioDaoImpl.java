/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.persistence.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.type.BooleanType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.hibernate.type.TimestampType;
import org.hibernate.type.Type;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.middleware.servicios.util.UtilsVO;
import com.indeval.portaldali.persistence.util.DateUtils;
import com.indeval.portalinternacional.middleware.servicios.constantes.BeneficiariosConstantes;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.BeneficiarioInstitucion;
import com.indeval.portalinternacional.middleware.servicios.modelo.ExemptPayeeW9;
import com.indeval.portalinternacional.middleware.servicios.modelo.ExemptionFatcaW9;
import com.indeval.portalinternacional.middleware.servicios.modelo.MILACodigoDepartamento;
import com.indeval.portalinternacional.middleware.servicios.modelo.MILASectorEconomico;
import com.indeval.portalinternacional.middleware.servicios.modelo.MILATipoDocumento;
import com.indeval.portalinternacional.middleware.servicios.modelo.MILATipoEmpresa;
import com.indeval.portalinternacional.middleware.servicios.vo.BeneficiariosPaginacionVO;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaBeneficiarioVO;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaBeneficiariosParam;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaNombreBeneficiarioParam;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaNombreBeneficiarioVO;
import com.indeval.portalinternacional.middleware.servicios.vo.NombreBeneficiario;
import com.indeval.portalinternacional.persistence.dao.BeneficiarioDao;

@SuppressWarnings({"unchecked"})
public class BeneficiarioDaoImpl extends BaseDaoHibernateImpl implements BeneficiarioDao,
        Constantes {

    /** Log de clase. */
	private static final Logger log = LoggerFactory.getLogger(BeneficiarioDaoImpl.class);
    /** Template de JDBC */
    private JdbcTemplate jdbcTemplate;

    /**
     * @see com.indeval.portalinternacional.persistence.dao.BeneficiarioDao#findBeneficiarios(com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario,
     *      com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO)
     */
    public PaginaVO findBeneficiarios(final ConsultaBeneficiariosParam params, PaginaVO paginaVO, Boolean isPopup) {
        /* Se verifica que los objetos de entrada no sean null */
        if (params == null) {
            throw new IllegalArgumentException(
                    "El objeto Beneficiario de parametros recibido es null");
        }

        paginaVO = UtilsVO.getPaginaNotBlank(paginaVO);
        Long cantidad = null;
        Integer executeConsulta = 1; //Boolean.TRUE
        
        /** Aqui busco si hay beneficiarios con status autorizado **/
        if(isPopup){
            cantidad = this.countConsultaBeneficiarios(params, isPopup, Boolean.TRUE);
            if(cantidad == null || cantidad == 0 ){
            	cantidad = this.countConsultaBeneficiarios(params, isPopup, Boolean.FALSE);
            	executeConsulta = 2;
            }        	
        }
        /** Fin **/
        else {
            cantidad = this.countConsultaBeneficiarios(params, isPopup, Boolean.FALSE);
        }

        if (cantidad != null) {
            paginaVO.setTotalRegistros(cantidad.intValue());
        } else {
            paginaVO.setTotalRegistros(0);
        }

        log.info("Cantidad: [" + paginaVO.getTotalRegistros().intValue() + "]");

        if (paginaVO.getTotalRegistros() != null && paginaVO.getTotalRegistros().intValue() > 0) {
            if (paginaVO.getRegistrosXPag().intValue() != PaginaVO.TODOS.intValue()) {
                System.out.println("********A:" + params.isTraeDireccion());
                paginaVO.setRegistros(this.resultadosConsultaBeneficiarios(params,
                        paginaVO.getOffset(), paginaVO.getRegistrosXPag(), isPopup, (executeConsulta == 1) ? Boolean.TRUE : Boolean.FALSE));
            } else {
                System.out.println("********B: params.isTraeDireccion():"
                        + params.isTraeDireccion());
                if (params.isTraeDireccion()) {
                    paginaVO.setRegistros(this.resultadosConsultaBeneficiariosConFetch(params,
                            paginaVO.getOffset(), paginaVO.getRegistrosXPag(), isPopup, (executeConsulta == 1) ? Boolean.TRUE : Boolean.FALSE));

                } else {
                    paginaVO.setRegistros(this.resultadosConsultaBeneficiariosConFetchSinDirec(
                            params, paginaVO.getOffset(), paginaVO.getRegistrosXPag(), isPopup, (executeConsulta == 1) ? Boolean.TRUE : Boolean.FALSE));
                }

            }
        } else {
            paginaVO.setRegistros(new ArrayList());
        }

        return paginaVO;

    }

    /**
     * @see com.indeval.portalinternacional.persistence.dao.BeneficiarioDao#findBeneficiariosLetras(com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario,
     *      com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO)
     */
    public PaginaVO findBeneficiariosLetras(final ConsultaBeneficiariosParam params,
            PaginaVO paginaVO) {
        /* Se verifica que los objetos de entrada no sean null */
        if (params == null) {
            throw new IllegalArgumentException(
                    "El objeto Beneficiario de parametros recibido es null");
        }
        paginaVO = UtilsVO.getPaginaNotBlank(paginaVO);
        paginaVO.setRegistros(this.resultadosConsultaBeneficiariosLetrasSinDirec(params,
                paginaVO.getOffset(), paginaVO.getRegistrosXPag()));
        return paginaVO;
    }

    /**
     * @see com.indeval.portalinternacional.persistence.dao.BeneficiarioDao#findBeneficiariosByName(com.indeval.portalinternacional.middleware.servicios.vo.ConsultaNombreBeneficiarioParam)
     */
    public PaginaVO findBeneficiariosByName(final ConsultaNombreBeneficiarioParam params) {
        /* Se verifica que los objetos de entrada no sean null */
        if (params == null) {
            throw new IllegalArgumentException(
                    "El objeto Beneficiario de parametros recibido es null");
        }

        PaginaVO paginaVO = new PaginaVO();
        paginaVO.setOffset(0);
        paginaVO.setRegistrosXPag(PaginaVO.TODOS);

        List registros = this.resultadosConsultaBeneficiariosPorNombre(params);
        paginaVO.setRegistros(registros);
        paginaVO.setTotalRegistros(registros.size());

        return paginaVO;

    }

    /**
     * @see com.indeval.portalinternacional.persistence.dao.BeneficiarioDao#findBeneficiarioById(java.lang.Long)
     */
    public Beneficiario findBeneficiarioById(final Long idBeneficiario) {
        log.info("Entrando a findBeneficiarioById()");
        final StringBuilder sb = new StringBuilder();
        final List<Object> paramsSQL = new ArrayList<Object>();
        final List<Type> tipos = new ArrayList<Type>();

        sb.append(" FROM " + Beneficiario.class.getName() + " ben ");
        sb.append(" 	JOIN FETCH ben.tipoBeneficiario tb ");
        sb.append(" 	JOIN FETCH ben.statusBenef stb ");
        sb.append(" 	LEFT JOIN FETCH ben.institucion i ");
        sb.append(" 	LEFT JOIN FETCH i.tipoInstitucion ti ");
        sb.append(" 	LEFT JOIN FETCH ben.domicilioW8Normal domw8nor ");
        sb.append(" 	LEFT JOIN FETCH ben.domicilioW8Correo domw8cor ");
        sb.append(" 	LEFT JOIN FETCH ben.domicilioW9 domw9 ");
        sb.append(" 	LEFT JOIN FETCH ben.domicilioMILA domMila ");
        sb.append(" 	LEFT JOIN FETCH ben.formatoW8BEN w8ben ");
        sb.append(" 	LEFT JOIN FETCH ben.formatoW8IMY w8imy ");
        sb.append(" 	LEFT JOIN FETCH ben.formatoW9 w9 ");
        sb.append(" 	LEFT JOIN FETCH ben.formatoMILA mila ");
        sb.append(" WHERE ben.idBeneficiario = ? ");
        paramsSQL.add(idBeneficiario);
        tipos.add(new LongType());
        sb.append("		AND ben.eliminado = false ");

        return (Beneficiario) this.getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException,
                    SQLException {
                Query query = session.createQuery(sb.toString());
                query.setParameters(paramsSQL.toArray(new Object[0]), tipos.toArray(new Type[0]));
                log.info("Saliendo de findBeneficiarioById()");
                return query.uniqueResult();
            }
        });
    }
    
    /**
     * @see com.indeval.portalinternacional.persistence.dao.BeneficiarioDao#findBeneficiarioById(java.lang.Long)
     */
    public Beneficiario findBeneficiarioByUoiNumber(final String uoiNumber) {
        log.info("Entrando a findBeneficiarioById()");
        final StringBuilder sb = new StringBuilder();
        final List<Object> paramsSQL = new ArrayList<Object>();
        final List<Type> tipos = new ArrayList<Type>();

        sb.append(" FROM " + Beneficiario.class.getName() + " ben ");
        sb.append(" 	JOIN FETCH ben.tipoBeneficiario tb ");
        sb.append(" 	JOIN FETCH ben.statusBenef stb ");
        sb.append(" 	LEFT JOIN FETCH ben.institucion i ");
        sb.append(" 	LEFT JOIN FETCH i.tipoInstitucion ti ");
        sb.append(" 	LEFT JOIN FETCH ben.domicilioW8Normal domw8nor ");
        sb.append(" 	LEFT JOIN FETCH ben.domicilioW8Correo domw8cor ");
        sb.append(" 	LEFT JOIN FETCH ben.domicilioW9 domw9 ");
        sb.append(" 	LEFT JOIN FETCH ben.domicilioMILA domMila ");
        sb.append(" 	LEFT JOIN FETCH ben.formatoW8BEN w8ben ");
        sb.append(" 	LEFT JOIN FETCH ben.formatoW8IMY w8imy ");
        sb.append(" 	LEFT JOIN FETCH ben.formatoW9 w9 ");
        sb.append(" 	LEFT JOIN FETCH ben.formatoMILA mila ");
        sb.append(" WHERE ben.uoiNumber = ? ");
        paramsSQL.add(uoiNumber);
        tipos.add(new StringType());
        sb.append("		AND ben.eliminado = false ");

        return (Beneficiario) this.getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException,
                    SQLException {
                Query query = session.createQuery(sb.toString());
                query.setParameters(paramsSQL.toArray(new Object[0]), tipos.toArray(new Type[0]));
                log.info("Saliendo de findBeneficiarioById()");
                return query.uniqueResult();
            }
        });
    }

    /**
     * @see com.indeval.portalinternacional.persistence.dao.BeneficiarioDao#findBeneficiarioById(java.lang.Long)
     */
    public Beneficiario findBeneficiarioByIdEliminados(final Long idBeneficiario) {
        log.info("Entrando a findBeneficiarioById()");
        final StringBuilder sb = new StringBuilder();
        final List<Object> paramsSQL = new ArrayList<Object>();
        final List<Type> tipos = new ArrayList<Type>();

        sb.append(" FROM " + Beneficiario.class.getName() + " ben ");
        sb.append(" 	JOIN FETCH ben.tipoBeneficiario tb ");
        sb.append(" 	JOIN FETCH ben.statusBenef stb ");
        sb.append(" 	LEFT JOIN FETCH ben.institucion i ");
        sb.append(" 	LEFT JOIN FETCH i.tipoInstitucion ti ");
        sb.append(" 	LEFT JOIN FETCH ben.domicilioW8Normal domw8nor ");
        sb.append(" 	LEFT JOIN FETCH ben.domicilioW8Correo domw8cor ");
        sb.append(" 	LEFT JOIN FETCH ben.domicilioW9 domw9 ");
        sb.append(" 	LEFT JOIN FETCH ben.domicilioMILA domicilioMILA ");
        sb.append(" 	LEFT JOIN FETCH ben.formatoW8BEN w8ben ");
        sb.append(" 	LEFT JOIN FETCH ben.formatoW8IMY w8imy ");
        sb.append(" 	LEFT JOIN FETCH ben.formatoW9 w9 ");
        sb.append(" 	LEFT JOIN FETCH ben.formatoMILA formatoMILA ");
        sb.append(" WHERE ben.idBeneficiario = ? ");
        paramsSQL.add(idBeneficiario);
        tipos.add(new LongType());

        return (Beneficiario) this.getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException,
                    SQLException {
                Query query = session.createQuery(sb.toString());
                query.setParameters(paramsSQL.toArray(new Object[0]), tipos.toArray(new Type[0]));
                log.info("Saliendo de findBeneficiarioByIdEliminado()");
                return query.uniqueResult();
            }
        });
    }


    private Long countConsultaBeneficiarios(final ConsultaBeneficiariosParam params, Boolean isPopup, Boolean onlyAuthorized) {

        StringBuilder sb = new StringBuilder();
        StringBuilder buf = new StringBuilder();
        List<Object> paramsSQL = new ArrayList<Object>();


        sb.append(" SELECT COUNT(*) ");
        sb.append(" FROM (  ");
        sb.append(" 	SELECT DISTINCT ben.id_beneficiario  ");
        sb.append(" 	FROM T_BENEFICIARIOS ben ");
        sb.append(" 		LEFT OUTER JOIN T_BENEFICIARIOS_INSTITUCION bi ON bi.id_beneficiario = ben.id_beneficiario ");
        sb.append(" 		LEFT OUTER JOIN T_CAMPOS_FORMATO_W8BEN w8ben ON w8ben.ID_CAMPOS_FORMATO_W8BEN = ben.ID_CAMPOS_FORMATO_W8BEN ");
        sb.append(" 		LEFT OUTER JOIN T_CAMPOS_FORMATO_W8IMY w8imy ON w8imy.ID_CAMPOS_FORMATO_W8IMY = ben.ID_CAMPOS_FORMATO_W8IMY ");
        sb.append(" 		LEFT OUTER JOIN T_CAMPOS_FORMATO_MILA mila ON mila.ID_CAMPOS_FORMATO_MILA = ben.ID_CAMPOS_FORMATO_MILA ");
        sb.append(" 		LEFT OUTER JOIN T_CAMPOS_FORMATO_W8BENE w8bene ON w8bene.ID_CAMPOS_FORMATO_W8BENE = ben.id_campos_formato_w8bene ");
        sb.append(" 		LEFT OUTER JOIN T_CAMPOS_FORMATO_W8IMY2015 w8imy2015 ON w8imy2015.ID_CAMPOS_FORMATO_W8IMY2015 = ben.id_campos_formato_w8imy2015 ");
        sb.append("	WHERE ben.eliminado = 0 ");

        if (params.getCustodio() != null && params.getCustodio().compareTo(CERO_LONG) > 0) {
            sb.append("	AND ben.id_cuenta_nombrada = ? ");
            paramsSQL.add(params.getCustodio());
        }

        if (params.getInstitucion() != null && params.getInstitucion().getIdInstitucion() != null
                && params.getInstitucion().getIdInstitucion().compareTo(CERO_LONG) > 0) {
            sb.append("	AND bi.id_institucion = ? ");
            paramsSQL.add(params.getInstitucion().getIdInstitucion());
        }

        if (params.getTipoBeneficiario() != null
                && params.getTipoBeneficiario().compareTo(CERO_LONG) > 0) {
            sb.append("	AND ben.id_tipo_beneficiario = ? ");
            paramsSQL.add(params.getTipoBeneficiario());
        }
        
        /** Se agrega bloque para consulta de Popup **/
        if(isPopup){
    		/** Se busca solo por estatus: AUTORIZADO, VENCIDO, CANCELADO **/
			 if (params.getStatusBenef() != null && params.getStatusBenef().compareTo(CERO_LONG) > 0) {
				 if(params.getStatusBenef() == STATUS_BENEFICIARIO_AUTORIZADO 
							|| params.getStatusBenef() == STATUS_BENEFICIARIO_VENCIDO 
							|| params.getStatusBenef() == STATUS_BENEFICIARIO_CANCELADO){
						sb.append("	AND ben.id_status_benef = ? ");
						paramsSQL.add(params.getStatusBenef());
				 } else {
						sb.append("	AND ben.id_status_benef = ? ");
						paramsSQL.add(STATUS_BENEFICIARIO_NO_EXISTE);
				 }
			} else {
				if (params.getStatusBenef() != null && params.getStatusBenef() == UNO_MENOS_LONG && onlyAuthorized) {
	    			sb.append("	AND ben.id_status_benef IN (2) ");
				} else if (params.getStatusBenef() != null && params.getStatusBenef() == UNO_MENOS_LONG && !onlyAuthorized) {
					sb.append("	AND ben.id_status_benef IN (3,5) ");
				}
    		}
        } else {
            if (params.getStatusBenef() != null && params.getStatusBenef().compareTo(CERO_LONG) > 0) {
                sb.append("	AND ben.id_status_benef = ? ");
                paramsSQL.add(params.getStatusBenef());
            }
    	}

        if (StringUtils.isNotBlank(params.getFormato()) && !params.getFormato().equals("-1")) {
            if (params.getFormato().equalsIgnoreCase("W8BEN")) {
                sb.append("	AND ben.tipo_Formato in (?,?,?) ");
                paramsSQL.add(params.getFormato().trim().toUpperCase());
                paramsSQL.add("W8BEN2014");
                paramsSQL.add("W8BEN2017");
                
                /** Se agrega criterio de busqueda por RFC **/
                if(StringUtils.isNotBlank(params.getRfc()) && params.getRfc() != null){
                	sb.append("	AND w8ben.FOREIGN_TAX_ID_NUMB = ? ");
                	paramsSQL.add(params.getRfc());
                }  

            } else if (BeneficiariosConstantes.FORMATO_W8_BEN_E.equals(params.getFormato())) {
                sb.append("	AND ben.tipo_Formato in (?,?) ");
                paramsSQL.add(params.getFormato().trim().toUpperCase());
                paramsSQL.add(BeneficiariosConstantes.FORMATO_W8_BEN_E_2016);
                /** Se agrega criterio de busqueda por RFC **/
                if(StringUtils.isNotBlank(params.getRfc()) && params.getRfc() != null){
                	sb.append("	AND w8bene.FOREING_TIN = ? ");
                	paramsSQL.add(params.getRfc());
                }
            } else if (BeneficiariosConstantes.FORMATO_W8_IMY.equals(params.getFormato())) {
                sb.append(" AND ben.tipo_Formato in (?,?,?) ");
                paramsSQL.add(params.getFormato().trim().toUpperCase());
                paramsSQL.add(BeneficiariosConstantes.FORMATO_W8_IMY2015);
                paramsSQL.add(BeneficiariosConstantes.FORMATO_W8_IMY2017);
            } else {
                sb.append("	AND ben.tipo_Formato = ? ");
                paramsSQL.add(params.getFormato().trim().toUpperCase());
            }

        }

        if (params.getFechaRegistroInicio() != null) {
            sb.append("	AND ben.fecha_registro >= ? ");
            log.debug("Fecha REgistro Final: ["
                    + DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaRegistroInicio(),
                            true) + "]");
            paramsSQL.add(DateUtils.preparaFechaConExtremoEnSegundos(
                    params.getFechaRegistroInicio(), true));
        }

        if (params.getFechaRegistroFin() != null) {
            sb.append("	AND ben.fecha_registro <= ? ");
            log.debug("Fecha REgistro Final: ["
                    + DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaRegistroFin(),
                            false) + "]");
            paramsSQL.add(DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaRegistroFin(),
                    false));
        }

        if (params.getFechaFormatoInicio() != null) {
            sb.append("	AND ben.fecha_formato >= ? ");
            paramsSQL.add(DateUtils.preparaFechaConExtremoEnSegundos(
                    params.getFechaFormatoInicio(), true));
        }

        if (params.getFechaFormatoFin() != null) {
            sb.append("	AND ben.fecha_formato <= ? ");
            paramsSQL.add(DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaFormatoFin(),
                    false));
        }

        if (StringUtils.isNotBlank(params.getUoiNumber())) {
            sb.append("	AND ben.UOI = ? "); 
            paramsSQL.add(params.getUoiNumber().trim().toUpperCase());
        }

        if (StringUtils.isNotBlank(params.getReferenceNumber())) {
            sb.append("	AND (w8ben.REFERENCE_NUMBERS LIKE ? ");
            paramsSQL.add(params.getReferenceNumber().trim().toUpperCase() + "%");
            sb.append("	OR w8imy.REFERENCE_NUMBERS LIKE ? ");
            paramsSQL.add(params.getReferenceNumber().trim().toUpperCase() + "%");
            sb.append("	OR mila.REFERENCIA LIKE ? ");
            paramsSQL.add(params.getReferenceNumber().trim().toUpperCase() + "%");
            sb.append("	OR w8bene.REFERENCE_NUMBER LIKE ? ");
            paramsSQL.add(params.getReferenceNumber().trim().toUpperCase() + "%");
            sb.append("	OR w8imy2015.REFERENCE_NUMBER LIKE ?) ");
            paramsSQL.add(params.getReferenceNumber().trim().toUpperCase() + "%");
        }

        if (params.getFechaAutorizacionInicio() != null) {
            sb.append("	AND ben.FECHA_AUTORIZACION >= ? ");
            paramsSQL.add(DateUtils.preparaFechaConExtremoEnSegundos(
                    params.getFechaAutorizacionInicio(), true));
        }

        if (params.getFechaAutorizacionFin() != null) {
            sb.append("	AND ben.FECHA_AUTORIZACION <= ? ");
            paramsSQL.add(DateUtils.preparaFechaConExtremoEnSegundos(
                    params.getFechaAutorizacionFin(), false));
        }

        if (StringUtils.isNotBlank(params.getAdp())) {
            sb.append("	AND ben.ADP = ? ");
            paramsSQL.add(params.getAdp().trim().toUpperCase());
        }

        if (StringUtils.isNotBlank(params.getNombreRazonSocial())) {
            sb.append(" AND (concat(ben.NOMBRE_BENEF,concat(' ',concat(ben.APELLIDO_PATERNO_BENEF,concat(' ',ben.APELLIDO_MATERNO_BENEF)))) like ? OR ben.RAZON_SOCIAL_BENEF like ?) ");
            String comodin = "";
            if (params.getNombreRazonSocial().trim().length() > 1) {
                comodin = "%";
            }
            paramsSQL.add(comodin + params.getNombreRazonSocial().trim().toUpperCase() + "%");
            paramsSQL.add(comodin + params.getNombreRazonSocial().trim().toUpperCase() + "%");
        }

        if (params.getActivo() != null) {
            sb.append(" AND ben.activo = ? ");
            paramsSQL.add(params.getActivo());
        }

        sb.append("	) ");

        log.debug("Query : [" + sb.toString() + "]");

        return this.jdbcTemplate.queryForLong(sb.toString(), paramsSQL.toArray(new Object[0]));

    }


    public List<BeneficiariosPaginacionVO> paginasConsultaBeneficiarios(
            final ConsultaBeneficiariosParam params) {
        StringBuilder sb = new StringBuilder();
        StringBuilder buf = new StringBuilder();
        List<Object> paramsSQL = new ArrayList<Object>();
        List<BeneficiariosPaginacionVO> lBeneficiariosPaginacionVO =
                new ArrayList<BeneficiariosPaginacionVO>();

        sb.append(" SELECT distinct(nvl(substr(ltrim(ben.NOMBRE_BENEF),1,1),substr(ltrim(ben.RAZON_SOCIAL_BENEF),1,1))) letra, to_char(COUNT(ben.NOMBRE_BENEF)) registros, to_char(round(COUNT(ben.NOMBRE_BENEF)/"
                + Constantes.noRegistrosxPag + ")) paginas ");
        sb.append(" 	FROM T_BENEFICIARIOS ben ");
        sb.append(" 		LEFT OUTER JOIN T_BENEFICIARIOS_INSTITUCION bi ON bi.id_beneficiario = ben.id_beneficiario ");
        if (StringUtils.isNotBlank(params.getReferenceNumber())) {
            sb.append(" 		LEFT OUTER JOIN T_CAMPOS_FORMATO_W8BEN w8ben ON w8ben.ID_CAMPOS_FORMATO_W8BEN = ben.ID_CAMPOS_FORMATO_W8BEN ");
            sb.append(" 		LEFT OUTER JOIN T_CAMPOS_FORMATO_W8IMY w8imy ON w8imy.ID_CAMPOS_FORMATO_W8IMY = ben.ID_CAMPOS_FORMATO_W8IMY ");
            sb.append(" 		LEFT OUTER JOIN T_CAMPOS_FORMATO_MILA mila ON mila.ID_CAMPOS_FORMATO_MILA = ben.ID_CAMPOS_FORMATO_MILA ");
        }
        sb.append("	WHERE ben.eliminado = 0 ");


        if (params.getCustodio() != null && params.getCustodio().compareTo(CERO_LONG) > 0) {
            sb.append("	AND ben.id_cuenta_nombrada = ? ");
            paramsSQL.add(params.getCustodio());
        }

        if (params.getInstitucion() != null && params.getInstitucion().getIdInstitucion() != null
                && params.getInstitucion().getIdInstitucion().compareTo(CERO_LONG) > 0) {
            sb.append("	AND bi.id_institucion = ? ");
            paramsSQL.add(params.getInstitucion().getIdInstitucion());
        }

        if (params.getTipoBeneficiario() != null
                && params.getTipoBeneficiario().compareTo(CERO_LONG) > 0) {
            sb.append("	AND ben.id_tipo_beneficiario = ? ");
            paramsSQL.add(params.getTipoBeneficiario());
        }

        if (params.getStatusBenef() != null && params.getStatusBenef().compareTo(CERO_LONG) > 0) {
            sb.append("	AND ben.id_status_benef = ? ");
            paramsSQL.add(params.getStatusBenef());
        }

        if (StringUtils.isNotBlank(params.getFormato()) && !params.getFormato().equals("-1")) {
            if (params.getFormato().equalsIgnoreCase("W8BEN")) {
                sb.append("	AND ben.tipo_Formato in (?,?,?) ");
                paramsSQL.add(params.getFormato().trim().toUpperCase());
                paramsSQL.add("W8BEN2014");
                paramsSQL.add("W8BEN2017");

            } else {
                sb.append("	AND ben.tipo_Formato = ? ");
                paramsSQL.add(params.getFormato().trim().toUpperCase());

            }
        }

        if (params.getFechaRegistroInicio() != null) {
            sb.append("	AND ben.fecha_registro >= ? ");
            log.debug("Fecha REgistro Final: ["
                    + DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaRegistroInicio(),
                            true) + "]");
            paramsSQL.add(DateUtils.preparaFechaConExtremoEnSegundos(
                    params.getFechaRegistroInicio(), true));
        }

        if (params.getFechaRegistroFin() != null) {
            sb.append("	AND ben.fecha_registro <= ? ");
            log.debug("Fecha REgistro Final: ["
                    + DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaRegistroFin(),
                            false) + "]");
            paramsSQL.add(DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaRegistroFin(),
                    false));
        }

        if (params.getFechaFormatoInicio() != null) {
            sb.append("	AND ben.fecha_formato >= ? ");
            paramsSQL.add(DateUtils.preparaFechaConExtremoEnSegundos(
                    params.getFechaFormatoInicio(), true));
        }

        if (params.getFechaFormatoFin() != null) {
            sb.append("	AND ben.fecha_formato <= ? ");
            paramsSQL.add(DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaFormatoFin(),
                    false));
        }

        if (StringUtils.isNotBlank(params.getUoiNumber())) {
            sb.append("	AND ben.UOI LIKE ? ");
            paramsSQL.add(params.getUoiNumber().trim().toUpperCase() + "%");
        }

        if (StringUtils.isNotBlank(params.getReferenceNumber())) {
            sb.append("	AND (w8ben.REFERENCE_NUMBERS LIKE ? ");
            paramsSQL.add(params.getReferenceNumber().trim().toUpperCase() + "%");
            sb.append("	OR w8imy.REFERENCE_NUMBERS LIKE ?) ");
            paramsSQL.add(params.getReferenceNumber().trim().toUpperCase() + "%");
        }

        if (params.getFechaAutorizacionInicio() != null) {
            sb.append("	AND ben.FECHA_AUTORIZACION >= ? ");
            paramsSQL.add(DateUtils.preparaFechaConExtremoEnSegundos(
                    params.getFechaAutorizacionInicio(), true));
        }

        if (params.getFechaAutorizacionFin() != null) {
            sb.append("	AND ben.FECHA_AUTORIZACION <= ? ");
            paramsSQL.add(DateUtils.preparaFechaConExtremoEnSegundos(
                    params.getFechaAutorizacionFin(), false));
        }

        if (StringUtils.isNotBlank(params.getAdp())) {
            sb.append("	AND ben.ADP = ? ");
            paramsSQL.add(params.getAdp().trim().toUpperCase());
        }

        if (StringUtils.isNotBlank(params.getNombreRazonSocial())) {
            sb.append(" AND (concat(ben.NOMBRE_BENEF,concat(' ',concat(ben.APELLIDO_PATERNO_BENEF,concat(' ',ben.APELLIDO_MATERNO_BENEF)))) like ? OR ben.RAZON_SOCIAL_BENEF like ?) ");
            String comodin = "";
            if (params.getNombreRazonSocial().trim().length() > 1) {
                comodin = "%";
            }
            paramsSQL.add(comodin + params.getNombreRazonSocial().trim().toUpperCase() + "%");
            paramsSQL.add(comodin + params.getNombreRazonSocial().trim().toUpperCase() + "%");
        }

        sb.append(" 		  group by nvl(substr(ltrim(ben.NOMBRE_BENEF),1,1),substr(ltrim(ben.RAZON_SOCIAL_BENEF),1,1)) order by 1");

        log.debug("Query : [" + sb.toString() + "]");


        System.out.println("***CONSUlTA:" + sb.toString());

        List<Object> lstResult =
                this.jdbcTemplate.queryForList(sb.toString(), paramsSQL.toArray(new Object[0]));


        Iterator keys = lstResult.iterator();
        while (keys.hasNext()) {

            ListOrderedMap entry = (ListOrderedMap) keys.next();
            BeneficiariosPaginacionVO benef = new BeneficiariosPaginacionVO();
            benef.setLetra((String) entry.get("LETRA"));
            benef.setCantidad(Integer.valueOf((String) entry.get("REGISTROS")));
            benef.setPaginas(Integer.valueOf((String) entry.get("PAGINAS")));
            lBeneficiariosPaginacionVO.add(benef);

        }



        return lBeneficiariosPaginacionVO;
    }

    public Long findBeneficiarioByUOI(final String uoiNumber) {
        final StringBuilder sb = new StringBuilder();

        sb.append(" SELECT  COUNT(*) ");
        sb.append(" FROM " + Beneficiario.class.getName() + " ben ");
        sb.append(" WHERE ben.uoiNumber like :uoiNumber1 ");

        List<Long> count =
                this.getHibernateTemplate().findByNamedParam(sb.toString(), "uoiNumber1",
                        uoiNumber + "%");
        if (count != null && count.size() == 1) {
            return count.get(0);
        }
        return null;
    }

    public Long findUOIMaxConsecutivo(final String uoiNumber) {
        final StringBuilder sb = new StringBuilder();

        sb.append(" SELECT max(to_number(substr(uoi, -4)))+1 as UOINEXT ");
        sb.append(" FROM T_BENEFICIARIOS  Where uoi like ?  ");
        // sb.append(" and LENGTH(uoi)=12 ");
        // verifica que sea un numero los ultimos 4 digitos del uoi
        sb.append(" and LENGTH(TRIM(TRANSLATE(substr(uoi, -4), ' +-.0123456789', ' '))) is null ");

        Long retorno = (Long) this.getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException,
                    SQLException {
                SQLQuery query = session.createSQLQuery(sb.toString());
                query.setParameter(0, uoiNumber + "%");
                query.addScalar("UOINEXT", Hibernate.LONG);

                return query.uniqueResult();
            }
        });

        return retorno;
    }

    public Long findMaxBeneficiarioMilaByCustodio(final long idCuentaNombrada) {
        final StringBuilder sb = new StringBuilder();

        sb.append(" SELECT  COUNT(*) ");
        sb.append(" FROM " + Beneficiario.class.getName() + " ben");
        sb.append(" WHERE ben.tipoFormato = 'MILA'");
        sb.append(" and ben.idCuentaNombrada = :idCuentaNombrada ");

        List<Long> count =
                this.getHibernateTemplate().findByNamedParam(sb.toString(), "idCuentaNombrada",
                        idCuentaNombrada);
        if (count != null && count.size() == 1) {
            return count.get(0);
        }
        return null;
    }

    private List<Beneficiario> resultadosConsultaBeneficiarios(
            final ConsultaBeneficiariosParam params, final Integer offset,
            final Integer registrosPorPagina, final Boolean isPopup, final Boolean onlyAuthorized) {

        final StringBuilder sb = new StringBuilder();
        final List<Object> paramsSQL = new ArrayList<Object>();
        final List<Type> tipos = new ArrayList<Type>();

        // sb.append(" SELECT  ben ");
        sb.append(" SELECT distinct ben ");
        sb.append(" FROM " + Beneficiario.class.getName() + " ben ");
        sb.append(" 	JOIN FETCH ben.tipoBeneficiario tb ");
        sb.append(" 	JOIN FETCH ben.statusBenef stb ");
        sb.append(" 	LEFT JOIN FETCH ben.domicilioW8Normal domW8Normal ");
        sb.append(" 	LEFT JOIN FETCH ben.domicilioW8Correo domW8Mail ");
        sb.append(" 	LEFT JOIN FETCH ben.domicilioW9 domW9 ");
        sb.append(" 	LEFT JOIN FETCH ben.domicilioMILA domicilioMILA ");
        sb.append(" 	LEFT JOIN FETCH ben.formatoW8BEN forW8ben ");
        sb.append(" 	LEFT JOIN FETCH forW8ben.field3 f3W8ben ");
        sb.append(" 	LEFT JOIN FETCH ben.formatoW8IMY forW8imy ");
        sb.append(" 	LEFT JOIN FETCH forW8imy.field3 f3W8imy ");
        sb.append(" 	LEFT JOIN FETCH ben.formatoW9 forW9 ");
        sb.append(" 	LEFT JOIN FETCH forW9.typeTaxPayer f3W9 ");
        sb.append(" 	LEFT JOIN FETCH ben.formatoMILA formatoMILA ");
        sb.append(" 	LEFT JOIN FETCH formatoMILA.tipoDocumentoIndentidad tipoDocumentoIndentidad ");
        sb.append(" 	LEFT JOIN ben.institucion i ");
        sb.append(" 	LEFT JOIN i.tipoInstitucion ti ");
        sb.append(" 	LEFT JOIN i.estadoInstitucion edoi ");

        sb.append(this.costruyeParams(paramsSQL, tipos, params, isPopup, onlyAuthorized));

        if(isPopup){
            sb.append(" ORDER BY ben.statusBenef.idStatusBenef, ben.idBeneficiario, tb.descTipoBeneficiario, ben.tipoFormato ");
        } else {
            sb.append(" ORDER BY ben.idBeneficiario, tb.descTipoBeneficiario, ben.tipoFormato ");        	
        }


        List<Beneficiario> retorno =
                (List<Beneficiario>) this.getHibernateTemplate().execute(new HibernateCallback() {

                    public Object doInHibernate(final Session session) throws HibernateException,
                            SQLException {
                        Query query = session.createQuery(sb.toString());
                        query.setParameters(paramsSQL.toArray(new Object[0]),
                                tipos.toArray(new Type[0]));
                        if (registrosPorPagina.intValue() != PaginaVO.TODOS.intValue()) {
                            query.setFirstResult(offset.intValue());
                            query.setMaxResults(registrosPorPagina);
                        }
                        return query.list();
                    }
                });

        return retorno;
    }

    private List<Beneficiario> resultadosConsultaBeneficiariosConFetch(
            final ConsultaBeneficiariosParam params, final Integer offset,
            final Integer registrosPorPagina, final Boolean isPopup, final Boolean onlyAuthorized) {

        List<Beneficiario> retorno = null;
        final StringBuilder sb = new StringBuilder();
        final List<Object> paramsSQL = new ArrayList<Object>();
        final List<Type> tipos = new ArrayList<Type>();

        Integer pagsReportesTmp = null;
        if(params.getPagina() != null){
        	pagsReportesTmp = params.getPagina() * Constantes.noRegistrosxPag;
        }
        
        final Integer pagsReportes = pagsReportesTmp;
        // final Integer pagsReportesFinal =
        // (params.getPagina()*Constantes.noRegistrosxPag)+noRegistrosxPag ;

        sb.append(" SELECT  DISTINCT ben ");
        sb.append(" FROM " + Beneficiario.class.getName() + " ben ");
        sb.append(" 	JOIN FETCH ben.tipoBeneficiario tb ");
        sb.append(" 	JOIN FETCH ben.statusBenef stb ");
        sb.append(" 	LEFT JOIN FETCH ben.domicilioW8Normal domW8Normal ");
        sb.append(" 	LEFT JOIN FETCH ben.domicilioW8Correo domW8Mail ");
        sb.append(" 	LEFT JOIN FETCH ben.domicilioW9 domW9 ");
        sb.append(" 	LEFT JOIN FETCH ben.domicilioMILA domMILA ");
        sb.append(" 	LEFT JOIN FETCH ben.formatoW8BEN forW8ben ");
        sb.append(" 	LEFT JOIN FETCH forW8ben.field3 f3W8ben ");
        sb.append(" 	LEFT JOIN FETCH ben.formatoW8IMY forW8imy ");
        sb.append(" 	LEFT JOIN FETCH forW8imy.field3 f3W8imy ");
        sb.append(" 	LEFT JOIN FETCH ben.formatoW9 forW9 ");
        sb.append(" 	LEFT JOIN FETCH forW9.typeTaxPayer f3W9 ");
        sb.append(" 	LEFT JOIN FETCH ben.institucion i ");
        sb.append(" 	LEFT JOIN FETCH ben.formatoMILA formatoMILA ");
        sb.append(" 	LEFT JOIN FETCH formatoMILA.tipoDocumentoIndentidad tipoDocumentoIndentidad ");
        sb.append(" 	LEFT JOIN FETCH i.tipoInstitucion ti ");
        sb.append(" 	LEFT JOIN FETCH i.estadoInstitucion edoi ");

        sb.append(this.costruyeParams(paramsSQL, tipos, params, isPopup, onlyAuthorized));

        if(isPopup){
            sb.append(" ORDER BY ben.statusBenef.idStatusBenef, ben.idBeneficiario,tb.descTipoBeneficiario, ben.tipoFormato ");
        } else {
            sb.append(" ORDER BY ben.idBeneficiario,tb.descTipoBeneficiario, ben.tipoFormato ");
        }

        try {
            retorno =
                    (List<Beneficiario>) this.getHibernateTemplate().execute(
                            new HibernateCallback() {

                                public Object doInHibernate(final Session session)
                                        throws HibernateException, SQLException {
                                    Query query = session.createQuery(sb.toString());
                                    query.setParameters(paramsSQL.toArray(new Object[0]),
                                            tipos.toArray(new Type[0]));
                                    if (registrosPorPagina.intValue() != PaginaVO.TODOS.intValue()) {
                                        query.setFirstResult(offset.intValue());
                                        query.setMaxResults(registrosPorPagina);
                                    }
                                    if (pagsReportes != null) {
                                        query.setFirstResult(pagsReportes);
                                        query.setMaxResults(Constantes.noRegistrosxPag);
                                    }

                                    return query.list();
                                }
                            });


        } catch (Exception e) {
            e.getMessage();
        }
        return retorno;


    }

    private List<Beneficiario> resultadosConsultaBeneficiariosConFetchSinDirec(
            final ConsultaBeneficiariosParam params, final Integer offset,
            final Integer registrosPorPagina, final Boolean isPopup, final Boolean onlyAuthorized) {

        final StringBuilder sb = new StringBuilder();
        final List<Object> paramsSQL = new ArrayList<Object>();
        final List<Type> tipos = new ArrayList<Type>();

        sb.append(" SELECT  DISTINCT ben ");
        sb.append(" FROM " + Beneficiario.class.getName() + " ben ");
        sb.append(" 	JOIN FETCH ben.tipoBeneficiario tb ");
        sb.append(" 	JOIN FETCH ben.statusBenef stb ");
        sb.append(" 	LEFT JOIN FETCH ben.domicilioW8Correo domW8Mail ");
        sb.append(" 	LEFT JOIN FETCH ben.formatoW8BEN forW8ben ");
        sb.append(" 	LEFT JOIN FETCH forW8ben.field3 f3W8ben ");
        sb.append(" 	LEFT JOIN FETCH ben.formatoW8IMY forW8imy ");
        sb.append(" 	LEFT JOIN FETCH forW8imy.field3 f3W8imy ");
        sb.append(" 	LEFT JOIN FETCH ben.formatoW9 forW9 ");
        sb.append(" 	LEFT JOIN FETCH forW9.typeTaxPayer f3W9 ");
        sb.append(" 	LEFT JOIN FETCH ben.formatoMILA formatoMILA ");
        sb.append(" 	LEFT JOIN FETCH formatoMILA.tipoDocumentoIndentidad tipoDocumentoIndentidad ");
        sb.append(" 	LEFT JOIN FETCH ben.institucion i ");
        sb.append(" 	LEFT JOIN FETCH i.tipoInstitucion ti ");
        sb.append(" 	LEFT JOIN FETCH i.estadoInstitucion edoi ");

        sb.append(this.costruyeParams(paramsSQL, tipos, params, isPopup, onlyAuthorized));

        // if(params.isOrdernar())
        // {
        
        
        if(isPopup){
            sb.append(" ORDER BY ben.statusBenef.idStatusBenef, ben.idBeneficiario,tb.descTipoBeneficiario, ben.tipoFormato ");
        } else {
            sb.append(" ORDER BY ben.idBeneficiario,tb.descTipoBeneficiario, ben.tipoFormato ");
        }
        // }

        List<Beneficiario> retorno =
                (List<Beneficiario>) this.getHibernateTemplate().execute(new HibernateCallback() {

                    public Object doInHibernate(final Session session) throws HibernateException,
                            SQLException {
                        Query query = session.createQuery(sb.toString());
                        query.setParameters(paramsSQL.toArray(new Object[0]),
                                tipos.toArray(new Type[0]));
                        if (registrosPorPagina.intValue() != PaginaVO.TODOS.intValue()) {
                            query.setFirstResult(offset.intValue());
                            query.setMaxResults(registrosPorPagina);
                        }
                        return query.list();
                    }
                });

        return retorno;
    }

    private List<Beneficiario> resultadosConsultaBeneficiariosLetrasSinDirec(
            final ConsultaBeneficiariosParam params, final Integer offset,
            final Integer registrosPorPagina) {

        final StringBuilder sb = new StringBuilder();
        final List<Object> paramsSQL = new ArrayList<Object>();
        final List<Type> tipos = new ArrayList<Type>();

        sb.append(" SELECT  new " + ConsultaBeneficiarioVO.class.getName() + " ( ");
        sb.append(" ben.idBeneficiario, ben.nombres, ");
        sb.append(" ben.razonSocial )  ");

        sb.append(" FROM " + Beneficiario.class.getName() + " ben ");
        // sb.append(" 	LEFT JOIN FETCH ben.domicilioW8Correo domW8Mail ");
        if (StringUtils.isNotBlank(params.getReferenceNumber())) {
            sb.append(" 	LEFT JOIN FETCH ben.formatoW8BEN forW8ben ");
            sb.append(" 	LEFT JOIN FETCH forW8ben.field3 f3W8ben ");
            sb.append(" 	LEFT JOIN FETCH ben.formatoW8IMY forW8imy ");
            sb.append(" 	LEFT JOIN FETCH forW8imy.field3 f3W8imy ");
            sb.append(" 	LEFT JOIN FETCH ben.formatoMILA mila ");
            sb.append(" 	LEFT JOIN FETCH mila.tipoDocumentoIndentidad tipoDocumentoIndentidad ");
        }
        // sb.append(" 	LEFT JOIN FETCH ben.formatoW9 forW9 ");
        // sb.append(" 	LEFT JOIN FETCH forW9.typeTaxPayer f3W9 ");
        if (params.getInstitucion() != null && params.getInstitucion().getIdInstitucion() != null
                && params.getInstitucion().getIdInstitucion().compareTo(CERO_LONG) > 0) {
            sb.append(" 	LEFT JOIN FETCH ben.institucion i ");
            sb.append(" 	LEFT JOIN FETCH i.tipoInstitucion ti ");
            sb.append(" 	LEFT JOIN FETCH i.estadoInstitucion edoi ");
        }

        sb.append(" WHERE ben.eliminado = false ");

        if (params.getCustodio() != null && params.getCustodio().compareTo(CERO_LONG) > 0) {
            sb.append("	AND ben.idCuentaNombrada = ? ");
            paramsSQL.add(params.getCustodio());
            tipos.add(new LongType());
        }

        if (params.getInstitucion() != null && params.getInstitucion().getIdInstitucion() != null
                && params.getInstitucion().getIdInstitucion().compareTo(CERO_LONG) > 0) {
            sb.append("	AND i.idInstitucion = ? ");
            paramsSQL.add(params.getInstitucion().getIdInstitucion());
            tipos.add(new LongType());
        }

        if (params.getTipoBeneficiario() != null
                && params.getTipoBeneficiario().compareTo(CERO_LONG) > 0) {
            sb.append("	AND ben.tipoBeneficiario.idTipoBeneficiario = ? ");
            paramsSQL.add(params.getTipoBeneficiario());
            tipos.add(new LongType());
        }

        if (params.getStatusBenef() != null && params.getStatusBenef().compareTo(CERO_LONG) > 0) {
            sb.append("	AND ben.statusBenef.idStatusBenef = ? ");
            paramsSQL.add(params.getStatusBenef());
            tipos.add(new LongType());
        }

        if (StringUtils.isNotBlank(params.getFormato()) && !params.getFormato().equals("-1")) {
            if (BeneficiariosConstantes.FORMATO_W8_BEN.equals(params.getFormato())) {
                sb.append("	AND ben.tipoFormato in (?,?,?) ");
                paramsSQL.add(params.getFormato().trim().toUpperCase());
                tipos.add(new StringType());
                paramsSQL.add(BeneficiariosConstantes.FORMATO_W8_BEN2014);
                tipos.add(new StringType());
                paramsSQL.add(BeneficiariosConstantes.FORMATO_W8_BEN2017);
                tipos.add(new StringType());
            } else if (BeneficiariosConstantes.FORMATO_W8_BEN_E.equals(params.getFormato())) {
                sb.append(" AND ben.tipoFormato in (?,?) ");
                paramsSQL.add(params.getFormato().trim().toUpperCase());
                tipos.add(new StringType());
                paramsSQL.add(BeneficiariosConstantes.FORMATO_W8_BEN_E_2016);
                tipos.add(new StringType());
            } else if (BeneficiariosConstantes.FORMATO_W8_IMY.equals(params.getFormato())) {
                sb.append(" AND ben.tipoFormato in (?,?,?) ");
                paramsSQL.add(params.getFormato().trim().toUpperCase());
                tipos.add(new StringType());
                paramsSQL.add(BeneficiariosConstantes.FORMATO_W8_IMY2015);
                tipos.add(new StringType());
                paramsSQL.add(BeneficiariosConstantes.FORMATO_W8_IMY2017);
                tipos.add(new StringType());
            } else {
                sb.append("	AND ben.tipoFormato = ? ");
                paramsSQL.add(params.getFormato().trim().toUpperCase());
                tipos.add(new StringType());
            }
        }

        if (params.getFechaRegistroInicio() != null) {
            sb.append("	AND ben.fechaRegistro >= ? ");
            log.debug("Fecha REgistro Final: ["
                    + DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaRegistroInicio(),
                            true) + "]");
            paramsSQL.add(DateUtils.preparaFechaConExtremoEnSegundos(
                    params.getFechaRegistroInicio(), true));
            tipos.add(new TimestampType());
        }

        if (params.getFechaRegistroFin() != null) {
            sb.append("	AND ben.fechaRegistro <= ? ");
            log.debug("Fecha REgistro Final: ["
                    + DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaRegistroFin(),
                            false) + "]");
            paramsSQL.add(DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaRegistroFin(),
                    false));
            tipos.add(new TimestampType());
        }

        if (params.getFechaFormatoInicio() != null) {
            sb.append("	AND ben.fechaFormato >= ? ");
            paramsSQL.add(DateUtils.preparaFechaConExtremoEnSegundos(
                    params.getFechaFormatoInicio(), true));
            tipos.add(new TimestampType());
        }

        if (params.getFechaFormatoFin() != null) {
            sb.append("	AND ben.fechaFormato <= ? ");
            paramsSQL.add(DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaFormatoFin(),
                    false));
            tipos.add(new TimestampType());
        }

        if (StringUtils.isNotBlank(params.getUoiNumber())) {
            sb.append("	AND ben.uoiNumber LIKE ? ");
            paramsSQL.add(params.getUoiNumber().trim().toUpperCase() + "%");
            tipos.add(new StringType());
        }

        if (StringUtils.isNotBlank(params.getReferenceNumber())) {
            sb.append("	AND (forW8ben.referenceNumbers LIKE ? ");
            paramsSQL.add(params.getReferenceNumber().trim().toUpperCase() + "%");
            tipos.add(new StringType());
            sb.append("	OR forW8imy.referenceNumbers LIKE ? ");
            paramsSQL.add(params.getReferenceNumber().trim().toUpperCase() + "%");
            tipos.add(new StringType());
            sb.append("     OR '1'=(select '1' from ben.formatoW8BENE forW8bene where forW8bene.referenceNumber LIKE ?) ");
            paramsSQL.add(params.getReferenceNumber().trim().toUpperCase() + "%");
            tipos.add(new StringType());
            sb.append("	OR '1'=(select '1' from ben.formatoW8IMY2015 forW8imy where forW8imy.referenceNumber LIKE ?) ) ");
            paramsSQL.add(params.getReferenceNumber().trim().toUpperCase() + "%");
            tipos.add(new StringType());
            sb.append(" OR '1'=(select '1' from ben.formatoW8IMY2017 forW8imy where forW8imy.referenceNumber LIKE ?) ) ");
            paramsSQL.add(params.getReferenceNumber().trim().toUpperCase() + "%");
            tipos.add(new StringType());
        }

        if (params.getFechaAutorizacionInicio() != null) {
            sb.append("	AND ben.fechaAutorizacion >= ? ");
            paramsSQL.add(DateUtils.preparaFechaConExtremoEnSegundos(
                    params.getFechaAutorizacionInicio(), true));
            tipos.add(new TimestampType());
        }

        if (params.getFechaAutorizacionFin() != null) {
            sb.append("	AND ben.fechaAutorizacion <= ? ");
            paramsSQL.add(DateUtils.preparaFechaConExtremoEnSegundos(
                    params.getFechaAutorizacionFin(), false));
            tipos.add(new TimestampType());
        }

        if (StringUtils.isNotBlank(params.getAdp())) {
            sb.append("	AND ben.adp = ? ");
            paramsSQL.add(params.getAdp().trim().toUpperCase());
            tipos.add(new StringType());
        }

        if (StringUtils.isNotBlank(params.getNombreRazonSocial())) {
            sb.append(" AND (concat(ben.nombres,concat(' ',concat(ben.apellidoPaterno,concat(' ',ben.apellidoMaterno)))) like ? OR ben.razonSocial like ?) ");
            String comodin = "";
            if (params.getNombreRazonSocial().trim().length() > 1) {
                comodin = "%";
            }
            paramsSQL.add(comodin + params.getNombreRazonSocial().trim().toUpperCase() + "%");
            tipos.add(new StringType());
            paramsSQL.add(comodin + params.getNombreRazonSocial().trim().toUpperCase() + "%");
            tipos.add(new StringType());
        }

        if (StringUtils.isNotBlank(params.getLetra())) {
            sb.append(" AND ((ben.nombres is not null AND ben.nombres like ?) OR (ben.razonSocial is not null AND ben.razonSocial like ?)) ");
            String comodin = "";
            if (params.getLetra().trim().length() > 1) {
                comodin = "%";
            }
            paramsSQL.add(comodin + params.getLetra().trim().toUpperCase() + "%");
            tipos.add(new StringType());
            paramsSQL.add(comodin + params.getLetra().trim().toUpperCase() + "%");
            tipos.add(new StringType());
        }

        if (params.isOrdernar()) {
            sb.append(" ORDER BY ben.idBeneficiario,tb.descTipoBeneficiario, ben.tipoFormato ");
        }

        List<Beneficiario> retorno =
                (List<Beneficiario>) this.getHibernateTemplate().execute(new HibernateCallback() {

                    public Object doInHibernate(final Session session) throws HibernateException,
                            SQLException {
                        Query query = session.createQuery(sb.toString());
                        query.setParameters(paramsSQL.toArray(new Object[0]),
                                tipos.toArray(new Type[0]));
                        if (registrosPorPagina.intValue() != PaginaVO.TODOS.intValue()) {
                            query.setFirstResult(offset.intValue());
                            query.setMaxResults(registrosPorPagina);
                        }
                        return query.list();
                    }
                });

        return retorno;
    }

    private List<ConsultaBeneficiarioVO> resultadosConsultaBeneficiariosPorNombre(
            final ConsultaNombreBeneficiarioParam params) {

        final StringBuilder sb = new StringBuilder();
        final List<Object> paramsSQL = new ArrayList<Object>();
        final List<Type> tipos = new ArrayList<Type>();

        sb.append(" SELECT  new " + ConsultaNombreBeneficiarioVO.class.getName() + " ( ");
        sb.append(" ben.idBeneficiario, ben.nombres, ben.apellidoPaterno, ben.apellidoMaterno, ");
        sb.append(" ben.razonSocial, ben.personaFisica, ben.tipoFormato, ben.statusBenef.descStatusBenef, ");
        sb.append(" ben.formatoW8BEN.foreignTaxIdNumb, ben.formatoW8IMY.foreignTaxIdNumb, ");
        sb.append(" ben.idCuentaNombrada, ben.tipoBeneficiario.descTipoBeneficiario ) ");
        sb.append(" FROM " + Beneficiario.class.getName() + " ben ");
        sb.append(" 	LEFT OUTER JOIN ben.formatoW8BEN w8ben ");
        sb.append(" 	LEFT OUTER JOIN ben.formatoW8IMY w8imy ");
        sb.append(" 	LEFT OUTER JOIN ben.formatoW9 w9 ");
        sb.append(" 	LEFT OUTER JOIN ben.formatoMILA mila ");

        sb.append(this.costruyeParamsByName(paramsSQL, tipos, params));

        sb.append(" ORDER BY ben.nombres, ben.apellidoPaterno, ben.apellidoMaterno, ben.razonSocial ");

        List<ConsultaBeneficiarioVO> retorno =
                (List<ConsultaBeneficiarioVO>) this.getHibernateTemplate().execute(
                        new HibernateCallback() {

                            public Object doInHibernate(final Session session)
                                    throws HibernateException, SQLException {
                                Query query = session.createQuery(sb.toString());
                                if (params.getListaBeneficiarios() != null
                                        && !params.getListaBeneficiarios().isEmpty()) {
                                    query.setParameterList("lista_benef",
                                            params.getListaBeneficiarios(), new LongType());
                                }
                                query.setParameters(paramsSQL.toArray(new Object[0]),
                                        tipos.toArray(new Type[0]));
                                return query.list();
                            }
                        });

        log.info("Tamanio lista : [" + retorno.size() + "]");

        return retorno;
    }

    private String costruyeParams(final List<Object> paramsSQL, final List<Type> tipos,
            final ConsultaBeneficiariosParam params, final Boolean isPopup, final Boolean onlyAuthorized) {
        StringBuilder sb = new StringBuilder();

        sb.append(" WHERE ben.eliminado = false ");

        if (params.getCustodio() != null && params.getCustodio().compareTo(CERO_LONG) > 0) {
            sb.append("	AND ben.idCuentaNombrada = ? ");
            paramsSQL.add(params.getCustodio());
            tipos.add(new LongType());
        }

        if (params.getInstitucion() != null && params.getInstitucion().getIdInstitucion() != null
                && params.getInstitucion().getIdInstitucion().compareTo(CERO_LONG) > 0) {
            sb.append("	AND i.idInstitucion = ? ");
            paramsSQL.add(params.getInstitucion().getIdInstitucion());
            tipos.add(new LongType());
        }

        if (params.getTipoBeneficiario() != null
                && params.getTipoBeneficiario().compareTo(CERO_LONG) > 0) {
            sb.append("	AND ben.tipoBeneficiario.idTipoBeneficiario = ? ");
            paramsSQL.add(params.getTipoBeneficiario());
            tipos.add(new LongType());
        }

        /** Se agrega bloque para consulta de Popup **/
        if(isPopup){
    		/** Se busca solo por estatus: AUTORIZADO, VENCIDO, CANCELADO **/
    		if (params.getStatusBenef() != null && params.getStatusBenef().compareTo(CERO_LONG) > 0) {
    			if(params.getStatusBenef() == STATUS_BENEFICIARIO_AUTORIZADO 
    					|| params.getStatusBenef() == STATUS_BENEFICIARIO_VENCIDO 
    					|| params.getStatusBenef() == STATUS_BENEFICIARIO_CANCELADO){
                    sb.append("	AND ben.statusBenef.idStatusBenef = ? ");
                    paramsSQL.add(params.getStatusBenef());
                    tipos.add(new LongType());
    			} else {
                    sb.append("	AND ben.statusBenef.idStatusBenef = ? ");
                    paramsSQL.add(STATUS_BENEFICIARIO_NO_EXISTE);
                    tipos.add(new LongType());
    			}
        	} else {
				if (params.getStatusBenef() != null && params.getStatusBenef() == UNO_MENOS_LONG && onlyAuthorized) {
	    			sb.append("	AND ben.statusBenef.idStatusBenef IN (2) ");
				} else if (params.getStatusBenef() != null && params.getStatusBenef() == UNO_MENOS_LONG && !onlyAuthorized) {
					sb.append("	AND ben.statusBenef.idStatusBenef IN (3,5) ");
				}
    		}
        } else {
            if (params.getStatusBenef() != null && params.getStatusBenef().compareTo(CERO_LONG) > 0) {
                sb.append("	AND ben.statusBenef.idStatusBenef = ? ");
                paramsSQL.add(params.getStatusBenef());
                tipos.add(new LongType());
            }
        }


        if (StringUtils.isNotBlank(params.getFormato()) && !params.getFormato().equals("-1")) {
            if (params.getFormato().equalsIgnoreCase("W8BEN")) {
                sb.append("	AND ben.tipoFormato in (?,?,?) ");
                paramsSQL.add(params.getFormato().trim().toUpperCase());
                tipos.add(new StringType());
                paramsSQL.add("W8BEN2014");
                tipos.add(new StringType());
                paramsSQL.add("W8BEN2017");
                tipos.add(new StringType());
                /** Se agrega criterio de busqueda por RFC **/
                if(StringUtils.isNotBlank(params.getRfc()) && params.getRfc() != null){
                	sb.append("	AND forW8ben.foreignTaxIdNumb = ? ");
                	paramsSQL.add(params.getRfc());
                	tipos.add(new StringType());
                }
                
                
            } else if (BeneficiariosConstantes.FORMATO_W8_IMY.equals(params.getFormato())) {
                sb.append("	AND ben.tipoFormato in (?,?,?) ");
                paramsSQL.add(params.getFormato().trim().toUpperCase());
                tipos.add(new StringType());
                paramsSQL.add(BeneficiariosConstantes.FORMATO_W8_IMY2015);
                tipos.add(new StringType());
                paramsSQL.add(BeneficiariosConstantes.FORMATO_W8_IMY2017);
                tipos.add(new StringType());
            } else if (BeneficiariosConstantes.FORMATO_W8_BEN_E.equals(params.getFormato())) {
                sb.append(" AND ben.tipoFormato in (?,?) ");
                paramsSQL.add(params.getFormato().trim().toUpperCase());
                tipos.add(new StringType());
                paramsSQL.add(BeneficiariosConstantes.FORMATO_W8_BEN_E_2016);
                tipos.add(new StringType());
                /** Se agrega criterio de busqueda por RFC **/
                if(StringUtils.isNotBlank(params.getRfc()) && params.getRfc() != null){
                	sb.append("	AND ben.formatoW8BENE.foreingTIN = ? ");
                	paramsSQL.add(params.getRfc());
                	tipos.add(new StringType());
                }
                
            } else {
                sb.append("	AND ben.tipoFormato = ? ");
                paramsSQL.add(params.getFormato().trim().toUpperCase());
                tipos.add(new StringType());
            }
        }

        if (params.getFechaRegistroInicio() != null) {
            sb.append("	AND ben.fechaRegistro >= ? ");
            log.debug("Fecha REgistro Final: ["
                    + DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaRegistroInicio(),
                            true) + "]");
            paramsSQL.add(DateUtils.preparaFechaConExtremoEnSegundos(
                    params.getFechaRegistroInicio(), true));
            tipos.add(new TimestampType());
        }

        if (params.getFechaRegistroFin() != null) {
            sb.append("	AND ben.fechaRegistro <= ? ");
            log.debug("Fecha REgistro Final: ["
                    + DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaRegistroFin(),
                            false) + "]");
            paramsSQL.add(DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaRegistroFin(),
                    false));
            tipos.add(new TimestampType());
        }

        if (params.getFechaFormatoInicio() != null) {
            sb.append("	AND ben.fechaFormato >= ? ");
            paramsSQL.add(DateUtils.preparaFechaConExtremoEnSegundos(
                    params.getFechaFormatoInicio(), true));
            tipos.add(new TimestampType());
        }

        if (params.getFechaFormatoFin() != null) {
            sb.append("	AND ben.fechaFormato <= ? ");
            paramsSQL.add(DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaFormatoFin(),
                    false));
            tipos.add(new TimestampType());
        }

        if (StringUtils.isNotBlank(params.getUoiNumber())) {
            sb.append("	AND ben.uoiNumber = ? ");
            paramsSQL.add(params.getUoiNumber().trim().toUpperCase());
            tipos.add(new StringType());
        }

        if (StringUtils.isNotBlank(params.getReferenceNumber())) {
            sb.append("	AND (forW8ben.referenceNumbers LIKE ? ");
            paramsSQL.add(params.getReferenceNumber().trim().toUpperCase() + "%");
            tipos.add(new StringType());
            sb.append("	OR forW8imy.referenceNumbers LIKE ? ");
            paramsSQL.add(params.getReferenceNumber().trim().toUpperCase() + "%");
            tipos.add(new StringType());
            sb.append("	OR formatoMILA.referencia LIKE ? ");
            paramsSQL.add(params.getReferenceNumber().trim().toUpperCase() + "%");
            tipos.add(new StringType());
            sb.append("     OR '1'=(select '1' from ben.formatoW8BENE forW8bene where forW8bene.referenceNumber LIKE ?) ");
            paramsSQL.add(params.getReferenceNumber().trim().toUpperCase() + "%");
            tipos.add(new StringType());
            sb.append("	OR '1'=(select '1' from ben.formatoW8IMY2015 forW8imy where forW8imy.referenceNumber LIKE ?) ) ");
            paramsSQL.add(params.getReferenceNumber().trim().toUpperCase() + "%");
            tipos.add(new StringType());
        }

        if (params.getFechaAutorizacionInicio() != null) {
            sb.append("	AND ben.fechaAutorizacion >= ? ");
            paramsSQL.add(DateUtils.preparaFechaConExtremoEnSegundos(
                    params.getFechaAutorizacionInicio(), true));
            tipos.add(new TimestampType());
        }

        if (params.getFechaAutorizacionFin() != null) {
            sb.append("	AND ben.fechaAutorizacion <= ? ");
            paramsSQL.add(DateUtils.preparaFechaConExtremoEnSegundos(
                    params.getFechaAutorizacionFin(), false));
            tipos.add(new TimestampType());
        }

        if (StringUtils.isNotBlank(params.getAdp())) {
            sb.append("	AND ben.adp = ? ");
            paramsSQL.add(params.getAdp().trim().toUpperCase());
            tipos.add(new StringType());
        }

        if (StringUtils.isNotBlank(params.getNombreRazonSocial())) {
            sb.append(" AND (concat(ben.nombres,concat(' ',concat(ben.apellidoPaterno,concat(' ',ben.apellidoMaterno)))) like ? OR ben.razonSocial like ?) ");
            String comodin = "";
            if (params.getNombreRazonSocial().trim().length() > 1) {
                comodin = "%";
            }
            paramsSQL.add(comodin + params.getNombreRazonSocial().trim().toUpperCase() + "%");
            tipos.add(new StringType());
            paramsSQL.add(comodin + params.getNombreRazonSocial().trim().toUpperCase() + "%");
            tipos.add(new StringType());
        }

        if (StringUtils.isNotBlank(params.getLetra())) {
            sb.append(" AND ((ben.nombres is not null AND ben.nombres like ?) OR (ben.razonSocial is not null AND ben.razonSocial like ?)) ");
            String comodin = "";
            if (params.getLetra().trim().length() > 1) {
                comodin = "%";
            }
            paramsSQL.add(comodin + params.getLetra().trim().toUpperCase() + "%");
            tipos.add(new StringType());
            paramsSQL.add(comodin + params.getLetra().trim().toUpperCase() + "%");
            tipos.add(new StringType());
        }

        if (params.getActivo() != null) {
            sb.append(" AND ben.activo = ? ");
            paramsSQL.add(params.getActivo());
            tipos.add(new BooleanType());
        }


        return sb.toString();
    }

    private String costruyeParamsByName(final List<Object> paramsSQL, final List<Type> tipos,
            final ConsultaNombreBeneficiarioParam params) {
        StringBuilder sb = new StringBuilder();

        sb.append(" WHERE ben.statusBenef.idStatusBenef IN (?,?) ");
        paramsSQL.add(STATUS_BENEFICIARIO_AUTORIZADO);
        tipos.add(new LongType());
        paramsSQL.add(STATUS_BENEFICIARIO_VENCIDO);
        tipos.add(new LongType());
        sb.append("		AND ben.eliminado = false ");
        sb.append(" 	AND ben.tipoBeneficiario.idTipoBeneficiario = ? ");
        paramsSQL.add(params.getIdTipoBeneficiario());
        tipos.add(new LongType());
        sb.append(" 	AND ben.idCuentaNombrada = ? ");
        paramsSQL.add(params.getCustodio());
        tipos.add(new LongType());

        sb.append("	AND ( 1=0 ");
        //
        // if( StringUtils.isNotBlank(params.getNombres()) ||
        // StringUtils.isNotBlank(params.getApellidoPaterno()) ||
        // StringUtils.isNotBlank(params.getApellidoMaterno()) ) {
        // sb.append( "	OR ( 1=1 " );
        //
        // if (StringUtils.isNotBlank(params.getNombres())) {
        // sb.append( "	AND ben.nombres LIKE ? " );
        // paramsSQL.add(params.getNombres().trim().toUpperCase()+"%");
        // tipos.add(new StringType());
        // }
        //
        // if (StringUtils.isNotBlank(params.getApellidoPaterno())) {
        // sb.append( "	AND ben.apellidoPaterno LIKE ? " );
        // paramsSQL.add(params.getApellidoPaterno().trim().toUpperCase()+"%");
        // tipos.add(new StringType());
        // }
        //
        // if (StringUtils.isNotBlank(params.getApellidoMaterno())) {
        // sb.append( "	AND ben.apellidoMaterno LIKE ? " );
        // paramsSQL.add(params.getApellidoMaterno().trim().toUpperCase()+"%");
        // tipos.add(new StringType());
        // }
        //
        // sb.append( "	) " );
        // }
        //
        //
        // if (StringUtils.isNotBlank(params.getRazonSocial())) {
        // sb.append( "		OR ben.razonSocial LIKE ? " );
        // paramsSQL.add(params.getRazonSocial().trim().toUpperCase()+"%");
        // tipos.add(new StringType());
        // }
        //

        if (StringUtils.isNotBlank(params.getRFC())) {
            sb.append("		OR ben.formatoW8BEN.foreignTaxIdNumb LIKE ? ");
            paramsSQL.add(params.getRFC().trim().toUpperCase() + "%");
            tipos.add(new StringType());
            sb.append("		OR ben.formatoW8IMY.foreignTaxIdNumb LIKE ? ");
            paramsSQL.add(params.getRFC().trim().toUpperCase() + "%");
            tipos.add(new StringType());
            sb.append("		OR ben.formatoMILA.referencia LIKE ? ");
            paramsSQL.add(params.getRFC().trim().toUpperCase() + "%");
            tipos.add(new StringType());
        }

        if (params.getListaBeneficiarios() != null && !params.getListaBeneficiarios().isEmpty()) {
            sb.append("         OR ben.idBeneficiario IN (:lista_benef) ");
        }

        sb.append("	) ");

        return sb.toString();
    }

    public List<NombreBeneficiario> getNombresBeneficiarios(
            final ConsultaNombreBeneficiarioParam param) {
        StringBuilder sb = new StringBuilder();
        List<Object> paramsSQL = new ArrayList<Object>();
        sb.append(" SELECT  new " + NombreBeneficiario.class.getName() + " ( ben.idBeneficiario, ");
        sb.append(" ben.nombres, ben.apellidoPaterno, ben.apellidoMaterno, ben.razonSocial, ben.personaFisica) ");
        sb.append(" FROM " + Beneficiario.class.getName() + " ben ");
        sb.append(" WHERE ben.statusBenef.idStatusBenef IN (?,?) ");
        paramsSQL.add(STATUS_BENEFICIARIO_AUTORIZADO);
        paramsSQL.add(STATUS_BENEFICIARIO_VENCIDO);
        sb.append("		AND ben.eliminado = false ");
        sb.append(" 	AND ben.tipoBeneficiario.idTipoBeneficiario = ? ");
        paramsSQL.add(param.getIdTipoBeneficiario());
        sb.append(" 	AND ben.idCuentaNombrada = ? ");
        paramsSQL.add(param.getCustodio());
        return this.getHibernateTemplate().find(sb.toString(), paramsSQL.toArray(new Object[] {}));
    }

    public List<NombreBeneficiario> getNombresBeneficiarios(final ConsultaBeneficiariosParam param) {
        StringBuilder sb = new StringBuilder();
        List<Object> paramsSQL = new ArrayList<Object>();
        sb.append(" SELECT  new " + NombreBeneficiario.class.getName() + " ( ben.idBeneficiario, ");
        sb.append(" ben.nombres, ben.apellidoPaterno, ben.apellidoMaterno, ben.razonSocial, ben.personaFisica) ");
        sb.append(" FROM " + Beneficiario.class.getName() + " ben ");
        // sb.append(" WHERE ben.eliminado = false ");
        sb.append(" WHERE ben.idBeneficiario is not null  ");
        return this.getHibernateTemplate().find(sb.toString(), paramsSQL.toArray(new Object[] {}));
    }

    public Long numeroMaximoRegistros() {
        return this.jdbcTemplate.queryForLong("select MAX(ID_BENEFICIARIO) from T_BENEFICIARIOS");
    }

    public BeneficiarioInstitucion getBeneficiarioInstitucion(final Long idBeneficiario,
            final Long idInstitucion) {
        BeneficiarioInstitucion retorno = null;

        StringBuilder sb = new StringBuilder();
        List<Object> paramsSQL = new ArrayList<Object>();

        sb.append(" FROM " + BeneficiarioInstitucion.class.getName() + " bi ");
        sb.append(" WHERE bi.institucion = ? ");
        paramsSQL.add(idInstitucion);
        sb.append("		AND bi.beneficiario = ? ");
        paramsSQL.add(idBeneficiario);

        List<BeneficiarioInstitucion> lista =
                this.getHibernateTemplate().find(sb.toString(), paramsSQL.toArray(new Object[] {}));

        if (lista != null && lista.size() == 1) {
            retorno = lista.get(0);
        }

        return retorno;
    }

    /**
     * @param jdbcTemplate the jdbcTemplate to set
     */
    public void setJdbcTemplate(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public Beneficiario getBeneficiarioByUoiInstitucion(final String uoi,
            final Long estatusBeneficiario, final Long idTipoInstitucion,
            final String folioInstitucion) {

        Beneficiario beneficiario = null;
        final StringBuilder sb = new StringBuilder();
        List<Object> paramsSQL = new ArrayList<Object>();

        sb.append(" SELECT  B ");
        sb.append(" FROM " + Beneficiario.class.getName() + " B ");
        sb.append("			JOIN FETCH B.statusBenef E ");
        if (idTipoInstitucion != null && StringUtils.isNotBlank(folioInstitucion)) {
            sb.append("			JOIN FETCH B.institucion I ");
        }
        sb.append(" WHERE B.uoiNumber like ? ");
        paramsSQL.add(uoi);
        sb.append("		  AND B.eliminado = false ");
        if (estatusBeneficiario != null) {
            sb.append(" 	  AND E.idStatusBenef = ? ");
            paramsSQL.add(estatusBeneficiario);
        }
        if (idTipoInstitucion != null && StringUtils.isNotBlank(folioInstitucion)) {
            sb.append("		  AND I.tipoInstitucion.idTipoInstitucion = ? ");
            paramsSQL.add(idTipoInstitucion);
            sb.append("		  AND I.folioInstitucion = ?");
            paramsSQL.add(folioInstitucion);
        }


        List<Beneficiario> lstBeneficiarios =
                this.getHibernateTemplate().find(sb.toString(), paramsSQL.toArray(new Object[] {}));
        if (lstBeneficiarios != null && !lstBeneficiarios.isEmpty()) {
            beneficiario = lstBeneficiarios.get(0);
            beneficiario.getTipoBeneficiario().getIdTipoBeneficiario();
            if (beneficiario.getTipoFormato().equalsIgnoreCase("W8BEN")
                    || beneficiario.getTipoFormato().equalsIgnoreCase("W8BEN2014")
                    || beneficiario.getTipoFormato().equalsIgnoreCase("W8BEN2017")) {
                if (beneficiario.getDomicilioW8Normal() != null) {
                    beneficiario.getDomicilioW8Normal().getCountry();
                }
                beneficiario.getFormatoW8BEN().getForeignTaxIdNumb();
            } else if (beneficiario.getTipoFormato().equalsIgnoreCase("W8IMY")) {
                if (beneficiario.getDomicilioW8Correo() != null) {
                    beneficiario.getDomicilioW8Correo().getCountry();
                }
                beneficiario.getFormatoW8IMY().getForeignTaxIdNumb();
            } else if (beneficiario.getTipoFormato().equalsIgnoreCase("W9")) {
                if (beneficiario.getDomicilioW9() != null) {
                    beneficiario.getDomicilioW9().getCountry();
                }
                beneficiario.getFormatoW9().getTaxClassification();
            }
        }

        return beneficiario;
    }

    /*
     * (non-Javadoc)
     * @see com.indeval.portalinternacional.persistence.dao.BeneficiarioDao#depurarFormatosFiscalesBeneficiarios(java.lang.Integer, java.util.List)
     */
    public Integer depurarFormatosFiscalesBeneficiarios(final Integer anioDepuracion, final List<String> tiposFormato) {
        Integer registrosEliminados = null;
        final StringBuilder sb = new StringBuilder();
        sb.append(" update  " + Beneficiario.class.getName() + " ben ");
        sb.append(" set    ben.statusBenef.idStatusBenef = :estadoVencidoSelect ");
        sb.append(" where  ben.statusBenef.idStatusBenef = :estadoAutorizado ");
        sb.append(" 	   and ben.tipoFormato in (:tiposFormato) ");
        sb.append(" 	   and ben.eliminado = :estadoEliminado ");
        sb.append(" 	   and to_number(to_char(ben.fechaFormato,'YYYY')) <= :anioDepuracion ");

        registrosEliminados = (Integer) this.getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(final Session session) throws HibernateException,
            SQLException {
                Query query = session.createQuery(sb.toString());
                query.setLong("estadoVencidoSelect", Long.valueOf(Constantes.STATUS_BENEFICIARIO_VENCIDO));
                query.setLong("estadoAutorizado", Long.valueOf(Constantes.STATUS_BENEFICIARIO_AUTORIZADO));
                query.setParameterList("tiposFormato", tiposFormato.toArray());
                query.setBoolean("estadoEliminado", Boolean.FALSE);
                query.setInteger("anioDepuracion", anioDepuracion);
                return query.executeUpdate();
            }
        });
        return registrosEliminados;
    }

    public List<Beneficiario> getBeneficiariosDepuracion(final Integer anioDepuracion, final List<String> tiposFormato) {
        List<Beneficiario> beneficiarios = null;

        final StringBuilder sb = new StringBuilder();
        sb.append(" select ben ");
        sb.append(" from " + Beneficiario.class.getName() + " ben ");
        sb.append(" 	JOIN FETCH ben.statusBenef stb ");
        sb.append(" where  stb.idStatusBenef = :estadoAutorizado ");
        sb.append(" 	   and ben.tipoFormato in (:tiposFormato) ");
        sb.append(" 	   and ben.eliminado = :estadoEliminado ");
        sb.append(" 	   and to_number(to_char(ben.fechaFormato,'YYYY')) <= :anioDepuracion ");

        beneficiarios = (List<Beneficiario>) this.getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(final Session session) throws HibernateException,
            SQLException {
                Query query = session.createQuery(sb.toString());
                query.setLong("estadoAutorizado", Long.valueOf(Constantes.STATUS_BENEFICIARIO_AUTORIZADO));
                query.setParameterList("tiposFormato", tiposFormato.toArray());
                query.setBoolean("estadoEliminado", Boolean.FALSE);
                query.setInteger("anioDepuracion", anioDepuracion);
                return query.list();
            }
        });

        return beneficiarios;
    }


    /**
     * @see com.indeval.portalinternacional.persistence.dao.BeneficiarioDao#consultaBeneficiarioByUoiForAdp(String)
     */
    public Beneficiario consultaBeneficiarioByUoiForAdp(final String uoi) {

        final StringBuilder sb = new StringBuilder();
        if (uoi == null) {
            return null;
        }
        sb.append(" FROM " + Beneficiario.class.getName() + " ben ");
        sb.append(" 	JOIN FETCH ben.tipoBeneficiario tb ");
        sb.append(" 	JOIN FETCH ben.statusBenef stb ");
        sb.append(" WHERE ben.uoiNumber = :uoi ");
        sb.append("	AND ben.eliminado = false ");
        @SuppressWarnings("unchecked")
        Beneficiario beneficiarioRetorno =
                (Beneficiario) this.getHibernateTemplate().execute(new HibernateCallback() {
                    public Object doInHibernate(final Session session) throws HibernateException,
                            SQLException {
                        Query query = session.createQuery(sb.toString());
                        query.setString("uoi", uoi);
                        return query.uniqueResult();
                    }
                });
        return beneficiarioRetorno;
    }

    /**
     * @see com.indeval.portalinternacional.persistence.dao.BeneficiarioDao#consultaBeneficiarioByUoiForInstituciones(String)
     */
    public Beneficiario consultaBeneficiarioByUoiForInstituciones(final String uoi) {

        final StringBuilder sb = new StringBuilder();
        if (uoi == null) {
            return null;
        }
        sb.append(" FROM " + Beneficiario.class.getName() + " ben ");
        sb.append(" 	JOIN FETCH ben.tipoBeneficiario tb ");
        sb.append(" 	JOIN FETCH ben.statusBenef stb ");
        sb.append(" 	LEFT JOIN FETCH ben.institucion i ");
        sb.append(" 	LEFT JOIN FETCH i.tipoInstitucion ti ");
        sb.append(" WHERE ben.uoiNumber = :uoi ");
        sb.append("	AND ben.eliminado = false ");
        @SuppressWarnings("unchecked")
        Beneficiario beneficiarioRetorno =
                (Beneficiario) this.getHibernateTemplate().execute(new HibernateCallback() {
                    public Object doInHibernate(final Session session) throws HibernateException,
                            SQLException {
                        Query query = session.createQuery(sb.toString());
                        query.setString("uoi", uoi);
                        return query.uniqueResult();
                    }
                });
        return beneficiarioRetorno;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.indeval.portalinternacional.persistence.dao.BeneficiarioDao#consultaCatExemptPayeeW9()
     */
    public List<ExemptPayeeW9> consultaCatExemptPayeeW9() {
        List<ExemptPayeeW9> list =
                (List<ExemptPayeeW9>) this.getHibernateTemplate().execute(new HibernateCallback() {
                    public List<ExemptPayeeW9> doInHibernate(final Session session)
                            throws HibernateException, SQLException {

                        Criteria criteria = session.createCriteria(ExemptPayeeW9.class);
                        criteria.addOrder(Order.asc("idExemptPayeeW9"));

                        return criteria.list();
                    }
                });

        return list;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.indeval.portalinternacional.persistence.dao.BeneficiarioDao#consultaCatExemptionFatcaW9()
     */
    public List<ExemptionFatcaW9> consultaCatExemptionFatcaW9() {
        List<ExemptionFatcaW9> list =
                (List<ExemptionFatcaW9>) this.getHibernateTemplate().execute(
                        new HibernateCallback() {
                            public List<ExemptionFatcaW9> doInHibernate(final Session session)
                                    throws HibernateException, SQLException {

                                Criteria criteria = session.createCriteria(ExemptionFatcaW9.class);
                                criteria.addOrder(Order.asc("idExemptionFatcaW9"));

                                return criteria.list();
                            }
                        });

        return list;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.indeval.portalinternacional.persistence.dao.BeneficiarioDao#getCatalogoExemptPayeeW9()
     */
    public Map<Integer, Long> getCatalogoExemptPayeeW9() {
        Map<Integer, Long> mapaExemptPayeeW9 = null;
        final StringBuilder sb = new StringBuilder();
        sb.append("SELECT DISTINCT payee.fatcaCode ,payee.idExemptPayeeW9 ");
        sb.append(" FROM " + ExemptPayeeW9.class.getName() + " payee ");
        sb.append(" ORDER BY payee.idExemptPayeeW9 ");
        @SuppressWarnings("unchecked")
        List<Object[]> retornoConsulta =
                (List<Object[]>) this.getHibernateTemplate().execute(new HibernateCallback() {
                    public Object doInHibernate(final Session session) throws HibernateException,
                            SQLException {
                        Query query = session.createQuery(sb.toString());
                        query.setCacheable(true);
                        query.setCacheRegion("com.indeval.portalinternacional.middleware.servicios.modelo.ExemptPayeeW9");
                        return query.list();
                    }
                });
        if (retornoConsulta != null && !retornoConsulta.isEmpty()) {
            mapaExemptPayeeW9 = new HashMap<Integer, Long>();
            for (Object[] exemptPayeeW9 : retornoConsulta) {
                mapaExemptPayeeW9.put(Integer.valueOf((String) exemptPayeeW9[0]),
                        (Long) exemptPayeeW9[1]);
            }
        }
        return mapaExemptPayeeW9;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.indeval.portalinternacional.persistence.dao.BeneficiarioDao#getCatalogoExemptionFatcaW9()
     */
    public Map<String, Long> getCatalogoExemptionFatcaW9() {
        Map<String, Long> mapaExemptionFatcaW9 = null;
        final StringBuilder sb = new StringBuilder();
        sb.append("SELECT DISTINCT fatcaCode.fatcaCode ,fatcaCode.idExemptionFatcaW9 ");
        sb.append(" FROM " + ExemptionFatcaW9.class.getName() + " fatcaCode ");
        sb.append(" ORDER BY fatcaCode.idExemptionFatcaW9 ");
        @SuppressWarnings("unchecked")
        List<Object[]> retornoConsulta =
                (List<Object[]>) this.getHibernateTemplate().execute(new HibernateCallback() {
                    public Object doInHibernate(final Session session) throws HibernateException,
                            SQLException {
                        Query query = session.createQuery(sb.toString());
                        query.setCacheable(true);
                        query.setCacheRegion("com.indeval.portalinternacional.middleware.servicios.modelo.ExemptionFatcaW9");
                        return query.list();
                    }
                });
        if (retornoConsulta != null && !retornoConsulta.isEmpty()) {
            mapaExemptionFatcaW9 = new HashMap<String, Long>();
            for (Object[] custodios : retornoConsulta) {
                mapaExemptionFatcaW9.put((String) custodios[0], (Long) custodios[1]);
            }
        }
        return mapaExemptionFatcaW9;
    }

    public List<MILASectorEconomico> consultaCatMilaSectorEconomico() {
        List<MILASectorEconomico> list =
                (List<MILASectorEconomico>) this.getHibernateTemplate().execute(
                        new HibernateCallback() {
                            public List<MILASectorEconomico> doInHibernate(final Session session)
                                    throws HibernateException, SQLException {
                                Criteria criteria =
                                        session.createCriteria(MILASectorEconomico.class);
                                criteria.addOrder(Order.asc("idSectorEconomico"));
                                return criteria.list();
                            }
                        });

        return list;
    }

    public List<MILATipoDocumento> consultaCatMilaTipoDocumento() {
        List<MILATipoDocumento> list =
                (List<MILATipoDocumento>) this.getHibernateTemplate().execute(
                        new HibernateCallback() {
                            public List<MILATipoDocumento> doInHibernate(final Session session)
                                    throws HibernateException, SQLException {
                                Criteria criteria = session.createCriteria(MILATipoDocumento.class);
                                criteria.addOrder(Order.asc("idTipoDocumento"));
                                return criteria.list();
                            }
                        });

        return list;
    }

    public List<MILATipoEmpresa> consultaCatMilaTipoEmpresa() {
        List<MILATipoEmpresa> list =
                (List<MILATipoEmpresa>) this.getHibernateTemplate().execute(
                        new HibernateCallback() {
                            public List<MILATipoEmpresa> doInHibernate(final Session session)
                                    throws HibernateException, SQLException {
                                Criteria criteria = session.createCriteria(MILATipoEmpresa.class);
                                criteria.addOrder(Order.asc("idTipoEmpresa"));
                                return criteria.list();
                            }
                        });

        return list;
    }

    public List<MILACodigoDepartamento> consultaCatMilaEstados() {
        List<MILACodigoDepartamento> list =
                (List<MILACodigoDepartamento>) this.getHibernateTemplate().execute(
                        new HibernateCallback() {
                            public List<MILACodigoDepartamento> doInHibernate(final Session session)
                                    throws HibernateException, SQLException {
                                Criteria criteria =
                                        session.createCriteria(MILACodigoDepartamento.class);
                                criteria.addOrder(Order.asc("nombre"));
                                return criteria.list();
                            }
                        });

        return list;
    }

}
