package com.indeval.portalinternacional.middleware.ws.impl;

import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portalinternacional.middleware.ws.DerechosCapitalesWSService;
import com.indeval.portalinternacional.middleware.ws.service.CapitalesService;
import com.indeval.sidv.ejercicios.derechos.patrimoniales.dto.SimulacionDerechoInternacionalDto;

/**
 * Implementacion del Web Service para servicios de Derechos de Capital.
 */
@WebService(endpointInterface = "com.indeval.portalinternacional.middleware.ws.DerechosCapitalesWSService")
public class DerechosCapitalesWSServiceImpl implements DerechosCapitalesWSService {

    /**
     * Log de clase
     */
	private static final Logger LOG = LoggerFactory.getLogger(DerechosCapitalesWSServiceImpl.class);

    /**
     * Servicio CapitalesService
     */
    private CapitalesService capitalesService;

    /**
     * @see com.indeval.portalinternacional.middleware.ws.DerechosCapitalesService#doCalculaSimulacionDerechoCapitalInter(SimulacionDerechoInternacionalDto)
     */
    @Override
    public boolean doCalculaSimulacionDerechoCapitalInter(SimulacionDerechoInternacionalDto dto) {
        LOG.info("####### Entrando a WS DerechosCapitalesWSServiceImpl.doCalculaSimulacionDerechoCapitalInter() ...");
        try {
            this.capitalesService.doCalculaSimulacionDerechoCapitalInter(dto);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("####### Error al enviar la simulacion a MAV...", e);
            return false;
        }
    }

    public void setCapitalesService(CapitalesService capitalesService) {
        this.capitalesService = capitalesService;
    }

}
