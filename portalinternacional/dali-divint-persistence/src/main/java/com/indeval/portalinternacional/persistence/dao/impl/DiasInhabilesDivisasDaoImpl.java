// Cambio Multidivisas
package com.indeval.portalinternacional.persistence.dao.impl;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portalinternacional.middleware.servicios.modelo.DiasInhabilesDivisas;
import com.indeval.portalinternacional.persistence.dao.DiasInhabilesDivisasDao;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

import java.util.Date;
import java.util.List;

public class DiasInhabilesDivisasDaoImpl extends BaseDaoHibernateImpl implements DiasInhabilesDivisasDao {
    @Override
    @SuppressWarnings("unchecked")
    public List<Date> getDiasInhabilesByIdDivisa(final Long idDivisa) {
        return (List<Date>)getHibernateTemplate().executeFind(new HibernateCallback(){
            public Object doInHibernate(Session session) throws HibernateException {
                Criteria criteria = session.createCriteria(DiasInhabilesDivisas.class);

                criteria.add(Restrictions.eq("idDivisa", idDivisa));

                criteria.setProjection(Projections.projectionList()
                        .add(Projections.property("diaInhabil")));

                List<Date> diasInhabiles = criteria.list();

                return diasInhabiles;
            }
        });
    }
}
