package com.indeval.portaldali.modelo.to.commons;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public class BitacoraReenvioConfLiqReteDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Date fechaReenvio;
	private BigInteger idInstEfec;
	private Integer institucionTraspasante;
	private Integer institucionReceptora;
	private String casfimInstitucionTraspasante;
	private String casfimInstitucionReceptora;
	private Integer cuentaTraspasante;
	private Integer cuentaReceptora;
	private Integer divisa;
	private Integer boveda;
	private Integer tipoInstruccion;
	private Integer tipoMensaje;
	private String folioOrigen;
	private Integer estadoInstruccion;
	private String concepto;
	private String claveRastreo;
	private BigDecimal monto;
	private String clabe;
	private String referenciaOperacion;
	private BigInteger referenciaParticipante;
	private String referenciaNumerica;	
	private Date fechaLiquidacion;
	private Date fechaRegistro;
	private BigInteger folioInstruccion;
	private BigInteger folioInstLiquidacion;
	private String descripcionEstado;
	private Integer institucionOrigen;
	private Integer tipoRetiro;
	private Integer liqSpei;
	private String resultadoReenvio;
	private String descErrorReenvio;
	private String idUsuario;
	private String ip;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getFechaReenvio() {
		return fechaReenvio;
	}

	public void setFechaReenvio(Date fechaReenvio) {
		this.fechaReenvio = fechaReenvio;
	}

	public BigInteger getIdInstEfec() {
		return idInstEfec;
	}

	public void setIdInstEfec(BigInteger idInstEfec) {
		this.idInstEfec = idInstEfec;
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

	public String getResultadoReenvio() {
		return resultadoReenvio;
	}

	public void setResultadoReenvio(String resultadoReenvio) {
		this.resultadoReenvio = resultadoReenvio;
	}

	public String getDescErrorReenvio() {
		return descErrorReenvio;
	}

	public void setDescErrorReenvio(String descErrorReenvio) {
		this.descErrorReenvio = descErrorReenvio;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

}
