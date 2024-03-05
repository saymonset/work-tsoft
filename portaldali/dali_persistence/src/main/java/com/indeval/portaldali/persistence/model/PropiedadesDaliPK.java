/*
 * Copyright (c) 2019 CMMV Tecnologia. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 
 * @author Amm
 */
@Embeddable
public class PropiedadesDaliPK implements Serializable {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Ambiente de ejecucion
	 */
	@Column(name = "ENVIRONMENT", unique = false, nullable = true)
	private String environment;

	/**
	 * Nombre del parametro
	 */
	@Column(name = "KEY", unique = false, nullable = true)
	private String key;

	/**
	 * @return
	 */
	public String getEnvironment() {
		return environment;
	}

	/**
	 * @param environment
	 */
	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	/**
	 * @return
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key
	 */
	public void setKey(String key) {
		this.key = key;
	}

    @Override
    public String toString() {
    	return new ToStringBuilder(this).
    		append("environment", environment).
    		append("key", key).
    		toString();
    }

}
