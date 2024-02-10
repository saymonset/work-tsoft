/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.persistence.dao;

import java.util.List;

import com.bursatec.persistence.dao.BaseDao;
import com.indeval.portaldali.middleware.servicios.modelo.dto.EstatusEmisionesDTO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.EmisionVO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.servicios.modelo.CatBic;
import com.indeval.portalinternacional.middleware.servicios.modelo.SicEmision;
import com.indeval.portalinternacional.middleware.servicios.vo.SicEmisionVO;

/**
 * @author javiles
 *
 */
public interface SicEmisionDao extends BaseDao {
    
    /**
     * Obtiene una lista de instancias de SicEmision correspondientes a los
     * datos de la emisi&oacute;n propocionados (tv, emisora serie y cupon o isin)
     * 
     * @param emisionVO
     * @return SicEmision
     */
    public List<SicEmision> findSicEmisionesByEmision(EmisionVO emisionVO);
    
    /**
     * Obtiene una lista de instancias de SicEmision correspondientes a los
     * datos del agente proporcionados (id, folio, cuenta)
     * 
     * @param agenteVO
     * @return List<SicEmision>
     */
    public List<SicEmision> findSicEmisionesByAgente(AgenteVO agenteVO);

    /**
     * Obtiene una lista de instancias de SicEmision correspondientes al
     * custodio proporcionado
     * 
     * @param catBic
     * @param todas
     * @return List<SicEmision>
     */
    public List<SicEmision> findSicEmisionesByCustodio(final CatBic catBic, Boolean todas);
    
    /**
     * Obtiene una lista de instancias de SicEmision correspondientes a los criterios proporcionados
     * 
     * @param catBic
     * @param emisionVO
     * @param formaDeOperar
     * @param paginaVO
     * @return PaginaVO
     */
    public PaginaVO findSicEmisionesByCustodio(final CatBic catBic, final EmisionVO emisionVO, final String formaDeOperar, final PaginaVO paginaVO);
    
    /**
     * Obtiene un objero SicEmision a partir de su Id
     * @param idSicEmision
     * @return SicEmision
     */
    public SicEmision findSicEmsionByPk(Long idSicEmision);
    
    /**
     * Obtiene las emisiones internacionales por custodio y emision
     * 
     * @param catBic
     * @param emisionVO
     * @param paginaVO
     * @return
     */
    public PaginaVO findSicEmisionesByEmisionAndCustodio(EstatusEmisionesDTO estatusEmisionesDTO, CatBic catBic, EmisionVO emisionVO, PaginaVO paginaVO);
    
    /**
     * Obtiene las emisiones internacionales por custodio y emision, y con Posicion Cero.
     * @param catBic
     * @param emisionVO
     * @param paginaVO
     * @return
     */
    public PaginaVO findSicEmisionesByEmisionAndCustodioPosicionCero(EstatusEmisionesDTO estatusEmisionesDTO, 
                                                                     CatBic catBic, EmisionVO emisionVO, 
                                                                     PaginaVO paginaVO);

	/**
	 * Obtiene una lista de emisiones vigentes por id custodio y el id de la emision
	 * @param idCatBic Id del custodio
	 * @param idCuentaNombrada Id de la cuenta nombrada
	 * @param idEmision Id de la emision
	 * @param estadoEmision Estado de la emision
	 * @param idSicEmision Id de Sic Emision
	 * @return Lista de Emisiones Vigentes
	 */
	public List<SicEmision> findSicEmisionVigenteByIdEmision(Long idCatBic, Long idCuentaNombrada,
			Long idEmision, String estadoEmision, Long idSicEmision);
	
	public SicEmision findSicEmisionByIdEmision(final Long idEmision);

	/**
	 * Obtiene una entidad SicEmision pero el query no se realiza usando las entidades de hibernate
	 * @param idEmision El id de la emision a buscar.
	 * @return Un objeto SicEmisionVO o null si no se encuentra.
	 */
    public SicEmisionVO findSicEmisionByIdEmisionWithoutEntities(final Long idEmision);

	/**
	 * Obtiene los registros de C_SIC_EMISIONES por el id de la emision relacionada.
	 * @param idEmision Id de la emision relacionada.
	 * @return Lista con los registros.
	 */
	List<SicEmision> findSicEmisionesByIdEmision(Long idEmision);
	
	public void guardaActualizaEmision (SicEmision sicEmision);
	
	public List<SicEmision> findSicEmisionVigenteByIdEmision(Long idCatBic, Long idCuentaNombrada,
	Long idEmision, String estadoEmision, Long idSicEmision, String formaOper, String aplicacion);

	/**
	 * Encuentra una entidad SicEmision basandose en los campos del constraint de la tabla.
	 * @param idCuentaNombrada
	 * @param idEmision
	 * @param idCatbic
	 * @return
	 */
	SicEmision findSicEmisionVigente(final Long idCuentaNombrada, final Long idEmision, final Long idCatbic);

	/**
	 * Obtiene la posicion total de la operacion sic de entrega de cambio de boveda de la tabla T_POSICIONES_CAMBIO_BOVEDA
	 * @param idOperacionSic El id de la operacion de entrega
	 * @return La posicion total
	 */
	Long getPosicionTotalOperacionSicCambioBoveda(Long idOperacionSic);

	/**
	 * Obtiene el id de la cuenta nombrada de la cuenta 5001 de Indeval (12-001)
	 * @return El id de la cuenta nombrada
	 */
	Long getIdCuentaNombrada5001Indeval();

	/**
	 * Obtiene la posicion disponible de la cuenta 5001 de Indeval (12-001), de la tabla T_POSICION_NOMBRADA
	 * @param idCuentaNombrada5001Indeval El id de la cuenta nombrada 5001 de Indeval
	 * @param idBoveda El id de la boveda
	 * @param idEmision El id de la emision
	 * @return La posicion disponible
	 */
	Long getPosicionDisponibleCta5001Indeval(Long idCuentaNombrada5001Indeval, Long idBoveda, Long idEmision);

}
