package com.indeval.portalinternacional.middleware.servicios.constantes;

import static com.indeval.portalinternacional.middleware.servicios.constantes.Constantes.*;

public enum EstatusDB {
    REGISTRADO(ID_ESTADO_REGISTRADO, DESC_ESTADO_REGISTRADO),
    AUTORIZADO(ID_ESTADO_AUTORIZADO, DESC_ESTADO_AUTORIZADO),
    CANCELADO(ID_ESTADO_CANCELADO, DESC_ESTADO_CANCELADO),
    ELIMINADO(ID_ESTADO_ELIMINADO, DESC_ESTADO_ELIMINADO),
    DESCONOCIDO(ID_ESTADO_DESCONOCIDO, DESC_ESTADO_DESCONOCIDO);
    private final int codigo;
    private final String descripcion;

    EstatusDB(int codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public static String obtenerDescripcion(int codigo) {
        switch (codigo) {
            case ID_ESTADO_REGISTRADO:
                return REGISTRADO.descripcion;
            case ID_ESTADO_AUTORIZADO:
                return AUTORIZADO.descripcion;
            case ID_ESTADO_CANCELADO:
                return CANCELADO.descripcion;
            case ID_ESTADO_ELIMINADO:
                return ELIMINADO.descripcion;
            default:
                return DESCONOCIDO.descripcion;
        }
    }

    public static EstatusDB obtenerEstado(int codigo) {
        switch (codigo) {
            case ID_ESTADO_REGISTRADO:
                return REGISTRADO;
            case ID_ESTADO_AUTORIZADO:
                return AUTORIZADO;
            case ID_ESTADO_CANCELADO:
                return CANCELADO;
            case ID_ESTADO_ELIMINADO:
                return ELIMINADO;
            default:
                return DESCONOCIDO;
        }
    }

    @Override
    public String toString() {
        return "EstatusHorario{" +
                "codigo=" + codigo +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
