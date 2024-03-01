/**
 * Multidivisas: Días Inhábiles de Divisas
 */
package com.indeval.portalinternacional.presentation.controller.diasInhabilesDivisas;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.DiasInhabilesDivisasService;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.FileTransferService;
import com.indeval.portalinternacional.middleware.services.util.vo.ProcessInfoVO;
import com.indeval.portalinternacional.middleware.servicios.dto.diasIhabilesDivisas.HistoricoDiasInhabilesDivisasDTO;
import com.indeval.portalinternacional.middleware.servicios.vo.FileTransferVO;
import com.indeval.portalinternacional.middleware.servicios.vo.ResumenVO;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;
import org.apache.commons.lang.StringUtils;
import org.apache.myfaces.custom.fileupload.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static com.indeval.portalinternacional.middleware.servicios.constantes.Constantes.ROL_INT_AUTORIZA_DIAS_DIVISA;
import static com.indeval.portalinternacional.middleware.servicios.constantes.Constantes.ROL_INT_OPERADOR_DIAS_DIVISA;


public class DiasInhabilesRegistroController extends ControllerBase {

    /**
     * Log de clase.
     */
    private static final Logger log = LoggerFactory.getLogger(DiasInhabilesRegistroController.class);

    private String OPERACION = "REGISTRO";

    private DiasInhabilesDivisasService diasInhabilesDivisasService;

    private List<HistoricoDiasInhabilesDivisasDTO> historicos;
    private Long idHistoricoSelected;
    private HistoricoDiasInhabilesDivisasDTO historicoSelected;

    private boolean validationErrors = false;

    private boolean usuarioPuedeRegistrar = false;


    /**
     * Nombre del proceso arrancado
     */
    private String process;

    /**
     * Informaci&oacute;n del resumen de carga
     */
    private ResumenVO resumen;

    private int totalPaginas = 1;

    private boolean consultaEjecutada;

    /**
     * Archivo subido por el usuario
     */
    private UploadedFile archivo;

    private ObjetosParaProcesar objetosParaProcesar;


    /**
     * Servicio para el acceso a file transfer
     */
    private FileTransferService fileTransferService;

    /**
     * Informacion del proceso de carga actual
     */
    private ProcessInfoVO processInfo;
    private FileTransferVO fileTransferVO;
    private AgenteVO agenteFirmado;


    /**
     * Mensaje de error a mostrar del m&eacute;todo del thread
     */
    private String mensajeError;


    /**
     * Lista con los tipos de archivos aceptados (content-type) por este file transfer
     */
    public static final List<String> listaArchivosAceptados = new ArrayList<String>();

    static {
        listaArchivosAceptados.add("text/csv");
    }


    ////////////////////////////////////////////Seccion de Metodos ////////////////////////////////////////////

    // <editor-fold defaultstate="collapsed" desc="Sección de Inicializadores">

    /**
     * Recupera la informaci&oacute;n del proceso que est&aacute; actualmente corriendo para mostrar la pantalla por primera vez.
     *
     * @return null
     */
    public String getInit() {
        consultaEjecutada = false;
        inicializaPermisos();
        inicializaObjetosProcesos();
        cargaHistoricos();
        return null;
    }

    /**
     * inicializaProcessInfoVO
     */
    public void inicializaObjetosProcesos() {
        log.info("inicializaProcessInfoVO");
        try {
            agenteFirmado = getAgenteFirmado();
            processInfo = new ProcessInfoVO();
            processInfo.setUsuario(agenteFirmado.getId() + agenteFirmado.getFolio());
            processInfo.setIdProceso(agenteFirmado.getId() + agenteFirmado.getFolio() + process);


            fileTransferVO = new FileTransferVO();
            fileTransferVO.setAgenteFirmado(agenteFirmado);
            paginaVO = new PaginaVO();
            fileTransferVO.setPaginaVO(paginaVO);
            fileTransferVO.setNombreUsuario(getNombreUsuarioSesion());
            fileTransferVO.setTipoProceso(process);
            fileTransferVO.setSoloErrores(false);

            resumen = new ResumenVO();

        } catch (BusinessException e) {
            log.error("Ocurrio un error:", e);
        }
    }

