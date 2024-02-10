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
import com.indeval.portalinternacional.middleware.servicios.modelo.HorarioBeneficiario;
import com.indeval.portalinternacional.persistence.dao.HorarioBeneficiarioDao;

public class HorarioBeneficiarioDaoImpl  extends BaseDaoHibernateImpl implements HorarioBeneficiarioDao{

	public List<HorarioBeneficiario> findHorariosDerecho(final Long idCuentaNombrada) {
		List<HorarioBeneficiario> horarios = null;
		
		HibernateCallback hibernateCallback = new HibernateCallback() {
    		public Object doInHibernate(Session session) throws HibernateException, SQLException {
    			Criteria criteria = session.createCriteria(HorarioBeneficiario.class);
    			criteria.add(Restrictions.eq("idCuentaNombrada",idCuentaNombrada));
    			criteria.add(Restrictions.eq("eliminado",Boolean.FALSE));
    			criteria.add(Restrictions.isNull("idInstitucion"));
    			criteria.add(Restrictions.isNull("folioInstitucion"));
    			return (criteria.list());
    		}
    	};
    	
    	horarios = (List<HorarioBeneficiario>)this.getHibernateTemplate().executeFind(hibernateCallback);
		
		return horarios;
	}

	public HorarioBeneficiario findHorarioInstitucion(final Long idCuentaNombrada,final Integer idInstitucion,final String folioInstitucion) {
		List<HorarioBeneficiario> horarios = null;
		HorarioBeneficiario horarioBeneficiario = null;
		
		HibernateCallback hibernateCallback = new HibernateCallback() {
    		public Object doInHibernate(Session session) throws HibernateException, SQLException {
    			Criteria criteria = session.createCriteria(HorarioBeneficiario.class);
    			criteria.add(Restrictions.eq("idCuentaNombrada",idCuentaNombrada));
    			criteria.add(Restrictions.eq("eliminado",Boolean.FALSE));
    			criteria.add(Restrictions.eq("idInstitucion",idInstitucion));
    			criteria.add(Restrictions.eq("folioInstitucion",folioInstitucion));
    			//criteria.add(Restrictions.isNull("porcentajeRet"));
    			criteria.add(Restrictions.isNull("tv"));
    			criteria.add(Restrictions.isNull("emisora"));
    			criteria.add(Restrictions.isNull("serie"));
    			return (criteria.list());
    		}
    	};
    	
    	horarios = (List<HorarioBeneficiario>)this.getHibernateTemplate().executeFind(hibernateCallback);
    	if(horarios != null && !horarios.isEmpty()){
    		horarioBeneficiario = horarios.get(0);
    	}
		
		return horarioBeneficiario;
	}

	public List<HorarioBeneficiario> findHorariosDerecho(){
		List<HorarioBeneficiario> horarios = null;
		
		HibernateCallback hibernateCallback = new HibernateCallback() {
    		public Object doInHibernate(Session session) throws HibernateException, SQLException {
    			Criteria criteria = session.createCriteria(HorarioBeneficiario.class);
    			criteria.add(Restrictions.eq("eliminado",Boolean.FALSE));
    			return (criteria.list());
    		}
    	};    	
    	horarios = (List<HorarioBeneficiario>)this.getHibernateTemplate().executeFind(hibernateCallback);		
		return horarios;
	}

	public HorarioBeneficiario findHorario(final HorarioBeneficiario horarioBeneficiario) {
		HorarioBeneficiario beneficiario = null;
		List<HorarioBeneficiario> horarios = null;
		
		HibernateCallback hibernateCallback = new HibernateCallback() {
    		public Object doInHibernate(Session session) throws HibernateException, SQLException {
    			Criteria criteria = session.createCriteria(HorarioBeneficiario.class);
    			criteria.add(Restrictions.eq("eliminado",Boolean.FALSE));
    			criteria.add(Restrictions.eq("idCuentaNombrada",horarioBeneficiario.getIdCuentaNombrada()));
    			if(horarioBeneficiario.getPorcentajeRet() != null){
    				criteria.add(Restrictions.eq("porcentajeRet",horarioBeneficiario.getPorcentajeRet()));
    			}
    			if(horarioBeneficiario.getDias() != null){
    				criteria.add(Restrictions.eq("dias",horarioBeneficiario.getDias()));
    			}else{
    				criteria.add(Restrictions.isNull("dias"));
    			}
    			if(horarioBeneficiario.getHora() != null){
    				criteria.add(Restrictions.eq("hora",horarioBeneficiario.getHora()));
    			}else{
    				criteria.add(Restrictions.isNull("hora"));
    			}
    			if(horarioBeneficiario.getMinuto() != null){
    				criteria.add(Restrictions.eq("minuto",horarioBeneficiario.getMinuto()));
    			}else{
    				criteria.add(Restrictions.isNull("minuto"));
    			}
    			if(horarioBeneficiario.getEsDespuesFechaCorte() != null){
    				criteria.add(Restrictions.eq("esDespuesFechaCorte",horarioBeneficiario.getEsDespuesFechaCorte()));
    			}else{
    				criteria.add(Restrictions.isNull("esDespuesFechaCorte"));
    			}    			
    			if(StringUtils.isNotBlank(horarioBeneficiario.getTv())){
    				criteria.add(Restrictions.eq("tv",horarioBeneficiario.getTv().trim()));
    			}else{
    				criteria.add(Restrictions.isNull("tv"));
    			}
    			if(StringUtils.isNotBlank(horarioBeneficiario.getEmisora())){
    				criteria.add(Restrictions.eq("emisora",horarioBeneficiario.getEmisora().trim()));
    			}else{
    				criteria.add(Restrictions.isNull("emisora"));
    			}
    			if(StringUtils.isNotBlank(horarioBeneficiario.getSerie())){
    				criteria.add(Restrictions.eq("serie",horarioBeneficiario.getSerie().trim()));
    			}else{
    				criteria.add(Restrictions.isNull("serie"));
    			}
    			if(horarioBeneficiario.getIdInstitucion() != null){
    				criteria.add(Restrictions.eq("idInstitucion",horarioBeneficiario.getIdInstitucion()));
    			}else{
    				criteria.add(Restrictions.isNull("idInstitucion"));
    			}
    			if(StringUtils.isNotBlank(horarioBeneficiario.getFolioInstitucion())){
    				criteria.add(Restrictions.eq("folioInstitucion",horarioBeneficiario.getFolioInstitucion().trim()));
    			}else{
    				criteria.add(Restrictions.isNull("folioInstitucion"));
    			}
    			return (criteria.list());
    		}
    	};    	
    	horarios = (List<HorarioBeneficiario>)this.getHibernateTemplate().executeFind(hibernateCallback);	
    	if(horarios != null && !horarios.isEmpty()){
    		beneficiario = horarios.get(0);
    	}
		return beneficiario;
	}
}
