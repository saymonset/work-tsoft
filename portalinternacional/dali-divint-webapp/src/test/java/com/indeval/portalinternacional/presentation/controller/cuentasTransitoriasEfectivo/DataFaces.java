package com.indeval.portalinternacional.presentation.controller.cuentasTransitoriasEfectivo;

import java.util.Date;

public class DataFaces {

    Long idDivisaConsulta;
    Long idCustodioConsulta;
    Date fechaInicio;
    Date fechaFin;
    String folioReferenciado;

    String filtro;

    public DataFaces(Long idDivisaConsulta, Long idCustodioConsulta, Date fechaInicio, Date fechaFin, String folioReferenciado, String filtro) {
        this.idDivisaConsulta = idDivisaConsulta;
        this.idCustodioConsulta = idCustodioConsulta;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.folioReferenciado = folioReferenciado;
        this.filtro = filtro;
    }
}
