/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.dao.common.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portaldali.persistence.dao.common.DualDaliDao;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class DualDaliDaoImpl extends BaseDaoHibernateImpl implements DualDaliDao {

    /** Log de clase. */
    private static final Logger logger = LoggerFactory.getLogger(DualDaliDaoImpl.class);
    
    /** Definicion de la variable para queries JDBC */
    private JdbcTemplate jdbcTemplate;
    
    private final static String QUERY = "Select sysdate from DUAL";

    /**
     * @see com.indeval.portaldali.persistence.dao.common.DualDaliDao#getFechaActual()
     */
    public Date getFechaActual() {
        
        logger.debug("Entrando a DualDaliDaoImpl.fechaActual()");
        
        /* Ejecuta el query y regresa la lista resultante */
        return (Date) jdbcTemplate.queryForObject(QUERY, Date.class);
        
    }

    /**
     * @return the jdbcTemplate
     */
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    /**
     * @param jdbcTemplate the jdbcTemplate to set
     */
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

}
