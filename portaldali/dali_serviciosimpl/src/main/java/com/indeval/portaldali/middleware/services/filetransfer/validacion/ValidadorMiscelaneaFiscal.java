/**
 * Bursatec - Portal DALI
 * Copyrigth (c) 2014 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.filetransfer.validacion;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.indeval.portaldali.middleware.constantes.FileTransferConstantes;
import com.indeval.portaldali.middleware.constantes.ValidacionConstantes;
import com.indeval.portaldali.middleware.dto.EmisionDTO;
import com.indeval.portaldali.middleware.dto.EmisoraDTO;
import com.indeval.portaldali.middleware.dto.InstitucionDTO;
import com.indeval.portaldali.middleware.dto.SerieDTO;
import com.indeval.portaldali.middleware.dto.TipoNaturalezaDTO;
import com.indeval.portaldali.middleware.dto.TipoValorDTO;
import com.indeval.portaldali.middleware.dto.filetransfer.CampoRegFileTransDto;
import com.indeval.portaldali.middleware.dto.filetransfer.MiscelaneaFiscalCapital;
import com.indeval.portaldali.middleware.dto.filetransfer.MiscelaneaFiscalDinero;
import com.indeval.portaldali.middleware.services.common.ValidacionService;
import com.indeval.portaldali.middleware.services.common.constants.DaliConstants;
import com.indeval.portaldali.middleware.services.common.constants.TipoCustodiaConstants;
import com.indeval.portaldali.middleware.services.filetransfer.util.Constantes;
import com.indeval.portaldali.persistence.dao.common.EmisionDaliDAO;
import com.indeval.portaldali.persistence.dao.common.InstitucionDaliDAO;
import com.indeval.portaldali.persistence.model.FileTransfer;

/**
 * Clase que realiza las validaciones para los registros en el proceso de file transfer de miscelanea fiscal.
 * 
 * @author Pablo Balderas
 */
public class ValidadorMiscelaneaFiscal extends ValidadorFileTransfer {
	
	/** Servicio para las validaciones de file transfer */
	private ValidacionService validacionService =  null;

	/** Dao para la consulta de emisiones */
	private EmisionDaliDAO emisionDaliDAO = null;

	/** Dao para la consulta de instituciones  */
	private InstitucionDaliDAO institucionDAO = null;
	
	/** Institución firmada que ejecuta el proceso file transfer */
	private InstitucionDTO institucionFirmada = null;
	
