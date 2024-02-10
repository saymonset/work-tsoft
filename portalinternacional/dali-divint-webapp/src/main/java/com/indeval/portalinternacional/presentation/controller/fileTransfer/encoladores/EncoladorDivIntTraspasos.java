/*
 * Copyright (c) 2005-2007 Bursatec. All Rights Reserved
 */
package com.indeval.portalinternacional.presentation.controller.fileTransfer.encoladores;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bursatec.seguridad.presentation.constants.SeguridadConstants;
import com.indeval.portaldali.middleware.services.util.ConsultaCatalogoService;
import com.indeval.portaldali.middleware.services.util.ConvertBO2VO;
import com.indeval.portaldali.middleware.services.util.DateUtilService;
import com.indeval.portaldali.middleware.services.util.UtilService;
import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.EmisionVO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.dao.common.CuentaNombradaDao;
import com.indeval.portaldali.persistence.dao.common.CuponDao;
import com.indeval.portaldali.persistence.dao.common.EmisionDao;
import com.indeval.portaldali.persistence.modelo.Cupon;
import com.indeval.portaldali.persistence.modelo.Emision;
import com.indeval.portalinternacional.common.util.IsoHelper;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService;
import com.indeval.portalinternacional.middleware.services.util.UtilDivIntService;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.CatBic;
import com.indeval.portalinternacional.middleware.servicios.modelo.SicDetalle;
import com.indeval.portalinternacional.middleware.servicios.vo.CamposDivIntVO;
import com.indeval.portalinternacional.middleware.servicios.vo.GrabaOperacionParams;
import com.indeval.portalinternacional.middleware.servicios.vo.RegistroProcesadoVO;
import com.indeval.portalinternacional.middleware.servicios.vo.TotalesProcesoVO;
import com.indeval.portalinternacional.persistence.dao.CatBicDao;
import com.indeval.portalinternacional.persistence.dao.SicDetalleDao;
import com.indeval.protocolofinanciero.api.ProtocoloFinancieroException;
import com.indeval.protocolofinanciero.api.vo.TraspasoContraPagoVO;
import com.indeval.protocolofinanciero.api.vo.TraspasoLibrePagoVO;

/**
 * Clase con m&eacute;todos para firmar y env&iacute;ar a protocolo los filetransfer de Division Internacional
 * 
 * @author Esteban Herrera
 *
 */
@SuppressWarnings("unchecked")
public class EncoladorDivIntTraspasos implements IEncoladorProtocoloFinanciero {

	/** DAO para emisiones */
    private EmisionDao emisionDao = null;

    /** Clase de utileria para fechas */
    private DateUtilService dateUtilService = null;
    
    /** Clase de utileria para ISOs */
    private IsoHelper isoHelper = null;
    
    /** Servicio con metodos de utileria */
    
    
    private UtilService utilService = null;
    
    /**
     * CuponDao 
     */
    private CuponDao cuponDao = null;
    
    /**
	 * UtilDivIntService
	 */
	private UtilDivIntService utilDivIntService = null;
	
	/**
	 * CatBicDao 
	 */
	private CatBicDao catBicDao = null;
	
	/** Servicio para obtener la institucion */
	private ConsultaCatalogoService consultaCatService;
	
	/**
	 * SicDetalleDao
	 */
	private SicDetalleDao sicDetalleDao = null;

