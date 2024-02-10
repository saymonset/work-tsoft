/**
 * Bursatec - Portal Internacional
 * Copyrigth (c) 2015 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import java.util.List;

import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.ListaDistribucion;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.Persona;

/**
 * Interfaz de negocio que define los métodos para la administración de personas.
 * 
 * @author Pablo Balderas
 *
 */
public interface PersonaService {

	/**
	 * Crea una persona en el sistema.
	 * @param persona Persona a crear.
	 * @return Persona creada.
	 */
	Persona crearPersona(Persona persona);
	
	/**
	 * Modifica una persona en el sistema.
	 * @param persona Persona a modificar.
	 * @return Persona modificada.
	 */
	Persona modificarPersona(Persona persona);
	
	/**
	 * Obtiene una persona por su id.
	 * @param idPersona Id de la persona a obtener.
	 * @return Persona obtenida.
	 */
	Persona obtenerPersonaPorId(Long idPersona);
	
	/**
	 * Realiza la busqueda paginada de personas.
	 * @param criterioBusqueda Criterios para realizar la busqueda.
	 * @param paginacion Objeto para realizar la paginación
	 * @return PaginaVO con los resultados de la busqueda y el estado de la paginación.
	 */
	PaginaVO buscarPersonas(Persona criterioBusqueda, PaginaVO paginaVO);
	
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
	/**
	 * Obtiene las listas a las que no pertenece la Persona
	 * @param idPersona
	 * @return
	 */
	List<ListaDistribucion> obtenerListasNoAsociadas(Long idPersona);
	/**
	 * Obtiene las listas a las que pertenece la Persona
	 * @param idPersona
	 * @return
	 */
	List<ListaDistribucion> obtenerListasAsociadas(Long idPersona);

}
