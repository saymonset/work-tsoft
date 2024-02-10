/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 * 
 *Jun 18, 2008
 */
package com.indeval.portalinternacional.presentation.validation;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.slf4j.LoggerFactory;

/**
 * Obtiene un mensaje localizado del sistema para ser presentado al usuario.
 * 
 * @param bundleName El nombre del recurso solicitado.
 * @param key La llave del elemento solicitado.
 * @return El mensaje localizado relacionado al key proporcionado.
 * 
 * @author Erik Vera Montoya
 * @version 1.0
 */
public class BundleManager {
    public static String obtenerMensajeLocalizado(String bundleName, String key) {
        String message = null;

        ResourceBundle resourceBundle = null;

        try {
            System.out.println("Bundle: " + bundleName);
            resourceBundle = ResourceBundle.getBundle(bundleName);
        } catch (MissingResourceException ex) {
        	LoggerFactory.getLogger(BundleManager.class).error("Ocurrio un error:",ex);
            System.out.println("Error: ");
        }

        if (resourceBundle != null) {
            message = resourceBundle.getString(key);
            System.out.println("Mensaje: " + message);
        }
        return message;

    }

}
