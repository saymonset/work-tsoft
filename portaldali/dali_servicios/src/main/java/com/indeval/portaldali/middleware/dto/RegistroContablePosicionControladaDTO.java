/**
 * 2H Software
 * 
 * Sistema de Consulta de Estado de Cuenta - Indeval
 *
 * Creado el Dec 21, 2007
 */
package com.indeval.portaldali.middleware.dto;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

/**
 * Data Transfer Object que representa un registro contable de una posición
 * controlada.
 * 
 * @author José Antonio Huizar Moreno
 * @version 1.0
 * 
 */
public class RegistroContablePosicionControladaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Identificador del registro contable que se registró en una operación de
	 * una posición
	 */
	private long idRegistroContable;

	/** Posición correspondiente a la operación que se lleva a cabo */
	private PosicionDTO posicion;

	/** Fecha de registro de la operación */
	private Date fecha;

	/** Fecha inicial para la búsqueda de operaciones */
	private Date fechaInicial;

	/** Fecha final para la búsqueda de operaciones */
	private Date fechaFinal;

	/** Tipo de acción: Cargo o Abono */
	private TipoAccionDTO tipoAccion;

	/** Nombre y cuenta de la contraparte de la operación */
	private String contraparte;
	/**
	 * Datos del cupón asociado a la emisión y la fecha de liquidación
	 */
	private CuponDTO cupon = null;
	/**
	 * Identificador del ciclo durante el cual se realizo la afectaci&oacute;n.
	 */
	private Long idCiclo;

	/** Cantidad de posiciones que se registró en la operación */
	private BigInteger cantidad;

	/** Cantidad de posiciones recibidas en la operación */
	private BigInteger cantidadRecepcion;

	/** Indica si el registro proviene del histórico */
	private boolean historico;

	/** Cantidad de posiciones entregadas en la operación */
	private BigInteger cantidadEntrega;

	/** Valor de la posición disponible de la operación */
	private BigInteger posicionTotal;

	/** La descripción del registro contable */
	private String descripcion;

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
	 * @param idRegistroContable
	 *            el valor del atributo idRegistroContable a establecer
	 */
	public void setIdRegistroContable(long idRegistroContable) {
		this.idRegistroContable = idRegistroContable;
	}

	/**
	 * Obtiene el valor del atributo historico
	 * 
	 * @return el valor del atributo historico
	 */
	public boolean isHistorico() {
		return historico;
	}

	/**
	 * Establece el valor del atributo historico
	 * 
	 * @param historico
	 *            el valor del atributo historico a establecer
	 */
	public void setHistorico(boolean historico) {
		this.historico = historico;
	}

	/**
	 * Obtiene el valor del atributo posicion
	 * 
	 * @return el valor del atributo posicion
	 */
	public PosicionDTO getPosicion() {
		return posicion;
	}

	/**
	 * Establece el valor del atributo posicion
	 * 
	 * @param posicion
	 *            el valor del atributo posicion a establecer
	 */
	public void setPosicion(PosicionDTO posicion) {
		this.posicion = posicion;
	}

	/**
	 * Obtiene el valor del atributo fecha
	 * 
	 * @return el valor del atributo fecha
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * Establece el valor del atributo fecha
	 * 
	 * @param fecha
	 *            el valor del atributo fecha a establecer
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
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
	 * @param fechaInicial
	 *            el valor del atributo fechaInicial a establecer
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
	 * @param fechaFinal
	 *            el valor del atributo fechaFinal a establecer
	 */
	public void setFechaFinal(Date fechaFinal) {
		this.fechaFinal = fechaFinal;
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
	 * @param tipoAccion
	 *            el valor del atributo tipoAccion a establecer
	 */
	public void setTipoAccion(TipoAccionDTO tipoAccion) {
		this.tipoAccion = tipoAccion;
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
	 * @param contraparte
	 *            el valor del atributo contraparte a establecer
	 */
	public void setContraparte(String contraparte) {
		this.contraparte = contraparte;
	}

	/**
	 * Obtiene el valor del atributo idCiclo
	 * 
	 * @return el valor del atributo idCiclo
	 */
	public Long getIdCiclo() {
		return idCiclo;
	}

	/**
	 * Establece el valor del atributo idCiclo
	 * 
	 * @param idCiclo
	 *            el valor del atributo idCiclo a establecer
	 */
	public void setIdCiclo(Long idCiclo) {
		this.idCiclo = idCiclo;
	}

	/**
	 * Obtiene el valor del atributo cantidad
	 * 
	 * @return el valor del atributo cantidad
	 */
	public BigInteger getCantidad() {
		return cantidad;
	}

	/**
	 * Establece el valor del atributo cantidad
	 * 
	 * @param cantidad
	 *            el valor del atributo cantidad a establecer
	 */
	public void setCantidad(BigInteger cantidad) {
		this.cantidad = cantidad;
	}

	/**
	 * Obtiene el valor del atributo cantidadRecepcion
	 * 
	 * @return el valor del atributo cantidadRecepcion
	 */
	public BigInteger getCantidadRecepcion() {
		return cantidadRecepcion;
	}

	/**
	 * Establece el valor del atributo cantidadRecepcion
	 * 
	 * @param cantidadRecepcion
	 *            el valor del atributo cantidadRecepcion a establecer
	 */
	public void setCantidadRecepcion(BigInteger cantidadRecepcion) {
		this.cantidadRecepcion = cantidadRecepcion;
	}

	/**
	 * Obtiene el valor del atributo cantidadEntrega
	 * 
	 * @return el valor del atributo cantidadEntrega
	 */
	public BigInteger getCantidadEntrega() {
		return cantidadEntrega;
	}

	/**
	 * Establece el valor del atributo cantidadEntrega
	 * 
	 * @param cantidadEntrega
	 *            el valor del atributo cantidadEntrega a establecer
	 */
	public void setCantidadEntrega(BigInteger cantidadEntrega) {
		this.cantidadEntrega = cantidadEntrega;
	}

	/**
	 * Obtiene el valor del atributo posicionTotal
	 * 
	 * @return el valor del atributo posicionTotal
	 */
	public BigInteger getPosicionTotal() {
		return posicionTotal;
	}

	/**
	 * Establece el valor del atributo posicionTotal
	 * 
	 * @param posicionTotal
	 *            el valor del atributo posicionTotal a establecer
	 */
	public void setPosicionTotal(BigInteger posicionTotal) {
		this.posicionTotal = posicionTotal;
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
	 * @param descripcion
	 *            el valor del atributo descripcion a establecer
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Obtiene el campo cupon
	 * 
	 * @return cupon
	 */
	public CuponDTO getCupon() {
		return cupon;
	}

	/**
	 * Asigna el valor del campo cupon
	 * 
	 * @param cupon
	 *            el valor de cupon a asignar
	 */
	public void setCupon(CuponDTO cupon) {
		this.cupon = cupon;
	}

}
