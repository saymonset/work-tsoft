/**
 * Copyright (c) 2017 Bursatec. All Rights Reserved
 */
package com.indeval.portalinternacional.persistence.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Query;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portalinternacional.middleware.servicios.modelo.Emisiones;
import com.indeval.portalinternacional.persistence.dao.EmisionesConsultasDao;

/**
 * DAO de implementaci&oacute;n de EmisionesConsultasDao
 */

public class EmisionesConsultasDaoImpl extends BaseDaoHibernateImpl implements EmisionesConsultasDao {

    private static final int EMISION_LIBERADA = 3;

    /**
     * @see com.indeval.sidv.emisiones.persistence.model.dao.EmisionesConsultasDao#obtenerEmisionLiberada(String, String, String, String)
     */
    @SuppressWarnings("unchecked")
    public Emisiones obtenerEmisionLiberada(String tv, String emisora, String serie, String isin) {
        //Creamos el query
        StringBuffer query = new StringBuffer();
        query.append(" select emi from " + Emisiones.class.getName() + " emi ");
        query.append(" inner join fetch emi.instrumento inst ");
        query.append(" inner join fetch emi.emisora em ");
        query.append(" where inst.claveTipoValor = :tv ");
        query.append(" and em.clavePizarra = :emisora ");
        query.append(" and emi.serie = :serie ");
        query.append(" and emi.isin = :isin ");
        query.append(" and emi.idEstatusEmision = :estadoliberado ");
        //Ejecutamos el query
        Query q = getSession().createQuery(query.toString()).
                setParameter("tv", tv).
                setParameter("emisora", emisora).
                setParameter("serie", serie).
                setParameter("isin", isin).
                setParameter("estadoliberado", EMISION_LIBERADA);
        List<Emisiones> resultados = q.list();
        Emisiones emision = null;
        if(null != resultados && !resultados.isEmpty()) {
            emision = resultados.get(0);          
        }
        return emision;
    }

    /**
     * @see com.indeval.sidv.emisiones.persistence.model.dao.EmisionesConsultasDao#obtenerEmisionLiberada(String, String, String)
     */
    @SuppressWarnings("unchecked")
    public Emisiones obtenerEmisionLiberada(String tv, String emisora, String serie) {
        //Creamos el query
        StringBuffer query = new StringBuffer();
        query.append(" select emi from " + Emisiones.class.getName() + " emi ");
        query.append(" inner join fetch emi.instrumento inst ");
        query.append(" inner join fetch emi.emisora em ");
        query.append(" where inst.claveTipoValor = :tv ");
        query.append(" and em.clavePizarra = :emisora ");
        query.append(" and emi.serie = :serie ");
        query.append(" and emi.idEstatusEmision = :estadoliberado ");
        //Ejecutamos el query
        Query q = getSession().createQuery(query.toString()).
                setParameter("tv", tv).
                setParameter("emisora", emisora).
                setParameter("serie", serie).
                setParameter("estadoliberado", EMISION_LIBERADA);
        List<Emisiones> resultados = q.list();
        Emisiones emision = null;
        if(null != resultados && !resultados.isEmpty()) {
            emision = resultados.get(0);
        }
        return emision;
    }

    /**
     * @see com.indeval.sidv.emisiones.persistence.model.dao.EmisionesConsultasDao#getSaldo(Integer, Integer, Integer, Integer)
     */
    public BigDecimal getSaldo(Integer idEmision, Integer idCuenta, Integer idBoveda, Integer idCuponVigente) throws BusinessException {
        BigDecimal pos = (BigDecimal) getSession().createSQLQuery("SELECT POSICION_DISPONIBLE FROM T_POSICION_NOMBRADA WHERE ID_EMISION =" + idEmision + 
                                                                  " AND ID_CUENTA = " + idCuenta + 
                                                                  " AND ID_BOVEDA = " + idBoveda + 
                                                                  " AND ID_CUPON = " + idCuponVigente).uniqueResult();
        return pos;
    }

