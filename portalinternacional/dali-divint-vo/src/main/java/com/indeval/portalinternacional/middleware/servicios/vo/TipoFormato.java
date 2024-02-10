/**
 * 
 */
package com.indeval.portalinternacional.middleware.servicios.vo;

/**
 * Tipos de formato que se pueden capturar y/o consultar
 * 
 * @author César Hernández
 * 
 */
public enum TipoFormato {
    W8IMY("W8IMY"), W8BEN("W8BEN"), W8BEN2014("W8BEN2014"), W8BEN2017("W8BEN2017"), W9("W9"), MILA(
            "MILA"), NONE("NONE"), W8IMY2015("W8IMY2015"), W8BENE("W8BENE"), W8BENE2016(
            "W8BENE2016"), W8IMY2017("W8IMY2017");


    private final String tipoFormato;

    private TipoFormato(final String tipoFormato) {
        this.tipoFormato = tipoFormato;
    }

    public String getTipoFormato() {
        return this.tipoFormato;
    }

    public static TipoFormato obtenerInstancia(final String tipoFormato) {
        for (TipoFormato tf : TipoFormato.values()) {
            if (tf.getTipoFormato().trim().equalsIgnoreCase(tipoFormato.trim())) {
                return tf;
            }
        }
        return TipoFormato.NONE;
    }
}
