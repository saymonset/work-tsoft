/*
 *Copyright (c) 2005-2007 Bursatec. All Rights Reserved 
 */
package com.indeval.portaldali.presentation.controller.mercadodinero.capturaoperaciones;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import com.bursatec.seguridad.presentation.constants.SeguridadConstants;
import com.indeval.portaldali.middleware.dto.BovedaDTO;
import com.indeval.portaldali.middleware.dto.CriterioOrdenamientoDTO;
import com.indeval.portaldali.middleware.dto.CuentaDTO;
import com.indeval.portaldali.middleware.dto.EmisionDTO;
import com.indeval.portaldali.middleware.dto.EmisoraDTO;
import com.indeval.portaldali.middleware.dto.InstitucionDTO;
import com.indeval.portaldali.middleware.dto.PosicionDTO;
import com.indeval.portaldali.middleware.dto.SerieDTO;
import com.indeval.portaldali.middleware.dto.TipoCuentaDTO;
import com.indeval.portaldali.middleware.dto.TipoNaturalezaDTO;
import com.indeval.portaldali.middleware.dto.TipoTenenciaDTO;
import com.indeval.portaldali.middleware.dto.TipoValorDTO;
import com.indeval.portaldali.middleware.dto.util.DTOAssembler;
import com.indeval.portaldali.middleware.dto.util.ResultadoValidacionDTO;
import com.indeval.portaldali.middleware.services.common.constants.DaliConstants;
import com.indeval.portaldali.middleware.services.common.constants.TipoCuentaConstants;
import com.indeval.portaldali.middleware.services.common.constants.TipoCustodiaConstants;
import com.indeval.portaldali.middleware.services.enviooperaciones.EnvioOperacionesService;
import com.indeval.portaldali.middleware.services.estadocuenta.ConsultaPosicionService;
import com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService;
import com.indeval.portaldali.middleware.services.mercadodinero.RegistraOperacionParams;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import com.indeval.portaldali.middleware.services.modelo.InfrastructureException;
import com.indeval.portaldali.middleware.services.util.FechasUtilService;
import com.indeval.portaldali.middleware.services.util.UtilServices;
import com.indeval.portaldali.middleware.services.util.util.Constantes;
import com.indeval.portaldali.persistence.dao.common.EmisionDaliDAO;
import com.indeval.portaldali.presentation.common.constants.UtilConstantes;
import com.indeval.portaldali.presentation.controller.mercadodinero.capturaoperaciones.constants.CapturaOperacionesConstants;
import com.indeval.portaldali.presentation.dto.mercadodinero.TraspasoLibrePagoDTO;
import com.indeval.portaldali.presentation.helper.CalculoCapturaOperacionViewHelper;
import com.indeval.portaldali.presentation.helper.ServicesCapturaOperacionViewHelper;
import com.indeval.portaldali.presentation.util.ConsultaCatalogosFacade;
import com.indeval.portaldali.presentation.validation.DTOValidator;
import com.indeval.portaldali.vo.CaptOpsConfig;
import com.indeval.protocolofinanciero.api.vo.TraspasoLibrePagoVO;

/**
 * Controller de la pantalla de traspaso libre de pago
 * 
 * @author Juan Carlos Huizar Moreno
 * @version 1.0
 */
public class TraspasoLibrePagoController extends CapturaOperacionesController {

    /** La clase que permite el acceso a la consulta de los catálogos utilizados */
    private ConsultaCatalogosFacade consultaCatalogosFacade = null;
    /** El DTO para los elementos de la pantalla Traspaso libre de pago */
    private TraspasoLibrePagoDTO traspasoLibrePago = new TraspasoLibrePagoDTO();
    /** Servicio de negocio relacionado con el envo de operaciones */
    private EnvioOperacionesService envioOperacionesService = null;
    /** Servicio helper para la captura de operaciones */
    private ServicesCapturaOperacionViewHelper servicesCapturaOperacionViewHelper = null;
    /** Servicio de operaciones de mercado de dinero */
    private MercadoDineroService mercadoDineroService = null;
    /** Configuracicn de operaciones */
    private CaptOpsConfig configuracion = null;
    /** Validador de la Captura Operaciones Reporto Nominal Opcion Mismo Dia */
    private DTOValidator validadorTraspasoLibrePago;
    /** Valid Object de traspaso contrapago */
    private TraspasoLibrePagoVO vo = null;
    /** La fecha de liquidacion */
    private Date fechaLiquidacion = new Date();
    /**
     * Contiene los cálculos de Simulado, Fecha Regreso, Importe y Premio necesarios para las pantallas de Captura de Operaciones
     */
    private CalculoCapturaOperacionViewHelper calculos = new CalculoCapturaOperacionViewHelper();
    
    /** Para sacar el folio de una secuencia */
    private UtilServices utilService = null;
    
    /** Identificadores del mercado a filtrar*/    
	private List<Long> idMercadoDinero = null;
	/**Dao para la obtención de la emisión*/
	private EmisionDaliDAO emisionDaliDAO = null;
	/**Servicio para la obtencion de la posicion*/
	private ConsultaPosicionService consultaPosicionService;	
	/** Servicio para el acceso al cálculo de fechas*/
	private FechasUtilService fechasUtilService = null;

    /**
	 * Obtiene el valor del atributo fechaLiquidacion
	 *
	 * @return el valor del atributo fechaLiquidacion
	 */
	public Date getFechaLiquidacion() {
		return fechaLiquidacion;
	}

