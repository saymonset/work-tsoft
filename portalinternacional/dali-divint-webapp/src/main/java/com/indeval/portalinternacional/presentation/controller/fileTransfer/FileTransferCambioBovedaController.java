/*
 * Copyrigth (c) 2017 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.presentation.controller.fileTransfer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.commons.lang.StringUtils;
import org.apache.myfaces.custom.fileupload.UploadedFile;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bursatec.seguridad.presentation.constants.SeguridadConstants;
import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.FileTransferCambioBovedaService;
import com.indeval.portalinternacional.middleware.services.util.ExcelUtils;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.FileTransferCambioBoveda;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;

/**
 * Clase base para los backing bean del File Transfer de Cambio de Boveda.
 * 
 */
@SuppressWarnings("unused")
public class FileTransferCambioBovedaController extends ControllerBase {

	private static final Logger LOG = LoggerFactory.getLogger(FileTransferCambioBovedaController.class);
    private UploadedFile archivoASubir;
    private List<String> lineasArchivo;
    private FileTransferCambioBovedaService fileTransferCambioBovedaService;
    private boolean habilitarArchivo = true;
    private String idFolio;
    private String usuarioft;
    private FileTransferCambioBoveda fileTransfer;
    private boolean habilitarProcesar;
    private boolean fileTransferExistente;
    // private InstitucionesDao institucionesDao;

    /**
     * Metodo que inicia la pantalla.
     * 
     * @return
     */
    public String getInit() {

        // setInstitucionActual(institucionesDao.getInstitucion(getUsuario().getIdInstitucion()));
        usuarioft = super.getCveUsuarioSesion();
        idFolio = super.getAgenteFirmado().getId() + super.getAgenteFirmado().getFolio();
        // revisa hace una consulta por insttitucion para saber si existe el FT
        if (!(this.fileTransfer != null && this.fileTransfer.getLineasError() != null
                && this.fileTransfer.getLineasError().size() > 0)) {
            this.consultarExistente();
        }
        return "";
    }

