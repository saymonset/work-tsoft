/**
 *
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
import com.indeval.portaldali.presentation.dto.mercadodinero.ColocacionPrimariaDTO;
import com.indeval.portaldali.presentation.helper.CalculoCapturaOperacionViewHelper;
import com.indeval.portaldali.presentation.helper.IsoHelper;
import com.indeval.portaldali.presentation.helper.ServicesCapturaOperacionViewHelper;
import com.indeval.portaldali.presentation.util.ConsultaCatalogosFacade;
import com.indeval.portaldali.presentation.validation.DTOValidator;
import com.indeval.portaldali.vo.CaptOpsConfig;
import com.indeval.protocolofinanciero.api.vo.TraspasoContraPagoVO;

/**
 * @author José Antonio Huizar Moreno
 * @version 1.0
 *
 * Controller de la pantalla de Colocacion Primaria o Recompra
 */
public class ColocacionPrimariaController extends CapturaOperacionesController {

	/**
	 * Validadores del dto
	 */
	private DTOValidator validatorMismoDia = null;

	private DTOValidator validatorFechaValor = null;

	private DTOValidator validatorMismoDiaCompra = null;

	private DTOValidator validatorFechaValorCompra = null;

	/** DTO con la información capturada por el usuario en pantalla */
	private ColocacionPrimariaDTO colocacionPrimaria = new ColocacionPrimariaDTO();

	/** Servicio de negocio relacionado con el envo de operaciones */
	private EnvioOperacionesService envioOperacionesService = null;
	/**
	 * Servicio helper para la captura de operaciones
	 */
	private ServicesCapturaOperacionViewHelper servicesCapturaOperacionViewHelper = null;
	/**
	 * Servicio de operaciones de mercado de dinero
	 */
	private MercadoDineroService mercadoDineroService = null;
	/**
	 * Configuracicn de operaciones
	 */
	private CaptOpsConfig configuracion = null;
	/**
	 * Acceso a la consulta de catálogos desde la capa de vista
	 */
	private ConsultaCatalogosFacade consultaCatalogos = null;

	/** Dao para la obtención de la emisión */
	private EmisionDaliDAO emisionDaliDAO = null;

	/** Servicio para la obtencion de la posicion */
	private ConsultaPosicionService consultaPosicionService;

	/** Servicio para el acceso al cálculo de fechas */
	private FechasUtilService fechasUtilService = null;

	/** Servicio para calcular la fecha de liquidación con dias habiles */
	private UtilServices utilService = null;

	private TraspasoContraPagoVO vo = null;

	private boolean editarTraspasante = Boolean.FALSE;

	private boolean editarReceptor = Boolean.TRUE;

	/** Identificadores del mercado a filtrar */
	private List<Long> idMercadoDinero = null;
	
	
	/**
	 * /** Obtiene el campo colocacionPrimaria
	 *
	 * @return colocacionPrimaria
	 */
	public ColocacionPrimariaDTO getColocacionPrimaria() {
		return colocacionPrimaria;
	}


	public String getInitForm() {

		
		colocacionPrimaria.setMismoDia(CapturaOperacionesConstants.COMPRA_MISMO_DIA);
		colocacionPrimaria.setCompraDirecto(Boolean.FALSE);
		colocacionPrimaria.setIdFolioTraspasante(getInstitucionActual().getClaveTipoInstitucion() + getInstitucionActual().getFolioInstitucion());
		colocacionPrimaria.getPosicionTraspasante().getCuenta().setInstitucion(getInstitucionActual());
		colocacionPrimaria.setValorEn(MXP);
		
		return null;
	}

	/**
	 * Ejecuta las acciones necesarias para inicializar la pantalla de captura
	 * de operaciones de colocación primaria.
	 *
	 * @return <code>null</code>. No es necesario un valor de retorno.
	 */
	public String getInit() {
		
		if (isUsuarioIndeval()) {
			editarReceptor = Boolean.TRUE;
			editarTraspasante = Boolean.TRUE;
		}

		colocacionPrimaria.setFechaConcertacion(servicesCapturaOperacionViewHelper.getFechasUtilService().getCurrentDate());
		colocacionPrimaria.setFechaLiquidacion(servicesCapturaOperacionViewHelper.getFechasUtilService().getCurrentDate());
		
		if (colocacionPrimaria.getIdFolioReceptor() != null) {
			InstitucionDTO inst = consultaCatalogos.buscarInstitucionPorIdFolio(colocacionPrimaria.getIdFolioReceptor());
			if (inst != null) {
				colocacionPrimaria.getCuentaReceptor().setInstitucion(inst);
			} else {
				colocacionPrimaria.setIdFolioReceptor(StringUtils.EMPTY);
				colocacionPrimaria.setCuentaReceptor(new CuentaDTO());
			}
		}
		if (colocacionPrimaria.getIdFolioTraspasante() != null) {
			InstitucionDTO inst = consultaCatalogos.buscarInstitucionPorIdFolio(colocacionPrimaria.getIdFolioTraspasante());
			if (inst != null) {
				colocacionPrimaria.getPosicionTraspasante().getCuenta().setInstitucion(inst);
				colocacionPrimaria.setNetoEfectivo(consultaCatalogos.getSaldoNetoEfectivo(colocacionPrimaria.getIdFolioTraspasante()));
			} else {
				colocacionPrimaria.setNetoEfectivo(null);
				colocacionPrimaria.setIdFolioTraspasante(StringUtils.EMPTY);
			}
		}
		if (colocacionPrimaria.getCuentaReceptor().getCuenta() != null) {
			CuentaDTO cnt = consultaCatalogos.buscarCuentaPorNumeroCuentaNullSiNoExiste(colocacionPrimaria.getIdFolioReceptor()
					+ colocacionPrimaria.getCuentaReceptor().getCuenta());

			if (cnt != null) {
				colocacionPrimaria.setCuentaReceptor(cnt);
			} else {
				colocacionPrimaria.getCuentaReceptor().setTipoTenencia(new TipoTenenciaDTO());
				colocacionPrimaria.getCuentaReceptor().setNumeroCuenta(StringUtils.EMPTY);
				colocacionPrimaria.getCuentaReceptor().setCuenta(StringUtils.EMPTY);
				colocacionPrimaria.getCuentaReceptor().setNombreCuenta(StringUtils.EMPTY);
			}
		}
		if (colocacionPrimaria.getPosicionTraspasante().getCuenta().getCuenta() != null) {
			CuentaDTO cnt = consultaCatalogos.buscarCuentaPorNumeroCuentaNullSiNoExiste(colocacionPrimaria.getIdFolioTraspasante()
					+ colocacionPrimaria.getPosicionTraspasante().getCuenta().getCuenta());
			if (cnt != null) {
				colocacionPrimaria.getPosicionTraspasante().setCuenta(cnt);
			} else {

				colocacionPrimaria.getPosicionTraspasante().getCuenta().setTipoTenencia(new TipoTenenciaDTO());
				colocacionPrimaria.getPosicionTraspasante().getCuenta().setNumeroCuenta(StringUtils.EMPTY);
				colocacionPrimaria.getPosicionTraspasante().getCuenta().setCuenta(StringUtils.EMPTY);
				colocacionPrimaria.getPosicionTraspasante().getCuenta().setNombreCuenta(StringUtils.EMPTY);

			}
		}
		// obtener los datos de la posicion
		obtenerDatosPosicion();
		return null;
	}

