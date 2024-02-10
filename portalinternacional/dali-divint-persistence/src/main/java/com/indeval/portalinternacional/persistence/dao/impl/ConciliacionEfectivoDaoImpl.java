package com.indeval.portalinternacional.persistence.dao.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.modelo.Boveda;
import com.indeval.portaldali.persistence.modelo.Divisa;
import com.indeval.portaldali.persistence.util.DateUtils;
import com.indeval.portalinternacional.middleware.servicios.modelo.BitacoraConciliacionEfectivoInt;
import com.indeval.portalinternacional.middleware.servicios.modelo.ComentarioEfectivoInt;
import com.indeval.portalinternacional.middleware.servicios.modelo.ConciliacionEfectivoInt;
import com.indeval.portalinternacional.middleware.servicios.modelo.CuentaEfectivoInt;
import com.indeval.portalinternacional.middleware.servicios.modelo.DetalleConciliacionEfectivoInt;
import com.indeval.portalinternacional.middleware.servicios.modelo.HistoricoMovimientoEfe;
import com.indeval.portalinternacional.middleware.servicios.modelo.PaisInt;
import com.indeval.portalinternacional.middleware.servicios.vo.ConciliacionEfectivoIntDTO;
import com.indeval.portalinternacional.middleware.servicios.vo.CuentaEfectivoIntDTO;
import com.indeval.portalinternacional.middleware.servicios.vo.DetalleConciliacionEfectivoIntDTO;
import com.indeval.portalinternacional.persistence.dao.ConciliacionEfectivoDao;

/**
 * @author lmunoz
 *
 */
public class ConciliacionEfectivoDaoImpl extends BaseDaoHibernateImpl implements ConciliacionEfectivoDao {
	
