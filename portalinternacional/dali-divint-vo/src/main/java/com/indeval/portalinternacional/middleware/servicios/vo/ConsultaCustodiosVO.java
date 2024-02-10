/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.servicios.vo;

import java.io.Serializable;
import java.util.List;

import com.indeval.portalinternacional.middleware.servicios.modelo.CatBic;
import com.indeval.portalinternacional.middleware.servicios.modelo.SicDetalle;
import com.indeval.portalinternacional.middleware.servicios.modelo.SicEmision;

/**
 * @author <a href="mailto:ivan.kazakov@2hsoftware.com.mx">Ivan Kazakov</a>
 *
 */
public class ConsultaCustodiosVO implements Serializable {
	
	/**
	 * Constante de versi&oacute;n de serializaci&oacute;n 
	 */
	public static final long serialVersionUID = (long)1;
	
	/**
	 * CatBic 
	 */
	private CatBic catBic;
	
	/**
	 * List<SicDetalle> 
	 */
	private List<SicDetalle> sicDetalles;
	
	/**
	 * List<Emision>
	 */
	private List<SicEmision> sicEmisiones;

	/**
	 * String
	 */
	private String abreviacionCustodio;

    /**
     * String
     */
    private String nombreCorto;

    /**
     * String
     */
    private Integer factorCalculado;

    /**
     * Listado de tipos valor del custodio
     */
    List<String> tiposValorCustodio;

    /**
     * Listado de formatos del custodio
     */
    List<String> formatosCustodio;

	/**
	 * Constructor por omisi&oacute;n 
	 */
	public ConsultaCustodiosVO() {
		super();
	}
	
	/**
	 * Constructor parametrizado
	 * @param catBic
	 * @param sicDetalles
	 * @param sicEmisiones
	 */
	public ConsultaCustodiosVO(CatBic catBic, List<SicDetalle> sicDetalles, List<SicEmision> sicEmisiones) {
		super();
		this.catBic = catBic;
		this.sicDetalles = sicDetalles;
		this.sicEmisiones = sicEmisiones;
	}

    public ConsultaCustodiosVO(CatBic catBic, List<SicDetalle> sicDetalles, List<SicEmision> sicEmisiones, 
                               String abreviacionCustodio, List<String> tvsCustodio) {
        super();
        this.catBic = catBic;
        this.sicDetalles = sicDetalles;
        this.sicEmisiones = sicEmisiones;
        this.abreviacionCustodio = abreviacionCustodio;
        this.tiposValorCustodio = tvsCustodio;
    }

    public ConsultaCustodiosVO(CatBic catBic, List<SicDetalle> sicDetalles, List<SicEmision> sicEmisiones, 
                               String abreviacionCustodio, String nombrecorto, Integer factorCalculado,
                               List<String> tvsCustodio) {
        super();
        this.catBic = catBic;
        this.sicDetalles = sicDetalles;
        this.sicEmisiones = sicEmisiones;
        this.abreviacionCustodio = abreviacionCustodio;
        this.nombreCorto = nombrecorto;
        this.factorCalculado = factorCalculado;
        this.tiposValorCustodio = tvsCustodio;
    }
	
	/* Accessors and mutators */
	
	/**
	 * @return CatBic
	 */
	public CatBic getCatBic() {
		return catBic;
	}

	/**
	 * @param catBic
	 */
	public void setCatBic(CatBic catBic) {
		this.catBic = catBic;
	}

	/**
	 * @return List<SicDetalle>
	 */
	public List<SicDetalle> getSicDetalles() {
		return sicDetalles;
	}

	/**
	 * @param sicDetalles
	 */
	public void setSicDetalles(List<SicDetalle> sicDetalles) {
		this.sicDetalles = sicDetalles;
	}

	/**
	 * @return List<SicEmision>
	 */
	public List<SicEmision> getSicEmisiones() {
		return sicEmisiones;
	}

	/**
	 * @param emisiones
	 */
	public void setEmisiones(List<SicEmision> sicEmisiones) {
		this.sicEmisiones = sicEmisiones;
	}

    public String getAbreviacionCustodio() {
        return abreviacionCustodio;
    }

    public void setAbreviacionCustodio(String abreviacionCustodio) {
        this.abreviacionCustodio = abreviacionCustodio;
    }

    public String getNombreCorto() {
        return nombreCorto;
    }

    public void setNombreCorto(String nombreCorto) {
        this.nombreCorto = nombreCorto;
    }

    public Integer getFactorCalculado() {
        return factorCalculado;
    }

    public void setFactorCalculado(Integer factorCalculado) {
        this.factorCalculado = factorCalculado;
    }

    public List<String> getTiposValorCustodio() {
        return tiposValorCustodio;
    }

    public void setTiposValorCustodio(List<String> tiposValorCustodio) {
        this.tiposValorCustodio = tiposValorCustodio;
    }

    public List<String> getFormatosCustodio() {
        return formatosCustodio;
    }

    public void setFormatosCustodio(List<String> formatosCustodio) {
        this.formatosCustodio = formatosCustodio;
    }

}