    /**
     * Servicio para realizar la Inserci&oacute;n de la Operaci&oacute;n en la
     * Base de Datos
     */
    private DivisionInternacionalService divisionInternacionalService;
    
    
    private CuentaNombradaDao cuentaNombradaDao = null;
	/**
	 * @see com.indeval.portalinternacional.presentation.controller.fileTransfer.encoladores.IEncoladorProtocoloFinanciero#obtenerISOs(com.indeval.portalinternacional.middleware.servicios.vo.TotalesProcesoVO)
	 */
	public Map<String, Object> obtenerISOs(TotalesProcesoVO totProcVO) {
    	List<String> resultados = new ArrayList<String>();    
    	List lista = new ArrayList();
    	Map<String, Object> mapa = new HashMap<String, Object>();    	
    	PaginaVO pag = totProcVO.getPaginaVO();
        List l = pag.getRegistros();
        RegistroProcesadoVO reg;

        for (int i = 0; i < l.size(); i++) {
            reg = (RegistroProcesadoVO) l.get(i);
            // El registro no tiene errores
            if (!reg.getEstado().trim().equals(Constantes.ESTADO_ERROR)) {
                // Esta lista contiene cada uno de los campos que conforman el registro
                CamposDivIntVO camposDivIntVO = reg.getCamposDivIntVO();                
                if (camposDivIntVO.getTipoMensaje().equalsIgnoreCase(Constantes.TMSJ_542)) {
                	TraspasoLibrePagoVO librePagoVO = obtenerTraspasoLibrePagoVO(camposDivIntVO);
                	resultados.add(isoHelper.creaIsoSicTlp(librePagoVO, false));                	
                	lista.add(librePagoVO);                
                }else if(camposDivIntVO.getTipoMensaje().equalsIgnoreCase(Constantes.TMSJ_543)){                	
                	TraspasoContraPagoVO trasContra = obtenerTraspasoContraPagoVO(camposDivIntVO);                	                	                	
                	resultados.add(isoHelper.creaIsoSicDvp(trasContra, false));
                	lista.add(trasContra);                	                                
                }else if(camposDivIntVO.getTipoMensaje().equalsIgnoreCase(Constantes.TMSJ_540)){
                	TraspasoLibrePagoVO librePagoVO = obtenerTraspasoLibrePagoVO(camposDivIntVO);
                	resultados.add(isoHelper.creaIsoSicTlp(librePagoVO, true));                	
                	lista.add(librePagoVO);                	
                }else if( camposDivIntVO.getTipoMensaje().equalsIgnoreCase(Constantes.TMSJ_541)){                 	
                	TraspasoContraPagoVO trasContra = obtenerTraspasoContraPagoVO(camposDivIntVO);                	                	
                	resultados.add(isoHelper.creaIsoSicDvp(trasContra, true));               
                	lista.add(trasContra);                	
                }
            }
        }        
        mapa.put(ID_LISTA_ISOS, resultados);
		mapa.put(ID_LISTA_OBJETOS, lista);		
        return mapa;
    }

