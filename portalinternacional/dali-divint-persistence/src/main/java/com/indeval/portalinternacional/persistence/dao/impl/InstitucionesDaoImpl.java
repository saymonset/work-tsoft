/**
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved
 */
package com.indeval.portalinternacional.persistence.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portaldali.persistence.modelo.Institucion;
import com.indeval.portalinternacional.middleware.servicios.dto.InstitucionWebDTO;
import com.indeval.portalinternacional.middleware.servicios.modelo.Instituciones;
import com.indeval.portalinternacional.persistence.dao.InstitucionesDao;
import com.indeval.portalinternacional.persistence.util.DTOAssembler;
/**
 * DAO de implementaci&oacute;n de InstitucionesDao
 */

public class InstitucionesDaoImpl extends BaseDaoHibernateImpl implements InstitucionesDao {

    /**
     * Logger
     */
	private static final Logger LOG = LoggerFactory.getLogger(InstitucionesDaoImpl.class);
    
    /**
     * @see com.indeval.sidv.emisiones.persistence.model.dao.InstitucionesDao#flush()
     */
    public void flush() {
    	getHibernateTemplate().flush();
    }

	/** 
	 * @see com.indeval.sidv.emisiones.persistence.model.dao.InstitucionesDao#getInstitucionByTipoYFolio(java.lang.Integer, java.lang.String)
	 */
	public Instituciones getInstitucionByTipoYFolio(String tipoInstitucion, String folio) {
        LOG.info("####### Entrando a InstitucionesDaoImpl.getInstitucionByTipoYFolio()...");
		Instituciones instituciones = null;
		if (tipoInstitucion != null && folio != null) {
			String[] paramNames = { "idTipoInstitucion", "folio" };
			Object[] paramValues = { Long.valueOf(tipoInstitucion), folio.toUpperCase()};
			List list = getHibernateTemplate().findByNamedQueryAndNamedParam(
					"Instituciones.findByTipoYFolio", paramNames, paramValues);
			if (list != null && !list.isEmpty()) {
				instituciones = (Instituciones) list.iterator().next();
			}
		} else {
			return null;
		}
		return instituciones;
	}

	// Cambio Multidivisas
	@Override
	@SuppressWarnings("unchecked")
	public List<InstitucionWebDTO> buscarInstitucionesPorPrefijo(final String prefijo) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session
						.createQuery("FROM "
								+ Instituciones.class.getName()
								+ " i "
								+ " join fetch i.tipoInstitucion "
								+ " WHERE i.tipoInstitucion.claveTipoInstitucion || i.folio like ? order by i.tipoInstitucion.claveTipoInstitucion || i.folio");
				query.setParameter(0, (prefijo != null ? prefijo.toUpperCase() : "") + "%");
				Iterator itRes = query.list().iterator();
				List<InstitucionWebDTO> resultados = new ArrayList<InstitucionWebDTO>();
				while (itRes.hasNext()) {
					resultados.add(DTOAssembler.crearInstitucionWebDTO((Instituciones) itRes.next()));
				}
				return resultados;
			}
		});
	}

	@Override
	public InstitucionWebDTO buscarInstitucionPorClaveYFolio(final String idFolio) {
		return (InstitucionWebDTO) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery("FROM " + Instituciones.class.getName() + " i " + " join fetch i.tipoInstitucion "
						+ " WHERE i.tipoInstitucion.claveTipoInstitucion || i.folio  = :idFolio " + "OR i.tipoInstitucion || i.folio = :idFolio");
				//query.setParameter(0, idFolio);
				query.setString("idFolio", idFolio);
				Iterator itRes = query.list().iterator();
				InstitucionWebDTO resultado = null;
				if (itRes.hasNext()) {
					resultado = DTOAssembler.crearInstitucionWebDTO((Instituciones) itRes.next());
				}
				return resultado;
			}
		});
	}
	// Fin Cambio Multidivisas
}