	/**
	 * Establece el valor del atributo fechaLiquidacion
	 *
	 * @param fechaLiquidacion el valor del atributo fechaLiquidacion a establecer
	 */
	public void setFechaLiquidacion(Date fechaLiquidacion) {
		this.fechaLiquidacion = fechaLiquidacion;
	}

	/**
     * constructor
     */
    public String getInitForm() {
    	traspasoLibrePago.setMismoDia(CapturaOperacionesConstants.COMPRA_MISMO_DIA);
        traspasoLibrePago.setIdFolioTraspasante(getInstitucionActual().getClaveTipoInstitucion()
                + getInstitucionActual().getFolioInstitucion());
        traspasoLibrePago.getPosicionTraspasante().getCuenta().setInstitucion(getInstitucionActual());
        traspasoLibrePago.setDivisa(MXP);
        return null;
    }

    /**
     * Inicializa los datos de la página.
     * 
     * @return <code>null</code> este método no retorna nada.
     */
    public String getInit() {
        
    	traspasoLibrePago.setMismoDia(CapturaOperacionesConstants.COMPRA_MISMO_DIA);  	
    	traspasoLibrePago.setFechaConcertacion(servicesCapturaOperacionViewHelper.getFechasUtilService().getCurrentDate());
    	traspasoLibrePago.setFechaLiquidacion(traspasoLibrePago.getFechaConcertacion());    	
    	
    	if( !traspasoLibrePago.isCompra() ) {
    		if(  StringUtils.isBlank(traspasoLibrePago.getIdFolioTraspasante()) ) {
        	traspasoLibrePago.setIdFolioTraspasante(getInstitucionActual().getClaveTipoInstitucion() + getInstitucionActual().getFolioInstitucion());
        }
    	} else {
    		if(  StringUtils.isBlank(traspasoLibrePago.getIdFolioReceptor()) ) {
    			traspasoLibrePago.setIdFolioReceptor(getInstitucionActual().getClaveTipoInstitucion() + getInstitucionActual().getFolioInstitucion());
    		}
    	}
    	
        if (traspasoLibrePago.getIdFolioReceptor() != null) {
            InstitucionDTO inst = consultaCatalogosFacade.buscarInstitucionPorIdFolio(traspasoLibrePago.getIdFolioReceptor());
            if (inst != null) {
                traspasoLibrePago.getCuentaReceptor().setInstitucion(inst);
            } else {
            	traspasoLibrePago.setIdFolioReceptor(StringUtils.EMPTY);
            	traspasoLibrePago.setCuentaReceptor(new CuentaDTO());
			}
        }
        if (traspasoLibrePago.getIdFolioTraspasante() != null) {
            InstitucionDTO inst = consultaCatalogosFacade.buscarInstitucionPorIdFolio(traspasoLibrePago.getIdFolioTraspasante());
            if (inst != null) {
                traspasoLibrePago.getPosicionTraspasante().getCuenta().setInstitucion(inst);
            } else {
            	traspasoLibrePago.setNetoEfectivo(null);
            	traspasoLibrePago.setIdFolioTraspasante(StringUtils.EMPTY);
			}
        }
        if (traspasoLibrePago.getCuentaReceptor().getCuenta() != null) {
            CuentaDTO cnt = consultaCatalogosFacade.buscarCuentaPorNumeroCuentaNullSiNoExiste(traspasoLibrePago
                    .getIdFolioReceptor()
                    + traspasoLibrePago.getCuentaReceptor().getCuenta());

            if (cnt != null) {
                traspasoLibrePago.setCuentaReceptor(cnt);
            } else {
            	traspasoLibrePago.getCuentaReceptor().setTipoTenencia(new TipoTenenciaDTO());
            	traspasoLibrePago.getCuentaReceptor().setNumeroCuenta(StringUtils.EMPTY);
            	traspasoLibrePago.getCuentaReceptor().setCuenta(StringUtils.EMPTY);
            	traspasoLibrePago.getCuentaReceptor().setNombreCuenta(StringUtils.EMPTY);
			}
        }
        if (traspasoLibrePago.getPosicionTraspasante().getCuenta().getCuenta() != null) {
            CuentaDTO cnt = consultaCatalogosFacade.buscarCuentaPorNumeroCuentaNullSiNoExiste(traspasoLibrePago
                    .getIdFolioTraspasante()
                    + traspasoLibrePago.getPosicionTraspasante().getCuenta().getCuenta());
            if (cnt != null) {
                traspasoLibrePago.getPosicionTraspasante().setCuenta(cnt);
            } else {
				
            	traspasoLibrePago.getPosicionTraspasante().getCuenta().setTipoTenencia(new TipoTenenciaDTO());
            	traspasoLibrePago.getPosicionTraspasante().getCuenta().setNumeroCuenta(StringUtils.EMPTY);
            	traspasoLibrePago.getPosicionTraspasante().getCuenta().setCuenta(StringUtils.EMPTY);
            	traspasoLibrePago.getPosicionTraspasante().getCuenta().setNombreCuenta(StringUtils.EMPTY);
				
			}
        }
        //obtener los datos de la posicion
        obtenerDatosPosicion();
        return null;
    }
    
