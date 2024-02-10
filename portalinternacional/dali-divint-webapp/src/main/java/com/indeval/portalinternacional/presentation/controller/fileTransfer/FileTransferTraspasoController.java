/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * Agosto 28, 2008
 */
package com.indeval.portalinternacional.presentation.controller.fileTransfer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.myfaces.custom.fileupload.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bursatec.seguridad.presentation.constants.SeguridadConstants;
import com.bursatec.seguridad.vo.InstitucionVO;
import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.common.util.CifradorDescifradorBlowfish;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.FileTransferService;
import com.indeval.portalinternacional.middleware.services.util.FileUploadService;
import com.indeval.portalinternacional.middleware.services.util.vo.ProcessInfoVO;
import com.indeval.portalinternacional.middleware.servicios.vo.CampoArchivoVO;
import com.indeval.portalinternacional.middleware.servicios.vo.FileTransferVO;
import com.indeval.portalinternacional.middleware.servicios.vo.RegistroProcesadoVO;
import com.indeval.portalinternacional.middleware.servicios.vo.ResumenVO;
import com.indeval.portalinternacional.middleware.servicios.vo.TotalesProcesoVO;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;
import com.indeval.portalinternacional.presentation.controller.fileTransfer.encoladores.IEncoladorProtocoloFinanciero;

/**
 * Controller para el envio de archivos
 * 
 * @author Esteban Herrera
 * @version 1.0
 *
 */
@SuppressWarnings("unchecked")
public class FileTransferTraspasoController extends ControllerBase {

	/**
	 * Indica si existe un proceso iniciad por el controller
	 */
	private boolean procesoIniciado = false;
	
	/**
	 * Indica si debe mostrar en la pantalla el boton de detener proceso
	 */
	private boolean mostrarBotonDetenerProceso = false;
	
	/**
	 * Indica si se debe detener el poll en la siguiente vuelta
	 */
	private boolean detenerSiguienteVuelta = false;
	
	/** Bandera de Poll */
	private boolean polling = false;
	
	/** Resumen: de la fecha de Carga del documento */
	private String fechaCarga;
	
	/** Resumen: de la fecha de Carga del documento */
	private String horaCarga;
	
	/** Registros env&iacute;ados al protocolo */
	private String registrosEnviados;
	
	/** Registros cargados */
	private String registrosCargados;
	
	/** Registros con Error */
	private String registrosConError;
	
	/** Total de Registros */
	private String totalRegistros;
	
	/** Usuario responsable */
	private String usuarioResponsable;
	
	/** Almacena informaci&oacute;n de los registros a cargar, con error y la paginaci&oacute;n */
	private TotalesProcesoVO resumenPrevio;
	
	/**
	 * Progreso de avance
	 */
	private String progreso = null;
	
	/**
	 * Nombre del proceso arrancado
	 */
	private String process;
	
	/**
	 * Informaci&oacute;n del resumen de carga
	 */
	private ResumenVO resumen;
	
	/** Indica si pinta en pantalla el link para mostrar errores */
	private Boolean showLink = Boolean.TRUE;
	
	/**
	 * Paginador de registros de resumen de errores
	 */
	private PaginaVO pagina;
	
	/**
	 * Archivo subido por el usuario
	 */
	private UploadedFile archivo;
	
	/** ID de la pagina principal */
	private String navHome;
    
    /**
     * Servicio para el acceso a file transfer
     */
	private FileTransferService fileTransferService; 
	
	/**
	 * Servicio para el bloqueo y cordinaci&oacute;n del servicio
	 */
	private FileUploadService fileUploadService = null;
	
	/**
	 * Encolador del protocolo para el controller
	 */
	private IEncoladorProtocoloFinanciero encolador;
	
	/**
	 * Informacion del proceso de carga actual
	 */
	private  ProcessInfoVO processInfo = new ProcessInfoVO();
	
	/** ID de la instituci&oacute;n */
	private String id;
	
	/** Folio de la instituci&oacute;n */
	private String folio;
	
	/**
	 * Indica si se espera una confirmaci&oacute;n del usuario 
	 */
	private boolean esperaConfirmacion = false;
	
	/**
	 * Conjunto de isos Firmados
	 */
	private List<String> isosFirmados = null;
	
	/**
	 * Conjunto de isos sin firmar
	 */
	private List<String> isosSinFirmar = null;
	
	/**
	 * Conjunto de hash de isos
	 */
	private List<String> hashIso = null;
	
	
	/**
	 * Total de operaciones a firmar
	 */
	private int totalOperaciones = 0;
	
	/** Decifrador BlowFish para la firma electronica */
	private CifradorDescifradorBlowfish cdb = new CifradorDescifradorBlowfish();
	
	/**
	 * Mapa de par&aacute;metros de request
	 */
	private Map<String,String[]> params = null;
	
	/**
	 * Encabezado de los registros en el 
	 */
	private List<CampoArchivoVO> encabezado = null;
	
	/** Representa los datos de la instituci&oacute;n actual */
	private InstitucionVO institucionVO = null;
	
	/** Mensaje de error a mostrar del m&eacute;todo del thread */
	private String mensajeError;
	
	/** Lista con los VO con los datos de los registros del archivo */
	//private List<TraspasoLibrePagoVO> listaObjetosDatosOperacion;
	private List listaObjetosDatosOperacion;
	
	/** Indica si se presenta el applet y la logica para firmar operaciones */
	private boolean usuarioDebeFirmarOperacion;
	
	/** Log de clase. */
	private static final Logger log = LoggerFactory.getLogger(FileTransferTraspasoController.class);
	
	private ExecutorService executor = null;
	
	private FutureTask<String> futureTask = null;
	
	String ticket =null;
	
