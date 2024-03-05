/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * ControllerBase.java
 * 29/02/2008
 */
package com.indeval.portaldali.presentation.controller.common;

import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bursatec.seguridad.dto.UsuarioDTO;
import com.bursatec.seguridad.middleware.ejb.SeguridadServiceLocator;
import com.bursatec.seguridad.middleware.service.SeguridadException;
import com.bursatec.seguridad.middleware.service.SeguridadService;
import com.bursatec.seguridad.presentation.constants.SeguridadConstants;
import com.bursatec.seguridad.vo.CertificadoVO;
import com.indeval.commons.cache.services.DistCacheService;
import com.indeval.portaldali.middleware.dto.BovedaDTO;
import com.indeval.portaldali.middleware.dto.DivisaDTO;
import com.indeval.portaldali.middleware.dto.InstitucionDTO;
import com.indeval.portaldali.middleware.dto.util.EstadoPaginacionDTO;
import com.indeval.portaldali.middleware.services.common.ValidacionService;
import com.indeval.portaldali.middleware.services.common.constants.DaliConstants;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.presentation.jsf.constantes.ReportesConstantes;
import com.indeval.portaldali.presentation.util.ResultadoInvocacionServicioUtil;

/**
 * Clase base para los backing bean de DALI
 * 
 * @author Emigdio Hernandez
 * 
 */
public abstract class ControllerBase {

	protected final Logger logger = LoggerFactory.getLogger(getClass());
	protected EstadoPaginacionDTO paginacion = new EstadoPaginacionDTO();

	/** Mensaje a mostrar al usuario */
	protected String mensajeUsuario = null;

	/** Indica la severidad del mensaje al usuario */
	protected Severity severidadMensaje = null;
	
	/** Servicio de validación */
	private ValidacionService validacionService = null;
	
	public final static DivisaDTO MXP = new DivisaDTO(DaliConstants.ID_DIVISA_MEXICAN_PESO);
	
	public final static BovedaDTO BANXICO = new BovedaDTO(BovedaDTO.BOVEDA_BANXICO);
	
	/** Indica el estado de la peticion de un reporte */
	private boolean peticionReporteCompleta = false;
	
	private transient DistCacheService indevalCacheService;
    /**
     * Obtiene la descripción de un id dentro de una lista de select items.
     * @param key Id del valor
     * @param lista Lista a recorrer
     * @return Valor correspondiente al id
     */
    public String obtenerElementoSeleccionado(final Long key, final List<SelectItem> lista){
        String resultado = null;
        for(SelectItem item : lista){
            if(key != null && item.getValue().equals(key)) {
                resultado=item.getLabel();
                break;
            }
        }
        return resultado;
    }
	
	/**
	 * Método que reinicia las variables para monitorear el estado de la
	 * petición de un reporte.
	 */
	public void reiniciarEstadoPeticion() {
		peticionReporteCompleta = false;
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		session.removeAttribute(ReportesConstantes.ESTADO_PETICION_REPORTES);
	}
	
	/**
	 * Método que reinicia las variables para monitorear el estado de la
	 * petición de un reporte.
	 */
	public void asignarEstadoPeticionReporteCompleta() {
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		session.setAttribute(ReportesConstantes.ESTADO_PETICION_REPORTES, ReportesConstantes.ESTADO_PETICION_REPORTES_COMPLETO);
	}
	
	/**
	 * Método que valida si la petición que genera un reporte ha terminado.
	 * @param actionEvent Evento generado por faces.
	 */
	public void actualizarEstadoPeticion(ActionEvent actionEvent) {
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Integer estado = (Integer) session.getAttribute(ReportesConstantes.ESTADO_PETICION_REPORTES);
		if(estado != null && ReportesConstantes.ESTADO_PETICION_REPORTES_COMPLETO == estado) {
			peticionReporteCompleta = true;
		}
	}
	
	/**
	 * Metodo que indica el usuario actual debe firmar operaciones
	 * 
	 * @return true en caso de que deba firmar, false en caso contrario
	 */
	@SuppressWarnings("unchecked")
	protected boolean isUsuarioConFacultadFirmar() {
		boolean debeFirmar = false;
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		
		if(session != null) {
			HashMap<String, String> facultadesUsuario = 
				(HashMap<String, String>)session.getAttribute(SeguridadConstants.FACULTADES_SESION);
			
			if(facultadesUsuario !=null 
					&& facultadesUsuario.containsKey(SeguridadConstants.FACULTAD_FIRMA_OPERACION))
				debeFirmar = true;			
		}
		
		return debeFirmar;
	}

