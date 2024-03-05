/*
 *Copyright (c) 2005-2007 Bursatec. All Rights Reserved
 */
package com.indeval.portaldali.presentation.controller.tesoreria.fondos;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.bursatec.seguridad.presentation.constants.SeguridadConstants;
import com.indeval.portaldali.middleware.dto.BovedaDTO;
import com.indeval.portaldali.middleware.dto.CuentaEfectivoDTO;
import com.indeval.portaldali.middleware.dto.DivisaDTO;
import com.indeval.portaldali.middleware.dto.InstitucionDTO;
import com.indeval.portaldali.middleware.dto.TipoCuentaDTO;
import com.indeval.portaldali.middleware.dto.TipoInstruccionDTO;
import com.indeval.portaldali.middleware.dto.TipoNaturalezaDTO;
import com.indeval.portaldali.middleware.dto.util.DTOAssembler;
import com.indeval.portaldali.middleware.dto.util.ResultadoValidacionDTO;
import com.indeval.portaldali.middleware.services.common.constants.DaliConstants;
import com.indeval.portaldali.middleware.services.common.constants.TipoCuentaConstants;
import com.indeval.portaldali.middleware.services.common.constants.TipoCustodiaConstants;
import com.indeval.portaldali.middleware.services.efectivo.MovimientosEfectivoService;
import com.indeval.portaldali.middleware.services.enviooperaciones.EnvioOperacionesService;
import com.indeval.portaldali.middleware.services.mercadodinero.RegistraOperacionParams;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import com.indeval.portaldali.middleware.services.modelo.InfrastructureException;
import com.indeval.portaldali.middleware.services.tesoreria.TesoreriaService;
import com.indeval.portaldali.middleware.services.util.UtilServices;
import com.indeval.portaldali.middleware.services.util.util.Constantes;
import com.indeval.portaldali.presentation.common.constants.TesoreriaConstantes;
import com.indeval.portaldali.presentation.common.constants.UtilConstantes;
import com.indeval.portaldali.presentation.controller.mercadodinero.capturaoperaciones.CapturaOperacionesController;
import com.indeval.portaldali.presentation.dto.mercadodinero.TraspasoEfecCtasControlDTO;
import com.indeval.portaldali.presentation.helper.CalculoCapturaOperacionViewHelper;
import com.indeval.portaldali.presentation.helper.ServicesCapturaOperacionViewHelper;
import com.indeval.portaldali.presentation.util.ConsultaCatalogosFacade;
import com.indeval.portaldali.presentation.validation.DTOValidator;
import com.indeval.portaldali.vo.CaptOpsConfig;
import com.indeval.protocolofinanciero.api.vo.TraspasoEfectivoVO;

/**
 * Controller de la pantalla de Traspasos de Efectivo entre Cuentas de Control
 *
 * @author
 * @version 1.0
 */
public class TraspasosEfecCtasControlController extends CapturaOperacionesController {

	/** El DTO con los campos de Traspasos de Efectivo entre Cuentas de Control */
	private TraspasoEfecCtasControlDTO traspaso = new TraspasoEfecCtasControlDTO();

	/** La clase que permite el acceso a la consulta de los catálogos utilizados */
	private ConsultaCatalogosFacade consultaCatalogosFacade = null;

	/** Servicio para de Operaciones de Efectivo. */
	private MovimientosEfectivoService movimientosEfectivoService;

	/** Servicio helper para la captura de operaciones */
	private ServicesCapturaOperacionViewHelper servicesCapturaOperacionViewHelper = null;

	/** Servicio de operaciones de mercado de dinero */
	private TesoreriaService tesoreriaService = null;

	/** Configuracicn de operaciones */
	private CaptOpsConfig configuracion = null;

	/** Validador Captura Operaciones Reporto Nominal Opcion Fecha Valor */
	private DTOValidator validadorTraspasoEfect = null;

	/** Si la operación de registro es exitosa */
	private Boolean operacionExitosa = Boolean.FALSE;

	

