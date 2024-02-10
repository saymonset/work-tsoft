package com.indeval.portalinternacional.middleware.servicios.vo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.validation.Errors;

import com.indeval.portaldali.middleware.servicios.modelo.AbstractBaseDTO;

/**
 * 
 * @author Oscar Garcia Granados
 * 
 */
public class CamposFormatoW9 extends AbstractBaseDTO {

	/**
	 * Constante de serializacion
	 */
	private static final long serialVersionUID = 1L;

	private boolean bussinessName;
	/*se incremento de 5 a 7 opciones el selectOneRadio*/
	private long tamanioField3 = 7;
	
	private Map<Long, Boolean> field3;
	private boolean taxClassification;
	private boolean otherDescription;
	private boolean exemptPayee;
	private boolean listAccountNumbers;
	private boolean ssn;
	private boolean employerIdNumb;
	private boolean exemptPayeeCode;
	private boolean exemptionFromFatca;

	public void bloquearTodosTOBO() {
		field3 = new HashMap<Long, Boolean>();
		for (long i = 1; i <= tamanioField3; i++) {
			field3.put(i, true);
		}
		taxClassification = true;
		otherDescription = true;
	}

	public CamposFormatoW9() {
		field3 = new HashMap<Long, Boolean>();
		for (long i = 1; i <= tamanioField3; i++) {
			field3.put(i, false);
		}
	}

	
	public void validate(Object arg0, Errors arg1) {
		// TODO Auto-generated method stub
	}
	
	/**
	 * @return the field3
	 */
	public Map<Long, Boolean> getField3() {
		return field3;
	}

	public boolean isTaxClassification() {
		return taxClassification;
	}

	public boolean isOtherDescription() {
		return otherDescription;
	}

	public boolean isExemptPayee() {
		return exemptPayee;
	}

	public boolean isSsn() {
		return ssn;
	}

	public boolean isEmployerIdNumb() {
		return employerIdNumb;
	}

	public boolean isBussinessName() {
		return bussinessName;
	}

	public boolean isListAccountNumbers() {
		return listAccountNumbers;
	}

	public void setListAccountNumbers(boolean listAccountNumbers) {
		this.listAccountNumbers = listAccountNumbers;
	}

	public void setBussinessName(boolean bussinessName) {
		this.bussinessName = bussinessName;
	}

	public void setTaxClassification(boolean taxClassification) {
		this.taxClassification = taxClassification;
	}

	public void setOtherDescription(boolean otherDescription) {
		this.otherDescription = otherDescription;
	}

	public void setExemptPayee(boolean exemptPayee) {
		this.exemptPayee = exemptPayee;
	}

	public void setSsn(boolean ssn) {
		this.ssn = ssn;
	}

	public void setEmployerIdNumb(boolean employerIdNumb) {
		this.employerIdNumb = employerIdNumb;
	}

	public boolean isExemptPayeeCode() {
		return exemptPayeeCode;
	}

	public void setExemptPayeeCode(boolean exemptPayeeCode) {
		this.exemptPayeeCode = exemptPayeeCode;
	}

	public boolean isExemptionFromFatca() {
		return exemptionFromFatca;
	}

	public void setExemptionFromFatca(boolean exemptionFromFatca) {
		this.exemptionFromFatca = exemptionFromFatca;
	}
}
