/*
 * Copyrigth (c) 2009 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.dto;

import java.io.Serializable;
import java.sql.Clob;
import java.sql.Clob;
import java.util.Date;

/**
 * Data Transfer Object que representa una cuenta de retiro internacional
 *
 * @author FERNANDO VAZQUEZ ULLOA
 * @version 1.0
 */
public class CuentaRetiroInternacionalDTO implements Serializable {

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** cuenta de retiro general correspondiente */
	private long idCuentaRetiroInt;
	
	private long idCuentaRetiro;
	
	/** institucion que crea la cuenta */
	private InstitucionDTO institucion;
		
	/** estado de la cuenta */
	private EstadosCuentaDTO estado;
	
	/** divisa de la cuenta */
	private DivisaDTO divisa;

	/** nombre corto */
	private String nombreCorto;
	
	/** cuenta beneficiario final */
	private String cuentaBeneficiarioFinal;
	
	/** nombre beneficiario final */
	private String nombreBeneficiarioFinal;
	
	/** banco beneficiario */
	private String bancoBeneficiario;

	/** nombre banco beneficiario */
	private String nombreBancoBeneficiario;

	/** cuenta beneficiario */
	private String cuentaBeneficiario;

	/** cuenta intermediario */
	private String cuentaIntermediario;

	/** banco intermediario */
	private String bancoIntermediario;

	/** nombre intermediario */
	private String nombreIntermediario;

	/** detalles de pago */
	private String detallesPago;

	/** cuenta de retiro correspondiente a este detalle */
	private CuentaRetiroDTO cuentaRetiro;
	
	/** bitacora de estados*/
	private BitacoraEdosCuentaRetiroDTO bitacora;
	
	/** estado */
	private String estadoActual;
	
	/** blandera autorizar */
	private boolean autorizar;
	
	/** blandera autorizar */
	private boolean liberar;
	
	/** blandera autorizar */
	private boolean aprobar;
	
	/** blandera autorizar */
	private boolean cancelar;
	
	/** bandera modificada*/
	private boolean modificada;
	
	private Clob  creacionFirmada;

	
	/** modificacion firmada*/
	private Clob modificacionFirmada;
	
	/** fechas*/
	private Date fechaAutorizacion;
	private Date fechaAprobacion;
	private Date fechaLiberacion;
	private Date fechaModificacion;
	private Date fechaCreacion;
	private Date fechaCancelacion;


	public String toString(){
		return new StringBuffer("")
			.append("CuentaRetiroInternacionalDTO[")
			.append("bitacora:").append(bitacora)
			.append(",bitacora.getFechaCreacion():").append(bitacora.getFechaCreacion())
			.append(",cuentaRetiro:").append(cuentaRetiro)
			.append(",cuentaRetiro.getIdCuentaRetiro():").append(cuentaRetiro.getIdCuentaRetiro())
			.append(",cuentaRetiro.getDivisa():").append(cuentaRetiro.getDivisa())
			.append(",cuentaRetiro.getDivisa().getClaveAlfabetica()").append(cuentaRetiro.getDivisa().getClaveAlfabetica())
			.append("]")
			.toString(); 
	}

	public String getNombreCorto() {
		return nombreCorto;
	}

	public void setNombreCorto(String nombreCorto) {
		this.nombreCorto = nombreCorto;
	}

	public long getIdCuentaRetiroInt() {
		return idCuentaRetiroInt;
	}

	public void setIdCuentaRetiroInt(long idCuentaRetiro) {
		this.idCuentaRetiroInt = idCuentaRetiro;
	}

	

	
	public InstitucionDTO getInstitucion() {
		return institucion;
	}

	public void setInstitucion(InstitucionDTO institucion) {
		this.institucion = institucion;
	}

	public EstadosCuentaDTO getEstado() {
		return estado;
	}

	public void setEstado(EstadosCuentaDTO estado) {
		this.estado = estado;
	}

	public DivisaDTO getDivisa() {
		return divisa;
	}

	public void setDivisa(DivisaDTO divisa) {
		this.divisa = divisa;
	}

	public String getCuentaBeneficiarioFinal() {
		return cuentaBeneficiarioFinal;
	}

	public void setCuentaBeneficiarioFinal(String cuentaBeneficiarioFinal) {
		this.cuentaBeneficiarioFinal = cuentaBeneficiarioFinal;
	}

	public String getNombreBeneficiarioFinal() {
		return nombreBeneficiarioFinal;
	}

