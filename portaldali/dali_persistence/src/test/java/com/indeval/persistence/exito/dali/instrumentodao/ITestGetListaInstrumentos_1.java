/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.exito.dali.instrumentodao;

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
public class ITestGetListaInstrumentos_1 extends BaseDaoTestCase {
	
	/** Objeto de loggeo */
    private static final Logger log = LoggerFactory.getLogger(ITestGetListaInstrumentos_1.class);

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
	public void testGetListaInstrumentos_1() throws Exception {
		
		log.info("Entrando a ITestGetListaInstrumentos_1.testGetListaInstrumentos_1()");
		
		assertNotNull(cInstrumentoDao);
		
		List listaInstrumentos = cInstrumentoDao.getListaInstrumentos();
		assertNotNull(listaInstrumentos);
		assertTrue(!listaInstrumentos.isEmpty());
		
		log.debug("Se imprime la lista de instrumentos: ");
		UtilsLog.logElementosLista(listaInstrumentos);
		
		Set<Mercado> setMercados = new HashSet<Mercado>();
		for (Iterator iter = listaInstrumentos.iterator(); iter.hasNext();) {
			Instrumento element = (Instrumento) iter.next();
			setMercados.add(element.getMercado());
		}

		List<Mercado> listaMercados = new ArrayList<Mercado>();
		listaMercados.addAll(setMercados);
		log.debug("Se imprime la lista de mercados");
		UtilsLog.logElementosLista(listaMercados);
		
	}

}
