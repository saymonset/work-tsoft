/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.dao.conciliacion.impl;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.indeval.portaldali.persistence.dao.conciliacion.RegContValNombradaDao;
import com.indeval.portaldali.persistence.model.RegContValNombrada;
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
public class RegContValNombradaDaoImpl extends HibernateDaoSupport implements RegContValNombradaDao {


    private static final BigInteger BIG_INTEGER_ZERO = new BigInteger("0");
    private static final BigInteger VIGENTE = new BigInteger("1");
    
    private String[] cuentasTransicion = {"0030","0031"};
    
    /** Objeto de loggeo */
    private static final Logger logger = LoggerFactory.getLogger(RegContValNombradaDaoImpl.class);
    
    /**
     * @see com.indeval.portaldali.persistence.dao.conciliacion.RegContValNombradaDao#findArchivoConciliacion(com.indeval.portaldali.persistence.vo.AgentePK, com.indeval.portaldali.persistence.vo.EmisionPK, com.indeval.portaldali.persistence.vo.PageVO)
     */
    public PageVO findArchivoConciliacion(AgentePK agenteFirmado, EmisionPK emisionPK, PageVO pageVO) {
        
        logger.info("Entrando a RegContValNombradaDaoImpl.findArchivoConciliacion()");
        
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
            PageableDetachedCriteriaDali.createPageableDetachedCriteria(RegContValNombrada.class);
        
        criteria.createAlias("posicion", "p");
        criteria.createAlias("p.cupon", "oc");
        criteria.createAlias("oc.estadoCupon", "oce");           
        criteria.createAlias("oc.emision", "pe");
        criteria.createAlias("p.boveda", "bov");
        criteria.createAlias("pe.instrumento", "pei");
        criteria.createAlias("pe.emisora", "pee");
        
        criteria.createAlias("operacion", "o");     
        criteria.createAlias("o.tipoOperacion", "ot");
        criteria.createAlias("o.cuentaNombradaTraspasante", "oct");
        criteria.createAlias("o.cuentaNombradaReceptor", "ocr");
        criteria.createAlias("oct.institucion", "octi");
        criteria.createAlias("ocr.institucion", "ocri");
        criteria.createAlias("octi.tipoInstitucion", "octit");
        criteria.createAlias("ocri.tipoInstitucion", "ocrit");
        
        /* Se verifica si se requieren los movimientos y se agrega la condicion */
        if(pageVO.getValores() != null && pageVO.getValores().containsKey("MOV") &&
        		pageVO.getValores().containsKey(FECHA_FIN_AYER)){
            criteria.add(Restrictions.gt("fecha", pageVO.getValores().get(FECHA_FIN_AYER)));
        }
        
        if(pageVO.getValores() != null && pageVO.getValores().containsKey("BOVEDA")){
            criteria.add(Restrictions.eq("bov.idBoveda", (BigInteger)pageVO.getValores().get("BOVEDA")));
        }
        
        Junction conjunctionTraspasante = Restrictions.conjunction().add(
                Restrictions.eq("octit.claveTipoInstitucion", agenteFirmado.getIdInst().trim())).add(
                        Restrictions.eq("octi.folioInstitucion", agenteFirmado.getFolioInst().trim()));
        
        Junction conjuctionReceptor = Restrictions.conjunction().add(
                Restrictions.eq("ocrit.claveTipoInstitucion", agenteFirmado.getIdInst().trim())).add(
                        Restrictions.eq("ocri.folioInstitucion", agenteFirmado.getFolioInst().trim()));

        if(StringUtils.isNotBlank(agenteFirmado.getCuenta())){
            conjunctionTraspasante.add(Restrictions.eq("oct.cuenta", agenteFirmado.getCuenta().trim()));
            conjuctionReceptor.add(Restrictions.eq("ocr.cuenta", agenteFirmado.getCuenta().trim()));
        }
        else{
            conjunctionTraspasante.add(Restrictions.not(Restrictions.in("oct.cuenta", cuentasTransicion)));
            conjunctionTraspasante.add(Restrictions.not(Restrictions.in("ocr.cuenta", cuentasTransicion)));
        }
        
        criteria.add(Restrictions.disjunction().add(
                (Criterion)conjunctionTraspasante).add((Criterion)conjuctionReceptor));
        
        if(emisionPK != null) {
            
            if(StringUtils.isNotBlank(emisionPK.getTv())){
                criteria.add(Restrictions.eq("pei.claveTipoValor", emisionPK.getTv().trim()));
            }
            if(StringUtils.isNotBlank(emisionPK.getEmisora())){
                criteria.add(Restrictions.eq("pee.clavePizarra", emisionPK.getEmisora().trim()));
            }
            if(StringUtils.isNotBlank(emisionPK.getSerie())){
                criteria.add(Restrictions.eq("pe.serie", emisionPK.getSerie().trim()));
            }
            if(StringUtils.isNotBlank(emisionPK.getCupon())){
                criteria.add(Restrictions.eq("oc.claveCupon", emisionPK.getCupon().trim()));
                criteria.add(Restrictions.eq("oce.idEstatusCupon", VIGENTE));
            }
            
        }

        criteria.add(Restrictions.disjunction().add(
                Restrictions.gt("p.posicionDisponible", BIG_INTEGER_ZERO)).add(
                        Restrictions.gt("pe.saldoInicial",  BigDecimal.ZERO)));
        
        criteria.addOrder(Order.asc("pei.claveTipoValor")).addOrder(
                Order.asc("pee.descEmisora")).addOrder(Order.asc("pe.serie"))
                .addOrder(Order.asc("oc.claveCupon"));

        return criteria.pageResult(pageVO, this.getHibernateTemplate());
        
    }

}
