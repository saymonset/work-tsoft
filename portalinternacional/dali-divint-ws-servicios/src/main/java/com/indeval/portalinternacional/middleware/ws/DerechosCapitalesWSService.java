package com.indeval.portalinternacional.middleware.ws;

import javax.jws.WebParam;
import javax.jws.WebService;

import com.indeval.sidv.ejercicios.derechos.patrimoniales.dto.SimulacionDerechoInternacionalDto;

/**
 * Web Service para servicios de Derechos de Capital.
 */
@WebService(name="", targetNamespace="")
public interface DerechosCapitalesWSService {

    /**
     * Metodo que recibe una peticion de realizar la simulacion de un derecho capital.
     * @param dto
     */
    boolean doCalculaSimulacionDerechoCapitalInter(@WebParam(name = "dto") SimulacionDerechoInternacionalDto dto);

}
