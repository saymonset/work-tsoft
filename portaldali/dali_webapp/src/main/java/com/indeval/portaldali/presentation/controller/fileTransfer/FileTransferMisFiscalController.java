/**
 * Bursatec - Portal DALI
 * Copyrigth (c) 2014 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.presentation.controller.fileTransfer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;

import org.apache.myfaces.custom.fileupload.UploadedFile;

import com.bursatec.seguridad.presentation.constants.SeguridadConstants;
import com.indeval.portaldali.middleware.constantes.FileTransferConstantes;
import com.indeval.portaldali.middleware.dto.filetransfer.RegistroFileTransDto;
import com.indeval.portaldali.middleware.dto.util.EstadoPaginacionDTO;
import com.indeval.portaldali.middleware.dto.util.EstadoPaginacionExtended;
import com.indeval.portaldali.middleware.services.common.PropiedadesDaliService;
import com.indeval.portaldali.middleware.services.filetransfer.FileTransferMisFisService;
import com.indeval.portaldali.middleware.services.filetransfer.ResumenVO;
import com.indeval.portaldali.middleware.services.filetransfer.TotalesProcesoVO;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.BusinessDataException;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.modelo.PaginaVO;
import com.indeval.portaldali.middleware.services.util.FileUploadService;
import com.indeval.portaldali.middleware.services.util.vo.ProcessInfoVO;
import com.indeval.portaldali.presentation.controller.common.ControllerBase;
import com.indeval.portaldali.presentation.controller.fileTransfer.encolador.EncoladorMiscelaneaFiscal;
import com.indeval.portaldali.presentation.controller.fileTransfer.encolador.EncoladorPFI;
import com.indeval.portaldali.presentation.util.CifradorDescifradorBlowfish;
import com.indeval.portaldali.presentation.util.FileTransferUtil;
import com.indeval.portaldali.presentation.util.PaginadorUtil;
import com.indeval.protocolofinanciero.api.ProtocoloFinancieroException;

/**
 * Controller para realizar las transferencias de archivos para Miscelanea Fiscal.
 * 
 * @author Pablo Balderas.
 */
@SuppressWarnings("unused")
public class FileTransferMisFiscalController extends ControllerBase {
	
	/** Nombre del proceso arrancado */
	private String proceso;
	
	/** Archivo a procesar */
	private UploadedFile archivo = null;
	
	/** Resumen de la última operación */
	private ResumenVO resumen = new ResumenVO();
	
    /** Servicio para el acceso a file transfer */
	private FileTransferMisFisService fileTransferMisFisService;
	
	/** Servicio para el bloqueo y cordinación del servicio */
	private FileUploadService fileUploadService = null;
	
	/** Servicio para consulta de c_propiedades_dali */
	private PropiedadesDaliService propiedadesDaliService = null;
	
	/** Navegación */
	private String navHome;
	
	/** Agente firmado en el sistema */
    private AgenteVO agenteFirmado;
    
    /** Resultado de la operacion de file transfer */
    private TotalesProcesoVO totalProceso;
    
    /** Estado de la paginación */
    private EstadoPaginacionExtended edoPaginacion;
	
    /** Lista de registros procesados */
    private List<RegistroFileTransDto> registrosProcesados = null;
    
    /** Lista con los registros con error para el detalle de los mismos */
    private List<RegistroFileTransDto> registrosConError = null;
    
    /** Indica si debe mostrar el resumen de la operación. */
    private boolean mostrarResumen;

	/** Indica si ya se proceso el archivo */
    private boolean archivoProcesado;
    
    /** Indica si se muestra el link para los registros con error. */
    private boolean mostrarLink;
    
    /** Nombre del parametro que indica la navegación en la paginación */
    private final String PARAMETRO_NAVEGACION = "navegacion";

	/** Isos firmados */
	private List<String> isosFirmados = null;
	
	/** Isos sin firmar */
	private List<String> isosSinFirmar = null;
	
	/** Lista con las operaciones a firmar y encolar */
	private List<Object> listaTlps = null;
	
	/** Lista ligada a los tlps que indica si es compra o venta */
	private List<Object> compraVenta = null;
	
	/** Hash de isos */
	private List<String> hashIso = null;
	
	/** Total de operaciones a firmar */
	private int totalOperaciones = 0;
	
