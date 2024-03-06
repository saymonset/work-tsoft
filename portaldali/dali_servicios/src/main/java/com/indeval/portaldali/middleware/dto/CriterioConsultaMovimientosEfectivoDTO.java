/**
 * 2H Software SA de CV
 * 
 * Sistema de Consulta de Estado de Cuenta - Indeval
 *
 * Fecha de creación: Jan 03, 2008
 */
package com.indeval.portaldali.middleware.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * DTO de criterios para la consulta de movimientos de efectivo
 * 
 * @author Emigdio Hernández Rodríguez
 * @version 1.0
 */
public class CriterioConsultaMovimientosEfectivoDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * Emisión asociada a la posición
	 */

	private DivisaDTO divisa;
	/**
	 * Cuenta asociada a la posición
	 */
	private CuentaEfectivoDTO cuenta;
	/**
	 * bóveda asociada a la posición
	 */
	private BovedaDTO boveda;

	/**
	 * Criterio de fecha de inicio
	 */
	private Date fechaInicial = null;
	/**
	 * Criterio de fecha final
	 */
	private Date fechaFinal = null;

	/** La cuenta de valores de la contraparte */
	private CuentaDTO cuentaValoresContraparte = null;

	private CuentaEfectivoDTO cuentaContraparte = null;

	private TipoInstruccionDTO tipoInstruccion = null;

	private TipoOperacionDTO tipoOperacion = null;

	private String folioInstruccion = null;

	private boolean busquedaFechaConcertacion = false;

	private boolean busquedaFechaAplicacion = false;

	private int rolParticipante = -1;

	private int rolContraparte = -1;

	/** Indica si deben ordenarse lo resultados por tipo de instrucción */
	private boolean ordenarPorTipoDeInstruccion = false;

	/** La emisión para la consulta */
	private EmisionDTO emision = null;

	/**
	 * El importe de la busqueda
	 */
	private BigDecimal importe;
	
	/**
	 * Tipo de Retiro
	 */
	private String tipoRetiro;

	/**
	 * Obtiene el campo divisa
	 * 
	 * @return divisa
	 */
	public DivisaDTO getDivisa() {
		return divisa;
	}

	/**
	 * Obtiene el valor del atributo emision
	 * 
	 * @return el valor del atributo emision
	 */
	public EmisionDTO getEmision() {
		return emision;
	}

	/**
	 * Establece el valor del atributo emision
	 * 
	 * @param emision
	 *            el valor del atributo emision a establecer
	 */
	public void setEmision(EmisionDTO emision) {
		this.emision = emision;
	}

	/**
	 * Asigna el valor del campo divisa
	 * 
	 * @param divisa
	 *            el valor de divisa a asignar
	 */
	public void setDivisa(DivisaDTO divisa) {
		this.divisa = divisa;
	}

	/**
	 * Obtiene el valor del atributo cuentaValoresContraparte
	 * 
	 * @return el valor del atributo cuentaValoresContraparte
	 */
	public CuentaDTO getCuentaValoresContraparte() {
		return cuentaValoresContraparte;
	}

	/**
	 * Establece el valor del atributo cuentaValoresContraparte
	 * 
	 * @param cuentaValoresContraparte
	 *            el valor del atributo cuentaValoresContraparte a establecer
	 */
	public void setCuentaValoresContraparte(CuentaDTO cuentaValoresContraparte) {
		this.cuentaValoresContraparte = cuentaValoresContraparte;
	}

	/**
	 * Obtiene el campo cuenta
	 * 
	 * @return cuenta
	 */
	public CuentaEfectivoDTO getCuenta() {
		return cuenta;
	}

	/**
	 * Asigna el valor del campo cuenta
	 * 
	 * @param cuenta
	 *            el valor de cuenta a asignar
	 */
	public void setCuenta(CuentaEfectivoDTO cuenta) {
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
	public CuentaEfectivoDTO getCuentaContraparte() {
		return cuentaContraparte;
	}

	/**
	 * Asigna el valor del campo cuentaContraparte
	 * 
	 * @param cuentaContraparte
	 *            el valor de cuentaContraparte a asignar
	 */
	public void setCuentaContraparte(CuentaEfectivoDTO cuentaContraparte) {
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

	public boolean isOrdenarPorTipoDeInstruccion() {
		return ordenarPorTipoDeInstruccion;
	}

	public void setOrdenarPorTipoDeInstruccion(boolean ordenarPorTipoDeInstruccion) {
		this.ordenarPorTipoDeInstruccion = ordenarPorTipoDeInstruccion;
	}

	/**
	 * @param importe the importe to set
	 */
	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}

	/**
	 * @return the importe
	 */
	public BigDecimal getImporte() {
		return importe;
	}

	/**
	 * @param tipoRetiro the tipoRetiro to set
	 */
	public void setTipoRetiro(String tipoRetiro) {
		this.tipoRetiro = tipoRetiro;
	}

	/**
	 * @return the tipoRetiro
	 */
	public String getTipoRetiro() {
		return tipoRetiro;
	}

}