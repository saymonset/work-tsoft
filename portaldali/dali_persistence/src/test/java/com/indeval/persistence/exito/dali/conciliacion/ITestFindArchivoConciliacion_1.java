/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.exito.dali.conciliacion;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portaldali.persistence.dao.conciliacion.RegContValNombradaDao;
import com.indeval.portaldali.persistence.model.RegContValNombrada;
import com.indeval.portaldali.persistence.vo.AgentePK;
import com.indeval.portaldali.persistence.vo.EmisionPK;
import com.indeval.portaldali.persistence.vo.PageVO;


/**
 *
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class ITestFindArchivoConciliacion_1 extends BaseDaoTestCase {

    /** Objeto de loggeo */
    private static final Logger log = LoggerFactory.getLogger(ITestFindArchivoConciliacion_1.class);

    /** bean de cInstrumentoDao */
    private RegContValNombradaDao regContValNombradaDao;
    
    /**
     * 
     * @see com.indeval.persistence.unittest.BaseDaoTestCase#onSetUp()
     */
    protected void onSetUp() {

        super.onSetUp();
        regContValNombradaDao = (RegContValNombradaDao) getBean("regContValNombradaDao");

    }

    /**
     * Test Case para getVencimientosPendientesByInstitucionFechaVencimiento()
     * @throws Exception 
     */
    @SuppressWarnings("unchecked")
    public void testFindArchivoConciliacion_1() throws Exception {

        log.info("Entrando a ITestFindArchivoConciliacion_1.testFindArchivoConciliacion_1()");

        assertNotNull(regContValNombradaDao);
        
        AgentePK agenteFirmado = new AgentePK();
        agenteFirmado.setIdInst("01");
        agenteFirmado.setFolioInst("007");
        
        EmisionPK emisionPK = null;
        
        Map valores = new HashMap();
        PageVO pageParamsVO = new PageVO();
        pageParamsVO.setValores(valores);
        
        PageVO pageVO = 
            regContValNombradaDao.findArchivoConciliacion(agenteFirmado, emisionPK, pageParamsVO);
        this.validaPagina(pageVO);
        this.logElementosListaRegContValNombrada(pageVO.getRegistros());

    }
    
    /**
     * Test Case para getVencimientosPendientesByInstitucionFechaVencimiento()
     */
    @SuppressWarnings("unchecked")
    public void testFindArchivoConciliacion_2() throws Exception {

        log.info("Entrando a ITestFindArchivoConciliacion_1.testFindArchivoConciliacion_2()");

        assertNotNull(regContValNombradaDao);
        
        AgentePK agenteFirmado = new AgentePK();
        agenteFirmado.setIdInst("01");
        agenteFirmado.setFolioInst("007");
        agenteFirmado.setCuenta("0810");
        
        EmisionPK emisionPK = null;
        
        Map valores = new HashMap();
        PageVO pageParamsVO = new PageVO();
        pageParamsVO.setValores(valores);
        
        PageVO pageVO = 
            regContValNombradaDao.findArchivoConciliacion(agenteFirmado, emisionPK, pageParamsVO);
        this.validaPagina(pageVO);
        this.logElementosListaRegContValNombrada(pageVO.getRegistros());

    }
    
    /**
     * Test Case para getVencimientosPendientesByInstitucionFechaVencimiento()
     */
    @SuppressWarnings("unchecked")
    public void testFindArchivoConciliacion_3() throws Exception {

        log.info("Entrando a ITestFindArchivoConciliacion_1.testFindArchivoConciliacion_3()");

        assertNotNull(regContValNombradaDao);
        
        AgentePK agenteFirmado = new AgentePK();
        agenteFirmado.setIdInst("01");
        agenteFirmado.setFolioInst("007");
        agenteFirmado.setCuenta("0810");
        
        EmisionPK emisionPK = new EmisionPK();
        
        Map valores = new HashMap();
        PageVO pageParamsVO = new PageVO();
        pageParamsVO.setValores(valores);
        
        PageVO pageVO = 
            regContValNombradaDao.findArchivoConciliacion(agenteFirmado, emisionPK, pageParamsVO);
        this.validaPagina(pageVO);
        this.logElementosListaRegContValNombrada(pageVO.getRegistros());

    }
    
    /**
     * Test Case para getVencimientosPendientesByInstitucionFechaVencimiento()
     */
    @SuppressWarnings("unchecked")
    public void testFindArchivoConciliacion_4() throws Exception {

        log.info("Entrando a ITestFindArchivoConciliacion_1.testFindArchivoConciliacion_4()");

        assertNotNull(regContValNombradaDao);
        
        AgentePK agenteFirmado = new AgentePK();
        agenteFirmado.setIdInst("01");
        agenteFirmado.setFolioInst("007");
        agenteFirmado.setCuenta("0810");
        
        EmisionPK emisionPK = new EmisionPK();
        emisionPK.setTv("BI");
        
        Map valores = new HashMap();
        PageVO pageParamsVO = new PageVO();
        pageParamsVO.setValores(valores);
        
        PageVO pageVO = 
            regContValNombradaDao.findArchivoConciliacion(agenteFirmado, emisionPK, pageParamsVO);
        this.validaPagina(pageVO);
        this.logElementosListaRegContValNombrada(pageVO.getRegistros());

    }
    
    /**
     * Test Case para getVencimientosPendientesByInstitucionFechaVencimiento()
     */
    @SuppressWarnings("unchecked")
    public void testFindArchivoConciliacion_5() throws Exception {
        
        log.info("Entrando a ITestFindArchivoConciliacion_1.testFindArchivoConciliacion_5()");

        assertNotNull(regContValNombradaDao);
        
        AgentePK agenteFirmado = new AgentePK();
        agenteFirmado.setIdInst("01");
        agenteFirmado.setFolioInst("007");
        agenteFirmado.setCuenta("0810");
        
        EmisionPK emisionPK = new EmisionPK();
        emisionPK.setTv("BI");
        emisionPK.setEmisora("GOBFED");
        
        Map valores = new HashMap();
        PageVO pageParamsVO = new PageVO();
        pageParamsVO.setValores(valores);
        
        PageVO pageVO = 
            regContValNombradaDao.findArchivoConciliacion(agenteFirmado, emisionPK, pageParamsVO);
        this.validaPagina(pageVO);
        this.logElementosListaRegContValNombrada(pageVO.getRegistros());

    }
    
    /**
     * Test Case para getVencimientosPendientesByInstitucionFechaVencimiento()
     */
    @SuppressWarnings("unchecked")
    public void testFindArchivoConciliacion_6() throws Exception {

        log.info("Entrando a ITestFindArchivoConciliacion_1.testFindArchivoConciliacion_6()");

        assertNotNull(regContValNombradaDao);
        
        AgentePK agenteFirmado = new AgentePK();
        agenteFirmado.setIdInst("01");
        agenteFirmado.setFolioInst("007");
        agenteFirmado.setCuenta("0810");
        
        EmisionPK emisionPK = new EmisionPK();
        emisionPK.setTv("BI");
        emisionPK.setEmisora("GOBFED");
        emisionPK.setSerie("080911");
        
        Map valores = new HashMap();
        PageVO pageParamsVO = new PageVO();
        pageParamsVO.setValores(valores);
        
        PageVO pageVO = 
            regContValNombradaDao.findArchivoConciliacion(agenteFirmado, emisionPK, pageParamsVO);
        this.validaPagina(pageVO);
        this.logElementosListaRegContValNombrada(pageVO.getRegistros());

    }
    
    /**
     * Test Case para getVencimientosPendientesByInstitucionFechaVencimiento()
     */
    @SuppressWarnings("unchecked")
    public void testFindArchivoConciliacion_7() throws Exception {

        log.info("Entrando a ITestFindArchivoConciliacion_1.testFindArchivoConciliacion_7()");

        assertNotNull(regContValNombradaDao);
        
        AgentePK agenteFirmado = new AgentePK();
        agenteFirmado.setIdInst("01");
        agenteFirmado.setFolioInst("007");
        agenteFirmado.setCuenta("0810");
        
        EmisionPK emisionPK = new EmisionPK();
        emisionPK.setTv("BI");
        emisionPK.setEmisora("GOBFED");
        emisionPK.setSerie("080911");
        emisionPK.setCupon("0000");
        
        Map valores = new HashMap();
        PageVO pageParamsVO = new PageVO();
        pageParamsVO.setValores(valores);
        
        PageVO pageVO = 
            regContValNombradaDao.findArchivoConciliacion(agenteFirmado, emisionPK, pageParamsVO);
        this.validaPagina(pageVO);
        this.logElementosListaRegContValNombrada(pageVO.getRegistros());

    }    
    
    /**
     * Test Case para getVencimientosPendientesByInstitucionFechaVencimiento()
     */
    @SuppressWarnings("unchecked")
    public void testFindArchivoConciliacion_8() throws Exception {

        log.info("Entrando a ITestFindArchivoConciliacion_1.testFindArchivoConciliacion_8()");

        assertNotNull(regContValNombradaDao);
        
        AgentePK agenteFirmado = new AgentePK();
        agenteFirmado.setIdInst("01");
        agenteFirmado.setFolioInst("007");
        agenteFirmado.setCuenta("0810");
        
        EmisionPK emisionPK = new EmisionPK();
        emisionPK.setTv("BI");
        emisionPK.setEmisora("GOBFED");
        emisionPK.setSerie("080911");
        emisionPK.setCupon("0000");
        
        Map valores = new HashMap();
        valores.put("MOV", "MOV");
        PageVO pageParamsVO = new PageVO();
        pageParamsVO.setValores(valores);
        
        PageVO pageVO = 
            regContValNombradaDao.findArchivoConciliacion(agenteFirmado, emisionPK, pageParamsVO);
        this.validaPagina(pageVO);
        this.logElementosListaRegContValNombrada(pageVO.getRegistros());

    }    
    
    /**
     * Envia al Log algunos de los atributos de los elementos RegContValNombrada
     * contenidos en la lista que recibe.
     * @param lista
     */
    private void logElementosListaRegContValNombrada(List lista) {
        
        log.info("Imprimiendo los elementos RegContValNombrada...");
        int indice = 1;
        for (Iterator iterator = lista.iterator(); iterator.hasNext();) {

            log.debug("Imprimiendo el elemento " + indice);
            RegContValNombrada elemento = (RegContValNombrada) iterator.next();
            log.debug(elemento.getCantidad().toString());
            log.debug(elemento.getCargoA().toString());
            log.debug(elemento.getFecha().toString());
            log.debug(elemento.getIdCiclo().toString());
            log.debug(elemento.getIdCuenta().toString());
            log.debug(elemento.getIdOperacion().toString());
            log.debug(elemento.getIdPosicion().toString());
            log.debug(elemento.getIdRegContableControlada().toString());
            log.debug(elemento.getIdRegistroContable().toString());
            
            ++indice;
        }
        
    }


}
