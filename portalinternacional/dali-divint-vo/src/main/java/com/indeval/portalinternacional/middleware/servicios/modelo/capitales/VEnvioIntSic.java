package com.indeval.portalinternacional.middleware.servicios.modelo.capitales;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;




@Entity
@Table(name="V_ENVIO_MENSAJES_INT")
public class VEnvioIntSic implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_ENVIO")
	private Long idEnvioIntSic;

	@Column(name="ACK_AMH")
	private Integer ackAmh;

	@Column(name="ACK_SWIFT")
	private Integer ackSwift;

	@Column(name="FECHA_ACTUALIZACION")
	private Date fechaActualizacion;
	
	@Column(name="FECHA_ENVIO")
	private Date fechaEnvio;
	
	@Column(name="FOLIO_INDEVAL")
	private Long idCalendario;
	
	@Column(name="TIPO_VALOR")
	private String tipoValor;
	
	@Column(name="EMISORA")
    private String emisora;
    
	@Column(name="SERIE")
    private String serie;
    
	@Column(name="ISIN")
    private String isin;
	
	@Column(name="CFI")
	private String cfi;
	
	@Column(name="FOLIO_MENSAJE_INDEVAL")
	private Long idHistorico;
	
	@Column(name="FOLIO_BITACORA")
	private Long idBitacora;
	
	@Column(name="CONSECUTIVO")
	private Long consecutivo;
	
	@Column(name="NUMERO_ENVIO")
	private Long numeroEnvio;
	
	@Column(name="ID_ESTADO_MSJ")
	private Long idEstadoMensaje;
	
	@Column(name="NOMBRE_ESTADO")
	private String nombreEstado;
	
	@Column(name="NOMBRE_DESTINATARIO")
	private String nombreDestinatario;
	
	@Column(name="BICCODE")
	private String biccode;
	
	@Column(name="USUARIO")
	private String usuario;
	
	
	
	public VEnvioIntSic() {
	}



	/**
	 * @return the idEnvioIntSic
	 */
	public Long getIdEnvioIntSic() {
		return idEnvioIntSic;
	}



	/**
	 * @param idEnvioIntSic the idEnvioIntSic to set
	 */
	public void setIdEnvioIntSic(Long idEnvioIntSic) {
		this.idEnvioIntSic = idEnvioIntSic;
	}



	/**
	 * @return the ackAmh
	 */
	public Integer getAckAmh() {
		return ackAmh;
	}



	/**
	 * @param ackAmh the ackAmh to set
	 */
	public void setAckAmh(Integer ackAmh) {
		this.ackAmh = ackAmh;
	}



	/**
	 * @return the ackSwift
	 */
	public Integer getAckSwift() {
		return ackSwift;
	}



	/**
	 * @param ackSwift the ackSwift to set
	 */
	public void setAckSwift(Integer ackSwift) {
		this.ackSwift = ackSwift;
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
	 * @return the fechaEnvio
	 */
	public Date getFechaEnvio() {
		return fechaEnvio;
	}



	/**
	 * @param fechaEnvio the fechaEnvio to set
	 */
	public void setFechaEnvio(Date fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
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
	 * @return the cfi
	 */
	public String getCfi() {
		return cfi;
	}



	/**
	 * @param cfi the cfi to set
	 */
	public void setCfi(String cfi) {
		this.cfi = cfi;
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
	 * @return the idBitacora
	 */
	public Long getIdBitacora() {
		return idBitacora;
	}



	/**
	 * @param idBitacora the idBitacora to set
	 */
	public void setIdBitacora(Long idBitacora) {
		this.idBitacora = idBitacora;
	}



	/**
	 * @return the consecutivo
	 */
	public Long getConsecutivo() {
		return consecutivo;
	}



	/**
	 * @param consecutivo the consecutivo to set
	 */
	public void setConsecutivo(Long consecutivo) {
		this.consecutivo = consecutivo;
	}



	/**
	 * @return the numeroEnvio
	 */
	public Long getNumeroEnvio() {
		return numeroEnvio;
	}



	/**
	 * @param numeroEnvio the numeroEnvio to set
	 */
	public void setNumeroEnvio(Long numeroEnvio) {
		this.numeroEnvio = numeroEnvio;
	}



	/**
	 * @return the idEstadoMensaje
	 */
	public Long getIdEstadoMensaje() {
		return idEstadoMensaje;
	}



	/**
	 * @param idEstadoMensaje the idEstadoMensaje to set
	 */
	public void setIdEstadoMensaje(Long idEstadoMensaje) {
		this.idEstadoMensaje = idEstadoMensaje;
	}



	/**
	 * @return the nombreEstado
	 */
	public String getNombreEstado() {
		return nombreEstado;
	}



	/**
	 * @param nombreEstado the nombreEstado to set
	 */
	public void setNombreEstado(String nombreEstado) {
		this.nombreEstado = nombreEstado;
	}



	/**
	 * @return the nombreDestinatario
	 */
	public String getNombreDestinatario() {
		return nombreDestinatario;
	}



	/**
	 * @param nombreDestinatario the nombreDestinatario to set
	 */
	public void setNombreDestinatario(String nombreDestinatario) {
		this.nombreDestinatario = nombreDestinatario;
	}



	/**
	 * @return the biccode
	 */
	public String getBiccode() {
		return biccode;
	}



	/**
	 * @param biccode the biccode to set
	 */
	public void setBiccode(String biccode) {
		this.biccode = biccode;
	}



	/**
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}



	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
    
	
	
}