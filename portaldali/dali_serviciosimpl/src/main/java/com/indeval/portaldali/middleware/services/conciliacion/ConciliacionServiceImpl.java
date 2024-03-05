/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.conciliacion;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.bursatec.util.message.MessageResolver;
import com.indeval.portaldali.middleware.services.mercadocapitales.ArchivoConciliacionVO;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import com.indeval.portaldali.middleware.services.modelo.PaginaVO;
import com.indeval.portaldali.middleware.services.modelo.util.UtilsVOCatalogo;
import com.indeval.portaldali.middleware.services.util.UtilServices;
import com.indeval.portaldali.middleware.services.util.UtilsDaliVO;
import com.indeval.portaldali.persistence.dao.common.DateUtilsDao;
import com.indeval.portaldali.persistence.dao.common.PosicionNombradaDaliDao;
import com.indeval.portaldali.persistence.dao.conciliacion.OperacionNombradaDao;
import com.indeval.portaldali.persistence.dao.conciliacion.RegContValNombradaDao;
import com.indeval.portaldali.persistence.model.CuentaNombrada;
import com.indeval.portaldali.persistence.model.Cupon;
import com.indeval.portaldali.persistence.model.Emision;
import com.indeval.portaldali.persistence.model.OperacionNombrada;
import com.indeval.portaldali.persistence.model.PosicionNombrada;
import com.indeval.portaldali.persistence.model.RegContValNombrada;
import com.indeval.portaldali.persistence.util.DateUtil;
import com.indeval.portaldali.persistence.vo.PageVO;
import com.indeval.portaldali.persistence.vo.TPosicionNombradaParamsPersistence;

/**
 * Implementaci&oacute;n de los servicios para la conciliacion.
 *
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 * @version 1.0
 */
public class ConciliacionServiceImpl implements ConciliacionService {
    
    private static final String CUENTA_DE_EMISION = "E";

    /** Clase de Logueo */
    private static Logger logger = LoggerFactory.getLogger(ConciliacionServiceImpl.class);
    
    /** Bean de acceso a DateUtilsDao */
    private DateUtilsDao dateUtilsDao;
    
    /** Bean para acceso al properties de mensaje de errores */
    private MessageResolver errorResolver;
    
    /** Bean de acceso a OperacionNombradaDao */    
    private OperacionNombradaDao operacionNombradaDao;
    
    /** Bean de acceso a RegContValNombradaDao */
    private RegContValNombradaDao regContValNombradaDao;
    
    /** Bean de acceso a PosicionNombradaDaliDao */
    private PosicionNombradaDaliDao posicionNombradaDaliDao;

    /** Bean de acceso a UtilService */
    private UtilServices utilService;
    
