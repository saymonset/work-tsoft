/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.dao.common.impl;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.BigIntegerType;
import org.hibernate.type.StringType;
import org.hibernate.type.TimestampType;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.util.Assert;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portaldali.middleware.dto.PosicionDTO;
import com.indeval.portaldali.middleware.dto.TipoCuentaDTO;
import com.indeval.portaldali.middleware.dto.TipoNaturalezaDTO;
import com.indeval.portaldali.middleware.dto.TipoTenenciaDTO;
import com.indeval.portaldali.middleware.dto.util.DTOAssembler;
import com.indeval.portaldali.middleware.services.common.constants.TipoCuentaConstants;
import com.indeval.portaldali.middleware.services.common.constants.TipoTenenciaConstants;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import com.indeval.portaldali.persistence.dao.common.PosicionNombradaDaliDao;
import com.indeval.portaldali.persistence.dao.common.TipoTenenciaDAO;
import com.indeval.portaldali.persistence.model.EstadoPosicionNombrada;
import com.indeval.portaldali.persistence.model.PosicionNombrada;
import com.indeval.portaldali.persistence.model.PosicionNombradaHistorico;
import com.indeval.portaldali.persistence.model.TipoCuenta;
import com.indeval.portaldali.persistence.util.Constantes;
import com.indeval.portaldali.persistence.util.DateUtil;
import com.indeval.portaldali.persistence.vo.PageVO;
import com.indeval.portaldali.persistence.vo.TPosicionNombradaParamsPersistence;

