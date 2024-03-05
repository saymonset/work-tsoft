/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.dao.common;

import com.bursatec.persistence.dao.BaseDao;
import com.indeval.portaldali.middleware.dto.CuentaDTO;
import com.indeval.portaldali.persistence.model.CuentaNombrada;
import com.indeval.portaldali.persistence.vo.AgentePK;
import com.indeval.portaldali.persistence.vo.PageVO;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public interface CuentaNombradaDaliDao extends BaseDao {

    /**
     * @param agentePK
     * @param pageVO
     * @return PageVO
     */
    PageVO getCuentaNombrada(AgentePK agentePK, PageVO pageVO);

    /**
     * Obtiene el detalle de una cuenta nombrada tomando como parámetros el id y folio de 
     * la institución, el número de cuenta, tipo de custodia y naturaleza contable.
     * @param agentePK Objeto con los parámetros de búsqueda.
     * @param tipoCustodia Tipo de custodia
     * @param naturaleza Naturaleza contable
     * @return Dto con el detalle de la cuenta o null si no esta registrada en el sistema.
     */
    CuentaDTO obtenerCuentaNombradaInstitucion(AgentePK agentePK, String tipoCustodia, String naturaleza);
    
    /**
     * Obtiene una cuenta nombrada por el numero de cuenta y el id de la institucion.
     * @param idInstitucion Id de la institucion a la que pertenece la cuenta.
     * @param cuenta Numero de cuenta.
     * @return POJO con la cuenta nombrada o nulo si no se encuentra.
     */
    CuentaNombrada obtenerCuentaNombradaPorIdInstitucionCuenta(Long idInstitucion, String cuenta);
    
}
