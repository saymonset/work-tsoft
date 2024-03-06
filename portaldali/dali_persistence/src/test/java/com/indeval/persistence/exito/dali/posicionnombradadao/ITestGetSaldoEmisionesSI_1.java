/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.exito.dali.posicionnombradadao;

import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import com.indeval.portaldali.persistence.dao.estadocuenta.EstadoCuentaSocInvDAO;

/**
 * @author Rafael Ibarra Zendejas
 *
 */
public class ITestGetSaldoEmisionesSI_1 extends BaseDaoTestCase {
	
	/** Objeto de loggeo  */
    private static final Logger logger = LoggerFactory.getLogger(ITestGetSaldoEmisionesSI_1.class);

    /**
     * bean de cInstrumentoDao
     */
	private EstadoCuentaSocInvDAO estadoCuentaSocInvDAO;
	
    /**
     * @see com.indeval.persistence.portallegado.unittest.BaseDaoTestCase#onSetUp()
     */
    protected void onSetUp() {
        super.onSetUp();
        estadoCuentaSocInvDAO = (EstadoCuentaSocInvDAO) getBean("estadoCuentaSocInvDao");
    }
	
//    /**
//     * TestCase para getTPosicionNombradaByAgente()
//     */
//    @SuppressWarnings("unchecked")
//	public void testGetSaldoEmisionesSI_1() {
//    	
//    	log.info("Entrando a ITestGetSaldoEmisionesSI_1.testGetSaldoEmisionesSI_1()");
//    	
//    	assertNotNull(estadoCuentaSocInvDAO);
//        
//    	AgenteVO agente = new AgenteVO("01","003");
//    	
//    	List lista = (List)estadoCuentaSocInvDAO.getSaldoEstadoCuentasocInv(agente, "IMCTRAC");
//        
//        assertNotNull(lista);
//        assertFalse(lista.isEmpty());
//        
//    	log.debug("Registros en la pagina : [" + lista.size() + "]");
//    	
//    	Iterator it  = lista.iterator();
//    	
//    	while( it.hasNext() ) {
//    		Object[] array = (Object[])it.next();
//    		log.info("Emisora: [" + array[0] + "-" + array[1] + "-" + array[2] + "-" + array[3] + 
//    				"-" + array[4] + "-" + array[5] + "-" + array[6] + "]");
//    	}
//    	
//    }
    
    /**
     * TestCase para getTPosicionNombradaByAgente()
     */
    @SuppressWarnings("unchecked")
	public void testGetListaTemporal_1() {
    	
    	log.info("Entrando a ITestGetSaldoEmisionesSI_1.testGetListaTemporal_1()");
    	
    	assertNotNull(estadoCuentaSocInvDAO);
        
    	AgenteVO agente = new AgenteVO("01","003");
    	
    	List lista = (List)estadoCuentaSocInvDAO.getLista1234(agente);
        
        assertNotNull(lista);
        assertFalse(lista.isEmpty());
        
    	log.debug("Registros en la pagina : [" + lista.size() + "]");
    	
    	Iterator it  = lista.iterator();
    	
    	while( it.hasNext() ) {
    		Object array = (Object)it.next();
    		log.info("Emisora: [" + (String)array + "]");
    	}
    	
    }
   
}
