package com.indeval.portalinternacional.persistence.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.middleware.servicios.util.UtilsVO;
import com.indeval.portaldali.persistence.util.DateUtils;
import com.indeval.portalinternacional.middleware.servicios.modelo.FileTransferDivisas;
import com.indeval.portalinternacional.middleware.servicios.vo.BitacoraFileTransferVO;
import com.indeval.portalinternacional.persistence.dao.BitacoraFileTransferDao;

public class BitacoraFileTransferDaoImpl extends BaseDaoHibernateImpl implements BitacoraFileTransferDao {

	/** Log de clase. */
	private static final Logger log = LoggerFactory.getLogger(BitacoraFileTransferDaoImpl.class);

	@Override
	public PaginaVO findFileTransfer(BitacoraFileTransferVO parametros, PaginaVO paginaVO,
			Boolean obtenerTotalRegistros) {

		log.info("Entrando a findFileTransfer()");

		if (parametros == null) {
			throw new IllegalArgumentException("No existen parametros para la consulta");
		}

		paginaVO = UtilsVO.getPaginaNotBlank(paginaVO);

		DetachedCriteria detachedCriteria = null;

		if (obtenerTotalRegistros) {

			detachedCriteria = this.obtieneCriteriaFindFileTransfer(parametros);
			detachedCriteria.setProjection(Projections.rowCount());
			final List listaCount = this.getHibernateTemplate().findByCriteria(detachedCriteria);

			if (listaCount == null || listaCount.isEmpty()) {
				throw new IllegalArgumentException("Error al tratar de obtener el total de registros");
			}
			
			paginaVO.setTotalRegistros((Integer) listaCount.get(0));
			
		}

		boolean continuarConsulta = !obtenerTotalRegistros 
				|| (paginaVO.getTotalRegistros() != null && paginaVO.getTotalRegistros().intValue() > 0);

		if (continuarConsulta) {

			detachedCriteria = this.obtieneCriteriaFindFileTransfer(parametros);
			detachedCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			detachedCriteria.addOrder(Order.desc("fechaRegistro"));
			paginaVO.setRegistros(this.getHibernateTemplate().findByCriteria(detachedCriteria, paginaVO.getOffset(), 
					paginaVO.getRegistrosXPag()));

		}

		return paginaVO;

	}

	private DetachedCriteria obtieneCriteriaFindFileTransfer(final BitacoraFileTransferVO parametros) {

		log.info("Entrando a obtieneCriteriaFindFileTransferView()");
		
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(FileTransferDivisas.class);
		
		if (parametros.getUsRegistro() != null && !parametros.getUsRegistro().isEmpty()) {
			
	        detachedCriteria.add(Restrictions.eq("usuarioRegistro", parametros.getUsRegistro()));
	        
	    }

		if (parametros.getFechaProcesamiento() != null && parametros.getFechaProcesamiento()[0] != null 
				&& parametros.getFechaProcesamiento()[1] != null) {
			
			final Date[] fechas = DateUtils.preparaIntervaloFechas(parametros.getFechaProcesamiento()[0], 
					parametros.getFechaProcesamiento()[1]);
			
			if (fechas == null || fechas.length != 2 || fechas[0] == null || fechas[1] == null) {
				throw new IllegalArgumentException("Error al intentar obtener el rango de fechas");
			}
			
			detachedCriteria.add(Restrictions.between("fechaRegistro", fechas[0], fechas[1]));
			
		}

		return detachedCriteria;
		
	}

}
