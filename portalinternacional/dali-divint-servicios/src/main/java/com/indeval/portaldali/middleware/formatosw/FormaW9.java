/**
 * 
 */
package com.indeval.portaldali.middleware.formatosw;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.model.SelectItem;

import org.apache.commons.lang.StringUtils;

import com.indeval.portalinternacional.middleware.services.util.HabilitaCamposFormatosBeneficiarios;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.Domicilio;
import com.indeval.portalinternacional.middleware.servicios.modelo.ExemptPayeeW9;
import com.indeval.portalinternacional.middleware.servicios.modelo.ExemptionFatcaW9;
import com.indeval.portalinternacional.middleware.servicios.modelo.Field3W9;
import com.indeval.portalinternacional.middleware.servicios.modelo.FormatoW9;
import com.indeval.portalinternacional.middleware.servicios.vo.CamposFormatoW9;

/**
 * Clase para la forma W8BEN
 * 
 * @author Rafael Ibarra
 */
/**
 * @author ribarra
 *
 */
/**
 * @author ribarra
 *
 */
@SuppressWarnings("unchecked")
public class FormaW9 extends FormaGeneral implements Constantes {
	
	
	/** Lista de Items de Tax Payer */
	private List lstTipoTaxPayer;
	/**  Tax Payer */
	private Long tipoTaxPayer;
	/** Descripcion del Tax Payer */
	private String descripcionTipoTaxPayer;
	/** Bussines Name */
	private String bussinesName;
	/** Tax Classification */
	private String taxClassification;
	/** Other Description */
	private String otherDescription;
	/** Exempt Payee */
	private Boolean exemptPayee;
	/** Indica la calle de la direccion residencial */
	private String calleResidencial;
	/** Indica el numero exterior de la direccion residencial */
	private String numeroExteriorResidencial;
	/** Indica el numero interior de la direccion residencial */
	private String numeroInteriorResidencial;
	/** Indica el codigo postal de la direccion residencial */
	private String codigoPostalResidencial;
	/** Indica la ciudad o poblado de la direccion residencial */
	private String cityTownResidencial;
	/** Indica el estado de la direccion residencial */
	private String stateProvinceResidencial;
	/** Requester Name Address */
	private String requesterNameAddress;
	/** List Account Numbers */
	private String listAccountNumbers;
	/** SSN */
	private String ssn;
	/** Employer Id Numb */
	private String employerIdNumb;
	/** Campois bloqueados W9 */
	private CamposFormatoW9 campos = new CamposFormatoW9();
	
	private ExemptPayeeW9 exemptPayeeW9;
	
	private ExemptionFatcaW9 exemptionFatcaW9;
	
	public FormaW9() {
		super();
	}
	
	public FormaW9( Long idCuentaNombrada, Long idTipoBeneficiario, Long idInstitucion, List<Field3W9> listField3 ) {
		super(idCuentaNombrada, idTipoBeneficiario, idInstitucion);
		campos = HabilitaCamposFormatosBeneficiarios.definirCamposBloqueadosW9(idCuentaNombrada, idTipoBeneficiario);
		
		lstTipoTaxPayer = new ArrayList<SelectItem>();
		
		if( listField3 != null && listField3.size() > 0 ) {
			Map<Long, Boolean> mapaBloqueosCampo3 = campos.getField3();
			for(Field3W9 campo3 : listField3) {
				lstTipoTaxPayer.add(new SelectItem(campo3.getIdCampo(),campo3.getDescripcion(),campo3.getDescripcion(),mapaBloqueosCampo3.get(campo3.getIdCampo())));
			}
		}
		
		construyeReglas();
	}
	
	private void construyeReglas() {
		Long idCuentaNombrada = getCustodio();
		Long idTipoBeneficiario = getIdTipoBeneficiario();
		setPersonaFisica(false);
		// Definimos los valores por default
		if (idTipoBeneficiario == PERSONA_FISICA_EUA) {
			tipoTaxPayer = 1l;
		} else if (idTipoBeneficiario == PERSONA_MORAL_EUA) {
//			tipoTaxPayer = 2l;
			exemptPayee = true;
		}
	}
	
