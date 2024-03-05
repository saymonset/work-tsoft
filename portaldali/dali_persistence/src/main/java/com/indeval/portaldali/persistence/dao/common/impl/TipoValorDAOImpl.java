/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *TipoValorDAOImpl.java
 * 
 * 24/02/2008
 */
package com.indeval.portaldali.persistence.dao.common.impl;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.type.BigIntegerType;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.indeval.portaldali.middleware.dto.MercadoDTO;
import com.indeval.portaldali.middleware.dto.TipoValorDTO;
import com.indeval.portaldali.middleware.dto.util.DTOAssembler;
import com.indeval.portaldali.middleware.services.common.constants.DaliConstants;
import com.indeval.portaldali.persistence.dao.common.TipoValorDAO;
import com.indeval.portaldali.persistence.model.Instrumento;

/**
 * Objeto de acceso a la base de datos para las operaciones relacionadas con los
 * tipos de valor de una posición.
 * 
 * @author Emigdio Hernández
 * 
 */
public class TipoValorDAOImpl extends HibernateDaoSupport implements TipoValorDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.dao.TipoValorDAO#buscarTiposDeValoresPorMercado(com.indeval.estadocuenta.core.application.dto.MercadoDTO)
	 */
	@SuppressWarnings("unchecked")
	public List<TipoValorDTO> buscarTiposDeValoresPorMercado(final MercadoDTO mercado) {

		return (List<TipoValorDTO>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(Instrumento.class);

				if (mercado != null && mercado.getId() > 0) {
					if (mercado.getId() != DaliConstants.ID_MERCADO_DINERO) {
						criteria.add(Expression.eq("idMercado", new BigInteger(String.valueOf(mercado.getId()))));
					} else {
						criteria.add(Expression.in("idMercado", new BigInteger[] { new BigInteger(String.valueOf(DaliConstants.ID_MERCADO_PAPEL_BANCARIO)),
								new BigInteger(String.valueOf(DaliConstants.ID_MERCADO_PAPEL_GUBER)) }));
					}
				}

				criteria.addOrder(Order.asc("claveTipoValor"));

				Iterator tiposValorBO = criteria.list().iterator();

				List<TipoValorDTO> resultado = new ArrayList<TipoValorDTO>();
				while (tiposValorBO.hasNext()) {

					resultado.add(DTOAssembler.crearTipoValorDTO((Instrumento) tiposValorBO.next()));
				}
				return resultado;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.dao.TipoValorDAO#buscarTipoDeValorPorId(long)
	 */
	public TipoValorDTO buscarTipoDeValorPorId(long idTipoValor) {
		return DTOAssembler.crearTipoValorDTO((Instrumento) getHibernateTemplate().get(Instrumento.class, new BigInteger(String.valueOf(idTipoValor))));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.persistence.dao.common.TipoValorDAO#buscarTiposDeValoresPorMercadoYPrefijo(com.indeval.portaldali.middleware.dto.MercadoDTO,
	 *      java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<TipoValorDTO> buscarTiposDeValoresPorMercadoYPrefijo(MercadoDTO mercado, String prefijo) {
		String prefijoAjustado = prefijo.replace('*', '%');
		final StringBuffer query = new StringBuffer();
		final ArrayList<Object> params = new ArrayList<Object>();
		final ArrayList<Type> tipos = new ArrayList<Type>();
		query.append("FROM  " + Instrumento.class.getName() + " tv WHERE upper(tv.claveTipoValor) like ? ");
		params.add(prefijoAjustado.toUpperCase() + "%");
		tipos.add(new StringType());

		if (mercado != null && mercado.getId() > 0) {
			if (mercado.getId() != DaliConstants.ID_MERCADO_DINERO) {
				query.append(" AND tv.idMercado = ?");
				params.add(new BigInteger(String.valueOf(mercado.getId())));
				tipos.add(new BigIntegerType());
			} else {
				query.append(" AND tv.idMercado in (?,?)");

				params.add(new BigInteger(String.valueOf(DaliConstants.ID_MERCADO_PAPEL_BANCARIO)));
				tipos.add(new BigIntegerType());

				params.add(new BigInteger(String.valueOf(DaliConstants.ID_MERCADO_PAPEL_GUBER)));
				tipos.add(new BigIntegerType());
			}
		}
		query.append(" order by tv.claveTipoValor");
		
		return (List<TipoValorDTO>) getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query q = session.createQuery(query.toString()).setParameters(params.toArray(), tipos.toArray(new Type[] {})).setMaxResults(
						DaliConstants.CANTIDAD_MAXIMA_RESULTADOS_SUGGESTION_BOX);
				Iterator tiposValorBO = q.list().iterator();
				List<TipoValorDTO> resultado = new ArrayList<TipoValorDTO>();
				while (tiposValorBO.hasNext()) {

					resultado.add(DTOAssembler.crearTipoValorDTO((Instrumento) tiposValorBO.next()));
				}
				return resultado;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.persistence.dao.common.TipoValorDAO#buscarTipoDeValorPorClave(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public TipoValorDTO buscarTipoDeValorPorClave(final String clave) {

		
		return (TipoValorDTO)getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Iterator<Instrumento> resultadoBO = session.createQuery(
						"FROM  " + Instrumento.class.getName() + " tv WHERE upper(tv.claveTipoValor) = upper(:tv) ").setParameter("tv", clave).list().iterator();
				TipoValorDTO resultado = null;
				if (resultadoBO.hasNext()) {
					resultado = DTOAssembler.crearTipoValorDTO(resultadoBO.next());
				}
				return resultado;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.persistence.dao.common.TipoValorDAO#buscarTiposDeValoresPorMercados(java.lang.Long[],
	 *      java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<TipoValorDTO> buscarTiposDeValoresPorMercados(Long[] idsMercados, String prefijo) {
		String prefijoAjustado = prefijo.replace('*', '%');
		final StringBuffer query = new StringBuffer();
		final ArrayList<Object> params = new ArrayList<Object>();
		final ArrayList<Type> tipos = new ArrayList<Type>();
		query.append("FROM  " + Instrumento.class.getName() + " tv WHERE upper(tv.claveTipoValor) like ? ");
		params.add(prefijoAjustado.toUpperCase() + "%");
		tipos.add(new StringType());

		if (idsMercados != null && idsMercados.length > 0) {
			query.append(" AND tv.idMercado in ( ");
			for (int i = 0; i < idsMercados.length; i++) {
				if (i > 0) {
					query.append(",");
				}
				query.append("?");
				params.add(new BigInteger(String.valueOf(idsMercados[i])));
				tipos.add(new BigIntegerType());

			}
			query.append(") ");

		}
		query.append(" order by tv.claveTipoValor");
		
		return (List<TipoValorDTO>)getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query q = session.createQuery(query.toString()).setParameters(params.toArray(), tipos.toArray(new Type[] {})).setMaxResults(
						DaliConstants.CANTIDAD_MAXIMA_RESULTADOS_SUGGESTION_BOX);
				Iterator tiposValorBO = q.list().iterator();
				List<TipoValorDTO> resultado = new ArrayList<TipoValorDTO>();
				while (tiposValorBO.hasNext()) {

					resultado.add(DTOAssembler.crearTipoValorDTO((Instrumento) tiposValorBO.next()));
				}
				return resultado;
			}
		});
	}
}