	/**
	 * Método que valida la vigencia del certificado electrónico.
	 * @return true si el certificado es vigente; false en caso contrario.
	 * @throws BusinessException en caso de ocurrir un error
	 */
	public void validarVigenciaCertificado() throws BusinessException {
		try {
			if(isUsuarioConFacultadFirmar()) {				
				SeguridadService seguridadExposedService = SeguridadServiceLocator.obtenerSeguridadExposedService();
				if(seguridadExposedService == null) {
					throw new BusinessException(obtenerMensajePropiedades("msgErrorSeguridad"));
				}
				UsuarioDTO usuario = obtenerUsuarioSesion();
				if(usuario != null && StringUtils.isNotBlank(usuario.getNumeroSerieCertificado())) {
					//Indica si deben validar o no los certificados dependiendo la bandera en c_propiedades_dali
	                boolean validarCertificado = seguridadExposedService.isValidaCertificados();
					//Obtiene el certificado
					CertificadoVO certificado = seguridadExposedService.getCertificadoInfo(usuario.getNumeroSerieCertificado());
	                if(validarCertificado && 
	                		!((certificado.getStatus() != null && SeguridadConstants.CERTIFICADO_VALIDO_VALUE == certificado.getStatus()) && 
	                		validarFechaVencimientoCertificado(certificado))) {
	                	throw new BusinessException(obtenerMensajePropiedades("msgErrorCertificadoNoVigente"));
	                }
				}
				else {
					throw new BusinessException(obtenerMensajePropiedades("msgErrorSinCertificado"));
				}
			}
		}
		
		catch(SeguridadException exception) {
			String mensaje = obtenerMensajePropiedades("msgErrorSeguridad");
			mensaje = mensaje.concat(exception.getMessage());
			throw new BusinessException(mensaje);
		}catch(Exception exception) {
			String mensaje = obtenerMensajePropiedades("msgErrorSeguridad");
			mensaje = mensaje.concat(exception.getMessage());
			throw new BusinessException(mensaje);
		}
	}
	
	/**
	 * Obtiene el usuario de la sesión activa.
	 * @return Dto con los datos del usuario
	 */
	public UsuarioDTO obtenerUsuarioSesion() {
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		return (UsuarioDTO) session.getAttribute(SeguridadConstants.USUARIO_SESION);		
	}
	
	public String obtenerTicketSesion() {
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		return (String) session.getAttribute(SeguridadConstants.TICKET_SESION);
	}
	/**
	 * Valida que la fecha de vencimiento del certificado sea menor o igual a la fecha actual.
	 * @param certificado Objeto con el certificado.
	 * @return true si la fecha de vencimiento es valida; false en caso contrario.
	 */
	private boolean validarFechaVencimientoCertificado(CertificadoVO certificado) {
		boolean valido = false;
		if(certificado != null && certificado.getFechaVencimiento() != null) {
			valido = new Date().compareTo(certificado.getFechaVencimiento()) <= 0;
		}
		return valido;
	}

	/**
	 * Valida que la fecha haya sido capturada y que sea día hábil en su caso.
	 * @param fecha Fecha a validar.
	 * @param diaHabil true si debe validar día hábil.
	 * @param nombreCampo Nombre del campo en la pantalla.
	 * @return true si pasa la validación; false en otro caso.
	 */
	public boolean validarFechaObligatoria(Date fecha, boolean diaHabil, String nombreCampo) {
		boolean fechaValida = true;
		if(fecha != null) {
			if(diaHabil) {
				fechaValida = validacionService.validarFechaDiaHabil(fecha);
				if(!fechaValida) {
					addMessageFromProperties("msgFechaNoDiaHabil", new Object[]{nombreCampo}, FacesMessage.SEVERITY_ERROR);
				}
			}
		}
		else {
			fechaValida = false;
			addMessageFromProperties("msgErrorCampoObligatorio", new Object[]{nombreCampo}, FacesMessage.SEVERITY_ERROR);
		}
		return fechaValida;
	}

	/**
	 * Valida que en un rango de fechas, la fecha final sea mayor o igual a la inicial.
	 * @param fechaInicial
	 * @param fechaFinal
	 * @return true si se cumple la condición; false en otro caso.
	 */
	public boolean validarFechaFinalVsFechaInicial(Date fechaInicial, Date fechaFinal) {
		boolean valido = true;
		if(fechaInicial != null && fechaFinal != null) {
			int comparacion = fechaFinal.compareTo(fechaInicial);
			valido = comparacion != -1;
			if(!valido) {
				addMessageFromProperties("msgFechaFinalMayorFechaInicial", FacesMessage.SEVERITY_ERROR);
			}
		}
		return valido;
	}
        
