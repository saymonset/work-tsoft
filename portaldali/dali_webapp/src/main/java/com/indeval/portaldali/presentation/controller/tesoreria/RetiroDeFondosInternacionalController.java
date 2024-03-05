/*
 *Copyright (c) 2005-2006 Bursatec. All Rights Reserved 
 */
package com.indeval.portaldali.presentation.controller.tesoreria;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.bursatec.seguridad.dto.UsuarioDTO;
import com.bursatec.seguridad.presentation.constants.SeguridadConstants;
import com.indeval.portaldali.middleware.dto.CuentaEfectivoDTO;
import com.indeval.portaldali.middleware.dto.DivisaDTO;
import com.indeval.portaldali.middleware.dto.CuentaRetiroInternacionalDTO;
import com.indeval.portaldali.middleware.dto.InstitucionDTO;
import com.indeval.portaldali.middleware.dto.RetiroEfectivoInternacionalDTO;
import com.indeval.portaldali.middleware.dto.TipoCuentaDTO;
import com.indeval.portaldali.middleware.dto.TipoNaturalezaDTO;
import com.indeval.portaldali.middleware.dto.util.DTOAssembler;
import com.indeval.portaldali.middleware.dto.util.ResultadoValidacionDTO;
import com.indeval.portaldali.middleware.services.common.constants.DaliConstants;
import com.indeval.portaldali.middleware.services.common.constants.TipoCuentaConstants;
import com.indeval.portaldali.middleware.services.common.constants.TipoCustodiaConstants;
import com.indeval.portaldali.middleware.services.mercadodinero.RegistraOperacionParams;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.tesoreria.AdmonRetirosEfectivoService;
import com.indeval.portaldali.middleware.services.tesoreria.TesoreriaService;
import com.indeval.portaldali.middleware.services.util.UtilServices;
import com.indeval.portaldali.middleware.services.util.util.Constantes;
import com.indeval.portaldali.presentation.common.constants.TesoreriaConstantes;
import com.indeval.portaldali.presentation.controller.common.ControllerBase;
import com.indeval.portaldali.presentation.dto.mercadodinero.RetiroFondosDTO;
import com.indeval.portaldali.presentation.helper.IsoHelper;
import com.indeval.portaldali.presentation.helper.ServicesCapturaOperacionViewHelper;
import com.indeval.portaldali.presentation.util.CifradorDescifradorBlowfish;
import com.indeval.portaldali.presentation.util.ConsultaCatalogosFacade;
import com.indeval.portaldali.presentation.util.ResultadoInvocacionServicioUtil;
import com.indeval.portaldali.presentation.validation.DTOValidator;
import com.indeval.portaldali.vo.CaptOpsConfig;
import com.indeval.protocolofinanciero.api.vo.RetiroEfectivoVO;

/**
 * 
 * Controller para la pantalla de retiro de fondos internacional.
 * 
 * @author Maria C. Buendia
 */

public class RetiroDeFondosInternacionalController extends ControllerBase {
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
    /** Servicio para sacar folios de una secuencia */
    private UtilServices utilService;
	/** Ayudante para la generación de las cadenas ISO que deberán ser firmadas */    
    /** Folio de la operación */
    protected String folioAsignado = null;    
    /** Servicio helper para la captura de operaciones */
    private ServicesCapturaOperacionViewHelper servicesCapturaOperacionViewHelper = null;
    /** Servicio admon cuentas retiro internacional */
    private AdmonRetirosEfectivoService retiroEfvoInterService;
 
    
    
    //private CuentasRetiroEfectivoDao cuentaRetiroDao;
    /** EL numero de serie asociado al iso firmado */
    protected String numeroSerie = "";
    
	/** Ayudante para la generación de las cadenas ISO que deberán ser firmadas */
	protected IsoHelper isoHelper = null;
	/**
	 * Conjunto de isos Firmados
	 */
	private List<String> isosFirmados = null;
	/**
	 * Conjunto de isos sin firmar
	 */
	private List<String> isosSinFirmar = null;
	/**
	 * Conjunto de hash de isos
	 */
	private List<String> hashIso = null;
	/** cifrador-descifrador */
    protected CifradorDescifradorBlowfish cdb = new CifradorDescifradorBlowfish();
	
	private int totalOperaciones = 0;
    
	private String mensaje = "";
    
    private static String TIPO_INSTRUCCION_RET_FONDOS = "RETE"; 
    
    private RetiroEfectivoVO vo = null;

   

	/**
	 * Inicializa los datos de la página.
	 * 
	 * @return <code>null</code> este método no retorna nada.
	 */
	
