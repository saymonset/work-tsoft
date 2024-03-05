/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * SeleccionarPosicionBean.java
 * 14/04/2008
 */
package com.indeval.portaldali.presentation.controller.common;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.indeval.portaldali.middleware.dto.BovedaDTO;
import com.indeval.portaldali.middleware.dto.CriterioOrdenamientoDTO;
import com.indeval.portaldali.middleware.dto.CuentaDTO;
import com.indeval.portaldali.middleware.dto.EmisionDTO;
import com.indeval.portaldali.middleware.dto.InstitucionDTO;
import com.indeval.portaldali.middleware.dto.PosicionDTO;
import com.indeval.portaldali.middleware.dto.SerieDTO;
import com.indeval.portaldali.middleware.dto.TipoCuentaDTO;
import com.indeval.portaldali.middleware.dto.TipoNaturalezaDTO;
import com.indeval.portaldali.middleware.dto.TipoTenenciaDTO;
import com.indeval.portaldali.middleware.dto.util.EstadoPaginacionExtended;
import com.indeval.portaldali.middleware.services.common.constants.DaliConstants;
import com.indeval.portaldali.middleware.services.common.constants.TipoCuentaConstants;
import com.indeval.portaldali.middleware.services.common.constants.TipoCustodiaConstants;
import com.indeval.portaldali.middleware.services.estadocuenta.ConsultaPosicionService;
import com.indeval.portaldali.middleware.services.util.FechasUtilService;
import com.indeval.portaldali.middleware.services.util.UtilServices;
import com.indeval.portaldali.presentation.util.ConsultaCatalogosFacade;

/**
 * Backing bean para atender las solicitudes de búsqueda de posiciones
 * 
 * @author Emigdio
 * 
 */
public class SeleccionarPosicionBean extends ControllerBase {
	/**
	 * Resultado de posiciones encontradas
	 */
	List<PosicionDTO> listaPosiciones = null;
	/**
	 * Dao para el acceso a la consulta de posición
	 */
	ConsultaPosicionService consultaPosicionService = null;
	/**
	 * Clase de acceso a la consulta de los catálogos utilizados
	 */
	ConsultaCatalogosFacade consultaCatalogos = null;
	/**
	 * Criterio de consulta
	 */
	PosicionDTO criterio = new PosicionDTO();

	/** DTO que contiene el criterio de ordenamiento de las posiciones */
	CriterioOrdenamientoDTO orden = null;

	/**
	 * Identificadores del mercado a filtrar
	 */
	List<Long> idMercado = null;

	/** El estado de la paginación */
	private EstadoPaginacionExtended paginacionExtendida = new EstadoPaginacionExtended();

	/**
	 * Servicio para el acceso al cálculo de fechas
	 */
	private FechasUtilService fechasUtilService = null;

	
	private UtilServices utilService;

	/**
	 * constructor predeterminado
	 */
	public SeleccionarPosicionBean() {
		paginacionExtendida.setRegistrosPorPagina(20);
	}

	/**
	 * Obtiene el valor del atributo paginacionExtendida
	 * 
	 * @return el valor del atributo paginacionExtendida
	 */
	public EstadoPaginacionExtended getPaginacionExtendida() {
		return paginacionExtendida;
	}

	/**
	 * Establece el valor del atributo paginacionExtendida
	 * 
	 * @param paginacionExtendida
	 *            el valor del atributo paginacionExtendida a establecer
	 */
	public void setPaginacionExtendida(EstadoPaginacionExtended paginacionExtendida) {
		this.paginacionExtendida = paginacionExtendida;
	}

