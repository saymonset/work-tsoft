/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.services.divisioninternacional;


import java.io.Reader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.hibernate.lob.ClobImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bursatec.util.message.MessageResolver;
import com.indeval.portaldali.middleware.services.util.ConvertBO2VO;
import com.indeval.portaldali.middleware.services.util.DateUtilService;
import com.indeval.portaldali.middleware.services.util.ValidatorService;
import com.indeval.portaldali.middleware.servicios.modelo.BusinessDataException;
import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.EmisionVO;
import com.indeval.portaldali.middleware.servicios.util.UtilsVO;
import com.indeval.portaldali.persistence.dao.common.BovedaDao;
import com.indeval.portaldali.persistence.dao.common.CuentaNombradaDao;
import com.indeval.portaldali.persistence.dao.common.DivisaDao;
import com.indeval.portaldali.persistence.dao.common.EmisionDao;
import com.indeval.portaldali.persistence.dao.common.PosicionNombradaDao;
import com.indeval.portaldali.persistence.modelo.Boveda;
import com.indeval.portaldali.persistence.modelo.CuentaNombrada;
import com.indeval.portaldali.persistence.modelo.Divisa;
import com.indeval.portaldali.persistence.modelo.Emision;
import com.indeval.portaldali.persistence.modelo.PosicionNombrada;
import com.indeval.portaldali.persistence.util.DateUtils;
import com.indeval.portalinternacional.middleware.services.util.FactoryDivInt;
import com.indeval.portalinternacional.middleware.services.util.UtilDivIntService;
import com.indeval.portalinternacional.middleware.services.validador.ValidatorDivIntService;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.CatBic;
import com.indeval.portalinternacional.middleware.servicios.modelo.FileTransfer;
import com.indeval.portalinternacional.middleware.servicios.modelo.OperacionSic;
import com.indeval.portalinternacional.middleware.servicios.modelo.SicDetalle;
import com.indeval.portalinternacional.middleware.servicios.modelo.SicEmision;
import com.indeval.portalinternacional.middleware.servicios.vo.CampoArchivoVO;
import com.indeval.portalinternacional.middleware.servicios.vo.CamposDivIntVO;
import com.indeval.portalinternacional.middleware.servicios.vo.FileTransferVO;
import com.indeval.portalinternacional.middleware.servicios.vo.RegistroProcesadoVO;
import com.indeval.portalinternacional.middleware.servicios.vo.ResumenVO;
import com.indeval.portalinternacional.middleware.servicios.vo.TotalesProcesoVO;
import com.indeval.portalinternacional.middleware.servicios.vo.ValidaNegocioVO;
import com.indeval.portalinternacional.persistence.dao.CatBicDao;
import com.indeval.portalinternacional.persistence.dao.FileTransferDao;
import com.indeval.portalinternacional.persistence.dao.SicDetalleDao;
import com.indeval.portalinternacional.persistence.dao.SicEmisionDao;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 * 
 */
@SuppressWarnings({"unchecked"})
public class FileTransferServiceImpl_backup implements FileTransferService, Constantes {
	
	/** Constante para omitir la obtenci&oacute;n del campo Boveda del viejo Layout */
	private static final int POSICIONES_CAMPO_BOVEDA = 8;

	/** Define el Tiempo M&aacute;ximo de Ejecuci&oacute;n */
	private static final int TIEMPO_MAXIMO_DE_EJECUCION = 10000;

	/** Para el registro de log de esta clase */
	private static final Logger log = LoggerFactory.getLogger(FileTransferServiceImpl_backup.class);
	
	public static enum VALORES_VALIDOS {BOVEDAS, CUENTAS_TRASPASANTE, CUSTODIOS, DEPOSITANTES, 
		DIVISAS, EMISIONES, SIC_EMISION, FECHA_ACTUAL};

    /** Bean de acceso a los servicios de DateUtilService */
    private DateUtilService dateUtilService;
    
    /** List Detalle custodio */
//    private List detalleCustodio;
    
    /** Bean de acceso a BovedaDao */
    private BovedaDao bovedaDao;
    
    /** Bean de acceso a CatBicDao */
    private CatBicDao catBicDao;
    
    /** List Detalle cuentaNombradaDao */
    private CuentaNombradaDao cuentaNombradaDao;
    
    /** Bean de acceso a DivisaDao */
    private DivisaDao divisaDao;
    
    /** Bean de acceso a DivisionInternacionalService  */
    private DivisionInternacionalService divisionInternacionalService;
    
    /** Bean de acceso a EmisionDao */
    private EmisionDao emisionDao;
    
	/** Resolvedor de Mensajes */
	private MessageResolver errorResolver;
	
    /** Bean de acceso a FileTransferDao */
    private FileTransferDao fileTransferDao;
	
	/** Bean de acceso a PosicionNombradaDao */
	private PosicionNombradaDao posicionNombradaDao;
	
	/** Bean de acceso a SicDetalleDao */
	private SicDetalleDao sicDetalleDao;
	
	/** Bean de acceso a SicEmisionDao */
	private SicEmisionDao sicEmisionDao;
	
	/** Bean de acceso a UtilDivIntService */
	private UtilDivIntService utilDivIntService;
    
    /** Bean de acceso a validatorDivIntService */
    private ValidatorDivIntService validatorDivIntService;
    
	/** Bean de acceso a validatorDivIntService */
	private ValidatorService validatorService;
	
	/**
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.FileTransferService#almacenaInformacion(com.indeval.portalinternacional.middleware.servicios.vo.FileTransferVO)
	 */
	public Integer almacenaInformacion(FileTransferVO fileTransferVO)
			throws BusinessException {
		
		log.info("Entrando a FileTransferServiceImpl.almacenaInformacion()");
		
		if (fileTransferDao == null) {
			throw new BusinessException(errorResolver.getMessage("J0015"));
		}
		
		/* Valida parametros */
		validatorService.validaDTONoNulo(fileTransferVO, " FileTransferVO ");
		validatorService.validaAgente(fileTransferVO.getAgenteFirmado(), false);		
		validatorService.validarClaveTipo(fileTransferVO.getTipoProceso(), " tipo de proceso ");

		if (fileTransferVO.getInformacionArchivo() == null || fileTransferVO.getInformacionArchivo().length <= 0) {
			throw new BusinessException(errorResolver.getMessage("J0047",
					new Object[] { "informacionArchivo" }));
		}
		if (fileTransferVO.getOffset() >= fileTransferVO.getInformacionArchivo().length) {
			throw new BusinessException(errorResolver.getMessage("J0045", 
					new Object[] { Integer.toString(fileTransferVO.getInformacionArchivo().length) }));
		}

		/* Borra la informacion antes de almacenarla */
		if (fileTransferVO.getOffset() == 0) {
			this.cancelaProceso(fileTransferVO);
		}

		/* Se arma el mapa para almacenar los parametros ya validados */
		Map parametrosValidos = this.actualizaParametrosValidos(null, null);
		
		/* Se recupera la fecha del dia con horas, minutos, segundo en ceros  y se coloca en el Map */
		Date fechaActual = dateUtilService.getCurrentDate();
		parametrosValidos.put(VALORES_VALIDOS.FECHA_ACTUAL, fechaActual);
		
		/* Realiza la lectura y almacenamiento de registros */
		long inicioProceso = Calendar.getInstance().getTimeInMillis();
		int ultimoRegistro = 0;
		for (int i = fileTransferVO.getOffset(); i < fileTransferVO.getInformacionArchivo().length; i++) {
			
			FileTransfer fileTransfer = new FileTransfer();

			fileTransfer.setIdInst(fileTransferVO.getAgenteFirmado().getId());
			fileTransfer.setFolioInst(fileTransferVO.getAgenteFirmado().getFolio());
			fileTransfer.setTipoReg(fileTransferVO.getTipoProceso());
			fileTransfer.setConsecReg(BigInteger.valueOf(i + 1));
			
			fileTransfer.setUsuario(fileTransferVO.getNombreUsuario());

			/* Este metodo invoca a rellenaCamposNotNull() en caso de error para rellenar los campos 
			 * Not Null y asi evitar un SQLException al momento de efectuar el save */
			this.parseaInformacion(fileTransferVO.getAgenteFirmado(), fileTransfer,
					fileTransferVO.getInformacionArchivo()[i].toString(), parametrosValidos);

			fileTransferDao.save(fileTransfer);

			ultimoRegistro = i;

			long finalProceso = Calendar.getInstance().getTimeInMillis();
			if ((finalProceso - inicioProceso) > TIEMPO_MAXIMO_DE_EJECUCION) {
				/* Interrumpe el proceso en el tiempo maximo de ejecucion para evitar el Timed Out de la conexion */
				break;
			}
			
		}

		/* En el caso de que se concluya el procesamiento de la informacion del archivo, se retorna -1 
		 * para indicar a la pantalla el fin del proceso */
		if ((ultimoRegistro + 1) == fileTransferVO.getInformacionArchivo().length) {
			return -1;
		}

		return ultimoRegistro + 1;

	}
	
