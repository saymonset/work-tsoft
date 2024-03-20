package com.indeval.portalinternacional.middleware.servicios.vo;

import com.indeval.portalinternacional.middleware.servicios.modelo.TipoPagoInt;

import java.io.Serializable;

public class TipoPagoIntVO implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Long id;//ID_TIPO_PAGO
    private String clavePago;//CLAVE_PAGO
    private String descripcion;//DESCRIPCION
    private Integer caev;//CAEV
    private Integer participante;//PARTICIPANTE


    public TipoPagoIntVO() {
    }

    //calendarioDerechos.getTipoPagoCAEV()
    public TipoPagoIntVO(TipoPagoInt o) {
        this();
        if (o!=null){
            this.id = o.getId();
            this.clavePago = o.getClavePago();
            this.descripcion = o.getDescripcion();
            this.caev = o.getCaev();
            this.participante = o.getParticipante();
        }
    }
    public TipoPagoIntVO(Long id, String clavePago, String descripcion, Integer caev, Integer participante) {
        this.id = id;
        this.clavePago = clavePago;
        this.descripcion = descripcion;
        this.caev = caev;
        this.participante = participante;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClavePago() {
        return clavePago;
    }

    public void setClavePago(String clavePago) {
        this.clavePago = clavePago;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getCaev() {
        return caev;
    }

    public void setCaev(Integer caev) {
        this.caev = caev;
    }

    public Integer getParticipante() {
        return participante;
    }

    public void setParticipante(Integer participante) {
        this.participante = participante;
    }
}
