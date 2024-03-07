/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.exception.dali.cinstrumentodao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portaldali.persistence.dao.common.InstrumentoDaliDao;
import com.indeval.portaldali.persistence.model.Instrumento;
import com.indeval.portaldali.persistence.model.Mercado;
import com.indeval.portaldali.persistence.util.UtilsLog;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 *
 */
public class ITestGetListaInstrumentos_e1 extends BaseDaoTestCase {
	
	/** Objeto de loggeo */
    private static final Logger logger = LoggerFactory.getLogger(ITestGetListaInstrumentos_e1.class);

    /**
     * bean de cInstrumentoDao
     */
	private InstrumentoDaliDao cInstrumentoDao;
	
    /**
     * @see com.indeval.persistence.unittest.BaseDaoTestCase#onSetUp()
     */
    protected void onSetUp() {
        super.onSetUp();
        cInstrumentoDao = (InstrumentoDaliDao) getBean("cInstrumentoDao");
    }
	
	/**
	 * TestCase para probar exitosamente getCInstrumento()
	 * @throws Exception
	 */
	public void testGetListaInstrumentos_e1() throws Exception {
		
		logger.info("Entrando a ITestGetListaInstrumentos_e1.testGetListaInstrumentos_e1()");
		
		assertNotNull(cInstrumentoDao);
		
		List listaInstrumentos = cInstrumentoDao.getListaInstrumentos();
		assertNotNull(listaInstrumentos);
		assertTrue(!listaInstrumentos.isEmpty());
		
		logger.debug("Se imprime la lista de instrumentos: ");
		UtilsLog.logElementosLista(listaInstrumentos);
		
		Set<Mercado> setMercados = new HashSet<Mercado>();
		for (Iterator iter = listaInstrumentos.iterator(); iter.hasNext();) {
			Instrumento element = (Instrumento) iter.next();
			setMercados.add(element.getMercado());
		}

		List<Mercado> listaMercados = new ArrayList<Mercado>();
		listaMercados.addAll(setMercados);
		logger.debug("Se imprime la lista de mercados");
		UtilsLog.logElementosLista(listaMercados);
		
	}

}
