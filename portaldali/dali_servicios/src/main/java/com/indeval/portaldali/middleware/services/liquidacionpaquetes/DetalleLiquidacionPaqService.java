package com.indeval.portaldali.middleware.services.liquidacionpaquetes;

import com.indeval.portaldali.middleware.dto.MopInstruccionLiquidacionDTO;
import com.indeval.portaldali.middleware.dto.MopPaqueteDTO;

public interface DetalleLiquidacionPaqService {
	/**
	 * Obtiene el paquete en funcion de los 5 atributos
	 * @param paqueteDTO
	 * @return PaqueteDTO
	 */
	MopPaqueteDTO getPaquete(MopPaqueteDTO paqueteDTO, String ticket, Long idInstitucionActual);

	/**
	 * Cancela el paquete completo
	 * @param paqueteDTO
	 * @return Boolean
	 */
	Boolean cancelPaquete(MopPaqueteDTO paqueteDTO, String ticket);

	/**
	 * Cancela una instruccion del paquete.
	 * @param instruccionLiquidacionDTO
	 * @param paqueteDTO
	 * @return Boolean
	 */
	String cancelInstruccionLiquidacionPaquete(MopInstruccionLiquidacionDTO instruccionLiquidacionDTO, MopPaqueteDTO paqueteDTO, String ticket);
}
