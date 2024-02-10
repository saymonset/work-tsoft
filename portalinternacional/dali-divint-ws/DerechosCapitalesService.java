package com.indeval.portalinternacional.middleware.ws;

import javax.jws.WebParam;
import javax.jws.WebService;

import com.indeval.sidv.ejercicios.derechos.patrimoniales.dto.SimulacionDerechoInternacionalDto;

/**
 * Web Service para servicios de Derechos de Capital.
 */
@WebService
public interface DerechosCapitalesService {

    void doCalculaSimulacionDerechoCapitalInter(@WebParam(name = "dto") SimulacionDerechoInternacionalDto dto);

}
