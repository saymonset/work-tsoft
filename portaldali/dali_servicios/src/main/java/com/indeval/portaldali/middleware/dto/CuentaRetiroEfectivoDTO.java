/*
 * Copyrigth (c) 2009 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Data Transfer Object que representa una cuenta de retiro nacional
 * e internacional
 *
 * @author Maria C. Buendia
 * @version 1.0
 */
public class CuentaRetiroEfectivoDTO implements Serializable {

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** tipo: N-nacional, I-internacional*/
	private Character tipoCuenta;

	/** comun - cuenta de retiro general correspondiente */
	private long idCuentaRetiro;
	
	/** nacional - cuenta retiro nacional*/
	private long idCuentaRetiroNal;
	
	/** internacional - cuenta de retiro internacional */
	private long idCuentaRetiroInt;
	
	/** comun - id cuenta x institucion */
	private long idCuentaPorInstitucion;
	
	/** comun - institucion que crea la cuenta */
	private InstitucionDTO institucion;
		
	/** comun - estado de la cuenta */
	private EstadosCuentaDTO estado;
	
	/** comun - divisa de la cuenta */
	private DivisaDTO divisa;
	
	/** nacional - institucion beneficiaria */
	private InstitucionDTO institucionBeneficiario;
	
	/** nacional - boveda*/
	private BovedaDTO boveda;
	
	/** nacional - cuenta beneficiario */
	private String cuentaBeneficiario;
	
	/** nacional - nombre beneficiario */
	private String nombreBeneficiario;

	/** comun - cuenta de retiro correspondiente a este detalle */
	private CuentaRetiroDTO cuentaRetiro;
	
	/** comun - bitacora de estados*/
	private BitacoraEdosCuentaRetiroDTO bitacora;
	
	/** internacional - banco beneficiario */
	private String bancoBeneficiario;
	
	/** internacional - banco intermediario */
	private String bancoIntermediario;
	
	/** internacional - cuenta beneficiario final */
	private String cuentaBeneficiarioFinal;
	
	/** internacional - cuenta intermediario */
	private String cuentaIntermediario;
	
	/** internacional - detalles de pago */
	private String detallesPago;
	
	/** internacional - nombre banco beneficiario */
	private String nombreBancoBeneficiario;
	
	/** internacional - nombre beneficiario final */
	private String nombreBeneficiarioFinal;
	
	/** internacional - nombre corto */
	private String nombreCorto;
	
	/** internacional - nombre intermediario */
	private String nombreIntermediario;
	
	/** comun - estado */
	private String estadoActual;
	
	/** comun - bandera autorizar */
	private boolean autorizar;
	
	/** comun - bandera autorizar */
	private boolean liberar;
	
	/** internacional - bandera autorizar */
	private boolean aprobar;
	
	/** comun - bandera autorizar */
	private boolean cancelar;
	
	/** comun - bandera modificada*/
	private boolean modificada;
	
	/** comun - firmas*/
	Map<String, Object> datosFirmas = new HashMap<String, Object>(0);
	
	/** fechas*/
	private Date fechaAutorizacion;
	private Date fechaAprobacion; //solo internacional
	private Date fechaLiberacion;
	private Date fechaModificacion;
	private Date fechaCreacion;
	private Date fechaCancelacion;
	
	/** nacional - limites */
	private BigDecimal montoMaximoMensual;
	private BigDecimal montoMaximoDiario;
	private BigDecimal montoMaximoXTran;
	private Long numMaximoMovsXMes;
	
	/** comun - banderas para marcar las seleccionadas */
	private boolean seleccAutorizar;
	private boolean seleccLiberar;
	private boolean seleccAprobar;
	private boolean seleccCancelar;

