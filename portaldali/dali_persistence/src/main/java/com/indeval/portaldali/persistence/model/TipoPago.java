/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
 
 @Entity
 @Table(name="C_TIPO_PAGO")
public class TipoPago {
	 
    private Integer idTipoPago;    
    private String descripcion; 
    
   
    @Column(name ="DESCRIPCION", nullable = false)
    public String getDescripcion() {
		return descripcion;
	}
	@Id
	@Column(name ="ID_TIPO_PAGO", unique = true, nullable = false)
	public Integer getIdTipoPago() {
		return idTipoPago;
	}
	
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public void setIdTipoPago(Integer idTipoPago) {
		this.idTipoPago = idTipoPago;
	}
	
	
	
    
}
