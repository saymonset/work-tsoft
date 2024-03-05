/**
 * 2H Software - Bursatec
 * Sistema de Consulta de Estados de Cuenta
 *
 */
package com.indeval.portaldali.middleware.services.common;

import java.util.List;

import com.indeval.portaldali.middleware.dto.TipoTenenciaDTO;

/**
 * Define la funcionalidad del servicio de negocio encargado de realizar las
 * operaciones de consulta al catálogo de tipos de cuenta.
 * 
 * @author Emigdio Hernández
 * @version 1.0
 */
public interface ConsultaTipoTenenciaService {
	/**
	 * Consulta los valores disponibles del catálogo de tipo de cuenta (llamado
	 * tipo de tenencia en el negocio). Estos valores estn filtrados
	 * necesariamente por tipo de cuenta (Nombrada o controlada) y por
	 * naturaleza de la cuenta (Pasivo o Activo)
	 * 
	 * @param criterio
	 *            DTO con el criterio de consulta
	 * @return Lista con los tipos de tenencia encontrados
	 */
	public List<TipoTenenciaDTO> consultarTipoCuentaPorTipoCuentaYTipoNaturaleza(
			TipoTenenciaDTO criterio);

	/**
	 * Busca un elemento en el catálogo de tipos de tenencia por medio de su
	 * identificador.
	 * 
	 * @param idTipoTenencia
	 *            el identificador a consultar.
	 * @return un elemento de tipo {@link TipoTenenciaDTO} el cual corresponde
	 *         al identificador proporcionado. <code>null</code> en caso de no
	 *         existir el elemento.
	 */
	public TipoTenenciaDTO buscarTipoTenenciaPorId(long idTipoTenencia);
}