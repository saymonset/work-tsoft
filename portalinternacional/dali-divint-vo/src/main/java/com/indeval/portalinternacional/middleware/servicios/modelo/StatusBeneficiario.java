/*
 * Copyrigth (c) 2008 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

 @Entity
 @Table(name="C_STATUS_BENEFICIARIOS")
public class StatusBeneficiario implements Serializable {

private static final long serialVersionUID = 1L;

    private Long idStatusBenef;
    private String descStatusBenef;


     @Id
     @Column(name ="id_status_benef", unique = true, nullable = false)
    public Long getIdStatusBenef() {
        return idStatusBenef;
    }

     @Column(name ="desc_status_benef", unique = false, nullable = true)
    public String getDescStatusBenef() {
        return descStatusBenef;
    }


    public void setIdStatusBenef(Long idStatusBenef) {
        this.idStatusBenef = idStatusBenef;
    }

    public void setDescStatusBenef(String descStatusBenef) {
        this.descStatusBenef = descStatusBenef;
    }
}
