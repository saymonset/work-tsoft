package com.indeval.portalinternacional.middleware.services.divisioninternacional.beneficiarios;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.modelo.Institucion;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.FormatoW8Service;
import com.indeval.portalinternacional.middleware.servicios.constantes.BeneficiariosConstantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.CatBic;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaBeneficiariosParam;
import com.indeval.portalinternacional.persistence.dao.CatBicDao;

public class ExportacionBeneficiariosServiceImpl implements ExportacionBeneficiariosService {
	
	/** Servicio de beneficiarios */
	private ControlBeneficiariosService controlBeneficiariosService;
	
	/** Dao para la consulta de CatBic */
	private CatBicDao catBicDao;
	
    /** Servicio para parsear el formato a xml y viceversa */
    private FormatoW8Service formatoW8Service;
	
    /** Mapa que contiene la lista de custodios */
    private Map<Long, String> mapaCustodios;
	
    /*
     * (non-Javadoc)
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.beneficiarios.ExportacionBeneficiariosService#obtenerRegistrosLayout(java.lang.String, java.sql.Date, java.sql.Date)
     */
	@SuppressWarnings("unchecked")
	public List<String> obtenerRegistrosLayauts(String formato, Date fechaInicio, Date fechaFin) {
		//Inicializa el mapa
    	iniciliazarMapaCustodios();
		//Lista con los registros procesados
		List<String> registrosProcesados = null;
		//Objeto donde regresa los resultados de la consulta
		PaginaVO paginaVO;
		//Parametros para la busqueda
		ConsultaBeneficiariosParam param = new ConsultaBeneficiariosParam();
		param.setStatusBenef(2L);
		if(null != fechaInicio) {			
			param.setFechaFormatoInicio(fechaInicio);
		}
		if(null != fechaFin) {			
			param.setFechaFormatoFin(fechaFin);
		}
		
		//W8-BEN
		if (BeneficiariosConstantes.FORMATO_W8_BEN.equals(formato)) {
			//Coloca el formato
			param.setFormato(BeneficiariosConstantes.FORMATO_W8_BEN);
			//Ejecuta la consulta
			paginaVO = controlBeneficiariosService.consultaBeneficiarios(param, null, true);
			registrosProcesados = procesarBeneficiariosW8BEN((List<Beneficiario>) paginaVO.getRegistros());
		}
		else if (BeneficiariosConstantes.FORMATO_W8_BEN_E.equals(formato)) {
			//Coloca el formato
			param.setFormato(BeneficiariosConstantes.FORMATO_W8_BEN_E);
			//Ejecuta la consulta
			paginaVO = controlBeneficiariosService.consultaBeneficiarios(param, null, true);
			registrosProcesados = 
				ExportacionW8BENE.procesarBeneficiariosW8BENE(
					(List<Beneficiario>) paginaVO.getRegistros(), mapaCustodios, formatoW8Service);
        }
		else if (BeneficiariosConstantes.FORMATO_W8_IMY.equals(formato)) {
			//Coloca el formato
			param.setFormato(BeneficiariosConstantes.FORMATO_W8_IMY);
			//Ejecuta la consulta
			paginaVO = controlBeneficiariosService.consultaBeneficiarios(param, null, true);
			registrosProcesados = ExportacionW8IMY.procesarBeneficiariosW8IMY((List<Beneficiario>) paginaVO.getRegistros(), mapaCustodios, formatoW8Service);
        }
		else if (BeneficiariosConstantes.FORMATO_W9.equals(formato)) {
			//Coloca el formato
			param.setFormato(BeneficiariosConstantes.FORMATO_W9);
			//Ejecuta la consulta
			paginaVO = controlBeneficiariosService.consultaBeneficiarios(param, null, true);
			registrosProcesados = procesarBeneficiariosW9((List<Beneficiario>) paginaVO.getRegistros());
        }
		return registrosProcesados;
	}
	
	/**
	 * Inicializa el mapa de custodios
	 * @throws BusinessException En caso de ocurrir un error.
	 */
	private void iniciliazarMapaCustodios() throws BusinessException {
		if(null == this.mapaCustodios) {
            this.mapaCustodios = new HashMap<Long, String>();
            List<CatBic> listaCustodios = catBicDao.findCatBic();
            for (CatBic catBic : listaCustodios) {
            	this.mapaCustodios.put(catBic.getCuentaNombrada().getIdCuentaNombrada(), catBic.getAbreviacionCustodio());
			}
    	}
	}

