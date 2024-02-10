/*
 * Copyrigth (c) 2009 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.presentation.controller.seguridad;

import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;

/**
 *
 * @author Rafael Ibarra Zendejas
 * @version 1.0
 */
public class MensajeInternacionalBean extends ControllerBase {

    private String mensajeUsuario;

    /**
     * Metodo que regresa un mensaje de usuario
     */
    public String getMensajeUsuario() {
        return mensajeUsuario;
    }

    /**
     * Metodo que limpia el mensaje de usuario
     * @return null
     */
    public String getLimpiaMensaje() {
        mensajeUsuario = null;
        return null;
    }

    /**
     * @param mensajeUsuario the mensajeUsuario to set
     */
    public void setMensajeUsuario(String mensajeUsuario) {
        this.mensajeUsuario = mensajeUsuario;
    }

}
