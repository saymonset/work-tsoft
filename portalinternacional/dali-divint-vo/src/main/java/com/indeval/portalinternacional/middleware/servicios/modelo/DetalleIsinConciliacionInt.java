package com.indeval.portalinternacional.middleware.servicios.modelo;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * 
 * @author Arzate-Jacinto A.
 *
 */
@Entity
@Table(name="T_DETALLE_ISIN_CONCILIACION")
@SequenceGenerator(name = "foliador",sequenceName = "SEQ_T_DET_ISIN_CONC_INT",initialValue = 1, allocationSize = 1)
public class DetalleIsinConciliacionInt implements Serializable {


	private static final long serialVersionUID = -8107368254386835571L;
    
	
	private Long id;
	private Long idDetalleConciliacion;
	private String posicionEmision;
	private String conceptoPosicion;
	private Double valor;
	private String tipoMoneda;
	
	
	public DetalleIsinConciliacionInt() {
		super();
	}
	
	public DetalleIsinConciliacionInt(
			Long detalleConciliacion,
			Double valor, String tipoMoneda) {
		super();
		this.idDetalleConciliacion = detalleConciliacion;
		this.valor = valor;
		this.tipoMoneda = tipoMoneda;
	}
	
	public DetalleIsinConciliacionInt(DetalleIsinConciliacionInt detalleIsin) {
		super();
		this.valor = new Double(detalleIsin.getValor());
		this.tipoMoneda = detalleIsin.getTipoMoneda();
	}

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="foliador")
	@Column(name="ID_DETALLE_ISIN")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name="ID_DETALLE_CONCILIACION")
	public Long getIdDetalleConciliacion() {
		return idDetalleConciliacion;
	}
	public void setIdDetalleConciliacion(Long idDetalleConciliacion) {
		this.idDetalleConciliacion = idDetalleConciliacion;
	}
	
	/**
	 * Es el nombre o concepto AGGR AVAI ,etc
	 * @return
	 */
	@Column(name="DESC_POSICION_EMISION")
	public String getPosicionEmision() {
		return posicionEmision.trim();
	}

	public void setPosicionEmision(String posicionEmision) {
		this.posicionEmision = posicionEmision.trim();
	}
	
	/**
	 * puede ser FAMT UNIT o AMOR
	 * @return
	 */
    @Column(name = "DESC_EMISION_CONCEPTO")
	public String getConceptoPosicion() {
		return conceptoPosicion;
	}

	public void setConceptoPosicion(String conceptoPosicion) {
		this.conceptoPosicion = conceptoPosicion;
	}

	@Column(name="CANTIDAD")
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	public void setValor(String valor)
	{
		this.valor = new Double(valor);
	}
	
	@Column(name="TIPO_MONEDA")
	public String getTipoMoneda() {
		return tipoMoneda;
	}
	public void setTipoMoneda(String tipoMoneda) {
		this.tipoMoneda = tipoMoneda;
	}
	
	
	public void add(String valor)
	{
		this.valor = this.valor + new Double(valor);
	}
	
	@Override
	public String toString()
	{
		StringBuilder lsbDetalleIsinConc = new StringBuilder();
		lsbDetalleIsinConc.append("\t id: \t\t--->\t")
		.append(this.id)
		.append("\n\t id detalle: \t\t--->\t")
		.append(this.idDetalleConciliacion)
		.append("\n\t posicion emision: \t\t--->\t")
		.append(this.posicionEmision)
		.append("\n\t concepto posicion: \t\t--->\t")
		.append(this.conceptoPosicion)
		.append("\n\t valor: \t\t--->\t")
		.append(this.valor)
		.append("\n\t tipo moneda: \t\t--->\t")
		.append(this.tipoMoneda);
		return "";
	}
}

