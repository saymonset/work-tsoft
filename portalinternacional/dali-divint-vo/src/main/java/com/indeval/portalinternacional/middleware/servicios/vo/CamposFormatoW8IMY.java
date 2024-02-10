package com.indeval.portalinternacional.middleware.servicios.vo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.validation.Errors;

import com.indeval.portaldali.middleware.servicios.modelo.AbstractBaseDTO;

/**
 * @author Oscar Garcia Granados
 *
 */
public class CamposFormatoW8IMY extends AbstractBaseDTO {
	
	/**
     * Constante de serializacion
     */
    private static final long serialVersionUID = 1L;
    
    long tamanioField3 = 8;
    
    /*Aqui se ponen las opciones disponibles en el campo 3 del
	tipo de entidad para saber si se habilitan o no*/
    private Map<Long, Boolean> field3;
	/*campo 6*/
	private boolean usTaxpayerIdentificationNumber;
	private boolean usTaxpayerIdentificationNumberSSNorITIN;
	private boolean usTaxpayerIdentificationNumberEIN;
	private boolean usTaxpayerIdentificationNumberQIEIN;
	/*campo 8*/
	private boolean referenceNumbers;
	/*campo 9*/
	private boolean field9a;
	private boolean field9b;
	private boolean field9bLine;
	private boolean field9c;
	private boolean field9cLine;
	/* campo 10 */
	private boolean field10a;
	private boolean field10b; 
	/*campo 11*/
	private boolean field11;
	/*campo 12*/
	private boolean field12;
	/*campo 13*/
	private boolean field13;
	/*campo 14*/
	private boolean field14;
	/*campo 15*/
	private boolean field15;


	public CamposFormatoW8IMY() {
		field3 = new HashMap<Long, Boolean>();
		for (long i = 1; i <= tamanioField3; i++) {
			field3.put(i, false);
		}
	}
	
	public void bloquearTodosOBO() {
		field3 = new HashMap<Long, Boolean>();
		for (long i = 1; i <= tamanioField3; i++) {
			field3.put(i, true);
		}
	}
	
	public void bloquearTodosCampo6() {
		usTaxpayerIdentificationNumber = true;
		usTaxpayerIdentificationNumberEIN = true;
		usTaxpayerIdentificationNumberQIEIN  = true;
		usTaxpayerIdentificationNumberSSNorITIN = true;
	}
	
	public void bloquearTodosPartes() {
		field9a = true;
		field9b = true;
		field9bLine = true;
		field9c = true;;
		field9cLine = true;
		field10a = true;
		field10b = true;
		field11 = true;
		field12 = true;
		field13 = true;
		field14 = true;
		field15 = true;
	}

	public void validate(Object arg0, Errors arg1) {
		
	}
	
	/**
	 * @return the field3
	 */
	public Map<Long, Boolean> getField3() {
		return field3;
	}

	/**
	 * 
	 * @return field15
	 */
	public boolean isField15() {
		return field15;
	}

	/**
	 * 
	 * @param field15
	 */
	public void setField15(boolean field15) {
		this.field15 = field15;
	}

	/**
	 * 
	 * @return field9a
	 */
	public boolean isField9a() {
		return field9a;
	}

	/**
	 * 
	 * @param field9a
	 */
	public void setField9a(boolean field9a) {
		this.field9a = field9a;
	}

	/**
	 * 
	 * @return field9b
	 */
	public boolean isField9b() {
		return field9b;
	}

	/**
	 * 
	 * @param field9b
	 */
	public void setField9b(boolean field9b) {
		this.field9b = field9b;
	}

	/**
	 * 
	 * @return field9bLine
	 */
	public boolean isField9bLine() {
		return field9bLine;
	}

	/**
	 * 
	 * @param field9bLine
	 */
	public void setField9bLine(boolean field9bLine) {
		this.field9bLine = field9bLine;
	}

	/**
	 * 
	 * @return field9c
	 */
	public boolean isField9c() {
		return field9c;
	}

	/**
	 * 
	 * @param field9c
	 */
	public void setField9c(boolean field9c) {
		this.field9c = field9c;
	}

	/**
	 * 
	 * @return field9cLine
	 */
	public boolean isField9cLine() {
		return field9cLine;
	}

