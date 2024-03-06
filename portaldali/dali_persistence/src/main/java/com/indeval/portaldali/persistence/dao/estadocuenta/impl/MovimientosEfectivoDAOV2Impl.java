/**
 * 2H Software - Bursatec
 * 
 * Sistema de Consulta de Estados de Cuenta
 *
 */
package com.indeval.portaldali.persistence.dao.estadocuenta.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.type.BigDecimalType;
import org.hibernate.type.BigIntegerType;
import org.hibernate.type.DateType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.indeval.portaldali.middleware.dto.CriterioConsultaMovimientosEfectivoDTO;
import com.indeval.portaldali.middleware.dto.RegistroContableSaldoControladaDTO;
import com.indeval.portaldali.middleware.dto.RegistroContableSaldoNombradaDTO;
import com.indeval.portaldali.middleware.dto.util.DTOAssembler;
import com.indeval.portaldali.middleware.dto.util.EnsambladorDTOConsultasEfectivo;
import com.indeval.portaldali.middleware.services.common.constants.DaliConstants;
import com.indeval.portaldali.middleware.services.common.constants.RolCuentaConstants;
import com.indeval.portaldali.persistence.dao.common.DateUtilsDao;
import com.indeval.portaldali.persistence.dao.estadocuenta.MovimientosEfectivoDAOV2;
import com.indeval.portaldali.persistence.model.CuentaNombrada;
import com.indeval.portaldali.persistence.model.Divisa;
import com.indeval.portaldali.persistence.model.InstruccionOperacionVal;
import com.indeval.portaldali.persistence.model.RegContEfecControlada;
import com.indeval.portaldali.persistence.model.RegContEfecControladaHistorico;
import com.indeval.portaldali.persistence.model.RegContEfecNombrada;
import com.indeval.portaldali.persistence.model.RegContEfecNombradaHistorico;
import com.indeval.portaldali.persistence.model.TipoAccion;
import com.indeval.portaldali.persistence.model.TipoRetiro;
import com.indeval.portaldali.persistence.util.Constantes;
import com.indeval.portaldali.persistence.util.TipoPagoConstants;
import com.indeval.portaldali.persistence.util.TipoRetiroConstants;

/**
 * Implentación del objeto de acceso a datos para la consulta de los movimientos
 * de saldos de efectivo. Ya sea para cuentas controladas o nombradas.
 * 
 * @author Emigdio Hernández Rodríguez
 * @version 1.0
 */
public class MovimientosEfectivoDAOV2Impl extends HibernateDaoSupport implements MovimientosEfectivoDAOV2 {

	private Logger logger = LoggerFactory.getLogger(MovimientosEfectivoDAOV2Impl.class);
	