	public Beneficiario construyeBO() {
		Beneficiario retorno = new Beneficiario();
		
		construyeReglas();
		construyeBOBasico(retorno);
		
		FormatoW9 formatoW9 = new FormatoW9();
		formatoW9.setBusinessName(bussinesName);
		Field3W9 field3W9 = new Field3W9();
		field3W9.setIdCampo(tipoTaxPayer);
		formatoW9.setTypeTaxPayer(field3W9);
		formatoW9.setOtherDescription(otherDescription);
		formatoW9.setTaxClassification(taxClassification);
		formatoW9.setExemptPayee(exemptPayee);
		formatoW9.setRequesterNameAddress(requesterNameAddress);
		formatoW9.setListAccountNumbers(listAccountNumbers);
		formatoW9.setSsn(ssn);
		formatoW9.setEmployerIdNumb(employerIdNumb);
		
		
		if(exemptPayeeW9.getIdExemptPayeeW9() == null){
			formatoW9.setExemptPayeeW9(null);
		}
		else{
			formatoW9.setExemptPayeeW9(exemptPayeeW9);
		}
		
		if(exemptionFatcaW9.getIdExemptionFatcaW9() == null){
			formatoW9.setExemptionFatcaW9(null);
		}
		else{
			formatoW9.setExemptionFatcaW9(exemptionFatcaW9);
		}
		
		retorno.setFormatoW9(formatoW9);
		
		retorno.setDomicilioW9(construyeDomicilioNormal());
		
		return retorno;
	}
	
	public void construyeBean(Beneficiario beneficiario, boolean llenaCamposVacios) {
		super.construyeBean(beneficiario, llenaCamposVacios);
		
		descTipoBeneficiario = beneficiario.getFormatoW9().getTypeTaxPayer().getDescripcion();
		
		campos = HabilitaCamposFormatosBeneficiarios.definirCamposBloqueadosW9(custodio, idTipoBeneficiario);
		
		bussinesName = beneficiario.getFormatoW9().getBusinessName();
		
		tipoTaxPayer = beneficiario.getFormatoW9().getTypeTaxPayer().getIdCampo();
		descripcionTipoTaxPayer = beneficiario.getFormatoW9().getTypeTaxPayer().getDescripcion();
		if( StringUtils.isNotBlank(descripcionTipoTaxPayer) ) {
			descripcionTipoTaxPayer = descripcionTipoTaxPayer.toUpperCase();
		}
		
		taxClassification = beneficiario.getFormatoW9().getTaxClassification();
		otherDescription = beneficiario.getFormatoW9().getOtherDescription();
		exemptPayee = beneficiario.getFormatoW9().getExemptPayee();
		
		if(beneficiario.getDomicilioW9() != null) {
			calleResidencial = beneficiario.getDomicilioW9().getStreet();
			numeroExteriorResidencial =  beneficiario.getDomicilioW9().getOuterNumber();
			numeroInteriorResidencial =  beneficiario.getDomicilioW9().getInteriorNumber();
			codigoPostalResidencial = beneficiario.getDomicilioW9().getPostalCode();
			cityTownResidencial = beneficiario.getDomicilioW9().getCityTown();
			stateProvinceResidencial = beneficiario.getDomicilioW9().getStateProvince();
		}
		
		construyeReglas();
		
		requesterNameAddress = beneficiario.getFormatoW9().getRequesterNameAddress();
		listAccountNumbers = beneficiario.getFormatoW9().getListAccountNumbers();
		ssn = beneficiario.getFormatoW9().getSsn();
		employerIdNumb = beneficiario.getFormatoW9().getEmployerIdNumb();
		//idExemptPayeeCode = (beneficiario.getFormatoW9().getExemptPayeeW9() == null) ? null : beneficiario.getFormatoW9().getExemptPayeeW9().getIdExemptPayeeW9();
		//idExemptionFatcaCode = (beneficiario.getFormatoW9().getExemptionFatcaW9() == null) ? null : beneficiario.getFormatoW9().getExemptionFatcaW9().getIdExemptionFatcaW9();
		exemptPayeeW9 = beneficiario.getFormatoW9().getExemptPayeeW9();
		exemptionFatcaW9 = beneficiario.getFormatoW9().getExemptionFatcaW9();
		//activo = (beneficiario.getFormatoW9().getActivo() != null) ? beneficiario.getFormatoW9().getActivo().toString() : null;

        if(llenaCamposVacios) {
            ponerValoresVacios();
        }
	}
	
