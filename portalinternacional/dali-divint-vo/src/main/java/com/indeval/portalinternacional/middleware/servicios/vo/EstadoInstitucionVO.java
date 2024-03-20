package com.indeval.portalinternacional.middleware.servicios.vo;

import com.indeval.portaldali.persistence.modelo.EstadoInstitucion;

import java.io.Serializable;

public class EstadoInstitucionVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long idEstadoInstitucion;
    private String claveEstadoInstitucion;
    private String descripcion;

    public EstadoInstitucionVO() {
    }

    public EstadoInstitucionVO(EstadoInstitucion o) {
        if (o!=null){
            this.idEstadoInstitucion = o.getIdEstadoInstitucion();
            this.claveEstadoInstitucion = o.getClaveEstadoInstitucion();
            this.descripcion = o.getDescripcion();
        }
    }

    public EstadoInstitucionVO(Long idEstadoInstitucion, String claveEstadoInstitucion, String descripcion) {
        this.idEstadoInstitucion = idEstadoInstitucion;
        this.claveEstadoInstitucion = claveEstadoInstitucion;
        this.descripcion = descripcion;
    }

    public Long getIdEstadoInstitucion() {
        return idEstadoInstitucion;
    }

    public void setIdEstadoInstitucion(Long idEstadoInstitucion) {
        this.idEstadoInstitucion = idEstadoInstitucion;
    }

    public String getClaveEstadoInstitucion() {
        return claveEstadoInstitucion;
    }

    public void setClaveEstadoInstitucion(String claveEstadoInstitucion) {
        this.claveEstadoInstitucion = claveEstadoInstitucion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
