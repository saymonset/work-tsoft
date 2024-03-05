/**
 * Sistema Portal DALI
 * 
 * Bursatec - 2H Software SA de CV
 *
 * Creado: Nov 21, 2008
 */
package com.indeval.portaldali.persistence.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Mapeo de la tabla T_REG_CONTABLE_VAL_CONTROL_H
 * 
 * @author José Antonio Huizar Moreno
 * @version 1.0
 * 
 */
@Entity
@Table(name = "T_REG_CONTABLE_VAL_CONTROL_H")
public class RegContValControladaHistorico implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * Identificador de la acci&oacute;n.
	 */
	@Id
	@Column(name = "ID_REGISTRO_CONTABLE_H")
	private BigInteger idRegistroContable;

	/**
	 * Identificador de la posici&oacute;n afectada.
	 */
	@Column(name = "ID_POSICION", updatable = false)
	private BigInteger idPosicion;

	/**
	 * La posición controlada relacionada con este registro contable
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns ({
        @JoinColumn(name="ID_POSICION", referencedColumnName = "ID_POSICION_CONTROLADA",updatable = false, insertable = false),
        @JoinColumn(name="FECHA_CREACION", referencedColumnName = "FECHA_CREACION",updatable = false, insertable = false)
    })
	private PosicionControladaHistorico posicionControlada;

	/**
	 * Tipo de acci&oacutel;n aplicada a la posici&oacute;n.
	 */
	@Column(name = "ID_TIPO_ACCION", updatable = false)
	private TipoAccion tipoAccion;

	/**
	 * Cantidad aplicada a la posici&oacute;n.
	 */
	@Column(name = "CANTIDAD", updatable = false)
	private BigInteger cantidad;

	/**
	 * Fecha en que se afecto la posici&oacute;n.
	 */
	@Column(name = "FECHA", updatable = false)
	private Date fecha;

	/**
	 * Fecha en que se creó el registro en el histórico.
	 */
	@Column(name = "FECHA_CREACION", updatable = false)
	private Date fechaCreacion;

	/**
	 * Identificador del ciclo durante el cual se realizo la afectaci&oacute;n.
	 */
	@Column(name = "ID_CICLO", updatable = false)
	private BigInteger idCiclo;

	/**
	 * Identificador de la cuenta de la posici&oacute;n afectada.
	 */
	@Column(name = "ID_CUENTA", updatable = false)
	private BigInteger idCuenta;

	/**
	 * Identificador de la instituci&oacute;n.
	 */
	@Column(name = "ID_INSTITUCION", updatable = false)
	private BigInteger idInstitucion;

	/**
	 * Tipo de registro contable.
	 */
	@Enumerated
	@Column(name = "ID_TIPO_REGISTRO_CONTABLE", updatable = false)
	private TipoRegistroContableControlada tipoRegistroContableControlada;

	/**
	 * Obtiene el valor del atributo idRegistroContable
	 * 
	 * @return el valor del atributo idRegistroContable
	 */
	public BigInteger getIdRegistroContable() {
		return idRegistroContable;
	}

	/**
	 * Establece el valor del atributo idRegistroContable
	 * 
	 * @param idRegistroContable
	 *            el valor del atributo idRegistroContable a establecer
	 */
	public void setIdRegistroContable(BigInteger idRegistroContable) {
		this.idRegistroContable = idRegistroContable;
	}

	/**
	 * Obtiene el valor del atributo idPosicion
	 * 
	 * @return el valor del atributo idPosicion
	 */
	public BigInteger getIdPosicion() {
		return idPosicion;
	}

	/**
	 * Establece el valor del atributo idPosicion
	 * 
	 * @param idPosicion
	 *            el valor del atributo idPosicion a establecer
	 */
	public void setIdPosicion(BigInteger idPosicion) {
		this.idPosicion = idPosicion;
	}

	/**
	 * Obtiene el valor del atributo posicionControlada
	 * 
	 * @return el valor del atributo posicionControlada
	 */
	public PosicionControladaHistorico getPosicionControlada() {
		return posicionControlada;
	}

	/**
	 * Establece el valor del atributo posicionControlada
	 * 
	 * @param posicionControlada
	 *            el valor del atributo posicionControlada a establecer
	 */
	public void setPosicionControlada(PosicionControladaHistorico posicionControlada) {
		this.posicionControlada = posicionControlada;
	}

	/**
	 * Obtiene el valor del atributo tipoAccion
	 * 
	 * @return el valor del atributo tipoAccion
	 */
	public TipoAccion getTipoAccion() {
		return tipoAccion;
	}

	/**
	 * Establece el valor del atributo tipoAccion
	 * 
	 * @param tipoAccion
	 *            el valor del atributo tipoAccion a establecer
	 */
	public void setTipoAccion(TipoAccion tipoAccion) {
		this.tipoAccion = tipoAccion;
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
	 * Obtiene el valor del atributo fechaCreacion
	 * 
	 * @return el valor del atributo fechaCreacion
	 */
	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	/**
	 * Establece el valor del atributo fechaCreacion
	 * 
	 * @param fechaCreacion
	 *            el valor del atributo fechaCreacion a establecer
	 */
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	/**
	 * Obtiene el valor del atributo idCiclo
	 * 
	 * @return el valor del atributo idCiclo
	 */
	public BigInteger getIdCiclo() {
		return idCiclo;
	}

	/**
	 * Establece el valor del atributo idCiclo
	 * 
	 * @param idCiclo
	 *            el valor del atributo idCiclo a establecer
	 */
	public void setIdCiclo(BigInteger idCiclo) {
		this.idCiclo = idCiclo;
	}

	/**
	 * Obtiene el valor del atributo idCuenta
	 * 
	 * @return el valor del atributo idCuenta
	 */
	public BigInteger getIdCuenta() {
		return idCuenta;
	}

	/**
	 * Establece el valor del atributo idCuenta
	 * 
	 * @param idCuenta
	 *            el valor del atributo idCuenta a establecer
	 */
	public void setIdCuenta(BigInteger idCuenta) {
		this.idCuenta = idCuenta;
	}

	/**
	 * Obtiene el valor del atributo idInstitucion
	 * 
	 * @return el valor del atributo idInstitucion
	 */
	public BigInteger getIdInstitucion() {
		return idInstitucion;
	}

	/**
	 * Establece el valor del atributo idInstitucion
	 * 
	 * @param idInstitucion
	 *            el valor del atributo idInstitucion a establecer
	 */
	public void setIdInstitucion(BigInteger idInstitucion) {
		this.idInstitucion = idInstitucion;
	}

	/**
	 * Obtiene el valor del atributo tipoRegistroContableControlada
	 * 
	 * @return el valor del atributo tipoRegistroContableControlada
	 */
	public TipoRegistroContableControlada getTipoRegistroContableControlada() {
		return tipoRegistroContableControlada;
	}

	/**
	 * Establece el valor del atributo tipoRegistroContableControlada
	 * 
	 * @param tipoRegistroContableControlada
	 *            el valor del atributo tipoRegistroContableControlada a
	 *            establecer
	 */
	public void setTipoRegistroContableControlada(TipoRegistroContableControlada tipoRegistroContableControlada) {
		this.tipoRegistroContableControlada = tipoRegistroContableControlada;
	}

}