        /**
	 * Valida que en un rango de fechas, la fecha final sea mayor o igual a la inicial.
	 * @param fechaInicial
	 * @param fechaFinal
	 * @return true si se cumple la condición; false en otro caso.
	 */
	public boolean validarFechaInicialRangoDias(Date fechaInicial, int rangoDias) {
		boolean valido = true;
		if(fechaInicial != null) {
                    Calendar fechaAnterior = Calendar.getInstance();
                    fechaAnterior.set(Calendar.HOUR_OF_DAY, 0);
                    fechaAnterior.set(Calendar.MINUTE, 0);
                    fechaAnterior.set(Calendar.SECOND, 0);
                    fechaAnterior.set(Calendar.MILLISECOND, 0);
                    fechaAnterior.add(Calendar.DATE, rangoDias*-1);
                    valido = fechaInicial.compareTo(fechaAnterior.getTime()) >= 0;
                    if(!valido) {
                        addMessageFromProperties("msgFechaInicialRangoDias", new Object[]{rangoDias}, FacesMessage.SEVERITY_ERROR);
                    }
		}
		return valido;
	}
	
	/**
	 * Obtiene de la sesi&oacute;n Web del usuario la instituci&oacute;n actual del
	 * participante.
	 * 
	 * @return DTO con los datos de la instituci&oacute;n.
	 */
	public InstitucionDTO getInstitucionActual() {
		FacesContext ctx = FacesContext.getCurrentInstance();
		InstitucionDTO institucionActual = (InstitucionDTO) ((HttpSession) ctx
				.getExternalContext().getSession(false))
				.getAttribute(SeguridadConstants.INSTITUCION_ACTUAL);

		return institucionActual;
	}
	
	/**
	 * Obtiene de la sesi&oacute;n Web del usuario el ticket de sesion
	 * 
	 * @return Ticket sesion
	 */
	public String getTicketSesion() {
		FacesContext ctx = FacesContext.getCurrentInstance();
		String ticket = (String) ((HttpSession) ctx
				.getExternalContext().getSession(false))
				.getAttribute(SeguridadConstants.TICKET_SESION);

		return ticket;
	}
	
	/**
	 * Verifica si el usuario en sesi&oacute;n pertenece a la instituci&oacute;n
	 * INDEVAL.
	 * 
	 * @return <code>true</code> si el usuario en sesi&oacute;n pertenece a la
	 *         instituci&oacute;n INDEVAL. <code>false</code> en cualquier otro
	 *         caso.
	 */
	public boolean isUsuarioIndeval() {
		InstitucionDTO inst = getInstitucionActual();
		boolean res = false;
		if (SeguridadConstants.ID_INSTITUCION_INDEVAL.equals(inst.getId())) {
			res = true;
		}
		return res;
	}

	public long getIdInstitucionIndeval() {
		return SeguridadConstants.ID_INSTITUCION_INDEVAL;
	}
	/**
	 * Prepara el objeto EstadoPaginacionDTO con los resultados enviados como
	 * parámetro.
	 * 
	 * @param numeroResultados
	 *            número de resultados obtenidos de la consulta
	 */
	protected void incializarEstadoPaginacion(long numeroResultados) {
		paginacion.setTotalResultados((int) numeroResultados);
		if (paginacion.getTotalResultados() > 0) {
			paginacion.setNumeroPagina(1);
			paginacion.setTotalPaginas((int) Math.ceil((double) paginacion
					.getTotalResultados()
					/ (double) paginacion.getRegistrosPorPagina()));
		} else {
			paginacion.setNumeroPagina(0);
			// paginacion.setRegistrosPorPagina(0);
			paginacion.setTotalPaginas(0);
		}
	}

	/**
	 * método que obtiene la fecha actual para los reportes
	 * 
	 * @return la fecha del día actual.
	 */
	public Date getFechaActual() {
		return new Date();
	}

	/**
	 * Indica si es un día adecuado para realizar la captura.
	 * 
	 * @return <code>true</code> si el día es adecuado para realizar la
	 *         captura.
	 */
	public boolean isDiaDeCaptura() {

		Calendar fechaActual = GregorianCalendar.getInstance();
		fechaActual.setTime(getFechaActual());

		return fechaActual.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY
				|| fechaActual.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY;
	}

	/**
	 * Obtiene el campo paginacion
	 * 
	 * @return paginacion
	 */
	public EstadoPaginacionDTO getPaginacion() {
		return paginacion;
	}

