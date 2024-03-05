/*
 * Copyrigth (c) 2010 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.dto;

import java.io.Serializable;

/**
 * Data Transfer Object para paso de datos del model a los servicios
 *  
 * @author Maria C. Buendia
 * @version 1.0
 */
public class ParametrosDaliDTO implements Serializable{
	
	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** identificador de cuenta */
	private Long idParametro;
	
	/** horario inicial de modificacion para las cuentas de retiro */
	private String horaInicioModificacionCuentas;
	
	/** horario final de modificacion para las cuentas de retiro */
	private String horaFinModificacionCuentas;
	
	/** tipos de institucion autorizados para la creacion de cuentas */
	private String ctasBComTipoInstituciones;

	public Long getIdParametro() {
		return idParametro;
	}

	public void setIdParametro(Long idParametro) {
		this.idParametro = idParametro;
	}

	public String getHoraInicioModificacionCuentas() {
		return horaInicioModificacionCuentas;
	}

	public void setHoraInicioModificacionCuentas(
			String horaInicioModificacionCuentas) {
		this.horaInicioModificacionCuentas = horaInicioModificacionCuentas;
	}

	public String getHoraFinModificacionCuentas() {
		return horaFinModificacionCuentas;
	}

	public void setHoraFinModificacionCuentas(String horaFinModificacionCuentas) {
		this.horaFinModificacionCuentas = horaFinModificacionCuentas;
	}

	public String getCtasBComTipoInstituciones() {
		return ctasBComTipoInstituciones;
	}

	public void setCtasBComTipoInstituciones(String ctasBComTipoInstituciones) {
		this.ctasBComTipoInstituciones = ctasBComTipoInstituciones;
	}

}