	public void setNombreBeneficiarioFinal(String nombreBeneficiarioFinal) {
		this.nombreBeneficiarioFinal = nombreBeneficiarioFinal;
	}

	public String getBancoBeneficiario() {
		return bancoBeneficiario;
	}

	public void setBancoBeneficiario(String bancoBeneficiario) {
		this.bancoBeneficiario = bancoBeneficiario;
	}

	public String getNombreBancoBeneficiario() {
		return nombreBancoBeneficiario;
	}

	public void setNombreBancoBeneficiario(String nombreBancoBeneficiario) {
		this.nombreBancoBeneficiario = nombreBancoBeneficiario;
	}

	public String getCuentaBeneficiario() {
		return cuentaBeneficiario;
	}

	public void setCuentaBeneficiario(String cuentaBeneficiario) {
		this.cuentaBeneficiario = cuentaBeneficiario;
	}

	public String getCuentaIntermediario() {
		return cuentaIntermediario;
	}

	public void setCuentaIntermediario(String cuentaIntermediario) {
		this.cuentaIntermediario = cuentaIntermediario;
	}

	public String getBancoIntermediario() {
		return bancoIntermediario;
	}

	public void setBancoIntermediario(String bancoIntermediario) {
		this.bancoIntermediario = bancoIntermediario;
	}

	public String getNombreIntermediario() {
		return nombreIntermediario;
	}

	public void setNombreIntermediario(String nombreIntermediario) {
		this.nombreIntermediario = nombreIntermediario;
	}

	public String getDetallesPago() {
		return detallesPago;
	}

	public void setDetallesPago(String detallesPago) {
		this.detallesPago = detallesPago;
	}

	public boolean isAutorizar() {
		return autorizar;
	}

	public void setAutorizar(boolean autorizar) {
		this.autorizar = autorizar;
	}

	public boolean isLiberar() {
		return liberar;
	}

	public void setLiberar(boolean liberar) {
		this.liberar = liberar;
	}

	public boolean isAprobar() {
		return aprobar;
	}

	public void setAprobar(boolean aprobar) {
		this.aprobar = aprobar;
	}

	public boolean isCancelar() {
		return cancelar;
	}

	public void setCancelar(boolean cancelar) {
		this.cancelar = cancelar;
	}

	public boolean isModificada() {
		return modificada;
	}

	public void setModificada(boolean modificada) {
		this.modificada = modificada;
	}

	public CuentaRetiroDTO getCuentaRetiro() {
		return cuentaRetiro;
	}

	public void setCuentaRetiro(CuentaRetiroDTO cuentaRetiro) {
		this.cuentaRetiro = cuentaRetiro;
	}

	public BitacoraEdosCuentaRetiroDTO getBitacora() {
		return bitacora;
	}

	public void setBitacora(BitacoraEdosCuentaRetiroDTO bitacora) {
		this.bitacora = bitacora;
	}

	public String getEstadoActual() {
		return estadoActual;
	}

	public void setEstadoActual(String estadoActual) {
		this.estadoActual = estadoActual;
	}

	public Clob getCreacionFirmada() {
		return creacionFirmada;
	}

	public void setCreacionFirmada(Clob creacionFirmada) {
		this.creacionFirmada = creacionFirmada;
	}


	public Clob getModificacionFirmada() {
		return modificacionFirmada;
	}

	public void setModificacionFirmada(Clob modificacionFirmada) {
		this.modificacionFirmada = modificacionFirmada;
	}

	public Date getFechaAutorizacion() {
		return fechaAutorizacion;
	}

	public void setFechaAutorizacion(Date fechaAutorizacion) {
		this.fechaAutorizacion = fechaAutorizacion;
	}

	public Date getFechaAprobacion() {
		return fechaAprobacion;
	}

	public void setFechaAprobacion(Date fechaAprobacion) {
		this.fechaAprobacion = fechaAprobacion;
	}

	public Date getFechaLiberacion() {
		return fechaLiberacion;
	}

	public void setFechaLiberacion(Date fechaLiberacion) {
		this.fechaLiberacion = fechaLiberacion;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaCancelacion() {
		return fechaCancelacion;
	}

	public void setFechaCancelacion(Date fechaCancelacion) {
		this.fechaCancelacion = fechaCancelacion;
	}

	public long getIdCuentaRetiro() {
		return idCuentaRetiro;
	}

	public void setIdCuentaRetiro(long idCuentaRetiro) {
		this.idCuentaRetiro = idCuentaRetiro;
	}

	
}
