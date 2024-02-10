package com.indeval.portalinternacional.persistence.dao.impl;

import java.sql.SQLException;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portalinternacional.middleware.servicios.modelo.adp.AdpCustodioPorcentaje;
import com.indeval.portalinternacional.persistence.dao.AdpCustodioPorcentajeDao;

public class AdpCustodioPorcentajeDaoImpl extends BaseDaoHibernateImpl implements AdpCustodioPorcentajeDao {

	/** Log de clase */
	private static final Logger log = LoggerFactory.getLogger(ClaveAdpDaoImpl.class);
	
	@Override
	public Integer getPorcentaje(final Long claveAdp, final Long cuentaNombrada) {
		try {
			log.info("Entrando a getPorcentaje()");
			AdpCustodioPorcentaje adpCustodio = (AdpCustodioPorcentaje) getHibernateTemplate().execute(new HibernateCallback() {
				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					Criteria criteria = session.createCriteria(AdpCustodioPorcentaje.class, "adpCustodioPorcentaje");
					criteria.add(Restrictions.eq("adpCustodioPorcentaje.claveAdp.idClaveAdp", claveAdp));
					criteria.add(Restrictions.eq("adpCustodioPorcentaje.idCuentaNombrada", cuentaNombrada));
					return criteria.uniqueResult();
				}
			});
			return adpCustodio.getPorcentaje();
		}catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

}