/**
 * 2H Software SA de CV
 * 
 * Sistema de Consulta de Estado de Cuenta - Indeval
 *
 * Fecha de creación: Dec 24, 2007
 */
package com.indeval.portaldali.persistence.dao.estadocuenta;

import java.util.Date;
import java.util.List;

import com.indeval.portaldali.middleware.dto.CuentaDTO;
import com.indeval.portaldali.middleware.dto.OperacionPosicionDTO;
import com.indeval.portaldali.middleware.dto.RegistroContablePosicionNombradaDTO;
import com.indeval.portaldali.persistence.model.RegContValNombrada;
import com.indeval.portaldali.persistence.model.RegContValNombradaHistorico;

/**
 * Interface que expone los métodos de acceso a la base de datos para las consultas relacionadas al 
 * detalle de movimientos.
 * 
 * @author Sandra Flores Arrieta
 * @version 1.0
 *
 */
public interface DetalleMovimientoPosicionDAO {
	
	/**
	 * Obtiene el detalle de un movimiento en base a si id de registro contable
	 * @param idRegistroCOntable id del registro contable para obtener los detalles del movimiento
	 * @return BO que contiene la información del detalle del movimiento
	 */	
	RegContValNombrada consultarDetalleMovimiento(long idRegistroContable);
	
	/**
	 * Obtiene el detalle de los registros contables que pertenecen a una operación DVP que presntamente
	 * pertenezcan a un pago en parcialidades de una misma instrucción origen.
	 * 
	 * @param registroContable el DTO con los criterios para realizar la consulta.
	 * @return una lista con objetos {@link RegistroContablePosicionNombradaDTO} que representan los registros contables en cuestin.
	 */
	List<OperacionPosicionDTO> buscarOperacionesDeInstruccion(RegistroContablePosicionNombradaDTO registroContable);
	
	/**
	 * Busca un registro contable de una posición nombrada por medio de su identificador en la base de datos.
	 * 
	 * @param idRegistroContable el identificador del registro contable a consultar.
	 * @return un objeto {@link RegistroContablePosicionNombradaDTO} que representa el registro a consultar.
	 */
	RegistroContablePosicionNombradaDTO buscarRegistroContablePosicionNombradaPorId(long idRegistroContable);
	
	/**
	 * Obtiene el detalle de las contrapartes que participan como receptores en una operación de tipo DVP en la que
	 * el interesado participa como traspasante.
	 * 
	 * @param registroContable el DTO con los criterios para realizar la consulta.
	 * @return una lista con arreglos de objetos cuya primera posición contiene el identificador de la cuenta de la
	 * contraparte. En la segunda posición contiene la fecha del último movimiento de la operación y la tercera posición
	 * contiene la cantidad total de la instrucción.
	 */
	List<Object[]> buscarContrapartesDeInstruccion(RegistroContablePosicionNombradaDTO registroContable);
	
	/**
	 * Busca las posiciones entregadas en garantía a un préstamo.
	 * 
	 * @param idInstruccion el identificador de la instrucción a consultar.
	 * @return una lista con los registros contables que contienen la información de las posiciones entregadas como garantía al préstamo.
	 */
	List<RegistroContablePosicionNombradaDTO> buscarGarantiasDePrestamos(long idInstruccion);
	
	/**
	 * Busca el detalle de un prestamo en base a el id de la instrucción a la que pertenece.
	 * 
	 * @param idInstruccion el identificador de la instrucción a consultar.
	 * @return un DTO con los datos del préstamo. <code>null</code> si no se encuentra el detalle del préstamo.
	 */
	RegistroContablePosicionNombradaDTO buscarDetalleDePrestamo(long idInstruccion);
	
	/**
	 * Obtiene la cuenta y el total liquidado hasta la fecha en esa cuenta como parte de una operación de tipo DVP.
	 * 
	 * @param idsCuentas los identificadores de las cuentas a consultar.
	 * @param registroContable el DTO con los criterios para realizar la consulta. 
	 * @return Una lista con arreglos de objetos cuya primera posición contiene el objeto {@link CuentaDTO} el cual contiene
	 * los datos de la cuenta consultada y en la segunda posición del arreglo la cantidad total liquidada como parte de la instrucción.
	 */
	List<Object[]> buscarCuentasYTotalPagadoDeInstruccion(List<Long> idsCuentas, RegistroContablePosicionNombradaDTO registroContable);
	
	
	
	// Para datos historicos
	
	
	/**
	 * Obtiene el detalle de un movimiento en base a si id de registro contable historicos
	 * @param idRegistroCOntable id del registro contable para obtener los detalles del movimiento
	 * @return BO que contiene la información del detalle del movimiento
	 */	
	RegContValNombradaHistorico consultarDetalleMovimientoHistorico(long idRegistroContable);
	
	/**
	 * Obtiene el detalle de los registros contables historicos que pertenecen a una operación DVP que presntamente
	 * pertenezcan a un pago en parcialidades de una misma instrucción origen.
	 * 
	 * @param registroContable el DTO con los criterios para realizar la consulta.
	 * @return una lista con objetos {@link RegistroContablePosicionNombradaDTO} que representan los registros contables en cuestin.
	 */
	List<OperacionPosicionDTO> buscarOperacionesDeInstruccionHistorico(RegistroContablePosicionNombradaDTO registroContable);
	
	/**
	 * Obtiene el detalle historico de las contrapartes que participan como receptores en una operación de tipo DVP en la que
	 * el interesado participa como traspasante.
	 * 
	 * @param registroContable el DTO con los criterios para realizar la consulta.
	 * @return una lista con arreglos de objetos cuya primera posición contiene el identificador de la cuenta de la
	 * contraparte. En la segunda posición contiene la fecha del último movimiento de la operación y la tercera posición
	 * contiene la cantidad total de la instrucción.
	 */
	List<Object[]> buscarContrapartesDeInstruccionHistorico(RegistroContablePosicionNombradaDTO registroContable);
	
	/**
	 * Obtiene la cuenta y el total liquidado hasta la fecha historica en esa cuenta como parte de una operación de tipo DVP.
	 * 
	 * @param idsCuentas los identificadores de las cuentas a consultar.
	 * @param registroContable el DTO con los criterios para realizar la consulta. 
	 * @return Una lista con arreglos de objetos cuya primera posición contiene el objeto {@link CuentaDTO} el cual contiene
	 * los datos de la cuenta consultada y en la segunda posición del arreglo la cantidad total liquidada como parte de la instrucción.
	 */
	List<Object[]> buscarCuentasYTotalPagadoDeInstruccionHistorico(List<Long> idsCuentas, RegistroContablePosicionNombradaDTO registroContable);
	
	/**
	 * Busca el detalle de un prestamo en base a el id de la instrucción a la que pertenece.
	 * 
	 * @param idInstruccion el identificador de la instrucción a consultar.
	 * @return un DTO con los datos del préstamo. <code>null</code> si no se encuentra el detalle del préstamo.
	 */
	RegistroContablePosicionNombradaDTO buscarDetalleDePrestamoHistorico(long idInstruccion);
	
	/**
	 * Busca las posiciones entregadas en garantía a un préstamo.
	 * 
	 * @param idInstruccion el identificador de la instrucción a consultar.
	 * @return una lista con los registros contables que contienen la información de las posiciones entregadas como garantía al préstamo.
	 */
	List<RegistroContablePosicionNombradaDTO> buscarGarantiasDePrestamosHistorico(long idInstruccion);
}
