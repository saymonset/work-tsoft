/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.persistence.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.dao.common.util.ValidatorUtil;
import com.indeval.portaldali.persistence.util.PageableCriteria;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.CatBic;
import com.indeval.portalinternacional.middleware.servicios.modelo.SicDetalle;
import com.indeval.portalinternacional.persistence.dao.SicDetalleDao;

/**
 * @author javiles
 *
 */
@SuppressWarnings({"unchecked"})
public class SicDetalleDaoImpl extends BaseDaoHibernateImpl implements SicDetalleDao {
    
    /** Log de clase. */
	private static final Logger log = LoggerFactory.getLogger(SicDetalleDaoImpl.class);

    /**
     * @see com.indeval.portalinternacional.persistence.dao.SicDetalleDao#findSicDetalle(AgenteVO[])
     */
    public List<SicDetalle> findSicDetalle(AgenteVO[] agenteVO) {
        
    	log.info("Entrando a SicDetalleDaoImpl.findSicDetalle(AgenteVO[])");
        
    	return (List<SicDetalle>) findDepositantes(agenteVO, null, true);
        
    }
    
    /**
     * @see com.indeval.portalinternacional.persistence.dao.SicDetalleDao#findSicDetalle(com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO)
     */
    public List<SicDetalle> findSicDetalle(AgenteVO agenteVO) {
        
        log.info("Entrando a SicDetalleDaoImpl.findSicDetalle(AgenteVO)");
        
        ValidatorUtil.validaAgenteVO(agenteVO, true);
        
        return findSicDetalle(new AgenteVO[]{agenteVO});
        
    }

    /**
     * @see com.indeval.portalinternacional.persistence.dao.SicDetalleDao#findSicDetalle(com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO[], java.lang.String)
     */
    public SicDetalle findSicDetalle(AgenteVO[] agenteVO, String depositante) {
    	
    	/* Valida el depositante recibido */
    	if (StringUtils.isBlank(depositante)) {
    	    throw new IllegalArgumentException("Falta la descripcion del depositante");
    	}
    	
    	return (SicDetalle) findDepositantes(agenteVO, depositante, true);
    }
    
	/**
	 * @see com.indeval.portalinternacional.persistence.dao.SicDetalleDao#findDepositantes(AgenteVO[], String)
	 */
    public Integer findDepositantes(AgenteVO[] agenteVO, String depositante) {

    	log.info("Entrando a SicDetalleDaoImpl.findDepositantes()");

    	/* Valida el depositante recibido */
    	if (StringUtils.isBlank(depositante)) {
    	    throw new IllegalArgumentException("Falta la descripcion del depositante");
    	}
   
        return (Integer) findDepositantes(agenteVO, depositante, false);
    }
    
    /**
     * Obtiene la lista de instancias SicDetalle correspondientes a los {@link AgenteVO} 
     * proporcionados filtrada por depositante si es requerido.
     * 
     * @param agenteVO arreglo de agentes 
     * @param depositante filtro opcional
     * @param recupera si es false solo retorna el count, si es true retorna la lista
     * @return Object
     */
    private Object findDepositantes(AgenteVO[] agenteVO, String depositante, boolean recupera) {
    	
    	log.info("Entrando a SicDetalleDaoImpl.findDepositantes()");
    	
    	/* Valida el arreglo de agentes */
    	if (agenteVO == null || agenteVO.length == 0) {
    	    throw new IllegalArgumentException("No existen agentes para la consulta");
    	}

    	/* Objeto de retorno */
    	Object o = null;
    	
    	/* Realiza la consulta */
    	DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SicDetalle.class)
    	    .createAlias("cuentaNombrada", "cn")
            .createAlias("cn.institucion", "i")
            .createAlias("i.tipoInstitucion", "ti");

    	Disjunction disjunction = Restrictions.disjunction();
    	for (int i = 0; i < agenteVO.length; i++) {
    		/* Valida el agente */
    	    ValidatorUtil.validaAgenteVO(agenteVO[i], true);
    	    /* Agrega filtros */
    	    disjunction.add(Restrictions.conjunction()
    	            .add(Restrictions.eq("ti.claveTipoInstitucion", agenteVO[i].getId()))
    	            .add(Restrictions.eq("i.folioInstitucion", agenteVO[i].getFolio()))
    	            .add(Restrictions.eq("cn.cuenta", agenteVO[i].getCuenta())));
    	}
    	detachedCriteria.add(disjunction);
    	
