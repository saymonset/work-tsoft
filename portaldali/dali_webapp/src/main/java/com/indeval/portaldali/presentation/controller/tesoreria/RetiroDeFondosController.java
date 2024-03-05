/*
 *Copyright (c) 2005-2006 Bursatec. All Rights Reserved 
 */
package com.indeval.portaldali.presentation.controller.tesoreria;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.bursatec.seguridad.dto.UsuarioDTO;
import com.bursatec.seguridad.presentation.constants.SeguridadConstants;
import com.indeval.portaldali.middleware.dto.BovedaDTO;
import com.indeval.portaldali.middleware.dto.CuentaEfectivoDTO;
import com.indeval.portaldali.middleware.dto.CuentaRetiroDTO;
import com.indeval.portaldali.middleware.dto.DivisaDTO;
import com.indeval.portaldali.middleware.dto.CuentaRetiroEfectivoDTO;
import com.indeval.portaldali.middleware.dto.EstadoInstruccionDTO;
import com.indeval.portaldali.middleware.dto.EstadosCuentaDTO;
import com.indeval.portaldali.middleware.dto.InstitucionDTO;
import com.indeval.portaldali.middleware.dto.RetiroEfectivoDTO;
import com.indeval.portaldali.middleware.dto.TipoCuentaDTO;
import com.indeval.portaldali.middleware.dto.TipoNaturalezaDTO;
import com.indeval.portaldali.middleware.dto.criterio.CriterioCuentaEfectivoDTO;
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
import com.indeval.portaldali.middleware.services.modelo.InfrastructureException;
import com.indeval.portaldali.middleware.services.tesoreria.AdmonRetirosEfectivoService;
import com.indeval.portaldali.middleware.services.tesoreria.TesoreriaService;
import com.indeval.portaldali.middleware.services.tesoreria.cuentas.AdministracionCuentasRetiroService;
import com.indeval.portaldali.middleware.services.util.UtilServices;
import com.indeval.portaldali.middleware.services.util.util.Constantes;
import com.indeval.portaldali.presentation.common.constants.TesoreriaConstantes;
import com.indeval.portaldali.presentation.common.constants.UtilConstantes;
import com.indeval.portaldali.presentation.controller.mercadodinero.capturaoperaciones.CapturaOperacionesController;
import com.indeval.portaldali.presentation.controller.mercadodinero.capturaoperaciones.constants.CapturaOperacionesConstants;
import com.indeval.portaldali.presentation.dto.mercadodinero.RetiroFondosDTO;
import com.indeval.portaldali.presentation.helper.ServicesCapturaOperacionViewHelper;
import com.indeval.portaldali.presentation.util.ConsultaCatalogosFacade;
import com.indeval.portaldali.presentation.util.ResultadoInvocacionServicioUtil;
import com.indeval.portaldali.presentation.validation.DTOValidator;
import com.indeval.portaldali.vo.CaptOpsConfig;
import com.indeval.protocolofinanciero.api.vo.RetiroEfectivoVO;

/**
 * 
 * Controller para la pantalla de retiro de fondos.
 * 
 * @author Juan Carlos Huizar Moreno
 */

public class RetiroDeFondosController extends CapturaOperacionesController {
	/** El DTO con los campos de la pantalla Retiro de fondos */
	private RetiroFondosDTO retiro = new RetiroFondosDTO();
	/** La clase que permite el acceso a la consulta de los catálogos utilizados */
	private ConsultaCatalogosFacade consultaCatalogosFacade = null;
	/** Servicio para ejecutar el retiro de fondos */
	private TesoreriaService tesoreriaService = null;
	/** Validador de los campos de la pantalla retiro de fondos. */
	private DTOValidator validadorRetiroDeFondos;
	/** Saldo leido */
	private Double saldoDisponibleObtenido;
	/** Configuracicn de operaciones */
	private CaptOpsConfig configuracion = null;
	/** Si la operación de registro es exitosa */
	private Boolean operacionExitosa = Boolean.FALSE;
	/** Servicio para de Operaciones de Efectivo. */
	private MovimientosEfectivoService movimientosEfectivoService;
    /** Servicio para sacar folios de una secuencia */
    private UtilServices utilService;
    /** Define si es retiro de banca comercial o no*/
    private Boolean esBancaComercial = null;
    /** Validador de los campos de la pantalla retiro de fondos banca comercial. */
	private DTOValidator validadorRetiroBancaComercial;
	/** Service de retiros*/
	private AdmonRetirosEfectivoService admonRetiroEfectivo;
    /** servicio de administracion de cuentas */
    private AdministracionCuentasRetiroService admonCuentasService;
	/** Retiro Dto de BCom*/
	private RetiroEfectivoDTO retiroDto = null;
	/** Control de campos Beneficiario BCom [0] -> idFolioReceptor, [1] -> nombreCuentaBeneficiario */
	private String[] datosAnteriores;
    
    /**
     * Servicio helper para la captura de operaciones
     */
    private ServicesCapturaOperacionViewHelper servicesCapturaOperacionViewHelper = null;
 
    private static String TIPO_INSTRUCCION_RET_FONDOS = "RETE";
    
    private static final String INSTITUCION_INDEVAL = "INDEVAL";
    
    private static final BigDecimal MONTO_MAX_MENSUAL = new BigDecimal("9999999999999");
    
    private static final BigDecimal MONTO_MAX_DIARIO = new BigDecimal("100000000000");
    
    private static final BigDecimal MONTO_MAX_TRANSAC = new BigDecimal("100000000000");
    
    private static final Long MONTO_MOVS_MENSUAL = new Long("99999");
    
    private static final String SEQ_CUENTA_RETIRO_NAL = "C_CUENTA_RETIRO_NAL_SEQ";
    
    private RetiroEfectivoVO vo = null;
    
    /** Bandera para validar cuenta clabe y spei del beneficiario de la institucion **/
    private Boolean disabledBCom;
    
    /** Bandera para validar clave spei de la institucion **/
    private Boolean disabledSpei;
    
    /** Bandera para validar opera siac y clave casfim de la institucion **/
    private Boolean disabledSiac;

    private boolean primeraVez;
    
    
    public RetiroDeFondosController(){
    	retiro.setValorEn(MXP);
    	
    	this.primeraVez = true;
    }

