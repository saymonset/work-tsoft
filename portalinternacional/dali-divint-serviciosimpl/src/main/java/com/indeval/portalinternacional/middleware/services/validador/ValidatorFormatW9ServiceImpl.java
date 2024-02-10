/**
 * 
 */
package com.indeval.portalinternacional.middleware.services.validador;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.persistence.modelo.Institucion;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService;
import com.indeval.portalinternacional.middleware.servicios.constantes.BeneficiariosConstantes;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.CatBic;
import com.indeval.portalinternacional.middleware.servicios.modelo.FormatoW9;
import com.indeval.portalinternacional.middleware.servicios.modelo.filetransfer.beneficiario.tipoformato.FileTransferFormaW9;

/**
 * Implementa las validaciones del formato W9
 * 
 * @author César Hernández
 *
 */
public class ValidatorFormatW9ServiceImpl extends ValidatorFormatW9  {

	/** Para el registro de log de esta clase */
	private static final Logger log = LoggerFactory.getLogger(ValidatorFormatW9ServiceImpl.class);
    
    
    /** Mapa con los custodios validos */
    private Map<String, Long> custodios;
    
    /**Servicio para el control de beneficiarios */
    private ControlBeneficiariosService controlBeneficiariosService;    
   
	
	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.validador.ValidatorFormatService#validaFormato()
	 */
	public void validaFormatoW(Beneficiario beneficiario) throws BusinessException {
		
		FormatoW9 formato = beneficiario.getFormatoW9();
		
		getValidatorService().validaDTONoNulo(formato, " formato W9 ");
		
		getValidatorService().validaDTONoNulo(formato.getTypeTaxPayer(), " Tax Payer ");
		getValidatorService().validaDTONoNulo(formato.getTypeTaxPayer().getIdCampo(), " Tax Payer ");
		
		if(formato.getTypeTaxPayer().getIdCampo() <= 0) {
			throw new BusinessException(getErrorResolver().getMessage("J0053", (Object)" Tax Payer "));
		 }
		
		if( beneficiario.getTipoBeneficiario().getIdTipoBeneficiario() == Constantes.PERSONA_FISICA_EUA ) {
			if( formato.getTypeTaxPayer().getIdCampo() != 1 ) {
				throw new BusinessException( getErrorResolver().getMessage("J0062", new Object[] {}) );
			}
			if( StringUtils.isBlank(formato.getSsn()) ) {
				throw new BusinessException( getErrorResolver().getMessage("J0065", new Object[] {}) );
			} else {
				Pattern pattern =  Pattern.compile("\\d{3}-\\d{2}\\-\\d{4}");
				Matcher matcher =  pattern.matcher(formato.getSsn());
				
				if( formato.getSsn().length() != 11 || !matcher.find() ) {
					throw new BusinessException( getErrorResolver().getMessage("J0067", new Object[] {}) );
				}
			}
		} else if( beneficiario.getTipoBeneficiario().getIdTipoBeneficiario() == Constantes.PERSONA_MORAL_EUA ) {
			/* se modifican las condiciones, se agrega el 3,5,6 y 7 */
			if( formato.getTypeTaxPayer().getIdCampo() != 2 && formato.getTypeTaxPayer().getIdCampo() != 3 &&
					formato.getTypeTaxPayer().getIdCampo() != 4 && formato.getTypeTaxPayer().getIdCampo() != 5 &&
					formato.getTypeTaxPayer().getIdCampo() != 6 && formato.getTypeTaxPayer().getIdCampo() != 7) {
				throw new BusinessException( getErrorResolver().getMessage("J0063", new Object[] {}) );
			} else if(formato.getTypeTaxPayer().getIdCampo() == 4) {
				getValidatorService().validarClaveTipo(formato.getTaxClassification(), " Tax Classification  ");
				/*se modifican las letras permitidas, anteriores D,C y P,  actuales C,S y P. Se modifico la etiqueta del error J0076 */
				if(!formato.getTaxClassification().equals("S") &&
						!formato.getTaxClassification().equals("C") &&
						!formato.getTaxClassification().equals("P") ) {
					throw new BusinessException( getErrorResolver().getMessage("J0076", new Object[] {}) );
				}
			}
			// se agrega validacion del campo Other, si se selecciono esta opcion
			else if(formato.getTypeTaxPayer().getIdCampo()==5){
				getValidatorService().validarClaveTipo(formato.getOtherDescription(), " Other ");
			}
			if( formato.getExemptPayee() == null || !formato.getExemptPayee() ) {
				throw new BusinessException( getErrorResolver().getMessage("J0064", new Object[] {}) );
			}
			if( StringUtils.isBlank(formato.getEmployerIdNumb()) ) {
				throw new BusinessException( getErrorResolver().getMessage("J0066", new Object[] {}) );
			} else {
				Pattern pattern =  Pattern.compile("\\d{2}-\\d{7}");
				Matcher matcher =  pattern.matcher(formato.getEmployerIdNumb());
				
				if( formato.getEmployerIdNumb().length() != 10 || !matcher.find() ) {
					throw new BusinessException( getErrorResolver().getMessage("J0068", new Object[] {}) );
				}
			}
			
//			if(formato.getExemptPayeeW9().getIdExemptPayeeW9() != null) {
			if(formato.getExemptPayeeW9() == null) {
				throw new BusinessException( getErrorResolver().getMessage("J0102", new Object[] {}) );
			} else if(formato.getExemptPayeeW9() != null) {
				if(formato.getExemptPayeeW9().getIdExemptPayeeW9() != null && formato.getExemptPayeeW9().getIdExemptPayeeW9().longValue() <= 0){
					throw new BusinessException( getErrorResolver().getMessage("J0102", new Object[] {}) );
				}
			}
			
//			if(formato.getExemptionFatcaW9().getIdExemptionFatcaW9() != null) {
			if(formato.getExemptionFatcaW9() == null) {
				throw new BusinessException( getErrorResolver().getMessage("J0103", new Object[] {}) );
			}else if(formato.getExemptionFatcaW9() != null) {
				if(formato.getExemptionFatcaW9().getIdExemptionFatcaW9() != null && formato.getExemptionFatcaW9().getIdExemptionFatcaW9().longValue() <= 0){
					throw new BusinessException( getErrorResolver().getMessage("J0103", new Object[] {}) );
				}
			}
		}	
	}
	
	
	
