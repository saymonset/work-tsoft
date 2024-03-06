/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.exito.dali.instrumentodao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portaldali.persistence.dao.common.InstrumentoDaliDao;
import com.indeval.portaldali.persistence.model.Instrumento;
import com.indeval.portaldali.persistence.model.Mercado;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 *
 */
public class ITestGetCInstrumento_1 extends BaseDaoTestCase {
	
	/** Objeto de loggeo */
    private static final Logger logger = LoggerFactory.getLogger(ITestGetCInstrumento_1.class);

    /**
     * bean de cInstrumentoDao
     */
	private InstrumentoDaliDao cInstrumentoDao;
	
    /**
     * @see com.indeval.persistence.portallegado.unittest.BaseDaoTestCase#onSetUp()
     */
    protected void onSetUp() {
        super.onSetUp();
        cInstrumentoDao = (InstrumentoDaliDao) getBean("cInstrumentoDao");
    }
	
	/**
	 * TestCase para probar exitosamente getCInstrumento()
	 * @throws Exception
	 */
	public void testGetCInstrumento_1() throws Exception {
		
		log.info("Entrando a ITestGetCInstrumento.testGetCInstrumento()");
		
		assertNotNull(cInstrumentoDao);
		
		Instrumento cInstrumento = cInstrumentoDao.getInstrumento("BI");
		assertNotNull(cInstrumento);
		
		Mercado cMercado = cInstrumento.getMercado();
		assertNotNull(cMercado);
		
		log.debug("El objeto Mercado...");
		log.debug("  Id Mercado : [" + cMercado.getIdMercado() + "]");
		log.debug("  Clave Mercado : [" + cMercado.getClave() + "]");
		log.debug("  Descripcion Mercado : [" + cMercado.getDescripcion() + "]");

	}

}
