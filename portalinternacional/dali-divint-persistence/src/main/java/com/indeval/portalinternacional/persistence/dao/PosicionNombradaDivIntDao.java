/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.persistence.dao;

import java.math.BigInteger;
import java.util.List;

import com.bursatec.persistence.dao.BaseDao;
import com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO;

/**
 * @author <a href="mailto:ivan.kazakov@2hsoftware.com.mx">Ivan Kazakov</a>
 *
 */
public interface PosicionNombradaDivIntDao extends BaseDao {

	/**
	 * Obtiene el n&uacute;mero de emisiones distintas referenciadas por posiciones nombradas
	 * correspondientes a la cuenta nombrada proporcionada
	 * @param Long
	 * @param posicionDisponibleNoCero
	 * @return Long
	 */
	public Long countNumeroEmisionesPosicionPorCuentaNombrada(Long idCuentaNombrada, boolean posicionDisponibleNoCero);
	
	/**
	 * Obtiene el total de posici&oacute;n disponible correspondiente a las posiciones 
	 * de la cuenta nombrada proporcionada
	 * @param Long
	 * @param posicionDisponibleNoCero
	 * @return BigInteger
	 */
	public BigInteger sumPosicionDisponiblePorCuentaNombrada(Long idCuentaNombrada, boolean posicionDisponibleNoCero);

	/**
	 * Obtiene el n&uacute;mero de emisiones arqueadas Inbursa
	 * @return Long
	 */
	public Long countNumeroEmisionesInbur();
	
	/**
	 * Obtiene el n&uacute;mero de emisiones arqueadas Nafinsa
	 * @return Long
	 */
	public Long countNumeroEmisionesNafin();
	
	/**
	 * Obtiene el n&uacute;mero de emisiones arqueadas Vitro
	 * @return Long
	 */
	public Long countNumeroEmisionesVitro();
	
	/**
	 * Obtiene el n&uacute;mero de emisiones arqueadas Banamex
	 * @return Long
	 */
	public Long countNumeroEmisionesBanamex();

	/**
	 * Obtiene el total de posici&oacute;n disponible correspondiente a Inbursa 
	 * @return BigInteger
	 */
	public BigInteger sumPosicionDisponibleInbur();

	/**
	 * Obtiene el total de posici&oacute;n disponible correspondiente a Nafin 
	 * @return BigInteger
	 */
	public BigInteger sumPosicionDisponibleNafin();
	
	/**
	 * Obtiene el total de posici&oacute;n disponible correspondiente a Vitro 
	 * @return BigInteger
	 */
	public BigInteger sumPosicionDisponibleVitro();

	/**
	 * Obtiene el total de posici&oacute;n disponible correspondiente a Banamex 
	 * @return BigInteger
	 */
	public BigInteger sumPosicionDisponibleBanamex();
	
	/**
	 * Realiza la consulta de posiciones de Fideicomisos por agente
	 * @param agenteVO
	 * @return List<Object>
	 */
	public List<Object> consultaPosicionesFideicomiso(AgenteVO agenteVO);

}