	 /**
     * Parsea la informacion para almacenaInformacion()
     * @param agenteFirmado
	 * @param fileTransfer
	 * @param informacion
	 * @param parametrosValidos
     * @throws BusinessDataException
     * @throws BusinessException
     */
    private void parseaInformacion(AgenteVO agenteFirmado, FileTransfer fileTransfer,
            String informacion, Map parametrosValidos) throws BusinessException {
    	
        log.info("Entrando al metodo parseaInformacion()");
        if (informacion == null) {
            throw new BusinessException(errorResolver.getMessage("J0048", (Object)"listaFileTransfer"));
        }

        boolean error = false;
        StringBuffer msgError = new StringBuffer();
        StringBuffer numCampoError = new StringBuffer();
        String cadenaErrorFormato = errorResolver.getMessage("J0051");

        int tam = informacion.length();
        /* Obtiene los campos correspondientes al layout */
        String[] campos = this.camposTraspasosDivInt(informacion);

        if (campos == null) {
            msgError.append(cadenaErrorFormato);
            msgError.append("en la estructura del registro");
            msgError.append(GUION);
            error = true;
        }
        else {

            AgenteVO agenteVendedor = new AgenteVO();
            EmisionVO emisionVO = new EmisionVO();
            String cuentaRecep = CERO_STRING;

            /* Obtiene el mapa de indices (true) correspondiente al layout */
            Map<Object, Object> indice = FactoryDivInt.indicesNombresTraspasosDivInt(true);
            
    		/* Se ejecutan validaciones de formato */
            if (validatorDivIntService.validaFormato(
            		indice, campos, msgError, numCampoError, tam)) {
            	
           	    /* Se construye el objeto de ValidaNegocioVO */
                ValidaNegocioVO validaNegocioVO = 
                	new ValidaNegocioVO(agenteFirmado, indice, campos, msgError, numCampoError, tam,
                			(List) parametrosValidos.get(VALORES_VALIDOS.BOVEDAS),
                			(List) parametrosValidos.get(VALORES_VALIDOS.CUENTAS_TRASPASANTE),
                			(Map) parametrosValidos.get(VALORES_VALIDOS.CUSTODIOS), 
                			(Map) parametrosValidos.get(VALORES_VALIDOS.SIC_EMISION),
                			(List) parametrosValidos.get(VALORES_VALIDOS.DEPOSITANTES),
                			(List) parametrosValidos.get(VALORES_VALIDOS.DIVISAS),
                			(List) parametrosValidos.get(VALORES_VALIDOS.EMISIONES), 
                			(List) parametrosValidos.get(VALORES_VALIDOS.CUENTAS_TRASPASANTE),
                			(Date) parametrosValidos.get(VALORES_VALIDOS.FECHA_ACTUAL));
                
                /* Se determina si se recibio la clave valor y se marca el objeto para indicarlo */
                fileTransfer.setClavevalorProporcionada(
                		validaNegocioVO.getEmisionVO().tienePKValida() ? 0 : 1);
                
                /* Se recupera la emisionVO y se valida, asi como el mercado 
                 * y la posicion en caso de ser requerida */
        		validaNegocioVO = this.recuperaEmision(validaNegocioVO);
        		
        		/* Se obtiene la emisionVO del objeto ValidaNegocioVO */
                emisionVO = validaNegocioVO.getEmisionVO();
                
            	/* Se ejecutan otras validaciones de negocio */
                /* ALERT! El objeto validaNegocioVO puede ser modificado durante las validaciones */
                if (!this.validaNegocio(validaNegocioVO)) {
                    error = true;
                }
                /* cuentaRecep se obtuvo internamente en validaNegocio() */
                agenteVendedor = validaNegocioVO.getAgenteTraspasante();
                cuentaRecep =  validaNegocioVO.getCuentaRecep();
                
                /* Se actualiza el mapa de parametros validos */
                parametrosValidos = actualizaParametrosValidos(parametrosValidos, validaNegocioVO);
                
                if(error) {
                	log.debug("Errores : " + validaNegocioVO.getMsgError());
                }
                
            }
            else {
                error = true;
            }
            
            /* Se llena el objeto a guardar */
            fileTransfer = FactoryDivInt.llenaFileTransferDivInt(fileTransfer, indice, campos, tam, 
            		agenteVendedor, emisionVO, cuentaRecep);
            
        }
        
        /* Se coloca la fecha actual */
        fileTransfer.setFechaReg((Date) parametrosValidos.get(VALORES_VALIDOS.FECHA_ACTUAL));
        
        if (error) {
        	/* Se marca el estado de error */
            fileTransfer.setEstadoReg(ESTADO_ERROR);
            /* Se llenan los campos Not Null cuando falla la obtencion de los campos 
             * correspondientes al layout */
            rellenaCamposNotNull(fileTransfer);
            
            if (numCampoError.length() > 0) {
                fileTransfer.setCamposError(numCampoError.toString().substring(0,
                        numCampoError.length() - 1));
            }
            fileTransfer.setError(new ClobImpl(msgError.toString().substring(0, msgError.length() - 1)));
            
        }
        else {
            fileTransfer.setEstadoReg(ESTADO_NUEVO);
        }
    }
    
    /**
     * Rellena con CERO_STRING o CERO_LONG los campos Not Null en caso de alg&uacute;n Error 
     * @param fileTransfer
     */
    private void rellenaCamposNotNull(FileTransfer fileTransfer){
    	
    	log.info("Entrando a FileTransferServiceImpl.rellenaCamposNotNull()");
    	
    	fileTransfer.setCantidadTitulos(fileTransfer.getCantidadTitulos() == null ? 
        		CERO_STRING : fileTransfer.getCantidadTitulos());
        fileTransfer.setTipoOperacion(fileTransfer.getTipoOperacion() == null ? 
        		CERO_STRING : fileTransfer.getTipoOperacion());
        fileTransfer.setTipoMensaje(fileTransfer.getTipoMensaje() == null ? 
        		CERO_STRING : fileTransfer.getTipoMensaje());
        fileTransfer.setTipoMovto(fileTransfer.getTipoMovto() == null ? 
        		CERO_STRING : fileTransfer.getTipoMovto());   
        fileTransfer.setFechaLiquidacion(fileTransfer.getFechaLiquidacion() == null ? 
        		CERO_STRING : fileTransfer.getFechaLiquidacion());
        fileTransfer.setFechaNotificacion(fileTransfer.getFechaNotificacion() == null ? 
        		CERO_STRING : fileTransfer.getFechaNotificacion());
        fileTransfer.setFechaOperacion(fileTransfer.getFechaOperacion() == null ? 
        		CERO_STRING : fileTransfer.getFechaOperacion());
        fileTransfer.setIdInstRecep(fileTransfer.getIdInstRecep() == null ? 
        		CERO_STRING : fileTransfer.getIdInstRecep());
        fileTransfer.setFolioInstRecep(fileTransfer.getFolioInstRecep() == null ? 
        		CERO_STRING : fileTransfer.getFolioInstRecep());        
        fileTransfer.setCuentaRecep(fileTransfer.getCuentaRecep() == null ? 
        		CERO_STRING : fileTransfer.getCuentaRecep());
        fileTransfer.setIdBoveda(fileTransfer.getIdBoveda() == null ? 
        		CERO_LONG : fileTransfer.getIdBoveda());
        fileTransfer.setIdInstTrasp(fileTransfer.getIdInstTrasp() == null ? 
        		CERO_STRING : fileTransfer.getIdInstTrasp());        
        fileTransfer.setFolioInstTrasp(fileTransfer.getFolioInstTrasp() == null ? 
        		CERO_STRING : fileTransfer.getFolioInstTrasp());
        fileTransfer.setCuentaTrasp(fileTransfer.getCuentaTrasp() == null ? 
        		CERO_STRING : fileTransfer.getCuentaTrasp());
        fileTransfer.setEstatusOper(fileTransfer.getEstatusOper() == null ? 
        		CERO_STRING : fileTransfer.getEstatusOper());
        
    	
    }
    
    /**
     * Actualiza el mapa de parametros validos
     * @param parametrosValidos Si es null se crea un objeto Map nuevo
     * @param validaNegocioVO Si es null se retorna el Map recibido sin modificarse
     * @return Map
     */
	private Map actualizaParametrosValidos(Map<Object, Object> parametrosValidos, ValidaNegocioVO validaNegocioVO){
		
		log.info("Entrando a FileTransferServiceImpl.actualizaParametrosValidos()");
		
    	if(parametrosValidos == null){
    		
    		/* Se arma el mapa para almacenar los parametros ya validados */
    		parametrosValidos = new HashMap<Object, Object>();
    		parametrosValidos.put(VALORES_VALIDOS.BOVEDAS, new ArrayList<Long>());
    		parametrosValidos.put(VALORES_VALIDOS.CUENTAS_TRASPASANTE, new ArrayList<String>());
    		parametrosValidos.put(VALORES_VALIDOS.CUSTODIOS, new HashMap<String, AgenteVO>());
    		parametrosValidos.put(VALORES_VALIDOS.SIC_EMISION, new HashMap<String, AgenteVO>());
    		parametrosValidos.put(VALORES_VALIDOS.DEPOSITANTES, new ArrayList<String>());
    		parametrosValidos.put(VALORES_VALIDOS.DIVISAS, new ArrayList<String>());
    		parametrosValidos.put(VALORES_VALIDOS.EMISIONES, new ArrayList<String>());
    		
    	}
    	
   		if(validaNegocioVO != null && validaNegocioVO.getEmisionVO() != null){

        	/* Se limpia el mapa de parametros validos */
            parametrosValidos.clear();
            parametrosValidos.put(VALORES_VALIDOS.FECHA_ACTUAL, validaNegocioVO.getFechaActual());
            
            /* Se actualiza la lista de bovedas validadas */
            if(!validaNegocioVO.isEsBovedaValidada() && validaNegocioVO.isEsBovedaValida()){
            	validaNegocioVO.getBovedasValidas().add(validaNegocioVO.getBovedasValidas());
            }
            parametrosValidos.put(VALORES_VALIDOS.BOVEDAS, validaNegocioVO.getDivisasValidas());
            
            /* Se actualiza el mapa de cuentas traspasante validadas */
            if(!validaNegocioVO.isEsCuentaTraspasanteValidada() && validaNegocioVO.isEsCuentaTraspasanteValida()){
            	validaNegocioVO.getCuentasTraspasanteValidas().add(
            			validaNegocioVO.getAgenteTraspasante().toResumeString());	
            }
            parametrosValidos.put(VALORES_VALIDOS.CUENTAS_TRASPASANTE, 
            		validaNegocioVO.getCuentasTraspasanteValidas());
            
            /* Se actualiza la lista de custodios validados */
            if(!validaNegocioVO.isEsCustodioValidado() && validaNegocioVO.isEsCustodioValido()){
            	validaNegocioVO.getCustodiosValidos().put(
            			validaNegocioVO.getEmisionVO().toResumeString().concat(
            					validaNegocioVO.getCustodio()), validaNegocioVO.getAgenteSic());
            }
            parametrosValidos.put(VALORES_VALIDOS.CUSTODIOS, validaNegocioVO.getCustodiosValidos());
            
            /* Se actualiza la lista de emision SIC validadas */
            if(!validaNegocioVO.isEsSicEmisionValidado() && validaNegocioVO.isEsSicEmisionValido()){
            	validaNegocioVO.getSicEmision().put(
            			validaNegocioVO.getEmisionVO().toResumeString(), validaNegocioVO.getAgentesSic());
            }
            parametrosValidos.put(VALORES_VALIDOS.SIC_EMISION, validaNegocioVO.getSicEmision());
            
            /* Se actualiza la lista de depositantes validados */
            if(!validaNegocioVO.isEsDepositanteValidado() && validaNegocioVO.isEsDepositanteValido()){
            	validaNegocioVO.getDepositantesValidos().add(validaNegocioVO.
            			getEmisionVO().toResumeString().concat(validaNegocioVO.getDepositante()));	
            }
            parametrosValidos.put(VALORES_VALIDOS.DEPOSITANTES, validaNegocioVO.getDepositantesValidos());
            
            /* Se actualiza la lista de divisas validadas */
            if(!validaNegocioVO.isEsDivisaValidada() && validaNegocioVO.isEsDivisaValida()){
            	validaNegocioVO.getDivisasValidas().add(validaNegocioVO.getDivisa());	
            }
            parametrosValidos.put(VALORES_VALIDOS.DIVISAS, validaNegocioVO.getDivisasValidas());
            
            /* Se actualiza la lista de emisiones validadas */
            if(!validaNegocioVO.isEsEmisionValidada() && validaNegocioVO.isEsEmisionValida()){
            	validaNegocioVO.getEmisionesValidas().add(validaNegocioVO.getEmisionVO().toResumeString());
            }
            parametrosValidos.put(VALORES_VALIDOS.EMISIONES, validaNegocioVO.getEmisionesValidas());
            
        	
    	}
    	
    	return parametrosValidos;
    	
    }
    
