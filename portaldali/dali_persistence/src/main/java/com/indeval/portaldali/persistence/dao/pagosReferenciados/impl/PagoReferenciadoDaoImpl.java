/**
 * CMMV - Portal Dali
 * Copyrigth (c) 2017 CMMV. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.dao.pagosReferenciados.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portaldali.middleware.constantes.ValidacionConstantes;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.modelo.PaginaVO;
import com.indeval.portaldali.modelo.to.pagosReferenciados.ParamConsultaBitacoraPagoReferenciado;
import com.indeval.portaldali.persistence.dao.pagosReferenciados.PagoReferenciadoDao;
import com.indeval.portaldali.persistence.util.DateUtil;
import com.indeval.portaldali.persistencia.pagosReferenciados.BitacoraPagosReferenciados;

/**
 * Implementacion de la interfz de datos PagoReferenciadoDao.
 * 
 * @author Pablo Balderas
 */
public class PagoReferenciadoDaoImpl extends BaseDaoHibernateImpl implements PagoReferenciadoDao {

	/*
	 * (non-Javadoc)
	 * @see com.indeval.portaldali.persistence.dao.pagosReferenciados.PagoReferenciadoDao#findBitacoraPagosReferenciados(com.indeval.portaldali.middleware.services.modelo.PaginaVO, com.indeval.portaldali.modelo.to.pagosReferenciados.ParamConsultaBitacoraPagoReferenciado)
	 */
	@SuppressWarnings("all")
	public PaginaVO findBitacoraPagosReferenciados(boolean esExportacion, PaginaVO paginaVO, 
		final ParamConsultaBitacoraPagoReferenciado parametrosBusqueda) throws BusinessException {
		try {
	        if (paginaVO == null) {
	        	paginaVO = new PaginaVO();
	        }
	        final Integer offset = paginaVO.getOffset() != null ? paginaVO.getOffset() : null;
	        final Integer regxpag = paginaVO.getRegistrosXPag() != null ? paginaVO.getRegistrosXPag() : null;
	        HibernateCallback hibernateCallback = new HibernateCallback() {
	            public Object doInHibernate(final Session session) throws HibernateException, SQLException {
	                Criteria crit = crearCriterioBusqueda(parametrosBusqueda).getExecutableCriteria(session);
	                if (offset != null && regxpag != null && regxpag != PaginaVO.TODOS) {
	                    crit.setMaxResults(regxpag);
	                    crit.setFetchSize(regxpag);
	                    crit.setFirstResult(offset);
	                }
	                return crit.list();
	            }
	        };
			List<BitacoraPagosReferenciados> resultados = (List<BitacoraPagosReferenciados>)getHibernateTemplate().executeFind(hibernateCallback);
			
	        //Si es una consulta, calcula el número total de registros.
	        if(!esExportacion) {        	
	        	final DetachedCriteria criteriaSum = crearCriterioBusqueda(parametrosBusqueda);
	        	Integer tam = (Integer) this.getHibernateTemplate().execute(new HibernateCallback() {
	        		public Object doInHibernate(final Session session) throws HibernateException, SQLException {
	        			criteriaSum.setProjection(Projections.rowCount());
	        			Criteria crit = criteriaSum.getExecutableCriteria(session);
	        			return crit.uniqueResult();
	        		}
	        	});
	        	paginaVO.setTotalRegistros(tam);
	        }
			paginaVO.setRegistros(resultados);
	        return paginaVO;
		}
		catch(Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	
	/**
	 * Metodo que construye el criterio de busqueda en base al TO de entrada.
	 * @param parametrosBusqueda TO con los parametros de la consulta
	 * @return Criterio para realizar la consulta.
	 */
	private DetachedCriteria crearCriterioBusqueda(ParamConsultaBitacoraPagoReferenciado parametrosBusqueda) {
		DetachedCriteria criteria = DetachedCriteria.forClass(BitacoraPagosReferenciados.class);
		if(parametrosBusqueda != null) {
			if(StringUtils.isNotBlank(parametrosBusqueda.getFolioPreliquidador())) {
				criteria.add(Restrictions.eq("folioPreliquidador", parametrosBusqueda.getFolioPreliquidador()));
			}
			if(StringUtils.isNotBlank(parametrosBusqueda.getFolioInstruccion())) {
				criteria.add(Restrictions.eq("folioInstruccion", parametrosBusqueda.getFolioInstruccion()));
			}
			if(StringUtils.isNotBlank(parametrosBusqueda.getImporte()) && 
					parametrosBusqueda.getImporte().matches(ValidacionConstantes.ER_NUMERO_ENTERO_DECIMAL)) {
				criteria.add(Restrictions.eq("importe", new BigDecimal(parametrosBusqueda.getImporte())));
			}
			if(StringUtils.isNotBlank(parametrosBusqueda.getClaveRastreo())) {
				criteria.add(Restrictions.eq("claveRastreo", parametrosBusqueda.getClaveRastreo()));
			}
	        if (parametrosBusqueda.getFechaRegistroInicial() != null
	                && parametrosBusqueda.getFechaRegistroFinal() != null) {
	            criteria.add(Restrictions.between("fechaRegistro",
	            	DateUtil.preparaFechaConExtremoEnSegundos(parametrosBusqueda.getFechaRegistroInicial(), Boolean.TRUE),
	            	DateUtil.preparaFechaConExtremoEnSegundos(parametrosBusqueda.getFechaRegistroFinal(), Boolean.FALSE)));
	        }
	        else if (parametrosBusqueda.getFechaRegistroFinal() != null) {
	            criteria.add(Restrictions.le("fechaRegistro",
	            	DateUtil.preparaFechaConExtremoEnSegundos(parametrosBusqueda.getFechaRegistroFinal(), Boolean.TRUE)));
	        }
	        else if (parametrosBusqueda.getFechaRegistroInicial() != null) {
	            criteria.add(Restrictions.ge("fechaRegistro",
	            	DateUtil.preparaFechaConExtremoEnSegundos(parametrosBusqueda.getFechaRegistroInicial(), Boolean.FALSE)));
	        }
			if(StringUtils.isNotBlank(parametrosBusqueda.getEstatus())) {
				criteria.add(Restrictions.eq("estatus", parametrosBusqueda.getEstatus()));
			}
		}
		return criteria;
	}
	
	

}