	public String toString(){
		return new StringBuffer("")
			.append("CuentaRetiroEfectivoDTO[")
			.append("bitacora:").append(bitacora)
			.append(",bitacora.getFechaCreacion():").append(bitacora!=null?bitacora.getFechaCreacion():null)
			.append(",cuentaRetiro:").append(cuentaRetiro)
			.append(",cuentaRetiro.getIdCuentaRetiro():").append(cuentaRetiro!=null?cuentaRetiro.getIdCuentaRetiro():null)
			.append(",idCuentaRetiroNal:").append(idCuentaRetiroNal)
			.append(",idCuentaRetiroInt:").append(idCuentaRetiroInt)
			.append(",cuentaRetiro.getDivisa():").append(cuentaRetiro!=null?cuentaRetiro.getDivisa():null)
			.append(",cuentaRetiro.getDivisa().getClaveAlfabetica():").append(cuentaRetiro!=null?cuentaRetiro.getDivisa().getClaveAlfabetica():null)
			.append(",institucionBeneficiario:").append(institucionBeneficiario)
			.append(",boveda:").append(boveda)
			.append(",montoMaximoMensual:").append(montoMaximoMensual)
			.append(",montoMaximoDiario:").append(montoMaximoDiario)
			.append(",montoMaximoXTran:").append(montoMaximoXTran)
			.append(",numMaximoMovsXMes:").append(numMaximoMovsXMes)
			.append("]")
			.toString(); 
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

	public long getIdCuentaRetiroNal() {
		return idCuentaRetiroNal;
	}

	public void setIdCuentaRetiroNal(long idCuentaRetiroNal) {
		this.idCuentaRetiroNal = idCuentaRetiroNal;
	}

	public String getCuentaBeneficiario() {
		return cuentaBeneficiario;
	}

	public void setCuentaBeneficiario(String cuentaBeneficiario) {
		this.cuentaBeneficiario = cuentaBeneficiario;
	}

	public String getNombreBeneficiario() {
		return nombreBeneficiario;
	}

	public void setNombreBeneficiario(String nombreBeneficiario) {
		this.nombreBeneficiario = nombreBeneficiario;
	}

	public BigDecimal getMontoMaximoMensual() {
		return montoMaximoMensual;
	}

	public void setMontoMaximoMensual(BigDecimal montoMaximoMensual) {
		this.montoMaximoMensual = montoMaximoMensual;
	}

	public BigDecimal getMontoMaximoDiario() {
		return montoMaximoDiario;
	}

	public void setMontoMaximoDiario(BigDecimal montoMaximoDiario) {
		this.montoMaximoDiario = montoMaximoDiario;
	}

	public BigDecimal getMontoMaximoXTran() {
		return montoMaximoXTran;
	}

	public void setMontoMaximoXTran(BigDecimal montoMaximoXTran) {
		this.montoMaximoXTran = montoMaximoXTran;
	}

	public Long getNumMaximoMovsXMes() {
		return numMaximoMovsXMes;
	}

	public void setNumMaximoMovsXMes(Long numMaximoMovsXMes) {
		this.numMaximoMovsXMes = numMaximoMovsXMes;
	}

	public BovedaDTO getBoveda() {
		return boveda;
	}

	public void setBoveda(BovedaDTO boveda) {
		this.boveda = boveda;
	}

	public InstitucionDTO getInstitucionBeneficiario() {
		return institucionBeneficiario;
	}

	public void setInstitucionBeneficiario(InstitucionDTO institucionBeneficiario) {
		this.institucionBeneficiario = institucionBeneficiario;
	}

	public Character getTipoCuenta() {
		return tipoCuenta;
	}

	public void setTipoCuenta(Character tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	}

	public long getIdCuentaRetiroInt() {
		return idCuentaRetiroInt;
	}

	public void setIdCuentaRetiroInt(long idCuentaRetiroInt) {
		this.idCuentaRetiroInt = idCuentaRetiroInt;
	}

	public String getBancoBeneficiario() {
		return bancoBeneficiario;
	}

	public void setBancoBeneficiario(String bancoBeneficiario) {
		this.bancoBeneficiario = bancoBeneficiario;
	}

	public String getBancoIntermediario() {
		return bancoIntermediario;
	}

	public void setBancoIntermediario(String bancoIntermediario) {
		this.bancoIntermediario = bancoIntermediario;
	}

	public String getCuentaBeneficiarioFinal() {
		return cuentaBeneficiarioFinal;
	}

	public void setCuentaBeneficiarioFinal(String cuentaBeneficiarioFinal) {
		this.cuentaBeneficiarioFinal = cuentaBeneficiarioFinal;
	}

	public String getCuentaIntermediario() {
		return cuentaIntermediario;
	}

	public void setCuentaIntermediario(String cuentaIntermediario) {
		this.cuentaIntermediario = cuentaIntermediario;
	}

	public String getDetallesPago() {
		return detallesPago;
	}

	public void setDetallesPago(String detallesPago) {
		this.detallesPago = detallesPago;
	}

	public String getNombreBancoBeneficiario() {
		return nombreBancoBeneficiario;
	}

	public void setNombreBancoBeneficiario(String nombreBancoBeneficiario) {
		this.nombreBancoBeneficiario = nombreBancoBeneficiario;
	}

	public String getNombreBeneficiarioFinal() {
		return nombreBeneficiarioFinal;
	}

	public void setNombreBeneficiarioFinal(String nombreBeneficiarioFinal) {
		this.nombreBeneficiarioFinal = nombreBeneficiarioFinal;
	}

	public String getNombreCorto() {
		return nombreCorto;
	}

	public void setNombreCorto(String nombreCorto) {
		this.nombreCorto = nombreCorto;
	}

	public String getNombreIntermediario() {
		return nombreIntermediario;
	}

	public void setNombreIntermediario(String nombreIntermediario) {
		this.nombreIntermediario = nombreIntermediario;
	}

	public Map<String, Object> getDatosFirmas() {
		return datosFirmas;
	}

	public void setDatosFirmas(Map<String, Object> datosFirmas) {
		this.datosFirmas = datosFirmas;
	}

	public long getIdCuentaPorInstitucion() {
		return idCuentaPorInstitucion;
	}

	public void setIdCuentaPorInstitucion(long idCuentaPorInstitucion) {
		this.idCuentaPorInstitucion = idCuentaPorInstitucion;
	}

	public boolean isSeleccAutorizar() {
		return seleccAutorizar;
	}

	public void setSeleccAutorizar(boolean seleccAutorizar) {
		this.seleccAutorizar = seleccAutorizar;
	}

	public boolean isSeleccLiberar() {
		return seleccLiberar;
	}

	public void setSeleccLiberar(boolean seleccLiberar) {
		this.seleccLiberar = seleccLiberar;
	}

	public boolean isSeleccAprobar() {
		return seleccAprobar;
	}

	public void setSeleccAprobar(boolean seleccAprobar) {
		this.seleccAprobar = seleccAprobar;
	}

	public boolean isSeleccCancelar() {
		return seleccCancelar;
	}

	public void setSeleccCancelar(boolean seleccCancelar) {
		this.seleccCancelar = seleccCancelar;
	}

	
}
