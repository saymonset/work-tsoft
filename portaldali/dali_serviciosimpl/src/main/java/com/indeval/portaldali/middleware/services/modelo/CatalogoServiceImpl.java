/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.modelo;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bursatec.util.message.MessageResolver;
import com.indeval.portaldali.middleware.services.modelo.util.Constantes;
import com.indeval.portaldali.middleware.services.modelo.util.UtilsVOCatalogo;
import com.indeval.portaldali.middleware.services.util.UtilServices;
import com.indeval.portaldali.middleware.services.util.UtilsDaliVO;
import com.indeval.portaldali.persistence.dao.common.DateUtilsDao;
import com.indeval.portaldali.persistence.dao.common.InstrumentoDaliDao;
import com.indeval.portaldali.persistence.dao.common.PosicionControladaDaliDao;
import com.indeval.portaldali.persistence.dao.common.PosicionNombradaDaliDao;
import com.indeval.portaldali.persistence.vo.EmisionPersistence;
import com.indeval.portaldali.persistence.vo.InstrumentoVO;
import com.indeval.portaldali.persistence.vo.PageVO;
import com.indeval.portaldali.persistence.vo.TPosicionControladaParamsPersistence;
import com.indeval.portaldali.persistence.vo.TPosicionNombradaParamsPersistence;

/**
 * Implementacion de los servicios de catalogo
 * 
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class CatalogoServiceImpl implements CatalogoService, Constantes {

//	/** Bean para CLotesDao */
//	private CLotesDao clotesDao;
//
//	/** Bean de acceso a datos de Emisiones */
//	private EmisionesDao emisionesDao;
//	
//    /** Bean de acceso a datos de EmisionesDaoJDBC */
//    private EmisionesDaoJDBC emisionesDaoJdbc;
//	
//    /** Bean de acceso a EmisoraDao */
//    private EmisoraDao emisoraDao;
//    
//    /** Bean de acceso a datos de EstadoValores. */
//    private EstadoValoresDao estadoValoresDao;
//        
//    /** Bean de acceso a datos de HistoricoEstadoCuentaTraspasoDao */
//    private HistoricoEstadoCuentaTraspasoDao historicoEstadoCuentaTraspasoDao;
//        
//    /** Acceso a consultas de la tabla historica..hstoposi */
//    private HistoricoPosicionDao historicoPosicionDao;
    
	/** Bean de acceso a datos de Instrumento */
	private InstrumentoDaliDao instrumentoDaliDao;
	
	/** Bean para acceso al properties de mensaje de errores */
	private MessageResolver errorResolver;
    
    /** Bean de acceso a datos de PosicionNombrada */
    private PosicionNombradaDaliDao posicionNombradaDaliDao;
    
    /** Bean de acceso a datos de PosicionControlada */
    private PosicionControladaDaliDao posicionControladaDaliDao;

//    /** Bean de acceso a datos de TransaccionMercadoDineroDao */
//    private TransaccionMercadoDineroDao tranMercDinDao;
//    
//    /** Bean de acceso a datos de TraspasoDao */
//    private TraspasoDao traspasoDepositosRetirosDao;
        
	/** Bean de acceso a DateUtilsDao */
	private DateUtilsDao dateUtilsDao;
	
	/** Bean de acceso a UtilService */
	private UtilServices utilService;
	   
