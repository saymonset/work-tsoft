/*
 * Copyright (c) 2015 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

 /**
 * Mapeo tabla T_CAMPOS_FORMATO_W8IMY2015
 * 
 * @author amoralesm
 *
 */
@Entity
@Table(name="T_CAMPOS_FORMATO_W8IMY2015")
@SequenceGenerator(name = "foliador", sequenceName = "SEQ_CAMPOS_FORMATO_W8IMY2015", allocationSize = 1, initialValue = 1)
public class FormatoW8IMY2017 implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
    @Column(name ="ID_CAMPOS_FORMATO_W8IMY2015", unique = false, nullable = false)
	private Long idCamposFormato;
	
    /**
     * Archivo que contiene el vector de precios
     */
    @Lob
    @Column(name = "CAMPOS_FORMATO")
    private String camposFormato;

    /** US TIN */
    @Column(name="US_TIN")
    private String usTIN;
    
    /** Reference Number */
    @Column(name="REFERENCE_NUMBER")
    private String referenceNumber;
    
    /** GIIN */
    @Column(name="GIIN")
    private String giin;

    /** PRINT NAME */
    @Column(name="PRINT_NAME")
    private String printName;

	/**
	 * Constructor principal
	 */
	public FormatoW8IMY2017() {
	}

	/**
	 * Constructor de la clase
	 * 
	 * @param idCamposFormato
	 * @param camposFormato
	 * @param usTIN
	 * @param referenceNumber
	 * @param giin
	 * @param printName
	 */
	public FormatoW8IMY2017(Long idCamposFormato, String camposFormato, String usTIN, String referenceNumber,
			String giin, String printName) {
		this.idCamposFormato = idCamposFormato;
		this.camposFormato = camposFormato;
		this.usTIN = usTIN;
		this.referenceNumber = referenceNumber;
		this.giin = giin;
		this.printName = printName;
	}
    
	/**
	 * @return the idCamposFormato
	 */
	public Long getIdCamposFormato() {
		return idCamposFormato;
	}

	/**
	 * @param idCamposFormato the idCamposFormato to set
	 */
	public void setIdCamposFormato(Long idCamposFormato) {
		this.idCamposFormato = idCamposFormato;
	}

	/**
	 * @return the camposFormato
	 */
	public String getCamposFormato() {
		return camposFormato;
	}

	/**
	 * @param camposFormato the camposFormato to set
	 */
	public void setCamposFormato(String camposFormato) {
		this.camposFormato = camposFormato;
	}

	@Override
    public int hashCode() {
        return new HashCodeBuilder(13, 23).append(idCamposFormato).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return true;
        }
        if(obj instanceof FormatoW8IMY2017){
        	return ((FormatoW8IMY2017) obj).getIdCamposFormato().compareTo(idCamposFormato)==0;
        } else {
        	return false;
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("Id campos formato", idCamposFormato)
                .append("contenido", camposFormato)
                .toString();
    }

	/**
	 * Método para obtener el atributo usTIN
	 * @return El atributo usTIN
	 */
	public String getUsTIN() {
		return usTIN;
	}

	/**
	 * Método para establecer el atributo usTIN
	 * @param usTIN El valor del atributo usTIN a establecer.
	 */
	public void setUsTIN(String usTIN) {
		this.usTIN = usTIN;
	}

	/**
	 * Método para obtener el atributo referenceNumber
	 * @return El atributo referenceNumber
	 */
	public String getReferenceNumber() {
		return referenceNumber;
	}

	/**
	 * Método para establecer el atributo referenceNumber
	 * @param referenceNumber El valor del atributo referenceNumber a establecer.
	 */
	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	/**
	 * @return the giin
	 */
	public String getGiin() {
		return giin;
	}

	/**
	 * @param giin the giin to set
	 */
	public void setGiin(String giin) {
		this.giin = giin;
	}

	/**
	 * @return the printName
	 */
	public String getPrintName() {
		return printName;
	}

	/**
	 * @param printName the printName to set
	 */
	public void setPrintName(String printName) {
		this.printName = printName;
	}

}
