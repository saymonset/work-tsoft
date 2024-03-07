/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.exito.dali.conciliacion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portaldali.persistence.dao.conciliacion.OperacionNombradaDao;
import com.indeval.portaldali.persistence.model.OperacionNombrada;
import com.indeval.portaldali.persistence.util.UtilsLog;
import com.indeval.portaldali.persistence.vo.AgentePK;
import com.indeval.portaldali.persistence.vo.EmisionPK;
import com.indeval.portaldali.persistence.vo.PageVO;


/**
 *
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class ITestFindMovimientosArchivoConciliacion_1 extends BaseDaoTestCase {

    /** Objeto de loggeo */
    private static final Logger log = LoggerFactory.getLogger(ITestFindMovimientosArchivoConciliacion_1.class);

    /** bean de cInstrumentoDao */
    private OperacionNombradaDao operacionNombradaDao;
    
    /**
     * 
     * @see com.indeval.persistence.unittest.BaseDaoTestCase#onSetUp()
     */
    protected void onSetUp() {

        super.onSetUp();
        operacionNombradaDao = (OperacionNombradaDao) getBean("operacionNombradaDao");

    }

    /**
     * Test Case para getVencimientosPendientesByInstitucionFechaVencimiento()
     * @throws Exception 
     */
    @SuppressWarnings("unchecked")
    public void testFindArchivoConciliacion_1() throws Exception {

        log.info("Entrando a ITestFindArchivoConciliacion_1.testFindArchivoConciliacion_1()");

        assertNotNull(operacionNombradaDao);
        
        AgentePK agenteFirmado = new AgentePK();
        agenteFirmado.setIdInst("02");
        agenteFirmado.setFolioInst("033");
        
        EmisionPK emisionPK = null;
        
        Map valores = new HashMap();
        PageVO pageParamsVO = new PageVO();
        pageParamsVO.setValores(valores);
        
        PageVO pageVO = 
        	operacionNombradaDao.findMovimientosArchivoConciliacion(agenteFirmado, emisionPK, pageParamsVO);
        this.validaPagina(pageVO);
        this.logElementosListaOperacionNombrada(pageVO.getRegistros());

    }
    
    /**
     * Test Case para getVencimientosPendientesByInstitucionFechaVencimiento()
     */
    @SuppressWarnings("unchecked")
    public void testFindArchivoConciliacion_2() throws Exception {

        log.info("Entrando a ITestFindArchivoConciliacion_1.testFindArchivoConciliacion_2()");

        assertNotNull(operacionNombradaDao);
        
        AgentePK agenteFirmado = new AgentePK();
        agenteFirmado.setIdInst("02");
        agenteFirmado.setFolioInst("033");
        agenteFirmado.setCuenta("8814");
        
        EmisionPK emisionPK = null;
        
        Map valores = new HashMap();
        PageVO pageParamsVO = new PageVO();
        pageParamsVO.setValores(valores);
        
        PageVO pageVO = 
        	operacionNombradaDao.findMovimientosArchivoConciliacion(agenteFirmado, emisionPK, pageParamsVO);
        this.validaPagina(pageVO);
        this.logElementosListaOperacionNombrada(pageVO.getRegistros());

    }
    
    /**
     * Test Case para getVencimientosPendientesByInstitucionFechaVencimiento()
     */
    @SuppressWarnings("unchecked")
    public void testFindArchivoConciliacion_3() throws Exception {

        log.info("Entrando a ITestFindArchivoConciliacion_1.testFindArchivoConciliacion_3()");

        assertNotNull(operacionNombradaDao);
        
        AgentePK agenteFirmado = new AgentePK();
        agenteFirmado.setIdInst("02");
        agenteFirmado.setFolioInst("033");
        agenteFirmado.setCuenta("8814");
        
        EmisionPK emisionPK = new EmisionPK();
        
        Map valores = new HashMap();
        PageVO pageParamsVO = new PageVO();
        pageParamsVO.setValores(valores);
        
        PageVO pageVO = 
        	operacionNombradaDao.findMovimientosArchivoConciliacion(agenteFirmado, emisionPK, pageParamsVO);
        this.validaPagina(pageVO);
        this.logElementosListaOperacionNombrada(pageVO.getRegistros());

    }
    
    /**
     * Test Case para getVencimientosPendientesByInstitucionFechaVencimiento()
     */
    @SuppressWarnings("unchecked")
    public void testFindArchivoConciliacion_4() throws Exception {

        log.info("Entrando a ITestFindArchivoConciliacion_1.testFindArchivoConciliacion_4()");

        assertNotNull(operacionNombradaDao);
        
        AgentePK agenteFirmado = new AgentePK();
        agenteFirmado.setIdInst("02");
        agenteFirmado.setFolioInst("033");
        agenteFirmado.setCuenta("8814");
        
        EmisionPK emisionPK = new EmisionPK();
        emisionPK.setTv("M");
        
        Map valores = new HashMap();
        PageVO pageParamsVO = new PageVO();
        pageParamsVO.setValores(valores);
        
        PageVO pageVO = 
        	operacionNombradaDao.findMovimientosArchivoConciliacion(agenteFirmado, emisionPK, pageParamsVO);
        this.validaPagina(pageVO);
        this.logElementosListaOperacionNombrada(pageVO.getRegistros());

    }
    
    /**
     * Test Case para getVencimientosPendientesByInstitucionFechaVencimiento()
     */
    @SuppressWarnings("unchecked")
    public void testFindArchivoConciliacion_5() throws Exception {
        
        log.info("Entrando a ITestFindArchivoConciliacion_1.testFindArchivoConciliacion_5()");

        assertNotNull(operacionNombradaDao);
        
        AgentePK agenteFirmado = new AgentePK();
        agenteFirmado.setIdInst("02");
        agenteFirmado.setFolioInst("033");
        agenteFirmado.setCuenta("8814");
        
        EmisionPK emisionPK = new EmisionPK();
        emisionPK.setTv("M");
        emisionPK.setEmisora("GOBFED");
        
        Map valores = new HashMap();
        PageVO pageParamsVO = new PageVO();
        pageParamsVO.setValores(valores);
        
        PageVO pageVO = 
        	operacionNombradaDao.findMovimientosArchivoConciliacion(agenteFirmado, emisionPK, pageParamsVO);
        this.validaPagina(pageVO);
        this.logElementosListaOperacionNombrada(pageVO.getRegistros());

    }
    
    /**
     * Test Case para getVencimientosPendientesByInstitucionFechaVencimiento()
     */
    @SuppressWarnings("unchecked")
    public void testFindArchivoConciliacion_6() throws Exception {

        log.info("Entrando a ITestFindArchivoConciliacion_1.testFindArchivoConciliacion_6()");

        assertNotNull(operacionNombradaDao);
        
        AgentePK agenteFirmado = new AgentePK();
        agenteFirmado.setIdInst("02");
        agenteFirmado.setFolioInst("033");
        agenteFirmado.setCuenta("8814");
        
        EmisionPK emisionPK = new EmisionPK();
        emisionPK.setTv("M");
        emisionPK.setEmisora("GOBFED");
        emisionPK.setSerie("111222");
        
        Map valores = new HashMap();
        PageVO pageParamsVO = new PageVO();
        pageParamsVO.setValores(valores);
        
        PageVO pageVO = 
        	operacionNombradaDao.findMovimientosArchivoConciliacion(agenteFirmado, emisionPK, pageParamsVO);
        this.validaPagina(pageVO);
        this.logElementosListaOperacionNombrada(pageVO.getRegistros());

    }
    
    /**
     * Test Case para getVencimientosPendientesByInstitucionFechaVencimiento()
     */
    @SuppressWarnings("unchecked")
    public void testFindArchivoConciliacion_7() throws Exception {

        log.info("Entrando a ITestFindArchivoConciliacion_1.testFindArchivoConciliacion_7()");

        assertNotNull(operacionNombradaDao);
        
        AgentePK agenteFirmado = new AgentePK();
        agenteFirmado.setIdInst("02");
        agenteFirmado.setFolioInst("033");
        agenteFirmado.setCuenta("8814");
        
        EmisionPK emisionPK = new EmisionPK();
        emisionPK.setTv("M");
        emisionPK.setEmisora("GOBFED");
        emisionPK.setSerie("111222");
        emisionPK.setCupon("0000");
        
        Map valores = new HashMap();
        PageVO pageParamsVO = new PageVO();
        pageParamsVO.setValores(valores);
        
        PageVO pageVO = 
        	operacionNombradaDao.findMovimientosArchivoConciliacion(agenteFirmado, emisionPK, pageParamsVO);
        this.validaPagina(pageVO);
        this.logElementosListaOperacionNombrada(pageVO.getRegistros());

    }    
    
    /**
     * Test Case para getVencimientosPendientesByInstitucionFechaVencimiento()
     */
    @SuppressWarnings("unchecked")
    public void testFindArchivoConciliacion_8() throws Exception {

        log.info("Entrando a ITestFindArchivoConciliacion_1.testFindArchivoConciliacion_8()");

        assertNotNull(operacionNombradaDao);
        
        AgentePK agenteFirmado = new AgentePK();
        agenteFirmado.setIdInst("02");
        agenteFirmado.setFolioInst("033");
        agenteFirmado.setCuenta("8814");
        
        EmisionPK emisionPK = new EmisionPK();
        emisionPK.setTv("M");
        emisionPK.setEmisora("GOBFED");
        emisionPK.setSerie("111222");
        emisionPK.setCupon("0000");
        
        Map valores = new HashMap();
        valores.put("MOV", "MOV");
        PageVO pageParamsVO = new PageVO();
        pageParamsVO.setValores(valores);
        
        PageVO pageVO = 
        	operacionNombradaDao.findMovimientosArchivoConciliacion(agenteFirmado, emisionPK, pageParamsVO);
        this.validaPagina(pageVO);
        this.logElementosListaOperacionNombrada(pageVO.getRegistros());

    }    
    
    /**
     * Envia al Log algunos de los atributos de los elementos RegContValNombrada
     * contenidos en la lista que recibe.
     * @param lista
     */
    @SuppressWarnings("unchecked")
	private void logElementosListaOperacionNombrada(List lista) {
        
        log.debug("Imprimiendo los elementos OperacionNombrada...");
        int indice = 1;
        
        List listIdsPosicion = new ArrayList();
        for (Iterator iterator = lista.iterator(); iterator.hasNext();) {
            
            log.debug("Imprimiendo el elemento " + indice);
            OperacionNombrada operacionNombrada = (OperacionNombrada) iterator.next();
            log.debug("CargoEfectivoA" + operacionNombrada.getCargoEfectivoA());
            log.debug("CargoValoresA" + operacionNombrada.getCargoValoresA());
            log.debug("FolioOperacion" + operacionNombrada.getFolioOperacion());
            log.debug("Monto" + operacionNombrada.getMonto());
            log.debug("NumeroTitulos" + operacionNombrada.getNumeroTitulos());
            log.debug("ParticipanteOrigen" + operacionNombrada.getParticipanteOrigen());
            log.debug("Precio" + operacionNombrada.getPrecio());
            if(operacionNombrada.getCuentaNombradaTraspasante()!=null){
            	log.debug("Cuenta" + 
            			operacionNombrada.getCuentaNombradaTraspasante().getCuenta());
            	log.debug("FechaAlta" + 
            			operacionNombrada.getCuentaNombradaTraspasante().getFechaAlta());
            	log.debug("FechaBaja" + 
            			operacionNombrada.getCuentaNombradaTraspasante().getFechaBaja());
            	log.debug("Iban" + 
            			operacionNombrada.getCuentaNombradaTraspasante().getIban());
            	log.debug("NombreCuenta" + 
            			operacionNombrada.getCuentaNombradaTraspasante().getNombreCuenta());
            	log.debug("RazonSocialCuenta" + 
            			operacionNombrada.getCuentaNombradaTraspasante().getRazonSocialCuenta());
            }
            if(operacionNombrada.getCuentaNombradaReceptor()!=null){
            	log.debug("Cuenta" + 
            			operacionNombrada.getCuentaNombradaReceptor().getCuenta());
            	log.debug("FechaAlta" + 
            			operacionNombrada.getCuentaNombradaReceptor().getFechaAlta());
            	log.debug("FechaBaja" + 
            			operacionNombrada.getCuentaNombradaReceptor().getFechaBaja());
            	log.debug("Iban" + 
            			operacionNombrada.getCuentaNombradaReceptor().getIban());
            	log.debug("NombreCuenta" + 
            			operacionNombrada.getCuentaNombradaReceptor().getNombreCuenta());
            	log.debug("RazonSocialCuenta" + 
            			operacionNombrada.getCuentaNombradaReceptor().getRazonSocialCuenta());            	
            }
            if(operacionNombrada.getPosicionTraspasante() != null){
            	log.debug("idPosicionTraspasante = [" + 
            			operacionNombrada.getPosicionTraspasante().getIdPosicion() + "]");
            	listIdsPosicion.add(operacionNombrada.getPosicionTraspasante().getIdPosicion().intValue());
            }
            if(operacionNombrada.getPosicionReceptor() != null){
            	log.debug("idPosicionReceptor = [" + 
            			operacionNombrada.getPosicionReceptor().getIdPosicion() + "]");
            	listIdsPosicion.add(operacionNombrada.getPosicionReceptor().getIdPosicion().intValue());
            }
            
            ++indice;
        }
        
        UtilsLog.logElementosLista(listIdsPosicion);
        
    }


}
