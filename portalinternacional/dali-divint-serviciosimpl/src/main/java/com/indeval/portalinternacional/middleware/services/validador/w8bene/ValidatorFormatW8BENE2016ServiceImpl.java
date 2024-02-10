/**
 * Bursatec - Portal Internacional
 * Copyrigth (c) 2015 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.services.validador.w8bene;

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
import com.indeval.portalinternacional.middleware.servicios.constantes.OpcionesCampo4BENE;
import com.indeval.portalinternacional.middleware.servicios.constantes.OpcionesCampo5BENE;
import com.indeval.portalinternacional.middleware.servicios.modelo.filetransfer.beneficiario.tipoformato.FileTransferFormaW8BENE;

/**
 * Clase que implementa las validaciones utilizadas en el proceso de file transfer para el formato
 * W8BEN-E.
 *
 * @author Pablo Balderas
 */
public class ValidatorFormatW8BENE2016ServiceImpl extends ValidatorFormatW8BENE2016 implements Constantes {

	/** Resultado de la validación */
	boolean valido;
	
	/** Mapa donde se almacenan temporalmente las instituciones capturadas en el file transfer y reducir los accesos a la BD */
	private Map<String,Long> mapaInstituciones = null;

	/*
	 * (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.validador.ValidatorBeneficiario#esRegistroFileTransferValido(com.indeval.portalinternacional.middleware.servicios.modelo.filetransfer.beneficiario.tipoformato.FileTransferForma, java.util.List)
	 */
	@Override
	public boolean esRegistroFileTransferValido(FileTransferFormaW8BENE fileTransferForma, List<FileTransferFormaW8BENE> listaRegistrosErrorAux) {
		valido = true;
		//Valida los campos comunes
		valido = validarCamposComunes(fileTransferForma, BeneficiariosConstantes.ID_FORMATO_W8_BEN_E, listaRegistrosErrorAux);
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
		//Valida el campo 5
		if(valido && StringUtils.isBlank(fileTransferForma.getCampo5())) {
			valido = false;
			agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
				FileTransferBenefConstantes.CHAPTER_4_STATUS, Constantes.MENSAJE_CAMPO_NULO);
		}
		/** Agrego validaciones para opciones: 5 Chapter 4 Status **/
/*		if(valido && !validarChapter4Status(fileTransferForma)) {
			valido = false;
			agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
				FileTransferBenefConstantes.CHAPTER_4_STATUS_ANEXOS, Constantes.MENSAJE_CAMPO_INVALIDO);
		}
*/		
		if(valido && !OpcionesCampo5BENE.isInEnum(fileTransferForma.getCampo5(), OpcionesCampo5BENE.class)) {
			valido = false;
			agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
				FileTransferBenefConstantes.CHAPTER_4_STATUS, Constantes.MENSAJE_CAMPO_INVALIDO);
		}
		//Valida los campos del domicilio
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
			validarLongitudDomicilioCorrespondencia(fileTransferForma, listaRegistrosErrorAux);
		}
		//Valida la longitud del campo 8
		if(valido) {
			valido = validarLongitudCadena(fileTransferForma.getCampo8(), FileTransferBenefConstantes.LONGITUD_11,
				fileTransferForma, listaRegistrosErrorAux, FileTransferBenefConstantes.US_TIN);
		}
		//Valida la longitud del campo 9
		if(valido) {
			valido = validarLongitudCadena(fileTransferForma.getCampo9a(), FileTransferBenefConstantes.LONGITUD_19,
				fileTransferForma, listaRegistrosErrorAux, FileTransferBenefConstantes.GIIN_9a);
		}
		if(valido) {
			valido = validarLongitudCadena(fileTransferForma.getCampo9b(), FileTransferBenefConstantes.LONGITUD_22,
				fileTransferForma, listaRegistrosErrorAux, FileTransferBenefConstantes.FOREING_TIN);
		}
		//Valida la longitud del campo 10
		if(valido) {
			valido = validarLongitudCadena(fileTransferForma.getCampo10(), FileTransferBenefConstantes.LONGITUD_33,
				fileTransferForma, listaRegistrosErrorAux, FileTransferBenefConstantes.REFERENCE_NUMBER);
		}
		//Valida la longitud del campo 13
		if(valido) {
			valido = validarLongitudCadena(fileTransferForma.getCampo13(), FileTransferBenefConstantes.LONGITUD_19,
				fileTransferForma, listaRegistrosErrorAux, FileTransferBenefConstantes.GIIN_13);
		}
		//Valida la longitud del campo 15d
		if(valido) {
			valido = validarLongitudCadena(fileTransferForma.getCampo15d(), FileTransferBenefConstantes.LONGITUD_40,
				fileTransferForma, listaRegistrosErrorAux, FileTransferBenefConstantes.SPECIAL_RATES_CONDITIONS_REASON_1);
		}
		//Valida el print name
		if(valido) {
			valido = validarCadenaObligatorioLongitud(fileTransferForma.getPrintName(), FileTransferBenefConstantes.LONGITUD_100,
				fileTransferForma, listaRegistrosErrorAux, FileTransferBenefConstantes.PRINT_NAME);
		}
		return valido;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.validador.ValidatorBeneficiario#validaReglasNegocioBeneficiarioFiletransfer(com.indeval.portalinternacional.middleware.servicios.modelo.filetransfer.beneficiario.tipoformato.FileTransferForma, java.util.List, java.lang.String, boolean)
	 */
	@Override
	public boolean validaReglasNegocioBeneficiarioFiletransfer(FileTransferFormaW8BENE fileTransferForma,
			List<FileTransferFormaW8BENE> listaRegistrosErrorAux,
			String idClaveInstitucion, boolean isIndeval) {
		//Id de institución del registro.
		Long idInstitucion = null;
		//Reinicia la bandera de la validación
		valido = true;
		System.out.println("****************ValidatorFormatW8BENEServiceImpl");
		// 1. Si no es Indeval, valida que la institución con la que ingreso sea igual a la del registro
		if(!isIndeval) {
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
		// 3. Valida la fecha de formato
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
		// 3. Valida el campo 4
		if(valido) {			
			//Valida que haya capturado una opción.
			if(StringUtils.isBlank(fileTransferForma.getCampo4a())) {
				valido = false;
				agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
						FileTransferBenefConstantes.CHAPTER_3_STATUS, Constantes.MENSAJE_CAMPO_NULO);
			}
			//Valida que para persona moral extranjera a EUA haya capturado la opción A
			else if(FileTransferBenefConstantes.PERSONA_MORAL_EXTRANJERA_EUA.equals(fileTransferForma.getTipoBeneficiario()) && 
					!OpcionesCampo4BENE.A.toString().equals(fileTransferForma.getCampo4a())) {
				valido = false;
				agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
					FileTransferBenefConstantes.CHAPTER_3_STATUS, Constantes.MENSAJE_CAMPO_INVALIDO);	
			}
			//Valida que para fideicomisos complejos haya capturado la opción F
			else if(FileTransferBenefConstantes.FIDEICOMISO_COMPLEJO.equals(fileTransferForma.getTipoBeneficiario()) && 
					!OpcionesCampo4BENE.F.toString().equals(fileTransferForma.getCampo4a())) {
				valido = false;
				agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
					FileTransferBenefConstantes.CHAPTER_3_STATUS, Constantes.MENSAJE_CAMPO_INVALIDO);
			}
			//Valida que para SIEFORE/AFORE haya capturado la opción J
			else if(FileTransferBenefConstantes.SIEFORE_AFORE.equals(fileTransferForma.getTipoBeneficiario()) && 
					!OpcionesCampo4BENE.J.toString().equals(fileTransferForma.getCampo4a())) {
				valido = false;
				agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
					FileTransferBenefConstantes.CHAPTER_3_STATUS, Constantes.MENSAJE_CAMPO_INVALIDO);
			}
			
			else if(OpcionesCampo4BENE.B.toString().equals(fileTransferForma.getCampo4a()) 
        			&& (StringUtils.isBlank(fileTransferForma.getCampo4b()) 
        					|| !this.validaCampoBooleano(fileTransferForma.getCampo4b()))){
	            this.valido = false;
	            this.agregaMensajeError(fileTransferForma, listaRegistrosErrorAux,
	                    FileTransferBenefConstantes.COMPLETE_PART_III,
	                    Constantes.MENSAJE_CAMPO_INVALIDO);
        	}
			
			else if(OpcionesCampo4BENE.C.toString().equals(fileTransferForma.getCampo4a()) 
        			&& (StringUtils.isBlank(fileTransferForma.getCampo4b()) 
        					|| !this.validaCampoBooleano(fileTransferForma.getCampo4b()))){
        		this.valido = false;
	            this.agregaMensajeError(fileTransferForma, listaRegistrosErrorAux,
	                    FileTransferBenefConstantes.COMPLETE_PART_III,
	                    Constantes.MENSAJE_CAMPO_INVALIDO);
        	}
			
			else if(OpcionesCampo4BENE.D.toString().equals(fileTransferForma.getCampo4a()) 
        			&& (StringUtils.isBlank(fileTransferForma.getCampo4b()) 
        					|| !this.validaCampoBooleano(fileTransferForma.getCampo4b()))){
        		this.valido = false;
	            this.agregaMensajeError(fileTransferForma, listaRegistrosErrorAux,
	                    FileTransferBenefConstantes.COMPLETE_PART_III,
	                    Constantes.MENSAJE_CAMPO_INVALIDO);
        	}
			else if(OpcionesCampo4BENE.E.toString().equals(fileTransferForma.getCampo4a()) 
        			&& (StringUtils.isBlank(fileTransferForma.getCampo4b()) 
        					|| !this.validaCampoBooleano(fileTransferForma.getCampo4b()))){
        		this.valido = false;
	            this.agregaMensajeError(fileTransferForma, listaRegistrosErrorAux,
	                    FileTransferBenefConstantes.COMPLETE_PART_III,
	                    Constantes.MENSAJE_CAMPO_INVALIDO);
        	}
        	
        	//Valida que que sea una opción valida
			else if(!OpcionesCampo4BENE.isInEnum(fileTransferForma.getCampo4a(), OpcionesCampo4BENE.class)) {
				valido = false;
				agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
					FileTransferBenefConstantes.CHAPTER_3_STATUS, Constantes.MENSAJE_CAMPO_INVALIDO);
			}
		}


		// 4. Valida el campo 5. Si captura las opciones f, i, l, m, n, p, q, u, v, w, y, ab, o ae, valida
		// los campos 1 al 6.
		if(valido) {
			//Valida Part IV
			if(OpcionesCampo5BENE.F.toString().equals(fileTransferForma.getCampo5())) {
				validarParte_IV(fileTransferForma, listaRegistrosErrorAux);
			}
			//Valida Part VII y Part XXVIII
			else if(OpcionesCampo5BENE.I.toString().equals(fileTransferForma.getCampo5()) ||
					OpcionesCampo5BENE.AE.toString().equals(fileTransferForma.getCampo5())) {
				validarPartes_VII_XXVIII(fileTransferForma, listaRegistrosErrorAux);
			}
			//Valida Part X
			else if(OpcionesCampo5BENE.L.toString().equals(fileTransferForma.getCampo5())) {
				validarParte_X(fileTransferForma, listaRegistrosErrorAux);
			}
			//Valida Part XI y Part XXVI
			else if(OpcionesCampo5BENE.M.toString().equals(fileTransferForma.getCampo5()) ||
					OpcionesCampo5BENE.AB.toString().equals(fileTransferForma.getCampo5())) {
				validarPartes_XI_XXVI(fileTransferForma, listaRegistrosErrorAux);
			}
			//Valida Part XII
			else if(OpcionesCampo5BENE.N.toString().equals(fileTransferForma.getCampo5())) {
				validarParte_XII(fileTransferForma, listaRegistrosErrorAux);
			}
			//Valida Part XIV
			else if(OpcionesCampo5BENE.P.toString().equals(fileTransferForma.getCampo5())) {
				validarParte_XIV(fileTransferForma, listaRegistrosErrorAux);
			}
			//Valida Part XV
			else if(OpcionesCampo5BENE.Q.toString().equals(fileTransferForma.getCampo5())) {
				validarParte_XV(fileTransferForma, listaRegistrosErrorAux);
			}
			//Valida Part XIX, Part XX y Part XXI
			else if(OpcionesCampo5BENE.U.toString().equals(fileTransferForma.getCampo5()) ||
					OpcionesCampo5BENE.V.toString().equals(fileTransferForma.getCampo5()) ||
					OpcionesCampo5BENE.W.toString().equals(fileTransferForma.getCampo5())) {
				validarPartes_XIX_XX_XXI(fileTransferForma, listaRegistrosErrorAux);
			}
			//Valida Part XXIII
			else if(OpcionesCampo5BENE.Y.toString().equals(fileTransferForma.getCampo5())) {
				validarParte_XXIII(fileTransferForma, listaRegistrosErrorAux);
			}
			
			//Valida el campo 9a en opciones B, C, D
            else if(OpcionesCampo5BENE.B.toString().equals(fileTransferForma.getCampo5())
            		&& StringUtils.isBlank(fileTransferForma.getCampo9a())){
            	this.valido = false;
                this.agregaMensajeError(fileTransferForma, listaRegistrosErrorAux,
                        FileTransferBenefConstantes.GIIN_9a, Constantes.MENSAJE_CAMPO_NULO);
            }
            
            else if(OpcionesCampo5BENE.C.toString().equals(fileTransferForma.getCampo5())
            		&& StringUtils.isBlank(fileTransferForma.getCampo9a())){
            	this.valido = false;
                this.agregaMensajeError(fileTransferForma, listaRegistrosErrorAux,
                        FileTransferBenefConstantes.GIIN_9a, Constantes.MENSAJE_CAMPO_NULO);
            }
            
            else if(OpcionesCampo5BENE.D.toString().equals(fileTransferForma.getCampo5())
            		&& StringUtils.isBlank(fileTransferForma.getCampo9a())){
            	this.valido = false;
                this.agregaMensajeError(fileTransferForma, listaRegistrosErrorAux,
                        FileTransferBenefConstantes.GIIN_9a, Constantes.MENSAJE_CAMPO_NULO);
            }
		}
		// 5. Valida que haya capturado todos los campos del domicilo de correspondencia
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

		
		
		 if (!this.validaCampoBooleanoVerdadero(fileTransferForma.getCampo14a())
	                || !this.validaCampoBooleanoVerdadero(fileTransferForma.getCampo14b())) {
	            this.valido = false;
	            this.agregaMensajeError(fileTransferForma, listaRegistrosErrorAux,
	                    FileTransferBenefConstantes.CLAIM_TAX_TREATY_BENEFITS_A + " y/o "
	                            + FileTransferBenefConstantes.CLAIM_TAX_TREATY_BENEFITS_B,
	                    Constantes.MENSAJE_CAMPO_INVALIDO);
	        }
		 
        // Si esta seleccionado el campo 14b, debe hacer al menos una opcion de la sublista
        if(this.valido && this.validaCampoBooleanoVerdadero(fileTransferForma.getCampo14b())){
        	if(StringUtils.isBlank(fileTransferForma.getAnexo8())){
                this.valido = false;
                this.agregaMensajeError(fileTransferForma, listaRegistrosErrorAux,
                		FileTransferBenefConstantes.CAMPO_8, Constantes.MENSAJE_CAMPO_NULO);
        	}
        }
		
		// 8. Valida que si el beneficio es Siefore/Afore, los campos hayan sido capturados y el porcentaje sea correcto
		if(valido && FileTransferBenefConstantes.SIEFORE_AFORE.equals(fileTransferForma.getTipoBeneficiario())) {
			//Valida que todos los campos hayan sido capturados
			if (StringUtils.isBlank(fileTransferForma.getCampo15a())) {
				valido = false;
				agregaMensajeError(fileTransferForma, listaRegistrosErrorAux,
					FileTransferBenefConstantes.SPECIAL_RATES_CONDITIONS_ARTICLE, 
					Constantes.MENSAJE_CAMPO_NULO);
			}
			if (valido && StringUtils.isBlank(fileTransferForma.getCampo15b())) {
				valido = false;
				agregaMensajeError(fileTransferForma, listaRegistrosErrorAux,
					FileTransferBenefConstantes.SPECIAL_RATES_CONDITIONS_RATE_WITHHOLDING, 
					Constantes.MENSAJE_CAMPO_NULO);
			}
			if (valido && !(BeneficiariosConstantes.VALOR_DIVIDENDS.equals(fileTransferForma.getCampo15a()) ||
					BeneficiariosConstantes.VALOR_INTEREST.equals(fileTransferForma.getCampo15a())) && 
					StringUtils.isBlank(fileTransferForma.getCampo15c())) {
				valido = false;
				agregaMensajeError(fileTransferForma, listaRegistrosErrorAux,
					FileTransferBenefConstantes.SPECIAL_RATES_CONDITIONS_TYPE_INCOME, 
					Constantes.MENSAJE_CAMPO_NULO);
			}
		}
		//Valida el porcentaje
		if(valido && StringUtils.isNotBlank(fileTransferForma.getCampo15b())) {
			if(!fileTransferForma.getCampo15b().matches(Constantes.PATRON_PORCENTAJE_RETENCION)) {
				valido = false;
				agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
						FileTransferBenefConstantes.SPECIAL_RATES_CONDITIONS_RATE_WITHHOLDING, 
						Constantes.MENSAJE_CAMPO_INVALIDO);
			}
			if(valido) {
				Double nuevoPorcentaje = Double.valueOf(fileTransferForma.getCampo15b().trim());
				if(nuevoPorcentaje > Constantes.VALOR_MAXIMO_PORCENTAJE_RETENCION || nuevoPorcentaje < Constantes.VALOR_MINIMO_PORCENTAJE_RETENCION) {
					valido = false;
					agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
							FileTransferBenefConstantes.SPECIAL_RATES_CONDITIONS_RATE_WITHHOLDING, 
							Constantes.MENSAJE_CAMPO_INVALIDO);
				}
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
	 * Valida las longitudes de los campos de domicilio de correspondencia.
	 * @param valido Indica si es valido el campo.
	 * @param fileTransferForma Objeto con los campos a validar.
	 * @param listaRegistrosErrorAux Lista de errores.
	 */
	private void validarLongitudDomicilioCorrespondencia(FileTransferFormaW8BENE fileTransferForma, 
			List<FileTransferFormaW8BENE> listaRegistrosErrorAux) {
		valido = validarLongitudCadena(fileTransferForma.getCampo7a(), FileTransferBenefConstantes.LONGITUD_150,
			fileTransferForma, listaRegistrosErrorAux, FileTransferBenefConstantes.MAILING_ADDRESS);
		valido = valido && validarLongitudCadena(fileTransferForma.getCampo7b(), FileTransferBenefConstantes.LONGITUD_150,
			fileTransferForma, listaRegistrosErrorAux, FileTransferBenefConstantes.CITY_TOWN_7B);
		valido = valido && validarLongitudCadena(fileTransferForma.getCampo7c(), FileTransferBenefConstantes.LONGITUD_150,
			fileTransferForma, listaRegistrosErrorAux, FileTransferBenefConstantes.COUNTRY_7C);
	}

	/**
	 * Valida que la parte IV haya sido correctamente capturada.
	 * @param valido Variable donde se guarda el resultado de la validación.
	 * @param fileTransferForma Objeto con los campos a validar.
	 * @param listaRegistrosErrorAux Lista de errores.
	 */
	private void validarParte_IV(FileTransferFormaW8BENE fileTransferForma, 
			List<FileTransferFormaW8BENE> listaRegistrosErrorAux) {
		// 1) Valida que el campo 1 haya sido capturado.
		if(StringUtils.isBlank(fileTransferForma.getAnexo1())) {
			valido = false;
			agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
				FileTransferBenefConstantes.CAMPO_1, Constantes.MENSAJE_CAMPO_NULO);
		}
		//Valida que el campo 2 o 3 hayan sido capturados
		else if(StringUtils.isBlank(fileTransferForma.getAnexo2()) && 
				StringUtils.isBlank(fileTransferForma.getAnexo2())) {
			valido = false;
			agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
				FileTransferBenefConstantes.CAMPO_2 + " y/o " + FileTransferBenefConstantes.CAMPO_3, 
				Constantes.MENSAJE_CAMPO_NULO);
		}
		//Valida que el campo 2 o 3 tengan el valor correcto
		else if(!validaCampoBooleanoVerdadero(fileTransferForma.getAnexo2()) && 
				!validaCampoBooleanoVerdadero(fileTransferForma.getAnexo3())) {
			valido = false;
			agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
				FileTransferBenefConstantes.CAMPO_2 + " y/o " + FileTransferBenefConstantes.CAMPO_3, 
				Constantes.MENSAJE_CAMPO_INVALIDO);
		}
	}

	/**
	 * Valida que las partes VII y XXVIII hayan sido correctamente capturadas.
	 * @param valido Variable donde se guarda el resultado de la validación.
	 * @param fileTransferForma Objeto con los campos a validar.
	 * @param listaRegistrosErrorAux Lista de errores.
	 */
	private void validarPartes_VII_XXVIII(FileTransferFormaW8BENE fileTransferForma, 
			List<FileTransferFormaW8BENE> listaRegistrosErrorAux) {
		//Valida que los campos 1 y 2 hayan sidos capturados.
		if(StringUtils.isBlank(fileTransferForma.getAnexo1()) || 
				StringUtils.isBlank(fileTransferForma.getAnexo2())) {
			valido = false;
			agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
				FileTransferBenefConstantes.CAMPO_1 + " y/o " + FileTransferBenefConstantes.CAMPO_2, 
				Constantes.MENSAJE_CAMPO_NULO);
		}
		//Valida que el campo 2 sea valido
		else if(!validaCampoBooleanoVerdadero(fileTransferForma.getAnexo2())) {
			valido = false;
			agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
				FileTransferBenefConstantes.CAMPO_2, 
				Constantes.MENSAJE_CAMPO_INVALIDO);
		}
	}
	
	/**
	 * Valida que la parte X haya sido correctamente capturada.
	 * @param valido Variable donde se guarda el resultado de la validación.
	 * @param fileTransferForma Objeto con los campos a validar.
	 * @param listaRegistrosErrorAux Lista de errores.
	 */
	private void validarParte_X(FileTransferFormaW8BENE fileTransferForma, 
			List<FileTransferFormaW8BENE> listaRegistrosErrorAux) {
		//Valida que el campo 1 este capturado y al menos uno de los campos 2, 3 o 4 haya sido capturado
		if(StringUtils.isBlank(fileTransferForma.getAnexo1())){
			valido = false;
			agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
					FileTransferBenefConstantes.CAMPO_1, Constantes.MENSAJE_CAMPO_NULO);
		} else {
			int selected = 0;
			if(StringUtils.isNotBlank(fileTransferForma.getAnexo2())){
				selected++;
			}
			if(StringUtils.isNotBlank(fileTransferForma.getAnexo3())){
				selected++;
			}
			if(StringUtils.isNotBlank(fileTransferForma.getAnexo4())){
				selected++;
			}
			if(selected == 0){
				valido = false;
				agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
						FileTransferBenefConstantes.CAMPO_2 + " y/o " +
						FileTransferBenefConstantes.CAMPO_3 + " y/o " +
						FileTransferBenefConstantes.CAMPO_4 + " y/o ", 
						Constantes.MENSAJE_CAMPO_NULO);
			} else if(selected > 1){
				valido = false;
				agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
						FileTransferBenefConstantes.CAMPO_2 + " y/o " +
						FileTransferBenefConstantes.CAMPO_3 + " y/o " +
						FileTransferBenefConstantes.CAMPO_4 + " y/o ", 
						Constantes.MENSAJE_CAMPO_INVALIDO);
			} else {
				//Valida que los campos capturados sean validos.
				if(!validaCampoBooleanoVerdadero(fileTransferForma.getAnexo1()) &&
						!validaCampoBooleanoVerdadero(fileTransferForma.getAnexo2()) &&
						!validaCampoBooleanoVerdadero(fileTransferForma.getAnexo3()) &&
						!validaCampoBooleanoVerdadero(fileTransferForma.getAnexo4())) {
					valido = false;
					agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
						FileTransferBenefConstantes.CAMPO_1 + " y/o " + 
						FileTransferBenefConstantes.CAMPO_2 + " y/o " +
						FileTransferBenefConstantes.CAMPO_3 + " y/o " +
						FileTransferBenefConstantes.CAMPO_4 + " y/o ", 
						Constantes.MENSAJE_CAMPO_INVALIDO);
				}
			}
		}
	}
	
	/**
	 * Valida que las partes XI y XXVI hayan sido correctamente capturadas.
	 * @param valido Variable donde se guarda el resultado de la validación.
	 * @param fileTransferForma Objeto con los campos a validar.
	 * @param listaRegistrosErrorAux Lista de errores.
	 */
	private void validarPartes_XI_XXVI(FileTransferFormaW8BENE fileTransferForma, 
			List<FileTransferFormaW8BENE> listaRegistrosErrorAux) {
		//Valida que el campo 1 este capturado y solo campos 2 o 3 solo uno
		if(StringUtils.isBlank(fileTransferForma.getAnexo1())){
			valido = false;
			agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
					FileTransferBenefConstantes.CAMPO_1, Constantes.MENSAJE_CAMPO_NULO);
		} else {
			int selected = 0;
			if(StringUtils.isNotBlank(fileTransferForma.getAnexo2())){
				selected++;
			}
			if(StringUtils.isNotBlank(fileTransferForma.getAnexo3())){
				selected++;
			}
			if(selected == 0){
				valido = false;
				agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
						FileTransferBenefConstantes.CAMPO_2 + " y/o " +
						FileTransferBenefConstantes.CAMPO_3 + " y/o ",
						Constantes.MENSAJE_CAMPO_NULO);
			} else if(selected == 2){
				valido = false;
				agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
						FileTransferBenefConstantes.CAMPO_2 + " y/o " +
						FileTransferBenefConstantes.CAMPO_3 + " y/o ",
						Constantes.MENSAJE_CAMPO_INVALIDO);
			} else {
				//Valida que los campos capturados sean validos.
				if(valido && !validaCampoBooleanoVerdadero(fileTransferForma.getAnexo1()) &&
						!validaCampoBooleanoVerdadero(fileTransferForma.getAnexo2()) &&
						!validaCampoBooleanoVerdadero(fileTransferForma.getAnexo3())) {
					valido = false;
					agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
						FileTransferBenefConstantes.CAMPO_1 + " y/o " + 
						FileTransferBenefConstantes.CAMPO_2 + " y/o " +
						FileTransferBenefConstantes.CAMPO_3, 
						Constantes.MENSAJE_CAMPO_INVALIDO);
				}
			}
		}
	}
	
	/**
	 * Valida que la parte XII haya sido correctamente capturada.
	 * @param valido Variable donde se guarda el resultado de la validación.
	 * @param fileTransferForma Objeto con los campos a validar.
	 * @param listaRegistrosErrorAux Lista de errores.
	 */
    private void validarParte_XII(final FileTransferFormaW8BENE fileTransferForma,
            final List<FileTransferFormaW8BENE> listaRegistrosErrorAux) {
        if (StringUtils.isBlank(fileTransferForma.getAnexo1())) {
            this.valido = false;
            this.agregaMensajeError(fileTransferForma, listaRegistrosErrorAux,
                    FileTransferBenefConstantes.CAMPO_1, Constantes.MENSAJE_CAMPO_NULO);
        } else if (!this.validaCampoBooleanoVerdadero(fileTransferForma.getAnexo1())) {
            this.valido = false;
            this.agregaMensajeError(fileTransferForma, listaRegistrosErrorAux,
                    FileTransferBenefConstantes.CAMPO_1, Constantes.MENSAJE_CAMPO_INVALIDO);
        } else if (StringUtils.isBlank(fileTransferForma.getAnexo2())
        		|| StringUtils.isBlank(fileTransferForma.getAnexo5())) {
            this.valido = false;
            this.agregaMensajeError(fileTransferForma, listaRegistrosErrorAux,
            		FileTransferBenefConstantes.CAMPO_2 + " y/o " +
            		FileTransferBenefConstantes.CAMPO_5,
            		Constantes.MENSAJE_CAMPO_NULO);
        } else {
        	if (StringUtils.isNotBlank(fileTransferForma.getAnexo3()) && !this.validaCampoBooleanoVerdadero(fileTransferForma.getAnexo3())) {
                this.valido = false;
                this.agregaMensajeError(fileTransferForma, listaRegistrosErrorAux,
                        FileTransferBenefConstantes.CAMPO_3, Constantes.MENSAJE_CAMPO_INVALIDO);
        	} else if(StringUtils.isNotBlank(fileTransferForma.getAnexo4()) && !this.validaCampoBooleanoVerdadero(fileTransferForma.getAnexo4())) {
                this.valido = false;
                this.agregaMensajeError(fileTransferForma, listaRegistrosErrorAux,
                        FileTransferBenefConstantes.CAMPO_4, Constantes.MENSAJE_CAMPO_INVALIDO);
        	} else if(StringUtils.isNotBlank(fileTransferForma.getAnexo3()) && StringUtils.isNotBlank(fileTransferForma.getAnexo4())){
                this.valido = false;
                this.agregaMensajeError(fileTransferForma, listaRegistrosErrorAux,
                		FileTransferBenefConstantes.CAMPO_3 + " y/o " +
                		FileTransferBenefConstantes.CAMPO_4, Constantes.MENSAJE_CAMPO_INVALIDO);
        	} else if(StringUtils.isBlank(fileTransferForma.getAnexo3()) && StringUtils.isBlank(fileTransferForma.getAnexo4())){
                this.valido = false;
                this.agregaMensajeError(fileTransferForma, listaRegistrosErrorAux,
                		FileTransferBenefConstantes.CAMPO_3 + " y " +
                		FileTransferBenefConstantes.CAMPO_4, Constantes.MENSAJE_CAMPO_NULO);
        	}
        }
    }

	/**
	 * Valida que la parte XIV haya sido correctamente capturada.
	 * @param valido Variable donde se guarda el resultado de la validación.
	 * @param fileTransferForma Objeto con los campos a validar.
	 * @param listaRegistrosErrorAux Lista de errores.
	 */
	private void validarParte_XIV(FileTransferFormaW8BENE fileTransferForma, 
			List<FileTransferFormaW8BENE> listaRegistrosErrorAux) {
		if(StringUtils.isBlank(fileTransferForma.getAnexo1()) &&
				StringUtils.isBlank(fileTransferForma.getAnexo2())) {
			valido = false;
			agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
				FileTransferBenefConstantes.CAMPO_1 + " y/o " + 
				FileTransferBenefConstantes.CAMPO_2 + " y/o ", 
				Constantes.MENSAJE_CAMPO_NULO);
		}
		else if(StringUtils.isNotBlank(fileTransferForma.getAnexo1()) &&
				StringUtils.isNotBlank(fileTransferForma.getAnexo2())) {
			valido = false;
			agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
				FileTransferBenefConstantes.CAMPO_1 + " y/o " + 
				FileTransferBenefConstantes.CAMPO_2 + " y/o ", 
				Constantes.MENSAJE_CAMPO_INVALIDO);
		} else {
			if (!validaCampoBooleanoVerdadero(fileTransferForma.getAnexo1()) &&
					!validaCampoBooleanoVerdadero(fileTransferForma.getAnexo2())) {
				valido = false;
				agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
					FileTransferBenefConstantes.CAMPO_1 + " y/o " + 
					FileTransferBenefConstantes.CAMPO_2 + " y/o ", 
					Constantes.MENSAJE_CAMPO_INVALIDO);
			}
		}
	}
	
	/**
	 * Valida que la parte XV haya sido correctamente capturada.
	 * @param valido Variable donde se guarda el resultado de la validación.
	 * @param fileTransferForma Objeto con los campos a validar.
	 * @param listaRegistrosErrorAux Lista de errores.
	 */
	private void validarParte_XV(FileTransferFormaW8BENE fileTransferForma, 
			List<FileTransferFormaW8BENE> listaRegistrosErrorAux) {
		int selected = 0;
		if(StringUtils.isNotBlank(fileTransferForma.getAnexo1())){
			selected++;
		}
		if(StringUtils.isNotBlank(fileTransferForma.getAnexo2())){
			selected++;
		}
		if(StringUtils.isNotBlank(fileTransferForma.getAnexo3())){
			selected++;
		}
		if(StringUtils.isNotBlank(fileTransferForma.getAnexo4())){
			selected++;
		}
		if(StringUtils.isNotBlank(fileTransferForma.getAnexo5())){
			selected++;
		}
		if(StringUtils.isNotBlank(fileTransferForma.getAnexo6())){
			selected++;
		}
		if(selected == 0){
			valido = false;
			agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
					FileTransferBenefConstantes.CAMPO_1 + " y/o " +
					FileTransferBenefConstantes.CAMPO_2 + " y/o " +
					FileTransferBenefConstantes.CAMPO_3 + " y/o " +
					FileTransferBenefConstantes.CAMPO_4 + " y/o " +
					FileTransferBenefConstantes.CAMPO_5 + " y/o " +
					FileTransferBenefConstantes.CAMPO_6 + " y/o ", 
					Constantes.MENSAJE_CAMPO_NULO);
		} else if(selected > 1){
			valido = false;
			agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
				FileTransferBenefConstantes.CAMPO_1 + " y/o " + 
				FileTransferBenefConstantes.CAMPO_2 + " y/o " +
				FileTransferBenefConstantes.CAMPO_3 + " y/o " +
				FileTransferBenefConstantes.CAMPO_4 + " y/o " +
				FileTransferBenefConstantes.CAMPO_5 + " y/o " +
				FileTransferBenefConstantes.CAMPO_6, 
				Constantes.MENSAJE_CAMPO_INVALIDO);
		} else{
			if(!validaCampoBooleanoVerdadero(fileTransferForma.getAnexo1()) &&
					!validaCampoBooleanoVerdadero(fileTransferForma.getAnexo2()) &&
					!validaCampoBooleanoVerdadero(fileTransferForma.getAnexo3()) &&
					!validaCampoBooleanoVerdadero(fileTransferForma.getAnexo4()) &&
					!validaCampoBooleanoVerdadero(fileTransferForma.getAnexo5()) &&
					!validaCampoBooleanoVerdadero(fileTransferForma.getAnexo6())) {
				valido = false;
				agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
					FileTransferBenefConstantes.CAMPO_1 + " y/o " + 
					FileTransferBenefConstantes.CAMPO_2 + " y/o " +
					FileTransferBenefConstantes.CAMPO_3 + " y/o " +
					FileTransferBenefConstantes.CAMPO_4 + " y/o " +
					FileTransferBenefConstantes.CAMPO_5 + " y/o " +
					FileTransferBenefConstantes.CAMPO_6, 
					Constantes.MENSAJE_CAMPO_INVALIDO);
			}
		}
	}
	
	/**
	 * Valida que las partes XIX, XX y XXI hayan sido correctamente capturadas.
	 * @param valido Variable donde se guarda el resultado de la validación.
	 * @param fileTransferForma Objeto con los campos a validar.
	 * @param listaRegistrosErrorAux Lista de errores.
	 */
	private void validarPartes_XIX_XX_XXI(FileTransferFormaW8BENE fileTransferForma, 
			List<FileTransferFormaW8BENE> listaRegistrosErrorAux) {
		if(StringUtils.isBlank(fileTransferForma.getAnexo1())
				|| StringUtils.isBlank(fileTransferForma.getAnexo2())) {
			valido = false;
			agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
				FileTransferBenefConstantes.CAMPO_1 + " y/o " + FileTransferBenefConstantes.CAMPO_2, 
				Constantes.MENSAJE_CAMPO_NULO);
		}
		else if(!validaCampoBooleanoVerdadero(fileTransferForma.getAnexo1())) {
			valido = false;
			agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
				FileTransferBenefConstantes.CAMPO_1, Constantes.MENSAJE_CAMPO_INVALIDO);
		}
	}
	
	/**
	 * Valida que la parte XXIII haya sido correctamente capturada.
	 * @param valido Variable donde se guarda el resultado de la validación.
	 * @param fileTransferForma Objeto con los campos a validar.
	 * @param listaRegistrosErrorAux Lista de errores.
	 */
	private void validarParte_XXIII(FileTransferFormaW8BENE fileTransferForma, 
			List<FileTransferFormaW8BENE> listaRegistrosErrorAux) {
		if(StringUtils.isBlank(fileTransferForma.getAnexo1()) && 
				StringUtils.isBlank(fileTransferForma.getAnexo3())) {
			valido = false;
			agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
				FileTransferBenefConstantes.CAMPO_1 + " y/o " +
				FileTransferBenefConstantes.CAMPO_3, 
				Constantes.MENSAJE_CAMPO_NULO);
		} else if(StringUtils.isNotBlank(fileTransferForma.getAnexo1()) && 
				StringUtils.isNotBlank(fileTransferForma.getAnexo3())) {
			valido = false;
			agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
				FileTransferBenefConstantes.CAMPO_1 + " y/o " +
				FileTransferBenefConstantes.CAMPO_3, 
				Constantes.MENSAJE_CAMPO_INVALIDO);
		} else if(StringUtils.isNotBlank(fileTransferForma.getAnexo1())){
			if(StringUtils.isBlank(fileTransferForma.getAnexo2())){
				valido = false;
				agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
						FileTransferBenefConstantes.CAMPO_2, 
						Constantes.MENSAJE_CAMPO_NULO);
			}
		} else if(StringUtils.isNotBlank(fileTransferForma.getAnexo3())){
			if(StringUtils.isBlank(fileTransferForma.getAnexo4()) || StringUtils.isBlank(fileTransferForma.getAnexo5())){
				valido = false;
				agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
						FileTransferBenefConstantes.CAMPO_4 + " y/o " +
						FileTransferBenefConstantes.CAMPO_5,
						Constantes.MENSAJE_CAMPO_NULO);
			}
		} 
		
		if(valido){
			if(!validaCampoBooleanoVerdadero(fileTransferForma.getAnexo1()) && 
					!validaCampoBooleanoVerdadero(fileTransferForma.getAnexo3())) {
				valido = false;
				agregaMensajeError(fileTransferForma, listaRegistrosErrorAux, 
					FileTransferBenefConstantes.CAMPO_1 + " y/o " +
					FileTransferBenefConstantes.CAMPO_3, 
					Constantes.MENSAJE_CAMPO_INVALIDO);
			}
		}
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.validador.ValidatorBeneficiario#init()
	 */
	@Override
	public void init() {
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