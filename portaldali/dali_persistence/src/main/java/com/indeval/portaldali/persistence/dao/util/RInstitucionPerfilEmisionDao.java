/**
 * CMMV - Portal Dali
 * Copyrigth (c) 2018 CMMV. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.dao.util;

/**
 * Interfaz que define los metodos para las consultas de la relacion
 * Institucion-Perfil / Emision. 
 * 
 * @author Pablo Balderas
 */
public interface RInstitucionPerfilEmisionDao {

	/**
	 * Valida que la institucion tenga el rol de agente colocador asociado a la emision.
	 * @param idInstitucion Id de la institucion.
	 * @param idEmision Id de la emsision.
	 * @return true si cumple la condicion; false en caso contrario.
	 */
	boolean validarRolAgenteColocadorPorIdInstitucionIdEmision(Long idInstitucion, Long idEmision);
	
}
