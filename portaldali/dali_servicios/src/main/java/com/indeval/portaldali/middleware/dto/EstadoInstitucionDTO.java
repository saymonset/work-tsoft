/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.dto;

import java.io.Serializable;
import java.math.BigInteger;

 
 /**
  * 
  * @author Fernando vazquez ulloa 2009-11-12
  *
  */
public class EstadoInstitucionDTO implements Serializable {
	 
    private BigInteger idEstadoInstitucion; // PK
    
    private String claveEstadoInstitucion; 
    
    private String descripcion; 

     
     
    public BigInteger getIdEstadoInstitucion() {
        return idEstadoInstitucion;
    }

     
    public String getClaveEstadoInstitucion() {
        return claveEstadoInstitucion;
    }

     
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * 
     */
    public void setIdEstadoInstitucion(BigInteger idEstadoInstitucion) {
        this.idEstadoInstitucion = idEstadoInstitucion;
    }

    /**
     * 
     */
    public void setClaveEstadoInstitucion(String claveEstadoInstitucion) {
        this.claveEstadoInstitucion = claveEstadoInstitucion;
    }

    /**
     * 
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
}
