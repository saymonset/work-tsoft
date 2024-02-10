package com.indeval.portalinternacional.persistence.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.middleware.servicios.util.UtilsVO;
import com.indeval.portalinternacional.middleware.servicios.modelo.FileTransBeneficiario;
import com.indeval.portalinternacional.persistence.dao.FileTransferBenefDao;


public class FileTransferBenefDaoImpl  extends BaseDaoHibernateImpl implements FileTransferBenefDao{

	public PaginaVO findCargaExistente(final Integer idInstitucion,final String folioInstitucion,PaginaVO paginaVO) {
		Long cantidad = null;
		List<FileTransBeneficiario> fileTransBeneficiarios = null;
		paginaVO=UtilsVO.getPaginaNotBlank(paginaVO);
		final int offset = paginaVO.getOffset().intValue();
		final int regXPag = paginaVO.getRegistrosXPag().intValue();
		final boolean notAll = paginaVO.getRegistrosXPag().intValue() != PaginaVO.TODOS.intValue();
		
		cantidad = countBeneficiarios(idInstitucion, folioInstitucion);
		if( cantidad != null ) {
			paginaVO.setTotalRegistros(cantidad.intValue());
		} else {
			paginaVO.setTotalRegistros(0);
		}		
		
		HibernateCallback hibernateCallback = new HibernateCallback() {
    		public Object doInHibernate(Session session) throws HibernateException, SQLException {
    			Criteria criteria = session.createCriteria(FileTransBeneficiario.class);
    			criteria.add(Restrictions.eq("idInstitucion",idInstitucion));
    			criteria.add(Restrictions.eq("folioInstitucion",folioInstitucion));
    			if(notAll){
    				criteria.setFirstResult(offset);
    				criteria.setMaxResults(regXPag);
    			}
    			return (criteria.list());
    		}
    	};    		
    	fileTransBeneficiarios = (List<FileTransBeneficiario>)this.getHibernateTemplate().executeFind(hibernateCallback);
    	paginaVO.setRegistros(fileTransBeneficiarios);
		return paginaVO;
	}
	public void saveCarga(List<FileTransBeneficiario> beneficiarios) {				
		this.getHibernateTemplate().saveOrUpdateAll(beneficiarios);
	}
	public void deleteCarga(final Integer idInstitucion,final String folioInstitucion, final Boolean deleteOnlyWithError) {
		final StringBuilder sb = new StringBuilder();
		final List<Object> paramsSql = new ArrayList<Object>();
		final List<Type> tiposSql = new ArrayList<Type>();
		
		sb.append(" DELETE FROM " + FileTransBeneficiario.class.getName() + " st ");
		sb.append(" WHERE idInstitucion = ? ");
		paramsSql.add(idInstitucion);
		tiposSql.add(new IntegerType());
		sb.append(" AND folioInstitucion = ? ");
		paramsSql.add(folioInstitucion);
		tiposSql.add(new StringType());
		
		if(deleteOnlyWithError){
			sb.append(" AND error IS NULL ");
		}
		
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sb.toString());
				query.setParameters(paramsSql.toArray(new Object[0]), tiposSql.toArray(new Type[0]));
				return query.executeUpdate();
			}
		});
	}
	public long getNumRegistrosConError(final Integer idInstitucion,final String folioInstitucion) {
		List<FileTransBeneficiario> fileTransBeneficiarios = null;
		long resgistros = 0;
		
		HibernateCallback hibernateCallback = new HibernateCallback() {
    		public Object doInHibernate(Session session) throws HibernateException, SQLException {
    			Criteria criteria = session.createCriteria(FileTransBeneficiario.class);
    			criteria.add(Restrictions.eq("idInstitucion",idInstitucion));
    			criteria.add(Restrictions.eq("folioInstitucion",folioInstitucion));
    			criteria.add(Restrictions.isNotNull("error"));
    			return (criteria.list());
    		}
    	};    		
    	fileTransBeneficiarios = (List<FileTransBeneficiario>)this.getHibernateTemplate().executeFind(hibernateCallback);
    	if(fileTransBeneficiarios != null && !fileTransBeneficiarios.isEmpty() ){
    		resgistros = fileTransBeneficiarios.size();
    	}
    	
		return resgistros;
	}
	
	@Override
	public List<FileTransBeneficiario> consultaBeneficiariosConError(final Integer idInstitucion, final String folioInstitucion) {
		List<FileTransBeneficiario> fileTransBeneficiarios = null;				
		HibernateCallback hibernateCallback = new HibernateCallback() {
    		public Object doInHibernate(Session session) throws HibernateException, SQLException {
    			Criteria criteria = session.createCriteria(FileTransBeneficiario.class);
    			criteria.add(Restrictions.eq("idInstitucion",idInstitucion));
    			criteria.add(Restrictions.eq("folioInstitucion",folioInstitucion));
    			criteria.add(Restrictions.isNotNull("error"));
    			return (criteria.list());
    		}
    	};    		
    	fileTransBeneficiarios = (List<FileTransBeneficiario>)this.getHibernateTemplate().executeFind(hibernateCallback);
		return fileTransBeneficiarios;
	}
	
	public long getNumRegistrosSinError(final Integer idInstitucion,final String folioInstitucion) {
		List<FileTransBeneficiario> fileTransBeneficiarios = null;
		long resgistros = 0;
		
		HibernateCallback hibernateCallback = new HibernateCallback() {
    		public Object doInHibernate(Session session) throws HibernateException, SQLException {
    			Criteria criteria = session.createCriteria(FileTransBeneficiario.class);
    			criteria.add(Restrictions.eq("idInstitucion",idInstitucion));
    			criteria.add(Restrictions.eq("folioInstitucion",folioInstitucion));
    			criteria.add(Restrictions.isNull("error"));
    			return (criteria.list());
    		}
    	};    		
    	fileTransBeneficiarios = (List<FileTransBeneficiario>)this.getHibernateTemplate().executeFind(hibernateCallback);
    	if(fileTransBeneficiarios != null && !fileTransBeneficiarios.isEmpty() ){
    		resgistros = fileTransBeneficiarios.size();
    	}
    	
		return resgistros;
	}		
	
	private Long countBeneficiarios(final Integer idInstitucion,final String folioInstitucion){
		Long numBeneficiarios = null;
		List<FileTransBeneficiario> fileTransBeneficiarios = null;
		
		HibernateCallback hibernateCallback = new HibernateCallback() {
    		public Object doInHibernate(Session session) throws HibernateException, SQLException {
    			Criteria criteria = session.createCriteria(FileTransBeneficiario.class);
    			criteria.add(Restrictions.eq("idInstitucion",idInstitucion));
    			criteria.add(Restrictions.eq("folioInstitucion",folioInstitucion));    			
    			return (criteria.list());
    		}
    	};    		
    	fileTransBeneficiarios = (List<FileTransBeneficiario>)this.getHibernateTemplate().executeFind(hibernateCallback);
    	if(fileTransBeneficiarios != null && !fileTransBeneficiarios.isEmpty()){
    		numBeneficiarios = Long.valueOf(fileTransBeneficiarios.size());
    	}  	 
		
		return numBeneficiarios;
	}
	public List<FileTransBeneficiario> findCargaExistenteSinError(final Integer idInstitucion,final String folioInstitucion) {
		List<FileTransBeneficiario> fileTransBeneficiarios = null;				
		HibernateCallback hibernateCallback = new HibernateCallback() {
    		public Object doInHibernate(Session session) throws HibernateException, SQLException {
    			Criteria criteria = session.createCriteria(FileTransBeneficiario.class);
    			criteria.add(Restrictions.eq("idInstitucion",idInstitucion));
    			criteria.add(Restrictions.eq("folioInstitucion",folioInstitucion));
    			criteria.add(Restrictions.isNull("error"));
    			return (criteria.list());
    		}
    	};    		
    	fileTransBeneficiarios = (List<FileTransBeneficiario>)this.getHibernateTemplate().executeFind(hibernateCallback);
		return fileTransBeneficiarios;
	}

}
