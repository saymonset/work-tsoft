/**
 * 2H Software SA de CV
 * 
 * Proyecto: Framework RENAPO
 * 
 * Archivo: DTOValidator.java
 *
 * Creado el Jul 12, 2007
 */
package com.indeval.portaldali.presentation.validation;

import com.indeval.portaldali.middleware.dto.util.ResultadoValidacionDTO;

/**
 * Define el comportamiento de un validador de DTOs.
 *
 * @author José Antonio Huizar Moreno
 * @version 1.0
 *
 */
public interface DTOValidator {
	
	/**
	 * Valida un DTO conforme a los criterios de validación que se encuentran
	 * registrados en este validador.
	 * 
	 * @param dto el DTO a validar.
	 * @return el resultado de la validación del DTO.
	 */
	ResultadoValidacionDTO validarDTO(Object dto);
	
}
