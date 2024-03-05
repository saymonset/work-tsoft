/**
 * 2H Software SA de CV
 * 
 * Sistema de Consulta de Estado de Cuenta - Indeval
 *
 * Fecha de creación: Jan 11, 2008
 */
package com.indeval.portaldali.presentation.controller.estadocuenta.modeloconsulta;

import java.util.ArrayList;
import java.util.List;

import com.bursatec.seguridad.presentation.constants.SeguridadConstants;
import com.indeval.portaldali.middleware.dto.InstitucionDTO;
import com.indeval.portaldali.middleware.dto.TipoInstitucionDTO;
import com.indeval.portaldali.middleware.services.common.ConsultaInstitucionService;
import com.indeval.portaldali.middleware.services.common.ConsultaTipoInstitucionService;
import com.indeval.portaldali.presentation.controller.common.ControllerBase;

/**
 * Backing bean base para los beans que generan reportes
 * 
 * @author Sandra Flores Arrieta
 * @version 1.0
 * 
 */
public abstract class BackingBeanBase extends ControllerBase {
	
	/** Servicio de negocio para la consulta de instituciones */
	private ConsultaInstitucionService consultaInstitucionService;

	/** Servicio de negocio para la consulta del catálogo de tipos de insitución */
	private ConsultaTipoInstitucionService consultaTipoInstitucionService;

	/**
	 * Obtiene el campo consultaInstitucionService
	 * 
	 * @return consultaInstitucionService
	 */
	public ConsultaInstitucionService getConsultaInstitucionService() {
		return consultaInstitucionService;
	}

	/**
	 * Asigna el campo consultaInstitucionService
	 * 
	 * @param consultaInstitucionService
	 *            el valor de consultaInstitucionService a asignar
	 */
	public void setConsultaInstitucionService(ConsultaInstitucionService consultaInstitucionService) {
		this.consultaInstitucionService = consultaInstitucionService;
	}

	/**
	 * Obtiene el valor del atributo consultaTipoInstitucionService
	 * 
	 * @return el valor del atributo consultaTipoInstitucionService
	 */
	public ConsultaTipoInstitucionService getConsultaTipoInstitucionService() {
		return consultaTipoInstitucionService;
	}

	/**
	 * Establece el valor del atributo consultaTipoInstitucionService
	 * 
	 * @param consultaTipoInstitucionService
	 *            el valor del atributo consultaTipoInstitucionService a
	 *            establecer.
	 */
	public void setConsultaTipoInstitucionService(ConsultaTipoInstitucionService consultaTipoInstitucionService) {
		this.consultaTipoInstitucionService = consultaTipoInstitucionService;
	}

	/**
	 * Verifica si el usuario en sesión pertenece a la institucion INDEVAL.
	 * 
	 * @return <code>true</code> si el usuario en sesión pertenece a la
	 *         institución INDEVAL. <code>false</code> en cualquier otro caso.
	 */
	public boolean isUsuarioIndeval() {
		InstitucionDTO inst = getInstitucionActual();
		boolean res = false;
		
		if (inst!=null && SeguridadConstants.ID_INSTITUCION_INDEVAL.equals(inst.getId())) {
			res = true;
		}
		return res;
	}

	/**
	 * Busca instituciones en el catálogo cuyo nombre corto comience con el
	 * prefijo proporcionado.
	 * 
	 * @param prefijo
	 *            el prefijo a consultar en la BD.
	 * @return una lista con objetos de tipo {@link InstitucionDTO} con todas
	 *         las coincidencias encontradas.
	 */
	public List<InstitucionDTO> buscarInstitucionesPorPrefijo(Object value) {
		List<InstitucionDTO> resultado = null;
		List<TipoInstitucionDTO> institucion = new ArrayList<TipoInstitucionDTO>();
		List<InstitucionDTO> listaInstituciones = null;
		String prefijoAjustado = "";

		if (value != null) {
			prefijoAjustado = String.valueOf(value).trim().replace("*", "%");
			listaInstituciones = new ArrayList<InstitucionDTO>();

			institucion = getConsultaTipoInstitucionService().consultaTipoInstitucionPorPrefijo(prefijoAjustado);
			resultado = getConsultaInstitucionService().buscarInstitucionesPorPrefijo(prefijoAjustado);

			if (!institucion.isEmpty()) {

				InstitucionDTO ins = new InstitucionDTO();
				ins.setId(new Long(100) + institucion.get(0).getId());
				ins.setClaveTipoInstitucion(institucion.get(0).getClaveTipoInstitucion());
				ins.setNombreCorto(institucion.get(0).getDescripcion());

				listaInstituciones.add(ins);
			}

			for (InstitucionDTO institucionDTO : resultado) {
				listaInstituciones.add(institucionDTO);
			}
		}

		return listaInstituciones;
	}
}
