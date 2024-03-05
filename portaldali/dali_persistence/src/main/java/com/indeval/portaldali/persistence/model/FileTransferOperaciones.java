/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.StringUtils;

/**
 * @hibernate.mapping
 *
 * @hibernate.class
 *   table="T_FILETRANSFER_OPERACIONES"
 *   proxy="com.indeval.persistence.portaldali.dali.modelo.FileTransferOperaciones";
 */
@Entity
@Table(name="T_FILETRANSFER_OPERACIONES")
public class FileTransferOperaciones {

	@EmbeddedId
    private FileTransferPK fileTransferPK; // PK
    
	@Column(name ="fecha_liquidacion")
	private Date fechaLiquidacion;

	@Column(name ="fecha_reporto")
    private Date fechaReporto;

	@Column(name ="fecha_concertacion")
    private Date fechaConcertacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name ="fecha_hora_cierre_oper")
    private Date fechaHoraCierreOper;
	
	@Column(name ="precio_titulo")
    private BigDecimal precioTitulo;

	@Column(name ="tasa_premio")
    private BigDecimal tasaPremio;

	@Column(name ="importe")
    private BigDecimal importe;

	@Column(name ="mercado")
    private String mercado;

	@Column(name ="origen_aplicacion")
    private String origenAplicacion;

	@Column(name ="origen")
    private String origen;

	@Column(name ="divisa")
    private String divisa;
		

	@Column(name ="sociedad_serie")
    private String sociedadSerie;

	@Column(name ="marca_compra")
    private Integer marcaCompra;
    

    /**
     * @hibernate.composite-id
     * @return the fileTransferPK
     */
    public FileTransferPK getFileTransferPK() {
        return fileTransferPK;
    }

    /**
     * @hibernate.property
     *   column="fecha_liquidacion"
     *   not-null="false"
     */
    public Date getFechaLiquidacion() {
        return fechaLiquidacion;
    }

    /**
     * @hibernate.property
     *   column="fecha_reporto"
     *   not-null="false"
     */
    public Date getFechaReporto() {
        return fechaReporto;
    }

    /**
     * @hibernate.property
     *   column="fecha_concertacion"
     *   not-null="false"
     */
    public Date getFechaConcertacion() {
        return fechaConcertacion;
    }

    public Date getFechaHoraCierreOper() {
		return fechaHoraCierreOper;
	}

	/**
     * @hibernate.property
     *   column="precio_titulo"
     *   not-null="false"
     */
    public BigDecimal getPrecioTitulo() {
        return precioTitulo;
    }

    /**
     * @hibernate.property
     *   column="tasa_premio"
     *   not-null="false"
     */
    public BigDecimal getTasaPremio() {
        return tasaPremio;
    }

    /**
     * @hibernate.property
     *   column="importe"
     *   not-null="false"
     */
    public BigDecimal getImporte() {
        return importe;
    }

    /**
     * @hibernate.property
     *   column="mercado"
     *   not-null="false"
     */
    public String getMercado() {
        return StringUtils.isNotBlank(mercado) ? mercado.trim() : mercado;
    }

    /**
     * @hibernate.property
     *   column="origen_aplicacion"
     *   not-null="false"
     */
    public String getOrigenAplicacion() {
        return StringUtils.isNotBlank(origenAplicacion) ? origenAplicacion.trim() : origenAplicacion;
    }

    /**
     * @hibernate.property
     *   column="origen"
     *   not-null="false"
     */
    public String getOrigen() {
        return StringUtils.isNotBlank(origen) ? origen.trim() : origen;
    }

    /**
     * @hibernate.property
     *   column="divisa"
     *   not-null="false"
     */
    public String getDivisa() {
        return StringUtils.isNotBlank(divisa) ? divisa.trim() : divisa;
    }

    /**
     * @hibernate.property
     *   column="sociedad_serie"
     *   not-null="false"
     */
    public String getSociedadSerie() {
        return StringUtils.isNotBlank(sociedadSerie) ? sociedadSerie.trim() : sociedadSerie;
    }

    /**
     * @hibernate.property
     *   column="marca_compra"
     *   not-null="false"
     */
    public Integer getMarcaCompra() {
        return marcaCompra;
    }
    


    /**
     * @param fileTransferPK the fileTransferPK to set
     */
    public void setFileTransferPK(FileTransferPK fileTransferPK) {
        this.fileTransferPK = fileTransferPK;
    }

    /**
     * @param fechaLiquidacion
     */
    public void setFechaLiquidacion(Date fechaLiquidacion) {
        this.fechaLiquidacion = fechaLiquidacion;
    }

    /**
     * @param fechaReporto
     */
    public void setFechaReporto(Date fechaReporto) {
        this.fechaReporto = fechaReporto;
    }

    /**
     * @param fechaConcertacion
     */
    public void setFechaConcertacion(Date fechaConcertacion) {
        this.fechaConcertacion = fechaConcertacion;
    }

    public void setFechaHoraCierreOper(Date fechaHoraCierreOper) {
		this.fechaHoraCierreOper = fechaHoraCierreOper;
	}

	/**
     * @param precioTitulo
     */
    public void setPrecioTitulo(BigDecimal precioTitulo) {
        this.precioTitulo = precioTitulo;
    }

    /**
     * @param tasaPremio
     */
    public void setTasaPremio(BigDecimal tasaPremio) {
        this.tasaPremio = tasaPremio;
    }

    /**
     * @param importe
     */
    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    /**
     * @param mercado
     */
    public void setMercado(String mercado) {
        this.mercado = mercado;
    }

    /**
     * @param origenAplicacion
     */
    public void setOrigenAplicacion(String origenAplicacion) {
        this.origenAplicacion = origenAplicacion;
    }

    /**
     * @param origen
     */
    public void setOrigen(String origen) {
        this.origen = origen;
    }

    /**
     * @param divisa
     */
    public void setDivisa(String divisa) {
        this.divisa = divisa;
    }

    /**
     * @param sociedadSerie
     */
    public void setSociedadSerie(String sociedadSerie) {
        this.sociedadSerie = sociedadSerie;
    }

    /**
     * @param marcaCompra
     */
    public void setMarcaCompra(Integer marcaCompra) {
        this.marcaCompra = marcaCompra;
    }
 
  
    
}
