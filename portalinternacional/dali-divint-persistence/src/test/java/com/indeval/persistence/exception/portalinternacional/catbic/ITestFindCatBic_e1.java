package com.indeval.persistence.exception.portalinternacional.catbic;

import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portalinternacional.middleware.servicios.modelo.CatBic;
import com.indeval.portalinternacional.persistence.dao.CatBicDao;

public class ITestFindCatBic_e1 extends BaseDaoTestCase {
	
	/** Log de clase. */
	private static final Logger log = LoggerFactory.getLogger(ITestFindCustodios_e1.class);

	/** Dao a probar */
	private CatBicDao catBicDao;

	/**
	 * @see com.indeval.persistence.unittest.BaseDaoTestCase#onSetUp()
	 */
	protected void onSetUp() throws Exception {
		super.onSetUp();
		catBicDao = (CatBicDao) getBean("catBicDao");
	}

	/**
	 * TestCase para probar CatBicDao.findCatbic()
	 */
	public void testFindCatBic() {
		log.info("Entrando a ITestFindCatBic_e1.testFindCatBic()");
		assertNotNull(catBicDao);
		List<CatBic> catbics= catBicDao.findCatBic();
		for(int i=0;i<catbics.size();i++)
    	{
    		CatBic element = catbics.get(i);
 		    assertNotNull(element);
 	        log.debug("CatBic [" + ReflectionToStringBuilder.toString(element) + "]");	      
    	}		
	}

}
