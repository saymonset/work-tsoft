package com.indeval.portalinternacional.persistence.dao.impl;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.modelo.Divisa;
import com.indeval.portaldali.persistence.util.DateUtils;
import com.indeval.portalinternacional.middleware.servicios.dto.DivisaDTO;
import com.indeval.portalinternacional.middleware.servicios.dto.EstadoPaginacionDTO;
import com.indeval.portalinternacional.middleware.servicios.dto.SettlementDisciplineRegimeVO;
import com.indeval.portalinternacional.middleware.servicios.modelo.*;
import com.indeval.portalinternacional.middleware.servicios.vo.ConciliacionIntDTO;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaSaldoCustodiosInDTO;
import com.indeval.portalinternacional.persistence.dao.DivisaDaliDao;
import com.indeval.portalinternacional.persistence.util.DTOAssembler;
import org.apache.commons.lang.StringUtils;
import org.hibernate.*;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.Type;
import org.springframework.orm.hibernate3.HibernateCallback;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DivisaDaliDaoImpl extends BaseDaoHibernateImpl implements DivisaDaliDao {
	private List<Long> divisa;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.indeval.dali.integration.dao.DivisaDAO#buscarDivisas(EstadoPaginacionDTO)
	 */
	@SuppressWarnings("unchecked")
	public List<DivisaDTO> buscarDivisas(final EstadoPaginacionDTO estadoPaginacion) {

		return (List<DivisaDTO>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery("FROM " + DivisaInt.class.getName() + " d " + " order by d.descripcion");

				if (estadoPaginacion != null) {
					query.setFirstResult(
							(estadoPaginacion.getNumeroPagina() - 1) * estadoPaginacion.getRegistrosPorPagina());
					query.setMaxResults(estadoPaginacion.getRegistrosPorPagina());
				}

				List<DivisaInt> consulta = query.list();
				List<DivisaDTO> resultadosConsulta = new ArrayList<DivisaDTO>();
				for (DivisaInt divisa : consulta) {
					resultadosConsulta.add(DTOAssembler.crearDivisaDTO(divisa));
				}
				return resultadosConsulta;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.indeval.dali.integration.dao.DivisaDAO#obtenerProyeccionDeDivisas()
	 */
	public int obtenerProyeccionDeDivisas() {

		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery("SELECT COUNT(*) FROM " + DivisaInt.class.getName() + " d ");
				Number res = (Number) query.uniqueResult();

				return res.intValue();
			}
		});
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.indeval.dali.integration.dao.DivisaDAO#consultarDivisaPorId(long)
	 */
	public DivisaDTO consultarDivisaPorId(long idDivisa) {
		DivisaDTO divisaDTO = DTOAssembler.crearDivisaDTO(
				(DivisaInt) getHibernateTemplate().get(DivisaInt.class, new BigInteger(String.valueOf(idDivisa))));
		return divisaDTO;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.indeval.dali.integration.dao.DivisaDAO#buscarDivisas(Set,
	 * EstadoPaginacionDTO)
	 */
	@SuppressWarnings("unchecked")
	public List<DivisaDTO> buscarDivisas(List<Long> idsDivisa, final EstadoPaginacionDTO estadoPaginacion) {

		final ArrayList<Object> params = new ArrayList<Object>();
		final ArrayList<Object> tipos = new ArrayList<Object>();
		final StringBuffer querySting = new StringBuffer();
		querySting.append("FROM " + DivisaInt.class.getName() + " div ");

		querySting.append("WHERE div.idDivisa in " + "(" + obtenerCriterioDivisas(idsDivisa, params, tipos) + ") ");

		querySting.append(" ");

		return (List<DivisaDTO>) getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(querySting.toString());

				if (estadoPaginacion != null) {
					query.setFirstResult(
							(estadoPaginacion.getNumeroPagina() - 1) * estadoPaginacion.getRegistrosPorPagina());
					query.setMaxResults(estadoPaginacion.getRegistrosPorPagina());
				}
				query.setParameters(params.toArray(), tipos.toArray(new Type[] {}));
				List<DivisaInt> consulta = query.list();
				List<DivisaDTO> resultadosConsulta = new ArrayList<DivisaDTO>();
				for (DivisaInt divisa : consulta) {

					resultadosConsulta.add(DTOAssembler.crearDivisaDTO(divisa));
				}
				return resultadosConsulta;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.indeval.dali.integration.dao.DivisaDAO#obtenerProyeccionDeDivisas(java.
	 * util.Set)
	 */
	public int obtenerProyeccionDeDivisas(List<Long> idsDivisa) {
		final ArrayList<Object> params = new ArrayList<Object>();
		final ArrayList<Object> tipos = new ArrayList<Object>();
		final StringBuffer querySting = new StringBuffer();

		querySting.append("SELECT COUNT(*) FROM " + DivisaInt.class.getName() + " div ");
		querySting.append("WHERE div.idDivisa in " + "(" + obtenerCriterioDivisas(idsDivisa, params, tipos) + ") ");
		querySting.append(" ");

		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(querySting.toString());
				query.setParameters(params.toArray(), tipos.toArray(new Type[] {}));

				Number resultado = (Number) query.uniqueResult();

				return resultado.intValue();
			}
		});
	}

	/**
	 * Construye una sentencia de HQL para obtener los distintos ID de divisa
	 * válidos
	 *
	 * @param idsDivisa Lista de identificadores de divisas a filtrar
	 * @param params    Lista de parámetros utilizados en el query, este parámetro
	 *                  sirve para que la función agregue el valor del parámetro
	 *                  que utilizar
	 * @param tipos     Lista de tipos de datos utilizados en el query, este
	 *                  parámetro sirve para que la función agregue el tipo de
	 *                  parámetro que utilizar
	 * @return Sentencia HQL para obtener los distintos ID de divisa
	 */
	private String obtenerCriterioDivisas(List<Long> idsDivisa, ArrayList<Object> params, ArrayList<Object> tipos) {
		StringBuffer query = new StringBuffer();
		boolean ponerComa = false;
		for (Long id : idsDivisa) {
			if (ponerComa) {
				query.append(",");
			}
			query.append(new BigInteger(String.valueOf(id.toString())));
			ponerComa = true;
		}
		if (!ponerComa) {
			query.append("-1");
		}

		return query.toString();
	}

	public List<Long> getDivisa() {
		return divisa;
	}

	public void setDivisa(List<Long> divisa) {
		this.divisa = divisa;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.persistence.dao.common.DivisaDaliDAO#
	 * buscarDivisasPorTipoOperacion(com.indeval.portaldali.middleware.dto.util.
	 * EstadoPaginacionDTO, com.indeval.portaldali.persistence.model.TipoOperacion)
	 */
	@SuppressWarnings("unchecked")
	public List<DivisaDTO> buscarDivisasPorTipoInstruccion(final EstadoPaginacionDTO estadoPaginacion,
			final BigInteger idTipoInstruccion) {
		return (List<DivisaDTO>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery("FROM " + DivisaInt.class.getName() + " d " + " where d.idDivisa IN ( "
						+ " SELECT DISTINCT(tid.divisa.idDivisa) FROM " + TipoInstruccionDivisa.class.getName()
						+ " tid " + " WHERE tid.tipoInstruccion.idTipoInstruccion = " + idTipoInstruccion + " ) "
						+ " order by d.descripcion");

				if (estadoPaginacion != null) {
					query.setFirstResult(
							(estadoPaginacion.getNumeroPagina() - 1) * estadoPaginacion.getRegistrosPorPagina());
					query.setMaxResults(estadoPaginacion.getRegistrosPorPagina());
				}

				List<DivisaInt> consulta = query.list();
				List<DivisaDTO> resultadosConsulta = new ArrayList<DivisaDTO>();
				for (DivisaInt divisa : consulta) {
					resultadosConsulta.add(DTOAssembler.crearDivisaDTO(divisa));
				}
				return resultadosConsulta;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.persistence.dao.common.DivisaDaliDAO#
	 * obtenerDivisaPorClaveAlfavetica(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public DivisaDTO obtenerDivisaPorClaveAlfavetica(final String claveDivisa) {
		return (DivisaDTO) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(
						"FROM " + DivisaInt.class.getName() + " d " + " where d.claveAlfabetica = '" + claveDivisa + "' ");
				List<DivisaInt> consulta = query.list();
				DivisaDTO divisaDTO = null;
				if (consulta != null && consulta.size() > 0) {
					divisaDTO = DTOAssembler.crearDivisaDTO(consulta.get(0));
				}
				return divisaDTO;
			}
		});
	}

	public Divisa findDivisaByClaveAlfabetica(final String claveDivisa) {
		return (Divisa) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(
						" FROM " + Divisa.class.getName() + " d " + " where d.claveAlfabetica = '" + claveDivisa + "' ");
				Divisa divisa = (Divisa) query.uniqueResult();

				return divisa;
			}
		});
	}

		/*
		 * (non-Javadoc)
		 *
		 * @see com.indeval.portaldali.persistence.dao.common.DivisaDaliDAO#
		 * buscarDivisasPorTipoOperacion(com.indeval.portaldali.middleware.dto.util.
		 * EstadoPaginacionDTO, com.indeval.portaldali.persistence.model.TipoOperacion)
		 */
		@SuppressWarnings("unchecked")

		public List<DivisaDTO> findDivisaByBovedad(final Long idBoveda)  {
			final StringBuilder query = new StringBuilder();
			query.append(" SELECT cd.ID_DIVISA as id, cd.CLAVE_ALFABETICA as claveAlfabetica, " +
					"cd.CLAVE_NUMERICA  as claveNumerica, cd.DESCRIPCION as descripcion ");
			query.append(" FROM C_DIVISA cd, R_DIVISA_BOVEDA rdb  ");
			query.append(" WHERE cd.ID_DIVISA = rdb.ID_DIVISA  and rdb.ID_BOVEDA =  "+idBoveda);

			return (List) getHibernateTemplate().execute(new HibernateCallback() {

				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					SQLQuery sqlQuery = session.createSQLQuery(query.toString());

					sqlQuery.addScalar("id", Hibernate.LONG);
					sqlQuery.addScalar("claveAlfabetica", Hibernate.STRING);
					sqlQuery.addScalar("claveNumerica", Hibernate.STRING);
					sqlQuery.addScalar("descripcion", Hibernate.STRING);

					sqlQuery.setResultTransformer(Transformers.aliasToBean(DivisaDTO.class));
					return sqlQuery.list();
				}
			});
		}
		/**BORRAR INICIO*/
		/**BORRAR */

	/**
	 * Genera parametros para conciliacion
	 * @param consultaSaldoCustodiosInDTO
	 * @return DetachedCriteria
	 */
	private DetachedCriteria paramsConsultaSaldoCustodios(ConsultaSaldoCustodiosInDTO consultaSaldoCustodiosInDTO){
		//Criteria
		DetachedCriteria criteria = DetachedCriteria.forClass(SaldoNombradaInt.class);

//
//		select ins.razon_social, ins.id_tipo_institucion, ins.id_institucion, cue.cuenta, nom.id_divisa, nom.id_boveda, nom.saldo_disponible, nom.saldo_no_disponible
//		from DALI_ADMIN.t_saldo_nombrada nom
//		inner join DALI_ADMIN.c_cuenta_nombrada cue
//		on cue.id_cuenta_nombrada = nom.id_cuenta
//		inner join DALI_ADMIN.c_institucion ins
//		on ins.id_institucion = cue.id_institucion
//		where nom.ID_DIVISA=3
//		AND nom.id_boveda = 13
//		AND nom.ID_CUENTA <> 4040;
		// Realiza las uniones necesarias utilizando el método createAlias
		criteria.createAlias("divisa", "div");
		criteria.createAlias("boveda", "bov");
		criteria.createAlias("cuentaNombrada", "cue");
		criteria.createAlias("cue.institucion", "ins");
		// Agrega las restricciones utilizando el método add
		if(consultaSaldoCustodiosInDTO.getDivisaDali() != null ){
			BigInteger divisaId = new BigInteger(consultaSaldoCustodiosInDTO.getDivisaDali());
		//	criteria.add(Restrictions.eq("div.idDivisa", divisaId));
			criteria.add(Restrictions.eq("idDivisa", divisaId));
		}
		if(consultaSaldoCustodiosInDTO.getDivisaDali() != null ){
		//	Long bovedaId = new Long(consultaSaldoCustodiosInDTO.getBovedaDali());
 		//	criteria.add(Restrictions.eq("bov.idBoveda", bovedaId));
			BigInteger bovedaId = new BigInteger(consultaSaldoCustodiosInDTO.getBovedaDali());
			criteria.add(Restrictions.eq("idBoveda", bovedaId));
		}
		if(consultaSaldoCustodiosInDTO.getIdCuenta() != null ){
			BigInteger idCuenta = new BigInteger(consultaSaldoCustodiosInDTO.getIdCuenta());
			criteria.add(Restrictions.ne("idCuenta", idCuenta));
		}
//		criteria.add(Restrictions.eq("sn.idDivisa", consultaSaldoCustodiosInDTO.getDivisaDali()));
//		criteria.add(Restrictions.eq("sn.idBoveda", consultaSaldoCustodiosInDTO.getBovedaDali()));
//		criteria.add(Restrictions.ne("sn.idCuenta", 4040));

		/*================PARAMETROS==========================*/

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria;
	}

	@Override
	public PaginaVO consultaSaldoCustodio(ConsultaSaldoCustodiosInDTO consultaSaldoCustodiosInDTO, PaginaVO pagina) throws BusinessException {
		try {
			if(pagina == null){
				pagina = new PaginaVO();
			}
			final Integer offset = pagina.getOffset() != null ? pagina.getOffset():null;
			final Integer regxpag = pagina.getRegistrosXPag() != null ? pagina.getRegistrosXPag():null;

			final DetachedCriteria criteria=paramsConsultaSaldoCustodios(consultaSaldoCustodiosInDTO);

			//Callback
			HibernateCallback hibernateCallback = new HibernateCallback() {
				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					Criteria crit = criteria.getExecutableCriteria(session);
					if ( offset != null && regxpag !=null && regxpag != PaginaVO.TODOS) {
						crit.setMaxResults(regxpag);
						crit.setFetchSize(regxpag);
						crit.setFirstResult(offset);
					}
					return crit.list();
				}
			};
			//Ejecucion
			@SuppressWarnings("unchecked")
			List<SaldoNombradaInt> saldoCustodios = (List<SaldoNombradaInt>)this.getHibernateTemplate().executeFind(hibernateCallback);
			if(saldoCustodios != null){
				pagina.setRegistros(saldoCustodios);
			}
			final DetachedCriteria criteriaSum = paramsConsultaSaldoCustodios(consultaSaldoCustodiosInDTO);
			Integer tam = (Integer) this.getHibernateTemplate().execute(new HibernateCallback(){
				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					criteriaSum.setProjection(Projections.rowCount());
					Criteria crit = criteriaSum.getExecutableCriteria(session);
					return crit.uniqueResult();
				}

			});
			pagina.setTotalRegistros(tam);
		}catch (Exception e){
			System.out.println("e.toString() = " + e.toString());
		}

		return pagina;
	}


}
