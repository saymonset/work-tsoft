/*
 *Copyright (c) 2005-2007 Bursatec. All Rights Reserved
 */
package com.indeval.portaldali.presentation.util;

import com.indeval.portaldali.middleware.services.modelo.BusinessException;

/**
 * Excepción que sucede si Ha fallado la generación del Hash de la firma Digital.
 *
 * @author Carlos Hernadez
 * @version 1.0
 */

public class HashFirmaDigitalException extends BusinessException {

    /**
     * Object que nos puede proporcionar mas información sobre lo sucedido.
     */
    private Object m_object = null;

    /**
     * @noinspection FieldNameHidesFieldInSuperclass
     */
    public static final String STANDARD_MSG = "<error>Ha fallado la generación del Hash de la firma Digital</error>";

    /**
     * Constructor para HashFirmaDigitalException.
     */
    public HashFirmaDigitalException() {
        super(STANDARD_MSG);
    }

    /**
     * Constructor para HashFirmaDigitalException.
     *
     * @param message que se pretende contenga la excepción.
     */
    public HashFirmaDigitalException(String message) {
        super(message);
    }

    /**
     * Constructor para HashFirmaDigitalException.
     *
     * @param message que se pretende contenga la excepción.
     * @param o       objeto del error.
     */
    public HashFirmaDigitalException(String message, Object o) {
        super(message);
        this.m_object = o;
    }

    /**
     * Constructor para HashFirmaDigitalException.
     *
     * @param message que se pretende contenga la excepción.
     * @param cause   excepción que origin esta.
     */
    public HashFirmaDigitalException(String message, Throwable cause) {
        super(message, cause);

    }

    /**
     * Constructor para HashFirmaDigitalException.
     *
     * @param message que se pretende contenga la excepción.
     * @param cause   excepción que origin esta.
     * @param o       objeto del error.
     */
    public HashFirmaDigitalException(String message, Throwable cause, Object o) {
        super(message, cause);
        this.m_object = o;

    }

    /**
     * Constructor para HashFirmaDigitalException.
     *
     * @param cause excepción que origin esta.
     */
    public HashFirmaDigitalException(Throwable cause) {
        super(STANDARD_MSG, cause);
    }

    /**
     * Constructor para HashFirmaDigitalException.
     *
     * @param cause excepción que origin esta.
     * @param o     objeto del error.
     */
    public HashFirmaDigitalException(Throwable cause, Object o) {
        super(STANDARD_MSG, cause);
        this.m_object = o;
    }

    /**
     * Convierte este <code>HashFirmaDigitalException</code> a su representación textual.
     *
     * @noinspection DuplicateStringLiteralInspection
     */
    public String toString() {

        StringBuffer sb = new StringBuffer();
        sb.append("\n<Instance Class = '").append(this.getClass()).append("'>\n");
        sb.append("\t<super>").append(super.toString()).append("</super>\n");
        sb.append("\t<object>").append(m_object).append("</object>\n");
        sb.append("</Instance>");
        return sb.toString();
    }

    /**
     * Devuelve el objeto que puede proporcionarnos mas información sobre el porque sucedi esta
     * excepción.
     *
     * @return El objeto con la información de la excepcipon
     */
    public Object getObject() {
        return m_object;
    }

    /**
     * Establece el objeto que puede proporcionarnos mas información sobre el porque sucedi esta
     * excepción.
     *
     * @param object El objeto para proporcionar información de la excepción
     */
    public void setObject(Object object) {
        this.m_object = object;
    }
}