    /**
     * @see com.indeval.portalinternacional.presentation.controller.fileTransfer.encoladores.IEncoladorProtocoloFinanciero#firmayEncola(com.indeval.portalinternacional.middleware.servicios.vo.TotalesProcesoVO, java.util.List, java.util.List)
     */
    public Map<BigInteger, Integer> firmayEncola(TotalesProcesoVO totProcVO, List lista, List<String> isosFirmados, String ticket) throws BusinessException, ProtocoloFinancieroException {
        LinkedHashMap mpRegistrosEncolados = new LinkedHashMap();
        PaginaVO pag = totProcVO.getPaginaVO();
        List l = pag.getRegistros();
        RegistroProcesadoVO reg;
        int index = 0;
        for (int i = 0; i < l.size(); i++) {
            reg = (RegistroProcesadoVO) l.get(i);            
            // El registro no tiene errores
            if (!reg.getEstado().trim().equals(Constantes.ESTADO_ERROR)) {            	
            	if(lista != null && !lista.isEmpty() &&  index < lista.size()) {
	                // Esta lista contiene cada uno de los campos que conforman el registro
	                CamposDivIntVO camposDivIntVO = reg.getCamposDivIntVO();	                	                
	                if (camposDivIntVO.getTipoMensaje().equalsIgnoreCase(Constantes.TMSJ_542)
	                        || camposDivIntVO.getTipoMensaje().equalsIgnoreCase(Constantes.TMSJ_540)){	                        
	                	TraspasoLibrePagoVO librePagoVO = (TraspasoLibrePagoVO)lista.get(index);	                		                		                
	                	try {
	                		HashMap<String, Object> valoresAdicionales = llenaValoresAdicionales(camposDivIntVO,librePagoVO);		                
		                	//se agrega el ticket al mapa
		                	valoresAdicionales.put(SeguridadConstants.USR_CREDENCIAL, ticket);	
	                    	GrabaOperacionParams grabaOperacionParams = new GrabaOperacionParams();
	                    	grabaOperacionParams.setTraspasoLibrePagoVO(librePagoVO);
	                    	grabaOperacionParams.setDatosAdicionales(valoresAdicionales);	                
	                    	grabaOperacionParams.setOrigenRegistro(Constantes.ID_ORIGEN_DIV_INTERNACIONAL);	                    
	                    	if(isosFirmados != null && !isosFirmados.isEmpty() &&  index < isosFirmados.size()) {
	                    		grabaOperacionParams.setIsoFirmado(isosFirmados.get(index));
	                    	}
	                    	if(camposDivIntVO.getTipoMensaje().equalsIgnoreCase(Constantes.TMSJ_540)){
	                    		grabaOperacionParams.setRecepcion(true);
	                    	}
	                    	divisionInternacionalService.grabaOperacion(grabaOperacionParams);	                    	                    
	                        mpRegistrosEncolados.put(reg.getConsec(), new Integer(1));
		                    }
		                    catch (Exception e) {
		                        mpRegistrosEncolados.put(reg.getConsec(), new Integer(0));
		                        LoggerFactory.getLogger(this.getClass()).error("Error al grabar registro de operacion", e);
		                    }	                    
		                    index++;
	                }  
	                else if(camposDivIntVO.getTipoMensaje().equalsIgnoreCase(Constantes.TMSJ_543)
	                        || camposDivIntVO.getTipoMensaje().equalsIgnoreCase(Constantes.TMSJ_541)){
	                		TraspasoContraPagoVO traspasoContra = (TraspasoContraPagoVO)lista.get(index);	                		
	                		try {		                			          
		                		HashMap<String, Object> valoresAdicionales = llenaValoresAdicionales(camposDivIntVO,traspasoContra);		                
			                	//se agrega el ticket al mapa
			                	valoresAdicionales.put(SeguridadConstants.USR_CREDENCIAL, ticket);	
		                    	GrabaOperacionParams grabaOperacionParams = new GrabaOperacionParams();		                    	
		                    	grabaOperacionParams.setTraspasoContraPagoVO(traspasoContra);
		                    	grabaOperacionParams.setDatosAdicionales(valoresAdicionales);	                
		                    	grabaOperacionParams.setOrigenRegistro(Constantes.ID_ORIGEN_DIV_INTERNACIONAL);	                    
		                    	if(isosFirmados != null && !isosFirmados.isEmpty() &&  index < isosFirmados.size()) {
		                    		grabaOperacionParams.setIsoFirmado(isosFirmados.get(index));
		                    	}
		                    	if(camposDivIntVO.getTipoMensaje().equalsIgnoreCase(Constantes.TMSJ_541)){
		                    		grabaOperacionParams.setRecepcion(true);
		                    	}
		                    	divisionInternacionalService.grabaOperacion(grabaOperacionParams);	                    	                    
		                        mpRegistrosEncolados.put(reg.getConsec(), new Integer(1));
		                    }
		                    catch (Exception e) {
		                        mpRegistrosEncolados.put(reg.getConsec(), new Integer(0));
		                        LoggerFactory.getLogger(this.getClass()).error("Error al grabar registro de operacion", e);
		                    }	                    
		                    index++;	                	                                	                    
	                }	              	              	                
                }
            }
        }
        return mpRegistrosEncolados;
    }
    
