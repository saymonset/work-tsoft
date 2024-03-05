/**
 * Archivo: BundleManager.java
 *
 * Fecha de creación: Oct 9, 2006
 *
 * Proyecto: Framework RENAPO J2EE
 *
 * Derechos Reservados 2H Software 2007
 */
package com.indeval.portaldali.presentation.validation;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementación de un manejador de objetos de tipo ResourceBundle para el soporte de mensajes de usuario localizados en el
 * sistema.
 * 
 * @author José Antonio Huizar Moreno
 * @version 1.0
 */
public class BundleManager {
	private static final Logger logger = LoggerFactory.getLogger(BundleManager.class);
	
    /**
     * Obtiene un mensaje localizado del sistema para ser presentado al usuario.
     * 
     * @param bundleName El nombre del recurso solicitado.
     * @param key La llave del elemento solicitado.
     * @return El mensaje localizado relacionado al key proporcionado.
     */
    public static String obtenerMensajeLocalizado(String bundleName, String key) {
        String message = null;

        ResourceBundle resourceBundle = null;

        try {
        	System.out.println("Bundle: " + bundleName);
            resourceBundle = ResourceBundle.getBundle(bundleName);
        } catch (MissingResourceException ex) {
            logger.error(ex.getMessage(), ex);
            System.out.println("Error: ");
        }

        if (resourceBundle != null) {
            message = resourceBundle.getString(key);
            System.out.println("Mensaje: " + message);
        }
        return message;

    }
}
