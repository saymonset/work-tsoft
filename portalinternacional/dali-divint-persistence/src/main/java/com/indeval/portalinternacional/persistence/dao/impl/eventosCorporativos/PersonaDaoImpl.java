/**
 * Bursatec - Portal Internacional
 * Copyrigth (c) 2015 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.persistence.dao.impl.eventosCorporativos;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;

import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.ListaDistribucion;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.ListaPersona;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.Persona;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.PersonaVista;
import com.indeval.portalinternacional.persistence.dao.eventosCorporativos.PersonaDao;

/**
 * Implementación de la Interfaz de la entidad Persona.
 * 
 * @author amoralesm
 *
 */
public class PersonaDaoImpl extends BaseDaoHibernateImpl implements PersonaDao {

	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.persistence.dao.eventosCorporativos.PersonaDao#buscarPersonas(com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.Persona, com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO)
	 */
	public PaginaVO buscarPersonas(Persona criterioBusqueda, PaginaVO paginaVO) {

    	final DetachedCriteria criteriaSum = paramsConsultarPersonas(criterioBusqueda);
    	
    	if (paginaVO != null && (paginaVO.getTotalRegistros() == null || paginaVO.getTotalRegistros() <= 0)) {
	    	Integer totalRegistros = (Integer) this.getHibernateTemplate().execute(new HibernateCallback(){
				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					criteriaSum.setProjection(Projections.rowCount());
	    			Criteria crit = criteriaSum.getExecutableCriteria(session);
	    			return crit.uniqueResult();
	    		}
			});
	    	paginaVO.setTotalRegistros(totalRegistros.intValue());
			paginaVO.setOffset(0);
		}
	
		final Integer offset = paginaVO.getOffset() != null ? paginaVO.getOffset():null;		
		final Integer regxpag = paginaVO.getRegistrosXPag() != null ? paginaVO.getRegistrosXPag():null;

		final DetachedCriteria criteria = paramsConsultarPersonas(criterioBusqueda);
		
