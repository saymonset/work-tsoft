/**
 * Bursatec - Portal Internacional
 * Copyrigth (c) 2016 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.servicios.vo;

import java.io.Serializable;

/**
 * Transfer Object con los totales por porcentaje de retención.
 * 
 * @author Pablo Balderas
 *
 */
public class TotalesPorcentajeRetencionTO implements Serializable {

	/** Id para la serializacion */
	private static final long serialVersionUID = -7840493251813906334L;

	/** Porcentaje de retencion */
	private Double porcentajeRetencionReal;
	
	/** Asignacion o numero de titulos */
	private Long asignacion;
	
	/** Monto bruto */
	private Double montoBruto;
	
	/** Monto fee */
	private Double montoFee;
	
	/** Impuesto retenido */
	private Double impuestoRetenido;
	
	/** Monto neto */
	private Double montoNeto;

	/**
	 * Método para obtener el atributo porcentajeRetencionReal
	 * @return El atributo porcentajeRetencionReal
	 */
	public Double getPorcentajeRetencionReal() {
		return porcentajeRetencionReal;
	}

	/**
	 * Método para establecer el atributo porcentajeRetencionReal
	 * @param porcentajeRetencionReal El valor del atributo porcentajeRetencionReal a establecer.
	 */
	public void setPorcentajeRetencionReal(Double porcentajeRetencionReal) {
		this.porcentajeRetencionReal = porcentajeRetencionReal != null ? porcentajeRetencionReal : 0;
	}

	/**
	 * Método para obtener el atributo asignacion
	 * @return El atributo asignacion
	 */
	public Long getAsignacion() {
		return asignacion;
	}

	/**
	 * Método para establecer el atributo asignacion
	 * @param asignacion El valor del atributo asignacion a establecer.
	 */
	public void setAsignacion(Long asignacion) {
		this.asignacion = asignacion != null ? asignacion : 0;
	}

	/**
	 * Método para obtener el atributo montoBruto
	 * @return El atributo montoBruto
	 */
	public Double getMontoBruto() {
		return montoBruto;
	}

	/**
	 * Método para establecer el atributo montoBruto
	 * @param montoBruto El valor del atributo montoBruto a establecer.
	 */
	public void setMontoBruto(Double montoBruto) {
		this.montoBruto = montoBruto != null ? montoBruto : 0;
	}

	/**
	 * Método para obtener el atributo montoFee
	 * @return El atributo montoFee
	 */
	public Double getMontoFee() {
		return montoFee;
	}

	/**
	 * Método para establecer el atributo montoFee
	 * @param montoFee El valor del atributo montoFee a establecer.
	 */
	public void setMontoFee(Double montoFee) {
		this.montoFee = montoFee != null ? montoFee : 0;
	}

	/**
	 * Método para obtener el atributo impuestoRetenido
	 * @return El atributo impuestoRetenido
	 */
	public Double getImpuestoRetenido() {
		return impuestoRetenido;
	}

	/**
	 * Método para establecer el atributo impuestoRetenido
	 * @param impuestoRetenido El valor del atributo impuestoRetenido a establecer.
	 */
	public void setImpuestoRetenido(Double impuestoRetenido) {
		this.impuestoRetenido = impuestoRetenido != null ? impuestoRetenido : 0;
	}

	/**
	 * Método para obtener el atributo montoNeto
	 * @return El atributo montoNeto
	 */
	public Double getMontoNeto() {
		return montoNeto;
	}

	/**
	 * Método para establecer el atributo montoNeto
	 * @param montoNeto El valor del atributo montoNeto a establecer.
	 */
	public void setMontoNeto(Double montoNeto) {
		this.montoNeto = montoNeto != null ? montoNeto : 0;
	}
	
	
}
