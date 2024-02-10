/**
 * Bursatec - Portal Internacional
 * Copyrigth (c) 2015 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.presentation.controller.eventosCorporativos.catalogos;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.apache.commons.lang.StringUtils;

import com.indeval.portaldali.middleware.services.util.ConsultaCatalogoService;
import com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.modelo.Institucion;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.ListaDistribucionService;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.PersonaService;
import com.indeval.portalinternacional.middleware.servicios.constantes.CatalogosConstantes;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;

import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.ListaDistribucion;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.Persona;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.PersonaVista;

/**
 * Controlador para la administración del catálogo de personas.
 * 
 * @author Pablo Balderas
 *
 */
public class PersonaController extends CatalogosEventosCorporativosController {

	/** Filtro para buscar por nombre de la persona */
	private String nombrePersona;
	
	/** Filtro para buscar por correo electrónico */
	private String correoElectronico;
	
	/** Objeto para el registro o modificación de personas */
	private Persona persona;
	
	/** Lista para la consulta de personas */
	private List<PersonaVista> personas;
	
	/** Servicio para la administración de personas */
	private PersonaService personaService;
	
	private String nombreInstitucion;
	
	private String claveInstitucion;
	private AgenteVO agenteVO;
	
	private ConsultaCatalogoService consultaCatService;

	private boolean enabledInstitucion;
	private ListaDistribucionService listaDistribucionService;
	private Integer listaDistribucion;
	private List<ListaDistribucion> listasDistribucion;
	private List<SelectItem> comboListaDistribucion;
	private String descripcion;

	private PaginaVO paginaReportes;
	private static Integer OPCION_TODOS=-1;
	/**
	 * Método para inicializar la pantalla
	 * @return Nulo
	 */
	public String getInit() {
		if(!isConsultaEjecutada()) {
			limpiarConsulta(null);
		}
		this.enabledInstitucion=false;
		this.agenteVO = getAgenteFirmado(); 			 
		this.claveInstitucion=agenteVO.getId()+agenteVO.getFolio();
		//this.nombreInstitucion = getBuscaInstitucion();
		
		if(StringUtils.isNotEmpty(this.claveInstitucion)&& CatalogosConstantes.INSTITUCION_INDEVAL.equals(this.claveInstitucion)){
			this.enabledInstitucion=true;
		}
		return null;
	}
	
	/**
	 * Método para inicializar la pantalla de alta/modificación
	 * @return Nulo
	 */
	public String getInitAltaModificacion() {
		String idParametro = 
			FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().
				get(CatalogosConstantes.PARAMETRO_ID_PERSONA);
		this.enabledInstitucion=false;
		if(idParametro != null) {
			//Es modificación
			setEdicion(true);
			//Obtiene el detalle de la persona
			Long idPersona = Long.valueOf(idParametro);
			persona = personaService.obtenerPersonaPorId(idPersona);
			//Coloca el estado del registro
			setEstadoRegistro(CatalogosConstantes.VALOR_ESTATUS_ACTIVO.equals(persona.getInactivo()));
			//institucion
			this.agenteVO = getAgenteFirmado();	
			
			if(persona.getInstitucion()!= null){
				this.claveInstitucion=persona.getInstitucion().getClaveInstitucion();
			}else{
				 
				this.claveInstitucion=agenteVO.getId()+agenteVO.getFolio();
			}
			this.nombreInstitucion = getBuscaInstitucion();			
			
			
			String institucionAgente=agenteVO.getId()+agenteVO.getFolio();
			
			if(StringUtils.isNotEmpty(institucionAgente)&& CatalogosConstantes.INSTITUCION_INDEVAL.equals(institucionAgente)){
				this.enabledInstitucion=true;
			}
			
			//Listas
			//Obtiene los grupos no asociados a la lista
			List<ListaDistribucion> listasActivas = personaService.obtenerListasNoAsociadas(persona.getIdPersona());
			if(listasActivas !=null && listasActivas.size() > 0){
				setListaPrincipal(obtenerListaSelectItem(listasActivas));
			}
			//Obtiene los grupos asociados a la lista
			List<ListaDistribucion> listasAsociadas = personaService.obtenerListasAsociadas(persona.getIdPersona());
			if(listasAsociadas !=null && listasAsociadas.size() > 0){
				setListaSecundaria(obtenerListaSelectItem(listasAsociadas));
			}
		}
		else {
			//Es nuevo registro
			setEdicion(false);
			//Inicializa el objeto persona
			persona = new Persona();
			//Por omision, la persona debe nacer habilitada
			setEstadoRegistro(true);
			this.agenteVO = getAgenteFirmado(); 			 
			this.claveInstitucion=agenteVO.getId()+agenteVO.getFolio();
			this.nombreInstitucion = getBuscaInstitucion();
			
			if(StringUtils.isNotEmpty(this.claveInstitucion)&& CatalogosConstantes.INSTITUCION_INDEVAL.equals(this.claveInstitucion)){
				this.enabledInstitucion=true;
			}
			
			//Listas
			//Obtiene los grupos no asociados a la lista
			List<ListaDistribucion> listasActivas = personaService.obtenerListasNoAsociadas(persona.getIdPersona());
			if(listasActivas !=null && listasActivas.size() > 0){
				setListaPrincipal(obtenerListaSelectItem(listasActivas));
			}
		}
		setResultadoRegMod(false);
		return null;
	}

