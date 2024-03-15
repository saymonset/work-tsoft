package com.indeval.portalinternacional.middleware.servicios.vo;

import com.indeval.portalinternacional.middleware.servicios.modelo.EstadoDerechoInt;

import java.io.Serializable;

public class EstadoDerechoIntVO implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Integer id;//ID_ESTADO_DERECHO_INT
    private String claveEstado;//CLAVE_ESTADO
    private String descripcion;//DESCRIPCION
    private Integer participante;//PARTICIPANTE


    public EstadoDerechoIntVO() {
    }

    public EstadoDerechoIntVO(EstadoDerechoInt estadoDerechoInt) {
        if (estadoDerechoInt != null){
            this.id = estadoDerechoInt.getId();
            this.claveEstado = estadoDerechoInt.getClaveEstado();
            this.descripcion = estadoDerechoInt.getDescripcion();
            this.participante= estadoDerechoInt.getParticipante();
        }
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClaveEstado() {
        return claveEstado;
    }

    public void setClaveEstado(String claveEstado) {
        this.claveEstado = claveEstado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getParticipante() {
        return participante;
    }

    public void setParticipante(Integer participante) {
        this.participante = participante;
    }
}
