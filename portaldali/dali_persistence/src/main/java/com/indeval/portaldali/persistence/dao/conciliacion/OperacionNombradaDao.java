/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.dao.conciliacion;

import java.math.BigInteger;

import com.indeval.portaldali.persistence.vo.AgentePK;
import com.indeval.portaldali.persistence.vo.EmisionPK;
import com.indeval.portaldali.persistence.vo.PageVO;



/**
 * Interface que expone los m&eacute;todos para las operaciones realizadas sobre la tabla 
 * nombrada de registros contables de valores.
 * 
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 *
 */
public interface OperacionNombradaDao {
    
    /** Clave para colocar en el Map de la pagina la fecha fin ayer */
    public static final String FECHA_FIN_AYER = "FECHA";
    public static final String BOVEDA = "BOVEDA";
    public static final String MOVIMIENTOS = "MOV";
    public static final String DETALLES = "DET";

    /**
     * Realiza la consulta para los movimientos del archivo de conciliacion
     * @param agenteFirmado
     * @param emision
     * @param pageVO
     * @return PageVO
     */
    PageVO findMovimientosArchivoConciliacion(AgentePK agenteFirmado, EmisionPK emisionPK, PageVO pageVO);
    
    /**
     * Realiza la consulta para los detalles del archivo de conciliacion
     * @param idsPosicion
     * @param pageVO
     * @return PageVO
     */
    PageVO findDetalleArchivoConciliacion(BigInteger idsPosicion, PageVO pageVO);
    
    /**
     * Regresa el numero de titulos pertenecientes a operaciones nombradas
     * cuyo cupon tenga los datos de la emision extranjera
     * @param emisionPK Objeto con los datos de la emision extranjera
     * @return num de titulos
     */
    BigInteger findNumeroTitulosEmisionesExtranjeras(EmisionPK emisionPK);
    
}
