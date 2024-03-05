/**
 * Bursatec - Portal DALI
 * Copyrigth (c) 2014 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entidad que representa la tabla T_FILETRANSFER_MISCELANEA.
 * 
 * @author Pablo Balderas
 */
@Entity
@Table(name="T_FILETRANSFER_MISCELANEA")
public class FileTransferMiscelanea implements Serializable {

	/** Id para la serialización */
	private static final long serialVersionUID = 7966769852661608310L;

    /** PK de la tabla */
    @EmbeddedId
    private FileTransferPK fileTransferPK;
    
    /** Fecha de registro */
    @Column(name="FECHA_REGISTRO")
    private Date fechaRegistro;
    
    /** Fecha de concertacion */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="FECHA_HORA_CIERRE_OPER")
    private Date fechaHoraCierreOper;
    
    /** Id del traspasante */
    @Column(name="ID_TRASPASANTE")
    private String idTraspasante;
    
    /** Folio del traspasante */
    @Column(name="FOLIO_TRASPASANTE")
    private String folioTraspasante;
    
    /** Cuenta del traspasante */
    @Column(name="CUENTA_TRASPASANTE")
    private String cuentaTraspasante;
    
    /** Id del receptor */
    @Column(name="ID_RECEPTOR")
    private String idReceptor;
    
    /** Folio del receptor */
    @Column(name="FOLIO_RECEPTOR")
    private String folioReceptor;
    
    /** Cuenta del receptor */
    @Column(name="CUENTA_RECEPTOR")
    private String cuentaReceptor;
    
    /** TV */
    @Column(name="TV")
    private String tipoValor;
    
    /** Emisora */
    @Column(name="EMISORA")
    private String emisora;
    
    /** Serie */
    @Column(name="SERIE")
    private String serie;
    
    /** Cupón */
    @Column(name="CUPON")
    private String cupon;
    
    /** ISIN */
    @Column(name="ISIN")
    private String isin;
    
    /** Boveda */
    @Column(name="BOVEDA")
    private String boveda;
    
    /** Cantidad operada */
    @Column(name="CANTIDAD_OPERADA") 
    private BigInteger cantidadOperada;
    
    /** Divisa */
    @Column(name="DIVISA")
    private String divisa;
    
    /** Fecha de adquisición */
    @Column(name="FECHA_ADQUISICION")
    private Date fechaAdquisicion;
    
    /** Precio de adquisición */
    @Column(name="PRECIO_ADQUISICION")
    private BigDecimal precioAdquisicion;
    
    /** Cliente */
    @Column(name="CLIENTE")
    private String cliente;
    
    /** RFC / CURP */
    @Column(name="RFC_CURP")
    private String rfcCurp;
    
    /** Bandera que indica si es extranjero */
	@Column(name="EXTRANJERO")
    private boolean extranjero;
    
    /** Costo fiscal actualizado */
    @Column(name="COSTO_FISCAL_ACTUALIZADO")
    private BigDecimal costoFiscalActualizado;

    /** Bandera que indica si es compra */
	@Column(name="COMPRA")
    private boolean compra;
    
	/**
	 * @return the fileTransferPK
	 */
	public FileTransferPK getFileTransferPK() {
		return fileTransferPK;
	}

	/**
	 * @param fileTransferPK the fileTransferPK to set
	 */
	public void setFileTransferPK(FileTransferPK fileTransferPK) {
		this.fileTransferPK = fileTransferPK;
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
	public void setTv(String tipoValor) {
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
