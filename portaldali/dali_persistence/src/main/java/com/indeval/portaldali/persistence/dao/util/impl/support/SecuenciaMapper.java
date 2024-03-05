/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.dao.util.impl.support;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

/**
 * 
 * @author csanchez
 *
 */
public class SecuenciaMapper implements RowMapper {
    
    /** Objeto de loggeo para BitacoraMatchMapper */
    private static final Logger logger = LoggerFactory.getLogger(SecuenciaMapper.class);

    /**
     * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
     */
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        logger.info("Entrando a SecuenciaMapper.mapRow()");
        BigInteger secuencia = null;
        if(rs != null){
            secuencia = new BigInteger(rs.getString("nextval"));
        }
        return secuencia;
    }

}