	/**
	 * Metodo que procesa la lista de beneficiarios para obtener los datos a exportar.
	 * @param beneficiarios Lista con los beneficiarios resultado de la busqueda.
	 * @return Lista 
	 */
	private List<String> procesarBeneficiariosW8BEN(List<Beneficiario> beneficiarios) {
		ArrayList<String> registrosProcesados = new ArrayList<>();
		
		//StringBuilder para cada registro
		StringBuilder registroCVS = new StringBuilder();
		//Cadenas auxiliares
		String us_tin;
		String foreign_tax_identifying;
		String date_of_birth;
		String treaty_country_cd;
		String income_type;
		String treaty_explanation;
		String treaty_provision;
		String treaty_rate;
		String reference_number;
		String print_name;
		String capacity;
		//Recorremos la lista de beneficiarios
		for (Beneficiario beneficiario : beneficiarios) {
			//Valida que el origen del beneficiario sea Dali
			if(StringUtils.isNotBlank(beneficiario.getOrigen()) && 
				BeneficiariosConstantes.ORIGEN_BENEFICIARIO_DALI.equals(beneficiario.getOrigen())) {
				registroCVS = new StringBuilder();
				//Account Number
				registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
				//Client Id - UOI
				registroCVS.append(beneficiario.getUoiNumber().replace(",", StringUtils.EMPTY) + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
				//NAME1
				registroCVS.append(beneficiario.getNombres().replace(",", StringUtils.EMPTY) + BeneficiariosConstantes.CADENA_ESPACIO_BLANCO +
					beneficiario.getApellidoPaterno().replace(",", StringUtils.EMPTY) + BeneficiariosConstantes.CADENA_ESPACIO_BLANCO +
					beneficiario.getApellidoMaterno().replace(",", StringUtils.EMPTY) + BeneficiariosConstantes.CADENA_ESPACIO_BLANCO +
					BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
				//ORG_COUNTRY_CD
				registroCVS.append(beneficiario.getPaisIncorporacion().replace(",", StringUtils.EMPTY) + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
				//PR_ADDRESS
				registroCVS.append(beneficiario.getDomicilioW8Normal().getCalleNumeros().replace(",", StringUtils.EMPTY) + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
				//PR_CITY
				registroCVS.append(beneficiario.getDomicilioW8Normal().getCityTown().replace(",", StringUtils.EMPTY) + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
				//PR_COUNTRY_CD
				registroCVS.append(beneficiario.getDomicilioW8Normal().getCountry().replace(",", StringUtils.EMPTY) + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
				//PR_INCAREOF
				registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
				//PR_POBOX
				registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
				//PR_STATE_PROV
				registroCVS.append(beneficiario.getDomicilioW8Normal().getStateProvince().replace(",", StringUtils.EMPTY) + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
				//PR_ZIP
				registroCVS.append(beneficiario.getDomicilioW8Normal().getPostalCode().replace(",", StringUtils.EMPTY) + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
				
				if(null != beneficiario.getDomicilioW8Correo()) {
					//MAILING_ADDRESS
					registroCVS.append(beneficiario.getDomicilioW8Correo().getCalleNumeros().replace(",", StringUtils.EMPTY)  + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//MAIL_CITY
					registroCVS.append(beneficiario.getDomicilioW8Correo().getCityTown().replace(",", StringUtils.EMPTY) + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//MAIL_COUNTRY_CD
					registroCVS.append(beneficiario.getDomicilioW8Correo().getCountry().replace(",", StringUtils.EMPTY) + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//MAIL_STATE_PROV
					registroCVS.append(beneficiario.getDomicilioW8Correo().getStateProvince().replace(",", StringUtils.EMPTY) + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//MAIL_ZIP
					registroCVS.append(beneficiario.getDomicilioW8Correo().getPostalCode().replace(",", StringUtils.EMPTY) + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
				}
				else {
					//MAILING_ADDRESS
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//MAIL_CITY
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//MAIL_COUNTRY_CD
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//MAIL_STATE_PROV
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//MAIL_ZIP
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
				}
				
				if(null != beneficiario.getFormatoW8BEN()) {
					//US_TIN
					us_tin = (StringUtils.isNotBlank(beneficiario.getFormatoW8BEN().getTaxpayerIdNumb())) ?
							beneficiario.getFormatoW8BEN().getTaxpayerIdNumb().replace(",", StringUtils.EMPTY) : 
								StringUtils.EMPTY;
					registroCVS.append(us_tin + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//FOREIGN_TAX_IDENTIFYING
					foreign_tax_identifying = (StringUtils.isNotBlank(beneficiario.getFormatoW8BEN().getForeignTaxIdNumb())) ?
							beneficiario.getFormatoW8BEN().getForeignTaxIdNumb().replace(",", StringUtils.EMPTY) :
								StringUtils.EMPTY;
					registroCVS.append(foreign_tax_identifying + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//NOFTIN
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//DATE_OF_BIRTH
					date_of_birth = (null != beneficiario.getFormatoW8BEN().getFechaNacimiento()) ?
							DateFormatUtils.format(beneficiario.getFormatoW8BEN().getFechaNacimiento(), "MM/dd/yyyy") :
								StringUtils.EMPTY;
					registroCVS.append(date_of_birth + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//TREATY_COUNTRY_CD
					treaty_country_cd = (StringUtils.isNotBlank(beneficiario.getFormatoW8BEN().getField9OptionACountry())) ?
							beneficiario.getFormatoW8BEN().getField9OptionACountry().replace(",", StringUtils.EMPTY) :
								StringUtils.EMPTY;
					registroCVS.append(treaty_country_cd + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//INCOME_TYPE
					income_type = (StringUtils.isNotBlank(beneficiario.getFormatoW8BEN().getField10Type())) ?
							beneficiario.getFormatoW8BEN().getField10Type().replace(",", StringUtils.EMPTY) :
								StringUtils.EMPTY;
					registroCVS.append(income_type + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//TREATY_EXPLANATION
					treaty_explanation = (StringUtils.isNotBlank(beneficiario.getFormatoW8BEN().getField10Reasons())) ?
							beneficiario.getFormatoW8BEN().getField10Reasons().replace(",", StringUtils.EMPTY) :
								StringUtils.EMPTY;
					registroCVS.append(treaty_explanation + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//TREATY_PROVISION
					treaty_provision = (StringUtils.isNotBlank(beneficiario.getFormatoW8BEN().getField10Article())) ?
							beneficiario.getFormatoW8BEN().getField10Article().replace(",", StringUtils.EMPTY) :
								StringUtils.EMPTY;
					registroCVS.append(treaty_provision + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//TREATY_RATE
					treaty_rate = (StringUtils.isNotBlank(beneficiario.getFormatoW8BEN().getField10Rate())) ?
							beneficiario.getFormatoW8BEN().getField10Rate().replace(",", StringUtils.EMPTY) :
								StringUtils.EMPTY;
					registroCVS.append(treaty_rate + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//REFERENCE_NUMBER
					reference_number = (StringUtils.isNotBlank(beneficiario.getFormatoW8BEN().getReferenceNumbers())) ?
							beneficiario.getFormatoW8BEN().getReferenceNumbers().replace(",", StringUtils.EMPTY) :
								StringUtils.EMPTY;
					registroCVS.append(reference_number + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//SIGNATURE
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//PRINT_NAME
					print_name = (StringUtils.isNotBlank(beneficiario.getFormatoW8BEN().getSigner())) ?
							beneficiario.getFormatoW8BEN().getSigner().replace(",", StringUtils.EMPTY) :
								StringUtils.EMPTY;
					registroCVS.append(print_name + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
					//CAPACITY
					capacity = (StringUtils.isNotBlank(beneficiario.getFormatoW8BEN().getCapacityActing())) ?
							beneficiario.getFormatoW8BEN().getCapacityActing().replace(",", StringUtils.EMPTY) :
								StringUtils.EMPTY;
					registroCVS.append(capacity + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
				}
				
				//DATE_SIGNED
				registroCVS.append((null != beneficiario.getFechaFormato()) ?
					DateFormatUtils.format(beneficiario.getFechaFormato(), "MM/dd/yyyy")  + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS :
						StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
				//BO_IND
				registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
				
				//Institucion
				Set<Institucion> instituciones = beneficiario.getInstitucion();
				if(null != instituciones && !instituciones.isEmpty()) {
					Institucion i = instituciones.iterator().next();
					registroCVS.append(i.getTipoInstitucion().getClaveTipoInstitucion() + i.getFolioInstitucion() + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
				}
				else {				
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
				}
				//Custodio
				registroCVS.append(mapaCustodios.get(beneficiario.getIdCuentaNombrada()).replace(",", StringUtils.EMPTY) + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
				//U.O.I.
				registroCVS.append(beneficiario.getUoiNumber().replace(",", StringUtils.EMPTY) + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
				//ADP
				registroCVS.append(((StringUtils.isNotBlank(beneficiario.getAdp())) ? 
						beneficiario.getAdp().replace(",", StringUtils.EMPTY) : StringUtils.EMPTY) + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
				
				//Agrega a registros procesados
				registrosProcesados.add(registroCVS.toString());
				//Reinicia los objetos auxiliares
				registroCVS = null;
				us_tin = null;
				foreign_tax_identifying = null;
				date_of_birth = null;
				treaty_country_cd = null;
				income_type = null;
				treaty_explanation = null;
				treaty_provision = null;
				treaty_rate = null;
				reference_number = null;
				print_name = null;
				capacity = null;
			}
		}
		return registrosProcesados;
	}

	/**
	 * Metodo que procesa la lista de beneficiarios para obtener los datos a exportar.
	 * @param beneficiarios Lista con los beneficiarios resultado de la busqueda.
	 * @return Lista
	 */
	private List<String> procesarBeneficiariosW9(List<Beneficiario> beneficiarios) {
		ArrayList<String> registrosProcesados = new ArrayList<>();
		
		//StringBuilder para cada registro
		StringBuilder registroCVS = new StringBuilder();
		
		for (Beneficiario beneficiario : beneficiarios) {
			//Valida que el origen del beneficiario sea Dali
			if(StringUtils.isNotBlank(beneficiario.getOrigen()) && 
				BeneficiariosConstantes.ORIGEN_BENEFICIARIO_DALI.equals(beneficiario.getOrigen())) {
				registroCVS = new StringBuilder();
				//Account Number
				registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
				//Client Id - UOI
				registroCVS.append(beneficiario.getUoiNumber().replace(",", StringUtils.EMPTY) + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
				//NAME
				registroCVS.append((StringUtils.isNotBlank(beneficiario.getRazonSocial()) ? 
					beneficiario.getRazonSocial().replace(",", StringUtils.EMPTY) : 
						StringUtils.EMPTY) + 
							BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
				//DE_NAME
				registroCVS.append((StringUtils.isNotBlank(beneficiario.getFormatoW9().getBusinessName()) ? 
					beneficiario.getFormatoW9().getBusinessName().replace(",", StringUtils.EMPTY): 
						StringUtils.EMPTY) + 
							BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
				//FEDERAL_TAX_CLASSIFICATION
				StringBuilder federal_tax_classification = new StringBuilder();
				federal_tax_classification.append(beneficiario.getFormatoW9().getTypeTaxPayer().getDescripcion().replace(",", StringUtils.EMPTY));
				if(Long.valueOf(4).equals( beneficiario.getFormatoW9().getTypeTaxPayer().getIdCampo())) {
					federal_tax_classification.append("-");
					federal_tax_classification.append(beneficiario.getFormatoW9().getTaxClassification().replace(",", StringUtils.EMPTY));
				}
				else if(Long.valueOf(5).equals( beneficiario.getFormatoW9().getTypeTaxPayer().getIdCampo())) {
					federal_tax_classification.append("-");
					federal_tax_classification.append(beneficiario.getFormatoW9().getOtherDescription().replace(",", StringUtils.EMPTY));
				}
				registroCVS.append(federal_tax_classification + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
				//EXEMPT_PAYEE_CODE
				registroCVS.append((null != beneficiario.getFormatoW9().getExemptPayeeW9() &&
					StringUtils.isNotBlank(beneficiario.getFormatoW9().getExemptPayeeW9().getFatcaCode()) ?
					beneficiario.getFormatoW9().getExemptPayeeW9().getFatcaCode().replace(",", StringUtils.EMPTY) : 
						StringUtils.EMPTY) + 
							BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
				//EXEMPT_FATCA_REPORTING_CODE
				registroCVS.append((null != beneficiario.getFormatoW9().getExemptionFatcaW9() &&
					StringUtils.isNotBlank(beneficiario.getFormatoW9().getExemptionFatcaW9().getFatcaCode()) ?
					beneficiario.getFormatoW9().getExemptionFatcaW9().getFatcaCode().replace(",", StringUtils.EMPTY) : 
						StringUtils.EMPTY) + 
							BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
				//PR_ADDRESS
				registroCVS.append(beneficiario.getDomicilioW9().getCalleNumeros().replace(",", StringUtils.EMPTY) + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
				//PR_CITY
				registroCVS.append(beneficiario.getDomicilioW9().getCityTown().replace(",", StringUtils.EMPTY) + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
				//PR_STATE_PROV
				registroCVS.append(beneficiario.getDomicilioW9().getStateProvince().replace(",", StringUtils.EMPTY) + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
				//PR_ZIP
				registroCVS.append(beneficiario.getDomicilioW9().getPostalCode().replace(",", StringUtils.EMPTY) + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
				//PR_COUNTRY
				registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
				//REFERENCE_NUMBER
				registroCVS.append((StringUtils.isNotBlank(beneficiario.getFormatoW9().getListAccountNumbers()) ? 
					beneficiario.getFormatoW9().getListAccountNumbers().replace(",", StringUtils.EMPTY) : 
						StringUtils.EMPTY) + 
							BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
				//SSN
				registroCVS.append((StringUtils.isNotBlank(beneficiario.getFormatoW9().getSsn()) ? 
					beneficiario.getFormatoW9().getSsn().replace(",", StringUtils.EMPTY) : 
						StringUtils.EMPTY) + 
							BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
				//EIN
				registroCVS.append((StringUtils.isNotBlank(beneficiario.getFormatoW9().getEmployerIdNumb()) ? 
					beneficiario.getFormatoW9().getEmployerIdNumb().replace(",", StringUtils.EMPTY) : 
						StringUtils.EMPTY) + 
							BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
				//SIGNATURE
				registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
				//DATE_SIGNED
				registroCVS.append(((null != beneficiario.getFechaFormato()) ?
					DateFormatUtils.format(beneficiario.getFechaFormato(), "MM/dd/yyyy") :
						StringUtils.EMPTY) + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
				//Institucion
				Set<Institucion> instituciones = beneficiario.getInstitucion();
				if(null != instituciones && !instituciones.isEmpty()) {
					Institucion i = instituciones.iterator().next();
					registroCVS.append(i.getTipoInstitucion().getClaveTipoInstitucion() + i.getFolioInstitucion() + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
				}
				else {				
					registroCVS.append(StringUtils.EMPTY + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
				}
				//Custodio
				registroCVS.append(mapaCustodios.get(beneficiario.getIdCuentaNombrada()).replace(",", StringUtils.EMPTY) + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
				//U.O.I.
				registroCVS.append(beneficiario.getUoiNumber().replace(",", StringUtils.EMPTY) + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
				//ADP
				registroCVS.append(((StringUtils.isNotBlank(beneficiario.getAdp())) ? beneficiario.getAdp().replace(",", StringUtils.EMPTY) : StringUtils.EMPTY) + BeneficiariosConstantes.SEPARADOR_COLUMNAS_CVS);
				//Agrega a registros procesados
				registrosProcesados.add(registroCVS.toString());
			}
		}
		
		return registrosProcesados;
	}

	/**
	 * Metodo para establecer el atributo controlBeneficiariosService
	 * @param controlBeneficiariosService El valor del atributo controlBeneficiariosService a establecer.
	 */
	public void setControlBeneficiariosService(ControlBeneficiariosService controlBeneficiariosService) {
		this.controlBeneficiariosService = controlBeneficiariosService;
	}

	/**
	 * Metodo para establecer el atributo formatoW8Service
	 * @param formatoW8Service El valor del atributo formatoW8Service a establecer.
	 */
	public void setFormatoW8Service(FormatoW8Service formatoW8Service) {
		this.formatoW8Service = formatoW8Service;
	}

	/**
	 * Metodo para establecer el atributo catBicDao
	 * @param catBicDao El valor del atributo catBicDao a establecer.
	 */
	public void setCatBicDao(CatBicDao catBicDao) {
		this.catBicDao = catBicDao;
	}

}