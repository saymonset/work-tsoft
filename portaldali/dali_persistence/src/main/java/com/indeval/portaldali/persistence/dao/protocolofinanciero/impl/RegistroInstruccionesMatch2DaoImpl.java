/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.dao.protocolofinanciero.impl;

import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.util.Assert;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portaldali.persistence.dao.protocolofinanciero.RegistroInstruccionesMatch2Dao;
import com.indeval.portaldali.persistence.model.protocolofinanciero.RegistroInstruccionesMatch2;
import com.indeval.portaldali.persistence.vo.AgentePK;
/**
 * Clase encarga de realizar la actualizacion y obtencion sobre 
 * la tabla T_REGISTRO_INSTRUCCIONES_MATCH
 * @author Sergio Mena
 *
 */
public class RegistroInstruccionesMatch2DaoImpl extends BaseDaoHibernateImpl implements
		RegistroInstruccionesMatch2Dao {
	
	 /** Log de clase. */
    private static final Logger logger = LoggerFactory
            .getLogger(RegistroInstruccionesMatch2DaoImpl.class);

    private static final String ESTADO_INSTRUCCION_CANCELA_PORTAL = "CANCPORTAL";
    
    /** Recepcion Contrapago */
    private static final String MENSAJE_541 = "541";
    
    /** Entrega Contrapago */
    private static final String MENSAJE_543 = "543";
    
    /** Recepcion TLP */
    private static final String MENSAJE_540 = "540";
    
    /** Entrega TLP */
    private static final String MENSAJE_542 = "542";
    
    private static final String EXPIRA = "S";
    

    /**
     * 
     * @see com.indeval.portaldali.persistence.dao.protocolofinanciero.RegistroInstruccionesMatch2Dao
     * #actualizaEdoInsCancelaMatch2(com.indeval.portaldali.persistence.model.protocolofinanciero.RegistroInstruccionesMatch2, com.indeval.persistence.catalogo.modelo.AgentePK)
     */
	public int actualizaEdoInsCancelaMatch2(RegistroInstruccionesMatch2 registroInstruccionesMatch2, AgentePK agenteFirmado) {
		
		logger.info("Entrando a RegistroInstruccionesMatch2DaoImpl.actualizaEdoInsCancelaMatchTemplate");
		
		Assert.notNull(agenteFirmado, "El agente firmado no puede ser nulo");
		Assert.notNull(agenteFirmado.getIdInst(),
		"El id de la instituci\u00f3n no puede ser nulo");
		Assert.notNull(agenteFirmado.getFolioInst(),
		"El folio de la instituci\u00f3n no puede ser nulo");
		
		final String idFolioAgente = agenteFirmado.getIdInst() + agenteFirmado.getFolioInst();
		
		int resultado = 0;
		if(registroInstruccionesMatch2.getIdFolioReceptor().equalsIgnoreCase(idFolioAgente) 
				&& (registroInstruccionesMatch2.getTipoMensaje().equalsIgnoreCase(MENSAJE_541)
                        || registroInstruccionesMatch2.getTipoMensaje().equalsIgnoreCase(MENSAJE_540))
				|| registroInstruccionesMatch2.getIdFolioTraspasante().equalsIgnoreCase(idFolioAgente) 
				&& (registroInstruccionesMatch2.getTipoMensaje().equalsIgnoreCase(MENSAJE_543)
                        || registroInstruccionesMatch2.getTipoMensaje().equalsIgnoreCase(MENSAJE_542)) 
				&& !registroInstruccionesMatch2.getEstadoInstruccion().equalsIgnoreCase(ESTADO_INSTRUCCION_CANCELA_PORTAL)
				&& (registroInstruccionesMatch2.getExpira().equalsIgnoreCase(EXPIRA) 
						|| StringUtils.isNotBlank(registroInstruccionesMatch2.getExpira()))) {
			
			registroInstruccionesMatch2.setEstadoInstruccion(ESTADO_INSTRUCCION_CANCELA_PORTAL);
			
			if(this.getHibernateTemplate().merge(registroInstruccionesMatch2) != null ) {
				resultado = 1;
			}
		}
		
		return resultado;
	}

	/**
	 * 
	 * @see com.indeval.portaldali.persistence.dao.protocolofinanciero.RegistroInstruccionesMatch2Dao
	 * #getRegistroInstruccionMatch2(java.lang.Long)
	 */
	public RegistroInstruccionesMatch2 getRegistroInstruccionMatch2(
			final Long idBitacoraMatch) {
		
		Assert.notNull(idBitacoraMatch, "El idBitacoraMatch no puede ser nulo");
		
		 return (RegistroInstruccionesMatch2)getHibernateTemplate().execute(new HibernateCallback(){
				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					final Query query = session.getNamedQuery("RegistroInstruccionesMatch2.getRegistroInstruccion");
					query.setParameter(0, idBitacoraMatch);
					return query.uniqueResult();
				}
	    	});
	}
	
	/**
	 * 
	 * @see com.indeval.portaldali.persistence.dao.protocolofinanciero.RegistroInstruccionesMatch2Dao
	 * #updateStatusConfirmacion(java.math.BigInteger)
	 */
	public int updateStatusConfirmacion(RegistroInstruccionesMatch2 registroInstruccionesMatch2) {

		logger.info("Entrando a RegistroInstruccionesMatch2DaoImpl.updateStatusConfirmacion()");
		
		Assert.notNull(registroInstruccionesMatch2, "El idBitacoraMatch no puede ser nulo");

		int resultado = 0;

		if(registroInstruccionesMatch2 != null 
				&& StringUtils.isNotBlank(registroInstruccionesMatch2.getExpira()) 
				&& !EXPIRA.equalsIgnoreCase(registroInstruccionesMatch2.getExpira())){

			/* Se recupera el valor de la columna confirmacion y se incrementa en 1*/
			Integer confirmacion = Integer.valueOf(0);
			try {
				confirmacion = Integer.parseInt(StringUtils.isBlank(registroInstruccionesMatch2.getConfirmacion()) ? 
						"0" : registroInstruccionesMatch2.getConfirmacion());
			}
			catch (NumberFormatException e) {
				logger.debug("El valor del campo confirmacion no es numerico : [" + e.getMessage() + "]");
				e.printStackTrace(); // Si el valor no es un numero, el mismo se deja en 0.
			}
			++confirmacion;
			registroInstruccionesMatch2.setConfirmacion(confirmacion.toString());
			
		}
		
		
		
		if(this.getHibernateTemplate().merge(registroInstruccionesMatch2) != null){
			resultado = 1;
		}
		return resultado;
	}

}
