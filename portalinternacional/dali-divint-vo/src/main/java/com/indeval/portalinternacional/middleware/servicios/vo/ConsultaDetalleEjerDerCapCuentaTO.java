/**
 * Bursatec - Portal Internacional
 * Copyrigth (c) 2016 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.servicios.vo;

import java.io.Serializable;
import java.util.List;

import com.indeval.portalinternacional.middleware.servicios.modelo.capitales.DerechoCapitalHistoricoCuenta;

/**
 * Transfer Object que representa el resultado de la consulta de detalle de
 * ejercicio de derechos de capital por cuenta
 * 
 * @author Pablo Balderas
 */
public class ConsultaDetalleEjerDerCapCuentaTO implements Serializable {

	/** Id para la serialización */
	private static final long serialVersionUID = 622868833665334198L;

	/** Lista con los resultados de la consulta  */
	private List<DerechoCapitalHistoricoCuenta> resultadosConsulta;
	
	/** Lista con los totales por porncentaje de retención */
	private List<TotalesPorcentajeRetencionTO> totalesPorcentajeRetencion;

	/** Subtotal asignación */
    private Long subtotalAsignacion = 0L;
    
    /** Subtotal monto bruto */
    private Double subtotalMontoBruto = 0D;

    /** Subtotal monto fee */
    private Double subtotalMontoFee = 0D;
    
    /** Subtotal impuesto retenido */
    private Double subtotalImpuestoRetenido = 0D;

    /** Subtotal monto neto */
    private Double subtotalMontoNeto = 0D;
    
    /** Total asignación */
    private Long totalAsignacion = 0L;
    
    /** Total monto bruto */
    private Double totalMontoBruto = 0D;

    /** Total monto fee */
    private Double totalMontoFee = 0D;
    
    /** Total impuesto retenido */
    private Double totalImpuestoRetenido = 0D;

    /** Total monto neto */
    private Double totalMontoNeto = 0D;

	/**
	 * Método para obtener el atributo resultadosConsulta
	 * @return El atributo resultadosConsulta
	 */
	public List<DerechoCapitalHistoricoCuenta> getResultadosConsulta() {
		return resultadosConsulta;
	}

	/**
	 * Método para establecer el atributo resultadosConsulta
	 * @param resultadosConsulta El valor del atributo resultadosConsulta a establecer.
	 */
	public void setResultadosConsulta(List<DerechoCapitalHistoricoCuenta> resultadosConsulta) {
		this.resultadosConsulta = resultadosConsulta;
	}

	/**
	 * Método para obtener el atributo subtotalAsignacion
	 * @return El atributo subtotalAsignacion
	 */
	public Long getSubtotalAsignacion() {
		return subtotalAsignacion;
	}

	/**
	 * Método para establecer el atributo subtotalAsignacion
	 * @param subtotalAsignacion El valor del atributo subtotalAsignacion a establecer.
	 */
	public void setSubtotalAsignacion(Long subtotalAsignacion) {
		this.subtotalAsignacion = subtotalAsignacion;
	}

	/**
	 * Método para obtener el atributo subtotalMontoBruto
	 * @return El atributo subtotalMontoBruto
	 */
	public Double getSubtotalMontoBruto() {
		return subtotalMontoBruto;
	}

	/**
	 * Método para establecer el atributo subtotalMontoBruto
	 * @param subtotalMontoBruto El valor del atributo subtotalMontoBruto a establecer.
	 */
	public void setSubtotalMontoBruto(Double subtotalMontoBruto) {
		this.subtotalMontoBruto = subtotalMontoBruto;
	}

	/**
	 * Método para obtener el atributo subtotalImpuestoRetenido
	 * @return El atributo subtotalImpuestoRetenido
	 */
	public Double getSubtotalImpuestoRetenido() {
		return subtotalImpuestoRetenido;
	}

	/**
	 * Método para establecer el atributo subtotalImpuestoRetenido
	 * @param subtotalImpuestoRetenido El valor del atributo subtotalImpuestoRetenido a establecer.
	 */
	public void setSubtotalImpuestoRetenido(Double subtotalImpuestoRetenido) {
		this.subtotalImpuestoRetenido = subtotalImpuestoRetenido;
	}

