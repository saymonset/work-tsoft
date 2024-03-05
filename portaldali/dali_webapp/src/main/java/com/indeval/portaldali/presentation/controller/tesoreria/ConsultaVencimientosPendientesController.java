/*
 *Copyright (c) 2005-2006 Bursatec. All Rights Reserved 
 */
package com.indeval.portaldali.presentation.controller.tesoreria;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import com.indeval.portaldali.middleware.dto.BovedaDTO;
import com.indeval.portaldali.middleware.dto.InstitucionDTO;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.CatalogoService;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import com.indeval.portaldali.middleware.services.modelo.PaginaVO;
import com.indeval.portaldali.presentation.controller.common.ControllerBase;
import com.indeval.portaldali.presentation.util.ConsultaCatalogosFacade;

/**
 * Controller encargado de consultar los vencimientos pendientes.
 * @author Hector Omar Segura Olivares
 * @version 1.0
 */
public class ConsultaVencimientosPendientesController extends ControllerBase {
	
	private CatalogoService catalogoService;
	
	/**
	 * Acceso a la consulta de catálogos desde la capa de vista
	 */
	private ConsultaCatalogosFacade consultaCatalogos = null;
	
	private InstitucionDTO institucionDTO = getInstitucionActual();
	
	private String claveFolioInstitucion = null;
	
	private String institucion = null;
	
	private Date fecha = null;
	
	private PaginaVO paginaReportes = null;
	
	/** El numero de pagina actual de la consulta */
	private int numeroPagina = -1;

	/** El total de paginas de resultados productos de la consulta */
	private int totalPaginas = -1;
	
	/**
	 * La cantidad de registros que seran presentados en una pagina de
	 * resultados
	 */
	private int registrosPorPagina = -1;
	
	/**
	 * Indica si se estn desplegando resultados
	 */
	private boolean consultaEjecutada = false;
	
	private PaginaVO paginaVO = null;
	
	private AgenteVO agenteVO = null;
	
	/** Indica si debe dejar log en la bitacora o no */
	private boolean debeDejarBitacora;
	
	private BovedaDTO bovedaValorDTO;

		
	public void limpiar(ActionEvent e){
		inicializarCampos();
		setRegistrosXPag(20);
	}
	
	
	public void editarCriterios(ActionEvent e) {
		consultaEjecutada = false;
	}
	
	public String getInitForm(){
		limpiar(null);
		return null;
	}
	
	/**
	 * Método encargado de regresar el valor cuando cambia un campo.
	 * @return null
	 */
	public String getInit() {
		
		
		
		if(claveFolioInstitucion != null) {
			InstitucionDTO inst = consultaCatalogos
			.buscarInstitucionPorIdFolio(claveFolioInstitucion);
			
			if(inst != null) {
				institucion = inst.getNombreCorto();
			}
			else {
				institucion = "TODAS";
			}
		}
		
		return null;
	}
	
	private void inicializarCampos() {
		
		setClaveFolioInstitucion(institucionDTO.getClaveTipoInstitucion() + institucionDTO.getFolioInstitucion());
		setInstitucion(institucionDTO.getNombreCorto());
		setFecha(new Date());
		consultaEjecutada = false;
		paginaVO = new PaginaVO();
		paginaVO.setRegistros(null);
	}

	/**
     * Realiza la búsqueda de vencimientos pendientes.
     * @param ev
     */
    public void buscar(){
    	ejecutarConsulta();
    }
    
