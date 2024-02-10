/*
 * Copyright (c) 2017 S.D. Indeval. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;


/**
 * Nos permite hacer operaciones de transformar un xml a un tipo de objeto y
 * viceversa.
 */
public interface ParserAdaptadorService {

    /**
     * Method doConvertToXML.
     * @param object Object
     * @return String
     * @throws BusinessException
     */
    public String doConvertToXML(Object object) throws BusinessException;

    /**
     * Method doConvertFromXML.
     * @param object String
     * @return Object
     * @throws BusinessException
     */
    public Object doConvertFromXML(String object) throws BusinessException;

}
