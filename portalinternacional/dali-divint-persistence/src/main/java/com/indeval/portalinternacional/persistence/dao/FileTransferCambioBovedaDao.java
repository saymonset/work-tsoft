/**
 * Copyright (c) 2017 Bursatec. All Rights Reserved
 */
package com.indeval.portalinternacional.persistence.dao;

import java.util.List;

import com.indeval.portaldali.persistence.modelo.BitacoraOperaciones;
import com.indeval.portalinternacional.middleware.servicios.modelo.FileTransferCambioBoveda;

/**
 * Interfaz de DAO para las operaciones relacionadas con el File Transfer de Cambio de B&oacute;veda.
 * 
 */
public interface FileTransferCambioBovedaDao {

   /**
    * Registra la infomacion de un File Transfer.
    * @param fileTransfer
    * @return idFileTransfer
    */
   Integer save(FileTransferCambioBoveda fileTransfer);

   /**
    * Actualiza la infomacion de un registro de File Transfer.
    * @param fileTransfer
    */
   void update(FileTransferCambioBoveda fileTransfer);   

   /**
    * Obtiene un registro File Transfer por id-folio de institucion y en estatus CREADO.
    * @param idFolio
    * @return
    */
   FileTransferCambioBoveda getByIdFolioYEstadoCreado(String idFolio);

   /**
    * Registra la informacion de un registro de la tabla T_BITACORA_OPERACIONES
    * @param bitacoraOperaciones
    * @return
    */
   public Integer salvarBitacoraOperaciones(BitacoraOperaciones bitacoraOperaciones);

   /**
    * Hace un flush a la session
    */
   void flush();

}
