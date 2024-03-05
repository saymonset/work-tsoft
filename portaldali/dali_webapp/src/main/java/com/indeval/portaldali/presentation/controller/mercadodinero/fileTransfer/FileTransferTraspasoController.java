/*
 *Copyright (c) 2005-2007 Bursatec. All Rights Reserved 
 */
package com.indeval.portaldali.presentation.controller.mercadodinero.fileTransfer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
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

import org.apache.commons.lang.StringUtils;
import org.apache.myfaces.custom.fileupload.UploadedFile;

import com.bursatec.seguridad.presentation.constants.SeguridadConstants;
import com.indeval.portaldali.middleware.constantes.FileTransferConstantes;
import com.indeval.portaldali.middleware.dto.InstitucionDTO;
import com.indeval.portaldali.middleware.dto.util.EstadoPaginacionDTO;
import com.indeval.portaldali.middleware.services.common.PropiedadesDaliService;
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
import com.indeval.portaldali.persistencia.fileTransfer.ArchivosFileTransfer;
import com.indeval.portaldali.presentation.common.constants.UtilConstantes;
import com.indeval.portaldali.presentation.controller.common.ControllerBase;
import com.indeval.portaldali.presentation.controller.mercadodinero.fileTransfer.encoladores.EncoladorDepositosEfectivo;
import com.indeval.portaldali.presentation.controller.mercadodinero.fileTransfer.encoladores.EncoladorMCTraspasos;
import com.indeval.portaldali.presentation.controller.mercadodinero.fileTransfer.encoladores.EncoladorMDTraspasos;
import com.indeval.portaldali.presentation.controller.mercadodinero.fileTransfer.encoladores.EncoladorTref;
import com.indeval.portaldali.presentation.util.CifradorDescifradorBlowfish;

/**
 * Controller de la Captura de Operaciones
 * 
 * @author Juan Carlos Huizar Moreno
 * @version 1.0
 */
public class FileTransferTraspasoController extends ControllerBase {
	
	/** Indica si existe un proceso iniciad por el controller */
	private boolean procesoIniciado = false;
	
	/** Indica si debe mostrar en la pantalla el boton de detener proceso  */
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
	
	private TotalesProcesoVO resumenPrevio;
	
	/** Progreso de avance */
	private String progreso = null;
	
	/** Nombre del proceso arrancado */
	private String process;
	
	/** Informacion del resumen de carga */
	private ResumenVO resumen;
	
	private Boolean showLink = Boolean.TRUE;
	
	/** Paginador de registros de resumen de errores */
	private PaginaVO pagina;
	
	/** Archivo subido por el usuario */
	private UploadedFile archivo;
	
	private String navHome;
	
    /** Servicio para el acceso a file transfer */
	private FileTransferService fileTransferService;
	
	/** Servicio para el bloqueo y cordinacion del servicio */
	private FileUploadService fileUploadService = null;

	/** Servicio para consulta de c_propiedades_dali */
	private PropiedadesDaliService propiedadesDaliService = null;

	/** Encolador del protocolo para el controler */
	private IEncoladorProtocoloFinanciero encolador;
	
	/** Informacion del proceso de carga actual */
	private  ProcessInfoVO processInfo = new ProcessInfoVO();
	
	private String id;
	
	/** Indica si se espera una confirmacion del usuario */
	private boolean esperaConfirmacion = false;
	
	private String folio;
	
	/** Conjunto de isos Firmados */
	private List<String> isosFirmados = null;
	
	/** Conjunto de isos sin firmar */
	private List<String> isosSinFirmar = null;
	
	/** Conjunto de hash de isos */
	private List<String> hashIso = null;
	
	/** Total de operaciones a firmar */
	private int totalOperaciones = 0;
	
	
	/** Utileria para la firma digital */
	private CifradorDescifradorBlowfish cdb = new CifradorDescifradorBlowfish();
	
	/** Mapa de parametros de request */
	Map<String,String[]> params = null;
	
	/** Encabezado de los registros en el  */
	private List<CampoArchivoVO> encabezado = null;
	
	/** Lista con los VO de traspaso libre pago o traspaso contra pago con la informacion de los registros del archivo */
	private List<Object> listaObjetosDatosOperacion;
	
	/** Indica si se presenta el applet y la logica para firmar operaciones */
	private boolean usuarioDebeFirmarOperacion;
	
	/** Objeto para almacenar el archivo del file transfer */
	private ArchivosFileTransfer archivosFileTransfer = null;
	
