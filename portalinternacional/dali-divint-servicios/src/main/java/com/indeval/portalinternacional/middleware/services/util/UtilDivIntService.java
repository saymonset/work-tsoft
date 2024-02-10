/**
 * 
 */
package com.indeval.portalinternacional.middleware.services.util;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.EmisionVO;
import com.indeval.portaldali.persistence.modelo.CuentaNombrada;

/**
 * @author javiles
 *
 */
public interface UtilDivIntService {

    /**
     * Obtiene la cuenta receptora de las operaciones SIC (0030, 0031) a partir
     * del id, folio y tipo de cuenta (SICP o SICT) de la institucion
     * 
     * @param cuentaNombrada
     * @return String
     * @throws BusinessException
     */
    String obtieneCuentaReceptora(CuentaNombrada cuentaNombrada) throws BusinessException;
    
    /**
     * Obtiene la cuenta receptora de las operaciones SIC (0030, 0031) a partir
     * del id, folio y tipo de cuenta (SICP o SICT) de la institucion - con un AgenteVO
     * 
     * @param agenteVO
     * @return String
     * @throws BusinessException
     */
    String obtieneCuentaReceptora(AgenteVO agenteVO) throws BusinessException;
    
    /**
     * Obtiene el arreglo de AgenteVO correspondiente a una emision 
     * @param emisionVO
     * @return AgenteVO[]
     */
    AgenteVO[] obtieneAgentesSIC(EmisionVO emisionVO);
    
    /**
     * Obtiene el arreglo de id de catbic correspondiente a una emision 
     * @param emisionVO
     * @return Long[]
     */
    Long[] obtieneCatBics(EmisionVO emisionVO);
    
    Long getCuentaNombradaOfCustodio(Long idEmision);
    
}
