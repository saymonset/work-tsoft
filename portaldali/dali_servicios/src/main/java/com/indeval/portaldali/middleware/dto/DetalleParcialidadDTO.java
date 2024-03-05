/**
 * 2H Software
 * 
 * Sistema de Consulta de Estado de Cuenta - Indeval
 *
 * Creado el Jan 8, 2008
 */
package com.indeval.portaldali.middleware.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Data Transfer Object que representa el detalle de una parcialidad en una
 * operación DVP
 *
 * @author José Antonio Huizar Moreno
 * @version 1.0
 *
 */
public class DetalleParcialidadDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** La fecha en que se realizó el pago de la parcialidad */
	private Date fecha;
	
	/** El folio de la operación o la instrucción de la parcialidad */
	private String folio;
	
	/** La descripción de la contraparte */
	private String contraparte;
	
	/** La descripci que indica si se afect al disponible o al no disponible */
	private String afectaA;
	
	/** La cantidad total entregada a la contraparte como pago de la parcialidad */
	private Double totalEntregado;
	
	/** La cantidad pendiente por entregar de la operación */
	private Double pendientePorEntregar;
	
	/** La cantidad total de la operación original */
	private Double totalOperacion;
	
	/** El estado de la operación */
	private String estado;
	
	/** El importe pendiente de la operación */
	private Double importePendiente;
	
	/** El importe pagado de la operación */
	private Double importePagado;
	
	/** El importe de la operación */
	private Double importeOperado;
	
	/** La descripción de la divisa */
	private String descripcionDivisa;
	
	/** La descripción de la bóveda de efectivo */
	private String descripcionBovedaEfectivo;
	
	/**
	 * Obtiene el valor del atributo fecha
	 * 
	 * @return el valor del atributo fecha
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * Obtiene el valor del atributo importePendiente
	 * 
	 * @return el valor del atributo importePendiente
	 */
	public Double getImportePendiente() {
		return importePendiente;
	}

	/**
	 * Establece el valor del atributo importePendiente
	 *
	 * @param importePendiente el valor del atributo importePendiente a establecer
	 */
	public void setImportePendiente(Double importePendiente) {
		this.importePendiente = importePendiente;
	}

	/**
	 * Obtiene el valor del atributo importePagado
	 * 
	 * @return el valor del atributo importePagado
	 */
	public Double getImportePagado() {
		return importePagado;
	}

	/**
	 * Establece el valor del atributo importePagado
	 *
	 * @param importePagado el valor del atributo importePagado a establecer
	 */
	public void setImportePagado(Double importePagado) {
		this.importePagado = importePagado;
	}

	/**
	 * Obtiene el valor del atributo descripcionDivisa
	 * 
	 * @return el valor del atributo descripcionDivisa
	 */
	public String getDescripcionDivisa() {
		return descripcionDivisa;
	}

	/**
	 * Establece el valor del atributo descripcionDivisa
	 *
	 * @param descripcionDivisa el valor del atributo descripcionDivisa a establecer
	 */
	public void setDescripcionDivisa(String descripcionDivisa) {
		this.descripcionDivisa = descripcionDivisa;
	}

	/**
	 * Obtiene el valor del atributo descripcionBovedaEfectivo
	 * 
	 * @return el valor del atributo descripcionBovedaEfectivo
	 */
	public String getDescripcionBovedaEfectivo() {
		return descripcionBovedaEfectivo;
	}

	/**
	 * Establece el valor del atributo descripcionBovedaEfectivo
	 *
	 * @param descripcionBovedaEfectivo el valor del atributo descripcionBovedaEfectivo a establecer
	 */
	public void setDescripcionBovedaEfectivo(String descripcionBovedaEfectivo) {
		this.descripcionBovedaEfectivo = descripcionBovedaEfectivo;
	}

	/**
	 * Establece el valor del atributo fecha
	 *
	 * @param fecha el valor del atributo fecha a establecer
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	/**
	 * Obtiene el valor del atributo folio
	 * 
	 * @return el valor del atributo folio
	 */
	public String getFolio() {
		return folio;
	}

	/**
	 * Establece el valor del atributo folio
	 *
	 * @param folio el valor del atributo folio a establecer
	 */
	public void setFolio(String folio) {
		this.folio = folio;
	}

	/**
	 * Obtiene el valor del atributo contraparte
	 * 
	 * @return el valor del atributo contraparte
	 */
	public String getContraparte() {
		return contraparte;
	}

	/**
	 * Establece el valor del atributo contraparte
	 *
	 * @param contraparte el valor del atributo contraparte a establecer
	 */
	public void setContraparte(String contraparte) {
		this.contraparte = contraparte;
	}

	/**
	 * Obtiene el valor del atributo afectaA
	 * 
	 * @return el valor del atributo afectaA
	 */
	public String getAfectaA() {
		return afectaA;
	}

	/**
	 * Establece el valor del atributo afectaA
	 *
	 * @param afectaA el valor del atributo afectaA a establecer
	 */
	public void setAfectaA(String afectaA) {
		this.afectaA = afectaA;
	}

	/**
	 * Obtiene el valor del atributo totalEntregado
	 * 
	 * @return el valor del atributo totalEntregado
	 */
	public Double getTotalEntregado() {
		return totalEntregado;
	}

	/**
	 * Establece el valor del atributo totalEntregado
	 *
	 * @param totalEntregado el valor del atributo totalEntregado a establecer
	 */
	public void setTotalEntregado(Double totalEntregado) {
		this.totalEntregado = totalEntregado;
	}

	/**
	 * Obtiene el valor del atributo pendientePorEntregar
	 * 
	 * @return el valor del atributo pendientePorEntregar
	 */
	public Double getPendientePorEntregar() {
		return pendientePorEntregar;
	}

	/**
	 * Establece el valor del atributo pendientePorEntregar
	 *
	 * @param pendientePorEntregar el valor del atributo pendientePorEntregar a establecer
	 */
	public void setPendientePorEntregar(Double pendientePorEntregar) {
		this.pendientePorEntregar = pendientePorEntregar;
	}

	/**
	 * Obtiene el valor del atributo totalOperacion
	 * 
	 * @return el valor del atributo totalOperacion
	 */
	public Double getTotalOperacion() {
		return totalOperacion;
	}

	/**
	 * Establece el valor del atributo totalOperacion
	 *
	 * @param totalOperacion el valor del atributo totalOperacion a establecer
	 */
	public void setTotalOperacion(Double totalOperacion) {
		this.totalOperacion = totalOperacion;
	}

	/**
	 * Obtiene el valor del atributo estado
	 * 
	 * @return el valor del atributo estado
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * Establece el valor del atributo estado
	 *
	 * @param estado el valor del atributo estado a establecer
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}

	/**
	 * Obtiene el valor del atributo importeOperado
	 * 
	 * @return el valor del atributo importeOperado
	 */
	public Double getImporteOperado() {
		return importeOperado;
	}

	/**
	 * Establece el valor del atributo importeOperado
	 *
	 * @param importeOperado el valor del atributo importeOperado a establecer
	 */
	public void setImporteOperado(Double importeOperado) {
		this.importeOperado = importeOperado;
	}
	
}