	/**
	 * Llena el combo de Divisas de acuerdo al TipoInstruccion
	 *
	 * @return  Lista de SelectItem para el combo.
	 */
	public List<SelectItem> getOpcionesComboDivisaPorTipoInstruccion() {
		TipoInstruccionDTO tipoInstruccion = getTipoInstruccion();
		return consultaCatalogos.getOpcionesComboDivisaPorTipoInstruccion(tipoInstruccion);
	}


	/**
	 * Asigna el campo colocacionPrimaria
	 *
	 * @param colocacionPrimaria
	 *            el valor de colocacionPrimaria a asignar
	 */
	public void setColocacionPrimaria(ColocacionPrimariaDTO colocacionPrimaria) {
		this.colocacionPrimaria = colocacionPrimaria;
	}

	/**
	 * Obtiene el campo envioOperacionesService
	 *
	 * @return envioOperacionesService
	 */
	public EnvioOperacionesService getEnvioOperacionesService() {
		return envioOperacionesService;
	}

	/**
	 * Asigna el campo envioOperacionesService
	 *
	 * @param envioOperacionesService
	 *            el valor de envioOperacionesService a asignar
	 */
	public void setEnvioOperacionesService(EnvioOperacionesService envioOperacionesService) {
		this.envioOperacionesService = envioOperacionesService;
	}

