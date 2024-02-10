package com.indeval.portalinternacional.middleware.servicios.vo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.validation.Errors;

import com.indeval.portaldali.middleware.servicios.modelo.AbstractBaseDTO;

/**
 * @author Oscar Garcia Granados
 * 
 */
public class CamposFormatoW8BEN extends AbstractBaseDTO {

	/**
	 * Constante de serializacion
	 */
	private static final long serialVersionUID = 1L;

	/* Campos booleanos de los campos del W8BEN que se pueden alterar */
	/*
	 * Aqui se ponen las opciones disponibles en el campo 3 del tipo de entidad
	 * para saber si se habilitan o no
	 */
	private long tamanioField3 = 13;
	
	private Map<Long, Boolean> field3;
	/* campo 8 */
	private boolean referenceNumbers;
	/* campo 9 */
	private boolean field9a;
	private boolean field9aResidencePlace;
	private boolean field9b;
	private boolean field9c;
	private boolean field9d;
	private boolean field9e;
	/* campo 10 */
	private boolean field10Article;
	private boolean field10Rate;
	private boolean field10TypeOfIncome;
	private boolean field10Reasons;
	/* campo 11 */
	private boolean field11;
	/* campo "Capacity in which acting" */
	private boolean partIVCapacityInWhichActing;

	public CamposFormatoW8BEN() {
		field3 = new HashMap<Long, Boolean>();
		for (long i = 1; i <= tamanioField3; i++) {
			field3.put(i, false);
		}
	}

	public void bloqueaTodosTOBO() {
		field3 = new HashMap<Long, Boolean>();
		for (long i = 1; i <= tamanioField3; i++) {
			field3.put(i, true);
		}
	}

	public void validate(Object arg0, Errors arg1) {

	}

	/**
	 * @return Map<Long, Boolean>
	 */
	public Map<Long, Boolean> getField3() {
		return field3;
	}

	/**
	 * 
	 * @return field10Article
	 */
	public boolean isField10Article() {
		return field10Article;
	}

	/**
	 * 
	 * @param field10Article
	 */
	public void setField10Article(boolean field10Article) {
		this.field10Article = field10Article;
	}

	/**
	 * 
	 * @return field10Rate
	 */
	public boolean isField10Rate() {
		return field10Rate;
	}

	/**
	 * 
	 * @param field10Rate
	 */
	public void setField10Rate(boolean field10Rate) {
		this.field10Rate = field10Rate;
	}

	/**
	 * 
	 * @return field10Reasons
	 */
	public boolean isField10Reasons() {
		return field10Reasons;
	}

	/**
	 * 
	 * @param field10Reasons
	 */
	public void setField10Reasons(boolean field10Reasons) {
		this.field10Reasons = field10Reasons;
	}

	/**
	 * 
	 * @return field10TypeOfIncome
	 */
	public boolean isField10TypeOfIncome() {
		return field10TypeOfIncome;
	}

	/**
	 * 
	 * @param field10TypeOfIncome
	 */
	public void setField10TypeOfIncome(boolean field10TypeOfIncome) {
		this.field10TypeOfIncome = field10TypeOfIncome;
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
	 * @return field9aResidencePlace
	 */
	public boolean isField9aResidencePlace() {
		return field9aResidencePlace;
	}

	/**
	 * 
	 * @param field9aResidencePlace
	 */
	public void setField9aResidencePlace(boolean field9aResidencePlace) {
		this.field9aResidencePlace = field9aResidencePlace;
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
	 * @return field9d
	 */
	public boolean isField9d() {
		return field9d;
	}

	/**
	 * 
	 * @param field9d
	 */
	public void setField9d(boolean field9d) {
		this.field9d = field9d;
	}

	/**
	 * 
	 * @return field9e
	 */
	public boolean isField9e() {
		return field9e;
	}

	/**
	 * 
	 * @param field9e
	 */
	public void setField9e(boolean field9e) {
		this.field9e = field9e;
	}

	/**
	 * 
	 * @return partIVCapacityInWhichActing
	 */
	public boolean isPartIVCapacityInWhichActing() {
		return partIVCapacityInWhichActing;
	}

	/**
	 * 
	 * @param partIVCapacityInWhichActing
	 */
	public void setPartIVCapacityInWhichActing(
			boolean partIVCapacityInWhichActing) {
		this.partIVCapacityInWhichActing = partIVCapacityInWhichActing;
	}

	/**
	 * 
	 * @return
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

}
