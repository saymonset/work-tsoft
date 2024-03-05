/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * InstitucionDaliDAOImpl.java
 * 04/03/2008
 */
package com.indeval.portaldali.persistence.dao.common.impl;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.indeval.portaldali.middleware.dto.InstitucionDTO;
import com.indeval.portaldali.middleware.dto.util.DTOAssembler;
import com.indeval.portaldali.middleware.services.common.constants.DaliConstants;
import com.indeval.portaldali.persistence.dao.common.InstitucionDaliDAO;
import com.indeval.portaldali.persistence.model.Institucion;

/**
 * Implementa los métodos de consulta definidos por la interfaz
 * {@link InstitucionDaliDAO}.
 * 
 * @author Emigdio Hernández
 * 
 */
public class InstitucionDaliDAOImpl extends HibernateDaoSupport implements InstitucionDaliDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.dao.InstitucionDAO#buscarInstituciones()
	 */
	@SuppressWarnings("unchecked")
	public List<InstitucionDTO> buscarInstituciones() {
		Iterator itRes = getHibernateTemplate().find("FROM " + Institucion.class.getName() + " i join fetch i.tipoInstitucion order by i.idInstitucion asc ")
				.iterator();
		List<InstitucionDTO> resultados = new ArrayList<InstitucionDTO>();
		while (itRes.hasNext()) {
			resultados.add(DTOAssembler.crearInstitucionDTO((Institucion) itRes.next()));
		}
		return resultados;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.dao.InstitucionDAO#buscarInstitucionPorId(long)
	 */
	public InstitucionDTO buscarInstitucionPorId(long id) {
		return DTOAssembler.crearInstitucionDTO((Institucion) getHibernateTemplate().get(Institucion.class, new BigInteger(String.valueOf(id))));
	}
	
	/**
	 * Obtiene un Objeto de tipo @link InstitucionDTO} a partir de la clave spei
	 * 
	 * @param casfim
	 * @return
	 */
	public InstitucionDTO buscarInstitucionPorClaveSpei(final String claveSpei){
		return (InstitucionDTO) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery("FROM " + Institucion.class.getName() + " i WHERE i.claveSpei = ? ");
				query.setParameter(0, claveSpei);
				Institucion intitucion = (Institucion)query.uniqueResult();
				InstitucionDTO resultado = null;
				if (intitucion != null) {
					resultado = DTOAssembler.crearInstitucionDTO(intitucion);
				}
				return resultado;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.dao.InstitucionDAO#buscarInstitucionesPorPrefijo(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<InstitucionDTO> buscarInstitucionesPorPrefijo(final String prefijo) {

		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session
						.createQuery("FROM "
								+ Institucion.class.getName()
								+ " i "
								+ " join fetch i.tipoInstitucion "
								+ " WHERE i.tipoInstitucion.claveTipoInstitucion || i.folioInstitucion like ? order by i.tipoInstitucion.claveTipoInstitucion || i.folioInstitucion");
				query.setParameter(0, (prefijo != null ? prefijo.toUpperCase() : "") + "%");
				Iterator itRes = query.list().iterator();
				List<InstitucionDTO> resultados = new ArrayList<InstitucionDTO>();
				while (itRes.hasNext()) {
					resultados.add(DTOAssembler.crearInstitucionDTO((Institucion) itRes.next()));
				}
				return resultados;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.dao.InstitucionDAO#buscarOrigenPorPrefijo(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<String> buscarOrigenPorPrefijo(String prefijo) {

		if(prefijo.contains("%%")){
			prefijo=prefijo.replace("%", "*");
		}
			
		this.getHibernateTemplate().setMaxResults(DaliConstants.CANTIDAD_MAXIMA_RESULTADOS_SUGGESTION_BOX);
		List<String> origenes = this
				.getHibernateTemplate()
				.find(
						"SELECT DISTINCT(i.origen) FROM "
								+ Institucion.class.getName()
								+ " i WHERE i.origen LIKE ? ORDER BY i.origen",
						prefijo + "%");
		List<String> resultado = new ArrayList<String>();
		for (String o : origenes) {
			resultado.add(o);
		}
		
		return resultado;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.dao.InstitucionDAO#buscarInstitucionPorNombreCorto(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public InstitucionDTO buscarInstitucionPorNombreCorto(final String nombre) {

		return (InstitucionDTO) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery("FROM " + Institucion.class.getName() + " i " + " join fetch i.tipoInstitucion "
						+ " WHERE i.nombreCorto = ? ");
				query.setParameter(0, nombre);
				Iterator itRes = query.list().iterator();
				InstitucionDTO resultado = null;
				if (itRes.hasNext()) {
					resultado = DTOAssembler.crearInstitucionDTO((Institucion) itRes.next());
				}
				return resultado;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.persistence.dao.common.InstitucionDaliDAO#buscarInstitucionPorClaveYFolio(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public InstitucionDTO buscarInstitucionPorClaveYFolio(final String claveFolio) {

		return (InstitucionDTO) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery("FROM " + Institucion.class.getName() + " i " + " join fetch i.tipoInstitucion "
						+ " WHERE i.tipoInstitucion.claveTipoInstitucion || i.folioInstitucion  = ? ");
				query.setParameter(0, claveFolio);
				Iterator itRes = query.list().iterator();
				InstitucionDTO resultado = null;
				if (itRes.hasNext()) {
					resultado = DTOAssembler.crearInstitucionDTO((Institucion) itRes.next());
				}
				return resultado;
			}
		});
	}

	public InstitucionDTO buscarInstitucionPorCasfim(final String casfim) {
		
		return (InstitucionDTO) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery("FROM " + Institucion.class.getName() + " i " 
						+ " join fetch i.tipoInstitucion "
						+ " WHERE i.claveCasfim = ? ");
				query.setParameter(0, casfim);
				Iterator itRes = query.list().iterator();
				InstitucionDTO resultado = null;
				if (itRes.hasNext()) {
					resultado = DTOAssembler.crearInstitucionDTO((Institucion) itRes.next());
				}
				return resultado;
			}
		});
	
	}
}