	private DateUtilsDao dateUtilsDao;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.dao.MovimientosEfectivoDAO#buscarCuentasDeRegistrosContablesDeEfectivoControlada(com.indeval.estadocuenta.core.application.dto.CriterioConsultaMovimientosEfectivoDTO)
	 */
	@SuppressWarnings("unchecked")
	public List<Long> buscarCuentasDeRegistrosContablesDeEfectivoControlada(final CriterioConsultaMovimientosEfectivoDTO criterio) {
		return (List<Long>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuffer query = new StringBuffer();
				ArrayList<Object> params = new ArrayList<Object>();
				ArrayList<Object> tipos = new ArrayList<Object>();
				query.append("SELECT DISTINCT reg.saldo.idCuenta FROM " + RegContEfecControlada.class.getName() + " reg ");
				query.append(constuirCriteriosEfectivoControlada("reg", criterio, params, tipos, null, false));
				List<BigInteger> ids = 
					session.createQuery(query.toString()).setParameters(params.toArray(), tipos.toArray(new Type[] {})).list();
				List<Long> resultados = DTOAssembler.transformarListaBigIntegerEnLong(ids);

				//Se colocan a nulo los objetos
				query = null;
				params = null;
				tipos = null;
				ids = null;
				return resultados;
			}
		});

	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.dao.MovimientosEfectivoDAO#buscarCuentasDeRegistrosContablesDeEfectivoControladaHistorico(com.indeval.estadocuenta.core.application.dto.CriterioConsultaMovimientosEfectivoDTO)
	 */
	@SuppressWarnings("unchecked")
	public List<Long> buscarCuentasDeRegistrosContablesDeEfectivoControladaHistorico(final CriterioConsultaMovimientosEfectivoDTO criterio) {
		return (List<Long>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuffer query = new StringBuffer();
				ArrayList<Object> params = new ArrayList<Object>();
				ArrayList<Object> tipos = new ArrayList<Object>();
				query.append("SELECT DISTINCT reg.saldo.idCuenta FROM " + RegContEfecControladaHistorico.class.getName() + " reg ");
				query.append(constuirCriteriosEfectivoControlada("reg", criterio, params, tipos, null, true));
				List<BigInteger> ids = 
					session.createQuery(query.toString()).setParameters(params.toArray(), tipos.toArray(new Type[] {})).list();
				List<Long> resultados = DTOAssembler.transformarListaBigIntegerEnLong(ids);

				//Se colocan a nulo los objetos
				query = null;
				params = null;
				tipos = null;
				ids = null;
				return resultados;
			}
		});

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.dao.MovimientosEfectivoDAO#buscarCuentasDeRegistrosContablesDeEfectivoNombrada(com.indeval.estadocuenta.core.application.dto.CriterioConsultaMovimientosEfectivoDTO)
	 */
	@SuppressWarnings("unchecked")
	public List<Long> buscarCuentasDeRegistrosContablesDeEfectivoNombrada(final CriterioConsultaMovimientosEfectivoDTO criterio) {
		return (List<Long>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuffer query = new StringBuffer();
				ArrayList<Object> params = new ArrayList<Object>();
				ArrayList<Object> tipos = new ArrayList<Object>();
				query.append("SELECT DISTINCT reg.saldoNombrada.idCuenta FROM " + RegContEfecNombrada.class.getName() + " reg ");
				query.append(constuirCriteriosEfectivoNombrada("reg", criterio, params, tipos, null, false));				
				List<BigInteger> ids = 
					session.createQuery(query.toString()).setParameters(params.toArray(), tipos.toArray(new Type[] {})).list();
				List<Long> resultados = DTOAssembler.transformarListaBigIntegerEnLong(ids);

				//Se colocan a nulo los objetos
				query = null;
				params = null;
				tipos = null;
				ids = null;
				return resultados;
			}
		});

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.dao.MovimientosEfectivoDAO#buscarCuentasDeRegistrosContablesDeEfectivoNombradaHistorico(com.indeval.estadocuenta.core.application.dto.CriterioConsultaMovimientosEfectivoDTO)
	 */
	@SuppressWarnings("unchecked")
	public List<Long> buscarCuentasDeRegistrosContablesDeEfectivoNombradaHistorico(final CriterioConsultaMovimientosEfectivoDTO criterio) {
		return (List<Long>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuffer query = new StringBuffer();
				ArrayList<Object> params = new ArrayList<Object>();
				ArrayList<Object> tipos = new ArrayList<Object>();
				query.append("SELECT DISTINCT reg.saldoNombradaHistorico.idCuenta FROM " + RegContEfecNombradaHistorico.class.getName() + " reg ");
				query.append(constuirCriteriosEfectivoNombrada("reg", criterio, params, tipos, null, true));
				query.append(" AND reg.fechaCreacion = reg.operacion.fechaCreacion ");
				List<BigInteger> ids = 
					session.createQuery(query.toString()).setParameters(params.toArray(), tipos.toArray(new Type[] {})).list();
				List<Long> resultados = DTOAssembler.transformarListaBigIntegerEnLong(ids);

				//Se colocan a nulo los objetos
				query = null;
				params = null;
				tipos = null;
				ids = null;
				return resultados;
			}
		});

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.dao.MovimientosEfectivoDAO#buscarDivisasDeRegistrosContablesDeEfectivoControlada(com.indeval.estadocuenta.core.application.dto.CriterioConsultaMovimientosEfectivoDTO)
	 */
	@SuppressWarnings("unchecked")
	public List<Long> buscarDivisasDeRegistrosContablesDeEfectivoControlada(final CriterioConsultaMovimientosEfectivoDTO criterio) {
		return (List<Long>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuffer query = new StringBuffer();
				ArrayList<Object> params = new ArrayList<Object>();
				ArrayList<Object> tipos = new ArrayList<Object>();
				query.append("SELECT DISTINCT reg.saldo.idDivisa FROM " + RegContEfecControlada.class.getName() + " reg ");
				query.append(constuirCriteriosEfectivoControlada("reg", criterio, params, tipos, null, false));				
				List<BigInteger> ids = 
					session.createQuery(query.toString()).setParameters(params.toArray(), tipos.toArray(new Type[] {})).list();
				List<Long> resultados = DTOAssembler.transformarListaBigIntegerEnLong(ids);

				//Se colocan a nulo los objetos
				query = null;
				params = null;
				tipos = null;
				ids = null;
				return resultados;
			}
		});
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.dao.MovimientosEfectivoDAO#buscarDivisasDeRegistrosContablesDeEfectivoControladaHistorico(com.indeval.estadocuenta.core.application.dto.CriterioConsultaMovimientosEfectivoDTO)
	 */
	@SuppressWarnings("unchecked")
	public List<Long> buscarDivisasDeRegistrosContablesDeEfectivoControladaHistorico(final CriterioConsultaMovimientosEfectivoDTO criterio) {
		return (List<Long>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuffer query = new StringBuffer();
				ArrayList<Object> params = new ArrayList<Object>();
				ArrayList<Object> tipos = new ArrayList<Object>();
				query.append("SELECT DISTINCT reg.saldo.idDivisa FROM " + RegContEfecControladaHistorico.class.getName() + " reg ");
				query.append(constuirCriteriosEfectivoControlada("reg", criterio, params, tipos, null, true));				
				List<BigInteger> ids = 
					session.createQuery(query.toString()).setParameters(params.toArray(), tipos.toArray(new Type[] {})).list();
				List<Long> resultados = DTOAssembler.transformarListaBigIntegerEnLong(ids);

				//Se colocan a nulo los objetos
				query = null;
				params = null;
				tipos = null;
				ids = null;
				return resultados;
			}
		});
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.dao.MovimientosEfectivoDAO#buscarDivisasDeRegistrosContablesDeEfectivoNombrada(com.indeval.estadocuenta.core.application.dto.CriterioConsultaMovimientosEfectivoDTO)
	 */
	@SuppressWarnings("unchecked")
	public List<Long> buscarDivisasDeRegistrosContablesDeEfectivoNombrada(final CriterioConsultaMovimientosEfectivoDTO criterio) {
		return (List<Long>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuffer query = new StringBuffer();
				ArrayList<Object> params = new ArrayList<Object>();
				ArrayList<Object> tipos = new ArrayList<Object>();
				query.append("SELECT DISTINCT reg.saldoNombrada.idDivisa FROM " + RegContEfecNombrada.class.getName() + " reg ");
				query.append(constuirCriteriosEfectivoNombrada("reg", criterio, params, tipos, null, false));
				List<BigInteger> ids = 
					session.createQuery(query.toString()).setParameters(params.toArray(), tipos.toArray(new Type[] {})).list();
				List<Long> resultados = DTOAssembler.transformarListaBigIntegerEnLong(ids);

				//Se colocan a nulo los objetos
				query = null;
				params = null;
				tipos = null;
				ids = null;
				return resultados;
			}
		});

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.dao.MovimientosEfectivoDAO#buscarDivisasDeRegistrosContablesDeEfectivoNombrada(com.indeval.estadocuenta.core.application.dto.CriterioConsultaMovimientosEfectivoDTO)
	 */
	@SuppressWarnings("unchecked")
	public List<Long> buscarDivisasDeRegistrosContablesDeEfectivoNombradaHistorico(final CriterioConsultaMovimientosEfectivoDTO criterio) {
		return (List<Long>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuffer query = new StringBuffer();
				ArrayList<Object> params = new ArrayList<Object>();
				ArrayList<Object> tipos = new ArrayList<Object>();
				query.append("SELECT DISTINCT reg.saldoNombradaHistorico.idDivisa FROM " + RegContEfecNombradaHistorico.class.getName() + " reg ");
				query.append(constuirCriteriosEfectivoNombrada("reg", criterio, params, tipos, null, true));
				query.append(" AND reg.fechaCreacion = reg.operacion.fechaCreacion ");
				List<BigInteger> ids = 
					session.createQuery(query.toString()).setParameters(params.toArray(), tipos.toArray(new Type[] {})).list();
				List<Long> resultados = DTOAssembler.transformarListaBigIntegerEnLong(ids);

				//Se colocan a nulo los objetos
				query = null;
				params = null;
				tipos = null;
				ids = null;
				return resultados;
			}
		});

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.dao.MovimientosEfectivoDAO#buscarRegistrosContablesDeEfectivoControlada(com.indeval.estadocuenta.core.application.dto.CriterioConsultaMovimientosEfectivoDTO)
	 */
	@SuppressWarnings("unchecked")
	public List<RegistroContableSaldoControladaDTO> buscarRegistrosContablesDeEfectivoControlada(final CriterioConsultaMovimientosEfectivoDTO criterio, final List<Long> idsDivisas) {
		return (List<RegistroContableSaldoControladaDTO>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuffer query = new StringBuffer();
				ArrayList<Object> params = new ArrayList<Object>();
				ArrayList<Object> tipos = new ArrayList<Object>();
				query.append("FROM " + RegContEfecControlada.class.getName() + " reg ");
				query.append(constuirCriteriosEfectivoControlada("reg", criterio, params, tipos, idsDivisas, false));
				query.append(" ORDER BY reg.saldo.cuentaControlada.institucion.tipoInstitucion.claveTipoInstitucion || "
						+ "reg.saldo.cuentaControlada.institucion.folioInstitucion || "
						+ "reg.saldo.cuentaControlada.cuenta, reg.saldo.divisa.idDivisa, reg.saldo.boveda.idBoveda,reg.fecha ");
				Query hQuery = session.createQuery(query.toString()).setParameters(params.toArray(), tipos.toArray(new Type[] {}));
				List<RegContEfecControlada> registros = hQuery.list();
				List<RegistroContableSaldoControladaDTO> resultado = new ArrayList<RegistroContableSaldoControladaDTO>();
				for (RegContEfecControlada saldo : registros) {
//					resultado.add(DTOAssembler.crearRegistroContableSaldoControladaDTO(saldo));
					resultado.add(EnsambladorDTOConsultasEfectivo.crearRegContableSaldoControladaDTO(saldo));
				}

				//Se colocan a nulo los objetos
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
	 * @see com.indeval.estadocuenta.core.application.dao.MovimientosEfectivoDAO#buscarRegistrosContablesDeEfectivoControladaHistorico(com.indeval.estadocuenta.core.application.dto.CriterioConsultaMovimientosEfectivoDTO)
	 */
	@SuppressWarnings("unchecked")
	public List<RegistroContableSaldoControladaDTO> buscarRegistrosContablesDeEfectivoControladaHistorico(final CriterioConsultaMovimientosEfectivoDTO criterio, final List<Long> idsDivisas) {
		return (List<RegistroContableSaldoControladaDTO>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuffer query = new StringBuffer();
				ArrayList<Object> params = new ArrayList<Object>();
				ArrayList<Object> tipos = new ArrayList<Object>();
				query.append("FROM " + RegContEfecControladaHistorico.class.getName() + " reg ");
				query.append(constuirCriteriosEfectivoControlada("reg", criterio, params, tipos, idsDivisas, true));
				query.append(" ORDER BY reg.saldo.cuentaControlada.institucion.tipoInstitucion.claveTipoInstitucion || "
						+ "reg.saldo.cuentaControlada.institucion.folioInstitucion || "
						+ "reg.saldo.cuentaControlada.cuenta, reg.saldo.divisa.idDivisa, reg.saldo.boveda.idBoveda,reg.fecha ");				
				Query hQuery = session.createQuery(query.toString()).setParameters(params.toArray(), tipos.toArray(new Type[] {}));
				List<RegContEfecControladaHistorico> registros = hQuery.list();
				List<RegistroContableSaldoControladaDTO> resultado = new ArrayList<RegistroContableSaldoControladaDTO>();
				for (RegContEfecControladaHistorico saldo : registros) {
					resultado.add(EnsambladorDTOConsultasEfectivo.crearRegContableSaldoControladaHistoricoDTO(saldo));
				}

				//Se colocan a nulo los objetos
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
	 * @see com.indeval.estadocuenta.core.application.dao.MovimientosEfectivoDAO#buscarRegistrosContablesDeEfectivoNombrada(com.indeval.estadocuenta.core.application.dto.CriterioConsultaMovimientosEfectivoDTO)
	 */
	@SuppressWarnings("unchecked")
	public List<RegistroContableSaldoNombradaDTO> buscarRegistrosContablesDeEfectivoNombrada(final CriterioConsultaMovimientosEfectivoDTO criterio, final List<Long> idsDivisas) {
		return (List<RegistroContableSaldoNombradaDTO>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuffer query = new StringBuffer();
				ArrayList<Object> params = new ArrayList<Object>();
				ArrayList<Object> tipos = new ArrayList<Object>();
				query.append("SELECT reg, (SELECT iopv.plazoReporto || ',' || iopv.tasaNegociada FROM " + InstruccionOperacionVal.class.getName() + " iopv ");
				query.append("	WHERE reg.operacion.instruccion.folioInstruccion = iopv.folioControl ");
				query.append("		AND reg.operacion.instruccion.idModuloNegocio = 1 ) ");
				query.append("	, reg.operacion.instruccion.instruccionEfectivo.tipoRetiro ");
				query.append("	, reg.operacion.instruccion.instruccionEfectivo.idTipoPago ");
				query.append("FROM " + RegContEfecNombrada.class.getName() + " reg ");
				query.append(" join fetch reg.operacion ");
				query.append(" join fetch reg.operacion.tipoOperacion ");
				query.append(" join fetch reg.operacion.instruccion ");
				query.append(" left outer join fetch reg.operacion.instruccion.instruccionEfectivo ");
				query.append(" left join reg.operacion.instruccion.instruccionEfectivo.tipoRetiro ");
				query.append(" join fetch reg.operacion.instruccion.tipoInstruccion ");
				query.append(" join fetch reg.saldoNombrada ");
				query.append(" join fetch reg.saldoNombrada.divisa ");
				query.append(" join fetch reg.saldoNombrada.boveda ");
				query.append(" join fetch reg.saldoNombrada.cuentaNombrada ");
				query.append(" left outer join fetch reg.operacion.cuentaEfectivoTraspasante ");
				query.append(" left outer join fetch reg.operacion.cuentaEfectivoTraspasante.tipoCuenta ");
				query.append(" left outer join fetch reg.operacion.cuentaEfectivoTraspasante.institucion ");
				query.append(" left outer join fetch reg.operacion.cuentaEfectivoTraspasante.institucion.tipoInstitucion ");
				query.append(" left outer join fetch reg.operacion.cuentaEfectivoReceptor ");
				query.append(" left outer join fetch reg.operacion.cuentaEfectivoReceptor.tipoCuenta ");
				query.append(" left outer join fetch reg.operacion.cuentaEfectivoReceptor.institucion ");
				query.append(" left outer join fetch reg.operacion.cuentaEfectivoReceptor.institucion.tipoInstitucion ");
				query.append(" left outer join fetch reg.operacion.posicionNombrada ");
				query.append(" left outer join fetch reg.operacion.posicionNombrada.boveda ");
				query.append(" left outer join fetch reg.operacion.posicionNombrada.cupon ");
				query.append(" left outer join fetch reg.operacion.posicionNombrada.cupon.emision ");
				query.append(" left outer join fetch reg.operacion.posicionNombrada.cupon.emision.emisora ");
				query.append(" left outer join fetch reg.operacion.posicionNombrada.cupon.emision.instrumento ");
				query.append(" left outer join fetch reg.operacion.posicionNombrada.cupon.emision.instrumento.mercado ");
				query.append(" left outer join fetch reg.operacion.posicionNombrada.cupon.emision.divisa ");
				query.append(" left outer join fetch reg.operacion.cuentaNombradaTraspasante ");
				query.append(" left outer join fetch reg.operacion.cuentaNombradaTraspasante.institucion ");
				query.append(" left outer join fetch reg.operacion.cuentaNombradaTraspasante.institucion.tipoInstitucion ");
				query.append(" left outer join fetch reg.operacion.cuentaNombradaTraspasante.tipoCuenta ");
				query.append(" left outer join fetch reg.operacion.cuentaNombradaReceptor ");
				query.append(" left outer join fetch reg.operacion.cuentaNombradaReceptor.institucion ");
				query.append(" left outer join fetch reg.operacion.cuentaNombradaReceptor.institucion.tipoInstitucion ");
				query.append(" left outer join fetch reg.operacion.cuentaNombradaReceptor.tipoCuenta ");
				query.append(constuirCriteriosEfectivoNombrada("reg", criterio, params, tipos, idsDivisas, false));
				if (criterio.isOrdenarPorTipoDeInstruccion()) {
					query.append(" ORDER BY reg.saldoNombrada.cuentaNombrada.institucion.tipoInstitucion.claveTipoInstitucion || "
									+ "reg.saldoNombrada.cuentaNombrada.institucion.folioInstitucion || "
									+ "reg.saldoNombrada.cuentaNombrada, reg.saldoNombrada.divisa.idDivisa, reg.saldoNombrada.boveda.idBoveda, reg.operacion.instruccion.tipoInstruccion.nombreCorto, reg.fecha ");
				}
				else {
					query.append(" ORDER BY reg.saldoNombrada.cuentaNombrada.institucion.tipoInstitucion.claveTipoInstitucion || "
							+ "reg.saldoNombrada.cuentaNombrada.institucion.folioInstitucion || "
							+ "reg.saldoNombrada.cuentaNombrada, reg.saldoNombrada.divisa.idDivisa, reg.saldoNombrada.boveda.idBoveda, reg.fecha ");
				}
				Query hQuery = session.createQuery(query.toString()).setParameters(params.toArray(), tipos.toArray(new Type[] {}));
				List<Object[]> registros = hQuery.list();
				List<RegistroContableSaldoNombradaDTO> resultado = new ArrayList<RegistroContableSaldoNombradaDTO>();
				for (Object[] elementos : registros) {
					resultado.add(
							EnsambladorDTOConsultasEfectivo.crearRegistroContableSaldoNombradaConDatosOperacionEInstruccion(
									(RegContEfecNombrada)elementos[0], (String)elementos[1], (TipoRetiro)elementos[2], (Integer)elementos[3]));
				}

				//Se colocan a nulo los objetos
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
	 * @see com.indeval.estadocuenta.core.application.dao.MovimientosEfectivoDAO#buscarRegistrosContablesDeEfectivoNombradaHistorico(com.indeval.estadocuenta.core.application.dto.CriterioConsultaMovimientosEfectivoDTO)
	 */
	@SuppressWarnings("unchecked")
	public List<RegistroContableSaldoNombradaDTO> buscarRegistrosContablesDeEfectivoNombradaHistorico(final CriterioConsultaMovimientosEfectivoDTO criterio, final List<Long> idsDivisas) {
		List<RegistroContableSaldoNombradaDTO> retorno = (List<RegistroContableSaldoNombradaDTO>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuffer query = new StringBuffer();
				ArrayList<Object> params = new ArrayList<Object>();
				ArrayList<Object> tipos = new ArrayList<Object>();
				query.append("SELECT reg, (SELECT iopv.plazoReporto || ',' || iopv.tasaNegociada FROM " + InstruccionOperacionVal.class.getName() + " iopv ");
				query.append("	WHERE reg.operacion.instruccion.folioInstruccion = iopv.folioControl ");
				query.append("		AND reg.operacion.instruccion.idModuloNegocio = 1 ) ");
				query.append("	, reg.operacion.instruccion.instruccionEfectivo.tipoRetiro ");
				query.append("	, reg.operacion.instruccion.instruccionEfectivo.idTipoPago ");
				query.append("FROM " + RegContEfecNombradaHistorico.class.getName() + " reg ");
				query.append(" join fetch reg.operacion ");
				query.append(" join fetch reg.operacion.tipoOperacion ");
				query.append(" join fetch reg.operacion.instruccion ");
				query.append(" join fetch reg.operacion.instruccion.tipoInstruccion ");
				query.append(" left outer join fetch reg.operacion.instruccion.instruccionEfectivo ");
				query.append(" left join  reg.operacion.instruccion.instruccionEfectivo.tipoRetiro ");
				query.append(" join fetch reg.saldoNombradaHistorico ");
				query.append(" join fetch reg.saldoNombradaHistorico.divisa ");
				query.append(" join fetch reg.saldoNombradaHistorico.boveda ");
				query.append(" join fetch reg.saldoNombradaHistorico.cuentaNombrada ");
				query.append(" left outer join fetch reg.operacion.cuentaEfectivoTraspasante ");
				query.append(" left outer join fetch reg.operacion.cuentaEfectivoTraspasante.tipoCuenta ");
				query.append(" left outer join fetch reg.operacion.cuentaEfectivoTraspasante.institucion ");
				query.append(" left outer join fetch reg.operacion.cuentaEfectivoTraspasante.institucion.tipoInstitucion ");
				query.append(" left outer join fetch reg.operacion.cuentaEfectivoReceptor ");
				query.append(" left outer join fetch reg.operacion.cuentaEfectivoReceptor.tipoCuenta ");
				query.append(" left outer join fetch reg.operacion.cuentaEfectivoReceptor.institucion ");
				query.append(" left outer join fetch reg.operacion.cuentaEfectivoReceptor.institucion.tipoInstitucion ");
				query.append(" left outer join fetch reg.operacion.posicionNombrada ");
				query.append(" left outer join fetch reg.operacion.posicionNombrada.boveda ");
				query.append(" left outer join fetch reg.operacion.posicionNombrada.cupon ");
				query.append(" left outer join fetch reg.operacion.posicionNombrada.cupon.emision ");
				query.append(" left outer join fetch reg.operacion.posicionNombrada.cupon.emision.emisora ");
				query.append(" left outer join fetch reg.operacion.posicionNombrada.cupon.emision.instrumento ");
				query.append(" left outer join fetch reg.operacion.posicionNombrada.cupon.emision.instrumento.mercado ");
				query.append(" left outer join fetch reg.operacion.posicionNombrada.cupon.emision.divisa ");
				query.append(" left outer join fetch reg.operacion.cuentaNombradaTraspasante ");
				query.append(" left outer join fetch reg.operacion.cuentaNombradaTraspasante.institucion ");
				query.append(" left outer join fetch reg.operacion.cuentaNombradaTraspasante.institucion.tipoInstitucion ");
				query.append(" left outer join fetch reg.operacion.cuentaNombradaTraspasante.tipoCuenta ");
				query.append(" left outer join fetch reg.operacion.cuentaNombradaReceptor ");
				query.append(" left outer join fetch reg.operacion.cuentaNombradaReceptor.institucion ");
				query.append(" left outer join fetch reg.operacion.cuentaNombradaReceptor.institucion.tipoInstitucion ");
				query.append(" left outer join fetch reg.operacion.cuentaNombradaReceptor.tipoCuenta ");
				query.append(constuirCriteriosEfectivoNombrada("reg", criterio, params, tipos, idsDivisas, true));
				// 	Criterio para comparar fechas de registro contable contra operacion nombrada historicos
				query.append(" AND reg.fechaCreacion = reg.operacion.fechaCreacion  ");
				if (criterio.isOrdenarPorTipoDeInstruccion()) {
					query.append(" ORDER BY reg.saldoNombradaHistorico.cuentaNombrada.institucion.tipoInstitucion.claveTipoInstitucion || "
									+ "reg.saldoNombradaHistorico.cuentaNombrada.institucion.folioInstitucion || "
									+ "reg.saldoNombradaHistorico.cuentaNombrada, reg.saldoNombradaHistorico.divisa.idDivisa, reg.saldoNombradaHistorico.boveda.idBoveda, reg.operacion.instruccion.tipoInstruccion.nombreCorto, reg.fecha ");
				}
				else {
					query.append(" ORDER BY reg.saldoNombradaHistorico.cuentaNombrada.institucion.tipoInstitucion.claveTipoInstitucion || "
							+ "reg.saldoNombradaHistorico.cuentaNombrada.institucion.folioInstitucion || "
							+ "reg.saldoNombradaHistorico.cuentaNombrada, reg.saldoNombradaHistorico.divisa.idDivisa, reg.saldoNombradaHistorico.boveda.idBoveda, reg.fecha ");
				}
				Query hQuery = session.createQuery(query.toString()).setParameters(params.toArray(), tipos.toArray(new Type[] {}));
				List<Object[]> registros = hQuery.list();
				List<RegistroContableSaldoNombradaDTO> resultado = new ArrayList<RegistroContableSaldoNombradaDTO>();
				for (Object[] elementos : registros) {
					resultado.add(EnsambladorDTOConsultasEfectivo.crearRegistroContableSaldoNombradaConDatosOperacionEInstruccionHistorico(
							(RegContEfecNombradaHistorico)elementos[0], (String)elementos[1], (TipoRetiro)elementos[2], (Integer)elementos[3], dateUtilsDao.getDateFechaDB()));
				}

				//Se colocan a nulo los objetos
				query = null;
				params = null;
				tipos = null;
				registros = null;
				return resultado;
			}
		});
		
		return retorno;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.persistence.dao.estadocuenta.MovimientosEfectivoDAO#obtenerProyeccionDeRegistrosContablesDeSaldosControlada(com.indeval.portaldali.middleware.dto.CriterioConsultaMovimientosEfectivoDTO)
	 */
	public long obtenerProyeccionDeRegistrosContablesDeSaldosControlada(final CriterioConsultaMovimientosEfectivoDTO criterio) {
		return (Long) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuffer query = new StringBuffer();
				ArrayList<Object> params = new ArrayList<Object>();
				ArrayList<Object> tipos = new ArrayList<Object>();
				query.append("SELECT COUNT(*) FROM " + RegContEfecControlada.class.getName() + " reg ");
				query.append(constuirCriteriosEfectivoControlada("reg", criterio, params, tipos, null, false));
				long res = 0;
				Number resultadoBD = (Number) session.createQuery(query.toString()).setParameters(params.toArray(), tipos.toArray(new Type[] {})).uniqueResult();
				if (resultadoBD != null) {
					res = resultadoBD.longValue();
				}

				//Se colocan a nulo los objetos
				query = null;
				params = null;
				tipos = null;
				return res;
			}
		});
		
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.persistence.dao.estadocuenta.MovimientosEfectivoDAO#obtenerProyeccionDeRegistrosContablesDeSaldosControladaHistorico(com.indeval.portaldali.middleware.dto.CriterioConsultaMovimientosEfectivoDTO)
	 */
	public long obtenerProyeccionDeRegistrosContablesDeSaldosControladaHistorico(final CriterioConsultaMovimientosEfectivoDTO criterio) {
		return (Long) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuffer query = new StringBuffer();
				ArrayList<Object> params = new ArrayList<Object>();
				ArrayList<Object> tipos = new ArrayList<Object>();
				query.append("SELECT COUNT(*) FROM " + RegContEfecControladaHistorico.class.getName() + " reg ");
				query.append(constuirCriteriosEfectivoControlada("reg", criterio, params, tipos, null, true));
				long res = 0;
				Number resultadoBD = (Number) session.createQuery(query.toString()).setParameters(params.toArray(), tipos.toArray(new Type[] {})).uniqueResult();
				if (resultadoBD != null) {
					res = resultadoBD.longValue();
				}

				//Se colocan a nulo los objetos
				query = null;
				params = null;
				tipos = null;
				return res;
			}
		});
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.persistence.dao.estadocuenta.MovimientosEfectivoDAO#obtenerProyeccionDeRegistrosContablesDeSaldosNombrada(com.indeval.portaldali.middleware.dto.CriterioConsultaMovimientosEfectivoDTO)
	 */
	public long obtenerProyeccionDeRegistrosContablesDeSaldosNombrada(final CriterioConsultaMovimientosEfectivoDTO criterio) {
		return (Long) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuffer query = new StringBuffer();
				ArrayList<Object> params = new ArrayList<Object>();
				ArrayList<Object> tipos = new ArrayList<Object>();
				query.append("SELECT COUNT(*) FROM " + RegContEfecNombrada.class.getName() + " reg ");
				query.append(constuirCriteriosEfectivoNombrada("reg", criterio, params, tipos, null, false));				
				long res = 0;
				Number resultadoBD = (Number) session.createQuery(query.toString()).setParameters(params.toArray(), tipos.toArray(new Type[] {})).uniqueResult();
				if (resultadoBD != null) {
					res = resultadoBD.longValue();
				}

				//Se colocan a nulo los objetos
				query = null;
				params = null;
				tipos = null;
				return res;
			}
		});
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.persistence.dao.estadocuenta.MovimientosEfectivoDAO#obtenerProyeccionDeRegistrosContablesDeSaldosNombradaHistorico(com.indeval.portaldali.middleware.dto.CriterioConsultaMovimientosEfectivoDTO)
	 */
	public long obtenerProyeccionDeRegistrosContablesDeSaldosNombradaHistorico(final CriterioConsultaMovimientosEfectivoDTO criterio) {
		return (Long) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuffer query = new StringBuffer();
				ArrayList<Object> params = new ArrayList<Object>();
				ArrayList<Object> tipos = new ArrayList<Object>();
				query.append("SELECT COUNT(*) FROM " + RegContEfecNombradaHistorico.class.getName() + " reg ");
				query.append(constuirCriteriosEfectivoNombrada("reg", criterio, params, tipos, null, true));
				query.append(" AND reg.fechaCreacion = reg.operacion.fechaCreacion ");				
				long res = 0;
				Number resultadoBD = (Number) session.createQuery(query.toString()).setParameters(params.toArray(), tipos.toArray(new Type[] {})).uniqueResult();
				if (resultadoBD != null) {
					res = resultadoBD.longValue();
				}

				//Se colocan a nulo los objetos
				query = null;
				params = null;
				tipos = null;
				return res;
			}
		});
		
	}

	/**
	 * Construye una cadena con la sección correspondiente a los criterios de la
	 * consulta en base al objeto de criterios recibido como parámetro.
	 * 
	 * @param alias Alias de la tabla de registro contable.
	 * @param criterio Objeto con los criterios de la consulta
	 * @param params Parametros que utilizan los criterios.
	 * @param tipos Tipos de datos que utilizan los criterio.
	 * @return Cadena con los criterios de la consulta.
	 */
	private String constuirCriteriosEfectivoControlada(
			String alias, CriterioConsultaMovimientosEfectivoDTO criterio, ArrayList<Object> params,
			ArrayList<Object> tipos, List<Long> idsDivisas, Boolean isHistorico) {
		
		StringBuffer query = new StringBuffer();
		
		if(isHistorico) {
			query.append(" where " + alias + ".fechaCreacion BETWEEN ? AND ? ");
		}
		else {			
			query.append(" where trunc(" + alias + ".fecha) BETWEEN ? AND ? ");
		}
		params.add(new Timestamp(criterio.getFechaInicial().getTime()));
		tipos.add(new DateType());
		params.add(new Timestamp(criterio.getFechaFinal().getTime()));
		tipos.add(new DateType());

		// Cuenta
		if (criterio.getCuenta().getTipoNaturaleza() != null && !criterio.getCuenta().getTipoNaturaleza().getId().equals("-1")) {
			query.append("AND " + alias + ".saldo.cuentaControlada.tipoCuenta.naturalezaContable = ? ");
			params.add(criterio.getCuenta().getTipoNaturaleza().getId());
			tipos.add(new StringType());
		}

		if (criterio.getCuenta() != null && !"-1".equals(criterio.getCuenta().getNumeroCuenta())) {
			query.append("AND (" + alias + ".saldo.cuentaControlada.institucion.tipoInstitucion.claveTipoInstitucion " + "|| " + alias
					+ ".saldo.cuentaControlada.institucion.folioInstitucion || " + "" + alias + ".saldo.cuentaControlada.cuenta)" + " = ? ");
			params.add(criterio.getCuenta().getNumeroCuenta());
			tipos.add(new StringType());
		}

		// Divisa
		if (criterio.getDivisa() != null && criterio.getDivisa().getId() > 0) {
			query.append("AND " + alias + ".saldo.idDivisa = ? ");
			params.add(new BigInteger(String.valueOf(criterio.getDivisa().getId())));
			tipos.add(new BigIntegerType());
		}
		if (idsDivisas != null && idsDivisas.size() > 0) {
			query.append(" and " + alias + ".saldo.idDivisa in ( ");
			for (Iterator<Long> it = idsDivisas.iterator(); it.hasNext();) {
				Long idDivisa = it.next();
				query.append("?");
				params.add(new BigInteger(String.valueOf(idDivisa)));
				tipos.add(new BigIntegerType());
				if (it.hasNext()) {
					query.append(",");
				}
			}
			query.append(" ) ");
		}

		// Boveda
		if (criterio.getBoveda() != null && criterio.getBoveda().getId() > 0) {
			query.append("AND " + alias + ".saldo.idBoveda = ? ");
			params.add(new BigInteger(String.valueOf(criterio.getBoveda().getId())));
			tipos.add(new BigIntegerType());
		}

		// Institucion
		if (criterio.getCuenta().getInstitucion().getId() > 0) {
			query.append("AND " + alias + ".saldo.cuentaControlada.institucion.idInstitucion = ? ");
			params.add(new BigInteger(String.valueOf(criterio.getCuenta().getInstitucion().getId())));
			tipos.add(new BigIntegerType());
		}

		// Tipo de institucion
		if (criterio.getCuenta().getInstitucion().getClaveTipoInstitucion() != null) {
			query.append("AND " + alias + ".saldo.cuentaControlada.institucion.tipoInstitucion.claveTipoInstitucion = ? ");
			params.add(new BigInteger(String.valueOf(criterio.getCuenta().getInstitucion().getClaveTipoInstitucion())));
			tipos.add(new BigIntegerType());
		}

		if (criterio.getRolParticipante() != RolCuentaConstants.AMBOS) {

			if (criterio.getRolParticipante() == RolCuentaConstants.TRASPASANTE) {
				query.append(" AND " + alias + ".tipoAccion in (?,?) ");
				params.add(TipoAccion.CARGO_PASIVO_VALUE);
				tipos.add(new IntegerType());
				params.add(TipoAccion.ABONO_ACTIVO_VALUE);
				tipos.add(new IntegerType());
			} else {
				query.append(" AND reg.tipoAccion in (?,?) ");
				params.add(TipoAccion.ABONO_PASIVO_VALUE);
				tipos.add(new IntegerType());
				params.add(TipoAccion.CARGO_ACTIVO_VALUE);
				tipos.add(new IntegerType());
			}

		}
		return query.toString();

	}

	/**
	 * Construye una cadena con la sección correspondiente a los criterios de la
	 * consulta en base al objeto de criterios recibido como parámetro.
	 * 
	 * @param alias
	 *            Alias de la tabla de registro contable.
	 * @param criterio
	 *            Objeto con los criterios de la consulta
	 * @param params
	 *            Parametros que utilizan los criterios.
	 * @param tipos
	 *            Tipos de datos que utilizan los criterio.
	 * @return Cadena con los criterios de la consulta.
	 */
	
	/**
	 * Pablo Julián Balderas Méndez 03/Noviembre/2013
	 * Se ajusta la columna saldoNombrada por saldoNombradaHistorico.
	 */
	private String constuirCriteriosEfectivoNombrada(String alias, CriterioConsultaMovimientosEfectivoDTO criterio, ArrayList<Object> params,
			ArrayList<Object> tipos, List<Long> idsDivisas, Boolean isHistorico) {
		StringBuffer query = new StringBuffer();

		if (!criterio.isBusquedaFechaAplicacion() && !criterio.isBusquedaFechaConcertacion()) {
			if(isHistorico) {				
				query.append(" where " + alias + ".fechaCreacion between ? and ? ");
			}
			else {
				query.append(" where trunc(" + alias + ".fecha) between ? and ? ");
			}
			params.add(new Timestamp(criterio.getFechaInicial().getTime()));
			tipos.add(new DateType());
			params.add(new Timestamp(criterio.getFechaFinal().getTime()));
			tipos.add(new DateType());
		} 
		else {
			query.append(" WHERE (");
			if (criterio.isBusquedaFechaAplicacion()) {
				query.append("  trunc(reg.operacion.instruccion.fechaAplicacion) BETWEEN ? AND ? ");
				params.add(new Timestamp(criterio.getFechaInicial().getTime()));
				tipos.add(new DateType());
				params.add(new Timestamp(criterio.getFechaFinal().getTime()));
				tipos.add(new DateType());
			}
			if (criterio.isBusquedaFechaConcertacion()) {
				if (params.size() > 0) {
					query.append(" OR ");
				}
				query.append("  trunc(reg.operacion.instruccion.fechaConcertacion) BETWEEN ? AND ? ");
				params.add(new Timestamp(criterio.getFechaInicial().getTime()));
				tipos.add(new DateType());
				params.add(new Timestamp(criterio.getFechaFinal().getTime()));
				tipos.add(new DateType());
			}
			query.append(" ) ");
		}

		// Institucion
		if (criterio.getCuenta().getInstitucion() != null && criterio.getCuenta().getInstitucion().getId() > 0) {
			query.append("AND " + alias + ".saldoNombrada.cuentaNombrada.institucion.idInstitucion = ? ");
			params.add(new BigInteger(String.valueOf(criterio.getCuenta().getInstitucion().getId())));
			tipos.add(new BigIntegerType());
		}

		// Cuenta
		if (criterio.getCuenta().getTipoNaturaleza() != null && !criterio.getCuenta().getTipoNaturaleza().getId().equals("-1")) {
			query.append("AND " + alias + ".saldoNombrada.cuentaNombrada.tipoCuenta.naturalezaContable = ? ");
			params.add(criterio.getCuenta().getTipoNaturaleza().getId());
			tipos.add(new StringType());
		}

		if (criterio.getCuenta() != null && !"-1".equals(criterio.getCuenta().getNumeroCuenta())) {
			String numeroCuenta = criterio.getCuenta().getNumeroCuenta();
			if( StringUtils.isNotBlank(numeroCuenta) &&
					numeroCuenta.length() == 9 ) {
				String id = numeroCuenta.substring(0,2);
				String folio = numeroCuenta.substring(2,5);
				String cuenta = numeroCuenta.substring(5,9);
				query.append(" AND " + alias + ".saldoNombrada.cuentaNombrada.institucion.tipoInstitucion.claveTipoInstitucion = ? " );
				query.append(" AND " + alias + ".saldoNombrada.cuentaNombrada.institucion.folioInstitucion = ? " );
				query.append(" AND " + alias + ".saldoNombrada.cuentaNombrada.cuenta = ? " );
				params.add(id);
				params.add(folio);
				params.add(cuenta);
				tipos.add(new StringType());
				tipos.add(new StringType());
			tipos.add(new StringType());
		}
//			query.append("AND (" + alias + ".saldoNombrada.cuentaNombrada.institucion.tipoInstitucion.claveTipoInstitucion " + "|| " + alias
//					+ ".saldoNombrada.cuentaNombrada.institucion.folioInstitucion || " + "" + alias + ".saldoNombrada.cuentaNombrada.cuenta)" + " = ? ");
//			params.add(criterio.getCuenta().getNumeroCuenta());
//			tipos.add(new StringType());
		}

		// Emision

		if (criterio.getEmision() != null && criterio.getEmision().getIsin() != null && criterio.getEmision().getIsin().length() > 0) {
			query.append(" and reg.operacion.posicionNombrada.cupon.emision.isin = ? ");
			params.add(criterio.getEmision().getIsin().toUpperCase());
			tipos.add(new StringType());
		}

		if (criterio.getEmision() != null && criterio.getEmision().getSerie() != null && StringUtils.isNotBlank(criterio.getEmision().getSerie().getSerie())
				&& !criterio.getEmision().getSerie().getSerie().equals("-1")) {
			query.append(" and reg.operacion.posicionNombrada.cupon.emision.serie = ? ");
			params.add(criterio.getEmision().getSerie().getSerie());
			tipos.add(new StringType());
		}

		if (criterio.getEmision() != null && criterio.getEmision().getEmisora() != null && criterio.getEmision().getEmisora().getId() > 0) {
			query.append(" and reg.operacion.posicionNombrada.cupon.emision.idEmisora = ? ");
			params.add(new BigInteger(String.valueOf(criterio.getEmision().getEmisora().getId())));
			tipos.add(new BigIntegerType());
		}else {
			if (criterio.getEmision() != null && criterio.getEmision().getEmisora() != null
					&& StringUtils.isNotBlank(criterio.getEmision().getEmisora().getDescripcion()) 
					&& !"TODOS".equalsIgnoreCase(criterio.getEmision().getEmisora().getDescripcion())) {
				query.append(" and reg.operacion.posicionNombrada.cupon.emision.idEmisora is null "); 
			}
		}

		if (criterio.getEmision() != null && criterio.getEmision().getTipoValor() != null && criterio.getEmision().getTipoValor().getId() > 0) {
			query.append(" and reg.operacion.posicionNombrada.cupon.emision.instrumento.idInstrumento = ? ");
			params.add(new BigInteger(String.valueOf(criterio.getEmision().getTipoValor().getId())));
			tipos.add(new BigIntegerType());
		} else { 
			if (criterio.getEmision() != null && criterio.getEmision().getTipoValor() != null 
					&& StringUtils.isNotBlank(criterio.getEmision().getTipoValor().getClaveTipoValor())) {
				query.append(" and reg.operacion.posicionNombrada.cupon.emision.instrumento.claveTipoValor = ? ");
				params.add(criterio.getEmision().getTipoValor().getClaveTipoValor());
				tipos.add(new StringType());
			}
		}

		if (criterio.getEmision() != null && criterio.getEmision().getTipoValor() != null && criterio.getEmision().getTipoValor().getMercado() != null
				&& criterio.getEmision().getTipoValor().getMercado().getId() > 0) {
			if (DaliConstants.ID_MERCADO_DINERO != criterio.getEmision().getTipoValor().getMercado().getId()) {
				query.append(" and reg.operacion.posicionNombrada.cupon.emision.instrumento.mercado.idMercado = ? ");
				params.add(new BigInteger(String.valueOf(criterio.getEmision().getTipoValor().getMercado().getId())));
				tipos.add(new BigIntegerType());
			} else {
				query.append(" and reg.operacion.posicionNombrada.cupon.emision.instrumento.mercado.idMercado in (?,?) ");

				params.add(new BigInteger(String.valueOf(DaliConstants.ID_MERCADO_PAPEL_BANCARIO)));
				tipos.add(new BigIntegerType());

				params.add(new BigInteger(String.valueOf(DaliConstants.ID_MERCADO_PAPEL_GUBER)));
				tipos.add(new BigIntegerType());
			}
		}

		// Divisa

		if (criterio.getDivisa() != null && criterio.getDivisa().getId() > 0) {
			query.append("AND " + alias + ".saldoNombrada.idDivisa = ? ");
			params.add(new BigInteger(String.valueOf(criterio.getDivisa().getId())));
			tipos.add(new BigIntegerType());
		} else if (idsDivisas != null && idsDivisas.size() > 0) {
			query.append(" and " + alias + ".saldoNombrada.idDivisa in ( ");
			for (Iterator<Long> it = idsDivisas.iterator(); it.hasNext();) {
				Long idDivisa = it.next();
				query.append("?");
				params.add(new BigInteger(String.valueOf(idDivisa)));
				tipos.add(new BigIntegerType());
				if (it.hasNext()) {
					query.append(",");
				}
			}
			query.append(" ) ");
		}

		// Boveda
		if (criterio.getBoveda() != null && criterio.getBoveda().getId() > 0) {
			query.append("AND " + alias + ".saldoNombrada.idBoveda = ? ");
			params.add(new BigInteger(String.valueOf(criterio.getBoveda().getId())));
			tipos.add(new BigIntegerType());
		}

		// Tipo de institucion
		if (criterio.getCuenta().getInstitucion().getClaveTipoInstitucion() != null) {
			query.append("AND " + alias + ".saldoNombrada.cuentaNombrada.institucion.tipoInstitucion.claveTipoInstitucion = ? ");
			params.add(new BigInteger(String.valueOf(criterio.getCuenta().getInstitucion().getClaveTipoInstitucion())));
			tipos.add(new BigIntegerType());
		}

		// Importe
		if (criterio.getImporte() != null && criterio.getImporte().compareTo(BigDecimal.ZERO) > 0) {
			query.append("AND " + alias + ".importe = ? ");
			logger.info("Importe: [" + criterio.getImporte().setScale(2,RoundingMode.HALF_DOWN) + "]");
			params.add(criterio.getImporte().setScale(2,RoundingMode.HALF_DOWN));
			tipos.add(new BigDecimalType());
		}
		
		// Tipo Retiro
		/**
		 * Se realiza merge con la revisión 200813 que tiene los cambios para la resolución del JIRA PORDALI-2072
		 * Pablo Balderas 08/Octubre/2014
		 */
		if (StringUtils.isNotBlank(criterio.getTipoRetiro()) && 
				!"-1".equals(criterio.getTipoRetiro()) &&
				(TipoRetiroConstants.NOMBRE_CORTO_RETIRO_SPEI.equalsIgnoreCase(criterio.getTipoRetiro().trim()) || 
				TipoRetiroConstants.NOMBRE_CORTO_RETIRO_SIAC.equalsIgnoreCase(criterio.getTipoRetiro().trim())) ) {
			String tipoDeposito = "";			
			Integer idTipoRetiro = null;
			if( criterio.getTipoRetiro().equalsIgnoreCase(TipoRetiroConstants.NOMBRE_CORTO_RETIRO_SPEI) ) {
				tipoDeposito = tipoDeposito + TipoPagoConstants.PARTICIPANTE_A_PARTICIPANTE_SPEI + ", ";
				tipoDeposito = tipoDeposito + TipoPagoConstants.PARTICIPANTE_A_TERCERO_SPEI  + ", ";
				tipoDeposito = tipoDeposito + TipoPagoConstants.TERCERO_A_TERCERO_SPEI;
				idTipoRetiro = TipoRetiroConstants.ID_RETIRO_SPEI;
			} 
			else if( criterio.getTipoRetiro().equalsIgnoreCase(TipoRetiroConstants.NOMBRE_CORTO_RETIRO_SIAC) ) {
				tipoDeposito = tipoDeposito + TipoPagoConstants.TRASPASO_FONDOS_SIAC;
				idTipoRetiro = TipoRetiroConstants.ID_RETIRO_SIAC;
			}
			query.append(" AND (" + alias + ".operacion.instruccion.instruccionEfectivo.tipoRetiro = ? ");
			query.append(" OR " + alias + ".operacion.instruccion.instruccionEfectivo.idTipoPago IN ( " + tipoDeposito + " ) ) ");
			params.add(idTipoRetiro);
			tipos.add(new IntegerType());
		}

		// Criterio rol contraparte

		if (criterio.getRolContraparte() == RolCuentaConstants.AMBOS) {

			query.append(" AND (   (" + construirCriteriosCuentaContraparte("reg", "Receptor", criterio, params, tipos) + " AND ("
					+ " reg.operacion.idCuentaEfectivoTraspasante is null OR "
					+ " reg.operacion.idCuentaEfectivoTraspasante = reg.saldoNombrada.cuentaNombrada.idCuentaNombrada ) " + ")  " + "OR  ("
					+ construirCriteriosCuentaContraparte("reg", "Traspasante", criterio, params, tipos) + " AND ("
					+ " reg.operacion.idCuentaEfectivoReceptor is null OR "
					+ " reg.operacion.idCuentaEfectivoReceptor = reg.saldoNombrada.cuentaNombrada.idCuentaNombrada ) " + ") ) ");

			if (criterio.getCuentaValoresContraparte() != null && criterio.getCuentaValoresContraparte().getIdCuenta() > 0) {

				query.append(" AND ((reg.operacion.idCuentaNombradaTraspasante = ? ");
				query.append(" AND reg.operacion.idInstitucionReceptor = reg.saldoNombrada.cuentaNombrada.institucion.idInstitucion ) OR ( ");
				query.append(" reg.operacion.idCuentaNombradaReceptor = ? ");
				query.append(" AND reg.operacion.idInstitucionTraspasante = reg.saldoNombrada.cuentaNombrada.institucion.idInstitucion )) ");

				params.add(new BigInteger(String.valueOf(criterio.getCuentaValoresContraparte().getIdCuenta())));
				tipos.add(new BigIntegerType());

				params.add(new BigInteger(String.valueOf(criterio.getCuentaValoresContraparte().getIdCuenta())));
				tipos.add(new BigIntegerType());
			}
		}

		if (criterio.getRolContraparte() == RolCuentaConstants.RECEPTOR) {

			query.append(" AND ( reg.saldoNombrada.cuentaNombrada.idCuentaNombrada =  reg.operacion.idCuentaNombradaTraspasante " + "OR "
					+ " reg.operacion.idCuentaNombradaTraspasante is null" + ")");

			// Criterio cuenta contraparte
			query.append(" AND ( " + construirCriteriosCuentaContraparte("reg", "Receptor", criterio, params, tipos) + " )");

			if (criterio.getCuentaValoresContraparte() != null && criterio.getCuentaValoresContraparte().getIdCuenta() > 0) {

				query.append(" AND ( reg.operacion.idCuentaNombradaReceptor = ? ");
				query.append(" AND reg.operacion.idInstitucionTraspasante = reg.saldoNombrada.cuentaNombrada.institucion.idInstitucion )");

				params.add(new BigInteger(String.valueOf(criterio.getCuentaValoresContraparte().getIdCuenta())));
				tipos.add(new BigIntegerType());
			}

		}
		if (criterio.getRolContraparte() == RolCuentaConstants.TRASPASANTE) {

			query.append(" AND ( reg.saldoNombrada.cuentaNombrada.idCuentaNombrada =  reg.operacion.idCuentaNombradaReceptor" + " OR "
					+ " reg.operacion.idCuentaNombradaReceptor is null" + ")");

			// Criterio cuenta contraparte
			query.append(" AND ( " + construirCriteriosCuentaContraparte("reg", "Traspasante", criterio, params, tipos) + " )");

			if (criterio.getCuentaValoresContraparte() != null && criterio.getCuentaValoresContraparte().getIdCuenta() > 0) {

				query.append(" AND (reg.operacion.idCuentaNombradaTraspasante = ? ");
				query.append(" AND reg.operacion.idInstitucionReceptor = reg.saldoNombrada.cuentaNombrada.institucion.idInstitucion )");

				params.add(new BigInteger(String.valueOf(criterio.getCuentaValoresContraparte().getIdCuenta())));
				tipos.add(new BigIntegerType());
			}
		}

		// Criterio rol participante
		if (criterio.getRolParticipante() != RolCuentaConstants.AMBOS) {

			if (criterio.getRolParticipante() == RolCuentaConstants.TRASPASANTE) {
				query.append(" AND (  " + " ( reg.operacion.idCuentaEfectivoTraspasante = reg.saldoNombrada.cuentaNombrada.idCuentaNombrada OR "
						+ " reg.operacion.idCuentaEfectivoTraspasante is null ) " + "  ) ");
			} else {
				query.append(" AND (  " + "  ( reg.operacion.idCuentaEfectivoReceptor = reg.saldoNombrada.cuentaNombrada.idCuentaNombrada OR "
						+ " reg.operacion.idCuentaEfectivoReceptor is null ) " + "  ) ");
			}

		}

		// Criterio de instrucción

		if (criterio.getTipoInstruccion().getIdTipoInstruccion() > 0) {
			query.append(" AND reg.operacion.instruccion.tipoInstruccion.idTipoInstruccion = ? ");
			params.add(new BigInteger(String.valueOf(criterio.getTipoInstruccion().getIdTipoInstruccion())));
			tipos.add(new BigIntegerType());
		}
		// tipo de operación
		if (criterio.getTipoOperacion().getId() > 0) {
			query.append(" AND reg.operacion.tipoOperacion.idTipoOperacion = ? ");
			params.add(new BigInteger(String.valueOf(criterio.getTipoOperacion().getId())));
			tipos.add(new BigIntegerType());
		}
		// folio de instrucción
		if (criterio.getFolioInstruccion() != null && criterio.getFolioInstruccion().length() > 0) {
			query.append(" AND reg.operacion.instruccion.folioInstruccion = ? ");
			params.add(NumberUtils.toLong(criterio.getFolioInstruccion(), 0));
			tipos.add(new LongType());
		}

		// Exclusion de los tipos de operacion
		query.append(" AND reg.operacion.tipoOperacion.claveTipoOperacion NOT IN ('").append(Constantes.BLOQUEO_EFECTIVO).append("','").append(
				Constantes.DESBLOQUEO_EFECTIVO).append("') ");

		//Obtiene la cadena
		String strQuery = query.toString();
		//Ajusta la columna saldoNombrada dependiendo si es historica o no
		if(isHistorico) {
			strQuery = strQuery.replaceAll("saldoNombrada", "saldoNombradaHistorico");
		}
		//Retorna la cadena
		return strQuery;
	}

	/**
	 * Construye una cadena con los criterios necesarios para filtrar la cuenta
	 * de la contraparte.
	 * 
	 * @param alias
	 *            Alias de la tabla de registro contable a utilizar.
	 * @param aliasCuentaContraparte
	 *            Alias o nombre de la propiedad de cuenta a utilizar. Los
	 *            valores válidos son Traspasante y Receptor
	 * @param criterio
	 *            Criterio para construir los parámetros.
	 * @param params
	 *            Lista de parámetros nuevos que los criterios agregan.
	 * @param tipos
	 *            Lista de tipos nuevos que los criterios agregan.
	 * @return Cadena con la parte de la consulta que involucra los criterios.
	 */
	private String construirCriteriosCuentaContraparte(String alias, String aliasCuentaContraparte, CriterioConsultaMovimientosEfectivoDTO criterio,
			ArrayList<Object> params, ArrayList<Object> tipos) {
		StringBuffer query = new StringBuffer();

		query.append(alias + ".operacion.idCuentaEfectivo" + aliasCuentaContraparte + " in  (");

		query.append("SELECT cc.idCuentaNombrada FROM " + CuentaNombrada.class.getName() + " cc WHERE ");
		query.append(" cc.tipoCuenta.naturalezaContable = ? ");
		params.add(criterio.getCuentaContraparte().getTipoNaturaleza().getId());
		tipos.add(new StringType());

		query.append(" AND cc.tipoCuenta.naturalezaProcesoLiquidacion = ? ");
		params.add(criterio.getCuentaContraparte().getTipoCuenta().getId());
		tipos.add(new StringType());

		if (!"-1".equals(criterio.getCuentaContraparte().getNumeroCuenta())) {
			query.append(" AND ( cc.institucion.tipoInstitucion.claveTipoInstitucion || " + "cc.institucion.folioInstitucion || " + "cc.cuenta " + ") = ? ");
			params.add(criterio.getCuentaContraparte().getNumeroCuenta());
			tipos.add(new StringType());

		}

		if (criterio.getCuentaContraparte().getTipoCustodia() != null && !criterio.getCuentaContraparte().getTipoCustodia().equals("-1")) {
			query.append(" AND cc.tipoCuenta.tipoCustodia = ? ");
			params.add(criterio.getCuentaContraparte().getTipoCustodia());
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

	/**
	 * @see com.indeval.portaldali.persistence.dao.estadocuenta.MovimientosEfectivoDAOV2#buscarDivisas()
	 */
	@SuppressWarnings("unchecked")
	public List<BigInteger> buscarDivisas() {
		final StringBuffer query = new StringBuffer();
		query.append(" SELECT div.idDivisa ");
		query.append(" FROM " + Divisa.class.getName() + " div ");
		query.append(" ORDER BY div.idDivisa ASC ");
		return (List<BigInteger>)getHibernateTemplate().find(query.toString());
	}

	public DateUtilsDao getDateUtilsDao() {
		return dateUtilsDao;
	}

	public void setDateUtilsDao(DateUtilsDao dateUtilsDao) {
		this.dateUtilsDao = dateUtilsDao;
	}

	

}