	/**
	 * Asigna el campo paginacion
	 * 
	 * @param paginacion
	 *            el valor de paginacion a asignar
	 */
	public void setPaginacion(EstadoPaginacionDTO paginacion) {
		this.paginacion = paginacion;
	}

	/**
	 * Verifica si existe un error en la última invocación de un servicio de
	 * negocio remoto
	 * 
	 * @return
	 */
	public boolean existeErrorEnInvocacion() {
		return ResultadoInvocacionServicioUtil.existeError();
	}




	


	/**
	 * Ejecuta la consulta necesaria para la generación del reporte.
	 * 
	 * @return una colección con los elementos que generarn el reporte.
	 */
	protected Collection<? extends Object> ejecutarConsultaReporte() {
		return null;
	}

	/**
	 * Crea los parámetros extras para el reporte
	 * 
	 * @return Mapa de parámetros
	 */
	protected Map<String, Object> llenarParametros() {
		return null;
	}

	/**
	 * Obtiene el nombre del reporte a ejecutar @ return Nombre del reporte a
	 * ejecutar
	 */
	protected String getNombreReporte() {
		return null;
	}

	/**
	 * Obtiene el campo mensajeUsuario
	 * 
	 * @return mensajeUsuario
	 */
	public String getMensajeUsuario() {
		return mensajeUsuario;
	}

	/**
	 * Asigna el campo mensajeUsuario
	 * 
	 * @param mensajeUsuario
	 *            el valor de mensajeUsuario a asignar
	 */
	public void setMensajeUsuario(String mensajeUsuario) {
		this.mensajeUsuario = mensajeUsuario;
	}

	/**
	 * método que obtiene el id a partir del idFolioInstitucion
	 * 
	 * @param idFolioInstitucion
	 * @return idInstitucion
	 */
	public String obtenerIdInstitucion(String idFolioInstitucion) {
		return StringUtils.substring(idFolioInstitucion, 0, 2);
	}

	/**
	 * método que obtiene el folioInstitucion a partir del idFolioInstitucion
	 * 
	 * @param idFolioInstitucion
	 * @return folioInstitucion
	 */
	public String obtenerFolioInstitucion(String idFolioInstitucion) {
		return StringUtils.substring(idFolioInstitucion, 2, 5);
	}
	
	/**
	 * Para obtener el mapa de parametros del request
	 * 
	 * @return Mapa de parametros del request
	 */
	public Map<String, String> getParameterMap() {
		return getContext().getExternalContext().getRequestParameterMap();
	}
	
	/**
	 * Para obtener el contexto de Faces
	 * 
	 * @return Objeto de contexto de Faces
	 */
	public FacesContext getContext() {
		return FacesContext.getCurrentInstance();
	}
	
	/**
	 * Metodo de utileria para obtener el valor de una expresion EL de faces
	 * 
	 * @param La expresion a evaluar
	 * @return El valor de la expresion
	 */
	public Object getValue(final String obj) {
		final ExpressionFactory expr = getFacesContext().getApplication()
				.getExpressionFactory();
		final ValueExpression valExp = expr.createValueExpression(
				getELContext(), obj, Object.class);
		return valExp.getValue(getELContext());
	}

	/**
	 * Metodo de utileria para establecer el valor de una expresion EL de faces
	 * 
	 * @param obj La expresion a evaluar
	 * @param val El valor a establecer
	 */
	public void setValue(final String obj, final Object val) {
		final ExpressionFactory expr = getFacesContext().getApplication()
				.getExpressionFactory();
		final ValueExpression valExp = expr.createValueExpression(
				getELContext(), obj, Object.class);
		valExp.setValue(getELContext(), val);
	}

