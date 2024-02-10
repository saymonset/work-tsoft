/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.persistence.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portalinternacional.middleware.servicios.modelo.FileUpload;
import com.indeval.portalinternacional.persistence.dao.FileUploadDao;

/**
 * Implementacion del DAO para el mecanismo de lock de trasferencia de archivos
 * 
 * @author Esteban Herrera
 * 
 */
@SuppressWarnings({"unchecked"})
public class FileUploadDaoImpl extends BaseDaoHibernateImpl implements
		FileUploadDao {

	/** Objeto de loggeo */
	private static final Logger log = LoggerFactory.getLogger(FileUploadDaoImpl.class);

	/**
	 * @see com.indeval.portalinternacional.persistence.dao.FileUploadDao#getLock(com.indeval.portalinternacional.middleware.servicios.modelo.FileUpload)
	 */
	public Boolean getLock(FileUpload fileUpload) {
		log.info("Entrando a FileUploadDaoImpl.getLock()");
		List<Object> registros = getHibernateTemplate()
				.find(
						"from "+ FileUpload.class.getName()  +" f where f.idProceso = ? ",
						fileUpload.getIdProceso());
		if (registros != null && !registros.isEmpty())
			return Boolean.FALSE;
		getHibernateTemplate().save(fileUpload);
		return Boolean.TRUE;
	}

	/**
	 * @see com.indeval.portalinternacional.persistence.dao.FileUploadDao#getProcessInfo(com.indeval.portalinternacional.middleware.servicios.modelo.FileUpload)
	 */
	public FileUpload getProcessInfo(FileUpload fileUpload) {
		log.info("Entrando a FileUploadDaoImpl.getProcessInfo()");
		List<FileUpload> registros = getHibernateTemplate()
				.find(
						"from " + FileUpload.class.getName() + " f where f.idProceso = ?",
						fileUpload.getIdProceso());
		if (registros != null && !registros.isEmpty())
			return registros.get(0);
		return null;
	}

	/**
	 * @see com.indeval.portalinternacional.persistence.dao.FileUploadDao#isProcessRunning(com.indeval.portalinternacional.middleware.servicios.modelo.FileUpload)
	 */
	public Boolean isProcessRunning(FileUpload fileUpload) {
		log.info("Entrando a FileUploadDaoImpl.isProcessRunning()");
		List<FileUpload> registros = getHibernateTemplate()
				.find(
						"from " + FileUpload.class.getName() + " f where f.idProceso = ?",
						fileUpload.getIdProceso());
		if (registros != null && !registros.isEmpty())
			return Boolean.TRUE;
		return Boolean.FALSE;
	}

	/**
	 * @see com.indeval.portalinternacional.persistence.dao.FileUploadDao#releaseLock(com.indeval.portalinternacional.middleware.servicios.modelo.FileUpload)
	 */
	public void releaseLock(FileUpload fileUpload) {
		log.info("Entrando a FileUploadDaoImpl.releaseLock()");
		getHibernateTemplate().delete(fileUpload);
	}

	/**
	 * @see com.indeval.portalinternacional.persistence.dao.FileUploadDao#updateProcessInfo(com.indeval.portalinternacional.middleware.servicios.modelo.FileUpload)
	 */
	public void updateProcessInfo(FileUpload fileUpload) {
		log.info("Entrando a FileUploadDaoImpl.updateProcessInfo()");
		getHibernateTemplate().update(fileUpload);
	}

}
