package com.indeval.portalinternacional.presentation.controller.horariosCustodios;

import com.indeval.portaldali.persistence.modelo.Divisa;
import com.indeval.portalinternacional.middleware.servicios.modelo.Custodio;

import javax.faces.model.SelectItem;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.indeval.portalinternacional.middleware.servicios.constantes.EstatusDB.*;
import static com.indeval.portalinternacional.middleware.servicios.constantes.EstatusDB.CANCELADO;

public class UtilHorariosCustodioController {

    /**
     * Inicializa lista de estados
     */
    public static List<SelectItem> obtenerEstados() {
        List<SelectItem> status = new ArrayList<>();
        status.add(new SelectItem(Integer.toString(REGISTRADO.getCodigo()), REGISTRADO.getDescripcion()));
        status.add(new SelectItem(Integer.toString(AUTORIZADO.getCodigo()), AUTORIZADO.getDescripcion()));
        status.add(new SelectItem(Integer.toString(CANCELADO.getCodigo()), CANCELADO.getDescripcion()));
        return status;
    }


    /**
     * Inicializa lista de bovedas
     */
    public static List<SelectItem> obtenerCustodios(List<Custodio> custodios) {
        List<SelectItem> listaCustodio = new ArrayList<>();
        for (Custodio custodio : custodios) {
            listaCustodio.add(new SelectItem(custodio.getId().toString(), custodio.getNombreCorto()));
        }
        return listaCustodio;
    }

    /**
     * Inicializa lista de divisas
     */
    public static List<SelectItem> obtenerDivisas(Divisa[] divisas) {
        List<SelectItem> listaDivisas = new ArrayList<>();
        for (Divisa divisa : divisas) {
            listaDivisas.add(new SelectItem(divisa.getIdDivisa().toString(), divisa.getDescripcion()));
        }
        return listaDivisas;
    }


    /**
     * Genera todas las horas del día
     */
    public static List<SelectItem> obtenerListaHorasDelDia(boolean inicio) {
        List<SelectItem> listaHoras = new ArrayList<>();
        SimpleDateFormat formatoPresentacion = new SimpleDateFormat("HH:mm");
        SimpleDateFormat formatoCarga = new SimpleDateFormat("HH:mm:ss");

        for (int hora = 0; hora < 24; hora++) {
            Date horaActual = new Date();
            horaActual.setHours(hora);
            int valor = (inicio ? 0 : 59);
            horaActual.setMinutes(valor);
            horaActual.setSeconds(valor);
            String horaPresentacion = formatoPresentacion.format(horaActual);
            String horaCarga = formatoCarga.format(horaActual);
            SelectItem item = new SelectItem(horaCarga, horaPresentacion);
            listaHoras.add(item);
        }

        return listaHoras;
    }


}
