/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * CriterioEstatusOpEfectivoDTO.java
 * 04/03/2008
 */
package com.indeval.portaldali.middleware.dto;

import java.io.Serializable;
import java.util.Date;

import com.indeval.portaldali.middleware.services.common.constants.RolConstants;

/**
 * Representa los criterios que se utilizan en la pantalla de Estatus de
 * operaciones de efectivo.
 * 
 * @author Juan Carlos Huizar Moreno.
 * 
 */
public class OperacionValorEfectivoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/** Criterio de consulta: tipo de papel. */
	private MercadoDTO tipoPapel;

	/** Criterio de consulta: estado de instruccion. */
	private EstadoInstruccionDTO estadoInstruccion;

	/** Criterio de consulta: fecha de concertación. */
	private Date fechaConcertacion = null;

	/** Criterio de consulta: fecha de liquidación. */
	private Date fechaLiquidacion = null;

	/** Criterio de consulta: rol. */
	private int rol;

	/** Criterio de consulta: cuenta propia. */
	private CuentaDTO cuentaPropia = null;

	/** Criterio de consulta: cuenta contraparte. */
	private CuentaDTO cuentaContraparte = null;

	/** Criterio de consulta: divisa. */
	private DivisaDTO divisa = null;

	/** Criterio de consulta: origen. */
	private String origen = null;

	/** Criterio de consulta: error. */
	private String error = null;

	/** Criterio de consulta: cantidad. */
	private String cantidad = null;

	/** Criterio de consulta: monto. */
	private String monto = null;

	/** Criterio de consulta: folioControl. */
	private String folioControl = null;

	/** Criterio de consulta: folioDescripcion. */
	private String folioDescripcion = null;

	/** Criterio de consulta: bajaDescripcion. */
	private Integer bajaDescripcion = null;

	/** Criterio de consulta: folioUsuario. */
	private String folioUsuario = null;

	/** Criterio de consulta: tipoMensaje. */
	private TipoMensajeDTO tipoMensaje = null;

	/** Criterio de consulta: remitente. */
	private boolean remitente = false;

	/**
	 * Obtiene el campo tipoPapel
	 * 
	 * @return tipoPapel
	 */
	public MercadoDTO getTipoPapel() {
		return tipoPapel;
	}

	/**
	 * Asigna el campo tipoPapel
	 * 
	 * @param tipoPapel
	 *            el valor de tipoPapel a asignar
	 */
	public void setTipoPapel(MercadoDTO tipoPapel) {
		this.tipoPapel = tipoPapel;
	}

	/**
	 * Obtiene el campo estadoInstruccion
	 * 
	 * @return estadoInstruccion
	 */
	public EstadoInstruccionDTO getEstadoInstruccion() {
		return estadoInstruccion;
	}

	/**
	 * Asigna el campo estadoInstruccion
	 * 
	 * @param estadoInstruccion
	 *            el valor de estadoInstruccion a asignar
	 */
	public void setEstadoInstruccion(EstadoInstruccionDTO estadoInstruccion) {
		this.estadoInstruccion = estadoInstruccion;
	}

	/** métodos getters & setters. */

	/**
	 * Obtiene el campo fechaConcertacion
	 * 
	 * @return fechaConcertacion
	 */
	public Date getFechaConcertacion() {
		return fechaConcertacion;
	}

	/**
	 * Asigna el campo fechaConcertacion
	 * 
	 * @param fechaConcertacion
	 *            el valor de fechaConcertacion a asignar
	 */
	public void setFechaConcertacion(Date fechaConcertacion) {
		this.fechaConcertacion = fechaConcertacion;
	}

	/**
	 * Obtiene el campo fechaLiquidacion
	 * 
	 * @return fechaLiquidacion
	 */
	public Date getFechaLiquidacion() {
		return fechaLiquidacion;
	}

	/**
	 * Asigna el campo fechaLiquidacion
	 * 
	 * @param fechaLiquidacion
	 *            el valor de fechaLiquidacion a asignar
	 */
	public void setFechaLiquidacion(Date fechaLiquidacion) {
		this.fechaLiquidacion = fechaLiquidacion;
	}

	/**
	 * Obtiene el campo rol
	 * 
	 * @return rol
	 */
	public int getRol() {
		return rol;
	}

	/**
	 * Asigna el campo rol
	 * 
	 * @param rol
	 *            el valor de rol a asignar
	 */
	public void setRol(int rol) {
		this.rol = rol;
	}

	/**
	 * Obtiene el campo cuentaPropia
	 * 
	 * @return cuentaPropia
	 */
	public CuentaDTO getCuentaPropia() {
		return cuentaPropia;
	}

	/**
	 * Asigna el campo cuentaPropia
	 * 
	 * @param cuentaPropia
	 *            el valor de cuentaPropia a asignar
	 */
	public void setCuentaPropia(CuentaDTO cuentaPropia) {
		this.cuentaPropia = cuentaPropia;
	}

	/**
	 * Obtiene el campo cuentaContrparte
	 * 
	 * @return cuentaContrparte
	 */
	public CuentaDTO getCuentaContraparte() {
		return cuentaContraparte;
	}

	/**
	 * Asigna el campo cuentaContrparte
	 * 
	 * @param cuentaContrparte
	 *            el valor de cuentaContrparte a asignar
	 */
	public void setCuentaContraparte(CuentaDTO cuentaContraparte) {
		this.cuentaContraparte = cuentaContraparte;
	}

	/**
	 * Obtiene el campo origen
	 * 
	 * @return origen
	 */
	public String getOrigen() {
		return origen;
	}

	/**
	 * Asigna el campo origen
	 * 
	 * @param origen
	 *            el valor de origen a asignar
	 */
	public void setOrigen(String origen) {
		this.origen = origen;
	}

	/**
	 * Obtiene el campo error
	 * 
	 * @return error
	 */
	public String getError() {
		return error;
	}

	/**
	 * Asigna el campo error
	 * 
	 * @param error
	 *            el valor de error a asignar
	 */
	public void setError(String error) {
		this.error = error;
	}

	/**
	 * Obtiene el campo cantidad
	 * 
	 * @return cantidad
	 */
	public String getCantidad() {
		return cantidad;
	}

	/**
	 * Asigna el campo cantidad
	 * 
	 * @param cantidad
	 *            el valor de cantidad a asignar
	 */
	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}

	/**
	 * Obtiene el campo monto
	 * 
	 * @return monto
	 */
	public String getMonto() {
		return monto;
	}

	/**
	 * Asigna el campo monto
	 * 
	 * @param monto
	 *            el valor de monto a asignar
	 */
	public void setMonto(String monto) {
		this.monto = monto;
	}

	/**
	 * Obtiene el campo folioControl
	 * 
	 * @return folioControl
	 */
	public String getFolioControl() {
		return folioControl;
	}

	/**
	 * Asigna el campo folioControl
	 * 
	 * @param folioControl
	 *            el valor de folioControl a asignar
	 */
	public void setFolioControl(String folioControl) {
		this.folioControl = folioControl;
	}

	/**
	 * Obtiene el campo folioDescripcion
	 * 
	 * @return folioDescripcion
	 */
	public String getFolioDescripcion() {
		return folioDescripcion;
	}

	/**
	 * Asigna el campo folioDescripcion
	 * 
	 * @param folioDescripcion
	 *            el valor de folioDescripcion a asignar
	 */
	public void setFolioDescripcion(String folioDescripcion) {
		this.folioDescripcion = folioDescripcion;
	}

	/**
	 * Obtiene el campo bajaDescripcion
	 * 
	 * @return bajaDescripcion
	 */
	public Integer getBajaDescripcion() {
		return bajaDescripcion;
	}

	/**
	 * Asigna el campo bajaDescripcion
	 * 
	 * @param bajaDescripcion
	 *            el valor de bajaDescripcion a asignar
	 */
	public void setBajaDescripcion(Integer bajaDescripcion) {
		this.bajaDescripcion = bajaDescripcion;
	}

	/**
	 * Obtiene el campo divisa
	 * 
	 * @return divisa
	 */
	public DivisaDTO getDivisa() {
		return divisa;
	}

	/**
	 * Asigna el campo divisa
	 * 
	 * @param divisa
	 *            el valor de divisa a asignar
	 */
	public void setDivisa(DivisaDTO divisa) {
		this.divisa = divisa;
	}

	/**
	 * Obtiene el campo folioUsuario
	 * 
	 * @return folioUsuario
	 */
	public String getFolioUsuario() {
		return folioUsuario;
	}

	/**
	 * Asigna el campo folioUsuario
	 * 
	 * @param folioUsuario
	 *            el valor de folioUsuario a asignar
	 */
	public void setFolioUsuario(String folioUsuario) {
		this.folioUsuario = folioUsuario;
	}

	/**
	 * Obtiene el campo tipoMensaje
	 * 
	 * @return tipoMensaje
	 */
	public TipoMensajeDTO getTipoMensaje() {
		return tipoMensaje;
	}

	/**
	 * Asigna el campo tipoMensaje
	 * 
	 * @param tipoMensaje
	 *            el valor de tipoMensaje a asignar
	 */
	public void setTipoMensaje(TipoMensajeDTO tipoMensaje) {
		this.tipoMensaje = tipoMensaje;
	}

	/***/

	/**
	 * Obtiene el campo remitente
	 * 
	 * @return remitente
	 */
	public boolean isRemitente() {
		return remitente;
	}

	/**
	 * Asigna el campo remitente
	 * 
	 * @param remitente
	 *            el valor de remitente a asignar
	 */
	public void setRemitente(boolean remitente) {
		this.remitente = remitente;
	}

	/**
	 * Obtiene la cadena con la descripción del rol
	 * 
	 * @return
	 */
	public String getDescripcionRol() {
		return RolConstants.DESCRIPCION_ROLES[rol];
	}
}
