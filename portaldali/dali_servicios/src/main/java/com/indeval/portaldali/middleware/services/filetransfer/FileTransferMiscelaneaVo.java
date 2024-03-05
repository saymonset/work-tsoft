/**
 * Bursatec - Portal DALI
 * Copyrigth (c) 2014 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.filetransfer;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * Value Object que representa los registros de file transfer de miscelanea fiscal.
 * 
 * @author Pablo Balderas
 *
 */
public class FileTransferMiscelaneaVo implements Serializable {

	/** Id para la serialización. */
	private static final long serialVersionUID = -2764859552976908667L;

	/** Id de la institución que realiza el file transfer */
	private String idInst;

	/** Folio de la institución que realiza el file transfer */
    private String folioInst;

	/** Tipo de file transfer */
    private String tipoReg;
	
	/** Consecutivo */
    private Integer consec;
	
	/** Fecha de registro */
    private Date fechaRegistro;
    
    /** Fecha y hora de concertacion */
    private Date fechaHoraCierreOper;
    
    /** Id del traspasante */
    private String idTraspasante;
    
    /** Folio del traspasante */
    private String folioTraspasante;
    
    /** Cuenta del traspasante */
    private String cuentaTraspasante;
    
    /** Id del receptor */
    private String idReceptor;
    
    /** Folio del receptor */
    private String folioReceptor;
    
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
    private Date fechaAdquisicion;
    
    /** Precio de adquisición */
    private BigDecimal precioAdquisicion;
    
    /** Cliente */
    private String cliente;
    
    /** RFC / CURP */
    private String rfcCurp;
    
    /** Bandera que indica si es extranjero */
    private boolean extranjero;
    
    /** Costo fiscal actualizado */
    private BigDecimal costoFiscalActualizado;

    /** Bandera que indica si es compra */
    private boolean compra;

	/**
	 * @return the idInst
	 */
	public String getIdInst() {
		return idInst;
	}

	/**
	 * @param idInst the idInst to set
	 */
	public void setIdInst(String idInst) {
		this.idInst = idInst;
	}

	/**
	 * @return the folioInst
	 */
	public String getFolioInst() {
		return folioInst;
	}

	/**
	 * @param folioInst the folioInst to set
	 */
	public void setFolioInst(String folioInst) {
		this.folioInst = folioInst;
	}

	/**
	 * @return the tipoReg
	 */
	public String getTipoReg() {
		return tipoReg;
	}

	/**
	 * @param tipoReg the tipoReg to set
	 */
	public void setTipoReg(String tipoReg) {
		this.tipoReg = tipoReg;
	}

	/**
	 * @return the consec
	 */
	public Integer getConsec() {
		return consec;
	}

	/**
	 * @param consec the consec to set
	 */
	public void setConsec(Integer consec) {
		this.consec = consec;
	}

	/**
	 * @return the fechaRegistro
	 */
	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	/**
	 * @param fechaRegistro the fechaRegistro to set
	 */
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Date getFechaHoraCierreOper() {
		return fechaHoraCierreOper;
	}

	public void setFechaHoraCierreOper(Date fechaHoraCierreOper) {
		this.fechaHoraCierreOper = fechaHoraCierreOper;
	}

	/**
	 * @return the idTraspasante
	 */
	public String getIdTraspasante() {
		return idTraspasante;
	}

	/**
	 * @param idTraspasante the idTraspasante to set
	 */
	public void setIdTraspasante(String idTraspasante) {
		this.idTraspasante = idTraspasante;
	}

	/**
	 * @return the folioTraspasante
	 */
	public String getFolioTraspasante() {
		return folioTraspasante;
	}

	/**
	 * @param folioTraspasante the folioTraspasante to set
	 */
	public void setFolioTraspasante(String folioTraspasante) {
		this.folioTraspasante = folioTraspasante;
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
	 * @return the idReceptor
	 */
	public String getIdReceptor() {
		return idReceptor;
	}

	/**
	 * @param idReceptor the idReceptor to set
	 */
	public void setIdReceptor(String idReceptor) {
		this.idReceptor = idReceptor;
	}

	/**
	 * @return the folioReceptor
	 */
	public String getFolioReceptor() {
		return folioReceptor;
	}

	/**
	 * @param folioReceptor the folioReceptor to set
	 */
	public void setFolioReceptor(String folioReceptor) {
		this.folioReceptor = folioReceptor;
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
	public Date getFechaAdquisicion() {
		return fechaAdquisicion;
	}

	/**
	 * @param fechaAdquisicion the fechaAdquisicion to set
	 */
	public void setFechaAdquisicion(Date fechaAdquisicion) {
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
	public boolean isExtranjero() {
		return extranjero;
	}

	/**
	 * @param extranjero the extranjero to set
	 */
	public void setExtranjero(boolean extranjero) {
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

	/**
	 * @return the compra
	 */
	public boolean isCompra() {
		return compra;
	}

	/**
	 * @param compra the compra to set
	 */
	public void setCompra(boolean compra) {
		this.compra = compra;
	}
	
	
}
