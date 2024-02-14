/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.services.exito.divisioninternacional.filetransfer;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.servicios.vo.FileTransferVO;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 *
 */
@SuppressWarnings({"unchecked"})
public class UtilsFileTransfer {
	
    /**
     * Log de clase.
     */
	private static final Logger log = LoggerFactory.getLogger(UtilsFileTransfer.class);
    
    /**
     * @return FileTransferVO
     */
    public static FileTransferVO getFileTransferVO() {
    	
    	log.info("Entrando a UtilsFileTransfer.getFileTransferVO()");
    	
    	FileTransferVO fileTransferVO = new FileTransferVO();
    	
    	return fileTransferVO;
    }
    
    /**
     * @return FileTransferVO
     */
    public static FileTransferVO getFileTransferVO(AgenteVO agenteFirmado, String tipoProceso) {
    	
    	log.info("Entrando a UtilsFileTransfer.getFileTransferVO()");
    	
    	FileTransferVO fileTransferVO = new FileTransferVO();
    	fileTransferVO.setAgenteFirmado(agenteFirmado);
    	fileTransferVO.setPaginaVO(new PaginaVO());
    	fileTransferVO.setSoloErrores(true);
    	fileTransferVO.setTipoProceso(tipoProceso);
    	
    	return fileTransferVO;
    }
    
    /**
     * @return FileTransferVO
     */
    public static FileTransferVO getFileTransferVO(AgenteVO agenteFirmado, String tipoProceso, 
    		List informacion, int offset, String usuario) {
    	
    	log.info("Entrando a UtilsFileTransfer.getFileTransferVO()");
    	
    	FileTransferVO fileTransferVO = new FileTransferVO();
    	fileTransferVO.setAgenteFirmado(agenteFirmado);
    	
    	String[] informacionArchivo = new String[informacion.size()];
    	fileTransferVO.setInformacionArchivo((String[]) informacion.toArray(informacionArchivo));
    	fileTransferVO.setOffset(offset);
    	fileTransferVO.setPaginaVO(new PaginaVO());
    	fileTransferVO.setSoloErrores(true);
    	fileTransferVO.setTipoProceso(tipoProceso);
    	fileTransferVO.setNombreUsuario(usuario);
    	return fileTransferVO;
    }

}
