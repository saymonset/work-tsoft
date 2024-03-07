/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_INSTRUCCION_EFECTIVO")
public class InstruccionEfectivoSimple implements Serializable {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_INSTRUCCION_EFECTIVO")
	private BigInteger id;

	@Column(name = "ID_INSTITUCION_TRASPASANTE")
	private Integer institucionTraspasante;

	@Column(name = "ID_INSTITUCION_RECEPTORA")
	private Integer institucionReceptora;

	@Column(name = "CASFIM_INSTITUCION_TRASPASANTE")
	private String casfimInstitucionTraspasante;

	@Column(name = "CASFIM_INSTITUCION_RECEPTORA")
	private String casfimInstitucionReceptora;

	@Column(name = "ID_CUENTA_TRASPASANTE")
	private Integer cuentaTraspasante;

	@Column(name = "ID_CUENTA_RECEPTORA")
	private Integer cuentaReceptora;

	@Column(name = "ID_DIVISA")
	private Integer divisa;

	@Column(name = "ID_BOVEDA")
	private Integer boveda;

	@Column(name = "ID_TIPO_INSTRUCCION")
	private Integer tipoInstruccion;
	
	@Column(name = "ID_TIPO_MENSAJE")
	private Integer tipoMensaje;

	@Column(name = "ID_FOLIO_ORIGEN")
	private String folioOrigen;

	@Column(name = "ID_ESTADO_INSTRUCCION")
	private Integer estadoInstruccion;

	@Column(name = "CONCEPTO")
	private String concepto;

	@Column(name = "CLAVE_RASTREO")
	private String claveRastreo;

	@Column(name = "MONTO")
	private BigDecimal monto;
	
	@Column(name = "CLABE")
	private String clabe;

	@Column(name = "REFERENCIA_OPERACION")
	private String referenciaOperacion;
	
	@Column(name = "REFERENCIA_PARTICIPANTE")
	private BigInteger referenciaParticipante;

	@Column(name = "REFERENCIA_NUMERICA")
	private String referenciaNumerica;	

	@Column(name = "FECHA_LIQUIDACION")
	private Date fechaLiquidacion;

	@Column(name = "FECHA_REGISTRO")
	private Date fechaRegistro;

	@Column(name = "FOLIO_INSTRUCCION")
	private BigInteger folioInstruccion;

	@Column(name = "FOLIO_INST_LIQUIDACION")
	private BigInteger folioInstLiquidacion;

	@Column(name = "DESCRIPCION_ESTADO")
	private String descripcionEstado;

	@Column(name = "ID_INSTITUCION_ORIGEN")
	private Integer institucionOrigen;

	@Column(name = "ID_TIPO_RETIRO")	
	private Integer tipoRetiro;

	@Column(name = "LIQ_SPEI")
	private Integer liqSpei;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public Integer getInstitucionTraspasante() {
		return institucionTraspasante;
	}

	public void setInstitucionTraspasante(Integer institucionTraspasante) {
		this.institucionTraspasante = institucionTraspasante;
	}

	public Integer getInstitucionReceptora() {
		return institucionReceptora;
	}

	public void setInstitucionReceptora(Integer institucionReceptora) {
		this.institucionReceptora = institucionReceptora;
	}

	public String getCasfimInstitucionTraspasante() {
		return casfimInstitucionTraspasante;
	}

	public void setCasfimInstitucionTraspasante(String casfimInstitucionTraspasante) {
		this.casfimInstitucionTraspasante = casfimInstitucionTraspasante;
	}

	public String getCasfimInstitucionReceptora() {
		return casfimInstitucionReceptora;
	}

	public void setCasfimInstitucionReceptora(String casfimInstitucionReceptora) {
		this.casfimInstitucionReceptora = casfimInstitucionReceptora;
	}

	public Integer getCuentaTraspasante() {
		return cuentaTraspasante;
	}

	public void setCuentaTraspasante(Integer cuentaTraspasante) {
		this.cuentaTraspasante = cuentaTraspasante;
	}

	public Integer getCuentaReceptora() {
		return cuentaReceptora;
	}

	public void setCuentaReceptora(Integer cuentaReceptora) {
		this.cuentaReceptora = cuentaReceptora;
	}

	public Integer getDivisa() {
		return divisa;
	}

	public void setDivisa(Integer divisa) {
		this.divisa = divisa;
	}

	public Integer getBoveda() {
		return boveda;
	}

	public void setBoveda(Integer boveda) {
		this.boveda = boveda;
	}

	public Integer getTipoInstruccion() {
		return tipoInstruccion;
	}

	public void setTipoInstruccion(Integer tipoInstruccion) {
		this.tipoInstruccion = tipoInstruccion;
	}

	public Integer getTipoMensaje() {
		return tipoMensaje;
	}

	public void setTipoMensaje(Integer tipoMensaje) {
		this.tipoMensaje = tipoMensaje;
	}

	public String getFolioOrigen() {
		return folioOrigen;
	}

	public void setFolioOrigen(String folioOrigen) {
		this.folioOrigen = folioOrigen;
	}

	public Integer getEstadoInstruccion() {
		return estadoInstruccion;
	}

	public void setEstadoInstruccion(Integer estadoInstruccion) {
		this.estadoInstruccion = estadoInstruccion;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public String getClaveRastreo() {
		return claveRastreo;
	}

	public void setClaveRastreo(String claveRastreo) {
		this.claveRastreo = claveRastreo;
	}

	public BigDecimal getMonto() {
		return monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	public String getClabe() {
		return clabe;
	}

	public void setClabe(String clabe) {
		this.clabe = clabe;
	}

	public String getReferenciaOperacion() {
		return referenciaOperacion;
	}

	public void setReferenciaOperacion(String referenciaOperacion) {
		this.referenciaOperacion = referenciaOperacion;
	}

	public BigInteger getReferenciaParticipante() {
		return referenciaParticipante;
	}

	public void setReferenciaParticipante(BigInteger referenciaParticipante) {
		this.referenciaParticipante = referenciaParticipante;
	}

	public String getReferenciaNumerica() {
		return referenciaNumerica;
	}

	public void setReferenciaNumerica(String referenciaNumerica) {
		this.referenciaNumerica = referenciaNumerica;
	}

	public Date getFechaLiquidacion() {
		return fechaLiquidacion;
	}

	public void setFechaLiquidacion(Date fechaLiquidacion) {
		this.fechaLiquidacion = fechaLiquidacion;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public BigInteger getFolioInstruccion() {
		return folioInstruccion;
	}

	public void setFolioInstruccion(BigInteger folioInstruccion) {
		this.folioInstruccion = folioInstruccion;
	}

	public BigInteger getFolioInstLiquidacion() {
		return folioInstLiquidacion;
	}

	public void setFolioInstLiquidacion(BigInteger folioInstLiquidacion) {
		this.folioInstLiquidacion = folioInstLiquidacion;
	}

	public String getDescripcionEstado() {
		return descripcionEstado;
	}

	public void setDescripcionEstado(String descripcionEstado) {
		this.descripcionEstado = descripcionEstado;
	}

	public Integer getInstitucionOrigen() {
		return institucionOrigen;
	}

	public void setInstitucionOrigen(Integer institucionOrigen) {
		this.institucionOrigen = institucionOrigen;
	}

	public Integer getTipoRetiro() {
		return tipoRetiro;
	}

	public void setTipoRetiro(Integer tipoRetiro) {
		this.tipoRetiro = tipoRetiro;
	}

	public Integer getLiqSpei() {
		return liqSpei;
	}

	public void setLiqSpei(Integer liqSpei) {
		this.liqSpei = liqSpei;
	}
	
}