//    /** Acceso a consultas de la tabla bdcapital..valor */
//    private ValorDao valorDao;
    
	/** Log de clase. */
	private static final Logger logger = LoggerFactory.getLogger(CatalogoServiceImpl.class);
    
    /**
     * @see com.indeval.portaldali.middleware.services.modelo.CatalogoService#getVencimientosPendientesByInstitucionFechaVencimientoAgurpadosPorEmision(com.indeval.portaldali.middleware.services.modelo.AgenteVO, java.util.Date, java.math.BigInteger, com.indeval.portaldali.middleware.services.modelo.PaginaVO)
     */
    public PaginaVO getVencimientosPendientesByInstitucionFechaVencimientoAgurpadosPorEmision( AgenteVO agenteVO, Date fechaVencimiento, BigInteger idBoveda, PaginaVO paginaVO, Boolean debeDejarLog) throws BusinessException {
    	
    	logger.info("Entrando a CatalogoServiceImpl." + "getVencimientosPendientesByInstitucionFechaVencimientoAgurpadosPorEmision()");

		utilService.validaDTONoNulo(fechaVencimiento, " fechaVencimiento ");
		utilService.validaDTONoNulo(agenteVO, " agenteVO ");
		agenteVO.tieneClaveValida();

		TPosicionControladaParamsPersistence tPosicionControladaParamsPersistence = UtilsVOCatalogo.getInstanceTPosicionControladaParamsPersistence(agenteVO, null);
//		tPosicionControladaParamsPersistence.setPageVO(UtilsDaliVO.getPageVO(paginaVO));
		tPosicionControladaParamsPersistence
				.setFechaVencimiento(fechaVencimiento);
		if (idBoveda != null) {
			logger.trace("Realiza filtro por idBoveda: " + idBoveda);
			tPosicionControladaParamsPersistence.setIdBoveda(idBoveda);
		}

		final List<Object[]> resultado = posicionControladaDaliDao.getVencsPendientesByInstitucionFechaVencimiento(tPosicionControladaParamsPersistence);
	
		if (resultado == null || resultado.isEmpty()) {
			paginaVO.setRegistros(null);
			paginaVO.setTotalRegistros(0);
		} else {
			preparaEmisionVO(paginaVO, resultado);
		} 

		/* Regresa la pagina con la nueva lista de registros */
		return paginaVO;
	}

	private void preparaEmisionVO(PaginaVO paginaVO, List<Object[]> resultado) {
		final List<EmisionVO> lista = new ArrayList<EmisionVO>();
		for (final Object[] posCtr : resultado) {
			final EmisionVO emision = preparesEmisionVo(posCtr);
			lista.add(emision);
		}
		paginaVO.extraerSublist(lista);
	}

	private EmisionVO preparesEmisionVo(final Object[] rowFromQry) {
		final EmisionVO emision = new EmisionVO();
		emision.setSaldoDisponible(BigDecimal.valueOf(new BigDecimal(
				rowFromQry[SUMA_POS].toString()).doubleValue()));
		emision.setIdTipoValor(rowFromQry[TIPO_VALOR].toString()); // posCtr.getEmision().getInstrumento().getClaveTipoValor()
		emision.setEmisora(rowFromQry[EMISORA].toString());// posCtr.getEmision().getEmisora().getClavePizarra()
		emision.setSerie(rowFromQry[SERIE].toString());// posCtr.getEmision().getSerie()
		emision.setNombreCortoBoveda(rowFromQry[NOMBRE_BVD_POS].toString());// posCtr.getEmision().getBoveda().getNombreCorto().toString()
		emision.setCupon(rowFromQry[CUPON].toString());// posCtr.getCupon().getClaveCupon()
		if (logger.isTraceEnabled()) {
			logger.trace("prepareEmisionVo() \n" + emision);
		}
		return emision;
	}

    /**
     * @see com.indeval.portaldali.middleware.services.modelo.CatalogoService#getEmision(com.indeval.portaldali.middleware.services.modelo.EmisionVO)
     */
    public EmisionVO getEmision(EmisionVO emisionVO, BigInteger idBoveda) throws BusinessException {
        
        logger.info("Entrando a CatalogoServiceImpl.getEmision()");
        
        /* Se valida la emision recibida */
        utilService.validaDTONoNulo(emisionVO, " emision ");
        emisionVO.tienePKValida();
        
        // Mensaje en caso de error
        StringBuffer mensaje = new StringBuffer(" No se encontraron registros coincidentes ");
        if (emisionVO != null && StringUtils.isNotBlank(emisionVO.getIdTipoValor())) {
            mensaje.append(" con el tipo valor = [" + emisionVO.getIdTipoValor() + "]");
        }
        
        TPosicionNombradaParamsPersistence tPosicionNombradaParamsPersistence = 
            UtilsVOCatalogo.getInstanceTPosicionNombradaParamsPersistence(null, emisionVO);
        tPosicionNombradaParamsPersistence.setConsulta(BY_MERCADO);
        tPosicionNombradaParamsPersistence.setMercado(PAPELES_DINERO);
        tPosicionNombradaParamsPersistence.setIdBoveda(idBoveda);
        
        PageVO pageVO = 
            posicionNombradaDaliDao.getTPosicionNombrada(tPosicionNombradaParamsPersistence);
        
        EmisionVO emision = null;
        PaginaVO pagina = this.convertPageVOTPosicionNombrada2PaginaVOEmisionVO(pageVO, mensaje.toString());
        if(pagina.getRegistros() != null && !pagina.getRegistros().isEmpty()) {
        	emision = (EmisionVO) pagina.getRegistros().get(0);
        }
        
        return emision;
    }

    /**
     * @see com.indeval.portaldali.middleware.services.modelo.CatalogoService#getEmisoraByTVDinero(java.lang.String)
     */
    public String getEmisoraByTVDinero(String tv) throws BusinessException {

		logger.info("Entrando a CatalogoServiceImpl.getEmisoraByTVDinero()");

		if (StringUtils.isBlank(tv)) {
			throw new BusinessException(errorResolver.getMessage("J0028", 
					new Object[]{UtilsDaliVO.BLANK}));
		}
        List emisoras = posicionNombradaDaliDao.getEmisoraByTV(tv);

		if (emisoras == null || emisoras.size() != 1) {
			return null;
		}
		return (String) emisoras.get(0);
    	
    }

    /**
     * @see com.indeval.portaldali.middleware.services.modelo.CatalogoService#getListaEmisionesDinero(com.indeval.portaldali.middleware.services.modelo.AgenteVO, com.indeval.portaldali.middleware.services.modelo.EmisionVO)
     */
    public EmisionVO[] getListaEmisionesDinero(AgenteVO agenteVO, EmisionVO emisionVO, 
            BigInteger idBoveda) throws BusinessException {

		logger.info("Entrando a CatalogoServiceImpl.getListaEmisionesDinero()");

		return this.getArrayEmisionVO(this.getListaEmisionesDinero(
		        agenteVO, emisionVO, idBoveda, new PaginaVO()));
    	
    }

    /**
     * @see com.indeval.portaldali.middleware.services.modelo.CatalogoService#getListaEmisionesDinero(com.indeval.portaldali.middleware.services.modelo.AgenteVO, com.indeval.portaldali.middleware.services.modelo.EmisionVO, com.indeval.portaldali.middleware.services.modelo.PaginaVO)
     */
    public PaginaVO getListaEmisionesDinero(AgenteVO agenteVO, EmisionVO emisionVO, 
            BigInteger idBoveda, PaginaVO paginaVO) throws BusinessException {
        
        logger.info("Entrando a CatalogoServiceImpl.getListaEmisionesDinero()");
        
        /* Se valida el agente recibido */
        utilService.validaAgente(agenteVO);
                
        /* Se valida la emision recibida */
        utilService.validaDTONoNulo(emisionVO, " emision ");
        

        TPosicionNombradaParamsPersistence tPosicionNombradaParamsPersistence = 
            UtilsVOCatalogo.getInstanceTPosicionNombradaParamsPersistence(agenteVO, emisionVO);
        tPosicionNombradaParamsPersistence.setIdBoveda(idBoveda);
        
        /* Se obtiene y se valida la pagina de resultados */
        PageVO pageVO = posicionNombradaDaliDao.getTPosicionNombrada(tPosicionNombradaParamsPersistence);
        
        return this.convertPageVOTPosicionNombrada2PaginaVOEmisionVO(pageVO, null);
        
    }

    /**
     * @see com.indeval.portaldali.middleware.services.modelo.CatalogoService#getListaEmisionesValpreE(com.indeval.portaldali.middleware.services.modelo.EmisionVO, com.indeval.portaldali.middleware.services.modelo.AgenteVO)
     */
    public EmisionVO[] getListaEmisionesValpreE(EmisionVO emisionVO, AgenteVO agenteVO,
            BigInteger idBoveda) throws BusinessException {
    	
    	logger.info("Entrando a CatalogoServiceImpl.getListaEmisionesValpreE()");

        return this.getArrayEmisionVO(this.getListaEmisionesValpreE(
                emisionVO, agenteVO, idBoveda, new PaginaVO()));

    }
    
    /**
     * @see com.indeval.portaldali.middleware.services.modelo.CatalogoService#getListaEmisionesValpreE(com.indeval.portaldali.middleware.services.modelo.EmisionVO, com.indeval.portaldali.middleware.services.modelo.AgenteVO, com.indeval.portaldali.middleware.services.modelo.PaginaVO)
     */
    public PaginaVO getListaEmisionesValpreE(EmisionVO emisionVO, AgenteVO agenteVO,
            BigInteger idBoveda, PaginaVO paginaVO) throws BusinessException {

        logger.info("Entrando a CatalogoServiceImpl.getListaEmisionesValpreE()");

        /* Se valida el agente recibido */
        utilService.validaAgente(agenteVO, false);

        /* Se valida la emision recibida */
        utilService.validaDTONoNulo(emisionVO, " emision ");

        TPosicionNombradaParamsPersistence tPosicionNombradaParamsPersistence = 
            UtilsVOCatalogo.getInstanceTPosicionNombradaParamsPersistence(agenteVO, emisionVO);
        tPosicionNombradaParamsPersistence.setIdBoveda(idBoveda);
            
        PageVO pageVO = 
            posicionNombradaDaliDao.getTPosicionNombradaValpreE(tPosicionNombradaParamsPersistence);
        
        return this.convertPageVOTPosicionNombrada2PaginaVOEmisionVO(pageVO, null);
        
    }
    
    /**
     * @see com.indeval.portaldali.middleware.services.modelo.CatalogoService#getListaEmisionesValpreEAdmonG(com.indeval.portaldali.middleware.services.modelo.AgenteVO, com.indeval.portaldali.middleware.services.modelo.EmisionVO)
     */
    public EmisionVO[] getListaEmisionesValpreEAdmonG(AgenteVO agenteVO, EmisionVO emisionVO, 
            BigInteger idBoveda) throws BusinessException{

    	logger.info("Entrando a CatalogoServiceImpl.getListaEmisionesValpreEAdmonG()");
        
        PaginaVO paginaVO = 
            this.getListaEmisionesValpreEAdmonG(agenteVO, emisionVO, idBoveda, new PaginaVO());
        
		return this.getArrayEmisionVO(paginaVO);
    	
    }
    
    /**
     * @see com.indeval.portaldali.middleware.services.modelo.CatalogoService#getListaEmisionesValpreEAdmonG(com.indeval.portaldali.middleware.services.modelo.AgenteVO, com.indeval.portaldali.middleware.services.modelo.EmisionVO, com.indeval.portaldali.middleware.services.modelo.PaginaVO)
     */
    public PaginaVO getListaEmisionesValpreEAdmonG(AgenteVO agenteVO, EmisionVO emisionVO, 
            BigInteger idBoveda, PaginaVO paginaVO) throws BusinessException{

        logger.info("Entrando a CatalogoServiceImpl.getListaEmisionesValpreEAdmonG()");
        
        /* Se valida el agente recibido */
        utilService.validaAgente(agenteVO, false);
        
        TPosicionNombradaParamsPersistence tPosicionNombradaParamsPersistence =
            UtilsVOCatalogo.getInstanceTPosicionNombradaParamsPersistence(agenteVO, emisionVO);
        tPosicionNombradaParamsPersistence.setIdBoveda(idBoveda);
        PageVO pageVO = 
            posicionNombradaDaliDao.getTPosicionNombradaValpreEAdmonG(tPosicionNombradaParamsPersistence);
        
        return this.convertPageVOTPosicionNombrada2PaginaVOEmisionVO(pageVO, null);
        
    }

    /**
     * @see com.indeval.portaldali.middleware.services.modelo.CatalogoService#getListaEmisiones(com.indeval.portaldali.middleware.services.modelo.EmisionVO, java.lang.Boolean)
     */
    public EmisionVO[] getListaEmisiones(EmisionVO emisionVO, BigInteger idBoveda, 
            Boolean mercado) throws BusinessException{

    	logger.info("Entrando a CatalogoServiceImpl.getListaEmisiones()");
    	
    	return this.getListaEmisiones(emisionVO, idBoveda, mercado, false);
    	
    }

    /**
     * @see com.indeval.portaldali.middleware.services.modelo.CatalogoService#getListaEmisiones(com.indeval.portaldali.middleware.services.modelo.EmisionVO, java.lang.Boolean, boolean)
     */
    public EmisionVO[] getListaEmisiones(EmisionVO emisionVO, BigInteger idBoveda, Boolean mercado, 
            boolean isMercadoDinero) throws BusinessException{
        
        logger.info("Entrando a CatalogoServiceImpl.getListaEmisiones()");
        
        return this.getArrayEmisionVO(this.getListaEmisiones(emisionVO, idBoveda, mercado, 
                isMercadoDinero, new PaginaVO()));
    }

    /**
     * @see com.indeval.portaldali.middleware.services.modelo.CatalogoService#getListaEmisiones(com.indeval.portaldali.middleware.services.modelo.EmisionVO, java.lang.Boolean, boolean, com.indeval.portaldali.middleware.services.modelo.PaginaVO)
     */
    public PaginaVO getListaEmisiones(EmisionVO emisionVO, BigInteger idBoveda, Boolean mercado, 
            boolean isMercadoDinero, PaginaVO paginaVO) throws BusinessException{

    	logger.info("Entrando a CatalogoServiceImpl.getListaEmisiones()");
    	

        // Se validan los parametros de entrada
        utilService.validaDTONoNulo(emisionVO, " EmisionVO ");
        if (mercado == null || mercado.booleanValue()) {
            if (StringUtils.isBlank(emisionVO.getIdTipoValor())) {
    			throw new BusinessException(errorResolver.getMessage("J0028", 
    					new Object[]{UtilsDaliVO.BLANK}));
            }
        }
        
        // Se construye el objeto Example
        EmisionPersistence emisionExample = new EmisionPersistence();
        emisionExample.setEmisionPk(UtilsDaliVO.getEmisionPK(emisionVO));
        emisionExample.setCuponCortado(Constantes.EN_FIRME);
        emisionExample.setInstrumento(new com.indeval.portaldali.persistence.vo.InstrumentoVO());

        if (!isMercadoDinero) {

            // aqui estamos en mercado de capitales.
            emisionExample = buscaInstrumento(emisionExample);

            if (!(PAPEL_BANCARIO.equalsIgnoreCase(emisionExample
                    .getInstrumento().getMercado()) || PAPEL_GUBERNAMENTAL
                    .equalsIgnoreCase(emisionExample.getInstrumento()
                            .getMercado()))) {
                emisionExample.getInstrumento().setMercado(MERCADO_CAPITALES);

            } 
            else {
                throw new BusinessException(errorResolver.getMessage("J0077",
                        new Object[] { "Capitales" }));  
            }
        } 
        else {

            emisionExample.getInstrumento().setMercado(mercado == null || mercado.booleanValue() ?
                    PAPELES_DINERO : MERCADO_CAPITALES);
            
        }
        
        TPosicionNombradaParamsPersistence tPosicionNombradaParamsPersistence = 
            UtilsVOCatalogo.getTPosicionNombradaParamsPersistenceExample(emisionExample);
        tPosicionNombradaParamsPersistence.setIdBoveda(idBoveda);

        PageVO pageVO = 
            posicionNombradaDaliDao.getTPosicionNombradaByExample(tPosicionNombradaParamsPersistence);
        
        return this.convertPageVOTPosicionNombrada2PaginaVOEmisionVO(pageVO, null);
    	
    }
    
    /**
     * @see com.indeval.portaldali.middleware.services.modelo.CatalogoService#getListaEmisionesTesoreria(com.indeval.portaldali.middleware.services.modelo.EmisionVO, java.lang.Boolean, boolean)
     */
    public EmisionVO[] getListaEmisionesTesoreria(EmisionVO emisionVO, BigInteger idBoveda, 
            Boolean mercado, boolean isMercadoDinero) throws BusinessException{
        
        logger.info("Entrando a CatalogoServiceImpl.getListaEmisionesTesoreria()");
        
        return this.getArrayEmisionVO(this.getListaEmisionesTesoreria(emisionVO, idBoveda, mercado, 
                isMercadoDinero, new PaginaVO()));
    }
    
    /**
     * @see com.indeval.portaldali.middleware.services.modelo.CatalogoService#getListaEmisionesTesoreria(com.indeval.portaldali.middleware.services.modelo.EmisionVO, java.lang.Boolean, boolean, com.indeval.portaldali.middleware.services.modelo.PaginaVO)
     */
    public PaginaVO getListaEmisionesTesoreria(EmisionVO emisionVO, BigInteger idBoveda, 
            Boolean mercado, boolean isMercadoDinero, PaginaVO paginaVO) throws BusinessException {
        
        logger.info("Entrando a CatalogoServiceImpl.getListaEmisionesTesoreria()");
        
        // Se validan los parametros de entrada
        utilService.validaDTONoNulo(emisionVO, " EmisionVO ");
        if (mercado == null || mercado.booleanValue()) {
            if (StringUtils.isBlank(emisionVO.getIdTipoValor())) {
                throw new BusinessException(errorResolver.getMessage("J0028", 
                        new Object[]{UtilsDaliVO.BLANK}));
            }
        }

        // Se construye el objeto Example y se recupera el instrumento
        EmisionPersistence emisionExample = new EmisionPersistence();
        emisionExample.setCuponCortado(Constantes.EN_FIRME);
        emisionExample.setEmisionPk(UtilsDaliVO.parseEmisionVO2EmisionPK(emisionVO));
        emisionExample.setInstrumento(new com.indeval.portaldali.persistence.vo.InstrumentoVO());
        emisionExample = buscaInstrumento(emisionExample);

        /* Se settea el mercado en el emisionExample */
        if (!isMercadoDinero) {

            /* Como la consulta es de tesoreria, se determina a cual mercado pertenece la emision */
            if (!(PAPEL_BANCARIO.equalsIgnoreCase(emisionExample
                    .getInstrumento().getMercado()) || PAPEL_GUBERNAMENTAL
                    .equalsIgnoreCase(emisionExample.getInstrumento()
                            .getMercado()))) {
                logger.debug("La emision es de mercado de capitales");
                emisionExample.getInstrumento().setMercado(MERCADO_CAPITALES);

            } 
            else {
                emisionExample.getInstrumento().setMercado(PAPELES_DINERO);
            }
        } 
        else {

            emisionExample.getInstrumento().setMercado(mercado == null || mercado.booleanValue() ?
                    PAPELES_DINERO : MERCADO_CAPITALES);

        }

        TPosicionNombradaParamsPersistence tPosicionNombradaParamsPersistence = 
            UtilsVOCatalogo.getTPosicionNombradaParamsPersistenceExample(emisionExample);
        tPosicionNombradaParamsPersistence.setIdBoveda(idBoveda);

        PageVO pageVO = 
            posicionNombradaDaliDao.getTPosicionNombradaByExample(tPosicionNombradaParamsPersistence);
        
        return this.convertPageVOTPosicionNombrada2PaginaVOEmisionVO(pageVO, null);
                
    }

    /**
     * Valida (y recupera de ser necesario) el instrumento para la emision recibida
	 *
	 * @param emision
	 * @return EmisionPersistence recibida con el instrumento recuperado
	 * @throws BusinessException
	 */
	public EmisionVO buscaInstrumento(EmisionVO emisionVO) throws BusinessException {

        logger.info("Entrando a MercadoDineroServiceImpl.buscaInstrumento()");

		return UtilsDaliVO.getEmisionVO(
				this.buscaInstrumento(UtilsDaliVO.parseEmisionVO2Emision(emisionVO)));

	}
	
	/**
     * Valida (y recupera de ser necesario) el instrumento para la emision recibida
	 *
	 * @param emision
	 * @return EmisionPersistence recibida con el instrumento recuperado
	 * @throws BusinessException
	 */
	private EmisionPersistence buscaInstrumento(EmisionPersistence emision) throws BusinessException {

        logger.info("Entrando a MercadoDineroServiceImpl.buscaInstrumento()");
        
		/* Se recupera el instrumento y se valida el mercado si la emision no esta registrada */
		String mercado = null;
		
		if (emision.getInstrumento() == null
				|| StringUtils.isBlank(emision.getInstrumento().getMercado())) {
			utilService.validaDTONoNulo(emision.getEmisionPk(), " EmisionPK ");
			if (StringUtils.isNotBlank(emision.getEmisionPk().getTv())) {
				emision.setInstrumento(this.getInstrumento(emision));
			} 
            else {
                throw new BusinessException(
                        errorResolver.getMessage("J0028", new Object[]{" para las emisiones"}));
			}
		}

		if (emision.getInstrumento() != null) {
			mercado = emision.getInstrumento().getMercado().trim();
			
			utilService.validaDTONoNulo(mercado, " mercado ");
		}
		

		logger.debug("El mercado del instrumento es = [" + mercado + "]");

		return emision;

	}
	
    /**
     * Retorna el Instrumento correspondiente a la tv de una emision
     *
     * @param emision
     *
     * @return Instrumento
     *
     * @throws BusinessException
     */
    private InstrumentoVO getInstrumento(EmisionPersistence emision) throws BusinessException {

        logger.info("Entrando a MercadoDineroServiceImpl.getFolioOperaciones()");

        utilService.validaDTONoNulo(emision, " EmisionPersistence ");
        utilService.validaDTONoNulo(emision.getEmisionPk(), " EmisionPK ");

        return UtilsVOCatalogo.getInstanceInstrumento(instrumentoDaliDao.getInstrumento(
                utilService.validarClaveTipo(emision.getEmisionPk().getTv(), " Tipo Valor ")));

    }

    /**
     * @see com.indeval.portaldali.middleware.services.modelo.CatalogoService#getListaEmisiones(java.lang.String[])
     */
    public EmisionVO[] getListaEmisiones(String[] idTipoValor, BigInteger idBoveda) 
            throws BusinessException {
        
        logger.info("Entrando a CatalogoServiceImpl.getListaEmisiones()");
        
        return this.getArrayEmisionVO(this.getListaEmisiones(idTipoValor, idBoveda, new PaginaVO()));
        
    }

    /**
     * @see com.indeval.portaldali.middleware.services.modelo.CatalogoService#getListaEmisiones(java.lang.String[], com.indeval.portaldali.middleware.services.modelo.PaginaVO)
     */
    public PaginaVO getListaEmisiones(String[] idTipoValor, BigInteger idBoveda, PaginaVO paginaVO) 
            throws BusinessException {

    	logger.info("Entrando a CatalogoServiceImpl.getListaEmisiones()");
    	
    	// Se valida el arreglo de entrada
    	utilService.validaDTONoNulo(idTipoValor, " arreglo de tipos de valores ");
    	String[] tipoValorParser = utilService.parseArray(idTipoValor);
		if (tipoValorParser == null) {
			throw new BusinessException(errorResolver.getMessage("J0078", 
                    new Object[] {" de tipos de valores "}));
		}
        
        TPosicionNombradaParamsPersistence tPosicionNombradaParamsPersistence = 
            UtilsVOCatalogo.getInstanceTPosicionNombradaParamsPersistence(null, null);
        tPosicionNombradaParamsPersistence.setTiposDeValor(tipoValorParser);
        tPosicionNombradaParamsPersistence.setIdBoveda(idBoveda);
        
        PageVO pageVO = posicionNombradaDaliDao.getTPosicionNombradaByIdTipoValor(
                tPosicionNombradaParamsPersistence);
        
		return this.convertPageVOTPosicionNombrada2PaginaVOEmisionVO(pageVO, null);
    	
    }

