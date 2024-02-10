/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.exito.portalinternacional.operacionsic;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.EmisionVO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.modelo.CuentaNombrada;
import com.indeval.portaldali.persistence.modelo.Emision;
import com.indeval.portaldali.persistence.util.DateUtils;
import com.indeval.portalinternacional.middleware.servicios.modelo.OperacionSic;
import com.indeval.portalinternacional.persistence.dao.OperacionSicDao;

/**
 * @author javiles
 *
 */
@SuppressWarnings({"unchecked"})
public class ITestFindOperaciones_1 extends BaseDaoTestCase {

	private static final Logger log = LoggerFactory.getLogger(ITestFindOperaciones_1.class);
    
    private OperacionSicDao operacionSicDao;
    
    protected void onSetUp() throws Exception {
        super.onSetUp();
        operacionSicDao = (OperacionSicDao) getBean("operacionSicDao");
    }
    
    public void ttestFindOperaciones() {
        
        log.info("Ejectuando prueba testFindOperaciones()");
        
        assertNotNull(operacionSicDao);
        Calendar cal = Calendar.getInstance();
        cal.set(2008, Calendar.JULY, 18, 0, 0, 0);
        OperacionSic operacionSic = new OperacionSic();
        operacionSic.setCuentaNombrada(new CuentaNombrada(new AgenteVO("01", "003", "0307")));
        operacionSic.setEmision(new Emision(new EmisionVO("1", "ALFA", "A", "0022")));
        operacionSic.setFechaConsulta(DateUtils.preparaIntervaloFechas(cal.getTime(), new Date()));
        PaginaVO paginaVO = null;
        paginaVO = operacionSicDao.findOperaciones(operacionSic, paginaVO, true);
        
        assertNotNull("No existe la pagina", paginaVO);
        assertNotNull("No hay total de registros", paginaVO.getTotalRegistros());
        assertTrue("No hay lista de registros", (paginaVO.getRegistros() != null && !paginaVO.getRegistros().isEmpty()));
        
        log.info("Total de registros= " + paginaVO.getTotalRegistros());
        for (Iterator iter = paginaVO.getRegistros().iterator(); iter.hasNext();) {
            OperacionSic element = (OperacionSic) iter.next();
            log.info("Registro [" + ReflectionToStringBuilder.toString(element) + "]");
            log.info("Registro.EstatusOperacion [" + ReflectionToStringBuilder.toString(element.getEstatusOperacion()) + "]");
        }
        
    }
    
    public void ttestFindOperaciones_2() {
        
        log.info("Ejectuando prueba testFindOperaciones()");
        
        assertNotNull(operacionSicDao);
        Calendar cal = Calendar.getInstance();
        cal.set(2008, Calendar.JULY, 18, 0, 0, 0);
        OperacionSic operacionSic = new OperacionSic();
        operacionSic.setCuentaNombrada(new CuentaNombrada(new AgenteVO("01", "003", "0307")));
        operacionSic.setFechaConsulta(DateUtils.preparaIntervaloFechas(cal.getTime(), new Date()));
        PaginaVO paginaVO = new PaginaVO();
        paginaVO.setOffset(1);
        paginaVO = operacionSicDao.findOperaciones(operacionSic, paginaVO, true);
        
        assertNotNull("No existe la pagina", paginaVO);
        assertNotNull("No hay total de registros", paginaVO.getTotalRegistros());
        assertTrue("No hay lista de registros", (paginaVO.getRegistros() != null && !paginaVO.getRegistros().isEmpty()));
        
        log.info("Total de registros= " + paginaVO.getTotalRegistros());
        for (Iterator iter = paginaVO.getRegistros().iterator(); iter.hasNext();) {
            OperacionSic element = (OperacionSic) iter.next();
            log.info("Registro [" + ReflectionToStringBuilder.toString(element) + "]");
        }
        
    }
    
    public void ttestFindOperaciones_3() {
        
        log.info("Ejectuando prueba testFindOperaciones()");
        
        assertNotNull(operacionSicDao);
        Calendar cal = Calendar.getInstance();
        cal.set(2008, Calendar.JULY, 18, 0, 0, 0);
        OperacionSic operacionSic = new OperacionSic();
        operacionSic.setCuentaNombrada(new CuentaNombrada(new AgenteVO("01", "003", "0307")));
        operacionSic.setFechaConsulta(DateUtils.preparaIntervaloFechas(cal.getTime(), new Date()));
        PaginaVO paginaVO = new PaginaVO();
        paginaVO = operacionSicDao.findOperaciones(operacionSic, paginaVO, true);
        
        assertNotNull("No existe la pagina", paginaVO);
        assertNotNull("No hay total de registros", paginaVO.getTotalRegistros());
        assertTrue("No hay lista de registros", (paginaVO.getRegistros() != null && !paginaVO.getRegistros().isEmpty()));
        
        log.info("Total de registros= " + paginaVO.getTotalRegistros());
        for (Iterator iter = paginaVO.getRegistros().iterator(); iter.hasNext();) {
            OperacionSic element = (OperacionSic) iter.next();
            log.info("Registro [" + ReflectionToStringBuilder.toString(element) + "]");
        }
        
    }
    