	/**
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.FileTransferService#cancelaProceso(com.indeval.portalinternacional.middleware.servicios.vo.FileTransferVO)
	 */
	public void cancelaProceso(FileTransferVO fileTransferVO)
			throws BusinessException {
		
		log.info("Entrando a FileTransferServiceImpl.cancelaProceso()");
		
		if (fileTransferDao == null) {
			throw new BusinessException(errorResolver.getMessage("J0015"));
		}
		
		/* Valida parametros */
		validatorService.validaDTONoNulo(fileTransferVO, " FileTransferVO ");
		validatorService.validaAgente(fileTransferVO.getAgenteFirmado(), false);
		validatorService.validarClaveTipo(fileTransferVO.getTipoProceso(), " tipo de proceso ");
		
		/* Obtiene la lista de instancias FileTransfer */
		List<FileTransfer> listaFileTransferDivInt = this.findFileTransferDivIntByIdFolioTipoReg(fileTransferVO);

		for (Iterator<FileTransfer> iterator = listaFileTransferDivInt.iterator(); iterator.hasNext();) {
			FileTransfer fileTransferDivInt = (FileTransfer) iterator.next();
			fileTransferDao.delete(fileTransferDivInt);
		}
		
	}

	/**
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.FileTransferService#grabaInformacion(com.indeval.portalinternacional.middleware.servicios.vo.FileTransferVO)
	 */
	public void grabaInformacion(FileTransferVO fileTransferVO)
			throws BusinessException {
		
		log.info("Entrando a FileTransferServiceImpl.grabaInformacion()");
		
		if (fileTransferDao == null) {
			throw new BusinessException(errorResolver.getMessage("J0015"));
		}

		/* Se validan los parametros */
		validatorService.validaDTONoNulo(fileTransferVO, " FileTransferVO ");
		validatorService.validaAgente(fileTransferVO.getAgenteFirmado(), false);
		validatorService.validarClaveTipo(fileTransferVO.getTipoProceso(), " tipo de proceso ");

		/* Obtiene la lista de instancias FileTransfer */
		List listaFileTransfer = this.findFileTransferDivIntByIdFolioTipoReg(fileTransferVO);
		
		if (listaFileTransfer != null && !listaFileTransfer.isEmpty()) {
			
	        Boolean isProtocolo = null;

	        for (Iterator iterator = listaFileTransfer.iterator(); iterator.hasNext();) {
	            FileTransfer fileTransfer = (FileTransfer) iterator.next();

	            if (fileTransferVO.getConsecProtocolo() != null
	                    && !fileTransferVO.getConsecProtocolo().isEmpty()
	                    && fileTransferVO.getConsecProtocolo().containsKey(fileTransfer.getConsecReg())) {
	                if (((Integer) fileTransferVO.getConsecProtocolo().get(fileTransfer.getConsecReg())).intValue() == 1) {
	                    isProtocolo = Boolean.TRUE;
	                }
	                else {
	                    isProtocolo = null;
	                }
	            }
	            else {
	                isProtocolo = Boolean.FALSE;
	            }

	            if (isProtocolo != null) {
	                if (isProtocolo) {
	                    fileTransfer.setEstadoReg(ESTADO_A_PROTOCOLO);
	                    fileTransferDao.update(fileTransfer);
	                }
	            }
	            else {
	                fileTransfer.setEstadoReg(ESTADO_ERROR);
	                fileTransferDao.update(fileTransfer);
	            }
	            if (fileTransfer.getEstadoReg().equalsIgnoreCase(ESTADO_NUEVO)) {
	                try {
	                	
	                    divisionInternacionalService.insertaOperacionSIC(this.creaOperacionSIC(fileTransfer));
	                    fileTransfer.setEstadoReg(ESTADO_CARGADO);
	                    fileTransferDao.update(fileTransfer);
	                }
	                catch (Exception e) {
	                    throw new BusinessException(e.getMessage());
	                }
	            }

	        }
			
		}
		
	}
    
	/**
	 * Encapsula las validaciones de la emision, el mercado y la posicion 
	 * en caso de que se requiera esta ultima.
	 * @param validaNegocioVO
	 * @return ValidaNegocioVO
	 */
	private ValidaNegocioVO recuperaEmision(ValidaNegocioVO validaNegocioVO) {

		log.info("Entrando a FileTransferServiceImpl.recuperaEmision()");

		if(validaNegocioVO != null) {
			
			boolean noError = validaNegocioVO.isNoError();

			/* Se recupera la emisionVO y se verifica si se debe validar */
			EmisionVO emisionVO = validaNegocioVO.getEmisionVO();
			if(emisionVO != null && !validaNegocioVO.isEsEmisionValidada()){

				/* Se coloca el idBoveda en la emisionVO */
				emisionVO.setIdBoveda(validaNegocioVO.getIdBoveda());
				
				/* Se obtiene el objeto PosicionNombrada */
				PosicionNombrada posicionNombrada = 
					posicionNombradaDao.findPosicion(validaNegocioVO.getAgenteTraspasante(), emisionVO);

				/* Se valida el saldo disponible (la posicion) de la emision, cuando son Entregas (tipo_oper='E') */
				if (StringUtils.isNotBlank(validaNegocioVO.getCampos()
						[((Integer) validaNegocioVO.getIndice().get(TIPO_MOV)).intValue()]) && 
						validaNegocioVO.getCampos()[((Integer) validaNegocioVO.getIndice().
								get(TIPO_MOV)).intValue()].trim().equalsIgnoreCase(TIPO_MOVTO_E)) {

					if(posicionNombrada == null || 
							BIG_INTEGER_ZERO.compareTo(posicionNombrada.getPosicionDisponible()) > 0) {
						this.agregaErrorEmisionInvalida(validaNegocioVO, false);
					}

				}
				
				/* Se settea la emision valida en el objeto validaNegocioVO */
				if(posicionNombrada == null || posicionNombrada.getCupon() == null ||
						posicionNombrada.getCupon().getEmision() == null){
					this.agregaErrorEmisionInvalida(validaNegocioVO, true);
					validaNegocioVO.setEmisionVO(null);
				}
				else {
					validaNegocioVO.setEmisionVO(ConvertBO2VO.crearEmisionVO(
							posicionNombrada, validaNegocioVO.getFechaActual()));
					validaNegocioVO.setEsEmisionValida(true);
				}
				
				/* Se toma la emision ya validada y se valida el mercado */
				if(validaNegocioVO.getEmisionVO() == null || 
						StringUtils.isBlank(validaNegocioVO.getEmisionVO().getMercado())) {
					this.agregaErrorComun("No existe el mercado", validaNegocioVO.getMsgError());
					this.agregaErrorComun(((Integer) validaNegocioVO
							.getIndice().get(TV)).toString(), validaNegocioVO.getNumCampoError());
					noError = false;
				}

			}

			validaNegocioVO.setNoError(noError);
		}
		
		return validaNegocioVO;

	}
    
    /**
     * Agrega Error Emision Invalida
     * @param validaNegocioVO
     * @return
     */
    private ValidaNegocioVO agregaErrorEmisionInvalida(ValidaNegocioVO validaNegocioVO, boolean tienePosicion){
    	
    	log.info("Entrando a FileTransferServiceImpl.agregaErrorEmisionInvalida()");
    	
    	this.agregaErrorComun(errorResolver.getMessage(
    			tienePosicion ? "J0001" : "J0019"), validaNegocioVO.getMsgError());
    	this.agregaErrorComun(((Integer) validaNegocioVO.getIndice().
    			get(TV)).toString(), validaNegocioVO.getNumCampoError());
    	this.agregaErrorComun(((Integer) validaNegocioVO.getIndice().
    			get(EMISORA)).toString(), validaNegocioVO.getNumCampoError());
    	this.agregaErrorComun(((Integer) validaNegocioVO.getIndice().
    			get(SERIE)).toString(), validaNegocioVO.getNumCampoError());
    	this.agregaErrorComun(((Integer) validaNegocioVO.getIndice().
    			get(CUPON)).toString(), validaNegocioVO.getNumCampoError());
    	this.agregaErrorComun(((Integer) validaNegocioVO.getIndice().
    			get(ISIN)).toString(), validaNegocioVO.getNumCampoError());
    	validaNegocioVO.setNoError(false);
    	
    	return validaNegocioVO;
    	
    }
    
    /**
     * Construye un objeto {@link OperacionSic} a partir del objeto {@link FileTransfer} recibido
     * @param fileTransfer
     * @return OperacionSICParams
     * @throws ParseException
     * @throws BusinessException
     */
    private OperacionSic creaOperacionSIC(FileTransfer fileTransfer)
            throws ParseException, BusinessException {

    	log.info("Entrando a FileTransferServiceImpl.creaOperacionSICVO()");
    	
    	OperacionSic operacionSIC = null;
    	
    	/* Se valida el objeto FileTransfer recibido y se arma el OperacionSic */
    	if(fileTransfer != null){
    		
            operacionSIC = new OperacionSic();
            
            operacionSIC.setTipoTraspaso(fileTransfer.getTipoMovto());
            
            operacionSIC.setTipoOperacion(fileTransfer.getTipoOperacion());
            
            /* Se arma el AgenteVO con los datos del Agente Traspasante */
            AgenteVO agenteTraspasante = new AgenteVO(fileTransfer.getFolioInstTrasp(), 
            		fileTransfer.getIdInstTrasp(), fileTransfer.getCuentaTrasp());
            
            /* Se recupera la CuentaNombrada */
            CuentaNombrada cuentaNombrada = cuentaNombradaDao.findCuenta(agenteTraspasante);
            
            /* Se settea la Cuenta Nombrada en el objeto OperacionSic */
            operacionSIC.setCuentaNombrada(cuentaNombrada);
            
            operacionSIC.setFechaLiquidacion(DateUtils.convierteStringToDate(fileTransfer.getFechaLiquidacion()));
            
            operacionSIC.setFechaNotificacion(DateUtils.convierteStringToDate(fileTransfer.getFechaNotificacion()));
            
            operacionSIC.setFechaOperacion(DateUtils.convierteStringToDate(fileTransfer.getFechaOperacion()));
            
            /* Se arma el EmisionVO */
            EmisionVO emisionVO = new EmisionVO(fileTransfer.getTv(), fileTransfer.getEmisora(), 
            		fileTransfer.getSerie(), fileTransfer.getCupon());
            Emision emision = emisionDao.findEmision(emisionVO);

            /* Se settea la Emision en el objeto OperacionSic */
            operacionSIC.setEmision(emision);
            
            operacionSIC.setBoveda(new Boveda());
            operacionSIC.getBoveda().setIdBoveda(fileTransfer.getIdBoveda());

            /* Recupera el objeto SICEmision  que corresponde a la emision recibida */
            List listaSicEmision =  sicEmisionDao.findSicEmisionesByEmision(emisionVO);
            
            /* Recupera las CuentaNombrada de la lista de SICEmision y se construye una lista de AgenteVO */
            List<AgenteVO> listaAgenteVO = new ArrayList<AgenteVO>();
            for (Iterator<SicEmision> iterator = listaSicEmision.iterator(); iterator.hasNext();) {
                SicEmision sicEmision = (SicEmision) iterator.next();
                if(sicEmision != null) {
                    listaAgenteVO.add(ConvertBO2VO.crearAgenteVO(sicEmision.getCuentaNombrada()));
                }

            }

            if(listaAgenteVO != null && !listaAgenteVO.isEmpty()){

                /* Se construye un arreglo de AgenteVO */
                AgenteVO[] arregloAgenteVO = null;
                arregloAgenteVO = new AgenteVO[listaAgenteVO.size()];
                arregloAgenteVO = listaAgenteVO.toArray(arregloAgenteVO);

                /* Se recupera y settea el SicDetalle*/
                SicDetalle sicDetalle = 
                    sicDetalleDao.findSicDetalle(arregloAgenteVO, fileTransfer.getNombreDepositante());
                operacionSIC.setSicDetalle(sicDetalle);

                /* Se recupera y settea el CatBic */
                List listaCatBic = 
                    catBicDao.findCatBic(arregloAgenteVO, fileTransfer.getDescCustodio());

                operacionSIC.setCatBic((CatBic) listaCatBic.get(0));
            }
            
            operacionSIC.setCuentaContraparte(fileTransfer.getCuentaContraparte());
            
            operacionSIC.setDescContraparte(fileTransfer.getDescContraparte());
            
            operacionSIC.setInstruccEspeciales(fileTransfer.getInstruccEspeciales());
            
            operacionSIC.setNomCtaBenef(fileTransfer.getNombreCuentaBeneficiario());
            
            operacionSIC.setNumCtaBenef(fileTransfer.getNumeroCuentaBeneficiario());
            
            operacionSIC.setImporte(fileTransfer.getImporte() != null ? new BigDecimal(fileTransfer.getImporte()) : null);
            
            operacionSIC.setDivisa(fileTransfer.getDivisa());
            
    	}

        return operacionSIC;
    }

