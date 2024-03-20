package com.indeval.portalinternacional.middleware.servicios.vo;

import com.indeval.portaldali.persistence.modelo.TipoCuenta;

import java.io.Serializable;

public class TipoCuentaVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long idTipoCuenta;
    private String claveTipoCuenta;
    private String descripcion;
    private String naturalezaProcesoLiquidacion;
    private String naturalezaContable;
    private String naturalezaLegal;
    private String tipoTenencia;
    private String tipoCustodia;
    private String tipoAdministracion;
    private String claveSubgrupo;

    public TipoCuentaVO() {
    }

    public TipoCuentaVO(TipoCuenta o) {
        if (o !=null){
            this.idTipoCuenta = o.getIdTipoCuenta();
            this.claveTipoCuenta = o.getClaveTipoCuenta();
            this.descripcion = o.getDescripcion();
            this.naturalezaProcesoLiquidacion = o.getNaturalezaProcesoLiquidacion();
            this.naturalezaContable = o.getNaturalezaContable();
            this.naturalezaLegal = o.getNaturalezaLegal();
            this.tipoTenencia = o.getTipoTenencia();
            this.tipoCustodia = o.getTipoCustodia();
            this.tipoAdministracion = o.getTipoAdministracion();
            this.claveSubgrupo = o.getClaveSubgrupo();
        }
    }
    public TipoCuentaVO(Long idTipoCuenta, String claveTipoCuenta, String descripcion,
                        String naturalezaProcesoLiquidacion, String naturalezaContable, String naturalezaLegal,
                        String tipoTenencia, String tipoCustodia, String tipoAdministracion, String claveSubgrupo) {
        this.idTipoCuenta = idTipoCuenta;
        this.claveTipoCuenta = claveTipoCuenta;
        this.descripcion = descripcion;
        this.naturalezaProcesoLiquidacion = naturalezaProcesoLiquidacion;
        this.naturalezaContable = naturalezaContable;
        this.naturalezaLegal = naturalezaLegal;
        this.tipoTenencia = tipoTenencia;
        this.tipoCustodia = tipoCustodia;
        this.tipoAdministracion = tipoAdministracion;
        this.claveSubgrupo = claveSubgrupo;
    }

    public Long getIdTipoCuenta() {
        return idTipoCuenta;
    }

    public void setIdTipoCuenta(Long idTipoCuenta) {
        this.idTipoCuenta = idTipoCuenta;
    }

    public String getClaveTipoCuenta() {
        return claveTipoCuenta;
    }

    public void setClaveTipoCuenta(String claveTipoCuenta) {
        this.claveTipoCuenta = claveTipoCuenta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNaturalezaProcesoLiquidacion() {
        return naturalezaProcesoLiquidacion;
    }

    public void setNaturalezaProcesoLiquidacion(String naturalezaProcesoLiquidacion) {
        this.naturalezaProcesoLiquidacion = naturalezaProcesoLiquidacion;
    }

    public String getNaturalezaContable() {
        return naturalezaContable;
    }

    public void setNaturalezaContable(String naturalezaContable) {
        this.naturalezaContable = naturalezaContable;
    }

    public String getNaturalezaLegal() {
        return naturalezaLegal;
    }

    public void setNaturalezaLegal(String naturalezaLegal) {
        this.naturalezaLegal = naturalezaLegal;
    }

    public String getTipoTenencia() {
        return tipoTenencia;
    }

    public void setTipoTenencia(String tipoTenencia) {
        this.tipoTenencia = tipoTenencia;
    }

    public String getTipoCustodia() {
        return tipoCustodia;
    }

    public void setTipoCustodia(String tipoCustodia) {
        this.tipoCustodia = tipoCustodia;
    }

    public String getTipoAdministracion() {
        return tipoAdministracion;
    }

    public void setTipoAdministracion(String tipoAdministracion) {
        this.tipoAdministracion = tipoAdministracion;
    }

    public String getClaveSubgrupo() {
        return claveSubgrupo;
    }

    public void setClaveSubgrupo(String claveSubgrupo) {
        this.claveSubgrupo = claveSubgrupo;
    }
}
