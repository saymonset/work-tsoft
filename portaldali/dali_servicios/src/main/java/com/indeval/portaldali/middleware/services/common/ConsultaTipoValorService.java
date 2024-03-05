/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * 
 * 03/03/2008
 */
package com.indeval.portaldali.middleware.services.common;

import java.util.List;

import com.indeval.portaldali.middleware.dto.MercadoDTO;
import com.indeval.portaldali.middleware.dto.TipoValorDTO;

/**
 * Interface de servicio de negocio encargado de realizar las operaciones 
 * de consulta a la tabla de instrumentos.
 * 
 * @author Emigdio Hernández
 * @version 1.0
 */
public interface ConsultaTipoValorService {

	/**
	 * Obtiene una lista con objetos de tipo TipoValorDTO con los datos de los tipos de 
	 * valores o instrumentos que se pueden operar, asociados a las emisiones.
	 * 
	 * @param mercado DTO con los datos del mercado por el cual se filtran los tipos de valores.
	 * @return  Lista con objetos de tipo TipoValorDTO con los datos de los tipos de 
	 * valores o instrumentos que se pueden operar, asociados a las emisiones.
	 */
	List<TipoValorDTO> buscarTiposDeValoresPorMercado(MercadoDTO mercado);
	
	/**
	 * Busca un Tipo de Instrumento en la base de datos basado en el ID
	 * @param idTipoValor Id del tipo de instrumento
	 * @return DTO que representa los datos del tipos de Valor
	 */
	TipoValorDTO buscarTipoDeValorPorId(long idTipoValor);
	/**
	 * Obtiene una lista con objetos de tipo TipoValorDTO con los datos de los tipos de 
	 * valores o instrumento que se pueden operar, asociados a las emisiones.
	 * 
	 * @param mercado DTO con los datos del mercado por el cual se filtran los tipos de valores.
	 * @param prefijo Prefijo para buscar la clave y/o descripción del tipo de valor
	 * @return  Lista con objetos de tipo TipoValorDTO con los datos de los tipos de 
	 * valores o instrumento que se pueden operar, asociados a las emisiones.
	 */
	List<TipoValorDTO> buscarTiposDeValoresPorMercadoYPrefijo(MercadoDTO mercado,String prefijo);
	/**
	 * Obtiene una lista con objetos de TipoValorDTO con los datos de los tipos de valores
	 * o instrumento que se pueden operar, asociados a las emisiones.
	 * @param mercados Identificadores válidos de mercado de dinero para realizar la búsqueda
	 * @param prefijo Prefijo del clave de tipo de valor
	 * @return Lista con los tipos de valor encontrados
	 */
	List<TipoValorDTO> buscarTiposDeValoresPorMercados(Long[] idsMercados,String prefijo);
	
	/**
	 * Busca un tipo de valor en específico basado en su clave de tipo de valor
	 * @param clave Calve de tipo de valor
	 * @return DTO con los datos localizados, null en caso de no localizarlo
	 */
	TipoValorDTO buscarTipoDeValorPorClave(String clave);
}
