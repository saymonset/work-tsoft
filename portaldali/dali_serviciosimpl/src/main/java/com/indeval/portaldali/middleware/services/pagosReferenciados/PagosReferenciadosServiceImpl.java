/**
 * CMMV - Portal Dali
 * Copyrigth (c) 2017 CMMV. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.pagosReferenciados;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.modelo.PaginaVO;
import com.indeval.portaldali.modelo.to.pagosReferenciados.ParamConsultaBitacoraPagoReferenciado;
import com.indeval.portaldali.persistence.dao.pagosReferenciados.PagoReferenciadoDao;

/**
 * Implementacion de la interfaz de negocio PagosReferenciadosService.
 * 
 * @author Pablo Balderas.
 */
public class PagosReferenciadosServiceImpl implements PagosReferenciadosService {

	/** Dao par realizar las consultas */
	private PagoReferenciadoDao pagoReferenciadoDao;
	
	/** Constante de Serializacion */
	private static Logger logger = LoggerFactory.getLogger(PagosReferenciadosServiceImpl.class);
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portaldali.middleware.services.pagosReferenciados.PagosReferenciadosService#findBitacoraPagosReferenciados(boolean, com.indeval.portaldali.middleware.services.modelo.PaginaVO, com.indeval.portaldali.modelo.to.pagosReferenciados.ParamConsultaBitacoraPagoReferenciado)
	 */
	public PaginaVO findBitacoraPagosReferenciados(boolean esExportacion, PaginaVO paginaVO,
			ParamConsultaBitacoraPagoReferenciado parametrosBusqueda) throws BusinessException {
		try {			
			return pagoReferenciadoDao.findBitacoraPagosReferenciados(esExportacion, paginaVO, parametrosBusqueda);
		}
		catch(Exception e) {
			logger.debug("==========Error PagosReferenciadosServiceImpl==========", e);
			throw new BusinessException(e.getMessage(), e);
		}
	}

	/**
	 * Método para establecer el atributo pagoReferenciadoDao
	 * @param pagoReferenciadoDao El valor del atributo pagoReferenciadoDao a establecer.
	 */
	public void setPagoReferenciadoDao(PagoReferenciadoDao pagoReferenciadoDao) {
		this.pagoReferenciadoDao = pagoReferenciadoDao;
	}

}
