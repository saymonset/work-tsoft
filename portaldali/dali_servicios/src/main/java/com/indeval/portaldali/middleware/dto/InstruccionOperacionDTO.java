package com.indeval.portaldali.middleware.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;



public class InstruccionOperacionDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long idInstruccionOperacionVal;
	private Long idInstitucionReceptora;
	private Long idInstitucionTraspasante;
	private Integer plazoReporto;
	private Integer idDivisa;
	private Double precioTitulo;
	private Double tasaNegociada;
	private Integer cantidadTitulos;
	private Double importe;
	private Date fechaLiquidacion;
	private Date fechaConcertacion;
	public Long getIdInstruccionOperacionVal() {
		return idInstruccionOperacionVal;
	}
	public void setIdInstruccionOperacionVal(Long idInstruccionOperacionVal) {
		this.idInstruccionOperacionVal = idInstruccionOperacionVal;
	}
	public Long getIdInstitucionReceptora() {
		return idInstitucionReceptora;
	}
	public void setIdInstitucionReceptora(Long idInstitucionReceptora) {
		this.idInstitucionReceptora = idInstitucionReceptora;
	}
	public Long getIdInstitucionTraspasante() {
		return idInstitucionTraspasante;
	}
	public void setIdInstitucionTraspasante(Long idInstitucionTraspasante) {
		this.idInstitucionTraspasante = idInstitucionTraspasante;
	}
	public Integer getPlazoReporto() {
		return plazoReporto;
	}
	public void setPlazoReporto(Integer plazoReporto) {
		this.plazoReporto = plazoReporto;
	}
	public Integer getIdDivisa() {
		return idDivisa;
	}
	public void setIdDivisa(Integer idDivisa) {
		this.idDivisa = idDivisa;
	}
	public Double getPrecioTitulo() {
		return precioTitulo;
	}
	public void setPrecioTitulo(Double precioTitulo) {
		this.precioTitulo = precioTitulo;
	}
	public Double getTasaNegociada() {
		return tasaNegociada;
	}
	public void setTasaNegociada(Double tasaNegociada) {
		this.tasaNegociada = tasaNegociada;
	}
	public Integer getCantidadTitulos() {
		return cantidadTitulos;
	}
	public void setCantidadTitulos(Integer cantidadTitulos) {
		this.cantidadTitulos = cantidadTitulos;
	}
	public Double getImporte() {
		return importe;
	}
	public void setImporte(Double importe) {
		this.importe = importe;
	}
	public Date getFechaLiquidacion() {
		return fechaLiquidacion;
	}
	public void setFechaLiquidacion(Date fechaLiquidacion) {
		this.fechaLiquidacion = fechaLiquidacion;
	}
	public Date getFechaConcertacion() {
		return fechaConcertacion;
	}
	public void setFechaConcertacion(Date fechaConcertacion) {
		this.fechaConcertacion = fechaConcertacion;
	}
}
