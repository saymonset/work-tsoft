/*
 * Multidivisas: Dás inhábiles por Divisa
 */
package com.indeval.portalinternacional.middleware.servicios.dto.diasIhabilesDivisas;

import com.indeval.portalinternacional.middleware.servicios.dto.DivisaDTO;

import java.util.Date;

public class FechaDiaInhabilDTO {
    private String[] campos;
    private String divisa;
    private DivisaDTO divisaDTO;
    private Integer dia;
    private Integer mes;
    private Integer anio;
    private Date fecha;
    private ErrorDiaInhabilDivisa error;

    private boolean valida;

    public String[] getCampos() {
        return campos;
    }

    public void setCampos(String[] campos) {
        this.campos = campos;
    }

    public String getDivisa() {
        return divisa;
    }

    public void setDivisa(String divisa) {
        this.divisa = divisa;
    }

    public DivisaDTO getDivisaDTO() {
        return divisaDTO;
    }

    public void setDivisaDTO(DivisaDTO divisaDTO) {
        this.divisaDTO = divisaDTO;
    }

    public Integer getDia() {
        return dia;
    }

    public void setDia(Integer dia) {
        this.dia = dia;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public FechaDiaInhabilDTO() {
    }

    public ErrorDiaInhabilDivisa getError() {
        return error;
    }

    public void setError(ErrorDiaInhabilDivisa error) {
        this.error = error;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public boolean isValida() {
        return valida;
    }

    public void setValida(boolean valida) {
        this.valida = valida;
    }

    @Override
    public String toString() {
        return "ValidadorFecha{" +
                "divisa='" + divisa + '\'' +
                ", dia=" + dia +
                ", mes=" + mes +
                ", anio=" + anio +
                ", fecha=" + fecha +
                ", error='" + error + '\'' +
                ", valida=" + valida +
                '}';
    }
}
