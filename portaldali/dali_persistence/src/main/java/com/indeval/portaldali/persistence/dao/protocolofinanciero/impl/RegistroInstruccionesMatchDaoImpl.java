/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.dao.protocolofinanciero.impl;

import java.math.BigInteger;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portaldali.persistence.dao.protocolofinanciero.RegistroInstruccionesMatchDao;
import com.indeval.portaldali.persistence.model.protocolofinanciero.RegistroInstruccionesMatch;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class RegistroInstruccionesMatchDaoImpl extends BaseDaoHibernateImpl
    implements RegistroInstruccionesMatchDao {

    /** Objeto de loggeo para RegistroInstruccionesMatchDaoImpl */
    private static final Logger logger = LoggerFactory.getLogger(RegistroInstruccionesMatchDaoImpl.class);

    private static final String EXPIRADA = "S";
    
    /**
     * @see com.indeval.persistence.portallegado.protocolofinanciero.dao.RegistroInstruccionesMatchDao#getByPK(java.math.BigInteger)
     */
    public RegistroInstruccionesMatch getByPK(BigInteger idBitacoraMatch) {

        logger.info("Entrando a RegistroInstruccionesMatchDaoImpl.getByPK()");

        return (RegistroInstruccionesMatch) this.getHibernateTemplate()
                                                .get(RegistroInstruccionesMatch.class,
                                                     idBitacoraMatch);

    }

    /**
     * 
     * @see com.indeval.persistence.portallegado.protocolofinanciero.dao.RegistroInstruccionesMatchDao#updateStatusConfirmacion(java.math.BigInteger)
     */
//    public int updateStatusConfirmacion(final BigInteger idBitacoraMatch) {
//
//    	log.info("Entrando a RegistroInstruccionesMatchDaoImpl.updateStatusConfirmacion()");
//
//    	int resultado = 0;
//
//    	final RegistroInstruccionesMatch match = this.getByPK(idBitacoraMatch);
//
//    	if(match == null 
//    			&& StringUtils.isNotBlank(match.getConfirmacion()) 
//    			&& StringUtils.isNotBlank(match.getExpira()) 
//    			&& !match.getExpira().equalsIgnoreCase("S")){
//    		resultado = 0;
//    	}
//    	else{
//    		match.setConfirmacion("EN_PROCESO");
//    	}
//    	if(this.getHibernateTemplate().merge(match) != null){
//    		resultado = 1;
//    	}
//    	return resultado;
//    }
    
    /**
     * 
     * @see com.indeval.persistence.portallegado.protocolofinanciero.dao.RegistroInstruccionesMatchDao#updateStatusConfirmacion(java.math.BigInteger)
     */
    public int updateStatusConfirmacion(final BigInteger idBitacoraMatch) {

        logger.info("Entrando a RegistroInstruccionesMatchDaoImpl.updateStatusConfirmacion()");

        int resultado = 0;

        final RegistroInstruccionesMatch match = this.getByPK(idBitacoraMatch);

        if(match != null 
                && StringUtils.isNotBlank(match.getExpira()) 
                && !EXPIRADA.equalsIgnoreCase(match.getExpira())){

            /* Se recupera el valor de la columna confirmacion y se incrementa en 1*/
            Integer confirmacion = Integer.valueOf(0);
            try {
                confirmacion = Integer.parseInt(match.getConfirmacion() == null ? 
                        "0" : match.getConfirmacion());
            }
            catch (NumberFormatException e) {
                logger.info("El valor del campo confirmacion no es numerico : [" + e.getMessage() + "]");
                e.printStackTrace(); // Si el valor no es un numero, el mismo se deja en 0.
            }
            ++confirmacion;
            match.setConfirmacion(confirmacion.toString());
        }
        if(this.getHibernateTemplate().merge(match) != null){
            resultado = 1;
        }
        return resultado;
    }
    
}
