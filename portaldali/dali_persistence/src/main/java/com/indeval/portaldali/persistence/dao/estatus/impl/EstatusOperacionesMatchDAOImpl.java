/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * EstatusOperacionesMatchDAO.java
 * 04/03/2008
 */
package com.indeval.portaldali.persistence.dao.estatus.impl;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.type.BigDecimalType;
import org.hibernate.type.BigIntegerType;
import org.hibernate.type.DateType;
import org.hibernate.type.DoubleType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.hibernate.type.TimestampType;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.indeval.portaldali.middleware.dto.OperacionValorMatchDTO;
import com.indeval.portaldali.middleware.dto.TipoInstruccionDTO;
import com.indeval.portaldali.middleware.dto.criterio.CriterioMatchOperacionesDTO;
import com.indeval.portaldali.middleware.dto.criterio.CriterioMatchOperacionesExportacionDTO;
import com.indeval.portaldali.middleware.dto.util.DTOAssembler;
import com.indeval.portaldali.middleware.dto.util.EstadoPaginacionDTO;
import com.indeval.portaldali.middleware.model.util.ConsultaOperacionesMatch;
import com.indeval.portaldali.middleware.services.common.constants.DaliConstants;
import com.indeval.portaldali.middleware.services.common.constants.EstadoInstruccionConstants;
import com.indeval.portaldali.middleware.services.common.constants.RolConstants;
import com.indeval.portaldali.middleware.services.modelo.InfrastructureException;
import com.indeval.portaldali.persistence.dao.common.DateUtilsDao;
import com.indeval.portaldali.persistence.dao.common.util.QueryUtil;
import com.indeval.portaldali.persistence.model.CuentaNombrada;
import com.indeval.portaldali.persistence.model.Institucion;
import com.indeval.portaldali.persistence.model.InstruccionOperacionVal;
import com.indeval.portaldali.persistence.model.LiquidacionParcialInstruccionMov;
import com.indeval.portaldali.persistence.model.MensajeBean;
import com.indeval.portaldali.persistence.model.MensajeBean.EstadoInstruccion;
import com.indeval.portaldali.persistence.model.OperacionNombrada;
import com.indeval.portaldali.persistence.model.operacionesMatch.ConsultaInstruccionesMatchSinOperacion;
import com.indeval.portaldali.persistence.model.operacionesMatch.ConsultaOperacionValor;

/**
 * Implementación del Objeto de Acceso a Datos para la consulta de operaciones
 * de valores.
 * 
 * @author Emigdio Hernández
 * 
 */
public class EstatusOperacionesMatchDAOImpl extends HibernateDaoSupport implements com.indeval.portaldali.persistence.dao.estatus.EstatusOperacionesMatchDAO {

	private static final Logger logger = LoggerFactory.getLogger(EstatusOperacionesMatchDAOImpl.class);
	
	/**
	 * Mapeo de los tipos de operación con el tipo de instrucción.
	 */
	private HashMap<String, TipoInstruccionDTO> mapeoOperacionInstruccion;