	public List<SelectItem> obtenerListaSelectItem(List lista) {
		List<ListaDistribucion> listaDistribucion = (List<ListaDistribucion>) lista;
		List<SelectItem> listaSelectItems = new ArrayList<SelectItem>();
		for (ListaDistribucion obj : listaDistribucion) {
			listaSelectItems.add(new SelectItem(obj.getIdLista().intValue(), obj.getNombre() + "-" + obj.getDescripcion()));
		}
		return listaSelectItems;
	}
	
	public List<SelectItem> obtenerListaSelectItemSinDesc(List lista) {
		List<ListaDistribucion> listaDistribucion = (List<ListaDistribucion>) lista;
		List<SelectItem> listaSelectItems = new ArrayList<SelectItem>();
		for (ListaDistribucion obj : listaDistribucion) {
			listaSelectItems.add(new SelectItem(obj.getIdLista().intValue(), obj.getNombre() ));
		}
		return listaSelectItems;
	}
	
	/**
	 * Obtiene la lista de listas de distribucion a partir de la lista de select item
	 * @param lista Lista de select items
	 * @return Lista de distribucion
	 */
	private List<ListaDistribucion> obtenerListaDistribucion(List<SelectItem> lista) {
		List<ListaDistribucion> listaDistribucion = new ArrayList<ListaDistribucion>();
		for (SelectItem selectItem : lista) {
			Long id = new Long(((Integer) selectItem.getValue()).longValue());
			listaDistribucion.add(new ListaDistribucion(id, null, null, null));
		}
		return listaDistribucion;
	}	
	/**
	 * llena la lista de distribucion para el combo
	 * @return
	 */
	public List<SelectItem> getComboListaDistribucion(){
		if(this.comboListaDistribucion == null){
			
			List<SelectItem> listaSelectItems = new ArrayList<SelectItem>();
			listaSelectItems.add(new SelectItem(new Integer(-1), "Todas" ));
			listaSelectItems.addAll(obtenerListaSelectItemSinDesc(listaDistribucionService.obtenerListasDistribucionActivas()));
			this.comboListaDistribucion =listaSelectItems;
			
		}
		return this.comboListaDistribucion;
	}
	/**
	 * Método para limpiar la consulta.
	 * @param actionEvent Evento generado por faces.
	 */
	public void limpiarConsulta(ActionEvent actionEvent) {
		nombrePersona = null;
		correoElectronico = null;
		this.descripcion=null;
		this.listaDistribucion=-1;
		setFiltroEstatus(null);
		setPaginaVO(new PaginaVO());
		personas = null;
		setConsultaEjecutada(false);		
		setMostrarCriteriosBusqueda(true);
		setMostrarResumenBusqueda(false);
		setMostrarBotonEditar(false);
		setMostrarReportes(false);
	}

