
/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.persistence.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.*;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.LongType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO;
import com.indeval.portaldali.persistence.modelo.Emision;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.Bovedas;
import com.indeval.portalinternacional.middleware.servicios.modelo.CatBic;
import com.indeval.portalinternacional.middleware.servicios.modelo.Custodio;
import com.indeval.portalinternacional.middleware.servicios.modelo.RTipoValorCustodio;
import com.indeval.portalinternacional.middleware.servicios.modelo.SicEmision;
import com.indeval.portalinternacional.middleware.servicios.vo.CatBicVO;
import com.indeval.portalinternacional.persistence.dao.CatBicDao;

/**
 * @author javiles
 *
 */
@SuppressWarnings({"unchecked"})
public class CatBicDaoImpl extends BaseDaoHibernateImpl implements CatBicDao {

    /** Log de clase. */
	private static final Logger log = LoggerFactory.getLogger(CatBicDaoImpl.class);
    
    /**
     * @see com.indeval.portalinternacional.persistence.dao.CatBicDao#findCatBic(AgenteVO[])
     */
    public List<CatBic> findCatBic(AgenteVO[] agenteVO) {
        
        log.info("Entrando a CatBicDaoImpl.findCatBic(AgenteVO[])");
        
    	return (List<CatBic>) findCatBic(agenteVO, null, true);
        
    }

    /**
     * @see com.indeval.portalinternacional.persistence.dao.CatBicDao#findCustodios(AgenteVO[], String)
     */
    public Integer findCustodios(AgenteVO[] agenteVO, String custodio) {
        
        log.info("Entrando a CatBicDaoImpl.findCustodios(AgenteVO[])");

        if (StringUtils.isBlank(custodio)) {
            throw new IllegalArgumentException("Falta la descripcion del custodio");
        }
        
    	return (Integer) findCatBic(agenteVO, custodio, false);

    }
    
    /**
     * @see com.indeval.portalinternacional.persistence.dao.CatBicDao#findCatBic(com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO[], java.lang.String)
     */
    public List<CatBic> findCatBic(AgenteVO[] agenteVO, String custodio) {
    	
        if (StringUtils.isBlank(custodio)) {
            throw new IllegalArgumentException("Falta la descripcion del custodio");
        }
    	return (List<CatBic>) findCatBic(agenteVO, custodio, true);
    	
    }
    
    
 
    
    /**
     * @see com.indeval.portalinternacional.persistence.dao.CatBicDao#findCatBic(com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO[], java.lang.String)
     */
    public List<CatBic> findCatBic(Long[] idCatbics, String custodio) {
    	
        if (StringUtils.isBlank(custodio)) {
            throw new IllegalArgumentException("Falta la descripcion del custodio");
        }
    	
    	return (List<CatBic>) findCatBic(idCatbics, custodio, true);
    	
    }

