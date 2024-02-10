/*
 * Copyright (c) 2015 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.persistence.unittest.BaseDaoTestCase;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.Persona;


/**
 * 
 * @author Abraham Morales
 * 
 */
@SuppressWarnings( { "unchecked" })
public class ITestEventoCorporativo extends BaseDaoTestCase {
	
	/**
	 * Log de la clase
	 */
	private static final Logger log = LoggerFactory.getLogger(ITestEventoCorporativo.class);

	/*
	 * 
	 */
	private ListaDistribucionService listaDistribucionService;

	
	/**
	 * 
	 */
	private PersonaService personaService;

	
	/**
	  * 
	  */
	protected void onSetUp() throws Exception {
		super.onSetUp();
		
		listaDistribucionService = (ListaDistribucionService) getBean("listaDistribucionService");
		
		personaService = (PersonaService) getBean("personaService");
		
		
	}

	/**
	 * 
	 */
	public void ttestListaDistribucion(){
		log.debug("============================ entrando a testListaDistribucion() ===================================");

		/*
		log.debug("==> Obtener una lista existente ...");
		Long idListaDistribucion = 33l;
		ListaDistribucion listaDistribucion = listaDistribucionService.obtenerListaDistribucionPorId(idListaDistribucion);
		assertNotNull(listaDistribucion);
		log.debug("Lista: " + listaDistribucion.getIdLista() + "," + listaDistribucion.getNombre() + "," + listaDistribucion.getInactivo());
		for(Grupo obj : listaDistribucion.getGrupos()){
			log.debug("contiene: " + obj.getIdGrupo() + "," + obj.getNombre() + "," + obj.getInactivo());	
		}
		*/

		/*
		log.debug("==> Obtener listas activas ...");
		List<ListaDistribucion> listaActivas = listaDistribucionService.obtenerListasDistribucionActivas();
		assertTrue(listaActivas.size()>0);
		log.debug(listaActivas.size());
		for(ListaDistribucion obj : listaActivas){
			log.debug("[" + obj.getIdLista() + "," + obj.getNombre() + "," + obj.getInactivo() + "]");	
		}
		*/

		/*
		log.debug("==> Registrar una lista ...");
		
		List<Long> gruposXLista = new ArrayList<Long>();
		gruposXLista.add(Long.valueOf(4l));
		gruposXLista.add(Long.valueOf(5));
		gruposXLista.add(Long.valueOf(11l));
		gruposXLista.add(Long.valueOf(14l));
		gruposXLista.add(Long.valueOf(18l));
		gruposXLista.add(Long.valueOf(23l));
		gruposXLista.add(Long.valueOf(25l));
		gruposXLista.add(Long.valueOf(26l));
		
		ListaDistribucion listaNueva = new ListaDistribucion();
		listaNueva.setInactivo(0l);
		listaNueva.setNombre("Lista Junit Nueva");

		ListaDistribucion listaConfirma = listaDistribucionService.crearListaDistribucion(listaNueva, gruposXLista);

		log.debug("Lista Nueva confirmada: " + listaConfirma.getIdLista() + "," + listaConfirma.getNombre() + "," + listaConfirma.getInactivo());

		for(Grupo obj : listaConfirma.getGrupos()){
			log.debug("contiene: " + obj.getIdGrupo() + "," + obj.getNombre() + "," + obj.getInactivo());	
		}
		*/

		/*
		log.debug("==> Modificar una lista ...");
		Long idListaModificar = 30l;
		ListaDistribucion listaModificar = listaDistribucionService.obtenerListaDistribucionPorId(idListaModificar);

		listaModificar.setInactivo(1l);
		listaModificar.setNombre("Nombre Junit Modificado");
		List<Grupo> gruposXListaModificar = new ArrayList<Grupo>();
		gruposXListaModificar.add(grupoDao.obtenerGrupoPorId(11l));
		gruposXListaModificar.add(grupoDao.obtenerGrupoPorId(12l));
		gruposXListaModificar.add(grupoDao.obtenerGrupoPorId(13l));
		gruposXListaModificar.add(grupoDao.obtenerGrupoPorId(16l));
		listaModificar.setGrupos(gruposXListaModificar);
		
		listaDistribucionService.modificarListaDistribucion(listaModificar);

		ListaDistribucion listaModificada = listaDistribucionService.obtenerListaDistribucionPorId(idListaModificar);

		log.debug("Lista Modificada confirmada: " + listaModificada.getIdLista() + "," + listaModificada.getNombre() + "," + listaModificada.getInactivo());

		for(Grupo obj : listaModificada.getGrupos()){
			log.debug("contiene: " + obj.getIdGrupo() + "," + obj.getNombre() + "," + obj.getInactivo());	
		}
		*/
		
		/*
		log.debug("==> Paginación Listas");
		ListaDistribucion criterioBusqueda = new ListaDistribucion(null, null, null);
		PaginaVO pagina = new PaginaVO();
		pagina.setRegistrosXPag(10);
		
		listaDistribucionService.buscarListasDistribucion(criterioBusqueda, pagina);
		
		assertNotNull(pagina);
		log.debug("Estado pagina: " + pagina.getRegistrosXPag() + ", " + pagina.getTotalRegistros() + ", " + pagina.getOffset());
		assertTrue(pagina.getRegistros().size() > 0);
		for(ListaDistribucion obj : (List<ListaDistribucion>)pagina.getRegistros()){
			log.debug("[" + obj.getIdLista() + "," + obj.getNombre() + "," + obj.getInactivo() + "]");	
		}

		pagina.setOffset(10);
		listaDistribucionService.buscarListasDistribucion(criterioBusqueda, pagina);
		
		assertNotNull(pagina);
		log.debug("Estado pagina: " + pagina.getRegistrosXPag() + ", " + pagina.getTotalRegistros() + ", " + pagina.getOffset());
		assertTrue(pagina.getRegistros().size() > 0);
		for(ListaDistribucion obj : (List<ListaDistribucion>)pagina.getRegistros()){
			log.debug("[" + obj.getIdLista() + "," + obj.getNombre() + "," + obj.getInactivo() + "]");	
		}

		pagina.setOffset(40);
		listaDistribucionService.buscarListasDistribucion(criterioBusqueda, pagina);
		
		assertNotNull(pagina);
		log.debug("Estado pagina: " + pagina.getRegistrosXPag() + ", " + pagina.getTotalRegistros() + ", " + pagina.getOffset());
		assertTrue(pagina.getRegistros().size() > 0);
		for(ListaDistribucion obj : (List<ListaDistribucion>)pagina.getRegistros()){
			log.debug("[" + obj.getIdLista() + "," + obj.getNombre() + "," + obj.getInactivo() + "]");	
		}

		log.debug("==> Paginación Lista por NOMBRE");
		ListaDistribucion criterioBusquedaNombre = new ListaDistribucion(null, "Auto", null);
		PaginaVO paginaNombre = new PaginaVO();
		paginaNombre.setRegistrosXPag(8);
		
		listaDistribucionService.buscarListasDistribucion(criterioBusquedaNombre, paginaNombre);
		
		assertNotNull(paginaNombre);
		log.debug("Estado pagina: " + paginaNombre.getRegistrosXPag() + ", " + paginaNombre.getTotalRegistros() + ", " + paginaNombre.getOffset());
		assertTrue(paginaNombre.getRegistros().size() > 0);
		for(ListaDistribucion obj : (List<ListaDistribucion>)paginaNombre.getRegistros()){
			log.debug("[" + obj.getIdLista() + "," + obj.getNombre() + "," + obj.getInactivo() + "]");	
		}

		paginaNombre.setOffset(16);
		listaDistribucionService.buscarListasDistribucion(criterioBusquedaNombre, paginaNombre);
		
		assertNotNull(paginaNombre);
		log.debug("Estado pagina: " + paginaNombre.getRegistrosXPag() + ", " + paginaNombre.getTotalRegistros() + ", " + paginaNombre.getOffset());
		assertTrue(paginaNombre.getRegistros().size() > 0);
		for(ListaDistribucion obj : (List<ListaDistribucion>)paginaNombre.getRegistros()){
			log.debug("[" + obj.getIdLista() + "," + obj.getNombre() + "," + obj.getInactivo() + "]");	
		}
		
		log.debug("==> Paginación Lista por ESTADO ACTIVO");
		ListaDistribucion criterioBusquedaEdo = new ListaDistribucion(null, null, 0l);
		PaginaVO paginaEdo = new PaginaVO();
		paginaEdo.setRegistrosXPag(5);

		listaDistribucionService.buscarListasDistribucion(criterioBusquedaEdo, paginaEdo);

		assertNotNull(paginaEdo);
		log.debug("Estado pagina: " + paginaEdo.getRegistrosXPag() + ", " + paginaEdo.getTotalRegistros() + ", " + paginaEdo.getOffset());
		assertTrue(paginaEdo.getRegistros().size() > 0);
		for(ListaDistribucion obj : (List<ListaDistribucion>)paginaEdo.getRegistros()){
			log.debug("[" + obj.getIdLista() + "," + obj.getNombre() + "," + obj.getInactivo() + "]");	
		}

		paginaEdo.setOffset(10);
		listaDistribucionService.buscarListasDistribucion(criterioBusquedaEdo, paginaEdo);

		assertNotNull(paginaEdo);
		log.debug("Estado pagina: " + paginaEdo.getRegistrosXPag() + ", " + paginaEdo.getTotalRegistros() + ", " + paginaEdo.getOffset());
		assertTrue(paginaEdo.getRegistros().size() > 0);
		for(ListaDistribucion obj : (List<ListaDistribucion>)paginaEdo.getRegistros()){
			log.debug("[" + obj.getIdLista() + "," + obj.getNombre() + "," + obj.getInactivo() + "]");	
		}

		paginaEdo.setOffset(30);
		listaDistribucionService.buscarListasDistribucion(criterioBusquedaEdo, paginaEdo);

		assertNotNull(paginaEdo);
		log.debug("Estado pagina: " + paginaEdo.getRegistrosXPag() + ", " + paginaEdo.getTotalRegistros() + ", " + paginaEdo.getOffset());
		assertTrue(paginaEdo.getRegistros().size() > 0);
		for(ListaDistribucion obj : (List<ListaDistribucion>)paginaEdo.getRegistros()){
			log.debug("[" + obj.getIdLista() + "," + obj.getNombre() + "," + obj.getInactivo() + "]");	
		}

		log.debug("==> Paginación Lista por ESTADO INACTIVO");
		ListaDistribucion criterioBusquedaEdoI = new ListaDistribucion(null, null, 1l);
		PaginaVO paginaEdoI = new PaginaVO();
		paginaEdoI.setRegistrosXPag(4);

		listaDistribucionService.buscarListasDistribucion(criterioBusquedaEdoI, paginaEdoI);

		assertNotNull(paginaEdoI);
		log.debug("Estado pagina: " + paginaEdoI.getRegistrosXPag() + ", " + paginaEdoI.getTotalRegistros() + ", " + paginaEdoI.getOffset());
		assertTrue(paginaEdoI.getRegistros().size() > 0);
		for(ListaDistribucion obj : (List<ListaDistribucion>)paginaEdoI.getRegistros()){
			log.debug("[" + obj.getIdLista() + "," + obj.getNombre() + "," + obj.getInactivo() + "]");	
		}

		paginaEdoI.setOffset(4);
		listaDistribucionService.buscarListasDistribucion(criterioBusquedaEdoI, paginaEdoI);

		assertNotNull(paginaEdoI);
		log.debug("Estado pagina: " + paginaEdoI.getRegistrosXPag() + ", " + paginaEdoI.getTotalRegistros() + ", " + paginaEdoI.getOffset());
		assertTrue(paginaEdoI.getRegistros().size() > 0);
		for(ListaDistribucion obj : (List<ListaDistribucion>)paginaEdoI.getRegistros()){
			log.debug("[" + obj.getIdLista() + "," + obj.getNombre() + "," + obj.getInactivo() + "]");	
		}

		paginaEdoI.setOffset(7);
		listaDistribucionService.buscarListasDistribucion(criterioBusquedaEdoI, paginaEdoI);

		log.debug("Estado pagina: " + paginaEdoI.getRegistrosXPag() + ", " + paginaEdoI.getTotalRegistros() + ", " + paginaEdoI.getOffset());
		assertTrue(paginaEdoI.getRegistros().size()<1);
		for(ListaDistribucion obj : (List<ListaDistribucion>)paginaEdoI.getRegistros()){
			log.debug("[" + obj.getIdLista() + "," + obj.getNombre() + "," + obj.getInactivo() + "]");	
		}

		
		/*
		log.debug("==> Busqueda por Nombre");
		String nombre = "Nombre Junit Modificado";
		ListaDistribucion listaXNombre = listaDistribucionService.obtenerListaDistribucionPorNombre(nombre);
		assertNotNull(listaXNombre);
		log.debug("[" + listaXNombre.getIdLista() + "," + listaXNombre.getNombre() + "," + listaXNombre.getInactivo() + "]");

		nombre = "Anonimo";
		ListaDistribucion listaXNombreUnknown= listaDistribucionService.obtenerListaDistribucionPorNombre(nombre);
		assertNull(listaXNombreUnknown);
		log.debug(listaXNombreUnknown);
		*/
		
	}

