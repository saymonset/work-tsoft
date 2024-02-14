package com.indeval.portalinternacional.middleware.services.exito.divisioninternacional.controlbeneficiarios;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portalinternacional.middleware.services.BaseITestService;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService;

public class ITestActualizaStatusBeneficiario_1 extends BaseITestService{
	
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
    public void testActualizaStatusBeneficiario() throws ParseException {
		Date fechaValida = controlBeneficiariosService.obtieneFechaValida();
		log.info("Fecha Valida: [" + fechaValida + "]");

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Date fecha = sdf.parse("01-01-2005");
		log.info("Fecha: [" + sdf.format(fecha) + "] valida: [" + (fechaValida.compareTo(fecha) <= 0) + "]");

		fecha = sdf.parse("01-01-2006");
		log.info("Fecha: [" + sdf.format(fecha) + "] valida: [" + (fechaValida.compareTo(fecha) <= 0) + "]");

		fecha = sdf.parse("31-12-2005");
		log.info("Fecha: [" + sdf.format(fecha) + "] valida: [" + (fechaValida.compareTo(fecha) <= 0) + "]");

		fecha = sdf.parse("01-01-2008");
		log.info("Fecha: [" + sdf.format(fecha) + "] valida: [" + (fechaValida.compareTo(fecha) <= 0) + "]");

		fecha = sdf.parse("01-01-2009");
		log.info("Fecha: [" + sdf.format(fecha) + "] valida: [" + (fechaValida.compareTo(fecha) <= 0) + "]");


		fecha = sdf.parse("01-09-2006");
		log.info("Fecha: [" + sdf.format(fecha) + "] valida: [" + (fechaValida.compareTo(fecha) <= 0) + "]");

		fecha = sdf.parse("30-09-2006");
		log.info("Fecha: [" + sdf.format(fecha) + "] valida: [" + (fechaValida.compareTo(fecha) <= 0) + "]");

		fecha = sdf.parse("01-10-2006");
		log.info("Fecha: [" + sdf.format(fecha) + "] valida: [" + (fechaValida.compareTo(fecha) <= 0) + "]");

		fecha = sdf.parse("31-10-2006");
		log.info("Fecha: [" + sdf.format(fecha) + "] valida: [" + (fechaValida.compareTo(fecha) <= 0) + "]");

		fecha = sdf.parse("01-01-2010");
		log.info("Fecha: [" + sdf.format(fecha) + "] valida: [" + (fechaValida.compareTo(fecha) <= 0) + "]");

    }

}
