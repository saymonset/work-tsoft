package com.indeval.portalinternacional.middleware.servicios.vo;

import com.indeval.portaldali.persistence.modelo.EstadoCuenta;

import java.io.Serializable;

public class EstadoCuentaVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long idCuentaEstado;
    private String claveCuentaEstado;
    private String descripcion;

    public EstadoCuentaVO() {
    }

    public EstadoCuentaVO(EstadoCuenta o) {
        if (o!=null){
            this.idCuentaEstado = o.getIdCuentaEstado();
            this.claveCuentaEstado = o.getClaveCuentaEstado();
            this.descripcion = o.getDescripcion();
        }

    }

    public EstadoCuentaVO(Long idCuentaEstado, String claveCuentaEstado, String descripcion) {
        this.idCuentaEstado = idCuentaEstado;
        this.claveCuentaEstado = claveCuentaEstado;
        this.descripcion = descripcion;
    }

    public Long getIdCuentaEstado() {
        return idCuentaEstado;
    }

    public void setIdCuentaEstado(Long idCuentaEstado) {
        this.idCuentaEstado = idCuentaEstado;
    }

    public String getClaveCuentaEstado() {
        return claveCuentaEstado;
    }

    public void setClaveCuentaEstado(String claveCuentaEstado) {
        this.claveCuentaEstado = claveCuentaEstado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
