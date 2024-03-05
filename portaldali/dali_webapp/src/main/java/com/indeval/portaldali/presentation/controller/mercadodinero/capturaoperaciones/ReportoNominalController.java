/*
 *Copyright (c) 2005-2007 Bursatec. All Rights Reserved
 */
package com.indeval.portaldali.presentation.controller.mercadodinero.capturaoperaciones;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

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
import com.indeval.portaldali.middleware.dto.TipoInstruccionDTO;
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
import com.indeval.portaldali.presentation.dto.mercadodinero.ReportoNominalDTO;
import com.indeval.portaldali.presentation.helper.CalculoCapturaOperacionViewHelper;
import com.indeval.portaldali.presentation.helper.ServicesCapturaOperacionViewHelper;
import com.indeval.portaldali.presentation.util.ConsultaCatalogosFacade;
import com.indeval.portaldali.presentation.validation.DTOValidator;
import com.indeval.portaldali.vo.CaptOpsConfig;
import com.indeval.protocolofinanciero.api.vo.TraspasoContraPagoVO;

/**
 * Controller de la Captura de Operaciones para la opción de Reporto nominal.
 *
 * @author Juan Carlos Huizar Moreno
 * @version 1.0
 */
public class ReportoNominalController extends CapturaOperacionesController {

    /** La clase que permite el acceso a la consulta de los catálogos utilizados */
    private ConsultaCatalogosFacade consultaCatalogosFacade = null;
    /** El DTO que representa a los elementos de la pantalla de reporto nominal */
    private ReportoNominalDTO reporto = new ReportoNominalDTO();
    /** Servicio de negocio relacionado con el envo de operaciones */
    private EnvioOperacionesService envioOperacionesService = null;
    /** Servicio helper para la captura de operaciones */
    private ServicesCapturaOperacionViewHelper servicesCapturaOperacionViewHelper = null;
    /** Servicio de operaciones de mercado de dinero */
    private MercadoDineroService mercadoDineroService = null;
    /** Configuracicn de operaciones */
    private CaptOpsConfig configuracion = null;
    /** Validador Captura Operaciones Reporto Nominal Opcion Mismo Dia */
    private DTOValidator validadorMD = null;
    /** Validador Captura Operaciones Reporto Nominal Opcion Fecha Valor */
    private DTOValidator validadorFV = null;
    /** Validador Captura Operaciones Reporto Nominal Opcion Compra En MD */
    private DTOValidator validadorCompraMD = null;
    /** Validador Captura Operaciones Reporto Nominal Opcion Compra En FV */
    private DTOValidator validadorCompraFV = null;
    /** Valid Object de traspaso contrapago */
    private TraspasoContraPagoVO vo = null;

    private boolean cambioMismoDia = Boolean.FALSE;

    private boolean editarTraspasante = Boolean.FALSE;

    private boolean editarReceptor = Boolean.TRUE;
    /**
     * Contiene los cálculos de Simulado, Fecha Regreso, Importe y Premio necesarios para las pantallas de Captura de Operaciones
     */
    private CalculoCapturaOperacionViewHelper calculos = new CalculoCapturaOperacionViewHelper();
    /** Servicio para calcular la fecha de liquidación con dias habiles*/
    private UtilServices utilService = null;

    /** Identificadores del mercado a filtrar*/
	private List<Long> idMercadoDinero = null;
	/**Dao para la obtención de la emisión*/
	private EmisionDaliDAO emisionDaliDAO = null;
	/**Servicio para la obtencion de la posicion*/
	private ConsultaPosicionService consultaPosicionService;
	/** Servicio para el acceso al cálculo de fechas*/
	private FechasUtilService fechasUtilService = null;

    public String getInitForm(){
    	reporto.setMismoDia(CapturaOperacionesConstants.COMPRA_MISMO_DIA);
    	reporto.setCompraReporto(Boolean.FALSE);
    	reporto.setIdFolioTraspasante(getInstitucionActual().getClaveTipoInstitucion() + getInstitucionActual().getFolioInstitucion());
    	reporto.getPosicionTraspasante().getCuenta().setInstitucion(getInstitucionActual());
    	reporto.getPosicionTraspasante().setBoveda(new BovedaDTO());
    	reporto.setDivisa(MXP);
    	return null;
    }

    /**
     * Indica si la compra se realiza el mismo día o no (compra segun la fecha).
     *
     * @return <code>true</code> si la compra se realiza el mismo día. <code>false</code> si la compra se realiza segun la
     *         fecha.
     */
    public boolean isCompraMismoDia() {
        return reporto.getMismoDia() != null && reporto.getMismoDia().equals(CapturaOperacionesConstants.COMPRA_MISMO_DIA);
    }

    /**
     * Indica si la compra se realiza en directo.
     *
     * @return <code>true</code> si la compra se realiza en reporto el mismo día. <code>false</code> si la compra se realiza
     *         segun la fecha.
     */
    public boolean isCompraEnReporto() {
        return reporto.getCompraReporto() != null && reporto.getCompraReporto().booleanValue();
    }

