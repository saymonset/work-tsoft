/**
 * Bursatec - Portal DALI
 * Copyrigth (c) 2014 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.filetransfer.validacion;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.bursatec.util.message.MessageResolver;
import com.indeval.portaldali.middleware.constantes.FileTransferConstantes;
import com.indeval.portaldali.middleware.dto.filetransfer.CampoRegFileTransDto;
import com.indeval.portaldali.middleware.services.filetransfer.util.Constantes;
import com.indeval.portaldali.persistence.model.FileTransfer;

/**
 * Clase abstracta que define los métodos para la validación de platillas de file transfer.
 * 
 * @author Pablo Balderas
 */
public abstract class ValidadorFileTransfer {
	
	/** Bean para acceso al properties de mensaje de errores */
	private MessageResolver messageResolver;

	/**
	 * Valida que el registro cumpla con la longitud requerida.
	 * @param tipoFileTransfer Tipo de file transfer.
	 * @param registro Registro a validar
	 */
	public abstract boolean validarLongitudRegistro(String tipoFileTransfer, String registro);

	/**
	 * Realiza la validación de un registro de file transfer.
	 * @param fileTransfer Registro de file transfer
	 * @param camposRegistro Campos que conforman el registro.
	 * @return true si la validacion es correcta; false si hubo errores.
	 */
	public abstract boolean validarRegistro(FileTransfer fileTransfer, List<CampoRegFileTransDto> camposRegistro);

	/**
	 * Valida los campos marcados como requeridos.
	 * @param fileTransfer Registro de file transfer
	 * @param camposRegistro Campos que conforman el registro.
	 * @return true si la validacion es correcta; false si hubo errores.
	 */
	protected boolean validarCamposRequeridos(FileTransfer fileTransfer, List<CampoRegFileTransDto> camposRegistro) {
		boolean conError = false;
		StringBuffer mensajeError = new StringBuffer();
		StringBuffer camposError = new StringBuffer();
		//Itera la lista de campos y valida los requeridos
		for (CampoRegFileTransDto campo : camposRegistro) {
			if(campo.isRequerido()) {
				if(campo.getValorCampo() instanceof String) {
					if(StringUtils.isBlank(campo.getValorCampo())) {
						campo.setCampoCorrecto(Boolean.FALSE);
						mensajeError.append(
							messageResolver.getMessage("J0401", new Object[]{campo.getEtiquetaCampo()}) + Constantes.GUION);
						camposError.append(campo.getNumeroCampo() + Constantes.GUION);
						conError = Boolean.TRUE;
					}
					else {
						campo.setCampoCorrecto(Boolean.TRUE);
					}
				}
			}
			else {
				campo.setCampoCorrecto(Boolean.TRUE);
			}
		}
		//Coloca los mensajes de error y los campos con error.
		if(conError) {
			if(fileTransfer.getError() != null) {				
				fileTransfer.setError(fileTransfer.getError() + mensajeError.toString());
			}
			else {
				fileTransfer.setError(mensajeError.toString());
			}
			if(fileTransfer.getCamposError() != null) {
				fileTransfer.setCamposError(fileTransfer.getCamposError() + camposError.toString());
			}
			else {				
				fileTransfer.setCamposError(camposError.toString());
			}
			
			fileTransfer.setEstado(Constantes.ESTADO_ERROR);
		}
		return !conError;
	}

	/**
	 * @return the messageResolver
	 */
	public MessageResolver getMessageResolver() {
		return messageResolver;
	}

	/**
	 * @param messageResolver the messageResolver to set
	 */
	public void setMessageResolver(MessageResolver messageResolver) {
		this.messageResolver = messageResolver;
	}

	
}
