/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.dao.protocolofinanciero;

import java.util.List;

import com.indeval.portaldali.persistence.vo.BitacoraMatchParamsPersistence;



/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public interface BitacoraMatchDao {

    /**
     * Retorna una lista con los datos de las tablas de Bitacora del Match
     * @param params
     * @return
     */
    List getRegistrosBitacoraMatch(BitacoraMatchParamsPersistence params);
    
}