	/**
	 * Muestra los criterios de busqueda y oculta el resumen.
	 * @param actionEvent Evento generado por faces.
	 */
	public void editarConsulta(ActionEvent actionEvent) {
		ajustarCriteriosBusqueda();
		//Indicamos que partes de la pantalla deben mostrarse
		setMostrarCriteriosBusqueda(true);
		setMostrarResumenBusqueda(false);
		setMostrarBotonEditar(false);
	}
	
	/**
	 * Método para realizar la búsqueda de las listas de distribución de acuerdo
	 * a los parámetros capturados por el usuario.
	 * @param actionEvent Evento generado por faces.
	 */
	public void buscar(ActionEvent actionEvent) {
		//Inicializa la paginación
		inicializarPaginacion();
		//Ejecuta la consulta
		ejecutarConsulta();
		//Indica que la consulta ha sido ejecutada
		setConsultaEjecutada(true);
		setMostrarCriteriosBusqueda(false);
		setMostrarResumenBusqueda(true);
		setMostrarBotonEditar(true);
		//Indica si debe mostrar la generación de reportes
		setMostrarReportes(personas != null && !personas.isEmpty());
		//Ajusta los criterios de busqueda
		ajustarCriteriosDeBusqueda();
	}
	
	
	/**
	 * Genera los reportes de Consulta de Emisiones.
	 * @param actionEvent Evento generado por faces.
	 */
	@SuppressWarnings("unchecked")
	public void generarReportes(ActionEvent actionEvent) {
		reiniciarEstadoPeticion();
		paginaReportes = new PaginaVO();
		paginaReportes.setOffset(0);
		paginaReportes.setRegistrosXPag(PaginaVO.TODOS);
		paginaReportes.setRegistros(null);
//		paginaVO = new PaginaVO();
//		paginaVO.setOffset(0);
//		paginaVO.setRegistrosXPag(PaginaVO.TODOS);
//		paginaVO.setRegistros(null);
		paginaReportes = personaService.buscarPersonas(crearCriterioBusqueda(), paginaReportes);
		personas = paginaReportes.getRegistros();
		//Coloca el total de resultados
		setTotalRegistrosExportacion(personas.size());
	}
	
	/**
	 * Guarda una lista de distribución en el sistema.
	 * @param actionEvent Evento generado por faces.
	 */
	public void guardar(ActionEvent actionEvent) {
		setResultadoRegMod(false);
		//Valida los datos capturados por el usuario		
		if(validarPersona()) {
			//Coloca el estado de la lista
			this.persona.setInactivo(
				isEstadoRegistro() ? CatalogosConstantes.VALOR_ESTATUS_ACTIVO : CatalogosConstantes.VALOR_ESTATUS_INACTIVO);
			//Pasa el nombre y la descripción a mayusculas
			persona.setNombre(persona.getNombre().toUpperCase());
			persona.setDescripcion(persona.getDescripcion().toUpperCase());
			
			//Pon la institucion
			persona.setInstitucion(consultaCatService.findInstitucionByClaveFolio(this.claveInstitucion));
			
			//guardando listas
			persona.setListas(obtenerListaDistribucion(this.getListaSecundaria()));
			
			//fecha Actualizacion
			persona.setFechaActualizacion(Calendar.getInstance().getTime());
			
			//Se trata de una modificación
			if(isEdicion()) {
				persona = personaService.modificarPersona(persona);
				
			}
			//Se trata de un registro
			else {
				persona = personaService.crearPersona(persona);
			}			
			
			//Indicamos si la operación fue correcta.
			setResultadoRegMod(persona != null);
		}
	}
	