/**
 * Objeto de acceso a datos de PosicionNombrada
 * 
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class PosicionNombradaDaliDaoImpl extends BaseDaoHibernateImpl implements PosicionNombradaDaliDao,
        Constantes {

    List emisionExtranjera; // Lista inyectada para validar emisiones extranjeras

    /** Objeto de loggeo */
    private static final Logger logger = LoggerFactory.getLogger(PosicionNombradaDaliDaoImpl.class);

    /**
     * @see com.indeval.portaldali.persistence.dao.common.PosicionNombradaDaliDao#getDetalleEmisiones(TPosicionNombradaParamsPersistence)
     */
    public PageVO getDetalleEmisiones(
            final TPosicionNombradaParamsPersistence tPosicionNombradaParamsPersistence) {

        logger.info("Entrando a PosicionNombradaDaliDaoImpl.getDetalleEmisiones()");

        final PageVO pagAux = tPosicionNombradaParamsPersistence.getPageVO() != null ? 
                tPosicionNombradaParamsPersistence.getPageVO() : new PageVO();

        List listaTPosicionNombrada = null;

        Integer countRegistros = (Integer) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {

                Criteria criteria = session.createCriteria(PosicionNombrada.class)
                    .setProjection(Projections.rowCount())
                    .createAlias("emision", "e")
                    .createAlias("e.instrumento", "ei")
                    .createAlias("e.emisora", "ee")
                    .createAlias("e.cupon", "cc")
                    .createAlias("cc.estadoCupon", "ec")
                    .createAlias("boveda", "b");

                if (tPosicionNombradaParamsPersistence.getFechaInicio() != null
                        && tPosicionNombradaParamsPersistence.getFechaInicio() != null) {
                    criteria.add(Restrictions.between("e.fechaEmision",
                            tPosicionNombradaParamsPersistence.getFechaInicio(),
                            tPosicionNombradaParamsPersistence.getFechaFin()));
                }

                if (StringUtils.isNotBlank(tPosicionNombradaParamsPersistence
                        .getEmisionExtranjera())
                        && !TODOS.equalsIgnoreCase(tPosicionNombradaParamsPersistence
                                .getEmisionExtranjera())) {
                    criteria.add(Restrictions.eq("e.emisionExtranjera",
                            tPosicionNombradaParamsPersistence.getEmisionExtranjera().trim()));
                }
                else {
                    criteria.add(Restrictions.in("e.emisionExtranjera", getEmisionExtranjera()));
                }

                if (tPosicionNombradaParamsPersistence.getTiposDeValor() != null
                        && StringUtils.isNotBlank(tPosicionNombradaParamsPersistence
                                .getTiposDeValor()[0])) {
                    criteria.add(Restrictions.eq("ei.claveTipoValor",
                            tPosicionNombradaParamsPersistence.getTiposDeValor()[0].trim()));
                }

                if (StringUtils.isNotBlank(tPosicionNombradaParamsPersistence.getEmisora())) {
                    criteria.add(Restrictions.eq("ee.descEmisora",
                            tPosicionNombradaParamsPersistence.getEmisora().trim()));
                }

                if (StringUtils.isNotBlank(tPosicionNombradaParamsPersistence.getSerie())) {
                    criteria.add(Restrictions.eq("e.serie", tPosicionNombradaParamsPersistence
                            .getSerie().trim()));
                }

                if (StringUtils.isNotBlank(tPosicionNombradaParamsPersistence.getIsin())) {
                    criteria.add(Restrictions.eq("e.isin", 
                            tPosicionNombradaParamsPersistence.getIsin().trim()));
                }
                
                /* Filtro de Boveda */
                if(tPosicionNombradaParamsPersistence.getIdBoveda()!=null) {
                    criteria.add(Restrictions.eq("b.idBoveda", 
                            tPosicionNombradaParamsPersistence.getIdBoveda()));    
                }
                
                criteria.add(Restrictions.eq("ec.idEstatusCupon", VIGENTE));
                
                return ((Integer) ((criteria.list() != null && !criteria.list().isEmpty()) ? 
                        criteria.list().get(0) : new Integer(0))).intValue();
            }

        });

        logger.debug("Registros encontrados: [" + countRegistros + "]");

        if (countRegistros.intValue() > 0) {

            listaTPosicionNombrada = (List) getHibernateTemplate().execute(new HibernateCallback() {

                public Object doInHibernate(Session session) throws HibernateException,
                        SQLException {

                    Criteria criteria = session.createCriteria(PosicionNombrada.class)
                        .createAlias("emision", "e")
                        .createAlias("e.instrumento", "ei")
                        .createAlias("e.emisora", "ee")
                        .createAlias("e.cupon", "cc")
                        .createAlias("cc.estadoCupon", "ec")
                        .createAlias("boveda", "b");

                    if (tPosicionNombradaParamsPersistence.getFechaInicio() != null
                            && tPosicionNombradaParamsPersistence.getFechaInicio() != null) {
                        criteria.add(Restrictions.between("e.fechaEmision",
                                tPosicionNombradaParamsPersistence.getFechaInicio(),
                                tPosicionNombradaParamsPersistence.getFechaFin()));
                    }

                    if (StringUtils.isNotBlank(tPosicionNombradaParamsPersistence
                            .getEmisionExtranjera())
                            && !TODOS.equalsIgnoreCase(tPosicionNombradaParamsPersistence
                                    .getEmisionExtranjera())) {
                        criteria.add(Restrictions.eq("e.emisionExtranjera",
                                tPosicionNombradaParamsPersistence.getEmisionExtranjera().trim()));
                    }
                    else {
                        criteria
                                .add(Restrictions.in("e.emisionExtranjera", getEmisionExtranjera()));
                    }

                    if (tPosicionNombradaParamsPersistence.getTiposDeValor() != null
                            && StringUtils.isNotBlank(tPosicionNombradaParamsPersistence
                                    .getTiposDeValor()[0])) {
                        criteria.add(Restrictions.eq("ei.claveTipoValor",
                                tPosicionNombradaParamsPersistence.getTiposDeValor()[0].trim()));
                    }

                    if (StringUtils.isNotBlank(tPosicionNombradaParamsPersistence.getEmisora())) {
                        criteria.add(Restrictions.eq("ee.descEmisora",
                                tPosicionNombradaParamsPersistence.getEmisora().trim()));
                    }

                    if (StringUtils.isNotBlank(tPosicionNombradaParamsPersistence.getSerie())) {
                        criteria.add(Restrictions.eq("e.serie", tPosicionNombradaParamsPersistence
                                .getSerie().trim()));
                    }

                    if (StringUtils.isNotBlank(tPosicionNombradaParamsPersistence.getIsin())) {
                        criteria.add(Restrictions.eq("e.isin", tPosicionNombradaParamsPersistence
                                .getIsin().trim()));
                    }

                    /* Filtro de Boveda */
                    if(tPosicionNombradaParamsPersistence.getIdBoveda()!=null) {
                        criteria.add(Restrictions.eq("b.idBoveda", 
                                tPosicionNombradaParamsPersistence.getIdBoveda()));    
                    }
                    
                    criteria.add(Restrictions.eq("ec.idEstatusCupon", VIGENTE));

                    // ordenamiento ascendente por tv, emisora, serie
                    criteria.addOrder(Order.asc("ei.claveTipoValor")).addOrder(
                            Order.asc("ee.descEmisora")).addOrder(Order.asc("e.serie")).addOrder(
                            Order.asc("ec.idEstatusCupon"));

                    if (tPosicionNombradaParamsPersistence.isTest()) {
                        criteria.setMaxResults(TPosicionNombradaParamsPersistence.LIMITE);
                    }
                    else if (pagAux.getRegistrosXPag().intValue() > 0) {
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
     * @see com.indeval.portaldali.persistence.dao.common.PosicionNombradaDaliDao#getEmisoraByTV(java.lang.String)
     */
    public List getEmisoraByTV(final String tv) {

        logger.info("Entrando a PosicionNombradaDaliDaoImpl.getEmisoraByTV()");

        Assert.isTrue(StringUtils.isNotBlank(tv), "Debe seleccionar una tv valida");

        List<String> listaEmisoras = null;
        List listaTPosicionNombrada = (List) getHibernateTemplate().execute(
                new HibernateCallback() {

                    public Object doInHibernate(Session session) throws HibernateException,
                            SQLException {
                        Criteria criteria = session.createCriteria(PosicionNombrada.class)
                                .createAlias("emision", "e").createAlias("e.instrumento", "ei")
                                .add(Restrictions.eq("ei.claveTipoValor", tv));

                        // ordenamiento ascendente por tv
                        criteria.addOrder(Order.asc("ei.claveTipoValor"));

                        return criteria.list();
                    }

                });

        if (listaTPosicionNombrada != null && !listaTPosicionNombrada.isEmpty()) {

            logger.debug("Se extraen las emisoras");

            Set<String> setEmisoras = new HashSet<String>();
            for (Iterator iter = listaTPosicionNombrada.iterator(); iter.hasNext();) {
                PosicionNombrada element = (PosicionNombrada) iter.next();
                setEmisoras.add(element.getCupon().getEmision().getEmisora().getDescEmisora());
            }
            listaEmisoras = new ArrayList<String>();
            listaEmisoras.addAll(setEmisoras);
        }

        return listaEmisoras;

    }

    /*
     * NOTAS DE DESARROLLO
     * 
     * select v.tv,v.emisora,v.serie,v.cupon,v.saldo_disponible,
     * e.fecha_vencimiento, DATEDIFF(day ,getdate(),e.fecha_vencimiento) as
     * dias_vigente, e.isin,i.mercado, e.precio_vector from bddinero..valores v
     * {o bdcaptal..valores v} , catalogo..emisiones e, catalogo..instrumentos i
     * where v.id_inst='02' and v.folio_inst='022' and v.cuenta='8322' and
     * v.tv='F' and e.cupon_cortado = 'F' and i.mercado in ('PB','PG') and
     * v.tv=e.tv and v.emisora=e.emisora and v.serie=e.serie and v.cupon=e.cupon
     * and i.tv=v.tv and v.saldo_disponible >0 order by e.tv, e.emisora,
     * e.serie, e.cupon
     */
    /**
     * @see com.indeval.portaldali.persistence.dao.common.PosicionNombradaDaliDao#getTPosicionNombrada(TPosicionNombradaParamsPersistence)
     */
    public PageVO getTPosicionNombrada(
            final TPosicionNombradaParamsPersistence tPosicionNombradaParamsPersistence) {

        logger.info("Entrando a PosicionNombradaDaliDaoImpl.getTPosicionNombrada()");

        Assert.notNull(tPosicionNombradaParamsPersistence,
                "El objeto de parametros recibido esta null");

        if (tPosicionNombradaParamsPersistence.getConsulta() != null
                && BY_INSTITUCION.equalsIgnoreCase(
                        tPosicionNombradaParamsPersistence.getConsulta())) {

            validaTPosicionNombradaParamsPersistence(tPosicionNombradaParamsPersistence);

        }

        final PageVO pagAux = tPosicionNombradaParamsPersistence.getPageVO() != null ? 
                tPosicionNombradaParamsPersistence.getPageVO() : new PageVO();

        List listaTPosicionNombrada = null;

        Integer countRegistros = (Integer) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {

                Criteria criteria = session.createCriteria(PosicionNombrada.class)
                    .setProjection(Projections.rowCount())
                    .createAlias("cupon", "cc")
                    .createAlias("cc.estadoCupon", "ec")
                    .createAlias("cc.emision", "e")
                    .createAlias("e.instrumento", "ei")
                    .createAlias("e.emisora", "ee")
                    .createAlias("cuentaNombrada", "cn")
                    .createAlias("cn.institucion", "i")
                    .createAlias("i.tipoInstitucion", "ti")
                    .createAlias("boveda", "b");

                if (tPosicionNombradaParamsPersistence.getIdInstitucion() != null
                        && StringUtils.isNotBlank(tPosicionNombradaParamsPersistence
                                .getIdInstitucion())) {
                    criteria.add(Restrictions.eq("ti.claveTipoInstitucion",
                            tPosicionNombradaParamsPersistence.getIdInstitucion().trim()));
                }

                if (tPosicionNombradaParamsPersistence.getFolioInstitucion() != null
                        && StringUtils.isNotBlank(tPosicionNombradaParamsPersistence
                                .getFolioInstitucion())) {
                    criteria.add(Restrictions.eq("i.folioInstitucion",
                            tPosicionNombradaParamsPersistence.getFolioInstitucion().trim()));
                }
                
                if(tPosicionNombradaParamsPersistence.getCuentas() != null
                		&& StringUtils.isNotBlank(tPosicionNombradaParamsPersistence.getCuentas()[0])) {
                	criteria.add(Restrictions.eq("cn.cuenta", tPosicionNombradaParamsPersistence.getCuentas()[0]));
                }

                if (tPosicionNombradaParamsPersistence.getTiposDeValor() != null
                        && StringUtils.isNotBlank(tPosicionNombradaParamsPersistence
                                .getTiposDeValor()[0])) {
                    criteria.add(Restrictions.eq("ei.claveTipoValor",
                            tPosicionNombradaParamsPersistence.getTiposDeValor()[0].trim()));
                }

                if (StringUtils.isNotBlank(tPosicionNombradaParamsPersistence.getEmisora())) {
                    criteria.add(Restrictions.eq("ee.descEmisora",
                            tPosicionNombradaParamsPersistence.getEmisora().trim()));
                }

                if (StringUtils.isNotBlank(tPosicionNombradaParamsPersistence.getSerie())) {
                    criteria.add(Restrictions.eq("e.serie", tPosicionNombradaParamsPersistence
                            .getSerie().trim()));
                }
                
                if (StringUtils.isNotBlank(tPosicionNombradaParamsPersistence.getCupon())) {
                    criteria.add(Restrictions.eq("cc.claveCupon", tPosicionNombradaParamsPersistence.
                    		getCupon().trim()));
                }
                
                if (StringUtils.isNotBlank(tPosicionNombradaParamsPersistence.getIsin())) {
                    criteria.add(Restrictions.eq("e.isin", tPosicionNombradaParamsPersistence.
                    		getIsin().trim()));
                }

                /* Filtro de Boveda */
                if(tPosicionNombradaParamsPersistence.getIdBoveda()!=null) {
                    criteria.add(Restrictions.eq("b.idBoveda", 
                            tPosicionNombradaParamsPersistence.getIdBoveda()));    
                }
                
                criteria.add(Restrictions.eq("ec.idEstatusCupon", VIGENTE));
                criteria.add(Restrictions.isNotNull("posicionDisponible"));
                criteria.add(Restrictions.gt("posicionDisponible", new BigInteger("0")));

                if (BY_MERCADO.equalsIgnoreCase(tPosicionNombradaParamsPersistence.getConsulta())
                        && StringUtils.isNotBlank(tPosicionNombradaParamsPersistence.getMercado())) {

                    criteria.createAlias("ei.mercado", "m");
                    if (MERCADO_CAPITALES.equalsIgnoreCase(tPosicionNombradaParamsPersistence
                            .getMercado().trim())) {
                        /* Error de datos obliga a efectuar un like */
                        /*
                         * criteria.add(Restrictions.eq("m.claveMercado",
                         * MERCADO_CAPITALES));
                         */
                        criteria.add(Restrictions.like("m.clave", MERCADO_CAPITALES + "%"));
                    }
                    else {
                        /* Error de datos obliga a efectuar un like */
                        /*
                         * criteria.add(Restrictions.in("m.claveMercado", new
                         * Object[] {PAPEL_BANCARIO, PAPEL_GUBERNAMENTAL}));
                         */
                        criteria.add(Restrictions.or(Restrictions.like("m.clave", PAPEL_BANCARIO
                                + "%"), Restrictions.like("m.clave", PAPEL_GUBERNAMENTAL + "%")));
                    }
                }

                return ((Integer) ((criteria.list() != null && !criteria.list().isEmpty()) ? 
                        criteria.list().get(0) : new Integer(0))).intValue();
            }

        });

        logger.debug("Registros encontrados: [" + countRegistros + "]");

        if (countRegistros.intValue() > 0) {

            listaTPosicionNombrada = (List) getHibernateTemplate().execute(new HibernateCallback() {

                public Object doInHibernate(Session session) throws HibernateException,
                        SQLException {

                    Criteria criteria = session.createCriteria(PosicionNombrada.class)
		                    .createAlias("cupon", "cc")
		                    .createAlias("cc.estadoCupon", "ec")
		                    .createAlias("cc.emision", "e")
		                    .createAlias("e.instrumento", "ei")
		                    .createAlias("e.emisora", "ee")
		                    .createAlias("cuentaNombrada", "cn")
		                    .createAlias("cn.institucion", "i")
		                    .createAlias("i.tipoInstitucion", "ti")
		                    .createAlias("boveda", "b");

                    if (tPosicionNombradaParamsPersistence.getIdInstitucion() != null
                            && StringUtils.isNotBlank(tPosicionNombradaParamsPersistence
                                    .getIdInstitucion())) {
                        criteria.add(Restrictions.eq("ti.claveTipoInstitucion",
                                tPosicionNombradaParamsPersistence.getIdInstitucion().trim()));
                    }

                    if (tPosicionNombradaParamsPersistence.getFolioInstitucion() != null
                            && StringUtils.isNotBlank(tPosicionNombradaParamsPersistence
                                    .getFolioInstitucion())) {
                        criteria.add(Restrictions.eq("i.folioInstitucion",
                                tPosicionNombradaParamsPersistence.getFolioInstitucion().trim()));
                    }
                    
                    if(tPosicionNombradaParamsPersistence.getCuentas() != null
                    		&& tPosicionNombradaParamsPersistence.getCuentas().length > 0) {
                    	criteria.add(Restrictions.in("cn.cuenta", tPosicionNombradaParamsPersistence.getCuentas()));
                    }

                    if (tPosicionNombradaParamsPersistence.getTiposDeValor() != null
                            && StringUtils.isNotBlank(tPosicionNombradaParamsPersistence
                                    .getTiposDeValor()[0])) {
                        criteria.add(Restrictions.eq("ei.claveTipoValor",
                                tPosicionNombradaParamsPersistence.getTiposDeValor()[0].trim()));
                    }

                    if (StringUtils.isNotBlank(tPosicionNombradaParamsPersistence.getEmisora())) {
                        criteria.add(Restrictions.eq("ee.descEmisora",
                                tPosicionNombradaParamsPersistence.getEmisora().trim()));
                    }

                    if (StringUtils.isNotBlank(tPosicionNombradaParamsPersistence.getSerie())) {
                        criteria.add(Restrictions.eq("e.serie", tPosicionNombradaParamsPersistence
                                .getSerie().trim()));
                    }
                    
                    if (StringUtils.isNotBlank(tPosicionNombradaParamsPersistence.getCupon())) {
                        criteria.add(Restrictions.eq("cc.claveCupon", tPosicionNombradaParamsPersistence.
                        		getCupon().trim()));
                    }
                    
                    if (StringUtils.isNotBlank(tPosicionNombradaParamsPersistence.getIsin())) {
                        criteria.add(Restrictions.eq("e.isin", tPosicionNombradaParamsPersistence.
                        		getIsin().trim()));
                    }

                    /* Filtro de Boveda */
                    if(tPosicionNombradaParamsPersistence.getIdBoveda()!=null) {
                        criteria.add(Restrictions.eq("b.idBoveda", 
                                tPosicionNombradaParamsPersistence.getIdBoveda()));    
                    }
                    
                    criteria.add(Restrictions.eq("ec.idEstatusCupon", VIGENTE));
                    criteria.add(Restrictions.isNotNull("posicionDisponible"));
                    criteria.add(Restrictions.gt("posicionDisponible", new BigInteger("0")));

                    if (BY_MERCADO.equalsIgnoreCase(tPosicionNombradaParamsPersistence
                            .getConsulta())
                            && StringUtils.isNotBlank(tPosicionNombradaParamsPersistence
                                    .getMercado())) {

                        criteria.createAlias("ei.mercado", "m");
                        if (MERCADO_CAPITALES.equalsIgnoreCase(tPosicionNombradaParamsPersistence
                                .getMercado().trim())) {
                            /* Error de datos obliga a efectuar un like */
                            /*
                             * criteria.add(Restrictions.eq("m.claveMercado",
                             * MERCADO_CAPITALES));
                             */
                            criteria.add(Restrictions.like("m.clave", MERCADO_CAPITALES + "%"));
                        }
                        else {
                            /* Error de datos obliga a efectuar un like */
                            /*
                             * criteria.add(Restrictions.in("m.claveMercado",
                             * new Object[] {PAPEL_BANCARIO,
                             * PAPEL_GUBERNAMENTAL}));
                             */
                            criteria.add(Restrictions.or(Restrictions.like("m.clave",
                                    PAPEL_BANCARIO + "%"), Restrictions.like("m.clave",
                                    PAPEL_GUBERNAMENTAL + "%")));
                        }
                    }

                    // ordenamiento ascendente por tv, emisora, serie
                    criteria.addOrder(Order.asc("ei.claveTipoValor")).addOrder(
                            Order.asc("ee.descEmisora")).addOrder(Order.asc("e.serie")).addOrder(
                            Order.asc("ec.idEstatusCupon"));

                    if (tPosicionNombradaParamsPersistence.isTest()) {
                        criteria.setMaxResults(TPosicionNombradaParamsPersistence.LIMITE);
                    }
                    else if (pagAux.getRegistrosXPag().intValue() > 0) {
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
     * XXX Al parecer no es utilizado, o si ???
     * 
     * @see com.indeval.portaldali.persistence.dao.common.PosicionNombradaDaliDao#getTPosicionNombradaByAgente(TPosicionNombradaParamsPersistence)
     */
    public PageVO getTPosicionNombradaByAgente(
            final TPosicionNombradaParamsPersistence tPosicionNombradaParamsPersistence) {

        logger.info("Entrando a PosicionNombradaDaliDaoImpl.getTPosicionNombradaByAgente()");

        validaTPosicionNombradaParamsPersistence(tPosicionNombradaParamsPersistence);

        final PageVO pagAux = tPosicionNombradaParamsPersistence.getPageVO() != null ? 
                tPosicionNombradaParamsPersistence.getPageVO() : new PageVO();

        List listaTPosicionNombrada = null;

        Integer countRegistros = (Integer) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {

                Criteria criteria = session.createCriteria(PosicionNombrada.class)
                    .setProjection(Projections.rowCount())
                    .createAlias("emision", "e")
                    .createAlias("e.instrumento", "ei")
                    .createAlias("e.emisora", "ee")
                    .createAlias("e.cupon", "cc")
                    .createAlias("cc.estadoCupon", "ec")
                    .createAlias("cuentaNombrada", "cn")
                    .createAlias("cn.institucion", "i")
                    .createAlias("i.tipoInstitucion", "ti")
                    .createAlias("boveda", "b");

                criteria.add(
                        Restrictions.eq("ti.claveTipoInstitucion",
                                tPosicionNombradaParamsPersistence.getIdInstitucion().trim())).add(
                        Restrictions.eq("i.folioInstitucion", tPosicionNombradaParamsPersistence
                                .getFolioInstitucion().trim()));

                /* Filtro de Boveda */
                if(tPosicionNombradaParamsPersistence.getIdBoveda()!=null) {
                    criteria.add(Restrictions.eq("b.idBoveda", 
                            tPosicionNombradaParamsPersistence.getIdBoveda()));    
                }
                
                criteria.add(Restrictions.eq("ec.idEstatusCupon", VIGENTE));
                
                if (tPosicionNombradaParamsPersistence.isTest()) {
                    criteria.setMaxResults(TPosicionNombradaParamsPersistence.LIMITE);
                }
                else if (pagAux.getRegistrosXPag().intValue() > 0) {
                    criteria.setFirstResult(pagAux.getOffset().intValue());
                    criteria.setMaxResults(pagAux.getRegistrosXPag().intValue());
                }

                return ((Integer) ((criteria.list() != null && !criteria.list().isEmpty()) ? 
                        criteria.list().get(0) : new Integer(0))).intValue();
            }

        });

        logger.debug("Registros encontrados: [" + countRegistros + "]");

        if (countRegistros.intValue() > 0) {

            listaTPosicionNombrada = (List) getHibernateTemplate().execute(new HibernateCallback() {

                public Object doInHibernate(Session session) throws HibernateException,
                        SQLException {

                    Criteria criteria = session.createCriteria(PosicionNombrada.class)
                        .createAlias("emision", "e")
                        .createAlias("e.instrumento", "ei")
                        .createAlias("e.emisora", "ee")
                        .createAlias("e.cupon", "cc")
                        .createAlias("cc.estadoCupon", "ec")
                        .createAlias("cuentaNombrada", "cn")
                        .createAlias("cn.institucion", "i")
                        .createAlias("i.tipoInstitucion", "ti")
                        .createAlias("boveda", "b");

                    criteria.add(
                            Restrictions.eq("ti.claveTipoInstitucion",
                                    tPosicionNombradaParamsPersistence.getIdInstitucion().trim()))
                            .add(Restrictions.eq("i.folioInstitucion",
                                            tPosicionNombradaParamsPersistence
                                                    .getFolioInstitucion().trim()));

                    /* Filtro de Boveda */
                    if(tPosicionNombradaParamsPersistence.getIdBoveda()!=null) {
                        criteria.add(Restrictions.eq("b.idBoveda", 
                                tPosicionNombradaParamsPersistence.getIdBoveda()));    
                    }
                    
                    criteria.add(Restrictions.eq("ec.idEstatusCupon", VIGENTE));
                    
                    // ordenamiento ascendente por tv, emisora, serie
                    criteria.addOrder(Order.asc("ei.claveTipoValor")).addOrder(
                            Order.asc("ee.descEmisora")).addOrder(Order.asc("e.serie")).addOrder(
                            Order.asc("ec.idEstatusCupon"));

                    if (tPosicionNombradaParamsPersistence.isTest()) {
                        criteria.setMaxResults(TPosicionNombradaParamsPersistence.LIMITE);
                    }
                    else if (pagAux.getRegistrosXPag().intValue() > 0) {
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
     * @see com.indeval.portaldali.persistence.dao.common.PosicionNombradaDaliDao#getTPosicionNombradaByCuenta(TPosicionNombradaParamsPersistence)
     */
    public List getTPosicionNombradaByCuenta(
            final TPosicionNombradaParamsPersistence tPosicionNombradaParamsPersistence) {

        logger.info("Entrando a PosicionNombradaDaliDaoImpl.getTPosicionNombradaByCuenta()");

        Assert.notNull(tPosicionNombradaParamsPersistence,
                "El objeto de parametros recibido esta null");

        if (StringUtils.isNotBlank(tPosicionNombradaParamsPersistence.getConsulta())
                && BY_INSTITUCION.equalsIgnoreCase(
                        tPosicionNombradaParamsPersistence.getConsulta().trim())) {

            validaTPosicionNombradaParamsPersistence(tPosicionNombradaParamsPersistence);

            Assert.notNull(tPosicionNombradaParamsPersistence.getCuentas(),
                    "Se requiere al menos una cuenta");
            Assert.isTrue(StringUtils
                    .isNotBlank(tPosicionNombradaParamsPersistence.getCuentas()[0]),
                    "La cuenta de la institucion esta null");

        }

        return (List) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Criteria criteria = session.createCriteria(PosicionNombrada.class)
                    .createAlias("cuentaNombrada", "cn")
                    .add(Restrictions.eq("cn.cuenta", 
                            tPosicionNombradaParamsPersistence.getCuentas()[0].trim()));

                criteria.createAlias("emision", "e")
                    .createAlias("e.instrumento", "ei")
                    .createAlias("e.emisora", "ee")
                    .createAlias("e.cupon", "cc")
                    .createAlias("cc.estadoCupon", "ec")
                    .createAlias("boveda", "b");

                if (StringUtils.isNotBlank(tPosicionNombradaParamsPersistence.getConsulta())
                        && BY_INSTITUCION.equalsIgnoreCase(tPosicionNombradaParamsPersistence
                                .getConsulta().trim())) {

                    if (tPosicionNombradaParamsPersistence.getTiposDeValor() != null
                            && StringUtils.isNotBlank(tPosicionNombradaParamsPersistence
                                    .getTiposDeValor()[0])) {
                        criteria.add(Restrictions.eq("ei.claveTipoValor",
                                tPosicionNombradaParamsPersistence.getTiposDeValor()[0].trim()));
                    }

                    if (StringUtils.isNotBlank(tPosicionNombradaParamsPersistence.getEmisora())) {
                        criteria.add(Restrictions.eq("ee.descEmisora",
                                tPosicionNombradaParamsPersistence.getEmisora().trim()));
                    }

                    if (StringUtils.isNotBlank(tPosicionNombradaParamsPersistence.getSerie())) {
                        criteria.add(Restrictions.eq("e.serie", tPosicionNombradaParamsPersistence
                                .getSerie().trim()));
                    }

                    /* Filtro de Boveda */
                    if(tPosicionNombradaParamsPersistence.getIdBoveda()!=null) {
                        criteria.add(Restrictions.eq("b.idBoveda", 
                                tPosicionNombradaParamsPersistence.getIdBoveda()));    
                    }
                    
                    criteria.add(Restrictions.eq("ec.idEstatusCupon", VIGENTE));

                }

                // ordenamiento ascendente por tv, emisora, serie
                criteria.addOrder(Order.asc("ei.claveTipoValor")).addOrder(
                        Order.asc("ee.descEmisora")).addOrder(Order.asc("e.serie")).addOrder(
                        Order.asc("cc.claveCupon"));

                return criteria.list();
            }

        });
    }

    /**
     * @see com.indeval.portaldali.persistence.dao.common.PosicionNombradaDaliDao#getTPosicionNombradaByCuentas(com.indeval.portaldali.persistence.vo.TPosicionNombradaParamsPersistence)
     */
    public List[] getTPosicionNombradaByCuentas(
            final TPosicionNombradaParamsPersistence tPosicionNombradaParamsPersistence) {

        logger.info("Entrando a PosicionNombradaDaliDaoImpl.getTPosicionNombradaByCuentas()");

        validaTPosicionNombradaParamsPersistence(tPosicionNombradaParamsPersistence);

        Assert.notNull(tPosicionNombradaParamsPersistence.getCuentas(),
                "El arreglo de cuentas esta null");

        TPosicionNombradaParamsPersistence paramsAux = 
            clonaTPosicionNombradaParamsPersistence(tPosicionNombradaParamsPersistence);
        List[] arregloRetorno = new List[tPosicionNombradaParamsPersistence.getCuentas().length];

        for (int i = 0; i < tPosicionNombradaParamsPersistence.getCuentas().length; i++) {

            paramsAux.setCuentas(new String[] {
                tPosicionNombradaParamsPersistence.getCuentas()[i] });
            arregloRetorno[i] = getTPosicionNombradaByCuenta(paramsAux);
        }

        return arregloRetorno;
    }

    /**
     * @see com.indeval.portaldali.persistence.dao.common.PosicionNombradaDaliDao#getTPosicionNombradaByExample(com.indeval.portaldali.persistence.vo.TPosicionNombradaParamsPersistence)
     */
    public PageVO getTPosicionNombradaByExample(
            final TPosicionNombradaParamsPersistence tPosicionNombradaParamsPersistence) {

        logger.info("Entrando a PosicionNombradaDaliDaoImpl.getTPosicionNombradaByExample()");

        Assert.notNull(tPosicionNombradaParamsPersistence,
                "El objeto de parametros recibido esta null");
        Assert.notNull(tPosicionNombradaParamsPersistence.getTPosicionNombrada(),
                "El objeto example recibido esta null");

        final PageVO pagAux = tPosicionNombradaParamsPersistence.getPageVO() != null ? 
                tPosicionNombradaParamsPersistence.getPageVO() : new PageVO();

        List listaTPosicionNombrada = null;

        Integer countRegistros = (Integer) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {

                Example example = Example.create(
                        tPosicionNombradaParamsPersistence.getTPosicionNombrada()).excludeZeroes();
                Criteria criteria = session.createCriteria(PosicionNombrada.class).setProjection(
                        Projections.rowCount()).add(example);

                return ((Integer) ((criteria.list() != null && !criteria.list().isEmpty()) ? 
                        criteria.list().get(0) : new Integer(0))).intValue();
            }

        });

        logger.debug("Registros encontrados: [" + countRegistros + "]");

        if (countRegistros.intValue() > 0) {

            listaTPosicionNombrada = (List) getHibernateTemplate().execute(new HibernateCallback() {

                public Object doInHibernate(Session session) throws HibernateException,
                        SQLException {

                    Example example = Example.create(
                            tPosicionNombradaParamsPersistence.getTPosicionNombrada())
                            .excludeZeroes();
                    Criteria criteria = session.createCriteria(PosicionNombrada.class);
                    criteria.add(example)
                            .createAlias("emision", "e")
                            .createAlias("e.instrumento", "ei")
                            .createAlias("e.emisora", "ee")
                            .createAlias("e.cupon", "cc")
                            .createAlias("cc.estadoCupon", "ec")
                            .createAlias("boveda", "b");

                    // ordenamiento ascendente por tv, emisora, serie
                    criteria.addOrder(Order.asc("ei.claveTipoValor")).addOrder(
                            Order.asc("ee.descEmisora")).addOrder(Order.asc("e.serie")).addOrder(
                            Order.asc("cc.claveCupon"));

                    if (tPosicionNombradaParamsPersistence.isTest()) {
                        criteria.setMaxResults(TPosicionNombradaParamsPersistence.LIMITE);
                    }
                    else if (pagAux.getRegistrosXPag().intValue() > 0) {
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
     * @see com.indeval.portaldali.persistence.dao.common.PosicionNombradaDaliDao#getTPosicionNombradaByIdTipoValor(TPosicionNombradaParamsPersistence)
     */
    public PageVO getTPosicionNombradaByIdTipoValor(
            final TPosicionNombradaParamsPersistence tPosicionNombradaParamsPersistence) {

        logger.info("Entrando a PosicionNombradaDaliDaoImpl.getTPosicionNombradaByIdTipoValor()");

        Assert.notNull(tPosicionNombradaParamsPersistence, "El objeto de parametros esta null");
        Assert.notNull(tPosicionNombradaParamsPersistence.getTiposDeValor(),
                "Se debe recibir al menos un tipo de valor");
        Assert.isTrue(StringUtils
                .isNotBlank(tPosicionNombradaParamsPersistence.getTiposDeValor()[0]),
                "El tipo de valor esta nulo o vacio");

        final PageVO pagAux = tPosicionNombradaParamsPersistence.getPageVO() != null ? 
                tPosicionNombradaParamsPersistence.getPageVO() : new PageVO();

        List listaTPosicionNombrada = null;

        Integer countRegistros = (Integer) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {

                Criteria criteria = session.createCriteria(PosicionNombrada.class)
                    .setProjection(Projections.rowCount())
                    .createAlias("emision", "e")
                    .createAlias("e.instrumento", "ei")
                    .createAlias("e.emisora", "ee")
                    .createAlias("e.cupon", "cc")
                    .createAlias("cc.estadoCupon", "ec")                    
                    .createAlias("boveda", "b");

                criteria.add(Restrictions.eq("ei.claveTipoValor",
                        tPosicionNombradaParamsPersistence.getTiposDeValor()[0].trim()));
                
                /* Filtro de Boveda */
                if(tPosicionNombradaParamsPersistence.getIdBoveda()!=null) {
                    criteria.add(Restrictions.eq("b.idBoveda", 
                            tPosicionNombradaParamsPersistence.getIdBoveda()));    
                }
                
                criteria.add(Restrictions.eq("ec.idEstatusCupon", VIGENTE));

                return ((Integer) ((criteria.list() != null && !criteria.list().isEmpty()) ? 
                        criteria.list().get(0) : new Integer(0))).intValue();
            }

        });

        logger.debug("Registros encontrados: [" + countRegistros + "]");

        if (countRegistros.intValue() > 0) {

            listaTPosicionNombrada = (List) getHibernateTemplate().execute(new HibernateCallback() {

                public Object doInHibernate(Session session) throws HibernateException,
                        SQLException {

                    Criteria criteria = session.createCriteria(PosicionNombrada.class)
                        .createAlias("emision", "e")
                        .createAlias("e.instrumento", "ei")
                        .createAlias("e.emisora", "ee")
                        .createAlias("e.cupon", "cc")
                        .createAlias("cc.estadoCupon", "ec")
                        .createAlias("boveda", "b");

                    criteria.add(Restrictions.eq("ei.claveTipoValor",
                            tPosicionNombradaParamsPersistence.getTiposDeValor()[0].trim()));

                    /* Filtro de Boveda */
                    if(tPosicionNombradaParamsPersistence.getIdBoveda()!=null) {
                        criteria.add(Restrictions.eq("b.idBoveda", 
                                tPosicionNombradaParamsPersistence.getIdBoveda()));    
                    }
                    
                    criteria.add(Restrictions.eq("ec.idEstatusCupon", VIGENTE));
                    
                    // ordenamiento ascendente por tv, emisora, serie
                    criteria.addOrder(Order.asc("ei.claveTipoValor")).addOrder(
                            Order.asc("ee.descEmisora")).addOrder(Order.asc("e.serie")).addOrder(
                            Order.asc("ec.idEstatusCupon"));

                    if (tPosicionNombradaParamsPersistence.isTest()) {
                        criteria.setMaxResults(TPosicionNombradaParamsPersistence.LIMITE);
                    }
                    else if (pagAux.getRegistrosXPag().intValue() > 0) {
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

    /*
     * NOTAS DE IMPLEMENTACION
     * 
     * select * from emisiones where( exists ( select 1 from historica..edoctatr
     * h where h.fecha_movto='06-FEB-2007' and h.tv = emisiones.tv and h.emisora =
     * emisiones.emisora and h.serie = emisiones.serie and h.cupon =
     * emisiones.cupon and (( h.id_inst='01' and h.folio_inst='001' and
     * h.cuenta='0117' ) or ( h.id_inst_recep='1' and h.folio_inst_recep= '001'
     * and h.cuenta_recep='0117' )) ))
     */
    /**
     * TODO Working : Falta resolver dependencia hacia los datos de
     * historica..edoctatr
     * 
     * @see com.indeval.portaldali.persistence.dao.common.PosicionNombradaDaliDao#getTPosicionNombradaByInstitucionEdoCtaUnico(TPosicionNombradaParamsPersistence)
     */
    public PageVO getTPosicionNombradaByInstitucionEdoCtaUnico(
            final TPosicionNombradaParamsPersistence tPosicionNombradaParamsPersistence) {

        logger.info("Entrando a PosicionNombradaDaliDaoImpl.getTPosicionNombradaByInstitucionEdoCtaUnico()");

        validaTPosicionNombradaParamsPersistence(tPosicionNombradaParamsPersistence);

        final PageVO pagAux = tPosicionNombradaParamsPersistence.getPageVO() != null ? 
                tPosicionNombradaParamsPersistence.getPageVO() : new PageVO();

        List listaTPosicionNombrada = null;

        Integer countRegistros = (Integer) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {

                Criteria criteria = session.createCriteria(PosicionNombrada.class)
                    .setProjection(Projections.rowCount())
                    .createAlias("cuentaNombrada", "cn")
                    .createAlias("cn.institucion", "i")
                    .createAlias("i.tipoInstitucion", "ti")
                    .createAlias("emision", "e")
                    .createAlias("e.instrumento", "ei")
                    .createAlias("e.emisora", "ee")
                    .createAlias("e.cupon", "cc")
                    .createAlias("cc.estadoCupon", "ec")
                    .createAlias("boveda", "b");

                criteria.add(
                        Restrictions.eq("ti.claveTipoInstitucion",
                                tPosicionNombradaParamsPersistence.getIdInstitucion().trim())).add(
                        Restrictions.eq("i.folioInstitucion", tPosicionNombradaParamsPersistence
                                .getFolioInstitucion().trim()));
                if (tPosicionNombradaParamsPersistence.getCuentas() != null
                        && StringUtils
                                .isNotBlank(tPosicionNombradaParamsPersistence.getCuentas()[0])) {
                    criteria.add(Restrictions.eq("cn.cuenta", tPosicionNombradaParamsPersistence
                            .getCuentas()[0].trim()));
                }
                if (tPosicionNombradaParamsPersistence.getTiposDeValor() != null
                        && StringUtils.isNotBlank(tPosicionNombradaParamsPersistence
                                .getTiposDeValor()[0])) {
                    criteria.add(Restrictions.eq("ei.claveTipoValor",
                            tPosicionNombradaParamsPersistence.getTiposDeValor()[0].trim()));
                }

                if (StringUtils.isNotBlank(tPosicionNombradaParamsPersistence.getEmisora())) {
                    criteria.add(Restrictions.eq("ee.descEmisora",
                            tPosicionNombradaParamsPersistence.getEmisora().trim()));
                }

                if (StringUtils.isNotBlank(tPosicionNombradaParamsPersistence.getSerie())) {
                    criteria.add(Restrictions.eq("e.serie", tPosicionNombradaParamsPersistence
                            .getSerie().trim()));
                }

                /* Filtro de Boveda */
                if(tPosicionNombradaParamsPersistence.getIdBoveda()!=null) {
                    criteria.add(Restrictions.eq("b.idBoveda", 
                            tPosicionNombradaParamsPersistence.getIdBoveda()));    
                }
                
                criteria.add(Restrictions.eq("ec.idEstatusCupon", VIGENTE));

                return ((Integer) ((criteria.list() != null && !criteria.list().isEmpty()) ? criteria
                        .list().get(0)
                        : new Integer(0))).intValue();
            }

        });

        logger.debug("Registros encontrados: [" + countRegistros + "]");

        if (countRegistros.intValue() > 0) {

            listaTPosicionNombrada = (List) getHibernateTemplate().execute(new HibernateCallback() {

                public Object doInHibernate(Session session) throws HibernateException,
                        SQLException {

                    Criteria criteria = session.createCriteria(PosicionNombrada.class)
                        .createAlias("cuentaNombrada", "cn")
                        .createAlias("cn.institucion", "i")
                        .createAlias("i.tipoInstitucion", "ti")
                        .createAlias("emision", "e")
                        .createAlias("e.instrumento", "ei")
                        .createAlias("e.emisora", "ee")
                        .createAlias("e.cupon", "cc")
                        .createAlias("cc.estadoCupon", "ec")
                        .createAlias("boveda", "b");

                    criteria.add(
                            Restrictions.eq("ti.claveTipoInstitucion",
                                    tPosicionNombradaParamsPersistence.getIdInstitucion().trim()))
                            .add(Restrictions.eq("i.folioInstitucion",
                                            tPosicionNombradaParamsPersistence
                                                    .getFolioInstitucion().trim()));
                    if (tPosicionNombradaParamsPersistence.getCuentas() != null
                            && StringUtils.isNotBlank(tPosicionNombradaParamsPersistence
                                    .getCuentas()[0])) {
                        criteria.add(Restrictions.eq("cn.cuenta",
                                tPosicionNombradaParamsPersistence.getCuentas()[0].trim()));
                    }
                    if (tPosicionNombradaParamsPersistence.getTiposDeValor() != null
                            && StringUtils.isNotBlank(tPosicionNombradaParamsPersistence
                                    .getTiposDeValor()[0])) {
                        criteria.add(Restrictions.eq("ei.claveTipoValor",
                                tPosicionNombradaParamsPersistence.getTiposDeValor()[0].trim()));
                    }

                    if (StringUtils.isNotBlank(tPosicionNombradaParamsPersistence.getEmisora())) {
                        criteria.add(Restrictions.eq("ee.descEmisora",
                                tPosicionNombradaParamsPersistence.getEmisora().trim()));
                    }

                    if (StringUtils.isNotBlank(tPosicionNombradaParamsPersistence.getSerie())) {
                        criteria.add(Restrictions.eq("e.serie", tPosicionNombradaParamsPersistence
                                .getSerie().trim()));
                    }

                    /* Filtro de Boveda */
                    if(tPosicionNombradaParamsPersistence.getIdBoveda()!=null) {
                        criteria.add(Restrictions.eq("b.idBoveda", 
                                tPosicionNombradaParamsPersistence.getIdBoveda()));    
                    }
                    
                    criteria.add(Restrictions.eq("ec.idEstatusCupon", VIGENTE));

                    // ordenamiento ascendente por tv, emisora, serie
                    criteria.addOrder(Order.asc("ei.claveTipoValor")).addOrder(
                            Order.asc("ee.descEmisora")).addOrder(Order.asc("e.serie")).addOrder(
                            Order.asc("ec.idEstatusCupon"));

                    if (tPosicionNombradaParamsPersistence.isTest()) {
                        criteria.setMaxResults(TPosicionNombradaParamsPersistence.LIMITE);
                    }
                    else if (pagAux.getRegistrosXPag().intValue() > 0) {
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

    /*
     * NOTAS DE IMPLEMENTACION
     * 
     * SQL : select * from bdcaptal..valores v, agentes a where
     * v.saldo_disponible > 0 and v.id_inst = "02" and v.folio_inst = "013" and
     * v.id_inst=a.id_inst and v.folio_inst=a.folio_inst and v.cuenta=a.cuenta
     * and e.cupon_cortado='F' and i.mercado='MC' order by v.tv, v.emisora,
     * v.serie, v.cupon
     * 
     * Si se recibe la PK completa se deben mostrar las emisiones con
     * saldo_disponible >= 0
     */
    /**
     * TODO Working : Se requiere emisiones con saldo disponible >= 0
     * 
     * @see com.indeval.portaldali.persistence.dao.common.PosicionNombradaDaliDao#getTPosicionNombradaCapitales(com.indeval.portaldali.persistence.vo.TPosicionNombradaParamsPersistence)
     */
    public PageVO getTPosicionNombradaCapitales(
            final TPosicionNombradaParamsPersistence tPosicionNombradaParamsPersistence) {

        logger.info("Entrando a PosicionNombradaDaliDaoImpl.getTPosicionNombradaCapitales()");

        this.validaTPosicionNombradaParamsPersistence(tPosicionNombradaParamsPersistence);

        final PageVO pagAux = tPosicionNombradaParamsPersistence.getPageVO() != null ? 
                tPosicionNombradaParamsPersistence.getPageVO() : new PageVO();

        List listaTPosicionNombrada = null;

        Integer countRegistros = (Integer) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {

                Criteria criteria = session.createCriteria(PosicionNombrada.class)
                    .setProjection(Projections.rowCount())
                    .createAlias("cuentaNombrada", "cn")
                    .createAlias("cn.institucion", "i")
                    .createAlias("i.tipoInstitucion", "ti")
                    .createAlias("emision", "e")
                    .createAlias("e.instrumento", "ei")
                    .createAlias("ei.mercado", "m")
                    .createAlias("e.emisora", "ee")
                    .createAlias("e.cupon", "cc")
                    .createAlias("cc.estadoCupon", "ec")
                    .createAlias("boveda", "b");

                criteria.add(
                        Restrictions.eq("ti.claveTipoInstitucion",
                                tPosicionNombradaParamsPersistence.getIdInstitucion().trim())).add(
                        Restrictions.eq("i.folioInstitucion", tPosicionNombradaParamsPersistence
                                .getFolioInstitucion().trim()))
                // .add(Restrictions.eq("m.claveMercado",
                        // MERCADO_CAPITALES)); // Error de datos obliga
                        // a efectuar un like
                        .add(Restrictions.ilike("m.clave", MERCADO_CAPITALES + "%"));

                if (tPosicionNombradaParamsPersistence.getCuentas() != null
                        && StringUtils
                                .isNotBlank(tPosicionNombradaParamsPersistence.getCuentas()[0])) {
                    criteria.add(Restrictions.eq("cn.cuenta", tPosicionNombradaParamsPersistence
                            .getCuentas()[0].trim()));
                }
                if (tPosicionNombradaParamsPersistence.getTiposDeValor() != null
                        && StringUtils.isNotBlank(tPosicionNombradaParamsPersistence
                                .getTiposDeValor()[0])) {
                    criteria.add(Restrictions.eq("ei.claveTipoValor",
                            tPosicionNombradaParamsPersistence.getTiposDeValor()[0].trim()));
                }

                if (StringUtils.isNotBlank(tPosicionNombradaParamsPersistence.getEmisora())) {
                    criteria.add(Restrictions.eq("ee.descEmisora",
                            tPosicionNombradaParamsPersistence.getEmisora().trim()));
                }

                if (StringUtils.isNotBlank(tPosicionNombradaParamsPersistence.getSerie())) {
                    criteria.add(Restrictions.eq("e.serie", tPosicionNombradaParamsPersistence
                            .getSerie().trim()));
                }

                /* Filtro de Boveda */
                if(tPosicionNombradaParamsPersistence.getIdBoveda()!=null) {
                    criteria.add(Restrictions.eq("b.idBoveda", 
                            tPosicionNombradaParamsPersistence.getIdBoveda()));    
                }
                
                criteria.add(Restrictions.eq("ec.idEstatusCupon", VIGENTE));

                return ((Integer) ((criteria.list() != null && !criteria.list().isEmpty()) ? 
                        criteria.list().get(0) : new Integer(0))).intValue();
            }

        });

        logger.debug("Registros encontrados: [" + countRegistros + "]");

        if (countRegistros.intValue() > 0) {

            listaTPosicionNombrada = (List) getHibernateTemplate().execute(new HibernateCallback() {

                public Object doInHibernate(Session session) throws HibernateException,
                        SQLException {

                    Criteria criteria = session.createCriteria(PosicionNombrada.class)
                        .createAlias("cuentaNombrada", "cn")
                        .createAlias("cn.institucion", "i")
                        .createAlias("i.tipoInstitucion", "ti")
                        .createAlias("emision", "e")
                        .createAlias("e.instrumento", "ei")
                        .createAlias("ei.mercado", "m")
                        .createAlias("e.emisora", "ee")
                        .createAlias("e.cupon", "cc")
                        .createAlias("cc.estadoCupon", "ec")
                        .createAlias("boveda", "b");

                    criteria.add(
                            Restrictions.eq("ti.claveTipoInstitucion",
                                    tPosicionNombradaParamsPersistence.getIdInstitucion().trim()))
                            .add(Restrictions.eq("i.folioInstitucion",
                                            tPosicionNombradaParamsPersistence
                                                    .getFolioInstitucion().trim()))
                            // .add(Restrictions.eq("m.claveMercado",
                            // MERCADO_CAPITALES)); // Error de datos
                            // obliga a efectuar un like
                            .add(Restrictions.ilike("m.clave", MERCADO_CAPITALES + "%"));

                    if (tPosicionNombradaParamsPersistence.getCuentas() != null
                            && StringUtils.isNotBlank(tPosicionNombradaParamsPersistence
                                    .getCuentas()[0])) {
                        criteria.add(Restrictions.eq("cn.cuenta",
                                tPosicionNombradaParamsPersistence.getCuentas()[0].trim()));
                    }
                    if (tPosicionNombradaParamsPersistence.getTiposDeValor() != null
                            && StringUtils.isNotBlank(tPosicionNombradaParamsPersistence
                                    .getTiposDeValor()[0])) {
                        criteria.add(Restrictions.eq("ei.claveTipoValor",
                                tPosicionNombradaParamsPersistence.getTiposDeValor()[0].trim()));
                    }

                    if (StringUtils.isNotBlank(tPosicionNombradaParamsPersistence.getEmisora())) {
                        criteria.add(Restrictions.eq("ee.descEmisora",
                                tPosicionNombradaParamsPersistence.getEmisora().trim()));
                    }

                    if (StringUtils.isNotBlank(tPosicionNombradaParamsPersistence.getSerie())) {
                        criteria.add(Restrictions.eq("e.serie", tPosicionNombradaParamsPersistence
                                .getSerie().trim()));
                    }

                    /* Filtro de Boveda */
                    if(tPosicionNombradaParamsPersistence.getIdBoveda()!=null) {
                        criteria.add(Restrictions.eq("b.idBoveda", 
                                tPosicionNombradaParamsPersistence.getIdBoveda()));    
                    }
                    
                    criteria.add(Restrictions.eq("ec.idEstatusCupon", VIGENTE));

                    // ordenamiento ascendente por tv, emisora, serie
                    criteria.addOrder(Order.asc("ei.claveTipoValor")).addOrder(
                            Order.asc("ee.descEmisora")).addOrder(Order.asc("e.serie")).addOrder(
                            Order.asc("ec.idEstatusCupon"));

                    if (tPosicionNombradaParamsPersistence.isTest()) {
                        criteria.setMaxResults(TPosicionNombradaParamsPersistence.LIMITE);
                    }
                    else if (pagAux.getRegistrosXPag().intValue() > 0) {
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

    /*
     * NOTAS DE IMPLEMENTACION
     * 
     * SQL : select v.tv as tv, v.emisora as emisora, v.serie as serie, v.cupon
     * as cupon, v.saldo_disponible as saldo_disponible, e.fecha_vencimiento,
     * DATEDIFF(day ,getdate(),e.fecha_vencimiento) as dias_vigente, e.isin as
     * isin, i.mercado as mercado, e.precio_vector as precio_vector, e.divisa as
     * divisa from bdcaptal..valores v, catalogo..emisiones e, catalogo..agentes
     * a, catalogo..instrumentos i where v.tv=e.tv and v.emisora=e.emisora and
     * v.serie=e.serie and v.cupon=e.cupon and i.tv=v.tv and v.saldo_disponible >
     * 0 -- si no es historico and v.id_inst=a.id_inst and
     * v.folio_inst=a.folio_inst and v.cuenta=a.cuenta and v.id_inst = ? and
     * v.folio_inst = ? and e.cupon_cortado= 'F' -- si no es conCortadas and
     * e.cupon_cortado in ('F', 'C') -- si es conCortadas and v.tv= 'D2' and
     * v.emisora = ? and v.serie = ? and v.cupon = ? and e.emision_extr in
     * ('CPOS', 'BCPO', 'ADR', 'ADCP', 'IADC', 'BADC', 'VIVI', 'LIBR')
     * 
     * union all
     * 
     * select v.tv as tv, v.emisora as emisora, v.serie as serie, v.cupon as
     * cupon, v.saldo_disponible as saldo_disponible, e.fecha_vencimiento,
     * DATEDIFF(day ,getdate(),e.fecha_vencimiento) as dias_vigente, e.isin as
     * isin, i.mercado as mercado, e.precio_vector as precio_vector, e.divisa as
     * divisa from bddinero..valores v, catalogo..emisiones e, catalogo..agentes
     * a, catalogo..instrumentos i where v.tv=e.tv and v.emisora=e.emisora and
     * v.serie=e.serie and v.cupon=e.cupon and i.tv=v.tv and v.saldo_disponible >
     * 0 -- si no es historico and v.id_inst=a.id_inst and
     * v.folio_inst=a.folio_inst and v.cuenta=a.cuenta and v.id_inst = ? and
     * v.folio_inst = ? and e.cupon_cortado= 'F' -- si no es conCortadas and
     * e.cupon_cortado in ('F', 'C') -- si es conCortadas and v.tv= 'D2' and
     * v.emisora = ? and v.serie = ? and v.cupon = ? and e.emision_extr in
     * ('CPOS', 'BCPO', 'ADR', 'ADCP', 'IADC', 'BADC', 'VIVI', 'LIBR')
     * 
     * order by v.tv, v.emisora, v.serie, v.cupon
     */
    /**
     * @see com.indeval.portaldali.persistence.dao.common.PosicionNombradaDaliDao#getTPosicionNombradaDivisionInternacional(com.indeval.portaldali.persistence.vo.TPosicionNombradaParamsPersistence)
     */
    public PageVO getTPosicionNombradaDivisionInternacional(
            final TPosicionNombradaParamsPersistence tPosicionNombradaParamsPersistence) {

        logger.info("Entrando a PosicionNombradaDaliDaoImpl.getTPosicionNombradaDivisionInternacional()");

        Assert.notNull(tPosicionNombradaParamsPersistence,
                "El objeto de parametros recibido esta null");

        if (StringUtils.isNotBlank(tPosicionNombradaParamsPersistence.getConsulta())) {
            this.validaTPosicionNombradaParamsPersistence(tPosicionNombradaParamsPersistence);
        }

        final PageVO pagAux = tPosicionNombradaParamsPersistence.getPageVO() != null ? 
                tPosicionNombradaParamsPersistence.getPageVO() : new PageVO();

        List listaTPosicionNombrada = null;

        Integer countRegistros = (Integer) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {

                Criteria criteria = session.createCriteria(PosicionNombrada.class)
                    .setProjection(Projections.rowCount())
                    .createAlias("emision", "e")
                    .createAlias("e.instrumento", "ei")
                    .createAlias("e.emisora", "ee")
                    .createAlias("e.cupon", "cc")
                    .createAlias("cc.estadoCupon", "ec")
                    .createAlias("cuentaNombrada", "cn")
                    .createAlias("cn.institucion", "i")
                    .createAlias("i.tipoInstitucion", "ti")
                    .createAlias("boveda", "b");

                if (tPosicionNombradaParamsPersistence.getIdInstitucion() != null
                        && StringUtils.isNotBlank(tPosicionNombradaParamsPersistence
                                .getIdInstitucion())) {
                    criteria.add(Restrictions.eq("ti.claveTipoInstitucion",
                            tPosicionNombradaParamsPersistence.getIdInstitucion().trim()));
                }

                if (tPosicionNombradaParamsPersistence.getFolioInstitucion() != null
                        && StringUtils.isNotBlank(tPosicionNombradaParamsPersistence
                                .getFolioInstitucion())) {
                    criteria.add(Restrictions.eq("i.folioInstitucion",
                            tPosicionNombradaParamsPersistence.getFolioInstitucion().trim()));
                }

                if (tPosicionNombradaParamsPersistence.getCuentas() != null
                        && StringUtils
                                .isNotBlank(tPosicionNombradaParamsPersistence.getCuentas()[0])) {
                    criteria.add(Restrictions.eq("cn.cuenta", tPosicionNombradaParamsPersistence
                            .getCuentas()[0].trim()));
                }

                if (tPosicionNombradaParamsPersistence.getTiposDeValor() != null
                        && StringUtils.isNotBlank(tPosicionNombradaParamsPersistence
                                .getTiposDeValor()[0])) {
                    criteria.add(Restrictions.eq("ei.claveTipoValor",
                            tPosicionNombradaParamsPersistence.getTiposDeValor()[0].trim()));
                }

                if (StringUtils.isNotBlank(tPosicionNombradaParamsPersistence.getEmisora())) {
                    criteria.add(Restrictions.eq("ee.descEmisora",
                            tPosicionNombradaParamsPersistence.getEmisora().trim()));
                }

                if (StringUtils.isNotBlank(tPosicionNombradaParamsPersistence.getSerie())) {
                    criteria.add(Restrictions.eq("e.serie", tPosicionNombradaParamsPersistence
                            .getSerie().trim()));
                }

                if (tPosicionNombradaParamsPersistence.getConsulta() != null) {
                    if (StringUtils.isNotBlank(tPosicionNombradaParamsPersistence.getIsin())) {
                        criteria.add(Restrictions.eq("e.isin", tPosicionNombradaParamsPersistence
                                .getIsin().trim()));
                    }
                    else {
                        criteria.add(Restrictions.not(Restrictions.ilike("e.isin", "MX%")));
                    }
                }

                /* Filtro de Boveda */
                if(tPosicionNombradaParamsPersistence.getIdBoveda()!=null) {
                    criteria.add(Restrictions.eq("b.idBoveda", 
                            tPosicionNombradaParamsPersistence.getIdBoveda()));    
                }
                
                criteria.add(Restrictions.eq("ec.idEstatusCupon", VIGENTE));

                if (StringUtils.isNotBlank(tPosicionNombradaParamsPersistence.getMercado())) {

                    criteria.createAlias("ei.mercado", "m");
                    if (MERCADO_CAPITALES.equalsIgnoreCase(tPosicionNombradaParamsPersistence
                            .getMercado().trim())) {
                        /* Error de datos obliga a efectuar un like */
                        /*
                         * criteria.add(Restrictions.eq("m.claveMercado",
                         * MERCADO_CAPITALES));
                         */
                        criteria.add(Restrictions.like("m.clave", MERCADO_CAPITALES + "%"));
                    }
                    else {
                        /* Error de datos obliga a efectuar un like */
                        /*
                         * criteria.add(Restrictions.in("m.claveMercado", new
                         * Object[] {PAPEL_BANCARIO, PAPEL_GUBERNAMENTAL}));
                         */
                        criteria.add(Restrictions.or(Restrictions.like("m.clave", PAPEL_BANCARIO
                                + "%"), Restrictions.like("m.clave", PAPEL_GUBERNAMENTAL + "%")));
                    }
                }

                return ((Integer) ((criteria.list() != null && !criteria.list().isEmpty()) ? 
                        criteria.list().get(0) : new Integer(0))).intValue();
            }

        });

        logger.debug("Registros encontrados: [" + countRegistros + "]");

        if (countRegistros.intValue() > 0) {

            listaTPosicionNombrada = (List) getHibernateTemplate().execute(new HibernateCallback() {

                public Object doInHibernate(Session session) throws HibernateException,
                        SQLException {

                    Criteria criteria = session.createCriteria(PosicionNombrada.class)
                        .createAlias("emision", "e")
                        .createAlias("e.instrumento", "ei")
                        .createAlias("e.emisora", "ee")
                        .createAlias("e.cupon", "cc")
                        .createAlias("cc.estadoCupon", "ec")
                        .createAlias("cuentaNombrada", "cn")
                        .createAlias("cn.institucion", "i")
                        .createAlias("i.tipoInstitucion", "ti")
                        .createAlias("boveda", "b");

                    if (tPosicionNombradaParamsPersistence.getIdInstitucion() != null
                            && StringUtils.isNotBlank(tPosicionNombradaParamsPersistence
                                    .getIdInstitucion())) {
                        criteria.add(Restrictions.eq("ti.claveTipoInstitucion",
                                tPosicionNombradaParamsPersistence.getIdInstitucion().trim()));
                    }

                    if (tPosicionNombradaParamsPersistence.getFolioInstitucion() != null
                            && StringUtils.isNotBlank(tPosicionNombradaParamsPersistence
                                    .getFolioInstitucion())) {
                        criteria.add(Restrictions.eq("i.folioInstitucion",
                                tPosicionNombradaParamsPersistence.getFolioInstitucion().trim()));
                    }

                    if (tPosicionNombradaParamsPersistence.getCuentas() != null
                            && StringUtils.isNotBlank(tPosicionNombradaParamsPersistence
                                    .getCuentas()[0])) {
                        criteria.add(Restrictions.eq("cn.cuenta",
                                tPosicionNombradaParamsPersistence.getCuentas()[0].trim()));
                    }

                    if (tPosicionNombradaParamsPersistence.getTiposDeValor() != null
                            && StringUtils.isNotBlank(tPosicionNombradaParamsPersistence
                                    .getTiposDeValor()[0])) {
                        criteria.add(Restrictions.eq("ei.claveTipoValor",
                                tPosicionNombradaParamsPersistence.getTiposDeValor()[0].trim()));
                    }

                    if (StringUtils.isNotBlank(tPosicionNombradaParamsPersistence.getEmisora())) {
                        criteria.add(Restrictions.eq("ee.descEmisora",
                                tPosicionNombradaParamsPersistence.getEmisora().trim()));
                    }

                    if (StringUtils.isNotBlank(tPosicionNombradaParamsPersistence.getSerie())) {
                        criteria.add(Restrictions.eq("e.serie", tPosicionNombradaParamsPersistence
                                .getSerie().trim()));
                    }

                    if (tPosicionNombradaParamsPersistence.getConsulta() != null) {
                        if (StringUtils.isNotBlank(tPosicionNombradaParamsPersistence.getIsin())) {
                            criteria.add(Restrictions.eq("e.isin",
                                    tPosicionNombradaParamsPersistence.getIsin().trim()));
                        }
                        else {
                            criteria.add(Restrictions.not(Restrictions.ilike("e.isin", "MX%")));
                        }
                    }

                    /* Filtro de Boveda */
                    if(tPosicionNombradaParamsPersistence.getIdBoveda()!=null) {
                        criteria.add(Restrictions.eq("b.idBoveda", 
                                tPosicionNombradaParamsPersistence.getIdBoveda()));    
                    }
                    
                    criteria.add(Restrictions.eq("ec.idEstatusCupon", VIGENTE));

                    if (StringUtils.isNotBlank(tPosicionNombradaParamsPersistence.getMercado())) {

                        criteria.createAlias("ei.mercado", "m");
                        if (MERCADO_CAPITALES.equalsIgnoreCase(tPosicionNombradaParamsPersistence
                                .getMercado().trim())) {
                            /* Error de datos obliga a efectuar un like */
                            /*
                             * criteria.add(Restrictions.eq("m.claveMercado",
                             * MERCADO_CAPITALES));
                             */
                            criteria.add(Restrictions.like("m.clave", MERCADO_CAPITALES + "%"));
                        }
                        else {
                            /* Error de datos obliga a efectuar un like */
                            /*
                             * criteria.add(Restrictions.in("m.claveMercado",
                             * new Object[] {PAPEL_BANCARIO,
                             * PAPEL_GUBERNAMENTAL}));
                             */
                            criteria.add(Restrictions.or(Restrictions.like("m.clave",
                                    PAPEL_BANCARIO + "%"), Restrictions.like("m.clave",
                                    PAPEL_GUBERNAMENTAL + "%")));
                        }
                    }

                    // ordenamiento ascendente por tv, emisora, serie
                    criteria.addOrder(Order.asc("ei.claveTipoValor")).addOrder(
                            Order.asc("ee.descEmisora")).addOrder(Order.asc("e.serie")).addOrder(
                            Order.asc("ec.idEstatusCupon"));

                    if (tPosicionNombradaParamsPersistence.isTest()) {
                        criteria.setMaxResults(TPosicionNombradaParamsPersistence.LIMITE);
                    }
                    else if (pagAux.getRegistrosXPag().intValue() > 0) {
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

    /*
     * NOTAS DE DESARROLLO
     * 
     * from EmisionPersistence e where (e.emisionExtra like '%ADC' or
     * e.emisionExtra like '%CPO' or e.emisionExtra like '%VIV' or
     * e.emisionExtra = 'ADCP' or e.emisionExtra = 'CPOS' or e.emisionExtra like
     * 'VI%') and cuponCortado = 'F' and e.emisionPk.tv = :tv and
     * e.emisionPk.emisora = :emisora and e.emisionPk.serie = :serie and
     * e.emisionPk.cupon = :cupon
     */
    /**
     * @see com.indeval.portaldali.persistence.dao.common.PosicionNombradaDaliDao#getTPosicionNombradaFileTransferMC(TPosicionNombradaParamsPersistence)
     */
    public PosicionNombrada getTPosicionNombradaFileTransferMC(
            final TPosicionNombradaParamsPersistence tPosicionNombradaParamsPersistence) {

        logger.info("Entrando a PosicionNombradaDaliDaoImpl.getTPosicionNombradaFileTransferMC()");

        Assert.notNull(tPosicionNombradaParamsPersistence.getTiposDeValor(), "La tv esta null");
        Assert.isTrue(StringUtils
                .isNotBlank(tPosicionNombradaParamsPersistence.getTiposDeValor()[0]),
                "La tv esta null");
        Assert.isTrue(StringUtils.isNotBlank(tPosicionNombradaParamsPersistence.getEmisora()),
                "La emisora esta null");
        Assert.isTrue(StringUtils.isNotBlank(tPosicionNombradaParamsPersistence.getSerie()),
                "La serie esta null");

        List listaTPosicionNombrada = (List) getHibernateTemplate().execute(
                new HibernateCallback() {

                    public Object doInHibernate(Session session) throws HibernateException,
                            SQLException {
                        Criteria criteria = session.createCriteria(PosicionNombrada.class)
                                .createAlias("emision", "e")
                                .createAlias("e.instrumento", "ei")
                                .createAlias("e.emisora", "ee")
                                .createAlias("e.cupon", "cc")
                                .createAlias("cc.estadoCupon", "ec")
                                .createAlias("boveda", "b");

                        criteria.add(
                                Restrictions.eq("ei.claveTipoValor",
                                        tPosicionNombradaParamsPersistence.getTiposDeValor()[0]
                                                .trim())).add(
                                Restrictions.eq("ee.descEmisora",
                                        tPosicionNombradaParamsPersistence.getEmisora().trim()))
                                .add(Restrictions.eq("e.serie",
                                        tPosicionNombradaParamsPersistence.getSerie().trim()));

                        criteria.add(Restrictions.disjunction().add(
                                Restrictions.ilike("e.emisionExtranjera", "%ADC")).add(
                                Restrictions.ilike("e.emisionExtranjera", "%CPO")).add(
                                Restrictions.ilike("e.emisionExtranjera", "%VIV")).add(
                                Restrictions.eq("e.emisionExtranjera", "ADCP")).add(
                                Restrictions.eq("e.emisionExtranjera", "CPOS")).add(
                                Restrictions.ilike("e.emisionExtranjera", "VI%")));

                        /* Filtro de Boveda */
                        if(tPosicionNombradaParamsPersistence.getIdBoveda()!=null) {
                            criteria.add(Restrictions.eq("b.idBoveda", 
                                    tPosicionNombradaParamsPersistence.getIdBoveda()));    
                        }
                        
                        criteria.add(Restrictions.eq("ec.idEstatusCupon", VIGENTE));

                        // ordenamiento ascendente por tv, emisora, serie
                        criteria.addOrder(Order.asc("ei.claveTipoValor")).addOrder(
                                Order.asc("ee.descEmisora")).addOrder(Order.asc("e.serie"))
                                .addOrder(Order.asc("ec.idEstatusCupon"));

                        return criteria.list();
                    }

                });

        PosicionNombrada tPosicionNombrada = null;

        if (listaTPosicionNombrada != null && !listaTPosicionNombrada.isEmpty()) {
            tPosicionNombrada = (PosicionNombrada) listaTPosicionNombrada.get(0);
        }

        return tPosicionNombrada;

    }

    /*
     * NOTAS DE IMPLEMENTACION
     * 
     * select v.cv_al, v.ge_cuenta, v.cv_tipo_valor, v.cv_emisora, v.cv_serie,
     * v.cv_cupon, e.isin, precio_vector = (select mt_vector from
     * bdcamara..kvector_precios where cv_tipo_valor = v.cv_tipo_valor and
     * cv_emisora = v.cv_emisora and cv_serie = v.cv_serie and cv_cupon =
     * v.cv_cupon and ge_actual = 'S' and fe_vector = (select max(fe_vector)
     * from bdcamara..kvector_precios where cv_tipo_valor = v.cv_tipo_valor and
     * cv_emisora = v.cv_emisora and cv_serie = v.cv_serie and cv_cupon =
     * v.cv_cupon) ) * (1 - (( select ge_garantia_porc_pena from
     * bdcamara..cemisora where ge_garantia = 'S' and cv_tipo_valor =
     * v.cv_tipo_valor and cv_emisora = v.cv_emisora and cv_serie = v.cv_serie)
     * /100 ) ), convert(char(12),no_titulos) as no_titulos from
     * bdcamara..vinffs013 v, catalogo..emisiones e where ((cv_al = '01003') and
     * (ge_cuenta = '0315')) and e.tv=v.cv_tipo_valor and e.emisora=v.cv_emisora
     * and e.serie=v.cv_serie and e.cupon = v.cv_cupon and (select mt_vector
     * from bdcamara..kvector_precios where cv_tipo_valor = v.cv_tipo_valor and
     * cv_emisora = v.cv_emisora and cv_serie = v.cv_serie and cv_cupon =
     * v.cv_cupon and ge_actual = 'S' and fe_vector = (select max(fe_vector)
     * from bdcamara..kvector_precios where cv_tipo_valor = v.cv_tipo_valor and
     * cv_emisora = v.cv_emisora and cv_serie = v.cv_serie and cv_cupon =
     * v.cv_cupon) ) * (1 - (( select ge_garantia_porc_pena from
     * bdcamara..cemisora where ge_garantia = 'S' and cv_tipo_valor =
     * v.cv_tipo_valor and cv_emisora = v.cv_emisora and cv_serie = v.cv_serie)
     * /100 ) ) <>null union all select v.cv_al, v.ge_cuenta, v.cv_tipo_valor,
     * v.cv_emisora, v.cv_serie, v.cv_cupon, e.isin, precio_vector = (select
     * mt_vector from bdcamara..kvector_precios where cv_tipo_valor =
     * v.cv_tipo_valor and cv_emisora = v.cv_emisora and cv_serie = v.cv_serie
     * and cv_cupon = v.cv_cupon and ge_actual = 'S' and fe_vector = (select
     * max(fe_vector) from bdcamara..kvector_precios where cv_tipo_valor =
     * v.cv_tipo_valor and cv_emisora = v.cv_emisora and cv_serie = v.cv_serie
     * and cv_cupon = v.cv_cupon) ) * (1 - (( select ge_garantia_porc_pena from
     * bdcamara..cemisora where ge_garantia = 'S' and cv_tipo_valor =
     * v.cv_tipo_valor and cv_emisora = v.cv_emisora and cv_serie = v.cv_serie)
     * /100 ) ), convert(char(12),no_titulos) as no_titulos from
     * bdcamara..vinffs013c v, catalogo..emisiones e where ((cv_al = '01003')
     * and (ge_cuenta = '0315')) and e.tv=v.cv_tipo_valor and
     * e.emisora=v.cv_emisora and e.serie=v.cv_serie and e.cupon = v.cv_cupon
     * and (select mt_vector from bdcamara..kvector_precios where cv_tipo_valor =
     * v.cv_tipo_valor and cv_emisora = v.cv_emisora and cv_serie = v.cv_serie
     * and cv_cupon = v.cv_cupon and ge_actual = 'S' and fe_vector = (select
     * max(fe_vector) from bdcamara..kvector_precios where cv_tipo_valor =
     * v.cv_tipo_valor and cv_emisora = v.cv_emisora and cv_serie = v.cv_serie
     * and cv_cupon = v.cv_cupon) ) * (1 - (( select ge_garantia_porc_pena from
     * bdcamara..cemisora where ge_garantia = 'S' and cv_tipo_valor =
     * v.cv_tipo_valor and cv_emisora = v.cv_emisora and cv_serie = v.cv_serie)
     * /100 ) ) <>null order by cv_al, ge_cuenta, cv_tipo_valor, cv_emisora,
     * cv_serie, cv_cupon
     */
    /**
     * TODO Working : Falta resolver dependencias hacia los datos de ...
     * bdcamara..kvector_precios bdcamara..cemisora bdcamara..vinffs013
     * bdcamara..vinffs013c
     * 
     * @see com.indeval.portaldali.persistence.dao.common.PosicionNombradaDaliDao#getTPosicionNombradaGarantias(com.indeval.portaldali.persistence.vo.TPosicionNombradaParamsPersistence)
     */
    public PageVO getTPosicionNombradaGarantias(
            final TPosicionNombradaParamsPersistence tPosicionNombradaParamsPersistence) {

        logger.info("Entrando a PosicionNombradaDaliDaoImpl.getTPosicionNombradaGarantias()");

        this.validaTPosicionNombradaParamsPersistence(tPosicionNombradaParamsPersistence);

        final PageVO pagAux = tPosicionNombradaParamsPersistence.getPageVO() != null ? 
                tPosicionNombradaParamsPersistence.getPageVO() : new PageVO();

        List listaTPosicionNombrada = null;

        Integer countRegistros = (Integer) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {

                Criteria criteria = session.createCriteria(PosicionNombrada.class)
                    .setProjection(Projections.rowCount())
                    .createAlias("emision", "e")
                    .createAlias("e.instrumento", "ei")
                    .createAlias("e.emisora", "ee")
                    .createAlias("e.cupon", "cc")
                    .createAlias("cc.estadoCupon", "ec")
                    .createAlias("boveda", "b");

                /* Filtro de Boveda */
                if(tPosicionNombradaParamsPersistence.getIdBoveda()!=null) {
                    criteria.add(Restrictions.eq("b.idBoveda", 
                            tPosicionNombradaParamsPersistence.getIdBoveda()));    
                }
                
                criteria.add(Restrictions.eq("ec.idEstatusCupon", VIGENTE));
                
                return ((Integer) ((criteria.list() != null && !criteria.list().isEmpty()) ? 
                        criteria.list().get(0) : new Integer(0))).intValue();
            }

        });

        logger.debug("Registros encontrados: [" + countRegistros + "]");

        if (countRegistros.intValue() > 0) {

            listaTPosicionNombrada = (List) getHibernateTemplate().execute(new HibernateCallback() {

                public Object doInHibernate(Session session) throws HibernateException,
                        SQLException {

                    Criteria criteria = session.createCriteria(PosicionNombrada.class)
                        .createAlias("emision", "e")
                        .createAlias("e.instrumento", "ei")
                        .createAlias("e.emisora", "ee")
                        .createAlias("e.cupon", "cc")
                        .createAlias("cc.estadoCupon", "ec")
                        .createAlias("boveda", "b");

                    /* Filtro de Boveda */
                    if(tPosicionNombradaParamsPersistence.getIdBoveda()!=null) {
                        criteria.add(Restrictions.eq("b.idBoveda", 
                                tPosicionNombradaParamsPersistence.getIdBoveda()));    
                    }
                    
                    criteria.add(Restrictions.eq("ec.idEstatusCupon", VIGENTE));
                    
                    // ordenamiento ascendente por tv, emisora, serie
                    criteria.addOrder(Order.asc("ei.claveTipoValor")).addOrder(
                            Order.asc("ee.descEmisora")).addOrder(Order.asc("e.serie")).addOrder(
                            Order.asc("ec.idEstatusCupon"));

                    if (tPosicionNombradaParamsPersistence.isTest()) {
                        criteria.setMaxResults(TPosicionNombradaParamsPersistence.LIMITE);
                    }
                    else if (pagAux.getRegistrosXPag().intValue() > 0) {
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

    /*
     * NOTAS DE IMPLEMENTACION:
     * 
     * select tv, emisora, serie, cupon from catalogo..decretos_variable group
     * by tv, emisora, serie, cupon
     */
    /**
     * TODO Working : Falta resolver dependencia hacia los datos de
     * catalogo..decretos_variable
     * 
     * @see com.indeval.portaldali.persistence.dao.common.PosicionNombradaDaliDao#getTPosicionNombradaPorEjercicioDivisionInternacional(com.indeval.portaldali.persistence.vo.TPosicionNombradaParamsPersistence)
     */
    public PageVO getTPosicionNombradaPorEjercicioDivisionInternacional(
            final TPosicionNombradaParamsPersistence tPosicionNombradaParamsPersistence) {

        logger.info("Entrando a PosicionNombradaDaliDaoImpl."
                + "getTPosicionNombradaPorEjercicioDivisionInternacional()");

        final PageVO pagAux = tPosicionNombradaParamsPersistence.getPageVO() != null ? 
                tPosicionNombradaParamsPersistence.getPageVO() : new PageVO();

        List listaTPosicionNombrada = null;

        Integer countRegistros = (Integer) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {

                Criteria criteria = session.createCriteria(PosicionNombrada.class)
                    .setProjection(Projections.rowCount())
                    .createAlias("emision", "e")
                    .createAlias("e.instrumento", "ei")
                    .createAlias("e.emisora", "ee")
                    .createAlias("e.cupon", "cc")
                    .createAlias("cc.estadoCupon", "ec")
                    .createAlias("boveda", "b");

                /* Filtro de Boveda */
                if(tPosicionNombradaParamsPersistence.getIdBoveda()!=null) {
                    criteria.add(Restrictions.eq("b.idBoveda", 
                            tPosicionNombradaParamsPersistence.getIdBoveda()));    
                }
                
                criteria.add(Restrictions.eq("ec.idEstatusCupon", VIGENTE));
                
                return ((Integer) ((criteria.list() != null && !criteria.list().isEmpty()) ? 
                        criteria.list().get(0) : new Integer(0))).intValue();
            }

        });

        logger.debug("Registros encontrados: [" + countRegistros + "]");

        if (countRegistros.intValue() > 0) {

            listaTPosicionNombrada = (List) getHibernateTemplate().execute(new HibernateCallback() {

                public Object doInHibernate(Session session) throws HibernateException,
                        SQLException {

                    Criteria criteria = session.createCriteria(PosicionNombrada.class)
                        .createAlias("emision", "e")
                        .createAlias("e.instrumento", "ei")
                        .createAlias("e.emisora", "ee")
                        .createAlias("e.cupon", "cc")
                        .createAlias("cc.estadoCupon", "ec")
                        .createAlias("boveda", "b");

                    /* Filtro de Boveda */
                    if(tPosicionNombradaParamsPersistence.getIdBoveda()!=null) {
                        criteria.add(Restrictions.eq("b.idBoveda", 
                                tPosicionNombradaParamsPersistence.getIdBoveda()));    
                    }
                    
                    criteria.add(Restrictions.eq("ec.idEstatusCupon", VIGENTE));
                    
                    // ordenamiento ascendente por tv, emisora, serie
                    criteria.addOrder(Order.asc("ei.claveTipoValor")).addOrder(
                            Order.asc("ee.descEmisora")).addOrder(Order.asc("e.serie")).addOrder(
                            Order.asc("ec.idEstatusCupon"));

                    if (tPosicionNombradaParamsPersistence.isTest()) {
                        criteria.setMaxResults(TPosicionNombradaParamsPersistence.LIMITE);
                    }
                    else if (pagAux.getRegistrosXPag().intValue() > 0) {
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
     * @see com.indeval.portaldali.persistence.dao.common.PosicionNombradaDaliDao#getTPosicionNombradaValpreE(com.indeval.portaldali.persistence.vo.TPosicionNombradaParamsPersistence)
     */
    public PageVO getTPosicionNombradaValpreE(
            final TPosicionNombradaParamsPersistence tPosicionNombradaParamsPersistence) {

        logger.info("Entrando a PosicionNombradaDaliDaoImpl.getTPosicionNombradaValpreE()");

        this.validaTPosicionNombradaParamsPersistence(tPosicionNombradaParamsPersistence);

        final PageVO pagAux = tPosicionNombradaParamsPersistence.getPageVO() != null ? 
                tPosicionNombradaParamsPersistence.getPageVO() : new PageVO();

        List listaTPosicionNombrada = null;

        Integer countRegistros = (Integer) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {

                Criteria criteria = session.createCriteria(PosicionNombrada.class)
                    .setProjection(Projections.rowCount())
                    .createAlias("cuentaNombrada", "cn")
                    .createAlias("cn.tipoCuenta", "ctc")
                    .createAlias("cn.institucion", "i")
                    .createAlias("i.tipoInstitucion", "ti")
                    .createAlias("emision", "e")
                    .createAlias("e.instrumento", "ei")
                    .createAlias("e.emisora", "ee")
                    .createAlias("e.cupon", "cc")
                    .createAlias("cc.estadoCupon", "ec")
                    .createAlias("boveda", "b");

                criteria.add(
                        Restrictions.eq("ti.claveTipoInstitucion",
                                tPosicionNombradaParamsPersistence.getIdInstitucion().trim())).add(
                        Restrictions.eq("i.folioInstitucion", 
                                tPosicionNombradaParamsPersistence.getFolioInstitucion().trim())).add(
                        Restrictions.eq("ctc.claveTipoCuenta", CUENTA_GARANTIA_VALPRE_E));

                if (tPosicionNombradaParamsPersistence.getCuentas() != null
                        && StringUtils.isNotBlank(tPosicionNombradaParamsPersistence.getCuentas()[0])) {
                    criteria.add(Restrictions.eq("cn.cuenta", 
                            tPosicionNombradaParamsPersistence.getCuentas()[0].trim()));
                }
                if (tPosicionNombradaParamsPersistence.getTiposDeValor() != null
                        && StringUtils.isNotBlank(
                                tPosicionNombradaParamsPersistence.getTiposDeValor()[0])) {
                    criteria.add(Restrictions.eq("ei.claveTipoValor",
                            tPosicionNombradaParamsPersistence.getTiposDeValor()[0].trim()));
                }

                if (StringUtils.isNotBlank(tPosicionNombradaParamsPersistence.getEmisora())) {
                    criteria.add(Restrictions.eq("ee.descEmisora",
                            tPosicionNombradaParamsPersistence.getEmisora().trim()));
                }

                if (StringUtils.isNotBlank(tPosicionNombradaParamsPersistence.getSerie())) {
                    criteria.add(Restrictions.eq("e.serie", tPosicionNombradaParamsPersistence
                            .getSerie().trim()));
                }

                /* Filtro de Boveda */
                if(tPosicionNombradaParamsPersistence.getIdBoveda()!=null) {
                    criteria.add(Restrictions.eq("b.idBoveda", 
                            tPosicionNombradaParamsPersistence.getIdBoveda()));    
                }
                
                criteria.add(Restrictions.eq("ec.idEstatusCupon", VIGENTE));

                return ((Integer) ((criteria.list() != null && !criteria.list().isEmpty()) ? 
                        criteria.list().get(0) : new Integer(0))).intValue();
            }

        });

        logger.debug("Registros encontrados: [" + countRegistros + "]");

        if (countRegistros.intValue() > 0) {

            listaTPosicionNombrada = (List) getHibernateTemplate().execute(new HibernateCallback() {

                public Object doInHibernate(Session session) throws HibernateException,
                        SQLException {

                    Criteria criteria = session.createCriteria(PosicionNombrada.class)
                        .createAlias("cuentaNombrada", "cn")
                        .createAlias("cn.tipoCuenta", "ctc")
                        .createAlias("cn.institucion", "i")
                        .createAlias("i.tipoInstitucion", "ti")
                        .createAlias("emision", "e")
                        .createAlias("e.instrumento", "ei")
                        .createAlias("e.emisora", "ee")
                        .createAlias("e.cupon", "cc")
                        .createAlias("cc.estadoCupon", "ec")
                        .createAlias("boveda", "b");

                    criteria.add(
                            Restrictions.eq("ti.claveTipoInstitucion",
                                    tPosicionNombradaParamsPersistence.getIdInstitucion().trim()))
                            .add(
                                    Restrictions.eq("i.folioInstitucion",
                                            tPosicionNombradaParamsPersistence
                                                    .getFolioInstitucion().trim())).add(
                                    Restrictions
                                            .eq("ctc.claveTipoCuenta", CUENTA_GARANTIA_VALPRE_E));

                    if (tPosicionNombradaParamsPersistence.getCuentas() != null
                            && StringUtils.isNotBlank(tPosicionNombradaParamsPersistence
                                    .getCuentas()[0])) {
                        criteria.add(Restrictions.eq("cn.cuenta",
                                tPosicionNombradaParamsPersistence.getCuentas()[0].trim()));
                    }
                    if (tPosicionNombradaParamsPersistence.getTiposDeValor() != null
                            && StringUtils.isNotBlank(tPosicionNombradaParamsPersistence
                                    .getTiposDeValor()[0])) {
                        criteria.add(Restrictions.eq("ei.claveTipoValor",
                                tPosicionNombradaParamsPersistence.getTiposDeValor()[0].trim()));
                    }

                    if (StringUtils.isNotBlank(tPosicionNombradaParamsPersistence.getEmisora())) {
                        criteria.add(Restrictions.eq("ee.descEmisora",
                                tPosicionNombradaParamsPersistence.getEmisora().trim()));
                    }

                    if (StringUtils.isNotBlank(tPosicionNombradaParamsPersistence.getSerie())) {
                        criteria.add(Restrictions.eq("e.serie", tPosicionNombradaParamsPersistence
                                .getSerie().trim()));
                    }

                    /* Filtro de Boveda */
                    if(tPosicionNombradaParamsPersistence.getIdBoveda()!=null) {
                        criteria.add(Restrictions.eq("b.idBoveda", 
                                tPosicionNombradaParamsPersistence.getIdBoveda()));    
                    }
                    
                    criteria.add(Restrictions.eq("ec.idEstatusCupon", VIGENTE));

                    // ordenamiento ascendente por tv, emisora, serie
                    criteria.addOrder(Order.asc("ei.claveTipoValor")).addOrder(
                            Order.asc("ee.descEmisora")).addOrder(Order.asc("e.serie")).addOrder(
                            Order.asc("ec.idEstatusCupon"));

                    if (tPosicionNombradaParamsPersistence.isTest()) {
                        criteria.setMaxResults(TPosicionNombradaParamsPersistence.LIMITE);
                    }
                    else if (pagAux.getRegistrosXPag().intValue() > 0) {
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

    /*
     * NOTAS DE IMPLEMENTACION
     * 
     * SQL : select * from bddinero..valores v, agentes a where saldo_disponible >
     * 0 and tv in (select tv from bdvalpre..insgar i where i.funcion
     * in('AM','G')) and v.id_inst = "02" and v.folio_inst = "013" and
     * v.id_inst=a.id_inst and v.folio_inst=a.folio_inst and a.tipo_cta='PROP'
     * and v.cuenta=a.cuenta and i.mercado='PG' order by v.tv, v.emisora,
     * v.serie, v.cupon
     * 
     */
    /**
     * TODO Working : Falta resolver dependencia hacia los datos de
     * bdvalpre..insgar
     * 
     * @see com.indeval.portaldali.persistence.dao.common.PosicionNombradaDaliDao#getTPosicionNombradaValpreEAdmonG(com.indeval.portaldali.persistence.vo.TPosicionNombradaParamsPersistence)
     */
    public PageVO getTPosicionNombradaValpreEAdmonG(
            final TPosicionNombradaParamsPersistence tPosicionNombradaParamsPersistence) {

        logger.info("Entrando a PosicionNombradaDaliDaoImpl.getTPosicionNombradaValpreEAdmonG()");

        this.validaTPosicionNombradaParamsPersistence(tPosicionNombradaParamsPersistence);

        final PageVO pagAux = tPosicionNombradaParamsPersistence.getPageVO() != null ? 
                tPosicionNombradaParamsPersistence.getPageVO() : new PageVO();

        List listaTPosicionNombrada = null;

        Integer countRegistros = (Integer) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {

                Criteria criteria = session.createCriteria(PosicionNombrada.class)
                    .setProjection(Projections.rowCount())
                    .createAlias("cuentaNombrada", "cn")
                    .createAlias("cn.tipoCuenta", "ctc")
                    .createAlias("cn.institucion", "i")
                    .createAlias("i.tipoInstitucion", "ti")
                    .createAlias("emision", "e")
                    .createAlias("e.instrumento", "ei")
                    .createAlias("e.emisora", "ee")
                    .createAlias("e.cupon", "cc")
                    .createAlias("cc.estadoCupon", "ec")
                    .createAlias("boveda", "b");

                criteria.add(
                        Restrictions.eq("ti.claveTipoInstitucion",
                                tPosicionNombradaParamsPersistence.getIdInstitucion().trim())).add(
                        Restrictions.eq("i.folioInstitucion", tPosicionNombradaParamsPersistence
                                .getFolioInstitucion().trim()));

                criteria.add(Restrictions.eq("ctc.claveTipoCuenta", CUENTA_PROPIA));

                /* Filtro de Boveda */
                if(tPosicionNombradaParamsPersistence.getIdBoveda()!=null) {
                    criteria.add(Restrictions.eq("b.idBoveda", 
                            tPosicionNombradaParamsPersistence.getIdBoveda()));    
                }
                
                criteria.add(Restrictions.eq("ec.idEstatusCupon", VIGENTE));

                return ((Integer) ((criteria.list() != null && !criteria.list().isEmpty()) ? 
                        criteria.list().get(0) : new Integer(0))).intValue();
            }

        });

        logger.debug("Registros encontrados: [" + countRegistros + "]");

        if (countRegistros.intValue() > 0) {

            listaTPosicionNombrada = (List) getHibernateTemplate().execute(new HibernateCallback() {

                public Object doInHibernate(Session session) throws HibernateException,
                        SQLException {

                    Criteria criteria = session.createCriteria(PosicionNombrada.class)
                        .createAlias("cuentaNombrada", "cn")
                        .createAlias("cn.tipoCuenta", "ctc")
                        .createAlias("cn.institucion", "i")
                        .createAlias("i.tipoInstitucion", "ti")
                        .createAlias("emision", "e")
                        .createAlias("e.instrumento", "ei")
                        .createAlias("e.emisora", "ee")
                        .createAlias("e.cupon", "cc")
                        .createAlias("cc.estadoCupon", "ec")
                        .createAlias("boveda", "b");

                    criteria.add(
                            Restrictions.eq("ti.claveTipoInstitucion",
                                    tPosicionNombradaParamsPersistence.getIdInstitucion().trim()))
                            .add(
                                    Restrictions.eq("i.folioInstitucion",
                                            tPosicionNombradaParamsPersistence
                                                    .getFolioInstitucion().trim()));

                    criteria.add(Restrictions.eq("ctc.claveTipoCuenta", CUENTA_PROPIA));

                    /* Filtro de Boveda */
                    if(tPosicionNombradaParamsPersistence.getIdBoveda()!=null) {
                        criteria.add(Restrictions.eq("b.idBoveda", 
                                tPosicionNombradaParamsPersistence.getIdBoveda()));    
                    }
                    
                    criteria.add(Restrictions.eq("ec.idEstatusCupon", VIGENTE));

                    // ordenamiento ascendente por tv, emisora, serie
                    criteria.addOrder(Order.asc("ei.claveTipoValor")).addOrder(
                            Order.asc("ee.descEmisora")).addOrder(Order.asc("e.serie")).addOrder(
                            Order.asc("ec.idEstatusCupon"));

                    if (tPosicionNombradaParamsPersistence.isTest()) {
                        criteria.setMaxResults(TPosicionNombradaParamsPersistence.LIMITE);
                    }
                    else if (pagAux.getRegistrosXPag().intValue() > 0) {
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

    // /* NOTAS DE DESARROLLO
    // *
    // * select e.tv as tv, e.emisora as emisora, e.serie as serie, e.cupon as
    // cupon,
    // * sum(v.saldo_disponible) as sumaSaldoDisponible from
    // * catalogo..emisiones e, bddinero..valores v where cupon_cortado='F' and
    // * v.saldo_disponible > 0 and e.tv=v.tv and e.emisora=v.emisora and
    // * e.serie=v.serie and e.cupon=v.cupon and v.id_inst ='02'
    // * and v.folio_inst ='061' and fecha_vencimiento = '24-may-2007'
    // * group by e.tv, e.emisora, e.serie, e.cupon
    // */
    // /**
    // * @see
    // com.indeval.persistence.portallegado.dali.dao.PosicionNombradaDao#getVencimientosPendientesByInstitucionFechaVencimiento(TPosicionNombradaParamsPersistence)
    // */
    // public PageVO getVencimientosPendientesByInstitucionFechaVencimiento(
    // final TPosicionNombradaParamsPersistence
    // tPosicionNombradaParamsPersistence) {
    //		
    // log.info("Entrando a PosicionNombradaDaliDaoImpl." +
    // "getVencimientosPendientesByInstitucionFechaVencimiento()");
    //        
    // this.validaTPosicionNombradaParamsPersistence(tPosicionNombradaParamsPersistence);
    //        
    // Assert.notNull(tPosicionNombradaParamsPersistence.getFechaVencimiento(),
    // "La fecha de vencimiento esta nula");
    //		
    // final PageVO pagAux = tPosicionNombradaParamsPersistence.getPageVO() !=
    // null ?
    // tPosicionNombradaParamsPersistence.getPageVO() : new PageVO();
    //
    // List listaTPosicionNombrada = null;
    //
    // Integer countRegistros = (Integer) getHibernateTemplate().execute(new
    // HibernateCallback(){
    //
    // public Object doInHibernate(Session session) throws HibernateException,
    // SQLException {
    //
    // Criteria criteria = session.createCriteria(PosicionNombrada.class)
    // .createAlias("CuentaNombrada", "cn")
    // .createAlias("cn.CInstitucion", "i")
    // .createAlias("i.CTipoInstitucion", "ti")
    // .createAlias("EmisionPersistence", "e")
    // .createAlias("e.CInstrumento", "ei")
    // .createAlias("ei.CMercado", "m")
    // .createAlias("e.CEmisora", "ee")
    // .createAlias("e.CCupon", "cc")
    // .createAlias("cc.CEstatusCupones", "ec");
    //                
    // criteria.add(Restrictions.eq("ti.claveTipoInstitucion",
    // tPosicionNombradaParamsPersistence.getIdInstitucion().trim()))
    // .add(Restrictions.eq("i.folioInstitucion",
    // tPosicionNombradaParamsPersistence.getFolioInstitucion().trim()))
    // .add(Restrictions.eq("e.fechaVencimiento",
    // tPosicionNombradaParamsPersistence.getFechaVencimiento()));
    //
    // if(tPosicionNombradaParamsPersistence.getTiposDeValor() != null &&
    // StringUtils.isNotBlank(tPosicionNombradaParamsPersistence.getTiposDeValor()[0])){
    // criteria.add(Restrictions.eq("ei.claveTipoValor",
    // tPosicionNombradaParamsPersistence.getTiposDeValor()[0].trim()));
    // }
    //
    // if(StringUtils.isNotBlank(tPosicionNombradaParamsPersistence.getEmisora())){
    // criteria.add(Restrictions.eq("ee.descEmisora",
    // tPosicionNombradaParamsPersistence.getEmisora().trim()));
    // }
    //
    // if(StringUtils.isNotBlank(tPosicionNombradaParamsPersistence.getSerie())){
    // criteria.add(Restrictions.eq(
    // "e.serie", tPosicionNombradaParamsPersistence.getSerie().trim()));
    // }
    //                
    // criteria.add(Restrictions.eq("ec.idEstatusCupon", VIGENTE));
    //                
    // criteria.setProjection(Projections.projectionList()
    // .add(Projections.rowCount())
    // .add(Projections.groupProperty("ei.claveTipoValor"))
    // .add(Projections.groupProperty("ee.descEmisora"))
    // .add(Projections.groupProperty("e.serie"))
    // .add(Projections.groupProperty("cc.claveCupon"))
    // .add(Projections.sum("posicionDisponible")));
    //
    // return ((Integer)((Object[])criteria.list().get(0))[0]).intValue();
    // }
    //
    // });
    //
    // log.debug("Registros encontrados: [" + countRegistros + "]");
    //
    // if(countRegistros.intValue() > 0) {
    //
    // listaTPosicionNombrada = (List) getHibernateTemplate().execute(new
    // HibernateCallback(){
    //
    // public Object doInHibernate(Session session)
    // throws HibernateException, SQLException {
    //
    // Criteria criteria = session.createCriteria(PosicionNombrada.class)
    // .createAlias("CuentaNombrada", "cn")
    // .createAlias("cn.CInstitucion", "i")
    // .createAlias("i.CTipoInstitucion", "ti")
    // .createAlias("EmisionPersistence", "e")
    // .createAlias("e.CInstrumento", "ei")
    // .createAlias("ei.CMercado", "m")
    // .createAlias("e.CEmisora", "ee")
    // .createAlias("e.CCupon", "cc")
    // .createAlias("cc.CEstatusCupones", "ec");
    //
    // criteria.add(Restrictions.eq("ti.claveTipoInstitucion",
    // tPosicionNombradaParamsPersistence.getIdInstitucion().trim()))
    // .add(Restrictions.eq("i.folioInstitucion",
    // tPosicionNombradaParamsPersistence.getFolioInstitucion().trim()))
    // .add(Restrictions.eq("e.fechaVencimiento",
    // tPosicionNombradaParamsPersistence.getFechaVencimiento()));
    //
    // if(tPosicionNombradaParamsPersistence.getTiposDeValor() != null &&
    // StringUtils.isNotBlank(tPosicionNombradaParamsPersistence.getTiposDeValor()[0])){
    // criteria.add(Restrictions.eq("ei.claveTipoValor",
    // tPosicionNombradaParamsPersistence.getTiposDeValor()[0].trim()));
    // }
    //
    // if(StringUtils.isNotBlank(tPosicionNombradaParamsPersistence.getEmisora())){
    // criteria.add(Restrictions.eq("ee.descEmisora",
    // tPosicionNombradaParamsPersistence.getEmisora().trim()));
    // }
    //
    // if(StringUtils.isNotBlank(tPosicionNombradaParamsPersistence.getSerie())){
    // criteria.add(Restrictions.eq(
    // "e.serie", tPosicionNombradaParamsPersistence.getSerie().trim()));
    // }
    //
    // criteria.add(Restrictions.eq("ec.idEstatusCupon", VIGENTE));
    //
    // criteria.setProjection(Projections.projectionList()
    // .add(Projections.groupProperty("ei.claveTipoValor"))
    // .add(Projections.groupProperty("ee.descEmisora"))
    // .add(Projections.groupProperty("e.serie"))
    // .add(Projections.groupProperty("cc.claveCupon"))
    // .add(Projections.sum("posicionDisponible")));
    //                    
    // //ordenamiento ascendente por tv, emisora, serie
    // criteria.addOrder(Order.asc("ei.claveTipoValor"))
    // .addOrder(Order.asc("ee.descEmisora"))
    // .addOrder(Order.asc("e.serie"))
    // .addOrder(Order.asc("cc.claveCupon"));
    //
    // if(tPosicionNombradaParamsPersistence.isTest()){
    // criteria.setMaxResults(TPosicionNombradaParamsPersistence.LIMITE);
    // }
    // else if(pagAux.getRegistrosXPag().intValue() > 0){
    // criteria.setFirstResult(pagAux.getOffset().intValue());
    // criteria.setMaxResults(pagAux.getRegistrosXPag().intValue());
    // }
    // return criteria.list();
    // }
    //
    // });
    // }
    //
    // PageVO pageReturn = new PageVO();
    // pageReturn.setTotalRegistros(countRegistros);
    // pageReturn.setRegistros(listaTPosicionNombrada);
    //
    // return pageReturn;
    //        
    // }

    /**
     * Construye una instancia Clon de un TPosicionNombradaParamsPersistence
     * 
     * @param tPosicionNombradaParamsPersistence
     * @return TPosicionNombradaParamsPersistence
     */
    private TPosicionNombradaParamsPersistence clonaTPosicionNombradaParamsPersistence(
            TPosicionNombradaParamsPersistence tPosicionNombradaParamsPersistence) {

        logger.info("Entrando a PosicionNombradaDaliDaoImpl.clonaTPosicionNombradaParamsPersistence()");

        TPosicionNombradaParamsPersistence clon = new TPosicionNombradaParamsPersistence();
        BeanUtils.copyProperties(tPosicionNombradaParamsPersistence, clon);

        return clon;

    }

    /**
     * Valida que el TPosicionNombradaParamsPersistence no sea nulo y que al
     * menos cuente con el id y el folio de la institucion
     */
    private void validaTPosicionNombradaParamsPersistence(
            TPosicionNombradaParamsPersistence tPosicionNombradaParamsPersistence) {

        logger.info("Entrando a PosicionNombradaDaliDaoImpl.validaTPosicionNombradaParamsPersistence()");

        Assert.notNull(tPosicionNombradaParamsPersistence,
                "El objeto de parametros recibido esta null");
        Assert.isTrue(
                StringUtils.isNotBlank(tPosicionNombradaParamsPersistence.getIdInstitucion()),
                "El id de la institucion esta null");
        Assert.isTrue(StringUtils.isNotBlank(tPosicionNombradaParamsPersistence
                .getFolioInstitucion()), "El folio de la institucion esta null");

    }

    /**
     * @return the emisionExtranjera
     */
    public List getEmisionExtranjera() {
        return emisionExtranjera;
    }

    /**
     * @param emisionExtranjera
     *            the emisionExtranjera to set
     */
    public void setEmisionExtranjera(List emisionExtranjera) {
        this.emisionExtranjera = emisionExtranjera;
    }

	/**
	 * @see com.indeval.portaldali.persistence.dao.common.PosicionNombradaDaliDao#getTPosicionNombradaSaldoInicial(com.indeval.portaldali.persistence.vo.TPosicionNombradaParamsPersistence)
	 */
	public PageVO getTPosicionNombradaSaldoInicial(
			final TPosicionNombradaParamsPersistence tPosicionNombradaParamsPersistence) {
		logger.info("Entrando a PosicionNombradaDaliDaoImpl.getTPosicionNombradaSaldoInicial()");

        Assert.notNull(tPosicionNombradaParamsPersistence,
                "El objeto de parametros recibido esta null");

        
        final PageVO pagAux = tPosicionNombradaParamsPersistence.getPageVO() != null ? 
                tPosicionNombradaParamsPersistence.getPageVO() : new PageVO();

        List listaTPosicionNombrada = null;

        Long countRegistros = (Long) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {

            	StringBuffer query = new StringBuffer();
            	List<Type> tipos = new ArrayList<Type>();
            	List<Object> params = new ArrayList<Object>();
            	
            	query.append("SELECT COUNT(*) ");
            	query.append("FROM " + PosicionNombrada.class.getName() + " pn ");
            	query.append("	LEFT OUTER JOIN pn.posicionNombradaHistorico pnh ");
            	query.append("		WITH pnh.fecha = trunc(?) ");
				query.append("	JOIN pn.cupon c ");
				query.append("	JOIN c.emision e ");
				query.append("	JOIN e.instrumento ins ");
				query.append("	JOIN e.emisora es ");
				query.append("	JOIN pn.cuentaNombrada cn ");
				query.append("	JOIN cn.tipoCuenta tc ");
				query.append("	JOIN cn.institucion i ");
				query.append("	JOIN i.tipoInstitucion ti ");
				query.append("	JOIN pn.boveda bov ");
            	query.append("WHERE c.idEstadoCupon = ? ");
            	query.append("	AND ");
            	query.append("	( ");
            	query.append("		(pn.posicionDisponible+pn.posicionNoDisponible) > 0 ");
            	query.append("		OR EXISTS ( ");
            	query.append("			SELECT edo ");
            	query.append("			FROM " + EstadoPosicionNombrada.class.getName() + " edo ");
            	query.append("			WHERE edo.idPosicion = pn.idPosicion ");
            	query.append("				AND edo.ciclo.estado = 1 ");
            	query.append("				AND edo.ciclo.fechaInicio BETWEEN ? AND ? ");
            	query.append("		) ");
            	query.append("	) ");
            	
            	// FECHA DE AYER PARA LOS HISTORICOS
            	tipos.add(new TimestampType());
            	params.add(tPosicionNombradaParamsPersistence.getFechaInicio());
            	tipos.add(new BigIntegerType());
            	params.add(VIGENTE);
            	// FECHA ACTUAL PARA LAS OPERACIONES
            	Date[] fechasActuales = 
            		DateUtil.preparaIntervaloFechas(tPosicionNombradaParamsPersistence.getFechaMovimiento(), 
            				tPosicionNombradaParamsPersistence.getFechaMovimiento());
            	tipos.add(new TimestampType());
            	params.add(fechasActuales[0]);
            	tipos.add(new TimestampType());
            	params.add(fechasActuales[1]);
            	
            	if (StringUtils.isNotBlank(tPosicionNombradaParamsPersistence.getIdInstitucion())) {
            		 query.append("	AND ti.claveTipoInstitucion = ? ");
            		 tipos.add(new StringType());
            		 params.add(tPosicionNombradaParamsPersistence.getIdInstitucion());
                }

                if (StringUtils.isNotBlank(tPosicionNombradaParamsPersistence.getFolioInstitucion())) {
                	 query.append("	AND i.folioInstitucion = ? ");
            		 tipos.add(new StringType());
            		 params.add(tPosicionNombradaParamsPersistence.getFolioInstitucion());
                }
                 
                if(tPosicionNombradaParamsPersistence.getCuentas() != null
                 		&& StringUtils.isNotBlank(tPosicionNombradaParamsPersistence.getCuentas()[0])) {
                	 query.append("	AND cn.cuenta = ? ");
            		 tipos.add(new StringType());
            		 params.add(tPosicionNombradaParamsPersistence.getCuentas()[0]);
                }
                
                if (tPosicionNombradaParamsPersistence.getTiposDeValor() != null 
                		&& StringUtils.isNotBlank(tPosicionNombradaParamsPersistence.getTiposDeValor()[0])) {
                	query.append("	AND ins.claveTipoValor = ? ");
	           		tipos.add(new StringType());
	           		params.add(tPosicionNombradaParamsPersistence.getTiposDeValor()[0]);
                }

                if (StringUtils.isNotBlank(tPosicionNombradaParamsPersistence.getEmisora())) {
                	query.append("	AND es.clavePizarra = ? ");
	           		tipos.add(new StringType());
	           		params.add(tPosicionNombradaParamsPersistence.getEmisora());
                }

                if (StringUtils.isNotBlank(tPosicionNombradaParamsPersistence.getSerie())) {
                	query.append("	AND e.serie = ? ");
	           		tipos.add(new StringType());
	           		params.add(tPosicionNombradaParamsPersistence.getSerie());
                }
                
                if (StringUtils.isNotBlank(tPosicionNombradaParamsPersistence.getCupon())) {
                	query.append("	AND c.claveCupon = ? ");
	           		tipos.add(new StringType());
	           		params.add(tPosicionNombradaParamsPersistence.getCupon());
                }
            	
                if (StringUtils.isNotBlank(tPosicionNombradaParamsPersistence.getIsin())) {
                	query.append("	AND e.isin = ? ");
	           		tipos.add(new StringType());
	           		params.add(tPosicionNombradaParamsPersistence.getIsin());
                }

                /* Filtro de Boveda */
                if(tPosicionNombradaParamsPersistence.getIdBoveda()!=null
                		&& !tPosicionNombradaParamsPersistence.getIdBoveda().equals(BigInteger.ZERO) ) {
                	query.append("	AND bov.idBoveda = ? ");
	           		tipos.add(new BigIntegerType());
	           		params.add(tPosicionNombradaParamsPersistence.getIdBoveda());
                }
                
                return  session.createQuery(query.toString()).setParameters(params.toArray(), tipos.toArray(new Type[]{})).uniqueResult();
            }

        });

        logger.info("Registros encontrados: [" + countRegistros + "]");

        if ( countRegistros != null && countRegistros.intValue() > 0) {

            listaTPosicionNombrada = (List) getHibernateTemplate().execute(new HibernateCallback() {

            	public Object doInHibernate(Session session) throws HibernateException,
                        SQLException {
                	
            		StringBuffer query = new StringBuffer();
					List<Type> tipos = new ArrayList<Type>();
					List<Object> params = new ArrayList<Object>();

					query.append("SELECT pn, pnh.posicionDisponible ");
					query.append("FROM " + PosicionNombrada.class.getName() + " pn ");
	            	query.append("	LEFT OUTER JOIN pn.posicionNombradaHistorico pnh ");
	            	query.append("		WITH pnh.fecha = trunc(?) ");
					query.append("	JOIN FETCH pn.cupon c ");
					query.append("	JOIN FETCH c.emision e ");
					query.append("	JOIN FETCH e.instrumento ins ");
					query.append("	JOIN FETCH e.emisora es ");
					query.append("	JOIN FETCH pn.cuentaNombrada cn ");
					query.append("	JOIN FETCH cn.tipoCuenta tc ");
					query.append("	JOIN FETCH cn.institucion i ");
					query.append("	JOIN FETCH i.tipoInstitucion ti ");
					query.append("	JOIN FETCH pn.boveda bov ");
					query.append("WHERE c.idEstadoCupon = ? ");
	            	query.append("	AND ");
	            	query.append("	( ");
	            	query.append("		(pn.posicionDisponible+pn.posicionNoDisponible) > 0 ");
	            	query.append("		OR EXISTS ( ");
	            	query.append("			SELECT edo ");
	            	query.append("			FROM " + EstadoPosicionNombrada.class.getName() + " edo ");
	            	query.append("			WHERE edo.idPosicion = pn.idPosicion ");
	            	query.append("				AND edo.ciclo.estado = 1 ");
	            	query.append("				AND edo.ciclo.fechaInicio BETWEEN ? AND ? ");
	            	query.append("		) ");
	            	query.append("	) ");
	            	
	            	// FECHA DE AYER PARA LOS HISTORICOS
	            	tipos.add(new TimestampType());
	            	params.add(tPosicionNombradaParamsPersistence.getFechaInicio());
	            	tipos.add(new BigIntegerType());
	            	params.add(VIGENTE);
	            	// FECHA ACTUAL PARA LAS OPERACIONES
	            	Date[] fechasActuales = 
	            		DateUtil.preparaIntervaloFechas(tPosicionNombradaParamsPersistence.getFechaMovimiento(), 
	            				tPosicionNombradaParamsPersistence.getFechaMovimiento());
	            	tipos.add(new TimestampType());
	            	params.add(fechasActuales[0]);
	            	tipos.add(new TimestampType());
	            	params.add(fechasActuales[1]);
	            	
	            	if (StringUtils.isNotBlank(tPosicionNombradaParamsPersistence.getIdInstitucion())) {
	            		 query.append("	AND ti.claveTipoInstitucion = ? ");
	            		 tipos.add(new StringType());
	            		 params.add(tPosicionNombradaParamsPersistence.getIdInstitucion());
	                }

	                if (StringUtils.isNotBlank(tPosicionNombradaParamsPersistence.getFolioInstitucion())) {
	                	 query.append("	AND i.folioInstitucion = ? ");
	            		 tipos.add(new StringType());
	            		 params.add(tPosicionNombradaParamsPersistence.getFolioInstitucion());
	                }
	                 
	                if(tPosicionNombradaParamsPersistence.getCuentas() != null
	                 		&& StringUtils.isNotBlank(tPosicionNombradaParamsPersistence.getCuentas()[0])) {
	                	 query.append("	AND cn.cuenta = ? ");
	            		 tipos.add(new StringType());
	            		 params.add(tPosicionNombradaParamsPersistence.getCuentas()[0]);
	                }
	                
	                if (tPosicionNombradaParamsPersistence.getTiposDeValor() != null 
	                		&& StringUtils.isNotBlank(tPosicionNombradaParamsPersistence.getTiposDeValor()[0])) {
	                	query.append("	AND ins.claveTipoValor = ? ");
		           		tipos.add(new StringType());
		           		params.add(tPosicionNombradaParamsPersistence.getTiposDeValor()[0]);
	                }

	                if (StringUtils.isNotBlank(tPosicionNombradaParamsPersistence.getEmisora())) {
	                	query.append("	AND es.clavePizarra = ? ");
		           		tipos.add(new StringType());
		           		params.add(tPosicionNombradaParamsPersistence.getEmisora());
	                }

	                if (StringUtils.isNotBlank(tPosicionNombradaParamsPersistence.getSerie())) {
	                	query.append("	AND e.serie = ? ");
		           		tipos.add(new StringType());
		           		params.add(tPosicionNombradaParamsPersistence.getSerie());
	                }
	                
	                if (StringUtils.isNotBlank(tPosicionNombradaParamsPersistence.getCupon())) {
	                	query.append("	AND c.claveCupon = ? ");
		           		tipos.add(new StringType());
		           		params.add(tPosicionNombradaParamsPersistence.getCupon());
	                }
	            	
	                if (StringUtils.isNotBlank(tPosicionNombradaParamsPersistence.getIsin())) {
	                	query.append("	AND e.isin = ? ");
		           		tipos.add(new StringType());
		           		params.add(tPosicionNombradaParamsPersistence.getIsin());
	                }

	                /* Filtro de Boveda */
	                if(tPosicionNombradaParamsPersistence.getIdBoveda()!=null
	                		&& !tPosicionNombradaParamsPersistence.getIdBoveda().equals(BigInteger.ZERO) ) {
	                	query.append("	AND bov.idBoveda = ? ");
		           		tipos.add(new BigIntegerType());
		           		params.add(tPosicionNombradaParamsPersistence.getIdBoveda());
	                }
                    
                    query.append("ORDER BY ins.claveTipoValor, " +
                    		"es.clavePizarra, " +
                    		"e.serie, " +
                    		"c.claveCupon ");
                    
                    Query q = session.createQuery(query.toString()).setParameters(params.toArray(), tipos.toArray(new Type[]{}));
                    
                    if (pagAux.getRegistrosXPag().intValue() > 0) {
                    	q.setFirstResult(pagAux.getOffset().intValue());
                    	q.setMaxResults(pagAux.getRegistrosXPag().intValue());
                    }
                    
                    return q.list();
                }

            });
        }

        PageVO pageReturn = new PageVO();
        pageReturn.setTotalRegistros(countRegistros != null ? countRegistros.intValue() : 0);
        pageReturn.setRegistros(listaTPosicionNombrada);

        return pageReturn;
	}

	public BigInteger getSaldoInicial( final
			TPosicionNombradaParamsPersistence tPosicionNombradaParamsPersistence) {
		
		 BigInteger saldoInicial = (BigInteger) getHibernateTemplate().execute(new HibernateCallback() {

         	@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session) throws HibernateException,
                     SQLException {
		
         		StringBuffer query = new StringBuffer();
				List<Type> tipos = new ArrayList<Type>();
				List<Object> params = new ArrayList<Object>();
				
				query.append(" SELECT pnh.posicionDisponible ");
				query.append(" FROM " + PosicionNombradaHistorico.class.getName() + " pnh ");
				query.append("	JOIN pnh.cupon c ");
				query.append("	JOIN c.emision e ");
				query.append("	JOIN e.instrumento ins ");
				query.append("	JOIN e.emisora es ");
				query.append("	JOIN pnh.cuentaNombrada cn ");
				query.append("	JOIN cn.tipoCuenta tc ");
				query.append("	JOIN cn.institucion i ");
				query.append("	JOIN i.tipoInstitucion ti ");
				query.append("	JOIN pnh.boveda bov ");
				query.append(" WHERE pnh.fecha = trunc(?) ");
				tipos.add(new TimestampType());
		    	params.add(tPosicionNombradaParamsPersistence.getFechaInicio());
		    	
		    	query.append(" 	AND c.idEstadoCupon = ? ");
		    	tipos.add(new BigIntegerType());
		    	params.add(VIGENTE);
		    	
		    	if (StringUtils.isNotBlank(tPosicionNombradaParamsPersistence.getIdInstitucion())) {
           		 query.append("	AND ti.claveTipoInstitucion = ? ");
           		 tipos.add(new StringType());
           		 params.add(tPosicionNombradaParamsPersistence.getIdInstitucion());
               }

               if (StringUtils.isNotBlank(tPosicionNombradaParamsPersistence.getFolioInstitucion())) {
               	 query.append("	AND i.folioInstitucion = ? ");
           		 tipos.add(new StringType());
           		 params.add(tPosicionNombradaParamsPersistence.getFolioInstitucion());
               }
                
               if(tPosicionNombradaParamsPersistence.getCuentas() != null
                		&& StringUtils.isNotBlank(tPosicionNombradaParamsPersistence.getCuentas()[0])) {
               	 query.append("	AND cn.cuenta = ? ");
           		 tipos.add(new StringType());
           		 params.add(tPosicionNombradaParamsPersistence.getCuentas()[0]);
               }
               
               if (tPosicionNombradaParamsPersistence.getTiposDeValor() != null 
               		&& StringUtils.isNotBlank(tPosicionNombradaParamsPersistence.getTiposDeValor()[0])) {
               	query.append("	AND ins.claveTipoValor = ? ");
	           		tipos.add(new StringType());
	           		params.add(tPosicionNombradaParamsPersistence.getTiposDeValor()[0]);
               }

               if (StringUtils.isNotBlank(tPosicionNombradaParamsPersistence.getEmisora())) {
               	query.append("	AND es.clavePizarra = ? ");
	           		tipos.add(new StringType());
	           		params.add(tPosicionNombradaParamsPersistence.getEmisora());
               }

               if (StringUtils.isNotBlank(tPosicionNombradaParamsPersistence.getSerie())) {
               	query.append("	AND e.serie = ? ");
	           		tipos.add(new StringType());
	           		params.add(tPosicionNombradaParamsPersistence.getSerie());
               }
               
               if (StringUtils.isNotBlank(tPosicionNombradaParamsPersistence.getCupon())) {
               	query.append("	AND c.claveCupon = ? ");
	           		tipos.add(new StringType());
	           		params.add(tPosicionNombradaParamsPersistence.getCupon());
               }
           	
               if (StringUtils.isNotBlank(tPosicionNombradaParamsPersistence.getIsin())) {
               	query.append("	AND e.isin = ? ");
	           		tipos.add(new StringType());
	           		params.add(tPosicionNombradaParamsPersistence.getIsin());
               }

               /* Filtro de Boveda */
               if(tPosicionNombradaParamsPersistence.getIdBoveda()!=null
               		&& !tPosicionNombradaParamsPersistence.getIdBoveda().equals(BigInteger.ZERO) ) {
               	query.append("	AND bov.idBoveda = ? ");
	           		tipos.add(new BigIntegerType());
	           		params.add(tPosicionNombradaParamsPersistence.getIdBoveda());
               }
				
               Query q = session.createQuery(query.toString()).setParameters(params.toArray(), tipos.toArray(new Type[]{}));
               
               List lista = q.list();
               
               BigInteger retorno = BigInteger.ZERO;
               
               if( lista!= null && lista.size() > 0 ) {
            	   logger.debug("Tamaño de la lista: [" + lista.size() + "]");
            	   retorno = (BigInteger)lista.get(0);
               } else {
            	   logger.warn("No existe algun elemento que cuente con los parametros de busqueda");
               }
               
				return retorno;
         	}
		 });
		
		return saldoInicial;
	}

	public BigInteger getSaldoActual(
			final TPosicionNombradaParamsPersistence tPosicionNombradaParamsPersistence) {
		BigInteger saldoInicial = (BigInteger) getHibernateTemplate().execute(new HibernateCallback() {

         	@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session) throws HibernateException,
                     SQLException {
		
         		StringBuffer query = new StringBuffer();
				List<Type> tipos = new ArrayList<Type>();
				List<Object> params = new ArrayList<Object>();
				
				query.append(" SELECT pn.posicionDisponible ");
				query.append(" FROM " + PosicionNombrada.class.getName() + " pn ");
				query.append("	JOIN pn.cupon c ");
				query.append("	JOIN c.emision e ");
				query.append("	JOIN e.instrumento ins ");
				query.append("	JOIN e.emisora es ");
				query.append("	JOIN pn.cuentaNombrada cn ");
				query.append("	JOIN cn.tipoCuenta tc ");
				query.append("	JOIN cn.institucion i ");
				query.append("	JOIN i.tipoInstitucion ti ");
				query.append("	JOIN pn.boveda bov ");
				query.append(" WHERE c.idEstadoCupon = ? ");
		    	tipos.add(new BigIntegerType());
		    	params.add(VIGENTE);
		    	
		    	if (StringUtils.isNotBlank(tPosicionNombradaParamsPersistence.getIdInstitucion())) {
           		 query.append("	AND ti.claveTipoInstitucion = ? ");
           		 tipos.add(new StringType());
           		 params.add(tPosicionNombradaParamsPersistence.getIdInstitucion());
               }

               if (StringUtils.isNotBlank(tPosicionNombradaParamsPersistence.getFolioInstitucion())) {
               	 query.append("	AND i.folioInstitucion = ? ");
           		 tipos.add(new StringType());
           		 params.add(tPosicionNombradaParamsPersistence.getFolioInstitucion());
               }
                
               if(tPosicionNombradaParamsPersistence.getCuentas() != null
                		&& StringUtils.isNotBlank(tPosicionNombradaParamsPersistence.getCuentas()[0])) {
               	 query.append("	AND cn.cuenta = ? ");
           		 tipos.add(new StringType());
           		 params.add(tPosicionNombradaParamsPersistence.getCuentas()[0]);
               }
               
               if (tPosicionNombradaParamsPersistence.getTiposDeValor() != null 
               		&& StringUtils.isNotBlank(tPosicionNombradaParamsPersistence.getTiposDeValor()[0])) {
               	query.append("	AND ins.claveTipoValor = ? ");
	           		tipos.add(new StringType());
	           		params.add(tPosicionNombradaParamsPersistence.getTiposDeValor()[0]);
               }

               if (StringUtils.isNotBlank(tPosicionNombradaParamsPersistence.getEmisora())) {
               	query.append("	AND es.clavePizarra = ? ");
	           		tipos.add(new StringType());
	           		params.add(tPosicionNombradaParamsPersistence.getEmisora());
               }

               if (StringUtils.isNotBlank(tPosicionNombradaParamsPersistence.getSerie())) {
               	query.append("	AND e.serie = ? ");
	           		tipos.add(new StringType());
	           		params.add(tPosicionNombradaParamsPersistence.getSerie());
               }
               
               if (StringUtils.isNotBlank(tPosicionNombradaParamsPersistence.getCupon())) {
               	query.append("	AND c.claveCupon = ? ");
	           		tipos.add(new StringType());
	           		params.add(tPosicionNombradaParamsPersistence.getCupon());
               }
           	
               if (StringUtils.isNotBlank(tPosicionNombradaParamsPersistence.getIsin())) {
               	query.append("	AND e.isin = ? ");
	           		tipos.add(new StringType());
	           		params.add(tPosicionNombradaParamsPersistence.getIsin());
               }

               /* Filtro de Boveda */
               if(tPosicionNombradaParamsPersistence.getIdBoveda()!=null
               		&& !tPosicionNombradaParamsPersistence.getIdBoveda().equals(BigInteger.ZERO) ) {
               	query.append("	AND bov.idBoveda = ? ");
	           		tipos.add(new BigIntegerType());
	           		params.add(tPosicionNombradaParamsPersistence.getIdBoveda());
               }
				
               Query q = session.createQuery(query.toString()).setParameters(params.toArray(), tipos.toArray(new Type[]{}));
               
               List lista = q.list();
               
               BigInteger retorno = BigInteger.ZERO;
               
               if( lista!= null && lista.size() > 0 ) {
            	   logger.debug("Tamaño de la lista: [" + lista.size() + "]");
            	   retorno = (BigInteger)lista.get(0);
               } else {
            	   logger.warn("No existe algun elemento que cuente con los parametros de busqueda");
               }
               
				return retorno;
         	}
		 });
		
		return saldoInicial;
	}

	@SuppressWarnings("unchecked")
	public List<PosicionDTO> getCuentasParaFondeoMatch(final AgenteVO agenteFirmado, final EmisionVO emision) {
		List<PosicionNombrada> listaPosiciones = (List<PosicionNombrada>) getHibernateTemplate().execute(new HibernateCallback() {

         	
			public Object doInHibernate(Session session) throws HibernateException,
                     SQLException {
		
         		StringBuffer query = new StringBuffer();
				List<Type> tipos = new ArrayList<Type>();
				List<Object> params = new ArrayList<Object>();
				
				query.append(" FROM " + PosicionNombrada.class.getName() + " pn ");
				query.append("	JOIN FETCH pn.cupon c ");
				query.append("	JOIN FETCH c.emision e ");
				query.append("	LEFT OUTER JOIN FETCH e.divisa div ");
				query.append("	JOIN FETCH e.instrumento ins ");
				query.append("	JOIN FETCH ins.mercado mer ");
				query.append("	JOIN FETCH e.emisora es ");
				query.append("	JOIN FETCH pn.cuentaNombrada cn ");
				query.append("	JOIN FETCH cn.tipoCuenta tc ");
				query.append("	JOIN FETCH cn.institucion i ");
				query.append("	JOIN FETCH i.tipoInstitucion ti ");
				query.append("	JOIN FETCH pn.boveda bov ");
				// Filtro de cupon vigente
				query.append(" WHERE c.idEstadoCupon = ? ");
		    	tipos.add(new BigIntegerType());
		    	params.add(VIGENTE);
		    	// Filtro de posicion mayor a 0
		    	query.append(" AND pn.posicionDisponible > 0 ");
		    	// Filtro de cuentas aceptadas
		    	query.append(" AND tc.naturalezaProcesoLiquidacion = ? ");
		    	tipos.add(new StringType());
		    	params.add(TipoCuentaConstants.TIPO_NOMBRADA);
		    	
		    	query.append(" AND tc.naturalezaContable = ? ");
		    	tipos.add(new StringType());
		    	params.add(TipoNaturalezaDTO.PASIVO);
		    	
		    	query.append(" AND tc.tipoTenencia = ? ");
		    	tipos.add(new StringType());
		    	params.add(TipoTenenciaConstants.CIRCULANTE);
		    	
		    	query.append(" AND TRIM(tc.claveSubgrupo) NOT IN ('GARANTIAS') ");
		    	
		    	query.append(" AND TRIM(cn.cuenta) NOT IN ('0050','0051','0052','0053','0054','0055','0056','0057') ");
		    	
		    	
		    	if (StringUtils.isNotBlank(agenteFirmado.getId())) {
           		 query.append("	AND ti.claveTipoInstitucion = ? ");
           		 tipos.add(new StringType());
           		 params.add(agenteFirmado.getId());
               }

               if (StringUtils.isNotBlank(agenteFirmado.getFolio())) {
               	 query.append("	AND i.folioInstitucion = ? ");
           		 tipos.add(new StringType());
           		 params.add(agenteFirmado.getFolio());
               }
               
               if (StringUtils.isNotBlank(agenteFirmado.getCuenta())) {
                 	 query.append("	AND NOT (trim(cn.cuenta) = ?) ");
             		 tipos.add(new StringType());
             		 params.add(agenteFirmado.getCuenta());
                 }
                
               if (StringUtils.isNotBlank(emision.getIdTipoValor())) {
               	query.append("	AND ins.claveTipoValor = ? ");
	           		tipos.add(new StringType());
	           		params.add(emision.getIdTipoValor());
               }

               if (StringUtils.isNotBlank(emision.getEmisora())) {
               	query.append("	AND es.clavePizarra = ? ");
	           		tipos.add(new StringType());
	           		params.add(emision.getEmisora());
               }

               if (StringUtils.isNotBlank(emision.getSerie())) {
               	query.append("	AND e.serie = ? ");
	           		tipos.add(new StringType());
	           		params.add(emision.getSerie());
               }
               
               if (StringUtils.isNotBlank(emision.getCupon())) {
               	query.append("	AND c.claveCupon = ? ");
	           		tipos.add(new StringType());
	           		params.add(emision.getCupon());
               }
           	
               if (StringUtils.isNotBlank(emision.getIsin())) {
               	query.append("	AND e.isin = ? ");
	           		tipos.add(new StringType());
	           		params.add(emision.getIsin());
               }

               Query q = session.createQuery(query.toString()).setParameters(params.toArray(), tipos.toArray(new Type[]{}));
               
               List lista = q.list();
               
				return lista;
         	}
		 });
		
		List<PosicionDTO> retorno = new ArrayList<PosicionDTO>();
		
		if( listaPosiciones != null && listaPosiciones .size() > 0 ) {
			for( PosicionNombrada posicion : listaPosiciones ) {
				retorno.add(DTOAssembler.crearPosicionNombradaDTO(posicion));
			}
		}
		
		return retorno;
	}
}
