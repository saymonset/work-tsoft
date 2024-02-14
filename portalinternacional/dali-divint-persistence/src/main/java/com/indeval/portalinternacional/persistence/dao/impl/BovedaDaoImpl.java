/*
 * Copyright (c) 2017 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.persistence.dao.impl;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.indeval.portaldali.persistence.modelo.TipoBoveda;
import com.indeval.portalinternacional.middleware.servicios.dto.BovedaDto;
import com.indeval.portalinternacional.middleware.servicios.dto.DivisaDTO;
import com.indeval.portalinternacional.middleware.servicios.dto.EstadoPaginacionDTO;
import com.indeval.portalinternacional.middleware.servicios.modelo.DivisaBoveda;
import com.indeval.portalinternacional.persistence.util.DTOAssembler;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.Bovedas;
import com.indeval.portalinternacional.persistence.dao.BovedaDao;

/**
 * Implementacion de BovedaDao
 */
public class BovedaDaoImpl extends BaseDaoHibernateImpl implements BovedaDao {

    /**
     * @see com.indeval.portalinternacional.persistence.dao.BovedaDao#findBovedaByNombreCorto(String)
     */
    public Bovedas findBovedaByNombreCorto(String nombreCorto) {
        final StringBuilder sb = new StringBuilder();

        sb.append(" FROM " + Bovedas.class.getName() + " bov ");
        sb.append(" WHERE bov.nombreCorto = '" + nombreCorto + "' ");

        Bovedas boveda = (Bovedas) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createQuery(sb.toString());
                return query.uniqueResult();
            }
        });

        return boveda;
    }

    /**
     * @see com.indeval.portalinternacional.persistence.dao.BovedaDao#findAllBovedasValores()
     */
    @SuppressWarnings("unchecked")
    public List<Bovedas> findAllBovedasValores() {
        final StringBuilder sb = new StringBuilder();

        sb.append(" FROM " + Bovedas.class.getName() + " bov ");
        sb.append(" WHERE bov.idTipoBoveda = " + Constantes.TIPO_BOVEDA_VALORES_INT + " ");
        sb.append(" ORDER BY bov.idBoveda ");

        return (List<Bovedas>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createQuery(sb.toString());
                return query.list();
            }
        });
    }

    /**
     * @see com.indeval.portalinternacional.persistence.dao.BovedaDao#getBovedaByIdCuentaBoveda(Integer)
     */
    public Bovedas getBovedaByIdCuentaBoveda(Integer idCuentaBoveda) {
        final StringBuilder sb = new StringBuilder();

        sb.append(" FROM " + Bovedas.class.getName() + " bov ");
        sb.append(" WHERE bov.idCuentaBoveda = " + idCuentaBoveda + " ");

        return (Bovedas) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createQuery(sb.toString());
                return query.uniqueResult();
            }
        });
    }

// Cambio Multidivisas
    @Override
    @SuppressWarnings("unchecked")
    public List<BigInteger> obtenerBovedasPorDivisa(final DivisaDTO divisaDTO) {
        final StringBuilder sb = new StringBuilder();
        sb.append(" FROM DivisaBoveda d where d.id.idDivisa  = ").append(divisaDTO.getId());

        return (List<BigInteger>) getHibernateTemplate().execute(new HibernateCallback(){
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
              //  Query query = session.createQuery(" FROM " + DivisaBoveda.class.getName() + " d where d.idDivisa  = "+ divisaDTO.getId() +" ");
                Query query = session.createQuery(sb.toString());
                List<DivisaBoveda> consulta = query.list();
                List<BigInteger> resultadosConsulta = new ArrayList<BigInteger>();
                for (DivisaBoveda divisaBoveda : consulta) {
                    resultadosConsulta.add(divisaBoveda.getId().getIdBoveda());
                }
                return resultadosConsulta;
            }
        });
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<BovedaDto> buscarBovedasPorTipoCustodia(final BovedaDto boveda, final EstadoPaginacionDTO estadoPaginacion) {
        return (List<BovedaDto> )getHibernateTemplate().executeFind(new HibernateCallback(){
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Criteria criteria = session.createCriteria(Bovedas.class);

                if (boveda != null && boveda.getTipoBoveda().getClave() != null) {

                    if (boveda.getTipoBoveda().getClave().equals("V")) {
                        criteria.add(Expression.in("idTipoBoveda", new Object[] { TipoBoveda.INTERNACIONAL_VALORES, TipoBoveda.NACIONAL_VALORES })

                        );
                    } else {
                        criteria.add(Expression.in("idTipoBoveda", new Object[] { TipoBoveda.INTERNACIONAL_EFECTIVO, TipoBoveda.INTERNACIONAL_EFECTIVO })

                        );
                    }

                }

                criteria.addOrder(Order.asc("descripcion"));

                if (estadoPaginacion != null) {
                    criteria.setFirstResult((estadoPaginacion.getNumeroPagina() * estadoPaginacion.getRegistrosPorPagina()) - 1);
                    criteria.setMaxResults(estadoPaginacion.getRegistrosPorPagina());
                }

                Iterator bovedasBO = criteria.list().iterator();
                List<BovedaDto> resultado = new ArrayList<BovedaDto>();
                BovedaDto bovedaDTO = null;
                while (bovedasBO.hasNext()) {
                    Bovedas boveda = (Bovedas) bovedasBO.next();
                    bovedaDTO = DTOAssembler.crearBovedasDTO(boveda);
                    resultado.add(bovedaDTO);
                }

                return resultado;
            }
        });
    }

    /**
     * Multidivisas
     * @see com.indeval.portalinternacional.persistence.dao.BovedaDao#findAllBovedasValores()
     */
    @SuppressWarnings("unchecked")
    public List<Bovedas> findAllBovedasEfectivo() {
        final StringBuilder sb = new StringBuilder();

        sb.append(" FROM " + Bovedas.class.getName() + " bov ");
        sb.append(" WHERE bov.idTipoBoveda = " + TipoBoveda.INTERNACIONAL_EFECTIVO + " ");
        sb.append(" ORDER BY bov.idBoveda ");

        return (List<Bovedas>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createQuery(sb.toString());
                return query.list();
            }
        });
    }

    //Fin Cambio Multidivisas
}
