/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.modelo;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.validation.Errors;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class EmisionVO extends AbstractBaseDTO {

	/** Constante de Serializacion */
	private static final long serialVersionUID = 1L;

	/** */
	private String idTipoValor;

	/** */
	private String emisora;

	/** */
	private String serie;

	/** */
	private String cupon;

	/** */
	private BigDecimal saldoDisponible;

	/** */
	private BigDecimal saldoInicialDia;

	/** */
	private Date fechaVencimiento;

	/** */
	private BigDecimal precioVector;

	/** */
	private String isin;

	/** */
	private String mercado;

	/** */
	private Integer diasVigentes;

	/** */
	private String alta;

	/** */
	private String cuponCortado;

	/** Ultimo Hecho */
	private BigDecimal ultimoHecho;

	/** Valor Nominal */
	private BigDecimal valorNominal;

	private String nombreCortoBoveda;

	/** Constructor */
	public EmisionVO() {
		this.setSaldoDisponible(BIG_DECIMAL_ZERO);
		this.setSaldoInicialDia(BIG_DECIMAL_ZERO);
		this.setPrecioVector(BIG_DECIMAL_ZERO);
		setUltimoHecho(BIG_DECIMAL_ZERO);
		setValorNominal(BIG_DECIMAL_ZERO);
	}

	/**
	 * Obtiene el Ultimo Hecho
	 * 
	 * @return Ultimo Hecho
	 */
	public BigDecimal getUltimoHecho() {
		return ultimoHecho;
	}

	/**
	 * Establece el Ultimo Hecho
	 * 
	 * @param ultimoHecho
	 *            Ultimo Hecho a establecer
	 */
	public void setUltimoHecho(BigDecimal ultimoHecho) {
		this.ultimoHecho = ultimoHecho;
	}

	/**
	 * Obtiene el Valor Nominal
	 * 
	 * @return Valor Nominal
	 */
	public BigDecimal getValorNominal() {
		return valorNominal;
	}

	/**
	 * Establece el Valor Nominal
	 * 
	 * @param valorNominal
	 *            Valor Nominal a establecer
	 */
	public void setValorNominal(BigDecimal valorNominal) {
		this.valorNominal = valorNominal;
	}

	/**
	 * @return String
	 */
	public String getMercado() {
		return mercado;
	}

	/**
	 * @param mercado
	 */
	public void setMercado(String mercado) {
		this.mercado = mercado;
	}

	/**
	 * @return String
	 */
	public String getCupon() {
		return cupon;
	}

	/**
	 * @param cupon
	 */
	public void setCupon(String cupon) {
		this.cupon = cupon;
		if (this.cupon != null)
			this.cupon = this.cupon.toUpperCase();
	}

	/**
	 * @return String
	 */
	public String getEmisora() {
		return emisora;
	}

	/**
	 * @param emisora
	 */
	public void setEmisora(String emisora) {
		this.emisora = emisora;
		if (this.emisora != null)
			this.emisora = this.emisora.toUpperCase();

	}

	/**
	 * @return String
	 */
	public String getIdTipoValor() {
		return idTipoValor;
	}

	/**
	 * @param idTipoValor
	 */
	public void setIdTipoValor(String idTipoValor) {
		this.idTipoValor = idTipoValor;
		if (this.idTipoValor != null)
			this.idTipoValor = this.idTipoValor.toUpperCase();
	}

	/**
	 * @return String
	 */
	public String getSerie() {
		return serie;
	}

	/**
	 * @param serie
	 */
	public void setSerie(String serie) {
		this.serie = serie;
		if (this.serie != null)
			this.serie = this.serie.toUpperCase();
	}

	/**
	 * @return Date
	 */
	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}

	/**
	 * @param fechaVencimiento
	 */
	public void setFechaVencimiento(Date fechaVencimiento) {
		if (fechaVencimiento != null) {
			this.fechaVencimiento = clona(fechaVencimiento);
		} else {
			this.fechaVencimiento = null;
		}
	}

	/**
	 * @return String
	 */
	public String getIsin() {
		return isin;
	}

	/**
	 * @param isin
	 */
	public void setIsin(String isin) {
		this.isin = isin;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getPrecioVector() {
		return precioVector;
	}

	/**
	 * @param precioVector
	 */
	public void setPrecioVector(BigDecimal precioVector) {
		this.precioVector = clonaBigDecimal(precioVector);
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getSaldoDisponible() {
		return saldoDisponible;
	}

	/**
	 * @param saldoDisponible
	 */
	public void setSaldoDisponible(BigDecimal saldoDisponible) {
		this.saldoDisponible = clonaBigDecimal(saldoDisponible);
	}

	/**
	 * @return Integer
	 */
	public Integer getDiasVigentes() {
		return diasVigentes;
	}

	/**
	 * @param diasVigentes
	 */
	public void setDiasVigentes(Integer diasVigentes) {
		this.diasVigentes = diasVigentes;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getSaldoInicialDia() {
		return saldoInicialDia;
	}

	/**
	 * @param saldoInicialDia
	 */
	public void setSaldoInicialDia(BigDecimal saldoInicialDia) {
		this.saldoInicialDia = clonaBigDecimal(saldoInicialDia);
	}

	/**
	 * @return String
	 */
	public String getAlta() {
		return this.alta;
	}

	/**
	 * @param alta
	 */
	public void setAlta(String alta) {
		this.alta = alta;
	}

	/**
	 * @return String
	 */
	public String getCuponCortado() {
		return cuponCortado;
	}

	/**
	 * @param cuponCortado
	 */
	public void setCuponCortado(String cuponCortado) {
		this.cuponCortado = cuponCortado;
	}

	public String getNombreCortoBoveda() {
		return nombreCortoBoveda;
	}
	
	public void setNombreCortoBoveda(final String nombreCortoBoveda) {
		this.nombreCortoBoveda = nombreCortoBoveda;
	}

	/**
	 * Retorna true si el objeto recibido es una instancia de EmisionVO
	 * 
	 * @param inObject
	 * @return boolean
	 */
	public static boolean isAn(Object inObject) {

		return !(inObject == null) && ((inObject instanceof EmisionVO));
	}

	/**
	 * Retorna una cadena String con los valores de los atributos del objeto
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("Mercado", getMercado())
				.append("Alta", getAlta())
				.append("CuponCortado", getCuponCortado())
				.append("IdTipoValor", getIdTipoValor())
				.append("Emisora", getEmisora()).append("Serie", getSerie())
				.append("Cupon", getCupon())
				.append("Boveda", getNombreCortoBoveda())
				.append("SaldoDisponible", getSaldoDisponible()).toString();
	}

	/**
	 * Retorna una cadena String con los valores de los atributos tv, emisora,
	 * serie, cupon del objeto
	 * 
	 * @return String
	 */
	public String toResumeString() {
		StringBuffer sbEmisionVO = new StringBuffer(StringUtils.isNotBlank(this.getIdTipoValor()) ? this.getIdTipoValor().trim() : "");
		if (StringUtils.isNotBlank(this.getEmisora()))
			sbEmisionVO.append(" : " + this.getEmisora().trim());
		if (StringUtils.isNotBlank(this.getSerie()))
			sbEmisionVO.append(" : " + this.getSerie().trim());
		if (StringUtils.isNotBlank(this.getCupon()))
			sbEmisionVO.append(" : " + this.getCupon().trim());

		return sbEmisionVO.toString();
	}

	/**
	 * Valida que la PK (TV, EMISORA, SERIE, CUPON) de la emision sea valida
	 * 
	 * @throws BusinessException
	 */
	public void tienePKValida() throws BusinessException {
		if (StringUtils.isBlank(this.idTipoValor)) {
			throw new BusinessException("Error: La emision tiene NULL o VACIO el atributo TV");
		}
		if (StringUtils.isBlank(this.emisora)) {
			throw new BusinessException("Error: La emision tiene NULL o VACIO el atributo EMISORA");
		}
		if (StringUtils.isBlank(this.serie)) {
			throw new BusinessException("Error: La emision tiene NULL o VACIO el atributo SERIE");
		}
		if (StringUtils.isBlank(this.cupon)) {
			throw new BusinessException("Error: La emision tiene NULL o VACIO el atributo CUPON");
		}
	}

	/**
	 * Compara usando solo la llave (tv, emisora, serie, cupon)
	 * 
	 * @param obj
	 *            contra el cual se compara.
	 * @return boolean
	 */
	public boolean equals(Object obj) {
		boolean valido = isAn(obj);
		if (valido) {
			EmisionVO emision = (EmisionVO) obj;

			if ((this.getIdTipoValor() == null && emision.getIdTipoValor() != null) || (this.getEmisora() == null && emision.getEmisora() != null)
					|| (this.getSerie() == null && emision.getSerie() != null) || (this.getCupon() == null && emision.getCupon() != null)) {
				valido = false;
			}
			if (valido) {
				if ((StringUtils.isNotBlank(this.idTipoValor) && !this.idTipoValor.trim().equalsIgnoreCase(
						emision.getIdTipoValor() == null ? null : emision.getIdTipoValor().trim()))
						|| (StringUtils.isNotBlank(this.emisora) && !this.emisora.trim().equalsIgnoreCase(
								emision.getEmisora() == null ? null : emision.getEmisora().trim()))
						|| (StringUtils.isNotBlank(this.serie) && !this.serie.trim().equalsIgnoreCase(
								emision.getSerie() == null ? null : emision.getSerie().trim()))
						|| (StringUtils.isNotBlank(this.cupon) && !this.cupon.trim().equalsIgnoreCase(
								emision.getCupon() == null ? null : emision.getCupon().trim()))) {
					valido = false;
				}
			}
		}

		return valido;
	}

	/**
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object obj, Errors errors) {

	}


}
