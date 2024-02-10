package com.indeval.portalinternacional.presentation.controller.beneficiarios;

import static com.indeval.portalinternacional.middleware.servicios.dto.SettlementDisciplineRegimeVO.createSettlementDisciplineRegimeVoToDelete;
import static com.indeval.portalinternacional.middleware.servicios.dto.SettlementDisciplineRegimeVO.createSettlementDisciplineRegimeVoToModelWithChanges;
import static com.indeval.portalinternacional.middleware.servicios.dto.SettlementDisciplineRegimeVO.transformSettlementDisciplineRegimeVoToModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.SettlementDisciplineRegimeService;
import com.indeval.portalinternacional.middleware.services.makerchecker.SolicitudAutorizacionService;
import com.indeval.portalinternacional.middleware.services.util.JsonUtil;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.dto.SettlementDisciplineRegimeVO;
import com.indeval.portalinternacional.middleware.servicios.dto.SolicitudAutorizacionDTO;
import com.indeval.portalinternacional.middleware.servicios.modelo.SettlementDisciplineRegime;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;

public class SettlementDisciplineRegimeController extends ControllerBase{
	
	/** Log de clase. */
    private static final Logger log = LoggerFactory.getLogger(SettlementDisciplineRegimeController.class);
    
    /** Constante que indica el ID de la pagina de consulta de beneficiarios */
    private static final String CONSULTA_CUSTODIOS_CSDR = "admonCustodiosCsdr"; //$NON-NLS-1$
    private static final String NUEVO_CUSTODIO_CSDR = "nuevoCustodiosCsdr"; //$NON-NLS-1$
    private static final String tipoConfiguracionCustodio = "Custodio";
    private static final String tipoConfiguracionInstitucion = "Institucion";
	
	private SettlementDisciplineRegimeService settlementDisciplineRegimeService;
	
	private SolicitudAutorizacionService solicitudAutorizacionService;
	
	private List<SettlementDisciplineRegimeVO> listSettlementDisciplineRegime;
	private List<SettlementDisciplineRegimeVO> listConfiguracionInstitucion;
	
	private SettlementDisciplineRegimeVO settDisciplineRegimeVO = new SettlementDisciplineRegimeVO();
	
	private String tipoConfiguracion;
	
	private boolean edicionHabilitada;
	
	private String selectedItemConfiguracion;
	
	public String getInitConfiguracion() {
		log.debug("SettlementDisciplineRegimeController :: getInitConfiguracion");
		this.listSettlementDisciplineRegime = new ArrayList<SettlementDisciplineRegimeVO>();
		this.listConfiguracionInstitucion = new ArrayList<SettlementDisciplineRegimeVO>();
		this.listSettlementDisciplineRegime = settlementDisciplineRegimeService.getSettlementDisciplineRegimeForCustodio();
//		this.listConfiguracionInstitucion = settlementDisciplineRegimeService.getSettlementDisciplineRegimeForInstitucion();
		return null;
	}
	
	public String getInitNuevaConfiguracion(){
		log.debug("SettlementDisciplineRegimeController :: getInitNuevaConfiguracion: " + this.tipoConfiguracion);
		limpiar();
		return null;
	}
	
