/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * 
 * 27/02/2008
 */
package com.indeval.portaldali.presentation.dto.mercadodinero;

import java.io.Serializable;

import com.indeval.portaldali.middleware.dto.BovedaDTO;
import com.indeval.portaldali.middleware.dto.CuentaEfectivoDTO;
import com.indeval.portaldali.middleware.dto.DivisaDTO;

/**
 * DTO que representa a los elementos de la pantalla de captura de operaciones
 * para el tipo de operacion Reporto Nominal.
 * 
 * @author Juan Carlos Huizar Moreno
 * @version 1.0
 * 
 */
public class TraspasoEfecCtasControlDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/** El tipo de traspaso de efectivo entre cuentas controladas. */
	private Integer tipoTraspaso;

	/**
	 * Auxiliar para el campo participante traspasante (El id y folio del
	 * participante).
	 */
	private String idFolioTraspasante;

	/**
	 * Auxiliar para el campo participante receptor (El id y folio del
	 * participante).
	 */
	private String idFolioReceptor;

	/**
	 * La cuenta origen del traspaso
	 */
	private CuentaEfectivoDTO cuentaOrigen = new CuentaEfectivoDTO();

	/**
	 * La cuenta destino del traspaso
	 */
	private CuentaEfectivoDTO cuentaDestino = new CuentaEfectivoDTO();

	/**
	 * La cuenta destino del traspaso
	 */
	private Double importeATraspasar;

	/**
	 * La cuenta destino del traspaso
	 */
	private Double saldoDeEfectivo;

	/** El saldo disponible */
	private Double saldoDisponible = null;

	/**
	 * Folio de confirmacion de la operacion
	 */
	private Integer folioRetiro;

	/** Divisa del valor nominal */
	private DivisaDTO valorEn = new DivisaDTO();
	
	/** Boveda referenciada */
	private BovedaDTO boveda = new BovedaDTO();

	/** métodos getters & setters. */

	/**
	 * @return the tipoTraspaso
	 */
	public Integer getTipoTraspaso() {
		return tipoTraspaso;
	}

	/**
	 * @param tipoTraspaso
	 *            the tipoTraspaso to set
	 */
	public void setTipoTraspaso(Integer tipoTraspaso) {
		this.tipoTraspaso = tipoTraspaso;
	}

	/**
	 * @return the idFolioTraspasante
	 */
	public String getIdFolioTraspasante() {
		return idFolioTraspasante;
	}

	/**
	 * @param idFolioTraspasante
	 *            the idFolioTraspasante to set
	 */
	public void setIdFolioTraspasante(String idFolioTraspasante) {
		this.idFolioTraspasante = idFolioTraspasante;
	}

	/**
	 * @return the idFolioReceptor
	 */
	public String getIdFolioReceptor() {
		return idFolioReceptor;
	}

	/**
	 * @param idFolioReceptor
	 *            the idFolioReceptor to set
	 */
	public void setIdFolioReceptor(String idFolioReceptor) {
		this.idFolioReceptor = idFolioReceptor;
	}

	/**
	 * @return the cuentaOrigen
	 */
	public CuentaEfectivoDTO getCuentaOrigen() {
		return cuentaOrigen;
	}

	/**
	 * @param cuentaOrigen
	 *            the cuentaOrigen to set
	 */
	public void setCuentaOrigen(CuentaEfectivoDTO cuentaOrigen) {
		this.cuentaOrigen = cuentaOrigen;
	}

	/**
	 * @return the cuentaDestino
	 */
	public CuentaEfectivoDTO getCuentaDestino() {
		return cuentaDestino;
	}

	/**
	 * @param cuentaDestino
	 *            the cuentaDestino to set
	 */
	public void setCuentaDestino(CuentaEfectivoDTO cuentaDestino) {
		this.cuentaDestino = cuentaDestino;
	}

	/**
	 * @return the importeATraspasar
	 */
	public Double getImporteATraspasar() {
		return importeATraspasar;
	}

	/**
	 * @param importeATraspasar
	 *            the importeATraspasar to set
	 */
	public void setImporteATraspasar(Double importeATraspasar) {
		this.importeATraspasar = importeATraspasar;
	}

	/**
	 * @return the saldoDeEfectivo
	 */
	public Double getSaldoDeEfectivo() {
		return saldoDeEfectivo;
	}

	/**
	 * @param saldoDeEfectivo
	 *            the saldoDeEfectivo to set
	 */
	public void setSaldoDeEfectivo(Double saldoDeEfectivo) {
		this.saldoDeEfectivo = saldoDeEfectivo;
	}

	/**
	 * @return the saldoDisponible
	 */
	public Double getSaldoDisponible() {
		return saldoDisponible;
	}

	/**
	 * @param saldoDisponible
	 *            the saldoDisponible to set
	 */
	public void setSaldoDisponible(Double saldoDisponible) {
		this.saldoDisponible = saldoDisponible;
	}

	/**
	 * @return the folioRetiro
	 */
	public Integer getFolioRetiro() {
		return folioRetiro;
	}

	/**
	 * @param folioRetiro
	 *            the folioRetiro to set
	 */
	public void setFolioRetiro(Integer folioRetiro) {
		this.folioRetiro = folioRetiro;
	}


	public DivisaDTO getValorEn() {
		return valorEn;
	}

	public void setValorEn(DivisaDTO valorEn) {
		this.valorEn = valorEn;
	}

	public BovedaDTO getBoveda() {
		return boveda;
	}

	public void setBoveda(BovedaDTO boveda) {
		this.boveda = boveda;
	}

}