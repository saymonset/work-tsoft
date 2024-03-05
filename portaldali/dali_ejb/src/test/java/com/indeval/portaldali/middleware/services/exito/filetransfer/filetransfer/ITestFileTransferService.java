/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.exito.filetransfer.filetransfer;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.BaseITestService;
import com.indeval.portaldali.middleware.services.filetransfer.CampoArchivoVO;
import com.indeval.portaldali.middleware.services.filetransfer.FileTransferService;
import com.indeval.portaldali.middleware.services.filetransfer.RegistroProcesadoVO;
import com.indeval.portaldali.middleware.services.filetransfer.ResumenVO;
import com.indeval.portaldali.middleware.services.filetransfer.TotalesProcesoVO;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;

/**
 * @author Jos&eacute; Avil&eacute;s
 *
 */
public class ITestFileTransferService extends BaseITestService {

    /** Servicio de log */
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /** Bean del servicio a ser probado */
    private FileTransferService fileTransferService;

    /**
     * @see com.indeval.portaldali.middleware.services.BaseITestService#onSetUp()
     */
    protected void onSetUp() throws Exception {
        super.onSetUp();
        if (fileTransferService == null) {
            fileTransferService = (FileTransferService) applicationContext
                    .getBean("fileTransferService");
        }
    }