	/**
	 * Metodo de utileria para simplificar el acceso al contexto de faces
	 * 
	 * @return El objeto FacesContext
	 */
	public FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}
	
	/**
	 * Metodo de utileria para simplificar el acceso al contexto de EL
	 * 
	 * @return El objeto ELContext
	 */
	public ELContext getELContext() {
		return getFacesContext().getELContext();
	}
	
	/**
	 * Agrega un mensaje al contexto y de esta forma, se pueda presentar al usuario
	 * 
	 * @param idMensaje ID del mensaje en el archivo de propiedades
	 * @param severidad La severidad con la que se manda el mensaje
	 */
	protected void addMessageFromProperties(final String idMensaje, final Severity severidad) {
		final String mensaje = (String) getValue("#{mensajes." + idMensaje + "}");
		final FacesMessage facesMessage = new FacesMessage(
				severidad, mensaje, mensaje);

		getFacesContext().addMessage(null, facesMessage);
	}

	/**
	 * Agrega un mensaje al contexto y de esta forma, se pueda presentar al usuario
	 * @param idMensaje Id del mensaje en el archivo de propiedades
	 * @param parametros Parametros del mensaje
	 * @param severidad La severidad con la que se manda el mensaje
	 */
	protected void addMessageFromProperties(final String idMensaje, final Object[] parametros, final Severity severidad) {
		String mensaje = (String) getValue("#{mensajes." + idMensaje + "}");
		if (null != parametros && parametros.length > 0) {
			mensaje = (new MessageFormat(mensaje)).format(parametros, new StringBuffer(), null).toString();
		}		
		final FacesMessage facesMessage = new FacesMessage(
				severidad, mensaje, mensaje);			
		getFacesContext().addMessage(null, facesMessage);
	}
	
	/**
	 * Agrega un mensaje al contexto.
	 * @param mensaje Mensaje a mostrar
	 * @param severidad Severidad del mensaje
	 */
	protected void addMessage(final String mensaje, final Severity severidad) {
		final FacesMessage facesMessage = new FacesMessage(severidad, mensaje, mensaje);			
		getFacesContext().addMessage(null, facesMessage);
	}
	
	/**
	 * Método que obtiene un mensajes del archivo properties.
	 * @param idMensaje Id del mensaje en el archivo properties.
	 * @return Cadena con el mensaje.
	 */
	protected String obtenerMensajePropiedades(String idMensaje) {
		return (String) getValue("#{mensajes." + idMensaje + "}");
	}
	
	/**
	 * Sugiere el paso del garbage collector
	 */
	public void pasarGarbageCollector(){
	    Runtime garbage = Runtime.getRuntime();
	    garbage.gc();
	}

	/**
	 * Método para obtener el atributo severidadMensaje
	 * @return El atributo severidadMensaje
	 */
	public Severity getSeveridadMensaje() {
		return severidadMensaje;
	}

	/**
	 * Método para establecer el atributo severidadMensaje
	 * @param severidadMensaje El valor del atributo severidadMensaje a establecer.
	 */
	public void setSeveridadMensaje(Severity severidadMensaje) {
		this.severidadMensaje = severidadMensaje;
	}

	/**
	 * Método para obtener el atributo validacionService
	 * @return El atributo validacionService
	 */
	public ValidacionService getValidacionService() {
		return validacionService;
	}

	/**
	 * Método para establecer el atributo validacionService
	 * @param validacionService El valor del atributo validacionService a establecer.
	 */
	public void setValidacionService(ValidacionService validacionService) {
		this.validacionService = validacionService;
	}

	/**
	 * Método para obtener el atributo peticionReporteCompleta
	 * @return El atributo peticionReporteCompleta
	 */
	public boolean isPeticionReporteCompleta() {
		return peticionReporteCompleta;
	}

	/**
	 * Método para establecer el atributo peticionReporteCompleta
	 * @param peticionReporteCompleta El valor del atributo peticionReporteCompleta a establecer.
	 */
	public void setPeticionReporteCompleta(boolean peticionReporteCompleta) {
		this.peticionReporteCompleta = peticionReporteCompleta;
	}
	
	
	public void setIndevalCacheService(DistCacheService indevalCacheService) {
		this.indevalCacheService = indevalCacheService;
	}
	
	public DistCacheService getIndevalCacheService() {
		return indevalCacheService;
	}
	
	
	
	public void addToCache(String id, String key, String value, Long timeValue,TimeUnit timeUnit){
		StringBuilder sb=new StringBuilder();
		sb.append(id).append("|").append(key);
		indevalCacheService.addToCache(sb.toString(), value,-1L,TimeUnit.SECONDS, timeValue, timeUnit);
		sb=null;
	}
	
	public String getFromCache(String id, String key){
		StringBuilder sb=new StringBuilder();
		sb.append(id).append("|").append(key);
		return indevalCacheService.getFromCache(sb.toString());
	}

	public void removeFromCache(String key){
		indevalCacheService.removeFromCache(key);
	}

    public String obtenerIpRemota() {
    	FacesContext ctx = FacesContext.getCurrentInstance();
    	HttpServletRequest request = (HttpServletRequest) ctx.getExternalContext().getRequest();
    	return request.getRemoteAddr();
    }
    protected boolean tieneFacultad(String facultad) {
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		
		if(session!=null) {
			HashMap<String, String> facultades = 
					(HashMap<String, String>)session.getAttribute(SeguridadConstants.FACULTADES_SESION);
			
			return facultades !=null && facultades.containsKey(facultad);
		}
			
		return false; 
	}
}
