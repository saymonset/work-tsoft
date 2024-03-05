/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.dao.common.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.util.Assert;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portaldali.persistence.dao.common.InstrumentoDaliDao;
import com.indeval.portaldali.persistence.model.Instrumento;
import com.indeval.portaldali.persistence.util.Constantes;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class InstrumentoDaliDaoImpl extends BaseDaoHibernateImpl implements
        InstrumentoDaliDao, Constantes {

    /**
     * Log de clase.
     */
    private static final Logger logger = LoggerFactory.getLogger(InstrumentoDaliDaoImpl.class);
    
    /**
     * @see com.indeval.portaldali.persistence.dao.common.InstrumentoDaliDao#getListaInstrumentos()
     */
    @SuppressWarnings("unchecked")
	public List getListaInstrumentos() { 
        return (List) getHibernateTemplate().execute(new HibernateCallback(){

            public Object doInHibernate(Session session) 
                    throws HibernateException, SQLException {
                Criteria criteria = session.createCriteria(Instrumento.class);
                return criteria.list();
            }
            
        });
    }

    /**
     * @see com.indeval.portaldali.persistence.dao.common.InstrumentoDaliDao#getInstrumento(java.lang.String)
     */
    public Instrumento getInstrumento(String tv) {
        
        logger.info("Entrando a InstrumentoDaliDaoImpl.getCInstrumento()");
        
        return getInstrumento(tv, null);
    }

    /**
     * @see com.indeval.portaldali.persistence.dao.common.InstrumentoDaliDao#getInstrumento(java.lang.String, java.lang.String)
     */
    public Instrumento getInstrumento(final String tv, final String mercado) {
        
        logger.info("Entrando a InstrumentoDaliDaoImpl.getCInstrumento()");
        
        Assert.isTrue(StringUtils.isNotBlank(tv), "El tipo de valor es un dato obligatorio");
        
        final Instrumento cinstrumento = new Instrumento();

        return (Instrumento) this.getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(Session session)
                throws HibernateException, SQLException {

                    Example example = Example.create(cinstrumento);
                    Criteria criteria = session.createCriteria(Instrumento.class);
                    
                    criteria.add(example);
                    criteria.add(Restrictions.eq("claveTipoValor", tv));
                    
                    if(StringUtils.isNotBlank(mercado)){
                        criteria.createAlias("mercado", "m");
                        if(MERCADO_CAPITALES.equalsIgnoreCase(mercado.trim())) {
                            criteria.add(Restrictions.eq("m.clave", MERCADO_CAPITALES)); 
                        }
                        else {
                            criteria.add(Restrictions.in("m.clave", 
                                    new Object[] {PAPEL_BANCARIO, PAPEL_GUBERNAMENTAL}));
                        }

                    }
                    return criteria.uniqueResult();

            }
        });
    }

}