	/**
	 * Indica el tipo de retiro que se realizar.
	 * 
	 * @return <code>true</code> si el retiro de fondos es de tipo SPEI
	 *         institución. <code>false</code> si el retiro de fondos es de
	 *         tipo SIAC terceros.
	 */
	public boolean isRetiroSPEI() {
		return retiro.getTipoRetiro() != null && retiro.getTipoRetiro().equals(CapturaOperacionesConstants.SPEI);
	}

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
	 * Inicializa los datos de la página.
	 * 
	 * @return <code>null</code> este método no retorna nada.
	 */
	public String getInit() {
		
		InstitucionDTO inst = new InstitucionDTO();
		if (retiro.getIdFolioUsuario() == null) {
			retiro.setIdFolioUsuario(getInstitucionActual().getClaveTipoInstitucion() + getInstitucionActual().getFolioInstitucion());
		}
		if (retiro.getIdFolioUsuario() != null) {
			inst = consultaCatalogosFacade.buscarInstitucionPorIdFolio(retiro.getIdFolioUsuario());
			if (inst != null) {
				retiro.getSaldoUsuario().setCuenta(new CuentaEfectivoDTO());
				retiro.getSaldoUsuario().getCuenta().setInstitucion(inst);
			}
		}
		this.setIsoSinFirmar(null);
		
		retiro.setCuentaBeneficiaria("");
		BovedaDTO boveda = new BovedaDTO();
		boveda.setId(new Long(11));
		retiro.getSaldoUsuario().setBoveda(boveda);
		Double saldo = consultaCatalogosFacade.getSaldoNetoEfectivoPorBovedaDivisa(retiro.getIdFolioUsuario(), boveda.getId(), retiro.getValorEn().getId());
		retiro.getSaldoUsuario().setSaldoDisponible(saldo != null ? saldo : new Double(0));

		
		//para bcom... [0] -> idFolioReceptor, [1] -> nombreCuentaBeneficiario
		datosAnteriores = new String[]{null,null};

		Map<String , String> map=FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
	    String bancaComercial=map.get("esBancaComercial");
	    String tipoRetiro=map.get("tipoRetiro");
	    esBancaComercial = esBancaComercial == null? 
	    						(StringUtils.isEmpty(bancaComercial)? false:Boolean.parseBoolean(bancaComercial))
	    						: esBancaComercial;
		retiro.setTipoRetiro(retiro.getTipoRetiro() == null ? 
								(tipoRetiro==null?null:Integer.valueOf(tipoRetiro))
								: retiro.getTipoRetiro());
		if(esBancaComercial){
			retiro.setTipoRetiro(11);
		}
		
		if (retiro.getIdFolioReceptor() != null) {
			inst = consultaCatalogosFacade.buscarInstitucionPorIdFolio(retiro.getIdFolioReceptor());
			if (inst != null) {
				retiro.setInstitucionReceptor(inst);
			}
		}
		
		if(retiro.getInstitucionReceptor()==null){
			inst = new InstitucionDTO();
			inst.setNombreCorto("");
			retiro.setInstitucionReceptor(new InstitucionDTO());
		}
		
		if(retiro.getCuentaBeneficiario()==null){
			CuentaRetiroEfectivoDTO cuenta = new CuentaRetiroEfectivoDTO();
			cuenta.setNombreBeneficiario("");
			retiro.setCuentaBeneficiario(cuenta);
		}
		
		this.disabledBCom = Boolean.FALSE;
		this.disabledSpei = Boolean.FALSE;
		this.disabledSiac = Boolean.FALSE;
		
		logger.debug("getInstitucionActual() :: " + getInstitucionActual()
					+ " :: inst.getNombreCorto() :: " + getInstitucionActual().getNombreCorto() 
					+ " :: inst.getCuentaClabeBeneficiario() :: " + getInstitucionActual().getCuentaClabeBeneficiario()
					+ " :: inst.getClaveSpei() :: " + getInstitucionActual().getClaveSpei());
		
		/** VALIDA BOCM **/
		if(!getInstitucionActual().getNombreCorto().trim().equals(INSTITUCION_INDEVAL)){
			// Valido que la institucion tenga clave spei y cuenta clabe
			if(getInstitucionActual().getCuentaClabeBeneficiario() == null ||  StringUtils.isBlank(getInstitucionActual().getCuentaClabeBeneficiario())
					|| getInstitucionActual().getClaveSpeiBeneficiario() == null || StringUtils.isBlank(getInstitucionActual().getClaveSpeiBeneficiario())
					|| getInstitucionActual().getCuentaClabe() == null || StringUtils.isBlank(getInstitucionActual().getCuentaClabe())){
				this.disabledBCom = Boolean.TRUE;
			}
		}
		
		/** VALIDA SPEI **/
		if(!getInstitucionActual().getNombreCorto().trim().equals(INSTITUCION_INDEVAL)){
			// Valido que la institucion tenga clave spei y cuenta clabe
			if(getInstitucionActual().getClaveSpei() == null || StringUtils.isBlank(getInstitucionActual().getClaveSpei())){
				this.disabledSpei = Boolean.TRUE;
			}
		}
		
		/** VALIDA SIAC **/
		if(!getInstitucionActual().getNombreCorto().trim().equals(INSTITUCION_INDEVAL)){
			// Valido que la institucion tenga clave spei y cuenta clabe
			if(getInstitucionActual().getClaveCasfim() == null ||  StringUtils.isBlank(getInstitucionActual().getClaveCasfim()) 
					|| getInstitucionActual().isOperaSiac() == null || !getInstitucionActual().isOperaSiac()){
				this.disabledSiac = Boolean.TRUE;
			}
		}

		datosAnteriores[0] = retiro.getIdFolioReceptor();
		
		return null;
	}