    	if(StringUtils.isNotBlank(depositante)){
    	    detachedCriteria.add(Restrictions.like("depLiq", "%" + depositante.trim().toUpperCase() + "%"));	
    	}
    
    	/* Si el caso es solo efectuar el count */
    	if(!recupera){
    	    detachedCriteria.setProjection(Projections.rowCount());
    	    List<SicDetalle> lista = getHibernateTemplate().findByCriteria(detachedCriteria);
    	    o = lista != null && !lista.isEmpty() ? lista.get(0) : null;	
    	}
    	/* Si el caso es recuperar el registro unico del depositante (se envia el depositante) */
        else if (StringUtils.isNotBlank(depositante)) {
            List lista = getHibernateTemplate().findByCriteria(detachedCriteria);
            if (lista != null && lista.size() == 1) {
                o = lista.get(0);
            }
        }
    	/* Si el caso es recuperar todos los registros (se consulta sin el depositante) */
    	else{
    	    detachedCriteria.addOrder(Order.asc("ti.claveTipoInstitucion"))
                .addOrder(Order.asc("i.folioInstitucion"))
                .addOrder(Order.asc("cn.cuenta"));
            o = getHibernateTemplate().findByCriteria(detachedCriteria);
    	}
        
        return o;
    	
    }
    
    private Object findDepositantes(Long[] idCatBic, String depositante, boolean recupera) {
    	
    	log.info("Entrando a SicDetalleDaoImpl.findDepositantes()");
    	
    	/* Valida el arreglo de agentes */
    	if (idCatBic == null || idCatBic.length == 0) {
    	    throw new IllegalArgumentException("No existen agentes para la consulta");
    	}

    	/* Objeto de retorno */
    	Object o = null;
    	
    	/* Realiza la consulta */
    	DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SicDetalle.class);

    	 detachedCriteria.add(Restrictions.in("catBic.idCatbic", idCatBic));
    	
    	if(StringUtils.isNotBlank(depositante)){
    	    detachedCriteria.add(Restrictions.like("depLiq", "%" + depositante.trim().toUpperCase() + "%"));	
    	}
    
    	/* Si el caso es solo efectuar el count */
    	if(!recupera){
    	    detachedCriteria.setProjection(Projections.rowCount());
    	    List<SicDetalle> lista = getHibernateTemplate().findByCriteria(detachedCriteria);
    	    o = lista != null && !lista.isEmpty() ? lista.get(0) : null;	
    	}
    	/* Si el caso es recuperar el registro unico del depositante (se envia el depositante) */
        else if (StringUtils.isNotBlank(depositante)) {
            List lista = getHibernateTemplate().findByCriteria(detachedCriteria);
            if (lista != null && lista.size() == 1) {
                o = lista.get(0);
            }
        }
    	/* Si el caso es recuperar todos los registros (se consulta sin el depositante) */
    	else{
    	    detachedCriteria.addOrder(Order.asc("ti.claveTipoInstitucion"))
                .addOrder(Order.asc("i.folioInstitucion"))
                .addOrder(Order.asc("cn.cuenta"));
            o = getHibernateTemplate().findByCriteria(detachedCriteria);
    	}
        
        return o;
    	
    }

	/**
	 * @see com.indeval.portalinternacional.persistence.dao.SicDetalleDao#findDepositantes(java.util.List)
	 */
	public List<SicDetalle> findDepositantes(final List<CatBic> catBics) {
		if ((catBics == null) || (catBics.isEmpty())) {
			throw new IllegalArgumentException("catBics es NULL o vacio");
		}
		HibernateCallback hibernateCallback = new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(SicDetalle.class);
				List<Long> idsCatBic = new ArrayList<Long>(catBics.size());
				for (int i = 0; i < catBics.size(); i++) {
					if ((catBics.get(i) != null)) {
						Long idCatBic = catBics.get(i).getIdCatbic();
						if ((idCatBic != null) && (!idsCatBic.contains(idCatBic))) {
							idsCatBic.add(idCatBic);
						}
					}
				}
				if (!idsCatBic.isEmpty()) {
					criteria.createAlias("catBic", "cb");
					criteria.add(Restrictions.in("cb.idCatbic", idsCatBic));
				}
				return (criteria.list());
			}
		};
		
		return ((List<SicDetalle>)this.getHibernateTemplate().execute(hibernateCallback));
	}

	/**
	 * @see com.indeval.portalinternacional.persistence.dao.SicDetalleDao#findDepositantes(java.util.List)
	 */
	public List<SicDetalle> findDepositantes(final CatBic catBic, final Boolean todos) {
		if (catBic == null) {
			throw new IllegalArgumentException("catBic es NULL o vacio");
		}
		HibernateCallback hibernateCallback = new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(SicDetalle.class);
				criteria.createAlias("catBic", "cb");
				criteria.add(Restrictions.eq("cb.idCatbic", catBic.getIdCatbic()));
				if ((todos != null) && (!todos.booleanValue())) {
					criteria.add(Restrictions.eq("estatusRegistro", Constantes.ESTATUS_REGISTRO_VIGENTE));
				}
				return (criteria.list());
			}
		};
		
		return ((List<SicDetalle>)this.getHibernateTemplate().execute(hibernateCallback));
	}
	
	/**
	 * @see com.indeval.portalinternacional.persistence.dao.SicDetalleDao#findDepositantes(com.indeval.portalinternacional.middleware.servicios.modelo.CatBic, java.lang.String, java.lang.String, com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO)
	 */
	public PaginaVO findDepositantes(final CatBic catBic, final String bicDepLiq, final String idDepLiq, final String depLiq, final PaginaVO paginaVO) {
		HibernateCallback hibernateCallback = new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				PageableCriteria pageableCriteria = PageableCriteria.createPageableCriteria(session, SicDetalle.class);
				pageableCriteria.createAlias("catBic", "cb");
				pageableCriteria.add(Restrictions.eq("cb.idCatbic",catBic.getIdCatbic()));
				if (StringUtils.isNotBlank(bicDepLiq)) {
					pageableCriteria.add(Restrictions.eq("bicDepLiq",bicDepLiq));
				}
				if (StringUtils.isNotBlank(idDepLiq)) {
					pageableCriteria.add(Restrictions.eq("idDepLiq",idDepLiq));
				}
				if (StringUtils.isNotBlank(depLiq)) {
					pageableCriteria.add(Restrictions.eq("depLiq",depLiq));
				}
				pageableCriteria.addOrder(Order.desc("idSicDetalle"));
				return (pageableCriteria.pageResult(paginaVO));
			}
		};
		return ((PaginaVO)this.getHibernateTemplate().execute(hibernateCallback));
	}

	public SicDetalle findSicDetalle(Long[] idCatBics, String depositante) {
		/* Valida el depositante recibido */
    	if (StringUtils.isBlank(depositante)) {
    	    throw new IllegalArgumentException("Falta la descripcion del depositante");
    	}
    	
    	return (SicDetalle) findDepositantes(idCatBics, depositante, true);
	}

	public SicDetalle findByDepLiqVigente(String depLiq) {
	    SicDetalle sd = null;

        StringBuffer query = new StringBuffer();        
        query.append(" from " + SicDetalle.class.getName() + " sd " );
        query.append(" where sd.depLiq = ? ");
        query.append(" and sd.estatusRegistro = ? ");
        ArrayList<Object> params = new ArrayList<Object>();
        params.add(depLiq);
        params.add(Constantes.ESTATUS_REGISTRO_VIGENTE);
        ArrayList<Type> tipos = new ArrayList<Type>();
        tipos.add(new StringType());
        tipos.add(new StringType());
        Query q = getSession().createQuery(query.toString()).setParameters(params.toArray(), tipos.toArray(new Type[] {}));
        List<SicDetalle> res = q.list();
        
        if (res != null && !res.isEmpty()) {
            sd = (SicDetalle) res.get(0);
        }

        return sd;
    }

	/**
	 * @see com.indeval.portalinternacional.persistence.dao.SicDetalleDao#findDepositantesLiquidadores()
	 */
	public List<String> findDepositantesLiquidadores() {
	    StringBuffer query = new StringBuffer();        
        query.append(" SELECT DISTINCT sd.depLiq ");
        query.append(" FROM " + SicDetalle.class.getName() + " sd " );
        query.append(" ORDER BY sd.depLiq ASC ");
        List<String> dl = this.getHibernateTemplate().find(query.toString());
        return dl;
	}

}
