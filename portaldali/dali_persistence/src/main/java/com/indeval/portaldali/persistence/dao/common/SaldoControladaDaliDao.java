/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.dao.common;

import java.math.BigInteger;
import java.util.List;

import com.bursatec.persistence.dao.BaseDao;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public interface SaldoControladaDaliDao extends BaseDao {
	
	/**
	 * Obtiene el saldo de la cuenta nombrada de efectivo
	 * @param id
	 * @param folio
	 * @param BigInteger
	 * @return List
	 */
	List getSaldoControlada(String id, String folio, BigInteger idBoveda);

}
