/**
 * 2H Software
 * Sistema de Consulta de Estado de Cuenta - Indeval
 */
package com.indeval.portaldali.presentation.controller.estadocuenta.modeloconsulta.impl;

import java.util.List;

import com.indeval.portaldali.middleware.dto.TipoInstruccionDTO;
import com.indeval.portaldali.middleware.services.common.ConsultaTipoInstruccionService;
import com.indeval.portaldali.presentation.controller.estadocuenta.modeloconsulta.ConsultaAbstract;

/**
 * Representa la implementación de la funcionalidad para obtener la consulta de los valores
 * que puede tener el tipo de instrucción de una operación.
 * @author Emigdio Hernández
 * @version 1.0
 */
public class ConsultaTipoInstruccion extends ConsultaAbstract<TipoInstruccionDTO> {
	/**
	 * Servicio para el acceso al catálogo de tipos de instrucción.
	 */
	private ConsultaTipoInstruccionService consultaTipoInstruccionService = null;
	/**
	 * Indica si los tipos de custodia válidos para la consulta de instrucciones
	 */
	private String tiposCustodia = null;
	
	
	/* (non-Javadoc)
	 * @see com.indeval.estadocuenta.presentation.model.ConsultaAbstract#getPaginaDeResultados()
	 */
	public List<TipoInstruccionDTO> getPaginaDeResultados() {
		
		return null;
	}

	/* (non-Javadoc)
	 * @see com.indeval.estadocuenta.presentation.model.ConsultaAbstract#getResultados()
	 */
	public List<TipoInstruccionDTO> getResultados() {
		return consultaTipoInstruccionService.buscarTiposDeInstruccion(tiposCustodia);
	}

	/* (non-Javadoc)
	 * @see com.indeval.estadocuenta.presentation.model.Consulta#getOpcionSeleccionada()
	 */
	public TipoInstruccionDTO getOpcionSeleccionada() {
		if(opcionSeleccionada == null){
			opcionSeleccionada = new TipoInstruccionDTO();
			opcionSeleccionada.setIdTipoInstruccion(-1L);
			opcionSeleccionada.setDescripcion("TODOS");
			opcionSeleccionada.setNombreCorto("TODOS");
		}
		return opcionSeleccionada;
	}

	/* (non-Javadoc)
	 * @see com.indeval.estadocuenta.presentation.model.Consulta#setOpcionSeleccionada(java.lang.Object)
	 */
	public void setOpcionSeleccionada(TipoInstruccionDTO opcion) {
		opcionSeleccionada = null;
		if(opcion != null){
			opcionSeleccionada = consultaTipoInstruccionService.buscarTipoDeInstruccionPorClave(opcion.getNombreCorto());
		}
		
		if(opcionSeleccionada==null){
			getOpcionSeleccionada();
		}

	}

	/**
	 * Obtiene el campo consultaTipoInstruccionService
	 * @return  consultaTipoInstruccionService
	 */
	public ConsultaTipoInstruccionService getConsultaTipoInstruccionService() {
		return consultaTipoInstruccionService;
	}

	/**
	 * Asigna el valor del campo consultaTipoInstruccionService
	 * @param consultaTipoInstruccionService el valor de consultaTipoInstruccionService a asignar
	 */
	public void setConsultaTipoInstruccionService(
			ConsultaTipoInstruccionService consultaTipoInstruccionService) {
		this.consultaTipoInstruccionService = consultaTipoInstruccionService;
	}

	

	/**
	 * Obtiene el campo tiposCustodia
	 * @return  tiposCustodia
	 */
	public String getTiposCustodia() {
		return tiposCustodia;
	}

	/**
	 * Asigna el valor del campo tiposCustodia
	 * @param tiposCustodia el valor de tiposCustodia a asignar
	 */
	public void setTiposCustodia(String tiposCustodia) {
		this.tiposCustodia = tiposCustodia;
	}

}
