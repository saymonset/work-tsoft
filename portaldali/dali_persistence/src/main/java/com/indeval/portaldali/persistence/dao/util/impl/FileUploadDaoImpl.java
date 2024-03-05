/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.dao.util.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portaldali.persistence.dao.util.FileUploadDao;
import com.indeval.portaldali.persistence.model.FileUpload;

/**
 * 
 * @author csanchez
 * 
 */
public class FileUploadDaoImpl extends BaseDaoHibernateImpl implements
		FileUploadDao {

	/** Objeto de loggeo */
	private static final Logger logger = LoggerFactory.getLogger(FileUploadDaoImpl.class);

	/**
	 * @see com.indeval.portaldali.persistence.dao.util.FileUploadDao#getLock(com.indeval.portaldali.persistence.model.FileUpload)
	 */
	@SuppressWarnings("unchecked")
	public Boolean getLock(FileUpload fileUpload) {
		logger.trace("Entrando a FileUploadDaoImpl.getLock()");
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
	 * @see com.indeval.portaldali.persistence.dao.util.FileUploadDao#getProcessInfo(com.indeval.portaldali.persistence.model.FileUpload)
	 */
	@SuppressWarnings("unchecked")
	public FileUpload getProcessInfo(FileUpload fileUpload) {
		logger.trace("Entrando a FileUploadDaoImpl.getProcessInfo()");
		List<FileUpload> registros = getHibernateTemplate()
				.find(
						"from com.indeval.portaldali.persistence.model.FileUpload f where f.idProceso = ?",
						fileUpload.getIdProceso());
		if (registros != null && !registros.isEmpty())
			return registros.get(0);
		return null;
	}

	/**
	 * @see com.indeval.portaldali.persistence.dao.util.FileUploadDao#isProcessRunning(com.indeval.portaldali.persistence.model.FileUpload)
	 */
	@SuppressWarnings( { "unchecked", "unchecked" })
	public Boolean isProcessRunning(FileUpload fileUpload) {
		logger.trace("Entrando a FileUploadDaoImpl.isProcessRunning()");
		List<FileUpload> registros = getHibernateTemplate()
				.find(
						"from com.indeval.portaldali.persistence.model.FileUpload f where f.idProceso = ?",
						fileUpload.getIdProceso());
		if (registros != null && !registros.isEmpty())
			return Boolean.TRUE;
		return Boolean.FALSE;
	}

	/**
	 * @see com.indeval.portaldali.persistence.dao.util.FileUploadDao#releaseLock(com.indeval.portaldali.persistence.model.FileUpload)
	 */
	public void releaseLock(FileUpload fileUpload) {
		logger.trace("Entrando a FileUploadDaoImpl.releaseLock()");
		getHibernateTemplate().delete(fileUpload);
	}

	/**
	 * @see com.indeval.portaldali.persistence.dao.util.FileUploadDao#updateProcessInfo(com.indeval.portaldali.persistence.model.FileUpload)
	 */
	public void updateProcessInfo(FileUpload fileUpload) {
		logger.trace("Entrando a FileUploadDaoImpl.updateProcessInfo()");
		getHibernateTemplate().update(fileUpload);
	}

}
