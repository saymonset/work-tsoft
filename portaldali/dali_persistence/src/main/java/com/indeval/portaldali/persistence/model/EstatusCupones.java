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
 @Table(name="C_ESTATUS_CUPONES")
public class EstatusCupones {
	 
    private BigInteger idEstatusCupon; // PK
    
    private String descEstatusCupon; 


     @Id
     @Column(name ="id_estatus_cupon", unique = true, nullable = false)
    public BigInteger getIdEstatusCupon() {
        return idEstatusCupon;
    }

     @Column(name ="desc_estatus_cupon", unique = true, nullable = false)
    public String getDescEstatusCupon() {
        return descEstatusCupon;
    }

    /**
     * @param idEstatusCupon
     */
    public void setIdEstatusCupon(BigInteger idEstatusCupon) {
        this.idEstatusCupon = idEstatusCupon;
    }

    /**
     * @param descEstatusCupon
     */
    public void setDescEstatusCupon(String descEstatusCupon) {
        this.descEstatusCupon = descEstatusCupon;
    }
    
}
