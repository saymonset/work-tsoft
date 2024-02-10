package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.indeval.portaldali.persistence.modelo.Boveda;
import com.indeval.portaldali.persistence.modelo.Cupon;
import com.indeval.portaldali.persistence.modelo.Divisa;

@Entity
@Table(name="T_DERECHOS_CAPITAL")
public class Derecho implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5821111663277864567L;
	
	private Long idDerechoCapital;
	
	private Cupon cuponOrigen;
	
	private ElementEstatusDerecho elementEstatusDerecho;
	
	private Float precioProducto;
	
	private Divisa divisaProducto;
	
	private Date fechaCorte;
	
	private Date fechaPago;
	
	private TipoDerecho tipoDerecho;
	
	private SubTipoDerecho subTipoDerecho;
	
	private Boveda boveda;
	
	private List<DerechoBeneficiario> derechosBeneficiarios;
	
	private List<GrupoRetencion> gruposRetencion;	

    @Id
    @Column(name ="ID_DERECHO_CAPITAL", unique = true, nullable = false)
	public Long getIdDerechoCapital() {
		return idDerechoCapital;
	}

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name ="ID_CUPON_ORIGEN", nullable = true)
	public Cupon getCuponOrigen() {
		return cuponOrigen;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_ELEM_ESTATUS_DERECHO", nullable = false)
	public ElementEstatusDerecho getElementEstatusDerecho() {
		return elementEstatusDerecho;
	}

    @Column(name ="PRECIO_PRODUCTO", unique = false, nullable = false)
	public Float getPrecioProducto() {
		return precioProducto;
	}

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name ="ID_DIVISA_PRODUCTO",nullable = false)
	public Divisa getDivisaProducto() {    	
		return divisaProducto;
	}

    @Column(name ="FECHA_CORTE", unique = false, nullable = false)
	public Date getFechaCorte() {
		return fechaCorte;
	}

    @Column(name ="FECHA_PAGO_DERECHO_CAPITAL", unique = false, nullable = false)
	public Date getFechaPago() {
		return fechaPago;
	}

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name ="ID_TIPO_DERECHO",nullable = false)
	public TipoDerecho getTipoDerecho() {
		return tipoDerecho;
	}
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name ="ID_SUBTIPO_DERECHO",nullable = true)
	public SubTipoDerecho getSubTipoDerecho() {
		return subTipoDerecho;
	}

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name ="ID_BOVEDA_EFECTIVO",nullable = false)
	public Boveda getBoveda() {
		return boveda;
	}
    
	@OneToMany(fetch= FetchType.EAGER)
	@JoinColumn(name = "ID_DERECHO_CAPITAL")
	public List<DerechoBeneficiario> getDerechosBeneficiarios() {
		return derechosBeneficiarios;
	}
	
    @OneToMany(fetch= FetchType.LAZY)
	@JoinColumn(name = "ID_DERECHO_CAPITAL")
	public List<GrupoRetencion> getGruposRetencion() {
		return gruposRetencion;
	}

	public void setIdDerechoCapital(Long idDerechoCapital) {
		this.idDerechoCapital = idDerechoCapital;
	}

	public void setCuponOrigen(Cupon cuponOrigen) {
		this.cuponOrigen = cuponOrigen;
	}

	public void setElementEstatusDerecho(ElementEstatusDerecho elementEstatusDerecho) {
		this.elementEstatusDerecho = elementEstatusDerecho;
	}

	public void setPrecioProducto(Float precioProducto) {
		this.precioProducto = precioProducto;
	}

	public void setDivisaProducto(Divisa divisaProducto) {
		this.divisaProducto = divisaProducto;
	}

	public void setFechaCorte(Date fechaCorte) {
		this.fechaCorte = fechaCorte;
	}

	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}

	public void setBoveda(Boveda boveda) {
		this.boveda = boveda;
	}

	public void setDerechosBeneficiarios(
			List<DerechoBeneficiario> derechoBeneficiarios) {
		this.derechosBeneficiarios = derechoBeneficiarios;
	}

	public void setGruposRetencion(List<GrupoRetencion> gruposRetencion) {
		this.gruposRetencion = gruposRetencion;
	}

	public void setTipoDerecho(TipoDerecho tipoDerecho) {
		this.tipoDerecho = tipoDerecho;
	}

	public void setSubTipoDerecho(SubTipoDerecho subTipoDerecho) {
		this.subTipoDerecho = subTipoDerecho;
	}
	
}
