/**
 * CMMV - Portal Dali
 * Copyrigth (c) 2016 CMMV. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.dto.criterio;

import java.io.Serializable;
import java.util.Date;

/**
 * Transfer Object que contiene los parametros de consulta para Operaciones y Match
 * 
 * @author Pablo Balderas
 */
public class ParamConsultaMatchOperacionesTO implements Serializable {

	/** Id para la serializacion */
	private static final long serialVersionUID = 7153570071889430429L;

	/** Institucion Participante */
	private Long idInstitucionParticipante;
	private String institucionParticipante;
	private String cuentaParticipante;

	/** Institucion Contraparte */
	private Long idInstitucionContraparte;
	private String institucionContraparte;
	private String cuentaContraparte;
	
	/** Mercado */
	private Long idMercado;
	private String mercado;
	
	/** Papel */
	private Long idPapel;
	private String papel;
	
	/** Estatus */
	private Long idEstatus;
	private String estatus;
	
	/** Instruccion */
	private Long idInstruccion;
	private String instruccion;
	
	/** Fecha registro/liquidacion */
	private Date fechaRegistro;
	private Date fechaLiquidacion;
	
	/** Rol */
	private Long idRol;
	private String rol;
	
	/** TV / Emisora / Serie */
	private String tv;
	private String emisora;
	private String serie;
	
	/** Folio usuario */
	private String folioUsuario;
	
	/** Mensaje */
	private Long idMensaje;
	private String mensaje;
	
	/** Remitente */
	private boolean remitente;
	
	/** Origen */
	private String origen;
	
	/** Error */
	private Long idError;
	private String error;
	
	/** Cantidad / Monto */
	private String cantidad;
	private String monto;
	
	/** Boveda valores */
	private Long idBovedaValores;
	private String bovedaValores;
	
	/** Divisa */
	private Long idDivisa;
	private String divisa;
	
	/** Boveda Efectivo */
	private Long idBovedaEfectivo;
	private String bovedaEfectivo;
	
	/** Folio Control */
	private String folioControl;
	
	/** Folio Origen */
	private String folioOrigen;

	/**
	 * Método para obtener el atributo idInstitucionParticipante
	 * @return El atributo idInstitucionParticipante
	 */
	public Long getIdInstitucionParticipante() {
		return idInstitucionParticipante;
	}

	/**
	 * Método para establecer el atributo idInstitucionParticipante
	 * @param idInstitucionParticipante El valor del atributo idInstitucionParticipante a establecer.
	 */
	public void setIdInstitucionParticipante(Long idInstitucionParticipante) {
		this.idInstitucionParticipante = idInstitucionParticipante;
	}

	/**
	 * Método para obtener el atributo institucionParticipante
	 * @return El atributo institucionParticipante
	 */
	public String getInstitucionParticipante() {
		return institucionParticipante;
	}

	/**
	 * Método para establecer el atributo institucionParticipante
	 * @param institucionParticipante El valor del atributo institucionParticipante a establecer.
	 */
	public void setInstitucionParticipante(String institucionParticipante) {
		this.institucionParticipante = institucionParticipante;
	}

	/**
	 * Método para obtener el atributo cuentaParticipante
	 * @return El atributo cuentaParticipante
	 */
	public String getCuentaParticipante() {
		return cuentaParticipante;
	}

	/**
	 * Método para establecer el atributo cuentaParticipante
	 * @param cuentaParticipante El valor del atributo cuentaParticipante a establecer.
	 */
	public void setCuentaParticipante(String cuentaParticipante) {
		this.cuentaParticipante = cuentaParticipante;
	}

	/**
	 * Método para obtener el atributo idInstitucionContraparte
	 * @return El atributo idInstitucionContraparte
	 */
	public Long getIdInstitucionContraparte() {
		return idInstitucionContraparte;
	}

	/**
	 * Método para establecer el atributo idInstitucionContraparte
	 * @param idInstitucionContraparte El valor del atributo idInstitucionContraparte a establecer.
	 */
	public void setIdInstitucionContraparte(Long idInstitucionContraparte) {
		this.idInstitucionContraparte = idInstitucionContraparte;
	}

	/**
	 * Método para obtener el atributo institucionContraparte
	 * @return El atributo institucionContraparte
	 */
	public String getInstitucionContraparte() {
		return institucionContraparte;
	}

