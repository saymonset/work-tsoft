/*
 * Copyrigth (c) 2009 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.mercadodinero;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Clob;
import java.util.Date;

import org.springframework.validation.Errors;

import com.indeval.portaldali.middleware.dto.CuentaRetiroInternacionalDTO;
import com.indeval.portaldali.middleware.dto.InstitucionDTO;
import com.indeval.portaldali.middleware.services.AbstractBaseDTO;




/**
 * vo para RetiroEfectivoInternacional 
 * 
 * 
 *
 * @author FERNANDO VAZQUEZ ULLOA
 * FECHA: 2009-11-09
 * @version 1.0
 */



public class RetiroEfectivoInternacionalVO extends AbstractBaseDTO {

	/** Constante de serializacion */
	private static final long serialVersionUID = 1L;
	
	/** identificador de cuenta */
	
	
	
	private BigInteger idCuentaRetiroInt;
	
	/** estado de la cuenta */
	
	
	private BigInteger estado;
	
	/** divisa de la cuenta */
	
	
	private BigInteger divisa;
	
	/** institucion que crea la cuenta */
	
	private String referenciaMensaje;
	
	
	private InstitucionDTO institucion;
	
	/** boveda correspondiente */
	
	
	private BigInteger boveda;
	
	/** estado de la cuenta */
	
	
		
	/** importe traspaso */
	
	private BigDecimal importeTraspaso;
	
	private CuentaRetiroInternacionalDTO cuentaBeneficiario;
	
	/** fecha valor */
	
	private Date fechaValor;	
	
	/** concepto de pago*/
	
	private String conceptoPago;
	
	/** fecha creacion */
	
	private Date fechaCreacion;
	
	/** firma de la creacion */
	
	private Clob creacionFirmada;
	
	/** fecha aprobacion */
	
	private Date fechaAutorizacion;
	
	/** firma de la aprobacion */
	
	private Clob autorizacionFirmada;	

	/** fecha liberacion */
	
	private Date fechaLiberacion;
	
	/** firma de la liberacion */
	
	private Clob liberacionFirmada;

	public BigInteger getIdCuentaRetiroInt() {
		return idCuentaRetiroInt;
	}

	public void setIdCuentaRetiroInt(BigInteger idCuentaRetiroInt) {
		this.idCuentaRetiroInt = idCuentaRetiroInt;
	}

	public BigInteger getEstado() {
		return estado;
	}

	public void setEstado(BigInteger estado) {
		this.estado = estado;
	}

	public BigInteger getDivisa() {
		return divisa;
	}

	public void setDivisa(BigInteger divisa) {
		this.divisa = divisa;
	}



	public BigInteger getBoveda() {
		return boveda;
	}

	public void setBoveda(BigInteger boveda) {
		this.boveda = boveda;
	}



	public CuentaRetiroInternacionalDTO getCuentaBeneficiario() {
		return cuentaBeneficiario;
	}

	public void setCuentaBeneficiario(CuentaRetiroInternacionalDTO cuentaBeneficiario) {
		this.cuentaBeneficiario = cuentaBeneficiario;
	}

	public BigDecimal getImporteTraspaso() {
		return importeTraspaso;
	}

	public void setImporteTraspaso(BigDecimal importeTraspaso) {
		this.importeTraspaso = importeTraspaso;
	}

	public Date getFechaValor() {
		return fechaValor;
	}

	public void setFechaValor(Date fechaValor) {
		this.fechaValor = fechaValor;
	}

	public String getConceptoPago() {
		return conceptoPago;
	}

	public void setConceptoPago(String conceptoPago) {
		this.conceptoPago = conceptoPago;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Clob getCreacionFirmada() {
		return creacionFirmada;
	}

	public void setCreacionFirmada(Clob creacionFirmada) {
		this.creacionFirmada = creacionFirmada;
	}

	public Date getFechaLiberacion() {
		return fechaLiberacion;
	}

	public void setFechaLiberacion(Date fechaLiberacion) {
		this.fechaLiberacion = fechaLiberacion;
	}

	public Clob getLiberacionFirmada() {
		return liberacionFirmada;
	}

	public void setLiberacionFirmada(Clob liberacionFirmada) {
		this.liberacionFirmada = liberacionFirmada;
	}

	public Date getFechaAutorizacion() {
		return fechaAutorizacion;
	}

	public void setFechaAutorizacion(Date fechaAutorizacion) {
		this.fechaAutorizacion = fechaAutorizacion;
	}

	public Clob getAutorizacionFirmada() {
		return autorizacionFirmada;
	}

	public void setAutorizacionFirmada(Clob autorizacionFirmada) {
		this.autorizacionFirmada = autorizacionFirmada;
	}

	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		
	}

	public String getReferenciaMensaje() {
		return referenciaMensaje;
	}

	public void setReferenciaMensaje(String referenciaMensaje) {
		this.referenciaMensaje = referenciaMensaje;
	}

	public InstitucionDTO getInstitucion() {
		return institucion;
	}

	public void setInstitucion(InstitucionDTO institucion) {
		this.institucion = institucion;
	}

	

}
