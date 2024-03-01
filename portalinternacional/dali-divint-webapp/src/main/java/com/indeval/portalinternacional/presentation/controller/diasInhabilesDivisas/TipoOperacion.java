/**
 * Multidivisas: Días Inhábiles de Divisas : Tipos de operacion criterio front
 */
package com.indeval.portalinternacional.presentation.controller.diasInhabilesDivisas;


public enum TipoOperacion {
    REGITRAR(1, "REGISTRAR"),
    CONSULTAR(2, "CONSULTAR"),
    DESCONOCIDO(0, "desconocido");

    private Integer tipo;
    private String operacion;


    TipoOperacion(Integer tipo, String operacion) {
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

    public TipoOperacion obtenerTipoOperacion(Integer tipo) {
        switch (tipo) {
            case 1:
                return REGITRAR;
            case 2:
                return CONSULTAR;
            default:
                return DESCONOCIDO;
        }
    }

    @Override
    public String toString() {
        return "TipoOperacion{" +
                "tipo=" + tipo +
                ", operacion='" + operacion + '\'' +
                '}';
    }
}
