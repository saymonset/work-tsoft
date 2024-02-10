package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.util.Date;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("derecho")
public class DerechoMensaje {
	
	@XStreamAlias("id") 
    @XStreamAsAttribute
	private Long id;
	
	 @XStreamAlias("emisora") 
     @XStreamAsAttribute
	private String emisora;
	 
	 @XStreamAlias("serie") 
     @XStreamAsAttribute 
	private String serie;
	 
	 @XStreamAlias("cupon") 
     @XStreamAsAttribute
	private String cupon;
	 
	 @XStreamAlias("isin") 
     @XStreamAsAttribute
	private String isin;
	 
	 @XStreamAlias("fechaCorte")     
	private Date fechaCorte;
	 
	 @XStreamAlias("fechaPago")     
	private Date fechaPago;

	/**
	 * @return the emision
	 */
	public String getEmisora() {
		return emisora;
	}

	/**
	 * @param emision the emision to set
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
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
}