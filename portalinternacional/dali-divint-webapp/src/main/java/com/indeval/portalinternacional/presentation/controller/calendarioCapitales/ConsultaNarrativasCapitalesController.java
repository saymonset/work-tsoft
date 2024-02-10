package com.indeval.portalinternacional.presentation.controller.calendarioCapitales;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.CapitalesDistribucionService;
import com.indeval.portalinternacional.middleware.servicios.modelo.capitales.NarrativaCapitalesVO;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;

/**
 * Controller para el pop up de Detalle Narrativas Capitales (detalleNarrativasCapitales.xhtml)
 * @author javier.perez
 *
 */
public class ConsultaNarrativasCapitalesController extends ControllerBase {
	
	
	private static final Logger LOG = LoggerFactory.getLogger(ConsultaNarrativasCapitalesController.class);
	
	private String idCalendario;
	private String idHistorico;
	private String origen;
	private NarrativaCapitalesVO narrativaVO;
	private CapitalesDistribucionService capitalesDistribucionService;
	private List<NarrativaCapitalesVO> listaNarrativas;
	private boolean isCalendario;	
	private boolean isAgregar;
	private boolean isEditar;
	private String nuevaNarrativa;
	private boolean bloqueaBoton;
	private static Pattern PATRON_NARRATIVA = Pattern.compile(".*[ñ|Ñ|\\<|\\>|=].*");
	
	/**
	 * M&eacute;todo de inico de la pantalla
	 * @return
	 */
	public String getInit(){
		Long idCalendario = null;
		Long idHistorico = null;
		limpiaVariables();
		try{
			if(this.origen == null){			
				FacesContext facesContext = FacesContext.getCurrentInstance();
			    this.idCalendario = facesContext.getExternalContext().getRequestParameterMap().get("idCalendario");	
			    this.origen = facesContext.getExternalContext().getRequestParameterMap().get("origen");
			    LOG.info("************* VALOR ID CALENDARIO RECUPERADO: "+this.idCalendario);
			    LOG.info("************* VALOR ORIGEN RECUPERADO: "+this.origen);
			    this.isCalendario = Boolean.valueOf(this.origen);
			    if(!this.isCalendario){
			    	this.idHistorico = facesContext.getExternalContext().getRequestParameterMap().get("idHistorico");
			    	LOG.info("************* VALOR ID HISTORICO RECUPERADO: "+this.idHistorico);
			    }			   
		    	validaParametros();
		    	idCalendario = Long.valueOf(this.idCalendario);
		    	if(!this.isCalendario){
		    		idHistorico = Long.valueOf(this.idHistorico);
		    	}	    						    
			}else{
				idHistorico = this.narrativaVO != null ? this.narrativaVO.getFolioMensaje():null;
				idCalendario = this.narrativaVO != null ? this.narrativaVO.getFolioIndeval():null;
			}
			if(idCalendario != null){				
				this.listaNarrativas = Collections.emptyList();
				if(isCalendario){
					this.listaNarrativas = capitalesDistribucionService.consultaNarrativasForIdCalendarioOrIdHistorico(idCalendario,isCalendario);
				}else{
					this.listaNarrativas = capitalesDistribucionService.consultaNarrativasForIdCalendarioOrIdHistorico(idHistorico,isCalendario);
					List<NarrativaCapitalesVO>  narrativasEnviadas = Collections.emptyList(); 
					narrativasEnviadas = capitalesDistribucionService.consultaNarrativasEnviadasPorIdCalendario(idCalendario);					
					if(this.listaNarrativas.isEmpty() && !narrativasEnviadas.isEmpty()){
						this.listaNarrativas = new ArrayList<NarrativaCapitalesVO>();						
					}
					this.listaNarrativas.addAll(narrativasEnviadas);					
				}				   			 
				Collections.sort(this.listaNarrativas,Collections.reverseOrder());	
			}else{
				this.listaNarrativas = Collections.emptyList();
			}
			   	
			if(this.isCalendario){
	    		if(this.listaNarrativas.isEmpty()){
	    			addErrorMessage("No se encontraron Narrativas para el registro solicitado.");
	    		}    		
	    	}else{
	    		procesaNarrativa();
	    	}
		}catch(BusinessException be){
			LOG.error("Error al cargar pantalla de Narrativas: ",be);
			addErrorMessage(be.getMessage());
		}catch(Exception ex){
			LOG.error("Error al cargar pantalla de Narrativas: ",ex);
			addErrorMessage("Error al consultar las Narrativas, consulte a su administrador");
		}
		
		return null;
	}
	
	/**
	 * Inicializa las variables
	 */
	private void limpiaVariables(){			
		this.isAgregar = false;
		this.isEditar = false;
		this.bloqueaBoton = true;		
		this.nuevaNarrativa = "";
	}
	
