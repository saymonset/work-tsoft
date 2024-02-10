/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.servicios.vo;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import org.springframework.validation.Errors;

import com.indeval.portaldali.middleware.servicios.modelo.AbstractBaseDTO;

/**
 * 
 * @author csanchez
 * 
 */
public class CamposDivIntVO extends AbstractBaseDTO {

    /**
	 * Constante de Serializacion
	 */
	private static final long serialVersionUID = 1L;

	private String idInstTrasp;

    private String folioInstTrasp;

    private String cuentaTrasp;

    private String idInstRecep;

    private String folioInstRecep;

    private String cuentaRecep;

    private String tv;

    private String emisora;

    private String serie;

    private String cupon;
    
    private String isin;
    
    private Long idBoveda;
    
    private BigInteger cantidad;

    private String tipoOperacion;

    private String tipoMovimiento;

    private Date fechaNotificacion;

    private Date fechaLiquidacion;

    private Date fechaOperacion;

    private String descCustodio;

    private String cuentaContraparte;

    private String descContraparte;

    private String nombreDepositante;

    private String nombreCuentaBeneficiario;

    private String numeroCuentaBeneficiario;

    private BigDecimal importe;

    private String divisa;

    private String instrucEsp;

    private String tipoMensaje;

    private String estatus;

    private String bajaLogica;
    
    private String mercado;
    /*
     * variable para identificar el fileTransfer
     */
    private String tipoFileTransfer;
    
    private String liquidacionParcial;

    /**
     * @return the idInstTrasp
     */
    public String getIdInstTrasp() {
        return idInstTrasp;
    }

    /**
     * @param idInstTrasp
     *            the idInstTrasp to set
     */
    public void setIdInstTrasp(String idInstTrasp) {
        this.idInstTrasp = idInstTrasp;
    }

    /**
     * @return the folioInstTrasp
     */
    public String getFolioInstTrasp() {
        return folioInstTrasp;
    }

    /**
     * @param folioInstTrasp
     *            the folioInstTrasp to set
     */
    public void setFolioInstTrasp(String folioInstTrasp) {
        this.folioInstTrasp = folioInstTrasp;
    }

    /**
     * @return the cuentaTrasp
     */
    public String getCuentaTrasp() {
        return cuentaTrasp;
    }

    /**
     * @param cuentaTrasp
     *            the cuentaTrasp to set
     */
    public void setCuentaTrasp(String cuentaTrasp) {
        this.cuentaTrasp = cuentaTrasp;
    }

    /**
     * @return the idInstRecep
     */
    public String getIdInstRecep() {
        return idInstRecep;
    }

    /**
     * @param idInstRecep
     *            the idInstRecep to set
     */
    public void setIdInstRecep(String idInstRecep) {
        this.idInstRecep = idInstRecep;
    }

    /**
     * @return the folioInstRecep
     */
    public String getFolioInstRecep() {
        return folioInstRecep;
    }

    /**
     * @param folioInstRecep
     *            the folioInstRecep to set
     */
    public void setFolioInstRecep(String folioInstRecep) {
        this.folioInstRecep = folioInstRecep;
    }

    /**
     * @return the cuentaRecep
     */
    public String getCuentaRecep() {
        return cuentaRecep;
    }

    /**
     * @param cuentaRecep
     *            the cuentaRecep to set
     */
    public void setCuentaRecep(String cuentaRecep) {
        this.cuentaRecep = cuentaRecep;
    }

    /**
     * @return the tv
     */
    public String getTv() {
        return tv;
    }

    /**
     * @param tv
     *            the tv to set
     */
    public void setTv(String tv) {
        this.tv = tv;
    }

    /**
     * @return the emisora
     */
    public String getEmisora() {
        return emisora;
    }

    /**
     * @param emisora
     *            the emisora to set
     */
    public void setEmisora(String emisora) {
        this.emisora = emisora;
    }

    /**
     * @return the serie
     */
    public String getSerie() {
        return serie;
    }

    /**
     * @param serie
     *            the serie to set
     */
    public void setSerie(String serie) {
        this.serie = serie;
    }

    /**
     * @return the cupon
     */
    public String getCupon() {
        return cupon;
    }

    /**
     * @param cupon
     *            the cupon to set
     */
    public void setCupon(String cupon) {
        this.cupon = cupon;
    }

    /**
     * @return the cantidad
     */
    public BigInteger getCantidad() {
        return cantidad;
    }

