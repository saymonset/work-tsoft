/**
 * 
 */
package com.indeval.persistence.exito.portalinternacional.operacionsic;

import java.math.BigInteger;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portalinternacional.middleware.servicios.modelo.OperacionSic;
import com.indeval.portalinternacional.persistence.dao.OperacionSicDao;

/**
 * @author javiles
 *
 */
public class ITestFindOperacionByFolioControl_1 extends BaseDaoTestCase {

	private static final Logger log = LoggerFactory.getLogger(ITestFindOperaciones_1.class);
    
    private OperacionSicDao operacionSicDao;
    
    protected void onSetUp() throws Exception {
        super.onSetUp();
        operacionSicDao = (OperacionSicDao) getBean("operacionSicDao");
    }
    
    public void testFindOperacionByFolioControl() {
        
        log.info("Ejectuando prueba testFindOperacionByFolioControl()");
        
        assertNotNull(operacionSicDao);
        BigInteger folioControl = BigInteger.valueOf(72055);
        OperacionSic operacionSic = operacionSicDao.findOperacionByFolioControl(folioControl);
        
        assertNotNull("No existe operacionSic", operacionSic);
        
        log.info("operacionSic["+ReflectionToStringBuilder.toString(operacionSic)+"]");
        
    }
    
}
