/*
 * Copyright (c) 2010 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.services.divisioninternacional;

/**
 * Servicio temporal de beneficiarios borrar codigo una vez usuado
 * @author Rafael Ibarra Zendejas
 * @version 1.0
 */
public interface TemporalBeneficiariosService {

	/**
	 * Servicio para copiar unir los campos de nombres, apellido paterno, apellido materno, razo social en uno solo
	 */
	void copiaNombreRazonSocial();

	/**
	 * Servicio para Validar los nombres de los beneficiarios
	 */
	void validaNombresBeneficiarios();
}
