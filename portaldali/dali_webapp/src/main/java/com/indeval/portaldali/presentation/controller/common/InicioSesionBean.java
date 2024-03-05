/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * InicioSesionBean.java
 * 04/03/2008
 */
package com.indeval.portaldali.presentation.controller.common;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.rmi.RemoteException;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.jasypt.encryption.pbe.PBEStringEncryptor;

import com.bursatec.seguridad.dto.UsuarioDTO;
import com.bursatec.seguridad.middleware.ejb.CaptchaService;
import com.bursatec.seguridad.middleware.ejb.CaptchaServiceLocator;
import com.bursatec.seguridad.middleware.ejb.SeguridadServiceLocator;
import com.bursatec.seguridad.middleware.service.SeguridadException;
import com.bursatec.seguridad.middleware.service.SeguridadService;
import com.bursatec.seguridad.persistence.model.Usuario;
import com.bursatec.seguridad.presentation.constants.SeguridadConstants;
import com.bursatec.seguridad.vo.InstitucionVO;
import com.bursatec.seguridad.vo.RespuestaVO;
import com.bursatec.seguridad.vo.SistemaVO;
import com.bursatec.seguridad.vo.TicketInfoVO;
import com.indeval.portaldali.middleware.dto.InstitucionDTO;
import com.indeval.portaldali.middleware.services.common.ConsultaInstitucionService;
import com.indeval.portaldali.persistence.util.DateUtil;
import com.indeval.portaldali.presentation.common.constants.UtilConstantes;
import com.indeval.portaldali.presentation.jsf.menunavegacion.constants.MenuNavegacionConstants;
import com.indeval.portaldali.presentation.jsf.menunavegacion.vo.ElementoMenu;
import com.indeval.portaldali.presentation.util.MenuNavegacionHelper;
import com.indeval.sms.exceptions.SmsException;
import com.indeval.sms.servicios.AvisosService;
import com.indeval.sms.vo.MensajeSmsVO;


/**
 * Backing bean para el inicio de sesión de un usuario
 * 
 * @author Emigdio
 * 
 */
public class InicioSesionBean extends InicioSesionFirmaDigital {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2562195793067200415L;

	private static final String SERIAL_UNO = "1111111111111";

	/**
	 * Servicio para la consulta de instituciones
	 */
	private ConsultaInstitucionService consultaInstitucionService = null;

	/** El elemento del menú que representa la raíz del menú de navegación */
	private ElementoMenu elementoMenuRaiz = null;

	/** Mensaje de error */
	private String mensajeError = null;

	/** Mensaje de error */
	private String mensajeError2 = null;

	/** User */
	private String usuario = null;
	
	/** Password */
	private String password = null;

	/** El token de seguridad del usuario. */
	private String token = null;
	
	/** Texto del captcha capturado */
	private String textoCaptcha = "";

	/** El delay para los autocompletes de la aplicación */
	private long delay = 0;

	/** Servicio de negocio para consultar si existen mensajes para el usuario */
	private AvisosService avisosService;

	/** La llave para la sesión de los mensajes del usuario */
	private static final String MENSAJES_SESSION_KEY = "mensajesUsuario";

	/** Encriptador para el ticket de la sesion */
	private PBEStringEncryptor stringEncryptor;
	
	/** Bandera que indica si el usuario puede cambiar de password o no */
	private boolean puedeCambiarPassword = false;
	
	/** Bandera que indica que el usuario debe de cambiar su password por que ya caduco */
	private boolean debeCambiarPassword = false;
	
	/** Campo que indica cuantos dias de vigencia le quedan al password */
	private long diasPorVencer;
	
	/** Tipo de login para el usuario */
	private int tipoAutenticacion = -1;
	
	/** Bandera que indica si se debe mostrar el captcha o no */
	private boolean mostrarCaptcha = true;

	/**  */
	private boolean loginPassword = false;
	
	
	private boolean validaIndex = true;
	
	private static final String CAMBIO_INSTITUCION_ACTUAL = "CAMBIO_INSTITUCION_ACTUAL";
	/**
	 * Obtiene el campo consultaInstitucionService
	 * 
	 * @return consultaInstitucionService
	 */
	public ConsultaInstitucionService getConsultaInstitucionService() {
		return consultaInstitucionService;
	}

	/**
	 * Asigna el valor del campo consultaInstitucionService
	 * 
	 * @param consultaInstitucionService
	 *            el valor de consultaInstitucionService a asignar
	 */
	public void setConsultaInstitucionService(ConsultaInstitucionService consultaInstitucionService) {
		this.consultaInstitucionService = consultaInstitucionService;
	}

