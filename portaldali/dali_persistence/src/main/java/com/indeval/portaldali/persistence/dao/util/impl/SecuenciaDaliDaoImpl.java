/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.dao.util.impl;

import java.lang.reflect.Constructor;
import java.math.BigInteger;
import java.sql.SQLException;

import org.hibernate.dialect.Dialect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.JdbcTemplate;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portaldali.persistence.dao.util.SecuenciaDaliDao;

/**
 * 
 * @author cmsanchez
 * 
 */
public class SecuenciaDaliDaoImpl extends BaseDaoHibernateImpl implements SecuenciaDaliDao, InitializingBean{

    /** Log de clase. */
    private static final Logger logger = LoggerFactory.getLogger(SecuenciaDaliDaoImpl.class);

    /** Acceso a JdbcTemplate */
    private JdbcTemplate jdbcTemplateOracle;
    
    private String hibernateDialect;
    
    /**
     * @see com.indeval.persistence.SecuenciaDaliDao.util.dao.SecuenciaDao#getConsecutivo(java.lang.String)
     */
    public BigInteger getConsecutivo(String nombreSecuencia) throws SQLException {
        logger.info("Entrando al metodo getConsecutivo()");
        
        Dialect dialect=null;
        try {
        	Class clazz = Class.forName(this.hibernateDialect);
    		Constructor<Dialect> constructor = clazz.getConstructor(new Class[] {});
    		dialect = constructor.newInstance(new Object[] {});
    		
    		Long nextVal = this.jdbcTemplateOracle.queryForLong(dialect.getSequenceNextValString(nombreSecuencia), new Object[0]);
    		return BigInteger.valueOf(nextVal);
        }catch(Exception e) {
        	logger.error(e.getMessage(), e);
        	throw new IllegalStateException(e);
        }
    }

    /**
     * @param jdbcTemplateOracle
     */
    public void setJdbcTemplateOracle(JdbcTemplate jdbcTemplateOracle) {
        this.jdbcTemplateOracle = jdbcTemplateOracle;
    }

	public void setHibernateDialect(String hibernateDialect) {
		this.hibernateDialect = hibernateDialect;
	}
}
