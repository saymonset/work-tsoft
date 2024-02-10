/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.exito.portalinternacional.estatusoperaciones;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portalinternacional.middleware.servicios.modelo.EstatusOperacion;
import com.indeval.portalinternacional.persistence.dao.EstatusOperacionDao;

/**
 * @author javiles
 *
 */
@SuppressWarnings({"unchecked"})
public class ITestFindEstatusOperaciones_1 extends BaseDaoTestCase {

    /** Log de clase */
	private static final Logger log = LoggerFactory.getLogger(ITestFindEstatusOperaciones_1.class);
    
    /** Dao a probar */
    private EstatusOperacionDao estatusOperacionDao;
    
    /**
     * @see com.indeval.persistence.unittest.BaseDaoTestCase#onSetUp()
     */
    protected void onSetUp() throws Exception {
        super.onSetUp();
        estatusOperacionDao = (EstatusOperacionDao) getBean("estatusOperacionDao");
    }
    
    public void testFindEstatusOperaciones() {
        
        log.info("Ejecutando prueba testFindEstatusOperaciones()");
        
        assertNotNull(estatusOperacionDao);
        
        List<EstatusOperacion> lista = estatusOperacionDao.findEstatusOperaciones();
        assertNotNull("No existe la lista de registros", lista);
        assertTrue("No existen registros en la lista", !lista.isEmpty());
        
        for (Iterator iter = lista.iterator(); iter.hasNext();) {
            EstatusOperacion element = (EstatusOperacion) iter.next();
            log.debug("Registro [" + ReflectionToStringBuilder.toString(element) + "]");
        }
        
    }
    
}