    /**
     * Inicializa los datos de la página. *
     *
     * @return <code>null</code> este método no retorna nada.
     */
    public String getInit() {

    	if(isUsuarioIndeval()) {
    		editarReceptor = Boolean.TRUE;
    		editarTraspasante = Boolean.TRUE;
    	}

    	reporto.setFechaConcertacion(servicesCapturaOperacionViewHelper.getFechasUtilService().getCurrentDate());
        reporto.setFechaLiquidacion(servicesCapturaOperacionViewHelper.getFechasUtilService().getCurrentDate());

        if (reporto.getMismoDia() == null){
        	reporto.setMismoDia(CapturaOperacionesConstants.COMPRA_MISMO_DIA);
        }
        if(reporto.getCompraReporto() == null) {

            reporto.setCompraReporto(Boolean.FALSE);

        }

        if(! reporto.getCompraReporto().booleanValue() && !isUsuarioIndeval()){
        	reporto.setIdFolioTraspasante(getInstitucionActual().getClaveTipoInstitucion()
                    + getInstitucionActual().getFolioInstitucion());
        }

        if (reporto.getIdFolioReceptor() != null) {
            InstitucionDTO inst = consultaCatalogosFacade.buscarInstitucionPorIdFolio(reporto.getIdFolioReceptor());
            if (inst != null) {
                reporto.getCuentaReceptor().setInstitucion(inst);
            } else {
            	reporto.setIdFolioReceptor(StringUtils.EMPTY);
            	reporto.setCuentaReceptor(new CuentaDTO());
			}
        }
        if (reporto.getIdFolioTraspasante() != null) {
            InstitucionDTO inst = consultaCatalogosFacade.buscarInstitucionPorIdFolio(reporto.getIdFolioTraspasante());
            if (inst != null) {
                reporto.getPosicionTraspasante().getCuenta().setInstitucion(inst);
                reporto.setNetoEfectivo(consultaCatalogosFacade.getSaldoNetoEfectivo(reporto.getIdFolioTraspasante()));
            }else {
            	reporto.setNetoEfectivo(null);
            	reporto.setIdFolioTraspasante(StringUtils.EMPTY);
			}
        }else{
        	reporto.setNetoEfectivo(null);
        }
        if (reporto.getCuentaReceptor().getCuenta() != null) {
            CuentaDTO cnt = consultaCatalogosFacade.buscarCuentaPorNumeroCuentaNullSiNoExiste(reporto.getIdFolioReceptor()
                    + reporto.getCuentaReceptor().getCuenta());

            if (cnt != null) {
                reporto.setCuentaReceptor(cnt);
            }else {
            	reporto.getCuentaReceptor().setTipoTenencia(new TipoTenenciaDTO());
            	reporto.getCuentaReceptor().setNumeroCuenta(StringUtils.EMPTY);
            	reporto.getCuentaReceptor().setCuenta(StringUtils.EMPTY);
            	reporto.getCuentaReceptor().setNombreCuenta(StringUtils.EMPTY);
			}
        }
        if (reporto.getPosicionTraspasante().getCuenta().getCuenta() != null) {
            CuentaDTO cnt = consultaCatalogosFacade.buscarCuentaPorNumeroCuentaNullSiNoExiste(reporto.getIdFolioTraspasante()
                    + reporto.getPosicionTraspasante().getCuenta().getCuenta());
            if (cnt != null) {
                reporto.getPosicionTraspasante().setCuenta(cnt);
            } else {

            	reporto.getPosicionTraspasante().getCuenta().setTipoTenencia(new TipoTenenciaDTO());
            	reporto.getPosicionTraspasante().getCuenta().setNumeroCuenta(StringUtils.EMPTY);
            	reporto.getPosicionTraspasante().getCuenta().setCuenta(StringUtils.EMPTY);
            	reporto.getPosicionTraspasante().getCuenta().setNombreCuenta(StringUtils.EMPTY);

			}
        }
        //obtener los datos de la posicion
        obtenerDatosPosicion();
        return null;
    }

    /**
     * ActionListener para la casilla de selección que permite seleccionar si la compra se realiza en reporto o no.
     *
     * @param event el evento que dispar este ActionListener
     */
    public void cambioCompraDirecto(ActionEvent event) {

    	if (reporto.getCompraReporto() != null && reporto.getCompraReporto() == Boolean.TRUE) {
        	editarReceptor = Boolean.FALSE;
        	editarTraspasante = Boolean.TRUE;
            reporto.getPosicionTraspasante().setCuenta(new CuentaDTO());
            reporto.setIdFolioTraspasante(StringUtils.EMPTY);
            reporto.setCuentaReceptor(new CuentaDTO());
            reporto.setIdFolioReceptor(getInstitucionActual().getClaveTipoInstitucion()
                    + getInstitucionActual().getFolioInstitucion());
            reporto.setFechaConcertacion(servicesCapturaOperacionViewHelper.getFechasUtilService().getCurrentDate());
            getInit();
            reporto.setBovedaEfectivo(BANXICO);
        } else {
        	editarReceptor = Boolean.TRUE;
        	editarTraspasante = Boolean.FALSE;
            reporto = new ReportoNominalDTO();
            getInit();
            reporto.setBovedaEfectivo(new BovedaDTO());
        }
    }

