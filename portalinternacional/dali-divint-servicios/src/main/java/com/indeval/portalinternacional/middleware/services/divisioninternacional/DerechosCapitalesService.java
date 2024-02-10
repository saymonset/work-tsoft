/**
 * Bursatec - Portal Internacional
 * Copyrigth (c) 2016 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.servicios.vo.ParamConsultaDetalleEjerDerCapTO;

/**
 * Interfaz de negocio que define los métodos para la consulta de los derechos de capitales.
 * 
 * @author Pablo Balderas
 */
public interface DerechosCapitalesService {

	/**
	 * Obtine el detalle de los derechos de capitales.
	 * @param paginaVO Objeto para la paginación.
	 * @param parametros Parámetros de búsqueda.
	 * @param esExportacion Indica si es una exportación
        @param debeDejarLog SE COBRA ESTA CONSULTA
	 * @return Objeto con la información de la paginación y los resultados de la consulta.
	 * @throws BusinessException En caso de ocurrir un error de negocio.
	 */
	PaginaVO consultaDetalleDerechosCapital(
		PaginaVO paginaVO, ParamConsultaDetalleEjerDerCapTO parametros, Boolean esExportacion, Boolean debeDejarLog) throws BusinessException;
	
	/**
	 * Obtine el detalle de los derechos de capitales por cuenta.
	 * @param paginaVO Objeto para la paginación.
	 * @param parametros Parámetros de búsqueda.
	 * @param esExportacion Indica si es una exportación
        @param debeDejarLog SE COBRA ESTA CONSULTA
	 * @return Objeto con la información de la paginación y los resultados de la consulta.
	 * @throws BusinessException En caso de ocurrir un error de negocio.
	 */
	PaginaVO consultaDetalleDerechosCapitalCuenta(PaginaVO paginaVO, ParamConsultaDetalleEjerDerCapTO parametros, Boolean esExportacion, Boolean debeDejarLog) throws BusinessException;
}
