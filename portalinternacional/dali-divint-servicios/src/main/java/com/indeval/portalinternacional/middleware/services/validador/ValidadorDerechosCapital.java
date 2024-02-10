/**
 * Bursatec - Portal Internacional
 * Copyrigth (c) 2016 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.services.validador;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portalinternacional.middleware.servicios.vo.ParamConsultaDetalleEjerDerCapTO;

/**
 * Interfaz que expone los servicios de validación para los derechos de capital.
 * 
 * @author Pablo Balderas
 */
public interface ValidadorDerechosCapital {

	/**
	 * Valida que los parámetros de busqueda sean validos.
	 * @param parametros Parametros a validar.
	 * @throws BusinessException En caso de error o no cumplir las reglas de negocio.
	 */
	void validarParametrosConsultaDerechosCuenta(ParamConsultaDetalleEjerDerCapTO parametros) throws BusinessException;
	
	
	/**
	 * Valida que los paŕametros de busqueda de la Consulta Derechos Beneficiarios sean correctos.
	 * @param parametros Parametros a validar.
	 * @throws BusinessException En caso de error o no cumplir las reglas de negocio.
	 */
	void validarParametrosConsultaDerechosBeneficiarios(ParamConsultaDetalleEjerDerCapTO parametros) throws BusinessException;
	
}