	/**
	 * Pruebas unitarias para Grupos
	 */
	public void testGrupos(){
		log.debug("============================ entrando a testGrupo() ===================================");
		
		//grupoService.buscarGrupos(criterioBusqueda, paginacion);

		/*
		log.debug("==> Registrar un Grupo ...");
		List<Long> personasXGrupo = new ArrayList<Long>();
		personasXGrupo.add(5l);
		personasXGrupo.add(6l);
		personasXGrupo.add(15l);
		personasXGrupo.add(16l);
		personasXGrupo.add(35l);
		
		Grupo grupoAlta = new Grupo();
		grupoAlta.setNombre("Grupo Junit Alta");
		grupoAlta.setInactivo(0l);
		
		Grupo grupoAltaConfirma = grupoService.crearGrupo(grupoAlta, personasXGrupo);
		
		log.debug("Grupo Registrado: " + grupoAltaConfirma.getIdGrupo() + "," + grupoAltaConfirma.getNombre() + "," + grupoAltaConfirma.getInactivo());
		
		for(Persona persona : grupoAltaConfirma.getPersonas()){
			log.debug("Contiene Persona: " + persona.getIdPersona() + "," + persona.getNombre() + "," + persona.getDescripcion() + "," + persona.getCorreo() + "," + persona.getInactivo());	
		}
		*/

		/*
		log.debug("==> Modificar un grupo ...");
		
		Long idGrupoModificar = 10l;
		Grupo grupoModificar = grupoService.obtenerGrupoPorId(idGrupoModificar);

		grupoModificar.setInactivo(0l);
		grupoModificar.setNombre("Nombre Junit Modificado");
		
		List<Persona> personasXGrupoModificar = new ArrayList<Persona>();
		personasXGrupoModificar.add(personaDao.obtenerPersonaPorId(19l));
		personasXGrupoModificar.add(personaDao.obtenerPersonaPorId(21l));
		personasXGrupoModificar.add(personaDao.obtenerPersonaPorId(22l));
		personasXGrupoModificar.add(personaDao.obtenerPersonaPorId(25l));
		personasXGrupoModificar.add(personaDao.obtenerPersonaPorId(30l));
		personasXGrupoModificar.add(personaDao.obtenerPersonaPorId(33l));
		personasXGrupoModificar.add(personaDao.obtenerPersonaPorId(34l));
		personasXGrupoModificar.add(personaDao.obtenerPersonaPorId(35l));
		personasXGrupoModificar.add(personaDao.obtenerPersonaPorId(36l));
		grupoModificar.setPersonas(personasXGrupoModificar);
		
		grupoService.modificarGrupo(grupoModificar);

		Grupo grupoModificado = grupoService.obtenerGrupoPorId(idGrupoModificar);

		log.debug("Grupo Modificado confirmado: " + grupoModificado.getIdGrupo() + "," + grupoModificado.getNombre() + "," + grupoModificado.getInactivo());

		for(Persona persona : grupoModificado.getPersonas()){
			log.debug("Contiene Persona: " + persona.getIdPersona() + "," + persona.getNombre() + "," + persona.getDescripcion() + "," + persona.getCorreo() + "," + persona.getInactivo());	
		}
		*/
		
		//grupoService.obtenerGrupoPorId(idGrupo);
		//grupoService.obtenerGruposNoPertenecientesListaDistribucion(idListaDistribucion);
		//grupoService.obtenerGruposPorListaDistribucion(idListaDistribucion);
	
		
		
		
		
		/*
		log.debug("==> Paginación Grupos");
		Grupo criterioBusqueda = new Grupo(null, null, null);
		PaginaVO pagina = new PaginaVO();
		pagina.setRegistrosXPag(8);
		
		grupoService.buscarGrupos(criterioBusqueda, pagina);
	
		assertNotNull(pagina);
		log.debug("Estado pagina: " + pagina.getRegistrosXPag() + ", " + pagina.getTotalRegistros() + ", " + pagina.getOffset());
		assertTrue(pagina.getRegistros().size() > 0);
		for(Grupo obj : (List<Grupo>)pagina.getRegistros()){
			log.debug("[" + obj.getIdGrupo() + "," + obj.getNombre() + "," + obj.getInactivo() + "]");	
		}

		pagina.setOffset(8);
		grupoService.buscarGrupos(criterioBusqueda, pagina);
		
		assertNotNull(pagina);
		log.debug("Estado pagina: " + pagina.getRegistrosXPag() + ", " + pagina.getTotalRegistros() + ", " + pagina.getOffset());
		assertTrue(pagina.getRegistros().size() > 0);
		for(Grupo obj : (List<Grupo>)pagina.getRegistros()){
			log.debug("[" + obj.getIdGrupo() + "," + obj.getNombre() + "," + obj.getInactivo() + "]");	
		}

		pagina.setOffset(16);
		grupoService.buscarGrupos(criterioBusqueda, pagina);

		log.debug("Estado pagina: " + pagina.getRegistrosXPag() + ", " + pagina.getTotalRegistros() + ", " + pagina.getOffset());
		assertTrue(pagina.getRegistros().size() > 0);
		for(Grupo obj : (List<Grupo>)pagina.getRegistros()){
			log.debug("[" + obj.getIdGrupo() + "," + obj.getNombre() + "," + obj.getInactivo() + "]");	
		}
		
		log.debug("==> Paginación Grupo por ID");
		
		Grupo criterioBusquedaId = new Grupo(20l, null, null);
		PaginaVO paginaId = new PaginaVO();
		paginaId.setRegistrosXPag(8);
		
		grupoService.buscarGrupos(criterioBusquedaId, paginaId);
	
		assertNotNull(paginaId);
		log.debug("Estado pagina: " + paginaId.getRegistrosXPag() + ", " + paginaId.getTotalRegistros() + ", " + paginaId.getOffset());
		assertTrue(paginaId.getRegistros().size() > 0);
		for(Grupo obj : (List<Grupo>)paginaId.getRegistros()){
			log.debug("[" + obj.getIdGrupo() + "," + obj.getNombre() + "," + obj.getInactivo() + "]");	
		}

		
		log.debug("==> Paginación Grupo por NOMBRE");
		Grupo criterioBusquedaNombre = new Grupo(null, "Grupo", null);
		PaginaVO paginaNombre = new PaginaVO();
		paginaNombre.setRegistrosXPag(5);
		
		grupoService.buscarGrupos(criterioBusquedaNombre, paginaNombre);
		
		assertNotNull(paginaNombre);
		log.debug("Estado pagina: " + paginaNombre.getRegistrosXPag() + ", " + paginaNombre.getTotalRegistros() + ", " + paginaNombre.getOffset());
		assertTrue(paginaNombre.getRegistros().size() > 0);
		for(Grupo obj : (List<Grupo>)paginaNombre.getRegistros()){
			log.debug("[" + obj.getIdGrupo() + "," + obj.getNombre() + "," + obj.getInactivo() + "]");	
		}

		paginaNombre.setOffset(10);
		grupoService.buscarGrupos(criterioBusquedaNombre, paginaNombre);
		
		assertNotNull(paginaNombre);
		log.debug("Estado pagina: " + paginaNombre.getRegistrosXPag() + ", " + paginaNombre.getTotalRegistros() + ", " + paginaNombre.getOffset());
		assertTrue(paginaNombre.getRegistros().size() > 0);
		for(Grupo obj : (List<Grupo>)paginaNombre.getRegistros()){
			log.debug("[" + obj.getIdGrupo() + "," + obj.getNombre() + "," + obj.getInactivo() + "]");	
		}

		log.debug("==> Paginación Grupo por ESTADO ACTIVO");
		Grupo criterioBusquedaEdo = new Grupo(null, null, 0l);
		PaginaVO paginaEdo = new PaginaVO();
		paginaEdo.setRegistrosXPag(5);
		
		grupoService.buscarGrupos(criterioBusquedaEdo, paginaEdo);
		
		assertNotNull(paginaEdo);
		log.debug("Estado pagina: " + paginaEdo.getRegistrosXPag() + ", " + paginaEdo.getTotalRegistros() + ", " + paginaEdo.getOffset());
		assertTrue(paginaEdo.getRegistros().size() > 0);
		for(Grupo obj : (List<Grupo>)paginaEdo.getRegistros()){
			log.debug("[" + obj.getIdGrupo() + "," + obj.getNombre() + "," + obj.getInactivo() + "]");	
		}

		paginaEdo.setOffset(10);
		grupoService.buscarGrupos(criterioBusquedaEdo, paginaEdo);
		
		assertNotNull(paginaEdo);
		log.debug("Estado pagina: " + paginaEdo.getRegistrosXPag() + ", " + paginaEdo.getTotalRegistros() + ", " + paginaEdo.getOffset());
		assertTrue(paginaEdo.getRegistros().size() > 0);
		for(Grupo obj : (List<Grupo>)paginaEdo.getRegistros()){
			log.debug("[" + obj.getIdGrupo() + "," + obj.getNombre() + "," + obj.getInactivo() + "]");	
		}
		
		log.debug("==> Paginación Grupo por ESTADO INACTIVO");
		Grupo criterioBusquedaEdoI = new Grupo(null, null, 1l);
		PaginaVO paginaEdoI = new PaginaVO();
		paginaEdoI.setRegistrosXPag(6);
		
		grupoService.buscarGrupos(criterioBusquedaEdoI, paginaEdoI);
		
		assertNotNull(paginaEdoI);
		log.debug("Estado pagina: " + paginaEdoI.getRegistrosXPag() + ", " + paginaEdoI.getTotalRegistros() + ", " + paginaEdoI.getOffset());
		assertTrue(paginaEdoI.getRegistros().size() > 0);
		for(Grupo obj : (List<Grupo>)paginaEdoI.getRegistros()){
			log.debug("[" + obj.getIdGrupo() + "," + obj.getNombre() + "," + obj.getInactivo() + "]");	
		}

		paginaEdoI.setOffset(6);
		grupoService.buscarGrupos(criterioBusquedaEdoI, paginaEdoI);
		
		assertNotNull(paginaEdoI);
		log.debug("Estado pagina: " + paginaEdoI.getRegistrosXPag() + ", " + paginaEdoI.getTotalRegistros() + ", " + paginaEdoI.getOffset());
		assertTrue(paginaEdoI.getRegistros().size() > 0);
		for(Grupo obj : (List<Grupo>)paginaEdoI.getRegistros()){
			log.debug("[" + obj.getIdGrupo() + "," + obj.getNombre() + "," + obj.getInactivo() + "]");	
		}

		paginaEdoI.setOffset(12);
		grupoService.buscarGrupos(criterioBusquedaEdoI, paginaEdoI);
		
		assertNotNull(paginaEdoI);
		log.debug("Estado pagina: " + paginaEdoI.getRegistrosXPag() + ", " + paginaEdoI.getTotalRegistros() + ", " + paginaEdoI.getOffset());
		assertTrue(paginaEdoI.getRegistros().size() < 1);
		for(Grupo obj : (List<Grupo>)paginaEdoI.getRegistros()){
			log.debug("[" + obj.getIdGrupo() + "," + obj.getNombre() + "," + obj.getInactivo() + "]");	
		}
		*/
		
		/*
		log.debug("==> Busqueda por Nombre");
		String nombre = "Grupo G";
		Grupo grupoXNombre = grupoService.obtenerGrupoPorNombre(nombre);
		assertNotNull(grupoXNombre);
		log.debug("[" + grupoXNombre.getIdGrupo() + "," + grupoXNombre.getNombre() + "," + grupoXNombre.getInactivo() + "]");

		nombre = "Anonimo";
		Grupo grupoXNombreUnknown= grupoService.obtenerGrupoPorNombre(nombre);
		assertNull(grupoXNombreUnknown);
		log.debug(grupoXNombreUnknown);
		*/
		
	}
	