	/**
	 * Método para establecer el atributo institucionContraparte
	 * @param institucionContraparte El valor del atributo institucionContraparte a establecer.
	 */
	public void setInstitucionContraparte(String institucionContraparte) {
		this.institucionContraparte = institucionContraparte;
	}

	/**
	 * Método para obtener el atributo cuentaContraparte
	 * @return El atributo cuentaContraparte
	 */
	public String getCuentaContraparte() {
		return cuentaContraparte;
	}

	/**
	 * Método para establecer el atributo cuentaContraparte
	 * @param cuentaContraparte El valor del atributo cuentaContraparte a establecer.
	 */
	public void setCuentaContraparte(String cuentaContraparte) {
		this.cuentaContraparte = cuentaContraparte;
	}

	/**
	 * Método para obtener el atributo idMercado
	 * @return El atributo idMercado
	 */
	public Long getIdMercado() {
		return idMercado;
	}

	/**
	 * Método para establecer el atributo idMercado
	 * @param idMercado El valor del atributo idMercado a establecer.
	 */
	public void setIdMercado(Long idMercado) {
		this.idMercado = idMercado;
	}

	/**
	 * Método para obtener el atributo mercado
	 * @return El atributo mercado
	 */
	public String getMercado() {
		return mercado;
	}

	/**
	 * Método para establecer el atributo mercado
	 * @param mercado El valor del atributo mercado a establecer.
	 */
	public void setMercado(String mercado) {
		this.mercado = mercado;
	}

	/**
	 * Método para obtener el atributo idPapel
	 * @return El atributo idPapel
	 */
	public Long getIdPapel() {
		return idPapel;
	}

	/**
	 * Método para establecer el atributo idPapel
	 * @param idPapel El valor del atributo idPapel a establecer.
	 */
	public void setIdPapel(Long idPapel) {
		this.idPapel = idPapel;
	}

	/**
	 * Método para obtener el atributo papel
	 * @return El atributo papel
	 */
	public String getPapel() {
		return papel;
	}

	/**
	 * Método para establecer el atributo papel
	 * @param papel El valor del atributo papel a establecer.
	 */
	public void setPapel(String papel) {
		this.papel = papel;
	}

	/**
	 * Método para obtener el atributo idEstatus
	 * @return El atributo idEstatus
	 */
	public Long getIdEstatus() {
		return idEstatus;
	}

	/**
	 * Método para establecer el atributo idEstatus
	 * @param idEstatus El valor del atributo idEstatus a establecer.
	 */
	public void setIdEstatus(Long idEstatus) {
		this.idEstatus = idEstatus;
	}

	/**
	 * Método para obtener el atributo estatus
	 * @return El atributo estatus
	 */
	public String getEstatus() {
		return estatus;
	}

	/**
	 * Método para establecer el atributo estatus
	 * @param estatus El valor del atributo estatus a establecer.
	 */
	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	/**
	 * Método para obtener el atributo idInstruccion
	 * @return El atributo idInstruccion
	 */
	public Long getIdInstruccion() {
		return idInstruccion;
	}

	/**
	 * Método para establecer el atributo idInstruccion
	 * @param idInstruccion El valor del atributo idInstruccion a establecer.
	 */
	public void setIdInstruccion(Long idInstruccion) {
		this.idInstruccion = idInstruccion;
	}

	/**
	 * Método para obtener el atributo instruccion
	 * @return El atributo instruccion
	 */
	public String getInstruccion() {
		return instruccion;
	}

	/**
	 * Método para establecer el atributo instruccion
	 * @param instruccion El valor del atributo instruccion a establecer.
	 */
	public void setInstruccion(String instruccion) {
		this.instruccion = instruccion;
	}

	/**
	 * Método para obtener el atributo fechaRegistro
	 * @return El atributo fechaRegistro
	 */
	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	/**
	 * Método para establecer el atributo fechaRegistro
	 * @param fechaRegistro El valor del atributo fechaRegistro a establecer.
	 */
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	/**
	 * Método para obtener el atributo fechaLiquidacion
	 * @return El atributo fechaLiquidacion
	 */
	public Date getFechaLiquidacion() {
		return fechaLiquidacion;
	}

