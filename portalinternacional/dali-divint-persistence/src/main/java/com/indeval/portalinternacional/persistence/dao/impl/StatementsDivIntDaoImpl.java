/*
 * Copyright (c) 2010 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.persistence.dao.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.type.BigDecimalType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.hibernate.type.TimestampType;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.middleware.servicios.util.UtilsVO;
import com.indeval.portaldali.persistence.util.DateUtils;
import com.indeval.portalinternacional.middleware.servicios.modelo.StatementDivint;
import com.indeval.portalinternacional.middleware.servicios.vo.ArchivoStatementsVO;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaStatementsParam;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaStatementsTotalesVO;
import com.indeval.portalinternacional.middleware.servicios.vo.StatementDivintVO;
import com.indeval.portalinternacional.persistence.dao.StatementsDivIntDao;

/**
 * Implementación del Dao para el manejo del modelo de Statements.
 *
 * @author Rafael Ibarra Zendejas
 * @version 1.0
 */
public class StatementsDivIntDaoImpl extends BaseDaoHibernateImpl
		implements StatementsDivIntDao {

	/** Log de clase. */
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	public StatementDivint findStatementById(Long id) {
		log.debug("Entrnado a StatementsDivIntDaoImpl.findStatementById con id: [" + id + "]");
		StatementDivint retorno = null;
		StringBuilder sb = new StringBuilder();
		List<Object> paramsSql = new ArrayList<Object>();

		sb.append(" FROM " + StatementDivint.class.getName() + " st ");
		sb.append("		JOIN FETCH st.emision e ");
		sb.append("		JOIN FETCH e.emisora ems ");
		sb.append("		JOIN FETCH e.instrumento ins ");
		sb.append("		JOIN FETCH st.institucion i ");
		sb.append("		JOIN FETCH i.tipoInstitucion ti ");
		sb.append("		JOIN FETCH st.tipoBeneficiario tb ");
		sb.append("	WHERE st.idStatement = ? ");
		paramsSql.add(id);

		List<StatementDivint> lista = this.getHibernateTemplate().find(sb.toString(), paramsSql.toArray(new Object[0]));
		if (lista != null && lista.size() == 1) {
			retorno = lista.get(0);
		}

		return retorno;
	}

	public PaginaVO findStatements(ConsultaStatementsParam param, PaginaVO paginaVO) {
		log.debug("Entrnado a StatementsDivIntDaoImpl.findStatements con param: [" + param + "]");
		if (param == null) {
			throw new IllegalArgumentException("El objeto de parametros para la consulta de Statements es nulo");
		}
		paginaVO = UtilsVO.getPaginaNotBlank(paginaVO);
		// Si solo debes obtener una parte de la consulta
		if (paginaVO.getRegistrosXPag().intValue() != PaginaVO.TODOS.intValue()) {
			paginaVO.setTotalRegistros(countFindStatements(param, paginaVO));
			if (paginaVO.getTotalRegistros() != null && paginaVO.getTotalRegistros().intValue() > 0) {
				paginaVO.setRegistros(listFindStatements(param, paginaVO));
			} else {
				paginaVO.setRegistros(new ArrayList<StatementDivintVO>());
			}
		} else {
			// si debes de obtener la totalidad de la consulta
			List<StatementDivintVO> registros = listFindStatements(param, paginaVO);
			paginaVO.setTotalRegistros(registros.size());
			paginaVO.setRegistros(registros);
		}

		return paginaVO;
	}

	public ConsultaStatementsTotalesVO findTotalesStatements(ConsultaStatementsParam param) {
		log.debug("Entrnado a StatementsDivIntDaoImpl.findStatements con param: [" + param + "]");
		ConsultaStatementsTotalesVO retorno = null;
		if (param == null) {
			throw new IllegalArgumentException("El objeto de parametros para la consulta de Statements es nulo");
		}
		final StringBuilder sb = new StringBuilder();
		final List<Object> paramsSql = new ArrayList<Object>();
		final List<Type> tiposSql = new ArrayList<Type>();
		sb.append(" SELECT new " + ConsultaStatementsTotalesVO.class.getName() + " ( SUM(st.numeroTitulos), ");
		sb.append("		SUM(st.dividendo), SUM(st.impuesto), SUM(st.dividendoNeto) ) ");
		sb.append(" FROM " + StatementDivint.class.getName() + " st ");
		sb.append("		JOIN st.emision e ");
		sb.append("		JOIN e.emisora ems ");
		sb.append("		JOIN e.instrumento ins ");
		sb.append("		JOIN st.institucion i ");
		sb.append("		JOIN i.tipoInstitucion ti ");
		sb.append("		JOIN st.tipoBeneficiario tb ");
		sb.append(construyeParams(paramsSql, tiposSql, param));
		List<ConsultaStatementsTotalesVO> resultado = this.getHibernateTemplate().find(sb.toString(), paramsSql.toArray(new Object[0]));
		if (resultado != null && resultado.size() == 1) {
			retorno = resultado.get(0);
		}
		return retorno;
	}

	public PaginaVO findArchivoStatements(String nombreArchivo, PaginaVO paginaVO) {
		log.debug("Entrando a StatementsDivIntDaoImpl.findArchivoStatements con nombreArchivo: [" + nombreArchivo + "]");
		paginaVO = UtilsVO.getPaginaNotBlank(paginaVO);
		// Si solo debes obtener una parte de la consulta
		if (paginaVO.getRegistrosXPag().intValue() != PaginaVO.TODOS.intValue()) {
			paginaVO.setTotalRegistros( countArchivoStatements(nombreArchivo) );
			if (paginaVO.getTotalRegistros() != null && paginaVO.getTotalRegistros().intValue() > 0) {
				paginaVO.setRegistros(listArchivoStatements(nombreArchivo, paginaVO));
			} else {
				paginaVO.setRegistros(new ArrayList<ArchivoStatementsVO>());
			}
		} else {
			// si debes de obtener la totalidad de la consulta
			List<ArchivoStatementsVO> registros = listArchivoStatements(nombreArchivo, paginaVO);
			paginaVO.setTotalRegistros(registros.size());
			paginaVO.setRegistros(registros);
		}
		
		return paginaVO;
	}

	public int deleteArchivo(String nombreArchivo) {
		log.debug("Entrando a StatementsDivIntDaoImpl.deleteArchivo con nombreArchivo: [" + nombreArchivo + "]");
		if (StringUtils.isBlank(nombreArchivo)) {
			throw new IllegalArgumentException("El nombre del archivo no puede ser null o vacio");
		}
		final StringBuilder sb = new StringBuilder();
		final List<Object> paramsSql = new ArrayList<Object>();
		final List<Type> tiposSql = new ArrayList<Type>();
		sb.append(" DELETE FROM " + StatementDivint.class.getName() + " st ");
		sb.append(" WHERE st.archivoOrigen = ? ");
		paramsSql.add( transformaCadena(nombreArchivo) );
		tiposSql.add(new StringType());
		log.debug("Delete a ejecutar: [" + sb.toString() + "],[" + nombreArchivo + "]");

		Integer filasBorradas = (Integer)getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sb.toString());
				query.setParameters(paramsSql.toArray(new Object[0]), tiposSql.toArray(new Type[0]));
				return query.executeUpdate();
			}
		});
		if(filasBorradas ==null) {
			filasBorradas = 0;
		}
		return filasBorradas.intValue();
	}

	private int countFindStatements(ConsultaStatementsParam param, PaginaVO paginaVO) {
		int retorno = 0;
		StringBuilder sb = new StringBuilder();
		List<Object> paramsSql = new ArrayList<Object>();
		List<Type> tiposSql = new ArrayList<Type>();
		// Si solo debes obtener una parte de la consulta
		sb.append(" SELECT COUNT(*) ");
		sb.append(" FROM " + StatementDivint.class.getName() + " st ");
		sb.append("		JOIN st.emision e ");
		sb.append("		JOIN e.emisora ems ");
		sb.append("		JOIN e.instrumento ins ");
		sb.append("		JOIN st.institucion i ");
		sb.append("		JOIN i.tipoInstitucion ti ");
		sb.append("		JOIN st.tipoBeneficiario tb ");
		sb.append(construyeParams(paramsSql, tiposSql, param));
		List<Long> resultado = this.getHibernateTemplate().find(sb.toString(), paramsSql.toArray(new Object[0]));
		if (resultado != null && resultado.size() == 1) {
			retorno = resultado.get(0).intValue();
		}
		return retorno;
	}

	private List<StatementDivintVO> listFindStatements(ConsultaStatementsParam param, final PaginaVO paginaVO) {
		final StringBuilder sb = new StringBuilder();
		final List<Object> paramsSql = new ArrayList<Object>();
		final List<Type> tiposSql = new ArrayList<Type>();
		// Si solo debes obtener una parte de la consulta
		sb.append(" SELECT new " + StatementDivintVO.class.getName() + " (st.idStatement, ins.claveTipoValor, ems.clavePizarra, ");
		sb.append("		e.serie, e.isin, st.fechaPago, st.fechaRegistro, st.fechaCreacion, ti.claveTipoInstitucion || i.folioInstitucion, i.nombreCorto, ");
		sb.append("		st.adp, st.nombre, st.rfc, st.direccion, st.taxPayerNumber, st.formato, st.statusOwner, tb.descTipoBeneficiario, ");
		sb.append("		st.pais, st.porcentajeRetencion, st.numeroTitulos, st.proporcion, st.dividendo, st.impuesto, st.dividendoNeto, ");
		sb.append("		st.archivoOrigen, i.razonSocial, st.cargoInstitucion, i.idInstitucion, i.rfc, st.custodio ) ");
		sb.append(" FROM " + StatementDivint.class.getName() + " st ");
		sb.append("		JOIN st.emision e ");
		sb.append("		JOIN e.emisora ems ");
		sb.append("		JOIN e.instrumento ins ");
		sb.append("		JOIN st.institucion i ");
		sb.append("		JOIN i.tipoInstitucion ti ");
		sb.append("		JOIN st.tipoBeneficiario tb ");
		sb.append(construyeParams(paramsSql, tiposSql, param));
		sb.append("	ORDER BY st.nombre ");

		List<StatementDivintVO> retorno = (List<StatementDivintVO>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sb.toString());
				query.setParameters(paramsSql.toArray(new Object[0]), tiposSql.toArray(new Type[0]));
				if (paginaVO.getRegistrosXPag().intValue() != PaginaVO.TODOS.intValue()) {
					query.setFirstResult(paginaVO.getOffset());
					query.setMaxResults(paginaVO.getRegistrosXPag());
				}
				return query.list();
			}
		});

		if (retorno == null) {
			retorno = new ArrayList<StatementDivintVO>();
		}
		return retorno;
	}

	private String construyeParams(List<Object> paramsSql, List<Type> tiposSql, ConsultaStatementsParam param) {
		StringBuilder sb = new StringBuilder();

		sb.append(" WHERE 1=1 ");
		// Filtro para el nombre
		if (StringUtils.isNotBlank(param.getNombre())) {
			sb.append("		AND st.nombre like ? ");
			paramsSql.add("%" + transformaCadena(param.getNombre()) + "%");
			tiposSql.add(new StringType());
		}
		//Filtro para el tipo de formato
		if (StringUtils.isNotBlank(param.getFormato()) &&
				!param.getFormato().equals("-1")) {
			sb.append("	AND st.formato = ? ");
			paramsSql.add(transformaCadena(param.getFormato()));
			tiposSql.add(new StringType());
		}
		//Filtro para el tipo de valor de la emision
		if (StringUtils.isNotBlank(param.getTv())) {
			sb.append("	AND ins.claveTipoValor = ? ");
			paramsSql.add(transformaCadena(param.getTv()));
			tiposSql.add(new StringType());
		}
		//Filtro para la clave pizarra de la emision
		if (StringUtils.isNotBlank(param.getEmisora())) {
			sb.append("	AND ems.clavePizarra = ? ");
			paramsSql.add(transformaCadena(param.getEmisora()));
			tiposSql.add(new StringType());
		}
		//Filtro para la serie de la emision
		if (StringUtils.isNotBlank(param.getSerie())) {
			sb.append("	AND e.serie = ? ");
			paramsSql.add(transformaCadena(param.getSerie()));
			tiposSql.add(new StringType());
		}
		//Filtro para el isin de la emision
		if (StringUtils.isNotBlank(param.getIsin())) {
			sb.append("	AND e.isin = ? ");
			paramsSql.add(transformaCadena(param.getIsin()));
			tiposSql.add(new StringType());
		}
		//Filtro para el Id de la institucion
		if (validaLong(param.getIdInstitucion())) {
			sb.append("	AND i.idInstitucion = ? ");
			paramsSql.add(param.getIdInstitucion());
			tiposSql.add(new LongType());
		}
		// Filtro para la fecha de inicio de pago
		if (param.getFechaPagoInicio() != null) {
			sb.append("	AND st.fechaPago >= ? ");
			paramsSql.add(DateUtils.preparaFechaConExtremoEnSegundos(param.getFechaPagoInicio(), true));
			tiposSql.add(new TimestampType());
		}
		// Filtro para la fecha de fin de pago
		if (param.getFechaPagoFin() != null) {
			sb.append("	AND st.fechaPago <= ? ");
			paramsSql.add(DateUtils.preparaFechaConExtremoEnSegundos(param.getFechaPagoFin(), false));
			tiposSql.add(new TimestampType());
		}
		// Filtro para la fecha de inicio de registro (record)
		if (param.getFechaRegistroInicio() != null) {
			sb.append("	AND st.fechaRegistro >= ? ");
			paramsSql.add(DateUtils.preparaFechaConExtremoEnSegundos(param.getFechaRegistroInicio(), true));
			tiposSql.add(new TimestampType());
		}
		// Filtro para la fecha de fin de registro (record)
		if (param.getFechaRegistroFin() != null) {
			sb.append("	AND st.fechaRegistro <= ? ");
			paramsSql.add(DateUtils.preparaFechaConExtremoEnSegundos(param.getFechaRegistroFin(), false));
			tiposSql.add(new TimestampType());
		}
		//Filtro para el ADP del bebenficiario
		if (StringUtils.isNotBlank(param.getAdp())) {
			sb.append("	AND st.adp = ? ");
			paramsSql.add(transformaCadena(param.getAdp()));
			tiposSql.add(new StringType());
		}
		//Filtro para el ArchivoOrigen del registro
		if (StringUtils.isNotBlank(param.getArchivo())) {
			sb.append("	AND st.archivoOrigen = ? ");
			paramsSql.add(transformaCadena(param.getArchivo()));
			tiposSql.add(new StringType());
		}
		//Filtro para el Id del tipo de beneficiario
		if (validaLong(param.getIdTipoBeneficiario())) {
			sb.append("	AND tb.idTipoBeneficiario = ? ");
			paramsSql.add(param.getIdTipoBeneficiario());
			tiposSql.add(new LongType());
		}
		
		//Modificacion 21/11/2012 se agrega filtro a la consulta
		if (param.getPorcentaje() != null){
			sb.append("	AND st.porcentajeRetencion = ? ");
			paramsSql.add(new BigDecimal(param.getPorcentaje()));
			tiposSql.add(new BigDecimalType());
		}
		
		//Modificacion 22/11/2012 se agrega filtro a la consulta
		if (!param.getCustodio().equals("-1") 
			&& StringUtils.isNotBlank(param.getCustodio())
				){
			sb.append("	AND st.custodio = ? ");
			paramsSql.add(param.getCustodio());
			tiposSql.add(new StringType());
		}

		return sb.toString();
	}
	

	private String transformaCadena(String cadena) {
		return StringUtils.trim(StringUtils.upperCase(cadena));
	}

	private boolean validaLong(Long numero) {
		boolean retorno = false;
		if (numero != null && numero.compareTo(Long.valueOf(0)) > 0) {
			retorno = true;
		}
		return retorno;
	}

	private List<ArchivoStatementsVO> listArchivoStatements(String nombreArchivo, final PaginaVO paginaVO) {
		final StringBuilder sb = new StringBuilder();
		final List<Object> paramsSql = new ArrayList<Object>();
		final List<Type> tiposSql = new ArrayList<Type>();
		// Si solo debes obtener una parte de la consulta
		sb.append(" SELECT new " + ArchivoStatementsVO.class.getName() + " ( ");
		sb.append("		st.archivoOrigen, COUNT(st.archivoOrigen) ) ");
		sb.append(" FROM " + StatementDivint.class.getName() + " st ");
		sb.append(construyeParamsArchivoStatements(paramsSql, tiposSql, nombreArchivo));
		sb.append("	GROUP BY st.archivoOrigen ");
		sb.append("	ORDER BY st.archivoOrigen ");

		List<ArchivoStatementsVO> retorno = (List<ArchivoStatementsVO>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sb.toString());
				query.setParameters(paramsSql.toArray(new Object[0]), tiposSql.toArray(new Type[0]));
				if (paginaVO.getRegistrosXPag().intValue() != PaginaVO.TODOS.intValue()) {
					query.setFirstResult(paginaVO.getOffset());
					query.setMaxResults(paginaVO.getRegistrosXPag());
				}
				return query.list();
			}
		});

		if (retorno == null) {
			retorno = new ArrayList<ArchivoStatementsVO>();
		}
		return retorno;
	}

	private int countArchivoStatements(String nombreArchivo) {
		int retorno = 0;
		StringBuilder sb = new StringBuilder();
		List<Object> paramsSql = new ArrayList<Object>();
		List<Type> tiposSql = new ArrayList<Type>();
		// Si solo debes obtener una parte de la consulta
		sb.append(" SELECT COUNT( DISTINCT st.archivoOrigen ) ");
		sb.append(" FROM " + StatementDivint.class.getName() + " st ");
		sb.append(construyeParamsArchivoStatements(paramsSql, tiposSql, nombreArchivo));
		List<Long> resultado = this.getHibernateTemplate().find(sb.toString(), paramsSql.toArray(new Object[0]));
		if (resultado != null && resultado.size() == 1) {
			retorno = resultado.get(0).intValue();
		}
		return retorno;
	}

	private String construyeParamsArchivoStatements(List<Object> paramsSql, List<Type> tiposSql,
			String nombreArchivo) {
		StringBuilder sb = new StringBuilder();
		sb.append(" WHERE 1=1 ");
		// Filtro para el nombre del archivo
		if (StringUtils.isNotBlank( nombreArchivo )) {
			sb.append("		AND st.archivoOrigen like ? ");
			paramsSql.add("%" + transformaCadena(nombreArchivo) + "%");
			tiposSql.add(new StringType());
		}
		return sb.toString();
	}

	public List<String> listaCustodios() {
			
		final StringBuilder sb = new StringBuilder();

		sb.append(" SELECT  " );
		sb.append("		 distinct(st.custodio)  ");
		sb.append(" FROM " + StatementDivint.class.getName() + " st ");
		sb.append("	WHERE 1=1 ");
		sb.append("	ORDER BY st.custodio ");

		List<String> custodios = (List<String>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sb.toString());
				return query.list();
			}
		});

		if (custodios == null) {
			custodios = new ArrayList<String>();
		}
		return custodios;
	}
	
	
}
