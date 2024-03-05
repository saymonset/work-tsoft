/*
 * Copyrigth (c) 2009 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.dao.admonretiros.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.lob.ClobImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portaldali.middleware.dto.CuentaDTO;
import com.indeval.portaldali.middleware.dto.RetiroEfectivoDTO;
import com.indeval.portaldali.middleware.dto.RetiroEfectivoInternacionalDTO;
import com.indeval.portaldali.middleware.dto.criterio.CriterioEstatusOpEfectivoDTO;
import com.indeval.portaldali.middleware.dto.util.DTOAssembler;
import com.indeval.portaldali.middleware.services.common.constants.DaliConstants;
import com.indeval.portaldali.persistence.model.EstadoInstruccion;
import com.indeval.portaldali.persistence.model.RetiroEfectivo;
import com.indeval.portaldali.persistence.model.RetiroEfectivoInternacional;
import com.indeval.portaldali.persistence.dao.admonretiros.RetiroEfectivoDao;

/**
 * Implementacion de la interface RetiroEfectivoDao, contiene los metodos 
 * para las acciones sobre el catalogo de retiros  
 * 
 * @author Maria C. Buendia
 * @version 1.0
 */
public class RetiroEfectivoDaoImpl extends BaseDaoHibernateImpl implements RetiroEfectivoDao {
	
    /** Log de clase. */
    private static final Logger logger = LoggerFactory.getLogger(RetiroEfectivoDaoImpl.class);

	/**
	 * Consulta los retiros por id
	 * @param id objeto Long que identifica un retiro
	 * @return objeto tipo RetiroEfectivoInternacional
	 */
	public RetiroEfectivoInternacional getRetiro(BigInteger id){
		logger.debug("Encontrando... RetiroEfectivoInternacional con id : " + id);
		return (RetiroEfectivoInternacional)getHibernateTemplate().get(RetiroEfectivoInternacional.class, id);
	}
	
	/**
	 * Almacena un nuevo registro de retiro de efectivo
	 * @param  RetiroEfectivoInternacional
	 */
	public void save(RetiroEfectivoInternacional retiro){
		logger.info("salvando un retiro internacional");
		super.save(retiro);
	}
	
	/**
	 * Almacena un nuevo registro de retiro de efectivo nacional
	 * @param  RetiroEfectivoInternacional
	 */
	public void save(RetiroEfectivo retiro){
		logger.info("salvando un retiro nacional");
		super.save(retiro);
	}
	
	/**
	 * Actualiza un retiro
	 * @param  RetiroEfectivoInternacional
	 */
	public void saveOrUpdate(RetiroEfectivoInternacional retiro){
		logger.debug("saveOrUpdate, RetiroEfectivoInternacional");
		getHibernateTemplate().saveOrUpdate(retiro);
	}
	
