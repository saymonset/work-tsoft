package com.indeval.portalinternacional.middleware.services.validador;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.persistence.modelo.Institucion;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService;
import com.indeval.portalinternacional.middleware.servicios.constantes.BeneficiariosConstantes;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.CatBic;
import com.indeval.portalinternacional.middleware.servicios.modelo.filetransfer.beneficiario.tipoformato.FileTransferFormaW8BEN2014;

/**
 * 
 * @author omarnl
 *
 */
public class ValidatorFormatW8BEN2014ServiceImpl extends ValidatorFormatW8BEN2014  {

	/** Para el registro de log de esta clase */
	private static final Logger log = LoggerFactory.getLogger(ValidatorFormatW8BEN2014ServiceImpl.class);
        
    
    private Map<String,Long> mapaCustodios = null;
    private Map<String,Long> mapaInstituciones = null;
    
    
    /** Mapa con los custodios validos */
    private Map<String, Long> custodios;
    
    /**Servicio para el control de beneficiarios */
    private ControlBeneficiariosService controlBeneficiariosService;
	
	
	public boolean esRegistroFileTransferValido(FileTransferFormaW8BEN2014 fileTransferFormaW8BEN2014,List<FileTransferFormaW8BEN2014> listaRegistrosErrorAux){		
		
		
		//tipo Formato
		if(esCampoFileTransferBeneficiarioNulo(fileTransferFormaW8BEN2014.getTipoFormato())){			        	
			agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W9_TIPO_FORMATO,Constantes.MENSAJE_CAMPO_NULO);			
			return false;		
		}
		
		//tipo Beneficiario
		if(esCampoFileTransferBeneficiarioNulo(fileTransferFormaW8BEN2014.getTipoBeneficiario())){
			agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W9_TIPO_BENEFICIARIO,Constantes.MENSAJE_CAMPO_NULO);		
			return false;				
		}
		
		//clave custodio
		if(esCampoFileTransferBeneficiarioNulo(fileTransferFormaW8BEN2014.getClaveCustodio())){
			agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W9_CUSTODIO,Constantes.MENSAJE_CAMPO_NULO);			
			return false;			
		}
		
		//valida custodio activo en bd
