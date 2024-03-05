/**
 * 
 */
package com.indeval.persistence.dao;

import java.math.BigInteger;
import java.util.List;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portaldali.persistence.dao.common.SaldoNombradaDaliDao;

/**
 * @author antonio
 * 
 */
public class TestSaldoNombradaDAO extends
		BaseDaoTestCase {

	/**
	 * saldoNombradaDao
	 */
	@SuppressWarnings("unchecked")
	public void testDao() {
		
		SaldoNombradaDaliDao dao = (SaldoNombradaDaliDao)applicationContext.getBean("saldoNombradaDao");
		
		List listaSaldoNombrada = 
			dao.getSaldoNombrada("02", "013", new BigInteger("1"));
		
		assertTrue(listaSaldoNombrada != null && !listaSaldoNombrada.isEmpty());
	}
	
		
}
