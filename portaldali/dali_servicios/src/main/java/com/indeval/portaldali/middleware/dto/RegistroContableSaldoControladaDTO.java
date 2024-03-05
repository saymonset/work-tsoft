/**
 * 2H Software
 * 
 * Sistema de Consulta de Estado de Cuenta - Indeval
 *
 * Creado el Dec 31, 2007
 */
package com.indeval.portaldali.middleware.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Data Transfer Object que representa un registro contable que forma parte del
 * detalle de un estado de cuenta.
 *
 * @author José Antonio Huizar Moreno
 * @version 1.0
 *
 */
public class RegistroContableSaldoControladaDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** Identificador del registro contable que se registró en una operación de un saldo */
	private long idRegistroContable;

	/** Indica si el registro proviene del histórico */
	private boolean historico;
	
	/** Fecha de registro de la operación */
	private Date fechaLiquidacion;

	/** Tipo de acción: Cargo o Abono */
	private TipoAccionDTO tipoAccion;
	
	/** Tipo de operación */
	private TipoOperacionDTO tipoOperacion;	
	
	/** Descripción del registro contable */
	private String descripcion = null;
	
	/** Nombre y cuenta de la contraparte de la operación */
	private String contraparte;
	
	/** Cantidad de posiciones recibidas en la operación*/
	private BigDecimal cantidadRecepcion;
	
	/** Cantidad de posiciones entregadas en la operación*/
	private BigDecimal cantidadEntrega;
	
	/** Cantidad que se registró en la operación*/
	private BigDecimal cantidad;
	
	/** Valor de la suma del saldo disponible y no disponible de la operación */
	private BigDecimal saldoTotal;
	
	/** Saldo de efectivo correspondiente a la operación */
	private SaldoEfectivoDTO saldoEfectivo;
	
	/** Fecha inicial para la búsqueda de operaciones */
	private Date fechaInicial;
	
	/** Fecha final para la búsqueda de operaciones */
	private Date fechaFinal;

	/**
	 * Obtiene el valor del atributo idRegistroContable
	 * 
	 * @return el valor del atributo idRegistroContable
	 */
	public long getIdRegistroContable() {
		return idRegistroContable;
	}

	/**
	 * Establece el valor del atributo idRegistroContable
	 *
	 * @param idRegistroContable el valor del atributo idRegistroContable a establecer
	 */
	public void setIdRegistroContable(long idRegistroContable) {
		this.idRegistroContable = idRegistroContable;
	}

	/**
	 * Obtiene el valor del atributo fechaLiquidacion
	 * 
	 * @return el valor del atributo fechaLiquidacion
	 */
	public Date getFechaLiquidacion() {
		return fechaLiquidacion;
	}

	/**
	 * Establece el valor del atributo fechaLiquidacion
	 *
	 * @param fechaLiquidacion el valor del atributo fechaLiquidacion a establecer
	 */
	public void setFechaLiquidacion(Date fechaLiquidacion) {
		this.fechaLiquidacion = fechaLiquidacion;
	}

	/**
	 * Obtiene el valor del atributo tipoAccion
	 * 
	 * @return el valor del atributo tipoAccion
	 */
	public TipoAccionDTO getTipoAccion() {
		return tipoAccion;
	}

	/**
	 * Establece el valor del atributo tipoAccion
	 *
	 * @param tipoAccion el valor del atributo tipoAccion a establecer
	 */
	public void setTipoAccion(TipoAccionDTO tipoAccion) {
		this.tipoAccion = tipoAccion;
	}

	/**
	 * Obtiene el valor del atributo tipoOperacion
	 * 
	 * @return el valor del atributo tipoOperacion
	 */
	public TipoOperacionDTO getTipoOperacion() {
		return tipoOperacion;
	}

	/**
	 * Establece el valor del atributo tipoOperacion
	 *
	 * @param tipoOperacion el valor del atributo tipoOperacion a establecer
	 */
	public void setTipoOperacion(TipoOperacionDTO tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	/**
	 * Obtiene el valor del atributo descripcion
	 * 
	 * @return el valor del atributo descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Establece el valor del atributo descripcion
	 *
	 * @param descripcion el valor del atributo descripcion a establecer
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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
	 * Obtiene el valor del atributo saldoTotal
	 * 
	 * @return el valor del atributo saldoTotal
	 */
	public BigDecimal getSaldoTotal() {
		return saldoTotal;
	}

	/**
	 * Establece el valor del atributo saldoTotal
	 *
	 * @param saldoTotal el valor del atributo saldoTotal a establecer
	 */
	public void setSaldoTotal(BigDecimal saldoTotal) {
		this.saldoTotal = saldoTotal;
	}

	/**
	 * Obtiene el valor del atributo saldoEfectivo
	 * 
	 * @return el valor del atributo saldoEfectivo
	 */
	public SaldoEfectivoDTO getSaldoEfectivo() {
		return saldoEfectivo;
	}

	/**
	 * Establece el valor del atributo saldoEfectivo
	 *
	 * @param saldoEfectivo el valor del atributo saldoEfectivo a establecer
	 */
	public void setSaldoEfectivo(SaldoEfectivoDTO saldoEfectivo) {
		this.saldoEfectivo = saldoEfectivo;
	}

	/**
	 * Obtiene el valor del atributo fechaInicial
	 * 
	 * @return el valor del atributo fechaInicial
	 */
	public Date getFechaInicial() {
		return fechaInicial;
	}

	/**
	 * Establece el valor del atributo fechaInicial
	 *
	 * @param fechaInicial el valor del atributo fechaInicial a establecer
	 */
	public void setFechaInicial(Date fechaInicial) {
		this.fechaInicial = fechaInicial;
	}

	/**
	 * Obtiene el valor del atributo fechaFinal
	 * 
	 * @return el valor del atributo fechaFinal
	 */
	public Date getFechaFinal() {
		return fechaFinal;
	}

	/**
	 * Establece el valor del atributo fechaFinal
	 *
	 * @param fechaFinal el valor del atributo fechaFinal a establecer
	 */
	public void setFechaFinal(Date fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	/**
	 * @return the cantidadRecepcion
	 */
	public BigDecimal getCantidadRecepcion() {
		return cantidadRecepcion;
	}

	/**
	 * @param cantidadRecepcion the cantidadRecepcion to set
	 */
	public void setCantidadRecepcion(BigDecimal cantidadRecepcion) {
		this.cantidadRecepcion = cantidadRecepcion;
	}

	/**
	 * @return the cantidadEntrega
	 */
	public BigDecimal getCantidadEntrega() {
		return cantidadEntrega;
	}

	/**
	 * @param cantidadEntrega the cantidadEntrega to set
	 */
	public void setCantidadEntrega(BigDecimal cantidadEntrega) {
		this.cantidadEntrega = cantidadEntrega;
	}

	/**
	 * @return the cantidad
	 */
	public BigDecimal getCantidad() {
		return cantidad;
	}

	/**
	 * @param cantidad the cantidad to set
	 */
	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
	}

	/**
	 * @return the historico
	 */
	public boolean isHistorico() {
		return historico;
	}

	/**
	 * @param historico the historico to set
	 */
	public void setHistorico(boolean historico) {
		this.historico = historico;
	}
	
}