    /**
     * llevar a cabo la inserción de la Operación en la BD
     * 
     * @return String Siguiente Página a redireccionar
     */
    public void enviarOperacion(ActionEvent event) {
    	super.limpiarCampos(); // isos
    	
		try {				
			if(StringUtils.isBlank(validarCuenta(event)) && StringUtils.isBlank(validarCompra(event))){
				traspasoLibrePago.getPosicionTraspasante().getCuenta().setInstitucion(
						consultaCatalogosFacade.buscarInstitucionPorIdNoNulaFolio(traspasoLibrePago.getIdFolioTraspasante()));
				traspasoLibrePago.getCuentaReceptor().setInstitucion(
						consultaCatalogosFacade.buscarInstitucionPorIdNoNulaFolio(traspasoLibrePago.getIdFolioReceptor()));
				// Si el usuario debe firmar la operacion, se recuperar la firma.
				// Si no se ha firmado, se procesan los datos y regresa el control a la pantalla para firmar
				if (isUsuarioConFacultadFirmar()) {
					//Valida la vigencia del certificado - 18-Agosto-2014 Pablo Balderas
					validarVigenciaCertificado();
				}
				
				procesarDatos();
			}
		}
		catch(BusinessException businessException) {
    		addMessage(businessException.getMessage(), FacesMessage.SEVERITY_ERROR);
    	}
    }
    
