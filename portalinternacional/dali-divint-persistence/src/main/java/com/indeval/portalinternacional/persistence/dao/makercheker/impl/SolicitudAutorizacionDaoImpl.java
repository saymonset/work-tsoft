package com.indeval.portalinternacional.persistence.dao.makercheker.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.makercheker.SolicitudAutorizacion;
import com.indeval.portalinternacional.persistence.dao.makercheker.SolicitudAutorizacionDao;


public class SolicitudAutorizacionDaoImpl extends BaseDaoHibernateImpl implements SolicitudAutorizacionDao{
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Override
	public void save(List<SolicitudAutorizacion> solicitudes) {
		if(LOGGER.isTraceEnabled())LOGGER.trace("save");
		for(SolicitudAutorizacion solicitud: solicitudes) {
			save(solicitud);
		}
	}
	
	@Override
	public SolicitudAutorizacion findById(long id) {
		if(LOGGER.isTraceEnabled())LOGGER.trace("findById");
		return (SolicitudAutorizacion) getByPk(SolicitudAutorizacion.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SolicitudAutorizacion> findByIdProcesoAutorizacion(final long idProcesoAutorizacion) {
		if(LOGGER.isTraceEnabled())LOGGER.trace("findByIdProceso");
		return (List<SolicitudAutorizacion>)getHibernateTemplate().execute(new HibernateCallback() {
			
			@Override
			public List<SolicitudAutorizacion> doInHibernate(Session session) throws HibernateException, SQLException {
				
				Criteria criteria = session.createCriteria(SolicitudAutorizacion.class);
				criteria.add( Restrictions.eq("idProcesoAutorizacion", idProcesoAutorizacion ));

				return criteria.list();
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SolicitudAutorizacion> findByClaveFlujoOperativo(final String[] clavesFlujoOperativo) {
		if(LOGGER.isTraceEnabled())LOGGER.trace("findByClaveFlujoOperativo");
		
		return (List<SolicitudAutorizacion>)getHibernateTemplate().execute(new HibernateCallback() {
			
			@Override
			public List<SolicitudAutorizacion> doInHibernate(Session session) throws HibernateException, SQLException {
				
				Criteria criteria = session.createCriteria(SolicitudAutorizacion.class);
				criteria.add( Restrictions.eq("estatus", Constantes.ESTATUS_SOLICITUD_PENDIENTE));
				criteria.add( Restrictions.in("claveFlujoOperativo", clavesFlujoOperativo));

				return criteria.list();
			}
		});
	}

	@Override
	public int actualizaEstatus(final long idSolicitud, final String estatus) {
		if(LOGGER.isTraceEnabled())LOGGER.trace("actualizaEstatus");
		
		return (Integer)getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Integer doInHibernate(Session session) throws HibernateException, SQLException {
				
				Query query = session.getNamedQuery("SolicitudAutorizacion.actualizaEstatus");
				query.setParameter("estatus", estatus);
				query.setParameter("fechaRespuesta", new Date());
				query.setParameter("idSolicitud", idSolicitud);
				
				return query.executeUpdate();
			}
		});
	}
}
