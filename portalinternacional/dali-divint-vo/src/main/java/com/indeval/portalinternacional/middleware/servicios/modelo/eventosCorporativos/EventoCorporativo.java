/**
 * 
 */
package com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos;

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

import com.indeval.portalinternacional.middleware.servicios.modelo.Custodio;
/**
 * @author lmunoz
 *
 */
@Entity
@Table(name = "T_EVENTO_CORPORATIVO_EVCO")
@SequenceGenerator(name = "foliador", sequenceName = "SEQ_T_Evento_Corporativo_EVCO", allocationSize = 1, initialValue = 1)
public class EventoCorporativo implements Serializable{
	/*
	 * Si agregas un atributo, agregarlo en la vista en el decorador EventoCorporativoDeco
	 */
	private Long idEventoCorporativo;
	private Date fechaCreacion;
	private Date fechaRegistro;
	private String tipoValor;
	private String emisora;
	private String serie;
	private String isin;
	private TipoDerechoEvCo tipoDerechoMO;
	private TipoDerechoEvCo tipoDerechoML;
	private TipoMercado tipoMercado;
	private TipoEvento tipoEvento;
	private Estado estado;
	private Date fechaIndeval;
	private Date fechaCliente;
	private Date fechaPago;
	private Integer prioridad;
	private Custodio custodio;
	private Date fechaActualizacion;
	private Boolean actualizado;
	/**
	 * @return the idEventoCorporativo
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
	@Column(name = "ID_EVENTO_CORPORATIVO", unique = true, nullable = false)
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
	 * @return the fechaCreacion
	 */
	@Column(name = "FECHA_CREACION", unique = false, nullable = true)
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	/**
	 * @param fechaCreacion the fechaCreacion to set
	 */
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	/**
	 * @return the fechaRegistro
	 */
	@Column(name = "FECHA_REGISTRO", unique = false, nullable = true)
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
	 * @return the tipoValor
	 */
	@Column(name = "TIPO_VALOR", unique = false, nullable = true)
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
	@Column(name = "EMISORA", unique = false, nullable = true)
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
	@Column(name = "SERIE", unique = false, nullable = true)
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
	@Column(name = "ISIN", unique = false, nullable = true)
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
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_TIPO_DERECHO_MO", referencedColumnName="ID_TIPO_DERECHO",unique = false, nullable = true, insertable=true, updatable=true)
	public TipoDerechoEvCo getTipoDerechoMO() {
		return tipoDerechoMO;
	}
	/**
	 * @param tipoDerechoMO the tipoDerechoMO to set
	 */
	public void setTipoDerechoMO(TipoDerechoEvCo tipoDerechoMO) {
		this.tipoDerechoMO = tipoDerechoMO;
	}
	/**
	 * @return the tipoDerechoML
	 */
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_TIPO_DERECHO_ML", referencedColumnName="ID_TIPO_DERECHO",unique = false, nullable = true, insertable=true, updatable=true)
	public TipoDerechoEvCo getTipoDerechoML() {
		return tipoDerechoML;
	}
	/**
	 * @param tipoDerechoML the tipoDerechoML to set
	 */
	public void setTipoDerechoML(TipoDerechoEvCo tipoDerechoML) {
		this.tipoDerechoML = tipoDerechoML;
	}
	/**
	 * @return the tipoMercado
	 */
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_TIPO_MERCADO", unique = false, nullable = true)
	public TipoMercado getTipoMercado() {
		return tipoMercado;
	}
	/**
	 * @param tipoMercado the tipoMercado to set
	 */
	public void setTipoMercado(TipoMercado tipoMercado) {
		this.tipoMercado = tipoMercado;
	}
	/**
	 * @return the estado
	 */
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_ESTADO", unique = false, nullable = true)
	public Estado getEstado() {
		return estado;
	}
	/**
	 * @param estado the estado to set
	 */
	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	/**
	 * @return the fechaIndeval
	 */
	@Column(name = "FECHA_INDEVAL", unique = false, nullable = true)
	public Date getFechaIndeval() {
		return fechaIndeval;
	}
	/**
	 * @param fechaIndeval the fechaIndeval to set
	 */
	public void setFechaIndeval(Date fechaIndeval) {
		this.fechaIndeval = fechaIndeval;
	}
	/**
	 * @return the fechaCliente
	 */
	@Column(name = "FECHA_CLIENTE", unique = false, nullable = true)
	public Date getFechaCliente() {
		return fechaCliente;
	}
	/**
	 * @param fechaCliente the fechaCliente to set
	 */
	public void setFechaCliente(Date fechaCliente) {
		this.fechaCliente = fechaCliente;
	}
	/**
	 * @return the fechaPago
	 */
	@Column(name = "FECHA_PAGO", unique = false, nullable = true)
	public Date getFechaPago() {
		return fechaPago;
	}
	/**
	 * @param fechaPago the fechaPago to set
	 */
	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}
	/**
	 * @return the prioridad
	 */
	@Column(name = "PRIORIDAD", unique = false, nullable = false)
	public Integer getPrioridad() {
		return prioridad;
	}
	/**
	 * @param prioridad the prioridad to set
	 */
	public void setPrioridad(Integer prioridad) {
		this.prioridad = prioridad;
	}
	/**
	 * @return the tipoEvento
	 */
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_TIPO_EVENTO", unique = false, nullable = false)
	public TipoEvento getTipoEvento() {
		return tipoEvento;
	}
	/**
	 * @param tipoEvento the tipoEvento to set
	 */
	public void setTipoEvento(TipoEvento tipoEvento) {
		this.tipoEvento = tipoEvento;
	}
	/**
	 * @return the custodio
	 */
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_CUSTODIO", unique = false, nullable = false)
	public Custodio getCustodio() {
		return custodio;
	}
	/**
	 * @param custodio the custodio to set
	 */
	public void setCustodio(Custodio custodio) {
		this.custodio = custodio;
	}
	/**
	 * @return the fechaActualizacion
	 */
	@Column(name = "FECHA_ACTUALIZACION", unique = false, nullable = true)
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
	 * @return the actualizado
	 */
	
	@Column(name = "ACTUALIZADO", columnDefinition = "BIT", length = 1, nullable=true)
	public Boolean getActualizado() {
		return actualizado;
	}
	/**
	 * @param actualizado the actualizado to set
	 */
	public void setActualizado(Boolean actualizado) {
		this.actualizado = actualizado;
	}
	
}
