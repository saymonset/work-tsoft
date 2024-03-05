/** Copyright Bursatec 2006 */

package com.indeval.portaldali.presentation.util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Alejandro Aguilar
 * 
 * Componente para la busqueda y manejo de mensajes personalizados 
 *
 */

public class MessageBundle {
    
    /** El objeto resource bundle */
    ResourceBundle rb = null;
    
    /** El bundle por default */
    public static final String DEFAULT_BUNDLE = "bundle.Messages";

    private final Logger logger = LoggerFactory.getLogger(MessageBundle.class);

    /**  Constructor privado */
    private MessageBundle(String name, Locale locale) throws Exception {
        try {
            if (locale == null) {
                rb = ResourceBundle.getBundle(name);
            } else {
                rb = ResourceBundle.getBundle(name, locale);
            }
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
            throw e;
        }
    }
    
    /** entrega el MessageBundle por default
     * @return el objeto MesageBundle
     */
    
    public static MessageBundle getDefaultBundle(){
    	return MessageBundle.searchBundle(MessageBundle.DEFAULT_BUNDLE);
    }
    
    /** Busca un bundle que cuadre con un locale
     * @param name el nombre del recurso .properties en el class path, ej com.bursatec.Bundle 
     * @param locale el locale que se quiere cargar
     * @return el objeto MesageBundle
     */
    public static MessageBundle searchBundle(String name, Locale locale) {
        try{
            MessageBundle mb = new MessageBundle(name, locale);
            return mb;
        }catch( Exception e ){
        	e.printStackTrace();
            return null;
        }
    }

    /** Busca un bundle 
     * @param name el nombre del recurso .properties en el class path, ej com.bursatec.Bundle 
     * @return el objeto MesageBundle
     */
    public static MessageBundle searchBundle(String name) {
        try{
            MessageBundle mb = new MessageBundle( name, null );
            return mb;
        }catch( Exception e ){
        	e.printStackTrace();
            return null;
        }
    }

    /**
     * 
     * @param messageKey El key del mensaje en el bundle
     * @param params El array de parametros para el mensaje
     * @return el mensaje formateado
     */
    public String getMessage(String messageKey, Object[] params) {
        try{
            String msgPattern = rb.getString(messageKey);
            if (params != null) {
                return MessageFormat.format(msgPattern, params);
            }
            return msgPattern;
        }catch (Exception e) {
        	e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Agrega un mensage al FacesContext 
     * @param clientId el client id para el cual se pondra el mensaje
     * @param messageKey El key del mensaje en el bundle
     * @param params El array de parametros para el mensaje
     */
    public void addFacesMessage( String clientId, String messageKey, Object[] params ){
        FacesMessage message = new FacesMessage( getMessage(messageKey, params ) );
        FacesContext.getCurrentInstance().addMessage(clientId, message);
    }
    
    /**
     * Agrega un mensage al FacesContext 
     * @param clientId el client id para el cual se pondra el mensaje
     * @param stringMessage El mensaje
     */
    public void addMessage( String clientId, String stringMessage ){
        FacesMessage message = new FacesMessage( stringMessage );
        FacesContext.getCurrentInstance().addMessage(clientId, message);
    }
    
    
    /**
     * Agrega un mensage al FacesContext
     * @param el componente que servira de comodin para efectuar la busqueda del id 
     * @param Id el id para el cual se pondra el mensaje
     * @param messageKey El key del mensaje en el bundle
     * @param params El array de parametros para el mensaje
     */
    public void addFacesMessage( UIComponent component, String id, String messageKey, Object[] consulta ) throws Exception{
        try{
            UIComponent com = component.findComponent(id);
            FacesMessage message = new FacesMessage( getMessage(messageKey, consulta ) );
            FacesContext.getCurrentInstance().addMessage(com.getClientId(FacesContext.getCurrentInstance()), message);
        }catch ( Exception e ){
            MessageBundle.searchBundle( MessageBundle.DEFAULT_BUNDLE ).addFacesMessage("", "ERROR_COMP_NOT_FOUND", new Object[]{id});
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
    }

}
