package com.indeval.portaldali.middleware.services.advice;

import com.indeval.portaldali.middleware.dto.BitacoraIngresosConsulta;

/**
 * Interface del servicio de utilidades para
 * la bitacora de consultas
 * 
 * @author Rafael Ibarra
 *
 */
public interface BitacoraConsultaService {
	
	/**
	 * Metodo para utilizado para el envio de Mensajes mensajes
	 * 
	 * @param BitacoraIngresosConsulta bitacora
	 */
	public void enviaMensajes(BitacoraIngresosConsulta bitacora) throws Exception;
	
}
