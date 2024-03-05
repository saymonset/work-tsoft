/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.movimientosadministrativos;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bursatec.util.message.MessageResolver;
import com.indeval.portaldali.middleware.services.modelo.BusinessDataException;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.modelo.CatalogoService;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import com.indeval.portaldali.middleware.services.modelo.util.UtilsVOCatalogo;
import com.indeval.portaldali.middleware.services.movimientosadministrativos.util.Constantes;
import com.indeval.portaldali.middleware.services.util.UtilServices;
import com.indeval.portaldali.middleware.services.util.UtilsDaliVO;
import com.indeval.portaldali.middleware.services.util.ValidadorCuentasEmision;
import com.indeval.portaldali.persistence.model.CuentaNombrada;
import com.indeval.portaldali.persistence.vo.AgentePersistence;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 *
 */
public class TraspasosAdministrativosServiceImpl implements
	TraspasosAdministrativosService, Constantes {


    /** Log de clase. */
    private static final Logger logger = LoggerFactory.getLogger(TraspasosAdministrativosServiceImpl.class);

    /** Bean de acceso a catalogoService */
    private CatalogoService catalogoService;

    /** Bean para acceso al properties de mensaje de errores */
    private MessageResolver errorResolver;
    
    /** Bean de acceso a UtilService */
    private UtilServices utilService;

	/** Servicio de validacion para control de cuentas de emision */
	private ValidadorCuentasEmision validadorCuentasEmision;
    
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portaldali.middleware.services.movimientosadministrativos.TraspasosAdministrativosService#businessRulesTraspasosAdministrativos(com.indeval.portaldali.middleware.services.movimientosadministrativos.TraspasosAdministrativosParams)
	 */
	public BigInteger businessRulesTraspasosAdministrativos(TraspasosAdministrativosParams params) throws BusinessException {
		logger.info("Entrando a TraspasosAdministrativosServiceImpl." + "businessRulesTraspasosAdministrativos");
		// Valida parametros de entrada
		utilService.validaDTONoNulo(params, " params ");
		utilService.validaDTONoNulo(params.getCantidadTitulos(), " cantidad de titulos ");
		if (params.getCantidadTitulos().intValue() == 0) {
			throw new BusinessDataException(errorResolver.getMessage("JBR-996"), "JBR-996");
		}

		AgentePersistence traspasante = 
			UtilsVOCatalogo.getInstanceAgente(
				(CuentaNombrada) utilService.validaAgente(params.getTraspasante(), " traspasante ", true).getRegistros().get(0));
		AgentePersistence receptor = 
			UtilsVOCatalogo.getInstanceAgente(
				(CuentaNombrada) utilService.validaAgente(params.getReceptor(), " receptor ", true).getRegistros().get(0));

		//Valida que las cuentas sean diferentes cuando es el mismo traspasante y receptor
		if (UtilsDaliVO.getAgenteVO(traspasante).equals(UtilsDaliVO.getAgenteVO(receptor))) {
			throw new BusinessDataException(errorResolver.getMessage("J0056"), "J0056");
		}

		utilService.validaDTONoNulo(params.getEmision(), " emision ");
		params.getEmision().tienePKValida();

		// Valida que el instrumento corresponda al mercado indicado
		EmisionVO emisionVO = catalogoService.buscaInstrumento(params.getEmision());
		if (emisionVO.getMercado() != null) {
			if (params.isMercadoDinero() && emisionVO.getMercado().equalsIgnoreCase(Constantes.MERCADO_CAPITALES)) {
				throw new BusinessException(errorResolver.getMessage("J0077", new Object[] { "Dinero" }));
			}
			else if (!params.isMercadoDinero() && !emisionVO.getMercado().equalsIgnoreCase(Constantes.MERCADO_CAPITALES)) {
				throw new BusinessException(errorResolver.getMessage("J0077", new Object[] { "Capitales" }));
			}
		}
		else {
			throw new BusinessException("La emisi\u00f3n no es v\u00e1lida para los datos capturados");
		}

		//Valida que el tipo de cuenta inicie solo con PRO o TER para el trasp y recep
		validaTipoCta(traspasante, "traspasante");
		validaTipoCta(receptor, "receptor");

		//Realiza la validacion de control de cuentas de emision
		//Mayo/2018 - Pablo Balderas
		validadorCuentasEmision.validarCuentasEmisionMercadoDinero(params.getTraspasante(), params.getReceptor(), params.getEmision());
		
		// Se obtiene y retorna el folio dependiendo del mercado
		return obtieneFolio(params.isMercadoDinero());
	}
    
    /**
     * Valida el tipoCta del agente
     * @param agente
     * @throws BusinessDataException
     */
    private void validaTipoCta(AgentePersistence agente, String nombreTipoAgente) throws BusinessException{
    	
    	utilService.validarClaveTipo(agente.getTipoCta(), " tipoCta ");
    	
    	if (agente.getTipoCta().trim().length() < 3) {
    		throw new BusinessDataException(errorResolver.getMessage("J0000",
    				new Object[] { "Longitud incorrecta del campo tipoCta" }), "J0000");
    	}
    	
//    	if(!agente.getTipoCta().trim().startsWith(TIPO_CTA_PRO) && 
//    			!agente.getTipoCta().trim().startsWith(TIPO_CTA_TER) ){
//    		throw new BusinessDataException(errorResolver.getMessage("J0000",
//    				new Object[] { "El campo tipoCta del " + nombreTipoAgente + " solo puede iniciar " +
//    						"con PRO o TER" }), "J0000");
//    	}
    	
    }
    
    /**
     * @param isMercadoDinero
     * @return BigInteger
     * @throws BusinessException 
     * @throws NumberFormatException 
     */
    private BigInteger obtieneFolio(boolean isMercadoDinero) throws BusinessException {
    	
    	BigInteger folio = null;
    	
    	if (isMercadoDinero) {
    		Integer folioMD = Integer.valueOf(
    				utilService.getFolio(SECUENCIA_FOLIO_CONTROL).toString());		
    		folio = new BigInteger(folioMD != null ? folioMD.toString() : null);
    	} 
    	else {
    		BigDecimal folioMC = BigDecimal.valueOf(
    				utilService.getFolio(SECUENCIA_FOLIO_CONTROL).intValue());		
    		folio = new BigInteger(folioMC != null ?  folioMC.toString() : null);
    	}
    	
    	return folio;
    	
    }

    /**
	 * @param errorResolver
	 */
	public void setErrorResolver(MessageResolver errorResolver) {
		this.errorResolver = errorResolver;
	}

	/**
	 * @param utilService
	 */
	public void setUtilService(UtilServices utilService) {
		this.utilService = utilService;
	}

 /**
 * @param catalogoService
	 */
	public void setCatalogoService(CatalogoService catalogoService) {
		this.catalogoService = catalogoService;
	}

/**
 * Metodo para establecer el atributo validadorCuentasEmision
 * @param validadorCuentasEmision El valor del atributo validadorCuentasEmision a establecer.
 */
public void setValidadorCuentasEmision(ValidadorCuentasEmision validadorCuentasEmision) {
	this.validadorCuentasEmision = validadorCuentasEmision;
}

}
