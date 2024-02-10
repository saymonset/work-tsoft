/**
 * 
 */
package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import java.util.List;
import java.util.Set;

import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.modelo.Divisa;
import com.indeval.portalinternacional.middleware.servicios.modelo.BitacoraConfirmacion;
import com.indeval.portalinternacional.middleware.servicios.modelo.ConfirmacionEfectivo;
import com.indeval.portalinternacional.middleware.servicios.vo.ConfirmacionIntDTO;

/**
 * @author César Hernández
 *
 */
public interface ConfirmacionIntService {

	/**
	 * Consulta los BIC codes
	 * @return
	 */
	List<String> consultaBicCodes();
	
	/**
	 * Consultas las divisas a partir de una lista de BIC codes
	 * @param bicCodes
	 * @return
	 */
	List<Divisa> consultaDivisas(Set<String> bicCodes);
	
	/**
	 * Consultas las cuentas tomando en cuenta los BIC Codes y divisas
	 * @param bicCodes
	 * @param divisas
	 * @return
	 */
	List<String> consultaCuentas(Set<String> bicCodes, Set<String> divisas);
	
	/**
	 * Consulta las confirmaciones
	 * @param confirmacionIntDTO
	 * @param paginaVO
	 * @return
	 */
	PaginaVO consultaConfirmaciones(ConfirmacionIntDTO confirmacionIntDTO, final PaginaVO paginaVO);
	
	/**
	 * Consulta la bitacora de confirmacion
	 * @param idConfirmacion
	 * @return
	 */
	BitacoraConfirmacion consultaBitacoraConfirmacion(final Long idConfirmacion);
	
	/**
	 * Agrega o actualiza los comentarios dali para un registro de confirmacion
	 * @param idConfirmacion
	 * @param comentariosDali
	 */
	void agregaActualizaComentariosDali(final Long idConfirmacion, final String comentariosDali);
	
	/**
	 * Actualiza el id folio para un registro en especifico
	 * @param idConfirmacion
	 * @param idFolio
	 */
	void agregaActualizaIdFolio(final Long idConfirmacion, final String idFolio);
	
	/**
	 * Clcula la suma total de la cantidad
	 * @param dto
	 * @return
	 */
	Double obtieneSumaTotalCreditos(final ConfirmacionIntDTO dto);
	
	/**
	 * Clcula la suma total de debitos
	 * @param dto
	 * @return
	 */
	Double obtieneSumaTotalDebitos(final ConfirmacionIntDTO dto);
	
	void ordernarConfirmaciones(List<ConfirmacionEfectivo> listaConfirmacion);
}
