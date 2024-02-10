package com.indeval.portalinternacional.middleware.servicios.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


@Entity
@Table(name = "C_TIPO_FORMATO")
public class TipoFormatoBeneficiario implements Serializable {
	
	/**
	 * Constante de serializacion por default 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Long idTipoFormatoBene;
	
	private String descripcionTipoFormato;

	@Id
	@Column(name = "ID_TIPO_FORMATO", unique = true, nullable = false)
	public Long getIdTipoFormatoBene() {
		return idTipoFormatoBene;
	}
	
	
	public void setIdTipoFormatoBene(Long idTipoFormatoBene) {
		this.idTipoFormatoBene = idTipoFormatoBene;
	}
	
	@Column(name ="TIPO_FORMATO", unique = false, nullable = false)
	public String getDescripcionTipoFormato() {
		return descripcionTipoFormato;
	}

	
	public void setDescripcionTipoFormato(String descripcionTipoFormato) {
		this.descripcionTipoFormato = descripcionTipoFormato;
	}
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) {return false;}
		if(obj == this) {return true;}
		if(!(obj instanceof TipoFormatoBeneficiario)) {return false;}

		TipoFormatoBeneficiario other=(TipoFormatoBeneficiario)obj;
		
		boolean isEquals = false;

		isEquals=EqualsBuilder.reflectionEquals(this, other);
		
		return isEquals;
	}

}
