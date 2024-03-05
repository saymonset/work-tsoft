/**
 * 2H Software - Bursatec
 * 
 * Sistema de Consulta de Estados de Cuenta
 *
 * Dec 5, 2007
 *
 */
package com.indeval.portaldali.persistence.dao.estadocuenta.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.BigIntegerType;
import org.hibernate.type.DateType;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.indeval.portaldali.middleware.dto.CriterioOrdenamientoDTO;
import com.indeval.portaldali.middleware.dto.CuponDTO;
import com.indeval.portaldali.middleware.dto.PosicionDTO;
import com.indeval.portaldali.middleware.dto.TipoCuentaDTO;
import com.indeval.portaldali.middleware.dto.util.DTOAssembler;
import com.indeval.portaldali.middleware.dto.util.EstadoPaginacionDTO;
import com.indeval.portaldali.middleware.dto.util.EstadoPaginacionExtended;
import com.indeval.portaldali.middleware.services.common.constants.DaliConstants;
import com.indeval.portaldali.modelo.to.estadocuenta.TotalesPosicionTO;
import com.indeval.portaldali.persistence.dao.estadocuenta.PosicionDAO;
import com.indeval.portaldali.persistence.model.Cupon;
import com.indeval.portaldali.persistence.model.Emision;
import com.indeval.portaldali.persistence.model.PosicionControlada;
import com.indeval.portaldali.persistence.model.PosicionControladaHistorico;
import com.indeval.portaldali.persistence.model.PosicionNombrada;
import com.indeval.portaldali.persistence.model.PosicionNombradaHistorico;
import com.indeval.portaldali.persistence.util.ConsultaPosicionControladaUtil;
import com.indeval.portaldali.persistence.util.ConsultaPosicionNombradaUtil;
import com.indeval.portaldali.persistencia.posicion.Posicion;


/**
 * Implementación del servicio para las operaciones realizadas sobre las tablas
 * de posiciones controladas y nombradas de la base de datos.
 * 
 * @author Sandra Flores Arrieta
 * 
 */
public class PosicionDAOImpl extends HibernateDaoSupport implements PosicionDAO {

