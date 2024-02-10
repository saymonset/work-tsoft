package com.indeval.portalinternacional.presentation.controller.multicarga;

import static com.indeval.portalinternacional.middleware.services.util.ExcelUtils.convertInputStreamToHSSFWorkbook;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.myfaces.custom.fileupload.UploadedFile;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.util.ConsultaCatalogoService;
import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO;
import com.indeval.portaldali.persistence.modelo.Institucion;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.MulticargaInternacionalService;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.multicarga.exception.MulticargaException;
import com.indeval.portalinternacional.middleware.servicios.multicarga.excell.vo.MulticargaResumenExcelVO;
import com.indeval.portalinternacional.middleware.servicios.multicarga.excell.vo.MulticargaVO;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;

@SuppressWarnings("unchecked")
public class MulticargaMultiempresaController extends ControllerBase{
	
	  /** Log de clase. */
	private static final Logger log = LoggerFactory.getLogger(MulticargaMultiempresaController.class);
    
    /** Archivo subido por el usuario */
    private UploadedFile archivo;
    
    /** Indica si pinta en pantalla el link para mostrar errores */
    private boolean showLink = false;
    
    private String claveInstitucion;
    
    private String nombreInstitucion;
    
    private Long idInstitucionDestino;
    
    /** Indica la bandera de Inicio */
    private boolean banderaInicio = false;
    
    /**Indica si el proceso termino correctamente*/
    private boolean consultaEjecutada = false;
           
    /** VO con el resumen de la carga*/
    private MulticargaResumenExcelVO multicargaResumenExcelVO;

    /**Nombre del Usuario*/
    private String nombreUsuarioResponsable;
    
    /** Agente Firmado */
    private AgenteVO agenteVO;
    
    /** Servicio Multicarga Internacional*/
    private MulticargaInternacionalService multicargaInternacionalService;
    
    /** Servicio para obtener la institucion */
	private ConsultaCatalogoService consultaCatService;
	
             
    
