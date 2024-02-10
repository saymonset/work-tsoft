
package com.indeval.portalinternacional.middleware.services.validador;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.FormatoMILA;
import com.indeval.portalinternacional.middleware.servicios.modelo.PaisInt;
import com.indeval.portalinternacional.middleware.servicios.modelo.filetransfer.beneficiario.tipoformato.FileTransferFormaW8BEN2014;

/**
 * 
 * @author omarnl
 *
 */
public class ValidatorFormatMILAServiceImpl extends ValidatorFormatMILA  {

	/** Para el registro de log de esta clase */
	private static final Logger log = LoggerFactory.getLogger(ValidatorFormatMILAServiceImpl.class);
        
    
    private Map<String,Long> mapaCustodios = null;
    private Map<String,Long> mapaInstituciones = null;
    
	
	
	
	
	public boolean esRegistroFileTransferValido(FileTransferFormaW8BEN2014 fileTransferFormaW8BEN2014,List<FileTransferFormaW8BEN2014> listaRegistrosErrorAux){		
		return false;
	}
	
	/*
	 * 
	 */
	public boolean validaReglasNegocioBeneficiarioFiletransfer(FileTransferFormaW8BEN2014 fileTransferFormaW8BEN2014,List<FileTransferFormaW8BEN2014> listaRegistrosErrorAux,String idClaveInstitucion,boolean isIndeval){		
													
		return false;
			
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

	@Override
	public void validaFormatoW(Beneficiario beneficiario) {
		
		FormatoMILA formato = beneficiario.getFormatoMILA();
		
		getValidatorService().validaDTONoNulo(formato, " formato MILA ");
		
		getValidatorService().validaDTONoNulo(formato.getTipoDocumentoIndentidad(), " Tipo de documento de identidad ");
		getValidatorService().validaDTONoNulo(formato.getTipoDocumentoIndentidad().getIdTipoDocumento(), " Tipo de documento de identidad ");
		
		Long idTipoDocumento = formato.getTipoDocumentoIndentidad().getIdTipoDocumento();
		
		if(idTipoDocumento <= 0) {
			throw new BusinessException(getErrorResolver().getMessage("J0053", (Object)" Tipo de documento de identidad "));
		 }
		
		String identificadorMila = formato.getIdentificadorMILA();
		//Se valida el formato del codigo MILA en caso de que venga
		if(!StringUtils.isEmpty(identificadorMila)){
				Pattern pattern =  Pattern.compile("\\d{10}");
				Matcher matcher =  pattern.matcher(identificadorMila);
				if(identificadorMila.length() != 10 || !matcher.find() ) {
					throw new BusinessException( getErrorResolver().getMessage("J0115", new Object[] {}) );
				}
		}
		
		//Validaciones del tipo de documento de indetidad
		String numeroDocumentoIdentidad = formato.getNumeroDocumento();
		
		if(StringUtils.isEmpty(numeroDocumentoIdentidad)) {
			throw new BusinessException(getErrorResolver().getMessage("J0053", (Object)" El n\u00FAmero de documento de identidad "));
		}
		
		//Se valida el SSN
		if(idTipoDocumento==71){
				Pattern pattern =  Pattern.compile("\\d{3}-\\d{2}\\-\\d{4}");
				Matcher matcher =  pattern.matcher(numeroDocumentoIdentidad);
				if(numeroDocumentoIdentidad.length() != 11 || !matcher.find() ) {
					throw new BusinessException( getErrorResolver().getMessage("J0067", new Object[] {}) );
				}
		}
		//Se valida el EIN
		if(idTipoDocumento==73){
				Pattern pattern =  Pattern.compile("\\d{2}-\\d{7}");
				Matcher matcher =  pattern.matcher(numeroDocumentoIdentidad);
				if(numeroDocumentoIdentidad.length() != 10 || !matcher.find()  ) {
					throw new BusinessException( getErrorResolver().getMessage("J0068", new Object[] {}) );
				}
		}
		
		//Se Valida el RFC para personas morales
		if(idTipoDocumento==70){
			Pattern pattern =  Pattern.compile("[A-Z,Ñ,&]{3,4}[0-9]{2}[0-1][0-9][0-3][0-9][A-Z,0-9]?[A-Z,0-9]?[0-9,A-Z]?");
			Matcher matcher =  pattern.matcher(numeroDocumentoIdentidad);
			if(numeroDocumentoIdentidad.length() != 12 || !matcher.find()  ) {
				throw new BusinessException( getErrorResolver().getMessage("J0116", new Object[] {}) );
			}
		}
		
		//Se valida la credencial de elector IFE
		if(idTipoDocumento==69){
			if(numeroDocumentoIdentidad.length() != 18 ) {
				throw new BusinessException( getErrorResolver().getMessage("J0113", new Object[] {}) );
			}
		}
		
		//Se valida que si se seleciona un tipo de documento Otros, se especifique que tipo de documento es
		//en el campo nombreDocumento
		if(idTipoDocumento.equals(41L) 
				||idTipoDocumento.equals(43L)
				||idTipoDocumento.equals(50L)
				||idTipoDocumento.equals(60L)
				||idTipoDocumento.equals(65L)
				||idTipoDocumento.equals(67L)
				||idTipoDocumento.equals(72L)
				||idTipoDocumento.equals(74L)
				||idTipoDocumento.equals(77L)
				||idTipoDocumento.equals(79L)){
			getValidatorService().validaDTONoNulo(formato.getNombreDocumento(), " El nombre del documento de identidad ");
			if(StringUtils.isBlank(formato.getNombreDocumento())){
				throw new BusinessException(getErrorResolver().getMessage("J0053", (Object)" El nombre del documento de identidad "));
			}
		}
		
		
		//Validaciones por tipo de persona
		if(beneficiario.getPersonaFisica()){
			//Se Valida el RFC para personas fisicas con o sin homoclavea pero solo para mexicanos
			if(PaisInt.ID_PAIS_MEXICO == formato.getPaisNacionalidad().getIdPais()){
				String rfc = formato.getRfc();
				Pattern pattern =  Pattern.compile("[A-Z,Ñ,&]{3,4}[0-9]{2}[0-1][0-9][0-3][0-9][A-Z,0-9]?[A-Z,0-9]?[0-9,A-Z]?");
				Matcher matcher =  pattern.matcher(rfc);
				if(StringUtils.isEmpty(rfc) || !(rfc.length()==10 || rfc.length()==13) || !matcher.find()  ) {
					throw new BusinessException( getErrorResolver().getMessage("J0112", new Object[] {}) );
				}
			}
		}
		else{
			getValidatorService().validaDTONoNulo(formato.getSectorEconomico(), " Sector econ\u00F3mico ");
			getValidatorService().validaDTONoNulo(formato.getSectorEconomico().getIdSectorEconomico(), " Sector econ\u00F3mico ");
			
			getValidatorService().validaDTONoNulo(formato.getCaracterEntidad(), " Car\u00E1cter de la entidad ");
			getValidatorService().validaDTONoNulo(formato.getCaracterEntidad().getIdTipoEmpresa(), " Car\u00E1cter de la entidad ");

		}
		
		getValidatorService().validaDTONoNulo(formato.getPaisDireccion(), " Pais del Domicilio MILA ");
		getValidatorService().validaDTONoNulo(formato.getPaisDireccion().getIdPais(), " Pais del Domicilio MILA ");
		if(PaisInt.ID_PAIS_MEXICO == formato.getPaisDireccion().getIdPais()){
			getValidatorService().validaDTONoNulo(formato.getCodigoEstadoEntidad(), " Estado del Domicilio MILA ");
			getValidatorService().validaDTONoNulo(formato.getCodigoEstadoEntidad().getIdCodigoEstado(), " Estado del Domicilio MILA ");
		}
		
		if(formato.getArchivos()==null || (formato.getArchivos().getComprobanteUno() == null && formato.getArchivos().getComprobanteDos() == null)){
			throw new BusinessException( getErrorResolver().getMessage("J0114", new Object[] {}) );
		}
	}
}