	/**
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.FileTransferService#muestraInformacion(com.indeval.portalinternacional.middleware.servicios.vo.FileTransferVO)
	 */
	public TotalesProcesoVO muestraInformacion(FileTransferVO fileTransferVO)
			throws BusinessException {
		
		log.info("Entrando a FileTransferServiceImpl.muestraInformacion()");
		
		/* Se validan los parametros */
		validatorService.validaDTONoNulo(fileTransferVO, "");
		validatorService.validaAgente(fileTransferVO.getAgenteFirmado(), false);
		validatorService.validarClaveTipo(fileTransferVO.getTipoProceso(), " tipo de proceso ");

		fileTransferVO.setPaginaVO(UtilsVO.getPaginaNotBlank(fileTransferVO.getPaginaVO()));
		
		log.debug("FileTransferVO = [" + fileTransferVO.toString() + "]");
		log.debug("Informacion Archivo = [" + fileTransferVO.toStringInformacionArchivo() + "]");

		/* Valida parametros */
		TotalesProcesoVO totalesProcesoVO = null;
		try {

			/* Obtiene la lista de instancias FileTransfer */
			List<FileTransfer> listaFileTransferDivInt = 
				this.findFileTransferDivIntByIdFolioTipoReg(fileTransferVO);

			if (listaFileTransferDivInt != null && !listaFileTransferDivInt.isEmpty()) {
				
				ArrayList listaRegistros = new ArrayList();
				int registrosError = 0;
				int totalregistros = 0;
				for (Iterator iterator = listaFileTransferDivInt.iterator(); iterator.hasNext();) {
					FileTransfer fileTransfer = (FileTransfer) iterator.next();
					validatorService.validaDTONoNulo(fileTransfer, " fileTransferDivInt ");
					if (!fileTransferVO.isSoloErrores()
							|| (fileTransferVO.isSoloErrores() && fileTransfer.getEstadoReg()
									.compareTo(ESTADO_ERROR) == 0)) {
						RegistroProcesadoVO registroProcesadoVO = new RegistroProcesadoVO();
						CamposDivIntVO camposDivIntVO = new CamposDivIntVO();
//						registroProcesadoVO.setCadena(fileTransfer.getCadena());
						registroProcesadoVO.setConsec(fileTransfer.getConsecReg());
						registroProcesadoVO.setDatos(this.generaDatos(fileTransfer, 
								fileTransferVO.getTipoProceso(), camposDivIntVO));
						if (registroProcesadoVO.getDatos() == null) {
							registroProcesadoVO
									.setDatos((List<Object>) new ArrayList<Object>());
						}
						/* Se convierte el Clob en un String para poder hacer el split() */
						if(fileTransfer.getError() != null) {
							Reader chr_instream = fileTransfer.getError().getCharacterStream();
							char[] chr_buffer = new char[(int)fileTransfer.getError().length()];
							chr_instream.read( chr_buffer );
							String errores = new String(chr_buffer );
							registroProcesadoVO.setMensajesError(errores.split("-"));
						}
						registroProcesadoVO.setEstado(fileTransfer.getEstadoReg());
						registroProcesadoVO.setFechaRegistro(fileTransfer.getFechaReg());
						registroProcesadoVO.setNombreUsuario(fileTransfer.getUsuario());

						registroProcesadoVO.setCamposDivIntVO(camposDivIntVO);

						if (fileTransfer.getEstadoReg().compareTo(ESTADO_ERROR) == 0) {
							registrosError++;
						}

						totalregistros++;
						listaRegistros.add(registroProcesadoVO);
					}
				}
				
				if(listaRegistros != null && !listaRegistros.isEmpty()) {
					totalesProcesoVO = new TotalesProcesoVO();
					fileTransferVO.getPaginaVO().extraerSublist(listaRegistros);
	
					totalesProcesoVO.setPaginaVO(fileTransferVO.getPaginaVO());
					totalesProcesoVO.setRegistrosACargar(new Integer(totalregistros
							- registrosError));
					totalesProcesoVO.setRegistrosConError(new Integer(registrosError));
				}
			}
			
			
		} catch (Exception e) {
			log.debug(e.getMessage());
			e.printStackTrace();
		}

		return totalesProcesoVO;
		
	}

	/**
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.FileTransferService#obtieneResumen(com.indeval.portalinternacional.middleware.servicios.vo.FileTransferVO)
	 */
	public ResumenVO obtieneResumen(FileTransferVO fileTransferVO)
			throws BusinessException {
		
		log.info("Entrando a FileTransferServiceImpl.obtieneResumen()");

		/* Valida parametros */
		validatorService.validaDTONoNulo(fileTransferVO, "");
		validatorService.validaAgente(fileTransferVO.getAgenteFirmado(), false);
		validatorService.validarClaveTipo(fileTransferVO.getTipoProceso(), " tipo de proceso ");

		/* Obtiene la lista de instancias FileTransfer */
		List<FileTransfer> listaFileTransferDivInt = 
			this.findFileTransferDivIntByIdFolioTipoReg(fileTransferVO);
		
		/* Se inicializan las variables de conteo */
		int registrosCargados = 0;
		int registrosError = 0;
		int registrosNuevos = 0;
		int totalRegistros = 0;
		int registrosProtocolo = 0;
		String nombreUsuario = "";
		Date fechaCarga = null;
		
		/* Se iteran los registros para efectuar el conteo */
		if (listaFileTransferDivInt != null
				&& !listaFileTransferDivInt.isEmpty()) {
			for (Iterator iterator = listaFileTransferDivInt.iterator(); iterator.hasNext();) {
				FileTransfer fileTransfer = (FileTransfer) iterator.next();
				validatorService.validaDTONoNulo(fileTransfer, " fileTransfer ");

				if (fileTransfer.getEstadoReg().compareTo(ESTADO_ERROR) == 0) {
					registrosError++;
				} else if (fileTransfer.getEstadoReg().compareTo(
						ESTADO_NUEVO) == 0) {
					registrosNuevos++;
				} else if (fileTransfer.getEstadoReg().compareTo(
						ESTADO_CARGADO) == 0) {
					registrosCargados++;
				} else if (fileTransfer.getEstadoReg().compareTo(
						ESTADO_A_PROTOCOLO) == 0) {
					registrosProtocolo++;
				}

				nombreUsuario = fileTransfer.getUsuario();
				fechaCarga = fileTransfer.getFechaReg();
				totalRegistros++;
			}

			if (totalRegistros != registrosCargados + registrosError
					+ registrosNuevos + registrosProtocolo) {
				throw new BusinessException(errorResolver.getMessage(
						"J0049", new Object[] { " " }));
			}

		}

		/* Se construye el resumen */
		ResumenVO resumenVO = new ResumenVO();
		resumenVO.setAgenteFirmado(fileTransferVO.getAgenteFirmado());
		resumenVO.setFechaCarga(fechaCarga);
		resumenVO.setTipoProceso(fileTransferVO.getTipoProceso());
		resumenVO.setTotalCargados(new Integer(registrosCargados));
		resumenVO.setTotalError(new Integer(registrosError));
		resumenVO.setTotalNuevos(new Integer(registrosNuevos));
		resumenVO.setTotalRegistros(new Integer(totalRegistros));
		resumenVO.setTotalProtocolo(new Integer(registrosProtocolo));
		resumenVO.setNombreUsuario(nombreUsuario);

		return resumenVO;
		
	}
	
	/**
	 * Obtiene la lista de instancias FileTransferDivInt
	 * @param fileTransferVO
	 * @return List
	 */
	private List<FileTransfer> findFileTransferDivIntByIdFolioTipoReg(FileTransferVO fileTransferVO) {
		
		log.info("Entrando a FileTransferServiceImpl.findFileTransferDivIntByIdFolioTipoReg()");
		
		if (fileTransferDao == null) {
			throw new BusinessException(errorResolver.getMessage("J0015"));
		}
		
		List<FileTransfer> listaFileTransfer = null;
		if(fileTransferVO != null && fileTransferVO.getAgenteFirmado()!= null && 
				StringUtils.isNotBlank(fileTransferVO.getTipoProceso())) {
			listaFileTransfer = fileTransferDao.findFileTransferDivIntByIdFolioTipoReg(
					fileTransferVO.getAgenteFirmado().getId(), 
					fileTransferVO.getAgenteFirmado().getFolio(), 
					fileTransferVO.getTipoProceso().trim());
		}
		
		return listaFileTransfer;
		
	}

