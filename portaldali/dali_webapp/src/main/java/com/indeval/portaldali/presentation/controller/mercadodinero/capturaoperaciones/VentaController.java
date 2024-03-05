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
import com.indeval.portaldali.presentation.dto.mercadodinero.VentaDTO;
import com.indeval.portaldali.presentation.helper.CalculoCapturaOperacionViewHelper;
import com.indeval.portaldali.presentation.helper.ServicesCapturaOperacionViewHelper;
import com.indeval.portaldali.presentation.util.ConsultaCatalogosFacade;
import com.indeval.portaldali.presentation.validation.DTOValidator;
import com.indeval.portaldali.vo.CaptOpsConfig;
import com.indeval.protocolofinanciero.api.vo.TraspasoContraPagoVO;

/**
 * Controller de la pantalla de Venta
 *
 * @author Juan Carlos Huizar Moreno
 * @version 1.0
 */
public class VentaController extends CapturaOperacionesController {

    /** La clase que permite el acceso a la consulta de los catalogos utilizados */
    private ConsultaCatalogosFacade consultaCatalogosFacade = null;

    // Validadores Inyectados via Spring //

    /**
     * Validador Mismo Dia, depende del valor del ratioButton con la etiqueta "Mismo Dia" (MD)
     */
    private DTOValidator validadorMD;

    /**
     * Validador Fecha Valor, depende del valor del ratioButton con la etiqueta "Fecha Valor" (FV)
     */
    private DTOValidator validadorFV;

    /**
     * Validador Mismo Dia Compra en Directo, depende del valor del ratioButton con la etiqueta "Mismo Dia" (MD) y del checkBox
     * con la etiqueda "Compra en Directo" (CD)
     */
    private DTOValidator validadorMDCD;

    /**
     * Validador Fecha Valor Compra en Directo, depende del valor del ratioButton con la etiqueta "Fecha Valor" (FV) y del
     * checkBox con la etiqueda "Compra en Directo" (CD)
     */
    private DTOValidator validadorFVCD;

    /**
     * El DTO que representa a los elementos de la pantalla de venta, con el detalle de los datos del Traspasante o participante y
     * del Receptor o contraparte, asi como los campos necesarios con la informacion necesaria para realizar la captura de la
     * operacion.
     */
    private VentaDTO venta = new VentaDTO();

    /**
     * Servicio de negocio relacionado con el envio de operaciones
     */
    private EnvioOperacionesService envioOperacionesService = null;

    /**
     * Servicio helper para la captura de operaciones
     */
    private ServicesCapturaOperacionViewHelper servicesCapturaOperacionViewHelper = null;

    /**
     * Configuracicon de operaciones
     */
    private CaptOpsConfig configuracion = null;
    /**
     *
     * /** El DTO que representa a los elementos Calculados.
     */
    private CalculoCapturaOperacionViewHelper calculo = new CalculoCapturaOperacionViewHelper();

    /** Servicio para calcular la fecha de liquidación con dias habiles*/
    private UtilServices utilService = null;

    /**
     * Servicio de operaciones de mercado de dinero
     */
    private MercadoDineroService mercadoDineroService = null;

    /** Campo de checkbox de compra */
    private Boolean compra = null;

    /** Operacion Mismo dia (MD) o Fecha Valor (FV) */
    private Integer mismoDiaFechaValor = null;

    /** Fecha del dia */
    private Date fecha = null;

    private boolean editarTraspasante = Boolean.FALSE;

    private boolean editarReceptor = Boolean.TRUE;

    /* Si el campo idTraspasante es readonly. */
    private Boolean idTraspasanteReadonly = null;
    /* Si el campo folioTraspasante es readonly. */
    private Boolean folioTraspasanteReadonly = null;
    /* Si el campo "idReceptor" es readonly. */
    private Boolean idReceptorReadonly = null;
    /* Si el campo folioReceptor es readonly. */
    private Boolean folioReceptorReadonly = null;

    /* Si el campo días vigentes debe mostrarse en la pantalla. */
    private Boolean diasVigentesInhabilitado;
    /* Si el campo de Plazo Rep(días) debe mostrarse en la pantalla. */
    private Boolean plazoRepDiasInhabilitado;
    /* Si el campo ISIN debe mostrarse en la pantalla. */
    private Boolean isinInhabilitado;
    /* Si el campo Precio vector debe mostrarse en la pantalla. */
    private Boolean precioVectorInhabilitado;

    /* Si el campo Saldo Disponible debe mostrarse en la pantalla. */
    private Boolean saldoDisponibleInhabilitado;
    /* Si el campo Simulado debe mostrarse en la pantalla. */
    private Boolean simuladoInhabilitado;
    /* Si el campo Neto efectivo debe mostrarse en la pantalla. */
    private Boolean netoEfectivoInhabilitado;

    /* Si el campo de Plazo Liquidación(Horas) debe mostrarse en la pantalla. */
    private Boolean plazoLiquidacionHorasInhabilitado;
    /** Valid Object de traspaso contrapago */
    private TraspasoContraPagoVO vo = null;

    /** Identificadores del mercado a filtrar*/
	private List<Long> idMercadoDinero = null;
	/**Dao para la obtención de la emisión*/
	private EmisionDaliDAO emisionDaliDAO = null;
	/**Servicio para la obtencion de la posicion*/
	private ConsultaPosicionService consultaPosicionService;
	/** Servicio para el acceso al cálculo de fechas*/
	private FechasUtilService fechasUtilService = null;
    /**
     * Contiene los cálculos de Simulado, Fecha Regreso, Importe y Premio necesarios para las pantallas de Captura de Operaciones
     */
    private CalculoCapturaOperacionViewHelper calculos = new CalculoCapturaOperacionViewHelper();

    /**
     * Constructor
     */
    public String getInitForm() {
        venta.setMismoDia(CapturaOperacionesConstants.COMPRA_MISMO_DIA);
        venta.setCompraDirecto(Boolean.FALSE);
        venta.setIdFolioTraspasante(getInstitucionActual().getClaveTipoInstitucion()
                + getInstitucionActual().getFolioInstitucion());
        venta.getPosicionTraspasante().getCuenta().setInstitucion(getInstitucionActual());
        venta.setValorEn(MXP);
        return null;
    }

