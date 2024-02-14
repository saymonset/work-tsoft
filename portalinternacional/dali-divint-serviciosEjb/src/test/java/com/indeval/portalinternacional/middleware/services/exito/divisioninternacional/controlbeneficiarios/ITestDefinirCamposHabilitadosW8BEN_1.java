package com.indeval.portalinternacional.middleware.services.exito.divisioninternacional.controlbeneficiarios;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portalinternacional.middleware.services.BaseITestService;
import com.indeval.portalinternacional.middleware.services.util.HabilitaCamposFormatosBeneficiarios;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.vo.CamposFormatoW8BEN;

public class ITestDefinirCamposHabilitadosW8BEN_1 extends BaseITestService implements Constantes{
	
	/** Servicio de log */
	private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 
     *
     */
    public void testDefinirCamposHabilitadosW8BEN() {
    	log.info("Entrando a testDefinirCamposHabilitadosW8BEN()");
    	Long idCustodio= new Long(CLEARSTREAM);
    	Long idTipoBeneficiario= new Long(PERSONA_FISICA_NO_EUA);
    	CamposFormatoW8BEN camposFormatow8ben=
    		HabilitaCamposFormatosBeneficiarios.definirCamposBloqueadosW8BEN(idCustodio, idTipoBeneficiario);
    	log.debug("CamposFormatoW8BEN [" + ReflectionToStringBuilder.toString(camposFormatow8ben) + "]");
    	System.out.println("CamposFormatoW8BEN [" + ReflectionToStringBuilder.toString(camposFormatow8ben) + "]");
    	
    }

}