	/**
	 * Encapsula validaciones y reglas de negocio
	 * ALERT! El objeto validaNegocioVO puede ser modificado durante las validaciones
	 * @param validaNegocioVO
	 * @return boolean
	 * @throws BusinessException
	 */
	private boolean validaNegocio(ValidaNegocioVO validaNegocioVO)
			throws BusinessException {

		log.info("Entrando a FileTransferServiceImpl.validaNegocio()");		

		boolean noError = validaNegocioVO.isNoError();
		
		Date fechaActualHrCero = dateUtilService.getFechaHoraCero(validaNegocioVO.getFechaActual());

		/* Valida que la institucion del archivo corresponda a la del agente firmado */
		if (!validaNegocioVO.vendedorEsAgenteFirmado()) {
			this.agregaErrorComun(errorResolver.getMessage("J0039"),
					validaNegocioVO.getMsgError());
			this.agregaErrorComun(((Integer) validaNegocioVO
					.getIndice().get(ID_INST_VEND)).toString(), validaNegocioVO
					.getNumCampoError());
			this.agregaErrorComun(((Integer) validaNegocioVO
					.getIndice().get(FOLIO_INST_VEND)).toString(),
					validaNegocioVO.getNumCampoError());
			return false;
		}

		/* Valida que el traspasante exista */
		if(!validaNegocioVO.isEsCuentaTraspasanteValidada()){
			
			try {
				CuentaNombrada cuentaNombrada = 
					cuentaNombradaDao.findCuenta(validaNegocioVO.getAgenteTraspasante());
	
				AgenteVO agenteTrasp = ConvertBO2VO.crearAgenteVO(cuentaNombrada);
				int longitudInicialMsgError = validaNegocioVO.getMsgError().length();
				if(agenteTrasp == null || !agenteTrasp.tieneClave() || 
						StringUtils.isBlank(agenteTrasp.getCuenta())){
					this.agregaErrorComun(errorResolver.getMessage("J0022"),
							validaNegocioVO.getMsgError());
					this.agregaErrorComun(((Integer) validaNegocioVO
							.getIndice().get(ID_INST_VEND)).toString(), validaNegocioVO
							.getNumCampoError());
					this.agregaErrorComun(((Integer) validaNegocioVO
							.getIndice().get(FOLIO_INST_VEND)).toString(),
							validaNegocioVO.getNumCampoError());
					this.agregaErrorComun(((Integer) validaNegocioVO
							.getIndice().get(CUENTA_VEND)).toString(), validaNegocioVO
							.getNumCampoError());
					noError = false;	
				}
				else{
					validaNegocioVO.setAgenteTraspasante(agenteTrasp);
				}
			
				/* Valida que sea una cuenta permitida para operaciones SIC */
				if (!validatorDivIntService.esCuentaValida(validaNegocioVO.getAgenteTraspasante())) {
					this.agregaErrorComun(errorResolver.getMessage("J0023"), 
							validaNegocioVO.getMsgError());
					this.agregaErrorComun(((Integer) validaNegocioVO
							.getIndice().get(CUENTA_VEND)).toString(), validaNegocioVO
							.getNumCampoError());
					noError = false;
				}
				int longitudFinalMsgError = validaNegocioVO.getMsgError().length();
				if(longitudFinalMsgError - longitudInicialMsgError == 0){
					validaNegocioVO.setEsCuentaTraspasanteValida(true);
				}
			} catch(BusinessException e) {
				this.agregaErrorComun(errorResolver.getMessage("J0022"),
						validaNegocioVO.getMsgError());
				this.agregaErrorComun(((Integer) validaNegocioVO
						.getIndice().get(ID_INST_VEND)).toString(), validaNegocioVO
						.getNumCampoError());
				this.agregaErrorComun(((Integer) validaNegocioVO
						.getIndice().get(FOLIO_INST_VEND)).toString(),
						validaNegocioVO.getNumCampoError());
				this.agregaErrorComun(((Integer) validaNegocioVO
						.getIndice().get(CUENTA_VEND)).toString(), validaNegocioVO
						.getNumCampoError());
				noError = false;
			}
		}

		/* Obtiene la cuenta receptora */
		try {
			/* TODO utilDivIntService.obtieneCuentaReceptora() siempre devuelve '0030' 
			 * hasta definir la regla de negocio */
			validaNegocioVO.setCuentaRecep(
					utilDivIntService.obtieneCuentaReceptora(validaNegocioVO.getAgenteTraspasante()));
	
			if (CERO_STRING.equalsIgnoreCase(validaNegocioVO.getCuentaRecep())) {
				this.agregaErrorComun(errorResolver.getMessage("J0040"), 
						validaNegocioVO.getMsgError());
				this.agregaErrorComun(
						((Integer) validaNegocioVO.getIndice()
								.get(ID_INST_VEND)).toString(), validaNegocioVO
								.getNumCampoError());
				this.agregaErrorComun(
						((Integer) validaNegocioVO.getIndice().get(
								FOLIO_INST_VEND)).toString(), validaNegocioVO
								.getNumCampoError());
				noError = false;
			}
			
		} 
		catch(BusinessException e) {
			/* TODO este try-catch no deberia de estar utilDivIntService.obtieneCuentaReceptora()
			 * no deberia arrojar BusinessException */
			this.agregaErrorComun(errorResolver.getMessage("J0040"), 
					validaNegocioVO.getMsgError());
			this.agregaErrorComun(
					((Integer) validaNegocioVO.getIndice()
							.get(ID_INST_VEND)).toString(), validaNegocioVO
							.getNumCampoError());
			this.agregaErrorComun(
					((Integer) validaNegocioVO.getIndice().get(
							FOLIO_INST_VEND)).toString(), validaNegocioVO
							.getNumCampoError());
			noError = false;
		}
		
		/* Se valida la Boveda */
		if(!validaNegocioVO.isEsBovedaValidada()){
			Boveda boveda = bovedaDao.findBovedaById(validaNegocioVO.getIdBoveda());
			if(boveda == null){
				this.agregaErrorComun(errorResolver.getMessage("J0050"), 
						validaNegocioVO.getMsgError());
				this.agregaErrorComun(((Integer) validaNegocioVO
								.getIndice().get(ID_BOVEDA)).toString(),
								validaNegocioVO.getNumCampoError());
				noError = false;
				validaNegocioVO.setIdBoveda(CERO_LONG);
			}
			else {
				validaNegocioVO.setEsBovedaValida(true);
			}
		}
		
		/* Se valida la fecha de notificaci&oacute;n que trae el archivo plano */
		String fechaNot = FactoryDivInt.fechaArchivo2fechaFileTransfer(
				validaNegocioVO.getCampos()[(
						(Integer) validaNegocioVO.getIndice().get(FECHA_NOT)).intValue()]);
		try {
			if (DateUtils.convierteStringToDate2(fechaNot).compareTo(fechaActualHrCero) != 0) {
				this.agregaErrorComun(errorResolver.getMessage("J0024"), 
						validaNegocioVO.getMsgError());
				this.agregaErrorComun(((Integer) validaNegocioVO
								.getIndice().get(FECHA_NOT)).toString(),
								validaNegocioVO.getNumCampoError());
				noError = false;
			}
		} catch (ParseException e) {
			this.agregaErrorComun(errorResolver.getMessage("J0025"), 
					validaNegocioVO.getMsgError());
			this.agregaErrorComun(((Integer) validaNegocioVO
					.getIndice().get(FECHA_NOT)).toString(), validaNegocioVO
					.getNumCampoError());
			noError = false;
		}

		/*
		 * Se valida la fecha de liquidaci&oacute;n que trae el archivo plano
		 * sea h&aacute;bil
		 */
		String fechaLiq = FactoryDivInt.fechaArchivo2fechaFileTransfer(
				validaNegocioVO.getCampos()[(
						(Integer) validaNegocioVO.getIndice().get(FECHA_LIQ)).intValue()]);
		Date fechaLiqD = null;
		try {
			fechaLiqD = DateUtils.convierteStringToDate2(fechaLiq);
			if (dateUtilService.esInhabil(fechaLiqD)) {
				this.agregaErrorComun(errorResolver.getMessage("J0027"),
						validaNegocioVO.getMsgError());
				this.agregaErrorComun(((Integer) validaNegocioVO
								.getIndice().get(FECHA_LIQ)).toString(),
								validaNegocioVO.getNumCampoError());
				noError = false;
			}
		} catch (ParseException e) {
			this.agregaErrorComun(errorResolver.getMessage("J0028"),
					validaNegocioVO.getMsgError());
			this.agregaErrorComun(((Integer) validaNegocioVO
					.getIndice().get(FECHA_LIQ)).toString(), validaNegocioVO
					.getNumCampoError());
			noError = false;
		}

		/*
		 * Se valida la fecha de operaci&oacute;n que trae el archivo plano sea
		 * h&aacute;bil
		 */
		String fechaOper = FactoryDivInt
				.fechaArchivo2fechaFileTransfer(validaNegocioVO.getCampos()[((Integer) validaNegocioVO
						.getIndice().get(FECHA_OPER)).intValue()]);
		Date fechaOperD = null;
		try {
			fechaOperD = DateUtils.convierteStringToDate2(fechaOper);
			if (dateUtilService.esInhabil(fechaOperD)) {
				this.agregaErrorComun(errorResolver.getMessage("J0029"),
						validaNegocioVO.getMsgError());
				this.agregaErrorComun(((Integer) validaNegocioVO
								.getIndice().get(FECHA_OPER)).toString(),
								validaNegocioVO.getNumCampoError());
				noError = false;
			}
		} catch (ParseException e) {
			this.agregaErrorComun(errorResolver.getMessage("J0030"),
					validaNegocioVO.getMsgError());
			this.agregaErrorComun(((Integer) validaNegocioVO
					.getIndice().get(FECHA_OPER)).toString(), validaNegocioVO
					.getNumCampoError());
			noError = false;
		}

		/*
		 * Se valida que la fecha de operacion sea menor o igual a la de
		 * liquidacion
		 */
		if (fechaLiqD != null && fechaOperD != null) {
			if (fechaLiqD.compareTo(fechaOperD) < 0) {
				this.agregaErrorComun(errorResolver.getMessage("J0031"),
								validaNegocioVO.getMsgError());
				this.agregaErrorComun(((Integer) validaNegocioVO
								.getIndice().get(FECHA_OPER)).toString(),
								validaNegocioVO.getNumCampoError());
				noError = false;
			}
		}

		/* Se valida que la cantidad operada sea mayor a 0 */
		if (!validatorDivIntService.validaCantidades(validaNegocioVO
				.getCampos()[((Integer) validaNegocioVO.getIndice().get(
				CANTIDAD)).intValue()])) {
			this.agregaErrorComun(errorResolver.getMessage("J0032"),
					validaNegocioVO.getMsgError());
			this.agregaErrorComun(((Integer) validaNegocioVO
					.getIndice().get(CANTIDAD)).toString(), validaNegocioVO
					.getNumCampoError());
			noError = false;
		}

		/* Se actualiza la bandera noError */
		validaNegocioVO.setNoError(noError);
		
		/* Verifica el Depositante y el Custodio */
		/* ALERT! El objeto validaNegocioVO puede ser modificado durante las validaciones */
		validaNegocioVO = this.validaDepositanteCustodio(validaNegocioVO);
		
		/* Verifica el Importe y la Divisa */
		/* ALERT! El objeto validaNegocioVO puede ser modificado durante las validaciones */
		validaNegocioVO = this.validaImporteDivisa(validaNegocioVO);
		
		return validaNegocioVO.isNoError();
	}
	
