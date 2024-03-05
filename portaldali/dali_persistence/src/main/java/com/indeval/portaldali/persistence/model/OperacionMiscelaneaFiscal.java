/**
 * Sistema Portal DALI
 * 
 * Bursatec - 2H Software SA de CV
 *
 * Creado: Oct 24, 2008
 */
package com.indeval.portaldali.persistence.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Mapeo de Hibernate para la tabla T_MISCELANEA_FISCAL
 * 
 * @author José Antonio Huizar Moreno
 * @version 1.0
 * 
 */
@Entity
@Table(name = "T_MISCELANEA_FISCAL")
public class OperacionMiscelaneaFiscal {
	/**
	 * El identificador de la tabla T_LIQUIDACION_PARCIAL_MOV
	 */
	@Id
	@Column(name = "ID_MISCELANEA_FISCAL")
	private BigInteger idMiscelaneaFiscal;

	/**
	 * El identificador de la tabla T_LIQUIDACION_PARCIAL_MOV
	 */
	@Column(name = "ID_INSTRUCCION_OPERACION_VAL", insertable= false, updatable = false)
	private BigInteger idInstruccionOperacionVal;

	/**
	 * El curp ó rfc de la operación.
	 */
	@Column(name = "CURP_RFC")
	private String curpRfc;

	/**
	 * El cliente de la operación.
	 */
	@Column(name = "CLIENTE")
	private String cliente;

	/**
	 * El precio de la adquisición de la operación.
	 */
	@Column(name = "PRECIO_ADQUISICION")
	private BigDecimal precioAdquisicion;

	/**
	 * La fecha de adquisición de la operación.
	 */
	@Column(name = "FECHA_ADQUISICION")
	private Date fechaAdquisicion;

	/**
	 * La fecha de liquidación de la operación.
	 */
	@Column(name = "FECHA_LIQUIDACION")
	private Date fechaLiquidacion;

	/**
	 * Instrucción asociada a este detalle de miscelanea fiscal.
	 */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_INSTRUCCION_OPERACION_VAL")
	private InstruccionOperacionVal instruccionOperacionVal;
	
	/**
	 * El precio de la adquisición de la operación.
	 */
	@Column(name = "COSTO_PROM_ACTUALIZADO")
	private BigDecimal costoPromedio;

	/**
	 * La fecha de adquisición de la operación.
	 */
	@Transient //se elimino el campo en uso, pero se sigue conservando en la bd
	private Date fechaActualizacion;
	
	/**
	 * Obtiene el valor del atributo idMiscelaneaFiscal
	 * 
	 * @return el valor del atributo idMiscelaneaFiscal
	 */
	public BigInteger getIdMiscelaneaFiscal() {
		return idMiscelaneaFiscal;
	}

	/**
	 * Establece el valor del atributo idMiscelaneaFiscal
	 * 
	 * @param idMiscelaneaFiscal
	 *            el valor del atributo idMiscelaneaFiscal a establecer
	 */
	public void setIdMiscelaneaFiscal(BigInteger idMiscelaneaFiscal) {
		this.idMiscelaneaFiscal = idMiscelaneaFiscal;
	}

	/**
	 * Obtiene el valor del atributo instruccionOperacionVal
	 * 
	 * @return el valor del atributo instruccionOperacionVal
	 */
	public InstruccionOperacionVal getInstruccionOperacionVal() {
		return instruccionOperacionVal;
	}

	/**
	 * Establece el valor del atributo instruccionOperacionVal
	 * 
	 * @param instruccionOperacionVal
	 *            el valor del atributo instruccionOperacionVal a establecer
	 */
	public void setInstruccionOperacionVal(InstruccionOperacionVal instruccionOperacionVal) {
		this.instruccionOperacionVal = instruccionOperacionVal;
	}

	/**
	 * Obtiene el valor del atributo idInstruccionOperacionVal
	 *
	 * @return el valor del atributo idInstruccionOperacionVal
	 */
	public BigInteger getIdInstruccionOperacionVal() {
		return idInstruccionOperacionVal;
	}

	/**
	 * Establece el valor del atributo idInstruccionOperacionVal
	 *
	 * @param idInstruccionOperacionVal el valor del atributo idInstruccionOperacionVal a establecer
	 */
	public void setIdInstruccionOperacionVal(BigInteger idInstruccionOperacionVal) {
		this.idInstruccionOperacionVal = idInstruccionOperacionVal;
	}

	/**
	 * Obtiene el valor del atributo curpRfc
	 * 
	 * @return el valor del atributo curpRfc
	 */
	public String getCurpRfc() {
		return curpRfc;
	}

	/**
	 * Establece el valor del atributo curpRfc
	 * 
	 * @param curpRfc
	 *            el valor del atributo curpRfc a establecer
	 */
	public void setCurpRfc(String curpRfc) {
		this.curpRfc = curpRfc;
	}

	/**
	 * Obtiene el valor del atributo cliente
	 * 
	 * @return el valor del atributo cliente
	 */
	public String getCliente() {
		return cliente;
	}

	/**
	 * Establece el valor del atributo cliente
	 * 
	 * @param cliente
	 *            el valor del atributo cliente a establecer
	 */
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	/**
	 * Obtiene el valor del atributo precioAdquisicion
	 * 
	 * @return el valor del atributo precioAdquisicion
	 */
	public BigDecimal getPrecioAdquisicion() {
		return precioAdquisicion;
	}

	/**
	 * Establece el valor del atributo precioAdquisicion
	 * 
	 * @param precioAdquisicion
	 *            el valor del atributo precioAdquisicion a establecer
	 */
	public void setPrecioAdquisicion(BigDecimal precioAdquisicion) {
		this.precioAdquisicion = precioAdquisicion;
	}

	/**
	 * Obtiene el valor del atributo fechaAdquisicion
	 * 
	 * @return el valor del atributo fechaAdquisicion
	 */
	public Date getFechaAdquisicion() {
		return fechaAdquisicion;
	}

	/**
	 * Establece el valor del atributo fechaAdquisicion
	 * 
	 * @param fechaAdquisicion
	 *            el valor del atributo fechaAdquisicion a establecer
	 */
	public void setFechaAdquisicion(Date fechaAdquisicion) {
		this.fechaAdquisicion = fechaAdquisicion;
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
	 * @param fechaLiquidacion
	 *            el valor del atributo fechaLiquidacion a establecer
	 */
	public void setFechaLiquidacion(Date fechaLiquidacion) {
		this.fechaLiquidacion = fechaLiquidacion;
	}

	/**
	 * @return the costoPromedio
	 */
	public BigDecimal getCostoPromedio() {
		return costoPromedio;
	}

	/**
	 * @param costoPromedio the costoPromedio to set
	 */
	public void setCostoPromedio(BigDecimal costoPromedio) {
		this.costoPromedio = costoPromedio;
	}

	/**
	 * @return the fechaActualizacion
	 */
	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}

	/**
	 * @param fechaActualizacion the fechaActualizacion to set
	 */
	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

}
