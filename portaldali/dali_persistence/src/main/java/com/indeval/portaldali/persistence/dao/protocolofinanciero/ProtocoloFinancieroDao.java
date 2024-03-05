/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.dao.protocolofinanciero;

import java.math.BigInteger;
import java.util.List;

import com.bursatec.persistence.dao.BaseDao;
import com.indeval.portaldali.persistence.vo.AgentePK;
import com.indeval.portaldali.persistence.vo.BitacoraMatchParamsPersistence;


/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public interface ProtocoloFinancieroDao extends BaseDao {

    /**
     *
     * @param params
     * @return List
     */
    List getBitacoraMatch(BitacoraMatchParamsPersistence params);

    /**
     *
     * @param idBitacoraMatch 
     * @param operacion
     * @param agenteFirmado 
     * @return int
     * @deprecated
     * use RegistroInstruccionesMatch2Dao.actualizaEdoInsCancelaMatch2
     */
    int actualizaEdoInsExpira(BigInteger idBitacoraMatch, boolean operacion, AgentePK agenteFirmado);

}
