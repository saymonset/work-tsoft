/*
 *Copyright (c) 2009 Bursatec. All Rights Reserved 
 */
package com.indeval.portaldali.presentation.dto.tesoreria;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Clob;

import com.indeval.portaldali.middleware.dto.BovedaDTO;
import com.indeval.portaldali.middleware.dto.DivisaDTO;
import com.indeval.portaldali.middleware.dto.InstitucionDTO;
//import com.indeval.portaldali.middleware.dto.SaldoEfectivoDTO;

/**
 * DTO que representa a los elementos de la pantalla de captura alta de cuentas
 * 
 * @author Maria C. Buendia
 * @version 1.0
 */
public class AltaCuentaDTO implements Serializable {
	/** Because is serializable */
	private static final long serialVersionUID = 123L;
	
	/**
	 * Auxiliar para el campo participante traspasante (El id y folio del
	 * participante).
	 */
	private String idFolioUsuario;
	
	/** DTO que representa a la posicion, como son la cuenta, emision y boveda del participante.*/
	//private SaldoEfectivoDTO saldoUsuario = new SaldoEfectivoDTO();
	
	/** Divisa */
	private DivisaDTO valorEn = new DivisaDTO();
	
	/** Boveda */
	private BovedaDTO boveda;
	
	/** Alias para la cuenta*/
	private String aliasCuenta;
	
	/** La institucion para quien se crea la cuenta */
	private InstitucionDTO institucion = new InstitucionDTO();
	
	/** id y folio del banco beneficiario*/
	private String idBancoBeneficiario;
	
	/** La institucion asociada al banco beneficiario */
	private InstitucionDTO institucionBeneficiario = new InstitucionDTO();
	
	/** Cuenta Beneficiario Final */
	private String cuentaBeneficiarioFinal;
	
	/** Nombre Beneficiario Final */
	private String nombreBeneficiarioFinal;
	
	/** Banco Beneficiario */
	private String bancoBeneficiario;
	
	/** Nombre Banco Beneficiario */
	private String nombreBancoBeneficiario;
	
	/** Cuenta Banco Beneficiario */
	private String cuentaBancoBeneficiario;
	
	/** Cuenta Beneficiario */
	private String cuentaBeneficiario;

	/** Banco Intermediario */
	private String bancoIntermediario;
	
	/** Nombre Banco Intermediario */
	private String nombreBancoIntermediario;
	
	/** Nombre Beneficiario */
	private String nombreBeneficiario;
	
	/** Cuenta Intermediario */
	private String cuentaIntermediario;
	
	/** Detalles pago*/
	private String detallesPago;
	
	/** Monto Maximo Mensual*/
	private String montoMaximoMensual;
	
	/** Monto Maximo Diario */
	private String montoMaximoDiario;
	
	/** Monto Maximo por Transaccion */
	private String montoMaximoXTran;
	
	/** Numero Maximo de Movimientos por Mes */
	private String numMaximoMovsXMes;
	
	/** identificador de cuenta (para modificaciones)*/
	private long id;
	
	/** identificador de cuenta x institucion (para modificaciones) que no vaya nulo*/
	private long idPorInstitucion;	
	
	private Clob creacionFirmada;
	
	/** cuenta retiro*/
	private BigInteger idCuentaRetiro;
	
	public String getIdFolioUsuario() {
		return idFolioUsuario;
	}

	public void setIdFolioUsuario(String idFolioUsuario) {
		this.idFolioUsuario = idFolioUsuario;
	}

	/*
	public SaldoEfectivoDTO getSaldoUsuario() {
		return saldoUsuario;
	}

	public void setSaldoUsuario(SaldoEfectivoDTO saldoUsuario) {
		this.saldoUsuario = saldoUsuario;
	}
	*/
	
	public DivisaDTO getValorEn() {
		return valorEn;
	}

	public void setValorEn(DivisaDTO valorEn) {
		this.valorEn = valorEn;
	}

	public BovedaDTO getBoveda() {
		return boveda;
	}

	public void setBoveda(BovedaDTO boveda) {
		this.boveda = boveda;
	}
	
	public String getAliasCuenta() {
		return aliasCuenta;
	}

	public void setAliasCuenta(String aliasCuenta) {
		this.aliasCuenta = aliasCuenta;
	}

	public String getIdBancoBeneficiario() {
		return idBancoBeneficiario;
	}

	public void setIdBancoBeneficiario(String idBancoBeneficiario) {
		this.idBancoBeneficiario = idBancoBeneficiario;
	}

	public InstitucionDTO getInstitucion() {
		return institucion;
	}

	public void setInstitucion(InstitucionDTO institucion) {
		this.institucion = institucion;
	}
	
	public InstitucionDTO getInstitucionBeneficiario() {
		return institucionBeneficiario;
	}

	public void setInstitucionBeneficiario(InstitucionDTO institucionBeneficiario) {
		this.institucionBeneficiario = institucionBeneficiario;
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

	public String getNombreBancoIntermediario() {
		return nombreBancoIntermediario;
	}

	public void setNombreBancoIntermediario(String nombreBancoIntermediario) {
		this.nombreBancoIntermediario = nombreBancoIntermediario;
	}

	public String getDetallesPago() {
		return detallesPago;
	}

	public void setDetallesPago(String detallesPago) {
		this.detallesPago = detallesPago;
	}

	public String getMontoMaximoMensual() {
		return montoMaximoMensual;
	}

	public void setMontoMaximoMensual(String montoMaximoMensual) {
		this.montoMaximoMensual = montoMaximoMensual;
	}

	public String getMontoMaximoDiario() {
		return montoMaximoDiario;
	}

	public void setMontoMaximoDiario(String montoMaximoDiario) {
		this.montoMaximoDiario = montoMaximoDiario;
	}

	public String getMontoMaximoXTran() {
		return montoMaximoXTran;
	}

	public void setMontoMaximoXTran(String montoMaximoXTran) {
		this.montoMaximoXTran = montoMaximoXTran;
	}

	public String getNumMaximoMovsXMes() {
		return numMaximoMovsXMes;
	}

	public void setNumMaximoMovsXMes(String numMaximoMovsXMes) {
		this.numMaximoMovsXMes = numMaximoMovsXMes;
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

	public String getCuentaBancoBeneficiario() {
		return cuentaBancoBeneficiario;
	}

	public void setCuentaBancoBeneficiario(String cuentaBancoBeneficiario) {
		this.cuentaBancoBeneficiario = cuentaBancoBeneficiario;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BigInteger getIdCuentaRetiro() {
		return idCuentaRetiro;
	}

	public void setIdCuentaRetiro(BigInteger idCuentaRetiro) {
		this.idCuentaRetiro = idCuentaRetiro;
	}

	public Clob getCreacionFirmada() {
		return creacionFirmada;
	}

	public void setCreacionFirmada(Clob creacionFirmada) {
		this.creacionFirmada = creacionFirmada;
	}

	public long getIdPorInstitucion() {
		return idPorInstitucion;
	}

	public void setIdPorInstitucion(long idPorInstitucion) {
		this.idPorInstitucion = idPorInstitucion;
	}

	
	
	
}