	private ExecutorService executor = null;
	
	private FutureTask<String> futureTask = null;
    
	private Map<String, Object> datosAdicionales;
	
	/** Lista con los tipos de archivos aceptados (content-type) por este file transfer */
	public static final List<String> LISTA_TIPOS_ARCHIVOS_ACEPTADOS = new ArrayList<String>();
	
	/** Cadena con el contenido del archivo */
	//private String contenidoArchivo;
	
	private static final String TIPO_PROCESO__TREF = "TR";
	
	private String MENSAJE_FIRMA = "Usted No tiene habilitada la facultad de firma digital.";
	
	private boolean primeraVez;
	static {
		LISTA_TIPOS_ARCHIVOS_ACEPTADOS.add("text/plain");
	}
	
	/////////////////////////// SECCION DE METODOS ///////////////////////////
	
	public FileTransferTraspasoController() {
		super();
		this.primeraVez = true;
	}

	/**
	 * Consulta los detalles de los registros cargados y validados para la confirmacion del usuario
	 * @return null;
	 */
	public String getShowDetalleErrores() {
        try {
            AgenteVO agenteFirmado = new AgenteVO();
            agenteFirmado.setId(getInstitucionActual().getClaveTipoInstitucion());
            agenteFirmado.setFolio(getInstitucionActual().getFolioInstitucion());
            
	        resumenPrevio = fileTransferService.muestraInformacion(agenteFirmado, process, true, pagina);
	        
	        if(resumenPrevio != null) {
		        resumenPrevio = agregaMensajeSinError(resumenPrevio);
		        pagina = resumenPrevio.getPaginaVO();
	        }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            release();
            return UtilConstantes.ERROR;
        }
        
        return null;
    }
	
