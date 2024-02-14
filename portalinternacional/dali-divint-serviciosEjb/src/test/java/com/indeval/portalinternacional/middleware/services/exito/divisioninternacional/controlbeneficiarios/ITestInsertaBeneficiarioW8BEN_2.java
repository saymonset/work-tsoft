package com.indeval.portalinternacional.middleware.services.exito.divisioninternacional.controlbeneficiarios;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.persistence.modelo.Institucion;
import com.indeval.portalinternacional.middleware.services.BaseITestService;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService;
import com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.Domicilio;
import com.indeval.portalinternacional.middleware.servicios.modelo.Field3W8BEN;
import com.indeval.portalinternacional.middleware.servicios.modelo.FormatoW8BEN;
import com.indeval.portalinternacional.middleware.servicios.modelo.TipoBeneficiario;

public class ITestInsertaBeneficiarioW8BEN_2 extends BaseITestService{
	
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
        	controlBeneficiariosService = (ControlBeneficiariosService) applicationContext
                    .getBean("controlBeneficiariosService");
        }
    }
    
    /**
     * 
     *
     */
    public void testInsertaBeneficiario() {
    	log.info("Entrando a testInsertaBeneficiario()");
    	assertNotNull(controlBeneficiariosService);
    	Beneficiario beneficiario=new Beneficiario();
    	FormatoW8BEN forma = new FormatoW8BEN();
    	/* Se ponen valores a los campos del objeto */
    	TipoBeneficiario tipoBeneficiario=new TipoBeneficiario();
    	Institucion institucion= new Institucion();
    	
    	Field3W8BEN field3W8BEN = new Field3W8BEN();
    	field3W8BEN.setIdCampo(2l);
    	forma.setField3(field3W8BEN);
    	forma.setForeignTaxIdNumb("IAZR820909");
    	forma.setField9OptionA(false);
    	forma.setField9OptionACountry("MEXICO");
    	forma.setField9OptionB(false);
    	forma.setField9OptionC(false);
    	forma.setField9OptionD(false);
    	forma.setField9OptionE(false);
    	forma.setCapacityActing("C.O.");
    	forma.setField11(false);
    	forma.setDisabledDireccionPostal(true);
    	forma.setDisabledreferenceNumber(true);
    	forma.setDisabledUsIdNumber(true);
    	
    	/* Campos del Beneficiario */
    	beneficiario.setRazonSocial("RIZCDF123123");
    	beneficiario.setPersonaFisica(false);
    	beneficiario.setTipoFormato("W8BEN");
    	beneficiario.setFormatoW8BEN(forma);
    	beneficiario.setPaisIncorporacion("MEXICO");
    	beneficiario.setFechaFormato(new Date());
    	
    	Domicilio domicilio = new Domicilio();
    	domicilio.setStreet("bAL");
    	domicilio.setOuterNumber("bAL");
    	domicilio.setPostalCode("bAL");
    	domicilio.setStateProvince("bAL");
    	domicilio.setCityTown("bAL");
    	domicilio.setCountry("bAL");
    	beneficiario.setDomicilioW8Normal(domicilio);
    	
    	tipoBeneficiario.setIdTipoBeneficiario(new Long(2));
    	
    	/* Campos de la Institucion */
    	/* Hay BusinessException si no se pone este campo */
    	institucion.setIdInstitucion(new Long(1));
	  
	    /* Campos del tipo de Beneficiario*/
	    
	    
	    /* Se insertan los objetos relacionados con Beneficiario */
	    List<Institucion> lista = new ArrayList<Institucion>();
	    lista.add(institucion);
//	    beneficiario.setInstitucion(lista);
	    beneficiario.setTipoBeneficiario(tipoBeneficiario);
	    beneficiario.setIdCuentaNombrada(4032l);
	    
    	/* Se realiza la insercion */
    	controlBeneficiariosService.insertaBeneficiario(beneficiario,3l);
    	log.info("Se grabo el registro exitosamente");
    		
    }

}
