/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * SeleccionarPosicionBean.java
 * 17/04/2008
 */
package com.indeval.portaldali.presentation.controller.common;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import com.indeval.portaldali.middleware.dto.DivisaDTO;
import com.indeval.portaldali.presentation.util.ConsultaCatalogosFacade;

/**
 * Backing bean para atender las solicitudes de llenado de select Items
 * 
 * @author Emigdio Hernández
 * 
 */
public class CatalogosBean extends ControllerBase{
	
	/**
	 * Acceso a la consulta de catálogos 
	 * 
	 */
	private ConsultaCatalogosFacade consultaCatalogos = null;

	
	
	
	/**
	 * Obtiene las opciones disponibles en el catalogo de divisas
	 * @return Lista de SelectItem con las divisas existentes en la base
	 */
	public List<SelectItem> getOpcionesComboDivisa() {
		List<SelectItem> opcionesCombo = new ArrayList<SelectItem>();		
		//Agregamos la opción de todos
		//Agregamos las divisas existentes en el catalogo
		
		
		opcionesCombo.add(new SelectItem(new DivisaDTO(-1), "TODAS"));
		
		for(DivisaDTO divisa : consultaCatalogos.getConsultaDivisaService().consultarDivisas(null)) {
			opcionesCombo.add(new SelectItem(divisa, divisa.getDescripcion()));
		}		
		return opcionesCombo;
	}
	
	
	/**
	 * Obtiene el campo consultaCatalogos
	 * @return  consultaCatalogos
	 */
	public ConsultaCatalogosFacade getConsultaCatalogos() {
		return consultaCatalogos;
	}

	/**
	 * Asigna el campo consultaCatalogos
	 * @param consultaCatalogos el valor de consultaCatalogos a asignar
	 */
	public void setConsultaCatalogos(ConsultaCatalogosFacade consultaCatalogos) {
		this.consultaCatalogos = consultaCatalogos;
	}

	/**
	 * Obtiene una lista de <code>SelectItem</codes>s para llenar el combo box
	 * de elecci&oacute;n de b&oacute;veda de valor.
	 * 
	 * @return
	 */
	public List<SelectItem> getBovedasValor() {
		return consultaCatalogos.getSelectItemsBovedasValor();
	}

	/**
	 * Obtiene una lista de <code>SelectItem</code> para popular el combo box
	 * de elecci&oacute;n de b&oacute;veda de efectivo.
	 * 
	 * @return
	 */
	public List<SelectItem> getBovedasEfectivo() {
		return consultaCatalogos.getSelectItemsBovedasEfectivo();
	}

}
