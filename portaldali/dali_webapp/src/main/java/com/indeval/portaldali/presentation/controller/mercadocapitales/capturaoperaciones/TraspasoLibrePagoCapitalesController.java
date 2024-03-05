/*
 *Copyright (c) 2005-2007 Bursatec. All Rights Reserved 
 */
package com.indeval.portaldali.presentation.controller.mercadocapitales.capturaoperaciones;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bursatec.seguridad.presentation.constants.SeguridadConstants;
import com.indeval.portaldali.middleware.dto.BovedaDTO;
import com.indeval.portaldali.middleware.dto.CriterioOrdenamientoDTO;
import com.indeval.portaldali.middleware.dto.CuentaDTO;
import com.indeval.portaldali.middleware.dto.DivisaDTO;
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
import com.indeval.portaldali.middleware.services.mercadocapitales.GetCapturaTraspasoParams;
import com.indeval.portaldali.middleware.services.mercadocapitales.MercadoCapitalesService;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import com.indeval.portaldali.middleware.services.modelo.InfrastructureException;
import com.indeval.portaldali.middleware.services.util.UtilServices;
import com.indeval.portaldali.middleware.services.util.util.Constantes;
import com.indeval.portaldali.persistence.dao.common.EmisionDaliDAO;
import com.indeval.portaldali.presentation.common.constants.UtilConstantes;
import com.indeval.portaldali.presentation.controller.mercadodinero.capturaoperaciones.CapturaOperacionesController;
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
public class TraspasoLibrePagoCapitalesController extends CapturaOperacionesController {

    /** La clase que permite el acceso a la consulta de los catálogos utilizados */
    private ConsultaCatalogosFacade consultaCatalogosFacade = null;
    /** El DTO para los elementos de la pantalla Traspaso libre de pago */
    private TraspasoLibrePagoDTO traspasoLibrePago = new TraspasoLibrePagoDTO();
    /** Servicio de negocio relacionado con el envo de operaciones */
    private EnvioOperacionesService envioOperacionesService = null;
    /** Servicio helper para la captura de operaciones */
    private ServicesCapturaOperacionViewHelper servicesCapturaOperacionViewHelper = null;
    /** Servicio de operaciones de mercado de capitales */
    MercadoCapitalesService mercadoCapitalesService = null;
    /** Configuracicn de operaciones */
    private CaptOpsConfig configuracion = null;
    /** Validador de la Captura Operaciones Reporto Nominal Opcion Mismo Dia */
    private DTOValidator validadorTraspasoLibrePago;
    /** Valid Object de traspaso contrapago */
    private TraspasoLibrePagoVO vo = null;
    /** Trasa de log */
    private final Logger log = LoggerFactory.getLogger(TraspasoLibrePagoCapitalesController.class);
    /**
     * Contiene los cálculos de Simulado, Fecha Regreso, Importe y Premio necesarios para las pantallas de Captura de Operaciones
     */
    private CalculoCapturaOperacionViewHelper calculos = new CalculoCapturaOperacionViewHelper();
    /** Servicio para sacar folio de una secuencia */
    private UtilServices utilService = null;
    /** La fecha de liquidacion */
    private Date fechaLiquidacion = new Date();
    
