/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.exception.enviooperaciones.enviooperaciones;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.indeval.portaldali.middleware.services.BaseITestService;
import com.indeval.portaldali.middleware.services.enviooperaciones.EnvioOperacionesService;
import com.indeval.portaldali.persistence.dao.common.DateUtilsDao;
import com.indeval.portaldali.persistence.model.BitacoraOperaciones;
import com.indeval.protocolofinanciero.api.vo.TraspasoLibrePagoVO;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * Clase de prueba para el servicio que graba operaciones de traspasos libres de
 * pago
 * 
 * @author salvador
 */
public class ITestGrabaOperacionTraspasoLibrePago extends BaseITestService {

    /**
     * Objeto de loggeo
     */
    private static final Logger logger = LoggerFactory.getLogger(ITestGrabaOperacionTraspasoLibrePago.class);

    /** Bean del servicio a ser probado */
    private EnvioOperacionesService envioOperacionesService;

    /** Bean de acceso a DateUtilsDao */
    private DateUtilsDao dateUtilsDao;

    /**
     * En este método se inicializan las propiedades que serán utilizadas
     * durante la clase de prueba.
     * 
     * @throws Exception
     */
    protected void onSetUp() throws Exception {
        super.onSetUp();

        if (envioOperacionesService == null) {
            envioOperacionesService = 
                (EnvioOperacionesService) applicationContext.getBean("envioOperacionesService");
        }
        if (dateUtilsDao == null) {
            dateUtilsDao = (DateUtilsDao) applicationContext.getBean("dateUtilsDao");
        }
    }

    /**
     * 
     * @throws Exception
     */
    public void testGrabaOperacionTraspasoLibrePago() throws Exception {
        log.info("Entrando al metodo testGrabaOperacionTraspasoLibrePago");

        TraspasoLibrePagoVO vo = new TraspasoLibrePagoVO();
        vo.setTipoInstruccion("M");
        vo.setTipoValor("M7");
        vo.setEmisora("GOBFED");
        vo.setSerie("091224");
        vo.setCupon("0000");
        vo.setCantidadTitulos(new Long(1000000000));
        vo.setFechaLiquidacion(new Date());
        vo.setIdFolioCtaReceptora("020338814");
        vo.setIdFolioCtaTraspasante("020228330");
        vo.setReferenciaOperacion("774211");
        vo.setReferenciaMensaje("12222");
        vo.setFechaRegistro(new Date());

        vo.setFechaAdquisicion(new Date());
        vo.setPrecioAdquisicion(new BigDecimal(12.12));
        vo.setCliente("CLIENTE");
        vo.setRfcCurp("RFCCURP");

        boolean isCompra = false;

        //TODO
//        BigInteger result = envioOperacionesService.grabaOperacion(vo, isCompra);
//        log.info("resultado " + result);
        //TODO
    }

    /**
     * 
     * @throws Exception
     */
    public void testRecuperaBitacoraOperacion() throws Exception {
        log.info("Entrando al metodo testRecuperaBitacoraOperacion");

        TransactionTemplate template = this.getTransactionTemplate();

        template.execute(new TransactionCallback() {

            public Object doInTransaction(TransactionStatus status) {
                BigDecimal id = new BigDecimal(424);

                BitacoraOperaciones bitacoraOperaciones = (BitacoraOperaciones) getHibernateTemplate()
                        .get(BitacoraOperaciones.class, id);

                System.out.println(ToStringBuilder.reflectionToString(bitacoraOperaciones));

                Clob clob = bitacoraOperaciones.getDatosAdicionales();
                try {
                    log.info("CLOB: " + clobToString(clob));
                    log.info("CLOB MAPA: " + clobTOMap(clob).getClass().getName());
                    log.info("CLOB MAPA DESGLOSADO: " + clobTOMap(clob));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
    }

    /**
     * 
     * @param clob
     * @return
     * @throws SQLException
     */
    private String clobToString(Clob clob) throws SQLException {
        log.info("Entrando al metodo clobToString");
        StringBuffer buffer = null;
        if (clob != null) {
            char[] mensaje = new char[(int) clob.length()];
            buffer = new StringBuffer("");
            try {
                clob.getCharacterStream().read(mensaje);
                buffer.append(mensaje);
                if (buffer != null) {
                    return buffer.toString();
                }
            } catch (IOException e) {

                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 
     * @param clob
     * @return
     * @throws SQLException
     */
    private Map clobTOMap(Clob clob) throws SQLException {
        XStream stream = new XStream(new DomDriver());
        stream.alias("mapa", Map.class);

        String x = this.clobToString(clob);

        Map resultado = (HashMap) stream.fromXML(x);
        return resultado;
    }

}
