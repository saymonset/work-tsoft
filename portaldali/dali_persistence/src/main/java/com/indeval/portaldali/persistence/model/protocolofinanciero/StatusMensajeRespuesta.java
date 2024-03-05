/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.model.protocolofinanciero;

import java.math.BigInteger;

/**
 * @hibernate.mapping
 * @hibernate.class table=STATUS_MENSAJE_RESPUESTA
 */
public class StatusMensajeRespuesta {
    
    private BigInteger idStatusMensajeRespuesta; // key?

    private BigInteger tipoMensaje; // key?

    private String calificador; // key?

    private String codigo; // key?

    private String descripcion; // key?

    private String identificador;

    private String statusOperacion;

    /**
     * @hibernate.property column="id_status_mensaje_respuesta" not-null="true"
     * @return BigInteger
     */
    public BigInteger getIdStatusMensajeRespuesta() {
        return idStatusMensajeRespuesta;
    }

    /**
     * @hibernate.property column="tipo_mensaje" not-null="true"
     * @return BigInteger
     */
    public BigInteger getTipoMensaje() {
        return tipoMensaje;
    }

    /**
     * @hibernate.property column="calificador" not-null="true"
     * @return String
     */
    public String getCalificador() {
        return calificador;
    }

    /**
     * @hibernate.property column="codigo" not-null="true"
     * @return String
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @hibernate.property column="descripcion" not-null="true"
     * @return String
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @hibernate.property column="identificador" not-null="false"
     * @return String
     */
    public String getIdentificador() {
        return identificador;
    }

    /**
     * @hibernate.property column="status_operacion" not-null="false"
     * @return String
     */
    public String getStatusOperacion() {
        return statusOperacion;
    }

    /**
     * @param idStatusMensajeRespuesta
     */
    public void setIdStatusMensajeRespuesta(BigInteger idStatusMensajeRespuesta) {
        this.idStatusMensajeRespuesta = idStatusMensajeRespuesta;
    }

    /**
     * @param tipoMensaje
     */
    public void setTipoMensaje(BigInteger tipoMensaje) {
        this.tipoMensaje = tipoMensaje;
    }

    /**
     * @param calificador
     */
    public void setCalificador(String calificador) {
        this.calificador = calificador;
    }

    /**
     * @param codigo
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * @param descripcion
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @param identificador
     */
    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    /**
     * @param statusOperacion
     */
    public void setStatusOperacion(String statusOperacion) {
        this.statusOperacion = statusOperacion;
    }
}