	/**
	 * Valida que el Id Folio del Transpasante o Receptor no este vacio
	 *
	 * @param e
	 */
	private String validarIdFolioParticipantes() {
		mensajeUsuario = "";

		if(StringUtils.isBlank(colocacionPrimaria.getIdFolioTraspasante())) {
			mensajeUsuario = "La clave Tipo Instituci\u00f3n Trasapasante es requerida";
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, mensajeUsuario, mensajeUsuario));
		}
		else if(StringUtils.isBlank(colocacionPrimaria.getIdFolioReceptor())) {
			mensajeUsuario = "La clave Tipo Instituci\u00f3n Receptor es requerida";
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, mensajeUsuario, mensajeUsuario));
		}

		return mensajeUsuario;

	}

	/**
	 * ActionListener para el botn que permite persistir la operación capturada
	 * por el usuario.
	 *
	 * @param event
	 *            el evento que dispar este ActionListener
	 */
	public void enviarOperacion(ActionEvent e) {
		super.limpiarCampos();
		
		try {				
			if(StringUtils.isEmpty(validarIdFolioParticipantes())) {
	    		InstitucionDTO traspasante = consultaCatalogos.buscarInstitucionPorIdNoNulaFolio(colocacionPrimaria.getIdFolioTraspasante());
				InstitucionDTO receptor = consultaCatalogos.buscarInstitucionPorIdNoNulaFolio(colocacionPrimaria.getIdFolioReceptor());
				if(traspasante != null && StringUtils.isNotBlank(traspasante.getClaveTipoInstitucion())) {
					colocacionPrimaria.getPosicionTraspasante().getCuenta().setInstitucion(traspasante);
					if(receptor != null && StringUtils.isNotBlank(receptor.getClaveTipoInstitucion())) {
						colocacionPrimaria.getCuentaReceptor().setInstitucion(receptor);
						// Si el usuario debe firmar la operacion, se recuperar la firma.
						// Si no se ha firmado, se procesan los datos y regresa el control a la pantalla para firmar
						if (isUsuarioConFacultadFirmar()) {
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
		if (isUsuarioConFacultadFirmar()) {
			validarVigenciaCertificado();
			recuperarCamposFirma();
			if (!StringUtils.isEmpty(isoFirmado)) {
				try {
					grabarOperacion(event);
				}catch (BusinessException e) {
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
	 * Atiene la solicitud para la realización del cálculo simulado
	 *
	 * @param e
	 */
	public void realizarCalculoSimulado(ActionEvent e) {
		CalculoCapturaOperacionViewHelper calculos = new CalculoCapturaOperacionViewHelper();

		calculos.setTipoOperacion("J");
		calculos.setCantidadOperadaLeida(colocacionPrimaria.getCantidadOperada() != null ? colocacionPrimaria.getCantidadOperada().toString() : "");
		calculos
				.setCantidadOperada(new BigDecimal(colocacionPrimaria.getCantidadOperada() != null ? colocacionPrimaria.getCantidadOperada().doubleValue() : 0));
		calculos.setImporte(new BigDecimal(colocacionPrimaria.getImporte() != null ? colocacionPrimaria.getImporte().doubleValue() : 0));

		calculos.setSaldoDisponibleLeido(colocacionPrimaria.getSaldoDisponible() != null ? colocacionPrimaria.getSaldoDisponible().toString() : "");
		calculos.setPlazoRepDiasLeido("0");
		calculos.setPlazoLiquidacionHorasLeido(colocacionPrimaria.getPlazoLiquidacionHoras() != null ? colocacionPrimaria.getPlazoLiquidacionHoras().toString()
				: "");
		calculos.setPlazoRepDiasInhabilitadoLeido("false");
		calculos.setPlazoLiquidacionHorasInhabilitadoLeido("false");
		calculos.setPrecioTituloLeido(colocacionPrimaria.getPrecioTitulo() != null ? colocacionPrimaria.getPrecioTitulo().toString() : "");
		calculos.setPrecioTitulo(new BigDecimal(colocacionPrimaria.getPrecioTitulo() != null ? colocacionPrimaria.getPrecioTitulo().doubleValue() : 0));
		calculos.setPrecioTituloInhabilitadoLeido("false");
		calculos.setImporteInhabilitadoLeido("false");
		calculos.setTasaPremioInhabilitadoLeido("false");
		calculos.setTasaPremioLeido(null);

		servicesCapturaOperacionViewHelper.realizarCalculos(calculos);

		colocacionPrimaria.setSimulado(calculos.getSimulado() != null ? calculos.getSimulado().longValue() : null);
		colocacionPrimaria.setImporte(calculos.getImporte() != null ? calculos.getImporte().doubleValue() : null);

		/*
		 * Se elimina validación a petición del usuario
		 *
		 * if(colocacionPrimaria.getSaldoDisponible() != null) { if (
		 * colocacionPrimaria.getSaldoDisponible().longValue() <
		 * colocacionPrimaria.getImporte()) { mensajeUsuario = "Importe no puede
		 * ser mayor al efectivo neto";
		 * FacesContext.getCurrentInstance().addMessage( null, new
		 * FacesMessage(FacesMessage.SEVERITY_INFO, mensajeUsuario,
		 * mensajeUsuario)); } }
		 */
	}

	/**
	 * Realiza los cálculos simulados de la operacicn y construye el objeto de
	 * parámetros que el servicio de negocio espera
	 */
	private void calcularYRegistrarOperacicon() {
		boolean encontroError = false;
		RegistraOperacionParams registraOperacionParams = new RegistraOperacionParams();
		registraOperacionParams.setClaveReporto("J");
		// --- Se deja fijo el ROL DEL AGENTE FIRMADO de Traspasante (T)
		// (Receptor-R)
		registraOperacionParams.setRol("T");
		// --- PLAZO REP.(DIAS)
		// if (colocacionPrimaria.getP != null &&
		// !this.getPlazoRepDias().trim().equalsIgnoreCase("")) {
		// registraOperacionParams.setPlazoReporto(Integer.valueOf(this.getPlazoRepDias().trim()));
		// }
		// else {
		//
		// }
		registraOperacionParams.setPlazoReporto(Integer.valueOf("0"));

		if( null != colocacionPrimaria.getPosicionTraspasante().getBoveda()  && colocacionPrimaria.getPosicionTraspasante().getBoveda().getId() > 0 ){
			colocacionPrimaria.getPosicionTraspasante().setBoveda(consultaCatalogos.buscarBovedaPorId(colocacionPrimaria.getPosicionTraspasante().getBoveda().getId()));
		}else{
			colocacionPrimaria.getPosicionTraspasante().setBoveda(null);
		}
		if( null != colocacionPrimaria.getBovedaEfectivo()  && colocacionPrimaria.getBovedaEfectivo().getId()> 0 ){
			colocacionPrimaria.setBovedaEfectivo(consultaCatalogos.buscarBovedaPorId(colocacionPrimaria.getBovedaEfectivo().getId()));
		}else{
			colocacionPrimaria.setBovedaEfectivo(null);
		}

		colocacionPrimaria.setValorEn(consultaCatalogos.buscarDivisaPorId(colocacionPrimaria.getValorEn().getId()));

		// --- PLAZO LIQ.(HORAS)
		if (colocacionPrimaria.getPlazoLiquidacionHoras() != null) {
			registraOperacionParams.setPlazoLiquidacion(colocacionPrimaria.getPlazoLiquidacionHoras());
		} else {
			// --- AQUI SE INTRODUCE UN "CERO" PARA EL CASO DE QUE VENGA NULO EL
			// --- PLAZO LIQUIDACION(HORAS) ESTO APLICA TANTO PARA OPERACIONES
			// --- "MISMO DIA" Y PARA LOS TIPOS DE OPERACION
			// --- FONDEO CUENTA PROPIA Y TRASPASO LIBRE DE PAGO

			registraOperacionParams.setPlazoLiquidacion(Integer.valueOf("0"));
		}

		CalculoCapturaOperacionViewHelper calculos = new CalculoCapturaOperacionViewHelper();

		calculos.setTipoOperacion(registraOperacionParams.getClaveReporto());
		calculos.setCantidadOperadaLeida(colocacionPrimaria.getCantidadOperada() != null ? colocacionPrimaria.getCantidadOperada().toString() : "");
		calculos.setCantidadOperada(new BigDecimal(colocacionPrimaria.getCantidadOperada() != null ? colocacionPrimaria.getCantidadOperada().doubleValue() : 0));
		calculos.setImporte(new BigDecimal(colocacionPrimaria.getImporte() != null ? colocacionPrimaria.getImporte().doubleValue() : 0));
		calculos.setSaldoDisponibleLeido(colocacionPrimaria.getSaldoDisponible() != null ? colocacionPrimaria.getSaldoDisponible().toString() : "");
		calculos.setPlazoRepDiasLeido(registraOperacionParams.getPlazoReporto().toString());
		calculos.setPlazoLiquidacionHorasLeido(colocacionPrimaria.getPlazoLiquidacionHoras() != null ? colocacionPrimaria.getPlazoLiquidacionHoras().toString(): "");
		calculos.setPlazoRepDiasInhabilitadoLeido("false");
		calculos.setPlazoLiquidacionHorasInhabilitadoLeido("false");
		calculos.setPrecioTituloLeido(colocacionPrimaria.getPrecioTitulo().toString());
		calculos.setPrecioTituloInhabilitadoLeido("false");
		calculos.setImporteInhabilitadoLeido("false");
		calculos.setTasaPremioInhabilitadoLeido("false");
		calculos.setTasaPremioLeido(null);

		servicesCapturaOperacionViewHelper.realizarCalculos(calculos);

		if (calculos != null && calculos.getMensajeFechaRegreso() != null && !calculos.getMensajeFechaRegreso().trim().equalsIgnoreCase("")) {
			// --- ENCONTRO UN ERROR AL CALCULAR LA FECHA DE REGRESO
			encontroError = true;
		}

		if (calculos != null && calculos.getMensajeImporte() != null && !calculos.getMensajeImporte().trim().equalsIgnoreCase("")) {
			// --- ENCONTRO UN ERROR AL CALCULAR EL IMPORTE
			encontroError = true;
		}

		if (calculos != null && calculos.getMensajePrecioTitulo() != null && !calculos.getMensajePrecioTitulo().trim().equalsIgnoreCase("")) {
			// --- ENCONTRO UN ERROR AL CALCULAR EL PRECIO TITULO
			encontroError = true;
		}

		if (calculos != null && calculos.getMensajePremio() != null && !calculos.getMensajePremio().trim().equalsIgnoreCase("")) {
			// --- ENCONTRO UN ERROR AL CALCULAR EL PREMIO
			encontroError = true;
		}

		if (calculos != null && calculos.getMensajeSimulado() != null && !calculos.getMensajeSimulado().trim().equalsIgnoreCase("")) {
			// --- ENCONTRO UN ERROR AL CALCULAR EL SIMULADO
			encontroError = true;
		}

		// realizarCalculos(this.getTipoOperacion(), this.getCantidadOperada(),
		// this.getSaldoDisponible(), this.getPlazoRepDias(),
		// this.getPlazoLiquidacionHoras(),
		// this.getPlazoRepDiasInhabilitado().toString(),
		// this.getPlazoLiquidacionHorasInhabilitado().toString(),
		// this.getPrecioTitulo(),
		// this.getPrecioTituloInhabilitado().toString(),
		// this.getImporteInhabilitado().toString(),
		// this.getTasaPremioInhabilitado().toString(), this.getTasaPremio());

		// --- FECHA REGRESO

		if (encontroError) {

		} else {
			if (calculos.getFechaRegreso() != null) {
				try {

					registraOperacionParams.setFechaRegreso(calculos.getFechaRegreso());
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				registraOperacionParams.setFechaRegreso(servicesCapturaOperacionViewHelper.getFechasUtilService().getCurrentDate());
			}

			// --- TASA PREMIO
			// if(StringUtils.isNotBlank( this.getTasaPremio())){
			// registraOperacionParams.setTasaPremio( new
			// BigDecimal(this.getTasaPremio().trim()));
			// }

			/*
			 * if( calculos.getPremio() != null &&
			 * !calculos.getPremio().trim().equals("")){
			 * System.out.println("calculos.getPremio()
			 * ["+calculos.getPremio()+"]");
			 * //registraOperacionParams.setTasaPremio(new
			 * BigDecimal(calculos.getPremio().replaceAll(",", ""))); }
			 */

			/*
			 * if (this.getTasaPremio() != null &&
			 * !this.getTasaPremio().trim().equalsIgnoreCase("")) {
			 * registraOperacionParams.setTasaPremio(new
			 * BigDecimal(this.getTasaPremio().trim())); }
			 */

			// --- FECHA CONCERTACION
			if (colocacionPrimaria.getFechaConcertacion() != null) {

				registraOperacionParams.setFechaConcertacion(colocacionPrimaria.getFechaConcertacion());
			}

			// --- CANTIDAD OPERADA
			if (colocacionPrimaria.getCantidadOperada() != null) {
				registraOperacionParams.setCantidadOperada(new BigDecimal(colocacionPrimaria.getCantidadOperada()));
			} else {
				registraOperacionParams.setCantidadOperada(new BigDecimal(0));
			}

			// --- BOVEDA DE VALORES
			if (colocacionPrimaria.getPosicionTraspasante().getBoveda() != null) {
				registraOperacionParams.setIdBoveda(BigInteger.valueOf(colocacionPrimaria.getPosicionTraspasante().getBoveda().getId()));
			}

			// --- VALOR EN
			if (colocacionPrimaria.getValorEn() != null) {
				registraOperacionParams.setDivisa(colocacionPrimaria.getValorEn().getClaveAlfabetica());
			}

			// --- PRECIO TITULO
			if (colocacionPrimaria.getPrecioTitulo() != null) {
				registraOperacionParams.setPrecioTitulo(new BigDecimal(colocacionPrimaria.getPrecioTitulo()));
			} else {
				registraOperacionParams.setPrecioTitulo(new BigDecimal(0));
			}

			// --- IMPORTE

			if (calculos.getImporte() != null) {
				registraOperacionParams.setImporte(calculos.getImporte());
			}

			if (colocacionPrimaria.getImporte() != null) {
				/**se pone la restriccion de solo 2 decimales y que redonde al valor siguiente*/
				registraOperacionParams.setImporte(new BigDecimal(((double) colocacionPrimaria.getPrecioTitulo()
						* (double) colocacionPrimaria.getCantidadOperada())).setScale(2,BigDecimal.ROUND_HALF_UP ));
			} else {
				registraOperacionParams.setImporte(new BigDecimal(0));
			}

			// --- PREMIO
			// if (this.getPremio() != null &&
			// !this.getPremio().trim().equalsIgnoreCase("")) {
			// String valorPremio =
			// eliminarFormatoSeparadorMiles(this.getPremio());
			// registraOperacionParams.setPremio(new BigDecimal(valorPremio));
			// }
			// else {
			// registraOperacionParams.setPremio(new BigDecimal(0));
			// }
			registraOperacionParams.setPremio(new BigDecimal(0));

			registraOperacionParams.setEmision(new EmisionVO());
			registraOperacionParams.getEmision().setCupon(colocacionPrimaria.getPosicionTraspasante().getEmision().getCupon());
			registraOperacionParams.getEmision().setDiasVigentes(colocacionPrimaria.getPosicionTraspasante().getEmision().getDiasVigentes());
			registraOperacionParams.getEmision().setEmisora(colocacionPrimaria.getPosicionTraspasante().getEmision().getEmisora().getDescripcion());
			registraOperacionParams.getEmision().setFechaVencimiento(colocacionPrimaria.getPosicionTraspasante().getEmision().getFechaVencimiento());
			registraOperacionParams.getEmision().setIdTipoValor(colocacionPrimaria.getPosicionTraspasante().getEmision().getTipoValor().getClaveTipoValor());
			registraOperacionParams.getEmision().setIsin(colocacionPrimaria.getPosicionTraspasante().getEmision().getIsin());
			registraOperacionParams.getEmision().setMercado(
					colocacionPrimaria.getPosicionTraspasante().getEmision().getTipoValor().getMercado().getClaveMercado());
			// registraOperacionParams.getEmision().setPrecioVector()
			// registraOperacionParams.getEmision().setSaldoDisponible(colocacionPrimaria.getPosicionTraspasante().getEmision().get)
			registraOperacionParams.getEmision().setSerie(colocacionPrimaria.getPosicionTraspasante().getEmision().getSerie().getSerie());

			registraOperacionParams.setTraspasante(DTOAssembler.crearAgenteVO(colocacionPrimaria.getPosicionTraspasante().getCuenta()));
			registraOperacionParams.setReceptor(DTOAssembler.crearAgenteVO(colocacionPrimaria.getCuentaReceptor()));

			registraOperacionParams.setFechaLiq(servicesCapturaOperacionViewHelper.calculaFechaLiquidacion(registraOperacionParams.getPlazoLiquidacion()));
			registraOperacionParams.setFechaHoraCierreOper(colocacionPrimaria.getFechaHoraCierreOper());

			folioAsignado = "";

			if (this.configuracion.getTiposOperacionesFirmadas().contains(registraOperacionParams.getClaveReporto())) {
				if (mercadoDineroService != null) {
					folioAsignado = mercadoDineroService.validaGetColocacionPrimariaRecompraBusinessRules(registraOperacionParams);
				}
				if (!existeErrorEnInvocacion()) {
					vo = new TraspasoContraPagoVO();
					vo = servicesCapturaOperacionViewHelper.inicializaTraspasoContrapagoVO(registraOperacionParams);
					vo.setReferenciaOperacion(folioAsignado);
					vo.setTipoInstruccion(registraOperacionParams.getClaveReporto());
					vo.setTasaNegociada(null);
					vo.setTasaReferencia(null);
					vo.setTasaFija(false);
					vo.setFechaRegistro(servicesCapturaOperacionViewHelper.getFechasUtilService().getCurrentDate());
					vo.setFechaVencimiento(null);
					vo.setPrecio(new BigDecimal(colocacionPrimaria.getPrecioTitulo() != null ? colocacionPrimaria.getPrecioTitulo() : new Double("0")));
					if(null != colocacionPrimaria.getPosicionTraspasante().getBoveda()){
						vo.setBoveda(colocacionPrimaria.getPosicionTraspasante().getBoveda().getNombreCorto());
					}
					if(null != colocacionPrimaria.getBovedaEfectivo()){
						vo.setBovedaEfectivo(colocacionPrimaria.getBovedaEfectivo().getNombreCorto());
					}
					if(null != colocacionPrimaria.getValorEn()){
						vo.setDivisa(colocacionPrimaria.getValorEn().getClaveAlfabetica());
					}
					vo.setReferenciaMensaje(utilService.getFolio(Constantes.SEQ_REFERENCIA_MENSAJE).toString());

					// Si el usuario debe firmar la operacion, se crea el iso
					if (isUsuarioConFacultadFirmar() && validarDTO()) {
						isoSinFirmar = isoHelper.creaISO(vo, colocacionPrimaria.getCompraDirecto() != null ? colocacionPrimaria.getCompraDirecto()
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

	/**
	 * Valida que el importe de la operación no sea mayor al neto efectivo
	 *
	 * @param e
	 */
	public String validarCuenta(ActionEvent e) {

		if (colocacionPrimaria.getPosicionTraspasante().getCuenta().getCuenta() != null
				&& !colocacionPrimaria.getPosicionTraspasante().getCuenta().getCuenta().equals("")) {
			if (colocacionPrimaria.getPosicionTraspasante().getCuenta().getCuenta().equals(colocacionPrimaria.getCuentaReceptor().getCuenta())
					&& colocacionPrimaria.getIdFolioTraspasante().equals(colocacionPrimaria.getIdFolioReceptor())) {
				mensajeUsuario = "La cuenta traspasante no puede ser la misma que la del receptor";
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, mensajeUsuario, mensajeUsuario));

				if (isCompraEnDirecto() == Boolean.TRUE) {
					colocacionPrimaria.getPosicionTraspasante().getCuenta().setCuenta("");
				} else {
					colocacionPrimaria.getCuentaReceptor().setCuenta("");
				}
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
		if (!cdb.validation(hashIso, isoSinFirmar)) {
			throw new InfrastructureException(UtilConstantes.ERROR_ISO_DIFERENTE);
		}
		isoFirmado = (new StringBuilder()).append(isoSinFirmar).append(numeroSerie).append("\n").append("{SHA1withRSA}").append(isoFirmado)
				.toString();
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

		if( null != colocacionPrimaria.getValorEn() && colocacionPrimaria.getValorEn().getId()>0 ){
			DivisaDTO divisa= consultaCatalogos.buscarDivisaPorId(colocacionPrimaria.getValorEn().getId());
			vo.setDivisa( null!= divisa ? divisa.getClaveAlfabetica() : null);
		}

		envioOperacionesService.grabaOperacion(vo, folioControl, colocacionPrimaria.getCompraDirecto() != null ? colocacionPrimaria.getCompraDirecto()
				.booleanValue() : false, datosAdicionales, null, isoFirmado);

		if (!existeErrorEnInvocacion()) {
			if (colocacionPrimaria.getCompraDirecto().booleanValue()) {
				mensajeUsuario = "La operaci\u00f3n se llev\u00f3 a cabo exitosamente. Folio usuario : " + folioAsignado;
			} else {
				mensajeUsuario = "La operaci\u00f3n se llev\u00f3 a cabo exitosamente. Folio de la operaci\u00f3n : " + folioAsignado;
			}
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, mensajeUsuario, mensajeUsuario));
			colocacionPrimaria = new ColocacionPrimariaDTO();
			limpiarCampos(null);
		}
	}

	/**
	 * Ejecuta las validaciones de los DTOs de captura de acuerdo al tipo de
	 * operación a realizar
	 *
	 * @return
	 */
	private boolean validarDTO() {
		ResultadoValidacionDTO resultadao = null;

		if(colocacionPrimaria.getPrecioTitulo() != null && colocacionPrimaria.getPrecioTitulo() <= 0) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"El precio t\u00EDtulo deber ser mayor a 0", "El precio t\u00EDtulo deber ser mayor a 0"));
			resultadao = new ResultadoValidacionDTO();
			resultadao.setValido(Boolean.FALSE);

			return resultadao.isValido();
		}

		if(colocacionPrimaria.getBovedaEfectivo() != null && colocacionPrimaria.getBovedaEfectivo().getId() < 1 &&  colocacionPrimaria.getCompraDirecto() ) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Seleccione la b\u00f3veda de efectivo", "Seleccione la b\u00f3veda de efectivo"));
			resultadao = new ResultadoValidacionDTO();
			resultadao.setValido(Boolean.FALSE);
			return resultadao.isValido();
		}

		if(colocacionPrimaria.getValorEn()!= null && colocacionPrimaria.getValorEn().getId() < 1) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Seleccione la divisa", "Seleccione la divisa"));
			resultadao = new ResultadoValidacionDTO();
			resultadao.setValido(Boolean.FALSE);
			return resultadao.isValido();
		}

		if (CapturaOperacionesConstants.COMPRA_MISMO_DIA.equals(colocacionPrimaria.getMismoDia())) {
			// Mismo dia
			if (colocacionPrimaria.getCompraDirecto().booleanValue()) {
				resultadao = validatorMismoDiaCompra.validarDTO(colocacionPrimaria);
			} else {
				resultadao = validatorMismoDia.validarDTO(colocacionPrimaria);
			}

		} else {
			// fecha valor
			if (colocacionPrimaria.getCompraDirecto().booleanValue()) {
				resultadao = validatorFechaValorCompra.validarDTO(colocacionPrimaria);
			} else {
				resultadao = validatorFechaValor.validarDTO(colocacionPrimaria);
			}
		}
		if (!resultadao.isValido()) {

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, resultadao.getMensaje(), resultadao.getMensaje()));
		}
		return resultadao.isValido();
	}

	/**
	 * ActionListener para el botn que permite limpiar los campos de la forma
	 * de captura.
	 *
	 * @param event
	 *            el evento que dispar este ActionListener
	 */
	public void limpiarCampos(ActionEvent event) {
		colocacionPrimaria = new ColocacionPrimariaDTO();
		colocacionPrimaria.setMismoDia(CapturaOperacionesConstants.COMPRA_MISMO_DIA);
		colocacionPrimaria.setCompraDirecto(Boolean.FALSE);
		colocacionPrimaria.setIdFolioTraspasante(getInstitucionActual().getClaveTipoInstitucion() + getInstitucionActual().getFolioInstitucion());
		colocacionPrimaria.getPosicionTraspasante().getCuenta().setInstitucion(getInstitucionActual());
		colocacionPrimaria.setFechaConcertacion(new Date());
		colocacionPrimaria.setFechaLiquidacion(new Date());
		colocacionPrimaria.setValorEn(MXP);
		colocacionPrimaria.getPosicionTraspasante().getCuenta().setNombreCuenta(null);
		super.limpiarCampos();
		cambioCompraDirecto(event);

	}

	public void calcularFechaLiquidacion(ActionEvent e) {

		Date fechaLiquidacion = null;
		int plazoDias = 0;

		if (colocacionPrimaria.getPlazoLiquidacionHoras() == null) {
			colocacionPrimaria.setPlazoLiquidacionHoras(new Integer(24));
		}
		if (colocacionPrimaria.getMismoDia().intValue() == 1) {
			plazoDias=colocacionPrimaria.getPlazoLiquidacionHoras().intValue()/24;
			fechaLiquidacion = utilService.agregarDiasHabiles(colocacionPrimaria.getFechaConcertacion(), plazoDias);
		} else {
			fechaLiquidacion = servicesCapturaOperacionViewHelper.getFechasUtilService().getCurrentDate();
			colocacionPrimaria.setPlazoLiquidacionHoras(null);
		}

		colocacionPrimaria.setFechaLiquidacion(fechaLiquidacion);

	}

	/**
	 * Obtiene las bovedas de Efectivo para la divisa elegida
	 * @return
	 */
	public List<SelectItem> getBovedasEfectivoPorDivisa() {
		List<SelectItem> bovedas = new ArrayList<SelectItem>();
		TipoInstruccionDTO tipoInstruccion = getTipoInstruccion();

		if (colocacionPrimaria.getValorEn() != null){
			bovedas = consultaCatalogos.getSelectItemsBovedasEfectivoPorDivisaTipoInstruccion(colocacionPrimaria.getValorEn(), tipoInstruccion);
		}
		return bovedas;
	}

	/**
	 * Obtiene el Tipo de instruccion correspondiente
	 * @return TipoInstruccionDTO que corresponde a la operacion
	 */
	public TipoInstruccionDTO getTipoInstruccion(){
		return utilService.obtenerTipoInstruccionPorClaveOperacion("CORE");
	}

	/**
	 * Indica si la compra se realiza el mismo día o no.
	 *
	 * @return <code>true</code> si la compra se realiza el mismo día.
	 *         <code>false</code> en cualquier otro caso.
	 */
	public boolean isCompraMismoDia() {
		return colocacionPrimaria.getMismoDia() != null && colocacionPrimaria.getMismoDia().equals(CapturaOperacionesConstants.COMPRA_MISMO_DIA);
	}

	/**
	 * Indica si la compra se realiza en directo.
	 *
	 * @return <code>true</code> si la compra se realiza en directo.
	 *         <code>false</code> en cualquier otro caso.
	 */
	public boolean isCompraEnDirecto() {
		return colocacionPrimaria.getCompraDirecto() != null && colocacionPrimaria.getCompraDirecto().booleanValue();
	}

	/**
	 * ActionListener para la casilla de selección que permite seleccionar si la
	 * compra se realiza en directo o no.
	 *
	 * @param event
	 *            el evento que dispar este ActionListener
	 */
	public void cambioCompraDirecto(ActionEvent event) {

		colocacionPrimaria.setMismoDia(CapturaOperacionesConstants.COMPRA_MISMO_DIA);
		// colocacionPrimaria.setCompraDirecto(Boolean.FALSE);
		colocacionPrimaria.setPosicionTraspasante(new PosicionDTO());
		colocacionPrimaria.setPrecioVector(null);
		colocacionPrimaria.setSaldoDisponible(null);
		colocacionPrimaria.setSimulado(null);
		colocacionPrimaria.setDiasVigentes(null);

		if (colocacionPrimaria.getCompraDirecto() != null && colocacionPrimaria.getCompraDirecto().booleanValue()) {

			editarReceptor = Boolean.FALSE;
			editarTraspasante = Boolean.TRUE;
			colocacionPrimaria.setIdFolioTraspasante(StringUtils.EMPTY);
			colocacionPrimaria.setCuentaReceptor(new CuentaDTO());
			colocacionPrimaria.getCuentaReceptor().setInstitucion(getInstitucionActual());
			colocacionPrimaria.setIdFolioReceptor(getInstitucionActual().getClaveTipoInstitucion() + getInstitucionActual().getFolioInstitucion());

			colocacionPrimaria.setBovedaEfectivo(BANXICO);
		} else {

			editarReceptor = Boolean.TRUE;
			editarTraspasante = Boolean.FALSE;
			colocacionPrimaria.setIdFolioTraspasante(getInstitucionActual().getClaveTipoInstitucion() + getInstitucionActual().getFolioInstitucion());
			colocacionPrimaria.getPosicionTraspasante().getCuenta().setInstitucion(getInstitucionActual());
			colocacionPrimaria.setCuentaReceptor(new CuentaDTO());

			colocacionPrimaria.setIdFolioReceptor(StringUtils.EMPTY);
			colocacionPrimaria.setBovedaEfectivo(new BovedaDTO());
		}

		if (isUsuarioIndeval()) {
			editarReceptor = Boolean.TRUE;
			editarTraspasante = Boolean.TRUE;
		}
	}

	/**
	 * Metodo para la obtencion de la Posicion a partir de los campos
	 * idFolioParticiante, TV, Emisora, Serie.
	 *
	 * return <code>null</code>. No es necesario un valor de retorno.
	 */
	public String obtenerDatosPosicion() {
		// primero se obtiene la Posicion
		PosicionDTO posicion = null;
		if (StringUtils.isNotEmpty(colocacionPrimaria.getPosicionTraspasante().getCuenta().getCuenta())
				&& StringUtils.isNotEmpty(colocacionPrimaria.getPosicionTraspasante().getEmision().getTipoValor().getClaveTipoValor())
				&& StringUtils.isNotEmpty(colocacionPrimaria.getPosicionTraspasante().getEmision().getEmisora().getDescripcion())
				&& StringUtils.isNotEmpty(colocacionPrimaria.getPosicionTraspasante().getEmision().getSerie().getSerie())) {
			PosicionDTO criterio = new PosicionDTO();

			criterio.setCuenta(new CuentaDTO());
			criterio.getCuenta().setTipoCustodia(TipoCustodiaConstants.VALORES);
			criterio.getCuenta().setTipoTenencia(new TipoTenenciaDTO());
			criterio.getCuenta().getTipoTenencia().setTipoCuenta(new TipoCuentaDTO(TipoCuentaConstants.TIPO_NOMBRADA, ""));
			criterio.getCuenta().getTipoTenencia().setTipoNaturaleza(new TipoNaturalezaDTO(TipoNaturalezaDTO.PASIVO, ""));
			criterio.getCuenta().setNumeroCuenta(
					getInstitucionActual().getClaveTipoInstitucion() + getInstitucionActual().getFolioInstitucion()
							+ colocacionPrimaria.getPosicionTraspasante().getCuenta().getCuenta());
			criterio.setEmision(new EmisionDTO());
			InstitucionDTO institucion = null;
			institucion = consultaCatalogos.buscarInstitucionPorIdFolio(colocacionPrimaria.getIdFolioTraspasante());

			if (institucion != null) {
				// cuenta
				if (StringUtils.isNotEmpty(colocacionPrimaria.getPosicionTraspasante().getCuenta().getCuenta())) {
					criterio.getCuenta().setNumeroCuenta(
							institucion.getClaveTipoInstitucion() + institucion.getFolioInstitucion()
									+ colocacionPrimaria.getPosicionTraspasante().getCuenta().getCuenta());
				} else {
					criterio.getCuenta().setCuenta("-1");
				}

				// TV
				criterio.getEmision().setTipoValor(
						consultaCatalogos.buscarTipoValorPorClave(colocacionPrimaria.getPosicionTraspasante().getEmision().getTipoValor().getClaveTipoValor()
								.toUpperCase()));
				// Emisora
				criterio.getEmision().setEmisora(
						consultaCatalogos.buscarEmisoraPorNombreCorto(colocacionPrimaria.getPosicionTraspasante().getEmision().getEmisora().getDescripcion()
								.toUpperCase()));
				criterio.getEmision().setSerie(new SerieDTO());
				// Serie
				criterio.getEmision().getSerie().setSerie(colocacionPrimaria.getPosicionTraspasante().getEmision().getSerie().getSerie().trim().toUpperCase());

				if (colocacionPrimaria.getPosicionTraspasante().getBoveda() != null && colocacionPrimaria.getPosicionTraspasante().getBoveda().getId() > 0) {
					criterio.setBoveda(new BovedaDTO());
					criterio.getBoveda().setId(colocacionPrimaria.getPosicionTraspasante().getBoveda().getId());
				}
				criterio.getBoveda().setId(NumberUtils.toLong("-1", DaliConstants.VALOR_COMBO_TODOS));
				criterio.getCuenta().setInstitucion(institucion);
				criterio.setFechaFinal(new Date());

				CriterioOrdenamientoDTO orden = new CriterioOrdenamientoDTO();
				orden.setColumna("sortCuenta");

				List<PosicionDTO> listaPosiciones = consultaPosicionService.consultarPosicionesPorMercado(criterio, null, orden, idMercadoDinero
						.toArray(new Long[] {}));

				colocacionPrimaria.getPosicionTraspasante().getEmision().setCupon(null);
				colocacionPrimaria.getPosicionTraspasante().getEmision().setIsin(null);
				colocacionPrimaria.getPosicionTraspasante().getEmision().getTipoValor().getMercado().setClaveMercado(null);
				colocacionPrimaria.getPosicionTraspasante().setBoveda(null);
				colocacionPrimaria.setSaldoDisponible(null);
				colocacionPrimaria.setDiasVigentes(null);

				if (listaPosiciones != null && !listaPosiciones.isEmpty()) {

					colocacionPrimaria.getPosicionTraspasante().getEmision().setIsin(null);

					posicion = listaPosiciones.get(0);

					// llenado de los datos para ser desplegado en pantalla
					colocacionPrimaria.getPosicionTraspasante().getCuenta().setCuenta(posicion.getCuenta().getCuenta());
					colocacionPrimaria.getPosicionTraspasante().getEmision().getSerie().setSerie(
							colocacionPrimaria.getPosicionTraspasante().getEmision().getSerie().getSerie().toUpperCase());
					colocacionPrimaria.getPosicionTraspasante().getEmision().getTipoValor().setClaveTipoValor(
							colocacionPrimaria.getPosicionTraspasante().getEmision().getTipoValor().getClaveTipoValor().toUpperCase());
					colocacionPrimaria.getPosicionTraspasante().getEmision().getEmisora().setDescripcion(posicion.getEmision().getEmisora().getDescripcion());
					colocacionPrimaria.getPosicionTraspasante().getEmision().setCupon(posicion.getEmision().getCupon());
					colocacionPrimaria.getPosicionTraspasante().getEmision().setIsin(posicion.getEmision().getIsin());
					colocacionPrimaria.getPosicionTraspasante().getEmision().getTipoValor().getMercado().setClaveMercado(
							posicion.getEmision().getTipoValor().getMercado().getClaveMercado());
					colocacionPrimaria.getPosicionTraspasante().setBoveda(new BovedaDTO());
					colocacionPrimaria.getPosicionTraspasante().getBoveda().setNombreCorto(posicion.getBoveda().getNombreCorto());
					colocacionPrimaria.getPosicionTraspasante().getBoveda().setId(posicion.getBoveda().getId());
					colocacionPrimaria.setSaldoDisponible(new BigDecimal(posicion.getPosicionDisponible()).longValue());

					if (posicion.getEmision().getFechaVencimiento() != null) {
						colocacionPrimaria.setDiasVigentes((int)utilService.dateDiff(fechasUtilService.getCurrentDate(),posicion.getEmision().getFechaVencimiento()));
					}
				}
			}

		}


		if ( posicion == null && StringUtils.isNotEmpty(colocacionPrimaria.getPosicionTraspasante().getEmision().getTipoValor().getClaveTipoValor())
				&& StringUtils.isNotEmpty(colocacionPrimaria.getPosicionTraspasante().getEmision().getEmisora().getDescripcion())
				&& StringUtils.isNotEmpty(colocacionPrimaria.getPosicionTraspasante().getEmision().getSerie().getSerie())) {

			EmisionDTO criterio = new EmisionDTO();
			criterio.setTipoValor(new TipoValorDTO());
			criterio.getTipoValor().setClaveTipoValor(colocacionPrimaria.getPosicionTraspasante().getEmision().getTipoValor().getClaveTipoValor().toUpperCase());
			criterio.setEmisora(new EmisoraDTO());
			criterio.getEmisora().setDescripcion(colocacionPrimaria.getPosicionTraspasante().getEmision().getEmisora().getDescripcion().toUpperCase());
			criterio.setSerie(new SerieDTO());
			criterio.getSerie().setSerie(colocacionPrimaria.getPosicionTraspasante().getEmision().getSerie().getSerie().toUpperCase());
			List<EmisionDTO> listaEmisiones = emisionDaliDAO.consultarEmisionesPorDescripciones(criterio, null);

			if (listaEmisiones != null && !listaEmisiones.isEmpty() && listaEmisiones.size() == 1) {
				// pasar los datos al objeto de la pantalla
				colocacionPrimaria.getPosicionTraspasante().getEmision().getEmisora().setDescripcion(listaEmisiones.get(0).getEmisora().getDescripcion());
				colocacionPrimaria.getPosicionTraspasante().getEmision().getTipoValor().setClaveTipoValor(
						listaEmisiones.get(0).getTipoValor().getClaveTipoValor());
				colocacionPrimaria.getPosicionTraspasante().getEmision().getTipoValor().getMercado().setClaveMercado(
						listaEmisiones.get(0).getTipoValor().getMercado().getClaveMercado());
				colocacionPrimaria.getPosicionTraspasante().getEmision().getSerie().setSerie(listaEmisiones.get(0).getSerie().getSerie());
				colocacionPrimaria.getPosicionTraspasante().getEmision().setIsin(listaEmisiones.get(0).getIsin());
				colocacionPrimaria.getPosicionTraspasante().getEmision().setCupon(listaEmisiones.get(0).getCupon());

				colocacionPrimaria.getPosicionTraspasante().setBoveda(new BovedaDTO());
				colocacionPrimaria.getPosicionTraspasante().getBoveda().setNombreCorto(listaEmisiones.get(0).getBoveda().getNombreCorto());
				colocacionPrimaria.getPosicionTraspasante().getBoveda().setId(listaEmisiones.get(0).getBoveda().getId());
				if (listaEmisiones.get(0).getFechaVencimiento() != null) {
					colocacionPrimaria.setDiasVigentes((int)utilService.dateDiff(fechasUtilService.getCurrentDate(),listaEmisiones.get(0).getFechaVencimiento()));
				}
			} else {
				colocacionPrimaria.getPosicionTraspasante().getEmision().setCupon(null);
				colocacionPrimaria.getPosicionTraspasante().getEmision().setIsin(null);
				colocacionPrimaria.getPosicionTraspasante().getEmision().getTipoValor().getMercado().setClaveMercado(null);
				colocacionPrimaria.getPosicionTraspasante().setBoveda(null);
				colocacionPrimaria.setSaldoDisponible(null);
				colocacionPrimaria.setDiasVigentes(null);
			}

		}
		return null;
	}

	/**
	 * Obtiene el campo servicesCapturaOperacionViewHelper
	 *
	 * @return servicesCapturaOperacionViewHelper
	 */
	public ServicesCapturaOperacionViewHelper getServicesCapturaOperacionViewHelper() {
		return servicesCapturaOperacionViewHelper;
	}

	/**
	 * Asigna el campo servicesCapturaOperacionViewHelper
	 *
	 * @param servicesCapturaOperacionViewHelper
	 *            el valor de servicesCapturaOperacionViewHelper a asignar
	 */
	public void setServicesCapturaOperacionViewHelper(ServicesCapturaOperacionViewHelper servicesCapturaOperacionViewHelper) {
		this.servicesCapturaOperacionViewHelper = servicesCapturaOperacionViewHelper;
	}

	/**
	 * Obtiene el campo mercadoDineroService
	 *
	 * @return mercadoDineroService
	 */
	public MercadoDineroService getMercadoDineroService() {
		return mercadoDineroService;
	}

	/**
	 * Asigna el campo mercadoDineroService
	 *
	 * @param mercadoDineroService
	 *            el valor de mercadoDineroService a asignar
	 */
	public void setMercadoDineroService(MercadoDineroService mercadoDineroService) {
		this.mercadoDineroService = mercadoDineroService;
	}

	/**
	 * Obtiene el campo configuracion
	 *
	 * @return configuracion
	 */
	public CaptOpsConfig getConfiguracion() {
		return configuracion;
	}

	/**
	 * Asigna el campo configuracion
	 *
	 * @param configuracion
	 *            el valor de configuracion a asignar
	 */
	public void setConfiguracion(CaptOpsConfig configuracion) {
		this.configuracion = configuracion;
	}

	/**
	 * Obtiene el campo validatorMismoDia
	 *
	 * @return validatorMismoDia
	 */
	public DTOValidator getValidatorMismoDia() {
		return validatorMismoDia;
	}

	/**
	 * Asigna el campo validatorMismoDia
	 *
	 * @param validatorMismoDia
	 *            el valor de validatorMismoDia a asignar
	 */
	public void setValidatorMismoDia(DTOValidator validatorMismoDia) {
		this.validatorMismoDia = validatorMismoDia;
	}

	/**
	 * Obtiene el campo validatorFechaValor
	 *
	 * @return validatorFechaValor
	 */
	public DTOValidator getValidatorFechaValor() {
		return validatorFechaValor;
	}

	/**
	 * Asigna el campo validatorFechaValor
	 *
	 * @param validatorFechaValor
	 *            el valor de validatorFechaValor a asignar
	 */
	public void setValidatorFechaValor(DTOValidator validatorFechaValor) {
		this.validatorFechaValor = validatorFechaValor;
	}

	/**
	 * Obtiene el campo validatorMismoDiaCompra
	 *
	 * @return validatorMismoDiaCompra
	 */
	public DTOValidator getValidatorMismoDiaCompra() {
		return validatorMismoDiaCompra;
	}

	/**
	 * Asigna el campo validatorMismoDiaCompra
	 *
	 * @param validatorMismoDiaCompra
	 *            el valor de validatorMismoDiaCompra a asignar
	 */
	public void setValidatorMismoDiaCompra(DTOValidator validatorMismoDiaCompra) {
		this.validatorMismoDiaCompra = validatorMismoDiaCompra;
	}

	/**
	 * Obtiene el campo validatorFechaValorCompra
	 *
	 * @return validatorFechaValorCompra
	 */
	public DTOValidator getValidatorFechaValorCompra() {
		return validatorFechaValorCompra;
	}

	/**
	 * Asigna el campo validatorFechaValorCompra
	 *
	 * @param validatorFechaValorCompra
	 *            el valor de validatorFechaValorCompra a asignar
	 */
	public void setValidatorFechaValorCompra(DTOValidator validatorFechaValorCompra) {
		this.validatorFechaValorCompra = validatorFechaValorCompra;
	}

	/**
	 * Obtiene el campo consultaCatalogos
	 *
	 * @return consultaCatalogos
	 */
	public ConsultaCatalogosFacade getConsultaCatalogos() {
		return consultaCatalogos;
	}

	/**
	 * Asigna el campo consultaCatalogos
	 *
	 * @param consultaCatalogos
	 *            el valor de consultaCatalogos a asignar
	 */
	public void setConsultaCatalogos(ConsultaCatalogosFacade consultaCatalogos) {
		this.consultaCatalogos = consultaCatalogos;
	}

	/**
	 * Obtiene el campo isoHelper
	 *
	 * @return isoHelper
	 */
	public IsoHelper getIsoHelper() {
		return isoHelper;
	}

	/**
	 * Asigna el campo isoHelper
	 *
	 * @param isoHelper
	 *            el valor de isoHelper a asignar
	 */
	public void setIsoHelper(IsoHelper isoHelper) {
		this.isoHelper = isoHelper;
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
	 * @param vo
	 *            el valor de vo a asignar
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

	public EmisionDaliDAO getEmisionDAO() {
		return emisionDaliDAO;
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
	 * @param consultaPosicionService
	 *            el valor del atributo consultaPosicionService a establecer
	 */
	public void setConsultaPosicionService(ConsultaPosicionService consultaPosicionService) {
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
	 * @param fechasUtilService
	 *            el valor del atributo fechasUtilService a establecer
	 */
	public void setFechasUtilService(FechasUtilService fechasUtilService) {
		this.fechasUtilService = fechasUtilService;
	}

	/**
	 * Establece el valor del atributo emisionDaliDAO
	 *
	 * @param emisionDaliDAO
	 *            el valor del atributo emisionDaliDAO a establecer
	 */
	public void setEmisionDAO(EmisionDaliDAO emisionDaliDAO) {
		this.emisionDaliDAO = emisionDaliDAO;
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
	 * @param idMercadoDinero
	 *            el valor del atributo idMercadoDinero a establecer
	 */
	public void setIdMercadoDinero(List<Long> idMercadoDinero) {
		this.idMercadoDinero = idMercadoDinero;
	}
}