    /**
     * Crea un objeto de tipo TraspasoContraPagoVO con los datos necesarios
     * 
     * @param camposDivIntVO VO con los datos a obtener
     * @return Objeto de tipo TraspasoContraPagoVO
     */
    private TraspasoContraPagoVO obtenerTraspasoContraPagoVO(CamposDivIntVO camposDivIntVO) {
    
    	TraspasoContraPagoVO contraPagoVO = new TraspasoContraPagoVO();    	
    	contraPagoVO.setCantidadTitulos(camposDivIntVO.getCantidad().longValue());
    	contraPagoVO.setTipoValor(camposDivIntVO.getTv().trim().toUpperCase());
    	contraPagoVO.setCupon(camposDivIntVO.getCupon().trim().toUpperCase());
    	contraPagoVO.setDivisa(camposDivIntVO.getDivisa());
    	contraPagoVO.setEmisora(camposDivIntVO.getEmisora().trim().toUpperCase());
    	contraPagoVO.setSerie(camposDivIntVO.getSerie().trim().toUpperCase());
    	contraPagoVO.setTipoInstruccion(Constantes.TIPO_OPER_V);
    	contraPagoVO.setCuentaContraparte(camposDivIntVO.getCuentaContraparte().trim());
    	contraPagoVO.setIdFolioCtaTraspasante(camposDivIntVO.getIdInstTrasp().concat(
                camposDivIntVO.getFolioInstTrasp()).concat(
                        camposDivIntVO.getCuentaTrasp()));
    	contraPagoVO.setIdFolioCtaReceptora(camposDivIntVO.getIdInstRecep().concat(
                camposDivIntVO.getFolioInstRecep()).concat(
                        camposDivIntVO.getCuentaRecep()));     	 
        contraPagoVO.setISIN(StringUtils.stripToNull(camposDivIntVO.getIsin()));        	        
    	 /* se obtiene el TV, Emisora, Serie y el Cupon sino lo trae el File, para ingresarlos en el Iso*/
        EmisionVO emisionVO = null;
        if (StringUtils.isBlank(contraPagoVO.getTipoValor())
                || StringUtils.isBlank(contraPagoVO.getEmisora())
                || StringUtils.isBlank(contraPagoVO.getSerie())
                || StringUtils.isBlank(contraPagoVO.getCupon())) {
            emisionVO =  new EmisionVO(camposDivIntVO.getIsin());
            Emision emision = emisionDao.findEmisionLiberada(new EmisionVO(camposDivIntVO.getIsin()));
            
            if(emision != null) {
	            emisionVO = ConvertBO2VO.crearEmisionVO(emision);
	            Cupon cupon = cuponDao.findCuponByIdEmision(emision.getIdEmision());
	            if(emisionVO != null) {
	            	contraPagoVO.setTipoValor(emisionVO.getTv());
	            	contraPagoVO.setEmisora(emisionVO.getEmisora());
	            	contraPagoVO.setSerie(emisionVO.getSerie());
	            	contraPagoVO.setCupon(cupon.getClaveCupon());
	            }
            }            
        }
        contraPagoVO.setFechaRegistro(getHoy());
        contraPagoVO.setFechaConcertacion(camposDivIntVO.getFechaOperacion());
        contraPagoVO.setFechaLiquidacion(parseFechaAbsoluta(camposDivIntVO.getFechaLiquidacion()));       	    	    	   	   
    	contraPagoVO.setMonto(camposDivIntVO.getImporte());    	
    	contraPagoVO.setReferenciaMensaje(utilService.getFolio(Constantes.SEQ_REFERENCIA_MENSAJE).toString());
    	contraPagoVO.setReferenciaOperacion(utilService.getFolio(Constantes.SEQ_FOLIO_CONTROL).toString());    	    	    	    	          
    	 /* se busca el BicProd para la descripcion del custodio*/
        Long[] agenteVOs = null;
        List<CatBic> catBics = null;
        if(emisionVO == null){
        	emisionVO = new EmisionVO(contraPagoVO.getTipoValor(),contraPagoVO.getEmisora(),contraPagoVO.getSerie(),contraPagoVO.getCupon());
        }      
        agenteVOs = utilDivIntService.obtieneCatBics(emisionVO); 
        /*se modifica para que solo devuelva un catBic, sele agrega parametro descripcionCustodio*/
     	catBics = catBicDao.findCatBic(agenteVOs,camposDivIntVO.getDescCustodio().trim());
        agenteVOs = new Long[catBics.size()];
		for (int i = 0; i < catBics.size(); i++) {
			agenteVOs[i] = catBics.get(i).getIdCatbic();			
			String bicProd = catBics.get(i).getDetalleCustodio();
			if(bicProd.equalsIgnoreCase(camposDivIntVO.getDescCustodio().trim())){
				contraPagoVO.setCustodio(catBics.get(i).getBicProd());			
			}
		}
		/*se busca el BicDepLiq para la descripcion del depositante ingresado en el file*/
		SicDetalle detalle = (SicDetalle) sicDetalleDao.findSicDetalle(agenteVOs,camposDivIntVO.getNombreDepositante().trim());
		contraPagoVO.setLugarDeLiquidacion(detalle.getBicDepLiq());
		//CAMPOS NUEVOS
		if(StringUtils.isNotBlank(camposDivIntVO.getInstrucEsp())){
			contraPagoVO.setInstruccionesEspeciales("LP:" + camposDivIntVO.getLiquidacionParcial() + camposDivIntVO.getInstrucEsp());
		}
		
    	contraPagoVO.setDescripcionBeneficiarioFinal(camposDivIntVO.getNombreCuentaBeneficiario().trim());
    	contraPagoVO.setDescripcionCuentaContraparte(camposDivIntVO.getDescContraparte().trim());
    	
    	contraPagoVO.setCuentaBeneficiarioFinal(camposDivIntVO.getNumeroCuentaBeneficiario());		    	    	    	    	       	   
    	contraPagoVO.setBoveda(contraPagoVO.getCustodio());
    	contraPagoVO.setBovedaEfectivo(contraPagoVO.getCustodio());
    	return contraPagoVO;
    }

