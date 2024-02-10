package com.indeval.portalinternacional.middleware.servicios.modelo.capitales;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.indeval.portalinternacional.middleware.servicios.vo.EstadoEnvioMoi;

@Entity
@Table(name = "V_ENVIO_LEG_HIST_INT")
public class EnvioHistIntSicView implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**  T_ENVIO_LEG_SIC.ID_ENVIO_LEG_SIC */
	@Id
	@Column(name = "ID_ENVIO_LEG_SIC")
	private Long idEnvioLegSic;
	
	/** T_ENVIO_LEG_SIC.USUARIO as "USUARIO_SOLICITANTE" */
	@Column(name = "USUARIO_SOLICITANTE")
	private String usuarioSolicitante;
	
	/** T_ENVIO_LEG_SIC.USUARIO_ACTUALIZA as "USUARIO_AUTORIZA" */
	@Column(name = "USUARIO_AUTORIZA")
	private String usuarioAutoriza;
	
	/** T_ENVIO_LEG_SIC.ESTADO_ENVIO as "ESTADO_ENVIO" */
	@Column(name = "ESTADO_ENVIO")
	private Integer estadoEnvio;
	
	/** T_ENVIO_LEG_SIC.FECHA_CREACION as "FECHA_SOLICITUD" */
	@Column(name = "FECHA_SOLICITUD")
	private Date fechaSolicitud;
	
	/** T_ENVIO_LEG_SIC.FECHA_ACTUALIZACION */
	@Column(name = "FECHA_ACTUALIZACION")
	private Date fechaActualizacion;
	
	/** T_ENVIO_LEG_SIC.ID_HIST_DERECHO_INT as "FOLIO_MENSAJE" */
	@Column(name = "FOLIO_MENSAJE")
	private Long folioMensaje;
	
	/** T_ENVIO_LEG_SIC.ID_CALENDARIO_INT as "FOLIO_INDEVAL" */
	@Column(name = "FOLIO_INDEVAL")
	private Long folioIndeval;
	
	/** C_TIPO_PAGO_INT.CLAVE_PAGO as "TIPO_DERECHO_CAEV" */
	@Column(name = "TIPO_DERECHO_CAEV")
	private String tipoDerechoCaev;
	
	/** C_TIPO_PAGO_INT.CLAVE_PAGO as "TIPO_DERECHO_CAMV" */
	@Column(name = "TIPO_DERECHO_CAMV")
	private String tipoDerechoCamv;
	
	/** T_ENVIO_LEG_SIC.DESTINATARIO */
	@Column(name = "DESTINATARIO")
	private String destinatario;
	
	/** T_HISTORICO_DERECHO_INT.FECHA_XDATE */
	@Column(name = "FECHA_XDATE")
	private Date fechaExdate;
	
	/** T_HISTORICO_DERECHO_INT.FECHA_HORA_RECEPCION */
	@Column(name = "FECHA_HORA_RECEPCION")
	private Date fechaHoraRecepcion;
	
	/** C_CUSTODIO.NOMBRE_CORTO AS "FUENTE" */
	@Column(name = "FUENTE")
	private String fuente;
	
	/** T_HISTORICO_DERECHO_INT.ISIN */
	@Column(name = "ISIN")
	private String isin;
	
	/** T_HISTORICO_DERECHO_INT.TIPO_VALOR */
	@Column(name = "TIPO_VALOR")
	private String tipoValor;
	
	/** T_HISTORICO_DERECHO_INT.SERIE */
	@Column(name = "SERIE")
	private String serie;
	
	/** T_HISTORICO_DERECHO_INT.CUPON */
	@Column(name = "CUPON")
	private String cupon;
	
	/** T_HISTORICO_DERECHO_INT.EMISORA */
	@Column(name = "EMISORA")
	private String emisora;
	
	@Transient
	private String descEstado;
	

	/** T_HISTORICO_DERECHO_INT.FECHA_CORTE */
	@Column(name = "FECHA_CORTE")
	private Date fechaCorte;
	
	/** V_EMISIONES_INT.LISTADA */
	@Column(name = "LISTADA")
	private String listada;
	
	/** C_TIPO_PAGO_INT.DESCRIPCION as "DESCRIPCION_CAEV" */
	@Column(name = "DESCRIPCION_CAEV")
	private String descripcionCaev;
	
	/** C_TIPO_PAGO_INT.DESCRIPCION as "DESCRIPCION_CAMV" */
	@Column(name = "DESCRIPCION_CAMV")
	private String descripcionCamv;
	
	
	
	public EnvioHistIntSicView(){}

	/**
	 * @return the idEnvioLegSic
	 */
	public Long getIdEnvioLegSic() {
		return idEnvioLegSic;
	}

	/**
	 * @param idEnvioLegSic the idEnvioLegSic to set
	 */
	public void setIdEnvioLegSic(Long idEnvioLegSic) {
		this.idEnvioLegSic = idEnvioLegSic;
	}

	/**
	 * @return the usuarioSolicitante
	 */
	public String getUsuarioSolicitante() {
		return usuarioSolicitante;
	}

	/**
	 * @param usuarioSolicitante the usuarioSolicitante to set
	 */
	public void setUsuarioSolicitante(String usuarioSolicitante) {
		this.usuarioSolicitante = usuarioSolicitante;
	}

	/**
	 * @return the usuarioAutoriza
	 */
	public String getUsuarioAutoriza() {
		return usuarioAutoriza;
	}

	/**
	 * @param usuarioAutoriza the usuarioAutoriza to set
	 */
	public void setUsuarioAutoriza(String usuarioAutoriza) {
		this.usuarioAutoriza = usuarioAutoriza;
	}

	/**
	 * @return the estadoEnvio
	 */
	public Integer getEstadoEnvio() {
		return estadoEnvio;
	}

	/**
	 * @param estadoEnvio the estadoEnvio to set
	 */
	public void setEstadoEnvio(Integer estadoEnvio) {
		this.estadoEnvio = estadoEnvio;
	}

	/**
	 * @return the fechaSolicitud
	 */
	public Date getFechaSolicitud() {
		return fechaSolicitud;
	}

	/**
	 * @param fechaSolicitud the fechaSolicitud to set
	 */
	public void setFechaSolicitud(Date fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}

	/**
	 * @return the fechaActualizacion
	 */
	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}

	/**
	 * @param fechaActualizacion the fechaActualizacion to set
	 */
	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	/**
	 * @return the folioMensaje
	 */
	public Long getFolioMensaje() {
		return folioMensaje;
	}

	/**
	 * @param folioMensaje the folioMensaje to set
	 */
	public void setFolioMensaje(Long folioMensaje) {
		this.folioMensaje = folioMensaje;
	}

	/**
	 * @return the folioIndeval
	 */
	public Long getFolioIndeval() {
		return folioIndeval;
	}

	/**
	 * @param folioIndeval the folioIndeval to set
	 */
	public void setFolioIndeval(Long folioIndeval) {
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
	 * @return the fechaExdate
	 */
	public Date getFechaExdate() {
		return fechaExdate;
	}

	/**
	 * @param fechaExdate the fechaExdate to set
	 */
	public void setFechaExdate(Date fechaExdate) {
		this.fechaExdate = fechaExdate;
	}

	/**
	 * @return the fechaHoraRecepcion
	 */
	public Date getFechaHoraRecepcion() {
		return fechaHoraRecepcion;
	}

	/**
	 * @param fechaHoraRecepcion the fechaHoraRecepcion to set
	 */
	public void setFechaHoraRecepcion(Date fechaHoraRecepcion) {
		this.fechaHoraRecepcion = fechaHoraRecepcion;
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
		return isin;
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
		return tipoValor;
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
		return serie;
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
		return emisora;
	}

	/**
	 * @param emisora the emisora to set
	 */
	public void setEmisora(String emisora) {
		this.emisora = emisora;
	}
	/**
	 * @return the descEstado
	 */
	public String getDescEstado() {
		
		return this.estadoEnvio != null ? EstadoEnvioMoi.values()[this.estadoEnvio].getValor() : ""; 		
	}

	/**
	 * @param descEstado the descEstado to set
	 */
	public void setDescEstado(String descEstado) {
		this.descEstado = descEstado;
	}

	/**
	 * @return the fechaCorte
	 */
	public Date getFechaCorte() {
		return fechaCorte;
	}

	/**
	 * @param fechaCorte the fechaCorte to set
	 */
	public void setFechaCorte(Date fechaCorte) {
		this.fechaCorte = fechaCorte;
	}

	/**
	 * @return the listada
	 */
	public String getListada() {
		return listada;
	}

	/**
	 * @param listada the listada to set
	 */
	public void setListada(String listada) {
		this.listada = listada;
	}

	/**
	 * @return the descripcionCaev
	 */
	public String getDescripcionCaev() {
		return descripcionCaev;
	}

	/**
	 * @param descripcionCaev the descripcionCaev to set
	 */
	public void setDescripcionCaev(String descripcionCaev) {
		this.descripcionCaev = descripcionCaev;
	}

	/**
	 * @return the descripcionCamv
	 */
	public String getDescripcionCamv() {
		return descripcionCamv;
	}

	/**
	 * @param descripcionCamv the descripcionCamv to set
	 */
	public void setDescripcionCamv(String descripcionCamv) {
		this.descripcionCamv = descripcionCamv;
	}
}
