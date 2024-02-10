// Cambio Multidivisas
package com.indeval.portalinternacional.persistence.dao.impl;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portalinternacional.middleware.servicios.modelo.Custodio;
import com.indeval.portalinternacional.middleware.servicios.modelo.HorariosCustodios;
import com.indeval.portalinternacional.persistence.dao.CustodioDao;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustodioDaoImpl extends BaseDaoHibernateImpl implements CustodioDao {
    @Override
    @SuppressWarnings("unchecked")
    public Integer getIdCustodioByIdCatbic(final Long idCatbic) {
        List<Integer> resultados = (List<Integer>) getHibernateTemplate().executeFind(new HibernateCallback(){
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Criteria criteria = session.createCriteria(Custodio.class);

                criteria.add(Restrictions.eq("idCatBic.idCatbic", idCatbic));

                criteria.setProjection(Projections.projectionList()
                        .add(Projections.property("id")));

                return (List<Integer>) criteria.list();
            }
        });

        return resultados.get(0);
    }
}
