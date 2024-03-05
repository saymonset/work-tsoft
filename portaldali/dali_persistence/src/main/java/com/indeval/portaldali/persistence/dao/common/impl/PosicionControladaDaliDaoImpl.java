/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.dao.common.impl;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.BigIntegerType;
import org.hibernate.type.DateType;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.util.Assert;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portaldali.persistence.dao.common.PosicionControladaDaliDao;
import com.indeval.portaldali.persistence.dao.common.util.QueryUtil;
import com.indeval.portaldali.persistence.model.Cupon;
import com.indeval.portaldali.persistence.model.PosicionControlada;
import com.indeval.portaldali.persistence.util.Constantes;
import com.indeval.portaldali.persistence.util.DateUtil;
import com.indeval.portaldali.persistence.vo.PageVO;
import com.indeval.portaldali.persistence.vo.TPosicionControladaParamsPersistence;
import com.indeval.portaldali.persistence.vo.TPosicionNombradaParamsPersistence;


/**
 * Objeto de acceso a datos de PosicionControlada
 *
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class PosicionControladaDaliDaoImpl extends BaseDaoHibernateImpl implements PosicionControladaDaliDao,
        Constantes {

    /** Objeto de loggeo */
    private static final Logger log = LoggerFactory.getLogger(PosicionControladaDaliDaoImpl.class);

    /*
     * NOTAS DE DESARROLLO
     *
     * select e.tv as tv, e.emisora as emisora, e.serie as serie, e.cupon as
     * cupon, sum(v.saldo_disponible) as sumaSaldoDisponible from
     * catalogo..emisiones e, bddinero..valores v where cupon_cortado='F' and
     * v.saldo_disponible > 0 and e.tv=v.tv and e.emisora=v.emisora and
     * e.serie=v.serie and e.cupon=v.cupon and v.id_inst ='02' and v.folio_inst
     * ='061' and fecha_vencimiento = '24-may-2007' group by e.tv, e.emisora,
     * e.serie, e.cupon
     */
    
    
    @SuppressWarnings("unchecked")
	public PageVO getVencimientosPendientesPorInstitucionFechaVencimientoAgrupadoPorEmision(final TPosicionControladaParamsPersistence tPosicionControladaParamsPersistence) {
    	this.validaTPosicionControladaParamsPersistence(tPosicionControladaParamsPersistence);

        Assert.notNull(tPosicionControladaParamsPersistence.getFechaVencimiento(),
                       "La fecha de vencimiento esta nula");

        List listaTPosicionNombrada = null;

        Long countRegistros = (Long) getHibernateTemplate().execute(new HibernateCallback() {

                public Object doInHibernate(Session session)
                                     throws HibernateException, SQLException {
                	
                	StringBuffer queryString = new StringBuffer();
                	List<Type> tipos = new ArrayList<Type>();
                	List<Object> params = new ArrayList<Object>();
                	
                	queryString.append(" SELECT COUNT(*) FROM " + Cupon.class.getName() + " c WHERE c.idCupon IN ( ");
                	queryString.append("	SELECT pc.cupon.idCupon FROM " + PosicionControlada.class.getName() + " pc ");
                	crearCriteriosConsultaPosicionesControladas(queryString, params, tipos, tPosicionControladaParamsPersistence);
                	queryString.append("	GROUP BY pc.cupon.idCupon ");
                	queryString.append(" )");
                    
                    return  session.createQuery(queryString.toString()).setParameters(params.toArray(), tipos.toArray(new Type[]{})).uniqueResult();

                }
                

            });

        log.debug("Registros encontrados: [" + countRegistros + "]");

        if (countRegistros.intValue() > 0) {

            listaTPosicionNombrada = (List) getHibernateTemplate().execute(new HibernateCallback() {

                    public Object doInHibernate(Session session)
                                         throws HibernateException, SQLException {

                    	StringBuffer queryString = new StringBuffer();
                    	List<Type> tipos = new ArrayList<Type>();
                    	List<Object> params = new ArrayList<Object>();

                    	queryString.append("SELECT c, ( " );
                    	queryString.append("	SELECT SUM(pc.posicion) FROM " + PosicionControlada.class.getName() + " pc ");
                    	queryString.append("	WHERE pc.cupon.idCupon = c.idCupon " );
                    	if(!tPosicionControladaParamsPersistence.getFolioInstitucion().equals("000")){
                    		queryString.append("		AND pc.cuentaControlada.institucion.folioInstitucion = '" + 
                    			StringUtils.trimToEmpty(tPosicionControladaParamsPersistence.getFolioInstitucion()) + "' " );
                    	}
                    	if(!tPosicionControladaParamsPersistence.getIdInstitucion().equals("00")) {
                    		queryString.append("		AND pc.cuentaControlada.institucion.tipoInstitucion.claveTipoInstitucion = '" + 
                    			StringUtils.trimToEmpty(tPosicionControladaParamsPersistence.getIdInstitucion()) + "' " );
                    	}
                    	queryString.append(" ) " );
                    	queryString.append("FROM " + Cupon.class.getName() + " c ");
                    	queryString.append("	left outer join fetch c.emision ");
//                    	queryString.append("	join fetch c.emision.instrumento ");
//                    	queryString.append("	join fetch c.emision.emisora ");
                    	queryString.append("WHERE ");
                    	queryString.append("	c.idCupon IN ( ");
                    	queryString.append("		SELECT pc.cupon.idCupon FROM " + PosicionControlada.class.getName() + " pc ");
                    	crearCriteriosConsultaPosicionesControladas(queryString, params, tipos, tPosicionControladaParamsPersistence);
                    	queryString.append("		GROUP BY pc.cupon.idCupon ");
                    	queryString.append(" )");
                    	queryString.append("		ORDER BY c.emision.instrumento.claveTipoValor,  c.emision.emisora.descEmisora, c.emision.serie, c.idCupon");
                    	
                        
                    	Query query = session.createQuery(queryString.toString());
                    	
                        return  query.setParameters(params.toArray(), tipos.toArray(new Type[]{})).list();

                    }

                });

        }

        PageVO pageReturn = new PageVO();
        pageReturn.setTotalRegistros(countRegistros.intValue());
        pageReturn.setRegistros(listaTPosicionNombrada);

        return pageReturn;
    }
    

    /**
     * Crea los criterios para realizar la consulta sobre las posiciones controladas.
     * 
     * @param queryString el StringBuffer en el cual se construye el query.
     * @param params la lista que contiene los parámetros para realizar la consulta.
     * @param tipos la lista que contiene los tipos de parámetros para realizar la consulta.
     * @param posicionControladaParamsPersistence el objeto con los criterios necesarios para realizar la consulta.
     */
	private void crearCriteriosConsultaPosicionesControladas(
			StringBuffer queryString,
			List<Object> params,
			List<Type> tipos,
			TPosicionControladaParamsPersistence posicionControladaParamsPersistence) {
		
		QueryUtil.agregarCondicion(queryString, params);
		queryString.append(" pc.cupon.estadoCupon.idEstatusCupon = ? ");
		params.add(VIGENTE);
		tipos.add(new BigIntegerType());
		// Saldo mayor a cero
		QueryUtil.agregarCondicion(queryString, params);
		queryString.append(" pc.posicion > 0 ");
		
		if(!posicionControladaParamsPersistence.getIdInstitucion().equals("00")) {
			QueryUtil.agregarCondicion(queryString, params);
			queryString.append(" pc.cuentaControlada.institucion.tipoInstitucion.claveTipoInstitucion = ? ");
			params.add(StringUtils.trimToEmpty(posicionControladaParamsPersistence.getIdInstitucion()));
			tipos.add(new StringType());
		}
		
		if(!posicionControladaParamsPersistence.getFolioInstitucion().equals("000")) {
			QueryUtil.agregarCondicion(queryString, params);
			queryString.append(" pc.cuentaControlada.institucion.folioInstitucion = ? ");
			params.add(StringUtils.trimToEmpty(posicionControladaParamsPersistence.getFolioInstitucion()));
			tipos.add(new StringType());
		}
		
		if(StringUtils.isNotBlank(posicionControladaParamsPersistence.getFolioInstitucion())) {
			QueryUtil.agregarCondicion(queryString, params);
			queryString.append(" trunc(pc.cupon.emision.fechaVencimiento) = trunc(?) ");
			params.add(posicionControladaParamsPersistence.getFechaVencimiento());
			tipos.add(new DateType());
		}

	}
    
    /**
     * 
     * @see com.indeval.portaldali.persistence.dao.common.PosicionControladaDaliDao#getVencimientosPendientesByInstitucionFechaVencimiento(TPosicionControladaParamsPersistence)
     */
    public PageVO getVencimientosPendientesByInstitucionFechaVencimiento(final TPosicionControladaParamsPersistence tPosicionControladaParamsPersistence) {

        log.info("Entrando a .getVencimientosPendientesByInstitucionFechaVencimiento()");

        this.validaTPosicionControladaParamsPersistence(tPosicionControladaParamsPersistence);

        Assert.notNull(tPosicionControladaParamsPersistence.getFechaVencimiento(),
                       "La fecha de vencimiento esta nula");

        final PageVO pagAux = (tPosicionControladaParamsPersistence.getPageVO() != null)
                              ? tPosicionControladaParamsPersistence.getPageVO() : new PageVO();

        List listaTPosicionNombrada = null;

        Integer countRegistros = (Integer) getHibernateTemplate().execute(new HibernateCallback() {

                @SuppressWarnings("unchecked")
				public Object doInHibernate(Session session)
                                     throws HibernateException, SQLException {
                	
                	
                    Criteria criteria = session.createCriteria(PosicionControlada.class)
                                               .createAlias("cuentaControlada", "ccc")
                                               .createAlias("ccc.institucion", "i")
                                               .createAlias("i.tipoInstitucion", "ti")
                                               .createAlias("cupon", "cc")
                                               .createAlias("cc.estadoCupon", "ec")
                                               .createAlias("cc.emision", "e")
                                               .createAlias("e.instrumento", "ei")
                                               .createAlias("ei.mercado", "m")
                                               .createAlias("e.emisora", "ee")
                                               .createAlias("boveda", "b");

                    criteria.add(Restrictions.between("e.fechaVencimiento", 
                                    DateUtil.preparaFechaConExtremoEnSegundos(
                                            tPosicionControladaParamsPersistence.getFechaVencimiento(), true), 
                                            DateUtil.preparaFechaConExtremoEnSegundos(
                                                    tPosicionControladaParamsPersistence.getFechaVencimiento(), false)));
        
                    if(!tPosicionControladaParamsPersistence.getIdInstitucion().equals("00")) {
                        criteria.add(Restrictions.eq("ti.claveTipoInstitucion",
                                tPosicionControladaParamsPersistence.getIdInstitucion()
                                                                    .trim()));
                    }
                    
                    if(!tPosicionControladaParamsPersistence.getFolioInstitucion().equals("000")) {
                        criteria.add(Restrictions.eq("i.folioInstitucion", tPosicionControladaParamsPersistence
                                .getFolioInstitucion().trim()));
                    }
        
                    if(tPosicionControladaParamsPersistence.getIdBoveda() != null 
                            && BigInteger.valueOf(-1).intValue() != tPosicionControladaParamsPersistence.getIdBoveda().intValue()){
                        criteria.add(Restrictions.eq("b.idBoveda",
                                tPosicionControladaParamsPersistence.getIdBoveda()));                        
                    }

                    if ((tPosicionControladaParamsPersistence.getTiposDeValor() != null) &&
                            StringUtils.isNotBlank(tPosicionControladaParamsPersistence.getTiposDeValor()[0])) {

                        criteria.add(Restrictions.eq("ei.claveTipoValor",
                                                     tPosicionControladaParamsPersistence.getTiposDeValor()[0].trim()));

                    }

                    if (StringUtils.isNotBlank(tPosicionControladaParamsPersistence.getEmisora())) {

                        criteria.add(Restrictions.eq("ee.clavePizarra",
                                                     tPosicionControladaParamsPersistence.getEmisora()
                                                                                         .trim()));

                    }

                    if (StringUtils.isNotBlank(tPosicionControladaParamsPersistence.getSerie())) {

                        criteria.add(Restrictions.eq("e.serie",
                                                     tPosicionControladaParamsPersistence.getSerie()
                                                                                         .trim()));

                    }
                    
                    criteria.add(Restrictions.eq("ec.idEstatusCupon", VIGENTE));
                    
                    criteria.setProjection(Projections.rowCount());
                    
                    List<Integer> resultados = criteria.list();
                    Integer resultado = 0;
                    
                    if(resultados != null && !resultados.isEmpty()) {
                    	resultado = resultados.get(0);
                    }
                    
                    return resultado;

                }
                
            });

        log.debug("Registros encontrados: [" + countRegistros + "]");

        if (countRegistros.intValue() > 0) {

            listaTPosicionNombrada = (List) getHibernateTemplate().execute(new HibernateCallback() {

                    public Object doInHibernate(Session session)
                                         throws HibernateException, SQLException {

                        Criteria criteria = session.createCriteria(PosicionControlada.class)
                                                  .createAlias("cuentaControlada", "ccc")
	                                               .createAlias("ccc.institucion", "i")
	                                               .createAlias("i.tipoInstitucion", "ti")
	                                               .createAlias("cupon", "cc")
	                                               .createAlias("cc.estadoCupon", "ec")
	                                               .createAlias("cc.emision", "e")
	                                               .createAlias("e.instrumento", "ei")
	                                               .createAlias("ei.mercado", "m")
	                                               .createAlias("e.emisora", "ee")
	                                               .createAlias("boveda", "b");

                        criteria.add(Restrictions.between("e.fechaVencimiento", 
					                   DateUtil.preparaFechaConExtremoEnSegundos(
					                           tPosicionControladaParamsPersistence.getFechaVencimiento(), true), 
					                           DateUtil.preparaFechaConExtremoEnSegundos(
					                                   tPosicionControladaParamsPersistence.getFechaVencimiento(), false)));
					
                        if(!tPosicionControladaParamsPersistence.getIdInstitucion().equals("00")) {
                            criteria.add(Restrictions.eq("ti.claveTipoInstitucion",
                                    tPosicionControladaParamsPersistence.getIdInstitucion()
                                                                        .trim()));
                        }

                        if(!tPosicionControladaParamsPersistence.getFolioInstitucion().equals("000")) {
                            criteria.add(Restrictions.eq("i.folioInstitucion", tPosicionControladaParamsPersistence
                                    .getFolioInstitucion().trim()));
                        }

                        if(tPosicionControladaParamsPersistence.getIdBoveda() != null 
					           && BigInteger.valueOf(-1).intValue() != tPosicionControladaParamsPersistence.getIdBoveda().intValue()){
					       criteria.add(Restrictions.eq("b.idBoveda",
					               tPosicionControladaParamsPersistence.getIdBoveda()));                        
					   }
					
					   if ((tPosicionControladaParamsPersistence.getTiposDeValor() != null) &&
					           StringUtils.isNotBlank(tPosicionControladaParamsPersistence.getTiposDeValor()[0])) {
					
					       criteria.add(Restrictions.eq("ei.claveTipoValor",
					                                    tPosicionControladaParamsPersistence.getTiposDeValor()[0].trim()));
					
					   }
					
					   if (StringUtils.isNotBlank(tPosicionControladaParamsPersistence.getEmisora())) {
					
					       criteria.add(Restrictions.eq("ee.clavePizarra",
					                                    tPosicionControladaParamsPersistence.getEmisora()
					                                                                        .trim()));
					
					   }
					
					   if (StringUtils.isNotBlank(tPosicionControladaParamsPersistence.getSerie())) {
					
					       criteria.add(Restrictions.eq("e.serie",
					                                    tPosicionControladaParamsPersistence.getSerie()
					                                                                        .trim()));
					
					   }
					
					   criteria.add(Restrictions.eq("ec.idEstatusCupon", VIGENTE));

                        // ordenamiento ascendente por tv, emisora, serie
                        criteria.addOrder(Order.asc("ei.claveTipoValor"))
                                .addOrder(Order.asc("ee.descEmisora")).addOrder(Order.asc("e.serie"))
                                .addOrder(Order.asc("cc.claveCupon"));

                        if (tPosicionControladaParamsPersistence.isTest()) {

                            criteria.setMaxResults(TPosicionNombradaParamsPersistence.LIMITE);

                        } else if (pagAux.getRegistrosXPag().intValue() > 0) {

                            criteria.setFirstResult(pagAux.getOffset().intValue());
                            criteria.setMaxResults(pagAux.getRegistrosXPag().intValue());

                        }

                        return criteria.list();

                    }

                });

        }

        PageVO pageReturn = new PageVO();
        pageReturn.setTotalRegistros(countRegistros);
        pageReturn.setRegistros(listaTPosicionNombrada);

        return pageReturn;

    }

    /**
     * Valida que el TPosicionNombradaParamsPersistence no sea nulo y que al menos cuente
     * con el id y el folio de la institucion
     *
     * @param tPosicionControladaParamsPersistence
     */
    private void validaTPosicionControladaParamsPersistence(TPosicionControladaParamsPersistence tPosicionControladaParamsPersistence) {

        log.info("Entrando a PosicionControladaDaliDaoImpl.validaTPosicionControladaParamsPersistence()");

        Assert.notNull(tPosicionControladaParamsPersistence,
                       "El objeto de parametros recibido esta null");
        Assert.isTrue(StringUtils.isNotBlank(tPosicionControladaParamsPersistence.getIdInstitucion()),
                      "El id de la institucion esta null");
        Assert.isTrue(StringUtils.isNotBlank(tPosicionControladaParamsPersistence.getFolioInstitucion()),
                      "El folio de la institucion esta null");

    }

	public List<Object[]> getVencsPendientesByInstitucionFechaVencimiento(
			final TPosicionControladaParamsPersistence posContrParams) {
		log.debug("Entrando a getVencimientosPendientesByInstitucionFechaVencimiento() ");
		Assert.notNull(posContrParams.getFechaVencimiento(),
				"La fecha de vencimiento esta nula");
		List<Object[]> posicionCtrlList = null; // NOPMD
		Integer countRegistros = (Integer) getHibernateTemplate().execute(
				new PosCtrlRegsCountHibernateCallback(posContrParams));
		if (countRegistros != null && countRegistros.intValue() > 0) {
			@SuppressWarnings("unchecked")
			final List<Object[]> posCtrlListAux = (List<Object[]>) getHibernateTemplate()
					.execute(
							new PosicionControladaRegistrosHibernateCallback(
									posContrParams));
			posicionCtrlList = posCtrlListAux;
		} else {
			countRegistros = 0;
		}
		if (posicionCtrlList != null) {
			log.debug("Registros encontrados: [" + posicionCtrlList.size()
					+ "]");
		}
		// final PageVO pageReturn = new PageVO();
		// pageReturn.setTotalRegistros(countRegistros.intValue());
		// pageReturn.setRegistros(posicionCtrlList);

		return posicionCtrlList;

	}

	private class PosCtrlRegsCountHibernateCallback implements
			HibernateCallback {
		private final TPosicionControladaParamsPersistence posCtrlParams;// NOPMD

		public PosCtrlRegsCountHibernateCallback(
				final TPosicionControladaParamsPersistence posCtrlParams) {
			this.posCtrlParams = posCtrlParams;
		}

		public Integer doInHibernate(final Session session)
				throws HibernateException, SQLException {
			final Criteria criteria = getCriteria(session, posCtrlParams);
			criteria.setProjection(Projections.projectionList()
					.add(projGroupBy).add(Projections.rowCount()));
			if (log.isTraceEnabled()) {
				log.trace("criteria count: " + criteria);
			}
			@SuppressWarnings({ "rawtypes" })
			final List resultados = criteria.list();
			if (log.isTraceEnabled()) {
				log.trace("resultados: " + resultados);
			}

			Integer count = 1; // NOPMD
//			if (resultados != null) {
//				count = (Integer)resultados.get(4); TODO
//			}

			return count;
		}

	}

	private class PosicionControladaRegistrosHibernateCallback implements
			HibernateCallback {
		private final TPosicionControladaParamsPersistence posCtrlParams;// NOPMD

		public PosicionControladaRegistrosHibernateCallback(
				final TPosicionControladaParamsPersistence posCtrlParams) {
			this.posCtrlParams = posCtrlParams;
		}

		public List<PosicionControlada> doInHibernate(final Session session)
				throws HibernateException, SQLException {
			final Criteria criteria = getCriteria(session, posCtrlParams);
			// ordenamiento ascendente por tv, emisora, serie
			// criteria.addOrder(Order.asc("ei.claveTipoValor"))
			// .addOrder(Order.asc("ee.descEmisora"))
			// .addOrder(Order.asc("e.serie"))
			// .addOrder(Order.asc("cc.claveCupon"));
			criteria.setProjection(projGroupBy);
			final PageVO pagAux = (posCtrlParams.getPageVO() == null) ? new PageVO() // NOPMD
					: posCtrlParams.getPageVO();
			if (posCtrlParams.isTest()) {
				criteria.setMaxResults(TPosicionNombradaParamsPersistence.LIMITE);
			} else if (pagAux.getRegistrosXPag().intValue() > 0) {
				criteria.setFirstResult(pagAux.getOffset().intValue());
				criteria.setMaxResults(pagAux.getRegistrosXPag().intValue());
			}
			if (log.isTraceEnabled()) {
				log.trace("criteria registros: " + criteria);
			}

			@SuppressWarnings("unchecked")
			final List<PosicionControlada> result = criteria.list();
			if (log.isTraceEnabled()) {
				log.trace("result: " + result);
			}
			return result;

		}

	}

	private Projection projGroupBy = Projections.projectionList()
			.add(Projections.sum("posCtrl.posicion"))
			.add(Projections.groupProperty("ei.claveTipoValor"))
			.add(Projections.groupProperty("ee.clavePizarra"))
			.add(Projections.groupProperty("e.serie"))
			.add(Projections.groupProperty("b.nombreCorto"))
			//.add(Projections.groupProperty("e.boveda.nombreCorto"))
			.add(Projections.groupProperty("cc.claveCupon"))
			;

	private Criteria getCriteria(final Session session,
			final TPosicionControladaParamsPersistence posCtrlParams) {
		final Criteria criteria = session
				.createCriteria(PosicionControlada.class, "posCtrl")
				.createAlias("cuentaControlada", "ccc")
				.createAlias("ccc.institucion", "i")
				.createAlias("i.tipoInstitucion", "ti")
				.createAlias("cupon", "cc").createAlias("cc.estadoCupon", "ec")
				.createAlias("cc.emision", "e")
				.createAlias("e.instrumento", "ei")
				.createAlias("ei.mercado", "m").createAlias("e.emisora", "ee")
				.createAlias("boveda", "b");

        criteria.add(Restrictions.between(
						"e.fechaVencimiento",
						DateUtil.preparaFechaConExtremoEnSegundos(
								posCtrlParams.getFechaVencimiento(), true),
						DateUtil.preparaFechaConExtremoEnSegundos(
								posCtrlParams.getFechaVencimiento(), false)))
				// CGM - Debido a que realizara sumas, descarto desde ya a
				// los valores que no afectan a la suma:
				.add(Restrictions.ne("posCtrl.posicion", BigInteger.ZERO))
				.add(Restrictions.eq("ec.idEstatusCupon", Constantes.VIGENTE));
        if (!posCtrlParams.getIdInstitucion().equals("00")) {
            criteria.add(Restrictions.eq("ti.claveTipoInstitucion",
                    posCtrlParams.getIdInstitucion().trim()));
        }
        if (!posCtrlParams.getFolioInstitucion().equals("000")) {
            criteria.add(Restrictions.eq("i.folioInstitucion", posCtrlParams
                    .getFolioInstitucion().trim()));
        }
		if (posCtrlParams.getIdBoveda() != null
                && BigInteger.valueOf(-1).intValue() != posCtrlParams
                        .getIdBoveda().intValue()) {
			criteria.add(Restrictions.eq("b.idBoveda",
					posCtrlParams.getIdBoveda()));
		}

		return criteria;
	}

}
