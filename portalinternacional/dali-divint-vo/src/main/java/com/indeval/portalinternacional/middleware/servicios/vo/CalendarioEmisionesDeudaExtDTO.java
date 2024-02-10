package com.indeval.portalinternacional.middleware.servicios.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class CalendarioEmisionesDeudaExtDTO implements Serializable{
	/**
	 * private String custodio;
	private String estado;
	private String tipoPagoCAEV;
	private String tipoPagoCAMV;
	private String divisa;
	private String tipoValor;
	private String emisora;
	private String serie;
	private String isin;
	private String cupon;
	private String refCustodio;
	private Date fechaPago;
	private Date fechaCorte;
	 */
	private static final long serialVersionUID = 1L;
	private Long idCalendario; //REF CUSTODIO:requerido
	private String refCustodio; //REF CUSTODIO:requerido	
	private String tipoValor;//TV:requerido
	private String emisora;//EMISORA:requerido
	private String serie;//SERIE:requerido
	private String cupon;//CUPON:requerido
	private String isin;//ISIN:mensaje
	private Date fechaCorte;//FECHA CORTE:mensaje	
	private Date fechaPago;//FECHA PAGO:mensaje	
	private String divisa;//MONEDA:mensaje
	private Integer custodio;//CUSTODIO:mensaje
	private Integer tipoPagoCAEV;//TIPO DE PAGO CAEV:mensaje
	private Integer tipoPagoCAMV;//TIPO DE PAGO CAEV:mensaje
	private Integer estado;//Estado
	private boolean calendarioIndeval;
	private boolean hojaDeTrabajo;	
	private Integer estadoMensajeria;
	
	
	
	/**
	 * 
	 */
	public CalendarioEmisionesDeudaExtDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public void init(){
		this.cupon=null;
		this.custodio=-1;
		this.divisa=null;
		this.emisora=null;
		this.estado=-1;
		this.fechaCorte=null;
		this.fechaPago=null;
		this.tipoValor=null;
		this.isin=null;
		this.refCustodio=null;		
		this.serie=null;
		this.tipoPagoCAEV=-1;
		this.tipoPagoCAMV=-1;
		this.estadoMensajeria=-1;
		this.calendarioIndeval=false;
	}
	
	/**
	 * verifica si es una consulta sin filtros	
	 * @return si es una consulta sin filtros
	 */
	public Boolean isAll(){
		if((this.cupon!=null && !this.cupon.equals(""))||
			(this.custodio!=null && this.custodio!=-1) ||
			(this.divisa!=null && !this.divisa.equals(""))||
			(this.emisora!=null && !this.emisora.equals(""))||
			(this.estado!=null && this.estado!=-1) ||
			this.fechaCorte!=null ||
			this.fechaPago!=null ||
			(this.tipoValor!=null && !this.tipoValor.equals(""))||
			(this.isin!=null && !this.isin.equals(""))||
			(this.refCustodio!=null && !this.refCustodio.equals("")) ||		
			(this.serie!=null && !this.serie.equals(""))||
			(this.tipoPagoCAEV!=null && this.tipoPagoCAEV!=-1) ||
			(this.tipoPagoCAMV!=null && this.tipoPagoCAMV!=-1)){
			return false;
		}
		return true;
	}
	/**
	 * @return the idCalendario
	 */
	public Long getIdCalendario() {
		return idCalendario;
	}

	/**
	 * @param idCalendario the idCalendario to set
	 */
	public void setIdCalendario(Long idCalendario) {
		this.idCalendario = idCalendario;
	}

	/**
	 * @return the refCustodio
	 */
	public String getRefCustodio() {
		return refCustodio;
	}

	/**
	 * @param refCustodio the refCustodio to set
	 */
	public void setRefCustodio(String refCustodio) {
		this.refCustodio = refCustodio;
	}

	/**
	 * @return the tipoValor
	 */
	public String getTipoValor() {
		return this.tipoValor != null ? this.tipoValor.toUpperCase():null;
	}

	/**
	 * @param tipoValor the tipoValor to set
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
	 * @return the fechaCorte
	 */
	public Date getFechaCorte() {
		return fechaCorte;
	}

	/**
	 * @param fechaCorte the fechaCorte to set
	 */
	public void setFechaCorte(Date fechaCorte) {
		this.fechaCorte = fechaCorte;
	}

	/**
	 * @return the fechaPago
	 */
	public Date getFechaPago() {
		return fechaPago;
	}

	/**
	 * @param fechaPago the fechaPago to set
	 */
	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
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
	 * @return the custodio
	 */
	public Integer getCustodio() {
		return custodio;
	}

	/**
	 * @param custodio the custodio to set
	 */
	public void setCustodio(Integer custodio) {
		this.custodio = custodio;
	}

	/**
	 * @return the tipoPagoCAEV
	 */
	public Integer getTipoPagoCAEV() {
		return tipoPagoCAEV;
	}

	/**
	 * @param tipoPagoCAEV the tipoPagoCAEV to set
	 */
	public void setTipoPagoCAEV(Integer tipoPagoCAEV) {
		this.tipoPagoCAEV = tipoPagoCAEV;
	}

	/**
	 * @return the tipoPagoCAMV
	 */
	public Integer getTipoPagoCAMV() {
		return tipoPagoCAMV;
	}

	/**
	 * @param tipoPagoCAMV the tipoPagoCAMV to set
	 */
	public void setTipoPagoCAMV(Integer tipoPagoCAMV) {
		this.tipoPagoCAMV = tipoPagoCAMV;
	}

	/**
	 * @return the estado
	 */
	public Integer getEstado() {
		return estado;
	}

	/**
	 * @param estado the estado to set
	 */
	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	/**
	 * @return the isCalendarioIndeval
	 */
	public Boolean isCalendarioIndeval() {
		return calendarioIndeval;
	}

	/**
	 * @param isCalendarioIndeval the isCalendarioIndeval to set
	 */
	public void setCalendarioIndeval(Boolean calendarioIndeval) {
		this.calendarioIndeval = calendarioIndeval;
	}

	/**
	 * @return the isHojaDeTrabajo
	 */
	public Boolean isHojaDeTrabajo() {
		return hojaDeTrabajo;
	}

	/**
	 * @param isHojaDeTrabajo the isHojaDeTrabajo to set
	 */
	public void setHojaDeTrabajo(Boolean hojaDeTrabajo) {
		this.hojaDeTrabajo = hojaDeTrabajo;
	}

	/**
	 * @return the estadoMensajeria
	 */
	public Integer getEstadoMensajeria() {
		return estadoMensajeria;
	}

	/**
	 * @param estadoMensajeria the estadoMensajeria to set
	 */
	public void setEstadoMensajeria(String estadoMensajeria) {
		if(StringUtils.isNotEmpty(estadoMensajeria)){
			setEstadoMensajeria(Integer.valueOf(estadoMensajeria) );
		}
	}
	/**
	 * @param estadoMensajeria the estadoMensajeria to set
	 */
	public void setEstadoMensajeria(Integer estadoMensajeria) {
		this.estadoMensajeria = estadoMensajeria;
	}
	
}
