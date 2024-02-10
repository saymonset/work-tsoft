/**
 * 
 */
package com.indeval.portalinternacional.persistence.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.Field3W8BEN;
import com.indeval.portalinternacional.middleware.servicios.modelo.Field3W8IMY;
import com.indeval.portalinternacional.middleware.servicios.modelo.Field3W9;
import com.indeval.portalinternacional.persistence.dao.CamposFormatosDao;

/**
 * @author ribarra
 *
 */
@SuppressWarnings("unchecked")
public class CamposFormatosDaoImpl extends BaseDaoHibernateImpl implements
		CamposFormatosDao, Constantes {

	/**
	 * @see com.indeval.portalinternacional.persistence.dao.CamposFormatosDao#getField3W8BEN()
	 */
	public List<Field3W8BEN> getField3W8BEN() {
		DetachedCriteria criteria = DetachedCriteria.forClass(Field3W8BEN.class);
		criteria.addOrder(Order.asc("idCampo"));
		return (List<Field3W8BEN>)getHibernateTemplate().findByCriteria(criteria);
	}

	/**
	 * @see com.indeval.portalinternacional.persistence.dao.CamposFormatosDao#getField3W8IMY()
	 */
	public List<Field3W8IMY> getField3W8IMY() {
		DetachedCriteria criteria = DetachedCriteria.forClass(Field3W8IMY.class);
		criteria.addOrder(Order.asc("idCampo"));
		return (List<Field3W8IMY>)getHibernateTemplate().findByCriteria(criteria);
	}

	/**
	 * @see com.indeval.portalinternacional.persistence.dao.CamposFormatosDao#getField3W9()
	 */
	public List<Field3W9> getField3W9() {
		DetachedCriteria criteria = DetachedCriteria.forClass(Field3W9.class);
		criteria.addOrder(Order.asc("idCampo"));
		return (List<Field3W9>)getHibernateTemplate().findByCriteria(criteria);
	}

}
