/**
 * Bursatec - Portal Internacional
 * Copyrigth (c) 2016 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.services.validador;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.bursatec.util.message.MessageResolver;
import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portalinternacional.middleware.servicios.constantes.CatalogosConstantes;
import com.indeval.portalinternacional.middleware.servicios.constantes.ValidacionesConstantes;
import com.indeval.portalinternacional.middleware.servicios.vo.ParamConsultaDetalleEjerDerCapTO;

/**
 * Implementación de la interfaz ValidadorDerechosCapital
 * 
 * @author Pablo Balderas
 */
public class ValidadorDerechosCapitalImpl implements ValidadorDerechosCapital {

	/** Resolvedor de Mensajes */
    private MessageResolver messageResolver;
	
    /*
     * (non-Javadoc)
     * @see com.indeval.portalinternacional.middleware.services.validador.ValidadorDerechosCapital#validarParametrosConsultaDerechosBeneficiarios(com.indeval.portalinternacional.middleware.servicios.vo.ParamConsultaDetalleEjerDerCapTO)
     */
    public void validarParametrosConsultaDerechosBeneficiarios(ParamConsultaDetalleEjerDerCapTO parametros) throws BusinessException {
    	boolean paramBeneficiario = StringUtils.isBlank(parametros.getNombreBeneficiario());
    	boolean paramRFC = StringUtils.isBlank(parametros.getRfc());
    	boolean paramMontos = parametros.getMontoBruto() == null && parametros.getMontoNeto() == null &&
    		parametros.getCantidadTitulos() == null && parametros.getImpuestoRetenido() == null;
    	boolean paramUOIPaisRefCustodio = 
    		StringUtils.isBlank(parametros.getUoi()) &&
    		StringUtils.isBlank(parametros.getPais()) &&
    		StringUtils.isBlank(parametros.getReferenciaCustodio());	
    	boolean paramTipoBeneficiarioTipoFormato = !validarId(parametros.getIdTipoBeneficiario()) &&
    		StringUtils.isBlank(parametros.getTipoFormato());
    	boolean paramGiinEstadoFormWReferenceNumber =
    		StringUtils.isBlank(parametros.getGiin()) &&
    		StringUtils.isBlank(parametros.getEstadoFormatoW()) &&
    		StringUtils.isBlank(parametros.getReferenceNumber());
    	if(validarParametrosComunes(parametros) && paramBeneficiario && paramRFC && paramMontos &&
    			paramUOIPaisRefCustodio && paramTipoBeneficiarioTipoFormato && paramGiinEstadoFormWReferenceNumber) {
			throw new BusinessException(messageResolver.getMessage("errorParametroObligatorio"));
		}
    	validarParametros(parametros);
		validarRangoFechas(parametros);
    }

    /* (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.validador.ValidadorDerechosCapital#validarParametrosBusqueda(com.indeval.portalinternacional.middleware.servicios.vo.ParamConsultaDetalleEjerDerCapTO)
	 */
	public void validarParametrosConsultaDerechosCuenta(ParamConsultaDetalleEjerDerCapTO parametros) throws BusinessException {
		boolean paramFee = parametros.getFee() == null;
		boolean paramMontos = parametros.getMontoBruto() == null &&
			parametros.getMontoFee() == null &&
			parametros.getImpuestoRetenido() == null &&
			parametros.getMontoNeto() == null;
		if(validarParametrosComunes(parametros) && paramFee && paramMontos) {
			throw new BusinessException(messageResolver.getMessage("errorParametroObligatorio"));
		}
		validarParametros(parametros);
		validarRangoFechas(parametros);
	}

	/**
	 * Valida que al menos un parámetro de búsqueda haya sido capturado.
	 * @param parametros Parametros a validar.
	 * @throws BusinessException En caso de error.
	 */
	private boolean validarParametrosComunes(ParamConsultaDetalleEjerDerCapTO parametros) throws BusinessException {
		boolean parametrosEmision = StringUtils.isBlank(parametros.getTv()) && StringUtils.isBlank(parametros.getEmisora()) &&
			StringUtils.isBlank(parametros.getSerie()) && StringUtils.isBlank(parametros.getIsin());
		boolean parametrosInstitucionCuenta = StringUtils.isBlank(parametros.getInstitucion()) && StringUtils.isBlank(parametros.getCuenta());
		boolean parametrosFecha = parametros.getFechaCorteInicial() == null && parametros.getFechaCorteFinal() == null &&
			parametros.getFechaPagoInicial() == null && parametros.getFechaPagoFinal() == null;
		boolean parametroDivisa = !validarId(parametros.getIdDivisa());
		boolean parametroCustodio = !validarId(parametros.getIdCustodio());
		boolean paramPorcentajeRetencionProporcion = parametros.getPorcentajeRetencion() == null && parametros.getProporcion() == null;
		return parametrosEmision && parametrosInstitucionCuenta && parametrosFecha && 
			parametroDivisa && parametroCustodio && paramPorcentajeRetencionProporcion;
	}
	
