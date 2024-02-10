/*
 *Copyright (c) 2005-2007 Bursatec. All Rights Reserved
 */
package com.indeval.portalinternacional.common.util;

import org.apache.commons.lang.StringUtils;

import com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO;

/**
 * Clase utilitatia para descomponer el idFolioAgente a id y folio del agente en dos cadenas
 * 
 * @author Erik Vera Montoya
 * @version 1.0
 */
public class AgenteViewHelper {

    /**
     * método para obtener el idFolio a partir de el id y del folio
     * 
     * @param idInstitucion
     * @param folioInstitucion
     * @return idFolioInstitucion
     */
    public static String obtenerClaveTipoInstitucionYFolio(String idInstitucion, String folioInstitucion) {
        return new StringBuffer().append(idInstitucion).append(folioInstitucion).toString();
    }

    /**
     * método que obtiene el id a partir del idFolioInstitucion
     * 
     * @param idFolioInstitucion
     * @return idInstitucion
     */
    public static String obtenerIdInstitucion(String idFolioInstitucion) {
        return StringUtils.substring(idFolioInstitucion, 0, 2);
    }

    /**
     * método que obtiene el folioInstitucion a partir del idFolioInstitucion
     * 
     * @param idFolioInstitucion
     * @return folioInstitucion
     */
    public static String obtenerFolioInstitucion(String idFolioInstitucion) {
        return StringUtils.substring(idFolioInstitucion, 2, 5);
    }

    /**
     * Crea un objeto AgenteVO a partir de el id-folio y el número de cuenta
     * 
     * @param idFolio Id y Folio del agente
     * @param cuenta N&acute;mero de cuenta
     * @return AgenteVO creado
     */
    public static AgenteVO crearAgenteVO(String idFolio, String cuenta) {
        AgenteVO agente = new AgenteVO();
        agente.setId(obtenerIdInstitucion(idFolio));
        agente.setFolio(obtenerFolioInstitucion(idFolio));
        agente.setCuenta(cuenta);
        return agente;
    }

}
