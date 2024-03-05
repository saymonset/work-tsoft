/**
 * Sistema Portal DALI
 * 
 * Bursatec - 2H Software SA de CV
 *
 * Creado: Nov 18, 2008
 */
package com.indeval.portaldali.persistence.dao.estadocuenta.impl;

import java.math.BigInteger;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.type.BigIntegerType;
import org.hibernate.type.DateType;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.indeval.portaldali.middleware.dto.PosicionDTO;
import com.indeval.portaldali.middleware.dto.RegistroContablePosicionControladaDTO;
import com.indeval.portaldali.middleware.dto.RegistroContablePosicionNombradaDTO;
import com.indeval.portaldali.middleware.dto.util.DTOAssembler;
import com.indeval.portaldali.middleware.dto.util.EnsambladorDTOConsultasValores;
import com.indeval.portaldali.middleware.services.common.constants.DaliConstants;
import com.indeval.portaldali.persistence.dao.estadocuenta.EstadoCuentaPosicionDAOV2;
import com.indeval.portaldali.persistence.model.PosicionControlada;
import com.indeval.portaldali.persistence.model.PosicionControladaHistorico;
import com.indeval.portaldali.persistence.model.PosicionNombrada;
import com.indeval.portaldali.persistence.model.PosicionNombradaHistorico;
import com.indeval.portaldali.persistence.model.RegContValControlada;
import com.indeval.portaldali.persistence.model.RegContValControladaHistorico;
import com.indeval.portaldali.persistence.model.RegContValNombrada;
import com.indeval.portaldali.persistence.model.RegContValNombradaHistorico;

/**
 * Implementación del DAO para la consulta del estado de cuenta del DALI
 * 
 * @author José Antonio Huizar Moreno
 * @version 1.0
 * 
 */
