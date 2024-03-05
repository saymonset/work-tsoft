/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * ErrorGeneralBean.java
 * Apr 21, 2008
 */
package com.indeval.portaldali.presentation.controller.common;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.ServletException;

/**
 * Implementación de un backing bean encargado 
 *
 * @author José Antonio Huizar Moreno
 * @version 1.0
 *
 */
public class ErrorGeneralBean implements Serializable {
    
	/** Llave a utilizar en el mapa del request para obtener el detalle de la excepción */
	private static final String JAVAX_SERVLET_ERROR_EXCEPTION = "javax.servlet.error.exception";
	
	private static final long serialVersionUID = 1L;

	/**
     * Obtiene la información relacionada con el mensaje de error.
     * 
     * @return la información relacionada con el mensaje de error.
     */
    public String getInfoMessage() {
        return "Un error no esperado ha ocurrido mientras se procesaba su solicitud. Por favor corte y pegue la siguiente información en un email y envelo a <b>" +
        "admin@portaldali.com </b>. Si el error persiste, favor de comunicarse con el personal de soporte tcnico.";
    }
    
    /**
     * Obtiene el detalle del error que reción ha ocurrido en la aplicación.
     * 
     * @return el detalle del error que reción ha ocurrido en la aplicación.
     */
    @SuppressWarnings("unchecked")
	public String getStackTrace() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map requestMap = context.getExternalContext().getRequestMap();
        Throwable ex = (Throwable) requestMap.get(JAVAX_SERVLET_ERROR_EXCEPTION);

        StringWriter writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        fillStackTrace(ex, pw);

        return writer.toString();
    }
    
    /**
     * Obtiene todo el detalle de las excepciones que originaron el error en la aplicación.
     * 
     * @param ex la excepción arrojada por la aplicación.
     * @param pw el escritor en el cual se escribirn las excepciones.
     */
    private void fillStackTrace(Throwable ex, PrintWriter pw) {
        if (null == ex) {
            return;
        }

        ex.printStackTrace(pw);

        if (ex instanceof ServletException) {
            Throwable cause = ((ServletException) ex).getRootCause();

            if (null != cause) {
                pw.println("Causa raíz: ");
                fillStackTrace(cause, pw);
            }
        } else {
            Throwable cause = ex.getCause();

            if (null != cause) {
                pw.println("Causa: ");
                fillStackTrace(cause, pw);
            }
        }
    }
}
