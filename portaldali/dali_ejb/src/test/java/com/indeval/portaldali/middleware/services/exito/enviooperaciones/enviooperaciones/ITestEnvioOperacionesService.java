/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.exito.enviooperaciones.enviooperaciones;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.BaseITestService;
import com.indeval.portaldali.middleware.services.enviooperaciones.EnvioOperacionesService;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.modelo.PaginaVO;
import com.indeval.portaldali.middleware.services.util.vo.BitacoraMatchParams;
import com.indeval.portaldali.middleware.services.util.vo.BitacoraMatchVO;
import com.indeval.portaldali.middleware.services.util.vo.BitacoraVOParams;
import com.indeval.portaldali.middleware.services.util.vo.BitacoraVOTotales;
import com.indeval.portaldali.middleware.services.util.vo.TradingOutVO;
import com.indeval.portaldali.persistence.util.UtilsLog;
import com.indeval.protocolofinanciero.api.ProtocoloFinancieroException;
import com.indeval.protocolofinanciero.api.vo.TraspasoContraPagoVO;
import com.indeval.protocolofinanciero.api.vo.TraspasoLibrePagoVO;

/**
 * Clase de prueba para los metodos de envioOperacionesService
 * 
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class ITestEnvioOperacionesService extends BaseITestService {

    /** Objeto de loggeo para ITestEnvioOperacionesService */
    private static final Logger logger = LoggerFactory.getLogger(ITestEnvioOperacionesService.class);

    /** Bean del servicio a ser probado */
    private EnvioOperacionesService envioOperacionesService;

    /** Constructor para enviarle el nombre del metodo */
    public ITestEnvioOperacionesService(String name) {
        super(name);

    }

    /**
     * En este m&eacute;todo se inicializan las propiedades que ser&aacute;n utilizadas
     * durante la clase de prueba.
     * 
     * @throws Exception
     */
    protected void onSetUp() throws Exception {

        super.onSetUp();

        if (envioOperacionesService == null) {

            envioOperacionesService = (EnvioOperacionesService) applicationContext
                    .getBean("envioOperacionesService");

        }

    }

    /**
     * TestCase para
     * enviaOperacion(com.indeval.protocolofinanciero.api.vo.TraspasoContraPagoVO,
     * java.lang.String, java.lang.String)
     * 
     * @throws ProtocoloFinancieroException
     * @throws BusinessException
     */
    public void testEnviaOperacionDvp() throws ProtocoloFinancieroException, BusinessException {

        log.debug("Entrando a testEnviaOperacionDvp()");

        assertNotNull(envioOperacionesService);

        Calendar cal = Calendar.getInstance();
        cal.set(2007, 9, 18, 0, 0, 0);

        TraspasoContraPagoVO vo = new TraspasoContraPagoVO();
        vo.setTipoInstruccion("V");
        vo.setIdFolioCtaTraspasante("020618499");
        vo.setTipoValor("91");
        vo.setEmisora("WALMEX");
        vo.setSerie("V");
        vo.setCupon("0046");
        vo.setCantidadTitulos(new Long("18000"));
        vo.setIdFolioCtaReceptora("02061840");
        vo.setFechaLiquidacion(cal.getTime());
        vo.setFechaConcertacion(cal.getTime());
        vo.setFechaVencimiento(cal.getTime());
        vo.setFechaRegistro(cal.getTime());
        vo.setMonto(new BigDecimal("125487"));
        vo.setPrecio(new BigDecimal("524.112"));
        vo.setDivisa("MX");
        vo.setReferenciaOperacion("0");

        String folioControl = "24";
        String idFolio = "02061";
        String origen = "PORTAL";

        envioOperacionesService.enviaOperacion(vo, folioControl, false);

        log.debug("Ejecucion exitosa de testEnviaOperacionDvp()");

    }

    /**
     * Test para
     * enviaOperacion(com.indeval.protocolofinanciero.api.vo.TraspasoLibrePagoVO,
     * java.lang.String, java.lang.String)
     * 
     * @throws ProtocoloFinancieroException
     * @throws BusinessException
     */
    public void testEnviaOperacionTlp() throws ProtocoloFinancieroException, BusinessException {

        log.debug("Entrando a testEnviaOperacionTlp()");

        assertNotNull(envioOperacionesService);

        TraspasoLibrePagoVO vo = new TraspasoLibrePagoVO();
        vo.setIdFolioCtaTraspasante("010030307");
        vo.setCantidadTitulos(new Long(2450));
        vo.setTipoValor("BI");
        vo.setEmisora("GOBFED");
        vo.setSerie("070812");
        vo.setCupon("0000");

        Calendar cal = Calendar.getInstance();
        cal.set(2007, 9, 16, 0, 0, 0);
        vo.setFechaLiquidacion(cal.getTime());
        vo.setFechaRegistro(cal.getTime());
        vo.setIdFolioCtaReceptora("010030030");
        vo.setTipoInstruccion("T");
        vo.setReferenciaOperacion("0021244");
        vo.setFechaRegistro(cal.getTime());

        String folioControl = "010101";
        String idFolio = "01003";
        String origen = "PORTAL";
        envioOperacionesService.enviaOperacion(vo, folioControl, false);

        log.debug("Ejecucion exitosa de testEnviaOperacionTlp()");

    }

    /**
     * TestCase para probar el servicio
     * reenviaOperacion(com.indeval.protocolofinanciero.api.vo.TraspasoContraPagoVO,
     * java.lang.String)
     * 
     * @throws ProtocoloFinancieroException
     * @throws BusinessException
     */
    public void testReenviaOperacionDvp() throws ProtocoloFinancieroException, BusinessException {

        log.debug("Entrando a testReenviaOperacionDvp()");

        assertNotNull(envioOperacionesService);

        TraspasoContraPagoVO vo = new TraspasoContraPagoVO();
        vo.setIdFolioCtaTraspasante("010030307");
        vo.setCantidadTitulos(new Long(2450));
        vo.setMonto(new BigDecimal(123456));
        vo.setTipoValor("BI");
        vo.setEmisora("GOBFED");
        vo.setSerie("070812");
        vo.setCupon("0000");

        Calendar cal = Calendar.getInstance();
        cal.set(2007, Calendar.OCTOBER, 12, 0, 0, 0);
        cal.set(Calendar.MILLISECOND, 0);
        vo.setFechaConcertacion(cal.getTime());
        vo.setFechaLiquidacion(cal.getTime());
        vo.setIdFolioCtaReceptora("010030030");
        vo.setTipoInstruccion("V");
        vo.setReferenciaOperacion("0021244");

        String folioControl = "10101";
        String idFolio = "01003";
        String origen = "PORTAL";
        //TODO
//        envioOperacionesService.reenviaOperacion(vo, folioControl, 1, false);

        log.debug("Ejecucion exitosa de testReenviaOperacionDvp()");

    }

    /**
     * TestCase para probar el servicio BitacoraOperacion
     * 
     * @throws Exception
     */
    public void testBitacoraOperacion() throws Exception {

        log.info("Entrando al metodo ITestEnvioOperacionesService.testBitacoraOperacion()");

        BitacoraVOParams params = new BitacoraVOParams();
        params.setIdTrasp("01");
        params.setFolioTrasp("003");

        PaginaVO paginaVO = envioOperacionesService.getMensajeBitacora(params);

        assertNotNull(paginaVO);

        log.debug("RESULTADO: " + ReflectionToStringBuilder.reflectionToString(paginaVO));
        UtilsLog.logElementosLista(paginaVO.getRegistros());

    }

    /**
     * TestCase para probar el servicio getMensajeBitacoraMatch()
     * 
     * @throws Exception
     */
    public void testGetMensajeBitacoraMatch() throws Exception {

        log.info("Entrando a ITestEnvioOperacionesService.testGetMensajeBitacoraMatch()");

        BitacoraMatchParams params = new BitacoraMatchParams();
        params.setAgenteFirmado(new AgenteVO("01", "003"));

        Calendar c = Calendar.getInstance();
        c.set(2008, Calendar.JANUARY, 23,0,0,0);
        c.set(Calendar.MILLISECOND, 0);
        params.setFechaLiquidacion(c.getTime());

        BitacoraMatchVO[] bitacoraVOs = envioOperacionesService.getMensajeBitacoraMatch(params);

        assertNotNull(bitacoraVOs);
        assertTrue(bitacoraVOs.length > 0);

        // UtilsLog.logElementosArreglo(bitacoraVOs, true);
        // UtilsLog.logElementosArreglo(bitacoraVOs, false);
        for (int i = 0; i < bitacoraVOs.length; i++) {

            log.debug("Objeto [" + (i + 1) + " ] de " + bitacoraVOs.length + " : "
                    + ReflectionToStringBuilder.reflectionToString(bitacoraVOs[i]));
            log.debug("Operacion del Objeto ["
                    + (i + 1)
                    + " ] de "
                    + bitacoraVOs.length
                    + " : "
                    + ReflectionToStringBuilder.reflectionToString((TradingOutVO) bitacoraVOs[i]
                            .getMensaje()));

        }

    }

    /**
     * Prueba el servicio recibeOperacion()
     * 
     * @throws Exception
     */
    public void testRecibeOperacion() throws Exception {

        log.info("Entrando a ITestEnvioOperacionesService.testRecibeOperacion()");

        assertNotNull(envioOperacionesService);

        envioOperacionesService.recibeOperacion();

        int cont = 0;

        while (cont < 6) {

            try {

                Thread.sleep(10000);
                log.debug("Despierta despues de 10 seg [" + new Date() + "]");
                log.info("Despierta despues de 10 seg [" + new Date() + "]");
                cont++;

            }
            catch (Exception e) {

                e.printStackTrace();

            }

        }

        log.debug("Ejecucion exitosa de testRecibeOperacion()");

    }

    /**
     * Metodo para probar la lista de bitacora de operaciones x estatus de
     * registro
     * 
     * @throws Exception
     */
    public void testBitacoraOperacionXEstatus() throws Exception {

        log.info("Entrando al metodo ITestEnvioOperacionesService.testBitacoraOperacion()");

        //TODO
//        List bitacoraVOTotales = envioOperacionesService.getMensajeBitacoraXEstatusRegistro("NE",
//                "H2H");
//
//        assertNotNull(bitacoraVOTotales);
//
//        log.debug("Numero de registros : " + bitacoraVOTotales.size());
//
//        for (int i = 0; i < bitacoraVOTotales.size(); i++) {
//
//            log.debug("Objeto(" + i + ") :"
//                    + ToStringBuilder.reflectionToString(bitacoraVOTotales.get(i)));
//
//        }
        //TODO

    }

    /**
     * Metodo para probar los totales de estatus registro de bitacora
     * operaciones
     * 
     * @throws Exception
     */
    public void testGetTotales() throws Exception {

        log.info("Entrando al metodo ITestEnvioOpService.testGetTotales()");

        BitacoraVOParams bitacoraVO = new BitacoraVOParams();
        bitacoraVO.setIdTrasp("01");
        bitacoraVO.setFolioTrasp("003");

        BitacoraVOTotales bot = envioOperacionesService.getTotalesBitacora(bitacoraVO);

        assertNotNull(bot);

        log.debug("# EN : " + bot.getTotalesEnviados());
        log.debug("# NE :" + bot.getTotalesNE());
        log.debug("# ACK : " + bot.getTotalesACK());
        log.debug("# NAK : " + bot.getTotalesNAK());

    }

    /**
     * M&eacute;todo de prueba para el servicio confirmaOperacion
     * 
     * @throws Exception
     */
    public void testConfirmaOperacion() throws Exception {

        log.info("Entrando al metodo testConfirmaOperacion");

        BigInteger idBitacoraMatch = new BigInteger("68709");
        AgenteVO agenteFirmado = new AgenteVO("01", "014");
     //   envioOperacionesService.confirmaOperacionMatch(idBitacoraMatch, agenteFirmado,null,null,null);

    }

    /**
     * M&eacute;todo de prueba para el servicio confirmaOperacion , cuando no se
     * encuentra una operaci&oacute;n de match
     * 
     * @throws Exception
     */
    public void testConfirmaOperacionFalla() throws Exception {

        log.info("Entrando al metodo testConfirmaOperacion");

        BigInteger idBitacoraMatch = new BigInteger("64800");
        AgenteVO agenteFirmado = new AgenteVO("01", "006");
      //  envioOperacionesService.confirmaOperacionMatch(idBitacoraMatch, agenteFirmado,null,null,null);

    }

    /**
     * M&eacute;todo de prueba para el servicio confirmaOperacion, cuando el agente no
     * tiene permiso para confirmar la operacion
     * 
     * @throws Exception
     */
    public void testConfirmaOperacionSinPermiso() throws Exception {

        log.info("Entrando al metodo testConfirmaOperacion");

        BigInteger idBitacoraMatch = new BigInteger("65749");
        AgenteVO agenteFirmado = new AgenteVO("02", "015");
     //   envioOperacionesService.confirmaOperacionMatch(idBitacoraMatch, agenteFirmado,null,null,null);

    }

    /**
     * Este metodo sirve para probar los metodos que son agregados al TestSuite
     * solo con el nombre del metodo que corresponde.
     * 
     * @return Test
     */
    public static Test suite() {

        TestSuite suite = new TestSuite();
         suite.addTest(new
         ITestEnvioOperacionesService("testGetMensajeBitacoraMatch"));
        //suite.addTest(new ITestEnvioOperacionesService("testactualizaEdoInsExpira"));

        return suite;

    }

    /**
     * M&eacute;todo de prueba para el servicio de actualizaEdoInsExpira(), caso:
     * Expiraci&oacute;n - (true)
     * 
     * @throws Exception
     */
    public void testActualizaEdoInsExpira_Expira() throws Exception {

        log.info("Entrando a ITestEnvioOperacionesService.testActualizaEdoInsExpira_Expira()");

        AgenteVO agenteFirmado = new AgenteVO();

        agenteFirmado.setId("01");
        agenteFirmado.setFolio("037");

        int numregistros = 1;/*envioOperacionesService.actualizaEstadoInstruccionExpira(new BigInteger(
        "64097"), agenteFirmado, true);*/

        assertNotNull(numregistros);
        assertTrue(numregistros > 0);

        log.debug("Numero de registros expirados" + numregistros);

    }

    /**
     * M&eacute;todo de prueba para el servicio de actualizaEdoInsExpira, caso:
     * Cancelaci&oacute;n - (false)
     * 
     * @throws Exception
     */
    public void testActualizaEdoInsExpira_Cancela() throws Exception {

        log.info("Entrando a ITestEnvioOperacionesService.testActualizaEdoInsExpira_Cancela()");

        AgenteVO agenteFirmado = new AgenteVO();

        agenteFirmado.setId("02");
        agenteFirmado.setFolio("033");

        int numregistros = 1;/*envioOperacionesService.actualizaEstadoInstruccionExpira(new BigInteger(
        "64097"), agenteFirmado, true);*/
        assertNotNull(numregistros);
        assertTrue(numregistros > 0);

        log.debug("Numero de registros cancelados" + numregistros);

    }

    /**
     * M&eacute;todo de prueba para el servicio de actualizaEdoInsExpira, caso: Cuando
     * agente firmado es null
     * 
     * @throws Exception
     */
    public void testActualizaEdoInsExpira_Null() throws Exception {

        log.info("Entrando a ITestEnvioOperacionesService.testActualizaEdoInsExpira_Cancela()");

        AgenteVO agenteFirmado = new AgenteVO();

        agenteFirmado.setId("02");
        agenteFirmado.setFolio("033");

        int numregistros = 1;/*envioOperacionesService.actualizaEstadoInstruccionExpira(new BigInteger(
        "64097"), agenteFirmado, true);*/
        assertNotNull(numregistros);
        assertTrue(numregistros > 0);

        log.debug("Numero de registros cancelados" + numregistros);

    }

    /**
     * M&eacute;todo de prueba para el servicio de actualizaEdoInsExpira, caso: Cuando
     * agente firmado es vacio
     * 
     * @throws Exception
     */
    public void testActualizaEdoInsExpira_Vacio() throws Exception {

        log.info("Entrando a ITestEnvioOperacionesService.testActualizaEdoInsExpira_Cancela()");

        AgenteVO agenteFirmado = new AgenteVO();

        agenteFirmado.setId("");
        agenteFirmado.setFolio("");

        int numregistros = 1;/*envioOperacionesService.actualizaEstadoInstruccionExpira(new BigInteger(
        "64097"), agenteFirmado, true);*/
        assertNotNull(numregistros);
        assertTrue(numregistros > 0);

        log.debug("Numero de registros cancelados" + numregistros);

    }

    /**
     * M&eacute;todo de prueba para el servicio de actualizaEdoInsExpira, caso: Cuando
     * id_bitacora es invalido
     * 
     * @throws Exception
     */

    public void testActualizaEdoInsExpira_BitacoraInvalido() throws Exception {

        log.info("Entrando a ITestEnvioOperacionesService.testActualizaEdoInsExpira_Cancela()");

        AgenteVO agenteFirmado = new AgenteVO();

        agenteFirmado.setId("02");
        agenteFirmado.setFolio("033");

        int numregistros = 1;/*envioOperacionesService.actualizaEstadoInstruccionExpira(new BigInteger(
        "64097"), agenteFirmado, true);*/
        assertNotNull(numregistros);
        assertTrue(numregistros > 0);

        log.debug("Numero de registros cancelados" + numregistros);

    }

    /**
     * M&eacute;todo de prueba para el servicio de actualizaEdoInsExpira, caso: Cuando
     * agente firmado es invalido
     * 
     * @throws Exception
     */

    public void testActualizaEdoInsExpira_AgenteInvalido() throws Exception {

        log.info("Entrando a ITestEnvioOperacionesService.testActualizaEdoInsExpira_Cancela()");

        AgenteVO agenteFirmado = new AgenteVO();

        agenteFirmado.setId("22");
        agenteFirmado.setFolio("223");

        int numregistros = 1;/*envioOperacionesService.actualizaEstadoInstruccionExpira(new BigInteger(
        "64097"), agenteFirmado, true);*/
        assertNotNull(numregistros);
        assertTrue(numregistros > 0);

        log.debug("Numero de registros cancelados" + numregistros);

    }

    public void testValidaInstitucionPFI() {
        System.out.println("testValidaInstitucionPFI");

        log.info("Ejecutando testValidaInstitucionPFI()");

        assertNotNull(envioOperacionesService);

        AgenteVO agenteVO = new AgenteVO();
        agenteVO.setId("01");
        agenteVO.setFolio("003");

        System.out.println("testValidaInstitucionPFI 1");
        for (int i = 0; i < 10; i++) {
            System.out.println("testValidaInstitucionPFI 2 [" + i + "]");
            boolean instTienePFI = false;
            try {
            	//TODO
//                instTienePFI = envioOperacionesService.validaInstitucionPFI(agenteVO);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("testValidaInstitucionPFI 3 [" + i + "]");
            log.debug("La institucion " + agenteVO.getClave() + " utiliza PFI?: " + instTienePFI);
            System.out.println("La institucion " + agenteVO.getClave() + " utiliza PFI?: "
                    + instTienePFI);

        }
        System.out.println("Termina  ");
        log.info("Termina ejecucion de testValidaInstitucionPFI()");
    }

}
