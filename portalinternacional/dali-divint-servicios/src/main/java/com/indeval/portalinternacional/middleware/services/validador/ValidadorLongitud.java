/**
 * Copyrigth (c) 2017 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.services.validador;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;

/**
 * Interfaz que expone los servicios de validacion de longitudes.
 * 
 */
public interface ValidadorLongitud {

	/**
	 * Valida la longitud de una cadena.
	 * @param cadena Cadena a validar.
	 * @param longitud La longitud debida.
	 * @param campo El campo en validacion.
	 * @throws SidvException
	 */
	void validarLongitudCadena(String cadena, int longitud, String campo) throws BusinessException;

	boolean validaLongitud(String cadena, int longitud);

}