    /** Identificadores del mercado a filtrar*/    
	private List<Long> idMercadoCapitales = null;
	/**Dao para la obtención de la emisión*/
	private EmisionDaliDAO emisionDaliDAO = null;
	/**Servicio para la obtencion de la posicion*/
	private ConsultaPosicionService consultaPosicionService;		
	
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
        //Obtener los datos de la posicion
        obtenerDatosPosicion();
        return null;
    }
    
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
     * llevar a cabo la inserción de la Operación en la BD
     * 
     * @return String Siguiente Página a redireccionar
     */
    public void enviarOperacion(ActionEvent event) {
    	super.limpiarCampos();
    	try {
        	if(StringUtils.isBlank(validarCuenta(event)) && StringUtils.isBlank(validarCompra(event))){
    	        traspasoLibrePago.getPosicionTraspasante().getCuenta().setInstitucion(
    	        	consultaCatalogosFacade.buscarInstitucionPorIdNoNulaFolio(traspasoLibrePago.getIdFolioTraspasante()));
    	        traspasoLibrePago.getCuentaReceptor().setInstitucion(
    	        	consultaCatalogosFacade.buscarInstitucionPorIdNoNulaFolio(traspasoLibrePago.getIdFolioReceptor()));   
    			// Si el usuario debe firmar la operacion, se recuperar la firma.
    			// Si no se ha firmado, se procesan los datos y regresa el control a la pantalla para firmar
    			if (isUsuarioConFacultadFirmar()) {
    				//Valida la vigencia del certificado - 09-Enero-2015 Pablo Balderas
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
			//Valida la vigencia del certificado - 09-Enero-2015 Pablo Balderas
			validarVigenciaCertificado();
			recuperarCamposFirma();
			if (!StringUtils.isEmpty(isoFirmado)) {
				try {
					grabarOperacion(event);
				}catch(BusinessException e) {
					addMessage(e.getMessage(), FacesMessage.SEVERITY_ERROR);
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
	 * Valida que el importe de la operación no sea mayor al neto efectivo
	 * 
	 * @param e
	 */
	public String validarCuenta(ActionEvent e) {
		this.mensajeUsuario="";
		if(traspasoLibrePago.getPosicionTraspasante().getCuenta().getCuenta() != null &&
				!traspasoLibrePago.getPosicionTraspasante().getCuenta().getCuenta().equals("")) {
			if (traspasoLibrePago.getPosicionTraspasante().getCuenta().getCuenta()
					.equals(traspasoLibrePago.getCuentaReceptor().getCuenta())&&this.traspasoLibrePago.getPosicionTraspasante().getCuenta().getInstitucion()
					.getFolioInstitucion().equals(this.traspasoLibrePago.getCuentaReceptor().getInstitucion().getFolioInstitucion())) {
				mensajeUsuario = "La cuenta de Transpasante no puede ser igual a la cuenta del Receptor";
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
				mensajeUsuario = "Un TLP entre la misma insitución no necesita operación de confirmación";
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								mensajeUsuario, mensajeUsuario));
			}
		}
		return mensajeUsuario;
		
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
    	
    	enviarOperacionABitacora();
    }

	/**
     * Cambio el tipo de TLP venta o compra
     * 
     * @param ActionEvent event
     */
    public void cambioCompra(ActionEvent event) {
    	log.info("Entrando a TraspasoLibrePagoCapitalesController.cambioCompra");
    	boolean valor = traspasoLibrePago.isCompra();
    	traspasoLibrePago = new TraspasoLibrePagoDTO();
    	traspasoLibrePago.setDivisa(MXP);
    	traspasoLibrePago.setCompra(valor);
    	isoFirmado = null;
    	isoSinFirmar = "";
    	numeroSerie = null;
    	hashIso = null;
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
		BovedaDTO boveda = null;
		if(traspasoLibrePago.getPosicionTraspasante().getBoveda() != null) {			
			boveda = consultaCatalogosFacade.buscarBovedaPorId(traspasoLibrePago.getPosicionTraspasante().getBoveda().getId());
		}
		vo.setBoveda(null != boveda ? boveda.getNombreCorto() : null);
		try
		{
    	envioOperacionesService.grabaOperacion(vo, folioControl, traspasoLibrePago.isCompra(), datosAdicionales, null, isoFirmado);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
    	if (!existeErrorEnInvocacion()) {
            mensajeUsuario = "La operaci\u00f3n se llev\u00f3 a cabo exitosamente. Folio de la operaci\u00f3n : " + folioAsignado;

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, mensajeUsuario, mensajeUsuario));
            traspasoLibrePago = new TraspasoLibrePagoDTO();
            limpiarCampos(null);
        }
	}

    private void calcularYRegistrarOperacion() {

        // buscar la divisa por id
    	DivisaDTO divisa = consultaCatalogosFacade.buscarDivisaPorId(traspasoLibrePago.getDivisa().getId());
    	BovedaDTO boveda = null;
    	if(traspasoLibrePago.getPosicionTraspasante().getBoveda() != null) {
    		boveda = consultaCatalogosFacade.buscarBovedaPorId(traspasoLibrePago.getPosicionTraspasante().getBoveda().getId());
    	}
        traspasoLibrePago.setDivisa(divisa);
        traspasoLibrePago.setBoveda(boveda);

        boolean encontroError = false;
        GetCapturaTraspasoParams registraOperacionParams = new GetCapturaTraspasoParams();

        // --- AQUI SE LLEVA A CABO LA INSERCION DE LA OPERACION

        registraOperacionParams.setFechaLiquidacion(traspasoLibrePago.getFechaLiquidacion());
        registraOperacionParams.setFechaHoraCierreOper(traspasoLibrePago.getFechaHoraCierreOper());
        // --- TIPO DE OPERACION, TRASPASO LIBRE DE PAGO ("T")
        registraOperacionParams.setCveReporto("T");

        // --- AGENTE TRASPASANTE
        registraOperacionParams
                .setTraspasante(DTOAssembler.crearAgenteVO(traspasoLibrePago.getPosicionTraspasante().getCuenta()));

        // --- AGENTE DEL RECEPTOR
        registraOperacionParams.setReceptor(DTOAssembler.crearAgenteVO(traspasoLibrePago.getCuentaReceptor()));
        registraOperacionParams.setEmision(new EmisionVO());
        registraOperacionParams.setCantidad(new BigDecimal(traspasoLibrePago.getCantidadOperada()));

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

        calculos.setTipoOperacion(registraOperacionParams.getCveReporto());
        calculos.setCantidadOperadaLeida(traspasoLibrePago.getCantidadOperada() != null ? traspasoLibrePago.getCantidadOperada()
                .toString() : "0");
        calculos.setSaldoDisponibleLeido(traspasoLibrePago.getSaldoDisponible() != null ? traspasoLibrePago.getSaldoDisponible()
                .toString() : "0");
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

        if(!encontroError) {
	        folioAsignado = mercadoCapitalesService.businessRulesCapturaTraspaso(registraOperacionParams);
	        
	        if (!existeErrorEnInvocacion()) {
	            vo = new TraspasoLibrePagoVO();
	
	            vo = inicializaTraspasoContrapagoVO(registraOperacionParams);
	            vo.setReferenciaOperacion(folioAsignado);
	            vo.setTipoInstruccion(calculos.getTipoOperacion());
	            vo.setReferenciaMensaje(utilService.getFolio(Constantes.SEQ_REFERENCIA_MENSAJE).toString());	            
	            vo.setBoveda(null != boveda ? boveda.getNombreCorto() : null);
	            
	            // Si el usuario debe firmar la operacion, se crea el iso
	            if(isUsuarioConFacultadFirmar()) {
		            isoSinFirmar = isoHelper.creaISO(vo,  traspasoLibrePago.isCompra());
		            hashIso = cdb.cipherHash(isoSinFirmar);
	            } else {
	            	enviarOperacionABitacora();
	            }
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
     * Ejecuta las validaciones de los DTOs de captura de acuerdo al tipo de operación a realizar
     * 
     * @return
     */
    private boolean validarDTO() {
        ResultadoValidacionDTO resultadao = null;
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
        isoFirmado = null;
        isoSinFirmar = "";
        numeroSerie = null;
        hashIso = null;
        getInit();
    }
    
    /**
     * Crea un objeto TraspasoLibrePagoVO y lo llena con los valores del objeto registraOperacionParams.
     * Este m&eacute;todo tiene como objetivo eliminar la redundancia de codigo que puede existir al momento de incializar
     * el objeto vo en cada una de los tipos de operaciones 
     *  
     * @param registraOperacionParams Objeto de donde se tomara la informacion
     * @return vo con los campos inicializados en base al objeto proporcionado como parametros
     */
    private TraspasoLibrePagoVO inicializaTraspasoContrapagoVO(GetCapturaTraspasoParams registraOperacionParams) {
        
    	TraspasoLibrePagoVO vo = new TraspasoLibrePagoVO();
    	
		vo.setTipoValor(registraOperacionParams.getEmision().getIdTipoValor().trim());
        vo.setEmisora(registraOperacionParams.getEmision().getEmisora().trim());
        vo.setSerie(registraOperacionParams.getEmision().getSerie().trim());
        vo.setCupon(registraOperacionParams.getEmision().getCupon().trim());
        vo.setCantidadTitulos(new Long(registraOperacionParams.getCantidad().longValue()));
        vo.setIdFolioCtaReceptora(   registraOperacionParams.getReceptor().getId()+ 
                registraOperacionParams.getReceptor().getFolio() + 
                registraOperacionParams.getReceptor().getCuenta());
        vo.setIdFolioCtaTraspasante( registraOperacionParams.getTraspasante().getId() + 
                registraOperacionParams.getTraspasante().getFolio() +
                registraOperacionParams.getTraspasante().getCuenta());
        vo.setFechaLiquidacion(registraOperacionParams.getFechaLiquidacion());
        vo.setFechaRegistro( registraOperacionParams.getFechaLiquidacion() );
        vo.setFechaHoraCierreOper(registraOperacionParams.getFechaHoraCierreOper());

		return vo;
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
				
				List<PosicionDTO> listaPosiciones = consultaPosicionService.consultarPosicionesPorMercado(criterio, null, orden, idMercadoCapitales.toArray(new Long[]{}));
				
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
					traspasoLibrePago.getPosicionTraspasante().getEmision().getTipoValor().getMercado().setClaveMercado(
							posicion.getEmision().getTipoValor().getMercado().getClaveMercado());
					traspasoLibrePago.getPosicionTraspasante().setBoveda(new BovedaDTO());
					traspasoLibrePago.getPosicionTraspasante().getBoveda().setNombreCorto(posicion.getBoveda().getNombreCorto());
					traspasoLibrePago.getPosicionTraspasante().getBoveda().setId(posicion.getBoveda().getId());
					traspasoLibrePago.getPosicionTraspasante().getEmision().setIsin(posicion.getEmision().getIsin());
					traspasoLibrePago.setSaldoDisponible(new BigDecimal(posicion.getPosicionDisponible()).longValue());
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

	public MercadoCapitalesService getMercadoCapitalesService() {
		return mercadoCapitalesService;
	}

	public void setMercadoCapitalesService(
			MercadoCapitalesService mercadoCapitalesService) {
		this.mercadoCapitalesService = mercadoCapitalesService;
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
	 * Obtiene el valor del atributo idMercadoCapitales
	 *
	 * @return el valor del atributo idMercadoCapitales
	 */
	public List<Long> getIdMercadoCapitales() {
		return idMercadoCapitales;
	}

	/**
	 * Establece el valor del atributo idMercadoCapitales
	 *
	 * @param idMercadoCapitales el valor del atributo idMercadoCapitales a establecer
	 */
	public void setIdMercadoCapitales(List<Long> idMercadoCapitales) {
		this.idMercadoCapitales = idMercadoCapitales;
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
	

}