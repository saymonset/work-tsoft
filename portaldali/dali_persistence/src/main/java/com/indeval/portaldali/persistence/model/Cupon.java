/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

/**
 * 
 * @author Sandra Flores Arrieta
 * @version 1.0
 */
@Entity
@Table(name = "C_CUPON")
@FilterDef (name="cuponActivo", parameters=@ParamDef(name="estadoCupon", type="integer"))
@Filter(name="cuponActivo", condition=":estadoCupon = idEstadoCupon")
public class Cupon implements Serializable {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Identificador del cupón.
	 */
	@Id
	@Column(name = "ID_CUPON")
	private BigInteger idCupon;

	/**
	 * Emisión a la que pertenece el cupón.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_EMISION", updatable = false, insertable = false)
	private Emision emision;

	/**
	 * Identificador de la emisión a la que pertenece el cupón.
	 */
	@Column(name = "ID_EMISION")
	private BigInteger idEmision;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_ESTADO_CUPON")
	private EstatusCupones estadoCupon; // FK

	/**
	 * Identificador del estado del cupón.
	 */
	@Column(name = "ID_ESTADO_CUPON", insertable=false, updatable=false)
	private BigInteger idEstadoCupon;

	/**
	 * Fecha y hora de inicio del periodo.
	 */
	@Column(name = "FECHA_INICIO_PERIODO")
	private Date fechaInicio;

	/**
	 * Fecha y hora de fin del periodo.
	 */
	@Column(name = "FECHA_FIN_PERIODO")
	private Date fechaFin;

	/**
	 * Clave del cupón.
	 */
	@Column(name = "CLAVE_CUPON")
	private String claveCupon;
	@Column(name = "fecha_pago", unique = false, nullable = true)
	private Date fechaPago;
	@Column(name = "plazo_periodo_vigente", unique = false, nullable = true)
	private BigDecimal plazoPeriodoVigente;
	@Column(name = "valor_nominal", unique = false, nullable = true)
	private BigDecimal valorNominal;
	@Column(name = "id_divisa_valor_nominal", unique = false, nullable = true)
	private BigInteger idDivisaValorNominal;
	@Column(name = "factor_actualizacion", unique = false, nullable = true)
	private BigDecimal factorActualizacion;
	@Column(name = "id_divisa_factor_actualizacion", unique = false, nullable = true)
	private BigInteger idDivisaFactorActualizacion;
	@Column(name = "id_divisa_pago", unique = false, nullable = true)
	private BigInteger idDivisaPago;
	@Column(name = "fecha_corte", unique = false, nullable = true)
	private Date fechaCorte;
	@Column(name = "tasa_interes", unique = false, nullable = true)
	private BigDecimal tasaInteres;
	@Column(name = "tasa_rendimiento", unique = false, nullable = true)
	private BigDecimal tasaRendimiento;
	@Column(name = "tasa_descuento", unique = false, nullable = true)
	private BigDecimal tasaDescuento;
	@Column(name = "valor_ultimo_hecho", unique = false, nullable = true)
	private BigDecimal valorUltimoHecho;
	@Column(name = "valor_nominal_original", unique = false, nullable = true)
	private BigDecimal valorNominalOriginal;
	@Column(name = "fecha_ultimo_hecho", unique = false, nullable = true)
	private Date fechaUltimoHecho;
	@Column(name = "fecha_creacion", unique = true, nullable = false)
	private Date fechaCreacion;
	@Column(name = "fecha_ult_modificacion", unique = true, nullable = false)
	private Date fechaUltModificacion;

	/**
	 * método para obtener el atributo idCupon
	 * 
	 * @return the idCupon
	 */
	public BigInteger getIdCupon() {
		return idCupon;
	}

	/**
	 * método para establecer el atributo idCupon
	 * 
	 * @param idCupon
	 *            the idCupon to set
	 */
	public void setIdCupon(BigInteger idCupon) {
		this.idCupon = idCupon;
	}

	/**
	 * método para obtener el atributo emision
	 * 
	 * @return the emision
	 */
	public Emision getEmision() {
		return emision;
	}

	/**
	 * método para establecer el atributo emision
	 * 
	 * @param emision
	 *            the emision to set
	 */
	public void setEmision(Emision emision) {
		this.emision = emision;
	}

	/**
	 * método para obtener el atributo idEmision
	 * 
	 * @return the idEmision
	 */
	public BigInteger getIdEmision() {
		return idEmision;
	}

	/**
	 * método para establecer el atributo idEmision
	 * 
	 * @param idEmision
	 *            the idEmision to set
	 */
	public void setIdEmision( BigInteger idEmision) {
		this.idEmision = idEmision;
	}

	/**
	 * método para obtener el atributo idEstadoCupon
	 * 
	 * @return the idEstadoCupon
	 */
	public BigInteger getIdEstadoCupon() {
		return idEstadoCupon;
	}

	/**
	 * método para establecer el atributo idEstadoCupon
	 * 
	 * @param idEstadoCupon
	 *            the idEstadoCupon to set
	 */
	public void setIdEstadoCupon(BigInteger idEstadoCupon) {
		this.idEstadoCupon = idEstadoCupon;
	}

	/**
	 * método para obtener el atributo fechaInicio
	 * 
	 * @return the fechaInicio
	 */
	public Date getFechaInicio() {
		return fechaInicio;
	}