	/**
	 * Inicializa los resultados de la consulta de posiciones del participante.
	 * 
	 * @return null
	 */
	public String getInit() {
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		
		if(getInstitucionActual() != null){
		
			criterio.setCuenta(new CuentaDTO());
			
			criterio.getCuenta().setTipoTenencia(new TipoTenenciaDTO());
			criterio.getCuenta().getTipoTenencia().setTipoCustodia(TipoCustodiaConstants.VALORES);
			criterio.getCuenta().getTipoTenencia().setTipoCuenta(new TipoCuentaDTO(TipoCuentaConstants.TIPO_NOMBRADA, ""));
			criterio.getCuenta().getTipoTenencia().setTipoNaturaleza(new TipoNaturalezaDTO(TipoNaturalezaDTO.PASIVO, ""));
			InstitucionDTO institucion = null;
			if (params.get("idFolioInstitucion") != null) {
				institucion = consultaCatalogos.buscarInstitucionPorIdFolio(params.get("idFolioInstitucion"));
			}
			if (institucion != null) {
	
				if (!StringUtils.isEmpty(params.get("cuenta"))) {
					criterio.getCuenta().setNumeroCuenta(params.get("cuenta"));
				} else {
					criterio.getCuenta().setNumeroCuenta("-1");
				}
	
				criterio.setEmision(new EmisionDTO());
	
				if (!StringUtils.isEmpty(params.get("tv"))) {
					criterio.getEmision().setTipoValor(consultaCatalogos.buscarTipoValorPorClave(params.get("tv")));
	
				}
				if (!StringUtils.isEmpty(params.get("emisora"))) {
					criterio.getEmision().setEmisora(consultaCatalogos.buscarEmisoraPorNombreCorto(params.get("emisora")));
				}
	
				if (!StringUtils.isEmpty(params.get("serie") != null ? params.get("serie").trim() : null)) {
					criterio.getEmision().setSerie(new SerieDTO());
					criterio.getEmision().getSerie().setSerie(params.get("serie").trim());
				}
				if (!StringUtils.isEmpty(params.get("isin"))) {
					criterio.getEmision().setIsin(params.get("isin"));
				}
				if (!StringUtils.isEmpty(params.get("cupon"))) {
					criterio.getEmision().setCupon(params.get("cupon"));
				}
	
				if (!StringUtils.isEmpty(params.get("idBoveda"))) {
					criterio.setBoveda(new BovedaDTO());
					criterio.getBoveda().setId(NumberUtils.toLong(params.get("idBoveda"), DaliConstants.VALOR_COMBO_TODOS));
				}
	
				criterio.getCuenta().setInstitucion(institucion);
				criterio.setFechaFinal(new Date());
	
				incializarEstadoPaginacionExtendida(consultaPosicionService.obtenerProyeccionDeConsultaPosicionesYEmisionesVigentes(criterio, idMercado
						.toArray(new Long[] {})));
				listaPosiciones = consultaPosicionService.consultarPosicionesYEmisionesVigentes(criterio, paginacionExtendida, idMercado.toArray(new Long[] {}));
				for (PosicionDTO posicionDTO : listaPosiciones) {
					if (getInstitucionActual().getId() != posicionDTO.getCuenta().getInstitucion().getId() && !isUsuarioIndeval()) {
						posicionDTO.setPosicion("");
						posicionDTO.setPosicionDisponible("");
						posicionDTO.setPosicionNoDisponible("");
					}
					if (posicionDTO.getEmision().getFechaVencimiento() != null) {
						posicionDTO
								.getEmision()
								.setDiasVigentes((int)utilService.dateDiff(fechasUtilService.getCurrentDate(),posicionDTO.getEmision().getFechaVencimiento()));
	
					}
				}
			} else {
	
			}
		}
		return null;
	}
	
	/**
	 * Prepara el objeto EstadoPaginacionDTO con los resultados enviados como
	 * parámetro.
	 * 
	 * @param numeroResultados
	 *            número de resultados obtenidos de la consulta
	 */
	protected void incializarEstadoPaginacionExtendida(long numeroResultados) {
		paginacionExtendida.setTotalResultados((int) numeroResultados);
		if (paginacionExtendida.getTotalResultados() > 0) {
			paginacionExtendida.setNumeroPagina(1);
			paginacionExtendida.setTotalPaginas((int) Math.ceil((double) paginacionExtendida
					.getTotalResultados()
					/ (double) paginacionExtendida.getRegistrosPorPagina()));
		} else {
			paginacionExtendida.setNumeroPagina(0);
			paginacionExtendida.setTotalPaginas(0);
		}
		paginacionExtendida.setIndice(0);
		paginacionExtendida.setSubindice(0);
	}
	
