/*
 * Copyright (c) 2019 CMMV Tecnologia. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 
 * @author Amm
 */
@Entity
@Table(name = "C_PROPIEDADES_DALI")
public class PropiedadesDali implements Serializable {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PropiedadesDaliPK propiedadesDaliPK; 

	/**
	 * Valor del parametro
	 */
	@Column(name = "VALUE", unique = false, nullable = true)
	private String value;

	/**
	 * @return the propiedadesDaliPK
	 */
	public PropiedadesDaliPK getPropiedadesDaliPK() {
		return propiedadesDaliPK;
	}

	/**
	 * @param propiedadesDaliPK the propiedadesDaliPK to set
	 */
	public void setPropiedadesDaliPK(PropiedadesDaliPK propiedadesDaliPK) {
		this.propiedadesDaliPK = propiedadesDaliPK;
	}

	/**
	 * @return
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 */
	public void setValue(String value) {
		this.value = value;
	}

    @Override
    public String toString() {
    	return new ToStringBuilder(this).
    		append("pk", propiedadesDaliPK).
    		append("value", value).
    		toString();
    }

}
