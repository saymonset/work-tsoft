package com.indeval.portalinternacional.persistence.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portaldali.persistence.modelo.Emision;
import com.indeval.portaldali.persistence.modelo.Emisora;
import com.indeval.portaldali.persistence.modelo.Institucion;
import com.indeval.portaldali.persistence.modelo.Instrumento;
import com.indeval.portalinternacional.persistence.dao.EmisionDao;

public class EmisionDaoImpl extends BaseDaoHibernateImpl implements EmisionDao{

	public Emision findEmisionByIsin(final String isin) {
		List<Emision> lstEmisiones = null;
		Emision emision = null;
		HibernateCallback hibernateCallback = new HibernateCallback() {
    		public Object doInHibernate(Session session) throws HibernateException, SQLException {
    			Criteria criteria = session.createCriteria(Emision.class);
    			criteria.add(Restrictions.eq("isin",isin.trim())); 
    			return (criteria.list());
    		}
    	};    	
    	lstEmisiones = (List<Emision>)this.getHibernateTemplate().executeFind(hibernateCallback);
    	if(lstEmisiones != null && !lstEmisiones.isEmpty()){
    		emision = lstEmisiones.get(0); 
    	}
		return emision;
	}

	public Emisora findEmisoraByCvePizarra(final String clavePizarra) {
		List<Emisora> lstEmisoras = null;
		Emisora emisora = null;
		HibernateCallback hibernateCallback = new HibernateCallback() {
    		public Object doInHibernate(Session session) throws HibernateException, SQLException {
    			Criteria criteria = session.createCriteria(Emisora.class);
    			criteria.add(Restrictions.eq("clavePizarra",clavePizarra.trim())); 
    			return (criteria.list());
    		}
    	};    	
    	lstEmisoras = (List<Emisora>)this.getHibernateTemplate().executeFind(hibernateCallback);
    	if(lstEmisoras != null && !lstEmisoras.isEmpty()){
    		emisora = lstEmisoras.get(0); 
    	}
		return emisora;
	}

	public Instrumento findInstrumentoByTv(final String tv) {
		List<Instrumento> lstInstrumentos = null;
		Instrumento instrumento = null;
		HibernateCallback hibernateCallback = new HibernateCallback() {
    		public Object doInHibernate(Session session) throws HibernateException, SQLException {
    			Criteria criteria = session.createCriteria(Instrumento.class);
    			criteria.add(Restrictions.eq("claveTipoValor",tv.trim())); 
    			return (criteria.list());
    		}
    	};    	
    	lstInstrumentos = (List<Instrumento>)this.getHibernateTemplate().executeFind(hibernateCallback);
    	if(lstInstrumentos != null && !lstInstrumentos.isEmpty()){
    		instrumento = lstInstrumentos.get(0); 
    	}
		return instrumento;
	}

	public Institucion findInstitucionByIdFolio(final Long id,final String folio) {
		List<Institucion> lstInstituciones = null;
		Institucion institucion = null;
		HibernateCallback hibernateCallback = new HibernateCallback() {
    		public Object doInHibernate(Session session) throws HibernateException, SQLException {
    			Criteria criteria = session.createCriteria(Institucion.class);
    			criteria.add(Restrictions.eq("folioInstitucion",folio));
    			criteria.createAlias("tipoInstitucion", "ti");
    			criteria.add(Restrictions.eq("ti.idTipoInstitucion",id));
    			return (criteria.list());
    		}
    	};    	
    	lstInstituciones = (List<Institucion>)this.getHibernateTemplate().executeFind(hibernateCallback);
    	if(lstInstituciones != null && !lstInstituciones.isEmpty()){
    		institucion = lstInstituciones.get(0); 
    	}
		
		return institucion;
	}

}
