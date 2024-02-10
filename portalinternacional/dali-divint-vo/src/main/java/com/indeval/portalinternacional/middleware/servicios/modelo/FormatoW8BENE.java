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
 * Mapeo tabla T_CAMPOS_FORMATO_W8BENE
 * 
 * @author amoralesm
 *
 */
@Entity
@Table(name="T_CAMPOS_FORMATO_W8BENE")
@SequenceGenerator(name = "foliador", sequenceName = "SEQ_CAMPOS_FORMATO_W8BENE", allocationSize = 1, initialValue = 1)
public class FormatoW8BENE implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
    @Column(name ="ID_CAMPOS_FORMATO_W8BENE", unique = false, nullable = false)
	private Long idCamposFormatoW8bene;
	
    /**
     * Archivo que contiene el vector de precios
     */
    @Lob
    @Column(name = "CAMPOS_FORMATO")
    private String camposFormato;
    
    /** US TIN */
    @Column(name="US_TIN")
    private String usTIN;
    
    /** Foreing TIN */
    @Column(name="FOREING_TIN")
    private String foreingTIN;
    
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
	public FormatoW8BENE() {}
    
	/**
	 * Constructor con atributos
	 * 
	 * @param idCamposFormatoW8bene
	 * @param camposFormato
	 */
	public FormatoW8BENE(Long idCamposFormatoW8bene, String camposFormato) {
		this.idCamposFormatoW8bene = idCamposFormatoW8bene;
		this.camposFormato = camposFormato;
	}

	/**
	 * Constructor de la clase
	 * @param idCamposFormatoW8bene Id del registro
	 * @param camposFormato Cadena con los campos en XML
	 * @param usTIN US TIN
	 * @param foreingTIN Foreing TIN
	 * @param referenceNumber Número de referencia
	 * @param giin GIIN
	 * @param printName persona que captura el Beneficiario
	 */
	public FormatoW8BENE(Long idCamposFormatoW8bene, String camposFormato, String usTIN,
			String foreingTIN, String referenceNumber, String giin, String printName) {
		this.idCamposFormatoW8bene = idCamposFormatoW8bene;
		this.camposFormato = camposFormato;
		this.usTIN = usTIN;
		this.foreingTIN = foreingTIN;
		this.referenceNumber = referenceNumber;
		this.giin = giin;
		this.printName = printName;
	}

	@Override
    public int hashCode() {
        return new HashCodeBuilder(13, 23).append(idCamposFormatoW8bene).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return true;
        }
        if(obj instanceof FormatoW8BENE){
        	return ((FormatoW8BENE) obj).getIdCamposFormatoW8bene().compareTo(idCamposFormatoW8bene)==0;
        } else {
        	return false;
        }
    }
    
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("Id campos formato", idCamposFormatoW8bene)
                .append("contenido", camposFormato)
                .toString();
    }

	/**
	 * Método para obtener el atributo idCamposFormatoW8bene
	 * @return El atributo idCamposFormatoW8bene
	 */
	public Long getIdCamposFormatoW8bene() {
		return idCamposFormatoW8bene;
	}

	/**
	 * Método para establecer el atributo idCamposFormatoW8bene
	 * @param idCamposFormatoW8bene El valor del atributo idCamposFormatoW8bene a establecer.
	 */
	public void setIdCamposFormatoW8bene(Long idCamposFormatoW8bene) {
		this.idCamposFormatoW8bene = idCamposFormatoW8bene;
	}

	/**
	 * Método para obtener el atributo camposFormato
	 * @return El atributo camposFormato
	 */
	public String getCamposFormato() {
		return camposFormato;
	}

	/**
	 * Método para establecer el atributo camposFormato
	 * @param camposFormato El valor del atributo camposFormato a establecer.
	 */
	public void setCamposFormato(String camposFormato) {
		this.camposFormato = camposFormato;
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
	 * Método para obtener el atributo foreingTIN
	 * @return El atributo foreingTIN
	 */
	public String getForeingTIN() {
		return foreingTIN;
	}

	/**
	 * Método para establecer el atributo foreingTIN
	 * @param foreingTIN El valor del atributo foreingTIN a establecer.
	 */
	public void setForeingTIN(String foreingTIN) {
		this.foreingTIN = foreingTIN;
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
