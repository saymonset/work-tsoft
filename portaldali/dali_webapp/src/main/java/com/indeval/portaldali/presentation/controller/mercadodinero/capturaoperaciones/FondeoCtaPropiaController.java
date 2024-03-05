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
import com.indeval.portaldali.presentation.dto.mercadodinero.FondeoCtaPropiaDTO;
import com.indeval.portaldali.presentation.helper.CalculoCapturaOperacionViewHelper;
import com.indeval.portaldali.presentation.helper.ServicesCapturaOperacionViewHelper;
import com.indeval.portaldali.presentation.util.ConsultaCatalogosFacade;
import com.indeval.portaldali.presentation.validation.DTOValidator;
import com.indeval.portaldali.vo.CaptOpsConfig;
import com.indeval.protocolofinanciero.api.vo.TraspasoLibrePagoVO;

/**
 * Controller de la Captura de Operaciones para la opción de fONDEO A CTA
 * PROPIA.
 * 
 * @author Marcos Rivas
 * 
 * @version 1.0
 */
public class FondeoCtaPropiaController extends CapturaOperacionesController {

	/** La clase que permite el acceso a la consulta de los catálogos utilizados */
	private ConsultaCatalogosFacade consultaCatalogosFacade = null;
	/**
	 * El DTO que representa a los elementos de la pantalla de fondeoCtaPropia
	 * nominal
	 */
	private FondeoCtaPropiaDTO fondeoCtaPropia = new FondeoCtaPropiaDTO();
	/** Servicio de negocio relacionado con el envo de operaciones */
	private EnvioOperacionesService envioOperacionesService = null;
	/** Servicio helper para la captura de operaciones */
	private ServicesCapturaOperacionViewHelper servicesCapturaOperacionViewHelper = null;
	/** Servicio de operaciones de mercado de dinero */
	private MercadoDineroService mercadoDineroService = null;
	/** Servicio para la obtencion de la posicion */
	private ConsultaPosicionService consultaPosicionService;
	/** Servicio para el acceso al cálculo de fechas */
	private FechasUtilService fechasUtilService = null;
	/** Configuracicn de operaciones */
	private CaptOpsConfig configuracion = null;
	/** Validador Captura Operaciones Reporto Nominal Opcion Mismo Dia */
	private DTOValidator valida = null;
	/**
	 * Contiene los cálculos de Simulado, Fecha Regreso, Importe y Premio
	 * necesarios para las pantallas de Captura de Operaciones
	 */
	private CalculoCapturaOperacionViewHelper calculos = new CalculoCapturaOperacionViewHelper();

	/** Valid Object de traspaso contrapago */
	private TraspasoLibrePagoVO vo = null;

	/** Para sacar secuencias */
	private UtilServices utilService = null;
	/** Identificadores del mercado a filtrar */
	private List<Long> idMercadoDinero = null;
	/** Dao para la obtención de la emisión */
	private EmisionDaliDAO emisionDaliDAO = null;

	
	public String getInitForm() {
		fondeoCtaPropia.setIdFolioTraspasante(getInstitucionActual().getClaveTipoInstitucion() + getInstitucionActual().getFolioInstitucion());
		fondeoCtaPropia.setIdFolioReceptor(getInstitucionActual().getClaveTipoInstitucion() + getInstitucionActual().getFolioInstitucion());
		fondeoCtaPropia.getPosicionTraspasante().getCuenta().setInstitucion(getInstitucionActual());
//		fondeoCtaPropia.setValorEn(MXP);
		return null;
	}

