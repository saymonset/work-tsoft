/*
 * Copyright (c) 2019 CMMV Tecnologia. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.dto;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * DTO para PropiedadesDali 
 * @author Amm
 */

public class PropiedadesDaliDTO implements Serializable {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Ambiente de ejecucion
	 */
	private String environment;

	/**
	 * Nombre del parametro
	 */
	private String key;

	/**
	 * Valor del parametro
	 */
	private String value;

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
    		append("environment", environment).
    		append("key", key).
    		append("value", value).
    		toString();
    }

}
