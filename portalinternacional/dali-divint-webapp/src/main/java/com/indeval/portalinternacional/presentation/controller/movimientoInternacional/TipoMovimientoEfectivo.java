package com.indeval.portalinternacional.presentation.controller.movimientoInternacional;

import static com.indeval.portalinternacional.middleware.servicios.constantes.Constantes.*;

public enum TipoMovimientoEfectivo {
    LIBERAR_SELECCION(TIPO_MOVIMIENTO_EFECTIVO_LIBERAR_SELECCION, "liberaSeleccion"),
    LIBERAR(TIPO_MOVIMIENTO_EFECTIVO_LIBERA, "libera"),
    CANCELAR_SELECCION(TIPO_MOVIMIENTO_EFECTIVO_CANCELAR_SELECCION, "cancelaSeleccion"),
    CANCELAR(TIPO_MOVIMIENTO_EFECTIVO_CANCELA, "cancela"),
    DESCONOCIDO(0, "desconocido");

    private Integer tipo;
    private String movimiento;

    TipoMovimientoEfectivo(Integer tipo, String movimiento) {
        this.tipo = tipo;
        this.movimiento = movimiento;
    }

    public static TipoMovimientoEfectivo getTipoMovimiento(Integer tipo) {
        switch (tipo) {
            case TIPO_MOVIMIENTO_EFECTIVO_LIBERAR_SELECCION:
                return LIBERAR_SELECCION;
            case TIPO_MOVIMIENTO_EFECTIVO_LIBERA:
                return LIBERAR;
            case TIPO_MOVIMIENTO_EFECTIVO_CANCELAR_SELECCION:
                return CANCELAR_SELECCION;
            case TIPO_MOVIMIENTO_EFECTIVO_CANCELA:
                return CANCELAR;
            default:
                return DESCONOCIDO;
        }
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public String getMovimiento() {
        return movimiento;
    }

    public void setMovimiento(String movimiento) {
        this.movimiento = movimiento;
    }

    @Override
    public String toString() {
        return "TipoMovimientoEfectivo{" +
                "tipo=" + tipo +
                ", movimiento='" + movimiento + '\'' +
                '}';
    }
}
