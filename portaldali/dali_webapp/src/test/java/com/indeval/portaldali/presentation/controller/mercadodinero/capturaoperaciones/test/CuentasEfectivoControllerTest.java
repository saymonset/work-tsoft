/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * Apr 30, 2008
 */
package com.indeval.portaldali.presentation.controller.mercadodinero.capturaoperaciones.test;

import java.util.Map;

import javax.faces.context.FacesContext;

import org.apache.commons.lang.StringUtils;

import com.indeval.portaldali.middleware.dto.CuentaEfectivoDTO;
import com.indeval.portaldali.middleware.dto.InstitucionDTO;
import com.indeval.portaldali.middleware.dto.TipoCuentaDTO;
import com.indeval.portaldali.middleware.dto.TipoNaturalezaDTO;
import com.indeval.portaldali.middleware.services.common.constants.TipoCuentaConstants;
import com.indeval.portaldali.middleware.services.common.constants.TipoCustodiaConstants;
import com.indeval.portaldali.presentation.common.BaseWebTestCase;
import com.indeval.portaldali.presentation.util.ConsultaCatalogosFacade;

/**
 * Controller de prueba para verificar el funcionamiento del servicio para
 * consultar las cuentas de tipo Efectivo Nombrada, que se usara en la pantalla
 * de Traspaso de efectivo entre cuentas propias.
 * 
 * @author Juan Carlos Huizar Moreno
 * 
 */
public class CuentasEfectivoControllerTest extends BaseWebTestCase {

	/**
	 * Acceso a la consulta de catálogos.
	 */
	ConsultaCatalogosFacade consultaCatalogos = null;

	/**
	 * método que atiende las solicitudes del catálogo de cuentas tipo Efectivo
	 * Nombrada.
	 * 
	 * @param prefijo
	 *            Criterio de búsqueda
	 * @return Lista con las cuentas
	 */
	public CuentaEfectivoDTO buscarCuentaReceptor(Object prefijo) {
		CuentaEfectivoDTO criterio = new CuentaEfectivoDTO();
		CuentaEfectivoDTO resultado = new CuentaEfectivoDTO();
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

		InstitucionDTO institucion = null;
		criterio.setTipoCustodia(TipoCustodiaConstants.EFECTIVO);
		criterio.setTipoCustodia(TipoCustodiaConstants.EFECTIVO);
		criterio.setTipoCuenta(new TipoCuentaDTO(TipoCuentaConstants.TIPO_NOMBRADA, ""));
		criterio.setTipoNaturaleza(new TipoNaturalezaDTO(TipoNaturalezaDTO.PASIVO, ""));

		if (!StringUtils.isEmpty(params.get("nombre_id_folio_receptor")) && !StringUtils.isEmpty(params.get(params.get("nombre_id_folio_receptor")))) {
			institucion = consultaCatalogos.buscarInstitucionPorIdFolio(params.get(params.get("nombre_id_folio_receptor")));
		}
		if (institucion != null) {
			criterio.setInstitucion(institucion);
			criterio.setNumeroCuenta(institucion.getClaveTipoInstitucion() + institucion.getFolioInstitucion());
			resultado = consultaCatalogos.getConsultaCuentaService().buscarCuentaEfectivoPorNumeroCuenta(criterio);
		}
		return resultado;

	}

}