    /**
     * @see com.indeval.portaldali.middleware.services.conciliacion.ConciliacionService#archivoConciliacion(com.indeval.portaldali.middleware.services.modelo.AgenteVO, com.indeval.portaldali.middleware.services.modelo.EmisionVO, com.indeval.portaldali.middleware.services.modelo.PaginaVO)
     */
    @SuppressWarnings("unchecked")
    public PaginaVO archivoConciliacion(AgenteVO agenteFirmado, EmisionVO emision, PaginaVO paginaVO, Boolean debeDejarLog)
            throws BusinessException {

        logger.info("Entrando a MercadoCapitalesServiceImpl.archivoConciliacion()");

        /* Valida el id y folio del agente firmado */
        utilService.validaDTONoNulo(agenteFirmado, " Agente ");
        agenteFirmado.tieneClaveValida();
        
        /* Se obtiene la fecha fin del dia de ayer */
        Date fechaFinAyer = obtieneFechaDiaFinAyer();
        
        PageVO pageVO =  null;

        /* Se verifica si se quieren los movimientos en cuyo caso se pasa al Dao la fecha fin ayer */
        if(paginaVO.getValores() != null && paginaVO.getValores().containsKey(OperacionNombradaDao.MOVIMIENTOS)){
            paginaVO.getValores().put(OperacionNombradaDao.FECHA_FIN_AYER, dateUtilsDao.getDateFechaDB());
            
            /* Ejecuta el DAO  para buscar los movimientos */
            pageVO =  operacionNombradaDao.findMovimientosArchivoConciliacion(
                    UtilsDaliVO.getAgentePK(agenteFirmado), UtilsDaliVO.getEmisionPK(emision), 
                    UtilsDaliVO.getPageVO(paginaVO));
            
        }
        else if(paginaVO.getValores() != null && paginaVO.getValores().containsKey(OperacionNombradaDao.DETALLES)) {
        	paginaVO.getValores().put(OperacionNombradaDao.FECHA_FIN_AYER, dateUtilsDao.getDateFechaDB());
        	/* Ejecuta el DAO  para buscar los detalles */
        	pageVO =  operacionNombradaDao.findDetalleArchivoConciliacion(
        			(BigInteger) paginaVO.getValores().get(OperacionNombradaDao.DETALLES), 
        			UtilsDaliVO.getPageVO(paginaVO));
        	
        }
        else {
        	
        	/* Ejecuta el DAO  para buscar los registros principales */
        	TPosicionNombradaParamsPersistence tPosicionNombradaParamsPersistence = 
        		UtilsVOCatalogo.getInstanceTPosicionNombradaParamsPersistence(agenteFirmado, emision);
        	if( StringUtils.isNotEmpty(agenteFirmado.getCuenta()) ) {
        		tPosicionNombradaParamsPersistence.setCuentas(new String[]{ agenteFirmado.getCuenta() });
        	}
        	if( StringUtils.isNotBlank(emision.getIsin()) ) {
        		tPosicionNombradaParamsPersistence.setIsin(emision.getIsin());
        	}
        	if(paginaVO.getValores() != null && paginaVO.getValores().containsKey(OperacionNombradaDao.BOVEDA)){
        		tPosicionNombradaParamsPersistence.setIdBoveda((BigInteger) paginaVO.getValores().get(OperacionNombradaDao.BOVEDA));
        	}
        	Date fechaActual = dateUtilsDao.getDateFechaDB();
        	Date fechaAyer = utilService.agregarDiasHabiles(fechaActual, -1);
        	Date[] rangoFechas = DateUtil.preparaIntervaloFechas(fechaAyer, fechaAyer);
        	tPosicionNombradaParamsPersistence.setFechaInicio(rangoFechas[0]);
        	tPosicionNombradaParamsPersistence.setFechaFin(rangoFechas[1]);
        	tPosicionNombradaParamsPersistence.setFechaMovimiento(fechaActual);
        	tPosicionNombradaParamsPersistence.setPageVO(UtilsDaliVO.getPageVO(paginaVO));
        	logger.info("***Objeto Enviado como paramas: [" + ToStringBuilder.reflectionToString(tPosicionNombradaParamsPersistence) + "]");
        	pageVO =  posicionNombradaDaliDao.getTPosicionNombradaSaldoInicial(tPosicionNombradaParamsPersistence);
        	
        }

        /* Se validan y preparan los resultados a devolver */
        if (pageVO != null && pageVO.getRegistros() != null && 
                !pageVO.getRegistros().isEmpty()) {
            
        	this.preparaPaginaVO(pageVO, paginaVO, agenteFirmado, fechaFinAyer);
            
        }
        else {
            paginaVO.setRegistros(null);
            paginaVO.setTotalRegistros(0);
        }

        /* Regresa la pagina con la nueva lista de registros */
        return paginaVO;

    }
    
    /**
     * Prepara la paginaVO con los ArchivoConciliacionVO
     * @param pageVO
     * @param paginaVO
     * @return PaginaVO
     */
    @SuppressWarnings("unchecked")
	private PaginaVO preparaPaginaVO(PageVO pageVO, PaginaVO paginaVO, AgenteVO agenteFirmado, 
    		Date fechaFinAyer) {
    	
        /* Arma los VOs y coloca los registros en la paginaVO */
        List listRegistros =  new ArrayList();
    	if(paginaVO.getValores() != null && (paginaVO.getValores().containsKey(OperacionNombradaDao.MOVIMIENTOS) || 
    			paginaVO.getValores().containsKey(OperacionNombradaDao.DETALLES))){
    		
    		/* Se construyen objetos ArchivoConciliacionVO a partir de RegContValNombrada */
    		listRegistros = this.obtieneArchivoConciliacionVOAPartirDeOperacionNombrada(
    				pageVO.getRegistros(), agenteFirmado, fechaFinAyer);
    	}
    	else {
    		
    		/* Se construyen objetos ArchivoConciliacionVO a partir de PosicionNombrada */
    		listRegistros = this.obtieneArchivoConciliacionVOAPartirDePosicionNombrada(
    				pageVO.getRegistros(), agenteFirmado, fechaFinAyer);
    	}
    	
        paginaVO.setRegistros(listRegistros);
        paginaVO.setTotalRegistros(pageVO.getTotalRegistros() - 
                (pageVO.getRegistros().size() - listRegistros.size()));
    	
    	return paginaVO;
    	
    }

