/**
 * 2H Software
 * Sistema de Consulta de Estado de Cuenta - Indeval
 */
package com.indeval.portaldali.presentation.controller.estadocuenta.modeloconsulta.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.indeval.portaldali.middleware.dto.TipoNaturalezaDTO;
import com.indeval.portaldali.presentation.controller.estadocuenta.modeloconsulta.ConsultaAbstract;




/**
 * Representa la implementación de la funcionalidad para obtener la consulta de los valores
 * que puede tener la naturaleza contable de una cuenta.
 * Esta clase es inicializada con 2 valores posibles para la naturaleza de la cuenta:
 * El Pasivo (P)
 * El Activo (A)
 * @author Emigdio Hernández
 * @version 1.0
 */
public class ConsultaTipoNaturaleza extends ConsultaAbstract<TipoNaturalezaDTO> {
	/**
	 * Lista con los posibles valores para un tipo de naturaleza contable
	 */
	private Map<String,TipoNaturalezaDTO>listaResultados=null;
	
	
	
	/* (non-Javadoc)
	 * @see com.indeval.secu.presentation.model.ConsultaAbstract#getPaginaDeResultados()
	 */
	@Override
	public List<TipoNaturalezaDTO> getPaginaDeResultados() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.estadocuenta.presentation.model.ConsultaAbstract#setOpcionSeleccionada()
	 */
	public void setOpcionSeleccionada(TipoNaturalezaDTO tipoNaturaleza) {
		if(tipoNaturaleza != null){
			opcionSeleccionada = listaResultados.get(tipoNaturaleza.getId());
		}else{
			opcionSeleccionada = listaResultados.get(TipoNaturalezaDTO.PASIVO);
		}
		
	}
	/*
	 * (non-Javadoc)
	 * @see com.indeval.estadocuenta.presentation.model.ConsultaAbstract#getResultados()
	 */
	public List<TipoNaturalezaDTO> getResultados() {
		List <TipoNaturalezaDTO>resultados = new ArrayList<TipoNaturalezaDTO>();
		resultados.addAll(listaResultados.values());
		return resultados;
	}


	/**
	 * Obtiene el campo listaResultados
	 * @return  listaResultados
	 */
	public Map<String, TipoNaturalezaDTO> getListaResultados() {
		return listaResultados;
	}


	/**
	 * Asigna el valor del campo listaResultados
	 * @param listaResultados el valor de listaResultados a asignar
	 */
	public void setListaResultados(Map<String, TipoNaturalezaDTO> listaResultados) {
		this.listaResultados = listaResultados;
	}


	public TipoNaturalezaDTO getOpcionSeleccionada() {
		return opcionSeleccionada;
	}
		

}
