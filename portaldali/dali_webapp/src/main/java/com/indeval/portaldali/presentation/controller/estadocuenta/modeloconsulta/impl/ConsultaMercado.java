/**
 * 2H Software
 * Sistema de Consulta de Estado de Cuenta - Indeval
 */
package com.indeval.portaldali.presentation.controller.estadocuenta.modeloconsulta.impl;

import java.util.List;

import com.indeval.portaldali.middleware.dto.MercadoDTO;
import com.indeval.portaldali.middleware.services.common.ConsultaMercadoService;
import com.indeval.portaldali.middleware.services.common.constants.DaliConstants;
import com.indeval.portaldali.presentation.controller.estadocuenta.modeloconsulta.ConsultaAbstract;

/**
 * Representa una clase que sirve para implementar la consulta al catálogo de Mercados,
 * esta clase sirve para consultar los mercados disponibles en la base de datos.
 * @author Emigdio Hernández
 * @version 1.0
 */
public class ConsultaMercado extends ConsultaAbstract<MercadoDTO> {
	/**
	 * Servicio de negocio para el acceso al catálogo de mercados
	 */
	private ConsultaMercadoService consultaMercadoService = null;
	
	@Override
	public List<MercadoDTO> getPaginaDeResultados() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MercadoDTO> getResultados() {
		return consultaMercadoService.buscarMercados();
	}

	public MercadoDTO getOpcionSeleccionada() {
		if(opcionSeleccionada == null){
			opcionSeleccionada = new MercadoDTO();
			opcionSeleccionada.setId(-1);
			opcionSeleccionada.setDescripcion("TODOS");
			
		}
		return opcionSeleccionada;
	}

	public void setOpcionSeleccionada(MercadoDTO opcion) {
		opcionSeleccionada = null;
		if(opcion != null){
			if(DaliConstants.ID_MERCADO_DINERO != opcion.getId()) {
				opcionSeleccionada = consultaMercadoService.buscarMercadoPorId(opcion.getId());
			} else {
				opcionSeleccionada = opcion;
				opcionSeleccionada.setDescripcion(DaliConstants.DESCRIPCION_MERCADO_DINERO);
			}
		}
		
		if(opcionSeleccionada==null){
			opcionSeleccionada = new MercadoDTO();
			opcionSeleccionada.setDescripcion("TODOS");
			opcionSeleccionada.setId(-1);
		}
		
	}

	/**
	 * Obtiene el campo consultaMercadoService
	 * @return  consultaMercadoService
	 */
	public ConsultaMercadoService getConsultaMercadoService() {
		return consultaMercadoService;
	}

	/**
	 * Asigna el valor del campo consultaMercadoService
	 * @param consultaMercadoService el valor de consultaMercadoService a asignar
	 */
	public void setConsultaMercadoService(
			ConsultaMercadoService consultaMercadoService) {
		this.consultaMercadoService = consultaMercadoService;
	}

}