    /**
     * inicializaPaginaVO
     */
    private void inicializarPaginaVO() {
        paginaVO = new PaginaVO();
        paginaVO.setRegistrosXPag(20);
        paginaVO.setOffset(0);
    }


    /**
     * Verifica si el usuario cuenta con los permisos para Registrar Historico de Días Inhábiles de Divisas
     */
    private void inicializaPermisos() {
        log.info("validarPermisos "
                + ":: INT_AUTORIZA_DIAS_DIVISA [" + ROL_INT_AUTORIZA_DIAS_DIVISA + "] "
                + ":: INT_OPERADOR_DIAS_DIVISA [" + ROL_INT_OPERADOR_DIAS_DIVISA + "]");
        this.usuarioPuedeRegistrar = isUserInRoll(ROL_INT_OPERADOR_DIAS_DIVISA);
    }

    // </editor-fold>


    public String cargaHistoricos(ActionEvent event) {
        log.debug(event.toString());
        return cargaHistoricos();
    }

    public String cargaHistoricos() {
        log.debug("Cargar Historicos" + OPERACION);
        if (paginaVO == null) this.inicializarPaginaVO();
        this.historicos = diasInhabilesDivisasService.getAll(getNombreUsuarioSesion(), null);
        return null;
    }

    public boolean isPuedeCargarArchivo() {
        boolean condicion = usuarioPuedeRegistrar &&
                (objetosParaProcesar == null
                        || objetosParaProcesar.getEstadoCarga() == null
                        || objetosParaProcesar.getEstadoCarga().equals(ProcessInfoVO.CARGANDO));
        return condicion;
    }

    public boolean isPuedeProcesarInfo() {
        boolean condicion = objetosParaProcesar != null
                && objetosParaProcesar.getEstadoCarga() != null
                && objetosParaProcesar.getEstadoCarga().equals(ProcessInfoVO.CARGADO)
                && !this.objetosParaProcesar.getObjetosParaProcesar().isEmpty();
        return condicion;
    }

    public boolean isPuedeTerminar() {
        boolean condicion = objetosParaProcesar != null
                && objetosParaProcesar.getEstadoCarga() != null
                && objetosParaProcesar.getEstadoCarga().equals(ProcessInfoVO.TERMINADO);
        return condicion;
    }

    public boolean isHayNombreArchivo() {
        boolean condicion = this.objetosParaProcesar != null
                && this.objetosParaProcesar.getNombreArchivo() != null
                && !this.objetosParaProcesar.getNombreArchivo().isEmpty();
        return condicion;
    }


    // <editor-fold defaultstate="collapsed" desc="Sección de carga de Días Inhábiles por Divisas">

    private void cargaDiasInhabilesDivisasPaginaVO() {
        log.debug("cargaDiasInhabilesDivisasPaginaVO :: idHistorico [" + this.idHistoricoSelected + "]");
        consultaEjecutada = false;
        if (objetosParaProcesar != null
                && objetosParaProcesar.getEstadoCarga() != null) {
            cargaHistoricos();
        }
        if (this.paginaVO == null) inicializarPaginaVO();
        PaginaVO resultados = diasInhabilesDivisasService.getDiasInhabilesByIdHistorico(this.idHistoricoSelected, this.paginaVO);
        this.paginaVO.setTotalRegistros(resultados.getTotalRegistros());
        this.paginaVO.setRegistros(resultados.getRegistros());

        totalPaginas = this.paginaVO.getTotalRegistros() / this.paginaVO.getRegistrosXPag();

        if (this.paginaVO.getTotalRegistros() % this.paginaVO.getRegistrosXPag() > 0) totalPaginas++;

        totalPaginas = (totalPaginas <= 0) ? 1 : totalPaginas;

        log.debug("Total de Páginas : " + totalPaginas);
        log.debug("Total de Registros: " + this.paginaVO.getRegistros().size());
        consultaEjecutada = true;
    }