	/** VO con los datos a registras sobre la operacion */
	private TraspasoEfectivoVO vo = null;

	/** Servicio para asignar folio apartir de una secuencia */
	private UtilServices utilService;

	private boolean primeraVez;
	/**
	 * Constructor
	 */
	public TraspasosEfecCtasControlController(){
		traspaso.setValorEn(MXP);
		
		this.primeraVez = true;
	}
	/**
	 * Contiene los cálculos de Simulado, Fecha Regreso, Importe y Premio
	 * necesarios para las pantallas de Captura de Operaciones
	 */
	private CalculoCapturaOperacionViewHelper calculos = new CalculoCapturaOperacionViewHelper();

	public String getValidaFacultadFirma() {
		logger.info("getValidaFacultadFirma: " + this.primeraVez + " - " + isUsuarioConFacultadFirmar());
		
		if(this.primeraVez && !isUsuarioConFacultadFirmar()) {
			String mensaje = "Usted No tiene habilitada la facultad de firma digital.";
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, mensaje, mensaje));
		}
		
		this.primeraVez = false;
		
		return null;
	}
	/**
	 * Inicializa los datos de la página. *
	 *
	 * @return <code>null</code> este método no retorna nada.
	 */
	public String getInit() {
		traspaso.getCuentaDestino().setInstitucion(new InstitucionDTO());
		if (traspaso.getIdFolioTraspasante() == null) {
			traspaso.setIdFolioTraspasante(getInstitucionActual().getClaveTipoInstitucion() + getInstitucionActual().getFolioInstitucion());
		}
		if (traspaso.getIdFolioTraspasante() != null) {
			InstitucionDTO inst = consultaCatalogosFacade.buscarInstitucionPorIdFolio(traspaso.getIdFolioTraspasante());
			CuentaEfectivoDTO cnt = buscarParticipanteEfectivo(traspaso.getIdFolioTraspasante());
			if (inst != null) {
				traspaso.getCuentaOrigen().setInstitucion(inst);
			}
			if (cnt != null) {
				traspaso.getCuentaOrigen().setDescripcion(cnt.getDescripcion());
				traspaso.getCuentaOrigen().setCuenta(cnt.getCuenta());
				if(inst!=null)
				traspaso.getCuentaOrigen().setNombreCuenta(inst.getNombreCorto());
			}
			traspaso.setSaldoDisponible(consultaCatalogosFacade.getSaldoNetoEfectivo(traspaso.getIdFolioTraspasante()));
		}
		if (traspaso.getIdFolioReceptor() != null && !traspaso.getIdFolioReceptor().equals("")) {
			InstitucionDTO instR = consultaCatalogosFacade.buscarInstitucionPorIdFolio(traspaso.getIdFolioReceptor());
			CuentaEfectivoDTO cntR = buscarParticipanteEfectivo(traspaso.getIdFolioReceptor());
			if (instR != null) {
				traspaso.getCuentaDestino().setInstitucion(instR);
			}
			if (cntR != null) {
				traspaso.getCuentaDestino().setDescripcion(cntR.getDescripcion());
				traspaso.getCuentaDestino().setCuenta(cntR.getCuenta());
				traspaso.getCuentaDestino().setNombreCuenta(instR.getNombreCorto());
			}
		}

		BovedaDTO boveda = new BovedaDTO();
		boveda.setId(BovedaDTO.BOVEDA_BANXICO);
		traspaso.setBoveda(boveda);
		traspaso.setValorEn(MXP);

		if(StringUtils.isEmpty(this.traspaso.getIdFolioReceptor())){
			if(this.traspaso.getCuentaDestino()!=null){
			this.traspaso.getCuentaDestino().setCuenta("");
			this.traspaso.getCuentaDestino().setDescripcion("");
			this.traspaso.getCuentaDestino().setNombreCuenta("");
			this.traspaso.getCuentaDestino().setNumeroCuenta("");
			}

		}
		return null;
	}

	/**
	 * método que atiende las solicitudes del catálogo de cuentas tipo Efectivo
	 * Nombrada.
	 *
	 * @param prefijo
	 *            Criterio de búsqueda
	 * @return Lista con las cuentas
	 */
	public CuentaEfectivoDTO buscarParticipanteEfectivo(String idFolioPariticipante) {
		CuentaEfectivoDTO criterio = new CuentaEfectivoDTO();
		CuentaEfectivoDTO resultado = null;
		List<CuentaEfectivoDTO> resultados = new ArrayList<CuentaEfectivoDTO>();

		InstitucionDTO institucion = new InstitucionDTO();
		criterio.setTipoCustodia(TipoCustodiaConstants.EFECTIVO);
		criterio.setTipoCuenta(new TipoCuentaDTO(TipoCuentaConstants.TIPO_NOMBRADA, ""));
		criterio.setTipoNaturaleza(new TipoNaturalezaDTO(TipoNaturalezaDTO.PASIVO, ""));

		if (!StringUtils.isEmpty(idFolioPariticipante)) {
			institucion = consultaCatalogosFacade.buscarInstitucionPorIdFolio(idFolioPariticipante);
		}
		if (institucion != null) {
			criterio.setInstitucion(institucion);
			criterio.setNumeroCuenta(String.valueOf(DaliConstants.VALOR_COMBO_TODOS));
			resultados = consultaCatalogosFacade.getConsultaCuentaService().buscarCuentasNombradasEfectivo(criterio, null);
			if (resultados != null && !resultados.isEmpty()) {
				resultado = resultados.get(0);
			}
		}
		BovedaDTO boveda = new BovedaDTO();
		boveda.setId(new Long(11));
		traspaso.setBoveda(boveda);
		return resultado;
	}

	/**
	 * método para validar la existencia de los campos requeridos y su formato.
	 */
	private boolean validarDTO() {
		ResultadoValidacionDTO resultado = null;
		resultado = validadorTraspasoEfect.validarDTO(traspaso);

		if (traspaso.getIdFolioReceptor().equals(traspaso.getIdFolioTraspasante())) {

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"El traspasante no puede ser el receptor", "El traspasante no puede ser el receptor"));

			resultado.setValido(Boolean.FALSE);
			return resultado.isValido();

		}

		if (!resultado.isValido()) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, resultado.getMensaje(), resultado.getMensaje()));
		}
		if(traspaso.getImporteATraspasar() != null && traspaso.getImporteATraspasar().doubleValue() <= 0) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "El importe a traspasar debe ser mayor a 0", "El importe a traspasar debe ser mayor a 0"));
			resultado.setValido(Boolean.FALSE);
		}
		if(traspaso.getIdFolioTraspasante() == null || traspaso.getIdFolioTraspasante().equals("")) {

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "El participante es requerido", "El participante es requerido"));
			resultado.setValido(Boolean.FALSE);
		}
		if(traspaso.getIdFolioReceptor() == null || traspaso.getIdFolioReceptor().equals("")) {

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "El receptor es requerido", "El receptor es requerido"));
			resultado.setValido(Boolean.FALSE);
		}

		return resultado.isValido();
	}

	/**
	 * Limpiar los campos de la página respetando las opciones de la operacion.
	 */
	public void limpiarCampos(ActionEvent event) {
		traspaso = new TraspasoEfecCtasControlDTO();
		traspaso.setValorEn(MXP);
		this.limpiarCampos();
		getInit();
	}

	/**
	 * Listener para el guardar que permite persistir la Operación en la BD
	 *
	 */
	public void enviarOperacion(ActionEvent event) {
		logger.debug("enviarOperacion");
		
		super.limpiarCampos();
		if( !isUsuarioConFacultadFirmar()) {
    		String mensaje = "Usted No tiene habilitada la facultad de firma digital.";
    		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, mensaje, mensaje));
    		
    		return;
    	}
		
		try {				
			traspaso.getCuentaOrigen().setInstitucion(consultaCatalogosFacade.buscarInstitucionPorIdNoNulaFolio(traspaso.getIdFolioTraspasante()));
			traspaso.getCuentaDestino().setInstitucion(consultaCatalogosFacade.buscarInstitucionPorIdNoNulaFolio(traspaso.getIdFolioReceptor()));
			// Si el usuario debe firmar la operacion, se recuperar la firma.
			// Si no se ha firmado, se procesan los datos y regresa el control a la pantalla para firmar
			if(isUsuarioConFacultadFirmar()) {
				//Valida la vigencia del certificado - 18-Agosto-2014 Pablo Balderas
				validarVigenciaCertificado();
			}
			
			procesarDatos();
		}
		catch(BusinessException businessException) {
    		addMessage(businessException.getMessage(), FacesMessage.SEVERITY_ERROR);
    	}
	}

	public void enviarOperacion2(ActionEvent event) {
		if(isUsuarioConFacultadFirmar()) {
			//Valida la vigencia del certificado - 18-Agosto-2014 Pablo Balderas
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
		logger.debug("procesarDatos");
		boolean valido = validarDTO();
		if (valido) {
			retirarFondosTercero();
		}
	}

	/**
	 * Guardar la operación firmada
	 * @param e
	 */
    public void grabarOperacion(ActionEvent e){
    	logger.debug("grabarOperacion");
    	if(!cdb.validation(hashIso, isoSinFirmar)){
    		throw new InfrastructureException(UtilConstantes.ERROR_ISO_DIFERENTE);
    	}

    	isoFirmado = (new StringBuilder()).append(isoSinFirmar).append(numeroSerie).append("\n").append("{SHA1withRSA}").append(isoFirmado).toString();

    	enviarOperacionABitacora();
    }

	/**
	 * Envia la operacion a bitacora
	 */
	private void enviarOperacionABitacora() {
		logger.debug("enviarOperacionABitacora");
		HashMap<String, String> datosFirma = new HashMap<String, String>();
		//datosAdicionales.put("ordenante", vo.getOrdenante());
        //datosAdicionales.put("beneficiario", vo.getBeneficiario());
        
		// Ticket NN
		datosFirma.put(SeguridadConstants.USR_CREDENCIAL, getTicketSesion());
		datosFirma.put(SeguridadConstants.USUARIO_SESION, obtenerUsuarioSesion().getClaveUsuario());
        datosFirma.put(SeguridadConstants.SERIE, numeroSerie);
        datosFirma.put(SeguridadConstants.ISO_FIRMADO, isoFirmado);
        
        // Guarda operacion en T_OPERACION_EFECTIVO
        movimientosEfectivoService.guardarOperacion(vo, datosFirma);
    	
    	if(!existeErrorEnInvocacion()){
            mensajeUsuario = "La operaci\u00f3n se llev\u00f3 a cabo exitosamente. Folio de la operaci\u00f3n : "
	            + folioAsignado;
			FacesContext.getCurrentInstance().addMessage(null,
	                new FacesMessage(FacesMessage.SEVERITY_INFO, mensajeUsuario, mensajeUsuario));
			limpiarCampos(null);

    	}
	}

	/** Calcular el saldo de efectivo. */
	public void realizarCalculo(ActionEvent event) {

		calculos.setCantidadOperadaLeida(traspaso.getImporteATraspasar() != null ? traspaso.getImporteATraspasar().toString() : "");
		calculos.setCantidadOperada(new BigDecimal(traspaso.getImporteATraspasar() != null ? traspaso.getImporteATraspasar().doubleValue() : 0));
		calculos.setSaldoDisponibleLeido(traspaso.getSaldoDisponible() != null ? traspaso.getSaldoDisponible().toString() : "");
		servicesCapturaOperacionViewHelper.realizarCalculos(calculos);

		/*
		 * Realiza el cálculo del saldo de Efectivo se necesitan:
		 * cantidadOperadaLeida(ImporteATraspasar), saldoDisponibleLeido
		 */
		traspaso.setSaldoDeEfectivo(calculos.getSimulado() != null ? calculos.getSimulado().doubleValue() : 0);
		if(calculos.getSimulado() != null && calculos.getSimulado().doubleValue() < 0) {
			mensajeUsuario = "Va a retirar una cantidad mayor al disponible.";
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							mensajeUsuario, mensajeUsuario));
		}
	}

	/**
	 * Retirar los fondos de la cuenta para terceros
	 *
	 */

	private void retirarFondosTercero() {
		logger.debug("retirarFondosTercero");
		
		boolean encontroError = false;
		RegistraOperacionParams registraOperacionParams = new RegistraOperacionParams();

		// --- AGENTE TRASPASANTE
		registraOperacionParams.setTraspasante(DTOAssembler.crearAgenteVO(traspaso.getCuentaOrigen()));

		// --- AGENTE DEL RECEPTOR
		registraOperacionParams.setReceptor(DTOAssembler.crearAgenteVO(traspaso.getCuentaDestino()));
		registraOperacionParams.setEmision(new EmisionVO());

		// --- SALDO DISPONIBLE
		if (traspaso.getSaldoDisponible() != null) {
			registraOperacionParams.getEmision().setSaldoDisponible(new BigDecimal(traspaso.getSaldoDisponible().doubleValue()));
		}

		// --- CANTIDAD OPERADA
		if (traspaso.getImporteATraspasar() != null) {
			registraOperacionParams.setCantidadOperada(new BigDecimal(traspaso.getImporteATraspasar()));
		} else {
			registraOperacionParams.setCantidadOperada(new BigDecimal(0));
		}

		// --- SALDO DE EFECTIVO
		if (traspaso.getSaldoDeEfectivo() != null) {
			registraOperacionParams.setNetoEfectivo(new BigDecimal(traspaso.getSaldoDeEfectivo()));
		}

		// --- ENCONTRO UN ERROR AL CALCULAR EL SALDO DE EFECTIVO
		if (calculos != null && calculos.getMensajeSimulado() != null && !calculos.getMensajeSimulado().trim().equalsIgnoreCase("")) {
			encontroError = true;
		}

		if(!encontroError) {
			traspaso.setFolioRetiro(null);
			/**@TODO
			 *
			 */
			BigInteger folio = tesoreriaService.businessRulesTraspasarEntreCuentas(registraOperacionParams.getTraspasante(), registraOperacionParams.getReceptor(),
					new BigInteger(String.valueOf(traspaso.getBoveda().getId())), new BigDecimal(traspaso.getImporteATraspasar()));
			if(!existeErrorEnInvocacion()){
				traspaso.setFolioRetiro(folio.intValue());
				folioAsignado = folio.toString();

				vo = new TraspasoEfectivoVO();
				vo.setBeneficiario(traspaso.getCuentaDestino().getInstitucion().getNombreCorto());
				vo.setCuentaBeneficiaria(generaCuentaEfectivo(traspaso.getCuentaDestino().getInstitucion().getClaveTipoInstitucion(),
											traspaso.getCuentaDestino().getInstitucion().getFolioInstitucion(),
											traspaso.getCuentaDestino().getCuenta()));
				vo.setCuentaOrdenante(generaCuentaEfectivo(traspaso.getCuentaOrigen().getInstitucion().getClaveTipoInstitucion(),
										traspaso.getCuentaOrigen().getInstitucion().getFolioInstitucion(),
										traspaso.getCuentaOrigen().getCuenta()));
				vo.setOrdenante(traspaso.getCuentaOrigen().getInstitucion().getNombreCorto());

				vo.setFechaLiquidacion(servicesCapturaOperacionViewHelper.getFechasUtilService().getCurrentDate());
	            vo.setFechaRegistro(servicesCapturaOperacionViewHelper.getFechasUtilService().getCurrentDate());
	            vo.setMonto(new BigDecimal(traspaso.getImporteATraspasar()));
	            vo.setTipoInstruccion(TesoreriaConstantes.TIPO_INSTRUCCION__TRASPASO_EFECTIVO);
	            vo.setReferenciaMensaje(utilService.getFolio(Constantes.SEQ_REFERENCIA_MENSAJE).toString());
	            vo.setReferenciaOperacion(folioAsignado);

	            DivisaDTO divisa = consultaCatalogosFacade.buscarDivisaPorId(traspaso.getValorEn().getId());
	            vo.setDivisa( null != divisa ? divisa.getClaveAlfabetica(): null);//era:  com.indeval.portaldali.middleware.services.enviooperaciones.util.Constantes.DIVISA_MXN

	            BovedaDTO boveda = consultaCatalogosFacade.buscarBovedaPorId(traspaso.getBoveda().getId());
	            vo.setBovedaTraspasante(null != boveda ? boveda.getNombreCorto():null);// era:  com.indeval.portaldali.middleware.services.enviooperaciones.util.Constantes.E_BANXICO
	            vo.setBoveda(null != boveda ? boveda.getNombreCorto():null);// era:  com.indeval.portaldali.middleware.services.enviooperaciones.util.Constantes.E_BANXICO

	            // Si el usuario debe firmar la operacion, se crea el iso
	            if(isUsuarioConFacultadFirmar()) {
		            isoSinFirmar = isoHelper.creaISO(vo, false);
					hashIso = cdb.cipherHash(isoSinFirmar);
	            } else {
	            	enviarOperacionABitacora();
	            }
		    }
		}
	}

	/**
	 * Genera cuenta de efectivo para cuentas CC
	 *
	 * @param id ID de la institucion
	 * @param folio Folio de la institucion
	 * @param cuenta Cuenta del participante
	 * @return cuenta clabe
	 */
    private String generaCuentaEfectivo(String id, String folio, String cuenta) {
    	AgenteVO agente = new AgenteVO();

    	agente.setCuenta(cuenta);
    	agente.setFolio(folio);
    	agente.setId(id);

    	return tesoreriaService.getCuentaClabeEfectivoPorCuentaNombrada(agente);
    }

	/**
	 * Busca una lista de participantes cuyo id-folio comience con la cadena de
	 * criterio recibido
	 *
	 * @param prefijo
	 *            Cadena de criterio id-folio
	 * @return Lista de instituciones recuperadas
	 */
	public List<InstitucionDTO> buscarParticipante(Object prefijo) {
		List<InstitucionDTO> instituciones  = new ArrayList<InstitucionDTO>();

		if (prefijo != null) {
			String prefijoAjustado = String.valueOf(prefijo).trim().replace("*", "%");
			instituciones = consultaCatalogosFacade.getConsultaInstitucionService()
				.buscarInstitucionesPorPrefijo(prefijoAjustado);
		}
		return instituciones;
	}

	/**
	 * Valida que el importe de la operación no sea mayor al neto efectivo
	 *
	 * @param e
	 */
	public String validarParticipante(ActionEvent e) {

		if (traspaso.getIdFolioTraspasante() == null || traspaso.getIdFolioTraspasante().equals("")) {
			mensajeUsuario = "El traspasante es requerido";
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							mensajeUsuario, mensajeUsuario));

		} else if (traspaso.getIdFolioReceptor().equals(traspaso.getIdFolioTraspasante())) {
			mensajeUsuario = "El traspasante no puede ser el receptor";
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							mensajeUsuario, mensajeUsuario));

			traspaso.setIdFolioReceptor("");

		}

		return mensajeUsuario;
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

		if (traspaso.getValorEn() != null){
			bovedas = consultaCatalogosFacade.getSelectItemsBovedasEfectivoPorDivisaTipoInstruccion(traspaso.getValorEn(), tipoInstruccion);
		}
		return bovedas;
	}

	/**
	 * Obtiene el Tipo de instruccion correspondiente
	 * @return TipoInstruccionDTO que corresponde a la operacion
	 */
	public TipoInstruccionDTO getTipoInstruccion(){
		return utilService.obtenerTipoInstruccionPorClaveOperacion(TesoreriaConstantes.TIPO_INSTRUCCION__TRASPASO_EFECTIVO);
	}

	/**
	 * Obtiene el saldo disponible de la boveda elegida
	 * @param e El actionListener que dispara el evento
	 */
	public void obtieneSaldoDisponibleBoveda(ActionEvent e){
		
		if (StringUtils.isNotBlank(traspaso.getIdFolioTraspasante())
				&& traspaso.getBoveda().getId() > 0) {
			Double saldo = consultaCatalogosFacade
					.getSaldoNetoEfectivoPorBovedaDivisa(traspaso
							.getIdFolioTraspasante(), traspaso.getBoveda()
							.getId(), traspaso.getValorEn().getId());
			logger.info("SALDO DISPONIBLE "
					+ ReflectionToStringBuilder.toString(saldo));
			traspaso.setSaldoDisponible(saldo != null ? saldo : 0);
		}
	}
	
	/**
	 * metodo que reinicia los valores de los atributos al cambiar de divisa
	 * 16/10/12
	 */
	public void reiniciaValores(ActionEvent e){
		traspaso.setBoveda(new BovedaDTO());
		traspaso.setSaldoDisponible(0d);
		
	}

	/** métodos getters & setters */


	/**
	 * @return the traspaso
	 */

	public TraspasoEfecCtasControlDTO getTraspaso() {

		if(traspaso.getImporteATraspasar()!=null){

			if(traspaso.getImporteATraspasar()>9999999999999.99)

					{this.traspaso.setImporteATraspasar(null);

					}}

			return traspaso;
	}

	/**
	 * @param traspaso
	 *            the traspaso to set
	 */
	public void setTraspaso(TraspasoEfecCtasControlDTO traspaso) {
		if(this.traspaso!=null){
			if(this.traspaso.getImporteATraspasar()<9999999999999.99)
			this.traspaso = traspaso;
			else
				{this.traspaso.setImporteATraspasar(null);

				}}
	}

	/**
	 * @return the consultaCatalogosFacade
	 */
	public ConsultaCatalogosFacade getConsultaCatalogosFacade() {
		return consultaCatalogosFacade;
	}

	/**
	 * @param consultaCatalogosFacade
	 *            the consultaCatalogosFacade to setFacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							mensajeUsuario, mensajeUsuario));
	 */
	public void setConsultaCatalogosFacade(ConsultaCatalogosFacade consultaCatalogosFacade) {
		this.consultaCatalogosFacade = consultaCatalogosFacade;
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
	 * @return the validadorTraspasoEfect
	 */
	public DTOValidator getValidadorTraspasoEfect() {
		return validadorTraspasoEfect;
	}

	/**
	 * @param validadorTraspasoEfect
	 *            the validadorTraspasoEfect to set
	 */
	public void setValidadorTraspasoEfect(DTOValidator validadorTraspasoEfect) {
		this.validadorTraspasoEfect = validadorTraspasoEfect;
	}

	/**
	 * @return the tesoreriaService
	 */
	public TesoreriaService getTesoreriaService() {
		return tesoreriaService;
	}

	/**
	 * @param tesoreriaService
	 *            the tesoreriaService to set
	 */
	public void setTesoreriaService(TesoreriaService tesoreriaService) {
		this.tesoreriaService = tesoreriaService;
	}

	/**
	 * @return the operacionExitosa
	 */
	public Boolean getOperacionExitosa() {
		return operacionExitosa;
	}

	/**
	 * @param operacionExitosa
	 *            the operacionExitosa to set
	 */
	public void setOperacionExitosa(Boolean operacionExitosa) {
		this.operacionExitosa = operacionExitosa;
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

	public void setMovimientosEfectivoService(MovimientosEfectivoService movimientosEfectivoService) {
		this.movimientosEfectivoService = movimientosEfectivoService;
	}

	
}