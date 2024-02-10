package com.indeval.portalinternacional.middleware.servicios.vo;

import java.io.Serializable;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Clase para Mensaje Secuencia
 * @author arivera
 * @version 1.0
 */
@XStreamAlias("MensajeSecuencia")
public class MensajeSecuenciaVO implements Serializable {

    /**
     * Version serial.
     */
    private static final long serialVersionUID = 1L;
    /**
     * Atributo que contiene la secuencia que distingue un closter de mensajes
     * de otro.
     */
    private String sequence;
    /**
     * Identificador unico y secuencial de orden de cada mensaje en una
     * secuencia.
     */
    private int position;
    /**
     * Especifica el numero de mensajes en el cluster.
     */
    private int size;
    /**
     * Cuerpo del mensaje, con la lista de resultados a retornar al cliente.
     */
    private List messageBody;

    /**
     *
     */
    public MensajeSecuenciaVO() {
    }

    public MensajeSecuenciaVO(String sequence, int position, int size, List messageBody) {
        this.sequence = sequence;
        this.position = position;
        this.size = size;
        this.messageBody = messageBody;
    }

    @Override
    public String toString() {
        return org.apache.commons.lang.builder.ToStringBuilder.reflectionToString(this,
                org.apache.commons.lang.builder.ToStringStyle.MULTI_LINE_STYLE);
    }

    /**
     * @return the sequence
     */
    public String getSequence() {
        return sequence;
    }

    /**
     * @param sequence the sequence to set
     */
    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    /**
     * @return the position
     */
    public int getPosition() {
        return position;
    }

    /**
     * @param position the position to set
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * @return the size
     */
    public int getSize() {
        return size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * @return the messageBody
     */
    public List getMessageBody() {
        return messageBody;
    }

    /**
     * @param messageBody the messageBody to set
     */
    public void setMessageBody(List messageBody) {
        this.messageBody = messageBody;
    }

}
