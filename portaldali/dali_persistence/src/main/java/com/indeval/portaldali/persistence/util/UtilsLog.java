/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.util;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class UtilsLog {

    /**
     * Log de clase.
     */
    private static final Logger log = LoggerFactory.getLogger(UtilsLog.class);
    
    /**
     * Define el numero limite de elementos a imprimir en 
     * logElementosLista() y logElementosArreglo()
     */
    private static final int LIMITE = 5; 
	
    /**
     * Metodo que imprime en el Log 
     * los pares (clave,valor) del mapa que recibe
     * @param mapa
     */
    public static void logClaveValor(Map mapa){

        log.debug("Entrando al metodo logClaveValor() ");

        if(mapa!=null && !mapa.isEmpty()) {
            Set setKeysMapaTraspasante = mapa.keySet();
            for (Iterator iter = setKeysMapaTraspasante.iterator(); iter
                    .hasNext();) {
                Object key = (Object) iter.next();
                log.debug("clave : [" + key.toString() + "] , valor : [" + 
                        mapa.get(key) + "]");
                
            }    
        }
        else {
            log.debug("El objeto Map recibido es NULL o esta VACIO");
        }
        

    }
    
    /**
     * Metodo que imprime al Log los primeros (primeros=true) 
     * o los ultimos (primeros=false) elementos del arreglo que recibe
     * @param arreglo
     * @param primeros
     */
    public static void logElementosArreglo(Object[] arreglo, boolean primeros){
    	
        log.debug("Entrando al metodo logElementosArreglo() ");
        
        if(arreglo!=null && arreglo.length > 0){
            
            log.debug("El arreglo tiene: [" + arreglo.length + "] elementos");
            int j=0;
            
            if(!primeros && arreglo.length > LIMITE){
                j = arreglo.length - LIMITE;
                log.debug("Imprimiendo ultimos elementos del arreglo");
            }
            else {
                log.debug("Imprimiendo primeros elementos del arreglo");
            }
            
            for (int i=j; i < arreglo.length; i++) {
                
                //Se imprimen objetos en el Log
                if(i < LIMITE){
                    log.debug("Imprimiendo el objeto " + (i+1));
                    log.debug("[" + ToStringBuilder.reflectionToString(arreglo[i]) + "]");                
                }
                
            }
            
        }
        else if(arreglo!=null){
            log.debug("El arreglo de objetos recibido esta VACIO");
        }
        else {
            log.debug("El arreglo de objetos recibido es NULL");
        }
    	
    }
    
    /**
     * Metodo que imprime al Log los primeros (primeros=true) y los ultimos (primeros=false) 
     * elementos de la lista que recibe.
     * NOTA: Falla con objetos lazy
     * @param lista
     * @param primeros
     */
    public static void logElementosLista(List lista){
    	logElementosLista(lista, true);
    	logElementosLista(lista, false);
    }
    
    /**
     * Metodo que imprime al Log los primeros (primeros=true) 
     * o los ultimos (primeros=false) elementos de la lista que recibe.
     * NOTA: Falla con objetos lazy
     * @param lista
     * @param primeros
     */
    public static void logElementosLista(List lista, boolean primeros) {
        
        log.debug("Entrando al metodo logElementosLista(" + primeros + ")");
        
        Assert.notNull(lista, "El objeto List recibido es NULL");
        Assert.notEmpty(lista, "La Lista recibida esta vacia");

        log.debug("La lista tiene: [" + lista.size() + "] elementos");
        int i = 0;  //Variable contador

        ListIterator iterador = lista.listIterator();
        if(primeros){
            log.debug("Imprimiendo los primeros elementos de la lista...");
        }
        else{
            log.debug("La lista tiene [" + lista.size() + "] elementos");
            if(lista.size() > LIMITE){
                iterador = lista.listIterator((lista.size()- LIMITE));
                i = lista.size()- LIMITE;                    
            }
            log.debug("Imprimiendo los ultimos elementos de la lista...");
        }

        for (ListIterator iter = iterador; iter.hasNext();) {
            ++i;
            Object elementoLista = (Object) iter.next();

            //Se imprimen objetos en el Log
            if(primeros){
                if(i < LIMITE){
                    log.debug("Imprimiendo el objeto " + i);
                    log.debug("[" + ToStringBuilder.reflectionToString(elementoLista) + "]");                
                }                    
            }
            else {
                if(i < lista.size()){
                    log.debug("Imprimiendo el objeto " + i);
                    log.debug("[" + ToStringBuilder.reflectionToString(elementoLista) + "]");                
                }
            }


        }

    }
	
}