//    /**
//     * @see com.indeval.portaldali.middleware.services.modelo.CatalogoService#getListaEmisionesByCuentaEstadoCuentaMercadoCapitales(com.indeval.portaldali.middleware.services.mercadocapitales.GetEstadocuentaMercadoParams)
//     */
//    public CuentasYEmisionesEstadoCuentaCapitalesVO 
//    		getListaEmisionesByCuentaEstadoCuentaMercadoCapitales(
//    				GetEstadocuentaMercadoParams params) throws BusinessException{
//
//    	log.info("Entrando a " +
//    			"CatalogoServiceImpl.getListaEmisionesByCuentaEstadoCuentaMercadoCapitales()");
//    	
//    	// Se valida el objeto de parametros cuando se invoca directamente
//        if (params.isObtenerListas()) {
//            params.validaParams();
//        }
//
//        // Se construye el arreglo de AgentesPK
//        List listaCuentas = new ArrayList();
//
//        // Se recupera la lista de cuentas
//        if (StringUtils.isBlank(params.getAgenteFirmado().getCuenta()) || 
//        		params.isObtenerListas()) {
//
//            // Si el agente recibido no tiene la cuenta, o si el servicio fue
//            // llamado
//            // para obtener las listas de cuentas y de emisiones, se recuperan
//            // todas las cuentas
//            listaCuentas = traspasoDepositosRetirosDao
//                    .getListaCuentasNuevoEstadoCuentaMercadoCapitales(
//                    		parseGetEstadocuentaMercadoParams2GetEstadocuentaMercadoVO(params));
//
//            if (listaCuentas == null || listaCuentas.isEmpty()) {
//            	throw new BusinessException(errorResolver.getMessage("J9997", 
//            			new Object[]{" para el agente "+ params.getAgenteFirmado().toString()}));            	
//            }
//
//        }
//        else {
//            listaCuentas.add(params.getAgenteFirmado().getCuenta());
//        }
//        UtilsLog.logElementosLista(listaCuentas);
//
//        String[] arregloCuentas = new String[listaCuentas.size()];
//        arregloCuentas = (String[]) listaCuentas.toArray(arregloCuentas);
//
//        // Se construye objeto de retorno y se settea el arreglo de cuentas
//        CuentasYEmisionesEstadoCuentaCapitalesVO retorno = 
//        	new CuentasYEmisionesEstadoCuentaCapitalesVO();
//        retorno.setArregloCuentas(arregloCuentas);
//
//        // Se settea la primera cuenta en caso de que el agente firmado no tenga
//        // la cuenta
//        if (StringUtils.isBlank(params.getAgenteFirmado().getCuenta())) {
//            params.getAgenteFirmado().setCuenta((String) listaCuentas.get(0));
//        }
//
//        // Se retira la emision si es una invocacion desde la pantalla
//        if (params.isObtenerListas() && !params.isConFiltro()) {
//            params.setClaveValor(new EmisionVO());
//        }
//
//        // Se recupera la lista de emisiones para la cuenta
//        List listaEmisiones = traspasoDepositosRetirosDao
//                .getListaEmisionesByCuentaNuevoEstadoCuentaMercadoCapitales(
//                		parseGetEstadocuentaMercadoParams2GetEstadocuentaMercadoVO(params));
//
//        if (listaEmisiones == null || listaEmisiones.isEmpty()) {
//            StringBuffer mensajeError = new StringBuffer(" No hay registros ");
//            mensajeError.append(" para el agente " + params.getAgenteFirmado().toString());
//            if (params.getClaveValor() != null) {
//                mensajeError.append(" con la emision " + params.getClaveValor().toResumeString());
//            }
//            throw new BusinessException(mensajeError.toString());
//        }
//
//        // Se construye el arreglo de emisionesVO a partir de la lista de
//        // arreglos de Object
//        List listaEmisionesVO = parseObject2EmisionesVO(listaEmisiones, false);
//        log.debug(" Hay [" + listaEmisionesVO.size() + "] emisiones para el agente ["
//                + params.getAgenteFirmado().toString() + "] ");
//        log.debug("Imprimendo las emisionesVO");
//        UtilsLog.logElementosLista(listaEmisionesVO);
//
//        EmisionVO[] arregloEmisionesVO = new EmisionVO[listaEmisionesVO.size()];
//        arregloEmisionesVO = (EmisionVO[]) listaEmisionesVO.toArray(arregloEmisionesVO);
//
//        // Se settea el arreglo de emisiones en el objeto de retorno
//        retorno.setArregloEmisionesCuenta(arregloEmisionesVO);
//        return retorno;
//    	
//    }

