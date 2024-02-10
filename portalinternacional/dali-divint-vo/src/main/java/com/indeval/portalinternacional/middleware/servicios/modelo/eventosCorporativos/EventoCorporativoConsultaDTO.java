package com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class EventoCorporativoConsultaDTO {
	private String idEventoCorporativo;
	private Date fechaCreacionInicio;
	private Date fechaCreacionFin;
	private Date fechaRegistroInicio;
	private Date fechaRegistroFin;
	private String tipoValor;
	private String emisora;
	private String serie;
	private String isin;
	private Long tipoDerechoMO;
	private Long tipoDerechoML;
	private Long tipoMercado;
	private Long estado;
	private Date fechaIndevalInicio;
	private Date fechaIndevalFin;
	private Date fechaClienteInicio;
	private Date fechaClienteFin;
	private Date fechaPagoInicio;
	private Date fechaPagoFin;
	private Long custodio;
	private Long tipoEvento;
	private boolean isEdicion;
	
	
	
	
	/**
	 * @return the idEventoCorporativo
	 */
	public String getIdEventoCorporativo() {
		return idEventoCorporativo;
	}
	/**
	 * @param idEventoCorporativo the idEventoCorporativo to set
	 */
	public void setIdEventoCorporativo(String idEventoCorporativo) {
		this.idEventoCorporativo = idEventoCorporativo;
	}
	
	/**
	 * @return the fechaCreacionInicio
	 */
	public Date getFechaCreacionInicio() {
		return fechaCreacionInicio;
	}
	/**
	 * @param fechaCreacionInicio the fechaCreacionInicio to set
	 */
	public void setFechaCreacionInicio(Date fechaCreacionInicio) {
		this.fechaCreacionInicio = fechaCreacionInicio;
	}
	/**
	 * @return the fechaCreacionFin
	 */
	public Date getFechaCreacionFin() {
		return fechaCreacionFin;
	}
	/**
	 * @param fechaCreacionFin the fechaCreacionFin to set
	 */
	public void setFechaCreacionFin(Date fechaCreacionFin) {
		this.fechaCreacionFin = fechaCreacionFin;
	}
	/**
	 * @return the fechaRegistroInicio
	 */
	public Date getFechaRegistroInicio() {
		return fechaRegistroInicio;
	}
	/**
	 * @param fechaRegistroInicio the fechaRegistroInicio to set
	 */
	public void setFechaRegistroInicio(Date fechaRegistroInicio) {
		this.fechaRegistroInicio = fechaRegistroInicio;
	}
	/**
	 * @return the fechaRegistroFin
	 */
	public Date getFechaRegistroFin() {
		return fechaRegistroFin;
	}
	/**
	 * @param fechaRegistroFin the fechaRegistroFin to set
	 */
	public void setFechaRegistroFin(Date fechaRegistroFin) {
		this.fechaRegistroFin = fechaRegistroFin;
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
	 * @return the tipoDerechoMO
	 */
	public Long getTipoDerechoMO() {
		return tipoDerechoMO;
	}
	/**
	 * @param tipoDerechoMO the tipoDerechoMO to set
	 */
	public void setTipoDerechoMO(Long tipoDerechoMO) {
		this.tipoDerechoMO = tipoDerechoMO;
	}
	/**
	 * @param tipoDerechoMO the tipoDerechoMO to set
	 */
	public void setTipoDerechoMO(String tipoDerechoMO) {
		if(StringUtils.isNumeric(tipoDerechoMO)){
			this.tipoDerechoMO = Long.valueOf(tipoDerechoMO);
		}else{
			this.tipoDerechoMO =null;
		}
	}
	/**
	 * @return the tipoDerechoML
	 */
	public Long getTipoDerechoML() {
		return tipoDerechoML;
	}
	/**
	 * @param tipoDerechoML the tipoDerechoML to set
	 */
	public void setTipoDerechoML(Long tipoDerechoML) {
		this.tipoDerechoML = tipoDerechoML;
	}
	/**
	 * @param tipoDerechoML the tipoDerechoML to set
	 */
	public void setTipoDerechoML(String tipoDerechoML) {
		if(StringUtils.isNumeric(tipoDerechoML)){
			this.tipoDerechoML = Long.valueOf(tipoDerechoML);
		}else{
			this.tipoDerechoML =null;
		}
	}
	/**
	 * @return the tipoMercado
	 */
	public Long getTipoMercado() {
		return tipoMercado;
	}
	/**
	 * @param tipoMercado the tipoMercado to set
	 */
	public void setTipoMercado(Long tipoMercado) {
		this.tipoMercado = tipoMercado;
	}
	/**
	 * @param tipoMercado the tipoDerechoML to set
	 */
	public void setTipoMercado(String tipoMercado) {
		if(StringUtils.isNumeric(tipoMercado)){
			this.tipoMercado = Long.valueOf(tipoMercado);
		}else{
			this.tipoMercado =null;
		}
	}
	/**
	 * @return the estado
	 */
	public Long getEstado() {
		return estado;
	}
	/**
	 * @param estado the estado to set
	 */
	public void setEstado(Long estado) {
		this.estado = estado;
	}
	/**
	 * @param tipoMercado the tipoDerechoML to set
	 */
	public void setEstado(String estado) {
		if(StringUtils.isNumeric(estado)){
			this.estado = Long.valueOf(estado);
		}else{
			this.estado =null;
		}
	}
	/**
	 * @return the fechaIndevalInicio
	 */
	public Date getFechaIndevalInicio() {
		return fechaIndevalInicio;
	}
	/**
	 * @param fechaIndevalInicio the fechaIndevalInicio to set
	 */
	public void setFechaIndevalInicio(Date fechaIndevalInicio) {
		this.fechaIndevalInicio = fechaIndevalInicio;
	}
	/**
	 * @return the fechaIndevalFin
	 */
	public Date getFechaIndevalFin() {
		return fechaIndevalFin;
	}
	/**
	 * @param fechaIndevalFin the fechaIndevalFin to set
	 */
	public void setFechaIndevalFin(Date fechaIndevalFin) {
		this.fechaIndevalFin = fechaIndevalFin;
	}
	/**
	 * @return the fechaClienteInicio
	 */
	public Date getFechaClienteInicio() {
		return fechaClienteInicio;
	}
	/**
	 * @param fechaClienteInicio the fechaClienteInicio to set
	 */
	public void setFechaClienteInicio(Date fechaClienteInicio) {
		this.fechaClienteInicio = fechaClienteInicio;
	}
	/**
	 * @return the fechaClienteFin
	 */
	public Date getFechaClienteFin() {
		return fechaClienteFin;
	}
	/**
	 * @param fechaClienteFin the fechaClienteFin to set
	 */
	public void setFechaClienteFin(Date fechaClienteFin) {
		this.fechaClienteFin = fechaClienteFin;
	}
	/**
	 * @return the fechaPagoInicio
	 */
	public Date getFechaPagoInicio() {
		return fechaPagoInicio;
	}
	/**
	 * @param fechaPagoInicio the fechaPagoInicio to set
	 */
	public void setFechaPagoInicio(Date fechaPagoInicio) {
		this.fechaPagoInicio = fechaPagoInicio;
	}
	/**
	 * @return the fechaPagoFin
	 */
	public Date getFechaPagoFin() {
		return fechaPagoFin;
	}
	/**
	 * @param fechaPagoFin the fechaPagoFin to set
	 */
	public void setFechaPagoFin(Date fechaPagoFin) {
		this.fechaPagoFin = fechaPagoFin;
	}
	/**
	 * @return the custodio
	 */
	public Long getCustodio() {
		return custodio;
	}
	/**
	 * @param custodio the custodio to set
	 */
	public void setCustodio(Long custodio) {
		this.custodio = custodio;
	}
	/**
	 * @param tipoMercado the tipoDerechoML to set
	 */
	public void setCustodio(String custodio) {
		if(StringUtils.isNumeric(custodio)){
			this.custodio = Long.valueOf(custodio);
		}else{
			this.custodio =null;
		}
	}
	/**
	 * @return the consultaIndeval
	 */
	public boolean isEdicion() {
		return isEdicion;
	}
	/**
	 * @param consultaIndeval the consultaIndeval to set
	 */
	public void setEdicion(boolean consultaIndeval) {
		this.isEdicion = consultaIndeval;
	}
	/**
	 * @return the tipoEvento
	 */
	public Long getTipoEvento() {
		return tipoEvento;
	}
	/**
	 * @param tipoEvento the tipoEvento to set
	 */
	public void setTipoEvento(Long tipoEvento) {
		this.tipoEvento = tipoEvento;
	}
	
	/**
	 * @param tipoEvento the tipoEvento to set
	 */
	public void setTipoEvento(String tipoEvento) {
		if(StringUtils.isNumeric(tipoEvento)){
			this.tipoEvento = Long.valueOf(tipoEvento);
		}else{
			this.tipoEvento =null;
		}
	}
}