    /**
     * Obtiene la lista de ArchivoConciliacionVO a partir de la lista de RegContValNombrada
     * 
     * @param lista
     * @param fechaFinAyer
     * @return List
     * @throws BusinessException
     */
    @SuppressWarnings("unchecked")
    private List<ArchivoConciliacionVO> obtieneArchivoConciliacionVOAPartirDeOperacionNombrada(
            List<RegContValNombrada> lista, AgenteVO agenteFirmado, Date fechaFinAyer) throws BusinessException {

        if (lista == null) {
            throw new BusinessException(
                    "No existe la lista a convertir");
        }
        if (lista.isEmpty()) {
            throw new BusinessException(
                    "La lista a convertir est&aacute; vac&iacute;");
        }

        /* Se crea la lista de retorno */
        List<ArchivoConciliacionVO> resultado = new ArrayList<ArchivoConciliacionVO>();
        
        for (Iterator iter = lista.iterator(); iter.hasNext();) {
            
        	OperacionNombrada operacionNombrada = (OperacionNombrada) iter.next();
            
            if(operacionNombrada != null) {
                ArchivoConciliacionVO archivoConciliacionVO = 
                    createArchivoConciliacionVO(operacionNombrada, agenteFirmado, fechaFinAyer);
                
//                if (archivoConciliacionVO != null && archivoConciliacionVO.getSaldoTesoreria() != null
//                        && archivoConciliacionVO.getSaldoDisponible() != null && archivoConciliacionVO.getSaldoInicial() != null 
//                        && archivoConciliacionVO.getSaldoInicial().add(archivoConciliacionVO.getSaldoTesoreria()).
//                            add(archivoConciliacionVO.getSaldoDisponible()).compareTo(BigDecimal.ZERO) != 0){
                        resultado.add(archivoConciliacionVO);
//                    }

                }
            
            }

        return resultado;
    }
    
    /**
     * Obtiene la lista de ArchivoConciliacionVO a partir de la lista de RegContValNombrada
     * 
     * @param lista
     * @param fechaFinAyer
     * @return List
     * @throws BusinessException
     */
    @SuppressWarnings("unchecked")
    private List<ArchivoConciliacionVO> obtieneArchivoConciliacionVOAPartirDePosicionNombrada (
            List lista, AgenteVO agenteFirmado, Date fechaFinAyer) throws BusinessException {

        logger.info("Entrando a MercadoCapitalesServiceImpl.obtieneArchivoConciliacionVO()");

        if (lista == null) {
            throw new BusinessException(
                    "No existe la lista a convertir");
        }
        if (lista.isEmpty()) {
            throw new BusinessException(
                    "La lista a convertir est&aacute; vac&iacute;");
        }

        /* Se crea la lista de retorno */
        List<ArchivoConciliacionVO> resultado = new ArrayList<ArchivoConciliacionVO>();
        
        for (Iterator iter = lista.iterator(); iter.hasNext();) {
        	Object array[] = (Object[])iter.next();
            PosicionNombrada posicionNombrada = (PosicionNombrada)array[0];
            BigInteger saldoInicial = (BigInteger)array[1];
        	
            if(posicionNombrada != null) {
                ArchivoConciliacionVO archivoConciliacionVO = 
                    createArchivoConciliacionVO(posicionNombrada, saldoInicial, agenteFirmado, fechaFinAyer);
                       resultado.add(archivoConciliacionVO);
                }
            
            }

        return resultado;
    }
    