	/**
	 * Método que realiza la consulta de las personas después del registro o modificación.
	 * @param actionEvent Evento generado por faces.
	 */
	public void cerrarVentanaAltaModificacion(ActionEvent actionEvent) {
		//Inicializa la paginación
		inicializarPaginacion();
		//Resea los campos de búsqueda
		nombrePersona = null;
		correoElectronico = null;
		setFiltroEstatus(-1L);
		ejecutarConsulta();
		//Indica que la consulta ha sido ejecutada
		setConsultaEjecutada(true);
		setMostrarCriteriosBusqueda(false);
		setMostrarResumenBusqueda(true);
		setMostrarBotonEditar(true);
		//Indica si debe mostrar la generación de reportes
		setMostrarReportes(personas != null && !personas.isEmpty());
		//Ajusta los criterios de busqueda
		ajustarCriteriosDeBusqueda();
		//Coloca el mensaje de exito o error
		if(isResultadoRegMod()) {
			addMessageFromProperties("msgOperacionExitosa", FacesMessage.SEVERITY_INFO);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.indeval.portalinternacional.presentation.controller.common.ControllerBase#ejecutarConsulta()
	 */
	@SuppressWarnings("unchecked")
	public String ejecutarConsulta() {
		ajustarCriteriosBusqueda();
		//Realizamos la busqueda
		try{
			paginaVO = personaService.buscarPersonas(crearCriterioBusqueda(), paginaVO);
			if(paginaVO != null && paginaVO.getRegistros() != null && !paginaVO.getRegistros().isEmpty()) {
				personas = paginaVO.getRegistros();
			}
			else {
				personas = new ArrayList<PersonaVista>();
			}
			//Coloca el total de paginas
			setTotalPaginas(paginaVO.getTotalRegistros() / paginaVO.getRegistrosXPag());
			if(paginaVO.getTotalRegistros() % paginaVO.getRegistrosXPag() > 0) {
				setTotalPaginas(getTotalPaginas() + 1);
			}
			setTotalPaginas((getTotalPaginas() <= 0) ? 1 : getTotalPaginas());
		}catch(IllegalArgumentException iae){
			addErrorMessage("El folio de la instituci\u00F3n debe ser num\u00E9rico");
		}
		return null;
	}
	
	/**
	 * Método que valida los valores de la lista de distribución a registrar o modificar.
	 * @return true si la validación es correcta; false en caso contrario.
	 */
	private boolean validarPersona() {
		boolean valido = true;
		//Valida el nombre
		if(StringUtils.isEmpty(persona.getNombre())) {
			addMessageFromProperties("msgErrorCampoObligatorio", new Object[]{"Nombre Persona"}, FacesMessage.SEVERITY_ERROR);
			valido = false;
		}
		//Valida la descripción
		if(StringUtils.isNotEmpty(persona.getDescripcion()) && persona.getDescripcion().length() > 100) {
			addMessageFromProperties("msgErrorDescripcion", FacesMessage.SEVERITY_ERROR);
			valido = false;
		}
		//Valida el correo
		if(StringUtils.isEmpty(persona.getCorreo())) {
			addMessageFromProperties("msgErrorCampoObligatorio", new Object[]{"Correo Persona"}, FacesMessage.SEVERITY_ERROR);
			valido = false;
		}
		if(StringUtils.isEmpty(this.claveInstitucion) || (StringUtils.isNotEmpty(this.claveInstitucion) && !StringUtils.isNumeric(this.claveInstitucion) )){
			addMessageFromProperties("msgErrorCampoObligatorio", new Object[]{"Instituci\u00F3n"}, FacesMessage.SEVERITY_ERROR);
			valido = false;
		}
		getBuscaInstitucion();
		if(StringUtils.isNotEmpty(this.claveInstitucion) && StringUtils.isEmpty(this.nombreInstitucion)){
			addMessageFromProperties("msgErrorCampoObligatorio", new Object[]{"Instituci\u00F3n existente"}, FacesMessage.SEVERITY_ERROR);
			valido = false;
		}
		if(StringUtils.isNotEmpty(persona.getCorreo())) {
			if(!Pattern.matches(CatalogosConstantes.EXPRESION_REGULAR_COREO, persona.getCorreo())) {
				addMessageFromProperties("msgErrorCorrero", FacesMessage.SEVERITY_ERROR);
				valido = false;
			}
		}
		//Valida que el correo no este dado de alta
		Persona personaPorCorreo = 
			personaService.obtenerPersonaPorCorreo(persona.getCorreo());
		if(isEdicion()) {
			if(personaPorCorreo != null && !persona.getIdPersona().equals(personaPorCorreo.getIdPersona())) {
				addMessageFromProperties("msgErrorCorreoExistente", FacesMessage.SEVERITY_ERROR);
				valido = false;
			}
		}
		else if(personaPorCorreo != null && personaPorCorreo.getCorreo().equals(persona.getCorreo())) {
			addMessageFromProperties("msgErrorCorreoExistente", FacesMessage.SEVERITY_ERROR);
			valido = false;
		}
		if(getListaSecundaria() == null ||(getListaSecundaria()!= null && getListaSecundaria().size()==0)) {
			addErrorMessage("La lista destino debe contener por lo menos una lista");
			valido = false;
		}
		return valido;
	}
	
	/**
	 * Crea el criterio de busqueda para personas
	 * @return Objeto con el criterio de busqueda.
	 */
	private Persona crearCriterioBusqueda() {
		ajustarCriteriosBusqueda();
		//Creamos el criterio de busqueda
		Persona criterioPersona = new Persona(); 
		//Colocamos los criterios capturados por el usuario
		criterioPersona.setNombre(StringUtils.isNotEmpty(nombrePersona) ? nombrePersona.toUpperCase() : null);
		criterioPersona.setCorreo(StringUtils.isNotEmpty(correoElectronico) ? correoElectronico : null);
		criterioPersona.setDescripcion(StringUtils.isNotEmpty(descripcion) ? descripcion.toUpperCase() : null);
		criterioPersona.setInactivo(getFiltroEstatus());
		
		if(this.claveInstitucion != null){
			Institucion inst =new Institucion();
			inst.setClaveInstitucion(this.claveInstitucion);
			criterioPersona.setInstitucion(inst);
		}
		if(this.listaDistribucion != null && !this.listaDistribucion.equals(-1)){
			criterioPersona.setListaDistribucion(this.listaDistribucion.longValue());
		}
		//Regresamos el criterio creado
		return criterioPersona;
	}

	/**
	 * Ajusta los criterios de busqueda para colocar "TODOS" si no capturo nada.
	 */
	private void ajustarCriteriosDeBusqueda() {
		//Ajusta los criterios de busqueda
		nombrePersona = StringUtils.isEmpty(nombrePersona) ? CatalogosConstantes.TODOS : nombrePersona.toUpperCase();
		correoElectronico = StringUtils.isEmpty(correoElectronico) ? CatalogosConstantes.TODOS : correoElectronico;
	}
	
	/**
	 * Ajusta los criterios de busqueda
	 */
	private void ajustarCriteriosBusqueda() {
		if(CatalogosConstantes.TODOS.equals(nombrePersona)) {
			nombrePersona = null;
		}
		if(CatalogosConstantes.TODOS.equals(correoElectronico)) {
			correoElectronico = null;
		}
	}
	
	public void buscaInstitucion(ActionEvent e){
		getBuscaInstitucion();
	}
	
	
	public String getBuscaInstitucion(){
		if(StringUtils.isNotBlank(this.claveInstitucion) && claveInstitucion.length() >= Constantes.LONGITUD_INSTITUCION){
        	Institucion institucion = consultaCatService.findInstitucionByClaveFolio(this.claveInstitucion);
        	if(institucion != null){
        		this.nombreInstitucion = institucion.getNombreCorto();
        	}else{
        		this.nombreInstitucion = "";
        	}
        }else{
        	this.nombreInstitucion = "";
        }
		return null;
	}
	
	public String getListaDistribucionDescripcion(){
		String opc = null;
		if(this.getListaDistribucion() == null){
			return "";
		}
		if(this.OPCION_TODOS.equals(this.getListaDistribucion())){
			opc="Todas";
		}else{
			for(SelectItem si : this.comboListaDistribucion){
				if(si.getValue().equals(this.getListaDistribucion())){
					opc=si.getLabel();
				}
			}
		}
		return opc;
	}
	
	public String getDescripcionSummary(){
		if(StringUtils.isNotEmpty(this.descripcion)){
			return this.descripcion.toUpperCase();
		}
		return "Todas";
	}
	public String getClaveInstitucionSummary(){
		if(StringUtils.isNotEmpty(this.claveInstitucion)){
			return this.claveInstitucion;
		}
		return "Todas";
	}
	/**
	 * Método para obtener el atributo nombrePersona
	 * @return El atributo nombrePersona
	 */
	public String getNombrePersona() {
		return nombrePersona;
	}

	/**
	 * Método para establecer el atributo nombrePersona
	 * @param nombrePersona El valor del atributo nombrePersona a establecer.
	 */
	public void setNombrePersona(String nombrePersona) {
		this.nombrePersona = nombrePersona;
	}

	/**
	 * Método para obtener el atributo correoElectronico
	 * @return El atributo correoElectronico
	 */
	public String getCorreoElectronico() {
		return correoElectronico;
	}

	/**
	 * Método para establecer el atributo correoElectronico
	 * @param correoElectronico El valor del atributo correoElectronico a establecer.
	 */
	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	/**
	 * Método para obtener el atributo persona
	 * @return El atributo persona
	 */
	public Persona getPersona() {
		return persona;
	}

	/**
	 * Método para establecer el atributo persona
	 * @param persona El valor del atributo persona a establecer.
	 */
	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	/**
	 * Método para obtener el atributo personas
	 * @return El atributo personas
	 */
	public List<PersonaVista> getPersonas() {
		return personas;
	}

	/**
	 * Método para establecer el atributo personas
	 * @param personas El valor del atributo personas a establecer.
	 */
	public void setPersonas(List<PersonaVista> personas) {
		this.personas = personas;
	}

	/**
	 * Método para obtener el atributo personaService
	 * @return El atributo personaService
	 */
	public PersonaService getPersonaService() {
		return personaService;
	}

	/**
	 * Método para establecer el atributo personaService
	 * @param personaService El valor del atributo personaService a establecer.
	 */
	public void setPersonaService(PersonaService personaService) {
		this.personaService = personaService;
	}

	/**
	 * @return the nombreInstitucion
	 */
	public String getNombreInstitucion() {
		return nombreInstitucion;
	}

	/**
	 * @param nombreInstitucion the nombreInstitucion to set
	 */
	public void setNombreInstitucion(String nombreInstitucion) {
		this.nombreInstitucion = nombreInstitucion;
	}

	/**
	 * @return the claveInstitucion
	 */
	public String getClaveInstitucion() {
		return claveInstitucion;
	}

	/**
	 * @param claveInstitucion the claveInstitucion to set
	 */
	public void setClaveInstitucion(String claveInstitucion) {
		this.claveInstitucion = claveInstitucion;
	}

	/**
	 * @param consultaCatService the consultaCatService to set
	 */
	public void setConsultaCatService(ConsultaCatalogoService consultaCatService) {
		this.consultaCatService = consultaCatService;
	}

	/**
	 * @return the enabledInstitucion
	 */
	public boolean isEnabledInstitucion() {
		return enabledInstitucion;
	}

	/**
	 * @param enabledInstitucion the enabledInstitucion to set
	 */
	public void setEnabledInstitucion(boolean enabledInstitucion) {
		this.enabledInstitucion = enabledInstitucion;
	}

	/**
	 * @return the listaDistribucion
	 */
	public Integer getListaDistribucion() {
		return listaDistribucion;
	}

	/**
	 * @param listaDistribucion the listaDistribucion to set
	 */
	public void setListaDistribucion(Integer listaDistribucion) {
		this.listaDistribucion = listaDistribucion;
	}
	
	public void setListaDistribucion(String listaDistribucion) {
		if(listaDistribucion != null){
			this.listaDistribucion = Integer.valueOf(listaDistribucion);
		}
	}
	/**
	 * @return the listasDistribucion
	 */
	public List<ListaDistribucion> getListasDistribucion() {
		return listasDistribucion;
	}

	/**
	 * @param listasDistribucion the listasDistribucion to set
	 */
	public void setListasDistribucion(List<ListaDistribucion> listasDistribucion) {
		this.listasDistribucion = listasDistribucion;
	}

	/**
	 * @param listaDistribucionService the listaDistribucionService to set
	 */
	public void setListaDistribucionService(
			ListaDistribucionService listaDistribucionService) {
		this.listaDistribucionService = listaDistribucionService;
	}

	/**
	 * @param comboListaDistribucion the comboListaDistribucion to set
	 */
	public void setComboListaDistribucion(List<SelectItem> comboListaDistribucion) {
		this.comboListaDistribucion = comboListaDistribucion;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
