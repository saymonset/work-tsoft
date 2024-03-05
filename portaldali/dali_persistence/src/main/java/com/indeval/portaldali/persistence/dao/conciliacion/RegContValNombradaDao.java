/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.dao.conciliacion;

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
public interface RegContValNombradaDao {
    
    /** Clave para colocar en el Map de la pagina la fecha fin ayer */
    public static final String FECHA_FIN_AYER = "FECHA";

    /**
     * Realiza la consulta para el archivo de conciliacion
     * @param agenteFirmado
     * @param emision
     * @param pageVO
     * @return PageVO
     */
    PageVO findArchivoConciliacion(AgentePK agenteFirmado, EmisionPK emisionPK, PageVO pageVO);
    
}
