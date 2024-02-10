/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * InicioSesionBean.java
 * 04/03/2008
 */
package com.indeval.portalinternacional.presentation.controller.seguridad;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.rmi.RemoteException;
import java.security.Principal;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO;
import com.indeval.portaldali.persistence.dao.common.InstitucionDao;
import com.indeval.portaldali.persistence.modelo.Institucion;
import com.indeval.portaldali.presentation.jsf.menunavegacion.constants.MenuNavegacionConstants;
import com.indeval.portaldali.presentation.jsf.menunavegacion.vo.ElementoMenu;
import com.indeval.portalinternacional.common.util.MenuNavegacionHelper;

/**
 * Backing bean para el inicio de sesion de un usuario
 * 
 * @author Emigdio
 * 
 */
public class InicioSesionBean extends InicioSesionFirmaDigital {

	/** El elemento del menu que representa la raiz del menu de navegacion */
	private ElementoMenu elementoMenuRaiz = null;

	/**
	 * Mensaje de error
	 */
	private String mensajeError = null;
	/**
	 * User
	 */
	private String usuario = null;
	/**
	 * Password
	 */
	private String password = null;
	/**
	 * Texto del captcha capturado
	 */
	private String textoCaptcha = "";

	/** Tipo de login para el usuario */
	private int tipoAutenticacion = -1;
	
	private InstitucionDao institucionDao;

	/** Bandera que indica si se debe mostrar el captcha o no */
	private boolean mostrarCaptcha = true;
	
	/**  */
	private boolean loginPassword = false;
	
