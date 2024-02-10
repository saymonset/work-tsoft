/**
 * 
 */
package com.indeval.portalinternacional.persistence.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
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
import com.indeval.portalinternacional.middleware.servicios.modelo.BitacoraMensajeConciliacionInt;
import com.indeval.portalinternacional.middleware.servicios.modelo.BitacoraMensajeSwift;
import com.indeval.portalinternacional.middleware.servicios.modelo.ConciliacionInt;
import com.indeval.portalinternacional.middleware.servicios.modelo.DetalleConciliacionInt;
import com.indeval.portalinternacional.middleware.servicios.vo.ConciliacionIntDTO;
import com.indeval.portalinternacional.middleware.servicios.vo.DetalleConciliacionIntDTO;
import com.indeval.portalinternacional.persistence.dao.ConciliacionIntDao;

/**
 * @author lmunoz
 *
 */
public class ConciliacionIntDaoImpl extends BaseDaoHibernateImpl implements ConciliacionIntDao{
	
	/**
	 * consulta de conciliacion
	 */
	public PaginaVO consultaConciliacion(ConciliacionIntDTO conciliacion, PaginaVO pagina) throws BusinessException {
		if(pagina == null){
			pagina = new PaginaVO();
		}
		final Integer offset = pagina.getOffset() != null ? pagina.getOffset():null;		
		final Integer regxpag = pagina.getRegistrosXPag() != null ? pagina.getRegistrosXPag():null;
		
		final DetachedCriteria criteria=paramsConsultaConciliacionInt(conciliacion);
		
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
		List<ConciliacionInt> conciliaciones = (List<ConciliacionInt>)this.getHibernateTemplate().executeFind(hibernateCallback);
    	if(conciliaciones != null){
    		pagina.setRegistros(conciliaciones);    		
    	}
    	final DetachedCriteria criteriaSum = paramsConsultaConciliacionInt(conciliacion);
    	Integer tam = (Integer) this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				criteriaSum.setProjection(Projections.rowCount());
    			Criteria crit = criteriaSum.getExecutableCriteria(session);        			
    			return crit.uniqueResult();
    		}
			
		});		
		pagina.setTotalRegistros(tam);
		return pagina;
	}
	
	/**
	 * Genera parametros para conciliacion
	 * @param conciliacion
	 * @return DetachedCriteria
	 */
	private DetachedCriteria paramsConsultaConciliacionInt(ConciliacionIntDTO conciliacion){		
		//Criteria
		DetachedCriteria criteria = DetachedCriteria.forClass(ConciliacionInt.class);
		
		/*================PARAMETROS==========================*/		  
		 
		criteria.add(Restrictions.isNotNull("id"));
		criteria.addOrder(Order.desc("id"));
		
		//fechaConciliacion
		if(conciliacion.getFechaConciliacionInicio() != null && conciliacion.getFechaConciliacionFin() != null ){
			criteria.add(Restrictions.between("fechaConciliacion", 
					DateUtils.preparaFechaConExtremoEnSegundos(conciliacion.getFechaConciliacionInicio(),Boolean.TRUE),
					DateUtils.preparaFechaConExtremoEnSegundos(conciliacion.getFechaConciliacionFin(),Boolean.FALSE) ) );
		}else if(conciliacion.getFechaConciliacionInicio() != null){
			criteria.add(Restrictions.le("fechaConciliacion", 
					DateUtils.preparaFechaConExtremoEnSegundos(conciliacion.getFechaConciliacionInicio(), Boolean.FALSE) ));
		}else if(conciliacion.getFechaConciliacionFin() != null){
			criteria.add(Restrictions.ge("fechaConciliacion", 
					DateUtils.preparaFechaConExtremoEnSegundos(conciliacion.getFechaConciliacionFin(), Boolean.TRUE) ) );
		}
		
		//fechaMensaje
		if(conciliacion.getFechaMensajeInicio() != null && conciliacion.getFechaMensajeFin() != null ){
			criteria.add(Restrictions.between("fechaMensaje", 
					DateUtils.preparaFechaConExtremoEnSegundos(conciliacion.getFechaMensajeInicio(),Boolean.TRUE),
					DateUtils.preparaFechaConExtremoEnSegundos(conciliacion.getFechaMensajeFin(),Boolean.FALSE) ) );
		}else if(conciliacion.getFechaMensajeInicio() != null){
			criteria.add(Restrictions.le("fechaMensaje", 
					DateUtils.preparaFechaConExtremoEnSegundos(conciliacion.getFechaMensajeInicio(), Boolean.FALSE ) ));
		}else if(conciliacion.getFechaMensajeFin() != null){
			criteria.add(Restrictions.ge("fechaMensaje", 
					DateUtils.preparaFechaConExtremoEnSegundos(conciliacion.getFechaMensajeFin(), Boolean.TRUE ) ));
		}		
		
		//bovedaDali
		if(conciliacion.getBovedaDali() != null && conciliacion.getBovedaDali() >= 0){
			criteria.createAlias("bovedaDali", "bovedaDaliAlias")
			.add(Restrictions.eq("bovedaDaliAlias.idBoveda", conciliacion.getBovedaDali().longValue()));
		}
		
		//custodio
		if(conciliacion.getCustodio() != null && conciliacion.getCustodio() >= 0 ){
			criteria.createAlias("custodio", "custodioAlias")
			.add(Restrictions.eq("custodioAlias.id", conciliacion.getCustodio()));
		}
		//folio
		if(StringUtils.isNotEmpty(conciliacion.getFolio())){			
			criteria.add(Restrictions.eq("id", Long.valueOf(conciliacion.getFolio())));
		}
		//Alias estatusconciliacion
		if(conciliacion.isPorConc() || conciliacion.isDifCust() || conciliacion.isDifDali() || conciliacion.isDifPos()){
			criteria.createAlias("estatusConciliacion", "ec");
		}
		//isPorConc
		if(conciliacion.isPorConc()){
			criteria.add(Restrictions.lt("ec.id", 4l));
		}
		//isDifCust
		if(conciliacion.isDifCust()){
			criteria.add(Restrictions.and(Restrictions.eq("ec.id", 4l), Restrictions.eq("diferenciaEmisionesMensaje", Boolean.TRUE)));
		}
		//isDifDali
		if(conciliacion.isDifDali()){
			criteria.add(Restrictions.and(Restrictions.eq("ec.id", 4l), Restrictions.eq("diferenciaEmisionesDali", Boolean.TRUE)));
		}
		//isDifPos
		if(conciliacion.isDifPos()){
			criteria.add(Restrictions.and(Restrictions.eq("ec.id", 4l), Restrictions.ne("diferencia", 0l)));
		}
		//conciliacion Nacional
		if(conciliacion.getConciliacionNacional() != null && conciliacion.getConciliacionNacional()){
			criteria.add(Restrictions.eq("conciliacionNacional", Boolean.TRUE));
		}
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria;
	}
	
	/**
	 * genera la consulta de detalle de conciliaciones
	 */
	public PaginaVO consultaDetalleConciliacion(DetalleConciliacionIntDTO detalleConciliacion, PaginaVO pagina)
			throws BusinessException {
		if(pagina == null){
			pagina = new PaginaVO();
		}
		final Integer offset = pagina.getOffset() != null ? pagina.getOffset():null;		
		final Integer regxpag = pagina.getRegistrosXPag() != null ? pagina.getRegistrosXPag():null;
		
		// totales
		final DetachedCriteria criteriaSum=paramsConsultaDetalleConciliacionInt(detalleConciliacion);
		
    	Integer tam = (Integer) this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				criteriaSum.setProjection(Projections.rowCount());
    			Criteria crit = criteriaSum.getExecutableCriteria(session);        			
    			return crit.uniqueResult();
    		}
			
		});		
		pagina.setTotalRegistros(tam);
		
		//ejecuta la consulta solo si tiene registros
		if(tam > 0){
			final DetachedCriteria criteria=paramsConsultaDetalleConciliacionInt(detalleConciliacion);
			
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
			List<DetalleConciliacionInt> conciliaciones = (List<DetalleConciliacionInt>)this.getHibernateTemplate().executeFind(hibernateCallback);
	    	
	    	if(conciliaciones != null){
	    		pagina.setRegistros(conciliaciones);    		
	    	}
		}
		return pagina;
	}
	/**
	 * 
	 * @param detalleConciliacion
	 * @return
	 */
	private DetachedCriteria paramsConsultaDetalleConciliacionInt(DetalleConciliacionIntDTO detalleConciliacion){
		//Criteria
		DetachedCriteria criteria = DetachedCriteria.forClass(DetalleConciliacionInt.class);
		
		criteria.add(Restrictions.isNotNull("id"));
		criteria.addOrder(Order.desc("id"));
		
		//fechaConciliacion
		if(detalleConciliacion.getFechaConciliacionInicio() != null && detalleConciliacion.getFechaConciliacionFin() != null ){
			criteria.add(Restrictions.between("fechaConciliacion", 
					DateUtils.preparaFechaConExtremoEnSegundos(detalleConciliacion.getFechaConciliacionInicio(),Boolean.TRUE),
					DateUtils.preparaFechaConExtremoEnSegundos(detalleConciliacion.getFechaConciliacionFin(),Boolean.FALSE) ) );
		}else if(detalleConciliacion.getFechaConciliacionInicio() != null){
			criteria.add(Restrictions.le("fechaConciliacion", 
					DateUtils.preparaFechaConExtremoEnSegundos(detalleConciliacion.getFechaConciliacionInicio(), Boolean.FALSE) ));
		}else if(detalleConciliacion.getFechaConciliacionFin() != null){
			criteria.add(Restrictions.ge("fechaConciliacion", 
					DateUtils.preparaFechaConExtremoEnSegundos(detalleConciliacion.getFechaConciliacionFin(), Boolean.TRUE) ) );
		}
		
		//fechaMensaje
		if(detalleConciliacion.getFechaMensajeInicio() != null && detalleConciliacion.getFechaMensajeFin() != null ){
			criteria.add(Restrictions.between("fechaMensaje", 
					DateUtils.preparaFechaConExtremoEnSegundos(detalleConciliacion.getFechaMensajeInicio(),Boolean.TRUE),
					DateUtils.preparaFechaConExtremoEnSegundos(detalleConciliacion.getFechaMensajeFin(),Boolean.FALSE) ) );
		}else if(detalleConciliacion.getFechaMensajeInicio() != null){
			criteria.add(Restrictions.le("fechaMensaje", 
					DateUtils.preparaFechaConExtremoEnSegundos(detalleConciliacion.getFechaMensajeInicio(), Boolean.FALSE ) ));
		}else if(detalleConciliacion.getFechaMensajeFin() != null){
			criteria.add(Restrictions.ge("fechaMensaje", 
					DateUtils.preparaFechaConExtremoEnSegundos(detalleConciliacion.getFechaMensajeFin(), Boolean.TRUE ) ));
		}
		
		//bovedaDali
		if(detalleConciliacion.getBoveda() != null && detalleConciliacion.getBoveda() >= 0){
			criteria.createAlias("boveda", "bovedaAlias")
			.add(Restrictions.eq("bovedaAlias.idBoveda", detalleConciliacion.getBoveda().longValue()));
		}
		
		//custodio
		if(detalleConciliacion.getCustodio() != null && detalleConciliacion.getCustodio() >= 0 ){
			criteria.createAlias("custodio", "custodioAlias")
			.add(Restrictions.eq("custodioAlias.id", detalleConciliacion.getCustodio().intValue()  ));
		}
		//folio
		if(detalleConciliacion.getFolio() != null && detalleConciliacion.getFolio() > 0){			
			criteria.add(Restrictions.eq("conciliacion", detalleConciliacion.getFolio() ));
		}
		//conDiferencia
		if(detalleConciliacion.getConDiferencia()){
			criteria.add(Restrictions.or(Restrictions.isNull("diferencia"), Restrictions.ne("diferencia", 0l)));
		}
		//tipoValor
		if(StringUtils.isNotEmpty(detalleConciliacion.getTipoValor() ) ){
			criteria.add(Restrictions.eq("tv", detalleConciliacion.getTipoValor()).ignoreCase());
		}
		//emisora
		if(StringUtils.isNotEmpty(detalleConciliacion.getEmisora() ) ){
			criteria.add(Restrictions.eq("emisora", detalleConciliacion.getEmisora()).ignoreCase());
		}
		//serie
		if(StringUtils.isNotEmpty(detalleConciliacion.getSerie() ) ){
			criteria.add(Restrictions.eq("serie", detalleConciliacion.getSerie()).ignoreCase());
		}
		//isin
		if(StringUtils.isNotEmpty(detalleConciliacion.getIsin() ) ){
			criteria.add(Restrictions.eq("isin", detalleConciliacion.getIsin()).ignoreCase());
		}
		//criteria.setProjection(Projections.distinct());
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria;
	}

	public List<BitacoraMensajeConciliacionInt> consultaBitacoraMensajeConciliacionInt(final Long id) throws BusinessException {		
		final StringBuilder sb = new StringBuilder();		
		sb.append(" FROM " + BitacoraMensajeConciliacionInt.class.getName() + " bit ");
		sb.append(" where bit.conciliacion = :id ");
		sb.append(" ORDER BY bit.fechaRecepcion desc, bit.id desc ");
		@SuppressWarnings("unchecked")
		List<BitacoraMensajeConciliacionInt> retorno = (List<BitacoraMensajeConciliacionInt>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sb.toString());	
				query.setLong("id", id);
				return query.list();
			}
		});
		return retorno;
	}
}
