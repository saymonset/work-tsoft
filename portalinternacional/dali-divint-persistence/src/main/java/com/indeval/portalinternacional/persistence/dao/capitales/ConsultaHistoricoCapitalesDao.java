/**
 * 
 */
package com.indeval.portalinternacional.persistence.dao.capitales;

import com.bursatec.persistence.dao.BaseDao;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.servicios.vo.ParamConsultaDetalleEjerDerCapTO;

/**
 * @author lmunoz
 * 
 */
public interface ConsultaHistoricoCapitalesDao extends BaseDao {
    
	/**
	 * 
	 * @param paginaVO
	 * @param params
	 * @param esExportacion
	 * @return
	 */
	PaginaVO consultaHistoricoCapitales(PaginaVO paginaVO, ParamConsultaDetalleEjerDerCapTO params, boolean esExportacion);
    
	/**
	 * 
	 * @param pagina
	 * @param params
	 * @param esExportacion
	 * @return
	 */
    PaginaVO consultaHistoricoCapitalesCuenta(PaginaVO pagina, final ParamConsultaDetalleEjerDerCapTO params, boolean esExportacion);
}