    /**
     * Crea una instancia de ArchivoConciliacionVO a partir del objeto OperacionNombrada recibido
     * @param operacionNombrada
     * @param fechaFinAyer
     * @return ArchivoConciliacionVO
     */
    private ArchivoConciliacionVO createArchivoConciliacionVO(OperacionNombrada operacionNombrada, 
            AgenteVO agenteFirmado, Date fechaFinAyer) {
        
        logger.info("Entrando a ConciliacionServiceImpl.createArchivoConciliacionVO()");
        
        ArchivoConciliacionVO archivoConciliacionVO = null;
        
        if(operacionNombrada != null && operacionNombrada.getPosicionNombrada() != null){
            
            archivoConciliacionVO = new ArchivoConciliacionVO();
            archivoConciliacionVO.setFechaHora((operacionNombrada.getInstruccion()!= null ? 
            		operacionNombrada.getInstruccion().getFechaAplicacion() : null));
            
            PosicionNombrada posicionTraspasante = operacionNombrada.getPosicionNombrada();
            PosicionNombrada posicionReceptor = operacionNombrada.getPosicionReceptor();
            
            
            CuentaNombrada cuentaNombradaReceptor = posicionReceptor.getCuentaNombrada();
            CuentaNombrada cuentaNombradaTraspasante = posicionTraspasante.getCuentaNombrada();
            
            AgenteVO agenteReceptor = createAgenteVOOperacion(cuentaNombradaReceptor);
            AgenteVO agenteTraspasante = createAgenteVOOperacion(cuentaNombradaTraspasante);
            
            boolean operacionPermitida = true;
            boolean saldoTesoreria = false;
            if(agenteReceptor != null && agenteReceptor.equalsSinCuenta(agenteFirmado)){
                archivoConciliacionVO.setAgenteFirmado(agenteReceptor);

                if(cuentaNombradaReceptor.getTipoCuenta() != null && 
                        StringUtils.isNotBlank(cuentaNombradaReceptor.getTipoCuenta().getTipoTenencia()) &&
                        cuentaNombradaReceptor.getTipoCuenta().getTipoTenencia().equalsIgnoreCase(CUENTA_DE_EMISION)){
                    saldoTesoreria = true;
                }

            }
            else if(agenteTraspasante != null && agenteTraspasante.equalsSinCuenta(agenteFirmado)){
                archivoConciliacionVO.setAgenteFirmado(agenteTraspasante);
                
                if(cuentaNombradaTraspasante.getTipoCuenta() != null && 
                        StringUtils.isNotBlank(cuentaNombradaTraspasante.getTipoCuenta().getTipoTenencia()) &&
                        cuentaNombradaTraspasante.getTipoCuenta().getTipoTenencia().equalsIgnoreCase(CUENTA_DE_EMISION)){
                    saldoTesoreria = true;
                }
                
            }
            else {
                logger.debug("WARNING : "+ agenteTraspasante.toResumeString() + " != " + 
                        agenteFirmado.toResumeString() + " && " + agenteReceptor.toResumeString() + 
                        " != " + agenteFirmado.toResumeString());
                System.out.println("WARNING : "+ agenteTraspasante.toResumeString() + " != " + 
                        agenteFirmado.toResumeString() + " && " + agenteReceptor.toResumeString() + 
                        " != " + agenteFirmado.toResumeString());
                operacionPermitida = false;
            }
            
            if(operacionPermitida){
                
                archivoConciliacionVO.setAgenteReceptor(agenteReceptor);
                archivoConciliacionVO.setAgenteTraspasante(agenteTraspasante);
                
                EmisionVO emisionVO = this.createEmisionVO(operacionNombrada.getCupon());
                archivoConciliacionVO.setEmision(emisionVO);            
                
                if(operacionNombrada.getCupon() != null) {
                    archivoConciliacionVO.setValorNominal(operacionNombrada.getCupon().getValorNominal() != null ? 
                    		operacionNombrada.getCupon().getValorNominal() : BigDecimal.ZERO);
                    archivoConciliacionVO.setUltimoHecho(operacionNombrada.getCupon().getValorUltimoHecho() != null ?
                    		operacionNombrada.getCupon().getValorUltimoHecho() : BigDecimal.ZERO); 
                }
                
                archivoConciliacionVO.setSaldoInicial(operacionNombrada.getCupon().getEmision().getSaldoInicial() != null ? 
                		operacionNombrada.getCupon().getEmision().getSaldoInicial() : BigDecimal.ZERO);
                
                archivoConciliacionVO
                        .setTieneDetalle(archivoConciliacionVO.getFechaHora() != null ? 
                                !fechaFinAyer.after(archivoConciliacionVO.getFechaHora()) : true);
                
                archivoConciliacionVO.setCantidadOperada(operacionNombrada.getNumeroTitulos());
                
                if(operacionNombrada.getInstruccion() != null &&
                		operacionNombrada.getInstruccion().getTipoInstruccion() != null &&
                		StringUtils.isNotBlank(operacionNombrada.getInstruccion().getTipoInstruccion().getNombreCorto())) {
                	archivoConciliacionVO.setClaveTipoOperacion(operacionNombrada.getInstruccion().getTipoInstruccion().getNombreCorto());
                }
                
                if(saldoTesoreria){
                    archivoConciliacionVO.setSaldoTesoreria(archivoConciliacionVO.getSaldoDisponible() != null ?
                            archivoConciliacionVO.getSaldoDisponible() : BigDecimal.ZERO);
                    archivoConciliacionVO.setSaldoDisponible(BigDecimal.ZERO);
                    archivoConciliacionVO.setUltimoHecho(BigDecimal.ZERO);
                    archivoConciliacionVO.setValorNominal(BigDecimal.ZERO);
                }
                else {
                    archivoConciliacionVO.setSaldoTesoreria(BigDecimal.ZERO);
                }
                
                if( archivoConciliacionVO.getSaldoDisponible() != null ) {
                	archivoConciliacionVO.setTotal(archivoConciliacionVO.getSaldoDisponible().add(
                        archivoConciliacionVO.getSaldoTesoreria()));
                }
                
                if(operacionNombrada.getBovedaValores() != null &&
                		StringUtils.isNotBlank(operacionNombrada.getBovedaValores().getNombreCorto())) {
                	archivoConciliacionVO.setNombreCortoBoveda(operacionNombrada.getBovedaValores().getNombreCorto().trim());
                }

                if(operacionNombrada.getFolioOperacion() != null ) {
                	archivoConciliacionVO.setFolioControl(operacionNombrada.getFolioOperacion());
                }
            }
            
        }
        
        return archivoConciliacionVO;
        
    }
    
    
    /**
     * Crea una instancia de ArchivoConciliacionVO a partir del objeto PosicionNombrada recibido
     * @param posicionNombrada
     * @param fechaFinAyer
     * @return ArchivoConciliacionVO
     */
    private ArchivoConciliacionVO createArchivoConciliacionVO(PosicionNombrada posicionNombrada, 
            BigInteger saldoInicial, AgenteVO agenteFirmado, Date fechaFinAyer) {
        
        ArchivoConciliacionVO archivoConciliacionVO = null;
        
        if(posicionNombrada != null){
            
            archivoConciliacionVO = new ArchivoConciliacionVO();
            archivoConciliacionVO.setIdPosicion(posicionNombrada.getIdPosicion());
            
            CuentaNombrada cuentaNombrada = posicionNombrada.getCuentaNombrada();
            AgenteVO agenteVO =  createAgenteVO(cuentaNombrada);
            
            archivoConciliacionVO.setAgenteTraspasante(agenteVO);
            archivoConciliacionVO.setAgenteReceptor(agenteVO);
            archivoConciliacionVO.setAgenteFirmado(agenteVO);
            
            boolean saldoTesoreria = false;
            if(cuentaNombrada.getTipoCuenta() != null && 
                    StringUtils.isNotBlank(cuentaNombrada.getTipoCuenta().getTipoTenencia()) &&
                    cuentaNombrada.getTipoCuenta().getTipoTenencia().equalsIgnoreCase(CUENTA_DE_EMISION)){
                saldoTesoreria = true;
            }
            
            EmisionVO emisionVO = this.createEmisionVO(posicionNombrada.getCupon());
            archivoConciliacionVO.setEmision(emisionVO);            

            if(posicionNombrada.getCupon() != null) {
            	archivoConciliacionVO.setValorNominal(posicionNombrada.getCupon().getValorNominal() != null ? 
            			posicionNombrada.getCupon().getValorNominal() : BigDecimal.ZERO);
            	archivoConciliacionVO.setUltimoHecho(posicionNombrada.getCupon().getValorUltimoHecho() != null ?
            			posicionNombrada.getCupon().getValorUltimoHecho() : BigDecimal.ZERO); 
            }

            archivoConciliacionVO.setSaldoInicial(saldoInicial != null ? 
            		new BigDecimal(saldoInicial) : BigDecimal.ZERO);
            BigInteger sumaPosicion = posicionNombrada.getPosicionDisponible() != null ?
            		posicionNombrada.getPosicionDisponible() : BigInteger.ZERO;
            sumaPosicion = sumaPosicion.add( posicionNombrada.getPosicionNoDisponible() != null ?
            		posicionNombrada.getPosicionNoDisponible() : BigInteger.ZERO );
            archivoConciliacionVO.setSaldoDisponible( new BigDecimal(sumaPosicion) );

            archivoConciliacionVO.setTieneDetalle(archivoConciliacionVO.getFechaHora() != null ? 
            		!fechaFinAyer.after(archivoConciliacionVO.getFechaHora()) : true);

            archivoConciliacionVO.setCantidadOperada(posicionNombrada.getPosicionDisponible());

            if(saldoTesoreria){
            	archivoConciliacionVO.setSaldoTesoreria(archivoConciliacionVO.getSaldoDisponible() != null ?
            			archivoConciliacionVO.getSaldoDisponible() : BigDecimal.ZERO);
            	archivoConciliacionVO.setSaldoDisponible(BigDecimal.ZERO);
            	archivoConciliacionVO.setUltimoHecho(BigDecimal.ZERO);
            	archivoConciliacionVO.setValorNominal(BigDecimal.ZERO);
            }
            else {
            	archivoConciliacionVO.setSaldoTesoreria(BigDecimal.ZERO);
            }

            archivoConciliacionVO.setTotal(archivoConciliacionVO.getSaldoDisponible().add(
            		archivoConciliacionVO.getSaldoTesoreria()));

            if( posicionNombrada != null && posicionNombrada.getBoveda() != null) {
            	archivoConciliacionVO.setIdBoveda(posicionNombrada.getBoveda().getIdBoveda() != null ? 
            			posicionNombrada.getBoveda().getIdBoveda(): null);
            	if(StringUtils.isNotBlank(posicionNombrada.getBoveda().getNombreCorto())) {
            		archivoConciliacionVO.setNombreCortoBoveda(posicionNombrada.getBoveda().getNombreCorto());	
            	}
            }

        }

        return archivoConciliacionVO;

    }
    
