/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.persistence.dao.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.BigIntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portaldali.middleware.servicios.modelo.dto.EstatusEmisionesDTO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.EmisionVO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.dao.common.util.ValidatorUtil;
import com.indeval.portaldali.persistence.util.PageableCriteria;
import com.indeval.portaldali.persistence.util.constants.Constantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.CatBic;
import com.indeval.portalinternacional.middleware.servicios.modelo.SicEmision;
import com.indeval.portalinternacional.middleware.servicios.vo.SicEmisionVO;
import com.indeval.portalinternacional.persistence.dao.SicEmisionDao;

/**
 * @author javiles
 *
 */
@SuppressWarnings("unchecked")
public class SicEmisionDaoImpl extends BaseDaoHibernateImpl implements
        SicEmisionDao, Constantes {
    
    /** Log de clase. */
	private static final Logger log = LoggerFactory.getLogger(SicEmisionDaoImpl.class);

    /**
     * JdbcTemplate
     */
    private JdbcTemplate jdbcTemplate;

    /**
     * @see com.indeval.portalinternacional.persistence.dao.SicEmisionDao#findSicEmisionesByEmision(EmisionVO)
     */
    public List<SicEmision> findSicEmisionesByEmision(EmisionVO emisionVO) {
        
        log.info("Entrando a findSicEmision()");
        
        /* Valida datos de la emision */
        ValidatorUtil.validaEmisionVO(emisionVO);
        
        /* Realiza la consulta */
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SicEmision.class)
            .createAlias("cuentaNombrada", "cn")
            .createAlias("cn.institucion", "inst")
            .createAlias("inst.tipoInstitucion", "ti")
            .createAlias("emision", "e")
            .createAlias("e.instrumento", "i")
            .createAlias("e.emisora", "em");
        /* Busca emision por isin */
        if (StringUtils.isNotBlank(emisionVO.getIsin())) {
            detachedCriteria.add(Restrictions.eq("e.isin", emisionVO.getIsin()));
        }
        /* Busca emision por tv, emisora, serie, cupon */
        if (StringUtils.isNotBlank(emisionVO.getTv())) {
            detachedCriteria.add(Restrictions.eq("i.claveTipoValor", emisionVO.getTv()));
        }
        if (StringUtils.isNotBlank(emisionVO.getEmisora())) {
            detachedCriteria.add(Restrictions.eq("em.clavePizarra", emisionVO.getEmisora()));
        }
        if (StringUtils.isNotBlank(emisionVO.getSerie())) {
            detachedCriteria.add(Restrictions.eq("e.serie", emisionVO.getSerie()));
        }
        /* Solo se consultan registros con estatus "vigente" */
        detachedCriteria.add(Restrictions.eq("estatusRegistro",com.indeval.portalinternacional.middleware.servicios.constantes.Constantes.ESTATUS_REGISTRO_VIGENTE));
        detachedCriteria.addOrder(Order.asc("ti.claveTipoInstitucion"))
                .addOrder(Order.asc("inst.folioInstitucion"))
                .addOrder(Order.asc("cn.cuenta"))
                .addOrder(Order.asc("i.claveTipoValor"))
                .addOrder(Order.asc("em.clavePizarra"))
                .addOrder(Order.asc("e.serie"));
        
        return getHibernateTemplate().findByCriteria(detachedCriteria);
        
    }

    /**
     * @see com.indeval.portalinternacional.persistence.dao.SicEmisionDao#findSicEmisionesByAgente(com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO)
     */
    public List<SicEmision> findSicEmisionesByAgente(final AgenteVO agenteVO) {
    	
    	HibernateCallback hibernateCallback = new HibernateCallback() {
    		public Object doInHibernate(Session session) throws HibernateException, SQLException {
    			Criteria criteria = session.createCriteria(SicEmision.class);
    			criteria.createAlias("cuentaNombrada", "cn");
    			criteria.createAlias("cn.institucion", "i");
    			criteria.createAlias("i.tipoInstitucion", "ti");
    			
    			criteria.add(Restrictions.eq("ti.claveTipoInstitucion",agenteVO.getId()));
    			criteria.add(Restrictions.eq("i.folioInstitucion",agenteVO.getFolio()));
    			criteria.add(Restrictions.eq("cn.cuenta",agenteVO.getCuenta()));
    			return (criteria.list());
    		}
    	};
    	
    	return ((List<SicEmision>)this.getHibernateTemplate().execute(hibernateCallback));
    }

    /**
     * @see com.indeval.portalinternacional.persistence.dao.SicEmisionDao#findSicEmisionesByCustodio(com.indeval.portalinternacional.middleware.servicios.modelo.CatBic)
     */
    public List<SicEmision> findSicEmisionesByCustodio(final CatBic catBic, final Boolean todas) {
    	
    	HibernateCallback hibernateCallback = new HibernateCallback() {
    		public Object doInHibernate(Session session) throws HibernateException, SQLException {
    			Criteria criteria = session.createCriteria(SicEmision.class);
    			criteria.createAlias("catBic", "cb");
    			criteria.add(Restrictions.eq("cb.idCatbic",catBic.getIdCatbic()));
    			if ((todas != null) && (!todas.booleanValue())) {
    				criteria.add(Restrictions.eq("estatusRegistro",com.indeval.portalinternacional.middleware.servicios.constantes.Constantes.ESTATUS_REGISTRO_VIGENTE));
    			}
    			return (criteria.list());
    		}
    	};
    	return ((List<SicEmision>)this.getHibernateTemplate().executeFind(hibernateCallback));
    }
    
    /**
     * @see com.indeval.portalinternacional.persistence.dao.SicEmisionDao#findSicEmisionesByCustodio(com.indeval.portalinternacional.middleware.servicios.modelo.CatBic, com.indeval.portaldali.middleware.servicios.modelo.vo.EmisionVO, java.lang.String, com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO)
     */
    public PaginaVO findSicEmisionesByCustodio(final CatBic catBic, final EmisionVO emisionVO, final String formaDeOperar, final PaginaVO paginaVO) {
    		
    	HibernateCallback hibernateCallback = new HibernateCallback() {
    		public Object doInHibernate(Session session) throws HibernateException, SQLException {
    			PageableCriteria pageableCriteria = PageableCriteria.createPageableCriteria(session, SicEmision.class);
    			pageableCriteria.createAlias("catBic", "cb");
				pageableCriteria.createAlias("emision", "e");
    			pageableCriteria.add(Restrictions.eq("cb.idCatbic", catBic.getIdCatbic()));
    			if (StringUtils.isNotBlank(formaDeOperar)) {
    				pageableCriteria.add(Restrictions.eq("formaOper", formaDeOperar));
    			}
    			if (emisionVO != null) {
    				if (StringUtils.isNotBlank(emisionVO.getTv())) {
    					pageableCriteria.createAlias("e.instrumento", "ei");
    					pageableCriteria.add(Restrictions.eq("ei.claveTipoValor", emisionVO.getTv()));
    				}
    				if (StringUtils.isNotBlank(emisionVO.getEmisora())) {
    					pageableCriteria.createAlias("e.emisora", "ee");
    					pageableCriteria.add(Restrictions.eq("ee.clavePizarra", emisionVO.getEmisora()));
    				}
    				if (StringUtils.isNotBlank(emisionVO.getSerie())) {
    					pageableCriteria.add(Restrictions.eq("e.serie", emisionVO.getSerie()));
    				}
    			}
    			return (pageableCriteria.pageResult(paginaVO));
    		}
    	};
    	return ((PaginaVO)this.getHibernateTemplate().execute(hibernateCallback));
    }

    /** 
     * @see com.indeval.portalinternacional.persistence.dao.SicEmisionDao#findSicEmsionByPk(java.lang.Long)
     */
	public SicEmision findSicEmsionByPk(Long idSicEmision) {		
		return (SicEmision)getByPk(SicEmision.class, idSicEmision);
	}
	
	/**
     * @see com.indeval.portalinternacional.persistence.dao.SicEmisionDao#findSicEmisionesByEmision(EmisionVO)
     */
    public PaginaVO findSicEmisionesByEmisionAndCustodio(EstatusEmisionesDTO estatusEmisionesDTO, CatBic catBic, EmisionVO emisionVO, final PaginaVO paginaVO) {
        
        log.info("Entrando a findSicEmisionesByEmisionAndCustodio()");
        final StringBuffer sql = new StringBuffer();
        
        final ArrayList<Object> params = new ArrayList<Object>();
		final ArrayList<Type> tipos = new ArrayList<Type>();
        
        sql.append("FROM " + SicEmision.class.getName() + " s ");
        sql.append("join fetch s.cuentaNombrada ");
        sql.append("join fetch s.cuentaNombrada.institucion ");
        sql.append("join fetch s.cuentaNombrada.institucion.tipoInstitucion ");
        sql.append("join fetch s.emision ");
        sql.append("join fetch s.emision.instrumento ");
        sql.append("join fetch s.emision.emisora ");
        
        sql.append(construirCriterioEmision(estatusEmisionesDTO, catBic, emisionVO, params, tipos));
        sql.append(obtenerPrefijoCondicion(params.size()));
        sql.append(" s.estatusRegistro = ? ");
		params.add(com.indeval.portalinternacional.middleware.servicios.constantes.Constantes.ESTATUS_REGISTRO_VIGENTE);
		tipos.add(new StringType());
		
		sql.append(" ORDER BY s.emision.instrumento.claveTipoValor, s.emision.emisora.clavePizarra, s.emision.serie ");

		List<SicEmision> resultados = (List<SicEmision>) getHibernateTemplate().execute(new HibernateCallback(){
			/*
			 * (non-Javadoc)
			 * @see org.springframework.orm.hibernate3.HibernateCallback#doInHibernate(org.hibernate.Session)
			 */
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sql.toString());
				query.setParameters(params.toArray(), tipos.toArray(new Type[] {}));
				
				paginaVO.setRegistros(query.list());
		        paginaVO.setTotalRegistros(paginaVO.getRegistros().size());
				
				if (paginaVO.getRegistrosXPag() != PaginaVO.TODOS) {

					query.setFirstResult(paginaVO.getOffset());
					query.setMaxResults(paginaVO.getRegistrosXPag());
				}

				List<SicEmision> resultados = query.list();
				
				return resultados;
			}
		});
        
		
        paginaVO.setRegistros(resultados);
		
        return paginaVO;
    }
    
    /**
     * @see com.indeval.portalinternacional.persistence.dao.SicEmisionDao#findSicEmisionesByEmisionAndCustodioPosicionCero(EstatusEmisionesDTO,CatBic,EmisionVO,PaginaVO)
     */
    public PaginaVO findSicEmisionesByEmisionAndCustodioPosicionCero(EstatusEmisionesDTO estatusEmisionesDTO, CatBic catBic, EmisionVO emisionVO, final PaginaVO paginaVO) {
        log.info("####### Entrando a findSicEmisionesByEmisionAndCustodioPosicionCero()...");
        final StringBuffer sql = new StringBuffer();
        final ArrayList<Object> params = new ArrayList<Object>();
        final ArrayList<Type> tipos = new ArrayList<Type>();
        sql.append(" select distinct(cse.ID_SIC_EMISIONES), inst.CLAVE_TIPO_VALOR, emisora.CLAVE_PIZARRA, emision.SERIE ");
        sql.append(" from ");
        sql.append(" C_SIC_EMISIONES cse ");
        sql.append(" inner join C_EMISION emision on cse.ID_EMISION = emision.ID_EMISION ");
        sql.append(" inner join C_INSTRUMENTO inst on inst.ID_INSTRUMENTO = emision.ID_INSTRUMENTO ");
        sql.append(" inner join C_EMISORA emisora on emisora.ID_EMISORA = emision.ID_EMISORA ");
        sql.append(" where 1=1 ");
        // Filtro por TV
        if (StringUtils.isNotBlank(emisionVO.getTv())) {
            sql.append(" and inst.CLAVE_TIPO_VALOR = ? ");
            params.add(emisionVO.getTv().toUpperCase());
            tipos.add(new StringType());
        }
        // Filtro por Emisora
        if (StringUtils.isNotBlank(emisionVO.getEmisora())) {
            sql.append(" and emisora.CLAVE_PIZARRA = ? ");
            params.add(emisionVO.getEmisora().toUpperCase());
            tipos.add(new StringType());
        }
        // Filtro por Serie
        if (StringUtils.isNotBlank(emisionVO.getSerie())) {
            sql.append("and emision.SERIE = ? ");
            params.add(emisionVO.getSerie().toUpperCase());
            tipos.add(new StringType());
        }
        // Filtro por Isin
        if (StringUtils.isNotBlank(emisionVO.getIsin())) {
            // Consulta por identificador ISIN unico
            sql.append("and emision.ISIN =  ? ");
            params.add(emisionVO.getIsin().toUpperCase());
            tipos.add(new StringType());
        }
        // Filtro por estatus de emision
        if (estatusEmisionesDTO.getId() > -1) {
            sql.append("and emision.ID_ESTATUS_EMISION = ? ");
            params.add(BigInteger.valueOf(estatusEmisionesDTO.getId()));
            tipos.add(new BigIntegerType());
        }
        // Filtro por custodio
        if (catBic.getCuentaNombrada().getIdCuentaNombrada() > -1) {
            sql.append("and cse.ID_CUENTA_NOMBRADA = ? ");
            params.add(catBic.getCuentaNombrada().getIdCuentaNombrada());
            tipos.add(new LongType());
        }
        //Indica que el registro de c_sic_emision sea vigente
        sql.append(" and cse.ESTATUS_REGISTRO = ? ");
        params.add(com.indeval.portalinternacional.middleware.servicios.constantes.Constantes.ESTATUS_REGISTRO_VIGENTE);
        tipos.add(new StringType());
        //Subconsulta para filtrar aquellas emisiones que tengas posicion cero
        sql.append(" and not EXISTS ( ");
        sql.append(" select 1 ");
        sql.append(" from T_POSICION_NOMBRADA pn ");
        sql.append(" inner join C_CUENTA_NOMBRADA cn on cn.ID_CUENTA_NOMBRADA = pn.ID_CUENTA ");
        sql.append(" inner join C_TIPO_CUENTA tc on tc.ID_TIPO_CUENTA = cn.ID_TIPO_CUENTA and tc.NATURALEZA_CONTABLE = 'P' and tc.TIPO_CUSTODIA = 'V' and tc.NATURALEZA_PROC_LIQ = 'N' ");
        sql.append(" where pn.ID_EMISION = cse.ID_EMISION ");
        sql.append(" and (pn.POSICION_DISPONIBLE + pn.POSICION_NO_DISPONIBLE) > 0 ");
        sql.append(" ) ");
        //Ordena por TV, Emisora y Serie
        sql.append(" order by inst.CLAVE_TIPO_VALOR, emisora.CLAVE_PIZARRA, emision.SERIE ");
        List<SicEmision> resultados = (List<SicEmision>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                SQLQuery query = session.createSQLQuery(sql.toString());
                query.setParameters(params.toArray(), tipos.toArray(new Type[] {}));
                paginaVO.setRegistros(query.list());
                paginaVO.setTotalRegistros(paginaVO.getRegistros().size());
                if (paginaVO.getRegistrosXPag() != PaginaVO.TODOS) {
                    query.setFirstResult(paginaVO.getOffset());
                    query.setMaxResults(paginaVO.getRegistrosXPag());
                }
                List<SicEmision> resultados = convertirAListSicEmision(query.list());
                return resultados;
            }
        });

        paginaVO.setRegistros(resultados);

        return paginaVO;
    }

    /**
     * Crea un listado de objetos SicEmision a partir de la busqueda que retorna varios registros en un listado de Object[]. 
     * @param registros
     * @return El listado de objetos SicEmision. 
     */
    private List<SicEmision> convertirAListSicEmision(List<Object[]> registros) {
        List<SicEmision> resultados = new ArrayList<SicEmision>();
        SicEmision sicemision = null;
        for (Object[] registro : registros) {
            sicemision = new SicEmision();
            sicemision = this.findSicEmsionByPk(((BigDecimal) registro[0]).longValue());
            sicemision.setCuentaNombrada(sicemision.getCuentaNombrada());
            sicemision.setEmision(sicemision.getEmision());
            sicemision.setCatBic(sicemision.getCatBic());
            resultados.add(sicemision);
            sicemision = null;
        }
        return resultados;
    }
    
    /**
	 * Determina si antes de incluir un nuevo parametro se incluye una condicion
	 * WHERE o una condicion AND
	 * 
	 * @param size
	 * @return
	 */
	private Object obtenerPrefijoCondicion(int size) {
		if (size > 0) {
			return " AND ";
		}
		return " WHERE ";
	}
    
    private String construirCriterioEmision(EstatusEmisionesDTO estatusEmisionesDTO, CatBic catBic, EmisionVO criterio, ArrayList<Object> params, ArrayList<Type> tipos) {
		StringBuffer queryString = new StringBuffer();

		//isin
		if (criterio.getIsin() != null && criterio.getIsin().length() > 0) {
			// Consulta por identificador ISIN unico
			queryString.append(obtenerPrefijoCondicion(params.size()));
			queryString.append(" s.emision.isin = ? ");
			params.add(criterio.getIsin().toUpperCase());
			tipos.add(new StringType());

		}
		// serie
		if (criterio.getSerie() != null && StringUtils.isNotBlank(criterio.getSerie()) && !criterio.getSerie().equals("-1")) {

			queryString.append(obtenerPrefijoCondicion(params.size()));
			queryString.append(" s.emision.serie = ? ");
			params.add(criterio.getSerie().toUpperCase());
			tipos.add(new StringType());
		}

		// descripcion clave pizarra
		if (StringUtils.isNotEmpty(criterio.getEmisora())) {

			queryString.append(obtenerPrefijoCondicion(params.size()));
			queryString.append(" s.emision.emisora.clavePizarra = ? ");
			params.add(criterio.getEmisora().toUpperCase());
			tipos.add(new StringType());
		}
		
		// tipo valor
		if (StringUtils.isNotEmpty(criterio.getTv())) {

			queryString.append(obtenerPrefijoCondicion(params.size()));
			queryString.append(" s.emision.instrumento.claveTipoValor = ? ");
			params.add(criterio.getTv().toUpperCase());
			tipos.add(new StringType());
		}
		
		// custodio
		if (catBic.getCuentaNombrada().getIdCuentaNombrada() > -1) {

			queryString.append(obtenerPrefijoCondicion(params.size()));
			queryString.append(" s.catBic.cuentaNombrada.idCuentaNombrada = ? ");
			params.add(catBic.getCuentaNombrada().getIdCuentaNombrada());
			tipos.add(new LongType());
		}
		
		// estatus
		if (estatusEmisionesDTO.getId() > -1) {

			queryString.append(obtenerPrefijoCondicion(params.size()));
			queryString.append(" s.emision.idEstatusEmision = ? ");
			params.add(java.math.BigInteger.valueOf(estatusEmisionesDTO.getId()));
			tipos.add(new BigIntegerType());
		}

		return queryString.toString();
	}

	public List<SicEmision> findSicEmisionVigenteByIdEmision(final Long idCatBic, final Long idCuentaNombrada,
			final Long idEmision, final String estadoEmision, final Long idSicEmision) {
		HibernateCallback hibernateCallback = new HibernateCallback() {
    		public Object doInHibernate(Session session) throws HibernateException, SQLException {
    			Criteria criteria = session.createCriteria(SicEmision.class);
    			criteria.createAlias("catBic", "cb");
				criteria.createAlias("cuentaNombrada", "cn");
				criteria.createAlias("emision", "e");
    			criteria.add(Restrictions.eq("cb.idCatbic",idCatBic));
				criteria.add(Restrictions.eq("cn.idCuentaNombrada",idCuentaNombrada));
				criteria.add(Restrictions.eq("e.idEmision",idEmision));
				criteria.add(Restrictions.eq("estatusRegistro",estadoEmision));
				if(idSicEmision != null && idSicEmision > 0) {
					criteria.add(Restrictions.ne("idSicEmisiones",idSicEmision));
				}

    			return (criteria.list());
    		}
    	};
    	return ((List<SicEmision>)this.getHibernateTemplate().executeFind(hibernateCallback));
	}
	
	public List<SicEmision> findSicEmisionVigenteByIdEmision(final Long idCatBic, final Long idCuentaNombrada,
			final Long idEmision, final String estadoEmision, final Long idSicEmision, 
			final String formaOper, final String aplicacion) {
		HibernateCallback hibernateCallback = new HibernateCallback() {
    		public Object doInHibernate(Session session) throws HibernateException, SQLException {
    			Criteria criteria = session.createCriteria(SicEmision.class);
    			criteria.createAlias("catBic", "cb");
				criteria.createAlias("cuentaNombrada", "cn");
				criteria.createAlias("emision", "e");
    			criteria.add(Restrictions.eq("cb.idCatbic",idCatBic));
				criteria.add(Restrictions.eq("cn.idCuentaNombrada",idCuentaNombrada));
				criteria.add(Restrictions.eq("e.idEmision",idEmision));
                criteria.add(Restrictions.eq("estatusRegistro",estadoEmision));
				
				if(idSicEmision != null && idSicEmision > 0) {
					criteria.add(Restrictions.ne("idSicEmisiones",idSicEmision));
				}

    			return (criteria.list());
    		}
    	};
    	return ((List<SicEmision>)this.getHibernateTemplate().executeFind(hibernateCallback));
	}

	public SicEmision findSicEmisionByIdEmision(final Long idEmision) {
		List<SicEmision> lstEmisionesSic = null;
		SicEmision sicEmision = null;
    	HibernateCallback hibernateCallback = new HibernateCallback() {
    		public Object doInHibernate(Session session) throws HibernateException, SQLException {
    			Criteria criteria = session.createCriteria(SicEmision.class);
    			criteria.createAlias("emision", "e");
    			criteria.add(Restrictions.eq("e.idEmision",idEmision));   
    			criteria.add(Restrictions.eq("estatusRegistro",com.indeval.portalinternacional.middleware.servicios.constantes.Constantes.ESTATUS_REGISTRO_VIGENTE));
    			return (criteria.list());
    		}
    	};
    	lstEmisionesSic = (List<SicEmision>)this.getHibernateTemplate().executeFind(hibernateCallback);
    	if(lstEmisionesSic != null && !lstEmisionesSic.isEmpty()){
    		sicEmision = lstEmisionesSic.get(0);
    	}
    	
    	return sicEmision;
	}

	/**
	 * @see com.indeval.portalinternacional.persistence.dao.SicEmisionDao.findSicEmisionByIdEmisionWithoutEntities(Long)
	 */
	public SicEmisionVO findSicEmisionByIdEmisionWithoutEntities(final Long idEmision) {
	    log.info("\n\n####### Entrando a findSicEmisionByIdEmisionWithoutEntities()...\n\n");
	    StringBuilder sql = new StringBuilder();
        List<Object> paramsSQL = new ArrayList<Object>();
        List<Object> resultados = null;
        SicEmisionVO sicEmisionVO = null;
        List<SicEmisionVO> listadoSicEmisionVO = new ArrayList<SicEmisionVO>();

	    sql.append(" SELECT cse.ID_SIC_EMISIONES idsicemisiones, ");
	    sql.append(" cse.ID_CUENTA_NOMBRADA idcuentanombrada, ");
	    sql.append(" cse.ID_EMISION idemision, ");
	    sql.append(" cse.ESTATUS_REGISTRO estatusregistro ");
	    sql.append(" FROM C_SIC_EMISIONES cse ");
	    sql.append(" WHERE cse.ID_EMISION = ? ");
        sql.append(" AND cse.ESTATUS_REGISTRO = '" + com.indeval.portalinternacional.middleware.servicios.constantes.Constantes.ESTATUS_REGISTRO_VIGENTE + "' ");

        log.info("\n\n####### QUERY=[" + sql.toString() + "]\n\n");

        paramsSQL.add(idEmision);

        resultados = this.getJdbcTemplate().queryForList(sql.toString(), paramsSQL.toArray(new Object[0]));

        log.info("\n\n####### resultados=[" + resultados.size() + "]\n\n");
        if (resultados != null && !resultados.isEmpty()) {
            Iterator registrosIterador = resultados.iterator();
            while (registrosIterador.hasNext()) {
                ListOrderedMap entry = (ListOrderedMap) registrosIterador.next();
                sicEmisionVO = new SicEmisionVO();
                sicEmisionVO.setIdSicEmision(((BigDecimal) entry.get("idsicemisiones")).longValue());
                sicEmisionVO.setIdCuentaNombrada(((BigDecimal) entry.get("idcuentanombrada")).longValue());
                sicEmisionVO.setIdEmision(((BigDecimal) entry.get("idemision")).longValue());
                sicEmisionVO.setEstatusRegistro((String) entry.get("estatusregistro"));
                listadoSicEmisionVO.add(sicEmisionVO);
            }
        }

        if (listadoSicEmisionVO.size() > 0) {
            sicEmisionVO = listadoSicEmisionVO.get(0);
        }

        return sicEmisionVO;
	}

	/*
	 * (non-Javadoc)
	 * @see com.indeval.portalinternacional.persistence.dao.SicEmisionDao#findSicEmisionesByIdEmision(java.lang.Long)
	 */
	public List<SicEmision> findSicEmisionesByIdEmision(final Long idEmision) {
    	HibernateCallback hibernateCallback = new HibernateCallback() {
    		public Object doInHibernate(Session session) throws HibernateException, SQLException {
    			Criteria criteria = session.createCriteria(SicEmision.class);
    			criteria.createAlias("emision", "e");
    			criteria.add(Restrictions.eq("e.idEmision",idEmision));   
    			criteria.add(Restrictions.eq("estatusRegistro",com.indeval.portalinternacional.middleware.servicios.constantes.Constantes.ESTATUS_REGISTRO_VIGENTE));
    			return (criteria.list());
    		}
    	};
    	return (List<SicEmision>)this.getHibernateTemplate().executeFind(hibernateCallback);
	}

	public void guardaActualizaEmision(SicEmision sicEmision) {
		this.getHibernateTemplate().saveOrUpdate(sicEmision);
		
	}

	/**
	 * @see com.indeval.portalinternacional.persistence.dao.SicEmisionDao#findSicEmisionVigente(Long , Long, Long)
	 */
    public SicEmision findSicEmisionVigente(final Long idCuentaNombrada, final Long idEmision, final Long idCatbic) {
    	HibernateCallback sicEmisionVigente = new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(SicEmision.class);
	            criteria.createAlias("catBic", "cb");
	            criteria.createAlias("cuentaNombrada", "cn");
	            criteria.createAlias("emision", "e");
	            criteria.add(Restrictions.eq("cn.idCuentaNombrada", idCuentaNombrada));
	            criteria.add(Restrictions.eq("e.idEmision", idEmision));
	            criteria.add(Restrictions.eq("cb.idCatbic", idCatbic));
	            criteria.add(Restrictions.eq("estatusRegistro", 
	            	com.indeval.portalinternacional.middleware.servicios.constantes.Constantes.ESTATUS_REGISTRO_VIGENTE));
	            return criteria.uniqueResult();
			}
		};
        return (SicEmision) this.getHibernateTemplate().execute(sicEmisionVigente);
    }

    /*
     * @see com.indeval.portalinternacional.persistence.dao.SicEmisionDao#getPosicionTotalOperacionSicCambioBoveda(Long)
     */
    public Long getPosicionTotalOperacionSicCambioBoveda(Long idOperacionSic) {
        final StringBuffer sql = new StringBuffer();
        final List<Object> paramsSQL = new ArrayList<Object>();

        sql.append(" select POSICION_TOTAL ");
        sql.append(" from T_POSICIONES_CAMBIO_BOVEDA ");
        sql.append(" where ID_OPERACION_SIC_ENTREGA = ? ");

        paramsSQL.add(idOperacionSic);

        return this.getJdbcTemplate().queryForLong(sql.toString(), paramsSQL.toArray(new Object[0]));
    }

    /*
     * @see com.indeval.portalinternacional.persistence.dao.SicEmisionDao#getIdCuentaNombrada5001Indeval()
     */
    public Long getIdCuentaNombrada5001Indeval() {
        final StringBuffer sql = new StringBuffer();
        final String CUENTA_DE_INDEVAL = "5001";
        final Long ID_TIPO_INSTITUCION_INDEVAL = 12L;
        final String FOLIO_INSTITUCION_INDEVAL = "001";

        sql.append(" select ID_CUENTA_NOMBRADA ");
        sql.append(" from C_CUENTA_NOMBRADA cn, C_INSTITUCION i ");
        sql.append(" where cn.cuenta = '" + CUENTA_DE_INDEVAL + "' ");
        sql.append(" and cn.id_institucion = i.id_institucion ");
        sql.append(" and i.id_tipo_institucion = " + ID_TIPO_INSTITUCION_INDEVAL + " ");
        sql.append(" and i.folio_institucion = '" + FOLIO_INSTITUCION_INDEVAL + "' ");

        return this.getJdbcTemplate().queryForLong(sql.toString());
    }

    public Long getPosicionDisponibleCta5001Indeval(Long idCuentaNombrada5001Indeval, Long idBoveda, Long idEmision) {
        final StringBuffer sql = new StringBuffer();
        final List<Object> paramsSQL = new ArrayList<Object>();

        sql.append(" select POSICION_DISPONIBLE ");
        sql.append(" from T_POSICION_NOMBRADA ");
        sql.append(" where ID_CUENTA = ? ");
        paramsSQL.add(idCuentaNombrada5001Indeval);
        sql.append(" and ID_BOVEDA = ? ");
        paramsSQL.add(idBoveda);
        sql.append(" and ID_EMISION = ? ");
        paramsSQL.add(idEmision);

        return this.getJdbcTemplate().queryForLong(sql.toString(), paramsSQL.toArray(new Object[0]));
        
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

}
