/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.persistence.dao.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portaldali.persistence.modelo.Depositos;
import com.indeval.portaldali.persistence.util.DateUtils;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.persistence.dao.DepositosDivIntDao;

/**
 * @author <a href="mailto:ivan.kazakov@2hsoftware.com.mx">Ivan Kazakov</a>
 *
 */
@SuppressWarnings({"unchecked"})
public class DepositosDivIntDaoImpl extends BaseDaoHibernateImpl implements DepositosDivIntDao {

	/**
	 * @see com.indeval.portalinternacional.persistence.dao.DepositosDivIntDao#getDepositosDivInt(java.util.Date)
	 */
	public List<Depositos> getDepositosDivInt(final Long idCuentaNombrada, final Date fechaConsulta) {
		HibernateCallback hibernateCallback = new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(Depositos.class);
				criteria.createAlias("cuentaNombrada","cn");
				criteria.createAlias("cupon", "c");
				criteria.createAlias("c.estadoCupon","ec");
				criteria.add(Restrictions.eq("ec.idEstatusCupon", Constantes.ESTATUS_CUPON_VIGENTE));
				
				if (fechaConsulta != null) {
					Date[] intervaloFechas = DateUtils.preparaIntervaloFechas(fechaConsulta, fechaConsulta);
					criteria.add(Restrictions.between("fechaRegistro", intervaloFechas[0], intervaloFechas[1]));
				}
				if (idCuentaNombrada != null) {
					criteria.add(Restrictions.eq("cn.idCuentaNombrada", idCuentaNombrada));
				}
				return (criteria.list());
			}
		};
		
		return ((List<Depositos>)this.getHibernateTemplate().execute(hibernateCallback));
	}
}
