package com.indeval.portalinternacional.middleware.services.exito.divisioninternacional.controlbeneficiarios;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portalinternacional.middleware.services.BaseITestService;
import com.indeval.portalinternacional.middleware.services.util.HabilitaCamposFormatosBeneficiarios;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.vo.CamposFormatoW8IMY;

public class ITestDefinirCamposHabilitadosW8IMY_1 extends BaseITestService implements Constantes{
	
	/** Servicio de log */
	private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 
     *
     */
    public void testDefinirCamposHabilitadosW8IMY() {
    	log.info("Entrando a testDefinirCamposHabilitadosW8IMY()");
    	Long idCustodio= new Long(THE_BANK_OF_NEW_YORK_1);
    	Long idTipoBeneficiario= new Long(FIDEICOMISO_SIMPLE);
    	CamposFormatoW8IMY camposFormatow8imy=
    		HabilitaCamposFormatosBeneficiarios.definirCamposBloqueadosW8IMY(idCustodio, idTipoBeneficiario);
    	log.debug("CamposFormatoW8IMY [" + ReflectionToStringBuilder.toString(camposFormatow8imy) + "]");
    	System.out.println("CamposFormatoW8IMY [" + ReflectionToStringBuilder.toString(camposFormatow8imy) + "]");
    	
    }

}
