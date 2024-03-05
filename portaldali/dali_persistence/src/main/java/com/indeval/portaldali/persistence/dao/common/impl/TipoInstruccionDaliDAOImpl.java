/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * TipoInstruccionDaliDAOImpl.java
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

import com.indeval.portaldali.middleware.dto.TipoInstruccionDTO;
import com.indeval.portaldali.middleware.dto.util.DTOAssembler;
import com.indeval.portaldali.persistence.dao.common.TipoInstruccionDaliDAO;
import com.indeval.portaldali.persistence.model.TipoInstruccion;

/**
 * Implementación de la interfaz para las operaciones de consulta del catálogo
 * de tipos de instrucción de la base de datos.
 * 
 * @author Emigdio Hernández Rodríguez
 * @version 1.0
 */
public class TipoInstruccionDaliDAOImpl extends HibernateDaoSupport implements TipoInstruccionDaliDAO {

	/** Mecanismo de logging de la clase */
	@SuppressWarnings("unused")
	private Logger logger = LoggerFactory.getLogger(TipoInstruccionDaliDAOImpl.class);

	/** La lista de nombre de instrucción de efectivo */
	private List<String> nombresIntruccionEfectivo;

	/** La lista de nombres de instrucción de valores */
	private List<String> nombresIntruccionValores;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.dao.TipoInstruccionDAO#buscarTipoDeInstruccionPorId(long)
	 */
	public TipoInstruccionDTO buscarTipoDeInstruccionPorId(long idTipoInstruccion) {

		return DTOAssembler.crearTipoInstruccionDTO((TipoInstruccion) getHibernateTemplate().get(TipoInstruccion.class,
				new BigInteger(String.valueOf(idTipoInstruccion))));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.dao.TipoInstruccionDAO#buscarTiposDeInstruccion(int)
	 */
	@SuppressWarnings("unchecked")
	public List<TipoInstruccionDTO> buscarTiposDeInstruccion() {

		Iterator resultados = getHibernateTemplate().find("FROM " + TipoInstruccion.class.getName() + " t ORDER BY t.nombreCorto").iterator();
		List<TipoInstruccionDTO> resultadosDTO = new ArrayList<TipoInstruccionDTO>();

		while (resultados.hasNext()) {
			resultadosDTO.add(DTOAssembler.crearTipoInstruccionDTO((TipoInstruccion) resultados.next()));
		}
		return resultadosDTO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.persistence.dao.common.TipoInstruccionDaliDAO#buscarTiposDeInstruccionPorPrefijo()
	 */
	@SuppressWarnings("unchecked")
	public List<TipoInstruccionDTO> buscarTiposDeInstruccionPorPrefijo(String prefijo) {
		String prefijoAjustado = StringUtils.EMPTY;
		if (prefijo != null) {
			prefijoAjustado = prefijo.replace('*', '%');
		}

		prefijoAjustado = prefijoAjustado.toUpperCase();
		List<TipoInstruccion> resultados = getHibernateTemplate().find(
				"FROM " + TipoInstruccion.class.getName() + " t where (upper(t.nombreCorto) like '" + prefijoAjustado + "%' OR  "
						+ "  upper(t.descripcion) like '" + prefijoAjustado + "%') ORDER BY t.nombreCorto");
		List<TipoInstruccionDTO> resultadosDTO = new ArrayList<TipoInstruccionDTO>();
		for (TipoInstruccion bo : resultados) {
			resultadosDTO.add(DTOAssembler.crearTipoInstruccionDTO(bo));
		}

		return resultadosDTO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.persistence.dao.common.TipoInstruccionDaliDAO#buscarTipoDeInstruccionPorClave(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public TipoInstruccionDTO buscarTipoDeInstruccionPorClave(final String claveTipoInstruccion) {
		
		return (TipoInstruccionDTO)getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				List resultadoBO = session.createQuery("FROM " + TipoInstruccion.class.getName() + " t where upper(t.nombreCorto)= upper(:clave)").setParameter(
						"clave", claveTipoInstruccion).list();
				TipoInstruccionDTO dto = null;
				if (resultadoBO.size() > 0) {
					dto = DTOAssembler.crearTipoInstruccionDTO((TipoInstruccion) resultadoBO.get(0));
				}

				return dto;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.dao.TipoInstruccionDAO#buscarTiposDeInstruccion(int)
	 */
	@SuppressWarnings("unchecked")
	public List<TipoInstruccionDTO> buscarTiposDeInstruccion(String tiposCustodia) {

		Iterator resultados = getHibernateTemplate().find("FROM " + TipoInstruccion.class.getName() + " t ORDER BY t.nombreCorto").iterator();
		List<TipoInstruccionDTO> resultadosDTO = new ArrayList<TipoInstruccionDTO>();

		while (resultados.hasNext()) {
			resultadosDTO.add(DTOAssembler.crearTipoInstruccion((TipoInstruccion) resultados.next()));
		}
		return resultadosDTO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.persistence.dao.common.TipoInstruccionDaliDAO#buscarTiposDeInstruccionPorIdsEfectivo(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<TipoInstruccionDTO> buscarTiposDeInstruccionPorIdsEfectivo(String prefijo) {
		String prefijoAjustado = StringUtils.EMPTY;
		if (prefijo != null) {
			prefijoAjustado = prefijo.replace('*', '%');
		}

		prefijoAjustado = prefijoAjustado + "%";

		if (nombresIntruccionEfectivo != null && nombresIntruccionEfectivo.size() != 0) {
			DetachedCriteria criteria = DetachedCriteria.forClass(TipoInstruccion.class);
			Criterion cr1 = Restrictions.ilike("descripcion", prefijoAjustado);
			Criterion cr2 = Restrictions.ilike("nombreCorto", prefijoAjustado);

			criteria.add(Restrictions.or(cr1, cr2));
			criteria.add(Restrictions.in("nombreCorto", nombresIntruccionEfectivo));
			criteria.addOrder(Order.asc("nombreCorto"));
			List<TipoInstruccion> resultados = getHibernateTemplate().findByCriteria(criteria);
			List<TipoInstruccionDTO> retorno = new ArrayList<TipoInstruccionDTO>();
			for (TipoInstruccion obj : resultados) {
				retorno.add(DTOAssembler.crearTipoInstruccion(obj));
			}
			return retorno;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.persistence.dao.common.TipoInstruccionDaliDAO#buscarTiposDeInstruccionPorIdsEfectivo(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<TipoInstruccionDTO> buscarTiposDeInstruccionPorIdsValores(String prefijo) {
		String prefijoAjustado = StringUtils.EMPTY;
		if (prefijo != null) {
			prefijoAjustado = prefijo.replace('*', '%');
		}

		prefijoAjustado = prefijoAjustado + "%";

		if (nombresIntruccionValores != null && nombresIntruccionValores.size() != 0) {
			DetachedCriteria criteria = DetachedCriteria.forClass(TipoInstruccion.class);
			Criterion cr1 = Restrictions.ilike("descripcion", prefijoAjustado);
			Criterion cr2 = Restrictions.ilike("nombreCorto", prefijoAjustado);

			criteria.add(Restrictions.or(cr1, cr2));
			criteria.add(Restrictions.in("nombreCorto", nombresIntruccionValores));
			criteria.addOrder(Order.asc("nombreCorto"));
			List<TipoInstruccion> resultados = getHibernateTemplate().findByCriteria(criteria);
			List<TipoInstruccionDTO> retorno = new ArrayList<TipoInstruccionDTO>();
			for (TipoInstruccion obj : resultados) {
				retorno.add(DTOAssembler.crearTipoInstruccion(obj));
			}
			return retorno;
		}
		return null;
	}

	/**
	 * Obtiene el valor del atributo nombresIntruccionEfectivo
	 * 
	 * @return el valor del atributo nombresIntruccionEfectivo
	 */
	public List<String> getNombresIntruccionEfectivo() {
		return nombresIntruccionEfectivo;
	}

	/**
	 * Establece el valor del atributo nombresIntruccionEfectivo
	 * 
	 * @param nombresIntruccionEfectivo
	 *            el valor del atributo nombresIntruccionEfectivo a establecer
	 */
	public void setNombresIntruccionEfectivo(List<String> nombresIntruccionEfectivo) {
		this.nombresIntruccionEfectivo = nombresIntruccionEfectivo;
	}

	/**
	 * Obtiene el valor del atributo nombresIntruccionValores
	 * 
	 * @return el valor del atributo nombresIntruccionValores
	 */
	public List<String> getNombresIntruccionValores() {
		return nombresIntruccionValores;
	}

	/**
	 * Establece el valor del atributo nombresIntruccionValores
	 * 
	 * @param nombresIntruccionValores
	 *            el valor del atributo nombresIntruccionValores a establecer
	 */
	public void setNombresIntruccionValores(List<String> nombresIntruccionValores) {
		this.nombresIntruccionValores = nombresIntruccionValores;
	}

}
