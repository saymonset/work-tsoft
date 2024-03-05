/**
 * 2H Software
 * Sistema de Consulta de Estado de Cuenta - Indeval
 * 
 * Creado: 07 de Diciembre de 2007
 */
package com.indeval.portaldali.presentation.controller.estadocuenta.modeloconsulta.impl;

import java.util.List;

import com.indeval.portaldali.middleware.dto.EmisoraDTO;
import com.indeval.portaldali.middleware.services.common.ConsultaEmisoraService;
import com.indeval.portaldali.middleware.services.common.constants.DaliConstants;
import com.indeval.portaldali.presentation.controller.estadocuenta.modeloconsulta.ConsultaAbstract;

/**
 * Implementación del esquema de consulta para las emisoras de una emisión.
 * 
 * @author José Antonio Huizar Moreno
 * @version 1.0
 */
public class ConsultaEmisora extends ConsultaAbstract<EmisoraDTO> {
	
	/** Servicio de negocio para la consulta de emisoras */
	private ConsultaEmisoraService consultaEmisoraService = null;
	
	/* (non-Javadoc)
	 * @see com.indeval.estadocuenta.presentation.model.ConsultaAbstract#getPaginaDeResultados()
	 */
	@Override
	public List<EmisoraDTO> getPaginaDeResultados() {

		return null;
	}

	/* (non-Javadoc)
	 * @see com.indeval.estadocuenta.presentation.model.ConsultaAbstract#getResultados()
	 */
	@Override
	public List<EmisoraDTO> getResultados() {
		
		return consultaEmisoraService.buscarEmisoras();
	}
	
	/**
	 * Busca emisoras en el catálogo cuya descripción comiencen con el prefijo proporcionado.
	 * 
	 * @param prefijo el prefijo a consultar en la BD.
	 * @return una lista con objetos de tipo {@link EmisoraDTO} con todas las coincidencias encontradas.
	 */
	public List<EmisoraDTO> buscarEmisorasPorPrefijo(String prefijo) {
		
		return consultaEmisoraService.buscarEmisorasPorPrefijo(prefijo);
	}
	
	/* (non-Javadoc)
	 * @see com.indeval.estadocuenta.presentation.model.Consulta#getOpcionSeleccionada()
	 */
	public EmisoraDTO getOpcionSeleccionada() {
		if(opcionSeleccionada==null){
			opcionSeleccionada = new EmisoraDTO();
			opcionSeleccionada.setId(-1);
			opcionSeleccionada.setDescripcion("TODAS");
		}
		return opcionSeleccionada;
	}

	/* (non-Javadoc)
	 * @see com.indeval.estadocuenta.presentation.model.Consulta#setOpcionSeleccionada(java.lang.Object)
	 */
	public void setOpcionSeleccionada(EmisoraDTO opcion) {
		opcionSeleccionada = null;
		if(opcion != null && opcion.getId() == DaliConstants.VALOR_COMBO_NINGUNO){
			opcionSeleccionada = opcion;
			return;
		}

		if(opcion !=null){
			opcionSeleccionada = consultaEmisoraService.buscarEmisoraPorId(opcion.getId());
		}
		
		if(opcionSeleccionada==null){
			opcionSeleccionada = new EmisoraDTO();
			opcionSeleccionada.setId(-1);
			opcionSeleccionada.setDescripcion("TODAS");
		}
	}

	/**
	 * Obtiene el campo consultaEmisoraService
	 * @return  consultaEmisoraService
	 */
	public ConsultaEmisoraService getConsultaEmisoraService() {
		return consultaEmisoraService;
	}

	/**
	 * Asigna el valor del campo consultaEmisoraService
	 * @param consultaEmisoraService el valor de consultaEmisoraService a asignar
	 */
	public void setConsultaEmisoraService(
			ConsultaEmisoraService consultaEmisoraService) {
		this.consultaEmisoraService = consultaEmisoraService;
	}

}