/*		if(!controlBeneficiariosService.esCatBicActivo(custodios.get(fileTransferFormaW8BEN2014.getClaveCustodio()))) {
			agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W9_CUSTODIO,Constantes.MENSAJE_INHABILITADO);			
			return false;
		}
*/
		// Valida abreviacion custodio
		CatBic catBic = controlBeneficiariosService.findAbreviacionByCustodio(fileTransferFormaW8BEN2014.getClaveCustodio()); 
		if(catBic == null || catBic.getAbreviacionCustodio() == null){
			agregaMensajeError(fileTransferFormaW8BEN2014, listaRegistrosErrorAux, Constantes.ABREVIACION_CUSTODIO, Constantes.MENSAJE_CAMPO_INVALIDO);			
		}
		
		if(catBic != null && (catBic.getActivo() == null || catBic.getActivo() == 0)){
			agregaMensajeError(fileTransferFormaW8BEN2014, listaRegistrosErrorAux, Constantes.W9_CUSTODIO, Constantes.MENSAJE_INHABILITADO);			
		}
	
		//clave instituci&oacute;n
		if(esCampoFileTransferBeneficiarioNulo(fileTransferFormaW8BEN2014.getClaveInstitucion())){
			agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W9_ID_INSTITUCION,Constantes.MENSAJE_CAMPO_NULO);			
			return false;
		}
		
		//fecha formato
		if(esCampoFileTransferBeneficiarioNulo(fileTransferFormaW8BEN2014.getFechaFormato())){
			agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W9_FECHA_FORMATO,Constantes.MENSAJE_CAMPO_NULO);			
			return false;
		}
		
		//Estado del beneficiario
		if(esCampoFileTransferBeneficiarioNulo(fileTransferFormaW8BEN2014.getEstado())){
			agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W9_ESTADO,Constantes.MENSAJE_CAMPO_NULO);			
			return false;
		}
		
		
		//nombre 
		if(esCampoFileTransferBeneficiarioNulo(fileTransferFormaW8BEN2014.getNombre())){
			agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W9_NAME,Constantes.MENSAJE_CAMPO_NULO);			
			return false;
		}
		
		//apellido paterno 
		if(esCampoFileTransferBeneficiarioNulo(fileTransferFormaW8BEN2014.getApellidoPaterno())){
					agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W8BEN2014_apellido_paterno,Constantes.MENSAJE_CAMPO_NULO);			
					return false;
		}
				
		//apellido materno 
		if(esCampoFileTransferBeneficiarioNulo(fileTransferFormaW8BEN2014.getApellidoMaterno())){
					agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W8BEN2014_apellido_materno,Constantes.MENSAJE_CAMPO_NULO);			
					return false;
		}
		
		//Country_of_citizenship
		if(esCampoFileTransferBeneficiarioNulo(fileTransferFormaW8BEN2014.getPaisResidencia())){
			agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W8BEN2014_Country_of_citizenship,Constantes.MENSAJE_CAMPO_NULO);		
			return false;			
		}
		
		
		//Street
		if(esCampoFileTransferBeneficiarioNulo(fileTransferFormaW8BEN2014.getResidenceAddressStreet())){
			agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W9_STREET,Constantes.MENSAJE_CAMPO_NULO);			
			return false;
		}
		
		//Outer number
		if(esCampoFileTransferBeneficiarioNulo(fileTransferFormaW8BEN2014.getResidenceAddressOutNumber())){
			agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W9_OUTER_NUMBER,Constantes.MENSAJE_CAMPO_NULO);			
			return false;
		}
		
		//interior number
		if(esCampoFileTransferBeneficiarioNulo(fileTransferFormaW8BEN2014.getResidenceAddressIntNumber())){
			agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W9_INTERIOR_NUMBER,Constantes.MENSAJE_CAMPO_NULO);			
			return false;
		}
		
		//zip code
		if(esCampoFileTransferBeneficiarioNulo(fileTransferFormaW8BEN2014.getResidenceAddressPostalCode())){
			agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W9_ZIP_CODE,Constantes.MENSAJE_CAMPO_NULO);			
			return false;    		
		}
		
		//city
		if(esCampoFileTransferBeneficiarioNulo(fileTransferFormaW8BEN2014.getResidenceAddressCity())){
			agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W9_CITY ,Constantes.MENSAJE_CAMPO_NULO);			
			return false;			
		}
		
		//state
		if(esCampoFileTransferBeneficiarioNulo(fileTransferFormaW8BEN2014.getResidenceAddressState())){
			agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W9_STATE,Constantes.MENSAJE_CAMPO_NULO);			
			return false;			
		}
		
		//country
		if(esCampoFileTransferBeneficiarioNulo(fileTransferFormaW8BEN2014.getResidenceAddressCountry())){
			agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W8BEN2014_RESIDENCE_COUNTRY,Constantes.MENSAJE_CAMPO_NULO);			
			return false;	
		}
		
		
		
		//mail Street
		if(esCampoFileTransferBeneficiarioNulo(fileTransferFormaW8BEN2014.getMailAddressStreet())){
			agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W8BEN2014_STREET_MA,Constantes.MENSAJE_CAMPO_NULO);			
			return false;
		}
		
		//mail Outer number
		if(esCampoFileTransferBeneficiarioNulo(fileTransferFormaW8BEN2014.getMailAddressOutNumber())){
			agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W8BEN2014_OUTER_NUMBER_MA,Constantes.MENSAJE_CAMPO_NULO);			
			return false;
		}
		
		//mail interior number
		if(esCampoFileTransferBeneficiarioNulo(fileTransferFormaW8BEN2014.getMailAddressIntNumber())){
			agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W8BEN2014_INTERIOR_NUMBER_MA,Constantes.MENSAJE_CAMPO_NULO);			
			return false;
		}
		
		//mail zip code
		if(esCampoFileTransferBeneficiarioNulo(fileTransferFormaW8BEN2014.getMailAddressPostalCode())){
			agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W8BEN2014_ZIP_CODE_MA,Constantes.MENSAJE_CAMPO_NULO);			
			return false;    		
		}
		
		//mail city
		if(esCampoFileTransferBeneficiarioNulo(fileTransferFormaW8BEN2014.getMailAddressCity())){
			agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W8BEN2014_CITY_MA ,Constantes.MENSAJE_CAMPO_NULO);			
			return false;			
		}
		
		//mail state
		if(esCampoFileTransferBeneficiarioNulo(fileTransferFormaW8BEN2014.getMailAddressState())){
			agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W8BEN2014_STATE_MA,Constantes.MENSAJE_CAMPO_NULO);			
			return false;			
		}
		
		//mail country
		if(esCampoFileTransferBeneficiarioNulo(fileTransferFormaW8BEN2014.getMailAddressCountry())){
			agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W8BEN2014_COUNTRY_MA,Constantes.MENSAJE_CAMPO_NULO);			
			return false;	
		}
		
		//Foreign_tax_payer_ID
		if(esCampoFileTransferBeneficiarioNulo(fileTransferFormaW8BEN2014.getForeingTaxIdNumber())){
			agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W8BEN2014_Foreign_tax_payer_ID,Constantes.MENSAJE_CAMPO_NULO);			
			return false;	
		}
		//ReferenceNumber
		if(esCampoFileTransferBeneficiarioNulo(fileTransferFormaW8BEN2014.getReferenceNumber())){
			agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W8BEN2014_reference_number,Constantes.MENSAJE_CAMPO_NULO);			
			return false;	
		}
		
		//FechaNacimiento
		if(esCampoFileTransferBeneficiarioNulo(fileTransferFormaW8BEN2014.getFechaNacimiento())){
			agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W8BEN2014_date_of_birth,Constantes.MENSAJE_CAMPO_NULO);			
			return false;	
		}
		
		// Pais de residencia beneficiario
		if(esCampoFileTransferBeneficiarioNulo(fileTransferFormaW8BEN2014.getPaisResidenteBeneficiaro())){
			agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W8BEN2014_country_of_residence,Constantes.MENSAJE_CAMPO_NULO);			
			return false;	
		}
		
		//IdArticuloReclamoTarifa
		if(esCampoFileTransferBeneficiarioNulo(fileTransferFormaW8BEN2014.getIdArticuloReclamoTarifa())){
			agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W8BEN2014_article,Constantes.MENSAJE_CAMPO_NULO);			
			return false;	
		}

		//getPorcentajeTasaRetencion
		if(esCampoFileTransferBeneficiarioNulo(fileTransferFormaW8BEN2014.getPorcentajeTasaRetencion())){
			agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W8BEN2014_rate_withholding,Constantes.MENSAJE_CAMPO_NULO);			
			return false;	
		}		


		if(esCampoFileTransferBeneficiarioNulo(fileTransferFormaW8BEN2014.getTipoIngreso())){
			agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W8BEN2014_type_of_income_reasons,Constantes.MENSAJE_CAMPO_NULO);			
			return false;	
		}
		
		if(esCampoFileTransferBeneficiarioNulo(fileTransferFormaW8BEN2014.getRazonArticuloReclamo())){
			agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W8BEN2014_reasons,Constantes.MENSAJE_CAMPO_NULO);			
			return false;	
		}
		
