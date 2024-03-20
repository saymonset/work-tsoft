package com.indeval.portalinternacional.middleware.servicios.vo;

import com.indeval.portaldali.persistence.modelo.TipoBoveda;

import java.io.Serializable;

public class TipoBovedaVO implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final int NACIONAL_EFECTIVO = 0;
    public static final int NACIONAL_VALORES = 1;
    public static final int INTERNACIONAL_EFECTIVO = 2;
    public static final int INTERNACIONAL_VALORES = 3;
    private Long idTipoBoveda;
    private String claveTipoBoveda;
    private String ubicacion;
    private String descripcion;

    public TipoBovedaVO() {
    }

    public TipoBovedaVO(TipoBoveda o) {
        this();
        if (o!=null){
            this.idTipoBoveda = o.getIdTipoBoveda();
            this.claveTipoBoveda = o.getClaveTipoBoveda();
            this.ubicacion = o.getUbicacion();
            this.descripcion = o.getDescripcion();
        }

    }
    public TipoBovedaVO(Long idTipoBoveda, String claveTipoBoveda, String ubicacion, String descripcion) {
        this.idTipoBoveda = idTipoBoveda;
        this.claveTipoBoveda = claveTipoBoveda;
        this.ubicacion = ubicacion;
        this.descripcion = descripcion;
    }

    public Long getIdTipoBoveda() {
        return idTipoBoveda;
    }

    public void setIdTipoBoveda(Long idTipoBoveda) {
        this.idTipoBoveda = idTipoBoveda;
    }

    public String getClaveTipoBoveda() {
        return claveTipoBoveda;
    }

    public void setClaveTipoBoveda(String claveTipoBoveda) {
        this.claveTipoBoveda = claveTipoBoveda;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
