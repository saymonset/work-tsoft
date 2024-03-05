package com.indeval.portaldali.persistence.dao.common.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.indeval.portaldali.persistence.dao.common.DepositanteValidoBanxicoDao;
import com.indeval.portaldali.persistence.model.DepositanteValidoBanxico;

public class DepositanteValidoBanxicoDaoImpl extends HibernateDaoSupport implements DepositanteValidoBanxicoDao {

	@SuppressWarnings("unchecked")
	public boolean isDepositanteValidoBanxico(final Long idInstitucion) {
		List<DepositanteValidoBanxico> depositantesValidosParaBanxico = getHibernateTemplate().executeFind(
			new HibernateCallback() {
				public Object doInHibernate(Session session) throws HibernateException {
					Query query = session.getNamedQuery("DepositanteValidoBanxico.getDepositantesValidosParaBanxico");
					query.setLong("idInstitucion", idInstitucion);
					return query.list();
				}
			}
		);

		return !depositantesValidosParaBanxico.isEmpty();
	}
}