	private void ponerValoresVacios() {
		bussinesName = StringUtils.isNotBlank(bussinesName) ? bussinesName : BEN_CADENA_VACIA;
		
		taxClassification = StringUtils.isNotBlank(taxClassification) ? taxClassification : BEN_CADENA_VACIA;
		otherDescription = StringUtils.isNotBlank(otherDescription) ? otherDescription : BEN_CADENA_VACIA;
		
		calleResidencial = StringUtils.isNotBlank(calleResidencial) ? calleResidencial : BEN_CADENA_VACIA;
		numeroExteriorResidencial = StringUtils.isNotBlank(numeroExteriorResidencial) ? numeroExteriorResidencial : BEN_CADENA_VACIA;
		numeroInteriorResidencial = StringUtils.isNotBlank(numeroInteriorResidencial) ? numeroInteriorResidencial : BEN_CADENA_VACIA;
		codigoPostalResidencial = StringUtils.isNotBlank(codigoPostalResidencial) ? codigoPostalResidencial : BEN_CADENA_VACIA;
		cityTownResidencial = StringUtils.isNotBlank(cityTownResidencial) ? cityTownResidencial : BEN_CADENA_VACIA;
		stateProvinceResidencial = StringUtils.isNotBlank(stateProvinceResidencial) ? stateProvinceResidencial : BEN_CADENA_VACIA;
		
		requesterNameAddress = StringUtils.isNotBlank(requesterNameAddress) ? requesterNameAddress : BEN_CADENA_VACIA;
		listAccountNumbers = StringUtils.isNotBlank(listAccountNumbers) ? listAccountNumbers : BEN_CADENA_VACIA;
		ssn = StringUtils.isNotBlank(ssn) ? ssn : BEN_CADENA_VACIA;
		employerIdNumb = StringUtils.isNotBlank(employerIdNumb) ? employerIdNumb : BEN_CADENA_VACIA;
	}
	
	private Domicilio construyeDomicilioNormal() {
		Domicilio retorno = new Domicilio();
		retorno.setStreet(calleResidencial);
		retorno.setOuterNumber(numeroExteriorResidencial);
		retorno.setInteriorNumber(numeroInteriorResidencial);
		retorno.setPostalCode(codigoPostalResidencial);
		retorno.setCityTown(cityTownResidencial);
		retorno.setStateProvince(stateProvinceResidencial);
		return retorno;
	}

    public Beneficiario construyeBO(Beneficiario beneficiario) {

		construyeReglas();
		construyeBOBasico(beneficiario);

		FormatoW9 formatoW9 = beneficiario.getFormatoW9();
		formatoW9.setBusinessName(bussinesName);
		Field3W9 field3W9 = formatoW9.getTypeTaxPayer();
		field3W9.setIdCampo(tipoTaxPayer);
		formatoW9.setTypeTaxPayer(field3W9);
		formatoW9.setOtherDescription(otherDescription);
		formatoW9.setTaxClassification(taxClassification);
		formatoW9.setExemptPayee(exemptPayee);
		formatoW9.setRequesterNameAddress(requesterNameAddress);
		formatoW9.setListAccountNumbers(listAccountNumbers);
		formatoW9.setSsn(ssn);
		formatoW9.setEmployerIdNumb(employerIdNumb);
		
		if(exemptPayeeW9.getIdExemptPayeeW9() == null || exemptPayeeW9.getIdExemptPayeeW9().longValue() == -1){
			formatoW9.setExemptPayeeW9(null);
		}
		else{
			formatoW9.setExemptPayeeW9(exemptPayeeW9);
		}
		
		if(exemptionFatcaW9.getIdExemptionFatcaW9() == null || exemptionFatcaW9.getIdExemptionFatcaW9().longValue() == -1){
			formatoW9.setExemptionFatcaW9(null);
		}
		else{
			formatoW9.setExemptionFatcaW9(exemptionFatcaW9);
		}
		
		beneficiario.setFormatoW9(formatoW9);
		beneficiario.setDomicilioW9(construyeDomicilioNormal(beneficiario.getDomicilioW9()));
		return beneficiario;
	}

    private Domicilio construyeDomicilioNormal(Domicilio retorno) {
		retorno.setStreet(calleResidencial);
		retorno.setOuterNumber(numeroExteriorResidencial);
		retorno.setInteriorNumber(numeroInteriorResidencial);
		retorno.setPostalCode(codigoPostalResidencial);
		retorno.setCityTown(cityTownResidencial);
		retorno.setStateProvince(stateProvinceResidencial);
		return retorno;
	}

	public List getLstTipoTaxPayer() {
		return lstTipoTaxPayer;
	}

	public Long getTipoTaxPayer() {
		return tipoTaxPayer;
	}

	public String getBussinesName() {
		return bussinesName;
	}

