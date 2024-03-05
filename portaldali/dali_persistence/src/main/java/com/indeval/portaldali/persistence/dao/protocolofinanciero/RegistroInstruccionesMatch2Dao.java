/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.dao.protocolofinanciero;


import com.indeval.portaldali.persistence.model.protocolofinanciero.RegistroInstruccionesMatch2;
import com.indeval.portaldali.persistence.vo.AgentePK;

/**
 * Interfaz para definir los metodos relacionados con RegistroInstruccionesMatchDao2
 * @author Sergio Mena
 *
 */
public interface RegistroInstruccionesMatch2Dao {

	/**
	 * Metodo utilizado para actualizar la tabala T_REGISTRO_INSTRUCCIONES_MATCH en edo
	 * cancelado
	 * @param registroInstruccionesMatch2
	 * @param agenteFirmado
	 * @return RegistroInstruccionesMatch2
	 */
	int actualizaEdoInsCancelaMatch2(
			RegistroInstruccionesMatch2 registroInstruccionesMatch2, AgentePK agenteFirmado);
	
	/**
	 * Metodo utilizado para obtener el pojo RegistroInstruccionesMatch2 al pasar
	 * su id
	 * @param idBitacoraMatch
	 * @return RegistroInstruccionesMatch2
	 */
	RegistroInstruccionesMatch2 getRegistroInstruccionMatch2(Long idBitacoraMatch);
	
	/**
	 * Metodo utilizado para actualizar el campo de confirmacion
	 * @param registroInstruccionesMatch2
	 * @return int
	 */
	int updateStatusConfirmacion(RegistroInstruccionesMatch2 registroInstruccionesMatch2);
}
