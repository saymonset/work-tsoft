/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * TODO cambiar el uso de Enumeration por la relacion real indicada en el modelo
 * de datos
 * 
 * Esta tabla contiene las instrucciones que sera&acute;n procesadas por el
 * Sistema de Liquidaci&oacute;n y que son recibidas a travs del bus de
 * servicios.
 * 
 * T_INSTRUCCION_LIQUIDACION
 * 
 * @author rchavez
 * @version 1.0
 */
@Entity
@Table(name = "T_INSTRUCCION_LIQUIDACION")
@SequenceGenerator(name = "foliador", sequenceName = "SEQ_T_INSTRUCCION_LIQUIDACION", allocationSize = 1, initialValue = 1)
public class InstruccionLiquidacion implements Serializable, Cloneable {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Identificador de la instrucci&oacute;n de liquidaci&oacute;n.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
	@Column(name = "ID_INSTRUCCION_LIQUIDACION")
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
	@JoinColumns( {
        @JoinColumn(name = "ID_TIPO_INSTRUCCION", nullable=true, insertable = false, updatable = false, referencedColumnName="ID_TIPO_INSTRUCCION"),
        @JoinColumn(name = "FOLIO_INSTRUCCION", nullable=true, insertable = false, updatable = false, referencedColumnName="FOLIO_INSTRUCCION")
	})
	private InstruccionEfectivo instruccionEfectivo;

	/**
	 * Operaciones asociadas a la instrucci&oacute;n.
	 */
	@OneToMany(mappedBy = "instruccion", cascade = { CascadeType.ALL })
	private Set<OperacionNombrada> operaciones;

	/**
	 * Identificador secuencial de la Instrucci&oacute;n de Liquidacion.
	 * 
	 * @return BigInteger
	 */
	public BigInteger getIdInstruccionLiquidacion() {
		return idInstruccionLiquidacion;
	}

	/**
	 * Identificador secuencial de la Instrucci&oacute;n de Liquidacion.
	 * 
	 * @param idInstruccionLiquidacion
	 */
	public void setIdInstruccionLiquidacion(BigInteger idInstruccionLiquidacion) {
		this.idInstruccionLiquidacion = idInstruccionLiquidacion;
	}