    public String getInit() {
    	
    	logger.debug("En constructor RetiroDeFondosInternacionalController");
    	retiro.setValorEn(
    			new DivisaDTO(
    					Long.parseLong(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("cveDivisa"))
    			));
    	
    	
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
        retiro.setFechaValor(new Date());
        retiro.setCuentasBeneficiario(getCuentasInternacional());
        return null;
  }
	
	
	

	/**
	 * Genera la lista de cuentas de retiro internacional para el combo
	 * @return lista de estados 
	 */
	public List<SelectItem> getCuentasInternacional(){
		logger.debug("en getCuentasInternacional... ");
		List<SelectItem> opcionesSelect = new ArrayList<SelectItem>();
		opcionesSelect.add(new SelectItem(""+DaliConstants.VALOR_COMBO_TODOS,"Selecciona una cuenta"));
	
		Long getId =  retiro.getValorEn().getId();
		if (getId != null){
			retiroEfvoInterService.getCuentasInterXDivisa(new BigInteger(getId.toString()  ));				
		} else{
			retiroEfvoInterService.getCuentasInterXDivisa(BigInteger.ZERO);
		}
		
		
		for (CuentaRetiroInternacionalDTO cuenta: retiroEfvoInterService.getCuentasInterXDivisa(new BigInteger( String.valueOf(retiro.getValorEn().getId())))) {
			opcionesSelect.add(new SelectItem(""+cuenta.getIdCuentaRetiroInt(), cuenta.getNombreCorto()));
		}
		return opcionesSelect;
	}
	
	/**
	 * Obtiene las cuentas de beneficiario para la divisa seleccionada
	 * @param e El actionListener que dispara el evento 
	 */
	public void obtieneCuentasBeneficiario(ActionEvent e){		
		retiro.setCuentasBeneficiario(getCuentasInternacional());
	}
	
	/**
	 * Ejecuta las validaciones de los DTOs de captura de acuerdo al tipo de
	 * operacion a realizar
	 * 
	 * @return
	 */
	private boolean validarDTO() {
		ResultadoValidacionDTO resultado = null;
		resultado = validadorRetiroDeFondos.validarDTO(retiro);
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
		getInit();
	}
	