	private DateUtilsDao dateUtilsDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.persistence.dao.estatus.EstatusOperacionesMatchDAO#consultarOperacionesValor(com.indeval.portaldali.middleware.dto.criterio.CriterioMatchOperacionesDTO)
	 */
	@SuppressWarnings("unchecked")
	public Collection<OperacionValorMatchDTO> consultarOperacionesValor(final CriterioMatchOperacionesDTO criterio, final EstadoPaginacionDTO paginacion) {

		final StringBuffer query = new StringBuffer();
		final ArrayList<Object> params = new ArrayList<Object>();
		final ArrayList<Type> tipos = new ArrayList<Type>();

		query.append(" FROM " + InstruccionOperacionVal.class.getName() + " as iopv ");

		query.append("left outer join fetch iopv.cupon ");
		query.append("left outer join fetch iopv.cupon.emision ");
		query.append("left outer join fetch iopv.errorDali ");
		query.append("left outer join fetch iopv.cupon.emision.emisora ");
		query.append("left outer join fetch iopv.cupon.emision.instrumento ");
		query.append("left join fetch iopv.parcialidadesLiquidacion  ");
		query.append("left outer join fetch iopv.cuentaNombradaBancoTrabajo ");
		query.append("left outer join fetch iopv.cuentaNombradaReceptora ");
		query.append("left outer join fetch iopv.cuentaNombradaTraspasante ");
		query.append("left outer join fetch iopv.divisa ");
		query.append("left outer join fetch iopv.boveda ");
		query.append("left outer join fetch iopv.emisionPendiente ");
		query.append("left outer join fetch iopv.emisionPendiente.estadoEmision ");
		query.append("left outer join fetch iopv.estadoInstruccionCat ");
		query.append("left outer join fetch iopv.institucionBancoTrabajo ");
		query.append("left outer join fetch iopv.institucionReceptora ");
		query.append("left outer join fetch iopv.institucionTraspasante ");
		query.append("left outer join fetch iopv.tipoInstruccion ");
		query.append("left outer join fetch iopv.tipoMensajeCat ");
		query.append("left outer join fetch iopv.operacionMiscelaneaFiscal ");

		crearCriteriosQueryConsultaOperacionesValor(query, params, tipos, criterio, false);

		return (Collection<OperacionValorMatchDTO>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {

				Query hQuery = session.createQuery(query.toString());

				hQuery.setParameters(params.toArray(), tipos.toArray(new Type[] {}));

				if (paginacion != null) {
					int totalRegistros = hQuery.list().size();
					
					paginacion.setTotalResultados(totalRegistros);
					
					int totalPaginas = totalRegistros / paginacion.getRegistrosPorPagina();
					
					if(totalRegistros % paginacion.getRegistrosPorPagina() > 0)
						totalPaginas++;
					
					totalPaginas = (totalPaginas <= 0) ? 1 : totalPaginas;
					
					paginacion.setTotalPaginas(totalPaginas);
					

					hQuery.setMaxResults(paginacion.getRegistrosPorPagina());
					hQuery.setFetchSize(paginacion.getRegistrosPorPagina());
					hQuery.setFirstResult(((paginacion.getNumeroPagina() - 1) * paginacion.getRegistrosPorPagina()));
				}

				List<InstruccionOperacionVal> resultadosBO = hQuery.list();

				Set<OperacionValorMatchDTO> resultados = new TreeSet<OperacionValorMatchDTO>(new Comparator<OperacionValorMatchDTO>() {
					public int compare(OperacionValorMatchDTO o1, OperacionValorMatchDTO o2) {
						String s1 = o1.getFolioControl() + o1.getInstitucionTraspasante().getClaveTipoInstitucion() +
							o1.getInstitucionTraspasante().getFolioInstitucion() + o1.getInstitucionReceptora().getClaveTipoInstitucion() +
								o1.getInstitucionReceptora().getFolioInstitucion();
						String s2 = o2.getFolioControl() + o2.getInstitucionTraspasante().getClaveTipoInstitucion() +
							o2.getInstitucionTraspasante().getFolioInstitucion() + o2.getInstitucionReceptora().getClaveTipoInstitucion() +
								o2.getInstitucionReceptora().getFolioInstitucion();
						int resultado = s1.compareTo(s2);
						
						return resultado;
					}

				});
				if(resultadosBO != null && !resultadosBO.isEmpty()) {
					Date fechaSistema = dateUtilsDao.getDateFechaDB();					
					for (InstruccionOperacionVal bo : resultadosBO) {
						resultados.addAll(DTOAssembler.crearOperacionValorMatchDTO(bo, null, criterio.getInstitucionParticipante(), false, criterio
								.getEstadoInstruccion() != null ? criterio.getEstadoInstruccion().getIdEstadoInstruccion() : DaliConstants.VALOR_COMBO_TODOS,
										criterio.getRol(), false, fechaSistema));
					}
				}
				return resultados;
			}
		});

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.persistence.dao.estatus.EstatusOperacionesMatchDAO#buscarParcialidades(com.indeval.portaldali.middleware.dto.criterio.CriterioMatchOperacionesDTO,
	 *      com.indeval.portaldali.middleware.dto.util.EstadoPaginacionDTO)
	 */
	@SuppressWarnings("unchecked")
	public List<OperacionValorMatchDTO> buscarParcialidades(final CriterioMatchOperacionesDTO criterio, final EstadoPaginacionDTO paginacion) {

		final StringBuffer query = new StringBuffer();
		final ArrayList<Object> params = new ArrayList<Object>();
		final ArrayList<Type> tipos = new ArrayList<Type>();

		query.append("FROM " + LiquidacionParcialInstruccionMov.class.getName() + " lp ");
		query.append("join fetch lp.instruccionOperacionVal iopv ");
		query.append("left outer join fetch iopv.cupon ");
		query.append("left outer join fetch iopv.cupon.emision ");
		query.append("left outer join fetch iopv.errorDali ");
		query.append("left outer join fetch iopv.cupon.emision.emisora ");
		query.append("left outer join fetch iopv.cupon.emision.instrumento ");
		query.append("left outer join fetch iopv.cuentaNombradaBancoTrabajo ");
		query.append("left outer join fetch iopv.cuentaNombradaReceptora ");
		query.append("left outer join fetch iopv.cuentaNombradaTraspasante ");
		query.append("left outer join fetch iopv.divisa ");
		query.append("left outer join fetch iopv.emisionPendiente ");
		query.append("left outer join fetch iopv.emisionPendiente.estadoEmision ");
		query.append("left outer join fetch iopv.estadoInstruccionCat ");
		query.append("left outer join fetch iopv.institucionBancoTrabajo ");
		query.append("left outer join fetch iopv.institucionReceptora ");
		query.append("left outer join fetch iopv.institucionTraspasante ");
		query.append("left outer join fetch iopv.tipoInstruccion ");
		query.append("left outer join fetch iopv.tipoMensajeCat ");
		query.append("left outer join fetch iopv.operacionMiscelaneaFiscal ");

		crearCriteriosQueryConsultaOperacionesValor(query, params, tipos, criterio, true);

		// cantidad de títulos
		if (NumberUtils.toLong(criterio.getCantidad(), DaliConstants.VALOR_COMBO_TODOS) != DaliConstants.VALOR_COMBO_TODOS) {
			QueryUtil.agregarCondicion(query, params);
			// i.getInstruccionOperacionVal().getCantidadTitulos();
			query.append(" lp.numeroTitulos = ? ");
			params.add(NumberUtils.toLong(criterio.getCantidad(), DaliConstants.VALOR_COMBO_TODOS));
			tipos.add(new LongType());
		}

		// monto
		if (NumberUtils.toDouble(criterio.getMonto(), DaliConstants.VALOR_COMBO_TODOS) != DaliConstants.VALOR_COMBO_TODOS) {
			QueryUtil.agregarCondicion(query, params);
			// i.getInstruccionOperacionVal().getImporte()
			query.append(" TO_CHAR(lp.importe,'FM99999999999999.99') = to_char(?,'FM99999999999999.99') ");
			params.add(new BigDecimal(NumberUtils.toDouble(criterio.getMonto(), DaliConstants.VALOR_COMBO_TODOS)));
			tipos.add(new BigDecimalType());
		}

		// folio control
		if (!StringUtils.isEmpty(criterio.getFolioControl())) {
			QueryUtil.agregarCondicion(query, params);
			// i.getInstruccionOperacionVal().getFolioControl();
			query.append(" lp.idLiquidacionParcialInstruccionMov = ? ");
			params.add(NumberUtils.toLong(criterio.getFolioControl(), DaliConstants.VALOR_COMBO_TODOS));
			tipos.add(new LongType());
		}

		// fecha liquidación
		if (criterio.getFechaLiquidacion() != null) {
			QueryUtil.agregarCondicion(query, params);
			// i.getFechaLiquidacion()
			query.append("trunc(lp.fechaHora) = ? ");
			params.add(criterio.getFechaLiquidacion());
			tipos.add(new DateType());
		}

		return (List<OperacionValorMatchDTO>) getHibernateTemplate().execute(
			new HibernateCallback() {
				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					// Obtiene la fecha del sistema
					// Pablo Balderas - 05/Agosto/2015
					Date fechaSistema = dateUtilsDao.getDateFechaDB();
					
					List<OperacionValorMatchDTO> operaciones = new ArrayList<OperacionValorMatchDTO>();

					Query hQuery = session.createQuery(query.toString());
					hQuery.setParameters(params.toArray(), tipos.toArray(new Type[] {}));

					List<LiquidacionParcialInstruccionMov> resultadosBO = hQuery.list();
					for (LiquidacionParcialInstruccionMov bo : resultadosBO) {
						List<OperacionValorMatchDTO> opers = (List<OperacionValorMatchDTO>) DTOAssembler.crearOperacionValorMatchDTO(
							bo.getInstruccionOperacionVal(), null, criterio.getInstitucionParticipante(),
							false, criterio.getEstadoInstruccion() != null ?
									criterio.getEstadoInstruccion().getIdEstadoInstruccion()
									: DaliConstants.VALOR_COMBO_TODOS,
							criterio.getRol(), true, fechaSistema);

						for (OperacionValorMatchDTO operacionValorMatchDTO : opers) {
							operacionValorMatchDTO.setCantidadTitulos(bo.getNumeroTitulos() != null ? bo.getNumeroTitulos().longValue() : 0);
							operacionValorMatchDTO.setImporte(bo.getImporte() != null ? bo.getImporte().doubleValue() : 0);
							operacionValorMatchDTO.setFolioControl(bo.getIdInstruccionLiquidacion() != null ? bo.getIdLiquidacionParcialInstruccionMov().toString() : "0");
							operacionValorMatchDTO.getEstadoInstruccion().setClaveEstadoInstruccion(EstadoInstruccionConstants.CLAVE_EDO_INSTRUCCION_LIQUIDADA);
							operacionValorMatchDTO.getEstadoInstruccion().setIdEstadoInstruccion(EstadoInstruccionConstants.INSTRUCCION_LIQUIDADA);
							operacionValorMatchDTO.setFolioOrigen(bo.getInstruccionOperacionVal().getFolioControl() != null ? bo.getInstruccionOperacionVal().getFolioControl().toString() : "0");
							operacionValorMatchDTO.setFechaLiquidacion(bo.getFechaHora());

							if (criterio.getEstadoInstruccion() != null
									&& criterio.getEstadoInstruccion().getIdEstadoInstruccion() == DaliConstants.VALOR_COMBO_TODOS
									|| operacionValorMatchDTO.getEstadoInstruccion().getIdEstadoInstruccion() == criterio.getEstadoInstruccion().getIdEstadoInstruccion()) {
								operaciones.add(operacionValorMatchDTO);
							}
						}
					}

					return operaciones;
				}
			}
		);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.persistence.dao.estatus.EstatusOperacionesMatchDAO#obtenerProyeccionConsultaOperacionesValor(com.indeval.portaldali.middleware.dto.criterio.CriterioMatchOperacionesDTO)
	 */
	public long obtenerProyeccionConsultaOperacionesValor(final CriterioMatchOperacionesDTO criterio) {

		long res = (Long) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {

				StringBuffer query = new StringBuffer();
				ArrayList<Object> params = new ArrayList<Object>();
				ArrayList<Type> tipos = new ArrayList<Type>();

				query.append("SELECT COUNT(*) FROM " + InstruccionOperacionVal.class.getName() + " iopv ");
				query.append("left join iopv.cupon ");
				query.append("left join iopv.cupon.emision ");
				query.append("left join iopv.cupon.emision.emisora ");
				query.append("left join iopv.cupon.emision.instrumento ");
				query.append("left join iopv.emisionPendiente ");

				crearCriteriosQueryConsultaOperacionesValor(query, params, tipos, criterio, false);

				Query hQuery = session.createQuery(query.toString());

				hQuery.setParameters(params.toArray(), tipos.toArray(new Type[] {}));

				return hQuery.uniqueResult();
			}
		});

		if (criterio.getEstadoInstruccion() != null
				&& (criterio.getEstadoInstruccion().getIdEstadoInstruccion() == EstadoInstruccionConstants.INSTRUCCION_LIQUIDADA || criterio
						.getEstadoInstruccion().getIdEstadoInstruccion() == DaliConstants.VALOR_COMBO_TODOS)) {

			res += (Long) getHibernateTemplate().execute(new HibernateCallback() {
				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					StringBuffer query = new StringBuffer();
					ArrayList<Object> params = new ArrayList<Object>();
					ArrayList<Type> tipos = new ArrayList<Type>();

					query.append("SELECT COUNT(*) FROM " + LiquidacionParcialInstruccionMov.class.getName() + " liqp ");
					query.append("WHERE liqp.idInstruccionOperacionVal in ( ");
					query.append("SELECT iopv.idInstruccionOperacionVal FROM " + InstruccionOperacionVal.class.getName() + " iopv ");
					crearCriteriosQueryConsultaOperacionesValor(query, params, tipos, criterio, false);
					query.append(")");

					Query hQuery = session.createQuery(query.toString());

					hQuery.setParameters(params.toArray(), tipos.toArray(new Type[] {}));

					return hQuery.uniqueResult();
				}
			});
		}

		if (criterio.getEstadoInstruccion() != null
				&& criterio.getEstadoInstruccion().getIdEstadoInstruccion() == EstadoInstruccionConstants.INSTRUCCION_LIQUIDADA) {

			criterio.getEstadoInstruccion().setIdEstadoInstruccion(EstadoInstruccionConstants.LIQUIDADA_PARCIALMENTE);
			res -= (Long) getHibernateTemplate().execute(new HibernateCallback() {
				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					StringBuffer query = new StringBuffer();
					ArrayList<Object> params = new ArrayList<Object>();
					ArrayList<Type> tipos = new ArrayList<Type>();

					query.append("SELECT COUNT(*) FROM " + InstruccionOperacionVal.class.getName() + " iopv ");
					crearCriteriosQueryConsultaOperacionesValor(query, params, tipos, criterio, false);

					Query hQuery = session.createQuery(query.toString());
					hQuery.setParameters(params.toArray(), tipos.toArray(new Type[] {}));
					return hQuery.uniqueResult();
				}
			});
			criterio.getEstadoInstruccion().setIdEstadoInstruccion(EstadoInstruccionConstants.INSTRUCCION_LIQUIDADA);
		}

		if (criterio.getEstadoInstruccion() != null
				&& ((criterio.getEstadoInstruccion().getIdEstadoInstruccion() > DaliConstants.VALOR_COMBO_TODOS && criterio.getEstadoInstruccion()
						.getIdEstadoInstruccion() == EstadoInstruccionConstants.INSTRUCCION_LIQUIDADA) || (criterio.getEstadoInstruccion()
						.getIdEstadoInstruccion() == DaliConstants.VALOR_COMBO_TODOS))
				&& (StringUtils.isNotBlank(criterio.getFolioControl()) || StringUtils.isNotBlank(criterio.getCantidad()) || StringUtils.isNotBlank(criterio
						.getMonto()))) {

			res += (Long) getHibernateTemplate().execute(new HibernateCallback() {
				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					StringBuffer query = new StringBuffer();
					ArrayList<Object> params = new ArrayList<Object>();
					ArrayList<Type> tipos = new ArrayList<Type>();

					boolean existenParametros = true;

					query.append("SELECT COUNT(*) FROM " + LiquidacionParcialInstruccionMov.class.getName() + " liqp ");
					query.append("WHERE liqp.idInstruccionOperacionVal in ( ");
					query.append("SELECT iopv.idInstruccionOperacionVal FROM " + InstruccionOperacionVal.class.getName() + " iopv ");
					crearCriteriosQueryConsultaOperacionesValor(query, params, tipos, criterio, true);
					query.append(")");

					if (params.isEmpty()) {
						params.add(new Date());
						existenParametros = false;
					}

					// cantidad de títulos
					if (NumberUtils.toLong(criterio.getCantidad(), DaliConstants.VALOR_COMBO_TODOS) != DaliConstants.VALOR_COMBO_TODOS) {
						QueryUtil.agregarCondicion(query, params);
						// i.getInstruccionOperacionVal().getCantidadTitulos();
						query.append(" liqp.numeroTitulos = ? ");
						params.add(NumberUtils.toLong(criterio.getCantidad(), DaliConstants.VALOR_COMBO_TODOS));
						tipos.add(new LongType());
					}

					// monto
					if (NumberUtils.toDouble(criterio.getMonto(), DaliConstants.VALOR_COMBO_TODOS) != DaliConstants.VALOR_COMBO_TODOS) {
						QueryUtil.agregarCondicion(query, params);
						// i.getInstruccionOperacionVal().getImporte()
						query.append(" TO_CHAR(liqp.importe,'FM99999999999999.99') = to_char(?,'FM99999999999999.99') ");
						params.add(new BigDecimal(NumberUtils.toDouble(criterio.getMonto(), DaliConstants.VALOR_COMBO_TODOS)));
						tipos.add(new BigDecimalType());
					}

					// folio control
					if (!StringUtils.isEmpty(criterio.getFolioControl())) {
						QueryUtil.agregarCondicion(query, params);
						// i.getInstruccionOperacionVal().getFolioControl();
						query.append(" liqp.idLiquidacionParcialInstruccionMov = ? ");
						params.add(NumberUtils.toLong(criterio.getFolioControl(), DaliConstants.VALOR_COMBO_TODOS));
						tipos.add(new LongType());
					}

					// fecha liquidación
					if (criterio.getFechaLiquidacion() != null) {
						QueryUtil.agregarCondicion(query, params);
						// i.getFechaLiquidacion()
						query.append("trunc(liqp.fechaHora) = ? ");
						params.add(criterio.getFechaLiquidacion());
						tipos.add(new DateType());
					}

					if (!existenParametros) {
						params.remove(0);
					}

					Query hQuery = session.createQuery(query.toString());

					hQuery.setParameters(params.toArray(), tipos.toArray(new Type[] {}));

					return hQuery.uniqueResult();
				}
			});
		}

		return res;
	}

	/**
	 * Obtiene el valor del atributo mapeoOperacionInstruccion
	 * 
	 * @return el valor del atributo mapeoOperacionInstruccion
	 */
	public HashMap<String, TipoInstruccionDTO> getMapeoOperacionInstruccion() {
		return mapeoOperacionInstruccion;
	}

	/**
	 * Establece el valor del atributo mapeoOperacionInstruccion
	 * 
	 * @param mapeoOperacionInstruccion
	 *            el valor del atributo mapeoOperacionInstruccion a establecer
	 */
	public void setMapeoOperacionInstruccion(HashMap<String, TipoInstruccionDTO> mapeoOperacionInstruccion) {
		this.mapeoOperacionInstruccion = mapeoOperacionInstruccion;
	}

	/**
	 * Agrega a la cadena enviada como parámetro el query que se construye con
	 * los criterios indicados, agregando al arreglo de parametros y tipos los
	 * valores de cada parámetro utilizado en el query.
	 * 
	 * @param query
	 *            Query a completar
	 * @param params
	 *            Parametros a completar
	 * @param tipos
	 *            Tipos a completar
	 * @param criterio
	 *            criterio a utilizar
	 */
	private void crearCriteriosQueryConsultaOperacionesValor(StringBuffer query, List<Object> params, List<Type> tipos, CriterioMatchOperacionesDTO criterio,
			boolean excluirCriteriosParcialidades) {

		if (criterio.getMercado() != null && criterio.getMercado().getId() != DaliConstants.VALOR_COMBO_TODOS) {
			QueryUtil.agregarCondicion(query, params);
			// i.getEmision().getInstrumento().getIdMercado()
			if (criterio.getMercado().getId() == DaliConstants.ID_MERCADO_DINERO) {
				query.append(" iopv.cupon.emision.instrumento.idMercado IN (?,?) ");
				params.add(new BigInteger(String.valueOf(DaliConstants.ID_MERCADO_PAPEL_BANCARIO)));
				tipos.add(new BigIntegerType());
				params.add(new BigInteger(String.valueOf(DaliConstants.ID_MERCADO_PAPEL_GUBER)));
				tipos.add(new BigIntegerType());
			} else {
				query.append(" iopv.cupon.emision.instrumento.idMercado = ? ");
				params.add(new BigInteger(String.valueOf(criterio.getMercado().getId())));
				tipos.add(new BigIntegerType());
			}
		}

		// Tipo de operación
		if (criterio.getTipoInstruccion() != null && criterio.getTipoInstruccion().getIdTipoInstruccion() != DaliConstants.VALOR_COMBO_TODOS) {
			QueryUtil.agregarCondicion(query, params);
			// i.getIdTipoInstruccion()

			query.append("iopv.idTipoInstruccion = ? ");
			params.add(new BigInteger(String.valueOf(criterio.getTipoInstruccion().getIdTipoInstruccion())));
			tipos.add(new BigIntegerType());
		}
		// fecha Concertacion
		if (criterio.getFechaConcertacion() != null) {
			QueryUtil.agregarCondicion(query, params);
			// i.getFechaConcertacion()
			query.append("trunc(iopv.fechaConcertacion) = ? ");
			params.add(criterio.getFechaConcertacion());
			tipos.add(new DateType());
		}
		// fecha de concertacion solo para miscelanea fiscal
		if(criterio.getFechaInicioPeriodo() != null) {
			QueryUtil.agregarCondicion(query, params);
			query.append(" iopv.fechaConcertacion >= ? ");
			params.add(horaInicioFin(criterio.getFechaInicioPeriodo(), true));
			tipos.add(new TimestampType());
		}
		if(criterio.getFechaFinPeriodo() != null) {
			QueryUtil.agregarCondicion(query, params);
			query.append(" iopv.fechaConcertacion <= ? ");
			params.add(horaInicioFin(criterio.getFechaFinPeriodo(), false));
			tipos.add(new TimestampType());
		}
		
		if(criterio.getBovedaValores() != null && criterio.getBovedaValores().getId()>0 ) {
			QueryUtil.agregarCondicion(query, params);
			query.append(" iopv.boveda.idBoveda = ? ");
			params.add(criterio.getBovedaValores().getId());
			tipos.add(new LongType());
		}
		
		if(criterio.getBovedaEfectivo() != null && criterio.getBovedaEfectivo().getId()>0 ) {
			QueryUtil.agregarCondicion(query, params);
			query.append(" iopv.bovedaEfectivo.idBoveda = ? ");
			params.add(criterio.getBovedaEfectivo().getId());
			tipos.add(new LongType());
		}
		
		if(criterio.getDivisa() != null && criterio.getDivisa().getId()>0 ) {
			QueryUtil.agregarCondicion(query, params);
			query.append(" iopv.divisa.idDivisa = ? ");
			params.add(criterio.getDivisa().getId());
			tipos.add(new LongType());
		}

		// Roles
		boolean existenCriteriosTraspORecep = (criterio.getInstitucionParticipante() != null && (criterio.getInstitucionParticipante().getId() > DaliConstants.VALOR_COMBO_TODOS || StringUtils
				.isNotBlank(criterio.getInstitucionParticipante().getClaveTipoInstitucion())))
				|| (criterio.getCuentaParticipante() != null && StringUtils.isNotBlank(criterio.getCuentaParticipante().getCuenta()))
				|| (criterio.getInstitucionContraparte() != null && (criterio.getInstitucionContraparte().getId() > DaliConstants.VALOR_COMBO_TODOS || StringUtils
						.isNotBlank(criterio.getInstitucionContraparte().getClaveTipoInstitucion())))
				|| (criterio.getCuentaContraparte() != null && StringUtils.isNotBlank(criterio.getCuentaContraparte().getCuenta()));

		if (existenCriteriosTraspORecep) {
			QueryUtil.agregarCondicion(query, params);

			if (criterio.getRol() == RolConstants.ROL_AMBOS) {
				query.append(" ( ");
			}

			if (criterio.getRol() == RolConstants.ROL_AMBOS || criterio.getRol() == RolConstants.ROL_TRASPASANTE) {

				query.append(" ( ( iopv.idInstitucionBancoTrabajo is null ");

				if (criterio.getInstitucionParticipante() != null
						&& (criterio.getInstitucionParticipante().getId() > DaliConstants.VALOR_COMBO_TODOS || StringUtils.isNotBlank(criterio
								.getInstitucionParticipante().getClaveTipoInstitucion()))) {

					if (criterio.getInstitucionParticipante().getId() > DaliConstants.VALOR_COMBO_TODOS) {
						query.append(" AND iopv.idInstitucionTraspasante = ? ");
						params.add(new BigInteger(String.valueOf(criterio.getInstitucionParticipante().getId())));
						tipos.add(new BigIntegerType());
					} else {
						query.append(" AND iopv.institucionTraspasante.tipoInstitucion.claveTipoInstitucion = ? ");
						params.add(criterio.getInstitucionParticipante().getClaveTipoInstitucion());
						tipos.add(new StringType());
					}
				}

				if (criterio.getCuentaParticipante() != null && StringUtils.isNotBlank(criterio.getCuentaParticipante().getCuenta())) {

					query.append(" AND iopv.cuentaNombradaTraspasante.cuenta = ? ");
					params.add(criterio.getCuentaParticipante().getCuenta());
					tipos.add(new StringType());
				}

				if (criterio.getInstitucionContraparte() != null
						&& (criterio.getInstitucionContraparte().getId() > DaliConstants.VALOR_COMBO_TODOS || StringUtils.isNotBlank(criterio
								.getInstitucionContraparte().getClaveTipoInstitucion()))) {

					if (criterio.getInstitucionContraparte().getId() > DaliConstants.VALOR_COMBO_TODOS) {
						query.append(" AND iopv.idInstitucionReceptora = ? ");
						params.add(new BigInteger(String.valueOf(criterio.getInstitucionContraparte().getId())));
						tipos.add(new BigIntegerType());
					} else {
						query.append(" AND iopv.institucionReceptora.tipoInstitucion.claveTipoInstitucion = ? ");
						params.add(criterio.getInstitucionContraparte().getClaveTipoInstitucion());
						tipos.add(new StringType());
					}
				}

				if (criterio.getCuentaContraparte() != null && StringUtils.isNotBlank(criterio.getCuentaContraparte().getCuenta())) {

					query.append(" AND iopv.cuentaNombradaReceptora.cuenta = ? ");
					params.add(criterio.getCuentaContraparte().getCuenta());
					tipos.add(new StringType());
				}

				query.append(" ) OR ( iopv.idInstitucionBancoTrabajo is not null ");

				if (criterio.getInstitucionParticipante() != null
						&& (criterio.getInstitucionParticipante().getId() > DaliConstants.VALOR_COMBO_TODOS || StringUtils.isNotBlank(criterio
								.getInstitucionParticipante().getClaveTipoInstitucion()))) {

					if (criterio.getInstitucionParticipante().getId() > DaliConstants.VALOR_COMBO_TODOS) {
						query.append(" AND iopv.idInstitucionTraspasante = ? ");
						params.add(new BigInteger(String.valueOf(criterio.getInstitucionParticipante().getId())));
						tipos.add(new BigIntegerType());
					} else {
						query.append(" AND iopv.institucionTraspasante.tipoInstitucion.claveTipoInstitucion = ? ");
						params.add(criterio.getInstitucionParticipante().getClaveTipoInstitucion());
						tipos.add(new StringType());
					}
				}

				if (criterio.getCuentaParticipante() != null && StringUtils.isNotBlank(criterio.getCuentaParticipante().getCuenta())) {

					query.append(" AND iopv.cuentaNombradaTraspasante.cuenta = ? ");
					params.add(criterio.getCuentaParticipante().getCuenta());
					tipos.add(new StringType());
				}

				if (criterio.getInstitucionContraparte() != null
						&& (criterio.getInstitucionContraparte().getId() > DaliConstants.VALOR_COMBO_TODOS || StringUtils.isNotBlank(criterio
								.getInstitucionContraparte().getClaveTipoInstitucion()))) {

					if (criterio.getInstitucionContraparte().getId() > DaliConstants.VALOR_COMBO_TODOS) {
						query.append(" AND iopv.idInstitucionBancoTrabajo = ? ");
						params.add(new BigInteger(String.valueOf(criterio.getInstitucionContraparte().getId())));
						tipos.add(new BigIntegerType());
					} else {
						query.append(" AND iopv.institucionBancoTrabajo.tipoInstitucion.claveTipoInstitucion = ? ");
						params.add(criterio.getInstitucionContraparte().getClaveTipoInstitucion());
						tipos.add(new StringType());
					}
				}

				if (criterio.getCuentaContraparte() != null && StringUtils.isNotBlank(criterio.getCuentaContraparte().getCuenta())) {

					query.append(" AND iopv.cuentaNombradaBancoTrabajo.cuenta = ? ");
					params.add(criterio.getCuentaContraparte().getCuenta());
					tipos.add(new StringType());
				}

				query.append(" ) OR ( iopv.idInstitucionBancoTrabajo is not null ");

				if (criterio.getInstitucionParticipante() != null
						&& (criterio.getInstitucionParticipante().getId() > DaliConstants.VALOR_COMBO_TODOS || StringUtils.isNotBlank(criterio
								.getInstitucionParticipante().getClaveTipoInstitucion()))) {

					if (criterio.getInstitucionParticipante().getId() > DaliConstants.VALOR_COMBO_TODOS) {
						query.append(" AND iopv.idInstitucionBancoTrabajo = ? ");
						params.add(new BigInteger(String.valueOf(criterio.getInstitucionParticipante().getId())));
						tipos.add(new BigIntegerType());
					} else {
						query.append(" AND iopv.institucionBancoTrabajo.tipoInstitucion.claveTipoInstitucion = ? ");
						params.add(criterio.getInstitucionParticipante().getClaveTipoInstitucion());
						tipos.add(new StringType());
					}
				}

				if (criterio.getCuentaParticipante() != null && StringUtils.isNotBlank(criterio.getCuentaParticipante().getCuenta())) {

					query.append(" AND iopv.cuentaNombradaBancoTrabajo.cuenta = ? ");
					params.add(criterio.getCuentaParticipante().getCuenta());
					tipos.add(new StringType());
				}

				if (criterio.getInstitucionContraparte() != null
						&& (criterio.getInstitucionContraparte().getId() > DaliConstants.VALOR_COMBO_TODOS || StringUtils.isNotBlank(criterio
								.getInstitucionContraparte().getClaveTipoInstitucion()))) {

					if (criterio.getInstitucionContraparte().getId() > DaliConstants.VALOR_COMBO_TODOS) {
						query.append(" AND iopv.idInstitucionReceptora = ? ");
						params.add(new BigInteger(String.valueOf(criterio.getInstitucionContraparte().getId())));
						tipos.add(new BigIntegerType());
					} else {
						query.append(" AND iopv.institucionReceptora.tipoInstitucion.claveTipoInstitucion = ? ");
						params.add(criterio.getInstitucionContraparte().getClaveTipoInstitucion());
						tipos.add(new StringType());
					}
				}

				if (criterio.getCuentaContraparte() != null && StringUtils.isNotBlank(criterio.getCuentaContraparte().getCuenta())) {

					query.append(" AND iopv.cuentaNombradaReceptora.cuenta = ? ");
					params.add(criterio.getCuentaContraparte().getCuenta());
					tipos.add(new StringType());
				}

				query.append(" ) ) ");

			}

			if (criterio.getRol() == RolConstants.ROL_AMBOS) {
				query.append(" or ");
			}

			if (criterio.getRol() == RolConstants.ROL_AMBOS || criterio.getRol() == RolConstants.ROL_RECEPTOR) {

				query.append(" ( (  iopv.idInstitucionBancoTrabajo is null ");

				if (criterio.getInstitucionContraparte() != null
						&& (criterio.getInstitucionContraparte().getId() > DaliConstants.VALOR_COMBO_TODOS || StringUtils.isNotBlank(criterio
								.getInstitucionContraparte().getClaveTipoInstitucion()))) {

					if (criterio.getInstitucionContraparte().getId() > DaliConstants.VALOR_COMBO_TODOS) {
						query.append(" AND iopv.idInstitucionTraspasante = ? ");
						params.add(new BigInteger(String.valueOf(criterio.getInstitucionContraparte().getId())));
						tipos.add(new BigIntegerType());
					} else {
						query.append(" AND iopv.institucionTraspasante.tipoInstitucion.claveTipoInstitucion = ? ");
						params.add(criterio.getInstitucionContraparte().getClaveTipoInstitucion());
						tipos.add(new StringType());
					}
				}

				if (criterio.getCuentaContraparte() != null && StringUtils.isNotBlank(criterio.getCuentaContraparte().getCuenta())) {

					query.append(" AND iopv.cuentaNombradaTraspasante.cuenta = ? ");
					params.add(criterio.getCuentaContraparte().getCuenta());
					tipos.add(new StringType());
				}

				if (criterio.getInstitucionParticipante() != null
						&& (criterio.getInstitucionParticipante().getId() > DaliConstants.VALOR_COMBO_TODOS || StringUtils.isNotBlank(criterio
								.getInstitucionParticipante().getClaveTipoInstitucion()))) {

					if (criterio.getInstitucionParticipante().getId() > DaliConstants.VALOR_COMBO_TODOS) {
						query.append(" AND iopv.idInstitucionReceptora = ? ");
						params.add(new BigInteger(String.valueOf(criterio.getInstitucionParticipante().getId())));
						tipos.add(new BigIntegerType());
					} else {
						query.append(" AND iopv.institucionReceptora.tipoInstitucion.claveTipoInstitucion = ? ");
						params.add(criterio.getInstitucionParticipante().getClaveTipoInstitucion());
						tipos.add(new StringType());
					}
				}

				if (criterio.getCuentaParticipante() != null && StringUtils.isNotBlank(criterio.getCuentaParticipante().getCuenta())) {

					query.append(" AND iopv.cuentaNombradaReceptora.cuenta = ? ");
					params.add(criterio.getCuentaParticipante().getCuenta());
					tipos.add(new StringType());
				}

				query.append(" ) OR ( iopv.idInstitucionBancoTrabajo is not null ");

				if (criterio.getInstitucionContraparte() != null
						&& (criterio.getInstitucionContraparte().getId() > DaliConstants.VALOR_COMBO_TODOS || StringUtils.isNotBlank(criterio
								.getInstitucionContraparte().getClaveTipoInstitucion()))) {

					if (criterio.getInstitucionContraparte().getId() > DaliConstants.VALOR_COMBO_TODOS) {
						query.append(" AND iopv.idInstitucionBancoTrabajo = ? ");
						params.add(new BigInteger(String.valueOf(criterio.getInstitucionContraparte().getId())));
						tipos.add(new BigIntegerType());
					} else {
						query.append(" AND iopv.institucionBancoTrabajo.tipoInstitucion.claveTipoInstitucion = ? ");
						params.add(criterio.getInstitucionContraparte().getClaveTipoInstitucion());
						tipos.add(new StringType());
					}
				}

				if (criterio.getCuentaContraparte() != null && StringUtils.isNotBlank(criterio.getCuentaContraparte().getCuenta())) {

					query.append(" AND iopv.cuentaNombradaBancoTrabajo.cuenta = ? ");
					params.add(criterio.getCuentaContraparte().getCuenta());
					tipos.add(new StringType());
				}

				if (criterio.getInstitucionParticipante() != null
						&& (criterio.getInstitucionParticipante().getId() > DaliConstants.VALOR_COMBO_TODOS || StringUtils.isNotBlank(criterio
								.getInstitucionParticipante().getClaveTipoInstitucion()))) {

					if (criterio.getInstitucionParticipante().getId() > DaliConstants.VALOR_COMBO_TODOS) {
						query.append(" AND iopv.idInstitucionReceptora = ? ");
						params.add(new BigInteger(String.valueOf(criterio.getInstitucionParticipante().getId())));
						tipos.add(new BigIntegerType());
					} else {
						query.append(" AND iopv.institucionReceptora.tipoInstitucion.claveTipoInstitucion = ? ");
						params.add(criterio.getInstitucionParticipante().getClaveTipoInstitucion());
						tipos.add(new StringType());
					}
				}

				if (criterio.getCuentaParticipante() != null && StringUtils.isNotBlank(criterio.getCuentaParticipante().getCuenta())) {

					query.append(" AND iopv.cuentaNombradaReceptora.cuenta = ? ");
					params.add(criterio.getCuentaParticipante().getCuenta());
					tipos.add(new StringType());
				}

				query.append(" ) OR ( iopv.idInstitucionBancoTrabajo is not null ");

				if (criterio.getInstitucionContraparte() != null
						&& (criterio.getInstitucionContraparte().getId() > DaliConstants.VALOR_COMBO_TODOS || StringUtils.isNotBlank(criterio
								.getInstitucionContraparte().getClaveTipoInstitucion()))) {

					if (criterio.getInstitucionContraparte().getId() > DaliConstants.VALOR_COMBO_TODOS) {
						query.append(" AND iopv.idInstitucionTraspasante = ? ");
						params.add(new BigInteger(String.valueOf(criterio.getInstitucionContraparte().getId())));
						tipos.add(new BigIntegerType());
					} else {
						query.append(" AND iopv.institucionTraspasante.tipoInstitucion.claveTipoInstitucion = ? ");
						params.add(criterio.getInstitucionContraparte().getClaveTipoInstitucion());
						tipos.add(new StringType());
					}
				}

				if (criterio.getCuentaContraparte() != null && StringUtils.isNotBlank(criterio.getCuentaContraparte().getCuenta())) {

					query.append(" AND iopv.cuentaNombradaTraspasante.cuenta = ? ");
					params.add(criterio.getCuentaContraparte().getCuenta());
					tipos.add(new StringType());
				}

				if (criterio.getInstitucionParticipante() != null
						&& (criterio.getInstitucionParticipante().getId() > DaliConstants.VALOR_COMBO_TODOS || StringUtils.isNotBlank(criterio
								.getInstitucionParticipante().getClaveTipoInstitucion()))) {

					if (criterio.getInstitucionParticipante().getId() > DaliConstants.VALOR_COMBO_TODOS) {
						query.append(" AND iopv.idInstitucionBancoTrabajo = ? ");
						params.add(new BigInteger(String.valueOf(criterio.getInstitucionParticipante().getId())));
						tipos.add(new BigIntegerType());
					} else {
						query.append(" AND iopv.institucionBancoTrabajo.tipoInstitucion.claveTipoInstitucion = ? ");
						params.add(criterio.getInstitucionParticipante().getClaveTipoInstitucion());
						tipos.add(new StringType());
					}
				}

				if (criterio.getCuentaParticipante() != null && StringUtils.isNotBlank(criterio.getCuentaParticipante().getCuenta())) {

					query.append(" AND iopv.cuentaNombradaBancoTrabajo.cuenta = ? ");
					params.add(criterio.getCuentaParticipante().getCuenta());
					tipos.add(new StringType());
				}

				query.append(" ) ) ");
			}

			if (criterio.getRol() == RolConstants.ROL_AMBOS) {
				query.append(" ) ");
			}
		}

		// Tipo de Valor

		if ((criterio.getEmision() != null && criterio.getEmision().getTipoValor() != null && criterio.getEmision().getTipoValor().getId() > DaliConstants.VALOR_COMBO_TODOS)
				|| (criterio.getEmision() != null && criterio.getEmision().getEmisora() != null && criterio.getEmision().getEmisora().getId() > DaliConstants.VALOR_COMBO_TODOS)
				|| (criterio.getEmision() != null && criterio.getEmision().getSerie() != null && !StringUtils.isEmpty(criterio.getEmision().getSerie()
						.getSerie()))) {
			QueryUtil.agregarCondicion(query, params);

			query.append("((");

			if (criterio.getEmision() != null && criterio.getEmision().getTipoValor() != null
					&& criterio.getEmision().getTipoValor().getId() > DaliConstants.VALOR_COMBO_TODOS) {

				query.append(" iopv.cupon.emision.idInstrumento = ? ");
				params.add(new BigInteger(String.valueOf(criterio.getEmision().getTipoValor().getId())));
				tipos.add(new BigIntegerType());
			}

			// emisora
			if (criterio.getEmision() != null && criterio.getEmision().getEmisora() != null
					&& criterio.getEmision().getEmisora().getId() > DaliConstants.VALOR_COMBO_TODOS) {

				if (criterio.getEmision() != null && criterio.getEmision().getTipoValor() != null
						&& criterio.getEmision().getTipoValor().getId() > DaliConstants.VALOR_COMBO_TODOS) {
					query.append(" and ");
				}

				query.append(" iopv.cupon.emision.idEmisora = ? ");
				params.add(new BigInteger(String.valueOf(criterio.getEmision().getEmisora().getId())));
				tipos.add(new BigIntegerType());
			}

			// serie
			if (criterio.getEmision() != null && criterio.getEmision().getSerie() != null && !StringUtils.isEmpty(criterio.getEmision().getSerie().getSerie())) {
				if ((criterio.getEmision() != null && criterio.getEmision().getTipoValor() != null && criterio.getEmision().getTipoValor().getId() > DaliConstants.VALOR_COMBO_TODOS)
						|| (criterio.getEmision() != null && criterio.getEmision().getEmisora() != null && criterio.getEmision().getEmisora().getId() > DaliConstants.VALOR_COMBO_TODOS)) {
					query.append(" and ");
				}

				query.append(" iopv.cupon.emision.serie= ? ");
				params.add(criterio.getEmision().getSerie().getSerie());
				tipos.add(new StringType());
			}

			query.append(") OR (");

			if (criterio.getEmision() != null && criterio.getEmision().getTipoValor() != null
					&& criterio.getEmision().getTipoValor().getId() > DaliConstants.VALOR_COMBO_TODOS) {

				query.append(" iopv.emisionPendiente.tv = ? ");
				params.add(criterio.getEmision().getTipoValor().getClaveTipoValor());
				tipos.add(new StringType());
			}

			// emisora
			if (criterio.getEmision() != null && criterio.getEmision().getEmisora() != null
					&& criterio.getEmision().getEmisora().getId() > DaliConstants.VALOR_COMBO_TODOS) {

				if (criterio.getEmision() != null && criterio.getEmision().getTipoValor() != null
						&& criterio.getEmision().getTipoValor().getId() > DaliConstants.VALOR_COMBO_TODOS) {
					query.append(" and ");
				}

				query.append(" iopv.emisionPendiente.emisora = ? ");
				params.add(criterio.getEmision().getEmisora().getDescripcion());
				tipos.add(new StringType());
			}

			// serie
			if (criterio.getEmision() != null && criterio.getEmision().getSerie() != null && !StringUtils.isEmpty(criterio.getEmision().getSerie().getSerie())) {

				if ((criterio.getEmision() != null && criterio.getEmision().getTipoValor() != null && criterio.getEmision().getTipoValor().getId() > DaliConstants.VALOR_COMBO_TODOS)
						|| (criterio.getEmision() != null && criterio.getEmision().getEmisora() != null && criterio.getEmision().getEmisora().getId() > DaliConstants.VALOR_COMBO_TODOS)) {
					query.append(" and ");
				}
				query.append(" iopv.emisionPendiente.serie = ? ");
				params.add(criterio.getEmision().getSerie().getSerie());
				tipos.add(new StringType());
			}

			query.append("))");
		}
		// Folio Usuario
		if (!StringUtils.isEmpty(criterio.getFolioUsuario())) {
			if(criterio.getInstitucionParticipante().getId() < 0){
				QueryUtil.agregarCondicion(query, params);
				query.append(" ( ");
				query.append(" iopv.folioInstruccionReceptora = ? ");
				query.append(" or ");
				query.append(" iopv.folioInstruccionTraspasante = ? ");
				query.append(" ) ");
				params.add(criterio.getFolioUsuario());
				tipos.add(new StringType());
				params.add(criterio.getFolioUsuario());
				tipos.add(new StringType());
			}
			else{
			QueryUtil.agregarCondicion(query, params);
			// i.getInstruccionOperacionVal().getFolioInstruccionReceptora()
			// i.getInstruccionOperacionVal().getFolioInstruccionTraspasante()
			query.append(" ( ");
			query.append(" (iopv.folioInstruccionReceptora = ? and  " + " iopv.idInstitucionReceptora = ?)  ");
			query.append(" or ");
			query.append(" (iopv.folioInstruccionTraspasante = ? and " + " iopv.idInstitucionTraspasante= ? 	)  ");
			query.append(" ) ");
			params.add(criterio.getFolioUsuario());
			tipos.add(new StringType());
			params.add(new BigInteger(String.valueOf(criterio.getInstitucionParticipante().getId())));
			tipos.add(new BigIntegerType());
			params.add(criterio.getFolioUsuario());
			tipos.add(new StringType());
			params.add(new BigInteger(String.valueOf(criterio.getInstitucionParticipante().getId())));
			tipos.add(new BigIntegerType());
			}
			
		}

		// tipo de mensaje
		if (criterio.getTipoMensaje() != null && criterio.getTipoMensaje().getIdTipoMensaje() != DaliConstants.VALOR_COMBO_TODOS) {
			QueryUtil.agregarCondicion(query, params);
			// i.getInstruccionOperacionVal().getIdTipoMensaje();
			query.append(" iopv.idTipoMensaje = ? ");
			params.add(new BigInteger(String.valueOf(criterio.getTipoMensaje().getIdTipoMensaje())));
			tipos.add(new BigIntegerType());

		}
		// remitente
		if (criterio.isRemitente()) {
			QueryUtil.agregarCondicion(query, params);
			// i.
			query.append(" iopv.mensajeRecibido like ? ");

			params.add("%<FolioInstitucion>" + criterio.getInstitucionParticipante().getFolioInstitucion() + "</FolioInstitucion>%<IdInstitucion>"
					+ criterio.getInstitucionParticipante().getClaveTipoInstitucion() + "</IdInstitucion>%");
			tipos.add(new StringType());

		}

		// origen
		if (!StringUtils.isEmpty(criterio.getOrigen())) {
			QueryUtil.agregarCondicion(query, params);
			query.append(" ( iopv.institucionReceptora.origen = ?  OR iopv.institucionTraspasante.origen = ? )");

			params.add(criterio.getOrigen());
			tipos.add(new StringType());

			params.add(criterio.getOrigen());
			tipos.add(new StringType());
		}

		// error
		if (StringUtils.isNotBlank(criterio.getError().getClaveError())) {
			QueryUtil.agregarCondicion(query, params);
			query.append(" iopv.errorDali.claveError = ? ");
			params.add(criterio.getError().getClaveError());
			tipos.add(new StringType());
		}

		if (!excluirCriteriosParcialidades) {

			// Estado de instruccion
			if (criterio.getEstadoInstruccion() != null && criterio.getEstadoInstruccion().getIdEstadoInstruccion() != DaliConstants.VALOR_COMBO_TODOS) {
				QueryUtil.agregarCondicion(query, params);

				query.append("iopv.idEstadoInstruccion = ? ");
				params.add(new BigInteger(String.valueOf(criterio.getEstadoInstruccion().getIdEstadoInstruccion())));
				tipos.add(new BigIntegerType());
			}

			// cantidad de títulos
			if (NumberUtils.toLong(criterio.getCantidad(), DaliConstants.VALOR_COMBO_TODOS) != DaliConstants.VALOR_COMBO_TODOS) {
				QueryUtil.agregarCondicion(query, params);
				// i.getInstruccionOperacionVal().getCantidadTitulos();
				query.append(" iopv.cantidadTitulos = ? ");
				params.add(NumberUtils.toLong(criterio.getCantidad(), DaliConstants.VALOR_COMBO_TODOS));
				tipos.add(new LongType());
			}

			// monto
			if (NumberUtils.toDouble(criterio.getMonto(), DaliConstants.VALOR_COMBO_TODOS) != DaliConstants.VALOR_COMBO_TODOS) {
				QueryUtil.agregarCondicion(query, params);
				// i.getInstruccionOperacionVal().getImporte()
				query.append(" TO_CHAR(iopv.importe,'FM99999999999999.99') = to_char(?,'FM99999999999999.99') ");
				params.add(new BigDecimal(NumberUtils.toDouble(criterio.getMonto(), DaliConstants.VALOR_COMBO_TODOS)));
				tipos.add(new BigDecimalType());
			}

			// folio control
			if (!StringUtils.isEmpty(criterio.getFolioControl())) {
				QueryUtil.agregarCondicion(query, params);
				// i.getInstruccionOperacionVal().getFolioControl();
				query.append(" iopv.folioControl = ? ");
				params.add(NumberUtils.toLong(criterio.getFolioControl(), DaliConstants.VALOR_COMBO_TODOS));
				tipos.add(new LongType());
			}
			
			// referencia paquete
			if (!StringUtils.isEmpty(criterio.getReferenciaPaquete())) {
				QueryUtil.agregarCondicion(query, params);
				//TODO
				query.append(" upper(trim(iopv.referenciaPaquete)) = upper(trim(?)) "
						+ "and (iopv.estadoInstruccionCat.claveEstadoInstruccion not in ('SM','EP','FV') "
						//+ "and not (iopv.estadoInstruccionCat.claveEstadoInstruccion = 'PE' "
						+ "and iopv.fechaLiquidacion < sysdate)");
				params.add(criterio.getReferenciaPaquete());
				tipos.add(new StringType());
			}
			
			// fecha liquidación
			if (criterio.getFechaLiquidacion() != null) {
				QueryUtil.agregarCondicion(query, params);
				// i.getFechaLiquidacion()
				query.append("trunc(iopv.fechaLiquidacion) = ? ");
				params.add(criterio.getFechaLiquidacion());
				tipos.add(new DateType());
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.persistence.dao.estatus.EstatusOperacionesMatchDAO#consultarInstruccionesMatchSinOperacion(com.indeval.portaldali.middleware.dto.criterio.CriterioMatchOperacionesDTO,
	 *      com.indeval.portaldali.middleware.dto.util.EstadoPaginacionDTO)
	 */
	@SuppressWarnings("unchecked")
	public List<OperacionValorMatchDTO> consultarInstruccionesMatchSinOperacion(final CriterioMatchOperacionesDTO criterio, final EstadoPaginacionDTO paginacion) {

		final StringBuffer query = new StringBuffer();
		final ArrayList<Object> params = new ArrayList<Object>();
		final ArrayList<Type> tipos = new ArrayList<Type>();

		query.append(" FROM " + MensajeBean.class.getName() + " match, ");
		query.append(Institucion.class.getName() + " institucionTraspasante, ");
		query.append(Institucion.class.getName() + " institucionReceptora, ");
		query.append(CuentaNombrada.class.getName() + " cuentaReceptora, ");
		query.append(CuentaNombrada.class.getName() + " cuentaTraspasante ");
		query.append("left outer join fetch match.instrumento ");
		query.append(" WHERE  (institucionTraspasante.tipoInstitucion.claveTipoInstitucion || institucionTraspasante.folioInstitucion ) = match.idFolioTraspasante and ");
		query.append("  (institucionReceptora.tipoInstitucion.claveTipoInstitucion || institucionReceptora.folioInstitucion ) = match.idFolioReceptor  ");
		query.append(" AND  ( cuentaReceptora.cuenta = match.cuentaReceptor and cuentaReceptora.institucion =  institucionReceptora) ");
		query.append(" AND  ( cuentaTraspasante.cuenta = match.cuentaTraspasante and cuentaTraspasante.institucion =  institucionTraspasante) ");
		params.add(new Date());
		crearCriteriosQueryConsultaMatchSinOperacion(query, params, tipos, criterio);
		params.remove(0);

		return (List<OperacionValorMatchDTO>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {

				Query hQuery = session.createQuery(query.toString());

				hQuery.setParameters(params.toArray(), tipos.toArray(new Type[] {}));

				if (paginacion != null) {
					hQuery.setMaxResults(paginacion.getRegistrosPorPagina());
					hQuery.setFetchSize(paginacion.getRegistrosPorPagina());
					hQuery.setFirstResult(((paginacion.getNumeroPagina() - 1) * paginacion.getRegistrosPorPagina()));
				}

				List<Object[]> resultadosBO = hQuery.list();
				List<OperacionValorMatchDTO> resultados = new ArrayList<OperacionValorMatchDTO>();
				// Obtiene la fecha del sistema
				// Pablo Balderas - 05/Agosto/2015
				Date fechaSistema = dateUtilsDao.getDateFechaDB();
				for (Object[] bo : resultadosBO) {
					OperacionValorMatchDTO operacionValor = DTOAssembler.crearOperacionValorMatchDTO((MensajeBean) bo[0], (Institucion) bo[1],
							(Institucion) bo[2], (CuentaNombrada) bo[3], (CuentaNombrada) bo[4], criterio.getInstitucionParticipante(), fechaSistema);

					if ("V".equals(operacionValor.getTipoInstruccion().getNombreCorto()) && operacionValor.getEmision() != null
							&& operacionValor.getEmision().getTipoValor() != null && operacionValor.getEmision().getTipoValor().getMercado() != null
							&& operacionValor.getEmision().getTipoValor().getMercado().getId() == DaliConstants.ID_MERCADO_CAPITALES) {
						operacionValor.setTipoInstruccion(mapeoOperacionInstruccion.get("VC"));
					}
					else {
						operacionValor.setTipoInstruccion(mapeoOperacionInstruccion.get(operacionValor.getTipoInstruccion().getNombreCorto()));
					}
					resultados.add(operacionValor);
				}

				return resultados;
			}
		});

	}

	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.persistence.dao.estatus.EstatusOperacionesMatchDAO#consultarInstruccionesMatchSinOperacionPorId(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public List<OperacionValorMatchDTO> consultarInstruccionesMatchSinOperacionPorId(final Long idBitacoraMatch) {

		final StringBuffer query = new StringBuffer();
		final ArrayList<Object> params = new ArrayList<Object>();
		final ArrayList<Type> tipos = new ArrayList<Type>();

		query.append(" FROM " + MensajeBean.class.getName() + " match, ");
		query.append(Institucion.class.getName() + " institucionTraspasante, ");
		query.append(Institucion.class.getName() + " institucionReceptora, ");
		query.append(CuentaNombrada.class.getName() + " cuentaReceptora, ");
		query.append(CuentaNombrada.class.getName() + " cuentaTraspasante ");
		query.append("left outer join fetch match.instrumento ");
		query
				.append(" WHERE  (institucionTraspasante.tipoInstitucion.claveTipoInstitucion || institucionTraspasante.folioInstitucion ) = match.idFolioTraspasante and ");
		query.append("  (institucionReceptora.tipoInstitucion.claveTipoInstitucion || institucionReceptora.folioInstitucion ) = match.idFolioReceptor  ");
		query.append(" AND  ( cuentaReceptora.cuenta = match.cuentaReceptor and cuentaReceptora.institucion =  institucionReceptora) ");
		query.append(" AND  ( cuentaTraspasante.cuenta = match.cuentaTraspasante and cuentaTraspasante.institucion =  institucionTraspasante) ");

		query.append(" AND match.idInstruccion = ? ");
		params.add(new BigInteger(String.valueOf(idBitacoraMatch)));
		tipos.add(new BigIntegerType());

		return (List<OperacionValorMatchDTO>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {

				Query hQuery = session.createQuery(query.toString());

				hQuery.setParameters(params.toArray(), tipos.toArray(new Type[] {}));

				List<Object[]> resultadosBO = hQuery.list();
				List<OperacionValorMatchDTO> resultados = new ArrayList<OperacionValorMatchDTO>();
				// Obtiene la fecha del sistema
				// Pablo Balderas - 05/Agosto/2015
				Date fechaSistema = dateUtilsDao.getDateFechaDB();
				for (Object[] bo : resultadosBO) {
					OperacionValorMatchDTO operacionValor = DTOAssembler.crearOperacionValorMatchDTO((MensajeBean) bo[0], (Institucion) bo[1],
							(Institucion) bo[2], (CuentaNombrada) bo[3], (CuentaNombrada) bo[4], null, fechaSistema);

					if ("V".equals(operacionValor.getTipoInstruccion().getNombreCorto()) && operacionValor.getEmision() != null
							&& operacionValor.getEmision().getTipoValor() != null && operacionValor.getEmision().getTipoValor().getMercado() != null
							&& operacionValor.getEmision().getTipoValor().getMercado().getId() == DaliConstants.ID_MERCADO_CAPITALES) {
						operacionValor.setTipoInstruccion(mapeoOperacionInstruccion.get("VC"));
					} else {
						operacionValor.setTipoInstruccion(mapeoOperacionInstruccion.get(operacionValor.getTipoInstruccion().getNombreCorto()));
					}
					resultados.add(operacionValor);
				}

				return resultados;
			}
		});

	}

	/**
	 * Construye el query con los criterios necesarios para consultar las
	 * instrucciones de match según los parámetros de criterios enviados.
	 * 
	 * @param query
	 *            Query donde se pone el resultado
	 * @param params
	 *            Lista de parámetros
	 * @param tipos
	 *            Lista de tipos
	 * @param criterio
	 *            Criterios a utilizar
	 */
	private void crearCriteriosQueryConsultaMatchSinOperacion(StringBuffer query, ArrayList<Object> params, ArrayList<Type> tipos,
			CriterioMatchOperacionesDTO criterio) {

		if (criterio.getEstadoInstruccion().getIdEstadoInstruccion() > DaliConstants.VALOR_COMBO_TODOS) {

			if (criterio.getEstadoInstruccion().getIdEstadoInstruccion() == EstadoInstruccionConstants.SIN_MATCH) {
				QueryUtil.agregarCondicion(query, params);
				query.append(" match.estadoMensajeString IN ( ?, ?, ? ) ");
				params.add(EstadoInstruccion.SIN_MATCH.toString());
				tipos.add(new StringType());
				params.add(EstadoInstruccion.PRE_MATCH.toString());
				tipos.add(new StringType());
				params.add(EstadoInstruccion.POSIBLE_MATCH.toString());
				tipos.add(new StringType());
			}
			if (criterio.getEstadoInstruccion().getIdEstadoInstruccion() == EstadoInstruccionConstants.CANCELADA) {
				QueryUtil.agregarCondicion(query, params);
				query.append(" match.estadoMensajeString IN ( ?, ? ) ");
				params.add(EstadoInstruccion.CANCELADA.toString());
				tipos.add(new StringType());
				params.add(EstadoInstruccion.CANCELADA_NO_REQUIERE_MATCH.toString());
				tipos.add(new StringType());
			}
		} else {
			QueryUtil.agregarCondicion(query, params);
			query.append(" match.estadoMensajeString NOT IN (?, ?, ?) ");
			params.add(EstadoInstruccion.CON_MATCH.toString());
			tipos.add(new StringType());
			params.add(EstadoInstruccion.CANCELAR_APLICADO.toString());
			tipos.add(new StringType());
			params.add(EstadoInstruccion.CANCELAR_NO_APLICADO.toString());
			tipos.add(new StringType());
		}

		if (criterio.getMercado() != null && criterio.getMercado().getId() != DaliConstants.VALOR_COMBO_TODOS) {
			QueryUtil.agregarCondicion(query, params);

			if (criterio.getMercado().getId() == DaliConstants.ID_MERCADO_DINERO) {
				query.append(" match.instrumento.idMercado IN (?,?) ");
				params.add(new BigInteger(String.valueOf(DaliConstants.ID_MERCADO_PAPEL_BANCARIO)));
				tipos.add(new BigIntegerType());
				params.add(new BigInteger(String.valueOf(DaliConstants.ID_MERCADO_PAPEL_GUBER)));
				tipos.add(new BigIntegerType());
			} else {
				query.append(" match.instrumento.idMercado = ? ");
				params.add(new BigInteger(String.valueOf(criterio.getMercado().getId())));
				tipos.add(new BigIntegerType());
			}

		}
		
		//Divisa
		if (criterio.getDivisa()!= null && criterio.getDivisa().getId() != DaliConstants.VALOR_COMBO_TODOS  
					&&  StringUtils.isNotEmpty(criterio.getDivisa().getClaveAlfabetica()) ) {
			QueryUtil.agregarCondicion(query, params);
 
			query.append(" match.divisa = ? ");
			params.add(criterio.getDivisa().getClaveAlfabetica());
			tipos.add(new StringType());  

		}
		
		//Boveda efectivo
		if (criterio.getBovedaEfectivo()!= null && criterio.getBovedaEfectivo().getId() != DaliConstants.VALOR_COMBO_TODOS  
					&&  StringUtils.isNotEmpty(criterio.getBovedaEfectivo().getNombreCorto()) ) {
			QueryUtil.agregarCondicion(query, params);
 
			query.append(" match.bovedaEfectivo = ? ");
			params.add(criterio.getBovedaEfectivo().getNombreCorto());
			tipos.add(new StringType());  

		}
		
		//Boveda valores
		if (criterio.getBovedaValores()!= null && criterio.getBovedaValores().getId() != DaliConstants.VALOR_COMBO_TODOS  
					&&  StringUtils.isNotEmpty(criterio.getBovedaValores().getNombreCorto()) ) {
			QueryUtil.agregarCondicion(query, params);
 
			query.append(" match.boveda = ? ");
			params.add(criterio.getBovedaValores().getNombreCorto());
			tipos.add(new StringType());  

		}

		// folio usuario
		if (!StringUtils.isEmpty(criterio.getFolioUsuario())) {

			QueryUtil.agregarCondicion(query, params);
			// m.getFolioInstruccion()
			query.append(" match.folioInstruccion = ? ");
			params.add(criterio.getFolioUsuario());
			tipos.add(new StringType());
		}

		// tipo operacion (instruccion)
		if (criterio.getTipoInstruccion() != null && criterio.getTipoInstruccion().getIdTipoInstruccion() != DaliConstants.VALOR_COMBO_TODOS) {
			QueryUtil.agregarCondicion(query, params);

			String tipoOperacion = obtenerTipoDeOperacionEquivalenteATipoInstruccion(criterio.getTipoInstruccion().getNombreCorto());

			query.append(" match.tipoOperacionInstruccion = ? ");
			params.add(tipoOperacion);
			tipos.add(new StringType());
			if (criterio.getTipoInstruccion().getNombreCorto().equals("DVPC")) {
				query.append(" AND match.instrumento.idMercado = ? ");
				params.add(new BigInteger(String.valueOf(DaliConstants.ID_MERCADO_CAPITALES)));
				tipos.add(new BigIntegerType());
			}
			if (criterio.getTipoInstruccion().getNombreCorto().equals("COVE")) {
				query.append(" AND match.instrumento.idMercado IN (?,?) ");
				params.add(new BigInteger(String.valueOf(DaliConstants.ID_MERCADO_PAPEL_BANCARIO)));
				tipos.add(new BigIntegerType());
				params.add(new BigInteger(String.valueOf(DaliConstants.ID_MERCADO_PAPEL_GUBER)));
				tipos.add(new BigIntegerType());
			}
		}

		// tipo mensaje
		if (criterio.getTipoMensaje() != null && criterio.getTipoMensaje().getIdTipoMensaje() != DaliConstants.VALOR_COMBO_TODOS) {
			QueryUtil.agregarCondicion(query, params);
			// m.getTipoMensaje()
			query.append(" match.tipoMensaje = ? ");
			params.add(criterio.getTipoMensaje().getClaveTipoMensaje());
			tipos.add(new StringType());

		}

		// fecha Concertacion (registro)
		if (criterio.getFechaConcertacion() != null) {
			QueryUtil.agregarCondicion(query, params);
			// m.getFechaHoraRegistro
			query.append(" trunc(match.fechaHoraRegistro) = ? ");
			params.add(criterio.getFechaConcertacion());
			tipos.add(new DateType());
		}

		// fecha liquidación
		if (criterio.getFechaLiquidacion() != null) {
			QueryUtil.agregarCondicion(query, params);
			// m.getFechaLiquidacion()
			query.append(" trunc(match.fechaLiquidacion) = ? ");
			params.add(criterio.getFechaLiquidacion());
			tipos.add(new DateType());
		}

		// Roles
		boolean existenCriteriosTraspORecep = (criterio.getInstitucionParticipante() != null && (criterio.getInstitucionParticipante().getId() > DaliConstants.VALOR_COMBO_TODOS || StringUtils
				.isNotBlank(criterio.getInstitucionParticipante().getClaveTipoInstitucion())))
				|| (criterio.getCuentaParticipante() != null && StringUtils.isNotBlank(criterio.getCuentaParticipante().getCuenta()))
				|| (criterio.getInstitucionContraparte() != null && (criterio.getInstitucionContraparte().getId() > DaliConstants.VALOR_COMBO_TODOS || StringUtils
						.isNotBlank(criterio.getInstitucionContraparte().getClaveTipoInstitucion())))
				|| (criterio.getCuentaContraparte() != null && StringUtils.isNotBlank(criterio.getCuentaContraparte().getCuenta()));

		if (existenCriteriosTraspORecep) {
			QueryUtil.agregarCondicion(query, params);

			if (criterio.getRol() == RolConstants.ROL_AMBOS) {
				query.append(" ( ");
			}

			if (criterio.getRol() == RolConstants.ROL_AMBOS || criterio.getRol() == RolConstants.ROL_TRASPASANTE) {

				query.append(" ( ");

				if (criterio.getInstitucionParticipante() != null
						&& (criterio.getInstitucionParticipante().getId() > DaliConstants.VALOR_COMBO_TODOS || StringUtils.isNotBlank(criterio
								.getInstitucionParticipante().getClaveTipoInstitucion()))) {

					if (criterio.getInstitucionParticipante().getId() > DaliConstants.VALOR_COMBO_TODOS) {
						query.append("match.idFolioTraspasante = ? ");
						params.add(criterio.getInstitucionParticipante().getClaveTipoInstitucion()
								+ criterio.getInstitucionParticipante().getFolioInstitucion());
						tipos.add(new StringType());
					} else {
						query.append("substr(match.idFolioTraspasante,1,2) = ? ");
						params.add(criterio.getInstitucionParticipante().getClaveTipoInstitucion());
						tipos.add(new StringType());
					}
				}

				if (criterio.getCuentaParticipante() != null && StringUtils.isNotBlank(criterio.getCuentaParticipante().getCuenta())) {

					if (criterio.getInstitucionParticipante() != null
							&& (criterio.getInstitucionParticipante().getId() > DaliConstants.VALOR_COMBO_TODOS || StringUtils.isNotBlank(criterio
									.getInstitucionParticipante().getClaveTipoInstitucion()))) {
						query.append("and ");
					}

					query.append("match.cuentaTraspasante = ? ");
					params.add(criterio.getCuentaParticipante().getCuenta());
					tipos.add(new StringType());
				}

				if (criterio.getInstitucionContraparte() != null
						&& (criterio.getInstitucionContraparte().getId() > DaliConstants.VALOR_COMBO_TODOS || StringUtils.isNotBlank(criterio
								.getInstitucionContraparte().getClaveTipoInstitucion()))) {

					if ((criterio.getInstitucionParticipante() != null && (criterio.getInstitucionParticipante().getId() > DaliConstants.VALOR_COMBO_TODOS || StringUtils
							.isNotBlank(criterio.getInstitucionParticipante().getClaveTipoInstitucion())))
							|| (criterio.getCuentaParticipante() != null && StringUtils.isNotBlank(criterio.getCuentaParticipante().getCuenta()))) {
						query.append("and ");
					}

					if (criterio.getInstitucionContraparte().getId() > DaliConstants.VALOR_COMBO_TODOS) {
						query.append("match.idFolioReceptor = ? ");
						params.add(criterio.getInstitucionContraparte().getClaveTipoInstitucion() + criterio.getInstitucionContraparte().getFolioInstitucion());
						tipos.add(new StringType());
					} else {
						query.append("substr(match.idFolioReceptor,1,2) = ? ");
						params.add(criterio.getInstitucionContraparte().getClaveTipoInstitucion());
						tipos.add(new StringType());
					}
				}

				if (criterio.getCuentaContraparte() != null && StringUtils.isNotBlank(criterio.getCuentaContraparte().getCuenta())) {

					if (criterio.getInstitucionParticipante() != null
							&& (criterio.getInstitucionParticipante().getId() > DaliConstants.VALOR_COMBO_TODOS || StringUtils.isNotBlank(criterio
									.getInstitucionParticipante().getClaveTipoInstitucion()))
							|| (criterio.getInstitucionContraparte() != null && (criterio.getInstitucionContraparte().getId() > DaliConstants.VALOR_COMBO_TODOS || StringUtils
									.isNotBlank(criterio.getInstitucionContraparte().getClaveTipoInstitucion())))
							|| (criterio.getCuentaParticipante() != null && StringUtils.isNotBlank(criterio.getCuentaParticipante().getCuenta()))) {
						query.append("and ");
					}

					query.append("match.cuentaReceptor = ? ");
					params.add(criterio.getCuentaContraparte().getCuenta());
					tipos.add(new StringType());
				}

				query.append(" ) ");
			}

			if (criterio.getRol() == RolConstants.ROL_AMBOS) {
				query.append(" or ");
			}

			if (criterio.getRol() == RolConstants.ROL_AMBOS || criterio.getRol() == RolConstants.ROL_RECEPTOR) {

				query.append(" ( ");

				if (criterio.getInstitucionContraparte() != null
						&& (criterio.getInstitucionContraparte().getId() > DaliConstants.VALOR_COMBO_TODOS || StringUtils.isNotBlank(criterio
								.getInstitucionContraparte().getClaveTipoInstitucion()))) {

					if (criterio.getInstitucionContraparte().getId() > DaliConstants.VALOR_COMBO_TODOS) {

						query.append("match.idFolioTraspasante = ? ");
						params.add(criterio.getInstitucionContraparte().getClaveTipoInstitucion() + criterio.getInstitucionContraparte().getFolioInstitucion());
						tipos.add(new StringType());
					} else {
						query.append("substr(match.idFolioTraspasante,1,2) = ? ");
						params.add(criterio.getInstitucionContraparte().getClaveTipoInstitucion());
						tipos.add(new StringType());
					}
				}

				if (criterio.getCuentaContraparte() != null && StringUtils.isNotBlank(criterio.getCuentaContraparte().getCuenta())) {

					if ((criterio.getInstitucionContraparte() != null && (criterio.getInstitucionContraparte().getId() > DaliConstants.VALOR_COMBO_TODOS || StringUtils
							.isNotBlank(criterio.getInstitucionContraparte().getClaveTipoInstitucion())))) {
						query.append("and ");
					}

					query.append("match.cuentaTraspasante = ? ");
					params.add(criterio.getCuentaContraparte().getCuenta());
					tipos.add(new StringType());
				}

				if (criterio.getInstitucionParticipante() != null
						&& (criterio.getInstitucionParticipante().getId() > DaliConstants.VALOR_COMBO_TODOS || StringUtils.isNotBlank(criterio
								.getInstitucionParticipante().getClaveTipoInstitucion()))) {

					if ((criterio.getInstitucionContraparte() != null && (criterio.getInstitucionContraparte().getId() > DaliConstants.VALOR_COMBO_TODOS || StringUtils
							.isNotBlank(criterio.getInstitucionContraparte().getClaveTipoInstitucion())))
							|| (criterio.getCuentaContraparte() != null && StringUtils.isNotBlank(criterio.getCuentaContraparte().getCuenta()))) {
						query.append("and ");
					}
					if (criterio.getInstitucionParticipante().getId() > DaliConstants.VALOR_COMBO_TODOS) {
						query.append("match.idFolioReceptor = ? ");
						params.add(criterio.getInstitucionParticipante().getClaveTipoInstitucion()
								+ criterio.getInstitucionParticipante().getFolioInstitucion());
						tipos.add(new StringType());
					} else {
						query.append("substr(match.idFolioReceptor,1,2) = ? ");
						params.add(criterio.getInstitucionParticipante().getClaveTipoInstitucion());
						tipos.add(new StringType());
					}
				}

				if (criterio.getCuentaParticipante() != null && StringUtils.isNotBlank(criterio.getCuentaParticipante().getCuenta())) {

					if ((criterio.getInstitucionParticipante() != null && (criterio.getInstitucionParticipante().getId() > DaliConstants.VALOR_COMBO_TODOS || StringUtils
							.isNotBlank(criterio.getInstitucionParticipante().getClaveTipoInstitucion())))
							|| (criterio.getInstitucionContraparte() != null && (criterio.getInstitucionContraparte().getId() > DaliConstants.VALOR_COMBO_TODOS || StringUtils
									.isNotBlank(criterio.getInstitucionContraparte().getClaveTipoInstitucion())))
							|| (criterio.getCuentaContraparte() != null && StringUtils.isNotBlank(criterio.getCuentaContraparte().getCuenta()))) {
						query.append("and ");
					}

					query.append("match.cuentaReceptor = ? ");
					params.add(criterio.getCuentaParticipante().getCuenta());
					tipos.add(new StringType());
				}

				query.append(" ) ");
			}

			if (criterio.getRol() == RolConstants.ROL_AMBOS) {
				query.append(" ) ");
			}
		}

		// Tipo de Valor

		if (criterio.getEmision() != null && criterio.getEmision().getTipoValor() != null
				&& criterio.getEmision().getTipoValor().getId() > DaliConstants.VALOR_COMBO_TODOS) {
			QueryUtil.agregarCondicion(query, params);
			// m.getTipoValor()
			query.append(" match.tipoValor = ? ");
			params.add(criterio.getEmision().getTipoValor().getClaveTipoValor());
			tipos.add(new StringType());
		}

		// emisora
		if (criterio.getEmision() != null && criterio.getEmision().getEmisora() != null
				&& criterio.getEmision().getEmisora().getId() > DaliConstants.VALOR_COMBO_TODOS) {
			QueryUtil.agregarCondicion(query, params);
			// m.getEmisora()
			query.append(" match.emisora = ? ");
			params.add(criterio.getEmision().getEmisora().getDescripcion());
			tipos.add(new StringType());
		}

		// serie
		if (criterio.getEmision() != null && criterio.getEmision().getSerie() != null && !StringUtils.isEmpty(criterio.getEmision().getSerie().getSerie())) {
			QueryUtil.agregarCondicion(query, params);
			// m.getSerie()
			query.append(" match.serie= ? ");
			params.add(criterio.getEmision().getSerie().getSerie());
			tipos.add(new StringType());
		}

		// remitente

		if (criterio.isRemitente()) {
			QueryUtil.agregarCondicion(query, params);
			// m.getXml()
			query.append(" match.xml like ? ");

			params.add("%<FolioInstitucion>" + criterio.getInstitucionParticipante().getFolioInstitucion() + "</FolioInstitucion>%<IdInstitucion>"
					+ criterio.getInstitucionParticipante().getClaveTipoInstitucion() + "</IdInstitucion>%");
			tipos.add(new StringType());

		}

		// origen
		if (!StringUtils.isEmpty(criterio.getOrigen())) {
			QueryUtil.agregarCondicion(query, params);
			query.append(" ( match.origen = ? )");

			params.add(criterio.getOrigen());
			tipos.add(new StringType());
		}

		// cantidad de títulos
		if (NumberUtils.toLong(criterio.getCantidad(), DaliConstants.VALOR_COMBO_TODOS) != DaliConstants.VALOR_COMBO_TODOS) {
			QueryUtil.agregarCondicion(query, params);
			// m.getCantidadTitulos();
			query.append(" match.cantidadTitulos = ? ");
			params.add(NumberUtils.toLong(criterio.getCantidad(), DaliConstants.VALOR_COMBO_TODOS));
			tipos.add(new LongType());
		}

		// monto
		if (NumberUtils.toDouble(criterio.getMonto(), DaliConstants.VALOR_COMBO_TODOS) != DaliConstants.VALOR_COMBO_TODOS) {
			QueryUtil.agregarCondicion(query, params);
			// m.getImporte()
			query.append(" TO_CHAR(to_number(match.importe),'FM99999999999999.99') = to_char(?,'FM99999999999999.99') ");
			params.add(new BigDecimal(NumberUtils.toDouble(criterio.getMonto(), DaliConstants.VALOR_COMBO_TODOS)));
			tipos.add(new BigDecimalType());
		}

		// referencia paquete
		if (!StringUtils.isEmpty(criterio.getReferenciaPaquete())) {
			QueryUtil.agregarCondicion(query, params);
			//TODO
			query.append(" upper(trim(match.referenciaPaquete)) = upper(trim(?)) "
					+ "and (match.estadoMensajeString not in ('SIN_MATCH', 'CON_MATCH',"
					+ "'SIN_MATCH', 'CANCELADA', 'CANCELAR_APLICADO', "
					+ "'CANCELAR_NO_APLICADO', 'POSIBLE_MATCH') "	//de hecho ningun estado
//					+ "and not (match.estadoMensajeString = 'PE' "
					+ "and match.fechaLiquidacion < sysdate)");
			params.add(criterio.getReferenciaPaquete());
			tipos.add(new StringType());
		}
		
		// folio control
		if (!StringUtils.isEmpty(criterio.getFolioControl())) {
			QueryUtil.agregarCondicion(query, params);
			// /m.getFolioControl();
			query.append(" match.folioControl = ? ");
			params.add(criterio.getFolioControl());
			tipos.add(new StringType());
		}

	}

	/**
	 * Obtiene el tipo de operación equivalente a un tipo de instrucción.
	 * 
	 * @param tipoInstruccion
	 *            el tipo de instrucción del cual se desea obtener la
	 *            equivalencia.
	 * @return una cadena con el tipo de operación equivalente a un tipo de
	 *         instrucción.
	 */
	private String obtenerTipoDeOperacionEquivalenteATipoInstruccion(String tipoInstruccion) {
		String tipoOperacion = null;

		for (String llave : mapeoOperacionInstruccion.keySet()) {
			if (mapeoOperacionInstruccion.get(llave).getNombreCorto().equals(tipoInstruccion)) {
				tipoOperacion = llave;
				if (tipoOperacion.equals("VC")) {
					tipoOperacion = "V";
				}
			}
		}

		return tipoOperacion;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.persistence.dao.estatus.EstatusOperacionesMatchDAO#obtenerProyeccionConsultaInstruccionesMatchSinOperacion(com.indeval.portaldali.middleware.dto.criterio.CriterioMatchOperacionesDTO)
	 */
	public long obtenerProyeccionConsultaInstruccionesMatchSinOperacion(CriterioMatchOperacionesDTO criterio) {
		final StringBuffer query = new StringBuffer();
		final ArrayList<Object> params = new ArrayList<Object>();
		final ArrayList<Type> tipos = new ArrayList<Type>();
		
		query.append("SELECT COUNT(*) FROM " + MensajeBean.class.getName() + " match ");
		
		crearCriteriosQueryConsultaMatchSinOperacion(query, params, tipos, criterio);
		
		return (Long) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				
				Query hQuery = session.createQuery(query.toString());
				
				hQuery.setParameters(params.toArray(), tipos.toArray(new Type[] {}));
				
				Number res = (Number) hQuery.uniqueResult();
				
				return res.longValue();
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.persistence.dao.estatus.EstatusOperacionesMatchDAO#consultarInstruccionesMatchConOperacion(com.indeval.portaldali.middleware.dto.criterio.CriterioMatchOperacionesDTO,
	 *      com.indeval.portaldali.middleware.dto.util.EstadoPaginacionDTO)
	 */
	@SuppressWarnings("unchecked")
	public List<OperacionValorMatchDTO> consultarInstruccionesMatchConOperacion(final CriterioMatchOperacionesDTO criterio, final EstadoPaginacionDTO paginacion) {

		final StringBuffer query = new StringBuffer();
		final ArrayList<Object> params = new ArrayList<Object>();
		final ArrayList<Type> tipos = new ArrayList<Type>();

		query.append("FROM " + InstruccionOperacionVal.class.getName() + "  iopv,  " + MensajeBean.class.getName() + " match ");

		query.append(" WHERE match.idFolioReceptor = " + "iopv.institucionReceptora.tipoInstitucion.claveTipoInstitucion||"
				+ "iopv.institucionReceptora.folioInstitucion and ");
		query.append(" match.cuentaReceptor =  iopv.cuentaNombradaReceptora.cuenta and ");

		query.append(" match.idFolioTraspasante =  " + "iopv.institucionTraspasante.tipoInstitucion.claveTipoInstitucion||"
				+ "iopv.institucionTraspasante.folioInstitucion and ");
		query.append(" match.cuentaTraspasante =  iopv.cuentaNombradaTraspasante.cuenta and ");

		query.append(" match.tipoValor = iopv.cupon.emision.instrumento.claveTipoValor and ");
		query.append(" match.emisora = iopv.cupon.emision.emisora.descripcion and ");
		query.append(" match.serie = iopv.cupon.emision.serie and ");
		query.append(" match.cantidadTitulos = iopv.cantidadTitulos  and ");

		query.append(" match.tipoMensaje= iopv.tipoMensajeCat.claveTipoMensaje  and ");

		query.append(" match.fechaLiquidacion = iopv.fechaLiquidacion  and ");

		query.append(" ( ");

		query.append(" ( match.folioInstruccion = iopv.folioInstruccionReceptora  and "
				+ "match.folioInstruccionMatch = iopv.folioInstruccionTraspasante   and " + "match.tipoMensaje in ('540','541') ) or ");

		query.append(" ( match.folioInstruccion = iopv.folioInstruccionTraspasante and "
				+ "	 match.folioInstruccionMatch = iopv.folioInstruccionReceptora and " + " match.tipoMensaje in ('542','543')) ");

		query.append(" ) ");

		params.add(new Date());

		crearCriteriosQueryConsultaOperacionesValor(query, params, tipos, criterio, false);

		params.remove(0);

		return (List<OperacionValorMatchDTO>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {

				Query hQuery = session.createQuery(query.toString());

				hQuery.setParameters(params.toArray(), tipos.toArray(new Type[] {}));

				if (paginacion != null) {
					hQuery.setMaxResults(paginacion.getRegistrosPorPagina());
					hQuery.setFetchSize(paginacion.getRegistrosPorPagina());
					hQuery.setFirstResult(((paginacion.getNumeroPagina() - 1) * paginacion.getRegistrosPorPagina()));
				}

				List<Object[]> resultadosBO = hQuery.list();
				// mapa para evitar los registros de operación repetidos
				Map<Long, InstruccionOperacionVal> identificadoresOperacion = new HashMap<Long, InstruccionOperacionVal>();

				List<OperacionValorMatchDTO> resultados = new ArrayList<OperacionValorMatchDTO>();
				// Obtiene la fecha del sistema
				// Pablo Balderas - 05/Agosto/2015
				Date fechaSistema = dateUtilsDao.getDateFechaDB();
				for (Object[] bo : resultadosBO) {
					if (identificadoresOperacion.get(((InstruccionOperacionVal) bo[0]).getIdInstruccionOperacionVal()) == null) {
						resultados.addAll(DTOAssembler.crearOperacionValorMatchDTO((InstruccionOperacionVal) bo[0], (MensajeBean) bo[1], criterio
								.getInstitucionParticipante(), criterio.getEstadoInstruccion() != null
								&& (criterio.getEstadoInstruccion().getIdEstadoInstruccion() == EstadoInstruccionConstants.INSTRUCCION_LIQUIDADA || criterio
										.getEstadoInstruccion().getIdEstadoInstruccion() == DaliConstants.VALOR_COMBO_TODOS),
								criterio.getEstadoInstruccion() != null ? criterio.getEstadoInstruccion().getIdEstadoInstruccion()
										: DaliConstants.VALOR_COMBO_TODOS, criterio.getRol(), false, fechaSistema));
						identificadoresOperacion.put(((InstruccionOperacionVal) bo[0]).getIdInstruccionOperacionVal().longValue(),
								(InstruccionOperacionVal) bo[0]);
					}

				}

				return resultados;
			}
		});

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.persistence.dao.estatus.EstatusOperacionesMatchDAO#consultarInstruccionOperacionValorPorId(long)
	 */
	@SuppressWarnings("unchecked")
	public List<OperacionValorMatchDTO> consultarInstruccionOperacionValorPorId(long idInstruccion) {
		StringBuffer query = new StringBuffer();

		query.append("FROM " + InstruccionOperacionVal.class.getName() + " iopv ");
		query.append("left outer join fetch iopv.cupon.emision ");
		query.append("left outer join fetch iopv.cupon.emision.emisora ");
		query.append("left outer join fetch iopv.cupon.emision.instrumento ");
		query.append("left outer join fetch iopv.errorDali ");
		query.append("left outer join fetch iopv.cuentaNombradaBancoTrabajo ");
		query.append("left outer join fetch iopv.cuentaNombradaReceptora ");
		query.append("left outer join fetch iopv.cuentaNombradaTraspasante ");
		query.append("left outer join fetch iopv.cupon ");
		query.append("left outer join fetch iopv.divisa ");
		query.append("left outer join fetch iopv.emisionPendiente ");
		query.append("left outer join fetch iopv.emisionPendiente.estadoEmision ");
		query.append("left outer join fetch iopv.parcialidadesLiquidacion  ");
		query.append("left outer join fetch iopv.estadoInstruccionCat ");
		query.append("left outer join fetch iopv.institucionBancoTrabajo ");
		query.append("left outer join fetch iopv.institucionReceptora ");
		query.append("left outer join fetch iopv.institucionTraspasante ");
		query.append("left outer join fetch iopv.tipoInstruccion ");
		query.append("left outer join fetch iopv.tipoMensajeCat ");
		query.append("left outer join fetch iopv.operacionMiscelaneaFiscal ");
		query.append(" WHERE iopv.idInstruccionOperacionVal = ?");

		List<InstruccionOperacionVal> resultadosBO = getHibernateTemplate().find(query.toString(), new BigInteger(String.valueOf(idInstruccion)));
		List<OperacionValorMatchDTO> resultado = new ArrayList<OperacionValorMatchDTO>();
		if (resultadosBO != null && resultadosBO.size() > 0) {
			Collection<OperacionValorMatchDTO> resultados = DTOAssembler.crearOperacionValorMatchDTOParcialidades(resultadosBO.get(0), null, null, true,
					DaliConstants.VALOR_COMBO_TODOS, RolConstants.ROL_AMBOS, false, dateUtilsDao.getDateFechaDB());
			resultado.addAll(resultados);
		}
		return resultado;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.persistence.dao.estatus.EstatusOperacionesMatchDAO#verificarSiOperacionTieneParcialidades(java.util.List)
	 */
	@SuppressWarnings("unchecked")
	public Map<Long, Boolean> verificarSiOperacionTieneParcialidades(CriterioMatchOperacionesDTO criterio) {

		final Map<Long, Boolean> mapaIndicadorParcialidades = new HashMap<Long, Boolean>();
		final ArrayList<Object> params = new ArrayList<Object>();
		final ArrayList<Type> tipos = new ArrayList<Type>();

		final StringBuffer query = new StringBuffer();

		query.append("SELECT iopv.idInstruccionOperacionVal, ");
		query.append(" (SELECT COUNT(*) FROM " + OperacionNombrada.class.getName() + " opn ");
		query.append("  WHERE opn.instruccion.folioInstruccionLiquidacion = iopv.idInstruccionOperacionVal "
				+ " and (iopv.idCuentaNombradaReceptora  = opn.idCuentaNombradaReceptor  and "
				+ " iopv.idCuentaNombradaTraspasante = opn.idCuentaNombradaTraspasante)) ");
		query.append(" FROM " + InstruccionOperacionVal.class.getName() + " iopv ");

		crearCriteriosQueryConsultaOperacionesValor(query, params, tipos, criterio, false);

		return (Map<Long, Boolean>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {

				Object[] resultado = null;

				Query hQuery = session.createQuery(query.toString());
				hQuery.setParameters(params.toArray(), tipos.toArray(new Type[] {}));
				Iterator<Object[]> resultadosConsulta = hQuery.list().iterator();

				while (resultadosConsulta.hasNext()) {
					resultado = resultadosConsulta.next();
					mapaIndicadorParcialidades.put(new Long(((BigInteger) resultado[0]).longValue()), ((Number) resultado[1]).longValue() > 1);
				}

				return mapaIndicadorParcialidades;
			}
		});

	}

	private Date horaInicioFin(Date hora, boolean inicio) {
		if (hora == null) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(hora);

		if (inicio) {
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
		} else {
			cal.set(Calendar.HOUR_OF_DAY, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
			cal.set(Calendar.MILLISECOND, 999);
		}

		return cal.getTime();
	}

    /**
     * Arma el query con sus parametros y sus tipos de acuerdo a los criterios de busqueda de la consulta de operaciones
     * valor y de las parcialidades.
     * @param criterio Los criterios de busqueda. 
     * @param strQuery La cadena de query a armar. 
     * @param parametros Los valores de los parametros. 
     * @param tipos Los tipos de los parametros. 
     */
    private void armarQueryConsultaOperacionesValorYParcialidades(CriterioMatchOperacionesExportacionDTO criterio, 
                                                                  StringBuilder strQuery, 
                                                                  List<Object> parametros, List<Type> tipos) {
        // Tipo de mercado
        if (criterio.getIdMercado() != DaliConstants.VALOR_COMBO_TODOS) {
            if (criterio.getIdMercado() == DaliConstants.ID_MERCADO_DINERO) {
                strQuery.append("and ID_MERCADO IN (?,?) ");
                parametros.add(DaliConstants.ID_MERCADO_PAPEL_BANCARIO);
                tipos.add(new LongType());
                parametros.add(DaliConstants.ID_MERCADO_PAPEL_GUBER);
                tipos.add(new LongType());
            } 
            else {
                strQuery.append("and ID_MERCADO = ? ");
                parametros.add(new BigInteger(String.valueOf(criterio.getIdMercado())));
                tipos.add(new BigIntegerType());
            }
        }

        // Tipo Operacion (Instruccion)
        if (criterio.getIdTipoInstruccion() != null && criterio.getIdTipoInstruccion().longValue() != DaliConstants.VALOR_COMBO_TODOS) {
            strQuery.append("and ID_TIPO_INSTRUCCION = ? ");
            parametros.add(new BigInteger(String.valueOf(criterio.getIdTipoInstruccion().longValue())));
            tipos.add(new BigIntegerType());
        }

        // Fecha Concertacion
        if (criterio.getFechaConcertacion() != null) {
            strQuery.append("and trunc(FECHA_CONCERTACION) = ? ");
            parametros.add(criterio.getFechaConcertacion());
            tipos.add(new DateType());
        }

        // Fecha de Concertacion solo para Miscelanea Fiscal
        if(criterio.getFechaInicioPeriodo() != null) {
            strQuery.append(" and FECHA_CONCERTACION >= ? ");
            parametros.add(horaInicioFin(criterio.getFechaInicioPeriodo(), true));
            tipos.add(new TimestampType());
        }
        if(criterio.getFechaFinPeriodo() != null) {
            strQuery.append(" and FECHA_CONCERTACION <= ? ");
            parametros.add(horaInicioFin(criterio.getFechaFinPeriodo(), false));
            tipos.add(new TimestampType());
        }

        // Boveda Valores
        if(criterio.getIdBovedaValores() > 0) {
            strQuery.append("and ID_BOVEDA_VALORES = ? ");
            parametros.add(criterio.getIdBovedaValores());
            tipos.add(new LongType());
        }

        // Boveda Efectivo
        if(criterio.getIdBovedaEfectivo() > 0) {
            strQuery.append("and ID_BOVEDA_EFECTIVO = ? ");
            parametros.add(criterio.getIdBovedaEfectivo());
            tipos.add(new LongType());
        }

        // Divisa
        if(criterio.getIdDivisa() > 0) {
            strQuery.append("and ID_DIVISA = ? ");
            parametros.add(criterio.getIdDivisa());
            tipos.add(new LongType());
        }

        // Roles
        boolean existenCriteriosTraspORecep = 
            (criterio.getIdInstitucionParticipante() > DaliConstants.VALOR_COMBO_TODOS || 
             StringUtils.isNotBlank(criterio.getClaveTipoInstitucionParticipante())) || 
            StringUtils.isNotBlank(criterio.getCuentaParticipante()) || 
            (criterio.getIdInstitucionContraparte() > DaliConstants.VALOR_COMBO_TODOS || 
             StringUtils.isNotBlank(criterio.getClaveTipoInstitucionContraparte())) || 
            StringUtils.isNotBlank(criterio.getCuentaContraparte());

        if (existenCriteriosTraspORecep) {
            strQuery.append(" AND "); 

            if (criterio.getRol() == RolConstants.ROL_AMBOS) {
                strQuery.append(" ( ");
            }

            if (criterio.getRol() == RolConstants.ROL_AMBOS || criterio.getRol() == RolConstants.ROL_TRASPASANTE) {
                strQuery.append(" ( ( ID_INSTITUCION_BANCO_TRABAJO IS NULL ");

                if (criterio.getIdInstitucionParticipante() > DaliConstants.VALOR_COMBO_TODOS || 
                     StringUtils.isNotBlank(criterio.getClaveTipoInstitucionParticipante())) {
                    if (criterio.getIdInstitucionParticipante() > DaliConstants.VALOR_COMBO_TODOS) {
                        strQuery.append("and ID_INSTITUCION_TRASPASANTE = ? ");
                        parametros.add(new BigInteger(String.valueOf(criterio.getIdInstitucionParticipante())));
                        tipos.add(new BigIntegerType());
                    } 
                    else {
                        strQuery.append("and CVE_TIPO_INSTIT_TRASPASANTE = ? ");
                        parametros.add(criterio.getClaveTipoInstitucionParticipante());
                        tipos.add(new StringType());
                    }
                }
                if (StringUtils.isNotBlank(criterio.getCuentaParticipante())) {
                    strQuery.append("and CUENTA_TRASPASANTE = ? ");
                    parametros.add(criterio.getCuentaParticipante());
                    tipos.add(new StringType());
                }

                if (criterio.getIdInstitucionContraparte() > DaliConstants.VALOR_COMBO_TODOS || 
                     StringUtils.isNotBlank(criterio.getClaveTipoInstitucionContraparte())) {
                    if (criterio.getIdInstitucionContraparte() > DaliConstants.VALOR_COMBO_TODOS) {
                        strQuery.append("and ID_INSTITUCION_RECEPTORA = ? ");
                        parametros.add(new BigInteger(String.valueOf(criterio.getIdInstitucionContraparte())));
                        tipos.add(new BigIntegerType());
                    } 
                    else {
                        strQuery.append("and CVE_TIPO_INSTIT_RECEPTORA = ? ");
                        parametros.add(criterio.getClaveTipoInstitucionContraparte());
                        tipos.add(new StringType());
                    }
                }
                if (StringUtils.isNotBlank(criterio.getCuentaContraparte())) {
                    strQuery.append("and CUENTA_RECEPTORA = ? ");
                    parametros.add(criterio.getCuentaContraparte());
                    tipos.add(new StringType());
                }

                strQuery.append(" ) OR ( ID_INSTITUCION_BANCO_TRABAJO IS NOT NULL ");

                if (criterio.getIdInstitucionParticipante() > DaliConstants.VALOR_COMBO_TODOS || 
                     StringUtils.isNotBlank(criterio.getClaveTipoInstitucionParticipante())) {
                    if (criterio.getIdInstitucionParticipante() > DaliConstants.VALOR_COMBO_TODOS) {
                        strQuery.append("and ID_INSTITUCION_TRASPASANTE = ? ");
                        parametros.add(new BigInteger(String.valueOf(criterio.getIdInstitucionParticipante())));
                        tipos.add(new BigIntegerType());
                    } 
                    else {
                        strQuery.append("and CVE_TIPO_INSTIT_TRASPASANTE = ? ");
                        parametros.add(criterio.getClaveTipoInstitucionParticipante());
                        tipos.add(new StringType());
                    }
                }
                if (StringUtils.isNotBlank(criterio.getCuentaParticipante())) {
                    strQuery.append("and CUENTA_TRASPASANTE = ? ");
                    parametros.add(criterio.getCuentaParticipante());
                    tipos.add(new StringType());
                }

                if (criterio.getIdInstitucionContraparte() > DaliConstants.VALOR_COMBO_TODOS || 
                     StringUtils.isNotBlank(criterio.getClaveTipoInstitucionContraparte())) {
                    if (criterio.getIdInstitucionContraparte() > DaliConstants.VALOR_COMBO_TODOS) {
                        strQuery.append(" and ID_INSTITUCION_BANCO_TRABAJO = ? ");
                        parametros.add(new BigInteger(String.valueOf(criterio.getIdInstitucionContraparte())));
                        tipos.add(new BigIntegerType());
                    } 
                    else {
                        strQuery.append("and CVE_TIPO_INSTIT_BANCO_TRABAJO = ? ");
                        parametros.add(criterio.getClaveTipoInstitucionContraparte());
                        tipos.add(new StringType());
                    }
                }
                if (StringUtils.isNotBlank(criterio.getCuentaContraparte())) {
                    strQuery.append("and CUENTA_BANCO_TRABAJO = ? "); 
                    parametros.add(criterio.getCuentaContraparte());
                    tipos.add(new StringType());
                }

                strQuery.append(" ) OR ( ID_INSTITUCION_BANCO_TRABAJO IS NOT NULL ");

                if (criterio.getIdInstitucionParticipante() > DaliConstants.VALOR_COMBO_TODOS || 
                     StringUtils.isNotBlank(criterio.getClaveTipoInstitucionParticipante())) {
                    if (criterio.getIdInstitucionParticipante() > DaliConstants.VALOR_COMBO_TODOS) {
                        strQuery.append(" and ID_INSTITUCION_BANCO_TRABAJO = ? ");
                        parametros.add(new BigInteger(String.valueOf(criterio.getIdInstitucionParticipante())));
                        tipos.add(new BigIntegerType());
                    } 
                    else {
                        strQuery.append("and CVE_TIPO_INSTIT_BANCO_TRABAJO = ? ");
                        parametros.add(criterio.getClaveTipoInstitucionParticipante());
                        tipos.add(new StringType());
                    }
                }
                if (StringUtils.isNotBlank(criterio.getCuentaParticipante())) {
                    strQuery.append("and CUENTA_BANCO_TRABAJO = ? "); 
                    parametros.add(criterio.getCuentaParticipante());
                    tipos.add(new StringType());
                }

                if ((criterio.getIdInstitucionContraparte() > DaliConstants.VALOR_COMBO_TODOS || 
                     StringUtils.isNotBlank(criterio.getClaveTipoInstitucionContraparte()))) {
                    if (criterio.getIdInstitucionContraparte() > DaliConstants.VALOR_COMBO_TODOS) {
                        strQuery.append("and ID_INSTITUCION_RECEPTORA = ? ");
                        parametros.add(new BigInteger(String.valueOf(criterio.getIdInstitucionContraparte())));
                        tipos.add(new BigIntegerType());
                    } 
                    else {
                        strQuery.append("and CVE_TIPO_INSTIT_RECEPTORA = ? ");
                        parametros.add(criterio.getClaveTipoInstitucionContraparte());
                        tipos.add(new StringType());
                    }
                }
                if (StringUtils.isNotBlank(criterio.getCuentaContraparte())) {
                    strQuery.append("and CUENTA_RECEPTORA = ? ");
                    parametros.add(criterio.getCuentaContraparte());
                    tipos.add(new StringType());
                }

                strQuery.append(" ) ) ");
            }

            if (criterio.getRol() == RolConstants.ROL_AMBOS) {
                strQuery.append(" or ");
            }

            if (criterio.getRol() == RolConstants.ROL_AMBOS || criterio.getRol() == RolConstants.ROL_RECEPTOR) {
                strQuery.append(" ( (  ID_INSTITUCION_BANCO_TRABAJO is null ");

                if (criterio.getIdInstitucionContraparte() > DaliConstants.VALOR_COMBO_TODOS || 
                     StringUtils.isNotBlank(criterio.getClaveTipoInstitucionContraparte())) {
                    if (criterio.getIdInstitucionContraparte() > DaliConstants.VALOR_COMBO_TODOS) {
                        strQuery.append("and ID_INSTITUCION_TRASPASANTE = ? ");
                        parametros.add(new BigInteger(String.valueOf(criterio.getIdInstitucionContraparte())));
                        tipos.add(new BigIntegerType());
                    } 
                    else {
                        strQuery.append("and CVE_TIPO_INSTIT_TRASPASANTE = ? ");
                        parametros.add(criterio.getClaveTipoInstitucionContraparte());
                        tipos.add(new StringType());
                    }
                }
                if (StringUtils.isNotBlank(criterio.getCuentaContraparte())) {
                    strQuery.append("and CUENTA_TRASPASANTE = ? ");
                    parametros.add(criterio.getCuentaContraparte());
                    tipos.add(new StringType());
                }

                if (criterio.getIdInstitucionParticipante() > DaliConstants.VALOR_COMBO_TODOS || 
                     StringUtils.isNotBlank(criterio.getClaveTipoInstitucionParticipante())) {
                    if (criterio.getIdInstitucionParticipante() > DaliConstants.VALOR_COMBO_TODOS) {
                        strQuery.append("and ID_INSTITUCION_RECEPTORA = ? ");
                        parametros.add(new BigInteger(String.valueOf(criterio.getIdInstitucionParticipante())));
                        tipos.add(new BigIntegerType());
                    } 
                    else {
                        strQuery.append("and CVE_TIPO_INSTIT_RECEPTORA = ? ");
                        parametros.add(criterio.getClaveTipoInstitucionParticipante());
                        tipos.add(new StringType());
                    }
                }
                if (StringUtils.isNotBlank(criterio.getCuentaParticipante())) {
                    strQuery.append("and CUENTA_RECEPTORA = ? ");
                    parametros.add(criterio.getCuentaParticipante());
                    tipos.add(new StringType());
                }

                strQuery.append(" ) OR ( ID_INSTITUCION_BANCO_TRABAJO is not null ");

                if (criterio.getIdInstitucionContraparte() > DaliConstants.VALOR_COMBO_TODOS || 
                     StringUtils.isNotBlank(criterio.getClaveTipoInstitucionContraparte())) {
                    if (criterio.getIdInstitucionContraparte() > DaliConstants.VALOR_COMBO_TODOS) {
                        strQuery.append(" and ID_INSTITUCION_BANCO_TRABAJO = ? ");
                        parametros.add(new BigInteger(String.valueOf(criterio.getIdInstitucionContraparte())));
                        tipos.add(new BigIntegerType());
                    } 
                    else {
                        strQuery.append("and CVE_TIPO_INSTIT_BANCO_TRABAJO = ? ");
                        parametros.add(criterio.getClaveTipoInstitucionContraparte());
                        tipos.add(new StringType());
                    }
                }
                if (StringUtils.isNotBlank(criterio.getCuentaContraparte())) {
                    strQuery.append("and CUENTA_BANCO_TRABAJO = ? "); 
                    parametros.add(criterio.getCuentaContraparte());
                    tipos.add(new StringType());
                }

                if (criterio.getIdInstitucionParticipante() > DaliConstants.VALOR_COMBO_TODOS || 
                     StringUtils.isNotBlank(criterio.getClaveTipoInstitucionParticipante())) {
                    if (criterio.getIdInstitucionParticipante() > DaliConstants.VALOR_COMBO_TODOS) {
                        strQuery.append("and ID_INSTITUCION_RECEPTORA = ? ");
                        parametros.add(new BigInteger(String.valueOf(criterio.getIdInstitucionParticipante())));
                        tipos.add(new BigIntegerType());
                    } 
                    else {
                        strQuery.append("and CVE_TIPO_INSTIT_RECEPTORA = ? ");
                        parametros.add(criterio.getClaveTipoInstitucionParticipante());
                        tipos.add(new StringType());
                    }
                }
                if (StringUtils.isNotBlank(criterio.getCuentaParticipante())) {
                    strQuery.append("and CUENTA_RECEPTORA = ? ");
                    parametros.add(criterio.getCuentaParticipante());
                    tipos.add(new StringType());
                }

                strQuery.append(" ) OR ( ID_INSTITUCION_BANCO_TRABAJO is not null ");

                if (criterio.getIdInstitucionContraparte() > DaliConstants.VALOR_COMBO_TODOS || 
                     StringUtils.isNotBlank(criterio.getClaveTipoInstitucionContraparte())) {
                    if (criterio.getIdInstitucionContraparte() > DaliConstants.VALOR_COMBO_TODOS) {
                        strQuery.append("and ID_INSTITUCION_TRASPASANTE = ? ");
                        parametros.add(new BigInteger(String.valueOf(criterio.getIdInstitucionContraparte())));
                        tipos.add(new BigIntegerType());
                    } 
                    else {
                        strQuery.append("and CVE_TIPO_INSTIT_TRASPASANTE = ? ");
                        parametros.add(criterio.getClaveTipoInstitucionContraparte());
                        tipos.add(new StringType());
                    }
                }
                if (StringUtils.isNotBlank(criterio.getCuentaContraparte())) {
                    strQuery.append("and CUENTA_TRASPASANTE = ? ");
                    parametros.add(criterio.getCuentaContraparte());
                    tipos.add(new StringType());
                }

                if (criterio.getIdInstitucionParticipante() > DaliConstants.VALOR_COMBO_TODOS || 
                     StringUtils.isNotBlank(criterio.getClaveTipoInstitucionParticipante())) {
                    if (criterio.getIdInstitucionParticipante() > DaliConstants.VALOR_COMBO_TODOS) {
                        strQuery.append(" and ID_INSTITUCION_BANCO_TRABAJO = ? ");
                        parametros.add(new BigInteger(String.valueOf(criterio.getIdInstitucionParticipante())));
                        tipos.add(new BigIntegerType());
                    } 
                    else {
                        strQuery.append("and CVE_TIPO_INSTIT_BANCO_TRABAJO = ? ");
                        parametros.add(criterio.getClaveTipoInstitucionParticipante());
                        tipos.add(new StringType());
                    }
                }
                if (StringUtils.isNotBlank(criterio.getCuentaParticipante())) {
                    strQuery.append("and CUENTA_BANCO_TRABAJO = ? "); 
                    parametros.add(criterio.getCuentaParticipante());
                    tipos.add(new StringType());
                }
                strQuery.append(" ) ) ");
            }

            if (criterio.getRol() == RolConstants.ROL_AMBOS) {
                strQuery.append(" ) ");
            }
        }

        // Tipo de Valor
        if (criterio.getIdTipoValor() > DaliConstants.VALOR_COMBO_TODOS || 
            criterio.getIdEmisora() > DaliConstants.VALOR_COMBO_TODOS || 
            !StringUtils.isEmpty(criterio.getSerie())) {
            strQuery.append(" AND "); 

            strQuery.append("((");

            if (criterio.getIdTipoValor() > DaliConstants.VALOR_COMBO_TODOS) {
                strQuery.append(" ID_INSTRUMENTO = ? "); 
                parametros.add(new BigInteger(String.valueOf(criterio.getIdTipoValor())));
                tipos.add(new BigIntegerType());
            }

            // emisora
            if (criterio.getIdEmisora() > DaliConstants.VALOR_COMBO_TODOS) {
                if (criterio.getIdTipoValor() > DaliConstants.VALOR_COMBO_TODOS) {
                    strQuery.append(" and ");
                }
                strQuery.append(" ID_EMISORA = ? "); 
                parametros.add(new BigInteger(String.valueOf(criterio.getIdEmisora())));
                tipos.add(new BigIntegerType());
            }

            // serie
            if (!StringUtils.isEmpty(criterio.getSerie())) {
                if (criterio.getIdTipoValor() > DaliConstants.VALOR_COMBO_TODOS || 
                    criterio.getIdEmisora() > DaliConstants.VALOR_COMBO_TODOS) {
                    strQuery.append(" and ");
                }
                strQuery.append(" SERIE = ? "); 
                parametros.add(criterio.getSerie());
                tipos.add(new StringType());
            }

            strQuery.append(") OR (");

            // tipo valor emision pendiente
            if (criterio.getIdTipoValor() > DaliConstants.VALOR_COMBO_TODOS) {
                strQuery.append(" TIPO_VALOR_PENDIENTE = ? "); 
                parametros.add(criterio.getClaveTipoValor());
                tipos.add(new StringType());
            }

            // emisora emision pendiente
            if (criterio.getIdEmisora() > DaliConstants.VALOR_COMBO_TODOS) {
                if (criterio.getIdTipoValor() > DaliConstants.VALOR_COMBO_TODOS) {
                    strQuery.append(" and ");
                }
                strQuery.append(" EMISORA_PENDIENTE = ? "); 
                parametros.add(criterio.getDescripcionEmisora());
                tipos.add(new StringType());
            }

            // serie emision pendiente
            if (!StringUtils.isEmpty(criterio.getSerie())) {
                if (criterio.getIdTipoValor() > DaliConstants.VALOR_COMBO_TODOS || 
                    criterio.getIdEmisora() > DaliConstants.VALOR_COMBO_TODOS) {
                    strQuery.append(" and ");
                }
                strQuery.append(" SERIE_PENDIENTE = ? "); 
                parametros.add(criterio.getSerie());
                tipos.add(new StringType());
            }
            strQuery.append("))");
        }
        
        // Folio Usuario
        if (!StringUtils.isEmpty(criterio.getFolioUsuario())) {
            if(criterio.getIdInstitucionParticipante() <= 0){
                strQuery.append(" and ( ");
                strQuery.append(" FOLIO_INSTRUCCION_RECEPTORA = ? ");
                strQuery.append(" or ");
                strQuery.append(" FOLIO_INSTRUCCION_TRASPASANTE = ? ");
                strQuery.append(" ) ");
                parametros.add(criterio.getFolioUsuario());
                tipos.add(new StringType());
                parametros.add(criterio.getFolioUsuario());
                tipos.add(new StringType());
            }
            else {
                strQuery.append(" AND ( ");
                strQuery.append(" (FOLIO_INSTRUCCION_RECEPTORA = ? and  " + " ID_INSTITUCION_RECEPTORA = ? ) ");
                strQuery.append(" or ");
                strQuery.append(" (FOLIO_INSTRUCCION_TRASPASANTE = ? and " + " ID_INSTITUCION_TRASPASANTE = ? ) ");
                strQuery.append(" ) ");
                parametros.add(criterio.getFolioUsuario());
                tipos.add(new StringType());
                parametros.add(new BigInteger(String.valueOf(criterio.getIdInstitucionParticipante())));
                tipos.add(new BigIntegerType());
                parametros.add(criterio.getFolioUsuario());
                tipos.add(new StringType());
                parametros.add(new BigInteger(String.valueOf(criterio.getIdInstitucionParticipante())));
                tipos.add(new BigIntegerType());
            }
        }

        // Tipo de Mensaje
        if (criterio.getIdTipoMensaje() != DaliConstants.VALOR_COMBO_TODOS) {
            strQuery.append(" and ID_TIPO_MENSAJE_CAT = ? ");
            parametros.add(new BigInteger(String.valueOf(criterio.getIdTipoMensaje())));
            tipos.add(new BigIntegerType());

        }

        // Remitente
        if (criterio.isRemitente()) {
            strQuery.append(" and MENSAJE_RECIBIDO like ? ");
            parametros.add("%<FolioInstitucion>" + 
                               criterio.getFolioInstitucionParticipante() + 
                           "</FolioInstitucion>%" + 
                           "<IdInstitucion>" + 
                               criterio.getClaveTipoInstitucionParticipante() + 
                           "</IdInstitucion>%");
            tipos.add(new StringType());

        }

        // Origen
        if (!StringUtils.isEmpty(criterio.getOrigen())) {
            strQuery.append(" and (ORIGEN_INSTITUCION_RECEPTORA = ? OR ORIGEN_INSTITUCION_TRASPASANTE = ?) ");
            parametros.add(criterio.getOrigen());
            tipos.add(new StringType());
            parametros.add(criterio.getOrigen());
            tipos.add(new StringType());
        }

        // Error
        if (StringUtils.isNotBlank(criterio.getClaveError())) {
            strQuery.append(" and CLAVE_ERROR = ? ");
            parametros.add(criterio.getClaveError());
            tipos.add(new StringType());
        }
    }

    /**
     * Crea el SQLQuery con sus parametros y sus tipos de la consulta de operaciones valor y de las parcialidades.
     * @param session La sesion de hibernate.
     * @param strQuery El query armado con anterioridad.
     * @param parametros Los valores de los parametros. 
     * @param tipos Los tipos de los parametros. 
     * @return El objeto SQLQuery preparado.
     */
    private SQLQuery crearQueryConsultaOperacionesValorYParcialidades(Session session, StringBuilder strQuery, 
                                                                      List<Object> parametros, List<Type> tipos) {
        SQLQuery query = session.createSQLQuery(strQuery.toString());
        query.setParameters(parametros.toArray(), tipos.toArray(new Type[] {}));
        query.addScalar("idInstruccionOperacion", Hibernate.LONG);
        query.addScalar("idBitacoraMatch", Hibernate.LONG);
        query.addScalar("folioControl", Hibernate.LONG);
        query.addScalar("porPaquete", Hibernate.BOOLEAN); 
        query.addScalar("referenciaPaquete", Hibernate.STRING);
        query.addScalar("folioOrigen", Hibernate.STRING);
        query.addScalar("origen", Hibernate.STRING);
        query.addScalar("tv", Hibernate.STRING); 
        query.addScalar("emisora", Hibernate.STRING);
        query.addScalar("serie", Hibernate.STRING);
        query.addScalar("cupon", Hibernate.STRING);
        query.addScalar("bovedaValores", Hibernate.STRING);
        query.addScalar("claveEstadoInstruccionCat", Hibernate.STRING);
        query.addScalar("descEstadoInstruccionCat", Hibernate.STRING);
        query.addScalar("idInstitucionTraspasante", Hibernate.LONG);
        query.addScalar("cveTipoInstitucionTraspasante", Hibernate.STRING);
        query.addScalar("idFolioInstitucionTraspasante", Hibernate.STRING);
        query.addScalar("cuentaTraspasante", Hibernate.STRING);
        query.addScalar("idInstitucionReceptora", Hibernate.LONG);
        query.addScalar("cveTipoInstitucionReceptora", Hibernate.STRING);
        query.addScalar("idFolioInstitucionReceptora", Hibernate.STRING);
        query.addScalar("cuentaReceptora", Hibernate.STRING);
        query.addScalar("precioTitulo", Hibernate.DOUBLE);
        query.addScalar("nombreCortoTipoInstruccion", Hibernate.STRING);
        query.addScalar("instruccion", Hibernate.STRING);
        query.addScalar("descTipoInstruccion", Hibernate.STRING);
        query.addScalar("claveTipoMensajeCat", Hibernate.STRING);
        query.addScalar("descTipoMensajeCat", Hibernate.STRING);
        query.addScalar("fechaLiquidacion", Hibernate.TIMESTAMP);
        query.addScalar("cantidadTitulos", Hibernate.LONG);
        query.addScalar("importe", Hibernate.DOUBLE); 
        query.addScalar("bovedaEfectivo", Hibernate.STRING);
        query.addScalar("tasaNegociada", Hibernate.DOUBLE); 
        query.addScalar("plazoReporto", Hibernate.LONG);
        query.addScalar("fechaReporto", Hibernate.STRING);
        query.addScalar("claveDivisa", Hibernate.STRING);
        query.addScalar("nombreCortoInstitTraspasante", Hibernate.STRING);
        query.addScalar("nombreCortoInstitReceptora", Hibernate.STRING);
        query.addScalar("fechaConcertacion", Hibernate.TIMESTAMP);
        query.addScalar("fechaHoraCierreOperTra", Hibernate.TIMESTAMP);
        query.addScalar("fechaHoraCierreOperRec", Hibernate.TIMESTAMP);
        query.addScalar("fechaHoraEncolamientoTra", Hibernate.TIMESTAMP);
        query.addScalar("fechaHoraEncolamientoRec", Hibernate.TIMESTAMP);
        query.addScalar("idInstitucionBancoTrabajo", Hibernate.LONG);
        query.addScalar("cveTipoInstitBancoTrabajo", Hibernate.STRING);
        query.addScalar("folioInstitBancoTrabajo", Hibernate.STRING);
        query.addScalar("nombreCortoInstBancoTrab", Hibernate.STRING);
        query.addScalar("cuentaBancoTrabajo", Hibernate.STRING);
        query.addScalar("folioInstruccionReceptora", Hibernate.STRING);
        query.addScalar("folioInstruccionTraspasante", Hibernate.STRING);
        query.addScalar("tieneParcialidades", Hibernate.BOOLEAN); 
        query.addScalar("idEstadoInstruccionCat", Hibernate.LONG);
        query.addScalar("isin", Hibernate.STRING);
        query.addScalar("totalOperacionesPaquete", Hibernate.STRING);
        query.addScalar("numeroOperacionPaquete", Hibernate.STRING);
        query.addScalar("totalTitulosPaquete", Hibernate.STRING);
        query.addScalar("totalImportePaquete", Hibernate.STRING);
        query.addScalar("conMiscelaneaFiscal", Hibernate.BOOLEAN); 
        query.addScalar("puedeConfirmar", Hibernate.BOOLEAN); 
        query.addScalar("idError", Hibernate.INTEGER);
        query.addScalar("claveError", Hibernate.STRING);
        query.addScalar("nombreCuentaTraspasante", Hibernate.STRING);
        query.addScalar("nombreCuentaReceptora", Hibernate.STRING);
        query.addScalar("interesesGenerados", Hibernate.DOUBLE); 
        query.addScalar("descError", Hibernate.STRING);
        return query;
    }

    /**
     * Metodo que crea el query para realizar la consulta de operaciones de valor.
     * @param session Sesion de hibernate para crear el query.
     * @param criterio Objeto con los parametros para realizar la consulta.
     * @return SQLQuery generado.
     */
    private SQLQuery crearQueryConsultaOperacionesValor(Session session, CriterioMatchOperacionesExportacionDTO criterio, String instOcultarFechaHoraCierreOper) {
        List<Object> parametros = new ArrayList<Object>();
        List<Type> tipos = new ArrayList<Type>();

        StringBuilder strQuery = new StringBuilder();

        strQuery.append("select ");
        strQuery.append("ID_INSTRUCCION_OPERACION as idInstruccionOperacion, ");
        strQuery.append("ID_BITACORA_MATCH as idBitacoraMatch, ");
        strQuery.append("FOLIO_CONTROL as folioControl, ");
        strQuery.append("POR_PAQUETE as porPaquete, ");
        strQuery.append("REFERENCIA_PAQUETE as referenciaPaquete, ");
        strQuery.append("null as folioOrigen, ");
        strQuery.append("(CASE WHEN ORIGEN_INSTITUCION_TRASPASANTE IS NOT NULL THEN ORIGEN_INSTITUCION_TRASPASANTE ELSE '03' END) || '|' || " + 
                        "(CASE WHEN ORIGEN_INSTITUCION_RECEPTORA IS NOT NULL THEN ORIGEN_INSTITUCION_RECEPTORA ELSE '03' END) as origen, ");
        strQuery.append("CASE WHEN TV IS NULL THEN TIPO_VALOR_PENDIENTE ELSE TV END as tv, ");
        strQuery.append("CASE WHEN EMISORA IS NULL THEN EMISORA_PENDIENTE ELSE EMISORA END as emisora, ");
        strQuery.append("CASE WHEN SERIE IS NULL THEN SERIE_PENDIENTE ELSE SERIE END as serie, ");
        strQuery.append("CASE WHEN CUPON IS NULL THEN CUPON_PENDIENTE ELSE CUPON END as cupon, ");
        strQuery.append("BOVEDA_VALORES as bovedaValores, ");
        strQuery.append("CLAVE_ESTADO_INSTRUCCION_CAT as claveEstadoInstruccionCat, ");
        strQuery.append("DESC_ESTADO_INSTRUCCION_CAT as descEstadoInstruccionCat, ");
        strQuery.append("ID_INSTITUCION_TRASPASANTE as idInstitucionTraspasante, ");
        strQuery.append("CVE_TIPO_INSTIT_TRASPASANTE as cveTipoInstitucionTraspasante, ");
        strQuery.append("ID_FOLIO_INSTIT_TRASPASANTE as idFolioInstitucionTraspasante, ");
        strQuery.append("CUENTA_TRASPASANTE as cuentaTraspasante, ");
        strQuery.append("ID_INSTITUCION_RECEPTORA as idInstitucionReceptora, ");
        strQuery.append("CVE_TIPO_INSTIT_RECEPTORA as cveTipoInstitucionReceptora, ");
        strQuery.append("ID_FOLIO_INSTIT_RECEPTORA as idFolioInstitucionReceptora, ");
        strQuery.append("CUENTA_RECEPTORA as cuentaReceptora, ");
        strQuery.append("PRECIO_TITULO as precioTitulo, ");
        strQuery.append("NOMBRE_CORTO_TIPO_INSTRUCCION as nombreCortoTipoInstruccion, ");
        strQuery.append("INSTRUCCION as instruccion, ");
        strQuery.append("DESC_TIPO_INSTRUCCION as descTipoInstruccion, ");
        strQuery.append("CLAVE_TIPO_MENSAJE_CAT as claveTipoMensajeCat, ");
        strQuery.append("DESC_TIPO_MENSAJE_CAT as descTipoMensajeCat, ");
        strQuery.append("FECHA_LIQUIDACION as fechaLiquidacion, ");
        strQuery.append("CANTIDAD_TITULOS as cantidadTitulos, ");
        strQuery.append("IMPORTE as importe, ");
        strQuery.append("BOVEDA_EFECTIVO as bovedaEfectivo, ");
        strQuery.append("CASE WHEN TASA_NEGOCIADA IS NULL THEN 0 ELSE TASA_NEGOCIADA END as tasaNegociada, "); 
        //strQuery.append("CASE WHEN PLAZO_REPORTO IS NULL AND ID_ESTADO_INSTRUCCION_CAT <> 6 THEN 0 ELSE PLAZO_REPORTO END as plazoReporto, ");
        strQuery.append("PLAZO_REPORTO as plazoReporto, ");
        strQuery.append("CASE WHEN PLAZO_REPORTO IS NOT NULL AND PLAZO_REPORTO > 0 THEN to_char(FECHA_LIQUIDACION + PLAZO_REPORTO, 'dd/mm/yyyy') ELSE '' END as fechaReporto, ");
        strQuery.append("CLAVE_DIVISA as claveDivisa, ");
        strQuery.append("NOMBRE_CORTO_INST_TRASPASANTE as nombreCortoInstitTraspasante, ");
        strQuery.append("NOMBRE_CORTO_INST_RECEPTORA as nombreCortoInstitReceptora, ");
        strQuery.append("FECHA_CONCERTACION as fechaConcertacion, ");
        strQuery.append("CASE WHEN ID_FOLIO_INSTIT_TRASPASANTE IN ("+ instOcultarFechaHoraCierreOper +") THEN NULL ELSE FECHA_HORA_CIERRE_OPER_TRA END as fechaHoraCierreOperTra, ");
        strQuery.append("CASE WHEN ID_FOLIO_INSTIT_RECEPTORA IN ("+ instOcultarFechaHoraCierreOper +") THEN NULL ELSE FECHA_HORA_CIERRE_OPER_REC END as fechaHoraCierreOperRec, ");
        strQuery.append("FECHA_HORA_ENCOLAMIENTO_TRA as fechaHoraEncolamientoTra, ");
        strQuery.append("FECHA_HORA_ENCOLAMIENTO_REC as fechaHoraEncolamientoRec, ");
        strQuery.append("ID_INSTITUCION_BANCO_TRABAJO as idInstitucionBancoTrabajo, ");
        strQuery.append("CVE_TIPO_INSTIT_BANCO_TRABAJO as cveTipoInstitBancoTrabajo, ");
        strQuery.append("FOLIO_INSTIT_BANCO_TRABAJO as folioInstitBancoTrabajo, ");
        strQuery.append("NOMBRE_CORTO_INST_BANCO_TRAB as nombreCortoInstBancoTrab, ");
        strQuery.append("CUENTA_BANCO_TRABAJO as cuentaBancoTrabajo, ");
        strQuery.append("FOLIO_INSTRUCCION_RECEPTORA as folioInstruccionReceptora, ");
        strQuery.append("FOLIO_INSTRUCCION_TRASPASANTE as folioInstruccionTraspasante, ");
        strQuery.append("TIENE_PARCIALIDADES as tieneParcialidades, ");
        strQuery.append("ID_ESTADO_INSTRUCCION_CAT as idEstadoInstruccionCat, ");
        strQuery.append("ISIN as isin, ");
        strQuery.append("TOTAL_OPERACIONES_PAQUETE as totalOperacionesPaquete, ");
        strQuery.append("NUMERO_OPERACION_PAQUETE as numeroOperacionPaquete, ");
        strQuery.append("TOTAL_TITULOS_PAQUETE as totalTitulosPaquete, ");
        strQuery.append("TOTAL_IMPORTE_PAQUETE as totalImportePaquete, ");
        strQuery.append("CON_MISCELANEA_FISCAL as conMiscelaneaFiscal, ");
        strQuery.append("PUEDE_CONFIRMAR as puedeConfirmar, ");
        strQuery.append("ID_ERROR_DALI as idError, ");
        strQuery.append("CLAVE_ERROR as claveError, ");
        strQuery.append("DESC_ERROR as descError, ");
        strQuery.append("NOMBRE_CUENTA_TRASPASANTE as nombreCuentaTraspasante, ");
        strQuery.append("NOMBRE_CUENTA_RECEPTORA as nombreCuentaReceptora, ");
        strQuery.append("INTERESES_GENERADOS as interesesGenerados ");
        strQuery.append("from V_CONS_OPER_VALOR ");
        strQuery.append("where 1 = 1 ");

        armarQueryConsultaOperacionesValorYParcialidades(criterio, strQuery, parametros, tipos);

        // Estado de Instruccion
        if (criterio.getIdEstadoInstruccion() != DaliConstants.VALOR_COMBO_TODOS) {
            strQuery.append(" and ID_ESTADO_INSTRUCCION_CAT = ? ");
            parametros.add(new BigInteger(String.valueOf(criterio.getIdEstadoInstruccion())));
            tipos.add(new BigIntegerType());
        }

        // Cantidad de Titulos
        if (NumberUtils.toLong(criterio.getCantidad(), DaliConstants.VALOR_COMBO_TODOS) != DaliConstants.VALOR_COMBO_TODOS) {
            strQuery.append(" and CANTIDAD_TITULOS = ? ");
            parametros.add(NumberUtils.toLong(criterio.getCantidad(), DaliConstants.VALOR_COMBO_TODOS));
            tipos.add(new LongType());
        }

        // Monto
        if (NumberUtils.toDouble(criterio.getMonto(), DaliConstants.VALOR_COMBO_TODOS) != DaliConstants.VALOR_COMBO_TODOS) {
            strQuery.append(" and TO_CHAR(IMPORTE,'FM99999999999999.99') = to_char(?,'FM99999999999999.99') ");
            parametros.add(NumberUtils.toDouble(criterio.getMonto(), DaliConstants.VALOR_COMBO_TODOS));
            tipos.add(new DoubleType());
        }

        // Folio Control
        if (!StringUtils.isEmpty(criterio.getFolioControl())) {
            strQuery.append(" and FOLIO_CONTROL = ? ");
            parametros.add(NumberUtils.toLong(criterio.getFolioControl(), DaliConstants.VALOR_COMBO_TODOS));
            tipos.add(new LongType());
        }

        // Referencia Paquete
        if (!StringUtils.isEmpty(criterio.getReferenciaPaquete())) {
        	strQuery.append(" and  (upper(trim(REFERENCIA_PAQUETE)) = upper(trim(?))  or upper(trim(FOLIO_ORIGEN)) = upper(trim(?))" +
        	        " and CLAVE_ESTADO_INSTRUCCION_CAT not in ('SM','EP','FV' ) )");
            parametros.add(criterio.getReferenciaPaquete());
            tipos.add(new StringType());
            parametros.add(criterio.getReferenciaPaquete());
            tipos.add(new StringType());
        }

        // Fecha Liquidacion
        if (criterio.getFechaLiquidacion() != null) {
            strQuery.append(" and trunc(FECHA_LIQUIDACION) = ? ");
            parametros.add(criterio.getFechaLiquidacion());
            tipos.add(new DateType());
        }

        SQLQuery query = crearQueryConsultaOperacionesValorYParcialidades(session, strQuery, parametros, tipos);

        query.setResultTransformer(new AliasToBeanResultTransformer(ConsultaOperacionValor.class));
        //SQLQuery query = session.createSQLQuery(strQuery.toString());

        parametros.clear();
        parametros = null;
        tipos.clear();
        tipos = null;

        return query;
    }

    /**
     * Auxiliar a generar otros datos de las operaciones valor.
     * @param registros Los registros a iterar.
     * @param criterio Los criterios de la busqueda.
     * @return El listado de operaciones valor.
     */
    private List<ConsultaOperacionesMatch> generaOtrosDatosOperacionesValor(List<ConsultaOperacionesMatch> registros, 
                                                                            final CriterioMatchOperacionesExportacionDTO criterio, 
                                                                            boolean ignorarEstatus) {
        List<ConsultaOperacionesMatch> operacionesAdicionales = new ArrayList<ConsultaOperacionesMatch>();
        List<ConsultaOperacionesMatch> registrosReales = new ArrayList<ConsultaOperacionesMatch>();
        int idEstadoInstruccion = criterio.getIdEstadoInstruccion() > 0 ? criterio.getIdEstadoInstruccion() : DaliConstants.VALOR_COMBO_TODOS;

        for (ConsultaOperacionesMatch operacion : registros) {
            if (operacion.getIdInstitucionBancoTrabajo() != null && operacion.getIdInstitucionBancoTrabajo().longValue() > 0) {
                if (criterio.getRol() == RolConstants.ROL_AMBOS || criterio.getRol() == RolConstants.ROL_RECEPTOR) {
                    try {
                        ConsultaOperacionesMatch aux = (ConsultaOperacionesMatch) BeanUtils.cloneBean(operacion);
                        if (criterio.getIdInstitucionParticipante() > 0 && 
                            criterio.getIdInstitucionParticipante() > DaliConstants.VALOR_COMBO_TODOS && 
                            criterio.getIdInstitucionParticipante() == operacion.getIdInstitucionBancoTrabajo().longValue()) {
                            aux.setIdFolioInstitucionReceptora(operacion.getCveTipoInstitBancoTrabajo() + 
                                                               operacion.getFolioInstitBancoTrabajo());
                            aux.setNombreCortoInstitReceptora(operacion.getNombreCortoInstBancoTrab());
                            aux.setCuentaReceptora(operacion.getCuentaBancoTrabajo());
                            operacion.setCuentaTraspasante(operacion.getCuentaBancoTrabajo());
                            operacionesAdicionales.add(aux);
                        }
                        else {
                            aux.setIdFolioInstitucionTraspasante(operacion.getCveTipoInstitBancoTrabajo() + 
                                                                 operacion.getFolioInstitBancoTrabajo());
                            aux.setNombreCortoInstitTraspasante(operacion.getNombreCortoInstBancoTrab());
                            aux.setCuentaTraspasante(operacion.getCuentaBancoTrabajo());
                            operacion.setCuentaReceptora(operacion.getCuentaBancoTrabajo());
                            if (criterio.getIdInstitucionParticipante() > 0 && 
                                criterio.getIdInstitucionParticipante() > DaliConstants.VALOR_COMBO_TODOS && 
                                criterio.getIdInstitucionParticipante() == operacion.getIdInstitucionReceptora().longValue()) {
                                operacionesAdicionales.add(aux);
                            }
                            else {
                                if (criterio.getIdInstitucionParticipante() <= 0 || 
                                    criterio.getIdInstitucionParticipante() == DaliConstants.VALOR_COMBO_TODOS) {
                                    operacionesAdicionales.add(aux);
                                }
                            }
                        }
                    } catch (IllegalAccessException e) {
                        logger.error("Ocurrio un error:",e);
                        throw new InfrastructureException(e);
                    } catch (InstantiationException e) {
                        logger.error("Ocurrio un error:",e);
                        throw new InfrastructureException(e);
                    } catch (InvocationTargetException e) {
                        logger.error("Ocurrio un error:",e);
                        throw new InfrastructureException(e);
                    } catch (NoSuchMethodException e) {
                        logger.error("Ocurrio un error:",e);
                        throw new InfrastructureException(e);
                    }
                }
                if (criterio.getRol() == RolConstants.ROL_AMBOS || criterio.getRol() == RolConstants.ROL_TRASPASANTE) {
                    try {
                        ConsultaOperacionesMatch aux = (ConsultaOperacionesMatch) BeanUtils.cloneBean(operacion);
                        if (criterio.getIdInstitucionParticipante() > 0 && 
                            criterio.getIdInstitucionParticipante() > DaliConstants.VALOR_COMBO_TODOS && 
                            criterio.getIdInstitucionParticipante() == operacion.getIdInstitucionBancoTrabajo().longValue()) {
                            aux.setIdFolioInstitucionTraspasante(operacion.getCveTipoInstitBancoTrabajo() + 
                                                                 operacion.getFolioInstitBancoTrabajo());
                            aux.setNombreCortoInstitTraspasante(operacion.getNombreCortoInstBancoTrab());
                            operacionesAdicionales.add(aux);
                        }
                        else {
                            aux.setIdFolioInstitucionReceptora(operacion.getCveTipoInstitBancoTrabajo() + 
                                                               operacion.getFolioInstitBancoTrabajo());
                            aux.setNombreCortoInstitReceptora(operacion.getNombreCortoInstBancoTrab());
                            if (criterio.getIdInstitucionParticipante() > 0 && 
                                criterio.getIdInstitucionParticipante() > DaliConstants.VALOR_COMBO_TODOS && 
                                criterio.getIdInstitucionParticipante() == operacion.getIdInstitucionTraspasante().longValue()) {
                                operacionesAdicionales.add(aux);
                            }
                            else {
                                if (criterio.getIdInstitucionParticipante() <= 0 || 
                                    criterio.getIdInstitucionParticipante() == DaliConstants.VALOR_COMBO_TODOS) {
                                    operacionesAdicionales.add(aux);
                                }
                            }
                        }
                    } catch (IllegalAccessException e) {
                        logger.error("Ocurrio un error:",e);
                        throw new InfrastructureException(e);
                    } catch (InstantiationException e) {
                        logger.error("Ocurrio un error:",e);
                        throw new InfrastructureException(e);
                    } catch (InvocationTargetException e) {
                        logger.error("Ocurrio un error:",e);
                        throw new InfrastructureException(e);
                    } catch (NoSuchMethodException e) {
                        logger.error("Ocurrio un error:",e);
                        throw new InfrastructureException(e);
                    }
                }
            }
            else {
                operacionesAdicionales.add(operacion);
            }

            if (criterio.getIdInstitucionParticipante() > 0) {
                for (ConsultaOperacionesMatch oper : operacionesAdicionales) {
                    oper.setFolioUsuario(oper.getIdInstitucionReceptora().longValue() == criterio.getIdInstitucionParticipante() ? 
                                         operacion.getFolioInstruccionReceptora() : operacion.getFolioInstruccionTraspasante());
                }
                /*operacion.setFolioUsuario(operacion.getIdInstitucionReceptora().longValue() == criterio.getIdInstitucionParticipante() ? 
                                          operacion.getFolioInstruccionReceptora() : operacion.getFolioInstruccionTraspasante());*/
            }
            else {
                for (ConsultaOperacionesMatch oper : operacionesAdicionales) {
                    oper.setFolioUsuario(operacion.getFolioInstruccionTraspasante());
                }
                //operacion.setFolioUsuario(operacion.getFolioInstruccionTraspasante());
            }

            if (operacion.isTieneParcialidades()) {
                for (ConsultaOperacionesMatch oper : operacionesAdicionales) {
                    oper.setTieneParcialidades(true);
                }
            }

            for (ConsultaOperacionesMatch op : operacionesAdicionales) {
                if (ignorarEstatus || idEstadoInstruccion == DaliConstants.VALOR_COMBO_TODOS || 
                    op.getIdEstadoInstruccionCat().longValue() == idEstadoInstruccion) {
                    registrosReales.add(op);
                }
            }

            operacionesAdicionales.clear();
        }

        operacionesAdicionales = null;

        return registrosReales;
    }

    /**
     * Metodo que crea el query para realizar la consulta de instrucciones match sin operacion.
     * @param session Sesion de hibernate para crear el query.
     * @param criterio Objeto con los parametros para realizar la consulta.
     * @param mapeoOperacionInstruccion Mapa de objetos TipoInstruccionDTO. 
     * @return SQLQuery generado.
     */
    private SQLQuery crearQueryConsultaInstruccionesMatchSinOperacion(Session session, CriterioMatchOperacionesExportacionDTO criterio,
                                                                      HashMap<String, TipoInstruccionDTO> mapeoOperacionInstruccion, String instOcultarFechaHoraCierreOper) {
        List<Object> parametros = new ArrayList<Object>();
        List<Type> tipos = new ArrayList<Type>();
            StringBuilder strQuery = new StringBuilder();

            strQuery.append("select ");
            strQuery.append("ID_BITACORA_MATCH as idBitacoraMatch, ");
            strQuery.append("FOLIO_CONTROL as folioControl, ");
            strQuery.append("POR_PAQUETE as porPaquete, ");
            strQuery.append("REFERENCIA_PAQUETE as referenciaPaquete, ");
            strQuery.append("FOLIO_ORIGEN as folioOrigen, ");
            strQuery.append("TIPO_OPERACION_INSTRUCCION as nombreCortoTipoInstruccion, ");
            strQuery.append("ORIGEN as origen, ");
            strQuery.append("TV as tv, ");
            strQuery.append("EMISORA as emisora, ");
            strQuery.append("SERIE as serie, ");
            strQuery.append("CUPON as cupon, ");
            strQuery.append("BOVEDA_VALORES as bovedaValores, ");
            strQuery.append("CLAVE_ESTADO_INSTRUCCION_CAT as claveEstadoInstruccionCat, ");
            strQuery.append("DESC_ESTADO_INSTRUCCION_CAT as descEstadoInstruccionCat, ");
            strQuery.append("ID_INSTITUCION_TRASPASANTE as idInstitucionTraspasante, "); 
            strQuery.append("ID_FOLIO_INSTIT_TRASPASANTE as idFolioInstitucionTraspasante, ");
            strQuery.append("CUENTA_TRASPASANTE as cuentaTraspasante, ");
            strQuery.append("NOMBRE_CORTO_INST_TRASPASANTE as nombreCortoInstitTraspasante, ");
            strQuery.append("ID_INSTITUCION_RECEPTORA as idInstitucionReceptora, "); 
            strQuery.append("ID_FOLIO_INSTIT_RECEPTORA as idFolioInstitucionReceptora, ");
            strQuery.append("NOMBRE_CORTO_INST_RECEPTORA as nombreCortoInstitReceptora, ");
            strQuery.append("CUENTA_RECEPTORA as cuentaReceptora, ");
            strQuery.append("PRECIO_TITULO as precioTitulo, ");
            strQuery.append("CASE WHEN FECHA_REPORTO IS NOT NULL THEN to_char(FECHA_REPORTO, 'dd/mm/yyyy') ELSE '' END as fechaReporto, ");
            strQuery.append("CLAVE_TIPO_MENSAJE_CAT as claveTipoMensajeCat, ");
            strQuery.append("FECHA_HORA_REGISTRO as fechaConcertacion, ");
            strQuery.append("CASE WHEN ID_FOLIO_INSTIT_TRASPASANTE IN ("+ instOcultarFechaHoraCierreOper +") THEN NULL ELSE FECHA_HORA_CIERRE_OPER_TRA END as fechaHoraCierreOperTra, ");
            strQuery.append("CASE WHEN ID_FOLIO_INSTIT_RECEPTORA IN ("+ instOcultarFechaHoraCierreOper +") THEN NULL ELSE FECHA_HORA_CIERRE_OPER_REC END as fechaHoraCierreOperRec, ");
            strQuery.append("FECHA_HORA_ENCOLAMIENTO_TRA as fechaHoraEncolamientoTra, ");
            strQuery.append("FECHA_HORA_ENCOLAMIENTO_REC as fechaHoraEncolamientoRec, ");
            strQuery.append("FECHA_LIQUIDACION as fechaLiquidacion, ");
            strQuery.append("CANTIDAD_TITULOS as cantidadTitulos, ");
            strQuery.append("IMPORTE as importe, ");
            strQuery.append("BOVEDA_EFECTIVO as bovedaEfectivo, ");
            strQuery.append("CASE WHEN TASA_NEGOCIADA IS NULL THEN '0' ELSE TASA_NEGOCIADA END as tasaNegociada, "); 
            strQuery.append("PLAZO_REPORTO as plazoReporto, ");
            strQuery.append("CLAVE_DIVISA as claveDivisa, ");
            strQuery.append("ID_MERCADO as idMercado, ");
            strQuery.append("FOLIO_INSTRUCCION as folioUsuario, ");
            strQuery.append("TOTAL_OPERACIONES_PAQUETE as totalOperacionesPaquete, ");
            strQuery.append("NUMERO_OPERACION_PAQUETE as numeroOperacionPaquete, ");
            strQuery.append("TOTAL_TITULOS_PAQUETE as totalTitulosPaquete, ");
            strQuery.append("TOTAL_IMPORTE_PAQUETE as totalImportePaquete, ");
            strQuery.append("NOMBRE_CUENTA_TRASPASANTE as nombreCuentaTraspasante, ");
            strQuery.append("NOMBRE_CUENTA_RECEPTORA as nombreCuentaReceptora, ");
            strQuery.append("MENSAJE_XML as mensajeXml, ");
            strQuery.append("CASE WHEN TIPO_OPERACION_INSTRUCCION IS NOT NULL AND TIPO_OPERACION_INSTRUCCION = 'M' AND MENSAJE_XML IS NOT NULL THEN 1 ELSE 0 END as conMiscelaneaFiscal ");
            strQuery.append("from V_CONS_INSTR_MATCH_SIN_OPER ");
            strQuery.append("where 1 = 1 ");

            // Estado de la Instruccion
            if (criterio.getIdEstadoInstruccion() > DaliConstants.VALOR_COMBO_TODOS) {
                if (criterio.getIdEstadoInstruccion() == EstadoInstruccionConstants.SIN_MATCH) {
                    strQuery.append("and ESTADO_INSTRUCCION IN (?,?,?) ");
                    parametros.add(EstadoInstruccion.SIN_MATCH.toString());
                    tipos.add(new StringType());
                    parametros.add(EstadoInstruccion.PRE_MATCH.toString());
                    tipos.add(new StringType());
                    parametros.add(EstadoInstruccion.POSIBLE_MATCH.toString());
                    tipos.add(new StringType());
                }
                if (criterio.getIdEstadoInstruccion() == EstadoInstruccionConstants.CANCELADA) {
                    strQuery.append("and ESTADO_INSTRUCCION IN (?,?) ");
                    parametros.add(EstadoInstruccion.CANCELADA.toString());
                    tipos.add(new StringType());
                    parametros.add(EstadoInstruccion.CANCELADA_NO_REQUIERE_MATCH.toString());
                    tipos.add(new StringType());
                }
            }
            else {
                strQuery.append("and ESTADO_INSTRUCCION NOT IN (?, ?, ?) ");
                parametros.add(EstadoInstruccion.CON_MATCH.toString());
                tipos.add(new StringType());
                parametros.add(EstadoInstruccion.CANCELAR_APLICADO.toString());
                tipos.add(new StringType());
                parametros.add(EstadoInstruccion.CANCELAR_NO_APLICADO.toString());
                tipos.add(new StringType());
            }

            // Tipo de mercado
            if (criterio.getIdMercado() != DaliConstants.VALOR_COMBO_TODOS) {
                if (criterio.getIdMercado() == DaliConstants.ID_MERCADO_DINERO) {
                    strQuery.append("and ID_MERCADO IN (?,?) ");
                    parametros.add(DaliConstants.ID_MERCADO_PAPEL_BANCARIO);
                    tipos.add(new LongType());
                    parametros.add(DaliConstants.ID_MERCADO_PAPEL_GUBER);
                    tipos.add(new LongType());
                }
                else {
                    strQuery.append("and ID_MERCADO = ? ");
                    parametros.add(new BigInteger(String.valueOf(criterio.getIdMercado())));
                    tipos.add(new BigIntegerType());
                }
            }

            // Divisa
            if (criterio.getIdDivisa() != DaliConstants.VALOR_COMBO_TODOS && StringUtils.isNotEmpty(criterio.getClaveAlfabetica())) {
                strQuery.append("and CLAVE_DIVISA = ? ");
                parametros.add(criterio.getClaveAlfabetica());
                tipos.add(new StringType());
            }

            // Boveda efectivo
            if (criterio.getIdBovedaEfectivo() != DaliConstants.VALOR_COMBO_TODOS &&
                StringUtils.isNotEmpty(criterio.getNombreCortoBovedaEfectivo())) {
                strQuery.append("and BOVEDA_EFECTIVO = ? ");
                parametros.add(criterio.getNombreCortoBovedaEfectivo());
                tipos.add(new StringType());
            }

            // Boveda valores
            if (criterio.getIdBovedaValores() != DaliConstants.VALOR_COMBO_TODOS &&
                StringUtils.isNotEmpty(criterio.getNombreCortoBovedaValores()) ) {
                strQuery.append("and BOVEDA_VALORES = ? ");
                parametros.add(criterio.getNombreCortoBovedaValores());
                tipos.add(new StringType());
            }

            // Folio usuario
            if (StringUtils.isNotBlank(criterio.getFolioUsuario())) {
                strQuery.append("and FOLIO_INSTRUCCION = ? ");
                parametros.add(criterio.getFolioUsuario());
                tipos.add(new StringType());
            }

            // Tipo Operacion (Instruccion)
            if (criterio.getIdTipoInstruccion().longValue() != DaliConstants.VALOR_COMBO_TODOS) {
                String tipoOperacion = obtenerTipoDeOperacionEquivalenteATipoInstruccion(criterio.getNombreCortoTipoInstruccion());
                strQuery.append("and TIPO_OPERACION_INSTRUCCION = ? ");
                parametros.add(tipoOperacion);
                tipos.add(new StringType());
                if (criterio.getNombreCortoTipoInstruccion().equals("DVPC")) {
                    strQuery.append("and ID_MERCADO = ? ");
                    parametros.add(new BigInteger(String.valueOf(DaliConstants.ID_MERCADO_CAPITALES)));
                    tipos.add(new BigIntegerType());
                }
                if (criterio.getNombreCortoTipoInstruccion().equals("COVE")) {
                    strQuery.append("and ID_MERCADO IN (?,?) ");
                    parametros.add(new BigInteger(String.valueOf(DaliConstants.ID_MERCADO_PAPEL_BANCARIO)));
                    tipos.add(new BigIntegerType());
                    parametros.add(new BigInteger(String.valueOf(DaliConstants.ID_MERCADO_PAPEL_GUBER)));
                    tipos.add(new BigIntegerType());
                }
            }

            // Tipo Mensaje
            if (criterio.getIdTipoMensaje() != DaliConstants.VALOR_COMBO_TODOS) {
                strQuery.append("and CLAVE_TIPO_MENSAJE_CAT = ? ");
                parametros.add(criterio.getClaveTipoMensaje());
                tipos.add(new StringType());
            }

            // Fecha Concertacion (registro)
            if (criterio.getFechaConcertacion() != null) {
                strQuery.append("and TRUNC(FECHA_HORA_REGISTRO) = ? ");
                parametros.add(criterio.getFechaConcertacion());
                tipos.add(new TimestampType());
            }

            // Fecha Liquidacion
            if (criterio.getFechaLiquidacion() != null) {
                strQuery.append("and FECHA_LIQUIDACION = ? ");
                parametros.add(criterio.getFechaLiquidacion());
                tipos.add(new DateType());
            }

            // Roles
            boolean existenCriteriosTraspORecep = 
                    (criterio.getIdInstitucionParticipante() > DaliConstants.VALOR_COMBO_TODOS || 
                     StringUtils.isNotBlank(criterio.getClaveTipoInstitucionParticipante())) || 
                    StringUtils.isNotBlank(criterio.getCuentaParticipante()) || 
                    (criterio.getIdInstitucionContraparte() > DaliConstants.VALOR_COMBO_TODOS || 
                     StringUtils.isNotBlank(criterio.getClaveTipoInstitucionContraparte())) || 
                    StringUtils.isNotBlank(criterio.getCuentaContraparte());

            if (existenCriteriosTraspORecep) {
                strQuery.append(" AND ");

                if (criterio.getRol() == RolConstants.ROL_AMBOS) {
                    strQuery.append(" ( ");
                }

                if (criterio.getRol() == RolConstants.ROL_AMBOS || criterio.getRol() == RolConstants.ROL_TRASPASANTE) {
                    strQuery.append(" ( ");
                    if (criterio.getIdInstitucionParticipante() > DaliConstants.VALOR_COMBO_TODOS || 
                         StringUtils.isNotBlank(criterio.getClaveTipoInstitucionParticipante())) {
                        if (criterio.getIdInstitucionParticipante() > DaliConstants.VALOR_COMBO_TODOS) {
                            strQuery.append("ID_FOLIO_INSTIT_TRASPASANTE = ? ");
                            parametros.add(criterio.getClaveTipoInstitucionParticipante() + 
                                           criterio.getFolioInstitucionParticipante());
                            tipos.add(new StringType());
                        } 
                        else {
                            strQuery.append("substr(ID_FOLIO_INSTIT_TRASPASANTE,1,2) = ? ");
                            parametros.add(criterio.getClaveTipoInstitucionParticipante());
                            tipos.add(new StringType());
                        }
                    }
                    if (StringUtils.isNotBlank(criterio.getCuentaParticipante())) {
                        if (criterio.getIdInstitucionParticipante() > DaliConstants.VALOR_COMBO_TODOS || 
                            StringUtils.isNotBlank(criterio.getClaveTipoInstitucionParticipante())) {
                            strQuery.append(" AND ");
                        }
                        strQuery.append("CUENTA_TRASPASANTE = ? ");
                        parametros.add(criterio.getCuentaParticipante());
                        tipos.add(new StringType());
                    }

                    if (criterio.getIdInstitucionContraparte() > DaliConstants.VALOR_COMBO_TODOS || 
                        StringUtils.isNotBlank(criterio.getClaveTipoInstitucionContraparte())) {
                        if (criterio.getIdInstitucionParticipante() > DaliConstants.VALOR_COMBO_TODOS || 
                             StringUtils.isNotBlank(criterio.getClaveTipoInstitucionParticipante()) || 
                             StringUtils.isNotBlank(criterio.getCuentaParticipante())) {
                            strQuery.append(" AND ");
                        }
                        if (criterio.getIdInstitucionContraparte() > DaliConstants.VALOR_COMBO_TODOS) {
                            strQuery.append("ID_FOLIO_INSTIT_RECEPTORA = ? ");
                            parametros.add(criterio.getClaveTipoInstitucionContraparte() + 
                                           criterio.getFolioInstitucionContraparte());
                            tipos.add(new StringType());
                        } else {
                            strQuery.append("substr(ID_FOLIO_INSTIT_RECEPTORA,1,2) = ? ");
                            parametros.add(criterio.getClaveTipoInstitucionContraparte());
                            tipos.add(new StringType());
                        }
                    }
                    if (StringUtils.isNotBlank(criterio.getCuentaContraparte())) {
                        if ((criterio.getIdInstitucionParticipante() > DaliConstants.VALOR_COMBO_TODOS || 
                             StringUtils.isNotBlank(criterio.getClaveTipoInstitucionParticipante())) || 
                           (criterio.getIdInstitucionContraparte() > DaliConstants.VALOR_COMBO_TODOS || 
                            StringUtils.isNotBlank(criterio.getClaveTipoInstitucionContraparte())) || 
                           StringUtils.isNotBlank(criterio.getCuentaParticipante())) {
                            strQuery.append(" AND ");
                        }
                        strQuery.append("CUENTA_RECEPTORA = ? ");
                        parametros.add(criterio.getCuentaContraparte());
                        tipos.add(new StringType());
                    }
                    strQuery.append(" ) ");
                }

                if (criterio.getRol() == RolConstants.ROL_AMBOS) {
                    strQuery.append(" OR ");
                }

                if (criterio.getRol() == RolConstants.ROL_AMBOS || criterio.getRol() == RolConstants.ROL_RECEPTOR) {
                    strQuery.append(" ( ");
                    if (criterio.getIdInstitucionContraparte() > DaliConstants.VALOR_COMBO_TODOS || 
                           StringUtils.isNotBlank(criterio.getClaveTipoInstitucionContraparte())) {
                        if (criterio.getIdInstitucionContraparte() > DaliConstants.VALOR_COMBO_TODOS) {
                            strQuery.append("ID_FOLIO_INSTIT_TRASPASANTE = ? ");
                            parametros.add(criterio.getClaveTipoInstitucionContraparte() + 
                                           criterio.getFolioInstitucionContraparte());
                            tipos.add(new StringType());
                        } else {
                            strQuery.append("substr(ID_FOLIO_INSTIT_TRASPASANTE,1,2) = ? ");
                            parametros.add(criterio.getClaveTipoInstitucionContraparte());
                            tipos.add(new StringType());
                        }
                    }
                    if (StringUtils.isNotBlank(criterio.getCuentaContraparte())) {
                        if (criterio.getIdInstitucionContraparte() > DaliConstants.VALOR_COMBO_TODOS || 
                            StringUtils.isNotBlank(criterio.getClaveTipoInstitucionContraparte())) {
                            strQuery.append(" AND ");
                        }
                        strQuery.append("CUENTA_TRASPASANTE = ? ");
                        parametros.add(criterio.getCuentaContraparte());
                        tipos.add(new StringType());
                    }

                    if (criterio.getIdInstitucionParticipante() > DaliConstants.VALOR_COMBO_TODOS || 
                        StringUtils.isNotBlank(criterio.getClaveTipoInstitucionParticipante())) {
                        if ((criterio.getIdInstitucionContraparte() > DaliConstants.VALOR_COMBO_TODOS || 
                             StringUtils.isNotBlank(criterio.getClaveTipoInstitucionContraparte())) || 
                             StringUtils.isNotBlank(criterio.getCuentaContraparte())) {
                            strQuery.append(" AND ");
                        }
                        if (criterio.getIdInstitucionParticipante() > DaliConstants.VALOR_COMBO_TODOS) {
                            strQuery.append("ID_FOLIO_INSTIT_RECEPTORA = ? ");
                            parametros.add(criterio.getClaveTipoInstitucionParticipante() + 
                                           criterio.getFolioInstitucionParticipante());
                            tipos.add(new StringType());
                        } else {
                            strQuery.append("substr(ID_FOLIO_INSTIT_RECEPTORA,1,2) = ? ");
                            parametros.add(criterio.getClaveTipoInstitucionParticipante());
                            tipos.add(new StringType());
                        }
                    }
                    if (StringUtils.isNotBlank(criterio.getCuentaParticipante())) {
                        if ((criterio.getIdInstitucionParticipante() > DaliConstants.VALOR_COMBO_TODOS || 
                             StringUtils.isNotBlank(criterio.getClaveTipoInstitucionParticipante())) || 
                            (criterio.getIdInstitucionContraparte() > DaliConstants.VALOR_COMBO_TODOS || 
                             StringUtils.isNotBlank(criterio.getClaveTipoInstitucionContraparte())) || 
                            StringUtils.isNotBlank(criterio.getCuentaContraparte())) {
                            strQuery.append(" AND ");
                        }
                        strQuery.append("CUENTA_RECEPTORA = ? ");
                        parametros.add(criterio.getCuentaParticipante());
                        tipos.add(new StringType());
                    }
                    strQuery.append(" ) ");
                }

                if (criterio.getRol() == RolConstants.ROL_AMBOS) {
                    strQuery.append(" ) ");
                }
            }

            // Tipo de Valor
            if (criterio.getIdTipoValor() > DaliConstants.VALOR_COMBO_TODOS) {
                strQuery.append("and TV = ? ");
                parametros.add(criterio.getClaveTipoValor());
                tipos.add(new StringType());
            }

            // Emisora
            if (criterio.getIdEmisora() > DaliConstants.VALOR_COMBO_TODOS) {
                strQuery.append("and EMISORA = ? ");
                parametros.add(criterio.getDescripcionEmisora());
                tipos.add(new StringType());
            }

            // Serie
            if (StringUtils.isNotBlank(criterio.getSerie())) {
                strQuery.append("and SERIE = ? ");
                parametros.add(criterio.getSerie());
                tipos.add(new StringType());
            }

            // Remitente
            if (criterio.isRemitente()) {
                strQuery.append("and MENSAJE_XML like ? ");
                parametros.add("%<FolioInstitucion>" + 
                                   criterio.getFolioInstitucionParticipante() + 
                               "</FolioInstitucion>%" + 
                               "<IdInstitucion>" + 
                                   criterio.getClaveTipoInstitucionParticipante() + 
                               "</IdInstitucion>%");
                tipos.add(new StringType());
            }

            // Origen
            if (StringUtils.isNotBlank(criterio.getOrigen())) {
                strQuery.append(" and ORIGEN = ? ");
                parametros.add(criterio.getOrigen());
                tipos.add(new StringType());
            }

            // Cantidad de Titulos
            if (NumberUtils.toLong(criterio.getCantidad(), DaliConstants.VALOR_COMBO_TODOS) != DaliConstants.VALOR_COMBO_TODOS) {
                strQuery.append("and CANTIDAD_TITULOS = ? ");
                parametros.add(NumberUtils.toLong(criterio.getCantidad(), DaliConstants.VALOR_COMBO_TODOS));
                tipos.add(new LongType());
            }

            // Monto
            if (NumberUtils.toDouble(criterio.getMonto(), DaliConstants.VALOR_COMBO_TODOS) != DaliConstants.VALOR_COMBO_TODOS) {
                strQuery.append("and TO_CHAR(to_number(IMPORTE),'FM99999999999999.99') = to_char(?,'FM99999999999999.99') ");
                parametros.add(new BigDecimal(NumberUtils.toDouble(criterio.getMonto(), DaliConstants.VALOR_COMBO_TODOS)));
                tipos.add(new BigDecimalType());
            }

            // Referencia Paquete
            if (!StringUtils.isEmpty(criterio.getReferenciaPaquete())) {
                strQuery.append(" and upper(trim(REFERENCIA_PAQUETE)) = upper(trim(?)) " + 
                                "and (ESTADO_INSTRUCCION not in ('SIN_MATCH', 'CON_MATCH'," + 
                                "'SIN_MATCH', 'CANCELADA', 'CANCELAR_APLICADO', " + 
                                "'CANCELAR_NO_APLICADO', 'POSIBLE_MATCH') )"); /*+ 
                                " and match.fechaLiquidacion < current_date()))");*/
                parametros.add(criterio.getReferenciaPaquete());
                tipos.add(new StringType());
            }

            // Folio Control
            if(StringUtils.isNotBlank(criterio.getFolioControl())) {
                strQuery.append("and FOLIO_CONTROL = ? ");
                parametros.add(criterio.getFolioControl());
                tipos.add(new StringType());
            }

            SQLQuery query = session.createSQLQuery(strQuery.toString());
            query.setParameters(parametros.toArray(), tipos.toArray(new Type[] {}));
            query.addScalar("idBitacoraMatch", Hibernate.LONG);
            query.addScalar("folioControl", Hibernate.LONG);
            query.addScalar("porPaquete", Hibernate.BOOLEAN); 
            query.addScalar("referenciaPaquete", Hibernate.STRING);
            query.addScalar("folioOrigen", Hibernate.STRING);
            query.addScalar("nombreCortoTipoInstruccion", Hibernate.STRING);
            query.addScalar("origen", Hibernate.STRING);
            query.addScalar("tv", Hibernate.STRING); 
            query.addScalar("emisora", Hibernate.STRING);
            query.addScalar("serie", Hibernate.STRING);
            query.addScalar("cupon", Hibernate.STRING);
            query.addScalar("bovedaValores", Hibernate.STRING);
            query.addScalar("claveEstadoInstruccionCat", Hibernate.STRING);
            query.addScalar("descEstadoInstruccionCat", Hibernate.STRING);
            query.addScalar("idInstitucionTraspasante", Hibernate.LONG);
            query.addScalar("idFolioInstitucionTraspasante", Hibernate.STRING);
            query.addScalar("cuentaTraspasante", Hibernate.STRING);
            query.addScalar("nombreCortoInstitTraspasante", Hibernate.STRING);
            query.addScalar("idInstitucionReceptora", Hibernate.LONG);
            query.addScalar("idFolioInstitucionReceptora", Hibernate.STRING);
            query.addScalar("nombreCortoInstitReceptora", Hibernate.STRING);
            query.addScalar("cuentaReceptora", Hibernate.STRING);
            query.addScalar("precioTitulo", Hibernate.DOUBLE);
            query.addScalar("fechaReporto", Hibernate.STRING);
            query.addScalar("claveTipoMensajeCat", Hibernate.STRING);
            query.addScalar("fechaConcertacion", Hibernate.TIMESTAMP);
            query.addScalar("fechaHoraCierreOperTra", Hibernate.TIMESTAMP);
            query.addScalar("fechaHoraCierreOperRec", Hibernate.TIMESTAMP);
            query.addScalar("fechaHoraEncolamientoTra", Hibernate.TIMESTAMP);
            query.addScalar("fechaHoraEncolamientoRec", Hibernate.TIMESTAMP);
            query.addScalar("fechaLiquidacion", Hibernate.TIMESTAMP);
            query.addScalar("cantidadTitulos", Hibernate.LONG);
            query.addScalar("importe", Hibernate.DOUBLE); 
            query.addScalar("bovedaEfectivo", Hibernate.STRING);
            query.addScalar("tasaNegociada", Hibernate.DOUBLE); 
            query.addScalar("plazoReporto", Hibernate.LONG);
            query.addScalar("claveDivisa", Hibernate.STRING);
            query.addScalar("idMercado", Hibernate.LONG);
            query.addScalar("folioUsuario", Hibernate.STRING);
            query.addScalar("totalOperacionesPaquete", Hibernate.STRING);
            query.addScalar("numeroOperacionPaquete", Hibernate.STRING);
            query.addScalar("totalTitulosPaquete", Hibernate.STRING);
            query.addScalar("totalImportePaquete", Hibernate.STRING);
            query.addScalar("nombreCuentaTraspasante", Hibernate.STRING);
            query.addScalar("nombreCuentaReceptora", Hibernate.STRING);
            query.addScalar("mensajeXml", Hibernate.STRING);
            query.addScalar("conMiscelaneaFiscal", Hibernate.BOOLEAN); 

            query.setResultTransformer(new AliasToBeanResultTransformer(ConsultaInstruccionesMatchSinOperacion.class));

            parametros.clear();
            parametros = null;
            tipos.clear();
            tipos = null;
            return query;
    }

    /**
     * Metodo que crea el query para realizar la consulta de la busqueda de parcialidades.
     * @param session Sesion de hibernate para crear el query.
     * @param criterio Objeto con los parametros para realizar la consulta.
     * @return SQLQuery generado.
     */
    private SQLQuery crearQueryBuscarParcialidades(Session session, CriterioMatchOperacionesExportacionDTO criterio, String instOcultarFechaHoraCierreOper) {
        List<Object> parametros = new ArrayList<Object>();
        List<Type> tipos = new ArrayList<Type>();
        StringBuilder strQuery = new StringBuilder();

        strQuery.append("select ");
        strQuery.append("ID_INSTRUCCION_OPERACION as idInstruccionOperacion, ");
        strQuery.append("ID_BITACORA_MATCH as idBitacoraMatch, ");
        strQuery.append("CASE WHEN ID_INSTRUCCION_LIQUIDACION IS NOT NULL THEN ID_LIQUIDACION_PARCIAL_MOV ELSE 0 END as folioControl, ");
        strQuery.append("POR_PAQUETE as porPaquete, ");
        strQuery.append("REFERENCIA_PAQUETE as referenciaPaquete, ");
        strQuery.append("FOLIO_CONTROL as folioOrigen, ");
        strQuery.append("(CASE WHEN ORIGEN_INSTITUCION_TRASPASANTE IS NOT NULL THEN ORIGEN_INSTITUCION_TRASPASANTE ELSE '03' END) || '|' || " + 
                        "(CASE WHEN ORIGEN_INSTITUCION_RECEPTORA IS NOT NULL THEN ORIGEN_INSTITUCION_RECEPTORA ELSE '03' END) as origen, ");
        strQuery.append("CASE WHEN TV IS NULL THEN TIPO_VALOR_PENDIENTE ELSE TV END as tv, ");
        strQuery.append("CASE WHEN EMISORA IS NULL THEN EMISORA_PENDIENTE ELSE EMISORA END as emisora, ");
        strQuery.append("CASE WHEN SERIE IS NULL THEN SERIE_PENDIENTE ELSE SERIE END as serie, ");
        strQuery.append("CASE WHEN CUPON IS NULL THEN CUPON_PENDIENTE ELSE CUPON END as cupon, ");
        strQuery.append("BOVEDA_VALORES as bovedaValores, ");
        strQuery.append("'LI' as claveEstadoInstruccionCat, ");
        strQuery.append("'Liquidada' as descEstadoInstruccionCat, ");
        strQuery.append("ID_INSTITUCION_TRASPASANTE as idInstitucionTraspasante, "); 
        strQuery.append("CVE_TIPO_INSTIT_TRASPASANTE as cveTipoInstitucionTraspasante, ");
        strQuery.append("ID_FOLIO_INSTIT_TRASPASANTE as idFolioInstitucionTraspasante, ");
        strQuery.append("CUENTA_TRASPASANTE as cuentaTraspasante, ");
        strQuery.append("ID_INSTITUCION_RECEPTORA as idInstitucionReceptora, ");
        strQuery.append("CVE_TIPO_INSTIT_RECEPTORA as cveTipoInstitucionReceptora, ");
        strQuery.append("ID_FOLIO_INSTIT_RECEPTORA as idFolioInstitucionReceptora, ");
        strQuery.append("CUENTA_RECEPTORA as cuentaReceptora, ");
        strQuery.append("PRECIO_TITULO as precioTitulo, ");
        strQuery.append("NOMBRE_CORTO_TIPO_INSTRUCCION as nombreCortoTipoInstruccion, ");
        strQuery.append("INSTRUCCION as instruccion, ");
        strQuery.append("DESC_TIPO_INSTRUCCION as descTipoInstruccion, ");
        strQuery.append("CLAVE_TIPO_MENSAJE_CAT as claveTipoMensajeCat, ");
        strQuery.append("DESC_TIPO_MENSAJE_CAT as descTipoMensajeCat, ");
        strQuery.append("FECHA_HORA as fechaLiquidacion, ");
        strQuery.append("CASE WHEN ID_FOLIO_INSTIT_TRASPASANTE IN ("+ instOcultarFechaHoraCierreOper +") THEN NULL ELSE FECHA_HORA_CIERRE_OPER_TRA END as fechaHoraCierreOperTra, ");
        strQuery.append("CASE WHEN ID_FOLIO_INSTIT_RECEPTORA IN ("+ instOcultarFechaHoraCierreOper +") THEN NULL ELSE FECHA_HORA_CIERRE_OPER_REC END as fechaHoraCierreOperRec, ");
        strQuery.append("FECHA_HORA_ENCOLAMIENTO_TRA as fechaHoraEncolamientoTra, ");
        strQuery.append("FECHA_HORA_ENCOLAMIENTO_REC as fechaHoraEncolamientoRec, ");
        strQuery.append("CASE WHEN NUMERO_TITULOS IS NOT NULL THEN NUMERO_TITULOS ELSE 0 END as cantidadTitulos, ");
        strQuery.append("CASE WHEN IMPORTE_LIQ_PARCIAL IS NOT NULL THEN IMPORTE_LIQ_PARCIAL ELSE '0' END as importe, ");
        strQuery.append("BOVEDA_EFECTIVO as bovedaEfectivo, ");
        strQuery.append("CASE WHEN TASA_NEGOCIADA IS NULL THEN 0 ELSE TASA_NEGOCIADA END as tasaNegociada, "); 
//        strQuery.append("CASE WHEN PLAZO_REPORTO IS NULL AND ID_ESTADO_INSTRUCCION_CAT <> 6 THEN 0 ELSE PLAZO_REPORTO END as plazoReporto, ");
        strQuery.append("PLAZO_REPORTO as plazoReporto, ");
        strQuery.append("CASE WHEN PLAZO_REPORTO IS NOT NULL AND PLAZO_REPORTO > 0 THEN to_char(FECHA_LIQUIDACION + PLAZO_REPORTO, 'dd/mm/yyyy') " + 
                        " ELSE null END as fechaReporto, ");
        strQuery.append("CLAVE_DIVISA as claveDivisa, ");
        strQuery.append("NOMBRE_CORTO_INST_TRASPASANTE as nombreCortoInstitTraspasante, ");
        strQuery.append("NOMBRE_CORTO_INST_RECEPTORA as nombreCortoInstitReceptora, ");
        strQuery.append("FECHA_CONCERTACION as fechaConcertacion, ");
        strQuery.append("ID_INSTITUCION_BANCO_TRABAJO as idInstitucionBancoTrabajo, ");
        strQuery.append("CVE_TIPO_INSTIT_BANCO_TRABAJO as cveTipoInstitBancoTrabajo, ");
        strQuery.append("FOLIO_INSTIT_BANCO_TRABAJO as folioInstitBancoTrabajo, ");
        strQuery.append("NOMBRE_CORTO_INST_BANCO_TRAB as nombreCortoInstBancoTrab, ");
        strQuery.append("CUENTA_BANCO_TRABAJO as cuentaBancoTrabajo, ");
        strQuery.append("FOLIO_INSTRUCCION_RECEPTORA as folioInstruccionReceptora, ");
        strQuery.append("FOLIO_INSTRUCCION_TRASPASANTE as folioInstruccionTraspasante, ");
        strQuery.append("TIENE_PARCIALIDADES as tieneParcialidades, ");
        strQuery.append("6 as idEstadoInstruccionCat, ");
        strQuery.append("ISIN as isin, ");
        strQuery.append("TOTAL_OPERACIONES_PAQUETE as totalOperacionesPaquete, ");
        strQuery.append("NUMERO_OPERACION_PAQUETE as numeroOperacionPaquete, ");
        strQuery.append("TOTAL_TITULOS_PAQUETE as totalTitulosPaquete, ");
        strQuery.append("TOTAL_IMPORTE_PAQUETE as totalImportePaquete, ");
        strQuery.append("CON_MISCELANEA_FISCAL as conMiscelaneaFiscal, ");
        strQuery.append("PUEDE_CONFIRMAR as puedeConfirmar, ");
        strQuery.append("ID_ERROR_DALI as idError, ");
        strQuery.append("CLAVE_ERROR as claveError, ");
        strQuery.append("DESC_ERROR as descError, ");
        strQuery.append("NOMBRE_CUENTA_TRASPASANTE as nombreCuentaTraspasante, ");
        strQuery.append("NOMBRE_CUENTA_RECEPTORA as nombreCuentaReceptora, ");
        strQuery.append("INTERESES_GENERADOS as interesesGenerados ");
        strQuery.append("from V_CONS_PARCIALIDADES ");
        strQuery.append("where 1 = 1 ");

        armarQueryConsultaOperacionesValorYParcialidades(criterio, strQuery, parametros, tipos);

        // Cantidad de Titulos
        if (NumberUtils.toLong(criterio.getCantidad(), DaliConstants.VALOR_COMBO_TODOS) != DaliConstants.VALOR_COMBO_TODOS) {
            strQuery.append(" and NUMERO_TITULOS = ? ");
            parametros.add(NumberUtils.toLong(criterio.getCantidad(), DaliConstants.VALOR_COMBO_TODOS));
            tipos.add(new LongType());
        }

        // Monto
        if (NumberUtils.toDouble(criterio.getMonto(), DaliConstants.VALOR_COMBO_TODOS) != DaliConstants.VALOR_COMBO_TODOS) {
            strQuery.append(" and TO_CHAR(IMPORTE,'FM99999999999999.99') = to_char(?,'FM99999999999999.99') ");
            parametros.add(new BigDecimal(NumberUtils.toDouble(criterio.getMonto(), DaliConstants.VALOR_COMBO_TODOS)));
            tipos.add(new BigDecimalType());
        }

        // Folio Control
        if (!StringUtils.isEmpty(criterio.getFolioControl())) {
            strQuery.append(" and ID_LIQUIDACION_PARCIAL_MOV = ? ");
            parametros.add(NumberUtils.toLong(criterio.getFolioControl(), DaliConstants.VALOR_COMBO_TODOS));
            tipos.add(new LongType());
        }

        // Fecha Liquidacion
        if (criterio.getFechaLiquidacion() != null) {
            strQuery.append("and trunc(FECHA_HORA) = ? ");
            parametros.add(criterio.getFechaLiquidacion());
            tipos.add(new DateType());
        }

        SQLQuery query = crearQueryConsultaOperacionesValorYParcialidades(session, strQuery, parametros, tipos);

        query.setResultTransformer(new AliasToBeanResultTransformer(ConsultaOperacionValor.class));

        parametros.clear();
        parametros = null;
        tipos.clear();
        tipos = null;

        return query;
    }

    /*
     * @see com.indeval.portaldali.persistence.dao.estatus.EstatusOperacionesMatchDAO#consultarOperacionesValorExportacion(CriterioMatchOperacionesExportacionDTO)
     */
    @SuppressWarnings("unchecked")
	public Collection<ConsultaOperacionesMatch> consultarOperacionesValorExportacion(final CriterioMatchOperacionesExportacionDTO criterio, final String instOcultarFechaHoraCierreOper) {
        return (Collection<ConsultaOperacionesMatch>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                SQLQuery sqlQuery = crearQueryConsultaOperacionesValor(session, criterio, instOcultarFechaHoraCierreOper);

                List<ConsultaOperacionesMatch> resultadosBD = sqlQuery.list();
                
                resultadosBD = generaOtrosDatosOperacionesValor(resultadosBD, criterio, false);

                Set<ConsultaOperacionesMatch> resultados = new TreeSet<ConsultaOperacionesMatch>(new Comparator<ConsultaOperacionesMatch>() {
                    public int compare(ConsultaOperacionesMatch o1, ConsultaOperacionesMatch o2) {
                        String s1 = o1.getFolioControl() + 
                                    o1.getCveTipoInstitucionTraspasante() + 
                                    o1.getIdFolioInstitucionTraspasante().substring(2) + 
                                    o1.getCveTipoInstitucionReceptora() + 
                                    o1.getIdFolioInstitucionReceptora().substring(2);
                        String s2 = o2.getFolioControl() + 
                                    o2.getCveTipoInstitucionTraspasante() + 
                                    o2.getIdFolioInstitucionTraspasante().substring(2) + 
                                    o2.getCveTipoInstitucionReceptora() + 
                                    o2.getIdFolioInstitucionReceptora().substring(2);
                        int resultado = s1.compareTo(s2);
                        return resultado;
                    }
                });

                resultados.addAll(resultadosBD);
                return resultados;
            }
        });
    }

    /*
     * @see com.indeval.portaldali.persistence.dao.estatus.EstatusOperacionesMatchDAO#consultarInstruccionesMatchSinOperacionExportacion(CriterioMatchOperacionesExportacionDTO)
     */
    @SuppressWarnings("unchecked")
	public List<ConsultaOperacionesMatch> consultarInstruccionesMatchSinOperacionExportacion(final CriterioMatchOperacionesExportacionDTO criterio, final String instOcultarFechaHoraCierreOper) {
        return (List<ConsultaOperacionesMatch>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                SQLQuery sqlQuery = crearQueryConsultaInstruccionesMatchSinOperacion(session, criterio, mapeoOperacionInstruccion, instOcultarFechaHoraCierreOper);

                List<ConsultaOperacionesMatch> resultados = sqlQuery.list();

                for (ConsultaOperacionesMatch operacion : resultados) {
                    TipoInstruccionDTO tipoInstr = null;
                    if ("V".equals(operacion.getNombreCortoTipoInstruccion()) && 
                        operacion.getIdMercado().longValue() == DaliConstants.ID_MERCADO_CAPITALES) {
                        tipoInstr = mapeoOperacionInstruccion.get("VC");
                        operacion.setNombreCortoTipoInstruccion(tipoInstr.getNombreCorto());
                        operacion.setInstruccion(tipoInstr.getDescripcion());
                    } else {
                        tipoInstr = mapeoOperacionInstruccion.get(operacion.getNombreCortoTipoInstruccion());
                        if (tipoInstr != null) {
                            operacion.setNombreCortoTipoInstruccion(tipoInstr.getNombreCorto());
                            operacion.setInstruccion(tipoInstr.getDescripcion());
                        }
                        else {
                            operacion.setNombreCortoTipoInstruccion("");
                            operacion.setInstruccion("");
                        }
                    }
                    tipoInstr = null;
                }

                return resultados;
            }
        });
    }

    /*
     * @see com.indeval.portaldali.persistence.dao.estatus.EstatusOperacionesMatchDAO#buscarParcialidadesExportacion(CriterioMatchOperacionesExportacionDTO)
     */
    @SuppressWarnings("unchecked")
	public List<ConsultaOperacionesMatch> buscarParcialidadesExportacion(final CriterioMatchOperacionesExportacionDTO criterio, final String instOcultarFechaHoraCierreOper) {
        return (List<ConsultaOperacionesMatch>) getHibernateTemplate().execute(
            new HibernateCallback() {
                public Object doInHibernate(Session session) throws HibernateException, SQLException {
                    SQLQuery sqlQuery = crearQueryBuscarParcialidades(session, criterio, instOcultarFechaHoraCierreOper);

                    List<ConsultaOperacionesMatch> resultados = sqlQuery.list();
                    
                    resultados = generaOtrosDatosOperacionesValor(resultados, criterio, true);

                    return resultados;
                }
            }
        );
    }

	public DateUtilsDao getDateUtilsDao() {
		return dateUtilsDao;
	}

	public void setDateUtilsDao(DateUtilsDao dateUtilsDao) {
		this.dateUtilsDao = dateUtilsDao;
	}

}
