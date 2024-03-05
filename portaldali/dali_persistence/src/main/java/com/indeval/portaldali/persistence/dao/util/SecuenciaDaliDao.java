/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.dao.util;

import java.math.BigInteger;
import java.sql.SQLException;

import com.bursatec.persistence.dao.BaseDao;

/**
 * Dao encargado de obtener el consecutivo de la secuencia
 * 
 * @author cmsanchez
 *
 */
public interface SecuenciaDaliDao extends BaseDao {
    
    /**
     * M&eacute;todo encargado de obtener el consecutivo de la secuencia
     * @param nombreSecuencia Secuencia de la cual se obtendra el consecutivo
     * @return Consecutivo de la secuencia
     * @throws SQLException 
     */
    BigInteger getConsecutivo(String nombreSecuencia) throws SQLException;

}
