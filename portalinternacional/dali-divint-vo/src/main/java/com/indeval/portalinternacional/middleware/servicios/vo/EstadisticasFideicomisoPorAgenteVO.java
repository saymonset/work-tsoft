/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.servicios.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author <a href="mailto:ivan.kazakov@2hsoftware.com.mx">Ivan Kazakov</a>
 *
 */
public class EstadisticasFideicomisoPorAgenteVO implements Serializable {
	
	/**
	 * Constante de versi&oacute;n de serializaci&oacute;n 
	 */
	public static final long serialVersionUID = (long)1;

	/**
	 * Agente involucrado 
	 */
	private AgenteFideicomisoVO agenteFideicomisoVO;
	
	/**
	 * Porcentaje correspondiente 
	 */
	private BigDecimal porcentaje;

	/**
	 * Constructor por omisi&oacute;n 
	 */
	public EstadisticasFideicomisoPorAgenteVO() {
		super();
	}
	
	/**
	 * Constructor parametrizado
	 * @param agenteFideicomisoVO
	 * @param porcentaje
	 */
	public EstadisticasFideicomisoPorAgenteVO(AgenteFideicomisoVO agenteFideicomisoVO, BigDecimal porcentaje) {
		super();
		this.agenteFideicomisoVO = agenteFideicomisoVO;
		this.porcentaje = porcentaje;
	}
	
	/**
	 * @return AgenteFideicomisoVO
	 */
	public AgenteFideicomisoVO getAgenteFideicomisoVO() {
		return agenteFideicomisoVO;
	}

	/**
	 * @param agenteFideicomisoVO
	 */
	public void setAgenteFideicomisoVO(AgenteFideicomisoVO agenteFideicomisoVO) {
		this.agenteFideicomisoVO = agenteFideicomisoVO;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getPorcentaje() {
		return porcentaje;
	}

	/**
	 * @param porcentaje
	 */
	public void setPorcentaje(BigDecimal porcentaje) {
		this.porcentaje = porcentaje;
	}
}
