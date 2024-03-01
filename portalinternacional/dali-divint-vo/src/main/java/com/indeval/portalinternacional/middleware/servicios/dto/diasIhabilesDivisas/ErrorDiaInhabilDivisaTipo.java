/*
 * Multidivisas: Dás inhábiles por Divisa : Control de Errores
 */
package com.indeval.portalinternacional.middleware.servicios.dto.diasIhabilesDivisas;

public enum ErrorDiaInhabilDivisaTipo {
    ERROR_FORMATO(1, "No se cuenta con el layout correcto [%s]"),
    ERROR_FECHA_DIA(2, "No se cuenta con un día válido para seguir procesando [%s]"),
    ERROR_FECHA_MES(3, "No se cuenta con un mes válido para seguir procesando [%s]"),
    ERROR_FECHA_ANIO(4, "No se cuenta con un año válido para seguir procesando [%s]"),
    ERROR_DIVISA(5, "No se cuenta con una divisa válida para seguir procesando [%s]"),
    ERROR_REPETIDO(6, "Registro repetido dentro del conjunto del archivo [%s]");

    private Integer tipo;
    private String descripcion;

    ErrorDiaInhabilDivisaTipo(Integer tipo, String descripcion) {
        this.tipo = tipo;
        this.descripcion = descripcion;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "ErrorDiaInhabilDivisaTipo{" +
                "tipo=" + tipo +
                ", descripcion='" + this.name() + '\'' +
                '}';
    }
}
