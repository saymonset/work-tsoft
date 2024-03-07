/**
 * Sistema Portal DALI
 * <p>
 * Bursatec - 2H Software SA de CV
 * <p>
 * Creado: Nov 3, 2008
 */
package com.indeval.persistence.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portaldali.middleware.dto.DetalleMovimientoEfectivoDTO;
import com.indeval.portaldali.persistence.dao.estadocuenta.DetalleMovimientoEfectivoDAO;

/**
 * Prueba unitaria para el DAO de la consulta de movimientos de Efectivo.
 *
 * @author José Antonio Huizar Moreno
 * @version 1.0
 */
public class TestConsultaDetalleMovimientoEfectivoDAO extends BaseDaoTestCase {

    private Logger logger = LoggerFactory.getLogger(TestConsultaDetalleMovimientoEfectivoDAO.class);

    public void testConsultaMovimientosEfectivo() {
        DetalleMovimientoEfectivoDAO detalleMovimientoEfectivoDAO =
                (DetalleMovimientoEfectivoDAO) applicationContext.getBean("detalleMovimientoEfectivoDAO");

        Long idRegistroContable = 155434482l;
        Long idFolioInstLiquidacio = 81837202l;

        DetalleMovimientoEfectivoDTO detalle =
                detalleMovimientoEfectivoDAO.consultarDetalleMovimientoEfectivo(idRegistroContable, false, idFolioInstLiquidacio);
        assertNotNull(detalle);

        logger.info("Folio Instruccion: [" + detalle.getInstruccion().getFolioInstruccion() + "]");
        logger.info("Tipo D/R: [" + detalle.getOperacion().getInstruccionLiquidacion().getInstruccionEfectivo().getTipoRetiro() + "]");
    }


    public void testBuscarInformacionRegistro() {
        testBuscarInformacionHistorico();
        testBuscarInformacion();
    }

    public void testBuscarInformacionHistorico() {
        DetalleMovimientoEfectivoDAO detalleMovimientoEfectivoDAO =
                (DetalleMovimientoEfectivoDAO) applicationContext.getBean("detalleMovimientoEfectivoDAO");

        logger.debug("Prueba Histórico");

        Long idRegistroContableH = 1l;
        Long idFolioInstLiquidacionH = 81817202l;

        DetalleMovimientoEfectivoDTO detalleH =
                detalleMovimientoEfectivoDAO.consultaDetalleEfectivoPorRegitroFolio(
                        idRegistroContableH, idFolioInstLiquidacionH, true);

        logger.debug(detalleH.toString());
        if (detalleH != null) {
            logger.info("Folio Instruccion: [" + detalleH.getInstruccion().getFolioInstruccion() + "]");
            logger.info("Tipo D/R: [" + detalleH.getOperacion().getInstruccionLiquidacion().getInstruccionEfectivo().getTipoRetiro() + "]");
        }
        assertTrue(true);

    }

    public void testBuscarInformacion() {
        DetalleMovimientoEfectivoDAO detalleMovimientoEfectivoDAO =
                (DetalleMovimientoEfectivoDAO) applicationContext.getBean("detalleMovimientoEfectivoDAO");

        logger.debug("Prueba CuentaNombrada");

        Long idRegistroContable = 155430080l;
        Long idFolioInstLiquidacion = 81793202l;

        DetalleMovimientoEfectivoDTO detalle =
                detalleMovimientoEfectivoDAO.consultaDetalleEfectivoPorRegitroFolio(
                        idRegistroContable, idFolioInstLiquidacion, false);

        logger.debug(detalle.toString());
        if (detalle != null) {
            logger.info("Folio Instruccion: [" + detalle.getInstruccion().getFolioInstruccion() + "]");
            logger.info("Tipo D/R: [" + detalle.getOperacion().getInstruccionLiquidacion().getInstruccionEfectivo().getTipoRetiro() + "]");
        }
        assertNotNull(detalle);

    }
}
