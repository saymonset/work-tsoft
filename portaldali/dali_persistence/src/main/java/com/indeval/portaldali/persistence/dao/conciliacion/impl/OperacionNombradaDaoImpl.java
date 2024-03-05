/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.dao.conciliacion.impl;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.indeval.portaldali.persistence.dao.conciliacion.OperacionNombradaDao;
import com.indeval.portaldali.persistence.model.EstadoInstruccion;
import com.indeval.portaldali.persistence.model.OperacionNombrada;
import com.indeval.portaldali.persistence.util.Constantes;
import com.indeval.portaldali.persistence.util.DateUtil;
import com.indeval.portaldali.persistence.util.PageableDetachedCriteriaDali;
import com.indeval.portaldali.persistence.vo.AgentePK;
import com.indeval.portaldali.persistence.vo.EmisionPK;
import com.indeval.portaldali.persistence.vo.PageVO;

/**
 * Implementaci&oacute;n de los servicios para las operaciones realizadas sobre la tabla 
 * nombrada de registros contables de valores.
 * 
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 *
 */
public class OperacionNombradaDaoImpl extends HibernateDaoSupport implements OperacionNombradaDao {

	private static final BigInteger VIGENTE = new BigInteger("1");
    private static final BigInteger LIQUIDADA = BigInteger.valueOf(6);
    private static final BigInteger LIQUIDADA_CON_BANCO_DE_TRABAJO = BigInteger.valueOf(9);
    
    
    private String[] cuentasTransicion = {"0030","0031"};
    
    private BigInteger[] estadosInstruccion = {LIQUIDADA, LIQUIDADA_CON_BANCO_DE_TRABAJO};
    
    private String[] tiposOperacion = {"A", "V"};
    
    /** Objeto de loggeo */
    private static final Logger logger = LoggerFactory.getLogger(OperacionNombradaDaoImpl.class);
    
   
    /**
     * @see com.indeval.portaldali.persistence.dao.conciliacion.OperacionNombradaDao#findDetalleArchivoConciliacion(com.indeval.portaldali.persistence.vo.AgentePK, com.indeval.portaldali.persistence.vo.EmisionPK, com.indeval.portaldali.persistence.vo.PageVO)
     */
    public PageVO findDetalleArchivoConciliacion(BigInteger idsPosicion, PageVO pageVO) {
        
        logger.info("Entrando a OperacionNombradaDaoImpl.findDetalleArchivoConciliacion()");
        
        /* Valida los parametros obligatorios */
        if (idsPosicion == null) {
            throw new IllegalArgumentException("Falta el idPosicion");
        }
        PageableDetachedCriteriaDali criteria = 
            PageableDetachedCriteriaDali.createPageableDetachedCriteria(OperacionNombrada.class);
        
        /* Se arma la estructura de Alias */
        criteria.createAlias("posicionReceptor", "pr");  
        criteria.createAlias("posicionNombrada", "pt");
        criteria.createAlias("instruccion","il");
        criteria.createAlias("tipoOperacion", "tp");
        
        /* Se preparan los Junctions */
        Junction conjunctionReceptor = Restrictions.conjunction().add(Restrictions.eq("pr.idPosicion", idsPosicion));
        Junction conjunctionTraspasante = Restrictions.conjunction().add(Restrictions.eq("pt.idPosicion", idsPosicion));
        
        /* Se verifica si se requieren los movimientos y se agrega la condicion */
        if(pageVO.getValores() != null && pageVO.getValores().containsKey(FECHA_FIN_AYER)){
        	Date fechaActual = (Date) pageVO.getValores().get(FECHA_FIN_AYER);
        	Date[] fechas = DateUtil.preparaIntervaloFechas(fechaActual, fechaActual);
        	criteria.add(Restrictions.between("il.fechaAplicacion", fechas[0], fechas[1]));
        }
        criteria.add(Restrictions.in("il.idEstadoInstruccion", estadosInstruccion));
        criteria.add(Restrictions.eq("il.bancoTrabajo", Boolean.FALSE));
        String arrayClaveTipoOp[] = {Constantes.BLOQUEO_TITULOS,Constantes.DESBLOQUEO_TITULOS,Constantes.ENTREGA_CONTRAENTREGA};
        criteria.add(Restrictions.not(Restrictions.in("tp.claveTipoOperacion", arrayClaveTipoOp)));
        
        criteria.add(Restrictions.disjunction().add(conjunctionReceptor).add(conjunctionTraspasante));

        logger.debug("QUERY DETALLE ARCHIVO DE CONCILIACION = [" + criteria.toString() + "]");
        return criteria.pageResult(pageVO, this.getHibernateTemplate());
        
    }
    
