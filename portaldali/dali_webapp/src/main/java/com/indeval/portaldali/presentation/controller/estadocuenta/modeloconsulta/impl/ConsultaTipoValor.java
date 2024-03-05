/**
 * 2H Software
 * Sistema de Consulta de Estado de Cuenta - Indeval
 */
package com.indeval.portaldali.presentation.controller.estadocuenta.modeloconsulta.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.indeval.portaldali.middleware.dto.TipoValorDTO;
import com.indeval.portaldali.middleware.services.common.ConsultaTipoValorService;
import com.indeval.portaldali.middleware.services.common.constants.DaliConstants;
import com.indeval.portaldali.presentation.controller.estadocuenta.modeloconsulta.ConsultaAbstract;

/**
 * Implementación del modelo de consulta para el tipo de valor.
 * @author Emigdio
 *
 */
public class ConsultaTipoValor extends ConsultaAbstract<TipoValorDTO> {
	
	private ConsultaMercado criterioMercado = null;
	
	private ConsultaTipoValorService consultaTipoValorService = null;
	
	/**
	 * Obtiene el campo criterioMercado
	 * @return  criterioMercado
	 */
	public ConsultaMercado getCriterioMercado() {
		return criterioMercado;
	}
	/**
	 * Asigna el valor del campo criterioMercado
	 * @param criterioMercado el valor de criterioMercado a asignar
	 */
	public void setCriterioMercado(ConsultaMercado criterioMercado) {
		this.criterioMercado = criterioMercado;
	}
	/*
	 * (non-Javadoc)
	 * @see com.indeval.estadocuenta.presentation.model.ConsultaAbstract#getPaginaDeResultados()
	 */
	@Override
	public List<TipoValorDTO> getPaginaDeResultados() {
		// TODO Auto-generated method stub
		return null;
	}
	/*
	 * (non-Javadoc)
	 * @see com.indeval.estadocuenta.presentation.model.ConsultaAbstract#getResultados()
	 */
	@Override
	public List<TipoValorDTO> getResultados() {
		TipoValorDTO criterio = new TipoValorDTO();
		
		criterio.setMercado(criterioMercado.getOpcionSeleccionada());
		
		return consultaTipoValorService.buscarTiposDeValoresPorMercado(criterioMercado.getOpcionSeleccionada());
	}
	/*
	 * (non-Javadoc)
	 * @see com.indeval.estadocuenta.presentation.model.Consulta#getOpcionSeleccionada()
	 */
	public TipoValorDTO getOpcionSeleccionada() {
		if(opcionSeleccionada==null){
			opcionSeleccionada = new TipoValorDTO();
			opcionSeleccionada.setMercado(criterioMercado.getOpcionSeleccionada());
			opcionSeleccionada.setId(-1);
			opcionSeleccionada.setDescripcion("TODOS");
		}
		return opcionSeleccionada;
	}
	/*
	 * (non-Javadoc)
	 * @see com.indeval.estadocuenta.presentation.model.Consulta#setOpcionSeleccionada(java.lang.Object)
	 */
	public void setOpcionSeleccionada(TipoValorDTO opcion) {
		opcionSeleccionada = null;
		if(opcion != null){
			if(opcion.getId() > 0) {
				opcionSeleccionada = consultaTipoValorService.buscarTipoDeValorPorId(opcion.getId());
			}
			if(StringUtils.isNotBlank(opcion.getClaveTipoValor())) {
				opcionSeleccionada = consultaTipoValorService.buscarTipoDeValorPorClave(opcion.getClaveTipoValor());
				if(opcionSeleccionada == null) {
					opcionSeleccionada = new TipoValorDTO();
					opcionSeleccionada.setId(DaliConstants.VALOR_COMBO_NINGUNO);
					opcionSeleccionada.setDescripcion(StringUtils.EMPTY);
					opcionSeleccionada.setClaveTipoValor(opcion.getClaveTipoValor());
				}
			}
		}
		
		if(opcionSeleccionada==null){
			opcionSeleccionada = new TipoValorDTO();
			opcionSeleccionada.setMercado(criterioMercado.getOpcionSeleccionada());
			opcionSeleccionada.setId(-1);
			opcionSeleccionada.setDescripcion("TODOS");
			opcionSeleccionada.setClaveTipoValor(StringUtils.EMPTY);
		}
		
	}
	/**
	 * Obtiene el campo consultaTipoValorService
	 * @return  consultaTipoValorService
	 */
	public ConsultaTipoValorService getConsultaTipoValorService() {
		return consultaTipoValorService;
	}
	/**
	 * Asigna el valor del campo consultaTipoValorService
	 * @param consultaTipoValorService el valor de consultaTipoValorService a asignar
	 */
	public void setConsultaTipoValorService(
			ConsultaTipoValorService consultaTipoValorService) {
		this.consultaTipoValorService = consultaTipoValorService;
	}

}
