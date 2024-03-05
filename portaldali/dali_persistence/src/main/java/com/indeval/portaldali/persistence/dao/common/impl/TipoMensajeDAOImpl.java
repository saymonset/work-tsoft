/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * TipoMensajeDAOImpl.java
 * 03/03/2008
 */
package com.indeval.portaldali.persistence.dao.common.impl;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.indeval.portaldali.middleware.dto.TipoMensajeDTO;
import com.indeval.portaldali.middleware.dto.util.DTOAssembler;
import com.indeval.portaldali.persistence.dao.common.TipoMensajeDAO;
import com.indeval.portaldali.persistence.model.TipoMensajeCat;

/**
 * Implementaci del DAO de acceso al catálogo de tipos de mensaje.
 * @author Emigdio Hernández
 *
 */
public class TipoMensajeDAOImpl extends HibernateDaoSupport implements
		TipoMensajeDAO {

	/* (non-Javadoc)
	 * @see com.indeval.portaldali.persistence.dao.common.TipoMensajeDAO#consultaTipoMensajePorId(int)
	 */
	public TipoMensajeDTO consultaTipoMensajePorId(int idTipoMensaje) {
		
		return DTOAssembler.crearTipoMensajeDTO((TipoMensajeCat)getHibernateTemplate().get(TipoMensajeCat.class, 
				new BigInteger(String.valueOf(idTipoMensaje))));
	}

	/* (non-Javadoc)
	 * @see com.indeval.portaldali.persistence.dao.common.TipoMensajeDAO#consultarTodosLosTiposMensaje()
	 */
	@SuppressWarnings("unchecked")
	public List<TipoMensajeDTO> consultarTodosLosTiposMensaje() {
		Iterator resultados = getHibernateTemplate().find("FROM "+
				TipoMensajeCat.class.getName() + " t ORDER BY t.claveTipoMensaje").iterator();
		List<TipoMensajeDTO> resultadosDTO = new ArrayList<TipoMensajeDTO>();
		
		while(resultados.hasNext()){
			resultadosDTO.add(DTOAssembler.crearTipoMensajeDTO((TipoMensajeCat)resultados.next()));
		}
		return resultadosDTO;
	}
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portaldali.persistence.dao.common.TipoMensajeDAO#consultaTipoMensajePorClave(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public TipoMensajeDTO consultaTipoMensajePorClave(final String claveTipoMensaje) {
		
		return (TipoMensajeDTO)getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				List<TipoMensajeCat> resultados = session.createQuery("FROM "+
						TipoMensajeCat.class.getName() + " t WHERE t.claveTipoMensaje = :clave ").
						setParameter("clave", claveTipoMensaje).list();
				TipoMensajeDTO dto = null;
				if(resultados.size()>0){
					dto = DTOAssembler.crearTipoMensajeDTO(resultados.get(0));
				}
				return dto;
			}
		});
	}

}