	/*
	 * (non-Javadoc)
	 * @see com.indeval.portaldali.persistence.dao.estadocuenta.PosicionDAO#buscarPosicionControladaPorId(long)
	 */
	public PosicionDTO buscarPosicionControladaPorId(final long idPosicion) {
		PosicionDTO posicionDTO = null;
		posicionDTO = (PosicionDTO) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				DetachedCriteria detachedCriteria = DetachedCriteria.forClass(PosicionControlada.class);
				detachedCriteria.add(Restrictions.eq("idPosicion", new BigInteger(String.valueOf(idPosicion))));
				Criteria criteria = detachedCriteria.getExecutableCriteria(session);
				PosicionControlada posicionControlada = (PosicionControlada) criteria.uniqueResult();
				return DTOAssembler.crearPosicionControladaDTO(posicionControlada);
			}
		});
		return posicionDTO;
	}

	/*
	 * (non-Javadoc)
	 * @see com.indeval.portaldali.persistence.dao.estadocuenta.PosicionDAO#buscarPosicionNombradaPorId(long)
	 */
	public PosicionDTO buscarPosicionNombradaPorId(final long idPosicion) {
		PosicionDTO posicionDTO = null;
		posicionDTO = (PosicionDTO) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				DetachedCriteria detachedCriteria = DetachedCriteria.forClass(PosicionNombrada.class);
				detachedCriteria.add(Restrictions.eq("idPosicion", new BigInteger(String.valueOf(idPosicion))));
				Criteria criteria = detachedCriteria.getExecutableCriteria(session);
				PosicionNombrada posicionNombrada = (PosicionNombrada) criteria.uniqueResult();
				return DTOAssembler.crearPosicionNombradaDTO(posicionNombrada);
			}
		});
		return posicionDTO;
	}

	/*
	 * (non-Javadoc)
	 * @see com.indeval.portaldali.persistence.dao.estadocuenta.PosicionDAO#buscarPosicionControladaHistoricoPorId(java.util.Date, long)
	 */
	public PosicionDTO buscarPosicionControladaHistoricoPorId(final Date fechaPosicion, final long idPosicion) {
		PosicionDTO posicionDTO = null;
		posicionDTO = (PosicionDTO) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				DetachedCriteria detachedCriteria = DetachedCriteria.forClass(PosicionControladaHistorico.class);
				detachedCriteria.add(Restrictions.eq("primaryKey.idPosicion", new BigInteger(String.valueOf(idPosicion))));
				detachedCriteria.add(Restrictions.eq("primaryKey.fecha", fechaPosicion));
				Criteria criteria = detachedCriteria.getExecutableCriteria(session);
				PosicionControladaHistorico posicionControladaHistorico = (PosicionControladaHistorico) criteria.uniqueResult();
				return DTOAssembler.crearPosicionControladaDTO(posicionControladaHistorico);
			}
		});
		return posicionDTO;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portaldali.persistence.dao.estadocuenta.PosicionDAO#buscarPosicionNombradaHistoricoPorId(java.util.Date, long)
	 */
	public PosicionDTO buscarPosicionNombradaHistoricoPorId(final Date fecha, final long idPosicion) {
		PosicionDTO posicionDTO = null;
		posicionDTO = (PosicionDTO) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				DetachedCriteria detachedCriteria = DetachedCriteria.forClass(PosicionNombradaHistorico.class);
				detachedCriteria.add(Restrictions.eq("primaryKey.idPosicion", new BigInteger(String.valueOf(idPosicion))));
				detachedCriteria.add(Restrictions.eq("primaryKey.fecha", fecha));
				Criteria criteria = detachedCriteria.getExecutableCriteria(session);
				PosicionNombradaHistorico posicionNombradaHistorico = (PosicionNombradaHistorico) criteria.uniqueResult();
				return DTOAssembler.crearPosicionNombradaDTO(posicionNombradaHistorico);
			}
		});
		return posicionDTO;
	}

	/**
	 * Consulta la entidad de Cupon para una cierta emisión en una cierta fecha.
	 * 
	 * @param emision
	 *            Emisión para la cul se buscara el cupón
	 * @param fechaCupon
	 *            Fecha para la cul se buscara el cupón
	 * @return Objeto de mapeo asociado al cupón encontrado, null en caso de no
	 *         localizar el cupón
	 */
	@SuppressWarnings("rawtypes")
	private Cupon obtenerCupon(Emision emision, Date fechaCupon) {
		Cupon cupon = null;
		Iterator res = getHibernateTemplate().find(
				"FROM " + Cupon.class.getName() + " c WHERE  "
						+ " c.cupon.emision.idEmision = ? and ? betweeidEstadoCuponn trunc(c.fechaInicio) and trunc(c.fechaFin)",
				new Object[] { emision.getIdEmision(), fechaCupon }).iterator();
		if (res.hasNext()) {
			cupon = (Cupon) res.next();
		}
		return cupon;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.dao.PosicionDAO#obtenerIdentificadoresValidosParaBoveda(com.indeval.estadocuenta.core.application.dto.PosicionDTO)
	 */
	@SuppressWarnings("unchecked")
	public List<Long> obtenerIdentificadoresValidosParaBoveda(PosicionDTO criterio) {
		final ArrayList<Object> parametros = new ArrayList<Object>();
		final StringBuffer query = new StringBuffer();
		final ArrayList<Type> tipos = new ArrayList<Type>();

		if (criterio.getCuenta().getTipoTenencia().getTipoCuenta().getId().equals(TipoCuentaDTO.CUENTA_CONTROLADA)) {
			query.append("SELECT DISTINCT poc.boveda.idBoveda  FROM " + PosicionControlada.class.getName() + " poc ");
			query.append(construirCriteriosConsultaPosicionesControladasAlDiaActual(criterio, "poc", parametros, tipos));
		} else {
			query.append("SELECT DISTINCT pon.boveda.idBoveda  FROM " + PosicionNombrada.class.getName() + " pon ");
			query.append(construirCriteriosConsultaPosicionesNombradasAlDiaActual(criterio, "pon", parametros, tipos));
		}

		return (List<Long>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query hQuery = session.createQuery(query.toString());
				hQuery.setParameters(parametros.toArray(), tipos.toArray(new Type[] {}));

				return DTOAssembler.transformarListaBigIntegerEnLong(hQuery.list());
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.persistence.dao.estadocuenta.PosicionDAO#obtenerIdentificadoresValidosParaBovedaHistoricas(com.indeval.portaldali.middleware.dto.PosicionDTO)
	 */
	@SuppressWarnings("unchecked")
	public List<Long> obtenerIdentificadoresValidosParaBovedaHistoricas(PosicionDTO criterio) {
		final ArrayList<Object> parametros = new ArrayList<Object>();
		final StringBuffer query = new StringBuffer();
		final ArrayList<Type> tipos = new ArrayList<Type>();

		if (criterio.getCuenta().getTipoTenencia().getTipoCuenta().getId().equals(TipoCuentaDTO.CUENTA_CONTROLADA)) {
			query.append("SELECT DISTINCT pch.boveda.idBoveda  FROM " + PosicionControladaHistorico.class.getName() + " pch ");
			query.append(construirCriteriosPosicionControladaHistoricas(criterio, "pch", parametros, tipos));
		} else {
			query.append("SELECT DISTINCT pnh.boveda.idBoveda  FROM " + PosicionNombradaHistorico.class.getName() + " pnh ");
			query.append(construirCriteriosPosicionNombradaHistoricas(criterio, "pnh", parametros, tipos));
		}

		return (List<Long>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query hQuery = session.createQuery(query.toString());
				hQuery.setParameters(parametros.toArray(), tipos.toArray(tipos.toArray(new Type[] {})));

				return DTOAssembler.transformarListaBigIntegerEnLong(hQuery.list());
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.dao.PosicionDAO#obtenerIdentificadoresValidosParaEmision(com.indeval.estadocuenta.core.application.dto.PosicionDTO)
	 */
	@SuppressWarnings("unchecked")
	public List<Long> obtenerIdentificadoresValidosParaEmision(PosicionDTO criterio) {
		final ArrayList<Object> parametros = new ArrayList<Object>();
		final StringBuffer query = new StringBuffer();
		final ArrayList<Type> tipos = new ArrayList<Type>();

		if (criterio.getCuenta().getTipoTenencia().getTipoCuenta().getId().equals(TipoCuentaDTO.CUENTA_CONTROLADA)) {
			query.append("SELECT DISTINCT poc.cupon.emision.idEmision  FROM " + PosicionControlada.class.getName() + " poc ");
			query.append(construirCriteriosConsultaPosicionesControladasAlDiaActual(criterio, "poc", parametros, tipos));
			query.append(" ORDER BY poc.cupon.emision.idEmision ");
		} else {
			query.append("SELECT DISTINCT pon.cupon.emision.idEmision FROM " + PosicionNombrada.class.getName() + " pon ");
			query.append(construirCriteriosConsultaPosicionesNombradasAlDiaActual(criterio, "pon", parametros, tipos));
			query.append(" ORDER BY pon.cupon.emision.idEmision ");
		}

		return (List<Long>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query hQuery = session.createQuery(query.toString());
				hQuery.setParameters(parametros.toArray(), tipos.toArray(tipos.toArray(new Type[] {})));

				return DTOAssembler.transformarListaBigIntegerEnLong(hQuery.list());
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.persistence.dao.estadocuenta.PosicionDAO#obtenerIdentificadoresValidosParaEmisionHistoricas(com.indeval.portaldali.middleware.dto.PosicionDTO)
	 */
	@SuppressWarnings("unchecked")
	public List<Long> obtenerIdentificadoresValidosParaEmisionHistoricas(PosicionDTO criterio) {
		final ArrayList<Object> parametros = new ArrayList<Object>();
		final StringBuffer query = new StringBuffer();
		final ArrayList<Type> tipos = new ArrayList<Type>();

		if (criterio.getCuenta().getTipoTenencia().getTipoCuenta().getId().equals(TipoCuentaDTO.CUENTA_CONTROLADA)) {
			query.append("SELECT DISTINCT pch.cupon.emision.idEmision  FROM " + PosicionControladaHistorico.class.getName() + " pch ");
			query.append(construirCriteriosPosicionControladaHistoricas(criterio, "pch", parametros, tipos));
			query.append(" ORDER BY pch.cupon.emision.idEmision ");
		} else {
			query.append("SELECT DISTINCT pnh.cupon.emision.idEmision FROM " + PosicionNombradaHistorico.class.getName() + " pnh ");
			query.append(construirCriteriosPosicionNombradaHistoricas(criterio, "pnh", parametros, tipos));
			query.append(" ORDER BY pnh.cupon.emision.idEmision ");
		}

		return (List<Long>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query hQuery = session.createQuery(query.toString());
				hQuery.setParameters(parametros.toArray(), tipos.toArray(tipos.toArray(new Type[] {})));

				return DTOAssembler.transformarListaBigIntegerEnLong(hQuery.list());
			}
		});
	}

	/**
	 * Construye la sección del query de consulta de posición nombrada referente
	 * a los criterios de emisión, bóveda, cuenta e institución.
	 * 
	 * @param criterio
	 *            Criterios a utilizar
	 * @param alias
	 *            Alias asignado a la tabla de Estadod posición nombrada
	 * @param params
	 *            Se utiliza para colocar los parámetros utilizados
	 * @param tipos
	 *            Se utiliza para colocar los tipos de los parámetros utilizados
	 * @return Cadena correspondiente a la sección de criterios del query de
	 *         posición nombrada
	 */
	private String construirCriteriosPosicionNombradaHistoricas(PosicionDTO criterio, String alias, ArrayList<Object> params, ArrayList<Type> tipos) {
		StringBuffer query = new StringBuffer();

		query.append("WHERE " + alias + ".fecha = TRUNC(?) AND (" + alias + ".posicionDisponible +" + alias
				+ ".posicionNoDisponible)" + " != 0 ");

		params.add(criterio.getFechaFinal());
		tipos.add(new DateType());

		if (criterio.isNoDisponible()) {
			query.append(" AND " + alias + ".posicionNoDisponible > 0 ");
		}

		// Boveda
		if (criterio.getBoveda() != null && criterio.getBoveda().getId() > 0) {
			query.append(" AND " + alias + ".boveda.idBoveda = ? ");
			params.add(new BigInteger(String.valueOf(criterio.getBoveda().getId())));
			tipos.add(new BigIntegerType());
		}

		// emision

		if (criterio.getEmision() != null && criterio.getEmision().getIsin() != null && criterio.getEmision().getIsin().length() > 0) {
			query.append(" and " + alias + ".cupon.emision.isin = ? ");
			params.add(criterio.getEmision().getIsin().toUpperCase());
			tipos.add(new StringType());
		}

		if (criterio.getEmision() != null && criterio.getEmision().getSerie() != null && StringUtils.isNotBlank(criterio.getEmision().getSerie().getSerie())
				&& !criterio.getEmision().getSerie().getSerie().equals("-1")) {
			query.append(" and " + alias + ".cupon.emision.serie = ? ");
			params.add(criterio.getEmision().getSerie().getSerie());
			tipos.add(new StringType());
		}

		if (criterio.getEmision() != null && criterio.getEmision().getEmisora() != null && 
				(criterio.getEmision().getEmisora().getId() > 0 || criterio.getEmision().getEmisora().getId() == DaliConstants.VALOR_COMBO_NINGUNO)) {
			query.append(" and " + alias + ".cupon.emision.idEmisora = ? ");
			params.add(new BigInteger(String.valueOf(criterio.getEmision().getEmisora().getId())));
			tipos.add(new BigIntegerType());
		}

		if (criterio.getEmision() != null && criterio.getEmision().getTipoValor() != null && 
				(criterio.getEmision().getTipoValor().getId() > 0 || criterio.getEmision().getTipoValor().getId() == DaliConstants.VALOR_COMBO_NINGUNO) ) {
			query.append(" and " + alias + ".cupon.emision.instrumento.idInstrumento = ? ");
			params.add(new BigInteger(String.valueOf(criterio.getEmision().getTipoValor().getId())));
			tipos.add(new BigIntegerType());
		}

		if (criterio.getEmision() != null && criterio.getEmision().getTipoValor() != null && criterio.getEmision().getTipoValor().getMercado() != null
				&& criterio.getEmision().getTipoValor().getMercado().getId() > 0) {
			if (criterio.getEmision().getTipoValor().getMercado().getId() != DaliConstants.ID_MERCADO_DINERO) {
				query.append(" and " + alias + ".cupon.emision.instrumento.mercado.idMercado = ? ");
				params.add(new BigInteger(String.valueOf(criterio.getEmision().getTipoValor().getMercado().getId())));
				tipos.add(new BigIntegerType());
			} else {
				query.append(" and " + alias + ".cupon.emision.instrumento.mercado.idMercado in (?,?) ");

				params.add(new BigInteger(String.valueOf(DaliConstants.ID_MERCADO_PAPEL_BANCARIO)));
				tipos.add(new BigIntegerType());

				params.add(new BigInteger(String.valueOf(DaliConstants.ID_MERCADO_PAPEL_GUBER)));
				tipos.add(new BigIntegerType());
			}
		}

		// Cuenta
		if (criterio.getCuenta() != null && !"-1".equals(criterio.getCuenta().getNumeroCuenta())) {
			query.append(" and (" + " " + alias + ".cuentaNombrada.institucion.tipoInstitucion.claveTipoInstitucion || " + alias
					+ ".cuentaNombrada.institucion.folioInstitucion || " + alias + ".cuentaNombrada.cuenta " + ") = ? ");
			params.add(criterio.getCuenta().getNumeroCuenta());
			tipos.add(new StringType());
		}

		if (criterio.getCuenta() != null && criterio.getCuenta().getTipoTenencia().getIdTipoCuenta() > 0) {
			query.append(" and " + alias + ".cuentaNombrada.tipoCuenta.idTipoCuenta = ? ");
			params.add(new BigInteger(String.valueOf(criterio.getCuenta().getTipoTenencia().getIdTipoCuenta())));
			tipos.add(new BigIntegerType());
		}

		if (criterio.getCuenta().getTipoTenencia().getTipoCustodia() != null && !"-1".equals(criterio.getCuenta().getTipoTenencia().getTipoCustodia())) {
			query.append(" and " + alias + ".cuentaNombrada.tipoCuenta.tipoCustodia = ? ");
			params.add(criterio.getCuenta().getTipoTenencia().getTipoCustodia());
			tipos.add(new StringType());
		}

		query.append(" and " + alias + ".cuentaNombrada.tipoCuenta.naturalezaContable = ? ");
		params.add(criterio.getCuenta().getTipoTenencia().getTipoNaturaleza().getId());
		tipos.add(new StringType());

		query.append(" and " + alias + ".cuentaNombrada.tipoCuenta.naturalezaProcesoLiquidacion = ? ");
		params.add(criterio.getCuenta().getTipoTenencia().getTipoCuenta().getId());
		tipos.add(new StringType());

		// Institucion
		if (criterio.getCuenta().getInstitucion() != null && criterio.getCuenta().getInstitucion().getId() > 0) {
			query.append(" and " + alias + ".cuentaNombrada.institucion.idInstitucion = ? ");
			params.add(new BigInteger(String.valueOf(criterio.getCuenta().getInstitucion().getId())));
			tipos.add(new BigIntegerType());
		}

		// Tipo de institucion
		if (criterio.getCuenta().getInstitucion().getClaveTipoInstitucion() != null) {
			query.append(" and " + alias + ".cuentaNombrada.institucion.tipoInstitucion.claveTipoInstitucion = ? ");
			params.add(new BigInteger(String.valueOf(criterio.getCuenta().getInstitucion().getClaveTipoInstitucion())));
			tipos.add(new BigIntegerType());
		}

		return query.toString();

	}

	/**
	 * Construye los criterios para realizar una consulta de posiciones al d&iacute;a
	 * actual
	 * 
	 * @param criterio
	 *            el DTO con los criterios para realizar la consulta.
	 * @param alias
	 *            Alias de la tabla de posici&oacute;n
	 * @param params
	 *            Lista en la cual se colocar&iacute;n los par&iacute;metros para la consulta.
	 * @param tipos
	 *            Lista en la cual se colocar&iacute;n los tipos de datos de los
	 *            par&iacute;metros para la cosulta.
	 * @return una cadena con el HQL necesario para realizar la consulta de
	 *         acuerdo a los criterios proporcionados.
	 */
	private String construirCriteriosConsultaPosicionesNombradasAlDiaActual(PosicionDTO criterio, String alias, ArrayList<Object> params, ArrayList<Type> tipos) {
		StringBuffer query = new StringBuffer();

		query.append(" WHERE " + alias + ".cuentaNombrada.tipoCuenta.naturalezaContable = ? AND (" + alias + ".posicionDisponible +" + alias
				+ ".posicionNoDisponible)" + " != 0 ");// chris
		params.add(criterio.getCuenta().getTipoTenencia().getTipoNaturaleza().getId());
		tipos.add(new StringType());

		query.append(" and " + alias + ".cuentaNombrada.tipoCuenta.naturalezaProcesoLiquidacion = ? ");
		params.add(criterio.getCuenta().getTipoTenencia().getTipoCuenta().getId());
		tipos.add(new StringType());

		if (criterio.isNoDisponible()) {
			query.append(" AND " + alias + ".posicionNoDisponible > 0 ");
		}

		// Boveda
		if (criterio.getBoveda() != null && criterio.getBoveda().getId() > 0) {
			query.append(" and " + alias + ".boveda.idBoveda = ? ");
			params.add(new BigInteger(String.valueOf(criterio.getBoveda().getId())));
			tipos.add(new BigIntegerType());
		}

		// emision

		if (criterio.getEmision() != null && criterio.getEmision().getIsin() != null && criterio.getEmision().getIsin().length() > 0) {
			query.append(" and " + alias + ".cupon.emision.isin = ? ");
			params.add(criterio.getEmision().getIsin().toUpperCase());
			tipos.add(new StringType());
		}

		if (criterio.getEmision() != null && criterio.getEmision().getSerie() != null && StringUtils.isNotBlank(criterio.getEmision().getSerie().getSerie())
				&& !criterio.getEmision().getSerie().getSerie().equals("-1")) {
			query.append(" and " + alias + ".cupon.emision.serie = ? ");
			params.add(criterio.getEmision().getSerie().getSerie().toUpperCase());
			tipos.add(new StringType());
		}

		if (criterio.getEmision() != null && criterio.getEmision().getEmisora() != null && 
				(criterio.getEmision().getEmisora().getId() > 0 || criterio.getEmision().getEmisora().getId() == DaliConstants.VALOR_COMBO_NINGUNO) ) {
			query.append(" and " + alias + ".cupon.emision.idEmisora = ? ");
			params.add(new BigInteger(String.valueOf(criterio.getEmision().getEmisora().getId())));
			tipos.add(new BigIntegerType());
		}

		if (criterio.getEmision() != null && criterio.getEmision().getTipoValor() != null && 
				(criterio.getEmision().getTipoValor().getId() > 0 || criterio.getEmision().getTipoValor().getId() == DaliConstants.VALOR_COMBO_NINGUNO) ) {
			query.append(" and " + alias + ".cupon.emision.instrumento.idInstrumento = ? ");
			params.add(new BigInteger(String.valueOf(criterio.getEmision().getTipoValor().getId())));
			tipos.add(new BigIntegerType());
		}

		if (criterio.getEmision() != null && criterio.getEmision().getTipoValor() != null && criterio.getEmision().getTipoValor().getMercado() != null
				&& criterio.getEmision().getTipoValor().getMercado().getId() > 0) {
			if (DaliConstants.ID_MERCADO_DINERO != criterio.getEmision().getTipoValor().getMercado().getId()) {
				query.append(" and " + alias + ".cupon.emision.instrumento.mercado.idMercado = ? ");
				params.add(new BigInteger(String.valueOf(criterio.getEmision().getTipoValor().getMercado().getId())));
				tipos.add(new BigIntegerType());
			} else {
				query.append(" and " + alias + ".cupon.emision.instrumento.mercado.idMercado in (?,?) ");

				params.add(new BigInteger(String.valueOf(DaliConstants.ID_MERCADO_PAPEL_BANCARIO)));
				tipos.add(new BigIntegerType());

				params.add(new BigInteger(String.valueOf(DaliConstants.ID_MERCADO_PAPEL_GUBER)));
				tipos.add(new BigIntegerType());
			}
		}

		// Cuenta
		if (criterio.getCuenta() != null && StringUtils.isNotBlank(criterio.getCuenta().getNumeroCuenta())
				&& !"-1".equals(criterio.getCuenta().getNumeroCuenta())) {
			query.append(" and (" + " " + alias + ".cuentaNombrada.institucion.tipoInstitucion.claveTipoInstitucion || " + alias
					+ ".cuentaNombrada.institucion.folioInstitucion || " + alias + ".cuentaNombrada.cuenta " + ") = ? ");
			params.add(criterio.getCuenta().getNumeroCuenta());
			tipos.add(new StringType());
		}

		if (criterio.getCuenta() != null && criterio.getCuenta().getTipoTenencia().getIdTipoCuenta() > 0) {
			query.append(" and " + alias + ".cuentaNombrada.tipoCuenta.idTipoCuenta = ? ");
			params.add(new BigInteger(String.valueOf(criterio.getCuenta().getTipoTenencia().getIdTipoCuenta())));
			tipos.add(new BigIntegerType());
		}

		if (criterio.getCuenta().getTipoTenencia().getTipoCustodia() != null && !"-1".equals(criterio.getCuenta().getTipoTenencia().getTipoCustodia())) {
			query.append(" and " + alias + ".cuentaNombrada.tipoCuenta.tipoCustodia = ? ");
			params.add(criterio.getCuenta().getTipoTenencia().getTipoCustodia());
			tipos.add(new StringType());
		}

		// Institucion
		if (criterio.getCuenta().getInstitucion() != null && criterio.getCuenta().getInstitucion().getId() > 0) {
			query.append(" and " + alias + ".cuentaNombrada.institucion.idInstitucion = ? ");
			params.add(new BigInteger(String.valueOf(criterio.getCuenta().getInstitucion().getId())));
			tipos.add(new BigIntegerType());
		}

		// Tipo de institucion
		if (criterio.getCuenta().getInstitucion().getClaveTipoInstitucion() != null) {
			query.append(" and " + alias + ".cuentaNombrada.institucion.tipoInstitucion.claveTipoInstitucion = ? ");
			params.add(new BigInteger(String.valueOf(criterio.getCuenta().getInstitucion().getClaveTipoInstitucion())));
			tipos.add(new BigIntegerType());
		}

		if(params.size() != tipos.size()) {
			throw new RuntimeException("Los tamanios de los arreglos de parametros y tipos no coinciden.");
		}
		
		return query.toString();
	}

	/**
	 * Construye la sección del query de consulta de posición controlada
	 * referente a los criterios de emisión, bóveda, cuenta e institución.
	 * 
	 * @param criterio
	 *            Criterios a utilizar
	 * @param alias
	 *            Alias asignado a la tabla de Estadod posición nombrada
	 * @param params
	 *            Se utiliza para colocar los parámetros utilizados
	 * @param tipos
	 *            Se utiliza para colocar los tipos de los parámetros utilizados
	 * @return Cadena correspondiente a la sección de criterios del query de
	 *         posición controlada
	 */
	private String construirCriteriosPosicionControladaHistoricas(PosicionDTO criterio, String alias, ArrayList<Object> params, ArrayList<Type> tipos) {

		StringBuffer query = new StringBuffer();

		query.append("WHERE " + alias + ".fecha = TRUNC(?)  AND (" + alias + ".posicion)" + " != 0 ");
		params.add(criterio.getFechaFinal());
		tipos.add(new DateType());

		// Boveda
		if (criterio.getBoveda().getId() > 0) {
			query.append(" and " + alias + ".boveda.idBoveda = ? ");
			params.add(new BigInteger(String.valueOf(criterio.getBoveda().getId())));
			tipos.add(new BigIntegerType());
		}

		// emision

		if (criterio.getEmision().getIsin() != null && criterio.getEmision().getIsin().length() > 0) {
			query.append(" and " + alias + ".cupon.emision.isin = ? ");
			params.add(criterio.getEmision().getIsin().toUpperCase());
			tipos.add(new StringType());
		}

		if (criterio.getEmision().getSerie().getSerie() != null && StringUtils.isNotBlank(criterio.getEmision().getSerie().getSerie())
				&& !criterio.getEmision().getSerie().getSerie().equals("-1")) {
			query.append(" and " + alias + ".cupon.emision.serie = ? ");
			params.add(criterio.getEmision().getSerie().getSerie());
			tipos.add(new StringType());
		}

		if (criterio.getEmision().getEmisora().getId() > 0 || criterio.getEmision().getEmisora().getId() == DaliConstants.VALOR_COMBO_NINGUNO) {
			query.append(" and " + alias + ".cupon.emision.idEmisora = ? ");
			params.add(new BigInteger(String.valueOf(criterio.getEmision().getEmisora().getId())));
			tipos.add(new BigIntegerType());
		}

		if (criterio.getEmision().getTipoValor().getId() > 0 || criterio.getEmision().getTipoValor().getId() == DaliConstants.VALOR_COMBO_NINGUNO) {
			query.append(" and " + alias + ".cupon.emision.instrumento.idInstrumento = ? ");
			params.add(new BigInteger(String.valueOf(criterio.getEmision().getTipoValor().getId())));
			tipos.add(new BigIntegerType());
		}

		if (criterio.getEmision().getTipoValor().getMercado().getId() > 0) {
			if (DaliConstants.ID_MERCADO_DINERO != criterio.getEmision().getTipoValor().getMercado().getId()) {
				query.append(" and " + alias + ".cupon.emision.instrumento.mercado.idMercado = ? ");
				params.add(new BigInteger(String.valueOf(criterio.getEmision().getTipoValor().getMercado().getId())));
				tipos.add(new BigIntegerType());
			} else {

				query.append(" and " + alias + ".cupon.emision.instrumento.mercado.idMercado in (?,?) ");

				params.add(new BigInteger(String.valueOf(DaliConstants.ID_MERCADO_PAPEL_BANCARIO)));
				tipos.add(new BigIntegerType());

				params.add(new BigInteger(String.valueOf(DaliConstants.ID_MERCADO_PAPEL_GUBER)));
				tipos.add(new BigIntegerType());
			}
		}

		// Cuenta
		if (!"-1".equals(criterio.getCuenta().getNumeroCuenta())) {
			query.append(" and (" + alias + ".cuentaControlada.institucion.tipoInstitucion.claveTipoInstitucion || " + alias
					+ ".cuentaControlada.institucion.folioInstitucion || " + alias + ".cuentaControlada.cuenta ) = ? ");
			params.add(criterio.getCuenta().getNumeroCuenta());
			tipos.add(new StringType());
		}

		if (criterio.getCuenta().getTipoTenencia().getIdTipoCuenta() > 0) {
			query.append(" and " + alias + ".cuentaControlada.tipoCuenta.idTipoCuenta = ? ");
			params.add(new BigInteger(String.valueOf(criterio.getCuenta().getTipoTenencia().getIdTipoCuenta())));
			tipos.add(new BigIntegerType());
		}

		if (criterio.getCuenta().getTipoTenencia().getTipoCustodia() != null && !"-1".equals(criterio.getCuenta().getTipoTenencia().getTipoCustodia())) {
			query.append(" and " + alias + ".cuentaControlada.tipoCuenta.tipoCustodia = ? ");
			params.add(criterio.getCuenta().getTipoTenencia().getTipoCustodia());
			tipos.add(new StringType());
		}

		query.append(" and " + alias + ".cuentaControlada.tipoCuenta.naturalezaContable = ? ");
		params.add(criterio.getCuenta().getTipoTenencia().getTipoNaturaleza().getId());
		tipos.add(new StringType());

		query.append(" and " + alias + ".cuentaControlada.tipoCuenta.naturalezaProcesoLiquidacion = ? ");
		params.add(criterio.getCuenta().getTipoTenencia().getTipoCuenta().getId());
		tipos.add(new StringType());
		// Institucion
		if (criterio.getCuenta().getInstitucion().getId() > 0) {
			query.append(" and " + alias + ".cuentaControlada.institucion.idInstitucion = ? ");
			params.add(new BigInteger(String.valueOf(criterio.getCuenta().getInstitucion().getId())));
			tipos.add(new BigIntegerType());
		}

		// Tipo de institucion
		if (criterio.getCuenta().getInstitucion().getClaveTipoInstitucion() != null) {
			query.append(" and " + alias + ".cuentaControlada.institucion.tipoInstitucion.claveTipoInstitucion = ? ");
			params.add(new BigInteger(String.valueOf(criterio.getCuenta().getInstitucion().getClaveTipoInstitucion())));
			tipos.add(new BigIntegerType());
		}

		return query.toString();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.dao.PosicionDAO#obtenerIdentificadoresValidosParaCuenta(com.indeval.estadocuenta.core.application.dto.PosicionDTO)
	 */
	@SuppressWarnings("unchecked")
	public List<Long> obtenerIdentificadoresValidosParaCuenta(PosicionDTO criterio) {
		final ArrayList<Object> parametros = new ArrayList<Object>();
		final StringBuffer query = new StringBuffer();
		final ArrayList<Type> tipos = new ArrayList<Type>();
		if (criterio.getCuenta().getTipoTenencia().getTipoCuenta().getId().equals(TipoCuentaDTO.CUENTA_CONTROLADA)) {
			query.append("SELECT DISTINCT poc.cuentaControlada.idCuentaControlada  FROM " + PosicionControlada.class.getName() + " poc ");
			query.append(construirCriteriosConsultaPosicionesControladasAlDiaActual(criterio, "poc", parametros, tipos));
		} else {
			query.append("SELECT DISTINCT pon.cuentaNombrada.idCuentaNombrada  FROM " + PosicionNombrada.class.getName() + " pon ");
			query.append(construirCriteriosConsultaPosicionesNombradasAlDiaActual(criterio, "pon", parametros, tipos));
		}

		return (List<Long>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query hQuery = session.createQuery(query.toString());
				hQuery.setParameters(parametros.toArray(), tipos.toArray(tipos.toArray(new Type[] {})));

				return DTOAssembler.transformarListaBigIntegerEnLong(hQuery.list());
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.persistence.dao.estadocuenta.PosicionDAO#obtenerIdentificadoresValidosParaCuentaHistoricas(com.indeval.portaldali.middleware.dto.PosicionDTO)
	 */
	@SuppressWarnings("unchecked")
	public List<Long> obtenerIdentificadoresValidosParaCuentaHistoricas(PosicionDTO criterio) {
		final ArrayList<Object> parametros = new ArrayList<Object>();
		final StringBuffer query = new StringBuffer();
		final ArrayList<Type> tipos = new ArrayList<Type>();
		if (criterio.getCuenta().getTipoTenencia().getTipoCuenta().getId().equals(TipoCuentaDTO.CUENTA_CONTROLADA)) {
			query.append("SELECT DISTINCT pch.cuentaControlada.idCuentaControlada  FROM " + PosicionControladaHistorico.class.getName() + " pch ");
			query.append(construirCriteriosPosicionControladaHistoricas(criterio, "pch", parametros, tipos));
		} else {
			query.append("SELECT DISTINCT pnh.cuentaNombrada.idCuentaNombrada  FROM " + PosicionNombradaHistorico.class.getName() + " pnh ");
			query.append(construirCriteriosPosicionNombradaHistoricas(criterio, "pnh", parametros, tipos));
		}

		return (List<Long>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query hQuery = session.createQuery(query.toString());
				hQuery.setParameters(parametros.toArray(), tipos.toArray(tipos.toArray(new Type[] {})));

				return DTOAssembler.transformarListaBigIntegerEnLong(hQuery.list());
			}
		});
	}

	/**
	 * Construye la sección del query de consulta de posición controlada
	 * referente a los criterios de emisión, bóveda, cuenta e institución.
	 * 
	 * @param criterio
	 *            Criterios a utilizar
	 * @param alias
	 *            Alias asignado a la tabla de Estadod posición nombrada
	 * @param params
	 *            Se utiliza para colocar los parámetros utilizados
	 * @param tipos
	 *            Se utiliza para colocar los tipos de los parámetros utilizados
	 * @return Cadena correspondiente a la sección de criterios del query de
	 *         posición controlada
	 */
	private Object construirCriteriosConsultaPosicionesControladasAlDiaActual(PosicionDTO criterio, String alias, ArrayList<Object> params,
			ArrayList<Type> tipos) {
		StringBuffer query = new StringBuffer();

		query.append(" WHERE " + alias + ".cuentaControlada.tipoCuenta.naturalezaContable = ? AND (" + alias + ".posicion)" + " != 0 ");// chris
		params.add(criterio.getCuenta().getTipoTenencia().getTipoNaturaleza().getId());
		tipos.add(new StringType());

		query.append(" and " + alias + ".cuentaControlada.tipoCuenta.naturalezaProcesoLiquidacion = ? ");
		params.add(criterio.getCuenta().getTipoTenencia().getTipoCuenta().getId());
		tipos.add(new StringType());

		// Boveda
		if (criterio.getBoveda() != null && criterio.getBoveda().getId() > 0) {
			query.append(" and " + alias + ".boveda.idBoveda = ? ");
			params.add(new BigInteger(String.valueOf(criterio.getBoveda().getId())));
			tipos.add(new BigIntegerType());
		}

		// emision

		if (criterio.getEmision() != null && criterio.getEmision().getIsin() != null && criterio.getEmision().getIsin().length() > 0) {
			query.append(" and " + alias + ".cupon.emision.isin = ? ");
			params.add(criterio.getEmision().getIsin().toUpperCase());
			tipos.add(new StringType());
		}

		if (criterio.getEmision() != null && criterio.getEmision().getSerie() != null && StringUtils.isNotBlank(criterio.getEmision().getSerie().getSerie())
				&& !criterio.getEmision().getSerie().getSerie().equals("-1")) {
			query.append(" and " + alias + ".cupon.emision.serie = ? ");
			params.add(criterio.getEmision().getSerie().getSerie().toUpperCase());
			tipos.add(new StringType());
		}

		if (criterio.getEmision() != null && criterio.getEmision().getEmisora() != null && 
				(criterio.getEmision().getEmisora().getId() > 0 || criterio.getEmision().getEmisora().getId() == DaliConstants.VALOR_COMBO_NINGUNO) ) {
			query.append(" and " + alias + ".cupon.emision.idEmisora = ? ");
			params.add(new BigInteger(String.valueOf(criterio.getEmision().getEmisora().getId())));
			tipos.add(new BigIntegerType());
		}

		if (criterio.getEmision() != null && criterio.getEmision().getTipoValor() != null && 
				(criterio.getEmision().getTipoValor().getId() > 0 || criterio.getEmision().getTipoValor().getId() == DaliConstants.VALOR_COMBO_NINGUNO)  ) {
			query.append(" and " + alias + ".cupon.emision.instrumento.idInstrumento = ? ");
			params.add(new BigInteger(String.valueOf(criterio.getEmision().getTipoValor().getId())));
			tipos.add(new BigIntegerType());
		}

		if (criterio.getEmision() != null 
				&& criterio.getEmision().getTipoValor() != null 
				&& criterio.getEmision().getTipoValor().getMercado() != null
				&& criterio.getEmision().getTipoValor().getMercado().getId() > 0) {
			if (criterio.getEmision().getTipoValor().getMercado().getId() != DaliConstants.ID_MERCADO_DINERO) {
				query.append(" and " + alias + ".cupon.emision.instrumento.mercado.idMercado = ? ");
				params.add(new BigInteger(String.valueOf(criterio.getEmision().getTipoValor().getMercado().getId())));
				tipos.add(new BigIntegerType());
			} else {
				query.append(" and " + alias + ".cupon.emision.instrumento.mercado.idMercado in (?,?) ");

				params.add(new BigInteger(String.valueOf(DaliConstants.ID_MERCADO_PAPEL_BANCARIO)));
				tipos.add(new BigIntegerType());

				params.add(new BigInteger(String.valueOf(DaliConstants.ID_MERCADO_PAPEL_GUBER)));
				tipos.add(new BigIntegerType());
			}
		}

		// Cuenta
		if (criterio.getCuenta() != null && !"-1".equals(criterio.getCuenta().getNumeroCuenta())) {
			query.append(" and (" + " " + alias + ".cuentaControlada.institucion.tipoInstitucion.claveTipoInstitucion || " + alias
					+ ".cuentaControlada.institucion.folioInstitucion || " + alias + ".cuentaControlada.cuenta " + ") = ? ");
			params.add(criterio.getCuenta().getNumeroCuenta());
			tipos.add(new StringType());
		}

		if (criterio.getCuenta() != null && criterio.getCuenta().getTipoTenencia().getIdTipoCuenta() > 0) {
			query.append(" and " + alias + ".cuentaControlada.tipoCuenta.idTipoCuenta = ? ");
			params.add(new BigInteger(String.valueOf(criterio.getCuenta().getTipoTenencia().getIdTipoCuenta())));
			tipos.add(new BigIntegerType());
		}

		if (criterio.getCuenta().getTipoTenencia().getTipoCustodia() != null && !"-1".equals(criterio.getCuenta().getTipoTenencia().getTipoCustodia())) {
			query.append(" and " + alias + ".cuentaControlada.tipoCuenta.tipoCustodia = ? ");
			params.add(criterio.getCuenta().getTipoTenencia().getTipoCustodia());
			tipos.add(new StringType());
		}

		// Institucion
		if (criterio.getCuenta().getInstitucion() != null && criterio.getCuenta().getInstitucion().getId() > 0) {
			query.append(" and " + alias + ".cuentaControlada.institucion.idInstitucion = ? ");
			params.add(new BigInteger(String.valueOf(criterio.getCuenta().getInstitucion().getId())));
			tipos.add(new BigIntegerType());
		}

		// Tipo de institucion
		if (criterio.getCuenta().getInstitucion().getClaveTipoInstitucion() != null) {
			query.append(" and " + alias + ".cuentaControlada.institucion.tipoInstitucion.claveTipoInstitucion = ? ");
			params.add(new BigInteger(String.valueOf(criterio.getCuenta().getInstitucion().getClaveTipoInstitucion())));
			tipos.add(new BigIntegerType());
		}

		return query.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.dao.PosicionDAO#obtenerCuponDTO(long,
	 *      java.util.Date)
	 */
	public CuponDTO obtenerCuponDTO(long idEmision, Date fecha) {
		CuponDTO dto = null;
		Emision emision = (Emision) getHibernateTemplate().get(Emision.class, new BigInteger(String.valueOf(idEmision)));
		if (emision != null) {
			Cupon cupon = this.obtenerCupon(emision, fecha);
			dto = DTOAssembler.crearCuponDTO(cupon);
		}

		return dto;
	}

	/*
	 * (non-Javadoc)
	 * @see com.indeval.portaldali.persistence.dao.estadocuenta.PosicionDAO#buscarPosicionNombrada(com.indeval.portaldali.middleware.dto.PosicionDTO, com.indeval.portaldali.middleware.dto.util.EstadoPaginacionDTO, com.indeval.portaldali.middleware.dto.CriterioOrdenamientoDTO, boolean)
	 */
	@SuppressWarnings("unchecked")
	public List<Posicion> buscarPosicionNombrada(PosicionDTO criterio, final EstadoPaginacionDTO paginacion,
			CriterioOrdenamientoDTO orden, boolean esHistorica) {
		final DetachedCriteria detachedCriteria = ConsultaPosicionNombradaUtil.crearDetachedCriteriaPosicionNombrada(criterio, esHistorica, orden);
		List<Posicion> resultadosVista = (List<Posicion>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
    			Criteria crit = detachedCriteria.getExecutableCriteria(session);
    			if(paginacion != null) {
    				crit.setFirstResult((paginacion.getNumeroPagina() - 1) * paginacion.getRegistrosPorPagina());
                    crit.setMaxResults(paginacion.getRegistrosPorPagina());
                    crit.setFetchSize(paginacion.getRegistrosPorPagina());
    			}
    			return crit.list();
			}
		});
		return resultadosVista;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portaldali.persistence.dao.estadocuenta.PosicionDAO#obtenerProyeccionConsultaPosicionNombrada(com.indeval.portaldali.middleware.dto.PosicionDTO, boolean)
	 */
	public Integer obtenerProyeccionConsultaPosicionNombrada(PosicionDTO criterio, boolean esHistorica) {
		final DetachedCriteria detachedCriteria =
			ConsultaPosicionNombradaUtil.crearDetachedCriteriaProyeccionPosicionNombrada(criterio, esHistorica);
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria crit = detachedCriteria.getExecutableCriteria(session);
				Number resultado = (Number) crit.uniqueResult();
				return resultado.intValue();
			}
		});
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portaldali.persistence.dao.estadocuenta.PosicionDAO#obtenerTotalesPosicionNombrada(com.indeval.portaldali.middleware.dto.PosicionDTO, boolean)
	 */
	@SuppressWarnings("unchecked")
	public TotalesPosicionTO obtenerTotalesPosicionNombrada(PosicionDTO criterio, boolean esHistorica) {
		TotalesPosicionTO totalesPosicionTO = null;
		final DetachedCriteria detachedCriteria = ConsultaPosicionNombradaUtil.crearDetachedCriteriaTotalesPosicionNombrada(criterio, esHistorica);
		List<Object> resultado = (List<Object>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
    			Criteria crit = detachedCriteria.getExecutableCriteria(session);
    			return crit.list();
			}
		});
		if(resultado != null && !resultado.isEmpty()) {
			Object[] totales = (Object[]) resultado.get(0);
			totalesPosicionTO = new TotalesPosicionTO();
			totalesPosicionTO.setPosicionTotal((Number) totales[0]);
			totalesPosicionTO.setPosicionTotalDisponible((Number) totales[1]);
			totalesPosicionTO.setPosicionTotalNoDisponible((Number) totales[2]);
			totalesPosicionTO.setValuacionTotal((Number) totales[3]);
		}
		return totalesPosicionTO;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portaldali.persistence.dao.estadocuenta.PosicionDAO#buscarPosicionControlada(com.indeval.portaldali.middleware.dto.PosicionDTO, com.indeval.portaldali.middleware.dto.util.EstadoPaginacionDTO, com.indeval.portaldali.middleware.dto.CriterioOrdenamientoDTO, boolean)
	 */
	@SuppressWarnings("unchecked")
	public List<Posicion> buscarPosicionControlada(PosicionDTO criterio, final EstadoPaginacionDTO paginacion, CriterioOrdenamientoDTO orden, boolean esHistorica) {
		final DetachedCriteria detachedCriteria = ConsultaPosicionControladaUtil.crearDetachedCriteriaPosicionControlada(criterio, esHistorica, orden);
		List<Posicion> resultadosVista = (List<Posicion>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria crit = detachedCriteria.getExecutableCriteria(session);
	    			if(paginacion != null) {
					crit.setFirstResult((paginacion.getNumeroPagina() - 1) * paginacion.getRegistrosPorPagina());
	                crit.setMaxResults(paginacion.getRegistrosPorPagina());
	                crit.setFetchSize(paginacion.getRegistrosPorPagina());
				}
				return crit.list();
			}
		});
		return resultadosVista;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portaldali.persistence.dao.estadocuenta.PosicionDAO#obtenerProyeccionConsultaPosicionControlada(com.indeval.portaldali.middleware.dto.PosicionDTO, boolean)
	 */
	public Integer obtenerProyeccionConsultaPosicionControlada(PosicionDTO criterio, boolean esHistorica) {
		final DetachedCriteria detachedCriteria =
			ConsultaPosicionControladaUtil.crearDetachedCriteriaProyeccionPosicionControlada(criterio, esHistorica);
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria crit = detachedCriteria.getExecutableCriteria(session);
				Number resultado = (Number) crit.uniqueResult();
				return resultado.intValue();
			}
		});
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portaldali.persistence.dao.estadocuenta.PosicionDAO#obtenerTotalesPosicionControlada(com.indeval.portaldali.middleware.dto.PosicionDTO, boolean)
	 */
	@SuppressWarnings("unchecked")
	public TotalesPosicionTO obtenerTotalesPosicionControlada(PosicionDTO criterio, boolean esHistorica) {
		TotalesPosicionTO totalesPosicionTO = null;
		final DetachedCriteria detachedCriteria = ConsultaPosicionControladaUtil.crearDetachedCriteriaTotalesPosicionControlada(criterio, esHistorica);
		List<Object> resultado = (List<Object>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
    			Criteria crit = detachedCriteria.getExecutableCriteria(session);
    			return crit.list();
			}
		});
		if(resultado != null && !resultado.isEmpty()) {
			Object[] totales = (Object[]) resultado.get(0);
			totalesPosicionTO = new TotalesPosicionTO();
			totalesPosicionTO.setPosicionTotal((Number) totales[0]);
			totalesPosicionTO.setValuacionTotal((Number) totales[1]);
		}
		return totalesPosicionTO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.persistence.dao.estadocuenta.PosicionDAO#consultarPosicionesNombradasPorMercado(com.indeval.portaldali.middleware.dto.PosicionDTO,
	 *      com.indeval.portaldali.middleware.dto.util.EstadoPaginacionDTO,
	 *      com.indeval.portaldali.middleware.dto.CriterioOrdenamientoDTO,
	 *      java.lang.Long[])
	 */
	@SuppressWarnings("unchecked")
	public List<PosicionDTO> consultarPosicionesNombradasPorMercado(PosicionDTO criterios, final EstadoPaginacionDTO paginacion, CriterioOrdenamientoDTO orden,
			Long[] identificadoresMercado) {

		final StringBuffer query = new StringBuffer();
		final ArrayList<Object> params = new ArrayList<Object>();
		final ArrayList<Type> tipos = new ArrayList<Type>();

		query.append("FROM " + PosicionNombrada.class.getName() + " pn ");
		query.append(" join fetch pn.boveda ");
		query.append(" join fetch pn.cupon c ");
		query.append(" join fetch pn.cupon.emision ");
		query.append(" join fetch pn.cupon.emision.emisora ");
		query.append(" join fetch pn.cupon.emision.instrumento ");
		query.append(" join fetch pn.cupon.emision.instrumento.mercado ");
		query.append(" join fetch pn.cupon.emision.divisa ");
		query.append(" join fetch pn.cuentaNombrada ");
		query.append(" join fetch pn.cuentaNombrada.institucion ");
		query.append(" join fetch pn.cuentaNombrada.institucion.tipoInstitucion ");
		query.append(" join fetch pn.cuentaNombrada.tipoCuenta ");

		query.append(construirCriteriosConsultaPosicionesNombradasAlDiaActual(criterios, "pn", params, tipos));

		if (StringUtils.isNotBlank(criterios.getEmision().getEmisora().getDescripcion())) {
			query.append(" AND pn.cupon.emision.emisora.descripcion = ? ");
			params.add(criterios.getEmision().getEmisora().getDescripcion());
			tipos.add(new StringType());
		}

		if (StringUtils.isNotBlank(criterios.getEmision().getTipoValor().getClaveTipoValor())) {
			query.append(" AND pn.cupon.emision.instrumento.claveTipoValor = ? ");
			params.add(criterios.getEmision().getTipoValor().getClaveTipoValor());
			tipos.add(new StringType());
		}

		if (identificadoresMercado != null && identificadoresMercado.length > 0) {
			query.append(" AND pn.cupon.emision.instrumento.idMercado in ( ");
			for (int i = 0; i < identificadoresMercado.length; i++) {
				if (i > 0) {
					query.append(",");
				}
				query.append("?");
				params.add(new BigInteger(String.valueOf(identificadoresMercado[i])));
				tipos.add(new BigIntegerType());

			}
			query.append(") ");

		}

		// query.append("AND c.estadoCupon.idEstatusCupon = 1 ");

		if (orden != null && orden.getColumna() != null) {

			if (orden.getColumna().equals("sortCuenta")) {
				query.append(" order by ("
						+ "pn.cupon.emision.instrumento.claveTipoValor|| pn.cuentaNombrada.institucion.tipoInstitucion.claveTipoInstitucion || "
						+ " pn.cuentaNombrada.institucion.folioInstitucion || " + " pn.cuentaNombrada.cuenta ) "
						+ ", pn.cupon.emision.emisora.descripcion, pn.cupon.emision.serie, pn.cupon.claveCupon");

			}

			if (orden.getColumna().equals("sortEmisora")) {
				query.append(" order by (" + " pn.cupon.emision.emisora.descripcion  ) ");

			}
			if (orden.getColumna().equals("sortBoveda")) {
				query.append(" order by (" + " pn.boveda.idBoveda  ) ");

			}

			if (orden.getColumna().equals("sortTv")) {
				query.append(" order by (" + " pn.cupon.emision.instrumento.claveTipoValor  ) ");

			}

			if (orden.getColumna().equals("sortPosicion")) {
				query.append(" order by ( pn.posicionDisponible + pn.posicionNoDisponible  ) ");
			}

			if (orden.isAscendente()) {
				query.append(" asc ");

			} else {
				query.append(" desc ");
			}

		} else {
			if (orden.getColumna().equals("sortTv")) {
				query.append(" order by (" + " pn.cupon.emision.instrumento.claveTipoValor  ) ");

			}
		}

		return getHibernateTemplate().executeFind(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query hQuery = session.createQuery(query.toString()).setParameters(params.toArray(), tipos.toArray(new Type[] {}));
				if (paginacion != null) {

					hQuery.setFirstResult((paginacion.getNumeroPagina() - 1) * paginacion.getRegistrosPorPagina());
					hQuery.setMaxResults(paginacion.getRegistrosPorPagina());
					hQuery.setFetchSize(paginacion.getRegistrosPorPagina());
				}

				List<PosicionNombrada> posiciones = hQuery.list();
				List<PosicionDTO> resultado = new ArrayList<PosicionDTO>();

				for (PosicionNombrada posicion : posiciones) {

					resultado.add(DTOAssembler.crearPosicionNombradaDTO(posicion));
				}
				return resultado;
			}

		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.persistence.dao.estadocuenta.PosicionDAO#buscarEmisionesYPosiciones(com.indeval.portaldali.middleware.dto.PosicionDTO,
	 *      com.indeval.portaldali.middleware.dto.util.EstadoPaginacionDTO,
	 *      java.lang.Long[])
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> buscarEmisionesYPosiciones(PosicionDTO criterio, final EstadoPaginacionDTO paginacion, Long[] identificadoresMercado) {

		final StringBuffer query = new StringBuffer();
		final List<Object> params = new ArrayList<Object>();
		final List<Type> tipos = new ArrayList<Type>();

		query.append("SELECT {cup.*}, {pon.*} FROM C_CUPON cup ");
		query.append("left outer join T_POSICION_NOMBRADA pon on cup.ID_CUPON = pon.ID_CUPON ");
		query.append("left outer join C_CUENTA_NOMBRADA cun on cun.ID_CUENTA_NOMBRADA = pon.ID_CUENTA ");
		query.append("left outer join C_TIPO_CUENTA tc on tc.ID_TIPO_CUENTA = cun.ID_TIPO_CUENTA ");
		query.append("left outer join C_EMISION em on cup.ID_EMISION = em.ID_EMISION ");
		query.append("left outer join C_BOVEDA bo on em.ID_BOVEDA = bo.ID_BOVEDA ");
		query.append("left outer join C_INSTRUMENTO tv on tv.ID_INSTRUMENTO = em.ID_INSTRUMENTO ");
		query.append("left outer join C_EMISORA emi on em.ID_EMISORA = emi.ID_EMISORA ");

		query.append("WHERE cup.ID_ESTADO_CUPON = ? ");
		params.add(new BigInteger(String.valueOf(DaliConstants.ID_CUPON_VIGENTE)));
		tipos.add(new BigIntegerType());

		if (identificadoresMercado != null && identificadoresMercado.length > 0) {
			query.append(" AND tv.ID_MERCADO IN ( ");
			for (int i = 0; i < identificadoresMercado.length; i++) {
				if (i > 0) {
					query.append(",");
				}
				query.append("?");
				params.add(new BigInteger(String.valueOf(identificadoresMercado[i])));
				tipos.add(new BigIntegerType());

			}
			query.append(") ");

		}

		query.append("AND ( (");

		if (criterio.getCuenta().getInstitucion() != null && criterio.getCuenta().getInstitucion().getId() > 0) {
			query.append(" cun.ID_INSTITUCION = ? AND ");
			params.add(new BigInteger(String.valueOf(criterio.getCuenta().getInstitucion().getId())));
			tipos.add(new BigIntegerType());
		}

		if (criterio.getCuenta() != null && StringUtils.isNotBlank(criterio.getCuenta().getNumeroCuenta())
				&& !"-1".equals(criterio.getCuenta().getNumeroCuenta())) {

			query.append(" cun.CUENTA =  ? AND ");
			params.add(criterio.getCuenta().getNumeroCuenta());
			tipos.add(new StringType());
		}

		query.append(" tc.TIPO_CUSTODIA = ? ");
		params.add(criterio.getCuenta().getTipoTenencia().getTipoCustodia());
		tipos.add(new StringType());

		query.append(" AND tc.NATURALEZA_PROC_LIQ = ? ");
		params.add(criterio.getCuenta().getTipoTenencia().getTipoCuenta().getId());
		tipos.add(new StringType());

		query.append(" AND tc.NATURALEZA_CONTABLE = ? ");
		params.add(criterio.getCuenta().getTipoTenencia().getTipoNaturaleza().getId());
		tipos.add(new StringType());

		query.append(") OR (cun.CUENTA is null) ) ");

		// emision
		if (criterio.getEmision() != null && criterio.getEmision().getIsin() != null && criterio.getEmision().getIsin().length() > 0) {
			query.append(" AND em.ISIN = ? ");
			params.add(criterio.getEmision().getIsin().toUpperCase());
			tipos.add(new StringType());
		}

		if (criterio.getEmision() != null && criterio.getEmision().getCupon() != null && criterio.getEmision().getCupon().length() > 0) {
			query.append(" AND cup.CLAVE_CUPON = ? ");
			params.add(criterio.getEmision().getCupon());
			tipos.add(new StringType());
		}

		if (criterio.getEmision() != null && criterio.getEmision().getSerie() != null && StringUtils.isNotBlank(criterio.getEmision().getSerie().getSerie())
				&& !criterio.getEmision().getSerie().getSerie().equals("-1")) {
			query.append(" AND em.SERIE = ? ");
			params.add(criterio.getEmision().getSerie().getSerie().toUpperCase());
			tipos.add(new StringType());
		}

		if (criterio.getEmision() != null && criterio.getEmision().getEmisora() != null 
				&& (criterio.getEmision().getEmisora().getId() > 0 || StringUtils.isNotBlank(criterio.getEmision().getEmisora().getDescripcion())) ) {
			query.append(" AND em.ID_EMISORA = ? ");
			params.add(new BigInteger(String.valueOf(criterio.getEmision().getEmisora().getId())));
			tipos.add(new BigIntegerType());
		}

		if (criterio.getEmision() != null && criterio.getEmision().getTipoValor() != null 
				&& (criterio.getEmision().getTipoValor().getId() > 0 
						|| (StringUtils.isNotBlank(criterio.getEmision().getTipoValor().getDescripcion())
							&& !DaliConstants.DESCRIPCION_TODOS.equals(criterio.getEmision().getTipoValor().getDescripcion())) ) ) {
			query.append(" AND em.ID_INSTRUMENTO = ? ");
			params.add(new BigInteger(String.valueOf(criterio.getEmision().getTipoValor().getId())));
			tipos.add(new BigIntegerType());
		}

		query.append("ORDER BY tv.CLAVE_TIPO_VALOR, emi.CLAVE_PIZARRA, em.SERIE, cup.CLAVE_CUPON, cun.CUENTA ");

		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {

				SQLQuery sqlQuery = session.createSQLQuery(query.toString());

				sqlQuery.addEntity("cup", Cupon.class);
				sqlQuery.addEntity("pon", PosicionNombrada.class);
				sqlQuery.setParameters(params.toArray(), tipos.toArray(new Type[] {}));
				if (paginacion != null) {
					sqlQuery.setFirstResult((paginacion.getNumeroPagina() - 1) * paginacion.getRegistrosPorPagina());
					sqlQuery.setMaxResults(paginacion.getRegistrosPorPagina());
					sqlQuery.setFetchSize(paginacion.getRegistrosPorPagina());
				}
				List<Object[]> resultados = sqlQuery.list();
				List<Object[]> dtos = new ArrayList<Object[]>();

				for (Object[] resultado : resultados) {
					CuponDTO cupon = DTOAssembler.crearCuponDTO((Cupon) resultado[0]);
					PosicionDTO posicion = DTOAssembler.crearPosicionNombradaDTO((PosicionNombrada) resultado[1]);
					dtos.add(new Object[] { cupon, posicion });
				}

				return dtos;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.persistence.dao.estadocuenta.PosicionDAO#contarEmisionesYPosiciones(com.indeval.portaldali.middleware.dto.PosicionDTO,
	 *      java.lang.Long[])
	 */
	public BigDecimal contarEmisionesYPosiciones(PosicionDTO criterio, Long[] identificadoresMercado) {

		final StringBuffer query = new StringBuffer();
		final List<Object> params = new ArrayList<Object>();
		final List<Type> tipos = new ArrayList<Type>();

		query.append("SELECT COUNT(*) as cuenta FROM C_CUPON cup ");
		query.append("left outer join T_POSICION_NOMBRADA pon on cup.ID_CUPON = pon.ID_CUPON ");
		query.append("left outer join C_CUENTA_NOMBRADA cun on cun.ID_CUENTA_NOMBRADA = pon.ID_CUENTA ");
		query.append("left outer join C_TIPO_CUENTA tc on tc.ID_TIPO_CUENTA = cun.ID_TIPO_CUENTA ");
		query.append("left outer join C_EMISION em on cup.ID_EMISION = em.ID_EMISION ");
		query.append("left outer join C_INSTRUMENTO tv on tv.ID_INSTRUMENTO = em.ID_INSTRUMENTO ");
		query.append("left outer join C_BOVEDA bo on em.ID_BOVEDA = bo.ID_BOVEDA ");
		query.append("left outer join C_EMISORA emi on em.ID_EMISORA = emi.ID_EMISORA ");

		query.append("WHERE cup.ID_ESTADO_CUPON = ? ");
		params.add(new BigInteger(String.valueOf(DaliConstants.ID_CUPON_VIGENTE)));
		tipos.add(new BigIntegerType());

		if (identificadoresMercado != null && identificadoresMercado.length > 0) {
			query.append(" AND tv.ID_MERCADO IN ( ");
			for (int i = 0; i < identificadoresMercado.length; i++) {
				if (i > 0) {
					query.append(",");
				}
				query.append("?");
				params.add(new BigInteger(String.valueOf(identificadoresMercado[i])));
				tipos.add(new BigIntegerType());

			}
			query.append(") ");

		}

		query.append("AND ( (");

		if (criterio.getCuenta().getInstitucion() != null && criterio.getCuenta().getInstitucion().getId() > 0) {
			query.append(" cun.ID_INSTITUCION = ? AND ");
			params.add(new BigInteger(String.valueOf(criterio.getCuenta().getInstitucion().getId())));
			tipos.add(new BigIntegerType());
		}

		if (criterio.getCuenta() != null && StringUtils.isNotBlank(criterio.getCuenta().getNumeroCuenta())
				&& !"-1".equals(criterio.getCuenta().getNumeroCuenta())) {

			query.append(" cun.CUENTA =  ? AND ");
			params.add(criterio.getCuenta().getNumeroCuenta());
			tipos.add(new StringType());
		}

		query.append(" tc.TIPO_CUSTODIA = ? ");
		params.add(criterio.getCuenta().getTipoTenencia().getTipoCustodia());
		tipos.add(new StringType());

		query.append(" AND tc.NATURALEZA_PROC_LIQ = ? ");
		params.add(criterio.getCuenta().getTipoTenencia().getTipoCuenta().getId());
		tipos.add(new StringType());

		query.append(" AND tc.NATURALEZA_CONTABLE = ? ");
		params.add(criterio.getCuenta().getTipoTenencia().getTipoNaturaleza().getId());
		tipos.add(new StringType());

		query.append(") OR (cun.CUENTA is null) ) ");

		// emision
		if (criterio.getEmision() != null && criterio.getEmision().getIsin() != null && criterio.getEmision().getIsin().length() > 0) {
			query.append(" AND em.ISIN = ? ");
			params.add(criterio.getEmision().getIsin().toUpperCase());
			tipos.add(new StringType());
		}

		if (criterio.getEmision() != null && criterio.getEmision().getCupon() != null && criterio.getEmision().getCupon().length() > 0) {
			query.append(" AND cup.CLAVE_CUPON = ? ");
			params.add(criterio.getEmision().getCupon());
			tipos.add(new StringType());
		}

		if (criterio.getEmision() != null && criterio.getEmision().getSerie() != null && StringUtils.isNotBlank(criterio.getEmision().getSerie().getSerie())
				&& !criterio.getEmision().getSerie().getSerie().equals("-1")) {
			query.append(" AND em.SERIE = ? ");
			params.add(criterio.getEmision().getSerie().getSerie().toUpperCase());
			tipos.add(new StringType());
		}

		if (criterio.getEmision() != null && criterio.getEmision().getEmisora() != null 
				&& (criterio.getEmision().getEmisora().getId() > 0 || StringUtils.isNotBlank(criterio.getEmision().getEmisora().getDescripcion())) ) {
			query.append(" AND em.ID_EMISORA = ? ");
			params.add(new BigInteger(String.valueOf(criterio.getEmision().getEmisora().getId())));
			tipos.add(new BigIntegerType());
		}

		if (criterio.getEmision() != null && criterio.getEmision().getTipoValor() != null 
				&& (criterio.getEmision().getTipoValor().getId() > 0 
						|| (StringUtils.isNotBlank(criterio.getEmision().getTipoValor().getDescripcion())
							&& !DaliConstants.DESCRIPCION_TODOS.equals(criterio.getEmision().getTipoValor().getDescripcion())) ) ) {
			query.append(" AND em.ID_INSTRUMENTO = ? ");
			params.add(new BigInteger(String.valueOf(criterio.getEmision().getTipoValor().getId())));
			tipos.add(new BigIntegerType());
		}

		return (BigDecimal) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {

				SQLQuery sqlQuery = session.createSQLQuery(query.toString());
				sqlQuery.setParameters(params.toArray(), tipos.toArray(new Type[] {}));
				sqlQuery.addScalar("cuenta");

				return sqlQuery.uniqueResult();
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.persistence.dao.estadocuenta.PosicionDAO#buscarPosicionesDeEmisiones(com.indeval.portaldali.middleware.dto.PosicionDTO,
	 *      com.indeval.portaldali.middleware.dto.util.EstadoPaginacionDTO,
	 *      java.lang.Long[], java.util.List)
	 */
	@SuppressWarnings("unchecked")
	public List<PosicionDTO> buscarPosicionesDeEmisiones(PosicionDTO criterio, final EstadoPaginacionExtended paginacion, Long[] identificadoresMercado,
			List<Long> idsEmisiones) {

		final StringBuffer query = new StringBuffer();
		final ArrayList<Object> params = new ArrayList<Object>();
		final ArrayList<Type> tipos = new ArrayList<Type>();

		query.append("FROM " + PosicionNombrada.class.getName() + " pn ");
		query.append(" join fetch pn.boveda ");
		query.append(" join fetch pn.cupon c ");
		query.append(" join fetch pn.cupon.emision ");
		query.append(" join fetch pn.cupon.emision.emisora ");
		query.append(" join fetch pn.cupon.emision.instrumento ");
		query.append(" join fetch pn.cupon.emision.instrumento.mercado ");
		query.append(" join fetch pn.cupon.emision.divisa ");
		query.append(" join fetch pn.cuentaNombrada ");
		query.append(" join fetch pn.cuentaNombrada.institucion ");
		query.append(" join fetch pn.cuentaNombrada.institucion.tipoInstitucion ");
		query.append(" join fetch pn.cuentaNombrada.tipoCuenta ");

		query.append(" WHERE pn.cuentaNombrada.tipoCuenta.naturalezaContable = ? ");
		params.add(criterio.getCuenta().getTipoTenencia().getTipoNaturaleza().getId());
		tipos.add(new StringType());

		query.append(" and pn.cuentaNombrada.tipoCuenta.naturalezaProcesoLiquidacion = ? ");
		params.add(criterio.getCuenta().getTipoTenencia().getTipoCuenta().getId());
		tipos.add(new StringType());

		// Boveda
		if (criterio.getBoveda() != null && criterio.getBoveda().getId() > 0) {
			query.append(" and pn.boveda.idBoveda = ? ");
			params.add(new BigInteger(String.valueOf(criterio.getBoveda().getId())));
			tipos.add(new BigIntegerType());
		}

		// emision
		if (idsEmisiones != null && !idsEmisiones.isEmpty()) {
			query.append(" AND pn.cupon.idEmision IN ( ");
			for (Iterator<Long> it = idsEmisiones.iterator(); it.hasNext();) {
				query.append("?");
				params.add(new BigInteger(String.valueOf(it.next())));
				tipos.add(new BigIntegerType());
				if (it.hasNext()) {
					query.append(",");
				}
			}
			query.append(" ) ");
		}

		if (identificadoresMercado != null && identificadoresMercado.length > 0) {
			query.append(" AND pn.cupon.emision.instrumento.idMercado in ( ");
			for (int i = 0; i < identificadoresMercado.length; i++) {
				if (i > 0) {
					query.append(",");
				}
				query.append("?");
				params.add(new BigInteger(String.valueOf(identificadoresMercado[i])));
				tipos.add(new BigIntegerType());

			}
			query.append(") ");

		}

		// Cuenta
		if (criterio.getCuenta() != null && StringUtils.isNotBlank(criterio.getCuenta().getNumeroCuenta())
				&& !"-1".equals(criterio.getCuenta().getNumeroCuenta())) {
			query
					.append(" and (pn.cuentaNombrada.institucion.tipoInstitucion.claveTipoInstitucion || pn.cuentaNombrada.institucion.folioInstitucion || pn.cuentaNombrada.cuenta) = ? ");
			params.add(criterio.getCuenta().getNumeroCuenta());
			tipos.add(new StringType());
		}

		if (criterio.getCuenta() != null && criterio.getCuenta().getTipoTenencia().getIdTipoCuenta() > 0) {
			query.append(" and pn.cuentaNombrada.tipoCuenta.idTipoCuenta = ? ");
			params.add(new BigInteger(String.valueOf(criterio.getCuenta().getTipoTenencia().getIdTipoCuenta())));
			tipos.add(new BigIntegerType());
		}

		if (criterio.getCuenta().getTipoTenencia().getTipoCustodia() != null && !"-1".equals(criterio.getCuenta().getTipoTenencia().getTipoCustodia())) {
			query.append(" and pn.cuentaNombrada.tipoCuenta.tipoCustodia = ? ");
			params.add(criterio.getCuenta().getTipoTenencia().getTipoCustodia());
			tipos.add(new StringType());
		}

		// Institucion
		if (criterio.getCuenta().getInstitucion() != null && criterio.getCuenta().getInstitucion().getId() > 0) {
			query.append(" and pn.cuentaNombrada.institucion.idInstitucion = ? ");
			params.add(new BigInteger(String.valueOf(criterio.getCuenta().getInstitucion().getId())));
			tipos.add(new BigIntegerType());
		}

		query
				.append(" order by pn.cupon.emision.instrumento.claveTipoValor, pn.cupon.emision.emisora.descripcion, pn.cupon.emision.serie, pn.cupon.claveCupon, ");
		query
				.append(" pn.cuentaNombrada.institucion.tipoInstitucion.claveTipoInstitucion, pn.cuentaNombrada.institucion.folioInstitucion, pn.cuentaNombrada.cuenta ");

		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query hQuery = session.createQuery(query.toString()).setParameters(params.toArray(), tipos.toArray(new Type[] {}));
				if (paginacion != null) {

					hQuery.setFirstResult(paginacion.getSubindice());
					hQuery.setMaxResults(paginacion.getRegistrosPorPagina() + 1);
					hQuery.setFetchSize(paginacion.getRegistrosPorPagina() + 1);
				}

				List<PosicionNombrada> posiciones = hQuery.list();
				List<PosicionDTO> resultado = new ArrayList<PosicionDTO>();

				for (PosicionNombrada posicion : posiciones) {

					resultado.add(DTOAssembler.crearPosicionNombradaDTO(posicion));
				}
				return resultado;
			}
		});

	}

}