	/** Utileria para la firma digital */
	private CifradorDescifradorBlowfish cdb = new CifradorDescifradorBlowfish();
    
	/** Encolador para miscelanea fiscal */
	private EncoladorMiscelaneaFiscal encoladorMiscelaneaFiscal;
	
	/** Lista con los tipos de archivos aceptados (content-type) por este file transfer */
	private static final List<String> LISTA_TIPOS_ARCHIVOS_ACEPTADOS = new ArrayList<String>();
	static {
		LISTA_TIPOS_ARCHIVOS_ACEPTADOS.add(FileTransferConstantes.CONTENT_TYPE_TEXTO_PLANO);
	}
	
	/** Indica si se debe mostrar el botoón cancelar en la pantalla  */
	private boolean mostrarBtnCancelar = true;
	
	/**
	 * Recupera la informacion del proceso que esta actualmente corriendo para mostrar la pantalla por primera vez.
	 * @return null
	 */
    public String getInit() {
    	//Inicializa el agente firmado
    	agenteFirmado = new AgenteVO();
        agenteFirmado.setId(getInstitucionActual().getClaveTipoInstitucion());
        agenteFirmado.setFolio(getInstitucionActual().getFolioInstitucion());
        agenteFirmado.setFirmado(true);

        if(edoPaginacion == null) {
        	edoPaginacion = new EstadoPaginacionExtended();
        	edoPaginacion.setRegistrosPorPagina(5);				
        	edoPaginacion.setNumeroPagina(1);
        	edoPaginacion.setTotalPaginas(0);
        	edoPaginacion.setTotalResultados(0);
        	edoPaginacion.getMetaDatos().put("reiniciarPaginacion", Boolean.TRUE);
        }
        
        if(registrosProcesados == null) {        	
        	registrosProcesados = new ArrayList<RegistroFileTransDto>();
        }
        
        if(!archivoProcesado) {        	
        	initResumenOperacion();
        }
        

        return null;
    }
    
    /**
     * Inicializa el detalle de los errores en el proceso de file transfer.
     * @return null
     */
    public String getInitDetalleErrores() {
    	AgenteVO agenteFirmadoAgenteVO = new AgenteVO();
        agenteFirmadoAgenteVO.setId(getInstitucionActual().getClaveTipoInstitucion());
        agenteFirmadoAgenteVO.setFolio(getInstitucionActual().getFolioInstitucion());
    	registrosConError = fileTransferMisFisService.obtenerErroresFileTransfer(agenteFirmadoAgenteVO, proceso);
    	return null;
    }
    
