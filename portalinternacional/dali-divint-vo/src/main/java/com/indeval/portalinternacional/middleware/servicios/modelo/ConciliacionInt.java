/**
 * 
 */
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.indeval.portaldali.persistence.modelo.Boveda;

/**
 * 
 * @author
 *
 */
@Entity
@Table(name = "T_CONCILIACION_INT")
@SequenceGenerator(name = "foliador", sequenceName = "SEQ_T_CONCILIACION_INT", allocationSize = 1, initialValue = 1)
public class ConciliacionInt implements Serializable{

	private static final long serialVersionUID = -2198929005144500134L;

	private Long id;	
	private Custodio custodio;
	private Date fechaMensaje;
	private Date fechaConciliacion;
	private BigDecimal posicionGlobalMensaje;
	private Long posicionDali;
	private BigDecimal diferencia;
	private Boveda bovedaDali;
	private List<DetalleConciliacionInt> listaDetallesConciliacion;
	private EstatusConciliacionInt estatusConciliacion;
	private Boolean esReporteGenerado;
	private Boolean diferenciaEmisionesDali;
	private Boolean diferenciaEmisionesMensaje;
	private Boolean conciliacionNacional;
	private Boolean inProgress;
	
	
	public ConciliacionInt() {
		super();
	}
	
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
    @Column(name = "ID_CONCILIACION")
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_CUSTODIO", unique = false, nullable = true)
	public Custodio getCustodio() {
		return custodio;
	}
	public void setCustodio(Custodio custodio) {
		this.custodio = custodio;
	}
	
	@Column(name = "FECHA_MENSAJE")
	public Date getFechaMensaje() {
		return fechaMensaje;
	}
	
	public void setFechaMensaje(Date fechaMensaje) {
		this.fechaMensaje = fechaMensaje;
	}
	
	@Column(name = "FECHA_CONCILIACION")
	public Date getFechaConciliacion() {
		return fechaConciliacion;
	}
	
	public void setFechaConciliacion(Date fechaConciliacion) {
		this.fechaConciliacion = fechaConciliacion;
	}
	
	@Column(name = "POSICION_GLOBAL_MENSAJE")
	public BigDecimal getPosicionGlobalMensaje() {
		return posicionGlobalMensaje;
	}
	
	public void setPosicionGlobalMensaje(BigDecimal posicionGlobalMensaje) {
		this.posicionGlobalMensaje = posicionGlobalMensaje;
	}
	
	@Column(name = "POSICION_DALI")
	public Long getPosicionDali() {
		return posicionDali;
	}
	
	public void setPosicionDali(Long posicionDali) {
		this.posicionDali = posicionDali;
	}
	
	@Column(name = "DIFERENCIA")
	public BigDecimal getDiferencia() {
		return diferencia;
	}
	
	public void setDiferencia(BigDecimal diferencia) {
		this.diferencia = diferencia;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_BOVEDA", unique = false, nullable = true)
	public Boveda getBovedaDali() {
		return bovedaDali;
	}
	public void setBovedaDali(Boveda bovedaDali) {
		this.bovedaDali = bovedaDali;
	}
	
	@OneToMany(targetEntity=DetalleConciliacionInt.class,cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="ID_DETALLE_CONCILIACION")
	public List<DetalleConciliacionInt> getListaDetallesConciliacion() {
		return listaDetallesConciliacion;
	}

	public void setListaDetallesConciliacion(
			List<DetalleConciliacionInt> listaDetallesConciliacion) {
		this.listaDetallesConciliacion = listaDetallesConciliacion;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_ESTATUS_CONCILIACION", unique = false, nullable = true)
	public EstatusConciliacionInt getEstatusConciliacion() {
		return estatusConciliacion;
	}

	public void setEstatusConciliacion(EstatusConciliacionInt estatusConciliacion) {
		this.estatusConciliacion = estatusConciliacion;
	}

	@Column(name = "REPORTE_GENERADO", columnDefinition = "BIT", length = 1)
	public Boolean getEsReporteGenerado() {
		return esReporteGenerado;
	}

	public void setEsReporteGenerado(Boolean esReporteGenerado) {
		this.esReporteGenerado = esReporteGenerado;
	}

	@Column(name = "DIFERENCIA_EMISIONES_DALI", columnDefinition = "BIT", length = 1)
	public Boolean getDiferenciaEmisionesDali() {
		return diferenciaEmisionesDali;
	}

	public void setDiferenciaEmisionesDali(Boolean diferenciaEmisionesDali) {
		this.diferenciaEmisionesDali = diferenciaEmisionesDali;
	}

	@Column(name = "DIFERENCIA_EMISIONES_MENSAJE", columnDefinition = "BIT", length = 1)
	public Boolean getDiferenciaEmisionesMensaje() {
		return diferenciaEmisionesMensaje;
	}

	public void setDiferenciaEmisionesMensaje(Boolean diferenciaEmisionesMensaje) {
		this.diferenciaEmisionesMensaje = diferenciaEmisionesMensaje;
	}
	/**
	 * @return the conciliacionNacional
	 */
	@Column(name = "CONCILIACION_NACIONAL", columnDefinition = "BIT", length = 1)
	public Boolean getConciliacionNacional() {
		return conciliacionNacional;
	}

	/**
	 * @param conciliacionNacional the conciliacionNacional to set
	 */
	public void setConciliacionNacional(Boolean conciliacionNacional) {
		this.conciliacionNacional = conciliacionNacional;
	}

	/**
	 * @return the inProgress
	 */
	@Column(name = "IN_PROGRESS")
	public Boolean getInProgress() {
		return inProgress;
	}

	/**
	 * @param inProgress the inProgress to set
	 */
	public void setInProgress(Boolean inProgress) {
		this.inProgress = inProgress;
	}

	
}