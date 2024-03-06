/**
 * 2H Software - Bursatec
 * 
 * Sistema de Consulta de Estados de Cuenta
 *
 * Dec 31, 2007
 *
 */
package com.indeval.portaldali.middleware.services.estadocuenta;

import com.indeval.portaldali.middleware.dto.DetalleMovimientoEfectivoDTO;

/**
 * Servicio de negocio que contiene la lógica para la consulta del detalle de movimientos 
 * de efectivo.
 * 
 * @author Pablo Julián Balderas Méndez
 * 
 */
public interface ConsultaDetalleMovimientoEfectivoService {

	/**
	 * Consulta el detalle de un movimiento de efectivo en base al id del registro contable y la 
	 * clave del tipo de operación.
	 * 
	 * @param idRegistroContable Id del registro contable asociado al movimiento
	 * @param isHistorico True si se tiene que buscar en los datos historicos, false en caso contrario
	 * @return DTO con el detalle del movimiento
	 */
	DetalleMovimientoEfectivoDTO consultarDetalleMovimientoEfectivo(long idRegistroContable, boolean isHistorico);
	
	/**
	 * Busca el identificador del registro contable de una operación de valores nombrada que corresponda
	 * al registro contable de efectivo en una operación DVP.
	 * 
	 * @param idRegistroContable el identificador del registro contable a consultar.
	 * @param isHistorico True si se tiene que buscar en los datos historicos, false en caso contrario
	 * @param idInstitucion el identificador de la institución del participante.
	 * @return el identificador del registro contable de valor nombrada que corresponde.
	 */
	long buscarIdRegistroContableValorNombradoDeOperacionDVP(long idRegistroContable, long idInstitucion, boolean isHistorico);
}
