/*
 *Copyright (c) 2010 Bursatec. All Rights Reserved 
 */
package com.indeval.portaldali.presentation.controller.tesoreria.cuentas.fileTransfer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
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
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.myfaces.custom.fileupload.UploadedFile;

import com.bursatec.seguridad.dto.UsuarioDTO;
import com.bursatec.seguridad.presentation.constants.SeguridadConstants;
import com.indeval.portaldali.middleware.dto.InstitucionDTO;
import com.indeval.portaldali.middleware.dto.util.EstadoPaginacionDTO;
import com.indeval.portaldali.middleware.services.filetransfer.CampoArchivoVO;
import com.indeval.portaldali.middleware.services.filetransfer.FileTransferService;
import com.indeval.portaldali.middleware.services.filetransfer.RegistroProcesadoVO;
import com.indeval.portaldali.middleware.services.filetransfer.ResumenVO;
import com.indeval.portaldali.middleware.services.filetransfer.TotalesProcesoVO;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.modelo.PaginaVO;
import com.indeval.portaldali.middleware.services.util.FileUploadService;
import com.indeval.portaldali.middleware.services.util.vo.ProcessInfoVO;
import com.indeval.portaldali.presentation.common.constants.UtilConstantes;
import com.indeval.portaldali.presentation.controller.common.ControllerBase;
import com.indeval.portaldali.presentation.controller.mercadodinero.fileTransfer.IEncoladorProtocoloFinanciero;
import com.indeval.portaldali.presentation.util.CifradorDescifradorBlowfish;

/**
 * Controller de la Captura de cuentas de banca comercial
 * 
 * @author Maria C. Buendia
 * @version 1.0
 */
public class FileTransferCuentasBCOMController extends ControllerBase {
	/** Indica si existe un proceso iniciado por el controller */
	private boolean procesoIniciado = false;
	/** Indica si debe mostrar en la pantalla el boton de detener proceso */
	private boolean mostrarBotonDetenerProceso = false;
	/** Indica si se debe detener el poll en la siguiente vuelta */
	private boolean detenerSiguienteVuelta = false;
	/** Bandera de Poll */
	private boolean polling = false;
	/** Mensaje de error */
	private String mensajeError;
	/** Institucion firmada */
	private InstitucionDTO institucion; 
	/** Resumen: de la fecha de Carga del documento */
	private String fechaCarga;
	/** Resumen: de la fecha de Carga del documento */
	private String horaCarga;
	/** Registros enviados al protocolo */
	private String registrosEnviados;
	/** Registros cargados */
	private String registrosCargados;
	/** Registros con Error */
	private String registrosConError;
	/** Total de Registros */
	private String totalRegistros;
	/** Usuario responsable */
	private String usuarioResponsable;
	private com.indeval.portaldali.middleware.services.filetransfer.TotalesProcesoVO resumenPrevio;
	/**
	 * Progreso de avance
	 */
	private String progreso = null;
	/**
	 * Nombre del proceso arrancado
	 */
	private String process;
	/**
	 * Información del resumen de carga
	 */
	private ResumenVO resumen;
	private Boolean showLink = Boolean.TRUE;
	/**
	 * Paginador de registros de resumen de errores
	 */
	private PaginaVO pagina;
	/**
	 * Archivo subido por el usuario
	 */
	private UploadedFile archivo;
	private String navHome;
    /**
     * Servicio para el acceso a file transfer
     */
	private FileTransferService fileTransferService;
	/**
	 * Servicio para el bloqueo y cordinación del servicio
	 */
	private FileUploadService fileUploadService = null;
	/**
	 * Encolador del protocolo para el controler
	 */
	private IEncoladorProtocoloFinanciero encolador;
	/**
	 * Información del proceso de carga actual
	 */
	private  ProcessInfoVO processInfo = new ProcessInfoVO();
	private String id;
	/**
	 * Indica si se espera una confirmación del usuario 
	 */
	private boolean esperaConfirmacion = false;
	
	private String folio;
	
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
	
	/** Utileria para la firma digital */
	private CifradorDescifradorBlowfish cdb = new CifradorDescifradorBlowfish();
	
	/**
	 * Mapa de parametros de request
	 */
	Map<String,String[]> params = null;
	
	/**
	 * Encabezado de los registros en el 
	 */
	private List<CampoArchivoVO> encabezado = null;
	
	/** Lista con los VO de traspaso libre pago o traspaso contra pago con la informacion de los registros del archivo */
	private List<Object> listaObjetosDatosOperacion;
	
	/** Indica si se presenta el applet y la logica para firmar operaciones */
	private boolean usuarioDebeFirmarOperacion;
	
	/** Usuario logueado */
	private UsuarioDTO usuario;
	
	private ExecutorService executor = null;
	
	private FutureTask<String> futureTask = null;
	
	/** Lista con los tipos de archivos aceptados (content-type) por este file transfer */
	public static final List<String> LISTA_TIPOS_ARCHIVOS_ACEPTADOS = new ArrayList<String>();
	
	static {
		LISTA_TIPOS_ARCHIVOS_ACEPTADOS.add("text/plain");
	}
	
