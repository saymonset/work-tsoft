/**
 * 2H Software SA de CV
 * 
 * Sistema de Consulta de Estado de Cuenta - Indeval
 *
 * Fecha de creación: Dec 24, 2007
 */
package com.indeval.portaldali.persistence.dao.estadocuenta.impl;

import java.math.BigDecimal;
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
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.indeval.portaldali.middleware.dto.OperacionPosicionDTO;
import com.indeval.portaldali.middleware.dto.RegistroContablePosicionNombradaDTO;
import com.indeval.portaldali.middleware.dto.util.DTOAssembler;
import com.indeval.portaldali.middleware.services.common.constants.TipoTenenciaConstants;
import com.indeval.portaldali.persistence.dao.estadocuenta.DetalleMovimientoPosicionDAO;
import com.indeval.portaldali.persistence.model.CuentaNombrada;
import com.indeval.portaldali.persistence.model.EstadoInstruccion;
import com.indeval.portaldali.persistence.model.OperacionNombrada;
import com.indeval.portaldali.persistence.model.OperacionNombradaHistorico;
import com.indeval.portaldali.persistence.model.RegContValNombrada;
import com.indeval.portaldali.persistence.model.RegContValNombradaHistorico;

/**
 * Implementación de la interface de acceso a la base de datos para las
 * consultas relacionadas al detalle de movimientos.
 * 
 * @author Sandra Flores Arrieta
 * @version 1.0
 * 
 */
