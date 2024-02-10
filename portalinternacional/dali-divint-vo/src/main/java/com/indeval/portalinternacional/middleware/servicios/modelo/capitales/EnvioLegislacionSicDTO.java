package com.indeval.portalinternacional.middleware.servicios.modelo.capitales;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EnvioLegislacionSicDTO implements Comparable<EnvioLegislacionSicDTO>{
	
	private Long idEnvio;
	
	private Long idCalendario;
	
	private Long idHistorico;
	
	private String usuario;
	
	private String usuarioAutoriza;
	
	private String destinatario;
	
	private Integer estado;
	
	private Date fechaSolicitudInicio;
	private Date fechaSolicitudFin;	
	private Date fechaAutorizacionInicio;
	private Date fechaAutorizacionFin;	
	private String estadoCadena;
	private String folioMensaje;
	private String folioIndeval;
	private String tipoDerechoCaev;
	private String tipoDerechoCamv;
	private Date fechaExdateInicio;
	private Date fechaExdateFin;
	private Date fechaHoraRecepcionInicio;
	private Date fechaHoraRecepcionFin;
	private String fuente;
	private String isin;
	private String tipoValor;
	private String serie;
	private String cupon;
	private String emisora;
	private Date fechaCorteInicio;
	private Date fechaCorteFin;
	private String estadoEmision;
	
	
	
	private static SimpleDateFormat FORMATO_FECHA_PANTALLA = new SimpleDateFormat("dd/MM/yyyy");
	
	
	public EnvioLegislacionSicDTO(){}

	/**
	 * @return the idEnvio
	 */
	public Long getIdEnvio() {
		return idEnvio;
	}

	/**
	 * @param idEnvio the idEnvio to set
	 */
	public void setIdEnvio(Long idEnvio) {
		this.idEnvio = idEnvio;
	}

	/**
	 * @return the idCalendario
	 */
	public Long getIdCalendario() {
		return idCalendario;
	}

	/**
	 * @param idCalendario the idCalendario to set
	 */
	public void setIdCalendario(Long idCalendario) {
		this.idCalendario = idCalendario;
	}

	/**
	 * @return the idHistorico
	 */
	public Long getIdHistorico() {
		return idHistorico;
	}

	/**
	 * @param idHistorico the idHistorico to set
	 */
	public void setIdHistorico(Long idHistorico) {
		this.idHistorico = idHistorico;
	}

	/**
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario != null ? usuario.toUpperCase() : null;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the usuarioAutoriza
	 */
	public String getUsuarioAutoriza() {
		return usuarioAutoriza != null ? usuarioAutoriza.toUpperCase() : null;
	}

	/**
	 * @param usuarioAutoriza the usuarioAutoriza to set
	 */
	public void setUsuarioAutoriza(String usuarioAutoriza) {
		this.usuarioAutoriza = usuarioAutoriza;
	}

	/**
	 * @return the destinatario
	 */
	public String getDestinatario() {
		return destinatario;
	}

	/**
	 * @param destinatario the destinatario to set
	 */
	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

	/**
	 * @return the estado
	 */
	public Integer getEstado() {
		return estado;
	}

	/**
	 * @param estado the estado to set
	 */
	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	/**
	 * @return the estadoCadena
	 */
	public String getEstadoCadena() {
		return estadoCadena;
	}

	/**
	 * @param estadoCadena the estadoCadena to set
	 */
	public void setEstadoCadena(String estadoCadena) {
		this.estadoCadena = estadoCadena;
	}

	/**
	 * @return the fechaSolicitudInicio
	 */
	public Date getFechaSolicitudInicio() {
		return fechaSolicitudInicio;
	}

	/**
	 * @param fechaSolicitudInicio the fechaSolicitudInicio to set
	 */
	public void setFechaSolicitudInicio(Date fechaSolicitudInicio) {
		this.fechaSolicitudInicio = fechaSolicitudInicio;
	}

	/**
	 * @return the fechaSolicitudFin
	 */
	public Date getFechaSolicitudFin() {
		return fechaSolicitudFin;
	}

	/**
	 * @param fechaSolicitudFin the fechaSolicitudFin to set
	 */
	public void setFechaSolicitudFin(Date fechaSolicitudFin) {
		this.fechaSolicitudFin = fechaSolicitudFin;
	}

	/**
	 * @return the fechaAutorizacionInicio
	 */
	public Date getFechaAutorizacionInicio() {
		return fechaAutorizacionInicio;
	}

	/**
	 * @param fechaAutorizacionInicio the fechaAutorizacionInicio to set
	 */
	public void setFechaAutorizacionInicio(Date fechaAutorizacionInicio) {
		this.fechaAutorizacionInicio = fechaAutorizacionInicio;
	}

	/**
	 * @return the fechaAutorizacionFin
	 */
	public Date getFechaAutorizacionFin() {
		return fechaAutorizacionFin;
	}

	/**
	 * @param fechaAutorizacionFin the fechaAutorizacionFin to set
	 */
	public void setFechaAutorizacionFin(Date fechaAutorizacionFin) {
		this.fechaAutorizacionFin = fechaAutorizacionFin;
	}

	/**
	 * @return the folioMensaje
	 */
	public String getFolioMensaje() {
		return folioMensaje;
	}

	/**
	 * @param folioMensaje the folioMensaje to set
	 */
	public void setFolioMensaje(String folioMensaje) {
		this.folioMensaje = folioMensaje;
	}

	/**
	 * @return the folioIndeval
	 */
	public String getFolioIndeval() {
		return folioIndeval;
	}

	/**
	 * @param folioIndeval the folioIndeval to set
	 */
	public void setFolioIndeval(String folioIndeval) {
		this.folioIndeval = folioIndeval;
	}
	
	
	/**
	 * @return the tipoDerechoCaev
	 */
	public String getTipoDerechoCaev() {
		return tipoDerechoCaev;
	}

	/**
	 * @param tipoDerechoCaev the tipoDerechoCaev to set
	 */
	public void setTipoDerechoCaev(String tipoDerechoCaev) {
		this.tipoDerechoCaev = tipoDerechoCaev;
	}

	/**
	 * @return the tipoDerechoCamv
	 */
	public String getTipoDerechoCamv() {
		return tipoDerechoCamv;
	}

	/**
	 * @param tipoDerechoCamv the tipoDerechoCamv to set
	 */
	public void setTipoDerechoCamv(String tipoDerechoCamv) {
		this.tipoDerechoCamv = tipoDerechoCamv;
	}

	/**
	 * @return the fuente
	 */
	public String getFuente() {
		return fuente;
	}

	/**
	 * @param fuente the fuente to set
	 */
	public void setFuente(String fuente) {
		this.fuente = fuente;
	}

	/**
	 * @return the isin
	 */
	public String getIsin() {
		return isin != null ? isin.toUpperCase() : null;
	}

	/**
	 * @param isin the isin to set
	 */
	public void setIsin(String isin) {
		this.isin = isin;
	}

	/**
	 * @return the tipoValor
	 */
	public String getTipoValor() {
		return tipoValor != null ? tipoValor.toUpperCase() : null;
	}

	/**
	 * @param tipoValor the tipoValor to set
	 */
	public void setTipoValor(String tipoValor) {
		this.tipoValor = tipoValor;
	}

	/**
	 * @return the serie
	 */
	public String getSerie() {
		return serie != null ? serie.toUpperCase() : null;
	}

	/**
	 * @param serie the serie to set
	 */
	public void setSerie(String serie) {
		this.serie = serie;
	}

	/**
	 * @return the cupon
	 */
	public String getCupon() {
		return cupon;
	}

	/**
	 * @param cupon the cupon to set
	 */
	public void setCupon(String cupon) {
		this.cupon = cupon;
	}

	/**
	 * @return the emisora
	 */
	public String getEmisora() {
		return emisora != null ? emisora.toUpperCase() : null;
	}

	/**
	 * @param emisora the emisora to set
	 */
	public void setEmisora(String emisora) {
		this.emisora = emisora;
	}

	/**
	 * @return the fechaExdateInicio
	 */
	public Date getFechaExdateInicio() {
		return fechaExdateInicio;
	}

	/**
	 * @param fechaExdateInicio the fechaExdateInicio to set
	 */
	public void setFechaExdateInicio(Date fechaExdateInicio) {
		this.fechaExdateInicio = fechaExdateInicio;
	}

	/**
	 * @return the fechaExdateFin
	 */
	public Date getFechaExdateFin() {
		return fechaExdateFin;
	}

	/**
	 * @param fechaExdateFin the fechaExdateFin to set
	 */
	public void setFechaExdateFin(Date fechaExdateFin) {
		this.fechaExdateFin = fechaExdateFin;
	}

	/**
	 * @return the fechaHoraRecepcionInicio
	 */
	public Date getFechaHoraRecepcionInicio() {
		return fechaHoraRecepcionInicio;
	}

	/**
	 * @param fechaHoraRecepcionInicio the fechaHoraRecepcionInicio to set
	 */
	public void setFechaHoraRecepcionInicio(Date fechaHoraRecepcionInicio) {
		this.fechaHoraRecepcionInicio = fechaHoraRecepcionInicio;
	}

	/**
	 * @return the fechaHoraRecepcionFin
	 */
	public Date getFechaHoraRecepcionFin() {
		return fechaHoraRecepcionFin;
	}

	/**
	 * @param fechaHoraRecepcionFin the fechaHoraRecepcionFin to set
	 */
	public void setFechaHoraRecepcionFin(Date fechaHoraRecepcionFin) {
		this.fechaHoraRecepcionFin = fechaHoraRecepcionFin;
	}

	//////////////////////////////
	public String getFechaAutorizacionFinFormato() {
		return FORMATO_FECHA_PANTALLA.format(this.fechaAutorizacionFin);
	}
		
	public String getFechaAutorizacionInicioFormato() {
		return FORMATO_FECHA_PANTALLA.format(this.fechaAutorizacionInicio);
	}
		
	public String getFechaSolicitudFinFormato() {
		return FORMATO_FECHA_PANTALLA.format(this.fechaSolicitudFin);
	}
	
	public String getFechaSolicitudInicioFormato() {
		return FORMATO_FECHA_PANTALLA.format(this.fechaSolicitudInicio);
	}
	
	public String getFechaExdateFinFormato() {
		return FORMATO_FECHA_PANTALLA.format(this.fechaExdateFin);
	}
	
	public String getFechaExdateInicioFormato() {
		return FORMATO_FECHA_PANTALLA.format(this.fechaExdateInicio);
	}
	
	public String getFechaCorteFinFormato() {
		return FORMATO_FECHA_PANTALLA.format(this.fechaCorteFin);
	}
	
	public String getFechaCorteInicioFormato() {
		return FORMATO_FECHA_PANTALLA.format(this.fechaCorteInicio);
	}

	/**
	 * @return the fechaCorteInicio
	 */
	public Date getFechaCorteInicio() {
		return fechaCorteInicio;
	}

	/**
	 * @param fechaCorteInicio the fechaCorteInicio to set
	 */
	public void setFechaCorteInicio(Date fechaCorteInicio) {
		this.fechaCorteInicio = fechaCorteInicio;
	}

	/**
	 * @return the fechaCorteFin
	 */
	public Date getFechaCorteFin() {
		return fechaCorteFin;
	}

	/**
	 * @param fechaCorteFin the fechaCorteFin to set
	 */
	public void setFechaCorteFin(Date fechaCorteFin) {
		this.fechaCorteFin = fechaCorteFin;
	}

	/**
	 * @return the estadoEmision
	 */
	public String getEstadoEmision() {
		return estadoEmision;
	}

	/**
	 * @param estadoEmision the estadoEmision to set
	 */
	public void setEstadoEmision(String estadoEmision) {
		this.estadoEmision = estadoEmision;
	}
	
	/**
	 * M&eacute;todo para la comparación del objeto
	 */	
	public int compareTo(EnvioLegislacionSicDTO obj) {
		EnvioLegislacionSicDTO envioLegislacionSicDTO = obj;		
		return this.idHistorico.compareTo(envioLegislacionSicDTO.getIdHistorico());
	}
}
