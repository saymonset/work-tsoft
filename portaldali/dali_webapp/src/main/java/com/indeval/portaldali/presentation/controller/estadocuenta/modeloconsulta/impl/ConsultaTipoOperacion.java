/**
 * 2H Software
 * Sistema de Consulta de Estado de Cuenta - Indeval
 */
package com.indeval.portaldali.presentation.controller.estadocuenta.modeloconsulta.impl;

import java.util.List;

import com.indeval.portaldali.middleware.dto.TipoOperacionDTO;
import com.indeval.portaldali.middleware.services.common.ConsultaTipoOperacionService;
import com.indeval.portaldali.presentation.controller.estadocuenta.modeloconsulta.ConsultaAbstract;

/**
 * Representa la implementación de la funcionalidad para obtener la consulta de los valores
 * que puede tener el tipo de operación de una operación.
 * @author Emigdio Hernández
 * @version 1.0
 */
public class ConsultaTipoOperacion extends ConsultaAbstract<TipoOperacionDTO> {
	/**
	 * Servicio para el acceso al catálogo de tipos de instrucción.
	 */
	private ConsultaTipoOperacionService consultaTipoOperacionService = null;
	/**
	 * Indica si las instrucciones son de efectivo o de valor
	 */
	private String tiposCustodia = null;
	
	
	/* (non-Javadoc)
	 * @see com.indeval.estadocuenta.presentation.model.ConsultaAbstract#getPaginaDeResultados()
	 */
	public List<TipoOperacionDTO> getPaginaDeResultados() {
		
		return null;
	}

	/* (non-Javadoc)
	 * @see com.indeval.estadocuenta.presentation.model.ConsultaAbstract#getResultados()
	 */
	public List<TipoOperacionDTO> getResultados() {
		return consultaTipoOperacionService.buscatTiposOperacion(tiposCustodia);
	}

	/* (non-Javadoc)
	 * @see com.indeval.estadocuenta.presentation.model.Consulta#getOpcionSeleccionada()
	 */
	public TipoOperacionDTO getOpcionSeleccionada() {
		if(opcionSeleccionada == null){
			opcionSeleccionada = new TipoOperacionDTO();
			opcionSeleccionada.setId(-1L);
			opcionSeleccionada.setDescripcion("TODOS");
			opcionSeleccionada.setClaveTipoOperacion("TODOS");
		}
		return opcionSeleccionada;
	}

	/* (non-Javadoc)
	 * @see com.indeval.estadocuenta.presentation.model.Consulta#setOpcionSeleccionada(java.lang.Object)
	 */
	public void setOpcionSeleccionada(TipoOperacionDTO opcion) {
		opcionSeleccionada = null;
		if(opcion != null){
			opcionSeleccionada = consultaTipoOperacionService.buscarTipoOperaciconPorId(opcion.getId());
		}
		
		if(opcionSeleccionada==null){
			getOpcionSeleccionada();
		}

	}

	

	

	

	/**
	 * Obtiene el campo consultaTipoOperacionService
	 * @return  consultaTipoOperacionService
	 */
	public ConsultaTipoOperacionService getConsultaTipoOperacionService() {
		return consultaTipoOperacionService;
	}

	/**
	 * Asigna el valor del campo consultaTipoOperacionService
	 * @param consultaTipoOperacionService el valor de consultaTipoOperacionService a asignar
	 */
	public void setConsultaTipoOperacionService(
			ConsultaTipoOperacionService consultaTipoOperacionService) {
		this.consultaTipoOperacionService = consultaTipoOperacionService;
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
