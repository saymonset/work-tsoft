/*
 *Copyright (c) 2009 Bursatec. All Rights Reserved 
 */
package com.indeval.portaldali.middleware.dto.criterio;

import java.io.Serializable;
import java.util.Date;

import com.indeval.portaldali.middleware.dto.BovedaDTO;
import com.indeval.portaldali.middleware.dto.DivisaDTO;
import com.indeval.portaldali.middleware.dto.EstadosCuentaDTO;
import com.indeval.portaldali.middleware.dto.InstitucionDTO;

/**
 * DTO que representa a los elementos de la pantalla de captura consulta de cuentas
 * 
 * @author Maria C. Buendia
 * @version 1.0
 */
public class CriterioCuentaEfectivoDTO implements Serializable {
	/** Because is serializable */
	private static final long serialVersionUID = 345L;
	
	/**
	 * Auxiliar para el campo participante traspasante (El id y folio del
	 * participante).
	 */
	private String idFolioUsuario;
	
	/** Divisa */
	private DivisaDTO valorEn = new DivisaDTO();
	
	/** Boveda */
	private BovedaDTO boveda;
	
	/** La institucion para quien se crea la cuenta */
	private InstitucionDTO institucion = new InstitucionDTO();
	
	/** para nacional - id y folio del banco beneficiario*/
	private String idBancoBeneficiario;
	
	/** para nacional - La institucion asociada al banco beneficiario */
	private InstitucionDTO institucionBeneficiario = new InstitucionDTO();
	
	/** folio cuenta */
	private String folioCuenta;
	
	/** folio cuenta por traspasante*/
	private String folioCuentaPorTraspasante;
	
	/** nombre cuenta */
	private String nombreCuenta;
	
	/** banco beneficiario */
	private String bancoBeneficiario;
	
	/** Cuenta Beneficiario */
	private String cuentaBeneficiario;
	
	/** Nombre Beneficiario */
	private String nombreBeneficiario;
	
	/** fecha de Creacion. */
	private Date fechaCreacion = null;
	
	/** fecha de Autorizacion. */
	private Date fechaAutorizacion = null;

	/** fecha de Aprobacion. */
	private Date fechaAprobacion = null;

	/** fecha de Modificacion. */
	private Date fechaModificacion = null;

	/** fecha de Liberacion. */
	private Date fechaLiberacion = null;

	/** fecha de Cancelacion. */
	private Date fechaCancelacion = null;

	/** Estado */
	private String estadoCuenta;
	
	/** Estado Dto*/
	private EstadosCuentaDTO estadoCuentaDto;
	
	public String getIdFolioUsuario() {
		return idFolioUsuario;
	}

	public void setIdFolioUsuario(String idFolioUsuario) {
		this.idFolioUsuario = idFolioUsuario;
	}

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

	public String getEstadoCuenta() {
		return estadoCuenta;
	}

	public void setEstadoCuenta(String estadoCuenta) {
		this.estadoCuenta = estadoCuenta;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
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

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public Date getFechaLiberacion() {
		return fechaLiberacion;
	}

	public void setFechaLiberacion(Date fechaLiberacion) {
		this.fechaLiberacion = fechaLiberacion;
	}

	public Date getFechaCancelacion() {
		return fechaCancelacion;
	}

	public void setFechaCancelacion(Date fechaCancelacion) {
		this.fechaCancelacion = fechaCancelacion;
	}

	public String getFolioCuenta() {
		return folioCuenta;
	}

	public void setFolioCuenta(String folioCuenta) {
		this.folioCuenta = folioCuenta;
	}

	public String getNombreCuenta() {
		return nombreCuenta;
	}

	public void setNombreCuenta(String nombreCuenta) {
		this.nombreCuenta = nombreCuenta;
	}

	public String getBancoBeneficiario() {
		return bancoBeneficiario;
	}

	public void setBancoBeneficiario(String bancoBeneficiario) {
		this.bancoBeneficiario = bancoBeneficiario;
	}

	public EstadosCuentaDTO getEstadoCuentaDto() {
		return estadoCuentaDto;
	}

	public void setEstadoCuentaDto(EstadosCuentaDTO estadoCuentaDto) {
		this.estadoCuentaDto = estadoCuentaDto;
	}

	public String getFolioCuentaPorTraspasante() {
		return folioCuentaPorTraspasante;
	}

	public void setFolioCuentaPorTraspasante(String folioCuentaPorTraspasante) {
		this.folioCuentaPorTraspasante = folioCuentaPorTraspasante;
	}

}
