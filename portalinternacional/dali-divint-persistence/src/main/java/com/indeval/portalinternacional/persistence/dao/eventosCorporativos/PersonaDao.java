/**
 * Bursatec - Portal Internacional
 * Copyrigth (c) 2015 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.persistence.dao.eventosCorporativos;

import java.util.List;

import com.bursatec.persistence.dao.BaseDao;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.ListaDistribucion;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.Persona;

/**
 * Interfaz que expone los métodos de administración de la entidad Persona.
 * 
 * @author Pablo Balderas
 *
 */
public interface PersonaDao extends BaseDao {

	/**
	 * Realiza la busqueda paginada de personas.
	 * @param criterioBusqueda Criterios para realizar la busqueda.
	 * @param paginacion Objeto para realizar la paginación
	 * @return PaginaVO con los resultados de la busqueda y el estado de la paginación.
	 */
	PaginaVO buscarPersonas(Persona criterioBusqueda, PaginaVO paginaVO);
	
	/**
	 * Obtiene una persona por su id.
	 * @param idPersona Id de la persona a obtener.
	 * @return Objeto con los datos de la persona.
	 */
	Persona obtenerPersonaPorId(Long idPersona);
	
	/**
	 * Obtiene la lista de personas pertenecientes a un grupo.
	 * @param idGrupo Id del grupo.
	 * @return Lista de personas pertenecientes al grupo.
	 */
	List<Persona> obtenerPersonasPorGrupo(Long idGrupo);
	
	/**
	 * Obtiene la lista de personas activas no asocidas al grupo.
	 * @param idGrupo Grupo por el cual se discriminan las personas.
	 * @return Lista de personas activas no asociadas al grupo.
	 */
	List<Persona> obtenerPersonasNoPertenecientesGrupo(Long idGrupo);
	
	/**
	 * Recupera los datos de una persona por medio de su correo
	 * 
	 * @param correo
	 * @return
	 */
	Persona obtenerPersonaPorCorreo(String correo);

	/**
	 * Obtiene una lista de todas las personas ACTIVAS.
	 * 
	 * @return
	 */
	List<Persona> obtenerPersonasActivas();

	void borraListaPersonaByPersona(Long idPersona);
	
	
}
