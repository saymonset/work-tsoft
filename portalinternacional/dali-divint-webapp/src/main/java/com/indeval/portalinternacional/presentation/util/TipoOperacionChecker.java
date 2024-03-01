/**
 * Multidivisas: Tipos de Operación manejados desde la vista
 */
package com.indeval.portalinternacional.presentation.util;

import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;

import static com.indeval.portalinternacional.middleware.servicios.constantes.Constantes.*;

public enum TipoOperacionChecker {

    LIBERAR_SELECCION(TIPO_OPERACION_LIBERAR_SELECCION, DESC_TIPO_OPERACION_LIBERAR_SELECCION),
    LIBERAR(TIPO_OPERACION_LIBERA, DESC_TIPO_OPERACION_LIBERA),
    CANCELAR_SELECCION(TIPO_OPERACION_CANCELAR_SELECCION, DESC_TIPO_OPERACION_CANCELAR_SELECCION),
    CANCELAR(TIPO_OPERACION_CANCELA, DESC_TIPO_OPERACION_CANCELA),

    AUTORIZA_SELECCION(TIPO_OPERACION_AUTORIZA_SELECCION, DESC_TIPO_OPERACION_AUTORIZA_SELECCION),
    AUTORIZA(TIPO_OPERACION_AUTORIZA, DESC_TIPO_OPERACION_AUTORIZA),

    ELIMINA_SELECCION(TIPO_OPERACION_ELIMINA_SELECCION, DESC_TIPO_OPERACION_ELIMINA_SELECCION),
    ELIMINA(TIPO_OPERACION_ELIMINA, DESC_TIPO_OPERACION_ELIMINA),

    DESCONOCIDO(TIPO_OPERACION_DESCONOCIDA, DESC_TIPO_OPERACION_DESCONOCIDA);

    private Integer tipo;
    private String operacion;

    TipoOperacionChecker(Integer tipo, String operacion) {
        this.tipo = tipo;
        this.operacion = operacion;
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


    public static TipoOperacionChecker getTipoOperacionHorarioCustodio(Integer tipo) {
        switch (tipo) {
            case TIPO_OPERACION_AUTORIZA_SELECCION:
                return AUTORIZA_SELECCION;
            case TIPO_OPERACION_AUTORIZA:
                return AUTORIZA;
            case Constantes.TIPO_OPERACION_CANCELAR_SELECCION:
                return CANCELAR_SELECCION;
            case Constantes.TIPO_OPERACION_CANCELA:
                return CANCELAR;
            default:
                return DESCONOCIDO;
        }
    }

    public static TipoOperacionChecker getTipoOperacionMovimientoEfectivo(Integer tipo) {
        switch (tipo) {
            case TIPO_OPERACION_LIBERAR_SELECCION:
                return LIBERAR_SELECCION;
            case TIPO_OPERACION_LIBERA:
                return LIBERAR;
            case TIPO_OPERACION_CANCELAR_SELECCION:
                return CANCELAR_SELECCION;
            case TIPO_OPERACION_CANCELA:
                return CANCELAR;
            default:
                return DESCONOCIDO;
        }
    }


    public static TipoOperacionChecker getTipoOperacionDiasInhabilesDivisas(Integer tipo) {
        switch (tipo) {
            case TIPO_OPERACION_AUTORIZA:
                return AUTORIZA;
            case TIPO_OPERACION_CANCELA:
                return CANCELAR;
            case TIPO_OPERACION_ELIMINA_SELECCION:
                return ELIMINA_SELECCION;
            case TIPO_OPERACION_ELIMINA:
                return ELIMINA;
            default:
                return DESCONOCIDO;
        }
    }






    @Override
    public String toString() {
        return "TipoOperacionChecker{" +
                "tipo=" + tipo +
                ", operacion='" + operacion + '\'' +
                '}';
    }
}