	/**
	 * método para establecer el atributo fechaInicio
	 * 
	 * @param fechaInicio
	 *            the fechaInicio to set
	 */
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	/**
	 * método para obtener el atributo fechaFin
	 * 
	 * @return the fechaFin
	 */
	public Date getFechaFin() {
		return fechaFin;
	}

	/**
	 * método para establecer el atributo fechaFin
	 * 
	 * @param fechaFin
	 *            the fechaFin to set
	 */
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	/**
	 * método para obtener el atributo claveCupon
	 * 
	 * @return the claveCupon
	 */
	public String getClaveCupon() {
		return claveCupon;
	}

	/**
	 * método para establecer el atributo claveCupon
	 * 
	 * @param claveCupon
	 *            the claveCupon to set
	 */
	public void setClaveCupon(String claveCupon) {
		this.claveCupon = claveCupon;
	}

	public EstatusCupones getEstadoCupon() {
		return estadoCupon;
	}

	
	public Date getFechaPago() {
		return fechaPago;
	}


	public BigDecimal getPlazoPeriodoVigente() {
		return plazoPeriodoVigente;
	}

	
	public BigDecimal getValorNominal() {
		return valorNominal;
	}

	
	public BigInteger getIdDivisaValorNominal() {
		return idDivisaValorNominal;
	}

	public BigDecimal getFactorActualizacion() {
		return factorActualizacion;
	}

	
	public BigInteger getIdDivisaFactorActualizacion() {
		return idDivisaFactorActualizacion;
	}

	
	public BigInteger getIdDivisaPago() {
		return idDivisaPago;
	}

	
	public Date getFechaCorte() {
		return fechaCorte;
	}

	
	public BigDecimal getTasaInteres() {
		return tasaInteres;
	}

	
	public BigDecimal getTasaRendimiento() {
		return tasaRendimiento;
	}


	public BigDecimal getTasaDescuento() {
		return tasaDescuento;
	}

	
	public BigDecimal getValorUltimoHecho() {
		return valorUltimoHecho;
	}

	
	public BigDecimal getValorNominalOriginal() {
		return valorNominalOriginal;
	}

	
	public Date getFechaUltimoHecho() {
		return fechaUltimoHecho;
	}

	
	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	
	public Date getFechaUltModificacion() {
		return fechaUltModificacion;
	}

	/**
	 * @param idEstadoCupon
	 */
	public void setEstadoCupon(EstatusCupones estadoCupon) {
		this.estadoCupon = estadoCupon;
	}

	/**
	 * @param fechaPago
	 */
	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}

	/**
	 * @param plazoPeriodoVigente
	 */
	public void setPlazoPeriodoVigente(BigDecimal plazoPeriodoVigente) {
		this.plazoPeriodoVigente = plazoPeriodoVigente;
	}

	/**
	 * @param valorNominal
	 */
	public void setValorNominal(BigDecimal valorNominal) {
		this.valorNominal = valorNominal;
	}

	/**
	 * @param idDivisaValorNominal
	 */
	public void setIdDivisaValorNominal(BigInteger idDivisaValorNominal) {
		this.idDivisaValorNominal = idDivisaValorNominal;
	}

	/**
	 * @param factorActualizacion
	 */
	public void setFactorActualizacion(BigDecimal factorActualizacion) {
		this.factorActualizacion = factorActualizacion;
	}

	/**
	 * @param idDivisaFactorActualizacion
	 */
	public void setIdDivisaFactorActualizacion(
			BigInteger idDivisaFactorActualizacion) {
		this.idDivisaFactorActualizacion = idDivisaFactorActualizacion;
	}

	/**
	 * @param idDivisaPago
	 */
	public void setIdDivisaPago(BigInteger idDivisaPago) {
		this.idDivisaPago = idDivisaPago;
	}

	/**
	 * @param fechaCorte
	 */
	public void setFechaCorte(Date fechaCorte) {
		this.fechaCorte = fechaCorte;
	}

	/**
	 * @param tasaInteres
	 */
	public void setTasaInteres(BigDecimal tasaInteres) {
		this.tasaInteres = tasaInteres;
	}

	/**
	 * @param tasaRendimiento
	 */
	public void setTasaRendimiento(BigDecimal tasaRendimiento) {
		this.tasaRendimiento = tasaRendimiento;
	}

	/**
	 * @param tasaDescuento
	 */
	public void setTasaDescuento(BigDecimal tasaDescuento) {
		this.tasaDescuento = tasaDescuento;
	}

	/**
	 * @param valorUltimoHecho
	 */
	public void setValorUltimoHecho(BigDecimal valorUltimoHecho) {
		this.valorUltimoHecho = valorUltimoHecho;
	}

	/**
	 * @param valorNominalOriginal
	 */
	public void setValorNominalOriginal(BigDecimal valorNominalOriginal) {
		this.valorNominalOriginal = valorNominalOriginal;
	}

	/**
	 * @param fechaUltimoHecho
	 */
	public void setFechaUltimoHecho(Date fechaUltimoHecho) {
		this.fechaUltimoHecho = fechaUltimoHecho;
	}

	/**
	 * @param fechaCreacion
	 */
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	/**
	 * @param fechaUltModificacion
	 */
	public void setFechaUltModificacion(Date fechaUltModificacion) {
		this.fechaUltModificacion = fechaUltModificacion;
	}

}