	/** Lista con los tipos de archivos aceptados (content-type) por este file transfer */
	public static final List<String> listaArchivosAceptados= new ArrayList<String>();
	
	static {
		listaArchivosAceptados.add("text/plain");
	}
	
	
	////////////////////////////////////////////Seccion de Metodos ////////////////////////////////////////////
	
	/**
     * Obtiene la instituci&oacute;n actual del participante.
     * 
     * @return VO con los datos de la instituci&oacute;n.
     */
    public InstitucionVO getInstitucionActual() {
    	if (institucionVO == null && getAgenteFirmado() != null && getAgenteFirmado().getId() != null && getAgenteFirmado().getFolio() != null) {
    		institucionVO = new InstitucionVO();
    		
    		institucionVO.setClave(getAgenteFirmado().getId());
    		institucionVO.setFolio(getAgenteFirmado().getFolio());
    	}
    	
    	return institucionVO;
    }
    
	/**
	 * Consulta los detalles de los registros cargados y validados para la confirmaci&oacute;n del usuario
	 * @return null;
	 */
	public String getShowDetalleErrores() {
        try {
            AgenteVO agenteFirmado = getAgenteFirmado();
            FileTransferVO fileTransferVO = new FileTransferVO();
            fileTransferVO.setAgenteFirmado(agenteFirmado);
            fileTransferVO.setPaginaVO(paginaVO);
            fileTransferVO.setTipoProceso(process);
            fileTransferVO.setSoloErrores(true);
            
	        resumenPrevio = fileTransferService.muestraInformacion(fileTransferVO);
	        
	        if(resumenPrevio != null) {
		        resumenPrevio = agregaMensajeSinError(resumenPrevio);
		        pagina = resumenPrevio.getPaginaVO();
	        }
	        
        }catch (Exception e) {
        	log.error("Ocurrio un error:",e);
            release();
            throw new BusinessException(e.getMessage());
        }
        return null;
	}
	
	/**
	 * 
	 * @param p
	 * @return
	 */
	public TotalesProcesoVO agregaMensajeSinError(TotalesProcesoVO p){
    	PaginaVO pagina = p.getPaginaVO();
    	
    	if(pagina != null) {
	    	List l = pagina.getRegistros();

	    	if(l != null) {
		    	for(int i=0; i < l.size(); i++){
		    		RegistroProcesadoVO rp = (RegistroProcesadoVO) l.get(i);
		    		if(rp.getMensajesError() == null || rp.getMensajesError().length == 0){
		    			rp.setMensajesError(new String[]{"REGISTRO VALIDO"});
		    		}
		    	}
	    	}
    	}
    	return p;
    }
	
