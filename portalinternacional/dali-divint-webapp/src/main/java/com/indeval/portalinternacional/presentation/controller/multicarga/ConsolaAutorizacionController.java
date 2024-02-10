package com.indeval.portalinternacional.presentation.controller.multicarga;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.MulticargaInternacionalService;
import com.indeval.portalinternacional.middleware.servicios.modelo.EstadoMulticarga;
import com.indeval.portalinternacional.middleware.servicios.modelo.TipoOperacionMulticarga;
import com.indeval.portalinternacional.middleware.servicios.multicarga.excell.vo.MulticargaVO;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;

public class ConsolaAutorizacionController extends ControllerBase{
	
	/** Log de clase. */
	private static final Logger log = LoggerFactory.getLogger(ConsolaAutorizacionController.class);
    
	private List<SelectItem> listaEstadoRegistro;
	
	private List<SelectItem> listaTipoOperacion;
	
	private List listaErrores;
	
	private boolean showLink = false;
	
	private boolean banderaBitacoraConsulta = false;
	
	private String estadoOperacionCadena;
	
	private String tipoOperacionCadena;
	
	private Long idTipoOperacion;
	
	private boolean consultaEjecutada;
	
	private int totalPaginas = -1;	
		
	private PaginaVO resultados = null;
	
	private Date fechaCargaInicio;
	
	private Date fechaCargaFin;
	
	private Date fechaActualizacionInicio;
	
	private Date fechaActualizacionFin;
	
	private String nombreUsuario;
			
	private MulticargaVO multicargaVO;	
	// servicio
	private MulticargaInternacionalService multicargaInternacionalService;
	
	
	
	
	/**
	 * Inicializa el bean
	 * @return
	 */
	public String getInit()
	{
		if(paginaVO == null)
			paginaVO = new PaginaVO();
		
		this.estadoOperacionCadena = "-1";
		this.tipoOperacionCadena = "-1";
		this.fechaCargaInicio = null;
		this.fechaCargaFin = null;
		this.fechaActualizacionInicio = null;
		this.fechaActualizacionFin = null;
		this.listaErrores = null;
		
		
		inicializaEstadoRegistro() ;		
		inicializaTipoOperacion();		
		
		paginaVO.setRegistrosXPag(50);
		paginaVO.setOffset(0);
		
		this.nombreUsuario = getNombreUsuarioSesion();
		banderaBitacoraConsulta = false;
		
				
		return null;
	}
	
	/*
	 * =====================BOTONES========================
	 */
	
	/**
	 * Limpia todos los campos
	 * @param evt
	 */
	public void limpiar(ActionEvent evt)
	{
        banderaBitacoraConsulta = false;       
 		
        this.estadoOperacionCadena = "-1";
		this.tipoOperacionCadena = "-1";
		this.fechaCargaInicio = null;
		this.fechaCargaFin = null;
		this.fechaActualizacionInicio = null;
		this.fechaActualizacionFin = null;
		this.listaErrores = null;
 		
		if(resultados != null)
			resultados.getRegistros().clear();
		if(paginaVO.getRegistros() != null) {
			paginaVO.getRegistros().clear();
		}
		paginaVO.setRegistrosXPag(50);
		paginaVO.setOffset(0);		
		
		setConsultaEjecutada(false);
		setShowLink(false);
	}
	
	/**
	 * Buscar cargas
	 * @param evt
	 */
	public void buscarDetalleMulticargas(ActionEvent evt)
	{		
		paginaVO.setRegistrosXPag(50);
		paginaVO.setOffset(0);
        getPaginaVO().setRegistros(null);
		ejecutarConsulta();
	}		
	
