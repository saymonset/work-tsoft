/**
 * Copyrigth (c) 2017 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.services.validador;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;

/**
 * Implementación de la interfaz ValidadorLongitud
 * 
 */
public class ValidadorLongitudImpl implements ValidadorLongitud {

    /**
     * @see com.indeval.sidv.emisiones.middleware.service.validador.ValidadorLongitud#validarLongitudCadena(String, int, String)
     */
    public void validarLongitudCadena(String cadena, int longitud, String campo) throws BusinessException {
        if (cadena == null) {
            throw new BusinessException("El campo " + campo + " es nulo!");
        }
        if (longitud <= 0) {
            throw new BusinessException("La longitud para validar debe ser mayor a cero!");
        }
        if (cadena.length() != longitud) {
            throw new BusinessException("El campo " + campo + " es de una longitud distinta a " + longitud);
        }
    }

	public boolean validaLongitud(String cadena, int longitud) {
		if(cadena != null && cadena.length() == longitud){
			return true;
		}else{
			return false;
		}
	}

    
}
