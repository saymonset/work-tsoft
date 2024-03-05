/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */

package com.indeval.portaldali.middleware.services.tesoreria;

import org.springframework.validation.Errors;

import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.tesoreria.liquidaciondecretos.LiquidacionDecretosDetalleParams;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class GetDetalleCuentaLiqParams extends LiquidacionDecretosDetalleParams {

	/**
	 * Constante de serializacion
	 */
	private static final long serialVersionUID = 1L;

	// private AgenteVO agente;
	//    
	// private String folioVariable;
	//    
	// private String folioFija;
	//
	// /**
	// * @return Returns the agente.
	// */
	// public AgenteVO getAgente() {
	// return agente;
	// }
	//
	// /**
	// * @return Returns the folioFija.
	// */
	// public String getFolioFija() {
	// return folioFija;
	// }
	//
	// /**
	// * @return Returns the folioVariable.
	// */
	// public String getFolioVariable() {
	// return folioVariable;
	// }
	//
	// /**
	// * @param agente The agente to set.
	// */
	// public void setAgente(AgenteVO agente) {
	// this.agente = agente;
	// }
	//
	// /**
	// * @param folioFija The folioFija to set.
	// */
	// public void setFolioFija(String folioFija) {
	// this.folioFija = folioFija;
	// }
	//
	// /**
	// * @param folioVariable The folioVariable to set.
	// */
	// public void setFolioVariable(String folioVariable) {
	// this.folioVariable = folioVariable;
	// }
	//
	// /**
	// * Valida que el objeto tenga todos los atributos requeridos
	// * @throws BusinessException
	// */
	// public void esValido() throws BusinessException {
	// Assert.notNull(this.getAgente(), "El objeto params tiene el AGENTE
	// NULL");
	// this.getAgente().tieneClaveValida();
	// if(StringUtils.isBlank(this.getAgente().getCuenta())){
	// throw new BusinessException("El Agente tiene la cuenta NULL o VACIO");
	// }
	// if(StringUtils.isBlank(this.getFolioFija())){
	// throw new BusinessException("El objeto params tiene el FolioFija NULL o
	// VACIO");
	// }
	// if(StringUtils.isBlank(this.getFolioVariable())){
	// throw new BusinessException("El objeto params tiene el FolioVariable NULL
	// o VACIO");
	// }
	// }

	/**
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object obj, Errors errors) {
	}

	/**
	 * @return GetDetalleCuentaLiqParams
	 */
	public GetDetalleCuentaLiqParams clona() {
		GetDetalleCuentaLiqParams newParams = new GetDetalleCuentaLiqParams();
		if (this.getAgente() != null) {
			newParams.setAgente(new AgenteVO());
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