	public String getTaxClassification() {
		return taxClassification;
	}

	public String getOtherDescription() {
		return otherDescription;
	}

	public Boolean getExemptPayee() {
		return exemptPayee;
	}

	public String getCalleResidencial() {
		return calleResidencial;
	}

	public String getNumeroExteriorResidencial() {
		return numeroExteriorResidencial;
	}

	public String getNumeroInteriorResidencial() {
		return numeroInteriorResidencial;
	}

	public String getCodigoPostalResidencial() {
		return codigoPostalResidencial;
	}

	public String getCityTownResidencial() {
		return cityTownResidencial;
	}

	public String getStateProvinceResidencial() {
		return stateProvinceResidencial;
	}

	public String getRequesterNameAddress() {
		return requesterNameAddress;
	}

	public String getListAccountNumbers() {
		return listAccountNumbers;
	}

	public String getSsn() {
		return ssn;
	}

	public String getEmployerIdNumb() {
		return employerIdNumb;
	}

	public CamposFormatoW9 getCampos() {
		return campos;
	}

	public String getDescripcionTipoTaxPayer() {
		return descripcionTipoTaxPayer;
	}

	public void setDescripcionTipoTaxPayer(String descripcionTipoTaxPayer) {
		this.descripcionTipoTaxPayer = descripcionTipoTaxPayer;
	}

	public void setLstTipoTaxPayer(List lstTipoTaxPayer) {
		this.lstTipoTaxPayer = lstTipoTaxPayer;
	}

	public void setTipoTaxPayer(Long tipoTaxPayer) {
		this.tipoTaxPayer = tipoTaxPayer;
	}

	public void setBussinesName(String bussinesName) {
		this.bussinesName = bussinesName;
	}

	public void setTaxClassification(String taxClassification) {
		this.taxClassification = taxClassification;
	}

	public void setOtherDescription(String otherDescription) {
		this.otherDescription = otherDescription;
	}

	public void setExemptPayee(Boolean exemptPayee) {
		this.exemptPayee = exemptPayee;
	}

	public void setCalleResidencial(String calleResidencial) {
		this.calleResidencial = calleResidencial;
	}

	public void setNumeroExteriorResidencial(String numeroExteriorResidencial) {
		this.numeroExteriorResidencial = numeroExteriorResidencial;
	}

	public void setNumeroInteriorResidencial(String numeroInteriorResidencial) {
		this.numeroInteriorResidencial = numeroInteriorResidencial;
	}

	public void setCodigoPostalResidencial(String codigoPostalResidencial) {
		this.codigoPostalResidencial = codigoPostalResidencial;
	}

	public void setCityTownResidencial(String cityTownResidencial) {
		this.cityTownResidencial = cityTownResidencial;
	}

	public void setStateProvinceResidencial(String stateProvinceResidencial) {
		this.stateProvinceResidencial = stateProvinceResidencial;
	}

	public void setRequesterNameAddress(String requesterNameAddress) {
		this.requesterNameAddress = requesterNameAddress;
	}

	public void setListAccountNumbers(String listAccountNumbers) {
		this.listAccountNumbers = listAccountNumbers;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public void setEmployerIdNumb(String employerIdNumb) {
		this.employerIdNumb = employerIdNumb;
	}

	public void setCampos(CamposFormatoW9 campos) {
		this.campos = campos;
	}

	/**
	 * @return the exemptPayeeW9
	 */
	public ExemptPayeeW9 getExemptPayeeW9() {
		if(exemptPayeeW9 == null)
			exemptPayeeW9 = new ExemptPayeeW9();
		
		return exemptPayeeW9;
	}

	/**
	 * @param exemptPayeeW9 the exemptPayeeW9 to set
	 */
	public void setExemptPayeeW9(ExemptPayeeW9 exemptPayeeW9) {
		this.exemptPayeeW9 = exemptPayeeW9;
	}

	/**
	 * @return the exemptionFatcaW9
	 */
	public ExemptionFatcaW9 getExemptionFatcaW9() {
		if(exemptionFatcaW9 == null)
			exemptionFatcaW9 = new ExemptionFatcaW9();
		
		return exemptionFatcaW9;
	}

	/**
	 * @param exemptionFatcaW9 the exemptionFatcaW9 to set
	 */
	public void setExemptionFatcaW9(ExemptionFatcaW9 exemptionFatcaW9) {
		this.exemptionFatcaW9 = exemptionFatcaW9;
	}
}
