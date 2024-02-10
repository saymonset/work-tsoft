/**
 * Bursatec - Portal Internacional
 * Copyrigth (c) 2015 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import java.util.List;

import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.ListaDistribucion;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.ListaPersona;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.ListaPersonaPK;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.Persona;
import com.indeval.portalinternacional.persistence.dao.eventosCorporativos.ListaDistribucionDao;
import com.indeval.portalinternacional.persistence.dao.eventosCorporativos.PersonaDao;

/**
 * Implementación de servicios para Personas
 * 
 * @author amoralesm
 *
 */
public class PersonaServiceImpl implements PersonaService {

	private PersonaDao personaDao;
	private ListaDistribucionDao listaDistribucionDao;
	
	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.PersonaService#crearPersona(com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.Persona)
	 */
	public Persona crearPersona(Persona persona) {
		Long idPersona = (Long) personaDao.save(persona);
		Persona personaNueva = obtenerPersonaPorId(idPersona);
		
		for(ListaDistribucion ld : persona.getListas()){
			ListaPersona lp = new ListaPersona();
			ListaPersonaPK id = new ListaPersonaPK(personaNueva.getIdPersona(), ld.getIdLista());			
			lp.setId(id);
			personaDao.save(lp);
		}
		personaDao.flush();
		return personaNueva;
	}

	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.PersonaService#modificarPersona(com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.Persona)
	 */
	public Persona modificarPersona(Persona persona) {
		personaDao.update(persona);
		Persona personaModificada = obtenerPersonaPorId(persona.getIdPersona());
		
		//listas de distribucion
		personaDao.borraListaPersonaByPersona(persona.getIdPersona());
		for(ListaDistribucion ld : persona.getListas()){
			ListaPersona lp = new ListaPersona();
			ListaPersonaPK id = new ListaPersonaPK(persona.getIdPersona(), ld.getIdLista());			
			lp.setId(id);
			personaDao.save(lp);
		}
		personaDao.flush();
		return personaModificada;
	}

	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.PersonaService#obtenerPersonaPorId(java.lang.Long)
	 */
	public Persona obtenerPersonaPorId(Long idPersona) {
		return personaDao.obtenerPersonaPorId(idPersona);
	}

	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.PersonaService#buscarPersonas(com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.Persona, com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO)
	 */
	public PaginaVO buscarPersonas(Persona criterioBusqueda, PaginaVO paginaVO) {
		return personaDao.buscarPersonas(criterioBusqueda, paginaVO);
	}

	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.PersonaService#obtenerPersonasPorGrupo(java.lang.Long)
	 */
	public List<Persona> obtenerPersonasPorGrupo(Long idGrupo) {
		return personaDao.obtenerPersonasPorGrupo(idGrupo);
	}

	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.PersonaService#obtenerPersonasNoPertenecientesGrupo(java.lang.Long)
	 */
	public List<Persona> obtenerPersonasNoPertenecientesGrupo(Long idGrupo) {
		return personaDao.obtenerPersonasNoPertenecientesGrupo(idGrupo);
	}

	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.PersonaService#obtenerPersonaPorCorreo(java.lang.String)
	 */
	public Persona obtenerPersonaPorCorreo(String correo) {
		return personaDao.obtenerPersonaPorCorreo(correo);
	}

	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.PersonaService#obtenerPersonasActivas()
	 */
	public List<Persona> obtenerPersonasActivas() {
		return personaDao.obtenerPersonasActivas();
	}
	
	/**
	 * @param personaDao the personaDao to set
	 */
	public void setPersonaDao(PersonaDao personaDao) {
		this.personaDao = personaDao;
	}

	public List<ListaDistribucion> obtenerListasNoAsociadas(Long idPersona) {
		
		return this.listaDistribucionDao.obtenerListasNoPertenecientes(idPersona);
	}

	public List<ListaDistribucion> obtenerListasAsociadas(Long idPersona) {
		
		return this.listaDistribucionDao.obtenerListasPertenecientes(idPersona);
	}

	/**
	 * @param listaDistribucionDao the listaDistribucionDao to set
	 */
	public void setListaDistribucionDao(ListaDistribucionDao listaDistribucionDao) {
		this.listaDistribucionDao = listaDistribucionDao;
	}

}
