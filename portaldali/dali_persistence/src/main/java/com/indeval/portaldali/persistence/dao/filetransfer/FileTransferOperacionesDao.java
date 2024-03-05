/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.dao.filetransfer;

import java.util.List;

import com.bursatec.persistence.dao.BaseDao;


/**
 * @author Jos&eacute; Avil&eacute;s
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public interface FileTransferOperacionesDao extends BaseDao {
	/**
     * @param idInst
     * @param folioInst
     * @param tipoProceso
     * @return List
     */
    @SuppressWarnings("unchecked")
	List getByIdFolioTipo(final String idInst, final String folioInst, final String tipoProceso);
}
