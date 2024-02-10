 package com.indeval.portalinternacional.presentation.controller.beneficiarios;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.myfaces.custom.fileupload.UploadedFile;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.bursatec.seguridad.vo.InstitucionVO;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService;
import com.indeval.portalinternacional.middleware.services.util.vo.ProcessInfoVO;
import com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario;
import com.indeval.portalinternacional.middleware.servicios.vo.CambioEstatusBeneficiario;
import com.indeval.portalinternacional.middleware.servicios.vo.ListStatusBeneficiario;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;

public class CargaStatusBeneficiarioController extends ControllerBase {  
    
    public static String REGISTRO_ACTUALIZADO = "Correcto";
    
    public static String REGISTRO_NO_ACTUALIZADO = "Error";
    
    public static String ERROR_NO_EXISTE_BENEFICIARIO = "No existe beneficiario con UOI: ";
    
    public static String ERROR_NO_EXISTE_ESTATUS = "No existe el estatus: ";
    
    public static String ERROR_CHANGE_STATUS = "com.indeval.portaldali.middleware.servicios.modelo.BusinessException: ";
    
    public static Integer INICIO_ROW = 0;
	
	/** Resumen: de la fecha de Carga del documento */
	private String fechaCarga;
	
	/** Resumen: de la fecha de Carga del documento */
	private String horaCarga;
	
	/** Registros cargados */
	private String registrosCargados;
	
	/** Registros con Error */
	private String registrosConError;
	
	/** Total de Registros */
	private String totalRegistros;
	
	/** Usuario responsable */
	private String usuarioResponsable;
	
	/**
	 * Archivo subido por el usuario
	 */
	private UploadedFile archivo;
	
	/**
	 * Informacion del proceso de carga actual
	 */
	private  ProcessInfoVO processInfo = new ProcessInfoVO();
	
	/** ID de la institucion */
	private String id;
	
	/** Folio de la institucion */
	private String folio;
	
	/** Mensaje de error a mostrar del metodo del thread */
	private String mensajeError;

	/** Lista con los tipos de archivos aceptados (content-type) por este file transfer */
	public static final String archivoAceptado = ".xls";
	
	private List<CambioEstatusBeneficiario> listaCargaEstatus;
	
    /** Servicio de Beneficiarios */
    private ControlBeneficiariosService controlBeneficiariosService;
    
	/** Representa los datos de la institucion actual */
	private InstitucionVO institucionVO = null;
	
	/**
     * Obtiene la institucion actual del participante.
     * 
     * @return VO con los datos de la institucion.
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
	 * Recupera la informacion del proceso que est&aacute; actualmente corriendo para mostrar la pantalla por primera vez.
	 * @return null
	 */
    public String getInit() {
        return null;
    }
    
