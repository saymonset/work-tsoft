// Cambio Multidivisas
package com.indeval.portalinternacional.persistence.dao.MovimientosEfectivoInternacional;

import com.indeval.portalinternacional.middleware.servicios.modelo.MovimientoDepositoEfectivoInternacional;
import com.indeval.portalinternacional.middleware.servicios.modelo.MovimientoRetiroEfectivoInternacional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Util {

    public static MovimientoDepositoEfectivoInternacional getDeposito(){
        MovimientoDepositoEfectivoInternacional deposito = new MovimientoDepositoEfectivoInternacional();
        Date fechaLiq = new Date();
        deposito.setIdMovimientoEfectivoInt(null);
        deposito.setIdCuenta(3909L);
        deposito.setCuenta(null);
        deposito.setIdDivisa(3L);
        deposito.setIdBoveda(13L);
        deposito.setSaldoDisponible(2.84205282238E9);
        deposito.setImporteTraspasar(1238.0);
        deposito.setSaldoEfectivo(2.84205406038E9);
        Date fecha = new Date();
        deposito.setFechaAlta(fecha);
        deposito.setFechaUltimaModificacion(fecha);
        deposito.setFechaLiquidacion(fechaLiq);
        deposito.setEstadoMovEfectivoInt(2L);
        deposito.setEstadoLiqIndeval(1L);
        deposito.setFolioControl(100L);
        deposito.setIdCatbic(5L);
        deposito.setReferenciaRelacionada("Ej Ref Rel");
        return deposito;
    }

    public static MovimientoRetiroEfectivoInternacional getRetiro(){
        MovimientoRetiroEfectivoInternacional retiro = new MovimientoRetiroEfectivoInternacional();
        Date fechaLiq = new Date();
        retiro.setIdCuenta(3909L);
        retiro.setIdDivisa(3L);
        retiro.setIdBoveda(13L);
        retiro.setSaldoDisponible(2.84205007388E9);
        retiro.setImporteTraspasar(9999.0);
        retiro.setSaldoEfectivo(2.84204007488E9);
        Date fecha = new Date();
        retiro.setFechaAlta(fecha);
        retiro.setFechaUltimaModificacion(fecha);
        retiro.setFechaLiquidacion(fechaLiq);
        retiro.setEstadoMovEfectivoInt(1L);
        retiro.setEstadoLiqIndeval(1L);
        retiro.setReferenciaNumerica("531531");
        retiro.setReferenciaRelacionada("135asd");
        retiro.setNotasComentarios("Nota de prueba");
        retiro.setTipoMensajee("202");
        retiro.setFolioControl(615L);
        retiro.setIdCatbic(5L);
        return retiro;
    }

}
