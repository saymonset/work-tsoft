/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bursatec.util.message.MessageResolver;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.tesoreria.GetEdoCtaLiqParams;


/**
 * 
 * @author Sergio Mena
 *
 */
public class ValidaEstadoCuentaLiqServiceImpl implements
		ValidaEstadoCuentaLiqService {
	
	 /** Log de clase. */
    private Logger logger = LoggerFactory.getLogger(ValidaEstadoCuentaLiqServiceImpl.class);
    
    /** Resolvedor de Mensajes */
	private MessageResolver errorResolver;
	
	/** Bean de acceso a UtilService */
    private com.indeval.portaldali.middleware.services.util.UtilServices utilService;

	/**
	 * @see com.indeval.portaldali.middleware.services.util.ValidaEstadoCuentaLiqService
	 * #validaParamsEdoCuentaLiq(com.indeval.portaldali.middleware.services.tesoreria.GetEdoCtaLiqParams)
	 */
	public void validaParamsEdoCuentaLiq(GetEdoCtaLiqParams params) throws BusinessException {

		logger.info("Entrando al metodo ValidaEstadoCuentaLiqServiceImpl.validaParamsEdoCuentaLiq(params)");
		
		utilService.validaDTONoNulo(params.getInstitucionAutorizada(), "Institucion Autorizada");
		
		if(StringUtils.isBlank(params.getInstitucionAutorizada().toString())) {
			
			throw new BusinessException("Institucion Autorizada nula o vacia");
		}
		
		if(params.getInstitucionAutorizada().getId().equalsIgnoreCase("02") 
				&& params.getInstitucionAutorizada().getFolio().equalsIgnoreCase("033")) {
			
			if(params.getAgente().equals(params.getInstitucionAutorizada())) {
				
				throw new BusinessException("institucion autorizada y agente de consulta iguales");
			}
			if(params.getTipoConsulta().equalsIgnoreCase("PAGOS")) {

			    utilService.validaDTONoNulo(params.getEmision(), " emision ");

			    utilService.validaDTONoNulo(params.getEmision().getIdTipoValor(), " tv ");

			    if(!params.getEmision().getIdTipoValor().equalsIgnoreCase("F") &&
			            !params.getEmision().getIdTipoValor().equalsIgnoreCase("J") &&
			            !params.getEmision().getIdTipoValor().equalsIgnoreCase("G") &&
			            !params.getEmision().getIdTipoValor().equalsIgnoreCase("I") ) {

			        throw new BusinessException("TV debe ser F, J, G \u00f3 I");
			    }

			}
			else  {
			    throw new BusinessException("El Tipo de consulta no es PAGOS");
			}
		}
		else {
			throw new BusinessException(errorResolver.getMessage("J0053", 
					new Object[]{params.getInstitucionAutorizada()}),"J0053");
		}
		
		
	}

	/**
	 * @param errorResolver the errorResolver to set
	 */
	public void setErrorResolver(MessageResolver errorResolver) {
		this.errorResolver = errorResolver;
	}

	/**
	 * @param utilService the utilService to set
	 */
	public void setUtilService(
			com.indeval.portaldali.middleware.services.util.UtilServices utilService) {
		this.utilService = utilService;
	}
	
}
