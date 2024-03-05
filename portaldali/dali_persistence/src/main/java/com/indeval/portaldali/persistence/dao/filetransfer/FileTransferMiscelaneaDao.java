/**
 * Bursatec - Portal DALI
 * Copyrigth (c) 2014 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.dao.filetransfer;

import java.util.List;

import com.bursatec.persistence.dao.BaseDao;
import com.indeval.portaldali.persistence.model.FileTransferMiscelanea;

/**
 * Interface que expone las consultas de file transfer de miscelanea fiscal.
 * 
 * @author Pablo Balderas.
 *
 */
public interface FileTransferMiscelaneaDao extends BaseDao {

	/**
	 * Obtiene los registros asociados a una institucion y proceso.
	 * @param idInst Id de la institución.
	 * @param folioInst Folio de la institución.
	 * @param tipoProceso Tipo de proceso.
	 * @return
	 */
	List<FileTransferMiscelanea> getByIdFolioTipo(final String idInst, final String folioInst, final String tipoProceso);
	
}