	/**
	 * Método para obtener el atributo subtotalMontoNeto
	 * @return El atributo subtotalMontoNeto
	 */
	public Double getSubtotalMontoNeto() {
		return subtotalMontoNeto;
	}

	/**
	 * Método para establecer el atributo subtotalMontoNeto
	 * @param subtotalMontoNeto El valor del atributo subtotalMontoNeto a establecer.
	 */
	public void setSubtotalMontoNeto(Double subtotalMontoNeto) {
		this.subtotalMontoNeto = subtotalMontoNeto;
	}

	/**
	 * Método para obtener el atributo totalAsignacion
	 * @return El atributo totalAsignacion
	 */
	public Long getTotalAsignacion() {
		return totalAsignacion;
	}

	/**
	 * Método para establecer el atributo totalAsignacion
	 * @param totalAsignacion El valor del atributo totalAsignacion a establecer.
	 */
	public void setTotalAsignacion(Long totalAsignacion) {
		this.totalAsignacion = totalAsignacion;
	}

	/**
	 * Método para obtener el atributo totalMontoBruto
	 * @return El atributo totalMontoBruto
	 */
	public Double getTotalMontoBruto() {
		return totalMontoBruto;
	}

	/**
	 * Método para establecer el atributo totalMontoBruto
	 * @param totalMontoBruto El valor del atributo totalMontoBruto a establecer.
	 */
	public void setTotalMontoBruto(Double totalMontoBruto) {
		this.totalMontoBruto = totalMontoBruto;
	}

	/**
	 * Método para obtener el atributo totalImpuestoRetenido
	 * @return El atributo totalImpuestoRetenido
	 */
	public Double getTotalImpuestoRetenido() {
		return totalImpuestoRetenido;
	}

	/**
	 * Método para establecer el atributo totalImpuestoRetenido
	 * @param totalImpuestoRetenido El valor del atributo totalImpuestoRetenido a establecer.
	 */
	public void setTotalImpuestoRetenido(Double totalImpuestoRetenido) {
		this.totalImpuestoRetenido = totalImpuestoRetenido;
	}

	/**
	 * Método para obtener el atributo totalMontoNeto
	 * @return El atributo totalMontoNeto
	 */
	public Double getTotalMontoNeto() {
		return totalMontoNeto;
	}

	/**
	 * Método para establecer el atributo totalMontoNeto
	 * @param totalMontoNeto El valor del atributo totalMontoNeto a establecer.
	 */
	public void setTotalMontoNeto(Double totalMontoNeto) {
		this.totalMontoNeto = totalMontoNeto;
	}

	/**
	 * Método para obtener el atributo subtotalMontoFee
	 * @return El atributo subtotalMontoFee
	 */
	public Double getSubtotalMontoFee() {
		return subtotalMontoFee;
	}

	/**
	 * Método para establecer el atributo subtotalMontoFee
	 * @param subtotalMontoFee El valor del atributo subtotalMontoFee a establecer.
	 */
	public void setSubtotalMontoFee(Double subtotalMontoFee) {
		this.subtotalMontoFee = subtotalMontoFee;
	}

	/**
	 * Método para obtener el atributo totalMontoFee
	 * @return El atributo totalMontoFee
	 */
	public Double getTotalMontoFee() {
		return totalMontoFee;
	}

	/**
	 * Método para establecer el atributo totalMontoFee
	 * @param totalMontoFee El valor del atributo totalMontoFee a establecer.
	 */
	public void setTotalMontoFee(Double totalMontoFee) {
		this.totalMontoFee = totalMontoFee;
	}

	/**
	 * Método para obtener el atributo totalesPorcentajeRetencion
	 * @return El atributo totalesPorcentajeRetencion
	 */
	public List<TotalesPorcentajeRetencionTO> getTotalesPorcentajeRetencion() {
		return totalesPorcentajeRetencion;
	}

	/**
	 * Método para establecer el atributo totalesPorcentajeRetencion
	 * @param totalesPorcentajeRetencion El valor del atributo totalesPorcentajeRetencion a establecer.
	 */
	public void setTotalesPorcentajeRetencion(List<TotalesPorcentajeRetencionTO> totalesPorcentajeRetencion) {
		this.totalesPorcentajeRetencion = totalesPorcentajeRetencion;
	}
	
}
