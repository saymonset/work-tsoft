/**
 * 2H Software - Bursatec
 * 
 * Sistema de Consulta de Estados de Cuenta
 *
 * Dec 5, 2007
 *
 */
package com.indeval.portaldali.persistence.dao.estadocuenta.impl;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.type.BigIntegerType;
import org.hibernate.type.DateType;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.indeval.portaldali.middleware.dto.CriterioOrdenamientoDTO;
import com.indeval.portaldali.middleware.dto.SaldoEfectivoDTO;
import com.indeval.portaldali.middleware.dto.TipoCuentaDTO;
import com.indeval.portaldali.middleware.dto.util.DTOAssembler;
import com.indeval.portaldali.middleware.dto.util.EstadoPaginacionDTO;
import com.indeval.portaldali.middleware.services.common.constants.ColumnasOrdenadasConstants;
import com.indeval.portaldali.persistence.dao.estadocuenta.SaldoDAO;
import com.indeval.portaldali.persistence.model.SaldoControlada;
import com.indeval.portaldali.persistence.model.SaldoControladaHistorico;
import com.indeval.portaldali.persistence.model.SaldoNombrada;
import com.indeval.portaldali.persistence.model.SaldoNombradaHistorico;

/**
 * Implementacion del servicio de negocio para las consultas a la tabla de 
 * saldos en la base de datos.
 * 
 * @author 2H Software
 * 
 */
public class SaldoDAOV2Impl extends HibernateDaoSupport implements SaldoDAO {
	
	private final Logger log = LoggerFactory.getLogger(SaldoDAOV2Impl.class);

