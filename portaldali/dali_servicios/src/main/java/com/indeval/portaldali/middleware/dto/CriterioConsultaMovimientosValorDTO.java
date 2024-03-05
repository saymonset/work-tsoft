/**
 * 2H Software SA de CV
 * 
 * Sistema de Consulta de Estado de Cuenta - Indeval
 *
 * Fecha de creación: Jan 03, 2008
 */
package com.indeval.portaldali.middleware.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * DTO de criterios para la consulta de movimientos de valores
 * 
 * @author Emigdio Hernández Rodríguez
 * @version 1.0
 */
public class CriterioConsultaMovimientosValorDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * Emisión asociada a la posición
	 */

	public EmisionDTO emision;
	/**
	 * Cuenta asociada a la posición
	 */
	public CuentaDTO cuenta;
	/**
	 * bóveda asociada a la posición
	 */
	public BovedaDTO boveda;
	/**
	 * Identificador de la posición
	 */
	private long idPosicion;
	/**
	 * Valor de la posición disponible
	 */
	private String posicionDisponible = null;
	/**
	 * Valor de la posición no disponible
	 */
	private String posicionNoDisponible = null;
	/**
	 * Valor de la posición
	 */
	private String posicion = null;
	/**
	 * Criterio de fecha de inicio
	 */
	private Date fechaInicial = null;
	/**
	 * Criterio de fecha final
	 */
	private Date fechaFinal = null;

	private CuentaDTO cuentaContraparte = null;

	private TipoInstruccionDTO tipoInstruccion = null;

	private TipoOperacionDTO tipoOperacion = null;

	private String folioInstruccion = null;

	/** Indica que los resultados deben ordenarse por tipo de instrucción */
	private boolean ordenarPorTipoDeInstruccion = false;

	private boolean busquedaFechaConcertacion = false;

	private boolean busquedaFechaAplicacion = false;

	private boolean busquedaTipoOperacion = false;

	private int rolParticipante = -1;

	private int rolContraparte = -1;

	/**
	 * Obtiene el campo emision
	 * 
	 * @return emision
	 */
	public EmisionDTO getEmision() {
		return emision;
	}

	/**
	 * Asigna el valor del campo emision
	 * 
	 * @param emision
	 *            el valor de emision a asignar
	 */
	public void setEmision(EmisionDTO emision) {
		this.emision = emision;
	}

	/**
	 * Obtiene el valor del atributo ordenarPorTipoDeOperacion
	 * 
	 * @return el valor del atributo ordenarPorTipoDeOperacion
	 */
	public boolean isOrdenarPorTipoDeInstruccion() {
		return ordenarPorTipoDeInstruccion;
	}

	/**
	 * Establece el valor del atributo ordenarPorTipoDeOperacion
	 * 
	 * @param ordenarPorTipoDeOperacion
	 *            el valor del atributo ordenarPorTipoDeOperacion a establecer
	 */
	public void setOrdenarPorTipoDeInstruccion(boolean ordenarPorTipoDeInstruccion) {
		this.ordenarPorTipoDeInstruccion = ordenarPorTipoDeInstruccion;
	}

	/**
	 * Obtiene el campo cuenta
	 * 
	 * @return cuenta
	 */
	public CuentaDTO getCuenta() {
		return cuenta;
	}

	/**
	 * Asigna el valor del campo cuenta
	 * 
	 * @param cuenta
	 *            el valor de cuenta a asignar
	 */
	public void setCuenta(CuentaDTO cuenta) {
		this.cuenta = cuenta;
	}

	/**
	 * Obtiene el campo boveda
	 * 
	 * @return boveda
	 */
	public BovedaDTO getBoveda() {
		return boveda;
	}

	/**
	 * Asigna el valor del campo boveda
	 * 
	 * @param boveda
	 *            el valor de boveda a asignar
	 */
	public void setBoveda(BovedaDTO boveda) {
		this.boveda = boveda;
	}

	/**
	 * Obtiene el campo idPosicion
	 * 
	 * @return idPosicion
	 */
	public long getIdPosicion() {
		return idPosicion;
	}

	/**
	 * Asigna el valor del campo idPosicion
	 * 
	 * @param idPosicion
	 *            el valor de idPosicion a asignar
	 */
	public void setIdPosicion(long idPosicion) {
		this.idPosicion = idPosicion;
	}

	/**
	 * Obtiene el campo posicionDisponible
	 * 
	 * @return posicionDisponible
	 */
	public String getPosicionDisponible() {
		return posicionDisponible;
	}

	/**
	 * Asigna el valor del campo posicionDisponible
	 * 
	 * @param posicionDisponible
	 *            el valor de posicionDisponible a asignar
	 */
	public void setPosicionDisponible(String posicionDisponible) {
		this.posicionDisponible = posicionDisponible;
	}

	/**
	 * Obtiene el campo posicionNoDisponible
	 * 
	 * @return posicionNoDisponible
	 */
	public String getPosicionNoDisponible() {
		return posicionNoDisponible;
	}

	/**
	 * Asigna el valor del campo posicionNoDisponible
	 * 
	 * @param posicionNoDisponible
	 *            el valor de posicionNoDisponible a asignar
	 */
	public void setPosicionNoDisponible(String posicionNoDisponible) {
		this.posicionNoDisponible = posicionNoDisponible;
	}

	/**
	 * Obtiene el campo posicion
	 * 
	 * @return posicion
	 */
	public String getPosicion() {
		return posicion;
	}

	/**
	 * Asigna el valor del campo posicion
	 * 
	 * @param posicion
	 *            el valor de posicion a asignar
	 */
	public void setPosicion(String posicion) {
		this.posicion = posicion;
	}

	/**
	 * Obtiene el campo fechaInicial
	 * 
	 * @return fechaInicial
	 */
	public Date getFechaInicial() {
		return fechaInicial;
	}

	/**
	 * Asigna el valor del campo fechaInicial
	 * 
	 * @param fechaInicial
	 *            el valor de fechaInicial a asignar
	 */
	public void setFechaInicial(Date fechaInicial) {
		this.fechaInicial = fechaInicial;
	}

	/**
	 * Obtiene el campo fechaFinal
	 * 
	 * @return fechaFinal
	 */
	public Date getFechaFinal() {
		return fechaFinal;
	}

	/**
	 * Asigna el valor del campo fechaFinal
	 * 
	 * @param fechaFinal
	 *            el valor de fechaFinal a asignar
	 */
	public void setFechaFinal(Date fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	/**
	 * Obtiene el campo cuentaContraparte
	 * 
	 * @return cuentaContraparte
	 */
	public CuentaDTO getCuentaContraparte() {
		return cuentaContraparte;
	}

	/**
	 * Asigna el valor del campo cuentaContraparte
	 * 
	 * @param cuentaContraparte
	 *            el valor de cuentaContraparte a asignar
	 */
	public void setCuentaContraparte(CuentaDTO cuentaContraparte) {
		this.cuentaContraparte = cuentaContraparte;
	}

	/**
	 * Obtiene el campo tipoInstruccion
	 * 
	 * @return tipoInstruccion
	 */
	public TipoInstruccionDTO getTipoInstruccion() {
		return tipoInstruccion;
	}

	/**
	 * Asigna el valor del campo tipoInstruccion
	 * 
	 * @param tipoInstruccion
	 *            el valor de tipoInstruccion a asignar
	 */
	public void setTipoInstruccion(TipoInstruccionDTO tipoInstruccion) {
		this.tipoInstruccion = tipoInstruccion;
	}

	/**
	 * Obtiene el campo tipoOperacion
	 * 
	 * @return tipoOperacion
	 */
	public TipoOperacionDTO getTipoOperacion() {
		return tipoOperacion;
	}

	/**
	 * Asigna el valor del campo tipoOperacion
	 * 
	 * @param tipoOperacion
	 *            el valor de tipoOperacion a asignar
	 */
	public void setTipoOperacion(TipoOperacionDTO tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	/**
	 * Obtiene el campo folioInstruccion
	 * 
	 * @return folioInstruccion
	 */
	public String getFolioInstruccion() {
		return folioInstruccion;
	}

	/**
	 * Asigna el valor del campo folioInstruccion
	 * 
	 * @param folioInstruccion
	 *            el valor de folioInstruccion a asignar
	 */
	public void setFolioInstruccion(String folioInstruccion) {
		this.folioInstruccion = folioInstruccion;
	}

	/**
	 * Obtiene el campo busquedaFechaConcertacion
	 * 
	 * @return busquedaFechaConcertacion
	 */
	public boolean isBusquedaFechaConcertacion() {
		return busquedaFechaConcertacion;
	}

	/**
	 * Asigna el valor del campo busquedaFechaConcertacion
	 * 
	 * @param busquedaFechaConcertacion
	 *            el valor de busquedaFechaConcertacion a asignar
	 */
	public void setBusquedaFechaConcertacion(boolean busquedaFechaConcertacion) {
		this.busquedaFechaConcertacion = busquedaFechaConcertacion;
	}

	/**
	 * Obtiene el campo busquedaFechaAplicacion
	 * 
	 * @return busquedaFechaAplicacion
	 */
	public boolean isBusquedaFechaAplicacion() {
		return busquedaFechaAplicacion;
	}

	/**
	 * Asigna el valor del campo busquedaFechaAplicacion
	 * 
	 * @param busquedaFechaAplicacion
	 *            el valor de busquedaFechaAplicacion a asignar
	 */
	public void setBusquedaFechaAplicacion(boolean busquedaFechaAplicacion) {
		this.busquedaFechaAplicacion = busquedaFechaAplicacion;
	}

	/**
	 * Obtiene el campo rolParticipante
	 * 
	 * @return rolParticipante
	 */
	public int getRolParticipante() {
		return rolParticipante;
	}

	/**
	 * Asigna el valor del campo rolParticipante
	 * 
	 * @param rolParticipante
	 *            el valor de rolParticipante a asignar
	 */
	public void setRolParticipante(int rolParticipante) {
		this.rolParticipante = rolParticipante;
	}

	/**
	 * Obtiene el campo rolContraparte
	 * 
	 * @return rolContraparte
	 */
	public int getRolContraparte() {
		return rolContraparte;
	}

	/**
	 * Asigna el valor del campo rolContraparte
	 * 
	 * @param rolContraparte
	 *            el valor de rolContraparte a asignar
	 */
	public void setRolContraparte(int rolContraparte) {
		this.rolContraparte = rolContraparte;
	}

	public boolean isBusquedaTipoOperacion() {
		return busquedaTipoOperacion;
	}

	public void setBusquedaTipoOperacion(boolean busquedaTipoOperacion) {
		this.busquedaTipoOperacion = busquedaTipoOperacion;
	}

}