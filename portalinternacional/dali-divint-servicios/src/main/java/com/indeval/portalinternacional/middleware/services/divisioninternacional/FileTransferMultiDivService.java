/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.services.divisioninternacional;


import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.persistence.modelo.Boveda;
import com.indeval.portaldali.persistence.modelo.Divisa;
import com.indeval.portaldali.persistence.modelo.Institucion;
import com.indeval.portalinternacional.middleware.servicios.modelo.FileTransferDivisas;
import com.indeval.portalinternacional.middleware.servicios.vo.FileTransferVO;
import com.indeval.portalinternacional.middleware.servicios.vo.ResumenVO;
import com.indeval.portalinternacional.middleware.servicios.vo.TotalesProcesoVO;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 * 
 */
public interface FileTransferMultiDivService {

	/**
	 * Almacena la informaci&oacute;n que se leyo del archivo de texto en la tabla
	 * temporal correspondiente
	 * @param fileTransferVO
	 * @return int Numero de registros almacenados
	 * @throws BusinessException
	 */
//	Integer almacenaInformacion(FileTransferVO fileTransferVO)
//			throws BusinessException;

	/**
	 * Obtiene el resumen de la carga realizada
	 * @param idFileTransferDivisas
	 * 
	 * @return ArrayList Lista de objetos de la clase ResumenVO
	 * @throws BusinessException
	 */
	ResumenVO obtieneResumen(Long idFileTransferDivisas,FileTransferVO fileTransferVO)
			throws BusinessException;

	/**
	 * Muestra la lista de registros y el estado de cada uno de acuerdo a la
	 * validacion y/o carga realizada
	 * @param fileTransferVO TODO
	 * @return TotalesProcesoVO
	 * @throws Exception
	 */
	TotalesProcesoVO muestraInformacion(Long idFileTransfer,
										FileTransferVO fileTransferVO,Map<Integer, String> mapa)
			throws BusinessException;

	/**
     * Graba la informacion correspondiente a traspasos de division internacional 
     * para que sea procesada
	 * @param fileTransferVO
	 * 
	 * @throws BusinessException
	 */
	void grabaInformacion(FileTransferVO fileTransferVO) throws BusinessException;

	 /**
		* @author Genner.Cardenas.Ramirez
		* @param fileTransferVO
		* @return
		* @throws BusinessException
		*/
	 Integer almacenaInformacionExcelMD(Long idFileTransfer,FileTransferVO fileTransferVO,
										Map<Integer, String> mapaAtrib,
										Map<Integer, String> mapaValAtrib,
										Map<String, Boveda> bovedas,
										Map<String, Divisa> divisas,
										Map<String, Institucion> instituciones) throws BusinessException;

	List<FileTransferDivisas> findFileTransferDivIntByUsuario(String cveUsuario);

	void cancelaProceso(Long idFileTransfer,FileTransferVO fileTransferVO)
			throws BusinessException;
}