//    /**
//     * Construye un objeto GetEstadocuentaMercadoVO a partir del
//     * GetEstadocuentaMercadoParams recibido
//     * 
//     * @param params
//     * @return GetEstadocuentaMercadoVO
//     */
//    private GetEstadocuentaMercadoVO parseGetEstadocuentaMercadoParams2GetEstadocuentaMercadoVO(
//            GetEstadocuentaMercadoParams params) {
//
//        log.info("Entrando a " +
//        		"CatalogoServiceImpl.parseGetEstadocuentaMercadoParams2GetEstadocuentaMercadoVO()");
//
//        GetEstadocuentaMercadoVO paramsPersistence = null;
//
//        if (params != null) {
//            paramsPersistence = new GetEstadocuentaMercadoVO();
//            paramsPersistence.setAgenteFirmado(UtilsDaliVO.parseAgenteVO2AgentePK(params
//                    .getAgenteFirmado()));
//            if (StringUtils.isNotBlank(params.getAplicacion())) {
//                paramsPersistence.setAplicacion(params.getAplicacion().trim());
//            }
//            paramsPersistence.setClaveValor(UtilsDaliVO
//                    .parseEmisionVO2EmisionPK(params.getClaveValor()));
//            paramsPersistence.setFechaOperacion(params.getFechaOperacion());
//            paramsPersistence.setFechaAyer(utilService.getLaborableDay(
//            		utilService.modificaFecha(params.getFechaOperacion(), Calendar.DATE, -1), -1));
//            Date fechaOperacionInicioDia = params.getFechaOperacion();
//            Date fechaOperacionFinDia = params.getFechaOperacion();
//            Date[] fechas = DateUtil.preparaIntervaloFechas(fechaOperacionInicioDia,
//                    fechaOperacionFinDia);
//            paramsPersistence.setFechaOperacionInicioDia(fechas[0]);
//            paramsPersistence.setFechaOperacionFinDia(fechas[1]);
//            if (StringUtils.isNotBlank(params.getOrigen())) {
//                paramsPersistence.setOrigen(params.getOrigen().trim());
//            }
//            paramsPersistence.setPagina(UtilsDaliVO.getPageVO(params.getPaginaVO()));
//            paramsPersistence.setTipoOperacion(this.recuperaTiposOperacion(params
//                    .getTipoOperacion()));
//        }
//
//        return paramsPersistence;
//    }
    
//    /**
//     * Aplica las reglas de negocio para la seleccion de tipos de operacion
//     * 
//     * @param tipoOperacionSeleccionadas
//     * @return String[]
//     */
//    private String[] recuperaTiposOperacion(String[] tipoOperacionSeleccionadas) {
//
//		log.info("Entrando a CatalogoServiceImpl.recuperaTiposOperacion()");
//	
//        String[] tipoOperacion2 = {
//                Constantes.TIPO_OPERACION_S, Constantes.TIPO_OPERACION_O,
//                Constantes.TIPO_OPERACION_T, Constantes.TIPO_OPERACION_A,
//                Constantes.TIPO_OPERACION_D, Constantes.TIPO_OPERACION_R };
//
//        if (tipoOperacionSeleccionadas != null && tipoOperacionSeleccionadas.length > 0) {
//
//            // Se retiran los elementos nulos o vacios del arreglo original
//            String[] tipoOperacionParser = utilService.parseArray(tipoOperacionSeleccionadas);
//            if (tipoOperacionParser != null && tipoOperacionParser.length > 0) {
//                List listaTipoOperacion = Arrays.asList(tipoOperacionParser);
//                List listaTipo = new ArrayList();
//
//                for (Iterator iter = listaTipoOperacion.iterator(); iter.hasNext();) {
//                    String element = (String) iter.next();
//                    if (element.equals(Constantes.TIPO_OPERACION_V)) {
//                        listaTipo.add(Constantes.TIPO_OPERACION_S);
//                        listaTipo.add(Constantes.TIPO_OPERACION_O);
//                    }
//                    else if (element.equals(Constantes.TIPO_OPERACION_T)) {
//                        listaTipo.add(element);
//                        listaTipo.add(Constantes.TIPO_OPERACION_A);
//                    }
//                    else {
//                        listaTipo.add(element);
//                    }
//                }
//                String[] tipoOperacion = new String[listaTipo.size()];
//                tipoOperacion = (String[]) listaTipo.toArray(tipoOperacion);
//                tipoOperacion2 = tipoOperacion;
//            }
//        }
//
//        return tipoOperacion2;
//
//    }
    
//    /**
//     * Si boolean es true, convierte una lista de EmisionVO en una lista de
//     * EmisionPK Si boolean es false, convierte una lista de Object[] en una
//     * lista de EmisionVO
//     * 
//     * @param listaEmisiones
//     * @param pk
//     * @return List
//     * @throws BusinessException
//     */
//    private List parseObject2EmisionesVO(List listaEmisiones, boolean pk) throws BusinessException {
//
//    	log.info("Entrando a CatalogoServiceImpl.parseObject2EmisionesVO()");
//    	
//    	utilService.validaDTONoNulo(listaEmisiones, " listaEmisiones ");
//    	
//    	log.debug("tamanio de la lista principal en metodo privado " + listaEmisiones.size());
//    	
//        List listaReturn = new ArrayList();
//
//        for (Iterator iter = listaEmisiones.iterator(); iter.hasNext();) {
//
//            if (pk) {
//                EmisionVO emisionVO = (EmisionVO) iter.next();
//                EmisionPK emisionPK = UtilsDaliVO.parseEmisionVO2EmisionPK(emisionVO);
//                if (emisionPK != null) {
//                    listaReturn.add(emisionPK);
//                }
//            }
//            else {
//                Object[] elemento = (Object[]) iter.next();
//                EmisionVO emisionVO = new EmisionVO();
//
//                emisionVO.setIdTipoValor(elemento[1] == null ? UtilsDaliVO.BLANK : elemento[1]
//                        .toString());
//                emisionVO.setEmisora(elemento[2] == null ? UtilsDaliVO.BLANK : elemento[2].toString());
//                emisionVO.setSerie(elemento[3] == null ? UtilsDaliVO.BLANK : elemento[3].toString());
//                emisionVO.setCupon(elemento[4] == null ? UtilsDaliVO.BLANK : elemento[4].toString());
//                emisionVO.setSaldoDisponible(elemento[5] == null ? UtilsDaliVO.BIG_DECIMAL_ZERO
//                        : (BigDecimal) elemento[5]);
//                emisionVO.setSaldoInicialDia(elemento[6] == null ? UtilsDaliVO.BIG_DECIMAL_ZERO
//                        : (BigDecimal) elemento[6]);
//                listaReturn.add(emisionVO);
//            }
//        }
//        
//        log.debug("Se devuelve una lista con [" + listaReturn.size() + "]");
//        
//        return listaReturn;
//    }
    
//    /**
//     * @see com.indeval.portaldali.middleware.services.modelo.CatalogoService#getTipoValorByAgente(com.indeval.portaldali.middleware.services.modelo.AgenteVO, java.lang.String)
//     */
//    public String[] getTipoValorByAgente(AgenteVO agenteVO, String tabla) throws BusinessException{
//
//    	log.info("Entrando a CatalogoServiceImpl.getTipoValorByAgente()");
//    	
//    	/* Se validan los parametros */
//    	if (StringUtils.isBlank(tabla)) {
//            throw new BusinessException(errorResolver.getMessage("J0079"));
//        }
//		/* Se valida el agente recibido */
//		utilService.validaAgente(agenteVO, false);
//        
//        if (tabla.equalsIgnoreCase("bdcaptal_valores")
//                && StringUtils.isBlank(agenteVO.getCuenta())) {
//            throw new BusinessException("Falta la Cuenta del agente");
//        }
//
//        List listaTV = new ArrayList();
//
//        if (tabla.equalsIgnoreCase("bdcaptal_mdintran")) {
//            listaTV = tranMercDinDao.getDistinctTv(agenteVO.getId(), agenteVO.getFolio(), null, null);
//        }
//        else if (tabla.equalsIgnoreCase("bdcaptal_valores")) {
//                listaTV = valorDao.getDistinctTv(
//                		agenteVO.getId(), agenteVO.getFolio(), agenteVO.getCuenta());
//        }
//        else {
//        	throw new BusinessException(errorResolver.getMessage("J0080"));
//        }
//
//        String[] tvs = new String[listaTV.size()];
//        for (int i = 0; i < listaTV.size(); i++) {
//            tvs[i] = (String) listaTV.get(i);
//        }
//        return tvs;
//    	
//    }

