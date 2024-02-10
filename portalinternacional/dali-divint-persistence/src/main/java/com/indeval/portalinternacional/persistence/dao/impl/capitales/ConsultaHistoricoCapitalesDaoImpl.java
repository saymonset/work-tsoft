/**
 * Bursatec - Portal Internacional
 * Copyrigth (c) 2016 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.persistence.dao.impl.capitales;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.servicios.modelo.capitales.DerechoCapitalHistorico;
import com.indeval.portalinternacional.middleware.servicios.modelo.capitales.DerechoCapitalHistoricoCuenta;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaDerechosBeneficiarioTO;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaDetalleEjerDerCapCuentaTO;
import com.indeval.portalinternacional.middleware.servicios.vo.ParamConsultaDetalleEjerDerCapTO;
import com.indeval.portalinternacional.middleware.servicios.vo.TotalesPorcentajeRetencionTO;
import com.indeval.portalinternacional.persistence.dao.capitales.ConsultaHistoricoCapitalesDao;
import com.indeval.portalinternacional.persistence.util.ConsultaDerechosBeneficiariosUtil;
import com.indeval.portalinternacional.persistence.util.ConsultaHistoricoCapitalesCuentaUtil;

/**
 * Implementación de la interfaz ConsultaHistoricoCapitalesDao.
 * @author lmunoz
 */
public class ConsultaHistoricoCapitalesDaoImpl extends BaseDaoHibernateImpl implements ConsultaHistoricoCapitalesDao {

	/*
	 * (non-Javadoc)
	 * @see com.indeval.portalinternacional.persistence.dao.capitales.ConsultaHistoricoCapitalesDao#consultaHistoricoCapitales(com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO, com.indeval.portalinternacional.middleware.servicios.vo.ParamConsultaDetalleEjerDerCapTO, boolean)
	 */
	@SuppressWarnings("all")
    public PaginaVO consultaHistoricoCapitales(PaginaVO pagina, final ParamConsultaDetalleEjerDerCapTO params, boolean esExportacion)
    		throws BusinessException {
    	ConsultaDerechosBeneficiarioTO resultadosConsulta = new ConsultaDerechosBeneficiarioTO();
        if (pagina == null) {
            pagina = new PaginaVO();
        }
        final Integer offset = pagina.getOffset() != null ? pagina.getOffset() : null;
        final Integer regxpag = pagina.getRegistrosXPag() != null ? pagina.getRegistrosXPag() : null;
        //Realiza un count distinc para saber el número de divisas en el universo de la consulta
        HibernateCallback ejecucionDivisas = new HibernateCallback() {
            public Object doInHibernate(final Session session) throws HibernateException, SQLException {
                Criteria crit = ConsultaDerechosBeneficiariosUtil.paramsConsultaCapitales(params, false).getExecutableCriteria(session);
                ProjectionList proList = Projections.projectionList();
                proList.add(Projections.countDistinct("divisa"));
                crit.setProjection(proList);
                return crit.uniqueResult();
            }
        };
        final Integer numeroDivisas = (Integer) this.getHibernateTemplate().execute(ejecucionDivisas);
        //Realiza la consulta
        HibernateCallback hibernateCallback = new HibernateCallback() {
            public Object doInHibernate(final Session session) throws HibernateException, SQLException {
                Criteria crit = ConsultaDerechosBeneficiariosUtil.paramsConsultaCapitales(params, true).getExecutableCriteria(session);
                if (offset != null && regxpag != null && regxpag != PaginaVO.TODOS) {
                    crit.setMaxResults(regxpag);
                    crit.setFetchSize(regxpag);
                    crit.setFirstResult(offset);
                }
                return crit.list();
            }
        };
        List<DerechoCapitalHistorico> derechos = getHibernateTemplate().executeFind(hibernateCallback);
        if (derechos != null) {
        	resultadosConsulta.setRegistrosConsulta(derechos);
        	for (DerechoCapitalHistorico derechoCapitalHistorico : derechos) {				
        		if(!esExportacion) {
        			resultadosConsulta.setSubtotalAsignacion(
        				sumaLong(resultadosConsulta.getSubtotalAsignacion(), derechoCapitalHistorico.getAsignacion()));
        			if(numeroDivisas == 1) {        				
        				resultadosConsulta.setSubtotalMontoBruto(
        					sumaDouble(resultadosConsulta.getSubtotalMontoBruto(), derechoCapitalHistorico.getMontoBruto()));
        				resultadosConsulta.setSubtotalMontoFee(
        					sumaDouble(resultadosConsulta.getSubtotalMontoFee(), derechoCapitalHistorico.getMontoFee()));
        				resultadosConsulta.setSubtotalImpuestoRetenido(
        					sumaDouble(resultadosConsulta.getSubtotalImpuestoRetenido(), derechoCapitalHistorico.getImpuestoRetenido()));
        				resultadosConsulta.setSubtotalMontoNeto(
        					sumaDouble(resultadosConsulta.getSubtotalMontoNeto(), derechoCapitalHistorico.getMontoNeto()));
        			}
        		}
			}
        }
        //Realiza el calculo de los totales
        HibernateCallback ejecucionTotales = new HibernateCallback() {
            public Object doInHibernate(final Session session) throws HibernateException, SQLException {
                Criteria crit = ConsultaDerechosBeneficiariosUtil.paramsConsultaCapitales(params, false).getExecutableCriteria(session);
                ProjectionList proList = Projections.projectionList();
                proList.add(Projections.sum("asignacion"));
                if(numeroDivisas == 1) {
                	proList.add(Projections.sum("montoBruto"));
                	proList.add(Projections.sum("montoFee"));
                	proList.add(Projections.sum("impuestoRetenido"));
                	proList.add(Projections.sum("montoNeto"));
                }
                crit.setProjection(proList);
                return crit.list();
            }
        };
        List<Object> listaTotales = this.getHibernateTemplate().executeFind(ejecucionTotales);
        if(listaTotales != null && !listaTotales.isEmpty()) {
        	if(numeroDivisas == 1) {       		
        		Object[] arregloTotales = (Object[]) listaTotales.get(0);
        		resultadosConsulta.setTotalAsignacion((Long) arregloTotales[0]);
        		resultadosConsulta.setTotalMontoBruto((Double) arregloTotales[1]);
        		resultadosConsulta.setTotalMontoFee((Double) arregloTotales[2]);
        		resultadosConsulta.setTotalImpuestoRetenido((Double) arregloTotales[3]);
        		resultadosConsulta.setTotalMontoNeto((Double) arregloTotales[4]);
        	}
        	else {
        		resultadosConsulta.setTotalAsignacion((Long) listaTotales.get(0));
        	}
        }
        //Si es una consulta, calcula el número total de registros.
        if(!esExportacion) {        	
        	final DetachedCriteria criteriaSum = ConsultaDerechosBeneficiariosUtil.paramsConsultaCapitales(params, false);
        	Integer tam = (Integer) this.getHibernateTemplate().execute(new HibernateCallback() {
        		public Object doInHibernate(final Session session) throws HibernateException, SQLException {
        			criteriaSum.setProjection(Projections.rowCount());
        			Criteria crit = criteriaSum.getExecutableCriteria(session);
        			return crit.uniqueResult();
        		}
        	});
        	pagina.setTotalRegistros(tam);
        }
        //Prepara la respuesta
        List<ConsultaDerechosBeneficiarioTO> listaResultado = new ArrayList<ConsultaDerechosBeneficiarioTO>();
        listaResultado.add(resultadosConsulta);
        pagina.setRegistros(listaResultado);
        return pagina;
    }