	/**
	 * Realiza la operacion de retiro de fondos por medio del servicio
	 * TesoreriaService.
	 * 
	 * @param ValueChangeEvent
	 *            evento que ejecuta el componente de retiro
	 */
	public void retirarFondos(ActionEvent event) {
		retiro.getSaldoUsuario().getCuenta().setInstitucion(consultaCatalogosFacade.buscarInstitucionPorIdNoNulaFolio(retiro.getIdFolioUsuario()));
	 	operacionExitosa = Boolean.TRUE;
  
    	 boolean valido = validarDTO();
         if (valido) {
        	 try {
        		 registrarOperacion();
        	 } catch (BusinessException bex) {
        		 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, bex.getMessage(), bex.getMessage()));
        	 }
         }

	}
	
    /**
     * ActionListener para el boton que permite persistir la operacion capturada por el usuario.
     * 
     * @param event el evento que dispara este ActionListener
     */
    public void enviarOperacion(ActionEvent e) {
    	super.limpiarCampos();
    	boolean isUsuarioConFacultadFirmar = isUsuarioConFacultadFirmar();
    	
    	if( !isUsuarioConFacultadFirmar) {
    		String mensaje = "Usted No tiene habilitada la facultad de firma digital.";
    		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, mensaje, mensaje));
    		
    		return;
    	}
    	
		try {				
	    	retiro.getSaldoUsuario().getCuenta().setInstitucion(consultaCatalogosFacade.buscarInstitucionPorIdNoNulaFolio(retiro.getIdFolioUsuario()));
	        // Si el usuario debe firmar la operacion, se recuperar la firma.
	        // Si no se ha firmado, se procesan los datos y regresa el control a la pantalla para firmar
	        if(isUsuarioConFacultadFirmar) {
				//Valida la vigencia del certificado - 18-Agosto-2014 Pablo Balderas
	        	validarVigenciaCertificado();
		        procesarDatos();
	        }
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
		boolean valido = validarDTO();
		if (valido) {
			registrarOperacion();
		}
	}
	
	/** Realiza la operacion de registrar la operacion. */
	public void registrarOperacion() {
		RegistraOperacionParams registraOperacionParams = new RegistraOperacionParams();

		// --- USUARIO QUE HACE EL RETIRO DE FONDOS
		registraOperacionParams.setTraspasante(DTOAssembler.crearAgenteVO(retiro.getSaldoUsuario().getCuenta()));
		// --- IMPORTE A RETIRAR
		if (retiro.getImporteRetiro() != null) {
			registraOperacionParams.setCantidadOperada(retiro.getImporteRetiro());
		} else {
			registraOperacionParams.setCantidadOperada(new BigDecimal(0));
		}
		// --- VALOR EN
		if (retiro.getValorEn() != null) {
			registraOperacionParams.setDivisa(retiro.getValorEn().getClaveAlfabetica());
		}
	
		BigInteger folioInteger = null;
		folioInteger = tesoreriaService.businessRulesRetirarFondos(registraOperacionParams.getTraspasante(), retiro.getTipoRetiro().toString(),
				new BigInteger(String.valueOf(retiro.getSaldoUsuario().getBoveda().getId())), retiro.getImporteRetiro());
		
		if (!ResultadoInvocacionServicioUtil.existeError()) {
			folioAsignado = folioInteger.toString();
			retiro.setFolioOperacion(new Integer(folioAsignado));
			vo = new RetiroEfectivoVO();
			vo.setFechaRegistro(servicesCapturaOperacionViewHelper.getFechasUtilService().getCurrentDate());
			vo.setTipoInstruccion(TIPO_INSTRUCCION_RET_FONDOS);
			vo.setMonto(retiro.getImporteRetiro());
			vo.setFechaLiquidacion(servicesCapturaOperacionViewHelper.getFechasUtilService().getCurrentDate());
			vo.setOrdenante(retiro.getSaldoUsuario().getCuenta().getInstitucion().getNombreCorto());
			vo.setCuentaOrdenante(generaCuentaEfectivo(retiro.getIdFolioUsuario()));
			vo.setBeneficiario(retiro.getSaldoUsuario().getCuenta().getInstitucion().getNombreCorto());
			vo.setCuentaBeneficiaria(vo.getCuentaOrdenante());
			vo.setReferenciaMensaje(utilService.getFolio(Constantes.SEQ_REFERENCIA_MENSAJE).toString());
			vo.setReferenciaOperacion(this.folioAsignado);			
			vo.setReferenciaNumericaSPEI(utilService.getFolio(Constantes.SEQ_REFERENCIA_NUMERICA_SPEI).toString());
			vo.setConceptoPago(retiro.getTipoRetiro() == Constantes.ID_RETIRO_SIAC ? Constantes.DESC_RETIRO_SIAC : Constantes.DESC_RETIRO_SPEI);
//			vo.setReferenciaNumericaSPEI(retiro.getReferenciaNumerica());
//			vo.setConceptoPago(retiro.getConceptoPago());
			vo.setTipoRetiro(vo.getConceptoPago());
			 
			vo.setBoveda(com.indeval.portaldali.middleware.services.enviooperaciones.util.Constantes.E_BANXICO);
			vo.setBovedaTraspasante(com.indeval.portaldali.middleware.services.enviooperaciones.util.Constantes.E_BANXICO);
			
			if (retiro.getValorEn() != null && retiro.getValorEn().getId() > 0) {
				DivisaDTO divisa = consultaCatalogosFacade.buscarDivisaPorId(retiro.getValorEn().getId());
				if(divisa != null) {
					vo.setDivisa(divisa.getClaveAlfabetica());
				} else {
					vo.setDivisa(com.indeval.portaldali.middleware.services.enviooperaciones.util.Constantes.DIVISA_MXN);
				}
			} else {
				vo.setDivisa(com.indeval.portaldali.middleware.services.enviooperaciones.util.Constantes.DIVISA_MXN);
			}
			
			

            // Si el usuario debe firmar la operacion, se crea el iso
            if(isUsuarioConFacultadFirmar()) {
				isoSinFirmar = isoHelper.creaISO(vo, false);
				logger.debug("ISO_ANTES_DE_FIRMAR :*******************"+isoSinFirmar+"*************************************");
				hashIso = cdb.cipherHash(isoSinFirmar);
            } else {
            	enviarOperacionABitacora();
            }
		}
	}
	
	/**
	 * Genera cuenta de efectivo para cuentas CC
	 * 
	 * @param idFolioEfectivo
	 * @return
	 * @throws BusinessException
	 * 
	 */
	private String generaCuentaEfectivo(String idFolioEfectivo) {
		CuentaEfectivoDTO cnt = buscarParticipanteEfectivo(idFolioEfectivo);

		if (StringUtils.isBlank(idFolioEfectivo)) {
			throw new BusinessException(
					"El id y folio de la cuenta no puede ser nulo");
		}

		AgenteVO agente = new AgenteVO();
    	
    	agente.setCuenta(cnt != null ? cnt.getCuenta() : TesoreriaConstantes.CTA_EFE_DALI_CTA_EFECTIVO);
    	agente.setId(idFolioEfectivo.substring(0,2));
    	agente.setFolio(idFolioEfectivo.substring(2,5));
    	
    	return tesoreriaService.getCuentaClabeEfectivoPorCuentaNombrada(agente);

	}

	/**
	 * Guarda la operacion firmada
	 * 
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
		//HashMap<String, String> datosAdicionales = new HashMap<String, String>();
		//datosAdicionales.put("ordenante", vo.getOrdenante());
        //datosAdicionales.put("beneficiario", vo.getBeneficiario());            
        //datosAdicionales.put("tipoRetiro", vo.getConceptoPago());
		//datosAdicionales.put("referenciaNumericaSPEI", vo.getReferenciaNumericaSPEI());
		//datosAdicionales.put(SeguridadConstants.USR_CREDENCIAL, (String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(SeguridadConstants.TICKET_SESION));		
    	
		Map<String, String> datosFirma = new HashMap<>();
		datosFirma.put(SeguridadConstants.USR_CREDENCIAL, getTicketSesion());
        datosFirma.put(SeguridadConstants.USUARIO_SESION, obtenerUsuarioSesion().getClaveUsuario());
        datosFirma.put(SeguridadConstants.SERIE, numeroSerie);
        datosFirma.put(SeguridadConstants.ISO_FIRMADO, isoFirmado);
        
        // Guarda operacion en T_OPERACION_EFECTIVO
        movimientosEfectivoService.guardarOperacion(vo, datosFirma);
        
    	if(!existeErrorEnInvocacion()){
                mensajeUsuario = "La operaci\u00f3n se llev\u00f3 a cabo exitosamente. Folio de la operaci\u00f3n : " + folioAsignado;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, mensajeUsuario, mensajeUsuario));
            retiro = new RetiroFondosDTO();
            this.limpiar(null);
    	}
	}

	/**
	 * Ejecuta las validaciones de los DTOs de captura de acuerdo al tipo de
	 * operacion a realizar
	 * 
	 * @return
	 */
	private boolean validarDTO() {
		ResultadoValidacionDTO resultado = null;
		resultado = esBancaComercial? validadorRetiroBancaComercial.validarDTO(retiro):validadorRetiroDeFondos.validarDTO(retiro);
		if (!resultado.isValido()) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, resultado.getMensaje(), resultado.getMensaje()));
		}
		return resultado.isValido();
	}

	/**
	 * Calcula el saldo con el que quedar la cuenta con el importe del retiro
	 * capturado.
	 * 
	 * @param ValueChangeEvent
	 *            evento que ejecuta el componente de retiro
	 */
	public void calculaSaldoActual(ActionEvent event) {
		if(retiro.getSaldoUsuario().getSaldoDisponible() == null) {
			retiro.getSaldoUsuario().setSaldoDisponible(new Double(0));
		}
		if (retiro.getImporteRetiro()!=null){
			retiro.setSaldoActual(retiro.getSaldoUsuario().getSaldoDisponible() - retiro.getImporteRetiro().doubleValue());
			if(retiro.getSaldoActual()<0){
				
				   
				 mensajeUsuario = "Va a Retirar una Cantidad Mayor al Disponible";
		            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, mensajeUsuario, mensajeUsuario));
				
				
			}
		}
		
	}

	/**
	 * Limpia los datos en la pantalla
	 * 
	 * @param ValueChangeEvent
	 *            evento que dispara la accion
	 */
	public void limpiar(ActionEvent event) {
		retiro = new RetiroFondosDTO();
		retiro.setValorEn(MXP);
		retiroDto = null;
		getInit();
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
	 * Busca la institucion apartir del ID-Folio
	 * 
	 * @param event
	 */
	public void buscarInstitucionParticipante(ActionEvent event) {
		InstitucionDTO institucion = null;
		if(StringUtils.isNotBlank(retiro.getIdFolioUsuario())) {
			institucion = consultaCatalogosFacade.buscarInstitucionPorIdFolio(retiro.getIdFolioUsuario());
			if(institucion != null) {
				retiro.getSaldoUsuario().getCuenta().setInstitucion(institucion);
				retiro.setInstitucionReceptor(institucion);
				//si se capturo un IdFolioReceptor diferente, los campos se limpian
				if(!StringUtils.isBlank(datosAnteriores[0]) && !datosAnteriores[0].equals(retiro.getIdFolioReceptor())){
					retiro.setFolioCuentaPorTraspasante(null);
					retiro.setCuentaBeneficiaria("");
					retiro.setNombreBeneficiario("");
					CuentaRetiroEfectivoDTO cuenta = new CuentaRetiroEfectivoDTO();
					cuenta.setNombreBeneficiario("");
					cuenta.setCuentaBeneficiario("");
					retiro.setCuentaBeneficiario(cuenta);
					//se pone el nuevo valor como anterior
					datosAnteriores[0] = retiro.getIdFolioReceptor();
				}
				else{
					buscarCuentaBeneficiarioNal(event);
				}
				buscarCuentaBeneficiarioNalPorFolio(event);
				Double saldo = consultaCatalogosFacade.getSaldoNetoEfectivoPorBovedaDivisa(retiro.getIdFolioUsuario(), retiro.getSaldoUsuario().getBoveda().getId(), retiro.getValorEn().getId());
				retiro.getSaldoUsuario().setSaldoDisponible(saldo != null ? saldo : new Double(0));
			}
			else {
				addMessageFromProperties("msgInstitucionNoExiste", new Object[]{retiro.getIdFolioUsuario()}, FacesMessage.SEVERITY_ERROR);
			}
		}
		else {
    		addMessageFromProperties("msgErrorCampoObligatorio", new Object[]{"Traspasante"}, FacesMessage.SEVERITY_ERROR);
		}
		
		/*
		retiro.getSaldoUsuario().getCuenta().setInstitucion(new InstitucionDTO());
		if(retiro.getIdFolioUsuario() != null) {
			InstitucionDTO i = consultaCatalogosFacade.buscarInstitucionPorIdFolio(retiro.getIdFolioUsuario());
			if(i !=  null) {
				retiro.getSaldoUsuario().getCuenta().setInstitucion(i);
			}
		}
		
		retiro.setInstitucionReceptor(new InstitucionDTO());
		if (retiro.getIdFolioReceptor() != null) {
			InstitucionDTO i = consultaCatalogosFacade.buscarInstitucionPorIdFolio(retiro.getIdFolioReceptor());
			if (i != null) {
				retiro.setInstitucionReceptor(i);
			}

			//si se capturo un IdFolioReceptor diferente, los campos se limpian
			if(!StringUtils.isBlank(datosAnteriores[0]) && !datosAnteriores[0].equals(retiro.getIdFolioReceptor())){
				retiro.setFolioCuentaPorTraspasante(null);
				retiro.setCuentaBeneficiaria("");
				retiro.setNombreBeneficiario("");
				CuentaRetiroEfectivoDTO cuenta = new CuentaRetiroEfectivoDTO();
				cuenta.setNombreBeneficiario("");
				cuenta.setCuentaBeneficiario("");
				retiro.setCuentaBeneficiario(cuenta);
				//se pone el nuevo valor como anterior
				datosAnteriores[0] = retiro.getIdFolioReceptor();
			}else{
				buscarCuentaBeneficiarioNal(event);
			}
		}
		
		buscarCuentaBeneficiarioNalPorFolio(event);
		
		Double saldo = consultaCatalogosFacade.getSaldoNetoEfectivoPorBovedaDivisa(retiro.getIdFolioUsuario(), retiro.getSaldoUsuario().getBoveda().getId(), retiro.getValorEn().getId());
		retiro.getSaldoUsuario().setSaldoDisponible(saldo != null ? saldo : new Double(0));
		
		*/
	}
	
	/**
	 * Metodo que atiende las solicitudes del catalogo de cuentas tipo Efectivo
	 * Nombrada.
	 * 
	 * @param prefijo
	 *            Criterio de busqueda
	 * @return Lista con las cuentas
	 */
	private CuentaEfectivoDTO buscarParticipanteEfectivo(String idFolioPariticipante) {
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
		return resultado;
	}
	/**
	 * Obtiene el saldo disponible de la boveda elegida
	 * @param e El actionListener que dispara el evento 
	 */
	public void obtieneSaldoDisponibleBoveda(ActionEvent e){
		if (retiro.getIdFolioUsuario() != null && retiro.getSaldoUsuario().getBoveda().getId()> 0) {
			Double saldo = consultaCatalogosFacade.getSaldoNetoEfectivoPorBoveda(retiro.getIdFolioUsuario(),retiro.getSaldoUsuario().getBoveda().getId());
			retiro.getSaldoUsuario().setSaldoDisponible(saldo != null ? saldo : new Double(0));			
		}
		
	}
	
	/**
	 * @return the retiro
	 */
	public RetiroFondosDTO getRetiro() {
		if(retiro.getImporteRetiro()!=null){
			
		if(retiro.getImporteRetiro().doubleValue()>9999999999999.99)
		
				{this.retiro.setImporteRetiro(null);
				
				}}
				
		return retiro;
	}

	/**
	 * @param retiro
	 *            the retiro to set
	 */
	public void setRetiro(RetiroFondosDTO retiro) {
		if(this.retiro!=null){
		if(this.retiro.getImporteRetiro().doubleValue()<9999999999999.99)
		this.retiro = retiro;
		else 
			{this.retiro.setImporteRetiro(null);
			
			}}
			}
	
	/**
	 * Busca una lista de cuentas cuyo numero de cuenta comience con la cadena de
	 * criterio recibido
	 * @param prefijo Cadena de criterio numero de cuenta
	 * @return Lista de cuentas recuperadas
	 */
	public List<CuentaRetiroEfectivoDTO> buscarCuentaNal(Object prefijo) {
		List<CuentaRetiroEfectivoDTO> cuentas  = new ArrayList<CuentaRetiroEfectivoDTO>();
		if (prefijo != null
				&& retiro != null 
				&& retiro.getIdFolioUsuario() != null && !retiro.getIdFolioUsuario().equals("")) { 
//				&& retiro.getIdFolioReceptor() != null && !retiro.getIdFolioReceptor().equals("")) {
			String prefijoAjustado = String.valueOf(prefijo).trim().replace("*", "");
			CriterioCuentaEfectivoDTO criterio = new CriterioCuentaEfectivoDTO();
			criterio.setNombreBeneficiario(prefijoAjustado.toUpperCase());
			criterio.setValorEn(retiro.getValorEn());
			InstitucionDTO i = consultaCatalogosFacade.buscarInstitucionPorIdFolio(retiro.getIdFolioUsuario());
			criterio.setInstitucion(i !=  null? i:null);
//			InstitucionDTO ib = consultaCatalogosFacade.buscarInstitucionPorIdFolio(retiro.getIdFolioReceptor());
//			criterio.setInstitucionBeneficiario(ib);
			EstadoInstruccionDTO estado = consultaCatalogosFacade.buscarEstadoInstruccionPorClave("LA");
			criterio.setEstadoCuenta(estado==null||estado.getIdEstadoInstruccion()==0? null : (""+estado.getIdEstadoInstruccion()) );
			
			cuentas = admonRetiroEfectivo.buscarCuentasPorPrefijo(criterio, true);
		}
		return cuentas;
	}
	
	/**
	 * Busca la cuenta apartir del numero de cuenta
	 * @param event 
	 */
	public void buscarCuentaBeneficiarioNal(ActionEvent event) {
		CuentaRetiroEfectivoDTO cuenta = null;
		
		if(retiro != null 
				&& !StringUtils.isEmpty(retiro.getNombreBeneficiario())
				&& retiro.getIdFolioUsuario() != null && !retiro.getIdFolioUsuario().equals("")) {
//				&& retiro.getIdFolioReceptor() != null && !retiro.getIdFolioReceptor().equals("")) {
			CriterioCuentaEfectivoDTO criterio = new CriterioCuentaEfectivoDTO();
			criterio.setValorEn(retiro.getValorEn());
			InstitucionDTO i = consultaCatalogosFacade.buscarInstitucionPorIdFolio(retiro.getIdFolioUsuario());
			criterio.setInstitucion(i !=  null? i:null);
//			InstitucionDTO ib = consultaCatalogosFacade.buscarInstitucionPorIdFolio(retiro.getIdFolioReceptor());
//			criterio.setInstitucionBeneficiario(ib);
			retiro.setCuentaBeneficiaria(
					StringUtils.isEmpty(retiro.getCuentaBeneficiaria())? 
							(retiro.getNombreBeneficiario().indexOf("*") == -1 ?
									retiro.getCuentaBeneficiaria()
									:retiro.getNombreBeneficiario().substring(0,retiro.getNombreBeneficiario().indexOf("*")))
							:retiro.getCuentaBeneficiaria());
			criterio.setFolioCuenta(retiro.getCuentaBeneficiaria());
			retiro.setNombreBeneficiario(
					retiro.getNombreBeneficiario().indexOf("*") == -1 ?
							retiro.getNombreBeneficiario()
							:retiro.getNombreBeneficiario().substring(retiro.getNombreBeneficiario().indexOf("*")+1));
			criterio.setNombreBeneficiario(retiro.getNombreBeneficiario().toUpperCase());
			EstadoInstruccionDTO estado = consultaCatalogosFacade.buscarEstadoInstruccionPorClave("LA");
			criterio.setEstadoCuenta(estado==null||estado.getIdEstadoInstruccion()==0? null : (""+estado.getIdEstadoInstruccion()) );
			criterio.setFolioCuentaPorTraspasante(null);
			
			cuenta = admonRetiroEfectivo.buscarCuentaPorCriterio(criterio,true);
			retiro.setCuentaBeneficiario(cuenta != null?cuenta:null);
			retiro.setCuentaBeneficiaria(cuenta != null?""+cuenta.getIdCuentaRetiroNal():null);
			retiro.setFolioCuentaPorTraspasante(cuenta != null? ""+cuenta.getIdCuentaPorInstitucion():null);
			retiro.setInstitucionReceptor(cuenta != null? cuenta.getInstitucionBeneficiario():null);
			retiro.setIdFolioReceptor(cuenta != null? ""+cuenta.getInstitucionBeneficiario().getClaveTipoInstitucion()+cuenta.getInstitucionBeneficiario().getFolioInstitucion():null);
		}
		
		if(cuenta==null){
			cuenta = new CuentaRetiroEfectivoDTO();
			cuenta.setNombreBeneficiario("");
			cuenta.setCuentaBeneficiario("");
			retiro.setCuentaBeneficiario(cuenta);
			retiro.setCuentaBeneficiaria("");
			retiro.setFolioCuentaPorTraspasante(null);
			retiro.setIdFolioReceptor("");
			retiro.setInstitucionReceptor(new InstitucionDTO());
		}
		
	}
	
	/**
	 * Busca la cuenta a partir del folio
	 * @param event 
	 */
	public void buscarCuentaBeneficiarioNalPorFolio(ActionEvent event) {
		CuentaRetiroEfectivoDTO cuenta = null;
		
		if(retiro != null 
				&& !StringUtils.isBlank(retiro.getFolioCuentaPorTraspasante())
				&& !StringUtils.isBlank(retiro.getFolioCuentaPorTraspasante().trim())
//				&& !retiro.getFolioCuentaPorTraspasante().equals("*")
				&& retiro.getIdFolioUsuario() != null && !retiro.getIdFolioUsuario().equals("")) {
			CriterioCuentaEfectivoDTO criterio = new CriterioCuentaEfectivoDTO();
			criterio.setValorEn(retiro.getValorEn());
			InstitucionDTO i = consultaCatalogosFacade.buscarInstitucionPorIdFolio(retiro.getIdFolioUsuario());
			criterio.setInstitucion(i !=  null? i:null);
			
			logger.debug("1retiro.getFolioCuentaPorTraspasante():"+retiro.getFolioCuentaPorTraspasante().trim());
			logger.debug("1retiro.getCuentaBeneficiaria():"+retiro.getFolioCuentaPorTraspasante().trim());
			
			retiro.setCuentaBeneficiaria(
					StringUtils.isEmpty(retiro.getCuentaBeneficiaria())? 
							(retiro.getFolioCuentaPorTraspasante().indexOf("*") == -1 ?
									retiro.getCuentaBeneficiaria()
									:retiro.getFolioCuentaPorTraspasante().substring(0,retiro.getFolioCuentaPorTraspasante().indexOf("*")))
							:retiro.getCuentaBeneficiaria());
			
			logger.debug("2retiro.getFolioCuentaPorTraspasante():"+retiro.getFolioCuentaPorTraspasante().trim());
			logger.debug("2retiro.getCuentaBeneficiaria():"+retiro.getFolioCuentaPorTraspasante().trim());
			
			criterio.setFolioCuenta(retiro.getCuentaBeneficiaria());
//			criterio.setFolioCuenta(i.getCuentaClabeBeneficiario());
			
			logger.debug("3retiro.getFolioCuentaPorTraspasante():"+retiro.getFolioCuentaPorTraspasante().trim());
			logger.debug("3retiro.getCuentaBeneficiaria():"+retiro.getFolioCuentaPorTraspasante().trim());
			
			retiro.setFolioCuentaPorTraspasante(
					retiro.getFolioCuentaPorTraspasante().indexOf("*") == -1 ?
							retiro.getFolioCuentaPorTraspasante()
							:retiro.getFolioCuentaPorTraspasante().substring(retiro.getFolioCuentaPorTraspasante().indexOf("*")+1));
			
			logger.debug("4retiro.getFolioCuentaPorTraspasante():"+retiro.getFolioCuentaPorTraspasante().trim());
			
			criterio.setFolioCuentaPorTraspasante(retiro.getFolioCuentaPorTraspasante().toString());
//			criterio.setFolioCuentaPorTraspasante(getInstitucionActual().getFolioInstitucion());
			EstadoInstruccionDTO estado = consultaCatalogosFacade.buscarEstadoInstruccionPorClave("LA");
			criterio.setEstadoCuenta(estado==null||estado.getIdEstadoInstruccion()==0? null : (""+estado.getIdEstadoInstruccion()) );
			
			
			cuenta = admonRetiroEfectivo.buscarCuentaPorCriterio(criterio,true);
			
			retiro.setCuentaBeneficiario(cuenta !=  null?cuenta:null);
			retiro.setCuentaBeneficiaria(cuenta !=  null?""+cuenta.getIdCuentaRetiroNal():"");
			retiro.setNombreBeneficiario(cuenta != null?""+cuenta.getNombreBeneficiario():null);
			retiro.setInstitucionReceptor(cuenta != null?cuenta.getInstitucionBeneficiario():null);
			retiro.setIdFolioReceptor(cuenta != null && cuenta.getInstitucionBeneficiario() != null? 
					cuenta.getInstitucionBeneficiario().getClaveTipoInstitucion() + cuenta.getInstitucionBeneficiario().getFolioInstitucion()
					:"");
			
//			datosAnteriores[0] = retiro.getIdFolioReceptor();
		}
		
		if(cuenta==null){
			retiro.setCuentaBeneficiario(new CuentaRetiroEfectivoDTO());
			retiro.getCuentaBeneficiario().setNombreBeneficiario("");
			retiro.getCuentaBeneficiario().setCuentaBeneficiario("");
			retiro.setCuentaBeneficiaria("");
			retiro.setInstitucionReceptor(new InstitucionDTO());
			retiro.getInstitucionReceptor().setNombreCorto("");
			retiro.setIdFolioReceptor("");
//			datosAnteriores[0] = retiro.getIdFolioReceptor();
			retiro.setNombreBeneficiario("");
		}
		
	}
	
	/**
	 * Busca la cuenta a partir del folio
	 * @param event 
	 */
	public List<CuentaRetiroEfectivoDTO> buscarCuentasNalPorFolio(Object prefijo) {		
		List<CuentaRetiroEfectivoDTO> cuentas  = new ArrayList<CuentaRetiroEfectivoDTO>();
		
		if (prefijo != null
				&& retiro != null 
				&& retiro.getIdFolioUsuario() != null && !retiro.getIdFolioUsuario().equals("")) {
			String prefijoAjustado = String.valueOf(prefijo).trim().replace("*", "");
			CriterioCuentaEfectivoDTO criterio = new CriterioCuentaEfectivoDTO();
//			criterio.setNombreBeneficiario(prefijoAjustado.toUpperCase());
			criterio.setValorEn(retiro.getValorEn());
			InstitucionDTO i = consultaCatalogosFacade.buscarInstitucionPorIdFolio(retiro.getIdFolioUsuario());
			criterio.setInstitucion(i !=  null? i:null);
			criterio.setFolioCuentaPorTraspasante(StringUtils.isBlank(prefijoAjustado)?null:prefijoAjustado);
			EstadoInstruccionDTO estado = consultaCatalogosFacade.buscarEstadoInstruccionPorClave("LA");
			criterio.setEstadoCuenta(estado==null||estado.getIdEstadoInstruccion()==0? null : (""+estado.getIdEstadoInstruccion()) );
			
			cuentas = admonRetiroEfectivo.buscarCuentasPorPrefijo(criterio, true);
		}
		
		return cuentas;		
		
	}	
	
	
	/**
	 * Guarda en la base de datos un retiro de banca comercial
	 * @param e objeto tipo ActionEvent
	 */
	public void guardarRetiroBancaComercial(ActionEvent e) {
		logger.debug("en guardarRetiroBancaComercial...");
		
		super.limpiarCampos();
		
		if( !isUsuarioConFacultadFirmar()) {
    		String mensaje = "Usted No tiene habilitada la facultad de firma digital.";
    		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, mensaje, mensaje));
    		
    		return;
    	}
		
		esBancaComercial = true;
		
        /** Valido que la institucion tenga cuenta y clave spei beneficiario **/
		InstitucionDTO inst = new InstitucionDTO();
		if (retiro.getIdFolioUsuario() == null) {
			retiro.setIdFolioUsuario(getInstitucionActual().getClaveTipoInstitucion() + getInstitucionActual().getFolioInstitucion());
		}
		if (retiro.getIdFolioUsuario() != null) {
			inst = consultaCatalogosFacade.buscarInstitucionPorIdFolio(retiro.getIdFolioUsuario());
		}
		
		if(inst.getCuentaClabeBeneficiario() == null || StringUtils.isBlank(inst.getCuentaClabeBeneficiario())){
			String mensajeBComCuenta = "La institucion no cuenta con una cuenta clabe beneficiario";
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, mensajeBComCuenta, mensajeBComCuenta));			
		}
		
		if(inst.getClaveSpeiBeneficiario() == null || StringUtils.isBlank(inst.getClaveSpeiBeneficiario())){
			String mensajeBComClave = "La institucion no cuenta con una clave spei beneficiario";
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, mensajeBComClave, mensajeBComClave));
		}
		
		/** FIN **/
		
		try{
			retiroDto = generaObjetoRetiroEfectivoDTO();
			retiroDto.getIdInstReceptor().setCuentaClabeBeneficiario(inst.getCuentaClabeBeneficiario());
			
			retiro.setInstitucionReceptor(retiroDto.getIdInstReceptor());
			retiro.setNombreBeneficiario(retiroDto.getIdInstReceptor().getNombreCorto());
			
			CuentaRetiroEfectivoDTO cuentaBeneficiario = new CuentaRetiroEfectivoDTO(); 
			cuentaBeneficiario.setNombreBeneficiario(retiroDto.getIdInstReceptor().getNombreCorto());
			cuentaBeneficiario.setCuentaBeneficiario(inst.getCuentaClabeBeneficiario());
			retiro.setCuentaBeneficiaria(inst.getCuentaClabeBeneficiario());
			retiro.setFolioCuentaPorTraspasante(retiroDto.getIdInstReceptor().getFolioInstitucion());
			retiro.setIdFolioReceptor(retiroDto.getIdInstReceptor().getFolioInstitucion());
			
			cuentaBeneficiario.setBoveda(retiroDto.getBoveda());
			cuentaBeneficiario.setDivisa(retiroDto.getDivisa());
			cuentaBeneficiario.setInstitucionBeneficiario(retiroDto.getInstitucion());
			cuentaBeneficiario.setInstitucion(retiroDto.getInstitucion());
			
			retiro.setCuentaBeneficiario(cuentaBeneficiario);
			retiroDto.setCuentaRetiroEfectivo(cuentaBeneficiario);
			retiroDto.setCuentaBeneficiario(inst.getCuentaClabeBeneficiario());
			retiroDto.setNombreBeneficiario(retiroDto.getIdInstReceptor().getNombreCorto());
			
			
			if(isUsuarioConFacultadFirmar()) {
				//Valida la vigencia del certificado - 18-Agosto-2014 Pablo Balderas
				validarVigenciaCertificado();
				
		        if (validarDTO() && admonRetiroEfectivo.businessRulesCrearRetiroBancaComercial(retiroDto)) {
					isoSinFirmar = isoHelper.creaISO(retiroDto, false);
					isoSinFirmar +='\n';
					hashIso = cdb.cipherHash(isoSinFirmar);
		        }
			}
		}
		catch(BusinessException ex) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ex.getMessage()));
			ex.printStackTrace();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
	public void guardarRetiroBancaComercial2(ActionEvent event) {
		if(isUsuarioConFacultadFirmar()) {
			validarVigenciaCertificado();
			recuperarCamposFirma();
			
			if(!StringUtils.isEmpty(isoFirmado)) {
				
				if(!cdb.validation(hashIso, isoSinFirmar)){
		        	throw new InfrastructureException(UtilConstantes.ERROR_ISO_DIFERENTE);
				}
				
				try {
					if (validarDTO() && admonRetiroEfectivo.businessRulesCrearRetiroBancaComercial(retiroDto)) {
						crearRetiroBancaComercial(event, true, retiroDto);
					}else {
						logger.warn("Fall� alguna validaci�n!");
					}
					
				}catch(BusinessException ex) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ex.getMessage()));
				}
			}
		}
		
		
		super.limpiarCampos();
	}
	/**
	 * Genera el retiro para banca comercial
	 */
	private void crearRetiroBancaComercial(ActionEvent e, boolean conFirma, RetiroEfectivoDTO retiroDto){
		logger.debug("en crearRetiroBancaComercial");
		
		//si truena el businessRulesCrearRetiroBancaComercial no cae aqui
		Map<String, String> datosFirma = new HashMap<String, String>(0);
		if(conFirma){
    		isoFirmado = (new StringBuilder()).append(isoSinFirmar).append(numeroSerie).append("\n").append("{SHA1withRSA}").append(isoFirmado).toString();
    		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
    		UsuarioDTO usuario= session!=null?(UsuarioDTO)session.getAttribute(SeguridadConstants.USUARIO_SESION):null;
    		datosFirma.put(SeguridadConstants.ISO_FIRMADO, isoFirmado);
    		datosFirma.put(SeguridadConstants.USUARIO_SESION, usuario.getClaveUsuario());
    		datosFirma.put(SeguridadConstants.SERIE, numeroSerie);
    		datosFirma.put(SeguridadConstants.USR_CREDENCIAL, getTicketSesion());
    	}
    	
    	
    	//si truena el businessRulesCrearRetiroBancaComercial no cae aqui
    	// admonRetiroEfectivo.crearRetiro(retiroDto);
		// Guarda operacion en T_OPERACION_EFECTIVO
		movimientosEfectivoService.guardarOperacion(retiroDto, datosFirma);
        
    	mensajeUsuario = "El registro de la operaci\u00f3n se llev\u00f3 a cabo exitosamente. Folio de la operaci\u00f3n: " + retiroDto.getReferenciaOperacion();
    	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, mensajeUsuario, mensajeUsuario));
    	limpiar(e);

	}
	
	/**
	 * Genera el objeto dto para el retiro de banca comercial
	 */
	private RetiroEfectivoDTO generaObjetoRetiroEfectivoDTO(){
		retiroDto = retiroDto == null? new RetiroEfectivoDTO():retiroDto;
		retiroDto.setBoveda(retiro.getSaldoUsuario().getBoveda());		
		if(retiro.getConceptoPago()!=null){		
			retiroDto.setConceptoPago(retiro.getConceptoPago().trim().toUpperCase());
		}		
//		retiroDto.setCuentaRetiroEfectivo(retiro.getCuentaBeneficiario());
		retiro.setValorEn(consultaCatalogosFacade.buscarDivisaPorId(DaliConstants.ID_DIVISA_MEXICAN_PESO));
		retiroDto.setDivisa(retiro.getValorEn());
		InstitucionDTO institucionReceptor = admonCuentasService.getInstitucionForClaveSpei(getInstitucionActual().getClaveSpeiBeneficiario());
		retiroDto.setIdInstReceptor(institucionReceptor);
		retiroDto.setImporteTraspaso(retiro.getImporteRetiro());
		retiroDto.setInstitucion(retiro.getSaldoUsuario().getCuenta().getInstitucion());
		if(retiro.getReferenciaNumerica()!=null){
			retiroDto.setReferencia(retiro.getReferenciaNumerica().trim());
		}
		retiroDto.setReferenciaMensaje(
				StringUtils.isEmpty(retiroDto.getReferenciaMensaje())? 
						""+utilService.getFolio(Constantes.SEQ_REFERENCIA_MENSAJE)
						:retiroDto.getReferenciaMensaje());
		retiroDto.setReferenciaOperacion(
				StringUtils.isEmpty(retiroDto.getReferenciaOperacion())? 
						""+utilService.getFolio(Constantes.SECUENCIA_FOLIO_CONTROL)
						:retiroDto.getReferenciaOperacion());
		retiroDto.setFechaLiberacion(utilService.getCurrentDate());
		
		return retiroDto;
	}

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

	public CaptOpsConfig getConfiguracion() {
		return configuracion;
	}

	public void setConfiguracion(CaptOpsConfig configuracion) {
		this.configuracion = configuracion;
	}

	/**
	 * @return the validadorRetiroDeFondos
	 */
	public DTOValidator getValidadorRetiroDeFondos() {
		return validadorRetiroDeFondos;
	}

	/**
	 * @param validadorRetiroDeFondos
	 *            the validadorRetiroDeFondos to set
	 */
	public void setValidadorRetiroDeFondos(DTOValidator validadorRetiroDeFondos) {
		this.validadorRetiroDeFondos = validadorRetiroDeFondos;
	}

	/**
	 * @return the saldoDisponibleObtenido
	 */
	public Double getSaldoDisponibleObtenido() {
		return saldoDisponibleObtenido;
	}

	/**
	 * @param saldoDisponibleObtenido
	 *            the saldoDisponibleObtenido to set
	 */
	public void setSaldoDisponibleObtenido(Double saldoDisponibleObtenido) {
		this.saldoDisponibleObtenido = saldoDisponibleObtenido;
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
	 * @return the servicesCapturaOperacionViewHelper
	 */
	public ServicesCapturaOperacionViewHelper getServicesCapturaOperacionViewHelper() {
		return servicesCapturaOperacionViewHelper;
	}

	/**
	 * @param servicesCapturaOperacionViewHelper the servicesCapturaOperacionViewHelper to set
	 */
	public void setServicesCapturaOperacionViewHelper(
			ServicesCapturaOperacionViewHelper servicesCapturaOperacionViewHelper) {
		this.servicesCapturaOperacionViewHelper = servicesCapturaOperacionViewHelper;
	}

	/**
	 * @return the vo
	 */
	public RetiroEfectivoVO getVo() {
		return vo;
	}

	/**
	 * @param vo the vo to set
	 */
	public void setVo(RetiroEfectivoVO vo) {
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

	public boolean isEsBancaComercial() {
		return esBancaComercial;
	}

	public void setEsBancaComercial(boolean esBancaComercial) {
		this.esBancaComercial = esBancaComercial;
	}

	public DTOValidator getValidadorRetiroBancaComercial() {
		return validadorRetiroBancaComercial;
	}

	public void setValidadorRetiroBancaComercial(
			DTOValidator validadorRetiroBancaComercial) {
		this.validadorRetiroBancaComercial = validadorRetiroBancaComercial;
	}

	public AdmonRetirosEfectivoService getAdmonRetiroEfectivo() {
		return admonRetiroEfectivo;
	}

	public void setAdmonRetiroEfectivo(
			AdmonRetirosEfectivoService admonRetiroEfectivo) {
		this.admonRetiroEfectivo = admonRetiroEfectivo;
	}

	/**
	 * @return the disabledBCom
	 */
	public Boolean getDisabledBCom() {
		return disabledBCom;
	}

	/**
	 * @param disabledBCom the disabledBCom to set
	 */
	public void setDisabledBCom(Boolean disabledBCom) {
		this.disabledBCom = disabledBCom;
	}

	/**
	 * @return the disabledSpei
	 */
	public Boolean getDisabledSpei() {
		return disabledSpei;
	}

	/**
	 * @param disabledSpei the disabledSpei to set
	 */
	public void setDisabledSpei(Boolean disabledSpei) {
		this.disabledSpei = disabledSpei;
	}

	/**
	 * @return the disabledSiac
	 */
	public Boolean getDisabledSiac() {
		return disabledSiac;
	}

	/**
	 * @param disabledSiac the disabledSiac to set
	 */
	public void setDisabledSiac(Boolean disabledSiac) {
		this.disabledSiac = disabledSiac;
	}

	/**
	 * @param admonCuentasService the admonCuentasService to set
	 */
	public void setAdmonCuentasService(AdministracionCuentasRetiroService admonCuentasService) {
		this.admonCuentasService = admonCuentasService;
	}

	public void setMovimientosEfectivoService(MovimientosEfectivoService movimientosEfectivoService) {
		this.movimientosEfectivoService = movimientosEfectivoService;
	}
}
