package com.indeval.portalinternacional.persistence.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portalinternacional.middleware.servicios.modelo.CuentaCorresponsal;
import com.indeval.portalinternacional.persistence.dao.CuentaCorresponsalDao;

@SuppressWarnings("unchecked")
public class CuentaCorresponsalDaoImpl extends BaseDaoHibernateImpl implements CuentaCorresponsalDao{

	@Override
	public List<CuentaCorresponsal> findCuentaCorresponsalByIdInstitucion(final Long idInstitucion) {
		List<CuentaCorresponsal> obj = (List<CuentaCorresponsal>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = getSession().getNamedQuery("CuentaCorresponsal.findByIdInstitucion");
				query.setParameter("idInstitucion", idInstitucion);
				return query.list();
			}
		});
		return obj;
	}

	@Override
	public List<CuentaCorresponsal> findCuentaCorresponsalByIdDivisa(final Long idDivisa) {
		List<CuentaCorresponsal> obj = (List<CuentaCorresponsal>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = getSession().getNamedQuery("CuentaCorresponsal.findByIdDivisa");
				query.setParameter("idDivisa", idDivisa);
				return query.list();
			}
		});
		return obj;
	}

	@Override
	public List<CuentaCorresponsal> findCuentaCorresponsalByIdDivisaAndIdInstitucion(final Long idDivisa,
			final Long idInstitucion) {
		List<CuentaCorresponsal> obj = (List<CuentaCorresponsal>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = getSession().getNamedQuery("CuentaCorresponsal.findByIdDivisaAndIdInstitucion");
				query.setParameter("idDivisa", idDivisa);
				query.setParameter("idInstitucion", idInstitucion);
				return query.list();
			}
		});
		return obj;		
	}

	@Override
	public List<Long> findIdDivisasByIdInstitucion(final Long idInstitucion) {
		List<Long> obj = (List<Long>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = getSession().getNamedQuery("CuentaCorresponsal.findIdDivisasByIdInstitucion");
				query.setParameter("idInstitucion", idInstitucion);
				return query.list();
			}
		});
		return obj;	
	}

}