	public boolean esRegistroFileTransferValido(FileTransferFormaW9 fileTransferFormaW9,List<FileTransferFormaW9> listaRegistrosErrorAux){		
		log.info("Entrando a ValidatorFormatW9ServiceImpl.esRegistroFileTransferValido()");
		
		//tipo Formato
		if(esCampoFileTransferBeneficiarioNulo(fileTransferFormaW9.getTipoFormato())){			        	
			agregaMensajeError(fileTransferFormaW9,listaRegistrosErrorAux,Constantes.W9_TIPO_FORMATO,Constantes.MENSAJE_CAMPO_NULO);			
			return false;		
		}
		
		//tipo Beneficiario
		if(esCampoFileTransferBeneficiarioNulo(fileTransferFormaW9.getTipoBeneficiario())){
			agregaMensajeError(fileTransferFormaW9,listaRegistrosErrorAux,Constantes.W9_TIPO_BENEFICIARIO,Constantes.MENSAJE_CAMPO_NULO);		
			return false;				
		}
		
		//clave custodio
		if(esCampoFileTransferBeneficiarioNulo(fileTransferFormaW9.getClaveCustodio())){
			agregaMensajeError(fileTransferFormaW9,listaRegistrosErrorAux,Constantes.W9_CUSTODIO,Constantes.MENSAJE_CAMPO_NULO);			
			return false;			
		}
		// Valida abreviacion custodio
		CatBic catBic = controlBeneficiariosService.findAbreviacionByCustodio(fileTransferFormaW9.getClaveCustodio()); 
		if(catBic == null || catBic.getAbreviacionCustodio() == null){
			agregaMensajeError(fileTransferFormaW9, listaRegistrosErrorAux, Constantes.ABREVIACION_CUSTODIO, Constantes.MENSAJE_CAMPO_INVALIDO);			
		}
		
		if(catBic != null && (catBic.getActivo() == null || catBic.getActivo() == 0)){
			agregaMensajeError(fileTransferFormaW9, listaRegistrosErrorAux, Constantes.W9_CUSTODIO, Constantes.MENSAJE_INHABILITADO);			
		}
		
		//valida custodio activo en bd
/*		if(!controlBeneficiariosService.esCatBicActivo(custodios.get(fileTransferFormaW9.getClaveCustodio()))) {
			agregaMensajeError(fileTransferFormaW9,listaRegistrosErrorAux,Constantes.W9_CUSTODIO,Constantes.MENSAJE_INHABILITADO);			
			return false;
		}
*/		
		//clave instituci&oacute;n
		if(esCampoFileTransferBeneficiarioNulo(fileTransferFormaW9.getClaveInstitucion())){
			agregaMensajeError(fileTransferFormaW9,listaRegistrosErrorAux,Constantes.W9_ID_INSTITUCION,Constantes.MENSAJE_CAMPO_NULO);			
			return false;
		}
		
		//fecha formato
		if(esCampoFileTransferBeneficiarioNulo(fileTransferFormaW9.getFechaFormato())){
			agregaMensajeError(fileTransferFormaW9,listaRegistrosErrorAux,Constantes.W9_FECHA_FORMATO,Constantes.MENSAJE_CAMPO_NULO);			
			return false;
		}
		
		//Estado del beneficiario
		if(esCampoFileTransferBeneficiarioNulo(fileTransferFormaW9.getEstado())){
			agregaMensajeError(fileTransferFormaW9,listaRegistrosErrorAux,Constantes.W9_ESTADO,Constantes.MENSAJE_CAMPO_NULO);			
			return false;
		}
		
		//nombre - razon social
		if(esCampoFileTransferBeneficiarioNulo(fileTransferFormaW9.getRazonSocial())){
			agregaMensajeError(fileTransferFormaW9,listaRegistrosErrorAux,Constantes.W9_NAME,Constantes.MENSAJE_CAMPO_NULO);			
			return false;
		}
		
		//Business name
		if(esCampoFileTransferBeneficiarioNulo(fileTransferFormaW9.getBussinesName())){
			agregaMensajeError(fileTransferFormaW9,listaRegistrosErrorAux,Constantes.W9_BUSINESS_NAME,Constantes.MENSAJE_CAMPO_NULO);		
			return false;			
		}
		
		//Tipo TaxPayer
		if(esCampoFileTransferBeneficiarioNulo(fileTransferFormaW9.getTipoTaxPayer())){
			agregaMensajeError(fileTransferFormaW9,listaRegistrosErrorAux,Constantes.W9_CLASIFICATION,Constantes.MENSAJE_CAMPO_NULO);			
			return false;
		}
		
		//Exempt payee code
		if(esCampoFileTransferBeneficiarioNulo(fileTransferFormaW9.getExemptPayeeCode())){
			agregaMensajeError(fileTransferFormaW9,listaRegistrosErrorAux,Constantes.W9_EXEMPT_PAYEE_CODE,Constantes.MENSAJE_CAMPO_NULO);			
			return false;			
		}
		
		//fatca reporting Code
		if(esCampoFileTransferBeneficiarioNulo(fileTransferFormaW9.getFatcaReportingCode())){
			agregaMensajeError(fileTransferFormaW9,listaRegistrosErrorAux,Constantes.W9_EXEMPTION_FATCA_CODE,Constantes.MENSAJE_CAMPO_NULO);			
			return false;
		}
		
		//Street
		if(esCampoFileTransferBeneficiarioNulo(fileTransferFormaW9.getStreet())){
			agregaMensajeError(fileTransferFormaW9,listaRegistrosErrorAux,Constantes.W9_STREET,Constantes.MENSAJE_CAMPO_NULO);			
			return false;
		}
		
		//Outer number
		if(esCampoFileTransferBeneficiarioNulo(fileTransferFormaW9.getOuterNumber())){
			agregaMensajeError(fileTransferFormaW9,listaRegistrosErrorAux,Constantes.W9_OUTER_NUMBER,Constantes.MENSAJE_CAMPO_NULO);			
			return false;
		}
		
		//interior number
		if(esCampoFileTransferBeneficiarioNulo(fileTransferFormaW9.getInteriorNumber())){
			agregaMensajeError(fileTransferFormaW9,listaRegistrosErrorAux,Constantes.W9_INTERIOR_NUMBER,Constantes.MENSAJE_CAMPO_NULO);			
			return false;
		}
		
		//zip code
		if(esCampoFileTransferBeneficiarioNulo(fileTransferFormaW9.getZipCode())){
			agregaMensajeError(fileTransferFormaW9,listaRegistrosErrorAux,Constantes.W9_ZIP_CODE,Constantes.MENSAJE_CAMPO_NULO);			
			return false;    		
		}
		
		//city
		if(esCampoFileTransferBeneficiarioNulo(fileTransferFormaW9.getCity())){
			agregaMensajeError(fileTransferFormaW9,listaRegistrosErrorAux,Constantes.W9_CITY ,Constantes.MENSAJE_CAMPO_NULO);			
			return false;			
		}
		
		//state
		if(esCampoFileTransferBeneficiarioNulo(fileTransferFormaW9.getState())){
			agregaMensajeError(fileTransferFormaW9,listaRegistrosErrorAux,Constantes.W9_STATE,Constantes.MENSAJE_CAMPO_NULO);			
			return false;			
		}
		
		//requester name and address
		if(esCampoFileTransferBeneficiarioNulo(fileTransferFormaW9.getRequesterNameAddress())){
			agregaMensajeError(fileTransferFormaW9,listaRegistrosErrorAux,Constantes.W9_REQUESTER_NAME_AND_ADDRESS,Constantes.MENSAJE_CAMPO_NULO);			
			return false;	
		}
		
		//getListAccountNumbers
		if(esCampoFileTransferBeneficiarioNulo(fileTransferFormaW9.getListAccountNumbers())){
			agregaMensajeError(fileTransferFormaW9,listaRegistrosErrorAux,Constantes.W9_LIST_ACCOUNT_NUMBER,Constantes.MENSAJE_CAMPO_NULO);			
			return false;
		}
		
		//ssn
		if(esCampoFileTransferBeneficiarioNulo(fileTransferFormaW9.getSsn())){
			agregaMensajeError(fileTransferFormaW9,listaRegistrosErrorAux,Constantes.W9_SECURITY_NUMBER,Constantes.MENSAJE_CAMPO_NULO);			
			return false;
		}
		
		//Employer idNumb
		if(esCampoFileTransferBeneficiarioNulo(fileTransferFormaW9.getEmployerIdNumb())){
			agregaMensajeError(fileTransferFormaW9,listaRegistrosErrorAux,Constantes.W9_EMPLOYER_ID_NUMBER,Constantes.MENSAJE_CAMPO_NULO);			
			return false;
		}
		
		return true;
	}
	