	/*
     * (non-Javadoc)
     * @see com.indeval.portalinternacional.persistence.dao.capitales.ConsultaHistoricoCapitalesDao#consultaHistoricoCapitalesCuenta(com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO, com.indeval.portalinternacional.middleware.servicios.vo.ParamConsultaDetalleEjerDerCapTO, boolean)
     */
    @SuppressWarnings("all")
    public PaginaVO consultaHistoricoCapitalesCuenta(PaginaVO pagina, final ParamConsultaDetalleEjerDerCapTO params, boolean esExportacion)
    		throws BusinessException {
    	ConsultaDetalleEjerDerCapCuentaTO resultados = new ConsultaDetalleEjerDerCapCuentaTO();
        if (pagina == null) {
            pagina = new PaginaVO();
        }
        //Utileria para obtener los diferentes querys a ejecutar.
        final ConsultaHistoricoCapitalesCuentaUtil consultaHistoricoCapitalesCuentaUtil = 
        	new ConsultaHistoricoCapitalesCuentaUtil(params);
        final Integer offset = pagina.getOffset() != null ? pagina.getOffset() : null;
        final Integer regxpag = pagina.getRegistrosXPag() != null ? pagina.getRegistrosXPag() : null;
        //Realiza la consulta de derechos por cuenta
        HibernateCallback callbackConsultaDerechosCuenta = new HibernateCallback() {
            public Object doInHibernate(final Session session) throws HibernateException, SQLException {            	
            	SQLQuery query = consultaHistoricoCapitalesCuentaUtil.crearSQLQueryRegistros(session);
                if (offset != null && regxpag != null && regxpag != PaginaVO.TODOS) {
                	query.setMaxResults(regxpag);
                	query.setFetchSize(regxpag);
                	query.setFirstResult(offset);
                }
                return query.list();
            }
        };
        List<DerechoCapitalHistoricoCuenta> derechos = this.getHibernateTemplate().executeFind(callbackConsultaDerechosCuenta);
        //Si la consulta arrojo resultados, sigue con la ejecución
        if(derechos != null && !derechos.isEmpty()) {
        	resultados.setResultadosConsulta(derechos);
            //Realiza un count distinc para saber el número de divisas en el universo de la consulta
            HibernateCallback callbackCountDivisas = new HibernateCallback() {
                public Object doInHibernate(final Session session) throws HibernateException, SQLException {
                    SQLQuery query = consultaHistoricoCapitalesCuentaUtil.crearSQLQueryCuentaDivisas(session);
                    return query.uniqueResult();
                }
            };
            consultaHistoricoCapitalesCuentaUtil.setNumeroDivisas((Integer) this.getHibernateTemplate().execute(callbackCountDivisas));
        	if(!esExportacion) {
        		//Calcula los subtotales de la página
        		for (DerechoCapitalHistoricoCuenta derechoCapitalHistoricoCuenta : derechos) {
        			resultados.setSubtotalAsignacion(
        				sumaLong(resultados.getSubtotalAsignacion(), derechoCapitalHistoricoCuenta.getAsignacion()));
        			if(consultaHistoricoCapitalesCuentaUtil.isCalcularTotales()) {
        				resultados.setSubtotalMontoBruto(
        					sumaDouble(resultados.getSubtotalMontoBruto(), derechoCapitalHistoricoCuenta.getMontoBruto()));
        				resultados.setSubtotalMontoFee(
        					sumaDouble(resultados.getSubtotalMontoFee(), derechoCapitalHistoricoCuenta.getMontoFee()));
        				resultados.setSubtotalImpuestoRetenido(
        					sumaDouble(resultados.getSubtotalImpuestoRetenido(), derechoCapitalHistoricoCuenta.getImpuestoRetenido()));
        				resultados.setSubtotalMontoNeto(
        					sumaDouble(resultados.getSubtotalMontoNeto(), derechoCapitalHistoricoCuenta.getMontoNeto()));
        			}
        		}
        		//Calcula el número total de registros
        		Integer tam = (Integer) this.getHibernateTemplate().execute(new HibernateCallback() {
        			public Object doInHibernate(final Session session) throws HibernateException, SQLException {
        				SQLQuery query = consultaHistoricoCapitalesCuentaUtil.crearSQLQueryTotalRegistros(session);
        				return query.uniqueResult();
        			}
        		});
        		pagina.setTotalRegistros(tam);
        	}
        	//Calcula los totales
        	List<Object> listaTotales = (List<Object>) this.getHibernateTemplate().execute(new HibernateCallback() {
        		public Object doInHibernate(final Session session) throws HibernateException, SQLException {
        			SQLQuery query = consultaHistoricoCapitalesCuentaUtil.crearSQLQueryTotales(session);
        			return query.list();
        		}
        	});
            if(listaTotales != null && !listaTotales.isEmpty()) {
            	if(consultaHistoricoCapitalesCuentaUtil.isCalcularTotales()) {       		
            		Object[] arregloTotales = (Object[]) listaTotales.get(0);
            		resultados.setTotalAsignacion((Long) arregloTotales[0]);
            		resultados.setTotalMontoBruto((Double) arregloTotales[1]);
            		resultados.setTotalMontoFee((Double) arregloTotales[2]);
            		resultados.setTotalImpuestoRetenido((Double) arregloTotales[3]);
            		resultados.setTotalMontoNeto((Double) arregloTotales[4]);
            	}
            	else {
            		resultados.setTotalAsignacion((Long) listaTotales.get(0));
            	}
            }
        	//Calcula los totales por porcentaje de retencion
        	HibernateCallback callbackTotalesPorcentajeRetencion = new HibernateCallback() {
        		public Object doInHibernate(final Session session) throws HibernateException, SQLException {            	
        			SQLQuery query = 
        				consultaHistoricoCapitalesCuentaUtil.crearSQLQueryTotalesPorcentajeRetencion(session);
        			return query.list();
        		}
        	};
        	resultados.setTotalesPorcentajeRetencion(
    			(List<TotalesPorcentajeRetencionTO>) 
    				getHibernateTemplate().executeFind(callbackTotalesPorcentajeRetencion));
        }
        //Prepara la respuesta
        List<ConsultaDetalleEjerDerCapCuentaTO> listaResultado = new ArrayList<ConsultaDetalleEjerDerCapCuentaTO>();
        listaResultado.add(resultados);
        pagina.setRegistros(listaResultado);
        return pagina;
    }
    
	/**
	 * Realiza la suma de dos objetos Long
	 * @param sumando1 Primer sumando
	 * @param sumando2 Segundo sumando
	 * @return Long con la suma.
	 */
	private Long sumaLong(Long sumando1, Long sumando2) {
		return (sumando1 != null ? sumando1 : 0) + (sumando2 != null ? sumando2 : 0);
	}
	
	/**
	 * Realiza la suma de dos objetos Double
	 * @param sumando1 Primer sumando
	 * @param sumando2 Segundo sumando
	 * @return Long con la suma.
	 */
	private Double sumaDouble(Double sumando1, Double sumando2) {
		return (sumando1 != null ? sumando1 : 0) + (sumando2 != null ? sumando2 : 0);
	}
}
