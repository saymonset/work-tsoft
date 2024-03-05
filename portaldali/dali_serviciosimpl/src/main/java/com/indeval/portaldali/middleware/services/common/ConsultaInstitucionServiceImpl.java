/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * ConsultaInstitucionService.java
 * 06/03/2008
 */
package com.indeval.portaldali.middleware.services.common;

import java.util.List;

import com.indeval.portaldali.middleware.dto.InstitucionDTO;
import com.indeval.portaldali.middleware.services.common.ConsultaInstitucionService;
import com.indeval.portaldali.persistence.dao.common.InstitucionDaliDAO;

/**
 * Implementa la funcionalidad definida por la interfaz para las operaciones de consulta de instituciones.
 * @author Emigdio
 *
 */
public class ConsultaInstitucionServiceImpl implements
		ConsultaInstitucionService {
	
	private InstitucionDaliDAO institucionDaliDAO = null;
	/* (non-Javadoc)
	 * @see com.indeval.estadocuenta.core.application.services.ConsultaInstitucionService#buscarInstitucionPorId(long)
	 */
	public InstitucionDTO buscarInstitucionPorId(long id) {
		
		return institucionDaliDAO.buscarInstitucionPorId(id);
	}

	/* (non-Javadoc)
	 * @see com.indeval.estadocuenta.core.application.services.ConsultaInstitucionService#buscarInstituciones()
	 */
	public List<InstitucionDTO> buscarInstituciones() {
		return institucionDaliDAO.buscarInstituciones();
	}

	/**
	 * Obtiene el campo institucionDaliDAO
	 * @return  institucionDaliDAO
	 */
	public InstitucionDaliDAO getInstitucionDAO() {
		return institucionDaliDAO;
	}

	/**
	 * Asigna el valor del campo institucionDaliDAO
	 * @param institucionDaliDAO el valor de institucionDaliDAO a asignar
	 */
	public void setInstitucionDAO(InstitucionDaliDAO institucionDaliDAO) {
		this.institucionDaliDAO = institucionDaliDAO;
	}

	/*
	 * (non-Javadoc)
	 * @see com.indeval.estadocuenta.core.application.services.ConsultaInstitucionService#buscarInstitucionesPorPrefijo(java.lang.String)
	 */
	public List<InstitucionDTO> buscarInstitucionesPorPrefijo(String prefijo) {
		
		return institucionDaliDAO.buscarInstitucionesPorPrefijo(prefijo);
	}

	/*
	 * (non-Javadoc)
	 * @see com.indeval.estadocuenta.core.application.services.ConsultaInstitucionService#buscarInstitucionPorNombreCorto(java.lang.String)
	 */
	public InstitucionDTO buscarInstitucionPorNombreCorto(String nombre) {
		
		return institucionDaliDAO.buscarInstitucionPorNombreCorto(nombre);
	}

	/*
	 * (non-Javadoc)
	 * @see com.indeval.estadocuenta.core.application.services.ConsultaInstitucionService#buscarInstitucionPorClaveYFolio(java.lang.String)
	 */
	public InstitucionDTO buscarInstitucionPorClaveYFolio(String claveFolio) {
		return institucionDaliDAO.buscarInstitucionPorClaveYFolio(claveFolio);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.estadocuenta.core.application.services.ConsultaInstitucionService#buscarOrigenPorPrefijo(java.lang.String)
	 */
	public List<String> buscarOrigenPorPrefijo(String prefijo) {
		return institucionDaliDAO.buscarOrigenPorPrefijo(prefijo);
	}

}
