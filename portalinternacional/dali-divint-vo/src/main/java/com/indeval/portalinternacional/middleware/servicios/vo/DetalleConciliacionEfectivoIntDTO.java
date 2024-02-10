package com.indeval.portalinternacional.middleware.servicios.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("detalle")
public class DetalleConciliacionEfectivoIntDTO implements Serializable{
	
	@XStreamAlias("bicCodes") 
    @XStreamAsAttribute
	private Set<String> bicCodes;

	@XStreamAlias("divisas") 
    @XStreamAsAttribute
	private Set<String> divisas;
	
	@XStreamAlias("cuentas") 
    @XStreamAsAttribute
	private Set<String> cuentas;
	
	@XStreamAlias("codigoVerificacion") 
    @XStreamAsAttribute
	private Integer codigoVerificacion;
	
	@XStreamAlias("folioConciliacion") 
    @XStreamAsAttribute
	private Long folioConciliacion;
	
	@XStreamAlias("referenciaMensaje") 
    @XStreamAsAttribute
	private String referenciaMensaje;
	
	@XStreamAlias("tipo") 
    @XStreamAsAttribute
	private String tipo;
	
	@XStreamAlias("fechaBalanceInicio") 
    @XStreamAsAttribute
	private Date fechaBalanceInicio;
	
	@XStreamAlias("fechaBalanceFin") 
    @XStreamAsAttribute
	private Date fechaBalanceFin;
	
	@XStreamAlias("fechaEmisionInicio") 
    @XStreamAsAttribute
	private Date fechaEmisionInicio;
	
	@XStreamAlias("fechaEmisionFin") 
    @XStreamAsAttribute
	private Date fechaEmisionFin;
	
	@XStreamAlias("fechaCreditoDebitoInicio") 
    @XStreamAsAttribute
	private Date fechaCreditoDebitoInicio;
	
	@XStreamAlias("fechaCreditoDebitoFin") 
    @XStreamAsAttribute
	private Date fechaCreditoDebitoFin;
	
	@XStreamAlias("cuentaComercial") 
    @XStreamAsAttribute
	private Boolean cuentaComercial;
	
	@XStreamAlias("cuentaCustodia") 
    @XStreamAsAttribute
	private Boolean cuentaCustodia;
	
	@XStreamAlias("comentarios") 
    @XStreamAsAttribute
	private Boolean comentarios;
	
	/**
	 * @return the bicCodes
	 */
	public Set<String> getBicCodes() {
		return bicCodes;
	}
	/**
	 * @param bicCodes the bicCodes to set
	 */
	public void setBicCodes(Set<String> bicCodes) {
		this.bicCodes = bicCodes;
	}
	/**
	 * @return the divisas
	 */
	public Set<String> getDivisas() {
		return divisas;
	}
	/**
	 * @param divisas the divisas to set
	 */
	public void setDivisas(Set<String> divisas) {
		this.divisas = divisas;
	}
	/**
	 * @return the cuentas
	 */
	public Set<String> getCuentas() {
		return cuentas;
	}
	/**
	 * @param cuentas the cuentas to set
	 */
	public void setCuentas(Set<String> cuentas) {
		this.cuentas = cuentas;
	}
	/**
	 * @return the codigoVerificacion
	 */
	public Integer getCodigoVerificacion() {
		return codigoVerificacion;
	}
	/**
	 * @param codigoVerificacion the codigoVerificacion to set
	 */
	public void setCodigoVerificacion(Integer codigoVerificacion) {
		this.codigoVerificacion = codigoVerificacion;
	}
	/**
	 * @return the folioConciliacion
	 */
	public Long getFolioConciliacion() {
		return folioConciliacion;
	}
	/**
	 * @param folioConciliacion the folioConciliacion to set
	 */
	public void setFolioConciliacion(Long folioConciliacion) {
		this.folioConciliacion = folioConciliacion;
	}
	/**
	 * @return the referenciaMensaje
	 */
	public String getReferenciaMensaje() {
		return referenciaMensaje;
	}
	/**
	 * @param referenciaMensaje the referenciaMensaje to set
	 */
	public void setReferenciaMensaje(String referenciaMensaje) {
		this.referenciaMensaje = referenciaMensaje;
	}
	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}
	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	/**
	 * @return the fechaBalanceInicio
	 */
	public Date getFechaBalanceInicio() {
		return fechaBalanceInicio;
	}
	/**
	 * @param fechaBalanceInicio the fechaBalanceInicio to set
	 */
	public void setFechaBalanceInicio(Date fechaBalanceInicio) {
		this.fechaBalanceInicio = fechaBalanceInicio;
	}
	/**
	 * @return the fechaBalanceFin
	 */
	public Date getFechaBalanceFin() {
		return fechaBalanceFin;
	}
	/**
	 * @param fechaBalanceFin the fechaBalanceFin to set
	 */
	public void setFechaBalanceFin(Date fechaBalanceFin) {
		this.fechaBalanceFin = fechaBalanceFin;
	}
	/**
	 * @return the fechaEmisionInicio
	 */
	public Date getFechaEmisionInicio() {
		return fechaEmisionInicio;
	}
	/**
	 * @param fechaEmisionInicio the fechaEmisionInicio to set
	 */
	public void setFechaEmisionInicio(Date fechaEmisionInicio) {
		this.fechaEmisionInicio = fechaEmisionInicio;
	}
	/**
	 * @return the fechaEmisionFin
	 */
	public Date getFechaEmisionFin() {
		return fechaEmisionFin;
	}
	/**
	 * @param fechaEmisionFin the fechaEmisionFin to set
	 */
	public void setFechaEmisionFin(Date fechaEmisionFin) {
		this.fechaEmisionFin = fechaEmisionFin;
	}
	/**
	 * @return the fechaCreditoDebitoInicio
	 */
	public Date getFechaCreditoDebitoInicio() {
		return fechaCreditoDebitoInicio;
	}
	/**
	 * @param fechaCreditoDebitoInicio the fechaCreditoDebitoInicio to set
	 */
	public void setFechaCreditoDebitoInicio(Date fechaCreditoDebitoInicio) {
		this.fechaCreditoDebitoInicio = fechaCreditoDebitoInicio;
	}
	/**
	 * @return the fechaCreditoDebitoFin
	 */
	public Date getFechaCreditoDebitoFin() {
		return fechaCreditoDebitoFin;
	}
	/**
	 * @param fechaCreditoDebitoFin the fechaCreditoDebitoFin to set
	 */
	public void setFechaCreditoDebitoFin(Date fechaCreditoDebitoFin) {
		this.fechaCreditoDebitoFin = fechaCreditoDebitoFin;
	}
	/**
	 * @return the cuentaComercial
	 */
	public Boolean getCuentaComercial() {
		return cuentaComercial;
	}
	/**
	 * @param cuentaComercial the cuentaComercial to set
	 */
	public void setCuentaComercial(Boolean cuentaComercial) {
		this.cuentaComercial = cuentaComercial;
	}
	/**
	 * @return the cuentaCustodia
	 */
	public Boolean getCuentaCustodia() {
		return cuentaCustodia;
	}
	/**
	 * @param cuentaCustodia the cuentaCustodia to set
	 */
	public void setCuentaCustodia(Boolean cuentaCustodia) {
		this.cuentaCustodia = cuentaCustodia;
	}
	/**
	 * @return the comentarios
	 */
	public Boolean getComentarios() {
		return comentarios;
	}
	/**
	 * @param comentarios the comentarios to set
	 */
	public void setComentarios(Boolean comentarios) {
		this.comentarios = comentarios;
	}
}
