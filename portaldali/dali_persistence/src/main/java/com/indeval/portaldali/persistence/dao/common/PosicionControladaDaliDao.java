/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.dao.common;

import java.util.List;

import com.bursatec.persistence.dao.BaseDao;
import com.indeval.portaldali.persistence.vo.PageVO;
import com.indeval.portaldali.persistence.vo.TPosicionControladaParamsPersistence;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public interface PosicionControladaDaliDao extends BaseDao {

    /**
     * @param tPosicionControladaParamsPersistence
     * @return PageVO - contiene una lista de Object[]
     */
    PageVO getVencimientosPendientesByInstitucionFechaVencimiento(
            TPosicionControladaParamsPersistence tPosicionControladaParamsPersistence);
    
    /**
     * @param tPosicionControladaParamsPersistence
     * @return PageVO - contiene una lista de Object[]
     */
    PageVO getVencimientosPendientesPorInstitucionFechaVencimientoAgrupadoPorEmision(
            TPosicionControladaParamsPersistence tPosicionControladaParamsPersistence);
    
	// CGM: Al parecer los metodos de aca arriba no son utilizados por nadie, de
	// modo que se recomienda se eliminen, pues cuando se ha cambiado a
	// multibodeva y mutidivisa, se han tenido que reimplementar desde cero.

	/**
	 * Debido a que se seleccionan campos especificos de la entidad
	 * PosicionControlada, se deja como tarea al cliente (service) el armado de
	 * los VOs a partir de los datos 'crudos' devueltos por esta consulta.
	 * 
     * @param tPosicionControladaParamsPersistence
	 * @return List<Object[]> Cada elemento de la lista corresponde a un registro
	 *         con los siguientes campos:
	 *         <p>
	 *         0 - Suma de posiciones <br>
	 *         1 - CveTipoValor (posicion.emision.instrumento.claveTipoValor)<br>
	 *         2 - Nombre Emisora (posicion.emision.emisora.clavePizarra)<br>
	 *         3 - Serie (posicion.emision.serie)<br>
	 *         4 - Nombre Boveda de la Posicion (posicion.boveda.nombreCorto)<br>
	 *         5 - Nombre Boveda de la Emision
	 *         (posicion.emision.boveda.nombreCorto)<br>
	 *         6 - Cupon (posicion.cupon.claveCupon)<br>
	 */
	List<Object[]> getVencsPendientesByInstitucionFechaVencimiento(
            TPosicionControladaParamsPersistence tPosicionControladaParamsPersistence);

    
}