    public void ttestFindOperaciones_4() {
        
        log.info("Ejectuando prueba testFindOperaciones()");
        
        assertNotNull(operacionSicDao);
        Calendar cal = Calendar.getInstance();
        cal.set(2008, Calendar.JULY, 18, 0, 0, 0);
        OperacionSic operacionSic = new OperacionSic();
        operacionSic.setCuentaNombrada(new CuentaNombrada(new AgenteVO("01", "003", "0307")));
        operacionSic.setFechaConsulta(DateUtils.preparaIntervaloFechas(cal.getTime(), new Date()));
        PaginaVO paginaVO = new PaginaVO();
        paginaVO.setRegistrosXPag(2);
        paginaVO = operacionSicDao.findOperaciones(operacionSic, paginaVO, true);
        
        assertNotNull("No existe la pagina", paginaVO);
        assertNotNull("No hay total de registros", paginaVO.getTotalRegistros());
        assertTrue("No hay lista de registros", (paginaVO.getRegistros() != null && !paginaVO.getRegistros().isEmpty()));
        
        log.info("Total de registros= " + paginaVO.getTotalRegistros());
        for (Iterator iter = paginaVO.getRegistros().iterator(); iter.hasNext();) {
            OperacionSic element = (OperacionSic) iter.next();
            log.info("Registro [" + ReflectionToStringBuilder.toString(element) + "]");
        }
        
    }
    
    public void ttestFindOperaciones_5() {
        
        log.info("Ejectuando prueba testFindOperaciones()");
        
        assertNotNull(operacionSicDao);
        Calendar cal = Calendar.getInstance();
        cal.set(2008, Calendar.SEPTEMBER, 17);
        OperacionSic operacionSic = new OperacionSic();
        AgenteVO agenteVO = new AgenteVO();
        agenteVO.setId("01");
        agenteVO.setFolio("003");
        operacionSic.setCuentaNombrada(new CuentaNombrada(agenteVO));
        operacionSic.setFechaConsulta(DateUtils.preparaIntervaloFechas(cal.getTime(), cal.getTime()));
        PaginaVO paginaVO = null;
        paginaVO = operacionSicDao.findOperaciones(operacionSic, paginaVO, true);
        
        assertNotNull("No existe la pagina", paginaVO);
        assertNotNull("No hay total de registros", paginaVO.getTotalRegistros());
        assertTrue("No hay lista de registros", (paginaVO.getRegistros() != null && !paginaVO.getRegistros().isEmpty()));
        
        log.info("Total de registros= " + paginaVO.getTotalRegistros());
        for (Iterator iter = paginaVO.getRegistros().iterator(); iter.hasNext();) {
            OperacionSic element = (OperacionSic) iter.next();
            log.info("Registro [" + ReflectionToStringBuilder.toString(element) + "]");
        }
        
    }
    
    public void testFindOperaciones_6() {
        
        log.info("Ejectuando prueba testFindOperaciones()");
        
        assertNotNull(operacionSicDao);
        Calendar cal = Calendar.getInstance();
        cal.set(2008, Calendar.NOVEMBER, 06, 0, 0, 0);
        OperacionSic operacionSic = new OperacionSic();
        operacionSic.setCuentaNombrada(new CuentaNombrada(new AgenteVO("01", "003", "0307")));
        operacionSic.setFechaConsulta(DateUtils.preparaIntervaloFechas(cal.getTime(), new Date()));
        PaginaVO paginaVO = null;
        paginaVO = operacionSicDao.findOperaciones(operacionSic, paginaVO, true);
        
        assertNotNull("No existe la pagina", paginaVO);
        assertNotNull("No hay total de registros", paginaVO.getTotalRegistros());
        assertTrue("No hay lista de registros", (paginaVO.getRegistros() != null && !paginaVO.getRegistros().isEmpty()));
        
        log.info("Total de registros= " + paginaVO.getTotalRegistros());
        for (Iterator iter = paginaVO.getRegistros().iterator(); iter.hasNext();) {
            OperacionSic element = (OperacionSic) iter.next();
            log.info("Registro [" + ReflectionToStringBuilder.toString(element) + "]");
            log.info("Registro.EstatusOperacion [" + ReflectionToStringBuilder.toString(element.getEstatusOperacion()) + "]");
        }
        
    }
    
}
