/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * 
 * 27/02/2008
 */
package com.indeval.portaldali.presentation.dto.mercadodinero;

import java.io.Serializable;
import java.util.Date;

import com.indeval.portaldali.middleware.dto.CuentaDTO;
import com.indeval.portaldali.middleware.dto.DivisaDTO;
import com.indeval.portaldali.middleware.dto.PosicionDTO;

/**
 * DTO que representa a los elementos de la pantalla de captura de operaciones
 * para el tipo de operacion Reporto Nominal.
 * 
 * @author Juan Carlos Huizar Moreno
 * @author José Antonio Huizar Moreno
 * @version 2.0
 * 
 */
public class AperturaDeSistemaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/** El DTO que representara a la cuenta de la contraparte. */
	private CuentaDTO cuentaReceptor = new CuentaDTO();

	/**
	 * EL DTO que representa a la posicion, como son la cuenta, emision y boveda
	 * del participante.
	 */
	private PosicionDTO posicionTraspasante = new PosicionDTO();

	/** El Tipo de apertura */
	private Integer tipoApertura;

	/** El saldo disponible de la posición seleccionada */
	private Double saldoDisponible = null;

	/** El saldo actual de la posición seleccionada */
	private Double saldoActual = null;

	/** Cantidad */
	private Long cantidadOperada = null;

	/** Fecha de adquisición */
	private Date fechaAdquisicion = null;
	
	/** Date asociada a la fecha y hora de cierre de la operacion */
	private Date fechaHoraCierreOper;

	/** Precio de adquisición */
	private Double precioAdquisicion;

	/** El cliente */
	private String cliente;

	/** El RFC  CURP */
	private String rfcCurp;

	/** Extranjero */
	private Boolean extranjero;

	/** ISIN */
	private String isin;

	/** El precio vector */
	private Double precioVector;

	/** Indica si se trata de una recepción */
	private Boolean recepcion;

	/** El identificador y el folio del traspasante */
	private String idFolioTraspasante;

	/** El identificador y el folio del receptor */
	private String idFolioReceptor;

	/** Divisa del valor nominal */
	private DivisaDTO valorEn = new DivisaDTO();

	/**
	 * Obtiene el campo saldoActual
	 * 
	 * @return saldoActual
	 */
	public Double getSaldoActual() {
		return saldoActual;
	}

	/**
	 * Asigna el campo saldoActual
	 * 
	 * @param saldoActual
	 *            el valor de saldoActual a asignar
	 */
	public void setSaldoActual(Double saldoActual) {
		this.saldoActual = saldoActual;
	}

	/**
	 * Asigna el campo saldoDisponible
	 * 
	 * @param saldoDisponible
	 *            el valor de saldoDisponible a asignar
	 */
	public void setSaldoDisponible(Double saldoDisponible) {
		this.saldoDisponible = saldoDisponible;
	}

	/**
	 * Obtiene el campo saldoDisponible
	 * 
	 * @return saldoDisponible
	 */
	public Double getSaldoDisponible() {
		return saldoDisponible;
	}

	/**
	 * Obtiene el campo cuentaReceptor
	 * 
	 * @return cuentaReceptor
	 */
	public CuentaDTO getCuentaReceptor() {
		return cuentaReceptor;
	}

	/**
	 * Asigna el campo cuentaReceptor
	 * 
	 * @param cuentaReceptor
	 *            el valor de cuentaReceptor a asignar
	 */
	public void setCuentaReceptor(CuentaDTO cuentaReceptor) {
		this.cuentaReceptor = cuentaReceptor;
	}

	/**
	 * Obtiene el campo posicionTraspasante
	 * 
	 * @return posicionTraspasante
	 */
	public PosicionDTO getPosicionTraspasante() {
		return posicionTraspasante;
	}

	/**
	 * Asigna el campo posicionTraspasante
	 * 
	 * @param posicionTraspasante
	 *            el valor de posicionTraspasante a asignar
	 */
	public void setPosicionTraspasante(PosicionDTO posicionTraspasante) {
		this.posicionTraspasante = posicionTraspasante;
	}

	/**
	 * @return the tipoApertura
	 */
	public Integer getTipoApertura() {
		return tipoApertura;
	}

	/**
	 * @param tipoApertura
	 *            the tipoApertura to set
	 */
	public void setTipoApertura(Integer tipoApertura) {
		this.tipoApertura = tipoApertura;
	}

	/**
	 * Obtiene el campo fechaAdquisicion
	 * 
	 * @return fechaAdquisicion
	 */
	public Date getFechaAdquisicion() {
		return fechaAdquisicion;
	}

	/**
	 * Asigna el campo fechaAdquisicion
	 * 
	 * @param fechaAdquisicion
	 *            el valor de fechaAdquisicion a asignar
	 */
	public void setFechaAdquisicion(Date fechaAdquisicion) {
		this.fechaAdquisicion = fechaAdquisicion;
	}

	/**
	 * Obtiene el campo precioAdquisicion
	 * 
	 * @return precioAdquisicion
	 */
	public Double getPrecioAdquisicion() {
		return precioAdquisicion;
	}

	/**
	 * Asigna el campo precioAdquisicion
	 * 
	 * @param precioAdquisicion
	 *            el valor de precioAdquisicion a asignar
	 */
	public void setPrecioAdquisicion(Double precioAdquisicion) {
		this.precioAdquisicion = precioAdquisicion;
	}

	/**
	 * Obtiene el campo cliente
	 * 
	 * @return cliente
	 */
	public String getCliente() {
		return cliente;
	}

	/**
	 * Asigna el campo cliente
	 * 
	 * @param cliente
	 *            el valor de cliente a asignar
	 */
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	/**
	 * Obtiene el campo rfcCurp
	 * 
	 * @return rfcCurp
	 */
	public String getRfcCurp() {
		return rfcCurp;
	}

	/**
	 * Asigna el campo rfcCurp
	 * 
	 * @param rfcCurp
	 *            el valor de rfcCurp a asignar
	 */
	public void setRfcCurp(String rfcCurp) {
		this.rfcCurp = rfcCurp;
	}

	/**
	 * Obtiene el campo extranjero
	 * 
	 * @return extranjero
	 */
	public Boolean getExtranjero() {
		return extranjero;
	}

	/**
	 * Asigna el campo extranjero
	 * 
	 * @param extranjero
	 *            el valor de extranjero a asignar
	 */
	public void setExtranjero(Boolean extranjero) {
		this.extranjero = extranjero;
	}

	/**
	 * Obtiene el campo isin
	 * 
	 * @return isin
	 */
	public String getIsin() {
		return isin;
	}

	/**
	 * Asigna el campo isin
	 * 
	 * @param isin
	 *            el valor de isin a asignar
	 */
	public void setIsin(String isin) {
		this.isin = isin;
	}

	/**
	 * Obtiene el campo precioVector
	 * 
	 * @return precioVector
	 */
	public Double getPrecioVector() {
		return precioVector;
	}

	/**
	 * Asigna el campo precioVector
	 * 
	 * @param precioVector
	 *            el valor de precioVector a asignar
	 */
	public void setPrecioVector(Double precioVector) {
		this.precioVector = precioVector;
	}

	/**
	 * Obtiene el campo recepcion
	 * 
	 * @return recepcion
	 */
	public Boolean getRecepcion() {
		return recepcion;
	}

	/**
	 * Asigna el campo recepcion
	 * 
	 * @param recepcion
	 *            el valor de recepcion a asignar
	 */
	public void setRecepcion(Boolean recepcion) {
		this.recepcion = recepcion;
	}

	/**
	 * Obtiene el campo idFolioTraspasante
	 * 
	 * @return idFolioTraspasante
	 */
	public String getIdFolioTraspasante() {
		return idFolioTraspasante;
	}

	/**
	 * Asigna el campo idFolioTraspasante
	 * 
	 * @param idFolioTraspasante
	 *            el valor de idFolioTraspasante a asignar
	 */
	public void setIdFolioTraspasante(String idFolioTraspasante) {
		this.idFolioTraspasante = idFolioTraspasante;
	}

	/**
	 * Obtiene el campo idFolioReceptor
	 * 
	 * @return idFolioReceptor
	 */
	public String getIdFolioReceptor() {
		return idFolioReceptor;
	}

	/**
	 * Asigna el campo idFolioReceptor
	 * 
	 * @param idFolioReceptor
	 *            el valor de idFolioReceptor a asignar
	 */
	public void setIdFolioReceptor(String idFolioReceptor) {
		this.idFolioReceptor = idFolioReceptor;
	}

	/**
	 * Obtiene el valor del campo cantidadOperada
	 * 
	 * @return el valor de cantidadOperada
	 */
	public Long getCantidadOperada() {
		return cantidadOperada;
	}

	/**
	 * Asigna el campo cantidadOperada
	 * 
	 * @param cantidadOperada
	 *            el valor de cantidadOperada a asignar
	 */
	public void setCantidadOperada(Long cantidadOperada) {
		this.cantidadOperada = cantidadOperada;
	}

	/**
	 * @return the valorEn
	 */
	public DivisaDTO getValorEn() {
		return valorEn;
	}

	/**
	 * @param valorEn
	 *            the valorEn to set
	 */
	public void setValorEn(DivisaDTO valorEn) {
		this.valorEn = valorEn;
	}

	public Date getFechaHoraCierreOper() {
		return fechaHoraCierreOper;
	}

	public void setFechaHoraCierreOper(Date fechaHoraCierreOper) {
		this.fechaHoraCierreOper = fechaHoraCierreOper;
	}

	/** ******* métodos getters & setters. */

}