    /**
     * Crea un objeto de tipo TraspasoLibrePagoVO con los datos necesarios
     * 
     * @param camposDivIntVO VO con los datos a obtener
     * @return Objeto de tipo TraspasoLibrePagoVO
     */
    private TraspasoLibrePagoVO obtenerTraspasoLibrePagoVO(CamposDivIntVO camposDivIntVO) {
    	TraspasoLibrePagoVO librePagoVO = new TraspasoLibrePagoVO();    	
        librePagoVO.setTipoInstruccion(Constantes.TIPO_OPER_T);
        librePagoVO.setIdFolioCtaTraspasante(camposDivIntVO.getIdInstTrasp().concat(
                camposDivIntVO.getFolioInstTrasp()).concat(
                camposDivIntVO.getCuentaTrasp()));
        librePagoVO.setIdFolioCtaReceptora(camposDivIntVO.getIdInstRecep().concat(
                camposDivIntVO.getFolioInstRecep()).concat(
                camposDivIntVO.getCuentaRecep()));
        librePagoVO.setTipoValor(camposDivIntVO.getTv().trim().toUpperCase());
        librePagoVO.setEmisora(camposDivIntVO.getEmisora().trim().toUpperCase());
        librePagoVO.setSerie(camposDivIntVO.getSerie().trim().toUpperCase());
        librePagoVO.setCupon(camposDivIntVO.getCupon().trim().toUpperCase());
        librePagoVO.setCantidadTitulos(camposDivIntVO.getCantidad().longValue());
        librePagoVO.setFechaRegistro(getHoy());
        librePagoVO.setFechaConcertacion(camposDivIntVO.getFechaOperacion());
        librePagoVO.setFechaLiquidacion(parseFechaAbsoluta(camposDivIntVO.getFechaLiquidacion()));                      
        librePagoVO.setReferenciaMensaje(utilService.getFolio(Constantes.SEQ_REFERENCIA_MENSAJE).toString());
        librePagoVO.setReferenciaOperacion(utilService.getFolio(Constantes.SEQ_FOLIO_CONTROL).toString());      
        librePagoVO.setISIN(StringUtils.stripToNull(camposDivIntVO.getIsin()));
             
        /* se obtiene el TV, Emisora, Serie y el Cupon sino lo trae el File, para ingresarlos en el Iso*/
        EmisionVO emisionVO = null;
        if (StringUtils.isBlank(librePagoVO.getTipoValor())
                || StringUtils.isBlank(librePagoVO.getEmisora())
                || StringUtils.isBlank(librePagoVO.getSerie())
                || StringUtils.isBlank(librePagoVO.getCupon())) {
            emisionVO =  new EmisionVO(camposDivIntVO.getIsin());
            Emision emision = emisionDao.findEmisionLiberada(new EmisionVO(camposDivIntVO.getIsin()));
            
            if(emision != null) {
	            emisionVO = ConvertBO2VO.crearEmisionVO(emision);
	            Cupon cupon = cuponDao.findCuponByIdEmision(emision.getIdEmision());
	            if(emisionVO != null) {
		            librePagoVO.setTipoValor(emisionVO.getTv());
		            librePagoVO.setEmisora(emisionVO.getEmisora());
		            librePagoVO.setSerie(emisionVO.getSerie());
		            librePagoVO.setCupon(cupon.getClaveCupon());
	            }
            }            
        }
        /*Datos nuevos*/
        if(StringUtils.isNotBlank(camposDivIntVO.getInstrucEsp())){
        	librePagoVO.setInstruccionesEspeciales("LP:" + camposDivIntVO.getLiquidacionParcial() + camposDivIntVO.getInstrucEsp());
        }
        librePagoVO.setDescripcionBeneficiarioFinal(camposDivIntVO.getNombreCuentaBeneficiario().trim());
        librePagoVO.setDescripcionCuentaContraparte(camposDivIntVO.getDescContraparte().trim());
        librePagoVO.setCuentaContraparte(camposDivIntVO.getCuentaContraparte().trim());
        librePagoVO.setCuentaBeneficiarioFinal(camposDivIntVO.getNumeroCuentaBeneficiario());
        /* se busca el BicProd para la descripcion del custodio*/
        Long[] agenteVOs = null;
        List<CatBic> catBics = null;
        if(emisionVO == null){
        	emisionVO = new EmisionVO(librePagoVO.getTipoValor(),librePagoVO.getEmisora(),librePagoVO.getSerie(),librePagoVO.getCupon());
        }            
        agenteVOs = utilDivIntService.obtieneCatBics(emisionVO);
        /*se modifica para que solo devuelva un catBic, sele agrega parametro descripcionCustodio*/
     	catBics = catBicDao.findCatBic(agenteVOs,camposDivIntVO.getDescCustodio().trim());
        agenteVOs = new Long[catBics.size()];
        
		for (int i = 0; i < catBics.size(); i++) {
			agenteVOs[i] = catBics.get(i).getIdCatbic();			
			String bicProd = catBics.get(i).getDetalleCustodio();
			if(bicProd.equalsIgnoreCase(camposDivIntVO.getDescCustodio().trim())){
				librePagoVO.setCustodio(catBics.get(i).getBicProd());			
			}
		}
		/*se busca el BicDepLiq para la descripcion del depositante ingresado en el file*/
		SicDetalle detalle = (SicDetalle) sicDetalleDao.findSicDetalle(agenteVOs,camposDivIntVO.getNombreDepositante().trim());
		librePagoVO.setLugarDeLiquidacion(detalle.getBicDepLiq());
        
        return librePagoVO;
	}

