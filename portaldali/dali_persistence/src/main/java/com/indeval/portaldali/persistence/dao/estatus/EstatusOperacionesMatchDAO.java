/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * EstatusOperacionesMatchDAO.java
 * 04/03/2008
 */
package com.indeval.portaldali.persistence.dao.estatus;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.indeval.portaldali.middleware.dto.OperacionValorMatchDTO;
import com.indeval.portaldali.middleware.dto.criterio.CriterioMatchOperacionesDTO;
import com.indeval.portaldali.middleware.dto.criterio.CriterioMatchOperacionesExportacionDTO;
import com.indeval.portaldali.middleware.dto.util.EstadoPaginacionDTO;
import com.indeval.portaldali.middleware.model.util.ConsultaOperacionesMatch;

/**
 * Interfaz que define la funcionliadad que debe de implementar el objeto de
 * acceso a datos para la consulta unificada de Match y Estatus de Operaciones.
 * 
 * @author Emigdio Hernández
 * 
 */
public interface EstatusOperacionesMatchDAO {
	/**
	 * Consulta las operaciones de valor registradas en el sistema según los
	 * criterios recibidos como parámetro. Este método únicamente consulta datos
	 * de la tabla de MOV T_INSTRUCCION_OPERACION_VAL El objeto de criterios
	 * contiene 2 tipos de propiedades campos primitivos como String o boolea y
	 * otros DTOs que representan criterios de búsqueda de alguna tabla
	 * asociada. Los criterios de búsqueda representados por otros DTOs
	 * consideran que para cada DTO asociado al criterio existe un ID , dicho id
	 * (el equivalente al id de la tabla) ser el dato tomado en cuenta para
	 * realizar la búsqueda. Un valor de -1 indica que el criterio no se debe de
	 * incluir en la búsqueda.
	 * 
	 * @param criterio
	 *            DTO con los criterios para realizar la consulta.
	 * @param paginacion
	 *            Estado de la paginación
	 * @return Lista con los objetos que representan los datos de las
	 *         operaciones localizadas
	 */
	Collection<OperacionValorMatchDTO> consultarOperacionesValor(CriterioMatchOperacionesDTO criterio, EstadoPaginacionDTO paginacion);

	/**
	 * Consulta las operaciones de valores registradas en el sistema que fueron
	 * creadas como parcialidades. Este método busca sobre la tabla de
	 * parcialidades.
	 * 
	 * @param criterio
	 *            el DTO con los criterioes para realizar la consulta.
	 * @param paginacion
	 *            el estado de la paginacion
	 * @return Lista con los objetos que representan los datos de las
	 *         operaciones localizadas
	 */
	List<OperacionValorMatchDTO> buscarParcialidades(CriterioMatchOperacionesDTO criterio, EstadoPaginacionDTO paginacion);

	/**
	 * Obtiene la proyección de los resultados de una consulta de operaciones de
	 * valor / match basado en los criterios enviados. Los criterios de búsqueda
	 * representados por otros DTOs consideran que para cada DTO asociado al
	 * criterio existe un ID , dicho id (el equivalente al id de la tabla) ser
	 * el dato tomado en cuenta para realizar la búsqueda. Un valor de -1 indica
	 * que el criterio no se debe de incluir en la búsqueda.
	 * 
	 * @param criterio
	 *            DTO con los criterios para realizar la consulta.
	 * @return número de registros localizados
	 */
	long obtenerProyeccionConsultaOperacionesValor(CriterioMatchOperacionesDTO criterio);

	/**
	 * Consulta las instrucciones de match pendientes de match registradas en la
	 * bd según los criterios recibidos como parámetro. El objeto de criterios
	 * contiene 2 tipos de propiedades campos primitivos como String o boolea y
	 * otros DTOs que representan criterios de búsqueda de alguna tabla
	 * asociada. Los criterios de búsqueda representados por otros DTOs
	 * consideran que para cada DTO asociado al criterio existe una clave ,
	 * dicha clave ser el dato tomado en cuenta para realizar la búsqueda. Un
	 * valor de -1 en el ID del DTO asociado al criterio indica que el criterio
	 * no se debe de incluir en la búsqueda.
	 * 
	 * @param criterio
	 *            DTO con los criterios para realizar la consulta.
	 * @param paginacion
	 *            Estado de la paginación
	 * @return Lista con los objetos que representan los datos de las
	 *         instrucciones de match localizadas
	 */
	List<OperacionValorMatchDTO> consultarInstruccionesMatchSinOperacion(CriterioMatchOperacionesDTO criterio, EstadoPaginacionDTO paginacions);
	
	



	
	
	
	/**
	 * Consulta las instrucciones match sin operacion por medio de su
	 * identificador.
	 * 
	 * @param idBitacoraMatch
	 *            el identificador de la instrucción a buscar.
	 * @return la instrucción relacionada con el identificador proporcionado.
	 */
	public List<OperacionValorMatchDTO> consultarInstruccionesMatchSinOperacionPorId(final Long idBitacoraMatch);