//		if(esCampoFileTransferBeneficiarioNulo(fileTransferFormaW8BEN2014.getNombreFirmante())){
//			agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W8BEN2014_name_of_signer,Constantes.MENSAJE_CAMPO_NULO);			
//			return false;	
//		}
//		
//		if(esCampoFileTransferBeneficiarioNulo(fileTransferFormaW8BEN2014.getCapacidadActuacion())){
//			agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W8BEN2014_capacity_whichacting,Constantes.MENSAJE_CAMPO_NULO);			
//			return false;	
//		}
		
		
		
		
		
		
		
		return true;
	}
	
	/*
	 * 
	 */
	public boolean validaReglasNegocioBeneficiarioFiletransfer(FileTransferFormaW8BEN2014 fileTransferFormaW8BEN2014,List<FileTransferFormaW8BEN2014> listaRegistrosErrorAux,String idClaveInstitucion,boolean isIndeval){		
													
					
		//tipo formato
		if(fileTransferFormaW8BEN2014.getTipoFormato().length() > Constantes.W8BEN2014_LONGITUD_TIPO_FORMATO ){
			agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W9_TIPO_FORMATO,Constantes.MENSAJE_LONGITUD_INVALIDO);
			return false;
		}else if(!getPatronAlfanumerico().matcher(fileTransferFormaW8BEN2014.getTipoFormato()).matches()){
			agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W9_TIPO_FORMATO,Constantes.MENSAJE_FORMATO_INCORRECTO);				
    		return false;			
		}else if(!fileTransferFormaW8BEN2014.getTipoFormato().equalsIgnoreCase(BeneficiariosConstantes.FORMATO_W8_BEN)||
				  fileTransferFormaW8BEN2014.getTipoFormato().equals(Constantes.W9_NO_APLICA)){
			agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W9_TIPO_FORMATO,Constantes.MENSAJE_CAMPO_INVALIDO);			
    		return false;
		} 		
		
		
		if(fileTransferFormaW8BEN2014.getTipoBeneficiario().length() > Constantes.LONGITUD_TIPO_BENEFICIARIO_W8BEN){
			agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W9_TIPO_BENEFICIARIO,Constantes.MENSAJE_LONGITUD_INVALIDO);		
			return false;
		}else if(!getPatronAlfabetico().matcher(fileTransferFormaW8BEN2014.getTipoBeneficiario()).matches()){
			agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W9_TIPO_BENEFICIARIO,Constantes.MENSAJE_FORMATO_INCORRECTO);		
			return false;			
		}
		else if( getMapeoTipoBeneficairioValidoW9().get(fileTransferFormaW8BEN2014.getTipoBeneficiario())  == null){
			agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W9_TIPO_BENEFICIARIO,Constantes.MENSAJE_CAMPO_INVALIDO);			
			return false;			
		}
		
		
		
		CatBic catBic = controlBeneficiariosService.findAbreviacionByCustodio(fileTransferFormaW8BEN2014.getClaveCustodio());
		//clave custodio
		if(fileTransferFormaW8BEN2014.getClaveCustodio().length() > Constantes.LONGITUD_CLAVE_CUSTODIO ){
			agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W9_CUSTODIO,Constantes.MENSAJE_LONGITUD_INVALIDO);	
			return false;
		}else if(!getPatronAlfabetico().matcher(fileTransferFormaW8BEN2014.getClaveCustodio()).matches()){
			agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W9_CUSTODIO,Constantes.MENSAJE_FORMATO_INCORRECTO);	
			return false;			
		}
