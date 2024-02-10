package com.indeval.portalinternacional.persistence.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.type.Type;

 class CalendarioInternacionalQuery {
	private StringBuilder sb;
	private List<Object> paramsSQL;
	private List<Type> tipos;	
	private String select;
	private String selectCounter;
	
	public CalendarioInternacionalQuery(){				
		this.sb = new StringBuilder();
		this.paramsSQL = new ArrayList<Object>();
		this.tipos = new ArrayList<Type>();
	}

	/**
	 * @return the query
	 */
	public String getQuery() {
		return select+sb.toString();
	}

	public String getCountQuery() {
		return selectCounter+sb.toString();
	}
	
	public void setParamSql(Object obj,Type tipo){
		this.paramsSQL.add(obj);	
		this.tipos.add(tipo);		
	}

	public void setEnunciado(String enunciado){
		this.sb.append(enunciado);
	}
	
	public void setSelectEnunciado(String select){
		this.select=select+" ";
	}
	
	public void setSelectCounterEnunciado(String select){
		this.selectCounter=select+" ";
	}
	/**
	 * @return the paramsSQL
	 */
	public List<Object> getParamsSQL() {
		return paramsSQL;
	}

	/**
	 * @param paramsSQL the paramsSQL to set
	 */
	public void setParamsSQL(List<Object> paramsSQL) {
		this.paramsSQL = paramsSQL;
	}

	/**
	 * @return the tipos
	 */
	public List<Type> getTipos() {
		return tipos;
	}

	/**
	 * @param tipos the tipos to set
	 */
	public void setTipos(List<Type> tipos) {
		this.tipos = tipos;
	}

}
