package com.indeval.portalinternacional.persistence.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portalinternacional.middleware.servicios.modelo.adp.ClaveAdp;
import com.indeval.portalinternacional.persistence.dao.ClaveAdpDao;

public class ClaveAdpDaoImpl extends BaseDaoHibernateImpl implements ClaveAdpDao{

	/** Log de clase */
	private static final Logger log = LoggerFactory.getLogger(ClaveAdpDaoImpl.class);
	
	@Override
	public ClaveAdp findClaveAdpByClaveAdp(final String claveAdp) {
		log.info("Entrando a findClaveAdpByClaveAdp()");
		final StringBuilder sb = new StringBuilder();
		final List<Object> paramsSQL = new ArrayList<Object>();
		final List<Type> tipos = new ArrayList<Type>();
		
		sb.append(" FROM " + ClaveAdp.class.getName() + " clave ");
		sb.append(" WHERE clave.claveAdp = ?");
		
		paramsSQL.add(claveAdp);
		tipos.add(new StringType());
		
		return (ClaveAdp) this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(final Session session) throws HibernateException,
			SQLException {
				Query query = session.createQuery(sb.toString());
                query.setParameters(paramsSQL.toArray(new Object[0]), tipos.toArray(new Type[0]));
                return query.uniqueResult();
			}
		});
	}
}