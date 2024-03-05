/**
 * CMMV - Portal Dali
 * Copyrigth (c) 2017 CMMV. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.pagosReferenciados;

import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.modelo.PaginaVO;
import com.indeval.portaldali.modelo.to.pagosReferenciados.ParamConsultaBitacoraPagoReferenciado;

/**
 * Interfaz de negocio que expone los servicios para la consulta de la bitacora
 * de pagos referenciados.
 * 
 * @author Pablo Balderas
 */
public interface PagosReferenciadosService {

	/**
	 * Metodo que busca las entradas de la bitacora de pagos referenciados por folio preliquidador.
	 * @param esExportacion Indica si es una exportacion
	 * @param paginaVO Objeto para la paginacion.
	 * @param parametrosBusqueda Parametros de busqueda.
	 * @return Objeto con la paginacion y los resultados.
	 * @throws BusinessException En caso de ocurrir un error
	 */
	PaginaVO findBitacoraPagosReferenciados(boolean esExportacion, PaginaVO paginaVO,
		ParamConsultaBitacoraPagoReferenciado parametrosBusqueda) throws BusinessException;

}