	/**
	 * Obtiene el valor del atributo token
	 * 
	 * @return el valor del atributo token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * Establece el valor del atributo token
	 * 
	 * @param token
	 *            el valor del atributo token a establecer
	 */
	public void setToken(String token) {
		this.token = token;
	}
	
	
	
	
	/**
	 * Determina el tipo de login para el usuario que se esta firmando.
	 * @param event Evento que genera la llamada
	 */
	public String determinaLogin() {
		mensajeError = null;
		String destino = null;
		try {
			logger.debug("Determinando tipo de login para el usuario: "+usuario);
			//Valida que haya capturado usuario, password y captcha
			if(StringUtils.isNotBlank(usuario) && StringUtils.isNotBlank(password)) {
				
			

					SeguridadService seguridadExposedService = SeguridadServiceLocator.obtenerSeguridadExposedService();
					if (seguridadExposedService == null) {
						mensajeError = "No se pudo localizar el servicio de Seguridad.";
					}
					else {
						tipoAutenticacion = 
							seguridadExposedService.getTipoAutentificacionPorUsuarioSistema(usuario, SeguridadConstants.SISTEMA_ESTADO_CUENTA);
						if( tipoAutenticacion == UtilConstantes.DEFAULT_VALUE) {
							SistemaVO sistema = 
								seguridadExposedService.getSistemaWithAuthWebByNombre(SeguridadConstants.SISTEMA_ESTADO_CUENTA);
							tipoAutenticacion = sistema.getTipoAutentificacion();
						}
						
						 if( mostrarCaptcha ==true && StringUtils.isBlank(textoCaptcha)&&!getNoValidaCaptcha()){					 
							 mensajeError = obtenerMensajePropiedades("msgErrorUsrPassCaptcha");
							 tipoAutenticacion = -1;
						 }else{

							 
							 switch(tipoAutenticacion) {
								case UtilConstantes.LOGIN_PASSWORD_CERTIFICADO:
									logger.debug("Login determado para el usuario :"+usuario+" LOGIN_PASSWORD_CERTIFICADO");
									//hace paso 1 de firma digital
									
									HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance()
											.getExternalContext().getRequest();
									String ticket = seguridadExposedService.autentificarFirmaDigitalFase1(usuario, SeguridadConstants.SISTEMA_ESTADO_CUENTA, password, request.getRemoteAddr());
									setTicketSinFirmar(ticket);
									//mostrarCaptcha = true;
								break;
								case UtilConstantes.LOGIN_PASSWORD:
									logger.debug("Login determado para el usuario :"+usuario+" LOGIN_PASSWORD");
									destino = iniciarSesion();
									loginPassword = true;
									break;								
								case UtilConstantes.LOGIN_OTP:
									logger.debug("Login determado para el usuario :"+usuario+" LOGIN_OTP");
									mostrarCaptcha = false;
								default:
								break;
							}
							 
						 }
					}
					
				 
			}
			else {
				mensajeError = obtenerMensajePropiedades("msgErrorUsrPassCaptcha");
			}
		}
		
		catch (SeguridadException se) {
			String errorMessage = se.getMessage();
			logger.error( "Error de seguridad", se);
			if (StringUtils.isNotBlank(errorMessage)) {
				int tamanio = errorMessage.length();
				if (tamanio > 110) {
					errorMessage = errorMessage.substring(0, 110);
				}
				mensajeError = errorMessage;
			}
			else {
				mensajeError = "Usuario o contrase\u00F1a incorrectos.";
			}
			if (mensajeError.equalsIgnoreCase("El password del ticket esta expirado.")) {
				mensajeError = "El password ha caducado";
			}
			tipoAutenticacion = -1;
		}catch (Exception re) {
			logger.error("Error al iniciar sesi\u00F3n en el servicio web de seguridad", re);
			mensajeError = "Usuario o contrase\u00F1a incorrectos.";
			tipoAutenticacion = -1;
		}
		return destino;
	}
	
	/**
	 * Realiza el proceso de login en el servicio web de seguridad de bursate.
	 * @return Destino de la pagina de inicio del sistema, null en caso de no lograr el login
	 */
	
	public String iniciarSesion() {
		String destino = "login";
		diasPorVencer = 0;
		FacesContext ctx = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) ctx.getExternalContext().getRequest();
		