	@SuppressWarnings("unchecked")
	public PaginaVO consultaConciliacionEfectivoInt(final ConciliacionEfectivoIntDTO params, PaginaVO pagina) {
		if(pagina == null){
			pagina = new PaginaVO();
		}
		
		final Integer offset = pagina.getOffset() != null ? pagina.getOffset() : null;		
		final Integer regxpag = pagina.getRegistrosXPag() != null ? pagina.getRegistrosXPag() : null;
		
		final String totalConsulta = createConciliacionEfectivoQuery(params, "count");
		
		Long totalConciliaciones = (Long) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(totalConsulta);
				query = createConciliacionEfectivoParams(params, query);
				return query.uniqueResult();
			}
		});
		
		pagina.setTotalRegistros(totalConciliaciones.intValue());
		
		if(totalConciliaciones > 0) {
			final String consulta = createConciliacionEfectivoQuery(params, "conc");
		
			List<ConciliacionEfectivoInt> listaConciliaciones = (List<ConciliacionEfectivoInt>) getHibernateTemplate().execute(new HibernateCallback() {
				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					Query query = session.createQuery(consulta);
					query = createConciliacionEfectivoParams(params, query);
				
					if ( offset != null && regxpag !=null && regxpag != PaginaVO.TODOS) {
						query.setMaxResults(regxpag);
						query.setFetchSize(regxpag);
						query.setFirstResult(offset);
					}
					return query.list();
				}
			});
			
			if(listaConciliaciones != null){
				pagina.setRegistros(listaConciliaciones);
			}
		}
		
		return pagina;
	}
	
	public Object[] obtieneTotalesConciliacion(final ConciliacionEfectivoIntDTO params, String tipo) {
		final String sumConsulta = createConciliacionEfectivoQuery(params, tipo);
		
		Object[] sumaBalance = (Object[]) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sumConsulta);
				query = createConciliacionEfectivoParams(params, query);
				return query.uniqueResult();
			}
		});
		
		return sumaBalance;
	}
	
	@SuppressWarnings("unchecked")
	public PaginaVO consultaDetalleConciliacionEfectivoInt(final DetalleConciliacionEfectivoIntDTO params, PaginaVO pagina) {
		if(pagina == null){
			pagina = new PaginaVO();
		}
		
		final Integer offset = pagina.getOffset() != null ? pagina.getOffset() : null;		
		final Integer regxpag = pagina.getRegistrosXPag() != null ? pagina.getRegistrosXPag() : null;
		
		final String totalConsulta = createDetalleConciliacionEfectivoQuery(params, "count");
		
		Long totalConciliaciones = (Long) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(totalConsulta);
				query = createDetalleConciliacionEfectivoParams(params, query);
				return query.uniqueResult();
			}
		});
		
		pagina.setTotalRegistros(totalConciliaciones.intValue());
		
		if(totalConciliaciones > 0) {
			final String consulta = createDetalleConciliacionEfectivoQuery(params, "conc");
		
			List<DetalleConciliacionEfectivoInt> listaDetalleConciliaciones = (List<DetalleConciliacionEfectivoInt>) getHibernateTemplate().execute(new HibernateCallback() {
				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					Query query = session.createQuery(consulta);
					query = createDetalleConciliacionEfectivoParams(params, query);
				
					if ( offset != null && regxpag !=null && regxpag != PaginaVO.TODOS) {
						query.setMaxResults(regxpag);
						query.setFetchSize(regxpag);
						query.setFirstResult(offset);
					}
					return query.list();
				}
			});
			
			if(listaDetalleConciliaciones != null){
				pagina.setRegistros(listaDetalleConciliaciones);
			}
		}
		
		return pagina;
	}
	
	public BigDecimal obtieneSumaCreditoDebito(final DetalleConciliacionEfectivoIntDTO params, String tipo) {
		final String sumConsulta = createDetalleConciliacionEfectivoQuery(params, tipo);
		
		BigDecimal suma = (BigDecimal) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sumConsulta);
				query = createDetalleConciliacionEfectivoParams(params, query);
				return query.uniqueResult();
			}
		});
		
		return suma;
	}
	
	private String createConciliacionEfectivoQuery(ConciliacionEfectivoIntDTO params, String tipo){
		StringBuilder sb = new StringBuilder();
		String fetch = "";
		
		if(tipo.equals("count")){
			sb.append("select count(a.folioConciliacion) ");
			fetch = "JOIN ";
		} else if(tipo.equals("totales")){
			sb.append("select sum(a.balanceInicio), sum(a.balanceFinal), sum(a.netoMovimientos), sum(a.saldoBoveda), ");
			sb.append("sum(a.balanceFinal - a.saldoBoveda), sum(a.balanceFinal - (a.balanceInicio + a.netoMovimientos)) ");
			fetch = "JOIN ";
		} else {
			sb.append("select a ");
			fetch = "JOIN FETCH ";
		}
		
		sb.append("FROM ConciliacionEfectivoInt a ");
		sb.append(fetch);
		sb.append("a.listaBitacoraConciliacionEfectivo b ");
		sb.append("WHERE b.idBitacora = ");
		sb.append("(SELECT min(c.idBitacora) FROM BitacoraConciliacionEfectivoInt c WHERE b.idConciliacionEfectivo = c.idConciliacionEfectivo ");
		
		if(params.getFechaEmisionInicio() != null && params.getFechaEmisionFin() != null){
			sb.append("AND c.fechaEmision between :fechaEmisionInicio and :fechaEmisionFin) ");
		} else if(params.getFechaEmisionInicio() != null){
			sb.append("AND c.fechaEmision <= :fechaEmisionInicio) ");
		} else if(params.getFechaEmisionFin() != null){
			sb.append("AND c.fechaEmision >= :fechaEmisionFin) ");
		} else {
			sb.append(") ");
		}
		
		if(params.getBicCodes() != null && params.getBicCodes().size() > 0 && !params.getBicCodes().contains("TODOS")){
			sb.append("AND a.bicCode in (:bicCodes) ");
		}
		if(params.getDivisas() != null && params.getDivisas().size() > 0 && !params.getDivisas().contains("TODAS")){
			sb.append("AND a.divisa in (:divisas) ");
		}
		if(params.getCuentas() != null && params.getCuentas().size() > 0 && !params.getCuentas().contains("TODAS")){
			sb.append("AND a.cuenta in (:cuentas) ");
		}
		if(params.getCuentaComercial() || params.getCuentaCustodia()){
			sb.append("AND a.cuenta in (select cuenta from CuentaEfectivoInt where tipoCuenta in (:tipoCuenta)) ");
		}
		if(params.getDiferencia()){
			sb.append("AND a.balanceFinal - a.saldoBoveda != 0 ");
		}
		if(params.getFolioConciliacion() != null){
			sb.append("AND a.folioConciliacion = :folioConciliacion ");
		}
		if(params.getStatementNumber() != null){
			sb.append("AND a.statementNumber = :statementNumber ");
		}
		if(params.getReferenciaMT() != null){
			sb.append("AND a.referencia = :referenciaMT ");
		}
		if(params.getFechaBalanceInicio() != null && params.getFechaBalanceFin() != null){
			sb.append("AND a.fechaBalance between :fechaBalanceInicio and :fechaBalanceFin ");
		} else if(params.getFechaBalanceInicio() != null){
			sb.append("AND a.fechaBalance <= :fechaBalanceInicio ");
		} else if(params.getFechaBalanceFin() != null){
			sb.append("AND a.fechaBalance >= :fechaBalanceFin ");
		}
		sb.append("ORDER BY a.folioConciliacion desc");
		
		return sb.toString();
	}
	
	private String createDetalleConciliacionEfectivoQuery(DetalleConciliacionEfectivoIntDTO params, String tipo){
		StringBuilder sb = new StringBuilder();
		String fetch = "";
		
		if(tipo.equals("count")){
			sb.append("select count(distinct c) ");
			fetch = "LEFT JOIN ";
		} else if(tipo.equals("credito") || tipo.equals("debito")){
			sb.append("select sum(c.monto) ");
			fetch = "LEFT JOIN ";
		} else {
			sb.append("select distinct c ");
			fetch = "LEFT JOIN FETCH ";
		}
		
		sb.append("FROM ConciliacionEfectivoInt a ");
		sb.append("JOIN a.listaDetalleConciliacionEfectivo c ");
		sb.append(fetch);
		sb.append("c.listaComentarioEfectivo co ");
		sb.append("WHERE a.folioConciliacion is not null ");
		
		if(tipo.equals("credito")){
			sb.append("AND c.tipo = 'C' ");
		} else if(tipo.equals("debito")){
			sb.append("AND c.tipo = 'D' ");
		}
		
		if(params.getBicCodes() != null && params.getBicCodes().size() > 0 && !params.getBicCodes().contains("TODOS")){
			sb.append("AND a.bicCode in (:bicCodes) ");
		}
		if(params.getDivisas() != null && params.getDivisas().size() > 0 && !params.getDivisas().contains("TODAS")){
			sb.append("AND a.divisa in (:divisas) ");
		}
		if(params.getCuentas() != null && params.getCuentas().size() > 0 && !params.getCuentas().contains("TODAS")){
			sb.append("AND a.cuenta in (:cuentas) ");
		}
		if(params.getCuentaComercial() || params.getCuentaCustodia()){
			sb.append("AND a.cuenta in (select cuenta from CuentaEfectivoInt where tipoCuenta in (:tipoCuenta)) ");
		}
		if(params.getComentarios()){
			sb.append("AND c.conComentarios = 1");
		}
		if(params.getCodigoVerificacion() != null && params.getCodigoVerificacion() > 0){
			sb.append("AND c.codigoIdentificacionEfectivo.idCodigoIdentificacion = :codigoIdentificacion ");
		}
		if(params.getTipo() != null){
			sb.append("AND c.tipo = :tipo ");
		}
		if(params.getFolioConciliacion() != null){
			sb.append("AND a.folioConciliacion = :folioConciliacion ");
		}
		if(params.getReferenciaMensaje() != null){
			sb.append("AND a.referencia = :referencia ");
		}
		if(params.getFechaBalanceInicio() != null && params.getFechaBalanceFin() != null){
			sb.append("AND a.fechaBalance between :fechaBalanceInicio and :fechaBalanceFin ");
		} else if(params.getFechaBalanceInicio() != null){
			sb.append("AND a.fechaBalance <= :fechaBalanceInicio ");
		} else if(params.getFechaBalanceFin() != null){
			sb.append("AND a.fechaBalance >= :fechaBalanceFin ");
		}
		if(params.getFechaEmisionInicio() != null && params.getFechaEmisionFin() != null){
			sb.append("AND c.fechaRegistro between :fechaEmisionInicio and :fechaEmisionFin ");
		} else if(params.getFechaEmisionInicio() != null){
			sb.append("AND c.fechaRegistro <= :fechaEmisionInicio ");
		} else if(params.getFechaEmisionFin() != null){
			sb.append("AND c.fechaRegistro >= :fechaEmisionFin ");
		}
		if(params.getFechaCreditoDebitoInicio() != null && params.getFechaCreditoDebitoFin() != null){
			sb.append("AND c.fechaValor between :fechaCreditoDebitoInicio and :fechaCreditoDebitoFin ");
		} else if(params.getFechaCreditoDebitoInicio() != null){
			sb.append("AND c.fechaValor <= :fechaCreditoDebitoInicio ");
		} else if(params.getFechaCreditoDebitoFin() != null){
			sb.append("AND c.fechaValor >= :fechaCreditoDebitoFin ");
		}
		sb.append("ORDER BY c.conciliacionEfectivo.folioConciliacion desc");
		
		return sb.toString();
	}
	
	private Query createConciliacionEfectivoParams(ConciliacionEfectivoIntDTO params, Query query){
		if(params.getBicCodes() != null && params.getBicCodes().size() > 0 && !params.getBicCodes().contains("TODOS")){
			query.setParameterList("bicCodes", params.getBicCodes());
		}
		if(params.getDivisas() != null && params.getDivisas().size() > 0 && !params.getDivisas().contains("TODAS")){
			query.setParameterList("divisas", params.getDivisas());
		}
		if(params.getCuentas() != null && params.getCuentas().size() > 0 && !params.getCuentas().contains("TODAS")){
			query.setParameterList("cuentas", params.getCuentas());
		}
		if(params.getCuentaComercial() || params.getCuentaCustodia()){
			Set<Integer> tipoCuenta = new HashSet<Integer>();
			
			if(params.getCuentaComercial())
				tipoCuenta.add(1);
			
			if(params.getCuentaCustodia())
				tipoCuenta.add(2);
			
			query.setParameterList("tipoCuenta", tipoCuenta);
		}
		if(params.getFolioConciliacion() != null){
			query.setLong("folioConciliacion", params.getFolioConciliacion());
		}
		if(params.getStatementNumber() != null){
			query.setInteger("statementNumber", params.getStatementNumber());
		}
		if(params.getReferenciaMT() != null){
			query.setString("referenciaMT", params.getReferenciaMT());
		}
		if(params.getFechaBalanceInicio() != null && params.getFechaBalanceFin() != null){
			query.setParameter("fechaBalanceInicio", DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaBalanceInicio(), true));
			query.setParameter("fechaBalanceFin", DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaBalanceFin(), false));
		} else if(params.getFechaBalanceInicio() != null){
			query.setParameter("fechaBalanceInicio", DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaBalanceInicio(), false));
		} else if(params.getFechaBalanceFin() != null){
			query.setParameter("fechaBalanceFin", DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaBalanceFin(), true));
		}
		if(params.getFechaEmisionInicio() != null && params.getFechaEmisionFin() != null){
			query.setParameter("fechaEmisionInicio", params.getFechaEmisionInicio());
			query.setParameter("fechaEmisionFin", params.getFechaEmisionFin());
		} else if(params.getFechaEmisionInicio() != null){
			query.setParameter("fechaEmisionInicio", params.getFechaEmisionInicio());
		} else if(params.getFechaEmisionFin() != null){
			query.setParameter("fechaEmisionFin", params.getFechaEmisionFin());
		}
		return query;
	}
	
	private Query createDetalleConciliacionEfectivoParams(DetalleConciliacionEfectivoIntDTO params, Query query){
		if(params.getBicCodes() != null && params.getBicCodes().size() > 0 && !params.getBicCodes().contains("TODOS")){
			query.setParameterList("bicCodes", params.getBicCodes());
		}
		if(params.getDivisas() != null && params.getDivisas().size() > 0 && !params.getDivisas().contains("TODAS")){
			query.setParameterList("divisas", params.getDivisas());
		}
		if(params.getCuentas() != null && params.getCuentas().size() > 0 && !params.getCuentas().contains("TODAS")){
			query.setParameterList("cuentas", params.getCuentas());
		}
		if(params.getCuentaComercial() || params.getCuentaCustodia()){
			Set<Integer> tipoCuenta = new HashSet<Integer>();
			
			if(params.getCuentaComercial())
				tipoCuenta.add(1);
			
			if(params.getCuentaCustodia())
				tipoCuenta.add(2);
			
			query.setParameterList("tipoCuenta", tipoCuenta);
		}
		if(params.getCodigoVerificacion() != null && params.getCodigoVerificacion() > 0){
			query.setInteger("codigoIdentificacion", params.getCodigoVerificacion());
		}
		if(params.getTipo() != null){
			query.setString("tipo", params.getTipo());
		}
		if(params.getFolioConciliacion() != null){
			query.setLong("folioConciliacion", params.getFolioConciliacion());
		}
		if(params.getReferenciaMensaje() != null){
			query.setString("referencia", params.getReferenciaMensaje());
		}
		if(params.getFechaBalanceInicio() != null && params.getFechaBalanceFin() != null){
			query.setParameter("fechaBalanceInicio", DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaBalanceInicio(), true));
			query.setParameter("fechaBalanceFin", DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaBalanceFin(), false));
		} else if(params.getFechaBalanceInicio() != null){
			query.setParameter("fechaBalanceInicio", DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaBalanceInicio(), false));
		} else if(params.getFechaBalanceFin() != null){
			query.setParameter("fechaBalanceFin", DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaBalanceFin(), true));
		}
		if(params.getFechaEmisionInicio() != null && params.getFechaEmisionFin() != null){
			query.setParameter("fechaEmisionInicio", DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaEmisionInicio(),true));
			query.setParameter("fechaEmisionFin", DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaEmisionFin(), false));
		} else if(params.getFechaEmisionInicio() != null){
			query.setParameter("fechaEmisionInicio", DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaEmisionInicio(),false));
		} else if(params.getFechaEmisionFin() != null){
			query.setParameter("fechaEmisionFin", DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaEmisionFin(), true));
		}
		if(params.getFechaCreditoDebitoInicio() != null && params.getFechaCreditoDebitoFin() != null){
			query.setParameter("fechaCreditoDebitoInicio", DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaCreditoDebitoInicio(),true));
			query.setParameter("fechaCreditoDebitoFin", DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaCreditoDebitoFin(), false));
		} else if(params.getFechaCreditoDebitoInicio() != null){
			query.setParameter("fechaCreditoDebitoInicio", DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaCreditoDebitoInicio(),false));
		} else if(params.getFechaCreditoDebitoFin() != null){
			query.setParameter("fechaCreditoDebitoFin", DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaCreditoDebitoFin(), true));
		}
		return query;
	}

	/**
	 * @see com.indeval.portalinternacional.persistence.dao.ConciliacionEfectivoDao#getBitacoraConciliacionEfectivoInt(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public List<BitacoraConciliacionEfectivoInt> getBitacoraConciliacionEfectivoInt(final Long idConciliacion) {
		final StringBuilder sb = new StringBuilder();		
		sb.append("FROM BitacoraConciliacionEfectivoInt b ");
		sb.append(" WHERE b.idConciliacionEfectivo = :idConciliacion ");		
		return (List<BitacoraConciliacionEfectivoInt>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sb.toString());
				query.setLong("idConciliacion", idConciliacion);
				return query.list();
			}
		});
	}

	@SuppressWarnings("unchecked")
	public List<String> consultaBicCodes() {
		final StringBuilder sb = new StringBuilder();		
		sb.append("SELECT distinct bicCode FROM CuentaEfectivoInt a ");
		sb.append("WHERE a.activo = 1 ");
		sb.append("ORDER BY bicCode ");
		
		return (List<String>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sb.toString());
				return query.list();
			}
		});
	}

	@SuppressWarnings("unchecked")
	public List<Divisa> consultaDivisas(final Set<String> bicCodes) {
		final StringBuilder sb = new StringBuilder();		
		sb.append("SELECT distinct a.divisa FROM CuentaEfectivoInt a ");
		sb.append("WHERE a.activo = 1 ");
		
		if(bicCodes != null && bicCodes.size() > 0 && !bicCodes.contains("TODOS")){
			sb.append("AND a.bicCode IN (:bicCodes) ");
		}
		sb.append("ORDER BY case a.divisa.claveAlfabetica ");
		sb.append("when 'USD' then 1 when 'MXN' then 2 when 'EUR' then 3 when 'GBP' then 4 end, a.divisa.claveAlfabetica");
		
		return (List<Divisa>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sb.toString());
				if(bicCodes != null && bicCodes.size() > 0 && !bicCodes.contains("TODOS")){
					query.setParameterList("bicCodes", bicCodes);
				}
				return query.list();
			}
		});
	}

	@SuppressWarnings("unchecked")
	public List<String> consultaCuentas(final Set<String> bicCodes, final Set<String> divisas) {
		final StringBuilder sb = new StringBuilder();
		sb.append("SELECT distinct a.cuenta FROM CuentaEfectivoInt a ");
		sb.append("WHERE a.activo = 1 ");
		
		if(bicCodes != null && bicCodes.size() > 0 && !bicCodes.contains("TODOS")){
			sb.append("AND a.bicCode IN (:bicCodes) ");
		}
		
		if(divisas != null && divisas.size() > 0 && !divisas.contains("TODAS")){
			sb.append("AND a.divisa.claveAlfabetica IN (:divisas) ");
		}
		sb.append("ORDER BY a.cuenta");
		
		return (List<String>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sb.toString());
				
				if(bicCodes != null && bicCodes.size() > 0 && !bicCodes.contains("TODOS")){
					query.setParameterList("bicCodes", bicCodes);
				}
				
				if(divisas != null && divisas.size() > 0 && !divisas.contains("TODAS")){
					query.setParameterList("divisas", divisas);
				}
				return query.list();
			}
		});
	}

	@SuppressWarnings("unchecked")
	public List<ComentarioEfectivoInt> findComentariosByIdDetalle(final Long idDetalle) {
		final StringBuilder sb = new StringBuilder();		
		sb.append("FROM ComentarioEfectivoInt a ");
		sb.append("WHERE a.idDetalle = :idDetalle");
		
		return (List<ComentarioEfectivoInt>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sb.toString());
				query.setLong("idDetalle", idDetalle);
				return query.list();
			}
		});
	}

	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.persistence.dao.ConciliacionEfectivoDao#consultaCuentasEfectivo()
	 */
	@SuppressWarnings("unchecked")
	public PaginaVO consultaCuentasEfectivo(final PaginaVO paginaVO, final CuentaEfectivoIntDTO dto) throws BusinessException {		
		List<CuentaEfectivoInt> list = (List<CuentaEfectivoInt>)
			this.getHibernateTemplate().execute(new HibernateCallback() {
	        	public List<CuentaEfectivoInt> doInHibernate(Session session) throws HibernateException, SQLException {
	        		Criteria criteria = session.createCriteria(CuentaEfectivoInt.class, "ce");
	        		
	        		creaCriterio(criteria, dto);
	        		
	        		criteria.addOrder(Order.asc("bicCode"));
	        		criteria.addOrder(Order.asc("cuenta"));
	        		
	        		if(paginaVO != null && paginaVO.getRegistrosXPag() != PaginaVO.TODOS){
	        			criteria.setFirstResult(paginaVO.getOffset());
	        			criteria.setMaxResults(paginaVO.getRegistrosXPag());
	        			criteria.setFetchSize(paginaVO.getRegistrosXPag());
	        		}
	        		
	        		return criteria.list();
	        	}
			});
		
		if(list != null){
			paginaVO.setRegistros(list);
			
			Long total = (Long)
					this.getHibernateTemplate().execute(new HibernateCallback() {
			        	public Long doInHibernate(Session session) throws HibernateException, SQLException {
			        		
			        		Criteria criteria = session.createCriteria(CuentaEfectivoInt.class, "ce");
			        		criteria.setProjection(Projections.rowCount());
			        		
			        		creaCriterio(criteria, dto);

			        		return Long.valueOf(criteria.uniqueResult().toString());
			        	}
					});
			
			paginaVO.setTotalRegistros(total.intValue());
		}
		
		return paginaVO;
	}
	
	private void creaCriterio(Criteria criteria, CuentaEfectivoIntDTO dto){
		if(dto.getBicCodes() != null && dto.getBicCodes().size() > 0 && !dto.getBicCodes().contains("TODOS")){
			criteria.add(Restrictions.in("bicCode", dto.getBicCodes()));
		}
		
		if(dto.getDivisas() != null && dto.getDivisas().size() > 0){
			criteria.add(Restrictions.in("divisa.idDivisa", dto.getDivisas()));
		}
		
		if(dto.getCuentas() != null && dto.getCuentas().size() > 0 && !dto.getCuentas().contains("TODAS")){
			criteria.add(Restrictions.in("cuenta", dto.getCuentas()));
		}
		
		if(dto.getTipoCuenta() > -1){
			criteria.add(Restrictions.eq("tipoCuenta", dto.getTipoCuenta()));
		}
		
		if(dto.getCuentaNegocio() != null && !dto.getCuentaNegocio().equals("TODAS")){
			criteria.add(Restrictions.eq("cuentaNegocio", dto.getCuentaNegocio()));
		}
	}

	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.persistence.dao.ConciliacionEfectivoDao#consultaPaises()
	 */
	@SuppressWarnings("unchecked")
	public List<PaisInt> consultaPaises() {
		return (List<PaisInt>)
				this.getHibernateTemplate().execute(new HibernateCallback() {
		        	public List<PaisInt> doInHibernate(Session session) throws HibernateException, SQLException {
		        		Criteria criteria = session.createCriteria(PaisInt.class);
		        		
		        		return criteria.list();
		        	}
				});
	}

	/* (non-Javadoc)
	 * @see com.indeval.portalinternacional.persistence.dao.ConciliacionEfectivoDao#existeCuenta(java.lang.String, com.indeval.portaldali.persistence.modelo.Divisa, java.lang.String)
	 */
	public boolean existeCuenta(final String bicCode, final Long idDivisa, final String cuenta, final Long idCuenta) {
		CuentaEfectivoInt cei = (CuentaEfectivoInt)
				this.getHibernateTemplate().execute(new HibernateCallback() {
		        	public CuentaEfectivoInt doInHibernate(Session session) throws HibernateException, SQLException {
		        		Criteria criteria = session.createCriteria(CuentaEfectivoInt.class);
		        		criteria.add(Restrictions.eq("bicCode", bicCode));
		        		criteria.add(Restrictions.eq("divisa.idDivisa", idDivisa.longValue()));
		        		criteria.add(Restrictions.eq("cuenta", cuenta));
		        		
		        		if(idCuenta.longValue() > 0){
		        			criteria.add(Restrictions.ne("idCuenta", idCuenta));
		        		}
		        		
		        		return (CuentaEfectivoInt)criteria.uniqueResult();
		        	}
				});
		
		if(cei != null)
			return true;
		else
			return false;
	}

	/*
	 * (non-Javadoc)
	 * @see com.indeval.portalinternacional.persistence.dao.ConciliacionEfectivoDao#existeCuenta950(java.lang.String, java.lang.Long, java.lang.String, java.lang.Long)
	 */
	@Override
	public boolean existeCuenta950(final String bicCode, final Long idDivisa, final String cuenta950, final Long idCuenta) {
		CuentaEfectivoInt cei = (CuentaEfectivoInt)
				this.getHibernateTemplate().execute(new HibernateCallback() {
		        	public CuentaEfectivoInt doInHibernate(Session session) throws HibernateException, SQLException {
		        		Criteria criteria = session.createCriteria(CuentaEfectivoInt.class);
		        		criteria.add(Restrictions.eq("bicCode", bicCode));
		        		criteria.add(Restrictions.eq("divisa.idDivisa", idDivisa.longValue()));
		        		criteria.add(Restrictions.eq("cuenta950", cuenta950));
		        		
		        		if(idCuenta.longValue() > 0){
		        			criteria.add(Restrictions.ne("idCuenta", idCuenta));
		        		}
		        		
		        		return (CuentaEfectivoInt)criteria.uniqueResult();
		        	}
				});
		
		if(cei != null)
			return true;
		else
			return false;
	}

	public BigDecimal consultaSaldoInicialCuenta(final Set<String> bicCodes, final String divisa, final Set<String> cuentas) {
		return (BigDecimal)this.getHibernateTemplate().execute(new HibernateCallback() {
			public BigDecimal doInHibernate(Session session) throws HibernateException, SQLException {
        		Criteria criteria = session.createCriteria(CuentaEfectivoInt.class);
        		criteria.createAlias("divisa", "d");
        		criteria.setProjection(Projections.sum("saldo"));
        		
        		if(bicCodes != null && bicCodes.size() > 0 && !bicCodes.contains("TODOS")){
        			criteria.add(Restrictions.in("bicCode", bicCodes));
        		}
        		
        		criteria.add(Restrictions.eq("d.claveAlfabetica", divisa));
        		
        		if(cuentas != null && cuentas.size() > 0 && !cuentas.contains("TODAS")){
        			criteria.add(Restrictions.in("cuenta", cuentas));
        		}
        		
        		return (BigDecimal)criteria.uniqueResult();
			}
		});
	}

	public BigDecimal consultaSaldoAcumuladoHistorico(final Set<String> bicCodes, final String divisa, final Set<String> cuentas, final Date fecha) {
		return (BigDecimal)this.getHibernateTemplate().execute(new HibernateCallback() {
			public BigDecimal doInHibernate(Session session) throws HibernateException, SQLException {
        		Criteria criteria = session.createCriteria(HistoricoMovimientoEfe.class);
        		criteria.createAlias("divisa", "d");
        		criteria.setProjection(Projections.sum("saldo"));
        		
        		if(bicCodes != null && bicCodes.size() > 0 && !bicCodes.contains("TODOS")){
        			criteria.add(Restrictions.in("bicCode", bicCodes));
        		}
        		
        		criteria.add(Restrictions.eq("d.claveAlfabetica", divisa));
        		
        		if(cuentas != null && cuentas.size() > 0 && !cuentas.contains("TODAS")){
        			criteria.add(Restrictions.in("cuenta", cuentas));
        		}
        		
        		criteria.add(Restrictions.between("fecha", DateUtils.preparaFechaConExtremoEnSegundos(fecha, true), DateUtils.preparaFechaConExtremoEnSegundos(fecha, false)));
        		
        		return (BigDecimal)criteria.uniqueResult();
			}
		});
	}

	public BigDecimal consultaSaldoMensaje(final Set<String> bicCodes, final String divisa, final Set<String> cuentas, final Date fecha, final String balance) {
		
		return (BigDecimal)this.getHibernateTemplate().execute(new HibernateCallback() {
			public BigDecimal doInHibernate(Session session) throws HibernateException, SQLException {
        		Criteria criteria = session.createCriteria(ConciliacionEfectivoInt.class);
        		criteria.setProjection(Projections.sum(balance));
        		
        		if(bicCodes != null && bicCodes.size() > 0 && !bicCodes.contains("TODOS")){
        			criteria.add(Restrictions.in("bicCode", bicCodes));
        		}
        		
        		criteria.add(Restrictions.eq("divisa", divisa));
        		
        		if(cuentas != null && cuentas.size() > 0 && !cuentas.contains("TODAS")){
        			criteria.add(Restrictions.in("cuenta", cuentas));
        		}
        		
        		criteria.add(Restrictions.between("fechaBalance", DateUtils.preparaFechaConExtremoEnSegundos(fecha, true), DateUtils.preparaFechaConExtremoEnSegundos(fecha, false)));
        		
        		return (BigDecimal)criteria.uniqueResult();
			}
		});
	}
	
	public List<Boveda> consultaBovedasEfectivo() {		
		final StringBuilder sb = new StringBuilder();		
		sb.append("FROM " + Boveda.class.getName() + " bov ");
		sb.append("where bov.tipoBoveda.idTipoBoveda in (0, 2)");
		
		@SuppressWarnings("unchecked")
		List<Boveda> retorno = (List<Boveda>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sb.toString());	
				return query.list();
			}
		});
		return retorno;
	}

}