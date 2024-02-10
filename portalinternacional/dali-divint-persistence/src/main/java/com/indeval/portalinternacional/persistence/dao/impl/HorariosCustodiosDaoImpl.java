// Cambio Multidivisas
package com.indeval.portalinternacional.persistence.dao.impl;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portalinternacional.middleware.servicios.dto.HorariosCustodiosDto;
import com.indeval.portalinternacional.middleware.servicios.modelo.HorariosCustodios;
import com.indeval.portalinternacional.persistence.dao.HorariosCustodiosDao;
import com.indeval.portalinternacional.persistence.util.DTOAssembler;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

import java.sql.SQLException;
import java.util.*;

public class HorariosCustodiosDaoImpl extends BaseDaoHibernateImpl implements HorariosCustodiosDao {
    @Override
    @SuppressWarnings("unchecked")
    public List<HorariosCustodiosDto> findAllByIdDivisa(final Integer idDivisa) {
        return (List<HorariosCustodiosDto>)getHibernateTemplate().executeFind(new HibernateCallback(){
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Criteria criteria = session.createCriteria(HorariosCustodios.class);

                if (idDivisa != null) {
                    criteria.add(Expression.in("idDivisa", new Object[]{idDivisa, idDivisa}));

                    Iterator horariosCustodiosBO = criteria.list().iterator();
                    List<HorariosCustodiosDto> resultado = new ArrayList<HorariosCustodiosDto>();
                    HorariosCustodiosDto horariosCustodiosDto = null;
                    while (horariosCustodiosBO.hasNext()) {
                        HorariosCustodios horariosCustodios = (HorariosCustodios) horariosCustodiosBO.next();
                        horariosCustodiosDto = DTOAssembler.crearHorariosCustodiosDTO(horariosCustodios);
                        resultado.add(horariosCustodiosDto);
                    }

                    return resultado;
                }
                return null;

            }
        });
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Object[]> getHorarioInicialYHorarioFinalPorIdCustodio(final Integer idCustodio) {
        return (List<Object[]>)getHibernateTemplate().executeFind(new HibernateCallback(){
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Criteria criteria = session.createCriteria(HorariosCustodios.class);

                criteria.add(Restrictions.eq("idCustodio", idCustodio));

                criteria.setProjection(Projections.projectionList()
                        .add(Projections.property("horarioInicial"))
                        .add(Projections.property("horarioFinal")));

                List<Object[]> resultados = criteria.list();

                return resultados;
            }
        });
    }
}