    /**
     * Consultar un file transfer existente, esto es que ya se encuentre activo.
     */
    public void consultarExistente() {
        FileTransferCambioBoveda ft = null;
        try {
            ft = this.fileTransferCambioBovedaService.consultaFileTransferActivo(idFolio);
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "No se puede generar la lista de operaciones del File transfer",
                            "No se puede generar la lista de operaciones del File transfer"));
            LOG.debug("No se pudo cargar la lista de operaciones del FT");
        }

        if (ft != null) {
            this.fileTransfer = ft;
            this.fileTransferExistente = true;
        } else {
            this.fileTransferExistente = false;
        }

        // si no existe habilita dialogo de subir archivo
        if (this.fileTransferExistente) {
            this.habilitarArchivo = false;
            this.habilitarProcesar = true;
        } else {
            this.habilitarArchivo = true;
            this.habilitarProcesar = false;
        }
    }

    /**
     * Agrega el File Transfer que se recibi&oacute;.
     */
    private void agregarFiletransfer() {
        try {
            this.fileTransfer = this.fileTransferCambioBovedaService.crearFileTransfer(this.fileTransfer);
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Error al subir el fileTransfer", "Error al subir el fileTransfer"));
            LOG.debug("Error al persistir File transfer!", e);
        }
    }

    /**
     * Procesa un file transfer.
     * 
     * @param event
     */
    public void procesaFiletransfer(ActionEvent event) {
        LOG.info("####### Entrando a FileTransferCambioBovedaController.procesaFiletransfer()...");
        FileTransferCambioBoveda ftExistente = null;
        try {
            ftExistente = this.fileTransferCambioBovedaService.consultaFileTransferActivo(idFolio);
        }
        catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Error al consultar el FileTransfer", "Error al consultar el FileTransfer"));
            LOG.debug("No se pudo cargar la lista de operaciones del FT");
        }
        try {
            if (ftExistente != null && Constantes.ESTATUS_CREADO.equals(ftExistente.getEstado())) {
                this.fileTransferCambioBovedaService.procesarOperacionesFileTransfer(fileTransfer);
                fileTransfer = null;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                        "FileTransfer Procesado", "FileTransfer Procesado"));
            }
            else {
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
		                "El FileTransfer ya fue procesado o cancelado por otro usuario",
		                "El FileTransfer ya fue procesado o cancelado por otro usuario"));
            }
        }
        catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Error al Procesar el fileTransfer", "Error al Procesar el fileTransfer"));
            LOG.debug("Error al persistir File transfer", e);
            e.printStackTrace();
        }
    }

    /**
     * Cancelaci&oacute;n de un file transfer.
     * 
     * @param event
     */
    public void cancelaFiletransfer(ActionEvent event) {
        LOG.info("####### Entrando a FileTransferCambioBovedaController.cancelaFiletransfer()...");
        FileTransferCambioBoveda ftExistente = null;
        try {
            ftExistente = this.fileTransferCambioBovedaService.consultaFileTransferActivo(idFolio);
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Error al consultar el FileTransfer", "Error al consultar el FileTransfer"));
            LOG.debug("No se pudo cargar la lista de operaciones del FT");
        }
        try {
            if (ftExistente != null && Constantes.ESTATUS_CREADO.equals(ftExistente.getEstado())) {
                this.fileTransferCambioBovedaService.cancelarFileTransfer(this.fileTransfer);
                this.fileTransfer = null;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                        "FileTransfer Cancelado", "FileTransfer Cancelado"));
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                "El FileTransfer ya fue procesado o cancelado por otro usuario",
                                "El FileTransfer ya fue procesado o cancelado por otro usuario"));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Error al Cancelar el fileTransfer", "Error al Cancelar el fileTransfer"));
            LOG.debug("Error al cancelar File transfer", e);
        }
    }

    /**
     * Parsea el archivo cargado y retorna los resultados como un arreglo de
     * String donde cada elemento es un rengl&oacute;n leido del archivo
     * 
     * @param processId
     *            Identificador del proceso
     * @param processInfo
     *            Objeto de informaci&oacute;n del proceso
     * @return Lista con las l&iacute;neas del archivo leidas
     * @throws IOException
     */
    private ArrayList<String> readFile(UploadedFile archivoASubir) throws IOException {

        InputStreamReader ir = null;
        BufferedReader br = null;

        try {
            long fileSize = 0;
            ir = new InputStreamReader(archivoASubir.getInputStream());
            fileSize = archivoASubir.getSize();

            br = new BufferedReader(ir);
            ArrayList<String> arlArchivo = new ArrayList<String>();
            String linea = null;
            linea = br.readLine();

            while (linea != null) {
                if (StringUtils.isNotBlank(linea)) {
                    arlArchivo.add(linea);
                }
                linea = br.readLine();
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

    /**
     * Toma el evento de iniciar carga de archivo en excel, genera un proceso
     * para el usuario en turno, verifica y guarda el archivo ingresado y
     * muestra la informaci&oacute;n de este
     */
    public void uploadFile2(final ActionEvent event) {
        LOG.info("####### Entrando a FileTransferCambioBovedaController.uploadFile2()...");
        String mensaje = null;
        try {
            this.validaFormatoArchivo();
            HSSFWorkbook wb = null;
            wb = ExcelUtils.convertInputStreamToHSSFWorkbook(this.archivoASubir.getInputStream());
            HSSFSheet sheet = null;
            sheet = ExcelUtils.convertHSSFWorkbookToHSSFSheet(wb);
            int[] rangoFilas = ExcelUtils.devuelveRangoDeFilas(sheet);
            ExcelUtils.validaFormatoExcelBeneficiarios(sheet, rangoFilas);
            ExcelUtils.validaRangoMaximoCargaBeneficiarios(rangoFilas);
            this.lineasArchivo = this.generaListas(sheet, rangoFilas);

            if (this.lineasArchivo != null && this.lineasArchivo.size() > 0) {
                this.fileTransfer = this.fileTransferCambioBovedaService.getFileTransfer(this.lineasArchivo,
                        this.idFolio, this.usuarioft);
                // si no hay errores persiste el ft
                if (this.fileTransfer != null) {
                    this.fileTransfer.setUsrCredencial((String) FacesContext.getCurrentInstance().getExternalContext()
                            .getSessionMap().get(SeguridadConstants.TICKET_SESION));
                    this.agregarFiletransfer();
                    this.habilitarArchivo = false;
                    this.habilitarProcesar = true;
                }
            }
        } catch (IOException mx) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, mx.getMessage(), mx.getMessage()));
            LOG.debug("Error al cargar filetransfer", mx);
            mx.printStackTrace();

        } catch (BusinessException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, e.getMessage(), e.getMessage()));
            LOG.debug("Error al cargar filetransfer", e);
            e.printStackTrace();
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, ex.getMessage(), ex.getMessage()));
            LOG.debug("Error al cargar filetransfer", ex);
            ex.printStackTrace();
        }
    }

    /**
     * Valida el formato del archivo xls.
     */
    private void validaFormatoArchivo() throws BusinessException {
        LOG.debug("####### validaFormatoArchivo()...");
        String mensajeError = null;
        if (this.archivoASubir == null) {
            mensajeError = "El archivo de operaciones es un dato requerido!";
            throw new BusinessException(mensajeError);
        }
        String nombreArchivo = StringUtils.upperCase(StringUtils.trimToEmpty(this.archivoASubir.getName()));

        if (nombreArchivo.endsWith("XLSX")) {
            mensajeError = "Cambiar formato, guardar con extensi\u00F3n .xls!";
            throw new BusinessException(mensajeError);
        }
        if (!nombreArchivo.endsWith("XLS")) {
            mensajeError = "Formato incorrecto, s\u00F3lo se acepta formato .xls!";
            throw new BusinessException(mensajeError);
        }

        if (StringUtils.isBlank(this.usuarioft) || StringUtils.isBlank(this.idFolio)) {
            mensajeError = "Se necesita un usuario con sesi\u00F3n iniciada!";
            throw new BusinessException(mensajeError);
        }
    }

    protected List<String> generaListas(HSSFSheet sheet, int[] rangoFilas) throws BusinessException {
        List<String> operaciones = new ArrayList<String>();
        HSSFRow row = null;
        for (int numeroRegistro = rangoFilas[0] + 1; numeroRegistro <= rangoFilas[1]; numeroRegistro++) {
            row = sheet.getRow(numeroRegistro);
            if (row != null) {
                String s = this.convertRowToFileTransferForma(row);
                operaciones.add(s);
            }
        }

        return operaciones;
    }

    public String convertRowToFileTransferForma(HSSFRow row) throws BusinessException {
        StringBuilder sb = new StringBuilder();
        try {
            if (row != null) {

//                // tipoValor
//                String tipoValor = "";
//                if (StringUtils.isNotBlank(ExcelUtils.getColumnAsString(row.getCell(0)))) {
//                    tipoValor = ExcelUtils.transformaCadena(ExcelUtils.getColumnAsString(row.getCell(0)));
//                    tipoValor = tipoValor.contains(".") ? tipoValor.substring(0, tipoValor.indexOf(".")) : tipoValor;
//                }
//                sb.append(tipoValor);
//                sb.append("|");
//
//                // Emisora
//                String emisora = "";
//                if (StringUtils.isNotBlank(ExcelUtils.getColumnAsString(row.getCell(1)))) {
//                    emisora = ExcelUtils.transformaCadena(ExcelUtils.getColumnAsString(row.getCell(1)));
//                }
//                sb.append(emisora);
//                sb.append("|");
//
//                // Serie
//                String serie = "";
//                if (StringUtils.isNotBlank(ExcelUtils.getColumnAsString(row.getCell(2)))) {
//                    serie = ExcelUtils.getColumnAsString(row.getCell(2));
//                    serie = serie.contains(".") ? serie.substring(0, serie.indexOf(".")) : serie;
//                }
//                sb.append(serie);
//                sb.append("|");

                // ISIN
                String isin = "";
                if (StringUtils.isNotBlank(ExcelUtils.getColumnAsString(row.getCell(0)))) {
                    isin = ExcelUtils.transformaCadena(ExcelUtils.getColumnAsString(row.getCell(0)));
                }
                sb.append(isin);
                sb.append("|");

                // fechaLiquidacion
                String fechaLiquidacion = "";
                if (StringUtils.isNotBlank(ExcelUtils.getColumnAsString(row.getCell(1)))) {
                    fechaLiquidacion = ExcelUtils.getColumnAsString(row.getCell(1));
                }
                sb.append(fechaLiquidacion);
                sb.append("|");

                // fechaOperacion
                String fechaOperacion = "";
                if (StringUtils.isNotBlank(ExcelUtils.getColumnAsString(row.getCell(2)))) {
                    fechaOperacion = ExcelUtils.getColumnAsString(row.getCell(2));
                }
                sb.append(fechaOperacion);
                sb.append("|");

                // custodioDestino
                String custodioDestino = "";
                if (StringUtils.isNotBlank(ExcelUtils.getColumnAsString(row.getCell(3)))) {
                    custodioDestino = ExcelUtils.transformaCadena(ExcelUtils.getColumnAsString(row.getCell(3)));
                }
                sb.append(custodioDestino);
                sb.append("|");

                //Cuenta Indeval
                String cuentaIndeval = "";
                if (StringUtils.isNotBlank(ExcelUtils.getColumnAsString(row.getCell(4)))) {
                	cuentaIndeval = ExcelUtils.transformaCadena(ExcelUtils.getColumnAsString(row.getCell(4)));
                }
                sb.append(cuentaIndeval);
                sb.append("|");
                
                // cuentaContraparte
                String cuentaContraparte = "";
                if (StringUtils.isNotBlank(ExcelUtils.getColumnAsString(row.getCell(5)))) {
                    cuentaContraparte = ExcelUtils.transformaCadena(ExcelUtils.getColumnAsString(row.getCell(5)));
                }
                sb.append(cuentaContraparte);
                sb.append("|");

                // descCuentaContraparte
                String descCuentaContraparte = "";
                if (StringUtils.isNotBlank(ExcelUtils.getColumnAsString(row.getCell(6)))) {
                    descCuentaContraparte = ExcelUtils.transformaCadena(ExcelUtils.getColumnAsString(row.getCell(6)));
                }
                sb.append(descCuentaContraparte);
                sb.append("|");

                // depositanteLiquidador
                String depositanteLiquidador = "";
                if (StringUtils.isNotBlank(ExcelUtils.getColumnAsString(row.getCell(7)))) {
                    depositanteLiquidador = ExcelUtils.transformaCadena(ExcelUtils.getColumnAsString(row.getCell(7)));
                }
                sb.append(depositanteLiquidador);
                sb.append("|");

                // nomCuentaBeneficiario
                String nomCuentaBeneficiario = "";
                if (StringUtils.isNotBlank(ExcelUtils.getColumnAsString(row.getCell(8)))) {
                    nomCuentaBeneficiario = ExcelUtils.transformaCadena(ExcelUtils.getColumnAsString(row.getCell(8)));
                }
                sb.append(nomCuentaBeneficiario);
                sb.append("|");

                // numCuentaBeneficiario
                String numCuentaBeneficiario = "";
                if (StringUtils.isNotBlank(ExcelUtils.getColumnAsString(row.getCell(9)))) {
                    numCuentaBeneficiario = ExcelUtils.transformaCadena(ExcelUtils.getColumnAsString(row.getCell(9)));
                }
                sb.append(numCuentaBeneficiario);
                sb.append("|");

                // tipoOperacion
                String tipoOperacion = "";
                if (StringUtils.isNotBlank(ExcelUtils.getColumnAsString(row.getCell(10)))) {
                    tipoOperacion = ExcelUtils.transformaCadena(ExcelUtils.getColumnAsString(row.getCell(10)));
                }
                sb.append(tipoOperacion);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("Error en la obtenci\u00F3n de un dato del registro " + row.getRowNum() + "!");
        }

        return sb.toString();
    }

    /**
     * Para el upload de un txt.
     * 
     * @param e
     * @throws IOException
     */
    public void uploadFile(ActionEvent e) throws IOException {
        boolean errors = false;

        if (this.archivoASubir == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "El archivo es requerido", "El archivo es requerido"));
            errors = true;
            this.fileTransfer = null;
            // }else if (this.archivoASubir != null &&
            // !this.archivoASubir.getName().contains(".txt")){
            // FacesContext.getCurrentInstance().addMessage(null,
            // new FacesMessage(FacesMessage.SEVERITY_WARN, "El archivo debe ser
            // de tipo texto plano. " ,"El archivo debe ser de tipo texto
            // plano."));
            // errors = true;
            // this.fileTransfer = null;
        } else {
            // lectura de archivo
            this.lineasArchivo = readFile(this.archivoASubir);
            // maximo 1000 mov
            if (this.lineasArchivo != null && this.lineasArchivo.size() > 1000) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                        "Maximo mil movimientos", "Maximo mil movimientos"));
                errors = true;
                this.fileTransfer = null;

                // tiene movimientos
            } else if (this.lineasArchivo != null && this.lineasArchivo.size() > 0) {

                this.fileTransfer = fileTransferCambioBovedaService.getFileTransfer(this.lineasArchivo, this.idFolio,
                        this.usuarioft);

                // si no hay errores persiste el ft
                if (this.fileTransfer != null && !this.fileTransfer.isErroresSintaxis()) {
                    consultarExistente();
                    if (!this.fileTransferExistente) {
                        agregarFiletransfer();
                        this.habilitarArchivo = false;
                        this.habilitarProcesar = true;
                    }
                } else {
                    this.habilitarArchivo = true;
                    this.habilitarProcesar = false;
                }
            }
        }

    }

    public List<String> getLineasArchivo() {
        return lineasArchivo;
    }

    public void setLineasArchivo(List<String> lineasArchivo) {
        this.lineasArchivo = lineasArchivo;
    }

    public boolean isHabilitarArchivo() {
        return habilitarArchivo;
    }

    public void setHabilitarArchivo(boolean habilitarArchivo) {
        this.habilitarArchivo = habilitarArchivo;
    }

    public String getIdFolio() {
        return idFolio;
    }

    public void setIdFolio(String idFolio) {
        this.idFolio = idFolio;
    }

    public FileTransferCambioBoveda getFileTransfer() {
        return fileTransfer;
    }

    public void setFileTransfer(FileTransferCambioBoveda fileTransfer) {
        this.fileTransfer = fileTransfer;
    }

    public boolean isHabilitarProcesar() {
        return habilitarProcesar;
    }

    public void setHabilitarProcesar(boolean habilitarProcesar) {
        this.habilitarProcesar = habilitarProcesar;
    }

    public boolean isFileTransferExistente() {
        return fileTransferExistente;
    }

    public void setFileTransferExistente(boolean fileTransferExistente) {
        this.fileTransferExistente = fileTransferExistente;
    }

    public UploadedFile getArchivoASubir() {
        return archivoASubir;
    }

    public void setArchivoASubir(UploadedFile archivoASubir) {
        this.archivoASubir = archivoASubir;
    }

    public String getUsuarioft() {
        return usuarioft;
    }

    public void setUsuarioft(String usuarioft) {
        this.usuarioft = usuarioft;
    }

    /*
     * public void setInstitucionesDao(InstitucionesDao institucionesDao) {
     * this.institucionesDao = institucionesDao; }
     */

    public FileTransferCambioBovedaService getFileTransferCambioBovedaService() {
        return fileTransferCambioBovedaService;
    }

    public void setFileTransferCambioBovedaService(FileTransferCambioBovedaService fileTransferCambioBovedaService) {
        this.fileTransferCambioBovedaService = fileTransferCambioBovedaService;
    }

}