    /**
     * Crea una instancia de EmisionVO a partir del objeto Emision recibido
     * @param emision
     * @return EmisionVO
     */
    private EmisionVO createEmisionVO(Cupon cupon){

    	EmisionVO emisionVO = null;

    	if(cupon != null){

    		emisionVO = new EmisionVO();

    		Emision emision = cupon.getEmision();

    		if(emision.getInstrumento() != null && 
    				StringUtils.isNotBlank(emision.getInstrumento().getClaveTipoValor())){
    			emisionVO.setIdTipoValor(emision.getInstrumento().getClaveTipoValor().trim());                
    		}

    		if(emision.getEmisora() != null && 
    				StringUtils.isNotBlank(emision.getEmisora().getClavePizarra())){
    			emisionVO.setEmisora(emision.getEmisora().getClavePizarra().trim());    
    		}

    		if(StringUtils.isNotBlank(emision.getSerie())){
    			emisionVO.setSerie(emision.getSerie().trim());    
    		}

    		if(StringUtils.isNotBlank(cupon.getClaveCupon())){
    			emisionVO.setCupon(cupon.getClaveCupon().trim());  
    		}

    		if( StringUtils.isNotBlank(emision.getIsin()) ) {
    			emisionVO.setIsin(emision.getIsin());
    		}

    	}

    	return emisionVO;
    }
    
