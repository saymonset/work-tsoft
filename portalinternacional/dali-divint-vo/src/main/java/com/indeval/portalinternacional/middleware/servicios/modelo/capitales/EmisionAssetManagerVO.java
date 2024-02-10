package com.indeval.portalinternacional.middleware.servicios.modelo.capitales;

import java.io.Serializable;

public class EmisionAssetManagerVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long idEmision;
			
	private String tipoValor;
	
	private String emisora;
	
	private String serie;
	
	private String isin;
	
	private String custodioDesc;
	
	private String estadoEmision;
	
	private Long idAssetManager;
	
	private String assetManagerDesc;

	public EmisionAssetManagerVO() {
		super();	
	}

	/**
	 * @return the idEmision
	 */
	public Long getIdEmision() {
		return idEmision;
	}

	/**
	 * @param idEmision the idEmision to set
	 */
	public void setIdEmision(Long idEmision) {
		this.idEmision = idEmision;
	}

	/**
	 * @return the tipoValor
	 */
	public String getTipoValor() {
		return tipoValor;
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
	 * @return the custodioDesc
	 */
	public String getCustodioDesc() {
		return custodioDesc;
	}

	/**
	 * @param custodioDesc the custodioDesc to set
	 */
	public void setCustodioDesc(String custodioDesc) {
		this.custodioDesc = custodioDesc;
	}

	/**
	 * @return the estadoEmision
	 */
	public String getEstadoEmision() {
		return estadoEmision;
	}

	/**
	 * @param estadoEmision the estadoEmision to set
	 */
	public void setEstadoEmision(String estadoEmision) {
		this.estadoEmision = estadoEmision;
	}

	/**
	 * @return the idAssetManager
	 */
	public Long getIdAssetManager() {
		return idAssetManager;
	}

	/**
	 * @param idAssetManager the idAssetManager to set
	 */
	public void setIdAssetManager(Long idAssetManager) {
		this.idAssetManager = idAssetManager;
	}

	/**
	 * @return the assetManagerDesc
	 */
	public String getAssetManagerDesc() {
		return assetManagerDesc;
	}

	/**
	 * @param assetManagerDesc the assetManagerDesc to set
	 */
	public void setAssetManagerDesc(String assetManagerDesc) {
		this.assetManagerDesc = assetManagerDesc;
	}
	
	

}
