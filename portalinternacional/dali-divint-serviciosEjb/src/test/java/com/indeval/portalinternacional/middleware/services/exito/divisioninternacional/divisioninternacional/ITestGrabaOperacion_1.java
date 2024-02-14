/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.services.exito.divisioninternacional.divisioninternacional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portalinternacional.middleware.services.BaseITestService;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService;
import com.indeval.portalinternacional.middleware.servicios.vo.GrabaOperacionParams;
import com.indeval.protocolofinanciero.api.vo.TraspasoLibrePagoVO;

/**
 * @author javiles
 *
 */
public class ITestGrabaOperacion_1 extends BaseITestService {

    /** Servicio de log */
	private final Logger log = LoggerFactory.getLogger(this.getClass());

    /** Servicio que sera probado en esta prueba */
    private DivisionInternacionalService divisionInternacionalService;

    /**
     * @see com.indeval.portalinternacional.portalinternacional.services.BaseITestService#onSetUp()
     */
    protected void onSetUp() throws Exception {
        super.onSetUp();
        if (divisionInternacionalService == null) {
            divisionInternacionalService = (DivisionInternacionalService) applicationContext
                    .getBean("divisionInternacionalService");
        }
    }

    public void testGrabaOperacion() {
        
        log.info("Ejecutando a testGrabaOperacion()");
        
        assertNotNull(divisionInternacionalService);
        
        GrabaOperacionParams params = new GrabaOperacionParams();
        params.setOrigenRegistro("DIVINT");
        params.setTraspasoLibrePagoVO(new TraspasoLibrePagoVO());
        params.getTraspasoLibrePagoVO().setIdFolioCtaTraspasante("010030307");
        params.getTraspasoLibrePagoVO().setIdFolioCtaReceptora("010030030");
        params.getTraspasoLibrePagoVO().setTipoValor("D1");
        params.getTraspasoLibrePagoVO().setEmisora("UMS08F");
        params.getTraspasoLibrePagoVO().setSerie("2008F");
        params.getTraspasoLibrePagoVO().setCupon("0011");
        params.getTraspasoLibrePagoVO().setCantidadTitulos(Long.valueOf(10001));
        params.getTraspasoLibrePagoVO().setTipoInstruccion("T");
        params.getTraspasoLibrePagoVO().setBoveda("1");
        params.getTraspasoLibrePagoVO().setReferenciaOperacion("99991");
        params.getTraspasoLibrePagoVO().setReferenciaMensaje("88881");
        divisionInternacionalService.grabaOperacion(params);
        
        log.debug("Se gabo el registro exitosamente");
        
    }
    
}
