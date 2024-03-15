package com.indeval.portalinternacional.middleware.servicios.vo;

import com.indeval.portalinternacional.middleware.servicios.modelo.Control;

import java.io.Serializable;

public class ControlVO implements Serializable {
    private Long idMoiControlDerechos;
    private String descripcion;
    private String estado;
    private Integer idControl;
    private Integer idEstado;


    public ControlVO() {
    }

    public ControlVO(Control o) {
        if (o!=null){
            this.idMoiControlDerechos = o.getIdMoiControlDerechos();
            this.descripcion = o.getDescripcion();
            this.estado = o.getEstado();
            this.idControl = o.getIdControl();
            this.idEstado = o.getIdEstado();
        }

    }

    public Long getIdMoiControlDerechos() {
        return idMoiControlDerechos;
    }

    public void setIdMoiControlDerechos(Long idMoiControlDerechos) {
        this.idMoiControlDerechos = idMoiControlDerechos;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getIdControl() {
        return idControl;
    }

    public void setIdControl(Integer idControl) {
        this.idControl = idControl;
    }

    public Integer getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Integer idEstado) {
        this.idEstado = idEstado;
    }
}