	public String getValidaFacultadFirma() {
		logger.info("getValidaFacultadFirma" + this.primeraVez);
		
		if(this.primeraVez && TIPO_PROCESO__TREF.equals(process) && !isUsuarioConFacultadFirmar()) {
			FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, MENSAJE_FIRMA, MENSAJE_FIRMA));
		}
		
		this.primeraVez = false;
		
		return null;
	}
	
	/**
	 * Recupera la informacion del proceso que esta actualmente corriendo para mostrar la pantalla por primera vez.
	 * @return null
	 */
    public String getInit() {
    	inicializarPaginacion();
    	setId(getInstitucionActual().getClaveTipoInstitucion());
        setFolio(getInstitucionActual().getFolioInstitucion());
        usuarioDebeFirmarOperacion = isUsuarioConFacultadFirmar();
        
    	archivosFileTransfer = new ArchivosFileTransfer();
    	archivosFileTransfer.setUsuario(obtenerUsuarioSesion().getClaveUsuario());
    	archivosFileTransfer.setIdInstitucion(getInstitucionActual().getId());
        
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
        return null;
    }
    
    /**
     * Inicializa los objetos de paginacion
     */
    private void inicializarPaginacion() {
    	pagina = new PaginaVO();
    	paginacion.setRegistrosPorPagina(5);
    	pagina.setRegistrosXPag(paginacion.getRegistrosPorPagina());
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
     * Verifica si el proceso que esta corriendo corresponde al usuario actual
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
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,mensajeError,mensajeError));
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
    		logger.error(e.getMessage(), e);
    		release();
    	}
    	
    	return navHome;
    }

	/**
	 * Libera el lock del usuario
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
     * Recupera el usuario que esta actualmente cargando un archivo
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
     * Recupera el detalle de la informacion que se esta cargando actualmente o que espera confirmacion.
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
            resumen = fileTransferService.obtieneResumen(agenteFirmado,process);
            showLink = new Boolean(!resumen.getTotalError().equals(new Integer(0)));
            processInfo =  fileUploadService.getProcessInfo(processInfo);
            if( processInfo != null){
            	if( processInfo.getStatus().equals(ProcessInfoVO.CARGANDO) ||
            		processInfo.getStatus().equals(ProcessInfoVO.GUARDANDO) ||
            		processInfo.getStatus().equals(ProcessInfoVO.VALIDANDO) ||
            		processInfo.getStatus().equals(ProcessInfoVO.PROCESANDO) ){
            		return navHome;
            	}
            	else {
            		return getResumenPrevioCarga();
            	}
            }
        } catch (Exception e) {
            mensajeError = e.getMessage();
        	e.printStackTrace();
            logger.error(e.getMessage(), e);
            release();
            //return UtilConstantes.ERROR;
        }
        
        return navHome;
    }
    
    /**
     * Consulta el resumen de la validaciones aplicadas y el resultado de estas a la ultima carga del archivo del usuario
     * @return
     */
    @SuppressWarnings("unchecked")
	public String getResumenPrevioCarga(){
    	try {
	    	AgenteVO agenteFirmado = new AgenteVO();
	        agenteFirmado.setId(getId());
	        agenteFirmado.setFolio(getFolio());
	        agenteFirmado.setFirmado(true);
	        resumenPrevio = fileTransferService.muestraInformacion(agenteFirmado, process, false, pagina);
	        
	        if(resumenPrevio != null) {
		        resumenPrevio = agregaMensajeSinError(resumenPrevio);
		        pagina = resumenPrevio.getPaginaVO();
		        
		        if(pagina != null && pagina.getRegistros().size()>0){
		        	RegistroProcesadoVO primerRegistro = (RegistroProcesadoVO)pagina.getRegistros().get(0);
		        	encabezado = primerRegistro.getDatos();
		        	if(paginacion.getTotalResultados() <= 0) {
		        		incializarEstadoPaginacion(pagina.getTotalRegistros());
		        	}
		        }else {
		        	inicializarPaginacion();
		        }
	        }
	        
	        return navHome;
    	} catch (Exception e) {
    		mensajeError = e.getMessage();
    		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(mensajeError));
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            release();
            return UtilConstantes.ERROR;
    	}
    }
    
    /**
     * Agrega los mensajes de error al objeto que contiene los resultados
     * @param p Objeto con los datos de error
     * @return Mismo objeto con los datos de error
     */
    @SuppressWarnings("unchecked")
	public TotalesProcesoVO agregaMensajeSinError(TotalesProcesoVO p){
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
    public void cancelar(ActionEvent ev){
    	try{
    		AgenteVO agenteFirmado = new AgenteVO();
            agenteFirmado.setId(getId());
            agenteFirmado.setFolio(getFolio());
            agenteFirmado.setFirmado(true);
            
    		fileTransferService.cancelaProceso(agenteFirmado, process);
    		
    		getDetalle();
    	} catch (Exception e) {
    		mensajeError = e.getMessage();
    		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(mensajeError));
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            
		} finally{
			release();
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
	public void procesar(ActionEvent e){
    	try {
    		//Si el usuario debe firmar operaciones.
    		if(isUsuarioConFacultadFirmar()) {    			
    			//Valida la vigencia del certificado - 18-Agosto-2014 Pablo Balderas
    			validarVigenciaCertificado();
    		}
	    	mensajeError = null;
	    	processInfo.setUsuario(getId() + getFolio());
	    	processInfo.setIdProceso(getId() + getFolio()+process);
	        //Generar firmas
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
			mostrarBotonDetenerProceso = false;
	        datosAdicionales = new HashMap<String, Object>();
	        datosAdicionales.put(SeguridadConstants.USR_CREDENCIAL,
	                (String) FacesContext.getCurrentInstance()
	                        .getExternalContext().getSessionMap()
	                        .get(SeguridadConstants.TICKET_SESION));
	        datosAdicionales.put(SeguridadConstants.USUARIO_SESION, 
	        		obtenerUsuarioSesion().getClaveUsuario());
	        
	        logger.trace("datosAdicionales: " + datosAdicionales);
	        executor = Executors.newSingleThreadExecutor();
			futureTask = 
				new FutureTask<String>(new Callable<String>() {
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

    /**
     * Atiende la peticion del usuario para iniciar el proceso de carga de archivo de operaciones.
     * @param e
     */
    public void uploadFile(ActionEvent e) {
    	mensajeError = null;
        boolean errors = false;
        
        setId(getInstitucionActual().getClaveTipoInstitucion());
        setFolio(getInstitucionActual().getFolioInstitucion());
        
        // SOLO PARA TRANSFERENCIAS
        if(TIPO_PROCESO__TREF.equals(process) && !isUsuarioConFacultadFirmar()) {
			 
			FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, MENSAJE_FIRMA, MENSAJE_FIRMA));
			return;
		}
        
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

    	// Validar limite de registros en la carga
    	int cantRegistros = 0;
    	try {
			cantRegistros = this.readFileNumReg();
		} catch (IOException e1) {
			logger.error(e1.getMessage(), e1);
            mensajeError = "Ocurrio un error al validar el numero de registros en el archivo";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, mensajeError,mensajeError));
            return;
		}

		int limiteMax = propiedadesDaliService.obtenerLimMaxRegFileTransfer(FileTransferConstantes.PARAMETRO_MAXIMO_REG_FT_PORTAL);
		boolean continuarCarga = limiteMax >= cantRegistros;
		if (!continuarCarga) {
    		addMessageFromProperties(
				"msgArchivoExcedeNumeroRegistros", 
				new Object[]{limiteMax}, 
				FacesMessage.SEVERITY_ERROR);
    		return;
		}

        
        if(processInfo==null){
        	processInfo = new ProcessInfoVO();
        }
        processInfo.setUsuario(getId() + getFolio());
        processInfo.setIdProceso(getId() + getFolio()+process);
        processInfo.setAbort('F');
        processInfo.setPorcentajeTerminado(new BigDecimal(0));
        processInfo.setStatus(ProcessInfoVO.CARGANDO);
        processInfo.setUsuario(getId()+getFolio());
        Boolean candado = fileUploadService.getLock(processInfo);

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
        
        getDetalle();

        executor = Executors.newSingleThreadExecutor();
		futureTask = new FutureTask<String>(new Callable<String>() {
				public String call() {
		        	run();
		        	return null;
		       }});
		executor.execute(futureTask);
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
     * Le indica al proceso de poll que se detenga la siguiente vez que pase por la verificacion
     * @return
     */
    public String getDetenerPoll() {
    	detenerSiguienteVuelta = true;
    	
    	getInit();
    	return null;
    }
    
    /**
     * Funcion ejecutada en cada vuelta del monitoreo del porceso de file transfer
     * @param e
     */
    public void pollEstado(ActionEvent e) { }

    
    /**
     * Parsea el archivo cargado y retorna el numero de registros
     * 
     * @return numero de registros
     * @throws IOException 
     */
	private int readFileNumReg() throws IOException {
        InputStreamReader ir = null;
        BufferedReader br = null;
        //Inicializa la variable
        String contenido = new String();
        ArrayList<String> arlArchivo = new ArrayList<String>();
        try {
            ir = new InputStreamReader( archivo.getInputStream() );
            br = new BufferedReader(ir);

            String linea = null;
            linea = br.readLine();
            while( linea != null ) {
                if( StringUtils.isNotBlank(linea.trim())) {
                    arlArchivo.add(linea);
                    contenido = contenido.concat(linea + "\n");
                }
                linea = br.readLine();
            }
        } finally {
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
        
        return arlArchivo.size();
    }

    
    /**
     * Parsea el archivo cargado y retorna los resultados como un arreglo de String donde cada elemento es un renglon leido del archivo
     * @param processId Identificador del proceso
     * @param processInfo Objeto de informacion del proceso
     * @return Lista con las lineas del archivo leidas
     * @throws IOException 
     */
	private ArrayList<String> readFile( String processId, ProcessInfoVO processInfo ) throws IOException {
        InputStreamReader ir = null;
        BufferedReader br = null;
        //Inicializa la variable
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
                processInfo =  fileUploadService.getProcessInfo(processInfo);
                if( processInfo == null ){ break;}
                porcTerminado = Math.min(porcTerminado, 100);
                processInfo.setPorcentajeTerminado(new BigDecimal( porcTerminado ));
                
                if( (porcTerminado % 10) == 0 ){
                	fileUploadService.updateProcessInfo(processInfo);
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
    private Double getProcentajeTerminado( int total, int actual ){
        if( actual < 0 ){
            return 100.00;
        }
        double porcentaje = (double)actual *(100.00) /  (double) total;
        return  new Double(porcentaje);
    }
    
    /**
     * Inicia la validacion o envio de operaciones del file transfer.
     * Esta funcion se ejecuta en un hilo en el servidor mientras que la pantalla regresa a dar una respuesta al usuario
     * y la pantalla monitorea la actividad de esta funcion mediante la comparticion de datos del controller y en la BD
     */
    @SuppressWarnings("unchecked")
	public void run() {
        try {
        	processInfo.setUsuario(getId() + getFolio());
            processInfo.setIdProceso(getId() + getFolio()+process);
            processInfo = fileUploadService.getProcessInfo(processInfo);
            AgenteVO agenteFirmado = new AgenteVO();
            agenteFirmado.setId(getId());
            agenteFirmado.setFolio(getFolio());
            int totalRegistros = 0;
            //Se carga el archivo
            if( processInfo.getStatus().equals(ProcessInfoVO.CARGANDO) ){
                ArrayList<String> arlArchivo = readFile(getId() + getFolio() + process,processInfo);
                totalRegistros = arlArchivo.size();
                processInfo = fileUploadService.getProcessInfo(processInfo);
                processInfo.setStatus(ProcessInfoVO.GUARDANDO);
                fileUploadService.updateProcessInfo(processInfo);
                processInfo.setPorcentajeTerminado(new BigDecimal( 0 ));
                int registroActual = 0;
                while( registroActual >= 0 )  {
                    registroActual = fileTransferService.almacenaInformacion(agenteFirmado, process, arlArchivo, registroActual, processInfo.getUsuario());
                    processInfo = fileUploadService.getProcessInfo(processInfo);
                    if( processInfo == null ){ return;}
                    processInfo.setPorcentajeTerminado(new BigDecimal(getProcentajeTerminado(totalRegistros, registroActual )));
                    fileUploadService.updateProcessInfo(processInfo);
                }
                processInfo.setStatus(ProcessInfoVO.VALIDANDO);
                processInfo.setPorcentajeTerminado(new BigDecimal( 0 ));
                fileUploadService.updateProcessInfo(processInfo);
                registroActual = 0;
                while( registroActual >= 0 ) {
                	BigInteger idBoveda = new BigInteger("1");
                    registroActual = fileTransferService.validaInformacion(agenteFirmado, process, idBoveda, registroActual);
                    processInfo = fileUploadService.getProcessInfo(processInfo);
                    if( processInfo == null ){ return;}
                    processInfo.setPorcentajeTerminado(new BigDecimal(getProcentajeTerminado(totalRegistros, registroActual )));
                    fileUploadService.updateProcessInfo(processInfo);
                }
                processInfo.setStatus(ProcessInfoVO.CONFIRMACION);
                processInfo.setPorcentajeTerminado(new BigDecimal( 0 ));
                fileUploadService.updateProcessInfo(processInfo);
                esperaConfirmacion = true;
            }
            //Se procesa el archivo
            else if( processInfo.getStatus().equals(ProcessInfoVO.PROCESANDO)) {
        		processInfo.setPorcentajeTerminado(new BigDecimal( 0 ));
		        fileUploadService.updateProcessInfo(processInfo);
		        // Para sacar todos los registros (en caso de que debido a la paginacion no vengan todos)
		        resumenPrevio =  fileTransferService.muestraInformacion(agenteFirmado, process, false, new PaginaVO());
		        
		        logger.info("usuarioDebeFirmarOperacion >>>>> " + usuarioDebeFirmarOperacion);
            	// Para checar si se debe firmar la operacion
            	if(usuarioDebeFirmarOperacion) {
	            	//crear ISOs para que se firmen las operaciones
	            	recuperarInformacionFirmas();
	            	if((isosFirmados == null || isosFirmados.isEmpty()) && (isosSinFirmar == null || isosSinFirmar.isEmpty())){
	            		isosFirmados = null;
	            		Map<String, Object> resultado = encolador.obtenerISOs(resumenPrevio);
	            		isosSinFirmar = (List<String>)resultado.get(IEncoladorProtocoloFinanciero.ID_LISTA_ISOS);
	            		listaObjetosDatosOperacion = (List<Object>)resultado.get(IEncoladorProtocoloFinanciero.ID_LISTA_OBJETOS);
	            	}
            	}
            	else {
            		// Se crean listas vacias solo para que pase la validacion
            		isosFirmados = new ArrayList<String>();
            		isosSinFirmar = new ArrayList<String>();
            		Map<String, Object> resultado = encolador.obtenerISOs(resumenPrevio);
            		listaObjetosDatosOperacion = (List<Object>)resultado.get(IEncoladorProtocoloFinanciero.ID_LISTA_OBJETOS);
            	}
            	
            	if((usuarioDebeFirmarOperacion && isosFirmados != null && !isosFirmados.isEmpty()) || !usuarioDebeFirmarOperacion){
            		
                    processInfo.setPorcentajeTerminado(new BigDecimal( 25 ));
                    fileUploadService.updateProcessInfo( processInfo);
                    if (encolador instanceof EncoladorDepositosEfectivo) {
                        ((EncoladorDepositosEfectivo) encolador).setDatosAdicionales(datosAdicionales);
                        ((EncoladorDepositosEfectivo) encolador).setFirmaRequerida(usuarioDebeFirmarOperacion);
                    }
                    else if(encolador instanceof EncoladorMDTraspasos) {
                        ((EncoladorMDTraspasos) encolador).setDatosAdicionales(datosAdicionales);
                    }
                    else if(encolador instanceof EncoladorMCTraspasos) {
                        ((EncoladorMCTraspasos) encolador).setDatosAdicionales(datosAdicionales);
                    }
                    
                    Map idsOperacionesFirmadas = null;
                    
                    if(encolador instanceof EncoladorTref) {
                    	idsOperacionesFirmadas = ((EncoladorTref) encolador).firmayEncola(resumenPrevio, listaObjetosDatosOperacion, isosFirmados, datosAdicionales);
                    }else {
                    	idsOperacionesFirmadas = encolador.firmayEncola(resumenPrevio, listaObjetosDatosOperacion, isosFirmados);
                    }
					
                    processInfo.setPorcentajeTerminado(new BigDecimal( 75 ));
                    fileUploadService.updateProcessInfo(processInfo);
    		        
                    fileTransferService.grabaInformacion(agenteFirmado, process, (HashMap) idsOperacionesFirmadas);
    		        
    		        fileUploadService.releaseLock(processInfo);
    		        getDetalle();
    		        isosFirmados = null;
    		        isosSinFirmar = null;
    		        paginacion = new EstadoPaginacionDTO();
    		        inicializarPaginacion();
    		        //Guarda el file transfer procesado
    		        guardarFileTransfer(resumenPrevio.getContenidoArchivo());
            	}else {
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
        catch (Exception e) {
           logger.error(e.getMessage(), e);
           e.printStackTrace();
           
           mensajeError = e.getMessage();
           release();
           isosFirmados = null;
	        isosSinFirmar = null;
        }
        finally{
        	polling = false;
        }
    }
    
    /**
     * Guarada el file transfer en la base de datos
     */
    private void guardarFileTransfer(String contenidoArchivo) {
    	archivosFileTransfer.setFechaCarga(new Date());
    	archivosFileTransfer.setRegistros(contenidoArchivo);
    	fileTransferService.guardarArchivoFileTransfer(archivosFileTransfer);
    }
    
    
    /**
     * Recupera la informacion de los campos ocultos de firmas electronicas
     */
    private void recuperarInformacionFirmas() {
    	logger.debug("Params: " + params.keySet());
    	
    	String[] numeroSerieArray = params.get("numeroSerie");
    	
    	if(numeroSerieArray != null) { // vienen datos de firma
    		isosFirmados = new ArrayList<String>();
    		String numeroSerie = numeroSerieArray[0];
    		
    		// Agregar numero de serie
    		if(this.datosAdicionales == null) this.datosAdicionales = new HashMap<>();
    		datosAdicionales.put(SeguridadConstants.SERIE, numeroSerie);
    		
    		String localIsoSinfirma=null;
    		for(int i=1;i<=totalOperaciones;i++){
    				localIsoSinfirma=params.get("isoSinFirmar"+i)[0].replace("\r\n","\n");
    				logger.info(localIsoSinfirma);
    				isosFirmados.add(localIsoSinfirma);
    		}
    		isosSinFirmar = new ArrayList<String>();
    	}
       		
	}
    
    /**
     * Indica si ya se firmó cada ISO en pantalla.
     * 
     * @return <code>true</code> si se firmó el ISO en pantalla.
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
     * Activa la bandera de polling
     * 
     * @param ae Evento generado durante la solicitud
     */
	public void activaPoll(ActionEvent ae){
    	this.setPolling(true);
    	
    }
    
	/**
	 * Desactiva la bandera de polling
	 */
    public void notPoll(){
    	this.setPolling(false);
    }
    
    /**
	 * Invoca las funciones de navegacion de pagina de la consulta principal de
	 * la pantalla
	 * 
	 * @param e Evento generado durante la solicitud
	 */
	public void navegarPorResultados(ActionEvent e) {
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
    public void actualizarPaginacionRegistro(ActionEvent e) {
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

	public IEncoladorProtocoloFinanciero getEncolador() {
		return encolador;
	}

	public void setEncolador(IEncoladorProtocoloFinanciero encolador) {
		this.encolador = encolador;
	}

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

	/**
	 * @return the propiedadesDaliService
	 */
	public PropiedadesDaliService getPropiedadesDaliService() {
		return propiedadesDaliService;
	}

	/**
	 * @param propiedadesDaliService the propiedadesDaliService to set
	 */
	public void setPropiedadesDaliService(PropiedadesDaliService propiedadesDaliService) {
		this.propiedadesDaliService = propiedadesDaliService;
	}

}