	/**
	 * 
	 * @param field9cLine
	 */
	public void setField9cLine(boolean field9cLine) {
		this.field9cLine = field9cLine;
	}

	/**
	 * 
	 * @return referenceNumbers
	 */
	public boolean isReferenceNumbers() {
		return referenceNumbers;
	}

	/**
	 * 
	 * @param referenceNumbers
	 */
	public void setReferenceNumbers(boolean referenceNumbers) {
		this.referenceNumbers = referenceNumbers;
	}

	/**
	 * 
	 * @return usTaxpayerIdentificationNumber
	 */
	public boolean isUsTaxpayerIdentificationNumber() {
		return usTaxpayerIdentificationNumber;
	}

	/**
	 * 
	 * @param usTaxpayerIdentificationNumber
	 */
	public void setUsTaxpayerIdentificationNumber(
			boolean usTaxpayerIdentificationNumber) {
		this.usTaxpayerIdentificationNumber = usTaxpayerIdentificationNumber;
	}

	/**
	 * 
	 * @return usTaxpayerIdentificationNumberEIN
	 */
	public boolean isUsTaxpayerIdentificationNumberEIN() {
		return usTaxpayerIdentificationNumberEIN;
	}

	/**
	 * 
	 * @param usTaxpayerIdentificationNumberEIN
	 */
	public void setUsTaxpayerIdentificationNumberEIN(
			boolean usTaxpayerIdentificationNumberEIN) {
		this.usTaxpayerIdentificationNumberEIN = usTaxpayerIdentificationNumberEIN;
	}

	/**
	 * 
	 * @return usTaxpayerIdentificationNumberQIEIN
	 */
	public boolean isUsTaxpayerIdentificationNumberQIEIN() {
		return usTaxpayerIdentificationNumberQIEIN;
	}

	/**
	 * 
	 * @param usTaxpayerIdentificationNumberQIEIN
	 */
	public void setUsTaxpayerIdentificationNumberQIEIN(
			boolean usTaxpayerIdentificationNumberQIEIN) {
		this.usTaxpayerIdentificationNumberQIEIN = usTaxpayerIdentificationNumberQIEIN;
	}

	/**
	 * 
	 * @return usTaxpayerIdentificationNumberSSNorITIN
	 */
	public boolean isUsTaxpayerIdentificationNumberSSNorITIN() {
		return usTaxpayerIdentificationNumberSSNorITIN;
	}

	/**
	 * 
	 * @param usTaxpayerIdentificationNumberSSNorITIN
	 */
	public void setUsTaxpayerIdentificationNumberSSNorITIN(
			boolean usTaxpayerIdentificationNumberSSNorITIN) {
		this.usTaxpayerIdentificationNumberSSNorITIN = usTaxpayerIdentificationNumberSSNorITIN;
	}

	/**
	 * 
	 * @return field10a
	 */
	public boolean isField10a() {
		return field10a;
	}

	/**
	 * 
	 * @param field10a
	 */
	public void setField10a(boolean field10a) {
		this.field10a = field10a;
	}

	/**
	 * 
	 * @return field10b
	 */
	public boolean isField10b() {
		return field10b;
	}

	/**
	 * 
	 * @param field10b
	 */
	public void setField10b(boolean field10b) {
		this.field10b = field10b;
	}

	/**
	 * 
	 * @return field11
	 */
	public boolean isField11() {
		return field11;
	}

	/**
	 * 
	 * @param field11
	 */
	public void setField11(boolean field11) {
		this.field11 = field11;
	}

	/**
	 * 
	 * @return field12
	 */
	public boolean isField12() {
		return field12;
	}

	/**
	 * 
	 * @param field12
	 */
	public void setField12(boolean field12) {
		this.field12 = field12;
	}

	/**
	 * 
	 * @return field13
	 */
	public boolean isField13() {
		return field13;
	}

	/**
	 * 
	 * @param field13
	 */
	public void setField13(boolean field13) {
		this.field13 = field13;
	}

	/**
	 * 
	 * @return field14
	 */
	public boolean isField14() {
		return field14;
	}

	/**
	 * 
	 * @param field14
	 */
	public void setField14(boolean field14) {
		this.field14 = field14;
	}

}
