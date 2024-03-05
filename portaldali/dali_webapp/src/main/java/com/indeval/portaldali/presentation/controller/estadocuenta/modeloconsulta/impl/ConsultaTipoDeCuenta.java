/**
 * 2H Software
 * Sistema de Consulta de Estado de Cuenta - Indeval
 */
package com.indeval.portaldali.presentation.controller.estadocuenta.modeloconsulta.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.indeval.portaldali.middleware.dto.TipoCuentaDTO;
import com.indeval.portaldali.presentation.controller.estadocuenta.modeloconsulta.ConsultaAbstract;



/**
 * Representa la implementación de la funcionalidad para obtener la consulta de los valores
 * que puede tener el tipo de cuenta de una cuenta.
 * Esta clase es inicializada con 2 valores posibles para la naturaleza de la cuenta:
 * Nombrada (N)
 * Controlada (C)
 * @author Emigdio Hernández
 * @version 1.0
 */
public class ConsultaTipoDeCuenta extends ConsultaAbstract<TipoCuentaDTO> {

	/**
	 * Lista con los posibles valores para un tipo de cuenta (naturaleza poroceso liquidación)
	 */
	private Map<String,TipoCuentaDTO>listaResultados=null;
	
	
	
	/* (non-Javadoc)
	 * @see com.indeval.secu.presentation.model.ConsultaAbstract#getPaginaDeResultados()
	 */
	@Override
	public List<TipoCuentaDTO> getPaginaDeResultados() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.indeval.secu.presentation.model.ConsultaAbstract#getResultados()
	 */
	@Override
	public List<TipoCuentaDTO> getResultados() {
		List <TipoCuentaDTO>resultados = new ArrayList<TipoCuentaDTO>();
		resultados.addAll(listaResultados.values());
		return resultados;
	}
	/*
	 * (non-Javadoc)
	 * @see com.indeval.estadocuenta.presentation.model.ConsultaAbstract#setOpcionSeleccionada()
	 */
	public void setOpcionSeleccionada(TipoCuentaDTO tipoCuenta) {
		if(tipoCuenta != null){
			opcionSeleccionada = listaResultados.get(tipoCuenta.getId());
		}else{
			opcionSeleccionada = listaResultados.get(TipoCuentaDTO.CUENTA_NOMBRADA);
		}
		
	}
	/**
	 * Obtiene el campo listaResultados
	 * @return  listaResultados
	 */
	public Map<String, TipoCuentaDTO> getListaResultados() {
		return listaResultados;
	}

	/**
	 * Asigna el valor del campo listaResultados
	 * @param listaResultados el valor de listaResultados a asignar
	 */
	public void setListaResultados(Map<String, TipoCuentaDTO> listaResultados) {
		this.listaResultados = listaResultados;
	}

	public TipoCuentaDTO getOpcionSeleccionada() {
		return opcionSeleccionada;
	}

	

}
