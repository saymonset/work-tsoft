/*
 * Multidivisas: Dás inhábiles por Divisa
 */
package com.indeval.portalinternacional.middleware.servicios.dto.diasIhabilesDivisas;

public class ErrorDiaInhabilDivisa {
    private ErrorDiaInhabilDivisaTipo tipoError;
    private String error;

    public ErrorDiaInhabilDivisa(ErrorDiaInhabilDivisaTipo tipoError, String error) {
        this.tipoError = tipoError;
        this.error = String.format(tipoError.getDescripcion(), (error.length() > 20 ? error.substring(20) + "..." : error));
    }

    public ErrorDiaInhabilDivisaTipo getTipoError() {
        return tipoError;
    }

    public void setTipoError(ErrorDiaInhabilDivisaTipo tipoError) {
        this.tipoError = tipoError;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "ErrorDiaInhabilDivisa{" +
                "tipoError=" + tipoError +
                ", error='" + error + '\'' +
                '}';
    }
}
