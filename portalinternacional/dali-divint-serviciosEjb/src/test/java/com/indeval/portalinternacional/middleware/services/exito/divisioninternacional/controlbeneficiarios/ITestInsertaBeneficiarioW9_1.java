package com.indeval.portalinternacional.middleware.services.exito.divisioninternacional.controlbeneficiarios;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.persistence.modelo.Institucion;
import com.indeval.portalinternacional.middleware.services.BaseITestService;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService;
import com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.Domicilio;
import com.indeval.portalinternacional.middleware.servicios.modelo.Field3W9;
import com.indeval.portalinternacional.middleware.servicios.modelo.FormatoW9;
import com.indeval.portalinternacional.middleware.servicios.modelo.StatusBeneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.TipoBeneficiario;

public class ITestInsertaBeneficiarioW9_1 extends BaseITestService{
	
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
    	FormatoW9 forma = new FormatoW9();
    	/* Se ponen valores a los campos del objeto */
    	TipoBeneficiario tipoBeneficiario=new TipoBeneficiario();
    	Institucion institucion= new Institucion();
    	StatusBeneficiario statusBeneficiario=new StatusBeneficiario();
    	
    	Field3W9 field3W9 = new Field3W9();
    	field3W9.setIdCampo(1l);
    	forma.setTypeTaxPayer(field3W9);
    	forma.setSsn("014-01-1234");
    	forma.setExemptPayee(false);
    	
    	/* Campos del Beneficiario */
//    	beneficiario.setNombres("RAUL");
//    	beneficiario.setApellidoPaterno("ibarra");
//    	beneficiario.setApellidoMaterno("zende");
    	beneficiario.setRazonSocial("ABC S.A. de C.V.");
    	beneficiario.setPersonaFisica(false);
    	beneficiario.setTipoFormato("W9");
    	beneficiario.setFormatoW9(forma);
    	
    	Domicilio domicilio = new Domicilio();
    	domicilio.setStreet("bAL");
    	domicilio.setOuterNumber("bAL");
    	domicilio.setPostalCode("bAL");
    	domicilio.setStateProvince("bAL");
    	domicilio.setCityTown("bAL");
    	
    	beneficiario.setDomicilioW9(domicilio);
    	
    	/* Campos de la Institucion */
    	/* Hay BusinessException si no se pone este campo */
    	institucion.setIdInstitucion(new Long(1));
	  
	    /* Campos del tipo de Beneficiario*/
	    tipoBeneficiario.setIdTipoBeneficiario(new Long(1));
	    
	    /* Campos del StatusBeneficiario*/
	    statusBeneficiario.setIdStatusBenef(new Long(1));
	     
	    /* Se insertan los objetos relacionados con Beneficiario */
	    List<Institucion> lista = new ArrayList<Institucion>();
	    lista.add(institucion);
//	    beneficiario.setInstitucion(lista);
//	    beneficiario.setCatbic(catBic);
	    beneficiario.setTipoBeneficiario(tipoBeneficiario);
	    beneficiario.setStatusBenef(statusBeneficiario);
	    beneficiario.setIdCuentaNombrada(4032l);
	    
    	
    	/* Se realiza la insercion */
    	controlBeneficiariosService.insertaBeneficiario(beneficiario,institucion.getIdInstitucion());
    	log.info("Se grabo el registro exitosamente");
    		
    }

}