		//Obtiene la sesion, guarda sus atributos, expira la sesion, crea una nueva sesion
		//y le setea los atributos.
		HttpSession session = request.getSession(false);
		
//		Enumeration<String> atributos = session.getAttributeNames();
//		Map<String,Object> atributosSesion = new HashMap<String, Object>();
//		while (atributos.hasMoreElements()) {
//			String nombreAtributo = atributos.nextElement(); 
//			atributosSesion.put(nombreAtributo, session.getAttribute(nombreAtributo));
//		}
//		session.invalidate();
//		HttpSession nuevaSesion = request.getSession(true);
//		Iterator<Map.Entry<String, Object>> it = atributosSesion.entrySet().iterator();
//        while(it.hasNext()) {
//            Map.Entry<String, Object> ent = (Map.Entry<String, Object>)it.next();
//            nuevaSesion.setAttribute(ent.getKey(), ent.getValue());
//        }
//		
//		if (nuevaSesion.getAttribute(SeguridadConstants.USUARIO_SESION) != null) {
//			return "loginOk";
//		}
		
//		if (session.getAttribute(SeguridadConstants.USUARIO_SESION) != null) {
//			return "loginOk";
//		}
		try {
			SeguridadService seguridadExposedService = SeguridadServiceLocator.obtenerSeguridadExposedService();
			if (seguridadExposedService == null) {
				mensajeError = "No se pudo localizar el servicio de Seguridad.";
				destino = "login";
			}
			else {
				String ticket = null;
				//Pide otra vez el tipo de autenticacion para asegurarse que es correcta
				tipoAutenticacion = 
					seguridadExposedService.getTipoAutentificacionPorUsuarioSistema(usuario, SeguridadConstants.SISTEMA_ESTADO_CUENTA);
				//Valida si el tipo de autenticacion es con certificado
				if (UtilConstantes.LOGIN_PASSWORD_CERTIFICADO == tipoAutenticacion) {
					recuperarCamposFirma();
					if(StringUtils.isBlank(getTicketFirmado())) {
						return determinaLogin();
					}
					ticket = seguridadExposedService.autentificarFirmaDigitalFase2(getTicketFirmado());
				}
				//En caso contrario, realiza el login con usuario y contraseña y obtiene el ticket
				else {
					ticket = 
						seguridadExposedService.loginToken(usuario, SeguridadConstants.SISTEMA_ESTADO_CUENTA, 
							password, StringUtils.defaultIfEmpty(token, null), request.getRemoteAddr());
				}
				//Obtiene la vigencia del password
				int vigenciaPassword = validaCambioPasswordTicket(seguridadExposedService, ticket);
				//Coloca en sesion el ticket
				session.setAttribute(SeguridadConstants.TICKET_SESION, ticket);
				//Valida la vigencia
				if (vigenciaPassword == 1) {
					
					// Se carga la informacion del usuario
					cargaInfoUsuario(seguridadExposedService,ticket);
					
					addToCache(ticket,usuario+"-ticket",ticket,Long.valueOf(session.getMaxInactiveInterval()),TimeUnit.SECONDS);
					Principal userPrincipal=request.getUserPrincipal();
					if(userPrincipal!=null){
						request.logout();
					}
					
					request.login(ticket+"|"+usuario, password);
					
					logger.info("\n******* INCIANDO SESION USUARIO: "+usuario+" con ticket:"+ticket);
					
					// Esta vigente y no ha entrado al periodo de aviso de cambio de password
					puedeCambiarPassword = false;
					debeCambiarPassword = false;
					
					
					// Se dirige al index principal
					//destino = "loginOk";
					destino = null;
				} 
				else if (vigenciaPassword == 2) {
					// Esta vigente y ya esta en el periodo de aviso de cambio de password
					
					cargaInfoUsuario(seguridadExposedService,ticket);
					
					addToCache(ticket,usuario+"-ticket",ticket,Long.valueOf(session.getMaxInactiveInterval()),TimeUnit.SECONDS);
					Principal userPrincipal=request.getUserPrincipal();
					if(userPrincipal!=null){
						request.logout();
					}
					
					request.login(ticket+"|"+usuario, password);
					
					puedeCambiarPassword = true;
					debeCambiarPassword = false;
					tipoAutenticacion = -1;
					destino = null;
				} 
				else if (vigenciaPassword == 0) {
					// El password ya no esta vigente					

					debeCambiarPassword = true;
					puedeCambiarPassword = false;
					tipoAutenticacion = -1;
					destino = null;
				}
			}
		}
		
