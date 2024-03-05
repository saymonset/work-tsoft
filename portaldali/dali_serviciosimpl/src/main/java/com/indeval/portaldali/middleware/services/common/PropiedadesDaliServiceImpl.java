/*
 * Copyright (c) 2019 CMMV Tecnologia. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.common;

import org.apache.commons.lang.StringUtils;

import com.indeval.portaldali.middleware.dto.PropiedadesDaliDTO;
import com.indeval.portaldali.persistence.dao.common.PropiedadesDaliDAO;

/**
 * Implementacion de servicios para acceso a datos de C_PROPIEDADES_DALI
 * 
 * @author amoralesm
 *
 */
public class PropiedadesDaliServiceImpl implements PropiedadesDaliService {

	private PropiedadesDaliDAO propiedadesDaliDAO;
	
	private String ambiente;
	
	private static final String PARAM_OCULTA_FECHA_CONCERTACION = "portalDali.ocultaFechaConcertacion";

	@Override
	public PropiedadesDaliDTO obtenerParametroPorAmbienteYNombre(PropiedadesDaliDTO criterios) {
		PropiedadesDaliDTO resultado = propiedadesDaliDAO.obtenerParametroPorAmbienteYNombre(criterios);
		return resultado;
	}

	@Override
	public int obtenerLimMaxRegFileTransfer(String nombreParametro) {
		int maximoRegistrosFT = 0;
		
		PropiedadesDaliDTO criterios = new PropiedadesDaliDTO();
		criterios.setEnvironment(ambiente);
		criterios.setKey(nombreParametro);
		PropiedadesDaliDTO objPropiedadDali = this.obtenerParametroPorAmbienteYNombre(criterios);
		if (objPropiedadDali.getValue() != null && Integer.valueOf(objPropiedadDali.getValue().trim()).intValue() >= 0) {
			maximoRegistrosFT = Integer.valueOf(objPropiedadDali.getValue().trim()).intValue();
		}

		return maximoRegistrosFT;
	}
	
	public int obtenerLimMaxAutorizaLibera() {
		int maximoRegistrosFT = 100; // default
		
		PropiedadesDaliDTO criterios = new PropiedadesDaliDTO();
		criterios.setEnvironment(ambiente);
		criterios.setKey("portalDali.maximo_registros_autoriza_libera");
		PropiedadesDaliDTO objPropiedadDali = this.obtenerParametroPorAmbienteYNombre(criterios);
		if (objPropiedadDali != null && 
				objPropiedadDali.getValue() != null && StringUtils.isNotBlank(objPropiedadDali.getValue()) && StringUtils.isNumeric(objPropiedadDali.getValue())) {
			maximoRegistrosFT = Integer.parseInt(objPropiedadDali.getValue());
		}

		return maximoRegistrosFT;
	}
	
	@Override
    public String obtenerInstitucionesOcultarFechaHoraCierreOper() {
		PropiedadesDaliDTO criterios = new PropiedadesDaliDTO();
		criterios.setEnvironment(ambiente);
		criterios.setKey(PARAM_OCULTA_FECHA_CONCERTACION);
		PropiedadesDaliDTO objPropiedadDali = this.obtenerParametroPorAmbienteYNombre(criterios);
		
		if(objPropiedadDali != null &&  objPropiedadDali.getValue() != null) {
			return "'" + objPropiedadDali.getValue().replaceAll(",", "','") + "'";
		}
		
		return "''";
    }
	
	/**
	 * @param propiedadesDaliDAO the propiedadesDaliDAO to set
	 */
	public void setPropiedadesDaliDAO(PropiedadesDaliDAO propiedadesDaliDAO) {
		this.propiedadesDaliDAO = propiedadesDaliDAO;
	}

	/**
	 * @param ambiente the ambiente to set
	 */
	public void setAmbiente(String ambiente) {
		this.ambiente = ambiente;
	}

}
