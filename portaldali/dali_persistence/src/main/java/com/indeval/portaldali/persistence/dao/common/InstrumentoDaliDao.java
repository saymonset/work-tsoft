/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.dao.common;

import java.util.List;

import com.bursatec.persistence.dao.BaseDao;
import com.indeval.portaldali.persistence.model.Instrumento;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public interface InstrumentoDaliDao extends BaseDao {
	
    /**
     * Regresa una Lista con todos los Instrumentos
     * @return List
     */
    List getListaInstrumentos();
    
    /**
     * Regresa un objeto de la clase Instrumento correspondiente
     * al tipo de valor recibido
     *
     * @param tv 
     * @return Instrumento
     */
    Instrumento getInstrumento(String tv);
    
    /**
     * Regresa un objeto de la clase Instrumento correspondiente
     * al tipo de valor recibido. Permite filtrar para obtener 
     * los instrumentos solo de un mercado.
     * 
     * @param tv
     * @param mercado  "MC" - > Mercado de Capitales | "PD"- > Mercado de Dinero
     * @return Instrumento
     */
    Instrumento getInstrumento(String tv, String mercado);

}
