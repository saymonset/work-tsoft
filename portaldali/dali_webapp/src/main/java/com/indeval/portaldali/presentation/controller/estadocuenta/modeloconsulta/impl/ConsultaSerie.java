/**
 * 2H Software
 * Sistema de Consulta de Estado de Cuenta - Indeval
 */
package com.indeval.portaldali.presentation.controller.estadocuenta.modeloconsulta.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.indeval.portaldali.middleware.dto.SerieDTO;
import com.indeval.portaldali.middleware.services.common.ConsultaEmisionService;
import com.indeval.portaldali.presentation.controller.estadocuenta.modeloconsulta.ConsultaAbstract;

/**
 * Implementa el modelo de consulta para la serie de una emisión.
 * @author Emigdio Hernández
 *
 */
public class ConsultaSerie extends ConsultaAbstract<SerieDTO> {
	/**
	 * Servicio de negocio para realizar consulta a la serie de las emisiones
	 */
	private ConsultaEmisionService consultaSerieService = null;
	/**
	 * Criterio de emisora para filtrar las series
	 */
	private ConsultaEmisora criterioEmisora = null;
	/**
	 * Criterio de tipo de valor para realizar el filtrado de las series
	 */
	private ConsultaTipoValor criterioTipoValor = null;
	/* (non-Javadoc)
	 * @see com.indeval.estadocuenta.presentation.model.ConsultaAbstract#getPaginaDeResultados()
	 */
	@Override
	public List<SerieDTO> getPaginaDeResultados() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.indeval.estadocuenta.presentation.model.ConsultaAbstract#getResultados()
	 */
	@Override
	public List<SerieDTO> getResultados() {
		SerieDTO criterio = new SerieDTO();
		criterio.setEmisora(criterioEmisora.getOpcionSeleccionada());
		criterio.setTipoValor(criterioTipoValor.getOpcionSeleccionada());
		
		return consultaSerieService.buscarSeries(criterio);
	}

	/* (non-Javadoc)
	 * @see com.indeval.estadocuenta.presentation.model.Consulta#getOpcionSeleccionada()
	 */
	public SerieDTO getOpcionSeleccionada() {
		
		if(opcionSeleccionada == null){
			opcionSeleccionada = new SerieDTO();
			opcionSeleccionada.setSerie(StringUtils.EMPTY);
			opcionSeleccionada.setDescripcion("TODAS");
			opcionSeleccionada.setEmisora(criterioEmisora.getOpcionSeleccionada());
			opcionSeleccionada.setTipoValor(criterioTipoValor.getOpcionSeleccionada());
		}
		
		return opcionSeleccionada;
	}

	/* (non-Javadoc)
	 * @see com.indeval.estadocuenta.presentation.model.Consulta#setOpcionSeleccionada(java.lang.Object)
	 */
	public void setOpcionSeleccionada(SerieDTO opcion) {
		opcionSeleccionada = null;
		
		if( opcion != null && !opcion.getSerie().equals(StringUtils.EMPTY) ){
			opcionSeleccionada = new SerieDTO();
			opcionSeleccionada.setSerie(opcion.getSerie());
			opcionSeleccionada.setDescripcion(opcion.getSerie());
			
			
		}
		if(opcionSeleccionada == null){
			opcionSeleccionada = new SerieDTO();
			opcionSeleccionada.setSerie(StringUtils.EMPTY);
			opcionSeleccionada.setDescripcion("TODAS");
			
		}
		opcionSeleccionada.setEmisora(criterioEmisora.getOpcionSeleccionada());
		opcionSeleccionada.setTipoValor(criterioTipoValor.getOpcionSeleccionada());

	}

	/**
	 * Obtiene el campo consultaSerieService
	 * @return  consultaSerieService
	 */
	public ConsultaEmisionService getConsultaSerieService() {
		return consultaSerieService;
	}

	/**
	 * Asigna el valor del campo consultaSerieService
	 * @param consultaSerieService el valor de consultaSerieService a asignar
	 */
	public void setConsultaSerieService(ConsultaEmisionService consultaSerieService) {
		this.consultaSerieService = consultaSerieService;
	}

	/**
	 * Obtiene el campo criterioEmisora
	 * @return  criterioEmisora
	 */
	public ConsultaEmisora getCriterioEmisora() {
		return criterioEmisora;
	}

	/**
	 * Asigna el valor del campo criterioEmisora
	 * @param criterioEmisora el valor de criterioEmisora a asignar
	 */
	public void setCriterioEmisora(ConsultaEmisora criterioEmisora) {
		this.criterioEmisora = criterioEmisora;
	}

	/**
	 * Obtiene el campo criterioTipoValor
	 * @return  criterioTipoValor
	 */
	public ConsultaTipoValor getCriterioTipoValor() {
		return criterioTipoValor;
	}

	/**
	 * Asigna el valor del campo criterioTipoValor
	 * @param criterioTipoValor el valor de criterioTipoValor a asignar
	 */
	public void setCriterioTipoValor(ConsultaTipoValor criterioTipoValor) {
		this.criterioTipoValor = criterioTipoValor;
	}

}
