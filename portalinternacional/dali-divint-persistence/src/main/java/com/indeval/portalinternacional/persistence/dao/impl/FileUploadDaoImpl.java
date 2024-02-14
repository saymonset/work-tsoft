/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.persistence.dao.impl;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portalinternacional.middleware.servicios.modelo.FileTransferDivisas;
import com.indeval.portalinternacional.middleware.servicios.modelo.FileUpload;
import com.indeval.portalinternacional.persistence.dao.FileUploadDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Implementacion del DAO para el mecanismo de lock de trasferencia de archivos
 *
 * @author Esteban Herrera
 */
@SuppressWarnings({"unchecked"})
public class FileUploadDaoImpl extends BaseDaoHibernateImpl implements
		FileUploadDao {

	/**
	 * Objeto de loggeo
	 */
	private static final Logger log = LoggerFactory.getLogger(FileUploadDaoImpl.class);

	/**
	 * @see com.indeval.portalinternacional.persistence.dao.FileUploadDao#getLock(com.indeval.portalinternacional.middleware.servicios.modelo.FileUpload)
	 */
	public Boolean getLock(FileUpload fileUpload) {
		log.info("Entrando a FileUploadDaoImpl.getLock()");

		List<Object> registros = getHibernateTemplate()
				.find(
						"from " + FileUpload.class.getName() + " f where f.idProceso = ? ",
						fileUpload.getIdProceso());
		if (registros != null && !registros.isEmpty())
			return Boolean.FALSE;
		System.out.println("Antes guardar");
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

	@Override
	public FileTransferDivisas getLockMulti(FileTransferDivisas ftransfer) {
		FileTransferDivisas ft = null;

		List<FileTransferDivisas> lft = getHibernateTemplate().find(" FROM " + FileTransferDivisas.class.getName() +
				" f WHERE f.idFileTransferDivisasInt=? ", ftransfer.getIdFileTransferDivisasInt());

		if (lft != null && !lft.isEmpty()) {
			ft = lft.get(0);
			return ft;

		} else {
			ftransfer.getEstatusDivisas().setIdEstatus(0L);
			Long id = (Long) getHibernateTemplate().save(ftransfer);
			ft = (FileTransferDivisas) getHibernateTemplate().get(FileTransferDivisas.class, id);
			System.out.println("FileTransferMultiDiv SAVE " + ft);
			return ft;
		}
	}

	public void updateProcessInfoMulti(FileTransferDivisas fileUpload) {
		log.info("Entrando a FileUploadDaoImpl.updateProcessInfo()");
		getHibernateTemplate().update(fileUpload);
	}

	public FileTransferDivisas getProcessInfoMulti(FileTransferDivisas fileUpload) {

		log.info("Entrando a FileUploadDaoImpl.getProcessInfoMulti()");
		List<FileTransferDivisas> registros = getHibernateTemplate().find(" FROM " + FileTransferDivisas.class.getName() + " f  WHERE f.idFileTransferDivisasInt=? ", fileUpload.getIdFileTransferDivisasInt());

		if (registros != null && !registros.isEmpty())
			return registros.get(0);
		return null;
	}

	public Boolean isProcessRunning(FileTransferDivisas fileUpload) {
		log.info("Entrando a FileUploadDaoImpl.isProcessRunning(FileTransferDivisas)");
		List<FileTransferDivisas> registros = getHibernateTemplate()
				.find(
						"from " + FileTransferDivisas.class.getName() + " f  f.idFileTransferDivisasInt=? ",
						fileUpload.getIdFileTransferDivisasInt());
		if (registros != null && !registros.isEmpty())
			return Boolean.TRUE;
		return Boolean.FALSE;
	}

	public FileTransferDivisas getFileTransferMDById(Long idFileTransferMD) {
		FileTransferDivisas f = null;
		f = (FileTransferDivisas) getHibernateTemplate().get(FileTransferDivisas.class, idFileTransferMD);
		return f;
	}

//	public void releaseLock(FileTransferDivisas fileUpload) {
//		log.info("Entrando a FileUploadDaoImpl.releaseLockFileTransferDivisas)");
//
//		getHibernateTemplate().get(EstatusDivisas.class,)
//		getHibernateTemplate().delete(fileUpload);
//	}
}
