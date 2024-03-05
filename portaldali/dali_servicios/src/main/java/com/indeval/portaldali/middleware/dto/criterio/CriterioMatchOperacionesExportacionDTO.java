/**
 * Bursatec 2016 - INDEVAL Portal DALI
 */
package com.indeval.portaldali.middleware.dto.criterio;

import java.io.Serializable;
import java.util.Date;

/**
 * Representa los criterios que se utilizan en la pantalla de match y estatus de
 * operaciones para la exportacion de datos.
 * 
 */
public class CriterioMatchOperacionesExportacionDTO implements Serializable {

	/**
	 * SV
	 */
	private static final long serialVersionUID = -4623360L;

	/**
	 * Id de mercado
	 */
	private long idMercado = 0;
	/**
	 * Clave mercado
	 */
	private String claveMercado;
	/**
	 * Id de estado de instruccion
	 */
	private int idEstadoInstruccion;
	/**
	 * Id de la divisa
	 */
    private long idDivisa;
    /**
     * Clave alfabetica de la divisa
     */
    private String claveAlfabetica;
    /**
     * Identificador del tipo de instruccion
     */
    private Long idTipoInstruccion;
    /**
     * Nombre corto del tipo de instruccion
     */
    private String nombreCortoTipoInstruccion;
	/**
	 * fecha de concertacion
	 */
	private Date fechaConcertacion;
	/**
	 * fecha de liquidacion
	 */
	private Date fechaLiquidacion;

	private int rol;
    /**
     * Numero de cuenta de 4 posiciones
     */
    private String cuentaParticipante;
    /**
     * Numero de cuenta de 4 posiciones
     */
    private String cuentaContraparte;

    private long idTipoValor = 0;

    private String claveTipoValor = null;
    /**
     * El identificador de la emisora
     */
    private long idEmisora = 0;
    /**
     * La descripcion de la emisora
     */
    private String descripcionEmisora;

    private String serie;

	private String folioUsuario = null;
	/**
     * Identificador de tipo mensaje
     */
    private int idTipoMensaje;
    /**
     * Clave de tipo mensaje
     */
    private String claveTipoMensaje;

	private boolean remitente = false;

	private String origen;
    /**
     * La clave del error de liquidacion
     */
    private String claveError;

	private String cantidad;

	private String monto;

	private String folioControl;
    /**
     * El identificador de la institucion participante
     */
    private long idInstitucionParticipante = 0;
    /**
     * La clave del tipo de institucion participante
     */
    private String claveTipoInstitucionParticipante;
    /**
     * El folio de la institucion participante
     */
    private String folioInstitucionParticipante;
    /**
     * El identificador de la institucion contraparte
     */
    private long idInstitucionContraparte = 0;
    /**
     * La clave del tipo de institucion contraparte
     */
    private String claveTipoInstitucionContraparte;
    /**
     * El folio de la institucion contraparte
     */
    private String folioInstitucionContraparte;

	private Date fechaInicioPeriodo;

	private Date fechaFinPeriodo;
    /**
     * El identificador de la boveda de valores
     */
    private long idBovedaValores = 0;
    /**
     * Nombre corto boveda valores
     */
    private String nombreCortoBovedaValores;
    /**
     * El identificador de la boveda de efectivo
     */
    private long idBovedaEfectivo = 0;
    /**
     * Nombre corto boveda efectivo
     */
    private String nombreCortoBovedaEfectivo;

	private String referenciaPaquete;

    public long getIdMercado() {
        return idMercado;
    }

    public void setIdMercado(long idMercado) {
        this.idMercado = idMercado;
    }

    public String getClaveMercado() {
        return claveMercado;
    }

    public void setClaveMercado(String claveMercado) {
        this.claveMercado = claveMercado;
    }

    public int getIdEstadoInstruccion() {
        return idEstadoInstruccion;
    }

    public void setIdEstadoInstruccion(int idEstadoInstruccion) {
        this.idEstadoInstruccion = idEstadoInstruccion;
    }

    public long getIdDivisa() {
        return idDivisa;
    }

    public void setIdDivisa(long idDivisa) {
        this.idDivisa = idDivisa;
    }

    public String getClaveAlfabetica() {
        return claveAlfabetica;
    }

    public void setClaveAlfabetica(String claveAlfabetica) {
        this.claveAlfabetica = claveAlfabetica;
    }

    public Long getIdTipoInstruccion() {
        return idTipoInstruccion;
    }

    public void setIdTipoInstruccion(Long idTipoInstruccion) {
        this.idTipoInstruccion = idTipoInstruccion;
    }

    public String getNombreCortoTipoInstruccion() {
        return nombreCortoTipoInstruccion;
    }

    public void setNombreCortoTipoInstruccion(String nombreCortoTipoInstruccion) {
        this.nombreCortoTipoInstruccion = nombreCortoTipoInstruccion;
    }

    public Date getFechaConcertacion() {
        return fechaConcertacion;
    }

    public void setFechaConcertacion(Date fechaConcertacion) {
        this.fechaConcertacion = fechaConcertacion;
    }

    public Date getFechaLiquidacion() {
        return fechaLiquidacion;
    }

    public void setFechaLiquidacion(Date fechaLiquidacion) {
        this.fechaLiquidacion = fechaLiquidacion;
    }

    public int getRol() {
        return rol;
    }

    public void setRol(int rol) {
        this.rol = rol;
    }

