/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.util.vo;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import com.indeval.protocolofinanciero.api.vo.TraspasoContraPagoVO;

import org.springframework.validation.Errors;

/**
 * Clase que encapsula los par&aacute;metros utilizados en el servicio de
 * confirmaci&oacute;n de operaciones match.
 * 
 * @author <a href="mailto:salvador.valeriano@itbrain.com.mx">Salvador Valeriano
 *         L.</a>
 */
public class ConfirmaOperacionParams extends AbstractBaseDTO {

	/**
	 * Serial UID
	 */
	private static final long serialVersionUID = 1L;

	TraspasoContraPagoVO vo;
	String folioControl;

	/**
	 * @return the folioControl
	 */
	public String getFolioControl() {
		return folioControl;
	}

	/**
	 * @param folioControl
	 *            the folioControl to set
	 */
	public void setFolioControl(String folioControl) {
		this.folioControl = folioControl;
	}

	/**
	 * @return the vo
	 */
	public TraspasoContraPagoVO getVo() {
		return vo;
	}

	/**
	 * @param vo
	 *            the vo to set
	 */
	public void setVo(TraspasoContraPagoVO vo) {
		this.vo = vo;
	}

	/**
	 * @see org.springframework.validation.Validator #validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object target, Errors errors) {

	}

}