	/**
	 * Recupera la informaci&oacute;n del proceso que est&aacute; actualmente corriendo para mostrar la pantalla por primera vez.
	 * @return null
	 */
    public String getInit() {
    	setRegistrosXPag(5);
        usuarioDebeFirmarOperacion = isUsuarioConFacultadFirmar();
    	setId(getAgenteFirmado().getId());
        setFolio(getAgenteFirmado().getFolio());
    	getDetalle();
    	if(processInfo != null && processInfo.getStatus() != null){
    		if( processInfo.getStatus().equals(ProcessInfoVO.CARGANDO) ||
            		processInfo.getStatus().equals(ProcessInfoVO.GUARDANDO) ||
            		processInfo.getStatus().equals(ProcessInfoVO.VALIDANDO) ||
            		processInfo.getStatus().equals(ProcessInfoVO.PROCESANDO) ){
    			polling = true;
    			procesoIniciado  = true;
    			mostrarBotonDetenerProceso = !processInfo.getStatus().equals(ProcessInfoVO.PROCESANDO);
        	}else if(processInfo.getStatus().equals(ProcessInfoVO.CONFIRMACION)){
    			esperaConfirmacion = true;
    			polling = false;
    			procesoIniciado = false;
    			mostrarBotonDetenerProceso = false;
    		}
    	}
    	
    	if(processInfo == null){
    		polling = false;
    		procesoIniciado = false;
    		esperaConfirmacion = false;
    		mostrarBotonDetenerProceso = false;
    	}
    	
    	ticket=(String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(SeguridadConstants.TICKET_SESION);
    	
        return null;
    }
	
    /**
     * Verifica si existe un proceso corriendo
     */
    public Boolean getIsProcessRunning() {
    	processInfo.setUsuario(getId() + getFolio());
        processInfo.setIdProceso(getId() + getFolio()+process);
        return fileUploadService.isProcessRunning(processInfo);
    }

    /**
     * Verifica si el proceso que est&aacute; corriendo corresponde al usuario actual
     * @return
     */
    public Boolean getIsThisMyProcess() {
    	if(processInfo == null){
    		processInfo = new ProcessInfoVO();
    	}
    	processInfo.setUsuario(getId() + getFolio());
        processInfo.setIdProceso(getId() + getFolio()+process);
        processInfo =  fileUploadService.getProcessInfo(processInfo);
        if( processInfo == null ) {
            return Boolean.FALSE;
        }
        
        return new Boolean( ( processInfo.getUsuario().equals(getId() + getFolio()) ) );
    }
    
    /**
     * Cancela el proceso de carga del archivo.
     */
    public void abortProcess(ActionEvent ev) {
    	esperaConfirmacion = false;
    	polling = false;
    	detenerSiguienteVuelta = true;
    	abortProcess();
    }
    
    /**
     * Cancela el proceso de carga del archivo.
     * @return Constante que indica que regrese a la pagina principal (si aplica a la hora de llamar el metodo)
     */
    public String abortProcess() {
    	try {    	
	    	//if(processInfo.getStatus().equals(ProcessInfoVO.CARGANDO) || processInfo.getStatus().equals(ProcessInfoVO.VALIDANDO)) {
    		if(futureTask != null && futureTask.isDone()) {
    			futureTask.cancel(true);
    		}
        	cancelar(null);
            mensajeError = "Proceso de carga detenido por el usuario";
            if(executor != null) {
        		executor.shutdownNow();
        	}
        	futureTask = null;
        	executor = null;
	        /*} else if(processInfo.getStatus().equals(ProcessInfoVO.PROCESANDO)) {
	    		futureTask.cancel(true);
	    		release();
	    		mensajeError = "Proceso de detenido por el usuario";
	            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,mensajeError,mensajeError));
	        	executor.shutdownNow();
	        	futureTask = null;
	        	executor = null;
	    	} else if(processInfo.getStatus().equals(ProcessInfoVO.CONFIRMACION)) {
	    		getResumenPrevioCarga();
	    		
	    		if(futureTask != null && futureTask.isDone() && executor != null) {
	    			executor.shutdownNow();
		        	futureTask = null;
		        	executor = null;
	    		}
	    	}*/
    	} catch(Exception e) {
    		e.printStackTrace();
    		log.error("Ocurrio un error:",e);
    		release();
    	}
    	
    	return navHome;
    }

	/**
	 * Quita el lock de la base y resetea las banderas
	 */
	private void release() {
		if(processInfo == null){
    		processInfo = new ProcessInfoVO();
    	}
		processInfo.setUsuario(getId() + getFolio());
        processInfo.setIdProceso(getId() + getFolio()+process);
    	fileUploadService.releaseLock(processInfo);
    	esperaConfirmacion = false;
    	polling = false;
    	detenerSiguienteVuelta = true;
	}

    /**
     * Recupera el usuario que est&aacute; actualmente cargando un archivo
     * @return
     */
    public String getUserLoadingFile() {
    	processInfo.setUsuario(getId() + getFolio());
        processInfo.setIdProceso(getId() + getFolio()+process);
        processInfo = fileUploadService.getProcessInfo(processInfo);
        if( processInfo == null ) {
            return "";
        }
        return processInfo.getUsuario();
    }

    /**
     * Recupera el detalle de la informaci&oacute;n que se esta cargando actualmente o que espera confirmaci&oacute;n.
     * @return
     */
    public String getDetalle() {
        try {
        	if(processInfo == null){
        		processInfo = new ProcessInfoVO();
        	}
        	processInfo.setUsuario(getId() + getFolio());
            processInfo.setIdProceso(getId() + getFolio()+process);
            AgenteVO agenteFirmado = new AgenteVO();
            agenteFirmado.setId(getId());
            agenteFirmado.setFolio(getFolio());
            agenteFirmado.setFirmado(true);
            FileTransferVO fileTransferVO = new FileTransferVO();
            fileTransferVO.setAgenteFirmado(agenteFirmado);
            fileTransferVO.setTipoProceso(process);
            
            resumen = fileTransferService.obtieneResumen(fileTransferVO);
            showLink = new Boolean(!resumen.getTotalError().equals(new Integer(0)));
            processInfo =  fileUploadService.getProcessInfo(processInfo);
            if( processInfo != null ){
            	if( processInfo.getStatus().equals(ProcessInfoVO.CARGANDO) ||
            		processInfo.getStatus().equals(ProcessInfoVO.GUARDANDO) ||
            		processInfo.getStatus().equals(ProcessInfoVO.VALIDANDO) ||            		
            		processInfo.getStatus().equals(ProcessInfoVO.PROCESANDO) ){
            		return navHome;
            	}else{
            		return getResumenPrevioCarga();
            	}
            }
        } catch(BusinessException e) {
        	log.error("Ocurrio un error:", e);
            release();
        }
        
        return navHome;
    }
    
    /**
     * M&eacute;todo base para poder ejecutar la consulta
     */
    public String ejecutarConsulta() {
    	return getResumenPrevioCarga();
    }
    
    /**
     * M&eacute;todo llamado al cambiar el numero de registros a mostrar por pagina
     * @param e
     */
    public void actualizarPaginacionRegistro(ActionEvent e) {
    	paginaVO.setOffset(0);
    	getResumenPrevioCarga();
    }
	
    /**
     * Consulta el resumen de la validaciones aplicadas y el resultado de estas a la &uacute;ltima carga del archivo del usuario
     * @return
     */
	public String getResumenPrevioCarga(){
    	try {
    		AgenteVO agenteFirmado = getAgenteFirmado();
            FileTransferVO fileTransferVO = new FileTransferVO();
            fileTransferVO.setAgenteFirmado(agenteFirmado);
            fileTransferVO.setPaginaVO(paginaVO);
            fileTransferVO.setTipoProceso(process);
            fileTransferVO.setSoloErrores(false);
            
	        resumenPrevio = fileTransferService.muestraInformacion(fileTransferVO);
	        
	        if(resumenPrevio != null) {
		        resumenPrevio = agregaMensajeSinError(resumenPrevio);
		        pagina = resumenPrevio.getPaginaVO();
		        
		        if(pagina != null && pagina.getRegistros().size()>0){
		        	RegistroProcesadoVO primerRegistro = (RegistroProcesadoVO)pagina.getRegistros().get(0);
		        	encabezado = primerRegistro.getDatos();
		        }
	        }
	        
	        return navHome;
    	}catch (Exception e) {
    		log.error("Error", e);
            release();
            throw new  BusinessException(e.getMessage());
    	}
    }
	
    /**
     * Cancela el proceso de validaci&oacute;n y procesamiento de los registros cargados
     * @param ev
     */
    public void cancelar(ActionEvent ev){
    	try {
    		AgenteVO agenteFirmado = getAgenteFirmado();
            FileTransferVO fileTransferVO = new FileTransferVO();
            fileTransferVO.setAgenteFirmado(agenteFirmado);
            fileTransferVO.setTipoProceso(process);
            
    		fileTransferService.cancelaProceso(fileTransferVO);
    		getDetalle();
    		paginaVO = new PaginaVO();
    		
    	} catch (BusinessException e) {
    		log.error("Ocurrio un error:",e);
            
		} finally {
			release();
    		isosFirmados = null;
    		isosSinFirmar = null;
		}
    	
    }

    /**
     * Inicia el proceso de validaci&oacute;n y env&iacute;o de operaciones  de los datos cargados del archivo.
     * @param e
     */
	public void procesar(ActionEvent e) {
		try {
    		//Si el usuario debe firmar operaciones, valida la vigencia del certificado
			//18-Agosto-2014 Pablo Balderas
    		if(isUsuarioConFacultadFirmar()) {
    			validarVigenciaCertificado();
    		}
	    	mensajeError = null;
	    	processInfo.setUsuario(getId() + getFolio());
	    	processInfo.setIdProceso(getId() + getFolio()+process);
	    	fileUploadService.getProcessInfo(processInfo);
	    	processInfo.setStatus(ProcessInfoVO.PROCESANDO);
	        processInfo.setPorcentajeTerminado(new BigDecimal( 0 ));
	        fileUploadService.updateProcessInfo(processInfo);
	        params = ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getParameterMap();
	        getDetalle();
	        polling = true;
	        procesoIniciado = true;
	        detenerSiguienteVuelta = false;
			esperaConfirmacion = false;
			executor = Executors.newSingleThreadExecutor();
			futureTask = new FutureTask<String>(new Callable<String>() {
					public String call() {
			        	run();
			        	return null;
			       }});
			executor.execute(futureTask);
		}
		catch(BusinessException businessException) {
	        FacesContext.getCurrentInstance().addMessage(
	        	null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
	        	businessException.getMessage(), businessException.getMessage()));
		}
		catch (Exception ex) {
		    ex.printStackTrace();
		    throw new BusinessException(ex.getMessage());
		}
		

    }
    
