package com.indeval.portalinternacional.middleware.services.exito.divisioninternacional.controlbeneficiarios;


import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portalinternacional.middleware.services.BaseITestService;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService;
import com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.Domicilio;
import com.indeval.portalinternacional.middleware.servicios.modelo.Field3W9;
import com.indeval.portalinternacional.middleware.servicios.modelo.FormatoW9;
import com.indeval.portalinternacional.middleware.servicios.modelo.TipoBeneficiario;

public class ITestInsertaBeneficiarioW9 extends BaseITestService {

    /** Servicio de log */
	private final Logger log = LoggerFactory.getLogger(this.getClass());
    /** Servicio que sera probado en esta prueba */
    private ControlBeneficiariosService controlBeneficiariosService;

    /**
     * @see com.indeval.portalinternacional.portalinternacional.services.BaseITestService#onSetUp()
     */
    protected void onSetUp() throws Exception {
        super.onSetUp();
        if (controlBeneficiariosService == null) {
            controlBeneficiariosService = (ControlBeneficiariosService) applicationContext.getBean("controlBeneficiariosService");
        }
    }

    /**
     * 
     *
     */
    public void testInsertaBeneficiario() {
        log.info("Entrando a testInsertaBeneficiario()");
        assertNotNull(controlBeneficiariosService);
        Beneficiario beneficiario = new Beneficiario();
        FormatoW9 forma = new FormatoW9();

        Field3W9 field3W9 = new Field3W9();
        field3W9.setIdCampo(1l);
        forma.setTypeTaxPayer(field3W9);
        forma.setSsn("123-45-6789");

        /* Campos del Beneficiario */
        beneficiario.setRazonSocial("PATITO");
        beneficiario.setPersonaFisica(false);
        beneficiario.setTipoFormato("W9");
        beneficiario.setFormatoW9(forma);
        beneficiario.setPaisIncorporacion("USA");
        beneficiario.setFechaFormato(new Date());

        Domicilio domicilio = new Domicilio();
        domicilio.setStreet("bAL");
        domicilio.setOuterNumber("bAL");
        domicilio.setPostalCode("bAL");
        domicilio.setStateProvince("bAL");
        domicilio.setCityTown("bAL");
        beneficiario.setDomicilioW9(domicilio);

        TipoBeneficiario tipoBeneficiario = new TipoBeneficiario();
        tipoBeneficiario.setIdTipoBeneficiario(new Long(1));
        beneficiario.setTipoBeneficiario(tipoBeneficiario);

        beneficiario.setIdCuentaNombrada(4032l);


        String numero = controlBeneficiariosService.obtieneCodigoBeneficiario(beneficiario);
//    	controlBeneficiariosService.insertaBeneficiario(beneficiario,3l);
        log.info("Se grabo el registro exitosamente: [" + numero + "]");
//        log.info("Se grabo el registro exitosamente");
    }
}
