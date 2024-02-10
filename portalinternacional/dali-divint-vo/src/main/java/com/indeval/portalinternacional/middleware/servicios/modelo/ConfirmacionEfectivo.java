/**
 * 
 */
package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
/**
 * 
 * @author lmunoz
 *
 */
@XStreamAlias("ConfirmacionEfectivo")
@Entity
@Table(name = "T_CONFIRMACION_EFECTIVO")
@SequenceGenerator(name = "foliador", sequenceName = "SEQ_T_CONFIRMACION_EFECTIVO", allocationSize = 1)
public class ConfirmacionEfectivo implements Serializable{
	@XStreamOmitField
	private static final long serialVersionUID = -1L;
	
	@XStreamAlias("idConfirmacion") 
	private Long idConfirmacion;
	
	@XStreamAlias("referenciaMensaje") 
	private String referenciaMensaje;
	
	@XStreamAlias("relatedReference") 
	private String relatedReference;
	
	@XStreamAlias("fechaCreditoDebito") 
	private Date fechaCreditoDebito;
	
	@XStreamAlias("tipoMensaje") 
	private String tipoMensaje;
	
	@XStreamAlias("fechaEmision") 
	private Date fechaEmision;
	
	@XStreamAlias("fechaRecepcionDali") 
	private Date fechaRecepcionDali;
	
	@XStreamAlias("bicCode") 
	private String bicCode;
	
	@XStreamAlias("divisa") 
	private String divisa;
	
	@XStreamAlias("cuenta") 
	private String cuenta;
	
	@XStreamAlias("cantidad") 
	private Double cantidad;
	
	@XStreamAlias("idFolio") 
	private String idFolio;
	
	@XStreamAlias("orderingInstitution") 
	private String orderingInstitution;
	
	@XStreamAlias("orderingCustomer") 
	private String orderingCustomer;
	
	@XStreamAlias("intermediary") 
	private String intermediary;
	
	@XStreamAlias("detalle") 
	private String detalle;
	
	@XStreamAlias("comentariosDali")
	private String comentariosDali;
	
	@XStreamOmitField
	private Boolean enviado;
	
	@XStreamOmitField
	private String hash;
	
	@XStreamAlias("ops") 
	private String ops;
	
