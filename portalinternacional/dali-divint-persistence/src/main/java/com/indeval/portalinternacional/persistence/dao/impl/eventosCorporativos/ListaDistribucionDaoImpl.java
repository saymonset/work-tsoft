/**
 * Bursatec - Portal Internacional
 * Copyrigth (c) 2015 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.persistence.dao.impl.eventosCorporativos;

import java.sql.SQLException;
import java.util.ArrayList;
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
import org.springframework.orm.hibernate3.HibernateCallback;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.ListaDistribucion;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.ListaPersona;
import com.indeval.portalinternacional.persistence.dao.eventosCorporativos.ListaDistribucionDao;

/**
 * Implementación de Interfaz para la entidad ListaDistribucion.
 * 
 * @author amoralesm
 *
 */
public class ListaDistribucionDaoImpl extends BaseDaoHibernateImpl implements
		ListaDistribucionDao {

	private static long indicadorNoInactivo = 0l;
	
	
	
	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.persistence.dao.eventosCorporativos.ListaDistribucionDao#obtenerListaDistribucionPorId(java.lang.Long)
	 */
	public ListaDistribucion obtenerListaDistribucionPorId(
			Long idListaDistribucion) {
		ListaDistribucion lista = (ListaDistribucion) super.getByPk(ListaDistribucion.class, idListaDistribucion);
		//lista.setGrupos(grupoDao.obtenerGruposPorListaDistribucion(lista.getIdLista()));
		return lista;
	}

	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.persistence.dao.eventosCorporativos.ListaDistribucionDao#buscarListasDistribucion(com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.ListaDistribucion, com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO)
	 */
	public PaginaVO buscarListasDistribucion(
			ListaDistribucion criterioBusqueda, PaginaVO paginaVO) {

    	final DetachedCriteria criteriaSum = paramsConsultarListaDistribucion(criterioBusqueda);
    	
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

		final DetachedCriteria criteria = paramsConsultarListaDistribucion(criterioBusqueda);
		
    	@SuppressWarnings("unchecked")
		List<ListaDistribucion> resultado = (List<ListaDistribucion>) this.getHibernateTemplate().execute(new HibernateCallback(){
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
	 * @see com.indeval.portalinternacional.persistence.dao.eventosCorporativos.ListaDistribucionDao#obtenerListasDistribucionActivas()
	 */
	public List<ListaDistribucion> obtenerListasDistribucionActivas() {
		final StringBuilder sb = new StringBuilder();
		sb.append("FROM " + ListaDistribucion.class.getName() + " lst");
		sb.append(" WHERE lst.inactivo = :indicadorInactivo");
		sb.append(" ORDER BY lst.idLista");

		@SuppressWarnings("unchecked")
		List<ListaDistribucion> obj = (List<ListaDistribucion>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sb.toString());
				query.setLong("indicadorInactivo", indicadorNoInactivo);
				return query.list();
			}
		});
		return obj;

	}

	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.persistence.dao.eventosCorporativos.ListaDistribucionDao#crearListaDistribucion(com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.ListaDistribucion, java.util.List)
	 */
	public Long crearListaDistribucion(
			ListaDistribucion listaDistribucion) {
		Long idListaDistribucion = (Long) save(listaDistribucion);
		
		
		return idListaDistribucion;
	}

	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.persistence.dao.eventosCorporativos.ListaDistribucionDao#actualizarListaDistribucion(com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.ListaDistribucion)
	 */
	public void actualizarListaDistribucion(ListaDistribucion listaDistribucion) {
		
		Long idLista = listaDistribucion.getIdLista();
		
		// Actualizar lista distribución
		this.actualizarLista(idLista, listaDistribucion.getNombre(), listaDistribucion.getDescripcion(),listaDistribucion.getInactivo());
		
		
		flush();
	}

	
	

	/**
	 * @param idListaDistribucion
	 * @param nombre
	 * @param descripcion 
	 * @param inactivo
	 */
	private void actualizarLista(final Long idLista, final String nombre, final String descripcion, final Long inactivo){

		final StringBuffer sb = new StringBuffer();
		
		sb.append("UPDATE " + ListaDistribucion.class.getName());
		sb.append(" SET nombre = :nombre, inactivo = :inactivo, descripcion = :descripcion ");
		sb.append(" WHERE idLista = :idLista");
				
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sb.toString());
				query.setString("nombre", nombre);
				query.setLong("inactivo", inactivo);
				query.setLong("idLista", idLista);
				query.setString("descripcion", descripcion);
				return query.executeUpdate();
			}
		});

	}

	
	/**
	 * @param sb
	 * @param paramNames
	 * @param paramValues
	 * @return
	 */
	private Long contarRegistros(final StringBuffer sb) {
		return (Long) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sb.toString());
				return query.uniqueResult();
			}
		});
	}


	/**
	 * @param sb
	 * @param pagina
	 * @return
	 */
	private List<ListaDistribucion> buscarListasDistribucion(final StringBuffer sb, final PaginaVO pagina) {

		@SuppressWarnings("unchecked")
		List<ListaDistribucion> obj = (List<ListaDistribucion>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sb.toString());
				if (pagina != null) {
					query.setFetchSize(pagina.getRegistrosXPag());
					query.setFirstResult(pagina.getOffset());
					query.setMaxResults(pagina.getRegistrosXPag());
				}
				return query.list();
			}
		});
		return obj;

	}

	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.persistence.dao.eventosCorporativos.ListaDistribucionDao#obtenerListaDistribucionPorNombre(java.lang.String)
	 */
	public ListaDistribucion obtenerListaDistribucionPorNombre(final String nombre) {
		ListaDistribucion listaDistribucion = null;
		final StringBuilder sb = new StringBuilder();
		sb.append("FROM " + ListaDistribucion.class.getName() + " lst");
		sb.append(" WHERE lst.nombre = :nombre");

		@SuppressWarnings("unchecked")
		List<ListaDistribucion> obj = (List<ListaDistribucion>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sb.toString());
				query.setString("nombre", nombre);
				return query.list();
			}
		});
		
		if(obj!=null && obj.size()>0){
			listaDistribucion = obj.get(0);
		}

		return listaDistribucion;

	}

	/**
	 * @param criterioBusqueda
	 * @return
	 */
	private DetachedCriteria paramsConsultarListaDistribucion(ListaDistribucion criterioBusqueda){		

		Long idLista = criterioBusqueda.getIdLista();
		String nombre = criterioBusqueda.getNombre();
		Long estado = criterioBusqueda.getInactivo();

		//Criteria
		DetachedCriteria criteria = DetachedCriteria.forClass(ListaDistribucion.class);
		
		/*================PARAMETROS==========================*/

		criteria.addOrder(Order.desc("idLista"));

		// ID Lista
		if(idLista != null && idLista > 0){
			criteria.add(Restrictions.eq("idLista", idLista));
		}

		// Nombre
		if(nombre != null && !nombre.trim().equals("")){
			criteria.add(Restrictions.like("nombre", nombre, MatchMode.ANYWHERE));
		}

		// Estado (activo / inactivo)
		if(estado != null && estado >= 0){
			criteria.add(Restrictions.eq("inactivo", estado));
		}
		
		return criteria;
	}
	
	public List<ListaDistribucion> obtenerListasPertenecientes(final Long idPersona) {
		if (idPersona == null){
			return null;
		}
		final StringBuilder sb = new StringBuilder();
		sb.append("FROM " + ListaDistribucion.class.getName() + " ld");
		sb.append(" WHERE ld.inactivo = :inactivo ");
		sb.append(" AND ld.idLista  IN (SELECT distinct lstPer.id.idLista FROM "+ListaPersona.class.getName()+" lstPer where lstPer.id.idPersona = :idPersona) ");		
		sb.append(" ORDER BY ld.nombre");

		@SuppressWarnings("unchecked")
		List<ListaDistribucion> obj = (List<ListaDistribucion>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sb.toString());
				query.setLong("idPersona", idPersona);
				query.setLong("inactivo", indicadorNoInactivo);
				return query.list();
			}
		});
		return obj;

	}

	
	
	public List<ListaDistribucion> obtenerListasNoPertenecientes(final Long idPersona) {

		final StringBuilder sb = new StringBuilder();
		sb.append("FROM " + ListaDistribucion.class.getName() + " ld ");
		sb.append(" WHERE ld.inactivo = :inactivo ");
		if(idPersona != null){
			sb.append(" AND ld.idLista NOT IN (SELECT distinct lstPer.id.idLista FROM "+ListaPersona.class.getName()+" lstPer where lstPer.id.idPersona = :idPersona) ");
		}
		sb.append(" ORDER BY ld.nombre");

		@SuppressWarnings("unchecked")
		List<ListaDistribucion> obj = (List<ListaDistribucion>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sb.toString());
				if(idPersona != null){
					query.setLong("idPersona", idPersona);
				}
				query.setLong("inactivo", indicadorNoInactivo);
				return query.list();
			}
		});
		return obj;
	}
}
