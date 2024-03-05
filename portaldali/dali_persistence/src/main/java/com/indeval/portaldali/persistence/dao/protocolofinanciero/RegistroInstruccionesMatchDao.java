/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.dao.protocolofinanciero;

import java.math.BigInteger;

import com.bursatec.persistence.dao.BaseDao;
import com.indeval.portaldali.persistence.model.protocolofinanciero.RegistroInstruccionesMatch;

/**
 * Interface de los m&eacute;todos de acceso a datos para la entidad RegistroInstruccionesMatchDao.
 * @author salvador
 *
 */
public interface RegistroInstruccionesMatchDao extends BaseDao {

    /**
     * Recupera un objeto RegistroInstruccionesMatch dada su PK.
     * @param idBitacoraMatch
     * @return RegistroInstruccionesMatch
     */
    RegistroInstruccionesMatch getByPK(BigInteger idBitacoraMatch);

    /**
     * Actualiza el estatus de confirmaci&oacute;n de una operaci&oacute;n match.
     * @param idBitacoraMatch
     * @return int
     */
    int updateStatusConfirmacion(BigInteger idBitacoraMatch);

}
