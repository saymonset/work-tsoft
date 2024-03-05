/*
 * Copyrigth (c) 2009 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.Map;

/**
 * DTO DEL RETIRO EFECTIVO INTERNACIONAL C_RETIRO_EFECTIVO_INT
 *@author fernando VAZQUEZ ULLOA 2009-11-09
 */
public class RetiroEfectivoInternacionalDTO implements Serializable {
		
	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** identificador de cuenta */
	private BigInteger idRetiroEfectivoInt;
	
	/** estado de la cuenta */
	private EstadoInstruccionCatDTO estado;
	
	/** divisa de la cuenta */
	private DivisaDTO divisa;
	
	/** institucion que crea la cuenta */
	private InstitucionDTO institucion;
	
	/** boveda correspondiente */
	private BovedaDTO boveda;

	/** cuenta retiro*/

	private Long IdCuentaBeneficiario;

	/*No se carga en el model, hay que cargarlo por separado si se quiere usar.*/
	private String cuentaBeneficiarioFinal;
		
	/** importe traspaso */
	private BigDecimal importeTraspaso;
	
	/** fecha valor */
	private Date fechaValor;	
	
	/** concepto de pago*/
	private String conceptoPago;
	
	/** fecha creacion */
	private Date fechaCreacion;
	
	/** fecha aprobacion */
	private Date fechaAutorizacion;
	
	/** fecha liberacion */
	private Date fechaLiberacion;

	/** fecha envio */
	private Date fechaEnvio;
	
	/** fecha liquidacion */
	private Date fechaLiquidacion;
	
	/** modulo origen */
	private String moduloOrigen;
	
	/** referencia operacion */
	private String referenciaOperacion;
	
	/** referencia operacion */
	private String referenciaMensaje;
	
	/** folio control */
	private String folioControl;
	
	/** el iso firmado */
	private Map<String, Object> datosFirmas;


	public EstadoInstruccionCatDTO getEstado() {
		return estado;
	}

	public void setEstado(EstadoInstruccionCatDTO estado) {
		this.estado = estado;
	}

	public DivisaDTO getDivisa() {
		return divisa;
	}

	public void setDivisa(DivisaDTO divisa) {
		this.divisa = divisa;
	}

	public InstitucionDTO getInstitucion() {
		return institucion;
	}

	public void setInstitucion(InstitucionDTO institucion) {
		this.institucion = institucion;
	}

	public BovedaDTO getBoveda() {
		return boveda;
	}

	public void setBoveda(BovedaDTO boveda) {
		this.boveda = boveda;
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

	public Date getFechaLiberacion() {
		return fechaLiberacion;
	}

	public void setFechaLiberacion(Date fechaLiberacion) {
		this.fechaLiberacion = fechaLiberacion;
	}

	public Date getFechaAutorizacion() {
		return fechaAutorizacion;
	}

	public void setFechaAutorizacion(Date fechaAutorizacion) {
		this.fechaAutorizacion = fechaAutorizacion;
	}

	public String getModuloOrigen() {
		return moduloOrigen;
	}

	public void setModuloOrigen(String moduloOrigen) {
		this.moduloOrigen = moduloOrigen;
	}

	public String getReferenciaOperacion() {
		return referenciaOperacion;
	}

	public void setReferenciaOperacion(String referenciaOperacion) {
		this.referenciaOperacion = referenciaOperacion;
	}

	public String getFolioControl() {
		return folioControl;
	}

	public void setFolioControl(String folioControl) {
		this.folioControl = folioControl;
	}


	public Date getFechaLiquidacion() {
		return fechaLiquidacion;
	}

	public void setFechaLiquidacion(Date fechaLiquidacion) {
		this.fechaLiquidacion = fechaLiquidacion;
	}

	public Date getFechaEnvio() {
		return fechaEnvio;
	}

	public void setFechaEnvio(Date fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}

	public String getReferenciaMensaje() {
		return referenciaMensaje;
	}

	public void setReferenciaMensaje(String referenciaMensaje) {
		this.referenciaMensaje = referenciaMensaje;
	}

	public Long getIdCuentaBeneficiario() {
		return IdCuentaBeneficiario;
	}

	public void setIdCuentaBeneficiario(Long idCuentaBeneficiario) {
		IdCuentaBeneficiario = idCuentaBeneficiario;
	}

	public BigInteger getIdRetiroEfectivoInt() {
		return idRetiroEfectivoInt;
	}

	public void setIdRetiroEfectivoInt(BigInteger idRetiroEfectivoInt) {
		this.idRetiroEfectivoInt = idRetiroEfectivoInt;
	}

	public String getCuentaBeneficiarioFinal() {
		return cuentaBeneficiarioFinal;
	}

	public void setCuentaBeneficiarioFinal(String cuentaBeneficiario) {
		this.cuentaBeneficiarioFinal = cuentaBeneficiario;
	}

	public Map<String, Object> getDatosFirmas() {
		return datosFirmas;
	}

	public void setDatosFirmas(Map<String, Object> datosFirmas) {
		this.datosFirmas = datosFirmas;
	}

}
