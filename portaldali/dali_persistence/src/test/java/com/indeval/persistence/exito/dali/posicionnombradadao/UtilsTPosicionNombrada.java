/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.persistence.exito.dali.posicionnombradadao;

import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.persistence.model.PosicionNombrada;
import com.indeval.portaldali.persistence.util.UtilsLog;
import com.indeval.portaldali.persistence.vo.TPosicionNombradaParamsPersistence;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class UtilsTPosicionNombrada {

	/**
	 * Log de clase.
	 */
	private static final Logger log = LoggerFactory.getLogger(UtilsTPosicionNombrada.class);

	/**
	 * Retorna una instancia de TPosicionNombradaParamsPersistence con la
	 * bandera test = true
	 * 
	 * @return TPosicionNombradaParamsPersistence
	 */
	public static TPosicionNombradaParamsPersistence getInstanceTPosicionNombradaParamsPersistence() {

		TPosicionNombradaParamsPersistence tPosicionNombradaParamsPersistence = new TPosicionNombradaParamsPersistence();

		tPosicionNombradaParamsPersistence.setTest(true);

		return tPosicionNombradaParamsPersistence;

	}

	/**
	 * Envia al Log los primeros y ultimos datos de una lista de
	 * PosicionNombrada asi como el elemento medio de la lista
	 * 
	 * @param listaTPosicionNombrada
	 */
	public static void logListaTPosicionNombrada(List listaTPosicionNombrada) {

		log
				.info("Entrando a UtilsTPosicionNombrada.logListaTPosicionNombrada()");

		if (listaTPosicionNombrada != null && !listaTPosicionNombrada.isEmpty()) {
			log.debug("Se imprime una muestra de los resultados obtenidos...");
			UtilsLog.logElementosLista(listaTPosicionNombrada);

			log.debug("Se imprime un elemento de la media lista...");
			int indice = listaTPosicionNombrada.size() / 2;

			PosicionNombrada tPosicionNombrada = (PosicionNombrada) listaTPosicionNombrada
					.get(indice + 1 < listaTPosicionNombrada.size() ? indice + 1
							: indice);

			UtilsTPosicionNombrada.logTPosicionNombrada(tPosicionNombrada);
		} else {
			log.debug("La Lista esta nula &oacute; no contiene elementos");
		}
	}

	/**
	 * Envia al Log los datos contenidos en un PosicionNombrada
	 * 
	 * @param tPosicionNombrada
	 */
	public static void logTPosicionNombrada(PosicionNombrada tPosicionNombrada) {

		log.info("Entrando a UtilsTPosicionNombrada.logTPosicionNombrada()");

		log.debug("CuentaNombrada ["
				+ ReflectionToStringBuilder
						.reflectionToString(tPosicionNombrada.getCuentaNombrada()) + "]");
		log.debug("Institucion ["
				+ ReflectionToStringBuilder.reflectionToString(tPosicionNombrada
								.getCuentaNombrada().getInstitucion()) + "]");
		log.debug("EmisionPersistence ["
				+ ReflectionToStringBuilder
						.reflectionToString(tPosicionNombrada.getCupon().getEmision())
				+ "]");
		log.debug("Instrumento ["
				+ ReflectionToStringBuilder
						.reflectionToString(tPosicionNombrada.getCupon().getEmision()
								.getInstrumento()) + "]");
		log.debug("Emisora ["
				+ ReflectionToStringBuilder
						.reflectionToString(tPosicionNombrada.getCupon().getEmision()
								.getEmisora()) + "]");

		log.debug("Cupon ["
				+ ReflectionToStringBuilder.reflectionToString(tPosicionNombrada.getCupon())
				+ "]");
		log.debug("EstatusCupones ["
				+ ReflectionToStringBuilder.reflectionToString(
						tPosicionNombrada.getCupon().getEstadoCupon()) + "]");
	}

}
