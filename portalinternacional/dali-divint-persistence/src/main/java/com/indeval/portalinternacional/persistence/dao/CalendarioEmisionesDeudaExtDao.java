package com.indeval.portalinternacional.persistence.dao;

import java.util.List;
import java.util.Set;

import com.bursatec.persistence.dao.BaseDao;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.modelo.Boveda;
import com.indeval.portaldali.persistence.modelo.TipoBoveda;
import com.indeval.portalinternacional.middleware.servicios.modelo.BitacoraMensajeSwift;
import com.indeval.portalinternacional.middleware.servicios.modelo.CalendarioDerechos;
import com.indeval.portalinternacional.middleware.servicios.modelo.Control;
import com.indeval.portalinternacional.middleware.servicios.modelo.Custodio;
import com.indeval.portalinternacional.middleware.servicios.modelo.EstadoDerechoInt;
import com.indeval.portalinternacional.middleware.servicios.modelo.TipoPagoInt;
import com.indeval.portalinternacional.middleware.servicios.vo.CalendarioEmisionesDeudaExtDTO;

public interface CalendarioEmisionesDeudaExtDao extends BaseDao {
	/**
	 * 
	 * @return
	 */
	public List<EstadoDerechoInt> getCatalogoEstadosDerechoInt();
	/**
	 * 
	 * @param isCAEV
	 * @return
	 */
	public List<TipoPagoInt> getCatalogoTiposPagoInt(Boolean isCAEV);		
	/**
	 * 	
	 * @return
	 */
	public List<Custodio> getCatalogoCustodios();
	/**
	 * 
	 * @param params
	 * @param offset
	 * @param regxpag
	 * @return
	 */
	public PaginaVO consultaCalendarioDerechos(CalendarioEmisionesDeudaExtDTO params, PaginaVO pagina);
	
	/**
	 * 
	 * @param ids
	 * @param nuevoEstado
	 * @return
	 * @throws BusinessException
	 */
	public Integer actualizarEstadosDerechoInt(Set<Long> ids, Integer nuevoEstado);
	/**
	 * 
	 * @param id
	 * @return
	 */
	public List<BitacoraMensajeSwift>  getBitacoraMensajeSwiftbyId(Long id);			
	/**
	 * 
	 * @param id
	 * @return
	 */
	public List<CalendarioDerechos> consultaCalendarioDerechosByIds(final Set<Long> ids);
	/**
	 * 
	 * @param soloInternacional
	 * @return
	 */
	 public List<Boveda> consultaBovedas(Integer tipoBoveda);
	/**
	 * 
	 * @param idBoveda
	 * @return
	 */
	 public Boveda consultaBoveda(Long idBoveda);
	 /**
	  * 
	  * @param id
	  * @return
	  * @throws BusinessException
	  */
	 public List<Control> obtieneEstadosMensajeria(String id) throws BusinessException;
	 
	 /**
	  * Regresa la bitacora filtrada por el id 
	  * @param id
	  * @return
	  */
	 public List<BitacoraMensajeSwift>  getBitacoraMensajeSwiftbyIdHist(final Long id);
}
