/*
 * Copyrigth (c) 2007-2007 S.D. Indeval. All Rigths Reserved.
 */
package com.indeval.portaldali.persistence.dao.protocolofinanciero.impl.support.configuration;

import com.indeval.portaldali.persistence.dao.protocolofinanciero.impl.support.MatchException;


/**
 * Interfaz que deben implementar todos los componentes del m&oacute;dulo match que guarden informaci&oacute;n en memoria. Sirve
 * princpialmente para poder refrescar los datos y cargarlos nuevamente desde la consola o al inicio de d&iacute;a.
 *
 * @author Alejandro Rodr&iacute;guez Consejo
 */
public interface CacheStoreMatch {

    /**
     * Regresa el nombre del Cache. Este nombre deber&aacute; ser &uacute;nico para cada uno de los componentes
     *
     * @return Nombre del cache
     */
    String getName();

    /**
     * Es invocado para limpiar los datos en memoria del cache.
     *
     * @throws MatchException -
     *             El componente que implemente este m&eacute;todo podr&aacute; indicar con este tipo de excepci&oacute;n
     *             cualquier error que ocurrar al intentar refrescar los datos.
     */
    void refresh() throws MatchException;
}
