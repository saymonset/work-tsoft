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
import com.indeval.portaldali.middleware.dto.InstruccionLiquidacionDTO;
import com.indeval.portaldali.middleware.dto.OperacionSaldoDTO;
import com.indeval.portaldali.middleware.services.estadocuenta.ConsultaDetalleMovimientoEfectivoService;
import com.indeval.portaldali.persistence.dao.estadocuenta.DetalleMovimientoEfectivoDAO;

/**
 * Implementación del servicio de negocio que contiene la lógica para la consulta 
 * del detalle de movimientos de efectivo.
 * 
 * @author Pablo Julián Balderas Méndez
 * 
 */
public class ConsultaDetalleMovimientoEfectivoServiceImpl implements ConsultaDetalleMovimientoEfectivoService {

	/** DAO para ejecutar las consutas de detalles de efectivo */
	private DetalleMovimientoEfectivoDAO detalleMovimientoEfectivoDAO = null;
	
	/* (non-Javadoc)
	 * @see com.indeval.estadocuenta.core.application.services.ConsultaDetalleMovimientoEfectivoService#consultarDetalleMovimientoEfectivo(long, java.lang.String)
	 */
	public DetalleMovimientoEfectivoDTO consultarDetalleMovimientoEfectivo(long idRegistroContable, boolean isHistorico) {
		//DTO con el detalle del movimiento
		DetalleMovimientoEfectivoDTO detalleMovimientoEfectivoDTO = 
			detalleMovimientoEfectivoDAO.consultarDetalleMovimientoEfectivo(idRegistroContable, isHistorico);
		//Colocamos la descripción del movimiento
		detalleMovimientoEfectivoDTO
			.setDescripcionMovimiento(obtenerDescripcionMovimiento(
					detalleMovimientoEfectivoDTO.getMovimiento(), 
					detalleMovimientoEfectivoDTO.getOperacion(), 
					detalleMovimientoEfectivoDTO.getInstruccion()));		
		return detalleMovimientoEfectivoDTO;
	}
	
	/**
	 * Busca el identificador del registro contable de una operación de valores nombrada que corresponda
	 * al registro contable de efectivo en una operación DVP.
	 * 
	 * @param idRegistroContable el identificador del registro contable a consultar.
	 * @param isHistorico True si se tiene que buscar en los datos historicos, false en caso contrario
	 * @return el identificador del registro contable de valor nombrada que corresponde.
	 */
	public long buscarIdRegistroContableValorNombradoDeOperacionDVP(long idRegistroContable, long idInstitucion, boolean isHistorico) {
		
		return detalleMovimientoEfectivoDAO.buscarIdRegistroContableValorNombradoDeOperacionDVP(idRegistroContable, idInstitucion, isHistorico);
	}
	
	/**
	 * Construye la cadena que contiene la descripción del movimiento realizado
	 * @param movimiento Cargo o abono que se realizó en el movimiento
	 * @param operacion Operación realizada en el movimiento
	 * @param instruccion Instrucción ejecutada en el movimiento
	 * @return descripción del movimiento realizado
	 */
	private String obtenerDescripcionMovimiento(
		String movimiento, OperacionSaldoDTO operacion, InstruccionLiquidacionDTO instruccion) {		
		String descripcion = movimiento;		
		descripcion += " POR " + operacion.getTipoOperacion().getDescripcion();
		descripcion += " POR " + instruccion.getTipoInstruccion().getDescripcion().toUpperCase();
		return descripcion;
	}

	/**
	 * Obtiene el atributo detalleMovimientoEfectivoDAO
	 *
	 * @return El atrubuto detalleMovimientoEfectivoDAO
	 */
	public DetalleMovimientoEfectivoDAO getDetalleMovimientoEfectivoDAO() {
		return detalleMovimientoEfectivoDAO;
	}

	/**
	 * Establece la propiedad detalleMovimientoEfectivoDAO
	 *
	 * @param detalleMovimientoEfectivoDAO el campo detalleMovimientoEfectivoDAO a establecer
	 */
	public void setDetalleMovimientoEfectivoDAO(
			DetalleMovimientoEfectivoDAO detalleMovimientoEfectivoDAO) {
		this.detalleMovimientoEfectivoDAO = detalleMovimientoEfectivoDAO;
	}
	
}
