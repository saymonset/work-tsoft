/*
 *Copyright (c) 2009 Bursatec. All Rights Reserved 
 */
package com.indeval.portaldali.presentation.controller.tesoreria.cuentas;

import java.math.BigInteger;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

import org.apache.axis.utils.StringUtils;

import com.bursatec.seguridad.dto.UsuarioDTO;
import com.bursatec.seguridad.presentation.constants.SeguridadConstants;
import com.indeval.portaldali.middleware.dto.BovedaDTO;
import com.indeval.portaldali.middleware.dto.CuentaRetiroEfectivoDTO;
import com.indeval.portaldali.middleware.dto.DivisaDTO;
import com.indeval.portaldali.middleware.dto.InstitucionDTO;
import com.indeval.portaldali.middleware.dto.util.ResultadoValidacionDTO;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.tesoreria.cuentas.AdministracionCuentasRetiroService;
import com.indeval.portaldali.presentation.controller.common.ControllerBase;
import com.indeval.portaldali.presentation.util.CifradorDescifradorBlowfish;
import com.indeval.portaldali.presentation.util.ConsultaCatalogosFacade;
import com.indeval.portaldali.presentation.validation.DTOValidator;
import com.indeval.portaldali.presentation.dto.tesoreria.AltaCuentaDTO;

/**
 * Controller de las pantalla de Creacion de Cuentas
 * 
 * @author Maria C. Buendia
 * @version 1.0
 */
public class AltaCuentaController extends ControllerBase {
	
	/** La clase que permite el acceso a la consulta de los catálogos utilizados */
	private ConsultaCatalogosFacade consultaCatalogosFacade;
	
	/** DTO para pasar los datos de la pantalla */
	private AltaCuentaDTO alta = new AltaCuentaDTO();
	
	/** Validador de los campos de la pantalla. */
	private DTOValidator validadorAltaCuenta;
	
	/** Validador de los campos de la pantalla internacional. */
	private DTOValidator validadorAltaCuentaInt;
	
    /** servicio de administracion de cuentas */
    private AdministracionCuentasRetiroService admonCuentasService;

    /** La cadena que contiene el iso firmado */
    protected String isoFirmado = "";
    
    /** La cadena que contiene el iso sin firmar */
    protected String isoSinFirmar = null;
    
    /** EL numero de serie asociado al iso firmado */
    protected String numeroSerie = "";
    
    /** Cadena Hash del ISO a firmar */
    protected String hashIso = null;
    
    /** define si se trata de una cuenta nacional(false) o internacional(true), default=nacional */
    private boolean esCuentaInternacional;

	/** cifrador-descifrador */
    protected CifradorDescifradorBlowfish cdb = new CifradorDescifradorBlowfish();
    
    /** define si es una alta o una modificacion */
	private boolean esModificacion;
	
	/** define si se esta limpiando la pantalla solamente */
	private boolean esLimpiar;
	
	/** define si se esta limpiando la pantalla solamente */
	private boolean esValidar;
	
	/** folio de la cuenta*/
	private BigInteger folioCuenta;
	
	/** folio de la cuenta por institucion */
	private BigInteger folioCuentaInstitucion;
	/** Bean de la pagina de consulta */
	private ConsultaCuentaController consultaCuentaController;
    
	

