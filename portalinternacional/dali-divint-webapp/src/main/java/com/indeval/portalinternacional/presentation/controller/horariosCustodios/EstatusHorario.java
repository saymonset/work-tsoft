package com.indeval.portalinternacional.presentation.controller.horariosCustodios;

public enum EstatusHorario {
    REGISTRADO(1, "REGISTRADO"),
    AUTORIZADO(2, "AUTORIZADO"),
    CANCELADO(3, "CANCELADO");
    private final int codigo;
    private final String descripcion;

    EstatusHorario(int codigo, String descripcion) {
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
            case 1:
                return REGISTRADO.descripcion;
            case 2:
                return AUTORIZADO.descripcion;
            case 3:
                return CANCELADO.descripcion;
            default:
                return "UNKNOWN";
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