    /**
     * Ejecuta la consulta con los criterios capturados por el usuario.
     */
	private void ejecutarConsulta() {
		agenteVO = new AgenteVO();
		if(!isUsuarioIndeval()) {
			agenteVO.setId(institucionDTO.getClaveTipoInstitucion());
			agenteVO.setFolio(institucionDTO.getFolioInstitucion());
		}
		else {
			if(claveFolioInstitucion.length() == 5) {
				agenteVO.setId(claveFolioInstitucion.substring(0, 2));
				agenteVO.setFolio(claveFolioInstitucion.substring(2, 5));
			}
			else if(claveFolioInstitucion.length() == 0){
				agenteVO.setId("00");
				agenteVO.setFolio("000");
			}
			else {
				agenteVO.setId("0");
				agenteVO.setFolio("0");
			}
		}
		if (fecha == null) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"La fecha no debe ir vac\u00EDa", "La fecha no debe ir vac\u00EDa"));
			consultaEjecutada = false;
		}
		else {
			paginaVO =
				catalogoService.getVencimientosPendientesByInstitucionFechaVencimientoAgurpadosPorEmision(
					agenteVO, fecha, BigInteger.valueOf(bovedaValorDTO.getId()), paginaVO, debeDejarBitacora);
			debeDejarBitacora = false;
			consultaEjecutada = true;
		}
	
	}
    
    public void cambiarTamanioPagina(ActionEvent ev){
    	paginaVO.setOffset(0); 
		ejecutarConsulta();
    	
    }
    
    
       
	/**
     * método para generar un reporte con formato XLS o PDF
     * 
     * @return cadena de para ejecutar el action para genera el reporte
     */
	public void generarReportes(ActionEvent e) {
		logger.info("Entrando a generarReportes()");
		reiniciarEstadoPeticion();
		if (fecha == null) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"La fecha no debe ir vac\u00EDa", "La fecha no debe ir vac\u00EDa"));
		} else {
			paginaReportes = new PaginaVO();

			paginaReportes = catalogoService
					.getVencimientosPendientesByInstitucionFechaVencimientoAgurpadosPorEmision(
							agenteVO, fecha,
							BigInteger.valueOf(bovedaValorDTO.getId()),
							paginaReportes, debeDejarBitacora);

			debeDejarBitacora = false;

			if (paginaReportes.getRegistros() == null) {
				paginaReportes.setRegistros(new ArrayList<EmisionVO>());
			}
		}
	}
	
    /**
     * Prepara el objeto EstadoPaginacionDTO con los resultados enviados como parámetro.
     * 
     * @param numeroResultados número de resultados obtenidos de la consulta
     */
    protected void incializarEstadoPaginacion(long numeroResultados) {
    	
        paginacion.setTotalResultados((int) numeroResultados);
        
        if (paginacion.getTotalResultados() > 0) {
            paginacion.setNumeroPagina(1);
            paginacion.setTotalPaginas((int) Math.ceil((double) paginacion.getTotalResultados()
                    / (double) paginacion.getRegistrosPorPagina()));
        } else {
            paginacion.setNumeroPagina(0);
            paginacion.setTotalPaginas(0);
        }
    }
    
    /**
	 * Actualiza la informacion de la paginacion al avanzar el numero de paginas
	 * solicitadas.
	 * 
	 * @param paginas
	 *            el numero de paginas a avanzar.
	 */
	public void avanzar(int paginas) {
		int numeropPag = getNumeroPagina();
		if (paginaVO.getTotalRegistros() > 0) {
			if (numeropPag + paginas > getTotalPaginas()) {
				numeropPag = getTotalPaginas();
			} else {
				numeropPag += paginas;
			}
			paginaVO.setOffset((numeropPag - 1) * paginaVO.getRegistrosXPag() + 0);
		}
		
		ejecutarConsulta();

	}

	/**
	 * Actualiza la informacion de la paginacion al retroceder el numero de
	 * paginas solicitadas.
	 * 
	 * @param paginas
	 *            el numero paginas a retroceder.
	 */
	public void retroceder(int paginas) {
		int numeropPag = getNumeroPagina();
		if (paginaVO.getTotalRegistros() > 0) {
			if ((numeropPag - paginas) < 1) {
				numeropPag = 1;
			} else {
				numeropPag -= paginas;
			}
		}
		paginaVO.setOffset((numeropPag - 1) * paginaVO.getRegistrosXPag() + 0);
		ejecutarConsulta();
	}

	/**
	 * Actualiza la informacion de la paginacion al establecer la primera pagina
	 * de resultados como la pagina actual.
	 * 
	 * @param event
	 *            el evento que dispara la ejecucion de este
	 *            {@link ActionListener}.
	 */
	public void irPrimeraPagina(ActionEvent event) {

		if (paginaVO.getTotalRegistros() > 0) {
			numeroPagina = 1;
		}
		paginaVO.setOffset(0);
		ejecutarConsulta();
	}

	/**
	 * Actualiza la informacion de la paginacion al establecer la ultima pagina
	 * de resultados como la pagina actual.
	 * 
	 * @param event
	 *            el evento que dispara la ejecucion de este
	 *            {@link ActionListener}.
	 */
	public void irUltimaPagina(ActionEvent event) {
		getNumeroPagina();
		if (paginaVO.getTotalRegistros() > 0) {
			paginaVO.setOffset((getTotalPaginas() - 1) * paginaVO.getRegistrosXPag() + 0);
		}
		ejecutarConsulta();
	}

	/**
	 * Indica si el estado actual de la paginación permite desplazarse al primer
	 * registro.
	 * 
	 * @return <code>true</code> si el estado actual de la paginación permite
	 *         ir al primer registro. <code>false</code> en cualquier otro
	 *         caso.
	 */
	public boolean isIrAlPrimero() {

		boolean resultado = false;

		if (paginaVO.getTotalRegistros() > 0) {
			if (getNumeroPagina() > 1) {
				resultado = true;
			}
		}

		return resultado;
	}

	/**
	 * Indica si el estado actual de la paginación permite desplazarse al último
	 * registro.
	 * 
	 * @return <code>true</code> si el estado actual de la paginación permite
	 *         ir al último registro. <code>false</code> en cualquier otro
	 *         caso.
	 */
	public boolean isIrAlUltimo() {
		boolean resultado = false;

		if (paginaVO.getTotalRegistros() > 0) {
			if (getNumeroPagina() < getTotalPaginas()) {
				resultado = true;
			}
		}

		return resultado;
	}

	/**
	 * Indica si el estado actual de la paginación permite ir a la siguiente
	 * página de resultados.
	 * 
	 * @return <code>true</code> si el estado actual de la paginación permite
	 *         ir a la siguiente página de resultados. <code>false</code> en
	 *         cualquier otro caso.
	 */
	public boolean isAvanzar() {
		boolean resultado = false;

		if (paginaVO.getTotalRegistros() > 0) {
			if (getNumeroPagina() < getTotalPaginas()) {
				resultado = true;
			}
		}

		return resultado;
	}

	/**
	 * Indica si el estado actual de la paginación permite avanzar 3 páginas de
	 * resultados.
	 * 
	 * @return <code>true</code> si el estado actual de la paginación permite
	 *         avanzar 3 páginas de resultados. <code>false</code> en
	 *         cualquier otro caso.
	 */
	public boolean isAvanzarRapido() {
		boolean resultado = false;

		if (paginaVO.getTotalRegistros() > 0) {
			if (getNumeroPagina()+2 < getTotalPaginas()) {
				resultado = true;
			}
		}

		return resultado;
	}

	/**
	 * Indica si el estado actual de la paginación permite retroceder una página
	 * de resultados.
	 * 
	 * @return <code>true</code> si el estado actual de la paginación permite
	 *         retroceder una página de resultados. <code>false</code> en
	 *         cualquier otro caso.
	 */
	public boolean isRetroceder() {
		boolean resultado = false;

		if (paginaVO.getTotalRegistros() > 0) {
			if (getNumeroPagina() > 1) {
				resultado = true;
			}
		}

		return resultado;
	}

	/**
	 * Indica si el estado actual de la paginación permite retroceder 3 páginas
	 * de resultados.
	 * 
	 * @return <code>true</code> si el estado actual de la paginación permite
	 *         retroceder 3 páginas de resultados.
	 */
	public boolean isRetrocederRapido() {
		boolean resultado = false;

		if (paginaVO.getTotalRegistros() > 0) {
			if (getNumeroPagina() > 3) {
				resultado = true;
			}
		}

		return resultado;
	}
	
	/**
	 * Reinicializa el estado de la paginación ya que se ha cambiado el tamaño de la página en la consulta.
	 * 
	 * @param event el vento que dispara la ejecución de este {@link ActionListener}
	 */
	public void cambioTamanioPagina(ActionEvent event) {
		paginaVO.setOffset(0);
		ejecutarConsulta();
	}

	/**
	 * Avanza a la siguiente página de resultados.
	 * 
	 * @param event el evento que dispara la ejecucion de este
	 *            {@link ActionListener}.
	 */
	public void avanzarPagina(ActionEvent event) {
		avanzar(1);
	}

	/**
	 * Avanza 3 páginas de resultados.
	 * 
	 * @param event el evento que dispara la ejecucion de este
	 *            {@link ActionListener}.
	 */
	public void avanzarPaginasRapido(ActionEvent event) {
		avanzar(3);
	}

	/**
	 * Retrocede 3 páginas de resultados.
	 * 
	 * @param event el evento que dispara la ejecucion de este
	 *            {@link ActionListener}.
	 */
	public void retrocederPaginasRapido(ActionEvent event) {
		retroceder(3);
	}

	/**
	 * Retrocede una página de resultados.
	 * 
	 * @param event el evento que dispara la ejecucion de este
	 *            {@link ActionListener}.
	 */
	public void retrocederPagina(ActionEvent event) {
		retroceder(1);
	}
	
	/**
	 * Obtiene el número de página que se está desplegando actualmente.
	 * 
	 * @return el número de página que se está desplegando actualmente.
	 */
	public int getNumeroPagina() {
		if (paginaVO != null && paginaVO.getRegistrosXPag() > 0) {

			numeroPagina = (int) Math.ceil(paginaVO.getOffset().doubleValue() / paginaVO.getRegistrosXPag().doubleValue()) + 1;
			int total = getTotalPaginas();
			if(numeroPagina > total) {
				numeroPagina = total;
			}

		}
		return numeroPagina;
	}
	
	/**
	 * Establece el número de registros por página.
	 * prefijo
	 * @param regs
	 *            el número de registros por página a desplegar.
	 */
	public void setRegistrosXPag(Integer regs) {
		paginaVO.setRegistrosXPag(regs);
	}
    
   	public String getInstitucion() {
		return institucion;
	}

	public void setInstitucion(String institucion) {
		this.institucion = institucion;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public CatalogoService getCatalogoService() {
		return catalogoService;
	}

	public void setCatalogoService(CatalogoService catalogoService) {
		this.catalogoService = catalogoService;
	}

	public boolean isConsultaEjecutada() {
		return consultaEjecutada;
	}

	public void setConsultaEjecutada(boolean consultaEjecutada) {
		this.consultaEjecutada = consultaEjecutada;
	}

	public int getTotalPaginas() {
		if (paginaVO != null && paginaVO.getTotalRegistros() > 0) {
			totalPaginas = (int) Math.ceil((paginaVO.getTotalRegistros().doubleValue() / paginaVO.getRegistrosXPag().doubleValue()));
		}
		return totalPaginas;
	}

	public void setTotalPaginas(int totalPaginas) {
		this.totalPaginas = totalPaginas;
	}

	public PaginaVO getPaginaVO() {
		return paginaVO;
	}

	public void setPaginaVO(PaginaVO paginaVO) {
		this.paginaVO = paginaVO;
	}

	public InstitucionDTO getInstitucionDTO() {
		return institucionDTO;
	}

	public void setInstitucionDTO(InstitucionDTO institucionDTO) {
		this.institucionDTO = institucionDTO;
	}

	public int getRegistrosPorPagina() {
		if (paginaVO != null && paginaVO.getRegistrosXPag() > 0) {
			registrosPorPagina = paginaVO.getRegistrosXPag();
		}
		return registrosPorPagina;
	}

	public void setRegistrosPorPagina(int registrosPorPagina) {
		this.registrosPorPagina = registrosPorPagina;
	}

	public void setNumeroPagina(int numeroPagina) {
		this.numeroPagina = numeroPagina;
	}
	
	public String getClaveFolioInstitucion() {
		return claveFolioInstitucion;
	}

	public void setClaveFolioInstitucion(String claveFolioInstitucion) {
		this.claveFolioInstitucion = claveFolioInstitucion;
	}

	public ConsultaCatalogosFacade getConsultaCatalogos() {
		return consultaCatalogos;
	}

	public void setConsultaCatalogos(ConsultaCatalogosFacade consultaCatalogos) {
		this.consultaCatalogos = consultaCatalogos;
	}

	public PaginaVO getPaginaReportes() {
		return paginaReportes;
	}

	public void setPaginaReportes(PaginaVO paginaReportes) {
		this.paginaReportes = paginaReportes;
	}

	/**
	 * @param debeDejarBitacora the debeDejarBitacora to set
	 */
	public void setDebeDejarBitacora(boolean debeDejarBitacora) {
		this.debeDejarBitacora = debeDejarBitacora;
	}

	/**
	 * @return the debeDejarBitacora
	 */
	public boolean isDebeDejarBitacora() {
		return debeDejarBitacora;
	}
	
	public BovedaDTO getBovedaValorDTO() {
		return bovedaValorDTO;
	}

	public void setBovedaValorDTO(BovedaDTO bovedaValorDTO) {
		this.bovedaValorDTO = bovedaValorDTO;
	}

	public BovedaDTO getBovedaSeleccionada() {
		return consultaCatalogos.getConsultaBovedaService()
				.consultarBovedaPorId(bovedaValorDTO.getId());
	}
	
}
