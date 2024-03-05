/**
 * Bursatec - Portal DALI
 * Copyrigth (c) 2014 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.filetransfer;

import java.util.List;
import java.util.Map;

import com.indeval.portaldali.middleware.dto.filetransfer.RegistroFileTransDto;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.BusinessDataException;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;

/**
 * Interface de negocio que define los servicios para realizar el
 * File Transfer de Miscelanea Fiscal.
 * 
 * @author Pablo Balderas
 */
public interface FileTransferMisFisService {

	/** Tipo de operacion de miscelanea fiscal de dinero */
	String TIPO_OPERACION_MISC_FISCAL = "M";
	
	/**
	 * Método que realiza la validación y registro del file transfer en la base de datos.
	 * @param registros Lista con los registros del file transfer.
	 * @param agenteFirmado Agente firmado que ejecuta el file transfer.
	 * @param tipoProceso Tipo de proceso.
	 * @param usuario Usuario que ejecuta el file transfer.
	 * @return Objeto con el resumen de la operación realizada.
	 */
	TotalesProcesoVO registrarFileTransfer(List<String> registros, AgenteVO agenteFirmado, String tipoProceso, String usuario) throws BusinessDataException;
	
	/**
	 * Obtiene la información del file transfer cargado actualmente.
	 * @param agenteFirmado Agente firmado que ejecuta el file transfer.
	 * @param tipoProceso Tipo de proceso.
	 * @return Objeto con la información del file transfer cargado actualmente.
	 */
	TotalesProcesoVO obtenerInformacionFileTransfer(AgenteVO agenteFirmado, String tipoProceso) throws BusinessDataException;

	/**
	 * Obtiene los registros con estatus de error de la tabla de file transfer.
	 * @param agenteFirmado Agente firmado que ejecuta el file transfer.
	 * @param tipoProceso Tipo de proceso.
	 * @return Lista con los registros con estatus de error.
	 * @throws BusinessDataException En caso de ocurrir un error.
	 */
	List<RegistroFileTransDto> obtenerErroresFileTransfer(AgenteVO agenteFirmado, String tipoProceso) throws BusinessDataException;
	
	/**
	 * Obtiene el resumen de la operación de file transfer.
	 * @param agenteFirmado Agente firmado que ejecuto el file transfer.
	 * @param tipoFileTransfer Tipo de file transfer.
	 * @return Vo con el resumen de la operación.
	 * @throws BusinessException En caso de ocurrir un error.
	 */
	ResumenVO obtenerResumenFileTransfer(AgenteVO agenteFirmado, String tipoFileTransfer) throws BusinessException;
	
	/**
	 * Obtiene las operaciones de miscelanea fiscal que pueden ser envíadas al PFI. 
	 * @param idInstitucion Id de la institución firmada.
	 * @param folioInstitucion Folio de la institución firmada.
	 * @param tipoFileTransfer Tipo de file transfer.
	 * @return Lista con las operaciones de miscelanea fiscal que pueden ser envíadas el PFI.
	 * @throws BusinessException En caso de ocurrir un error.
	 */
	List<FileTransferMiscelaneaVo> obtenerOperacionesMiscelaneaFiscal(String idInstitucion, String folioInstitucion, String tipoFileTransfer)
			throws BusinessException;
	
	/**
	 * Cancela la carga del file transfer
	 * @param agenteFirmado Agente firmado que ejecuta el file transfer.
	 * @param tipoProceso Tipo de proceso.
	 * @throws BusinessException En caso de ocurrir un error.
	 */
	void cancelarFileTransfer(AgenteVO agenteFirmado, String tipoProceso) throws BusinessException;
	
	/**
	 * Actualiza los registros de file transfer que fueron enviados al PFI.
	 * @param agenteFirmado Agente firmado que ejecuta el file transfer.
	 * @param tipoProceso Tipo de proceso.
	 * @param resultadoOper Resultado de la operación de encolamiento al PFI.
	 * @throws BusinessException En caso de ocurrir un error.
	 */
	void actualizarRegistrosProcesadosPFI(AgenteVO agenteFirmado, String tipoProceso, Map<Integer, Boolean> resultadoOper) 
			throws BusinessException;
	
	/**
	 * Valida que exista el candado para la institución y tipo de file transfer y registros para procesar.
	 * @param agenteFirmado Agente firmado.
	 * @param tipoFileTransfer Tipo de file transfer.
	 * @return true si procede la operación; false en caso contrario
	 */
	boolean validarRegistrosAProcesar(AgenteVO agenteFirmado, String tipoFileTransfer);
}