public class EstadoCuentaPosicionDAOV2Impl extends HibernateDaoSupport implements EstadoCuentaPosicionDAOV2 {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.persistence.dao.estadocuenta.EstadoCuentaPosicionDAOV2#buscarCuentasDePosicionesControladas(com.indeval.portaldali.middleware.dto.PosicionDTO)
	 */
	@SuppressWarnings("unchecked")
	public List<Long> buscarCuentasDePosicionesControladas(final PosicionDTO criterio) {
		return (List<Long>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {

				StringBuffer query = new StringBuffer();
				List<Object> params = new ArrayList<Object>();
				List<Type> tipos = new ArrayList<Type>();
				query.append(" SELECT DISTINCT pc.idCuenta FROM " + PosicionControlada.class.getName() + " pc ");
				query.append(construirCriteriosPosicionControlada(criterio, null, "pc", params, tipos));
				Query hQuery = session.createQuery(query.toString());
				hQuery.setParameters(params.toArray(), tipos.toArray(tipos.toArray(new Type[] {})));
				List<BigInteger> resultados = hQuery.list();
				List<Long> idsCuentasPosicionControlada = DTOAssembler.transformarListaBigIntegerEnLong(resultados); 
				
				//Se colocan los objetos a null
				resultados = null;
				query = null;
				params = null;
				tipos = null;
				
				return idsCuentasPosicionControlada;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.persistence.dao.estadocuenta.EstadoCuentaPosicionDAOV2#buscarCuentasDePosicionesControladasHistoricas(com.indeval.portaldali.middleware.dto.PosicionDTO)
	 */
	@SuppressWarnings("unchecked")
	public List<Long> buscarCuentasDePosicionesControladasHistoricas(final PosicionDTO criterio, final Date fecha) {
		return (List<Long>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuffer query = new StringBuffer();
				List<Object> params = new ArrayList<Object>();
				List<Type> tipos = new ArrayList<Type>();
				query.append(" SELECT DISTINCT pch.idCuenta FROM " + PosicionControladaHistorico.class.getName() + " pch ");
				query.append(construirCriteriosPosicionControlada(criterio, null, "pch", params, tipos));
				query.append(" AND pch.fecha = TRUNC(?) ");
				params.add(fecha);
				tipos.add(new DateType());
				Query hQuery = session.createQuery(query.toString());
				hQuery.setParameters(params.toArray(), tipos.toArray(tipos.toArray(new Type[] {})));
				List<BigInteger> resultados = hQuery.list();
				List<Long> idsCuentasPosicionControladaHistorica = DTOAssembler.transformarListaBigIntegerEnLong(resultados);

				//Se colocan los objetos a null
				query = null;
				params = null;
				tipos = null;
				resultados = null;
				return idsCuentasPosicionControladaHistorica;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.persistence.dao.estadocuenta.EstadoCuentaPosicionDAOV2#buscarCuentasDePosicionesNombradas(com.indeval.portaldali.middleware.dto.PosicionDTO)
	 */
	@SuppressWarnings("unchecked")
	public List<Long> buscarCuentasDePosicionesNombradas(final PosicionDTO criterio) {
		return (List<Long>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuffer query = new StringBuffer();
				List<Object> params = new ArrayList<Object>();
				List<Type> tipos = new ArrayList<Type>();
				query.append(" SELECT DISTINCT pon.idCuentaNombrada FROM " + PosicionNombrada.class.getName() + " pon ");
				query.append(construirCriteriosPosicionNombrada(criterio, null, "pon", params, tipos));
				Query hQuery = session.createQuery(query.toString());
				hQuery.setParameters(params.toArray(), tipos.toArray(tipos.toArray(new Type[] {})));
				List<BigInteger> resultados = hQuery.list();
				List<Long> idsCuentasPosicionNombrada = DTOAssembler.transformarListaBigIntegerEnLong(resultados);

				//Se colocan los objetos a null
				query = null;
				params = null;
				tipos = null;
				resultados = null;
				return idsCuentasPosicionNombrada;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.persistence.dao.estadocuenta.EstadoCuentaPosicionDAOV2#buscarCuentasDePosicionesNombradasHistoricas(com.indeval.portaldali.middleware.dto.PosicionDTO)
	 */
	@SuppressWarnings("unchecked")
	public List<Long> buscarCuentasDePosicionesNombradasHistoricas(final PosicionDTO criterio, final Date fecha) {
		return (List<Long>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuffer query = new StringBuffer();
				List<Object> params = new ArrayList<Object>();
				List<Type> tipos = new ArrayList<Type>();
				query.append(" SELECT DISTINCT pnh.idCuentaNombrada FROM " + PosicionNombradaHistorico.class.getName() + " pnh ");
				query.append(construirCriteriosPosicionNombrada(criterio, null, "pnh", params, tipos));
				query.append(" AND pnh.fecha = TRUNC(?) ");
				params.add(fecha);
				tipos.add(new DateType());
				Query hQuery = session.createQuery(query.toString());
				hQuery.setParameters(params.toArray(), tipos.toArray(tipos.toArray(new Type[] {})));
				List<BigInteger> resultados = hQuery.list();
				List<Long> idsCuentasPosicionNombradaHistorica = DTOAssembler.transformarListaBigIntegerEnLong(resultados);

				//Se colocan los objetos a null
				query = null;
				params = null;
				tipos = null;
				resultados = null;
				return idsCuentasPosicionNombradaHistorica;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.persistence.dao.estadocuenta.EstadoCuentaPosicionDAOV2#buscarEmisionesDePosicionesControladas(com.indeval.portaldali.middleware.dto.PosicionDTO)
	 */
	@SuppressWarnings("unchecked")
	public List<Long> buscarEmisionesDePosicionesControladas(final PosicionDTO criterio) {
		return (List<Long>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuffer query = new StringBuffer();
				List<Object> params = new ArrayList<Object>();
				List<Type> tipos = new ArrayList<Type>();
				query.append(" SELECT DISTINCT pc.cupon.idEmision FROM " + PosicionControlada.class.getName() + " pc ");
				query.append(construirCriteriosPosicionControlada(criterio, null, "pc", params, tipos));
				Query hQuery = session.createQuery(query.toString());
				hQuery.setParameters(params.toArray(), tipos.toArray(tipos.toArray(new Type[] {})));
				List<BigInteger> resultados = hQuery.list();
				List<Long> idsEmisionesPosicionControlada = DTOAssembler.transformarListaBigIntegerEnLong(resultados);

				//Se colocan los objetos a null
				query = null;
				params = null;
				tipos = null;
				resultados = null;
				return idsEmisionesPosicionControlada;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.persistence.dao.estadocuenta.EstadoCuentaPosicionDAOV2#buscarEmisionesDePosicionesControladasHistoricas(com.indeval.portaldali.middleware.dto.PosicionDTO)
	 */
	@SuppressWarnings("unchecked")
	public List<Long> buscarEmisionesDePosicionesControladasHistoricas(final PosicionDTO criterio, final Date fecha) {
		return (List<Long>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuffer query = new StringBuffer();
				List<Object> params = new ArrayList<Object>();
				List<Type> tipos = new ArrayList<Type>();
				query.append(" SELECT DISTINCT pch.cupon.idEmision FROM " + PosicionControladaHistorico.class.getName() + " pch ");
				query.append(construirCriteriosPosicionControlada(criterio, null, "pch", params, tipos));
				query.append(" AND pch.fecha = TRUNC(?) ");
				params.add(fecha);
				tipos.add(new DateType());
				Query hQuery = session.createQuery(query.toString());
				hQuery.setParameters(params.toArray(), tipos.toArray(tipos.toArray(new Type[] {})));
				List<BigInteger> resultados = hQuery.list();
				List<Long> idsEmisionesPosicionControladaHistoricas = DTOAssembler.transformarListaBigIntegerEnLong(resultados);

				//Se colocan los objetos a null
				query = null;
				params = null;
				tipos = null;
				resultados = null;
				return idsEmisionesPosicionControladaHistoricas;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.persistence.dao.estadocuenta.EstadoCuentaPosicionDAOV2#buscarEmisionesDePosicionesNombradas(com.indeval.portaldali.middleware.dto.PosicionDTO)
	 */
	@SuppressWarnings("unchecked")
	public List<Long> buscarEmisionesDePosicionesNombradas(final PosicionDTO criterio) {
		return (List<Long>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuffer query = new StringBuffer();
				List<Object> params = new ArrayList<Object>();
				List<Type> tipos = new ArrayList<Type>();
				query.append(" SELECT DISTINCT pon.cupon.idEmision FROM " + PosicionNombrada.class.getName() + " pon ");
				query.append(construirCriteriosPosicionNombrada(criterio, null, "pon", params, tipos));
				Query hQuery = session.createQuery(query.toString());
				hQuery.setParameters(params.toArray(), tipos.toArray(tipos.toArray(new Type[] {})));
				List<BigInteger> resultados = hQuery.list();
				List<Long> idsEmisionesPosicionNombrada = DTOAssembler.transformarListaBigIntegerEnLong(resultados);

				//Se colocan los objetos a null
				query = null;
				params = null;
				tipos = null;
				resultados = null;
				return idsEmisionesPosicionNombrada;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.persistence.dao.estadocuenta.EstadoCuentaPosicionDAOV2#buscarEmisionesDePosicionesNombradasHistoricas(com.indeval.portaldali.middleware.dto.PosicionDTO)
	 */
	@SuppressWarnings("unchecked")
	public List<Long> buscarEmisionesDePosicionesNombradasHistoricas(final PosicionDTO criterio, final Date fecha) {
		return (List<Long>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuffer query = new StringBuffer();
				List<Object> params = new ArrayList<Object>();
				List<Type> tipos = new ArrayList<Type>();
				query.append(" SELECT DISTINCT pnh.cupon.idEmision FROM " + PosicionNombradaHistorico.class.getName() + " pnh ");
				query.append(construirCriteriosPosicionNombrada(criterio, null, "pnh", params, tipos));
				query.append(" AND pnh.fecha = TRUNC(?) ");
				params.add(fecha);
				tipos.add(new DateType());
				Query hQuery = session.createQuery(query.toString());
				hQuery.setParameters(params.toArray(), tipos.toArray(tipos.toArray(new Type[] {})));
				List<BigInteger> resultados = hQuery.list();
				List<Long> idsEmisionesPosicionNombradaHistoricas = DTOAssembler.transformarListaBigIntegerEnLong(resultados);

				//Se colocan los objetos a null
				query = null;
				params = null;
				tipos = null;
				resultados = null;
				return idsEmisionesPosicionNombradaHistoricas;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.persistence.dao.estadocuenta.EstadoCuentaPosicionDAOV2#buscarPosicionesControladas(com.indeval.portaldali.middleware.dto.PosicionDTO,
	 *      java.util.List)
	 */
	@SuppressWarnings("unchecked")
	public List<PosicionDTO> buscarPosicionesControladas(final PosicionDTO criterio, final List<Long> idsEmisiones) {
		return (List<PosicionDTO>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuffer query = new StringBuffer();
				List<Object> params = new ArrayList<Object>();
				List<Type> tipos = new ArrayList<Type>();
				query.append("FROM " + PosicionControlada.class.getName() + " pc ");
				query.append(" join fetch pc.boveda ");
				query.append(" join fetch pc.cupon.emision ");
				query.append(" join fetch pc.cupon c ");
				query.append(" join fetch pc.cupon.emision.emisora ");
				query.append(" join fetch pc.cupon.emision.instrumento ");
				query.append(" join fetch pc.cupon.emision.instrumento.mercado ");
				query.append(" join fetch pc.cupon.emision.divisa ");
				query.append(" join fetch pc.cuentaControlada ");
				query.append(" join fetch pc.cuentaControlada.institucion ");
				query.append(" join fetch pc.cuentaControlada.institucion.tipoInstitucion ");
				query.append(" join fetch pc.cuentaControlada.tipoCuenta ");
				query.append(construirCriteriosPosicionControlada(criterio, idsEmisiones, "pc", params, tipos));
				query.append(" ORDER BY pc.cupon.idEmision, pc.idBoveda ");
				Query hQuery = session.createQuery(query.toString()).setParameters(params.toArray(), tipos.toArray(new Type[] {}));
				List<PosicionControlada> posiciones = hQuery.list();
				List<PosicionDTO> resultado = new ArrayList<PosicionDTO>();
				for (PosicionControlada posicion : posiciones) {
					resultado.add(EnsambladorDTOConsultasValores.crearPosicionControladaDTO(posicion));
				}

				//Se colocan los objetos a null
				query = null;
				params = null;
				tipos = null;
				posiciones = null;
				return resultado;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.persistence.dao.estadocuenta.EstadoCuentaPosicionDAOV2#buscarPosicionesControladasHistoricas(com.indeval.portaldali.middleware.dto.PosicionDTO,
	 *      java.util.List)
	 */
	@SuppressWarnings("unchecked")
	public List<PosicionDTO> buscarPosicionesControladasHistoricas(final PosicionDTO criterio, final Date fecha, final List<Long> idsEmisiones) {
		return (List<PosicionDTO>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuffer query = new StringBuffer();
				List<Object> params = new ArrayList<Object>();
				List<Type> tipos = new ArrayList<Type>();
				query.append("FROM " + PosicionControladaHistorico.class.getName() + " pch ");
				query.append(" join fetch pch.boveda ");
				query.append(" join fetch pch.cupon ");
				query.append(" join fetch pch.cupon.emision ");
				query.append(" join fetch pch.cupon.emision.emisora ");
				query.append(" join fetch pch.cupon.emision.instrumento ");
				query.append(" join fetch pch.cupon.emision.instrumento.mercado ");
				query.append(" join fetch pch.cupon.emision.divisa ");
				query.append(" join fetch pch.cuentaControlada ");
				query.append(" join fetch pch.cuentaControlada.institucion ");
				query.append(" join fetch pch.cuentaControlada.institucion.tipoInstitucion ");
				query.append(" join fetch pch.cuentaControlada.tipoCuenta ");
				query.append(construirCriteriosPosicionControlada(criterio, idsEmisiones, "pch", params, tipos));
				query.append(" AND pch.fecha = TRUNC(?) ");
				params.add(fecha);
				tipos.add(new DateType());
				query.append(" ORDER BY pch.cupon.idEmision, pch.idBoveda ");
				Query hQuery = session.createQuery(query.toString()).setParameters(params.toArray(), tipos.toArray(new Type[] {}));
				List<PosicionControladaHistorico> posiciones = hQuery.list();
				List<PosicionDTO> resultado = new ArrayList<PosicionDTO>();
				for (PosicionControladaHistorico posicion : posiciones) {
					resultado.add(EnsambladorDTOConsultasValores.crearPosicionControladaHistoricaDTO(posicion));
				}

				//Se colocan los objetos a null
				query = null;
				params = null;
				tipos = null;
				posiciones = null;
				return resultado;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.persistence.dao.estadocuenta.EstadoCuentaPosicionDAOV2#buscarPosicionesNombradas(com.indeval.portaldali.middleware.dto.PosicionDTO,
	 *      java.util.List)
	 */
	@SuppressWarnings("unchecked")
	public List<PosicionDTO> buscarPosicionesNombradas(final PosicionDTO criterio, final List<Long> idsEmisiones) {
		return (List<PosicionDTO>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuffer query = new StringBuffer();
				List<Object> params = new ArrayList<Object>();
				List<Type> tipos = new ArrayList<Type>();
				query.append(" FROM " + PosicionNombrada.class.getName() + " pon ");
				query.append("	join fetch pon.boveda ");
				query.append("	join fetch pon.cupon ");
				query.append("	join fetch pon.cupon.emision ");
				query.append("	join fetch pon.cupon.emision.emisora ");
				query.append("	join fetch pon.cupon.emision.instrumento ");
				query.append("	join fetch pon.cupon.emision.instrumento.mercado ");
				query.append("	join fetch pon.cupon.emision.divisa ");
				query.append("	join fetch pon.cuentaNombrada ");
				query.append("	join fetch pon.cuentaNombrada.institucion ");
				query.append("	join fetch pon.cuentaNombrada.institucion.tipoInstitucion ");
				query.append("	join fetch pon.cuentaNombrada.tipoCuenta ");
				query.append(construirCriteriosPosicionNombrada(criterio, idsEmisiones, "pon", params, tipos));
				query.append(" ORDER BY pon.cupon.idEmision asc, pon.idBoveda asc, (pon.posicionDisponible + pon.posicionNoDisponible) desc ");
				Query hQuery = session.createQuery(query.toString()).setParameters(params.toArray(), tipos.toArray(new Type[] {}));
				List<PosicionNombrada> posiciones = hQuery.list();
				List<PosicionDTO> resultado = new ArrayList<PosicionDTO>();
				for (PosicionNombrada posicion : posiciones) {
					resultado.add(EnsambladorDTOConsultasValores.crearPosicionNombradaDTO(posicion));
				}

				//Se colocan los objetos a null
				query = null;
				params = null;
				tipos = null;
				posiciones = null;
				return resultado;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.persistence.dao.estadocuenta.EstadoCuentaPosicionDAOV2#buscarPosicionesNombradasHistoricas(com.indeval.portaldali.middleware.dto.PosicionDTO,
	 *      java.util.List)
	 */
	@SuppressWarnings("unchecked")
	public List<PosicionDTO> buscarPosicionesNombradasHistoricas(final PosicionDTO criterio, final Date fecha, final List<Long> idsEmisiones) {
		return (List<PosicionDTO>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuffer query = new StringBuffer();
				List<Object> params = new ArrayList<Object>();
				List<Type> tipos = new ArrayList<Type>();
				query.append(" FROM " + PosicionNombradaHistorico.class.getName() + " pnh ");
				query.append("	join fetch pnh.boveda ");
				query.append("	join fetch pnh.cupon ");
				query.append("	join fetch pnh.cupon.emision ");
				query.append("	join fetch pnh.cupon.emision.emisora ");
				query.append("	join fetch pnh.cupon.emision.instrumento ");
				query.append("	join fetch pnh.cupon.emision.instrumento.mercado ");
				query.append("	join fetch pnh.cupon.emision.divisa ");
				query.append("	join fetch pnh.cuentaNombrada ");
				query.append("	join fetch pnh.cuentaNombrada.institucion ");
				query.append("	join fetch pnh.cuentaNombrada.institucion.tipoInstitucion ");
				query.append("	join fetch pnh.cuentaNombrada.tipoCuenta ");
				query.append(construirCriteriosPosicionNombrada(criterio, idsEmisiones, "pnh", params, tipos));
				query.append(" AND pnh.fecha = TRUNC(?) ");
				params.add(fecha);
				tipos.add(new DateType());
				query.append(" ORDER BY pnh.cupon.idEmision asc, pnh.idBoveda asc, (pnh.posicionDisponible + pnh.posicionNoDisponible) desc ");
				Query hQuery = session.createQuery(query.toString()).setParameters(params.toArray(), tipos.toArray(new Type[] {}));
				List<PosicionNombradaHistorico> posiciones = hQuery.list();
				List<PosicionDTO> resultado = new ArrayList<PosicionDTO>();
				for (PosicionNombradaHistorico posicion : posiciones) {
					resultado.add(EnsambladorDTOConsultasValores.crearPosicionNombradaHistoricaDTO(posicion));
				}

				//Se colocan los objetos a null
				query = null;
				params = null;
				tipos = null;
				posiciones = null;
				return resultado;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.persistence.dao.estadocuenta.EstadoCuentaPosicionDAOV2#buscarRegistrosContablesControladas(com.indeval.portaldali.middleware.dto.PosicionDTO,
	 *      java.util.List)
	 */
	@SuppressWarnings("unchecked")
	public List<RegistroContablePosicionControladaDTO> buscarRegistrosContablesControladas(final PosicionDTO criterio, final List<Long> idsEmisiones) {
		return (List<RegistroContablePosicionControladaDTO>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuffer query = new StringBuffer();
				List<Object> params = new ArrayList<Object>();
				List<Type> tipos = new ArrayList<Type>();
				query.append("FROM " + RegContValControlada.class.getName() + " reg ");
				query.append("	join fetch reg.posicionControlada ");
				query.append("	join fetch reg.posicionControlada.boveda ");
				query.append("	join fetch reg.posicionControlada.cupon c ");
				query.append("	join fetch reg.posicionControlada.cupon.emision ");
				query.append("	join fetch reg.posicionControlada.cupon.emision.emisora ");
				query.append("	join fetch reg.posicionControlada.cupon.emision.instrumento ");
				query.append("	join fetch reg.posicionControlada.cupon.emision.instrumento.mercado ");
				query.append("	join fetch reg.posicionControlada.cupon.emision.divisa ");
				query.append("	join fetch reg.posicionControlada.cuentaControlada ");
				query.append("	join fetch reg.posicionControlada.cuentaControlada.institucion ");
				query.append("	join fetch reg.posicionControlada.cuentaControlada.institucion.tipoInstitucion ");
				query.append("	join fetch reg.posicionControlada.cuentaControlada.tipoCuenta ");
				query.append(construirCriteriosPosicionControlada(criterio, idsEmisiones, "reg.posicionControlada", params, tipos));
				query.append("	AND trunc(reg.fecha) BETWEEN ? AND ? ");
				params.add(new Timestamp(criterio.getFechaInicio().getTime()));
				tipos.add(new DateType());
				params.add(new Timestamp(criterio.getFechaFinal().getTime()));
				tipos.add(new DateType());
				query.append(" ORDER BY reg.posicionControlada.cupon.emision.idEmision, reg.posicionControlada.boveda.idBoveda, reg.fecha ");
				Query hQuery = session.createQuery(query.toString()).setParameters(params.toArray(), tipos.toArray(new Type[] {}));
				List<RegContValControlada> posiciones = hQuery.list();
				List<RegistroContablePosicionControladaDTO> resultado = new ArrayList<RegistroContablePosicionControladaDTO>();
				for (RegContValControlada registro : posiciones) {
					resultado.add(EnsambladorDTOConsultasValores.crearRegContablePosicionControlada(registro));
				}

				//Se colocan los objetos a null
				query = null;
				params = null;
				tipos = null;
				posiciones = null;
				return resultado;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.persistence.dao.estadocuenta.EstadoCuentaPosicionDAOV2#buscarRegistrosContablesControladasHistoricos(com.indeval.portaldali.middleware.dto.PosicionDTO,
	 *      java.util.List)
	 */
	@SuppressWarnings("unchecked")
	public List<RegistroContablePosicionControladaDTO> buscarRegistrosContablesControladasHistoricos(final PosicionDTO criterio, final List<Long> idsEmisiones) {
		return (List<RegistroContablePosicionControladaDTO>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuffer query = new StringBuffer();
				List<Object> params = new ArrayList<Object>();
				List<Type> tipos = new ArrayList<Type>();
				query.append("FROM " + RegContValControladaHistorico.class.getName() + " reg ");
				query.append("	join fetch reg.posicionControlada ");
				query.append("	join fetch reg.posicionControlada.boveda ");
				query.append("	join fetch reg.posicionControlada.cupon c ");
				query.append("	join fetch reg.posicionControlada.cupon.emision ");		
				query.append("	join fetch reg.posicionControlada.cupon.emision.emisora ");
				query.append("	join fetch reg.posicionControlada.cupon.emision.instrumento ");
				query.append("	join fetch reg.posicionControlada.cupon.emision.instrumento.mercado ");
				query.append("	join fetch reg.posicionControlada.cupon.emision.divisa ");
				query.append("	join fetch reg.posicionControlada.cuentaControlada ");
				query.append("	join fetch reg.posicionControlada.cuentaControlada.institucion ");
				query.append("	join fetch reg.posicionControlada.cuentaControlada.institucion.tipoInstitucion ");
				query.append("	join fetch reg.posicionControlada.cuentaControlada.tipoCuenta ");
				query.append(construirCriteriosPosicionControlada(criterio, idsEmisiones, "reg.posicionControlada", params, tipos));
				query.append("	AND trunc(reg.fecha) BETWEEN ? AND ? ");
				params.add(new Timestamp(criterio.getFechaInicio().getTime()));
				tipos.add(new DateType());
				params.add(new Timestamp(criterio.getFechaFinal().getTime()));
				tipos.add(new DateType());
				query.append(" ORDER BY reg.posicionControlada.cupon.emision.idEmision, reg.posicionControlada.boveda.idBoveda, reg.fecha ");
				Query hQuery = session.createQuery(query.toString()).setParameters(params.toArray(), tipos.toArray(new Type[] {}));
				List<RegContValControladaHistorico> registros = hQuery.list();
				List<RegistroContablePosicionControladaDTO> resultado = new ArrayList<RegistroContablePosicionControladaDTO>();
				for (RegContValControladaHistorico registro : registros) {
					resultado.add(EnsambladorDTOConsultasValores.crearRegContablePosicionControladaHistorica(registro));
				}

				//Se colocan los objetos a null
				query = null;
				params = null;
				tipos = null;
				registros = null;
				return resultado;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.persistence.dao.estadocuenta.EstadoCuentaPosicionDAOV2#buscarRegistrosContablesNombradas(com.indeval.portaldali.middleware.dto.PosicionDTO,
	 *      java.util.List)
	 */
	@SuppressWarnings("unchecked")
	public List<RegistroContablePosicionNombradaDTO> buscarRegistrosContablesNombradas(final PosicionDTO criterio, final List<Long> idsEmisiones) {
		return (List<RegistroContablePosicionNombradaDTO>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuffer query = new StringBuffer();
				List<Object> params = new ArrayList<Object>();
				List<Type> tipos = new ArrayList<Type>();
				query.append("FROM " + RegContValNombrada.class.getName() + " reg ");
				query.append("  left outer join fetch reg.operacion ");
				query.append("  left outer join fetch reg.operacion.tipoOperacion ");
				query.append("  left outer join fetch reg.operacion.instruccion ");
				query.append("  left outer join fetch reg.operacion.instruccion.tipoInstruccion ");
				query.append("  left outer join fetch reg.posicion ");
				query.append("	left outer join fetch reg.posicion.boveda ");
				query.append("	left outer join fetch reg.posicion.cupon ");
				query.append("	left outer join fetch reg.posicion.cupon.emision ");
				query.append("	left outer join fetch reg.posicion.cupon.emision.emisora ");
				query.append("	left outer join fetch reg.posicion.cupon.emision.instrumento ");
				query.append("	left outer join fetch reg.posicion.cupon.emision.instrumento.mercado ");
				query.append("	left outer join fetch reg.posicion.cupon.emision.divisa ");		
				query.append("	left outer join fetch reg.posicion.cuentaNombrada ");
				query.append("	left outer join fetch reg.posicion.cuentaNombrada.institucion ");
				query.append("	left outer join fetch reg.posicion.cuentaNombrada.institucion.tipoInstitucion ");
				query.append("	left outer join fetch reg.posicion.cuentaNombrada.tipoCuenta ");
				query.append("  left outer join fetch reg.operacion.institucionReceptor ");
				query.append("  left outer join fetch reg.operacion.institucionReceptor.tipoInstitucion ");
				query.append("  left outer join fetch reg.operacion.institucionTraspasante ");
				query.append("  left outer join fetch reg.operacion.institucionTraspasante.tipoInstitucion ");
				query.append(construirCriteriosPosicionNombrada(criterio, idsEmisiones, "reg.posicion", params, tipos));
				query.append(" AND trunc(reg.fecha) BETWEEN ? AND ? ");
				params.add(new Timestamp(criterio.getFechaInicio().getTime()));
				tipos.add(new DateType());
				params.add(new Timestamp(criterio.getFechaFinal().getTime()));
				tipos.add(new DateType());
				query.append(" ORDER BY reg.posicion.cupon.emision.idEmision, reg.posicion.boveda.idBoveda, reg.fecha ");
				Query hQuery = session.createQuery(query.toString()).setParameters(params.toArray(), tipos.toArray(new Type[] {}));
				List<RegContValNombrada> registros = hQuery.list();
				List<RegistroContablePosicionNombradaDTO> resultado = new ArrayList<RegistroContablePosicionNombradaDTO>();
				for (RegContValNombrada registro : registros) {
					resultado.add(EnsambladorDTOConsultasValores.crearRegContablePosicionNombradaConDatosOperacion(registro));
				}

				//Se colocan los objetos a null
				query = null;
				params = null;
				tipos = null;
				registros = null;
				return resultado;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.persistence.dao.estadocuenta.EstadoCuentaPosicionDAOV2#buscarRegistrosContablesNombradasHistoricos(com.indeval.portaldali.middleware.dto.PosicionDTO,
	 *      java.util.List)
	 */
	@SuppressWarnings("unchecked")
	public List<RegistroContablePosicionNombradaDTO> buscarRegistrosContablesNombradasHistoricos(final PosicionDTO criterio, final List<Long> idsEmisiones) {
		return (List<RegistroContablePosicionNombradaDTO>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuffer query = new StringBuffer();
				List<Object> params = new ArrayList<Object>();
				List<Type> tipos = new ArrayList<Type>();
				query.append("FROM " + RegContValNombradaHistorico.class.getName() + " regh ");
				query.append("  left outer join fetch regh.operacion ");
				query.append("  left outer join fetch regh.operacion.tipoOperacion ");
				query.append("  left outer join fetch regh.operacion.instruccion ");
				query.append("  left outer join fetch regh.operacion.instruccion.tipoInstruccion ");
				query.append("  left outer join fetch regh.operacion.institucionReceptor ");
				query.append("  left outer join fetch regh.operacion.institucionReceptor.tipoInstitucion ");
				query.append("  left outer join fetch regh.operacion.institucionTraspasante ");
				query.append("  left outer join fetch regh.operacion.institucionTraspasante.tipoInstitucion ");
				query.append("  left outer join fetch regh.posicion ");
				query.append("	left outer join fetch regh.posicion.boveda ");
				query.append("	left outer join fetch regh.posicion.cupon ");
				query.append("	left outer join fetch regh.posicion.cupon.emision ");
				query.append("	left outer join fetch regh.posicion.cupon.emision.emisora ");
				query.append("	left outer join fetch regh.posicion.cupon.emision.instrumento ");
				query.append("	left outer join fetch regh.posicion.cupon.emision.instrumento.mercado ");
				query.append("	left outer join fetch regh.posicion.cupon.emision.divisa ");		
				query.append("	left outer join fetch regh.posicion.cuentaNombrada ");
				query.append("	left outer join fetch regh.posicion.cuentaNombrada.institucion ");
				query.append("	left outer join fetch regh.posicion.cuentaNombrada.institucion.tipoInstitucion ");
				query.append("	left outer join fetch regh.posicion.cuentaNombrada.tipoCuenta ");
				query.append(construirCriteriosPosicionNombrada(criterio, idsEmisiones, "regh.posicion", params, tipos));
				query.append(" AND trunc(regh.fecha) BETWEEN ? AND ? ");
				params.add(new Timestamp(criterio.getFechaInicio().getTime()));
				tipos.add(new DateType());
				params.add(new Timestamp(criterio.getFechaFinal().getTime()));
				tipos.add(new DateType());
				query.append(" AND regh.fechaCreacion = regh.operacion.fechaCreacion ");
				query.append(" ORDER BY regh.posicion.cupon.emision.idEmision, regh.posicion.boveda.idBoveda, regh.fecha ");
				Query hQuery = session.createQuery(query.toString()).setParameters(params.toArray(), tipos.toArray(new Type[] {}));
				List<RegContValNombradaHistorico> registros = hQuery.list();
				List<RegistroContablePosicionNombradaDTO> resultado = new ArrayList<RegistroContablePosicionNombradaDTO>();
				for (RegContValNombradaHistorico registro : registros) {
					resultado.add(EnsambladorDTOConsultasValores.crearRegContablePosicionNombradaConDatosOperacion(registro));
				}

				//Se colocan los objetos a null
				query = null;
				params = null;
				tipos = null;
				registros = null;
				return resultado;
			}
		});
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.persistence.dao.estadocuenta.EstadoCuentaPosicionDAOV2#obtenerProyeccionRegistrosContablesControladas(com.indeval.portaldali.middleware.dto.PosicionDTO)
	 */
	public Long obtenerProyeccionRegistrosContablesControladas(final PosicionDTO criterio) {
		return (Long) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuffer query = new StringBuffer();
				List<Object> params = new ArrayList<Object>();
				List<Type> tipos = new ArrayList<Type>();
				query.append("SELECT COUNT(*) FROM " + RegContValControlada.class.getName() + " regc ");
				query.append(construirCriteriosPosicionControlada(criterio, null, "regc.posicionControlada", params, tipos));
				query.append("	AND trunc(regc.fecha) BETWEEN ? AND ? ");
				params.add(new Timestamp(criterio.getFechaInicio().getTime()));
				tipos.add(new DateType());
				params.add(new Timestamp(criterio.getFechaFinal().getTime()));
				tipos.add(new DateType());
				Number resultadoBD = 
					(Number) session.createQuery(query.toString()).setParameters(params.toArray(), tipos.toArray(new Type[] {})).uniqueResult();
				long resultado = 0;
				if (resultadoBD != null) {
					resultado = resultadoBD.longValue();
				}

				//Se colocan los objetos a null
				query = null;
				params = null;
				tipos = null;
				return resultado;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.persistence.dao.estadocuenta.EstadoCuentaPosicionDAOV2#obtenerProyeccionRegistrosContablesControladasHistorico(com.indeval.portaldali.middleware.dto.PosicionDTO)
	 */
	public Long obtenerProyeccionRegistrosContablesControladasHistorico(final PosicionDTO criterio) {
		return (Long) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuffer query = new StringBuffer();
				List<Object> params = new ArrayList<Object>();
				List<Type> tipos = new ArrayList<Type>();
				query.append("SELECT COUNT(*) FROM " + RegContValControladaHistorico.class.getName() + " regch ");
				query.append(construirCriteriosPosicionControlada(criterio, null, "regch.posicionControlada", params, tipos));
				query.append("	AND trunc(regch.fecha) BETWEEN ? AND ? ");
				params.add(new Timestamp(criterio.getFechaInicio().getTime()));
				tipos.add(new DateType());
				params.add(new Timestamp(criterio.getFechaFinal().getTime()));
				tipos.add(new DateType());
				Number resultadoBD = 
					(Number) session.createQuery(query.toString()).setParameters(params.toArray(), tipos.toArray(new Type[] {})).uniqueResult();
				long resultado = 0;
				if (resultadoBD != null) {
					resultado = resultadoBD.longValue();
				}

				//Se colocan los objetos a null
				query = null;
				params = null;
				tipos = null;
				return resultado;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.persistence.dao.estadocuenta.EstadoCuentaPosicionDAOV2#obtenerProyeccionRegistrosContablesNombradas(com.indeval.portaldali.middleware.dto.PosicionDTO)
	 */
	public Long obtenerProyeccionRegistrosContablesNombradas(final PosicionDTO criterio) {
		return (Long) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuffer query = new StringBuffer();
				List<Object> params = new ArrayList<Object>();
				List<Type> tipos = new ArrayList<Type>();
				query.append("SELECT COUNT(*) FROM " + RegContValNombrada.class.getName() + " regn ");
				query.append(construirCriteriosPosicionNombrada(criterio, null, "regn.posicion", params, tipos));
				query.append("	AND trunc(regn.fecha) BETWEEN ? AND ? ");
				params.add(new Timestamp(criterio.getFechaInicio().getTime()));
				tipos.add(new DateType());
				params.add(new Timestamp(criterio.getFechaFinal().getTime()));
				tipos.add(new DateType());
				Number resultadoBD = 
					(Number) session.createQuery(query.toString()).setParameters(params.toArray(), tipos.toArray(new Type[] {})).uniqueResult();
				long resultado = 0;
				if (resultadoBD != null) {
					resultado = resultadoBD.longValue();
				}

				//Se colocan los objetos a null
				query = null;
				params = null;
				tipos = null;
				return resultado;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.persistence.dao.estadocuenta.EstadoCuentaPosicionDAOV2#obtenerProyeccionRegistrosContablesNombradasHistorico(com.indeval.portaldali.middleware.dto.PosicionDTO)
	 */
	public Long obtenerProyeccionRegistrosContablesNombradasHistorico(final PosicionDTO criterio) {
		return (Long) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuffer query = new StringBuffer();
				List<Object> params = new ArrayList<Object>();
				List<Type> tipos = new ArrayList<Type>();
				query.append("SELECT COUNT(*) FROM " + RegContValNombradaHistorico.class.getName() + " regnh ");
				query.append(construirCriteriosPosicionNombrada(criterio, null, "regnh.posicion", params, tipos));
				query.append("	AND trunc(regnh.fecha) BETWEEN ? AND ? ");
				params.add(new Timestamp(criterio.getFechaInicio().getTime()));
				tipos.add(new DateType());
				params.add(new Timestamp(criterio.getFechaFinal().getTime()));
				tipos.add(new DateType());
				query.append(" AND regnh.fechaCreacion = regnh.operacion.fechaCreacion ");
				Number resultadoBD = (Number) session.createQuery(query.toString()).setParameters(params.toArray(), tipos.toArray(new Type[] {}))
						.uniqueResult();
				long resultado = 0;
				if (resultadoBD != null) {
					resultado = resultadoBD.longValue();
				}

				//Se colocan los objetos a null
				query = null;
				params = null;
				tipos = null;
				return resultado;
			}
		});
	}

	
	/**
	 * Construye la sección del query de consulta de posición nombrada referente
	 * a los criterios de emisión, bóveda, cuenta e institución.
	 * 
	 * @param criterio
	 *            Criterios a utilizar
	 * @param idsEmisiones
	 *            Los identificadores de las emisiones a consultar.
	 * @param alias
	 *            Alias asignado a la tabla de Estadod posición nombrada
	 * @param params
	 *            Se utiliza para colocar los parámetros utilizados
	 * @param tipos
	 *            Se utiliza para colocar los tipos de los parámetros utilizados
	 * @return Cadena correspondiente a la sección de criterios del query de
	 *         posición nombrada
	 */
	private String construirCriteriosPosicionNombrada(PosicionDTO criterio, List<Long> idsEmisiones, String alias, List<Object> params, List<Type> tipos) {
		StringBuffer query = new StringBuffer();

		// Cuenta
		query.append("WHERE " + alias + ".cuentaNombrada.tipoCuenta.naturalezaContable = ? ");
		params.add(criterio.getCuenta().getTipoTenencia().getTipoNaturaleza().getId());
		tipos.add(new StringType());

		query.append(" and " + alias + ".cuentaNombrada.tipoCuenta.naturalezaProcesoLiquidacion = ? ");
		params.add(criterio.getCuenta().getTipoTenencia().getTipoCuenta().getId());
		tipos.add(new StringType());

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

		// No Disponible
		if (criterio.isNoDisponible()) {
			query.append(" AND " + alias + ".posicionNoDisponible > 0 AND ");
		}

		// Boveda
		if (criterio.getBoveda() != null && criterio.getBoveda().getId() > 0) {
			query.append(" AND " + alias + ".boveda.idBoveda = ? ");
			params.add(new BigInteger(String.valueOf(criterio.getBoveda().getId())));
			tipos.add(new BigIntegerType());
		}

		// emision

		if (idsEmisiones != null && !idsEmisiones.isEmpty()) {

			query.append(" AND " + alias + ".cupon.emision.idEmision in ( ");
			for (Iterator<Long> it = idsEmisiones.iterator(); it.hasNext();) {
				Long idEmision = it.next();
				query.append("?");
				params.add(new BigInteger(String.valueOf(idEmision)));
				tipos.add(new BigIntegerType());
				if (it.hasNext()) {
					query.append(",");
				}
			}
			query.append(" ) ");
		} else {

			if (criterio.getEmision() != null && criterio.getEmision().getIsin() != null && criterio.getEmision().getIsin().length() > 0) {
				query.append(" and " + alias + ".cupon.emision.isin = ? ");
				params.add(criterio.getEmision().getIsin().toUpperCase());
				tipos.add(new StringType());
			}

			if (criterio.getEmision() != null && criterio.getEmision().getSerie() != null
					&& StringUtils.isNotBlank(criterio.getEmision().getSerie().getSerie()) && !criterio.getEmision().getSerie().getSerie().equals("-1")) {
				query.append(" and " + alias + ".cupon.emision.serie = ? ");
				params.add(criterio.getEmision().getSerie().getSerie());
				tipos.add(new StringType());
			}

			if (criterio.getEmision() != null && criterio.getEmision().getEmisora() != null &&
					(criterio.getEmision().getEmisora().getId() > 0  || criterio.getEmision().getEmisora().getId() == DaliConstants.VALOR_COMBO_NINGUNO)){
				query.append(" and " + alias + ".cupon.emision.idEmisora = ? ");
				params.add(new BigInteger(String.valueOf(criterio.getEmision().getEmisora().getId())));
				tipos.add(new BigIntegerType());
			}

			if (criterio.getEmision() != null && criterio.getEmision().getTipoValor() != null &&
					(criterio.getEmision().getTipoValor().getId() > 0 || criterio.getEmision().getTipoValor().getId() == DaliConstants.VALOR_COMBO_NINGUNO)) {
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
			params.add(criterio.getCuenta().getInstitucion().getClaveTipoInstitucion());
			tipos.add(new StringType());
		}

		return query.toString();
	}

	/**
	 * Construye la sección del query de consulta de posición controlada
	 * referente a los criterios de emisión, bóveda, cuenta e institución.
	 * 
	 * @param criterio
	 *            Criterios a utilizar
	 * @param idsEmisiones
	 *            los identificadores de las emisiones a consultar.
	 * @param alias
	 *            Alias asignado a la tabla de Estadod posición nombrada
	 * @param params
	 *            Se utiliza para colocar los parámetros utilizados
	 * @param tipos
	 *            Se utiliza para colocar los tipos de los parámetros utilizados
	 * @return Cadena correspondiente a la sección de criterios del query de
	 *         posición controlada
	 */
	private String construirCriteriosPosicionControlada(PosicionDTO criterio, List<Long> idsEmisiones, String alias, List<Object> params, List<Type> tipos) {

		StringBuffer query = new StringBuffer();

		// Cuenta
		query.append("WHERE " + alias + ".cuentaControlada.tipoCuenta.naturalezaContable = ? ");
		params.add(criterio.getCuenta().getTipoTenencia().getTipoNaturaleza().getId());
		tipos.add(new StringType());

		query.append(" and " + alias + ".cuentaControlada.tipoCuenta.naturalezaProcesoLiquidacion = ? ");
		params.add(criterio.getCuenta().getTipoTenencia().getTipoCuenta().getId());
		tipos.add(new StringType());

		if (criterio.getCuenta() != null && StringUtils.isNotBlank(criterio.getCuenta().getNumeroCuenta())
				&& !"-1".equals(criterio.getCuenta().getNumeroCuenta())) {
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

		// Boveda
		if (criterio.getBoveda().getId() > 0) {
			query.append(" and " + alias + ".boveda.idBoveda = ? ");
			params.add(new BigInteger(String.valueOf(criterio.getBoveda().getId())));
			tipos.add(new BigIntegerType());
		}

		// emision
		if (idsEmisiones != null && !idsEmisiones.isEmpty()) {

			query.append(" AND " + alias + ".cupon.emision.idEmision in ( ");
			for (Iterator<Long> it = idsEmisiones.iterator(); it.hasNext();) {
				Long idEmision = it.next();
				query.append("?");
				params.add(new BigInteger(String.valueOf(idEmision)));
				tipos.add(new BigIntegerType());
				if (it.hasNext()) {
					query.append(",");
				}
			}
			query.append(" ) ");
		} else {
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
		}
		// Institucion
		if (criterio.getCuenta().getInstitucion().getId() > 0) {
			query.append(" and " + alias + ".cuentaControlada.institucion.idInstitucion = ? ");
			params.add(new BigInteger(String.valueOf(criterio.getCuenta().getInstitucion().getId())));
			tipos.add(new BigIntegerType());
		}

		// Tipo de institucion
		if (criterio.getCuenta().getInstitucion().getClaveTipoInstitucion() != null) {
			query.append(" and " + alias + ".cuentaControlada.institucion.tipoInstitucion.claveTipoInstitucion = ? ");
			params.add(criterio.getCuenta().getInstitucion().getClaveTipoInstitucion());
			tipos.add(new StringType());
		}

		return query.toString();

	}
}