	/**
	 * Consulta los detalles de los registros cargados y validados para la confirmacion del usuario
	 * @return null;
	 */
	public String getShowDetalleErrores() { //call desde pantalla de detalle de errores.
        try {
            AgenteVO agenteFirmado = new AgenteVO();
            agenteFirmado.setId(getInstitucionActual().getClaveTipoInstitucion());
            agenteFirmado.setFolio(getInstitucionActual().getFolioInstitucion());
            
            //busca en la tabla t_filetransfer
	        resumenPrevio = fileTransferService.muestraInformacion(agenteFirmado, process, true, pagina);//consulta de t_filetransfer
			   																							//consulta T_FILETRANSFER_OPERACIONES por pk
	        
	        if(resumenPrevio != null) {
		        resumenPrevio = agregaMensajeSinError(resumenPrevio);
		        pagina = resumenPrevio.getPaginaVO();
	        }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            release(); //quita el block y borra el fileUpload 
            return UtilConstantes.ERROR;
        }
        
        return null;
    }
	
	/**
	 * Recupera la informacion del proceso que esta actualmente corriendo para mostrar la pantalla por primera vez.
	 * @return null
	 */
    public String getInit() {//call desde pantalla fileTransfer
    	inicializarPaginacion();
    	setId(getInstitucionActual().getClaveTipoInstitucion());
        setFolio(getInstitucionActual().getFolioInstitucion());
        usuarioDebeFirmarOperacion = isUsuarioConFacultadFirmar();
    	getDetalle(); //obtiene estado del proceso de carga
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
    	
        return null;
    }
    
    /**
     * Inicializa los objetos de paginacion
     */
    private void inicializarPaginacion() {//call interno
    	pagina = new PaginaVO();
    	paginacion.setRegistrosPorPagina(5);
    	pagina.setRegistrosXPag(paginacion.getRegistrosPorPagina());
	}

	/**
     * Verifica si existe un proceso corriendo
     */
    public Boolean getIsProcessRunning() { //call ???
    	processInfo.setUsuario(getId() + getFolio());
        processInfo.setIdProceso(getId() + getFolio()+process);
        return fileUploadService.isProcessRunning(processInfo); //genera objeto fileUpload de processInfo
        														//consulta FileUpload, si hay registros -> true, sino -> false
    }
    
    /**
     * Verifica si el proceso que esta corriendo corresponde al usuario actual
     * @return
     */
    public Boolean getIsThisMyProcess() {//call desde pantalla fileTransfer, boton procesar y cancelar
    	if(processInfo == null){
    		processInfo = new ProcessInfoVO();
    	}
    	processInfo.setUsuario(getId() + getFolio());
        processInfo.setIdProceso(getId() + getFolio()+process);
        processInfo =  fileUploadService.getProcessInfo(processInfo); //genera fileUpload de processInfo
        															  //consulta FileUpload
        															  //genera processInfo de fileUpload
        if( processInfo == null ) {
            return Boolean.FALSE;
        }
        return new Boolean( ( processInfo.getUsuario().equals(getId() + getFolio()) ) );
    }
    
    /**
     * Cancela el proceso de carga del archivo.
     */
    public void abortProcess(ActionEvent ev) {//call desde pantalla fileTransfer, boton detener
    	esperaConfirmacion = false;
    	polling = false;
    	detenerSiguienteVuelta = true;
    	abortProcess();
    }
    