    public void enviarOperacion2(ActionEvent event) {
    	if (isUsuarioConFacultadFirmar()) {
    		validarVigenciaCertificado();
			recuperarCamposFirma();
			
			if(!StringUtils.isEmpty(isoFirmado)) {
				try {
					grabarOperacion(event);
				}catch(BusinessException ex) {
					addMessage(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
				}
			}
    	}
    	
    	super.limpiarCampos();
    }

	/**
	 * Valida, Calcula y registra la operacion antes de firmar
	 */
	private void procesarDatos() {
		boolean valido = validarDTO();
		if (valido) {
			calcularYRegistrarOperacion();
		}
	}
    
    /**
	 * Valida que la cuenta del Transpasante no pede ser igual a la cuenta del Receptor
	 * 
	 * @param e
	 */
	public String validarCuenta(ActionEvent e) {
		mensajeUsuario="";
		if(traspasoLibrePago.getPosicionTraspasante().getCuenta().getCuenta() != null &&
				!traspasoLibrePago.getPosicionTraspasante().getCuenta().getCuenta().equals("")) {
			if (traspasoLibrePago.getPosicionTraspasante().getCuenta().getCuenta()
					.equals(traspasoLibrePago.getCuentaReceptor().getCuenta())
					&&(traspasoLibrePago.getIdFolioTraspasante().equals(traspasoLibrePago.getIdFolioReceptor()))) {
				mensajeUsuario =  "La cuenta traspasante no puede ser la misma que la del receptor";
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								mensajeUsuario, mensajeUsuario));
				traspasoLibrePago.getCuentaReceptor().setCuenta("");
			}
		}
		return mensajeUsuario;
	}

    /**
	 * Valida si es una compra que no sea entre la misma institucion
	 * 
	 * @param e
	 */
	public String validarCompra(ActionEvent e) {
		this.mensajeUsuario="";
		if( traspasoLibrePago.isCompra() ) {
			String traspasante = traspasoLibrePago.getPosicionTraspasante().getCuenta().getInstitucion().getIdFolio();
			String receptor = traspasoLibrePago.getCuentaReceptor().getInstitucion().getIdFolio();
			if( StringUtils.isNotBlank(traspasante) && StringUtils.isNotBlank(receptor) &&
					traspasante.trim().equalsIgnoreCase(receptor.trim())) {
				mensajeUsuario = "Un TLP entre la misma insituci\u00f3n no necesita operaci\u00f3n de confirmaci\u00f3n";
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								mensajeUsuario, mensajeUsuario));
			}
		}
		return mensajeUsuario;
		
	}
	
	 /**
     * Cambio el tipo de TLP venta o compra
     * 
     * @param ActionEvent event
     */
    public void cambioCompra(ActionEvent event) {
    	logger.info("Entrando a TraspasoLibrePagoCapitalesController.cambioCompra");
    	boolean valor = traspasoLibrePago.isCompra();
    	traspasoLibrePago = new TraspasoLibrePagoDTO();
        traspasoLibrePago.setDivisa(MXP);
    	traspasoLibrePago.setCompra(valor);
        super.limpiarCampos();
    }

    /**
     * Guarda la operación firmada
     * 
     * @param e
     */
    public void grabarOperacion(ActionEvent e) {
    	if(!cdb.validation(hashIso, isoSinFirmar)){
    		throw new InfrastructureException(UtilConstantes.ERROR_ISO_DIFERENTE);
    	}
    	isoFirmado = (new StringBuilder()).append(isoSinFirmar).append(numeroSerie).append("\n").append("{SHA1withRSA}").append(isoFirmado).toString();
		logger.debug("isoFirmado: '" + isoFirmado + "'");
    	enviarOperacionABitacora();
    }

	/**
	 * Envia la operacion a bitacora
	 */
	private void enviarOperacionABitacora() {
		String folioControl = vo.getReferenciaOperacion();
		 
		HashMap<String, String> datosAdicionales = new HashMap<String, String>();     
				datosAdicionales.put(SeguridadConstants.USR_CREDENCIAL, 
						(String)FacesContext.getCurrentInstance().getExternalContext().
						getSessionMap().get(SeguridadConstants.TICKET_SESION));
 
    	envioOperacionesService.grabaOperacion(vo, folioControl, traspasoLibrePago.isCompra(), datosAdicionales, null, isoFirmado);
    	
        if (!existeErrorEnInvocacion()) {
            mensajeUsuario = "La operaci\u00f3n se llev\u00f3 a cabo exitosamente. Folio de la operaci\u00f3n : " + folioAsignado;

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, mensajeUsuario, mensajeUsuario));
            traspasoLibrePago = new TraspasoLibrePagoDTO();
            limpiarCampos(null);
        }
	}

    private void calcularYRegistrarOperacion() {

        // buscar la divisa y boveda por id
        traspasoLibrePago.setDivisa(consultaCatalogosFacade.buscarDivisaPorId(traspasoLibrePago.getDivisa().getId()));
        if( null != traspasoLibrePago.getPosicionTraspasante().getBoveda() &&  traspasoLibrePago.getPosicionTraspasante().getBoveda().getId() >0 ){
        	traspasoLibrePago.setBoveda(consultaCatalogosFacade.buscarBovedaPorId(traspasoLibrePago.getPosicionTraspasante().getBoveda().getId()));
        }else{
        	traspasoLibrePago.setBoveda(null);
        }

        boolean encontroError = false;
        RegistraOperacionParams registraOperacionParams = new RegistraOperacionParams();

        // --- AQUI SE LLEVA A CABO LA INSERCION DE LA OPERACION

        // --- Se deja fijo el ROL DEL AGENTE FIRMADO de Traspasante (T)
        registraOperacionParams.setRol("T");

        registraOperacionParams.setPlazoLiquidacion(traspasoLibrePago.getPlazoLiquidacionHoras());

        registraOperacionParams.setFechaConcertacion(traspasoLibrePago.getFechaConcertacion());
 
        // --- TIPO DE OPERACION, TRASPASO LIBRE DE PAGO ("T")
        registraOperacionParams.setClaveReporto("T");
        
        // --- AGENTE TRASPASANTE
        registraOperacionParams
                .setTraspasante(DTOAssembler.crearAgenteVO(traspasoLibrePago.getPosicionTraspasante().getCuenta()));

        // --- AGENTE DEL RECEPTOR
        registraOperacionParams.setReceptor(DTOAssembler.crearAgenteVO(traspasoLibrePago.getCuentaReceptor()));
        registraOperacionParams.setEmision(new EmisionVO());

        // --- EMISION
        if (traspasoLibrePago.getPrecioVector() != null) {
            registraOperacionParams.getEmision().setPrecioVector(new BigDecimal(traspasoLibrePago.getPrecioVector()));
        }
        // --- SALDO DISPONIBLE
        if (traspasoLibrePago.getSaldoDisponible() != null) {
            registraOperacionParams.getEmision().setSaldoDisponible(
                    new BigDecimal(traspasoLibrePago.getSaldoDisponible().doubleValue()));
        }
        registraOperacionParams.getEmision().setIdTipoValor(
                traspasoLibrePago.getPosicionTraspasante().getEmision().getTipoValor().getClaveTipoValor());
        registraOperacionParams.getEmision().setEmisora(
                traspasoLibrePago.getPosicionTraspasante().getEmision().getEmisora().getDescripcion());
        registraOperacionParams.getEmision().setSerie(
                traspasoLibrePago.getPosicionTraspasante().getEmision().getSerie().getSerie());
        registraOperacionParams.getEmision().setCupon(traspasoLibrePago.getPosicionTraspasante().getEmision().getCupon());
        registraOperacionParams.getEmision().setMercado(traspasoLibrePago.getPosicionTraspasante().getEmision().getTipoValor().getMercado().getClaveMercado());

        // --- DIAS VIGENTES
        if (traspasoLibrePago.getDiasVigentes() != null) {
            registraOperacionParams.setDiasVigentes(traspasoLibrePago.getDiasVigentes());
        }

        // --- SIMULADO
        if (traspasoLibrePago.getSimulado() != null) {
            registraOperacionParams.setSimulado(new BigInteger(traspasoLibrePago.getSimulado().toString()));
        }

        // --- NETO EFECTIVO
        if (traspasoLibrePago.getNetoEfectivo() != null) {
            registraOperacionParams.setNetoEfectivo(new BigDecimal(traspasoLibrePago.getNetoEfectivo()));
        }

        registraOperacionParams.setPlazoReporto(Integer.valueOf("0"));

        calculos.setTipoOperacion(registraOperacionParams.getClaveReporto());
        calculos.setCantidadOperadaLeida(traspasoLibrePago.getCantidadOperada() != null ? traspasoLibrePago.getCantidadOperada()
                .toString() : "0");
        calculos.setSaldoDisponibleLeido(traspasoLibrePago.getSaldoDisponible() != null ? traspasoLibrePago.getSaldoDisponible()
                .toString() : "0");
        calculos.setPlazoRepDiasLeido(registraOperacionParams.getPlazoReporto().toString());
        calculos.setPlazoRepDiasInhabilitadoLeido("true");
        calculos.setPlazoLiquidacionHorasInhabilitadoLeido("true");
        calculos.setPrecioTituloInhabilitadoLeido("true");
        calculos.setImporteInhabilitadoLeido("true");
        calculos.setTasaPremioInhabilitadoLeido("true");
        calculos.setTasaPremioLeido(null);

        servicesCapturaOperacionViewHelper.realizarCalculos(calculos);

        // --- ENCONTRO UN ERROR AL CALCULAR EL SIMULADO
        if (calculos != null && calculos.getMensajeSimulado() != null
                && !calculos.getMensajeSimulado().trim().equalsIgnoreCase("")) {
            encontroError = true;
        }
        // --- ENCONTRO UN ERROR AL CALCULAR EL SIMULADO
        if (calculos != null && calculos.getMensajeSimulado() != null
                && !calculos.getMensajeSimulado().trim().equalsIgnoreCase("")) {
            encontroError = true;
        }

        if (encontroError) {
        } else {
            // --- CANTIDAD OPERADA
            if (traspasoLibrePago.getCantidadOperada() != null) {
                registraOperacionParams.setCantidadOperada(new BigDecimal(traspasoLibrePago.getCantidadOperada()));
            } else {
                registraOperacionParams.setCantidadOperada(new BigDecimal(0));
            }
            // --- VALOR EN
            if (traspasoLibrePago.getDivisa() != null) {
                registraOperacionParams.setDivisa(traspasoLibrePago.getDivisa().getClaveAlfabetica());               
            }
            
            if(traspasoLibrePago.getBoveda() != null &&  traspasoLibrePago.getBoveda().getId() >0 ){
            	 registraOperacionParams.setIdBoveda(BigInteger.valueOf(traspasoLibrePago.getBoveda().getId()));
            }
        }
        registraOperacionParams.setFechaLiq(servicesCapturaOperacionViewHelper.calculaFechaLiquidacion(registraOperacionParams
                .getPlazoLiquidacion()));
        logger.debug("FechaLiq ["+registraOperacionParams.getFechaLiq()+"] ["+registraOperacionParams.getPlazoLiquidacion()+"]");
        
        registraOperacionParams.setFechaRegreso(calculos.getFechaRegreso());
        
        if (registraOperacionParams.getFechaRegreso() == null) {
            registraOperacionParams.setFechaRegreso(servicesCapturaOperacionViewHelper.getFechasUtilService().getCurrentDate());
        }
        registraOperacionParams.setFechaHoraCierreOper(traspasoLibrePago.getFechaHoraCierreOper());
        
        folioAsignado = mercadoDineroService.validaRegistraOperacionBusinessRules(registraOperacionParams);
        if (!existeErrorEnInvocacion()) {
            vo = new TraspasoLibrePagoVO();

            vo = servicesCapturaOperacionViewHelper.inicializaTraspasoLibrePagoVO(registraOperacionParams);
            vo.setReferenciaOperacion(folioAsignado);
            vo.setTipoInstruccion(calculos.getTipoOperacion());
            vo.setReferenciaMensaje(utilService.getFolio(Constantes.SEQ_REFERENCIA_MENSAJE).toString());
            
            if(traspasoLibrePago.getBoveda() != null && traspasoLibrePago.getBoveda().getNombreCorto() != null ){
            	vo.setBoveda(traspasoLibrePago.getBoveda().getNombreCorto());
            }
            // Si el usuario debe firmar la operacion, se crea el iso
            if(isUsuarioConFacultadFirmar()) {
	            isoSinFirmar = isoHelper.creaISO(vo,  traspasoLibrePago.isCompra());
	            hashIso = cdb.cipherHash(isoSinFirmar);
            } else {
            	enviarOperacionABitacora();
            }
        }

    }

    public void realizarCalculo(ActionEvent e) {

        calculos.setTipoOperacion("T");
        calculos.setCantidadOperadaLeida(traspasoLibrePago.getCantidadOperada() != null ? traspasoLibrePago.getCantidadOperada()
                .toString() : "");
        calculos.setCantidadOperada(new BigDecimal(traspasoLibrePago.getCantidadOperada() != null ? traspasoLibrePago
                .getCantidadOperada().doubleValue() : 0));
        calculos.setSaldoDisponibleLeido(traspasoLibrePago.getSaldoDisponible() != null ? traspasoLibrePago.getSaldoDisponible()
                .toString() : "");

        calculos.setPrecioTituloInhabilitadoLeido("false");
        calculos.setImporteInhabilitadoLeido("false");
        calculos.setTasaPremioInhabilitadoLeido("false");
        calculos.setPlazoRepDiasInhabilitadoLeido("false");

        servicesCapturaOperacionViewHelper.realizarCalculos(calculos);

        /*
         * Realiza el cálculo del saldo Simulado se necesitan: cantidadOperadaLeida, saldoDisponibleLeido
         */
        traspasoLibrePago.setSimulado(calculos.getSimulado() != null ? calculos.getSimulado().longValue() : null);

    }

    /**
     * Ejecuta las validaciones de los DTOs de captura de acuerdo al tipo de operaci\u00f3n a realizar
     * 
     * @return
     */
    private boolean validarDTO() {
        ResultadoValidacionDTO resultadao = null;
        if(traspasoLibrePago.getMismoDia() != null && traspasoLibrePago.getMismoDia() == 0) {
        	traspasoLibrePago.setPlazoLiquidacionHoras(0);
        }
        resultadao = validadorTraspasoLibrePago.validarDTO(traspasoLibrePago);
        if (!resultadao.isValido()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, resultadao.getMensaje(), resultadao.getMensaje()));
        }
        return resultadao.isValido();
    }

    /**
     * Limpiar los campos de la página respetando las opciones de la operacion.
     */
    public void limpiarCampos(ActionEvent e) {
        traspasoLibrePago = new TraspasoLibrePagoDTO();
        traspasoLibrePago.setDivisa(MXP);
        traspasoLibrePago.setCompra(false);
        super.limpiarCampos();
        getInit();
    }
    
