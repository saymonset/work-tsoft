/**
 * 2H Software
 * 
 * Sistema de Consulta de Estado de Cuenta - Indeval
 * 
 * Fecha de creación: 7 de Diciembre del 2007
 */
package com.indeval.portaldali.presentation.controller.estadocuenta.modeloconsulta.impl;


import java.util.ArrayList;
import java.util.List;

import com.indeval.portaldali.middleware.dto.EmisionDTO;
import com.indeval.portaldali.middleware.services.common.ConsultaEmisionService;
import com.indeval.portaldali.presentation.controller.estadocuenta.modeloconsulta.ConsultaAbstract;

/**
 * Implementación del esquema de consulta para las emisiones de una posición.
 * 
 * @author Jose Antonio Huizar Moreno
 * @version 1.0
 */
public class ConsultaEmision extends ConsultaAbstract<EmisionDTO> {
	/**
	 * Lista ocn el id de las emisiones con resultados válidos obtenidos para una consulta superior a la
	 * consulta de emisión
	 */
	private List<Long> emisionesValidas = new ArrayList<Long>();
	
	/** El artefacto para la consulta de las emisoras de una emisión */
	private ConsultaEmisora criterioEmisora = null;
	/**
	 * Artefacto para la consulta de series
	 */
	private ConsultaSerie criterioSerie = null;
	/**
	 * Artefacto para la consulta de tipo valor
	 */
	private ConsultaTipoValor criterioTipoValor = null;

	private String isin = null;

	/**
	 * método de inicialización, este método configura el criterio de consulta
	 * criterioSerie para que tenga las mismas referencias de objetos de
	 * criterio de emisora y criterio de tipo valor que tiene la emisión
	 */
	public void init() {
		criterioSerie.setCriterioEmisora(this.getCriterioEmisora());
		criterioSerie.setCriterioTipoValor(this.getCriterioTipoValor());
	}

	private ConsultaEmisionService consultaEmisionService = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.presentation.model.ConsultaAbstract#getPaginaDeResultados()
	 */
	@Override
	public List<EmisionDTO> getPaginaDeResultados() {

		return consultaEmisionService.consultarEmisiones(
				getOpcionSeleccionada(), getEstadoPaginacion(),emisionesValidas);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.presentation.model.ConsultaAbstract#getResultados()
	 */
	@Override
	public List<EmisionDTO> getResultados() {
		return consultaEmisionService.consultarEmisiones(
				getOpcionSeleccionada(), null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.presentation.model.Consulta#getOpcionSeleccionada()
	 */
	public EmisionDTO getOpcionSeleccionada() {

		opcionSeleccionada = new EmisionDTO();
		opcionSeleccionada.setIsin(isin);
		opcionSeleccionada.setSerie(criterioSerie.getOpcionSeleccionada());
		opcionSeleccionada.setEmisora(criterioEmisora.getOpcionSeleccionada());
		opcionSeleccionada.setTipoValor(criterioTipoValor
				.getOpcionSeleccionada());
		if ((isin != null && isin.length() > 0)
				|| (criterioTipoValor.getOpcionSeleccionada().getId() > 0
						&& criterioTipoValor.getOpcionSeleccionada()
								.getMercado().getId() > 0 && (criterioSerie
						.getOpcionSeleccionada().getSerie() != null && !criterioSerie
						.getOpcionSeleccionada().getSerie().equals("-1")))) {
			// Este ID no es el id real de la emisión, se coloca por default un
			// 1 para indicar
			// a la paginación del criterio que se seleccion una emisión en
			// particular
			opcionSeleccionada.setId(1);
		} else {
			opcionSeleccionada.setId(-1);
		}

		return opcionSeleccionada;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.presentation.model.Consulta#setOpcionSeleccionada(java.lang.Object)
	 */
	public void setOpcionSeleccionada(EmisionDTO opcion) {

		opcionSeleccionada = null;

		if (opcionSeleccionada == null) {
			opcionSeleccionada = new EmisionDTO();
			opcionSeleccionada.setIsin(isin);
			opcionSeleccionada.setSerie(criterioSerie.getOpcionSeleccionada());
			opcionSeleccionada.setEmisora(criterioEmisora
					.getOpcionSeleccionada());
			opcionSeleccionada.setTipoValor(criterioTipoValor
					.getOpcionSeleccionada());
			opcionSeleccionada.setId(-1);
		}

	}

	/**
	 * Obtiene el campo consultaEmisora
	 * 
	 * @return consultaEmisora
	 */
	public ConsultaEmisora getCriterioEmisora() {
		return criterioEmisora;
	}

	/**
	 * Asigna el valor del campo consultaEmisora
	 * 
	 * @param consultaEmisora
	 *            el valor de consultaEmisora a asignar
	 */
	public void setCriterioEmisora(ConsultaEmisora criterioEmisora) {
		this.criterioEmisora = criterioEmisora;
	}

	/**
	 * Obtiene el campo criterioSerie
	 * 
	 * @return criterioSerie
	 */
	public ConsultaSerie getCriterioSerie() {
		return criterioSerie;
	}

	/**
	 * Asigna el valor del campo criterioSerie
	 * 
	 * @param criterioSerie
	 *            el valor de criterioSerie a asignar
	 */
	public void setCriterioSerie(ConsultaSerie criterioSerie) {
		this.criterioSerie = criterioSerie;
	}

	/**
	 * Obtiene el campo criterioTipoValor
	 * 
	 * @return criterioTipoValor
	 */
	public ConsultaTipoValor getCriterioTipoValor() {
		return criterioTipoValor;
	}

	/**
	 * Asigna el valor del campo criterioTipoValor
	 * 
	 * @param criterioTipoValor
	 *            el valor de criterioTipoValor a asignar
	 */
	public void setCriterioTipoValor(ConsultaTipoValor criterioTipoValor) {
		this.criterioTipoValor = criterioTipoValor;
	}

	/**
	 * Obtiene el campo consultaEmisionService
	 * 
	 * @return consultaEmisionService
	 */
	public ConsultaEmisionService getConsultaEmisionService() {
		return consultaEmisionService;
	}

	/**
	 * Asigna el valor del campo consultaEmisionService
	 * 
	 * @param consultaEmisionService
	 *            el valor de consultaEmisionService a asignar
	 */
	public void setConsultaEmisionService(
			ConsultaEmisionService consultaEmisionService) {
		this.consultaEmisionService = consultaEmisionService;
	}

	/**
	 * Obtiene el campo isin
	 * 
	 * @return isin
	 */
	public String getIsin() {
		return isin;
	}

	/**
	 * Asigna el valor del campo isin
	 * 
	 * @param isin
	 *            el valor de isin a asignar
	 */
	public void setIsin(String isin) {
		this.isin = isin;
	}
	/*
	 * (non-Javadoc)
	 * @see com.indeval.estadocuenta.presentation.model.ConsultaAbstract#obtenerProyeccionConsulta()
	 */
	@Override
	public long obtenerProyeccionConsulta() {

		return consultaEmisionService
				.obtenerProyeccionDeEmisiones(getOpcionSeleccionada(),emisionesValidas);
	}

	/*
	 * (non-Javadoc)
	 * @see com.indeval.estadocuenta.presentation.model.Consulta#recibirNotificacionResultados(java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void recibirNotificacionResultados(List resultados){
		emisionesValidas.clear();
		emisionesValidas.addAll(resultados);
	}
	
}
