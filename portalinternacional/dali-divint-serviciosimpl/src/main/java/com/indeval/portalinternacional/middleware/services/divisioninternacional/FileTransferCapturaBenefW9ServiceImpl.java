package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import static com.indeval.portalinternacional.middleware.services.util.ExcelUtils.getColumnAsString;
import static com.indeval.portalinternacional.middleware.services.util.ExcelUtils.transformaCadena;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.Domicilio;
import com.indeval.portalinternacional.middleware.servicios.modelo.ExemptPayeeW9;
import com.indeval.portalinternacional.middleware.servicios.modelo.ExemptionFatcaW9;
import com.indeval.portalinternacional.middleware.servicios.modelo.Field3W9;
import com.indeval.portalinternacional.middleware.servicios.modelo.FormatoW9;
import com.indeval.portalinternacional.middleware.servicios.modelo.TipoBeneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.filetransfer.beneficiario.tipoformato.FileTransferFormaW9;

public class FileTransferCapturaBenefW9ServiceImpl extends FileTransferCapturaBenefServiceImpl<FileTransferFormaW9> {

	
	/** Log de clase. */
	private static final Logger log = LoggerFactory.getLogger(FileTransferCapturaBenefW9ServiceImpl.class);
    
    
	

	

	/*
	 * (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.FileTransferCapturaBenefService#guardaBeneficiarios(java.lang.String, java.lang.Long)
	 */
	public int guardaBeneficiarios(String claveUsuario,Long tipoFormato)throws BusinessException{
		log.info("Entrando a FileTransferCapturaBenefServiceImpl.guardaBeneficiarios()");
		Boolean cuentaConRegistrosCorrectos = Boolean.FALSE;
		List<FileTransferFormaW9> listaRegistrosCorrectos = null;
		int numeroBeneficiariosGuardados = 0;
		listaRegistrosCorrectos =  consultaRegistrosCorrectos(claveUsuario);
		Beneficiario beneficiario = null;
		if(tipoFormato == Constantes.FORMATO_W9){
			if(listaRegistrosCorrectos != null && !listaRegistrosCorrectos.isEmpty()){
				cuentaConRegistrosCorrectos = Boolean.TRUE;
				for(FileTransferFormaW9 fileTransferFormaW9: listaRegistrosCorrectos){
					Long idInstitucion = fileTransferFormaW9.getIdInstitucion();
					beneficiario = convierteBeneficiario(fileTransferFormaW9 );					
					getControlBeneficiariosService().insertaBeneficiario(beneficiario,idInstitucion);									
					numeroBeneficiariosGuardados++;
				}
			}
			eliminaCargaExistente(claveUsuario,cuentaConRegistrosCorrectos);
		}				
		return numeroBeneficiariosGuardados;
	}	
	
	
	
	



	/**
	 * m&eacute;todo para convertir un HSSFRow a un FileTransferFormaW9
	 * @param  HSSFRow
	 * @return FileTransferFormaW9	 
	 * */
	
	public FileTransferFormaW9 convertRowToFileTransferForma(HSSFRow row){
		FileTransferFormaW9 fileTransferFormaW9 = null;
		if(row != null){
			fileTransferFormaW9 = new FileTransferFormaW9();
			String tipoFormato = getColumnAsString(row.getCell(0));
			fileTransferFormaW9.setTipoFormato(transformaCadena(tipoFormato));
			
			String tipoBeneficiario = getColumnAsString(row.getCell(1));
			fileTransferFormaW9.setTipoBeneficiario(transformaCadena(tipoBeneficiario));
			
			String custodio = getColumnAsString(row.getCell(2));
			fileTransferFormaW9.setClaveCustodio(transformaCadena(custodio));
			
			String institucion = getColumnAsString(row.getCell(3));
			fileTransferFormaW9.setClaveInstitucion(transformaCadena(institucion));
			
			String fechaFormato = getColumnAsString(row.getCell(4));
			fileTransferFormaW9.setFechaFormato(transformaCadena(fechaFormato));
			
			String estado = getColumnAsString(row.getCell(5));
			fileTransferFormaW9.setEstado(transformaCadena(estado));
			
			String nombre = getColumnAsString(row.getCell(6));
			fileTransferFormaW9.setRazonSocial(transformaCadena(nombre));
			
			String businessName = getColumnAsString(row.getCell(7));
			fileTransferFormaW9.setBussinesName(transformaCadena(businessName));
			
			String clasification = getColumnAsString(row.getCell(8));
			fileTransferFormaW9.setTipoTaxPayer(transformaCadena(clasification));
			
			String exemptPayeeCode = getColumnAsString(row.getCell(9));
			fileTransferFormaW9.setExemptPayeeCode(transformaCadena(exemptPayeeCode));
			
			String fatcaCode = getColumnAsString(row.getCell(10));
			fileTransferFormaW9.setFatcaReportingCode(transformaCadena(fatcaCode));
			
			String street = getColumnAsString(row.getCell(11));
			fileTransferFormaW9.setStreet(transformaCadena(street));
			
			String outerNumber = getColumnAsString(row.getCell(12));
			fileTransferFormaW9.setOuterNumber(transformaCadena(outerNumber));
			
			String interiorNumber = getColumnAsString(row.getCell(13));
			fileTransferFormaW9.setInteriorNumber(transformaCadena(interiorNumber));
			
			String zipCode = getColumnAsString(row.getCell(14));
			fileTransferFormaW9.setZipCode(transformaCadena(zipCode));
			
			String city = getColumnAsString(row.getCell(15));
			fileTransferFormaW9.setCity(transformaCadena(city));
			
			String state = getColumnAsString(row.getCell(16));
			fileTransferFormaW9.setState(transformaCadena(state));
			
			String requestersNameAndAddress = getColumnAsString(row.getCell(17));
			fileTransferFormaW9.setRequesterNameAddress(transformaCadena(requestersNameAndAddress));
			
			String listAccountNumber = getColumnAsString(row.getCell(18));
			fileTransferFormaW9.setListAccountNumbers(transformaCadena(listAccountNumber));
			
			String socialSecurityNumber = getColumnAsString(row.getCell(19));
			fileTransferFormaW9.setSsn(transformaCadena(socialSecurityNumber));
			
			String employerIdentificationNumber = getColumnAsString(row.getCell(20));
			fileTransferFormaW9.setEmployerIdNumb(transformaCadena(employerIdentificationNumber));
						
		}
		return fileTransferFormaW9;
	}
	