	/*
	 * 
	 */
	public boolean validaReglasNegocioBeneficiarioFiletransfer(FileTransferFormaW9 fileTransferFormaW9,List<FileTransferFormaW9> listaRegistrosErrorAux,String idClaveInstitucion,boolean isIndeval){		
		log.info("Entrando a ValidatorFormatW9ServiceImpl.validaReglasNegocioBeneficiarioFiletransfer()");											
					
		//tipo formato
		if(fileTransferFormaW9.getTipoFormato().length() != Constantes.LONGITUD_TIPO_FORMATO || !getPatronAlfanumerico().matcher(fileTransferFormaW9.getTipoFormato()).matches()){
			agregaMensajeError(fileTransferFormaW9,listaRegistrosErrorAux,Constantes.W9_TIPO_FORMATO,Constantes.MENSAJE_FORMATO_INCORRECTO);				
    		return false;			
		}
		else if(!fileTransferFormaW9.getTipoFormato().equalsIgnoreCase(BeneficiariosConstantes.FORMATO_W9)){
			agregaMensajeError(fileTransferFormaW9,listaRegistrosErrorAux,Constantes.W9_TIPO_FORMATO,Constantes.MENSAJE_CAMPO_INVALIDO);			
    		return false;
		} 		
		
		//tipo Beneficiario
		boolean esPersonaFisica = false;
		if(fileTransferFormaW9.getTipoBeneficiario().length() != Constantes.LONGITUD_TIPO_BENEFICIARIO || !getPatronAlfabetico().matcher(fileTransferFormaW9.getTipoBeneficiario()).matches()){
			agregaMensajeError(fileTransferFormaW9,listaRegistrosErrorAux,Constantes.W9_TIPO_BENEFICIARIO,Constantes.MENSAJE_FORMATO_INCORRECTO);		
			return false;			
		}
		else if( getMapeoTipoBeneficairioValidoW9().get(fileTransferFormaW9.getTipoBeneficiario())  == null){
			agregaMensajeError(fileTransferFormaW9,listaRegistrosErrorAux,Constantes.W9_TIPO_BENEFICIARIO,Constantes.MENSAJE_CAMPO_INVALIDO);			
			return false;			
		}else if(getMapeoTipoBeneficairioValidoW9().get(fileTransferFormaW9.getTipoBeneficiario())){
			esPersonaFisica = true;		
		}
		fileTransferFormaW9.setPersonaFisica(esPersonaFisica);
		
		//clave custodio
		if(fileTransferFormaW9.getClaveCustodio().length() != Constantes.LONGITUD_CLAVE_CUSTODIO || !getPatronAlfabetico().matcher(fileTransferFormaW9.getClaveCustodio()).matches()){
			agregaMensajeError(fileTransferFormaW9,listaRegistrosErrorAux,Constantes.W9_CUSTODIO,Constantes.MENSAJE_FORMATO_INCORRECTO);	
			return false;			
		}
/*		else if(getCatBicDao().getCatalogoCustodios().get(fileTransferFormaW9.getClaveCustodio()) == null){
			agregaMensajeError(fileTransferFormaW9,listaRegistrosErrorAux,Constantes.W9_CUSTODIO,Constantes.MENSAJE_CAMPO_INVALIDO);			
			return false;			
		}
*/		
		//clave instituci&oacute;n
		if(fileTransferFormaW9.getClaveInstitucion().length() != Constantes.LONGITUD_INSTITUCION || !getPatronNumerico().matcher(fileTransferFormaW9.getClaveInstitucion()).matches()){
			agregaMensajeError(fileTransferFormaW9,listaRegistrosErrorAux,Constantes.W9_ID_INSTITUCION,Constantes.MENSAJE_FORMATO_INCORRECTO);			
			return false;				
		}		
		if(!isIndeval){
			if(!fileTransferFormaW9.getClaveInstitucion().equals(idClaveInstitucion)){
				agregaMensajeError(fileTransferFormaW9,listaRegistrosErrorAux,Constantes.W9_ID_INSTITUCION,Constantes.MENSAJE_CAMPO_INVALIDO);				
				return false;
			}
		}			
		Institucion institucion = getInstitucionDao().findInstitucionByClaveFolio(fileTransferFormaW9.getClaveInstitucion());
		if(institucion == null){
			agregaMensajeError(fileTransferFormaW9,listaRegistrosErrorAux,Constantes.W9_ID_INSTITUCION,Constantes.MENSAJE_CAMPO_INVALIDO);				
			return false;
		}
		fileTransferFormaW9.setIdInstitucion(institucion.getIdInstitucion());
		
		//fecha Formato
		Date fecha = null;
		if(fileTransferFormaW9.getFechaFormato().length() != Constantes.LONGITUD_FECHA_FORMATO || !getPatronFechaFormato().matcher(fileTransferFormaW9.getFechaFormato()).matches()){
			agregaMensajeError(fileTransferFormaW9,listaRegistrosErrorAux,Constantes.W9_FECHA_FORMATO,Constantes.MENSAJE_FORMATO_INCORRECTO);			
			return false;				
		}else{
			String fechaFormaSimple = convierteFechaArchivoFileTransfer(fileTransferFormaW9.getFechaFormato());
			
			if(fechaFormaSimple == null){
			agregaMensajeError(fileTransferFormaW9,listaRegistrosErrorAux,Constantes.W9_FECHA_FORMATO,Constantes.MENSAJE_CAMPO_INVALIDO);
			return false; 									
			}
							
			try{
				fecha = getSimpledateformat().parse(fechaFormaSimple);
				fileTransferFormaW9.setFechaFormatoDate(fecha);
			}catch(ParseException pe){
				agregaMensajeError(fileTransferFormaW9,listaRegistrosErrorAux,Constantes.W9_FECHA_FORMATO,Constantes.MENSAJE_FORMATO_INCORRECTO);
				return false; 
			}					
		}
		
		//estado custodio								
		if((fileTransferFormaW9.getEstado().length() != Constantes.LONGITUD_ESTADO_ACTIVO && fileTransferFormaW9.getEstado().length() != Constantes.LONGITUD_ESTADO_INACTIVO) ||
					!getPatronAlfabetico().matcher(fileTransferFormaW9.getEstado()).matches()){
			agregaMensajeError(fileTransferFormaW9,listaRegistrosErrorAux,Constantes.W9_ESTADO,Constantes.MENSAJE_FORMATO_INCORRECTO);			
			return false; 					
		}else if( getMapeoTipoEstadoBeneficiario().get(fileTransferFormaW9.getEstado()) == null){
			agregaMensajeError(fileTransferFormaW9,listaRegistrosErrorAux,Constantes.W9_ESTADO,Constantes.MENSAJE_CAMPO_INVALIDO);
			return false; 
		}
		fileTransferFormaW9.setActivo(getMapeoTipoEstadoBeneficiario().get(fileTransferFormaW9.getEstado()));
		
		//nombre - razon social			
		if(fileTransferFormaW9.getRazonSocial().length() > Constantes.LONGITUD_MAXIMA_NAME || fileTransferFormaW9.getRazonSocial().startsWith("_")){
			agregaMensajeError(fileTransferFormaW9,listaRegistrosErrorAux,Constantes.W9_NAME,Constantes.MENSAJE_FORMATO_INCORRECTO);			
			return false; 
		}	
		
		// business name
		boolean cuentaConBusinesName = false;
		if(!fileTransferFormaW9.getBussinesName().equalsIgnoreCase(Constantes.W9_NO_APLICA) && !esPersonaFisica){
			if(fileTransferFormaW9.getBussinesName().length() > Constantes.LONGITUD_MAXIMA_BUSINESS_NAME){
				agregaMensajeError(fileTransferFormaW9,listaRegistrosErrorAux,Constantes.W9_BUSINESS_NAME,Constantes.MENSAJE_FORMATO_INCORRECTO);			
				return false;
			}
			cuentaConBusinesName = true;											
		}
		fileTransferFormaW9.setCuentaConBusinessName(cuentaConBusinesName);
		
		//clasification
		if(fileTransferFormaW9.getTipoTaxPayer().length() > Constantes.LONGITUD_MAXIMA_CLASIFICATION){
				agregaMensajeError(fileTransferFormaW9,listaRegistrosErrorAux,Constantes.W9_CLASIFICATION,Constantes.MENSAJE_FORMATO_INCORRECTO);			
				return false;
		}
		
		// validaciones para personas fisicas
		if(fileTransferFormaW9.getPersonaFisica()) {
			//clasification			
			if(getMapeoW9TypeTaxPayer().get(fileTransferFormaW9.getTipoTaxPayer()) == null || getMapeoW9TypeTaxPayer().get(fileTransferFormaW9.getTipoTaxPayer()) > Constantes.INDIVIDUAL){
				agregaMensajeError(fileTransferFormaW9,listaRegistrosErrorAux,Constantes.W9_CLASIFICATION,Constantes.MENSAJE_CAMPO_INVALIDO);
				return false;					
			}
			fileTransferFormaW9.setIdTaxPayer(Constantes.INDIVIDUAL);				
			
			//ssn
			if(fileTransferFormaW9.getSsn().length() != Constantes.LONGITUD_SSN || !getPatronSsn().matcher(fileTransferFormaW9.getSsn()).matches()){
				agregaMensajeError(fileTransferFormaW9,listaRegistrosErrorAux,Constantes.W9_SECURITY_NUMBER,Constantes.MENSAJE_FORMATO_INCORRECTO);			
				return false;
			}
			
			///datos que no aplican para personas fisicas
			fileTransferFormaW9.setEmployerIdNumb(Constantes.W9_NO_APLICA);
			fileTransferFormaW9.setBussinesName(Constantes.W9_NO_APLICA);
			fileTransferFormaW9.setExemptPayeeCode(Constantes.W9_NO_APLICA);
			fileTransferFormaW9.setFatcaReportingCode(Constantes.W9_NO_APLICA);
		
		}else{
			///validaciones para personas morales
			
			//clasification
			if(( getMapeoW9TypeTaxPayer().get(fileTransferFormaW9.getTipoTaxPayer()) == null && !fileTransferFormaW9.getTipoTaxPayer().startsWith("OTH-"))){
				agregaMensajeError(fileTransferFormaW9,listaRegistrosErrorAux,Constantes.W9_CLASIFICATION,Constantes.MENSAJE_CAMPO_INVALIDO);			
				return false;					
			}else if(getMapeoW9TypeTaxPayer().get(fileTransferFormaW9.getTipoTaxPayer()) != null && getMapeoW9TypeTaxPayer().get(fileTransferFormaW9.getTipoTaxPayer()) == Constantes.INDIVIDUAL){
				agregaMensajeError(fileTransferFormaW9,listaRegistrosErrorAux,Constantes.W9_CLASIFICATION,Constantes.MENSAJE_CAMPO_INVALIDO);			
				return false;
			}
			
			Long idTaxPayer = getMapeoW9TypeTaxPayer().get(fileTransferFormaW9.getTipoTaxPayer());			
			if(fileTransferFormaW9.getTipoTaxPayer().startsWith("OTH-")){
				if(fileTransferFormaW9.getTipoTaxPayer().length() < 5){
					agregaMensajeError(fileTransferFormaW9,listaRegistrosErrorAux,Constantes.W9_CLASIFICATION,Constantes.MENSAJE_CAMPO_INVALIDO);			
					return false;
				}					
				fileTransferFormaW9.setIdTaxPayer(Constantes.OTHER);
				fileTransferFormaW9.setDescripcionTaxPayer(fileTransferFormaW9.getTipoTaxPayer().substring(4));
			}else{
				fileTransferFormaW9.setIdTaxPayer(idTaxPayer);
			}
			
			if(idTaxPayer != null && idTaxPayer == Constantes.LLIMITED_LIABILITY_COMPANY){
				fileTransferFormaW9.setTaxClassification(fileTransferFormaW9.getTipoTaxPayer().substring(4));				
			}
			
			//Exempt payee Code
			if(fileTransferFormaW9.getExemptPayeeCode().length() > Constantes.LONGITUD_MAXIMA_CODE_EXEMPT_PAYEE || !getPatronNumerico().matcher(fileTransferFormaW9.getExemptPayeeCode()).matches()){
				agregaMensajeError(fileTransferFormaW9,listaRegistrosErrorAux,Constantes.W9_EXEMPT_PAYEE_CODE,Constantes.MENSAJE_FORMATO_INCORRECTO);			
				return false;
			}
			Integer valorExemptPaye = null;
			try{
				valorExemptPaye = Integer.valueOf(fileTransferFormaW9.getExemptPayeeCode());
			}catch(NumberFormatException ne){
				agregaMensajeError(fileTransferFormaW9,listaRegistrosErrorAux,Constantes.W9_EXEMPT_PAYEE_CODE,Constantes.MENSAJE_FORMATO_INCORRECTO);			
				return false;
			}
			
			Long idExemptPayee = getBeneficiarioDao().getCatalogoExemptPayeeW9().get(valorExemptPaye);
			if(idExemptPayee == null){
				agregaMensajeError(fileTransferFormaW9,listaRegistrosErrorAux,Constantes.W9_EXEMPT_PAYEE_CODE,Constantes.MENSAJE_CAMPO_INVALIDO);			
				return false;
			}
			fileTransferFormaW9.setIdExemptPayee(idExemptPayee);
									
			// codigo fatca
			if(fileTransferFormaW9.getFatcaReportingCode().length() > Constantes.LONGITUD_CODE_FATCA || !getPatronAlfabetico().matcher(fileTransferFormaW9.getFatcaReportingCode()).matches()){
				agregaMensajeError(fileTransferFormaW9,listaRegistrosErrorAux,Constantes.W9_EXEMPTION_FATCA_CODE,Constantes.MENSAJE_FORMATO_INCORRECTO);			
				return false;			    	
			}
			Long idFatcaCode = getBeneficiarioDao().getCatalogoExemptionFatcaW9().get(fileTransferFormaW9.getFatcaReportingCode());
			if(idFatcaCode == null){
				agregaMensajeError(fileTransferFormaW9,listaRegistrosErrorAux,Constantes.W9_EXEMPTION_FATCA_CODE,Constantes.MENSAJE_CAMPO_INVALIDO);			
				return false;
			}
			fileTransferFormaW9.setIdFatcaCode(idFatcaCode);
						
			//Employed IdNumb
			if(fileTransferFormaW9.getEmployerIdNumb().length() != Constantes.LONGITUD_EMPLOYER_IDENTIFICATION_NUMBER || !getPatronEmployedId().matcher(fileTransferFormaW9.getEmployerIdNumb()).matches()){
				agregaMensajeError(fileTransferFormaW9,listaRegistrosErrorAux,Constantes.W9_EMPLOYER_ID_NUMBER,Constantes.MENSAJE_FORMATO_INCORRECTO);			
				return false;
			}
			fileTransferFormaW9.setSsn(Constantes.W9_NO_APLICA);
		}
		//interior number
		boolean cuentaConInteriorNumber = false;
		int longitudNumeroInterior = 0;
		if(!fileTransferFormaW9.getInteriorNumber().equalsIgnoreCase(Constantes.W9_NO_APLICA)){															
			cuentaConInteriorNumber = true;
			longitudNumeroInterior = fileTransferFormaW9.getInteriorNumber().length(); 
		}
		
		//se valida la logitud maxima de la suma de los campos street, numero exterior e interior si cuenta con uno
		int longitudCalle = fileTransferFormaW9.getStreet().length();
		int longitudNumeroExterior = fileTransferFormaW9.getOuterNumber().length();
		int longitudDireccion1 = longitudCalle + longitudNumeroExterior + longitudNumeroInterior;  
		if(longitudDireccion1 > Constantes.LONGITUD_MAXIMA_DIRECCION){
			agregaMensajeError(fileTransferFormaW9,listaRegistrosErrorAux,Constantes.W9_STREER_OUTER_NUMBER_INTERIOR,Constantes.MENSAJE_FORMATO_INCORRECTO);			
			return false;
		}		
		fileTransferFormaW9.setCuentaConNumeroInterior(cuentaConInteriorNumber);
		
		//se valida la longitud maxima de la suma de los campos zip code, city y state
		int longitudZipCode = fileTransferFormaW9.getZipCode().length();
		int longitudCity = fileTransferFormaW9.getCity().length();
		int longitudState = fileTransferFormaW9.getState().length();
		int longitudDireccion2 = longitudZipCode + longitudCity + longitudState;		
		if(longitudDireccion2 >Constantes.LONGITUD_MAXIMA_DIRECCION){
			agregaMensajeError(fileTransferFormaW9,listaRegistrosErrorAux,Constantes.W9_CITY_STATE_ZIP_CODE,Constantes.MENSAJE_FORMATO_INCORRECTO);
			return false;
		}
		
		//requester name address
		boolean cuentaConRequesterNameAddress = false;
		if(!fileTransferFormaW9.getRequesterNameAddress().equalsIgnoreCase(Constantes.W9_NO_APLICA)){			
			if(fileTransferFormaW9.getRequesterNameAddress().length() > Constantes.LONGITUD_MAXIMA_REQUESTER_NAME_AND_ADDRESS){
				agregaMensajeError(fileTransferFormaW9,listaRegistrosErrorAux,Constantes.W9_REQUESTER_NAME_AND_ADDRESS,Constantes.MENSAJE_FORMATO_INCORRECTO);			
				return false;
			}
			cuentaConRequesterNameAddress = true;			
		}
		fileTransferFormaW9.setCuentaConRequesterNameAddress(cuentaConRequesterNameAddress);
		
		//list account numbers
		boolean cuentaConListAccountNumber = false;	
		if(!fileTransferFormaW9.getListAccountNumbers().equalsIgnoreCase(Constantes.W9_NO_APLICA)){				
				if(fileTransferFormaW9.getListAccountNumbers().length() > Constantes.LONGITUD_MAXIMA_LIST_ACCOUND_NUMBER){
					agregaMensajeError(fileTransferFormaW9,listaRegistrosErrorAux,Constantes.W9_LIST_ACCOUNT_NUMBER,Constantes.MENSAJE_FORMATO_INCORRECTO);			
					return false;
				}
				cuentaConListAccountNumber = true;				
		}
		fileTransferFormaW9.setCuentaConListAccountNumber(cuentaConListAccountNumber);
		
		//tomamos el id de la cuentaNombrada
//		CatBic catbic = getCatBicDao().getCatalogoCustodios().get(fileTransferFormaW9.getClaveCustodio());
		CatBic catBic = controlBeneficiariosService.findAbreviacionByCustodio(fileTransferFormaW9.getClaveCustodio());
//		CuentaNombrada cuentaNombrada = catbic.getCuentaNombrada();
		if(catBic != null){
			fileTransferFormaW9.setIdCuentaNombrada((catBic.getCuentaNombrada().getIdCuentaNombrada() != null) ? catBic.getCuentaNombrada().getIdCuentaNombrada() : null);
		}
			
		return true;
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