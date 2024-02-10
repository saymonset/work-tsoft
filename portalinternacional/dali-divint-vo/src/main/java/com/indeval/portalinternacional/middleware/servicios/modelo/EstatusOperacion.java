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

 @Entity
 @Table(name="C_ESTATUS_OPERACIONES")
 @SequenceGenerator(name = "foliador", sequenceName = "ID_ESTATUS_OPERACIONES_SEQ", allocationSize = 1, initialValue = 1)
public class EstatusOperacion implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idEstatusOperacion;
    private String descEstatusOperacion;
    private Date fechaHora;

    /**
     * Constructor de la clase
     */
    public EstatusOperacion() {}
    
    /**
     * Constructor de la clase
     */
    public EstatusOperacion(Long idEstatusOperacion) {
		this.idEstatusOperacion = idEstatusOperacion;
	}
    
     @Id
     @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foliador")
     @Column(name ="id_estatus_operacion", unique = true, nullable = false)
    public Long getIdEstatusOperacion() {
        return idEstatusOperacion;
    }

     
     @Column(name ="desc_estatus_operacion", unique = false, nullable = false)
    public String getDescEstatusOperacion() {
        return descEstatusOperacion;
    }

     
     @Column(name ="fecha_hora", unique = false, nullable = false)
    public Date getFechaHora() {
        return fechaHora;
    }


    public void setIdEstatusOperacion(Long idEstatusOperacion) {
        this.idEstatusOperacion = idEstatusOperacion;
    }

    public void setDescEstatusOperacion(String descEstatusOperacion) {
        this.descEstatusOperacion = descEstatusOperacion;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }
}
