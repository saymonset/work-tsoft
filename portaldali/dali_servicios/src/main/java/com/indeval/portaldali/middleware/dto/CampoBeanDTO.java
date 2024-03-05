/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.dto;


/**
 * Clase que contiene el valor de cada elemento de una instrucción en el sistema.
 *
 * @author Fernando vazquez ulloa
 */
public class CampoBeanDTO {

    /** Valor del campo de la instrucci&oacute;n. */
    private String valor;

    /** Constructor que inicializa el objeto sin parametros. */
    public CampoBeanDTO() {
    }

    /**
     * Constructor que recibe el valor del campo.
     *
     * @param valor -
     *            Valor del campo.
     */
    public CampoBeanDTO(final String valor) {
        this.valor = valor;
    }

    /**
     * Regresa el valor del campo.
     *
     * @return String.
     */
    public final String getValor() {
        return valor;
    }

    /**
     * Establece el nuevo valor del campo.
     *
     * @param valor -
     *            Valor del campo.
     */
    public final void setValor(final String valor) {
        this.valor = valor;
    }
}
