/**
 * Sistema Portal DALI
 * 
 * Bursatec - 2H Software SA de CV
 *
 * Creado: Nov 24, 2008
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Mapeo de Hibernate para la tabla T_INSTRUCCION_LIQUIDACION_H
 * 
 * @author José Antonio Huizar Moreno
 * @version 1.0
 * 
 */
@Entity
@Table(name = "T_INSTRUCCION_LIQUIDACION_H")
public class InstruccionLiquidacionHistorico implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * Identificador de la instrucci&oacute;n de liquidaci&oacute;n.
	 */
	@Id
	@Column(name = "ID_INSTRUCCION_LIQUIDACION_H")
	private BigInteger idInstruccionLiquidacion;

	/**
	 * Folio de la instrucci&oacute;n.
	 */
	@Column(name = "FOLIO_INSTRUCCION")
	private BigInteger folioInstruccion;

	/**
	 * Folio de la instrucci&oacute;n de liquidaci&oacute;n.
	 */
	@Column(name = "FOLIO_INSTRUCCION_LIQUIDACION")
	private BigInteger folioInstruccionLiquidacion;

	/**
	 * Folio de la instrucci&oacute;n origen en caso de que la
	 * instrucci&oacute;n haya sido generada por alguna otra.
	 */
	@Column(name = "ID_INSTRUCCION_ORIGEN")
	private BigInteger idInstruccionLiquidacionOrigen;

	/**
	 * Tipo de la instrucci&oacute;n.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_TIPO_INSTRUCCION", updatable = false, insertable = false)
	private TipoInstruccion tipoInstruccion;

	/**
	 * Identificador del tipo de instrucci&oacute;n.
	 */
	@Column(name = "ID_TIPO_INSTRUCCION")
	private BigInteger idTipoInstruccion;

	/**
	 * Folio de la instrucci&oacute;n que debe ejecutarse previamente.
	 */
	@Column(name = "ID_INSTRUCCION_ANTERIOR")
	private BigInteger idInstruccionLiquidacionAnterior;

	/**
	 * Identificador del estado de la instrucci&oacute;n.
	 */
	@Enumerated
	@Column(name = "ID_ESTADO_INSTRUCCION", updatable = false, insertable = false)
	private EstadoInstruccion estadoInstruccion;

	/**
	 * Identificador del estado de la instrucci&oacute;n.
	 */
	@Column(name = "ID_ESTADO_INSTRUCCION")
	private BigInteger idEstadoInstruccion;

	/**
	 * Identificador del m&oacute;dulo que genero la instrucci&oacute;n.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_MODULO_ORIGEN", updatable = false, insertable = false)
	private ModuloNegocio moduloNegocio;

	@Column(name = "ID_MODULO_ORIGEN")
	private BigInteger idModuloNegocio;

	/**
	 * Fecha en que debe liquidarse la instrucci&oacute;n.
	 */
	@Column(name = "FECHA_LIQUIDACION")
	private Date fechaLiquidacion;

	/**
	 * Fecha l&iacute;mite en que debe liquidarse la instrucci&oacute;n.
	 */
	@Column(name = "FECHA_LIMITE_LIQUIDACION")
	private Date fechaLimiteLiquidacion;

	/**
	 * Fecha en que se registro la instrucci&oacute;n.
	 */
	@Column(name = "FECHA_CONCERTACION")
	private Date fechaConcertacion;

	/**
	 * Identificador del ciclo en que se manda liquidar la instrucci&oacute;n.
	 */
	@Column(name = "ID_CICLO")
	private BigInteger idCiclo;

	/**
	 * Fecha de aplicaci&oacute;n de la instrucci&oacute;n.
	 */
	@Column(name = "FECHA_APLICACION")
	private Date fechaAplicacion;

	/**
	 * Indica que la instruccion tiene asociado un bloqueo
	 */
	@Column(name = "BLOQUEADA")
	private boolean bloqueada;

	/**
	 * Indica que la instruccion es un banco de trabajo
	 */
	@Column(name = "BANCO_TRABAJO")
	private boolean bancoTrabajo;

	/** La instrucción de efectivo ligada a esta instrucción */
	@OneToOne(fetch = FetchType.LAZY, optional=true)
	@JoinColumn(name = "FOLIO_INSTRUCCION", nullable=true, insertable = false, updatable = false, referencedColumnName="FOLIO_INSTRUCCION")
	private InstruccionEfectivo instruccionEfectivo;

	/** Fecha de creción del registro */
	@Column(name="FECHA_CREACION")
	private Date fechaCreacion;

	/**
	 * Obtiene el valor del atributo idInstruccionLiquidacion
	 * 
	 * @return el valor del atributo idInstruccionLiquidacion
	 */
	public BigInteger getIdInstruccionLiquidacion() {
		return idInstruccionLiquidacion;
	}

	/**
	 * Establece el valor del atributo idInstruccionLiquidacion
	 * 
	 * @param idInstruccionLiquidacion
	 *            el valor del atributo idInstruccionLiquidacion a establecer
	 */
	public void setIdInstruccionLiquidacion(BigInteger idInstruccionLiquidacion) {
		this.idInstruccionLiquidacion = idInstruccionLiquidacion;
	}

	/**
	 * Obtiene el valor del atributo folioInstruccion
	 * 
	 * @return el valor del atributo folioInstruccion
	 */
	public BigInteger getFolioInstruccion() {
		return folioInstruccion;
	}

	/**
	 * Establece el valor del atributo folioInstruccion
	 * 
	 * @param folioInstruccion
	 *            el valor del atributo folioInstruccion a establecer
	 */
	public void setFolioInstruccion(BigInteger folioInstruccion) {
		this.folioInstruccion = folioInstruccion;
	}

	/**
	 * Obtiene el valor del atributo folioInstruccionLiquidacion
	 * 
	 * @return el valor del atributo folioInstruccionLiquidacion
	 */
	public BigInteger getFolioInstruccionLiquidacion() {
		return folioInstruccionLiquidacion;
	}

	/**
	 * Establece el valor del atributo folioInstruccionLiquidacion
	 * 
	 * @param folioInstruccionLiquidacion
	 *            el valor del atributo folioInstruccionLiquidacion a establecer
	 */
	public void setFolioInstruccionLiquidacion(BigInteger folioInstruccionLiquidacion) {
		this.folioInstruccionLiquidacion = folioInstruccionLiquidacion;
	}

	/**
	 * Obtiene el valor del atributo idInstruccionLiquidacionOrigen
	 * 
	 * @return el valor del atributo idInstruccionLiquidacionOrigen
	 */
	public BigInteger getIdInstruccionLiquidacionOrigen() {
		return idInstruccionLiquidacionOrigen;
	}

	/**
	 * Establece el valor del atributo idInstruccionLiquidacionOrigen
	 * 
	 * @param idInstruccionLiquidacionOrigen
	 *            el valor del atributo idInstruccionLiquidacionOrigen a
	 *            establecer
	 */
	public void setIdInstruccionLiquidacionOrigen(BigInteger idInstruccionLiquidacionOrigen) {
		this.idInstruccionLiquidacionOrigen = idInstruccionLiquidacionOrigen;
	}

	/**
	 * Obtiene el valor del atributo tipoInstruccion
	 * 
	 * @return el valor del atributo tipoInstruccion
	 */
	public TipoInstruccion getTipoInstruccion() {
		return tipoInstruccion;
	}

	/**
	 * Establece el valor del atributo tipoInstruccion
	 * 
	 * @param tipoInstruccion
	 *            el valor del atributo tipoInstruccion a establecer
	 */
	public void setTipoInstruccion(TipoInstruccion tipoInstruccion) {
		this.tipoInstruccion = tipoInstruccion;
	}

	/**
	 * Obtiene el valor del atributo idTipoInstruccion
	 * 
	 * @return el valor del atributo idTipoInstruccion
	 */
	public BigInteger getIdTipoInstruccion() {
		return idTipoInstruccion;
	}

	/**
	 * Establece el valor del atributo idTipoInstruccion
	 * 
	 * @param idTipoInstruccion
	 *            el valor del atributo idTipoInstruccion a establecer
	 */
	public void setIdTipoInstruccion(BigInteger idTipoInstruccion) {
		this.idTipoInstruccion = idTipoInstruccion;
	}

	/**
	 * Obtiene el valor del atributo idInstruccionLiquidacionAnterior
	 * 
	 * @return el valor del atributo idInstruccionLiquidacionAnterior
	 */
	public BigInteger getIdInstruccionLiquidacionAnterior() {
		return idInstruccionLiquidacionAnterior;
	}

	/**
	 * Establece el valor del atributo idInstruccionLiquidacionAnterior
	 * 
	 * @param idInstruccionLiquidacionAnterior
	 *            el valor del atributo idInstruccionLiquidacionAnterior a
	 *            establecer
	 */
	public void setIdInstruccionLiquidacionAnterior(BigInteger idInstruccionLiquidacionAnterior) {
		this.idInstruccionLiquidacionAnterior = idInstruccionLiquidacionAnterior;
	}

	/**
	 * Obtiene el valor del atributo estadoInstruccion
	 * 
	 * @return el valor del atributo estadoInstruccion
	 */
	public EstadoInstruccion getEstadoInstruccion() {
		return estadoInstruccion;
	}

	/**
	 * Establece el valor del atributo estadoInstruccion
	 * 
	 * @param estadoInstruccion
	 *            el valor del atributo estadoInstruccion a establecer
	 */
	public void setEstadoInstruccion(EstadoInstruccion estadoInstruccion) {
		this.estadoInstruccion = estadoInstruccion;
	}

	/**
	 * Obtiene el valor del atributo idEstadoInstruccion
	 * 
	 * @return el valor del atributo idEstadoInstruccion
	 */
	public BigInteger getIdEstadoInstruccion() {
		return idEstadoInstruccion;
	}

	/**
	 * Establece el valor del atributo idEstadoInstruccion
	 * 
	 * @param idEstadoInstruccion
	 *            el valor del atributo idEstadoInstruccion a establecer
	 */
	public void setIdEstadoInstruccion(BigInteger idEstadoInstruccion) {
		this.idEstadoInstruccion = idEstadoInstruccion;
	}

	/**
	 * Obtiene el valor del atributo moduloNegocio
	 * 
	 * @return el valor del atributo moduloNegocio
	 */
	public ModuloNegocio getModuloNegocio() {
		return moduloNegocio;
	}

	/**
	 * Establece el valor del atributo moduloNegocio
	 * 
	 * @param moduloNegocio
	 *            el valor del atributo moduloNegocio a establecer
	 */
	public void setModuloNegocio(ModuloNegocio moduloNegocio) {
		this.moduloNegocio = moduloNegocio;
	}

	/**
	 * Obtiene el valor del atributo idModuloNegocio
	 * 
	 * @return el valor del atributo idModuloNegocio
	 */
	public BigInteger getIdModuloNegocio() {
		return idModuloNegocio;
	}

	/**
	 * Establece el valor del atributo idModuloNegocio
	 * 
	 * @param idModuloNegocio
	 *            el valor del atributo idModuloNegocio a establecer
	 */
	public void setIdModuloNegocio(BigInteger idModuloNegocio) {
		this.idModuloNegocio = idModuloNegocio;
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
	 * Obtiene el valor del atributo fechaLimiteLiquidacion
	 * 
	 * @return el valor del atributo fechaLimiteLiquidacion
	 */
	public Date getFechaLimiteLiquidacion() {
		return fechaLimiteLiquidacion;
	}

	/**
	 * Establece el valor del atributo fechaLimiteLiquidacion
	 * 
	 * @param fechaLimiteLiquidacion
	 *            el valor del atributo fechaLimiteLiquidacion a establecer
	 */
	public void setFechaLimiteLiquidacion(Date fechaLimiteLiquidacion) {
		this.fechaLimiteLiquidacion = fechaLimiteLiquidacion;
	}

	/**
	 * Obtiene el valor del atributo fechaConcertacion
	 * 
	 * @return el valor del atributo fechaConcertacion
	 */
	public Date getFechaConcertacion() {
		return fechaConcertacion;
	}

	/**
	 * Establece el valor del atributo fechaConcertacion
	 * 
	 * @param fechaConcertacion
	 *            el valor del atributo fechaConcertacion a establecer
	 */
	public void setFechaConcertacion(Date fechaConcertacion) {
		this.fechaConcertacion = fechaConcertacion;
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
	 * Obtiene el valor del atributo fechaAplicacion
	 * 
	 * @return el valor del atributo fechaAplicacion
	 */
	public Date getFechaAplicacion() {
		return fechaAplicacion;
	}

	/**
	 * Establece el valor del atributo fechaAplicacion
	 * 
	 * @param fechaAplicacion
	 *            el valor del atributo fechaAplicacion a establecer
	 */
	public void setFechaAplicacion(Date fechaAplicacion) {
		this.fechaAplicacion = fechaAplicacion;
	}

	/**
	 * Obtiene el valor del atributo bloqueada
	 * 
	 * @return el valor del atributo bloqueada
	 */
	public boolean isBloqueada() {
		return bloqueada;
	}

	/**
	 * Establece el valor del atributo bloqueada
	 * 
	 * @param bloqueada
	 *            el valor del atributo bloqueada a establecer
	 */
	public void setBloqueada(boolean bloqueada) {
		this.bloqueada = bloqueada;
	}

	/**
	 * Obtiene el valor del atributo bancoTrabajo
	 * 
	 * @return el valor del atributo bancoTrabajo
	 */
	public boolean isBancoTrabajo() {
		return bancoTrabajo;
	}

	/**
	 * Establece el valor del atributo bancoTrabajo
	 * 
	 * @param bancoTrabajo
	 *            el valor del atributo bancoTrabajo a establecer
	 */
	public void setBancoTrabajo(boolean bancoTrabajo) {
		this.bancoTrabajo = bancoTrabajo;
	}

	/**
	 * Obtiene el valor del atributo instruccionEfectivo
	 * 
	 * @return el valor del atributo instruccionEfectivo
	 */
	public InstruccionEfectivo getInstruccionEfectivo() {
		return instruccionEfectivo;
	}

	/**
	 * Establece el valor del atributo instruccionEfectivo
	 * 
	 * @param instruccionEfectivo
	 *            el valor del atributo instruccionEfectivo a establecer
	 */
	public void setInstruccionEfectivo(InstruccionEfectivo instruccionEfectivo) {
		this.instruccionEfectivo = instruccionEfectivo;
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
	 * @param instruccionEfectivo el valor del atributo fechaCreacion a establecer
	 */
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

}
