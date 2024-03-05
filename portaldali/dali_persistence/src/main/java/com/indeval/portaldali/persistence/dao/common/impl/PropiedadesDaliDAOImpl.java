/*
 * Copyright (c) 2019 CMMV Tecnologia. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.dao.common.impl;

import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.indeval.portaldali.middleware.dto.PropiedadesDaliDTO;
import com.indeval.portaldali.middleware.dto.util.DTOAssembler;
import com.indeval.portaldali.persistence.dao.common.PropiedadesDaliDAO;
import com.indeval.portaldali.persistence.model.PropiedadesDali;

/**
 * Implementa interface para acceso a datos de C_PROPIEDADES_DALI
 * 
 * @author amoralesm
 *
 */
public class PropiedadesDaliDAOImpl extends HibernateDaoSupport implements PropiedadesDaliDAO {

	
	@Override
	public PropiedadesDaliDTO obtenerParametroPorAmbienteYNombre(final PropiedadesDaliDTO criterios) {

		return (PropiedadesDaliDTO) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery("FROM " + PropiedadesDali.class.getName() + " pd "
						+ "WHERE pd.propiedadesDaliPK.environment = ? "
						+ "AND pd.propiedadesDaliPK.key = ?"
						);
				query.setParameter(0, criterios.getEnvironment());
				query.setParameter(1, criterios.getKey());
				
				PropiedadesDali propiedadesDali = (PropiedadesDali) query.uniqueResult();
				PropiedadesDaliDTO resultado = null;
				if (propiedadesDali != null) {
					resultado = DTOAssembler.crearPropiedadesDaliDTO(propiedadesDali);
				}
				return resultado;
			}
		});
		
	}

}
