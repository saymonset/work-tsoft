/**
 * 2H Software SA de CV
 * 
 * Sistema de Consulta de Estado de Cuenta - Indeval
 * 
 */
package com.indeval.portaldali.presentation.controller.estadocuenta.modeloconsulta.impl;


import java.util.ArrayList;
import java.util.List;

import com.indeval.portaldali.middleware.dto.DivisaDTO;
import com.indeval.portaldali.middleware.services.common.ConsultaDivisaService;
import com.indeval.portaldali.presentation.controller.estadocuenta.modeloconsulta.ConsultaAbstract;

/**
 * Implementación del mecanismo de consulta para el catálogo de divisas.
 * 
 * @author Pablo Julián Balderas Méndez
 * @version 1.0
 */
public class ConsultaDivisa extends ConsultaAbstract<DivisaDTO> {

	/**
	 * Servicio de negocio para la consulta de divisas
	 */
	private ConsultaDivisaService consultaDivisaService = null;
	/**
	 * Consulta de cuentas de efectivo utilizado para filtrar las divisas.
	 * Se deben de obtener únicamente el conjunto de divisas asociado a los resultados de las cuentas
	 */
	private ConsultaCuentaEfectivo criterioCuenta = null;
	/**
	 * Conjunto de ids de divisas válidos obtenidos despus de una consulta principal
	 */
	private List<Long> divisasValidas = new ArrayList<Long>();
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.presentation.model.ConsultaAbstract#getPaginaDeResultados()
	 */
	@Override
	public List<DivisaDTO> getPaginaDeResultados() {
		return consultaDivisaService.buscarDivisas(divisasValidas,getEstadoPaginacion());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.presentation.model.ConsultaAbstract#getResultados()
	 */
	@Override
	public List<DivisaDTO> getResultados() {
		return consultaDivisaService.consultarDivisas(null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.presentation.model.Consulta#getOpcionSeleccionada()
	 */
	public DivisaDTO getOpcionSeleccionada() {

		if (opcionSeleccionada == null) {
			opcionSeleccionada = new DivisaDTO();
			opcionSeleccionada.setId(-1);
			opcionSeleccionada.setDescripcion("TODAS");
		}
		return opcionSeleccionada;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.presentation.model.Consulta#setOpcionSeleccionada(java.lang.Object)
	 */
	public void setOpcionSeleccionada(DivisaDTO opcion) {
		opcionSeleccionada = null;
		if (opcion != null) {
			opcionSeleccionada = consultaDivisaService
					.consultarDivisaPorId(opcion.getId());
		}

		if (opcionSeleccionada == null) {
			opcionSeleccionada = new DivisaDTO();
			opcionSeleccionada.setId(-1);
			opcionSeleccionada.setDescripcion("TODAS");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.presentation.model.ConsultaAbstract#obtenerProyeccionConsulta()
	 */
	@Override
	public long obtenerProyeccionConsulta() {
		
		return consultaDivisaService.obtenerProyeccionDeDivisas(divisasValidas);
	}

	/**
	 * Obtiene el valor del atributo consultaDivisaService
	 * 
	 * @return el valor del atributo consultaDivisaService
	 */
	public ConsultaDivisaService getConsultaDivisaService() {
		return consultaDivisaService;
	}

	/**
	 * Establece el valor del atributo consultaDivisaService
	 * 
	 * @param consultaDivisaService
	 *            el valor del atributo consultaDivisaService a establecer.
	 */
	public void setConsultaDivisaService(
			ConsultaDivisaService consultaDivisaService) {
		this.consultaDivisaService = consultaDivisaService;
	}

	/**
	 * Obtiene el campo criterioCuenta
	 * @return  criterioCuenta
	 */
	public ConsultaCuentaEfectivo getCriterioCuenta() {
		return criterioCuenta;
	}

	/**
	 * Asigna el valor del campo criterioCuenta
	 * @param criterioCuenta el valor de criterioCuenta a asignar
	 */
	public void setCriterioCuenta(ConsultaCuentaEfectivo criterioCuenta) {
		this.criterioCuenta = criterioCuenta;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.estadocuenta.presentation.model.Consulta#recibirNotificacionResultados(java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void recibirNotificacionResultados(List resultados){
		divisasValidas.clear();
		divisasValidas.addAll(resultados);
		
	}
	
	
}
