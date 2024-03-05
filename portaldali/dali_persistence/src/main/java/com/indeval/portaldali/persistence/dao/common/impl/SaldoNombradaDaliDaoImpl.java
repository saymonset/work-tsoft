/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.dao.common.impl;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.BigIntegerType;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.util.Assert;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portaldali.persistence.dao.common.SaldoNombradaDaliDao;
import com.indeval.portaldali.persistence.model.SaldoNombrada;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class SaldoNombradaDaliDaoImpl extends BaseDaoHibernateImpl implements
		SaldoNombradaDaliDao {
	
	private static final String EFEC_NOM = "EFEC_NOM";
	private static final String CUENTA_EFECTIVO = "2000";
	
	/** Objeto de loggeo */
    private static final Logger logger = LoggerFactory.getLogger(SaldoNombradaDaliDaoImpl.class);

	/**
	 * @see com.indeval.portaldali.persistence.dao.common.SaldoNombradaDaliDao#getSaldoNombrada(java.lang.String, java.lang.String)
	 */
	public List getSaldoNombrada(final String id, final String folio, 
	        final BigInteger idBoveda) {
		
		Assert.isTrue(StringUtils.isNotBlank(id));
		Assert.isTrue(StringUtils.isNotBlank(folio));
		Assert.notNull(idBoveda);

		logger.info("Entrando a SaldoNombradaDaliDaoImpl.getSaldoNombrada");
		
		return (List) getHibernateTemplate().execute(new HibernateCallback(){

    		public Object doInHibernate(Session session) throws HibernateException, SQLException {
    			Criteria criteria = session.createCriteria(SaldoNombrada.class)
    						.createAlias("cuentaNombrada", "ccn")
    						.createAlias("ccn.institucion", "ci")
    						.createAlias("ci.tipoInstitucion", "cti")
    						.createAlias("ccn.tipoCuenta", "ctc")
    						//.createAlias("ccn.cuentaEstado", "ce")
                            .createAlias("divisa", "cd")
    			            .createAlias("boveda", "b");
    			
    			criteria.add(Restrictions.eq("cti.claveTipoInstitucion", id))
    					.add(Restrictions.eq("ci.folioInstitucion", folio));
    			
    			criteria.add(Restrictions.eq("ctc.claveTipoCuenta", EFEC_NOM));
    			
    			criteria.add(Restrictions.eq("b.idBoveda", idBoveda));
			    
    			return criteria.list();
    		}
            
        });

	}
	
	@SuppressWarnings("unchecked")
	public List getSaldoNombradaDivisa(final String id, final String folio,
			final BigInteger idBoveda, final BigInteger idDivisa) {
		logger.info("Datos enviados: [" + id + folio + "-" + idBoveda + "-" + idDivisa + "]");
		if( validaAgente(id, folio) && 
				idBoveda != null && 
				idBoveda.intValue() > 0 &&
				idDivisa != null &&
				idDivisa.intValue() > 0 ) {
			List retorno = (List)getHibernateTemplate().execute(new HibernateCallback() {
				
				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					StringBuffer sb = new StringBuffer("");
					List<Object> params = new ArrayList<Object>();
					List<Type> types = new ArrayList<Type>();
					
					sb.append("SELECT sn ");
					sb.append("FROM " + SaldoNombrada.class.getName() + " sn ");
					sb.append("WHERE sn.cuentaNombrada.institucion.tipoInstitucion.claveTipoInstitucion = ? ");
					sb.append("		AND sn.cuentaNombrada.institucion.folioInstitucion = ? ");
					sb.append("		AND sn.boveda.idBoveda = ? ");
					sb.append("		AND sn.divisa.idDivisa = ? ");
					sb.append("		AND sn.cuentaNombrada.cuenta = ?");
					params.add( id );
					types.add( new StringType() );
					params.add( folio );
					types.add( new StringType() );
					params.add( idBoveda );
					types.add( new BigIntegerType() );
					params.add( idDivisa );
					types.add( new BigIntegerType() );
					params.add( CUENTA_EFECTIVO );
					types.add( new StringType() );
					
					Query query = session.createQuery(sb.toString());
                    return  query.setParameters(params.toArray(), types.toArray(new Type[]{})).list();
				}
				
			});
			return retorno;
		}
		return null;
	}

	private boolean validaAgente(String id, String folio) {
		if( StringUtils.isNotBlank(id) &&
				id.length() == 2 &&
				StringUtils.isNumeric(id) &&
				StringUtils.isNotBlank(folio) &&
				folio.length() == 3 &&
				StringUtils.isNumeric(folio)) {
			return true;
		}
		return false;
	}

}
