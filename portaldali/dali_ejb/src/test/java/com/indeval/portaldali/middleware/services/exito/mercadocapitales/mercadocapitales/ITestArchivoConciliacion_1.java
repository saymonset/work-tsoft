/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.exito.mercadocapitales.mercadocapitales;


import java.util.Iterator;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.BaseITestService;
import com.indeval.portaldali.middleware.services.mercadocapitales.ArchivoConciliacionVO;
import com.indeval.portaldali.middleware.services.mercadocapitales.MercadoCapitalesService;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import com.indeval.portaldali.middleware.services.modelo.PaginaVO;

/**
 * Clase de prueba para el servicio de Garantias.
 *
 * @author Igor Mejia
 */
public class ITestArchivoConciliacion_1 extends BaseITestService {

    /**
     * Servicio a probar.
     */
    private MercadoCapitalesService mercadoCapitales;

    /** Log de bitacora */
    Logger logger = LoggerFactory.getLogger(ITestArchivoConciliacion_1.class);

    /**
     * Inicializa lo que la prueba necesita para su ejecucion.
     */
    protected void onSetUp() throws Exception {
        super.onSetUp();
        if (mercadoCapitales == null) {
            mercadoCapitales = (MercadoCapitalesService) applicationContext
                    .getBean("mercadoCapitalesService");
        }
    }


    /**
     * Prueba el archivo de conciliacion para un agente firmado (id, folio,
     * cuenta)
     * Con todos los parametros llenos
     * @throws BusinessException
     */
    public void testArchivoConciliacion_1() throws BusinessException {

        log.debug("Entrando al metodo testArchivoConciliacion_1()");

        assertNotNull(mercadoCapitales);

        log.debug("Probando con agente - id, folio, cuenta");

        AgenteVO agenteVO = new AgenteVO();
        agenteVO.setId("01");
        agenteVO.setFolio("003");
        PaginaVO paginaVO = new PaginaVO();
        EmisionVO emisionVO = new EmisionVO();

        PaginaVO pagina = mercadoCapitales.archivoConciliacion(agenteVO,
        		emisionVO, paginaVO);
        assertNotNull(pagina);
        assertNotNull(pagina.getRegistros());
        assertFalse(pagina.getRegistros().isEmpty());

        for (Iterator iter = pagina.getRegistros().iterator(); iter
        .hasNext();) {
        	ArchivoConciliacionVO element = (ArchivoConciliacionVO) iter
        	.next();
        	log
        	.debug("Registro: "
        			+ ReflectionToStringBuilder
        			.reflectionToString(element));
        }
        log.debug("Total de registros: " + pagina.getTotalRegistros());

        log.debug("Saliendo del metodo testEJBArchivoConciliacion()");

    }





}
