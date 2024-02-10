package com.indeval.portalinternacional.middleware.services.divisioninternacional;


import java.util.List;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portalinternacional.middleware.servicios.vo.FileTransferCapturaBenefVO;
import com.indeval.portalinternacional.middleware.servicios.vo.ResumenCargaFilebenefVO;

public interface FileTransferCapturaBenefService<T>{
	
	/**
	 * Servicio para consultar la pre-carga del archivo con los beneficiarios dependiendo de la clave de usuario
	 * @param String claveUsuario
	 * @return ResumenCargaFilebenefVO
	 * @throws BusinessException
	 * */
	ResumenCargaFilebenefVO consultaCargaExistente(String claveUsuario)throws BusinessException;
	
	/**
	 * Servicio para guardar el archivo con registros de beneficiarios en la tabla temporal
	 * @param FileTransferCapturaBenefVO fileTransferCapturaBenefVO 
	 * @param String idClaveInstitucion
	 * @throws BusinessException
	 * */
	void guardaCargaTemporal(FileTransferCapturaBenefVO fileTransferCapturaBenefVO,String idClaveInstitucion) throws BusinessException;
	
	/**
	 * Servicio para consultar los registros pre-cargados, s&oacute;lo regresa los registros correctos
	 * @param String claveUsuario
	 * @return List lista de registros
	 * @throws BusinessException
	 * */
	List<T> consultaRegistrosCorrectos(String claveUsuario)throws BusinessException;
	
	
	/**
	 * Servicio para consultar los registros con error pre-cargados
	 * @param String claveUsuario
	 * @return List lista de registros
	 * @throws BusinessException
	 * */
	List<T> obtenerRegistrosError(String registrosXMLError)throws BusinessException;
	
	/**
	 * Servicio para guardar en la tabla de beneficiarios
	 * @param String claveUsuario
	 * @param Long tipoFormato
	 * @return int numero de registros guardados 
	 * @throws BusinessException
	 * */
	int guardaBeneficiarios(String claveUsuario,Long tipoFormato)throws BusinessException;
	
	/**
	 * Servicio para eliminar el registro de la carga temporal
	 * @param String claveUsuario
	 * @param Boolean cuentaConRegistrosCorrectos
	 * @throws BusinessException
	 * */
	void eliminaCargaExistente(String claveUsuario,Boolean cuentaConRegistrosCorrectos)throws BusinessException;
			
	
}