    /**
     * Test para
     * almacenaInformacion(com.indeval.portaldali.middleware.services.modelo.AgenteVO,
     * java.lang.String, java.util.ArrayList, int, String)
     */
    public void testAlmacenaInformacion() {

        log.debug("Entrando a testAlmacenaInformacion()");

        assertNotNull(fileTransferService);
        AgenteVO agenteFirmado = new AgenteVO();
        agenteFirmado.setId("02");
        agenteFirmado.setFolio("061");
        String tipoProceso = "TS";
        List informacionArchivo = new ArrayList();
        /* PARA TD */
        /*informacionArchivo
                .add("PRUEBAFILET1020616920010010109M   gobfed 01    001200000000000000018000E00429-oct-2007           0000000001090482279200000000000002589610072PE");
        informacionArchivo
                .add("PRUEBAFILET2010010109020616920M   gobfed 01    001200000000000000018000R00229-oct-2007           0000000001090482279200000000000002589610024PE");
        informacionArchivo
                .add("PRUEBAFILET3020616920010010109M   gobfed 01    001200000000000000018000V00029-oct-2007           0000000001090482279200000000000002589610048PE");
        informacionArchivo
                .add("PRUEBAFILET4010010109020616920M   gobfed 01    001200000000000000018000T0  29-oct-2007           0000000001090482279200000000000002589610000PE");
        informacionArchivo
                .add("PRUEBAFILET5010030307010010109M   gobfed 01    001200000000000000018000V2  12-sep-2007           0000000001090482279200000000000002589610048PE");
        informacionArchivo
                .add("PRUEBAFILET6020616920010010109M   gobfed 01    001200000000000000018000J1  29-oct-2007           0000000001090482279200000000000002589610000PE");
        informacionArchivo
                .add("PRUEBAFILET7020616920010010109M   gobfed 01    001200000000000000018000E00429-oct-2007           0000000001090482279200000000000002589610072PE1");*/
        /* PARA TS */
        informacionArchivo
                .add("29-oct-20070206184990206184091   SARE   B     0001T 0 0000000000000009100000000000000000000000");
        informacionArchivo
                .add("29-oct-20070100101090206184091   WALMEX V     0046A 0 0000000000000102540000000000000000000000");
        informacionArchivo
                .add("29-oct-20070100607030206184210   Q      CPO   0000TL0 0000000000000010000000000000000000000000");
        informacionArchivo
                .add("29-oct-20070206184990100101091   WALMEX V     0046TV0 0000000000000102540000000000000000000000");
        informacionArchivo
                .add("29-oct-20070206184210100607031   USCOM  B-1   0003TL0 0000000000000007700000000000000000000000");
        informacionArchivo
                .add("29-oct-20070206184210100607030   Q      CPO   0000TL0 0000000000000010000000000000000000000000");
        informacionArchivo
                .add("29-oct-20070206153350100607031   CEMEX  CPO   0000TV0 0000000000000002250000000085045154000000");
        informacionArchivo
                .add("29-oct-20070100101090201567001   SARE   B     0001TL0 0000000000000009100000000000000000000000");
        informacionArchivo
                .add("29-oct-20070100101090100607031   USCOM  B-1   0003TL0 0000000000000007700000000000000000000000");
        informacionArchivo
                .add("29-oct-20070100101090201567001   SARE   B     0001TL0 0000000000000009100000000000000000000000");
        informacionArchivo
                .add("29-oct-20070206184210100607031   USCOM  B-1   0003TL0 0000000000000007700000000000000000000000");
        informacionArchivo
                .add("29-oct-20070206153350100607031   SORIANAB     0001TV0 0000000000000002250000000085045154000000");
        informacionArchivo
                .add("29-oct-20070206184090201567001   SARE   B     0001TL0 0000000000000009100000000000000000000000");
        informacionArchivo
                .add("29-oct-20070206184210100607031   USCOM  B-1   0003TL0 0000000000000007700000000000000000000000");
        informacionArchivo
                .add("29-oct-20070206184090201567001   SARE   B     0001TL0 0000000000000009100000000000000000000000");
        informacionArchivo
                .add("29-oct-20070206184210100607031   USCOM  B-1   0003TL0 0000000000000007700000000000000000000000");
        /* PARA TC */
        /*informacionArchivo
                .add("1234567869200100400501   HOMEX  *     0000                 99929-oct-2007");
        informacionArchivo
                .add("1234567869200100400501   HOMEX  *     0000                 99929-oct-2007");
        informacionArchivo
                .add("1234567869200100400501   HOMEX  *     0000                 99929-oct-2007");
        informacionArchivo
                .add("1234567869200100400501   HOMEX  *     0000                 99929-oct-2007");
        informacionArchivo
                .add("1234567869209988811111   HOMEX  *     0000                 99929-oct-2007");
        informacionArchivo
                .add("1234567899990100400501   HOMEX  *     0000                 99929-oct-2007");
        informacionArchivo
                .add("1234567869202500200011   HOMEX  *     0000                 99929-jun-2007");
        informacionArchivo
                .add("1234567869200100400501   HOMEX  *     0000                 99929-06-2007");*/
        int offset = 0;
        String nombreUsuario = "JGAR";
        try {
            int resultado = fileTransferService.almacenaInformacion(agenteFirmado, tipoProceso,
                    (ArrayList) informacionArchivo, offset, nombreUsuario);
            log.debug("Resultado= " + resultado);
        }
        catch (BusinessException e) {
            log.debug(e.getMessage());
        }

        log.debug("Saliendo de testAlmacenaInformacion()");

    }

    /**
     * Test para
     * cancelaProceso(com.indeval.portaldali.middleware.services.modelo.AgenteVO,
     * java.lang.String)
     */
    public void testCancelaProceso() {

        log.debug("Entrando a testCancelaProceso()");

        assertNotNull(fileTransferService);
        AgenteVO agenteFirmado = new AgenteVO();
        agenteFirmado.setId("02");
        agenteFirmado.setFolio("061");
        String tipoProceso = "TS";
        try {
            fileTransferService.cancelaProceso(agenteFirmado, tipoProceso);
            log.debug("Se llevo a cabo la cancelacion con exito");
        }
        catch (BusinessException e) {
            log.debug(e.getMessage());
        }

        log.debug("Saliendo de testCancelaProceso()");

    }