	/**
	 * Encapsula las validaciones del depositante y del custodio
	 * @param validaNegocioVO
	 * @return ValidaNegocioVO
	 */
	private ValidaNegocioVO validaDepositanteCustodio(ValidaNegocioVO validaNegocioVO){
		
		log.info("Entrando a FileTransferServiceImpl.validaDepositanteCustodio()");
		
		AgenteVO[] agentesSic = null;
		AgenteVO agenteCatBic = null;
		AgenteVO agenteSicDetalle = null;
		
		/* Se verifican los Map y List utilizados para la verificacion de atributos validos */
		if(validaNegocioVO != null && validaNegocioVO.getEmisionVO() != null && 
				validaNegocioVO.getSicEmision()!= null &&
				validaNegocioVO.getCustodiosValidos()!= null &&
				validaNegocioVO.getDepositantesValidos()!= null &&
				StringUtils.isNotBlank(validaNegocioVO.getCustodio()) && 
				StringUtils.isNotBlank(validaNegocioVO.getDepositante())){
			
			/* Se verifica si el depositante aun no esta validado */
			if(!validaNegocioVO.getDepositantesValidos().contains(
							validaNegocioVO.getEmisionVO().toResumeString().trim().concat(
							validaNegocioVO.getCustodio().trim().concat(
							validaNegocioVO.getDepositante().trim())))){
				
				/* Se verifica si el custodio aun no esta validado */
				if(!validaNegocioVO.getCustodiosValidos().containsKey(
								validaNegocioVO.getCustodio())){
					
					/* Se verifica si el arreglo de AgenteSic aun no se obtiene */
					if(!validaNegocioVO.getSicEmision().containsKey(
									validaNegocioVO.getEmisionVO().toResumeString())){
						
						/* Se recupera el arreglo de AgenteVO vinculado al SicEmision */
						agentesSic = utilDivIntService.obtieneAgentesSIC(validaNegocioVO.getEmisionVO());
					}
					else {
						
						/* Se recupera el arreglo del Map de SicEmision validados */
						agentesSic = (AgenteVO[]) validaNegocioVO.getSicEmision().get(
								validaNegocioVO.getEmisionVO().toResumeString());
					}
					
					/* Se valida el custodio*/
					if(agentesSic == null){
						this.agregaErrorCustodioDepositanteInvalido(validaNegocioVO, null);
					}
					else {
						
	                    if (StringUtils.isNotBlank(validaNegocioVO.getTipoOperacion()) && 
	                    		validaNegocioVO.getTipoOperacion().equalsIgnoreCase(TRASPASO_CONTRA)) {
	                    		                        
	                		this.agregaErrorComun(errorResolver.getMessage("J0014"),
	                				validaNegocioVO.getMsgError());
	                		this.agregaErrorComun(((Integer) validaNegocioVO
	                				.getIndice().get(CUSTODIO)).toString(), validaNegocioVO
	                				.getNumCampoError());
	                		validaNegocioVO.setNoError(false);
	                        
	                    }
	                    else {
							validaNegocioVO.setEsSicEmisionValido(true);
							validaNegocioVO.setAgentesSic(agentesSic);
							List<CatBic> listaCatBic = 
								catBicDao.findCatBic(agentesSic, validaNegocioVO.getCustodio());
							if(listaCatBic != null && !listaCatBic.isEmpty()){
								agenteCatBic = ConvertBO2VO.crearAgenteVO(
										((CatBic)listaCatBic.get(0)).getCuentaNombrada());												
							}
	                    }

					}

				}
				else{
					agenteCatBic = (AgenteVO) validaNegocioVO.getCustodiosValidos().get(
							validaNegocioVO.getEmisionVO().toResumeString().trim().concat(
									validaNegocioVO.getCustodio().trim()));
				}
				
				/* Se verifica el custodio o se recupera en caso de requerirse */
				if(agenteCatBic == null) {
					this.agregaErrorCustodioDepositanteInvalido(validaNegocioVO, Boolean.FALSE);
				}
				else{
					/* Se guarda el AgenteVO en el validaNegocioVO */
					validaNegocioVO.setAgenteSic(agenteCatBic);
					validaNegocioVO.setEsCustodioValido(true);
					/* Se valida el depositante */
					AgenteVO[] arregloAgenteVO = {agenteCatBic};
					agenteSicDetalle = ConvertBO2VO.crearAgenteVO(sicDetalleDao.findSicDetalle(
							arregloAgenteVO, validaNegocioVO.getDepositante()).getCuentaNombrada());
				}
				
				if(agenteSicDetalle == null) {
					this.agregaErrorCustodioDepositanteInvalido(validaNegocioVO, Boolean.TRUE);
				}
				else {
					validaNegocioVO.setEsDepositanteValido(true);
				}
				
			}
			
		}
		else {
			this.agregaErrorCustodioDepositanteInvalido(validaNegocioVO, null);
		}
		
		return validaNegocioVO;
	}
	
    /**
     * Agrega Error Custodio Depositante Invalido
     * @param validaNegocioVO
     * @param errorDepositante Boolean 
     * 		{TRUE = agrega error depositante, FALSE = agrega error custodio, NULL = agrega ambos errores}
     * @return ValidaNegocioVO
     */
    private ValidaNegocioVO agregaErrorCustodioDepositanteInvalido(ValidaNegocioVO validaNegocioVO, 
    		Boolean errorDepositante){
    	
    	log.info("Entrando a FileTransferServiceImpl.agregaErrorCustodioDepositanteInvalido()");
    	
    	if(errorDepositante == null || errorDepositante){
    		this.agregaErrorComun(errorResolver.getMessage("J0043"),
    				validaNegocioVO.getMsgError());
    		this.agregaErrorComun(((Integer) validaNegocioVO
    				.getIndice().get(DEPOSITANTE)).toString(), validaNegocioVO
    				.getNumCampoError());
    		validaNegocioVO.setNoError(false);
    	}
    	if(errorDepositante == null || !errorDepositante){
    		this.agregaErrorComun(errorResolver.getMessage("J0042"),
    				validaNegocioVO.getMsgError());
    		this.agregaErrorComun(((Integer) validaNegocioVO
    				.getIndice().get(CUSTODIO)).toString(), validaNegocioVO
    				.getNumCampoError());
    		validaNegocioVO.setNoError(false);
    	}
    	
    	return validaNegocioVO;
    	
    }
	
	/**
	 * Encapsula las validaciones de Importe y Divisa
	 * @param validaNegocioVO
	 * @return ValidaNegocioVO
	 */
	private ValidaNegocioVO validaImporteDivisa(ValidaNegocioVO validaNegocioVO) {
		
		log.info("Entrando a FileTransferServiceImpl.validaImporteDivisa()");
		
		boolean noError = validaNegocioVO.isNoError();
		
		/* Si la operacion es de tipo TCP se valida que el importe y la divisa vengan */
		if (validaNegocioVO.isTraspasoContra()) {
		
			if (validaNegocioVO.getTam() < LONGITUD_TRASP_DIV_INT_INTER) {
				this.agregaErrorComun(errorResolver.getMessage("J0033"),
						validaNegocioVO.getMsgError());
				this.agregaErrorComun(((Integer) validaNegocioVO
								.getIndice().get(IMPORTE)).toString(),
								validaNegocioVO.getNumCampoError());
				this.agregaErrorComun(((Integer) validaNegocioVO
								.getIndice().get(DIVISA)).toString(),
								validaNegocioVO.getNumCampoError());
				noError = false;
			} 
			else {
				
				/* Se valida que el importe sea mayor a 0 */
				if (!validatorDivIntService.validaCantidades(validaNegocioVO
						.getCampos()[((Integer) validaNegocioVO.getIndice()
						.get(IMPORTE)).intValue()])) {
					this.agregaErrorComun(errorResolver.getMessage("J0034"),
							validaNegocioVO.getMsgError());
					this.agregaErrorComun(((Integer) validaNegocioVO
									.getIndice().get(IMPORTE)).toString(),
									validaNegocioVO.getNumCampoError());
					noError = false;
				}

				/* Se verifica que la divisa sea valida */
				if (!validaNegocioVO.isEsDivisaValidada()) {
					
					Divisa[] arregloDivisas = divisaDao.findDivisas();
					if(arregloDivisas!= null && arregloDivisas.length > 0){
						List listaDivisas = extraeClaveAlfabeticaDivisas(Arrays.asList(arregloDivisas));
						if(listaDivisas!= null && 
								!listaDivisas.contains(validaNegocioVO.getDivisa())){
							this.agregaErrorComun(errorResolver.getMessage("J0035"),
									validaNegocioVO.getMsgError());
							this.agregaErrorComun(
									((Integer) validaNegocioVO.getIndice().get(
											DIVISA)).toString(), validaNegocioVO
											.getNumCampoError());
							noError = false;
						}	
					}
					
				}
			}
			
		}
		
		validaNegocioVO.setNoError(noError);
		return validaNegocioVO;
		
	}
	
	/**
	 * Construye un List con las claves alfabeticas de las divisas
	 * @param listDivisas
	 * @return List
	 */
	private List<String> extraeClaveAlfabeticaDivisas(List<Divisa> listDivisas){
		
		log.info("Entrando a FileTransferServiceImpl.extraeClaveAlfabeticaDivisas()");
		
		List<String> listClaves = null;
		
		if (listDivisas != null) {
			
			listClaves = new ArrayList<String>();
			for (Iterator<Divisa> iterator = listDivisas.iterator(); iterator.hasNext();) {
				Divisa divisa = (Divisa) iterator.next();
				if(divisa != null && StringUtils.isNotBlank(divisa.getClaveAlfabetica())) {
					listClaves.add(divisa.getClaveAlfabetica().trim());	
				}
			}
		}
		return listClaves;
	}
    
    /**
     * Obtiene los campos correspondientes al layout de traspasos de
     * Divisi&oacute;n Internacional
     * 
     * @param cadena
     * @return Arreglo de String
     * @throws BusinessException
     */
    private String[] camposTraspasosDivInt(String cadena) throws BusinessException {
    	
        log.info("Entrando a FileTransferServiceImpl.camposTraspasosDivInt()");
        
        if (StringUtils.isBlank(cadena)) {
            throw new BusinessException(errorResolver.getMessage("J0026", new Object[] {
                " la cadena de informacion " }), "J0026");
        }

        /* Valida longitudes posibles */
        int tam = cadena.length();
        if (tam < LONGITUD_TRASP_DIV_INT_MIN || tam > LONGITUD_TRASP_DIV_INT_MAX
                || (tam < LONGITUD_TRASP_DIV_INT_INTER && tam > LONGITUD_TRASP_DIV_INT_MIN)) {
            return null;
        }

        List<Integer> posiciones = new ArrayList<Integer>();
        
        posiciones.add(2);
        posiciones.add(3);
        posiciones.add(4);
        posiciones.add(4);
        posiciones.add(7);
        posiciones.add(6);
        posiciones.add(4);
        posiciones.add(12);
        posiciones.add(2); /* Boveda */
        posiciones.add(20);
        posiciones.add(3);
        posiciones.add(1);
        posiciones.add(11);
        posiciones.add(11);
        posiciones.add(11);
        posiciones.add(35);
        posiciones.add(15);
        posiciones.add(40);
        posiciones.add(35);
        posiciones.add(40);
        posiciones.add(15);

        if (tam > LONGITUD_TRASP_DIV_INT_MIN) {
            posiciones.add(15);
            posiciones.add(3);
        }
        if (tam > LONGITUD_TRASP_DIV_INT_INTER) {
            int ultpos = tam - LONGITUD_TRASP_DIV_INT_INTER;
            posiciones.add(new Integer(ultpos));
        }

        return obtieneCampos(cadena, posiciones);
    }
    