public class DetalleMovimientoPosicionDAOImpl extends HibernateDaoSupport implements DetalleMovimientoPosicionDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.dao.DetalleMovimientoPosicionDAO#consultarDetalleMovimiento(long)
	 */
	@SuppressWarnings("unchecked")
	public RegContValNombrada consultarDetalleMovimiento(long idRegistroContable) {

		final StringBuffer query = new StringBuffer();
		final ArrayList<Object> params = new ArrayList<Object>();
		final ArrayList<Object> tipos = new ArrayList<Object>();

		query.append("FROM " + RegContValNombrada.class.getName() + " reg "
				+ " left join fetch reg.operacion " 
				+ " left join fetch reg.operacion.tipoOperacion " 
				+ " left join fetch reg.operacion.instruccion "
				+ " left join fetch reg.operacion.instruccion.tipoInstruccion " 
				+ " left join fetch reg.operacion.cuentaNombradaTraspasante "
				+ " left join fetch reg.operacion.cuentaNombradaTraspasante.institucion "
				+ " left join fetch reg.operacion.cuentaNombradaTraspasante.institucion.tipoInstitucion "
				+ " left join fetch reg.operacion.cuentaNombradaReceptor "
				+ " left join fetch reg.operacion.cuentaNombradaReceptor.institucion "
				+ " left join fetch reg.operacion.cuentaNombradaReceptor.institucion.tipoInstitucion "
				+ " left outer join fetch reg.operacion.institucionReceptor "
				+ " left outer join fetch reg.operacion.institucionReceptor.tipoInstitucion " 
				+ " left outer join fetch reg.operacion.institucionTraspasante "
				+ " left outer join fetch reg.operacion.institucionTraspasante.tipoInstitucion " 
				+ " left join fetch reg.posicion WHERE ");

		query.append(" reg.idRegistroContable = ? ");
		params.add(new BigInteger(String.valueOf(idRegistroContable)));
		tipos.add(new BigIntegerType());
		
		return (RegContValNombrada) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				
				List<RegContValNombrada> registros = session.createQuery(query.toString()).setParameters(params.toArray(), tipos.toArray(new Type[] {})).list();
				RegContValNombrada resultado = null;
				if (registros != null && registros.size() > 0) {
					resultado = registros.get(0);
				}

				return resultado;
				
			}
			
		});
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.dao.DetalleMovimientoPosicionDAO#consultarDetalleMovimiento(long)
	 */
	@SuppressWarnings("unchecked")
	public RegContValNombradaHistorico consultarDetalleMovimientoHistorico(long idRegistroContable) {

		final StringBuffer query = new StringBuffer();
		final ArrayList<Object> params = new ArrayList<Object>();
		final ArrayList<Object> tipos = new ArrayList<Object>();

		query.append("FROM " + RegContValNombradaHistorico.class.getName() + " reg "
				+ " left join fetch reg.operacion " 
				+ " left join fetch reg.operacion.tipoOperacion " 
				+ " left join fetch reg.operacion.instruccion "
				+ " left join fetch reg.operacion.instruccion.tipoInstruccion " 
				+ " left join fetch reg.operacion.cuentaNombradaTraspasante "
				+ " left join fetch reg.operacion.cuentaNombradaTraspasante.institucion "
				+ " left join fetch reg.operacion.cuentaNombradaTraspasante.institucion.tipoInstitucion "
				+ " left join fetch reg.operacion.cuentaNombradaReceptor "
				+ " left join fetch reg.operacion.cuentaNombradaReceptor.institucion "
				+ " left join fetch reg.operacion.cuentaNombradaReceptor.institucion.tipoInstitucion "
				+ " left outer join fetch reg.operacion.institucionReceptor "
				+ " left outer join fetch reg.operacion.institucionReceptor.tipoInstitucion " 
				+ " left outer join fetch reg.operacion.institucionTraspasante "
				+ " left outer join fetch reg.operacion.institucionTraspasante.tipoInstitucion " 
				+ " left join fetch reg.posicion WHERE ");

		query.append(" reg.idRegistroContableHistorico = ? ");
		params.add(new BigInteger(String.valueOf(idRegistroContable)));
		tipos.add(new BigIntegerType());
		query.append(" AND reg.fechaCreacion = reg.operacion.fechaCreacion ");
		
		return (RegContValNombradaHistorico) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				
				List<RegContValNombradaHistorico> registros = session.createQuery(query.toString()).setParameters(params.toArray(), tipos.toArray(new Type[] {})).list();
				RegContValNombradaHistorico resultado = null;
				if (registros != null && registros.size() > 0) {
					resultado = registros.get(0);
				}

				return resultado;
				
			}
			
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.dao.DetalleMovimientoPosicionDAO#buscarRegistroContablePosicionNombradaPorId(long)
	 */
	@SuppressWarnings("unchecked")
	public RegistroContablePosicionNombradaDTO buscarRegistroContablePosicionNombradaPorId(long idRegistroContable) {

		RegistroContablePosicionNombradaDTO resultado = null;
		StringBuffer queryString = new StringBuffer();

		queryString.append("FROM " + RegContValNombrada.class.getName() + " reg ");
		queryString.append("	join fetch reg.operacion ");
		queryString.append("	join fetch reg.operacion.tipoOperacion ");
		queryString.append("	join fetch reg.operacion.instruccion ");
		queryString.append("	join fetch reg.operacion.instruccion.tipoInstruccion ");
		queryString.append("	join fetch reg.operacion.institucionReceptor ");
		queryString.append("	join fetch reg.operacion.institucionReceptor.tipoInstitucion ");
		queryString.append("	join fetch reg.operacion.institucionTraspasante ");
		queryString.append("	join fetch reg.operacion.institucionTraspasante.tipoInstitucion ");
		queryString.append("	join fetch reg.operacion.institucionTraspasante ");
		queryString.append("	join fetch reg.operacion.cuentaNombradaTraspasante ");
		queryString.append("	join fetch reg.operacion.cuentaNombradaTraspasante.institucion ");
		queryString.append("	join fetch reg.operacion.cuentaNombradaTraspasante.institucion.tipoInstitucion ");
		queryString.append("	join fetch reg.operacion.cuentaNombradaReceptor ");
		queryString.append("	join fetch reg.operacion.cuentaNombradaReceptor.institucion ");
		queryString.append("	join fetch reg.operacion.cuentaNombradaReceptor.institucion.tipoInstitucion ");
		queryString.append("	join fetch reg.posicion ");
		queryString.append("	join fetch reg.posicion.boveda ");
		queryString.append("	join fetch reg.posicion.emision ");
		queryString.append("	join fetch reg.posicion.emision.emisora ");
		queryString.append("	join fetch reg.posicion.emision.instrumento ");
		queryString.append("	join fetch reg.posicion.emision.instrumento.mercado ");
		queryString.append("	join fetch reg.posicion.emision.divisa ");
		queryString.append("	join fetch reg.posicion.cuentaNombrada ");
		queryString.append("	join fetch reg.posicion.cuentaNombrada.institucion ");
		queryString.append("	join fetch reg.posicion.cuentaNombrada.institucion.tipoInstitucion ");
		queryString.append("	join fetch reg.posicion.cuentaNombrada.tipoCuenta WHERE reg.idRegistroContable = ?");

		List<RegContValNombrada> registros = getHibernateTemplate().find(queryString.toString(), new BigInteger(String.valueOf(idRegistroContable)));

		if (registros != null && registros.size() > 0) {
			resultado = DTOAssembler.crearRegistroContablePosicionNombradaConDatosOperacion(registros.get(0));
		}

		return resultado;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.dao.DetalleMovimientoPosicionDAO#buscarCuentasYTotalPagadoDeInstruccion(java.util.List,
	 *      com.indeval.estadocuenta.core.application.dto.RegistroContablePosicionNombradaDTO)
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> buscarCuentasYTotalPagadoDeInstruccion(List<Long> idsCuentas, RegistroContablePosicionNombradaDTO registroContable) {

		final StringBuffer queryString = new StringBuffer();
		final ArrayList<Object> params = new ArrayList<Object>();
		final ArrayList<Object> tipos = new ArrayList<Object>();

		queryString.append("SELECT cuenta, (");
		queryString.append("	SELECT SUM(opn.numeroTitulos)||'_'|| SUM(opn.monto) FROM " + OperacionNombrada.class.getName() + " opn ");
		queryString.append("	WHERE ( ");
		queryString.append("		opn.instruccion.idInstruccionLiquidacion = ? ");
		queryString.append("		OR opn.instruccion.idInstruccionLiquidacion = ? ");
		queryString.append("		OR opn.instruccion.idInstruccionLiquidacionOrigen = ? ");
		queryString.append("		OR opn.instruccion.idInstruccionLiquidacionOrigen = ? ");
		queryString.append("		) ");
		queryString.append("	AND opn.idPosicionNombrada = ? ");
		queryString.append("	AND opn.instruccion.estadoInstruccion = ? ");
		queryString.append("	AND opn.idCuentaNombradaTraspasante = ? ");
		queryString.append("	AND opn.idCuentaNombradaReceptor = cuenta.idCuentaNombrada ");
		queryString.append("	) ");
		queryString.append("FROM " + CuentaNombrada.class.getName() + " cuenta ");
		queryString.append("WHERE cuenta.idCuentaNombrada in (");

		params.add(new BigInteger(String.valueOf(registroContable.getOperacion().getInstruccionLiquidacion().getIdInstruccionLiquidacion())));
		tipos.add(new BigIntegerType());
		params.add(new BigInteger(String.valueOf(registroContable.getOperacion().getInstruccionLiquidacion().getIdInstruccionLiquidacionOrigen())));
		tipos.add(new BigIntegerType());
		params.add(new BigInteger(String.valueOf(registroContable.getOperacion().getInstruccionLiquidacion().getIdInstruccionLiquidacion())));
		tipos.add(new BigIntegerType());
		params.add(new BigInteger(String.valueOf(registroContable.getOperacion().getInstruccionLiquidacion().getIdInstruccionLiquidacionOrigen())));
		tipos.add(new BigIntegerType());
		params.add(new BigInteger(String.valueOf(registroContable.getPosicion().getIdPosicion())));
		tipos.add(new BigIntegerType());
		params.add(EstadoInstruccion.LIQUIDADA_VALUE);
		tipos.add(new IntegerType());
		params.add(new BigInteger(String.valueOf(registroContable.getOperacion().getCuentaTraspasante().getIdCuenta())));
		tipos.add(new BigIntegerType());

		for (Iterator<Long> it = idsCuentas.iterator(); it.hasNext();) {
			Long idCuenta = it.next();
			queryString.append("?");
			params.add(new BigInteger(String.valueOf(idCuenta)));
			tipos.add(new BigIntegerType());
			if (it.hasNext()) {
				queryString.append(",");
			}
		}

		queryString.append(") ORDER BY cuenta.idCuentaNombrada ");
		
		return (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				
				Query query = session.createQuery(queryString.toString()).setParameters(params.toArray(), tipos.toArray(new Type[] {}));
				ArrayList<Object[]> registros = new ArrayList<Object[]>();
				Iterator<Object[]> it = query.list().iterator();
				while (it.hasNext()) {
					Object[] registro = new Object[4];
					Object[] elemento = it.next();
					CuentaNombrada cuenta = (CuentaNombrada) elemento[0];
					String descripcionContraparte = "";
					registro[0] = DTOAssembler.crearCuentaDTO((CuentaNombrada) elemento[0]);
					String [] sumatorias = null;
					
					if(StringUtils.isNotEmpty((String)elemento[1])) {
						sumatorias = ((String)elemento[1]).split("_",-1);
						if(sumatorias.length != 2) {
							registro[1] = new BigInteger("0");
							registro[3] = new BigDecimal("0");
						} else {
							registro[1] = new BigInteger(StringUtils.defaultIfEmpty(sumatorias[0], "0"));
							registro[3] = new BigDecimal(StringUtils.defaultIfEmpty(sumatorias[1], "0"));
						}
					} else {
						registro[1] = new BigInteger("0");
						registro[3] = new BigDecimal("0");
					}

					descripcionContraparte = cuenta.getInstitucion().getNombreCorto();
					descripcionContraparte += " (" + cuenta.getInstitucion().getTipoInstitucion().getClaveTipoInstitucion() + "-"
							+ cuenta.getInstitucion().getFolioInstitucion() + "-" + cuenta.getCuenta() + ")";

					registro[2] = descripcionContraparte;
					
					registros.add(registro);
				}

				return registros;
				
			}
			
		});

		
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.dao.DetalleMovimientoPosicionDAO#buscarCuentasYTotalPagadoDeInstruccionHistorico(java.util.List,
	 *      com.indeval.estadocuenta.core.application.dto.RegistroContablePosicionNombradaDTO)
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> buscarCuentasYTotalPagadoDeInstruccionHistorico(List<Long> idsCuentas, RegistroContablePosicionNombradaDTO registroContable) {

		final StringBuffer queryString = new StringBuffer();
		final ArrayList<Object> params = new ArrayList<Object>();
		final ArrayList<Object> tipos = new ArrayList<Object>();

		queryString.append("SELECT cuenta, (");
		queryString.append("	SELECT SUM(opn.numeroTitulos)||'_'|| SUM(opn.monto) FROM " + OperacionNombradaHistorico.class.getName() + " opn ");
		queryString.append("	WHERE ( ");
		queryString.append("		opn.instruccion.idInstruccionLiquidacion = ? ");
		queryString.append("		OR opn.instruccion.idInstruccionLiquidacion = ? ");
		queryString.append("		OR opn.instruccion.idInstruccionLiquidacionOrigen = ? ");
		queryString.append("		OR opn.instruccion.idInstruccionLiquidacionOrigen = ? ");
		queryString.append("		) ");
		queryString.append("	AND opn.idPosicionNombrada = ? ");
		queryString.append("	AND opn.instruccion.estadoInstruccion = ? ");
		queryString.append("	AND opn.idCuentaNombradaTraspasante = ? ");
		queryString.append("	AND opn.idCuentaNombradaReceptor = cuenta.idCuentaNombrada ");
		queryString.append("	) ");
		queryString.append("FROM " + CuentaNombrada.class.getName() + " cuenta ");
		queryString.append("WHERE cuenta.idCuentaNombrada in (");

		params.add(new BigInteger(String.valueOf(registroContable.getOperacion().getInstruccionLiquidacion().getIdInstruccionLiquidacion())));
		tipos.add(new BigIntegerType());
		params.add(new BigInteger(String.valueOf(registroContable.getOperacion().getInstruccionLiquidacion().getIdInstruccionLiquidacionOrigen())));
		tipos.add(new BigIntegerType());
		params.add(new BigInteger(String.valueOf(registroContable.getOperacion().getInstruccionLiquidacion().getIdInstruccionLiquidacion())));
		tipos.add(new BigIntegerType());
		params.add(new BigInteger(String.valueOf(registroContable.getOperacion().getInstruccionLiquidacion().getIdInstruccionLiquidacionOrigen())));
		tipos.add(new BigIntegerType());
		params.add(new BigInteger(String.valueOf(registroContable.getPosicion().getIdPosicion())));
		tipos.add(new BigIntegerType());
		params.add(EstadoInstruccion.LIQUIDADA_VALUE);
		tipos.add(new IntegerType());
		params.add(new BigInteger(String.valueOf(registroContable.getOperacion().getCuentaTraspasante().getIdCuenta())));
		tipos.add(new BigIntegerType());

		for (Iterator<Long> it = idsCuentas.iterator(); it.hasNext();) {
			Long idCuenta = it.next();
			queryString.append("?");
			params.add(new BigInteger(String.valueOf(idCuenta)));
			tipos.add(new BigIntegerType());
			if (it.hasNext()) {
				queryString.append(",");
			}
		}

		queryString.append(") ORDER BY cuenta.idCuentaNombrada ");
		
		return (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				
				Query query = session.createQuery(queryString.toString()).setParameters(params.toArray(), tipos.toArray(new Type[] {}));
				ArrayList<Object[]> registros = new ArrayList<Object[]>();
				Iterator<Object[]> it = query.list().iterator();
				while (it.hasNext()) {
					Object[] registro = new Object[4];
					Object[] elemento = it.next();
					CuentaNombrada cuenta = (CuentaNombrada) elemento[0];
					String descripcionContraparte = "";
					registro[0] = DTOAssembler.crearCuentaDTO((CuentaNombrada) elemento[0]);
					String [] sumatorias = null;
					
					if(StringUtils.isNotEmpty((String)elemento[1])) {
						sumatorias = ((String)elemento[1]).split("_",-1);
						if(sumatorias.length != 2) {
							registro[1] = new BigInteger("0");
							registro[3] = new BigDecimal("0");
						} else {
							registro[1] = new BigInteger(StringUtils.defaultIfEmpty(sumatorias[0], "0"));
							registro[3] = new BigDecimal(StringUtils.defaultIfEmpty(sumatorias[1], "0"));
						}
					} else {
						registro[1] = new BigInteger("0");
						registro[3] = new BigDecimal("0");
					}

					descripcionContraparte = cuenta.getInstitucion().getNombreCorto();
					descripcionContraparte += " (" + cuenta.getInstitucion().getTipoInstitucion().getClaveTipoInstitucion() + "-"
							+ cuenta.getInstitucion().getFolioInstitucion() + "-" + cuenta.getCuenta() + ")";

					registro[2] = descripcionContraparte;
					
					registros.add(registro);
				}

				return registros;
				
			}
			
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.dao.DetalleMovimientoPosicionDAO#buscarContrapartesYEstadosDeInstruccion(com.indeval.estadocuenta.core.application.dto.RegistroContablePosicionNombradaDTO)
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> buscarContrapartesDeInstruccion(RegistroContablePosicionNombradaDTO registroContable) {

		final StringBuffer queryString = new StringBuffer();
		final ArrayList<Object> params = new ArrayList<Object>();
		final ArrayList<Object> tipos = new ArrayList<Object>();

		queryString.append("SELECT  opn.idCuentaNombradaReceptor,max(opn.instruccion.fechaLiquidacion),sum(opn.numeroTitulos), sum(opn.monto), opn.bovedaEfectivo.descripcion, opn.divisa.descripcion  ");
		queryString.append("FROM " + OperacionNombrada.class.getName() + " opn ");
		queryString.append("WHERE ( ");
		queryString.append("		opn.instruccion.idInstruccionLiquidacion = ? ");
		queryString.append("		OR opn.instruccion.idInstruccionLiquidacion = ? ");
		queryString.append("		OR opn.instruccion.idInstruccionLiquidacionOrigen = ? ");
		queryString.append("		OR opn.instruccion.idInstruccionLiquidacionOrigen = ? ");
		queryString.append("	) ");
		queryString.append("	AND opn.idPosicionNombrada = ? ");
		queryString.append("	AND opn.idCuentaNombradaTraspasante = ? ");
		queryString.append("GROUP BY opn.idCuentaNombradaReceptor, opn.bovedaEfectivo.descripcion, opn.divisa.descripcion ");
		queryString.append("ORDER BY opn.idCuentaNombradaReceptor ");

		params.add(new BigInteger(String.valueOf(registroContable.getOperacion().getInstruccionLiquidacion().getIdInstruccionLiquidacion())));
		tipos.add(new BigIntegerType());
		params.add(new BigInteger(String.valueOf(registroContable.getOperacion().getInstruccionLiquidacion().getIdInstruccionLiquidacionOrigen())));
		tipos.add(new BigIntegerType());
		params.add(new BigInteger(String.valueOf(registroContable.getOperacion().getInstruccionLiquidacion().getIdInstruccionLiquidacion())));
		tipos.add(new BigIntegerType());
		params.add(new BigInteger(String.valueOf(registroContable.getOperacion().getInstruccionLiquidacion().getIdInstruccionLiquidacionOrigen())));
		tipos.add(new BigIntegerType());
		params.add(new BigInteger(String.valueOf(registroContable.getPosicion().getIdPosicion())));
		tipos.add(new BigIntegerType());
		params.add(new BigInteger(String.valueOf(registroContable.getOperacion().getCuentaTraspasante().getIdCuenta())));
		tipos.add(new BigIntegerType());
		
		return (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				
				return session.createQuery(queryString.toString()).setParameters(params.toArray(), tipos.toArray(new Type[] {})).list();
			}
			
		});

		
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.dao.DetalleMovimientoPosicionDAO#buscarContrapartesYEstadosDeInstruccionHistorico(com.indeval.estadocuenta.core.application.dto.RegistroContablePosicionNombradaDTO)
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> buscarContrapartesDeInstruccionHistorico(RegistroContablePosicionNombradaDTO registroContable) {

		final StringBuffer queryString = new StringBuffer();
		final ArrayList<Object> params = new ArrayList<Object>();
		final ArrayList<Object> tipos = new ArrayList<Object>();

		queryString.append("SELECT  opn.idCuentaNombradaReceptor,max(opn.instruccion.fechaLiquidacion),sum(opn.numeroTitulos), sum(opn.monto), opn.bovedaEfectivo.descripcion, opn.divisa.descripcion  ");
		queryString.append("FROM " + OperacionNombradaHistorico.class.getName() + " opn ");
		queryString.append("WHERE ( ");
		queryString.append("		opn.instruccion.idInstruccionLiquidacion = ? ");
		queryString.append("		OR opn.instruccion.idInstruccionLiquidacion = ? ");
		queryString.append("		OR opn.instruccion.idInstruccionLiquidacionOrigen = ? ");
		queryString.append("		OR opn.instruccion.idInstruccionLiquidacionOrigen = ? ");
		queryString.append("	) ");
		queryString.append("	AND opn.idPosicionNombrada = ? ");
		queryString.append("	AND opn.idCuentaNombradaTraspasante = ? ");
		queryString.append("GROUP BY opn.idCuentaNombradaReceptor, opn.bovedaEfectivo.descripcion, opn.divisa.descripcion ");
		queryString.append("ORDER BY opn.idCuentaNombradaReceptor ");

		params.add(new BigInteger(String.valueOf(registroContable.getOperacion().getInstruccionLiquidacion().getIdInstruccionLiquidacion())));
		tipos.add(new BigIntegerType());
		params.add(new BigInteger(String.valueOf(registroContable.getOperacion().getInstruccionLiquidacion().getIdInstruccionLiquidacionOrigen())));
		tipos.add(new BigIntegerType());
		params.add(new BigInteger(String.valueOf(registroContable.getOperacion().getInstruccionLiquidacion().getIdInstruccionLiquidacion())));
		tipos.add(new BigIntegerType());
		params.add(new BigInteger(String.valueOf(registroContable.getOperacion().getInstruccionLiquidacion().getIdInstruccionLiquidacionOrigen())));
		tipos.add(new BigIntegerType());
		params.add(new BigInteger(String.valueOf(registroContable.getPosicion().getIdPosicion())));
		tipos.add(new BigIntegerType());
		params.add(new BigInteger(String.valueOf(registroContable.getOperacion().getCuentaTraspasante().getIdCuenta())));
		tipos.add(new BigIntegerType());
		
		return (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				
				return session.createQuery(queryString.toString()).setParameters(params.toArray(), tipos.toArray(new Type[] {})).list();
			}
			
		});
	}

	/*
	 * (non-Javadoc)
	 * @see com.indeval.estadocuenta.core.application.dao.DetalleMovimientoPosicionDAO#buscarDetalleDePrestamo(long)
	 */
	@SuppressWarnings("unchecked")
	public RegistroContablePosicionNombradaDTO buscarDetalleDePrestamo(long idInstruccion) {

		final StringBuffer queryString = new StringBuffer();
		final ArrayList<Object> params = new ArrayList<Object>();
		final ArrayList<Object> tipos = new ArrayList<Object>();
		
		queryString.append("FROM " + RegContValNombrada.class.getName() + " reg, ");
		queryString.append(OperacionNombrada.class.getName() + " opn ");
		queryString.append("	left outer join fetch reg.operacion ");
		queryString.append("	left outer join fetch reg.operacion.tipoOperacion ");
		queryString.append("	left outer join fetch reg.operacion.instruccion "); 
		queryString.append("	left outer join fetch reg.operacion.instruccion.tipoInstruccion "); 
		queryString.append("	left outer join fetch opn.cupon ");
		queryString.append("	left outer join fetch reg.posicion ");
		queryString.append("WHERE reg.operacion.instruccion.idInstruccionLiquidacion = ? ");
		queryString.append("	AND reg.operacion.cuentaNombradaReceptor.tipoCuenta.claveSubgrupo <> ? ");
		queryString.append("	AND reg.posicion.cuentaNombrada.tipoCuenta.claveSubgrupo <> ? ");
		queryString.append("	AND opn.idOperacion = reg.operacion.idOperacion ");
		
		params.add(new BigInteger(String.valueOf(idInstruccion)));
		params.add(TipoTenenciaConstants.CLAVE_SUBGRUPO_GARANTIAS);
		params.add(TipoTenenciaConstants.CLAVE_SUBGRUPO_GARANTIAS);
		
		tipos.add(new BigIntegerType());
		tipos.add(new StringType());
		tipos.add(new StringType());
		
		return (RegistroContablePosicionNombradaDTO) getHibernateTemplate().execute(new HibernateCallback() { 
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				
				RegistroContablePosicionNombradaDTO prestamo = null;
				
				List<Object []> registros = session.createQuery(queryString.toString()).setParameters(params.toArray(), tipos.toArray(new Type[] {}))
				.list();

				if (registros != null && registros.size() > 0) {
					Object []registro = registros.get(0);
			
					prestamo = DTOAssembler.crearRegistroContablePosicionNombradaConDatosOperacion((RegContValNombrada) registro[0]);
					OperacionNombrada posicionOperacion = (OperacionNombrada)registro[1];
					prestamo.getPosicion().getEmision().setCupon(posicionOperacion.getCupon().getClaveCupon());
					prestamo.setPrecio(posicionOperacion.getPrecio().doubleValue());
				}

				return prestamo;
			}
			
		});
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.estadocuenta.core.application.dao.DetalleMovimientoPosicionDAO#buscarDetalleDePrestamoHistorico(long)
	 */
	@SuppressWarnings("unchecked")
	public RegistroContablePosicionNombradaDTO buscarDetalleDePrestamoHistorico(long idInstruccion) {

		final StringBuffer queryString = new StringBuffer();
		final ArrayList<Object> params = new ArrayList<Object>();
		final ArrayList<Object> tipos = new ArrayList<Object>();
		
		queryString.append("FROM " + RegContValNombradaHistorico.class.getName() + " reg, ");
		queryString.append(OperacionNombradaHistorico.class.getName() + " opn ");
		queryString.append("	left outer join fetch reg.operacion ");
		queryString.append("	left outer join fetch reg.operacion.tipoOperacion ");
		queryString.append("	left outer join fetch reg.operacion.instruccion "); 
		queryString.append("	left outer join fetch reg.operacion.instruccion.tipoInstruccion "); 
		queryString.append("	left outer join fetch opn.cupon ");
		queryString.append("	left outer join fetch reg.posicion ");
		queryString.append("WHERE reg.operacion.instruccion.idInstruccionLiquidacion = ? ");
		queryString.append("	AND reg.operacion.cuentaNombradaReceptor.tipoCuenta.claveSubgrupo <> ? ");
		queryString.append("	AND reg.posicion.cuentaNombrada.tipoCuenta.claveSubgrupo <> ? ");
		queryString.append("	AND opn.idOperacion = reg.operacion.idOperacion ");
		queryString.append(" AND reg.fechaCreacion = reg.operacion.fechaCreacion ");
		
		params.add(new BigInteger(String.valueOf(idInstruccion)));
		params.add(TipoTenenciaConstants.CLAVE_SUBGRUPO_GARANTIAS);
		params.add(TipoTenenciaConstants.CLAVE_SUBGRUPO_GARANTIAS);
		
		tipos.add(new BigIntegerType());
		tipos.add(new StringType());
		tipos.add(new StringType());
		
		return (RegistroContablePosicionNombradaDTO) getHibernateTemplate().execute(new HibernateCallback() { 
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				
				RegistroContablePosicionNombradaDTO prestamo = null;
				
				List<Object []> registros = session.createQuery(queryString.toString()).setParameters(params.toArray(), tipos.toArray(new Type[] {}))
				.list();

				if (registros != null && registros.size() > 0) {
					Object []registro = registros.get(0);
			
					prestamo = DTOAssembler.crearRegistroContablePosicionNombradaConDatosOperacion((RegContValNombradaHistorico) registro[0]);
					OperacionNombradaHistorico posicionOperacion = (OperacionNombradaHistorico)registro[1];
					prestamo.getPosicion().getEmision().setCupon(posicionOperacion.getCupon().getClaveCupon());
					prestamo.setPrecio(posicionOperacion.getPrecio().doubleValue());
				}

				return prestamo;
			}
			
		});
	}

	/*
	 * (non-Javadoc)
	 * @see com.indeval.estadocuenta.core.application.dao.DetalleMovimientoPosicionDAO#buscarGarantiasDePrestamos(long)
	 */
	@SuppressWarnings("unchecked")
	public List<RegistroContablePosicionNombradaDTO> buscarGarantiasDePrestamos(long idInstruccion) {

		final StringBuffer queryString = new StringBuffer();
		final ArrayList<Object> params = new ArrayList<Object>();
		final ArrayList<Object> tipos = new ArrayList<Object>();
		final List<RegistroContablePosicionNombradaDTO> garantias = new ArrayList<RegistroContablePosicionNombradaDTO>();

		queryString.append("FROM " + RegContValNombrada.class.getName() + " reg , ");
		queryString.append(OperacionNombrada.class.getName() + " opn ");
		queryString.append("	left outer join fetch reg.operacion ");
		queryString.append("	left outer join fetch reg.operacion.tipoOperacion ");
		queryString.append("	left outer join fetch reg.operacion.instruccion "); 
		queryString.append("	left outer join fetch reg.operacion.instruccion.tipoInstruccion "); 
		queryString.append("	left outer join fetch opn.cupon ");
		queryString.append("	left outer join fetch reg.posicion ");
		queryString.append("WHERE reg.operacion.instruccion.idInstruccionLiquidacion = ? ");
		queryString.append("	AND reg.operacion.cuentaNombradaReceptor.tipoCuenta.claveSubgrupo = ? ");
		queryString.append("	AND reg.posicion.cuentaNombrada.tipoCuenta.claveSubgrupo = ? ");
		queryString.append("	AND opn.idOperacion = reg.operacion.idOperacion ");
		

		params.add(new BigInteger(String.valueOf(idInstruccion)));
		params.add(TipoTenenciaConstants.CLAVE_SUBGRUPO_GARANTIAS);
		params.add(TipoTenenciaConstants.CLAVE_SUBGRUPO_GARANTIAS);
		
		tipos.add(new BigIntegerType());
		tipos.add(new StringType());
		tipos.add(new StringType());
		
		return (List<RegistroContablePosicionNombradaDTO>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				
				Iterator<Object []> registros = session.createQuery(queryString.toString()).setParameters(params.toArray(), tipos.toArray(new Type[] {}))
				.list().iterator();

				while (registros.hasNext()) {
					Object []registro = registros.next();
					RegContValNombrada regContValNombrada = (RegContValNombrada) registro[0];
					OperacionNombrada operacionNombrada = (OperacionNombrada) registro[1];
					RegistroContablePosicionNombradaDTO registroContable = DTOAssembler.crearRegistroContablePosicionNombradaConDatosOperacion(regContValNombrada);
					registroContable.getPosicion().getEmision().setCupon(operacionNombrada.getCupon().getClaveCupon());
					registroContable.setPrecio(operacionNombrada.getPrecio().doubleValue());
					garantias.add(registroContable);
				}

				return garantias;
				
			}
		});
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.estadocuenta.core.application.dao.DetalleMovimientoPosicionDAO#buscarGarantiasDePrestamosHistorico(long)
	 */
	@SuppressWarnings("unchecked")
	public List<RegistroContablePosicionNombradaDTO> buscarGarantiasDePrestamosHistorico(long idInstruccion) {

		final StringBuffer queryString = new StringBuffer();
		final ArrayList<Object> params = new ArrayList<Object>();
		final ArrayList<Object> tipos = new ArrayList<Object>();
		final List<RegistroContablePosicionNombradaDTO> garantias = new ArrayList<RegistroContablePosicionNombradaDTO>();

		queryString.append("FROM " + RegContValNombradaHistorico.class.getName() + " reg , ");
		queryString.append(OperacionNombradaHistorico.class.getName() + " opn ");
		queryString.append("	left outer join fetch reg.operacion ");
		queryString.append("	left outer join fetch reg.operacion.tipoOperacion ");
		queryString.append("	left outer join fetch reg.operacion.instruccion "); 
		queryString.append("	left outer join fetch reg.operacion.instruccion.tipoInstruccion "); 
		queryString.append("	left outer join fetch opn.cupon ");
		queryString.append("	left outer join fetch reg.posicion ");
		queryString.append("WHERE reg.operacion.instruccion.idInstruccionLiquidacion = ? ");
		queryString.append("	AND reg.operacion.cuentaNombradaReceptor.tipoCuenta.claveSubgrupo = ? ");
		queryString.append("	AND reg.posicion.cuentaNombrada.tipoCuenta.claveSubgrupo = ? ");
		queryString.append("	AND opn.idOperacion = reg.operacion.idOperacion ");
		queryString.append(" 	AND reg.fechaCreacion = reg.operacion.fechaCreacion ");

		params.add(new BigInteger(String.valueOf(idInstruccion)));
		params.add(TipoTenenciaConstants.CLAVE_SUBGRUPO_GARANTIAS);
		params.add(TipoTenenciaConstants.CLAVE_SUBGRUPO_GARANTIAS);
		
		tipos.add(new BigIntegerType());
		tipos.add(new StringType());
		tipos.add(new StringType());
		
		return (List<RegistroContablePosicionNombradaDTO>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				
				Iterator<Object []> registros = session.createQuery(queryString.toString()).setParameters(params.toArray(), tipos.toArray(new Type[] {}))
				.list().iterator();

				while (registros.hasNext()) {
					Object []registro = registros.next();
					RegContValNombradaHistorico regContValNombrada = (RegContValNombradaHistorico) registro[0];
					OperacionNombradaHistorico operacionNombrada = (OperacionNombradaHistorico) registro[1];
					RegistroContablePosicionNombradaDTO registroContable = DTOAssembler.crearRegistroContablePosicionNombradaConDatosOperacion(regContValNombrada);
					registroContable.getPosicion().getEmision().setCupon(operacionNombrada.getCupon().getClaveCupon());
					registroContable.setPrecio(operacionNombrada.getPrecio().doubleValue());
					garantias.add(registroContable);
				}

				return garantias;
				
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.dao.DetalleMovimientoPosicionDAO#buscarOperacionesDeInstruccion(com.indeval.estadocuenta.core.application.dto.RegistroContablePosicionNombradaDTO)
	 */
	@SuppressWarnings("unchecked")
	public List<OperacionPosicionDTO> buscarOperacionesDeInstruccion(RegistroContablePosicionNombradaDTO registroContable) {

		final StringBuffer queryString = new StringBuffer();
		final ArrayList<Object> params = new ArrayList<Object>();
		final ArrayList<Object> tipos = new ArrayList<Object>();

		queryString.append("FROM " + OperacionNombrada.class.getName() + " opn ");
		queryString.append("	join fetch opn.tipoOperacion ");
		queryString.append("	join fetch opn.instruccion ");
		queryString.append("	join fetch opn.instruccion.tipoInstruccion ");
		queryString.append("	join fetch opn.institucionReceptor ");
		queryString.append("	join fetch opn.institucionReceptor.tipoInstitucion ");
		queryString.append("	join fetch opn.institucionTraspasante ");
		queryString.append("	join fetch opn.institucionTraspasante.tipoInstitucion ");
		queryString.append("	join fetch opn.institucionTraspasante ");
		queryString.append("	join fetch opn.cuentaNombradaTraspasante ");
		queryString.append("	join fetch opn.cuentaNombradaTraspasante.institucion ");
		queryString.append("	join fetch opn.cuentaNombradaTraspasante.institucion.tipoInstitucion ");
		queryString.append("	join fetch opn.cuentaNombradaReceptor ");
		queryString.append("	join fetch opn.cuentaNombradaReceptor.institucion ");
		queryString.append("	join fetch opn.cuentaNombradaReceptor.institucion.tipoInstitucion ");
		queryString.append("	join fetch opn.posicionNombrada ");
		queryString.append("	join fetch opn.posicionNombrada.boveda ");
		queryString.append("	join fetch opn.posicionNombrada.emision ");
		queryString.append("	join fetch opn.posicionNombrada.emision.emisora ");
		queryString.append("	join fetch opn.posicionNombrada.emision.instrumento ");
		queryString.append("	join fetch opn.posicionNombrada.emision.instrumento.mercado ");
		queryString.append("	join fetch opn.posicionNombrada.emision.divisa ");
		queryString.append("	join fetch opn.posicionNombrada.cuentaNombrada ");
		queryString.append("	join fetch opn.posicionNombrada.cuentaNombrada.institucion ");
		queryString.append("	join fetch opn.posicionNombrada.cuentaNombrada.institucion.tipoInstitucion ");
		queryString.append("	join fetch opn.posicionNombrada.cuentaNombrada.tipoCuenta ");
		queryString.append("WHERE ( ");
		queryString.append("		opn.instruccion.idInstruccionLiquidacion = ? ");
		queryString.append("		OR opn.instruccion.idInstruccionLiquidacion = ? ");
		queryString.append("		OR opn.instruccion.idInstruccionLiquidacionOrigen = ? ");
		queryString.append("		OR opn.instruccion.idInstruccionLiquidacionOrigen = ? ");
		queryString.append("	) ");
		queryString.append("	AND opn.idCuentaNombradaTraspasante = ? ");
		queryString.append("	AND opn.idCuentaNombradaReceptor = ? ");
		queryString.append("ORDER BY opn.instruccion.fechaLiquidacion ASC");

		params.add(new BigInteger(String.valueOf(registroContable.getOperacion().getInstruccionLiquidacion().getIdInstruccionLiquidacion())));
		tipos.add(new BigIntegerType());
		params.add(new BigInteger(String.valueOf(registroContable.getOperacion().getInstruccionLiquidacion().getIdInstruccionLiquidacionOrigen())));
		tipos.add(new BigIntegerType());
		params.add(new BigInteger(String.valueOf(registroContable.getOperacion().getInstruccionLiquidacion().getIdInstruccionLiquidacion())));
		tipos.add(new BigIntegerType());
		params.add(new BigInteger(String.valueOf(registroContable.getOperacion().getInstruccionLiquidacion().getIdInstruccionLiquidacionOrigen())));
		tipos.add(new BigIntegerType());
		params.add(new BigInteger(String.valueOf(registroContable.getOperacion().getCuentaTraspasante().getIdCuenta())));
		tipos.add(new BigIntegerType());
		params.add(new BigInteger(String.valueOf(registroContable.getOperacion().getCuentaReceptora().getIdCuenta())));
		tipos.add(new BigIntegerType());
		
		return (List<OperacionPosicionDTO>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				
				Query query = session.createQuery(queryString.toString()).setParameters(params.toArray(), tipos.toArray(new Type[] {}));
				ArrayList<OperacionPosicionDTO> registros = new ArrayList<OperacionPosicionDTO>();
				Iterator<OperacionNombrada> it = query.list().iterator();
				while (it.hasNext()) {
					registros.add(DTOAssembler.crearOperacion(it.next()));
				}

				return registros;
				
			}
		});
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.dao.DetalleMovimientoPosicionDAO#buscarOperacionesDeInstruccionHistorico(com.indeval.estadocuenta.core.application.dto.RegistroContablePosicionNombradaDTO)
	 */
	@SuppressWarnings("unchecked")
	public List<OperacionPosicionDTO> buscarOperacionesDeInstruccionHistorico(RegistroContablePosicionNombradaDTO registroContable) {

		final StringBuffer queryString = new StringBuffer();
		final ArrayList<Object> params = new ArrayList<Object>();
		final ArrayList<Object> tipos = new ArrayList<Object>();

		queryString.append("FROM " + OperacionNombradaHistorico.class.getName() + " opn ");
		queryString.append("	join fetch opn.tipoOperacion ");
		queryString.append("	join fetch opn.instruccion ");
		queryString.append("	join fetch opn.instruccion.tipoInstruccion ");
		queryString.append("	join fetch opn.institucionReceptor ");
		queryString.append("	join fetch opn.institucionReceptor.tipoInstitucion ");
		queryString.append("	join fetch opn.institucionTraspasante ");
		queryString.append("	join fetch opn.institucionTraspasante.tipoInstitucion ");
		queryString.append("	join fetch opn.institucionTraspasante ");
		queryString.append("	join fetch opn.cuentaNombradaTraspasante ");
		queryString.append("	join fetch opn.cuentaNombradaTraspasante.institucion ");
		queryString.append("	join fetch opn.cuentaNombradaTraspasante.institucion.tipoInstitucion ");
		queryString.append("	join fetch opn.cuentaNombradaReceptor ");
		queryString.append("	join fetch opn.cuentaNombradaReceptor.institucion ");
		queryString.append("	join fetch opn.cuentaNombradaReceptor.institucion.tipoInstitucion ");
		queryString.append("	join fetch opn.posicionNombrada ");
		queryString.append("	join fetch opn.posicionNombrada.boveda ");
		queryString.append("	join fetch opn.posicionNombrada.emision ");
		queryString.append("	join fetch opn.posicionNombrada.emision.emisora ");
		queryString.append("	join fetch opn.posicionNombrada.emision.instrumento ");
		queryString.append("	join fetch opn.posicionNombrada.emision.instrumento.mercado ");
		queryString.append("	join fetch opn.posicionNombrada.emision.divisa ");
		queryString.append("	join fetch opn.posicionNombrada.cuentaNombrada ");
		queryString.append("	join fetch opn.posicionNombrada.cuentaNombrada.institucion ");
		queryString.append("	join fetch opn.posicionNombrada.cuentaNombrada.institucion.tipoInstitucion ");
		queryString.append("	join fetch opn.posicionNombrada.cuentaNombrada.tipoCuenta ");
		queryString.append("WHERE ( ");
		queryString.append("		opn.instruccion.idInstruccionLiquidacion = ? ");
		queryString.append("		OR opn.instruccion.idInstruccionLiquidacion = ? ");
		queryString.append("		OR opn.instruccion.idInstruccionLiquidacionOrigen = ? ");
		queryString.append("		OR opn.instruccion.idInstruccionLiquidacionOrigen = ? ");
		queryString.append("	) ");
		queryString.append("	AND opn.idCuentaNombradaTraspasante = ? ");
		queryString.append("	AND opn.idCuentaNombradaReceptor = ? ");
		queryString.append("ORDER BY opn.instruccion.fechaLiquidacion ASC");

		params.add(new BigInteger(String.valueOf(registroContable.getOperacion().getInstruccionLiquidacion().getIdInstruccionLiquidacion())));
		tipos.add(new BigIntegerType());
		params.add(new BigInteger(String.valueOf(registroContable.getOperacion().getInstruccionLiquidacion().getIdInstruccionLiquidacionOrigen())));
		tipos.add(new BigIntegerType());
		params.add(new BigInteger(String.valueOf(registroContable.getOperacion().getInstruccionLiquidacion().getIdInstruccionLiquidacion())));
		tipos.add(new BigIntegerType());
		params.add(new BigInteger(String.valueOf(registroContable.getOperacion().getInstruccionLiquidacion().getIdInstruccionLiquidacionOrigen())));
		tipos.add(new BigIntegerType());
		params.add(new BigInteger(String.valueOf(registroContable.getOperacion().getCuentaTraspasante().getIdCuenta())));
		tipos.add(new BigIntegerType());
		params.add(new BigInteger(String.valueOf(registroContable.getOperacion().getCuentaReceptora().getIdCuenta())));
		tipos.add(new BigIntegerType());
		
		return (List<OperacionPosicionDTO>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				
				Query query = session.createQuery(queryString.toString()).setParameters(params.toArray(), tipos.toArray(new Type[] {}));
				ArrayList<OperacionPosicionDTO> registros = new ArrayList<OperacionPosicionDTO>();
				Iterator<OperacionNombradaHistorico> it = query.list().iterator();
				while (it.hasNext()) {
					registros.add(DTOAssembler.crearOperacionHistorico(it.next()));
				}

				return registros;
				
			}
		});
	}
}
