/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.modelo.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.indeval.portaldali.middleware.services.mercadodinero.util.Constantes;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import com.indeval.portaldali.middleware.services.util.UtilsDaliVO;
import com.indeval.portaldali.persistence.model.CuentaNombrada;
import com.indeval.portaldali.persistence.model.Cupon;
import com.indeval.portaldali.persistence.model.Instrumento;
import com.indeval.portaldali.persistence.model.PosicionControlada;
import com.indeval.portaldali.persistence.model.PosicionNombrada;
import com.indeval.portaldali.persistence.vo.AgentePK;
import com.indeval.portaldali.persistence.vo.AgentePersistence;
import com.indeval.portaldali.persistence.vo.EmisionPersistence;
import com.indeval.portaldali.persistence.vo.InstrumentoVO;
import com.indeval.portaldali.persistence.vo.TPosicionControladaParamsPersistence;
import com.indeval.portaldali.persistence.vo.TPosicionNombradaParamsPersistence;
import com.indeval.portaldali.persistence.vo.TPosicionParamsPersistence;

/**
 * Clase utilitaria que contiene metodos para el manejo o manipulacion de
 * los VOs utilizados por CatalogoService
 * 
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class UtilsVOCatalogo {

    /** Log de Clase. */
    private static final Logger logger = LoggerFactory.getLogger(UtilsVOCatalogo.class);

    /** Indica estatus vigente */
    private static final int VIGENTE = 1;
    
    /** Indica estatus vigente */
    private static final int NO_VIGENTE = 0;
    
    /** Indica cupon cortado F */
    private static final String CUPON_CORTADO = "F";
    
//    /**
//     * Construye una instancia EmisionVO a partir de un objeto PosicionNombrada
//     * @param tPosicionControlada
//     * @return EmisionVO
//     */
//    public static EmisionVO getInstanceEmisionVO(PosicionControlada tPosicionControlada){
//        
//        log.info("Entrando a UtilsVOCatalogo.getInstanceEmisionVO()");
//        
//        return getInstanceEmisionVO(tPosicionControlada, 
//                tPosicionControlada.getFechaActual() != null ? 
//                        tPosicionControlada.getFechaActual() :  new Date());
//    }
    
    /**
     * Construye una instancia EmisionVO a partir de un objeto PosicionNombrada
     * @param tPosicionControlada
     * @param fechaActual
     * @return EmisionVO
     */
    public static EmisionVO getInstanceEmisionVO(
            PosicionControlada tPosicionControlada, Date fechaActual){
        
        logger.info("Entrando a UtilsVOCatalogo.getInstanceEmisionVO()");
        
        EmisionVO emisionVO = null;
        
        if(tPosicionControlada != null && tPosicionControlada.getEmision() != null){
            
            emisionVO = new EmisionVO();
            
            if(tPosicionControlada.getEmision().getIdTipoAlta() != null) {
                emisionVO.setAlta(tPosicionControlada.getEmision().getIdTipoAlta().toString());
            }
            if(tPosicionControlada.getEmision().getEmisora() != null && 
                    StringUtils.isNotBlank(tPosicionControlada.getEmision().getEmisora().getDescEmisora())) {
                emisionVO.setEmisora(tPosicionControlada.getEmision().getEmisora().getDescEmisora().trim());    
            }
            
            emisionVO.setFechaVencimiento(tPosicionControlada.getEmision().getFechaVencimiento());
            emisionVO.setDiasVigentes(getDiffDay(fechaActual, emisionVO.getFechaVencimiento())); 

            if(tPosicionControlada.getEmision().getInstrumento() != null) {
                if(StringUtils.isNotBlank(
                        tPosicionControlada.getEmision().getInstrumento().getClaveTipoValor())){
                    emisionVO.setIdTipoValor(
                            tPosicionControlada.getEmision().getInstrumento().getClaveTipoValor().trim());
                } 
                if(tPosicionControlada.getEmision().getInstrumento().getMercado() != null && 
                        StringUtils.isNotBlank(tPosicionControlada.getEmision().getInstrumento().
                                getMercado().getClave())){
                    emisionVO.setMercado(tPosicionControlada.getEmision().getInstrumento().
                            getMercado().getClave());
                }
            }
            
            if(StringUtils.isNotBlank(tPosicionControlada.getEmision().getIsin())){
                emisionVO.setIsin(tPosicionControlada.getEmision().getIsin().trim());    
            }
            
            //emisionVO.setPrecioVector(precioVector); // Este dato lo proveera el MARC
            
            emisionVO.setSaldoDisponible(new BigDecimal(tPosicionControlada.getPosicion()));
            emisionVO.setSaldoInicialDia(tPosicionControlada.getEmision().getSaldoInicial());
            
            if(StringUtils.isNotBlank(tPosicionControlada.getEmision().getSerie())) {
                emisionVO.setSerie(tPosicionControlada.getEmision().getSerie().trim());    
            }
                        
            Set cupones = tPosicionControlada.getEmision().getCupon();
            
            if(cupones!= null && !cupones.isEmpty()){
                for (Iterator iter = cupones.iterator(); iter.hasNext();) {

                    Cupon element = (Cupon) iter.next();
                    if(element != null && element.getEstadoCupon() != null && 
                            StringUtils.isNotBlank(
                                    element.getEstadoCupon().getDescEstatusCupon())) {
                        if(element.getClaveCupon() != null) {
                            emisionVO.setCupon(element.getClaveCupon().toString());    
                        }
                        emisionVO.setCuponCortado(
                                element.getEstadoCupon().getDescEstatusCupon().trim());
                        emisionVO.setUltimoHecho(element.getValorUltimoHecho());
                        break;
                    }
                    
                }
            }

            emisionVO.setValorNominal(tPosicionControlada.getEmision().getValorNominal());
        }

        return emisionVO;
    }
    