    /**
     * Crea una instancia de AgenteVO a partir del objeto CuentaNombrada recibido
     * @param cuentaNombrada
     * @return
     */
    private AgenteVO createAgenteVO(CuentaNombrada cuentaNombrada) {
        
        AgenteVO agenteVO = null;
        
        if(cuentaNombrada != null && cuentaNombrada.getInstitucion() != null 
                && cuentaNombrada.getInstitucion().getTipoInstitucion() != null){
            
            agenteVO = new AgenteVO();
            
            if(StringUtils.isNotBlank(cuentaNombrada.getInstitucion().getTipoInstitucion().getClaveTipoInstitucion())){
                agenteVO.setId(cuentaNombrada.getInstitucion().getTipoInstitucion().getClaveTipoInstitucion().trim());    
            }
            
            if(StringUtils.isNotBlank(cuentaNombrada.getInstitucion().getFolioInstitucion())){
                agenteVO.setFolio(cuentaNombrada.getInstitucion().getFolioInstitucion().trim());    
            }
            
            if(StringUtils.isNotBlank(cuentaNombrada.getCuenta())){
                agenteVO.setCuenta(cuentaNombrada.getCuenta().trim());    
            }

            if(StringUtils.isNotBlank(cuentaNombrada.getInstitucion().getNombreCorto())){
                agenteVO.setNombreCorto(cuentaNombrada.getInstitucion().getNombreCorto());    
            }
            
            if(StringUtils.isNotBlank(cuentaNombrada.getInstitucion().getRazonSocial())){
                agenteVO.setRazon(cuentaNombrada.getInstitucion().getRazonSocial());
            }
            
            if(cuentaNombrada.getTipoCuenta() != null) {
                if(StringUtils.isNotBlank(cuentaNombrada.getTipoCuenta().getClaveTipoCuenta())){
                    agenteVO.setTipo(cuentaNombrada.getTipoCuenta().getClaveTipoCuenta().trim());
                }
                if(StringUtils.isNotBlank(cuentaNombrada.getTipoCuenta().getTipoTenencia())){
                    agenteVO.setTenencia(cuentaNombrada.getTipoCuenta().getTipoTenencia().trim());
                }
            }
            
        }
        
        return agenteVO;
        
    }
    
