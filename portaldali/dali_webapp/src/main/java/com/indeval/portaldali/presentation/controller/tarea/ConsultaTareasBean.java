package com.indeval.portaldali.presentation.controller.tarea;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import com.indeval.portaldali.middleware.dto.CriterioDTO;
import com.indeval.portaldali.middleware.dto.TareaDTO;
import com.indeval.portaldali.middleware.dto.util.EstadoPaginacionDTO;
import com.indeval.portaldali.middleware.dto.util.ResultadoValidacionDTO;
import com.indeval.portaldali.middleware.services.tarea.TareaService;
import com.indeval.portaldali.presentation.controller.common.ControllerBase;



public class ConsultaTareasBean extends ControllerBase{
	
	private TareaService tareaService;
	
	/**
	 * Indica si existen resultados disponibles
	 */
	private boolean consultaEjecutada = false;
	/**
	 * Lista que contiene el resultado de la consulta de participantes
	 */
	private List<TareaDTO> resultados = null;
	
	private List<TareaDTO> tareas = null;
	/**
	 * Criterios para la consulta
	 */
	private CriterioDTO criterio = new CriterioDTO();
	
	
	public String getInit() {
		paginacion = new EstadoPaginacionDTO();
		paginacion.setRegistrosPorPagina(20);
		return null;
	}
	
	/**
	 * Ejecuta la consulta de búsqueda de participantes para un conjunto
	 * de criterios solicitados
	 * @param e Action Event generado
	 */
	public void buscar() {
		logger.debug("buscar");
		
		ResultadoValidacionDTO resultado = crearCriterio();
		if (resultado.isValido()) {
			// long totalRegistros = tareaService.count(criterio);
				
			// incializarEstadoPaginacion(totalRegistros);
				
			buscarRegistros();
		}else {
			consultaEjecutada = false;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, resultado.getMensaje(), resultado.getMensaje()));
		}
	}
	
	/**
	 * Invoca las funciones de navegación de página de la consulta principal de
	 * la pantalla
	 *
	 * @param e
	 *            Evento generado durante la solicitud
	 */
	public void navegarPorResultados(ActionEvent e) {
		logger.debug("navegarPorResultados");
		String navegacion = (String) e.getComponent().getAttributes().get("navegacion");

		try {
			paginacion.getClass().getMethod(navegacion).invoke(paginacion);
		} catch (Exception ex) {
			logger.error("Error de invocación de método al navega por los resultados principales", ex);
		}
		
		paginar();
	}
	
	/**
	 * Busca las operaciones.
	 */
	private void buscarRegistros() {
		logger.debug("buscarRegistros");
		// consultaEjecutada = true;
		
		String usuario = obtenerUsuarioSesion().getClaveUsuario();
		String ticket = obtenerTicketSesion();
		
		logger.debug("usuario: " + usuario);
		logger.debug("ticket: " + ticket );
		tareas = tareaService.find(criterio, paginacion, usuario, ticket);
		
		incializarEstadoPaginacion(tareas.size());
		
		paginar();
	}
	
	private void paginar() {
		consultaEjecutada = true;
		
		int size = tareas.size();
		
		if (paginacion != null) {
            paginacion.setTotalResultados(size);
            if (paginacion.getTotalResultados() > 0) {
                paginacion.setTotalPaginas((int) Math.ceil((double) paginacion.getTotalResultados() / (double) paginacion.getRegistrosPorPagina()));
                if (paginacion.getNumeroPagina() < 1) {
                    paginacion.setNumeroPagina(1);
                }
            }else {
                paginacion.setNumeroPagina(0);
                paginacion.setTotalPaginas(0);
                
                resultados = new ArrayList<>();
            }
            
            int pagina = paginacion.getNumeroPagina();
            int registrosPorPagina = paginacion.getRegistrosPorPagina();

            if (pagina > 0 && registrosPorPagina > 0) {
                int primerRes = (paginacion.getNumeroPagina() - 1) * paginacion.getRegistrosPorPagina();
                int ultimoRes = (paginacion.getNumeroPagina() - 1) * paginacion.getRegistrosPorPagina() + paginacion.getRegistrosPorPagina();
                if (ultimoRes > (size - 1)) {
                    ultimoRes = size;
                }

                resultados = tareas.subList(primerRes, ultimoRes);
            }
        }
	}
	
	public ResultadoValidacionDTO crearCriterio() {
		logger.info("crearCriterio");
		
		ResultadoValidacionDTO resultado = new ResultadoValidacionDTO();
		resultado.setValido(true);
		
		return resultado;
	}
	/**
	 * Limpia los resultados de la consulta
	 *
	 * @param e
	 */
	public void limpiar(ActionEvent e) {
		paginacion = new EstadoPaginacionDTO();
		paginacion.setRegistrosPorPagina(20);
		
		consultaEjecutada = false;
		resultados = null;
	}

	public boolean isConsultaEjecutada() {
		return consultaEjecutada;
	}

	public void setConsultaEjecutada(boolean consultaEjecutada) {
		this.consultaEjecutada = consultaEjecutada;
	}

	public List<TareaDTO> getResultados() {
		return resultados;
	}

	public void setResultados(List<TareaDTO> resultados) {
		this.resultados = resultados;
	}

	public CriterioDTO getCriterio() {
		return criterio;
	}

	public void setCriterio(CriterioDTO criterio) {
		this.criterio = criterio;
	}

	public void setTareaService(TareaService tareaService) {
		this.tareaService = tareaService;
	}

	public List<TareaDTO> getTareas() {
		return tareas;
	}

	
}
