/*
 * Copyright (c) 2007-2007 S.D. Indeval. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.dao.protocolofinanciero.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portaldali.persistence.dao.protocolofinanciero.ValidadorPFIDao;
import com.indeval.portaldali.persistence.dao.protocolofinanciero.impl.support.MatchException;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class ValidadorPFIDaoImpl extends BaseDaoHibernateImpl  implements ValidadorPFIDao {

    
	/* Logger para registrar cualquier evento	 */
	private final Logger logger = LoggerFactory.getLogger(ValidadorPFIDaoImpl.class);
    
	/* Query para validar si el participante entra por PFI	 */
	private static final String SELECT_INSTITUCIONES_PFI = 
        "select t.CLAVE_TIPO_INSTITUCION||'|'||i.FOLIO " +
        "from C_INSTITUCIONES i, C_TIPOS_INSTITUCION t, C_PARTICIPANTE_PFI di " +
        "where i.ID_TIPO_INSTITUCION = t.ID_TIPO_INSTITUCION " +
        "and i.ID_INSTITUCION = di.ID_PARTICIPANTE and di.ACTIVO = 'S'";
	
//	private static final String SELECT_ORIGEN_BANXICO = "select max(VALOR_PROPIEDAD) " +
//            "from T_CONFIGURACION_MATCH where NOMBRE_PROPIEDAD ='ORIGEN_BANXICO'";
	
	private static Set<String> participantesPFI;
	
	private String name;
	
	private String origenBanxico;
    
    /** Acceso a JdbcTemplate */
    private JdbcTemplate jdbcTemplateOracle;
	
	/**
	 * @see com.indeval.persistence.portallegado.protocolofinanciero.dao.ValidadorPFIDao#validarParticipantePFI(java.lang.String, java.lang.String)
	 */
	public boolean validarParticipantePFI(String id, String folio) {
        try {
            this.refresh();
        }
        catch (MatchException e) {
            e.printStackTrace();
        }
		return participantesPFI.contains(participanteKey(id, folio));
	}

	/**
	 * @see com.indeval.persistence.portallegado.protocolofinanciero.dao.impl.support.configuration.CacheStoreMatch#getName()
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @see com.indeval.persistence.portallegado.protocolofinanciero.dao.impl.support.configuration.CacheStoreMatch#refresh()
	 */
	@SuppressWarnings("unchecked")
	public void refresh() throws MatchException {
		Set<String> pfi = new HashSet<String>();
        
        /* Se define el esquema */
        StringBuffer query =  new StringBuffer("");
            //new StringBuffer(this.getSchema()!=null ? (String) this.getSchema().get(0) : "");
        
		try {
			List<String> participantes = jdbcTemplateOracle.queryForList(
                    query.append(SELECT_INSTITUCIONES_PFI).toString(), String.class);
			
			for (String participante : participantes) {
				pfi.add(participante);
			}
		} catch (EmptyResultDataAccessException e) {
			logger.info("No existen participantes conectados al PFI");
		}
		participantesPFI = pfi;
	}
	
	/**
	 * @param id
	 * @param folio
	 * @return String
	 */
	private String participanteKey(String id, String folio){
		return new StringBuilder(id).append("|").append(folio).toString();
	}
	
	

	/**
	 * @see com.indeval.persistence.portallegado.protocolofinanciero.dao.ValidadorPFIDao#getOrigenBanxico()
	 */
	public String getOrigenBanxico() {
		return origenBanxico;
	}

	/**
	 * @see org.springframework.dao.support.DaoSupport#afterPropertiesSet()
	 */
//	@Override
//	public void afterPropertiesSet() throws Exception {
//		super.afterPropertiesSet();
//		refresh();
//	}

    /**
     * @return the jdbcTemplateOracle
     */
    public JdbcTemplate getJdbcTemplateOracle() {
        return jdbcTemplateOracle;
    }

    /**
     * @param jdbcTemplateOracle the jdbcTemplateOracle to set
     */
    public void setJdbcTemplateOracle(JdbcTemplate jdbcTemplateOracle) {
        this.jdbcTemplateOracle = jdbcTemplateOracle;
    }
	
}