//    /**
//     * Construye una instancia EmisionVO a partir de un objeto PosicionNombrada
//     * @param tPosicionNombrada
//     * @return EmisionVO
//     */
//    public static EmisionVO getInstanceEmisionVO(PosicionNombrada tPosicionNombrada){
//        
//        log.info("Entrando a UtilsVOCatalogo.getInstanceEmisionVO()");
//        
//        return getInstanceEmisionVO(tPosicionNombrada, tPosicionNombrada.getFechaActual() != null ?
//                tPosicionNombrada.getFechaActual() :  new Date());
//    }
    
    /**
     * Construye una instancia EmisionVO a partir de un objeto PosicionNombrada
     * @param tPosicionNombrada
     * @param fechaActual
     * @return EmisionVO
     */
    public static EmisionVO getInstanceEmisionVO(
            PosicionNombrada tPosicionNombrada, Date fechaActual){
        
        logger.info("Entrando a UtilsVOCatalogo.getInstanceEmisionVO()");
        
        EmisionVO emisionVO = null;
        
        if(tPosicionNombrada != null && tPosicionNombrada.getCupon().getEmision() != null){
            
            emisionVO = new EmisionVO();
            
            if(tPosicionNombrada.getCupon().getEmision().getIdTipoAlta() != null) {
                emisionVO.setAlta(tPosicionNombrada.getCupon().getEmision().getIdTipoAlta().toString());    
            }
            if(tPosicionNombrada.getCupon().getEmision().getEmisora() != null && 
                    StringUtils.isNotBlank(tPosicionNombrada.getCupon().getEmision().getEmisora().getDescEmisora())) {
                emisionVO.setEmisora(tPosicionNombrada.getCupon().getEmision().getEmisora().getDescEmisora().trim());    
            }
            
            emisionVO.setFechaVencimiento(tPosicionNombrada.getCupon().getEmision().getFechaVencimiento());
            emisionVO.setDiasVigentes(getDiffDay(fechaActual, emisionVO.getFechaVencimiento())); 

            if(tPosicionNombrada.getCupon().getEmision().getInstrumento() != null) {
                if(StringUtils.isNotBlank(
                        tPosicionNombrada.getCupon().getEmision().getInstrumento().getClaveTipoValor())){
                    emisionVO.setIdTipoValor(
                            tPosicionNombrada.getCupon().getEmision().getInstrumento().getClaveTipoValor().trim());
                } 
                if(tPosicionNombrada.getCupon().getEmision().getInstrumento().getMercado() != null && 
                        StringUtils.isNotBlank(tPosicionNombrada.getCupon().getEmision().getInstrumento().
                                getMercado().getClave())){
                    emisionVO.setMercado(tPosicionNombrada.getCupon().getEmision().getInstrumento().
                            getMercado().getClave());
                }
            }
            
            if(StringUtils.isNotBlank(tPosicionNombrada.getCupon().getEmision().getIsin())){
                emisionVO.setIsin(tPosicionNombrada.getCupon().getEmision().getIsin().trim());    
            }
            
            //emisionVO.setPrecioVector(precioVector); // Este dato lo proveera el MARC
            
            emisionVO.setSaldoDisponible(new BigDecimal(tPosicionNombrada.getPosicionDisponible()));
            emisionVO.setSaldoInicialDia(tPosicionNombrada.getCupon().getEmision().getSaldoInicial());
            
            if(StringUtils.isNotBlank(tPosicionNombrada.getCupon().getEmision().getSerie())) {
                emisionVO.setSerie(tPosicionNombrada.getCupon().getEmision().getSerie().trim());    
            }
                        
            Set cupones = tPosicionNombrada.getCupon().getEmision().getCupon();
            
            if(cupones!= null && !cupones.isEmpty()){
                for (Iterator iter = cupones.iterator(); iter.hasNext();) {

                    Cupon element = (Cupon) iter.next();
                    if(element != null && element.getEstadoCupon() != null && 
                            StringUtils.isNotBlank(
                                    element.getEstadoCupon().getDescEstatusCupon())) {
                        if(element.getClaveCupon() != null) {
                            emisionVO.setCupon(element.getClaveCupon().toString());    
                        }
                        emisionVO.setCuponCortado(
                                element.getEstadoCupon().getDescEstatusCupon().trim());
                        emisionVO.setUltimoHecho(element.getValorUltimoHecho());
                        break;
                    }
                    
                }
            }

            emisionVO.setValorNominal(tPosicionNombrada.getCupon().getEmision().getValorNominal());
        }

        return emisionVO;
    }
    
    /**
     * Construye un objeto Instrumento a partir de un Instrumento  
     * @param cinstrumento
     * @return Instrumento
     */
    public static com.indeval.portaldali.persistence.vo.InstrumentoVO getInstanceInstrumento(Instrumento cinstrumento) {
        
        logger.info("Entrando a UtilsVOCatalogo.getInstanceInstrumento()");
        
        InstrumentoVO instrumento = null; 
        
        if(cinstrumento != null) {
            instrumento = new InstrumentoVO();
            instrumento.setMercado(cinstrumento.getMercado() != null 
                    && StringUtils.isNotBlank(cinstrumento.getMercado().getClave()) ? 
                            cinstrumento.getMercado().getClave().trim() : UtilsDaliVO.BLANK);
            instrumento.setTv(StringUtils.isNotBlank(cinstrumento.getClaveTipoValor()) ? 
                    cinstrumento.getClaveTipoValor().trim() : UtilsDaliVO.BLANK);
            instrumento.setUso(""); //TODO falta definir origen
            instrumento.setClaseIns(""); //TODO falta definir origen
        }
        
        return instrumento;
    }
    
    /**
     * Construye una instancia de AgenteVO a partir de una de CuentaNombrada
     * @param cCuentaNombrada
     * @return AgentePersistence
     */
    public static AgenteVO getInstanceAgenteVO(CuentaNombrada cCuentaNombrada) {
        
        logger.info("Entrando a UtilsVOCatalogo.getInstanceAgenteVO()");
        
        return UtilsDaliVO.getAgenteVO(UtilsVOCatalogo.getInstanceAgente(cCuentaNombrada));
        
    }
    
    /**
     * Construye una instancia de AgentePersistence a partir de una de CuentaNombrada
     * @param cCuentaNombrada
     * @return AgentePersistence
     */
    public static AgentePersistence getInstanceAgente(CuentaNombrada cCuentaNombrada) {
        
        logger.info("Entrando a UtilsVOCatalogo.getInstanceCCuentaNombrada()");
        
        AgentePersistence agente = null;
        
        if(cCuentaNombrada!=null) {
            
            agente = new AgentePersistence();
            AgentePK agentePK = new AgentePK();
            agentePK.setIdInst(cCuentaNombrada.getInstitucion().getTipoInstitucion().getClaveTipoInstitucion());
            agentePK.setFolioInst(cCuentaNombrada.getInstitucion().getFolioInstitucion());
            agentePK.setCuenta(cCuentaNombrada.getCuenta());
            agente.setAgentePK(agentePK);
//            agente.setAplicacion(aplicacion);
//            agente.setCreditoDisponible(creditoDisponible);
//            agente.setCta_efectivo(cta_efectivo);
//            agente.setCta_valores(cta_valores);
            agente.setFechaAlta(cCuentaNombrada.getInstitucion().getFechaAlta());
//            agente.setFechaHora(fechaHora);
//            agente.setFechaVenc(fechaVenc);
//            agente.setLimiteCredito(limiteCredito);
            agente.setNombreCorto(cCuentaNombrada.getInstitucion().getNombreCorto());
//            agente.setProcTerc(procTerc);
            agente.setRazonSocial(cCuentaNombrada.getRazonSocialCuenta());
//            agente.setResponsable(responsable);
//            agente.setSocioIndeval(socioIndeval);
            agente.setStatus(cCuentaNombrada.getCuentaEstado().getDescripcion());
            agente.setTenencia(cCuentaNombrada.getTipoCuenta().getTipoTenencia());
            agente.setTipoAdmon(cCuentaNombrada.getTipoCuenta().getTipoAdministracion());
            agente.setTipoCta(cCuentaNombrada.getTipoCuenta().getClaveTipoCuenta());
//            agente.setTipoDepositante(tipoDepositante);
//            agente.setTipoOper(tipoOper);
//            agente.setTipoPortafolio(tipoPortafolio);
//            agente.setTraspasos(traspasos);
//            agente.setUso(uso);
//            agente.setUsuario(usuario);
//            agente.setUVersion(version);
//            agente.setValores(valores);
//            agente.setValoresCapital(valoresCapital);

        }
        
        return agente;
        
    }
    
    /**
     * Construye una instancia TPosicionControladaParamsPersistence a partir de los objetos AgenteVO
     * y EmisionVO recibidos
     * 
     * @param agenteVO
     * @param emisionVO
     * @return TPosicionControladaParamsPersistence
     */
    public static TPosicionControladaParamsPersistence getInstanceTPosicionControladaParamsPersistence(
            AgenteVO agenteVO, EmisionVO emisionVO){
        
        logger.info("Entrando a UtilsVOCatalogo.getInstanceTPosicionControladaParamsPersistence()");
        
        TPosicionControladaParamsPersistence tPosicionControladaParamsPersistence =
            new TPosicionControladaParamsPersistence();
        
        BeanUtils.copyProperties(UtilsVOCatalogo.getInstanceTPosicionParamsPersistence(
                agenteVO, emisionVO), tPosicionControladaParamsPersistence);
        
        return tPosicionControladaParamsPersistence;
    }
    
    /**
     * Construye una instancia TPosicionNombradaParamsPersistence a partir de los objetos AgenteVO
     * y EmisionVO recibidos
     * 
     * @param agenteVO
     * @param emisionVO
     * @return TPosicionNombradaParamsPersistence
     */
    public static TPosicionNombradaParamsPersistence getInstanceTPosicionNombradaParamsPersistence(
            AgenteVO agenteVO, EmisionVO emisionVO){
        
        logger.info("Entrando a UtilsVOCatalogo.getInstanceTPosicionNombradaParamsPersistence()");
        
        TPosicionNombradaParamsPersistence tPosicionNombradaParamsPersistence =
            new TPosicionNombradaParamsPersistence();
        
        BeanUtils.copyProperties(UtilsVOCatalogo.getInstanceTPosicionParamsPersistence(
                agenteVO, emisionVO), tPosicionNombradaParamsPersistence);
        
        return tPosicionNombradaParamsPersistence;
    }
    
    /**
     * Construye una instancia example de TPosicionNombradaParamsPersistence 
     * a partir del objeto EmisionPersistence recibido
     * 
     * @param emision
     * @return TPosicionNombradaParamsPersistence
     */
    public static TPosicionNombradaParamsPersistence getTPosicionNombradaParamsPersistenceExample(
            EmisionPersistence emision){
        
        logger.info("Entrando a UtilsVOCatalogo.getTPosicionNombradaParamsPersistenceExample()");
        
        TPosicionNombradaParamsPersistence tPosicionNombradaParamsPersistence =
            new TPosicionNombradaParamsPersistence();
        
        if(emision != null) {

            BeanUtils.copyProperties(UtilsVOCatalogo.getInstanceTPosicionParamsPersistence(
                    UtilsDaliVO.getAgenteVO(emision.getAgente()), UtilsDaliVO.getEmisionVO(emision)), 
                    tPosicionNombradaParamsPersistence);
            tPosicionNombradaParamsPersistence.setEmisionExtranjera(emision.getEmisionExtra());
            if (StringUtils.isNotBlank(emision.getCuponCortado())){
                if (CUPON_CORTADO.equalsIgnoreCase(emision.getCuponCortado())) {
                    tPosicionNombradaParamsPersistence.setEstatusCupon(VIGENTE);    
                }
                else  {
                    tPosicionNombradaParamsPersistence.setEstatusCupon(NO_VIGENTE);
                }
            }
            if(emision.getInstrumento()!=null && 
                    StringUtils.isNotBlank(emision.getInstrumento().getMercado())){
                tPosicionNombradaParamsPersistence.setMercado(emision.getInstrumento().getMercado());    
            }
            tPosicionNombradaParamsPersistence.setFechaVencimiento(emision.getFechaVencimiento());
            tPosicionNombradaParamsPersistence.setIsin(emision.getIsin());

        }
        
        tPosicionNombradaParamsPersistence.setExample(true);
        
        return tPosicionNombradaParamsPersistence;
        
    }
    
    /**
     * Construye una instancia TPosicionParamsPersistence a partir de los objetos AgenteVO
     * y EmisionVO recibidos
     * @param agenteVO
     * @param emisionVO
     * @return TPosicionParamsPersistence
     */
    public static TPosicionParamsPersistence getInstanceTPosicionParamsPersistence(
            AgenteVO agenteVO, EmisionVO emisionVO){
        
        logger.info("Entrando a UtilsVOCatalogo.getInstanceTPosicionParamsPersistence()");
        
        TPosicionParamsPersistence tPosicionNombradaParams = 
            new TPosicionNombradaParamsPersistence();
        
        if(agenteVO != null) {
            if(StringUtils.isNotBlank(agenteVO.getId())){
                tPosicionNombradaParams.setIdInstitucion(agenteVO.getId().trim());    
            }
            if(StringUtils.isNotBlank(agenteVO.getFolio())){
                tPosicionNombradaParams.setFolioInstitucion(agenteVO.getFolio().trim());    
            }            
        }

        if(emisionVO != null) {
            if(StringUtils.isNotBlank(emisionVO.getIdTipoValor())){
                tPosicionNombradaParams.setTiposDeValor(
                        new String[] {emisionVO.getIdTipoValor().trim()});    
            }
            if(StringUtils.isNotBlank(emisionVO.getEmisora())){
                tPosicionNombradaParams.setEmisora(emisionVO.getEmisora().trim());    
            }
            if(StringUtils.isNotBlank(emisionVO.getSerie())){
                tPosicionNombradaParams.setSerie(emisionVO.getSerie().trim());    
            }
            if(StringUtils.isNotBlank(emisionVO.getCupon())){
                tPosicionNombradaParams.setCupon(emisionVO.getCupon().trim());    
            }
        }
        
        return tPosicionNombradaParams;
        
    }
    
    /**
     * Calcula la diferencia en días entre las fechas recibidas 
     * 
     * @param fechaActual
     * @param fechaVencimiento
     * @return Integer
     */
    public static Integer getDiffDay(Date fechaActual, Date fechaVencimiento) {
        
        logger.info("Entrando a UtilsVOCatalogo.getDiffDay()");
        
        Integer dias = null; 
            
        if(fechaVencimiento != null) {
            
            fechaActual = fechaActualNotNull(fechaActual);
            
            dias = new Integer((int) ((fechaVencimiento.getTime() - fechaActual.getTime()) / 
                    Constantes.MILLISECONDXDAY));            
        }
                
        return dias;
    }