    /**
     * Atiende la petici&oacute;n del usuario para iniciar el proceso de carga de archivo de operaciones.
     * @param e
     */
    public void uploadFile(ActionEvent e) {
    	boolean errors = false;
    	mensajeError = null;
        
        try {
            setId(getAgenteFirmado().getId());
            setFolio(getAgenteFirmado().getFolio());
      
            if( archivo == null ) {
            	mensajeError = "El archivo de operaciones es un dato requerido";
            	FacesContext.getCurrentInstance().addMessage(null,
                         new FacesMessage(FacesMessage.SEVERITY_WARN, mensajeError,mensajeError));
                errors = true;
            }
        	else if(!listaArchivosAceptados.contains(archivo.getContentType())) {
        	mensajeError = "El archivo de operaciones debe ser de tipo texto plano";
        	FacesContext.getCurrentInstance().addMessage(null,
                     new FacesMessage(FacesMessage.SEVERITY_WARN, mensajeError,mensajeError));
            errors = true;
        	}
            if(!errors && (getId() == null || getId().equals("") ||
                    getFolio() == null || getFolio().equals(""))) {
            	mensajeError = "Se necesita un usuario logeado";
            	agregarInfoMensaje(mensajeError);
                errors = true;
            }
            if( errors ) {
                return;
            }
      
            if(processInfo == null){
            	processInfo = new ProcessInfoVO();
            }
            processInfo.setUsuario(getId() + getFolio());
            processInfo.setIdProceso(getId() + getFolio()+process);
            processInfo.setAbort('F');
            processInfo.setPorcentajeTerminado(new BigDecimal(0));
            processInfo.setStatus(ProcessInfoVO.CARGANDO);
            processInfo.setUsuario(getId()+getFolio());
            Boolean candado = fileUploadService.obtainLock(processInfo);

            if(!errors && !candado.booleanValue()) {
            	mensajeError = "Ya hay un proceso de carga iniciado para este tipo de papel";
            	System.out.println("Ya hay un proceso de carga iniciado para este tipo de papel");
            	agregarInfoMensaje(mensajeError);
                
                return;
            }
            
            this.setPolling(true);
            esperaConfirmacion = false;
            procesoIniciado = true;
            detenerSiguienteVuelta = false;
            isosFirmados = null;
            isosSinFirmar = null;
            mostrarBotonDetenerProceso = true;
            
            System.out.println("carga informacion en UPLOAD");
            getDetalle();

            executor = Executors.newSingleThreadExecutor();
            futureTask = new FutureTask<String>(new Callable<String>() {
            		public String call() {
                    	run();
                    	return null;
                   }});
            executor.execute(futureTask);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
    
    /**
     * Verifica si esta habilitado el proceso de polling para el monitoreo del proceso que esta corriendo actualmente
     * @return
     */
    public boolean isPollHabilitado(){
    	boolean res = false;
   
    	if(procesoIniciado){
			if(detenerSiguienteVuelta){
				res = false;
    			detenerSiguienteVuelta = false;
    			procesoIniciado = false;
    			mostrarBotonDetenerProceso = false;
			}else{
				res = true;
			}
		}else{
			res = false;
		}
    	
    	if(futureTask != null && futureTask.isDone() && executor != null) {
    		executor.shutdownNow();
        	futureTask = null;
        	executor = null;
    	}			
    	
    	return res;
    }
    
    /**
     * Le indica al proceso de polling que se detenga la siguiente vez que pase por la verificaci&oacute;n
     * @return
     */
    public String getDetenerPoll(){
    	detenerSiguienteVuelta = true;
    	
    	getInit();
    	return null;
    }
    
    /**
     * Funci&oacute;n ejecutada en cada vuelta del monitoreo del proceso de file transfer
     * @param e
     */
    public void pollEstado(ActionEvent e) { }
    
    /**
     * Parsea el archivo cargado y retorna los resultados como un arreglo de String donde cada elemento es un rengl&oacute;n leido del archivo
     * @param processId Identificador del proceso
     * @param processInfo Objeto de informaci&oacute;n del proceso
     * @return Lista con las l&iacute;neas del archivo leidas
     * @throws IOException 
     */
    private ArrayList<String> readFile( String processId, ProcessInfoVO processInfo ) throws IOException {

        InputStreamReader ir = null;
        BufferedReader br = null;
        
        try {
            long fileSize = 0;
            ir = new InputStreamReader( archivo.getInputStream() );
            fileSize = archivo.getSize();

            br = new BufferedReader(ir);
            ArrayList<String> arlArchivo = new ArrayList<String>();
            String linea = null;
            long bytesReaded = 0;
            linea = br.readLine();
            
            while( linea != null ) {
                bytesReaded += linea.length() + 2;
                if( StringUtils.isNotBlank(linea)) {
                    arlArchivo.add(linea); 
                }
                linea = br.readLine();
                double porcTerminado = 0;
                if( bytesReaded != 0 ) {
                    porcTerminado = ((double)bytesReaded/(double)fileSize) * 100;
                }
                processInfo.setStatus(ProcessInfoVO.CARGANDO);
                processInfo.setUsuario(getId() + getFolio());
                processInfo.setIdProceso(getId() + getFolio()+process);
                processInfo =  fileUploadService.getProcessInfo(processInfo);
                if( processInfo == null ){ break;}
                porcTerminado = Math.min(porcTerminado, 100);
                processInfo.setPorcentajeTerminado(new BigDecimal( porcTerminado ));
                
                if( (porcTerminado % 10) == 0 ){
                	fileUploadService.updateProcessInfo(processInfo);
                }
            }
            
            return arlArchivo;
        } catch (IOException e) {
        	throw e;
        } finally {
            if ( br != null ) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if ( ir != null ) {
                try {
                    ir.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    /**
     * Calcula el procentaje terminado de un proceso
     * @param total Total de unidades a procesar
     * @param actual Unidades procesadas hasta el momento
     * @return Porcentaje de avance del proceso
     */
    private Double getProcentajeTerminado( int total, int actual ){
        if( actual < 0 ){
            return 100.00;
        }
        double porcentaje = (double)actual *(100.00) /  (double) total;
        return  new Double(porcentaje);
    }
    
    /**
     * Inicia la validaci&oacute;n o env&iacute;o de operaciones del file transfer.
     * Esta funci&oacute;n se ejecuta en un hilo en el servidor mientras que la pantalla regresa a dar una respuesta al usuario
     * y la pantalla monitorea la actividad de esta funci&oacute;n mediante la compartici&oacute;n de datos del controller y en la BD
     */
	public void run() {
		
        try {
        	processInfo.setUsuario(getId() + getFolio());
            processInfo.setIdProceso(getId() + getFolio()+process);
            processInfo = fileUploadService.getProcessInfo(processInfo);
            
            // Crea el objeto con los datos del agente firmado a mano, ya que no se puede
            // acceder el contexto desde este metodo
            AgenteVO agenteFirmado = new AgenteVO();
            agenteFirmado.setId(getId());
            agenteFirmado.setFolio(getFolio());
            agenteFirmado.setFirmado(true);
            
            int totalRegistros = 0;
            if( processInfo.getStatus().equals(ProcessInfoVO.CARGANDO) ) {

                ArrayList<String> arlArchivo = readFile(getId() + getFolio() + process,processInfo);
                totalRegistros = arlArchivo.size();
                
                // Se realiza la validacion-guardado de la informacion
                processInfo = fileUploadService.getProcessInfo(processInfo);
                processInfo.setStatus(ProcessInfoVO.GUARDANDO);
                fileUploadService.updateProcessInfo(processInfo);
                processInfo.setPorcentajeTerminado(new BigDecimal( 0 ));
                

                int registroActual = 0;
                FileTransferVO fileTransferVO = new FileTransferVO();
                fileTransferVO.setAgenteFirmado(agenteFirmado);
                fileTransferVO.setTipoProceso(process);
                fileTransferVO.setNombreUsuario(processInfo.getUsuario());
                fileTransferVO.setInformacionArchivo(arlArchivo.toArray(new String[]{}));
                
                while( registroActual >= 0 )  {
                    fileTransferVO.setOffset(registroActual);
                    
                    registroActual = fileTransferService.almacenaInformacion(fileTransferVO);
                    
                    processInfo = fileUploadService.getProcessInfo(processInfo);
                    
                    if( processInfo == null ) { 
                    	return;
                    }
                    
                    processInfo.setPorcentajeTerminado(new BigDecimal(getProcentajeTerminado(totalRegistros, registroActual )));
                    fileUploadService.updateProcessInfo(processInfo);
                }
                processInfo.setStatus(ProcessInfoVO.CONFIRMACION);
                processInfo.setPorcentajeTerminado(new BigDecimal( 0 ));
                fileUploadService.updateProcessInfo(processInfo);
                esperaConfirmacion = true;

            } else if( processInfo.getStatus().equals(ProcessInfoVO.PROCESANDO) ){
        		processInfo.setPorcentajeTerminado(new BigDecimal( 0 ));
		        fileUploadService.updateProcessInfo(processInfo);
		        
            	// Para sacar todos los registros (en caso de que debido a la paginacion no vengan todos)
		        FileTransferVO fileTransferVO = new FileTransferVO();
                fileTransferVO.setAgenteFirmado(agenteFirmado);
                fileTransferVO.setTipoProceso(process);
                fileTransferVO.setSoloErrores(false);
                fileTransferVO.setPaginaVO(new PaginaVO());
		        resumenPrevio =  fileTransferService.muestraInformacion(fileTransferVO);
		        
            	// Para checar si se debe firmar la operacion
            	if(usuarioDebeFirmarOperacion) {
	            	//crear ISOs para que se firmen las operaciones
	            	recuperarInformacionFirmas();
	            	
	            	if((isosFirmados == null || isosFirmados.isEmpty()) && isosSinFirmar == null){
	            		isosFirmados = null;
	            		Map<String, Object> resultado = encolador.obtenerISOs(resumenPrevio);
	            		isosSinFirmar = (List<String>)resultado.get(IEncoladorProtocoloFinanciero.ID_LISTA_ISOS);
	            		//listaObjetosDatosOperacion = (List<TraspasoLibrePagoVO>)resultado.get(IEncoladorProtocoloFinanciero.ID_LISTA_OBJETOS);
	            		listaObjetosDatosOperacion = (List)resultado.get(IEncoladorProtocoloFinanciero.ID_LISTA_OBJETOS);
	            	}
            	} else {
            		// Se crean listas vacias solo para que pase la validacion
            		isosFirmados = new ArrayList<String>();
            		isosSinFirmar = new ArrayList<String>();
            		Map<String, Object> resultado = encolador.obtenerISOs(resumenPrevio);
            		//listaObjetosDatosOperacion = (List<TraspasoLibrePagoVO>)resultado.get(IEncoladorProtocoloFinanciero.ID_LISTA_OBJETOS);
            		listaObjetosDatosOperacion = (List)resultado.get(IEncoladorProtocoloFinanciero.ID_LISTA_OBJETOS);
            	}
            	
            	if(isosFirmados != null && (isosSinFirmar !=null && isosSinFirmar.size()==0)){
    		        
                    processInfo.setPorcentajeTerminado(new BigDecimal( 25 ));
                    fileUploadService.updateProcessInfo( processInfo);
                    
                    Map<BigInteger, Integer> idsOperacionesFirmadas = encolador.firmayEncola(resumenPrevio, listaObjetosDatosOperacion, isosFirmados,ticket);
                    
                    processInfo.setPorcentajeTerminado(new BigDecimal( 75 ));
                    fileUploadService.updateProcessInfo(processInfo);
                    
                    fileTransferVO = new FileTransferVO();
                    fileTransferVO.setAgenteFirmado(agenteFirmado);
                    fileTransferVO.setTipoProceso(process);
                    fileTransferVO.setConsecProtocolo((LinkedHashMap<BigInteger, Integer>)idsOperacionesFirmadas);
                    
    		        fileTransferService.grabaInformacion(fileTransferVO);
                    
    		        fileUploadService.releaseLock(processInfo);
    		        getDetalle();
    		        paginaVO = new PaginaVO();
    		        isosFirmados = null;
    		        isosSinFirmar = null;
            	} else {
                	if ((isosFirmados == null) && ((isosSinFirmar != null) && (isosSinFirmar.isEmpty()))) {
                		fileTransferVO = new FileTransferVO();
                		fileTransferVO.setAgenteFirmado(agenteFirmado);
                		fileTransferVO.setTipoProceso(process);
                		fileTransferVO.setConsecProtocolo(new LinkedHashMap());

                		fileTransferService.grabaInformacion(fileTransferVO);

                		fileUploadService.releaseLock(processInfo);
                		getDetalle();
                		paginaVO = new PaginaVO();
                		isosFirmados = null;
                		isosSinFirmar = null;
                	}
                	else {
                		processInfo.setStatus(ProcessInfoVO.CONFIRMACION);
                		processInfo.setPorcentajeTerminado(new BigDecimal( 0 ));
                		fileUploadService.updateProcessInfo(processInfo);
                		esperaConfirmacion = true;
                		totalOperaciones = isosSinFirmar.size();
                		hashIso = new ArrayList<String>();
                		for (String iso : isosSinFirmar) {
                			hashIso.add(cdb.cipherHash(iso));
                		}
                	}
            	}
            }
        } catch (Exception e) {
           if(processInfo.getStatus().equals(ProcessInfoVO.CARGANDO)) {
        	   mensajeError = "Hubo un error al cargar el archivo, verifique el formato: " + e.getMessage();
    	   } else if(processInfo.getStatus().equals(ProcessInfoVO.PROCESANDO)) {
    		   mensajeError = "Hubo un error al procesar el archivo, verifique: " + e.getMessage();
    	   } else if(processInfo.getStatus().equals(ProcessInfoVO.GUARDANDO)) {
    		   mensajeError = "Hubo un error al guardar el archivo, verifique el formato: " + e.getMessage();
    	   } else {
    	       mensajeError = "Hubo un error al leer el archivo, verifique" + e.getMessage();
    	   }
           e.printStackTrace();
           
           release();
           isosFirmados = null;
           isosSinFirmar = null;
        } finally{
        	polling = false;
        }
    }
    
    /**
     * Recupera la informaci&oacute;n de los campos ocultos de firmas electronicas
     */
    private void recuperarInformacionFirmas() {
    	
    	if(isosSinFirmar != null && totalOperaciones > 0){
    		isosFirmados = new ArrayList<String>();
    		isosSinFirmar = new ArrayList<String>();
    		
//    		String numeroSerie=null;
    		
    		 
    		
    		for(int i=1;i<=totalOperaciones;i++){
//    			if(params.get("isoFirmado"+i) != null && params.get("isoFirmado"+i).length >= 1 
//    					&& params.get("isoFirmado"+i)[0] != null && params.get("isoFirmado"+i)[0].trim().length() > 0) {
    				
//    				numeroSerie = params.get("numeroSerie")[0].replace("\r\n","\n");
    				String iso=params.get("isoSinFirmar"+i)[0].replace("\r\n","\n");
//    				String firma=params.get("isoFirmado"+i)[0].replace("\r\n","\n");
    				StringBuilder isoConfirma= new StringBuilder();
    				
    				isoConfirma.append(iso);
//    				.append(numeroSerie).append("\n").append(
//							"{SHA1withRSA}").append(firma);
			
    				isosFirmados.add(isoConfirma.toString());    				
    				
//    			} else if(params.get("isoSinFirmar"+i) != null && params.get("isoSinFirmar"+i).length >= 1) {
//    				isosSinFirmar.add(params.get("isoSinFirmar"+i)[0].replace("\r\n","\n"));
//    			}
    		}
    	}
       		
	}

    /**
     * Indica si ya se firma cada ISO en pantalla.
     * 
     * @return <code>true</code> si se firma el ISO en pantalla.
     */
    public boolean isIsosYaFirmados() {    	
    	boolean resultado=true;
    	
    	if (isosSinFirmar != null && isosSinFirmar.size() > 0) {			
    		resultado=true;
		} else {
			resultado = false;
		}
    	
    	return resultado;
    }
    
    /**
     * Activa el polling
     * @param ae
     */
	public void activaPoll(ActionEvent ae){
    	this.setPolling(true);
    }
    
	/**
	 * Desactiva el polling
	 */
    public void notPoll(){
    	this.setPolling(false);
    }
    
    
    
    
	//////////////////////////////////////////// Seccion de Getters y Setters ////////////////////////////////////////////

    /**
	 * @return the process
	 */
	public String getProcess() {
		return process;
	}

	/**
	 * @param process the process to set
	 */
	public void setProcess(String process) {
		this.process = process;
	}
    
    
    /**
	 * @return the procesoIniciado
	 */
	public boolean isProcesoIniciado() {
		return procesoIniciado;
	}

	/**
	 * @param procesoIniciado the procesoIniciado to set
	 */
	public void setProcesoIniciado(boolean procesoIniciado) {
		this.procesoIniciado = procesoIniciado;
	}

	/**
	 * @return the detenerSiguienteVuelta
	 */
	public boolean isDetenerSiguienteVuelta() {
		return detenerSiguienteVuelta;
	}

	/**
	 * @param detenerSiguienteVuelta the detenerSiguienteVuelta to set
	 */
	public void setDetenerSiguienteVuelta(boolean detenerSiguienteVuelta) {
		this.detenerSiguienteVuelta = detenerSiguienteVuelta;
	}

	/**
	 * @return the polling
	 */
	public boolean isPolling() {
		return polling;
	}

	/**
	 * @param polling the polling to set
	 */
	public void setPolling(boolean polling) {
		this.polling = polling;
	}

	/**
	 * @return the fechaCarga
	 */
	public String getFechaCarga() {
		return fechaCarga;
	}

	/**
	 * @param fechaCarga the fechaCarga to set
	 */
	public void setFechaCarga(String fechaCarga) {
		this.fechaCarga = fechaCarga;
	}

	/**
	 * @return the horaCarga
	 */
	public String getHoraCarga() {
		return horaCarga;
	}

	/**
	 * @param horaCarga the horaCarga to set
	 */
	public void setHoraCarga(String horaCarga) {
		this.horaCarga = horaCarga;
	}

	/**
	 * @return the registrosEnviados
	 */
	public String getRegistrosEnviados() {
		return registrosEnviados;
	}

	/**
	 * @param registrosEnviados the registrosEnviados to set
	 */
	public void setRegistrosEnviados(String registrosEnviados) {
		this.registrosEnviados = registrosEnviados;
	}

	/**
	 * @return the registrosCargados
	 */
	public String getRegistrosCargados() {
		return registrosCargados;
	}

	/**
	 * @param registrosCargados the registrosCargados to set
	 */
	public void setRegistrosCargados(String registrosCargados) {
		this.registrosCargados = registrosCargados;
	}

	/**
	 * @return the registrosConError
	 */
	public String getRegistrosConError() {
		return registrosConError;
	}

	/**
	 * @param registrosConError the registrosConError to set
	 */
	public void setRegistrosConError(String registrosConError) {
		this.registrosConError = registrosConError;
	}

	/**
	 * @return the totalRegistros
	 */
	public String getTotalRegistros() {
		return totalRegistros;
	}

	/**
	 * @param totalRegistros the totalRegistros to set
	 */
	public void setTotalRegistros(String totalRegistros) {
		this.totalRegistros = totalRegistros;
	}

	/**
	 * @return the usuarioResponsable
	 */
	public String getUsuarioResponsable() {
		return usuarioResponsable;
	}

	/**
	 * @param usuarioResponsable the usuarioResponsable to set
	 */
	public void setUsuarioResponsable(String usuarioResponsable) {
		this.usuarioResponsable = usuarioResponsable;
	}

	/**
	 * @return the resumenPrevio
	 */
	public TotalesProcesoVO getResumenPrevio() {
		return resumenPrevio;
	}

	/**
	 * @param resumenPrevio the resumenPrevio to set
	 */
	public void setResumenPrevio(TotalesProcesoVO resumenPrevio) {
		this.resumenPrevio = resumenPrevio;
	}

	/**
	 * @return the progreso
	 */
	public String getProgreso() {
		return progreso;
	}

	/**
	 * @param progreso the progreso to set
	 */
	public void setProgreso(String progreso) {
		this.progreso = progreso;
	}

	/**
	 * @return the resumen
	 */
	public ResumenVO getResumen() {
		return resumen;
	}

	/**
	 * @param resumen the resumen to set
	 */
	public void setResumen(ResumenVO resumen) {
		this.resumen = resumen;
	}

	/**
	 * @return the showLink
	 */
	public Boolean getShowLink() {
		return showLink;
	}

	/**
	 * @param showLink the showLink to set
	 */
	public void setShowLink(Boolean showLink) {
		this.showLink = showLink;
	}

	/**
	 * @return the pagina
	 */
	public PaginaVO getPagina() {
		return pagina;
	}

	/**
	 * @param pagina the pagina to set
	 */
	public void setPagina(PaginaVO pagina) {
		this.pagina = pagina;
	}

	/**
	 * @return the archivo
	 */
	public UploadedFile getArchivo() {
		return archivo;
	}

	/**
	 * @param archivo the archivo to set
	 */
	public void setArchivo(UploadedFile archivo) {
		this.archivo = archivo;
	}

	/**
	 * @return the navHome
	 */
	public String getNavHome() {
		return navHome;
	}

	/**
	 * @param navHome the navHome to set
	 */
	public void setNavHome(String navHome) {
		this.navHome = navHome;
	}

	/**
	 * @return the fileTransferService
	 */
	public FileTransferService getFileTransferService() {
		return fileTransferService;
	}

	/**
	 * @param fileTransferService the fileTransferService to set
	 */
	public void setFileTransferService(FileTransferService fileTransferService) {
		this.fileTransferService = fileTransferService;
	}

	/**
	 * @return the fileUploadService
	 */
	public FileUploadService getFileUploadService() {
		return fileUploadService;
	}

	/**
	 * @param fileUploadService the fileUploadService to set
	 */
	public void setFileUploadService(FileUploadService fileUploadService) {
		this.fileUploadService = fileUploadService;
	}

	/**
	 * @return the encolador
	 */
	public IEncoladorProtocoloFinanciero getEncolador() {
		return encolador;
	}

	/**
	 * @param encolador the encolador to set
	 */
	public void setEncolador(IEncoladorProtocoloFinanciero encolador) {
		this.encolador = encolador;
	}

	/**
	 * @return the processInfo
	 */
	public ProcessInfoVO getProcessInfo() {
		return processInfo;
	}

	/**
	 * @param processInfo the processInfo to set
	 */
	public void setProcessInfo(ProcessInfoVO processInfo) {
		this.processInfo = processInfo;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the folio
	 */
	public String getFolio() {
		return folio;
	}

	/**
	 * @param folio the folio to set
	 */
	public void setFolio(String folio) {
		this.folio = folio;
	}

	/**
	 * @return the esperaConfirmacion
	 */
	public boolean isEsperaConfirmacion() {
		return esperaConfirmacion;
	}

	/**
	 * @param esperaConfirmacion the esperaConfirmacion to set
	 */
	public void setEsperaConfirmacion(boolean esperaConfirmacion) {
		this.esperaConfirmacion = esperaConfirmacion;
	}

	/**
	 * @return the isosFirmados
	 */
	public List<String> getIsosFirmados() {
		return isosFirmados;
	}

	/**
	 * @param isosFirmados the isosFirmados to set
	 */
	public void setIsosFirmados(List<String> isosFirmados) {
		this.isosFirmados = isosFirmados;
	}

	/**
	 * @return the isosSinFirmar
	 */
	public List<String> getIsosSinFirmar() {
		return isosSinFirmar;
	}

	/**
	 * @param isosSinFirmar the isosSinFirmar to set
	 */
	public void setIsosSinFirmar(List<String> isosSinFirmar) {
		this.isosSinFirmar = isosSinFirmar;
	}

	/**
	 * @return the hashIso
	 */
	public List<String> getHashIso() {
		return hashIso;
	}

	/**
	 * @param hashIso the hashIso to set
	 */
	public void setHashIso(List<String> hashIso) {
		this.hashIso = hashIso;
	}

	/**
	 * @return the totalOperaciones
	 */
	public int getTotalOperaciones() {
		return totalOperaciones;
	}

	/**
	 * @param totalOperaciones the totalOperaciones to set
	 */
	public void setTotalOperaciones(int totalOperaciones) {
		this.totalOperaciones = totalOperaciones;
	}

	/**
	 * @return the cdb
	 */
	public CifradorDescifradorBlowfish getCdb() {
		return cdb;
	}

	/**
	 * @param cdb the cdb to set
	 */
	public void setCdb(CifradorDescifradorBlowfish cdb) {
		this.cdb = cdb;
	}

	/**
	 * @return the params
	 */
	public Map<String, String[]> getParams() {
		return params;
	}

	/**
	 * @param params the params to set
	 */
	public void setParams(Map<String, String[]> params) {
		this.params = params;
	}

	/**
	 * @return the encabezado
	 */
	public List<CampoArchivoVO> getEncabezado() {
		return encabezado;
	}

	/**
	 * @param encabezado the encabezado to set
	 */
	public void setEncabezado(List<CampoArchivoVO> encabezado) {
		this.encabezado = encabezado;
	}

	/**
	 * @return the mensajeError
	 */
	public String getMensajeError() {
		return mensajeError;
	}

	/**
	 * @param mensajeError the mensajeError to set
	 */
	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}

	/**
	 * @return the mostrarBotonDetenerProceso
	 */
	public boolean isMostrarBotonDetenerProceso() {
		return mostrarBotonDetenerProceso;
	}

	/**
	 * @param mostrarBotonDetenerProceso the mostrarBotonDetenerProceso to set
	 */
	public void setMostrarBotonDetenerProceso(boolean mostrarBotonDetenerProceso) {
		this.mostrarBotonDetenerProceso = mostrarBotonDetenerProceso;
	}

}
