/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.exito.dali.posicioncontroladadao;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.persistence.model.Cupon;
import com.indeval.portaldali.persistence.model.PosicionControlada;
import com.indeval.portaldali.persistence.util.UtilsLog;
import com.indeval.portaldali.persistence.vo.TPosicionControladaParamsPersistence;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class UtilsTPosicionControlada {

    /**
     * Log de clase.
     */
    private static final Logger log = LoggerFactory.getLogger(UtilsTPosicionControlada.class);
    
    /**
     * Retorna una instancia de TPosicionControladaParamsPersistence con la bandera test = true 
     * @return TPosicionControladaParamsPersistence
     */
    public static TPosicionControladaParamsPersistence 
    					getInstanceTPosicionControladaParamsPersistence() {
    	
    	TPosicionControladaParamsPersistence tPosicionControladaParamsPersistence = 
    		new TPosicionControladaParamsPersistence();
    	
    	tPosicionControladaParamsPersistence.setTest(true);
    	
    	return tPosicionControladaParamsPersistence;
    	
    }
    
    /**
     * Envia al Log los primeros y ultimos datos de una lista de PosicionControlada
     * asi como el elemento medio de la lista
     * 
     * @param listaTPosicionControlada
     */
    public static void logListaTPosicionControlada(List listaTPosicionControlada) {
    	
        log.info("Entrando a UtilsTPosicionControlada.logListaTPosicionControlada()");
        
    	log.debug("Se imprime una muestra de los resultados obtenidos...");
    	UtilsLog.logElementosLista(listaTPosicionControlada);
    	
    	log.debug("Se imprime un elemento de la media lista...");
    	int indice = listaTPosicionControlada.size() / 2;
    	
//        PosicionControlada tPosicionControlada = 
//    		(PosicionControlada) listaTPosicionControlada.get(indice+1);
//    	
//    	UtilsTPosicionControlada.logTPosicionControlada(tPosicionControlada);
    }

	/**
	 * Envia al Log los datos contenidos en un PosicionControlada
	 * @param tPosicionControlada
	 */
	public static void logTPosicionControlada(PosicionControlada tPosicionControlada) {
		
	    log.info("Entrando a UtilsTPosicionControlada.logTPosicionControlada()");

		log.debug("CuentaControlada ["+ ReflectionToStringBuilder.reflectionToString(
                tPosicionControlada.getCuentaControlada()) + "]");
    	log.debug("Institucion ["+ ReflectionToStringBuilder.reflectionToString(
                tPosicionControlada.getCuentaControlada().getInstitucion()) + "]");
    	log.debug("EmisionPersistence ["+ ReflectionToStringBuilder.reflectionToString(
                tPosicionControlada.getEmision()) + "]");
    	log.debug("Instrumento ["+ ReflectionToStringBuilder.reflectionToString(
                tPosicionControlada.getEmision().getInstrumento()) + "]");
    	log.debug("Emisora ["+ ReflectionToStringBuilder.reflectionToString(
                tPosicionControlada.getEmision().getEmisora()) + "]");
    	
    	Set setCupones = tPosicionControlada.getEmision().getCupon();
    	for (Iterator iter = setCupones.iterator(); iter.hasNext();) {
			Cupon element = (Cupon) iter.next();
			log.debug("Cupon ["+ ReflectionToStringBuilder.reflectionToString(
				element) + "]");
			log.debug("EstatusCupones ["+ ReflectionToStringBuilder.reflectionToString(
					element.getEstadoCupon()) + "]");
		}
	}
	
}
