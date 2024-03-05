/**
 * 2H Software
 * Sistema de Consulta de Estado de Cuenta - Indeval
 */
package com.indeval.portaldali.persistence.dao.common.impl;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.indeval.portaldali.middleware.dto.TipoTenenciaDTO;
import com.indeval.portaldali.middleware.dto.util.DTOAssembler;
import com.indeval.portaldali.persistence.dao.common.TipoTenenciaDAO;
import com.indeval.portaldali.persistence.model.TipoCuenta;

/**
 * Implementa la funcionalidad para realizar consultas a la base de datos y obtener datos de los tipos
 * de cuenta basado en los criterios enviados a la consulta.
 * @author Emigdio Hernández	
 * @version 1.0
 */
public class TipoTenenciaDAOImpl  extends HibernateDaoSupport implements TipoTenenciaDAO {

	/* (non-Javadoc)
	 * @see com.indeval.estadocuenta.core.application.dao.TipoTenenciaDAO#buscarBovedasPorTipoCustodia(com.indeval.estadocuenta.core.application.dto.TipoTenenciaDTO)
	 */
	@SuppressWarnings("unchecked")
	public List<TipoTenenciaDTO> buscarTipoTenenciaPorNaturalezaTipoCuentaYTipoCustodia(
			final TipoTenenciaDTO tipoTenencia) {
		
		return (List<TipoTenenciaDTO>)getHibernateTemplate().executeFind(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(TipoCuenta.class);
				criteria.add(Restrictions.eq("naturalezaContable",tipoTenencia.getTipoNaturaleza().getId())).
				add(Restrictions.eq("naturalezaProcesoLiquidacion",tipoTenencia.getTipoCuenta().getId())).
				add(Restrictions.eq("tipoCustodia", tipoTenencia.getTipoCustodia()));
				Iterator tiposTenencia = criteria.list().iterator();
				List<TipoTenenciaDTO>resultados = new ArrayList<TipoTenenciaDTO>();
				while( tiposTenencia.hasNext() ){
					resultados.add(DTOAssembler.crearTipoTeneciaDTO(((TipoCuenta)tiposTenencia.next())));
				}
				return resultados;
			}
		});
	}

	
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.estadocuenta.core.application.dao.TipoTenenciaDAO#buscarTipoTenenciaPorId(long)
	 */
	public TipoTenenciaDTO buscarTipoTenenciaPorId(long idTipoTenencia) {
		
		return DTOAssembler.crearTipoTeneciaDTO(((TipoCuenta) this.getHibernateTemplate().get(TipoCuenta.class, 
				new BigInteger(String.valueOf(idTipoTenencia)))));
	}

}