//    /**
//     * @see com.indeval.portaldali.middleware.services.modelo.CatalogoService#getListaEmisionesMercadoCapitales(com.indeval.portaldali.middleware.services.modelo.AgenteVO, java.lang.String)
//     */
//    public EmisionVO[] getListaEmisionesMercadoCapitales(AgenteVO agenteVO, String tipoValor)
//            throws BusinessException{
//
//    	log.info("Entrando a CatalogoServiceImpl.getListaEmisionesMercadoCapitales()");
//    	
//        EmisionVO emisionVO = new EmisionVO();
//        if (StringUtils.isNotBlank(tipoValor)) {
//            emisionVO.setIdTipoValor(tipoValor);
//        }
//        
//        return getListaEmisionesMercadoCapitales(agenteVO, emisionVO);
//    	
//    }

    /**
     * @see com.indeval.portaldali.middleware.services.modelo.CatalogoService#getListaEmisionesMercadoCapitales(com.indeval.portaldali.middleware.services.modelo.AgenteVO, com.indeval.portaldali.middleware.services.modelo.EmisionVO)
     */
    public EmisionVO[] getListaEmisionesMercadoCapitales(AgenteVO agenteVO, EmisionVO emisionVO, 
            BigInteger idBoveda) throws BusinessException {
        
        logger.info("Entrando a CatalogoServiceImpl.getListaEmisionesMercadoCapitales()");
        
        return this.getArrayEmisionVO(this.getListaEmisionesMercadoCapitales(agenteVO, emisionVO, 
                idBoveda, new PaginaVO()));
        
    }
    
    /**
     * @see com.indeval.portaldali.middleware.services.modelo.CatalogoService#getListaEmisionesMercadoCapitales(com.indeval.portaldali.middleware.services.modelo.AgenteVO, com.indeval.portaldali.middleware.services.modelo.EmisionVO, com.indeval.portaldali.middleware.services.modelo.PaginaVO)
     */
    public PaginaVO getListaEmisionesMercadoCapitales(AgenteVO agenteVO, EmisionVO emisionVO, 
            BigInteger idBoveda, PaginaVO paginaVO) throws BusinessException {
    	
    	logger.info("Entrando a CatalogoServiceImpl.getListaEmisionesMercadoCapitales()");

    	/* Se valida el agente recibido */
		utilService.validaAgente(agenteVO, false);
    	
        StringBuffer mensaje = new StringBuffer(" No se encontraron registros coincidentes ");
        if (emisionVO != null && StringUtils.isNotBlank(emisionVO.getIdTipoValor())) {
            mensaje.append(" con el tipo valor = [" + emisionVO.getIdTipoValor() + "]");
        }

        TPosicionNombradaParamsPersistence tPosicionNombradaParamsPersistence = 
            UtilsVOCatalogo.getInstanceTPosicionNombradaParamsPersistence(agenteVO, emisionVO);
        tPosicionNombradaParamsPersistence.setIdBoveda(idBoveda);
        
        PageVO pageVO = 
            posicionNombradaDaliDao.getTPosicionNombradaCapitales(tPosicionNombradaParamsPersistence);
        
        return this.convertPageVOTPosicionNombrada2PaginaVOEmisionVO(pageVO, mensaje.toString());
                    	
    }
    
    /**
     * @see com.indeval.portaldali.middleware.services.modelo.CatalogoService#getListaEmisionesCapitales(com.indeval.portaldali.middleware.services.modelo.AgenteVO, com.indeval.portaldali.middleware.services.modelo.EmisionVO)
     */
    public EmisionVO[] getListaEmisionesCapitales(AgenteVO agenteVO, EmisionVO emisionVO, 
            BigInteger idBoveda) throws BusinessException{
    
        logger.info("Entrando a CatalogoServiceImpl.getListaEmisionesCapitales()");
        
        return this.getArrayEmisionVO(this.getListaEmisionesCapitales(agenteVO, emisionVO, idBoveda,
                new PaginaVO()));
        
    }
    
    /**
     * @see com.indeval.portaldali.middleware.services.modelo.CatalogoService#getListaEmisionesCapitales(com.indeval.portaldali.middleware.services.modelo.AgenteVO, com.indeval.portaldali.middleware.services.modelo.EmisionVO, com.indeval.portaldali.middleware.services.modelo.PaginaVO)
     */
    public PaginaVO getListaEmisionesCapitales(AgenteVO agenteVO, EmisionVO emisionVO, 
            BigInteger idBoveda, PaginaVO paginaVO) throws BusinessException {

        logger.info("Entrando a CatalogoServiceImpl.getListaEmisionesCapitales()");
        
        /* Se valida el agente recibido */
        utilService.validaAgente(agenteVO, false);
        
        StringBuffer mensaje = new StringBuffer(" No se encontraron registros coincidentes ");
        if (emisionVO != null && StringUtils.isNotBlank(emisionVO.getIdTipoValor())) {
            mensaje.append(" con el tipo valor = [" + emisionVO.getIdTipoValor() + "]");
        }
        
        TPosicionNombradaParamsPersistence tPosicionNombradaParamsPersistence = 
            UtilsVOCatalogo.getInstanceTPosicionNombradaParamsPersistence(agenteVO, emisionVO);
        tPosicionNombradaParamsPersistence.setIdBoveda(idBoveda);

        /* Se obtiene y se valida la pagina de resultados */
        PageVO pageVO = posicionNombradaDaliDao.getTPosicionNombrada(tPosicionNombradaParamsPersistence);
        
        return this.convertPageVOTPosicionNombrada2PaginaVOEmisionVO(pageVO, mensaje.toString());

    }    

//    /**
//     * TODO tiene dependencia con historica..edoctatr
//     * @see com.indeval.portaldali.middleware.services.modelo.CatalogoService#getEmisionesEstadoCuentaUnico(com.indeval.portaldali.middleware.services.mercadocapitales.EstadoCuentaUnicoParams)
//     */
//    public EmisionVO[] getEmisionesEstadoCuentaUnico(EstadoCuentaUnicoParams params)
//            throws BusinessException{
//
//    	log.info("Entrando a CatalogoServiceImpl.getEmisionesEstadoCuentaUnico()");
//    	
//    	// Se valida el objeto de parametros
//        utilService.validaDTONoNulo(params, "");
//        params.validaParams();
//        utilService.validarClaveTipo(params.getAgenteFirmado().getCuenta(), " cuenta ");
//
//        // hacemos que la hora de la fecha de movimiento, sea la hora cero
//        params.setFechaMovimiento(dateUtilsDao.getFechaHoraCero(params.getFechaMovimiento()));
//
//        // Realizamos la consulta de emisiones para cada agente
//        List emisionesAgente = emisionesDao.getListaEmisionesByInstitucionEdoCtaUnico(
//        		UtilsDaliVO.parseAgenteVO2AgentePK(params.getAgenteFirmado()), 
//        		params.getFechaMovimiento(),
//                UtilsDaliVO.parseEmisionVO2EmisionPK(params.getEmisionVO()));
//
//        // Se valida la lista de emisiones recuperada
//        if (emisionesAgente.isEmpty()) {
//            throw new BusinessException(errorResolver.getMessage("J9999"));
//        }
//
//        // Recuperamos las emisiones de la cuenta recibida
//        EmisionVO[] emisionVOs = null;
//        emisionVOs = new EmisionVO[emisionesAgente.size()];
//        emisionVOs = this.arrayObject2ArrayEmisionVO(emisionesAgente);
//
//        return emisionVOs;
//    	
//    }
    
//    /**
//     * @param emisionesAgente
//     * @return EmisionVO[]
//     */
//    private EmisionVO[] arrayObject2ArrayEmisionVO(List emisionesAgente) {
//
//		log.info("Entrando a CatalogoServiceImpl.arrayObject2ArrayEmisionVO()");
//		
//        EmisionVO[] emisionVOs = new EmisionVO[emisionesAgente.size()];
//        if (emisionesAgente != null) {
//            for (int j = 0; j < emisionesAgente.size(); j++) {
//                Object[] emision = (Object[]) emisionesAgente.get(j);
//                EmisionVO emisionVO = new EmisionVO();
//                emisionVO.setIdTipoValor((String) emision[0]);
//                emisionVO.setEmisora((String) emision[1]);
//                emisionVO.setSerie((String) emision[2]);
//                emisionVO.setCupon((String) emision[3]);
//                emisionVOs[j] = emisionVO;
//            }
//        }
//        return emisionVOs;
//    }

//    /**
//     * @see com.indeval.portaldali.middleware.services.modelo.CatalogoService#getListaEmisionesEstadoCuentaSociedadesInversion(com.indeval.portaldali.middleware.services.modelo.AgenteVO, com.indeval.portaldali.middleware.services.modelo.EmisionVO)
//     */
//    public PaginaVO getListaEmisionesEstadoCuentaSociedadesInversion(AgenteVO agenteVO, 
//    		EmisionVO emisionVO) throws BusinessException{
//
//    	log.info("Entrando a " +
//    			"CatalogoServiceImpl.getListaEmisionesEstadoCuentaSociedadesInversion()");
//    	
//    	/* Se valida el agente recibido */
//		utilService.validaAgente(agenteVO, false);
//        
//        PageVO resultadoPersistencia = valorDao.getListaEmisionesEstadoCuentaSociedadesInversion(
//                UtilsDaliVO.getAgentePK(agenteVO), UtilsDaliVO.getEmisionPK(emisionVO), 
//                new PageVO(REGISTROS_PREDETERMINADOS_X_PAGINA));
//
//        if(resultadoPersistencia == null || resultadoPersistencia.getRegistros() == null || 
//        		resultadoPersistencia.getRegistros().isEmpty()){
//        	throw new BusinessException(errorResolver.getMessage("J9999"));	
//        }
//        
//        List<EmisionVO> registrosMiddleware = new ArrayList<EmisionVO>();
//        for (int i = 0; i < resultadoPersistencia.getRegistros().size(); i++) {
//            Object[] element = (Object[]) resultadoPersistencia.getRegistros().get(i);
//            EmisionVO emisionVO2 = new EmisionVO();
//            emisionVO2.setIdTipoValor((String) element[0]);
//            emisionVO2.setEmisora((String) element[1]);
//            registrosMiddleware.add(emisionVO2);
//        }
//
//        PaginaVO resultadoMiddleware = UtilsDaliVO.getPaginaVO(resultadoPersistencia);
//        resultadoMiddleware.setRegistros(registrosMiddleware);
//
//        return resultadoMiddleware;
//    	
//    }