	/**
     * Llena mapa con los valores de datos adicionales
     * 
     * @param camposDivIntVO Objeto de donde se sacan los datos adicionales
     * @return Mapap con los datos adicionales
     * @throws BusinessException
     */
    private HashMap<String, Object> llenaValoresAdicionales(CamposDivIntVO camposDivIntVO, Object objeto)
            throws BusinessException {
    	Logger log = LoggerFactory.getLogger(this.getClass());        
        HashMap datosAdicionales = new HashMap();
		String auxLiqParcial = "NO";
		if(camposDivIntVO.getTipoMensaje().equalsIgnoreCase(Constantes.TMSJ_540)
				|| camposDivIntVO.getTipoMensaje().equalsIgnoreCase(Constantes.TMSJ_541)){
			auxLiqParcial = StringUtils.isNotEmpty(camposDivIntVO.getLiquidacionParcial()) ? camposDivIntVO.getLiquidacionParcial() : "NO";
		}
		
		String auxInstruccionesEspeciales = "LP:" + auxLiqParcial;
    	if(objeto instanceof TraspasoContraPagoVO){
    		TraspasoContraPagoVO contraPagoVO = (TraspasoContraPagoVO)objeto;
    		datosAdicionales.put("lugarDeLiquidacion",      contraPagoVO.getLugarDeLiquidacion());
			datosAdicionales.put("cuentaBeneficiarioFinal", contraPagoVO.getCuentaBeneficiarioFinal());			
			datosAdicionales.put("descripcionCuentaContraparte", contraPagoVO.getDescripcionCuentaContraparte());
			datosAdicionales.put("descripcionBeneficiarioFinal", contraPagoVO.getDescripcionBeneficiarioFinal());
			if(StringUtils.isNotBlank(contraPagoVO.getInstruccionesEspeciales())){
				if(contraPagoVO.getInstruccionesEspeciales().length() >= 5){
					String auxCadena = contraPagoVO.getInstruccionesEspeciales().substring(0,5);
					if(auxCadena.contains("LP:SI") || auxCadena.contains("LP:NO")){
						auxInstruccionesEspeciales = contraPagoVO.getInstruccionesEspeciales();
					} else {
						auxInstruccionesEspeciales = auxInstruccionesEspeciales + contraPagoVO.getInstruccionesEspeciales();		
					}
				} else {
					auxInstruccionesEspeciales = auxInstruccionesEspeciales + contraPagoVO.getInstruccionesEspeciales();
				}
			}
			datosAdicionales.put("instruccionesEspeciales", auxInstruccionesEspeciales);
			datosAdicionales.put("custodio", contraPagoVO.getCustodio());
			if(StringUtils.isNotBlank(contraPagoVO.getISIN())){
				datosAdicionales.put("ISIN", contraPagoVO.getISIN());
			}
    		
    		
    	}else{
    		TraspasoLibrePagoVO librePagoVO = (TraspasoLibrePagoVO)objeto;
    		datosAdicionales.put("lugarDeLiquidacion",      librePagoVO.getLugarDeLiquidacion());
			datosAdicionales.put("cuentaBeneficiarioFinal", librePagoVO.getCuentaBeneficiarioFinal());			
			datosAdicionales.put("descripcionCuentaContraparte", librePagoVO.getDescripcionCuentaContraparte());			
			datosAdicionales.put("descripcionBeneficiarioFinal", librePagoVO.getDescripcionBeneficiarioFinal());
			if(StringUtils.isNotBlank(librePagoVO.getInstruccionesEspeciales())){
				log.debug("EncoladorDivIntTraspasos :: librePagoVO.getInstruccionesEspeciales(): " + librePagoVO.getInstruccionesEspeciales());
				if(librePagoVO.getInstruccionesEspeciales().length() >= 5){
					String auxCadena = librePagoVO.getInstruccionesEspeciales().substring(0,5);
					if(auxCadena.contains("LP:SI") || auxCadena.contains("LP:NO")){
						auxInstruccionesEspeciales = librePagoVO.getInstruccionesEspeciales();
					} else {
						auxInstruccionesEspeciales = auxInstruccionesEspeciales + librePagoVO.getInstruccionesEspeciales();		
					}
				} else {
					auxInstruccionesEspeciales = auxInstruccionesEspeciales + librePagoVO.getInstruccionesEspeciales();
				}
			}
			datosAdicionales.put("instruccionesEspeciales", auxInstruccionesEspeciales);
			datosAdicionales.put("custodio", librePagoVO.getCustodio());
			if(StringUtils.isNotBlank(librePagoVO.getISIN())){
				datosAdicionales.put("ISIN", librePagoVO.getISIN());
			}
    	}

        datosAdicionales.put(Constantes.CUENTA_CONTRAPARTE_DA,
        		StringUtils.trim(camposDivIntVO.getCuentaContraparte()));
        datosAdicionales.put(Constantes.DESC_CTA_CONTRAPARTE_DA,
        		StringUtils.trim(camposDivIntVO.getDescContraparte()));
        datosAdicionales.put(Constantes.FECHA_OP_DA,
        		camposDivIntVO.getFechaOperacion());
        datosAdicionales.put(Constantes.FECHA_LIQ_DA,
        		camposDivIntVO.getFechaLiquidacion());
        datosAdicionales.put(Constantes.FECHA_NOT_DA,
        		camposDivIntVO.getFechaNotificacion());
        datosAdicionales.put(Constantes.NUM_CUNETA_BENEF_DA,
        		camposDivIntVO.getNumeroCuentaBeneficiario());
        datosAdicionales.put(Constantes.NOM_CUENTA_BENEF_DA,
        		StringUtils.trim(camposDivIntVO.getNombreCuentaBeneficiario()));       
        datosAdicionales.put(Constantes.INSTRUCCIONES_ESP_DA, camposDivIntVO.getInstrucEsp());
        datosAdicionales.put(Constantes.TIPO_MENSAJE_DA,
        		camposDivIntVO.getTipoMensaje()); 
        if(Constantes.TIPO_MOVTO_E.equals(camposDivIntVO.getTipoMovimiento())){
        	datosAdicionales.put(Constantes.ESTATUS_DA, new Long(12));
        }else if(Constantes.TIPO_MOVTO_R.equals(camposDivIntVO.getTipoMovimiento())&& 
        		Constantes.TRASPASO_CONTRA.equals(camposDivIntVO.getTipoOperacion())){
        	datosAdicionales.put(Constantes.ESTATUS_DA, new Long(1));
        }else if(Constantes.TIPO_MOVTO_R.equals(camposDivIntVO.getTipoMovimiento())&& 
        		Constantes.TRASPASO_LIBRE.equals(camposDivIntVO.getTipoOperacion())){
        	datosAdicionales.put(Constantes.ESTATUS_DA, new Long(2));
        }        
        datosAdicionales.put(Constantes.PRECIO_DA, camposDivIntVO.getImporte() != null 
        		? camposDivIntVO.getImporte() : new BigDecimal(0));
        datosAdicionales.put(Constantes.DIVISA_DA, camposDivIntVO.getDivisa());       
        String tv = camposDivIntVO.getTv();
		String emisora = camposDivIntVO.getEmisora();
		String serie = camposDivIntVO.getSerie();
		String cupon = camposDivIntVO.getCupon();		
		String isin = camposDivIntVO.getIsin();		
		EmisionVO emisionVO = null;
		if (StringUtils.isNotBlank(tv) && StringUtils.isNotBlank(emisora) && StringUtils.isNotBlank(serie) && StringUtils.isNotBlank(cupon)) {
			emisionVO = new EmisionVO(tv,emisora,serie,cupon);
			emisionVO.setIdBoveda(Long.valueOf(1));			
		} else if (StringUtils.isNotBlank(isin)) {
			emisionVO = new EmisionVO(isin);
			emisionVO.setIdBoveda(Long.valueOf(1));
		}                
        Long[] agenteVOs = utilDivIntService.obtieneCatBics(emisionVO);
        /*se modifica para que solo devuelva un catBic, sele agrega parametro descripcionCustodio*/
        List<CatBic> catBics = catBicDao.findCatBic(agenteVOs,camposDivIntVO.getDescCustodio().trim());
        agenteVOs = new Long[catBics.size()];
        
        for (int i = 0; i < catBics.size(); i++) {
        	agenteVOs[i] = catBics.get(i).getIdCatbic();    			
        }    		
		SicDetalle sicDetalle = sicDetalleDao.findSicDetalle(agenteVOs, camposDivIntVO.getNombreDepositante().trim());
		if (sicDetalle != null) {
			datosAdicionales.put(Constantes.DEPOSITANTE_DA, sicDetalle.getIdSicDetalle());    			
		}
		return datosAdicionales;		
    }
          
