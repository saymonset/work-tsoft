/**
 * 2H Software
 * Sistema de Consulta de Estado de Cuenta - Indeval
 */
package com.indeval.portaldali.presentation.controller.estadocuenta.modeloconsulta.impl;


import java.util.ArrayList;
import java.util.List;

import com.indeval.portaldali.middleware.dto.BovedaDTO;
import com.indeval.portaldali.middleware.dto.util.EstadoPaginacionDTO;
import com.indeval.portaldali.middleware.services.common.ConsultaBovedaService;
import com.indeval.portaldali.presentation.controller.estadocuenta.modeloconsulta.ConsultaAbstract;

/**
 * Representa una clase que sirve para implementar la consulta al catálogo de
 * bóvedas, esta clase sirve para consultar las bóvedas de valor y las bóvedas
 * de efectivo.
 * 
 * @author Emigdio Hernández
 * @version 1.0
 */
public class ConsultaBoveda extends ConsultaAbstract<BovedaDTO> {
	/**
	 * Servicio de negocio para consultas las bóvedas en BD
	 */
	private ConsultaBovedaService consultaBovedaService = null;

	/**
	 * Clave para filtrar las bóvedas de valor
	 */
	public static String BOVEDA_VALOR = "V";
	/**
	 * Clave para filtrar las bóvedas de efectivo
	 */
	public static String BOVEDA_EFECTIVO = "E";
	/**
	 * Indica si la bóveda buscada es de valor o de efectivo
	 */
	private String claveTipoBoveda = null;
	/**
	 * Conjunto de ids de bóvedas válidos obtenidos despus de una consulta principal
	 */
	private List<Long> bovedasValidas = new ArrayList<Long>();
	/**
	 * Obtiene el campo claveTipoBoveda
	 * 
	 * @return claveTipoBoveda
	 */
	public String getClaveTipoBoveda() {
		return claveTipoBoveda;
	}

	/**
	 * Asigna el valor del campo claveTipoBoveda
	 * 
	 * @param claveTipoBoveda
	 *            el valor de claveTipoBoveda a asignar
	 */
	public void setClaveTipoBoveda(String claveTipoBoveda) {
		this.claveTipoBoveda = claveTipoBoveda;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.secu.presentation.model.ConsultaAbstract#getPaginaDeResultados()
	 */
	@Override
	public List<BovedaDTO> getPaginaDeResultados() {
		
		EstadoPaginacionDTO estadoPaginacionDTO = this.getEstadoPaginacion();
		
		BovedaDTO criterio = new BovedaDTO();
		criterio.setClaveTipoBoveda(getClaveTipoBoveda());
		return consultaBovedaService.buscarBovedasPorTipoCustodia(
				criterio, bovedasValidas,estadoPaginacionDTO);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.secu.presentation.model.ConsultaAbstract#getResultados()
	 */
	@Override
	public List<BovedaDTO> getResultados() {
		BovedaDTO criterio = new BovedaDTO();
		criterio.setClaveTipoBoveda(getClaveTipoBoveda());
		return consultaBovedaService.consultarBovedasPorTipoDeCustodia(
				criterio, null);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.presentation.model.Consulta#setOpcionSeleccionada(java.lang.Object)
	 */
	public void setOpcionSeleccionada(BovedaDTO opcion) {
		opcionSeleccionada = null;
		if (opcion != null) {
			opcionSeleccionada = consultaBovedaService
					.consultarBovedaPorId(opcion.getId());
		}

		if (opcionSeleccionada == null) {
			opcionSeleccionada = new BovedaDTO();
			opcionSeleccionada.setClaveTipoBoveda(this.getClaveTipoBoveda());
			opcionSeleccionada.setDescripcion("TODAS");
			opcionSeleccionada.setId(-1);
		}
	}

	/**
	 * Obtiene el campo consultaBovedaService
	 * 
	 * @return consultaBovedaService
	 */
	public ConsultaBovedaService getConsultaBovedaService() {
		return consultaBovedaService;
	}

	/**
	 * Asigna el valor del campo consultaBovedaService
	 * 
	 * @param consultaBovedaService
	 *            el valor de consultaBovedaService a asignar
	 */
	public void setConsultaBovedaService(
			ConsultaBovedaService consultaBovedaService) {
		this.consultaBovedaService = consultaBovedaService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.presentation.model.Consulta#getOpcionSeleccionada()
	 */
	public BovedaDTO getOpcionSeleccionada() {
		if (opcionSeleccionada == null) {
			opcionSeleccionada = new BovedaDTO();
			opcionSeleccionada.setClaveTipoBoveda(this.getClaveTipoBoveda());
			opcionSeleccionada.setDescripcion("TODAS");
			opcionSeleccionada.setId(-1);
		}
		return opcionSeleccionada;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.estadocuenta.presentation.model.ConsultaAbstract#obtenerProyeccionConsulta()
	 */
	@Override
	public long obtenerProyeccionConsulta() {
		
		return consultaBovedaService.
		obtenerProyeccionDeBusquedaDeBovedasPorTipoCustodia(getOpcionSeleccionada(),bovedasValidas);
		
	}
	/*
	 * (non-Javadoc)
	 * @see com.indeval.estadocuenta.presentation.model.Consulta#recibirNotificacionResultados(java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void recibirNotificacionResultados(List resultados){
		bovedasValidas.clear();
		bovedasValidas.addAll(resultados);
	}

}
