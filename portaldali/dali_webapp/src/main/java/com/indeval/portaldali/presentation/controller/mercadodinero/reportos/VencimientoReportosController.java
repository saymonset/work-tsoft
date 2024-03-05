package com.indeval.portaldali.presentation.controller.mercadodinero.reportos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.commons.lang.StringUtils;

import com.bursatec.seguridad.presentation.constants.SeguridadConstants;
import com.indeval.portaldali.middleware.dto.CuentaDTO;
import com.indeval.portaldali.middleware.dto.EmisoraDTO;
import com.indeval.portaldali.middleware.dto.InstitucionDTO;
import com.indeval.portaldali.middleware.dto.MercadoDTO;
import com.indeval.portaldali.middleware.dto.TipoCuentaDTO;
import com.indeval.portaldali.middleware.dto.TipoInstitucionDTO;
import com.indeval.portaldali.middleware.dto.TipoNaturalezaDTO;
import com.indeval.portaldali.middleware.dto.TipoTenenciaDTO;
import com.indeval.portaldali.middleware.dto.TipoValorDTO;
import com.indeval.portaldali.middleware.dto.VencimientoAnticipadoDTO;
import com.indeval.portaldali.middleware.dto.criterio.CriterioVencimientoReportosDTO;
import com.indeval.portaldali.middleware.dto.util.EstadoPaginacionDTO;
import com.indeval.portaldali.middleware.dto.util.ResultadoValidacionDTO;
import com.indeval.portaldali.middleware.model.util.ConsultaOperacionesMatch;
import com.indeval.portaldali.middleware.services.common.constants.DaliConstants;
import com.indeval.portaldali.middleware.services.common.constants.TipoCustodiaConstants;
import com.indeval.portaldali.middleware.services.reportos.VencimientoReportosService;
import com.indeval.portaldali.presentation.controller.common.ControllerBase;
import com.indeval.portaldali.presentation.util.ConsultaCatalogosFacade;

public class VencimientoReportosController extends ControllerBase {
	
	private final static MercadoDTO MERCADO_DINERO = new MercadoDTO(DaliConstants.ID_MERCADO_DINERO);
	
	/**
	 * Acceso a los elementos de los catálogos
	 */
	private ConsultaCatalogosFacade consultaCatalogos;
	/**
	 * Servicio de consulta de las operaciones en reporto
	 */
	private VencimientoReportosService vencimientoReportosService = null;

	/**
	 * Criterio de búsqueda
	 */
	private CriterioVencimientoReportosDTO criterio = new CriterioVencimientoReportosDTO();
	/**
	 * Indica si existen resultados disponibles
	 */
	private boolean consultaEjecutada = false;
	/**
	 * Lista que contiene los datos resultado de la consulta de estatus de
	 * operaciones y match
	 */
	private List<ConsultaOperacionesMatch> resultados = null;
	
	private VencimientoAnticipadoDTO solicitud = null;
	
	
	private Integer nuevoPlazo;
	private Date fechaVencimiento;
	private Date fechaVencimientoMin;
	private boolean validatioErrors;
	
	private boolean facultadLectura;
	private boolean facultadEscritura;
	private boolean puedeConsultar;
	
	public VencimientoReportosController() {
		logger.debug("constructor VencimientoReportosController");
		
		this.criterio = new CriterioVencimientoReportosDTO();
	}
	
	public String getInit() {
		logger.debug("getInit");
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String reload = params.get("reload");
		String modMessage = params.get("modMessage");
		logger.debug("reload: " + reload);
		logger.debug("modMessage: " + modMessage);
		
		paginacion = new EstadoPaginacionDTO();
		paginacion.setRegistrosPorPagina(20);
		
		fechaVencimientoMin = vencimientoReportosService.agregaDiasHabiles(new Date(), 2);
		
		if(Boolean.parseBoolean(reload)) {
			
			buscarOperaciones();// buscarOperaciones
			
			if(StringUtils.isNotBlank(modMessage)) {
				
				FacesContext.getCurrentInstance().addMessage("mmc", new FacesMessage(FacesMessage.SEVERITY_INFO, modMessage, modMessage));
			}
			
			
		}else {
			consultaEjecutada = false;
			criterio = new CriterioVencimientoReportosDTO();
			criterio.setFechaReportoIni(new Date());
			criterio.setFechaReportoFin(new Date());
			
			criterio.setInstitucionParticipante(getIdFolioInstitucionActual());
		}
		facultadEscritura = tieneFacultad(SeguridadConstants.FACULTAD_VTO_REPORTOS_OPERA);
		facultadLectura   = tieneFacultad(SeguridadConstants.FACULTAD_VTO_REPORTOS_CONSULTA);
		
		logger.debug("facultadEscritura: " + facultadEscritura);
		logger.debug("facultadLectura: " + facultadLectura);
		
		return null;
	}
	