		catch (SeguridadException se) {
			mostrarCaptcha = true;
			String errorMessage = se.getMessage();
			logger.error( "Error de seguridad", se);
			if (StringUtils.isNotBlank(errorMessage)) {
				int tamanio = errorMessage.length();
				if (tamanio > 110) {
					errorMessage = errorMessage.substring(0, 110);
				}
				mensajeError = errorMessage;
			}
			else {
				mensajeError = "Usuario o contrase\u00F1a incorrectos.";
			}
			destino = "login";
			tipoAutenticacion = -1;
			if (mensajeError.equalsIgnoreCase("El password del ticket esta expirado.")) {
				mensajeError = "El password ha caducado";
			}
			else if (mensajeError.equalsIgnoreCase("El ticket {0} no existe.")) {
				mensajeError = null;
				destino = determinaLogin();
			}
		}catch (Exception re) {
			logger.error("Error al iniciar sesi\u00F3n en el servicio web de seguridad", re);
			mostrarCaptcha = true;
			mensajeError = "Usuario o contrase\u00F1a incorrectos.";
			destino = "login";
			tipoAutenticacion = -1;
		}
		return destino;
	}
	
	
	public String getInit()  {
		
		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		HttpSession session = request.getSession(false);
		
		
		String ticket = (String) session.getAttribute(SeguridadConstants.TICKET_SESION);
		Principal userPrincipal=request.getUserPrincipal();
		// Lógica para detectar que el login viene del otro portal y que este cargue los valores en sesion
		if(ticket==null&&userPrincipal!=null){
				String userData[]=userPrincipal.getName().split("\\|");
				if(userData.length>1){
					ticket = userData[0];
					String userName= userData[1];
					if( getFromCache(ticket,userName+"-ticket")!=null){
						try {
							cargaInfoUsuario(SeguridadServiceLocator.obtenerSeguridadExposedService(),ticket);
						} catch (Exception e) {
							logout();
							return null;
						}
					}
					
				}
		}
		
		InstitucionVO institucionActual=(InstitucionVO)session.getAttribute(SeguridadConstants.INSTITUCION_VO_ACTUAL);		
		// Lógica para detectar un cambio de institucion y que hace un cambio de portal y que este actualice la institución recargando valores en sesion
		if(getFromCache(ticket,CAMBIO_INSTITUCION_ACTUAL)!=null&&institucionActual!=null&&!getFromCache(ticket,CAMBIO_INSTITUCION_ACTUAL).equalsIgnoreCase(institucionActual.getClave()+institucionActual.getFolio())){
				try {
					cargaInfoUsuario(SeguridadServiceLocator.obtenerSeguridadExposedService(),ticket);
				} catch (Exception e) {
					logout();
					return null;
				}
				
		}		
		return null;
	}

	public void cargaInfoUsuario(SeguridadService seguridadExposedService,String ticket) throws SeguridadException {
		

		if (seguridadExposedService == null) {
			throw new SeguridadException("No se puedo localizar el servicio de Seguridad");
		} else {
			FacesContext ctx = FacesContext.getCurrentInstance();

			HttpServletRequest request = (HttpServletRequest) ctx.getExternalContext().getRequest();

			HttpSession session = request.getSession(false);

			TicketInfoVO ticketInfo = seguridadExposedService.getTicketInfo(ticket);
			
			String usuario = ticketInfo.getClaveUsuario();

			String requestURI = request.getRequestURI().replaceAll(request.getContextPath() + "/", StringUtils.EMPTY);
			
			logger.info("\n******* cargaInfoUsuario ********"+
					 "\n usuario: "+usuario+" ticket:" + ticket +
					 "\n desde :"+requestURI+
					 "\n*********************************");
			mensajeError = null;

		
			Usuario usuarioSegu = seguridadExposedService.getUsuario(usuario);

			InstitucionVO institucionPrimaria = seguridadExposedService.getInstitucionPorPerfil(ticket);

			List<InstitucionVO> instituciones = seguridadExposedService.getInstitucionesAsignadasByClaveUsuarioNombreSistema(ticket, usuario, SeguridadConstants.SISTEMA_ESTADO_CUENTA);

			InstitucionVO[] insts = instituciones.toArray(new InstitucionVO[]{});

			Arrays.sort(insts, new InstitucionComparator());

			UsuarioDTO user = new UsuarioDTO();
			user.setNombre(usuarioSegu.getNombre());
			user.setApellidoPaterno(usuarioSegu.getApellidoPaterno());
			user.setApellidoMaterno(usuarioSegu.getApellidoMaterno());
			if(StringUtils.isNotBlank(usuarioSegu.getNumeroSerieCertificado())){			
				user.setNumeroSerieCertificado(usuarioSegu.getNumeroSerieCertificado());
			}else{
				user.setNumeroSerieCertificado(SERIAL_UNO);
			}

			user.setClaveUsuario(usuario);
			user.setInstitucionPrimaria(institucionPrimaria);
			user.setInstituciones(insts);
			InstitucionDTO institucionSesion = new InstitucionDTO();

			institucionSesion = consultaInstitucionService.buscarInstitucionPorClaveYFolio(institucionPrimaria.getClave() + institucionPrimaria.getFolio());

			institucionSesion.setId(institucionPrimaria.getId());
			institucionSesion.setFolioInstitucion(institucionPrimaria.getFolio());
			institucionSesion.setClaveTipoInstitucion(institucionPrimaria.getClave());
			institucionSesion.setNombreCorto(institucionPrimaria.getNombre());

			// Si la institución primaria es indeval colocar los datos
			// necesarios
			if (SeguridadConstants.NOMBRE_INSTITUCION_INDEVAL.equals(institucionSesion.getNombreCorto())) {
				institucionSesion.setId(SeguridadConstants.ID_INSTITUCION_INDEVAL);
			}

			if (institucionSesion == null) {
				// Verificar la existencia de la institucuión en la BD
			} else {
				session.setAttribute(SeguridadConstants.INSTITUCION_ACTUAL, institucionSesion);
				session.setAttribute(SeguridadConstants.INSTITUCION_VO_ACTUAL, institucionPrimaria);
			}
			session.setAttribute(SeguridadConstants.TICKET_SESION, ticket);
			session.setAttribute(SeguridadConstants.USUARIO_SESION, user);
			session.setAttribute(SeguridadConstants.INSTITUCIONES_DISPONIBLES, insts);
			// Busca los mensajes del usuario del SMS
			if (avisosService != null) {
				buscarMensajesDeUsuario();
			}

			//ticket = (String) session.getAttribute(SeguridadConstants.TICKET_SESION);
			String rolIndeval = "INDEVAL";
			RespuestaVO respuesta = null;

			boolean autorizadoRolIndeval = false;

			respuesta = seguridadExposedService.autorizaRol(ticket, request.getRemoteAddr(), rolIndeval);

			if (respuesta != null && respuesta.getTipoRespuesta() != null) {
				autorizadoRolIndeval = SeguridadConstants.TIPO_RESPUESTA_AUTORIZADO.equals(respuesta.getTipoRespuesta().toLowerCase());
				//subir a session la respuesta
				if (autorizadoRolIndeval) {
					session.setAttribute("ROLINDEVAL", autorizadoRolIndeval);
				} else {
					session.setAttribute("ROLINDEVAL", autorizadoRolIndeval);
				}
			}
			
			ElementoMenu menuUsuario = MenuNavegacionHelper.obtenerMenuNavegacionDeUsuario(elementoMenuRaiz, ticket);
			session.setAttribute(MenuNavegacionConstants.MENU_NAVEGACION_SESSION_KEY, menuUsuario);

			
			
		}
	}

	public String navegaIndex() {
		/*String destino = null;
		SeguridadService seguridadExposedService = SeguridadServiceLocator
				.obtenerSeguridadExposedService();
		FacesContext ctx = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) ctx
				.getExternalContext().getRequest();
		HttpSession session = request.getSession(false);
		
		String ticket = (String) session
				.getAttribute(SeguridadConstants.TICKET_SESION);
		if(ticket!=null){
			try {
				cargaInfoUsuario( seguridadExposedService, ticket);
				destino = "inicio";
	
			} catch (SeguridadException se) {
				String errorMessage = se.getMessage();
				LogFactory.getLog(InicioSesionBean.class).error("Error de seguridad", se);
				if (StringUtils.isNotBlank(errorMessage)) {
					int tamanio = errorMessage.length();
					if (tamanio > 110) {
						errorMessage = errorMessage.substring(0, 110);
					}
					mensajeError = errorMessage;
				} else {
					mensajeError = "Usuario o contrase\u00F1a incorrectos.";
				}
				if (mensajeError.equalsIgnoreCase("El password del ticket esta expirado.")) {
					mensajeError = "El password ha caducado";
				}
				destino = null;
			}
		}else{
			validaIndex=false;
		}
		return destino;*/
		return "inicioNavegaIndex";
		
	}
	
	
	public String toChangePassword() {
		return "changePasswordXWTY";
	}
	
	
	public String toChangePasswordRedirect() {
		return "changePasswordRedirect";
	}

	/**
	 * Metodo para validar la fecha de vencimiento de password
	 * @param seguridadExposedService Servicio de la seguridad
	 * @param ticket Ticket de la sesion
	 * @return 0 si el password ya esta vencido, 1 si esta vigente y todavia no entra a los dias de aviso, y 2 si esta vigente y
	 *			ya esta dentro de los dias de aviso del password
	 * @throws SeguridadException En case de error de la seguridad de Bursatec
	 * @throws RemoteException En case de error en el llamado al EJB
	 */
	private int validaCambioPasswordTicket(SeguridadService seguridadExposedService, String ticket)
			throws SeguridadException, RemoteException {
		int retorno = 0;
		// Se valida vigencia del password del usuario
		TicketInfoVO ticketInfo = seguridadExposedService.getTicketInfo(ticket);
		if (ticketInfo != null) {
			// Fecha de vencimiento
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(ticketInfo.getFechaVerificacionPassword());
			calendar.add(Calendar.DAY_OF_MONTH, new Long(ticketInfo.getDiasVigenciaPassword()).intValue());
			calendar = DateUtil.preparaFechaConExtremoEnSegundos(calendar, true);
			// Fecha Actual
			Calendar now = Calendar.getInstance();
			now = DateUtil.preparaFechaConExtremoEnSegundos(now, true);
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
			logger.debug("Dias de vigencia: [" + ticketInfo.getDiasVigenciaPassword() + "]");
			logger.debug("Fecha de verificacion: [" + sdf.format(ticketInfo.getFechaVerificacionPassword()) + "]");
			logger.debug("Fecha de vencimiento: [" + sdf.format(calendar.getTime()) + "]");
			logger.debug("Fecha actual: [" + sdf.format(now.getTime()) + "]");
			if (calendar.compareTo(now) > 0) {
				long differenciaDias = DateUtil.getPlazo(now, calendar);
				logger.debug("Dias de aviso de password: [" + ticketInfo.getDiasAvisoPassword() + "]");
				calendar.add(Calendar.DAY_OF_MONTH, new Long(ticketInfo.getDiasAvisoPassword()).intValue() * -1);
				logger.debug("Fecha de aviso de passowrd: [" + sdf.format(calendar.getTime()) + "]");
				//verificar si se debe notificar que el password va a vencer
				if (calendar.compareTo(now) <= 0) {
					diasPorVencer = differenciaDias;
					retorno = 2;
				} else {
					retorno = 1;
				}
			} else {
				retorno = 0;
			}
		}
		return retorno;
	}

	/**
	 * Obtiene el valor del atributo mensajesUsuario
	 * 
	 * @return el valor del atributo mensajesUsuario
	 */
	@SuppressWarnings("unchecked")
	public List<MensajeSmsVO> getMensajesUsuario() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		HttpSession session = request.getSession(false);
		return (List<MensajeSmsVO>) session.getAttribute(MENSAJES_SESSION_KEY);
	}

	/**
	 * Limpia los mensajes del usuario.
	 * 
	 * @return este método siempre retorna <code>null</code>.
	 */
	@SuppressWarnings("unchecked")
	public String getLimpiarMensajes() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		HttpSession session = request.getSession(false);
		UsuarioDTO user = (UsuarioDTO) session.getAttribute(SeguridadConstants.USUARIO_SESION);
		for (MensajeSmsVO mensaje : (List<MensajeSmsVO>) session.getAttribute(MENSAJES_SESSION_KEY)) {
			try {
				avisosService.informMessageRead(user.getClaveUsuario(), mensaje.getIdMensaje());			
			} catch (SmsException e) {
				logger.error(e.getMessage(), e);
			} finally {
				session.setAttribute(MENSAJES_SESSION_KEY, null);
			}
		}

		return null;
	}

	/**
	 * Obtiene el valor del atributo delay
	 * 
	 * @return el valor del atributo delay
	 */
	public long getDelay() {
		return delay;
	}

	/**
	 * Establece el valor del atributo delay
	 * 
	 * @param delay
	 *            el valor del atributo delay a establecer
	 */
	public void setDelay(long delay) {
		this.delay = delay;
	}

	/**
	 * Obtiene el tamaño de la lista mensajesUsuario
	 * 
	 * @return el tamaño de la lista mensajesUsuario
	 */
	@SuppressWarnings("unchecked")
	public int getNumeroMensajesUsuario() {
		int tamanio = 0;
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		HttpSession session = request.getSession(false);
		List<MensajeSmsVO> mensajes = (List<MensajeSmsVO>) session.getAttribute(MENSAJES_SESSION_KEY);
		if (mensajes != null && !mensajes.isEmpty()) {
			tamanio = mensajes.size();
		}
		return tamanio;
	}

	/**
	 * Escribe el Captcha generado
	 * 
	 * @param g2d
	 *            Objeto de grficos
	 * @param Objeto
	 *            que representa el ID generado para el captcha
	 */
	public void paint(Graphics2D g2d, Object id) {

		try {
			BufferedImage secureImage = generarCaptcha(id.toString());
			g2d.setClip(0, 0, secureImage.getWidth(), secureImage.getHeight());
			g2d.drawImage(secureImage, 0, 0, null);
		} catch (Exception ex) {
			logger.error("Error al generar la imagen para captcha con el id:" + id, ex);
		}
	}

	/**
	 * Genera un objeto que representa la imagen del captcha
	 * 
	 * @return Objeto con la representación de la imagen
	 */
	protected BufferedImage generarCaptcha(String id) {
		BufferedImage imagen = null;

		CaptchaService service = CaptchaServiceLocator.obtenerCaptchaService();
		if (service != null) {
			imagen = service.generateCaptchaForId(id).getImage();
		}

		return imagen;
	}

	/**
	 * Obtiene el menú de navegación asociado con el usuario en sesi\u00F3n.
	 * 
	 * @return el menú de navegaci\u00F3n asociado con el usuario en
	 *         sesi\u00F3n.
	 */
	public ElementoMenu getMenuUsuario() {
		FacesContext ctx = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) ctx.getExternalContext().getRequest();
		HttpSession session = request.getSession(false);

		return (ElementoMenu) session.getAttribute(MenuNavegacionConstants.MENU_NAVEGACION_SESSION_KEY);
	}

	/**
	 * Genera el ID del captcha a mostrar
	 * 
	 * @return
	 */
	public Object getCaptchaId() {

		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

		return request.getAttribute(SeguridadConstants.CAPTCHA_GENERATED_ID);
	}

	/**
	 * Verifica si existen mensajes para el usuario en sesión.
	 * 
	 * @return la lista de mensajes para el usuario en sesión. Una lista vacía
	 *         si no existen mensajes.
	 */
	@SuppressWarnings("unchecked")
	public void buscarMensajesDeUsuario() {
		List<MensajeSmsVO> mensajesUsuario = new ArrayList<MensajeSmsVO>();
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		HttpSession session = request.getSession(false);
		UsuarioDTO user = (UsuarioDTO) session.getAttribute(SeguridadConstants.USUARIO_SESION);
		if (StringUtils.isNotBlank(user.getClaveUsuario())) {
			try {
				mensajesUsuario = avisosService.getMessages(user.getClaveUsuario());
				session.setAttribute(MENSAJES_SESSION_KEY, mensajesUsuario);

			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	/**
	 * Realiza el proceso de logout en el WebService de seguridad de bursatec e
	 * invalida la sesión actual del usuario.
	 * 
	 * @return
	 */
	public String logout() {
		FacesContext ctx = FacesContext.getCurrentInstance();

		HttpServletRequest request = (HttpServletRequest) ctx.getExternalContext().getRequest();
		
		HttpSession session = request.getSession(false);
		
		String ticket = null;

		String usuario = null;

		try {
			ticket = (String) session.getAttribute(SeguridadConstants.TICKET_SESION);
			if(ticket!=null){
				
				TicketInfoVO ticketInfo = SeguridadServiceLocator.obtenerSeguridadExposedService().getTicketInfo(ticket);
				
				usuario = ticketInfo.getClaveUsuario();
				
				logger.info("******* CERRANDO SESION DE USUARIO: "+usuario+" con ticket:"+ticket+" EN SEGURIDAD");
				
				SeguridadServiceLocator.obtenerSeguridadExposedService().logout(ticket);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(ticket!=null){
				try{
					logger.info("******* BORRANDO DE CACHE DISTRIBUIDO SESION DE USUARIO: "+usuario+" con ticket:"+ticket+" EN SEGURIDAD");
					removeFromCache(ticket.concat("|").concat("CAMBIO_INSTITUCION_ACTUAL"));
					if(request.getUserPrincipal()!=null)
						removeFromCache(request.getUserPrincipal().getName()+"-ticket");
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			try{
					logger.info("******* INVALIDANDO SESION DE USUARIO: "+usuario+" con ticket:"+ticket);
					session.invalidate();
			}catch(Exception e){
					e.printStackTrace();
			}
			try {
					logger.info("******* HACIENDO LOGOUT DE USUARIO: "+usuario+" con ticket:"+ticket);
					request.logout();
			} catch (ServletException e) {				
					e.printStackTrace();
			}
			
			logger.info("******* TERMINANDO LA SESION DE USUARIO: "+usuario+" con ticket:"+ticket);
			
		}
		
		return "login";
	}

	public boolean isMensajesCaptcha() {
		return FacesContext.getCurrentInstance().getMessages().hasNext();
	}

	/**
	 * Obtiene el campo elementoMenuRaiz
	 * 
	 * @return elementoMenuRaiz
	 */
	public ElementoMenu getElementoMenuRaiz() {
		return elementoMenuRaiz;
	}

	/**
	 * Asigna el campo elementoMenuRaiz
	 * 
	 * @param elementoMenuRaiz
	 *            el valor de elementoMenuRaiz a asignar
	 */
	public void setElementoMenuRaiz(ElementoMenu elementoMenuRaiz) {
		this.elementoMenuRaiz = elementoMenuRaiz;
	}

	/**
	 * Obtiene el campo usuario
	 * 
	 * @return usuario
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * Asigna el valor del campo usuario
	 * 
	 * @param usuario
	 *            el valor de usuario a asignar
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * Obtiene el campo password
	 * 
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Asigna el valor del campo password
	 * 
	 * @param password
	 *            el valor de password a asignar
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Obtiene el campo mensajeError
	 * 
	 * @return mensajeError
	 */
	public String getMensajeError() {
		
		FacesContext facesContext = FacesContext.getCurrentInstance();
		
//		Map<String, String> parameters = facesContext.getExternalContext().getRequestParameterMap();
//		
//		mensajeError = parameters.get("loginForm:mensajeError");
		
		HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
		
		if(request.getAttribute("Mensaje")!=null) {
			mensajeError=(String)request.getAttribute("Mensaje");
		}
		
		 
		return mensajeError;
	}

	/**
	 * Asigna el valor del campo mensajeError
	 * 
	 * @param mensajeError
	 *            el valor de mensajeError a asignar
	 */
	public void setMensajeError(String mensajeError) {
		
			this.mensajeError = mensajeError;
		
	}

	/**
	 * Obtiene el campo textoCaptcha
	 * 
	 * @return textoCaptcha
	 */
	public String getTextoCaptcha() {
		return textoCaptcha;
	}

	/**
	 * Asigna el valor del campo textoCaptcha
	 * 
	 * @param textoCaptcha
	 *            el valor de textoCaptcha a asignar
	 */
	public void setTextoCaptcha(String textoCaptcha) {
		this.textoCaptcha = textoCaptcha;
	}

	/**
	 * Obtiene el valor del atributo avisosService
	 * 
	 * @return el valor del atributo avisosService
	 */
	public AvisosService getAvisosService() {
		return avisosService;
	}

	/**
	 * Establece el valor del atributo avisosService
	 * 
	 * @param avisosService
	 *            el valor del atributo avisosService a establecer
	 */
	public void setAvisosService(AvisosService avisosService) {
		this.avisosService = avisosService;
	}

	/**
	 * Encriptador para el ticket de la sesion
	 * @return the stringEncryptor
	 */
	public PBEStringEncryptor getStringEncryptor() {
		return stringEncryptor;
	}

	/**
	 * Encriptador para el ticket de la sesion
	 * @param stringEncryptor the stringEncryptor to set
	 */
	public void setStringEncryptor(PBEStringEncryptor stringEncryptor) {
		this.stringEncryptor = stringEncryptor;
	}

	/**
	 * Bandera que indica si el usuario puede cambiar de password o no
	 * @return the puedeCambiarPassword
	 */
	public boolean isPuedeCambiarPassword() {
		return puedeCambiarPassword;
	}

	/**
	 * Bandera que indica si el usuario puede cambiar de password o no
	 * @param puedeCambiarPassword the puedeCambiarPassword to set
	 */
	public void setPuedeCambiarPassword(boolean puedeCambiarPassword) {
		this.puedeCambiarPassword = puedeCambiarPassword;
	}

	/**
	 * Bandera que indica que el usuario debe de cambiar su password por que ya caduco
	 * @return the debeCambiarPassword
	 */
	public boolean isDebeCambiarPassword() {
		return debeCambiarPassword;
	}

	/**
	 * Bandera que indica que el usuario debe de cambiar su password por que ya caduco
	 * @param debeCambiarPassword the debeCambiarPassword to set
	 */
	public void setDebeCambiarPassword(boolean debeCambiarPassword) {
		this.debeCambiarPassword = debeCambiarPassword;
	}

	/**
	 * Campo que indica cuantos dias de vigencia le quedan al password
	 * @return the diasPorVencer
	 */
	public long getDiasPorVencer() {
		return diasPorVencer;
	}

	/**
	 * Campo que indica cuantos dias de vigencia le quedan al password
	 * @param diasPorVencer the diasPorVencer to set
	 */
	public void setDiasPorVencer(long diasPorVencer) {
		this.diasPorVencer = diasPorVencer;
	}

	/**
	 * Mensaje de error
	 * @return the mensajeError2
	 */
	public String getMensajeError2() {
		return mensajeError2;
	}

	/**
	 * Mensaje de error
	 * @param mensajeError2 the mensajeError2 to set
	 */
	public void setMensajeError2(String mensajeError2) {
		this.mensajeError2 = mensajeError2;
	}

	/**
	 * @return the tipoAutenticacion
	 */
	public int getTipoAutenticacion() {
		return tipoAutenticacion;
	}

	/**
	 * @param tipoAutenticacion the tipoAutenticacion to set
	 */
	public void setTipoAutenticacion(int tipoAutenticacion) {
		this.tipoAutenticacion = tipoAutenticacion;
	}

	/**
	 * @return the mostrarCaptcha
	 */
	public boolean isMostrarCaptcha() {
		return mostrarCaptcha;
	}

	/**
	 * @param mostrarCaptcha the mostrarCaptcha to set
	 */
	public void setMostrarCaptcha(boolean mostrarCaptcha) {
		this.mostrarCaptcha = mostrarCaptcha;
	}
	
	/**
	 * @return the loginPassword
	 */
	public boolean isLoginPassword() {
		return loginPassword;
	}

	/**
	 * @param loginPassword the loginPassword to set
	 */
	public void setLoginPassword(boolean loginPassword) {
		this.loginPassword = loginPassword;
	}
	
	
	public boolean isValidaIndex() {
		return validaIndex;
	}
	
	public boolean isTieneSesion() {
		
		FacesContext ctx = FacesContext.getCurrentInstance();
		
		HttpServletRequest request = (HttpServletRequest) ctx.getExternalContext().getRequest();
		
		HttpSession session = request.getSession(false);
		
		return session.getAttribute(SeguridadConstants.USUARIO_SESION) != null;
		
	}
	
	
	
	public Boolean getNoValidaCaptcha() {
		try {
			
			
			HttpServletRequest request = (HttpServletRequest) FacesContext
	                .getCurrentInstance().getExternalContext().getRequest();
			
			String usuario=request.getParameter("loginForm:usuario");
		
			return SeguridadServiceLocator.obtenerSeguridadExposedService().isNoValidaCaptchaPorUsuarioSistema(usuario, SeguridadConstants.SISTEMA_ESTADO_CUENTA);
			
		}catch (SeguridadException se) {			
			logger.error( "Error de seguridad", se);			
		}
		return Boolean.FALSE;
	}
}
/**
 * Comparator para ordenar las instituciones por clave y folio
 * 
 * @author emigdio
 * 
 */
class InstitucionComparator implements Comparator<InstitucionVO> {

	public int compare(InstitucionVO o1, InstitucionVO o2) {
		return (o1.getClave() + o1.getFolio()).compareTo(o2.getClave() + o2.getFolio());
	}
}
