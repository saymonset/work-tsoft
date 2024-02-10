/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * Agosto 28, 2008
 */
package com.indeval.portalinternacional.presentation.controller.statements;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import javax.faces.event.ActionEvent;

import org.apache.commons.lang.StringUtils;
import org.apache.myfaces.custom.fileupload.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bursatec.seguridad.vo.InstitucionVO;
import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.services.util.FileUploadService;
import com.indeval.portalinternacional.middleware.services.util.vo.ProcessInfoVO;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.excell.vo.CargaExcellVO;
import com.indeval.portalinternacional.middleware.servicios.excell.vo.CargaZipVO;
import com.indeval.portalinternacional.middleware.servicios.vo.FileTransferVO;
import com.indeval.portalinternacional.middleware.servicios.vo.RegistroProcesadoVO;
import com.indeval.portalinternacional.middleware.servicios.vo.TotalesProcesoVO;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;

/**
 * Controller para el envio de archivos
 * 
 * @author Esteban Herrera
 * @version 1.0
 *
 */
@SuppressWarnings("unchecked")
public class FileTransferStatementController extends ControllerBase {

    /** Log de clase. */
	private static final Logger log = LoggerFactory.getLogger(FileTransferStatementController.class);
    /** Indica si existe un proceso iniciad por el controller */
    private boolean procesoIniciado = false;
    /** Indica si debe mostrar en la pantalla el boton de detener proceso */
    private boolean mostrarBotonDetenerProceso = false;
    /** Indica si se debe detener el poll en la siguiente vuelta */
    private boolean detenerSiguienteVuelta = false;
    /** Bandera de Poll */
    private boolean polling = false;
    /** Resumen: de la fecha de Carga del documento */
    private String fechaCarga;
    /** Resumen: de la fecha de Carga del documento */
    private String horaCarga;
    /** Usuario responsable */
    private String usuarioResponsable;
    /** Progreso de avance */
    private String progreso = null;
    /** Nombre del proceso arrancado */
    private String process = Constantes.ID_PROCESO_FILE_TRANSFER_STATEMENT;
    /** Informaci&oacute;n del resumen de carga */
    private CargaZipVO cargaZip;
    /** Indica si pinta en pantalla el link para mostrar errores */
    private Boolean showLink = Boolean.TRUE;
    /** Archivo subido por el usuario */
    private UploadedFile archivo;
    /** ID de la pagina principal */
    private String navHome;
    /** Servicio para el bloqueo y cordinaci&oacute;n del servicio */
    private FileUploadService fileUploadService = null;
    /** Informacion del proceso de carga actual */
    private ProcessInfoVO processInfo = new ProcessInfoVO();
    /** ID de la instituci&oacute;n */
    private String id;
    /** Folio de la instituci&oacute;n */
    private String folio;
    /** Representa los datos de la instituci&oacute;n actual */
    private InstitucionVO institucionVO = null;
    /** Mensaje de error a mostrar del m&eacute;todo del thread */
    private String mensajeError;
    private ExecutorService executor = null;
    private FutureTask<String> futureTask = null;
    /** Utileria para cargar los Excel */
    private ExcellStatementUtil statementUtil;
    /** Indica la bandera de Inicio */
    private boolean banderaInicio = false;
    /** Bandera que indica si solamente se va a simular para validar el archivo */
    private boolean simular;

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

        } catch (Exception e) {
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
    public TotalesProcesoVO agregaMensajeSinError(TotalesProcesoVO p) {
        PaginaVO pagina = p.getPaginaVO();

        if (pagina != null) {
            List l = pagina.getRegistros();

            if (l != null) {
                for (int i = 0; i < l.size(); i++) {
                    RegistroProcesadoVO rp = (RegistroProcesadoVO) l.get(i);
                    if (rp.getMensajesError() == null || rp.getMensajesError().length == 0) {
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
        if (!banderaInicio) {
            id = getAgenteFirmado().getId();
            folio = getAgenteFirmado().getFolio();
            cargaZip = new CargaZipVO();
            getDetalleProceso();
            polling = false;
            procesoIniciado = false;
            mostrarBotonDetenerProceso = false;
            detenerSiguienteVuelta = false;
            simular = true;
        } else {
            banderaInicio = false;
        }
        return null;
    }

    /**
     * Verifica si existe un proceso corriendo
     */
    public Boolean getIsProcessRunning() {
        processInfo.setUsuario(id + folio);
        processInfo.setIdProceso(id + folio + process);
        return fileUploadService.isProcessRunning(processInfo);
    }

    /**
     * Verifica si el proceso que est&aacute; corriendo corresponde al usuario actual
     * @return
     */
    public Boolean getIsThisMyProcess() {
        if (processInfo == null) {
            processInfo = new ProcessInfoVO();
        }
        processInfo.setUsuario(id + folio);
        processInfo.setIdProceso(id + folio + process);
        processInfo = fileUploadService.getProcessInfo(processInfo);
        if (processInfo == null) {
            return Boolean.FALSE;
        }

        return new Boolean((processInfo.getUsuario().equals(id + folio)));
    }

    /**
     * Cancela el proceso de carga del archivo.
     */
    public void abortProcess(ActionEvent ev) {
        abortProcess();
        release();
    }

    /**
     * Cancela el proceso de carga del archivo.
     * @return Constante que indica que regrese a la pagina principal (si aplica a la hora de llamar el metodo)
     */
    public String abortProcess() {
        try {
            if (futureTask != null && futureTask.isDone()) {
                futureTask.cancel(true);
            }
            mensajeError = "Proceso de carga detenido por el usuario";
            if (executor != null) {
                executor.shutdownNow();
            }
            futureTask = null;
            executor = null;
        } catch (Exception e) {
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
        if (processInfo == null) {
            processInfo = new ProcessInfoVO();
        }
        processInfo.setUsuario(id + folio);
        processInfo.setIdProceso(id + folio + process);
        fileUploadService.releaseLock(processInfo);
        polling = false;
        detenerSiguienteVuelta = true;
    }

    /**
     * Recupera el usuario que est&aacute; actualmente cargando un archivo
     * @return
     */
    public String getUserLoadingFile() {
        processInfo.setUsuario(id + folio);
        processInfo.setIdProceso(id + folio + process);
        processInfo = fileUploadService.getProcessInfo(processInfo);
        if (processInfo == null) {
            return "";
        }
        return processInfo.getUsuario();
    }

    /**
     * Recupera el detalle de la informaci&oacute;n que se esta cargando actualmente o que espera confirmaci&oacute;n.
     * @return
     */
    public void getDetalleProceso() {
        try {
            if (processInfo == null) {
                processInfo = new ProcessInfoVO();
            }
            processInfo.setUsuario(id + folio);
            processInfo.setIdProceso(id + folio + process);
            processInfo = fileUploadService.getProcessInfo(processInfo);
        } catch (BusinessException ex) {
            log.error("Error al obtenber el estatus del proceso de carga", ex);
            release();
        }
    }

    /**
     * Atiende la petici&oacute;n del usuario para iniciar el proceso de carga de archivo de operaciones.
     * @param e
     */
    public void uploadFile(ActionEvent e) {
        mensajeError = null;

        try {
            id = getAgenteFirmado().getId();
            folio = getAgenteFirmado().getFolio();
            cargaZip = new CargaZipVO();

            if (archivo == null) {
                mensajeError = "El archivo de operaciones es un dato requerido";
                addWarnMessage(mensajeError);
                return;
            }
            String nombreArchivo = StringUtils.upperCase(StringUtils.trimToEmpty(archivo.getName()));
            if (!nombreArchivo.endsWith("ZIP") && !nombreArchivo.endsWith("XLS")) {
                mensajeError = "Formato con compatible solo se acepta .zip o .xls";
                addWarnMessage(mensajeError);
                return;
            }
            if (id == null || id.equals("") || folio == null || folio.equals("")) {
                mensajeError = "Se necesita un usuario logeado";
                agregarInfoMensaje(mensajeError);
                return;
            }

            if (processInfo == null) {
                processInfo = new ProcessInfoVO();
            }
            processInfo.setUsuario(id + folio);
            processInfo.setIdProceso(id + folio + process);
            processInfo.setAbort('T');//F
            processInfo.setPorcentajeTerminado(new BigDecimal(0));
            processInfo.setStatus(ProcessInfoVO.CARGANDO);
            Boolean candado = fileUploadService.obtainLock(processInfo);

            if (!candado.booleanValue()) {
                mensajeError = "Ya hay un proceso de carga iniciado para este tipo de papel";
                addErrorMessage(mensajeError);
                log.error(mensajeError);
                return;
            }

            this.polling = true;
            procesoIniciado = true;
            detenerSiguienteVuelta = false;
            mostrarBotonDetenerProceso = true;
            getDetalleProceso();

            executor = Executors.newSingleThreadExecutor();
            futureTask = new FutureTask<String>(new Callable<String>() {

                public String call() {
                    run();
                    return null;
                }
            });
            executor.execute(futureTask);
        } catch (Exception e1) {
            log.error("Error al subir el archivo", e1);
        }
    }

    /**
     * Verifica si esta habilitado el proceso de polling para el monitoreo del proceso que esta corriendo actualmente
     * @return
     */
    public boolean isPollHabilitado() {
        boolean res = false;

        if (procesoIniciado) {
            if (detenerSiguienteVuelta) {
                res = false;
                detenerSiguienteVuelta = false;
                procesoIniciado = false;
                mostrarBotonDetenerProceso = false;
            } else {
                res = true;
            }
        } else {
            res = false;
        }

        if (futureTask != null && futureTask.isDone() && executor != null) {
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
    public String getDetenerPoll() {
        detenerSiguienteVuelta = true;
        return null;
    }

    /**
     * Funci&oacute;n ejecutada en cada vuelta del monitoreo del proceso de file transfer
     * @param e
     */
    public void pollEstado(ActionEvent e) {
    }

    /**
     * Inicia la validaci&oacute;n o env&iacute;o de operaciones del file transfer.
     * Esta funci&oacute;n se ejecuta en un hilo en el servidor mientras que la pantalla regresa a dar una respuesta al usuario
     * y la pantalla monitorea la actividad de esta funci&oacute;n mediante la compartici&oacute;n de datos del controller y en la BD
     */
    public void run() {
        try {
            processInfo.setUsuario(id + folio);
            processInfo.setIdProceso(id + folio + process);
            processInfo = fileUploadService.getProcessInfo(processInfo);

            if (processInfo.getStatus().equals(ProcessInfoVO.CARGANDO)) {
                String nombreArchivo = StringUtils.upperCase(StringUtils.trimToEmpty(archivo.getName()));
                if (nombreArchivo.endsWith("ZIP")) {
                    cargaZip = statementUtil.guardaZip(archivo.getInputStream(), archivo.getName(), simular);
                } else if (nombreArchivo.endsWith("XLS")) {
                    cargaZip = new CargaZipVO();
                    try {
                        CargaExcellVO cargaExcel = statementUtil.cargaExcel(archivo.getInputStream(), nombreArchivo, null, simular);
                        cargaExcel.setNombreArchivo(nombreArchivo);
                        cargaZip.setTotalArchivos(1);
                        cargaZip.getResultados().add(cargaExcel);
                    } catch (Exception exception) {
                        log.error("Error", exception);
                        cargaZip.getErrores().put(nombreArchivo, exception.getMessage());

                    }
                }

                processInfo.setStatus(ProcessInfoVO.TERMINADO);
                processInfo.setPorcentajeTerminado(new BigDecimal(0));
                fileUploadService.updateProcessInfo(processInfo);
                release();
            }
        } catch (Exception e) {
            if (processInfo.getStatus().equals(ProcessInfoVO.CARGANDO)) {
                mensajeError = "Hubo un error al cargar el archivo, verifique el formato: " + e.getMessage();
            } else {
                mensajeError = "Hubo un error al leer el archivo, verifique" + e.getMessage();
            }
            log.error(mensajeError, e);
            release();
        } finally {
            polling = false;
        }
    }

    public boolean isProcesoIniciado() {
        return procesoIniciado;
    }

    public void setProcesoIniciado(boolean procesoIniciado) {
        this.procesoIniciado = procesoIniciado;
    }

    public boolean isMostrarBotonDetenerProceso() {
        return mostrarBotonDetenerProceso;
    }

    public void setMostrarBotonDetenerProceso(boolean mostrarBotonDetenerProceso) {
        this.mostrarBotonDetenerProceso = mostrarBotonDetenerProceso;
    }

    public boolean isDetenerSiguienteVuelta() {
        return detenerSiguienteVuelta;
    }

    public void setDetenerSiguienteVuelta(boolean detenerSiguienteVuelta) {
        this.detenerSiguienteVuelta = detenerSiguienteVuelta;
    }

    public boolean isPolling() {
        return polling;
    }

    public void setPolling(boolean polling) {
        this.polling = polling;
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

    public String getUsuarioResponsable() {
        return usuarioResponsable;
    }

    public void setUsuarioResponsable(String usuarioResponsable) {
        this.usuarioResponsable = usuarioResponsable;
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

    public CargaZipVO getCargaZip() {
        return cargaZip;
    }

    public void setCargaZip(CargaZipVO cargaZip) {
        this.cargaZip = cargaZip;
    }

    public Boolean getShowLink() {
        return showLink;
    }

    public void setShowLink(Boolean showLink) {
        this.showLink = showLink;
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

    public InstitucionVO getInstitucionVO() {
        return institucionVO;
    }

    public void setInstitucionVO(InstitucionVO institucionVO) {
        this.institucionVO = institucionVO;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }

    public ExecutorService getExecutor() {
        return executor;
    }

    public void setExecutor(ExecutorService executor) {
        this.executor = executor;
    }

    public FutureTask<String> getFutureTask() {
        return futureTask;
    }

    public void setFutureTask(FutureTask<String> futureTask) {
        this.futureTask = futureTask;
    }

    public ExcellStatementUtil getStatementUtil() {
        return statementUtil;
    }

    public void setStatementUtil(ExcellStatementUtil statementUtil) {
        this.statementUtil = statementUtil;
    }

    public boolean isBanderaInicio() {
        return banderaInicio;
    }

    public void setBanderaInicio(boolean banderaInicio) {
        this.banderaInicio = banderaInicio;
    }

    /**
     * Bandera que indica si solamente se va a simular para validar el archivo
     * @return the simular
     */
    public boolean isSimular() {
        return simular;
    }

    /**
     * Bandera que indica si solamente se va a simular para validar el archivo
     * @param simular the simular to set
     */
    public void setSimular(boolean simular) {
        this.simular = simular;
    }
    //////////////////////////////////////////// Seccion de Getters y Setters ////////////////////////////////////////////
}
