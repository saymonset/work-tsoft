/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */

package com.indeval.portaldali.middleware.services.tesoreria;

import java.util.Calendar;

import org.springframework.validation.Errors;

import com.indeval.sidv.decretos.persistence.model.vo.AgenteVO;
import com.indeval.sidv.decretos.persistence.model.vo.EmisionVO;


/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class GetEdoCtaLiqParams extends com.indeval.sidv.decretos.persistence.model.vo.LiquidacionDecretosParams {

	/**
	 * Constante de serializacion
	 */
	private static final long serialVersionUID = 1L;

	private AgenteVO institucionAutorizada = new AgenteVO();

	/**
	 * @return AgenteVO
	 */
	public AgenteVO getInstitucionAutorizada() {
		return institucionAutorizada;
	}

	/**
	 * @param institucionAutorizada
	 */
	public void setInstitucionAutorizada(AgenteVO institucionAutorizada) {
		this.institucionAutorizada = institucionAutorizada;
	}

	/**
	 * @return GetEdoCtaLiqParams
	 */
	public GetEdoCtaLiqParams clona() {
		Calendar cal = Calendar.getInstance();
		GetEdoCtaLiqParams newParams = new GetEdoCtaLiqParams();
		if (this.getAgente() != null) {
			newParams.setAgente(new AgenteVO());
			newParams.getAgente().setId(this.getAgente().getId());
			newParams.getAgente().setFolio(this.getAgente().getFolio());
			newParams.getAgente().setCuenta(this.getAgente().getCuenta());
		}

		if (this.getInstitucionAutorizada() != null) {
			newParams.setInstitucionAutorizada(new com.indeval.sidv.decretos.persistence.model.vo.AgenteVO());
			newParams.getInstitucionAutorizada().setId(this.getInstitucionAutorizada().getId());
			newParams.getInstitucionAutorizada().setFolio(this.getInstitucionAutorizada().getFolio());
		}

		if (this.getEmision() != null) {
			newParams.setEmision(new EmisionVO());
			newParams.getEmision().setIdTipoValor(this.getEmision().getIdTipoValor());
			newParams.getEmision().setEmisora(this.getEmision().getEmisora());
			newParams.getEmision().setSerie(this.getEmision().getSerie());
			newParams.getEmision().setCupon(this.getEmision().getCupon());
		}

		if (this.getFechaFin() != null) {
			cal.setTimeInMillis(this.getFechaFin().getTime());
			newParams.setFechaFin(cal.getTime());
		}

		if (this.getFechaIni() != null) {
			cal.setTimeInMillis(this.getFechaIni().getTime());
			newParams.setFechaIni(cal.getTime());
		}

		newParams.setTipoConsulta(this.getTipoConsulta());
		newParams.setTipoEjercicio(this.getTipoEjercicio());
		newParams.setTipoMoneda(this.getTipoMoneda());

		return newParams;
	}

	/**
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	public void validate(Object obj, Errors errors) {
	}

}
