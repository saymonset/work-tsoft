/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * CriterioMatchOperacionesDTO.java
 * 27/02/2008
 */
package com.indeval.portaldali.middleware.dto.criterio;

import java.io.Serializable;
import java.util.Date;

import com.indeval.portaldali.middleware.dto.BovedaDTO;
import com.indeval.portaldali.middleware.dto.CuentaDTO;
import com.indeval.portaldali.middleware.dto.DivisaDTO;
import com.indeval.portaldali.middleware.dto.EmisionDTO;
import com.indeval.portaldali.middleware.dto.ErrorDaliDTO;
import com.indeval.portaldali.middleware.dto.EstadoInstruccionDTO;
import com.indeval.portaldali.middleware.dto.InstitucionDTO;
import com.indeval.portaldali.middleware.dto.MercadoDTO;
import com.indeval.portaldali.middleware.dto.TipoInstruccionDTO;
import com.indeval.portaldali.middleware.dto.TipoMensajeDTO;
import com.indeval.portaldali.middleware.services.common.constants.RolConstants;

/**
 * Representa los criterios que se utilizan en la pantalla de match y estatus de
 * operaciones
 * 
 * @author Emigdio Hernández
 * @version 1.0
 */
public class CriterioMatchOperacionesDTO implements Serializable {

	/**
	 * SV
	 */
	private static final long serialVersionUID = -4623360913319653582L;

	/**
	 * Criterio de Tipo de mercado
	 */
	private MercadoDTO mercado = null;
	/**
	 * Criterio de estado de instrucción
	 */
	private EstadoInstruccionDTO estadoInstruccion = null;
	
	private DivisaDTO divisa = null;
	/**
	 * Criterio de tipo de instrucción
	 */
	private TipoInstruccionDTO tipoInstruccion = null;
	/**
	 * fecha de concertación
	 */
	private Date fechaConcertacion = null;
	/**
	 * fecha de liquidación
	 */
	private Date fechaLiquidacion = null;

	private int rol;

	private CuentaDTO cuentaParticipante = null;

	private CuentaDTO cuentaContraparte = null;

	private EmisionDTO emision = new EmisionDTO();

	private String folioUsuario = null;

	private TipoMensajeDTO tipoMensaje = null;

	private boolean remitente = false;

	private String origen = null;

	private ErrorDaliDTO error = new ErrorDaliDTO();

	private String cantidad;

	private String monto;

	private String folioControl;

	private InstitucionDTO institucionParticipante = null;

	private InstitucionDTO institucionContraparte = null;

	private Date fechaInicioPeriodo;

	private Date fechaFinPeriodo;	
	
	private BovedaDTO bovedaValores =  null;
	
	private BovedaDTO bovedaEfectivo =  null; 

	private String referenciaPaquete;
	
	/**
	 * Obtiene el campo mercado
	 * 
	 * @return mercado
	 */
	public MercadoDTO getMercado() {
		return mercado;
	}

