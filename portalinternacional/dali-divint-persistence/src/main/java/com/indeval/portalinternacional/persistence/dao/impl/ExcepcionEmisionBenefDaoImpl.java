package com.indeval.portalinternacional.persistence.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portalinternacional.middleware.servicios.modelo.ExcepcionEmisionBenef;
import com.indeval.portalinternacional.persistence.dao.ExcepcionEmisionBenefDao;

public class ExcepcionEmisionBenefDaoImpl  extends BaseDaoHibernateImpl implements ExcepcionEmisionBenefDao{

	public List<ExcepcionEmisionBenef> findExecepcionesEmision(final Long idCuentaNombrada) {
		List<ExcepcionEmisionBenef> lstExcepcionesEmisiones = null;		
    	HibernateCallback hibernateCallback = new HibernateCallback() {
    		public Object doInHibernate(Session session) throws HibernateException, SQLException {
    			Criteria criteria = session.createCriteria(ExcepcionEmisionBenef.class);
    			criteria.add(Restrictions.eq("idCuentaNombrada",idCuentaNombrada)); 
    			criteria.add(Restrictions.eq("eliminado",Boolean.FALSE));
    			return (criteria.list());
    		}
    	};    	
    	lstExcepcionesEmisiones = (List<ExcepcionEmisionBenef>)this.getHibernateTemplate().executeFind(hibernateCallback);    	    	    	    
    	return lstExcepcionesEmisiones;
	}
	
	public List<ExcepcionEmisionBenef> findExecepcionesEmision() {
		List<ExcepcionEmisionBenef> lstExcepcionesEmisiones = null;		
    	HibernateCallback hibernateCallback = new HibernateCallback() {
    		public Object doInHibernate(Session session) throws HibernateException, SQLException {
    			Criteria criteria = session.createCriteria(ExcepcionEmisionBenef.class);
    			criteria.add(Restrictions.eq("eliminado",Boolean.FALSE));    			
    			return (criteria.list());
    		}
    	};    	
    	lstExcepcionesEmisiones = (List<ExcepcionEmisionBenef>)this.getHibernateTemplate().executeFind(hibernateCallback);    	    	    	    
    	return lstExcepcionesEmisiones;
	}

	public ExcepcionEmisionBenef findExecepcionesEmision(final ExcepcionEmisionBenef emisionBenef) {
		List<ExcepcionEmisionBenef> lstExcepcionesEmisiones = null;
		ExcepcionEmisionBenef excepcionEmisionBenef = null;
		HibernateCallback hibernateCallback = new HibernateCallback() {
    		public Object doInHibernate(Session session) throws HibernateException, SQLException {
    			Criteria criteria = session.createCriteria(ExcepcionEmisionBenef.class);
    			criteria.add(Restrictions.eq("idCuentaNombrada",emisionBenef.getIdCuentaNombrada()));
    			criteria.add(Restrictions.eq("eliminado",Boolean.FALSE));
    			if(StringUtils.isNotBlank(emisionBenef.getTv())){
    				criteria.add(Restrictions.eq("tv",emisionBenef.getTv().trim()));
    			}
    			if(StringUtils.isNotBlank(emisionBenef.getEmisora())){
    				criteria.add(Restrictions.eq("emisora",emisionBenef.getEmisora().trim()));
    			}
    			if(StringUtils.isNotBlank(emisionBenef.getSerie())){
    				criteria.add(Restrictions.eq("serie",emisionBenef.getSerie().trim()));
    			}
    			if(StringUtils.isNotBlank(emisionBenef.getIsin())){
    				criteria.add(Restrictions.eq("isin",emisionBenef.getIsin().trim()));
    			}
    			return (criteria.list());
    		}
    	}; 
    	
    	lstExcepcionesEmisiones = (List<ExcepcionEmisionBenef>)this.getHibernateTemplate().executeFind(hibernateCallback);
    	if(lstExcepcionesEmisiones != null && !lstExcepcionesEmisiones.isEmpty()){
    		excepcionEmisionBenef = lstExcepcionesEmisiones.get(0);
    	}
    	
		return excepcionEmisionBenef;
	}

	public ExcepcionEmisionBenef findEmisionPorcentajeCero(final Long cuentaCustodio) {
		List<ExcepcionEmisionBenef> lstExcepcionesEmisiones = null;
		ExcepcionEmisionBenef excepcionEmisionBenef = null;
		HibernateCallback hibernateCallback = new HibernateCallback() {
    		public Object doInHibernate(Session session) throws HibernateException, SQLException {
    			Criteria criteria = session.createCriteria(ExcepcionEmisionBenef.class);
    			criteria.add(Restrictions.eq("idCuentaNombrada",cuentaCustodio));
    			criteria.add(Restrictions.eq("eliminado",Boolean.FALSE));
    			criteria.add(Restrictions.eq("esPorcentajeCero",Boolean.TRUE));    			
    			return (criteria.list());
    		}
    	}; 
    	
    	lstExcepcionesEmisiones = (List<ExcepcionEmisionBenef>)this.getHibernateTemplate().executeFind(hibernateCallback);
    	if(lstExcepcionesEmisiones != null && !lstExcepcionesEmisiones.isEmpty()){
    		excepcionEmisionBenef = lstExcepcionesEmisiones.get(0);
    	}
    	
		return excepcionEmisionBenef;
	}
}
