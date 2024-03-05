/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.tesoreria;

import org.springframework.validation.Errors;

import com.indeval.sidv.decretos.persistence.model.vo.AgenteVO; 
//com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.tesoreria.liquidaciondecretos.LiquidacionDecretosDetalleAmortizacionesParams;

/**
 * Esta clase encapsula los par&aacute;metros utilizados en el servicio que
 * recupera el detalle de amortizaciones para el estado de cuenta de
 * liquidaci&oacute;n por decretos.
 * 
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class GetDetalleAmortizacionesParams extends LiquidacionDecretosDetalleAmortizacionesParams {

	/**
	 * Constante de serializacion
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * M&eacute;todo gen&eacute;rico de validaci&oacute;n
	 * 
	 * @see com.indeval.sidv.decretos.persistence.model.vo.LiquidacionDecretosDetalleAmortizacionesParams#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object obj, Errors errors) {
	}

	/**
	 * Clona un objeto GetDetalleAmortizacionesParams
	 * 
	 * @return GetDetalleAmortizacionesParams
	 */
	public GetDetalleAmortizacionesParams clona() {
		GetDetalleAmortizacionesParams newParams = new GetDetalleAmortizacionesParams();
		if (this.getAgente() != null) {
			newParams.setAgente(new com.indeval.portaldali.middleware.services.modelo.AgenteVO());
			newParams.getAgente().setId(this.getAgente().getId());
			newParams.getAgente().setFolio(this.getAgente().getFolio());
			newParams.getAgente().setCuenta(this.getAgente().getCuenta());
		}
		newParams.setFolioFija(this.getFolioFija());
		newParams.setFolioVariable(this.getFolioVariable());
		if (this.getIdDerecho() != null) {
			newParams.setIdDerecho(new Integer(this.getIdDerecho().intValue()));
		}
		if (this.getIdTipoDerecho() != null) {
			newParams.setIdTipoDerecho(new Integer(this.getIdTipoDerecho().intValue()));
		}
		if (this.getIdTipoEjercicio() != null) {
			newParams.setIdTipoEjercicio(new Integer(this.getIdTipoEjercicio().intValue()));
		}
		return newParams;
	}

}