    /**
     * Obtiene la fecha actual
     * @return Returns the currentDate
     */
    public Date getHoy() {
        return dateUtilService.getCurrentDate();
    }

    /**
     * Establece a la fecha con el formato de dd/MM/yyyy absolutamente (Sin
     * horas, minutos, segundos).
     * 
     * @param fechaParam
     * @return
     */
    public Date parseFechaAbsoluta(Date fechaParam) {
        Calendar c = Calendar.getInstance();
        c.setTime(fechaParam);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        return c.getTime();
    }

	/**
	 * @return the emisionDao
	 */
	public EmisionDao getEmisionDao() {
		return emisionDao;
	}

	/**
	 * @param emisionDao the emisionDao to set
	 */
	public void setEmisionDao(EmisionDao emisionDao) {
		this.emisionDao = emisionDao;
	}

	/**
	 * @return the dateUtilService
	 */
	public DateUtilService getDateUtilService() {
		return dateUtilService;
	}

	/**
	 * @param dateUtilService the dateUtilService to set
	 */
	public void setDateUtilService(DateUtilService dateUtilService) {
		this.dateUtilService = dateUtilService;
	}

	/**
	 * @return the isoHelper
	 */
	public IsoHelper getIsoHelper() {
		return isoHelper;
	}

	/**
	 * @param isoHelper the isoHelper to set
	 */
	public void setIsoHelper(IsoHelper isoHelper) {
		this.isoHelper = isoHelper;
	}

