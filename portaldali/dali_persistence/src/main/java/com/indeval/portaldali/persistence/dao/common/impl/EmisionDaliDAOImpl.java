/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * EmisionDaliDAOImpl.java
 * 04/03/2008
 */
package com.indeval.portaldali.persistence.dao.common.impl;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.BigIntegerType;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.indeval.portaldali.middleware.dto.EmisionDTO;
import com.indeval.portaldali.middleware.dto.SerieDTO;
import com.indeval.portaldali.middleware.dto.util.DTOAssembler;
import com.indeval.portaldali.middleware.dto.util.EstadoPaginacionDTO;
import com.indeval.portaldali.middleware.dto.util.EstadoPaginacionExtended;
import com.indeval.portaldali.middleware.services.common.constants.DaliConstants;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import com.indeval.portaldali.persistence.dao.common.EmisionDaliDAO;
import com.indeval.portaldali.persistence.model.Cupon;
import com.indeval.portaldali.persistence.model.Emision;
import com.indeval.portaldali.persistence.util.Constantes;

/**
 * Objeto de acceso a la base de datos para las operaciones relacionadas con una
 * emisión.
 * 
 * @author Emigdio Hernández
 * 
 */
public class EmisionDaliDAOImpl extends HibernateDaoSupport implements EmisionDaliDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.dao.EmisionDAO#buscarSeries(com.indeval.estadocuenta.core.application.dto.TipoValorDTO,
	 *      com.indeval.estadocuenta.core.application.dto.EmisoraDTO)
	 */
	public List<SerieDTO> buscarSeries(SerieDTO criterio) {

		StringBuffer query = new StringBuffer();
		ArrayList<Object> params = new ArrayList<Object>();
		query.append("SELECT distinct emision.serie from " + Emision.class.getName() + " emision ");

		if (criterio.getTipoValor() != null && criterio.getTipoValor().getId() > 0) {
			query.append(" WHERE emision.idInstrumento = ? ");
			params.add(new BigInteger(String.valueOf(criterio.getTipoValor().getId())));
		}

		if (criterio.getTipoValor() != null && criterio.getTipoValor().getMercado() != null && criterio.getTipoValor().getMercado().getId() > 0) {
			query.append(obtenerPrefijoCondicion(params.size()));
			if (criterio.getTipoValor().getMercado().getId() != DaliConstants.ID_MERCADO_DINERO) {
				query.append(" emision.instrumento.mercado.idMercado = ?");
				params.add(new BigInteger(String.valueOf(criterio.getTipoValor().getMercado().getId())));
			} else {
				query.append(" emision.instrumento.mercado.idMercado IN (?,?) ");
				params.add(new BigInteger(String.valueOf(DaliConstants.ID_MERCADO_PAPEL_BANCARIO)));
				params.add(new BigInteger(String.valueOf(DaliConstants.ID_MERCADO_PAPEL_GUBER)));
			}
		}

		if (criterio.getEmisora() != null && criterio.getEmisora().getId() > 0) {
			query.append(obtenerPrefijoCondicion(params.size()));
			query.append(" emision.idEmisora = ?");
			params.add(new BigInteger(String.valueOf(criterio.getEmisora().getId())));
		}

		query.append(" ORDER BY emision.serie ");
		Iterator emisionesBO = getHibernateTemplate().find(query.toString(), params.toArray()).iterator();

		List<SerieDTO> resultado = new ArrayList<SerieDTO>();
		while (emisionesBO.hasNext()) {

			SerieDTO serie = new SerieDTO();
			serie.setSerie((String) emisionesBO.next());
			serie.setDescripcion(serie.getSerie());
			resultado.add(serie);

		}

		return resultado;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.persistence.dao.common.EmisionDaliDAO#buscarEmisionesPorCupon(com.indeval.portaldali.middleware.dto.EmisionDTO,
	 *      com.indeval.portaldali.middleware.dto.util.EstadoPaginacionDTO)
	 */
	@SuppressWarnings("unchecked")
	public List<EmisionDTO> buscarEmisionesPorCupon(EmisionDTO criterio, final EstadoPaginacionExtended estadoPaginacion, Long[] identificadoresMercado) {
		

		final StringBuffer queryString = new StringBuffer();
		final ArrayList<Object> params = new ArrayList<Object>();
		final ArrayList<Type> tipos = new ArrayList<Type>();

		queryString.append("FROM " + Cupon.class.getName() + " c ");
		queryString.append("	join fetch c.emision ");
		queryString.append("	join fetch c.emision.emisora ");
		queryString.append("	join fetch c.emision.instrumento ");
		queryString.append("	join fetch c.emision.instrumento.mercado ");
		queryString.append("	join fetch c.estadoCupon ");

		queryString.append("	WHERE c.idEstadoCupon = ? ");
		params.add(new BigInteger(String.valueOf(DaliConstants.ID_CUPON_VIGENTE)));
		tipos.add(new BigIntegerType());

		if (identificadoresMercado != null && identificadoresMercado.length > 0) {
			queryString.append(obtenerPrefijoCondicion(params.size()));
			queryString.append(" c.emision.instrumento.idMercado in ( ");
			for (int i = 0; i < identificadoresMercado.length; i++) {
				if (i > 0) {
					queryString.append(",");
				}
				queryString.append("?");
				params.add(new BigInteger(String.valueOf(identificadoresMercado[i])));
				tipos.add(new BigIntegerType());

			}
			queryString.append(" ) ");
		}

		queryString.append(construirCriterioEmision(criterio, "c.emision", params, tipos));

		queryString.append("	ORDER BY c.emision.instrumento.claveTipoValor, c.emision.emisora.descripcion, c.emision.serie, c.claveCupon");

		return (List<EmisionDTO>) getHibernateTemplate().execute(new HibernateCallback() {
			/*
			 * (non-Javadoc)
			 * @see org.springframework.orm.hibernate3.HibernateCallback#doInHibernate(org.hibernate.Session)
			 */
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(queryString.toString());
				query.setParameters(params.toArray(), tipos.toArray(new Type[] {}));

				if (estadoPaginacion != null) {

					query.setFirstResult(estadoPaginacion.getIndice());
					query.setMaxResults(estadoPaginacion.getRegistrosPorPagina());
					query.setFetchSize(estadoPaginacion.getRegistrosPorPagina());
				}
				
				List<EmisionDTO> emisiones = new ArrayList<EmisionDTO>();
				List<Cupon> cupones = query.list();
				for (Cupon cupon : cupones) {
					EmisionDTO emision = DTOAssembler.crearEmisionDTO(cupon.getEmision());
					emision.setCupon(cupon.getClaveCupon());
					emisiones.add(emision);
				}
			
				return emisiones;
			}
			
		});
	}

	public Long obtenerProyeccionBuscarEmisionesPorCupon(EmisionDTO criterio, Long[] identificadoresMercado) {

		final StringBuffer queryString = new StringBuffer();
		final ArrayList<Object> params = new ArrayList<Object>();
		final ArrayList<Type> tipos = new ArrayList<Type>();

		queryString.append("SELECT COUNT(*) FROM " + Cupon.class.getName() + " c ");

		queryString.append("	WHERE c.idEstadoCupon = ? ");
		params.add(new BigInteger(String.valueOf(DaliConstants.ID_CUPON_VIGENTE)));
		tipos.add(new BigIntegerType());

		if (identificadoresMercado != null && identificadoresMercado.length > 0) {
			queryString.append(obtenerPrefijoCondicion(params.size()));
			queryString.append(" c.emision.instrumento.idMercado in ( ");
			for (int i = 0; i < identificadoresMercado.length; i++) {
				if (i > 0) {
					queryString.append(",");
				}
				queryString.append("?");
				params.add(new BigInteger(String.valueOf(identificadoresMercado[i])));
				tipos.add(new BigIntegerType());

			}
			queryString.append(" ) ");
		}

		queryString.append(construirCriterioEmision(criterio, "c.emision", params, tipos));

		
		return (Long)getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(queryString.toString());
				query.setParameters(params.toArray(), tipos.toArray(new Type[] {}));

				return (Long) query.uniqueResult();
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.dao.EmisionDAO#consultarEmisiones(com.indeval.estadocuenta.core.application.dto.EmisionDTO)
	 */
	@SuppressWarnings("unchecked")
	public List<EmisionDTO> consultarEmisiones(EmisionDTO criterio, final EstadoPaginacionDTO estadoPaginacion) {

		final StringBuffer queryString = new StringBuffer();
		final ArrayList<Object> params = new ArrayList<Object>();
		final ArrayList<Type> tipos = new ArrayList<Type>();

		queryString.append("FROM " + Emision.class.getName() + " e " + "join fetch e.instrumento " + "join fetch e.instrumento.mercado "
				+ "join fetch e.emisora ");

		queryString.append(construirCriterioEmision(criterio, "e", params, tipos));

		
		return (List<EmisionDTO>)getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(queryString.toString());
				query.setParameters(params.toArray(), tipos.toArray(new Type[] {}));

				if (estadoPaginacion != null) {

					query.setFirstResult((estadoPaginacion.getNumeroPagina() - 1) * estadoPaginacion.getRegistrosPorPagina());
					query.setMaxResults(estadoPaginacion.getRegistrosPorPagina());
					query.setFetchSize(estadoPaginacion.getRegistrosPorPagina());
				}

				Iterator emisionesBO = query.list().iterator();

				List<EmisionDTO> resultado = new ArrayList<EmisionDTO>();
				while (emisionesBO.hasNext()) {

					resultado.add(DTOAssembler.crearEmisionDTO((Emision) emisionesBO.next()));
				}
				return resultado;
			}
		});
	}

	/**
	 * Determina si antes de incluir un nuevo parámetro se incluye una condición
	 * WHERE o una condición AND
	 * 
	 * @param size
	 * @return
	 */
	private Object obtenerPrefijoCondicion(int size) {
		if (size > 0) {
			return " AND ";
		}
		return " WHERE ";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.dao.EmisionDAO#obtenerProyeccionDeEmisiones(com.indeval.estadocuenta.core.application.dto.EmisionDTO)
	 */
	public int obtenerProyeccionDeEmisiones(EmisionDTO criterio) {
		final StringBuffer queryString = new StringBuffer();
		final ArrayList<Object> params = new ArrayList<Object>();
		final ArrayList<Type> tipos = new ArrayList<Type>();

		queryString.append("SELECT COUNT(*) FROM " + Emision.class.getName() + " e ");

		queryString.append(construirCriterioEmision(criterio, "e", params, tipos));

		
		return (Integer)getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(queryString.toString());
				query.setParameters(params.toArray(), tipos.toArray(new Type[] {}));

				Number res = (Number) query.uniqueResult();

				return res.intValue();
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.dao.EmisionDAO#consultarEmisionPorId(long)
	 */
	public EmisionDTO consultarEmisionPorId(long idEmision) {

		final StringBuffer queryString = new StringBuffer();
		final ArrayList<Object> params = new ArrayList<Object>();
		final ArrayList<Type> tipos = new ArrayList<Type>();

		queryString.append("FROM " + Emision.class.getName() + " e " + "join fetch e.instrumento " + "join fetch e.instrumento.mercado "
				+ "join fetch e.emisora ");
		queryString.append("WHERE e.idEmision = ?");
		params.add(new BigInteger(String.valueOf(idEmision)));
		tipos.add(new BigIntegerType());
		
		return (EmisionDTO)getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(queryString.toString());
				query.setParameters(params.toArray(), tipos.toArray(new Type[] {}));

				return DTOAssembler.crearEmisionDTO((Emision) query.list().get(0));
			}
		});
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portaldali.persistence.dao.common.EmisionDaliDAO#consultarEmisionPorTVEmisoraSerie(java.lang.String, java.lang.String, java.lang.String)
	 */
	public EmisionDTO consultarEmisionLiberadaPorTVEmisoraSerie(final String tv, final String emisora, final String serie) {
        HibernateCallback hibernateCallback = new HibernateCallback() {
            public Object doInHibernate(final Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(Emision.class);
				criteria.createAlias("instrumento", "i");
				criteria.createAlias("emisora", "e");
    			if(StringUtils.isNotBlank(tv)) {
    				criteria.add(Restrictions.eq("i.claveTipoValor", tv));
    			}
    			if(StringUtils.isNotBlank(emisora)) {
    				criteria.add(Restrictions.eq("e.clavePizarra", emisora));
    			}
    			if(StringUtils.isNotBlank(serie)) {
    				criteria.add(Restrictions.eq("serie", serie));
    			}
    			criteria.add(Restrictions.eq("idEstatusEmision", new BigInteger("3")));
				return criteria.uniqueResult();
        	}
        };
		Emision emision = (Emision) getHibernateTemplate().execute(hibernateCallback);
		return DTOAssembler.crearEmisionDTO(emision);
	}
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.dao.EmisionDAO#consultarEmisiones(com.indeval.estadocuenta.core.application.dto.EmisionDTO,
	 *      com.indeval.estadocuenta.core.application.dto.EstadoPaginacionDTO,
	 *      java.util.Set)
	 */
	@SuppressWarnings("unchecked")
	public List<EmisionDTO> consultarEmisiones(EmisionDTO criterio, EstadoPaginacionDTO estadoPaginacion, List<Long> emisionesValidas) {
		final StringBuffer queryString = new StringBuffer();
		final ArrayList<Object> params = new ArrayList<Object>();
		final ArrayList<Type> tipos = new ArrayList<Type>();

		queryString.append("FROM " + Emision.class.getName() + " e " + "join fetch e.instrumento " + "join fetch e.instrumento.mercado "
				+ "join fetch e.emisora ");

		queryString.append(construirCriterioEmision(criterio, "e", params, tipos));

		List<EmisionDTO> resultado = new ArrayList<EmisionDTO>();

		if (emisionesValidas.size() > 0) {

			queryString.append(construirCriterioIdsEmision(emisionesValidas, "e", estadoPaginacion, params, tipos));

			resultado = (List<EmisionDTO>)getHibernateTemplate().execute(new HibernateCallback(){
				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					List<EmisionDTO> resultado = new ArrayList<EmisionDTO>();
					
					Query query = session.createQuery(queryString.toString());
					query.setParameters(params.toArray(), tipos.toArray(new Type[] {}));

					// if(estadoPaginacion != null) {
					//				
					// query.setFirstResult((estadoPaginacion.getNumeroPagina()-1)
					// * estadoPaginacion.getRegistrosPorPagina());
					// query.setMaxResults(estadoPaginacion.getRegistrosPorPagina());
					// }

					Iterator emisionesBO = query.list().iterator();
					while (emisionesBO.hasNext()) {

						resultado.add(DTOAssembler.crearEmisionDTO((Emision) emisionesBO.next()));

					}
					return resultado;
				}
			});
		}

		return resultado;
	}

	/**
	 * Construye la cadena que representa al criterio de ids válidos para
	 * emisión
	 * 
	 * @param idsEmision
	 *            Identifacadores de emisión válidos
	 * @param alias
	 *            Alias de la tabla de emisión
	 * @param paginacion
	 *            Estado de la paginación
	 * @param params
	 *            parámetros utilizados para construir el criterio
	 * @param tipos
	 *            Tipos utilizados para construir el criterio
	 * @return
	 */
	private String construirCriterioIdsEmision(List<Long> idsEmision, String alias, EstadoPaginacionDTO estadoPaginacion, ArrayList<Object> params,
			ArrayList<Type> tipos) {
		StringBuffer queryString = new StringBuffer();
		queryString.append(obtenerPrefijoCondicion(params.size()));
		queryString.append(" e.idEmision in (");
		if (estadoPaginacion != null) {

			for (int i = (estadoPaginacion.getNumeroPagina() - 1) * estadoPaginacion.getRegistrosPorPagina(); i < estadoPaginacion.getRegistrosPorPagina()
					* estadoPaginacion.getNumeroPagina()
					&& i < idsEmision.size(); i++) {
				queryString.append(idsEmision.get(i));
				if (i + 1 < estadoPaginacion.getRegistrosPorPagina() * estadoPaginacion.getNumeroPagina() && i + 1 < idsEmision.size()) {
					queryString.append(",");
				}
			}

		} else {
			Iterator<Long> itIds = idsEmision.iterator();
			int total = 0;
			int maximo = 1000;
			Long id = null;
			while (itIds.hasNext()) {
				id = itIds.next();
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
		queryString.append(" ) ");
		return queryString.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.dao.EmisionDAO#obtenerProyeccionDeEmisiones(com.indeval.estadocuenta.core.application.dto.EmisionDTO,
	 *      java.util.Set)
	 */
	public int obtenerProyeccionDeEmisiones(EmisionDTO criterio, List<Long> emisionesValidas) {
		StringBuffer queryString = new StringBuffer();
		ArrayList<Object> params = new ArrayList<Object>();
		ArrayList<Type> tipos = new ArrayList<Type>();
		int res = 0;
		queryString.append("SELECT COUNT(*) FROM " + Emision.class.getName() + " e ");

		queryString.append(construirCriterioEmision(criterio, "e", params, tipos));

		if (emisionesValidas.size() > 0) {

			res = emisionesValidas.size();
		}

		return res;
	}

	private String construirCriterioEmision(EmisionDTO criterio, String alias, ArrayList<Object> params, ArrayList<Type> tipos) {
		StringBuffer queryString = new StringBuffer();

		if (criterio.getIsin() != null && criterio.getIsin().length() > 0) {
			// Consulta por identificador ISIN único
			queryString.append(obtenerPrefijoCondicion(params.size()));
			queryString.append(alias + ".isin = ? ");
			params.add(criterio.getIsin().toUpperCase());
			tipos.add(new StringType());

		} else {

			if (criterio.getSerie() != null && StringUtils.isNotBlank(criterio.getSerie().getSerie()) && !criterio.getSerie().getSerie().equals("-1")) {

				queryString.append(obtenerPrefijoCondicion(params.size()));
				queryString.append(alias + ".serie = ?");
				params.add(criterio.getSerie().getSerie());
				tipos.add(new StringType());
			}

			if (criterio.getEmisora().getId() > 0) {

				queryString.append(obtenerPrefijoCondicion(params.size()));
				queryString.append(alias + ".emisora.idEmisora = ?");
				params.add(new BigInteger(String.valueOf(criterio.getEmisora().getId())));
				tipos.add(new BigIntegerType());
			}

			if (criterio.getTipoValor().getId() > 0) {

				queryString.append(obtenerPrefijoCondicion(params.size()));
				queryString.append(alias + ".instrumento.idInstrumento = ?");
				params.add(new BigInteger(String.valueOf(criterio.getTipoValor().getId())));
				tipos.add(new BigIntegerType());
			}

			if (criterio.getTipoValor().getMercado().getId() > 0) {

				queryString.append(obtenerPrefijoCondicion(params.size()));
				if (criterio.getTipoValor().getMercado().getId() != DaliConstants.ID_MERCADO_DINERO) {
					queryString.append(alias + ".instrumento.mercado.idMercado = ?");
					params.add(new BigInteger(String.valueOf(criterio.getTipoValor().getMercado().getId())));
					tipos.add(new BigIntegerType());
				} else {
					queryString.append(alias + ".instrumento.mercado.idMercado IN (?,?)");

					params.add(new BigInteger(String.valueOf(DaliConstants.ID_MERCADO_PAPEL_BANCARIO)));
					tipos.add(new BigIntegerType());

					params.add(new BigInteger(String.valueOf(DaliConstants.ID_MERCADO_PAPEL_GUBER)));
					tipos.add(new BigIntegerType());
				}
			}

		}

		return queryString.toString();
	}

	/**
	 * Método que construye el query para obtener la lista de Emisiones,
	 * basándose en las descripciones de TV,emisiora y serie
	 * 
	 * @param criterio
	 * @param alias
	 * @param params
	 * @param tipos
	 * @return cadena con la consulta a la base de datos
	 */
	private String construirCriterioEmisionPorDescripciones(EmisionDTO criterio, String alias, ArrayList<Object> params, ArrayList<Type> tipos) {
		StringBuffer queryString = new StringBuffer();

		if (criterio.getIsin() != null && criterio.getIsin().length() > 0) {
			// Consulta por identificador ISIN único
			queryString.append(" WHERE " + alias + ".isin =? ");
			params.add(criterio.getIsin().toUpperCase());
			tipos.add(new StringType());

		} else {
			// serie
			if (criterio.getSerie() != null && StringUtils.isNotBlank(criterio.getSerie().getSerie()) && !criterio.getSerie().getSerie().equals("-1")) {

				queryString.append(obtenerPrefijoCondicion(params.size()));
				queryString.append(alias + ".serie = ?");
				params.add(criterio.getSerie().getSerie().toUpperCase());
				tipos.add(new StringType());
			}

			// descripcion clave pizarra
			if (StringUtils.isNotEmpty(criterio.getEmisora().getDescripcion())) {

				queryString.append(obtenerPrefijoCondicion(params.size()));
				queryString.append(alias + ".emisora.clavePizarra = ?");
				params.add(criterio.getEmisora().getDescripcion().toUpperCase());
				tipos.add(new StringType());
			}
			// tipo valor
			if (StringUtils.isNotEmpty(criterio.getTipoValor().getClaveTipoValor())) {

				queryString.append(obtenerPrefijoCondicion(params.size()));
				queryString.append(alias + ".instrumento.claveTipoValor = ?");
				params.add(criterio.getTipoValor().getClaveTipoValor().toUpperCase());
				tipos.add(new StringType());
			}

			if (criterio.getTipoValor().getMercado().getId() > 0) {

				queryString.append(obtenerPrefijoCondicion(params.size()));
				if (criterio.getTipoValor().getMercado().getId() != DaliConstants.ID_MERCADO_DINERO) {
					queryString.append(alias + ".instrumento.mercado.idMercado = ?");
					params.add(new BigInteger(String.valueOf(criterio.getTipoValor().getMercado().getId())));
					tipos.add(new BigIntegerType());
				} else {
					queryString.append(alias + ".instrumento.mercado.idMercado IN (?,?)");

					params.add(new BigInteger(String.valueOf(DaliConstants.ID_MERCADO_PAPEL_BANCARIO)));
					tipos.add(new BigIntegerType());

					params.add(new BigInteger(String.valueOf(DaliConstants.ID_MERCADO_PAPEL_GUBER)));
					tipos.add(new BigIntegerType());
				}
			}

		}

		return queryString.toString();
	}

	/**
	 * Método que construye el query para obtener el total de Emisiones,
	 * basándose en las descripciones de TV,emisiora y serie
	 * 
	 * @param criterio
	 * @param alias
	 * @param params
	 * @param tipos
	 * @return cadena con la consulta a la base de datos
	 */
	private String construirCriterioConsultaEmisiones(EmisionDTO criterio, String alias, ArrayList<Object> params, ArrayList<Type> tipos) {
		StringBuffer queryString = new StringBuffer();
		
		//isin
		if(criterio.getIsin() != null && criterio.getIsin().length() > 0) {
			// Consulta por identificador ISIN único
			//queryString.append(" WHERE " + alias + ".isin =? ");
			queryString.append(obtenerPrefijoCondicion(params.size()));
			queryString.append(alias + ".emision.isin = ?");
			params.add(criterio.getIsin().toUpperCase());
			tipos.add(new StringType());

		} 
		
		// serie
		if (criterio.getSerie() != null && StringUtils.isNotBlank(criterio.getSerie().getSerie()) && !criterio.getSerie().getSerie().equals("-1")) {

			queryString.append(obtenerPrefijoCondicion(params.size()));
			queryString.append(alias + ".emision.serie = ?");
			params.add(criterio.getSerie().getSerie().toUpperCase());
			tipos.add(new StringType());
		}

		// descripcion clave pizarra
		if (StringUtils.isNotEmpty(criterio.getEmisora().getDescripcion())) {

			queryString.append(obtenerPrefijoCondicion(params.size()));
			queryString.append(alias + ".emision.emisora.clavePizarra = ?");
			params.add(criterio.getEmisora().getDescripcion().toUpperCase());
			tipos.add(new StringType());
		}
		
		// tipo valor
		if (StringUtils.isNotEmpty(criterio.getTipoValor().getClaveTipoValor())) {

			queryString.append(obtenerPrefijoCondicion(params.size()));
			queryString.append(alias + ".emision.instrumento.claveTipoValor = ?");
			params.add(criterio.getTipoValor().getClaveTipoValor().toUpperCase());
			tipos.add(new StringType());
		}

		return queryString.toString();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.dao.EmisionDAO#consultarEmisionPorCupon(com.indeval.portaldali.middleware.services.modelo.EmisionVO)
	 */
	@SuppressWarnings("unchecked")
	public EmisionDTO consultarEmisionPorCupon(EmisionVO criterio) {
		final StringBuffer queryString = new StringBuffer();
		final ArrayList<Object> params = new ArrayList<Object>();
		final ArrayList<Type> tipos = new ArrayList<Type>();

		queryString.append("FROM " + Cupon.class.getName() + " c " + "join fetch c.emision e " + "join fetch c.estadoCupon ce "
				+ "join fetch e.instrumento ei " + "join fetch e.emisora ee ");
		queryString.append("WHERE c.claveCupon = ? AND ");
		params.add(criterio.getCupon());
		tipos.add(new StringType());
		queryString.append("ei.claveTipoValor = ? AND ");
		params.add(criterio.getIdTipoValor() !=  null? criterio.getIdTipoValor().toUpperCase(): criterio.getIdTipoValor());
		tipos.add(new StringType());
		queryString.append("ee.descEmisora = ? AND ");
		params.add(criterio.getEmisora() != null ? criterio.getEmisora().toUpperCase() : criterio.getEmisora());
		tipos.add(new StringType());
		queryString.append("e.serie = ? AND ");
		params.add(criterio.getSerie() != null ? criterio.getSerie().toUpperCase() : criterio.getSerie());
		tipos.add(new StringType());
		queryString.append("ce.idEstatusCupon = ?");
		params.add(Constantes.VIGENTE);
		tipos.add(new BigIntegerType());

		
		return (EmisionDTO)getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				EmisionDTO emisionDTO = null;
				
				Query query = session.createQuery(queryString.toString());
				query.setParameters(params.toArray(), tipos.toArray(new Type[] {}));

				List<Cupon> resultados = query.list();

				if (resultados != null && !resultados.isEmpty() && resultados.get(0) != null) {
					emisionDTO = DTOAssembler.crearEmisionDTO(resultados.get(0).getEmision());
					emisionDTO.setCupon(resultados.get(0).getClaveCupon());
				}

				return emisionDTO;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.persistence.dao.common.EmisionDaliDAO#consultarEmisionesPorDescripciones(com.indeval.portaldali.middleware.dto.EmisionDTO,
	 *      com.indeval.portaldali.middleware.dto.util.EstadoPaginacionDTO)
	 */
	@SuppressWarnings("unchecked")
	public List<EmisionDTO> consultarEmisionesPorDescripciones(EmisionDTO criterio, final EstadoPaginacionDTO estadoPaginacion) {
		final StringBuffer queryString = new StringBuffer();
		final StringBuffer queryStringCount = new StringBuffer();
		final ArrayList<Object> params = new ArrayList<Object>();
		final ArrayList<Object> paramsCount = new ArrayList<Object>();
		final ArrayList<Type> tipos = new ArrayList<Type>();
		final ArrayList<Type> tiposCount = new ArrayList<Type>();

		queryString.append("FROM " + Cupon.class.getName() + " c " + "join fetch c.emision e " + "join fetch c.estadoCupon ce "
				+ "join fetch e.instrumento ei " + "join fetch e.emisora ee ");

		queryString.append(construirCriterioEmisionPorDescripciones(criterio, "e", params, tipos));
		
		queryString.append(obtenerPrefijoCondicion(params.size()));
		queryString.append("ce.idEstatusCupon = ?");
		params.add(Constantes.VIGENTE);
		tipos.add(new BigIntegerType());
		
		//queryStringCount.append(queryString.toString());
		queryStringCount.append("SELECT COUNT(*) ");
		queryStringCount.append("FROM " + Cupon.class.getName() + " c " );
		queryStringCount.append(construirCriterioConsultaEmisiones(criterio, "c", paramsCount, tiposCount));
		queryStringCount.append(obtenerPrefijoCondicion(paramsCount.size()));
		queryStringCount.append("c.estadoCupon.idEstatusCupon = ?");
		paramsCount.add(Constantes.VIGENTE);
		tiposCount.add(new BigIntegerType());
		
		queryString.append("ORDER BY ei.claveTipoValor, ee.descEmisora, e.serie ");
		
		List<EmisionDTO> resultados = (List<EmisionDTO>) getHibernateTemplate().execute(new HibernateCallback(){
			/*
			 * (non-Javadoc)
			 * @see org.springframework.orm.hibernate3.HibernateCallback#doInHibernate(org.hibernate.Session)
			 */
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(queryString.toString());
				query.setParameters(params.toArray(), tipos.toArray(new Type[] {}));

				if (estadoPaginacion != null) {

					query.setFirstResult((estadoPaginacion.getNumeroPagina() - 1) * estadoPaginacion.getRegistrosPorPagina());
					query.setMaxResults(estadoPaginacion.getRegistrosPorPagina());
					
					Query queryCount = session.createQuery(queryStringCount.toString());
					queryCount.setParameters(paramsCount.toArray(), tiposCount.toArray(new Type[] {}));
					
					//int totalRegistros = queryCount.list().size();
					int totalRegistros = Integer.parseInt(queryCount.list().get(0).toString());
					
					estadoPaginacion.setTotalResultados(totalRegistros);
					
					int totalPaginas = totalRegistros / estadoPaginacion.getRegistrosPorPagina();
					
					if(totalRegistros % estadoPaginacion.getRegistrosPorPagina() > 0)
						totalPaginas++;
					
					totalPaginas = (totalPaginas <= 0) ? 1 : totalPaginas;
					
					estadoPaginacion.setTotalPaginas(totalPaginas);
					//estadoPaginacion.setTotalResultados(Integer.parseInt(queryCount.uniqueResult().toString()));
				}

				List<Cupon> resultados = query.list();
				List<EmisionDTO> resultado = new ArrayList<EmisionDTO>();
				for(int inx = 0; inx < resultados.size(); inx++){
					if (resultados != null && !resultados.isEmpty() && resultados.get(inx) != null) {
						EmisionDTO emisionDTO = new EmisionDTO();
						emisionDTO = DTOAssembler.crearEmisionDTO(resultados.get(inx).getEmision());
						emisionDTO.setCupon(resultados.get(inx).getClaveCupon());
						resultado.add(emisionDTO);
					}
				}
				return resultado;
			}
		});
		
		return resultados;
	}
}
