/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 * <p>
 * <p>
 * 27/02/2008
 */
package com.indeval.portaldali.middleware.dto;

import java.io.Serializable;

/**
 * Data Transfer Object que representa un elemento del catálogo de emisoras.
 *
 * @author Emigdio Hernández
 * @version 1.0
 *
 */
public class EmisoraDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /** El identificador de la emisora */
    private long id = 0;

    /** La descripción de la emisora */
    private String descripcion = null;

    /**
     * Obtiene el campo id
     * @return id
     */
    public long getId() {
        return id;
    }

    /**
     * Asigna el valor del campo id
     * @param id el valor de id a asignar
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Obtiene el campo descripcion
     * @return descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Asigna el valor del campo descripcion
     * @param descripcion el valor de descripcion a asignar
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "EmisoraDTO{" +
                "id=" + id +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}