	/**
	 * Valida los paramatros de busqueda.
	 * @param parametros Objeto a validar.
	 * @throws BusinessException En caso de error.
	 */
	private void validarParametros(ParamConsultaDetalleEjerDerCapTO parametros) throws BusinessException {
		boolean validos = true;
		if(StringUtils.isNotBlank(parametros.getTv())) {			
			validos = validos && validarExpresionRegular(parametros.getTv(), ValidacionesConstantes.LONGITUD_4, ValidacionesConstantes.ER_ALFANUMERICOS);
		}
		if(StringUtils.isNotBlank(parametros.getEmisora())) {			
			validos = validos && validarExpresionRegular(parametros.getEmisora(), ValidacionesConstantes.LONGITUD_7, ValidacionesConstantes.ER_EMISORA);
		}
		if(StringUtils.isNotBlank(parametros.getSerie())) {			
			validos = validos && validarExpresionRegular(parametros.getSerie(), ValidacionesConstantes.LONGITUD_6, ValidacionesConstantes.ER_SERIE);
		}
		if(StringUtils.isNotBlank(parametros.getIsin())) {			
			validos = validos && validarExpresionRegular(parametros.getIsin(), ValidacionesConstantes.LONGITUD_12, ValidacionesConstantes.ER_ALFANUMERICOS);
		}
		if(StringUtils.isNotBlank(parametros.getInstitucion())) {			
			validos = validos && validarExpresionRegular(parametros.getInstitucion(), ValidacionesConstantes.LONGITUD_5, ValidacionesConstantes.ER_NUMERICOS);
		}
		if(StringUtils.isNotBlank(parametros.getCuenta())) {			
			validos = validos && validarExpresionRegular(parametros.getCuenta(), ValidacionesConstantes.LONGITUD_4, ValidacionesConstantes.ER_NUMERICOS);
		}
		if(StringUtils.isNotBlank(parametros.getPorcentajeRetencion())) {
		    validos = validos && validarExpresionRegular(parametros.getPorcentajeRetencion(), ValidacionesConstantes.LONGITUD_6, ValidacionesConstantes.ER_DECIMALES);
		}
		if (StringUtils.isNotBlank(parametros.getRfc())) {
		    validos = validos && validarExpresionRegularSinLongitud(parametros.getRfc(), ValidacionesConstantes.ER_ALFANUMERICO_ESPACIO_GUIONES_PUNTO_DIAGONAL);
		}

        if (StringUtils.isNotBlank(parametros.getProporcion())) {
            validos = validos && validarExpresionRegularSinLongitud(parametros.getProporcion(), ValidacionesConstantes.ER_DECIMALES);
        }
        if (StringUtils.isNotBlank(parametros.getUoi())) {
            validos = validos && validarExpresionRegularSinLongitud(parametros.getUoi(), ValidacionesConstantes.ER_ALFANUMERICO_ESPACIO_GUIONES_PUNTO_DIAGONAL);
        }
        if (StringUtils.isNotBlank(parametros.getCantidadTitulos())) {
        	validos = validos && validarExpresionRegular(parametros.getCantidadTitulos(), ValidacionesConstantes.LONGITUD_18, ValidacionesConstantes.ER_NUMERICOS);
        }
        if (StringUtils.isNotBlank(parametros.getReferenciaCustodio())) {
            validos = validos && validarExpresionRegularSinLongitud(parametros.getReferenciaCustodio(), ValidacionesConstantes.ER_ALFANUMERICOS);
        }
        //Monto bruto, monto fee, impuesto retenido y monto neto
		if (StringUtils.isNotBlank(parametros.getMontoBruto())) {
		    validos = validos && validarExpresionRegularSinLongitud(parametros.getMontoBruto(), ValidacionesConstantes.ER_DECIMALES);
		}
		if(StringUtils.isNotBlank(parametros.getMontoFee())) {
			validos = validos && validarExpresionRegularSinLongitud(parametros.getMontoFee(), ValidacionesConstantes.ER_DECIMALES);
		}
		if (StringUtils.isNotBlank(parametros.getImpuestoRetenido())) {
			validos = validos && validarExpresionRegularSinLongitud(parametros.getImpuestoRetenido(), ValidacionesConstantes.ER_DECIMALES);
		}
        if (StringUtils.isNotBlank(parametros.getMontoNeto())) {
            validos = validos && validarExpresionRegularSinLongitud(parametros.getMontoNeto(), ValidacionesConstantes.ER_DECIMALES);
        }
        if(StringUtils.isNotBlank(parametros.getFee())) {
        	validos = validos && validarExpresionRegularSinLongitud(parametros.getFee(), ValidacionesConstantes.ER_DECIMALES);
        }
		if(!validos) {
			throw new BusinessException(messageResolver.getMessage("errorCriteriosBusqueda"));
		}
	}
	