	/*
	 * (non-Javadoc)
	 * @see com.indeval.estadocuenta.core.application.dao.SaldoDAO#consultarSaldosNombradas(com.indeval.estadocuenta.core.application.dto.SaldoEfectivoDTO)
	 */
	@SuppressWarnings("unchecked")
	public List<SaldoEfectivoDTO> consultarSaldosNombradas(SaldoEfectivoDTO saldo, final EstadoPaginacionDTO paginacion,CriterioOrdenamientoDTO orden) {
		log.debug("SaldoDAOV2Impl :: consultarSaldosNombradas");
		
		final ArrayList<Object> parametros = new ArrayList<Object>();
		final StringBuffer query = new StringBuffer();
		final ArrayList<Object> tipos = new ArrayList<Object>();
		
		query.append("FROM " + SaldoNombrada.class.getName() + " saldoNombrada ");
		query.append(" join fetch saldoNombrada.cuentaNombrada ");
		query.append(" join fetch saldoNombrada.cuentaNombrada.institucion ");
		query.append(" join fetch saldoNombrada.cuentaNombrada.institucion.tipoInstitucion ");
		query.append(" join fetch saldoNombrada.cuentaNombrada.tipoCuenta ");
		query.append(" join fetch saldoNombrada.boveda ");
		query.append(" join fetch saldoNombrada.divisa ");
		
		query.append(" WHERE 1=1 ");
		
		//Agregamos los parametros de la busqueda
		query.append(construirCriterioSaldoNombrado(saldo, "saldoNombrada", parametros, tipos));
		
		if(orden != null && orden.getColumna()!=null){
			
			if(orden.getColumna().equals("sortCuenta")){
				query.append(" order by (saldoNombrada.cuentaNombrada.institucion.tipoInstitucion.claveTipoInstitucion " +
					"|| saldoNombrada.cuentaNombrada.institucion.folioInstitucion || " +
					"saldoNombrada.cuentaNombrada.cuenta) ");
				
			}
			
			if(ColumnasOrdenadasConstants.POR_SALDO.equals(orden.getColumna())){
				query.append(" order by ("
						+ " saldoNombrada.saldoDisponible + saldoNombrada.saldoNoDisponible  ) ");
				
			}
			if(ColumnasOrdenadasConstants.POR_DIVISA.equals(orden.getColumna())){
				query.append(" order by ("
						+ " saldoNombrada.divisa.idDivisa  ) ");
				
			}
			if(ColumnasOrdenadasConstants.POR_BOVEDA.equals(orden.getColumna())){
				query.append(" order by ("
						+ " saldoNombrada.boveda.idBoveda  ) ");
				
			}
			
			if(orden.isAscendente()){
				query.append(" asc ");
				
			}else{
				query.append(" desc ");
			}
			query.append(", saldoNombrada.idSaldo asc");
			
		}
		

		return (List<SaldoEfectivoDTO>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				
				
				List<SaldoEfectivoDTO> resultadosBusqueda = new ArrayList<SaldoEfectivoDTO>();
				
				Query hQuery = session.createQuery(query.toString());
				hQuery.setParameters(parametros.toArray(), tipos.toArray(tipos.toArray(new Type[]{})));
				if(paginacion != null) {
					
					hQuery.setFirstResult((paginacion.getNumeroPagina() - 1)
							* paginacion.getRegistrosPorPagina());
					hQuery.setMaxResults(paginacion.getRegistrosPorPagina());
				}
				Iterator saldoEfectivoBO = hQuery.list().iterator();	
				while(saldoEfectivoBO.hasNext()) {
					SaldoEfectivoDTO saldoEfectivoDTO = new SaldoEfectivoDTO();
					SaldoNombrada estadoSaldoNombrada = (SaldoNombrada) saldoEfectivoBO.next();
					saldoEfectivoDTO = DTOAssembler.crearSaldoEfectivoDTO(estadoSaldoNombrada);
					resultadosBusqueda.add(saldoEfectivoDTO);
				}		
				return resultadosBusqueda;
			}
		});
		
	}	
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.estadocuenta.core.application.dao.SaldoDAO#consultarSaldosNombradasHistorico(com.indeval.estadocuenta.core.application.dto.SaldoEfectivoDTO)
	 */
	@SuppressWarnings("unchecked")
	public List<SaldoEfectivoDTO> consultarSaldosNombradasHistorico(SaldoEfectivoDTO saldo, final EstadoPaginacionDTO paginacion,CriterioOrdenamientoDTO orden) {
		
		final ArrayList<Object> parametros = new ArrayList<Object>();
		final StringBuffer query = new StringBuffer();
		final ArrayList<Object> tipos = new ArrayList<Object>();
		
		query.append("FROM " + SaldoNombradaHistorico.class.getName() + " saldoNombrada ");
		query.append(" join fetch saldoNombrada.cuentaNombrada ");
		query.append(" join fetch saldoNombrada.cuentaNombrada.institucion ");
		query.append(" join fetch saldoNombrada.cuentaNombrada.institucion.tipoInstitucion ");
		query.append(" join fetch saldoNombrada.cuentaNombrada.tipoCuenta ");
		query.append(" join fetch saldoNombrada.boveda ");
		query.append(" join fetch saldoNombrada.divisa ");
		
		query.append(" WHERE saldoNombrada.fechaCreacion = trunc(?) ");
		parametros.add(saldo.getFecha());
		tipos.add(new DateType());
		
		//Agregamos los parametros de la busqueda
		query.append(construirCriterioSaldoNombrado(saldo, "saldoNombrada", parametros, tipos));
		
		if(orden != null && orden.getColumna()!=null){
			
			if(orden.getColumna().equals("sortCuenta")){
				query.append(" order by (saldoNombrada.cuentaNombrada.institucion.tipoInstitucion.claveTipoInstitucion " +
					"|| saldoNombrada.cuentaNombrada.institucion.folioInstitucion || " +
					"saldoNombrada.cuentaNombrada.cuenta) ");
				
			}
			
			if(ColumnasOrdenadasConstants.POR_SALDO.equals(orden.getColumna())){
				query.append(" order by ("
						+ " saldoNombrada.saldoDisponible + saldoNombrada.saldoNoDisponible  ) ");
				
			}
			if(ColumnasOrdenadasConstants.POR_DIVISA.equals(orden.getColumna())){
				query.append(" order by ("
						+ " saldoNombrada.divisa.idDivisa  ) ");
				
			}
			if(ColumnasOrdenadasConstants.POR_BOVEDA.equals(orden.getColumna())){
				query.append(" order by ("
						+ " saldoNombrada.boveda.idBoveda  ) ");
				
			}
			
			if(orden.isAscendente()){
				query.append(" asc ");
				
			}else{
				query.append(" desc ");
			}
			query.append(", saldoNombrada.idSaldoHistorico asc");
			
		}
		

		return (List<SaldoEfectivoDTO>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				
				
				List<SaldoEfectivoDTO> resultadosBusqueda = new ArrayList<SaldoEfectivoDTO>();
				
				Query hQuery = session.createQuery(query.toString());
				hQuery.setParameters(parametros.toArray(), tipos.toArray(tipos.toArray(new Type[]{})));
				if(paginacion != null) {
					
					hQuery.setFirstResult((paginacion.getNumeroPagina() - 1)
							* paginacion.getRegistrosPorPagina());
					hQuery.setMaxResults(paginacion.getRegistrosPorPagina());
				}
				Iterator saldoEfectivoBO = hQuery.list().iterator();	
				while(saldoEfectivoBO.hasNext()) {
					SaldoEfectivoDTO saldoEfectivoDTO = new SaldoEfectivoDTO();
					SaldoNombradaHistorico estadoSaldoNombrada = (SaldoNombradaHistorico) saldoEfectivoBO.next();
					saldoEfectivoDTO = DTOAssembler.crearSaldoEfectivoDTO(estadoSaldoNombrada);
					resultadosBusqueda.add(saldoEfectivoDTO);
				}		
				return resultadosBusqueda;
			}
		});
		
	}	
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.estadocuenta.core.application.dao.SaldoDAO#consultarSaldosControladas(com.indeval.estadocuenta.core.application.dto.SaldoEfectivoDTO)
	 */
	@SuppressWarnings("unchecked")
	public List<SaldoEfectivoDTO> consultarSaldosControladas(SaldoEfectivoDTO saldo, final EstadoPaginacionDTO paginacion,CriterioOrdenamientoDTO orden){
		
		log.debug("SaldoDAOV2Impl :: consultarSaldosControladas");
		final ArrayList<Object> parametros = new ArrayList<Object>();
		final StringBuffer query = new StringBuffer();
		final ArrayList<Object> tipos = new ArrayList<Object>();
		
		query.append("FROM " + SaldoControlada.class.getName() + " saldoControlada ");
		query.append(" join fetch saldoControlada.cuentaControlada ");
		query.append(" join fetch saldoControlada.cuentaControlada.institucion ");
		query.append(" join fetch saldoControlada.cuentaControlada.institucion.tipoInstitucion ");
		query.append(" join fetch saldoControlada.cuentaControlada.tipoCuenta ");
		query.append(" join fetch saldoControlada.boveda ");
		query.append(" join fetch saldoControlada.divisa ");
		
		query.append(" WHERE 1=1 ");
		
		//Agregamos los parametros de la busqueda
		
		query.append(construirCriterioSaldoControlado(saldo, "saldoControlada", parametros, tipos));
		
		if(orden != null && orden.getColumna()!=null){
			
			if(orden.getColumna().equals("sortCuenta")){
				query.append(" order by (saldoControlada.cuentaControlada.institucion.tipoInstitucion.claveTipoInstitucion " +
					"|| saldoControlada.cuentaControlada.institucion.folioInstitucion || " +
					"saldoControlada.cuentaControlada.cuenta)");
				
			}
			
			if(orden.getColumna().equals("sortSaldo")){
				query.append(" order by ("
						+ " saldoControlada.saldo  ) ");
				
			}
			if(orden.getColumna().equals("sortDivisa")){
				query.append(" order by ("
						+ " saldoControlada.divisa.idDivisa  ) ");
				
			}
			if(orden.getColumna().equals("sortBoveda")){
				query.append(" order by ("
						+ " saldoControlada.boveda.idBoveda  ) ");
				
			}
			if(orden.isAscendente()){
				query.append(" asc ");
				
			}else{
				query.append(" desc ");
			}
			query.append(", saldoControlada.idSaldo asc ");
			
		}
		
		return (List<SaldoEfectivoDTO>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				
				List<SaldoEfectivoDTO> resultadosBusqueda = new ArrayList<SaldoEfectivoDTO>();
				
				Query hQuery = session.createQuery(query.toString());
				hQuery.setParameters(parametros.toArray(), tipos.toArray(tipos.toArray(new Type[]{})));
				
				if(paginacion != null) {
					
					hQuery.setFirstResult((paginacion.getNumeroPagina() - 1)
							* paginacion.getRegistrosPorPagina());
					hQuery.setMaxResults(paginacion.getRegistrosPorPagina());
				}
				
				Iterator saldoEfectivoBO = hQuery.list().iterator();	
				while(saldoEfectivoBO.hasNext()) {
					SaldoEfectivoDTO saldoEfectivoDTO = new SaldoEfectivoDTO();
					SaldoControlada estadoSaldoControlada = (SaldoControlada) saldoEfectivoBO.next();
					saldoEfectivoDTO = DTOAssembler.crearSaldoEfectivoDTO(estadoSaldoControlada);
					resultadosBusqueda.add(saldoEfectivoDTO);
				}		
				return resultadosBusqueda;
			}
		});
		
	}	
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.estadocuenta.core.application.dao.SaldoDAO#consultarSaldosControladasHistorico(com.indeval.estadocuenta.core.application.dto.SaldoEfectivoDTO)
	 */
	@SuppressWarnings("unchecked")
	public List<SaldoEfectivoDTO> consultarSaldosControladasHistorico(SaldoEfectivoDTO saldo, final EstadoPaginacionDTO paginacion,CriterioOrdenamientoDTO orden){
		
		final ArrayList<Object> parametros = new ArrayList<Object>();
		final StringBuffer query = new StringBuffer();
		final ArrayList<Object> tipos = new ArrayList<Object>();
		
		query.append("FROM " + SaldoControladaHistorico.class.getName() + " saldoControlada ");
		query.append(" join fetch saldoControlada.cuentaControlada ");
		query.append(" join fetch saldoControlada.cuentaControlada.institucion ");
		query.append(" join fetch saldoControlada.cuentaControlada.institucion.tipoInstitucion ");
		query.append(" join fetch saldoControlada.cuentaControlada.tipoCuenta ");
		query.append(" join fetch saldoControlada.boveda ");
		query.append(" join fetch saldoControlada.divisa ");
		
		query.append(" WHERE saldoControlada.fechaCreacion = trunc(?) ");
		parametros.add(saldo.getFecha());
		tipos.add(new DateType());
		
		//Agregamos los parametros de la busqueda
		
		query.append(construirCriterioSaldoControlado(saldo, "saldoControlada", parametros, tipos));
		
		if(orden != null && orden.getColumna()!=null){
			
			if(orden.getColumna().equals("sortCuenta")){
				query.append(" order by (saldoControlada.cuentaControlada.institucion.tipoInstitucion.claveTipoInstitucion " +
					"|| saldoControlada.cuentaControlada.institucion.folioInstitucion || " +
					"saldoControlada.cuentaControlada.cuenta)");
				
			}
			
			if(orden.getColumna().equals("sortSaldo")){
				query.append(" order by ("
						+ " saldoControlada.saldo  ) ");
				
			}
			if(orden.getColumna().equals("sortDivisa")){
				query.append(" order by ("
						+ " saldoControlada.divisa.idDivisa  ) ");
				
			}
			if(orden.getColumna().equals("sortBoveda")){
				query.append(" order by ("
						+ " saldoControlada.boveda.idBoveda  ) ");
				
			}
			if(orden.isAscendente()){
				query.append(" asc ");
				
			}else{
				query.append(" desc ");
			}
			query.append(", saldoControlada.idSaldoHistorico asc ");
			
		}
		
		return (List<SaldoEfectivoDTO>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				
				List<SaldoEfectivoDTO> resultadosBusqueda = new ArrayList<SaldoEfectivoDTO>();
				
				Query hQuery = session.createQuery(query.toString());
				hQuery.setParameters(parametros.toArray(), tipos.toArray(tipos.toArray(new Type[]{})));
				
				if(paginacion != null) {
					
					hQuery.setFirstResult((paginacion.getNumeroPagina() - 1)
							* paginacion.getRegistrosPorPagina());
					hQuery.setMaxResults(paginacion.getRegistrosPorPagina());
				}
				
				Iterator saldoEfectivoBO = hQuery.list().iterator();	
				while(saldoEfectivoBO.hasNext()) {
					SaldoEfectivoDTO saldoEfectivoDTO = new SaldoEfectivoDTO();
					SaldoControladaHistorico estadoSaldoControlada = (SaldoControladaHistorico) saldoEfectivoBO.next();
					saldoEfectivoDTO = DTOAssembler.crearSaldoEfectivoDTO(estadoSaldoControlada);
					resultadosBusqueda.add(saldoEfectivoDTO);
				}		
				return resultadosBusqueda;
			}
		});
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.indeval.estadocuenta.core.application.dao.SaldoDAO#obtenerProyeccionConsultaSaldosControladas(com.indeval.estadocuenta.core.application.dto.SaldoEfectivoDTO)
	 */
	public int obtenerProyeccionConsultaSaldosControladas(
			SaldoEfectivoDTO saldo) {
		
		final ArrayList<Object> parametros = new ArrayList<Object>();
		final StringBuffer query = new StringBuffer();
		final ArrayList<Object> tipos = new ArrayList<Object>();
		
		query.append("SELECT COUNT(*) FROM " + SaldoControlada.class.getName() + " saldoControlada ");
		
		query.append(" WHERE 1=1 ");
				
		//Agregamos los parametros de la busqueda
		query.append(construirCriterioSaldoControlado(saldo, "saldoControlada", parametros, tipos));
		
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				
				Query hQuery = session.createQuery(query.toString());
				hQuery.setParameters(parametros.toArray(), tipos.toArray(tipos.toArray(new Type[]{})));
				
				Number resultado = (Number)hQuery.uniqueResult();	
				
				return resultado.intValue();
			}
		});
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.indeval.estadocuenta.core.application.dao.SaldoDAO#obtenerProyeccionConsultaSaldosControladasHistorico(com.indeval.estadocuenta.core.application.dto.SaldoEfectivoDTO)
	 */
	public int obtenerProyeccionConsultaSaldosControladasHistorico(
			SaldoEfectivoDTO saldo) {
		
		final ArrayList<Object> parametros = new ArrayList<Object>();
		final StringBuffer query = new StringBuffer();
		final ArrayList<Object> tipos = new ArrayList<Object>();
		
		query.append("SELECT COUNT(*) FROM " + SaldoControladaHistorico.class.getName() + " saldoControlada ");
		
		query.append(" WHERE saldoControlada.fechaCreacion = trunc(?) ");
		parametros.add(saldo.getFecha());
		tipos.add(new DateType());
				
		//Agregamos los parametros de la busqueda
		query.append(construirCriterioSaldoControlado(saldo, "saldoControlada", parametros, tipos));
		
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				
				Query hQuery = session.createQuery(query.toString());
				hQuery.setParameters(parametros.toArray(), tipos.toArray(tipos.toArray(new Type[]{})));
				
				Number resultado = (Number)hQuery.uniqueResult();	
				
				return resultado.intValue();
			}
		});
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.indeval.estadocuenta.core.application.dao.SaldoDAO#obtenerProyeccionConsultaSaldosNombradas(com.indeval.estadocuenta.core.application.dto.SaldoEfectivoDTO)
	 */
	public int obtenerProyeccionConsultaSaldosNombradas(
			SaldoEfectivoDTO saldo) {
		
		final ArrayList<Object> parametros = new ArrayList<Object>();
		final StringBuffer query = new StringBuffer();
		final ArrayList<Object> tipos = new ArrayList<Object>();
		query.append("SELECT COUNT(*) FROM " + SaldoNombrada.class.getName() + " saldoNombrada ");
		
		query.append(" WHERE 1=1 ");
				
		//Agregamos los parametros de la busqueda
		query.append(construirCriterioSaldoNombrado(saldo, "saldoNombrada", parametros, tipos));
		
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				
				Query hQuery = session.createQuery(query.toString());
				hQuery.setParameters(parametros.toArray(), tipos.toArray(tipos.toArray(new Type[]{})));
				
				Number resultado = (Number)hQuery.uniqueResult();	
				
				return resultado.intValue();
			}
		});
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.indeval.estadocuenta.core.application.dao.SaldoDAO#obtenerProyeccionConsultaSaldosNombradasHistorico(com.indeval.estadocuenta.core.application.dto.SaldoEfectivoDTO)
	 */
	public int obtenerProyeccionConsultaSaldosNombradasHistorico(
			SaldoEfectivoDTO saldo) {
		
		final ArrayList<Object> parametros = new ArrayList<Object>();
		final StringBuffer query = new StringBuffer();
		final ArrayList<Object> tipos = new ArrayList<Object>();
		query.append("SELECT COUNT(*) FROM " + SaldoNombradaHistorico.class.getName() + " saldoNombrada ");
		
		query.append(" WHERE saldoNombrada.fechaCreacion = trunc(?) ");
		parametros.add(saldo.getFecha());
		tipos.add(new DateType());
				
		//Agregamos los parametros de la busqueda
		query.append(construirCriterioSaldoNombrado(saldo, "saldoNombrada", parametros, tipos));
		
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				
				Query hQuery = session.createQuery(query.toString());
				hQuery.setParameters(parametros.toArray(), tipos.toArray(tipos.toArray(new Type[]{})));
				
				Number resultado = (Number)hQuery.uniqueResult();	
				
				return resultado.intValue();
			}
		});
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.indeval.estadocuenta.core.application.dao.SaldoDAO#obtenerIdentificadoresValidosParaBoveda(com.indeval.estadocuenta.core.application.dto.SaldoEfectivoDTO)
	 */
	@SuppressWarnings("unchecked")
	public List<Long> obtenerIdentificadoresValidosParaBoveda(
			SaldoEfectivoDTO criterio) {
		
		final ArrayList<Object> parametros = new ArrayList<Object>();
		final StringBuffer query = new StringBuffer();
		final ArrayList<Object> tipos = new ArrayList<Object>();
		
		if(criterio.getCuenta().getTipoCuenta().getId().equals(TipoCuentaDTO.CUENTA_CONTROLADA)){
			query.append("SELECT DISTINCT  saldoControlada.idBoveda  FROM " + SaldoControlada.class.getName() + " saldoControlada ");
			query.append(" WHERE 1=1 ");
			query.append(construirCriterioSaldoControlado(criterio, "saldoControlada", parametros, tipos));
		}else{
			query.append("SELECT DISTINCT saldoNombrada.idBoveda  FROM " + SaldoNombrada.class.getName() + " saldoNombrada ");
			query.append(" WHERE 1=1 ");
			query.append(construirCriterioSaldoNombrado(criterio, "saldoNombrada", parametros, tipos));
		}
		
		return (List<Long>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				
				Query hQuery = session.createQuery(query.toString());
				hQuery.setParameters(parametros.toArray(), tipos.toArray(tipos.toArray(new Type[]{})));
				
				return DTOAssembler.transformarListaBigIntegerEnLong(hQuery.list());
			}
		});
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.indeval.estadocuenta.core.application.dao.SaldoDAO#obtenerIdentificadoresValidosParaBovedaHistorico(com.indeval.estadocuenta.core.application.dto.SaldoEfectivoDTO)
	 */
	@SuppressWarnings("unchecked")
	public List<Long> obtenerIdentificadoresValidosParaBovedaHistorico(
			SaldoEfectivoDTO criterio) {
		
		final ArrayList<Object> parametros = new ArrayList<Object>();
		final StringBuffer query = new StringBuffer();
		final ArrayList<Object> tipos = new ArrayList<Object>();
		
		if(criterio.getCuenta().getTipoCuenta().getId().equals(TipoCuentaDTO.CUENTA_CONTROLADA)){
			query.append("SELECT DISTINCT  saldoControlada.idBoveda  FROM " + SaldoControladaHistorico.class.getName() + " saldoControlada ");
			query.append(" WHERE saldoControlada.fechaCreacion = trunc(?) ");
			parametros.add(criterio.getFecha());
			tipos.add(new DateType());
			query.append(construirCriterioSaldoControlado(criterio, "saldoControlada", parametros, tipos));
		}else{
			query.append("SELECT DISTINCT saldoNombrada.idBoveda  FROM " + SaldoNombradaHistorico.class.getName() + " saldoNombrada ");
			query.append(" WHERE saldoNombrada.fechaCreacion = trunc(?) ");
			parametros.add(criterio.getFecha());
			tipos.add(new DateType());
			query.append(construirCriterioSaldoNombrado(criterio, "saldoNombrada", parametros, tipos));
		}
		
		return (List<Long>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				
				Query hQuery = session.createQuery(query.toString());
				hQuery.setParameters(parametros.toArray(), tipos.toArray(tipos.toArray(new Type[]{})));
				
				return DTOAssembler.transformarListaBigIntegerEnLong(hQuery.list());
			}
		});
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.indeval.estadocuenta.core.application.dao.SaldoDAO#obtenerIdentificadoresValidosParaDivisa(com.indeval.estadocuenta.core.application.dto.SaldoEfectivoDTO)
	 */
	@SuppressWarnings("unchecked")
	public List<Long> obtenerIdentificadoresValidosParaDivisa(
			SaldoEfectivoDTO criterio) {
		
		final ArrayList<Object> parametros = new ArrayList<Object>();
		final StringBuffer query = new StringBuffer();
		final ArrayList<Object> tipos = new ArrayList<Object>();
		
		if(criterio.getCuenta().getTipoCuenta().getId().equals(TipoCuentaDTO.CUENTA_CONTROLADA)){
			query.append("SELECT DISTINCT saldoControlada.idDivisa  FROM " + SaldoControlada.class.getName() + " saldoControlada ");
			query.append(" WHERE 1=1 ");
			query.append(construirCriterioSaldoControlado(criterio, "saldoControlada", parametros, tipos));
		}else{
			query.append("SELECT DISTINCT saldoNombrada.idDivisa  FROM " + SaldoNombrada.class.getName() + " saldoNombrada ");
			query.append(" WHERE 1=1 ");
			query.append(construirCriterioSaldoNombrado(criterio, "saldoNombrada", parametros, tipos));
		}
		
		return (List<Long>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				
				Query hQuery = session.createQuery(query.toString());
				hQuery.setParameters(parametros.toArray(), tipos.toArray(tipos.toArray(new Type[]{})));
			
				return DTOAssembler.transformarListaBigIntegerEnLong(hQuery.list());
			}
		});
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.indeval.estadocuenta.core.application.dao.SaldoDAO#obtenerIdentificadoresValidosParaDivisaHistorico(com.indeval.estadocuenta.core.application.dto.SaldoEfectivoDTO)
	 */
	@SuppressWarnings("unchecked")
	public List<Long> obtenerIdentificadoresValidosParaDivisaHistorico(
			SaldoEfectivoDTO criterio) {
		
		final ArrayList<Object> parametros = new ArrayList<Object>();
		final StringBuffer query = new StringBuffer();
		final ArrayList<Object> tipos = new ArrayList<Object>();
		
		if(criterio.getCuenta().getTipoCuenta().getId().equals(TipoCuentaDTO.CUENTA_CONTROLADA)){
			query.append("SELECT DISTINCT saldoControlada.idDivisa  FROM " + SaldoControladaHistorico.class.getName() + " saldoControlada ");
			query.append(" WHERE saldoControlada.fechaCreacion = trunc(?) ");
			parametros.add(criterio.getFecha());
			tipos.add(new DateType());
			query.append(construirCriterioSaldoControlado(criterio, "saldoControlada", parametros, tipos));
		}else{
			query.append("SELECT DISTINCT saldoNombrada.idDivisa  FROM " + SaldoNombradaHistorico.class.getName() + " saldoNombrada ");
			query.append(" WHERE saldoNombrada.fechaCreacion = trunc(?) ");
			parametros.add(criterio.getFecha());
			tipos.add(new DateType());
			query.append(construirCriterioSaldoNombrado(criterio, "saldoNombrada", parametros, tipos));
		}
		
		return (List<Long>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				
				Query hQuery = session.createQuery(query.toString());
				hQuery.setParameters(parametros.toArray(), tipos.toArray(tipos.toArray(new Type[]{})));
			
				return DTOAssembler.transformarListaBigIntegerEnLong(hQuery.list());
			}
		});
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.indeval.estadocuenta.core.application.dao.SaldoDAO#obtenerIdentificadoresValidosParaCuenta(com.indeval.estadocuenta.core.application.dto.SaldoEfectivoDTO)
	 */
	@SuppressWarnings("unchecked")
	public List<Long> obtenerIdentificadoresValidosParaCuenta(
			SaldoEfectivoDTO criterio) {
		
		final ArrayList<Object> parametros = new ArrayList<Object>();
		final StringBuffer query = new StringBuffer();
		final ArrayList<Object> tipos = new ArrayList<Object>();
	
		
		if(criterio.getCuenta().getTipoCuenta().getId().equals(TipoCuentaDTO.CUENTA_CONTROLADA)){
			query.append("SELECT DISTINCT saldoControlada.cuentaControlada.idCuentaControlada  FROM " + SaldoControlada.class.getName() + " saldoControlada ");
			query.append(" WHERE 1=1 ");
			query.append(construirCriterioSaldoControlado(criterio, "saldoControlada", parametros, tipos));
		}else{
			query.append("SELECT DISTINCT saldoNombrada.cuentaNombrada.idCuentaNombrada  FROM " + SaldoNombrada.class.getName() + " saldoNombrada ");
			query.append(" WHERE 1=1 ");
			query.append(construirCriterioSaldoNombrado(criterio, "saldoNombrada", parametros, tipos));
		}
		
		return (List<Long>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				
				Query hQuery = session.createQuery(query.toString());
				hQuery.setParameters(parametros.toArray(), tipos.toArray(tipos.toArray(new Type[]{})));
			
				return DTOAssembler.transformarListaBigIntegerEnLong(hQuery.list());
			}
		});
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.indeval.estadocuenta.core.application.dao.SaldoDAO#obtenerIdentificadoresValidosParaCuentaHistorico(com.indeval.estadocuenta.core.application.dto.SaldoEfectivoDTO)
	 */
	@SuppressWarnings("unchecked")
	public List<Long> obtenerIdentificadoresValidosParaCuentaHistorico(
			SaldoEfectivoDTO criterio) {
		
		final ArrayList<Object> parametros = new ArrayList<Object>();
		final StringBuffer query = new StringBuffer();
		final ArrayList<Object> tipos = new ArrayList<Object>();
	
		
		if(criterio.getCuenta().getTipoCuenta().getId().equals(TipoCuentaDTO.CUENTA_CONTROLADA)){
			query.append("SELECT DISTINCT saldoControlada.cuentaControlada.idCuentaControlada  FROM " + SaldoControladaHistorico.class.getName() + " saldoControlada ");
			query.append(" WHERE saldoControlada.fechaCreacion = trunc(?) ");
			parametros.add(criterio.getFecha());
			tipos.add(new DateType());
			query.append(construirCriterioSaldoControlado(criterio, "saldoControlada", parametros, tipos));
		}else{
			query.append("SELECT DISTINCT saldoNombrada.cuentaNombrada.idCuentaNombrada  FROM " + SaldoNombradaHistorico.class.getName() + " saldoNombrada ");
			query.append(" WHERE saldoNombrada.fechaCreacion = trunc(?) ");
			parametros.add(criterio.getFecha());
			tipos.add(new DateType());
			query.append(construirCriterioSaldoNombrado(criterio, "saldoNombrada", parametros, tipos));
		}
		
		return (List<Long>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				
				Query hQuery = session.createQuery(query.toString());
				hQuery.setParameters(parametros.toArray(), tipos.toArray(tipos.toArray(new Type[]{})));
			
				return DTOAssembler.transformarListaBigIntegerEnLong(hQuery.list());
			}
		});
		
	}

	/* (non-Javadoc)
	 * @see com.indeval.portaldali.persistence.dao.estadocuenta.SaldoDAO#obtenerSaldoEfectivoControladaHistoricoPorId(long)
	 */
	public SaldoEfectivoDTO obtenerSaldoEfectivoControladaHistoricoPorId(long idSaldo) {
		return DTOAssembler.crearSaldoEfectivoDTO((SaldoControladaHistorico ) getHibernateTemplate().get(SaldoControladaHistorico.class, new BigInteger(String.valueOf(idSaldo))));
	}

	/* (non-Javadoc)
	 * @see com.indeval.portaldali.persistence.dao.estadocuenta.SaldoDAO#obtenerSaldoEfectivoNombradaHistoricoPorId(long)
	 */
	public SaldoEfectivoDTO obtenerSaldoEfectivoNombradaHistoricoPorId(long idSaldo) {
		return DTOAssembler.crearSaldoEfectivoDTO((SaldoNombradaHistorico ) getHibernateTemplate().get(SaldoNombradaHistorico.class, new BigInteger(String.valueOf(idSaldo))));
	}

	/*
	 * (non-Javadoc)
	 * @see com.indeval.estadocuenta.core.application.dao.SaldoDAO#obtenerSaldoEfectivoControladaPorId(long)
	 */
	public SaldoEfectivoDTO obtenerSaldoEfectivoControladaPorId(long idSaldo) {
		return DTOAssembler.crearSaldoEfectivoDTO((SaldoControlada ) getHibernateTemplate().get(SaldoControlada.class, new BigInteger(String.valueOf(idSaldo))));
	}

	/*
	 * (non-Javadoc)
	 * @see com.indeval.estadocuenta.core.application.dao.SaldoDAO#obtenerSaldoEfectivoNombradaPorId(long)
	 */
	public SaldoEfectivoDTO obtenerSaldoEfectivoNombradaPorId(long idSaldo) {
		return DTOAssembler.crearSaldoEfectivoDTO((SaldoNombrada ) getHibernateTemplate().get(SaldoNombrada.class, new BigInteger(String.valueOf(idSaldo))));
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portaldali.persistence.dao.estadocuenta.SaldoDAO#obtenerTotalesConsultaSaldoControlada(SaldoEfectivoDTO)
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Number> obtenerTotalesConsultaSaldoControlada(SaldoEfectivoDTO criterio) {
		
		final ArrayList<Object> parametros = new ArrayList<Object>();
		final StringBuffer query = new StringBuffer();
		final ArrayList<Object> tipos = new ArrayList<Object>();
		
		query.append("SELECT SUM(saldoControlada.saldo) " +
				"FROM " + SaldoControlada.class.getName() + " saldoControlada ");
		
		query.append(" WHERE 1=1 ");
		
		//Agregamos los parametros de la busqueda
		query.append(construirCriterioSaldoControlado(criterio, "saldoControlada", parametros, tipos));
		
		return (Map<String, Number>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				
				Query hQuery = session.createQuery(query.toString());
				hQuery.setParameters(parametros.toArray(), tipos.toArray(tipos.toArray(new Type[]{})));
				
				Number resultado = (Number)hQuery.uniqueResult();	
				
				Map<String,Number> resultadosBusqueda = new HashMap<String, Number>();
				
				if(resultado != null){
					resultadosBusqueda.put("saldoTotal", resultado);
				}
				
				return resultadosBusqueda;
			}
		});
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portaldali.persistence.dao.estadocuenta.SaldoDAO#obtenerTotalesConsultaSaldoControladaHistorico(SaldoEfectivoDTO)
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Number> obtenerTotalesConsultaSaldoControladaHistorico(SaldoEfectivoDTO criterio) {
		
		final ArrayList<Object> parametros = new ArrayList<Object>();
		final StringBuffer query = new StringBuffer();
		final ArrayList<Object> tipos = new ArrayList<Object>();
		
		query.append("SELECT SUM(saldoControlada.saldo) " +
				"FROM " + SaldoControladaHistorico.class.getName() + " saldoControlada ");
		
		query.append(" WHERE saldoControlada.fechaCreacion = trunc(?) ");
		parametros.add(criterio.getFecha());
		tipos.add(new DateType());
		
		//Agregamos los parametros de la busqueda
		query.append(construirCriterioSaldoControlado(criterio, "saldoControlada", parametros, tipos));
		
		return (Map<String, Number>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				
				Query hQuery = session.createQuery(query.toString());
				hQuery.setParameters(parametros.toArray(), tipos.toArray(tipos.toArray(new Type[]{})));
				
				Number resultado = (Number)hQuery.uniqueResult();	
				
				Map<String,Number> resultadosBusqueda = new HashMap<String, Number>();
				
				if(resultado != null){
					resultadosBusqueda.put("saldoTotal", resultado);
				}
				
				return resultadosBusqueda;
			}
		});
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portaldali.persistence.dao.estadocuenta.SaldoDAO#obtenerTotalesConsultaSaldoNombrada(SaldoEfectivoDTO)
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Number> obtenerTotalesConsultaSaldoNombrada(SaldoEfectivoDTO criterio) {
		
		final ArrayList<Object> parametros = new ArrayList<Object>();
		final StringBuffer query = new StringBuffer();
		final ArrayList<Object> tipos = new ArrayList<Object>();
		
		query.append("SELECT SUM(saldoNombrada.saldoDisponible + saldoNombrada.saldoNoDisponible) , " +
							"SUM(saldoNombrada.saldoDisponible) , SUM(saldoNombrada.saldoNoDisponible) " +
							"FROM " + SaldoNombrada.class.getName() + " saldoNombrada ");
		
		query.append(" WHERE 1=1 ");
		
		//Agregamos los parametros de la busqueda
		query.append(construirCriterioSaldoNombrado(criterio, "saldoNombrada", parametros, tipos));
		
		return (Map<String, Number>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				
				Query hQuery = session.createQuery(query.toString());
				hQuery.setParameters(parametros.toArray(), tipos.toArray(tipos.toArray(new Type[]{})));
				Object[] resultado = (Object[])hQuery.uniqueResult();	
		
				Map<String,Number> resultadosBusqueda = new HashMap<String, Number>();
		
				if(resultado != null){
					resultadosBusqueda.put("saldoTotal", (Number)resultado[0]);
					resultadosBusqueda.put("saldoTotalDisponible", (Number)resultado[1]);
					resultadosBusqueda.put("saldoTotalNoDisponible", (Number)resultado[2]);
				}
		
				return resultadosBusqueda;
			}
		});
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portaldali.persistence.dao.estadocuenta.SaldoDAO#obtenerTotalesConsultaSaldoNombradaHistorico(SaldoEfectivoDTO)
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Number> obtenerTotalesConsultaSaldoNombradaHistorico(SaldoEfectivoDTO criterio) {
		
		final ArrayList<Object> parametros = new ArrayList<Object>();
		final StringBuffer query = new StringBuffer();
		final ArrayList<Object> tipos = new ArrayList<Object>();
		
		query.append("SELECT SUM(saldoNombrada.saldoDisponible + saldoNombrada.saldoNoDisponible) , " +
							"SUM(saldoNombrada.saldoDisponible) , SUM(saldoNombrada.saldoNoDisponible) " +
							"FROM " + SaldoNombradaHistorico.class.getName() + " saldoNombrada ");
		
		query.append(" WHERE saldoNombrada.fechaCreacion = trunc(?) ");
		parametros.add(criterio.getFecha());
		tipos.add(new DateType());
		
		//Agregamos los parametros de la busqueda
		query.append(construirCriterioSaldoNombrado(criterio, "saldoNombrada", parametros, tipos));
		
		return (Map<String, Number>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				
				Query hQuery = session.createQuery(query.toString());
				hQuery.setParameters(parametros.toArray(), tipos.toArray(tipos.toArray(new Type[]{})));
				Object[] resultado = (Object[])hQuery.uniqueResult();	
		
				Map<String,Number> resultadosBusqueda = new HashMap<String, Number>();
		
				if(resultado != null){
					resultadosBusqueda.put("saldoTotal", (Number)resultado[0]);
					resultadosBusqueda.put("saldoTotalDisponible", (Number)resultado[1]);
					resultadosBusqueda.put("saldoTotalNoDisponible", (Number)resultado[2]);
				}
		
				return resultadosBusqueda;
			}
		});
	}

	
	/**
	 * Construye la cadena de criterios y llena los parametros y tipos necesarios utilizados para la consulta
	 * de saldo de cuentas nombradas
	 * @param criterio Criterio de consulta para armar el query
	 * @param aliasEstadoSaldo Alias de la tabla de saldo nombrada
	 * @param params Lista de parametros, en esta lista se dejaran los parametros que la consulta utiliza
	 * @param tipos Lista de tipos, en esta lista se dejaran los tipos de parametros utilizados
	 * @return String con el query de los criterios de consulta
	 */
	private String construirCriterioSaldoNombrado(SaldoEfectivoDTO criterio,String aliasEstadoSaldo,ArrayList<Object> params,ArrayList<Object> tipos){
		StringBuffer query = new StringBuffer();
				
		if(criterio.getCuenta() != null && !criterio.getCuenta().getNumeroCuenta().equals("-1")) {
			query.append("AND ("+aliasEstadoSaldo+".cuentaNombrada.institucion.tipoInstitucion.claveTipoInstitucion " +
					"|| "+aliasEstadoSaldo+".cuentaNombrada.institucion.folioInstitucion || " +
					aliasEstadoSaldo+".cuentaNombrada.cuenta)" +
					" = ? ");
			params.add(criterio.getCuenta().getNumeroCuenta());
			tipos.add(new StringType());
		}
		if(criterio.getDivisa() != null && criterio.getDivisa().getId() > 0) {
			query.append("AND "+aliasEstadoSaldo+".idDivisa = ? ");
			params.add(new BigInteger(String.valueOf(criterio.getDivisa().getId())));
			tipos.add(new BigIntegerType());
		}
		if(criterio.getBoveda() != null && criterio.getBoveda().getId() > 0) {
			query.append("AND "+aliasEstadoSaldo+".idBoveda = ? ");
			params.add(new BigInteger(String.valueOf(criterio.getBoveda().getId())));
			tipos.add(new BigIntegerType());
		}		
		if( criterio.getCuenta().getInstitucion() != null && criterio.getCuenta().getInstitucion().getId()>0 ){
			query.append("AND "+aliasEstadoSaldo+".cuentaNombrada.institucion.idInstitucion = ? ");
			params.add(new BigInteger(String.valueOf(criterio.getCuenta().getInstitucion().getId())));
			tipos.add(new BigIntegerType());
		}
		if(criterio.getCuenta().getInstitucion().getClaveTipoInstitucion() != null) {
			query.append("AND "+aliasEstadoSaldo+".cuentaNombrada.institucion.tipoInstitucion.claveTipoInstitucion = ? ");
			params.add(new BigInteger(String.valueOf(criterio.getCuenta().getInstitucion().getClaveTipoInstitucion())));
			tipos.add(new BigIntegerType());
		}
		if( criterio.getCuenta().getTipoNaturaleza() != null && !criterio.getCuenta().getTipoNaturaleza().getId().equals("-1") ){
			query.append("AND "+aliasEstadoSaldo+".cuentaNombrada.tipoCuenta.naturalezaContable = ? ");
			params.add(criterio.getCuenta().getTipoNaturaleza().getId());
			tipos.add(new StringType());
		}
		
		return query.toString();
	}
	
	/**
	 * Construye la cadena de criterios y llena los parámetros y tipos necesarios utilizados para la consulta
	 * de saldo de cuentas controladas
	 * @param criterio Criterio de consulta para armar el query
	 * @param aliasEstadoSaldo Alias de la tabla de estado de saldo nombrada
	 * @param params Lista de parámetros, en esta lista se dejarn los parámetros que la consulta utiliza
	 * @param tipos Lista de tipos, en esta lista se dejarn los tipos de parámetros utilizados
	 * @return String con el query de los criterios de consulta
	 */
	private String construirCriterioSaldoControlado(SaldoEfectivoDTO criterio,String aliasEstadoSaldo,ArrayList<Object> params,ArrayList<Object> tipos){
		log.debug("SaldoDAOV2Impl :: construirCriterioSaldoControlado");
		StringBuffer query = new StringBuffer();
		
		if(criterio.getCuenta() != null && !criterio.getCuenta().getNumeroCuenta().equals("-1")) {
			query.append("AND ("+aliasEstadoSaldo+".cuentaControlada.institucion.tipoInstitucion.claveTipoInstitucion " +
					"|| "+aliasEstadoSaldo+".cuentaControlada.institucion.folioInstitucion || " +
					aliasEstadoSaldo+".cuentaControlada.cuenta)" +
					" = ? ");
			params.add(criterio.getCuenta().getNumeroCuenta());
			tipos.add(new StringType());
		}
		if(criterio.getDivisa() != null && criterio.getDivisa().getId() > 0) {
			query.append("AND "+aliasEstadoSaldo+".idDivisa = ? ");
			params.add(new BigInteger(String.valueOf(criterio.getDivisa().getId())));
			tipos.add(new BigIntegerType());
		}
		if(criterio.getBoveda() != null && criterio.getBoveda().getId() > 0) {
			query.append("AND "+aliasEstadoSaldo+".idBoveda = ? ");
			params.add(new BigInteger(String.valueOf(criterio.getBoveda().getId())));
			tipos.add(new BigIntegerType());
		}		
		if(criterio.getCuenta().getInstitucion() != null && criterio.getCuenta().getInstitucion().getId()>0 ){
			query.append("AND "+aliasEstadoSaldo+".cuentaControlada.institucion.idInstitucion = ? ");
			params.add(new BigInteger(String.valueOf(criterio.getCuenta().getInstitucion().getId())));
			tipos.add(new BigIntegerType());

			if(criterio.getCuenta().getInstitucion().getClaveTipoInstitucion() != null) {
				query.append("AND "+aliasEstadoSaldo+".cuentaControlada.institucion.tipoInstitucion.claveTipoInstitucion = ? ");
				params.add(new BigInteger(String.valueOf(criterio.getCuenta().getInstitucion().getClaveTipoInstitucion())));
				tipos.add(new BigIntegerType());
			}
		}
		if(criterio.getCuenta().getTipoNaturaleza() != null && ! "-1".equals(criterio.getCuenta().getTipoNaturaleza().getId())){
			query.append("AND "+aliasEstadoSaldo+".cuentaControlada.tipoCuenta.naturalezaContable = ? ");
			params.add(criterio.getCuenta().getTipoNaturaleza().getId());
			tipos.add(new StringType());
		}
		
		if(criterio.getCuenta().getTipoCustodia() != null &&  "E".equals(criterio.getCuenta().getTipoCustodia())){
			query.append("AND "+aliasEstadoSaldo+".cuentaControlada.tipoCuenta.tipoCustodia = ? ");
			params.add(criterio.getCuenta().getTipoCustodia());
			tipos.add(new StringType());
		}
		
		return query.toString();
	}
}
