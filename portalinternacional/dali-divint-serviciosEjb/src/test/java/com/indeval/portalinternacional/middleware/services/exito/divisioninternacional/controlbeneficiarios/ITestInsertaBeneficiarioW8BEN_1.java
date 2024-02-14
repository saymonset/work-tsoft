package com.indeval.portalinternacional.middleware.services.exito.divisioninternacional.controlbeneficiarios;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

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

public class ITestInsertaBeneficiarioW8BEN_1 extends BaseITestService{
	
	private String[] nombres = {"FLOR",
		"MARIA",
		"AGUSTIN",
		"SANDRA",
		"ROLANDO",
		"JUAN",
		"GERARDO",
		"RICARDO",
		"ELIZABETH",
		"SUSANA",
		"JOSE",
		"FRANCISCO",
		"ROBERTO",
		"JUAN",
		"FRANCISCO",
		"ERIK",
		"ANGEL",
		"RICARDO",
		"JORGE",
		"MOISES",
		"GERMAN",
		"EDUARDO",
		"RAFAEL",
		"EMMANUEL",
		"LUIS",
		"LUCIO",
		"JUAN",
		"FABIO",
		"JORGE",
		"VICTOR",
		"JUAN",
		"FRANCISCO",
		"JORGE",
		"CLAUDIA",
		"RODOLFO",
		"RICARDO",
		"EDGAR",
		"MARCO",
		"MILDRED",
		"MARIA",
		"JORGE",
		"DANIEL",
		"MARTHA",
		"MARIA",
		"JESUS",
		"CARLOS",
		"HECTOR",
		"JOSE",
		"JOSE",
		"ISRAEL",
		"JOSE",
		"MATIAS",
		"RAFAEL",
		"ARTURO",
		"GRACIELA",
		"TERESA",
		"LUIS",
		"LUZ",
		"RAMIREZ",
		"MARTINEZ",
		"MEZA",
		"MORALES",
		"DAVILA",
		"HERNANDEZ",
		"ALBERTO",
		"JAVIER",
		"SALVADOR",
		"HUMBERTO",
		"AGUSTIN",
		"NAVARRETE",
		"LUIS",
		"MENDOZA",
		"ABEL",
		"ISRAEL",
		"ELOY",
		"DAVID",
		"IBARRA",
		"ABELARDO",
		"ROBERTO",
		"QUIRINO",
		"MANUEL",
		"ALFREDO",
		"ISAY",
		"GUERRERO",
		"CARLOS",
		"JAVIER",
		"CRUZ",
		"GALAN",
		"CHRISTIAN",
		"LARIOS",
		"OMAR",
		"ANTONIO",
		"KARINA",
		"DEL",
		"ALEJANDRO",
		"BARRIOS",
		"LETICIA",
		"CONCEPCION",
		"SALVADOR",
		"ROQUE",
		"RAUL",
		"ANTONIO",
		"ISRAEL",
		"RODRIGUEZ",
		"LUIS",
		"ALEJANDRO",
		"MARTINEZ",
		"CERON",
		"REYES",
		"GARCIA",
		"CASAS",
		"LOPEZ",
		"HERNANDEZ",
		"CANCHOLA",
		"GONZALEZ",
		"MENDIETA",
		"GUTIERREZ",
		"NIEVES",
		"GONZALEZ",
		"IBARRA",
		"OLGUIN",
		"CHAVEZ",
		"CALDERON",
		"SEVILLA",
		"RIVERA",
		"GOMEZ",
		"AYALA",
		"JARAMILLO",
		"CORTES",
		"MALDONADO",
		"ZENDEJAS",
		"ALVARADO",
		"MUÑOZ",
		"CRUZ",
		"MONJARAS",
		"IZQUIERDO",
		"MONTES",
		"OCAMPO",
		"GARCIA",
		"THOME",
		"VAZQUEZ",
		"ESPINOZA",
		"DIAZ",
		"CALVA",
		"PEREZ",
		"ROJAS",
		"RAMIREZ",
		"ROCIO",
		"ZAMUDIO",
		"ARCHUNDIA",
		"GALVAN",
		"BUENDIA",
		"RAMOS",
		"ROMERO",
		"GONZALEZ",
		"MARTINEZ",
		"GONZALEZ",
		"CORREA",
		"RODRIGUEZ",
		"BARRIGA",
		"MONTIEL",
		"LOPEZ",
		"ALVARADO",
		"FONSECA",
		"BECERRIL",
		"CASTELLANOS",
		"GARCIA",
		"CABALLERO",
		"LOZANO",
		"PARTIDA",
		"ORDUÑA",
		"LANDA",
		"MARENTES",
		"GOMEZ",
		"OCHOA",
		"MIRANDA",
		"MANDUJANO",
		"GONZALEZ",
		"RODRIGUEZ",
		"MONROY",
		"ORTEGA",
		"CRUZ",
		"CHAVEZ",
		"FLORES",
		"AVILA",
		"PEREZ",
		"HERRERA",
		"HERNANDEZ",
		"AGUILAR",
		"CERVANTES",
		"MOSCO",
		"CARDONA",
		"JUAREZ",
		"MARTELL",
		"PEREZ",
		"GARCIA",
		"CANTERO",
		"PEREZ"};
	
