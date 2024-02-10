/**
 * Copyright (c) 2017 Bursatec. All Rights Reserved
 */
package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Clase que representa un registro de la tabla 'C_BOVEDA'.
 */
@Entity
@Table(name = "C_BOVEDA")
public class Bovedas implements Serializable {
  
    /** Serial Version UID */
    private static final long serialVersionUID = -4649889062292483751L;

    /**
     * Propiedad que representa el id de la b&oacute;veda
     */
    @Id
    @Column(name = "ID_BOVEDA", unique = true, nullable = false)
    private Integer idBoveda;
    
    /**
     * Propiedad que representa el tipo de b&oacute;veda
     */
    @Column(name = "ID_TIPO_BOVEDA", unique = false, nullable = false)
    private Integer idTipoBoveda;
    
    /**
     * Propiedad que representa el nombre corto de la  b&oacute;veda
     */
    @Column(name = "NOMBRE_CORTO", unique = false, nullable = false)
    private String nombreCorto;

    /**
     * Propiedad que representa la descripci&oacute;n de la  b&oacute;veda
     */
    @Column(name = "DESCRIPCION", unique = false, nullable = false)
    private String descripcion;
    
    /**
     * Propiedad que representa el tipo de b&oacute;veda
     */
    @Column(name = "ID_CUENTA_BOVEDA", unique = false, nullable = false)
    private Integer idCuentaBoveda;   

    /**
     * Constructor para TiposPapel.
     */
    public Bovedas() {
    }

    /**
     * Constructor para TiposPapel con primary key.
     * @param idBoveda
     */
    public Bovedas(Integer idBoveda){
        this.idBoveda = idBoveda;
    }
  

    /**
     * Obtiene el valor del atributo descripcion.
     * @return String
     */
    public String getDescripcion(){
        return descripcion;
    }
    
    /**
     * Establece el valor del atributo descripcion.
     * @param descripcion
     */
    public void setDescripcion(String descripcion){
        this.descripcion = descripcion;
    }
    
    /**
     * Obtiene el valor del atributo idBoveda.
     * @return Integer
     */
    public Integer getIdBoveda(){
        return idBoveda;
    }
    
    /**
     * Establece el valor del atributo idBoveda.
     * @param idBoveda
     */
    public void setIdBoveda(Integer idBoveda){
        this.idBoveda = idBoveda;
    }

    /**
     * Implementation of the equals comparison on the basis of equality of the
     * primary key values.
     * 
     * @param rhs
     * @return boolean
     */
    public boolean equals(Object rhs){
        if (rhs == null){
            return false;
        }
        if (!(rhs instanceof Bovedas)){
            return false;
        }
        Bovedas that = (Bovedas) rhs;
        if (this.getIdBoveda() == null || that.getIdBoveda() == null){
            return false;
        }
        return (this.getIdBoveda().equals(that.getIdBoveda()));
    }

    /**
     * @return Regresa el valor de idCuentaBoveda.
     */
    public Integer getIdCuentaBoveda() {
        return idCuentaBoveda;
    }

    /**
     * Establece el valor de idCuentaBoveda
     * @param idCuentaBoveda el valor de idCuentaBoveda a establecer.
     */
    public void setIdCuentaBoveda(Integer idCuentaBoveda) {
        this.idCuentaBoveda = idCuentaBoveda;
    }

    /**
     * @return Regresa el valor de nombreCorto.
     */
    public String getNombreCorto() {
        return nombreCorto;
    }

    /**
     * Establece el valor de nombreCorto
     * @param nombreCorto el valor de nombreCorto a establecer.
     */
    public void setNombreCorto(String nombreCorto) {
        this.nombreCorto = nombreCorto;
    }

    public Integer getIdTipoBoveda() {
        return idTipoBoveda;
    }

    public void setIdTipoBoveda(Integer idTipoBoveda) {
        this.idTipoBoveda = idTipoBoveda;
    }

}
