// Cambio Multidivisas
package com.indeval.portalinternacional.persistence.dao;

import com.bursatec.persistence.dao.BaseDao;

import java.math.BigInteger;
import java.util.List;

public interface SaldoNombradaDaliDao extends BaseDao {
	
	/**
	 * Obtiene el saldo de la cuenta nombrada de efectivo
	 * @param id
	 * @param folio
	 * @param BigInteger
	 * @return List
	 */
	List getSaldoNombrada(String id, String folio, BigInteger idBoveda);
	
	/**
	 * Obtiene el saldo de la cuenta nombrada de efectivo por boveda y divisa
	 * @param id
	 * @param folio
	 * @param idBoveda
	 * @param idDivisa
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	List getSaldoNombradaDivisa(String id, String folio, BigInteger idBoveda, BigInteger idDivisa);


}