public void calcularFechaLiquidacion(ActionEvent e) {
		
    	Date fechaLiquidacion = null;
		int plazoDias = 0;
		
		if(traspasoLibrePago.getPlazoLiquidacionHoras() == null) {
			traspasoLibrePago.setPlazoLiquidacionHoras(new Integer(24));
		}
		
		if(traspasoLibrePago.getMismoDia().intValue() == 1) {			
			plazoDias=traspasoLibrePago.getPlazoLiquidacionHoras().intValue()/24;		
			fechaLiquidacion = utilService.agregarDiasHabiles(traspasoLibrePago.getFechaConcertacion(), plazoDias);
		}
		else {
			fechaLiquidacion  = servicesCapturaOperacionViewHelper.getFechasUtilService().getCurrentDate();
			traspasoLibrePago.setPlazoLiquidacionHoras(null);
		}
				
		traspasoLibrePago.setFechaLiquidacion(fechaLiquidacion);
		
	}


		/**
		 * Indica si la compra se realiza el mismo día o no (compra segun la fecha).
		 * 
		 * @return <code>true</code> si la compra se realiza el mismo día. <code>false</code> si la compra se realiza segun la
		 *         fecha.
		 */
		public boolean isCompraMismoDia() {
		    return traspasoLibrePago.getMismoDia() != null && traspasoLibrePago.getMismoDia().equals(CapturaOperacionesConstants.COMPRA_MISMO_DIA);
		}
		    
    
    
    
    /**
	 * Metodo para la obtencion de la Posicion a partir
	 * de los campos idFolioParticiante, TV, Emisora, Serie.
	 * 
	 * return <code>null</code>. No es necesario un valor de retorno.
	 */
	public String obtenerDatosPosicion(){
		//primero se obtiene la Posicion
		PosicionDTO posicion = null;
		if (StringUtils.isNotEmpty(traspasoLibrePago.getPosicionTraspasante().getCuenta().getCuenta())
				&& StringUtils.isNotEmpty(traspasoLibrePago.getPosicionTraspasante().getEmision().getTipoValor().getClaveTipoValor())
				&& StringUtils.isNotEmpty(traspasoLibrePago.getPosicionTraspasante().getEmision().getEmisora().getDescripcion())
				&& StringUtils.isNotEmpty(traspasoLibrePago.getPosicionTraspasante().getEmision().getSerie().getSerie())) {
			PosicionDTO criterio = new PosicionDTO();
			
			criterio.setCuenta(new CuentaDTO());
			criterio.getCuenta().setTipoCustodia(TipoCustodiaConstants.VALORES);
			criterio.getCuenta().setTipoTenencia(new TipoTenenciaDTO());
			criterio.getCuenta().getTipoTenencia().setTipoCuenta(new TipoCuentaDTO(TipoCuentaConstants.TIPO_NOMBRADA,""));
			criterio.getCuenta().getTipoTenencia().setTipoNaturaleza(new TipoNaturalezaDTO(TipoNaturalezaDTO.PASIVO,""));
			criterio.getCuenta().setNumeroCuenta(getInstitucionActual().getClaveTipoInstitucion() 
					+ getInstitucionActual().getFolioInstitucion() + traspasoLibrePago.getPosicionTraspasante().getCuenta().getCuenta());
			criterio.setEmision(new EmisionDTO());
			InstitucionDTO institucion = null;
			institucion = consultaCatalogosFacade.buscarInstitucionPorIdFolio(traspasoLibrePago.getIdFolioTraspasante());
			
			if (institucion != null ) {
				//cuenta
				if (StringUtils.isNotEmpty(traspasoLibrePago.getPosicionTraspasante().getCuenta().getCuenta())) {
					criterio.getCuenta().setNumeroCuenta(institucion.getClaveTipoInstitucion() + institucion.getFolioInstitucion() + traspasoLibrePago.getPosicionTraspasante().getCuenta().getCuenta());					
				}else {
					criterio.getCuenta().setCuenta("-1");
				}
				
				//TV
				criterio.getEmision().setTipoValor(consultaCatalogosFacade.buscarTipoValorPorClave(traspasoLibrePago.getPosicionTraspasante().getEmision().getTipoValor().getClaveTipoValor()));
				//Emisora
				criterio.getEmision().setEmisora(consultaCatalogosFacade.buscarEmisoraPorNombreCorto(traspasoLibrePago.getPosicionTraspasante().getEmision().getEmisora().getDescripcion()));
				criterio.getEmision().setSerie(new SerieDTO());
				//Serie
				criterio.getEmision().getSerie().setSerie(traspasoLibrePago.getPosicionTraspasante().getEmision().getSerie().getSerie().trim());
				
				if (traspasoLibrePago.getPosicionTraspasante().getBoveda() != null && traspasoLibrePago.getPosicionTraspasante().getBoveda().getId()>0) {
					criterio.setBoveda(new BovedaDTO());
					criterio.getBoveda().setId(traspasoLibrePago.getPosicionTraspasante().getBoveda().getId());
				}
				criterio.getBoveda().setId(NumberUtils.toLong("-1",DaliConstants.VALOR_COMBO_TODOS));
				criterio.getCuenta().setInstitucion(institucion);
				criterio.setFechaFinal(new Date());
				
				CriterioOrdenamientoDTO orden = new CriterioOrdenamientoDTO(); 
				orden.setColumna("sortCuenta");
				
				List<PosicionDTO> listaPosiciones = consultaPosicionService.consultarPosicionesPorMercado(criterio, null, orden, idMercadoDinero.toArray(new Long[]{}));
				
				traspasoLibrePago.getPosicionTraspasante().getEmision().setCupon(null);
				traspasoLibrePago.getPosicionTraspasante().getEmision().setIsin(null);
				traspasoLibrePago.getPosicionTraspasante().getEmision().getTipoValor().getMercado().setClaveMercado(null);
				traspasoLibrePago.getPosicionTraspasante().setBoveda(null);
				traspasoLibrePago.setSaldoDisponible(null);
				traspasoLibrePago.setDiasVigentes(null);
				
				if (listaPosiciones != null  && !listaPosiciones.isEmpty()) {
					posicion = listaPosiciones.get(0);
					//obtencion de los datos para ser desplegado en pantalla
					traspasoLibrePago.getPosicionTraspasante().getCuenta().setCuenta(posicion.getCuenta().getCuenta());
					traspasoLibrePago.getPosicionTraspasante().getEmision().getEmisora().setDescripcion(posicion.getEmision().getEmisora().getDescripcion());
					traspasoLibrePago.getPosicionTraspasante().getEmision().setCupon(posicion.getEmision().getCupon());
					traspasoLibrePago.setIsin(posicion.getEmision().getIsin());
					traspasoLibrePago.getPosicionTraspasante().getEmision().setIsin(posicion.getEmision().getIsin());
					traspasoLibrePago.getPosicionTraspasante().getEmision().getTipoValor().getMercado().setClaveMercado(
							posicion.getEmision().getTipoValor().getMercado().getClaveMercado());
					traspasoLibrePago.getPosicionTraspasante().setBoveda(new BovedaDTO());
					traspasoLibrePago.getPosicionTraspasante().getBoveda().setNombreCorto(posicion.getBoveda().getNombreCorto());
					traspasoLibrePago.getPosicionTraspasante().getBoveda().setId(posicion.getBoveda().getId());
					traspasoLibrePago.setSaldoDisponible(new BigDecimal(posicion.getPosicionDisponible()).longValue());
					
					if (posicion.getEmision().getFechaVencimiento() != null) {
						traspasoLibrePago.setDiasVigentes((int)utilService.dateDiff(fechasUtilService.getCurrentDate(),posicion.getEmision().getFechaVencimiento()));
					}
				} 
			}
			
		}
		
		if (posicion == null && StringUtils.isNotEmpty(traspasoLibrePago.getPosicionTraspasante().getEmision().getTipoValor().getClaveTipoValor())
				&& StringUtils.isNotEmpty(traspasoLibrePago.getPosicionTraspasante().getEmision().getEmisora().getDescripcion())
				&& StringUtils.isNotEmpty(traspasoLibrePago.getPosicionTraspasante().getEmision().getSerie().getSerie())) {
			
			EmisionDTO criterio = new EmisionDTO();			
			criterio.setTipoValor(new TipoValorDTO());
			criterio.getTipoValor().setClaveTipoValor(traspasoLibrePago.getPosicionTraspasante().getEmision().getTipoValor().getClaveTipoValor());
			criterio.setEmisora(new EmisoraDTO());
			criterio.getEmisora().setDescripcion(traspasoLibrePago.getPosicionTraspasante().getEmision().getEmisora().getDescripcion());
			criterio.setSerie(new SerieDTO());
			criterio.getSerie().setSerie(traspasoLibrePago.getPosicionTraspasante().getEmision().getSerie().getSerie());
			List<EmisionDTO> listaEmisiones = emisionDaliDAO.consultarEmisionesPorDescripciones(criterio, null);
			
			if (listaEmisiones != null && !listaEmisiones.isEmpty() && listaEmisiones.size() == 1) {
				//pasar los datos al objeto de la pantalla
				traspasoLibrePago.getPosicionTraspasante().getEmision().getEmisora().setDescripcion(listaEmisiones.get(0).getEmisora().getDescripcion());
				traspasoLibrePago.getPosicionTraspasante().getEmision().getTipoValor().setClaveTipoValor(listaEmisiones.get(0).getTipoValor().getClaveTipoValor());
				traspasoLibrePago.getPosicionTraspasante().getEmision().getSerie().setSerie(listaEmisiones.get(0).getSerie().getSerie());
				traspasoLibrePago.getPosicionTraspasante().getEmision().getTipoValor().getMercado().setClaveMercado(listaEmisiones.get(0).getTipoValor().getMercado().getClaveMercado());
				traspasoLibrePago.getPosicionTraspasante().getEmision().setIsin(listaEmisiones.get(0).getIsin());
				traspasoLibrePago.getPosicionTraspasante().getEmision().setCupon(listaEmisiones.get(0).getCupon());
				
				traspasoLibrePago.getPosicionTraspasante().setBoveda(new BovedaDTO());
				traspasoLibrePago.getPosicionTraspasante().getBoveda().setNombreCorto(listaEmisiones.get(0).getBoveda().getNombreCorto());
				traspasoLibrePago.getPosicionTraspasante().getBoveda().setId(listaEmisiones.get(0).getBoveda().getId());
				
				if (listaEmisiones.get(0).getFechaVencimiento() != null) {
					traspasoLibrePago.setDiasVigentes((int)utilService.dateDiff(fechasUtilService.getCurrentDate(),listaEmisiones.get(0).getFechaVencimiento()));
				}

			} else {
				traspasoLibrePago.getPosicionTraspasante().getEmision().setCupon(null);
				traspasoLibrePago.getPosicionTraspasante().getEmision().setIsin(null);
				traspasoLibrePago.getPosicionTraspasante().getEmision().getTipoValor().getMercado().setClaveMercado(null);
				traspasoLibrePago.getPosicionTraspasante().setBoveda(null);
				traspasoLibrePago.setSaldoDisponible(null);
				traspasoLibrePago.setDiasVigentes(null);
			}
			
		}				
		return null;
	}

    /** ************** métodos getters & setters */

    /**
     * @return the consultaCatalogosFacade
     */
    public ConsultaCatalogosFacade getConsultaCatalogosFacade() {
        return consultaCatalogosFacade;
    }

    /**
     * @param consultaCatalogosFacade the consultaCatalogosFacade to set
     */
    public void setConsultaCatalogosFacade(ConsultaCatalogosFacade consultaCatalogosFacade) {
        this.consultaCatalogosFacade = consultaCatalogosFacade;
    }

    /**
     * @return the traspasoLibrePago
     */
    public TraspasoLibrePagoDTO getTraspasoLibrePago() {
        return traspasoLibrePago;
    }

    /**
     * @param traspasoLibrePago the traspasoLibrePago to set
     */
    public void setTraspasoLibrePago(TraspasoLibrePagoDTO traspasoLibrePago) {
        this.traspasoLibrePago = traspasoLibrePago;
    }

    /**
     * @return the validadorTraspasoLibrePago
     */
    public DTOValidator getValidadorTraspasoLibrePago() {
        return validadorTraspasoLibrePago;
    }

    /**
     * @param validadorTraspasoLibrePago the validadorTraspasoLibrePago to set
     */
    public void setValidadorTraspasoLibrePago(DTOValidator validadorTraspasoLibrePago) {
        this.validadorTraspasoLibrePago = validadorTraspasoLibrePago;
    }

    /**
     * @return the envioOperacionesService
     */
    public EnvioOperacionesService getEnvioOperacionesService() {
        return envioOperacionesService;
    }

    /**
     * @param envioOperacionesService the envioOperacionesService to set
     */
    public void setEnvioOperacionesService(EnvioOperacionesService envioOperacionesService) {
        this.envioOperacionesService = envioOperacionesService;
    }

    /**
     * @return the servicesCapturaOperacionViewHelper
     */
    public ServicesCapturaOperacionViewHelper getServicesCapturaOperacionViewHelper() {
        return servicesCapturaOperacionViewHelper;
    }

    /**
     * @param servicesCapturaOperacionViewHelper the servicesCapturaOperacionViewHelper to set
     */
    public void setServicesCapturaOperacionViewHelper(ServicesCapturaOperacionViewHelper servicesCapturaOperacionViewHelper) {
        this.servicesCapturaOperacionViewHelper = servicesCapturaOperacionViewHelper;
    }

    /**
     * @return the mercadoDineroService
     */
    public MercadoDineroService getMercadoDineroService() {
        return mercadoDineroService;
    }

    /**
     * @param mercadoDineroService the mercadoDineroService to set
     */
    public void setMercadoDineroService(MercadoDineroService mercadoDineroService) {
        this.mercadoDineroService = mercadoDineroService;
    }

    /**
     * @return the configuracion
     */
    public CaptOpsConfig getConfiguracion() {
        return configuracion;
    }

    /**
     * @param configuracion the configuracion to set
     */
    public void setConfiguracion(CaptOpsConfig configuracion) {
        this.configuracion = configuracion;
    }

    /**
     * @return the calculos
     */
    public CalculoCapturaOperacionViewHelper getCalculos() {
        return calculos;
    }

    /**
     * @param calculos the calculos to set
     */
    public void setCalculos(CalculoCapturaOperacionViewHelper calculos) {
        this.calculos = calculos;
    }

    /**
     * Obtiene el valor del campo vo
     * 
     * @return el valor de vo
     */
    public TraspasoLibrePagoVO getVo() {
        return vo;
    }

    /**
     * Asigna el campo vo
     * 
     * @param vo el valor de vo a asignar
     */
    public void setVo(TraspasoLibrePagoVO vo) {
        this.vo = vo;
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
	 * Obtiene el valor del atributo idMercadoDinero
	 *
	 * @return el valor del atributo idMercadoDinero
	 */
	public List<Long> getIdMercadoDinero() {
		return idMercadoDinero;
	}

	/**
	 * Establece el valor del atributo idMercadoDinero
	 *
	 * @param idMercadoDinero el valor del atributo idMercadoDinero a establecer
	 */
	public void setIdMercadoDinero(List<Long> idMercadoDinero) {
		this.idMercadoDinero = idMercadoDinero;
	}

	/**
	 * Obtiene el valor del atributo emisionDaliDAO
	 *
	 * @return el valor del atributo emisionDaliDAO
	 */
	public EmisionDaliDAO getEmisionDAO() {
		return emisionDaliDAO;
	}

	/**
	 * Establece el valor del atributo emisionDaliDAO
	 *
	 * @param emisionDaliDAO el valor del atributo emisionDaliDAO a establecer
	 */
	public void setEmisionDAO(EmisionDaliDAO emisionDaliDAO) {
		this.emisionDaliDAO = emisionDaliDAO;
	}

	/**
	 * Obtiene el valor del atributo consultaPosicionService
	 *
	 * @return el valor del atributo consultaPosicionService
	 */
	public ConsultaPosicionService getConsultaPosicionService() {
		return consultaPosicionService;
	}

	/**
	 * Establece el valor del atributo consultaPosicionService
	 *
	 * @param consultaPosicionService el valor del atributo consultaPosicionService a establecer
	 */
	public void setConsultaPosicionService(
			ConsultaPosicionService consultaPosicionService) {
		this.consultaPosicionService = consultaPosicionService;
	}

	/**
	 * Obtiene el valor del atributo fechasUtilService
	 *
	 * @return el valor del atributo fechasUtilService
	 */
	public FechasUtilService getFechasUtilService() {
		return fechasUtilService;
	}

	/**
	 * Establece el valor del atributo fechasUtilService
	 *
	 * @param fechasUtilService el valor del atributo fechasUtilService a establecer
	 */
	public void setFechasUtilService(FechasUtilService fechasUtilService) {
		this.fechasUtilService = fechasUtilService;
	}
}