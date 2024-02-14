package com.indeval.portalinternacional.middleware.services.exito.divisioninternacional.controlbeneficiarios;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portalinternacional.middleware.services.BaseITestService;
import com.indeval.portalinternacional.middleware.services.util.HabilitaCamposFormatosBeneficiarios;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.vo.CamposFormatoW9;

public class ITestDefinirCamposHabilitadosW9_1 extends BaseITestService implements Constantes{
	
	/** Servicio de log */
	private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 
     *
     */
    public void testDefinirCamposHabilitadosW9() {
    	log.info("Entrando a testDefinirCamposHabilitadosW9()");
    	Long idCustodio= new Long(THE_BANK_OF_NEW_YORK_1);
    	Long idTipoBeneficiario= new Long(9);
    	CamposFormatoW9 camposFormatow9=
    		HabilitaCamposFormatosBeneficiarios.definirCamposBloqueadosW9(idCustodio, idTipoBeneficiario);
    	log.debug("CamposFormatoW9[" + ReflectionToStringBuilder.toString(camposFormatow9) + "]");
    	
    }

}