    	@SuppressWarnings("unchecked")
		List<PersonaVista> resultado = (List<PersonaVista>) this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
    			Criteria crit = criteria.getExecutableCriteria(session);
    			if ( offset != null && regxpag !=null && regxpag != PaginaVO.TODOS) {
	    			crit.setMaxResults(regxpag);
	    			crit.setFetchSize(regxpag);
	    			crit.setFirstResult(offset);
    			}
    			return crit.list();
    		}
		});
    	
		paginaVO.setRegistros(resultado);
		
		return paginaVO;
	}

	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.persistence.dao.eventosCorporativos.PersonaDao#obtenerPersonaPorId(java.lang.Long)
	 */
	public Persona obtenerPersonaPorId(Long idPersona) {
		return (Persona) super.getByPk(Persona.class, idPersona);
	}

	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.persistence.dao.eventosCorporativos.PersonaDao#obtenerPersonasPorGrupo(java.lang.Long)
	 */
	public List<Persona> obtenerPersonasPorGrupo(final Long idGrupo) {

		final StringBuilder sb = new StringBuilder();
		sb.append("FROM " + Persona.class.getName() + " per");
		sb.append(" WHERE per.idPersona IN (SELECT lstPer.id.idPersona FROM GrupoPersonaDistribucion lstPer where lstPer.id.idGrupo = :idGrupo)");
		sb.append(" ORDER BY per.idPersona");

		@SuppressWarnings("unchecked")
		List<Persona> obj = (List<Persona>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sb.toString());
				query.setLong("idGrupo", idGrupo);
				return query.list();
			}
		});
		return obj;

	}

	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.persistence.dao.eventosCorporativos.PersonaDao#obtenerPersonasNoPertenecientesGrupo(java.lang.Long)
	 */
	public List<Persona> obtenerPersonasNoPertenecientesGrupo(final Long idGrupo) {

		final StringBuilder sb = new StringBuilder();
		sb.append("FROM " + Persona.class.getName() + " per");
		sb.append(" WHERE per.idPersona NOT IN (SELECT lstPer.id.idPersona FROM GrupoPersonaDistribucion lstPer where lstPer.id.idGrupo = :idGrupo)");
		sb.append(" AND per.inactivo = 0");
		sb.append(" ORDER BY per.idPersona");

		@SuppressWarnings("unchecked")
		List<Persona> obj = (List<Persona>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sb.toString());
				query.setLong("idGrupo", idGrupo);
				return query.list();
			}
		});
		return obj;
	}

	/**
	 * @param criterioBusqueda
	 * @return
	 */
	private DetachedCriteria paramsConsultarPersonas(Persona criterioBusqueda){		

		Long idPersona = criterioBusqueda.getIdPersona();
		String nombre = criterioBusqueda.getNombre();
		String correo = criterioBusqueda.getCorreo();
		Long estado = criterioBusqueda.getInactivo();

		//Criteria
		DetachedCriteria criteria = DetachedCriteria.forClass(PersonaVista.class);
		
		/*================PARAMETROS==========================*/		  
		 
		criteria.addOrder(Order.desc("idPersona"));

		// ID persona
		if(idPersona != null && idPersona > 0){
			criteria.add(Restrictions.eq("idPersona", idPersona));
		}

		// Nombre
		if(nombre != null && !nombre.trim().equals("")){
			criteria.add(Restrictions.ilike("nombre", nombre, MatchMode.ANYWHERE));
		}

		// Correo
		if(correo != null && !correo.trim().equals("")){
			criteria.add(Restrictions.ilike("correo", correo, MatchMode.ANYWHERE));
		}
		
		// Correo
		if(criterioBusqueda.getDescripcion() != null && !criterioBusqueda.getDescripcion().trim().equals("")){
			criteria.add(Restrictions.ilike("descripcion", criterioBusqueda.getDescripcion().trim(), MatchMode.ANYWHERE));
		}
		
		// Estado (activo / inactivo)
		if(estado != null && estado >= 0){
			criteria.add(Restrictions.eq("inactivo", estado));
		}
		//Id Folio
		if(criterioBusqueda.getInstitucion() != null && criterioBusqueda.getInstitucion().getClaveInstitucion() != null){
			criteria.createAlias("institucion", "inst");
			criteria.createAlias("inst.tipoInstitucion", "tins");
			criteria.add(Restrictions.eq("inst.folioInstitucion", criterioBusqueda.getInstitucion().getFolioInstitucion()));
			criteria.add(Restrictions.eq("tins.claveTipoInstitucion", criterioBusqueda.getInstitucion().getTipoInstitucion().getClaveTipoInstitucion()));
			
		}
		//ListaDistribucion
		if(criterioBusqueda.getListaDistribucion() != null){
			DetachedCriteria criteriaSub = DetachedCriteria.forClass(ListaPersona.class);			
			criteriaSub.add(Restrictions.eq("id.idLista", criterioBusqueda.getListaDistribucion()));
			criteriaSub.setProjection( Projections.property("id.idPersona") );
			
			criteria.add( Subqueries.propertyIn("idPersona", criteriaSub) );
		}
				
		return criteria;
	}

	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.persistence.dao.eventosCorporativos.PersonaDao#obtenerPersonaPorCorreo(java.lang.String)
	 */
	public Persona obtenerPersonaPorCorreo(final String correo) {
		Persona persona = null;
		final StringBuilder sb = new StringBuilder();
		sb.append("FROM " + Persona.class.getName() + " per");
		sb.append(" WHERE lower(per.correo) = :correo");

		@SuppressWarnings("unchecked")
		List<Persona> obj = (List<Persona>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sb.toString());
				query.setString("correo", (correo !=null ? correo.toLowerCase() : ""));
				return query.list();
			}
		});
		
		if(obj!=null && obj.size()>0){
			persona = obj.get(0);
		}

		return persona;
	}

	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.persistence.dao.eventosCorporativos.PersonaDao#obtenerPersonasActivas()
	 */
	public List<Persona> obtenerPersonasActivas() {
		final StringBuilder sb = new StringBuilder();
		sb.append("FROM " + Persona.class.getName() + " per");
		sb.append(" WHERE per.inactivo = 0");
		sb.append(" ORDER BY per.nombre asc");

		@SuppressWarnings("unchecked")
		List<Persona> obj = (List<Persona>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sb.toString());
				return query.list();
			}
		});
		return obj;
	}

	public void borraListaPersonaByPersona(final Long idPersona) {		
			final StringBuffer sb = new StringBuffer();
			sb.append("DELETE " + ListaPersona.class.getName());
			sb.append(" WHERE id.idPersona = :idPersona");				
			getHibernateTemplate().execute(new HibernateCallback() {
				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					Query query = session.createQuery(sb.toString());
					query.setLong("idPersona", idPersona);
					return query.executeUpdate();
				}
			});
	}
	
	
	
}
