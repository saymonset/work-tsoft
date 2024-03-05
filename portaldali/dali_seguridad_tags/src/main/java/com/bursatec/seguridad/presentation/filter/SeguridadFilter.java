/**
 * 2H Software
 * Sistema de Consulta de Estado de Cuenta - Indeval
 */
package com.bursatec.seguridad.presentation.filter;

import java.io.IOException;
import java.rmi.RemoteException;
import java.security.Principal;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bursatec.seguridad.dto.UsuarioDTO;
import com.bursatec.seguridad.middleware.ejb.CacheServiceLocator;
import com.bursatec.seguridad.middleware.ejb.SeguridadServiceLocator;
import com.bursatec.seguridad.middleware.service.SeguridadException;
import com.bursatec.seguridad.presentation.constants.SeguridadConstants;

/**
 * Implementación de un {@link Filter} para asegurar que al entrar a un recurso
 * protegido se tengan los datos del usuario en la sesión, lo cuál significa que
 * al menos ya se ha iniciado sesión.
 * 
 * @author Emigdio
 * 
 */
public class SeguridadFilter implements Filter {
	
	/** Inicio del dominio del sistema */
	private static final String STRING = "/";
	
	/** Pagina principal del sistema */
	private static final String INDEX_FACES = "/index.faces";
	/** Pagina principal del sistema */
	private static final String CHANGE_PASSWORD_FACES = "changePassword.faces";
	
	/** Pagina de autenticacion del sistema */
	
	private static final String LOGIN_FACES = "/login.faces";
	
	/** Tiempo en el que se valida el ticket */
	private Long tiempoValidacionTiket;
	
	/** Log de la clase */
	private Logger logger = LoggerFactory.getLogger(getClass());

	
	
	/**
	 * Inicialización del filtro.
	 * @param theFilterConfig (Parámetros, en caso que aplique).
	 * @throws ServletException (Excepciones que arroja, en caso que aplique).
	 */
	public void init(FilterConfig theFilterConfig) throws ServletException {
		//Obtiene el tiempo de renovacion del ticket
		tiempoValidacionTiket = Long.parseLong(theFilterConfig.getInitParameter("TIEMPO_RENOVACION_TICKET"));
	}
	
	
	
	/**
	 * Limpieza previa a la destrucción de una instancia del filtro
	 */
	public void destroy() {}

	/*
	 * (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = null;
		HttpServletResponse httpResponse = null;
		HttpSession session = null;
		String requestURI = null;
		UsuarioDTO usuario = null;	
		String ticket=null;		
		String userName=null;		
		
		
		try {
			httpRequest = (HttpServletRequest) request;
			
			httpResponse = (HttpServletResponse) response;
			
			requestURI = httpRequest.getRequestURI();				
			
			requestURI = requestURI.replaceAll(httpRequest.getContextPath() + STRING, StringUtils.EMPTY);			
			
			if (!isRichFacesResource(requestURI)) {
				
				if(logger.isDebugEnabled()){
					logger.debug("Accediendo a la URL:"+requestURI);
				}
				
				session = httpRequest.getSession(false);				
				
				if(session!=null) {
				
					usuario = (UsuarioDTO) session.getAttribute(SeguridadConstants.USUARIO_SESION);
					
					ticket = (String) session.getAttribute(SeguridadConstants.TICKET_SESION);
					
					if(usuario != null){
						
						userName=usuario.getClaveUsuario();
						
					}
				
				}
				
				Principal userPrincipal=httpRequest.getUserPrincipal();
				
				if(userPrincipal!=null) {
				
					String userData[]=userPrincipal.getName().split("\\|");
					if(userData.length>1){
						ticket = userData[0];
						if(userName==null)
							userName= userData[1];						
					}
					
				}
					
				if(usuario==null && ticket==null && CHANGE_PASSWORD_FACES.equalsIgnoreCase(requestURI)) {
                    request.getRequestDispatcher(LOGIN_FACES).forward(request, response);
                    return;
                }
				
				if(ticket!=null&&userName!=null&&session!=null) {	
					actualizaTiempoValidacion(session,ticket,userName);
//					if(isLoginResource(requestURI)) {
//						httpResponse.sendRedirect(httpRequest.getContextPath() + INDEX_FACES);
//					}
				}
					
				
				
				
			}
			
			chain.doFilter(request, response);
			return ;
		}catch (SeguridadException e) {
			
			
			if(httpRequest!=null&&session!=null) {
				if(logger.isDebugEnabled())
					logger.debug("ocurrio un error, invalidando sesion");
				session.invalidate();
				httpRequest.logout();
				request.getRequestDispatcher(LOGIN_FACES).forward(request, response);
				return;
			}
			
			

			logger.error("Ocurrio un error de seguridad",e);
			
		}catch (Exception e) {			
			throw new ServletException("Ocurrio un error en el filtro de seguridad",e);	
		}finally {
			httpRequest = null;
			httpResponse = null;
			session = null;			
			usuario = null;
			
		}
	}

	/**
	 * Verifica si es un recurso de login.
	 * @param requestURL La URL de la petición
	 * @return true si es recurso de login, falso de lo contrario
	 */
	private boolean isLoginResource(String requestURL) {
		return (requestURL.indexOf("login/") != -1) || (requestURL.indexOf("login.") != -1) ||
			requestURL.length() == 0 || requestURL.indexOf("richfaces.renderkit.html.Paint2DResource/DATA/") != -1 || requestURL.contains(".htc");

	}