	/**
	 * Obtiene la proyección de los resultados de una consulta de instrucciones
	 * de match basado en los criterios enviados. Los criterios de búsqueda
	 * representados por otros DTOs consideran que para cada DTO asociado al
	 * criterio existe una clave , dicha clave ser el dato tomado en cuenta
	 * para realizar la búsqueda. Un valor de -1 en el ID del DTO asociado al
	 * criterio indica que el criterio no se debe de incluir en la búsqueda.
	 * 
	 * @param criterio
	 *            DTO con los criterios para realizar la consulta.
	 * @return número de registros localizados
	 */
	long obtenerProyeccionConsultaInstruccionesMatchSinOperacion(CriterioMatchOperacionesDTO criterio);

	/**
	 * Consulta las operaciones de valor registradas en el sistema según los
	 * criterios recibidos como parámetro. Este método une la tabla de mov con
	 * la tabla de match según los criterios de : Instituci y cuenta
	 * traspasante y receptora folio de instrucción cantidad de títulos tipo de
	 * mensaje emisión fecha de liquidacicn El objeto de criterios contiene 2
	 * tipos de propiedades campos primitivos como String o boolea y otros DTOs
	 * que representan criterios de búsqueda de alguna tabla asociada. Los
	 * criterios de búsqueda representados por otros DTOs consideran que para
	 * cada DTO asociado al criterio existe un ID , dicho id (el equivalente al
	 * id de la tabla) ser el dato tomado en cuenta para realizar la búsqueda.
	 * Un valor de -1 indica que el criterio no se debe de incluir en la
	 * búsqueda.
	 * 
	 * @param criterio
	 *            DTO con los criterios para realizar la consulta.
	 * @param paginacion
	 *            Estado de la paginación
	 * @return Lista con los objetos que representan los datos de las
	 *         operaciones localizadas
	 */
	List<OperacionValorMatchDTO> consultarInstruccionesMatchConOperacion(CriterioMatchOperacionesDTO criterio, EstadoPaginacionDTO paginacion);

	/**
	 * Consulta un registro de operaciones de valor basado en su identificador
	 * único.
	 * 
	 * @param idInstruccion
	 *            Identificador del registro
	 * @return una lista donde el último elemento es el DTO con los datos que
	 *         representan la operación, los demás elementos en la lista son las
	 *         parcialidades relacionadas con la operación en caso de existir,
	 *         lista vacía en caso de no encontrarse
	 */
	List<OperacionValorMatchDTO> consultarInstruccionOperacionValorPorId(long idInstruccion);

	/**
	 * Realiza una consulta a las tablas de instrucción liquidación para
	 * verificar el número de parcialiadades de una operación de MOV, retorna un
	 * mapa con los identificadores de la operación como llave y una bandera
	 * true si la operación tiene parcialidades y false si no tiene
	 * parcialidades.
	 * 
	 * @param criterio
	 *            DTO con los valores para realizar la consulta.
	 * @return Mapa con el indicador si tiene o no parcialidades.
	 */
	Map<Long, Boolean> verificarSiOperacionTieneParcialidades(CriterioMatchOperacionesDTO criterio);

    /**
     * Consulta las operaciones valor registradas en la bd segun los criterios recibidos como parametro. 
     * 
     * @param criterio DTO con los criterios para realizar la consulta.
     * @return Lista con los objetos que representan los datos de las operaciones valor localizadas
     */
    Collection<ConsultaOperacionesMatch> consultarOperacionesValorExportacion(CriterioMatchOperacionesExportacionDTO criterio, String instOcultarFechaHoraCierreOper);

    /**
     * Consulta las instrucciones de match pendientes de match registradas en la bd segun los criterios recibidos como 
     * parametro. 
     * 
     * @param criterio DTO con los criterios para realizar la consulta.
     * @return Lista con los objetos que representan los datos de las instrucciones de match localizadas
     */
    List<ConsultaOperacionesMatch> consultarInstruccionesMatchSinOperacionExportacion(CriterioMatchOperacionesExportacionDTO criterio, String instOcultarFechaHoraCierreOper);

    /**
     * Consulta las parcialidades registradas en la bd segun los criterios recibidos como parametro. 
     * 
     * @param criterio DTO con los criterios para realizar la consulta.
     * @return Lista con los objetos que representan los datos de las operaciones valor localizadas
     */
    List<ConsultaOperacionesMatch> buscarParcialidadesExportacion(CriterioMatchOperacionesExportacionDTO criterio, String instOcultarFechaHoraCierreOper);

}