    /**
     * @see com.indeval.portaldali.persistence.dao.conciliacion.OperacionNombradaDao#findMovimientosArchivoConciliacion(com.indeval.portaldali.persistence.vo.AgentePK, com.indeval.portaldali.persistence.vo.EmisionPK, com.indeval.portaldali.persistence.vo.PageVO)
     */
    public PageVO findMovimientosArchivoConciliacion(AgentePK agenteFirmado, EmisionPK emisionPK, PageVO pageVO) {
    	
    	logger.info("Entrando a OperacionNombradaDaoImpl.findMovimientosArchivoConciliacion()");
    	  
        /* Valida los parametros obligatorios */
        if (agenteFirmado == null) {
            throw new IllegalArgumentException("Falta el AgentePK");
        }
        else {
            if (StringUtils.isBlank(agenteFirmado.getIdInst()) ||
                    StringUtils.isBlank(agenteFirmado.getFolioInst())) {
                throw new IllegalArgumentException("Falta algun dato obligatorio (Id, Folio) del AgentePK");
            }
        }
        
        PageableDetachedCriteriaDali criteria = 
            PageableDetachedCriteriaDali.createPageableDetachedCriteria(OperacionNombrada.class);
        
        /* Se arma la estructura de Alias */
        criteria.createAlias("posicionReceptor", "pr");
        criteria.createAlias("pr.boveda", "bovr");
        criteria.createAlias("pr.cuentaNombrada", "cr");
        criteria.createAlias("cr.institucion", "ir");
        criteria.createAlias("ir.tipoInstitucion", "tir");
        criteria.createAlias("pr.cupon", "ocr");
        criteria.createAlias("ocr.estadoCupon", "ocer");           
        criteria.createAlias("ocr.emision", "per");
        criteria.createAlias("per.instrumento", "peir");
        criteria.createAlias("per.emisora", "peer");
        
        criteria.createAlias("posicionNombrada", "pt");
        criteria.createAlias("pt.boveda", "bovt");
        criteria.createAlias("pt.cuentaNombrada", "ct");
        criteria.createAlias("ct.institucion", "it");
        criteria.createAlias("it.tipoInstitucion", "tit");
        criteria.createAlias("pt.cupon", "oct");
        criteria.createAlias("oct.estadoCupon", "ocet");           
        criteria.createAlias("oct.emision", "pet");
        criteria.createAlias("pet.instrumento", "peit");
        criteria.createAlias("pet.emisora", "peet");
        
        criteria.createAlias("instruccion","il");
        criteria.createAlias("tipoOperacion", "tp");
        
        /* Se preparan los Junctions */
        Junction conjunctionTraspasante = Restrictions.conjunction().add(
                Restrictions.eq("tir.claveTipoInstitucion", agenteFirmado.getIdInst().trim()))
                .add(Restrictions.eq("ir.folioInstitucion", agenteFirmado.getFolioInst().trim()));
        
        Junction conjuctionReceptor = Restrictions.conjunction().add(
                Restrictions.eq("tit.claveTipoInstitucion", agenteFirmado.getIdInst().trim()))
                .add(Restrictions.eq("it.folioInstitucion", agenteFirmado.getFolioInst().trim()));
        
        /* Se incorpora el filtro de cuenta en caso de ser requerido */
        if(StringUtils.isNotBlank(agenteFirmado.getCuenta())){
            conjunctionTraspasante.add(Restrictions.eq("cr.cuenta", agenteFirmado.getCuenta().trim()));
            conjuctionReceptor.add(Restrictions.eq("ct.cuenta", agenteFirmado.getCuenta().trim()));
        }
        else{
            conjunctionTraspasante.add(Restrictions.not(Restrictions.in("cr.cuenta", cuentasTransicion)));
            conjuctionReceptor.add(Restrictions.not(Restrictions.in("ct.cuenta", cuentasTransicion)));
        }

        /* Se incorpora el filtro de boveda en caso de ser requerido */
        if(pageVO.getValores() != null && pageVO.getValores().containsKey(BOVEDA)){
        	conjunctionTraspasante.add(Restrictions.eq("bovr.idBoveda", (BigInteger)pageVO.getValores().get(BOVEDA)));
        	conjuctionReceptor.add(Restrictions.eq("bovt.idBoveda", (BigInteger)pageVO.getValores().get(BOVEDA)));
        }
        
        /* Se incorpora el filtro de clave valor en caso de ser requerido */
        if(emisionPK != null) {
            
            if(StringUtils.isNotBlank(emisionPK.getTv())){
            	conjunctionTraspasante.add(Restrictions.eq("peit.claveTipoValor", emisionPK.getTv().trim()));
            	conjuctionReceptor.add(Restrictions.eq("peir.claveTipoValor", emisionPK.getTv().trim()));
            }
            if(StringUtils.isNotBlank(emisionPK.getEmisora())){
            	conjunctionTraspasante.add(Restrictions.eq("peet.clavePizarra", emisionPK.getEmisora().trim()));
            	conjuctionReceptor.add(Restrictions.eq("peer.clavePizarra", emisionPK.getEmisora().trim()));
            }
            if(StringUtils.isNotBlank(emisionPK.getSerie())){
            	conjunctionTraspasante.add(Restrictions.eq("pet.serie", emisionPK.getSerie().trim()));
            	conjuctionReceptor.add(Restrictions.eq("per.serie", emisionPK.getSerie().trim()));
            }
            if(StringUtils.isNotBlank(emisionPK.getCupon())){
            	conjunctionTraspasante.add(Restrictions.eq("oct.claveCupon", emisionPK.getCupon().trim()));
            	conjuctionReceptor.add(Restrictions.eq("ocr.claveCupon", emisionPK.getCupon().trim()));
            }
            
        }

        /* Se verifica si se requieren los movimientos y se agrega la condicion */
        if(pageVO.getValores() != null && pageVO.getValores().containsKey(FECHA_FIN_AYER)){
        	Date fechaActual = (Date) pageVO.getValores().get(FECHA_FIN_AYER);
        	Date[] fechas = DateUtil.preparaIntervaloFechas(fechaActual, fechaActual);
        	logger.info("Fechas : [" + fechas[0] + "]-[" + fechas[1] + "]");
        	criteria.add(Restrictions.between("il.fechaAplicacion", fechas[0], fechas[1]));
        }
        
        criteria.add(Restrictions.in("il.idEstadoInstruccion", estadosInstruccion));
        criteria.add(Restrictions.eq("il.bancoTrabajo", Boolean.FALSE));
        
        String arrayClaveTipoOp[] = {Constantes.BLOQUEO_TITULOS,Constantes.DESBLOQUEO_TITULOS,Constantes.ENTREGA_CONTRAENTREGA};
        criteria.add(Restrictions.not(Restrictions.in("tp.claveTipoOperacion", arrayClaveTipoOp)));
        
        criteria.add(Restrictions.disjunction().add(
                (Criterion)conjunctionTraspasante).add((Criterion)conjuctionReceptor));
        
        logger.debug("QUERY MOVIMIENTOS ARCHIVO DE CONCILIACION = [" + criteria.toString() + "]");
        return criteria.pageResult(pageVO, this.getHibernateTemplate());
        
    }
    
