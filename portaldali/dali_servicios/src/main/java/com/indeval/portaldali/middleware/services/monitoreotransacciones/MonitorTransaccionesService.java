/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.monitoreotransacciones;

import com.indeval.portaldali.middleware.services.modelo.BusinessException;

/**
 * Clase que monitorea las operaciones de ktransacciones y las envia a protocolo
 * 
 * @author csanchez
 * 
 */
public interface MonitorTransaccionesService {

	/**
	 * M&eacute;todo encargado de monitorear las transacciones pendientes
	 */
	void monitoreaTransacciones() throws BusinessException;
}
