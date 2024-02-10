/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.servicios.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.indeval.portaldali.middleware.servicios.modelo.vo.EmisionVO;

/**
 * @author <a href="mailto:ivan.kazakov@2hsoftware.com.mx">Ivan Kazakov</a>
 *
 */
public class ConsultaFideicomisosVO implements Serializable {
	
	/**
	 * Constante de versi&oacute;n de serializaci&oacute;n 
	 */
	public static final long serialVersionUID = (long)1;
	
	/**
	 * Emisi&oacute;n actual 
	 */
	private EmisionVO emisionVO;
	
	/**
	 * Lista posiciones asociadas a la emisi&oacute;n  
	 */
	private List<AgenteFideicomisoVO> agenteFideicomisoVOs;
	
	/**
	 * Estad&iacute;sticas por agente 
	 */
	private List<EstadisticasFideicomisoPorAgenteVO> estadisticasFideicomisosPorAgenteVO;
	
	/**
	 * Estad&iacute;sticas por cuenta 
	 */
	private List<EstadisticasFideicomisoPorCuentaVO> estadisticasFideicomisoPorCuentaVO;
	
	/**
	 * N&uacute;mero total de agentes involucrados 
	 */
	private Long totalAgentes;
	
	/**
	 * Total de la posici&oacute;n disponible actual 
	 */
	private BigInteger totalPosicionActual;
	
	/**
	 * Total de la valuaci&oacute;n de mercado actual 
	 */
	private BigInteger totalValuacionMercadoActual;
	
	/**
	 * Total de la valuaci&oacute;n de nominal actual 
	 */
	private BigDecimal totalValuacionNominalActual;
	
	/**
	 * Total de emisiones 
	 */
	private Long totalEmisiones;
	
	/**
	 * Saldo total global de posiciones 
	 */
	private BigInteger saldoTotal;
	
	/**
	 * Total de la valuaci&oacute;n de mercado global 
	 */
	private BigInteger totalValuacionMercado;
	
	/**
	 * Total de la valuaci&oacute;n nominal global 
	 */
	private BigDecimal totalValuacionNominal;

	/**
	 * Constructor por omisi&oacute;n 
	 */
	public ConsultaFideicomisosVO() {
		super();
	}
	
	/**
	 * Constructor parametrizado
	 * @param emisionVO
	 */
	public ConsultaFideicomisosVO(EmisionVO emisionVO) {
		super();
		this.emisionVO = emisionVO;
		this.agenteFideicomisoVOs = new ArrayList<AgenteFideicomisoVO>();
		this.estadisticasFideicomisoPorCuentaVO = new ArrayList<EstadisticasFideicomisoPorCuentaVO>();
		this.estadisticasFideicomisosPorAgenteVO = new ArrayList<EstadisticasFideicomisoPorAgenteVO>();
	}
	
	/**
	 * @return EmisionVO
	 */
	public EmisionVO getEmisionVO() {
		return emisionVO;
	}

	/**
	 * @param emisionVO
	 */
	public void setEmisionVO(EmisionVO emisionVO) {
		this.emisionVO = emisionVO;
	}

	/**
	 * @return List<AgenteFideicomisoVO>
	 */
	public List<AgenteFideicomisoVO> getAgenteFideicomisoVOs() {
		return agenteFideicomisoVOs;
	}

	/**
	 * @param agenteFideicomisoVOs
	 */
	public void setAgenteFideicomisoVOs(
			List<AgenteFideicomisoVO> agenteFideicomisoVOs) {
		this.agenteFideicomisoVOs = agenteFideicomisoVOs;
	}

	/**
	 * @return List<EstadisticasFideicomisoPorAgenteVO>
	 */
	public List<EstadisticasFideicomisoPorAgenteVO> getEstadisticasFideicomisosPorAgenteVO() {
		return estadisticasFideicomisosPorAgenteVO;
	}

	/**
	 * @param estadisticasFideicomisosPorAgenteVO
	 */
	public void setEstadisticasFideicomisosPorAgenteVO(
			List<EstadisticasFideicomisoPorAgenteVO> estadisticasFideicomisosPorAgenteVO) {
		this.estadisticasFideicomisosPorAgenteVO = estadisticasFideicomisosPorAgenteVO;
	}

	/**
	 * @return List<EstadisticasFideicomisoPorCuentaVO>
	 */
	public List<EstadisticasFideicomisoPorCuentaVO> getEstadisticasFideicomisoPorCuentaVO() {
		return estadisticasFideicomisoPorCuentaVO;
	}

	/**
	 * @param estadisticasFideicomisoPorCuentaVO
	 */
	public void setEstadisticasFideicomisoPorCuentaVO(
			List<EstadisticasFideicomisoPorCuentaVO> estadisticasFideicomisoPorCuentaVO) {
		this.estadisticasFideicomisoPorCuentaVO = estadisticasFideicomisoPorCuentaVO;
	}

	/**
	 * @return Long
	 */
	public Long getTotalAgentes() {
		return totalAgentes;
	}

	/**
	 * @param totalAgentes
	 */
	public void setTotalAgentes(Long totalAgentes) {
		this.totalAgentes = totalAgentes;
	}

	/**
	 * @return BigInteger
	 */
	public BigInteger getTotalPosicionActual() {
		return totalPosicionActual;
	}

	/**
	 * @param totalPosicionActual
	 */
	public void setTotalPosicionActual(BigInteger totalPosicionActual) {
		this.totalPosicionActual = totalPosicionActual;
	}

	/**
	 * @return BigInteger
	 */
	public BigInteger getTotalValuacionMercado() {
		return totalValuacionMercado;
	}

	/**
	 * @param totalValuacionMercado
	 */
	public void setTotalValuacionMercado(BigInteger totalValuacionMercado) {
		this.totalValuacionMercado = totalValuacionMercado;
	}

	/**
	 * @return Long
	 */
	public Long getTotalEmisiones() {
		return totalEmisiones;
	}

	/**
	 * @param totalEmisiones
	 */
	public void setTotalEmisiones(Long totalEmisiones) {
		this.totalEmisiones = totalEmisiones;
	}

	/**
	 * @return BigInteger
	 */
	public BigInteger getSaldoTotal() {
		return saldoTotal;
	}

	/**
	 * @param saldoTotal
	 */
	public void setSaldoTotal(BigInteger saldoTotal) {
		this.saldoTotal = saldoTotal;
	}

	/**
	 * @return BigInteger
	 */
	public BigInteger getTotalValuacionMercadoActual() {
		return totalValuacionMercadoActual;
	}

	/**
	 * @param totalValuacionMercadoActual
	 */
	public void setTotalValuacionMercadoActual(
			BigInteger totalValuacionMercadoActual) {
		this.totalValuacionMercadoActual = totalValuacionMercadoActual;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getTotalValuacionNominalActual() {
		return totalValuacionNominalActual;
	}

	/**
	 * @param totalValuacionNominalActual
	 */
	public void setTotalValuacionNominalActual(
			BigDecimal totalValuacionNominalActual) {
		this.totalValuacionNominalActual = totalValuacionNominalActual;
	}

	/**
	 * @return BigDecimal
	 */
	public BigDecimal getTotalValuacionNominal() {
		return totalValuacionNominal;
	}

	/**
	 * @param totalValuacionNominal
	 */
	public void setTotalValuacionNominal(BigDecimal totalValuacionNominal) {
		this.totalValuacionNominal = totalValuacionNominal;
	}
}
