/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.model;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

 @Entity
 @Table(name="C_ESTADO_INSTITUCION")
public class EstadoInstitucion {
	 
    private BigInteger idEstadoInstitucion; // PK
    
    private String claveEstadoInstitucion; 
    
    private String descripcion; 

     @Id
     @Column(name ="id_estado_institucion", unique = true, nullable = false)
    public BigInteger getIdEstadoInstitucion() {
        return idEstadoInstitucion;
    }

     @Column(name ="clave_estado_institucion", unique = true, nullable = false)
    public String getClaveEstadoInstitucion() {
        return claveEstadoInstitucion;
    }

     @Column(name ="descripcion", unique = true, nullable = false)
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param idEstadoInstitucion
     */
    public void setIdEstadoInstitucion(BigInteger idEstadoInstitucion) {
        this.idEstadoInstitucion = idEstadoInstitucion;
    }

    /**
     * @param claveEstadoInstitucion
     */
    public void setClaveEstadoInstitucion(String claveEstadoInstitucion) {
        this.claveEstadoInstitucion = claveEstadoInstitucion;
    }

    /**
     * @param descripcion
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
}
