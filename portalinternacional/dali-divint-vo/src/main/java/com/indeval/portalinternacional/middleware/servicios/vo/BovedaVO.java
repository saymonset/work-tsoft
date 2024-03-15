package com.indeval.portalinternacional.middleware.servicios.vo;


import com.indeval.portaldali.persistence.modelo.Boveda;

import java.io.Serializable;

public class BovedaVO  implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long idBoveda;
    private TipoBovedaVO tipoBoveda;
    private String nombreCorto;
    private String descripcion;
    private CuentaNombradaVO cuentaNombrada;

    public BovedaVO() {
    }

    public BovedaVO(Boveda o) {
        if (o!=null){
            this.idBoveda = o.getIdBoveda();
            this.tipoBoveda = new TipoBovedaVO(o.getTipoBoveda());
            this.nombreCorto =o.getNombreCorto();
            this.descripcion = o.getDescripcion();
            this.cuentaNombrada = new CuentaNombradaVO(o.getCuentaNombrada());
        }
    }

    public BovedaVO(Long idBoveda, TipoBovedaVO tipoBoveda, String nombreCorto, String descripcion, CuentaNombradaVO cuentaNombrada) {
        this.idBoveda = idBoveda;
        this.tipoBoveda = tipoBoveda;
        this.nombreCorto = nombreCorto;
        this.descripcion = descripcion;
        this.cuentaNombrada = cuentaNombrada;
    }

    public Long getIdBoveda() {
        return idBoveda;
    }

    public void setIdBoveda(Long idBoveda) {
        this.idBoveda = idBoveda;
    }

    public TipoBovedaVO getTipoBoveda() {
        return tipoBoveda;
    }

    public void setTipoBoveda(TipoBovedaVO tipoBoveda) {
        this.tipoBoveda = tipoBoveda;
    }

    public String getNombreCorto() {
        return nombreCorto;
    }

    public void setNombreCorto(String nombreCorto) {
        this.nombreCorto = nombreCorto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public CuentaNombradaVO getCuentaNombrada() {
        return cuentaNombrada;
    }

    public void setCuentaNombrada(CuentaNombradaVO cuentaNombrada) {
        this.cuentaNombrada = cuentaNombrada;
    }
}