//    /**
//     * TODO tiene dependencia con historica..edoctatr
//     * @see com.indeval.portaldali.middleware.services.modelo.CatalogoService#getListaEmisionesEstadoCuentaUnico(com.indeval.portaldali.middleware.services.modelo.AgenteVO, com.indeval.portaldali.middleware.services.modelo.EmisionVO, java.util.Date)
//     */
//    public PaginaVO getListaEmisionesEstadoCuentaUnico(AgenteVO agenteVO, EmisionVO emisionVO,
//            Date fechaMovimiento) throws BusinessException{
//
//    	log.info("Entrando a CatalogoServiceImpl.getListaEmisionesEstadoCuentaUnico()");
//    	
//    	utilService.validaDTONoNulo(agenteVO, " agente ");
//    	agenteVO.tieneClaveValida();
//
//        PageVO totalRegistros = (PageVO) historicoEstadoCuentaTraspasoDao
//                .getListaEmisionesHistoricoEstadoCuentaCapitales(UtilsDaliVO
//                        .parseAgenteVO2AgentePK(agenteVO), UtilsDaliVO
//                        .parseEmisionVO2EmisionPK(emisionVO), fechaMovimiento, 
//                        new PageVO(REGISTROS_PREDETERMINADOS_X_PAGINA));
//
//        List registrosPersistencia = totalRegistros.getRegistros();
//        EmisionVO item = null;
//        List registrosMiddleware = new ArrayList();
//        for (int i = 0; i < registrosPersistencia.size(); i++) {
//            Object[] objetoPersistencia = (Object[]) registrosPersistencia.get(i);
//            item = new EmisionVO();
//            item.setIdTipoValor((String) objetoPersistencia[0]);
//            item.setEmisora((String) objetoPersistencia[1]);
//            item.setSerie((String) objetoPersistencia[2]);
//            item.setCupon((String) objetoPersistencia[3]);
//            registrosMiddleware.add(item);
//        }
//
//        PaginaVO result = UtilsDaliVO.getPaginaVO(totalRegistros);
//        result.setRegistros(registrosMiddleware);
//
//        return result;
//    	
//    }
    
//    /**
//     * TODO tiene dependencia con bdcamara..kedo_valores
//     * @see com.indeval.portaldali.middleware.services.modelo.CatalogoService#getEmisionesInEstadoValores(java.lang.String, java.lang.String, java.lang.String, com.indeval.portaldali.middleware.services.modelo.PaginaVO)
//     */
//    public PaginaVO getEmisionesInEstadoValores(String claveAL, String claveANL, 
//            String tipoGarantia, PaginaVO paginaVO) throws BusinessException{
//
//    	log.info("Entrando a CatalogoServiceImpl.getEmisionesInEstadoValores()");
//    	
//    	// Se validan los parametros recibidos
//        utilService.validarClaveTipo(claveAL, "Clave AgentePersistence Liquidador");
//        utilService.validarClaveTipo(claveANL, "Clave AgentePersistence No Liquidador");
//        utilService.validarClaveTipo(tipoGarantia, "Tipo Garantia");
//        
//        //Se recupera la pagina de registros
//        paginaVO = UtilsDaliVO.getPaginaVO(
//                getEstadoValoresDao().getEmisionesInEstadoValores(
//                        claveAL, claveANL, tipoGarantia, UtilsDaliVO.getPageVO(paginaVO)));
//        
//        if(paginaVO == null || paginaVO.getRegistros() == null || 
//        		paginaVO.getRegistros().isEmpty()){
//            throw new BusinessException(errorResolver.getMessage("J9999"));
//        }
//        
//        //Se construyen los registros de EmisionVO
//        paginaVO.setRegistros(parseEstadoValores2EmisionVO(paginaVO.getRegistros()));
//        
//        return paginaVO;
//    	
//    }
    
//    /**
//     * Genera una lista de EmisionesVO a partir de la lista de EstadoValores recibida 
//     * @param listaEstadoValores
//     * @return List
//     */
//    private List parseEstadoValores2EmisionVO(List listaEstadoValores) throws BusinessException {
//        
//        log.info("Entrando a GarantiasServiceImpl.parseEstadoValores2EmisionVO()");
//        
//        if(listaEstadoValores == null) {
//            throw new BusinessException(errorResolver.getMessage("J9999")); 
//        }
//        
//        List<EmisionVO> listaEmisionesVO = new ArrayList<EmisionVO>();
//        
//        for (Iterator iterador = listaEstadoValores.iterator(); iterador.hasNext();) {
//            EstadoValores elementoValores = (EstadoValores) iterador.next();
//            EmisionVO emisionVO = UtilsDaliVO.getEmisionVO(elementoValores.getEmision());
//            emisionVO.setSaldoDisponible(elementoValores.getNoTitulos());
//            
//            Emisora emisora = elementoValores.getEmisora();
//            if(emisora != null){
//                BigDecimal precioVector = UtilsDaliVO.BIG_DECIMAL_ZERO;
//                Set vectorPrecios = elementoValores.getEmisora().getVectorPrecios();
//                for (Iterator iter = vectorPrecios.iterator(); iter.hasNext();) {
//                    VectorPrecio vectorPrecio = (VectorPrecio) iter.next();
//                    if(Constantes.GE_ACTUAL_S.equalsIgnoreCase(vectorPrecio.getGeActual())
//                            && vectorPrecio.getVectorPrecioPK().getCvCupon().trim().equals(
//                                    elementoValores.getEstadoValoresPK().getCvCupon())){
//                        precioVector = vectorPrecio.getMtVector();
//                        break;
//                    }
//                }
//                log.debug("Precio Vector = [" + precioVector + "]");
//                
//                BigDecimal geGarantiaPorcPena = emisora.getGeGarantiaPorcPena();
//                log.debug("geGarantiaPorcPena = [" + geGarantiaPorcPena + "]");
//                if(geGarantiaPorcPena != null && 
//                        UtilsDaliVO.BIG_DECIMAL_ZERO.compareTo(geGarantiaPorcPena) <= 0){
//                    log.debug("Calculo del castigo");
//                    BigDecimal castigo = UtilsDaliVO.BIG_DECIMAL_UNO.subtract(
//                            (geGarantiaPorcPena.divide(
//                                    UtilsDaliVO.BIG_DECIMAL_ZIEN, Constantes.ESCALA_BIGDECIMAL, 
//                                    BigDecimal.ROUND_HALF_EVEN)));
//                    log.debug("Castigo = [" + castigo + "]");
//                    precioVector = precioVector.multiply(castigo);
//                }
//                
//                log.debug("Precio Vector Castigado = [" + precioVector + "]");
//                emisionVO.setPrecioVector(precioVector);
//            }
//            log.debug("Emisora : [" + elementoValores.getEmisora() + "]");
//            listaEmisionesVO.add(emisionVO);
//        }
//        return listaEmisionesVO;
//    }
    
//    /**
//     * @see com.indeval.portaldali.middleware.services.modelo.CatalogoService#getEmisionesGarantias(java.lang.String, java.lang.String)
//     */
//    public List getEmisionesGarantias(String claveAL, String cuenta) throws BusinessException{
//
//    	log.info("Entrando a CatalogoServiceImpl.getEmisionesGarantias()");
//    	
//        utilService.validarClaveTipo(claveAL, "Clave AgentePersistence");
//        utilService.validarClaveTipo(cuenta, "Cuenta");
//        
//        List listaEmisiones = emisionesDaoJdbc.getEmisionesGarantias(claveAL, cuenta);
//        
//        if(listaEmisiones == null || listaEmisiones.isEmpty()) {
//            throw new BusinessException(errorResolver.getMessage("J9999"));
//        }
//        
//        return UtilsDaliVO.emisionesVOPersistence2EmisionesVO(listaEmisiones);
//    	
//    }
    
//    /**
//     * @see com.indeval.portaldali.middleware.services.modelo.CatalogoService#getListaEmisiones(com.indeval.portaldali.middleware.services.modelo.AgenteVO, com.indeval.portaldali.middleware.services.modelo.EmisionVO, boolean, boolean)
//     */
//    public EmisionVO[] getListaEmisiones(AgenteVO agenteVO, EmisionVO emisionVO,
//            boolean enCorte, boolean historico) throws BusinessException{
//
//    	log.info("Entrando a CatalogoServiceImpl.getListaEmisiones()");
//    	
//    	/* Se valida el agente recibido */
//		utilService.validaAgente(agenteVO, false);
//        
//        StringBuffer mensaje = new StringBuffer(" No se encontraron registros coincidentes ");
//        if (emisionVO != null && StringUtils.isNotBlank(emisionVO.getIdTipoValor())) {
//            mensaje.append(" con el tipo valor = [" + emisionVO.getIdTipoValor() + "]");
//        }
//
//        List listaEmisionesPersistence = null;
//        if (!historico && !enCorte) {
//            listaEmisionesPersistence = emisionesDaoJdbc.getEmisionesDivisionInternacional(UtilsDaliVO
//                    .parseAgenteVO2AgentePK(agenteVO), UtilsDaliVO.parseEmisionVO2Emision(emisionVO));
//        }
//        else {
//            listaEmisionesPersistence = emisionesDaoJdbc.getEmisionesDivisionInternacional(UtilsDaliVO
//                    .parseAgenteVO2AgentePK(agenteVO), UtilsDaliVO.parseEmisionVO2Emision(emisionVO),
//                    enCorte, historico);
//
//        }
//
//        if (listaEmisionesPersistence == null || listaEmisionesPersistence.isEmpty()) {
//            throw new BusinessException(mensaje.toString());
//        }
//
//        List listaEmisiones = UtilsDaliVO.emisionesVOPersistence2EmisionesVO(listaEmisionesPersistence);
//
//        EmisionVO[] emisionVOs = new EmisionVO[listaEmisiones.size()];
//
//        listaEmisiones.toArray(emisionVOs);
//
//        return emisionVOs;
//    	
//    }