	/**
	 * Inicializa los datos de la pagina.
	 * 
	 * @return <code>null</code>.
	 */
	public String getInit() {
		logger.debug("Entrando AltaCuentaController.getInit ");
		logger.debug(":::: 1 esCuentaInternacional:"+esCuentaInternacional);
		
		InstitucionDTO inst = new InstitucionDTO();
		if (alta.getIdFolioUsuario() == null) {
			alta.setIdFolioUsuario(getInstitucionActual().getClaveTipoInstitucion() + getInstitucionActual().getFolioInstitucion());
		}
		if (!esModificacion && alta.getIdFolioUsuario() != null) {
			inst = consultaCatalogosFacade.buscarInstitucionPorIdFolio(alta.getIdFolioUsuario());
			if (inst != null) {
				alta.setInstitucion(inst);
			}
		}

		BovedaDTO boveda = new BovedaDTO();
		boveda.setId(new Long(11));
		alta.setBoveda(boveda);
		
		if (alta.getInstitucionBeneficiario() != null) {
			inst = consultaCatalogosFacade.buscarInstitucionPorIdFolio(alta.getIdBancoBeneficiario());
			if (inst != null) {
				alta.setInstitucionBeneficiario(inst);
			}else{
				inst = new InstitucionDTO();
				inst.setNombreCorto("");
				alta.setInstitucionBeneficiario(inst);
			}
		}
		
        Map<String , String> map=FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String idCuenta=map.get("idCuenta");
        String esInternacional=map.get("esInternacional");
        String idDivisa=map.get("idDivisa");        
        
        if(!esLimpiar&&!esValidar){
        	esCuentaInternacional = Boolean.parseBoolean(esInternacional);
        	DivisaDTO divisa = idDivisa==null?MXP:consultaCatalogosFacade.buscarDivisaPorId(Long.parseLong(idDivisa));
        	alta.setValorEn(divisa);
        }
        
        if(!StringUtils.isEmpty(idCuenta)){
              esModificacion = true;
              logger.debug("::: buscando idCuenta="+idCuenta+", esCuentaInternacional:"+esCuentaInternacional);
              CuentaRetiroEfectivoDTO cuenta = esCuentaInternacional? 
            		  							admonCuentasService.buscarCuenta(BigInteger.valueOf(Long.valueOf(idCuenta)), false)
            		  							:admonCuentasService.buscarCuenta(BigInteger.valueOf(Long.valueOf(idCuenta)), true);
        	  alta.setIdCuentaRetiro(BigInteger.valueOf(cuenta.getIdCuentaRetiro()));
              alta.setIdFolioUsuario(cuenta.getInstitucion().getClaveTipoInstitucion() + cuenta.getInstitucion().getFolioInstitucion());
              alta.setInstitucion(cuenta.getInstitucion());
              alta.setValorEn(cuenta.getDivisa());
              alta.setIdPorInstitucion(cuenta.getIdCuentaPorInstitucion());
              
              if(esCuentaInternacional){
            	  alta.setId(cuenta.getIdCuentaRetiroInt());
	              alta.setAliasCuenta(cuenta.getNombreCorto());
	              alta.setCuentaBeneficiarioFinal(cuenta.getCuentaBeneficiarioFinal());             
	              alta.setNombreBeneficiarioFinal(cuenta.getNombreBeneficiarioFinal());
	              alta.setBancoBeneficiario(cuenta.getBancoBeneficiario());
	              alta.setNombreBancoBeneficiario(cuenta.getNombreBancoBeneficiario());
	              alta.setCuentaBancoBeneficiario(cuenta.getCuentaBeneficiario());
	              alta.setBancoIntermediario(cuenta.getBancoIntermediario());
	              alta.setNombreBancoIntermediario(cuenta.getNombreIntermediario()); //esta bien??
	              alta.setDetallesPago(cuenta.getDetallesPago());
	              InstitucionDTO instd = consultaCatalogosFacade.buscarInstitucionPorIdFolio(
			  				cuenta.getInstitucion().getClaveTipoInstitucion() 
			  				+ cuenta.getInstitucion().getFolioInstitucion());
	              alta.setInstitucion(instd);
	              alta.setIdFolioUsuario(instd.getClaveTipoInstitucion() + instd.getFolioInstitucion());
              }else{
            	  alta.setId(cuenta.getIdCuentaRetiroNal());
            	  alta.setBoveda(cuenta.getBoveda());
            	  alta.setCuentaBeneficiario(cuenta.getCuentaBeneficiario());
            	  alta.setNombreBeneficiario(cuenta.getNombreBeneficiario());
            	  alta.setMontoMaximoMensual(cuenta.getMontoMaximoMensual()==null?null:""+cuenta.getMontoMaximoMensual());
            	  alta.setMontoMaximoDiario(cuenta.getMontoMaximoDiario()==null?null:""+cuenta.getMontoMaximoDiario());
            	  alta.setMontoMaximoXTran(cuenta.getMontoMaximoXTran()==null?null:""+cuenta.getMontoMaximoXTran());
            	  alta.setNumMaximoMovsXMes(cuenta.getNumMaximoMovsXMes()==null||cuenta.getNumMaximoMovsXMes()==0? null:""+cuenta.getNumMaximoMovsXMes());
            	  InstitucionDTO instd = consultaCatalogosFacade.buscarInstitucionPorIdFolio(
							  				cuenta.getInstitucion().getClaveTipoInstitucion() 
							  				+ cuenta.getInstitucion().getFolioInstitucion());
            	  
            	  alta.setInstitucion(instd);
            	  alta.setIdFolioUsuario(instd.getClaveTipoInstitucion() + instd.getFolioInstitucion());
            	  alta.setIdBancoBeneficiario(cuenta.getInstitucionBeneficiario().getClaveTipoInstitucion() 
											+ cuenta.getInstitucionBeneficiario().getFolioInstitucion());
            	  InstitucionDTO instb = consultaCatalogosFacade.buscarInstitucionPorIdFolio(alta.getIdBancoBeneficiario());
            	  alta.setInstitucionBeneficiario(instb);
              }
              
        }
		
        esLimpiar = false;
        esValidar = false;
        
		return null;
	}
	
