/**
 * Sistema Portal DALI
 * 
 * Bursatec - 2H Software SA de CV
 *
 * Creado: Oct 24, 2008
 */
package com.indeval.portaldali.presentation.controller.bitacoramatch;

import java.util.List;

import javax.faces.context.FacesContext;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.indeval.portaldali.middleware.dto.OperacionValorMatchDTO;
import com.indeval.portaldali.middleware.services.estatus.ConsultaEstatusOperacionesMatchService;
import com.indeval.portaldali.presentation.controller.common.ControllerBase;

/**
 * Backing Bean para la consulta del detalle de una operación de miscelanea
 * fiscal.
 * 
 * @author José Antonio Huizar Moreno
 * @version 1.0
 * 
 */
public class DetalleMiscelaneaFiscalBean extends ControllerBase {

	/**
	 * Detalle de la operacion a mostrar
	 */
	private OperacionValorMatchDTO operacion = null;

	/**
	 * Servicio de consulta de las operaciones
	 */
	private ConsultaEstatusOperacionesMatchService consultaEstatusOperacionesMatchService = null;

	/**
	 * método de inicialización de propiedades
	 * 
	 * @return null
	 */
	public String getInit() {
		FacesContext ctx = FacesContext.getCurrentInstance();

		String folio = ctx.getExternalContext().getRequestParameterMap().get("folioInstruccionLiquidacion");
		String idBitacoraMatch = ctx.getExternalContext().getRequestParameterMap().get("idBitacoraMatch");
		List<OperacionValorMatchDTO> operaciones = null;
		if(StringUtils.isNotBlank(folio)) {
			operaciones = consultaEstatusOperacionesMatchService.consultarInstruccionOperacionValorPorId(NumberUtils.toLong(folio));
		}
		else if(StringUtils.isNotBlank(idBitacoraMatch)) {
			operaciones = consultaEstatusOperacionesMatchService.consultarInstruccionMatchoPorId(NumberUtils.toLong(idBitacoraMatch));
		}
		if (operaciones != null && operaciones.size() > 0) {
			operacion = operaciones.get(0);
		}

		return null;
	}

	/**
	 * Obtiene el valor del atributo operacion
	 * 
	 * @return el valor del atributo operacion
	 */
	public OperacionValorMatchDTO getOperacion() {
		return operacion;
	}

	/**
	 * Establece el valor del atributo operacion
	 * 
	 * @param operacion
	 *            el valor del atributo operacion a establecer
	 */
	public void setOperacion(OperacionValorMatchDTO operacion) {
		this.operacion = operacion;
	}

	/**
	 * Obtiene el valor del atributo consultaEstatusOperacionesMatchService
	 * 
	 * @return el valor del atributo consultaEstatusOperacionesMatchService
	 */
	public ConsultaEstatusOperacionesMatchService getConsultaEstatusOperacionesMatchService() {
		return consultaEstatusOperacionesMatchService;
	}

	/**
	 * Establece el valor del atributo consultaEstatusOperacionesMatchService
	 * 
	 * @param consultaEstatusOperacionesMatchService
	 *            el valor del atributo consultaEstatusOperacionesMatchService a
	 *            establecer
	 */
	public void setConsultaEstatusOperacionesMatchService(ConsultaEstatusOperacionesMatchService consultaEstatusOperacionesMatchService) {
		this.consultaEstatusOperacionesMatchService = consultaEstatusOperacionesMatchService;
	}

}