//    /**
//     * @see com.indeval.portaldali.middleware.services.modelo.CatalogoService#getListaEmisionesPorEjercicio(com.indeval.portaldali.middleware.services.modelo.EmisionVO, boolean)
//     */
//    public EmisionVO[] getListaEmisionesPorEjercicio(EmisionVO emisionVO,
//            boolean esFija) throws BusinessException{
//
//    	log.info("Entrando a CatalogoServiceImpl.getListaEmisionesPorEjercicio()");
//    	
//    	StringBuffer mensaje = new StringBuffer(" No se encontraron registros coincidentes ");
//        if (emisionVO != null && StringUtils.isNotBlank(emisionVO.getIdTipoValor())) {
//            mensaje.append(" con el tipo valor = [" + emisionVO.getIdTipoValor() + "]");
//        }
//
//        List listaEmisionesPersistence = emisionesDaoJdbc
//                .getEmisionesPorEjercicioDivisionInternacional(UtilsDaliVO.getEmisionPK(emisionVO),
//                        esFija);
//
//        if (listaEmisionesPersistence == null || listaEmisionesPersistence.isEmpty()) {
//            throw new BusinessException(mensaje.toString());
//        }
//
//        List listaEmisiones = UtilsDaliVO.emisionesVOPersistence2EmisionesVO(listaEmisionesPersistence);
//
//        EmisionVO[] emisionVOs = new EmisionVO[listaEmisiones.size()];
//
//        listaEmisiones.toArray(emisionVOs);
//
//        return emisionVOs;
//    	
//    }
    
//    /**
//     * @see com.indeval.portaldali.middleware.services.modelo.CatalogoService#getListaEmisionesFideicomisoArqueo(com.indeval.portaldali.middleware.services.modelo.AgenteVO, java.util.Date)
//     */
//    public EmisionVO[] getListaEmisionesFideicomisoArqueo(AgenteVO agenteVO,
//            Date fechaConsulta) throws BusinessException{
//    	
//    	log.info("Entrando a CatalogoServiceImpl.getListaEmisionesFideicomisoArqueo()");
//
//    	/* Se valida el agente recibido */
//		utilService.validaAgente(agenteVO, false);
//
//        String[] altas = getAltasArque(agenteVO);
//
//        List listaEmisiones = null;
//
//        boolean isHistorico = isHistorico(fechaConsulta);
//
//        if (isHistorico) {
//            listaEmisiones = historicoPosicionDao.getEmisionesHistoricasFideicomiso(altas[0],
//                    altas[1], altas[2], fechaConsulta);
//        }
//        else {
//            listaEmisiones = valorDao.getEmisionesFideicomiso(altas[0], altas[1], altas[2]);
//        }
//
//        if (listaEmisiones == null || listaEmisiones.isEmpty()) {
//            throw new BusinessException(errorResolver.getMessage("J9999"));
//        }
//
//        List<EmisionVO> listaEmisionesVO = new ArrayList<EmisionVO>();
//        for (Iterator iter = listaEmisiones.iterator(); iter.hasNext();) {
//            Object[] element = (Object[]) iter.next();
//            EmisionVO emisionVO = new EmisionVO();
//            emisionVO.setIdTipoValor(element[0].toString().trim());
//            emisionVO.setEmisora(element[1].toString().trim());
//            emisionVO.setSerie(element[2].toString().trim());
//            emisionVO.setCupon(element[3].toString().trim());
//            if (isHistorico) {
//                emisionVO.setUltimoHecho(new BigDecimal(((Double) element[4]).toString()));
//            }
//            else {
//                emisionVO.setUltimoHecho((BigDecimal) element[4]);
//            }
//            emisionVO.setValorNominal((BigDecimal) element[5]);
//            emisionVO.setAlta(element[6].toString().trim());
//            listaEmisionesVO.add(emisionVO);
//        }
//
//        EmisionVO[] emisionVOs = new EmisionVO[listaEmisionesVO.size()];
//
//        return (EmisionVO[]) listaEmisionesVO.toArray(emisionVOs);
//    	
//    }
    
//    /**
//     * Obtiene los parametros para buscar las emisiones del agente, necesarias
//     * para el arqueo.
//     *
//     * @param agenteVO
//     *            AgentePersistence con el cual se relizara la busqueda
//     * @return Los parametros para la busqueda de emisiones.
//     */
//    private String[] getAltasArque(AgenteVO agenteVO) {
//    	
//    	log.info("Entrando a CatalogoServiceImpl.getAltasArque()");
//    	String[] altas = {
//                null, null, null };
//
//        String clave = agenteVO.getClave();
//
//        if (clave.equalsIgnoreCase(Constantes.NAFINSA)) {
//            altas[0] = Constantes.ALTA1NAFINSA;
//            altas[1] = Constantes.ALTA2NAFINSA;
//            altas[2] = Constantes.ALTA3NAFINSA;
//
//        }
//        else if (clave.equalsIgnoreCase(Constantes.BANAMEX)) {
//            altas[0] = Constantes.ALTA1BANAMEX;
//            altas[1] = Constantes.ALTA2BANAMEX;
//            altas[2] = Constantes.ALTA2BANAMEX;
//        }
//        else if (clave.equalsIgnoreCase(Constantes.INBURSA)) {
//            altas[0] = Constantes.ALTA1INBURSA;
//            altas[1] = Constantes.ALTA2INBURSA;
//            altas[2] = Constantes.ALTA2INBURSA;
//        }
//        return altas;
//
//    }
    
//    /**
//     * Verifica si el arqueo que se calcular&aacute; es historico o en linea
//     *
//     * @param fechaConsulta
//     *            Fecha que indica si se requiere el arqueo historico o en
//     *            linea.
//     * @return TRUE en caso de que sea necesario calcular el arqueo historico,
//     *         FALSE en caso contrario
//     */
//    private boolean isHistorico(Date fechaConsulta) {
//    	
//    	log.info("Entrando a CatalogoServiceImpl.isHistorico()");
//    	
//        Date fechaActual = dateUtilsDao.getDateFechaDB();
//        fechaActual = dateUtilsDao.getFechaHoraCero(fechaActual);
//
//        if (fechaConsulta.compareTo(fechaActual) < 0) {
//            return true;
//        }
//        return false;
//    }
    
    /**
     * @see com.indeval.portaldali.middleware.services.modelo.CatalogoService#getEmisoraByTVCustodia(java.lang.String)
     */
    public String getEmisoraByTVCustodia(String tv) throws BusinessException{
    	
    	logger.info("Entrando a CatalogoServiceImpl.getEmisoraByTVCustodia()");

		if (StringUtils.isBlank(tv)) {
			throw new BusinessException(errorResolver.getMessage("J0028", 
					new Object[]{UtilsDaliVO.BLANK}));
		}
        
		List emisiones = posicionNombradaDaliDao.getEmisoraByTV(tv);

		if (emisiones == null || emisiones.size() != 1) {
			return null;
		}
		return (String) emisiones.get(0);
    	
    }

//    /**
//     * TODO tiene dependencia con catalogo..clotes y con catalogo..rinstrumentos
//     * @see com.indeval.portaldali.middleware.services.modelo.CatalogoService#getListaEmisionesStrips(com.indeval.portaldali.middleware.services.modelo.EmisionVO)
//     */
//    public List getListaEmisionesStrips(EmisionVO emisionVO) throws BusinessException{
//    	
//    	log.info("Entrando a CatalogoServiceImpl.getListaEmisionesStrips()");
//
//    	EmisionPK emisionPK = new EmisionPK();
//		if (emisionVO != null) {
//			if (emisionVO.getIdTipoValor() != null)
//				emisionPK.setTv(emisionVO.getIdTipoValor());
//			if (emisionVO.getEmisora() != null)
//				emisionPK.setEmisora(emisionVO.getEmisora());
//			if (emisionVO.getSerie() != null)
//				emisionPK.setSerie(emisionVO.getSerie());
//			if (emisionVO.getCupon() != null)
//				emisionPK.setCupon(emisionVO.getCupon());
//		}
//
//		List resultado = clotesDao.getListaEmisionesStrips(emisionPK);
//		Iterator iter = resultado.iterator();
//		ArrayList<EmisionesStripsVO> listaFinal = new ArrayList<EmisionesStripsVO>();
//
//		while (iter.hasNext()) {
//			CLotes clote = (CLotes) iter.next();
//
//			EmisionesStripsVO emisionesStripsVO = new EmisionesStripsVO();
//			EmisionVO emisionVOtmp = new EmisionVO();
//
//			emisionVOtmp.setIdTipoValor(clote.getClotesPK().getTv());
//			emisionVOtmp.setEmisora(clote.getClotesPK().getEmisora());
//			emisionVOtmp.setSerie(clote.getClotesPK().getSerie());
//			emisionVOtmp.setCupon(clote.getClotesPK().getCupon());
//
//			emisionesStripsVO.setEmision(emisionVOtmp);
//			emisionesStripsVO.setLoteMinimo(new Integer(clote.getLote()
//					.toString()));
//			listaFinal.add(emisionesStripsVO);
//		}
//		return listaFinal;
//    	
//    }
    