    /**
     * @param agenteVO
     * @param custodio
     * @param recupera
     * @return Object
     */
    private Object findCatBic(final AgenteVO[] agenteVO, final String custodio, final boolean recupera) {

    	if(log.isDebugEnabled())
    		log.debug("Entrando a CatBicDaoImpl.findCatBic()");

    	/* Valida el arreglo de agentes */
    	if (agenteVO == null || agenteVO.length == 0) {
    		throw new IllegalArgumentException("No existen agentes para la consulta");
    	}

    	return this.getHibernateTemplate().execute(new HibernateCallback(){

    		public Object doInHibernate(Session session) throws HibernateException, SQLException {

    			Object o = null;

    			/* Realiza la consulta */
    			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CatBic.class)
        			.createAlias("cuentaNombrada", "cn")
        			.createAlias("cn.institucion", "i")
        			.createAlias("i.tipoInstitucion", "ti");
    			
    			Disjunction disjunction = Restrictions.disjunction();
    			for (int i = 0; i < agenteVO.length; i++) {
    				/* Valida el agente */
    				//ValidatorUtil.validaAgenteVO(agenteVO[i], true);
    				/* Agrega filtros */
    				if(StringUtils.isNotBlank(agenteVO[i].getId()) || StringUtils.isNotBlank(agenteVO[i].getFolio()) || StringUtils.isNotBlank(agenteVO[i].getCuenta())) {
    					disjunction.add(Restrictions.conjunction()
        						.add(Restrictions.eq("ti.claveTipoInstitucion", agenteVO[i].getId()))
        						.add(Restrictions.eq("i.folioInstitucion", agenteVO[i].getFolio()))
        						.add(Restrictions.eq("cn.cuenta", agenteVO[i].getCuenta())));
    				}
    				else if(StringUtils.isNotBlank(agenteVO[i].getId()) || StringUtils.isNotBlank(agenteVO[i].getFolio())) {
    					disjunction.add(Restrictions.conjunction()
        						.add(Restrictions.eq("ti.claveTipoInstitucion", agenteVO[i].getId()))
        						.add(Restrictions.eq("i.folioInstitucion", agenteVO[i].getFolio())));
    				} else if(StringUtils.isNotBlank(agenteVO[i].getCuenta())) {
    					disjunction.add(Restrictions.conjunction()
        						.add(Restrictions.eq("cn.cuenta", agenteVO[i].getCuenta())));
    				}
    			}
    			detachedCriteria.add(disjunction);

    			if(StringUtils.isNotBlank(custodio)){
    			    detachedCriteria.add(Restrictions.like("detalleCustodio", "%" + custodio.trim().toUpperCase() + "%"));	
    			}

    			/* Si el caso es solo efectuar el count */
    			if(!recupera){ 
    			    detachedCriteria.setProjection(Projections.rowCount());
    			    List<CatBic> lista = getHibernateTemplate().findByCriteria(detachedCriteria);
    			    o = lista != null && !lista.isEmpty() ? lista.get(0) : null;
    			}

    			/* Si el caso es recuperar todos los registros (se consulta sin el custodio) */
    			else {
    			    detachedCriteria.addOrder(Order.asc("ti.claveTipoInstitucion"))
    				.addOrder(Order.asc("i.folioInstitucion"))
    				.addOrder(Order.asc("cn.cuenta"));
    			    o = getHibernateTemplate().findByCriteria(detachedCriteria);
    			}

    			return o;
    		}

    	});

    }
    
    private Object findCatBic(final Long[] idCatBic, final String custodio, final boolean recupera) {

    	log.info("Entrando a CatBicDaoImpl.findCatBic()");

    	/* Valida el arreglo de agentes */
    	if (idCatBic == null || idCatBic.length == 0) {
    		throw new IllegalArgumentException("No existen agentes para la consulta");
    	}

    	return this.getHibernateTemplate().execute(new HibernateCallback(){

    		public Object doInHibernate(Session session) throws HibernateException, SQLException {

    			Object o = null;

    			/* Realiza la consulta */
    			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CatBic.class);
    			
    			detachedCriteria.add(Restrictions.in("idCatbic", idCatBic));

    			if(StringUtils.isNotBlank(custodio)){
    			    detachedCriteria.add(Restrictions.like("detalleCustodio", "%" + custodio.trim().toUpperCase() + "%"));	
    			}

    			/* Si el caso es solo efectuar el count */
    			if(!recupera){ 
    			    detachedCriteria.setProjection(Projections.rowCount());
    			    List<CatBic> lista = getHibernateTemplate().findByCriteria(detachedCriteria);
    			    o = lista != null && !lista.isEmpty() ? lista.get(0) : null;
    			}

    			/* Si el caso es recuperar todos los registros (se consulta sin el custodio) */
    			else {
    			    detachedCriteria.addOrder(Order.asc("idCatbic"));
    			    o = getHibernateTemplate().findByCriteria(detachedCriteria);
    			}

    			return o;
    		}

    	});

    }
    
    /**
     * @see com.indeval.portalinternacional.persistence.dao.CatBicDao#findCatBic()
     */
    public List<CatBic> findCatBic(){

        log.info("Entrando a CatBicDaoImpl.findCatBic()");
    	
        /* Realiza la consulta */
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CatBic.class);
        detachedCriteria.addOrder(Order.asc("detalleCustodio"));
        return getHibernateTemplate().findByCriteria(detachedCriteria);
    	
    }
    
    /**
     * @see com.indeval.portalinternacional.persistence.dao.CatBicDao#findCatBic(java.util.List)
     */
    public List<CatBic> findCatBic(final List<SicEmision> sicEmisiones) {
    	HibernateCallback hibernateCallback = new HibernateCallback() {
    		public Object doInHibernate(Session session) throws HibernateException, SQLException {
    			Criteria criteria = session.createCriteria(CatBic.class);
    			List<Long> idsCatBic = new ArrayList<Long>(sicEmisiones.size());
    			for (int i = 0; i < sicEmisiones.size(); i++) {
    				if ((sicEmisiones.get(i) != null) && (sicEmisiones.get(i).getCatBic() != null)) {
    					Long idCatBic = sicEmisiones.get(i).getCatBic().getIdCatbic();
    					if ((idCatBic != null) && (!idsCatBic.contains(idCatBic))) {
    						idsCatBic.add(idCatBic);
    					}
    				}
    			}
    			if (!idsCatBic.isEmpty()) {
    				criteria.add(Restrictions.in("idCatbic",idsCatBic));
    			}
    			/* Solo se consultan registros vigentes */
    			criteria.add(Restrictions.eq("estatusRegistro",Constantes.ESTATUS_REGISTRO_VIGENTE));
    			return (criteria.list());
    		}
    	};
    	return ((List<CatBic>)this.getHibernateTemplate().executeFind(hibernateCallback));
    }

    /*
     * (non-Javadoc)
     * @see com.indeval.portalinternacional.persistence.dao.CatBicDao#findCatBicEnBaseAEmision(com.indeval.portaldali.persistence.modelo.Emision)
     */
    public List<CatBic> findCatBicEnBaseAEmision(final Emision emision) {
        log.debug("\n\n####### Entrando a findCatBicEnBaseAEmision()...");
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT se.catBic ");
        sb.append("FROM " + SicEmision.class.getName() + " se, " + Bovedas.class.getName() + " bo, " + 
                            Emision.class.getName() + " em ");
        sb.append("WHERE se.emision.idEmision = :idEmision ");
        sb.append("AND se.estatusRegistro = '" + Constantes.ESTATUS_REGISTRO_VIGENTE + "' ");
        sb.append("AND em.idEmision = se.emision.idEmision ");
        sb.append("AND bo.idBoveda = em.idBoveda ");
        sb.append("AND bo.idCuentaBoveda = se.cuentaNombrada.idCuentaNombrada ");

        return getHibernateTemplate().findByNamedParam(sb.toString(), "idEmision", emision.getIdEmision());
    }

	/**
	 * @see com.indeval.portalinternacional.persistence.dao.CatBicDao#findCatBicByName()
	 */
	public List findCatBicByName() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT DISTINCT cn.idCuentaNombrada ,cb.detalleCustodio ");
		sb.append("FROM " + CatBic.class.getName() + " cb ");
		sb.append("	JOIN cb.cuentaNombrada cn ");
		sb.append("WHERE cb.estatusRegistro = 'VIGENTE' ");
		sb.append(" AND cn.idCuentaNombrada NOT IN (4035,4033) ");
		sb.append("ORDER BY cb.detalleCustodio ");
		
		return this.getHibernateTemplate().find(sb.toString());
	}

	/**
	 * @see com.indeval.portalinternacional.persistence.dao.CatBicDao#findCatBicByName()
	 */
	public List findAllCatBicByName() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT DISTINCT cn.idCuentaNombrada ,cb.detalleCustodio ");
		sb.append("FROM " + CatBic.class.getName() + " cb ");
		sb.append("	JOIN cb.cuentaNombrada cn ");
		sb.append("WHERE cb.estatusRegistro = 'VIGENTE' ");
		sb.append("ORDER BY cb.detalleCustodio ");
		
		return this.getHibernateTemplate().find(sb.toString());
	}

	/**
	 * @see com.indeval.portalinternacional.persistence.dao.CatBicDao#findCatBicByCuentaNombrada(java.lang.Long)
	 */
	public int findCatBicByCuentaNombrada(Long idCuentaNombrada) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT COUNT(cb.detalleCustodio) ");
		sb.append("FROM " + CatBic.class.getName() + " cb ");
		sb.append("	JOIN cb.cuentaNombrada cn ");
		sb.append("WHERE cn.idCuentaNombrada = :idCuenta ");
		sb.append("	AND cb.estatusRegistro = 'VIGENTE' ");
		
		List resultado = getHibernateTemplate().findByNamedParam(sb.toString(), "idCuenta", idCuentaNombrada);
		
		if( resultado != null && 
				resultado.size() == 1 &&
				resultado.get(0) != null &&
				resultado.get(0) instanceof Long ) {
			return ((Long)resultado.get(0)).intValue();
		} else {
			return 0;
		}
		
	}

	//Cambio Multidivisas
	public Long findCatBicEnBaseABovedaEfectivoParticipante(final Long idBovedaEfectivo, final Long idInstitucion) {
		log.debug("\n\n####### Entrando a findCatBicEnBaseABovedaEfectivoParticipante()...");

		final StringBuilder sb = new StringBuilder();
		sb.append("SELECT C.ID_CATBIC ");
		sb.append("FROM C_CATBIC C, C_CATBIC_CUENTAS_PARTICIPANTE T, R_BOVEDA_V_E R, C_BOVEDA B ");
		sb.append("WHERE C.ID_CATBIC = T.ID_CATBIC(+) ");
		sb.append("AND C.ID_CUENTA_NOMBRADA = B.ID_CUENTA_BOVEDA ");
		sb.append("AND R.ID_VALORES = B.ID_BOVEDA ");
		sb.append("AND T.ID_CUENTA_NOMBRADA IS NULL ");
		sb.append("AND R.ID_EFECTIVO = :idBovedaEfectivo ");
		sb.append("AND (C.ID_INSTITUCION = :idInstitucion ");
		sb.append("	 OR C.ID_INSTITUCION IS NULL) ");
		sb.append("ORDER BY C.OMNIBUS ");

		List<Long> resultados = (List<Long>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery query = session.createSQLQuery(sb.toString());
				query.setCacheable(false);

				query.setLong("idBovedaEfectivo", idBovedaEfectivo);
				query.setLong("idInstitucion", idInstitucion);

				query.addScalar("ID_CATBIC", new LongType());

				return query.list();
			}
		});

		if(resultados != null && !resultados.isEmpty()) {
			return resultados.get(0);
		}

		return null;
	}
