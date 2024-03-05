/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * 
 * 27/02/2008
 */
package com.indeval.portaldali.presentation.dto.mercadodinero;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import javax.faces.model.SelectItem;

import com.indeval.portaldali.middleware.dto.CuentaRetiroEfectivoDTO;
import com.indeval.portaldali.middleware.dto.CuentaRetiroInternacionalDTO;
import com.indeval.portaldali.middleware.dto.DivisaDTO;
import com.indeval.portaldali.middleware.dto.InstitucionDTO;
import com.indeval.portaldali.middleware.dto.SaldoEfectivoDTO;

/**
 * DTO que representa a los elementos de la pantalla de captura de operaciones
 * para el tipo de operacion retiro de fondos.
 * 
 * @author Juan Carlos Huizar Moreno
 * @version 1.0
 * 
 */
public class RetiroFondosDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * EL DTO que representa a la posicion, como son la cuenta, emision y boveda
	 * del participante.
	 */
	private SaldoEfectivoDTO saldoUsuario = new SaldoEfectivoDTO();

	/** El tipo de traspaso de efectivo entre cuentas controladas. */
	private Integer tipoRetiro;

	/**
	 * Auxiliar para el campo participante traspasante (El id y folio del
	 * participante).
	 */
	private String idFolioUsuario;

	/**
	 * La cuenta destino del traspaso
	 */
	private BigDecimal importeRetiro =BigDecimal.ZERO;

	/**
	 * La cuenta destino del traspaso
	 */
	private Integer folioOperacion = 0;

	/** El saldo actual */
	private Double saldoActual = 0.0;

	/** Divisa */
	private DivisaDTO valorEn = new DivisaDTO();
	
	/** cuenta beneficiaria seleccionada */
	private String cuentaBeneficiaria = null;
	
	/** Cuenta beneficiario */
	private String cuentaRetiroInt;
	
	/** Lista de cuentas beneficiario a seleccionar */
	private List<SelectItem> cuentasBeneficiario = new ArrayList<SelectItem>(0);

	/** Concepto de pago*/
	private String conceptoPago;
	
	/** fecha valor*/
	private Date fechaValor;
	
	/** boveda */
	private String boveda;
	
	/** Auxiliar para el campo receptor (El id y folio del receptor). */
	private String idFolioReceptor;
	
	/** institucion receptor*/
	private InstitucionDTO institucionReceptor;
	
	/** Auxiliar para el campo nombre beneficiario. */
	private String nombreBeneficiario;
	
	/** cuenta beneficiario*/
	private CuentaRetiroEfectivoDTO cuentaBeneficiario;
	
	/** cuenta beneficiario*/
	private CuentaRetiroInternacionalDTO cuentaRetiroInternacional;
	
	/** referencia numerica*/
	private String referenciaNumerica;
	
	/** referencia operacion*/
	private String referenciaOperacion;
	
	/** referencia operacion*/
	private String referenciaMensaje;
	
	/** folio de la cuenta*/
	private String folioCuentaPorTraspasante;

	/**
	 * @return the tipoRetiro
	 */
	public Integer getTipoRetiro() {
		return tipoRetiro;
	}

	/**
	 * @param tipoRetiro
	 *            the tipoRetiro to set
	 */
	public void setTipoRetiro(Integer tipoRetiro) {
		this.tipoRetiro = tipoRetiro;
	}


	public BigDecimal getImporteRetiro() {
		return importeRetiro;
	}

	public void setImporteRetiro(BigDecimal importeRetiro) {
		this.importeRetiro = importeRetiro;
	}

	/**
	 * @return the folioOperacion
	 */
	public Integer getFolioOperacion() {
		return folioOperacion;
	}

	/**
	 * @param folioOperacion
	 *            the folioOperacion to set
	 */
	public void setFolioOperacion(Integer folioOperacion) {
		this.folioOperacion = folioOperacion;
	}

	/**
	 * @return the saldoActual
	 */
	public Double getSaldoActual() {
		return saldoActual;
	}

	/**
	 * @param saldoActual
	 *            the saldoActual to set
	 */
	public void setSaldoActual(Double saldoActual) {
		this.saldoActual = saldoActual;
	}

	/**
	 * @return the saldoUsuario
	 */
	public SaldoEfectivoDTO getSaldoUsuario() {
		return saldoUsuario;
	}

	/**
	 * @param saldoUsuario
	 *            the saldoUsuario to set
	 */
	public void setSaldoUsuario(SaldoEfectivoDTO saldoUsuario) {
		this.saldoUsuario = saldoUsuario;
	}

	/**
	 * @return the idFolioUsuario
	 */
	public String getIdFolioUsuario() {
		return idFolioUsuario;
	}

	/**
	 * @param idFolioUsuario
	 *            the idFolioUsuario to set
	 */
	public void setIdFolioUsuario(String idFolioUsuario) {
		this.idFolioUsuario = idFolioUsuario;
	}

	/**
	 * @return the valorEn
	 */
	public DivisaDTO getValorEn() {
		return valorEn;
	}

	/**
	 * @param valorEn
	 *            the valorEn to set
	 */
	public void setValorEn(DivisaDTO valorEn) {
		this.valorEn = valorEn;
	}

	/**
	 * @return the cuentaBeneficiaria
	 */
	public String getCuentaBeneficiaria() {
		return cuentaBeneficiaria;
	}

	/**
	 * @param cuentaBeneficiaria the cuentaBeneficiaria to set
	 */
	public void setCuentaBeneficiaria(String cuentaBeneficiaria) {
		this.cuentaBeneficiaria = cuentaBeneficiaria;
	}

	public String getCuentaRetiroInt() {
		return cuentaRetiroInt;
	}

	public void setCuentaRetiroInt(String cuentaRetiroInt) {
		this.cuentaRetiroInt = cuentaRetiroInt;
	}

	public List<SelectItem> getCuentasBeneficiario() {
		return cuentasBeneficiario;
	}

	public void setCuentasBeneficiario(List<SelectItem> cuentasBeneficiario) {
		this.cuentasBeneficiario = cuentasBeneficiario;
	}

	public String getConceptoPago() {
		return conceptoPago;
	}

	public void setConceptoPago(String conceptoPago) {
		this.conceptoPago = conceptoPago;
	}

	public Date getFechaValor() {
		return fechaValor;
	}

	public void setFechaValor(Date fechaValor) {
		this.fechaValor = fechaValor;
	}

	public String getBoveda() {
		return boveda;
	}

	public void setBoveda(String boveda) {
		this.boveda = boveda;
	}

	public String getIdFolioReceptor() {
		return idFolioReceptor;
	}

	public void setIdFolioReceptor(String idFolioReceptor) {
		this.idFolioReceptor = idFolioReceptor;
	}

	public InstitucionDTO getInstitucionReceptor() {
		return institucionReceptor;
	}

	public void setInstitucionReceptor(InstitucionDTO institucionReceptor) {
		this.institucionReceptor = institucionReceptor;
	}

	public CuentaRetiroEfectivoDTO getCuentaBeneficiario() {
		return cuentaBeneficiario;
	}

	public void setCuentaBeneficiario(CuentaRetiroEfectivoDTO cuentaBeneficiario) {
		this.cuentaBeneficiario = cuentaBeneficiario;
	}

	public String getReferenciaNumerica() {
		return referenciaNumerica;
	}

	public void setReferenciaNumerica(String referenciaNumerica) {
		this.referenciaNumerica = referenciaNumerica;
	}

	public CuentaRetiroInternacionalDTO getCuentaRetiroInternacional() {
		return cuentaRetiroInternacional;
	}

	public void setCuentaRetiroInternacional(
			CuentaRetiroInternacionalDTO cuentaRetiroInternacional) {
		this.cuentaRetiroInternacional = cuentaRetiroInternacional;
	}

	public String getReferenciaOperacion() {
		return referenciaOperacion;
	}

	public void setReferenciaOperacion(String referenciaOperacion) {
		this.referenciaOperacion = referenciaOperacion;
	}

	public String getReferenciaMensaje() {
		return referenciaMensaje;
	}

	public void setReferenciaMensaje(String referenciaMensaje) {
		this.referenciaMensaje = referenciaMensaje;
	}

	public String getNombreBeneficiario() {
		return nombreBeneficiario;
	}

	public void setNombreBeneficiario(String nombreBeneficiario) {
		this.nombreBeneficiario = nombreBeneficiario;
	}

	public String getFolioCuentaPorTraspasante() {
		return folioCuentaPorTraspasante;
	}

	public void setFolioCuentaPorTraspasante(String folioCuentaPorTraspasante) {
		this.folioCuentaPorTraspasante = folioCuentaPorTraspasante;
	}

	
	
}