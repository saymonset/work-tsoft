/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.persistence.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portalinternacional.middleware.servicios.modelo.TipoBeneficiario;
import com.indeval.portalinternacional.persistence.dao.TipoBeneficiarioDao;

public class TipoBeneficiarioDaoImpl extends BaseDaoHibernateImpl implements TipoBeneficiarioDao {
	/** Log de clase. */
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	public TipoBeneficiario findTipoBeneficiarioByDesc(String description) {
		log.debug("Entrando a TipoBeneficiarioDaoImpl.findTipoBeneficiarioByDesc");
		TipoBeneficiario retorno = null;

		if(StringUtils.isNotBlank(description)) {
			DetachedCriteria criteria = DetachedCriteria.forClass(TipoBeneficiario.class);
			criteria.add(Restrictions.ilike("descTipoBeneficiario", StringUtils.trim(description) + "%" ));
			List<TipoBeneficiario> lista = (List<TipoBeneficiario>) this.getHibernateTemplate().findByCriteria(criteria);
			if(lista != null && lista.size() == 1) {
				retorno = lista.get(0);
			}
		}

		return retorno;
	}

	/*
	 * (non-Javadoc)
	 * @see com.indeval.portalinternacional.persistence.dao.TipoBeneficiarioDao#getTiposBeneficiario()
	 */
	public List<TipoBeneficiario> getTiposBeneficiario() {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(TipoBeneficiario.class);
        detachedCriteria.addOrder(Order.asc("idTipoBeneficiario"));
        return getHibernateTemplate().findByCriteria(detachedCriteria);
	}

}
