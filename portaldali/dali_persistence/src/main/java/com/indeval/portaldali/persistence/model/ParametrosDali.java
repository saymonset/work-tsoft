/*
 * Copyrigth (c) 2010 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Cata&acute;logo de parametros para el Dali
 * 
 * C_PARAMETROS_DALI
 *
 * @author  Maria C. Buendia
 * @version 1.0
 */
@Entity
@Table(name="C_PARAMETROS_DALI")
public class ParametrosDali implements Serializable {
		
	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** identificador de cuenta */
	@Id
	@Column(name = "ID_PARAMETROS_DALI", nullable = false)
	private Long idParametro;
	
	/** horario inicial de modificacion para las cuentas de retiro */
	@Column(name = "INI_MODIF_CTA_HH24MI", nullable = false)
	private String horaInicioModificacionCuentas;
	
	/** horario final de modificacion para las cuentas de retiro */
	@Column(name = "FIN_MODIF_CTA_HH24MI", nullable = false)
	private String horaFinModificacionCuentas;
	
	/** tipos de institucion autorizados para la creacion de cuentas (numericos, separados por comas) */
	@Column(name = "CTAS_BCOM_IDTIPOS_INST", nullable = false)
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
