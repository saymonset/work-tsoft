/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
 
 @Entity
 @Table(name="C_TIPO_RETIRO")
public class TipoRetiro {
	 
    private Integer idTipoRetiro;
    
    private String descripcion;
    
    private String nombreCorto;
    
    
    @Column(name ="NOMBRE_CORTO", nullable = false)
    public String getNombreCorto() {
		return nombreCorto;
	}

	public void setNombreCorto(String nombreCorto) {
		this.nombreCorto = nombreCorto;
	}

	@Column(name ="DESCRIPCION", nullable = false)
    public String getDescripcion() {
		return descripcion;
	}
    
    @Id
	@Column(name ="ID_TIPO_RETIRO", unique = true, nullable = false)    
	public Integer getIdTipoRetiro() {
		return idTipoRetiro;
	}


	public void setIdTipoRetiro(Integer idTipoRetiro) {
		this.idTipoRetiro = idTipoRetiro;
	}
	
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}	
    
}
