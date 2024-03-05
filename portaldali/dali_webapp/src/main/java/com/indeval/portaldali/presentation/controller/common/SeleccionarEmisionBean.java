/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * SeleccionarPosicionBean.java
 * 17/04/2008
 */
package com.indeval.portaldali.presentation.controller.common;

import java.util.List;

import javax.faces.model.SelectItem;

import com.indeval.portaldali.middleware.dto.EmisoraDTO;
import com.indeval.portaldali.middleware.dto.TipoValorDTO;
import com.indeval.portaldali.middleware.services.common.ConsultaBovedaService;
import com.indeval.portaldali.presentation.util.ConsultaCatalogosFacade;

/**
 * Backing bean para atender las solicitudes de búsqueda de emisiones
 * 
 * @author Emigdio Hernández
 * 
 */
public class SeleccionarEmisionBean extends ControllerBase {
	
	
	private List<Long> idMercado = null;
	
	/**
	 * Acceso a la consulta de catálogos
	 * 
	 */
	ConsultaCatalogosFacade consultaCatalogos = null;

	/** Servicio para consultar las bóvedas disponibles en la base de datos. */
	private ConsultaBovedaService consultaBovedaService = null;

	/**
	 * método que atiende las solicitudes de autocomplete del catálogo de tipos
	 * de valor.
	 * 
	 * @param prefijo
	 *            Criterio de búsqueda
	 * @return Lista con los tipos de valor encontrados
	 */
	public List<TipoValorDTO> buscarTiposValorPorPrefijo(Object prefijo) {
		
		
		return consultaCatalogos.getConsultaTipoValorService().buscarTiposDeValoresPorMercados(idMercado.toArray(new Long[]{}), String.valueOf(prefijo));

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
			prefijoAjustado = String.valueOf(prefijo.toString().trim()).replace('*', '%');
		}
		return consultaCatalogos.getConsultaEmisoraService().buscarEmisorasPorPrefijo(prefijoAjustado);

	}

	/**
	 * Obtiene una lista de <code>SelectItem</code> para popular el combo box
	 * de elección de bóveda de valor
	 * 
	 * @return
	 */
	public List<SelectItem> getBovedasValor() {
		return consultaCatalogos.getSelectItemsBovedasValor();
	}

	/**
	 * Obtiene una lista de <code>SelectItem</code> para popular el combo box
	 * de elección de bóveda de efectivo.
	 * 
	 * @return
	 */
	public List<SelectItem> getBovedasEfectivo() {
		return consultaCatalogos.getSelectItemsBovedasEfectivo();
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
	 * @return the bovedaService
	 */
	public ConsultaBovedaService getBovedaService() {
		return consultaBovedaService;
	}

	/**
	 * @param bovedaService the bovedaService to set
	 */
	public void setBovedaService(ConsultaBovedaService bovedaService) {
		this.consultaBovedaService = bovedaService;
	}

	/**
	 * @return the consultaBovedaService
	 */
	public ConsultaBovedaService getConsultaBovedaService() {
		return consultaBovedaService;
	}

	/**
	 * @param consultaBovedaService the consultaBovedaService to set
	 */
	public void setConsultaBovedaService(ConsultaBovedaService consultaBovedaService) {
		this.consultaBovedaService = consultaBovedaService;
	}

	/**
	 * @return the idMercado
	 */
	public List<Long> getIdMercado() {
		return idMercado;
	}

	/**
	 * @param idMercado the idMercado to set
	 */
	public void setIdMercado(List<Long> idMercado) {
		this.idMercado = idMercado;
	}

	
	

	

}
