/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.exito.dali.posicionnombradadao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portaldali.persistence.dao.common.PosicionNombradaDaliDao;
import com.indeval.portaldali.persistence.vo.TPosicionNombradaParamsPersistence;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 *
 */
public class ITestGetTPosicionNombradaByCuentas_1 extends BaseDaoTestCase {
	
	/** Objeto de loggeo  */
    private static final Logger log = LoggerFactory.getLogger(ITestGetTPosicionNombradaByCuentas_1.class);

    /**
     * bean de cInstrumentoDao
     */
	private PosicionNombradaDaliDao tPosicionNombradaDao;
	
    /**
     * @see com.indeval.persistence.unittest.BaseDaoTestCase#onSetUp()
     */
    protected void onSetUp() {
        super.onSetUp();
        tPosicionNombradaDao = (PosicionNombradaDaliDao) getBean("tPosicionNombradaDao");
    }
	
    /**
     * TestCase para getTPosicionNombradaByArregloCuenta()
     */
    public void testGetTPosicionNombradaByArregloCuenta_1() {
    	
    	log.info("Entrando a ITestGetTPosicionNombradaByArregloCuenta_1." +
    			"testGetTPosicionNombradaByArregloCuenta_1()");
    	
    	assertNotNull(tPosicionNombradaDao);
    	
    	TPosicionNombradaParamsPersistence params = 
    		UtilsTPosicionNombrada.getInstanceTPosicionNombradaParamsPersistence();
    	params.setIdInstitucion("01");
    	params.setFolioInstitucion("014");
        
        String[] cuentas = new String[]{"1800", "1801", "1802" ,"1803" ,"1804" ,"1805"};
    	params.setCuentas(cuentas);
    	
    	List[] arreglolistaTPosicionNombrada = 
    		tPosicionNombradaDao.getTPosicionNombradaByCuentas(params);
    	
    	assertNotNull(arreglolistaTPosicionNombrada);
    	assertTrue(arreglolistaTPosicionNombrada.length > 0);
    	
    	for (int i = 0; i < arreglolistaTPosicionNombrada.length; i++) {
    		
    		List listaTPosicionNombrada = arreglolistaTPosicionNombrada[i];	
        	assertNotNull(listaTPosicionNombrada);
        	if(!listaTPosicionNombrada.isEmpty()){
                log.debug("Imprimiendo una muestra de los registros para la cuenta : [" + 
                        cuentas[i] + "]");
                UtilsTPosicionNombrada.logListaTPosicionNombrada(listaTPosicionNombrada);    
            }
            else {
                log.debug("No hay registros para la cuenta : [" + cuentas[i] + "]");
            }
        	
		}
    	
    }
    
}