    /**
     * Obtiene el arreglo de campos correspondiente al layout del filetransfer
     * porporcionado por la lista y las logitudes
     * 
     * @param cadena
     * @param posiciones
     * @return Arreglo de String
     * @throws BusinessException
     */
    private String[] obtieneCampos(String cadena, List posiciones) throws BusinessException {
    	
    	log.info("Entrando a FileTransferServiceImpl.obtieneCampos()");
    	
        if (StringUtils.isBlank(cadena)) {
            throw new BusinessException(errorResolver.getMessage("J0026", new Object[] {
                " la cadena de informacion " }));
        }
//        if (posiciones == null && posiciones.isEmpty()) {
//            throw new BusinessException(errorResolver.getMessage("J0046", new Object[] {
//                " de posiciones " }));
//        }

        String campo = null;
        int posIni = 0;
        int posFin = 0;
        String[] campos = new String[posiciones.size()];
        for (int i = 0; i < posiciones.size(); i++) {
        	
        	if(i != POSICIONES_CAMPO_BOVEDA){
                posFin = posIni + ((Integer) posiciones.get(i)).intValue();
                campo = cadena.substring(posIni, posFin);
                if (campo != null) {
                    campo = campo.toUpperCase();
                }
                campos[i] = campo;
                posIni = posFin;        		
        	}
        	else {
        		campos[i] = FactoryDivInt.BOVEDA_PREDETERMINADA.toString();
        	}
        	
        }

        return campos;
    }
	
	 /**
	 * Arma el VO con los datos necesarios de cada campo del archivo plano para
	 * poder presentarlos en pantalla
	 * 
	 * @param fileTransfer
	 * @param tipoProceso
	 * @param camposVO
	 * @return Una lista de instancias de la clase CampoArchivoVO
	 * @throws BusinessException
	 * @throws ParseException
	 */
	private List<Object> generaDatos(
			FileTransfer fileTransfer, String tipoProceso,
			CamposDivIntVO camposVO) throws BusinessException,
			ParseException {
		
		log.info("Entrando a FileTransferServiceImpl.generaDatos()");
		
		/* Valida parametros */
		if (fileTransfer == null) {
			throw new BusinessException(errorResolver.getMessage("J0026",
                                (Object)"el objeto fileTransferDivInt"));
		}
		if (StringUtils.isBlank(tipoProceso)) {
			throw new BusinessException(errorResolver.getMessage("J0026",
                                (Object)" el tipo de proceso "));
		}

		/* Obtiene lista de campos con error */
		String[] numeroCampoError = null;
		if (fileTransfer.getCamposError() != null) {
			numeroCampoError = fileTransfer.getCamposError().split("-");
			validatorService.validaDTONoNulo(numeroCampoError, " numero de campo con error ");
		}
		List<String> listaCamposError = new ArrayList<String>();
		for (int i = 0; numeroCampoError != null && i < numeroCampoError.length; i++) {
			if (StringUtils.isNotBlank(numeroCampoError[i])
					&& !listaCamposError.contains(numeroCampoError[i])) {
				listaCamposError.add(numeroCampoError[i]);
			}
		}
		
		/* Obtiene el mapa de indices de campos */
		Map<Object, Object> indiceCampo = new TreeMap<Object, Object>();
		indiceCampo = FactoryDivInt.indicesNombresTraspasosDivInt(true);

		/* Genera el objeto de salida del servicio */
		List<Object> listaCamposArchivo = new ArrayList<Object>();

		boolean error = false;

		/* Verificacion y setteo de los datos del traspasante */
		error = listaCamposError.contains(String.valueOf(indiceCampo.get(ID_INST_VEND)));
		CampoArchivoVO campoArchivoVO = FactoryDivInt.generaArchivoVO(
				fileTransfer.getIdInstTrasp().length(), 
				ID_INST_VEND, error, fileTransfer.getIdInstTrasp(), null);
		listaCamposArchivo.add(campoArchivoVO);
		camposVO.setIdInstTrasp(fileTransfer.getIdInstTrasp());

		error = listaCamposError.contains(String.valueOf(indiceCampo.get(FOLIO_INST_VEND)));
		campoArchivoVO = FactoryDivInt.generaArchivoVO(
				fileTransfer.getFolioInstTrasp().length(), 
				FOLIO_INST_VEND, error, fileTransfer.getFolioInstTrasp(), null);
		listaCamposArchivo.add(campoArchivoVO);
		camposVO.setFolioInstTrasp(fileTransfer.getFolioInstTrasp());

		error = listaCamposError.contains(String.valueOf(indiceCampo.get(CUENTA_VEND)));
		campoArchivoVO = FactoryDivInt.generaArchivoVO(
				fileTransfer.getCuentaTrasp().length(), 
				CUENTA_VEND, error, fileTransfer.getCuentaTrasp(), null);
		listaCamposArchivo.add(campoArchivoVO);
		camposVO.setCuentaTrasp(fileTransfer.getCuentaTrasp());

		/* Verificacion y setteo de los datos del receptor */
		error = listaCamposError.contains(String.valueOf(indiceCampo.get(ID_INST_VEND)));
		campoArchivoVO = FactoryDivInt.generaArchivoVO(
				fileTransfer.getIdInstRecep().length(), 
				ID_RECEP, error,fileTransfer.getIdInstRecep(), null);
		listaCamposArchivo.add(campoArchivoVO);
		camposVO.setIdInstRecep(fileTransfer.getIdInstRecep());

		error = listaCamposError.contains(String.valueOf(indiceCampo.get(FOLIO_INST_VEND)));
		campoArchivoVO = FactoryDivInt.generaArchivoVO(
				fileTransfer.getFolioInstRecep().length(), 
				FOLIO_RECEP, error, fileTransfer.getFolioInstRecep(), null);
		listaCamposArchivo.add(campoArchivoVO);
		camposVO.setFolioInstRecep(fileTransfer.getFolioInstRecep());

		error = listaCamposError.contains(String.valueOf(indiceCampo.get(CUENTA_VEND)));
		campoArchivoVO = FactoryDivInt.generaArchivoVO(
				fileTransfer.getCuentaRecep().length(), 
				CUENTA_RECEP, error, fileTransfer.getCuentaRecep(), null);
		listaCamposArchivo.add(campoArchivoVO);
		camposVO.setCuentaRecep(fileTransfer.getCuentaRecep());
		
		/* Verificacion y setteo de los atributos de la clave-valor */
		error = listaCamposError.contains(String.valueOf(indiceCampo.get(TV)));
		campoArchivoVO = FactoryDivInt.generaArchivoVO(
				fileTransfer.getTv(),
				TV, error, fileTransfer.getTv(), null);
		listaCamposArchivo.add(campoArchivoVO);

		error = listaCamposError.contains(String.valueOf(indiceCampo.get(EMISORA)));
		campoArchivoVO = FactoryDivInt.generaArchivoVO(
				fileTransfer.getEmisora(),
				EMISORA, error, fileTransfer.getEmisora(), null);
		listaCamposArchivo.add(campoArchivoVO);

		error = listaCamposError.contains(String.valueOf(indiceCampo.get(SERIE)));
		campoArchivoVO = FactoryDivInt.generaArchivoVO(
				fileTransfer.getSerie(), 
				SERIE, error, fileTransfer.getSerie(), null);
		listaCamposArchivo.add(campoArchivoVO);

		error = listaCamposError.contains(String.valueOf(indiceCampo.get(CUPON)));
		campoArchivoVO = FactoryDivInt.generaArchivoVO(
				fileTransfer.getCupon(), 
				CUPON, error, fileTransfer.getCupon(), null);
		listaCamposArchivo.add(campoArchivoVO);

		camposVO.setTv(fileTransfer.getTv());
		camposVO.setEmisora(fileTransfer.getEmisora());
		camposVO.setSerie(fileTransfer.getSerie());
		camposVO.setCupon(fileTransfer.getCupon());

		error = listaCamposError.contains(String.valueOf(indiceCampo.get(ISIN)));
		campoArchivoVO = FactoryDivInt.generaArchivoVO(
				fileTransfer.getIsin(),
				ISIN, error, fileTransfer.getIsin(), null);
		listaCamposArchivo.add(campoArchivoVO);
		camposVO.setIsin(fileTransfer.getIsin());
		
		/*  Verificacion y setteo de la Boveda */
		error = listaCamposError.contains(String.valueOf(indiceCampo.get(ID_BOVEDA)));
//		BigInteger idBoveda = null;
		campoArchivoVO = FactoryDivInt.generaArchivoVO(
				fileTransfer.getIdBoveda().toString().length(),
				ID_BOVEDA, error, fileTransfer.getIdBoveda(), null);
		listaCamposArchivo.add(campoArchivoVO);
		camposVO.setIdBoveda(fileTransfer.getIdBoveda());
		
		/*  Verificacion y setteo de la cantidad */
		error = listaCamposError.contains(String.valueOf(indiceCampo.get(CANTIDAD)));
		BigInteger cantidad = null;
		if (!error) {
			cantidad = new BigInteger(fileTransfer.getCantidadTitulos().trim());
		}
		campoArchivoVO = FactoryDivInt.generaArchivoVO(
				fileTransfer.getCantidadTitulos().length(), 
				CANTIDAD, error, cantidad != null ? cantidad : fileTransfer.getCantidadTitulos(), 
						"###################0");
		listaCamposArchivo.add(campoArchivoVO);
		camposVO.setCantidad(cantidad);

		/* Verificacion y setteo del tipo de operacion */
		error = listaCamposError.contains(String.valueOf(indiceCampo.get(TIPO_OPER)));
		campoArchivoVO = FactoryDivInt.generaArchivoVO(
				fileTransfer.getTipoOperacion().length(), 
				TIPO_OPER, error, fileTransfer.getTipoOperacion(), null);
		listaCamposArchivo.add(campoArchivoVO);
		camposVO.setTipoOperacion(fileTransfer.getTipoOperacion());

		/* Verificacion y setteo del tipo de movimiento */
		error = listaCamposError.contains(String.valueOf(indiceCampo.get(TIPO_MOV)));
		campoArchivoVO = FactoryDivInt.generaArchivoVO(
				fileTransfer.getTipoMovto(), 
				TIPO_MOV, error, fileTransfer.getTipoMovto(), null);
		listaCamposArchivo.add(campoArchivoVO);
		camposVO.setTipoMovimiento(fileTransfer.getTipoMovto());

		/* Verificacion y setteo de la fecha de notificacion */
		error = listaCamposError.contains(String.valueOf(indiceCampo.get(FECHA_NOT)));
		Date fecha = null;
		String fechaFormateada = null;
		if (!error) {
			fechaFormateada = FactoryDivInt.fechaArchivo2fechaFileTransfer(
					fileTransfer.getFechaNotificacion());
			if(StringUtils.isNotBlank(fechaFormateada)){
				fecha = DateUtils.convierteStringToDate2(fechaFormateada);	
			}
		}
		int tamFechaNotificacion = fileTransfer.getFechaNotificacion() != null ? 
				fileTransfer.getFechaNotificacion().length() : 0; 
		campoArchivoVO = FactoryDivInt.generaArchivoVO(tamFechaNotificacion, 
				FECHA_NOT, error, fecha != null ? fecha : fileTransfer.getFechaNotificacion(), 
						"dd/MM/yyyy");
		listaCamposArchivo.add(campoArchivoVO);
		camposVO.setFechaNotificacion(fecha);

		/* Verificacion y setteo de la fecha de liquidacion */
		error = listaCamposError.contains(String.valueOf(indiceCampo.get(FECHA_LIQ)));
		fecha = null;
		fechaFormateada = null;
		if (!error) {
			fechaFormateada = FactoryDivInt.fechaArchivo2fechaFileTransfer(
					fileTransfer.getFechaLiquidacion());
			if(StringUtils.isNotBlank(fechaFormateada)){
				fecha = DateUtils.convierteStringToDate2(fechaFormateada);	
			}
		}
		int tamFechaLiquidacion = fileTransfer.getFechaLiquidacion() != null ? 
				fileTransfer.getFechaLiquidacion().length() : 0; 
		campoArchivoVO = FactoryDivInt.generaArchivoVO(tamFechaLiquidacion, 
				FECHA_LIQ, error, fecha != null ? fecha : fileTransfer.getFechaLiquidacion(), 
						"dd/MM/yyyy");
		listaCamposArchivo.add(campoArchivoVO);
		camposVO.setFechaLiquidacion(fecha);

		/* Verificacion y setteo de la fecha de operacion */
		error = listaCamposError.contains(String.valueOf(indiceCampo.get(FECHA_OPER)));
		fecha = null;
		fechaFormateada = null;
		if (!error) {
			fechaFormateada = FactoryDivInt.fechaArchivo2fechaFileTransfer(
					fileTransfer.getFechaOperacion());
			if(StringUtils.isNotBlank(fechaFormateada)){
				fecha = DateUtils.convierteStringToDate2(fechaFormateada);	
			}
		}
		int tamFechaOperacion = fileTransfer.getFechaOperacion() != null ? 
				fileTransfer.getFechaOperacion().length() : 0; 		
		campoArchivoVO = FactoryDivInt.generaArchivoVO(tamFechaOperacion, 
				FECHA_OPER, error, fecha != null ? fecha : fileTransfer.getFechaOperacion(), 
						"dd/MM/yyyy");
		listaCamposArchivo.add(campoArchivoVO);
		camposVO.setFechaOperacion(fecha);

		/* Verificacion de los datos del custodio */
		error = listaCamposError.contains(String.valueOf(indiceCampo.get(CUSTODIO)));
		campoArchivoVO = FactoryDivInt.generaArchivoVO(
				fileTransfer.getDescCustodio(), 
				CUSTODIO, error, fileTransfer.getDescCustodio(), null);
		listaCamposArchivo.add(campoArchivoVO);
		camposVO.setDescCustodio(fileTransfer.getDescCustodio());

		/* Verificacion de los datos de la contraparte */
		error = listaCamposError.contains(String.valueOf(indiceCampo.get(CUENTA_CONTRA)));
		campoArchivoVO = FactoryDivInt.generaArchivoVO(
				fileTransfer.getCuentaContraparte(), 
				CUENTA_CONTRA, error, fileTransfer.getCuentaContraparte(), null);
		listaCamposArchivo.add(campoArchivoVO);
		camposVO.setCuentaContraparte(fileTransfer.getCuentaContraparte());

		error = listaCamposError.contains(String.valueOf(indiceCampo.get(CONTRAPARTE)));
		campoArchivoVO = FactoryDivInt.generaArchivoVO(
				fileTransfer.getDescContraparte(), 
				CONTRAPARTE, error, fileTransfer.getDescContraparte(), null);
		listaCamposArchivo.add(campoArchivoVO);
		camposVO.setDescContraparte(fileTransfer.getDescContraparte());

		/* Verificacion de los datos del depositante */
		error = listaCamposError.contains(String.valueOf(indiceCampo.get(DEPOSITANTE)));
		campoArchivoVO = FactoryDivInt.generaArchivoVO(
				fileTransfer.getNombreDepositante(), 
				DEPOSITANTE, error, fileTransfer.getNombreDepositante(), null);
		listaCamposArchivo.add(campoArchivoVO);
		camposVO.setNombreDepositante(fileTransfer.getNombreDepositante());

		/* Verificacion de los datos del beneficiario */
		error = listaCamposError.contains(String.valueOf(indiceCampo.get(NOMBRE_CUENTA_BEN)));
		campoArchivoVO = FactoryDivInt.generaArchivoVO(
				fileTransfer.getNombreCuentaBeneficiario(), 
				NOMBRE_CUENTA_BEN, error, fileTransfer.getNombreCuentaBeneficiario(), null);
		listaCamposArchivo.add(campoArchivoVO);
		camposVO.setNombreCuentaBeneficiario(fileTransfer.getNombreCuentaBeneficiario());

		error = listaCamposError.contains(String.valueOf(indiceCampo.get(NUMERO_CUENTA_BEN)));
		campoArchivoVO = FactoryDivInt.generaArchivoVO(fileTransfer.getNumeroCuentaBeneficiario(), 
				NUMERO_CUENTA_BEN, error, fileTransfer.getNumeroCuentaBeneficiario(), null);
		listaCamposArchivo.add(campoArchivoVO);
		camposVO.setNumeroCuentaBeneficiario(fileTransfer.getNumeroCuentaBeneficiario());

		/* Verificacion de los datos del importe y divisa */
		error = listaCamposError.contains(String.valueOf(indiceCampo.get(IMPORTE)));
		BigDecimal importe = null;
		if (!error && StringUtils.isNotBlank(fileTransfer.getImporte())) {
			importe = new BigDecimal(fileTransfer.getImporte().trim());
		}
		int tam = StringUtils.isNotBlank(fileTransfer.getImporte()) ? 
				fileTransfer.getImporte().length() : 15;
		campoArchivoVO = FactoryDivInt.generaArchivoVO(tam, 
				IMPORTE, error, importe != null ? importe : fileTransfer.getImporte(), 
						"###############0.0##");
		listaCamposArchivo.add(campoArchivoVO);
		camposVO.setImporte(importe);

		error = listaCamposError.contains(String.valueOf(indiceCampo.get(DIVISA)));
		campoArchivoVO = FactoryDivInt.generaArchivoVO(3, 
				DIVISA, error, fileTransfer.getDivisa(), null);
		listaCamposArchivo.add(campoArchivoVO);
		camposVO.setDivisa(fileTransfer.getDivisa());

		/* Verificacion de las instrucciones especiales */
		error = listaCamposError.contains(String.valueOf(indiceCampo.get(INST_ESP)));
		tam = fileTransfer.getInstruccEspeciales() != null ? 
				fileTransfer.getInstruccEspeciales().length() : 0;
		campoArchivoVO = FactoryDivInt.generaArchivoVO(tam, 
				INST_ESP, error, fileTransfer.getInstruccEspeciales(), null);
		listaCamposArchivo.add(campoArchivoVO);
		camposVO.setInstrucEsp(fileTransfer.getInstruccEspeciales());

		camposVO.setTipoMensaje(fileTransfer.getTipoMensaje());
		camposVO.setEstatus(fileTransfer.getEstatusOper());
		camposVO.setBajaLogica(fileTransfer.getBajaLogica());
		camposVO.setMercado(fileTransfer.getMercado());

		return listaCamposArchivo;
	}