    /**
     * Inicializa el resumen de la operación realizada.
     */
    private void initResumenOperacion() {
        //Busca la última operación realizada
        resumen = fileTransferMisFisService.obtenerResumenFileTransfer(agenteFirmado, proceso);
        
        //Si existe un proceso de file transfer pendiente
    	ProcessInfoVO processInfo = new ProcessInfoVO();
    	processInfo.setUsuario(
    		getInstitucionActual().getClaveTipoInstitucion() + 
    		getInstitucionActual().getFolioInstitucion());
        processInfo.setIdProceso(
        	getInstitucionActual().getClaveTipoInstitucion() + 
        	getInstitucionActual().getFolioInstitucion() + proceso);
        processInfo =  fileUploadService.getProcessInfo(processInfo);
        
        if(processInfo != null) {
        	totalProceso = 
        		fileTransferMisFisService.obtenerInformacionFileTransfer(agenteFirmado, proceso);
        	if(totalProceso != null && totalProceso.getPaginaVO() != null 
        			&& totalProceso.getPaginaVO().getRegistros() != null) {
        		inicializarPaginacion(totalProceso.getPaginaVO().getRegistros().size());
        	}
        	archivoProcesado = true;
        	mostrarResumen = false;
        }
        else {
        	mostrarLink = (resumen.getTotalError() != null && resumen.getTotalError() != 0);
        	archivoProcesado = false;
        	mostrarResumen = true;
        }
    }
    
    
    /**
     * Realiza la carga y validación del archivo en el sistema.
     * @param e Evento generado
     */
    public String cargarArchivo() {
        boolean errors = false;
        //Valida si el archivo no es nulo
        if( archivo == null ) {	
        	addMessageFromProperties("msgArchivoRequerido", FacesMessage.SEVERITY_ERROR);
        	errors = true;
        }
        else if(!LISTA_TIPOS_ARCHIVOS_ACEPTADOS.contains(archivo.getContentType())) {
        	addMessageFromProperties("msgArchivoTipoTextoPlano", FacesMessage.SEVERITY_ERROR);
        	errors = true;
        }
        if(!errors && (agenteFirmado.getId() == null || agenteFirmado.getId().equals("") ||
        		agenteFirmado.getFolio() == null || agenteFirmado.getFolio().equals(""))) {
            addMessageFromProperties("msgUsuarioLogeado", FacesMessage.SEVERITY_ERROR);
            errors = true;
        }
        if( errors ) {
        	archivoProcesado = false;
        	mostrarResumen = true;
        }
        else {        	
        	//Se lee el contenido del archivo
        	List<String> informacionArchivo = FileTransferUtil.leerArchivo(archivo);
        	//Valida que el archivo no sea nulo o vacio
        	if(informacionArchivo != null && !informacionArchivo.isEmpty()) {
            	//Valida si el archivo tiene mas de 1000 registros
            	//if(FileTransferConstantes.MAXIMO_REGISTROS_MISCELANEA_FISCAL >= informacionArchivo.size()) {
        		
        		int limiteMax = propiedadesDaliService.obtenerLimMaxRegFileTransfer(FileTransferConstantes.PARAMETRO_MAXIMO_REG_FT_PORTAL);
        		boolean continuarCarga = limiteMax >= informacionArchivo.size();
        		if (continuarCarga) {
            		try {
            			//Se procesa el archivo
            			totalProceso =
        					fileTransferMisFisService.registrarFileTransfer(
    							informacionArchivo, agenteFirmado, proceso, agenteFirmado.getId() + agenteFirmado.getFolio());
            			//Inicializa la paginacion
            			if(totalProceso != null && totalProceso.getPaginaVO().getRegistros() != null){        		
                    		inicializarPaginacion(totalProceso.getPaginaVO().getRegistros().size());
                    	}
            			//Indica que el archivo ya fue procesado
            			archivoProcesado = true;
            			mostrarResumen = false;
            			mostrarBtnCancelar = true;
            		}
            		catch(Exception e) {
            			addMessage(e.getMessage(), FacesMessage.SEVERITY_ERROR);
            		}
            	}
            	else {
            		addMessageFromProperties(
        				"msgArchivoExcedeNumeroRegistros", 
        				new Object[]{limiteMax}, 
        				FacesMessage.SEVERITY_ERROR);
            	}        		
        	}
        	else {
        		addMessageFromProperties("msgArchivoVacio", FacesMessage.SEVERITY_ERROR);
        	}
        }
        return navHome;
    }
    
