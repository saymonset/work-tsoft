/**
 * Bursatec - Portal DALI
 * Copyrigth (c) 2014 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.filetransfer.formatos;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Formato para Miscelanea Fiscal.
 * 
 * @author Pablo Balderas
 */
public class FormatoMiscelaneaFiscal {

    /** Fecha de registro */
    private String fechaRegistro;
    
    /** Fecha y hora de concertacion */
    private String fechaHoraCierreOper;
    
    /** Traspasante */
    private String traspasante;
    
    /** Cuenta del traspasante */
    private String cuentaTraspasante;
    
    /** Receptor */
    private String receptor;
    
    /** Cuenta del receptor */
    private String cuentaReceptor;
    
    /** TV */
    private String tipoValor;
    
    /** Emisora */
    private String emisora;
    
    /** Serie */
    private String serie;
    
    /** Cupón */
    private String cupon;
    
    /** ISIN */
    private String isin;
    
    /** Boveda */
    private String boveda;
    
    /** Cantidad operada */
    private BigInteger cantidadOperada;
    
    /** Divisa */
    private String divisa;
    
    /** Fecha de adquisición */
    private String fechaAdquisicion;
    
    /** Precio de adquisición */
    private BigDecimal precioAdquisicion;
    
    /** Cliente */
    private String cliente;
    
    /** RFC / CURP */
    private String rfcCurp;
    
    /** Bandera que indica si es extranjero */
    private String extranjero;
    
    /** Costo fiscal actualizado */
    private BigDecimal costoFiscalActualizado;

	/**
	 * @return the fechaRegistro
	 */
	public String getFechaRegistro() {
		return fechaRegistro;
	}

	/**
	 * @param fechaRegistro the fechaRegistro to set
	 */
	public void setFechaRegistro(String fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getFechaHoraCierreOper() {
		return fechaHoraCierreOper;
	}

	public void setFechaHoraCierreOper(String fechaHoraCierreOper) {
		this.fechaHoraCierreOper = fechaHoraCierreOper;
	}

	/**
	 * @return the traspasante
	 */
	public String getTraspasante() {
		return traspasante;
	}

	/**
	 * @param traspasante the traspasante to set
	 */
	public void setTraspasante(String traspasante) {
		this.traspasante = traspasante;
	}

	/**
	 * @return the cuentaTraspasante
	 */
	public String getCuentaTraspasante() {
		return cuentaTraspasante;
	}

	/**
	 * @param cuentaTraspasante the cuentaTraspasante to set
	 */
	public void setCuentaTraspasante(String cuentaTraspasante) {
		this.cuentaTraspasante = cuentaTraspasante;
	}

	/**
	 * @return the receptor
	 */
	public String getReceptor() {
		return receptor;
	}

	/**
	 * @param receptor the receptor to set
	 */
	public void setReceptor(String receptor) {
		this.receptor = receptor;
	}

	/**
	 * @return the cuentaReceptor
	 */
	public String getCuentaReceptor() {
		return cuentaReceptor;
	}

	/**
	 * @param cuentaReceptor the cuentaReceptor to set
	 */
	public void setCuentaReceptor(String cuentaReceptor) {
		this.cuentaReceptor = cuentaReceptor;
	}

	/**
	 * @return the tv
	 */
	public String getTipoValor() {
		return tipoValor;
	}

	/**
	 * @param tv the tv to set
	 */
	public void setTipoValor(String tipoValor) {
		this.tipoValor = tipoValor;
	}

	/**
	 * @return the emisora
	 */
	public String getEmisora() {
		return emisora;
	}

	/**
	 * @param emisora the emisora to set
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
	 * @param serie the serie to set
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
	 * @param cupon the cupon to set
	 */
	public void setCupon(String cupon) {
		this.cupon = cupon;
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
	 * @return the boveda
	 */
	public String getBoveda() {
		return boveda;
	}

	/**
	 * @param boveda the boveda to set
	 */
	public void setBoveda(String boveda) {
		this.boveda = boveda;
	}

	/**
	 * @return the cantidadOperada
	 */
	public BigInteger getCantidadOperada() {
		return cantidadOperada;
	}

	/**
	 * @param cantidadOperada the cantidadOperada to set
	 */
	public void setCantidadOperada(BigInteger cantidadOperada) {
		this.cantidadOperada = cantidadOperada;
	}

	/**
	 * @return the divisa
	 */
	public String getDivisa() {
		return divisa;
	}

	/**
	 * @param divisa the divisa to set
	 */
	public void setDivisa(String divisa) {
		this.divisa = divisa;
	}

	/**
	 * @return the fechaAdquisicion
	 */
	public String getFechaAdquisicion() {
		return fechaAdquisicion;
	}

	/**
	 * @param fechaAdquisicion the fechaAdquisicion to set
	 */
	public void setFechaAdquisicion(String fechaAdquisicion) {
		this.fechaAdquisicion = fechaAdquisicion;
	}

	/**
	 * @return the precioAdquisicion
	 */
	public BigDecimal getPrecioAdquisicion() {
		return precioAdquisicion;
	}

	/**
	 * @param precioAdquisicion the precioAdquisicion to set
	 */
	public void setPrecioAdquisicion(BigDecimal precioAdquisicion) {
		this.precioAdquisicion = precioAdquisicion;
	}

	/**
	 * @return the cliente
	 */
	public String getCliente() {
		return cliente;
	}

	/**
	 * @param cliente the cliente to set
	 */
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	/**
	 * @return the rfcCurp
	 */
	public String getRfcCurp() {
		return rfcCurp;
	}

	/**
	 * @param rfcCurp the rfcCurp to set
	 */
	public void setRfcCurp(String rfcCurp) {
		this.rfcCurp = rfcCurp;
	}

	/**
	 * @return the extranjero
	 */
	public String getExtranjero() {
		return extranjero;
	}

	/**
	 * @param extranjero the extranjero to set
	 */
	public void setExtranjero(String extranjero) {
		this.extranjero = extranjero;
	}

	/**
	 * @return the costoFiscalActualizado
	 */
	public BigDecimal getCostoFiscalActualizado() {
		return costoFiscalActualizado;
	}

	/**
	 * @param costoFiscalActualizado the costoFiscalActualizado to set
	 */
	public void setCostoFiscalActualizado(BigDecimal costoFiscalActualizado) {
		this.costoFiscalActualizado = costoFiscalActualizado;
	}

	
}
