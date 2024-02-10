package com.indeval.persistence.exception.portalinternacional.custodiotipobenef;

import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portalinternacional.middleware.servicios.modelo.CustodioTipoBenef;
import com.indeval.portalinternacional.persistence.dao.CustodioTipoBenefDao;

public class ITestFindByIdCatBic_e1 extends BaseDaoTestCase {
	
	/**
	 * Log de la clase
	 */
	private static final Logger log = LoggerFactory.getLogger(ITestFindByIdCatBic_e1.class);
	
	/**
	  * Dao que se va a probar
	 */
	 private CustodioTipoBenefDao custodioTipoBenefDao;
	 
	 /**
	  * 
	  */
	 protected void onSetUp() throws Exception {
        super.onSetUp();
        custodioTipoBenefDao = (CustodioTipoBenefDao) getBean("custodioTipoBenefDao");
     }
	 
	 /**
	  * 
	  *
	  */
	 public void testFindByIdCatBic() {
		 
		 log.info("Ejecutando prueba testFindByIdCatBic()");        
	     assertNotNull(custodioTipoBenefDao);
	     //Long idCatBic=new Long(1);
	     List<CustodioTipoBenef> listCustodioTipoBenef=custodioTipoBenefDao.findByNameCatBic("THE BANK OF NEW YORK");
	     log.debug("Total de registros = " + listCustodioTipoBenef.size());
	     for(int i=0;i<listCustodioTipoBenef.size();i++)
	     {
	    	 assertNotNull(listCustodioTipoBenef.get(i));
	    	 log.debug("CustodioTipoBenef [" + ReflectionToStringBuilder.toString(listCustodioTipoBenef.get(i)) + "]");
	     }
		 
	 }

}