    /**
     * Ejecuta las acciones necesarias para inicializar la pantalla de captura de operaciones de venta.
     *
     * @return <code>null</code>. No es necesario un valor de retorno.
     */
    public String getInit() {

    	if(isUsuarioIndeval()) {
    		editarReceptor = Boolean.TRUE;
    		editarTraspasante = Boolean.TRUE;
    	}

    	venta.setFechaConcertacion(servicesCapturaOperacionViewHelper.getFechasUtilService().getCurrentDate());
        venta.setFechaLiquidacion(servicesCapturaOperacionViewHelper.getFechasUtilService().getCurrentDate());

        if (venta.getIdFolioReceptor() != null) {
            InstitucionDTO inst = consultaCatalogosFacade.buscarInstitucionPorIdFolio(venta.getIdFolioReceptor());
            if (inst != null) {
                venta.getCuentaReceptor().setInstitucion(inst);
            } else {
            	venta.setIdFolioReceptor(StringUtils.EMPTY);
            	venta.setCuentaReceptor(new CuentaDTO());
			}
        }
        if (venta.getIdFolioTraspasante() != null) {
            InstitucionDTO inst = consultaCatalogosFacade.buscarInstitucionPorIdFolio(venta.getIdFolioTraspasante());
            if (inst != null) {
                venta.getPosicionTraspasante().getCuenta().setInstitucion(inst);
                venta.setNetoEfectivo(consultaCatalogosFacade.getSaldoNetoEfectivo(venta.getIdFolioTraspasante()));
            } else {
            	venta.setNetoEfectivo(null);
            	venta.setIdFolioTraspasante(StringUtils.EMPTY);
			}
        }
        if (venta.getCuentaReceptor().getCuenta() != null) {
            CuentaDTO cnt = consultaCatalogosFacade.buscarCuentaPorNumeroCuentaNullSiNoExiste(venta.getIdFolioReceptor()
                    + venta.getCuentaReceptor().getCuenta());

            if (cnt != null) {
                venta.setCuentaReceptor(cnt);
            } else {
            	venta.getCuentaReceptor().setTipoTenencia(new TipoTenenciaDTO());
            	venta.getCuentaReceptor().setNumeroCuenta(StringUtils.EMPTY);
            	venta.getCuentaReceptor().setCuenta(StringUtils.EMPTY);
            	venta.getCuentaReceptor().setNombreCuenta(StringUtils.EMPTY);
			}
        }
        if (venta.getPosicionTraspasante().getCuenta().getCuenta() != null) {
            CuentaDTO cnt = consultaCatalogosFacade.buscarCuentaPorNumeroCuentaNullSiNoExiste(venta.getIdFolioTraspasante()
                    + venta.getPosicionTraspasante().getCuenta().getCuenta());
            if (cnt != null) {
                venta.getPosicionTraspasante().setCuenta(cnt);
            } else {

            	venta.getPosicionTraspasante().getCuenta().setTipoTenencia(new TipoTenenciaDTO());
            	venta.getPosicionTraspasante().getCuenta().setNumeroCuenta(StringUtils.EMPTY);
            	venta.getPosicionTraspasante().getCuenta().setCuenta(StringUtils.EMPTY);
            	venta.getPosicionTraspasante().getCuenta().setNombreCuenta(StringUtils.EMPTY);
			}
        }
        //obtener los datos de la posicion
        obtenerDatosPosicion();
        return null;
    }

    /**
     * Indica si la compra se realiza en directo.
     *
     * @return <code>true</code> si la compra se realiza en directo. <code>false</code> en cualquier otro caso.
     */
    public boolean isCompraEnDirecto() {
        return venta.getCompraDirecto() != null && venta.getCompraDirecto().booleanValue();
    }

    /**
     * Indica si la compra se realiza el mismo día o no (compra segun la fecha).
     *
     * @return <code>true</code> si la compra se realiza el mismo día. <code>false</code> si la compra se realiza segun la
     *         fecha.
     */
    public boolean isCompraMismoDia() {
        return venta.getMismoDia() != null && venta.getMismoDia().equals(CapturaOperacionesConstants.COMPRA_MISMO_DIA);
    }

    /**
     * Limpiar los campos de la página respetando las opciones de la operacion.
     *
     * @return String referencia de la navegacion que debe seguir
     *
     */
    public void limpiarCampos(ActionEvent e) {
        venta = new VentaDTO();
        venta.setMismoDia(CapturaOperacionesConstants.COMPRA_MISMO_DIA);
        venta.setFechaConcertacion(servicesCapturaOperacionViewHelper.getFechasUtilService().getCurrentDate());
        venta.setFechaLiquidacion(servicesCapturaOperacionViewHelper.getFechasUtilService().getCurrentDate());
        venta.setCompraDirecto(Boolean.FALSE);
        venta.setIdFolioTraspasante(getInstitucionActual().getClaveTipoInstitucion()
                + getInstitucionActual().getFolioInstitucion());
        venta.getPosicionTraspasante().getCuenta().setInstitucion(getInstitucionActual());
        venta.setValorEn(MXP);
        super.limpiarCampos();
        cambioCompraDirecto(e);
    }

