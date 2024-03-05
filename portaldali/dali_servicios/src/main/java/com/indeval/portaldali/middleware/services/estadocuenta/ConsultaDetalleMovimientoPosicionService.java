/**
 * 2H Software SA de CV
 * 
 * Sistema de Consulta de Estado de Cuenta - Indeval
 *
 * Fecha de creación: Dec 24, 2007
 */
package com.indeval.portaldali.middleware.services.estadocuenta;

import com.indeval.portaldali.middleware.dto.DetalleMovimientoValorDTO;

/**
 * Servicio de negocio que contiene la lógica para la consulta del detalle de movimientos 
 * de valores.
 * 
 * @author Sandra Flores Arrieta
 * @version 1.0
 *
 */
public interface ConsultaDetalleMovimientoPosicionService {

	/**
	 * Consulta el detalle de un registro contable dependiendo del tipo de operación que este representa.
	 * 
	 * @param idRegistroContable id del registro contable del cual se requiere el detalle
	 * @param idInstitucion id de la institución del usuario en sesión.
	 * @param isHistorico True si se tiene que buscar en los datos historicos, false en caso contrario
	 * @return DTO con los datos del detalle del movimiento
	 */
	DetalleMovimientoValorDTO consultarDetalleMovimientoValor(long idRegistroContable, long idInstitucion, boolean isHistorico);
	
}