	/**
	 * 
	 * @param event
	 * @return
	 */
    public String modificarAction() {
    	log.info("SettlementDisciplineRegimeController :: modificarAction");
    	Boolean existenRegistros = Boolean.FALSE;
    	List<SettlementDisciplineRegimeVO> lstConfiguracion = new ArrayList<>();
    	if(StringUtils.isNotEmpty(this.tipoConfiguracion) && this.tipoConfiguracion.equals(tipoConfiguracionCustodio)){
    		lstConfiguracion = this.listSettlementDisciplineRegime;
    	} else if(StringUtils.isNotEmpty(this.tipoConfiguracion) && this.tipoConfiguracion.equals(tipoConfiguracionInstitucion)){
    		lstConfiguracion = this.listConfiguracionInstitucion;
    	}
    	for (SettlementDisciplineRegimeVO settlementDisciplineRegime : lstConfiguracion) {
			if (settlementDisciplineRegime.getIsEditar()) {
		    	/** Se envia solicitud para autorizar el cambio **/
		    	try {
		    		int cambios = 0;
		    		SettlementDisciplineRegimeVO csdrBD = settlementDisciplineRegimeService.getSettlementDisciplineRegimeByID(settlementDisciplineRegime.getIdConfigCsdr());
		    		if(!csdrBD.getHoldAndRelease().equals(settlementDisciplineRegime.getHoldAndRelease())){
		    			cambios++;
		    		}
		    		if(!csdrBD.getPartialSettlement().equals(settlementDisciplineRegime.getPartialSettlement())){
		    			cambios++;
		    		}		    		
		    		if(!csdrBD.getBilateralCancellation().equals(settlementDisciplineRegime.getBilateralCancellation())){
		    			cambios++;
		    		}
		    		if(cambios > 0){
			        	enviaSolicitudAutorizacion(settlementDisciplineRegime, settlementDisciplineRegimeService.getSettlementDisciplineRegimeByID(settlementDisciplineRegime.getIdConfigCsdr()));
						existenRegistros = Boolean.TRUE;
		    		}
		    	} catch (Exception ex){
		    		log.error(ex.getMessage());
		        	FacesContext.getCurrentInstance().addMessage(null,
		                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocurrió un error al enviar la solicitud: " + ex.getMessage(), "Ocurrió un error al enviar la solicitud: " + ex.getMessage()));
		        	return null;
		    	}
			}
		}
    	if(existenRegistros){
        	FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha creado la solicitud para modificar la configuración.", "Se ha creado la solicitud para modificar la configuración."));
    	}

    	return CONSULTA_CUSTODIOS_CSDR;
    }
    
	/**
	 * 
	 * @param event
	 * @return
	 */
    public String eliminarCustodio() {
    	log.info("SettlementDisciplineRegimeController :: eliminarCustodio");
    	Boolean existenRegistros = Boolean.FALSE;
    	List<SettlementDisciplineRegimeVO> lstConfiguracion = new ArrayList<>();
    	if(StringUtils.isNotEmpty(this.tipoConfiguracion) && this.tipoConfiguracion.equals(tipoConfiguracionCustodio)){
    		lstConfiguracion = this.listSettlementDisciplineRegime;
    	} else if(StringUtils.isNotEmpty(this.tipoConfiguracion) && this.tipoConfiguracion.equals(tipoConfiguracionInstitucion)){
    		lstConfiguracion = this.listConfiguracionInstitucion;
    	}
    	for (SettlementDisciplineRegimeVO settlementDisciplineRegime : lstConfiguracion) {			
			if (settlementDisciplineRegime.getIsEliminar()) {
		    	try {
					enviaSolicitudEliminarAutorizacion(settlementDisciplineRegime);
		    	} catch (Exception ex){
		    		log.error(ex.getMessage());
		        	FacesContext.getCurrentInstance().addMessage(null,
		                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocurrió un error al enviar la solicitud: " + ex.getMessage(), "Ocurrió un error al enviar la solicitud: " + ex.getMessage()));
		        	return null;
		    	}
		    	existenRegistros = Boolean.TRUE;
			}
		}
    	if(existenRegistros){
    		FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha creado la solicitud para eliminar la configuración.", "Se ha creado la solicitud para eliminar la configuración."));
    	}
    	
    	return CONSULTA_CUSTODIOS_CSDR;
    }
    
	/**
	 * 
	 * @param event
	 * @return
	 */
    public String nuevaConfiguracion() {
    	log.debug("SettlementDisciplineRegimeController :: nuevaConfiguracion");
    	this.settDisciplineRegimeVO = new SettlementDisciplineRegimeVO();
    	return NUEVO_CUSTODIO_CSDR;
    }
    
    public List <SelectItem> getCustodiosCSDR(){
    	log.debug("SettlementDisciplineRegimeController :: getCustodiosCSDR");
    	List<SettlementDisciplineRegimeVO> lstSettlementDisciplineRegimeVO = settlementDisciplineRegimeService.getCustodiosPendSettlementDisciplineRegime();
    	List <SelectItem> listaEstados = new ArrayList<SelectItem>();
    	for (SettlementDisciplineRegimeVO settleDisciplineRegime : lstSettlementDisciplineRegimeVO) {
    	    listaEstados.add(new SelectItem(settleDisciplineRegime.getIdCuentaBoveda() + "," + settleDisciplineRegime.getCuenta() + "," + settleDisciplineRegime.getDetalleCustodio(), settleDisciplineRegime.getDetalleCustodio()));
    	}

    	return listaEstados;
    }
    
    public List <SelectItem> getInstitucionesCSDR(){
    	log.debug("SettlementDisciplineRegimeController :: getCustodiosCSDR");
    	List<SettlementDisciplineRegimeVO> lstSettlementDisciplineRegimeVO = settlementDisciplineRegimeService.getInstitucionesPendSettlementDisciplineRegime();
    	List <SelectItem> listaEstados = new ArrayList<SelectItem>();
    	for (SettlementDisciplineRegimeVO settleDisciplineRegime : lstSettlementDisciplineRegimeVO) {
    	    listaEstados.add(new SelectItem(settleDisciplineRegime.getIdInstitucion() + "," + settleDisciplineRegime.getFolioInstitucion() + "," + settleDisciplineRegime.getNombreCortoInstitucion(), settleDisciplineRegime.getNombreCortoInstitucion()));
    	}

    	return listaEstados;
    }
	
	/**
	 * 
	 * @param event
	 */
	public void cambiaEdicion(ActionEvent event) {
		this.edicionHabilitada = false;
    	for (Iterator<SettlementDisciplineRegimeVO> iterator = listSettlementDisciplineRegime.iterator(); !this.edicionHabilitada && iterator.hasNext();) {
			if (iterator.next().getIsEditar()) {
				this.edicionHabilitada = true;
			}
		}
    }
	
	public String limpiar(){
		log.debug("SettlementDisciplineRegimeController :: limpiar");
		this.settDisciplineRegimeVO = new SettlementDisciplineRegimeVO();
		this.selectedItemConfiguracion = "-1";
		return null;
	}
	
    public String cancelar() {
    	log.debug("SettlementDisciplineRegimeController :: cancelar");
    	return CONSULTA_CUSTODIOS_CSDR;
    }

    public String guardar() {
    	log.info("SettlementDisciplineRegimeController :: guardar");
    	if(this.selectedItemConfiguracion.equals("-1")){
        	FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe seleccionar un Custodio.", "Debe seleccionar un Custodio."));
        	return null;
    	}

    	/** Se envia solicitud para autorizar el cambio **/
    	try {
        	enviaSolicitudAutorizacion(this.settDisciplineRegimeVO, null);
    	} catch (Exception ex){
    		log.error(ex.getMessage());
        	FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocurrió un error al enviar la solicitud: " + ex.getMessage(), "Ocurrió un error al enviar la solicitud: " + ex.getMessage()));
        	return null;
    	}  	
    	FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha creado la solicitud para el alta la configuración.", "Se ha creado la solicitud para el alta la configuración."));
    	return CONSULTA_CUSTODIOS_CSDR;
    }
    
	public void autorizaCustodio(final ActionEvent event) {
		log.debug("SettlementDisciplineRegimeController :: autorizaCustodio");
		for (SettlementDisciplineRegimeVO settlementDisciplineRegimeVO : this.listSettlementDisciplineRegime) {
			settlementDisciplineRegimeService.autorizaCustodioCSDR(settlementDisciplineRegimeVO);
		}

    	FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Se autorizaron los Custodios.", "Se autorizaron los Custodios."));
		
	}
	
	public String seleccionaConfiguracion(final ActionEvent event) {
		log.debug("SettlementDisciplineRegimeController :: seleccionaInstitucion: " + this.tipoConfiguracion);
		if(this.selectedItemConfiguracion != null && this.selectedItemConfiguracion.contains(",")){
	    	try{
	    		String[] selectedItemCustodioSplit = selectedItemConfiguracion.split(",");
	    		if(StringUtils.isNotEmpty(this.tipoConfiguracion) && this.tipoConfiguracion.equals(tipoConfiguracionCustodio)){
		        	this.settDisciplineRegimeVO.setIdCuentaBoveda(Long.valueOf(selectedItemCustodioSplit[0]));
		        	this.settDisciplineRegimeVO.setCuenta(Long.valueOf(selectedItemCustodioSplit[1]));
		        	this.settDisciplineRegimeVO.setDetalleCustodio(selectedItemCustodioSplit[2]);	    			
	    		} /*else if(StringUtils.isNotEmpty(this.tipoConfiguracion) && this.tipoConfiguracion.equals(tipoConfiguracionInstitucion)){
		        	this.settDisciplineRegimeVO.setIdInstitucion(Long.valueOf(selectedItemCustodioSplit[0]));
		        	this.settDisciplineRegimeVO.setFolioInstitucion(selectedItemCustodioSplit[1]);
		        	this.settDisciplineRegimeVO.setNombreCortoInstitucion(selectedItemCustodioSplit[2]);	
	    		} */

	    	} catch (Exception ex){
	        	FacesContext.getCurrentInstance().addMessage(null,
	                    new FacesMessage(FacesMessage.SEVERITY_INFO, ex.getMessage(), ex.getMessage()));
	    	} 
		}
		
		return null;
	}
	
	private void enviaSolicitudAutorizacion(SettlementDisciplineRegimeVO settDisciplineRegimeVONuevo, SettlementDisciplineRegimeVO settDisciplineRegimeVOAnterior) throws BusinessException {
		log.info("SettlementDisciplineRegimeController :: enviaSolicitudAutorizacion");
		boolean esActualizacion = settDisciplineRegimeVOAnterior != null;
		SettlementDisciplineRegime settlementDR = esActualizacion ? createSettlementDisciplineRegimeVoToModelWithChanges(settDisciplineRegimeVONuevo, settDisciplineRegimeVOAnterior) : transformSettlementDisciplineRegimeVoToModel(settDisciplineRegimeVONuevo, Boolean.FALSE); 
		final SolicitudAutorizacionDTO autorizacionDTO = new SolicitudAutorizacionDTO();
        autorizacionDTO.setClaveUsuario(getCveUsuarioSesion());
        autorizacionDTO.setDescripcion(esActualizacion ? getDescripcionActualizacionSolicitud(settDisciplineRegimeVONuevo, settDisciplineRegimeVOAnterior) : getDescripcionAltaSolicitud(settDisciplineRegimeVONuevo));
        autorizacionDTO.setValorNuevo(JsonUtil.serializeAsJson(settlementDR));
        autorizacionDTO.setValorAnterior(esActualizacion ? JsonUtil.serializeAsJson(transformSettlementDisciplineRegimeVoToModel(settDisciplineRegimeVOAnterior, Boolean.TRUE)) : null);
        
        String claveWorkflow = esActualizacion ? Constantes.CLAVE_WORKFLOW_EDICION_CUSTODIO_CSDR : Constantes.CLAVE_WORKFLOW_ALTA_CUSTODIO_CSDR;
        log.info("SettlementDisciplineRegimeController :: claveWorkflow: " + claveWorkflow);
        solicitudAutorizacionService.nuevaSolicitud(claveWorkflow, autorizacionDTO, getTicketSesion());
	}
	
	private void enviaSolicitudEliminarAutorizacion(SettlementDisciplineRegimeVO settDisciplineRegimeVO) throws BusinessException {
		log.info("SettlementDisciplineRegimeController :: enviaSolicitudEliminarAutorizacion");
		SettlementDisciplineRegime settlementDR = createSettlementDisciplineRegimeVoToDelete(settDisciplineRegimeVO); 
		final SolicitudAutorizacionDTO autorizacionDTO = new SolicitudAutorizacionDTO();
        autorizacionDTO.setClaveUsuario(getCveUsuarioSesion());
        autorizacionDTO.setDescripcion(getDescripcionEliminarSolicitud(settDisciplineRegimeVO));
        autorizacionDTO.setValorNuevo(null);
        autorizacionDTO.setValorAnterior(JsonUtil.serializeAsJson(settlementDR));
        solicitudAutorizacionService.nuevaSolicitud(Constantes.CLAVE_WORKFLOW_ELIMINAR_CUSTODIO_CSDR, autorizacionDTO, getTicketSesion());
	}
	
	public String getDescripcionAltaSolicitud(SettlementDisciplineRegimeVO settDisciplineRegimeVO) {
		log.debug("SettlementDisciplineRegimeController :: getDescripcionAltaSolicitud");
		StringBuilder descripcion = new StringBuilder();
		descripcion.append("Alta de Configuración para CSDR: ").append(settDisciplineRegimeVO.getDescripcionCamposLlave());
		return descripcion.toString();
	}
	
	public String getDescripcionActualizacionSolicitud(SettlementDisciplineRegimeVO settDisciplineRegimeVONuevo, SettlementDisciplineRegimeVO settDisciplineRegimeVOAnterior) {
		log.debug("SettlementDisciplineRegimeController :: getDescripcionActualizacionSolicitud");
		StringBuilder descripcion = new StringBuilder();
		if(settDisciplineRegimeVONuevo.getFolioInstitucion() != null){
			settDisciplineRegimeVOAnterior.setFolioInstitucion(settDisciplineRegimeVONuevo.getFolioInstitucion());
			settDisciplineRegimeVOAnterior.setNombreCortoInstitucion(settDisciplineRegimeVONuevo.getNombreCortoInstitucion());
		}
		descripcion.append("Cambios en: ").append(settDisciplineRegimeVOAnterior.getDescripcionCamposLlave());
		descripcion.append(" - Nuevos valores: ").append(settDisciplineRegimeVONuevo.getDescripcionCamposModificar(settDisciplineRegimeVOAnterior));
		return descripcion.toString();
	}
	
	public String getDescripcionEliminarSolicitud(SettlementDisciplineRegimeVO settDisciplineRegimeVO) {
		log.debug("SettlementDisciplineRegimeController :: getDescripcionEliminarSolicitud");
		StringBuilder descripcion = new StringBuilder();
		descripcion.append("Eliminar configuración para CSDR: ").append(settDisciplineRegimeVO.getDescripcionEliminar());
		return descripcion.toString();
	}

	/**
	 * @param settlementDisciplineRegimeService the settlementDisciplineRegimeService to set
	 */
	public void setSettlementDisciplineRegimeService(SettlementDisciplineRegimeService settlementDisciplineRegimeService) {
		this.settlementDisciplineRegimeService = settlementDisciplineRegimeService;
	}

	/**
	 * @return the listSettlementDisciplineRegime
	 */
	public List<SettlementDisciplineRegimeVO> getListSettlementDisciplineRegime() {
		return listSettlementDisciplineRegime;
	}

	/**
	 * @param listSettlementDisciplineRegime the listSettlementDisciplineRegime to set
	 */
	public void setListSettlementDisciplineRegime(List<SettlementDisciplineRegimeVO> listSettlementDisciplineRegime) {
		this.listSettlementDisciplineRegime = listSettlementDisciplineRegime;
	}

	/**
	 * @return the edicionHabilitada
	 */
	public boolean isEdicionHabilitada() {
		return edicionHabilitada;
	}

	/**
	 * @param edicionHabilitada the edicionHabilitada to set
	 */
	public void setEdicionHabilitada(boolean edicionHabilitada) {
		this.edicionHabilitada = edicionHabilitada;
	}

	/**
	 * @return the settDisciplineRegimeVO
	 */
	public SettlementDisciplineRegimeVO getSettDisciplineRegimeVO() {
		return settDisciplineRegimeVO;
	}

	/**
	 * @param settDisciplineRegimeVO the settDisciplineRegimeVO to set
	 */
	public void setSettDisciplineRegimeVO(SettlementDisciplineRegimeVO settDisciplineRegimeVO) {
		this.settDisciplineRegimeVO = settDisciplineRegimeVO;
	}

	/**
	 * @return the selectedItemConfiguracion
	 */
	public String getSelectedItemConfiguracion() {
		return selectedItemConfiguracion;
	}

	/**
	 * @param selectedItemConfiguracion the selectedItemConfiguracion to set
	 */
	public void setSelectedItemConfiguracion(String selectedItemConfiguracion) {
		this.selectedItemConfiguracion = selectedItemConfiguracion;
	}

	/**
	 * @param solicitudAutorizacionService the solicitudAutorizacionService to set
	 */
	public void setSolicitudAutorizacionService(SolicitudAutorizacionService solicitudAutorizacionService) {
		this.solicitudAutorizacionService = solicitudAutorizacionService;
	}

	/**
	 * @return the tipoConfiguracion
	 */
	public String getTipoConfiguracion() {
		return tipoConfiguracion;
	}

	/**
	 * @param tipoConfiguracion the tipoConfiguracion to set
	 */
	public void setTipoConfiguracion(String tipoConfiguracion) {
		this.tipoConfiguracion = tipoConfiguracion;
	}

	/**
	 * @return the listConfiguracionInstitucion
	 */
	public List<SettlementDisciplineRegimeVO> getListConfiguracionInstitucion() {
		return listConfiguracionInstitucion;
	}

	/**
	 * @param listConfiguracionInstitucion the listConfiguracionInstitucion to set
	 */
	public void setListConfiguracionInstitucion(List<SettlementDisciplineRegimeVO> listConfiguracionInstitucion) {
		this.listConfiguracionInstitucion = listConfiguracionInstitucion;
	}

}