	/**
	 * Verifica si es un script de a4j o rich faces
	 * @param requestURL La URL de la petición
	 * @return true si es recurso de login, falso de lo contrario
	 */
	private boolean isRichFacesResource(String requestURL) {		
		return (requestURL.indexOf("ajax4jsf.javascript.AjaxScript") != -1 ||
			requestURL.indexOf("ajax4jsf/javascript/scripts") != -1 ||
			requestURL.indexOf("richfaces") != -1 ||
			requestURL.indexOf("a4j/g") != -1);
	}
	
	/**
	 * Metodo que actualiza el tiempo de validacion del ticket
	 * @param request
	 * @throws ServletException
	 * @throws RemoteException
	 * @throws SeguridadException
	 */
	private  void actualizaTiempoValidacion(HttpSession session,String ticket,String userName ) throws ServletException, RemoteException, SeguridadException {        
        Date lastValidation = (Date) session.getAttribute(SeguridadConstants.ATTR_ULTIMA_VALIDACION);
        
        if (lastValidation != null) {
			// if the validation has previously been done, add the previous time plus the allowed interval
			long timeSpread = lastValidation.getTime() + (60000 * tiempoValidacionTiket);
			// if the interval has not been reached, allow the previous validation
			if (timeSpread <= System.currentTimeMillis()) {
				actualizaTicket(session,ticket,userName);
			}
        }
        else {
        	actualizaTicket(session,ticket,userName);
        }
	}

	/**
	 * Metodo que actualiza el ticket
	 * @param session
	 * @throws ServletException
	 * @throws RemoteException
	 * @throws SeguridadException
	 */
	private  void actualizaTicket(HttpSession session,String ticket,String userName ) throws ServletException, RemoteException, SeguridadException{
        
        if (StringUtils.isNotBlank(ticket)) {
        	
        	if(CacheServiceLocator.obtenerCacheService().getFromCache(ticket.concat("|").concat(userName+"-ticket"))==null) {
				throw new SeguridadException("Ticket no encontrado");
			}
        	
        	SeguridadServiceLocator.obtenerSeguridadExposedService().isTicketValido(ticket);
        	
			SeguridadServiceLocator.obtenerSeguridadExposedService().actualizaTicket(ticket);
			
			Date actualizacion = new Date();
			
			session.setAttribute(SeguridadConstants.ATTR_ULTIMA_VALIDACION, actualizacion);
        }
        else {
        	throw new NullPointerException("Ticket no encontrado");
        }
	}

	
	
}
