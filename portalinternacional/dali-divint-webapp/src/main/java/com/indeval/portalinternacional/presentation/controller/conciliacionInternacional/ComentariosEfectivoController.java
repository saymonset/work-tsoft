package com.indeval.portalinternacional.presentation.controller.conciliacionInternacional;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.bursatec.seguridad.dto.UsuarioDTO;
import com.bursatec.seguridad.presentation.constants.SeguridadConstants;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.ConciliacionEfectivoIntService;
import com.indeval.portalinternacional.middleware.servicios.modelo.ComentarioEfectivoInt;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;

public class ComentariosEfectivoController extends ControllerBase {
	
	private final static int COMENTARIOS_MIN_LENGTH = 1;
	
	private final static int COMENTARIOS_MAX_LENGTH = 100;

	private ConciliacionEfectivoIntService conciliacionEfectivoIntService;
	
	private Map<String, String> params;
	
	private List<ComentarioEfectivoInt> listaComentarios;
	
	private Long idDetalle;
	
	private String comentario;
	
	/**
	 * Inicializa el bean
	 * @return String
	 */
	public String getInit(){
		params = new HashMap<String, String>();
        Set<String> keys = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().keySet();
        
        for (String key : keys) {
            params.put(key, FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(key));
        }
        
		if(params.get("idDetalle") != null){
			idDetalle = Long.valueOf(params.get("idDetalle"));
		}
		
		if(idDetalle != null){
			listaComentarios = conciliacionEfectivoIntService.consultaComentarios(idDetalle);
		}
		
		return null;
	}
	
	/**
	 * Guarda los comentarios
	 * @param evt
	 */
	public void guardarComentario(ActionEvent evt){
		if(idDetalle != null && comentario != null){
			
			if(comentario.trim().length() >= COMENTARIOS_MIN_LENGTH && comentario.trim().length() <= COMENTARIOS_MAX_LENGTH){
				ComentarioEfectivoInt comentarioEfectivo = new ComentarioEfectivoInt();
				comentarioEfectivo.setIdDetalle(idDetalle);
				comentarioEfectivo.setComentario(comentario);
				comentarioEfectivo.setFecha(Calendar.getInstance().getTime());
			
				String usuario = getUsuarioFirmado();
				
				comentarioEfectivo.setUsuario(usuario);
			
				conciliacionEfectivoIntService.guardarComentario(comentarioEfectivo);
				conciliacionEfectivoIntService.actualizaComentarioDetalle(idDetalle);
				listaComentarios = conciliacionEfectivoIntService.consultaComentarios(idDetalle);
				addMessage("El comentario se guardó satisfactoriamente");
			} else {
				addErrorMessage("La longitud del campo debe ser mayor o igual a " + COMENTARIOS_MIN_LENGTH + " y menor o igual a " + COMENTARIOS_MAX_LENGTH);
			}
		}
	}
	
	 public String getUsuarioFirmado() {
		 String usuario = "";
		 FacesContext ctx = FacesContext.getCurrentInstance();
		 HttpServletRequest request = (HttpServletRequest) ctx.getExternalContext().getRequest();
		 HttpSession session = request.getSession(false);
		 UsuarioDTO user = (UsuarioDTO)session.getAttribute(SeguridadConstants.USUARIO_SESION);
		 
		 if(user != null){
			 usuario = user.getClaveUsuario();
		 }
		 
		 return usuario;
	 }

	/**
	 * @return the conciliacionEfectivoIntService
	 */
	public ConciliacionEfectivoIntService getConciliacionEfectivoIntService() {
		return conciliacionEfectivoIntService;
	}

	/**
	 * @param conciliacionEfectivoIntService the conciliacionEfectivoIntService to set
	 */
	public void setConciliacionEfectivoIntService(
			ConciliacionEfectivoIntService conciliacionEfectivoIntService) {
		this.conciliacionEfectivoIntService = conciliacionEfectivoIntService;
	}

	/**
	 * @return the params
	 */
	public Map<String, String> getParams() {
		return params;
	}

	/**
	 * @param params the params to set
	 */
	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	/**
	 * @return the listaComentarios
	 */
	public List<ComentarioEfectivoInt> getListaComentarios() {
		return listaComentarios;
	}

	/**
	 * @param listaComentarios the listaComentarios to set
	 */
	public void setListaComentarios(List<ComentarioEfectivoInt> listaComentarios) {
		this.listaComentarios = listaComentarios;
	}

	/**
	 * @return the idDetalle
	 */
	public Long getIdDetalle() {
		return idDetalle;
	}

	/**
	 * @param idDetalle the idDetalle to set
	 */
	public void setIdDetalle(Long idDetalle) {
		this.idDetalle = idDetalle;
	}

	/**
	 * @return the comentario
	 */
	public String getComentario() {
		return comentario;
	}

	/**
	 * @param comentario the comentario to set
	 */
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
}
