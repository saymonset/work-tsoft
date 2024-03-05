/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * DivisaDaliDAOImpl.java
 * 04/03/2008
 */
package com.indeval.portaldali.persistence.dao.common.impl;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.type.BigIntegerType;
import org.hibernate.type.Type;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.indeval.portaldali.middleware.dto.DivisaDTO;
import com.indeval.portaldali.middleware.dto.util.DTOAssembler;
import com.indeval.portaldali.middleware.dto.util.EstadoPaginacionDTO;
import com.indeval.portaldali.persistence.dao.common.DivisaDaliDAO;
import com.indeval.portaldali.persistence.model.Boveda;
import com.indeval.portaldali.persistence.model.Divisa;
import com.indeval.portaldali.persistence.model.TipoInstruccion;
import com.indeval.portaldali.persistence.model.TipoInstruccionDivisa;
import com.indeval.portaldali.persistence.model.TipoOperacion;

/**
 * Objeto de acceso a la base de datos para las operaciones relacionadas con el
 * catálogo de divisas.
 *
 * @author Emigdio Hernández
 *
 */
public class DivisaDaliDAOImpl extends HibernateDaoSupport implements DivisaDaliDAO {


	private List<Long> divisa;
	/*
	 * (non-Javadoc)
	 *
	 * @see com.indeval.dali.integration.dao.DivisaDAO#buscarDivisas(EstadoPaginacionDTO)
	 */
	@SuppressWarnings("unchecked")
	public List<DivisaDTO> buscarDivisas(final EstadoPaginacionDTO estadoPaginacion) {

		return (List<DivisaDTO>) getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery("FROM " + Divisa.class.getName() + " d " +
				" order by d.descripcion");

				if (estadoPaginacion != null) {
					query.setFirstResult((estadoPaginacion.getNumeroPagina() - 1) * estadoPaginacion.getRegistrosPorPagina());
					query.setMaxResults(estadoPaginacion.getRegistrosPorPagina());
				}

				List<Divisa> consulta = query.list();
				List<DivisaDTO> resultadosConsulta = new ArrayList<DivisaDTO>();
				for (Divisa divisa : consulta) {
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


		return (Integer) getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery("SELECT COUNT(*) FROM " + Divisa.class.getName() + " d ");
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
		DivisaDTO divisaDTO = DTOAssembler.crearDivisaDTO((Divisa) getHibernateTemplate().get(Divisa.class, new BigInteger(String.valueOf(idDivisa))));
		return divisaDTO;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.indeval.dali.integration.dao.DivisaDAO#buscarDivisas(Set,
	 *      EstadoPaginacionDTO)
	 */
	@SuppressWarnings("unchecked")
	public List<DivisaDTO> buscarDivisas(List<Long> idsDivisa, final EstadoPaginacionDTO estadoPaginacion) {

		final ArrayList<Object> params = new ArrayList<Object>();
		final ArrayList<Object> tipos = new ArrayList<Object>();
		final StringBuffer querySting = new StringBuffer();
		querySting.append("FROM " + Divisa.class.getName() + " div ");

		querySting.append("WHERE div.idDivisa in " + "(" + obtenerCriterioDivisas(idsDivisa, params, tipos) + ") ");

		querySting.append(" ");

		return (List<DivisaDTO>)getHibernateTemplate().executeFind(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(querySting.toString());

				if (estadoPaginacion != null) {
					query.setFirstResult((estadoPaginacion.getNumeroPagina() - 1) * estadoPaginacion.getRegistrosPorPagina());
					query.setMaxResults(estadoPaginacion.getRegistrosPorPagina());
				}
				query.setParameters(params.toArray(), tipos.toArray(new Type[] {}));
				List<Divisa> consulta = query.list();
				List<DivisaDTO> resultadosConsulta = new ArrayList<DivisaDTO>();
				for (Divisa divisa : consulta) {

					resultadosConsulta.add(DTOAssembler.crearDivisaDTO(divisa));
				}
				return resultadosConsulta;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.indeval.dali.integration.dao.DivisaDAO#obtenerProyeccionDeDivisas(java.util.Set)
	 */
	public int obtenerProyeccionDeDivisas(List<Long> idsDivisa) {
		final ArrayList<Object> params = new ArrayList<Object>();
		final ArrayList<Object> tipos = new ArrayList<Object>();
		final StringBuffer querySting = new StringBuffer();

		querySting.append("SELECT COUNT(*) FROM " + Divisa.class.getName() + " div ");
		querySting.append("WHERE div.idDivisa in " + "(" + obtenerCriterioDivisas(idsDivisa, params, tipos) + ") ");
		querySting.append(" ");

		return (Integer) getHibernateTemplate().execute(new HibernateCallback(){
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
	 * @param idsDivisa
	 *            Lista de identificadores de divisas a filtrar
	 * @param params
	 *            Lista de parámetros utilizados en el query, este parámetro
	 *            sirve para que la función agregue el valor del parámetro que
	 *            utilizar
	 * @param tipos
	 *            Lista de tipos de datos utilizados en el query, este parámetro
	 *            sirve para que la función agregue el tipo de parámetro que
	 *            utilizar
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
	 * @see com.indeval.portaldali.persistence.dao.common.DivisaDaliDAO#buscarDivisasPorTipoOperacion(com.indeval.portaldali.middleware.dto.util.EstadoPaginacionDTO, com.indeval.portaldali.persistence.model.TipoOperacion)
	 */
	@SuppressWarnings("unchecked")
	public List<DivisaDTO> buscarDivisasPorTipoInstruccion(
			final EstadoPaginacionDTO estadoPaginacion, final BigInteger idTipoInstruccion) {
		return (List<DivisaDTO>) getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery("FROM " + Divisa.class.getName() + " d " +
				" where d.idDivisa IN ( " +
				" SELECT DISTINCT(tid.divisa.idDivisa) FROM " + TipoInstruccionDivisa.class.getName() + " tid " +
				" WHERE tid.tipoInstruccion.idTipoInstruccion = "+ idTipoInstruccion + " ) " +
				" order by d.descripcion");

				if (estadoPaginacion != null) {
					query.setFirstResult((estadoPaginacion.getNumeroPagina() - 1) * estadoPaginacion.getRegistrosPorPagina());
					query.setMaxResults(estadoPaginacion.getRegistrosPorPagina());
				}

				List<Divisa> consulta = query.list();
				List<DivisaDTO> resultadosConsulta = new ArrayList<DivisaDTO>();
				for (Divisa divisa : consulta) {
					resultadosConsulta.add(DTOAssembler.crearDivisaDTO(divisa));
				}
				return resultadosConsulta;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * @see com.indeval.portaldali.persistence.dao.common.DivisaDaliDAO#obtenerDivisaPorClaveAlfavetica(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public DivisaDTO obtenerDivisaPorClaveAlfavetica(final String claveDivisa){
		return (DivisaDTO) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				Query query = session.createQuery("FROM " + Divisa.class.getName() + " d " +
						" where d.claveAlfabetica = '"+ claveDivisa + "' " );
				List<Divisa> consulta = query.list();
				DivisaDTO divisaDTO = null;
				if (consulta != null && consulta.size() > 0){
					divisaDTO = DTOAssembler.crearDivisaDTO(consulta.get(0));
				}
				return divisaDTO;
			}
		});
	}


}
