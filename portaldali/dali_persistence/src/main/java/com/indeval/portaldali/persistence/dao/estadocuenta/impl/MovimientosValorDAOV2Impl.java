/**
 * Sistema Portal DALI
 * 
 * Bursatec - 2H Software SA de CV
 *
 * Creado: Nov 25, 2008
 */
package com.indeval.portaldali.persistence.dao.estadocuenta.impl;

import java.math.BigInteger;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.type.BigIntegerType;
import org.hibernate.type.DateType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.indeval.portaldali.middleware.dto.CriterioConsultaMovimientosValorDTO;
import com.indeval.portaldali.middleware.dto.RegistroContablePosicionControladaDTO;
import com.indeval.portaldali.middleware.dto.RegistroContablePosicionNombradaDTO;
import com.indeval.portaldali.middleware.dto.util.DTOAssembler;
import com.indeval.portaldali.middleware.dto.util.EnsambladorDTOConsultasValores;
import com.indeval.portaldali.middleware.services.common.constants.DaliConstants;
import com.indeval.portaldali.middleware.services.common.constants.RolCuentaConstants;
import com.indeval.portaldali.persistence.dao.estadocuenta.MovimientosValorDAOV2;
import com.indeval.portaldali.persistence.model.CuentaNombrada;
import com.indeval.portaldali.persistence.model.RegContValControlada;
import com.indeval.portaldali.persistence.model.RegContValControladaHistorico;
import com.indeval.portaldali.persistence.model.RegContValNombrada;
import com.indeval.portaldali.persistence.model.RegContValNombradaHistorico;
import com.indeval.portaldali.persistence.util.Constantes;

/**
 * Implementación del DAO para la consulta de movimientos del DALI.
 * 
 * @author José Antonio Huizar Moreno
 * @version 1.0
 * 
 */
