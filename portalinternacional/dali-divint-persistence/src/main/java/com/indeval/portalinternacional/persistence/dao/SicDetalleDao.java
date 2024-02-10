/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.persistence.dao;

import java.util.List;

import com.bursatec.persistence.dao.BaseDao;
import com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.servicios.modelo.CatBic;
import com.indeval.portalinternacional.middleware.servicios.modelo.SicDetalle;

/**
 * @author javiles
 *
 */
public interface SicDetalleDao extends BaseDao {

    /**
     * Obtiene la lista de instancias SicDetalle correspondientes 
     * a los {@link AgenteVO} proporcionados
     * 
     * @param agenteVO
     * @return List<SicDetalle>
     */
    public List<SicDetalle> findSicDetalle(AgenteVO[] agenteVO);
    
    /**
     * Obtiene la lista de instancias SicDetalle correspondientes a la
     * cuenta nombrada proporcionada 
     * (Wrapper de findSicDetalle(AgenteVO[] agenteVO))
     * 
     * @param agenteVO
     * @return List<SicDetalle>
     */
    public List<SicDetalle> findSicDetalle(AgenteVO agenteVO);
    
    /**
     * Obtiene la instancia {@link SicDetalle} correspondiente a los {@link AgenteVO} 
     * proporcionados filtrada por depositante.
     * 
     * @param agenteVO
     * @param depositante
     * @return SicDetalle
     */
    public SicDetalle findSicDetalle(AgenteVO[] agenteVO, String depositante);
    
    /**
     * Obtiene la instancia {@link SicDetalle} correspondiente a los {@link AgenteVO} 
     * proporcionados filtrada por depositante.
     * 
     * @param agenteVO
     * @param depositante
     * @return SicDetalle
     */
    public SicDetalle findSicDetalle(Long[] idCatBics, String depositante);
    
    /**
     * @param agenteVO
     * @param depositante
     * @return Integer
     */
    public Integer findDepositantes(AgenteVO[] agenteVO, String depositante);
    
    /**
     * Obtiene la lista de depositantes correspondientes a los custodios proporcionados
     * @param catBics
     * @return List<SicDetalle>
     */
    public List<SicDetalle> findDepositantes(List<CatBic> catBics);

    /**
     * Obtiene la lista de depositantes correspondientes a los custodios proporcionados
     * @param catBic
     * @param todos
     * @return List<SicDetalle>
     */
    public List<SicDetalle> findDepositantes(CatBic catBic, Boolean todos);

	/**
	 * Obtiene las lista de depositantes correspondientes a los criterios proporcionados
	 * @param catBic
	 * @param bicDepLiq
	 * @param idDepLiq
	 * @param depLiq
	 * @param paginaVO
	 * @return PaginaVO
	 */
	public PaginaVO findDepositantes(final CatBic catBic, final String bicDepLiq, final String idDepLiq, final String depLiq, final PaginaVO paginaVO);

	/**
	 * Obtiene un objeto SicDetalle en base a la descripcion del campo DEP_LIQ y que este vigente.
	 * @param depLiq
	 * @return
	 */
	public SicDetalle findByDepLiqVigente(String depLiq);

	/**
	 * Obtiene todos las descripciones DEP_LIQ de SicDetalle para llenar el combo de Depositante/Liquidador.
	 * @return El listado de Cadenas
	 */
	List<String> findDepositantesLiquidadores();

}
