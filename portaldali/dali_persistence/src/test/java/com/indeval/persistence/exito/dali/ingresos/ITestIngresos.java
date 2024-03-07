/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.exito.dali.ingresos;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portaldali.persistence.dao.ingresos.IngresosDao;
import com.indeval.portaldali.persistence.model.Sistema;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 *
 */
public class ITestIngresos extends BaseDaoTestCase {
	
	/** Objeto de loggeo */
    private static final Logger log = LoggerFactory.getLogger(ITestIngresos.class);

    
    private IngresosDao ingresosDao;
	
    /**
     * @see com.indeval.persistence.unittest.BaseDaoTestCase#onSetUp()
     */
    protected void onSetUp() {
        super.onSetUp();
        ingresosDao = (IngresosDao) getBean("ingresosDao");
    }
	
	/**
	 * TestCase para probar exitosamente esInhabil()
	 * @throws Exception
	 */
	public void testGetSistema() throws Exception {
		Sistema sistema = ingresosDao.getSistemaPorNombre("PORTAL_DALI");
		
		if( sistema != null ) {
			log.info("Sistema: [" + ToStringBuilder.reflectionToString(sistema,ToStringStyle.MULTI_LINE_STYLE) + "]");
		} else {
			log.error("No se encontro el sistema");
		}
	}

}
