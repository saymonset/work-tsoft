/*
 * Copyrigth (c) 2010 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.dao.common.impl;

import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portaldali.middleware.dto.ParametrosDaliDTO;
import com.indeval.portaldali.middleware.dto.util.DTOAssembler;
import com.indeval.portaldali.persistence.dao.common.ParametrosDaliDao;
import com.indeval.portaldali.persistence.model.ParametrosDali;

/**
 * Clase que implementa los metodos definidos en la clase  
 * para las acciones sobre los parametros del Dali.
 * 
 * @author Maria C. Buendia
 * @version 1.0
 */
public class ParametrosDaliDaoImpl extends BaseDaoHibernateImpl implements ParametrosDaliDao{ 
    /** Log de clase. */
    private static final Logger log = LoggerFactory.getLogger(ParametrosDaliDaoImpl.class);
	
    /** 
	 * Obtiene el valor de los parametros de Dali.
	 * @param objeto ParametrosDali
	 */
    public ParametrosDaliDTO getAll(){
		log.info("Entrando a ParametrosDaliDaoImpl.getAll()");

		final StringBuffer query = new StringBuffer();
		query.append("from ");
		query.append(ParametrosDali.class.getName());
		log.debug("hquery:"+query.toString());
		
		return (ParametrosDaliDTO) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session) throws HibernateException, SQLException {
						Query hQuery = session.createQuery(query.toString());
						ParametrosDali parametros = (ParametrosDali) hQuery.uniqueResult();
						
						if(parametros == null){
							return null;
						}

						ParametrosDaliDTO paramDTO = DTOAssembler.crearParametrosDaliDTO(parametros);
						
						return paramDTO;
					}
				});
	}
    
}