/**
 * 2H Software
 * Sistema de Consulta de Estado de Cuenta - Indeval
 */
package com.indeval.portaldali.presentation.controller.estadocuenta.modeloconsulta.impl;

import java.util.List;

import com.indeval.portaldali.middleware.dto.TipoTenenciaDTO;
import com.indeval.portaldali.middleware.services.common.ConsultaTipoTenenciaService;
import com.indeval.portaldali.presentation.controller.estadocuenta.modeloconsulta.ConsultaAbstract;

/**
 * Representa la implementación de la funcionalidad para obtener la consulta de los tipos de tenencia
 * que una cuenta puede tener.
 * Esta consulta utiliza los criterios de <code>ConsultaTipoNaturalezaCuentaServiceImpl</code> y 
 * <code>ConsultaTipoDeCuentaServiceImpl</code> para encontrar los tipos de tenencia asociada, ya sea
 * para cuentas nombradas o controladas.
 * @author Emigdio Hernández
 * @version 1.0
 */
public class ConsultaTipoTenencia extends ConsultaAbstract<TipoTenenciaDTO> {
	
	/**
	 * Indica si el tipo de tenencia ser de efectivo o de valores
	 */
	private String tipoCustodia = null;
	/**
	 * Criterio de naturaleza utilizado para filtrar los tipos de tenencia
	 */
	private ConsultaTipoNaturaleza criterioNaturaleza = null;
	/**
	 * Criterio de tipo de cuenta para filtrar los tipos de tenencia
	 */
	private ConsultaTipoDeCuenta criterioTipoCuenta = null;
	
	private ConsultaTipoTenenciaService tipoTenenciaService = null;
	
	
	/* (non-Javadoc)
	 * @see com.indeval.secu.presentation.model.ConsultaAbstract#getPaginaDeResultados()
	 */
	@Override
	public List<TipoTenenciaDTO> getPaginaDeResultados() {
		
		return null;
	}

	/* (non-Javadoc)
	 * @see com.indeval.secu.presentation.model.ConsultaAbstract#getResultados()
	 */
	@Override
	public List<TipoTenenciaDTO> getResultados() {
		TipoTenenciaDTO criterio = new TipoTenenciaDTO();
		criterio.setTipoCustodia(getTipoCustodia());
		criterio.setTipoNaturaleza(criterioNaturaleza.getOpcionSeleccionada());
		criterio.setTipoCuenta(criterioTipoCuenta.getOpcionSeleccionada());
		return tipoTenenciaService.consultarTipoCuentaPorTipoCuentaYTipoNaturaleza(criterio);
	}

	/**
	 * Obtiene el campo tipoCustodia
	 * @return  tipoCustodia
	 */
	public String getTipoCustodia() {
		return tipoCustodia;
	}

	/**
	 * Asigna el valor del campo tipoCustodia
	 * @param tipoCustodia el valor de tipoCustodia a asignar
	 */
	public void setTipoCustodia(String tipoCustodia) {
		this.tipoCustodia = tipoCustodia;
	}

	/**
	 * Obtiene el campo criterioNaturaleza
	 * @return  criterioNaturaleza
	 */
	public ConsultaTipoNaturaleza getCriterioNaturaleza() {
		return criterioNaturaleza;
	}

	/**
	 * Asigna el valor del campo criterioNaturaleza
	 * @param criterioNaturaleza el valor de criterioNaturaleza a asignar
	 */
	public void setCriterioNaturaleza(
			ConsultaTipoNaturaleza criterioNaturaleza) {
		this.criterioNaturaleza = criterioNaturaleza;
	}

	/**
	 * Obtiene el campo criterioTipoCuenta
	 * @return  criterioTipoCuenta
	 */
	public ConsultaTipoDeCuenta getCriterioTipoCuenta() {
		return criterioTipoCuenta;
	}

	/**
	 * Asigna el valor del campo criterioTipoCuenta
	 * @param criterioTipoCuenta el valor de criterioTipoCuenta a asignar
	 */
	public void setCriterioTipoCuenta(
			ConsultaTipoDeCuenta criterioTipoCuenta) {
		this.criterioTipoCuenta = criterioTipoCuenta;
	}

	public void setOpcionSeleccionada(TipoTenenciaDTO opcion) {
		opcionSeleccionada = null;
		if(opcion != null){
			opcionSeleccionada = tipoTenenciaService.buscarTipoTenenciaPorId(opcion.getIdTipoCuenta());
		}
		
		if(opcionSeleccionada==null){
			opcionSeleccionada = new TipoTenenciaDTO();
			opcionSeleccionada.setTipoCustodia(getTipoCustodia());
			opcionSeleccionada.setTipoNaturaleza(criterioNaturaleza.getOpcionSeleccionada());
			opcionSeleccionada.setTipoCuenta(criterioTipoCuenta.getOpcionSeleccionada());
			opcionSeleccionada.setIdTipoCuenta(-1);
			opcionSeleccionada.setDescripcion("TODOS");
		}
		
	}

	/**
	 * Obtiene el campo tipoTenenciaService
	 * @return  tipoTenenciaService
	 */
	public ConsultaTipoTenenciaService getTipoTenenciaService() {
		return tipoTenenciaService;
	}

	/**
	 * Asigna el valor del campo tipoTenenciaService
	 * @param tipoTenenciaService el valor de tipoTenenciaService a asignar
	 */
	public void setTipoTenenciaService(
			ConsultaTipoTenenciaService tipoTenenciaService) {
		this.tipoTenenciaService = tipoTenenciaService;
	}

	public TipoTenenciaDTO getOpcionSeleccionada() {
		if(opcionSeleccionada == null){
			opcionSeleccionada = new TipoTenenciaDTO();
			opcionSeleccionada.setTipoCustodia(getTipoCustodia());
			opcionSeleccionada.setTipoNaturaleza(criterioNaturaleza.getOpcionSeleccionada());
			opcionSeleccionada.setTipoCuenta(criterioTipoCuenta.getOpcionSeleccionada());
			opcionSeleccionada.setDescripcion("TODOS");
			opcionSeleccionada.setIdTipoCuenta(-1);
			
		}
		
		return opcionSeleccionada;
	}
	

	

	
}