 private AgenteVO createAgenteVOOperacion(CuentaNombrada cuentaNombrada) {
        
        logger.info("Entrando a ConciliacionServiceImpl.createAgenteVO()");
        
        AgenteVO agenteVO = null;
        
        if(cuentaNombrada != null && cuentaNombrada.getInstitucion() != null 
                && cuentaNombrada.getInstitucion().getTipoInstitucion() != null){
            
            agenteVO = new AgenteVO();
            
            if(StringUtils.isNotBlank(cuentaNombrada.getInstitucion().getTipoInstitucion().getClaveTipoInstitucion())){
                agenteVO.setId(cuentaNombrada.getInstitucion().getTipoInstitucion().getClaveTipoInstitucion().trim());    
            }
            
            if(StringUtils.isNotBlank(cuentaNombrada.getInstitucion().getFolioInstitucion())){
                agenteVO.setFolio(cuentaNombrada.getInstitucion().getFolioInstitucion().trim());    
            }
            
            if(StringUtils.isNotBlank(cuentaNombrada.getCuenta())){
                agenteVO.setCuenta(cuentaNombrada.getCuenta().trim());    
            }

            if(StringUtils.isNotBlank(cuentaNombrada.getNombreCuenta())){
                agenteVO.setNombreCorto(cuentaNombrada.getNombreCuenta());    
            }
            
            if(StringUtils.isNotBlank(cuentaNombrada.getInstitucion().getRazonSocial())){
                agenteVO.setRazon(cuentaNombrada.getInstitucion().getRazonSocial());
            }
            
            if(cuentaNombrada.getTipoCuenta() != null) {
            	if(StringUtils.isNotBlank(cuentaNombrada.getTipoCuenta().getClaveTipoCuenta())){
                    agenteVO.setTipo(cuentaNombrada.getTipoCuenta().getClaveTipoCuenta().trim());
                }
                if(StringUtils.isNotBlank(cuentaNombrada.getTipoCuenta().getTipoTenencia())){
                    agenteVO.setTenencia(cuentaNombrada.getTipoCuenta().getTipoTenencia().trim());
                }
            }
            
        }
        
        return agenteVO;
        
    }
    
