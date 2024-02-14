package com.indeval.portalinternacional.presentation.controller.horariosCustodios;

import static com.indeval.portalinternacional.middleware.servicios.constantes.Constantes.*;

public enum TipoOperacionHorarioCustodio {
    AUTORIZA_SELECCION(TIPO_AUTORIZA_SELECCION, "autorizaSeleccion"),
    AUTORIZA(TIPO_AUTORIZA, "autoriza"),
    CANCELAR_SELECCION(TIPO_CANCELA_SELECCION, "cancelaSeleccion"),
    CANCELAR(TIPO_CANCELA, "cancela"),
    DESCONOCIDO(0, "desconocido");

    private Integer tipo;
    private String operacion;

    TipoOperacionHorarioCustodio(Integer tipo, String operacion) {
        this.tipo = tipo;
        this.operacion = operacion;
    }

    public static TipoOperacionHorarioCustodio getTipoOperacion(Integer tipo) {
        switch (tipo) {
            case TIPO_AUTORIZA_SELECCION:
                return AUTORIZA_SELECCION;
            case TIPO_AUTORIZA:
                return AUTORIZA;
            case TIPO_CANCELA_SELECCION:
                return CANCELAR_SELECCION;
            case TIPO_CANCELA:
                return CANCELAR;
            default:
                return DESCONOCIDO;
        }
    }


    public boolean equals(int tipo) {
        return this.tipo.intValue() == tipo;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public String getOperacion() {
        return operacion;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    @Override
    public String toString() {
        return "TipoOperacionHorarioCustodio{" +
                "tipo=" + tipo +
                ", operacion='" + operacion + '\'' +
                '}';
    }
}
