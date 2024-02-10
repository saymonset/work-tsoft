package com.indeval.portalinternacional.persistence.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portalinternacional.middleware.servicios.modelo.FileTransferCapturaBeneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.RegistrosBeneficiarios;
import com.indeval.portalinternacional.middleware.servicios.modelo.TipoFormatoBeneficiario;
import com.indeval.portalinternacional.persistence.dao.FileTransferCapturaBenefDao;

public class FileTransferCapturaBenefDaoImpl extends BaseDaoHibernateImpl implements FileTransferCapturaBenefDao {
	
	private static final Logger log = LoggerFactory.getLogger(FileTransferCapturaBenefDaoImpl.class);
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portalinternacional.persistence.dao.FileTransferCapturaBenefDao#findFileTransferCapturaBeneByIdFolioTipoReg(java.lang.String)
	 */
	public FileTransferCapturaBeneficiario findFileTransferCapturaBeneByIdFolioTipoReg(final String claveUsuario) {				
		/* Se realiza la consulta */
		log.info("Entrando a FileTransferCapturaBenefDaoImpl.findFileTransferCapturaBeneByIdFolioTipoReg()");
		final StringBuilder sb = new StringBuilder();
		if(claveUsuario == null){
			return null;		
		}
		sb.append(" FROM " + FileTransferCapturaBeneficiario.class.getName() + " fileCapturaBene ");
		sb.append(" where fileCapturaBene.usuario = :claveUsuario ");	
						
		@SuppressWarnings("unchecked")
		FileTransferCapturaBeneficiario fileTransferCapturaBeneficiario = (FileTransferCapturaBeneficiario) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sb.toString());	
				query.setString("claveUsuario", claveUsuario);				
				return query.uniqueResult();
			}
		});
		return fileTransferCapturaBeneficiario;			
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portalinternacional.persistence.dao.FileTransferCapturaBenefDao#getFormatoBeneficiario()
	 */
	public List<TipoFormatoBeneficiario> getFormatoBeneficiario(){
		log.info("Entrando a FileTransferCapturaBenefDaoImpl.getFormatoBeneficiario()");
		
		final StringBuilder sb = new StringBuilder();		
		sb.append(" FROM " + TipoFormatoBeneficiario.class.getName() + " formatoBene ");
		sb.append(" ORDER BY formatoBene.idTipoFormatoBene ");
		@SuppressWarnings("unchecked")
		List<TipoFormatoBeneficiario> retorno = (List<TipoFormatoBeneficiario>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sb.toString());				
				return query.list();
			}
		});
		return retorno;
	}

	
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portalinternacional.persistence.dao.FileTransferCapturaBenefDao#consultaRegistrosBeneByIdFileCarga(java.lang.String)
	 */
	public RegistrosBeneficiarios consultaRegistrosBeneByIdFileCarga(final String claveUsuario){
		log.info("Entrando a FileTransferCapturaBenefDaoImpl.consultaRegistrosBeneByIdFileCarga()");
		
		final StringBuilder sb = new StringBuilder();
		if(claveUsuario == null){
			return null;		
		}
		sb.append(" FROM " + RegistrosBeneficiarios.class.getName() + " registroBene ");
		sb.append(" where registroBene.usuario = :claveUsuario ");		
		
		@SuppressWarnings("unchecked")
		RegistrosBeneficiarios registrosBeneficiarios = (RegistrosBeneficiarios) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sb.toString());					
				query.setString("claveUsuario", claveUsuario);
				return query.uniqueResult();
			}
		});
		return registrosBeneficiarios;
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portalinternacional.persistence.dao.FileTransferCapturaBenefDao#deleteArchivoTempFileTransferBeneficiario(java.lang.String)
	 */
	public Integer deleteArchivoTempFileTransferBeneficiario(final String claveUsuario){		
		log.info("Entrando a FileTransferCapturaBenefDaoImpl.deleteArchivoTempFileTransferBeneficiario()");
		
		if (claveUsuario == null) {
			throw new IllegalArgumentException("La clave del usuario no puede ser null o vacio");
		}
		final StringBuilder sb = new StringBuilder();		
		sb.append(" DELETE FROM " + FileTransferCapturaBeneficiario.class.getName() + " fileCapturaBene ");
		sb.append(" WHERE fileCapturaBene.usuario = :claveUsuario ");
		
		@SuppressWarnings("unchecked")
		Integer numeroRegistrosEliminados = (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sb.toString());	
				query.setString("claveUsuario", claveUsuario);
				return query.executeUpdate();
			}
		});
		return numeroRegistrosEliminados.intValue();			
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portalinternacional.persistence.dao.FileTransferCapturaBenefDao#deleteArchivoTempRegistrosCorrectos(java.lang.String)
	 */
	public Integer deleteArchivoTempRegistrosCorrectos(final String claveUsuario) {		
		log.info("Entrando a FileTransferCapturaBenefDaoImpl.deleteArchivoTempRegistrosCorrectos()");
		
		if (claveUsuario == null) {
			throw new IllegalArgumentException("La clave del usuario no puede ser null o vacio");
		}
		final StringBuilder sb = new StringBuilder();		
		sb.append(" DELETE FROM " + RegistrosBeneficiarios.class.getName() + " registroBene ");
		sb.append(" WHERE registroBene.usuario = :claveUsuario ");
		
		@SuppressWarnings("unchecked")
		Integer numeroRegistrosEliminados = (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sb.toString());	
				query.setString("claveUsuario", claveUsuario);
				return query.executeUpdate();
			}
		});
		return numeroRegistrosEliminados.intValue();			
	}
	
	
	
}
