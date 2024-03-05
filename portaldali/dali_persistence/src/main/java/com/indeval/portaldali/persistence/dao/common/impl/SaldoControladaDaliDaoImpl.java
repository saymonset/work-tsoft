/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.dao.common.impl;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.util.Assert;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portaldali.persistence.dao.common.SaldoControladaDaliDao;
import com.indeval.portaldali.persistence.model.SaldoControlada;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class SaldoControladaDaliDaoImpl extends BaseDaoHibernateImpl implements
		SaldoControladaDaliDao {
	
	private static final String EFEC_CON = "EFEC_CON";
	
	/** Objeto de loggeo */
    private static final Logger logger = LoggerFactory.getLogger(SaldoControladaDaliDaoImpl.class);

	/**
	 * @see com.indeval.portaldali.persistence.dao.common.SaldoNombradaDaliDao#getSaldoNombrada(java.lang.String, java.lang.String)
	 */
	public List getSaldoControlada(final String id, final String folio,
	        final BigInteger idBoveda) {
		
		Assert.isTrue(StringUtils.isNotBlank(id));
		Assert.isTrue(StringUtils.isNotBlank(folio));
		Assert.notNull(idBoveda);

		logger.info("Entrando a SaldoControladaDaliDaoImpl.getSaldoNombrada");
		
		return (List) getHibernateTemplate().execute(new HibernateCallback(){

    		public Object doInHibernate(Session session) throws HibernateException, SQLException {
    			Criteria criteria = session.createCriteria(SaldoControlada.class)
    						.createAlias("cuentaControlada", "ccc")
    						.createAlias("ccc.institucion", "ci")
    						.createAlias("ci.tipoInstitucion", "cti")
    						.createAlias("ccc.tipoCuenta", "ctc")
    						.createAlias("ccc.cuentaEstado", "ce")
                            .createAlias("divisa", "cd")
                            .createAlias("boveda", "b");
    			
    			criteria.add(Restrictions.eq("cti.claveTipoInstitucion", id))
    					.add(Restrictions.eq("ci.folioInstitucion", folio));
    			
    			criteria.add(Restrictions.eq("ctc.claveTipoCuenta", EFEC_CON));
			    
    			criteria.add(Restrictions.eq("b.idBoveda", idBoveda));
    			
    			return criteria.list();
    		}
            
        });

	}

}
