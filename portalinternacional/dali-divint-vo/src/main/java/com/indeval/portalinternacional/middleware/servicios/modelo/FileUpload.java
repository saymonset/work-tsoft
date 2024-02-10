/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

 @Entity
 @Table(name="T_FILE_UPLOAD")
public class FileUpload {
    private String idProceso; //PK
    private String usuario;
    private BigDecimal porcentaje;
    private String status;
    private char abort;


     @Id
     @Column(name ="id_proceso", unique = true, nullable = false)
    public String getIdProceso() {
        return idProceso;
    }

     @Column(name ="usuario", unique = false, nullable = true)
    public String getUsuario() {
        return usuario;
    }

     @Column(name ="porcentaje", unique = false, nullable = true)
    public BigDecimal getPorcentaje() {
        return porcentaje;
    }

     @Column(name ="status", unique = false, nullable = true)
    public String getStatus() {
        return status;
    }

     @Column(name ="abort", unique = false, nullable = true)
    public char getAbort() {
        return abort;
    }


    public void setIdProceso(String idProceso) {
        this.idProceso = idProceso;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setPorcentaje(BigDecimal porcentaje) {
        this.porcentaje = porcentaje;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setAbort(char abort) {
        this.abort = abort;
    }
    
    @Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) {return false;}
		if(obj == this) {return true;}
		if(!(obj instanceof FileUpload)) {return false;}

		FileUpload other=(FileUpload)obj;
		
		boolean isEquals = false;

		isEquals=EqualsBuilder.reflectionEquals(this, other);
		
		return isEquals;
	}

}
