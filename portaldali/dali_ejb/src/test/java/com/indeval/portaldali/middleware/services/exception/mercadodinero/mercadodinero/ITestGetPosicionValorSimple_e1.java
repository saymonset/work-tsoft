/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.exception.mercadodinero.mercadodinero;

import java.util.Arrays;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.BaseITestService;
import com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService;
import com.indeval.portaldali.middleware.services.mercadodinero.PosicionValoresSimpleTotalVO;
import com.indeval.portaldali.middleware.services.mercadodinero.PosicionValoresSimpleVO;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import com.indeval.portaldali.middleware.services.modelo.PaginaVO;
import com.indeval.portaldali.persistence.dao.common.DateUtilsDao;

/**
 * La clase ITestMercadoDineroService tiene como objetivo probar los
 * m&eacute;todos creados en la clase MercadoDineroService
 *
 * @author Agustin Calderon Orduña.
 * @author Claudia Becerril Rejon.
 * @author Erick Navarrete Sevilla.
 * @author Domingo Suarez Torres.
 * @author Jose Guadalupe Aviles.
 * @author Ricardo Caballero.
 * @author Sergio Mena Zamora.
 * @author Salvador Valeriano Lucas.
 */
public class ITestGetPosicionValorSimple_e1 extends BaseITestService {

    /** Objeto de loggeo de clase */
    private static final Logger logger = LoggerFactory.getLogger(ITestGetPosicionValorSimple_e1.class);

    /** Inyecci&oacute;n del bean mercadoDineroService */
    private MercadoDineroService mercadoDineroService;

    /** Bean de acceso a DateUtilsDao */
    private DateUtilsDao dateUtilsDao;

    /**
     * En el m&eacute;todo onSetUp inicializamos los beans que utilizaremos en
     * la clase de prueba.
     *
     * @throws Exception
     */
    protected void onSetUp() throws Exception {
        super.onSetUp();
        if (mercadoDineroService == null) {
            mercadoDineroService = (MercadoDineroService) applicationContext
                    .getBean("mercadoDineroService");
        }
        if (dateUtilsDao == null) {
            dateUtilsDao = (DateUtilsDao) applicationContext.getBean("dateUtilsDao");
        }
    }

    /**
     *
     *
     */
    public void testGetPosicionValorSimple() {
        
        log.info("Entrando a ITestGetPosicionValorSimple_1.testGetPosicionValorSimple()");

        AgenteVO agenteVO = new AgenteVO("01", "003");
        EmisionVO emisionVO = new EmisionVO();
        // emisionVO.setIdTipoValor(" 91 ");
        PaginaVO paginaVO = new PaginaVO();
        paginaVO.setOffset(new Integer(0));
        paginaVO.setRegistrosXPag(new Integer(500));
        try {
            PosicionValoresSimpleTotalVO totalVO[] = mercadoDineroService
                    .getPosicionValorSimple(agenteVO, emisionVO, paginaVO, true);
            assertNotNull(totalVO);
            for (int i = 0; i < totalVO.length; i++) {
                String[] tiposValor = totalVO[i].getTiposValor();
                paginaVO = totalVO[i].getPaginaVO();
                assertNotNull(paginaVO);
                log.debug("total registros " + paginaVO.getTotalRegistros());
                log.debug("tvs  " + Arrays.asList(tiposValor) + " ");
                log.debug("Total tiposValor " + tiposValor.length);
                log.debug("Sub Total " + totalVO[i].getSaldoDisponible());

                assertNotNull(paginaVO.getRegistros());
                assertFalse(paginaVO.getRegistros().isEmpty());
                for (Iterator iter = paginaVO.getRegistros().iterator(); iter
                        .hasNext();) {
                    PosicionValoresSimpleVO simpleVO = (PosicionValoresSimpleVO) iter
                            .next();
                    // log.debug(ReflectionToStringBuilder.reflectionToString(simpleVO));
                }

            }

        } catch (BusinessException ex) {
            log.debug("Error: " + ex.getMessage());
        }

    }

}