/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.filetransfer;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.modelo.PaginaVO;
import com.indeval.portaldali.persistencia.fileTransfer.ArchivosFileTransfer;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public interface FileTransferService {

    /**
     * Almacena la informacion que se leyo del archivo de texto en la tabla
     * temporal correspondiente
     * 
     * @param agenteFirmado
     * @param tipoProceso
     * @param informacionArchivo
     * @param offset
     *            Numero del registro a partir del cual se ejecuta el servicio
     * @param nombreUsuario 
     * @return int Numero de registros almacenados
     * @throws BusinessException
     */
    int almacenaInformacion(AgenteVO agenteFirmado, String tipoProceso,
            ArrayList informacionArchivo, int offset, String nombreUsuario)
            throws BusinessException;

    /**
     * Valida la informacion almacenada en la tabla temporal
     * 
     * @param agenteFirmado
     * @param tipoProceso
     * @param offset
     *            Numero del registro a partir del cual se ejecuta el servicio
     * @return int Numero de registros validados
     * @throws BusinessException 
     */
    int validaInformacion(AgenteVO agenteFirmado, String tipoProceso, BigInteger idBoveda, 
            int offset) throws BusinessException;

    /**
     * Obtiene el resumen de la carga realizada
     * 
     * @param agenteFirmado
     * @param tipoProceso
     * @return ArrayList Lista de objetos de la clase ResumenVO
     * @throws BusinessException
     */
    ResumenVO obtieneResumen(AgenteVO agenteFirmado, String tipoProceso) throws BusinessException;

    
    /**
     * Muestra la lista de registros y el estado de cada uno de acuerdo a la
     * validacion y/o carga realizada
     * 
     * @param agenteFirmado
     * @param tipoProceso
     * @param soloErrores
     * @param paginaVO 
     * @return TotalesProcesoVO
     * @throws BusinessException
     */
    TotalesProcesoVO muestraInformacion(AgenteVO agenteFirmado, String tipoProceso,
            boolean soloErrores, PaginaVO paginaVO) throws BusinessException;

    /**
     * Cancela el proceso borrando los datos en la tabla temporal catalogo..filetransfer
     * 
     * @param agenteFirmado
     * @param tipoProceso
     * @throws BusinessException
     */
    void cancelaProceso(AgenteVO agenteFirmado, String tipoProceso) throws BusinessException;
    
    /**
     * Graba la informacion para su procesamiento (bddinero..tamp_mdin)
     * 
     * @param agenteFirmado
     * @param tipoProceso
     * @param consecProtocolo
     *            <KEY> Mapa con los consecutivos que se graban en birtacora
     *            operaciones <VALUE> 1 si se grabo correctamente, 0 en caso
     *            contrario
     * @throws BusinessException
     */
    void grabaInformacion(AgenteVO agenteFirmado, String tipoProceso, 
            HashMap consecProtocolo) throws BusinessException;

    
    /**
     * Guarda en la base de datos el archivo de file transfer que se procesa.
     * @param archivosFileTransfer Objeto con los datos y archivo del file transfer.
     * @throws BusinessException En caso de ocurrir un error
     */
    void guardarArchivoFileTransfer(ArchivosFileTransfer archivosFileTransfer) throws BusinessException;
    
}
