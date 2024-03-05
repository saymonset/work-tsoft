/**
 * 2H Software SA de CV
 * 
 * Sistema de Consulta de Estado de Cuenta - Indeval
 *
 * Fecha de creación: Dec 19, 2007
 */
package com.indeval.portaldali.middleware.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Data Transfer Object que representa una operación de una posición o saldo.
 * 
 * @author Sandra Flores Arrieta
 * @version 1.0
 * 
 */
public class OperacionPosicionDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/** Representa la cuenta del traspasante */
	private CuentaDTO cuentaTraspasante;

	/** Representa la cuenta del receptor */
	private CuentaDTO cuentaReceptora;

	/** La cuenta de efectivo del traspasante */
	private CuentaDTO cuentaEfectivoTraspasante;

	/** La cuenta de efectivo del receptor */
	private CuentaDTO cuentaEfectivoReceptor;

	/** Representa la divisa en que se efectu la operación */
	private DivisaDTO divisa;

	/** El monto total de la operación */
	private double monto;

	/** El precio del título para la operación */
	private double precio;

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
	 * N&uacute;mero de titulos involucrados en la operaci&oacute;n.
	 */
	private Long numeroTitulos;

	/** Posición correspondiente a la operación que se lleva a cabo */
	private PosicionDTO posicion;

	/**
	 * Indica si el cargo se realiza sobre el disponible o el no disponible en
	 * las posiciones nombradas.
	 */
	private Long cargoValoresA;

	/** El cupón relacionado con la operación y la posición */
	private CuponDTO cupon;

	/** La fecha del registro */
	private Date fechaRegistro = null;
	
	/**
	 * nuevos campos para manejo por paquete
	 **/
	private boolean porPaquete;
	
	private String referenciaPaquete;
	
	private String totalOperacionesPaquete;
	
	private String numeroOperacionPaquete;
	
	private String totalTitulosPaquete;
	
	private String totalImportePaquete;
	
	/**
	 * Obtiene el campo fechaRegistro
	 * 
	 * @return fechaRegistro
	 */
	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	/**
	 * Obtiene el valor del atributo precio
	 * 
	 * @return el valor del atributo precio
	 */
	public double getPrecio() {
		return precio;
	}

	/**
	 * Establece el valor del atributo precio
	 * 
	 * @param precio
	 *            el valor del atributo precio a establecer
	 */
	public void setPrecio(double precio) {
		this.precio = precio;
	}

	/**
	 * Asigna el campo fechaRegistro
	 * 
	 * @param fechaRegistro
	 *            el valor de fechaRegistro a asignar
	 */
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	/**
	 * Obtiene el valor del atributo cuentaTraspasante
	 * 
	 * @return el valor del atributo cuentaTraspasante
	 */
	public CuentaDTO getCuentaTraspasante() {
		return cuentaTraspasante;
	}

	/**
	 * Obtiene el campo numeroTitulos
	 * 
	 * @return numeroTitulos
	 */
	public Long getNumeroTitulos() {
		return numeroTitulos;
	}

	/**
	 * Asigna el campo numeroTitulos
	 * 
	 * @param numeroTitulos
	 *            el valor de numeroTitulos a asignar
	 */
	public void setNumeroTitulos(Long numeroTitulos) {
		this.numeroTitulos = numeroTitulos;
	}

	/**
	 * Obtiene el campo posicion
	 * 
	 * @return posicion
	 */
	public PosicionDTO getPosicion() {
		return posicion;
	}

	/**
	 * Asigna el campo posicion
	 * 
	 * @param posicion
	 *            el valor de posicion a asignar
	 */
	public void setPosicion(PosicionDTO posicion) {
		this.posicion = posicion;
	}

	/**
	 * Obtiene el campo cargoValoresA
	 * 
	 * @return cargoValoresA
	 */
	public Long getCargoValoresA() {
		return cargoValoresA;
	}

	/**
	 * Asigna el campo cargoValoresA
	 * 
	 * @param cargoValoresA
	 *            el valor de cargoValoresA a asignar
	 */
	public void setCargoValoresA(Long cargoValoresA) {
		this.cargoValoresA = cargoValoresA;
	}

	/**
	 * Obtiene el campo cupon
	 * 
	 * @return cupon
	 */
	public CuponDTO getCupon() {
		return cupon;
	}

	/**
	 * Asigna el campo cupon
	 * 
	 * @param cupon
	 *            el valor de cupon a asignar
	 */
	public void setCupon(CuponDTO cupon) {
		this.cupon = cupon;
	}

	/**
	 * Obtiene el valor del atributo monto
	 * 
	 * @return el valor del atributo monto
	 */
	public double getMonto() {
		return monto;
	}

	/**
	 * Establece el valor del atributo monto
	 * 
	 * @param monto
	 *            el valor del atributo monto a establecer
	 */
	public void setMonto(double monto) {
		this.monto = monto;
	}

	/**
	 * Establece el valor del atributo cuentaTraspasante
	 * 
	 * @param cuentaTraspasante
	 *            el valor del atributo cuentaTraspasante a establecer.
	 */
	public void setCuentaTraspasante(CuentaDTO cuentaTraspasante) {
		this.cuentaTraspasante = cuentaTraspasante;
	}

	/**
	 * Obtiene el valor del atributo cuentaReceptora
	 * 
	 * @return el valor del atributo cuentaReceptora
	 */
	public CuentaDTO getCuentaReceptora() {
		return cuentaReceptora;
	}

	/**
	 * Establece el valor del atributo cuentaReceptora
	 * 
	 * @param cuentaReceptora
	 *            el valor del atributo cuentaReceptora a establecer.
	 */
	public void setCuentaReceptora(CuentaDTO cuentaReceptora) {
		this.cuentaReceptora = cuentaReceptora;
	}

	/**
	 * Obtiene el valor del atributo divisa
	 * 
	 * @return el valor del atributo divisa
	 */
	public DivisaDTO getDivisa() {
		return divisa;
	}

	/**
	 * Establece el valor del atributo divisa
	 * 
	 * @param divisa
	 *            el valor del atributo divisa a establecer.
	 */
	public void setDivisa(DivisaDTO divisa) {
		this.divisa = divisa;
	}

	/**
	 * Obtiene el valor del atributo cargoEfectivoA
	 * 
	 * @return el valor del atributo cargoEfectivoA
	 */
	public Long getCargoEfectivoA() {
		return cargoEfectivoA;
	}

	/**
	 * Establece el valor del atributo cargoEfectivoA
	 * 
	 * @param cargoEfectivoA
	 *            el valor del atributo cargoEfectivoA a establecer.
	 */
	public void setCargoEfectivoA(Long cargoEfectivoA) {
		this.cargoEfectivoA = cargoEfectivoA;
	}

	/**
	 * Obtiene el valor del atributo idOperacion
	 * 
	 * @return el valor del atributo idOperacion
	 */
	public Long getIdOperacion() {
		return idOperacion;
	}

	/**
	 * Establece el valor del atributo idOperacion
	 * 
	 * @param idOperacion
	 *            el valor del atributo idOperacion a establecer.
	 */
	public void setIdOperacion(Long idOperacion) {
		this.idOperacion = idOperacion;
	}

	/**
	 * Obtiene el valor del atributo folio
	 * 
	 * @return el valor del atributo folio
	 */
	public Long getFolio() {
		return folio;
	}

	/**
	 * Establece el valor del atributo folio
	 * 
	 * @param folio
	 *            el valor del atributo folio a establecer.
	 */
	public void setFolio(Long folio) {
		this.folio = folio;
	}

	/**
	 * Obtiene el valor del atributo tipoOperacion
	 * 
	 * @return el valor del atributo tipoOperacion
	 */
	public TipoOperacionDTO getTipoOperacion() {
		return tipoOperacion;
	}

	/**
	 * Establece el valor del atributo tipoOperacion
	 * 
	 * @param tipoOperacion
	 *            el valor del atributo tipoOperacion a establecer.
	 */
	public void setTipoOperacion(TipoOperacionDTO tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	/**
	 * Obtiene el valor del atributo instruccionLiquidacion
	 * 
	 * @return el valor del atributo instruccionLiquidacion
	 */
	public InstruccionLiquidacionDTO getInstruccionLiquidacion() {
		return instruccionLiquidacion;
	}

	/**
	 * Establece el valor del atributo instruccionLiquidacion
	 * 
	 * @param instruccionLiquidacion
	 *            el valor del atributo instruccionLiquidacion a establecer.
	 */
	public void setInstruccionLiquidacion(
			InstruccionLiquidacionDTO instruccionLiquidacion) {
		this.instruccionLiquidacion = instruccionLiquidacion;
	}

	/**
	 * @return the cuentaEfectivoTraspasante
	 */
	public CuentaDTO getCuentaEfectivoTraspasante() {
		return cuentaEfectivoTraspasante;
	}

	/**
	 * @param cuentaEfectivoTraspasante
	 *            the cuentaEfectivoTraspasante to set
	 */
	public void setCuentaEfectivoTraspasante(CuentaDTO cuentaEfectivoTraspasante) {
		this.cuentaEfectivoTraspasante = cuentaEfectivoTraspasante;
	}

	/**
	 * @return the cuentaEfectivoReceptor
	 */
	public CuentaDTO getCuentaEfectivoReceptor() {
		return cuentaEfectivoReceptor;
	}

	/**
	 * @param cuentaEfectivoReceptor
	 *            the cuentaEfectivoReceptor to set
	 */
	public void setCuentaEfectivoReceptor(CuentaDTO cuentaEfectivoReceptor) {
		this.cuentaEfectivoReceptor = cuentaEfectivoReceptor;
	}

	public boolean isPorPaquete() {
		if(referenciaPaquete != null && referenciaPaquete.length() > 0){
			porPaquete = true;
		}else{
			porPaquete = false;
		}
		return porPaquete;
	}

	public void setPorPaquete(boolean porPaquete) {
		this.porPaquete = porPaquete;
	}

	public String getReferenciaPaquete() {
		return referenciaPaquete;
	}

	public void setReferenciaPaquete(String referenciaPaquete) {
		this.referenciaPaquete = referenciaPaquete;
	}

	public String getTotalOperacionesPaquete() {
		return totalOperacionesPaquete;
	}

	public void setTotalOperacionesPaquete(String totalOperacionesPaquete) {
		this.totalOperacionesPaquete = totalOperacionesPaquete;
	}

	public String getNumeroOperacionPaquete() {
		return numeroOperacionPaquete;
	}

	public void setNumeroOperacionPaquete(String numeroOperacionPaquete) {
		this.numeroOperacionPaquete = numeroOperacionPaquete;
	}

	public String getTotalTitulosPaquete() {
		return totalTitulosPaquete;
	}

	public void setTotalTitulosPaquete(String totalTitulosPaquete) {
		this.totalTitulosPaquete = totalTitulosPaquete;
	}

	public String getTotalImportePaquete() {
		return totalImportePaquete;
	}

	public void setTotalImportePaquete(String totalImportePaquete) {
		this.totalImportePaquete = totalImportePaquete;
	}

	
}