	/**
	 * m&eacute;todo para formar un Beneficiario tomando datos del FileTransferFormaW9 
	 * */
	public Beneficiario convierteBeneficiario(FileTransferFormaW9 fileTransferFormaW9 ){
		log.info("Entrando a FileTransferCapturaBenefW9ServiceImpl.convierteBeneficiario()");		
		String tipoFormato = fileTransferFormaW9.getTipoFormato();
		
		//datos comunes
		Beneficiario beneficiario = new Beneficiario();
		beneficiario.setRazonSocial(fileTransferFormaW9.getRazonSocial());
		beneficiario.setFechaFormato(fileTransferFormaW9.getFechaFormatoDate());
		beneficiario.setIdCuentaNombrada(fileTransferFormaW9.getIdCuentaNombrada());
		beneficiario.setActivo(fileTransferFormaW9.getActivo());
		beneficiario.setPersonaFisica(Boolean.FALSE);
		beneficiario.setTipoFormato(tipoFormato);
		beneficiario.setPaisIncorporacion("USA");				
		//tipo Beneficiario
		TipoBeneficiario tipoBeneficiario = new TipoBeneficiario();
		
		//formato W9	
		FormatoW9 formatoW9 = new FormatoW9();
		Field3W9 field3W9 = new Field3W9();
		//personas fisicas
		if(fileTransferFormaW9.getPersonaFisica()){
			tipoBeneficiario.setIdTipoBeneficiario(new Long(4));		
			formatoW9.setExemptPayee(false);					
			formatoW9.setSsn(fileTransferFormaW9.getSsn());				
		}else{
			//personas morales
			formatoW9.setExemptPayee(true);
			tipoBeneficiario.setIdTipoBeneficiario(new Long(11));
			formatoW9.setEmployerIdNumb(fileTransferFormaW9.getEmployerIdNumb());
			if(fileTransferFormaW9.getCuentaConBusinessName()){
				formatoW9.setBusinessName(fileTransferFormaW9.getBussinesName());
			}
			formatoW9.setTaxClassification(fileTransferFormaW9.getTaxClassification());									
			formatoW9.setOtherDescription(fileTransferFormaW9.getDescripcionTaxPayer());
				
			ExemptionFatcaW9 exemptionFatcaW9 = new ExemptionFatcaW9();
			exemptionFatcaW9.setIdExemptionFatcaW9(fileTransferFormaW9.getIdFatcaCode());
			formatoW9.setExemptionFatcaW9(exemptionFatcaW9);
			
			ExemptPayeeW9 exemptPayeeW9 = new ExemptPayeeW9();
			exemptPayeeW9.setIdExemptPayeeW9(fileTransferFormaW9.getIdExemptPayee());
			formatoW9.setExemptPayeeW9(exemptPayeeW9);												
		}						
			
		field3W9.setIdCampo(fileTransferFormaW9.getIdTaxPayer());
		formatoW9.setTypeTaxPayer(field3W9);
			
		//preguntamos por los campos opcionales
		if(fileTransferFormaW9.getCuentaConListAccountNumber()){
			formatoW9.setListAccountNumbers(fileTransferFormaW9.getListAccountNumbers());
		}
		if(fileTransferFormaW9.getCuentaConRequesterNameAddress()){
			formatoW9.setRequesterNameAddress(fileTransferFormaW9.getRequesterNameAddress());
		}							
		beneficiario.setFormatoW9(formatoW9);
		beneficiario.setTipoBeneficiario(tipoBeneficiario);											
		//domicilio
		Domicilio domicilio = new Domicilio();
		domicilio.setStreet(fileTransferFormaW9.getStreet());
		domicilio.setOuterNumber(fileTransferFormaW9.getOuterNumber());
		domicilio.setPostalCode(fileTransferFormaW9.getZipCode());
		domicilio.setCityTown(fileTransferFormaW9.getCity());			
		domicilio.setStateProvince(fileTransferFormaW9.getState());
		if(fileTransferFormaW9.getCuentaConNumeroInterior()){
			domicilio.setInteriorNumber(fileTransferFormaW9.getInteriorNumber());				
		}
		beneficiario.setDomicilioW9(domicilio);															
		return beneficiario;		
	}
	

	


		

}
