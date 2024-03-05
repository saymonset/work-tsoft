/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.model;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

 @Entity
 @Table(name="C_DIAS_INHABILES")
public class DiaInhabil {
	 
    private BigInteger idDiasInhabiles; // key?

	private Date diaInhabil; 

	private String aplicacion; 

	private String creador; 

	private Date fechaCreacion; 

	private Date fechaUltModificacion; 


     @Id
     @Column(name ="id_dias_inhabiles", unique = true, nullable = false)
    public BigInteger getIdDiasInhabiles() {
        return idDiasInhabiles;
    }

     @Column(name ="dia_inhabil", unique = true, nullable = false)
    public Date getDiaInhabil() {
        return diaInhabil;
    }

     @Column(name ="aplicacion", unique = true, nullable = false)
    public String getAplicacion() {
        return aplicacion;
    }

     @Column(name ="creador", unique = true, nullable = false)
    public String getCreador() {
        return creador;
    }

     @Column(name ="fecha_creacion", unique = true, nullable = false)
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

     @Column(name ="fecha_ult_modificacion", unique = true, nullable = false)
    public Date getFechaUltModificacion() {
        return fechaUltModificacion;
    }

    /**
     * @param idDiasInhabiles
     */
    public void setIdDiasInhabiles(BigInteger idDiasInhabiles) {
        this.idDiasInhabiles = idDiasInhabiles;
    }

    /**
     * @param diaInhabil
     */
    public void setDiaInhabil(Date diaInhabil) {
        this.diaInhabil = diaInhabil;
    }

    /**
     * @param aplicacion
     */
    public void setAplicacion(String aplicacion) {
        this.aplicacion = aplicacion;
    }

    /**
     * @param creador
     */
    public void setCreador(String creador) {
        this.creador = creador;
    }

    /**
     * @param fechaCreacion
     */
    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    /**
     * @param fechaUltModificacion
     */
    public void setFechaUltModificacion(Date fechaUltModificacion) {
        this.fechaUltModificacion = fechaUltModificacion;
    }
    
}
