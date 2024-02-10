// Cambio Multidivisas
package com.indeval.portalinternacional.persistence.dao.retiroEfectivoIntPendientes;

import com.indeval.portalinternacional.middleware.servicios.modelo.MovimientoDepositoEfectivoInternacional;
import com.indeval.portalinternacional.middleware.servicios.modelo.RetiroEfectivoIntPendientes;

import java.util.Date;


public class Util {

    public static RetiroEfectivoIntPendientes getRetiroPendiente(){
        RetiroEfectivoIntPendientes retiroPendiente = new RetiroEfectivoIntPendientes();
        Date fecha = new Date();

        retiroPendiente.setMensaje("Esto será el XML");
        retiroPendiente.setOrigen("PORTAL");
        retiroPendiente.setIdEstatus(0);
        retiroPendiente.setFechaRegistro(fecha);
        retiroPendiente.setFechaProceso(fecha);
        retiroPendiente.setComentario("Esto es un comentario");

        return retiroPendiente;
    }

}