	/**
	 * Pruebas unitarias para Personas
	 */
	public void testPersonas(){
		log.debug("============================ entrando a testPersonas() ===================================");
		
		/*
		log.debug("==> Registrar una Persona ...");
		Persona personaAlta = new Persona();
		personaAlta.setCorreo("correo_nuevo@host.com");
		personaAlta.setDescripcion("Persona Junit D");
		personaAlta.setInactivo(0l);
		personaAlta.setNombre("Persona Junit N");
		Persona personaNueva = personaService.crearPersona(personaAlta);
		assertNotNull(personaNueva);
		assertTrue(personaNueva.getIdPersona().compareTo(0l)>0);
		log.debug("Persona Nueva: " + personaNueva.getIdPersona() + "," + personaNueva.getNombre() + "," + personaNueva.getDescripcion() + "," + personaNueva.getCorreo() + "," + personaNueva.getInactivo());
		*/
		
		/*
		log.debug("==> Modificar una Persona ...");
		Long idPersona = 3l;
		Persona persona = personaDao.obtenerPersonaPorId(idPersona);
		persona.setCorreo("correo_modificado@host.com");
		persona.setDescripcion("Persona Junit Modificada D");
		persona.setInactivo(1l);
		persona.setNombre("Persona Junit Modificada N");
		Persona personaModificada = personaService.modificarPersona(persona);
		assertNotNull(personaModificada);
		log.debug("Persona Modificada: " + personaModificada.getIdPersona() + "," + personaModificada.getNombre() + "," + personaModificada.getDescripcion() + "," + personaModificada.getCorreo() + "," + personaModificada.getInactivo());
		*/
		
		/*
		log.debug("==> Personas por Grupo ...");
		
		Long[] gruposBuscar = {3l, 10l, 29l, 30l, 31l, 4l, 8l};
		for(long idGrupo : gruposBuscar){
			log.debug("==> Grupo [" + idGrupo + "]");
			List<Persona> personasGrupo = personaService.obtenerPersonasPorGrupo(idGrupo);
			for(Persona persona : personasGrupo){
				log.debug("P: " + persona.getIdPersona() + "," + persona.getNombre() + "," + persona.getDescripcion() + "," + persona.getCorreo() + "," + persona.getInactivo());	
			}
		}
		*/

		/*
		log.debug("==> Personas NO asignadas a un Grupo");
		Long[] grupos = {30l, 10l};
		for(long idGrupo : grupos){
			log.debug("==> Grupo [" + idGrupo + "]");
			List<Persona> personasNoGrupo = personaService.obtenerPersonasNoPertenecientesGrupo(idGrupo);
			for(Persona persona : personasNoGrupo){
				log.debug("P: " + persona.getIdPersona() + "," + persona.getNombre() + "," + persona.getDescripcion() + "," + persona.getCorreo() + "," + persona.getInactivo());	
			}
		}
		*/
	
		/*
		log.debug("==> Paginación Personas");
		
		log.debug("-- FILTRO: Todos los registros ---");
		
		Persona criterioBusqueda = new Persona(null, null, null, null, null);
		PaginaVO pagina = new PaginaVO();
		pagina.setRegistrosXPag(10);
		
		personaService.buscarPersonas(criterioBusqueda, pagina);
	
		assertNotNull(pagina);
		log.debug("Estado pagina: " + pagina.getRegistrosXPag() + ", " + pagina.getTotalRegistros() + ", " + pagina.getOffset() + ", " + pagina.getRegistros().size());	
		for(Persona persona : (List<Persona>) pagina.getRegistros()){
			log.debug("P: " + persona.getIdPersona() + "," + persona.getNombre() + "," + persona.getDescripcion() + "," + persona.getCorreo() + "," + persona.getInactivo());	
		}

		pagina.setOffset(20);
		personaService.buscarPersonas(criterioBusqueda, pagina);
		log.debug("Estado pagina: " + pagina.getRegistrosXPag() + ", " + pagina.getTotalRegistros() + ", " + pagina.getOffset());		
		for(Persona persona : (List<Persona>) pagina.getRegistros()){
			log.debug("P: " + persona.getIdPersona() + "," + persona.getNombre() + "," + persona.getDescripcion() + "," + persona.getCorreo() + "," + persona.getInactivo());	
		}
		
		log.debug("-- FILTRO: ID ---");
		Persona criterioBusqueda1 = new Persona(30l, null, null, null, null);
		PaginaVO pagina1 = new PaginaVO();
		pagina1.setRegistrosXPag(5);
		
		personaService.buscarPersonas(criterioBusqueda1, pagina1);
	
		assertNotNull(pagina1);
		log.debug("Estado pagina: " + pagina1.getRegistrosXPag() + ", " + pagina1.getTotalRegistros() + ", " + pagina1.getOffset());		
		for(Persona persona : (List<Persona>) pagina1.getRegistros()){
			log.debug("P: " + persona.getIdPersona() + "," + persona.getNombre() + "," + persona.getDescripcion() + "," + persona.getCorreo() + "," + persona.getInactivo());	
		}

		log.debug("-- FILTRO: Nombre ---");
		Persona criterioBusqueda2 = new Persona(null, "Junit", null, null, null);
		PaginaVO pagina2 = new PaginaVO();
		pagina2.setRegistrosXPag(8);
		
		personaService.buscarPersonas(criterioBusqueda2, pagina2);
	
		assertNotNull(pagina2);
		log.debug("Estado pagina: " + pagina2.getRegistrosXPag() + ", " + pagina2.getTotalRegistros() + ", " + pagina2.getOffset());		
		for(Persona persona : (List<Persona>) pagina2.getRegistros()){
			log.debug("P: " + persona.getIdPersona() + "," + persona.getNombre() + "," + persona.getDescripcion() + "," + persona.getCorreo() + "," + persona.getInactivo());	
		}

		log.debug("-- FILTRO: Correo ---");
		Persona criterioBusqueda3 = new Persona(null, null, "gmail.com", null, null);
		PaginaVO pagina3 = new PaginaVO();
		pagina3.setRegistrosXPag(9);
		
		personaService.buscarPersonas(criterioBusqueda3, pagina3);
	
		assertNotNull(pagina3);
		log.debug("Estado pagina: " + pagina3.getRegistrosXPag() + ", " + pagina3.getTotalRegistros() + ", " + pagina3.getOffset());		
		for(Persona persona : (List<Persona>) pagina3.getRegistros()){
			log.debug("P: " + persona.getIdPersona() + "," + persona.getNombre() + "," + persona.getDescripcion() + "," + persona.getCorreo() + "," + persona.getInactivo());	
		}

		pagina3.setOffset(36);
		personaService.buscarPersonas(criterioBusqueda3, pagina3);
		
		assertNotNull(pagina3);
		log.debug("Estado pagina: " + pagina3.getRegistrosXPag() + ", " + pagina3.getTotalRegistros() + ", " + pagina3.getOffset());		
		for(Persona persona : (List<Persona>) pagina3.getRegistros()){
			log.debug("P: " + persona.getIdPersona() + "," + persona.getNombre() + "," + persona.getDescripcion() + "," + persona.getCorreo() + "," + persona.getInactivo());	
		}
		
		log.debug("-- FILTRO: Estado Inactivo ---");
		Persona criterioBusqueda4 = new Persona(null, null, null, null, 1l);
		PaginaVO pagina4 = new PaginaVO();
		pagina4.setRegistrosXPag(10);
		
		personaService.buscarPersonas(criterioBusqueda4, pagina4);
	
		assertNotNull(pagina4);
		log.debug("Estado pagina: " + pagina4.getRegistrosXPag() + ", " + pagina4.getTotalRegistros() + ", " + pagina4.getOffset());		

		
		log.debug("-- FILTRO: Nombre + Estado Activo ---");
		
		Persona criterioBusqueda5 = new Persona(null, "Nombre A", null, null, 0l);
		PaginaVO pagina5 = new PaginaVO();
		pagina5.setRegistrosXPag(15);
		
		personaService.buscarPersonas(criterioBusqueda5, pagina5);
	
		assertNotNull(pagina5);
		log.debug("Estado pagina: " + pagina5.getRegistrosXPag() + ", " + pagina5.getTotalRegistros() + ", " + pagina5.getOffset());		
		for(Persona persona : (List<Persona>) pagina5.getRegistros()){
			log.debug("P: " + persona.getIdPersona() + "," + persona.getNombre() + "," + persona.getDescripcion() + "," + persona.getCorreo() + "," + persona.getInactivo());	
		}

		pagina5.setOffset(45);
		personaService.buscarPersonas(criterioBusqueda5, pagina5);
		
		assertNotNull(pagina5);
		log.debug("Estado pagina: " + pagina5.getRegistrosXPag() + ", " + pagina5.getTotalRegistros() + ", " + pagina5.getOffset());		
		for(Persona persona : (List<Persona>) pagina5.getRegistros()){
			log.debug("P: " + persona.getIdPersona() + "," + persona.getNombre() + "," + persona.getDescripcion() + "," + persona.getCorreo() + "," + persona.getInactivo());	
		}

		pagina5.setOffset(60);
		personaService.buscarPersonas(criterioBusqueda5, pagina5);
		
		assertNotNull(pagina5);
		log.debug("Estado pagina: " + pagina5.getRegistrosXPag() + ", " + pagina5.getTotalRegistros() + ", " + pagina5.getOffset());		
		for(Persona persona : (List<Persona>) pagina5.getRegistros()){
			log.debug("P: " + persona.getIdPersona() + "," + persona.getNombre() + "," + persona.getDescripcion() + "," + persona.getCorreo() + "," + persona.getInactivo());	
		}
		*/
		
		/*
		log.debug("==> Busqueda por Correo");
		String correo = "correoe@gmail.com";
		Persona personaXNombre = personaService.obtenerPersonaPorCorreo(correo);
		assertNotNull(personaXNombre);
		log.debug("[" + personaXNombre.getIdPersona() + "," + personaXNombre.getNombre() + "," + personaXNombre.getDescripcion() + "," + 
				personaXNombre.getCorreo() + "," + personaXNombre.getInactivo() + "]");

		correo = "correo@sitio.net.es";
		Persona personaXNombreUnknown = personaService.obtenerPersonaPorCorreo(correo);
		assertNull(personaXNombreUnknown);
		log.debug(personaXNombreUnknown);
		*/
		
		log.debug("==> Personas Activas ...");
		List<Persona> personasActivas = personaService.obtenerPersonasActivas();
		for(Persona persona : personasActivas){
			log.debug("P: " + persona.getIdPersona() + "," + persona.getNombre() + "," + persona.getDescripcion() + "," + persona.getCorreo() + "," + persona.getInactivo());	
		}

	}

}