//    /**
//     * Construye una lista de EmisionVO a partir de una lista de PosicionNombrada
//     * @param listaTPosicionNombrada
//     * @return List
//     */
//    public static List getListaEmisionVO(List listaTPosicionNombrada, Date fechaActual){
//    	
//    	log.info("Entrando a UtilsVOCatalogo.getEmisionVO()");
//    	
//    	List<EmisionVO> listaEmisionVO = new ArrayList<EmisionVO>();
//    	
//    	Date fechaActual = null;
//    	for (Iterator iter = listaEmisionVO.iterator(); iter.hasNext();) {
//    		PosicionNombrada element = (PosicionNombrada) iter.next();
//    		if(element.getFechaActual() != null) {
//    			fechaActual = element.getFechaActual();
//    			break; // interrumpe la iteracion al encontrar la fecha actual
//    		}
//		}
//
//        fechaActual = fechaActualNotNull(fechaActual);
//    	
//    	for (Iterator iter = listaTPosicionNombrada.iterator(); iter.hasNext();) {
//    		PosicionNombrada element = (PosicionNombrada) iter.next();
//    		if(element!=null) {
//    			listaEmisionVO.add(getInstanceEmisionVO(element, fechaActual));	
//    		}
//		}
//    	 
//    	return listaEmisionVO;
//    }
    
    /**
     * Construye una lista de EmisionVO a partir de una lista de PosicionNombrada 
     * o PosicionControlada
     *  
     * @param listaTPosicion
     * @param fechaActual
     * @return List
     * @throws BusinessException  
     */
    public static List getListaEmisionVO(List listaTPosicion, Date fechaActual) 
            throws BusinessException {
    	
    	logger.info("Entrando a UtilsVOCatalogo.getEmisionVO()");
        
        List<EmisionVO> listaEmisionVO = null;
        
        if(listaTPosicion != null && !listaTPosicion.isEmpty()) {
            
            fechaActual = fechaActualNotNull(fechaActual);
            
            listaEmisionVO = new ArrayList<EmisionVO>();
            
            if (listaTPosicion.get(0) instanceof PosicionNombrada) {
            
                for (Iterator iter = listaTPosicion.iterator(); iter.hasNext();) {
                    PosicionNombrada element = (PosicionNombrada) iter.next();
                    if(element!=null) {
                        listaEmisionVO.add(getInstanceEmisionVO(element, fechaActual)); 
                    }
                }
                
            }
            else if (listaTPosicion.get(0) instanceof PosicionControlada) {
                
                for (Iterator iter = listaTPosicion.iterator(); iter.hasNext();) {
                    PosicionControlada element = (PosicionControlada) iter.next();
                    if(element!=null) {
                        listaEmisionVO.add(getInstanceEmisionVO(element, fechaActual)); 
                    }
                }
                
            }
            else {
                throw new BusinessException("Error: La lista recibida contiene objetos del tipo [" +
                        listaTPosicion.get(0).getClass().getName() + "], se esperaban objetos del" +
                                "tipo PosicionNombrada o del tipo PosicionControlada");
            }
            
        }
    	 
    	return listaEmisionVO;
    }
    
    /**
     * Valida la fecha recibida no sea null, en caso contrario 
     * se utiliza la del servidor de la aplicacion
     * 
     * @param fechaActual
     * @return Date
     */
    private static Date fechaActualNotNull(Date fechaActual){
        
        logger.info("Entrando a UtilsVOCatalogo.fechaActualNotNull()");
        
        if(fechaActual == null) {
            logger.debug("Warning: No se recibio la fecha actual del Server Database, " +
                    "se hace uso de la fecha del Appliction Server");
            fechaActual = new Date(); /* En caso de que no se halla recibido la fecha actual de la BD 
                                         se utiliza la del servidor de la aplicacion */
        }
        
        return fechaActual;
        
    }
    
}