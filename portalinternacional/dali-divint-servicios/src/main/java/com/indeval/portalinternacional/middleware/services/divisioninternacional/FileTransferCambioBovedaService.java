/*
 * Copyright (c) 2017 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import java.util.List;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portalinternacional.middleware.servicios.modelo.FileTransferCambioBoveda;

/**
 * Interfaz para los servicios del File Transfer de Cambio de Boveda.
 * 
 */
public interface FileTransferCambioBovedaService {

    /**
     * Obtiene el File Transfer.
     * @param archivo
     * @param idFolio
     * @param usuario
     * @return La entidad de File Transfer.
     */
    FileTransferCambioBoveda getFileTransfer(List<String> archivo, String idFolio, String usuario); 

    /**
     * Realiza todo lo necesario para guardar el File Transfer, realizar validaciones de las operaciones y 
     * persistir en BD lo necesario.
     * @param fileTransfer El objeto que contiene parte de los datos a almacenar.
     * @return El FileTransfer creado con el listado de objetos FileTransferDetalleVO.
     * @throws BusinessException
     */
    FileTransferCambioBoveda crearFileTransfer(FileTransferCambioBoveda fileTransfer) throws BusinessException;

    /**
     * Procesa las operaciones del File Transfer.
     * @param fileTransfer La entidad File Transfer y tambien contiene las operaciones a persistir.
     * @throws BusinessException
     */
    void procesarOperacionesFileTransfer(FileTransferCambioBoveda fileTransfer) throws BusinessException;

    /**
     * Realiza la consulta de algun File Transfer activo.
     * @param idFolio El idFolio del usuario.
     * @return La entidad FileTransferCambioBoveda
     * @throws BusinessException
     */
    FileTransferCambioBoveda consultaFileTransferActivo(String idFolio) throws BusinessException;

    /**
     * Realiza la cancelacion del File Transfer.
     * @param fileTransfer El file transfer a cancelar.
     * @throws BusinessException
     */
    void cancelarFileTransfer(FileTransferCambioBoveda fileTransfer) throws BusinessException;

}