	/**
	 * Ejecuta la consulta y calcula el numero de pagina para la paginacion.
	 *  Este m&eacute;todo es un override de la clase padre
	 */
	@Override
	public String ejecutarConsulta(){		
		setParams();
		try{
			paginaVO = multicargaInternacionalService.consultaOperacionesMulticarga(multicargaVO, paginaVO);            
        
			totalPaginas = paginaVO.getTotalRegistros() / paginaVO.getRegistrosXPag();
		
			if(paginaVO.getTotalRegistros() % paginaVO.getRegistrosXPag() > 0){
				totalPaginas++;		
			}
			totalPaginas = (totalPaginas <= 0) ? 1 : totalPaginas;
		
			setConsultaEjecutada(true);
		}catch(BusinessException be){
			addErrorMessage(be.getMessage());
			be.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Arma el VO con los par&aacute;metros de consulta
	 * */
	private void setParams() {
		multicargaVO = new MulticargaVO();

		if(this.fechaCargaInicio != null ) {
			multicargaVO.setFechaCargaInicio(fechaCargaInicio);
		}
		if(this.fechaCargaFin != null ) {
			multicargaVO.setFechaCargaFin(fechaCargaFin);
		}
		if(this.fechaActualizacionInicio != null ) {
			multicargaVO.setFechaActualizacionInicio(fechaActualizacionInicio);
		}
		if(this.fechaActualizacionFin != null ) {
			multicargaVO.setFechaActualizacionFin(fechaActualizacionFin);
		}
		
		if (StringUtils.isNotBlank(estadoOperacionCadena) && this.estadoOperacionCadena.matches("-*[0-9]+")) {			
			multicargaVO.setEstadoRegistro(Long.valueOf(estadoOperacionCadena));
		}		
		if (StringUtils.isNotBlank(tipoOperacionCadena) && this.tipoOperacionCadena.matches("-*[0-9]+")) {			
			multicargaVO.setTipoOperacion(Long.valueOf(tipoOperacionCadena));
		}
		
		
	}
	
	/**
	 * toma el evento del bot&oacute;n borrar archivo
	 * */
	public void borrarArchivo(ActionEvent event){
		listaErrores = null;
		Long idRegistro = (Long)event.getComponent().getAttributes().get("idRegistro");
		if(idRegistro != null){
			Long id = Long.valueOf(idRegistro);
			log.debug("idMulticarga"+idRegistro);
			try{
				multicargaInternacionalService.borrarRegistroMulticarga(id,nombreUsuario);
				agregarInfoMensaje("El borrado de los registros se realizó de manera exitosa.");
				buscarDetalleMulticargas(event);
			}catch(BusinessException be){
				addErrorMessage(be.getMessage());
				be.printStackTrace();
			}catch(Exception e){
				e.printStackTrace();
			}	
			
		}
			
	}
	
	/**
	 * toma el evento del bot&oacute;n Autorizar archivo, si hay registros con error los guarda en listaErrores 
	 * */
	public void autorizarArchivo(ActionEvent event){
		listaErrores = null;
		Long idRegistro = (Long)event.getComponent().getAttributes().get("idRegistro");		
		if(idRegistro != null){
			Long id = Long.valueOf(idRegistro);
			log.debug("idMulticarga"+idRegistro);
			try{
				listaErrores = multicargaInternacionalService.autorizarRegistroMulticarga(id, nombreUsuario);
				if(listaErrores != null && listaErrores.size() > 0){
					Long tipoOperacion = (Long)event.getComponent().getAttributes().get("tipoOperacion");					
					setIdTipoOperacion(tipoOperacion); 
					setShowLink(true);
					addWarnMessage("La autorización genero registros con error.");						
				}else{
					agregarInfoMensaje("La autorización se realizó de manera exitosa.");						
				}
				buscarDetalleMulticargas(event);
			}catch(BusinessException be){
				addErrorMessage(be.getMessage());
				be.printStackTrace();
			}catch(Exception e){
				e.printStackTrace();
			}		
			
		}	
	}
	
	/**
	 * toma el action del bot&oacute;n generarExcel, regresa el outcome dependiendo el tipo de operaci&oacute;n
	 * */
	public String muestraErroresCarga(){				  
		String outcome= null;
		if(listaErrores != null && listaErrores.size() > 0){
			if(getIdTipoOperacion() == 1l){
				outcome = "detalleErrorAdpXls";
			}else if(getIdTipoOperacion() == 2l){
				outcome = "detalleErrorMultiempresaXls";				
			}			
		}
		return outcome;
	}
	
	
	/*
	 * =============TERMINA BOTONES========================
	 */
	
	
	  /**
	  * obtiene la lista de tipos de carga 
	  */
		public List<SelectItem> getTipoOperacion(){	
			if(this.listaTipoOperacion != null && this.listaTipoOperacion.size() > 0){
				return this.listaTipoOperacion;
			}
			List<TipoOperacionMulticarga> tipoOperacionMulticarga = multicargaInternacionalService.obtieneCatalogoTipoOperacionMulticarga();
			List <SelectItem> listaTipos = new ArrayList<SelectItem>();
			if( tipoOperacionMulticarga != null){
				for (TipoOperacionMulticarga tipos : tipoOperacionMulticarga){ 	    		
					listaTipos.add(new SelectItem(tipos.getIdTipoOperacionMulticarga().toString(), tipos.getTipoOperacionMulticarga()));	    		    		
				}
			}else{
				listaTipos.add(new SelectItem("-2","VACIO"));
			}
			this.listaTipoOperacion=listaTipos;
			return listaTipoOperacion;
    }
		

	/**
	   * Inicializa lista de tipos de carga
	   */
	    private void inicializaTipoOperacion() {		
	    	listaTipoOperacion = getTipoOperacion();	
	    }
	    
	    
	  /**
		 * obtiene la lista de estados 
		 */
		public List<SelectItem> getEstadoRegistro(){	
			if(this.listaEstadoRegistro != null && this.listaEstadoRegistro.size() > 0){
				return this.listaEstadoRegistro;
			}
	    	List<EstadoMulticarga> estadoMulticarga = multicargaInternacionalService.obtieneCatalogoEstadoMulticarga();
	    	List <SelectItem> listaEstados = new ArrayList<SelectItem>();
	    	if( estadoMulticarga != null){
		    	for (EstadoMulticarga estados : estadoMulticarga){ 	    		
		    		listaEstados.add(new SelectItem(estados.getIdEstadoMulticarga().toString(), estados.getEstadoMulticarga()));	    		    		
		    	}
	    	}else{
	    		listaEstados.add(new SelectItem("-2","VACIO"));
	    	}
	    	this.listaEstadoRegistro=listaEstados;
	    	return listaEstadoRegistro;
	    }
		
		
		/**
		 * Inicializa lista de estados
		 */
		private void inicializaEstadoRegistro() {		
			listaEstadoRegistro = getEstadoRegistro();	
		}
	
		
		/**
		 * retorna el valor del estado seleccionado
		 */
		public String getSelectedEstado(){
			String resultado = getSelected(getEstadoOperacionCadena() ,this.listaEstadoRegistro);
			if(resultado != null){
				return resultado;
			}
			return "TODAS"; 
		}
		
		/**
		 * retorna el valor del tipo de operaci&oacute;n seleccionado		 
		 */
		public String getSelectedTipo(){
			String resultado = getSelected(getTipoOperacionCadena() ,this.listaTipoOperacion);
			if(resultado != null){
				return resultado;
			}
			return "TODOS"; 
		}
		
		/**
		 * m&eacute;todo de ayuda para tomar el valor de las listas tipoOperacion y estadoRegistro
		 * */
		private String getSelected(String key, List<SelectItem> lista){
			String resultado = null;
			for(SelectItem item : lista){
				if(key != null && item.getValue().equals(key))
					resultado=item.getLabel();
			}
			return resultado;		
		}
				
	
	/**
	 * @return the listaEstadoCarga
	 */
	public List<SelectItem> getListaEstadoRegistro() {
		return listaEstadoRegistro;
	}



	/**
	 * @param listaEstadoCarga the listaEstadoCarga to set
	 */
	public void setListaEstadoRegistro(List<SelectItem> listaEstadoRegistro) {
		this.listaEstadoRegistro = listaEstadoRegistro;
	}



	/**
	 * @return the listaTipoCarga
	 */
	public List<SelectItem> getListaTipoOperacion() {
		return listaTipoOperacion;
	}

	

	/**
	 * @param listaTipoCarga the listaTipoCarga to set
	 */
	public void setListaTipoOperacion(List<SelectItem> listaTipoOperacion) {
		this.listaTipoOperacion = listaTipoOperacion;
	}

	/**
	 * @return the listaErrores
	 */
	public List getListaErrores() {
		return listaErrores;
	}

	/**
	 * @param listaErrores the listaErrores to set
	 */
	public void setListaErrores(List listaErrores) {
		this.listaErrores = listaErrores;
	}

	/**
	 * @return the showLink
	 */
	public boolean isShowLink() {
		return showLink;
	}

	/**
	 * @param showLink the showLink to set
	 */
	public void setShowLink(boolean showLink) {
		this.showLink = showLink;
	}

	/**
	 * @return the banderaBitacoraConsulta
	 */
	public boolean isBanderaBitacoraConsulta() {
		return banderaBitacoraConsulta;
	}

	/**
	 * @param banderaBitacoraConsulta the banderaBitacoraConsulta to set
	 */
	public void setBanderaBitacoraConsulta(boolean banderaBitacoraConsulta) {
		this.banderaBitacoraConsulta = banderaBitacoraConsulta;
	}

	/**
	* @param tipoOperacion the tipoOperacion to set
	*/
	
	public void setTipoOperacionCadena(String tipoOperacionCadena) {
		this.tipoOperacionCadena = tipoOperacionCadena;
	}
	
	public String getTipoOperacionCadena(){
		return tipoOperacionCadena; 
	}
	/**
	 * @return the idTipoOperacion
	 */
	public Long getIdTipoOperacion() {
		return idTipoOperacion;
	}

	/**
	 * @param idTipoOperacion the idTipoOperacion to set
	 */
	public void setIdTipoOperacion(Long idTipoOperacion) {
		this.idTipoOperacion = idTipoOperacion;
	}

	/**
	 * @param estadoCarga the estadoCarga to set
	 */
	public void setEstadoOperacionCadena(String estadoOperacionCadena) {
		this.estadoOperacionCadena = estadoOperacionCadena;
	}

	/**
	 * @return the estadoOperacion
	 */
	public String getEstadoOperacionCadena() {
		return estadoOperacionCadena;
	}

	/**
	 * @return the consultaEjecutada
	 */
	public boolean isConsultaEjecutada() {
		return consultaEjecutada;
	}



	/**
	 * @param consultaEjecutada the consultaEjecutada to set
	 */
	public void setConsultaEjecutada(boolean consultaEjecutada) {
		this.consultaEjecutada = consultaEjecutada;
	}



	/**
	 * @return the totalPaginas
	 */
	public int getTotalPaginas() {
		return totalPaginas;
	}



	/**
	 * @param totalPaginas the totalPaginas to set
	 */
	public void setTotalPaginas(int totalPaginas) {
		this.totalPaginas = totalPaginas;
	}



	/**
	 * @return the resultados
	 */
	public PaginaVO getResultados() {
		return resultados;
	}



	/**
	 * @param resultados the resultados to set
	 */
	public void setResultados(PaginaVO resultados) {
		this.resultados = resultados;
	}



	/**
	 * @return the fechaCargaInicio
	 */
	public Date getFechaCargaInicio() {
		return fechaCargaInicio;
	}

	/**
	 * @param fechaCargaInicio the fechaCargaInicio to set
	 */
	public void setFechaCargaInicio(Date fechaCargaInicio) {
		this.fechaCargaInicio = fechaCargaInicio;
	}

	/**
	 * @return the fechaCargaFin
	 */
	public Date getFechaCargaFin() {
		return fechaCargaFin;
	}

	/**
	 * @param fechaCargaFin the fechaCargaFin to set
	 */
	public void setFechaCargaFin(Date fechaCargaFin) {
		this.fechaCargaFin = fechaCargaFin;
	}

	/**
	 * @return the fechaActualizacionInicio
	 */
	public Date getFechaActualizacionInicio() {
		return fechaActualizacionInicio;
	}

	/**
	 * @param fechaActualizacionInicio the fechaActualizacionInicio to set
	 */
	public void setFechaActualizacionInicio(Date fechaActualizacionInicio) {
		this.fechaActualizacionInicio = fechaActualizacionInicio;
	}

	/**
	 * @return the fechaActualizacionFin
	 */
	public Date getFechaActualizacionFin() {
		return fechaActualizacionFin;
	}

	/**
	 * @param fechaActualizacionFin the fechaActualizacionFin to set
	 */
	public void setFechaActualizacionFin(Date fechaActualizacionFin) {
		this.fechaActualizacionFin = fechaActualizacionFin;
	}

	/**
	 * @return the nombreUsuario
	 */
	public String getNombreUsuario() {
		return nombreUsuario;
	}

	/**
	 * @param nombreUsuario the nombreUsuario to set
	 */
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	/**
	 * @return the multicargaVO
	 */
	public MulticargaVO getMulticargaVO() {
		return multicargaVO;
	}

	/**
	 * @param multicargaVO the multicargaVO to set
	 */
	public void setMulticargaVO(MulticargaVO multicargaVO) {
		this.multicargaVO = multicargaVO;
	}
	
	/**se inyecta la dependencia*/
	public void setMulticargaInternacionalService(
			MulticargaInternacionalService multicargaInternacionalService) {
		this.multicargaInternacionalService = multicargaInternacionalService;
	}

}