    /**
	 * @see 
	 * 	com.indeval.portaldali.persistence.dao.common.OperacionNombradaDao.findNumeroTitulosEmisionesExtranjeras(com.indeval.portaldali.persistence.model.EmisionPK)
	 */
	@SuppressWarnings("unchecked")
	public BigInteger findNumeroTitulosEmisionesExtranjeras(final EmisionPK emisionPK) {
		BigInteger noTitulos = null;
		
		Number resultado = (Number) getHibernateTemplate().execute(new HibernateCallback() {
			 public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuffer query = new StringBuffer();
				Map<String, Comparable> params = new HashMap<String, Comparable>();
				
				query.append("SELECT SUM(op.numeroTitulos) ");
				query.append("FROM " + OperacionNombrada.class.getName() + " op join op.cupon c ");
				query.append("join c.emision e join e.instrumento ei join e.emisora ee ");
				query.append("WHERE op.instruccion.estadoInstruccion IN (:pendiente,:liq,:liqBancoTrabajo) ");
				query.append("AND e.emisionExtranjera IS NOT NULL AND e.serie = :serie AND ei.claveTipoValor = :tv AND ee.descEmisora = :emisora");
				params.put("pendiente", EstadoInstruccion.PENDIENTE);
				params.put("liq", EstadoInstruccion.LIQUIDADA);
				params.put("liqBancoTrabajo", EstadoInstruccion.LIQUIDADA_CON_BCO_TRAB);
				params.put("serie", emisionPK.getSerie());
				params.put("tv", emisionPK.getTv());
				params.put("emisora", emisionPK.getEmisora());
				
				Query hQuery = session.createQuery(query.toString());
				hQuery.setProperties(params);
				
				return hQuery.uniqueResult();
			 }
		});
		
		if(resultado != null) {
			noTitulos = new BigInteger(resultado.toString());
		} else {
			noTitulos = new BigInteger("0");
		}
		
		return noTitulos;
	}

}
