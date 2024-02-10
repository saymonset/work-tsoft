/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.persistence.dao;

import java.util.List;

import com.bursatec.persistence.dao.BaseDao;
import com.indeval.portalinternacional.middleware.servicios.modelo.CustodioTipoBenef;
/**
 * 
 * @author Oscar Garcia Granados
 *
 */
@SuppressWarnings("unchecked")
public interface CustodioTipoBenefDao extends BaseDao{
	
	/**
	 * Devuelve una lista de objetos de clase CustodioTipoBenef que
	 * corresponden a cierto custodio.
	 * @param idCatBic
	 * @return List<CustodioTipoBenef>
	 */
	List findByIdCatBic(Long idCuentaNombrada);
	
	/**
	 * Devuelve una lista de objetos de clase CustodioTipoBenef.
	 * @return List<CustodioTipoBenef>
	 */
	List findByIdCatBic();
	
	/**
	 * @param idCuentaNombrada
	 * @param idTipoBeneficiario
	 * @return
	 */
	String findFormato(Long idCuentaNombrada, Long idTipoBeneficiario);
	
	/**
	 * Devuelve el formato de acuerdo al tipo de beneficirio y a la
	 * cuenta nombrada
	 * @param idCuentaNombrada
	 * @param idTipoBeneficiario
	 * @return String formato
	 */
	List<CustodioTipoBenef> findByNameCatBic(String catBic);

	/**
	 * Obtiene el porcentaje de Retencion para un custodio y tipo de
	 * beneficiario en especifico
	 * @param idCuentaNombrada ID del custodio
	 * @param idTipoBeneficiario Tipo de Beneficiario
	 * @return Porcentaje de Retencion o null si no existe
	 */
	Double getPorcentajeRetencion(Long idCuentaNombrada, Long idTipoBeneficiario);

	/**
	 * Devuelve la lista completa de Custodios y tipos de beneficiarios
	 * @return Lista de CustodioTipoBenef
	 */
	List<CustodioTipoBenef> getCustodiosTipoBeneficiario();

	/**
	 * Obtiene las cadenas de tipos beneficiarios de una cuenta nombrada en particular. 
	 * Las obtiene de la siguiente forma: idTipoBeneficiario-formato-porcentajeRetencion. 
	 * @param idCuentaNombrada El id de la cuenta nombrada.
	 * @return El listado de cadenas de tipos beneficiarios.
	 */
	List<String> findCadenasDeFormatoByIdCuentaNombrada(Long idCuentaNombrada);

	/**
	 * Obtiene objetos CustodioTipoBenef por id de cuenta nombrada
	 * @param idCuentaNombrada El id para la busqueda
	 * @return El listado de objetos CustodioTipoBenef
	 */
	List<CustodioTipoBenef> findCustodioTipoBenefByIdCuentaNombrada(Long idCuentaNombrada);

}
