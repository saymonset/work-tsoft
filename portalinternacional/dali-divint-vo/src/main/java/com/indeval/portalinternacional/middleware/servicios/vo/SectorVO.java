package com.indeval.portalinternacional.middleware.servicios.vo;

import com.indeval.portaldali.persistence.modelo.Sector;

import java.io.Serializable;

public class SectorVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long idSector;
    private String descripcion;

    public SectorVO() {
    }


    public SectorVO(Sector o) {
        if (o!=null){
            this.idSector = o.getIdSector();
            this.descripcion = o.getDescripcion();
        }

    }
    public SectorVO(Long idSector, String descripcion) {
        this.idSector = idSector;
        this.descripcion = descripcion;
    }

    public Long getIdSector() {
        return idSector;
    }

    public void setIdSector(Long idSector) {
        this.idSector = idSector;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