    /**
     * Test para
     * muestraInformacion(com.indeval.portaldali.middleware.services.modelo.AgenteVO,
     * java.lang.String, boolean , PaginaVO)
     * @throws Exception
     */
    public void testMuestraInformacion() throws Exception {

        log.debug("Entrando a testMuestraInformacion()");

        assertNotNull(fileTransferService);
        AgenteVO agenteFirmado = new AgenteVO();
        agenteFirmado.setId("02");
        agenteFirmado.setFolio("061");
        String tipoProceso = "TD";
    	TotalesProcesoVO totalesProcesoVO = fileTransferService.muestraInformacion(agenteFirmado, tipoProceso, false, null);
    	assertNotNull(totalesProcesoVO);
        assertNotNull(totalesProcesoVO.getPaginaVO());
        assertNotNull(totalesProcesoVO.getPaginaVO().getRegistros());
    	log.debug("Totales: " + ReflectionToStringBuilder.toString(totalesProcesoVO));
        for (Iterator iter = totalesProcesoVO.getPaginaVO().getRegistros().iterator(); iter.hasNext();) {
            RegistroProcesadoVO registro = (RegistroProcesadoVO) iter.next();
            log.debug("Registro: " + ReflectionToStringBuilder.reflectionToString(registro));
            for (Iterator iterator = registro.getDatos().iterator(); iterator.hasNext(); ) {
                CampoArchivoVO campoArchivoVO = (CampoArchivoVO) iterator.next();
                log.debug("Datos: " + ReflectionToStringBuilder.reflectionToString(campoArchivoVO));
            }
            for (int i = 0; registro.getMensajesError() != null && i < registro.getMensajesError().length; i++) {
                log.debug("Mensajes Error: " + registro.getMensajesError()[i]);
            }
        }

        log.debug("Saliendo de testMuestraInformacion()");

    }

    /**
     * Test para validaInformacion(AgenteVO agenteFirmado, String tipoProceso,
     * int offset)
     * @throws Exception
     */
    public void testValidaInformacion() throws Exception {

        log.debug("Entrando a testValidaInformacion()");

        assertNotNull(fileTransferService);
        AgenteVO agenteFirmado = new AgenteVO();
        agenteFirmado.setId("02");
        agenteFirmado.setFolio("061");
        String tipoProceso = "TS";
        int offset = 0;
        BigInteger idBoveda = new BigInteger("0");
        
        int resultado = fileTransferService.validaInformacion(agenteFirmado, tipoProceso, idBoveda, offset);
        log.debug("El resultado de la validacion es= " + resultado);

        log.debug("Saliendo de testValidaInformacion()");

    }


    /**
     * Test para obtieneResumen(AgenteVO agenteFirmado, String tipoProceso)
     */
    public void testObtieneResumen() {

        log.debug("Entrando a testObtieneResumen()");

        assertNotNull(fileTransferService);
        AgenteVO agenteFirmado = new AgenteVO();
        agenteFirmado.setId("02");
        agenteFirmado.setFolio("061");
        String tipoProceso = "TS";
        try {
            ResumenVO resumenVO = fileTransferService.obtieneResumen(agenteFirmado, tipoProceso);
            assertNotNull(resumenVO);
            log.debug("Resumen: " + ReflectionToStringBuilder.toString(resumenVO));
        }
        catch (BusinessException e) {
            log.debug(e.getMessage());
        }

        log.debug("Saliendo de testObtieneResumen()");

    }

    /**
     * Test para grabaInformacion(List listaFileTransfer)
     */
    public void testGrabaInformacion() throws BusinessException {

        log.debug("Entrando a testGrabaInformacion()");

        assertNotNull(fileTransferService);
        AgenteVO agenteFirmado = new AgenteVO();
        agenteFirmado.setId("02");
        agenteFirmado.setFolio("061");
        String tipoProceso = "TS";
        fileTransferService.grabaInformacion(agenteFirmado, tipoProceso, null);
        log.debug("Se grabo la informacion con exito");

        log.debug("Saliendo de testGrabaInformacion()");

    }

}
