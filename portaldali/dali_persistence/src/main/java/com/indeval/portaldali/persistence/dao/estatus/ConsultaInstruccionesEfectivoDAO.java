/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * ConsultaInstruccionesEfectivoDAO.java
 * Mar 7, 2008
 */
package com.indeval.portaldali.persistence.dao.estatus;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import com.indeval.portaldali.middleware.dto.InstruccionEfectivoDTO;
import com.indeval.portaldali.middleware.dto.RetiroEfectivoDTO;
import com.indeval.portaldali.middleware.dto.RetiroEfectivoInternacionalDTO;
import com.indeval.portaldali.middleware.dto.criterio.CriterioEstatusOpEfectivoDTO;
import com.indeval.portaldali.middleware.dto.util.EstadoPaginacionDTO;
import com.indeval.portaldali.persistence.model.InstruccionEfectivo;
import com.indeval.portaldali.persistence.model.InstruccionEfectivoSimple;


/**
 * Objeto de acceso a datos para realizar las consultas de instrucciones de efectivo.
 *
 * @author José Antonio Huizar Moreno
 * @version 1.0
 *
 */
public interface ConsultaInstruccionesEfectivoDAO {
	/**
	 * 
	 * Llave del mapa de resultados adicionales para el total del importe
	 */
	String TOTAL_IMPORTE = "totalImporte";
	String TOTAL_IMPORTE_MATCH = "totalImporteMatch";
	String TOTAL_IMPORTE_MOI = "totalImporteMoi";
	String TOTAL_IMPORTE_INTL = "totalImporteIntl";
	String TOTAL_IMPORTE_NAL = "totalImporteNal";
	
	/**
	 * Ejecuta la consulta de instrucciones de efectivo utilizando los criterios proporcionados por el usuario.
	 * 
	 * @param criterioConsulta el DTO con los datos necesarios para realizar la consulta.
	 * @param estadoPaginacion el DTO con el estado de la paginación actual.
	 * @return una lista con objetos de tipo {@link InstruccionEfectivoDTO} que contienen el detalle de los registros que cumplen con los criterios.
	 */
	List<InstruccionEfectivoDTO> consultarInstruccionesEfectivo(CriterioEstatusOpEfectivoDTO criterioConsulta, EstadoPaginacionDTO estadoPaginacion);
	
	
	/**
	 * Consulta de retiro de fondos internacional sin comprobacion
	 * @author fernando vazquez ulloa 06-11-2009
	 * @param CriterioEstatusOpEfectivoDTO
	 * @param EstadoPaginacionDTO
	 * @return
	 */
	List<RetiroEfectivoInternacionalDTO> getListRetiroFondosIntlSinComprobacion(CriterioEstatusOpEfectivoDTO criterio, EstadoPaginacionDTO estadoPaginacion);
	
	
	
	/**
	 * Consulta de retiro de fondos internacional en MOI
	 * @author fernando vazquez ulloa 06-11-2009
	 * @param CriterioEstatusOpEfectivoDTO
	 * @param EstadoPaginacionDTO
	 * @return
	 */
	List<RetiroEfectivoInternacionalDTO> getListRetiroFondosIntlEnMoi(CriterioEstatusOpEfectivoDTO criterio, EstadoPaginacionDTO estadoPaginacion);
	
	
	/**
	 * Consulta de instrucciones con match en el modulo de match.
	 * @author fernando vazquez ulloa 12-10-2009
	 * @param criterio
	 * @param paginacions
	 * @return
	 */
	List<InstruccionEfectivoDTO> consultarInstruccionesEnModuloMatchSinMatch(CriterioEstatusOpEfectivoDTO criterio, EstadoPaginacionDTO paginacion);

	
	
	/**
	 * Consulta de instrucciones del retiro de efectivo internacional por ID.
	 * @author fernando vazquez ulloa 2009-11-09
	 * @param criterio
	 * @param paginacions
	 * @return
	 */
	List<InstruccionEfectivoDTO> consultarInstRetiroEfecIntl(CriterioEstatusOpEfectivoDTO criterio, EstadoPaginacionDTO paginacion);
	
	List<InstruccionEfectivoDTO> consultarInstRetiroEfecNal(CriterioEstatusOpEfectivoDTO criterio, EstadoPaginacionDTO paginacion);
	