	/**
	 * Guarda el retiro con los datos de la pantalla
	 * 
	 * @param ValueChangeEvent
	 *            evento que dispara la accion
	 */
	public void guardarRetiro(ActionEvent event) {
	  	Map<String, String> map = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();        
		Boolean usaFirma = Boolean.valueOf(map.get("firma"));
		String numeroSerie = map.get("numeroSerie");
		isosSinFirmar = new ArrayList<String>();
		isosFirmados = new ArrayList<String>();
		hashIso = new ArrayList<String>();
		//int var=1;
		RetiroEfectivoInternacionalDTO retiroDTO = generarObjetoRetiroInternacionalDTO();
		//mensaje = "Inicia el metodo";
		if (!usaFirma || StringUtils.isNotEmpty(numeroSerie)){			

			String isoFirmado=null;
			String isoSinFirmar=map.get("isoSinFirmar1");					
			String firma =map.get("isoFirmado1");	
			if(StringUtils.isNotBlank(isoSinFirmar)  && StringUtils.isNotBlank(numeroSerie) && StringUtils.isNotBlank(firma)){
				StringBuilder isoCompleto= new StringBuilder();				
				isoCompleto.append(isoSinFirmar).append("\n")
				.append(numeroSerie).append("\n").append("{SHA1withRSA}").append(firma);
				isoFirmado=isoCompleto.toString();			
			}
		
				   //codigo1
					//retiro.setBoveda(""+retiro.getSaldoUsuario().getBoveda().getId());
					if(retiro.getBoveda()==null||retiro.getBoveda().equals(""+DaliConstants.VALOR_COMBO_TODOS)){
						retiro.setBoveda("");
					}
					if(retiro.getCuentaBeneficiaria()==null||retiro.getCuentaBeneficiaria().equals(""+DaliConstants.VALOR_COMBO_TODOS)){
						retiro.setCuentaBeneficiaria("");
					}
					try{
						
						mensaje = "antes de validar el rule";
						if(!retiroEfvoInterService.businessRulesCrearRetiro(retiro.getValorEn(), retiro.getImporteRetiro())){
							logger.error("Las validaciones para la creacion de retiro son incorrectas");
						}
						
						//retiroDTO = generarObjetoRetiroInternacionalDTO();
						HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			    		UsuarioDTO usuario= session!=null?(UsuarioDTO)session.getAttribute(SeguridadConstants.USUARIO_SESION):null;
						Map <String, Object> datosFirma = new HashMap<String, Object>(0);
						datosFirma.put("creacion_isoFirmado", isoFirmado);
			    		datosFirma.put("creacion_hash", hashIso);
			    		datosFirma.put("creacion_usuario", usuario.getClaveUsuario());
			    		datosFirma.put("creacion_serie", numeroSerie);
						retiroDTO.setDatosFirmas(datosFirma);
						
					  	retiroEfvoInterService.crearRetiro(retiroDTO);					  					  	
					  	limpiar(event);
					  	mensajeUsuario = "La información se guardo exitosamente.";
					  	mensaje = "La información se guardo exitosamente.";
				  		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, mensajeUsuario, mensajeUsuario));					  	
				  	//}
					//catch (BusinessException bex) {
				  	//	log.error("catch BusinessException bex");
				  	//	mensajeUsuario = "Hubo un error al guardar";
				  	//	mensaje = "Hubo un error al guardar";
				  	//	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, mensajeUsuario, mensajeUsuario));
				  	}catch (Exception ex){
				  		logger.error("catch Exception:"+ex);
				  		mensajeUsuario = "Hubo un error al guardar";
				  		mensaje = "Hubo un error al guardar";
				  		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, mensajeUsuario, mensajeUsuario));
				  		ex.printStackTrace();
				  	}
					//codigo2
		
		}else{
			//	SE REALIZA LA FIRMA DEL MENSAJE.
			retiroDTO.setReferenciaOperacion( utilService.getFolio(Constantes.SECUENCIA_FOLIO_CONTROL).toString());		
			retiroDTO.setReferenciaMensaje( utilService.getFolio(Constantes.SEQ_REFERENCIA_MENSAJE).toString());
			retiro.setReferenciaOperacion(retiroDTO.getReferenciaOperacion());
			retiro.setReferenciaMensaje(retiroDTO.getReferenciaMensaje());
			
			String isoElem=	retiroEfvoInterService.generaIso(retiroDTO);
			isosSinFirmar.add(isoElem);
			hashIso.add(cdb.cipherHash(isoElem));		
		}		
		totalOperaciones =isosSinFirmar.size();
	}
	
	/**
	 * Arma el DTO para la cuenta de retiro internacional
	 * @return retiro objeto del tipo RetiroEfectivoInternacionalDTO
	 */
	private RetiroEfectivoInternacionalDTO generarObjetoRetiroInternacionalDTO(){
		
		RetiroEfectivoInternacionalDTO retiroDTO = new RetiroEfectivoInternacionalDTO();
		retiroDTO.setConceptoPago(retiro.getConceptoPago());
		
		Long lngId =  Long.valueOf( retiro.getCuentaBeneficiaria());		
		retiroDTO.setIdCuentaBeneficiario(  lngId  );
		retiroDTO.setBoveda(consultaCatalogosFacade.buscarBovedaPorId(retiro.getSaldoUsuario().getBoveda().getId()));
		retiroDTO.setDivisa(consultaCatalogosFacade.buscarDivisaPorId(retiro.getValorEn().getId()));
		retiroDTO.setInstitucion(consultaCatalogosFacade.buscarInstitucionPorIdFolio(retiro.getIdFolioUsuario()));

		retiroDTO.setFechaValor(retiro.getFechaValor());
		retiroDTO.setImporteTraspaso(retiro.getImporteRetiro());

		retiroDTO.setReferenciaOperacion(retiro.getReferenciaOperacion());
		retiroDTO.setReferenciaMensaje(retiro.getReferenciaMensaje());
		
		return retiroDTO;
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
    	retiro.getSaldoUsuario().getCuenta().setInstitucion(consultaCatalogosFacade.buscarInstitucionPorIdNoNulaFolio(retiro.getIdFolioUsuario()));

        // Si el usuario debe firmar la operacion, se recuperar la firma.
        // Si no se ha firmado, se procesan los datos y regresa el control a la pantalla para firmar
        if(isUsuarioConFacultadFirmar()) {
	        /*recuperarCamposFirma();
	        
	        if (!StringUtils.isEmpty(isoFirmado)) {
	            grabarOperacion(e);
	        } else {
	            procesarDatos();
	        }*/
        } else {
        	procesarDatos();
        }
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
			//folioAsignado = folioInteger.toString();
			//retiro.setFolioOperacion(new Integer(folioAsignado));
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
			//vo.setReferenciaOperacion(this.folioAsignado);
			//vo.setReferenciaNumericaSPEI(this.folioAsignado);
			vo.setConceptoPago(retiro.getTipoRetiro() == Constantes.ID_RETIRO_SIAC ? Constantes.DESC_RETIRO_SIAC : Constantes.DESC_RETIRO_SPEI);

            // Si el usuario debe firmar la operacion, se crea el iso
            if(isUsuarioConFacultadFirmar()) {
//				isoSinFirmar = isoHelper.creaISO(vo, false);
	//			hashIso = cdb.cipherHash(isoSinFirmar);
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
    	/*
    	if(!cdb.validation(hashIso, isoSinFirmar)){
    		throw new InfrastructureException(UtilConstantes.ERROR_ISO_DIFERENTE);
    	}
    	isoFirmado = (new StringBuilder()).append(isoSinFirmar).append("\n").append(numeroSerie).append("\n").append("{SHA1withRSA}").append(isoFirmado).toString();
    	*/
    	
    	enviarOperacionABitacora();
    }

	/**
	 * Envia la operacion a bitacora
	 */
	private void enviarOperacionABitacora() {
		HashMap<String, String> datosAdicionales = new HashMap<String, String>();
		datosAdicionales.put("ordenante", vo.getOrdenante());
        datosAdicionales.put("beneficiario", vo.getBeneficiario());            
        datosAdicionales.put("tipoRetiro", vo.getConceptoPago());
    	
    	//envioOperacionesService.grabaOperacion(vo, folioAsignado, false, datosAdicionales, null, isoFirmado);
    	
    	if(!existeErrorEnInvocacion()){
//                mensajeUsuario = "La operaci\u00f3n se llev\u00f3 a cabo exitosamente. Folio de la operaci\u00f3n : " + folioAsignado;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, mensajeUsuario, mensajeUsuario));
            retiro = new RetiroFondosDTO();
            this.limpiar(null);
    	}
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
		if(retiro != null && retiro.getIdFolioUsuario() != null) {
			InstitucionDTO i = consultaCatalogosFacade.buscarInstitucionPorIdFolio(retiro.getIdFolioUsuario());
			if(i !=  null) {
				retiro.getSaldoUsuario().getCuenta().setInstitucion(i);
			}
		}
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
		
		//if(retiro.getBoveda() != null && !retiro.getBoveda().equals(""+DaliConstants.VALOR_COMBO_TODOS)
		//}
		
		//else 
		
		if (retiro.getIdFolioUsuario() != null && retiro.getSaldoUsuario().getBoveda().getId()> 0) {
			Double saldo = consultaCatalogosFacade.getSaldoNetoEfectivoPorBoveda(retiro.getIdFolioUsuario(),retiro.getSaldoUsuario().getBoveda().getId());
			retiro.getSaldoUsuario().setSaldoDisponible(saldo != null ? saldo : new Double(0));			
		}
		
	}
	
	/** métodos getters & setters */

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

	public IsoHelper getIsoHelper() {
		return isoHelper;
	}

	public void setIsoHelper(IsoHelper isoHelper) {
		this.isoHelper = isoHelper;
	}



	public String getNumeroSerie() {
		return numeroSerie;
	}

	public void setNumeroSerie(String numeroSerie) {
		this.numeroSerie = numeroSerie;
	}



    public List<String> getIsosFirmados() {
		return isosFirmados;
	}

	public void setIsosFirmados(List<String> isosFirmados) {
		this.isosFirmados = isosFirmados;
	}

	public List<String> getIsosSinFirmar() {
		return isosSinFirmar;
	}

	public void setIsosSinFirmar(List<String> isosSinFirmar) {
		this.isosSinFirmar = isosSinFirmar;
	}

	public List<String> getHashIso() {
		return hashIso;
	}

	public void setHashIso(List<String> hashIso) {
		this.hashIso = hashIso;
	}

	public int getTotalOperaciones() {
		return totalOperaciones;
	}

	public void setTotalOperaciones(int totalOperaciones) {
		this.totalOperaciones = totalOperaciones;
	}

	/**
     * Indica si ya se firmo el ISO en pantalla.
     * 
     * @return <code>true</code> si se firmo el ISO en pantalla.
     */
	/*
    public boolean isIsoYaFirmado() {
    	
    	return !StringUtils.isEmpty(isoSinFirmar);
    }
    */

	public AdmonRetirosEfectivoService getRetiroEfvoInterService() {
		return retiroEfvoInterService;
	}
	

	public void setRetiroEfvoInterService(
			AdmonRetirosEfectivoService retiroEfvoInterService) {
		this.retiroEfvoInterService = retiroEfvoInterService;
	}



	public CifradorDescifradorBlowfish getCdb() {
		return cdb;
	}

	public void setCdb(CifradorDescifradorBlowfish cdb) {
		this.cdb = cdb;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	
	
}
