/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.persistence.dao;

import com.bursatec.persistence.dao.BaseDao;
import com.indeval.portalinternacional.middleware.servicios.modelo.FileTransfer;
import com.indeval.portalinternacional.middleware.servicios.modelo.FileTransferDetalleDivisas;
import com.indeval.portalinternacional.middleware.servicios.modelo.FileTransferDivisas;

import java.util.List;

/**
 * @author Genner
 *
 */
public interface FileTransferMultiDao extends BaseDao {
	
	/**
	 * @param id
	 * @param folio
	 * @param tipoReg
	 * @return List
	 */
	List<FileTransfer> findFileTransferDivIntByIdFolioTipoReg(String id, String folio, String tipoReg);

	List<FileTransferDivisas> findFileTransferDivIntByUsuario(String cveUsuario);

	/**
	 * Encontrar el detalle de un FileTransfer multivisas
	 *
	 * @param idFileTransfer
	 * @return
	 */
	List<FileTransferDetalleDivisas> findFileTransferDetalleByIdFileTransfer(Long idFileTransfer);

	/**
	 * Encontrar un registro de fileTransferMultidivisas por id
	 * @param idFileTransfer
	 * @return
	 */
	FileTransferDivisas findFileTransferByIdFileTransfer(Long idFileTransfer);

	boolean validaSaldoDisponiblePorParticipanteEnFT(Long idFileTransfer);
}