    /**
     * Acción que procesa los registros correctos del archivo cargado.
     * @param actionEvent Evento generado por faces.
     */
    @SuppressWarnings("unchecked")
	public void procesarRegistros(ActionEvent actionEvent) {
    	
    	try {
    		//Valida si el candado esta activo y si existen registros a procesar
    		if(fileTransferMisFisService.validarRegistrosAProcesar(agenteFirmado, proceso)) {
        		//Datos adicionales para encolar las operaciones
        		HashMap<String, Object> datosAdicionales = new HashMap<String, Object>();
        		datosAdicionales.put(
        				SeguridadConstants.USR_CREDENCIAL,
        				(String) FacesContext.getCurrentInstance().getExternalContext().
        				getSessionMap().get(SeguridadConstants.TICKET_SESION));
        		
        		// Para checar si se debe firmar la operacion
        		if(isUsuarioConFacultadFirmar()) {
        			//Valida la vigencia del certificado - Pablo Balderas 05-Dic-2014
        			validarVigenciaCertificado();
        			//crear ISOs para que se firmen las operaciones
        			recuperarInformacionFirmas();
        			if((isosFirmados == null || isosFirmados.isEmpty()) && (isosSinFirmar == null || isosSinFirmar.isEmpty())){
        				isosFirmados = null;
        				Map<String, Object> resultado = 
        						encoladorMiscelaneaFiscal.obtenerISOs(agenteFirmado.getId(), agenteFirmado.getFolio(), proceso);
        				isosSinFirmar = (List<String>) resultado.get(EncoladorPFI.ID_LISTA_ISOS);
        				listaTlps = (List<Object>)resultado.get(EncoladorPFI.ID_LISTA_OBJ_PFI);
        				compraVenta = (List<Object>) resultado.get(EncoladorPFI.ID_LISTA_REGISTROS);
        			}
        		}
        		else {
        			// Se crean listas vacias solo para que pase la validacion
        			isosFirmados = new ArrayList<String>();
        			isosSinFirmar = new ArrayList<String>();
        			Map<String, Object> resultado = 
        					encoladorMiscelaneaFiscal.obtenerISOs(agenteFirmado.getId(), agenteFirmado.getFolio(), proceso);
        			listaTlps = (List<Object>)resultado.get(EncoladorPFI.ID_LISTA_OBJ_PFI);
        			compraVenta = (List<Object>) resultado.get(EncoladorPFI.ID_LISTA_REGISTROS);
        		}
        		
        		if(isosFirmados != null && (isosSinFirmar !=null && isosSinFirmar.size()==0)){
        			Map<Integer, Boolean> resultado = 
        				encoladorMiscelaneaFiscal.firmayEncola(listaTlps, isosFirmados, compraVenta, datosAdicionales);
        			//Actualiza el estado de los registros procesados.
        			fileTransferMisFisService.actualizarRegistrosProcesadosPFI(agenteFirmado, proceso, resultado);
        			//Libera el candado de file transfer
        			ProcessInfoVO processInfo = new ProcessInfoVO();
        			processInfo.setUsuario(
        					getInstitucionActual().getClaveTipoInstitucion() + 
        					getInstitucionActual().getFolioInstitucion());
        			processInfo.setIdProceso(
        					getInstitucionActual().getClaveTipoInstitucion() + 
        					getInstitucionActual().getFolioInstitucion() + 
        					proceso);
        			fileUploadService.releaseLock(processInfo);
        			//Obtiene el resumen de la operación
        			resumen = fileTransferMisFisService.obtenerResumenFileTransfer(agenteFirmado, proceso);
        			addMessageFromProperties("msgProcesoTerminado", FacesMessage.SEVERITY_INFO);
        			listaTlps = null;
        			isosFirmados = null;
        			isosSinFirmar = null;
        			paginacion = new EstadoPaginacionDTO();
        			archivoProcesado = false;
        			initResumenOperacion();
        		}
        		else {
        			totalOperaciones = isosSinFirmar.size();
        			hashIso = new ArrayList<String>();
        			for (String iso : isosSinFirmar) {
        				hashIso.add(cdb.cipherHash(iso));
        			}
        		}
        		mostrarBtnCancelar = false;
    		}
    		else {
    			addMessageFromProperties("msgErrorProcesoFileTransfer", FacesMessage.SEVERITY_ERROR);
            	registrosProcesados = new ArrayList<RegistroFileTransDto>();
            	archivoProcesado = false;
            	getInit();
    		}
    	}
    	catch(BusinessException businessException) {
    		addMessage(businessException.getMessage(), FacesMessage.SEVERITY_ERROR);
    	}
    	catch(ProtocoloFinancieroException protocoloFinancieroException) {
    		addMessage(protocoloFinancieroException.getMessage(), FacesMessage.SEVERITY_ERROR);
    	}
    }
    
    /**
     * Accion que cancela el proceso de file transfer.
     * @param actionEvent Evento generado por faces.
     */
    public void cancelarFileTransfer(ActionEvent actionEvent) {
    	//Se invoca la cancelacion en el servicio de file transfer.
    	fileTransferMisFisService.cancelarFileTransfer(agenteFirmado, proceso);
    	//Limpia la paginación
    	edoPaginacion = null;
    	//Limpia los totales del proceso
    	totalProceso = null;
    	//Limpia la lista de registros procesados
    	registrosProcesados = null;
        //Indica que no hay archivos procesados
    	archivoProcesado =  false;
    	//Inicializa la pantalla
    	getInit();

    	isosFirmados = null;
    	isosSinFirmar = null;
    }
    
    /**
	 * Método para realizar la paginación por los resultados del procesamiento del archivo.
	 * @param actionEvent Evento generado durante la solicitud.
	 */
	public void navegarPorResultados(ActionEvent actionEvent) {
		String navegacion = (String) actionEvent.getComponent().getAttributes().get(PARAMETRO_NAVEGACION);
		PaginadorUtil.navegarPaginacion(navegacion, edoPaginacion);
		//Calcula la paginacion
		PaginadorUtil.calcularPaginacion(edoPaginacion);
		//Obtine la sublista de acuerdo a los parametros calculados
		obtenerSubLista(edoPaginacion.getIndice(), edoPaginacion.getSubindice());
	}