	/**
	 * Asigna el campo mercado
	 * 
	 * @param mercado
	 *            el valor de mercado a asignar
	 */
	public void setMercado(MercadoDTO mercado) {
		this.mercado = mercado;
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
	 * @return the institucionContraparte
	 */
	public InstitucionDTO getInstitucionContraparte() {
		return institucionContraparte;
	}

	/**
	 * @param institucionContraparte
	 *            the institucionContraparte to set
	 */
	public void setInstitucionContraparte(InstitucionDTO institucionContraparte) {
		this.institucionContraparte = institucionContraparte;
	}

	/**
	 * Asigna el campo estadoInstruccion
	 * 
	 * @param estadoInstruccion
	 *            el valor de estadoInstruccion a asignar
	 */
	public void setEstadoInstruccion
	(EstadoInstruccionDTO estadoInstruccion) {
		this.estadoInstruccion = estadoInstruccion;
	}

	public DivisaDTO getDivisa() {
		return divisa;
	}

	public void setDivisa(DivisaDTO divisa) {
		this.divisa = divisa;
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
	 * Asigna el campo tipoInstruccion
	 * 
	 * @param tipoInstruccion
	 *            el valor de tipoInstruccion a asignar
	 */
	public void setTipoInstruccion(TipoInstruccionDTO tipoInstruccion) {
		this.tipoInstruccion = tipoInstruccion;
	}

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
	 * Obtiene el campo cuentaParticipante
	 * 
	 * @return cuentaParticipante
	 */
	public CuentaDTO getCuentaParticipante() {
		return cuentaParticipante;
	}

	/**
	 * Asigna el campo cuentaParticipante
	 * 
	 * @param cuentaParticipante
	 *            el valor de cuentaParticipante a asignar
	 */
	public void setCuentaParticipante(CuentaDTO cuentaParticipante) {
		this.cuentaParticipante = cuentaParticipante;
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
	 * Asigna el campo cuentaContraparte
	 * 
	 * @param cuentaContraparte
	 *            el valor de cuentaContraparte a asignar
	 */
	public void setCuentaContraparte(CuentaDTO cuentaContraparte) {
		this.cuentaContraparte = cuentaContraparte;
	}

	/**
	 * Obtiene el campo emision
	 * 
	 * @return emision
	 */
	public EmisionDTO getEmision() {
		return emision;
	}

	/**
	 * Asigna el campo emision
	 * 
	 * @param emision
	 *            el valor de emision a asignar
	 */
	public void setEmision(EmisionDTO emision) {
		this.emision = emision;
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
	 * Obtiene el valor del atributo error
	 * 
	 * @return el valor del atributo error
	 */
	public ErrorDaliDTO getError() {
		return error;
	}

	/**
	 * Establece el valor del atributo error
	 * 
	 * @param error
	 *            el valor del atributo error a establecer
	 */
	public void setError(ErrorDaliDTO error) {
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
	 * Obtiene la cadena con la descripción del rol
	 * 
	 * @return
	 */
	public String getDescripcionRol() {
		return RolConstants.DESCRIPCION_ROLES[rol];
	}

	/**
	 * Obtiene el campo institucionParticipante
	 * 
	 * @return institucionParticipante
	 */
	public InstitucionDTO getInstitucionParticipante() {
		return institucionParticipante;
	}

	/**
	 * Asigna el campo institucionParticipante
	 * 
	 * @param institucionParticipante
	 *            el valor de institucionParticipante a asignar
	 */
	public void setInstitucionParticipante(InstitucionDTO institucionParticipante) {
		this.institucionParticipante = institucionParticipante;
	}

	/**
	 * @return the fechaInicioPeriodo
	 */
	public Date getFechaInicioPeriodo() {
		return fechaInicioPeriodo;
	}

	/**
	 * @param fechaInicioPeriodo the fechaInicioPeriodo to set
	 */
	public void setFechaInicioPeriodo(Date fechaInicioPeriodo) {
		this.fechaInicioPeriodo = fechaInicioPeriodo;
	}

	/**
	 * @return the fechaFinPeriodo
	 */
	public Date getFechaFinPeriodo() {
		return fechaFinPeriodo;
	}

	/**
	 * @param fechaFinPeriodo the fechaFinPeriodo to set
	 */
	public void setFechaFinPeriodo(Date fechaFinPeriodo) {
		this.fechaFinPeriodo = fechaFinPeriodo;
	}

	/**
	 * @return the bovedaValores
	 */
	public BovedaDTO getBovedaValores() {
		return bovedaValores;
	}

	/**
	 * @param bovedaValores the bovedaValores to set
	 */
	public void setBovedaValores(BovedaDTO bovedaValores) {
		this.bovedaValores = bovedaValores;
	}

	/**
	 * @return the bovedaEfectivo
	 */
	public BovedaDTO getBovedaEfectivo() {
		return bovedaEfectivo;
	}

	/**
	 * @param bovedaEfectivo the bovedaEfectivo to set
	 */
	public void setBovedaEfectivo(BovedaDTO bovedaEfectivo) {
		this.bovedaEfectivo = bovedaEfectivo;
	}

	public String getReferenciaPaquete() {
		return referenciaPaquete;
	}

	public void setReferenciaPaquete(String referenciaPaquete) {
		this.referenciaPaquete = referenciaPaquete;
	}

}