	/**
	 * Método para establecer el atributo fechaLiquidacion
	 * @param fechaLiquidacion El valor del atributo fechaLiquidacion a establecer.
	 */
	public void setFechaLiquidacion(Date fechaLiquidacion) {
		this.fechaLiquidacion = fechaLiquidacion;
	}

	/**
	 * Método para obtener el atributo idRol
	 * @return El atributo idRol
	 */
	public Long getIdRol() {
		return idRol;
	}

	/**
	 * Método para establecer el atributo idRol
	 * @param idRol El valor del atributo idRol a establecer.
	 */
	public void setIdRol(Long idRol) {
		this.idRol = idRol;
	}

	/**
	 * Método para obtener el atributo rol
	 * @return El atributo rol
	 */
	public String getRol() {
		return rol;
	}

	/**
	 * Método para establecer el atributo rol
	 * @param rol El valor del atributo rol a establecer.
	 */
	public void setRol(String rol) {
		this.rol = rol;
	}

	/**
	 * Método para obtener el atributo tv
	 * @return El atributo tv
	 */
	public String getTv() {
		return tv;
	}

	/**
	 * Método para establecer el atributo tv
	 * @param tv El valor del atributo tv a establecer.
	 */
	public void setTv(String tv) {
		this.tv = tv;
	}

	/**
	 * Método para obtener el atributo emisora
	 * @return El atributo emisora
	 */
	public String getEmisora() {
		return emisora;
	}

	/**
	 * Método para establecer el atributo emisora
	 * @param emisora El valor del atributo emisora a establecer.
	 */
	public void setEmisora(String emisora) {
		this.emisora = emisora;
	}

	/**
	 * Método para obtener el atributo serie
	 * @return El atributo serie
	 */
	public String getSerie() {
		return serie;
	}

	/**
	 * Método para establecer el atributo serie
	 * @param serie El valor del atributo serie a establecer.
	 */
	public void setSerie(String serie) {
		this.serie = serie;
	}

	/**
	 * Método para obtener el atributo folioUsuario
	 * @return El atributo folioUsuario
	 */
	public String getFolioUsuario() {
		return folioUsuario;
	}

	/**
	 * Método para establecer el atributo folioUsuario
	 * @param folioUsuario El valor del atributo folioUsuario a establecer.
	 */
	public void setFolioUsuario(String folioUsuario) {
		this.folioUsuario = folioUsuario;
	}

	/**
	 * Método para obtener el atributo idMensaje
	 * @return El atributo idMensaje
	 */
	public Long getIdMensaje() {
		return idMensaje;
	}

	/**
	 * Método para establecer el atributo idMensaje
	 * @param idMensaje El valor del atributo idMensaje a establecer.
	 */
	public void setIdMensaje(Long idMensaje) {
		this.idMensaje = idMensaje;
	}

	/**
	 * Método para obtener el atributo mensaje
	 * @return El atributo mensaje
	 */
	public String getMensaje() {
		return mensaje;
	}

	/**
	 * Método para establecer el atributo mensaje
	 * @param mensaje El valor del atributo mensaje a establecer.
	 */
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	/**
	 * Método para obtener el atributo remitente
	 * @return El atributo remitente
	 */
	public boolean isRemitente() {
		return remitente;
	}

	/**
	 * Método para establecer el atributo remitente
	 * @param remitente El valor del atributo remitente a establecer.
	 */
	public void setRemitente(boolean remitente) {
		this.remitente = remitente;
	}

	/**
	 * Método para obtener el atributo origen
	 * @return El atributo origen
	 */
	public String getOrigen() {
		return origen;
	}

	/**
	 * Método para establecer el atributo origen
	 * @param origen El valor del atributo origen a establecer.
	 */
	public void setOrigen(String origen) {
		this.origen = origen;
	}

	/**
	 * Método para obtener el atributo idError
	 * @return El atributo idError
	 */
	public Long getIdError() {
		return idError;
	}

	/**
	 * Método para establecer el atributo idError
	 * @param idError El valor del atributo idError a establecer.
	 */
	public void setIdError(Long idError) {
		this.idError = idError;
	}

	/**
	 * Método para obtener el atributo error
	 * @return El atributo error
	 */
	public String getError() {
		return error;
	}

