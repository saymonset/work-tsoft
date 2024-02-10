package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.util.ValidatorService;
import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.middleware.servicios.util.UtilsVO;
import com.indeval.portalinternacional.middleware.servicios.modelo.FileTransferDivisas;
import com.indeval.portalinternacional.middleware.servicios.vo.BitacoraFileTransferVO;
import com.indeval.portalinternacional.persistence.dao.BitacoraFileTransferDao;

public class BitacoraFileTransferServiceImpl implements BitacoraFileTransferService {

	/** Para el registro de log de esta clase */
	private static final Logger log = LoggerFactory.getLogger(BitacoraFileTransferServiceImpl.class);
	
	/** Bean de acceso a ValidatorDivInt */
    private ValidatorService validatorService;
	private BitacoraFileTransferDao bitacoraFileTransferDao;
	
	@Override
	public PaginaVO consultarFileTransfer(BitacoraFileTransferVO parametros, PaginaVO paginaVO, Boolean obtenerTotalRegistros) throws BusinessException {
		
		log.info("Entrando a consultarFileTransfer()");
		
        final List listaFileTransfer = new ArrayList<FileTransferDivisas>();
        paginaVO = UtilsVO.getPaginaNotBlank(paginaVO);
        paginaVO = this.bitacoraFileTransferDao.findFileTransfer(parametros, paginaVO, obtenerTotalRegistros);
        
        if (!this.validatorService.validaPagina(paginaVO))
        	paginaVO.setRegistros(new ArrayList<FileTransferDivisas>());
        else {
        	
        	for (final Iterator iter = paginaVO.getRegistros().iterator(); iter.hasNext();) {
        		
        		final FileTransferDivisas fileTransfer = (FileTransferDivisas) iter.next();
        		listaFileTransfer.add(fileTransfer);
        		
        	}
       
        }
        
        paginaVO.setRegistros(listaFileTransfer);
		
		return paginaVO;
		
	}

	public ValidatorService getValidatorService() {
		return validatorService;
	}

	public void setValidatorService(ValidatorService validatorService) {
		this.validatorService = validatorService;
	}

	public BitacoraFileTransferDao getBitacoraFileTransferDao() {
		return bitacoraFileTransferDao;
	}

	public void setBitacoraFileTransferDao(BitacoraFileTransferDao bitacoraFileTransferDao) {
		this.bitacoraFileTransferDao = bitacoraFileTransferDao;
	}
	
}