	/** Log de la clase */
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	/**
	 * Determina el tipo de login para el usuario que se esta firmando.
	 * @param event Evento que genera la llamada
	 */
	public String determinaLogin() {
		mensajeError = null;
		String destino = null;
		try {
			log.debug("Determinando tipo de login para el usuario: "+usuario);
			//Valida que haya capturado usuario, password y captcha
			if(StringUtils.isNotBlank(usuario) && StringUtils.isNotBlank(password)) {
				
				 if( mostrarCaptcha ==true && StringUtils.isBlank(textoCaptcha)){
					 mensajeError = obtenerMensajePropiedades("msgErrorUsrPassCaptcha");
				 }else{

					SeguridadService seguridadExposedService = SeguridadServiceLocator.obtenerSeguridadExposedService();
					if (seguridadExposedService == null) {
						mensajeError = "No se pudo localizar el servicio de Seguridad.";
					}
					else {
						tipoAutenticacion = 
							seguridadExposedService.getTipoAutentificacionPorUsuarioSistema(usuario, SeguridadConstants.SISTEMA_ESTADO_CUENTA);
						if( tipoAutenticacion == SeguridadConstants.DEFAULT_VALUE) {
							SistemaVO sistema = 
								seguridadExposedService.getSistemaWithAuthWebByNombre(SeguridadConstants.SISTEMA_ESTADO_CUENTA);
							tipoAutenticacion = sistema.getTipoAutentificacion();
						}
						switch(tipoAutenticacion) {
							case SeguridadConstants.LOGIN_PASSWORD_CERTIFICADO:
								log.debug("Login determado para el usuario :"+usuario+" LOGIN_PASSWORD_CERTIFICADO");
								//hace paso 1 de firma digital
								FacesContext ctx = FacesContext.getCurrentInstance();
								HttpServletRequest request = (HttpServletRequest) ctx
										.getExternalContext().getRequest();
								String ticket = seguridadExposedService.autentificarFirmaDigitalFase1(usuario, SeguridadConstants.SISTEMA_ESTADO_CUENTA, password, request.getRemoteAddr());
								setTicketSinFirmar(ticket);
//								mostrarCaptcha = true;
							break;
							case SeguridadConstants.LOGIN_PASSWORD:
								log.debug("Login determado para el usuario :"+usuario+" LOGIN_PASSWORD");
								destino = iniciarSesion();
								loginPassword = true;
								break;
							case SeguridadConstants.LOGIN_OTP:
								log.debug("Login determado para el usuario :"+usuario+" LOGIN_OTP");
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
			log.error( "Error de seguridad", se);
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
			LoggerFactory.getLogger(InicioSesionBean.class)
			.error("Error al iniciar sesi\u00F3n en el servicio web de seguridad", re);
			mensajeError = "Usuario o contrase\u00F1a incorrectos.";
			tipoAutenticacion = -1;
		}
		return destino;
	}
	
	/**
	 * Realiza el proceso de login en el servicio web de seguridad de bursate.
	 * 
	 * @return Destino de la pagina de inicio del sistema, null en caso de no
	 *         lograr el login
	 */
	
	public String iniciarSesion() {
		String destino = null;
		FacesContext ctx = FacesContext.getCurrentInstance();

		HttpServletRequest request = (HttpServletRequest) ctx
				.getExternalContext().getRequest();
		HttpSession session = request.getSession(false);
		HttpServletResponse response = (HttpServletResponse) ctx
				.getExternalContext().getResponse();

		if (session.getAttribute(SeguridadConstants.USUARIO_SESION) != null) {
			return "loginOk";
		}

		try {
			SeguridadService seguridadExposedService = SeguridadServiceLocator
					.obtenerSeguridadExposedService();
			if (seguridadExposedService == null) {
				mensajeError = "No se pudo localizar el servicio de Seguridad.";
				destino = "login";
			} else {
				String ticket = seguridadExposedService.loginToken(usuario,
						SeguridadConstants.SISTEMA_ESTADO_CUENTA, password,
						null, request.getRemoteAddr());
				
				
				
				mensajeError = null;

				cargarInfoUsuario(seguridadExposedService, ticket);
				
				addToCache(ticket,usuario+"-ticket",ticket,Long.valueOf(session.getMaxInactiveInterval()),TimeUnit.SECONDS);
				Principal userPrincipal=request.getUserPrincipal();
				if(userPrincipal!=null){
					request.logout();
				}
				
				request.login(ticket+"|"+usuario, password);
				log.info("******* INCIANDO SESION USUARIO: "+usuario+" con ticket:"+ticket);
				destino = "loginOk";
			}
		} catch (SeguridadException se) {
			mensajeError = se.getMessage();
			destino = "login";
		}catch (Exception re) {
			log.error(
					"Error al iniciar sesion en el servicio web de seguridad",
					re);
			mensajeError = mensajeError + re.getMessage();
			if (mensajeError.toLowerCase().indexOf(
					SeguridadConstants.TICKET_EXPIRADO) != -1) {
				// El ticket ha expirado
				session.invalidate();
				try {
					ctx.responseComplete();
					response.sendRedirect(request.getContextPath());
				} catch (Exception e) {
					log.error(
							"Error, el ticket ha expirado", re);
				}
			}
			destino = "login";
		} 

		return destino;
	}

	
	public String getInit() throws RemoteException, SeguridadException, ServletException {
		
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
						cargarInfoUsuario(SeguridadServiceLocator.obtenerSeguridadExposedService(),ticket);
					} catch (SeguridadException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		InstitucionVO institucionActual=(InstitucionVO)session.getAttribute(SeguridadConstants.INSTITUCION_VO_ACTUAL);		
		// Lógica para detectar un cambio de institucion y que hace un cambio de portal y que este actualice la institución recargando valores en sesion
		if(getFromCache(ticket,"CAMBIO_INSTITUCION_ACTUAL")!=null&&institucionActual!=null&&!getFromCache(ticket,"CAMBIO_INSTITUCION_ACTUAL").equalsIgnoreCase(institucionActual.getClave()+institucionActual.getFolio())){
				try {
					cargarInfoUsuario(SeguridadServiceLocator.obtenerSeguridadExposedService(),ticket);
				} catch (SeguridadException e) {
					e.printStackTrace();
				}
				
		}
		return null;
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	public void cargarInfoUsuario(SeguridadService seguridadExposedService,String ticket ) throws SeguridadException, ServletException{
		FacesContext ctx = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) ctx
				.getExternalContext().getRequest();
		HttpSession session = request.getSession(false);
		
		TicketInfoVO ticketInfo = seguridadExposedService.getTicketInfo(ticket);
		
		usuario = ticketInfo.getClaveUsuario();
		
		
		
		InstitucionVO institucionPrimaria = seguridadExposedService
				.getInstitucionPorPerfil(ticket);

		
		List<InstitucionVO> instituciones = seguridadExposedService.
				getInstitucionesAsignadasByClaveUsuarioNombreSistema(ticket, usuario, SeguridadConstants.SISTEMA_ESTADO_CUENTA);

		InstitucionVO[] insts = instituciones
				.toArray(new InstitucionVO[] {});

		Arrays.sort(insts, new InstitucionComparator());

        Usuario usuarioSegu = seguridadExposedService.getUsuario(usuario);
		UsuarioDTO user = new UsuarioDTO();
		user.setClaveUsuario(usuario);
		user.setInstitucionPrimaria(institucionPrimaria);
		user.setInstituciones(insts);
        user.setNombre(usuarioSegu.getNombre());
        user.setApellidoPaterno(usuarioSegu.getApellidoPaterno());
        user.setApellidoMaterno(usuarioSegu.getApellidoMaterno());
        user.setNumeroSerieCertificado(usuarioSegu.getNumeroSerieCertificado());
        user.setNumeroSerieCertificado(usuarioSegu.getNumeroSerieCertificado());
		AgenteVO agente = new AgenteVO();

		if (institucionPrimaria != null) {
			
			Institucion institucionBD = institucionDao.findInstitucionById(institucionPrimaria.getId());
			
			agente.setId(institucionPrimaria.getClave().toString());
			agente.setFolio(institucionPrimaria.getFolio());
			agente.setNombreCorto(institucionPrimaria.getNombre());
			agente.setRazon(institucionBD.getRazonSocial());
			agente.setFirmado(true);

			session.setAttribute(SeguridadConstants.INSTITUCION_ACTUAL,
					agente);
			session.setAttribute(
					SeguridadConstants.INSTITUCION_VO_ACTUAL,
					institucionPrimaria);
		}

		session.setAttribute(SeguridadConstants.USUARIO_SESION, user);
		session.setAttribute(SeguridadConstants.TICKET_SESION, ticket);
		session.setAttribute(
				SeguridadConstants.INSTITUCIONES_DISPONIBLES, insts);
		
		List<String> roles = null;
		Set<String> facultades = null;		
		roles = seguridadExposedService.getNombresRolesPorPerfil(ticket);			
		facultades = seguridadExposedService.getFacultadesSimples(ticket);
				
		
		HashMap<String, String> mapFacultades= new  HashMap<String, String>();
		HashMap<String, String> mapRoles= new  HashMap<String, String>();
		
		if (roles != null){
			for (String elem : roles) {
				mapRoles.put(elem, elem);				
			}
		}
		
		if(facultades!=null){				
			for (String elem : facultades) {
				mapFacultades.put(elem, elem);
			}		
		}	
		
		session.removeAttribute(SeguridadConstants.ROLES_SESION);
		session.setAttribute(SeguridadConstants.ROLES_SESION, mapRoles);
		session.removeAttribute(SeguridadConstants.FACULTADES_SESION);
		session.setAttribute(SeguridadConstants.FACULTADES_SESION, mapFacultades);
		

		// verificar el rol indeval

		ticket = (String) session
				.getAttribute(SeguridadConstants.TICKET_SESION);
		String rolIndeval = "INDEVAL";
		RespuestaVO respuesta = null;

		boolean autorizadoRolIndeval = false;

		respuesta = seguridadExposedService.autorizaRol(ticket, request
				.getRemoteAddr(), rolIndeval);

		if (respuesta != null && respuesta.getTipoRespuesta() != null) {
			autorizadoRolIndeval = SeguridadConstants.TIPO_RESPUESTA_AUTORIZADO
					.equals(respuesta.getTipoRespuesta().toLowerCase());
			// subir a session la respuesta
			if (autorizadoRolIndeval) {
				session
						.setAttribute("ROLINDEVAL",
								autorizadoRolIndeval);
			} else {
				session
						.setAttribute("ROLINDEVAL",
								autorizadoRolIndeval);
			}
		}
		
		
		ElementoMenu menuUsuario = MenuNavegacionHelper
				.obtenerMenuNavegacionDeUsuario(elementoMenuRaiz,
						usuario, ticket);
		session.setAttribute(
				MenuNavegacionConstants.MENU_NAVEGACION_SESSION_KEY,
				menuUsuario);

		
	}
	
	/**
	 * Escribe el Captcha generado
	 * 
	 * @param g2d
	 *            Objeto de graficos
	 * @param Objeto
	 *            que representa el ID generado para el captcha
	 */
	public void paint(Graphics2D g2d, Object id) {

		try {
			BufferedImage secureImage = generarCaptcha(id.toString());
			g2d.setClip(0, 0, secureImage.getWidth(), secureImage.getHeight());
			g2d.drawImage(secureImage, 0, 0, null);
		} catch (Exception ex) {
			log.error(
					"Error al generar la imagen para captcha con el id:" + id,
					ex);
		}
	}

	/**
	 * Genera un objeto que representa la imagen del captcha
	 * 
	 * @return Objeto con la representacion de la imagen
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
	 * Obtiene el menu de navegacion asociado con el usuario en sesion.
	 * 
	 * @return el menu de navegacion asociado con el usuario en sesion.
	 */
	public ElementoMenu getMenuUsuario() {
		FacesContext ctx = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) ctx
				.getExternalContext().getRequest();
		HttpSession session = request.getSession(false);

		return (ElementoMenu) session
				.getAttribute(MenuNavegacionConstants.MENU_NAVEGACION_SESSION_KEY);
	}

	/**
	 * Genera el ID del captcha a mostrar
	 * 
	 * @return
	 */
	public Object getCaptchaId() {

		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();

		return request.getAttribute(SeguridadConstants.CAPTCHA_GENERATED_ID);
	}

	/**
	 * Obtiene el valor del atributo institucionDao
	 * 
	 * @return el valor del atributo institucionDao
	 */
	public InstitucionDao getInstitucionDao() {
		return institucionDao;
	}

	/**
	 * Establece el valor del atributo institucionDao
	 * 
	 * @param institucionDao
	 *            el valor del atributo institucionDao a establecer
	 */
	public void setInstitucionDao(InstitucionDao institucionDao) {
		this.institucionDao = institucionDao;
	}

	/**
	 * Realiza el proceso de logout en el WebService de seguridad de bursatec e
	 * invalida la sesion actual del usuario.
	 * 
	 * @return
	 */
	public void logout() {
		FacesContext ctx = FacesContext.getCurrentInstance();
		
		HttpServletRequest request = 	(HttpServletRequest) ctx.getExternalContext().getRequest();
		
		HttpSession session = request.getSession(false);
		
		String ticket = null; 
		
		String usuario = null;
		
		try {
			 ticket = (String) session.getAttribute(SeguridadConstants.TICKET_SESION);
			 
			 
			 if(ticket!=null){
					
					TicketInfoVO ticketInfo = SeguridadServiceLocator.obtenerSeguridadExposedService().getTicketInfo(ticket);
					
					usuario = ticketInfo.getClaveUsuario();
					
					log.info("******* CERRANDO SESION DE USUARIO: "+usuario+" con ticket:"+ticket+" EN SEGURIDAD");
					
					SeguridadServiceLocator.obtenerSeguridadExposedService().logout(ticket);
			}
			 
			 
		}
		catch (Exception e) {
			e.printStackTrace();
		}finally {
			
			
			if(ticket!=null){
				try{
					log.info("******* BORRANDO DE CACHE DISTRIBUIDO SESION DE USUARIO: "+usuario+" con ticket:"+ticket+" EN SEGURIDAD");
					removeFromCache(ticket.concat("|").concat("CAMBIO_INSTITUCION_ACTUAL"));
					if(request.getUserPrincipal()!=null)
						removeFromCache(request.getUserPrincipal().getName()+"-ticket");
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			try{
					log.info("******* INVALIDANDO SESION DE USUARIO: "+usuario+" con ticket:"+ticket);
					session.invalidate();
			}catch(Exception e){
					e.printStackTrace();
			}
			try {
					log.info("******* HACIENDO LOGOUT DE USUARIO: "+usuario+" con ticket:"+ticket);
					request.logout();
			} catch (ServletException e) {				
					e.printStackTrace();
			}
			
			log.info("******* TERMINANDO LA SESION DE USUARIO: "+usuario+" con ticket:"+ticket);
			
			
		}		
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
	
	
	public void setMostrarCaptcha(boolean mostrarCaptcha) {
		this.mostrarCaptcha = mostrarCaptcha;
	}
	
	public boolean isMostrarCaptcha() {
		return mostrarCaptcha;
	}
	
	public int getTipoAutenticacion() {
		return tipoAutenticacion;
	}
	
	public void setTipoAutenticacion(int tipoAutenticacion) {
		this.tipoAutenticacion = tipoAutenticacion;
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
		return (o1.getClave() + o1.getFolio()).compareTo(o2.getClave()
				+ o2.getFolio());
	}

}