/*		else if(mapaCustodios.get(fileTransferFormaW8BEN2014.getClaveCustodio()) == null){
			agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W9_CUSTODIO,Constantes.MENSAJE_CAMPO_INVALIDO);			
			return false;			
		}*/else {
			//TODO HACER EL SETEO DEL ID CUSTODIO
			if(catBic != null){
				fileTransferFormaW8BEN2014.setIdCuentaNombrada(catBic.getCuentaNombrada().getIdCuentaNombrada());
			}
			
		}
		
		
		
		
		//clave instituci&oacute;n
		if(fileTransferFormaW8BEN2014.getClaveInstitucion().length() > Constantes.LONGITUD_INSTITUCION){
			agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W9_ID_INSTITUCION,Constantes.MENSAJE_LONGITUD_INVALIDO);			
			return false;
		}else if(!getPatronNumerico().matcher(fileTransferFormaW8BEN2014.getClaveInstitucion()).matches()){
			agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W9_ID_INSTITUCION,Constantes.MENSAJE_FORMATO_INCORRECTO);			
			return false;				
		}		
		if(!isIndeval){
			if(!fileTransferFormaW8BEN2014.getClaveInstitucion().equals(idClaveInstitucion)){
				agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W9_ID_INSTITUCION,Constantes.MENSAJE_CAMPO_INVALIDO);				
				return false;
			}
		}
		
		
		Long idInstitucion=mapaInstituciones.get(fileTransferFormaW8BEN2014.getClaveInstitucion());
		if(idInstitucion==null){
			Institucion institucion = getInstitucionDao().findInstitucionByClaveFolio(fileTransferFormaW8BEN2014.getClaveInstitucion());
			if(institucion == null){
				agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W9_ID_INSTITUCION,Constantes.MENSAJE_CAMPO_INVALIDO);				
				return false;
			}
			idInstitucion=institucion.getIdInstitucion();
			mapaInstituciones.put(fileTransferFormaW8BEN2014.getClaveInstitucion(), idInstitucion);
		}
		
		
		fileTransferFormaW8BEN2014.setIdInstitucion(idInstitucion);
		
		
		
		
		
		//estado 							
		if(fileTransferFormaW8BEN2014.getEstado().length() > Constantes.LONGITUD_ESTADO_INACTIVO){
					agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W9_ESTADO,Constantes.MENSAJE_LONGITUD_INVALIDO);			
					return false;
		}else if(!getPatronAlfanumerico().matcher(fileTransferFormaW8BEN2014.getEstado()).matches()){
					agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W9_ESTADO,Constantes.MENSAJE_FORMATO_INCORRECTO);			
					return false; 					
		}else if( getMapeoTipoEstadoBeneficiario().get(fileTransferFormaW8BEN2014.getEstado()) == null){
					agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W9_ESTADO,Constantes.MENSAJE_CAMPO_INVALIDO);
					return false; 
		}
		
		
		//nombre 		
		if(fileTransferFormaW8BEN2014.getNombre().length() > Constantes.W8BEN2014_LONGITUD_Name){
			agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W9_NAME,Constantes.MENSAJE_LONGITUD_INVALIDO);			
			return false;  
		}else if(fileTransferFormaW8BEN2014.getNombre().startsWith("_")){
			agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W9_NAME,Constantes.MENSAJE_FORMATO_INCORRECTO);			
			return false; 
		}else if(fileTransferFormaW8BEN2014.getNombre().equals(Constantes.W9_NO_APLICA)){
			agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W9_NAME,Constantes.MENSAJE_CAMPO_INVALIDO);
			return false;
		}
		
		//apellido paterno 		
		if(fileTransferFormaW8BEN2014.getApellidoPaterno().length() > Constantes.W8BEN2014_LONGITUD_Ape_paterno){
					agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W8BEN2014_apellido_paterno,Constantes.MENSAJE_LONGITUD_INVALIDO);			
					return false;  
		}else if(fileTransferFormaW8BEN2014.getApellidoPaterno().startsWith("_")){
					agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W8BEN2014_apellido_paterno,Constantes.MENSAJE_FORMATO_INCORRECTO);			
					return false; 
		}else if(fileTransferFormaW8BEN2014.getApellidoPaterno().equals(Constantes.W9_NO_APLICA)){
					agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W8BEN2014_apellido_paterno,Constantes.MENSAJE_CAMPO_INVALIDO);
					return false;
		}
				
		//apellido materno 		
		if(fileTransferFormaW8BEN2014.getApellidoMaterno().length() > Constantes.W8BEN2014_LONGITUD_Ape_materno){
					agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W8BEN2014_apellido_materno,Constantes.MENSAJE_LONGITUD_INVALIDO);			
					return false;  
		}else if(fileTransferFormaW8BEN2014.getApellidoMaterno().startsWith("_")){
					agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W8BEN2014_apellido_materno,Constantes.MENSAJE_FORMATO_INCORRECTO);			
					return false; 
		}else if(fileTransferFormaW8BEN2014.getApellidoMaterno().equals(Constantes.W9_NO_APLICA)){
					agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W8BEN2014_apellido_materno,Constantes.MENSAJE_CAMPO_INVALIDO);
					return false;
		}
		
		
		
		Date fecha = null;
		if(fileTransferFormaW8BEN2014.getFechaFormato().length() > Constantes.LONGITUD_FECHA_FORMATO){
			agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W9_FECHA_FORMATO,Constantes.MENSAJE_LONGITUD_INVALIDO);			
			return false;
		}else if(!getPatronFechaFormato().matcher(fileTransferFormaW8BEN2014.getFechaFormato()).matches()){
			agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W9_FECHA_FORMATO,Constantes.MENSAJE_FORMATO_INCORRECTO);			
			return false;				
		}else{
			String fechaFormaSimple = convierteFechaArchivoFileTransfer(fileTransferFormaW8BEN2014.getFechaFormato());
			
			if(fechaFormaSimple == null){
			agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W9_FECHA_FORMATO,Constantes.MENSAJE_CAMPO_INVALIDO);
			return false; 									
			}
							
			try{
				fecha = getSimpledateformat().parse(fechaFormaSimple);
				fileTransferFormaW8BEN2014.setDateFechaFormato(fecha);
			}catch(ParseException pe){
				agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W9_FECHA_FORMATO,Constantes.MENSAJE_FORMATO_INCORRECTO);
				return false; 
			}					
		}
		
		
		
		
		//pais de residencia			
		if(fileTransferFormaW8BEN2014.getPaisResidencia().length() > Constantes.W8BEN2014_LONGITUD_RESIDENCIA){
			agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W8BEN2014_Country_of_citizenship,Constantes.MENSAJE_LONGITUD_INVALIDO);			
			return false; 
		}else if(fileTransferFormaW8BEN2014.getPaisResidencia().startsWith("_")){
					agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W8BEN2014_Country_of_citizenship,Constantes.MENSAJE_FORMATO_INCORRECTO);			
					return false; 
		}else if(fileTransferFormaW8BEN2014.getPaisResidencia().equals(Constantes.W9_NO_APLICA)){
			agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W8BEN2014_Country_of_citizenship,Constantes.MENSAJE_CAMPO_INVALIDO);
			return false;
		}
		
		//Direccion			
		if(fileTransferFormaW8BEN2014.getResidenceAddressStreet().length() > Constantes.W8BEN2014_LONGITUD_Street){
				agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W9_STREET,Constantes.MENSAJE_LONGITUD_INVALIDO);			
				return false;
		}else if(fileTransferFormaW8BEN2014.getResidenceAddressStreet().startsWith("_")){
				agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W9_STREET,Constantes.MENSAJE_FORMATO_INCORRECTO);			
				return false; 
		}else if(fileTransferFormaW8BEN2014.getResidenceAddressStreet().equals(Constantes.W9_NO_APLICA)){
				agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W9_STREET,Constantes.MENSAJE_CAMPO_INVALIDO);
				return false;
		}
				
		//OutNumber			
		if(fileTransferFormaW8BEN2014.getResidenceAddressOutNumber().length() > Constantes.W8BEN2014_LONGITUD_OutNumber){
				agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W9_OUTER_NUMBER,Constantes.MENSAJE_LONGITUD_INVALIDO);			
				return false;
		}else if(fileTransferFormaW8BEN2014.getResidenceAddressOutNumber().startsWith("_")){
				agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W9_OUTER_NUMBER,Constantes.MENSAJE_FORMATO_INCORRECTO);			
				return false; 
		}else if(fileTransferFormaW8BEN2014.getResidenceAddressOutNumber().equals(Constantes.W9_NO_APLICA)){
				agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W9_OUTER_NUMBER,Constantes.MENSAJE_CAMPO_INVALIDO);
				return false;
		}

		//IntNumber			
		if(fileTransferFormaW8BEN2014.getResidenceAddressIntNumber().length() > Constantes.W8BEN2014_LONGITUD_IntNumber){
			agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W9_INTERIOR_NUMBER,Constantes.MENSAJE_LONGITUD_INVALIDO);			
			return false;
		}else if(fileTransferFormaW8BEN2014.getResidenceAddressIntNumber().startsWith("_")){
			agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W9_INTERIOR_NUMBER,Constantes.MENSAJE_FORMATO_INCORRECTO);			
			return false; 
		} 

		//PostalCode			
		if(fileTransferFormaW8BEN2014.getResidenceAddressPostalCode().length() > Constantes.W8BEN2014_LONGITUD_PostalCode){
			agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W9_ZIP_CODE,Constantes.MENSAJE_LONGITUD_INVALIDO);			
			return false;
		}else if(fileTransferFormaW8BEN2014.getResidenceAddressPostalCode().startsWith("_")){
			agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W9_ZIP_CODE,Constantes.MENSAJE_FORMATO_INCORRECTO);			
			return false; 
		}else if(fileTransferFormaW8BEN2014.getResidenceAddressPostalCode().equals(Constantes.W9_NO_APLICA)){
			agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W9_ZIP_CODE,Constantes.MENSAJE_CAMPO_INVALIDO);
			return false;
		}
		
		
		//City
		if(fileTransferFormaW8BEN2014.getResidenceAddressCity().length() > Constantes.W8BEN2014_LONGITUD_City){
			agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W9_CITY,Constantes.MENSAJE_LONGITUD_INVALIDO);			
			return false; 
		}else if(fileTransferFormaW8BEN2014.getResidenceAddressCity().startsWith("_")){
			agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W9_CITY,Constantes.MENSAJE_FORMATO_INCORRECTO);			
			return false; 
		}else if(fileTransferFormaW8BEN2014.getResidenceAddressCity().equals(Constantes.W9_NO_APLICA)){
			agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W9_CITY,Constantes.MENSAJE_CAMPO_INVALIDO);
			return false;
		}
				
		//State
		if(fileTransferFormaW8BEN2014.getResidenceAddressState().length() > Constantes.W8BEN2014_LONGITUD_State ){
			agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W9_STATE,Constantes.MENSAJE_LONGITUD_INVALIDO);			
			return false;
		}else if(fileTransferFormaW8BEN2014.getResidenceAddressState().startsWith("_")){
			agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W9_STATE,Constantes.MENSAJE_FORMATO_INCORRECTO);			
			return false; 
		}else if(fileTransferFormaW8BEN2014.getResidenceAddressState().equals(Constantes.W9_NO_APLICA)){
			agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W9_STATE,Constantes.MENSAJE_CAMPO_INVALIDO);
			return false;
		}
	
		
		
		// Country
		if(fileTransferFormaW8BEN2014.getResidenceAddressCountry().length() > Constantes.W8BEN2014_LONGITUD_Country){
			agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W8BEN2014_RESIDENCE_COUNTRY,Constantes.MENSAJE_LONGITUD_INVALIDO);			
			return false;
		}else if(fileTransferFormaW8BEN2014.getResidenceAddressCountry().startsWith("_")){
			agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W8BEN2014_RESIDENCE_COUNTRY,Constantes.MENSAJE_FORMATO_INCORRECTO);			
			return false; 
		}else if(fileTransferFormaW8BEN2014.getResidenceAddressCountry().equals(Constantes.W9_NO_APLICA)){
			agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W8BEN2014_RESIDENCE_COUNTRY,Constantes.MENSAJE_CAMPO_INVALIDO);
			return false;
		}
		
		String valoresMailAddress[]={fileTransferFormaW8BEN2014.getMailAddressStreet(),
								 fileTransferFormaW8BEN2014.getMailAddressOutNumber(),								 
								 fileTransferFormaW8BEN2014.getMailAddressPostalCode(),
								 fileTransferFormaW8BEN2014.getMailAddressCity(),
								 fileTransferFormaW8BEN2014.getMailAddressState(),
								 fileTransferFormaW8BEN2014.getMailAddressCountry()
		};
		
		int numNAMailAddress=countNA(valoresMailAddress);
		//No NA mail alddress
		if(numNAMailAddress>0&&numNAMailAddress!=valoresMailAddress.length	){
					agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W8BEN2014_NA_MA,Constantes.MENSAJE_FORMATO_INCORRECTO);			
					return false;
		}
		
		
		
		if(fileTransferFormaW8BEN2014.getMailAddressStreet().length() > Constantes.W8BEN2014_LONGITUD_Street){
			agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W8BEN2014_STREET_MA,Constantes.MENSAJE_LONGITUD_INVALIDO);			
			return false;
		}
		
		if(fileTransferFormaW8BEN2014.getMailAddressOutNumber().length() > Constantes.W8BEN2014_LONGITUD_OutNumber){
			agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W8BEN2014_OUTER_NUMBER_MA,Constantes.MENSAJE_LONGITUD_INVALIDO);			
			return false;
		}
		
		if(fileTransferFormaW8BEN2014.getMailAddressIntNumber().length() > Constantes.W8BEN2014_LONGITUD_IntNumber){
			agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W8BEN2014_INTERIOR_NUMBER_MA,Constantes.MENSAJE_LONGITUD_INVALIDO);			
			return false;
		}
		
		if(fileTransferFormaW8BEN2014.getMailAddressPostalCode().length() > Constantes.W8BEN2014_LONGITUD_PostalCode){
			agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W8BEN2014_ZIP_CODE_MA,Constantes.MENSAJE_LONGITUD_INVALIDO);			
			return false;
		}
		
		if(fileTransferFormaW8BEN2014.getMailAddressCity().length() > Constantes.W8BEN2014_LONGITUD_City){
			agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W8BEN2014_CITY_MA,Constantes.MENSAJE_LONGITUD_INVALIDO);			
			return false;
		}
		
		if(fileTransferFormaW8BEN2014.getMailAddressState().length() > Constantes.W8BEN2014_LONGITUD_State){
			agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W8BEN2014_STATE_MA,Constantes.MENSAJE_LONGITUD_INVALIDO);			
			return false;
		}
		
		if(fileTransferFormaW8BEN2014.getMailAddressCountry().length() > Constantes.W8BEN2014_LONGITUD_Country){
			agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W8BEN2014_COUNTRY_MA,Constantes.MENSAJE_LONGITUD_INVALIDO);			
			return false;
		}

		//getForeingTaxIdNumber 	
		if(fileTransferFormaW8BEN2014.getForeingTaxIdNumber().length() > Constantes.W8BEN2014_LONGITUD_Foreign_tax_payer_ID){
			agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W8BEN2014_Foreign_tax_payer_ID,Constantes.MENSAJE_LONGITUD_INVALIDO);			
			return false;
		}
		else if(fileTransferFormaW8BEN2014.getForeingTaxIdNumber().startsWith("_")){
			agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W8BEN2014_Foreign_tax_payer_ID,Constantes.MENSAJE_FORMATO_INCORRECTO);			
			return false; 
		}
		else if("NA".equalsIgnoreCase(fileTransferFormaW8BEN2014.getForeingTaxIdNumber())){
			agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W8BEN2014_Foreign_tax_payer_ID,Constantes.MENSAJE_CAMPO_INVALIDO);			
			return false;
		}

		//getReferenceNumber
		if(fileTransferFormaW8BEN2014.getReferenceNumber().length() > Constantes.W8BEN2014_LONGITUD_reference_number){
			log.info("getReferenceNumber");
			agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W8BEN2014_reference_number,Constantes.MENSAJE_LONGITUD_INVALIDO);			
			return false;
		}else if(	fileTransferFormaW8BEN2014.getReferenceNumber().startsWith("_")){
			agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W8BEN2014_reference_number,Constantes.MENSAJE_FORMATO_INCORRECTO);			
			return false; 
		}else if(fileTransferFormaW8BEN2014.getIdCuentaNombrada()==Constantes.CN_CLEARSTREAM){
			fileTransferFormaW8BEN2014.setReferenceNumber("75986");
		}else if(fileTransferFormaW8BEN2014.getIdCuentaNombrada()==Constantes.CN_EUROCLEAR_BANK){
			fileTransferFormaW8BEN2014.setReferenceNumber("23310");
		}
		
		
		
		//Fecha Nacimiento
		if(fileTransferFormaW8BEN2014.getFechaNacimiento().length() > Constantes.LONGITUD_FECHA_FORMATO){
			agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W8BEN2014_date_of_birth,Constantes.MENSAJE_LONGITUD_INVALIDO);			
			return false;
		}else if(!getPatronFechaFormato().matcher(fileTransferFormaW8BEN2014.getFechaNacimiento()).matches()){
			agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W8BEN2014_date_of_birth,Constantes.MENSAJE_FORMATO_INCORRECTO);			
			return false;				
		}else {
			String fechaFormaSimple = convierteFechaArchivoFileTransfer(fileTransferFormaW8BEN2014.getFechaNacimiento());
			
			if(fechaFormaSimple == null){
				agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W8BEN2014_date_of_birth,Constantes.MENSAJE_CAMPO_INVALIDO);
				return false; 									
			}
							
			try{
				fecha = getSimpledateformat().parse(fechaFormaSimple);
				
			}catch(ParseException pe){
				agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W8BEN2014_date_of_birth,Constantes.MENSAJE_FORMATO_INCORRECTO);
				return false; 
			}
			
			
			
			if(fecha != null){
				Calendar cal = Calendar.getInstance();
				int yearActual = cal.get(Calendar.YEAR);			
				cal.setTime(fecha);
			    int year = cal.get(Calendar.YEAR);
			    
			    if(year < (yearActual-100) || year >= yearActual){
			    	agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W8BEN2014_date_of_birth,Constantes.MENSAJE_FORMATO_INCORRECTO);
			    	return false; 
			    }
			    fileTransferFormaW8BEN2014.setDateFechaNacimiento(fecha);
			}
			
			
			
		}
		
		
		//pais de residencia beneficiario		 	
		if(fileTransferFormaW8BEN2014.getPaisResidenteBeneficiaro().length() > Constantes.W8BEN2014_LONGITUD_RESIDENCIA){
			agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W8BEN2014_country_of_residence,Constantes.MENSAJE_LONGITUD_INVALIDO);			
			return false;
		}else if(fileTransferFormaW8BEN2014.getPaisResidenteBeneficiaro().startsWith("_")){
			agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W8BEN2014_country_of_residence,Constantes.MENSAJE_FORMATO_INCORRECTO);			
			return false; 
		}else if(fileTransferFormaW8BEN2014.getPaisResidenteBeneficiaro().equals(Constantes.W9_NO_APLICA)){
			agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W8BEN2014_country_of_residence,Constantes.MENSAJE_CAMPO_INVALIDO);
			return false;
		}
		
		String valoresReclamo[]={fileTransferFormaW8BEN2014.getIdArticuloReclamoTarifa(),
						  fileTransferFormaW8BEN2014.getPorcentajeTasaRetencion(),
						  fileTransferFormaW8BEN2014.getTipoIngreso(),
						  fileTransferFormaW8BEN2014.getRazonArticuloReclamo()};
		
		int numNAReclamo=countNA(valoresReclamo);
		//No NA Reclamo
		if(numNAReclamo>0&&numNAReclamo!=valoresReclamo.length	){
			agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W8BEN2014_article+","+
					 Constantes.W8BEN2014_rate_withholding+","+
					 Constantes.W8BEN2014_type_of_income_reasons+","+
					 Constantes.W8BEN2014_reasons,Constantes.MENSAJE_FORMATO_INCORRECTO);			
			return false;
		}else if(numNAReclamo==0){
			//article 	
			if(fileTransferFormaW8BEN2014.getIdArticuloReclamoTarifa().length() > Constantes.W8BEN2014_LONGITUD_article){
					agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W8BEN2014_article,Constantes.MENSAJE_LONGITUD_INVALIDO);			
					return false;
			}else if(fileTransferFormaW8BEN2014.getIdArticuloReclamoTarifa().startsWith("_")){
					agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W8BEN2014_article,Constantes.MENSAJE_FORMATO_INCORRECTO);			
					return false; 
			}
	
			
			//getPorcentajeTasaRetencion
			if(fileTransferFormaW8BEN2014.getPorcentajeTasaRetencion().length() > Constantes.W8BEN2014_LONGITUD_rate_withholding){
					agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W8BEN2014_rate_withholding,Constantes.MENSAJE_LONGITUD_INVALIDO);			
					return false;
			}else if(fileTransferFormaW8BEN2014.getPorcentajeTasaRetencion().startsWith("_")){
					agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W8BEN2014_rate_withholding,Constantes.MENSAJE_FORMATO_INCORRECTO);			
					return false; 		
			}else if(!getPatronPorcentaje().matcher(fileTransferFormaW8BEN2014.getPorcentajeTasaRetencion()).matches()){
				agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W8BEN2014_rate_withholding,Constantes.MENSAJE_FORMATO_INCORRECTO);			
				return false;		
			}
	
			//getTipoIngreso
			if(fileTransferFormaW8BEN2014.getTipoIngreso().length() > Constantes.W8BEN2014_LONGITUD_type_of_income_reasons){
					agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W8BEN2014_type_of_income_reasons,Constantes.MENSAJE_LONGITUD_INVALIDO);			
					return false;
			}else if(fileTransferFormaW8BEN2014.getTipoIngreso().startsWith("_")){
					agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W8BEN2014_type_of_income_reasons,Constantes.MENSAJE_FORMATO_INCORRECTO);			
					return false; 
			}
			
			//getRazonArticuloReclamo
			if(fileTransferFormaW8BEN2014.getRazonArticuloReclamo().length() > Constantes.W8BEN2014_LONGITUD_reasons){
					agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W8BEN2014_reasons,Constantes.MENSAJE_LONGITUD_INVALIDO);			
					return false;
			}else if(fileTransferFormaW8BEN2014.getRazonArticuloReclamo().startsWith("_")){
					agregaMensajeError(fileTransferFormaW8BEN2014,listaRegistrosErrorAux,Constantes.W8BEN2014_reasons,Constantes.MENSAJE_FORMATO_INCORRECTO);			
					return false; 
			}
			
		}
		
		
		
		return true;
	}
	
	private int countNA(String valores[]){
		int numNA=0;
		for(int i=0; i<valores.length; i++){
			if(valores[i].equalsIgnoreCase(Constantes.W9_NO_APLICA))
				numNA++;			
		}
		return numNA;
	}
	
	
	
	@Override
	public void init() {
		if(log.isDebugEnabled()){
			log.debug("Inicializando mapa de custodios e instituciones");
		}
		if(mapaCustodios==null){										
			mapaCustodios=getCatBicDao().getCustodiosIdCuentaNombrada();
		}
		if(mapaInstituciones==null){
			mapaInstituciones=new HashMap<String,Long>();
		}
		super.init();
	}
	
	@Override
	public void release() {
		// TODO Auto-generated method stub
		if(log.isDebugEnabled()){
			log.debug("Liberando mapa de custodios e instituciones");
		}
		if(mapaCustodios!=null){
			mapaCustodios.clear();
			mapaCustodios=null;
		}
		
		if(mapaInstituciones!=null){
			mapaInstituciones.clear();
			mapaInstituciones=null;
		}
		super.release();
	}

	public Map<String, Long> getCustodios() {
		return custodios;
	}

	public void setCustodios(Map<String, Long> custodios) {
		this.custodios = custodios;
	}

	public ControlBeneficiariosService getControlBeneficiariosService() {
		return controlBeneficiariosService;
	}

	public void setControlBeneficiariosService(ControlBeneficiariosService controlBeneficiariosService) {
		this.controlBeneficiariosService = controlBeneficiariosService;
	}
	
	
    	
	
}