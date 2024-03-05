/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.vo;

import java.math.BigInteger;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.indeval.portaldali.persistence.model.PosicionNombrada;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 *
 */
public class TPosicionParamsPersistence {
	
	/** Objeto de paginacion */
	private PageVO pageVO;
	
	/** bandera que define que se invoca desde un TestCase, se restringue la consulta */
	private boolean isTest;
	
    /**
     * Define el numero limite de elementos a recuperar cuando se invoca desde un TestCase
     * isTest = true 
     */
    public static final int LIMITE = 10; 
	
	/** 
	 * bandera que define si es una consulta ByExample 
	 *  true > se hace uso de tPosicionNombrada y se omite el resto de los atributos 
	 *  false > se omite tPosicionNombrada y se hace uso de los atributos que correspondan
	 *         dependiendo del valor del atributo consulta 
	 **/
	private boolean example;
	
	/** objeto utilizado unicamente cuando la bandera define que es una consulta ByExample 
	 *  example = true */
	private PosicionNombrada tPosicionNombrada;
	
	private String consulta; // helper para identificar los atributos que son obligatorios
	
	private String idInstitucion;
	
	private String folioInstitucion;
	
	private String[] cuentas;
	
	private String tipoCuenta; // PROP , GVALPREE , 
	
	private String[] tiposDeValor;
	
	private String emisora;
	
	private String serie;
	
	private String cupon;
	
	private String isin;
	
	private BigInteger idBoveda;
	
	/** inicia el rango de fecha emision a consultar */
	private Date fechaInicio;  
	
	/** finaliza el rango de fecha emision a consultar */
	private Date fechaFin;
	
	/** Permite filtrar las emisiones que existen en historica..edoctatr */
	private Date fechaMovimiento;
    
    /** Permite filtrar las emisiones por su fecha de vencimiento */
    private Date fechaVencimiento;
	
	private String mercado; // PD = in (PB, PG) / MC = in (MC)  / null incluye ambos mercados
	
	/** Cupon.CEstatusCupones.idEstatusCupon <= estatusCupon */
	private int estatusCupon ; // 1 = Vigente (cupon_cortado = F) / 2 = (cupon_cortado = C) / 4 = Baja (cupon_cortado = B)
	
	/** Define si se evalua emisiones extranjeras determinadas */ 
	private String emisionExtranjera;
	
	/** Bandera que define si se evaluan las historicas */
	private boolean historica;
	
	/**
	 * Constructor
	 */
	public TPosicionParamsPersistence() {
		this.setTest(false);
	}

	/**
	 * @return the consulta
	 */
	public String getConsulta() {
		return consulta;
	}

	/**
	 * @param consulta the consulta to set
	 */
	public void setConsulta(String consulta) {
		this.consulta = consulta;
	}

	/**
	 * @return the cuentas
	 */
	public String[] getCuentas() {
		return cuentas;
	}