    /**
     * Obtiene la fecha de fin de dia de ayer
     * @return Date
     */
    private Date obtieneFechaDiaFinAyer(){
        
        logger.info("Entrando a ConciliacionServiceImpl.obtieneFechaDiaFinAyer()");
        
        /* Se determina la fecha de fin de dia de ayer */
        Assert.notNull(dateUtilsDao, "La inyecci\u00f3n del DAO fall\u00f3");
        /* Obtiene fecha actual */
        Date fechaActual = dateUtilsDao.getDateFechaDB();
        Assert.notNull(fechaActual, "La fecha actual no puede ser nula");
        logger.debug("La fecha actual es : [" + fechaActual + "]");
        
        return DateUtil.addDays(dateUtilsDao.getFechaHoraFinDia(fechaActual), -1);
        
    }
    
    /**
     * @return the dateUtilsDao
     */
    public DateUtilsDao getDateUtilsDao() {
        return dateUtilsDao;
    }

    /**
     * @param dateUtilsDao the dateUtilsDao to set
     */
    public void setDateUtilsDao(DateUtilsDao dateUtilsDao) {
        this.dateUtilsDao = dateUtilsDao;
    }

    /**
     * @return the errorResolver
     */
    public MessageResolver getErrorResolver() {
        return errorResolver;
    }

    /**
     * @param errorResolver the errorResolver to set
     */
    public void setErrorResolver(MessageResolver errorResolver) {
        this.errorResolver = errorResolver;
    }

    /**
     * @return the regContValNombradaDao
     */
    public RegContValNombradaDao getRegContValNombradaDao() {
        return regContValNombradaDao;
    }

    /**
     * @param regContValNombradaDao the regContValNombradaDao to set
     */
    public void setRegContValNombradaDao(RegContValNombradaDao regContValNombradaDao) {
        this.regContValNombradaDao = regContValNombradaDao;
    }

    /**
     * @return the utilService
     */
    public UtilServices getUtilService() {
        return utilService;
    }

    /**
     * @param utilService the utilService to set
     */
    public void setUtilService(UtilServices utilService) {
        this.utilService = utilService;
    }

	/**
	 * @param posicionNombradaDaliDao
	 */
	public void setPosicionNombradaDao(PosicionNombradaDaliDao posicionNombradaDaliDao) {
		this.posicionNombradaDaliDao = posicionNombradaDaliDao;
	}

	/**
	 * @param operacionNombradaDao
	 */
	public void setOperacionNombradaDao(OperacionNombradaDao operacionNombradaDao) {
		this.operacionNombradaDao = operacionNombradaDao;
	}
	
	public BigInteger getPosicionInicial(AgenteVO agenteFirmado,
			EmisionVO emision, BigInteger bovedaId) {
		BigInteger retorno =BigInteger.valueOf(0);
		
		if( agenteFirmado != null && emision != null && bovedaId != null ) {
			TPosicionNombradaParamsPersistence params = new TPosicionNombradaParamsPersistence();
			
			if( StringUtils.isNotBlank(agenteFirmado.getId()) ) {
				params.setIdInstitucion(agenteFirmado.getId());
			}
			
			if( StringUtils.isNotBlank(agenteFirmado.getFolio()) ) {
				params.setFolioInstitucion(agenteFirmado.getFolio());
			}
			
			if( StringUtils.isNotBlank(agenteFirmado.getCuenta()) ) {
				String[] cuentas = {agenteFirmado.getCuenta()};
				params.setCuentas(cuentas);
			}
			
			if( StringUtils.isNotBlank(emision.getIdTipoValor()) ) {
				String[] tvs = {emision.getIdTipoValor()};
				params.setTiposDeValor(tvs);
			}
			
			if( StringUtils.isNotBlank(emision.getEmisora()) ) {
				params.setEmisora(emision.getEmisora());
			}
			
			if( StringUtils.isNotBlank(emision.getSerie()) ) {
				params.setSerie(emision.getSerie());
			}
			
			if( bovedaId.compareTo(BigInteger.ZERO) > 0 ) {
				params.setIdBoveda(bovedaId);
			}
			
			Date fechaActual = dateUtilsDao.getDateFechaDB();
			logger.info("Fecha Actual: [" + fechaActual + "]");
        	Date fechaAyer = utilService.agregarDiasHabiles(fechaActual, -1);
        	logger.info("Fecha Ayer: [" + fechaAyer + "]");
        	params.setFechaInicio(fechaAyer);
			
			retorno = posicionNombradaDaliDao.getSaldoInicial(params);
		}
		
		return retorno;
	}
	
}
