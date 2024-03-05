/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * ConsultaEstatusOperacionesEfectivoService.java
 * Mar 6, 2008
 */
package com.indeval.portaldali.middleware.services.estatus;

import java.util.List;
import java.util.Map;

import com.indeval.portaldali.middleware.dto.InstruccionEfectivoDTO;
import com.indeval.portaldali.middleware.dto.RetiroEfectivoDTO;
import com.indeval.portaldali.middleware.dto.RetiroEfectivoInternacionalDTO;
import com.indeval.portaldali.middleware.dto.criterio.CriterioEstatusOpEfectivoDTO;
import com.indeval.portaldali.middleware.dto.util.EstadoPaginacionDTO;


/**
 * Interfaz que define el contrato de del servicio de negocio para realizar consultas de estatus de 
 * operaciones de efectivo.
 *
 * @author José Antonio Huizar Moreno
 * @version 1.0
 *
 */
public interface ConsultaInstruccionesEfectivoService {
	/** 
	 * Llave del mapa de resultados adicionales para el total del importe
	 */
	String TOTAL_IMPORTE = "totalImporte";
	String TOTAL_IMPORTE_MATCH = "totalImporteMatch";
	String TOTAL_IMPORTE_INTL = "totalImporteIntl";
	String TOTAL_IMPORTE_NAL = "totalImporteNal";
	String TOTAL_IMPORTE_MOI = "totalImporteMoi";
	/**
	 * Ejecuta la consulta de instrucciones de efectivo utilizando los criterios proporcionados por el usuario.
	 * 
	 * @param criterioConsulta el DTO con los datos necesarios para realizar la consulta.
	 * @param estadoPaginacion el DTO con el estado de la paginación actual.
	 * @return una lista con objetos de tipo {@link InstruccionEfectivoDTO} que contienen el detalle de los registros que cumplen con los criterios.
	 */
	List<InstruccionEfectivoDTO> consultarInstruccionesEfectivo(CriterioEstatusOpEfectivoDTO criterioConsulta, EstadoPaginacionDTO estadoPaginacion);
	
	/**
	 * Ejecuta la consulta de instrucciones de retiro de efectivo internacional utilizando los criterios proporcionados por el usuario.
	 * 
	 * @param criterioConsulta el DTO con los datos necesarios para realizar la consulta.
	 * @param estadoPaginacion el DTO con el estado de la paginación actual.
	 * @return una lista con objetos de tipo {@link RetiroEfectivoInternacionalDTO} que contienen el detalle de los registros que cumplen con los criterios.
	 */
	List<RetiroEfectivoInternacionalDTO> getInstEfecIntl(CriterioEstatusOpEfectivoDTO criterioConsulta, EstadoPaginacionDTO estadoPaginacion);
	
	
	

	/**
	 * 
	 * Regresa un unico registro del match consultado por su ID
	 * @param IdMatch
	 * @return
	 */
	InstruccionEfectivoDTO consultarInstruccionMatchById(String idMatch);
	
	
	/**
	 * 
	 * Regresa un unico registro del retiro de efectivo internacional consultado por su ID. c_retiro_efectivo_int
	 * @param Id
	 * @return
	 */
	InstruccionEfectivoDTO consultarInstEfecIntlById(String id);

	/**
	 * 
	 * Regresa un unico registro del retiro de efectivo internacional consultado por su ID. c_retiro_efectivo_int
	 * @param Id
	 * @return
	 */
	RetiroEfectivoInternacionalDTO consultarInstEfecIntlByIdRetREI(String id);
	
	RetiroEfectivoDTO consultarRetiroEfectivoById(String id);
	
	/**
	 * 
	 * Regresa un unico registro del retiro de efectivo internacional consultado por su ID. c_retiro_efectivo_int
	 * @param Id
	 * @return
	 */
	/*RetiroEfectivoInternacionalVO consultarInstEfecIntlByIdRetREI(String id);*/
	
	
	
	/**
	 * 
	 * Aprobar un retiro en efectivo internacional
	 * @param Id - Identificador del registro c_retiro_efectivo_int
	 * @return
	 */
	void aprobarRetiroEfectivoInternacionalById(String id);
	
	void aprobarRetiroEfectivoById(String id, Map<String, Object> datosFirma);
	/**
	 * 
	 * Liberar un retiro en efectivo internacional
	 * @param Id - Identificador del registro c_retiro_efectivo_int
	 * @return
	 */
	void liberarRetiroEfecIntlById(String id);	
	
	void liberarRetiroEfectivoById(String id, Map<String, Object> datosFirma);
	
	
	
	
	/**
	 * Obtiene la proyección del total de registros que resultarn de ejecutar la consulta de efectivo utilizando los criterios proporcionados por el usuario.
	 * 
	 * @param criterioConsulta el DTO con los datos necesarios para realizar la consulta.
	 * @return el número total de registros que resultarn de ejecutar la consulta de efectivo utilizando los criterios proporcionados por el usuario. 
	 */
	int obtenerProyeccionConsultaInstruccionesEfectivo(CriterioEstatusOpEfectivoDTO criterioConsulta, Boolean debeDejarLog);
	
	/**
	 * Obtiene la proyección del total de registros que resultarn de ejecutar la consulta de efectivo intl 
	 * utilizando los criterios proporcionados por el usuario.
	 * 
	 * @param criterioConsulta el DTO con los datos necesarios para realizar la consulta.
	 * @return el número total de registros que resultarn de ejecutar la consulta de efectivo utilizando los criterios 
	 * proporcionados por el usuario. 
	 */
	
	int getProyeccionConsultaInstEfecIntl(CriterioEstatusOpEfectivoDTO criterioConsulta, Boolean debeDejarLog);
	
	
	/**
	 * Llena un mapa de resultados con las columnas de totales a consultar internacional:
	 * total de monto
	 * @param criterioConsulta Criterio para la realización de la consulta
	 * 
	 */
	Map<Object,Object> sumTotDeConsInstEfec(CriterioEstatusOpEfectivoDTO criterioConsulta);
	
	
	/**
	 * Llena un mapa de resultados con las columnas de totales a consultar para efectivo :
	 * total de monto
	 * @param criterioConsulta Criterio para la realización de la consulta
	 * 
	 */
	Map<Object,Object> obtenerTotalesDeConsultaInstruccionesEfectivo(CriterioEstatusOpEfectivoDTO criterioConsulta);
	
	boolean validaLiberacion();

	/**
	 * Obtiene los retiros SPEI/SIAC Liquidados-aprobados
	 */
	List<InstruccionEfectivoDTO> consultarReteAprobadas();

}