	 /** Servicio de log */
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	private static Random random = new Random(System.currentTimeMillis());

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
	
	public void testInsertaBeneficiarios() {
		for(int i = 0; i < 1000; i++) {
			log.info("Entrando a testInsertaBeneficiario(" + (i+1) + ")");
			insertaBeneficiario();
		}
	}

    private void insertaBeneficiario() {
    	
    	assertNotNull(controlBeneficiariosService);
    	Beneficiario beneficiario=new Beneficiario();
    	FormatoW8BEN forma = new FormatoW8BEN();
    	/* Se ponen valores a los campos del objeto */
    	TipoBeneficiario tipoBeneficiario=new TipoBeneficiario();
    	Institucion institucion= new Institucion();
    	
    	Field3W8BEN field3W8BEN = new Field3W8BEN();
    	field3W8BEN.setIdCampo(1l);
    	forma.setField3(field3W8BEN);
    	forma.setForeignTaxIdNumb("CIII894523");
    	forma.setField9OptionA(false);
    	forma.setField9OptionACountry("MEXICO");
    	forma.setField9OptionB(false);
    	forma.setField9OptionC(false);
    	forma.setField9OptionD(false);
    	forma.setField9OptionE(false);
    	forma.setCapacityActing("INDIVIDUAL");
    	forma.setField11(false);
    	forma.setDisabledDireccionPostal(true);
    	forma.setDisabledreferenceNumber(true);
    	forma.setDisabledUsIdNumber(true);
		
    	/* Campos del Beneficiario */
    	beneficiario.setNombres(nombres[random.nextInt(nombres.length)]);
    	beneficiario.setApellidoPaterno(nombres[random.nextInt(nombres.length)]);
    	beneficiario.setApellidoMaterno(nombres[random.nextInt(nombres.length)]);
    	beneficiario.setPersonaFisica(true);
    	beneficiario.setTipoFormato("W8BEN");
    	beneficiario.setFormatoW8BEN(forma);
    	beneficiario.setPaisIncorporacion("MEXICO");
    	
    	Domicilio domicilio = new Domicilio();
    	domicilio.setStreet("bAL");
    	domicilio.setOuterNumber("bAL");
    	domicilio.setPostalCode("bAL");
    	domicilio.setStateProvince("bAL");
    	domicilio.setCityTown("bAL");
    	domicilio.setCountry("bAL");
    	beneficiario.setDomicilioW8Normal(domicilio);
    	
    	tipoBeneficiario.setIdTipoBeneficiario(new Long(1));
    	
    	/* Campos de la Institucion */
    	/* Hay BusinessException si no se pone este campo */
    	institucion.setIdInstitucion(new Long(1));
	  
	    /* Campos del tipo de Beneficiario*/
	    
	    
	    /* Se insertan los objetos relacionados con Beneficiario */
	    List<Institucion> lista = new ArrayList<Institucion>();
	    lista.add(institucion);
//	    beneficiario.setInstitucion(lista);
	    beneficiario.setTipoBeneficiario(tipoBeneficiario);
	    beneficiario.setIdCuentaNombrada(4030l);

        beneficiario.setFechaFormato(new Date());
    	
//	    String numero = controlBeneficiariosService.obtieneCodigoBeneficiario(beneficiario);
	    
    	/* Se realiza la insercion */
    
    	controlBeneficiariosService.insertaBeneficiario(beneficiario,3l);
//    	log.info("Se grabo el registro exitosamente: [" + numero + "]");
    		
    }

}
