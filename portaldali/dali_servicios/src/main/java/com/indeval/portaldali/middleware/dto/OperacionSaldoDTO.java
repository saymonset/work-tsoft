/**
 * 2H Software - Bursatec
 * 
 * Sistema de Consulta de Estados de Cuenta
 *
 * Dec 20, 2007
 *
 */
package com.indeval.portaldali.middleware.dto;

import java.io.Serializable;

/**
 * Objeto de transferencia de datos que representa una operacion sobre un saldo
 * @author Pablo Julián Balderas Méndez
 * 
 */
public class OperacionSaldoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/** Representa la cuenta del traspasante */
	private CuentaEfectivoDTO cuentaTraspasante;

	/** Representa la cuenta del receptor */
	private CuentaEfectivoDTO cuentaReceptora;

	/** Representa la divisa en que se efectu la operación */
	private DivisaDTO divisa;
	
	/** Representa la cuenta de efectivo del traspasante */
	private CuentaEfectivoDTO cuentaEfectivoTraspasante = null;
	
	/** Representa la cuenta de efectivo del receptor */
	private CuentaEfectivoDTO cuentaEfectivoReceptor = null;

	/**
	 * Indicador de a que secci&oacute;n del saldo se cargan y abonan los
	 * movimientos de efectivo. Si afecta al disponible es 1. Si afecta al no
	 * disponible es 2.
	 */
	private Long cargoEfectivoA;

	/** El identificador único de la operación */
	private Long idOperacion;

	/**
	 * El folio de la operación dentro de la instrucci&oacute;n
	 * liquidaci&oacute;n
	 */
	private Long folio;

	/** El tipo de operación de la operación */
	private TipoOperacionDTO tipoOperacion;
	
	/**
	 * Instrucci&oacute;n de liquidaci&oacute;n a la que pertenece la
	 * operaci&oacute;n.
	 */
	private InstruccionLiquidacionDTO instruccionLiquidacion;

	/**
	 * Obtiene el atributo cuentaTraspasante
	 *
	 * @return El atrubuto cuentaTraspasante
	 */
	public CuentaEfectivoDTO getCuentaTraspasante() {
		return cuentaTraspasante;
	}

	/**
	 * Establece la propiedad cuentaTraspasante
	 *
	 * @param cuentaTraspasante el campo cuentaTraspasante a establecer
	 */
	public void setCuentaTraspasante(CuentaEfectivoDTO cuentaTraspasante) {
		this.cuentaTraspasante = cuentaTraspasante;
	}

	/**
	 * Obtiene el atributo cuentaReceptora
	 *
	 * @return El atrubuto cuentaReceptora
	 */
	public CuentaEfectivoDTO getCuentaReceptora() {
		return cuentaReceptora;
	}

	/**
	 * Establece la propiedad cuentaReceptora
	 *
	 * @param cuentaReceptora el campo cuentaReceptora a establecer
	 */
	public void setCuentaReceptora(CuentaEfectivoDTO cuentaReceptora) {
		this.cuentaReceptora = cuentaReceptora;
	}

	/**
	 * Obtiene el atributo divisa
	 *
	 * @return El atrubuto divisa
	 */
	public DivisaDTO getDivisa() {
		return divisa;
	}

	/**
	 * Establece la propiedad divisa
	 *
	 * @param divisa el campo divisa a establecer
	 */
	public void setDivisa(DivisaDTO divisa) {
		this.divisa = divisa;
	}

	/**
	 * Obtiene el atributo cargoEfectivoA
	 *
	 * @return El atrubuto cargoEfectivoA
	 */
	public Long getCargoEfectivoA() {
		return cargoEfectivoA;
	}

	/**
	 * Establece la propiedad cargoEfectivoA
	 *
	 * @param cargoEfectivoA el campo cargoEfectivoA a establecer
	 */
	public void setCargoEfectivoA(Long cargoEfectivoA) {
		this.cargoEfectivoA = cargoEfectivoA;
	}

	/**
	 * Obtiene el atributo idOperacion
	 *
	 * @return El atrubuto idOperacion
	 */
	public Long getIdOperacion() {
		return idOperacion;
	}

	/**
	 * Establece la propiedad idOperacion
	 *
	 * @param idOperacion el campo idOperacion a establecer
	 */
	public void setIdOperacion(Long idOperacion) {
		this.idOperacion = idOperacion;
	}

	/**
	 * Obtiene el atributo folio
	 *
	 * @return El atrubuto folio
	 */
	public Long getFolio() {
		return folio;
	}

	/**
	 * Establece la propiedad folio
	 *
	 * @param folio el campo folio a establecer
	 */
	public void setFolio(Long folio) {
		this.folio = folio;
	}

	/**
	 * Obtiene el atributo tipoOperacion
	 *
	 * @return El atrubuto tipoOperacion
	 */
	public TipoOperacionDTO getTipoOperacion() {
		return tipoOperacion;
	}

	/**
	 * Establece la propiedad tipoOperacion
	 *
	 * @param tipoOperacion el campo tipoOperacion a establecer
	 */
	public void setTipoOperacion(TipoOperacionDTO tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	/**
	 * Obtiene el atributo instruccionLiquidacion
	 *
	 * @return El atrubuto instruccionLiquidacion
	 */
	public InstruccionLiquidacionDTO getInstruccionLiquidacion() {
		return instruccionLiquidacion;
	}

	/**
	 * Establece la propiedad instruccionLiquidacion
	 *
	 * @param instruccionLiquidacion el campo instruccionLiquidacion a establecer
	 */
	public void setInstruccionLiquidacion(
			InstruccionLiquidacionDTO instruccionLiquidacion) {
		this.instruccionLiquidacion = instruccionLiquidacion;
	}

	/**
	 * Obtiene el atributo cuentaEfectivoTraspasante
	 *
	 * @return El atrubuto cuentaEfectivoTraspasante
	 */
	public CuentaEfectivoDTO getCuentaEfectivoTraspasante() {
		return cuentaEfectivoTraspasante;
	}

	/**
	 * Establece la propiedad cuentaEfectivoTraspasante
	 *
	 * @param cuentaEfectivoTraspasante el campo cuentaEfectivoTraspasante a establecer
	 */
	public void setCuentaEfectivoTraspasante(
			CuentaEfectivoDTO cuentaEfectivoTraspasante) {
		this.cuentaEfectivoTraspasante = cuentaEfectivoTraspasante;
	}

	/**
	 * Obtiene el atributo cuentaEfectivoReceptor
	 *
	 * @return El atrubuto cuentaEfectivoReceptor
	 */
	public CuentaEfectivoDTO getCuentaEfectivoReceptor() {
		return cuentaEfectivoReceptor;
	}

	/**
	 * Establece la propiedad cuentaEfectivoReceptor
	 *
	 * @param cuentaEfectivoReceptor el campo cuentaEfectivoReceptor a establecer
	 */
	public void setCuentaEfectivoReceptor(CuentaEfectivoDTO cuentaEfectivoReceptor) {
		this.cuentaEfectivoReceptor = cuentaEfectivoReceptor;
	}

}