	/**
     * Metodo llamado al actualizar el numero de registros por pagina.
     * @param e Evento generado durante la solicitud
     */
	public void actualizarPaginacionRegistro(ActionEvent e) {
    	//Calcula la paginacion
		PaginadorUtil.calcularPaginacion(edoPaginacion);
		//Obtine la sublista de acuerdo a los parametros calculados
		obtenerSubLista(edoPaginacion.getIndice(), edoPaginacion.getSubindice());
    }
	
	/**
	 * Método que indica si puede mandar a procesar los registros del file transfer
	 * @return true si es posible procesar; false en caso contrario
	 */
	public boolean isProcesar() {
		return totalProceso != null && totalProceso.getRegistrosACargar() != null && totalProceso.getRegistrosACargar() > 0;
	}
	
	/**
	 * Método que da el tiempo en que permaneceran ocultos los botones de la pantalla.
	 * @return Tiempo en que permaneceran ocultos los botones de la pantalla.
	 */
	public String getTiempoDelayBotones() {
		return "60000";
	}
	
	
    /**
     * Método que indica si los ISO asociados a los registros ya fueron firmados.
     * @return true si los ISO ya fueron firmados; false en caso contrario.
     */
    public boolean isIsosFirmados() {
    	return isosSinFirmar != null && !isosSinFirmar.isEmpty();
    }
		
