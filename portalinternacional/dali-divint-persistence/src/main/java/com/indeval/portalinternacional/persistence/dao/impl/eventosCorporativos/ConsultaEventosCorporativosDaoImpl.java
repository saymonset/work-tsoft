/**
 * 
 */
package com.indeval.portalinternacional.persistence.dao.impl.eventosCorporativos;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.LongType;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.util.DateUtils;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.Custodio;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.AdjuntosEventoCorporativo;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.BitacoraCambiosEvco;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.CuerpoEventoCorporativo;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.Estado;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.EventoCorporativo;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.EventoCorporativoConsultaDTO;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.ListaDistribucion;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.NotasEventoCorporativo;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.NotificacionEventoCorporativo;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.OpcionesEventoCorporativo;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.OperadorEvco;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.ReglaEstado;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.TipoDerechoDto;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.TipoDerechoEvCo;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.TipoEvento;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.TipoMercado;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.TipoValidacionEvco;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.ValidacionesEvco;
import com.indeval.portalinternacional.persistence.dao.eventosCorporativos.ConsultaEventosCorporativosDao;




/**
 * @author lmunoz
 *
 */
public class ConsultaEventosCorporativosDaoImpl extends BaseDaoHibernateImpl implements ConsultaEventosCorporativosDao {

	private static final Logger logger = LoggerFactory.getLogger(ConsultaEventosCorporativosDaoImpl.class);
	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.persistence.dao.eventosCorporativos.ConsultaEventosCorporativosDao#getEventosCorporativos(com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO, boolean)
	 */
	public PaginaVO getEventosCorporativos(EventoCorporativoConsultaDTO params, PaginaVO pagina,
			boolean isParticipante) throws BusinessException {
		if(pagina == null){
			pagina = new PaginaVO();
		}
		final Integer offset = pagina.getOffset() != null ? pagina.getOffset():null;		
		final Integer regxpag = pagina.getRegistrosXPag() != null ? pagina.getRegistrosXPag():null;
		
		final DetachedCriteria criteria=paramsConsultaEventoCorporativo(params,isParticipante);
		
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
		List<EventoCorporativo> eventos = (List<EventoCorporativo>)this.getHibernateTemplate().executeFind(hibernateCallback);
    	if(eventos != null){
    		pagina.setRegistros(eventos);    		
    	}
    	final DetachedCriteria criteriaSum = paramsConsultaEventoCorporativo(params,isParticipante);
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
	private DetachedCriteria paramsConsultaEventoCorporativo(EventoCorporativoConsultaDTO evcorp, boolean isParticipante){		
		//Criteria
		DetachedCriteria criteria = DetachedCriteria.forClass(EventoCorporativo.class);
		
		/*================PARAMETROS==========================*/		  
		//Estado Alias para ordenamiento		
		criteria.createAlias("estado", "estadoAlias");		
		
		criteria.add(Restrictions.isNotNull("idEventoCorporativo"));
		criteria.addOrder(Order.desc("prioridad"));
		criteria.addOrder(Order.asc("estadoAlias.ordenamiento"));		
		criteria.addOrder(Order.desc("idEventoCorporativo"));		
		
		//fechacliente
		if(evcorp.getFechaClienteInicio() != null && evcorp.getFechaClienteFin() != null ){
			criteria.add(Restrictions.between("fechaCliente", 
					DateUtils.preparaFechaConExtremoEnSegundos(evcorp.getFechaClienteInicio(),Boolean.TRUE),
					DateUtils.preparaFechaConExtremoEnSegundos(evcorp.getFechaClienteFin(),Boolean.FALSE) ) );
		}else if(evcorp.getFechaClienteInicio() != null){
			criteria.add(Restrictions.le("fechaCliente", 
					DateUtils.preparaFechaConExtremoEnSegundos(evcorp.getFechaClienteInicio(), Boolean.FALSE) ));
		}else if(evcorp.getFechaClienteFin() != null){
			criteria.add(Restrictions.ge("fechaCliente", 
					DateUtils.preparaFechaConExtremoEnSegundos(evcorp.getFechaClienteFin(), Boolean.TRUE) ) );
		}
		
		//fechaCreacion
		if(evcorp.getFechaCreacionInicio() != null && evcorp.getFechaCreacionFin() != null ){
			criteria.add(Restrictions.between("fechaCreacion", 
					DateUtils.preparaFechaConExtremoEnSegundos(evcorp.getFechaCreacionInicio(),Boolean.TRUE),
					DateUtils.preparaFechaConExtremoEnSegundos(evcorp.getFechaCreacionFin(),Boolean.FALSE) ) );
		}else if(evcorp.getFechaCreacionInicio() != null){
			criteria.add(Restrictions.le("fechaCreacion", 
					DateUtils.preparaFechaConExtremoEnSegundos(evcorp.getFechaCreacionInicio(), Boolean.FALSE ) ));
		}else if(evcorp.getFechaCreacionFin() != null){
			criteria.add(Restrictions.ge("fechaCreacion", 
					DateUtils.preparaFechaConExtremoEnSegundos(evcorp.getFechaCreacionFin(), Boolean.TRUE ) ));
		}		
		//fechaIndeval
		if(evcorp.getFechaIndevalInicio() != null && evcorp.getFechaIndevalFin() != null ){
			criteria.add(Restrictions.between("fechaIndeval", 
					DateUtils.preparaFechaConExtremoEnSegundos(evcorp.getFechaIndevalInicio(),Boolean.TRUE),
					DateUtils.preparaFechaConExtremoEnSegundos(evcorp.getFechaIndevalFin(),Boolean.FALSE) ) );
		}else if(evcorp.getFechaIndevalInicio() != null){
			criteria.add(Restrictions.le("fechaIndeval", 
					DateUtils.preparaFechaConExtremoEnSegundos(evcorp.getFechaIndevalInicio(), Boolean.FALSE ) ));
		}else if(evcorp.getFechaIndevalFin() != null){
			criteria.add(Restrictions.ge("fechaIndeval", 
					DateUtils.preparaFechaConExtremoEnSegundos(evcorp.getFechaIndevalFin(), Boolean.TRUE ) ));
		}	
		//fechaPago
		if(evcorp.getFechaPagoInicio() != null && evcorp.getFechaPagoFin() != null ){
			criteria.add(Restrictions.between("fechaPago", 
					DateUtils.preparaFechaConExtremoEnSegundos(evcorp.getFechaPagoInicio(),Boolean.TRUE),
					DateUtils.preparaFechaConExtremoEnSegundos(evcorp.getFechaPagoFin(),Boolean.FALSE) ) );
		}else if(evcorp.getFechaPagoInicio() != null){
			criteria.add(Restrictions.le("fechaPago", 
					DateUtils.preparaFechaConExtremoEnSegundos(evcorp.getFechaPagoInicio(), Boolean.FALSE ) ));
		}else if(evcorp.getFechaPagoFin() != null){
			criteria.add(Restrictions.ge("fechaPago", 
					DateUtils.preparaFechaConExtremoEnSegundos(evcorp.getFechaPagoFin(), Boolean.TRUE ) ));
		}
		
		//fechaRegistro
		if(evcorp.getFechaRegistroInicio() != null && evcorp.getFechaRegistroFin() != null ){
			criteria.add(Restrictions.between("fechaRegistro", 
					DateUtils.preparaFechaConExtremoEnSegundos(evcorp.getFechaRegistroInicio(),Boolean.TRUE),
					DateUtils.preparaFechaConExtremoEnSegundos(evcorp.getFechaRegistroFin(),Boolean.FALSE) ) );
		}else if(evcorp.getFechaRegistroInicio() != null){
			criteria.add(Restrictions.le("fechaRegistro", 
					DateUtils.preparaFechaConExtremoEnSegundos(evcorp.getFechaRegistroInicio(), Boolean.FALSE ) ));
		}else if(evcorp.getFechaRegistroFin() != null){
			criteria.add(Restrictions.ge("fechaRegistro", 
					DateUtils.preparaFechaConExtremoEnSegundos(evcorp.getFechaRegistroFin(), Boolean.TRUE ) ));
		}		
		
		
		//tipoDerechoMO
		if(evcorp.getTipoDerechoMO() != null && evcorp.getTipoDerechoMO() >= 0){
			criteria.createAlias("tipoDerechoMO", "tipoDerechoMOAlias")
			.add(Restrictions.eq("tipoDerechoMOAlias.idTipoDerecho", evcorp.getTipoDerechoMO().longValue()));
		}
		
		//tipoDerechoML
		if(evcorp.getTipoDerechoML() != null && evcorp.getTipoDerechoML() >= 0){
			criteria.createAlias("tipoDerechoML", "tipoDerechoMLAlias")
			.add(Restrictions.eq("tipoDerechoMLAlias.idTipoDerecho", evcorp.getTipoDerechoML().longValue()));
		}
		
		//tipoMercado
		if(evcorp.getTipoMercado() != null && evcorp.getTipoMercado() >= 0){
			criteria.createAlias("tipoMercado", "tipoMercadoAlias")
			.add(Restrictions.eq("tipoMercadoAlias.idTipoMercado", evcorp.getTipoMercado().longValue()));
		}
		//tipoEvento
		if(evcorp.getTipoEvento() != null && evcorp.getTipoEvento() >= 0){
			criteria.createAlias("tipoEvento", "tipoEventoAlias")
			.add(Restrictions.eq("tipoEventoAlias.idTipoEvento", evcorp.getTipoEvento().longValue()));
		}
		
		//Estado
		
		if(evcorp.getEstado() != null && evcorp.getEstado() >= 0){
			criteria.add(Restrictions.eq("estadoAlias.idEstado", evcorp.getEstado().longValue()));			
		}
		
		if(isParticipante){			
			criteria.add(Restrictions.gt("estadoAlias.idEstado", new Long(1)));			
		}
		//custodio
		if(evcorp.getCustodio() != null && evcorp.getCustodio() >= 0 ){
			criteria.createAlias("custodio", "custodioAlias")
			.add(Restrictions.eq("custodioAlias.id", evcorp.getCustodio().intValue()));
		}
		//folio
		if(StringUtils.isNotEmpty(evcorp.getIdEventoCorporativo())){			
			criteria.add(Restrictions.eq("idEventoCorporativo", Long.valueOf(evcorp.getIdEventoCorporativo())));
		}
		//tipoValor
		if(StringUtils.isNotEmpty(evcorp.getTipoValor())){			
			criteria.add(Restrictions.eq("tipoValor",evcorp.getTipoValor()));
		}
		//emisora
		if(StringUtils.isNotEmpty(evcorp.getEmisora())){			
			criteria.add(Restrictions.eq("emisora",evcorp.getEmisora()));
		}
		//serie
		if(StringUtils.isNotEmpty(evcorp.getSerie())){			
			criteria.add(Restrictions.eq("serie",evcorp.getSerie()));
		}
		//isin
		if(StringUtils.isNotEmpty(evcorp.getIsin())){			
			criteria.add(Restrictions.eq("isin",evcorp.getIsin()));
		}
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria;
	}
	
	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.persistence.dao.eventosCorporativos.ConsultaEventosCorporativosDao#getEstadosEventoCorporativo()
	 */
	public List<Estado> getEstadosEventoCorporativo() throws BusinessException {		
		final StringBuilder sb = new StringBuilder();		
		sb.append(" FROM " + Estado.class.getName() + " edo ");
		sb.append(" ORDER BY edo.idEstado ");
		@SuppressWarnings("unchecked")
		List<Estado> retorno = (List<Estado>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sb.toString());				
				return query.list();
			}
		});
		return retorno;		
	}

	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.persistence.dao.eventosCorporativos.ConsultaEventosCorporativosDao#getTiposMercado()
	 */
	public List<TipoMercado> getTiposMercado() throws BusinessException {
		final StringBuilder sb = new StringBuilder();		
		sb.append(" FROM " + TipoMercado.class.getName() + " tm ");
		sb.append(" ORDER BY tm.idTipoMercado ");
		@SuppressWarnings("unchecked")
		List<TipoMercado> retorno = (List<TipoMercado>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sb.toString());				
				return query.list();
			}
		});
		return retorno;	
	}

	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.persistence.dao.eventosCorporativos.ConsultaEventosCorporativosDao#getTiposDerecho()
	 */
	public List<TipoDerechoEvCo> getTiposDerecho() throws BusinessException {
		final StringBuilder sb = new StringBuilder();		
		sb.append(" FROM " + TipoDerechoEvCo.class.getName() + " td ");
		sb.append(" ORDER BY td.idTipoDerecho ");
		@SuppressWarnings("unchecked")
		List<TipoDerechoEvCo> retorno = (List<TipoDerechoEvCo>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sb.toString());				
				return query.list();
			}
		});
		return retorno;
	}

	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.persistence.dao.eventosCorporativos.ConsultaEventosCorporativosDao#getTiposDerechoMO()
	 */
	public List<TipoDerechoEvCo> getTiposDerechoMO() throws BusinessException {
		final StringBuilder sb = new StringBuilder();		
		sb.append(" FROM " + TipoDerechoEvCo.class.getName() + " td ");
		sb.append(" Where td.tipo = :tipoMercadoOrigen  ");
		sb.append(" ORDER BY td.tipoDerecho ");
		@SuppressWarnings("unchecked")
		List<TipoDerechoEvCo> retorno = (List<TipoDerechoEvCo>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sb.toString());	
				query.setString("tipoMercadoOrigen", Constantes.TIPO_EVCORP_CLAVE_MERCADO_ORIGEN);
				return query.list();
			}
		});
		return retorno;
	}

	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.persistence.dao.eventosCorporativos.ConsultaEventosCorporativosDao#getTiposDerechoML()
	 */
	public List<TipoDerechoEvCo> getTiposDerechoML() throws BusinessException {
		final StringBuilder sb = new StringBuilder();		
		sb.append(" FROM " + TipoDerechoEvCo.class.getName() + " td ");
		sb.append(" Where td.tipo = :tipoMercadoLocal  ");
		sb.append(" ORDER BY td.tipoDerecho ");
		@SuppressWarnings("unchecked")
		List<TipoDerechoEvCo> retorno = (List<TipoDerechoEvCo>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sb.toString());	
				query.setString("tipoMercadoLocal", Constantes.TIPO_EVCORP_CLAVE_MERCADO_LOCAL);
				return query.list();
			}
		});
		return retorno;
	}

	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.persistence.dao.eventosCorporativos.ConsultaEventosCorporativosDao#getTiposEvento()
	 */
	public List<TipoEvento> getTiposEvento() throws BusinessException {
		final StringBuilder sb = new StringBuilder();		
		sb.append(" FROM " + TipoEvento.class.getName() + " te ");
		sb.append(" ORDER BY te.idTipoEvento ");
		@SuppressWarnings("unchecked")
		List<TipoEvento> retorno = (List<TipoEvento>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sb.toString());				
				return query.list();
			}
		});
		return retorno;
	}
	
	public List<ReglaEstado> getReglasEstado() throws BusinessException {
		final StringBuilder sb = new StringBuilder();		
		sb.append(" FROM " + ReglaEstado.class.getName() + " re ");
		sb.append(" ORDER BY re.edoActual ");
		@SuppressWarnings("unchecked")
		List<ReglaEstado> retorno = (List<ReglaEstado>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sb.toString());				
				return query.list();
			}
		});
		return retorno;
	}

	public Integer actualizaEstado(final Long idevco, final Long estado)
			throws BusinessException {
		final StringBuilder sb = new StringBuilder();
		Integer retorno = 0;		
		sb.append(" update  " + EventoCorporativo.class.getName() + " ev ");
		sb.append(" set ev.estado.idEstado = :nuevoEstado , ev.actualizado=:actualizado , ev.prioridad = :prioridad ");
		sb.append(" where  ev.idEventoCorporativo = :id  ");	

		retorno+= (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sb.toString());
				query.setLong("nuevoEstado", estado);
				query.setLong("id", idevco);	
				query.setBoolean("actualizado", false);
				query.setInteger("prioridad", 0);
				int ret = query.executeUpdate();
				session.flush();
				return ret;
			}
		});			
		
		return retorno;		
	}

	public CuerpoEventoCorporativo getCuerpoEventoCorporativo(final Long folioevco)
			throws BusinessException {
		final StringBuilder sb = new StringBuilder();		
		sb.append(" FROM " + CuerpoEventoCorporativo.class.getName() + " ce ");
		sb.append(" Where ce.idEventoCorporativo= :idEventoCorporativo  ");
		CuerpoEventoCorporativo retorno = (CuerpoEventoCorporativo) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sb.toString());	
				query.setLong("idEventoCorporativo", folioevco);
				return query.uniqueResult();
			}
		});
		return retorno;
	}
	
	 public void saveWithFlush(Object params)
	            throws BusinessException {
	        logger.debug("Method :::::: (saveWithFlush)");
	        this.getHibernateTemplate().saveOrUpdate(params);
	        this.getHibernateTemplate().flush();
	    }
		
    public TipoDerechoEvCo findTipoDerechoById(Long idTipoDerecho)
            throws BusinessException {
        logger.info("Method :::::: findTipoDerechoById");
        final List<Object> paramsSQL = new ArrayList<Object>();
        final List<Type> tipos = new ArrayList<Type>();
        final StringBuilder sb = new StringBuilder();
        
        sb.append(" from " + TipoDerechoEvCo.class.getName() + " tip ");
        sb.append(" where tip.idTipoDerecho = ?");
        paramsSQL.add(idTipoDerecho);
        tipos.add(new LongType());
        
        return (TipoDerechoEvCo)getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException,
                    SQLException {
                Query query = session.createQuery(sb.toString());
                query.setParameters(paramsSQL.toArray(new Object[0]), tipos.toArray(new Type[0]));
                return query.uniqueResult();
            }
        });
    }

    public TipoMercado findTipoMercadoById(Long idTipoMercado)
            throws BusinessException {
        logger.info("Method :::::: findTipoMercadoById");
        final List<Object> paramsSQL = new ArrayList<Object>();
        final List<Type> tipos = new ArrayList<Type>();
        final StringBuilder sb = new StringBuilder();
        
        sb.append(" from " + TipoMercado.class.getName() + " tm ");
        sb.append(" where tm.idTipoMercado = ?");
        paramsSQL.add(idTipoMercado);
        tipos.add(new LongType());
        
        return (TipoMercado)getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException,
                    SQLException {
                Query query = session.createQuery(sb.toString());
                query.setParameters(paramsSQL.toArray(new Object[0]), tipos.toArray(new Type[0]));
                return query.uniqueResult();
            }
        });
    }
    
    public Long findSequenceValue()throws BusinessException{
        final StringBuilder sb = new StringBuilder();
        
        sb.append("select SEQ_T_Evento_Corporativo_EVCO.nextval from DUAL");
        
        java.math. BigDecimal next=(java.math.BigDecimal)getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException,
                    SQLException {
                Query query = session.createSQLQuery(sb.toString());
                return query.uniqueResult();
            }
        });
        if(next != null){
        	return  next.longValue();
        }else{
        	return null;
        }
    }

    public TipoEvento findTipoEventoById(Long idTipoEvento)
            throws BusinessException {
        logger.info("Method :::::: findTipoEventoById");
        final List<Object> paramsSQL = new ArrayList<Object>();
        final List<Type> tipos = new ArrayList<Type>();
        final StringBuilder sb = new StringBuilder();
        
        sb.append(" from " + TipoEvento.class.getName() + " te ");
        sb.append(" where te.idTipoEvento = ?");
        paramsSQL.add(idTipoEvento);
        tipos.add(new LongType());
        
        return (TipoEvento)getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException,
                    SQLException {
                Query query = session.createQuery(sb.toString());
                query.setParameters(paramsSQL.toArray(new Object[0]), tipos.toArray(new Type[0]));
                return query.uniqueResult();
            }
        });
    }

    public Estado findEstadoById(Long idEstado) throws BusinessException {
        logger.info("Method :::::: findEstadoById");
        final List<Object> paramsSQL = new ArrayList<Object>();
        final List<Type> tipos = new ArrayList<Type>();
        final StringBuilder sb = new StringBuilder();
        
        sb.append(" from " + Estado.class.getName() + " e ");
        sb.append(" where e.idEstado = ?");
        paramsSQL.add(idEstado);
        tipos.add(new LongType());
        
        return (Estado)getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException,
                    SQLException {
                Query query = session.createQuery(sb.toString());
                query.setParameters(paramsSQL.toArray(new Object[0]), tipos.toArray(new Type[0]));
                return query.uniqueResult();
            }
        });
    }

    public Custodio findCustodioById(Long idCustodio) throws BusinessException {
        logger.info("Method :::::: findCustodioById");
        final List<Object> paramsSQL = new ArrayList<Object>();
        final List<Type> tipos = new ArrayList<Type>();
        final StringBuilder sb = new StringBuilder();
        
        sb.append(" from " + Custodio.class.getName() + " c ");
        sb.append(" where c.id = ?");
        paramsSQL.add(idCustodio);
        tipos.add(new LongType());
        
        return (Custodio)getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException,
                    SQLException {
                Query query = session.createQuery(sb.toString());
                query.setParameters(paramsSQL.toArray(new Object[0]), tipos.toArray(new Type[0]));
                return query.uniqueResult();
            }
        });
    }
	public PaginaVO getBitacoraPorEventoCorportativo(EventoCorporativoConsultaDTO params, PaginaVO paginaVO)
			throws BusinessException {
    	
        logger.info("Method :::::: getBitacoraPorEventoCorportativo");
		final String idEventoCorporativo= params.getIdEventoCorporativo();	
		final StringBuilder sb2 = new StringBuilder();		
		sb2.append(" FROM " + BitacoraCambiosEvco.class.getName() + " bi ");
		sb2.append(" Where bi.idEventoCorporativo = :idEventoCorporativo and bi.visible=0");
		sb2.append(" order by bi.idBitacoraCambios asc ");

		@SuppressWarnings("unchecked")
		List<BitacoraCambiosEvco> registrosBitacora = (List<BitacoraCambiosEvco>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sb2.toString());	
				query.setLong("idEventoCorporativo", Long.parseLong(idEventoCorporativo)); 
				return query.list();
			}
		});
		
		paginaVO.setRegistros(registrosBitacora);

		return paginaVO;
	}
    public AdjuntosEventoCorporativo getArchivoAdjuntoPorId(Long idArchivo) throws BusinessException {
    	logger.info("Method :::::: getArchivoAdjuntoPorId");
        final List<Object> paramsSQL = new ArrayList<Object>();
        final List<Type> tipos = new ArrayList<Type>();
        final StringBuilder sb = new StringBuilder();
        
        sb.append(" from " + AdjuntosEventoCorporativo.class.getName() + " a ");
        sb.append(" where a.id = ?");
        paramsSQL.add(idArchivo);
        tipos.add(new LongType());
        
        return (AdjuntosEventoCorporativo)getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException,
                    SQLException {
                Query query = session.createQuery(sb.toString());
                query.setParameters(paramsSQL.toArray(new Object[0]), tipos.toArray(new Type[0]));
                return query.uniqueResult();
            }
        });
    }
	public List<BitacoraCambiosEvco> getBitacoraControlCambios(Long idEventoCorporativoParam)
			throws BusinessException {
		logger.info("Method :::::: getBitacoraControlCambios");
		final Long idEventoCorporativo= idEventoCorporativoParam;
		final StringBuilder sb2 = new StringBuilder();		
		sb2.append(" FROM " + BitacoraCambiosEvco.class.getName() + " bi ");
		sb2.append(" Where bi.idEventoCorporativo = :idEventoCorporativo ");
		sb2.append(" order by bi.idBitacoraCambios desc ");
		@SuppressWarnings("unchecked")
		List<BitacoraCambiosEvco> registrosBitacora = (List<BitacoraCambiosEvco>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sb2.toString());	
				query.setLong("idEventoCorporativo", idEventoCorporativo); 
				return query.list();
			}
		});
		return registrosBitacora;
	}

    public List<BitacoraCambiosEvco> getBitacoraPorRegistro(Long idBitacoraCambioParam) throws BusinessException {
    	logger.info("Method :::::: FindFileForRegister");
		final String idBitacoraCambio=  idBitacoraCambioParam.toString();
	
		final StringBuilder sb2 = new StringBuilder();		
		sb2.append(" FROM " + BitacoraCambiosEvco.class.getName() + " bi ");
		sb2.append(" Where bi.idBitacoraCambios = :idBitacoraCambios ");

		
		@SuppressWarnings("unchecked")
		List<BitacoraCambiosEvco> registrosBitacora = (List<BitacoraCambiosEvco>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sb2.toString());	
				query.setLong("idBitacoraCambios", Long.parseLong(idBitacoraCambio)); 
				return query.list();
			}
		});

		return registrosBitacora;
    }
    
    
    public void updateCambiosDeEstadoBitacora(Long idBitacoraCambioParam, Long estadoParam) throws BusinessException {
    	logger.info("Method :::::: updateCambiosDeEstadoBitacora");
    	final Long idBitacoraCambio= idBitacoraCambioParam;
    	final Long estado= estadoParam;
		final StringBuilder sb = new StringBuilder();
		Integer retorno = 0;		
		sb.append(" update  " + BitacoraCambiosEvco.class.getName() + " bi ");
		sb.append(" set bi.visible = :visible  ");
		sb.append(" where  bi.idBitacoraCambios = :idBitacoraCambios  ");	

		retorno+= (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sb.toString());
				query.setLong("idBitacoraCambios", idBitacoraCambio);	
				query.setLong("visible", estado);
				return query.executeUpdate();
			}
		});	

    }
    public List<NotasEventoCorporativo> getNotasPorEvco(Long idEvco) throws BusinessException {
    	logger.info("Method :::::: getNotasPorEvco");
		final String idEventoCorporativo=  idEvco.toString();
	
		final StringBuilder sb2 = new StringBuilder();		
		sb2.append(" FROM " + NotasEventoCorporativo.class.getName() + " bi ");
		sb2.append(" Where bi.idEventoCorporativo = :idEventoCorporativo ");

		
		@SuppressWarnings("unchecked")
		List<NotasEventoCorporativo> registrosBitacora = (List<NotasEventoCorporativo>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sb2.toString());	
				query.setLong("idEventoCorporativo", Long.parseLong(idEventoCorporativo)); 
				return query.list();
			}
		});

		return registrosBitacora;
    }
    
    
    public List<OpcionesEventoCorporativo> getOpcionesPorEvco(Long idEvco) throws BusinessException {
    	logger.info("Method :::::: getOpcionesPorEvco");
		final String idEventoCorporativo=  idEvco.toString();
	
		final StringBuilder sb2 = new StringBuilder();		
		sb2.append(" FROM " + OpcionesEventoCorporativo.class.getName() + " bi ");
		sb2.append(" Where bi.idEventoCorporativo = :idEventoCorporativo and bi.borrado = 0  order by bi.idOpcion asc");

		
		@SuppressWarnings("unchecked")
		List<OpcionesEventoCorporativo> registrosBitacora = (List<OpcionesEventoCorporativo>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sb2.toString());	
				query.setLong("idEventoCorporativo", Long.parseLong(idEventoCorporativo)); 
				return query.list();
			}
		});

		return registrosBitacora;
    }
    
    
    public List<AdjuntosEventoCorporativo> getAdjuntosPorEvco(final Long idEvco) throws BusinessException {
    	logger.info("Method :::::: getAdjuntosPorEvco");
			
		final StringBuilder sb2 = new StringBuilder();		
		sb2.append(" FROM " + AdjuntosEventoCorporativo.class.getName() + " bi ");
		sb2.append(" Where bi.idEventoCorporativo = :idEventoCorporativo and bi.borrado = 0");
		sb2.append(" order by bi.idAdjuntos asc");
		
		@SuppressWarnings("unchecked")
		List<AdjuntosEventoCorporativo> registrosBitacora = (List<AdjuntosEventoCorporativo>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sb2.toString());	
				query.setLong("idEventoCorporativo", idEvco); 
				return query.list();
			}
		});

		return registrosBitacora;
    }
    
    public List<AdjuntosEventoCorporativo> getAdjuntosPorEvcoNoData(final Long idEvco) throws BusinessException {
    	logger.info("Method :::::: getAdjuntosPorEvcoNo DATA");	
    	   	
		final StringBuilder sb2 = new StringBuilder();		
		sb2.append(" select new "+AdjuntosEventoCorporativo.class.getName()+"(bi.idAdjuntos, bi.idEventoCorporativo, bi.descripcion, bi.borrado)  "  );
		sb2.append("  FROM " + AdjuntosEventoCorporativo.class.getName() + " bi ");
		sb2.append(" Where bi.idEventoCorporativo = :idEventoCorporativo and bi.borrado = 0");
		sb2.append(" order by bi.idAdjuntos asc ");
		
		@SuppressWarnings("unchecked")
		List<AdjuntosEventoCorporativo> registrosBitacora = (List<AdjuntosEventoCorporativo>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sb2.toString());	
				query.setLong("idEventoCorporativo", idEvco); 
				return query.list();
			}
		});
		return registrosBitacora;
    }
    
    
    public EventoCorporativo getResumenCorporativo(final Long idEventoCorporativoParam) throws BusinessException {
    	logger.info("Method :::::: FindFileForRegister");
        final List<Object> paramsSQL = new ArrayList<Object>();
        final List<Type> tipos = new ArrayList<Type>();
        final StringBuilder sb = new StringBuilder();
        
        sb.append(" from " + EventoCorporativo.class.getName() + " e ");
        sb.append(" where e.idEventoCorporativo = :id");
        paramsSQL.add(idEventoCorporativoParam);
        tipos.add(new LongType());
        
        return (EventoCorporativo)getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException,
                    SQLException {
                Query query = session.createQuery(sb.toString());
                query.setLong("id", idEventoCorporativoParam);
                //query.setParameters(paramsSQL.toArray(new Object[0]), tipos.toArray(new Type[0]));
                return query.uniqueResult();
            }
        });
    }
    
	public TipoValidacionEvco getTipoValidacionById(Long id) throws BusinessException {
        final List<Object> paramsSQL = new ArrayList<Object>();
        final List<Type> tipos = new ArrayList<Type>();
        final StringBuilder sb = new StringBuilder();
        
        sb.append(" from " + TipoValidacionEvco.class.getName() + " tv ");
        sb.append(" where tv.idtipoValidacion = ? ");
        paramsSQL.add(id);
        tipos.add(new LongType());
        
		return (TipoValidacionEvco)getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createQuery(sb.toString());
                query.setParameters(paramsSQL.toArray(new Object[0]), tipos.toArray(new Type[0]));
				return query.uniqueResult();
			}
		});
	}

	public OperadorEvco getOperadorById(Long id) throws BusinessException {
        final List<Object> paramsSQL = new ArrayList<Object>();
        final List<Type> tipos = new ArrayList<Type>();
        final StringBuilder sb = new StringBuilder();
        
        sb.append(" from " + OperadorEvco.class.getName() + " oe ");
        sb.append(" where oe.idoperador = ? ");
        paramsSQL.add(id);
        tipos.add(new LongType());
        
		return (OperadorEvco)getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createQuery(sb.toString());
                query.setParameters(paramsSQL.toArray(new Object[0]), tipos.toArray(new Type[0]));
				return query.uniqueResult();
			}
		});
	}
    
    public List<NotificacionEventoCorporativo> getNotificacionesPorEvco(Long idEvco) throws BusinessException {
    	logger.info("Method :::::: getOpcionesPorEvco");
		final String idEventoCorporativo=  idEvco.toString();
	
		final StringBuilder sb2 = new StringBuilder();		
		sb2.append(" FROM " + NotificacionEventoCorporativo.class.getName() + " no ");
		sb2.append(" Where no.idEventoCorporativo = :idEventoCorporativo and no.vigente = 1 ");
		sb2.append(" order by no.idNotificacion asc ");

		
		@SuppressWarnings("unchecked")
		List<NotificacionEventoCorporativo> registrosBitacora = (List<NotificacionEventoCorporativo>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sb2.toString());	
				query.setLong("idEventoCorporativo", Long.parseLong(idEventoCorporativo)); 
				return query.list();
			}
		});

		return registrosBitacora;
    }
    public List<ValidacionesEvco> getValidacionesPorEvco(Long idEvco) throws BusinessException {
    	logger.info("Method :::::: getValidacionesPorEvco");
		final String idEventoCorporativo=  idEvco.toString();
	
		final StringBuilder sb2 = new StringBuilder();		
		sb2.append(" FROM " + ValidacionesEvco.class.getName() + " va ");
		sb2.append(" Where va.idEventoCorporativo = :idEventoCorporativo ");
		sb2.append(" order by va.idValidacion ");

		
		@SuppressWarnings("unchecked")
		List<ValidacionesEvco> registrosBitacora = (List<ValidacionesEvco>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sb2.toString());	
				query.setLong("idEventoCorporativo", Long.parseLong(idEventoCorporativo)); 
				return query.list();
			}
		});

		return registrosBitacora;
    }
    
    public PaginaVO getCatalogoTiposDerecho(TipoDerechoDto params, 	PaginaVO paginaVO) throws BusinessException {
        logger.info("ENTRAMOS::: Method :::::: getCatalogoTiposDerecho");
		if(paginaVO == null){
			paginaVO = new PaginaVO();
		}
		final Integer offset = paginaVO.getOffset() != null ? paginaVO.getOffset():null;		
		final Integer regxpag = paginaVO.getRegistrosXPag() != null ? paginaVO.getRegistrosXPag():null;
		
		final DetachedCriteria criteria=paramsTiposDerecho(params);
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
		List<TipoDerechoEvCo> eventos = (List<TipoDerechoEvCo>)this.getHibernateTemplate().executeFind(hibernateCallback);
    	if(eventos != null){
    		paginaVO.setRegistros(eventos);    		
    	}
    	final DetachedCriteria criteriaSum = paramsTiposDerecho(params);
    	Integer tam = (Integer) this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				criteriaSum.setProjection(Projections.rowCount());
    			Criteria crit = criteriaSum.getExecutableCriteria(session);        			
    			return crit.uniqueResult();
    		}
			
		});
    	paginaVO.setTotalRegistros(tam);
    	return paginaVO;
    }
    
	public List<TipoValidacionEvco> getAllTipoValidacionEvco() throws BusinessException {
		final StringBuilder sb = new StringBuilder();
		sb.append("from " + TipoValidacionEvco.class.getName() + " tv ");
		@SuppressWarnings("unchecked")
		List<TipoValidacionEvco> retorno = (List<TipoValidacionEvco>)getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sb.toString());
				return query.list();
			}
		});
		return retorno;
	}
	
	@SuppressWarnings("unchecked")
	public List<OperadorEvco> getAllOperadorEvco() throws BusinessException {
		final StringBuilder sb = new StringBuilder();
		sb.append(" from " + OperadorEvco.class.getName() + " oe ");
		sb.append(" order by oe.idoperador ");
		List<OperadorEvco> retorno = (List<OperadorEvco>)getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sb.toString());
				return query.list();
			}
		});
		return retorno;
	}
	
	@SuppressWarnings("unchecked")
	public List<ListaDistribucion> getAllListaDistribucion() throws BusinessException {
		final StringBuilder sb = new StringBuilder();
		sb.append(" from " + ListaDistribucion.class.getName()+" as ld");
		sb.append(" where ld.inactivo = 0 ");
		List<ListaDistribucion> retorno = (List<ListaDistribucion>)getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sb.toString());
				return query.list();
			}
		});
		return retorno;
	}

	public ListaDistribucion getListaDisById(Long id) throws BusinessException {
        final List<Object> paramsSQL = new ArrayList<Object>();
        final List<Type> tipos = new ArrayList<Type>();
        final StringBuilder sb = new StringBuilder();
        
        sb.append(" from " + ListaDistribucion.class.getName() + " ld ");
        sb.append(" where ld.idLista = ? ");
        paramsSQL.add(id);
        tipos.add(new LongType());
        
		return (ListaDistribucion)getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createQuery(sb.toString());
                query.setParameters(paramsSQL.toArray(new Object[0]), tipos.toArray(new Type[0]));
				return query.uniqueResult();
			}
		});
	}
    
    
    /**
	 * Genera parametros para conciliacion
	 * @param conciliacion
	 * @return DetachedCriteria
	 */
	private DetachedCriteria paramsTiposDerecho(TipoDerechoDto tipoDerecho){		
		//Criteria
		DetachedCriteria criteria = DetachedCriteria.forClass(TipoDerechoEvCo.class);
		
		/*================PARAMETROS==========================*/		  
 
		criteria.add(Restrictions.isNotNull("idTipoDerecho"));
		criteria.addOrder(Order.desc("idTipoDerecho"));

		if(!tipoDerecho.getTipoDerecho().equals("-1")){			
			criteria.add(Restrictions.eq("tipo",tipoDerecho.getTipoDerecho()));
		}

		if(!tipoDerecho.getActivo().equals("-1")){			
			criteria.add(Restrictions.eq("inactivo",Long.valueOf(tipoDerecho.getActivo())));
		}
		
		if(StringUtils.isNotEmpty(tipoDerecho.getNombre())){			
			criteria.add(Restrictions.ilike("tipoDerecho",tipoDerecho.getNombre(),MatchMode.ANYWHERE));
		}
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria;
	}
	

	
	public void borraInfoAdjuntosEvCo(final Long id) throws BusinessException {
		logger.info("Method :::::: LimpiaDatos de adjuntos idEvco: "+id);
    	
		final StringBuilder sb = new StringBuilder();
		Integer retorno = 0;		
		sb.append(" update  " + AdjuntosEventoCorporativo.class.getName() + " bi ");
		sb.append(" set bi.archivo = null  ");
		sb.append(" where  bi.idEventoCorporativo = :id  ");	

		retorno+= (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sb.toString());
				query.setLong("id", id);				
				return query.executeUpdate();
			}
		});	
	}

	public void deleteAdjuntoLogico(final Long idAdjuntos) {
		logger.info("Method :::::: LimpiaDatos de adjuntos idAdjuntos: "+idAdjuntos);	    	
		final StringBuilder sb = new StringBuilder();
		Integer retorno = 0;		
		sb.append(" update  " + AdjuntosEventoCorporativo.class.getName() + " bi ");
		sb.append(" set bi.archivo = null,  ");
		sb.append("  bi.borrado = :borrado  ");
		sb.append(" where  bi.idAdjuntos = :id  ");	

		retorno+= (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sb.toString());
				query.setLong("id", idAdjuntos);	
				query.setString("borrado", "1");
				return query.executeUpdate();
			}
		});
	}

}