	/**
	 * Consulta los retiros por id
	 * @param referenciaOperacion la referencia operacion del retiro
	 * @param esNacional define si es nacional o no
	 * @return objeto tipo RetiroEfectivoInternacionalDTO o RetiroEfectivoDTO
	 */
	public Object getRetiro(String referenciaOperacion, final boolean esNacional){
		final StringBuffer query = new StringBuffer();
		query.append(" from ");
		query.append(esNacional? RetiroEfectivo.class.getName(): RetiroEfectivoInternacional.class.getName()).append(" r ");
		query.append(" where r.referenciaOperacion = '").append(referenciaOperacion).append("'");

		
		return (Object) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session) throws HibernateException, SQLException {
						Query hQuery = session.createQuery(query.toString());
						Object retiro = esNacional? (RetiroEfectivo)hQuery.uniqueResult() 
													: (RetiroEfectivoInternacional)hQuery.uniqueResult();

						if(retiro == null){
							return null;
						}
						
						if(retiro instanceof RetiroEfectivo){
							RetiroEfectivo re = (RetiroEfectivo)retiro;
							RetiroEfectivoDTO retiroDTO = DTOAssembler.crearRetiroEfectivoDTO(re);
							return retiroDTO;
						}else{
							RetiroEfectivoInternacional re = (RetiroEfectivoInternacional)retiro;
							RetiroEfectivoInternacionalDTO retiroDTO = DTOAssembler.crearRetiroEfectivoInternacionalDTO(re);
							return retiroDTO;
						}
						
					}
				});		
	}
	
	
	@Override
	public RetiroEfectivo findByReferencia(final String referenciaOperacion) {
		return (RetiroEfectivo)getHibernateTemplate().execute(new HibernateCallback() {
			
			@Override
			public RetiroEfectivo doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(RetiroEfectivo.class);
				criteria.add(Restrictions.eq("referenciaOperacion", referenciaOperacion));
				
				//criteria.setFetchMode("estado", FetchMode.JOIN);
				//criteria.setFetchMode("tipoInstruccion", FetchMode.JOIN);
				criteria.setFetchMode("institucion", FetchMode.JOIN);
				//criteria.setFetchMode("institucion.tipoInstitucion", FetchMode.JOIN);
				criteria.setFetchMode("idInstReceptor", FetchMode.JOIN);
				//criteria.setFetchMode("idInstReceptor.tipoInstitucion", FetchMode.JOIN);
				//criteria.setFetchMode("divisa", FetchMode.JOIN);
				//criteria.setFetchMode("boveda", FetchMode.JOIN);
				
				// agregarCrite.rios(criterioConsulta, criteria);
				return (RetiroEfectivo)criteria.uniqueResult();
			}
		});
	}

	@Override
	public RetiroEfectivo findById(final long idOperacionEfectivo) {
		logger.debug("findById");
		
		return (RetiroEfectivo)getHibernateTemplate().execute(new HibernateCallback() {
			
			@Override
			public RetiroEfectivo doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(RetiroEfectivo.class);
				criteria.add(Restrictions.idEq(idOperacionEfectivo));
				
				criteria.setFetchMode("estado", FetchMode.JOIN);
				criteria.setFetchMode("tipoInstruccion", FetchMode.JOIN);
				criteria.setFetchMode("institucion", FetchMode.JOIN);
				criteria.setFetchMode("institucion.tipoInstitucion", FetchMode.JOIN);
				criteria.setFetchMode("idInstReceptor", FetchMode.JOIN);
				criteria.setFetchMode("idInstReceptor.tipoInstitucion", FetchMode.JOIN);
				criteria.setFetchMode("divisa", FetchMode.JOIN);
				criteria.setFetchMode("boveda", FetchMode.JOIN);
				
				// agregarCrite.rios(criterioConsulta, criteria);
				return (RetiroEfectivo)criteria.uniqueResult();
			}
		});
	}

	@Override
	public int libera(long id, String usuario, String serie, String isoFirmado) {
		logger.debug("libera");
		Query query = getSession().getNamedQuery("RetiroEfectivo.liberaOperacion");
		
		Date fecha = new Date();
		query.setParameter("idEstadoLiberada", BigInteger.valueOf(EstadoInstruccion.LIBERADA_VALUE));
		query.setParameter("idEstadoAutorizada", BigInteger.valueOf(EstadoInstruccion.AUTORIZADA_VALUE));
		query.setParameter("id", id);
		
		query.setParameter("usuario", usuario);
		query.setParameter("serie", serie);
		query.setParameter("firma", new ClobImpl(isoFirmado));
		query.setParameter("fecha", fecha);
		query.setParameter("fechaEnvio", fecha);
		
		return query.executeUpdate();
	}

	@Override
	public int autoriza(long id, String usuario, String serie, String isoFirmado) {
		logger.debug("autoriza");
		Query query = getSession().getNamedQuery("RetiroEfectivo.autorizaOperacion");
		
		
		
		query.setParameter("idEstadoAutorizada", BigInteger.valueOf(EstadoInstruccion.AUTORIZADA_VALUE));
		query.setParameter("idEstadoRegistrada", BigInteger.valueOf(EstadoInstruccion.REGISTRADA_VALUE));
		query.setParameter("id", id);
		
		query.setParameter("usuario", usuario);
		query.setParameter("serie", serie);
		query.setParameter("firma", new ClobImpl(isoFirmado));
		query.setParameter("fecha", new Date());
		
		return query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public List<RetiroEfectivo> find(final CriterioEstatusOpEfectivoDTO criterioConsulta){
		logger.debug("find");
		return (List<RetiroEfectivo>)getHibernateTemplate().execute(new HibernateCallback() {
			
			@Override
			public List<RetiroEfectivo> doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(RetiroEfectivo.class);
				
				criteria.setFetchMode("estado", FetchMode.JOIN);
				criteria.setFetchMode("tipoInstruccion", FetchMode.JOIN);
				criteria.setFetchMode("institucion", FetchMode.JOIN);
				criteria.setFetchMode("institucion.tipoInstitucion", FetchMode.JOIN);
				criteria.setFetchMode("idInstReceptor", FetchMode.JOIN);
				criteria.setFetchMode("idInstReceptor.tipoInstitucion", FetchMode.JOIN);
				criteria.setFetchMode("divisa", FetchMode.JOIN);
				criteria.setFetchMode("boveda", FetchMode.JOIN);
				
				criteria.createAlias("estado", "estado", Criteria.INNER_JOIN);
				criteria.createAlias("tipoInstruccion", "tipo", Criteria.INNER_JOIN);
				criteria.createAlias("institucion", "emisor", Criteria.INNER_JOIN);
				criteria.createAlias("idInstReceptor", "receptor", Criteria.INNER_JOIN);
				criteria.createAlias("divisa", "divisa", Criteria.INNER_JOIN);
				criteria.createAlias("boveda", "boveda", Criteria.INNER_JOIN);
				
				
				
				criteria.add(Restrictions.in("estado.idEstadoInstruccion", 
						Arrays.asList(
								BigInteger.valueOf(EstadoInstruccion.CANCELADA_VALUE), 
								BigInteger.valueOf(EstadoInstruccion.REGISTRADA_VALUE),
								BigInteger.valueOf(EstadoInstruccion.AUTORIZADA_VALUE)) ));/**/
				
				agregarCriterios(criterioConsulta, criteria);
				return criteria.list();
			}
		});
	} 
	@Override
	public int count(final CriterioEstatusOpEfectivoDTO criterioConsulta) {
		logger.debug("count");
		return (Integer)getHibernateTemplate().execute(new HibernateCallback() {
			
			@Override
			public Integer doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(RetiroEfectivo.class);
				criteria.setProjection(Projections.rowCount());
				criteria.createAlias("estado", "estado", Criteria.INNER_JOIN);
				criteria.createAlias("tipoInstruccion", "tipo", Criteria.INNER_JOIN);
				criteria.createAlias("institucion", "emisor", Criteria.INNER_JOIN);
				criteria.createAlias("idInstReceptor", "receptor", Criteria.INNER_JOIN);
				criteria.createAlias("divisa", "divisa", Criteria.INNER_JOIN);
				criteria.createAlias("boveda", "boveda", Criteria.INNER_JOIN);
				
				criteria.add(Restrictions.in("estado.idEstadoInstruccion", 
						Arrays.asList(
								BigInteger.valueOf(EstadoInstruccion.CANCELADA_VALUE), 
								BigInteger.valueOf(EstadoInstruccion.REGISTRADA_VALUE),
								BigInteger.valueOf(EstadoInstruccion.AUTORIZADA_VALUE)) ));/**/
				
				agregarCriterios(criterioConsulta, criteria);
				return ((Number)criteria.uniqueResult()).intValue();
			}
		});
	}
	
	@Override
	public Number sumImporte(final CriterioEstatusOpEfectivoDTO criterioConsulta) {
		logger.debug("sumImporte");
		return (BigDecimal)getHibernateTemplate().execute(new HibernateCallback() {
			
			@Override
			public Number doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(RetiroEfectivo.class);
				criteria.setProjection(Projections.sum("importeTraspaso"));
				criteria.createAlias("estado", "estado", Criteria.INNER_JOIN);
				criteria.createAlias("tipoInstruccion", "tipo", Criteria.INNER_JOIN);
				criteria.createAlias("institucion", "emisor", Criteria.INNER_JOIN);
				criteria.createAlias("idInstReceptor", "receptor", Criteria.INNER_JOIN);
				criteria.createAlias("divisa", "divisa", Criteria.INNER_JOIN);
				criteria.createAlias("boveda", "boveda", Criteria.INNER_JOIN);
				
				criteria.add(Restrictions.in("estado.idEstadoInstruccion", 
						Arrays.asList(
								BigInteger.valueOf(EstadoInstruccion.CANCELADA_VALUE), 
								BigInteger.valueOf(EstadoInstruccion.REGISTRADA_VALUE),
								BigInteger.valueOf(EstadoInstruccion.AUTORIZADA_VALUE)) ));/**/
				
				agregarCriterios(criterioConsulta, criteria);
				return ((Number)criteria.uniqueResult());
			}
		});
	}
	
	private void agregarCriterios(CriterioEstatusOpEfectivoDTO criterios, Criteria criteria) {
		
		// DIVISA
		if(criterios.getDivisa()!= null && criterios.getDivisa().getId() >  DaliConstants.VALOR_COMBO_TODOS ) {
			criteria.add(Restrictions.eq("divisa.idDivisa", BigInteger.valueOf(criterios.getDivisa().getId()) ));
		}
		// BOVEDA
		if(criterios.getBoveda()!=null && criterios.getBoveda().getId() > DaliConstants.VALOR_COMBO_TODOS) {
			criteria.add(Restrictions.eq("boveda.idBoveda", BigInteger.valueOf(criterios.getBoveda().getId()) ));
		}
		// TIPO INSTRUCCION 
		if(criterios.getTipoInstruccion()!=null && criterios.getTipoInstruccion().getIdTipoInstruccion() > DaliConstants.VALOR_COMBO_TODOS) {
			criteria.add(Restrictions.eq("tipo.idTipoInstruccion", BigInteger.valueOf(criterios.getTipoInstruccion().getIdTipoInstruccion()) ));
		}
		// TIPO RETIRO - 0:TODOS, 1:SPEI, 2:SIAC, 3:CCS
		if( StringUtils.isNotBlank(criterios.getTipoRetiro()) && StringUtils.isNumeric(criterios.getTipoRetiro()) ) {
			int tipoOperacion = Integer.parseInt(criterios.getTipoRetiro());
			
			switch(tipoOperacion) {
				case 1:
					criteria.add(Restrictions.eq("tipoOperacion", "SPEI" )); break;
				case 2:
					criteria.add(Restrictions.eq("tipoOperacion", "SIAC" )); break;
				case 3:
					criteria.add(Restrictions.eq("tipoOperacion", "CCS" )); break;
				case 0:
				default:
					// do nothing
			}
			
		}
		// FECHA LIQUIDACION
		if(criterios.getFechaLiquidacion()!= null) {
			Calendar low = Calendar.getInstance();
			low.setTime(criterios.getFechaLiquidacion() );
			low.set(Calendar.HOUR_OF_DAY, low.getMinimum(Calendar.HOUR_OF_DAY)); 
			low.set(Calendar.MINUTE, low.getMinimum(Calendar.MINUTE)); 
			low.set(Calendar.SECOND, low.getMinimum(Calendar.SECOND)); 
			low.set(Calendar.MILLISECOND, low.getMinimum(Calendar.MILLISECOND));
			Calendar hi = Calendar.getInstance();
			hi.setTime(criterios.getFechaLiquidacion());
			hi.set(Calendar.HOUR_OF_DAY, hi.getMaximum(Calendar.HOUR_OF_DAY)); 
			hi.set(Calendar.MINUTE, hi.getMaximum(Calendar.MINUTE)); 
			hi.set(Calendar.SECOND, hi.getMaximum(Calendar.SECOND)); 
			hi.set(Calendar.MILLISECOND, hi.getMaximum(Calendar.MILLISECOND));
			
			criteria.add(Restrictions.between("fechaCreacion", low.getTime(), hi.getTime()));
		}
		
		// FOLIO ORIGEN *****
		/* if(StringUtils.isNotEmpty(criterios.getReferenciaPaquete())) {
			criteria.add(Restrictions.like("referenciaOperacion", criterios.getReferenciaOperacion(), MatchMode.START));
		}/*     */
		
		// ESTADO INSTRUCCION
		if(criterios.getEstadoInstruccion()!=null && criterios.getEstadoInstruccion().getIdEstadoInstruccion() > DaliConstants.VALOR_COMBO_TODOS) {
			criteria.add(Restrictions.eq("estado.idEstadoInstruccion", BigInteger.valueOf(criterios.getEstadoInstruccion().getIdEstadoInstruccion()) ));
		}
		
		// REFERENCIA OPERACION
		if(StringUtils.isNotBlank(criterios.getReferenciaOperacion()) && StringUtils.isNumeric(criterios.getReferenciaOperacion())){
			criteria.add(Restrictions.like("referenciaOperacion", criterios.getReferenciaOperacion(), MatchMode.START));
		}
		
		// IMPORTE
		if(StringUtils.isNotBlank(criterios.getMonto())) {
			try {
				
				criteria.add(Restrictions.eq("importeTraspaso", new BigDecimal (criterios.getMonto()) ));
			}catch(NumberFormatException e) {
				logger.warn(e.getMessage());
			}
		}
		// ROLES  
		if(tieneAmbosAgentes(criterios)) {
			Criterion emisorParticipanteRestriction = Restrictions.eq("emisor.idInstitucion", 
					BigInteger.valueOf(criterios.getInstitucionParticipante().getId()) );
			Criterion emisortContraparteRestriction = Restrictions.eq("emisor.idInstitucion", 
					BigInteger.valueOf(criterios.getInstitucionContraparte().getId()) );
			Criterion receptorParticipanteRestriction = Restrictions.eq("receptor.idInstitucion", 
					BigInteger.valueOf(criterios.getInstitucionParticipante().getId()) );
			Criterion receptorContraparteRestriction = Restrictions.eq("receptor.idInstitucion", 
					BigInteger.valueOf(criterios.getInstitucionContraparte().getId()) );
			
			if(isRolTraspasante(criterios) ) {
				criteria.add(Restrictions.and(emisorParticipanteRestriction, receptorContraparteRestriction));
			}else if(isRolReceptor(criterios)) {
				criteria.add(Restrictions.and(receptorParticipanteRestriction, emisortContraparteRestriction));
			}else { // ambos
				Criterion and1 = Restrictions.and(emisorParticipanteRestriction, receptorContraparteRestriction);
				Criterion and2 = Restrictions.and(receptorParticipanteRestriction, emisortContraparteRestriction);
				
				criteria.add(Restrictions.or(and1, and2));
			}
		}else {
			CuentaDTO agente = obtenerAgente(criterios); 
			if(agente != null ){
				Criterion traspasanteRestriction = Restrictions.eq("emisor.idInstitucion", 
						BigInteger.valueOf(agente.getInstitucion().getId()) );
				Criterion receptorRestriction = Restrictions.eq("receptor.idInstitucion", 
						BigInteger.valueOf(agente.getInstitucion().getId()) );
				
				if(isRolTraspasante(criterios)) {
					criteria.add(traspasanteRestriction);
				}else if(isRolReceptor(criterios)) {
					criteria.add(receptorRestriction);
				}else { // ambos
					criteria.add(Restrictions.or(traspasanteRestriction, receptorRestriction));
				}
			}
		}
		
	}
	
	private boolean tieneAmbosAgentes(CriterioEstatusOpEfectivoDTO criterio) {
		return criterio.getInstitucionContraparte() != null && criterio.getInstitucionParticipante() != null;
	}
	
	private boolean isRolTraspasante(CriterioEstatusOpEfectivoDTO criterio) {
		return criterio.getRol() == 1;
	}

	private boolean isRolReceptor(CriterioEstatusOpEfectivoDTO criterio) {
		return criterio.getRol() == 2;
	}

	private CuentaDTO obtenerAgente(CriterioEstatusOpEfectivoDTO criterio) {
		if (criterio.getInstitucionParticipante() != null ) {
			return criterio.getCuentaParticipante();
		} else if (criterio.getInstitucionContraparte() != null ) {
			return criterio.getCuentaContraparte();
		}
		return null;
	}
}
