/**
 * Bursatec - Portal Dali
 * Copyrigth (c) 2015 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.aperturaSistema;

import java.math.BigInteger;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bursatec.util.message.MessageResolver;
import com.indeval.portaldali.middleware.services.common.ValidacionService;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.modelo.BusinessRulesMercadoDineroService;
import com.indeval.portaldali.middleware.services.util.UtilServices;
import com.indeval.portaldali.middleware.services.util.util.Constantes;

/**
 * Implementación de la interfaz de negocio AperturaSistemaService.
 * 
 * @author Pablo Balderas
 */
public class AperturaSistemaServiceImpl implements AperturaSistemaService {
	
	/** Log de clase. */
	private static final Logger log = LoggerFactory.getLogger(AperturaSistemaServiceImpl.class);
	
	/** Servicio de utilerias comunes */
	private UtilServices utilService = null;
	
	/** Servicio de validaciones */
	private ValidacionService validacionService =  null;
	
	/** Bean de acceso a BusinessRulesMercadoDineroService */
	private BusinessRulesMercadoDineroService businessRulesMercadoDineroService = null;
	
	/** Bean para acceso al properties de mensaje de errores */
	private MessageResolver messageResolver = null;

	/* (non-Javadoc)
	 * @see com.indeval.portaldali.middleware.services.aperturaSistema.AperturaSistemaService#validarReglasNegocioAperturaSistemas()
	 */
	public BigInteger validarObtenerSecuencia(AperturaSistemasParametros aperturaSistemasParametros) throws BusinessException {

		log.info("Inicializa método validarObtenerSecuencia()");

		/* Se valida el objeto de parametros */
		utilService.validaDTONoNulo(aperturaSistemasParametros, "");

		/* Se valida el agente traspasante */
		utilService.validaDTONoNulo(aperturaSistemasParametros.getTraspasante(),
				" agente traspasante ");
		aperturaSistemasParametros.getTraspasante().tieneClaveValida();
		utilService.isAgenteRegistrado(aperturaSistemasParametros.getTraspasante());

		/* Se valida el agente receptor */
		utilService.validaDTONoNulo(aperturaSistemasParametros.getReceptor(), " agente receptor ");
		aperturaSistemasParametros.getReceptor().tieneClaveValida();
		utilService.isAgenteRegistrado(aperturaSistemasParametros.getReceptor());

		// Valida que no se use la misma cuenta en los cruces
		if (aperturaSistemasParametros.getTraspasante().equals(aperturaSistemasParametros.getReceptor())) {
			throw new BusinessException(messageResolver.getMessage("J0066"));
		}
		
		//Se realiza la validación del RFC/CURP
		if(StringUtils.isNotBlank(aperturaSistemasParametros.getRfcCURP())) {
			if(!validacionService.validarRfcCurp(aperturaSistemasParametros.getRfcCURP())) {
				throw new BusinessException(messageResolver.getMessage("errorRfcCurp"));
			}
		}
		
		
		/* Para el mercado de dinero no se valida la emisión.
		 * Se valida que el cupón sea valido para la emisión
		if(!utilService.isCuponValidoParaEmision(params.getEmision())) {
			throw new BusinessException("La emisi\u00F3n no es v\u00E1lida para los datos capturados");
		}
		*/

		/* Se valida el formato de la cantidad operada */
//		utilService.validaNumber(params.getCantidad(), 
//		        patternResolver.getMessage("cantidad"));

		/* Se valida el tipo de movimiento */
		aperturaSistemasParametros.validaTipoMovimiento();


		/* Se valida la emision */
		utilService.validaDTONoNulo(aperturaSistemasParametros.getEmision(), " emision ");
		aperturaSistemasParametros.getEmision().tienePKValida();

		/*
		 * Se recupera el valor del mercado tanto para emisiones de dinero como
		 * para las de capitales
		 */
		
		//TODO
//		EmisionPersistence emision = this.buscaInstrumento(UtilsDaliVO.getEmision(aperturaSistemasParametros.getEmision()));
//		String mercado = emision.getInstrumento().getMercado();
//		
//		
//		if(AperturaSistemasConstantes.MERCADO_DINERO.equals(aperturaSistemasParametros.getMercado()) &&
//				!(com.indeval.portaldali.persistence.util.Constantes.PAPEL_BANCARIO.equalsIgnoreCase(mercado) ||
//						com.indeval.portaldali.persistence.util.Constantes.PAPEL_GUBERNAMENTAL.equalsIgnoreCase(mercado))) {
//			throw new BusinessException(messageResolver.getMessage("J0110"));
//		}
//		else if(AperturaSistemasConstantes.MERCADO_CAPITALES.equals(aperturaSistemasParametros.getMercado()) &&
//				com.indeval.portaldali.persistence.util.Constantes.PAPELES_DINERO.equalsIgnoreCase(mercado)) {
//			throw new BusinessException(messageResolver.getMessage("J0074"));
//		}


//		/* Se obtiene la fecha de liquidacion */
//		Date fechaLiquidacion = dateUtilsDao.getFechaHoraCero(dateUtilsDao
//				.getDateFechaDB());
//
//		/* Se obtiene el FolioLlave */
//		String llave = utilService.getLlaveFolio();

//		/*
//		 * Si es Apertura de Sistema utiliza... bddinero..UP_altamdin - >
//		 * BusinessRulesMercadoDineroService.altaMdintran ()
//		 * 
//		 * Si es Miscelanea Fiscal utiliza... catalogo..UP_traslp - >
//		 * BusinessRulesCatalogoService.registraTraspasosYCompensaValores()
//		 */
//		if (StringUtils.isNotBlank(aperturaSistemasParametros.getTipoMovimiento())
//				&& aperturaSistemasParametros.getTipoMovimiento().equalsIgnoreCase(APERTURA)) {
//
//
//		}
		
//		AltaMdintranParams altaMdintranParams = getInstanceAltaMdintranParams(
//				aperturaSistemasParametros, llave, mercado, fechaLiquidacion);
//		businessRulesMercadoDineroService.altaMdintran(altaMdintranParams);

		return utilService.getFolio(Constantes.SECUENCIA_FOLIO_CONTROL);
	}


	/**
	 * Método para establecer el atributo utilService
	 * @param utilService El valor del atributo utilService a establecer.
	 */
	public void setUtilService(UtilServices utilService) {
		this.utilService = utilService;
	}


	/**
	 * Método para establecer el atributo messageResolver
	 * @param messageResolver El valor del atributo messageResolver a establecer.
	 */
	public void setMessageResolver(MessageResolver messageResolver) {
		this.messageResolver = messageResolver;
	}


	/**
	 * Método para establecer el atributo validacionService
	 * @param validacionService El valor del atributo validacionService a establecer.
	 */
	public void setValidacionService(ValidacionService validacionService) {
		this.validacionService = validacionService;
	}


	/**
	 * Método para establecer el atributo businessRulesMercadoDineroService
	 * @param businessRulesMercadoDineroService El valor del atributo businessRulesMercadoDineroService a establecer.
	 */
	public void setBusinessRulesMercadoDineroService(
			BusinessRulesMercadoDineroService businessRulesMercadoDineroService) {
		this.businessRulesMercadoDineroService = businessRulesMercadoDineroService;
	}

}