    /**
     * @see com.indeval.sidv.emisiones.persistence.model.dao.EmisionesConsultasDao#getSaldoTotal(Integer, Integer, Integer)
     */
    public BigDecimal getSaldoTotal(Integer idEmision, Integer idBoveda, Integer idCuponVigente) throws BusinessException {
        BigDecimal pos = (BigDecimal) getSession().createSQLQuery("SELECT sum(pn.POSICION_DISPONIBLE + pn.POSICION_NO_DISPONIBLE) " +
                                                                  " FROM T_POSICION_NOMBRADA pn, C_CUENTA_NOMBRADA cn, C_TIPO_CUENTA tc " +
                                                                  " WHERE pn.ID_CUENTA = cn.ID_CUENTA_NOMBRADA " + 
                                                                  " and cn.ID_TIPO_CUENTA = tc.ID_TIPO_CUENTA " +
                                                                  " and tc.NATURALEZA_CONTABLE = 'P' " +
                                                                  " and tc.TIPO_CUSTODIA = 'V' " +
                                                                  " and tc.NATURALEZA_PROC_LIQ = 'N' " +
                                                                  " and pn.ID_EMISION = " + idEmision + 
                                                                  " AND pn.ID_BOVEDA = " + idBoveda + 
                                                                  " AND pn.ID_CUPON = " + idCuponVigente).uniqueResult();
        return pos;
    }

    /**
     * @see com.indeval.sidv.emisiones.persistence.model.dao.EmisionesConsultasDao#getSaldoPosicionNoDisponible(Integer, Integer, Integer)
     */
    public BigDecimal getSaldoPosicionNoDisponible(Integer idEmision, Integer idBoveda, Integer idCuponVigente) throws BusinessException {
        BigDecimal pos = (BigDecimal) getSession().createSQLQuery("SELECT sum(pn.POSICION_NO_DISPONIBLE) " +
                " FROM T_POSICION_NOMBRADA pn, C_CUENTA_NOMBRADA cn, C_TIPO_CUENTA tc " +
                " WHERE pn.ID_CUENTA = cn.ID_CUENTA_NOMBRADA " + 
                " and cn.ID_TIPO_CUENTA = tc.ID_TIPO_CUENTA " +
                " and tc.NATURALEZA_CONTABLE = 'P' " +
                " and tc.TIPO_CUSTODIA = 'V' " +
                " and tc.NATURALEZA_PROC_LIQ = 'N' " +
                " and pn.ID_EMISION = " + idEmision + 
                " AND pn.ID_BOVEDA = " + idBoveda + 
                " AND pn.ID_CUPON = " + idCuponVigente).uniqueResult();
        return pos;
    }

    /**
     * @see com.indeval.sidv.emisiones.persistence.model.dao.EmisionesConsultasDao#obtenerEmisionByIsin(String)
     */
    public Emisiones obtenerEmisionByIsin(String isin) {
        StringBuffer query = new StringBuffer();
        query.append(" from " + Emisiones.class.getName() + " emi ");
        query.append(" where emi.isin = :isin ");
        query.append(" and emi.idEstatusEmision = :estadoLiberada ");
        Query q = getSession().createQuery(query.toString()).
                setParameter("isin", isin).
                setParameter("estadoLiberada", EMISION_LIBERADA);
        return (Emisiones) q.uniqueResult();
    }

    /**
     * @see com.indeval.sidv.emisiones.persistence.model.dao.EmisionesConsultasDao#getById(Long)
     */
    public Emisiones getById(Long id) {
        return (Emisiones) getSession().get(Emisiones.class, id);
    }

    /**
     * @see com.indeval.sidv.emisiones.persistence.model.dao.EmisionesConsultasDao#update(Emisiones)
     */
    public void update(Emisiones emision) {
        getSession().update(emision);
        getSession().flush();
    }

}