	/**
	 * Procesa la Narrativa cuando el origen es la pantalla de hist&oacute;ricos
	 */
	private void procesaNarrativa(){		
		this.narrativaVO = null;
		if(this.listaNarrativas.isEmpty()){
			this.isAgregar = true;			
		}else{
			NarrativaCapitalesVO narrativaMensaje = null;
			for(NarrativaCapitalesVO narrativaFinal:this.listaNarrativas){
				if(StringUtils.equalsIgnoreCase("U",narrativaFinal.getOrigen())){
					this.isEditar = true;
					this.narrativaVO = narrativaFinal;
					this.nuevaNarrativa = this.narrativaVO.getNarrativa();
					break;
				}else{
					narrativaMensaje = narrativaFinal; 
				}
			}			
			if(this.narrativaVO == null){				
				this.isAgregar = true;
				this.narrativaVO = narrativaMensaje;
			}			
		}
	}
	
	
	/**
	 * Valida los parametros de entrada para la consulta
	 * @param idCalendario
	 * @param origen
	 * @throws BusinessException
	 */
	private void validaParametros()throws BusinessException{
		if(this.idCalendario == null || !StringUtils.isNumeric(this.idCalendario)){
			throw new BusinessException("Campo Folio Indeval Inv\u00E1lido");
		}
		if(StringUtils.isBlank(this.origen) || 
				!(StringUtils.equals(this.origen,"true") || StringUtils.equals(this.origen,"false")) ){
			throw new BusinessException("Campo Origen Inv\u00E1lido");
		}
		if(!this.isCalendario){
			if(this.idHistorico == null || !StringUtils.isNumeric(this.idHistorico)){
				throw new BusinessException("Campo Folio Mensaje Inv\u00E1lido");
			}
		}
	}
	
	/**
	 * Toma el evento de los botones Editar/Agregar
	 * @param e
	 */
	public void agregarNarrativa(ActionEvent e){
		this.bloqueaBoton = !this.bloqueaBoton;
	}
	
	/**
	 * Toma el evento del bot&oacute;n guardar Narrativa
	 * @param e
	 */
	public void guardarNarrativa(ActionEvent e){
		if(StringUtils.isNotBlank(this.nuevaNarrativa)){			
			if(PATRON_NARRATIVA.matcher(this.nuevaNarrativa.replaceAll("\r\n","")).matches()){
				addErrorMessage("Narrativa con car\u00E1cteres no permitidos (ï¿½, <, >, =)");
				return;
			}
			try{
				if(this.isAgregar){				
					this.narrativaVO = this.capitalesDistribucionService.getCalendarioHistoricoById(Long.valueOf(idHistorico));				
					this.narrativaVO.setNarrativa(this.nuevaNarrativa);
					this.narrativaVO.setUsuario(getNombreUsuarioSesion().toUpperCase());
					this.capitalesDistribucionService.guardarNarrrativa(this.narrativaVO);
				}else{
					this.narrativaVO.setNarrativa(this.nuevaNarrativa);
					this.narrativaVO.setUsuario(getNombreUsuarioSesion().toUpperCase());
					this.capitalesDistribucionService.actualizaNarrrativa(this.narrativaVO);
				}		
			}catch(BusinessException be){
				LOG.error("Error al procesar la narrativa: ",be);
				addErrorMessage(be.getMessage());
			}catch(Exception ex){
				LOG.error("Error al procesar la narrativa: ",ex);
				addErrorMessage("Error al procesar la narrativa.");
			}
							
		}				
	}
	
	/**
	 * Toma el evento del bot&oacute;n eliminar Narrativa
	 * @param e
	 */
	public void eliminarNarrativa(ActionEvent e){
		if(this.narrativaVO != null){
			try{
				this.capitalesDistribucionService.eliminaNarrrativa(this.narrativaVO);
			}catch(Exception ex){
				LOG.error("Error al intentar eliminar la narrativa: ",ex);
				addErrorMessage("Error al intentar eliminar la narrativa.");	
			}
			
		}
	}
	
////////////////////////////////////////// Init para las narrativas de usuario externo, solo narrativas enviadas /////////////////////////////	
	/**
	 * Consulta las narrativas por idCalendario y que hayan sido enviadas
	 * @return
	 */
	public String getInitConsulta(){
		Long idCalendario = null;
		FacesContext facesContext = FacesContext.getCurrentInstance();
	    this.idCalendario = facesContext.getExternalContext().getRequestParameterMap().get("idCalendario");
	    LOG.info("************* VALOR ID CALENDARIO RECUPERADO: "+this.idCalendario);
	    try{
	    	if(this.idCalendario == null || !StringUtils.isNumeric(this.idCalendario)){
				throw new BusinessException("Par\u00E1metro Inv\u00E1lido, consulte con su administrador");
			}
			idCalendario = Long.valueOf(this.idCalendario);
			this.listaNarrativas = Collections.emptyList();
			this.listaNarrativas = capitalesDistribucionService.consultaNarrativasEnviadasPorIdCalendario(idCalendario);
			if(this.listaNarrativas.isEmpty()){
    			addErrorMessage("No se encontraron Narrativas para el registro solicitado");
    		}else{
    			Collections.sort(this.listaNarrativas,Collections.reverseOrder());	
    		} 
	    }catch(BusinessException e){
	    	LOG.error("Error al recibir el parametro: ",e);
	    	addErrorMessage(e.getMessage());	    		    	
	    }catch(Exception e){
	    	LOG.error("Error al cargar la pantalla: ",e);
	    	addErrorMessage("Error al recibir los par\u00E1metros, consulte con su administrador");	    	
	    }
		
		return null;
	}
			
