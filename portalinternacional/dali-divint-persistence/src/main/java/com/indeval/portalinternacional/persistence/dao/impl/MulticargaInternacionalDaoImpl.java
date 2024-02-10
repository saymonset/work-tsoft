package com.indeval.portalinternacional.persistence.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.util.DateUtils;
import com.indeval.portalinternacional.middleware.servicios.modelo.EstadoMulticarga;
import com.indeval.portalinternacional.middleware.servicios.modelo.MulticargaInternacional;
import com.indeval.portalinternacional.middleware.servicios.modelo.RegistroMulticarga;
import com.indeval.portalinternacional.middleware.servicios.modelo.TipoOperacionMulticarga;
import com.indeval.portalinternacional.middleware.servicios.multicarga.excell.vo.MulticargaVO;
import com.indeval.portalinternacional.persistence.dao.MulticargaInternacionalDao;

@SuppressWarnings({"unchecked"})
public class MulticargaInternacionalDaoImpl extends BaseDaoHibernateImpl implements MulticargaInternacionalDao {
	
	/**
	 * @see com.indeval.portaldali.persistence.dao.common.MulticargaInternacionalDao#consultaByNombreArchivo(String)
	 */
	public MulticargaInternacional consultaByNombreArchivo(final String nombreArchivo) {
		
			final StringBuilder sb = new StringBuilder();
			if(nombreArchivo == null){
				return null;		
			}
			sb.append(" FROM " + MulticargaInternacional.class.getName() + " multiInt ");
			sb.append(" where multiInt.nombreArchivo = :nombreArchivo ");		
			
			@SuppressWarnings("unchecked")
			MulticargaInternacional multicargaInternacionalRetorno = (MulticargaInternacional) getHibernateTemplate().execute(new HibernateCallback() {
				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					Query query = session.createQuery(sb.toString());	
					query.setString("nombreArchivo", nombreArchivo);
					return query.uniqueResult();
				}
			});
			return multicargaInternacionalRetorno;
		}
	

	/**
	 * @see com.indeval.portaldali.persistence.dao.common.MulticargaInternacionalDao#getCatalogoEstadoMulticarga()
	 */
	public List<EstadoMulticarga> getCatalogoEstadoMulticarga(){
		final StringBuilder sb = new StringBuilder();		
		sb.append(" FROM " + EstadoMulticarga.class.getName() + " edoMulti ");
		sb.append(" ORDER BY edoMulti.idEstadoMulticarga ");
		@SuppressWarnings("unchecked")
		List<EstadoMulticarga> retorno = (List<EstadoMulticarga>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sb.toString());				
				return query.list();
			}
		});
		return retorno;
	}
	
	/**
	 * @see com.indeval.portaldali.persistence.dao.common.MulticargaInternacionalDao#getCatalogoTipoOperacionMulticarga()
	 */
	public List<TipoOperacionMulticarga> getCatalogoTipoOperacionMulticarga(){
		final StringBuilder sb = new StringBuilder();		
		sb.append(" FROM " + TipoOperacionMulticarga.class.getName() + " tipoOpeMulti ");
		sb.append(" ORDER BY tipoOpeMulti.idTipoOperacionMulticarga ");
		@SuppressWarnings("unchecked")
		List<TipoOperacionMulticarga> retorno = (List<TipoOperacionMulticarga>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sb.toString());				
				return query.list();
			}
		});
		return retorno;
	}
	
	/**
	 * @see com.indeval.portaldali.persistence.dao.common.MulticargaInternacionalDao#consultaOperacionesMulticarga(MulticargaVO,PaginaVO)
	 */
	public PaginaVO consultaOperacionesMulticarga(MulticargaVO multicargaVO,PaginaVO paginaVO) throws BusinessException{
		if(paginaVO == null){
			paginaVO = new PaginaVO();
		}
				
		final Integer offset = paginaVO.getOffset() != null ? paginaVO.getOffset():null;		
		final Integer regxpag = paginaVO.getRegistrosXPag() != null ? paginaVO.getRegistrosXPag():null;
		
		// totales
		final DetachedCriteria criteriaSum = paramsConsultaOperacionesMulticarga(multicargaVO);
		
    	Integer tam = (Integer) this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				criteriaSum.setProjection(Projections.rowCount());
    			Criteria crit = criteriaSum.getExecutableCriteria(session);        			
    			return crit.uniqueResult();
    		}
			
		});		
    	paginaVO.setTotalRegistros(tam);
		
		//ejecuta la consulta solo si tiene registros
		if(tam > 0){
			final DetachedCriteria criteria=paramsConsultaOperacionesMulticarga(multicargaVO);
			
			//Callback
			HibernateCallback hibernateCallback = new HibernateCallback() {
	    		public Object doInHibernate(Session session) throws HibernateException, SQLException {    			
	    			Criteria crit = criteria.getExecutableCriteria(session);
	    			if ( offset != null && regxpag !=null && regxpag != PaginaVO.TODOS) {
		    			crit.setMaxResults(regxpag);
		    			crit.setFetchSize(regxpag);
		    			crit.setFirstResult(offset);
	    			}
	    			return crit.list();
	    		}
	    	};    		
	    	//Ejecucion
	    	@SuppressWarnings("unchecked")
			List<MulticargaInternacional> multicargas = (List<MulticargaInternacional>)this.getHibernateTemplate().executeFind(hibernateCallback);	    	
	    	if(multicargas != null){
	    		paginaVO.setRegistros(multicargas);    		
	    	}
		}
		return paginaVO;	
	}
	
	/**
	 * 
	 * @param Operaciones Multicarga
	 * @return
	 */
	private DetachedCriteria paramsConsultaOperacionesMulticarga(MulticargaVO multicargaVO){
		//Criteria
		DetachedCriteria criteria = DetachedCriteria.forClass(MulticargaInternacional.class);
		
		criteria.add(Restrictions.isNotNull("idMulticargaInternacional"));
		criteria.addOrder(Order.desc("idMulticargaInternacional"));
		
		//fecha de Carga
		if(multicargaVO.getFechaCargaInicio() != null && multicargaVO.getFechaCargaFin() != null ){
			criteria.add(Restrictions.between("fechaHoraCarga", 
					DateUtils.preparaFechaConExtremoEnSegundos(multicargaVO.getFechaCargaInicio(),Boolean.TRUE),
					DateUtils.preparaFechaConExtremoEnSegundos(multicargaVO.getFechaCargaFin(),Boolean.FALSE) ) );
		}else if(multicargaVO.getFechaCargaInicio() != null){
			criteria.add(Restrictions.le("fechaHoraCarga", 
					DateUtils.preparaFechaConExtremoEnSegundos(multicargaVO.getFechaCargaInicio(), Boolean.FALSE) ));
		}else if(multicargaVO.getFechaCargaFin() != null){
			criteria.add(Restrictions.ge("fechaHoraCarga", 
					DateUtils.preparaFechaConExtremoEnSegundos(multicargaVO.getFechaCargaFin(), Boolean.TRUE) ) );
		}
		
		//fecha Auctualizacion
		if(multicargaVO.getFechaActualizacionInicio() != null && multicargaVO.getFechaActualizacionFin() != null ){
			criteria.add(Restrictions.between("fechaHoraActualizacion", 
					DateUtils.preparaFechaConExtremoEnSegundos(multicargaVO.getFechaActualizacionInicio(),Boolean.TRUE),
					DateUtils.preparaFechaConExtremoEnSegundos(multicargaVO.getFechaActualizacionFin(),Boolean.FALSE) ) );
		}else if(multicargaVO.getFechaActualizacionInicio() != null){
			criteria.add(Restrictions.le("fechaHoraActualizacion", 
					DateUtils.preparaFechaConExtremoEnSegundos(multicargaVO.getFechaActualizacionInicio(), Boolean.FALSE ) ));
		}else if(multicargaVO.getFechaActualizacionFin() != null){
			criteria.add(Restrictions.ge("fechaHoraActualizacion", 
					DateUtils.preparaFechaConExtremoEnSegundos(multicargaVO.getFechaActualizacionFin(), Boolean.TRUE ) ));
		}
		
		//Estado Multicarga
		if(multicargaVO.getEstadoRegistro() != null && multicargaVO.getEstadoRegistro() >= 0){
			criteria.createAlias("estadoMulticarga", "estadoMulticargaAlias")
			.add(Restrictions.eq("estadoMulticargaAlias.idEstadoMulticarga", multicargaVO.getEstadoRegistro().longValue()));
		}
		
		//Tipo Operacion Multicarga
		if(multicargaVO.getTipoOperacion() != null && multicargaVO.getTipoOperacion() >= 0 ){
			criteria.createAlias("tipoOperacionMulticarga", "tipoOperacionMulticargaAlias")
			.add(Restrictions.eq("tipoOperacionMulticargaAlias.idTipoOperacionMulticarga", multicargaVO.getTipoOperacion().longValue()  ));
		}
		
		//criteria.setProjection(Projections.distinct());
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria;
	}
	
	/**
	 * @see com.indeval.portaldali.persistence.dao.common.MulticargaInternacionalDao#consultaRegistrosByIdMulticarga(Long)
	 */
	public RegistroMulticarga consultaRegistrosByIdMulticarga(final Long idMulticarga) throws BusinessException{
		final StringBuilder sb = new StringBuilder();
		if(idMulticarga == null){
			return null;		
		}
		sb.append(" FROM " + RegistroMulticarga.class.getName() + " registroMulticarga ");
		sb.append(" where registroMulticarga.idMulticargaInternacional = :idMulticarga ");		
		
		@SuppressWarnings("unchecked")
		RegistroMulticarga registroMulticarga = (RegistroMulticarga) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sb.toString());	
				query.setParameter("idMulticarga", idMulticarga);
				return query.uniqueResult();
			}
		});
		return registroMulticarga;
		
	}
	
	/**
	 * @see com.indeval.portaldali.persistence.dao.common.MulticargaInternacionalDao#consultaMulticargaByIdMulticarga(Long)
	 */
	public MulticargaInternacional consultaMulticargaByIdMulticarga(final Long idMulticarga) throws BusinessException{
		
		final StringBuilder sb = new StringBuilder();
		if(idMulticarga == null){
			return null;		
		}
		sb.append(" FROM " + MulticargaInternacional.class.getName() + " multiInt ");
		sb.append(" where multiInt.idMulticargaInternacional = :idMulticarga ");		
		
		@SuppressWarnings("unchecked")
		MulticargaInternacional multicargaInternacionalRetorno = (MulticargaInternacional) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sb.toString());	
				query.setParameter("idMulticarga", idMulticarga);
				return query.uniqueResult();
			}
		});
		return multicargaInternacionalRetorno;		
		
	}
	
	
}
