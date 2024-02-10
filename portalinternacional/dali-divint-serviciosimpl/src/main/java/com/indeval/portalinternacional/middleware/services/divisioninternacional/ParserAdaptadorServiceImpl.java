/*
 * Copyright (c) 2017 S.D. Indeval. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.basic.DateConverter;

/**
 * Implementaci&oacute;n de la interfaz ParserAdaptadorService con xstream.
 * 
 * @author Jose Aviles
 */
public class ParserAdaptadorServiceImpl implements ParserAdaptadorService {

    /**
     * Objeto utilizado para almacenar mensajes en el log
     */
	private static final Logger log = LoggerFactory.getLogger(ParserAdaptadorServiceImpl.class);

    /**
     * Interpreta las instrucciones generadas por el SLV
     */
    private XStream xstream;

    private void doRgisterDateConverter() {
        getXstream().registerConverter(
                new DateConverter("yyyy-MM-dd KK:mm:ss.S a", new String[]{
                    "yyyy-MM-dd KK:mm:ss.S a"
                }));
    }

    public String doConvertToXML(Object object) throws BusinessException {
        if(log.isDebugEnabled()) log.debug("Entra a doConvertToXML");
        try {
            return getXstream().toXML(object);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessException(e.getMessage(), e);
        }
    }

    public Object doConvertFromXML(String object) throws BusinessException {
        if(log.isDebugEnabled()) log.debug("Entra a doConvertFromXML");
        try {
            return getXstream().fromXML(object);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessException(e.getMessage(), e);
        }
    }

    /**
     * @return the xstreamSLV
     */
    public XStream getXstream() {
        return xstream;
    }

    /**
     * @param xstreamSLV the xstreamSLV to set
     */
    public void setXstream(XStream xstreamSLV) {
        this.xstream = xstreamSLV;
        doRgisterDateConverter();
    }

}