	@XStreamOmitField
	private BitacoraConfirmacion bitacora;
	/**
	 * @return the idConfirmacion
	 */
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
    @Column(name = "ID_CONFIRMACION")
	public Long getIdConfirmacion() {
		return idConfirmacion;
	}
	/**
	 * @param idConfirmacion the idConfirmacion to set
	 */
	public void setIdConfirmacion(Long idConfirmacion) {
		this.idConfirmacion = idConfirmacion;
	}
	/**
	 * @return the referenciaMensaje
	 */
	@Column(name = "REFERENCIA_MENSAJE")
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
	 * @return the relatedReference
	 */
	@Column(name = "RELATED_REFERENCE")
	public String getRelatedReference() {
		return relatedReference;
	}
	/**
	 * @param relatedReference the relatedReference to set
	 */
	public void setRelatedReference(String relatedReference) {
		this.relatedReference = relatedReference;
	}
	/**
	 * @return the fechaCreditoDebito
	 */
	@Column(name = "FECHA_CREDITO_DEBITO")
	public Date getFechaCreditoDebito() {
		return fechaCreditoDebito;
	}
	/**
	 * @param fechaCreditoDebito the fechaCreditoDebito to set
	 */
	public void setFechaCreditoDebito(Date fechaCreditoDebito) {
		this.fechaCreditoDebito = fechaCreditoDebito;
	}
	/**
	 * @return the tipoMensaje
	 */
	@Column(name = "TIPO_MENSAJE")
	public String getTipoMensaje() {
		return tipoMensaje;
	}
	/**
	 * @param tipoMensaje the tipoMensaje to set
	 */
	public void setTipoMensaje(String tipoMensaje) {
		this.tipoMensaje = tipoMensaje;
	}
	/**
	 * @return the fechaRecepcion
	 */
	@Column(name = "FECHA_RECEPCION")
	public Date getFechaEmision() {
		return fechaEmision;
	}
	/**
	 * @param fechaRecepcion the fechaRecepcion to set
	 */
	public void setFechaEmision(Date fechaRecepcion) {
		this.fechaEmision = fechaRecepcion;
	}
	/**
	 * @return the fechaRecepcionDali
	 */
	@Column(name = "FECHA_RECEPCION_DALI")
	public Date getFechaRecepcionDali() {
		return fechaRecepcionDali;
	}
	/**
	 * @param fechaRecepcionDali the fechaRecepcionDali to set
	 */
	public void setFechaRecepcionDali(Date fechaRecepcionDali) {
		this.fechaRecepcionDali = fechaRecepcionDali;
	}
	/**
	 * @return the bicCode
	 */
	@Column(name = "BIC_CODE")
	public String getBicCode() {
		return bicCode;
	}
	/**
	 * @param bicCode the bicCode to set
	 */
	public void setBicCode(String bicCode) {
		this.bicCode = bicCode;
	}
	/**
	 * @return the divisa
	 */
	@Column(name = "DIVISA")
	public String getDivisa() {
		return divisa;
	}
	/**
	 * @param divisa the divisa to set
	 */
	public void setDivisa(String divisa) {
		this.divisa = divisa;
	}
	/**
	 * @return the cuenta
	 */
	@Column(name = "CUENTA")
	public String getCuenta() {
		return cuenta;
	}
	/**
	 * @param cuenta the cuenta to set
	 */
	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}
	/**
	 * @return the cantidad
	 */
	@Column(name = "CANTIDAD")
	public Double getCantidad() {
		return cantidad;
	}
	/**
	 * @param cantidad the cantidad to set
	 */
	public void setCantidad(Double cantidad) {
		this.cantidad = cantidad;
	}
	/**
	 * @return the idFolio
	 */
	@Column(name = "ID_FOLIO")
	public String getIdFolio() {
		return idFolio;
	}
	/**
	 * @param idFolio the idFolio to set
	 */
	public void setIdFolio(String idFolio) {
		this.idFolio = idFolio;
	}
	/**
	 * @return the orderingInstitution
	 */
	@Column(name = "ORDERING_INSTITUTION")
	public String getOrderingInstitution() {
		return orderingInstitution;
	}
	/**
	 * @param orderingInstitution the orderingInstitution to set
	 */
	public void setOrderingInstitution(String orderingInstitution) {
		this.orderingInstitution = orderingInstitution;
	}
	/**
	 * @return the intermediary
	 */
	@Column(name = "INTERMEDIARY")
	public String getIntermediary() {
		return intermediary;
	}
	/**
	 * @param intermediary the intermediary to set
	 */
	public void setIntermediary(String intermediary) {
		this.intermediary = intermediary;
	}
	/**
	 * @return the detalle
	 */
	@Column(name = "DETALLE")
	public String getDetalle() {
		return detalle;
	}
	/**
	 * @param detalle the detalle to set
	 */
	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}
	/**
	 * @return the comentariosDali
	 */
	@Column(name = "COMENTARIOS_DALI")
	public String getComentariosDali() {
		return comentariosDali;
	}
	/**
	 * @param comentariosDali the comentariosDali to set
	 */
	public void setComentariosDali(String comentariosDali) {
		this.comentariosDali = comentariosDali;
	}
	/**
	 * @return the enviado
	 */
	@Column(name = "ENVIADO", columnDefinition = "BIT", length = 1)
	public Boolean getEnviado() {
		return enviado;
	}
	/**
	 * @param enviado the enviado to set
	 */
	public void setEnviado(Boolean enviado) {
		this.enviado = enviado;
	}
	/**
	 * @return the hash
	 */
	@Column(name = "HASH")
	public String getHash() {
		return hash;
	}
	/**
	 * @param hash the hash to set
	 */
	public void setHash(String hash) {
		this.hash = hash;
	}
	/**
	 * @return the ops
	 */
	@Column(name = "OPS")
	public String getOps() {
		return ops;
	}
	/**
	 * @param ops the ops to set
	 */
	public void setOps(String ops) {
		this.ops = ops;
	}
	/**
	 * @return the bitacora
	 */
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CONFIRMACION", referencedColumnName="ID_CONFIRMACION", unique = true, nullable = true, insertable=false, updatable=false)
	public BitacoraConfirmacion getBitacora() {
		return bitacora;
	}
	/**
	 * @param bitacora the bitacora to set
	 */
	public void setBitacora(BitacoraConfirmacion bitacora) {
		this.bitacora = bitacora;
	}
	/**
	 * @param orderingCustomer the orderingCustomer to set
	 */
	public void setOrderingCustomer(String orderingCustomer) {
		this.orderingCustomer = orderingCustomer;
	}
	/**
	 * @return the orderingCustomer
	 */
	@Column(name = "ORDERING_CUSTOMER")
	public String getOrderingCustomer() {
		return orderingCustomer;
	}
	
}