	/**
	 * 
	 * @param e
	 */
	public void cancelar(ActionEvent e){
		this.bloqueaBoton = true;
	}

	/**
	 * @return the idCalendario
	 */
	public String getIdCalendario() {
		return idCalendario;
	}

	/**
	 * @param idCalendario the idCalendario to set
	 */
	public void setIdCalendario(String idCalendario) {
		this.idCalendario = idCalendario;
	}



	/**
	 * @return the narrativaVO
	 */
	public NarrativaCapitalesVO getNarrativaVO() {
		return narrativaVO;
	}



	/**
	 * @param narrativaVO the narrativaVO to set
	 */
	public void setNarrativaVO(NarrativaCapitalesVO narrativaVO) {
		this.narrativaVO = narrativaVO;
	}



	/**
	 * @return the origen
	 */
	public String getOrigen() {
		return origen;
	}



	/**
	 * @param origen the origen to set
	 */
	public void setOrigen(String origen) {
		this.origen = origen;
	}

	/**
	 * @param capitalesDistribucionService the capitalesDistribucionService to set
	 */
	public void setCapitalesDistribucionService(CapitalesDistribucionService capitalesDistribucionService) {
		this.capitalesDistribucionService = capitalesDistribucionService;
	}

	/**
	 * @return the listaNarrativas
	 */
	public List<NarrativaCapitalesVO> getListaNarrativas() {
		return listaNarrativas;
	}

	/**
	 * @param listaNarrativas the listaNarrativas to set
	 */
	public void setListaNarrativas(List<NarrativaCapitalesVO> listaNarrativas) {
		this.listaNarrativas = listaNarrativas;
	}

	/**
	 * @return the isCalendario
	 */
	public boolean getIsCalendario() {
		return isCalendario;
	}

	/**
	 * @param isCalendario the isCalendario to set
	 */
	public void setIsCalendario(boolean isCalendario) {
		this.isCalendario = isCalendario;
	}

	/**
	 * @return the isAgregar
	 */
	public boolean getIsAgregar() {
		return isAgregar;
	}

	/**
	 * @param isAgregar the isAgregar to set
	 */
	public void setIsAgregar(boolean isAgregar) {
		this.isAgregar = isAgregar;
	}

	/**
	 * @return the isEditar
	 */
	public boolean getIsEditar() {
		return isEditar;
	}

	/**
	 * @param isEditar the isEditar to set
	 */
	public void setIsEditar(boolean isEditar) {
		this.isEditar = isEditar;
	}

	/**
	 * @return the nuevaNarrativa
	 */
	public String getNuevaNarrativa() {
		return nuevaNarrativa;
	}

	/**
	 * @param nuevaNarrativa the nuevaNarrativa to set
	 */
	public void setNuevaNarrativa(String nuevaNarrativa) {
		this.nuevaNarrativa = nuevaNarrativa;
	}

	/**
	 * @return the bloqueaBoton
	 */
	public boolean isBloqueaBoton() {
		return bloqueaBoton;
	}

	/**
	 * @param bloqueaBoton the bloqueaBoton to set
	 */
	public void setBloqueaBoton(boolean bloqueaBoton) {
		this.bloqueaBoton = bloqueaBoton;
	}

	/**
	 * @param isCalendario the isCalendario to set
	 */
	public void setCalendario(boolean isCalendario) {
		this.isCalendario = isCalendario;
	}

	/**
	 * @param isAgregar the isAgregar to set
	 */
	public void setAgregar(boolean isAgregar) {
		this.isAgregar = isAgregar;
	}

	/**
	 * @param isEditar the isEditar to set
	 */
	public void setEditar(boolean isEditar) {
		this.isEditar = isEditar;
	}

	/**
	 * @return the idHistorico
	 */
	public String getIdHistorico() {
		return idHistorico;
	}

	/**
	 * @param idHistorico the idHistorico to set
	 */
	public void setIdHistorico(String idHistorico) {
		this.idHistorico = idHistorico;
	}

}
