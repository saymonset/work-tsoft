/*
 * Copyright (c) 2019 CMMV Tecnologia. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.common;


import com.indeval.portaldali.middleware.dto.PropiedadesDaliDTO;


/**
 * Servicios para acceso a datos de C_PROPIEDADES_DALI
 * 
 * @author amoralesm
 *
 */
public interface PropiedadesDaliService {

	/**
	 * Recupera un parametro de c_propiedades_dali.
	 * 
	 * @param criterios
	 * @return
	 */
	PropiedadesDaliDTO obtenerParametroPorAmbienteYNombre(PropiedadesDaliDTO criterios);

	/**
	 * Recupera el parametro de limite maximo de registros para file transfer
	 * 
	 * @param nombreParametro
	 * @return
	 */
	int obtenerLimMaxRegFileTransfer(String nombreParametro);
	int obtenerLimMaxAutorizaLibera();
	String obtenerInstitucionesOcultarFechaHoraCierreOper();
	
}