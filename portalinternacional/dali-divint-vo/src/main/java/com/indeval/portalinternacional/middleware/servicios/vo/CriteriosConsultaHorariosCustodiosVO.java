// Cambio Multidivisas
package com.indeval.portalinternacional.middleware.servicios.vo;

import java.util.Date;

public class CriteriosConsultaHorariosCustodiosVO {

    private Integer idDivisa;
    private Integer idCustodio;
    private Integer idEstatus;
    private Date fechaCreacion;


    /**
     * @return the idDivisa
     */
    public Integer getIdDivisa() {
        return idDivisa;
    }

    /**
     * @param idDivisa the idDivisa to set
     */
    public void setIdDivisa(Integer idDivisa) {
        this.idDivisa = idDivisa;
    }

    /**
     * @return the idCustodio
     */
    public Integer getIdCustodio() {
        return idCustodio;
    }

    /**
     * @param idCustodio the idCustodio to set
     */
    public void setIdCustodio(Integer idCustodio) {
        this.idCustodio = idCustodio;
    }

    /**
     * @return the idEstatus
     */
    public Integer getIdEstatus() {
        return idEstatus;
    }

    /**
     * @param idEstatus the idEstatus to set
     */
    public void setIdEstatus(Integer idEstatus) {
        this.idEstatus = idEstatus;
    }

    /**
     * @return the fechaCreacion
     */
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    /**
     * @param fechaCreacion the fechaCreacion to set
     */
    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @Override
    public String toString() {
        return "CriteriosConsultaHorariosCustodiosVO{" +
                "idDivisa=" + idDivisa +
                ", idCustodio=" + idCustodio +
                ", idEstatus=" + idEstatus +
                ", fechaCreacion=" + fechaCreacion +
                '}';
    }
}