    /**
     * Agrega la cadena String (mensaje) recibida al Stringbuffer recibido.
     * @param mensaje
     * @param buffer
     */
    private void agregaErrorComun(String mensaje, StringBuffer msgError) {
    	
        log.info("Entrando al metodo agregaErrorComun()");

        msgError.append(mensaje);
        msgError.append(GUION);
    }
    
	/* Setters */

	/**
	 * @param validatorService
	 */
	public void setValidatorService(ValidatorService validatorService) {
		this.validatorService = validatorService;
	}

	/**
	 * @param errorResolver
	 *            the errorResolver to set
	 */
	public void setErrorResolver(MessageResolver errorResolver) {
		this.errorResolver = errorResolver;
	}

	/**
	 * @param dateUtilService the dateUtilService to set
	 */
	public void setDateUtilService(DateUtilService dateUtilService) {
		this.dateUtilService = dateUtilService;
	}

	/**
	 * @param fileTransferDao the fileTransferDao to set
	 */
	public void setFileTransferDao(FileTransferDao fileTransferDao) {
		this.fileTransferDao = fileTransferDao;
	}

	/**
	 * @param divisaDao the divisaDao to set
	 */
	public void setDivisaDao(DivisaDao divisaDao) {
		this.divisaDao = divisaDao;
	}

	/**
	 * @param validatorDivIntService the validatorDivIntService to set
	 */
	public void setValidatorDivIntService(
			ValidatorDivIntService validatorDivIntService) {
		this.validatorDivIntService = validatorDivIntService;
	}

	/**
	 * @param posicionNombradaDao the posicionNombradaDao to set
	 */
	public void setPosicionNombradaDao(PosicionNombradaDao posicionNombradaDao) {
		this.posicionNombradaDao = posicionNombradaDao;
	}

	/**
	 * @param detalleCustodio the detalleCustodio to set
	 */
//	public void setDetalleCustodio(List detalleCustodio) {
//		this.detalleCustodio = detalleCustodio;
//	}

	/**
	 * @param utilDivIntService the utilDivIntService to set
	 */
	public void setUtilDivIntService(UtilDivIntService utilDivIntService) {
		this.utilDivIntService = utilDivIntService;
	}

	/**
	 * @param cuentaNombradaDao the cuentaNombradaDao to set
	 */
	public void setCuentaNombradaDao(CuentaNombradaDao cuentaNombradaDao) {
		this.cuentaNombradaDao = cuentaNombradaDao;
	}

	/**
	 * @param catBicDao the catBicDao to set
	 */
	public void setCatBicDao(CatBicDao catBicDao) {
		this.catBicDao = catBicDao;
	}

	/**
	 * @param sicDetalleDao the sicDetalleDao to set
	 */
	public void setSicDetalleDao(SicDetalleDao sicDetalleDao) {
		this.sicDetalleDao = sicDetalleDao;
	}

	/**
	 * @param sicEmisionDao the sicEmisionDao to set
	 */
	public void setSicEmisionDao(SicEmisionDao sicEmisionDao) {
		this.sicEmisionDao = sicEmisionDao;
	}

	/**
	 * @param divisionInternacionalService the divisionInternacionalService to set
	 */
	public void setDivisionInternacionalService(
			DivisionInternacionalService divisionInternacionalService) {
		this.divisionInternacionalService = divisionInternacionalService;
	}

	/**
	 * @param emisionDao the emisionDao to set
	 */
	public void setEmisionDao(EmisionDao emisionDao) {
		this.emisionDao = emisionDao;
	}

	/**
	 * @param bovedaDao the bovedaDao to set
	 */
	public void setBovedaDao(BovedaDao bovedaDao) {
		this.bovedaDao = bovedaDao;
	}
	
}