    /**
     * Obtiene el Histórico seleccionado desde front
     */
    private HistoricoDiasInhabilesDivisasDTO getHistoricoById(Long idHistorico) {
        log.debug("getHistoricoById :: " + idHistorico);
        for (HistoricoDiasInhabilesDivisasDTO dto : this.historicos) {
            if (idHistorico.equals(dto.getIdHistorico())) {
                log.debug(dto.toString());
                this.historicoSelected = dto;
                return dto;
            }
        }

        return null;
    }

    // </editor-fold>


    // <editor-fold defaultstate="collapsed" desc="Seccion de carga del Archivo desde Front">

    /**
     * Atiende la petici&oacute;n del usuario para iniciar el proceso de carga de archivo de operaciones.
     *
     * @param e
     */
    public void uploadFile(ActionEvent e) {
        log.info("uploadFile");
        try {
            objetosParaProcesar = new ObjetosParaProcesar();
            boolean errors = false;
            mensajeError = null;
            if (archivo == null) {
                mensajeError = "El archivo de carga de D\u00EDas Inh\u00E1biles de Divisas es un dato requerido";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, mensajeError, mensajeError));
                errors = true;
            } else if (!listaArchivosAceptados.contains(archivo.getContentType())) {
                mensajeError = "El archivo de carga de D\u00EDas Inh\u00E1biles de Divisas debe ser de tipo CSV";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, mensajeError, mensajeError));
                errors = true;
            }
            log.debug(agenteFirmado.toString());
            if (!errors && (agenteFirmado.getId() == null || agenteFirmado.getId().isEmpty() ||
                    agenteFirmado.getFolio() == null || agenteFirmado.getFolio().isEmpty())) {
                mensajeError = "Se necesita un usuario logeado";
                agregarInfoMensaje(mensajeError);
                errors = true;
            }
            if (errors) {
                return;
            }
            carga();
        } catch (Exception e1) {
            log.error(e1.toString(), e1);
            e1.printStackTrace();
        }
    }

    /**
     * Inicia la validaci&oacute;n del archivo a cargar
     */
    private void carga() {
        log.info("carga");
        try {
            this.processInfo.setStatus(ProcessInfoVO.CARGANDO);
            objetosParaProcesar.setEstadoCarga(ProcessInfoVO.CARGANDO);
            log.debug(processInfo.toString());

            int totalRegistros = 0;
            log.debug("Se esta cargando el archivo");
            ArrayList<String> arlArchivo = readFile(agenteFirmado.getId() + agenteFirmado.getFolio() + process);
            totalRegistros = arlArchivo.size();
            log.debug("Archivo leido :: " + totalRegistros);
            this.fileTransferVO.setTipoProceso(process);
            this.fileTransferVO.setNombreUsuario(processInfo.getUsuario());
            this.fileTransferVO.setInformacionArchivo(arlArchivo.toArray(new String[]{}));
            this.fileTransferService.almacenaInformacion(fileTransferVO);

            log.debug("Total de registros tranformados :: " + fileTransferVO.getPaginaVO().getTotalRegistros());

            log.debug("Obtener Resumen");
            resumen = fileTransferService.obtieneResumen(fileTransferVO);
            this.processInfo.setStatus(ProcessInfoVO.CONFIRMACION);
            objetosParaProcesar.setEstadoCarga(ProcessInfoVO.CONFIRMACION);
            this.objetosParaProcesar.setObjetosParaProcesar(this.fileTransferVO.getPaginaVO().getRegistros());
            this.objetosParaProcesar.setTotalRegistros(this.resumen.getTotalRegistros());
            this.objetosParaProcesar.setTotalParaProcesar(this.resumen.getTotalCargados());
            this.objetosParaProcesar.setTotalErrores(this.resumen.getTotalError());
            log.debug(this.objetosParaProcesar.toString());
            this.processInfo.setStatus(ProcessInfoVO.CARGADO);
            objetosParaProcesar.setEstadoCarga(ProcessInfoVO.CARGADO);

            log.debug("Total de registros a Cargar :: " + this.objetosParaProcesar.getTotalParaProcesar());
        } catch (Exception e) {
            log.error(e.toString(), e);
            mensajeError = "Hubo un error al cargar el archivo, verifique el formato: " + e.getMessage();
            e.printStackTrace();
        } finally {
            if (this.objetosParaProcesar.getObjetosParaProcesar() == null ||
                    this.objetosParaProcesar.getObjetosParaProcesar().isEmpty()) {
                this.processInfo.setStatus(ProcessInfoVO.TERMINADO);
                objetosParaProcesar.setEstadoCarga(ProcessInfoVO.TERMINADO);
            }
        }
    }

    /**
     * Parsea el archivo cargado y retorna los resultados como un arreglo de String donde cada elemento es un rengl&oacute;n leido del archivo
     *
     * @param processId Identificador del proceso
     * @return Lista con las l&iacute;neas del archivo leidas
     * @throws IOException
     */
    private ArrayList<String> readFile(String processId) throws IOException {
        log.debug("readFile :: [" + archivo.getName() + "]" + processId);
        this.fileTransferVO.setNombreArchivo(archivo.getName());
        this.objetosParaProcesar.setNombreArchivo(archivo.getName());
        InputStreamReader ir = null;
        BufferedReader br = null;

        try {
            long fileSize = 0;
            ir = new InputStreamReader(archivo.getInputStream());
            fileSize = archivo.getSize();

            br = new BufferedReader(ir);
            ArrayList<String> arlArchivo = new ArrayList<String>();
            String linea = null;
            long bytesReaded = 0;
            linea = br.readLine();

            while (linea != null) {
                bytesReaded += linea.length() + 2;
                if (StringUtils.isNotBlank(linea)) {
                    log.debug(linea);
                    arlArchivo.add(linea);
                }
                linea = br.readLine();
            }

            if (!arlArchivo.isEmpty()) {
                arlArchivo.remove(0);
            }
            return arlArchivo;
        } catch (IOException e) {
            throw e;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (ir != null) {
                try {
                    ir.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // </editor-fold>


    // <editor-fold defaultstate="collapsed" desc="Seccion de procesamiento de información del Archivo previamente cargado">

    /**
     * Inicia el proceso de validaci&oacute;n y env&iacute;o de operaciones  de los datos cargados del archivo.
     *
     * @param e
     */
    public void procesar(ActionEvent e) {
        log.info("procesar :: " + this.objetosParaProcesar.getNombreArchivo());
        try {
            if (isUsuarioConFacultadFirmar()) {
                validarVigenciaCertificado();
            }
            mensajeError = null;
            procesamiento();
        } catch (BusinessException businessException) {
            FacesContext.getCurrentInstance().addMessage(
                    null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            businessException.getMessage(), businessException.getMessage()));
        } catch (Exception ex) {
            log.error(ex.toString(), ex);
            ex.printStackTrace();
            throw new BusinessException(ex.getMessage());
        }
    }

    /**
     * Inicia el procesamiento de carga de días inhábiles por divisa
     */
    public void procesamiento() {
        log.info("procesamiento :: " + this.objetosParaProcesar.getNombreArchivo());
        try {
            inicializarPaginaVO();
            this.processInfo.setStatus(ProcessInfoVO.PROCESANDO);
            objetosParaProcesar.setEstadoCarga(ProcessInfoVO.PROCESANDO);
            log.debug(processInfo.toString());

            log.debug("Carga de Objetos a Procesar");
            this.fileTransferVO.setTipoProceso(process);
            this.fileTransferVO.setNombreArchivo(this.objetosParaProcesar.getNombreArchivo());
            this.fileTransferVO.getPaginaVO().setRegistros(this.objetosParaProcesar.getObjetosParaProcesar());
            this.fileTransferVO.getPaginaVO().setTotalRegistros(this.objetosParaProcesar.getTotalRegistros());
            this.fileTransferVO.setSoloErrores(false);
            fileTransferService.grabaInformacion(this.fileTransferVO);
            this.idHistoricoSelected = (Long) this.fileTransferVO.getObjetoReferenciaFinal();
            cargaDiasInhabilesDivisasPaginaVO();
            this.processInfo.setStatus(ProcessInfoVO.TERMINADO);
            objetosParaProcesar.setEstadoCarga(ProcessInfoVO.TERMINADO);
        } catch (Exception e) {
            mensajeError = "Hubo un error al procesar el archivo, verifique: " + e.getMessage();
            e.printStackTrace();
        }
    }

    // </editor-fold>


    /**
     * Cancela el proceso de validaci&oacute;n y procesamiento de los registros cargados
     *
     * @param ev
     */
    public void cancelar(ActionEvent ev) {
        getInit();
        inicializarPaginaVO();
        objetosParaProcesar = new ObjetosParaProcesar();
        validationErrors = false;
        consultaEjecutada = false;
    }

    /**
     * M&eacute;todo base para poder ejecutar la consulta
     */
    @Override
    public String ejecutarConsulta() {
        cargaDiasInhabilesDivisasPaginaVO();
        return null;
    }


    // <editor-fold defaultstate="collapsed" desc="Seccion getters and setters.">

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


    public DiasInhabilesDivisasService getDiasInhabilesDivisasService() {
        return diasInhabilesDivisasService;
    }

    public void setDiasInhabilesDivisasService(DiasInhabilesDivisasService diasInhabilesDivisasService) {
        this.diasInhabilesDivisasService = diasInhabilesDivisasService;
    }

    public List<HistoricoDiasInhabilesDivisasDTO> getHistoricos() {
        return historicos;
    }

    public void setHistoricos(List<HistoricoDiasInhabilesDivisasDTO> historicos) {
        this.historicos = historicos;
    }

    public Long getIdHistoricoSelected() {
        return idHistoricoSelected;
    }

    public void setIdHistoricoSelected(Long idHistoricoSelected) {
        this.idHistoricoSelected = idHistoricoSelected;
    }

    public FileTransferVO getFileTransferVO() {
        return fileTransferVO;
    }

    public void setFileTransferVO(FileTransferVO fileTransferVO) {
        this.fileTransferVO = fileTransferVO;
    }

    public ObjetosParaProcesar getObjetosParaProcesar() {
        return objetosParaProcesar;
    }

    public void setObjetosParaProcesar(ObjetosParaProcesar objetosParaProcesar) {
        this.objetosParaProcesar = objetosParaProcesar;
    }

    @Override
    public int getTotalPaginas() {
        return totalPaginas;
    }

    @Override
    public void setTotalPaginas(int totalPaginas) {
        this.totalPaginas = totalPaginas;
    }


    public boolean isValidationErrors() {
        return validationErrors;
    }

    public void setValidationErrors(boolean validationErrors) {
        this.validationErrors = validationErrors;
    }


    public boolean isUsuarioPuedeRegistrar() {
        return usuarioPuedeRegistrar;
    }

    public void setUsuarioPuedeRegistrar(boolean usuarioPuedeRegistrar) {
        this.usuarioPuedeRegistrar = usuarioPuedeRegistrar;
    }


    public String getOPERACION() {
        return OPERACION;
    }

    public void setOPERACION(String OPERACION) {
        this.OPERACION = OPERACION;
    }

    public boolean isConsultaEjecutada() {
        return consultaEjecutada;
    }

    public void setConsultaEjecutada(boolean consultaEjecutada) {
        this.consultaEjecutada = consultaEjecutada;
    }


    // </editor-fold>

}