	/**
	 * Busca la institucion apartir del ID-Folio
	 * 
	 * @param event
	 */
	public void buscarInstitucionParticipante(ActionEvent event) {
		alta.setInstitucion(new InstitucionDTO());
		if(alta != null && alta.getIdFolioUsuario() != null) {
			InstitucionDTO i = consultaCatalogosFacade.buscarInstitucionPorIdFolio(alta.getIdFolioUsuario());
			if(i !=  null) {
				alta.setInstitucion(i);
			}
		}
	}

	public String regresar() {
		consultaCuentaController.buscarCuentas(null);
		consultaCuentaController.setBloqueaInicio(Boolean.TRUE);
		return "consultaCuentaNacional";
	}
	
	
	/**
	 * Busca una lista de participantes cuyo id-folio comience con la cadena de
	 * criterio recibido
	 * 
	 * @param prefijo Cadena de criterio id-folio
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
	 * Define si debe hablitarse la seccion de intermediario o no
	 * @param el evento 
	 */
	public void limpiar(ActionEvent event) {
		String idDivisa = ""+alta.getValorEn().getId();
		InstitucionDTO i = alta.getInstitucion();
		alta = new AltaCuentaDTO();
		if(esModificacion){
			alta.setInstitucion(i);
			alta.setIdFolioUsuario(alta.getInstitucion().getClaveTipoInstitucion() + alta.getInstitucion().getFolioInstitucion());
		}
		DivisaDTO divisa = idDivisa==null?MXP:consultaCatalogosFacade.buscarDivisaPorId(Long.parseLong(idDivisa));
		alta.setValorEn(divisa);
		//limpiando la firma
		isoSinFirmar = null;
		isoFirmado = "";
		numeroSerie = "";
		hashIso = null;
		//bandera en true
		esLimpiar = true;
	}
	
