/**
 * comertec - Bursatec
 * Portal DALI
 *
 * TipoLiquidacionDaliDAOImpl.java
 * 13/10/2009
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

import com.indeval.portaldali.middleware.dto.TipoLiquidacionDTO;
import com.indeval.portaldali.middleware.dto.util.DTOAssembler;
import com.indeval.portaldali.persistence.dao.common.TipoLiquidacionDaliDAO;
import com.indeval.portaldali.persistence.model.TipoLiquidacion;

/**
 * Implementación de la interfaz para las operaciones de consulta del catálogo
 * de tipos de instrucción de la base de datos.
 * 
 * @author Emigdio Hernández Rodríguez
 * @version 1.0
 */
public class TipoLiquidacionDaliDAOImpl extends HibernateDaoSupport implements TipoLiquidacionDaliDAO {

	/** Mecanismo de logging de la clase */
	@SuppressWarnings("unused")
	private Logger log = LoggerFactory.getLogger(TipoLiquidacionDaliDAOImpl.class);

	
	private List<Long> estadosTipoLiquidacion;
	
	/** La lista de nombre de instrucción de efectivo */
	private List<String> nombresLiquidacionEfectivo;

	/** La lista de nombres de instrucción de valores */
	private List<String> nombresLiquidacionValores;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.dao.TipoLiquidacionDAO#buscarTipoDeLiquidacionPorId(long)
	 */
	public TipoLiquidacionDTO buscarTipoDeLiquidacionPorId(long idTipoLiquidacion) {

		return DTOAssembler.crearTipoLiquidacionDTO((TipoLiquidacion) getHibernateTemplate().get(TipoLiquidacion.class,
				new BigInteger(String.valueOf(idTipoLiquidacion))));
	}
	
	
	public List<TipoLiquidacionDTO> buscarTipoDeLiquidacionPorIds() {

		if (estadosTipoLiquidacion != null && estadosTipoLiquidacion.size() != 0) {
			List<BigInteger> listaDefinitiva = new ArrayList<BigInteger>();
			Iterator<Long> ite = estadosTipoLiquidacion.iterator();
			while (ite.hasNext()) {
				listaDefinitiva.add(new BigInteger(ite.next().toString()));
			}

			DetachedCriteria criteria = DetachedCriteria.forClass(TipoLiquidacion.class);
			criteria.add(Restrictions.in("idTipoLiq", listaDefinitiva));

			List resultados = getHibernateTemplate().findByCriteria(criteria);
			List<TipoLiquidacionDTO> retorno = new ArrayList<TipoLiquidacionDTO>();
			Iterator it = resultados.iterator();
			while (it.hasNext()) {
				TipoLiquidacion obj = (TipoLiquidacion) it.next();
				retorno.add(DTOAssembler.crearTipoLiquidacionDTO(obj));
			}
			return retorno;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.dao.TipoLiquidacionDAO#buscarTiposDeLiquidacion(int)
	 */
	@SuppressWarnings("unchecked")
	public List<TipoLiquidacionDTO> buscarTiposDeLiquidacion() {

		Iterator resultados = getHibernateTemplate().find("FROM " + TipoLiquidacion.class.getName() + " t ORDER BY t.nombreCorto").iterator();
		List<TipoLiquidacionDTO> resultadosDTO = new ArrayList<TipoLiquidacionDTO>();

		while (resultados.hasNext()) {
			resultadosDTO.add(DTOAssembler.crearTipoLiquidacionDTO((TipoLiquidacion) resultados.next()));
		}
		return resultadosDTO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.persistence.dao.common.TipoLiquidacionDaliDAO#buscarTiposDeLiquidacionPorPrefijo()
	 */
	@SuppressWarnings("unchecked")
	public List<TipoLiquidacionDTO> buscarTiposDeLiquidacionPorPrefijo(String prefijo) {
		String prefijoAjustado = StringUtils.EMPTY;
		if (prefijo != null) {
			prefijoAjustado = prefijo.replace('*', '%');
		}

		prefijoAjustado = prefijoAjustado.toUpperCase();
		List<TipoLiquidacion> resultados = getHibernateTemplate().find(
				"FROM " + TipoLiquidacion.class.getName() + " t where (upper(t.nombreCorto) like '" + prefijoAjustado + "%' OR  "
						+ "  upper(t.descripcion) like '" + prefijoAjustado + "%') ORDER BY t.nombreCorto");
		List<TipoLiquidacionDTO> resultadosDTO = new ArrayList<TipoLiquidacionDTO>();
		for (TipoLiquidacion bo : resultados) {
			resultadosDTO.add(DTOAssembler.crearTipoLiquidacionDTO(bo));
		}

		return resultadosDTO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.persistence.dao.common.TipoLiquidacionDaliDAO#buscarTipoDeLiquidacionPorClave(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public TipoLiquidacionDTO buscarTipoDeLiquidacionPorClave(final String claveTipoLiquidacion) {
		
		return (TipoLiquidacionDTO)getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				List resultadoBO = session.createQuery("FROM " + TipoLiquidacion.class.getName() + " t where upper(t.idTipoLiq)= upper(:clave)").setParameter(
						"clave", claveTipoLiquidacion).list();
				TipoLiquidacionDTO dto = null;
				if (resultadoBO.size() > 0) {

					dto = DTOAssembler.crearTipoLiquidacionDTO((TipoLiquidacion) resultadoBO.get(0));
				}

				return dto;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.dao.TipoLiquidacionDAO#buscarTiposDeLiquidacion(int)
	 */
	@SuppressWarnings("unchecked")
	public List<TipoLiquidacionDTO> buscarTiposDeLiquidacion(String tiposCustodia) {

		Iterator resultados = getHibernateTemplate().find("FROM " + TipoLiquidacion.class.getName() + " t ORDER BY t.nombreCorto").iterator();
		List<TipoLiquidacionDTO> resultadosDTO = new ArrayList<TipoLiquidacionDTO>();

		while (resultados.hasNext()) {
			resultadosDTO.add(DTOAssembler.crearTipoLiquidacion((TipoLiquidacion) resultados.next()));
		}
		return resultadosDTO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.persistence.dao.common.TipoLiquidacionDaliDAO#buscarTiposDeLiquidacionPorIdsEfectivo(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<TipoLiquidacionDTO> buscarTiposDeLiquidacionPorIdsEfectivo(String prefijo) {
		String prefijoAjustado = StringUtils.EMPTY;
		if (prefijo != null) {
			prefijoAjustado = prefijo.replace('*', '%');
		}

		prefijoAjustado = prefijoAjustado + "%";

		if (nombresLiquidacionEfectivo != null && nombresLiquidacionEfectivo.size() != 0) {
			DetachedCriteria criteria = DetachedCriteria.forClass(TipoLiquidacion.class);
			Criterion cr1 = Restrictions.ilike("descripcion", prefijoAjustado);
			Criterion cr2 = Restrictions.ilike("nombreCorto", prefijoAjustado);

			criteria.add(Restrictions.or(cr1, cr2));
			criteria.add(Restrictions.in("nombreCorto", nombresLiquidacionEfectivo));
			criteria.addOrder(Order.asc("nombreCorto"));
			List<TipoLiquidacion> resultados = getHibernateTemplate().findByCriteria(criteria);
			List<TipoLiquidacionDTO> retorno = new ArrayList<TipoLiquidacionDTO>();
			for (TipoLiquidacion obj : resultados) {
				retorno.add(DTOAssembler.crearTipoLiquidacion(obj));
			}
			return retorno;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.persistence.dao.common.TipoLiquidacionDaliDAO#buscarTiposDeLiquidacionPorIdsEfectivo(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<TipoLiquidacionDTO> buscarTiposDeLiquidacionPorIdsValores(String prefijo) {
		String prefijoAjustado = StringUtils.EMPTY;
		if (prefijo != null) {
			prefijoAjustado = prefijo.replace('*', '%');
		}

		prefijoAjustado = prefijoAjustado + "%";

		if (nombresLiquidacionValores != null && nombresLiquidacionValores.size() != 0) {
			DetachedCriteria criteria = DetachedCriteria.forClass(TipoLiquidacion.class);
			Criterion cr1 = Restrictions.ilike("descripcion", prefijoAjustado);
			Criterion cr2 = Restrictions.ilike("nombreCorto", prefijoAjustado);

			criteria.add(Restrictions.or(cr1, cr2));
			criteria.add(Restrictions.in("nombreCorto", nombresLiquidacionValores));
			criteria.addOrder(Order.asc("nombreCorto"));
			List<TipoLiquidacion> resultados = getHibernateTemplate().findByCriteria(criteria);
			List<TipoLiquidacionDTO> retorno = new ArrayList<TipoLiquidacionDTO>();
			for (TipoLiquidacion obj : resultados) {
				retorno.add(DTOAssembler.crearTipoLiquidacion(obj));
			}
			return retorno;
		}
		return null;
	}

	/**
	 * Obtiene el valor del atributo nombresLiquidacionEfectivo
	 * 
	 * @return el valor del atributo nombresLiquidacionEfectivo
	 */
	public List<String> getNombresLiquidacionEfectivo() {
		return nombresLiquidacionEfectivo;
	}

	/**
	 * Establece el valor del atributo nombresLiquidacionEfectivo
	 * 
	 * @param nombresLiquidacionEfectivo
	 *            el valor del atributo nombresLiquidacionEfectivo a establecer
	 */
	public void setNombresLiquidacionEfectivo(List<String> nombresLiquidacionEfectivo) {
		this.nombresLiquidacionEfectivo = nombresLiquidacionEfectivo;
	}

	/**
	 * Obtiene el valor del atributo nombresLiquidacionValores
	 * 
	 * @return el valor del atributo nombresLiquidacionValores
	 */
	public List<String> getNombresLiquidacionValores() {
		return nombresLiquidacionValores;
	}

	/**
	 * Establece el valor del atributo nombresLiquidacionValores
	 * 
	 * @param nombresLiquidacionValores
	 *            el valor del atributo nombresLiquidacionValores a establecer
	 */
	public void setNombresLiquidacionValores(List<String> nombresLiquidacionValores) {
		this.nombresLiquidacionValores = nombresLiquidacionValores;
	}


	public List<Long> getEstadosTipoLiquidacion() {
		return estadosTipoLiquidacion;
	}


	public void setEstadosTipoLiquidacion(List<Long> estadosTipoLiquidacion) {
		this.estadosTipoLiquidacion = estadosTipoLiquidacion;
	}

	
	
	
	/////
	
	/////
	
	
	
}