	/**
	 * Inicializa los datos de la página. *
	 * 
	 * @return <code>null</code> este método no retorna nada.
	 */
	public String getInit() {

		fondeoCtaPropia.setFechaConcertacion(servicesCapturaOperacionViewHelper.getFechasUtilService().getCurrentDate());

		if (fondeoCtaPropia.getIdFolioReceptor() != null) {
			InstitucionDTO inst = consultaCatalogosFacade.buscarInstitucionPorIdFolio(fondeoCtaPropia.getIdFolioReceptor());
			if (inst != null) {
				fondeoCtaPropia.getCuentaReceptor().setInstitucion(inst);
			} else {
				fondeoCtaPropia.setIdFolioReceptor(StringUtils.EMPTY);
				fondeoCtaPropia.setCuentaReceptor(new CuentaDTO());
			}
		}
		if (fondeoCtaPropia.getIdFolioTraspasante() != null) {
			InstitucionDTO inst = new InstitucionDTO();
			inst = consultaCatalogosFacade.buscarInstitucionPorIdFolio(fondeoCtaPropia.getIdFolioTraspasante());
			if (inst != null) {
				fondeoCtaPropia.getPosicionTraspasante().getCuenta().setInstitucion(inst);
				fondeoCtaPropia.setNetoEfectivo(consultaCatalogosFacade.getSaldoNetoEfectivo(fondeoCtaPropia.getIdFolioTraspasante()));
			} else {
				fondeoCtaPropia.setNetoEfectivo(null);
				fondeoCtaPropia.setIdFolioTraspasante(StringUtils.EMPTY);
			}
		} else {
			fondeoCtaPropia.setNetoEfectivo(null);
		}
		if (fondeoCtaPropia.getCuentaReceptor().getCuenta() != null) {
			CuentaDTO cnt = consultaCatalogosFacade.buscarCuentaPorNumeroCuentaNullSiNoExiste(fondeoCtaPropia.getIdFolioReceptor()
					+ fondeoCtaPropia.getCuentaReceptor().getCuenta());

			if (cnt != null) {
				fondeoCtaPropia.setCuentaReceptor(cnt);
			} else {
				fondeoCtaPropia.getCuentaReceptor().setTipoTenencia(new TipoTenenciaDTO());
				fondeoCtaPropia.getCuentaReceptor().setNumeroCuenta(StringUtils.EMPTY);
				fondeoCtaPropia.getCuentaReceptor().setCuenta(StringUtils.EMPTY);
				fondeoCtaPropia.getCuentaReceptor().setNombreCuenta(StringUtils.EMPTY);
			}
		}
		if (fondeoCtaPropia.getPosicionTraspasante().getCuenta().getCuenta() != null) {
			CuentaDTO cnt = consultaCatalogosFacade.buscarCuentaPorNumeroCuentaNullSiNoExiste(fondeoCtaPropia.getIdFolioTraspasante()
					+ fondeoCtaPropia.getPosicionTraspasante().getCuenta().getCuenta());
			if (cnt != null) {
				fondeoCtaPropia.getPosicionTraspasante().setCuenta(cnt);
			} else {

				fondeoCtaPropia.getPosicionTraspasante().getCuenta().setTipoTenencia(new TipoTenenciaDTO());
				fondeoCtaPropia.getPosicionTraspasante().getCuenta().setNumeroCuenta(StringUtils.EMPTY);
				fondeoCtaPropia.getPosicionTraspasante().getCuenta().setCuenta(StringUtils.EMPTY);
				fondeoCtaPropia.getPosicionTraspasante().getCuenta().setNombreCuenta(StringUtils.EMPTY);

			}
		}
		// obtener datos de la posicion
		obtenerDatosPosicion();

		return null;
	}

