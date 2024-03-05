/**
 * 2H Software - Bursatec
 * 
 * Sistema de Consulta de Estados de Cuenta
 *
 * Jan 14, 2008
 *
 */
package com.indeval.portaldali.middleware.services.estadocuenta.test;

import com.indeval.portaldali.middleware.services.BaseTestCase;
import com.indeval.portaldali.middleware.services.common.MensajePortalService;

public class MensajePortalServiceImplTest extends BaseTestCase {

	private MensajePortalService mensajePortalService;

	public void onSetUp() {
		super.onSetUp();
		mensajePortalService = (MensajePortalService) getBean("mensajePortalService");
	}

	public void testConsultarSaldosEfectivo() {
		assertNotNull(mensajePortalService);
	}

}