	public long getIdInstitucionActual() {
		return getInstitucionActual().getId();
	}
	
	private String getIdFolioInstitucionActual() {
		return getInstitucionActual().getClaveTipoInstitucion() + getInstitucionActual().getFolioInstitucion();
	}
	
	/**
	 * Ejecuta la consulta de búsqueda de operaciones y match para un conjunto
	 * de criterios solicitados
	 * @param e Action Event generado
	 */
	public void buscarReportos() {
		logger.debug("buscarReportos");
		
		// SI NO PUEDE CONSULTAR, MUESTRA UN MENSAJE
		if(!(puedeConsultar = facultadEscritura || facultadLectura) ) {
			String message="Usted no tiene facultades de consulta. Favor de verificar con su administrador.";
			logger.warn(message);
			FacesContext.getCurrentInstance().addMessage("mensajesConsulta",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
			
			return;
		}
				
		ResultadoValidacionDTO resultado = crearCriterio();
		if (resultado.isValido()) {
			
			long totalRegistros = vencimientoReportosService.countReportos(criterio);
			
			//criterio.getEmision().getSerie().setSerie(StringUtils.upperCase(criterio.getEmision().getSerie().getSerie()));
			incializarEstadoPaginacion(totalRegistros);
			
			buscarOperaciones();
		}
		else {
			consultaEjecutada = false;
			FacesContext.getCurrentInstance().addMessage("mensajesConsulta", new FacesMessage(FacesMessage.SEVERITY_ERROR, resultado.getMensaje(), resultado.getMensaje()));
		}
	}

	/**
	 * Busca las operaciones.
	 */
	private void buscarOperaciones() {
		logger.debug("buscarOperaciones");
		consultaEjecutada = true;
		
		resultados = vencimientoReportosService.findReportos(criterio, paginacion);
	}
	
	public boolean sePuedeVencer(Date fechaVencimiento) {
		if(fechaVencimiento == null) return false;
		return fechaVencimientoMin.compareTo(fechaVencimiento)>= 0;
	}
	
	public String obtenerReportoPorIdInstruccion() {
		logger.debug("buscarSolicitudPorIdInstruccion");
		Map<String , String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		
		String idInstruccionStr = params.get("idInstruccion");
		logger.debug("idInstruccion: " + idInstruccionStr);
        
		solicitud = vencimientoReportosService.findSolicitud(Long.valueOf(idInstruccionStr));
		
		return null;
	}
	
	/**
	 * Limpia los resultados de la consulta
	 *
	 * @param e
	 */
	public void limpiar(ActionEvent e) {
		paginacion = new EstadoPaginacionDTO();

		paginacion.setRegistrosPorPagina(20);
		criterio = new CriterioVencimientoReportosDTO();
		criterio.setFechaReportoIni(new Date());
		criterio.setFechaReportoFin(new Date());
		criterio.setInstitucionParticipante(getIdFolioInstitucionActual());
		
		consultaEjecutada = false;
		resultados = null;
	}
	
	public String toNuevaSolicitud() {
		logger.debug("toNuevaSolicitud");
		Map<String , String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		logger.debug(String.valueOf(params));
		String idInstruccionStr = params.get("idInstruccion");
		logger.debug("idInstruccion: "+ idInstruccionStr);
		return "nueva-solicitud";
	}
	
	public String toDetalleSolicitud() {
		logger.debug("toDetalleSolicitud");
		Map<String , String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		logger.debug(String.valueOf(params));
		String idVencimientoStr = params.get("idVencimiento");
		logger.debug("idInstruccion: " +idVencimientoStr);
		return "detalle-solicitud";
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
		ResultadoValidacionDTO resultado = crearCriterio();
		if (resultado.isValido()) {
			buscarOperaciones();
		} else {
			consultaEjecutada = false;
			FacesContext.getCurrentInstance().addMessage("mensajesConsulta", new FacesMessage(FacesMessage.SEVERITY_ERROR, resultado.getMensaje(), resultado.getMensaje()));
		}
	}
	
	/**
	 * Completa el objeto de criterios con los valores capturados por el usuario
	 */
	public ResultadoValidacionDTO crearCriterio() {
		logger.info("crearCriterio");
		
		ResultadoValidacionDTO resultado = new ResultadoValidacionDTO();
		resultado.setValido(true);

		// FILTRAR RESULTADOS POR 
		if(!isUsuarioIndeval()) {
			criterio.setIdInstitucionRestringeResultados(getInstitucionActual().getId());
		}

		//Se valida que se capture ambas fechas.
		if(criterio.getFechaReportoIni() == null || criterio.getFechaReportoFin() == null) {
			resultado.setValido(false);
			resultado.setMensaje("Las fechas de vencimiento son requeridas.");
		}
		
		
		// CUENTA CONTRAPARTE
		//criterio.setCuentaContraparte(cuentaContraparte);
		
		// CUENTA PARTICIPANTE
		//criterio.setCuentaParticipante(cuentaParticipante);
		
		// INSTITUCION CONTRAPARTE
		if (StringUtils.isNotEmpty(criterio.getInstitucionContraparte())) {
			
			String institucionContraparte = criterio.getInstitucionContraparte();
			
			if (institucionContraparte.length() >= 5) {
				this.criterio.setInstitucionContraparte( institucionContraparte.substring(0, 5));
				
				InstitucionDTO instContraparte = consultaCatalogos.buscarInstitucionPorIdFolio(institucionContraparte);
				
				if (instContraparte == null) {
					resultado.setValido(false);
					resultado.setMensaje("La instituci\u00F3n especificada para la contraparte no existe.");
				}else {
					criterio.setIdInstitucionContraparte(instContraparte.getId());
				}
			} else {
				criterio.setIdInstitucionContraparte(DaliConstants.VALOR_COMBO_TODOS);
				criterio.setClaveTipoInstitucionContraparte(institucionContraparte);
				
				List<TipoInstitucionDTO> tiposInstitucion = consultaCatalogos.getConsultaTipoInstitucionService().consultaTipoInstitucionPorPrefijo(
						institucionContraparte);
				if (tiposInstitucion == null || tiposInstitucion.isEmpty() || !tiposInstitucion.get(0).getClaveTipoInstitucion().equals(institucionContraparte)) {
					resultado.setValido(false);
					resultado.setMensaje("El tipo de instituci\u00F3n especificada para la contraparte no existe.");
				}
			}/**/
		} else {
			criterio.setIdInstitucionContraparte(DaliConstants.VALOR_COMBO_TODOS);
		}
		
		// INSTITUCION PARTICIPANTE
		if (StringUtils.isNotEmpty(criterio.getInstitucionParticipante())) {
			String institucionParticipante = criterio.getInstitucionParticipante();
			
			if (institucionParticipante.length() >= 5) {
				this.criterio.setInstitucionParticipante(institucionParticipante.substring(0, 5));
				
				InstitucionDTO instParticipante = consultaCatalogos.buscarInstitucionPorIdFolio(institucionParticipante);

				if (instParticipante == null) {
					resultado.setValido(false);
					resultado.setMensaje("La instituci\u00F3n especificada para el participante no existe.");
				}else {
					criterio.setIdInstitucionParticipante(instParticipante.getId());
				}
			} else {
				criterio.setIdInstitucionParticipante(DaliConstants.VALOR_COMBO_TODOS);
				criterio.setClaveTipoInstitucionParticipante(institucionParticipante);
				
				List<TipoInstitucionDTO> tiposInstitucion = consultaCatalogos.getConsultaTipoInstitucionService().consultaTipoInstitucionPorPrefijo(
						institucionParticipante);
				if (tiposInstitucion == null || tiposInstitucion.isEmpty()
						|| !tiposInstitucion.get(0).getClaveTipoInstitucion().equals(institucionParticipante)) {
					resultado.setValido(false);
					resultado.setMensaje("El tipo de instituci\u00F3n especificada para el participante no existe.");
				}
			}/***/
		} else {
			//criterio.setInstitucionParticipante(new InstitucionDTO());
			criterio.setIdInstitucionParticipante(DaliConstants.VALOR_COMBO_TODOS);
		}
		
		// TIPO VALOR
		if (StringUtils.isNotBlank(criterio.getTipoValor())) {
			TipoValorDTO tv = consultaCatalogos.buscarTipoValorPorClave(criterio.getTipoValor());
			
			if (tv.getId() == DaliConstants.VALOR_COMBO_TODOS) {
				resultado.setValido(false);
				resultado.setMensaje("El tipo de valor especificado no existe.");
			}else {
				criterio.setIdTipoValor(tv.getId());
				criterio.setClaveTipoValor(tv.getClaveTipoValor());
			}
		}else {
			criterio.setIdTipoValor(0);
			criterio.setClaveTipoValor(null);
		} 
		
		// EMISORA
		if (StringUtils.isNotBlank(criterio.getEmisora())) {
			EmisoraDTO emisoraDTO = consultaCatalogos.buscarEmisoraPorNombreCorto(criterio.getEmisora());
			
			if (emisoraDTO.getId() == DaliConstants.VALOR_COMBO_TODOS) {
				resultado.setValido(false);
				resultado.setMensaje("La emisora especificada no existe.");
			}else {
				criterio.setIdEmisora(emisoraDTO.getId());
				criterio.setDescripcionEmisora(emisoraDTO.getDescripcion());
			}
		}else {
			criterio.setIdEmisora(0);
			criterio.setDescripcionEmisora(null);
		}
		
		// SERIE
		/*
		if (StringUtils.isNotBlank(serie)) {
			criterio.getEmision().getSerie().setSerie(serie);
		}/***/
		
		return resultado;
	}
	
	/**
	 * Busca instituciones en el catálogo cuyo nombre corto comience con el
	 * prefijo proporcionado.
	 *
	 * @param prefijo
	 *            el prefijo a consultar en la BD.
	 * @return una lista con objetos de tipo {@link InstitucionDTO} con todas
	 *         las coincidencias encontradas.
	 */
	public List<InstitucionDTO> buscarParticipante(Object value) {
		List<InstitucionDTO> resultado = null;
		List<TipoInstitucionDTO> institucion = new ArrayList<TipoInstitucionDTO>();
		List<InstitucionDTO> listaInstituciones = null;
		String prefijoAjustado = "";

		if (value != null) {
			prefijoAjustado = String.valueOf(value).trim().replace("*", "%");
			listaInstituciones = new ArrayList<InstitucionDTO>();

			institucion = consultaCatalogos.getConsultaTipoInstitucionService().consultaTipoInstitucionPorPrefijo(prefijoAjustado);
			resultado = consultaCatalogos.getConsultaInstitucionService().buscarInstitucionesPorPrefijo(prefijoAjustado);

			if (!institucion.isEmpty()) {

				InstitucionDTO ins = new InstitucionDTO();
				ins.setId(new Long(100) + institucion.get(0).getId());
				ins.setClaveTipoInstitucion(institucion.get(0).getClaveTipoInstitucion());
				ins.setNombreCorto(institucion.get(0).getDescripcion());

				listaInstituciones.add(ins);
			}

			for (InstitucionDTO institucionDTO : resultado) {
				listaInstituciones.add(institucionDTO);
			}
		}

		return listaInstituciones;
	}
	
	/**
	 * método que atiende las solicitudes de autocomplete del catálogo de
	 * cuentas de participante.
	 *
	 * @param prefijo
	 *            Criterio de búsqueda
	 * @return Lista con las cuentas encontradas
	 */
	public List<CuentaDTO> buscarCuentasParticipantePorPrefijo(Object prefijo) {

		return buscarCuentasDeInstitucionPorPrefijo(this.criterio.getInstitucionParticipante(), (String) prefijo);
	}

	/**
	 * método que atiende las solicitudes de autocomplete del catálogo de
	 * cuentas de contraparte.
	 *
	 * @param prefijo
	 *            Criterio de búsqueda
	 * @return Lista con las cuentas encontradas
	 */
	public List<CuentaDTO> buscarCuentasContrapartePorPrefijo(Object prefijo) {

		return buscarCuentasDeInstitucionPorPrefijo(this.criterio.getInstitucionContraparte(), (String) prefijo);
	}
	
	/**
	 * método que atiende las solicitudes de autocomplete del catálogo de tipos
	 * de valor.
	 *
	 * @param prefijo
	 *            Criterio de búsqueda
	 * @return Lista con los tipos de valor encontrados
	 */
	public List<TipoValorDTO> buscarTiposValorPorPrefijo(Object prefijo) {
		return consultaCatalogos.getConsultaTipoValorService().buscarTiposDeValoresPorMercadoYPrefijo(MERCADO_DINERO, String.valueOf(prefijo));

	}

	/**
	 * método que atiende las solicitudes de autocomplete del catálogo de
	 * emisoras.
	 *
	 * @param prefijo
	 *            Criterio de búsqueda
	 * @return Lista con las emisoras encontradas
	 */
	public List<EmisoraDTO> buscarEmisorasPorPrefijo(Object prefijo) {
		String prefijoAjustado = "";
		if (prefijo != null) {
			prefijoAjustado = String.valueOf(prefijo).replace('*', '%');
		}
		return consultaCatalogos.getConsultaEmisoraService().buscarEmisorasPorPrefijo(prefijoAjustado);

	}

	/**
	 * Busca en el catálogo de cuentas aquellas que pertenezcan a una
	 * institución dada y comiencen con un prefijo dado.
	 *
	 * @param idFolioInstitucion
	 *            el identificador y el folio concatenados de la institución a
	 *            consultar.
	 * @param prefijoCuenta
	 *            el prefijo de la cuenta a consultar.
	 * @return una lista con objetos tipo {@link CuentaDTO} los cuales cumplen
	 *         con el criterio de consulta. Una lista vacía en caso de no
	 *         existir coincidencias.
	 */
	private List<CuentaDTO> buscarCuentasDeInstitucionPorPrefijo(String idFolioInstitucion, String prefijoCuenta) {
		List<CuentaDTO> cuentas = new ArrayList<CuentaDTO>();

		if (StringUtils.isNotEmpty(idFolioInstitucion) && StringUtils.isNotEmpty(prefijoCuenta)) {
			InstitucionDTO institucion = consultaCatalogos.buscarInstitucionPorIdFolio(idFolioInstitucion);
			if (institucion != null) {
				CuentaDTO criterio = new CuentaDTO();
				criterio.setInstitucion(institucion);
				criterio.setTipoTenencia(new TipoTenenciaDTO());

				criterio.getTipoTenencia().setTipoCuenta(new TipoCuentaDTO());
				criterio.getTipoTenencia().getTipoCuenta().setId(TipoCuentaDTO.CUENTA_NOMBRADA);
				criterio.getTipoTenencia().setTipoNaturaleza(new TipoNaturalezaDTO());
				criterio.getTipoTenencia().getTipoNaturaleza().setId(String.valueOf(DaliConstants.VALOR_COMBO_TODOS));
				criterio.getTipoTenencia().setTipoCustodia(TipoCustodiaConstants.VALORES);
				String prefijoAjustado = "";
				if (prefijoCuenta != null) {
					prefijoAjustado = String.valueOf(prefijoCuenta).replace('*', '%');
				}
				criterio.setNumeroCuenta(institucion.getClaveTipoInstitucion() + institucion.getFolioInstitucion() + prefijoAjustado);

				cuentas = consultaCatalogos.getConsultaCuentaService().buscarCuentasPorFragmentoNumeroCuenta(criterio);
			}
		}

		return cuentas;
	}
	
	
	public void setConsultaCatalogos(ConsultaCatalogosFacade consultaCatalogos) {
		this.consultaCatalogos = consultaCatalogos;
	}
	
	public void setVencimientoReportosService(VencimientoReportosService vencimientoReportosService) {
		this.vencimientoReportosService = vencimientoReportosService;
	}

	public CriterioVencimientoReportosDTO getCriterio() {
		return criterio;
	}

	public void setCriterio(CriterioVencimientoReportosDTO criterio) {
		this.criterio = criterio;
	}

	public boolean isConsultaEjecutada() {
		return consultaEjecutada;
	}

	public void setConsultaEjecutada(boolean consultaEjecutada) {
		this.consultaEjecutada = consultaEjecutada;
	}

	public List<ConsultaOperacionesMatch> getResultados() {
		return resultados;
	}

	public void setResultados(List<ConsultaOperacionesMatch> resultados) {
		this.resultados = resultados;
	}

	public VencimientoAnticipadoDTO getSolicitud() {
		return solicitud;
	}

	public void setSolicitud(VencimientoAnticipadoDTO solicitud) {
		this.solicitud = solicitud;
	}

	public Integer getNuevoPlazo() {
		return nuevoPlazo;
	}

	public void setNuevoPlazo(Integer nuevoPlazo) {
		this.nuevoPlazo = nuevoPlazo;
	}

	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	public Date getFechaVencimientoMin() {
		return fechaVencimientoMin;
	}

	public void setFechaVencimientoMin(Date fechaVencimientoMin) {
		this.fechaVencimientoMin = fechaVencimientoMin;
	}

	public boolean isValidatioErrors() {
		return validatioErrors;
	}

	public void setValidatioErrors(boolean validatioErrors) {
		this.validatioErrors = validatioErrors;
	}

	public boolean isFacultadLectura() {
		return facultadLectura;
	}

	public void setFacultadLectura(boolean facultadLectura) {
		this.facultadLectura = facultadLectura;
	}

	public boolean isFacultadEscritura() {
		return facultadEscritura;
	}

	public void setFacultadEscritura(boolean facultadEscritura) {
		this.facultadEscritura = facultadEscritura;
	}

	public boolean isPuedeConsultar() {
		return puedeConsultar;
	}

	public void setPuedeConsultar(boolean puedeConsultar) {
		this.puedeConsultar = puedeConsultar;
	}
	
}
