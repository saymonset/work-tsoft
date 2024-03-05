/*
 *Copyright (c) 2005-2007 Bursatec. All Rights Reserved 
 */
package com.indeval.portaldali.presentation.controller.tesoreria;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.commons.collections.bidimap.DualHashBidiMap;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;




import com.indeval.portaldali.middleware.dto.BovedaDTO;
import com.indeval.portaldali.middleware.dto.CuentaEfectivoDTO;
import com.indeval.portaldali.middleware.dto.InstitucionDTO;
import com.indeval.portaldali.middleware.dto.TipoCuentaDTO;
import com.indeval.portaldali.middleware.dto.TipoNaturalezaDTO;
import com.indeval.portaldali.middleware.dto.util.DTOAssembler;
import com.indeval.portaldali.middleware.dto.util.ResultadoValidacionDTO;
import com.indeval.portaldali.middleware.services.common.constants.DaliConstants;
import com.indeval.portaldali.middleware.services.common.constants.TipoCuentaConstants;
import com.indeval.portaldali.middleware.services.common.constants.TipoCustodiaConstants;
import com.indeval.portaldali.middleware.services.enviooperaciones.EnvioOperacionesService;
import com.indeval.portaldali.middleware.services.mercadodinero.RegistraOperacionParams;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import com.indeval.portaldali.middleware.services.modelo.InfrastructureException;
import com.indeval.portaldali.middleware.services.tesoreria.TesoreriaService;
import com.indeval.portaldali.middleware.services.util.UtilServices;
import com.indeval.portaldali.middleware.services.util.util.Constantes;
import com.indeval.portaldali.presentation.common.constants.UtilConstantes;
import com.indeval.portaldali.presentation.controller.mercadodinero.capturaoperaciones.CapturaOperacionesController;
import com.indeval.portaldali.presentation.dto.mercadodinero.TraspasoEfecCtasControlDTO;
import com.indeval.portaldali.presentation.helper.CalculoCapturaOperacionViewHelper;
import com.indeval.portaldali.presentation.helper.ServicesCapturaOperacionViewHelper;
import com.indeval.portaldali.presentation.util.ConsultaCatalogosFacade;
import com.indeval.portaldali.presentation.validation.DTOValidator;
import com.indeval.portaldali.vo.CaptOpsConfig;
import com.indeval.protocolofinanciero.api.vo.TraspasoEfectivoFvVO;


/**
 * Controller de la pantalla de Traspasos de Efectivo entre Cuentas de Control
 * 
 * 
 * @version 1.0
 */
public class CallMoneyController extends CapturaOperacionesController {

	/** El DTO con los campos de Traspasos de Efectivo entre Cuentas de Control */
	private TraspasoEfecCtasControlDTO traspaso = new TraspasoEfecCtasControlDTO();
	
	/** La clase que permite el acceso a la consulta de los catálogos utilizados */
	private ConsultaCatalogosFacade consultaCatalogosFacade = null;
	
	/** Servicio para el envio de operaciones. */
	private EnvioOperacionesService envioOperacionesService = null;
	
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
	
	/** ID del tipo de intruccion */
	private static final String TIPO_INSTR_TRASPASO_EFECTVIO = "TREF";
	
	/** VO con los datos a registras sobre la operacion */
//	private TraspasoEfectivoVO vo = null;
	private TraspasoEfectivoFvVO objCallMoney;	
	
	
	/** Servicio para asignar folio apartir de una secuencia */
	private UtilServices utilService;
	
	/**
	 * Esta variable define cuando es entrega o recepcion de efectivo
	 */
	private boolean compra;
	
	
	/**
	 * abono indica que tipo de abono se debe hacer 
	 * si la liquidacion es por dali , por spei  o solo en un regsitro contable
	 */
	private int abono;
	
	/**
	 * liquidacion al vencimiento indica que tipo de abono se debe hacer al vencimiento 
	 * si la liquidacion es por dali , por spei  o solo en un regsitro contable
	 */
	private int liquidacionVencimiento;
	
	
	/**
	 * tasa a la cual es pacatada la operacion 
	 * 
	 */
	private Double tasa;
	
	/**
	 * Plazo al cual es pactada la operacion
	 * 
	 */	
	private Integer plazo;
	
	
	/**
	 * este el el monto que se pagara al vencimiento de la operacion
	 *  
	 */
	private BigDecimal montoVencimiento;
	
	

