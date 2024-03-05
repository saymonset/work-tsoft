/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.modelo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.util.UtilServices;

/**
 * Implementacion de los servicios de BusinessRulesMercadoDinero
 * 
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class BusinessRulesMercadoDineroServiceImpl implements BusinessRulesMercadoDineroService {

    /** Log de clase. */
    private static final Logger logger = LoggerFactory.getLogger(BusinessRulesMercadoDineroServiceImpl.class);

    /** Bean de acceso a UtilService */
    private UtilServices utilService;   

    /*
     * NOTAS DE DESARROLLO:
     * exec bddinero..UP_altamdin @id1 @fol1 @cta1 @llave
     * @id2 @fol2 @cta2 @tv @emis @error @serie @cupon @cant_op @clave_reporto
     * @dias_plazo @fecha_reporto @precio_titulo @sociedad @tasa_premio
     * @baja_logica @fecha_liq @liq @mercado @origen_aplicac @origen @usuario
     * @area_trabajo @folio_descripc @divisa @folioc @fecha_concer @id_tasa_ref
     */
    /**
     * @see com.indeval.portaldali.middleware.services.modelo.BusinessRulesMercadoDineroService#altaMdintran(AltaMdintranParams)
     */
    public boolean altaMdintran(AltaMdintranParams params) throws BusinessException {

        logger.info("Entrando a BusinessRulesMercadoDineroServiceImpl.altaMdintran");

        //Se valida el objeto de parametros
        utilService.validaDTONoNulo(params, "");
        params.validaParams();
        
        boolean aplicacionBMVALGU = false;
        
        //Se determina la aplicacion en funcion del origen
        if("03".equalsIgnoreCase(params.getOrigen())){
            aplicacionBMVALGU = true;
        }
        
        return aplicacionBMVALGU;
    }

    /**
     * Inyeccion del bean utilService
     *
     * @param utilService
     */
    public void setUtilService(UtilServices utilService) {
        this.utilService = utilService;
    }
    
}
