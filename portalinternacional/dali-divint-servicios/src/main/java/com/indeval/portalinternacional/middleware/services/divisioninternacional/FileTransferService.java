/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.services.divisioninternacional;



import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portalinternacional.middleware.servicios.vo.FileTransferVO;
import com.indeval.portalinternacional.middleware.servicios.vo.ResumenVO;
import com.indeval.portalinternacional.middleware.servicios.vo.TotalesProcesoVO;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 * 
 */
public interface FileTransferService {

	/**
	 * Almacena la informaci&oacute;n que se leyo del archivo de texto en la tabla
	 * temporal correspondiente
	 * @param fileTransferVO
	 * @return int Numero de registros almacenados
	 * @throws BusinessException
	 */
	Integer almacenaInformacion(FileTransferVO fileTransferVO)
			throws BusinessException;

	/**
	 * Obtiene el resumen de la carga realizada
	 * @param fileTransferVO
	 * 
	 * @return ArrayList Lista de objetos de la clase ResumenVO
	 * @throws BusinessException
	 */
	ResumenVO obtieneResumen(FileTransferVO fileTransferVO)
			throws BusinessException;

	/**
	 * Muestra la lista de registros y el estado de cada uno de acuerdo a la
	 * validacion y/o carga realizada
	 * @param fileTransferVO TODO
	 * @return TotalesProcesoVO
	 * @throws Exception
	 */
	TotalesProcesoVO muestraInformacion(FileTransferVO fileTransferVO)
			throws BusinessException;

	/**
	 * Cancela el proceso borrando los datos en la tabla temporal
	 * catalogo..filetransfer
	 * @param fileTransferVO
	 * 
	 * @throws BusinessException
	 */
	void cancelaProceso(FileTransferVO fileTransferVO)
			throws BusinessException;

	/**
     * Graba la informacion correspondiente a traspasos de division internacional 
     * para que sea procesada
	 * @param fileTransferVO
	 * 
	 * @throws BusinessException
	 */
	void grabaInformacion(FileTransferVO fileTransferVO) throws BusinessException;

}
