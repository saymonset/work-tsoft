/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.servicios.vo;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO;

/**
 * @author <a href="mailto:ivan.kazakov@2hsoftware.com.mx">Ivan Kazakov</a>
 *
 */
public class AltaCustodioVO implements Serializable {
	
	/**
	 * Constante de versi&oacute;n de serializaci&oacute;n 
	 */
	public static final long serialVersionUID = (long)1;
	
	/**
	 * AgenteVO 
	 */
	private AgenteVO custodio;
	
	/**
	 * String 
	 */
	private String produccion;
	
	/**
	 * String 
	 */
	private String entrenamiento;
	
	/**
	 * String 
	 */
	private String cuentaIndeval;
	
	/**
	 * String 
	 */
	private String pais;
	
	/**
	 * String 
	 */
	private String mercado;
    
    /**
     * String 
     */
    private String abreviacion;
	
	/**
	 * String 
	 */
	private String estatus;
	
	/**
	 * String 
	 */
	private String moneda;
	
	/** String **/
	private String detalleCustodio;
    
    /**
     * String 
     */
    private String nombreCorto;
    
    /**
     * String 
     */
    private Integer factorCalculado;

    /**
     * Listado de tipos valor de custodio.
     */
    private List<String> listaTiposValorCustodio;

    /**
     * Listado de formatos de custodio.
     */
    private List<String> listaFormatosCustodio;

	/**
	 * Realiza la validaci&oacute;n del objeto
	 * @return String
	 */
	public String validate() {
		StringBuilder stringBuilder = new StringBuilder();
		if (custodio == null) {
			stringBuilder.append("custodio, ");
		}
		else {
			if (StringUtils.isBlank(custodio.getId())) {
				stringBuilder.append("custodio.id, ");
			}
			if (StringUtils.isBlank(custodio.getFolio())) {
				stringBuilder.append("custodio.folio, ");
			}
			if (StringUtils.isBlank(custodio.getCuenta())) {
				stringBuilder.append("custodio.cuenta, ");
			}
		}
		if (StringUtils.isBlank(produccion)) {
			stringBuilder.append("produccion, ");
		}
//		if (StringUtils.isBlank(entrenamiento)) {
//			stringBuilder.append("entrenamiento, ");
//		}
		if (StringUtils.isBlank(cuentaIndeval)) {
			stringBuilder.append("cuentaIndeval, ");
		}
		if (StringUtils.isBlank(pais)) {
			stringBuilder.append("pais, ");
		}
		if (StringUtils.isBlank(mercado)) {
			stringBuilder.append("mercado, ");
		}
        if (StringUtils.isBlank(abreviacion)) {
            stringBuilder.append("abreviacion, ");
        }
        if (StringUtils.isBlank(nombreCorto)) {
            stringBuilder.append("nombreCorto, ");
        }
        if (factorCalculado == null || factorCalculado.compareTo(0) < 0) {
            stringBuilder.append("factorCalculado, ");
        }
		if (StringUtils.isBlank(estatus)) {
			stringBuilder.append("estatus, ");
		}
        if (StringUtils.isBlank(detalleCustodio)) {
            stringBuilder.append("detalleCustodio, ");
        }
		if (StringUtils.isBlank(moneda)) {
			stringBuilder.append("moneda, ");
		}
		if (stringBuilder.length() == 0) {
			return (null);
		}
		stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
		return (stringBuilder.toString());
	}
	
	/* Accessors and mutators */
	
	/**
	 * @return AgenteVO
	 */
	public AgenteVO getCustodio() {
		return custodio;
	}
	
	/**
	 * @param custodio
	 */
	public void setCustodio(AgenteVO custodio) {
		this.custodio = custodio;
	}
	
	/**
	 * @return String
	 */
	public String getProduccion() {
		return produccion;
	}
	
	/**
	 * @param produccion
	 */
	public void setProduccion(String produccion) {
		this.produccion = produccion;
	}
	
	/**
	 * @return String
	 */
	public String getEntrenamiento() {
		return entrenamiento;
	}
	
	/**
	 * @param entrenamiento
	 */
	public void setEntrenamiento(String entrenamiento) {
		this.entrenamiento = entrenamiento;
	}
	
	/**
	 * @return String
	 */
	public String getCuentaIndeval() {
		return cuentaIndeval;
	}
	
	/**
	 * @param cuentaIndeval
	 */
	public void setCuentaIndeval(String cuentaIndeval) {
		this.cuentaIndeval = cuentaIndeval;
	}
	
	/**
	 * @return
	 */
	public String getPais() {
		return pais;
	}
	
	/**
	 * @param pais
	 */
	public void setPais(String pais) {
		this.pais = pais;
	}
	
	/**
	 * @return String
	 */
	public String getMercado() {
		return mercado;
	}
	
	/**
	 * @param mercado
	 */
	public void setMercado(String mercado) {
		this.mercado = mercado;
	}
    
    /**
     * @return String
     */
    public String getAbreviacion() {
        return abreviacion;
    }
    
    /**
     * @param abreviacion
     */
    public void setAbreviacion(String abreviacion) {
        this.abreviacion = abreviacion;
    }
	
	/**
	 * @return String
	 */
	public String getEstatus() {
		return estatus;
	}
	
	/**
	 * @param estatus
	 */
	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}
	
	/**
	 * @return String
	 */
	public String getMoneda() {
		return moneda;
	}
	
	/**
	 * @param moneda
	 */
	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	/**
	 * @return the detalleCustodio
	 */
	public String getDetalleCustodio() {
		return detalleCustodio;
	}

	/**
	 * @param detalleCustodio the detalleCustodio to set
	 */
	public void setDetalleCustodio(String detalleCustodio) {
		this.detalleCustodio = detalleCustodio;
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

    public List<String> getListaTiposValorCustodio() {
        return listaTiposValorCustodio;
    }

    public void setListaTiposValorCustodio(List<String> listaTiposValorCustodio) {
        this.listaTiposValorCustodio = listaTiposValorCustodio;
    }

    public List<String> getListaFormatosCustodio() {
        return listaFormatosCustodio;
    }

    public void setListaFormatosCustodio(List<String> listaFormatosCustodio) {
        this.listaFormatosCustodio = listaFormatosCustodio;
    }

}
