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
public class ITestGetListaEmisionesSI_1 extends BaseDaoTestCase {
	
	/** Objeto de loggeo  */
    private static final Logger logger = LoggerFactory.getLogger(ITestGetListaEmisionesSI_1.class);

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
	
    /**
     * TestCase para getTPosicionNombradaByAgente()
     */
    @SuppressWarnings("unchecked")
	public void testGetListaEmisionesSI_1() {
    	
    	log.info("Entrando a ITestGetListaEmisionesSI_1.testGetListaEmisionesSI_1()");
    	
    	assertNotNull(estadoCuentaSocInvDAO);
        
    	AgenteVO agente = new AgenteVO("01","003");
//    	EmisionVO emision = new EmisionVO();
//    	emision.setIdTipoValor("52");
//    	emision.setEmisora("HORZ");
    	
    	List<String> lista = (List<String>)estadoCuentaSocInvDAO.getListaEmisiones(agente, null);
        
        assertNotNull(lista);
        assertFalse(lista.isEmpty());
        
    	log.debug("Registros en la pagina : [" + lista.size() + "]");
    	
    	Iterator<String> it  = lista.iterator();
    	
    	while( it.hasNext() ) {
    		log.info("Emisora: [" + it.next() + "]");
    	}
    	
    }
    
//    /**
//     * TestCase para getTPosicionNombradaByAgente()
//     */
//    @SuppressWarnings("unchecked")
//	public void testGetListaEmisionesRazonSocialSI_1() {
//    	
//    	log.info("Entrando a ITestGetListaEmisionesSI_1.testGetListaEmisionesRazonSocialSI_1()");
//    	
//    	assertNotNull(estadoCuentaSocInvDAO);
//        
//    	AgenteVO agente = new AgenteVO("01","003");
//    	EmisionVO emision = new EmisionVO();
////    	emision.setIdTipoValor("52");
////    	emision.setEmisora("HORZ");
//    	
//    	List lista = (List)estadoCuentaSocInvDAO.getListaEmisionesRazonSocial(agente, emision);
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
//    		log.info("Emisora: [" + array[0] + " - " + array[1] + " - " + array[2] + "]");
//    	}
//    	
//    }
    
}
