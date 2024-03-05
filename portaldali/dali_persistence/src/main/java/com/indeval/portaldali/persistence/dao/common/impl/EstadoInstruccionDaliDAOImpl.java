/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * EstadoInstruccionDaliDAOImpl.java
 * 29/02/2008
 */
package com.indeval.portaldali.persistence.dao.common.impl;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.indeval.portaldali.middleware.dto.EstadoInstruccionDTO;
import com.indeval.portaldali.middleware.dto.util.DTOAssembler;
import com.indeval.portaldali.persistence.dao.common.EstadoInstruccionDaliDAO;
import com.indeval.portaldali.persistence.model.EstadoInstruccionCat;

/**
 * Implementación del DAO que permite la consulta al catálogo de estados de
 * instrucción.
 * 
 * @author Emigdio Hernández
 * 
 */
public class EstadoInstruccionDaliDAOImpl extends HibernateDaoSupport implements EstadoInstruccionDaliDAO {

	@SuppressWarnings("unused")
	private Logger logger = LoggerFactory.getLogger(EstadoInstruccionDaliDAOImpl.class);

	private List<Long> estadosInstruccion;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.dali.integration.dao.EstadoInstruccionDAO#consultarTodosLosEstadosInstruccion()
	 */
	@SuppressWarnings("unchecked")
	public List<EstadoInstruccionDTO> consultarTodosLosEstadosInstruccion() {
		List<EstadoInstruccionCat> res = getHibernateTemplate().find("FROM " + EstadoInstruccionCat.class.getName() + " es order by es.claveEstadoInstruccion");
		List<EstadoInstruccionDTO> resultados = new ArrayList<EstadoInstruccionDTO>();
		for (EstadoInstruccionCat bo : res) {

			resultados.add(DTOAssembler.crearEstadoInstruccionDTO(bo));

		}
		return resultados;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.dali.integration.dao.EstadoInstruccionDAO#buscarEstadosInstruccionPorIdsValores(String
	 *      prefijo)
	 */
	@SuppressWarnings("unchecked")
	public List<EstadoInstruccionDTO> buscarEstadosInstruccionPorIds(String prefijo) {
		String prefijoAjustado = StringUtils.EMPTY;
		if (prefijo != null) {
			prefijoAjustado = prefijo.replace('*', '%');
		}

		prefijoAjustado = prefijoAjustado + "%";

		DetachedCriteria criteria = DetachedCriteria.forClass(EstadoInstruccionCat.class);
		Criterion cr1 = Restrictions.ilike("claveEstadoInstruccion", prefijoAjustado);
		Criterion cr2 = Restrictions.ilike("descripcion", prefijoAjustado);

		criteria.add(Restrictions.or(cr1, cr2));
		criteria.addOrder(Order.asc("claveEstadoInstruccion"));
		List<EstadoInstruccionCat> resultados = getHibernateTemplate().findByCriteria(criteria);
		List<EstadoInstruccionDTO> retorno = new ArrayList<EstadoInstruccionDTO>();
		for (EstadoInstruccionCat obj : resultados) {
			retorno.add(DTOAssembler.crearEstadoInstruccionDTO(obj));
		}
		return retorno;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.dali.integration.dao.EstadoInstruccionDAO#consultarEstadoInstruccionPorClave(String
	 *      claveEstadoInstruccion)
	 */
	@SuppressWarnings("unchecked")
	public EstadoInstruccionDTO consultarEstadoInstruccionPorClave(final String claveEstadoInstruccion) {

		return (EstadoInstruccionDTO) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				List resultadoBO = session.createQuery(
						"FROM " + EstadoInstruccionCat.class.getName() + " t where upper(t.claveEstadoInstruccion)= upper(:clave)").setParameter("clave",
						claveEstadoInstruccion).list();
				EstadoInstruccionDTO dto = null;
				if (resultadoBO.size() > 0) {
					dto = DTOAssembler.crearEstadoInstruccionDTO((EstadoInstruccionCat) resultadoBO.get(0));
				}

				return dto;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.persistence.dao.common.EstadoInstruccionDaliDAO#consultarEstadoInstruccionPorId(int)
	 */
	public EstadoInstruccionDTO consultarEstadoInstruccionPorId(long idEstadoInstruccion) {

		return DTOAssembler.crearEstadoInstruccionDTO((EstadoInstruccionCat) getHibernateTemplate().get(EstadoInstruccionCat.class,
				new BigInteger(String.valueOf(idEstadoInstruccion))));
	}

	@SuppressWarnings("unchecked")
	public List<EstadoInstruccionDTO> consultarEstadosInstruccionPorIds() {
		if (estadosInstruccion != null && estadosInstruccion.size() != 0) {
			List<BigInteger> listaDefinitiva = new ArrayList<BigInteger>();
			Iterator<Long> ite = estadosInstruccion.iterator();
			while (ite.hasNext()) {
				listaDefinitiva.add(new BigInteger(ite.next().toString()));
			}

			DetachedCriteria criteria = DetachedCriteria.forClass(EstadoInstruccionCat.class);
			criteria.add(Restrictions.in("idEstadoInstruccion", listaDefinitiva));

			List resultados = getHibernateTemplate().findByCriteria(criteria);
			List<EstadoInstruccionDTO> retorno = new ArrayList<EstadoInstruccionDTO>();
			Iterator it = resultados.iterator();
			while (it.hasNext()) {
				EstadoInstruccionCat obj = (EstadoInstruccionCat) it.next();
				retorno.add(DTOAssembler.crearEstadoInstruccionDTO(obj));
			}
			return retorno;
		}
		return null;
	}

	/**
	 * @return the estadosInstruccion
	 */
	public List<Long> getEstadosInstruccion() {
		return estadosInstruccion;
	}

	/**
	 * @param estadosInstruccion
	 *            the estadosInstruccion to set
	 */
	public void setEstadosInstruccion(List<Long> estadosInstruccion) {
		this.estadosInstruccion = estadosInstruccion;
	}

}