	/**
	 * Método para establecer el atributo error
	 * @param error El valor del atributo error a establecer.
	 */
	public void setError(String error) {
		this.error = error;
	}

	/**
	 * Método para obtener el atributo cantidad
	 * @return El atributo cantidad
	 */
	public String getCantidad() {
		return cantidad;
	}

	/**
	 * Método para establecer el atributo cantidad
	 * @param cantidad El valor del atributo cantidad a establecer.
	 */
	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}

	/**
	 * Método para obtener el atributo monto
	 * @return El atributo monto
	 */
	public String getMonto() {
		return monto;
	}

	/**
	 * Método para establecer el atributo monto
	 * @param monto El valor del atributo monto a establecer.
	 */
	public void setMonto(String monto) {
		this.monto = monto;
	}

	/**
	 * Método para obtener el atributo idBovedaValores
	 * @return El atributo idBovedaValores
	 */
	public Long getIdBovedaValores() {
		return idBovedaValores;
	}

	/**
	 * Método para establecer el atributo idBovedaValores
	 * @param idBovedaValores El valor del atributo idBovedaValores a establecer.
	 */
	public void setIdBovedaValores(Long idBovedaValores) {
		this.idBovedaValores = idBovedaValores;
	}

	/**
	 * Método para obtener el atributo bovedaValores
	 * @return El atributo bovedaValores
	 */
	public String getBovedaValores() {
		return bovedaValores;
	}

	/**
	 * Método para establecer el atributo bovedaValores
	 * @param bovedaValores El valor del atributo bovedaValores a establecer.
	 */
	public void setBovedaValores(String bovedaValores) {
		this.bovedaValores = bovedaValores;
	}

	/**
	 * Método para obtener el atributo idDivisa
	 * @return El atributo idDivisa
	 */
	public Long getIdDivisa() {
		return idDivisa;
	}

	/**
	 * Método para establecer el atributo idDivisa
	 * @param idDivisa El valor del atributo idDivisa a establecer.
	 */
	public void setIdDivisa(Long idDivisa) {
		this.idDivisa = idDivisa;
	}

	/**
	 * Método para obtener el atributo divisa
	 * @return El atributo divisa
	 */
	public String getDivisa() {
		return divisa;
	}

	/**
	 * Método para establecer el atributo divisa
	 * @param divisa El valor del atributo divisa a establecer.
	 */
	public void setDivisa(String divisa) {
		this.divisa = divisa;
	}

	/**
	 * Método para obtener el atributo idBovedaEfectivo
	 * @return El atributo idBovedaEfectivo
	 */
	public Long getIdBovedaEfectivo() {
		return idBovedaEfectivo;
	}

	/**
	 * Método para establecer el atributo idBovedaEfectivo
	 * @param idBovedaEfectivo El valor del atributo idBovedaEfectivo a establecer.
	 */
	public void setIdBovedaEfectivo(Long idBovedaEfectivo) {
		this.idBovedaEfectivo = idBovedaEfectivo;
	}

	/**
	 * Método para obtener el atributo bovedaEfectivo
	 * @return El atributo bovedaEfectivo
	 */
	public String getBovedaEfectivo() {
		return bovedaEfectivo;
	}

	/**
	 * Método para establecer el atributo bovedaEfectivo
	 * @param bovedaEfectivo El valor del atributo bovedaEfectivo a establecer.
	 */
	public void setBovedaEfectivo(String bovedaEfectivo) {
		this.bovedaEfectivo = bovedaEfectivo;
	}

	/**
	 * Método para obtener el atributo folioControl
	 * @return El atributo folioControl
	 */
	public String getFolioControl() {
		return folioControl;
	}

	/**
	 * Método para establecer el atributo folioControl
	 * @param folioControl El valor del atributo folioControl a establecer.
	 */
	public void setFolioControl(String folioControl) {
		this.folioControl = folioControl;
	}

	/**
	 * Método para obtener el atributo folioOrigen
	 * @return El atributo folioOrigen
	 */
	public String getFolioOrigen() {
		return folioOrigen;
	}

	/**
	 * Método para establecer el atributo folioOrigen
	 * @param folioOrigen El valor del atributo folioOrigen a establecer.
	 */
	public void setFolioOrigen(String folioOrigen) {
		this.folioOrigen = folioOrigen;
	}
}