	/**
	 * Invoca las funciones de navegación de página de la consulta principal de
	 * la pantalla
	 * 
	 * @param e
	 *            Evento generado durante la solicitud
	 */
	public void navegarPorResultados(ActionEvent e) {
		String navegacion = (String) e.getComponent().getAttributes().get("navegacion");

		try {
			paginacionExtendida.getClass().getMethod(navegacion).invoke(paginacionExtendida);
		} catch (Exception ex) {
			//Logger.getLogger(this.getClass()).error("Error de invocación de método al navega por los resultados principales", ex);
		}

		listaPosiciones = consultaPosicionService.consultarPosicionesYEmisionesVigentes(criterio, paginacionExtendida, idMercado.toArray(new Long[] {}));
		for (PosicionDTO posicionDTO : listaPosiciones) {
			if (getInstitucionActual().getId() != posicionDTO.getCuenta().getInstitucion().getId() && !isUsuarioIndeval()) {
				posicionDTO.setPosicion("");
				posicionDTO.setPosicionDisponible("");
				posicionDTO.setPosicionNoDisponible("");
			}
			if (posicionDTO.getEmision().getFechaVencimiento() != null) {
				posicionDTO
						.getEmision()
						.setDiasVigentes((int)utilService.dateDiff(fechasUtilService.getCurrentDate(),posicionDTO.getEmision().getFechaVencimiento()));
									

			}
		}
	}

	/**
	 * Obtiene el campo listaPosiciones
	 * 
	 * @return listaPosiciones
	 */
	public List<PosicionDTO> getListaPosiciones() {
		return listaPosiciones;
	}

	/**
	 * Asigna el campo listaPosiciones
	 * 
	 * @param listaPosiciones
	 *            el valor de listaPosiciones a asignar
	 */
	public void setListaPosiciones(List<PosicionDTO> listaPosiciones) {
		this.listaPosiciones = listaPosiciones;
	}

	/**
	 * Obtiene el campo criterio
	 * 
	 * @return criterio
	 */
	public PosicionDTO getCriterio() {
		return criterio;
	}

	/**
	 * Asigna el campo criterio
	 * 
	 * @param criterio
	 *            el valor de criterio a asignar
	 */
	public void setCriterio(PosicionDTO criterio) {
		this.criterio = criterio;
	}

	/**
	 * Obtiene el campo consultaPosicionService
	 * 
	 * @return consultaPosicionService
	 */
	public ConsultaPosicionService getConsultaPosicionService() {
		return consultaPosicionService;
	}

	/**
	 * Asigna el campo consultaPosicionService
	 * 
	 * @param consultaPosicionService
	 *            el valor de consultaPosicionService a asignar
	 */
	public void setConsultaPosicionService(ConsultaPosicionService consultaPosicionService) {
		this.consultaPosicionService = consultaPosicionService;
	}

	/**
	 * Obtiene el campo consultaCatalogos
	 * 
	 * @return consultaCatalogos
	 */
	public ConsultaCatalogosFacade getConsultaCatalogos() {
		return consultaCatalogos;
	}

	/**
	 * Asigna el campo consultaCatalogos
	 * 
	 * @param consultaCatalogos
	 *            el valor de consultaCatalogos a asignar
	 */
	public void setConsultaCatalogos(ConsultaCatalogosFacade consultaCatalogos) {
		this.consultaCatalogos = consultaCatalogos;
	}

	/**
	 * Obtiene el campo fechasUtilService
	 * 
	 * @return fechasUtilService
	 */
	public FechasUtilService getFechasUtilService() {
		return fechasUtilService;
	}

	/**
	 * Asigna el campo fechasUtilService
	 * 
	 * @param fechasUtilService
	 *            el valor de fechasUtilService a asignar
	 */
	public void setFechasUtilService(FechasUtilService fechasUtilService) {
		this.fechasUtilService = fechasUtilService;
	}

	/**
	 * @return the idsMercado
	 */
	public List<Long> getIdMercado() {
		return idMercado;
	}

	/**
	 * @param idsMercado
	 *            the idsMercado to set
	 */
	public void setIdMercado(List<Long> idMercado) {
		this.idMercado = idMercado;
	}

	/**
	 * @return the orden
	 */
	public CriterioOrdenamientoDTO getOrden() {
		return orden;
	}

	/**
	 * @param orden
	 *            the orden to set
	 */
	public void setOrden(CriterioOrdenamientoDTO orden) {
		this.orden = orden;
	}

	public void setUtilService(UtilServices utilService) {
		this.utilService = utilService;
	}

	

}
