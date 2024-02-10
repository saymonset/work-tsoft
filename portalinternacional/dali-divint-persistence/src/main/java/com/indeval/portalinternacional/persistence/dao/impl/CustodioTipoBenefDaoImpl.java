/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.persistence.dao.impl;

import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portalinternacional.middleware.servicios.modelo.CustodioTipoBenef;
import com.indeval.portalinternacional.middleware.servicios.modelo.HistoricoBeneficiario;
import com.indeval.portalinternacional.persistence.dao.CustodioTipoBenefDao;
/**
 * 
 * @author Oscar Garcia Granados
 *
 */
@SuppressWarnings("unchecked")
public class CustodioTipoBenefDaoImpl extends BaseDaoHibernateImpl implements CustodioTipoBenefDao{

	 /** Log de clase. */
	private static final Logger log = LoggerFactory.getLogger(HistoricoBeneficiario.class);
	
	/**
	 * @see com.indeval.portalinternacional.persistence.dao.CustodioTipoBenefDao#findByIdCatBic(java.lang.Long)
	 */
	public List findByIdCatBic(Long idCuentaNombrada) {
		log.info("Entrando a findByIdCatBic()");
		
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT DISTINCT tb.idTipoBeneficiario, tb.descTipoBeneficiario ");
		sb.append("FROM " + CustodioTipoBenef.class.getName() + " ctb ");
		sb.append("	JOIN ctb.tipoBeneficiario tb ");
		sb.append("WHERE ctb.idCuentaNombrada = :parametro ");
		sb.append("ORDER BY tb.descTipoBeneficiario ");
		
		return getHibernateTemplate().findByNamedParam(sb.toString(),"parametro",idCuentaNombrada);
	}
	
	/**
	 * @see com.indeval.portalinternacional.persistence.dao.CustodioTipoBenefDao#findByIdCatBic(java.lang.Long)
	 */
	public List findByIdCatBic() {
		log.info("Entrando a findByIdCatBic()");
		
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT DISTINCT tb.idTipoBeneficiario, tb.descTipoBeneficiario ");
		sb.append("FROM " + CustodioTipoBenef.class.getName() + " ctb ");
		sb.append("	JOIN ctb.tipoBeneficiario tb ");
		sb.append("ORDER BY tb.descTipoBeneficiario ");
		
		return getHibernateTemplate().find(sb.toString());
	}

	/**
	 * @see com.indeval.portalinternacional.persistence.dao.CustodioTipoBenefDao#findByNameCatBic(java.lang.String)
	 */
	public List<CustodioTipoBenef> findByNameCatBic(String catBic) {
		log.info("Entrando a findByNameCatBic()");
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CustodioTipoBenef.class);
		detachedCriteria.createAlias("catbic", "cb");
		detachedCriteria.add(Restrictions.eq("cb.detalleCustodio", catBic));
		return getHibernateTemplate().findByCriteria(detachedCriteria);
	}

	public String findFormato(Long idCuentaNombrada, Long idTipoBeneficiario) {
		log.info("Entrando a findFormato(): [" +idCuentaNombrada+ "][" +idTipoBeneficiario+ "]");
		
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ctb.formato ");
		sb.append("FROM " + CustodioTipoBenef.class.getName() + " ctb ");
		sb.append("	JOIN ctb.tipoBeneficiario tb ");
		sb.append("WHERE ctb.idCuentaNombrada = :param1 ");
		sb.append("	AND tb.idTipoBeneficiario = :param2 ");
		String nombres[] = {"param1","param2"};
		Object valores[] = {idCuentaNombrada, idTipoBeneficiario};
		List resultados = getHibernateTemplate().findByNamedParam(sb.toString(), nombres, valores);
		
		if( resultados != null && resultados.size() == 1 ) {
			return (String)resultados.get(0);
		} else {
			return null;
		}
	}

	/**
	 * @see CustodioTipoBenefDao#getPorcentajeRetencion(java.lang.Long, java.lang.Long)
	 */
	public Double getPorcentajeRetencion(Long idCuentaNombrada, Long idTipoBeneficiario) {
		DetachedCriteria criteria = DetachedCriteria.forClass(CustodioTipoBenef.class);
		criteria.createAlias("tipoBeneficiario", "tb");
		criteria.setFetchMode("tipoBeneficiario", FetchMode.JOIN);

		criteria.add(Restrictions.eq("idCuentaNombrada", idCuentaNombrada));
		criteria.add(Restrictions.eq("tb.idTipoBeneficiario", idTipoBeneficiario));

		List<CustodioTipoBenef> listaCustodioTipoBenef = this.getHibernateTemplate().findByCriteria(criteria);

		if(listaCustodioTipoBenef != null &&
				listaCustodioTipoBenef.size() == 1) {
			return listaCustodioTipoBenef.get(0).getPorcentajeRetencion();
		} else {
			log.error("Lista nula o mas de un valor para esa combinacion");
		}

		return null;
	}

	/**
	 * @see CustodioTipoBenefDao#getCustodiosTipoBeneficiario()
	 */
	public List<CustodioTipoBenef> getCustodiosTipoBeneficiario() {
		DetachedCriteria criteria = DetachedCriteria.forClass(CustodioTipoBenef.class);
		criteria.setFetchMode("tipoBeneficiario", FetchMode.JOIN);

		return this.getHibernateTemplate().findByCriteria(criteria);
	}

	/*
	 * (non-Javadoc)
	 * @see com.indeval.portalinternacional.persistence.dao.CustodioTipoBenefDao#findCadenasDeFormatoByIdCuentaNombrada(java.lang.Long)
	 */
    public List<String> findCadenasDeFormatoByIdCuentaNombrada(Long idCuentaNombrada) {
        StringBuilder sb = new StringBuilder();

        sb.append("SELECT DISTINCT ctb.idCustodioTipoBenef||'-'||ctb.formato||'-'||ctb.porcentajeRetencion ");
        sb.append("FROM " + CustodioTipoBenef.class.getName() + " ctb ");
        sb.append("WHERE ctb.idCuentaNombrada = :id ");
        sb.append("ORDER BY ctb.idCustodioTipoBenef||'-'||ctb.formato||'-'||ctb.porcentajeRetencion ASC ");

        return (List<String>) getHibernateTemplate().findByNamedParam(sb.toString(), "id", idCuentaNombrada);
    }

    /*
     * (non-Javadoc)
     * @see com.indeval.portalinternacional.persistence.dao.CustodioTipoBenefDao#findCustodioTipoBenefByIdCuentaNombrada(java.lang.Long)
     */
    public List<CustodioTipoBenef> findCustodioTipoBenefByIdCuentaNombrada(Long idCuentaNombrada) {
        StringBuilder sb = new StringBuilder();

        sb.append("FROM " + CustodioTipoBenef.class.getName() + " ctb ");
        sb.append("WHERE ctb.idCuentaNombrada = :id ");
        sb.append("ORDER BY ctb.idCustodioTipoBenef ASC ");

        return (List<CustodioTipoBenef>) getHibernateTemplate().findByNamedParam(sb.toString(), "id", idCuentaNombrada);
    }

}
