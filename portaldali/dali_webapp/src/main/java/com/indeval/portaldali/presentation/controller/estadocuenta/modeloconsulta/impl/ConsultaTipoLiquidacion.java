/**
 * 2H Software
 * Sistema de Consulta de Estado de Cuenta - Indeval
 */
package com.indeval.portaldali.presentation.controller.estadocuenta.modeloconsulta.impl;

import java.math.BigInteger;
import java.util.List;

import com.indeval.portaldali.middleware.dto.TipoLiquidacionDTO;
import com.indeval.portaldali.middleware.services.common.ConsultaTipoLiquidacionService;
import com.indeval.portaldali.presentation.controller.estadocuenta.modeloconsulta.ConsultaAbstract;

/**
 * Representa la implementación de la funcionalidad para obtener la consulta de los valores
 * que puede tener el tipo de liquidación de una operación.
 * @author Emigdio Hernández
 * @version 1.0
 * 
 */
public class ConsultaTipoLiquidacion extends ConsultaAbstract<TipoLiquidacionDTO> {
	/**
	 * Servicio para el acceso al catálogo de tipos de liquidación.
	 */
	private ConsultaTipoLiquidacionService consultaTipoLiquidacionService = null;
	/**
	 * Indica si los tipos de custodia válidos para la consulta de Liquidaciones
	 */
	private String tiposCustodia = null;
	
	
	/* (non-Javadoc)
	 * @see com.indeval.estadocuenta.presentation.model.ConsultaAbstract#getPaginaDeResultados()
	 */
	public List<TipoLiquidacionDTO> getPaginaDeResultados() {
		
		return null;
	}

	/* (non-Javadoc)
	 * @see com.indeval.estadocuenta.presentation.model.ConsultaAbstract#getResultados()
	 */
	public List<TipoLiquidacionDTO> getResultados() {
		return consultaTipoLiquidacionService.buscarTiposDeLiquidacion(tiposCustodia);
	}

	/* (non-Javadoc)
	 * @see com.indeval.estadocuenta.presentation.model.Consulta#getOpcionSeleccionada()
	 */
	public TipoLiquidacionDTO getOpcionSeleccionada() {
		if(opcionSeleccionada == null){
			opcionSeleccionada = new TipoLiquidacionDTO();
			opcionSeleccionada.setIdTipoLiq(BigInteger.valueOf(-1L));
			opcionSeleccionada.setDescripcion("TODOS");
			opcionSeleccionada.setNombreCorto("TODOS");
		}
		return opcionSeleccionada;
	}

	/* (non-Javadoc)
	 * @see com.indeval.estadocuenta.presentation.model.Consulta#setOpcionSeleccionada(java.lang.Object)
	 */
	public void setOpcionSeleccionada(TipoLiquidacionDTO opcion) {
		opcionSeleccionada = null;
		if(opcion != null){
			opcionSeleccionada = consultaTipoLiquidacionService.buscarTipoDeLiquidacionPorClave(opcion.getNombreCorto());
		}
		
		if(opcionSeleccionada==null){
			getOpcionSeleccionada();
		}

	}

	/**
	 * Obtiene el campo consultaTipoLiquidacionService
	 * @return  consultaTipoLiquidacionService
	 */
	public ConsultaTipoLiquidacionService getConsultaTipoLiquidacionService() {
		return consultaTipoLiquidacionService;
	}

	/**
	 * Asigna el valor del campo consultaTipoLiquidacionService
	 * @param consultaTipoLiquidacionService el valor de consultaTipoLiquidacionService a asignar
	 */
	public void setConsultaTipoLiquidacionService(
			ConsultaTipoLiquidacionService consultaTipoLiquidacionService) {
		this.consultaTipoLiquidacionService = consultaTipoLiquidacionService;
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