public class MovimientosValorDAOV2Impl extends HibernateDaoSupport implements MovimientosValorDAOV2 {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.persistence.dao.estadocuenta.MovimientosValorDAOV2#buscarCuentasDePosicionesControladas(com.indeval.portaldali.middleware.dto.CriterioConsultaMovimientosValorDTO)
	 */
	@SuppressWarnings("unchecked")
	public List<Long> buscarCuentasDePosicionesControladas(final CriterioConsultaMovimientosValorDTO criterio) {
		return (List<Long>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuffer query = new StringBuffer();
				ArrayList<Object> params = new ArrayList<Object>();
				ArrayList<Object> tipos = new ArrayList<Object>();
				query.append("SELECT DISTINCT reg.posicionControlada.cuentaControlada.idCuentaControlada, ");
				query.append(" reg.posicionControlada.cuentaControlada.institucion.tipoInstitucion.claveTipoInstitucion || ");
				query.append(" reg.posicionControlada.cuentaControlada.institucion.folioInstitucion || ");
				query.append(" reg.posicionControlada.cuentaControlada.cuenta FROM ");
				query.append(RegContValControlada.class.getName() + " reg ");
				query.append(construirCriteriosMovimientosControlados("reg", criterio, null, params, tipos));
				query.append(" ORDER BY reg.posicionControlada.cuentaControlada.institucion.tipoInstitucion.claveTipoInstitucion || ");
				query.append(" reg.posicionControlada.cuentaControlada.institucion.folioInstitucion || ");
				query.append(" reg.posicionControlada.cuentaControlada.cuenta");
				Query hQuery = session.createQuery(query.toString()).setParameters(params.toArray(), tipos.toArray(new Type[] {}));
				List<Object[]> resultados = hQuery.list();
				List<BigInteger> ids = new ArrayList<BigInteger>();
				for (Object[] resultado : resultados) {
					ids.add((BigInteger) resultado[0]);
				}
				List<Long> idsCuentas = DTOAssembler.transformarListaBigIntegerEnLong(ids);

				//Coloca a null los objetos
				query = null;
				params = null;
				tipos = null;
				resultados = null;
				ids = null;
				return idsCuentas;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.persistence.dao.estadocuenta.MovimientosValorDAOV2#buscarCuentasDePosicionesControladasHistoricas(com.indeval.portaldali.middleware.dto.CriterioConsultaMovimientosValorDTO,
	 *      java.util.Date)
	 */
	@SuppressWarnings("unchecked")
	public List<Long> buscarCuentasDePosicionesControladasHistoricas(final CriterioConsultaMovimientosValorDTO criterio, final Date fecha) {
		return (List<Long>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuffer query = new StringBuffer();
				ArrayList<Object> params = new ArrayList<Object>();
				ArrayList<Object> tipos = new ArrayList<Object>();
				query.append("SELECT DISTINCT regh.posicionControlada.cuentaControlada.idCuentaControlada, ");
				query.append(" regh.posicionControlada.cuentaControlada.institucion.tipoInstitucion.claveTipoInstitucion || ");
				query.append(" regh.posicionControlada.cuentaControlada.institucion.folioInstitucion || ");
				query.append(" regh.posicionControlada.cuentaControlada.cuenta FROM ");
				query.append(RegContValControladaHistorico.class.getName() + " regh ");
				query.append(construirCriteriosMovimientosControlados("regh", criterio, null, params, tipos));
				query.append(" ORDER BY regh.posicionControlada.cuentaControlada.institucion.tipoInstitucion.claveTipoInstitucion || ");
				query.append(" regh.posicionControlada.cuentaControlada.institucion.folioInstitucion || ");
				query.append(" regh.posicionControlada.cuentaControlada.cuenta");
				Query hQuery = session.createQuery(query.toString()).setParameters(params.toArray(), tipos.toArray(new Type[] {}));
				List<Object[]> resultados = hQuery.list();
				List<BigInteger> ids = new ArrayList<BigInteger>();
				for (Object[] resultado : resultados) {
					ids.add((BigInteger) resultado[0]);
				}
				List<Long> idsCuentas = DTOAssembler.transformarListaBigIntegerEnLong(ids);

				//Coloca a null los objetos
				query = null;
				params = null;
				tipos = null;
				resultados = null;
				ids = null;
				return idsCuentas;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.persistence.dao.estadocuenta.MovimientosValorDAOV2#buscarCuentasDePosicionesNombradas(com.indeval.portaldali.middleware.dto.CriterioConsultaMovimientosValorDTO)
	 */
	@SuppressWarnings("unchecked")
	public List<Long> buscarCuentasDePosicionesNombradas(final CriterioConsultaMovimientosValorDTO criterio) {
		return (List<Long>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuffer query = new StringBuffer();
				ArrayList<Object> params = new ArrayList<Object>();
				ArrayList<Object> tipos = new ArrayList<Object>();
				query.append("SELECT DISTINCT reg.posicion.cuentaNombrada.idCuentaNombrada, ");
				query.append(" reg.posicion.cuentaNombrada.institucion.tipoInstitucion.claveTipoInstitucion || ");
				query.append(" reg.posicion.cuentaNombrada.institucion.folioInstitucion || ");
				query.append(" reg.posicion.cuentaNombrada.cuenta FROM ");
				query.append(RegContValNombrada.class.getName() + " reg ");
				query.append(construirCriteriosMovimientosNombrados("reg", criterio, null, params, tipos));
				query.append(" ORDER BY reg.posicion.cuentaNombrada.institucion.tipoInstitucion.claveTipoInstitucion || ");
				query.append(" reg.posicion.cuentaNombrada.institucion.folioInstitucion || ");
				query.append(" reg.posicion.cuentaNombrada.cuenta");
				Query hQuery = session.createQuery(query.toString()).setParameters(params.toArray(), tipos.toArray(new Type[] {}));
				List<Object[]> resultados = hQuery.list();
				List<BigInteger> ids = new ArrayList<BigInteger>();
				for (Object[] resultado : resultados) {
					ids.add((BigInteger) resultado[0]);
				}
				List<Long> idsCuentas = DTOAssembler.transformarListaBigIntegerEnLong(ids);

				//Coloca a null los objetos
				query = null;
				params = null;
				tipos = null;
				resultados = null;
				ids = null;
				return idsCuentas;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.persistence.dao.estadocuenta.MovimientosValorDAOV2#buscarCuentasDePosicionesNombradasHistoricas(com.indeval.portaldali.middleware.dto.CriterioConsultaMovimientosValorDTO,
	 *      java.util.Date)
	 */
	@SuppressWarnings("unchecked")
	public List<Long> buscarCuentasDePosicionesNombradasHistoricas(final CriterioConsultaMovimientosValorDTO criterio, final Date fecha) {
		return (List<Long>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuffer query = new StringBuffer();
				ArrayList<Object> params = new ArrayList<Object>();
				ArrayList<Object> tipos = new ArrayList<Object>();
				query.append("SELECT DISTINCT regh.posicion.cuentaNombrada.idCuentaNombrada, ");
				query.append(" regh.posicion.cuentaNombrada.institucion.tipoInstitucion.claveTipoInstitucion || ");
				query.append(" regh.posicion.cuentaNombrada.institucion.folioInstitucion || ");
				query.append(" regh.posicion.cuentaNombrada.cuenta FROM ");
				query.append(RegContValNombradaHistorico.class.getName() + " regh ");
				query.append(construirCriteriosMovimientosNombrados("regh", criterio, null, params, tipos));
				query.append(" AND regh.fechaCreacion = regh.operacion.fechaCreacion ");
				query.append(" ORDER BY regh.posicion.cuentaNombrada.institucion.tipoInstitucion.claveTipoInstitucion || ");
				query.append(" regh.posicion.cuentaNombrada.institucion.folioInstitucion || ");
				query.append(" regh.posicion.cuentaNombrada.cuenta");
				Query hQuery = session.createQuery(query.toString()).setParameters(params.toArray(), tipos.toArray(new Type[] {}));
				List<Object[]> resultados = hQuery.list();
				List<BigInteger> ids = new ArrayList<BigInteger>();
				for (Object[] resultado : resultados) {
					ids.add((BigInteger) resultado[0]);
				}
				List<Long> idsCuentas = DTOAssembler.transformarListaBigIntegerEnLong(ids);

				//Coloca a null los objetos
				query = null;
				params = null;
				tipos = null;
				resultados = null;
				ids = null;
				return idsCuentas;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.persistence.dao.estadocuenta.MovimientosValorDAOV2#buscarEmisionesDePosicionesControladas(com.indeval.portaldali.middleware.dto.CriterioConsultaMovimientosValorDTO)
	 */
	@SuppressWarnings("unchecked")
	public List<Long> buscarEmisionesDePosicionesControladas(final CriterioConsultaMovimientosValorDTO criterio) {
		return (List<Long>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuffer query = new StringBuffer();
				ArrayList<Object> params = new ArrayList<Object>();
				ArrayList<Object> tipos = new ArrayList<Object>();
				query.append("SELECT DISTINCT reg.posicionControlada.emision.idEmision FROM " + RegContValControlada.class.getName() + " reg ");
				query.append(construirCriteriosMovimientosControlados("reg", criterio, null, params, tipos));
				query.append(" ORDER BY reg.posicionControlada.emision.idEmision ");
				Query hQuery = session.createQuery(query.toString()).setParameters(params.toArray(), tipos.toArray(new Type[] {}));
				List<BigInteger> ids = hQuery.list();
				List<Long> idsEmisiones = DTOAssembler.transformarListaBigIntegerEnLong(ids);

				//Coloca a null los objetos
				query = null;
				params = null;
				tipos = null;
				ids = null;
				return idsEmisiones;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.persistence.dao.estadocuenta.MovimientosValorDAOV2#buscarEmisionesDePosicionesControladasHistoricas(com.indeval.portaldali.middleware.dto.CriterioConsultaMovimientosValorDTO,
	 *      java.util.Date)
	 */
	@SuppressWarnings("unchecked")
	public List<Long> buscarEmisionesDePosicionesControladasHistoricas(final CriterioConsultaMovimientosValorDTO criterio, final Date fecha) {
		return (List<Long>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuffer query = new StringBuffer();
				ArrayList<Object> params = new ArrayList<Object>();
				ArrayList<Object> tipos = new ArrayList<Object>();
				query.append("SELECT DISTINCT regh.posicionControlada.emision.idEmision FROM " + RegContValControladaHistorico.class.getName() + " regh ");
				query.append(construirCriteriosMovimientosControlados("regh", criterio, null, params, tipos));
				query.append(" ORDER BY regh.posicionControlada.emision.idEmision ");
				Query hQuery = session.createQuery(query.toString()).setParameters(params.toArray(), tipos.toArray(new Type[] {}));
				List<BigInteger> ids = hQuery.list();
				List<Long> idsEmisiones = DTOAssembler.transformarListaBigIntegerEnLong(ids);

				//Coloca a null los objetos
				query = null;
				params = null;
				tipos = null;
				ids = null;
				return idsEmisiones;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.persistence.dao.estadocuenta.MovimientosValorDAOV2#buscarEmisionesDePosicionesNombradas(com.indeval.portaldali.middleware.dto.CriterioConsultaMovimientosValorDTO)
	 */
	@SuppressWarnings("unchecked")
	public List<Long> buscarEmisionesDePosicionesNombradas(final CriterioConsultaMovimientosValorDTO criterio) {
		return (List<Long>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuffer query = new StringBuffer();
				ArrayList<Object> params = new ArrayList<Object>();
				ArrayList<Object> tipos = new ArrayList<Object>();
				query.append("SELECT DISTINCT reg.posicion.emision.idEmision  FROM " + RegContValNombrada.class.getName() + " reg ");
				query.append(construirCriteriosMovimientosNombrados("reg", criterio, null, params, tipos));
				query.append(" ORDER BY reg.posicion.emision.idEmision ");
				Query hQuery = session.createQuery(query.toString()).setParameters(params.toArray(), tipos.toArray(new Type[] {}));
				List<BigInteger> ids = hQuery.list();
				List<Long> idsEmisiones = DTOAssembler.transformarListaBigIntegerEnLong(ids);

				//Coloca a null los objetos
				query = null;
				params = null;
				tipos = null;
				ids = null;
				return idsEmisiones;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.persistence.dao.estadocuenta.MovimientosValorDAOV2#buscarEmisionesDePosicionesNombradasHistoricas(com.indeval.portaldali.middleware.dto.CriterioConsultaMovimientosValorDTO,
	 *      java.util.Date)
	 */
	@SuppressWarnings("unchecked")
	public List<Long> buscarEmisionesDePosicionesNombradasHistoricas(final CriterioConsultaMovimientosValorDTO criterio, final Date fecha) {
		return (List<Long>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuffer query = new StringBuffer();
				ArrayList<Object> params = new ArrayList<Object>();
				ArrayList<Object> tipos = new ArrayList<Object>();
				query.append("SELECT DISTINCT regh.posicion.emision.idEmision  FROM " + RegContValNombradaHistorico.class.getName() + " regh ");
				query.append(construirCriteriosMovimientosNombrados("regh", criterio, null, params, tipos));
				query.append(" AND regh.fechaCreacion = regh.operacion.fechaCreacion ");
				query.append(" ORDER BY regh.posicion.emision.idEmision ");
				Query hQuery = session.createQuery(query.toString()).setParameters(params.toArray(), tipos.toArray(new Type[] {}));
				List<BigInteger> ids = hQuery.list();
				List<Long> idsEmisiones = DTOAssembler.transformarListaBigIntegerEnLong(ids);

				//Coloca a null los objetos
				query = null;
				params = null;
				tipos = null;
				ids = null;
				return idsEmisiones;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.persistence.dao.estadocuenta.MovimientosValorDAOV2#buscarRegistrosContablesControladas(com.indeval.portaldali.middleware.dto.CriterioConsultaMovimientosValorDTO,
	 *      java.util.List)
	 */
	@SuppressWarnings("unchecked")
	public List<RegistroContablePosicionControladaDTO> buscarRegistrosContablesControladas(final CriterioConsultaMovimientosValorDTO criterio, final Set<Long> idsEmisiones) {
		return (List<RegistroContablePosicionControladaDTO>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuffer query = new StringBuffer();
				ArrayList<Object> params = new ArrayList<Object>();
				ArrayList<Object> tipos = new ArrayList<Object>();
				query.append("FROM " + RegContValControlada.class.getName() + " reg ");
				query.append(" join fetch reg.posicionControlada ");
				query.append(" join fetch reg.posicionControlada.boveda ");
				query.append(" join fetch reg.posicionControlada.emision ");
				query.append(" join fetch reg.posicionControlada.emision.emisora ");
				query.append(" join fetch reg.posicionControlada.emision.instrumento ");
				query.append(" join fetch reg.posicionControlada.emision.instrumento.mercado ");
				query.append(" join fetch reg.posicionControlada.emision.divisa ");
				query.append(" join fetch reg.posicionControlada.cuentaControlada ");
				query.append(" join fetch reg.posicionControlada.cuentaControlada.institucion ");
				query.append(" join fetch reg.posicionControlada.cuentaControlada.institucion.tipoInstitucion ");
				query.append(" join fetch reg.posicionControlada.cuentaControlada.tipoCuenta ");
				query.append(construirCriteriosMovimientosControlados("reg", criterio, idsEmisiones, params, tipos));
				query.append("ORDER BY reg.posicionControlada.cuentaControlada.institucion.tipoInstitucion.claveTipoInstitucion || "
								+ "reg.posicionControlada.cuentaControlada.institucion.folioInstitucion || "
								+ "reg.posicionControlada.cuentaControlada.cuenta, reg.posicionControlada.emision.instrumento.claveTipoValor, reg.posicionControlada.emision.idEmision, reg.posicionControlada.boveda.idBoveda, reg.fecha");
				Query hQuery = session.createQuery(query.toString()).setParameters(params.toArray(), tipos.toArray(new Type[] {}));
				List<RegistroContablePosicionControladaDTO> resultados = new ArrayList<RegistroContablePosicionControladaDTO>();
				List<RegContValControlada> registros = hQuery.list();
				for (RegContValControlada registro : registros) {
					resultados.add(EnsambladorDTOConsultasValores.crearRegContablePosicionControlada(registro));
				}

				//Coloca a null los objetos
				query = null;
				params = null;
				tipos = null;
				registros = null;
				return resultados;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.persistence.dao.estadocuenta.MovimientosValorDAOV2#buscarRegistrosContablesControladasHistoricos(com.indeval.portaldali.middleware.dto.CriterioConsultaMovimientosValorDTO,
	 *      java.util.List)
	 */
	@SuppressWarnings("unchecked")
	public List<RegistroContablePosicionControladaDTO> buscarRegistrosContablesControladasHistoricos(final CriterioConsultaMovimientosValorDTO criterio,
			final Set<Long> idsEmisiones) {
		return (List<RegistroContablePosicionControladaDTO>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuffer query = new StringBuffer();
				ArrayList<Object> params = new ArrayList<Object>();
				ArrayList<Object> tipos = new ArrayList<Object>();
				query.append("FROM " + RegContValControladaHistorico.class.getName() + " reg ");
				query.append(" join fetch reg.posicionControlada ");
				query.append(" join fetch reg.posicionControlada.boveda ");
				query.append(" join fetch reg.posicionControlada.emision ");
				query.append(" join fetch reg.posicionControlada.emision.emisora ");
				query.append(" join fetch reg.posicionControlada.emision.instrumento ");
				query.append(" join fetch reg.posicionControlada.emision.instrumento.mercado ");
				query.append(" join fetch reg.posicionControlada.emision.divisa ");
				query.append(" join fetch reg.posicionControlada.cuentaControlada ");
				query.append(" join fetch reg.posicionControlada.cuentaControlada.institucion ");
				query.append(" join fetch reg.posicionControlada.cuentaControlada.institucion.tipoInstitucion ");
				query.append(" join fetch reg.posicionControlada.cuentaControlada.tipoCuenta ");
				query.append(construirCriteriosMovimientosControlados("reg", criterio, idsEmisiones, params, tipos));
				query.append(" ORDER BY reg.posicionControlada.cuentaControlada.institucion.tipoInstitucion.claveTipoInstitucion || ");
				query.append(" reg.posicionControlada.cuentaControlada.institucion.folioInstitucion || ");
				query.append(" reg.posicionControlada.cuentaControlada.cuenta, reg.posicionControlada.emision.instrumento.claveTipoValor, ");
				query.append(" reg.posicionControlada.emision.idEmision, reg.posicionControlada.boveda.idBoveda, reg.fecha");
				Query hQuery = session.createQuery(query.toString()).setParameters(params.toArray(), tipos.toArray(new Type[] {}));
				List<RegistroContablePosicionControladaDTO> resultados = new ArrayList<RegistroContablePosicionControladaDTO>();
				List<RegContValControladaHistorico> registros = hQuery.list();
				for (RegContValControladaHistorico registro : registros) {
					resultados.add(EnsambladorDTOConsultasValores.crearRegContablePosicionControladaHistorica(registro));
				}

				//Coloca a null los objetos
				query = null;
				params = null;
				tipos = null;
				registros = null;
				return resultados;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.persistence.dao.estadocuenta.MovimientosValorDAOV2#buscarRegistrosContablesNombradas(com.indeval.portaldali.middleware.dto.CriterioConsultaMovimientosValorDTO,
	 *      java.util.List)
	 */
	@SuppressWarnings("unchecked")
	public List<RegistroContablePosicionNombradaDTO> buscarRegistrosContablesNombradas(final CriterioConsultaMovimientosValorDTO criterio, final Set<Long> idsEmisiones) {
		return (List<RegistroContablePosicionNombradaDTO>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuffer query = new StringBuffer();
				ArrayList<Object> params = new ArrayList<Object>();
				ArrayList<Object> tipos = new ArrayList<Object>();
				query.append("FROM " + RegContValNombrada.class.getName() + " reg ");
				query.append("  join fetch reg.operacion ");
				query.append("  join fetch reg.operacion.tipoOperacion ");
				query.append("  join fetch reg.operacion.instruccion ");
				query.append("  join fetch reg.operacion.instruccion.tipoInstruccion ");
				query.append("  left outer join fetch reg.operacion.institucionReceptor ");
				query.append("  left outer join fetch reg.operacion.institucionReceptor.tipoInstitucion ");
				query.append("  left outer join fetch reg.operacion.institucionTraspasante ");
				query.append("  left outer join fetch reg.operacion.institucionTraspasante.tipoInstitucion ");
				query.append("  left outer join fetch reg.operacion.cuentaNombradaTraspasante");
				query.append("  left outer join fetch reg.operacion.cuentaNombradaTraspasante.tipoCuenta ");
				query.append("  left outer join fetch reg.operacion.cuentaNombradaTraspasante.institucion ");
				query.append("  left outer join fetch reg.operacion.cuentaNombradaTraspasante.institucion.tipoInstitucion ");
				query.append("  left outer join fetch reg.operacion.cuentaNombradaReceptor ");
				query.append("  left outer join fetch reg.operacion.cuentaNombradaReceptor.tipoCuenta ");
				query.append("  left outer join fetch reg.operacion.cuentaNombradaReceptor.institucion ");
				query.append("  left outer join fetch reg.operacion.cuentaNombradaReceptor.institucion.tipoInstitucion ");
				query.append("  left outer join fetch reg.posicion ");
				query.append("  left outer join fetch reg.operacion.posicionNombrada ");
				query.append("	left outer join fetch reg.posicion.boveda ");
				query.append("	left outer join fetch reg.posicion.cupon.emision ");
				query.append("	left outer join fetch reg.posicion.cupon.emision.emisora ");
				query.append("	left outer join fetch reg.posicion.cupon.emision.instrumento ");
				query.append("	left outer join fetch reg.posicion.cupon.emision.instrumento.mercado ");
				query.append("	left outer join fetch reg.posicion.cupon.emision.divisa ");
				query.append("	left outer join fetch reg.posicion.cupon ");
				query.append("	left outer join fetch reg.posicion.cuentaNombrada ");
				query.append("	left outer join fetch reg.posicion.cuentaNombrada.institucion ");
				query.append("	left outer join fetch reg.posicion.cuentaNombrada.institucion.tipoInstitucion ");
				query.append("	left outer join fetch reg.posicion.cuentaNombrada.tipoCuenta ");
				query.append("	left outer join fetch reg.operacion.posicionNombrada.boveda ");
				query.append("	left outer join fetch reg.operacion.posicionNombrada.cupon.emision ");
				query.append("	left outer join fetch reg.operacion.posicionNombrada.cupon.emision.emisora ");
				query.append("	left outer join fetch reg.operacion.posicionNombrada.cupon.emision.instrumento ");
				query.append("	left outer join fetch reg.operacion.posicionNombrada.cupon.emision.instrumento.mercado ");
				query.append("	left outer join fetch reg.operacion.posicionNombrada.cupon.emision.divisa ");
				query.append("	left outer join fetch reg.operacion.posicionNombrada.cupon ");
				query.append("	left outer join fetch reg.operacion.posicionNombrada.cuentaNombrada ");
				query.append("	left outer join fetch reg.operacion.posicionNombrada.cuentaNombrada.institucion ");
				query.append("	left outer join fetch reg.operacion.posicionNombrada.cuentaNombrada.institucion.tipoInstitucion ");
				query.append("	left outer join fetch reg.operacion.posicionNombrada.cuentaNombrada.tipoCuenta ");
				query.append(construirCriteriosMovimientosNombrados("reg", criterio, idsEmisiones, params, tipos));
				if (criterio.isOrdenarPorTipoDeInstruccion()) {
					query.append(" ORDER BY 	reg.posicion.cuentaNombrada.institucion.tipoInstitucion.claveTipoInstitucion || ");
					query.append(" reg.posicion.cuentaNombrada.institucion.folioInstitucion || ");
					query.append(" reg.posicion.cuentaNombrada.cuenta, reg.posicion.emision.instrumento.claveTipoValor, ");
					query.append(" reg.posicion.emision.idEmision, reg.posicion.boveda.idBoveda, reg.operacion.instruccion.tipoInstruccion.nombreCorto, reg.fecha");
				} else {
					query.append("ORDER BY reg.posicion.cuentaNombrada.institucion.tipoInstitucion.claveTipoInstitucion || ");
					query.append(" reg.posicion.cuentaNombrada.institucion.folioInstitucion || ");
					query.append(" reg.posicion.cuentaNombrada.cuenta, reg.posicion.emision.instrumento.claveTipoValor, ");
					query.append(" reg.posicion.emision.idEmision, reg.posicion.boveda.idBoveda, reg.fecha");
				}
				Query hQuery = session.createQuery(query.toString()).setParameters(params.toArray(), tipos.toArray(new Type[] {}));
				List<RegistroContablePosicionNombradaDTO> resultados = new ArrayList<RegistroContablePosicionNombradaDTO>();
				List<RegContValNombrada> registros = hQuery.list();

				for (RegContValNombrada registro : registros) {
					resultados.add(EnsambladorDTOConsultasValores.crearRegContablePosicionNombradaConDatosOperacion(registro));
				}

				//Coloca a null los objetos
				query = null;
				params = null;
				tipos = null;
				registros = null;
				return resultados;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.persistence.dao.estadocuenta.MovimientosValorDAOV2#buscarRegistrosContablesNombradasHistoricos(com.indeval.portaldali.middleware.dto.CriterioConsultaMovimientosValorDTO,
	 *      java.util.List)
	 */
	@SuppressWarnings("unchecked")
	public List<RegistroContablePosicionNombradaDTO> buscarRegistrosContablesNombradasHistoricos(final CriterioConsultaMovimientosValorDTO criterio,
			final Set<Long> idsEmisiones) {
		return (List<RegistroContablePosicionNombradaDTO>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuffer query = new StringBuffer();
				ArrayList<Object> params = new ArrayList<Object>();
				ArrayList<Object> tipos = new ArrayList<Object>();
				query.append("FROM " + RegContValNombradaHistorico.class.getName() + " reg ");
				query.append("  join fetch reg.operacion ");
				query.append("  join fetch reg.operacion.tipoOperacion ");
				query.append("  join fetch reg.operacion.instruccion ");
				query.append("  join fetch reg.operacion.instruccion.tipoInstruccion ");
				query.append("  left outer join fetch reg.operacion.institucionReceptor ");
				query.append("  left outer join fetch reg.operacion.institucionReceptor.tipoInstitucion ");
				query.append("  left outer join fetch reg.operacion.institucionTraspasante ");
				query.append("  left outer join fetch reg.operacion.institucionTraspasante.tipoInstitucion ");
				query.append("  left outer join fetch reg.operacion.cuentaNombradaTraspasante");
				query.append("  left outer join fetch reg.operacion.cuentaNombradaTraspasante.tipoCuenta ");
				query.append("  left outer join fetch reg.operacion.cuentaNombradaTraspasante.institucion ");
				query.append("  left outer join fetch reg.operacion.cuentaNombradaTraspasante.institucion.tipoInstitucion ");
				query.append("  left outer join fetch reg.operacion.cuentaNombradaReceptor ");
				query.append("  left outer join fetch reg.operacion.cuentaNombradaReceptor.tipoCuenta ");
				query.append("  left outer join fetch reg.operacion.cuentaNombradaReceptor.institucion ");
				query.append("  left outer join fetch reg.operacion.cuentaNombradaReceptor.institucion.tipoInstitucion ");
				query.append("  left outer join fetch reg.posicion ");
				query.append("  left outer join fetch reg.operacion.posicionNombrada ");
				query.append("	left outer join fetch reg.posicion.boveda ");
				query.append("	left outer join fetch reg.posicion.cupon.emision ");
				query.append("	left outer join fetch reg.posicion.cupon.emision.emisora ");
				query.append("	left outer join fetch reg.posicion.cupon.emision.instrumento ");
				query.append("	left outer join fetch reg.posicion.cupon.emision.instrumento.mercado ");
				query.append("	left outer join fetch reg.posicion.cupon.emision.divisa ");
				query.append("	left outer join fetch reg.posicion.cupon ");
				query.append("	left outer join fetch reg.posicion.cuentaNombrada ");
				query.append("	left outer join fetch reg.posicion.cuentaNombrada.institucion ");
				query.append("	left outer join fetch reg.posicion.cuentaNombrada.institucion.tipoInstitucion ");
				query.append("	left outer join fetch reg.posicion.cuentaNombrada.tipoCuenta ");
				query.append("	left outer join fetch reg.operacion.posicionNombrada.boveda ");
				query.append("	left outer join fetch reg.operacion.posicionNombrada.cupon.emision ");
				query.append("	left outer join fetch reg.operacion.posicionNombrada.cupon.emision.emisora ");
				query.append("	left outer join fetch reg.operacion.posicionNombrada.cupon.emision.instrumento ");
				query.append("	left outer join fetch reg.operacion.posicionNombrada.cupon.emision.instrumento.mercado ");
				query.append("	left outer join fetch reg.operacion.posicionNombrada.cupon.emision.divisa ");
				query.append("	left outer join fetch reg.operacion.posicionNombrada.cupon ");
				query.append("	left outer join fetch reg.operacion.posicionNombrada.cuentaNombrada ");
				query.append("	left outer join fetch reg.operacion.posicionNombrada.cuentaNombrada.institucion ");
				query.append("	left outer join fetch reg.operacion.posicionNombrada.cuentaNombrada.institucion.tipoInstitucion ");
				query.append("	left outer join fetch reg.operacion.posicionNombrada.cuentaNombrada.tipoCuenta ");
				query.append(construirCriteriosMovimientosNombrados("reg", criterio, idsEmisiones, params, tipos));
				query.append(" AND reg.fechaCreacion = reg.operacion.fechaCreacion ");
				if (criterio.isOrdenarPorTipoDeInstruccion()) {
					query.append(" ORDER BY reg.posicion.cuentaNombrada.institucion.tipoInstitucion.claveTipoInstitucion || ");
					query.append(" reg.posicion.cuentaNombrada.institucion.folioInstitucion || ");
					query.append(" reg.posicion.cuentaNombrada.cuenta, reg.posicion.emision.instrumento.claveTipoValor, ");
					query.append(" reg.posicion.emision.idEmision, reg.posicion.boveda.idBoveda, reg.operacion.instruccion.tipoInstruccion.nombreCorto, reg.fecha");
				}
				else {
					query.append(" ORDER BY reg.posicion.cuentaNombrada.institucion.tipoInstitucion.claveTipoInstitucion || ");
					query.append(" reg.posicion.cuentaNombrada.institucion.folioInstitucion || ");
					query.append(" reg.posicion.cuentaNombrada.cuenta, reg.posicion.emision.instrumento.claveTipoValor, ");
					query.append(" reg.posicion.emision.idEmision, reg.posicion.boveda.idBoveda, reg.fecha");
				}
				Query hQuery = session.createQuery(query.toString()).setParameters(params.toArray(), tipos.toArray(new Type[] {}));
				List<RegistroContablePosicionNombradaDTO> resultados = new ArrayList<RegistroContablePosicionNombradaDTO>();
				List<RegContValNombradaHistorico> registros = hQuery.list();
				for (RegContValNombradaHistorico registro : registros) {
					resultados.add(EnsambladorDTOConsultasValores.crearRegContablePosicionNombradaConDatosOperacion(registro));
				}

				//Coloca a null los objetos
				query = null;
				params = null;
				tipos = null;
				registros = null;
				return resultados;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.persistence.dao.estadocuenta.MovimientosValorDAOV2#obtenerProyeccionRegistrosContablesControladas(com.indeval.portaldali.middleware.dto.CriterioConsultaMovimientosValorDTO)
	 */
	public Long obtenerProyeccionRegistrosContablesControladas(final CriterioConsultaMovimientosValorDTO criterio) {
		return (Long) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuffer query = new StringBuffer();
				ArrayList<Object> params = new ArrayList<Object>();
				ArrayList<Object> tipos = new ArrayList<Object>();
				query.append("SELECT COUNT(*) FROM " + RegContValControlada.class.getName() + " reg ");
				query.append(construirCriteriosMovimientosControlados("reg", criterio, null, params, tipos));
				Query hQuery = session.createQuery(query.toString()).setParameters(params.toArray(), tipos.toArray(new Type[] {}));
				Object res = hQuery.uniqueResult();
				long total = 0;
				if (res != null) {
					total = ((Number) hQuery.uniqueResult()).longValue();
				}

				//Coloca a null los objetos
				query = null;
				params = null;
				tipos = null;
				return total;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.persistence.dao.estadocuenta.MovimientosValorDAOV2#obtenerProyeccionRegistrosContablesControladasHistorico(com.indeval.portaldali.middleware.dto.CriterioConsultaMovimientosValorDTO)
	 */
	public Long obtenerProyeccionRegistrosContablesControladasHistorico(final CriterioConsultaMovimientosValorDTO criterio) {
		return (Long) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuffer query = new StringBuffer();
				ArrayList<Object> params = new ArrayList<Object>();
				ArrayList<Object> tipos = new ArrayList<Object>();
				query.append("SELECT COUNT(*) FROM " + RegContValControladaHistorico.class.getName() + " reg ");
				query.append(construirCriteriosMovimientosControlados("reg", criterio, null, params, tipos));
				Query hQuery = session.createQuery(query.toString()).setParameters(params.toArray(), tipos.toArray(new Type[] {}));
				Object res = hQuery.uniqueResult();
				long total = 0;
				if (res != null) {
					total = ((Number) hQuery.uniqueResult()).longValue();
				}

				//Coloca a null los objetos
				query = null;
				params = null;
				tipos = null;
				return total;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.persistence.dao.estadocuenta.MovimientosValorDAOV2#obtenerProyeccionRegistrosContablesNombradas(com.indeval.portaldali.middleware.dto.CriterioConsultaMovimientosValorDTO)
	 */
	public Long obtenerProyeccionRegistrosContablesNombradas(final CriterioConsultaMovimientosValorDTO criterio) {
		return (Long) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuffer query = new StringBuffer();
				ArrayList<Object> params = new ArrayList<Object>();
				ArrayList<Object> tipos = new ArrayList<Object>();
				query.append("SELECT COUNT(*) FROM " + RegContValNombrada.class.getName() + " reg ");
				query.append(construirCriteriosMovimientosNombrados("reg", criterio, null, params, tipos));
				Query hQuery = session.createQuery(query.toString()).setParameters(params.toArray(), tipos.toArray(new Type[] {}));
				Object res = hQuery.uniqueResult();
				long total = 0;
				if (res != null) {
					total = ((Number) hQuery.uniqueResult()).longValue();
				}

				//Coloca a null los objetos
				query = null;
				params = null;
				tipos = null;
				return total;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.persistence.dao.estadocuenta.MovimientosValorDAOV2#obtenerProyeccionRegistrosContablesNombradasHistorico(com.indeval.portaldali.middleware.dto.CriterioConsultaMovimientosValorDTO)
	 */
	public Long obtenerProyeccionRegistrosContablesNombradasHistorico(final CriterioConsultaMovimientosValorDTO criterio) {
		return (Long) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuffer query = new StringBuffer();
				ArrayList<Object> params = new ArrayList<Object>();
				ArrayList<Object> tipos = new ArrayList<Object>();
				query.append("SELECT COUNT(*) FROM " + RegContValNombradaHistorico.class.getName() + " reg ");
				query.append(construirCriteriosMovimientosNombrados("reg", criterio, null, params, tipos));
				query.append(" AND reg.fechaCreacion = reg.operacion.fechaCreacion ");
				Query hQuery = session.createQuery(query.toString()).setParameters(params.toArray(), tipos.toArray(new Type[] {}));
				Object res = hQuery.uniqueResult();
				long total = 0;
				if (res != null) {
					total = ((Number) hQuery.uniqueResult()).longValue();
				}

				//Coloca a null los objetos
				query = null;
				params = null;
				tipos = null;
				return total;
			}
		});
	}

	/**
	 * Construye una cadena con los criterios necesarios para realizar la
	 * consulta de registros contables de valores controlados.
	 * 
	 * @param alias
	 *            Alias de la tabla de registro contable a utilizar.
	 * @param criterio
	 *            Criterio para construir los parámetros.
	 * @param emisiones
	 *            Conjunto de identificadores de emisiones válidos para la
	 *            consulta
	 * @param params
	 *            Lista de parámetros nuevos que los criterios agregan.
	 * @param tipos
	 *            Lista de tipos nuevos que los criterios agregan.
	 * @return Cadena con la parte de la consulta que involucra los criterios.
	 */
	private String construirCriteriosMovimientosControlados(String alias, CriterioConsultaMovimientosValorDTO criterio, Set<Long> emisiones,
			ArrayList<Object> params, ArrayList<Object> tipos) {
		StringBuffer query = new StringBuffer();

		query.append(" WHERE trunc(" + alias + ".fecha) BETWEEN ? AND ? ");

		params.add(new Timestamp(criterio.getFechaInicial().getTime()));
		tipos.add(new DateType());
		params.add(new Timestamp(criterio.getFechaFinal().getTime()));
		tipos.add(new DateType());

		// EmisionPersistence

		if (criterio.getEmision().getIsin() != null && criterio.getEmision().getIsin().length() > 0) {
			query.append(" AND " + alias + ".posicionControlada.emision.isin = ? ");
			params.add(criterio.getEmision().getIsin().toUpperCase());
			tipos.add(new StringType());
		}

		if (criterio.getEmision().getSerie().getSerie() != null && StringUtils.isNotBlank(criterio.getEmision().getSerie().getSerie())
				&& !criterio.getEmision().getSerie().getSerie().equals("-1")) {
			query.append(" AND " + alias + ".posicionControlada.emision.serie = ? ");
			params.add(criterio.getEmision().getSerie().getSerie());
			tipos.add(new StringType());
		}

		if (criterio.getEmision().getEmisora().getId() > 0  || criterio.getEmision().getEmisora().getId() == DaliConstants.VALOR_COMBO_NINGUNO) {
			query.append(" AND " + alias + ".posicionControlada.emision.idEmisora = ? ");
			params.add(new BigInteger(String.valueOf(criterio.getEmision().getEmisora().getId())));
			tipos.add(new BigIntegerType());
		}

		if (criterio.getEmision().getTipoValor().getId() > 0  || criterio.getEmision().getTipoValor().getId() == DaliConstants.VALOR_COMBO_NINGUNO) {
			query.append(" AND " + alias + ".posicionControlada.emision.instrumento.idInstrumento = ? ");
			params.add(new BigInteger(String.valueOf((criterio.getEmision().getTipoValor().getId()))));
			tipos.add(new BigIntegerType());
		}

		if (criterio.getEmision().getTipoValor().getMercado().getId() > 0) {
			if (criterio.getEmision().getTipoValor().getMercado().getId() != DaliConstants.ID_MERCADO_DINERO) {
				query.append(" AND " + alias + ".posicionControlada.emision.instrumento.mercado.idMercado = ? ");
				params.add(new BigInteger(String.valueOf(criterio.getEmision().getTipoValor().getMercado().getId())));
				tipos.add(new BigIntegerType());
			} else {
				query.append(" AND " + alias + ".posicionControlada.emision.instrumento.mercado.idMercado IN (?,?) ");

				params.add(new BigInteger(String.valueOf(DaliConstants.ID_MERCADO_PAPEL_BANCARIO)));
				tipos.add(new BigIntegerType());

				params.add(new BigInteger(String.valueOf(DaliConstants.ID_MERCADO_PAPEL_GUBER)));
				tipos.add(new BigIntegerType());
			}
		}

		// Boveda
		if (criterio.getBoveda().getId() > 0) {
			query.append(" AND " + alias + ".posicionControlada.boveda.idBoveda = ? ");
			params.add(new BigInteger(String.valueOf(criterio.getBoveda().getId())));
			tipos.add(new BigIntegerType());
		}

		// Cuenta
		if (!"-1".equals(criterio.getCuenta().getNumeroCuenta())) {
			query.append(" AND ( " + "" + alias + ".posicionControlada.cuentaControlada.institucion.tipoInstitucion.claveTipoInstitucion || " + alias
					+ ".posicionControlada.cuentaControlada.institucion.folioInstitucion || " + alias + ".posicionControlada.cuentaControlada.cuenta "
					+ ") = ? ");
			params.add(criterio.getCuenta().getNumeroCuenta());
			tipos.add(new StringType());
		}

		if (criterio.getCuenta().getTipoTenencia().getIdTipoCuenta() > 0) {
			query.append(" AND " + alias + ".posicionControlada.cuentaControlada.tipoCuenta.idTipoCuenta = ? ");
			params.add(new BigInteger(String.valueOf(criterio.getCuenta().getTipoTenencia().getIdTipoCuenta())));
			tipos.add(new BigIntegerType());
		}

		if (criterio.getCuenta().getTipoTenencia().getTipoCustodia() != null && !criterio.getCuenta().getTipoTenencia().getTipoCustodia().equals("-1")) {
			query.append(" AND " + alias + ".posicionControlada.cuentaControlada.tipoCuenta.tipoCustodia = ? ");
			params.add(criterio.getCuenta().getTipoTenencia().getTipoCustodia());
			tipos.add(new StringType());
		}

		query.append(" AND " + alias + ".posicionControlada.cuentaControlada.tipoCuenta.naturalezaContable = ? ");
		params.add(criterio.getCuenta().getTipoTenencia().getTipoNaturaleza().getId());
		tipos.add(new StringType());

		query.append(" AND " + alias + ".posicionControlada.cuentaControlada.tipoCuenta.naturalezaProcesoLiquidacion = ? ");
		params.add(criterio.getCuenta().getTipoTenencia().getTipoCuenta().getId());
		tipos.add(new StringType());

		// Institucion
		if (criterio.getCuenta().getInstitucion().getId() > 0) {
			query.append(" AND " + alias + ".posicionControlada.cuentaControlada.institucion.idInstitucion = ? ");
			params.add(new BigInteger(String.valueOf(criterio.getCuenta().getInstitucion().getId())));
			tipos.add(new BigIntegerType());
		}
		// Tipo de institucion
		if (criterio.getCuenta().getInstitucion().getClaveTipoInstitucion() != null) {
			query.append(" AND " + alias + ".posicionControlada.cuentaControlada.institucion.tipoInstitucion.claveTipoInstitucion = ? ");
			params.add(new BigInteger(String.valueOf(criterio.getCuenta().getInstitucion().getClaveTipoInstitucion())));
			tipos.add(new BigIntegerType());
		}

		// Criterio de emisiones
		if (emisiones != null) {
			query.append(" AND " + alias + ".posicionControlada.emision.idEmision in ( ");
			if (emisiones.size() > 0) {

				for (Iterator<Long> it = emisiones.iterator(); it.hasNext();) {
					Long idEmision = it.next();
					query.append("?");
					params.add(new BigInteger(String.valueOf(idEmision)));
					tipos.add(new BigIntegerType());
					if (it.hasNext()) {
						query.append(",");
					}
				}

			} else {
				query.append("?");
				params.add(new BigInteger(String.valueOf(-1L)));
				tipos.add(new BigIntegerType());
			}
			query.append(" ) ");
		}

		return query.toString();
	}

	/**
	 * Construye una cadena con los criterios necesarios para realizar la
	 * consulta de registros contables de valores de posiciones nombradas.
	 * 
	 * @param alias
	 *            Alias de la tabla de registro contable a utilizar.
	 * @param aliasPosicionOperacion
	 *            Alias de la tabla de posición operación nombrada
	 * @param criterio
	 *            Criterio para construir los parámetros.
	 * @param emisiones
	 *            Conjunto de identificadores de emisiones válidos para la
	 *            consulta
	 * @param params
	 *            Lista de parámetros nuevos que los criterios agregan.
	 * @param tipos
	 *            Lista de tipos nuevos que los criterios agregan.
	 * @return Cadena con la parte de la consulta que involucra los criterios.
	 */
	private String construirCriteriosMovimientosNombrados(String alias, CriterioConsultaMovimientosValorDTO criterio, Set<Long> emisiones,
			ArrayList<Object> params, ArrayList<Object> tipos) {
		StringBuffer query = new StringBuffer();

		if (!criterio.isBusquedaFechaAplicacion() && !criterio.isBusquedaFechaConcertacion()) {
			query.append("  WHERE trunc(" + alias + ".fecha) BETWEEN ? AND ? ");
			params.add(new Timestamp(criterio.getFechaInicial().getTime()));
			tipos.add(new DateType());
			params.add(new Timestamp(criterio.getFechaFinal().getTime()));
			tipos.add(new DateType());
		} else {
			query.append(" WHERE (");
			if (criterio.isBusquedaFechaAplicacion()) {
				query.append("  trunc(" + alias + ".operacion.instruccion.fechaAplicacion) BETWEEN ? AND ? ");
				params.add(new Timestamp(criterio.getFechaInicial().getTime()));
				tipos.add(new DateType());
				params.add(new Timestamp(criterio.getFechaFinal().getTime()));
				tipos.add(new DateType());
			}
			if (criterio.isBusquedaFechaConcertacion()) {
				if (params.size() > 0) {
					query.append(" OR ");
				}
				query.append("  trunc(" + alias + ".operacion.instruccion.fechaConcertacion) BETWEEN ? AND ? ");
				params.add(new Timestamp(criterio.getFechaInicial().getTime()));
				tipos.add(new DateType());
				params.add(new Timestamp(criterio.getFechaFinal().getTime()));
				tipos.add(new DateType());
			}
			query.append(" ) ");
		}

		// EmisionPersistence

		if (criterio.getEmision().getIsin() != null && criterio.getEmision().getIsin().length() > 0) {
			query.append(" AND " + alias + ".posicion.emision.isin = ? ");
			params.add(criterio.getEmision().getIsin().toUpperCase());
			tipos.add(new StringType());
		}

		if (criterio.getEmision().getSerie().getSerie() != null && StringUtils.isNotBlank(criterio.getEmision().getSerie().getSerie())
				&& !criterio.getEmision().getSerie().getSerie().equals("-1")) {
			query.append(" AND " + alias + ".posicion.emision.serie = ? ");
			params.add(criterio.getEmision().getSerie().getSerie());
			tipos.add(new StringType());
		}

		if (criterio.getEmision().getEmisora().getId() > 0 || criterio.getEmision().getEmisora().getId() == DaliConstants.VALOR_COMBO_NINGUNO) {
			query.append(" AND " + alias + ".posicion.emision.idEmisora = ? ");
			params.add(new BigInteger(String.valueOf(criterio.getEmision().getEmisora().getId())));
			tipos.add(new BigIntegerType());
		}

		if (criterio.getEmision().getTipoValor().getId() > 0 || criterio.getEmision().getTipoValor().getId() == DaliConstants.VALOR_COMBO_NINGUNO) {
			query.append(" AND " + alias + ".posicion.emision.instrumento.idInstrumento = ? ");
			params.add(new BigInteger(String.valueOf(criterio.getEmision().getTipoValor().getId())));
			tipos.add(new BigIntegerType());
		}

		if (criterio.getEmision().getTipoValor().getMercado().getId() > 0) {
			if (criterio.getEmision().getTipoValor().getMercado().getId() != DaliConstants.ID_MERCADO_DINERO) {
				query.append(" AND " + alias + ".posicion.emision.instrumento.mercado.idMercado = ? ");
				params.add(new BigInteger(String.valueOf(criterio.getEmision().getTipoValor().getMercado().getId())));
				tipos.add(new BigIntegerType());
			} else {
				query.append(" AND " + alias + ".posicion.emision.instrumento.mercado.idMercado IN (?,?) ");
				params.add(new BigInteger(String.valueOf(DaliConstants.ID_MERCADO_PAPEL_BANCARIO)));
				tipos.add(new BigIntegerType());
				params.add(new BigInteger(String.valueOf(DaliConstants.ID_MERCADO_PAPEL_GUBER)));
				tipos.add(new BigIntegerType());
			}
		}

		// Boveda
		if (criterio.getBoveda().getId() > 0) {
			query.append(" AND " + alias + ".posicion.boveda.idBoveda = ? ");
			params.add(new BigInteger(String.valueOf(criterio.getBoveda().getId())));
			tipos.add(new BigIntegerType());
		}

		// Cuenta
		if (!"-1".equals(criterio.getCuenta().getNumeroCuenta())) {
			query.append(" AND ( " + alias + ".posicion.cuentaNombrada.institucion.tipoInstitucion.claveTipoInstitucion || "
					+ alias + ".posicion.cuentaNombrada.institucion.folioInstitucion || " + alias + ".posicion.cuentaNombrada.cuenta ) = ? ");
			params.add(criterio.getCuenta().getNumeroCuenta());
			tipos.add(new StringType());
		}

		if (criterio.getCuenta().getTipoTenencia().getIdTipoCuenta() > 0) {
			query.append(" AND " + alias + ".posicion.cuentaNombrada.tipoCuenta.idTipoCuenta = ? ");
			params.add(new BigInteger(String.valueOf(criterio.getCuenta().getTipoTenencia().getIdTipoCuenta())));
			tipos.add(new BigIntegerType());
		}

		if (criterio.getCuenta().getTipoTenencia().getTipoCustodia() != null && !criterio.getCuenta().getTipoTenencia().getTipoCustodia().equals("-1")) {
			query.append(" AND " + alias + ".posicion.cuentaNombrada.tipoCuenta.tipoCustodia = ? ");
			params.add(criterio.getCuenta().getTipoTenencia().getTipoCustodia());
			tipos.add(new StringType());
		}

		query.append(" AND " + alias + ".posicion.cuentaNombrada.tipoCuenta.naturalezaContable = ? ");
		params.add(criterio.getCuenta().getTipoTenencia().getTipoNaturaleza().getId());
		tipos.add(new StringType());

		query.append(" AND " + alias + ".posicion.cuentaNombrada.tipoCuenta.naturalezaProcesoLiquidacion = ? ");
		params.add(criterio.getCuenta().getTipoTenencia().getTipoCuenta().getId());
		tipos.add(new StringType());

		// Institucion
		if (criterio.getCuenta().getInstitucion().getId() > 0) {
			query.append(" AND " + alias + ".posicion.cuentaNombrada.institucion.idInstitucion = ? ");
			params.add(new BigInteger(String.valueOf(criterio.getCuenta().getInstitucion().getId())));
			tipos.add(new BigIntegerType());
		}

		// Tipo de institucion
		if (criterio.getCuenta().getInstitucion().getClaveTipoInstitucion() != null) {
			query.append(" AND " + alias + ".posicion.cuentaNombrada.institucion.tipoInstitucion.claveTipoInstitucion = ? ");
			params.add(new BigInteger(String.valueOf(criterio.getCuenta().getInstitucion().getClaveTipoInstitucion())));
			tipos.add(new BigIntegerType());
		}

		// Valores de cuenta Contraparte

		// Criterio rol contraparte

		if (criterio.getRolContraparte() == RolCuentaConstants.AMBOS) {

			query.append(" AND (   (" + construirCriteriosCuentaContraparte(alias, "Receptor", criterio, params, tipos) + " AND (" + alias
					+ ".operacion.idCuentaNombradaTraspasante is null OR " + alias
					+ ".operacion.idCuentaNombradaTraspasante = " + alias + ".posicion.cuentaNombrada.idCuentaNombrada ) ) OR  ("
					+ construirCriteriosCuentaContraparte(alias, "Traspasante", criterio, params, tipos) + " AND (" + alias
					+ ".operacion.idCuentaNombradaReceptor is null OR " + alias
					+ ".operacion.idCuentaNombradaReceptor = " + alias + ".posicion.cuentaNombrada.idCuentaNombrada ) ) ) ");

		}

		if (criterio.getRolContraparte() == RolCuentaConstants.RECEPTOR) {

			query.append(" AND ( " + alias + ".posicion.cuentaNombrada.idCuentaNombrada =  " + alias + ".operacion.idCuentaNombradaTraspasante " + "OR "
					+ alias + ".operacion.idCuentaNombradaTraspasante is null" + ")");

			// Criterio cuenta contraparte
			query.append(" AND ( " + construirCriteriosCuentaContraparte(alias, "Receptor", criterio, params, tipos) + " )");

		}
		if (criterio.getRolContraparte() == RolCuentaConstants.TRASPASANTE) {

			query.append(" AND ( " + alias + ".posicion.cuentaNombrada.idCuentaNombrada =  " + alias + ".operacion.idCuentaNombradaReceptor" + " OR " + alias
					+ ".operacion.idCuentaNombradaReceptor is null" + ")");

			// Criterio cuenta contraparte
			query.append(" AND ( " + construirCriteriosCuentaContraparte(alias, "Traspasante", criterio, params, tipos) + " )");

		}

		// Criterio rol participante

		if (criterio.getRolParticipante() != RolCuentaConstants.AMBOS) {

			if (criterio.getRolParticipante() == RolCuentaConstants.TRASPASANTE) {
				query.append(" AND (  ( " + alias + ".operacion.idCuentaNombradaTraspasante = " + alias + ".posicion.cuentaNombrada.idCuentaNombrada OR "
						+ alias + ".operacion.idCuentaNombradaTraspasante is null ) " + "  ) ");
			} else {
				query.append(" AND (  ( " + alias + ".operacion.idCuentaNombradaReceptor = " + alias + ".posicion.cuentaNombrada.idCuentaNombrada OR " + alias
						+ ".operacion.idCuentaNombradaReceptor is null ) " + "  ) ");
			}

		}

		// Criterio de emisiones
		if (emisiones != null && emisiones.size() < 21) {
			query.append(" AND " + alias + ".posicion.emision.idEmision in ( ");
			if (emisiones.size() > 0) {

				for (Iterator<Long> it = emisiones.iterator(); it.hasNext();) {
					Long idEmision = it.next();
					query.append("?");
					params.add(new BigInteger(String.valueOf(idEmision)));
					tipos.add(new BigIntegerType());
					if (it.hasNext()) {
						query.append(",");
					}
				}

			} else {
				query.append("?");
				params.add(new BigInteger(String.valueOf(-1L)));
				tipos.add(new BigIntegerType());
			}
			query.append(" ) ");
		}

		// Criterio de instrucción

		if (criterio.getTipoInstruccion().getIdTipoInstruccion() > 0) {
			query.append(" AND " + alias + ".operacion.instruccion.tipoInstruccion.idTipoInstruccion = ? ");
			params.add(new BigInteger(String.valueOf(criterio.getTipoInstruccion().getIdTipoInstruccion())));
			tipos.add(new BigIntegerType());
		}
		// tipo de operación
		if (criterio.getTipoOperacion().getId() > 0) {
			query.append(" AND " + alias + ".operacion.tipoOperacion.idTipoOperacion = ? ");
			params.add(new BigInteger(String.valueOf(criterio.getTipoOperacion().getId())));
			tipos.add(new BigIntegerType());
		}
		// folio de instrucción
		if (criterio.getFolioInstruccion() != null && criterio.getFolioInstruccion().length() > 0) {
			query.append(" AND " + alias + ".operacion.instruccion.folioInstruccion = ? ");
			params.add(NumberUtils.toLong(criterio.getFolioInstruccion(), 0));
			tipos.add(new LongType());
		}

		// Exclusion de los tipos de operacion

		query.append(" AND " + alias + ".operacion.tipoOperacion.claveTipoOperacion NOT IN ('").append(Constantes.BLOQUEO_TITULOS).append("','").append(
				Constantes.DESBLOQUEO_TITULOS).append("','").append(Constantes.ENTREGA_CONTRAENTREGA).append("')");

		return query.toString();
	}

	/**
	 * Construye una cadena con los criterios necesarios para filtrar la cuenta
	 * de la contraparte.
	 * 
	 * @param alias
	 *            Alias de la tabla de registro contable a utilizar.
	 * @param aliasCuentaContraparte
	 *            Alias o nombre de la propiedad de cuenta a utilizar. Los
	 *            valores válidos son _ cuentaNombradaTraspasante y
	 *            cuentaNombradaReceptor
	 * @param criterio
	 *            Criterio para construir los parámetros.
	 * @param params
	 *            Lista de parámetros nuevos que los criterios agregan.
	 * @param tipos
	 *            Lista de tipos nuevos que los criterios agregan.
	 * @return Cadena con la parte de la consulta que involucra los criterios.
	 */
	private String construirCriteriosCuentaContraparte(String alias, String aliasCuentaContraparte, CriterioConsultaMovimientosValorDTO criterio,
			ArrayList<Object> params, ArrayList<Object> tipos) {
		StringBuffer query = new StringBuffer();

		query.append(alias + ".operacion.idCuentaNombrada" + aliasCuentaContraparte + " in  (");

		query.append("SELECT cc.idCuentaNombrada FROM " + CuentaNombrada.class.getName() + " cc WHERE ");
		query.append(" cc.tipoCuenta.naturalezaContable = ? ");
		params.add(criterio.getCuentaContraparte().getTipoTenencia().getTipoNaturaleza().getId());
		tipos.add(new StringType());

		query.append(" AND cc.tipoCuenta.naturalezaProcesoLiquidacion = ? ");
		params.add(criterio.getCuentaContraparte().getTipoTenencia().getTipoCuenta().getId());
		tipos.add(new StringType());

		if (!"-1".equals(criterio.getCuentaContraparte().getNumeroCuenta())) {
			query.append(" AND ( cc.institucion.tipoInstitucion.claveTipoInstitucion || " + "cc.institucion.folioInstitucion || " + "cc.cuenta " + ") = ? ");
			params.add(criterio.getCuentaContraparte().getNumeroCuenta());
			tipos.add(new StringType());

		}

		if (criterio.getCuentaContraparte().getTipoTenencia().getIdTipoCuenta() > 0) {
			query.append(" AND cc.tipoCuenta.idTipoCuenta = ? ");
			params.add(new BigInteger(String.valueOf(criterio.getCuentaContraparte().getTipoTenencia().getIdTipoCuenta())));
			tipos.add(new BigIntegerType());

		}

		if (criterio.getCuentaContraparte().getTipoTenencia().getTipoCustodia() != null
				&& !criterio.getCuentaContraparte().getTipoTenencia().getTipoCustodia().equals("-1")) {
			query.append(" AND cc.tipoCuenta.tipoCustodia = ? ");
			params.add(criterio.getCuentaContraparte().getTipoTenencia().getTipoCustodia());
			tipos.add(new StringType());

		}

		// Institucion
		if (criterio.getCuentaContraparte().getInstitucion().getId() > 0) {
			query.append(" AND cc.institucion.idInstitucion = ? ");
			params.add(new BigInteger(String.valueOf(criterio.getCuentaContraparte().getInstitucion().getId())));
			tipos.add(new BigIntegerType());
		}

		// Tipo de institucion
		if (criterio.getCuentaContraparte().getInstitucion().getClaveTipoInstitucion() != null) {
			query.append(" AND cc.institucion.tipoInstitucion.claveTipoInstitucion = ? ");
			params.add(new BigInteger(String.valueOf(criterio.getCuentaContraparte().getInstitucion().getClaveTipoInstitucion())));
			tipos.add(new BigIntegerType());
		}

		query.append(" )");
		return query.toString();
	}

}
