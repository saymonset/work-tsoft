/**
 * Bursatec - Portal Internacional
 * Copyrigth (c) 2015 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.services.validador.w8imy;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.indeval.portaldali.persistence.modelo.Institucion;
import com.indeval.portalinternacional.middleware.servicios.constantes.BeneficiariosConstantes;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.constantes.FileTransferBenefConstantes;
import com.indeval.portalinternacional.middleware.servicios.constantes.OpcionesCampo4W8IMY;
import com.indeval.portalinternacional.middleware.servicios.constantes.OpcionesCampo5W8IMY;
import com.indeval.portalinternacional.middleware.servicios.modelo.filetransfer.beneficiario.tipoformato.FileTransferFormaW8IMY2015;

/**
 * Clase que implementa las validaciones utilizadas en el proceso de file transfer para el formato
 * W8IMY 2015.
 * 
 * @author Pablo Balderas
 *
 */
public class ValidatorFormatW8IMY2015ServiceImpl extends ValidatorFormatW8IMY2015 implements Constantes {

	/** Mapa donde se almacenan temporalmente las instituciones capturadas en el file transfer y reducir los accesos a la BD */
	private Map<String,Long> mapaInstituciones = null;
	
	/** Bandera que indica si los registros que se validan son correctos  */
	private boolean valido = false;
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.validador.ValidatorBeneficiario#esRegistroFileTransferValido(com.indeval.portalinternacional.middleware.servicios.modelo.filetransfer.beneficiario.tipoformato.FileTransferForma, java.util.List)
	 */
	@Override
	public boolean esRegistroFileTransferValido(FileTransferFormaW8IMY2015 fileTransferForma, List<FileTransferFormaW8IMY2015> listaRegistrosErrorAux) {
		valido = true;
		//Valida los campos comunes
		valido = validarCamposComunes(fileTransferForma, BeneficiariosConstantes.ID_FORMATO_W8_IMY, listaRegistrosErrorAux);
		//Valida el campo 1
		if(valido) {
			valido = validarCadenaObligatorioLongitud(fileTransferForma.getCampo1(), FileTransferBenefConstantes.LONGITUD_250,
				fileTransferForma, listaRegistrosErrorAux, FileTransferBenefConstantes.NAME_ORGANIZATION);
		}
		//Valida el campo 2
		if(valido) {
			valido = validarCadenaObligatorioLongitud(fileTransferForma.getCampo2(), FileTransferBenefConstantes.LONGITUD_50, 
				fileTransferForma, listaRegistrosErrorAux, FileTransferBenefConstantes.COUNTRY_INCORPORATION);
		}
		//Valida el campo 4
		if(valido && StringUtils.isBlank(fileTransferForma.getCampo4())) {
			valido = false;
			agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
				FileTransferBenefConstantes.CHAPTER_3_STATUS, Constantes.MENSAJE_CAMPO_NULO);
		}
		if(valido && !OpcionesCampo4W8IMY.isInEnum(fileTransferForma.getCampo4(), OpcionesCampo4W8IMY.class)) {
			valido = false;
			agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
				FileTransferBenefConstantes.CHAPTER_3_STATUS, Constantes.MENSAJE_CAMPO_INVALIDO);
		}
		//Valida el campo 5
		if(valido && StringUtils.isBlank(fileTransferForma.getCampo5())) {
			valido = false;
			agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
				FileTransferBenefConstantes.CHAPTER_4_STATUS, Constantes.MENSAJE_CAMPO_NULO);
		}
		if(valido && !OpcionesCampo5W8IMY.isInEnum(fileTransferForma.getCampo5(), OpcionesCampo5W8IMY.class)) {
			valido = false;
			agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
				FileTransferBenefConstantes.CHAPTER_4_STATUS, Constantes.MENSAJE_CAMPO_INVALIDO);
		}
		//Valida el campo 6 (Domicilio)
		if(valido) {
			valido = validarCadenaObligatorioLongitud(fileTransferForma.getCampo6a(), FileTransferBenefConstantes.LONGITUD_150,
				fileTransferForma, listaRegistrosErrorAux, FileTransferBenefConstantes.PERMANENT_RESIDENCE_ADDRESS);
		}
		if(valido) {			
			valido = validarCadenaObligatorioLongitud(fileTransferForma.getCampo6b(), FileTransferBenefConstantes.LONGITUD_150,
				fileTransferForma, listaRegistrosErrorAux, FileTransferBenefConstantes.CITY_TOWN_6B);
		}
		if(valido) {			
			valido = validarCadenaObligatorioLongitud(fileTransferForma.getCampo6c(), FileTransferBenefConstantes.LONGITUD_150,
				fileTransferForma, listaRegistrosErrorAux, FileTransferBenefConstantes.COUNTRY_6C);
		}
		//Si se capturo el domicilio de correspondencia, valida la longitud de los campos
		if(valido && (StringUtils.isNotBlank(fileTransferForma.getCampo7a()) && 
				StringUtils.isNotBlank(fileTransferForma.getCampo7b()) && 
				StringUtils.isNotBlank(fileTransferForma.getCampo7c()))) {
			validarLongitudDomicilioCorrespondencia(valido, fileTransferForma, listaRegistrosErrorAux);
		}
		//Valida la longitud del campo 8
		if(valido) {
			valido = validarLongitudCadena(fileTransferForma.getCampo8(), FileTransferBenefConstantes.LONGITUD_11, 
				fileTransferForma, listaRegistrosErrorAux, FileTransferBenefConstantes.US_TIN);
		}
		//Valida la longitud del campo 9

		/* Valida si campo 5 es:
		 * 	Participating FFI
		 * 	Reporting Model 1 FFI
		 * 	Reporting Model 2 FFI
		 * Entonces se debe llenar campo: GIIN (Campo9)
		 */
		if(valido && fileTransferForma.getCampo5().equals(FileTransferBenefConstantes.Participating_FF)
				|| fileTransferForma.getCampo5().equals(FileTransferBenefConstantes.Reporting_Model_1_FFI)
				|| fileTransferForma.getCampo5().equals(FileTransferBenefConstantes.Reporting_Model_2_FFI)){
			valido = validarCadenaObligatorioLongitud(fileTransferForma.getCampo9(), FileTransferBenefConstantes.LONGITUD_19, 
					fileTransferForma, listaRegistrosErrorAux, FileTransferBenefConstantes.GIIN);
		}

		if(valido) {
			valido = validarLongitudCadena(fileTransferForma.getCampo9(), FileTransferBenefConstantes.LONGITUD_19, 
				fileTransferForma, listaRegistrosErrorAux, FileTransferBenefConstantes.GIIN);
		}

		//Valida la longitud del campo 10
		if(valido) {
			valido = validarLongitudCadena(fileTransferForma.getCampo10(), FileTransferBenefConstantes.LONGITUD_33, 
				fileTransferForma, listaRegistrosErrorAux, FileTransferBenefConstantes.REFERENCE_NUMBER);
		}
		//Valida la longitud del campo 13
		if(valido) {
			valido = validarCadenaObligatorioLongitud(fileTransferForma.getCampo13(), FileTransferBenefConstantes.LONGITUD_19, 
				fileTransferForma, listaRegistrosErrorAux, FileTransferBenefConstantes.GIIN_13);
		}
		
		//Valida la parte XXIX - Certification
		if(valido) {
			valido = validarCadenaObligatorioLongitud(fileTransferForma.getCertification(), FileTransferBenefConstantes.LONGITUD_50, 
				fileTransferForma, listaRegistrosErrorAux, FileTransferBenefConstantes.PART_XXIX);
		}
		
		//Regresa el resultado de la validación
		return valido;
	}

	/*
	 * (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.validador.ValidatorBeneficiario#validaReglasNegocioBeneficiarioFiletransfer(com.indeval.portalinternacional.middleware.servicios.modelo.filetransfer.beneficiario.tipoformato.FileTransferForma, java.util.List, java.lang.String, boolean)
	 */
	@Override
	public boolean validaReglasNegocioBeneficiarioFiletransfer(FileTransferFormaW8IMY2015 fileTransferForma,
			List<FileTransferFormaW8IMY2015> listaRegistrosErrorAux, String idClaveInstitucion, boolean isIndeval) {
		valido = true;
		Long idInstitucion = null;
		// Valida que si el custodio es Deutsche Bank, el tipo de beneficiario no sea Fideicomiso Simple.
		if(FileTransferBenefConstantes.DEUTSCHE_BANK.equals(fileTransferForma.getClaveCustodio()) && 
				FileTransferBenefConstantes.FIDEICOMISO_SIMPLE.equals(fileTransferForma.getTipoBeneficiario())) {
			valido = false;
			agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, Constantes.W9_TIPO_BENEFICIARIO, Constantes.MENSAJE_CAMPO_INVALIDO);
		}
		//Si el tipo de beneficio fiscal es Fideicomiso Simple W8IMY, el custodio deberá ser DEUTSCHE BANK AG, NY
		if(FileTransferBenefConstantes.FIDEICOMISO_SIMPLE_W8.equals(fileTransferForma.getTipoBeneficiario()) &&
				!FileTransferBenefConstantes.DEUTSCHE_BANK.equals(fileTransferForma.getClaveCustodio())) {
			valido = false;
			agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, Constantes.W9_CUSTODIO, Constantes.MENSAJE_CAMPO_INVALIDO);	
		}
		// Si no es Indeval, valida que la institución con la que ingreso sea igual a la del registro
		if(!isIndeval){
			if(!fileTransferForma.getClaveInstitucion().equals(idClaveInstitucion)){
				valido = false;
				agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, Constantes.W9_ID_INSTITUCION, Constantes.MENSAJE_CAMPO_INVALIDO);				
			}
		}
		// 2. Busca la institución en el mapa. Si no la encuentra va a la base de datos y valida si existe el id-folio capturado.
		if(valido) {
			idInstitucion = mapaInstituciones.get(fileTransferForma.getClaveInstitucion());
			if(idInstitucion == null){
				Institucion institucion = getInstitucionDao().findInstitucionByClaveFolio(fileTransferForma.getClaveInstitucion());
				if(institucion == null){
					valido = false;
					agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, Constantes.W9_ID_INSTITUCION, Constantes.MENSAJE_CAMPO_INVALIDO);				
				}
				else {				
					idInstitucion=institucion.getIdInstitucion();
					mapaInstituciones.put(fileTransferForma.getClaveInstitucion(), idInstitucion);
				}
			}
		}
		// Valida la fecha de formato
		if(valido && fileTransferForma.getFechaFormato().length() > Constantes.LONGITUD_FECHA_FORMATO) {
			valido = false;
			agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, Constantes.W9_FECHA_FORMATO, Constantes.MENSAJE_LONGITUD_INVALIDO);			
		}
		else if(valido && !getPatronFechaFormato().matcher(fileTransferForma.getFechaFormato()).matches()) {
			valido = false;
			agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, Constantes.W9_FECHA_FORMATO, Constantes.MENSAJE_FORMATO_INCORRECTO);			
		}
		else {
			String fechaFormaSimple = convierteFechaArchivoFileTransfer(fileTransferForma.getFechaFormato());
			if(fechaFormaSimple == null) {
				valido = false;
				agregaMensajeError(fileTransferForma,listaRegistrosErrorAux,Constantes.W9_FECHA_FORMATO,Constantes.MENSAJE_CAMPO_INVALIDO);
			}
			else {			
				try {
					Date fecha = getSimpledateformat().parse(fechaFormaSimple);
					fileTransferForma.setDateFechaFormato(fecha);
				}
				catch(ParseException pe){
					valido = false;
					agregaMensajeError(fileTransferForma,listaRegistrosErrorAux,Constantes.W9_FECHA_FORMATO,Constantes.MENSAJE_FORMATO_INCORRECTO);
				}					
			}
		}
		// Valida el campo 4 de acuerdo al tipo de beneficio fiscal.
		validarCampo4(fileTransferForma, listaRegistrosErrorAux);
		// Si el tipo de beneficio es Otros(IMY), valida si el campo 4 y 5 llenan la parte V.
		// En caso contrario, valida las partes capturadas en cada campo
