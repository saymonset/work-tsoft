/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Cat&aacute;logo de emisoras a las que puede pertenecer una emisión.
 * 
 * @author SFLORES
 */
@Entity
@Table(name = "C_EMISORA")
public class Emisora implements Serializable {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	 @Id
     @Column(name ="id_emisora", unique = true, nullable = false)
	private BigInteger idEmisora;
     @Column(name ="clave_pizarra", unique = true, nullable = false,insertable=false,updatable=false)
	private String descripcion;
     @Column(name ="clave_pizarra", unique = true, nullable = false,insertable=false,updatable=false)
	private String descEmisora; 
	 @Column(name ="clave_pizarra", unique = true, nullable = false)
	private String clavePizarra; 
     @Column(name ="razon_social", unique = true, nullable = false)
	private String razonSocial; 
     @ManyToOne(fetch = FetchType.LAZY)
 	 @JoinColumn(name = "id_institucion")
	private Institucion institucion; // FK
     @Column(name ="id_tipo_emisora", unique = false, nullable = true)
	private BigInteger idTipoEmisora; 
     @Column(name ="clave", unique = true, nullable = false)
	private String clave; 
     @Column(name ="consecutivo", unique = false, nullable = true)
	private String consecutivo;
     @Column(name ="fecha_creacion", unique = true, nullable = false)
	private Date fechaCreacion; 
     @Column(name ="fecha_ult_modificacion", unique = true, nullable = false)
	private Date fechaUltModificacion; 
     @Column(name ="estatus_emisora", unique = true, nullable = false)
	private BigDecimal estatusEmisora; 

	/**
     * Identificador de la emisora.
     */

    public BigInteger getIdEmisora() {
        return idEmisora;
    }

     /**
      * Descripci&oacute;n de la emisora.
      */

    public String getDescEmisora() {
        return descEmisora;
    }


    public String getClavePizarra() {
        return clavePizarra;
    }

     
    public String getRazonSocial() {
        return razonSocial;
    }

  
    public Institucion getInstitucion() {
        return institucion;
    }

     
    public BigInteger getIdTipoEmisora() {
        return idTipoEmisora;
    }

    
    public String getClave() {
        return clave;
    }

    
    public String getConsecutivo() {
        return consecutivo;
    }

     
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

     
    public Date getFechaUltModificacion() {
        return fechaUltModificacion;
    }

     
    public BigDecimal getEstatusEmisora() {
        return estatusEmisora;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @param idEmisora the idEmisora to set
     */
    public void setIdEmisora(BigInteger idEmisora) {
        this.idEmisora = idEmisora;
    }

    /**
     * @param descEmisora the descEmisora to set
     */
    public void setDescEmisora(String descEmisora) {
        this.descEmisora = descEmisora;
    }

    /**
     * @param clavePizarra the clavePizarra to set
     */
    public void setClavePizarra(String clavePizarra) {
        this.clavePizarra = clavePizarra;
    }

    /**
     * @param razonSocial the razonSocial to set
     */
    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    /**
     * @param institucion the institucion to set
     */
    public void setInstitucion(Institucion institucion) {
        this.institucion = institucion;
    }

    /**
     * @param idTipoEmisora the idTipoEmisora to set
     */
    public void setIdTipoEmisora(BigInteger idTipoEmisora) {
        this.idTipoEmisora = idTipoEmisora;
    }

    /**
     * @param clave the clave to set
     */
    public void setClave(String clave) {
        this.clave = clave;
    }

    /**
     * @param consecutivo the consecutivo to set
     */
    public void setConsecutivo(String consecutivo) {
        this.consecutivo = consecutivo;
    }

    /**
     * @param fechaCreacion the fechaCreacion to set
     */
    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    /**
     * @param fechaUltModificacion the fechaUltModificacion to set
     */
    public void setFechaUltModificacion(Date fechaUltModificacion) {
        this.fechaUltModificacion = fechaUltModificacion;
    }

    /**
     * @param estatusEmisora the estatusEmisora to set
     */
    public void setEstatusEmisora(BigDecimal estatusEmisora) {
        this.estatusEmisora = estatusEmisora;
    }
     
}
