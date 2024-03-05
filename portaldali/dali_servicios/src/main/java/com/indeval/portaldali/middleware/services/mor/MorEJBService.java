package com.indeval.portaldali.middleware.services.mor;

import java.util.List;

import com.indeval.mor.dto.ConsultaRecuperacionDTO;
import com.indeval.mor.exception.MorDataException;
import com.indeval.mor.exception.MorException;
import com.indeval.mor.vo.DetalleMorVo;
import com.indeval.mor.vo.EmisoraVo;
import com.indeval.mor.vo.EstadoRecuperacionMorVo;
import com.indeval.mor.vo.InstrumentoVo;
import com.indeval.mor.vo.ModuloOrigenVo;
import com.indeval.mor.vo.PaginaVo;
import com.indeval.mor.vo.RecuperacionMorVo;
import com.indeval.mor.vo.TipoMovimientoVo;

@SuppressWarnings("rawtypes")
public interface MorEJBService  {
	/**
	 * Procesa el DTO para hasta lograr Consultar Recuperaciones unificando informacion de movimientos
	 * @param ConsultaRecuperacionDTO DTO con consultao.
	 * @throws MorDataException
	 */
	PaginaVo obtenerConsultaRecuperacion(ConsultaRecuperacionDTO consultaRecuperacionDTO) throws MorException;
	
	/**
	 * Procesa el DTO para hasta lograr Consultar Recuperaciones unificando informacion de movimientos
	 * @param ConsultaRecuperacionDTO DTO con consultao.
	 * @throws MorDataException
	 */
	PaginaVo<DetalleMorVo> obtenerConsultaRecuperacionPortalDali(ConsultaRecuperacionDTO consultaRecuperacionDTO) throws MorException;
	
	/**
	 * Obtiene una lista de instrumentos que coincidan con el prefijo de busqueda
	 * @param prefijo Prefijo para realizar la busqueda
	 * @return Lista con los intrumentos encontrados
	 */
	List<InstrumentoVo> obtenerInstrumentosPorPrefijo(String prefijo);
	
	/**
	 * Obtiene una lista de enisoras que coincidan con el prefijo de busqueda
	 * @param prefijo Prefijo para realizar la busqueda
	 * @return Lista con las emisoras encontradas
	 */
	List<EmisoraVo> obtenerEmisorasPorPrefijo(String prefijo);
	
	/**
	 * Obtiene una institucion con la clave y folio enviados.
	 * @param claveFolio clave y folio de la institucion a obtener.
	 * @return la institucion con el clave y folio enviados.
	 */

	/**
	 * Obtiene informacion de las emisiones que tienen datos parecidos a los prefijos enviados, encapsulando la informacion en RecuperacionMorVo.
	 * @param prefijoTV Prefijo de TV.
	 * @param prefijoEmisora Prefijo de Emisora.
	 * @param prefijoSerie Prefijo de Serie.
	 * @return Lista de objetos RecuperacionMorVo con la informacion de las emisiones.
	 */
	public List<RecuperacionMorVo> obtenerRecuperacionMorPorPrefijos(String prefijoTV, String prefijoEmisora, String prefijoSerie);
	
	/**
	 * Obtiene todos los estados de recuperacion del MOR.
	 * @return Lista de estados de recuperacion del MOR.
	 */
	public List<EstadoRecuperacionMorVo> obtenerEstadosRecuperacionMor();
	
	/**
	  * Obtiene todos los Tipos de Movimiento del MOR
	  * @return Lista de tipos de movimientos
	  */
	List<TipoMovimientoVo> obtenerTiposMovimiento() ;
	
	/**
	  * Obtiene todos los Modulos Origen del MOR
	  * @return Lista de Modulos Origen 
	  */
	List<ModuloOrigenVo>  obtenerModulosOrigen();

}