/*		if(valido && FileTransferBenefConstantes.OTROS_W8IMY.equals(fileTransferForma.getTipoBeneficiario()) &&
				OpcionesCampo4W8IMY.C.toString().equals(fileTransferForma.getCampo4()) &&
				OpcionesCampo5W8IMY.F.toString().equals(fileTransferForma.getCampo5())) {
			validarParte_V(fileTransferForma, listaRegistrosErrorAux);
		}
		else {
			// Valida las partes asociadas al campo 4
			if(valido) {
				validarPartesCampo4(fileTransferForma, listaRegistrosErrorAux);		
			}
			// Valida las partes asociadas al campo 5
			if(valido) {
				validarPartesCampo5(fileTransferForma, listaRegistrosErrorAux);			
			}
		}
*/
		// Valida que haya capturado todos los campos del domicilo de correspondencia
		if(valido) {
			if(!(StringUtils.isBlank(fileTransferForma.getCampo7a()) && 
					StringUtils.isBlank(fileTransferForma.getCampo7b()) &&
					StringUtils.isBlank(fileTransferForma.getCampo7c()))) {				
				//Valida que todos los campos hayan sido capturados
				if(!(StringUtils.isNotBlank(fileTransferForma.getCampo7a()) && 
						StringUtils.isNotBlank(fileTransferForma.getCampo7b()) &&
						StringUtils.isNotBlank(fileTransferForma.getCampo7c()))) {
					valido = false;
					agregaMensajeError(fileTransferForma, listaRegistrosErrorAux,
						FileTransferBenefConstantes.MAILING_ADDRESS + " o " +
								FileTransferBenefConstantes.CITY_TOWN_7B + " o " +
								FileTransferBenefConstantes.COUNTRY_7C + " o ", 
								Constantes.MENSAJE_CAMPO_NULO);
				}
			}
		}
		if(valido) {			
			// Valida que el campo 8 y la opcion 8a hayan sido capturadas si el tipo de beneficio es intermediario calificado c/s RPR.
			if((FileTransferBenefConstantes.INT_CALIF_CON_RPR.equals(fileTransferForma.getTipoBeneficiario()) ||
					FileTransferBenefConstantes.INT_CALIF_SIN_RPR.equals(fileTransferForma.getTipoBeneficiario()))) {
				if(StringUtils.isBlank(fileTransferForma.getCampo8()) || 
						StringUtils.isBlank(fileTransferForma.getCampo8a())) {
					valido = false;
					agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
						FileTransferBenefConstantes.US_TIN + " y/o " +
						FileTransferBenefConstantes.QI_EIN,
						Constantes.MENSAJE_CAMPO_NULO);						
				}
				else if(!(StringUtils.isBlank(fileTransferForma.getCampo8b()) &&
						StringUtils.isBlank(fileTransferForma.getCampo8c()) &&
						StringUtils.isBlank(fileTransferForma.getCampo8d()) &&
						StringUtils.isBlank(fileTransferForma.getCampo8e()))) {
					valido = false;
					agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
						FileTransferBenefConstantes.WP_EIN + " y/o " +
						FileTransferBenefConstantes.WT_EIN + " y/o " +
						FileTransferBenefConstantes.EIN + " y/o " +
						FileTransferBenefConstantes.SSN_ITIN,
						Constantes.MENSAJE_CAMPO_INVALIDO);
				}
				else if(!validaCampoBooleanoVerdadero(fileTransferForma.getCampo8a())) {
					valido = false;
					agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
						FileTransferBenefConstantes.QI_EIN,
						Constantes.MENSAJE_CAMPO_INVALIDO);
				}
			}
			//Para los fideicomisos simples, el campo 8 no debe ser capturado
			else if((FileTransferBenefConstantes.FIDEICOMISO_SIMPLE.equals(fileTransferForma.getTipoBeneficiario()) ||
					FileTransferBenefConstantes.FIDEICOMISO_SIMPLE_W8.equals(fileTransferForma.getTipoBeneficiario())) &&
					!(StringUtils.isBlank(fileTransferForma.getCampo8()) &&
							StringUtils.isBlank(fileTransferForma.getCampo8a()) &&
							StringUtils.isBlank(fileTransferForma.getCampo8b()) &&
							StringUtils.isBlank(fileTransferForma.getCampo8c()) &&
							StringUtils.isBlank(fileTransferForma.getCampo8d()) &&
							StringUtils.isBlank(fileTransferForma.getCampo8e()))){
				valido = false;
				agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
						FileTransferBenefConstantes.US_TIN + " y/o " +
						FileTransferBenefConstantes.QI_EIN + " y/o " +
						FileTransferBenefConstantes.WP_EIN + " y/o " +
						FileTransferBenefConstantes.WT_EIN + " y/o " +
						FileTransferBenefConstantes.EIN + " y/o " +
						FileTransferBenefConstantes.SSN_ITIN,
						Constantes.MENSAJE_CAMPO_INVALIDO);
			}
		}
		//Valida que si el custodio es Clearstream o Euroclear, el campo 10 Reference Number haya sido capturado
		if(valido && (FileTransferBenefConstantes.CLEARSTREAM_BANKING.equals(fileTransferForma.getClaveCustodio()) ||
				FileTransferBenefConstantes.EUROCLEAR_BANK.equals(fileTransferForma.getClaveCustodio())) &&
				StringUtils.isBlank(fileTransferForma.getCampo10())) {
			valido = false;
			agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
					FileTransferBenefConstantes.REFERENCE_NUMBER, 
					Constantes.MENSAJE_CAMPO_NULO);
		}
		if(valido) {
			//Si es Clearstream, valida que el valor del reference number sea el correcto
			if(FileTransferBenefConstantes.CLEARSTREAM_BANKING.equals(fileTransferForma.getClaveCustodio()) &&
					!BeneficiariosConstantes.REF_CLEARSTREAM.equals(fileTransferForma.getCampo10())) {
				valido = false;
				agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
					FileTransferBenefConstantes.REFERENCE_NUMBER, 
					Constantes.MENSAJE_CAMPO_INVALIDO);
			}
			//Si es Euroclear, valida que el valor del reference number sea el correcto
			else if(FileTransferBenefConstantes.EUROCLEAR_BANK.equals(fileTransferForma.getClaveCustodio()) &&
					!BeneficiariosConstantes.REF_EUROCLEAR_BANK.equals(fileTransferForma.getCampo10())) {
				valido = false;
				agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
					FileTransferBenefConstantes.REFERENCE_NUMBER, 
					Constantes.MENSAJE_CAMPO_INVALIDO);
			}
		}
		//Setea los id de institución, cuenta nombrada y tipo de beneficiario
		fileTransferForma.setIdInstitucion(idInstitucion);
		fileTransferForma.setIdCuentaNombrada(getCustodios().get(fileTransferForma.getClaveCustodio()));
		fileTransferForma.setIdTipoBeneficiario(getTiposBeneficiarios().get(fileTransferForma.getTipoBeneficiario()));
		//Regresa el resultado de la validación
		return valido;
	}

	/**
	 * Realiza la validación sobre el campo 4 del layout.
	 * @param fileTransferForma Objeto con los campos a validar.
	 * @param listaRegistrosErrorAux Lista de errores.
	 * @param valido Indica el estado de la validación.
	 */
	private void validarCampo4(FileTransferFormaW8IMY2015 fileTransferForma, List<FileTransferFormaW8IMY2015> listaRegistrosErrorAux) {
		if(valido) {
			//Valida que para los intermediarios calificados haya capturado la opción A
			if((FileTransferBenefConstantes.INT_CALIF_CON_RPR.equals(fileTransferForma.getTipoBeneficiario()) ||
					FileTransferBenefConstantes.INT_CALIF_SIN_RPR.equals(fileTransferForma.getTipoBeneficiario())) && 
					!OpcionesCampo4W8IMY.A.toString().equals(fileTransferForma.getCampo4())) {
				valido = false;
				agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
					FileTransferBenefConstantes.CHAPTER_3_STATUS, Constantes.MENSAJE_CAMPO_INVALIDO);
			}
			//Valida que para los fideicomisos simples haya capturado la opción H
			else if((FileTransferBenefConstantes.FIDEICOMISO_SIMPLE.equals(fileTransferForma.getTipoBeneficiario()) ||
					FileTransferBenefConstantes.FIDEICOMISO_SIMPLE_W8.equals(fileTransferForma.getTipoBeneficiario())) &&
					!OpcionesCampo4W8IMY.H.toString().equals(fileTransferForma.getCampo4())) {
				valido = false;
				agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
					FileTransferBenefConstantes.CHAPTER_3_STATUS, Constantes.MENSAJE_CAMPO_INVALIDO);
			}
		}
	}
	
	/**
	 * Realiza la validación de las partes asociadas al campo 4.
	 * @param fileTransferForma Objeto con los datos a validar.
	 * @param listaRegistrosErrorAux Lista de errores.
	 * @param valido Indica el estado de la validación.
	 */
	private void validarPartesCampo4(FileTransferFormaW8IMY2015 fileTransferForma, List<FileTransferFormaW8IMY2015> listaRegistrosErrorAux) {
		if(OpcionesCampo4W8IMY.A.toString().equals(fileTransferForma.getCampo4())) {
			validarParte_III(fileTransferForma, listaRegistrosErrorAux);
		}
		else if(OpcionesCampo4W8IMY.B.toString().equals(fileTransferForma.getCampo4())) {
			validarParte_IV(fileTransferForma, listaRegistrosErrorAux);
		}
		else if(OpcionesCampo4W8IMY.C.toString().equals(fileTransferForma.getCampo4()) ||
				OpcionesCampo4W8IMY.D.toString().equals(fileTransferForma.getCampo4())) {
			validarPartes_V_VI(fileTransferForma, listaRegistrosErrorAux);
		}
	}

	/**
	 * Realiza la validación de las partes asociadas al campo 5.
	 * @param fileTransferForma Objeto con los datos a validar.
	 * @param listaRegistrosErrorAux Lista de errores.
	 * @param valido Indica el estado de la validación.
	 */
	private void validarPartesCampo5(FileTransferFormaW8IMY2015 fileTransferForma, List<FileTransferFormaW8IMY2015> listaRegistrosErrorAux) {
		
		if(OpcionesCampo5W8IMY.B.toString().equals(fileTransferForma.getCampo5())
				|| OpcionesCampo5W8IMY.C.toString().equals(fileTransferForma.getCampo5())
				|| OpcionesCampo5W8IMY.D.toString().equals(fileTransferForma.getCampo5())){
			validarParte_9(fileTransferForma, listaRegistrosErrorAux);
		}
		else if(OpcionesCampo5W8IMY.F.toString().equals(fileTransferForma.getCampo5())) {
			validarParte_V(fileTransferForma, listaRegistrosErrorAux);
		}
		else if(OpcionesCampo5W8IMY.G.toString().equals(fileTransferForma.getCampo5())) {
			validarParte_X(fileTransferForma, listaRegistrosErrorAux);
		}
		else if(OpcionesCampo5W8IMY.J.toString().equals(fileTransferForma.getCampo5()) ||
				OpcionesCampo5W8IMY.Y.toString().equals(fileTransferForma.getCampo5())) {
			validarPartes_XIV_XXVII(fileTransferForma, listaRegistrosErrorAux);
		}
		else if(OpcionesCampo5W8IMY.L.toString().equals(fileTransferForma.getCampo5()) ||
				OpcionesCampo5W8IMY.M.toString().equals(fileTransferForma.getCampo5())) {
			validarPartes_XI_XVI(fileTransferForma, listaRegistrosErrorAux);
		}
		else if(OpcionesCampo5W8IMY.O.toString().equals(fileTransferForma.getCampo5())) {
			validarParte_XVIII(fileTransferForma, listaRegistrosErrorAux);
		}
		else if(OpcionesCampo5W8IMY.P.toString().equals(fileTransferForma.getCampo5())) {
			validarParte_XIX(fileTransferForma, listaRegistrosErrorAux);
		}
		else if(OpcionesCampo5W8IMY.R.toString().equals(fileTransferForma.getCampo5()) ||
				OpcionesCampo5W8IMY.S.toString().equals(fileTransferForma.getCampo5())) {
			validarPartes_XXI_XXII(fileTransferForma, listaRegistrosErrorAux);
		}
		else if(OpcionesCampo5W8IMY.T.toString().equals(fileTransferForma.getCampo5())) {
			validarParte_XXIII(fileTransferForma, listaRegistrosErrorAux);
		}
	}
	
	/**
	 * Realiza la validación de la parte III.
	 * @param fileTransferForma Objeto con los datos a validar.
	 * @param listaRegistrosErrorAux Lista de errores.
	 */
	private void validarParte_III(FileTransferFormaW8IMY2015 fileTransferForma, List<FileTransferFormaW8IMY2015> listaRegistrosErrorAux) {
		//Para los intermediarios calificados, el campo4Anexo1 debe ser YES
		if((FileTransferBenefConstantes.INT_CALIF_CON_RPR.equals(fileTransferForma.getTipoBeneficiario()) ||
				FileTransferBenefConstantes.INT_CALIF_SIN_RPR.equals(fileTransferForma.getTipoBeneficiario()))) {
			if(StringUtils.isBlank(fileTransferForma.getCampo4Anexo1())) {
				valido = false;
				agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
					FileTransferBenefConstantes.CHAPTER_3_STATUS_CAMPO1, Constantes.MENSAJE_CAMPO_NULO);
			}
			else if(!validaCampoBooleanoVerdadero(fileTransferForma.getCampo4Anexo1())) {
				valido = false;
				agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
					FileTransferBenefConstantes.CHAPTER_3_STATUS_CAMPO1, Constantes.MENSAJE_CAMPO_INVALIDO);
			}
		}
		if(valido) {
			//Valida que se haya capturado al menos uno de los campos 14a, 14b, 14c, 14d, 14e o 14f
			if(StringUtils.isBlank(fileTransferForma.getCampo4Anexo1()) &&
					StringUtils.isBlank(fileTransferForma.getCampo4Anexo2()) &&
					StringUtils.isBlank(fileTransferForma.getCampo4Anexo3()) &&
					StringUtils.isBlank(fileTransferForma.getCampo4Anexo5()) &&
					StringUtils.isBlank(fileTransferForma.getCampo4Anexo7()) &&
					StringUtils.isBlank(fileTransferForma.getCampo4Anexo11())) {
				valido = false;
				agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
					FileTransferBenefConstantes.CHAPTER_3_STATUS_CAMPO1  + "o" +
					FileTransferBenefConstantes.CHAPTER_3_STATUS_CAMPO2  + "o" +
					FileTransferBenefConstantes.CHAPTER_3_STATUS_CAMPO3  + "o" +
					FileTransferBenefConstantes.CHAPTER_3_STATUS_CAMPO5  + "o" +
					FileTransferBenefConstantes.CHAPTER_3_STATUS_CAMPO7  + "o" +
					FileTransferBenefConstantes.CHAPTER_3_STATUS_CAMPO11 + "o",
					Constantes.MENSAJE_CAMPO_NULO);
			}
			else if(!validaCampoBooleanoVerdadero(fileTransferForma.getCampo4Anexo1()) &&
					!validaCampoBooleanoVerdadero(fileTransferForma.getCampo4Anexo2()) &&
					!validaCampoBooleanoVerdadero(fileTransferForma.getCampo4Anexo3()) &&
					!validaCampoBooleanoVerdadero(fileTransferForma.getCampo4Anexo5()) &&
					!validaCampoBooleanoVerdadero(fileTransferForma.getCampo4Anexo7()) &&
					!validaCampoBooleanoVerdadero(fileTransferForma.getCampo4Anexo11())) {
				valido = false;
				agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
					FileTransferBenefConstantes.CHAPTER_3_STATUS_CAMPO1  + "o" +
					FileTransferBenefConstantes.CHAPTER_3_STATUS_CAMPO2  + "o" +
					FileTransferBenefConstantes.CHAPTER_3_STATUS_CAMPO3  + "o" +
					FileTransferBenefConstantes.CHAPTER_3_STATUS_CAMPO5  + "o" +
					FileTransferBenefConstantes.CHAPTER_3_STATUS_CAMPO7  + "o" +
					FileTransferBenefConstantes.CHAPTER_3_STATUS_CAMPO11 + "o",
					Constantes.MENSAJE_CAMPO_INVALIDO);
			}
			//Si capturo el campo 14c1, valida que el campo 14c haya sido capturado.
			else if(StringUtils.isNotBlank(fileTransferForma.getCampo4Anexo4()) &&
				StringUtils.isBlank(fileTransferForma.getCampo4Anexo3())) {
				valido = false;
				agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
					FileTransferBenefConstantes.CHAPTER_3_STATUS_CAMPO3,
					Constantes.MENSAJE_CAMPO_NULO);
			}
			//Si capturo el campo 14c1, valida que el campo 14c sea valido.
			else if(StringUtils.isNotBlank(fileTransferForma.getCampo4Anexo4()) && 
					!validaCampoBooleanoVerdadero(fileTransferForma.getCampo4Anexo3())) {
				valido = false;
				agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
					FileTransferBenefConstantes.CHAPTER_3_STATUS_CAMPO3,
					Constantes.MENSAJE_CAMPO_INVALIDO);
			}
			//Si capturo el campo 14d1, valida que el campo 14d haya sido capturado.
			else if(StringUtils.isNotBlank(fileTransferForma.getCampo4Anexo6()) && 
					StringUtils.isBlank(fileTransferForma.getCampo4Anexo5())) {
				valido = false;
				agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
					FileTransferBenefConstantes.CHAPTER_3_STATUS_CAMPO5,
					Constantes.MENSAJE_CAMPO_NULO);
			}
			//Si capturo el campo 14d1, valida que el campo 14d sea valido.
			else if(StringUtils.isNotBlank(fileTransferForma.getCampo4Anexo6()) && 
					!validaCampoBooleanoVerdadero(fileTransferForma.getCampo4Anexo5())) {
				valido = false;
				agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
					FileTransferBenefConstantes.CHAPTER_3_STATUS_CAMPO5,
					Constantes.MENSAJE_CAMPO_INVALIDO);
			}
			//Si capturo el campo 14e1, valida que el campo 14e haya sido capturado.
			else if(StringUtils.isNotBlank(fileTransferForma.getCampo4Anexo8()) && 
					StringUtils.isBlank(fileTransferForma.getCampo4Anexo7())) {
				valido = false;
				agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
					FileTransferBenefConstantes.CHAPTER_3_STATUS_CAMPO7,
					Constantes.MENSAJE_CAMPO_NULO);
			}
			//Si capturo el campo 14e1, valida que el campo 14e haya sido capturado.
			else if(StringUtils.isNotBlank(fileTransferForma.getCampo4Anexo8()) && 
					!validaCampoBooleanoVerdadero(fileTransferForma.getCampo4Anexo7())) {
				valido = false;
				agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
					FileTransferBenefConstantes.CHAPTER_3_STATUS_CAMPO7,
					Constantes.MENSAJE_CAMPO_INVALIDO);
			}
			else {			
				//Si capturo los campos 14e2 y/o 14e3, debe validar que el campo 14e haya sido capturado
				boolean e2 = StringUtils.isNotBlank(fileTransferForma.getCampo4Anexo9()) && 
					validaCampoBooleano(fileTransferForma.getCampo4Anexo9());
				boolean e3 = StringUtils.isNotBlank(fileTransferForma.getCampo4Anexo10()) && 
					validaCampoBooleano(fileTransferForma.getCampo4Anexo10());
				if((e2 || e3) && !validaCampoBooleanoVerdadero(fileTransferForma.getCampo4Anexo7())) {
					valido = false;
					agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
						FileTransferBenefConstantes.CHAPTER_3_STATUS_CAMPO7,
						Constantes.MENSAJE_CAMPO_NULO);
				}
			}	
		}
	}

	/**
	 * Realiza la validación de la parte IV.
	 * @param fileTransferForma Objeto con los datos a validar.
	 * @param listaRegistrosErrorAux Lista de errores.
	 */
	private void validarParte_IV(FileTransferFormaW8IMY2015 fileTransferForma, List<FileTransferFormaW8IMY2015> listaRegistrosErrorAux) {
		if(StringUtils.isEmpty(fileTransferForma.getCampo4Anexo1()) &&
				StringUtils.isEmpty(fileTransferForma.getCampo4Anexo2()) &&
				StringUtils.isEmpty(fileTransferForma.getCampo4Anexo3()) &&
				StringUtils.isEmpty(fileTransferForma.getCampo4Anexo4())) {
			valido = false;
			agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
				FileTransferBenefConstantes.CHAPTER_3_STATUS_CAMPO1  + "o" +
				FileTransferBenefConstantes.CHAPTER_3_STATUS_CAMPO2  + "o" +
				FileTransferBenefConstantes.CHAPTER_3_STATUS_CAMPO3  + "o" +
				FileTransferBenefConstantes.CHAPTER_3_STATUS_CAMPO4,
				Constantes.MENSAJE_CAMPO_NULO);
		}
		else if(!validaCampoBooleanoVerdadero(fileTransferForma.getCampo4Anexo1()) &&
				!validaCampoBooleanoVerdadero(fileTransferForma.getCampo4Anexo2()) &&
				!validaCampoBooleanoVerdadero(fileTransferForma.getCampo4Anexo3()) &&
				!validaCampoBooleanoVerdadero(fileTransferForma.getCampo4Anexo4())) {
			valido = false;
			agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
				FileTransferBenefConstantes.CHAPTER_3_STATUS_CAMPO1  + "o" +
				FileTransferBenefConstantes.CHAPTER_3_STATUS_CAMPO2  + "o" +
				FileTransferBenefConstantes.CHAPTER_3_STATUS_CAMPO3  + "o" +
				FileTransferBenefConstantes.CHAPTER_3_STATUS_CAMPO4,
				Constantes.MENSAJE_CAMPO_INVALIDO);
		}
	}
	
	/**
	 * Realiza la validación de las partes V y VI si fueron llenados en el campo 4.
	 * @param fileTransferForma Objeto con los datos a validar.
	 * @param listaRegistrosErrorAux Lista de errores.
	 */
	private void validarPartes_V_VI(FileTransferFormaW8IMY2015 fileTransferForma, List<FileTransferFormaW8IMY2015> listaRegistrosErrorAux) {
		if(StringUtils.isBlank(fileTransferForma.getCampo4Anexo1()) &&
				StringUtils.isBlank(fileTransferForma.getCampo4Anexo2()) &&
				StringUtils.isBlank(fileTransferForma.getCampo4Anexo3())) {
			valido = false;
			agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
				FileTransferBenefConstantes.CHAPTER_3_STATUS_CAMPO1 + " o " + 
				FileTransferBenefConstantes.CHAPTER_3_STATUS_CAMPO2 + " o " +
				FileTransferBenefConstantes.CHAPTER_3_STATUS_CAMPO3 + " o ", 
				Constantes.MENSAJE_CAMPO_NULO);
		}
		else if(!validaCampoBooleanoVerdadero(fileTransferForma.getCampo4Anexo1()) &&
				!validaCampoBooleanoVerdadero(fileTransferForma.getCampo4Anexo2()) &&
				!validaCampoBooleanoVerdadero(fileTransferForma.getCampo4Anexo3())) {
			valido = false;
			agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
				FileTransferBenefConstantes.CHAPTER_3_STATUS_CAMPO1 + " o " + 
				FileTransferBenefConstantes.CHAPTER_3_STATUS_CAMPO2 + " o " +
				FileTransferBenefConstantes.CHAPTER_3_STATUS_CAMPO3 + " o ", 
				Constantes.MENSAJE_CAMPO_INVALIDO);
		}
	}
	
	/** Valida si campo 5 es:
	 * 	Participating FFI
	 * 	Reporting Model 1 FFI
	 * 	Reporting Model 2 FFI
	 * Entonces se debe llenar campo: GIIN (Campo9)
	 * @param fileTransferForma Objeto con los datos a validar.
	 * @param listaRegistrosErrorAux Lista de errores.
	 */
	private void validarParte_9(FileTransferFormaW8IMY2015 fileTransferForma, List<FileTransferFormaW8IMY2015> listaRegistrosErrorAux) {
		if(StringUtils.isBlank(fileTransferForma.getCampo9())) {
			valido = false;
			agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
				FileTransferBenefConstantes.GIIN, 
				Constantes.MENSAJE_CAMPO_NULO);
		}
	}
	
	/**
	 * Realiza la validación de la parte V
	 * @param fileTransferForma Objeto con los datos a validar.
	 * @param listaRegistrosErrorAux Lista de errores.
	 */
	private void validarParte_V(FileTransferFormaW8IMY2015 fileTransferForma, List<FileTransferFormaW8IMY2015> listaRegistrosErrorAux) {
		if(StringUtils.isBlank(fileTransferForma.getCampo5Anexo1()) &&
				StringUtils.isBlank(fileTransferForma.getCampo5Anexo2()) &&
				StringUtils.isBlank(fileTransferForma.getCampo5Anexo3())) {
			valido = false;
			agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
				FileTransferBenefConstantes.CHAPTER_4_STATUS_CAMPO1 + " o " + 
				FileTransferBenefConstantes.CHAPTER_4_STATUS_CAMPO2 + " o " +
				FileTransferBenefConstantes.CHAPTER_4_STATUS_CAMPO3 + " o ", 
				Constantes.MENSAJE_CAMPO_NULO);
		}
		else if(!validaCampoBooleanoVerdadero(fileTransferForma.getCampo5Anexo1()) &&
				!validaCampoBooleanoVerdadero(fileTransferForma.getCampo5Anexo2()) &&
				!validaCampoBooleanoVerdadero(fileTransferForma.getCampo5Anexo3())) {
			valido = false;
			agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
				FileTransferBenefConstantes.CHAPTER_4_STATUS_CAMPO1 + " o " + 
				FileTransferBenefConstantes.CHAPTER_4_STATUS_CAMPO2 + " o " +
				FileTransferBenefConstantes.CHAPTER_4_STATUS_CAMPO3 + " o ", 
				Constantes.MENSAJE_CAMPO_INVALIDO);
		}
	}
	
	/**
	 * Realiza la validación de la parte X
	 * @param fileTransferForma Objeto con los datos a validar.
	 * @param listaRegistrosErrorAux Lista de errores.
	 */
	private void validarParte_X(FileTransferFormaW8IMY2015 fileTransferForma, List<FileTransferFormaW8IMY2015> listaRegistrosErrorAux) {
		//Valida que el campo5-anexo1 sea capturado
		if(StringUtils.isBlank(fileTransferForma.getCampo5Anexo1())) {
			valido = false;
			agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
				FileTransferBenefConstantes.CHAPTER_4_STATUS_CAMPO1, 
				Constantes.MENSAJE_CAMPO_NULO);
		}
		//Valida que capture el campo5-anexo2 o campo5-anexo3
		else if(StringUtils.isBlank(fileTransferForma.getCampo5Anexo2()) &&
				StringUtils.isBlank(fileTransferForma.getCampo5Anexo3())) {
			valido = false;
			agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
				FileTransferBenefConstantes.CHAPTER_4_STATUS_CAMPO2 + " o " +
				FileTransferBenefConstantes.CHAPTER_4_STATUS_CAMPO3, 
				Constantes.MENSAJE_CAMPO_NULO);
		}
		//Valida el campo5-anexo2 o campo5-anexo3
		else if(!validaCampoBooleanoVerdadero(fileTransferForma.getCampo5Anexo2()) &&
				!validaCampoBooleanoVerdadero(fileTransferForma.getCampo5Anexo3())) {
			valido = false;
			agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
				FileTransferBenefConstantes.CHAPTER_4_STATUS_CAMPO2 + " o " +
				FileTransferBenefConstantes.CHAPTER_4_STATUS_CAMPO3, 
				Constantes.MENSAJE_CAMPO_INVALIDO);
		}
	}
	
	/**
	 * Realiza la validación de las partes XI y XVI
	 * @param fileTransferForma Objeto con los datos a validar.
	 * @param listaRegistrosErrorAux Lista de errores.
	 */
	private void validarPartes_XI_XVI(FileTransferFormaW8IMY2015 fileTransferForma, List<FileTransferFormaW8IMY2015> listaRegistrosErrorAux) {
		if(StringUtils.isBlank(fileTransferForma.getCampo5Anexo1()) &&
				StringUtils.isBlank(fileTransferForma.getCampo5Anexo2()) &&
				StringUtils.isBlank(fileTransferForma.getCampo5Anexo3())) {
			valido = false;
			agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
				FileTransferBenefConstantes.CHAPTER_4_STATUS_CAMPO1 + " y/o " + 
				FileTransferBenefConstantes.CHAPTER_4_STATUS_CAMPO2 + " y/o " +
				FileTransferBenefConstantes.CHAPTER_4_STATUS_CAMPO3 + " y/o ", 
				Constantes.MENSAJE_CAMPO_NULO);
		}
		else if(!validaCampoBooleanoVerdadero(fileTransferForma.getCampo5Anexo1()) &&
				!validaCampoBooleanoVerdadero(fileTransferForma.getCampo5Anexo2()) &&
				!validaCampoBooleanoVerdadero(fileTransferForma.getCampo5Anexo3())) {
			valido = false;
			agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
				FileTransferBenefConstantes.CHAPTER_4_STATUS_CAMPO1 + " y/o " + 
				FileTransferBenefConstantes.CHAPTER_4_STATUS_CAMPO2 + " y/o " +
				FileTransferBenefConstantes.CHAPTER_4_STATUS_CAMPO3 + " y/o ", 
				Constantes.MENSAJE_CAMPO_INVALIDO);
		}
	}	
	
	/**
	 * Realiza la validación de las partes XIV y XXVII.
	 * @param fileTransferForma Objeto con los datos a validar.
	 * @param listaRegistrosErrorAux Lista de errores.
	 */
	private void validarPartes_XIV_XXVII(FileTransferFormaW8IMY2015 fileTransferForma, List<FileTransferFormaW8IMY2015> listaRegistrosErrorAux) {
		//Valida que el campo5-anexo1 y el campo5-anexo2 hayan sido capturados
		if(StringUtils.isBlank(fileTransferForma.getCampo5Anexo1()) || 
				StringUtils.isBlank(fileTransferForma.getCampo5Anexo2())) {
			valido = false;
			agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
				FileTransferBenefConstantes.CHAPTER_4_STATUS_CAMPO1 + " y/o " + 
				FileTransferBenefConstantes.CHAPTER_4_STATUS_CAMPO2, 
				Constantes.MENSAJE_CAMPO_NULO);
		}
		//Valida el campo5-anexo2
		else if(!validaCampoBooleanoVerdadero(fileTransferForma.getCampo5Anexo2())) {
			valido = false;
			agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
				FileTransferBenefConstantes.CHAPTER_4_STATUS_CAMPO2, 
				Constantes.MENSAJE_CAMPO_INVALIDO);
		}
	}
	
	/**
	 * Realiza la validación de la parte XVIII
	 * @param fileTransferForma Objeto con los datos a validar.
	 * @param listaRegistrosErrorAux Lista de errores.
	 */
	private void validarParte_XVIII(FileTransferFormaW8IMY2015 fileTransferForma, List<FileTransferFormaW8IMY2015> listaRegistrosErrorAux) {
		//Valida que el campo5-anexo1 haya sido capturado
		if(StringUtils.isBlank(fileTransferForma.getCampo5Anexo1())) {
			valido = false;
			agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
				FileTransferBenefConstantes.CHAPTER_4_STATUS_CAMPO1, Constantes.MENSAJE_CAMPO_NULO);
		}
		//Valida el campo5-anexo1
		else if(!validaCampoBooleanoVerdadero(fileTransferForma.getCampo5Anexo1())) {
			valido = false;
			agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
				FileTransferBenefConstantes.CHAPTER_4_STATUS_CAMPO1, Constantes.MENSAJE_CAMPO_INVALIDO);
		}
		//Valida que haya capturado el campo5-anexo 2, 3 o 4
		else if(valido && StringUtils.isBlank(fileTransferForma.getCampo5Anexo2()) && 
				StringUtils.isBlank(fileTransferForma.getCampo5Anexo3()) && 
				StringUtils.isBlank(fileTransferForma.getCampo5Anexo4())) {
			valido = false;
			agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
				FileTransferBenefConstantes.CHAPTER_4_STATUS_CAMPO2 + " o " + 
				FileTransferBenefConstantes.CHAPTER_4_STATUS_CAMPO3 + " o " +
				FileTransferBenefConstantes.CHAPTER_4_STATUS_CAMPO4 + " o ", 
				Constantes.MENSAJE_CAMPO_NULO);
		}
	}
	
	/**
	 * Realiza la validación de la parte XIX
	 * @param fileTransferForma Objeto con los datos a validar.
	 * @param listaRegistrosErrorAux Lista de errores.
	 */
	private void validarParte_XIX(FileTransferFormaW8IMY2015 fileTransferForma, List<FileTransferFormaW8IMY2015> listaRegistrosErrorAux) {
		//Valida que haya capturado el campo5-anexo 1, 2, 3, 4, 5 o 6
		if(StringUtils.isBlank(fileTransferForma.getCampo5Anexo1()) && 
				StringUtils.isBlank(fileTransferForma.getCampo5Anexo2()) &&
				StringUtils.isBlank(fileTransferForma.getCampo5Anexo3()) &&
				StringUtils.isBlank(fileTransferForma.getCampo5Anexo4()) &&
				StringUtils.isBlank(fileTransferForma.getCampo5Anexo5()) &&
				StringUtils.isBlank(fileTransferForma.getCampo5Anexo6())) {
			valido = false;
			agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
				FileTransferBenefConstantes.CHAPTER_4_STATUS_CAMPO1 + " o " + 
				FileTransferBenefConstantes.CHAPTER_4_STATUS_CAMPO2 + " o " +
				FileTransferBenefConstantes.CHAPTER_4_STATUS_CAMPO3 + " o " +
				FileTransferBenefConstantes.CHAPTER_4_STATUS_CAMPO4 + " o " + 
				FileTransferBenefConstantes.CHAPTER_4_STATUS_CAMPO5 + " o " +
				FileTransferBenefConstantes.CHAPTER_4_STATUS_CAMPO6 + " o ", 
				Constantes.MENSAJE_CAMPO_NULO);
		}
		//Valida los campos capturados
		else if(valido && !validaCampoBooleanoVerdadero(fileTransferForma.getCampo5Anexo1()) && 
				!validaCampoBooleanoVerdadero(fileTransferForma.getCampo5Anexo2()) &&
				!validaCampoBooleanoVerdadero(fileTransferForma.getCampo5Anexo3()) &&
				!validaCampoBooleanoVerdadero(fileTransferForma.getCampo5Anexo4()) &&
				!validaCampoBooleanoVerdadero(fileTransferForma.getCampo5Anexo5()) &&
				!validaCampoBooleanoVerdadero(fileTransferForma.getCampo5Anexo6())) {
			valido = false;
			agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
				FileTransferBenefConstantes.CHAPTER_4_STATUS_CAMPO1 + " o " + 
				FileTransferBenefConstantes.CHAPTER_4_STATUS_CAMPO2 + " o " +
				FileTransferBenefConstantes.CHAPTER_4_STATUS_CAMPO3 + " o " +
				FileTransferBenefConstantes.CHAPTER_4_STATUS_CAMPO4 + " o " + 
				FileTransferBenefConstantes.CHAPTER_4_STATUS_CAMPO5 + " o " +
				FileTransferBenefConstantes.CHAPTER_4_STATUS_CAMPO6 + " o ", 
				Constantes.MENSAJE_CAMPO_INVALIDO);
		}
	}
	
	/**
	 * Realiza la validación de las partes XXI y XXII
	 * @param fileTransferForma Objeto con los datos a validar.
	 * @param listaRegistrosErrorAux Lista de errores.
	 */
	private void validarPartes_XXI_XXII(FileTransferFormaW8IMY2015 fileTransferForma, List<FileTransferFormaW8IMY2015> listaRegistrosErrorAux) {
		//Valida que el campo5-anexo1 y el campo5-anexo2 hayan sido capturados
		if(StringUtils.isBlank(fileTransferForma.getCampo5Anexo1()) ||
				StringUtils.isBlank(fileTransferForma.getCampo5Anexo2())) {
			valido = false;
			agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
				FileTransferBenefConstantes.CHAPTER_4_STATUS_CAMPO1 + " o " + 
				FileTransferBenefConstantes.CHAPTER_4_STATUS_CAMPO2, 
				Constantes.MENSAJE_CAMPO_NULO);
		}
		//Valida el campo5-anexo1
		else if(!validaCampoBooleanoVerdadero(fileTransferForma.getCampo5Anexo1())) {
			valido = false;
			agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
				FileTransferBenefConstantes.CHAPTER_4_STATUS_CAMPO1, 
				Constantes.MENSAJE_CAMPO_INVALIDO);
		}
	}
	
	/**
	 * Realiza la validación de la parte XXIII
	 * @param fileTransferForma Objeto con los datos a validar.
	 * @param listaRegistrosErrorAux Lista de errores.
	 */
	private void validarParte_XXIII(FileTransferFormaW8IMY2015 fileTransferForma, List<FileTransferFormaW8IMY2015> listaRegistrosErrorAux) {
		if(StringUtils.isBlank(fileTransferForma.getCampo5Anexo1()) && 
				StringUtils.isBlank(fileTransferForma.getCampo5Anexo3())) {
			valido = false;
			agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
				FileTransferBenefConstantes.CHAPTER_4_STATUS_CAMPO1 + " y/o " + 
				FileTransferBenefConstantes.CHAPTER_4_STATUS_CAMPO3, 
				Constantes.MENSAJE_CAMPO_NULO);			
		}
		else if(!validaCampoBooleanoVerdadero(fileTransferForma.getCampo5Anexo1()) && 
				!validaCampoBooleanoVerdadero(fileTransferForma.getCampo5Anexo3())) {
			valido = false;
			agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
				FileTransferBenefConstantes.CHAPTER_4_STATUS_CAMPO1 + " y/o " + 
				FileTransferBenefConstantes.CHAPTER_4_STATUS_CAMPO3, 
				Constantes.MENSAJE_CAMPO_INVALIDO);			
		}
		else if(validaCampoBooleanoVerdadero(fileTransferForma.getCampo5Anexo1()) && 
				StringUtils.isBlank(fileTransferForma.getCampo5Anexo2())) {
			valido = false;
			agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
				FileTransferBenefConstantes.CHAPTER_4_STATUS_CAMPO2, 
				Constantes.MENSAJE_CAMPO_NULO);
		}
		else if(StringUtils.isNotBlank(fileTransferForma.getCampo5Anexo2()) &&
				!validaCampoBooleanoVerdadero(fileTransferForma.getCampo5Anexo1())) {
			valido = false;
			agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
				FileTransferBenefConstantes.CHAPTER_4_STATUS_CAMPO1, 
				Constantes.MENSAJE_CAMPO_INVALIDO);
		}
		else if(validaCampoBooleanoVerdadero(fileTransferForma.getCampo5Anexo3()) && 
				StringUtils.isBlank(fileTransferForma.getCampo5Anexo4()) &&
				StringUtils.isBlank(fileTransferForma.getCampo5Anexo5())) {
			valido = false;
			agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
				FileTransferBenefConstantes.CHAPTER_4_STATUS_CAMPO4 + " y/o " + 
				FileTransferBenefConstantes.CHAPTER_4_STATUS_CAMPO5, 
				Constantes.MENSAJE_CAMPO_NULO);
		}
		else if((StringUtils.isNotBlank(fileTransferForma.getCampo5Anexo4()) ||
				StringUtils.isNotBlank(fileTransferForma.getCampo5Anexo5())) &&
				!validaCampoBooleanoVerdadero(fileTransferForma.getCampo5Anexo3())) {
			valido = false;
			agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
				FileTransferBenefConstantes.CHAPTER_4_STATUS_CAMPO3, 
				Constantes.MENSAJE_CAMPO_INVALIDO);
		}
	}
	
	/**
	 * Valida las longitudes de los campos de domicilio de correspondencia.
	 * @param valido Indica si es valido el campo.
	 * @param fileTransferForma Objeto con los campos a validar.
	 * @param listaRegistrosErrorAux Lista de errores.
	 */
	private void validarLongitudDomicilioCorrespondencia(boolean valido, FileTransferFormaW8IMY2015 fileTransferForma, 
			List<FileTransferFormaW8IMY2015> listaRegistrosErrorAux) {
		valido = validarLongitudCadena(fileTransferForma.getCampo7a(), FileTransferBenefConstantes.LONGITUD_150,
			fileTransferForma, listaRegistrosErrorAux, FileTransferBenefConstantes.MAILING_ADDRESS);
		valido = valido && validarLongitudCadena(fileTransferForma.getCampo7b(), FileTransferBenefConstantes.LONGITUD_150,
			fileTransferForma, listaRegistrosErrorAux, FileTransferBenefConstantes.CITY_TOWN_7B);
		valido = valido && validarLongitudCadena(fileTransferForma.getCampo7c(), FileTransferBenefConstantes.LONGITUD_150,
			fileTransferForma, listaRegistrosErrorAux, FileTransferBenefConstantes.COUNTRY_7C);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.validador.ValidatorBeneficiario#init()
	 */
	@Override
	public void init() {
System.out.println("## ValidatorFormatW8IMY2015ServiceImpl :: init");
		//Inicializa el mapa de instituciones
		if(mapaInstituciones == null) {
			mapaInstituciones = new HashMap<String,Long>();
		}
		super.init();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.validador.ValidatorBeneficiario#release()
	 */
	@Override
	public void release() {
		//Libera el mapa de instituciones
		if(mapaInstituciones != null){
			mapaInstituciones.clear();
			mapaInstituciones = null;
		}
		super.release();
	}
}