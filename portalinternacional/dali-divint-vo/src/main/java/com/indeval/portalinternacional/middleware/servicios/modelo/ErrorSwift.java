/*
 * Copyrigth (c) 2008 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

 @Entity
 @Table(name="C_ERRORES_SWIFT")
 @SequenceGenerator(name = "foliador", sequenceName = "ID_ERRORES_SWIFT_SEQ", allocationSize = 1, initialValue = 1)
public class ErrorSwift implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idErrorSwift;
    private String codigoErrorSwift;
    private String descErrorSwift;
    private String aplicacion;
    private Date fechaHora;


     @Id
     @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
     @Column(name ="id_error_swift", unique = true, nullable = false)
    public Long getIdErrorSwift() {
        return idErrorSwift;
    }

     
     @Column(name ="codigo_error_swift", unique = true, nullable = false)
    public String getCodigoErrorSwift() {
        return codigoErrorSwift;
    }

     
     @Column(name ="desc_error_swift", unique = false, nullable = false)
    public String getDescErrorSwift() {
    	 
    	 if(null!=descErrorSwift){
    		 String errores[]=StringUtils.split(descErrorSwift, '\n');
    		 if(errores!=null && errores.length >0){
    			 return errores[0];
    		 }
    	 }    		 
    	 
        return descErrorSwift;
    }
     
     @Transient  
     public String getDescErrorSwiftEspa() {     	 
     	 if(null!=descErrorSwift){
     		 String errores[]=StringUtils.split(descErrorSwift, '\n');
     		 if(errores!=null && errores.length >1){
     			 return errores[1];
     		 }
     	 }    		     	 
         return null;
     }
      
      

     
     @Column(name ="aplicacion", unique = false, nullable = false)
    public String getAplicacion() {
        return aplicacion;
    }

     
     @Column(name ="fecha_hora", unique = false, nullable = false)
    public Date getFechaHora() {
        return fechaHora;
    }


    public void setIdErrorSwift(Long idErrorSwift) {
        this.idErrorSwift = idErrorSwift;
    }

    public void setCodigoErrorSwift(String codigoErrorSwift) {
        this.codigoErrorSwift = codigoErrorSwift;
    }

    public void setDescErrorSwift(String descErrorSwift) {
        this.descErrorSwift = descErrorSwift;
    }

    public void setAplicacion(String aplicacion) {
        this.aplicacion = aplicacion;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }
}