	/**
	 * Folio de negocio de la instrucci&oacute;n.
	 * 
	 * @return BigInteger
	 */
	public BigInteger getFolioInstruccion() {
		return folioInstruccion;
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
	 * Folio de negocio de la instrucci&oacute;n.
	 * 
	 * @param folioInstruccion
	 */
	public void setFolioInstruccion(BigInteger folioInstruccion) {
		this.folioInstruccion = folioInstruccion;
	}

	/**
	 * Folio de negocio de la instrucci&oacute;n.
	 * 
	 * @return BigInteger
	 */
	public BigInteger getFolioInstruccionLiquidacion() {
		return folioInstruccionLiquidacion;
	}

	/**
	 * Folio de Liquidacion de la instrucci&oacute;n.
	 * 
	 * @param folioLiquidacion
	 */
	public void setFolioInstruccionLiquidacion(BigInteger folioLiquidacion) {
		this.folioInstruccionLiquidacion = folioLiquidacion;
	}

	/**
	 * Referencia al Tipo de Instrucci&oacute;n.
	 * 
	 * @return TipoInstruccion
	 */
	public TipoInstruccion getTipoInstruccion() {
		return tipoInstruccion;
	}

	/**
	 * Referencia a la instrucci&oacute;n anterior.
	 * 
	 * @param tipoInstruccion
	 */
	public void setTipoInstruccion(TipoInstruccion tipoInstruccion) {
		this.tipoInstruccion = tipoInstruccion;
	}

	/**
	 * Referencia a la instrucci&oacute;n anterior.
	 * 
	 * @return InstruccionLiquidacion
	 */
	public BigInteger getIdInstruccionLiquidacionAnterior() {
		return idInstruccionLiquidacionAnterior;
	}

	/**
	 * Referencia a la instrucci&oacute;n anterior.
	 * 
	 * @param idInstruccionLiquidacionAnterior
	 */
	public void setIdInstruccionLiquidacionAnterior(BigInteger idInstruccionLiquidacionAnterior) {
		this.idInstruccionLiquidacionAnterior = idInstruccionLiquidacionAnterior;
	}

	/**
	 * Referencia al estado de la instrucci&oacute;n.
	 * 
	 * @return EstadoInstruccion
	 */
	public EstadoInstruccion getEstadoInstruccion() {
		return estadoInstruccion;
	}

	/**
	 * Referencia al estado de la instrucci&oacute;n.
	 * 
	 * @param estadoInstruccion
	 */
	public void setEstadoInstruccion(EstadoInstruccion estadoInstruccion) {
		this.estadoInstruccion = estadoInstruccion;
	}

	/**
	 * Sistema que origino la instrucci&oacute;n.
	 * 
	 * @return String
	 */
	public ModuloNegocio getModuloNegocio() {
		return moduloNegocio;
	}

	/**
	 * Sistema que origino la instrucci&oacute;n.
	 * 
	 * @param moduloNegocio
	 */
	public void setModuloNegocio(ModuloNegocio moduloNegocio) {
		this.moduloNegocio = moduloNegocio;
	}

	/**
	 * Identificador del sistema que origino la instrucci&oacute;n.
	 * 
	 * @return the idModuloNegocio
	 */
	public BigInteger getIdModuloNegocio() {
		return this.idModuloNegocio;
	}

	/**
	 * Identificador del sistema que origino la instrucci&oacute;n.
	 * 
	 * @param idModuloNegocio
	 *            the idModuloNegocio to set
	 */
	public void setIdModuloNegocio(BigInteger idModuloNegocio) {
		this.idModuloNegocio = idModuloNegocio;
	}

	/**
	 * Fecha en que se debe liquidar la operaci&oacute;n.
	 * 
	 * @return Date
	 */
	public Date getFechaLiquidacion() {
		return fechaLiquidacion;
	}

	/**
	 * Fecha en que se debe liquidar la operaci&oacute;n.
	 * 
	 * @param fechaLiquidacion
	 */
	public void setFechaLiquidacion(Date fechaLiquidacion) {
		this.fechaLiquidacion = fechaLiquidacion;
	}

	/**
	 * La fecha ma&acute;xima en la que se puede liquidar la operaci&oacute;n.
	 * 
	 * @return Date
	 */
	public Date getFechaLimiteLiquidacion() {
		return fechaLimiteLiquidacion;
	}

	/**
	 * La fecha ma&acute;xima en la que se puede liquidar la operaci&oacute;n.
	 * 
	 * @param fechaLimiteLiquidacion
	 */
	public void setFechaLimiteLiquidacion(Date fechaLimiteLiquidacion) {
		this.fechaLimiteLiquidacion = fechaLimiteLiquidacion;
	}

	/**
	 * La fecha en que se enva la operaci&oacute;n.
	 * 
	 * @return Date
	 */
	public Date getFechaConcertacion() {
		return fechaConcertacion;
	}

	/**
	 * La fecha en que se enva la operaci&oacute;n.
	 * 
	 * @param fechaConcertacion
	 */
	public void setFechaConcertacion(Date fechaConcertacion) {
		this.fechaConcertacion = fechaConcertacion;
	}

	/**
	 * Identificador del ciclo de liquidaci&oacute;n.
	 * 
	 * @return the idCiclo
	 */
	public BigInteger getIdCiclo() {
		return idCiclo;
	}

	/**
	 * Identificador del ciclo de liquidaci&oacute;n.
	 * 
	 * @param idCiclo
	 *            the idCiclo to set
	 */
	public void setIdCiclo(BigInteger idCiclo) {
		this.idCiclo = idCiclo;
	}

	/**
	 * Referencia a la instrucción origen. Este campo se utiliza para las
	 * instrucciones generadas por las amortizaciones. Se llenara con el campo
	 * de la instrucción que se liquido parcialmente.
	 * 
	 * @return BigInteger
	 */
	public BigInteger getIdInstruccionLiquidacionOrigen() {
		return idInstruccionLiquidacionOrigen;
	}

	/**
	 * Referencia a la instrucción origen. Este campo se utiliza para las
	 * instrucciones generadas por las amortizaciones. Se llenara con el campo
	 * de la instrucción que se liquido parcialmente.
	 * 
	 * @param idInstruccionLiquidacionOrigen
	 */
	public void setIdInstruccionLiquidacionOrigen(BigInteger idInstruccionLiquidacionOrigen) {
		this.idInstruccionLiquidacionOrigen = idInstruccionLiquidacionOrigen;
	}

	/**
	 * La fecha en que se liquida la instrucci&oacute;n.
	 * 
	 * @return Date
	 */
	public Date getFechaAplicacion() {
		return fechaAplicacion;
	}

	/**
	 * La fecha en que se liquida la instrucci&oacute;n.
	 * 
	 * @param fechaAplicacion
	 */
	public void setFechaAplicacion(Date fechaAplicacion) {
		this.fechaAplicacion = fechaAplicacion;
	}

	/**
	 * Las operaciones que forman esta instruccion.
	 * 
	 * @return Set
	 */
	public Set<OperacionNombrada> getOperaciones() {
		return operaciones;
	}

	/**
	 * Las operaciones que forman esta instruccion.
	 * 
	 * @param operaciones
	 */
	public void setOperaciones(Set<OperacionNombrada> operaciones) {
		this.operaciones = operaciones;
	}

	/**
	 * Identificador del tipo de instruccion
	 * 
	 * @return the idTipoInstruccion
	 */
	public BigInteger getIdTipoInstruccion() {
		return this.idTipoInstruccion;
	}

	/**
	 * Identificador del tipo de instruccion
	 * 
	 * @param idTipoInstruccion
	 *            the idTipoInstruccion to set
	 */
	public void setIdTipoInstruccion(BigInteger idTipoInstruccion) {
		this.idTipoInstruccion = idTipoInstruccion;
	}

	/**
	 * @return the bloqueada
	 */
	public boolean isBloqueada() {
		return this.bloqueada;
	}

	/**
	 * @param bloqueada
	 *            the bloqueada to set
	 */
	public void setBloqueada(boolean bloqueada) {
		this.bloqueada = bloqueada;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// return String.valueOf(idInstruccionLiquidacion);
		return super.toString();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(23, 33).append(idInstruccionLiquidacion).toHashCode();
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof InstruccionLiquidacion)) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		InstruccionLiquidacion rhs = (InstruccionLiquidacion) obj;
		return new EqualsBuilder().append(idInstruccionLiquidacion, rhs.getIdInstruccionLiquidacion()).isEquals();
	}

	/**
	 * @see java.lang.Object#clone()
	 */
	@Override
	public InstruccionLiquidacion clone() {
		InstruccionLiquidacion instruccion = null;
		try {
			instruccion = (InstruccionLiquidacion) super.clone();
		} catch (CloneNotSupportedException e) {
			// No deberia ocurrir dado que se dio permiso expl&iacute;cito a la
			// clase para
			// permitir que los objetos instanciados a partir de ella sean
			// clonados
			// implementando java.lang.Cloneable
		}
		return instruccion;
	}

	/**
	 * @return the bancoTrabajo
	 */
	public boolean isBancoTrabajo() {
		return bancoTrabajo;
	}

	/**
	 * @param bancoTrabajo
	 *            the bancoTrabajo to set
	 */
	public void setBancoTrabajo(boolean bancoTrabajo) {
		this.bancoTrabajo = bancoTrabajo;
	}

	/**
	 * @return
	 */
	public BigInteger getIdEstadoInstruccion() {
		return idEstadoInstruccion;
	}

	/**
	 * @param idEstadoInstruccion
	 */
	public void setIdEstadoInstruccion(BigInteger idEstadoInstruccion) {
		this.idEstadoInstruccion = idEstadoInstruccion;
	}

}