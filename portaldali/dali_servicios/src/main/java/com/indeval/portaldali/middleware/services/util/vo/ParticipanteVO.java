/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.util.vo;

import com.indeval.portaldali.middleware.services.AbstractBaseDTO;
import org.springframework.validation.Errors;

/**
 * Objeto que representa una institucion participante en el match con su id y
 * folio
 * 
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class ParticipanteVO extends AbstractBaseDTO {

	/** Constante de serializacion */
	private static final long serialVersionUID = 1L;

	/**
	 * Folio de la Instituci&oacute;n participante
	 */
	private String FolioInstitucion;

	/**
	 * ID de la Instituci&oacute;n participante
	 */
	private String IdInstitucion;

	/**
	 * @param target
	 * @param errors
	 */
	public void validate(Object target, Errors errors) {
	}

	/**
	 * @return the folioInstitucion
	 */
	public String getFolioInstitucion() {
		return FolioInstitucion;
	}

	/**
	 * @param folioInstitucion
	 *            the folioInstitucion to set
	 */
	public void setFolioInstitucion(String folioInstitucion) {
		this.FolioInstitucion = folioInstitucion;
	}

	/**
	 * @return the idInstitucion
	 */
	public String getIdInstitucion() {
		return IdInstitucion;
	}

	/**
	 * @param idInstitucion
	 *            the idInstitucion to set
	 */
	public void setIdInstitucion(String idInstitucion) {
		this.IdInstitucion = idInstitucion;
	}

}
