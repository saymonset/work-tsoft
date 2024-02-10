package com.indeval.portalinternacional.middleware.ws.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portalinternacional.middleware.ws.service.CapitalesService;
import com.indeval.sidv.ejercicios.derechos.patrimoniales.dto.SimulacionDerechoInternacionalDto;
import com.indeval.sidv.ejercicios.derechos.patrimoniales.middleware.ejb.Capital;

/**
 * Servicio que realiza la invocacion los metodos de los EJB de Capitales.
 */
public class CapitalesServiceImpl implements CapitalesService {

    /**
     * Log de clase
     */
	private static final Logger LOG = LoggerFactory.getLogger(CapitalesServiceImpl.class);

    /**
     * Instancia del EJB CapitalBean de Ejercicios MAV.
     */
    private Capital capitalEJB; 

    /**
     * @see com.indeval.portalinternacional.middleware.ws.service.CapitalesService#doCalculaSimulacionDerechoCapitalInter(SimulacionDerechoInternacionalDto)
     */
    @Override
    public void doCalculaSimulacionDerechoCapitalInter(SimulacionDerechoInternacionalDto dto) throws Exception {
        LOG.info("####### Entrando a WS CapitalesServiceImpl.doCalculaSimulacionDerechoCapitalInter() ...");
        this.capitalEJB.doCalculaSimulacionDerechoCapitalInter(dto);
    }

    public void setCapitalEJB(Capital capitalEJB) {
        this.capitalEJB = capitalEJB;
    }

}