	/** Lista de cuentas invalidas para los traspasantes de mercado de capitales */
	private List<String> cuentasTraspasanteInvalidasMercadoCapitales;
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portaldali.middleware.services.filetransfer.validacion.ValidadorFileTransfer#validarLongitudRegistro(java.lang.String, java.lang.String)
	 */
	public boolean validarLongitudRegistro(String tipoFileTransfer, String registro) {
		boolean resultadoValidacion = false;
		if(FileTransferConstantes.TIPO_OPERACION_FC.equals(tipoFileTransfer)) {
			resultadoValidacion = FileTransferConstantes.LONGITUD_FT_MISCELANEA_FISCAL_FC == registro.length();
		}
		else if(FileTransferConstantes.TIPO_OPERACION_FD.equals(tipoFileTransfer)) {
			resultadoValidacion = FileTransferConstantes.LONGITUD_FT_MISCELANEA_FISCAL_FD == registro.length();
		}
		return resultadoValidacion;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portaldali.middleware.services.filetransfer.validacion.ValidadorFileTransfer#validarRegistro(com.indeval.portaldali.persistence.model.FileTransfer, java.util.List)
	 */
	@Override
	public boolean validarRegistro(FileTransfer fileTransfer, List<CampoRegFileTransDto> camposRegistro) {
		boolean resultado = validarCamposRequeridos(fileTransfer, camposRegistro);
		if(resultado) {			
			resultado = validarCamposMiscelaneaFiscal(fileTransfer, camposRegistro);
		}
		return resultado;
	}
	
	/**
	 * Realiza las validaciones propias de los formatos de miscelanea fiscal.
	 * @param fileTransfer Registro de file transfer
	 * @param camposRegistro Campos que conforman el registro
	 */
	private boolean validarCamposMiscelaneaFiscal(FileTransfer fileTransfer, List<CampoRegFileTransDto> camposRegistro) {
		boolean resultadoValidacion = true;
		StringBuffer mensajeError = new StringBuffer();
		StringBuffer camposError = new StringBuffer();
		//Valida la fecha de registro
		validarFechaRegistro(fileTransfer, camposRegistro, mensajeError, camposError);
		//Valida que la institución que ejecuto el file transfer sea traspasante o receptor
		institucionFirmada = buscarInstitucion(
			fileTransfer.getFileTransferPK().getIdInst() + 
			fileTransfer.getFileTransferPK().getFolioInst());
		validarTraspasanteReceptor(fileTransfer, camposRegistro, mensajeError, camposError);
		//Valida los campos realacionados a la emision
		validarEmision(fileTransfer, camposRegistro, mensajeError, camposError);
		//Valida la cantidad operada
		validarCantidadOperada(fileTransfer, camposRegistro, mensajeError, camposError);
		//Validar divisa
		validarDivisa(fileTransfer, camposRegistro, mensajeError, camposError);
		//Valida la fecha de adquisición
		if(FileTransferConstantes.TIPO_OPERACION_FC.equals(fileTransfer.getFileTransferPK().getTipoReg())) {
			if(!validacionService.validarFechaDiaHabil(
					camposRegistro.get(MiscelaneaFiscalDinero.FECHA_ADQ.getPosicion() - 1).getValorCampo(), 
					FileTransferConstantes.FORMATO_FECHA_MISCELANEA_FISCAL, false)) {
				camposRegistro.get(MiscelaneaFiscalDinero.FECHA_ADQ.getPosicion() - 1).setCampoCorrecto(false);
				mensajeError.append(
						getMessageResolver().getMessage("errorFechaAdquisicion") + Constantes.GUION);
				camposError.append(MiscelaneaFiscalDinero.FECHA_ADQ.getPosicion() + Constantes.GUION);
			}
		}
		//Valida el precio de adquisicion
		validarPrecioAdquisicion(fileTransfer, camposRegistro, mensajeError, camposError);
		//Valida el cliente
		validarCliente(fileTransfer, camposRegistro, mensajeError, camposError);
		//Valida el RFC/CURP
		validarRfcCurp(fileTransfer, camposRegistro, mensajeError, camposError);	
		//Valida la fecha y hora de concertacion
		validarFechaHoraConcertacion(fileTransfer, camposRegistro, mensajeError, camposError);
		//Valida el costo fiscal actualizado
		if(FileTransferConstantes.TIPO_OPERACION_FD.equals(fileTransfer.getFileTransferPK().getTipoReg())) {
			validacionesMercadoCapitales(fileTransfer, camposRegistro, mensajeError, camposError);
		}
		//Si hubo errores, los colocamos en el objeto fileTransfer
		if(StringUtils.isNotBlank(mensajeError.toString()) && StringUtils.isNotBlank(camposError.toString())) {
			fileTransfer.setEstado(Constantes.ESTADO_ERROR);
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
			resultadoValidacion = false;
		}
		mensajeError = null;
		camposError = null;
		return resultadoValidacion;
	}
	
	/**
	 * Realiza las validaciones propias del mercado de capitales
	 * @param fileTransfer Registro de file transfer
	 * @param camposRegistro Lista de campos
	 * @param conError Indica si existe error
	 * @param mensajeError Mensajes de error
	 * @param camposError Campos con error
	 */
	private void validacionesMercadoCapitales(FileTransfer fileTransfer, List<CampoRegFileTransDto> camposRegistro,
			StringBuffer mensajeError, StringBuffer camposError) {
		CampoRegFileTransDto cuentaTraspasante = 
			camposRegistro.get(MiscelaneaFiscalCapital.CTA_TRAS.getPosicion() - 1);
		CampoRegFileTransDto costoFiscalActualizado = 
			camposRegistro.get(MiscelaneaFiscalCapital.COSTO_FISCAL_ACTUALIZADO.getPosicion() - 1);
		// 1) Valida que la cuenta traspasante no se encuentre dentro de la lista de cuentas invalidas
		if(cuentasTraspasanteInvalidasMercadoCapitales.contains(cuentaTraspasante.getValorCampo())) {
			cuentaTraspasante.setCampoCorrecto(false);
			mensajeError.append(
				getMessageResolver().getMessage(
					"errorCampoInvalido", new Object[]{cuentaTraspasante.getEtiquetaCampo()}) + Constantes.GUION);
			camposError.append(cuentaTraspasante.getNumeroCampo() + Constantes.GUION);
		}
		
		// 2) Valida que el costo fiscal actualizado sea de 13 enteros y 8 decimales
		if(!validacionService.validarExpresionRegular(
				costoFiscalActualizado.getValorCampo(), ValidacionConstantes.ER_NUMERO_13ENTEROS_8DECIMALES)) {
			costoFiscalActualizado.setCampoCorrecto(false);
			mensajeError.append(
				getMessageResolver().getMessage(
					"errorDecimal", new Object[]{costoFiscalActualizado.getEtiquetaCampo(), 13, 8}) + Constantes.GUION);
			camposError.append(MiscelaneaFiscalCapital.COSTO_FISCAL_ACTUALIZADO.getPosicion() + Constantes.GUION);
		}
		//Valida que el costo fiscal actualizado sea mayor a 0
		else {
			BigDecimal valorCostoFiscalActualizado = 
				new BigDecimal(costoFiscalActualizado.getValorCampo());
			if (Constantes.BIG_DECIMAL_ZERO.compareTo(valorCostoFiscalActualizado) != -1) {
				costoFiscalActualizado.setCampoCorrecto(false);
				mensajeError.append(
					getMessageResolver().getMessage(
						"errorValorMayorA", 
						new Object[]{costoFiscalActualizado.getEtiquetaCampo(), 0}) + Constantes.GUION);
				camposError.append(costoFiscalActualizado.getNumeroCampo() + Constantes.GUION);
			}
		}
		cuentaTraspasante = null;
		costoFiscalActualizado = null;
	}
	
	/**
	 * Valida la información del traspasante y el receptor
	 * @param fileTransfer Registro de file transfer
	 * @param camposRegistro Lista de campos
	 * @param conError Indica si existe error
	 * @param mensajeError Mensajes de error
	 * @param camposError Campos con error
	 */
	private void validarTraspasanteReceptor(FileTransfer fileTransfer, List<CampoRegFileTransDto> camposRegistro,
			StringBuffer mensajeError, StringBuffer camposError) {
		CampoRegFileTransDto traspasante = null;
		CampoRegFileTransDto receptor = null;
		CampoRegFileTransDto cuentaTraspasante = null;
		CampoRegFileTransDto cuentaReceptor = null;
		
		if(FileTransferConstantes.TIPO_OPERACION_FC.equals(fileTransfer.getFileTransferPK().getTipoReg())) {
			traspasante = 
				camposRegistro.get(MiscelaneaFiscalDinero.CVE_TRAS.getPosicion() - 1);
			receptor = 
				camposRegistro.get(MiscelaneaFiscalDinero.CVE_RECEP.getPosicion() - 1);
			cuentaTraspasante = 
				camposRegistro.get(MiscelaneaFiscalDinero.CTA_TRAS.getPosicion() - 1);
			cuentaReceptor = 
				camposRegistro.get(MiscelaneaFiscalDinero.CTA_RECEP.getPosicion() - 1);
		}
		else if(FileTransferConstantes.TIPO_OPERACION_FD.equals(fileTransfer.getFileTransferPK().getTipoReg())) {
			traspasante = 
				camposRegistro.get(MiscelaneaFiscalCapital.CVE_TRAS.getPosicion() - 1);
			receptor = 
				camposRegistro.get(MiscelaneaFiscalCapital.CVE_RECEP.getPosicion() - 1);
			cuentaTraspasante = 
				camposRegistro.get(MiscelaneaFiscalCapital.CTA_TRAS.getPosicion() - 1);
			cuentaReceptor = 
				camposRegistro.get(MiscelaneaFiscalCapital.CTA_RECEP.getPosicion() - 1);
		}
		//Valida que la institución firmada sea traspasante y/o receptor
		if(!(institucionFirmada.getIdFolio().equals(traspasante.getValorCampo()) || 
				institucionFirmada.getIdFolio().equals(receptor.getValorCampo()))) {
			traspasante.setCampoCorrecto(false);
			receptor.setCampoCorrecto(false);
			mensajeError.append(
				getMessageResolver().getMessage("errorInstitucionFirmada") + Constantes.GUION);
			camposError.append(traspasante.getNumeroCampo() + Constantes.GUION);
			camposError.append(receptor.getNumeroCampo() + Constantes.GUION);
		}
		else {
			//Valida la institución y cuenta traspasante
			if(StringUtils.isBlank(traspasante.getValorCampo()) ||
					StringUtils.isBlank(cuentaTraspasante.getValorCampo()) || 
					!validacionService.validarInstitucionCuenta(
						traspasante.getValorCampo().substring(0, 2),
						traspasante.getValorCampo().substring(2, 5),
						cuentaTraspasante.getValorCampo(), 
						TipoCustodiaConstants.VALORES, 
						TipoNaturalezaDTO.PASIVO)) {
				traspasante.setCampoCorrecto(false);
				cuentaTraspasante.setCampoCorrecto(false);
				mensajeError.append(getMessageResolver().getMessage(
					"errorInstCta", 
					new Object[]{traspasante.getEtiquetaCampo(), 
						cuentaTraspasante.getEtiquetaCampo()}) + 
					Constantes.GUION);
				camposError.append(traspasante.getNumeroCampo() + Constantes.GUION);
				camposError.append(cuentaTraspasante.getNumeroCampo() + Constantes.GUION);
			}
			//Valida la institución y cuenta receptor
			if(StringUtils.isBlank(receptor.getValorCampo()) ||
					StringUtils.isBlank(cuentaReceptor.getValorCampo()) ||
					!validacionService.validarInstitucionCuenta(
						receptor.getValorCampo().substring(0, 2),
						receptor.getValorCampo().substring(2, 5),
						cuentaReceptor.getValorCampo(), 
						TipoCustodiaConstants.VALORES, 
						TipoNaturalezaDTO.PASIVO)) {
				receptor.setCampoCorrecto(false);
				cuentaReceptor.setCampoCorrecto(false);
				mensajeError.append(getMessageResolver().getMessage(
					"errorInstCta", 
					new Object[]{receptor.getNombreCampo(), 
						cuentaReceptor.getNombreCampo()}) + 
					Constantes.GUION);
				camposError.append(receptor.getNumeroCampo() + Constantes.GUION);
				camposError.append(cuentaReceptor.getNumeroCampo() + Constantes.GUION);
			}
			//Si el traspasante y el receptor son el mismo, valida que las cuentas sean distintas
			if(traspasante.getValorCampo().equals(receptor.getValorCampo()) && 
					cuentaTraspasante.getValorCampo().equals(cuentaReceptor.getValorCampo())) {
				cuentaTraspasante.setCampoCorrecto(false);
				cuentaReceptor.setCampoCorrecto(false);
				mensajeError.append(getMessageResolver().getMessage(
					"errorCuentasTraspasanteReceptorIguales") + Constantes.GUION);
				camposError.append(cuentaTraspasante.getNumeroCampo() + Constantes.GUION);
				camposError.append(cuentaReceptor.getNumeroCampo() + Constantes.GUION);
			}
			//Valida que el id institución del receptor no sea de la CCV (25)
			if (FileTransferConstantes.ID_INSTITUCION_CCV.equals(receptor.getValorCampo().substring(0, 2))) {
				receptor.setCampoCorrecto(false);
				mensajeError.append(
					getMessageResolver().getMessage("errorIdInstitucionCCV") + Constantes.GUION);
				camposError.append(receptor.getNumeroCampo() + Constantes.GUION);
			}
		}
		traspasante = null;
		receptor = null;
		cuentaTraspasante = null;
		cuentaReceptor = null;
	}

	/**
	 * Valida la fecha de registro
	 * @param fileTransfer Registro de file transfer
	 * @param camposRegistro Lista de campos
	 * @param conError Indica si existe error
	 * @param mensajeError Mensajes de error
	 * @param camposError Campos con error
	 */
	private void validarFechaRegistro(FileTransfer fileTransfer, List<CampoRegFileTransDto> camposRegistro,
			StringBuffer mensajeError, StringBuffer camposError) {
		CampoRegFileTransDto fechaRegistro = null;
		if(FileTransferConstantes.TIPO_OPERACION_FC.equals(fileTransfer.getFileTransferPK().getTipoReg())) {
			fechaRegistro = 
				camposRegistro.get(MiscelaneaFiscalDinero.FECHA_REGISTRO.getPosicion() - 1);
		}
		else if(FileTransferConstantes.TIPO_OPERACION_FD.equals(fileTransfer.getFileTransferPK().getTipoReg())) {
			fechaRegistro = 
				camposRegistro.get(MiscelaneaFiscalCapital.FECHA_REGISTRO.getPosicion() - 1);
		}
		if(!validacionService.validarFechaDiaHabil(
				fechaRegistro.getValorCampo(), FileTransferConstantes.FORMATO_FECHA_MISCELANEA_FISCAL, true)) {
			fechaRegistro.setCampoCorrecto(false);
			mensajeError.append(getMessageResolver().getMessage("errorFechaRegistro") + Constantes.GUION);
			camposError.append(fechaRegistro.getNumeroCampo() + Constantes.GUION);
		}
		fechaRegistro = null;
	}
	
	/**
	 * Valida la fecha de registro
	 * @param fileTransfer Registro de file transfer
	 * @param camposRegistro Lista de campos
	 * @param conError Indica si existe error
	 * @param mensajeError Mensajes de error
	 * @param camposError Campos con error
	 */
	private void validarFechaHoraConcertacion(FileTransfer fileTransfer, List<CampoRegFileTransDto> camposRegistro,
			StringBuffer mensajeError, StringBuffer camposError) {
		CampoRegFileTransDto fechaHoraConcert = null;
		if(FileTransferConstantes.TIPO_OPERACION_FC.equals(fileTransfer.getFileTransferPK().getTipoReg())) {
			fechaHoraConcert = 
				camposRegistro.get(MiscelaneaFiscalDinero.FECHA_HORA_CONCERTACION.getPosicion() - 1);
		}
		else if(FileTransferConstantes.TIPO_OPERACION_FD.equals(fileTransfer.getFileTransferPK().getTipoReg())) {
			fechaHoraConcert = 
				camposRegistro.get(MiscelaneaFiscalCapital.FECHA_HORA_CONCERTACION.getPosicion() - 1);
		}
		if(!StringUtils.isEmpty(fechaHoraConcert.getValorCampo()) 
				&& (!validacionService.validarFechaValida(fechaHoraConcert.getValorCampo(), 
						FileTransferConstantes.FORMATO_FECHA_HORA_MISCELANEA_FISCAL))) {
			fechaHoraConcert.setCampoCorrecto(false);
			mensajeError.append(getMessageResolver().getMessage("errorFechaHoraConcertacion") + Constantes.GUION);
			camposError.append(fechaHoraConcert.getNumeroCampo() + Constantes.GUION);
		}
		fechaHoraConcert = null;
	}


	/**
	 * Valida el campo cliente
	 * @param fileTransfer Registro de file transfer
	 * @param camposRegistro Lista de campos
	 * @param conError Indica si existe error
	 * @param mensajeError Mensajes de error
	 * @param camposError Campos con error
	 */
	private void validarCliente(FileTransfer fileTransfer, List<CampoRegFileTransDto> camposRegistro,
			StringBuffer mensajeError, StringBuffer camposError) {
		CampoRegFileTransDto cliente = null;
		if(FileTransferConstantes.TIPO_OPERACION_FC.equals(fileTransfer.getFileTransferPK().getTipoReg())) {
			cliente = 
				camposRegistro.get(MiscelaneaFiscalDinero.CLIENTE.getPosicion() - 1);
		}
		else if(FileTransferConstantes.TIPO_OPERACION_FD.equals(fileTransfer.getFileTransferPK().getTipoReg())) {
			cliente = 
				camposRegistro.get(MiscelaneaFiscalCapital.CLIENTE.getPosicion() - 1);
		}
		if(!validacionService.validarExpresionRegular(cliente.getValorCampo(), ValidacionConstantes.ER_ALFANUMERICOS_ESPACIO_PUNTO)) {
			cliente.setCampoCorrecto(false);
			mensajeError.append(getMessageResolver().getMessage("errorCliente") + Constantes.GUION);
			camposError.append(cliente.getNumeroCampo() + Constantes.GUION);
		}
		cliente = null;
	}
	
	
	/**
	 * Valida el RFC/CURP
	 * @param fileTransfer Registro de file transfer
	 * @param camposRegistro Lista de campos
	 * @param conError Indica si existe error
	 * @param mensajeError Mensajes de error
	 * @param camposError Campos con error
	 */
	private void validarRfcCurp(FileTransfer fileTransfer, List<CampoRegFileTransDto> camposRegistro,
			StringBuffer mensajeError, StringBuffer camposError) {
		CampoRegFileTransDto rfcCurp = null;
		CampoRegFileTransDto extranjero = null;
		if(FileTransferConstantes.TIPO_OPERACION_FC.equals(fileTransfer.getFileTransferPK().getTipoReg())) {
			rfcCurp = 
				camposRegistro.get(MiscelaneaFiscalDinero.RFC_CURP.getPosicion() - 1);
			extranjero =
				camposRegistro.get(MiscelaneaFiscalDinero.EXTRANJERO.getPosicion() - 1);
		}
		else if(FileTransferConstantes.TIPO_OPERACION_FD.equals(fileTransfer.getFileTransferPK().getTipoReg())) {
			rfcCurp = 
				camposRegistro.get(MiscelaneaFiscalCapital.RFC_CURP.getPosicion() - 1);
			extranjero = 
				camposRegistro.get(MiscelaneaFiscalCapital.EXTRANJERO.getPosicion() - 1);
		}
		if(FileTransferConstantes.MISCELANEA_FISCAL_EXTRANJERO.equals(extranjero.getValorCampo()) || 
				FileTransferConstantes.MISCELANEA_FISCAL_NACIONAL.equals(extranjero.getValorCampo())) {
			//Si el campo extranjero es "0", el RFC/CURP es obligatorio
			if(FileTransferConstantes.MISCELANEA_FISCAL_NACIONAL.equals(extranjero.getValorCampo())) {
				if(StringUtils.isNotBlank(rfcCurp.getValorCampo())) {			
					if(!validacionService.validarRfcCurp(
							rfcCurp.getValorCampo())) {
						rfcCurp.setCampoCorrecto(false);
						mensajeError.append(
								getMessageResolver().getMessage("errorRfcCurp") + Constantes.GUION);
						camposError.append(rfcCurp.getNumeroCampo() + Constantes.GUION);
					}
				}
				else {
					rfcCurp.setCampoCorrecto(false);
					mensajeError.append(
						getMessageResolver().getMessage("J0401", 
							new Object[]{rfcCurp.getEtiquetaCampo()}) + Constantes.GUION);
					camposError.append(rfcCurp.getNumeroCampo() + Constantes.GUION);
				}
			}
			else if(FileTransferConstantes.MISCELANEA_FISCAL_EXTRANJERO.equals(extranjero.getValorCampo())) {
				rfcCurp.setValorCampo(FileTransferConstantes.RFC_EXTRANJERO);
			}
		}
		else {
			extranjero.setCampoCorrecto(false);
			mensajeError.append(
					getMessageResolver().getMessage("errorDosValores", new Object[]{
						extranjero.getEtiquetaCampo(),
						FileTransferConstantes.MISCELANEA_FISCAL_NACIONAL,
						FileTransferConstantes.MISCELANEA_FISCAL_EXTRANJERO
					}) + Constantes.GUION);
			camposError.append(extranjero.getNumeroCampo() + Constantes.GUION);
		}
		rfcCurp = null;
		extranjero = null;
	}

	/**
	 * Valida la información asociada con la emisión.
	 * @param fileTransfer Registro de file transfer
	 * @param camposRegistro Lista de campos
	 * @param conError Indica si existe error
	 * @param mensajeError Mensajes de error
	 */
	private void validarEmision(
			FileTransfer fileTransfer, List<CampoRegFileTransDto> camposRegistro, 
			StringBuffer mensajeError, StringBuffer camposError) {
		CampoRegFileTransDto tipoValor = null;
		CampoRegFileTransDto emisora = null;
		CampoRegFileTransDto serie = null;
		CampoRegFileTransDto cupon = null;
		CampoRegFileTransDto isin = null;
		CampoRegFileTransDto boveda = null;
		if(FileTransferConstantes.TIPO_OPERACION_FC.equals(fileTransfer.getFileTransferPK().getTipoReg())) {
			tipoValor = 
				camposRegistro.get(MiscelaneaFiscalDinero.TV.getPosicion() - 1);
			emisora = 
				camposRegistro.get(MiscelaneaFiscalDinero.EMISORA.getPosicion() - 1);
			serie = 
				camposRegistro.get(MiscelaneaFiscalDinero.SERIE.getPosicion() - 1);
			cupon = 
				camposRegistro.get(MiscelaneaFiscalDinero.CUPON.getPosicion() - 1);
			isin = 
				camposRegistro.get(MiscelaneaFiscalDinero.ISIN.getPosicion() - 1);
			boveda = 
				camposRegistro.get(MiscelaneaFiscalDinero.BOVEDA.getPosicion() - 1);
		}
		else if(FileTransferConstantes.TIPO_OPERACION_FD.equals(fileTransfer.getFileTransferPK().getTipoReg())) {
			tipoValor = 
					camposRegistro.get(MiscelaneaFiscalCapital.TV.getPosicion() - 1);
			emisora = 
				camposRegistro.get(MiscelaneaFiscalCapital.EMISORA.getPosicion() - 1);
			serie = 
				camposRegistro.get(MiscelaneaFiscalCapital.SERIE.getPosicion() - 1);
			cupon = 
				camposRegistro.get(MiscelaneaFiscalCapital.CUPON.getPosicion() - 1);
			isin = 
				camposRegistro.get(MiscelaneaFiscalCapital.ISIN.getPosicion() - 1);
			boveda = 
				camposRegistro.get(MiscelaneaFiscalCapital.BOVEDA.getPosicion() - 1);
		}
		
		EmisionDTO emision = buscarEmision(tipoValor.getValorCampo(), emisora.getValorCampo(), serie.getValorCampo());
		//Si la emisión existe, valida el cupon, el isin y la boveda.
		if(emision != null) {
			//Valida que la emisión pertenesca al mercado requerido
			boolean mercadoCorrecto = true;
			if(FileTransferConstantes.TIPO_OPERACION_FC.equals(fileTransfer.getFileTransferPK().getTipoReg())) {
				if(DaliConstants.ID_MERCADO_PAPEL_BANCARIO != emision.getTipoValor().getMercado().getId() && 
						DaliConstants.ID_MERCADO_PAPEL_GUBER != emision.getTipoValor().getMercado().getId()) {
					mercadoCorrecto = false;
				}
			}
			else if(FileTransferConstantes.TIPO_OPERACION_FD.equals(fileTransfer.getFileTransferPK().getTipoReg())) {
				if(DaliConstants.ID_MERCADO_CAPITALES != emision.getTipoValor().getMercado().getId()) {
					mercadoCorrecto = false;
				}
			}
			if(!mercadoCorrecto) {
				mensajeError.append(
						getMessageResolver().getMessage("errorEmisionMercado") + Constantes.GUION);
					camposError.append(tipoValor.getNumeroCampo() + Constantes.GUION);
					camposError.append(emisora.getNumeroCampo() + Constantes.GUION);
					camposError.append(serie.getNumeroCampo() + Constantes.GUION);
					tipoValor.setCampoCorrecto(false);
					emisora.setCampoCorrecto(false);
					serie.setCampoCorrecto(false);
			}
			//Valida que el cupon sea vigente
			if(!(emision != null && cupon.getValorCampo().equals(emision.getCupon()))) {
				mensajeError.append(
					getMessageResolver().getMessage("errorCuponNoVigente") + Constantes.GUION);
				camposError.append(cupon.getNumeroCampo() + Constantes.GUION);
				cupon.setCampoCorrecto(false);
			}
			//Valida el ISIN
			if(StringUtils.isNotBlank(isin.getValorCampo())) {
				if(!(emision != null && isin.getValorCampo().equals(emision.getIsin()))) {
					mensajeError.append(
						getMessageResolver().getMessage("errorIsinInvalido") + Constantes.GUION);
					camposError.append(isin.getNumeroCampo() + Constantes.GUION);
					isin.setCampoCorrecto(false);
				}
			}
			//Si el isin no viene en el registro, se llena
			else {
				isin.setValorCampo(emision.getIsin());
			}
			//Valida la boveda
			if(StringUtils.isNotBlank(boveda.getValorCampo())) {
				if(!(emision != null && emision.getBoveda().getNombreCorto().equals(boveda.getValorCampo()))) {
					mensajeError.append(
							getMessageResolver().getMessage("errorBovedaInvalida") + Constantes.GUION);
					camposError.append(boveda.getNumeroCampo() + Constantes.GUION);
					boveda.setCampoCorrecto(false);
				}
			}
			//Si la bóveda no viene en el registro, se llena
			else {
				boveda.setValorCampo(emision.getBoveda().getNombreCorto());
			}
		}
		else {			
			mensajeError.append(
				getMessageResolver().getMessage("errorEmisionNoExiste") + Constantes.GUION);
			camposError.append(tipoValor.getNumeroCampo() + Constantes.GUION);
			camposError.append(emisora.getNumeroCampo() + Constantes.GUION);
			camposError.append(serie.getNumeroCampo() + Constantes.GUION);
			tipoValor.setCampoCorrecto(false);
			emisora.setCampoCorrecto(false);
			serie.setCampoCorrecto(false);
		}
		tipoValor = null;
		emisora = null;
		serie = null;
		cupon = null;
		isin = null;
		boveda = null;
	}
	
	/**
	 * Valida el precio de adquisicion
	 * @param fileTransfer Registro de file transfer
	 * @param camposRegistro Lista de campos
	 * @param conError Indica si existe error
	 * @param mensajeError Mensajes de error
	 */
	private void validarPrecioAdquisicion(
			FileTransfer fileTransfer, List<CampoRegFileTransDto> camposRegistro, 
			StringBuffer mensajeError, StringBuffer camposError) {
		
		CampoRegFileTransDto precioAdquisicion = null;
		if(FileTransferConstantes.TIPO_OPERACION_FC.equals(fileTransfer.getFileTransferPK().getTipoReg())) {
			precioAdquisicion = 
				camposRegistro.get(MiscelaneaFiscalDinero.PRECIO_ADQ.getPosicion() - 1);
		}
		else if(FileTransferConstantes.TIPO_OPERACION_FD.equals(fileTransfer.getFileTransferPK().getTipoReg())) {
			precioAdquisicion = 
				camposRegistro.get(MiscelaneaFiscalCapital.PRECIO_ADQ.getPosicion() - 1);
		}
		//Valida que el precio de adquisicion sea un valor de máximo 13 enteros y 8 decimales
		if(!validacionService.validarExpresionRegular(precioAdquisicion.getValorCampo(), 
				ValidacionConstantes.ER_NUMERO_13ENTEROS_8DECIMALES)) {
			precioAdquisicion.setCampoCorrecto(false);
			mensajeError.append(
				getMessageResolver().getMessage(
					"errorDecimal", 
					new Object[]{precioAdquisicion.getEtiquetaCampo(), 13, 8}) + Constantes.GUION);
			camposError.append(precioAdquisicion.getNumeroCampo() + Constantes.GUION);
		}
		//Valida que el precio de adquisión sea mayor a 0
		else {
			BigDecimal valorPrecioAdquisicion = 
				new BigDecimal(precioAdquisicion.getValorCampo());
			if (Constantes.BIG_DECIMAL_ZERO.compareTo(valorPrecioAdquisicion) != -1) {
				precioAdquisicion.setCampoCorrecto(false);
				mensajeError.append(
					getMessageResolver().getMessage(
						"errorValorMayorA", 
						new Object[]{precioAdquisicion.getEtiquetaCampo(), 0}) + Constantes.GUION);
				camposError.append(precioAdquisicion.getNumeroCampo() + Constantes.GUION);
			}
		}
		precioAdquisicion = null;
	}
	
	/**
	 * Valida la cantidad operada
	 * @param fileTransfer Registro de file transfer
	 * @param camposRegistro Lista de campos
	 * @param conError Indica si existe error
	 * @param mensajeError Mensajes de error
	 * @param camposError Campos con error
	 */
	private void validarCantidadOperada(
			FileTransfer fileTransfer, List<CampoRegFileTransDto> camposRegistro, 
			StringBuffer mensajeError, StringBuffer camposError) {
		CampoRegFileTransDto cantidadOperada = null;
		if(FileTransferConstantes.TIPO_OPERACION_FC.equals(fileTransfer.getFileTransferPK().getTipoReg())) {
			cantidadOperada = 
				camposRegistro.get(MiscelaneaFiscalDinero.CANTIDAD_OPERADA.getPosicion() - 1);
		}
		else if(FileTransferConstantes.TIPO_OPERACION_FD.equals(fileTransfer.getFileTransferPK().getTipoReg())) {
			cantidadOperada = 
				camposRegistro.get(MiscelaneaFiscalCapital.CANTIDAD_OPERADA.getPosicion() - 1);
		}
		if(!validacionService.validarExpresionRegular(cantidadOperada.getValorCampo(), 
				ValidacionConstantes.ER_NUMERO_14ENTEROS)) {
			cantidadOperada.setCampoCorrecto(false);
			mensajeError.append(
				getMessageResolver().getMessage("errorEntero", 
					new Object[]{cantidadOperada.getEtiquetaCampo(), cantidadOperada.getLongitudCampo()}) 
					+ Constantes.GUION);
			camposError.append(cantidadOperada.getNumeroCampo() + Constantes.GUION);
		}
		//Valida que la cantidad operada sea mayor a 0
		else {
			BigInteger valorCantidadOperada = 
				new BigInteger(cantidadOperada.getValorCampo());
			if (Constantes.BIG_INTEGER_ZERO.compareTo(valorCantidadOperada) != -1) {
				cantidadOperada.setCampoCorrecto(false);
				mensajeError.append(
					getMessageResolver().getMessage(
						"errorValorMayorA", 
						new Object[]{cantidadOperada.getEtiquetaCampo(), 0}) + Constantes.GUION);
				camposError.append(cantidadOperada.getNumeroCampo() + Constantes.GUION);
			}
		}
		cantidadOperada = null;
	}
	
	/**
	 * Valida la divisa
	 * @param fileTransfer Registro de file transfer
	 * @param camposRegistro Lista de campos
	 * @param mensajeError Mensajes de error
	 * @param camposError Campos con error
	 */
	private void validarDivisa(
			FileTransfer fileTransfer, List<CampoRegFileTransDto> camposRegistro, 
			StringBuffer mensajeError, StringBuffer camposError) {
		CampoRegFileTransDto divisa = null;
		if(FileTransferConstantes.TIPO_OPERACION_FC.equals(fileTransfer.getFileTransferPK().getTipoReg())) {
			divisa = 
				camposRegistro.get(MiscelaneaFiscalDinero.DIVISA.getPosicion() - 1);
		}
		else if(FileTransferConstantes.TIPO_OPERACION_FD.equals(fileTransfer.getFileTransferPK().getTipoReg())) {
			divisa = 
				camposRegistro.get(MiscelaneaFiscalCapital.DIVISA.getPosicion() - 1);
		}
		if(!validacionService.validarDivisa(divisa.getValorCampo())) {
			divisa.setCampoCorrecto(false);
			mensajeError.append(
				getMessageResolver().getMessage("errorDivisa") + Constantes.GUION);
			camposError.append(divisa.getNumeroCampo() + Constantes.GUION);
		}
		divisa = null;
	}

	/**
	 * Busca una emisión por su TV, Emisora y Serie.
	 * @param tv Tv de la emisión.
	 * @param emisora Emisora de la emisión.
	 * @param serie Serie de la emisión.
	 * @return Dto con la emisión o null si no se encuentra.
	 */
	private EmisionDTO buscarEmision(String tv, String emisora, String serie) {
		EmisionDTO emision = null;
		if(StringUtils.isNotBlank(tv) && StringUtils.isNotBlank(emisora) && StringUtils.isNotBlank(serie)) {
			emision = new EmisionDTO();
			TipoValorDTO tipoValorDto = new TipoValorDTO();
			tipoValorDto.setClaveTipoValor(tv);
			
			EmisoraDTO emisoraDto = new EmisoraDTO();
			emisoraDto.setDescripcion(emisora);
			
			SerieDTO serieDto = new SerieDTO();
			serieDto.setSerie(serie);
			
			emision.setTipoValor(tipoValorDto);
			emision.setEmisora(emisoraDto);
			emision.setSerie(serieDto);
			
			List<EmisionDTO> resultadoBusqueda = 
					emisionDaliDAO.consultarEmisionesPorDescripciones(emision, null);
			if(resultadoBusqueda != null && !resultadoBusqueda.isEmpty() && resultadoBusqueda.size() == 1) {
				emision = resultadoBusqueda.get(0);
			}
			else {
				emision = null;
			}
		}
		return emision;
	}
	
	/**
	 * Busca la institucion por su id y folio.
	 * @param idFolio Id y folio para buscar la institucion.
	 * @return Dto con los datos de la institucion o null si no existe.
	 */
	private InstitucionDTO buscarInstitucion(String idFolio) {
		InstitucionDTO institucion = null;
		if(StringUtils.isNotBlank(idFolio)) {
			institucion = institucionDAO.buscarInstitucionPorClaveYFolio(idFolio);
		}
		return institucion;
	}
	
	/**
	 * @return the validacionService
	 */
	public ValidacionService getValidacionService() {
		return validacionService;
	}

	/**
	 * @param validacionService the validacionService to set
	 */
	public void setValidacionService(ValidacionService validacionService) {
		this.validacionService = validacionService;
	}

	/**
	 * @return the emisionDaliDAO
	 */
	public EmisionDaliDAO getEmisionDaliDAO() {
		return emisionDaliDAO;
	}

	/**
	 * @param emisionDaliDAO the emisionDaliDAO to set
	 */
	public void setEmisionDaliDAO(EmisionDaliDAO emisionDaliDAO) {
		this.emisionDaliDAO = emisionDaliDAO;
	}

	/**
	 * @return the institucionDAO
	 */
	public InstitucionDaliDAO getInstitucionDAO() {
		return institucionDAO;
	}

	/**
	 * @param institucionDAO the institucionDAO to set
	 */
	public void setInstitucionDAO(InstitucionDaliDAO institucionDAO) {
		this.institucionDAO = institucionDAO;
	}

	/**
	 * @return the cuentasTraspasanteInvalidasMercadoCapitales
	 */
	public List<String> getCuentasTraspasanteInvalidasMercadoCapitales() {
		return cuentasTraspasanteInvalidasMercadoCapitales;
	}

	/**
	 * @param cuentasTraspasanteInvalidasMercadoCapitales the cuentasTraspasanteInvalidasMercadoCapitales to set
	 */
	public void setCuentasTraspasanteInvalidasMercadoCapitales(
			List<String> cuentasTraspasanteInvalidasMercadoCapitales) {
		this.cuentasTraspasanteInvalidasMercadoCapitales = cuentasTraspasanteInvalidasMercadoCapitales;
	}

}
