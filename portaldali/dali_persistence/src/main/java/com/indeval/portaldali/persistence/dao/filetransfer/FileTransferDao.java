/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.dao.filetransfer;

import java.util.List;

import com.bursatec.persistence.dao.BaseDao;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.persistence.model.FileTransfer;
import com.indeval.portaldali.persistencia.fileTransfer.ArchivosFileTransfer;

/**
 * @author Jos&eacute; Avil&eacute;s
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public interface FileTransferDao extends BaseDao {

    /**
     * @param idInst
     * @param folioInst
     * @param tipoProceso
     * @return List
     */
    List<FileTransfer> getByIdFolioTipo(final String idInst, final String folioInst, final String tipoProceso);
        
    /**
     * Guarda en la base de datos el archivo de file transfer que se procesa.
     * @param archivosFileTransfer Objeto con los datos y archivo del file transfer.
     * @throws BusinessException En caso de ocurrir un error
     */
    void guardarArchivoFileTransfer(ArchivosFileTransfer archivosFileTransfer) throws BusinessException;
    
    
}