    /**
     * Listener para el guardar que permite persistir la Operación en la BD
     *
     * @return String Siguiente Página a redireccionar
     */
    public void enviarOperacion(ActionEvent event) {
    	super.limpiarCampos();
    	
		try {				
			if(StringUtils.isEmpty(validarIdFolioParticipantes()) && StringUtils.isEmpty(validarCuentas(event))){
				InstitucionDTO traspasante = consultaCatalogosFacade.buscarInstitucionPorIdNoNulaFolio(reporto.getIdFolioTraspasante());
				InstitucionDTO receptor = consultaCatalogosFacade.buscarInstitucionPorIdNoNulaFolio(reporto.getIdFolioReceptor());
				if(traspasante != null && StringUtils.isNotBlank(traspasante.getClaveTipoInstitucion())) {
					reporto.getPosicionTraspasante().getCuenta().setInstitucion(traspasante);
					if(receptor != null && StringUtils.isNotBlank(receptor.getClaveTipoInstitucion())) {
						reporto.getCuentaReceptor().setInstitucion(receptor);
						// Si el usuario debe firmar la operacion, se recuperar la firma.
						// Si no se ha firmado, se procesan los datos y regresa el control a la pantalla para firmar
						if(isUsuarioConFacultadFirmar()) {
							//Valida la vigencia del certificado - 18-Agosto-2014 Pablo Balderas
							validarVigenciaCertificado();
						}
						
						procesarDatos();
					}
					else {
						mensajeUsuario = "La clave Tipo Instituci\u00f3n Receptor no existe";
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, mensajeUsuario, mensajeUsuario));
					}
				}
				else {
					mensajeUsuario = "La clave Tipo Instituci\u00f3n Trasapasante no existe";
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, mensajeUsuario, mensajeUsuario));
				}
			}
		}
		catch(BusinessException businessException) {
    		addMessage(businessException.getMessage(), FacesMessage.SEVERITY_ERROR);
    	}
    }

    public void enviarOperacion2(ActionEvent event) {
    	
    	if(isUsuarioConFacultadFirmar()) {
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
			calcularYRegistrarOperacicon();
		}
	}

    /**
     * Guarda la operacion firmada
     *
     * @param e
     */
    public void grabarOperacion(ActionEvent e) {
    	if(!cdb.validation(hashIso, isoSinFirmar)){
    		throw new InfrastructureException(UtilConstantes.ERROR_ISO_DIFERENTE);
    	}
    	isoFirmado = (new StringBuilder()).append(isoSinFirmar).append(numeroSerie).append("\n").append("{SHA1withRSA}").append(isoFirmado).toString();
    	logger.debug("isoFirmado: '" + isoFirmado + "'");
    	if (validarDTO()) {
    		enviarOperacionABitacora();
    	}
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

		DivisaDTO divisa = consultaCatalogosFacade.buscarDivisaPorId(reporto.getDivisa().getId());
		vo.setDivisa( null != divisa ?  divisa.getClaveAlfabetica() : null );

    	envioOperacionesService.grabaOperacion(vo, folioControl, reporto.getCompraReporto() != null ? reporto.getCompraReporto()
                .booleanValue() : false, datosAdicionales, null, isoFirmado);

        if (!existeErrorEnInvocacion()) {
            if (reporto.getCompraReporto().booleanValue()) {
                mensajeUsuario = "La operaci\u00f3n se llev\u00f3 a cabo exitosamente. Folio usuario : " + folioAsignado;
            } else {
                mensajeUsuario = "La operaci\u00f3n se llev\u00f3 a cabo exitosamente. Folio de la operaci\u00f3n : "  + folioAsignado;
            }
            FacesContext.getCurrentInstance().addMessage(null,  new FacesMessage(FacesMessage.SEVERITY_INFO, mensajeUsuario, mensajeUsuario));
            reporto = new ReportoNominalDTO();
            limpiarCampos(null);
        }
	}

	/**
	 * Valida que el Id Folio del Transpasante o Receptor no este vacio
	 *
	 * @param e
	 */
	private String validarIdFolioParticipantes() {
		mensajeUsuario = "";

		if(StringUtils.isBlank(reporto.getIdFolioTraspasante())) {
			mensajeUsuario = "La clave Tipo Instituci\u00f3n Trasapasante es requerida";
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, mensajeUsuario, mensajeUsuario));
		}
		else if(StringUtils.isBlank(reporto.getIdFolioReceptor())) {
			mensajeUsuario = "La clave Tipo Instituci\u00f3n Receptor es requerida";
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, mensajeUsuario, mensajeUsuario));
		}

		return mensajeUsuario;

	}

	/**
	 * Valida que la cuenta del Transpasante no pede ser igual a la cuenta del Receptor
	 *
	 * @param e
	 */
	public String validarCuentas(ActionEvent e) {
		mensajeUsuario="";
		if(reporto.getPosicionTraspasante().getCuenta().getCuenta() != null &&
				!reporto.getPosicionTraspasante().getCuenta().getCuenta().equals("")) {
			if (reporto.getPosicionTraspasante().getCuenta().getCuenta()
					.equals(reporto.getCuentaReceptor().getCuenta())
					&&(reporto.getIdFolioTraspasante().equals(reporto.getIdFolioReceptor()))) {
				mensajeUsuario =  "La cuenta traspasante no puede ser la misma que la del receptor";
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								mensajeUsuario, mensajeUsuario));
				reporto.getCuentaReceptor().setCuenta("");
			}
		}
		return mensajeUsuario;
	}
    /**
	 * Valida que el importe de la operacion no sea mayor al neto efectivo
	 *
	 * @param e
	 */
	public String validarCuenta(ActionEvent e) {

		if(reporto.getPosicionTraspasante().getCuenta().getCuenta() != null &&
				!reporto.getPosicionTraspasante().getCuenta().getCuenta().equals("")) {
			if (reporto.getPosicionTraspasante().getCuenta().getCuenta()
					.equals(reporto.getCuentaReceptor().getCuenta())
					&&(reporto.getIdFolioTraspasante().equals(reporto.getIdFolioReceptor()))) {
				mensajeUsuario =  "La cuenta traspasante no puede ser la misma que la del receptor";
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								mensajeUsuario, mensajeUsuario));
				if (isCompraEnReporto() == Boolean.TRUE) {
					reporto.getPosicionTraspasante().getCuenta()
					.setCuenta("");
				} else {
					reporto.getCuentaReceptor().setCuenta("");
				}
			}
		}
		return mensajeUsuario;
	}

    /**
     * Realiza los cálculos simulados de la operacicn y construye el objeto de parámetros que el servicio de negocio espera
     */
    private void calcularYRegistrarOperacicon() {

        // buscar la divisa y boveda por id
        reporto.setDivisa(consultaCatalogosFacade.buscarDivisaPorId(reporto.getDivisa().getId()));
        if(reporto.getPosicionTraspasante().getBoveda() != null  &&  reporto.getPosicionTraspasante().getBoveda().getId() >0 ) {
        	reporto.getPosicionTraspasante().setBoveda(consultaCatalogosFacade.buscarBovedaPorId(reporto.getPosicionTraspasante().getBoveda().getId()));
        }

        if( null != reporto.getBovedaEfectivo() &&   reporto.getBovedaEfectivo().getId() >0 ){
        	reporto.setBovedaEfectivo(consultaCatalogosFacade.buscarBovedaPorId(reporto.getBovedaEfectivo().getId()));
        }else{
        	reporto.setBovedaEfectivo(null);
        }

        boolean encontroError = false;
        RegistraOperacionParams registraOperacionParams = new RegistraOperacionParams();

        // --- AQUI SE LLEVA A CABO LA INSERCION DE LA OPERACION
        // Se deja fijo el ROL DEL AGENTE FIRMADO de Traspasante (T), Receptor
        // (R)
        if (reporto.getCompraReporto().equals(Boolean.TRUE)) {
            registraOperacionParams.setRol("R");
        } else {
            registraOperacionParams.setRol("T");
        }

        // --- TIPO DE OPERACION "R" PARA REPORTO NOMINAL
        registraOperacionParams.setClaveReporto("R");

        // --- AGENTE TRASPASANTE
        registraOperacionParams.setTraspasante(DTOAssembler.crearAgenteVO(reporto.getPosicionTraspasante().getCuenta()));

        // --- AGENTE DEL RECEPTOR
        registraOperacionParams.setReceptor(DTOAssembler.crearAgenteVO(reporto.getCuentaReceptor()));
        registraOperacionParams.setEmision(new EmisionVO());

        // --- EMISION
        if (reporto.getPrecioVector() != null) {
            registraOperacionParams.getEmision().setPrecioVector(new BigDecimal(reporto.getPrecioVector()));
        }

        // --- SALDO DISPONIBLE
        if (reporto.getSaldoDisponible() != null) {
            registraOperacionParams.getEmision().setSaldoDisponible(new BigDecimal(reporto.getSaldoDisponible().doubleValue()));
        }

        // -- TIPO VALOR, --EMISORA, --SERIE, --CUPON
        registraOperacionParams.getEmision().setIdTipoValor(
                reporto.getPosicionTraspasante().getEmision().getTipoValor().getClaveTipoValor());
        registraOperacionParams.getEmision().setEmisora(
                reporto.getPosicionTraspasante().getEmision().getEmisora().getDescripcion());
        registraOperacionParams.getEmision().setSerie(reporto.getPosicionTraspasante().getEmision().getSerie().getSerie());
        registraOperacionParams.getEmision().setCupon(reporto.getPosicionTraspasante().getEmision().getCupon());
        registraOperacionParams.getEmision().setMercado(reporto.getPosicionTraspasante().getEmision().getTipoValor().getMercado().getClaveMercado());

        // --- DIAS VIGENTES
        if (reporto.getDiasVigentes() != null) {
            registraOperacionParams.setDiasVigentes(reporto.getDiasVigentes());
        }

        // --- SIMULADO
        if (reporto.getSimulado() != null) {
            registraOperacionParams.setSimulado(new BigInteger(reporto.getSimulado().toString()));
        }
        // --- NETO EFECTIVO
        if (reporto.getNetoEfectivo() != null) {
            registraOperacionParams.setNetoEfectivo(new BigDecimal(reporto.getNetoEfectivo()));
        }

        // --- PLAZO REP.(DIAS)
        if (reporto.getPlazoRepDias() != null && !reporto.getPlazoRepDias().equals("")) {
            registraOperacionParams.setPlazoReporto(Integer.valueOf(reporto.getPlazoRepDias()));
        }

        // --- PLAZO LIQ.(HORAS)
        if (!isCompraMismoDia()) {
            registraOperacionParams.setPlazoLiquidacion(reporto.getPlazoLiquidacionHoras());
        } else {
            // --- AQUI SE INTRODUCE UN "CERO" PARA EL CASO DE QUE VENGA NULO EL
            // --- PLAZO LIQUIDACION(HORAS) ESTO APLICA TANTO PARA OPERACIONES
            // --- "MISMO DIA" Y PARA LOS TIPOS DE OPERACION
            // --- FONDEO CUENTA PROPIA Y TRASPASO LIBRE DE PAGO

            registraOperacionParams.setPlazoLiquidacion(Integer.valueOf("0"));
        }

        CalculoCapturaOperacionViewHelper calculos = new CalculoCapturaOperacionViewHelper();

        calculos.setTipoOperacion(registraOperacionParams.getClaveReporto());
        calculos.setCantidadOperadaLeida(reporto.getCantidadOperada().toString());
        calculos.setSaldoDisponibleLeido(reporto.getSaldoDisponible() != null ? reporto.getSaldoDisponible().toString() : "0");
        calculos.setPlazoRepDiasLeido(registraOperacionParams.getPlazoReporto().toString());
        calculos.setPlazoLiquidacionHorasLeido(reporto.getPlazoLiquidacionHoras() != null ? reporto.getPlazoLiquidacionHoras()
                .toString() : "0");
        calculos.setPlazoRepDiasInhabilitadoLeido("false");
        calculos.setPlazoLiquidacionHorasInhabilitadoLeido("false");
        calculos.setPrecioTituloLeido(reporto.getPrecioTitulo().toString());
        calculos.setPrecioTituloInhabilitadoLeido("false");
        calculos.setImporteInhabilitadoLeido("false");
        calculos.setTasaPremioInhabilitadoLeido("false");
        calculos.setTasaPremioLeido(null);

        servicesCapturaOperacionViewHelper.realizarCalculos(calculos);

        // --- ENCONTRO UN ERROR AL CALCULAR LA FECHA DE REGRESO
        if (calculos != null && calculos.getMensajeFechaRegreso() != null
                && !calculos.getMensajeFechaRegreso().trim().equalsIgnoreCase("")) {
            encontroError = true;
        }
        // --- ENCONTRO UN ERROR AL CALCULAR EL IMPORTE
        if (calculos != null && calculos.getMensajeImporte() != null && !calculos.getMensajeImporte().trim().equalsIgnoreCase("")) {
            encontroError = true;
        }
        // --- ENCONTRO UN ERROR AL CALCULAR EL PRECIO TITULO
        if (calculos != null && calculos.getMensajePrecioTitulo() != null
                && !calculos.getMensajePrecioTitulo().trim().equalsIgnoreCase("")) {
            encontroError = true;
        }
        // --- ENCONTRO UN ERROR AL CALCULAR EL PREMIO
        if (calculos != null && calculos.getMensajePremio() != null && !calculos.getMensajePremio().trim().equalsIgnoreCase("")) {
            encontroError = true;
        }
        // --- ENCONTRO UN ERROR AL CALCULAR EL SIMULADO
        if (calculos != null && calculos.getMensajeSimulado() != null
                && !calculos.getMensajeSimulado().trim().equalsIgnoreCase("")) {
            encontroError = true;
        }

        if (encontroError) {
        } else {
            // --- FECHA REGRESO
            if (calculos.getFechaRegreso() != null) {
                try {
                    registraOperacionParams.setFechaRegreso(calculos.getFechaRegreso());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                registraOperacionParams.setFechaRegreso(servicesCapturaOperacionViewHelper.getFechasUtilService()
                        .getCurrentDate());
            }

            // --- TASA PREMIO
            if (calculos.getPremio() != null && !calculos.getPremio().equals("")) {
                registraOperacionParams.setTasaPremio(calculos.getPremio());
            }
            if (reporto.getTasaPremio() != null && !reporto.getTasaPremio().equals(0)) {
                registraOperacionParams.setTasaPremio(new BigDecimal(reporto.getTasaPremio()));
            }

            // --- FECHA CONCERTACION
            if (reporto.getFechaConcertacion() != null) {
                registraOperacionParams.setFechaConcertacion(reporto.getFechaConcertacion());
            }

            // --- CANTIDAD OPERADA
            if (reporto.getCantidadOperada() != null) {
                registraOperacionParams.setCantidadOperada(new BigDecimal(reporto.getCantidadOperada()));
            } else {
                registraOperacionParams.setCantidadOperada(new BigDecimal(0));
            }

            // --- BOVEDA TRASPASANTE
            if (reporto.getPosicionTraspasante().getBoveda() != null) {
                registraOperacionParams.setIdBoveda(BigInteger.valueOf(reporto.getPosicionTraspasante().getBoveda().getId()));
            }

            // --- VALOR EN
            if (reporto.getDivisa() != null) {
                registraOperacionParams.setDivisa(reporto.getDivisa().getClaveAlfabetica());
            }

            // --- PRECIO TITULO
            if (reporto.getPrecioTitulo() != null) {
                registraOperacionParams.setPrecioTitulo(new BigDecimal(reporto.getPrecioTitulo()));
            } else {
                registraOperacionParams.setPrecioTitulo(new BigDecimal(0));
            }

            // --- IMPORTE
            if (calculos.getImporte() != null) {
                registraOperacionParams.setImporte(calculos.getImporte());
            }
            if (reporto.getImporte() != null) {
            	/**se pone la restriccion de solo 2 decimales y que redonde al valor siguiente*/
                registraOperacionParams.setImporte( (new BigDecimal(reporto.getPrecioTitulo() * reporto.getCantidadOperada())).setScale(2, BigDecimal.ROUND_HALF_UP));
            } else {
                registraOperacionParams.setImporte(new BigDecimal(0));
            }

            // --- PREMIO
            if (reporto.getPremio() != null && !reporto.getPremio().equals(0)) {
                registraOperacionParams.setPremio(new BigDecimal(reporto.getPremio()));
            } else {
                registraOperacionParams.setPremio(new BigDecimal(0));
            }

            registraOperacionParams.setFechaLiq(servicesCapturaOperacionViewHelper
                    .calculaFechaLiquidacion(registraOperacionParams.getPlazoLiquidacion()));
            registraOperacionParams.setFechaHoraCierreOper(reporto.getFechaHoraCierreOper());
            
            if (this.configuracion.getTiposOperacionesFirmadas().contains(registraOperacionParams.getClaveReporto())) {

                folioAsignado = mercadoDineroService.validaRegistraOperacionBusinessRules(registraOperacionParams);
                if (!existeErrorEnInvocacion()) {
                    vo = new TraspasoContraPagoVO();

                    vo = servicesCapturaOperacionViewHelper.inicializaTraspasoContrapagoVO(registraOperacionParams);
                    vo.setReferenciaOperacion(folioAsignado);
                    vo.setReferenciaMensaje(utilService.getFolio(Constantes.SEQ_REFERENCIA_MENSAJE).toString());
                    vo.setTipoInstruccion(calculos.getTipoOperacion());

                    vo.setTasaNegociada(new Double(registraOperacionParams.getTasaPremio().doubleValue()));
                    vo.setTasaReferencia(new Double(registraOperacionParams.getTasaPremio().doubleValue()));

                    vo.setTasaFija(true);
                    vo.setFechaVencimiento(registraOperacionParams.getFechaRegreso());
                    vo.setPrecio(registraOperacionParams.getPrecioTitulo());
                    if(reporto.getPosicionTraspasante().getBoveda() != null ){
                    	vo.setBoveda(reporto.getPosicionTraspasante().getBoveda().getNombreCorto());
                    }else{
                    	vo.setBoveda(null);
                    }
                    if(reporto.getBovedaEfectivo() != null ){
                    	vo.setBovedaEfectivo(reporto.getBovedaEfectivo().getNombreCorto());
                    }else{
                    	vo.setBovedaEfectivo(null);
                    }

                    vo.setDivisa(reporto.getDivisa().getClaveAlfabetica());

                    // Si el usuario debe firmar la operacion, se crea el iso
                    if(isUsuarioConFacultadFirmar() && validarDTO()) {
	                    isoSinFirmar = isoHelper.creaISO(vo, reporto.getCompraReporto() != null ? reporto.getCompraReporto()
	                            .booleanValue() : false);
	                    hashIso = cdb.cipherHash(isoSinFirmar);
                    } else {
                    	enviarOperacionABitacora();
                    }
                }

            } else {
                // TODO operacion no soportada
            }
        }
    }

    /** Calcular el saldo simulado. */
    public void realizarCalculo(ActionEvent e) {
        // EL TIPO DE OPERACION ES REPORTO NOMINAL ("R")
        calculos.setTipoOperacion("R");

        calculos.setCantidadOperadaLeida(reporto.getCantidadOperada() != null ? reporto.getCantidadOperada().toString() : "");
        calculos.setCantidadOperada(new BigDecimal(reporto.getCantidadOperada() != null ? reporto.getCantidadOperada()
                .doubleValue() : 0));
        calculos.setImporte(new BigDecimal(reporto.getImporte() != null ? reporto.getImporte().doubleValue() : 0));
        calculos.setSaldoDisponibleLeido(reporto.getSaldoDisponible() != null ? reporto.getSaldoDisponible().toString() : "");
        calculos.setPlazoRepDiasLeido(reporto.getPlazoRepDias() != null ? reporto.getPlazoRepDias().toString() : "");
        calculos.setPlazoLiquidacionHorasLeido(reporto.getPlazoLiquidacionHoras() != null ? reporto.getPlazoLiquidacionHoras()
                .toString() : "");
        calculos.setPrecioTituloLeido(reporto.getPrecioTitulo() != null ? reporto.getPrecioTitulo().toString() : "");
        calculos.setPrecioTitulo(new BigDecimal(reporto.getPrecioTitulo() != null ? reporto.getPrecioTitulo().doubleValue() : 0));
        calculos.setTasaPremioLeido(reporto.getTasaPremio() != null ? reporto.getTasaPremio().toString() : "");

        //Campos cuando Fecha Valor este Habilitado
        if (CapturaOperacionesConstants.COMPRA_FECHA_VALOR.equals(reporto.getMismoDia())) {
        	calculos.setPlazoLiquidacionHorasLeido(reporto.getPlazoLiquidacionHoras() != null ? reporto.getPlazoLiquidacionHoras()
                    .toString() : "0");
        	calculos.setPlazoLiquidacionHorasInhabilitadoLeido("false");
        }

        calculos.setPrecioTituloInhabilitadoLeido("false");
        calculos.setImporteInhabilitadoLeido("false");
        calculos.setTasaPremioInhabilitadoLeido("false");
        calculos.setPlazoRepDiasInhabilitadoLeido("false");

        servicesCapturaOperacionViewHelper.realizarCalculos(calculos);

        /*
         * Realiza el cálculo del saldo Simulado se necesitan: cantidadOperadaLeida, saldoDisponibleLeido
         */
        reporto.setSimulado(calculos.getSimulado() != null ? calculos.getSimulado().longValue() : null);
        /*
         * Realiza el cálculo del Importe se necesitan: cantidadOperadaLeida, precioTituloLeido y debe estar habilitado
         * precioTituloInhabilitadoLeido
         */
        reporto.setImporte(calculos.getImporte() != null ? calculos.getImporte().doubleValue() : null);
        /*
         * Realiza el cálculo del Premio se necesitan: tasaPremioInhabilitadoLeido, plazoRepDiasInhabilitadoLeido,
         * tasaPremioLeido, plazoRepDiasLeido, cantidadOperadaLeida, precioTituloLeido y debe estar habilitado:
         * precioTituloInhabilitadoLeido, importeInhabilitadoLeido
         */
        reporto.setPremio(calculos.getPremio() != null ? calculos.getPremio().doubleValue() : null);
        /*
         * Realiza el cálculo de la fecha de regreso, se necesitan: plazoLiquidacionHorasLeido, plazoRepDiasInhabilitadoLeido y
         * debe estar habilitado: plazoLiquidacionHorasInhabilitadoLeido
         */
        reporto.setFechaRegreso(calculos.getFechaRegreso() != null ? calculos.getFechaRegreso() : null);
        /* Realiza el calculo de precio vector */
        // reporto.setPrecioVector();

        /* Valida que el importe no sea mayor al saldo Disponible
         * eliminado a peticion del usuario
        if(reporto.getSaldoDisponible() != null) {
        	if ( reporto.getSaldoDisponible().longValue() < reporto.getImporte()) {
        		mensajeUsuario = "Importe no puede ser mayor al efectivo neto";
        		FacesContext.getCurrentInstance().addMessage( null, new FacesMessage(FacesMessage.SEVERITY_INFO, mensajeUsuario, mensajeUsuario));
        	}
        }*/
        reporto.setFechaLiquidacion(calcularFechaLiquidacion());

    }

    /**
     * método para validar la existencia de los campos requeridos y su formato.
     */
    private boolean validarDTO() {
        ResultadoValidacionDTO resultadao = null;

        if (CapturaOperacionesConstants.COMPRA_MISMO_DIA.equals(reporto.getMismoDia())) {
            /* REPORTO NOMINAL CON OPCION DE MISMO DIA */
            if (reporto.getCompraReporto().equals(Boolean.TRUE)) {
                resultadao = validadorMD.validarDTO(reporto);
            } else {
                /* REPORTO NOMINAL CON OPCION DE FECHA VALOR */
                resultadao = validadorFV.validarDTO(reporto);
            }
            calculos.setTipoOperacion("T");
            calculos.setPlazoLiquidacionHorasInhabilitadoLeido("true");
        } else {
            /* REPORTO NOMINAL CON OPCION DE COMPRA EN REPORTO MD */
            if (reporto.getCompraReporto().equals(Boolean.TRUE)) {
                resultadao = validadorCompraMD.validarDTO(reporto);
            } else {
                /* REPORTO NOMINAL CON OPCION DE COMPRA EN REPORTO FV */
                resultadao = validadorCompraFV.validarDTO(reporto);
            }
            calculos.setTipoOperacion("R");
            calculos.setPlazoLiquidacionHorasInhabilitadoLeido("false");
        }

        if(reporto.getBovedaEfectivo() != null && reporto.getBovedaEfectivo().getId() < 1 && reporto.getCompraReporto() ) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Seleccione la b\u00f3veda de efectivo", "Seleccione la b\u00f3veda de efectivo"));
			resultadao = new ResultadoValidacionDTO();
			resultadao.setValido(Boolean.FALSE);
			return resultadao.isValido();
		}

		if(reporto.getDivisa()!= null && reporto.getDivisa().getId() < 1) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Seleccione la divisa", "Seleccione la divisa"));
			resultadao = new ResultadoValidacionDTO();
			resultadao.setValido(Boolean.FALSE);
			return resultadao.isValido();
		}

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
        reporto = new ReportoNominalDTO();
        reporto.setDivisa(MXP);
        super.limpiarCampos();
        getInit();
    }

    private Date calcularFechaLiquidacion() {

		Date fechaLiquidacion = null;
		int plazoDias = 0;

		if(reporto.getPlazoLiquidacionHoras() == null && reporto.getMismoDia().equals(CapturaOperacionesConstants.COMPRA_MISMO_DIA)) {
		    reporto.setPlazoLiquidacionHoras(new Integer(0));
		} 
		else if (reporto.getPlazoLiquidacionHoras()==null &&
				!reporto.getMismoDia().equals(CapturaOperacionesConstants.COMPRA_MISMO_DIA)) {
		    reporto.setPlazoLiquidacionHoras(new Integer(24));
		}

		plazoDias =reporto.getPlazoLiquidacionHoras().intValue()/24;

		fechaLiquidacion = utilService.agregarDiasHabiles(reporto.getFechaConcertacion(), plazoDias);

		cambioMismoDia = Boolean.TRUE;

		return fechaLiquidacion;

	}

    public void ajusteFechaLiquidacion(ActionEvent e) {
    	
    	if(cambioMismoDia) {
    		reporto.setFechaLiquidacion(servicesCapturaOperacionViewHelper.getFechasUtilService().getCurrentDate());
    		cambioMismoDia = Boolean.FALSE;
    	}
    	else {
    		reporto.setFechaLiquidacion(calcularFechaLiquidacion());
    	}
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
		if (StringUtils.isNotEmpty(reporto.getPosicionTraspasante().getCuenta().getCuenta())
				&& StringUtils.isNotEmpty(reporto.getPosicionTraspasante().getEmision().getTipoValor().getClaveTipoValor())
				&& StringUtils.isNotEmpty(reporto.getPosicionTraspasante().getEmision().getEmisora().getDescripcion())
				&& StringUtils.isNotEmpty(reporto.getPosicionTraspasante().getEmision().getSerie().getSerie())) {
			PosicionDTO criterio = new PosicionDTO();

			criterio.setCuenta(new CuentaDTO());
			criterio.getCuenta().setTipoCustodia(TipoCustodiaConstants.VALORES);
			criterio.getCuenta().setTipoTenencia(new TipoTenenciaDTO());
			criterio.getCuenta().getTipoTenencia().setTipoCuenta(new TipoCuentaDTO(TipoCuentaConstants.TIPO_NOMBRADA,""));
			criterio.getCuenta().getTipoTenencia().setTipoNaturaleza(new TipoNaturalezaDTO(TipoNaturalezaDTO.PASIVO,""));
			criterio.getCuenta().setNumeroCuenta(getInstitucionActual().getClaveTipoInstitucion()
					+ getInstitucionActual().getFolioInstitucion() + reporto.getPosicionTraspasante().getCuenta().getCuenta());
			criterio.setEmision(new EmisionDTO());
			InstitucionDTO institucion = null;
			institucion = consultaCatalogosFacade.buscarInstitucionPorIdFolio(reporto.getIdFolioTraspasante());

			if (institucion != null ) {
				//cuenta
				if (StringUtils.isNotEmpty(reporto.getPosicionTraspasante().getCuenta().getCuenta())) {
					criterio.getCuenta().setNumeroCuenta(institucion.getClaveTipoInstitucion() + institucion.getFolioInstitucion() + reporto.getPosicionTraspasante().getCuenta().getCuenta());
				}else {
					criterio.getCuenta().setCuenta("-1");
				}

				//TV
				criterio.getEmision().setTipoValor(consultaCatalogosFacade.buscarTipoValorPorClave(reporto.getPosicionTraspasante().getEmision().getTipoValor().getClaveTipoValor()));
				//Emisora
				criterio.getEmision().setEmisora(consultaCatalogosFacade.buscarEmisoraPorNombreCorto(reporto.getPosicionTraspasante().getEmision().getEmisora().getDescripcion()));
				criterio.getEmision().setSerie(new SerieDTO());
				//Serie
				criterio.getEmision().getSerie().setSerie(reporto.getPosicionTraspasante().getEmision().getSerie().getSerie().trim());

				if (reporto.getPosicionTraspasante().getBoveda() != null && reporto.getPosicionTraspasante().getBoveda().getId()>0) {
					criterio.setBoveda(new BovedaDTO());
					criterio.getBoveda().setId(reporto.getPosicionTraspasante().getBoveda().getId());
				}
				criterio.getBoveda().setId(NumberUtils.toLong("-1",DaliConstants.VALOR_COMBO_TODOS));
				criterio.getCuenta().setInstitucion(institucion);
				criterio.setFechaFinal(new Date());

				CriterioOrdenamientoDTO orden = new CriterioOrdenamientoDTO();
				orden.setColumna("sortCuenta");

				List<PosicionDTO> listaPosiciones = consultaPosicionService.consultarPosicionesPorMercado(criterio, null, orden, idMercadoDinero.toArray(new Long[]{}));

				reporto.getPosicionTraspasante().getEmision().setCupon(null);
				reporto.getPosicionTraspasante().getEmision().setIsin(null);
				reporto.getPosicionTraspasante().getEmision().getTipoValor().getMercado().setClaveMercado(null);
				reporto.getPosicionTraspasante().setBoveda(null);
				reporto.setSaldoDisponible(null);
				reporto.setDiasVigentes(null);

				if (listaPosiciones != null  && !listaPosiciones.isEmpty()) {
					posicion = listaPosiciones.get(0);
					//obtencion de los datos para ser desplegado en pantalla
					reporto.getPosicionTraspasante().getCuenta().setCuenta(posicion.getCuenta().getCuenta());
					reporto.getPosicionTraspasante().getEmision().getEmisora().setDescripcion(posicion.getEmision().getEmisora().getDescripcion());
					reporto.getPosicionTraspasante().getEmision().setCupon(posicion.getEmision().getCupon());
					reporto.setIsin(posicion.getEmision().getIsin());
					reporto.getPosicionTraspasante().getEmision().getTipoValor().getMercado().setClaveMercado(
							posicion.getEmision().getTipoValor().getMercado().getClaveMercado());
					reporto.getPosicionTraspasante().setBoveda(new BovedaDTO());
					reporto.getPosicionTraspasante().getBoveda().setNombreCorto(posicion.getBoveda().getNombreCorto());
					reporto.getPosicionTraspasante().getBoveda().setId(posicion.getBoveda().getId());
					reporto.setSaldoDisponible(new BigDecimal(posicion.getPosicionDisponible()).longValue());

					if (posicion.getEmision().getFechaVencimiento() != null) {
						reporto.setDiasVigentes((int)utilService.dateDiff(fechasUtilService.getCurrentDate(),posicion.getEmision().getFechaVencimiento()));
					}
				}
			}

		}

		if (posicion == null && StringUtils.isNotEmpty(reporto.getPosicionTraspasante().getEmision().getTipoValor().getClaveTipoValor())
				&& StringUtils.isNotEmpty(reporto.getPosicionTraspasante().getEmision().getEmisora().getDescripcion())
				&& StringUtils.isNotEmpty(reporto.getPosicionTraspasante().getEmision().getSerie().getSerie())) {

			EmisionDTO criterio = new EmisionDTO();
			criterio.setTipoValor(new TipoValorDTO());
			criterio.getTipoValor().setClaveTipoValor(reporto.getPosicionTraspasante().getEmision().getTipoValor().getClaveTipoValor());
			criterio.setEmisora(new EmisoraDTO());
			criterio.getEmisora().setDescripcion(reporto.getPosicionTraspasante().getEmision().getEmisora().getDescripcion());
			criterio.setSerie(new SerieDTO());
			criterio.getSerie().setSerie(reporto.getPosicionTraspasante().getEmision().getSerie().getSerie());
			List<EmisionDTO> listaEmisiones = emisionDaliDAO.consultarEmisionesPorDescripciones(criterio, null);

			if (listaEmisiones != null && !listaEmisiones.isEmpty() && listaEmisiones.size() == 1) {
				//pasar los datos al objeto de la pantalla
				reporto.getPosicionTraspasante().getEmision().getEmisora().setDescripcion(listaEmisiones.get(0).getEmisora().getDescripcion());
				reporto.getPosicionTraspasante().getEmision().getTipoValor().setClaveTipoValor(listaEmisiones.get(0).getTipoValor().getClaveTipoValor());
				reporto.getPosicionTraspasante().getEmision().getSerie().setSerie(listaEmisiones.get(0).getSerie().getSerie());
				reporto.getPosicionTraspasante().getEmision().getTipoValor().getMercado().setClaveMercado(listaEmisiones.get(0).getTipoValor().getMercado().getClaveMercado());
				reporto.setIsin(listaEmisiones.get(0).getIsin());
				reporto.getPosicionTraspasante().getEmision().setCupon(listaEmisiones.get(0).getCupon());

				reporto.getPosicionTraspasante().setBoveda(new BovedaDTO());
				reporto.getPosicionTraspasante().getBoveda().setNombreCorto(listaEmisiones.get(0).getBoveda().getNombreCorto());
				reporto.getPosicionTraspasante().getBoveda().setId(listaEmisiones.get(0).getBoveda().getId());


				if (listaEmisiones.get(0).getFechaVencimiento() != null) {
					reporto.setDiasVigentes((int)utilService.dateDiff(fechasUtilService.getCurrentDate(),listaEmisiones.get(0).getFechaVencimiento()));
				}

			} else {
				reporto.getPosicionTraspasante().getEmision().setCupon(null);
				reporto.getPosicionTraspasante().getEmision().setIsin(null);
				reporto.getPosicionTraspasante().getEmision().getTipoValor().getMercado().setClaveMercado(null);
				reporto.getPosicionTraspasante().setBoveda(null);
				reporto.setSaldoDisponible(null);
				reporto.setDiasVigentes(null);
			}

		}
		return null;
	}

	/**
	 * Llena el combo de Divisas de acuerdo al TipoInstruccion
	 *
	 * @return  Lista de SelectItem para el combo.
	 */
	public List<SelectItem> getOpcionesComboDivisaPorTipoInstruccion() {
		TipoInstruccionDTO tipoInstruccion = getTipoInstruccion();
		return consultaCatalogosFacade.getOpcionesComboDivisaPorTipoInstruccion(tipoInstruccion);
	}

	/**
	 * Obtiene las bovedas de Efectivo para la divisa elegida
	 * @return
	 */
	public List<SelectItem> getBovedasEfectivoPorDivisa() {
		List<SelectItem> bovedas = new ArrayList<SelectItem>();
		TipoInstruccionDTO tipoInstruccion = getTipoInstruccion();

		if (reporto.getDivisa() != null){
			bovedas = consultaCatalogosFacade.getSelectItemsBovedasEfectivoPorDivisaTipoInstruccion(reporto.getDivisa(), tipoInstruccion);
		}
		return bovedas;
	}

	/**
	 * Obtiene el Tipo de instruccion correspondiente
	 * @return TipoInstruccionDTO que corresponde a la operacion
	 */
	public TipoInstruccionDTO getTipoInstruccion(){
		return utilService.obtenerTipoInstruccionPorClaveOperacion("REPO");
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
     * @return the reporto
     */
    public ReportoNominalDTO getReporto() {
        return reporto;
    }

    /**
     * @param reporto the reporto to set
     */
    public void setReporto(ReportoNominalDTO reporto) {
        this.reporto = reporto;
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
     * @return the validadorMD
     */
    public DTOValidator getValidadorMD() {
        return validadorMD;
    }

    /**
     * @param validadorMD the validadorMD to set
     */
    public void setValidadorMD(DTOValidator validadorMD) {
        this.validadorMD = validadorMD;
    }

    /**
     * @return the validadorFV
     */
    public DTOValidator getValidadorFV() {
        return validadorFV;
    }

    /**
     * @param validadorFV the validadorFV to set
     */
    public void setValidadorFV(DTOValidator validadorFV) {
        this.validadorFV = validadorFV;
    }

    /**
     * @return the validadorCompraMD
     */
    public DTOValidator getValidadorCompraMD() {
        return validadorCompraMD;
    }

    /**
     * @param validadorCompraMD the validadorCompraMD to set
     */
    public void setValidadorCompraMD(DTOValidator validadorCompraMD) {
        this.validadorCompraMD = validadorCompraMD;
    }

    /**
     * @return the validadorCompraFV
     */
    public DTOValidator getValidadorCompraFV() {
        return validadorCompraFV;
    }

    /**
     * @param validadorCompraFV the validadorCompraFV to set
     */
    public void setValidadorCompraFV(DTOValidator validadorCompraFV) {
        this.validadorCompraFV = validadorCompraFV;
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
    public TraspasoContraPagoVO getVo() {
        return vo;
    }

    /**
     * Asigna el campo vo
     *
     * @param vo el valor de vo a asignar
     */
    public void setVo(TraspasoContraPagoVO vo) {
        this.vo = vo;
    }

	public UtilServices getUtilService() {
		return utilService;
	}

	public void setUtilService(UtilServices utilService) {
		this.utilService = utilService;
	}

	public boolean isEditarTraspasante() {
		return editarTraspasante;
	}

	public void setEditarTraspasante(boolean editarTraspasante) {
		this.editarTraspasante = editarTraspasante;
	}

	public boolean isEditarReceptor() {
		return editarReceptor;
	}

	public void setEditarReceptor(boolean editarReceptor) {
		this.editarReceptor = editarReceptor;
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