    /**
     * Metodo de inicio
     * @return null
     */
    public String getInit() {
        if (!this.banderaInicio) {                                                                                              
        	this.banderaInicio = true;
        	this.agenteVO = getAgenteFirmado();  
        	this.nombreUsuarioResponsable = getNombreUsuarioSesion();
        	this.archivo = null;
        	this.claveInstitucion = null;
        	this.nombreInstitucion = null;
        	this.idInstitucionDestino = null;
        }
        if(StringUtils.isNotBlank(this.claveInstitucion) && claveInstitucion.length() == Constantes.LONGITUD_INSTITUCION){
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
        
        
    /**
     * Atiende la petici&oacute;n del usuario para iniciar el proceso de carga de archivo de operaciones.
     * @return null
     */
    public String uploadFile() {  
    	showLink = false;
    	consultaEjecutada = false;
    	multicargaResumenExcelVO = null;
    	try{
    		validaFormatoArchivo();    	
    		MulticargaVO multicargaVO = generaVO();
    	    	
    		multicargaResumenExcelVO = multicargaInternacionalService.guardaRegistrosMulticargaMultiempresa(multicargaVO);
    		consultaEjecutada = true;
    		String mensaje = "Proceso terminado";
    		addMessage(mensaje);           
    		if(multicargaResumenExcelVO.getListaRegistrosError()!= null && multicargaResumenExcelVO.getListaRegistrosError().size()>0){
    			log.debug("***La carga tiene registros con error***");
    			showLink = true;
    		}    	    	
    	}catch(MulticargaException mex){
    		addErrorMessage(mex.getMessage());    	        	
        	mex.printStackTrace();
    	}catch(BusinessException bex){
    		addErrorMessage(bex.getMessage());    	
    		bex.printStackTrace();
    	}catch(Exception e){    		
    		e.printStackTrace();
    		addErrorMessage("Error al realizar la carga");    		
    	}    	    	    	
    	return null;  
    }
    
    /** Metodo que valida el formato del archivo*/
    private void validaFormatoArchivo() throws MulticargaException{
    	log.debug("**Validando Formato Archivo**");
    	String mensajeError = null;  
    	
    	if(StringUtils.isBlank(this.claveInstitucion) || this.claveInstitucion.length() != 5){
    		mensajeError = "Valor de Institución nulo o incorrecto"; 
    		throw new MulticargaException(mensajeError); 
    	}else if(StringUtils.isNotBlank(this.claveInstitucion)){
    		Institucion institucion = consultaCatService.findInstitucionByClaveFolio(this.claveInstitucion);
        	if(institucion == null){
        		mensajeError = "No existe la Institución"; 
        		throw new MulticargaException(mensajeError); 
        	}else{ 
        		this.idInstitucionDestino = institucion.getIdInstitucion(); 
        	}
    	}    
    	
    	if (archivo == null) {
    		mensajeError = "El archivo de operaciones es un dato requerido";    			                
    		throw new MulticargaException(mensajeError); 
    	}
    	String nombreArchivo = StringUtils.upperCase(StringUtils.trimToEmpty(archivo.getName()));
              
        if (nombreArchivo.endsWith("XLSX")) {
        	mensajeError = "Cambiar formato, guardar con extensin .xls";
            throw new MulticargaException(mensajeError); 
        }            
        if (!nombreArchivo.endsWith("XLS")) {
        	mensajeError = "Formato incorrecto, sólo se acepta formato .xls";
            throw new MulticargaException(mensajeError); 
        }
        
        if(StringUtils.isBlank(agenteVO.getId()) || StringUtils.isBlank(agenteVO.getFolio())){        
        	mensajeError = "Se necesita un usuario con sesión iniciada";
            throw new MulticargaException(mensajeError); 
        } 
        
        if(StringUtils.isBlank(nombreUsuarioResponsable)){        
        	mensajeError = "No tiene asignado un nombre el Usuario";
            throw new MulticargaException(mensajeError); 
        } 
        
        if(multicargaInternacionalService.nombreArchivoValido(nombreArchivo)){
        	mensajeError = "Nombre del archivo repetido";
            throw new MulticargaException(mensajeError);
        }
    }
    
    /** Metodo que genera el MulticargaVO tomando los datos ingresados por el usuario */
    private MulticargaVO generaVO() throws MulticargaException{
    	log.debug("**Generando el MulticargaVO**");
    	MulticargaVO multicargaVO = null;
    	HSSFWorkbook Wb = null;
    	try{
    		Wb = convertInputStreamToHSSFWorkbook(this.archivo.getInputStream());
    		multicargaVO = new MulticargaVO();
    		multicargaVO.setArchivo(Wb);
    		multicargaVO.setEstadoRegistro(Constantes.STATUS_MULTICARGA_PENDIENTE_DE_AUTORIZAR);    		
    		multicargaVO.setNombreArchivo(this.archivo.getName());    		
    		multicargaVO.setUsuarioResponsable(this.nombreUsuarioResponsable);
    		multicargaVO.setTipoOperacion(Constantes.OPERACION_MULTICARGA_MULTIEMPRESA);
    		multicargaVO.setIdInstitucionDestino(this.idInstitucionDestino);
    		multicargaVO.setClaveInstitucionDestino(this.claveInstitucion);
    	}catch(IOException ioex){    		
    		throw new MulticargaException("Error al leer el archivo");    	
    	}    	
    	return multicargaVO;
    }
        
    /**geters and seters*/
	
	public boolean isShowLink() {
		return showLink;
	}

	public void setShowLink(boolean showLink) {
		this.showLink = showLink;
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
	 * @return the idInstitucionDestino
	 */
	public Long getIdInstitucionDestino() {
		return idInstitucionDestino;
	}


	/**
	 * @param idInstitucionDestino the idInstitucionDestino to set
	 */
	public void setIdInstitucionDestino(Long idInstitucionDestino) {
		this.idInstitucionDestino = idInstitucionDestino;
	}


	public boolean isBanderaInicio() {
		return banderaInicio;
	}

	public void setBanderaInicio(boolean banderaInicio) {
		this.banderaInicio = banderaInicio;
	}

	public MulticargaResumenExcelVO getMulticargaResumenExcelVO() {
		return multicargaResumenExcelVO;
	}

	public void setMulticargaResumenExcelVO(
			MulticargaResumenExcelVO multicargaResumenExcelVO) {
		this.multicargaResumenExcelVO = multicargaResumenExcelVO;
	}
	
	public UploadedFile getArchivo() {
		return archivo;
	}

	public void setArchivo(UploadedFile archivo) {
		this.archivo = archivo;
	}
	
	public boolean isConsultaEjecutada() {
		return consultaEjecutada;
	}

	public String getNombreUsuarioResponsable() {
		return nombreUsuarioResponsable;
	}


	public void setNombreUsuarioResponsable(String nombreUsuarioResponsable) {
		this.nombreUsuarioResponsable = nombreUsuarioResponsable;
	}


	public void setConsultaEjecutada(boolean consultaEjecutada) {
		this.consultaEjecutada = consultaEjecutada;
	}		

	///inyecci&oacute;n del servicio
	public void setMulticargaInternacionalService(
			MulticargaInternacionalService multicargaInternacionalService) {
		this.multicargaInternacionalService = multicargaInternacionalService;
	}


	/**
	 * @param consultaCatService the consultaCatService to set
	 */
	public void setConsultaCatService(ConsultaCatalogoService consultaCatService) {
		this.consultaCatService = consultaCatService;
	}

}

