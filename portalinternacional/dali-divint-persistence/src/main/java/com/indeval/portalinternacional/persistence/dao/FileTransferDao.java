/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.persistence.dao;

import java.util.List;

import com.bursatec.persistence.dao.BaseDao;
import com.indeval.portalinternacional.middleware.servicios.modelo.FileTransfer;

/**
 * @author javiles
 *
 */
public interface FileTransferDao extends BaseDao {
	
	/**
	 * @param id
	 * @param folio
	 * @param tipoReg
	 * @return List
	 */
	List<FileTransfer> findFileTransferDivIntByIdFolioTipoReg(String id, String folio, String tipoReg);
	
}