// Fin Cambio Multidivisas
    /**
     * @see com.indeval.portalinternacional.persistence.dao.CatBicDao#findCatBicEntityByCuentaNombrada(java.lang.Long)
     */
    public String findCatBicEntityByCuentaNombrada(Long idCuentaNombrada) {
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT cb.bicProd  ");
        sb.append(" FROM " + CatBic.class.getName() + " cb ");
        sb.append(" JOIN cb.cuentaNombrada cn ");
        sb.append(" WHERE cn.idCuentaNombrada = :idCuenta ");
        sb.append(" AND cb.estatusRegistro = 'VIGENTE' ");

        List resultado = getHibernateTemplate().findByNamedParam(sb.toString(), "idCuenta", idCuentaNombrada);
        String cb = null;

        if (resultado != null && resultado.size() == 1 && resultado.get(0) != null) {
            cb = (String) resultado.get(0);
        }
        return cb;
    }

	public List<CatBic> findCatBicByIds(Long[] idAgente) {
		// TODO Auto-generated method stub
		if (idAgente == null  || idAgente.length == 0) {
    		throw new IllegalArgumentException("No existen agentes para la consulta");
    	}
    	StringBuilder sb = new StringBuilder();
		sb.append("SELECT cb ");
		sb.append("FROM " + CatBic.class.getName() + " cb ");
		sb.append("WHERE cb.idCatbic  in (:idAgente)");
		List <CatBic> resultado = getHibernateTemplate().findByNamedParam(sb.toString(), "idAgente", idAgente);
		
    	return  resultado;
		
	}
	
	public List<CatBic> findCatBicByIds(Long[] idAgente, String cuentaIndeval) {
		// TODO Auto-generated method stub
		if (idAgente == null  || idAgente.length == 0 
			&& cuentaIndeval == null) {
    		throw new IllegalArgumentException("No existen agentes para la consulta");
    	}
    	StringBuilder sb = new StringBuilder();
    	String[] parametros ={"idAgente","cuentaIndeval"};
    	Object[] valores = {idAgente,cuentaIndeval};
		sb.append("SELECT cb ");
		sb.append("FROM " + CatBic.class.getName() + " cb ");
		sb.append("WHERE cb.idCatbic  in (:idAgente)");
		sb.append(" and cb.cuentaIndeval  = :cuentaIndeval ");
		List <CatBic> resultado = getHibernateTemplate().findByNamedParam(sb.toString(), parametros, valores);
		
    	return  resultado;
		
	}
	
	public Map<Long,String> findCustodioCuentaNombrada(Long cuentaNombrada) {
		
		Map<Long,String> mapaCustodios = null;
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT DISTINCT cn.idCuentaNombrada , cb.detalleCustodio ");
		sb.append(" FROM " + CatBic.class.getName() + " cb ");
		sb.append("	JOIN cb.cuentaNombrada cn ");
		sb.append(" WHERE cb.estatusRegistro = 'VIGENTE' ");
		sb.append(" AND cn.idCuentaNombrada NOT IN (4035,4033) ");
		sb.append(" AND cn.idCuentaNombrada = :cuentaNombrada ");
		sb.append(" ORDER BY cb.detalleCustodio ");
		
		List<Object[]> resultadoConsulta = getHibernateTemplate().findByNamedParam(sb.toString(), "cuentaNombrada", cuentaNombrada);
		
		if(resultadoConsulta != null && !resultadoConsulta.isEmpty()){
			mapaCustodios = new HashMap<Long,String>();
			for( Object[] custodios : resultadoConsulta ) {
				mapaCustodios.put((Long)custodios[0], (String)custodios[1]);
			}			
		}
		
		return mapaCustodios;
		
	}
	
	/**
	 * @see com.indeval.portalinternacional.persistence.dao.CatBicDao#findCatBicByNameForMulticarga()
	 */
	public Map<Long,String> findCatBicByNameForMulticarga() {
		log.info("Entrando a CatBicDaoImpl.findCatBicByNameForMulticarga()");
		
		Map<Long,String> mapaCustodios = null;
		final StringBuilder sb = new StringBuilder();
		sb.append("SELECT DISTINCT cn.idCuentaNombrada ,cb.detalleCustodio ");
		sb.append("FROM " + CatBic.class.getName() + " cb ");
		sb.append("	JOIN cb.cuentaNombrada cn ");
		sb.append("WHERE cb.estatusRegistro = 'VIGENTE' ");
		sb.append(" AND cn.idCuentaNombrada NOT IN (4035,4033) ");
		sb.append("ORDER BY cb.detalleCustodio ");
		List<Object[]> resultadoConsulta = (List<Object[]>)getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sb.toString());
				query.setCacheable(true);
	            query.setCacheRegion("com.indeval.portalinternacional.middleware.servicios.modelo.CatBic");
	            return query.list();
			}
		});
		if(resultadoConsulta != null && !resultadoConsulta.isEmpty()){
			mapaCustodios = new HashMap<Long,String>();
			for( Object[] custodios : resultadoConsulta ) {
				mapaCustodios.put((Long)custodios[0], (String)custodios[1]);
			}			
		}
		
		return mapaCustodios;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portalinternacional.persistence.dao.CatBicDao#getCatalogoCustodios()
	 */
	public Map<String,CatBic> getCatalogoCustodios() {
		log.info("Entrando a CatBicDaoImpl.getCatalogoCustodios()");
		
		Map<String,CatBic> mapaCustodios = null;
		final StringBuilder sb = new StringBuilder();
		sb.append("SELECT DISTINCT cus.codigoBanco ,cus.idCatBic ");
		sb.append(" FROM " + Custodio.class.getName() + " cus ");
		sb.append(" WHERE cus.id NOT IN (4,5) ");
		sb.append(" ORDER BY cus.idCatBic ");
		@SuppressWarnings("unchecked")
		List<Object[]> retornoConsulta = (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sb.toString());
				query.setCacheable(true);
	            query.setCacheRegion("com.indeval.portalinternacional.middleware.servicios.modelo.Custodio");
				return query.list();
			}
		});
		if(retornoConsulta != null && !retornoConsulta.isEmpty()){
			mapaCustodios = new HashMap<String,CatBic>();
			for( Object[] custodios : retornoConsulta ) {
				mapaCustodios.put((String)custodios[0], (CatBic)custodios[1]);
			}			
		}
		return mapaCustodios;
	}
	
	
	
	public Map<String,Long> getCustodiosIdCuentaNombrada() {
		log.info("Entrando a CatBicDaoImpl.getCatalogoCustodios()");
		return (Map<String,Long> ) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				final StringBuilder sb = new StringBuilder();
//				sb.append("SELECT DISTINCT cus.codigoBanco ,cus.idCatBic.cuentaNombrada.idCuentaNombrada ");
//				sb.append(" FROM " + Custodio.class.getName() + " cus ");
//				sb.append(" WHERE cus.id NOT IN (4,5) ");
//				sb.append(" ORDER BY cus.idCatBic ");
//				Query query = session.createQuery(sb.toString());
				Integer[] CustodiosNoIncluidos={4,5};
				Criteria criteria = session.createCriteria(Custodio.class);
				criteria.add(Restrictions.not(Restrictions.in("id",CustodiosNoIncluidos)));				
				criteria.addOrder(Order.asc("idCatBic"));
				
				
				Map<String,Long> mapaResultados=new HashMap<String,Long>();				
				List<Custodio> listCustodiaos=criteria.list();
				
				for(Custodio custodio:listCustodiaos){					
					mapaResultados.put(custodio.getCodigoBanco(),custodio.getIdCatBic().getCuentaNombrada().getIdCuentaNombrada());
				}
				
				return mapaResultados;
			}
		});
		
		
	}

	/**
	 * @see com.indeval.portalinternacional.persistence.dao.CatBicDao#getCatBicEntityByIdCuentaNombrada(Long)
	 */
    public CatBic getCatBicEntityByIdCuentaNombrada(Long idCuentaNombrada) {
        StringBuilder sb = new StringBuilder();
        sb.append(" FROM " + CatBic.class.getName() + " cb ");
        sb.append(" WHERE cb.cuentaNombrada.idCuentaNombrada = :idCuentaNombrada ");
        sb.append(" AND cb.estatusRegistro = '" + Constantes.ESTATUS_REGISTRO_VIGENTE + "' ");
        sb.append(" order by cb.idCatbic desc ");

        List<CatBic> resultado = (List<CatBic>) getHibernateTemplate().findByNamedParam(sb.toString(), "idCuentaNombrada", idCuentaNombrada);
        CatBic cb = null;

        if (resultado != null && resultado.size() >= 1) {
            cb = (CatBic) resultado.get(0);
        }

        return cb;
    }
    
    /*
     * (non-Javadoc)
     * @see com.indeval.portalinternacional.persistence.dao.CatBicDao#getCatBicByIdCtaNombradaCtaIndeval(java.lang.Long, java.lang.String)
     */
    public CatBic getCatBicByIdCtaNombradaCtaIndeval(final Long idCuentaNombrada, final String cuentaIndeval) throws BusinessException {    	
    	try {
    		CatBic catBic = (CatBic) getHibernateTemplate().execute(new HibernateCallback() {
    			public Object doInHibernate(Session session) throws HibernateException, SQLException {
    				Criteria criteria = session.createCriteria(CatBic.class, "catBic");
    				criteria.add(Restrictions.eq("catBic.cuentaNombrada.idCuentaNombrada", idCuentaNombrada));
    				criteria.add(Restrictions.eq("catBic.cuentaIndeval", cuentaIndeval));
    				return criteria.uniqueResult();
    			}
    		});    		
    		return catBic;
    	}
    	catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
    }

    /*
     * (non-Javadoc)
     * @see com.indeval.portalinternacional.persistence.dao.CatBicDao#findCatBicByNameAndActive()
     */
	public List findCatBicByNameAndActive() {

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT DISTINCT cn.idCuentaNombrada ,cb.detalleCustodio ");
		sb.append("FROM " + CatBic.class.getName() + " cb ");
		sb.append("	JOIN cb.cuentaNombrada cn ");
		sb.append("WHERE cb.estatusRegistro = 'VIGENTE' ");
		sb.append(" AND cn.idCuentaNombrada NOT IN (4035,4033) ");
		sb.append("AND cb.activo = 1 ");
		sb.append("ORDER BY cb.detalleCustodio ");
		
		return this.getHibernateTemplate().find(sb.toString());
		
	}

	public List<Object[]> findCatBicByNameEntity() {

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT DISTINCT cn.idCuentaNombrada ,cb.detalleCustodio ,cb.bicProd ,cb.estatusRegistro ,cb.activo, cb.abreviacionCustodio ");
		sb.append("FROM " + CatBic.class.getName() + " cb ");
		sb.append("	JOIN cb.cuentaNombrada cn ");
		sb.append("WHERE cb.estatusRegistro = 'VIGENTE' ");
		sb.append(" AND cn.idCuentaNombrada NOT IN (4035,4033) ");
		sb.append("ORDER BY cb.detalleCustodio ");
		
		return this.getHibernateTemplate().find(sb.toString());
		
	}

	public void modificarCustodios(CatBicVO custodiosBeneficiarios) {
		Query query = getSession().createQuery("UPDATE CatBic cb SET cb.activo = :activo, cb.abreviacionCustodio = :abreviacionCustodio where cb.cuentaNombrada.idCuentaNombrada = :idCuentaNombrada");
		query.setInteger("activo", custodiosBeneficiarios.getActivo());
		query.setString("abreviacionCustodio", custodiosBeneficiarios.getAbreviacionCustodio());
		query.setLong("idCuentaNombrada", custodiosBeneficiarios.getCuentaNombrada());
		query.executeUpdate();
	}

	public CatBic findAbreviacionByCustodio(String abreviacionCustodio) {
		CatBic catBic = null;
    	StringBuilder sb = new StringBuilder();
    	String[] parametros ={"abreviacionCustodio"};
    	Object[] valores = {abreviacionCustodio};
		sb.append("SELECT cb ");
		sb.append("FROM " + CatBic.class.getName() + " cb ");
		sb.append("WHERE cb.abreviacionCustodio  = :abreviacionCustodio ");
		List <CatBic> resultado = getHibernateTemplate().findByNamedParam(sb.toString(), parametros, valores);
		if( resultado != null && resultado.size() > 0) {
			catBic = new CatBic();
			catBic = resultado.get(0);
		}
		return catBic;
	}

	/*
	 * (non-Javadoc)
	 * @see com.indeval.portalinternacional.persistence.dao.CatBicDao#getCustodiosByIdCatBic(java.lang.Long)
	 */
    public List<Custodio> getCustodiosByIdCatBic(Long idCatBic) {
        final StringBuilder sb = new StringBuilder();
        List<Custodio> custodios = null;

        sb.append(" FROM " + Custodio.class.getName() + " cus ");
        sb.append(" WHERE cus.idCatBic = " + idCatBic + " ");
        sb.append(" ORDER BY cus.idCatBic ASC ");
        List<Object> retornoConsulta = (List<Object>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createQuery(sb.toString());
                query.setCacheable(false);
                return query.list();
            }
        });

        if (retornoConsulta != null && !retornoConsulta.isEmpty()) {
            custodios = new ArrayList<Custodio>();
            for (Object object : retornoConsulta) {
                Custodio custodio = (Custodio) object;
                custodios.add(custodio);
            }
        }
        return custodios;
    }

    /*
     * (non-Javadoc)
     * @see com.indeval.portalinternacional.persistence.dao.CatBicDao#findTiposValorCustodiosByIdCustodio(java.lang.Long)
     */
    public List<String> findTiposValorCustodiosByIdCustodio(Long idCustodio) {
        final StringBuilder sb = new StringBuilder();
        List<String> tvsCustodio = new ArrayList<String>();

        sb.append(" FROM " + RTipoValorCustodio.class.getName() + " rtvc ");
        sb.append(" WHERE rtvc.idCustodio = " + idCustodio + " ");
        sb.append(" ORDER BY rtvc.tipoValor ASC ");
        List<Object> retornoConsulta = (List<Object>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createQuery(sb.toString());
                query.setCacheable(false);
                return query.list();
            }
        });

        if (retornoConsulta != null && !retornoConsulta.isEmpty()) {
            for (Object object : retornoConsulta) {
                RTipoValorCustodio rtvc = (RTipoValorCustodio) object;
                tvsCustodio.add(rtvc.getTipoValor());
            }
        }
        return tvsCustodio;
    }

    /*
     * (non-Javadoc)
     * @see com.indeval.portalinternacional.persistence.dao.CatBicDao#eliminarTiposValorCustodiosByIdCustodio(java.lang.Long)
     */
    public int eliminarTiposValorCustodiosByIdCustodio(Long idCustodio) {
        final StringBuilder sb = new StringBuilder();

        sb.append(" DELETE FROM " + RTipoValorCustodio.class.getName() + " rtvc ");
        sb.append(" WHERE rtvc.idCustodio = " + idCustodio + " ");
        int registrosBorrados = (int) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createQuery(sb.toString());
                query.setCacheable(false);
                return query.executeUpdate();
            }
        });

        return registrosBorrados;
    }

    /*
     * (non-Javadoc)
     * @see com.indeval.portalinternacional.persistence.dao.CatBicDao#eliminarTiposValorCustodio(java.lang.Long, java.util.List)
     */
    public int eliminarTiposValorCustodio(Long idCustodio, List<String> tvsCustodio) {
        final StringBuilder sb = new StringBuilder();

        StringBuffer cadenaTVs = new StringBuffer();
        for (int i = 0; i < tvsCustodio.size(); i++) {
            cadenaTVs.append("'" + tvsCustodio.get(i) + "'");
            if ((i+1) < tvsCustodio.size()) {
                cadenaTVs.append(",");
            }
        }
        sb.append(" DELETE FROM " + RTipoValorCustodio.class.getName() + " rtvc ");
        sb.append(" WHERE rtvc.idCustodio = " + idCustodio + " ");
        sb.append(" AND rtvc.tipoValor in ( " + cadenaTVs.toString() + ") ");
        int registrosBorrados = (int) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createQuery(sb.toString());
                query.setCacheable(false);
                return query.executeUpdate();
            }
        });

        
        
        return registrosBorrados;
    }

}