	/**
	 * Listener para el guardar que permite persistir la Operación en la BD
	 * 
	 * @return String Siguiente Página a redireccionar
	 */
	public void enviarOperacion(ActionEvent event) {
		super.limpiarCampos();
		
		try {				
			if (StringUtils.isEmpty(validarCuenta(event))) {
				fondeoCtaPropia.getPosicionTraspasante().getCuenta().setInstitucion(
						consultaCatalogosFacade.buscarInstitucionPorIdNoNulaFolio(fondeoCtaPropia.getIdFolioTraspasante()));
				fondeoCtaPropia.getCuentaReceptor().setInstitucion(consultaCatalogosFacade.buscarInstitucionPorIdNoNulaFolio(fondeoCtaPropia.getIdFolioReceptor()));
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
			if (!StringUtils.isEmpty(isoFirmado)) {
				try {
					grabarOperacion(event);
				}catch(Exception e) {
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
	 * Valida que el importe de la operación no sea mayor al neto efectivo
	 * 
	 * @param e
	 */
	public String validarCuenta(ActionEvent e) {
		mensajeUsuario = "";
		if (fondeoCtaPropia.getPosicionTraspasante().getCuenta().getCuenta() != null
				&& !fondeoCtaPropia.getPosicionTraspasante().getCuenta().getCuenta().equals("")) {
			if (fondeoCtaPropia.getPosicionTraspasante().getCuenta().getCuenta().equals(fondeoCtaPropia.getCuentaReceptor().getCuenta())
					&& (fondeoCtaPropia.getIdFolioTraspasante().equals(fondeoCtaPropia.getIdFolioReceptor()))) {
				mensajeUsuario = "La cuenta de Transpasante no puede ser igual a la cuenta del Receptor";
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, mensajeUsuario, mensajeUsuario));

				fondeoCtaPropia.getCuentaReceptor().setCuenta("");
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
		
		if(fondeoCtaPropia.getBoveda() != null ){
			vo.setBoveda(fondeoCtaPropia.getBoveda().getNombreCorto());
		}else{
			vo.setBoveda(null);
		}
		
		envioOperacionesService.grabaOperacion(vo, folioControl, false, datosAdicionales, null, isoFirmado);
		
		if (!existeErrorEnInvocacion()) {
			mensajeUsuario = "La operaci\u00f3n se llev\u00f3 a cabo exitosamente. Folio de la operaci\u00f3n : " + folioAsignado;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, mensajeUsuario, mensajeUsuario));
			fondeoCtaPropia = new FondeoCtaPropiaDTO();
			limpiarCampos(null);
		}
	}

	/**
	 * Realiza los cálculos simulados de la operacicn y construye el objeto de
	 * parámetros que el servicio de negocio espera
	 */
	private void calcularYRegistrarOperacicon() {
		// buscar la divisa por id
//		fondeoCtaPropia.setValorEn(consultaCatalogosFacade.buscarDivisaPorId(fondeoCtaPropia.getValorEn().getId()));

		// --- BOVEDA
		if(fondeoCtaPropia.getPosicionTraspasante().getBoveda() != null  &&  fondeoCtaPropia.getPosicionTraspasante().getBoveda().getId() >0 ){
			fondeoCtaPropia.setBoveda(consultaCatalogosFacade.buscarBovedaPorId(fondeoCtaPropia.getPosicionTraspasante().getBoveda().getId()));
		}else{
			fondeoCtaPropia.setBoveda(null);
		}
		
		boolean encontroError = false;
		RegistraOperacionParams registraOperacionParams = new RegistraOperacionParams();

		// --- AQUI SE LLEVA A CABO LA INSERCION DE LA OPERACION
		// Se deja fijo el ROL DEL AGENTE FIRMADO de Traspasante (T), Receptor
		// (R)
		registraOperacionParams.setRol("T");

		// --- TIPO DE OPERACION "F" PARA FONDEO A CUENTA PROPIA
		registraOperacionParams.setClaveReporto("T");

		// --- AGENTE TRASPASANTE
		registraOperacionParams.setTraspasante(DTOAssembler.crearAgenteVO(fondeoCtaPropia.getPosicionTraspasante().getCuenta()));

		// --- AGENTE DEL RECEPTOR
		registraOperacionParams.setReceptor(DTOAssembler.crearAgenteVO(fondeoCtaPropia.getCuentaReceptor()));
		registraOperacionParams.setEmision(new EmisionVO());

		// --- EMISION
		if (fondeoCtaPropia.getPrecioVector() != null) {
			registraOperacionParams.getEmision().setPrecioVector(new BigDecimal(fondeoCtaPropia.getPrecioVector()));
		}

		// --- SALDO DISPONIBLE
		if (fondeoCtaPropia.getSaldoDisponible() != null) {
			registraOperacionParams.getEmision().setSaldoDisponible(new BigDecimal(fondeoCtaPropia.getSaldoDisponible().doubleValue()));
		}

		// -- TIPO VALOR, --EMISORA, --SERIE, --CUPON
		registraOperacionParams.getEmision().setIdTipoValor(fondeoCtaPropia.getPosicionTraspasante().getEmision().getTipoValor().getClaveTipoValor());
		registraOperacionParams.getEmision().setEmisora(fondeoCtaPropia.getPosicionTraspasante().getEmision().getEmisora().getDescripcion());
		registraOperacionParams.getEmision().setSerie(fondeoCtaPropia.getPosicionTraspasante().getEmision().getSerie().getSerie());
		registraOperacionParams.getEmision().setCupon(fondeoCtaPropia.getPosicionTraspasante().getEmision().getCupon());
		registraOperacionParams.getEmision().setMercado(fondeoCtaPropia.getPosicionTraspasante().getEmision().getTipoValor().getMercado().getClaveMercado());

		// --- DIAS VIGENTES
		if (fondeoCtaPropia.getDiasVigentes() != null) {
			registraOperacionParams.setDiasVigentes(fondeoCtaPropia.getDiasVigentes());
		}

		// --- SIMULADO
		if (fondeoCtaPropia.getSimulado() != null) {
			registraOperacionParams.setSimulado(new BigInteger(fondeoCtaPropia.getSimulado().toString()));
		}
		// --- NETO EFECTIVO
		if (fondeoCtaPropia.getNetoEfectivo() != null) {
			registraOperacionParams.setNetoEfectivo(new BigDecimal(fondeoCtaPropia.getNetoEfectivo()));
		}

		registraOperacionParams.setPlazoReporto(Integer.valueOf("0"));

		// --- AQUI SE INTRODUCE UN "CERO" PARA EL CASO DE QUE VENGA NULO EL
		// --- PLAZO LIQUIDACION(HORAS) ESTO APLICA TANTO PARA OPERACIONES
		// --- "MISMO DIA" Y PARA LOS TIPOS DE OPERACION
		// --- FONDEO CUENTA PROPIA Y TRASPASO LIBRE DE PAGO

		registraOperacionParams.setPlazoLiquidacion(Integer.valueOf("0"));
		registraOperacionParams.setFechaHoraCierreOper(fondeoCtaPropia.getFechaHoraCierreOper());

		CalculoCapturaOperacionViewHelper calculos = new CalculoCapturaOperacionViewHelper();

		calculos.setTipoOperacion(registraOperacionParams.getClaveReporto());
		calculos.setCantidadOperadaLeida(fondeoCtaPropia.getCantidadOperada().toString());
		calculos.setSaldoDisponibleLeido(fondeoCtaPropia.getSaldoDisponible() != null ? fondeoCtaPropia.getSaldoDisponible().toString() : "0");
		calculos.setPlazoRepDiasLeido(registraOperacionParams.getPlazoReporto() != null ? registraOperacionParams.getPlazoReporto().toString() : "0");
		calculos.setPlazoRepDiasInhabilitadoLeido("false");

		calculos.setPlazoLiquidacionHorasInhabilitadoLeido("true");
		calculos.setPlazoLiquidacionHorasInhabilitado(true);
		calculos.setPrecioTituloInhabilitadoLeido("false");
		calculos.setImporteInhabilitadoLeido("false");
		calculos.setTasaPremioInhabilitadoLeido("false");
		calculos.setTasaPremioLeido(null);

		servicesCapturaOperacionViewHelper.realizarCalculos(calculos);

		// --- ENCONTRO UN ERROR AL CALCULAR LA FECHA DE REGRESO
		if (calculos != null && calculos.getMensajeFechaRegreso() != null && !calculos.getMensajeFechaRegreso().trim().equalsIgnoreCase("")) {
			encontroError = true;
		}
		// --- ENCONTRO UN ERROR AL CALCULAR EL IMPORTE
		if (calculos != null && calculos.getMensajeImporte() != null && !calculos.getMensajeImporte().trim().equalsIgnoreCase("")) {
			encontroError = true;
		}
		// --- ENCONTRO UN ERROR AL CALCULAR EL PRECIO TITULO
		if (calculos != null && calculos.getMensajePrecioTitulo() != null && !calculos.getMensajePrecioTitulo().trim().equalsIgnoreCase("")) {
			encontroError = true;
		}
		// --- ENCONTRO UN ERROR AL CALCULAR EL PREMIO
		if (calculos != null && calculos.getMensajePremio() != null && !calculos.getMensajePremio().trim().equalsIgnoreCase("")) {
			encontroError = true;
		}
		// --- ENCONTRO UN ERROR AL CALCULAR EL SIMULADO
		if (calculos != null && calculos.getMensajeSimulado() != null && !calculos.getMensajeSimulado().trim().equalsIgnoreCase("")) {
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
				registraOperacionParams.setFechaRegreso(servicesCapturaOperacionViewHelper.getFechasUtilService().getCurrentDate());
			}

			// --- TASA PREMIO
			if (calculos.getPremio() != null && !calculos.getPremio().equals("")) {
				registraOperacionParams.setTasaPremio(calculos.getPremio());
			}

			// --- FECHA CONCERTACION
			if (fondeoCtaPropia.getFechaConcertacion() != null) {
				registraOperacionParams.setFechaConcertacion(fondeoCtaPropia.getFechaConcertacion());
			}

			// --- CANTIDAD OPERADA
			if (fondeoCtaPropia.getCantidadOperada() != null) {
				registraOperacionParams.setCantidadOperada(new BigDecimal(fondeoCtaPropia.getCantidadOperada()));
			} else {
				registraOperacionParams.setCantidadOperada(new BigDecimal(0));
			}
			
			if (fondeoCtaPropia.getBoveda() != null) {
				registraOperacionParams.setIdBoveda(BigInteger.valueOf(fondeoCtaPropia.getBoveda().getId()));
			}

			// --- VALOR EN
//			if (fondeoCtaPropia.getValorEn() != null) {
//				registraOperacionParams.setDivisa(fondeoCtaPropia.getValorEn().getClaveAlfabetica());
//			}

			// --- IMPORTE
			if (calculos.getImporte() != null) {
				registraOperacionParams.setImporte(calculos.getImporte());
			}
			if (fondeoCtaPropia.getImporte() != null) {
				registraOperacionParams.setImporte(new BigDecimal(fondeoCtaPropia.getImporte()));
			} else {
				registraOperacionParams.setImporte(new BigDecimal(0));
			}

			registraOperacionParams.setFechaLiq(servicesCapturaOperacionViewHelper.calculaFechaLiquidacion(registraOperacionParams.getPlazoLiquidacion()));
			if (this.configuracion.getTiposOperacionesFirmadas().contains(registraOperacionParams.getClaveReporto())) {

				folioAsignado = mercadoDineroService.validaRegistraOperacionBusinessRules(registraOperacionParams);
				if (!existeErrorEnInvocacion()) {
					vo = new TraspasoLibrePagoVO();

					vo = servicesCapturaOperacionViewHelper.inicializaTraspasoLibrePagoVO(registraOperacionParams);
					vo.setReferenciaOperacion(folioAsignado);
					vo.setTipoInstruccion(calculos.getTipoOperacion());
					vo.setReferenciaMensaje(utilService.getFolio(Constantes.SEQ_REFERENCIA_MENSAJE).toString());
					if(fondeoCtaPropia.getBoveda() != null ){
						vo.setBoveda(fondeoCtaPropia.getBoveda().getNombreCorto());
					}else{
						vo.setBoveda(null);
					}

					// Si el usuario debe firmar la operacion, se crea el iso
					if (isUsuarioConFacultadFirmar()) {
						isoSinFirmar = isoHelper.creaISO(vo, false);
						hashIso = cdb.cipherHash(isoSinFirmar);
					} else {
						enviarOperacionABitacora();
					}
				}

			} else {
				// TODO operación no soportada
			}
		}
	}

	/** Calcular el saldo simulado. */
	public void realizarCalculo(ActionEvent e) {
		// EL TIPO DE OPERACION ES FONDEO A CUENTA PROPIA
		calculos.setTipoOperacion("F");

		calculos.setCantidadOperadaLeida(fondeoCtaPropia.getCantidadOperada() != null ? fondeoCtaPropia.getCantidadOperada().toString() : "");
		calculos.setCantidadOperada(new BigDecimal(fondeoCtaPropia.getCantidadOperada() != null ? fondeoCtaPropia.getCantidadOperada().doubleValue() : 0));
		calculos.setImporte(new BigDecimal(fondeoCtaPropia.getImporte() != null ? fondeoCtaPropia.getImporte().doubleValue() : 0));
		calculos.setSaldoDisponibleLeido(fondeoCtaPropia.getSaldoDisponible() != null ? fondeoCtaPropia.getSaldoDisponible().toString() : "");

		calculos.setPrecioTituloInhabilitadoLeido("false");
		calculos.setImporteInhabilitadoLeido("false");
		calculos.setTasaPremioInhabilitadoLeido("false");
		calculos.setPlazoRepDiasInhabilitadoLeido("false");

		servicesCapturaOperacionViewHelper.realizarCalculos(calculos);

		/*
		 * Realiza el cálculo del saldo Simulado se necesitan:
		 * cantidadOperadaLeida, saldoDisponibleLeido
		 */
		fondeoCtaPropia.setSimulado(calculos.getSimulado() != null ? calculos.getSimulado().longValue() : null);
		/*
		 * Realiza el cálculo del Importe se necesitan: cantidadOperadaLeida,
		 * precioTituloLeido y debe estar habilitado
		 * precioTituloInhabilitadoLeido
		 */
		fondeoCtaPropia.setImporte(calculos.getImporte() != null ? calculos.getImporte().doubleValue() : null);

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
		if (StringUtils.isNotEmpty(fondeoCtaPropia.getPosicionTraspasante().getCuenta().getCuenta())
				&& StringUtils.isNotEmpty(fondeoCtaPropia.getPosicionTraspasante().getEmision().getTipoValor().getClaveTipoValor())
				&& StringUtils.isNotEmpty(fondeoCtaPropia.getPosicionTraspasante().getEmision().getEmisora().getDescripcion())
				&& StringUtils.isNotEmpty(fondeoCtaPropia.getPosicionTraspasante().getEmision().getSerie().getSerie())) {
			PosicionDTO criterio = new PosicionDTO();

			criterio.setCuenta(new CuentaDTO());
			criterio.getCuenta().setTipoCustodia(TipoCustodiaConstants.VALORES);
			criterio.getCuenta().setTipoTenencia(new TipoTenenciaDTO());
			criterio.getCuenta().getTipoTenencia().setTipoCuenta(new TipoCuentaDTO(TipoCuentaConstants.TIPO_NOMBRADA, ""));
			criterio.getCuenta().getTipoTenencia().setTipoNaturaleza(new TipoNaturalezaDTO(TipoNaturalezaDTO.PASIVO, ""));
			criterio.getCuenta().setNumeroCuenta(
					getInstitucionActual().getClaveTipoInstitucion() + getInstitucionActual().getFolioInstitucion()
							+ fondeoCtaPropia.getPosicionTraspasante().getCuenta().getCuenta());
			criterio.setEmision(new EmisionDTO());
			InstitucionDTO institucion = null;
			institucion = consultaCatalogosFacade.buscarInstitucionPorIdFolio(fondeoCtaPropia.getIdFolioTraspasante());

			if (institucion != null) {
				// cuenta
				if (StringUtils.isNotEmpty(fondeoCtaPropia.getPosicionTraspasante().getCuenta().getCuenta())) {
					criterio.getCuenta().setNumeroCuenta(
							institucion.getClaveTipoInstitucion() + institucion.getFolioInstitucion()
									+ fondeoCtaPropia.getPosicionTraspasante().getCuenta().getCuenta());
				} else {
					criterio.getCuenta().setCuenta("-1");
				}

				// TV
				criterio.getEmision().setTipoValor(
						consultaCatalogosFacade.buscarTipoValorPorClave(fondeoCtaPropia.getPosicionTraspasante().getEmision().getTipoValor()
								.getClaveTipoValor()));
				// Emisora
				criterio.getEmision().setEmisora(
						consultaCatalogosFacade
								.buscarEmisoraPorNombreCorto(fondeoCtaPropia.getPosicionTraspasante().getEmision().getEmisora().getDescripcion()));
				criterio.getEmision().setSerie(new SerieDTO());
				// Serie
				criterio.getEmision().getSerie().setSerie(fondeoCtaPropia.getPosicionTraspasante().getEmision().getSerie().getSerie().trim());

				if (fondeoCtaPropia.getPosicionTraspasante().getBoveda() != null && fondeoCtaPropia.getPosicionTraspasante().getBoveda().getId() > 0) {
					criterio.setBoveda(new BovedaDTO());
					criterio.getBoveda().setId(fondeoCtaPropia.getPosicionTraspasante().getBoveda().getId());
				}else{
					criterio.getBoveda().setId(NumberUtils.toLong("-1", DaliConstants.VALOR_COMBO_TODOS));
				}
				
				criterio.getCuenta().setInstitucion(institucion);
				criterio.setFechaFinal(new Date());

				CriterioOrdenamientoDTO orden = new CriterioOrdenamientoDTO();
				orden.setColumna("sortCuenta");

				List<PosicionDTO> listaPosiciones = consultaPosicionService.consultarPosicionesPorMercado(criterio, null, orden, idMercadoDinero
						.toArray(new Long[] {}));
				
				fondeoCtaPropia.getPosicionTraspasante().getEmision().setCupon(null);
				fondeoCtaPropia.getPosicionTraspasante().getEmision().setIsin(null);
				fondeoCtaPropia.getPosicionTraspasante().getEmision().getTipoValor().getMercado().setClaveMercado(null);
				fondeoCtaPropia.getPosicionTraspasante().setBoveda(null);
				fondeoCtaPropia.setSaldoDisponible(null);
				fondeoCtaPropia.setDiasVigentes(null);

				if (listaPosiciones != null && !listaPosiciones.isEmpty()) {
					posicion = listaPosiciones.get(0);
					// obtencion de los datos para ser desplegado en pantalla
					fondeoCtaPropia.getPosicionTraspasante().getCuenta().setCuenta(posicion.getCuenta().getCuenta());
					fondeoCtaPropia.getPosicionTraspasante().getEmision().getEmisora().setDescripcion(posicion.getEmision().getEmisora().getDescripcion());
					fondeoCtaPropia.getPosicionTraspasante().getEmision().setCupon(posicion.getEmision().getCupon());
					fondeoCtaPropia.getPosicionTraspasante().getEmision().setIsin(posicion.getEmision().getIsin());
					fondeoCtaPropia.getPosicionTraspasante().getEmision().getTipoValor().getMercado().setClaveMercado(
							posicion.getEmision().getTipoValor().getMercado().getClaveMercado());
					fondeoCtaPropia.getPosicionTraspasante().setBoveda(new BovedaDTO());
					fondeoCtaPropia.getPosicionTraspasante().getBoveda().setNombreCorto(posicion.getBoveda().getNombreCorto());
					fondeoCtaPropia.getPosicionTraspasante().getBoveda().setId(posicion.getBoveda().getId());
					fondeoCtaPropia.setSaldoDisponible(new BigDecimal(posicion.getPosicionDisponible()).longValue());

					if (posicion.getEmision().getFechaVencimiento() != null) {
						fondeoCtaPropia.setDiasVigentes((int)utilService.dateDiff(fechasUtilService.getCurrentDate(),posicion.getEmision().getFechaVencimiento()));
					}
				}  
			}

		}

		if (posicion == null && StringUtils.isNotEmpty(fondeoCtaPropia.getPosicionTraspasante().getEmision().getTipoValor().getClaveTipoValor())
				&& StringUtils.isNotEmpty(fondeoCtaPropia.getPosicionTraspasante().getEmision().getEmisora().getDescripcion())
				&& StringUtils.isNotEmpty(fondeoCtaPropia.getPosicionTraspasante().getEmision().getSerie().getSerie())) {

			EmisionDTO criterio = new EmisionDTO();
			criterio.setTipoValor(new TipoValorDTO());
			criterio.getTipoValor().setClaveTipoValor(fondeoCtaPropia.getPosicionTraspasante().getEmision().getTipoValor().getClaveTipoValor());
			criterio.setEmisora(new EmisoraDTO());
			criterio.getEmisora().setDescripcion(fondeoCtaPropia.getPosicionTraspasante().getEmision().getEmisora().getDescripcion());
			criterio.setSerie(new SerieDTO());
			criterio.getSerie().setSerie(fondeoCtaPropia.getPosicionTraspasante().getEmision().getSerie().getSerie());
			List<EmisionDTO> listaEmisiones = emisionDaliDAO.consultarEmisionesPorDescripciones(criterio, null);

			if (listaEmisiones != null && !listaEmisiones.isEmpty() && listaEmisiones.size() == 1) {
				// pasar los datos al objeto de la pantalla
				fondeoCtaPropia.getPosicionTraspasante().getEmision().getEmisora().setDescripcion(listaEmisiones.get(0).getEmisora().getDescripcion());
				fondeoCtaPropia.getPosicionTraspasante().getEmision().getTipoValor()
						.setClaveTipoValor(listaEmisiones.get(0).getTipoValor().getClaveTipoValor());
				fondeoCtaPropia.getPosicionTraspasante().getEmision().getSerie().setSerie(listaEmisiones.get(0).getSerie().getSerie());
				fondeoCtaPropia.getPosicionTraspasante().getEmision().getTipoValor().getMercado().setClaveMercado(
						listaEmisiones.get(0).getTipoValor().getMercado().getClaveMercado());
				fondeoCtaPropia.getPosicionTraspasante().getEmision().setIsin(listaEmisiones.get(0).getIsin());
				fondeoCtaPropia.getPosicionTraspasante().getEmision().setCupon(listaEmisiones.get(0).getCupon());
				
				fondeoCtaPropia.getPosicionTraspasante().setBoveda(new BovedaDTO());
				fondeoCtaPropia.getPosicionTraspasante().getBoveda().setNombreCorto(listaEmisiones.get(0).getBoveda().getNombreCorto());
				fondeoCtaPropia.getPosicionTraspasante().getBoveda().setId(listaEmisiones.get(0).getBoveda().getId());
				
				if (listaEmisiones.get(0).getFechaVencimiento() != null) {
					fondeoCtaPropia.setDiasVigentes((int)utilService.dateDiff(fechasUtilService.getCurrentDate(),listaEmisiones.get(0).getFechaVencimiento()));
				}
			} else {
				fondeoCtaPropia.getPosicionTraspasante().getEmision().setCupon(null);
				fondeoCtaPropia.getPosicionTraspasante().getEmision().setIsin(null);
				fondeoCtaPropia.getPosicionTraspasante().getEmision().getTipoValor().getMercado().setClaveMercado(null);
				fondeoCtaPropia.getPosicionTraspasante().setBoveda(null);
				fondeoCtaPropia.setSaldoDisponible(null);
				fondeoCtaPropia.setDiasVigentes(null);
			}

		}
		return null;
	}

	/**
	 * método para validar la existencia de los campos requeridos y su formato.
	 */
	private boolean validarDTO() {
		ResultadoValidacionDTO resultadao = null;
		resultadao = valida.validarDTO(fondeoCtaPropia);
		
		if(!fondeoCtaPropia.getIdFolioTraspasante().equals(fondeoCtaPropia.getIdFolioReceptor())) {
			resultadao.setValido(Boolean.FALSE);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"El traspasante y receptor deben ser la misma instituci\u00F3n", "El traspasante y receptor deben ser la misma instituci\u00F3n"));
			
			return resultadao.isValido();
			
		}

		if (!resultadao.isValido()) {

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, resultadao.getMensaje(), resultadao.getMensaje()));
		}
		return resultadao.isValido();
	}

	/**
	 * Limpiar los campos de la página respetando las opciones de la operacion.
	 */
	public void limpiarCampos(ActionEvent e) {
		fondeoCtaPropia = new FondeoCtaPropiaDTO();
		fondeoCtaPropia.setIdFolioTraspasante(getInstitucionActual().getClaveTipoInstitucion() + getInstitucionActual().getFolioInstitucion());
		fondeoCtaPropia.setIdFolioReceptor(getInstitucionActual().getClaveTipoInstitucion() + getInstitucionActual().getFolioInstitucion());
		fondeoCtaPropia.setFechaConcertacion(new Date());
//		fondeoCtaPropia.setValorEn(MXP);
		super.limpiarCampos();
	}

	/** ************** métodos getters & setters */

	/**
	 * @return the consultaCatalogosFacade
	 */
	public ConsultaCatalogosFacade getConsultaCatalogosFacade() {
		return consultaCatalogosFacade;
	}

	/**
	 * @param consultaCatalogosFacade
	 *            the consultaCatalogosFacade to set
	 */
	public void setConsultaCatalogosFacade(ConsultaCatalogosFacade consultaCatalogosFacade) {
		this.consultaCatalogosFacade = consultaCatalogosFacade;
	}

	/**
	 * @return the fondeoCtaPropia
	 */
	public FondeoCtaPropiaDTO getFondeoCtaPropia() {
		return fondeoCtaPropia;
	}

	/**
	 * @param fondeoCtaPropia
	 *            the fondeoCtaPropia to set
	 */
	public void setFondeoCtaPropia(FondeoCtaPropiaDTO fondeoCtaPropia) {
		this.fondeoCtaPropia = fondeoCtaPropia;
	}

	/**
	 * @return the envioOperacionesService
	 */
	public EnvioOperacionesService getEnvioOperacionesService() {
		return envioOperacionesService;
	}

	/**
	 * @param envioOperacionesService
	 *            the envioOperacionesService to set
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
	 * @param servicesCapturaOperacionViewHelper
	 *            the servicesCapturaOperacionViewHelper to set
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
	 * @param mercadoDineroService
	 *            the mercadoDineroService to set
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
	 * @param configuracion
	 *            the configuracion to set
	 */
	public void setConfiguracion(CaptOpsConfig configuracion) {
		this.configuracion = configuracion;
	}

	/**
	 * @return the valida
	 */
	public DTOValidator getValida() {
		return valida;
	}

	/**
	 * @param valida
	 *            the valida to set
	 */
	public void setValida(DTOValidator valida) {
		this.valida = valida;
	}

	/**
	 * @return the calculos
	 */
	public CalculoCapturaOperacionViewHelper getCalculos() {
		return calculos;
	}

	/**
	 * @param calculos
	 *            the calculos to set
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
	 * @param vo
	 *            el valor de vo a asignar
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
	 * @param utilService
	 *            the utilService to set
	 */
	public void setUtilService(UtilServices utilService) {
		this.utilService = utilService;
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
	 * @param emisionDaliDAO
	 *            el valor del atributo emisionDaliDAO a establecer
	 */
	public void setEmisionDAO(EmisionDaliDAO emisionDaliDAO) {
		this.emisionDaliDAO = emisionDaliDAO;
	}
}