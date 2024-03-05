package com.indeval.portaldali.persistence.dao.common.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.indeval.portaldali.middleware.dto.TipoInstitucionDTO;
import com.indeval.portaldali.middleware.dto.util.DTOAssembler;
import com.indeval.portaldali.persistence.dao.common.TipoInstitucionDaliDAO;
import com.indeval.portaldali.persistence.model.TipoInstitucion;

public class TipoInstitucionDaliDAOImpl extends HibernateDaoSupport implements TipoInstitucionDaliDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.dao.TipoInstitucionDAO#buscarTipoInstitucionPorPrefijo()
	 */
	@SuppressWarnings("unchecked")
	public List<TipoInstitucionDTO> buscarTipoInstitucionPorPrefijo(final String prefijo) {

		return (List<TipoInstitucionDTO>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(
						"FROM " + TipoInstitucion.class.getName() + " ti " + " WHERE ti.claveTipoInstitucion like ? order by ti.claveTipoInstitucion");
				query.setParameter(0, (prefijo != null ? prefijo.toUpperCase() : "") + "%");
				Iterator itRes = query.list().iterator();
				List<TipoInstitucionDTO> resultados = new ArrayList<TipoInstitucionDTO>();
				while (itRes.hasNext()) {
					resultados.add(DTOAssembler.crearTipoInstitucionDTO((TipoInstitucion) itRes.next()));
				}
				return resultados;
			}
		});
	}

}