	/**
	 * ActionListener para el boton que permite guardar la cuenta.
	 * @param event el evento que dispara este ActionListener
	 */
	public String guardarCuenta(){
		logger.debug("en guardar cuenta...");
		logger.debug(":::: 4 esCuentaInternacional:" + esCuentaInternacional);
		String retorno = null;
		//Se agrega validación extra para saber si tiene la facultad CON_FIRMA
		//03-Febrero-2015 Pablo Balderas
		if(isUsuarioConFacultadFirmar()) {
			try {				
				//Valida la vigencia del certificado - 18-Agosto-2014 Pablo Balderas
				validarVigenciaCertificado();
				if(validarDTO()) {
					alta.setNombreBeneficiario(alta.getNombreBeneficiario().toUpperCase());
					logger.debug("institucion:"+alta.getInstitucion()+", cuenta clabe:" + alta.getInstitucion().getCuentaClabe());
					isoSinFirmar = null;
					boolean resultado = esCuentaInternacional ? admonCuentasService
							.businessRulesCrearCuentaRetiroInternacional(
									alta.getValorEn(),
									alta.getCuentaBeneficiarioFinal(),
									alta.getNombreBeneficiarioFinal(),
									alta.getBancoBeneficiario(),
									alta.getNombreBancoBeneficiario(),
									alta.getCuentaBancoBeneficiario(),
									alta.getBancoIntermediario(),
									alta.getNombreBancoIntermediario(), new long[] {
											esModificacion ? 1 : 0, alta.getId() })
							: admonCuentasService.businessRulesCrearCuentaRetiro(
									alta.getInstitucion(),
									alta.getInstitucionBeneficiario(),
									alta.getValorEn(),
									alta.getCuentaBeneficiario(),
									alta.getNombreBeneficiario(),
									alta.getMontoMaximoMensual(),
									alta.getMontoMaximoDiario(),
									alta.getMontoMaximoXTran(),
									alta.getNumMaximoMovsXMes(), new long[] {
											esModificacion ? 1 : 0, alta.getId() });
					Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
					isoFirmado = params.get("isoFirmado");
					hashIso = params.get("hashIso");
					isoSinFirmar = params.get("isoSinFirmar");
					numeroSerie = params.get("numeroSerie");
			        if (!StringUtils.isEmpty(isoFirmado)) {
//			        	if(!cdb.validation(hashIso, isoSinFirmar)){
//			        		throw new InfrastructureException(UtilConstantes.ERROR_ISO_DIFERENTE);
//			        	}
						isoFirmado = (new StringBuilder()).append(isoSinFirmar)
								.append("\n").append(numeroSerie).append("\n")
								.append("{SHA1withRSA}").append(isoFirmado)
								.toString();
			        	//limpiando para que ya no muestre el applet
			        	isoSinFirmar=null;
			        	//creando la cuenta firmada
						CuentaRetiroEfectivoDTO cuenta = 
							esCuentaInternacional ? generarCuentaInternacionalDTO() : generarCuentaNacionalDTO();
						cuenta.setTipoCuenta(esCuentaInternacional?'I':'N');
						HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			    		UsuarioDTO usuario= session!=null?(UsuarioDTO)session.getAttribute(SeguridadConstants.USUARIO_SESION):null;
						Map<String, Object> datosFirmas = new HashMap<String, Object>(0);
						datosFirmas.put(esModificacion?"modificacion_isoFirmado":"creacion_isoFirmado", isoFirmado);
						datosFirmas.put(esModificacion?"modificacion_usuario":"creacion_usuario", usuario.getClaveUsuario());
						datosFirmas.put(esModificacion?"modificacion_serie":"creacion_serie", numeroSerie);
						cuenta.setDatosFirmas(datosFirmas);
			        	if(esModificacion && !esCuentaInternacional) {
			        		cuenta.setIdCuentaRetiroNal(alta.getId());
			        	}
			        	else if(esModificacion && esCuentaInternacional) {
			        		cuenta.setIdCuentaRetiroInt(alta.getId());
			        	} 
			        	mensajeUsuario="";
			        	//obteniendo el horario de modificacion para validacion
			        	Map<String, Object> datosModificar = admonCuentasService.revisarModificacionesHabilitadas();
			        	//se comenta este codigo hasta que entre la modificacion de cuentas liberadas y aplique el horario.
			    		//por ahora, la bandera estara siempre en true.
			        	//boolean aplicaModificaciones = ((Boolean)datosModificar.get("aplicaModificaciones")).booleanValue();
			        	boolean aplicaModificaciones = true;
			    		//se valida aqui porque en lo que captura la firma podria terminarse el periodo de tiempo valido
			        	if(esModificacion && aplicaModificaciones) { 
			        		cuenta.setIdCuentaRetiro(alta.getIdCuentaRetiro().longValue());
			        		cuenta.setIdCuentaPorInstitucion(alta.getIdPorInstitucion());
			        		admonCuentasService.actualizarCuenta(cuenta);
			        		mensajeUsuario = "La modificaci\u00f3n se llev\u00f3 a cabo exitosamente.";
							retorno = regresar();
			        	}
			        	else if(esModificacion && !aplicaModificaciones){ 
			        		mensajeUsuario = "No se realiz\u00f3 la modificaci\u00f3n. "+ ((String)datosModificar.get("mensaje"));
			        	}
			        	else{
							folioCuenta = null;
							folioCuenta = 
								esCuentaInternacional ? 
									admonCuentasService.obtenerFolioCuenta(false) : 
									admonCuentasService.obtenerFolioCuenta(true);
							folioCuentaInstitucion = 
								esCuentaInternacional ? 
									admonCuentasService.obtenerFolioCuenta(false, cuenta.getInstitucion().getId()) : 
									admonCuentasService.obtenerFolioCuenta(true, cuenta.getInstitucion().getId());
			        		cuenta.setIdCuentaRetiroInt(folioCuenta.longValue());
			        		cuenta.setIdCuentaRetiroNal(folioCuenta.longValue());
			        		cuenta.setIdCuentaPorInstitucion(folioCuentaInstitucion.longValue());
			        		admonCuentasService.crearCuenta(cuenta);
			        		mensajeUsuario = "La creaci\u00f3n se llev\u00f3 a cabo exitosamente. Folio de la cuenta: " + folioCuentaInstitucion;
			        	}
			            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, mensajeUsuario, mensajeUsuario));
				        limpiar(null);
			        }
			        else{
			        	isoSinFirmar = armaDatosFirma();
						hashIso = cdb.cipherHash(isoSinFirmar);
			        }
				}
				else {
					isoSinFirmar = null;
				}			
			}
			catch (BusinessException bex) {
				FacesContext.getCurrentInstance().addMessage(
					null, new FacesMessage(FacesMessage.SEVERITY_ERROR, bex.getMessage(), bex.getMessage()));
			}
			catch (EJBException bex) {
				FacesContext.getCurrentInstance().addMessage(
					null, new FacesMessage(FacesMessage.SEVERITY_ERROR, bex.getCausedByException().getMessage(), 
						bex.getCausedByException().getMessage()));
			}
		}
		else {
			addMessageFromProperties("msgErrorSinCertificado", FacesMessage.SEVERITY_ERROR);
		}
		return retorno;
	}
	
	/**
	 * Generar el objeto cuenta a partir de los datos de la pantalla
	 * @return objeto CuentaRetiroEfectivoDTO 
	 */
	private CuentaRetiroEfectivoDTO generarCuentaNacionalDTO(){
		CuentaRetiroEfectivoDTO cuentadto = new CuentaRetiroEfectivoDTO();
		cuentadto.setInstitucion(alta.getInstitucion());
		cuentadto.setDivisa(alta.getValorEn());
		
		logger.debug("alta.getBoveda():"+alta.getBoveda());
		logger.debug("alta.getBoveda().getId():"+alta.getBoveda().getId());
		cuentadto.setBoveda(alta.getBoveda());
		InstitucionDTO instb = consultaCatalogosFacade.buscarInstitucionPorIdFolio(alta.getIdBancoBeneficiario());
		cuentadto.setInstitucionBeneficiario(instb);
		
		logger.debug("alta.getCuentaBeneficiario():"+alta.getCuentaBeneficiario());
		logger.debug("alta.getNombreBeneficiario():"+alta.getNombreBeneficiario());
		
		cuentadto.setCuentaBeneficiario(alta.getCuentaBeneficiario());
		cuentadto.setNombreBeneficiario(alta.getNombreBeneficiario());
		cuentadto.setMontoMaximoMensual(StringUtils.isEmpty(alta.getMontoMaximoMensual())?
											null:new BigDecimal(alta.getMontoMaximoMensual()));
		cuentadto.setMontoMaximoDiario(StringUtils.isEmpty(alta.getMontoMaximoDiario())?
											null:new BigDecimal(alta.getMontoMaximoDiario()));
		cuentadto.setMontoMaximoXTran(StringUtils.isEmpty(alta.getMontoMaximoXTran())?
											null:new BigDecimal(alta.getMontoMaximoXTran()));
		cuentadto.setNumMaximoMovsXMes(StringUtils.isEmpty(alta.getNumMaximoMovsXMes())?
											null:Long.parseLong(alta.getNumMaximoMovsXMes()));

		logger.debug("cuentadto.getCuentaBeneficiario():"+cuentadto.getCuentaBeneficiario());
		logger.debug("cuentadto.getNombreBeneficiario():"+cuentadto.getNombreBeneficiario());
		
		return cuentadto;
	}
	
	/**
	 * Arma el DTO para la cuenta de retiro internacional
	 * 
	 * @return cuenta objeto del tipo CuentaRetiroEfectivoDTO
	 */
	private CuentaRetiroEfectivoDTO generarCuentaInternacionalDTO(){
		CuentaRetiroEfectivoDTO cuenta = new CuentaRetiroEfectivoDTO();
		cuenta.setBancoBeneficiario(alta.getBancoBeneficiario());
		cuenta.setBancoIntermediario(alta.getBancoIntermediario());
		cuenta.setCuentaBeneficiario(alta.getCuentaBancoBeneficiario());
		cuenta.setCuentaBeneficiarioFinal(alta.getCuentaBeneficiarioFinal());
		cuenta.setCuentaIntermediario(alta.getCuentaIntermediario());
		cuenta.setDetallesPago(alta.getDetallesPago());
		cuenta.setNombreBancoBeneficiario(alta.getNombreBancoBeneficiario());
		cuenta.setNombreBeneficiarioFinal(alta.getNombreBeneficiarioFinal());
		cuenta.setNombreCorto(alta.getAliasCuenta());
		cuenta.setNombreIntermediario(alta.getNombreBancoIntermediario());
		cuenta.setInstitucion(alta.getInstitucion());
		cuenta.setDivisa(alta.getValorEn());

		return cuenta;
	}
	
	/**
	 * Ejecuta las validaciones de los DTOs de captura de acuerdo al tipo de
	 * operacion a realizar
	 * 
	 * @return
	 */
	private boolean validarDTO() {
		ResultadoValidacionDTO resultado = null;
		esValidar = true;
		logger.debug(":::: 5 esCuentaInternacional:"+esCuentaInternacional);
		resultado = esCuentaInternacional?
					validadorAltaCuentaInt.validarDTO(alta)
				    :validadorAltaCuenta.validarDTO(alta);
		if (!resultado.isValido()) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, resultado.getMensaje(), resultado.getMensaje()));
		}
		return resultado.isValido();
	}
	
	/**
	 * Arma los datos completos para la firma electronica
	 */
	private String armaDatosFirma(){
		StringBuffer datosFirma = new StringBuffer(esModificacion? "\nMODIFICACION DE CUENTA ":"\nCREACION DE CUENTA ");
		datosFirma.append(esCuentaInternacional?"INTERNACIONAL":"NACIONAL");
		datosFirma.append("\nTRASPASANTE: ").append(alta.getIdFolioUsuario()).append(" ").append(alta.getInstitucion().getNombreCorto());
		datosFirma.append("\nRECEPTOR: ").append(alta.getIdBancoBeneficiario()).append(" ").append(alta.getInstitucionBeneficiario().getNombreCorto());
		datosFirma.append("\nCUENTA BENEFICIARIO: ").append(alta.getCuentaBeneficiario());
		datosFirma.append("\nNOMBRE BENEFICIARIO: ").append(alta.getNombreBeneficiario());
		datosFirma.append("\nMONTO MAXIMO MENSUAL: ").append(StringUtils.isEmpty(alta.getMontoMaximoMensual())?"SIN LIMITE":alta.getMontoMaximoMensual());
		datosFirma.append("\nMONTO MAXIMO DIARIO: ").append(StringUtils.isEmpty(alta.getMontoMaximoDiario())?"SIN LIMITE":alta.getMontoMaximoDiario());
		datosFirma.append("\nMONTO MAXIMO TRANSACCION: ").append(StringUtils.isEmpty(alta.getMontoMaximoXTran())?"SIN LIMITE":alta.getMontoMaximoXTran());
		datosFirma.append("\nMOVIMIENTOS MAXIMOS POR MES: ").append(StringUtils.isEmpty(alta.getNumMaximoMovsXMes())?"SIN LIMITE":alta.getNumMaximoMovsXMes());
		datosFirma.append("\n");

		return datosFirma.toString();
	}
	
	/**
	 * Metodo vacio auxiliar 
	 * @param event
	 */
	public void auxiliar(ActionEvent event) {
		// vacio porque no tiene que hacer nada pero sirve para que tome la funcionalidad en los campos de montos
	}
	
	public ConsultaCatalogosFacade getConsultaCatalogosFacade() {
		return consultaCatalogosFacade;
	}

	public void setConsultaCatalogosFacade(
			ConsultaCatalogosFacade consultaCatalogosFacade) {
		this.consultaCatalogosFacade = consultaCatalogosFacade;
	}
	
	public AltaCuentaDTO getAlta() {
		return alta;
	}

	public void setAlta(AltaCuentaDTO alta) {
		this.alta = alta;
	}

	public String getIsoFirmado() {
		return isoFirmado;
	}

	public void setIsoFirmado(String isoFirmado) {
		this.isoFirmado = isoFirmado;
	}

	public String getIsoSinFirmar() {
		return isoSinFirmar;
	}

	public void setIsoSinFirmar(String isoSinFirmar) {
		this.isoSinFirmar = isoSinFirmar;
	}

	public String getNumeroSerie() {
		return numeroSerie;
	}

	public void setNumeroSerie(String numeroSerie) {
		this.numeroSerie = numeroSerie;
	}

	public String getHashIso() {
		return hashIso;
	}

	public void setHashIso(String hashIso) {
		this.hashIso = hashIso;
	}

	public DTOValidator getValidadorAltaCuenta() {
		return validadorAltaCuenta;
	}

	public void setValidadorAltaCuenta(DTOValidator validadorAltaCuenta) {
		this.validadorAltaCuenta = validadorAltaCuenta;
	}

	public AdministracionCuentasRetiroService getAdmonCuentasService() {
		return admonCuentasService;
	}

	public void setAdmonCuentasService(
			AdministracionCuentasRetiroService admonCuentasService) {
		this.admonCuentasService = admonCuentasService;
	}

	public boolean isEsCuentaInternacional() {
		return esCuentaInternacional;
	}

	public void setEsCuentaInternacional(boolean esCuentaInternacional) {
		this.esCuentaInternacional = esCuentaInternacional;
	}

	public boolean isEsModificacion() {
		return esModificacion;
	}

	public void setEsModificacion(boolean esModificacion) {
		this.esModificacion = esModificacion;
	}

	public DTOValidator getValidadorAltaCuentaInt() {
		return validadorAltaCuentaInt;
	}

	public void setValidadorAltaCuentaInt(DTOValidator validadorAltaCuentaInt) {
		this.validadorAltaCuentaInt = validadorAltaCuentaInt;
	}

	public BigInteger getFolioCuenta() {
		return folioCuenta;
	}

	public void setFolioCuenta(BigInteger folioCuenta) {
		this.folioCuenta = folioCuenta;
	}

	public BigInteger getFolioCuentaInstitucion() {
		return folioCuentaInstitucion;
	}

	public void setFolioCuentaInstitucion(BigInteger folioCuentaInstitucion) {
		this.folioCuentaInstitucion = folioCuentaInstitucion;
	}

	/**
	 * Bean de la pagina de consulta
	 * @return the consultaCuentaController
	 */ public ConsultaCuentaController getConsultaCuentaController() {
		return consultaCuentaController;
	}

	/**
	 * Bean de la pagina de consulta
	 * @param consultaCuentaController the consultaCuentaController to set
	 */ public void setConsultaCuentaController(ConsultaCuentaController consultaCuentaController) {
		this.consultaCuentaController = consultaCuentaController;
	}
}
