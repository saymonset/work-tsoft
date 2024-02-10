package com.indeval.portalinternacional.middleware.servicios.dto;

import java.io.Serializable;

public class CustodiosDepositantesDto implements Serializable {

	/** Id para la serializacion */
	private static final long serialVersionUID = -8168058611529545458L;
	
	/** tipo valor */
    private String tv;
    
	/** emisora */
    private String emisora;

    /** serie */
    private String serie;

    /** cupon */
    private String cupon;
    
    /** isin */
    private String isin;
    
    /** bicProd */
    private String bicProd;
    
    /** detalle custodio */
    private String detalleCustodio;
    
    /** estatus Registro */
    private String estatusRegistro;
    
    
    /**
     * Constructor por defecto
     */
    public CustodiosDepositantesDto() {}

    
    
	/**
	 * Obtiene el valor del atributo tv.
	 * @return el valor del atributo tv.
	 */  
	public String getTv() {
		return tv;
	}

	/**
	 * Establece el valor del atributo tv. 
	 * @param tv el valor del atributo tv a establecer.
	 */
	public void setTv(String tv) {
		this.tv = tv;
	}

	/**
	 * Obtiene el valor del atributo emisora.
	 * @return el valor del atributo emisora.
	 */
	public String getEmisora() {
		return emisora;
	}

	/**
	 * Establece el valor del atributo emisora. 
	 * @param emisora el valor del atributo emisora a establecer.
	 */
	public void setEmisora(String emisora) {
		this.emisora = emisora;
	}

	/**
	 * Obtiene el valor del atributo serie.
	 * @return el valor del atributo serie.
	 */
	public String getSerie() {
		return serie;
	}

	/**
	 * Establece el valor del atributo serie. 
	 * @param serie el valor del atributo serie a establecer.
	 */
	public void setSerie(String serie) {
		this.serie = serie;
	}

	/**
	 * Obtiene el valor del atributo cupon.
	 * @return el valor del atributo cupon.
	 */
	public String getCupon() {
		return cupon;
	}

	/**
	 * Establece el valor del atributo cupon. 
	 * @param cupon el valor del atributo cupon a establecer.
	 */
	public void setCupon(String cupon) {
		this.cupon = cupon;
	}

	/**
	 * Obtiene el valor del atributo isin.
	 * @return el valor del atributo isin.
	 */
	public String getIsin() {
		return isin;
	}

	/**
	 * Establece el valor del atributo isin. 
	 * @param isin el valor del atributo isin a establecer.
	 */
	public void setIsin(String isin) {
		this.isin = isin;
	}

	/**
	 * Obtiene el valor del atributo bicProd.
	 * @return el valor del atributo bicProd.
	 */
	public String getBicProd() {
		return bicProd;
	}

	/**
	 * Establece el valor del atributo bicProd. 
	 * @param bicProd el valor del atributo bicProd a establecer.
	 */
	public void setBicProd(String bicProd) {
		this.bicProd = bicProd;
	}

	/**
	 * Obtiene el valor del atributo detalleCustodio.
	 * @return el valor del atributo detalleCustodio.
	 */
	public String getDetalleCustodio() {
		return detalleCustodio;
	}

	/**
	 * Establece el valor del atributo detalleCustodio. 
	 * @param detalleCustodio el valor del atributo detalleCustodio a establecer.
	 */
	public void setDetalleCustodio(String detalleCustodio) {
		this.detalleCustodio = detalleCustodio;
	}

	/**
	 * Obtiene el valor del atributo estatusRegistro.
	 * @return el valor del atributo estatusRegistro.
	 */
	public String getEstatusRegistro() {
		return estatusRegistro;
	}

	/**
	 * Establece el valor del atributo estatusRegistro. 
	 * @param estatusRegistro el valor del atributo estatusRegistro a establecer.
	 */
	public void setEstatusRegistro(String estatusRegistro) {
		this.estatusRegistro = estatusRegistro;
	}



	@Override
	public String toString() {
		return "CustodiosDepositantesDto [tv=" + tv + ", emisora=" + emisora + ", serie=" + serie + ", cupon=" + cupon
				+ ", isin=" + isin + ", bicProd=" + bicProd + ", detalleCustodio=" + detalleCustodio
				+ ", estatusRegistro=" + estatusRegistro + "]";
	}
	
	
    
    
    

}
