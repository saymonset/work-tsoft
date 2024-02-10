package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author Fernando Pineda
 *
 */
@Entity
@Table(name = "T_DETALLE_CONCILIACION_EFE")
@SequenceGenerator(name = "foliador", sequenceName = "SEQ_T_DETALLE_CONCILIACION_EFE", allocationSize = 1)
public class DetalleConciliacionEfectivoInt implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
	@Column(name = "ID_DETALLE_CONCILIACION")
	private Long idDetalleConciliacion;
	
	@ManyToOne
	@JoinColumn(name = "ID_CONCILIACION_EFECTIVO")
	private ConciliacionEfectivoInt conciliacionEfectivo;
	
	@OneToMany(cascade = CascadeType.ALL)
	@OrderBy("fecha DESC")
	@JoinColumn(name="ID_DETALLE_CONCILIACION")
	private List<ComentarioEfectivoInt> listaComentarioEfectivo;
	
	@Column(name = "FECHA_VALOR")
	private Date fechaValor;
	
	@Column(name = "FECHA_REGISTRO")
	private Date fechaRegistro;
	
	@Column(name = "TIPO")
	private String tipo;
	
	@Column(name = "MONTO")
	private BigDecimal monto;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_TIPO_TRANSACCION")
	private TipoTransaccionEfectivoInt tipoTransaccion;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_CODIGO_IDENTIFICACION")
	private CodigoIdentificacionEfectivoInt codigoIdentificacionEfectivo;
	
	@Column(name = "CODIGO_IDENTIFICACION")
	private String codigoIdentificacion;
	
	@Column(name = "REFERENCIA_CUENTAHABIENTE")
	private String referenciaCuentahabiente;
	
	@Column(name = "REFERENCIA_INSTITUCION")
	private String referenciaInstitucion;
	
	@Column(name = "DETALLES_SUPLEMENTARIOS")
	private String detallesSuplementarios;
	
	@Column(name = "CON_COMENTARIOS")
	private Boolean conComentarios;
	
	@Column(name = "HASH")
	private Integer hash;
	
	/**
	 * @return the idDetalleConciliacion
	 */
	public Long getIdDetalleConciliacion() {
		return idDetalleConciliacion;
	}
	/**
	 * @param idDetalleConciliacion the idDetalleConciliacion to set
	 */
	public void setIdDetalleConciliacion(Long idDetalleConciliacion) {
		this.idDetalleConciliacion = idDetalleConciliacion;
	}
	/**
	 * @return the conciliacionEfectivo
	 */
	public ConciliacionEfectivoInt getConciliacionEfectivo() {
		return conciliacionEfectivo;
	}
	/**
	 * @param conciliacionEfectivo the conciliacionEfectivo to set
	 */
	public void setConciliacionEfectivo(ConciliacionEfectivoInt conciliacionEfectivo) {
		this.conciliacionEfectivo = conciliacionEfectivo;
	}
	/**
	 * @return the listaComentarioEfectivo
	 */
	public List<ComentarioEfectivoInt> getListaComentarioEfectivo() {
		return listaComentarioEfectivo;
	}
	/**
	 * @param listaComentarioEfectivo the listaComentarioEfectivo to set
	 */
	public void setListaComentarioEfectivo(
			List<ComentarioEfectivoInt> listaComentarioEfectivo) {
		this.listaComentarioEfectivo = listaComentarioEfectivo;
	}
	/**
	 * @return the fechaValor
	 */
	public Date getFechaValor() {
		return fechaValor;
	}
	/**
	 * @param fechaValor the fechaValor to set
	 */
	public void setFechaValor(Date fechaValor) {
		this.fechaValor = fechaValor;
	}
	/**
	 * @return the fechaRegistro
	 */
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	/**
	 * @param fechaRegistro the fechaRegistro to set
	 */
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
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
	 * @return the monto
	 */
	public BigDecimal getMonto() {
		return monto;
	}
	/**
	 * @param monto the monto to set
	 */
	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}
	/**
	 * @return the tipoTransaccion
	 */
	public TipoTransaccionEfectivoInt getTipoTransaccionEfectivo() {
		return tipoTransaccion;
	}
	/**
	 * @param tipoTransaccion the tipoTransaccion to set
	 */
	public void setTipoTransaccionEfectivo(TipoTransaccionEfectivoInt tipoTransaccion) {
		this.tipoTransaccion = tipoTransaccion;
	}
	/**
	 * @return the codigoIdentificacion
	 */
	public CodigoIdentificacionEfectivoInt getCodigoIdentificacionEfectivo() {
		return codigoIdentificacionEfectivo;
	}
	/**
	 * @param codigoIdentificacion the codigoIdentificacion to set
	 */
	public void setCodigoIdentificacionEfectivo(CodigoIdentificacionEfectivoInt codigoIdentificacionEfectivo) {
		this.codigoIdentificacionEfectivo = codigoIdentificacionEfectivo;
	}
	/**
	 * @return the codigoIdentificacion
	 */
	public String getCodigoIdentificacion() {
		return codigoIdentificacion;
	}
	/**
	 * @param codigoIdentificacion the codigoIdentificacion to set
	 */
	public void setCodigoIdentificacion(String codigoIdentificacion) {
		this.codigoIdentificacion = codigoIdentificacion;
	}
	/**
	 * @return the referenciaCuentahabiente
	 */
	public String getReferenciaCuentahabiente() {
		return referenciaCuentahabiente;
	}
	/**
	 * @param referenciaCuentahabiente the referenciaCuentahabiente to set
	 */
	public void setReferenciaCuentahabiente(String referenciaCuentahabiente) {
		this.referenciaCuentahabiente = referenciaCuentahabiente;
	}
	/**
	 * @return the referenciaInstitucion
	 */
	public String getReferenciaInstitucion() {
		return referenciaInstitucion;
	}
	/**
	 * @param referenciaInstitucion the referenciaInstitucion to set
	 */
	public void setReferenciaInstitucion(String referenciaInstitucion) {
		this.referenciaInstitucion = referenciaInstitucion;
	}
	/**
	 * @return the detallesSuplementarios
	 */
	public String getDetallesSuplementarios() {
		return detallesSuplementarios;
	}
	/**
	 * @param detallesSuplementarios the detallesSuplementarios to set
	 */
	public void setDetallesSuplementarios(String detallesSuplementarios) {
		this.detallesSuplementarios = detallesSuplementarios;
	}
	/**
	 * @return the conComentarios
	 */
	public Boolean getConComentarios() {
		return conComentarios;
	}
	/**
	 * @param conComentarios the conComentarios to set
	 */
	public void setConComentarios(Boolean conComentarios) {
		this.conComentarios = conComentarios;
	}
	/**
	 * @return the hash
	 */
	public Integer getHash() {
		return hash;
	}
	/**
	 * @param hash the hash to set
	 */
	public void setHash(Integer hash) {
		this.hash = hash;
	}
	
}