	/**
	 * @param cuentas the cuentas to set
	 */
	public void setCuentas(String[] cuentas) {
		this.cuentas = cuentas;
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
	 * @return the estatusCupon
	 */
	public int getEstatusCupon() {
		return estatusCupon;
	}

	/**
	 * @param estatusCupon the estatusCupon to set
	 */
	public void setEstatusCupon(int estatusCupon) {
		this.estatusCupon = estatusCupon;
	}

	/**
	 * @return the example
	 */
	public boolean isExample() {
		return example;
	}

	/**
	 * @param example the example to set
	 */
	public void setExample(boolean example) {
		this.example = example;
	}

	/**
	 * @return the fechaFin
	 */
	public Date getFechaFin() {
		return fechaFin;
	}

	/**
	 * @param fechaFin the fechaFin to set
	 */
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	/**
	 * @return the fechaInicio
	 */
	public Date getFechaInicio() {
		return fechaInicio;
	}

	/**
	 * @param fechaInicio the fechaInicio to set
	 */
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	/**
	 * @return the folioInstitucion
	 */
	public String getFolioInstitucion() {
		return folioInstitucion;
	}

	/**
	 * @param folioInstitucion the folioInstitucion to set
	 */
	public void setFolioInstitucion(String folioInstitucion) {
		this.folioInstitucion = folioInstitucion;
	}

	/**
	 * @return the idInstitucion
	 */
	public String getIdInstitucion() {
		return idInstitucion;
	}

	/**
	 * @param idInstitucion the idInstitucion to set
	 */
	public void setIdInstitucion(String idInstitucion) {
		this.idInstitucion = idInstitucion;
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
	 * @return the isTest
	 */
	public boolean isTest() {
		return isTest;
	}

	/**
	 * @param isTest the isTest to set
	 */
	public void setTest(boolean isTest) {
		this.isTest = isTest;
	}

	/**
	 * @return the mercado
	 */
	public String getMercado() {
		return mercado;
	}

	/**
	 * @param mercado the mercado to set
	 */
	public void setMercado(String mercado) {
		this.mercado = mercado;
	}

	/**
	 * @return the pageVO
	 */
	public PageVO getPageVO() {
		return pageVO;
	}

	/**
	 * @param pageVO the pageVO to set
	 */
	public void setPageVO(PageVO pageVO) {
		this.pageVO = pageVO;
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
	 * @return the tipoCuenta
	 */
	public String getTipoCuenta() {
		return tipoCuenta;
	}

	/**
	 * @param tipoCuenta the tipoCuenta to set
	 */
	public void setTipoCuenta(String tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	}

	/**
	 * @return the tiposDeValor
	 */
	public String[] getTiposDeValor() {
		return tiposDeValor;
	}

	/**
	 * @param tiposDeValor the tiposDeValor to set
	 */
	public void setTiposDeValor(String[] tiposDeValor) {
		this.tiposDeValor = tiposDeValor;
	}

	/**
	 * @return the tPosicionNombrada
	 */
	public PosicionNombrada getTPosicionNombrada() {
		return tPosicionNombrada;
	}

	/**
	 * @param posicionNombrada the tPosicionNombrada to set
	 */
	public void setTPosicionNombrada(PosicionNombrada posicionNombrada) {
		tPosicionNombrada = posicionNombrada;
	}

	/**
	 * @return the fechaMovimiento
	 */
	public Date getFechaMovimiento() {
		return fechaMovimiento;
	}

	/**
	 * @param fechaMovimiento the fechaMovimiento to set
	 */
	public void setFechaMovimiento(Date fechaMovimiento) {
		this.fechaMovimiento = fechaMovimiento;
	}

	/**
	 * @return the historica
	 */
	public boolean isHistorica() {
		return historica;
	}

	/**
	 * @param historica the historica to set
	 */
	public void setHistorica(boolean historica) {
		this.historica = historica;
	}

    /**
     * @return the fechaVencimiento
     */
    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    /**
     * @param fechaVencimiento the fechaVencimiento to set
     */
    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    /**
     * @return the emisionExtranjera
     */
    public String getEmisionExtranjera() {
        return emisionExtranjera;
    }

    /**
     * @param emisionExtranjera the emisionExtranjera to set
     */
    public void setEmisionExtranjera(String emisionExtranjera) {
        this.emisionExtranjera = emisionExtranjera;
    }

    /**
     * @return the idBoveda
     */
    public BigInteger getIdBoveda() {
        return idBoveda;
    }

    /**
     * @param idBoveda the idBoveda to set
     */
    public void setIdBoveda(BigInteger idBoveda) {
        this.idBoveda = idBoveda;
    }

    
    @Override
    public String toString() {
    	return new ToStringBuilder(this).
	       append("estatusCupon", estatusCupon).
	       append("idInstitucion", idInstitucion).
	       append("folioInstitucion", folioInstitucion).
	       append("fechaVencimiento", fechaVencimiento).
	       append("idBoveda", idBoveda).
	       toString();
    }
}