    /**
	 * Valida que la cuenta del Transpasante no pede ser igual a la cuenta del Receptor
	 *
	 * @param e
	 */
	public String validarCuenta(ActionEvent e) {
		mensajeUsuario="";
		if(venta.getPosicionTraspasante().getCuenta().getCuenta() != null &&
				!venta.getPosicionTraspasante().getCuenta().getCuenta().equals("")) {
			if (venta.getPosicionTraspasante().getCuenta().getCuenta()
					.equals(venta.getCuentaReceptor().getCuenta())
					&&(venta.getIdFolioTraspasante().equals(venta.getIdFolioReceptor()))) {
				mensajeUsuario =  "La cuenta traspasante no puede ser la misma que la del receptor";
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								mensajeUsuario, mensajeUsuario));
				if (isCompraEnDirecto() == Boolean.TRUE) {
					venta.getPosicionTraspasante().getCuenta()
					.setCuenta("");
				} else {
					venta.getCuentaReceptor().setCuenta("");
				}
			}
		}
		return mensajeUsuario;
	}

    /**
     * ActionListener para la casilla de selección que permite seleccionar si la compra se realiza en directo o no.
     *
     * @param event el evento que dispar este ActionListener
     */
    public void cambioCompraDirecto(ActionEvent event) {

    	venta.setPosicionTraspasante(new PosicionDTO());
        venta.setPrecioVector(null);
        venta.setSaldoDisponible(null);
        venta.setSimulado(null);
        venta.setDiasVigentes(null);

        if (venta.getCompraDirecto() != null && venta.getCompraDirecto().booleanValue()) {

        	editarReceptor = Boolean.FALSE;
        	editarTraspasante = Boolean.TRUE;
            venta.getPosicionTraspasante().setCuenta(new CuentaDTO());
            venta.setIdFolioTraspasante(StringUtils.EMPTY);
            venta.setCuentaReceptor(new CuentaDTO());
            venta.setIdFolioReceptor(getInstitucionActual().getClaveTipoInstitucion()
                    + getInstitucionActual().getFolioInstitucion());

            venta.setBovedaEfectivo(BANXICO);
        } else {

        	editarReceptor = Boolean.TRUE;
        	editarTraspasante = Boolean.FALSE;
            venta.getPosicionTraspasante().setCuenta(new CuentaDTO());
            venta.setIdFolioTraspasante(getInstitucionActual().getClaveTipoInstitucion()
                    + getInstitucionActual().getFolioInstitucion());
            venta.setCuentaReceptor(new CuentaDTO());
            venta.setIdFolioReceptor(StringUtils.EMPTY);
            venta.getPosicionTraspasante().getCuenta().setInstitucion(getInstitucionActual());
            venta.setBovedaEfectivo(new BovedaDTO());
        }

        if(isUsuarioIndeval()) {
    		editarTraspasante = Boolean.TRUE;
    		editarReceptor = Boolean.TRUE;
    	}
    }

    /**
     * llevar a cabo la inserci&oacute;n de la Operaci&oacute;n en la BD
     *
     * @return String Siguiente P&aacute;gina a redireccionar
     */
    public void enviarOperacion(ActionEvent e) {
    	super.limpiarCampos(); // isos
    	
    	try {
    		if(StringUtils.isEmpty(validarCuenta(e))){
    			venta.getPosicionTraspasante().getCuenta().setInstitucion(
    					consultaCatalogosFacade.buscarInstitucionPorIdNoNulaFolio(venta.getIdFolioTraspasante()));
    			venta.getCuentaReceptor().setInstitucion(
    					consultaCatalogosFacade.buscarInstitucionPorIdNoNulaFolio(venta.getIdFolioReceptor()));
    			venta.setValorEn(consultaCatalogosFacade.buscarDivisaPorId(venta.getValorEn().getId()));
    			// Si el usuario debe firmar la operacion, se recuperar la firma.
    			// Si no se ha firmado, se procesan los datos y regresa el control a la pantalla para firmar
    			if(isUsuarioConFacultadFirmar()) {
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

    public void enviarOperacion2(ActionEvent e) {
    	if(isUsuarioConFacultadFirmar()) {
    		validarVigenciaCertificado();
    		recuperarCamposFirma();
    		
    		if(!StringUtils.isEmpty(isoFirmado)) {
    			try {
    				grabarOperacion(e);
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
    	if(validarDTO()){
    		enviarOperacionABitacora();
    	}
    }

	/**
	 * Envia la operacion a bitacora
	 */
	private void enviarOperacionABitacora() {

		HashMap<String, String> datosAdicionales = new HashMap<String, String>();
				datosAdicionales.put(SeguridadConstants.USR_CREDENCIAL,
						(String)FacesContext.getCurrentInstance().getExternalContext().
						getSessionMap().get(SeguridadConstants.TICKET_SESION));

		vo.setDivisa(venta.getValorEn().getClaveAlfabetica());

		envioOperacionesService.grabaOperacion(vo, folioAsignado, venta.getCompraDirecto() != null ? venta.getCompraDirecto()
                .booleanValue() : false, datosAdicionales, null, isoFirmado);

        if (!existeErrorEnInvocacion()) {
            if (venta.getCompraDirecto().booleanValue()) {
                mensajeUsuario = "La operaci\u00f3n se llev\u00f3 a cabo exitosamente. Folio usuario : " + folioAsignado;
            } else {
                mensajeUsuario = "La operaci\u00f3n se llev\u00f3 a cabo exitosamente. Folio de la operaci\u00f3n : "
                        + folioAsignado;
            }
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, mensajeUsuario, mensajeUsuario));
            venta = new VentaDTO();
            limpiarCampos(null);
        }
	}

    /**
     * Atiene la solicitud para la realizacion del calculo simulado
     *
     * @param e
     */
    public void realizarCalculo(ActionEvent e) {
        calculos.setTipoOperacion("V");// V=VENTA

        calculos.setCantidadOperadaLeida(venta.getCantidadOperada() != null ? venta.getCantidadOperada().toString() : "");
        calculos.setCantidadOperada(new BigDecimal(venta.getCantidadOperada() != null ? venta.getCantidadOperada().doubleValue()
                : 0));
        calculos.setImporte(new BigDecimal(venta.getImporte() != null ? venta.getImporte().doubleValue() : 0));
        calculos.setSaldoDisponibleLeido(venta.getSaldoDisponible() != null ? venta.getSaldoDisponible().toString() : "");
        calculos.setPlazoLiquidacionHorasLeido(venta.getPlazoLiquidacionHoras() != null ? venta.getPlazoLiquidacionHoras()
                .toString() : "");

        calculos.setPrecioTituloLeido(venta.getPrecioTitulo() != null ? venta.getPrecioTitulo().toString() : "");
        calculos.setPrecioTitulo(new BigDecimal(venta.getPrecioTitulo() != null ? venta.getPrecioTitulo().doubleValue() : 0));
        calculos.setPrecioTituloInhabilitadoLeido("false");
        calculos.setImporteInhabilitadoLeido("false");
        calculos.setTasaPremioInhabilitadoLeido("false");
        calculos.setTasaPremioLeido(null);

        servicesCapturaOperacionViewHelper.realizarCalculos(calculos);

        /*
         * Realiza el cálculo del saldo Simulado se necesitan: cantidadOperadaLeida, saldoDisponibleLeido
         */
        venta.setSimulado(calculos.getSimulado() != null ? calculos.getSimulado().longValue() : null);
        /*
         * Realiza el cálculo del Importe se necesitan: cantidadOperadaLeida, precioTituloLeido y debe estar habilitado
         * precioTituloInhabilitadoLeido
         */
        venta.setImporte(calculos.getImporte() != null ? calculos.getImporte().doubleValue() : null);

        // queda pendiente neto efectivo

        /* Valida que el importe no sea mayor al saldo disponible*/
//        if(venta.getSaldoDisponible() != null) {
//        	if ( venta.getSaldoDisponible().longValue() < venta.getImporte()) {
//        		mensajeUsuario = "Importe no puede ser mayor al efectivo neto";
//        		FacesContext.getCurrentInstance().addMessage( null, new FacesMessage(FacesMessage.SEVERITY_INFO, mensajeUsuario, mensajeUsuario));
//        	}
//        }
    }

    /**
     *
     */
    private void calcularYRegistrarOperacicon() {
        boolean encontroError = false;
        RegistraOperacionParams registraOperacionParams = new RegistraOperacionParams();

        // --- AQUI SE LLEVA A CABO LA INSERCION DE LA OPERACION
        if (venta.getCompraDirecto() == Boolean.TRUE) {
            registraOperacionParams.setRol("R");
        } else {
            registraOperacionParams.setRol("T");
        }
        if(venta.getPosicionTraspasante().getBoveda() != null &&  venta.getPosicionTraspasante().getBoveda().getId() > 0) {
        	venta.getPosicionTraspasante().setBoveda(consultaCatalogosFacade.buscarBovedaPorId(venta.getPosicionTraspasante().getBoveda().getId()));
        }

        if( null != venta.getBovedaEfectivo()   && venta.getBovedaEfectivo().getId() > 0){
        	venta.setBovedaEfectivo(consultaCatalogosFacade.buscarBovedaPorId(venta.getBovedaEfectivo().getId()));
        }else{
        	venta.setBovedaEfectivo(null);
        }

        venta.setValorEn(consultaCatalogosFacade.buscarDivisaPorId(venta.getValorEn().getId()));

        // --- TIPO DE OPERACION "V" PARA VENTA
        registraOperacionParams.setClaveReporto("V");

        // --- AGENTE TRASPASANTE
        registraOperacionParams.setTraspasante(DTOAssembler.crearAgenteVO(venta.getPosicionTraspasante().getCuenta()));

        // --- AGENTE DEL RECEPTOR
        registraOperacionParams.setReceptor(DTOAssembler.crearAgenteVO(venta.getCuentaReceptor()));
        registraOperacionParams.setEmision(new EmisionVO());
        // --- EMISION
        if (venta.getPrecioVector() != null) {
            registraOperacionParams.getEmision().setPrecioVector(new BigDecimal(venta.getPrecioVector()));
        }
        // --- SALDO DISPONIBLE
        if (venta.getSaldoDisponible() != null) {
            registraOperacionParams.getEmision().setSaldoDisponible(new BigDecimal(venta.getSaldoDisponible().doubleValue()));
        }
        // --- TIPO VALOR
        registraOperacionParams.getEmision().setIdTipoValor(
                venta.getPosicionTraspasante().getEmision().getTipoValor().getClaveTipoValor());
        // --- EMISORA
        registraOperacionParams.getEmision()
                .setEmisora(venta.getPosicionTraspasante().getEmision().getEmisora().getDescripcion());
        // --- SERIE
        registraOperacionParams.getEmision().setSerie(venta.getPosicionTraspasante().getEmision().getSerie().getSerie());

        // --- CUPON
        registraOperacionParams.getEmision().setCupon(venta.getPosicionTraspasante().getEmision().getCupon());

        // --- DIAS VIGENTES
        if (venta.getDiasVigentes() != null) {
            registraOperacionParams.setDiasVigentes(venta.getDiasVigentes());
        }
        // --- SIMULADO
        if (venta.getSimulado() != null) {
            registraOperacionParams.setSimulado(new BigInteger(venta.getSimulado().toString()));
        }
        // --- NETO EFECTIVO
        if (venta.getNetoEfectivo() != null) {
            registraOperacionParams.setNetoEfectivo(new BigDecimal(venta.getNetoEfectivo()));
        }
        registraOperacionParams.getEmision().setIdTipoValor(
                venta.getPosicionTraspasante().getEmision().getTipoValor().getClaveTipoValor());
        registraOperacionParams.getEmision()
                .setEmisora(venta.getPosicionTraspasante().getEmision().getEmisora().getDescripcion());
        registraOperacionParams.getEmision().setSerie(venta.getPosicionTraspasante().getEmision().getSerie().getSerie());
        registraOperacionParams.getEmision().setCupon(venta.getPosicionTraspasante().getEmision().getCupon());
        registraOperacionParams.getEmision().setMercado(venta.getPosicionTraspasante().getEmision().getTipoValor().getMercado().getClaveMercado());

        // --- PLAZO LIQ.(HORAS)
        if (venta.getPlazoLiquidacionHoras() != null) {
            registraOperacionParams.setPlazoLiquidacion(venta.getPlazoLiquidacionHoras());
        } else {
            // --- AQUI SE INTRODUCE UN "CERO" PARA EL CASO DE QUE VENGA NULO EL
            // --- PLAZO LIQUIDACION(HORAS) ESTO APLICA TANTO PARA OPERACIONES
            // --- "MISMO DIA" Y PARA LOS TIPOS DE OPERACION
            // --- FONDEO CUENTA PROPIA Y TRASPASO LIBRE DE PAGO

            registraOperacionParams.setPlazoLiquidacion(Integer.valueOf("0"));
        }

        calculos.setTipoOperacion(registraOperacionParams.getClaveReporto());
        calculos.setCantidadOperadaLeida(venta.getCantidadOperada() != null ? venta.getCantidadOperada().toString() : "");
        calculos.setSaldoDisponibleLeido(venta.getSaldoDisponible() != null ? venta.getSaldoDisponible().toString() : "");
        calculos.setPlazoLiquidacionHorasLeido(venta.getPlazoLiquidacionHoras() != null ? venta.getPlazoLiquidacionHoras()
                .toString() : "");
        calculos.setPrecioTituloLeido(venta.getPrecioTitulo().toString());
        calculos.setCantidadOperada(new BigDecimal(venta.getCantidadOperada() != null ? venta.getCantidadOperada().doubleValue()
                : 0));
        calculos.setImporte(new BigDecimal(venta.getImporte() != null ? venta.getImporte().doubleValue() : 0));

        calculos.setPlazoRepDiasInhabilitadoLeido("false");
        calculos.setPlazoLiquidacionHorasInhabilitadoLeido("false");
        calculos.setPrecioTituloLeido(venta.getPrecioTitulo().toString());
        calculos.setPrecioTituloInhabilitadoLeido("false");
        calculos.setImporteInhabilitadoLeido("false");
        calculos.setTasaPremioInhabilitadoLeido("false");
        calculos.setTasaPremioLeido(null);

        servicesCapturaOperacionViewHelper.realizarCalculos(calculos);

        // --- ENCONTRO UN ERROR AL CALCULAR EL IMPORTE
        if (calculos != null && calculos.getMensajeImporte() != null && !calculos.getMensajeImporte().trim().equalsIgnoreCase("")) {
            encontroError = true;
        }
        // --- ENCONTRO UN ERROR AL CALCULAR EL PRECIO TITULO
        if (calculos != null && calculos.getMensajePrecioTitulo() != null
                && !calculos.getMensajePrecioTitulo().trim().equalsIgnoreCase("")) {
            encontroError = true;
        }
        // --- ENCONTRO UN ERROR AL CALCULAR EL SIMULADO
        if (calculos != null && calculos.getMensajeSimulado() != null
                && !calculos.getMensajeSimulado().trim().equalsIgnoreCase("")) {
            encontroError = true;
        }

        if (encontroError) {

        } else {
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
            // if(StringUtils.isNotBlank( this.getTasaPremio())){
            // registraOperacionParams.setTasaPremio( new BigDecimal(this.getTasaPremio().trim()));
            // }

            /*
             * if( calculos.getPremio() != null && !calculos.getPremio().trim().equals("")){
             * System.out.println("calculos.getPremio() ["+calculos.getPremio()+"]"); //registraOperacionParams.setTasaPremio(new
             * BigDecimal(calculos.getPremio().replaceAll(",", ""))); }
             */

            /*
             * if (this.getTasaPremio() != null && !this.getTasaPremio().trim().equalsIgnoreCase("")) {
             * registraOperacionParams.setTasaPremio(new BigDecimal(this.getTasaPremio().trim())); }
             */

            // --- FECHA CONCERTACION
            if (venta.getFechaConcertacion() != null) {
                registraOperacionParams.setFechaConcertacion(venta.getFechaConcertacion());
            }

            // --- CANTIDAD OPERADA
            if (venta.getCantidadOperada() != null) {
                registraOperacionParams.setCantidadOperada(new BigDecimal(venta.getCantidadOperada()));
            } else {
                registraOperacionParams.setCantidadOperada(new BigDecimal(0));
            }

            // --- Boveda EN
            if (venta.getPosicionTraspasante().getBoveda() != null) {
                registraOperacionParams.setIdBoveda(BigInteger.valueOf(venta.getPosicionTraspasante().getBoveda().getId()));
            }

            // --- VALOR EN
            if (venta.getValorEn() != null) {
                registraOperacionParams.setDivisa(venta.getValorEn().getClaveAlfabetica());
            }

            // --- PRECIO TITULO
            if (venta.getPrecioTitulo() != null) {
                registraOperacionParams.setPrecioTitulo(new BigDecimal(venta.getPrecioTitulo()));
            } else {
                registraOperacionParams.setPrecioTitulo(new BigDecimal(0));
            }

            // --- IMPORTE
            if (calculos.getImporte() != null) {
                registraOperacionParams.setImporte(calculos.getImporte());
            }

            if (venta.getImporte() != null) {
            	/**se pone la restriccion de solo 2 decimales y que redonde al valor siguiente*/
                registraOperacionParams.setImporte( (new BigDecimal(venta.getPrecioTitulo()).multiply(new BigDecimal(venta.getCantidadOperada())) ).setScale(2, BigDecimal.ROUND_HALF_UP));
            } else {
                registraOperacionParams.setImporte(new BigDecimal(0));
            }

            registraOperacionParams.setPremio(new BigDecimal(0));

            registraOperacionParams.setFechaLiq(servicesCapturaOperacionViewHelper
                    .calculaFechaLiquidacion(registraOperacionParams.getPlazoLiquidacion()));
            registraOperacionParams.setFechaHoraCierreOper(venta.getFechaHoraCierreOper());

            if (this.configuracion.getTiposOperacionesFirmadas().contains(registraOperacionParams.getClaveReporto())) {

                folioAsignado = mercadoDineroService.validaRegistraOperacionBusinessRules(registraOperacionParams);
                if (!existeErrorEnInvocacion()) {
                    vo = new TraspasoContraPagoVO();
                    vo = servicesCapturaOperacionViewHelper.inicializaTraspasoContrapagoVO(registraOperacionParams);
                    vo.setReferenciaOperacion(folioAsignado);
                    vo.setTipoInstruccion(registraOperacionParams.getClaveReporto());

                    vo.setFechaRegistro(servicesCapturaOperacionViewHelper.getFechasUtilService().getCurrentDate());
                    vo.setPrecio(new BigDecimal(venta.getPrecioTitulo() != null ? venta.getPrecioTitulo() : new Double("0")));
                    vo.setReferenciaMensaje(utilService.getFolio(Constantes.SEQ_REFERENCIA_MENSAJE).toString());
                    if(venta.getPosicionTraspasante().getBoveda() != null && venta.getPosicionTraspasante().getBoveda().getNombreCorto() != null ){
                    	vo.setBoveda(venta.getPosicionTraspasante().getBoveda().getNombreCorto());
                    }
                    else{
                    	vo.setBoveda(null);
                    }

                    if(venta.getBovedaEfectivo() != null){
                    	vo.setBovedaEfectivo(venta.getBovedaEfectivo().getNombreCorto());
                    }
                    else{
                    	vo.setBovedaEfectivo(null);
                    }

                    vo.setDivisa(venta.getValorEn().getClaveAlfabetica());

                    // Si el usuario debe firmar la operacion, se crea el iso
                    if(isUsuarioConFacultadFirmar() && validarDTO()) {
	                    isoSinFirmar = isoHelper.creaISO(vo, venta.getCompraDirecto() != null ? venta.getCompraDirecto().booleanValue() : false);
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

    /**
     * Ejecuta las validaciones de los DTOs de captura de acuerdo al tipo de operacion a realizar
     *
     * @return
     */
    private boolean validarDTO() {
        ResultadoValidacionDTO resultadao = null;

        if(venta.getPrecioTitulo() != null && venta.getPrecioTitulo() <= 0) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"El precio t\u00EDtulo deber ser mayor a 0", "El precio t\u00EDtulo deber ser mayor a 0"));
			resultadao = new ResultadoValidacionDTO();
			resultadao.setValido(Boolean.FALSE);

			return resultadao.isValido();
		}

        if (CapturaOperacionesConstants.COMPRA_MISMO_DIA.equals(venta.getMismoDia())) {
            // Mismo dia
            if (venta.getCompraDirecto().booleanValue()) {
                resultadao = validadorMDCD.validarDTO(venta);
            } else {
                resultadao = validadorMD.validarDTO(venta);
            }
            calculos.setTipoOperacion("T");
            calculos.setPlazoLiquidacionHorasInhabilitadoLeido("true");
        } else {
            // fecha valor
            if (venta.getCompraDirecto().booleanValue()) {
                resultadao = validadorFVCD.validarDTO(venta);
            } else {
                resultadao = validadorFV.validarDTO(venta);
            }
            calculos.setTipoOperacion("R");
            calculos.setPlazoLiquidacionHorasInhabilitadoLeido("false");
        }

        if(venta.getBovedaEfectivo() != null && venta.getBovedaEfectivo().getId() < 1 && venta.getCompraDirecto()) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Seleccione la b\u00f3veda de efectivo", "Seleccione la b\u00f3veda de efectivo"));
			resultadao = new ResultadoValidacionDTO();
			resultadao.setValido(Boolean.FALSE);
			return resultadao.isValido();
		}

		if(venta.getValorEn()!= null && venta.getValorEn().getId() < 1) {
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
	 * Obtiene las bovedas de Efectivo para la divisa elegida
	 * @return
	 */
	public List<SelectItem> getBovedasEfectivoPorDivisa() {
		List<SelectItem> bovedas = new ArrayList<SelectItem>();
		TipoInstruccionDTO tipoInstruccion = getTipoInstruccion();

		if (venta.getValorEn() != null){
			bovedas = consultaCatalogosFacade.getSelectItemsBovedasEfectivoPorDivisaTipoInstruccion(venta.getValorEn(), tipoInstruccion);
		}
		return bovedas;
	}

	/**
	 * Obtiene el Tipo de instruccion correspondiente
	 * @return TipoInstruccionDTO que corresponde a la operacion
	 */
	public TipoInstruccionDTO getTipoInstruccion(){
		return utilService.obtenerTipoInstruccionPorClaveOperacion("COVE");
	}

    public void calcularFechaLiquidacion(ActionEvent e) {

    	Date fechaLiquidacion = null;
		int plazoDias = 0;

		if(venta.getPlazoLiquidacionHoras() == null) {
			venta.setPlazoLiquidacionHoras(new Integer(24));
		}

		if(venta.getMismoDia().intValue() == 1) {
			plazoDias =venta.getPlazoLiquidacionHoras().intValue()/24;
			fechaLiquidacion = utilService.agregarDiasHabiles(venta.getFechaConcertacion(), plazoDias);
		}
		else {
			fechaLiquidacion  = servicesCapturaOperacionViewHelper.getFechasUtilService().getCurrentDate();
			venta.setPlazoLiquidacionHoras(null);
		}

		venta.setFechaLiquidacion(fechaLiquidacion);

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
		if (StringUtils.isNotEmpty(venta.getPosicionTraspasante().getCuenta().getCuenta())
				&& StringUtils.isNotEmpty(venta.getPosicionTraspasante().getEmision().getTipoValor().getClaveTipoValor())
				&& StringUtils.isNotEmpty(venta.getPosicionTraspasante().getEmision().getEmisora().getDescripcion())
				&& StringUtils.isNotEmpty(venta.getPosicionTraspasante().getEmision().getSerie().getSerie())) {
			PosicionDTO criterio = new PosicionDTO();

			criterio.setCuenta(new CuentaDTO());
			criterio.getCuenta().setTipoCustodia(TipoCustodiaConstants.VALORES);
			criterio.getCuenta().setTipoTenencia(new TipoTenenciaDTO());
			criterio.getCuenta().getTipoTenencia().setTipoCuenta(new TipoCuentaDTO(TipoCuentaConstants.TIPO_NOMBRADA,""));
			criterio.getCuenta().getTipoTenencia().setTipoNaturaleza(new TipoNaturalezaDTO(TipoNaturalezaDTO.PASIVO,""));
			criterio.getCuenta().setNumeroCuenta(getInstitucionActual().getClaveTipoInstitucion()
					+ getInstitucionActual().getFolioInstitucion() + venta.getPosicionTraspasante().getCuenta().getCuenta());
			criterio.setEmision(new EmisionDTO());
			InstitucionDTO institucion = null;
			institucion = consultaCatalogosFacade.buscarInstitucionPorIdFolio(venta.getIdFolioTraspasante());

			if (institucion != null ) {
				//cuenta
				if (StringUtils.isNotEmpty(venta.getPosicionTraspasante().getCuenta().getCuenta())) {
					criterio.getCuenta().setNumeroCuenta(institucion.getClaveTipoInstitucion() + institucion.getFolioInstitucion() + venta.getPosicionTraspasante().getCuenta().getCuenta());
				}else {
					criterio.getCuenta().setCuenta("-1");
				}

				//TV
				criterio.getEmision().setTipoValor(consultaCatalogosFacade.buscarTipoValorPorClave(venta.getPosicionTraspasante().getEmision().getTipoValor().getClaveTipoValor()));
				//Emisora
				criterio.getEmision().setEmisora(consultaCatalogosFacade.buscarEmisoraPorNombreCorto(venta.getPosicionTraspasante().getEmision().getEmisora().getDescripcion()));
				criterio.getEmision().setSerie(new SerieDTO());
				//Serie
				criterio.getEmision().getSerie().setSerie(venta.getPosicionTraspasante().getEmision().getSerie().getSerie().trim());

				if (venta.getPosicionTraspasante().getBoveda() != null && venta.getPosicionTraspasante().getBoveda().getId()>0) {
					criterio.setBoveda(new BovedaDTO());
					criterio.getBoveda().setId(venta.getPosicionTraspasante().getBoveda().getId());
				}else{
					criterio.getBoveda().setId(NumberUtils.toLong("-1",DaliConstants.VALOR_COMBO_TODOS));
				}
				criterio.getCuenta().setInstitucion(institucion);
				criterio.setFechaFinal(new Date());

				CriterioOrdenamientoDTO orden = new CriterioOrdenamientoDTO();
				orden.setColumna("sortCuenta");

				List<PosicionDTO> listaPosiciones = consultaPosicionService.consultarPosicionesPorMercado(criterio, null, orden, idMercadoDinero.toArray(new Long[]{}));

				venta.getPosicionTraspasante().getEmision().setCupon(null);
				venta.getPosicionTraspasante().getEmision().setIsin(null);
				venta.getPosicionTraspasante().getEmision().getTipoValor().getMercado().setClaveMercado(null);
				venta.getPosicionTraspasante().setBoveda(null);
				venta.setSaldoDisponible(null);
				venta.setDiasVigentes(null);

				if (listaPosiciones != null  && !listaPosiciones.isEmpty()) {
					posicion = listaPosiciones.get(0);
					//obtencion de los datos para ser desplegado en pantalla
					venta.getPosicionTraspasante().getCuenta().setCuenta(posicion.getCuenta().getCuenta());
					venta.getPosicionTraspasante().getEmision().getEmisora().setDescripcion(posicion.getEmision().getEmisora().getDescripcion());
					venta.getPosicionTraspasante().getEmision().setCupon(posicion.getEmision().getCupon());
					venta.getPosicionTraspasante().getEmision().setIsin(posicion.getEmision().getIsin());
					venta.getPosicionTraspasante().getEmision().getTipoValor().getMercado().setClaveMercado(
							posicion.getEmision().getTipoValor().getMercado().getClaveMercado());
					venta.getPosicionTraspasante().setBoveda(new BovedaDTO());
					venta.getPosicionTraspasante().getBoveda().setNombreCorto(posicion.getBoveda().getNombreCorto());
					venta.getPosicionTraspasante().getBoveda().setId(posicion.getBoveda().getId());
					venta.setSaldoDisponible(new BigDecimal(posicion.getPosicionDisponible()).longValue());

					if (posicion.getEmision().getFechaVencimiento() != null) {
						venta.setDiasVigentes((int)utilService.dateDiff(fechasUtilService.getCurrentDate(),posicion.getEmision().getFechaVencimiento()));
					}
				}
			}

		}

		if (posicion == null && StringUtils.isNotEmpty(venta.getPosicionTraspasante().getEmision().getTipoValor().getClaveTipoValor())
				&& StringUtils.isNotEmpty(venta.getPosicionTraspasante().getEmision().getEmisora().getDescripcion())
				&& StringUtils.isNotEmpty(venta.getPosicionTraspasante().getEmision().getSerie().getSerie())) {

			EmisionDTO criterio = new EmisionDTO();
			criterio.setTipoValor(new TipoValorDTO());
			criterio.getTipoValor().setClaveTipoValor(venta.getPosicionTraspasante().getEmision().getTipoValor().getClaveTipoValor());
			criterio.setEmisora(new EmisoraDTO());
			criterio.getEmisora().setDescripcion(venta.getPosicionTraspasante().getEmision().getEmisora().getDescripcion());
			criterio.setSerie(new SerieDTO());
			criterio.getSerie().setSerie(venta.getPosicionTraspasante().getEmision().getSerie().getSerie());
			List<EmisionDTO> listaEmisiones = emisionDaliDAO.consultarEmisionesPorDescripciones(criterio, null);

			if (listaEmisiones != null && !listaEmisiones.isEmpty() && listaEmisiones.size() == 1) {
				//pasar los datos al objeto de la pantalla
				venta.getPosicionTraspasante().getEmision().getEmisora().setDescripcion(listaEmisiones.get(0).getEmisora().getDescripcion());
				venta.getPosicionTraspasante().getEmision().getTipoValor().setClaveTipoValor(listaEmisiones.get(0).getTipoValor().getClaveTipoValor());
				venta.getPosicionTraspasante().getEmision().getSerie().setSerie(listaEmisiones.get(0).getSerie().getSerie());
				venta.getPosicionTraspasante().getEmision().getTipoValor().getMercado().setClaveMercado(listaEmisiones.get(0).getTipoValor().getMercado().getClaveMercado());
				venta.getPosicionTraspasante().getEmision().setIsin(listaEmisiones.get(0).getIsin());
				venta.getPosicionTraspasante().getEmision().setCupon(listaEmisiones.get(0).getCupon());

				venta.getPosicionTraspasante().setBoveda(new BovedaDTO());
				venta.getPosicionTraspasante().getBoveda().setNombreCorto(listaEmisiones.get(0).getBoveda().getNombreCorto());
				venta.getPosicionTraspasante().getBoveda().setId(listaEmisiones.get(0).getBoveda().getId());

				if (listaEmisiones.get(0).getFechaVencimiento() != null) {
					venta.setDiasVigentes((int)utilService.dateDiff(fechasUtilService.getCurrentDate(),listaEmisiones.get(0).getFechaVencimiento()));
				}

			} else {
				venta.getPosicionTraspasante().getEmision().setCupon(null);
				venta.getPosicionTraspasante().getEmision().setIsin(null);
				venta.getPosicionTraspasante().getEmision().getTipoValor().getMercado().setClaveMercado(null);
				venta.getPosicionTraspasante().setBoveda(null);
				venta.setSaldoDisponible(null);
				venta.setDiasVigentes(null);
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
     * Obtiene el valor del campo consultaCatalogosFacade
     *
     * @return el valor de consultaCatalogosFacade
     */
    public ConsultaCatalogosFacade getConsultaCatalogosFacade() {
        return consultaCatalogosFacade;
    }

    /**
     * Asigna el campo consultaCatalogosFacade
     *
     * @param consultaCatalogosFacade el valor de consultaCatalogosFacade a asignar
     */
    public void setConsultaCatalogosFacade(ConsultaCatalogosFacade consultaCatalogosFacade) {
        this.consultaCatalogosFacade = consultaCatalogosFacade;
    }

    /**
     * Obtiene el valor del campo venta
     *
     * @return el valor de venta
     */
    public VentaDTO getVenta() {
        return venta;
    }

    /**
     * Asigna el campo venta
     *
     * @param venta el valor de venta a asignar
     */
    public void setVenta(VentaDTO venta) {
        this.venta = venta;
    }

    /**
     * Obtiene el valor del campo compra
     *
     * @return el valor de compra
     */
    public Boolean getCompra() {
        return compra;
    }

    /**
     * Asigna el campo compra
     *
     * @param compra el valor de compra a asignar
     */
    public void setCompra(Boolean compra) {
        this.compra = compra;
    }

    /**
     * Obtiene el valor del campo mismoDiaFechaValor
     *
     * @return el valor de mismoDiaFechaValor
     */
    public Integer getMismoDiaFechaValor() {
        return mismoDiaFechaValor;
    }

    /**
     * Asigna el campo mismoDiaFechaValor
     *
     * @param mismoDiaFechaValor el valor de mismoDiaFechaValor a asignar
     */
    public void setMismoDiaFechaValor(Integer mismoDiaFechaValor) {
        this.mismoDiaFechaValor = mismoDiaFechaValor;
    }

    /**
     * Obtiene el valor del campo fecha
     *
     * @return el valor de fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * Asigna el campo fecha
     *
     * @param fecha el valor de fecha a asignar
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * Obtiene el valor del campo validadorMD
     *
     * @return el valor de validadorMD
     */
    public DTOValidator getValidadorMD() {
        return validadorMD;
    }

    /**
     * Asigna el campo validadorMD
     *
     * @param validadorMD el valor de validadorMD a asignar
     */
    public void setValidadorMD(DTOValidator validadorMD) {
        this.validadorMD = validadorMD;
    }

    /**
     * Obtiene el valor del campo validadorFV
     *
     * @return el valor de validadorFV
     */
    public DTOValidator getValidadorFV() {
        return validadorFV;
    }

    /**
     * Asigna el campo validadorFV
     *
     * @param validadorFV el valor de validadorFV a asignar
     */
    public void setValidadorFV(DTOValidator validadorFV) {
        this.validadorFV = validadorFV;
    }

    /**
     * Obtiene el valor del campo validadorMDCD
     *
     * @return el valor de validadorMDCD
     */
    public DTOValidator getValidadorMDCD() {
        return validadorMDCD;
    }

    /**
     * Asigna el campo validadorMDCD
     *
     * @param validadorMDCD el valor de validadorMDCD a asignar
     */
    public void setValidadorMDCD(DTOValidator validadorMDCD) {
        this.validadorMDCD = validadorMDCD;
    }

    /**
     * Obtiene el valor del campo validadorFVCD
     *
     * @return el valor de validadorFVCD
     */
    public DTOValidator getValidadorFVCD() {
        return validadorFVCD;
    }

    /**
     * Asigna el campo validadorFVCD
     *
     * @param validadorFVCD el valor de validadorFVCD a asignar
     */
    public void setValidadorFVCD(DTOValidator validadorFVCD) {
        this.validadorFVCD = validadorFVCD;
    }

    /**
     * Obtiene el valor del campo idTraspasanteReadonly
     *
     * @return el valor de idTraspasanteReadonly
     */
    public Boolean getIdTraspasanteReadonly() {
        return idTraspasanteReadonly;
    }

    /**
     * Asigna el campo idTraspasanteReadonly
     *
     * @param idTraspasanteReadonly el valor de idTraspasanteReadonly a asignar
     */
    public void setIdTraspasanteReadonly(Boolean idTraspasanteReadonly) {
        this.idTraspasanteReadonly = idTraspasanteReadonly;
    }

    /**
     * Obtiene el valor del campo folioTraspasanteReadonly
     *
     * @return el valor de folioTraspasanteReadonly
     */
    public Boolean getFolioTraspasanteReadonly() {
        return folioTraspasanteReadonly;
    }

    /**
     * Asigna el campo folioTraspasanteReadonly
     *
     * @param folioTraspasanteReadonly el valor de folioTraspasanteReadonly a asignar
     */
    public void setFolioTraspasanteReadonly(Boolean folioTraspasanteReadonly) {
        this.folioTraspasanteReadonly = folioTraspasanteReadonly;
    }

    /**
     * Obtiene el valor del campo idReceptorReadonly
     *
     * @return el valor de idReceptorReadonly
     */
    public Boolean getIdReceptorReadonly() {
        return idReceptorReadonly;
    }

    /**
     * Asigna el campo idReceptorReadonly
     *
     * @param idReceptorReadonly el valor de idReceptorReadonly a asignar
     */
    public void setIdReceptorReadonly(Boolean idReceptorReadonly) {
        this.idReceptorReadonly = idReceptorReadonly;
    }

    /**
     * Obtiene el valor del campo folioReceptorReadonly
     *
     * @return el valor de folioReceptorReadonly
     */
    public Boolean getFolioReceptorReadonly() {
        return folioReceptorReadonly;
    }

    /**
     * Asigna el campo folioReceptorReadonly
     *
     * @param folioReceptorReadonly el valor de folioReceptorReadonly a asignar
     */
    public void setFolioReceptorReadonly(Boolean folioReceptorReadonly) {
        this.folioReceptorReadonly = folioReceptorReadonly;
    }

    /**
     * Obtiene el valor del campo plazoLiquidacionHorasInhabilitado
     *
     * @return el valor de plazoLiquidacionHorasInhabilitado
     */
    public Boolean getPlazoLiquidacionHorasInhabilitado() {
        return plazoLiquidacionHorasInhabilitado;
    }

    /**
     * Asigna el campo plazoLiquidacionHorasInhabilitado
     *
     * @param plazoLiquidacionHorasInhabilitado el valor de plazoLiquidacionHorasInhabilitado a asignar
     */
    public void setPlazoLiquidacionHorasInhabilitado(Boolean plazoLiquidacionHorasInhabilitado) {
        this.plazoLiquidacionHorasInhabilitado = plazoLiquidacionHorasInhabilitado;
    }

    /**
     * Obtiene el valor del campo isinInhabilitado
     *
     * @return el valor de isinInhabilitado
     */
    public Boolean getIsinInhabilitado() {
        return isinInhabilitado;
    }

    /**
     * Asigna el campo isinInhabilitado
     *
     * @param isinInhabilitado el valor de isinInhabilitado a asignar
     */
    public void setIsinInhabilitado(Boolean isinInhabilitado) {
        this.isinInhabilitado = isinInhabilitado;
    }

    /**
     * Obtiene el valor del campo precioVectorInhabilitado
     *
     * @return el valor de precioVectorInhabilitado
     */
    public Boolean getPrecioVectorInhabilitado() {
        return precioVectorInhabilitado;
    }

    /**
     * Asigna el campo precioVectorInhabilitado
     *
     * @param precioVectorInhabilitado el valor de precioVectorInhabilitado a asignar
     */
    public void setPrecioVectorInhabilitado(Boolean precioVectorInhabilitado) {
        this.precioVectorInhabilitado = precioVectorInhabilitado;
    }

    /**
     * Obtiene el valor del campo simuladoInhabilitado
     *
     * @return el valor de simuladoInhabilitado
     */
    public Boolean getSimuladoInhabilitado() {
        return simuladoInhabilitado;
    }

    /**
     * Asigna el campo simuladoInhabilitado
     *
     * @param simuladoInhabilitado el valor de simuladoInhabilitado a asignar
     */
    public void setSimuladoInhabilitado(Boolean simuladoInhabilitado) {
        this.simuladoInhabilitado = simuladoInhabilitado;
    }

    /**
     * Obtiene el valor del campo saldoDisponibleInhabilitado
     *
     * @return el valor de saldoDisponibleInhabilitado
     */
    public Boolean getSaldoDisponibleInhabilitado() {
        return saldoDisponibleInhabilitado;
    }

    /**
     * Asigna el campo saldoDisponibleInhabilitado
     *
     * @param saldoDisponibleInhabilitado el valor de saldoDisponibleInhabilitado a asignar
     */
    public void setSaldoDisponibleInhabilitado(Boolean saldoDisponibleInhabilitado) {
        this.saldoDisponibleInhabilitado = saldoDisponibleInhabilitado;
    }

    /**
     * Obtiene el valor del campo netoEfectivoInhabilitado
     *
     * @return el valor de netoEfectivoInhabilitado
     */
    public Boolean getNetoEfectivoInhabilitado() {
        return netoEfectivoInhabilitado;
    }

    /**
     * Asigna el campo netoEfectivoInhabilitado
     *
     * @param netoEfectivoInhabilitado el valor de netoEfectivoInhabilitado a asignar
     */
    public void setNetoEfectivoInhabilitado(Boolean netoEfectivoInhabilitado) {
        this.netoEfectivoInhabilitado = netoEfectivoInhabilitado;
    }

    /**
     * Obtiene el valor del campo diasVigentesInhabilitado
     *
     * @return el valor de diasVigentesInhabilitado
     */
    public Boolean getDiasVigentesInhabilitado() {
        return diasVigentesInhabilitado;
    }

    /**
     * Asigna el campo diasVigentesInhabilitado
     *
     * @param diasVigentesInhabilitado el valor de diasVigentesInhabilitado a asignar
     */
    public void setDiasVigentesInhabilitado(Boolean diasVigentesInhabilitado) {
        this.diasVigentesInhabilitado = diasVigentesInhabilitado;
    }

    /**
     * Obtiene el valor del campo plazoRepDiasInhabilitado
     *
     * @return el valor de plazoRepDiasInhabilitado
     */
    public Boolean getPlazoRepDiasInhabilitado() {
        return plazoRepDiasInhabilitado;
    }

    /**
     * Asigna el campo plazoRepDiasInhabilitado
     *
     * @param plazoRepDiasInhabilitado el valor de plazoRepDiasInhabilitado a asignar
     */
    public void setPlazoRepDiasInhabilitado(Boolean plazoRepDiasInhabilitado) {
        this.plazoRepDiasInhabilitado = plazoRepDiasInhabilitado;
    }

    /**
     * Obtiene el valor del campo calculo
     *
     * @return el valor de calculo
     */
    public CalculoCapturaOperacionViewHelper getCalculo() {
        return calculo;
    }

    /**
     * Asigna el campo calculo
     *
     * @param calculo el valor de calculo a asignar
     */
    public void setCalculo(CalculoCapturaOperacionViewHelper calculo) {
        this.calculo = calculo;
    }

    /**
     * Obtiene el valor del campo envioOperacionesService
     *
     * @return el valor de envioOperacionesService
     */
    public EnvioOperacionesService getEnvioOperacionesService() {
        return envioOperacionesService;
    }

    /**
     * Asigna el campo envioOperacionesService
     *
     * @param envioOperacionesService el valor de envioOperacionesService a asignar
     */
    public void setEnvioOperacionesService(EnvioOperacionesService envioOperacionesService) {
        this.envioOperacionesService = envioOperacionesService;
    }

    /**
     * Obtiene el valor del campo servicesCapturaOperacionViewHelper
     *
     * @return el valor de servicesCapturaOperacionViewHelper
     */
    public ServicesCapturaOperacionViewHelper getServicesCapturaOperacionViewHelper() {
        return servicesCapturaOperacionViewHelper;
    }

    /**
     * Asigna el campo servicesCapturaOperacionViewHelper
     *
     * @param servicesCapturaOperacionViewHelper el valor de servicesCapturaOperacionViewHelper a asignar
     */
    public void setServicesCapturaOperacionViewHelper(ServicesCapturaOperacionViewHelper servicesCapturaOperacionViewHelper) {
        this.servicesCapturaOperacionViewHelper = servicesCapturaOperacionViewHelper;
    }

    /**
     * Obtiene el valor del campo configuracion
     *
     * @return el valor de configuracion
     */
    public CaptOpsConfig getConfiguracion() {
        return configuracion;
    }

    /**
     * Asigna el campo configuracion
     *
     * @param configuracion el valor de configuracion a asignar
     */
    public void setConfiguracion(CaptOpsConfig configuracion) {
        this.configuracion = configuracion;
    }

    /**
     * Obtiene el valor del campo mercadoDineroService
     *
     * @return el valor de mercadoDineroService
     */
    public MercadoDineroService getMercadoDineroService() {
        return mercadoDineroService;
    }

    /**
     * Asigna el campo mercadoDineroService
     *
     * @param mercadoDineroService el valor de mercadoDineroService a asignar
     */
    public void setMercadoDineroService(MercadoDineroService mercadoDineroService) {
        this.mercadoDineroService = mercadoDineroService;
    }

    /**
     * Obtiene el valor del campo calculos
     *
     * @return el valor de calculos
     */
    public CalculoCapturaOperacionViewHelper getCalculos() {
        return calculos;
    }

    /**
     * Asigna el campo calculos
     *
     * @param calculos el valor de calculos a asignar
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
