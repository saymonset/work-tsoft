/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.exito.dali.bitacoraoperaciones;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.dao.common.BitacoraOperacionesDao;
import com.indeval.portaldali.persistence.modelo.BitacoraOperaciones;

/**
 * @author javiles
 *
 */
@SuppressWarnings({"unchecked"})
public class ITestGetBitacoras_1 extends BaseDaoTestCase {

	private static final Logger log = LoggerFactory.getLogger(ITestGetBitacoras_1.class);
    
    private BitacoraOperacionesDao bitacoraOperacionesDao;
    
    protected void onSetUp() throws Exception {
        super.onSetUp();
        bitacoraOperacionesDao = (BitacoraOperacionesDao) getBean("bitacoraOperacionesDao");
    }

public void ttestGetBitacoras() {
        
        log.info("Entrando a ITestFindDepositantes_e1.testGetBitacoras()");
        
        assertNotNull(bitacoraOperacionesDao);
        
        Calendar cal = Calendar.getInstance();
        cal.set(2020, Calendar.MAY, 11);
        
        BitacoraOperaciones bitacoraOperaciones = new BitacoraOperaciones();
        bitacoraOperaciones.setIdTrasp("01");
        bitacoraOperaciones.setFolioTrasp("003");
//      bitacoraOperaciones.setIdRecep("01");
//      bitacoraOperaciones.setFolioRecep("003");
        bitacoraOperaciones.setEstatusRegistro(null);
        bitacoraOperaciones.setFechaConcertacion(cal.getTime());
        bitacoraOperaciones.setFechaLiquidacion(null);
        bitacoraOperaciones.setReferenciaMensaje(null);
        
        PaginaVO paginaVO = new PaginaVO();
        
        paginaVO = bitacoraOperacionesDao.getBitacoras(bitacoraOperaciones, paginaVO);
        
        assertNotNull("No existe PaginaVO", paginaVO);
        assertNotNull("No existe la lista de registros", paginaVO.getRegistros());
        assertTrue("No existen registros", !paginaVO.getRegistros().isEmpty());
        
        log.debug("Numero de registros = " + paginaVO.getTotalRegistros());
//        int cont = 0;
//        for (Iterator iter = paginaVO.getRegistros().iterator(); iter.hasNext();) {
//            BitacoraOperaciones bitaOperaciones = (BitacoraOperaciones) iter.next();
//            log.debug("Registro: " + ReflectionToStringBuilder.toString(bitaOperaciones));System.out.println("Registro: " + ReflectionToStringBuilder.toString(bitaOperaciones));
//        }
        
    }

    public void testGetBitacoras_2() {
        
        log.info("Entrando a ITestFindDepositantes_e1.testGetBitacoras()");
        
        assertNotNull(bitacoraOperacionesDao);
        
        BitacoraOperaciones bo = new BitacoraOperaciones();
        bo.setReferenciaOperacion("73706");
        List lista = bitacoraOperacionesDao.getBitacoras(bo);
        
        assertNotNull("No existe la lista", lista);
        assertTrue("No existen elementos en la lista", !lista.isEmpty());
        
        for (Iterator iterator = lista.iterator(); iterator.hasNext();) {
            BitacoraOperaciones bitOperaciones = (BitacoraOperaciones) iterator.next();
            log.debug("bitOperaciones["+ReflectionToStringBuilder.toString(bitOperaciones)+"]");
        }
        
    }
    
}