    /**
     * @param cantidad
     *            the cantidad to set
     */
    public void setCantidad(BigInteger cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * @return the tipoOperacion
     */
    public String getTipoOperacion() {
        return tipoOperacion;
    }

    /**
     * @param tipoOperacion
     *            the tipoOperacion to set
     */
    public void setTipoOperacion(String tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }

    /**
     * @return the tipoMovimiento
     */
    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    /**
     * @param tipoMovimiento
     *            the tipoMovimiento to set
     */
    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    /**
     * @return the fechaNotificacion
     */
    public Date getFechaNotificacion() {
        return fechaNotificacion;
    }

    /**
     * @param fechaNotificacion
     *            the fechaNotificacion to set
     */
    public void setFechaNotificacion(Date fechaNotificacion) {
        this.fechaNotificacion = fechaNotificacion;
    }

    /**
     * @return the fechaLiquidacion
     */
    public Date getFechaLiquidacion() {
        return fechaLiquidacion;
    }

    /**
     * @param fechaLiquidacion
     *            the fechaLiquidacion to set
     */
    public void setFechaLiquidacion(Date fechaLiquidacion) {
        this.fechaLiquidacion = fechaLiquidacion;
    }

    /**
     * @return the fechaOperacion
     */
    public Date getFechaOperacion() {
        return fechaOperacion;
    }

    /**
     * @param fechaOperacion
     *            the fechaOperacion to set
     */
    public void setFechaOperacion(Date fechaOperacion) {
        this.fechaOperacion = fechaOperacion;
    }

    /**
     * @return the descCustodio
     */
    public String getDescCustodio() {
        return descCustodio;
    }

    /**
     * @param descCustodio
     *            the descCustodio to set
     */
    public void setDescCustodio(String descCustodio) {
        this.descCustodio = descCustodio;
    }

    /**
     * @return the cuentaContraparte
     */
    public String getCuentaContraparte() {
        return cuentaContraparte;
    }

    /**
     * @param cuentaContraparte
     *            the cuentaContraparte to set
     */
    public void setCuentaContraparte(String cuentaContraparte) {
        this.cuentaContraparte = cuentaContraparte;
    }

    /**
     * @return the descContraparte
     */
    public String getDescContraparte() {
        return descContraparte;
    }

    /**
     * @param descContraparte
     *            the descContraparte to set
     */
    public void setDescContraparte(String descContraparte) {
        this.descContraparte = descContraparte;
    }

    /**
     * @return the nombreDepositante
     */
    public String getNombreDepositante() {
        return nombreDepositante;
    }

    /**
     * @param nombreDepositante
     *            the nombreDepositante to set
     */
    public void setNombreDepositante(String nombreDepositante) {
        this.nombreDepositante = nombreDepositante;
    }

    /**
     * @return the nombreCuentaBeneficiario
     */
    public String getNombreCuentaBeneficiario() {
        return nombreCuentaBeneficiario;
    }

    /**
     * @param nombreCuentaBeneficiario
     *            the nombreCuentaBeneficiario to set
     */
    public void setNombreCuentaBeneficiario(String nombreCuentaBeneficiario) {
        this.nombreCuentaBeneficiario = nombreCuentaBeneficiario;
    }

    /**
     * @return the numeroCuentaBeneficiario
     */
    public String getNumeroCuentaBeneficiario() {
        return numeroCuentaBeneficiario;
    }

    /**
     * @param numeroCuentaBeneficiario
     *            the numeroCuentaBeneficiario to set
     */
    public void setNumeroCuentaBeneficiario(String numeroCuentaBeneficiario) {
        this.numeroCuentaBeneficiario = numeroCuentaBeneficiario;
    }

    /**
     * @return the importe
     */
    public BigDecimal getImporte() {
        return importe;
    }

    /**
     * @param importe
     *            the importe to set
     */
    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    /**
     * @return the divisa
     */
    public String getDivisa() {
        return divisa;
    }

    /**
     * @param divisa
     *            the divisa to set
     */
    public void setDivisa(String divisa) {
        this.divisa = divisa;
    }

    /**
     * @return the instrucEsp
     */
    public String getInstrucEsp() {
        return instrucEsp;
    }

    /**
     * @param instrucEsp
     *            the instrucEsp to set
     */
    public void setInstrucEsp(String instrucEsp) {
        this.instrucEsp = instrucEsp;
    }

    /**
     * @return the tipoMensaje
     */
    public String getTipoMensaje() {
        return tipoMensaje;
    }

    /**
     * @param tipoMensaje
     *            the tipoMensaje to set
     */
    public void setTipoMensaje(String tipoMensaje) {
        this.tipoMensaje = tipoMensaje;
    }

    /**
     * @return the estatus
     */
    public String getEstatus() {
        return estatus;
    }

    /**
     * @param estatus
     *            the estatus to set
     */
    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    /**
     * @return the bajaLogica
     */
    public String getBajaLogica() {
        return bajaLogica;
    }

    /**
     * @param bajaLogica
     *            the bajaLogica to set
     */
    public void setBajaLogica(String bajaLogica) {
        this.bajaLogica = bajaLogica;
    }

    /**
     * @return the mercado
     */
    public String getMercado() {
        return mercado;
    }

    /**
     * @param mercado the mercado to set
     */
    public void setMercado(String mercado) {
        this.mercado = mercado;
    }

    /**
     * @return the isin
     */
    public String getIsin() {
        return isin;
    }

    /**
     * @param isin the isin to set
     */
    public void setIsin(String isin) {
        this.isin = isin;
    }

	/**
	 * @return the idBoveda
	 */
	public Long getIdBoveda() {
		return idBoveda;
	}

	/**
	 * @param idBoveda the idBoveda to set
	 */
	public void setIdBoveda(Long idBoveda) {
		this.idBoveda = idBoveda;
	}

	/**
	 * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
	 */
	public void validate(Object arg0, Errors arg1) {
		
	}

	public String getTipoFileTransfer() {
		return tipoFileTransfer;
	}

	public void setTipoFileTransfer(String tipoFileTransfer) {
		this.tipoFileTransfer = tipoFileTransfer;
	}

	/**
	 * @return the liquidacionParcial
	 */
	public String getLiquidacionParcial() {
		return liquidacionParcial;
	}

	/**
	 * @param liquidacionParcial the liquidacionParcial to set
	 */
	public void setLiquidacionParcial(String liquidacionParcial) {
		this.liquidacionParcial = liquidacionParcial;
	}
	
}
