/**
 * 2H Software SA de CV
 * 
 * Proyecto: Sistema de Integración de Datos y Documentos Digitalizados para la BDNRC
 * 
 * Archivo: CriterioValidacionCampoRequerido.java
 *
 * Creado el Jul 12, 2007
 */
package com.indeval.portaldali.presentation.validation.impl;

import java.util.Map;

import com.indeval.portaldali.middleware.dto.TipoValorDTO;
import com.indeval.portaldali.persistence.dao.common.impl.TipoValorDAOImpl;
import com.indeval.portaldali.presentation.validation.CriterioDeValidacionAbstract;

/**
 * Implementación de un criterio de validación para validar que el tipo de valor
 * seleccionado sea valido para mercado de dinero.
 * 
 * @author Juan Carlos Huizar Moreno
 * @version 1.0
 * 
 */
public class CriterioValidacionTipoValorEmision extends
		CriterioDeValidacionAbstract {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hh.renapo.framework.validacion.CriterioDeValidacion#validar(java.util.Map)
	 */
	public boolean validar(Map<String, Object> parametros) {
		boolean resultado = true;
		TipoValorDAOImpl tipoValorDAOImpl = null;

		if (parametros.get("tipoValorLeido") != null) {
			String tipoValorLeido = parametros.get("tipoValorLeido").toString();
			/*
			 * Si la consulta no arroja resultados, el TV no es valido para
			 * mercadoDinero
			 */
			TipoValorDTO tv = null;
			tv = tipoValorDAOImpl.buscarTipoDeValorPorClave(tipoValorLeido);
			if (tv.getClaveTipoValor() == null) {
				resultado = false;
			}
		}
		return resultado;
	}
}