    /**
     * Atiende la peticion del usuario para iniciar el proceso de carga de archivo de operaciones.
     * @param e
     */
    public void uploadFile(ActionEvent e) {
    	boolean errors = false;
    	mensajeError = null;
    	String fileNameAux = null;
    	if( archivo != null ) {
    		fileNameAux = archivo.getName().substring(archivo.getName().length()-4, archivo.getName().length());
    	}
        
        try {
            setId(getAgenteFirmado().getId());
            setFolio(getAgenteFirmado().getFolio());
            if( archivo == null ) {
            	mensajeError = "El archivo para cambio de estatus de beneficiarios es requerido";
            	FacesContext.getCurrentInstance().addMessage(null,
                         new FacesMessage(FacesMessage.SEVERITY_WARN, mensajeError,mensajeError));
                errors = true;
            }
        	else if(!archivo.getName().contains(archivoAceptado) || !fileNameAux.equals(archivoAceptado)) {
        		mensajeError = "El archivo para cambio de estatus de beneficiarios debe ser un formato: .xls";
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
            procesaArchivo();
        } catch (Exception e1) {
            e1.printStackTrace();
        }

    }

    /**
     * Recupera el detalle de la informacion que se esta cargando actualmente o que espera confirmacion.
     * @return
     */
    public String procesaArchivo() {
    	this.listaCargaEstatus = new ArrayList<CambioEstatusBeneficiario>();
		HSSFWorkbook wb;
		HSSFRow row; 
		HSSFCell cell;

		try {
			InputStream ExcelFileToRead = this.archivo.getInputStream();
			wb = new HSSFWorkbook(ExcelFileToRead);
			HSSFSheet sheet=wb.getSheetAt(0);
			Iterator rows = sheet.rowIterator();

			while (rows.hasNext()) {
				CambioEstatusBeneficiario statusBean = new CambioEstatusBeneficiario();
				Beneficiario beneficiario = new Beneficiario();
				long statusActualizar = 0;
				row = (HSSFRow) rows.next();
				Iterator cells = row.cellIterator();
				if(row.getRowNum() > INICIO_ROW){
					while (cells.hasNext()) {
						cell = (HSSFCell) cells.next();
						if(cell.getColumnIndex() == 0){
							statusBean.setUoi(cell.getStringCellValue());
							/**
							 * Obtengo detalle de beneficiario
							 */
							if(statusBean.getUoi() != null){
								beneficiario = this.controlBeneficiariosService.consultaBeneficiarioByUoi(statusBean.getUoi());								
							}
						} else if(cell.getColumnIndex() == 1){
							try{
								if(beneficiario != null){
									String estadoAactualizar = null;
									if(cell.getStringCellValue() != null){
										estadoAactualizar = cell.getStringCellValue().trim();
										statusBean.setEstadoActualizadoBeneficiario(estadoAactualizar);
										statusBean.setEstadoActualBeneficiario(beneficiario.getStatusBenef().getDescStatusBenef());
										if(cell.getStringCellValue().equals(ListStatusBeneficiario.REGISTRADO.getStatusBeneficiario())
												|| cell.getStringCellValue().equals(ListStatusBeneficiario.REGISTRADO_CORTO.getStatusBeneficiario())){
											statusActualizar = 1;
											statusBean.setEstadoActualizadoBeneficiario(ListStatusBeneficiario.REGISTRADO.getStatusBeneficiario());
										} else if(cell.getStringCellValue().equals(ListStatusBeneficiario.AUTORIZADO_CORTO.getStatusBeneficiario())
												|| cell.getStringCellValue().equals(ListStatusBeneficiario.AUTORIZADO.getStatusBeneficiario())){
											statusActualizar = 2;
											statusBean.setEstadoActualizadoBeneficiario(ListStatusBeneficiario.AUTORIZADO.getStatusBeneficiario());
										} else if(cell.getStringCellValue().equals(ListStatusBeneficiario.VENCIDO_CORTO.getStatusBeneficiario())
												|| cell.getStringCellValue().equals(ListStatusBeneficiario.VENCIDO.getStatusBeneficiario())){
											statusBean.setEstadoActualizadoBeneficiario(ListStatusBeneficiario.VENCIDO.getStatusBeneficiario());
											statusActualizar = 3;
										} else if(cell.getStringCellValue().equals(ListStatusBeneficiario.ACTUALIZADO_CORTO.getStatusBeneficiario())
												|| cell.getStringCellValue().equals(ListStatusBeneficiario.ACTUALIZADO.getStatusBeneficiario())){
											statusBean.setEstadoActualizadoBeneficiario(ListStatusBeneficiario.ACTUALIZADO.getStatusBeneficiario());
											statusActualizar = 4;
										} else if(cell.getStringCellValue().equals(ListStatusBeneficiario.CANCELADO_CORTO.getStatusBeneficiario())
												|| cell.getStringCellValue().equals(ListStatusBeneficiario.CANCELADO.getStatusBeneficiario())){
											statusActualizar = 5;
											statusBean.setEstadoActualizadoBeneficiario(ListStatusBeneficiario.CANCELADO.getStatusBeneficiario());
										} else if(cell.getStringCellValue().equals(ListStatusBeneficiario.ELIMINADO_CORTO.getStatusBeneficiario())
												|| cell.getStringCellValue().equals(ListStatusBeneficiario.ELIMINADO.getStatusBeneficiario())){
											statusBean.setEstadoActualizadoBeneficiario(ListStatusBeneficiario.ELIMINADO.getStatusBeneficiario());
											statusActualizar = 6;
										} else if(cell.getStringCellValue().equals(ListStatusBeneficiario.PRE_AUTORIZADO_CORTO.getStatusBeneficiario())
												|| cell.getStringCellValue().equals(ListStatusBeneficiario.PRE_AUTORIZADO.getStatusBeneficiario())){
											statusBean.setEstadoActualizadoBeneficiario(ListStatusBeneficiario.PRE_AUTORIZADO.getStatusBeneficiario());
											statusActualizar = 7;
										}
									}
									if(estadoAactualizar != null){
										this.controlBeneficiariosService.cambiaStatusBeneficiario(beneficiario, statusActualizar, statusBean.getEstadoActualizadoBeneficiario());
										statusBean.setRegistroActualizado(REGISTRO_ACTUALIZADO);
									} else{
										statusBean.setRegistroActualizado(REGISTRO_NO_ACTUALIZADO);
										statusBean.setError(ERROR_NO_EXISTE_ESTATUS);
									}
								} else {
									statusBean.setRegistroActualizado(REGISTRO_NO_ACTUALIZADO);
									statusBean.setError(ERROR_NO_EXISTE_BENEFICIARIO + statusBean.getUoi());
								}
							} catch(Exception ex){
								statusBean.setRegistroActualizado(REGISTRO_NO_ACTUALIZADO);
								statusBean.setError(ex.getMessage().replace(ERROR_CHANGE_STATUS, ""));
							}
						}
					}
				}
				this.listaCargaEstatus.add(statusBean);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				this.archivo.getInputStream().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return null;
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
	 * @return the listaCargaEstatus
	 */
	public List<CambioEstatusBeneficiario> getListaCargaEstatus() {
		return listaCargaEstatus;
	}

	/**
	 * @param listaCargaEstatus the listaCargaEstatus to set
	 */
	public void setListaCargaEstatus(List<CambioEstatusBeneficiario> listaCargaEstatus) {
		this.listaCargaEstatus = listaCargaEstatus;
	}
	
    /**
     * @param controlBeneficiariosService the controlBeneficiariosService to set
     */
    public void setControlBeneficiariosService(
            final ControlBeneficiariosService controlBeneficiariosService) {
        this.controlBeneficiariosService = controlBeneficiariosService;
    }

}
