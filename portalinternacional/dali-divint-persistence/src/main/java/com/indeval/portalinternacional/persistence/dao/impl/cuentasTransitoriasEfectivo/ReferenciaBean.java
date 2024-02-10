package com.indeval.portalinternacional.persistence.dao.impl.cuentasTransitoriasEfectivo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ReferenciaBean {

    BigDecimal idCuentaTransitoria;
    String folioRelacionado;
    String tipoMensaje;
    String detalleMovimientos;
    String seme;
    String mensajeISO;
    List<DivisaBean> divisas;


    public ReferenciaBean(BigDecimal idCuentaTransitoria, String folioRelacionado, String tipoMensaje, String mensajeISO, DivisaBean divisa) {
        this.idCuentaTransitoria = idCuentaTransitoria;
        this.folioRelacionado = folioRelacionado;
        this.tipoMensaje = tipoMensaje;
        this.divisas = new ArrayList<>();
        this.mensajeISO = mensajeISO;
        this.divisas.add(divisa);
    }

    public ReferenciaBean(BigDecimal idCuentaTransitoria, String folioRelacionado, String tipoMensaje, String detalleMovimientos, String seme, String mensajeISO, DivisaBean divisa) {
        this.idCuentaTransitoria = idCuentaTransitoria;
        this.folioRelacionado = folioRelacionado;
        this.tipoMensaje = tipoMensaje;
        this.detalleMovimientos = detalleMovimientos;
        this.seme = seme;
        this.mensajeISO = mensajeISO;
        this.divisas = new ArrayList<>();
        this.divisas.add(divisa);
    }

    public ReferenciaBean(String folioRelacionado, DivisaBean divisa) {
        this.folioRelacionado = folioRelacionado;
        this.divisas = new ArrayList<>();
        this.divisas.add(divisa);
    }

    @Override
    public String toString() {
        return "Referencia{"
                + "folioRelacionado=" + folioRelacionado + ", "
                + (idCuentaTransitoria == null ? "" : "idCuentaTransitoria=" + idCuentaTransitoria + ", ")
                + (tipoMensaje == null ? "" : "tipoMensaje=" + tipoMensaje + ", ")
                + (detalleMovimientos == null ? "" : "detalleMovimientos=" + detalleMovimientos + ", ")
                + (seme == null ? "" : ", seme=" + seme + ", ")
                + (mensajeISO == null ? "" : ", mensajeISO=" + mensajeISO + ", ")
                + "divisas=" + divisas + '}';
    }
}