	/**
	 * Consulta de instrucciones del retiro de efectivo internacional por ID.
	 * @author fernando vazquez ulloa 2009-11-09
	 * @param criterio
	 * @param paginacions
	 * @return
	 */
	List<RetiroEfectivoInternacionalDTO> consultarInstRetiroEfecIntlRetREI(CriterioEstatusOpEfectivoDTO criterio, EstadoPaginacionDTO paginacion);
	
	
	List<RetiroEfectivoDTO> consultarRetiroEfectivo(CriterioEstatusOpEfectivoDTO criterio, EstadoPaginacionDTO paginacion);

	
	
	/**
	 * @author 2009-11-09
	 * @param id
	 * Permite la actualizacion par aprobracion de los retiros de efectivo internacional.
	 */
	void aprobarRetiroEfectivoInternacionalById(String id);
	
	void aprobarRetiroEfectivoById(String id, Map<String, Object> datosFirma);
	
	/**
	 * @author 2009-11-10
	 * @param id
	 * Permite la actualizacion par aprobracion de los retiros de efectivo internacional.
	 */
	void liberarRetiroEfecIntlById(String id);
	
	void liberarRetiroEfectivoById(String id,Map<String, Object> datosFirma);
	
	
	
	/**
	 * Obtiene la proyección del total de registros que resultarn de ejecutar la consulta de efectivo utilizando los criterios 
	 * proporcionados por el usuario.
	 * 
	 * @param criterioConsulta el DTO con los datos necesarios para realizar la consulta.
	 * @return el número total de registros que resultarn de ejecutar la consulta de efectivo utilizando los criterios proporcionados 
	 * por el usuario. 
	 */
	int obtenerProyeccionConsultaInstruccionesEfectivo(CriterioEstatusOpEfectivoDTO criterioConsulta); 
	
	
	int obtenerProyeccionConsultaMensajeBean(CriterioEstatusOpEfectivoDTO criterioConsulta);
	
	int obtenerProyeccionConsultaRetiroEfectivo(CriterioEstatusOpEfectivoDTO criterioConsulta);
	
	/**
	 * Obtiene la proyección del total de registros que resultarn de ejecutar la consulta de efectivo intl utilizando 
	 * los criterios proporcionados por el usuario.
	 * 
	 * @param criterioConsulta el DTO con los datos necesarios para realizar la consulta.
	 * @return el número total de registros que resultarn de ejecutar la consulta de efectivo utilizando los criterios 
	 * proporcionados por el usuario. 
	 */
	int getProyeccionConsultaInstEfecIntl(CriterioEstatusOpEfectivoDTO criterioConsulta);
	
	
	/**
	 * Llena un mapa de resultados con las columnas de totales a consultar:
	 * total de monto
	 * @param criterioConsulta Criterio para la realización de la consulta
	 * 
	 */
	Map<Object, Object> obtenerTotalesDeConsultaInstruccionesEfectivo(CriterioEstatusOpEfectivoDTO criterioConsulta);
	/**
	 * Llena un mapa de resultados con las columnas de totales a consultar de la tabla del match:
	 * total de monto
	 * @param criterioConsulta Criterio para la realización de la consulta
	 * @author fernando vazquez ulloa 21-10-2009
	 */
	Map<Object, Object> obtenerTotalesDeConsultaInstruccionesEfectivoMatch(CriterioEstatusOpEfectivoDTO criterioConsulta);
	
	Map<Object, Object> obtenerTotalesDeConsultaRetiroEfectivo(CriterioEstatusOpEfectivoDTO criterioConsulta);
	
	/**
	 * Llena un mapa de resultados con las columnas de totales a consultar para internacional en el MOI:
	 * total de monto
	 * @param criterioConsulta Criterio para la realización de la consulta
	 * 
	 */
	Map<Object, Object> getTotDeConsInstEfecIntlMoi(CriterioEstatusOpEfectivoDTO criterioConsulta);
	/**
	 * Llena un mapa de resultados con las columnas de totales a consultar para internacional:
	 * total de monto
	 * @param criterioConsulta Criterio para la realización de la consulta
	 * 
	 */
	Map<Object, Object> getTotDeConsInstEfecIntl(CriterioEstatusOpEfectivoDTO criterioConsulta);
	
	/**
	 * Recupera las operaciones RETE que estan en estado Liquidado-Aprobado
	 */
	List<InstruccionEfectivoDTO> consultarReteAprobadas();
		
	/**
	 * Recupera un registro por ID
	 * 
	 * @param idInstruccion
	 * 
	 */
	InstruccionEfectivo getById(BigInteger idInstruccion);

	/**
	 * Recupera una instruccion de efectivo por el folio instruccion
	 * 
	 * @param folioInstruccion
	 * @return
	 */
	InstruccionEfectivoSimple getByFolioInstruccion(BigInteger folioInstruccion);
}
