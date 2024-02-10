/**
 * 
 */
package com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos;

import java.util.Date;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author kode-
 *
 */
@XStreamAlias("OpcionesDTO")
public class OpcionesDTO {

	private String numeroOpcion;
	private String valorDefault;
	private boolean isDefault;
	private String cuerpoOpcion;
	private Date fechaClienteOpcion;
	private Date fechaIndevalOpcion;
	private Date fechaPagoOpcion;
	private boolean borradoLogico;
	
	private Long idOpcion;
	private Long idEventoCorporativo;
	private String cuerpo;
	private String fechaIndeval;
	private String fechaCliente;
	private String fechaPago;
	private Long opcDefault;
	/**
	 * @return the idOpcion
	 */
	public Long getIdOpcion() {
		return idOpcion;
	}
	/**
	 * @param idOpcion the idOpcion to set
	 */
	public void setIdOpcion(Long idOpcion) {
		this.idOpcion = idOpcion;
	}
	/**
	 * @return the idEventoCorporativo
	 */
	public Long getIdEventoCorporativo() {
		return idEventoCorporativo;
	}
	/**
	 * @param idEventoCorporativo the idEventoCorporativo to set
	 */
	public void setIdEventoCorporativo(Long idEventoCorporativo) {
		this.idEventoCorporativo = idEventoCorporativo;
	}
	/**
	 * @return the cuerpo
	 */
	public String getCuerpo() {
		return cuerpo;
	}
	/**
	 * @param cuerpo the cuerpo to set
	 */
	public void setCuerpo(String cuerpo) {
		this.cuerpo = cuerpo;
	}
	/**
	 * @return the fechaIndeval
	 */
	public String getFechaIndeval() {
		return fechaIndeval;
	}
	/**
	 * @param fechaIndeval the fechaIndeval to set
	 */
	public void setFechaIndeval(String fechaIndeval) {
		this.fechaIndeval = fechaIndeval;
	}
	/**
	 * @return the fechaCliente
	 */
	public String getFechaCliente() {
		return fechaCliente;
	}
	/**
	 * @param fechaCliente the fechaCliente to set
	 */
	public void setFechaCliente(String fechaCliente) {
		this.fechaCliente = fechaCliente;
	}
	/**
	 * @return the fechaPago
	 */
	public String getFechaPago() {
		return fechaPago;
	}
	/**
	 * @param fechaPago the fechaPago to set
	 */
	public void setFechaPago(String fechaPago) {
		this.fechaPago = fechaPago;
	}
	/**
	 * @return the opcDefault
	 */
	public Long getOpcDefault() {
		return opcDefault;
	}
	/**
	 * @param opcDefault the opcDefault to set
	 */
	public void setOpcDefault(Long opcDefault) {
		this.opcDefault = opcDefault;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "OpcionesDTO [idOpcion=" + idOpcion + ", idEventoCorporativo="
				+ idEventoCorporativo + ", cuerpo=" + cuerpo
				+ ", fechaIndeval=" + fechaIndeval + ", fechaCliente="
				+ fechaCliente + ", fechaPago=" + fechaPago + ", opcDefault="
				+ opcDefault + "]";
	}
	

	public boolean isBorradoLogico() {
		return borradoLogico;
	}

	public void setBorradoLogico(boolean borradoLogico) {
		this.borradoLogico = borradoLogico;
	}
	public String getNumeroOpcion() {
		return numeroOpcion;
	}
	public void setNumeroOpcion(String numeroOpcion) {
		this.numeroOpcion = numeroOpcion;
	}
	public void setNumeroOpcion(Integer numeroOpcion) {
		if(numeroOpcion != null){
			this.numeroOpcion = numeroOpcion.toString();
		}
	}
	public String getValorDefault() {
		return valorDefault;
	}
	public void setValorDefault(String valorDefault) {
		this.valorDefault = valorDefault;
	}
	public boolean isDefault() {
		return isDefault;
	}
	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}
	public String getCuerpoOpcion() {
		return cuerpoOpcion;
	}
	public void setCuerpoOpcion(String cuerpoOpcion) {
		this.cuerpoOpcion = cuerpoOpcion;
	}
	public Date getFechaClienteOpcion() {
		return fechaClienteOpcion;
	}
	public void setFechaClienteOpcion(Date fechaClienteOpcion) {
		this.fechaClienteOpcion = fechaClienteOpcion;
	}
	public Date getFechaIndevalOpcion() {
		return fechaIndevalOpcion;
	}
	public void setFechaIndevalOpcion(Date fechaIndevalOpcion) {
		this.fechaIndevalOpcion = fechaIndevalOpcion;
	}
	public Date getFechaPagoOpcion() {
		return fechaPagoOpcion;
	}
	public void setFechaPagoOpcion(Date fechaPagoOpcion) {
		this.fechaPagoOpcion = fechaPagoOpcion;
	}
}
