/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * CuentaDaliDAOImpl.java
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
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.type.BigIntegerType;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.indeval.portaldali.middleware.dto.CuentaDTO;
import com.indeval.portaldali.middleware.dto.CuentaEfectivoDTO;
import com.indeval.portaldali.middleware.dto.TipoCuentaDTO;
import com.indeval.portaldali.middleware.dto.util.DTOAssembler;
import com.indeval.portaldali.middleware.dto.util.EstadoPaginacionDTO;
import com.indeval.portaldali.middleware.services.common.constants.CommonConstants;
import com.indeval.portaldali.middleware.services.common.constants.TipoCuentaConstants;
import com.indeval.portaldali.persistence.dao.common.CuentaDaliDAO;
import com.indeval.portaldali.persistence.model.CuentaControlada;
import com.indeval.portaldali.persistence.model.CuentaNombrada;

/**
 * Objeto de acceso a la base de datos para las operaciones relacionadas con las
 * cuentas controladas.
 * 
 * @author Emigdio Hernández
 * 
 */
public class CuentaDaliDAOImpl extends HibernateDaoSupport implements CuentaDaliDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.dao.CuentaDAO#buscarCuentasControladas(com.indeval.estadocuenta.core.application.dto.CuentaDTO,
	 *      com.indeval.estadocuenta.core.application.dto.EstadoPaginacionDTO)
	 */
	@SuppressWarnings("unchecked")
	public List<CuentaDTO> buscarCuentasControladas(CuentaDTO criterio, EstadoPaginacionDTO estadoPaginacion) {
		Iterator itRes = buscarCuentas(criterio, CuentaControlada.class, estadoPaginacion, null).iterator();
		List<CuentaDTO> resultado = new ArrayList<CuentaDTO>();
		while (itRes.hasNext()) {
			resultado.add(DTOAssembler.crearCuentaDTO((CuentaControlada) itRes.next()));
		}
		return resultado;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.dao.CuentaDAO#obtenerProyeccionDeBusquedaDeCuentasControladas(com.indeval.estadocuenta.core.application.dto.CuentaDTO)
	 */
	public int obtenerProyeccionDeBusquedaDeCuentasControladas(CuentaDTO criterio) {

		return contarCuentas(criterio, CuentaControlada.class, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.dao.CuentaDAO#obtenerProyeccionDeBusquedaDeCuentasNombradas(com.indeval.estadocuenta.core.application.dto.CuentaDTO)
	 */
	public int obtenerProyeccionDeBusquedaDeCuentasNombradas(CuentaDTO criterio) {

		return contarCuentas(criterio, CuentaNombrada.class, null);
	}

	/**
	 * Cuenta en la tabla de Cuenta nombrada o cuenta controlada según la clase
	 * requerida para la búsqueda.
	 * 
	 * @param criterio
	 *            Criterio proporcionado para realizar la búsqueda
	 * @param idsValidos
	 *            Conjunto de ids válidos para realizar la búsqueda, estos ids
	 *            se toman en cuentan cuando es diferente de null, si los
	 *            criterios tienen una longitud de 0 entonces no se debe de
	 *            tomar en cuenta ningn resultado.
	 * @return Lista con los resultados obtenidos de la consulta
	 */
	@SuppressWarnings("unchecked")
	private int contarCuentas(CuentaDTO criterio, Class tipoCuenta, List<Long> idsValidos) {
		ArrayList<Object> params = new ArrayList<Object>();
		ArrayList<Type> tipos = new ArrayList<Type>();
		StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT COUNT(*) FROM " + tipoCuenta.getName() + " cn WHERE ");
		queryString.append("  cn.tipoCuenta.tipoCustodia  = ? ");
		params.add(criterio.getTipoTenencia().getTipoCustodia());
		tipos.add(new StringType());
		if (!"-1".equals(criterio.getNumeroCuenta())) {
			queryString.append(" AND (cn.institucion.tipoInstitucion.claveTipoInstitucion || " + "cn.institucion.folioInstitucion || cn.cuenta )  = ? ");
			params.add(criterio.getNumeroCuenta());
			tipos.add(new StringType());
		}

		if (criterio.getTipoTenencia().getIdTipoCuenta() > 0) {

			queryString.append(" AND cn.tipoCuenta.idTipoCuenta  = ? ");
			params.add(new BigInteger(String.valueOf(criterio.getTipoTenencia().getIdTipoCuenta())));
			tipos.add(new BigIntegerType());
		}

		if (!criterio.getTipoTenencia().getTipoCuenta().getId().equals("-1")) {

			queryString.append(" AND  cn.tipoCuenta.naturalezaProcesoLiquidacion  = ? ");
			params.add(criterio.getTipoTenencia().getTipoCuenta().getId());
			tipos.add(new StringType());
		}

		if (!"-1".equals(criterio.getTipoTenencia().getTipoNaturaleza().getId())) {

			queryString.append(" AND cn.tipoCuenta.naturalezaContable  = ? ");
			params.add(criterio.getTipoTenencia().getTipoNaturaleza().getId());
			tipos.add(new StringType());
		}

		if (criterio.getInstitucion() != null && StringUtils.isNotBlank(criterio.getInstitucion().getClaveTipoInstitucion())) {

			queryString.append(" AND cn.institucion.tipoInstitucion.claveTipoInstitucion  = ? ");
			params.add(criterio.getInstitucion().getClaveTipoInstitucion());
			tipos.add(new StringType());
		}

		if (criterio.getInstitucion().getId() > 0) {

			queryString.append(" AND cn.institucion.idInstitucion  = ? ");
			params.add(new BigInteger(String.valueOf(criterio.getInstitucion().getId())));
			tipos.add(new BigIntegerType());
		}
		int res = 0;
		if (idsValidos != null && idsValidos.size() > 0) {
			// if
			// (criterio.getTipoTenencia().getTipoCuenta().getId().equals(TipoCuentaConstants.TIPO_CONTROLADA))
			// {
			// queryString.append(" AND cn.idCuentaControlada in ( ");
			// } else {
			// queryString.append(" AND cn.idCuentaNombrada in ( ");
			// }
			// Iterator itIds = idsValidos.iterator();
			// while (itIds.hasNext()) {
			// queryString.append(((Long) itIds.next()).toString());
			// if (itIds.hasNext()) {
			// queryString.append(",");
			// }
			// }
			// queryString.append(")");
			res = idsValidos.size();
		}

		return res;

	}

	/**
	 * Busca en la tabla de Cuenta nombrada o cuenta controlada según la clase
	 * requerida para la búsqueda. Filtra los resultados de la consulta en base
	 * a un conjunto de ids validos enviados Si el parámetro de ids validos es
	 * nulo, no se toma en cuenta.
	 * 
	 * @param criterio
	 *            Criterio proporcionado para realizar la búsqueda.
	 * @param estadoPaginacion
	 *            Estado de la paginación para obtener una página de resultados
	 * @param idsValidos
	 *            Conjunto de identificadores para filtrar la consulta
	 * @return Lista con los resultados obtenidos de la consulta
	 */
	@SuppressWarnings("unchecked")
	private List buscarCuentas(CuentaDTO criterio, Class tipoCuenta, EstadoPaginacionDTO estadoPaginacion, List<Long> idsValidos) {
		final ArrayList<Object> params = new ArrayList<Object>();
		final ArrayList<Type> tipos = new ArrayList<Type>();
		final StringBuffer queryString = new StringBuffer();
		List resultados = new ArrayList();
		queryString.append("FROM " + tipoCuenta.getName() + " cn " + " join fetch cn.institucion " + " join fetch cn.tipoCuenta "
				+ " join fetch cn.institucion.tipoInstitucion " + " WHERE ");
		queryString.append("  cn.tipoCuenta.tipoCustodia  = ? ");
		params.add(criterio.getTipoTenencia().getTipoCustodia());
		tipos.add(new StringType());

		if (!"-1".equals(criterio.getNumeroCuenta())) {
			queryString.append(" AND (cn.institucion.tipoInstitucion.claveTipoInstitucion || " + "cn.institucion.folioInstitucion || cn.cuenta )  = ? ");
			params.add(criterio.getNumeroCuenta());
			tipos.add(new StringType());
		}

		if (criterio.getTipoTenencia().getIdTipoCuenta() > 0) {

			queryString.append(" AND cn.tipoCuenta.idTipoCuenta  = ? ");
			params.add(new BigInteger(String.valueOf(criterio.getTipoTenencia().getIdTipoCuenta())));
			tipos.add(new BigIntegerType());
		}

		if (!criterio.getTipoTenencia().getTipoCuenta().getId().equals("-1")) {

			queryString.append("  AND  cn.tipoCuenta.naturalezaProcesoLiquidacion  = ? ");
			params.add(criterio.getTipoTenencia().getTipoCuenta().getId());
			tipos.add(new StringType());
		}

		if (!"-1".equals(criterio.getTipoTenencia().getTipoNaturaleza().getId())) {

			queryString.append("  AND  cn.tipoCuenta.naturalezaContable  = ? ");
			params.add(criterio.getTipoTenencia().getTipoNaturaleza().getId());
			tipos.add(new StringType());
		}

		if (criterio.getInstitucion().getId() > 0) {

			queryString.append("  AND cn.institucion.idInstitucion  = ? ");
			params.add(new BigInteger(String.valueOf(criterio.getInstitucion().getId())));
			tipos.add(new BigIntegerType());
		}

		if (criterio.getInstitucion() != null && StringUtils.isNotBlank(criterio.getInstitucion().getClaveTipoInstitucion())) {

			queryString.append(" AND cn.institucion.tipoInstitucion.claveTipoInstitucion  = ? ");
			params.add(criterio.getInstitucion().getClaveTipoInstitucion());
			tipos.add(new StringType());
		}

		if (idsValidos != null && idsValidos.size() > 0 && (estadoPaginacion.getRegistrosPorPagina() * estadoPaginacion.getNumeroPagina()) > 0) {
			if (criterio.getTipoTenencia().getTipoCuenta().getId().equals(TipoCuentaConstants.TIPO_CONTROLADA)) {
				queryString.append(" AND cn.idCuentaControlada in ( ");
			} else {
				queryString.append(" AND cn.idCuentaNombrada in ( ");
			}

			// La siguiente sección resuelve el problema que se presenta cuando
			// existen mas de mil emisiones diferentes en los resultados
			if (estadoPaginacion != null) {

				for (int i = (estadoPaginacion.getNumeroPagina() - 1) * estadoPaginacion.getRegistrosPorPagina(); i < estadoPaginacion.getRegistrosPorPagina()
						* estadoPaginacion.getNumeroPagina()
						&& i < idsValidos.size(); i++) {
					queryString.append(idsValidos.get(i));
					if (i + 1 < estadoPaginacion.getRegistrosPorPagina() * estadoPaginacion.getNumeroPagina() && i + 1 < idsValidos.size()) {
						queryString.append(",");
					}
				}

			} else {
				Iterator<Long> itIds = idsValidos.iterator();
				int total = 0;
				int maximo = 1000;
				BigInteger id = null;
				while (itIds.hasNext()) {
					id = new BigInteger(String.valueOf(itIds.next()));
					queryString.append(id.toString());
					if (itIds.hasNext()) {
						queryString.append(",");
					}
					total++;
					if (total >= maximo) {
						break;
					}
				}

			}

			// Iterator itIds = idsValidos.iterator();
			// while (itIds.hasNext()) {
			// queryString.append(((Long) itIds.next()).toString());
			// if (itIds.hasNext()) {
			// queryString.append(",");
			// }
			// }
			queryString.append(")");
			
			queryString.append(" order by (cn.institucion.tipoInstitucion.claveTipoInstitucion || " + "cn.institucion.folioInstitucion || cn.cuenta )");

			resultados = (List)getHibernateTemplate().execute(new HibernateCallback(){
				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					return session.createQuery(queryString.toString()).setParameters(params.toArray(), tipos.toArray(new Type[] {})).list();
				}
			});
			
		}
		
		return resultados;
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.dao.CuentaDAO#buscarCuentasNombradas(com.indeval.estadocuenta.core.application.dto.CuentaDTO,
	 *      com.indeval.estadocuenta.core.application.dto.EstadoPaginacionDTO)
	 */
	@SuppressWarnings("unchecked")
	public List<CuentaDTO> buscarCuentasNombradas(CuentaDTO criterio, EstadoPaginacionDTO estadoPaginacion) {
		Iterator itRes = buscarCuentas(criterio, CuentaNombrada.class, estadoPaginacion, null).iterator();
		List<CuentaDTO> resultado = new ArrayList<CuentaDTO>();
		while (itRes.hasNext()) {
			resultado.add(DTOAssembler.crearCuentaDTO((CuentaNombrada) itRes.next()));
		}
		return resultado;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.dao.CuentaDAO#buscarCuentaPorNumeroCuenta(com.indeval.estadocuenta.core.application.dto.CuentaDTO)
	 */
	@SuppressWarnings("unchecked")
	public CuentaDTO buscarCuentaPorNumeroCuenta(final CuentaDTO criterio) {
		final StringBuffer query = new StringBuffer();
		final ArrayList<Object> params = new ArrayList<Object>();
		final ArrayList<Type> tipos = new ArrayList<Type>();
		
		Class cuentaClass = null;
		if (criterio.getTipoTenencia().getTipoCuenta().getId().equals(TipoCuentaDTO.CUENTA_CONTROLADA)) {
			cuentaClass = CuentaControlada.class;
		} else {
			cuentaClass = CuentaNombrada.class;
		}
		query.append("FROM " + cuentaClass.getName() + " cn ");
		query.append(" join fetch cn.institucion ");
		query.append(" join fetch cn.tipoCuenta ");
		query.append(" join fetch cn.institucion.tipoInstitucion ");
		query.append(" WHERE ");
		
		query.append(" (cn.institucion.tipoInstitucion.claveTipoInstitucion || ");
		query.append("cn.institucion.folioInstitucion || cn.cuenta )  = ? ");
		params.add(criterio.getNumeroCuenta());
		tipos.add(new StringType());
		
		query.append("  AND cn.tipoCuenta.tipoCustodia  = ? ");
		params.add(criterio.getTipoTenencia().getTipoCustodia());
		tipos.add(new StringType());
		
		if (StringUtils.isNotBlank(criterio.getTipoTenencia().getTipoCuenta().getId()) && !criterio.getTipoTenencia().getTipoCuenta().getId().equals("-1")) {

			query.append("  AND  cn.tipoCuenta.naturalezaProcesoLiquidacion  = ? ");
			params.add(criterio.getTipoTenencia().getTipoCuenta().getId());
			tipos.add(new StringType());
		}

		if (StringUtils.isNotBlank(criterio.getTipoTenencia().getTipoNaturaleza().getId()) && !"-1".equals(criterio.getTipoTenencia().getTipoNaturaleza().getId())) {

			query.append("  AND  cn.tipoCuenta.naturalezaContable  = ? ");
			params.add(criterio.getTipoTenencia().getTipoNaturaleza().getId());
			tipos.add(new StringType());
		}

		
		return (CuentaDTO)getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query q = session.createQuery(query.toString()).setParameters(params.toArray(), tipos.toArray(new Type[] {}));
				Iterator itRes = q.list().iterator();
				CuentaDTO resultado = null;
				if (itRes.hasNext()) {
					if (criterio.getTipoTenencia().getTipoCuenta().getId().equals(TipoCuentaDTO.CUENTA_CONTROLADA)) {
						resultado = DTOAssembler.crearCuentaDTO((CuentaControlada) itRes.next());
					} else {
						resultado = DTOAssembler.crearCuentaDTO((CuentaNombrada) itRes.next());
					}

				}
				return resultado;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.dao.CuentaDAO#buscarCuentasPorFragmentoNumeroCuenta(com.indeval.estadocuenta.core.application.dto.CuentaDTO)
	 */
	@SuppressWarnings("unchecked")
	public List<CuentaDTO> buscarCuentasPorFragmentoNumeroCuenta(final CuentaDTO criterio) {
		final StringBuffer query = new StringBuffer();
		final ArrayList<Object> params = new ArrayList<Object>();
		final ArrayList<Type> tipos = new ArrayList<Type>();
		
		Class cuentaClass = null;
		
		if (criterio.getTipoTenencia().getTipoCuenta().getId().equals(TipoCuentaDTO.CUENTA_CONTROLADA)) {
			cuentaClass = CuentaControlada.class;
		} else {
			cuentaClass = CuentaNombrada.class;
		}

		query.append("FROM " + cuentaClass.getName() + " cn " + " join fetch cn.institucion " + " join fetch cn.tipoCuenta "
				+ " join fetch cn.institucion.tipoInstitucion " + " WHERE ");
		query.append(" (cn.institucion.tipoInstitucion.claveTipoInstitucion || " + "cn.institucion.folioInstitucion || cn.cuenta ) LIKE ? ");

		params.add(criterio.getNumeroCuenta() + "%");
		tipos.add(new StringType());

		query.append(" AND cn.tipoCuenta.tipoCustodia  = ? ");
		params.add(criterio.getTipoTenencia().getTipoCustodia());
		tipos.add(new StringType());

		if (criterio.getTipoTenencia().getIdTipoCuenta() > 0) {

			query.append(" AND cn.tipoCuenta.idTipoCuenta  = ? ");
			params.add(new BigInteger(String.valueOf(criterio.getTipoTenencia().getIdTipoCuenta())));
			tipos.add(new BigIntegerType());
		}

		if (!criterio.getTipoTenencia().getTipoCuenta().getId().equals("-1")) {

			query.append("  AND  cn.tipoCuenta.naturalezaProcesoLiquidacion  = ? ");
			params.add(criterio.getTipoTenencia().getTipoCuenta().getId());
			tipos.add(new StringType());
		}

		if (!"-1".equals(criterio.getTipoTenencia().getTipoNaturaleza().getId())) {

			query.append("  AND  cn.tipoCuenta.naturalezaContable  = ? ");
			params.add(criterio.getTipoTenencia().getTipoNaturaleza().getId());
			tipos.add(new StringType());
		}

		if (criterio.getInstitucion().getId() > 0) {

			query.append("  AND cn.institucion.idInstitucion  = ? ");
			params.add(new BigInteger(String.valueOf(criterio.getInstitucion().getId())));
			tipos.add(new BigIntegerType());
		}

		query.append(" order by (cn.institucion.tipoInstitucion.claveTipoInstitucion || " + "cn.institucion.folioInstitucion || cn.cuenta )");

		return (List<CuentaDTO>)getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				ArrayList<CuentaDTO> cuentas = new ArrayList<CuentaDTO>();
				Query q = session.createQuery(query.toString()).setParameters(params.toArray(), tipos.toArray(new Type[] {})).setMaxResults(
						CommonConstants.CANTIDAD_MAXIMA_RESULTADOS_SUGGESTION_BOX);

				Iterator itRes = q.list().iterator();

				while (itRes.hasNext()) {
					CuentaDTO resultado = null;
					if (criterio.getTipoTenencia().getTipoCuenta().getId().equals(TipoCuentaDTO.CUENTA_CONTROLADA)) {
						resultado = DTOAssembler.crearCuentaDTO((CuentaControlada) itRes.next());
					} else {
						resultado = DTOAssembler.crearCuentaDTO((CuentaNombrada) itRes.next());
					}
					cuentas.add(resultado);
				}

				return cuentas;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.dao.CuentaDAO#buscarCuentasControladasEfectivo(com.indeval.estadocuenta.core.application.dto.CuentaEfectivoDTO,
	 *      com.indeval.estadocuenta.core.application.dto.EstadoPaginacionDTO)
	 */
	@SuppressWarnings("unchecked")
	public List<CuentaEfectivoDTO> buscarCuentasControladasEfectivo(CuentaEfectivoDTO criterio, EstadoPaginacionDTO estadoPaginacion) {
		Iterator itRes = buscarCuentasEfectivo(criterio, CuentaControlada.class, estadoPaginacion, null).iterator();
		List<CuentaEfectivoDTO> resultado = new ArrayList<CuentaEfectivoDTO>();
		while (itRes.hasNext()) {
			resultado.add(DTOAssembler.crearCuentaEfectivoDTO((CuentaControlada) itRes.next()));
		}
		return resultado;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.dao.CuentaDAO#buscarCuentasNombradasEfectivo(com.indeval.estadocuenta.core.application.dto.CuentaEfectivoDTO,
	 *      com.indeval.estadocuenta.core.application.dto.EstadoPaginacionDTO)
	 */
	@SuppressWarnings("unchecked")
	public List<CuentaEfectivoDTO> buscarCuentasNombradasEfectivo(CuentaEfectivoDTO criterio, EstadoPaginacionDTO estadoPaginacion) {
		Iterator itRes = buscarCuentasEfectivo(criterio, CuentaNombrada.class, estadoPaginacion, null).iterator();
		List<CuentaEfectivoDTO> resultado = new ArrayList<CuentaEfectivoDTO>();
		while (itRes.hasNext()) {
			resultado.add(DTOAssembler.crearCuentaEfectivoDTO((CuentaNombrada) itRes.next()));
		}
		return resultado;

	}

	/**
	 * Obtiene la proyección del total de cuentas de efectivo controladas o
	 * nombradas considerando el tipo de cuenta, el cliente y el tipo de
	 * custodia.
	 * 
	 * @param criterio
	 *            un DTO con los criterios a considerar en la consulta.
	 * @param tipoCuenta
	 *            el tipo de cuenta buscada, debe ser una referencia a
	 *            {@link CuentaNombrada} o {@link CuentaControlada}.
	 * @param idsValidos
	 *            Conjunto de ids válidos para realizar la búsqueda, estos ids
	 *            se toman en cuentan cuando es diferente de null, si los
	 *            criterios tienen una longitud de 0 entonces no se debe de
	 *            tomar en cuenta ningn resultado.
	 * @return el número de cuentas que cumplen con el criterio proporcionado en
	 *         la base de datos.
	 */
	@SuppressWarnings("unchecked")
	private int contarCuentasEfectivo(CuentaEfectivoDTO criterio, Class tipoCuenta, List<Long> idsValidos) {
		final ArrayList<Object> params = new ArrayList<Object>();
		final ArrayList<Type> tipos = new ArrayList<Type>();
		final StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT COUNT(*) FROM " + tipoCuenta.getName() + " cn WHERE ");
		queryString.append("  cn.tipoCuenta.tipoCustodia  = ? ");
		params.add(criterio.getTipoCustodia());
		tipos.add(new StringType());

		int res = 0;
		if (!"-1".equals(criterio.getNumeroCuenta())) {
			queryString.append(" AND (cn.institucion.tipoInstitucion.claveTipoInstitucion || " + "cn.institucion.folioInstitucion || cn.cuenta )  = ? ");
			params.add(criterio.getNumeroCuenta());
			tipos.add(new StringType());
		}

		if (!criterio.getTipoCuenta().getId().equals("-1")) {

			queryString.append(" AND cn.tipoCuenta.naturalezaProcesoLiquidacion  = ? ");
			params.add(criterio.getTipoCuenta().getId());
			tipos.add(new StringType());
		}

		if (!criterio.getTipoNaturaleza().getId().equals("-1")) {

			queryString.append(" AND cn.tipoCuenta.naturalezaContable  = ? ");
			params.add(criterio.getTipoNaturaleza().getId());
			tipos.add(new StringType());
		}

		if (criterio.getInstitucion().getId() > 0) {

			queryString.append(" AND cn.institucion.idInstitucion  = ? ");
			params.add(new BigInteger(String.valueOf(criterio.getInstitucion().getId())));
			tipos.add(new BigIntegerType());
		}

		if (criterio.getInstitucion() != null && StringUtils.isNotBlank(criterio.getInstitucion().getClaveTipoInstitucion())) {

			queryString.append(" AND cn.institucion.tipoInstitucion.claveTipoInstitucion  = ? ");
			params.add(criterio.getInstitucion().getClaveTipoInstitucion());
			tipos.add(new StringType());
		}

		if (idsValidos != null && idsValidos.size() > 0) {
			if (criterio.getTipoCuenta().getId().equals(TipoCuentaConstants.TIPO_CONTROLADA)) {
				queryString.append(" AND cn.idCuentaControlada in ( ");
			} else {
				queryString.append(" AND cn.idCuentaNombrada in ( ");
			}
			Iterator itIds = idsValidos.iterator();
			while (itIds.hasNext()) {
				queryString.append(itIds.next().toString());
				if (itIds.hasNext()) {
					queryString.append(",");
				}
			}
			queryString.append(")");
		}

		if (idsValidos == null || idsValidos.size() > 0) {
			res = (Integer)getHibernateTemplate().execute(new HibernateCallback(){
				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					Query query = session.createQuery(queryString.toString()).setParameters(params.toArray(), tipos.toArray(new Type[] {}));
					return ((Number) query.uniqueResult()).intValue();
				}
			});
		}

		return res;
		
	}

	/**
	 * Busca las cuentas de efectivo controladas o nombradas considerando el
	 * tipo de cuenta, el cliente y el tipo de custodia.
	 * 
	 * @param criterio
	 *            un DTO con los criterios a considerar en la consulta.
	 * @param tipoCuenta
	 *            el tipo de cuenta buscada, debe ser una referencia a
	 *            {@link CuentaNombrada} o {@link CuentaControlada}.
	 * @param idsValidos
	 *            Si el parámetro es diferente de nulo, se consideraran
	 *            únicamente las cuentas cuyos identificadores coincidan con
	 *            algun identificador del listado.
	 * @return una lista con los objetos de tipo cuenta que cumplan con los
	 *         criterios de consulta.
	 */
	@SuppressWarnings("unchecked")
	private List buscarCuentasEfectivo(CuentaEfectivoDTO criterio, Class tipoCuenta, final EstadoPaginacionDTO estadoPaginacion, List<Long> idsValidos) {
		final ArrayList<Object> params = new ArrayList<Object>();
		final ArrayList<Type> tipos = new ArrayList<Type>();
		final StringBuffer queryString = new StringBuffer();
		queryString.append("FROM " + tipoCuenta.getName() + " cn " + " join fetch cn.institucion " + " join fetch cn.tipoCuenta "
				+ " join fetch cn.institucion.tipoInstitucion " + " WHERE ");

		queryString.append("  cn.tipoCuenta.tipoCustodia  = ? ");
		params.add(criterio.getTipoCustodia());
		tipos.add(new StringType());

		if (!"-1".equals(criterio.getNumeroCuenta())) {
			queryString.append(" and (cn.institucion.tipoInstitucion.claveTipoInstitucion || " + "cn.institucion.folioInstitucion || cn.cuenta )  = ? ");
			params.add(criterio.getNumeroCuenta());
			tipos.add(new StringType());
		}

		if (!criterio.getTipoCuenta().getId().equals("-1")) {

			queryString.append(" and cn.tipoCuenta.naturalezaProcesoLiquidacion  = ? ");
			params.add(criterio.getTipoCuenta().getId());
			tipos.add(new StringType());
		}

		if (!criterio.getTipoNaturaleza().getId().equals("-1")) {

			queryString.append(" and cn.tipoCuenta.naturalezaContable  = ? ");
			params.add(criterio.getTipoNaturaleza().getId());
			tipos.add(new StringType());
		}

		if (criterio.getInstitucion().getId() > 0) {

			queryString.append(" and cn.institucion.idInstitucion  = ? ");
			params.add(new BigInteger(String.valueOf(criterio.getInstitucion().getId())));
			tipos.add(new BigIntegerType());
		}

		if (criterio.getInstitucion() != null && StringUtils.isNotBlank(criterio.getInstitucion().getClaveTipoInstitucion())) {

			queryString.append(" AND cn.institucion.tipoInstitucion.claveTipoInstitucion  = ? ");
			params.add(criterio.getInstitucion().getClaveTipoInstitucion());
			tipos.add(new StringType());
		}

		List resultado = new ArrayList();

		if (idsValidos != null && idsValidos.size() > 0) {
			if (criterio.getTipoCuenta().getId().equals(TipoCuentaConstants.TIPO_CONTROLADA)) {
				queryString.append(" AND cn.idCuentaControlada in ( ");
			} else {
				queryString.append(" AND cn.idCuentaNombrada in ( ");
			}
			Iterator itIds = idsValidos.iterator();
			while (itIds.hasNext()) {
				queryString.append(itIds.next().toString());
				if (itIds.hasNext()) {
					queryString.append(",");
				}
			}
			queryString.append(")");
		}

		queryString.append(" order by (cn.institucion.tipoInstitucion.claveTipoInstitucion || " + "cn.institucion.folioInstitucion || cn.cuenta )");

		if (idsValidos == null || idsValidos.size() > 0) {

			resultado = (List)getHibernateTemplate().execute(new HibernateCallback(){
				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					Query query = session.createQuery(queryString.toString()).setParameters(params.toArray(), tipos.toArray(new Type[] {}));

					if (estadoPaginacion != null) {

						query.setFirstResult(((estadoPaginacion.getNumeroPagina() - 1) * estadoPaginacion.getRegistrosPorPagina()));
						query.setMaxResults(estadoPaginacion.getRegistrosPorPagina());
					}
					return query.list();
				}
			});

		}

		
		
		return resultado;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.dao.CuentaDAO#buscarCuentaEfectivoPorNumeroCuenta(com.indeval.estadocuenta.core.application.dto.CuentaEfectivoDTO)
	 */
	@SuppressWarnings("unchecked")
	public CuentaEfectivoDTO buscarCuentaEfectivoPorNumeroCuenta(CuentaEfectivoDTO criterio) {
		StringBuffer query = new StringBuffer();
		Class cuentaClass = null;

		if (criterio.getTipoCuenta().getId().equals(TipoCuentaDTO.CUENTA_CONTROLADA)) {
			cuentaClass = CuentaControlada.class;
		} else {
			cuentaClass = CuentaNombrada.class;
		}
		query.append("FROM " + cuentaClass.getName() + " cn " + " join fetch cn.institucion " + " join fetch cn.tipoCuenta "
				+ " join fetch cn.institucion.tipoInstitucion " + " WHERE ");
		query.append(" (cn.institucion.tipoInstitucion.claveTipoInstitucion || " + "cn.institucion.folioInstitucion || cn.cuenta )  = ? "
				+ " and cn.tipoCuenta.naturalezaProcesoLiquidacion = ?");

		Iterator itRes = getHibernateTemplate().find(query.toString(), new Object[] { criterio.getNumeroCuenta(), criterio.getTipoCuenta().getId() })
				.iterator();

		CuentaEfectivoDTO resultado = null;
		if (itRes.hasNext()) {

			if (criterio.getTipoCuenta().getId().equals(TipoCuentaDTO.CUENTA_CONTROLADA)) {
				resultado = DTOAssembler.crearCuentaEfectivoDTO((CuentaControlada) itRes.next());
			} else {
				resultado = DTOAssembler.crearCuentaEfectivoDTO((CuentaNombrada) itRes.next());
			}
		}
		return resultado;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.dao.CuentaDAO#obtenerProyeccionDeBusquedaDeCuentasControladasEfectivo(com.indeval.estadocuenta.core.application.dto.CuentaEfectivoDTO)
	 */
	public int obtenerProyeccionDeBusquedaDeCuentasControladasEfectivo(CuentaEfectivoDTO criterio) {

		return contarCuentasEfectivo(criterio, CuentaControlada.class, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.dao.CuentaDAO#obtenerProyeccionDeBusquedaDeCuentasNombradasEfectivo(com.indeval.estadocuenta.core.application.dto.CuentaEfectivoDTO)
	 */
	public int obtenerProyeccionDeBusquedaDeCuentasNombradasEfectivo(CuentaEfectivoDTO criterio) {

		return contarCuentasEfectivo(criterio, CuentaNombrada.class, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.dao.CuentaDAO#obtenerProyeccionDeBusquedaDeCuentasControladas(com.indeval.estadocuenta.core.application.dto.CuentaDTO,
	 *      java.util.Set)
	 */
	public int obtenerProyeccionDeBusquedaDeCuentasControladas(CuentaDTO criterio, List<Long> idsCuentaValidos) {
		return contarCuentas(criterio, CuentaControlada.class, idsCuentaValidos);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.dao.CuentaDAO#obtenerProyeccionDeBusquedaDeCuentasNombradas(com.indeval.estadocuenta.core.application.dto.CuentaDTO,
	 *      java.util.Set)
	 */
	public int obtenerProyeccionDeBusquedaDeCuentasNombradas(CuentaDTO criterio, List<Long> idsCuentaValidos) {
		return contarCuentas(criterio, CuentaNombrada.class, idsCuentaValidos);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.dao.CuentaDAO#buscarCuentasControladas(com.indeval.estadocuenta.core.application.dto.CuentaDTO,
	 *      com.indeval.estadocuenta.core.application.dto.EstadoPaginacionDTO,
	 *      java.util.Set)
	 */
	@SuppressWarnings("unchecked")
	public List<CuentaDTO> buscarCuentasControladas(CuentaDTO criterio, EstadoPaginacionDTO estadoPaginacion, List<Long> idsValidos) {
		Iterator itRes = buscarCuentas(criterio, CuentaControlada.class, estadoPaginacion, idsValidos).iterator();
		List<CuentaDTO> resultado = new ArrayList<CuentaDTO>();
		while (itRes.hasNext()) {
			resultado.add(DTOAssembler.crearCuentaDTO((CuentaControlada) itRes.next()));
		}
		return resultado;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.dao.CuentaDAO#buscarCuentasNombradas(com.indeval.estadocuenta.core.application.dto.CuentaDTO,
	 *      com.indeval.estadocuenta.core.application.dto.EstadoPaginacionDTO,
	 *      java.util.Set)
	 */
	@SuppressWarnings("unchecked")
	public List<CuentaDTO> buscarCuentasNombradas(CuentaDTO criterio, EstadoPaginacionDTO estadoPaginacion, List<Long> idsValidos) {
		Iterator itRes = buscarCuentas(criterio, CuentaNombrada.class, estadoPaginacion, idsValidos).iterator();
		List<CuentaDTO> resultado = new ArrayList<CuentaDTO>();
		while (itRes.hasNext()) {
			resultado.add(DTOAssembler.crearCuentaDTO((CuentaNombrada) itRes.next()));
		}
		return resultado;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.dao.CuentaDAO#obtenerProyeccionDeBusquedaDeCuentasControladasEfectivo(com.indeval.estadocuenta.core.application.dto.CuentaEfectivoDTO,
	 *      java.util.Set)
	 */
	public int obtenerProyeccionDeBusquedaDeCuentasControladasEfectivo(CuentaEfectivoDTO criterio, List<Long> idsValidos) {
		return contarCuentasEfectivo(criterio, CuentaControlada.class, idsValidos);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.dao.CuentaDAO#obtenerProyeccionDeBusquedaDeCuentasNombradasEfectivo(com.indeval.estadocuenta.core.application.dto.CuentaEfectivoDTO,
	 *      java.util.Set)
	 */
	public int obtenerProyeccionDeBusquedaDeCuentasNombradasEfectivo(CuentaEfectivoDTO criterio, List<Long> idsValidos) {
		return contarCuentasEfectivo(criterio, CuentaNombrada.class, idsValidos);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.dao.CuentaDAO#buscarCuentasControladasEfectivo(com.indeval.estadocuenta.core.application.dto.CuentaEfectivoDTO,
	 *      com.indeval.estadocuenta.core.application.dto.EstadoPaginacionDTO,
	 *      java.util.Set)
	 */
	@SuppressWarnings("unchecked")
	public List<CuentaEfectivoDTO> buscarCuentasControladasEfectivo(CuentaEfectivoDTO criterio, EstadoPaginacionDTO estadoPaginacion, List<Long> idsValidos) {
		Iterator itRes = buscarCuentasEfectivo(criterio, CuentaControlada.class, estadoPaginacion, idsValidos).iterator();
		List<CuentaEfectivoDTO> resultado = new ArrayList<CuentaEfectivoDTO>();
		while (itRes.hasNext()) {
			resultado.add(DTOAssembler.crearCuentaEfectivoDTO((CuentaControlada) itRes.next()));
		}
		return resultado;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.dao.CuentaDAO#buscarCuentasNombradasEfectivo(com.indeval.estadocuenta.core.application.dto.CuentaEfectivoDTO,
	 *      com.indeval.estadocuenta.core.application.dto.EstadoPaginacionDTO,
	 *      java.util.Set)
	 */
	@SuppressWarnings("unchecked")
	public List<CuentaEfectivoDTO> buscarCuentasNombradasEfectivo(CuentaEfectivoDTO criterio, EstadoPaginacionDTO estadoPaginacion, List<Long> idsValidos) {
		Iterator itRes = buscarCuentasEfectivo(criterio, CuentaNombrada.class, estadoPaginacion, idsValidos).iterator();
		List<CuentaEfectivoDTO> resultado = new ArrayList<CuentaEfectivoDTO>();
		while (itRes.hasNext()) {
			resultado.add(DTOAssembler.crearCuentaEfectivoDTO((CuentaNombrada) itRes.next()));
		}
		return resultado;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.dao.CuentaDAO#buscarCuentasPorFragmentoNumeroCuenta(com.indeval.estadocuenta.core.application.dto.CuentaEfectivoDTO)
	 */
	@SuppressWarnings("unchecked")
	public List<CuentaEfectivoDTO> buscarCuentasPorFragmentoNumeroCuenta(final CuentaEfectivoDTO criterio) {
		final StringBuffer query = new StringBuffer();
		final ArrayList<Object> params = new ArrayList<Object>();
		final ArrayList<Type> tipos = new ArrayList<Type>();
		Class cuentaClass = null;
		if (criterio.getTipoCuenta().getId().equals(TipoCuentaDTO.CUENTA_CONTROLADA)) {
			cuentaClass = CuentaControlada.class;
		} else {
			cuentaClass = CuentaNombrada.class;
		}

		query.append("FROM " + cuentaClass.getName() + " cn " + " join fetch cn.institucion " + " join fetch cn.tipoCuenta "
				+ " join fetch cn.institucion.tipoInstitucion " + " WHERE ");
		query.append(" (cn.institucion.tipoInstitucion.claveTipoInstitucion || " + "cn.institucion.folioInstitucion || cn.cuenta ) LIKE ? ");

		params.add(criterio.getNumeroCuenta() + "%");
		tipos.add(new StringType());

		query.append(" AND cn.tipoCuenta.tipoCustodia  = ? ");
		params.add(criterio.getTipoCustodia());
		tipos.add(new StringType());

		if (!criterio.getTipoCuenta().getId().equals("-1")) {

			query.append("  AND  cn.tipoCuenta.naturalezaProcesoLiquidacion  = ? ");
			params.add(criterio.getTipoCuenta().getId());
			tipos.add(new StringType());
		}

		if (!"-1".equals(criterio.getTipoNaturaleza().getId())) {

			query.append("  AND  cn.tipoCuenta.naturalezaContable  = ? ");
			params.add(criterio.getTipoNaturaleza().getId());
			tipos.add(new StringType());
		}

		if (criterio.getInstitucion().getId() > 0) {

			query.append("  AND cn.institucion.idInstitucion  = ? ");
			params.add(new BigInteger(String.valueOf(criterio.getInstitucion().getId())));
			tipos.add(new BigIntegerType());
		}

		query.append(" order by (cn.institucion.tipoInstitucion.claveTipoInstitucion || " + "cn.institucion.folioInstitucion || cn.cuenta )");

		
		
		return (List<CuentaEfectivoDTO>)getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				ArrayList<CuentaEfectivoDTO> cuentas = new ArrayList<CuentaEfectivoDTO>();
				Query q = session.createQuery(query.toString()).setParameters(params.toArray(), tipos.toArray(new Type[] {})).setMaxResults(
						CommonConstants.CANTIDAD_MAXIMA_RESULTADOS_SUGGESTION_BOX);

				Iterator itRes = q.list().iterator();

				while (itRes.hasNext()) {
					CuentaEfectivoDTO resultado = null;
					if (criterio.getTipoCuenta().getId().equals(TipoCuentaDTO.CUENTA_CONTROLADA)) {
						resultado = DTOAssembler.crearCuentaEfectivoDTO((CuentaControlada) itRes.next());
					} else {
						resultado = DTOAssembler.crearCuentaEfectivoDTO((CuentaNombrada) itRes.next());
					}
					cuentas.add(resultado);
				}

				return cuentas;
			}
		});
	}
}