	/**
	 * Valida los rangos de las fechas
	 * @param parametros Objeto con las fechas
	 */
	private void validarRangoFechas(ParamConsultaDetalleEjerDerCapTO parametros) throws BusinessException {
		if(parametros.getFechaCorteInicial() != null && parametros.getFechaCorteFinal() != null) {
			if(parametros.getFechaCorteInicial().compareTo(parametros.getFechaCorteFinal()) > 0) {
				throw new BusinessException(messageResolver.getMessage("errorFechaInicialMayorFechaFinal"));
			}
			else if(calcularDiferenciaFechasEnDias(parametros.getFechaCorteInicial(), parametros.getFechaCorteFinal()) > 90) {
				throw new BusinessException(messageResolver.getMessage("errorRangoFechas"));
			}
		}
		if(parametros.getFechaPagoInicial() != null && parametros.getFechaPagoFinal() != null) {
			if(parametros.getFechaPagoInicial().compareTo(parametros.getFechaPagoFinal()) > 0) {
				throw new BusinessException(messageResolver.getMessage("errorFechaInicialMayorFechaFinal"));
			}
			if(calcularDiferenciaFechasEnDias(parametros.getFechaPagoInicial(), parametros.getFechaPagoFinal()) > 90) {
				throw new BusinessException(messageResolver.getMessage("errorRangoFechas"));
			}
		}
	}
	
	/**
	 * Calcula la diferencia en días entre dos fechas
	 * @param fechaInicial
	 * @param fechaFinal
	 * @return Diferencia en días.
	 */
	private int calcularDiferenciaFechasEnDias(Date fechaInicial, Date fechaFinal) {
		Long diferencia = fechaFinal.getTime() - fechaInicial.getTime();
		Long difDias = diferencia / (24 * 60 * 60 * 1000);
		return difDias.intValue();
	}
	
	/**
	 * Valida si el id.
	 * @param id Id a validar
	 * @return true si el id es distinto de nulo y de -1; false en caso contrario
	 */
	private boolean validarId(Long id) {
		return id == null || !CatalogosConstantes.VALOR_TODOS.equals(id);
	}
	
	/**
	 * Método que valida que una cadena cumpla con longitud y una expresión regular.
	 * @param cadena Cadena a validar
	 * @param longitud Longitud a validar
	 * @param expresionRegular Expresion regular que debe cumplir
	 * @return true si cumple todo; false en caso de fallar
	 */
	private boolean validarExpresionRegular(String cadena, int longitud, String expresionRegular) {
		return cadena.length() <= longitud && cadena.matches(expresionRegular);
	}

    /**
     * Método que valida que una cadena cumpla una expresión regular.
     * @param cadena Cadena a validar
     * @param expresionRegular Expresion regular que debe cumplir
     * @return true si cumple todo; false en caso de fallar
     */
    private boolean validarExpresionRegularSinLongitud(String cadena, String expresionRegular) {
        return cadena.matches(expresionRegular);
    }

	/**
	 * Método para establecer el atributo messageResolver
	 * @param messageResolver El valor del atributo messageResolver a establecer.
	 */
	public void setMessageResolver(MessageResolver messageResolver) {
		this.messageResolver = messageResolver;
	}

}