	/**
	 * Constructor
	 */
	public CallMoneyController(){
		traspaso.setValorEn(MXP);
	}
	/**
	 * Contiene los cálculos de Simulado, Fecha Regreso, Importe y Premio
	 * necesarios para las pantallas de Captura de Operaciones
	 */
	private CalculoCapturaOperacionViewHelper calculos = new CalculoCapturaOperacionViewHelper();

	/**
	 * Inicializa los datos de la página. *
	 * 
	 * @return <code>null</code> este método no retorna nada.
	 */
	public String getInit() {
		plazo = 0;

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
		boveda.setId(Long.valueOf(11));
		traspaso.setBoveda(boveda);
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
	 * Calcula el monto que debe ser pagado en la fecha de vencimiento
	 * de la operacion de callmoney
	 * 
	 * @param event
	 */
	public void calcularMontoVencimiento(ActionEvent event) {
		
		if( plazo != null && plazo > 0 && tasa != null && tasa.doubleValue() > 0
				&& traspaso.getImporteATraspasar() !=null && traspaso.getImporteATraspasar().doubleValue()>0){
			BigDecimal importeOriginal= new BigDecimal(traspaso.getImporteATraspasar().doubleValue());
			BigDecimal plazoBig = new BigDecimal(plazo.intValue());
			BigDecimal tasaBig=  new BigDecimal(tasa.doubleValue());
			importeOriginal = importeOriginal.setScale(2, BigDecimal.ROUND_UP);
			plazoBig = plazoBig.setScale(2);
			tasaBig = tasaBig.setScale(6, BigDecimal.ROUND_UP);
			logger.debug("importeOriginal ["+importeOriginal +"]");
			logger.debug("plazoBig ["+plazoBig +"]");
			logger.debug("tasaBig ["+tasaBig +"]");
			montoVencimiento = importeOriginal.add( importeOriginal.multiply(plazoBig).multiply(tasaBig).divide(BigDecimal.valueOf(36000),2));	
		}	
		logger.debug("montoVencimiento ["+montoVencimiento +"]");
		
	}
	
	
	public void cambioCompra(ActionEvent event) {		
		compra= !compra? Boolean.TRUE:Boolean.FALSE;	
		logger.debug("Marca compra ["+compra+"]");
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
		boveda.setId(Long.valueOf(11));
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
			return resultado.isValido();
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
		if(tasa == null || tasa <= 0 ){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "La tasa debe ser mayor que cero", "La tasa debe ser mayor que cero"));
			resultado.setValido(Boolean.FALSE);
		}		
		if( plazo == null || plazo <= 0 ){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "El plazo debe ser mayor que cero", "El plazo debe ser mayor que cero"));
			resultado.setValido(Boolean.FALSE);
		} else if( plazo > 365 ){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "El plazo no debe ser mayor que 365", "El plazo no debe ser mayor que 365"));
			resultado.setValido(Boolean.FALSE);
		}
		
		return resultado.isValido();
	}

	/**
	 * Limpiar los campos de la página respetando las opciones de la operacion.
	 */
	public void limpiarCampos(ActionEvent event) {
		traspaso = new TraspasoEfecCtasControlDTO();
		plazo=0;
		tasa=null;
		montoVencimiento=null;
		liquidacionVencimiento=0;
		abono=0;		
		traspaso.setValorEn(MXP);
		this.limpiarCampos();
		getInit();
	}

	/**
	 * Listener para el guardar que permite persistir la Operación en la BD
	 * 
	 */
	public void enviarOperacion(ActionEvent event) {
		try {				
			traspaso.getCuentaOrigen().setInstitucion(consultaCatalogosFacade.buscarInstitucionPorIdNoNulaFolio(traspaso.getIdFolioTraspasante()));
			traspaso.getCuentaDestino().setInstitucion(consultaCatalogosFacade.buscarInstitucionPorIdNoNulaFolio(traspaso.getIdFolioReceptor()));
	        // Si el usuario debe firmar la operacion, se recuperar la firma.
	        // Si no se ha firmado, se procesan los datos y regresa el control a la pantalla para firmar
	        if(isUsuarioConFacultadFirmar()) {
				//Valida la vigencia del certificado - 18-Agosto-2014 Pablo Balderas
	        	validarVigenciaCertificado();
		        recuperarCamposFirma();
		        if (!StringUtils.isEmpty(isoFirmado)) {
		            grabarOperacion(event);
		        }
		        else {
		            procesarDatos();
		        }
	        }
	        else {
	        	procesarDatos();
	        }
		}
		catch(BusinessException businessException) {
    		addMessage(businessException.getMessage(), FacesMessage.SEVERITY_ERROR);
    	}
	}

	/**
	 * Valida, Calcula y registra la operacion antes de firmar
	 */
	private void procesarDatos() {
		boolean valido = validarDTO();
		if (valido) {
			prestamoEfectivo();
		}
	}
	
	/**
	 * Guardar la operación firmada
	 * @param e
	 */
    public void grabarOperacion(ActionEvent e){ 
    	if(!cdb.validation(hashIso, isoSinFirmar)){
    		throw new InfrastructureException(UtilConstantes.ERROR_ISO_DIFERENTE);
    	}
    	
    	isoFirmado = (new StringBuilder()).append(isoSinFirmar).append("\n").append(numeroSerie).append("\n").append("{SHA1withRSA}").append(isoFirmado).toString();
    	
    	enviarOperacionABitacora();
    }

	/**
	 * Envia la operacion a bitacora
	 */
	private void enviarOperacionABitacora() {
		HashMap<String, String> datosAdicionales = new HashMap<String, String>();
				
		
		datosAdicionales.put("idTrasp",traspaso.getIdFolioTraspasante().substring(0, 2));
		datosAdicionales.put("folioTrasp",traspaso.getIdFolioTraspasante().substring(2));
		datosAdicionales.put("idRecep",traspaso.getIdFolioReceptor().substring(0, 2));
		datosAdicionales.put("folioRecep",traspaso.getIdFolioReceptor().substring(2));
		
		datosAdicionales.put("liqInicio", objCallMoney.getLiquidacionInicio());
		datosAdicionales.put("liqVencimiento", objCallMoney.getLiquidacionVencimiento());
			
    	
		
    	envioOperacionesService.grabaOperacion(objCallMoney, folioAsignado, false, datosAdicionales, null, isoFirmado,false);
    	
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
		calcularMontoVencimiento(event);
	
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
	
	private void prestamoEfectivo() {

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
			
			DualHashBidiMap tiposDeCargosAbonos = new DualHashBidiMap(); 
									
			tiposDeCargosAbonos.put(0, "D");
			tiposDeCargosAbonos.put(1, "S");
			tiposDeCargosAbonos.put(2, "R");
						
						
			if(!existeErrorEnInvocacion()){
				traspaso.setFolioRetiro(folio.intValue());
				folioAsignado = folio.toString();
				
				objCallMoney = new TraspasoEfectivoFvVO();
				
				objCallMoney.setCuentaIdPrestamista(generaCuentaEfectivo(traspaso.getCuentaOrigen().getInstitucion().getClaveTipoInstitucion(),
						traspaso.getCuentaOrigen().getInstitucion().getFolioInstitucion(),
						traspaso.getCuentaOrigen().getCuenta()));
				objCallMoney.setCuentaIdPrestatario(generaCuentaEfectivo(traspaso.getCuentaDestino().getInstitucion().getClaveTipoInstitucion(),
						traspaso.getCuentaDestino().getInstitucion().getFolioInstitucion(),
						traspaso.getCuentaDestino().getCuenta()));
				
				objCallMoney.setLiquidacionInicio((String)tiposDeCargosAbonos.get(abono));				
				objCallMoney.setLiquidacionVencimiento((String)tiposDeCargosAbonos.get(liquidacionVencimiento));				
								
				objCallMoney.setDivisaInteres("MXN");
		        objCallMoney.setDivisaMonto("MXN");
		        objCallMoney.setBoveda("E-BANXICO");		        
				
		        objCallMoney.setFechaConcertacion(servicesCapturaOperacionViewHelper.getFechasUtilService().getCurrentDate());
		        objCallMoney.setFechaValor(objCallMoney.getFechaConcertacion());
		        
		        //El plazo se suma en dias naturales
		        Calendar calendarVencimeinto=Calendar.getInstance();
		        calendarVencimeinto.add(Calendar.DAY_OF_MONTH, plazo);
		        
		        if(!servicesCapturaOperacionViewHelper.isDiaHabil(calendarVencimeinto.getTime())){
		        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
		        		"La Fecha de Vencimiento deber ser un dia Habil","La Fecha de Vencimiento deber ser un dia Habil"));
		        	return;		        	
		        }		    
		        
		        objCallMoney.setFechaVencimiento(calendarVencimeinto.getTime());		        
		        objCallMoney.setFechaPagoInteres(objCallMoney.getFechaVencimiento());		        
		        
		        objCallMoney.setTasaInteres(Double.valueOf(tasa.toString()));
		        		       
		        objCallMoney.setMontoPrestamo(traspaso.getImporteATraspasar());
		        
		        BigDecimal tasaBig = new BigDecimal(tasa);
		        BigDecimal importe = new BigDecimal(traspaso.getImporteATraspasar().doubleValue()*plazo* tasaBig.divide(BigDecimal.valueOf(36000),6,BigDecimal.ROUND_HALF_UP).doubleValue());
		        objCallMoney.setMontoInteres(importe.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
		        logger.debug("setMontoInteres:"+objCallMoney.getMontoInteres());
		        
		        objCallMoney.setReferenciaMensaje(utilService.getFolio(Constantes.SEQ_REFERENCIA_MENSAJE).toString());
		        objCallMoney.setReferenciaOperacion(folioAsignado);
		        objCallMoney.setReferenciaContratoPrestamo(objCallMoney.getReferenciaOperacion());
	                       

	            // Si el usuario debe firmar la operacion, se crea el iso
	            if(isUsuarioConFacultadFirmar()) {
		            isoSinFirmar = isoHelper.creaISO(objCallMoney, false);
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
	 * Obtiene el saldo disponible de la boveda elegida
	 * @param e El actionListener que dispara el evento 
	 */
	public void obtieneSaldoDisponibleBoveda(ActionEvent e){
		if (StringUtils.isNotBlank(traspaso.getIdFolioTraspasante()) && traspaso.getBoveda().getId() > 0) {
			Double saldo= consultaCatalogosFacade.getSaldoNetoEfectivoPorBoveda(traspaso.getIdFolioTraspasante(), traspaso.getBoveda().getId());
			logger.info("SALDO DISPONIBLE " + ReflectionToStringBuilder.toString(saldo));
			traspaso.setSaldoDisponible(saldo != null ? saldo : 0);
		}
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


	public boolean isCompra() {
		return compra;
	}


	public void setCompra(boolean compra) {
		this.compra = compra;
	}
	

	public int getAbono() {
		return abono;
	}



	public void setAbono(int abono) {
		this.abono = abono;
	}



	public int getLiquidacionVencimiento() {
		return liquidacionVencimiento;
	}



	public void setLiquidacionVencimiento(int liquidacionVencimiento) {
		this.liquidacionVencimiento = liquidacionVencimiento;
	}






	public Integer getPlazo() {
		return plazo;
	}



	public void setPlazo(Integer plazo) {
		this.plazo = plazo;
	}



	public BigDecimal getMontoVencimiento() {
		
		//si es necesario, se corta el monto vencimiento a 2 decimales sin redondear
		if(montoVencimiento!=null
				&& montoVencimiento.toString().indexOf(".") != -1 
				&& (montoVencimiento.toString().substring((montoVencimiento.toString().indexOf("."))+1)).length() > 2){
			montoVencimiento = new BigDecimal(
					montoVencimiento.toString().substring(0,montoVencimiento.toString().indexOf(".")+1)
					+ montoVencimiento.toString().substring((montoVencimiento.toString().indexOf("."))+1).substring(0, 2));
		}
			
		return montoVencimiento;
	}

	public void setMontoVencimiento(BigDecimal montoVencimiento) {
//		Se deja comentado para que no haga el set, no es necesario y 
//		al hacer la conversion a BigDecimal no la hace exacta, coloca presicion no deseable para esta pantalla.
//		this.montoVencimiento = montoVencimiento;
	}

	public Double getTasa() {
		return tasa;
	}

	public void setTasa(Double tasa) {
		this.tasa = tasa;
	}

	


}