    /**
     * Recupera la informacion de los campos ocultos de firmas electronicas
     */
    @SuppressWarnings("unchecked")
	private void recuperarInformacionFirmas() {
    	Map<String,String[]> params = 
    		((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getParameterMap();
    	if(isosSinFirmar != null  &&  isosSinFirmar.size() >0 ) {
    		isosFirmados = new ArrayList<String>();
    		String numeroDeSerie=(String) params.get("numeroSerie")[0];
    		String localIsoSinfirma=null;
    		String localFirmaIso=null;
    		StringBuilder isoCompleto=null;    		
    		
    		for(int i=1;i<=totalOperaciones;i++){
//    			if(params.get("isoFirmado"+i) != null && params.get("isoFirmado"+i).length >= 1 
//    					&& params.get("isoFirmado"+i)[0] != null && params.get("isoFirmado"+i)[0].trim().length() > 0) {
    				isoCompleto = new StringBuilder();
    				localIsoSinfirma=params.get("isoSinFirmar"+i)[0].replace("\r\n","\n");
//    				localFirmaIso=params.get("isoFirmado"+i)[0].replace("\r\n","\n");
    				isoCompleto.append(localIsoSinfirma);
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
	 * Inicializa la paginación de los resultados.
	 */
	private void inicializarPaginacion(int numeroResultados) {
		if(edoPaginacion == null) {
			edoPaginacion = new EstadoPaginacionExtended();
		}
		edoPaginacion.setNumeroPagina(1);
		edoPaginacion.setRegistrosPorPagina(5);
		edoPaginacion.setTotalResultados(numeroResultados);
		actualizarPaginacionRegistro(null);
	}
    
    /**
     * 
     * @param inicio
     * @param fin
     */
	private void obtenerSubLista(int inicio, int fin) {
		registrosProcesados = new ArrayList<RegistroFileTransDto>();
		for(int i = inicio; i < fin; i++) {
			registrosProcesados.add(
				(RegistroFileTransDto) totalProceso.getPaginaVO().getRegistros().get(i));
		}
		
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
	 * @return the proceso
	 */
	public String getProceso() {
		return proceso;
	}

	/**
	 * @param proceso the proceso to set
	 */
	public void setProceso(String proceso) {
		this.proceso = proceso;
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
	 * @return the fileTransferMisFisService
	 */
	public FileTransferMisFisService getFileTransferMisFisService() {
		return fileTransferMisFisService;
	}


	/**
	 * @param fileTransferMisFisService the fileTransferMisFisService to set
	 */
	public void setFileTransferMisFisService(
			FileTransferMisFisService fileTransferMisFisService) {
		this.fileTransferMisFisService = fileTransferMisFisService;
	}


	/**
	 * @return the agenteFirmado
	 */
	public AgenteVO getAgenteFirmado() {
		return agenteFirmado;
	}


	/**
	 * @param agenteFirmado the agenteFirmado to set
	 */
	public void setAgenteFirmado(AgenteVO agenteFirmado) {
		this.agenteFirmado = agenteFirmado;
	}

	/**
	 * @return the totalProceso
	 */
	public TotalesProcesoVO getTotalProceso() {
		return totalProceso;
	}

	/**
	 * @param totalProceso the totalProceso to set
	 */
	public void setTotalProceso(TotalesProcesoVO totalProceso) {
		this.totalProceso = totalProceso;
	}

	/**
	 * @return the estadoPaginacionDTO
	 */
	public EstadoPaginacionExtended getEdoPaginacion() {
		return edoPaginacion;
	}

	/**
	 * @param estadoPaginacionDTO the estadoPaginacionDTO to set
	 */
	public void setEdoPaginacion(EstadoPaginacionExtended estadoPaginacionDTO) {
		this.edoPaginacion = estadoPaginacionDTO;
	}

	/**
	 * @return the archivoProcesado
	 */
	public boolean isArchivoProcesado() {
		return archivoProcesado;
	}

	/**
	 * @param archivoProcesado the archivoProcesado to set
	 */
	public void setArchivoProcesado(boolean archivoProcesado) {
		this.archivoProcesado = archivoProcesado;
	}

	/**
	 * @return the registrosProcesados
	 */
	public List<RegistroFileTransDto> getRegistrosProcesados() {
		return registrosProcesados;
	}

	/**
	 * @param registrosProcesados the registrosProcesados to set
	 */
	public void setRegistrosProcesados(
			List<RegistroFileTransDto> registrosProcesados) {
		this.registrosProcesados = registrosProcesados;
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
	 * @return the encoladorMiscelaneaFiscal
	 */
	public EncoladorMiscelaneaFiscal getEncoladorMiscelaneaFiscal() {
		return encoladorMiscelaneaFiscal;
	}

	/**
	 * @param encoladorMiscelaneaFiscal the encoladorMiscelaneaFiscal to set
	 */
	public void setEncoladorMiscelaneaFiscal(
			EncoladorMiscelaneaFiscal encoladorMiscelaneaFiscal) {
		this.encoladorMiscelaneaFiscal = encoladorMiscelaneaFiscal;
	}

	/**
	 * @return the mostrarLink
	 */
	public boolean isMostrarLink() {
		return mostrarLink;
	}

	/**
	 * @param mostrarLink the mostrarLink to set
	 */
	public void setMostrarLink(boolean mostrarLink) {
		this.mostrarLink = mostrarLink;
	}

	/**
	 * @return the registrosConError
	 */
	public List<RegistroFileTransDto> getRegistrosConError() {
		return registrosConError;
	}

	/**
	 * @param registrosConError the registrosConError to set
	 */
	public void setRegistrosConError(List<RegistroFileTransDto> registrosConError) {
		this.registrosConError = registrosConError;
	}

	/**
	 * @return the mostrarResumen
	 */
	public boolean isMostrarResumen() {
		return mostrarResumen;
	}

	/**
	 * @param mostrarResumen the mostrarResumen to set
	 */
	public void setMostrarResumen(boolean mostrarResumen) {
		this.mostrarResumen = mostrarResumen;
	}

	/**
	 * @return the listaTlps
	 */
	public List<Object> getListaTlps() {
		return listaTlps;
	}

	/**
	 * @param listaTlps the listaTlps to set
	 */
	public void setListaTlps(List<Object> listaTlps) {
		this.listaTlps = listaTlps;
	}

	/**
	 * @return the compraVenta
	 */
	public List<Object> getCompraVenta() {
		return compraVenta;
	}

	/**
	 * @param compraVenta the compraVenta to set
	 */
	public void setCompraVenta(List<Object> compraVenta) {
		this.compraVenta = compraVenta;
	}

	/**
	 * @return the mostrarBtnCancelar
	 */
	public boolean isMostrarBtnCancelar() {
		return mostrarBtnCancelar;
	}

	/**
	 * @param mostrarBtnCancelar the mostrarBtnCancelar to set
	 */
	public void setMostrarBtnCancelar(boolean mostrarBtnCancelar) {
		this.mostrarBtnCancelar = mostrarBtnCancelar;
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
