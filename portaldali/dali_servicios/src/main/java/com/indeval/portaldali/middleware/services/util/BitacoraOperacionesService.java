/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.util;

import java.math.BigInteger;

import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.util.vo.BitacoraMatchParams;
import com.indeval.portaldali.middleware.services.util.vo.BitacoraMatchVO;
import com.indeval.portaldali.middleware.services.util.vo.BitacoraVOParams;
import com.indeval.portaldali.middleware.services.util.vo.BitacoraVOTotales;
import com.indeval.portaldali.middleware.services.util.vo.TradingInVO;
import com.indeval.protocolofinanciero.api.vo.TraspasoContraPagoVO;
import com.indeval.protocolofinanciero.api.vo.TraspasoLibrePagoVO;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public interface BitacoraOperacionesService {

	/**
	 * Retorna una pagina con los registros de la bitacora para una determinada
	 * instituci&oacute;n de acuerdo a los filtros enviados.
	 * 
	 * @param bitacoraVO .
	 * 
	 * @return BitacoraVOTotales
	 * 
	 * @throws BusinessException
	 */
	BitacoraVOTotales getMensajeBitacora(BitacoraVOParams bitacoraVO)
			throws BusinessException;

	/**
	 * Retorna un arreglo con los registros de la bitacora de Match para una
	 * determinada instituci&oacute;n de acuerdo a los filtros enviados.
	 * 
	 * @param bitacoraVO
	 * 
	 * @return BitacoraMatchVO[]
	 * 
	 * @throws BusinessException
	 */
	BitacoraMatchVO[] getMensajeBitacoraMatch(BitacoraMatchParams bitacoraVO)
			throws BusinessException;

	/**
	 * Construye un objeto TraspasoContraPagoVO a partir del TradingInVO
	 * recibido
	 * 
	 * @param tradingInVO
	 * @return TraspasoContraPagoVO
	 */
	TraspasoContraPagoVO tradingInVO2TraspasoContraPago(TradingInVO tradingInVO, Boolean esConfirmacion);

	/**
	 * Parsea el XML recibido en el String a un Object
	 * 
	 * @param inMessage
	 * 
	 * @return Object
	 */
	Object getDetalleOperacion(String inMessage);

	/**
	 * Verifica los permisos para la confirmacion de una operacion en match
	 * 
	 * @param tradingInVO
	 * @param agenteFirmado
	 * @param bitacoraMatchVO
	 * @return BitacoraMatchVO
	 */
	BitacoraMatchVO verificaPermisosConfirmacionCancelacion(
			TradingInVO tradingInVO, AgenteVO agenteFirmado,
			BitacoraMatchVO bitacoraMatchVO);

	/**
	 * Marca como cancelada o expirada una operacion en Match de acuerdo al
	 * parametro operacion recibido (true = expira, false= cancela )
	 * 
	 * @param idBitacoraMatch
	 * @param agenteFirmado
	 * @param operacion
	 * @return int
	 * 
	 * @throws BusinessException
	 * @deprecated
	 */
	int actualizaEdoInsExpira(BigInteger idBitacoraMatch,
			AgenteVO agenteFirmado, boolean operacion) throws BusinessException;

	/**
	 * Convierte un TradingVO a TraspasoLibrePago
	 * 
	 * @param tradingInVO
	 * @return TraspasoLibrePagoVO
	 * @throws BusinessException
	 */
	TraspasoLibrePagoVO tradingInVO2TraspasoLibrePago(TradingInVO tradingInVO, Boolean esConfirmacion)
			throws BusinessException;

}