    public String getCuentaParticipante() {
        return cuentaParticipante;
    }

    public void setCuentaParticipante(String cuentaParticipante) {
        this.cuentaParticipante = cuentaParticipante;
    }

    public String getCuentaContraparte() {
        return cuentaContraparte;
    }

    public void setCuentaContraparte(String cuentaContraparte) {
        this.cuentaContraparte = cuentaContraparte;
    }

    public long getIdTipoValor() {
        return idTipoValor;
    }

    public void setIdTipoValor(long idTipoValor) {
        this.idTipoValor = idTipoValor;
    }

    public String getClaveTipoValor() {
        return claveTipoValor;
    }

    public void setClaveTipoValor(String claveTipoValor) {
        this.claveTipoValor = claveTipoValor;
    }

    public long getIdEmisora() {
        return idEmisora;
    }

    public void setIdEmisora(long idEmisora) {
        this.idEmisora = idEmisora;
    }

    public String getDescripcionEmisora() {
        return descripcionEmisora;
    }

    public void setDescripcionEmisora(String descripcionEmisora) {
        this.descripcionEmisora = descripcionEmisora;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getFolioUsuario() {
        return folioUsuario;
    }

    public void setFolioUsuario(String folioUsuario) {
        this.folioUsuario = folioUsuario;
    }

    public int getIdTipoMensaje() {
        return idTipoMensaje;
    }

    public void setIdTipoMensaje(int idTipoMensaje) {
        this.idTipoMensaje = idTipoMensaje;
    }

    public String getClaveTipoMensaje() {
        return claveTipoMensaje;
    }

    public void setClaveTipoMensaje(String claveTipoMensaje) {
        this.claveTipoMensaje = claveTipoMensaje;
    }

    public boolean isRemitente() {
        return remitente;
    }

    public void setRemitente(boolean remitente) {
        this.remitente = remitente;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getClaveError() {
        return claveError;
    }

    public void setClaveError(String claveError) {
        this.claveError = claveError;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getFolioControl() {
        return folioControl;
    }

    public void setFolioControl(String folioControl) {
        this.folioControl = folioControl;
    }

    public long getIdInstitucionParticipante() {
        return idInstitucionParticipante;
    }

    public void setIdInstitucionParticipante(long idInstitucionParticipante) {
        this.idInstitucionParticipante = idInstitucionParticipante;
    }

    public String getClaveTipoInstitucionParticipante() {
        return claveTipoInstitucionParticipante;
    }

    public void setClaveTipoInstitucionParticipante(String claveTipoInstitucionParticipante) {
        this.claveTipoInstitucionParticipante = claveTipoInstitucionParticipante;
    }

    public String getFolioInstitucionParticipante() {
        return folioInstitucionParticipante;
    }

    public void setFolioInstitucionParticipante(String folioInstitucionParticipante) {
        this.folioInstitucionParticipante = folioInstitucionParticipante;
    }

    public long getIdInstitucionContraparte() {
        return idInstitucionContraparte;
    }

    public void setIdInstitucionContraparte(long idInstitucionContraparte) {
        this.idInstitucionContraparte = idInstitucionContraparte;
    }

    public String getClaveTipoInstitucionContraparte() {
        return claveTipoInstitucionContraparte;
    }

    public void setClaveTipoInstitucionContraparte(String claveTipoInstitucionContraparte) {
        this.claveTipoInstitucionContraparte = claveTipoInstitucionContraparte;
    }

    public String getFolioInstitucionContraparte() {
        return folioInstitucionContraparte;
    }

    public void setFolioInstitucionContraparte(String folioInstitucionContraparte) {
        this.folioInstitucionContraparte = folioInstitucionContraparte;
    }

    public Date getFechaInicioPeriodo() {
        return fechaInicioPeriodo;
    }

    public void setFechaInicioPeriodo(Date fechaInicioPeriodo) {
        this.fechaInicioPeriodo = fechaInicioPeriodo;
    }

    public Date getFechaFinPeriodo() {
        return fechaFinPeriodo;
    }

    public void setFechaFinPeriodo(Date fechaFinPeriodo) {
        this.fechaFinPeriodo = fechaFinPeriodo;
    }

    public long getIdBovedaValores() {
        return idBovedaValores;
    }

    public void setIdBovedaValores(long idBovedaValores) {
        this.idBovedaValores = idBovedaValores;
    }

    public String getNombreCortoBovedaValores() {
        return nombreCortoBovedaValores;
    }

    public void setNombreCortoBovedaValores(String nombreCortoBovedaValores) {
        this.nombreCortoBovedaValores = nombreCortoBovedaValores;
    }

    public long getIdBovedaEfectivo() {
        return idBovedaEfectivo;
    }

    public void setIdBovedaEfectivo(long idBovedaEfectivo) {
        this.idBovedaEfectivo = idBovedaEfectivo;
    }

    public String getNombreCortoBovedaEfectivo() {
        return nombreCortoBovedaEfectivo;
    }

    public void setNombreCortoBovedaEfectivo(String nombreCortoBovedaEfectivo) {
        this.nombreCortoBovedaEfectivo = nombreCortoBovedaEfectivo;
    }

    public String getReferenciaPaquete() {
        return referenciaPaquete;
    }

    public void setReferenciaPaquete(String referenciaPaquete) {
        this.referenciaPaquete = referenciaPaquete;
    }

}
