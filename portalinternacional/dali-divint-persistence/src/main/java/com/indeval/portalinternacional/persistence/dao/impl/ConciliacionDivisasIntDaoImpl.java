package com.indeval.portalinternacional.persistence.dao.impl;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portalinternacional.middleware.servicios.dto.ConciliacionDivisasIntDTO;
import com.indeval.portalinternacional.middleware.servicios.dto.DivisaDTO;
import com.indeval.portalinternacional.middleware.servicios.dto.HorariosCustodiosDto;
import com.indeval.portalinternacional.middleware.servicios.modelo.ConciliacionDivisasInt;
import com.indeval.portalinternacional.middleware.servicios.modelo.DivisaBoveda;
import com.indeval.portalinternacional.middleware.servicios.modelo.DivisaInt;
import com.indeval.portalinternacional.middleware.servicios.modelo.HorariosCustodios;
import com.indeval.portalinternacional.persistence.dao.ConciliacionDivisasIntDao;
import com.indeval.portalinternacional.persistence.util.DTOAssembler;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.*;

public class ConciliacionDivisasIntDaoImpl extends BaseDaoHibernateImpl implements ConciliacionDivisasIntDao {

    @Override
    @SuppressWarnings("unchecked")
    public List<ConciliacionDivisasIntDTO> getAllByIdBovedaAndIdDivisa(final Integer idBoveda, final Integer idDivisa) {
        return (List<ConciliacionDivisasIntDTO>) getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Criteria criteria = session.createCriteria(ConciliacionDivisasInt.class);

                if (idDivisa != null && idBoveda != null) {
                    criteria.add(Restrictions.eq("idDivisa", idDivisa));
                    criteria.add(Restrictions.eq("idBoveda", idBoveda));
                    criteria.add(Restrictions.eq("fecha", getFechaSinTiempo()));

                    Iterator conciliacionDivisasInt = criteria.list().iterator();
                    List<ConciliacionDivisasIntDTO> result = new ArrayList<ConciliacionDivisasIntDTO>();
                    ConciliacionDivisasIntDTO conciliacionDivisasIntDTO = null;
                    while (conciliacionDivisasInt.hasNext()) {
                        ConciliacionDivisasInt conciliacionDivisasInt1 = (ConciliacionDivisasInt) conciliacionDivisasInt.next();
                        conciliacionDivisasIntDTO = DTOAssembler.crearConciliacionDivisasIntDTO(conciliacionDivisasInt1);
                        result.add(conciliacionDivisasIntDTO);
                    }

                    return result;
                }
                return null;
            }
        });
    }

    @SuppressWarnings("unchecked")
    public List<ConciliacionDivisasIntDTO> getAllByIdDivisa(final Integer idDivisa) {
        return (List<ConciliacionDivisasIntDTO>) getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Criteria criteria = session.createCriteria(ConciliacionDivisasInt.class);

                if (idDivisa != null) {
                    criteria.add(Restrictions.eq("idDivisa", idDivisa));
                    criteria.add(Restrictions.eq("fecha", getFechaSinTiempo()));

                    Iterator conciliacionDivisasInt = criteria.list().iterator();
                    List<ConciliacionDivisasIntDTO> result = new ArrayList<ConciliacionDivisasIntDTO>();
                    ConciliacionDivisasIntDTO conciliacionDivisasIntDTO = null;
                    while (conciliacionDivisasInt.hasNext()) {
                        ConciliacionDivisasInt conciliacionDivisasInt1 = (ConciliacionDivisasInt) conciliacionDivisasInt.next();
                        conciliacionDivisasIntDTO = DTOAssembler.crearConciliacionDivisasIntDTO(conciliacionDivisasInt1);
                        result.add(conciliacionDivisasIntDTO);
                    }

                    return result;
                }
                return null;
            }
        });
    }

    private Date getFechaSinTiempo(){
        Date fechaConTiempo = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaConTiempo);

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }
}
