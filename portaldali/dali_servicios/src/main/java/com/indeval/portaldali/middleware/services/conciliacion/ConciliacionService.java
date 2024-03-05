/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.conciliacion;

import java.math.BigInteger;

import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import com.indeval.portaldali.middleware.services.modelo.PaginaVO;

/**
 * Define la funcionalidad del servicio de conciliacion.
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 * @version 1.0
 */
public interface ConciliacionService {

    /** Constante para indicar el id de la Boveda como filtro */
    public static final String BOVEDA = "BOVEDA";
    
    /** Constante para utilizar como Key en el Map de la PaginaVO e indicar que se requieren 
     * solo los Movimientos */
    public static final String MOVIMIENTOS = "MOV";
    
    /** Constante para utilizar como Key en el Map de la PaginaVO e indicar que se requieren 
     * solo los Detalles */
    public static final String DETALLES = "DET";

    /**
     * Realiza la consulta del archivo de conciliaci&oacute;n
     * @param agenteFirmado
     * @param emision
     * @param pagina
     * @return PaginaVO
     * @throws BusinessException
     */
    PaginaVO archivoConciliacion(AgenteVO agenteFirmado, EmisionVO emision, PaginaVO pagina, Boolean debeDejarLog)
            throws BusinessException;

    
    /**
     * Metodo que regresa la posicion inicial de una emision.
     * 
     * @param agenteFirmado
     * @param emision
     * @param bovedaId
     * @return la posicion inicial
     */
    public BigInteger getPosicionInicial( AgenteVO agenteFirmado, EmisionVO emision, BigInteger bovedaId );
}