//    /**
//     * TODO tiene dependencia con bdcamara..cemisora
//     * TODO retirar parametros recibidos no utilizados
//     * 
//     * @see com.indeval.portaldali.middleware.services.modelo.CatalogoService#getListaEmisiones(com.indeval.portaldali.middleware.services.modelo.EmisionVO, com.indeval.portaldali.middleware.services.modelo.AgenteVO, boolean)
//     */
//    public EmisionVO[] getListaEmisiones(EmisionVO emisionVO, AgenteVO agenteVO, boolean isCapitales)
//            throws BusinessException{
//    	
//    	log.info("Entrando a CatalogoServiceImpl.getListaEmisiones()");
//
//    	/* Se valida la emisionVO recibida */
//    	utilService.validaDTONoNulo(emisionVO, " emision ");
//		
//		List emisoras = emisoraDao.getByEmision(UtilsDaliVO.getEmisionPK(emisionVO));
//		if (emisoras == null || emisoras.isEmpty()) {
//			throw new BusinessException(errorResolver.getMessage("J9999"));
//		}
//
//		EmisionVO[] arregloEmisionesVO = new EmisionVO[emisoras.size()];
//		int i = 0;
//		Iterator iter = emisoras.iterator();
//		while(iter.hasNext()) {
//			Emisora emisora = (Emisora) iter.next();
//			EmisionVO emisionVO2 = new EmisionVO();
//			emisionVO2.setIdTipoValor(emisora.getEmisoraPk().getCvTipoValor());
//			emisionVO2.setEmisora(emisora.getEmisoraPk().getCvEmisora());
//			emisionVO2.setSerie(emisora.getEmisoraPk().getCvSerie());
//			emisionVO2.setCupon(emisora.getGeCuponVigente());
//			emisionVO2.setIsin(emisora.getCvIsin());
//			arregloEmisionesVO[i] = emisionVO2;
//			i++;
//		}
//		return  arregloEmisionesVO;
//    	
//    }
    
    /**
     * @see com.indeval.portaldali.middleware.services.modelo.CatalogoService#getListaEmisionesControlPosicionValores(com.indeval.portaldali.middleware.services.modelo.AgenteVO, com.indeval.portaldali.middleware.services.modelo.EmisionVO)
     */
    public EmisionVO[] getListaEmisionesControlPosicionValores(AgenteVO agenteVO, EmisionVO emisionVO,
            BigInteger idBoveda) throws BusinessException {
    	
    	logger.info("Entrando a CatalogoServiceImpl.getListaEmisionesControlPosicionValores()");
        
        PaginaVO paginaVO = getListaEmisionesControlPosicionValores(agenteVO, emisionVO, idBoveda, 
                new PaginaVO());
        
        return this.getArrayEmisionVO(paginaVO);
    	
    }
    
    /**
     * @see com.indeval.portaldali.middleware.services.modelo.CatalogoService#getListaEmisionesControlPosicionValores(com.indeval.portaldali.middleware.services.modelo.AgenteVO, com.indeval.portaldali.middleware.services.modelo.EmisionVO, com.indeval.portaldali.middleware.services.modelo.PaginaVO)
     */
    public PaginaVO getListaEmisionesControlPosicionValores(AgenteVO agenteVO, EmisionVO emisionVO, 
            BigInteger idBoveda, PaginaVO paginaVO) throws BusinessException {
        
        logger.info("Entrando a CatalogoServiceImpl.getListaEmisionesControlPosicionValores()");

        /* Se valida el agente recibido */
        utilService.validaAgente(agenteVO, false);
        
        StringBuffer mensaje = new StringBuffer(
                " No se encontraron registros coincidentes ");

        if (emisionVO != null
                && StringUtils.isNotBlank(emisionVO.getIdTipoValor())) {
            mensaje.append(" con el tipo valor = ["
                    + emisionVO.getIdTipoValor() + "]");
        }

        TPosicionNombradaParamsPersistence tPosicionNombradaParamsPersistence = 
            UtilsVOCatalogo.getInstanceTPosicionNombradaParamsPersistence(agenteVO, emisionVO);
        tPosicionNombradaParamsPersistence.setIdBoveda(idBoveda);
        
        PageVO pageVO = 
            posicionNombradaDaliDao.getTPosicionNombradaCapitales(tPosicionNombradaParamsPersistence);
        
        return this.convertPageVOTPosicionNombrada2PaginaVOEmisionVO(pageVO, mensaje.toString());
        
    }
    
    /**
     * Convierte un PageVO de PosicionNombrada en una PaginaVO de EmisionVO
     * 
     * @param pageVO
     * @return PaginaVO
     * @throws BusinessException
     */
    private PaginaVO convertPageVOTPosicionNombrada2PaginaVOEmisionVO(PageVO pageVO, String mensaje) throws BusinessException{
        
        logger.info("Entrando a CatalogoServiceImpl.convertPageVOTPosicionNombrada2PaginaVOEmisionVO()");
        
        //UtilsDaliVO.validaPage(pageVO, mensaje);
        
        pageVO.setRegistros(UtilsVOCatalogo.getListaEmisionVO(pageVO.getRegistros(), 
                dateUtilsDao.getDateFechaDB()));
        
        return UtilsDaliVO.getPaginaVO(pageVO);
        
    }
    
    /**
     * Extrae la lista de EmisionVO contenida en un PaginaVO y lo convierte en un Array
     *  
     * @param paginaVO
     * @return EmisionVO[]
     * @throws BusinessException
     */
    private EmisionVO[] getArrayEmisionVO(PaginaVO paginaVO) throws BusinessException {
        
        logger.info("Entrando a CatalogoServiceImpl.getArrayEmisionVO()");
        
        utilService.validaPagina(paginaVO, null);

        // Se construye el arreglo de emisiones y se llena con los objetos de la lista
        EmisionVO[] emisionesVO = (EmisionVO[]) paginaVO.getRegistros().toArray(
                new EmisionVO[paginaVO.getRegistros().size()]);

        return emisionesVO;
        
    }
    
//	/**
//	 * @param emisionesDao the emisionesDao to set
//	 */
//	public void setEmisionesDao(EmisionesDao emisionesDao) {
//		this.emisionesDao = emisionesDao;
//	}

	/**
	 * @param errorResolver the errorResolver to set
	 */
	public void setErrorResolver(MessageResolver errorResolver) {
		this.errorResolver = errorResolver;
	}

	/**
	 * @param utilService the utilService to set
	 */
	public void setUtilService(UtilServices utilService) {
		this.utilService = utilService;
	}

	/**
     * @param instrumentoDaliDao the instrumentoDaliDao to set
     */
    public void setInstrumentoDao(InstrumentoDaliDao instrumentoDaliDao) {
        this.instrumentoDaliDao = instrumentoDaliDao;
    }

//    /**
//	 * @param emisoraDao the emisoraDao to set
//	 */
//	public void setEmisoraDao(EmisoraDao emisoraDao) {
//		this.emisoraDao = emisoraDao;
//	}
//
//	/**
//	 * @return the estadoValoresDao
//	 */
//	public EstadoValoresDao getEstadoValoresDao() {
//		return estadoValoresDao;
//	}
//
//	/**
//	 * @param estadoValoresDao the estadoValoresDao to set
//	 */
//	public void setEstadoValoresDao(EstadoValoresDao estadoValoresDao) {
//		this.estadoValoresDao = estadoValoresDao;
//	}
//
//	/**
//	 * @param clotesDao the clotesDao to set
//	 */
//	public void setClotesDao(CLotesDao clotesDao) {
//		this.clotesDao = clotesDao;
//	}
//
//	/**
//	 * @param historicoPosicionDao the historicoPosicionDao to set
//	 */
//	public void setHistoricoPosicionDao(HistoricoPosicionDao historicoPosicionDao) {
//		this.historicoPosicionDao = historicoPosicionDao;
//	}
//
//	/**
//	 * @param traspasoDepositosRetirosDao the traspasoDepositosRetirosDao to set
//	 */
//	public void setTraspasoDepositosRetirosDao(
//			TraspasoDao traspasoDepositosRetirosDao) {
//		this.traspasoDepositosRetirosDao = traspasoDepositosRetirosDao;
//	}
//
//	/**
//	 * @param valorDao the valorDao to set
//	 */
//	public void setValorDao(ValorDao valorDao) {
//		this.valorDao = valorDao;
//	}
//
//	/**
//	 * @param emisionesDaoJdbc the emisionesDaoJdbc to set
//	 */
//	public void setEmisionesDaoJdbc(EmisionesDaoJDBC emisionesDaoJdbc) {
//		this.emisionesDaoJdbc = emisionesDaoJdbc;
//	}
//
//	/**
//	 * @param historicoEstadoCuentaTraspasoDao the historicoEstadoCuentaTraspasoDao to set
//	 */
//	public void setHistoricoEstadoCuentaTraspasoDao(
//			HistoricoEstadoCuentaTraspasoDao historicoEstadoCuentaTraspasoDao) {
//		this.historicoEstadoCuentaTraspasoDao = historicoEstadoCuentaTraspasoDao;
//	}
//
//	/**
//	 * @param tranMercDinDao the tranMercDinDao to set
//	 */
//	public void setTranMercDinDao(TransaccionMercadoDineroDao tranMercDinDao) {
//		this.tranMercDinDao = tranMercDinDao;
//	}

    /**
     * @param posicionControladaDaliDao the tPosicionControladaDao to set
     */
    public void setPosicionControladaDao(PosicionControladaDaliDao posicionControladaDaliDao) {
        this.posicionControladaDaliDao = posicionControladaDaliDao;
    }

    /**
     * @param posicionNombradaDaliDao the tPosicionNombradaDao to set
     */
    public void setPosicionNombradaDao(PosicionNombradaDaliDao posicionNombradaDaliDao) {
        this.posicionNombradaDaliDao = posicionNombradaDaliDao;
    }

    /**
     * @param dateUtilsDao the dateUtilsDao to set
     */
    public void setDateUtilsDao(DateUtilsDao dateUtilsDao) {
        this.dateUtilsDao = dateUtilsDao;
    }

	private static final Integer SUMA_POS;
	private static final Integer TIPO_VALOR;
	private static final Integer EMISORA;
	private static final Integer SERIE;
	private static final Integer NOMBRE_BVD_POS;
	// private static final Integer NOMBRE_BVD_EMI;
	private static final Integer CUPON;

	static {
		SUMA_POS = 0;
		TIPO_VALOR = 1;
		EMISORA = 2;
		SERIE = 3;
		NOMBRE_BVD_POS = 4;
		// NOMBRE_BVD_EMI = 5;
		CUPON = 5;
	}

}
