/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.common.util;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;

/**
 * Excepci&oacute;n que sucede si Ha fallado la generaci&oacute;n del Hash de la firma Digital.
 *
 * @author Carlos Hernadez
 * @version 1.0
 */

public class HashFirmaDigitalException extends BusinessException {
	
	/**
	 * long 
	 */
	public static final long serialVersionUID = (long)1;

    /**
     * Object que nos puede proporcionar mas informaci&oacute;n sobre lo sucedido.
     */
    private Object m_object = null;

    /**
     * @noinspection FieldNameHidesFieldInSuperclass
     */
    public static final String STANDARD_MSG = "<error>Ha fallado la generaci&oacute;n del Hash de la firma Digital</error>";

    /**
     * Constructor para HashFirmaDigitalException.
     */
    public HashFirmaDigitalException() {
        super(STANDARD_MSG);
    }

    /**
     * Constructor para HashFirmaDigitalException.
     *
     * @param message que se pretende contenga la excepci&oacute;n.
     */
    public HashFirmaDigitalException(String message) {
        super(message);
    }

    /**
     * Constructor para HashFirmaDigitalException.
     *
     * @param message que se pretende contenga la excepci&oacute;n.
     * @param o       objeto del error.
     */
    public HashFirmaDigitalException(String message, Object o) {
        super(message);
        this.m_object = o;
    }

    /**
     * Constructor para HashFirmaDigitalException.
     *
     * @param message que se pretende contenga la excepci&oacute;n.
     * @param cause   excepci&oacute;n que origin&oacute; esta.
     */
    public HashFirmaDigitalException(String message, Throwable cause) {
        super(message, cause);

    }

    /**
     * Constructor para HashFirmaDigitalException.
     *
     * @param message que se pretende contenga la excepci&oacute;n.
     * @param cause   excepci&oacute;n que origin&oacute; esta.
     * @param o       objeto del error.
     */
    public HashFirmaDigitalException(String message, Throwable cause, Object o) {
        super(message, cause);
        this.m_object = o;

    }

    /**
     * Constructor para HashFirmaDigitalException.
     *
     * @param cause excepci&oacute;n que origin&oacute; esta.
     */
    public HashFirmaDigitalException(Throwable cause) {
        super(STANDARD_MSG, cause);
    }

    /**
     * Constructor para HashFirmaDigitalException.
     *
     * @param cause excepci&oacute;n que origin&oacute; esta.
     * @param o     objeto del error.
     */
    public HashFirmaDigitalException(Throwable cause, Object o) {
        super(STANDARD_MSG, cause);
        this.m_object = o;
    }

    /**
     * Convierte este <code>HashFirmaDigitalException</code> a su representaci&oacute;n textual.
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
     * Devuelve el objeto que puede proporcionarnos mas informaci&oacute;n sobre el porque sucedi&oacute; esta
     * excepci&oacute;n.
     *
     * @return El objeto con la informaci&oacute;n de la excepci&oacute;n
     */
    public Object getObject() {
        return m_object;
    }

    /**
     * Establece el objeto que puede proporcionarnos mas informaci&oacute;n sobre el porque sucedi&oacute; esta
     * excepci&oacute;n.
     *
     * @param object El objeto para proporcionar informaci&oacute;n de la excepci&oacute;n
     */
    public void setObject(Object object) {
        this.m_object = object;
    }
}