    /**
     * Cancela el proceso de carga del archivo.
     * @return Constante que indica que regrese a la pagina principal (si aplica a la hora de llamar el metodo)
     */
    public String abortProcess() { //call desde abortProcess
    	try {
    		if(futureTask != null && futureTask.isDone()) {
    			futureTask.cancel(true);
    		}
        	cancelar(null);
            mensajeError = "Proceso de carga detenido por el usuario";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,mensajeError,mensajeError));
        	if(executor != null) {
        		executor.shutdownNow();
        	}
        	futureTask = null;
        	executor = null;
    	} catch(Exception e) {
    		e.printStackTrace();
    		logger.error(e.getMessage(), e);
    		release();//quita el block y borra el fileUpload
    	}
    	
    	return navHome;
    }

	/**
	 * Libera el lock del usuario
	 */
	private void release() { //quita el block y borra el fileUpload
		if(processInfo == null){
    		processInfo = new ProcessInfoVO();
    	}
		processInfo.setUsuario(getId() + getFolio());
        processInfo.setIdProceso(getId() + getFolio()+process);
    	fileUploadService.releaseLock(processInfo);//quita el block y borra el fileUpload
    	esperaConfirmacion = false;
    	polling = false;
    	detenerSiguienteVuelta = true;
	}

    /**
     * Recupera el usuario que esta actualmente cargando un archivo
     * @return
     */
    public String getUserLoadingFile() { //call ???
    	processInfo.setUsuario(getId() + getFolio());
        processInfo.setIdProceso(getId() + getFolio()+process);
        processInfo = fileUploadService.getProcessInfo(processInfo);//genera objeto FileUpload con processInfo
																  //obtiene informacion de T_FILE_UPLOAD
																  //genera objeto processInfo con FileUpload
        if( processInfo == null ) {
            return "";
        }
        
        return processInfo.getUsuario();
    }
    
    /**
     * Recupera el detalle de la informacion que se esta cargando actualmente o que espera confirmacion.
     * @return
     */
    public String getDetalle() { //call desde init
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
            resumen = fileTransferService.obtieneResumen(agenteFirmado,process); //lee de t_filetransfer y guarda en otro formato
            showLink = new Boolean(!resumen.getTotalError().equals(new Integer(0)));
            processInfo =  fileUploadService.getProcessInfo(processInfo); //genera objeto FileUpload con processInfo
            															  //obtiene informacion de T_FILE_UPLOAD
            															  //genera objeto processInfo con FileUpload
            if( processInfo != null){
            	if( processInfo.getStatus().equals(ProcessInfoVO.CARGANDO) ||
            		processInfo.getStatus().equals(ProcessInfoVO.GUARDANDO) ||
            		processInfo.getStatus().equals(ProcessInfoVO.VALIDANDO) ||
            		processInfo.getStatus().equals(ProcessInfoVO.PROCESANDO) ){
            		return navHome; //se va a la pantalla mapeada
            	}
            	else {
            		return getResumenPrevioCarga(); //t_filetransfer y T_FILETRANSFER_OPERACIONES por pk
            	}
            }
        } catch (Exception e) {
            mensajeError = e.getMessage();
        	e.printStackTrace();
            logger.error(e.getMessage(), e);
            release();//quita el block y borra el fileUpload
            //return UtilConstantes.ERROR;
        }
        
        return navHome;
    }
    
    /**
     * Consulta el resumen de la validaciones aplicadas y el resultado de estas a la ultima carga del archivo del usuario
     * @return
     */
    @SuppressWarnings("unchecked")
	public String getResumenPrevioCarga(){ //call desde getDetalle
    	try {
	    	AgenteVO agenteFirmado = new AgenteVO();
	        agenteFirmado.setId(getId());
	        agenteFirmado.setFolio(getFolio());
	        agenteFirmado.setFirmado(true);
	        resumenPrevio = fileTransferService.muestraInformacion(agenteFirmado, process, false, pagina); //consulta de t_filetransfer
	        																							   //consulta T_FILETRANSFER_OPERACIONES por pk  
	        
	        if(resumenPrevio != null) {
		        resumenPrevio = agregaMensajeSinError(resumenPrevio);
		        pagina = resumenPrevio.getPaginaVO();
		        
		        if(pagina != null && pagina.getRegistros().size()>0){
		        	RegistroProcesadoVO primerRegistro = (RegistroProcesadoVO)pagina.getRegistros().get(0);
		        	encabezado = primerRegistro.getDatos();
		        	if(paginacion.getTotalResultados() <= 0) {
		        		incializarEstadoPaginacion(pagina.getTotalRegistros());
		        	}
		        }
	        }
	        
	        return navHome; //regresa a la pagina
    	} catch (Exception e) {
    		mensajeError = e.getMessage();
    		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(mensajeError));
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            release(); //quita el block y borra el fileUpload
            return UtilConstantes.ERROR;
    	}
    }
    
    /**
     * Agrega los mensajes de error al objeto que contiene los resultados
     * @param p Objeto con los datos de error
     * @return Mismo objeto con los datos de error
     */
    @SuppressWarnings("unchecked")
	public TotalesProcesoVO agregaMensajeSinError(TotalesProcesoVO p){ //coloca REGISTRO VALIDO si no hay eror en el registro
    	PaginaVO pagina = p.getPaginaVO();
    	List l = pagina.getRegistros();

    	for(int i=0; i<l.size(); i++){
    		RegistroProcesadoVO rp = (RegistroProcesadoVO) l.get(i);
    		if(rp.getMensajesError() == null || rp.getMensajesError().length == 0){
    			rp.setMensajesError(new String[]{"REGISTRO VALIDO"});
    		}
    	}
    	return p;
    }
    
    /**
     * Cancela el proceso de validacion y procesamiento de los registros cargados
     * @param ev
     */
    public void cancelar(ActionEvent ev){//call desde abortProcess() y pantalla filetransfer, btnCancelar
    	try{
    		AgenteVO agenteFirmado = new AgenteVO();
            agenteFirmado.setId(getId());
            agenteFirmado.setFolio(getFolio());
            agenteFirmado.setFirmado(true);
            
    		fileTransferService.cancelaProceso(agenteFirmado, process); //consulta t_filetransfer
    																    //consulta T_FILETRANSFER_OPERACIONES
    																	//borra lo encontrado FileTransfer y FileTransferOperaciones
    		
    		getDetalle();//obtiene estado del proceso de carga
    	} catch (Exception e) {
    		mensajeError = e.getMessage();
    		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(mensajeError));
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            
		} finally{
			release(); //quita el block y borra el fileUpload
    		isosFirmados = null;
    		isosSinFirmar = null;
	        paginacion = new EstadoPaginacionDTO();
	        inicializarPaginacion();
		}
    	
    }

    /**
     * Inicia el proceso de validacion y envio de operaciones  de los datos cargados del archivo.
     * @param e
     */
    @SuppressWarnings("unchecked")
	public void procesar(ActionEvent e){ //call pantalla filetransfer, btnProcesar
		//Se agrega validación extra para saber si tiene la facultad CON_FIRMA
		//03-Febrero-2015 Pablo Balderas
    	if(isUsuarioConFacultadFirmar()) {
    		try {
    			//Valida la vigencia del certificado - 18-Agosto-2014 Pablo Balderas
    			validarVigenciaCertificado();
    	    	mensajeError = null;
    	    	processInfo.setUsuario(getId() + getFolio());
    	    	processInfo.setIdProceso(getId() + getFolio()+process);
    	        //Generar firmas
    	    
    	    	fileUploadService.getProcessInfo(processInfo);//genera objeto FileUpload con processInfo
    														  //obtiene informacion de T_FILE_UPLOAD
    														  //genera objeto processInfo con FileUpload
    	    	processInfo.setStatus(ProcessInfoVO.PROCESANDO);
    	        processInfo.setPorcentajeTerminado(new BigDecimal( 0 ));
    	        fileUploadService.updateProcessInfo(processInfo); //genera objeto fileUpload de processInfo
    	        												  //hibernate.update(fileUpload)
    	        params = ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getParameterMap();
    	        HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
    	        usuario= session!=null?(UsuarioDTO)session.getAttribute(SeguridadConstants.USUARIO_SESION):null;
    	        getDetalle();//obtiene estado del proceso de carga
    	        polling = true;
    	        procesoIniciado = true;
    	        detenerSiguienteVuelta = false;
    			esperaConfirmacion = false;
    			mostrarBotonDetenerProceso = false;
    			//genera el hilo para el procesamiento
    			executor = Executors.newSingleThreadExecutor();
    			futureTask = new FutureTask<String>(new Callable<String>() {
    					public String call() {
    			        	run();
    			        	return null;
    			       }});
    			executor.execute(futureTask);
    		}
    		catch(BusinessException businessException) {
        		addMessage(businessException.getMessage(), FacesMessage.SEVERITY_ERROR);
        	}
    		catch (Exception ex) {
    		    ex.printStackTrace();
    		}
    	}
    	else {
    		addMessageFromProperties("msgErrorSinCertificado", FacesMessage.SEVERITY_ERROR);
    	}
    }

    /**
     * Atiende la peticion del usuario para iniciar el proceso de carga de archivo de operaciones.
     * @param e
     */
    public void uploadFile(ActionEvent e) {//call desde pantalla filetransfer, boton iniciar proceso
		//Se agrega validación extra para saber si tiene la facultad CON_FIRMA
		//03-Febrero-2015 Pablo Balderas
    	if(isUsuarioConFacultadFirmar()) {
        	mensajeError = null;
            boolean errors = false;
            
            setId(getInstitucionActual().getClaveTipoInstitucion());
            setFolio(getInstitucionActual().getFolioInstitucion());
           
            if( archivo == null ) {
            	mensajeError = "El archivo de operaciones es un dato requerido";
                errors = true;
            } else if(!LISTA_TIPOS_ARCHIVOS_ACEPTADOS.contains(archivo.getContentType())) {
            	mensajeError = "El archivo de operaciones debe ser de tipo texto plano";
            	errors = true;
            }
            if(!errors && (getId() == null || getId().equals("") ||
                    getFolio() == null || getFolio().equals(""))) {
                mensajeError = "Se necesita un usuario logeado";
                errors = true;
            }
            if( errors ) {
                return;
            }
           
            //inicializa el proceso de carga
            if(processInfo==null){
            	processInfo = new ProcessInfoVO();
            }
            processInfo.setUsuario(getId() + getFolio());
            processInfo.setIdProceso(getId() + getFolio()+process);
            processInfo.setAbort('F');
            processInfo.setPorcentajeTerminado(new BigDecimal(0));
            processInfo.setStatus(ProcessInfoVO.CARGANDO);
            processInfo.setUsuario(getId()+getFolio());
            Boolean candado = fileUploadService.getLock(processInfo);//genera objeto fileUpload de processInfoVO
            														 //consulta fileUpload por id
            														 //encuentra registros -> false, no hay -> true

            if(!errors && !candado.booleanValue()) {
                mensajeError = "Ya hay un proceso de carga iniciado para este tipo de papel";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, mensajeError,mensajeError));
                
                return;
            }
            this.setPolling(true);
            esperaConfirmacion = false;
            procesoIniciado = true;
        	detenerSiguienteVuelta = false;
        	isosFirmados = null;
        	isosSinFirmar = null;
        	mostrarBotonDetenerProceso = true;
            
            getDetalle();//obtiene estado del proceso de carga

            //genera el hilo para el upload
            executor = Executors.newSingleThreadExecutor();
    		futureTask = new FutureTask<String>(new Callable<String>() {
    				public String call() {
    		        	run();
    		        	return null;
    		       }});
    		executor.execute(futureTask);    		
    	}
    	else {
    		addMessageFromProperties("msgErrorSinCertificado", FacesMessage.SEVERITY_ERROR);
    	}
    }
    
    /**
     * Verifica si esta habilitado el proceso de polling para el monitoreo del proceso que esta corriendo actualmente
     * @return
     */
    public boolean isPollHabilitado(){ //call desde pantalla fileTransfer
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
     * Le indica al proceso de poll que se detenga la siguiente vez que pase por la verificacion
     * @return
     */
    public String getDetenerPoll() {//call desde pantalla fileTransfer
    	detenerSiguienteVuelta = true;
    	
    	getInit();
    	return null;
    }
    
    /**
     * Funcion ejecutada en cada vuelta del monitoreo del porceso de file transfer
     * @param e
     */
    public void pollEstado(ActionEvent e) { }//call desde pantalla fileTransfer
    
    /**
     * Parsea el archivo cargado y retorna los resultados como un arreglo de String donde cada elemento es un rengln ledo del archivo
     * @param processId Identificador del proceso
     * @param processInfo Objeto de información del proceso
     * @return Lista con las lneas del archivo ledas
     * @throws IOException 
     */
    @SuppressWarnings("unchecked")
	private ArrayList readFile( String processId, ProcessInfoVO processInfo ) throws IOException { //call desde run()

        InputStreamReader ir = null;
        BufferedReader br = null;
        try {
            long fileSize = 0;
            ir = new InputStreamReader( archivo.getInputStream() );
            fileSize = archivo.getSize();

            br = new BufferedReader(ir);
            ArrayList arlArchivo = new ArrayList();
            String linea = null;
            long bytesReaded = 0;
            linea = br.readLine();
            while( linea != null ) {
                bytesReaded += linea.length() + 2;
                if( StringUtils.isNotBlank(linea.trim())) {
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
                processInfo =  fileUploadService.getProcessInfo(processInfo); //genera objeto FileUpload con processInfo
																			  //obtiene informacion de T_FILE_UPLOAD
																			  //genera objeto processInfo con FileUpload
                if( processInfo == null ){ break;}
                porcTerminado = Math.min(porcTerminado, 100);
                processInfo.setPorcentajeTerminado(new BigDecimal( porcTerminado ));
                
                if( (porcTerminado % 10) == 0 ){
                	fileUploadService.updateProcessInfo(processInfo);//genera objeto FileUpload con processInfo
                													 //hibernate.update(fileUpload);
                }
            }
            return arlArchivo;
        }catch (IOException e) {
            throw e;
        }finally {
            if ( br != null ) {
                try {
                    br.close();
                } catch (IOException e) {
                    logger.warn(e.getMessage(), e);
                }
            }
            if ( ir != null ) {
                try {
                    ir.close();
                } catch (IOException e) {
                    logger.warn(e.getMessage(), e);
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
    private Double getProcentajeTerminado( int total, int actual ){ //call interno
        if( actual < 0 ){
            return 100.00;
        }
        double porcentaje = (double)actual *(100.00) /  (double) total;
        return  new Double(porcentaje);
    }
    
    /**
     * Inicia la validación o envo de operaciones del file transfer.
     * Esta función se ejecuta en un hilo en el servidor mientras que la pantalla regresa a dar una respuesta al usuario
     * y la pantalla monitorea la actividad de esta función mediante la compartición de datos del controller y en la BD
     */
    @SuppressWarnings("unchecked")
	public void run() { //call procesar() y upLoadFile()
        try {
            
        	processInfo.setUsuario(getId() + getFolio());
            processInfo.setIdProceso(getId() + getFolio()+process);
            processInfo = fileUploadService.getProcessInfo(processInfo);//genera objeto FileUpload con processInfo
																	    //obtiene informacion de T_FILE_UPLOAD
																	    //genera objeto processInfo con FileUpload
            AgenteVO agenteFirmado = new AgenteVO();
            agenteFirmado.setId(getId());
            agenteFirmado.setFolio(getFolio());
            int totalRegistros = 0;
            if( processInfo.getStatus().equals(ProcessInfoVO.CARGANDO) ){

                ArrayList arlArchivo = readFile(getId() + getFolio() + process,processInfo); //lee archivo, lista con lineas
                totalRegistros = arlArchivo.size();
                processInfo = fileUploadService.getProcessInfo(processInfo); //genera objeto FileUpload con processInfo
																			  //obtiene informacion de T_FILE_UPLOAD
																			  //genera objeto processInfo con FileUpload
                processInfo.setStatus(ProcessInfoVO.GUARDANDO);
                
                fileUploadService.updateProcessInfo(processInfo);//genera objeto FileUpload con processInfo
				 												 //hibernate.update(fileUpload);
                processInfo.setPorcentajeTerminado(new BigDecimal( 0 ));
                

                int registroActual = 0;
                
                while( registroActual >= 0 )  {
                										//save fileTransfer
                    registroActual = fileTransferService.almacenaInformacion(agenteFirmado, process, arlArchivo, registroActual, processInfo.getUsuario());
                    
                    processInfo = fileUploadService.getProcessInfo(processInfo);//genera objeto FileUpload con processInfo
																				  //obtiene informacion de T_FILE_UPLOAD
																				  //genera objeto processInfo con FileUpload
                    if( processInfo == null ){ return;}
                    processInfo.setPorcentajeTerminado(new BigDecimal(getProcentajeTerminado(totalRegistros, registroActual )));
                    fileUploadService.updateProcessInfo(processInfo);//genera objeto FileUpload con processInfo
					 												 //hibernate.update(fileUpload);
                }

                processInfo.setStatus(ProcessInfoVO.VALIDANDO);
                processInfo.setPorcentajeTerminado(new BigDecimal( 0 ));
                fileUploadService.updateProcessInfo(processInfo);//genera objeto FileUpload con processInfo
				 												//hibernate.update(fileUpload);
                registroActual = 0;
                
                while( registroActual >= 0 ) {
                	BigInteger idBoveda = new BigInteger("1");
                    registroActual = fileTransferService.validaInformacion(agenteFirmado, process, idBoveda, registroActual); 
                    										//consulta FileTransfer
                    										// tipoProceso -> validaInformacionNombreTipoProceso
                    
                    processInfo = fileUploadService.getProcessInfo(processInfo); //genera objeto FileUpload con processInfo
																				  //obtiene informacion de T_FILE_UPLOAD
																				  //genera objeto processInfo con FileUpload
                    if( processInfo == null ){ return;}
                    processInfo.setPorcentajeTerminado(new BigDecimal(getProcentajeTerminado(totalRegistros, registroActual )));
                    fileUploadService.updateProcessInfo(processInfo);//genera objeto FileUpload con processInfo
					 												//hibernate.update(fileUpload);
                }
                processInfo.setStatus(ProcessInfoVO.CONFIRMACION);
                processInfo.setPorcentajeTerminado(new BigDecimal( 0 ));
                fileUploadService.updateProcessInfo(processInfo);//genera objeto FileUpload con processInfo
                												 //hibernate.update(fileUpload);
                esperaConfirmacion = true;

            }
            else if( processInfo.getStatus().equals(ProcessInfoVO.PROCESANDO) ){
        		processInfo.setPorcentajeTerminado(new BigDecimal( 0 ));
//		        fileUploadService.updateProcessInfo(processInfo);//genera objeto FileUpload con processInfo
				 												 //hibernate.update(fileUpload);
                
		        // Para sacar todos los registros (en caso de que debido a la paginacion no vengan todos)
		        resumenPrevio =  fileTransferService.muestraInformacion(agenteFirmado, process, false, new PaginaVO());//consulta de t_filetransfer
																													   //consulta T_FILETRANSFER_OPERACIONES por pk
		        																									   //genera datos
            	// Para checar si se debe firmar la operacion
            	if(usuarioDebeFirmarOperacion) {
	            	//crear ISOs para que se firmen las operaciones
	            	recuperarInformacionFirmas();	            	
	            	if((isosFirmados == null || isosFirmados.isEmpty()) && isosSinFirmar == null){
	            		isosFirmados = null;
	            		//firma las cuentas
	            		Map<String, Object> resultado = encolador.obtenerISOs(resumenPrevio);
	            		isosSinFirmar = (List<String>)resultado.get(IEncoladorProtocoloFinanciero.ID_LISTA_ISOS);
	            		listaObjetosDatosOperacion = (List<Object>)resultado.get(IEncoladorProtocoloFinanciero.ID_LISTA_OBJETOS);
	            	}
            	}
            	else {
            		// Se crean listas vacias solo para que pase la validacion
            		isosFirmados = new ArrayList<String>();
            		isosSinFirmar = new ArrayList<String>();
            		logger.warn(" el usuario debe poder usar firma !!...");
            	}
            	
            	if(isosFirmados != null && (isosSinFirmar !=null && isosSinFirmar.size()==0)){
                    processInfo.setPorcentajeTerminado(new BigDecimal( 25 ));
                    fileUploadService.updateProcessInfo( processInfo);//genera objeto FileUpload con processInfo
					 												  //hibernate.update(fileUpload);
                    
                    //escribe la cuenta en la BD
					Map idsOperacionesFirmadas = encolador.firmayEncola(resumenPrevio, listaObjetosDatosOperacion, isosFirmados);
                    processInfo.setPorcentajeTerminado(new BigDecimal( 75 ));
                    fileUploadService.updateProcessInfo(processInfo);//genera objeto FileUpload con processInfo
					 												 //hibernate.update(fileUpload);
    		        
                    fileTransferService.grabaInformacion(agenteFirmado, process, (HashMap) idsOperacionesFirmadas);
                    					//valida agente y tipo proceso, solo que no esten vacios
                    				    //consulta file transfer
                    					//valida si es procesada por protocolo 
                    					//update(fileTransfer)
    		        
    		        fileUploadService.releaseLock(processInfo); //genera FileUpload de processInfo
    		        										    //hibernate.delete(fileUpload)
    		        getDetalle();//obtiene estado del proceso de carga
    		        isosFirmados = null;
    		        isosSinFirmar = null;
    		        paginacion = new EstadoPaginacionDTO();
    		        inicializarPaginacion();
            	} else {
            		processInfo.setStatus(ProcessInfoVO.CONFIRMACION);
                    processInfo.setPorcentajeTerminado(new BigDecimal( 0 ));
                    fileUploadService.updateProcessInfo(processInfo);//genera objeto FileUpload con processInfo
					 												 //hibernate.update(fileUpload);
                	esperaConfirmacion = true;
                	totalOperaciones = isosSinFirmar.size();
                	hashIso = new ArrayList<String>();
                	for (String iso : isosSinFirmar) {
                		hashIso.add(cdb.cipherHash(iso));
					}
            	}
            }
        } catch (Exception e) {
           logger.error(e.getMessage(), e);
           e.printStackTrace();
           
           mensajeError = e.getMessage();
           release();//quita el block y borra el fileUpload
           isosFirmados = null;
	        isosSinFirmar = null;
        } finally{
        	polling = false;
        	
        }
        
    }
    
    /**
     * Recupera la informacion de los campos ocultos de firmas electronicas
     */
    private void recuperarInformacionFirmas(){ //call desde run()
    	
    	if(isosSinFirmar != null && totalOperaciones > 0){
    		isosFirmados = new ArrayList<String>();
    		
    		String numeroDeSerie=(String)params.get("numeroSerie")[0];
    		if(StringUtils.isBlank(numeroDeSerie)){
    			isosSinFirmar = null;
    			isosFirmados = null;
    			return;
    		}
    		
    		String localIsoSinfirma=null;
//    		String localFirmaIso=null;
    		StringBuilder isoCompleto=null;    		
    		
    		for(int i=1;i<=totalOperaciones;i++){
//    			if(params.get("isoFirmado"+i) != null && params.get("isoFirmado"+i).length >= 1 
//    					&& params.get("isoFirmado"+i)[0] != null && params.get("isoFirmado"+i)[0].trim().length() > 0) {
    				isoCompleto = new StringBuilder();
    				/*completando datos para la firma*/
    				isoCompleto.append(usuario.getClaveUsuario()).append("_-*#");
    				isoCompleto.append(numeroDeSerie).append("_-*#");
    				/*colocando el iso firmado*/
    				localIsoSinfirma=params.get("isoSinFirmar"+i)[0].replace("\r\n","\n");
//    				localFirmaIso=params.get("isoFirmado"+i)[0].replace("\r\n","\n");
    				isoCompleto.append(localIsoSinfirma);
//    				.append("\n")
//    					.append(numeroDeSerie).append("\n").append("{SHA1withRSA}").append(localFirmaIso);
    				isosFirmados.add(isoCompleto.toString());
//    			} else if(params.get("isoSinFirmar"+i) != null && params.get("isoSinFirmar"+i).length >= 1) {
//    				isosSinFirmar.add(params.get("isoSinFirmar"+i)[0].replace("\r\n","\n"));
//    			}
    		}
    		isosSinFirmar = new ArrayList<String>();
    	}
       		
	}
    
    /**
     * Indica si ya se firmó cada ISO en pantalla.
     * 
     * @return <code>true</code> si se firmó el ISO en pantalla.
     */
    public boolean isIsosYaFirmados() { //call desde pantalla fileTransfer
    	boolean resultado=true;
    	
    	if (isosSinFirmar != null && isosSinFirmar.size() > 0) {			
    		resultado=true;
		} else {
			resultado = false;
		}
    	
    	return resultado;
    }
    
    /**
     * Activa la bandera de polling
     * 
     * @param ae Evento generado durante la solicitud
     */
	public void activaPoll(ActionEvent ae){ //call ???
    	this.setPolling(true);
    	
    }
    
	/**
	 * Desactiva la bandera de polling
	 */
    public void notPoll(){ //call ???
    	this.setPolling(false);
    }
    
    /**
	 * Invoca las funciones de navegacion de pagina de la consulta principal de
	 * la pantalla
	 * 
	 * @param e Evento generado durante la solicitud
	 */
	public void navegarPorResultados(ActionEvent e) { //call desde pantalla filetransfer
		String navegacion = (String) e.getComponent().getAttributes().get(
				"navegacion");

		try {
			paginacion.getClass().getMethod(navegacion).invoke(paginacion);
		} catch (Exception ex) {
			logger.error(
							"Error de invocacion de metodo al navega por los resultados principales",
							ex);
		}
		
		pagina.setOffset((paginacion.getNumeroPagina()-1)*paginacion.getRegistrosPorPagina());
		pagina.setRegistrosXPag(paginacion.getRegistrosPorPagina());
		getResumenPrevioCarga();
	}
	
	/**
     * Metodo llamado al actualizar el numero de registros por pagina
     *  
     * @param e Evento generado durante la solicitud
     */
    public void actualizarPaginacionRegistro(ActionEvent e) { //call desde pantalla FileTransfer
    	pagina.setRegistrosXPag(paginacion.getRegistrosPorPagina());
    	
    	// Para resetear la navigacion
    	paginacion.setTotalResultados(-1);
    	pagina.setOffset(0);
    	
    	getResumenPrevioCarga();
    }
	
	
	
	
	/////////////////////////// SECCION DE GETTERS Y SETTERS ///////////////////////////
	public String getMensajeError() {
		return mensajeError;
	}

	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}

	public InstitucionDTO getInstitucion() {
		return institucion;
	}

	public void setInstitucion(InstitucionDTO institucion) {
		this.institucion = institucion;
	}

	public String getFechaCarga() {
		return fechaCarga;
	}

	public void setFechaCarga(String fechaCarga) {
		this.fechaCarga = fechaCarga;
	}

	public String getHoraCarga() {
		return horaCarga;
	}

	public void setHoraCarga(String horaCarga) {
		this.horaCarga = horaCarga;
	}

	public String getRegistrosEnviados() {
		return registrosEnviados;
	}

	public void setRegistrosEnviados(String registrosEnviados) {
		this.registrosEnviados = registrosEnviados;
	}

	public String getRegistrosCargados() {
		return registrosCargados;
	}

	public void setRegistrosCargados(String registrosCargados) {
		this.registrosCargados = registrosCargados;
	}

	public String getRegistrosConError() {
		return registrosConError;
	}

	public void setRegistrosConError(String registrosConError) {
		this.registrosConError = registrosConError;
	}

	public String getTotalRegistros() {
		return totalRegistros;
	}

	public void setTotalRegistros(String totalRegistros) {
		this.totalRegistros = totalRegistros;
	}

	public String getUsuarioResponsable() {
		return usuarioResponsable;
	}

	public void setUsuarioResponsable(String usuarioResponsable) {
		this.usuarioResponsable = usuarioResponsable;
	}

	public com.indeval.portaldali.middleware.services.filetransfer.TotalesProcesoVO getResumenPrevio() {
		return resumenPrevio;
	}

	public void setResumenPrevio(
			com.indeval.portaldali.middleware.services.filetransfer.TotalesProcesoVO resumenPrevio) {
		this.resumenPrevio = resumenPrevio;
	}

	public String getProgreso() {
		return progreso;
	}

	public void setProgreso(String progreso) {
		this.progreso = progreso;
	}

	public String getProcess() {
		return process;
	}

	public void setProcess(String process) {
		this.process = process;
	}

	public ResumenVO getResumen() {
		return resumen;
	}

	public void setResumen(ResumenVO resume) {
		this.resumen = resume;
	}

	public Boolean getShowLink() {
		return showLink;
	}

	public void setShowLink(Boolean showLink) {
		this.showLink = showLink;
	}

	public PaginaVO getPagina() {
		return pagina;
	}

	public void setPagina(PaginaVO pagina) {
		this.pagina = pagina;
	}

	public UploadedFile getArchivo() {
		return archivo;
	}

	public void setArchivo(UploadedFile archivo) {
		this.archivo = archivo;
	}

	public String getNavHome() {
		return navHome;
	}

	public void setNavHome(String navHome) {
		this.navHome = navHome;
	}

	public FileTransferService getFileTransferService() {
		return fileTransferService;
	}

	public void setFileTransferService(FileTransferService fileTransferService) {
		this.fileTransferService = fileTransferService;
	}

//	public IEncoladorProtocoloFinanciero getEncolador() {
//		return encolador;
//	}
//
//	public void setEncolador(IEncoladorProtocoloFinanciero encolador) {
//		this.encolador = encolador;
//	}

	public FileUploadService getFileUploadService() {
		return fileUploadService;
	}

	public void setFileUploadService(FileUploadService fileUploadService) {
		this.fileUploadService = fileUploadService;
	}

	public ProcessInfoVO getProcessInfo() {
		return processInfo;
	}

	public void setProcessInfo(ProcessInfoVO processInfo) {
		this.processInfo = processInfo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public boolean isPolling() {
		
		return polling;
	}

	public void setPolling(boolean polling) {
		this.polling = polling;
	}

	public boolean isEsperaConfirmacion() {
		return esperaConfirmacion;
	}

	public void setEsperaConfirmacion(boolean esperaConfirmacion) {
		this.esperaConfirmacion = esperaConfirmacion;
	}

	public List<String> getIsosSinFirmar() {
		return isosSinFirmar;
	}

	public void setIsosSinFirmar(List<String> isosSinFirmar) {
		this.isosSinFirmar = isosSinFirmar;
	}

	public int getTotalOperaciones() {
		return totalOperaciones;
	}

	public void setTotalOperaciones(int totalOperaciones) {
		this.totalOperaciones = totalOperaciones;
	}

	public List<String> getHashIso() {
		return hashIso;
	}

	public void setHashIso(List<String> hashIso) {
		this.hashIso = hashIso;
	}

	public List<String> getIsosFirmados() {
		return isosFirmados;
	}

	public void setIsosFirmados(List<String> isosFirmados) {
		this.isosFirmados = isosFirmados;
	}

	public List<CampoArchivoVO> getEncabezado() {
		return encabezado;
	}

	public void setEncabezado(List<CampoArchivoVO> encabezado) {
		this.encabezado = encabezado;
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

	public IEncoladorProtocoloFinanciero getEncolador() {
		return encolador;
	}

	public void setEncolador(IEncoladorProtocoloFinanciero encolador) {
		this.encolador = encolador;
	}
}