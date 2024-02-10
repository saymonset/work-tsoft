package com.indeval.portalinternacional.middleware.ws.service;

import com.indeval.sidv.ejercicios.derechos.patrimoniales.dto.SimulacionDerechoInternacionalDto;

/**
 * Servicios de Capitales.
 */
public interface CapitalesService {
    
    /**
     * Realiza el calculo de la simulacion de los derechos de capital internacional.
     * @param dto
     * @throws Exception
     */
    void doCalculaSimulacionDerechoCapitalInter(SimulacionDerechoInternacionalDto dto) throws Exception;

}
