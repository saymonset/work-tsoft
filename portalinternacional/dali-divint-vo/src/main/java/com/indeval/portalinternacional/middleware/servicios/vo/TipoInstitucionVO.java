package com.indeval.portalinternacional.middleware.servicios.vo;

import com.indeval.portaldali.persistence.modelo.TipoInstitucion;

import java.io.Serializable;

public class TipoInstitucionVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long idTipoInstitucion;
    private String claveTipoInstitucion;
    private String descripcion;

    public TipoInstitucionVO() {
    }

    public TipoInstitucionVO(TipoInstitucion o) {
        if (o!=null){
            this.idTipoInstitucion = o.getIdTipoInstitucion();
            this.claveTipoInstitucion = o.getClaveTipoInstitucion();
            this.descripcion = o.getDescripcion();
        }
    }
public TipoInstitucionVO(Long idTipoInstitucion, String claveTipoInstitucion, String descripcion) {
        this.idTipoInstitucion = idTipoInstitucion;
        this.claveTipoInstitucion = claveTipoInstitucion;
        this.descripcion = descripcion;
    }

    public Long getIdTipoInstitucion() {
        return idTipoInstitucion;
    }

    public void setIdTipoInstitucion(Long idTipoInstitucion) {
        this.idTipoInstitucion = idTipoInstitucion;
    }

    public String getClaveTipoInstitucion() {
        return claveTipoInstitucion;
    }

    public void setClaveTipoInstitucion(String claveTipoInstitucion) {
        this.claveTipoInstitucion = claveTipoInstitucion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
