/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 * 
 * May 7, 2008
 */
package com.indeval.portaldali.presentation.controller.mercadodinero.fileTransfer;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.indeval.portaldali.middleware.services.filetransfer.TotalesProcesoVO;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.presentation.common.constants.UtilConstantes;
import com.indeval.portaldali.presentation.controller.common.ControllerBase;

/**
 * Controller en el cual se visualiza los resultados del analisis del archivo proporcionado por el usuario
 * 
 * @author Erik Vera Montoya
 * 
 * @version 1.0
 */
public class FileTransferPreviewCargaController extends ControllerBase {

    private TotalesProcesoVO resumenPrevio;
    // private transient LockFileUploadDao fileUploadDao;
    private String navPreview;

    public String getResumenPrevioCarga() {
        try {
            AgenteVO agenteFirmado = new AgenteVO();
            // agenteFirmado.setId(usuario.getId());
            // agenteFirmado.setFolio(usuario.getFolio());
            // resumenPrevio = fileTransferSrvc.muestraInformacion(agenteFirmado, process, false, paginador.getPaginaVO());
            // resumenPrevio = agregaMensajeSinError(resumenPrevio);
            // paginador.setPaginaVO(resumenPrevio.getPaginaVO());
            return navPreview;
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
            e.printStackTrace();
            // log.error(e);
            // fileUploadDao.releaseLock(usuario.getId(), usuario.getFolio(), process);
            return UtilConstantes.ERROR;
        }
    }

    /**
     * 
     * @return
     */
    public Boolean getIsThisMyProcess() {
        // ProcessInfoVO processInfo = fileUploadDao.getProcessInfo(usuario.getId(), usuario.getFolio(), process);
        /*
         * if (processInfo == null) { return Boolean.FALSE; }
         */
        // return new Boolean(usuario.getUser().equals(processInfo.getUsuario()));
        return null;
    }

    /**
     * método para procesar el archivo de file transfer
     * 
     * @return
     */
    public String procesar() {
        /*
         * ProcessInfoVO processInfo = fileUploadDao.getProcessInfo(usuario.getId(), usuario.getFolio(), process);
         * processInfo.setStatus(ProcessInfoVO.PROCESANDO); processInfo.setPorcentajeTerminado(new Double(0));
         * fileUploadDao.updateProcessInfo(usuario.getId() + usuario.getFolio() + process, processInfo); Thread thread = new
         * Thread(this); thread.setPriority(Thread.MIN_PRIORITY); thread.start(); return getDetalle();
         */
        return null;
    }

    /**
     * Metodo para cancelar el file transfer
     * 
     * @return
     */
    public String cancelar() {
        try {
            AgenteVO agenteFirmado = new AgenteVO();
            /*
             * agenteFirmado.setId(usuario.getId()); agenteFirmado.setFolio(usuario.getFolio());
             * fileTransferSrvc.cancelaProceso(agenteFirmado, process);
             * 
             */
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
            e.printStackTrace();
            // log.error(e);
            return UtilConstantes.ERROR;
        } finally {
            // fileUploadDao.releaseLock(usuario.getId(), usuario.getFolio(), process);
        }
        // return getDetalle();
        return null;
    }

}