	/**
	 * @return the divisionInternacionalService
	 */
	public DivisionInternacionalService getDivisionInternacionalService() {
		return divisionInternacionalService;
	}

	/**
	 * @param divisionInternacionalService the divisionInternacionalService to set
	 */
	public void setDivisionInternacionalService(
			DivisionInternacionalService divisionInternacionalService) {
		this.divisionInternacionalService = divisionInternacionalService;
	}

	/**
	 * @return the utilService
	 */
	public UtilService getUtilService() {
		return utilService;
	}

	/**
	 * @param utilService the utilService to set
	 */
	public void setUtilService(UtilService utilService) {
		this.utilService = utilService;
	}

	/**
	 * @return CuponDao
	 */
	public CuponDao getCuponDao() {
		return cuponDao;
	}

	/**
	 * @param cuponDao
	 */
	public void setCuponDao(CuponDao cuponDao) {
		this.cuponDao = cuponDao;
	}

	/**
	 * @param utilDivIntService the utilDivIntService to set
	 */
	public void setUtilDivIntService(UtilDivIntService utilDivIntService) {
		this.utilDivIntService = utilDivIntService;
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
	
	public void setCuentaNombradaDao(CuentaNombradaDao cuentaNombradaDao) {
		this.cuentaNombradaDao = cuentaNombradaDao;
	}
	/**
	 * @return the consultaCatService
	 */
	public ConsultaCatalogoService getConsultaCatService() {
		return consultaCatService;
	}

	/**
	 * @param consultaCatService
	 *            the consultaCatService to set
	 */
	public void setConsultaCatService(ConsultaCatalogoService consultaCatService) {
		this.consultaCatService = consultaCatService;
	}


}
