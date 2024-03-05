package com.indeval.persistence.exito.dali.common;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portaldali.persistence.dao.common.DepositanteValidoBanxicoDao;

public class DepositanteValidoBanxicoTest extends BaseDaoTestCase {

	private DepositanteValidoBanxicoDao depositanteValidoBanxicoDao;

	public void onSetUp() {
		super.onSetUp();
		depositanteValidoBanxicoDao = (DepositanteValidoBanxicoDao) applicationContext.getBean("depositanteValidoBanxicoDao");
	}

	public void test() {
		boolean isDepositanteValidoBanxico = depositanteValidoBanxicoDao.isDepositanteValidoBanxico(3l);
		System.err.println("isDepositanteValidoBanxico ====== [" + isDepositanteValidoBanxico + "]");
	}
}