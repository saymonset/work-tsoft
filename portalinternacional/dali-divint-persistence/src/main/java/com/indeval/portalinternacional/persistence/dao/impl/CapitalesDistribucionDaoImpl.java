package com.indeval.portalinternacional.persistence.dao.impl;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.modelo.Boveda;
import com.indeval.portaldali.persistence.util.DateUtils;
import com.indeval.portalinternacional.middleware.servicios.modelo.BitacoraMensajeSwift;
import com.indeval.portalinternacional.middleware.servicios.modelo.DestinatarioIntSic;
import com.indeval.portalinternacional.middleware.servicios.modelo.EnvioLegislacionSic;
import com.indeval.portalinternacional.middleware.servicios.modelo.EstadoMensajeIntSic;
import com.indeval.portalinternacional.middleware.servicios.modelo.capitales.AssetManagerEmision;
import com.indeval.portalinternacional.middleware.servicios.modelo.capitales.CalendarioDerechosCapitales;
import com.indeval.portalinternacional.middleware.servicios.modelo.capitales.CalendarioHistoricoDerechosCapitales;
import com.indeval.portalinternacional.middleware.servicios.modelo.capitales.CalendarioHistoricoDerechosCapitalesVo;
import com.indeval.portalinternacional.middleware.servicios.modelo.capitales.DescFechasAdIntSic;
import com.indeval.portalinternacional.middleware.servicios.modelo.capitales.EmisionAssetManagerVO;
import com.indeval.portalinternacional.middleware.servicios.modelo.capitales.EnvioHistIntSicView;
import com.indeval.portalinternacional.middleware.servicios.modelo.capitales.EnvioLegislacionSicDTO;
import com.indeval.portalinternacional.middleware.servicios.modelo.capitales.InstruccionCapitalesVO;
import com.indeval.portalinternacional.middleware.servicios.modelo.capitales.NarrativaCapitales;
import com.indeval.portalinternacional.middleware.servicios.modelo.capitales.NarrativaCapitalesVO;
import com.indeval.portalinternacional.middleware.servicios.modelo.capitales.SubTipoDerechoIntSic;
import com.indeval.portalinternacional.middleware.servicios.modelo.capitales.VCalendarioDerechoInt;
import com.indeval.portalinternacional.middleware.servicios.modelo.capitales.VCalendarioDerechoIntRep;
import com.indeval.portalinternacional.middleware.servicios.modelo.capitales.VCalendarioHistoricoDerechosCapitales;
import com.indeval.portalinternacional.middleware.servicios.modelo.capitales.VCalendarioHistoricoDerechosCapitalesRep;
import com.indeval.portalinternacional.middleware.servicios.modelo.capitales.VCalendarioHistoricoDerechosExterno;
import com.indeval.portalinternacional.middleware.servicios.modelo.capitales.VEmisionesCalendario;
import com.indeval.portalinternacional.middleware.servicios.modelo.capitales.VEnvioIntSic;
import com.indeval.portalinternacional.middleware.servicios.modelo.capitales.VHistoricoNarrativas;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaCapitalesParamsTO;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaEmisionesCalendarioParamsTO;
import com.indeval.portalinternacional.middleware.servicios.vo.EnvioCapitalesParamsTO;
import com.indeval.portalinternacional.persistence.dao.capitales.CapitalesDistribucionDao;

/**
 * @author piso9
 *
 */
@SuppressWarnings({ "unchecked" })
public class CapitalesDistribucionDaoImpl extends BaseDaoHibernateImpl implements CapitalesDistribucionDao {

	/** Log de clase. */
	private static final Logger logger = LoggerFactory.getLogger(CapitalesDistribucionDaoImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portalinternacional.persistence.dao.capitales.
	 * CapitalesDistribucionDao#consultaCalendarioCapitales(com.indeval.
	 * portalinternacional.middleware.servicios.vo.ConsultaCapitalesParamsTO,
	 * com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO)
	 */
	public PaginaVO consultaCalendarioCapitales(ConsultaCapitalesParamsTO params, PaginaVO paginaVO) {
		if (paginaVO == null) {
			paginaVO = new PaginaVO();
		}
		boolean isReporte = false;
		final Integer offset = paginaVO.getOffset() != null ? paginaVO.getOffset() : null;
		final Integer regxpag = paginaVO.getRegistrosXPag() != null ? paginaVO.getRegistrosXPag() : null;

		if (regxpag == PaginaVO.TODOS) {
			isReporte = true;
		}
		final DetachedCriteria criteria = paramsConsultaCapitales(params, isReporte);

		HibernateCallback hibernateCallback = new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria crit = criteria.getExecutableCriteria(session);
				if (offset != null && regxpag != null && regxpag != PaginaVO.TODOS) {
					crit.setMaxResults(regxpag);
					crit.setFetchSize(regxpag);
					crit.setFirstResult(offset);
				}
				return crit.list();
			}
		};

		if (!isReporte) {
			paginaVO.setRegistros(
					(List<VCalendarioDerechoInt>) this.getHibernateTemplate().executeFind(hibernateCallback));
		} else {
			paginaVO.setRegistros(
					(List<VCalendarioDerechoIntRep>) this.getHibernateTemplate().executeFind(hibernateCallback));
		}

		final DetachedCriteria criteriaSum = paramsConsultaCapitales(params, isReporte);
		Integer tam = (Integer) this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				criteriaSum.setProjection(Projections.rowCount());
				Criteria crit = criteriaSum.getExecutableCriteria(session);
				return crit.uniqueResult();
			}

		});
		paginaVO.setTotalRegistros(tam);
		return paginaVO;
	}

	/**
	 * Genera Citeria para la consulta calendario de derechos
	 * 
	 * @param params
	 * @param isReporte
	 * @param origen
	 * @return
	 */
	private DetachedCriteria paramsConsultaCapitales(ConsultaCapitalesParamsTO params, boolean isReporte) {

		DetachedCriteria criteria = null;

		if (isReporte) {
			criteria = DetachedCriteria.forClass(VCalendarioDerechoIntRep.class);
		} else {
			criteria = DetachedCriteria.forClass(VCalendarioDerechoInt.class);
		}
		criteria.add(Restrictions.isNotNull("idCalendario"));
		criteria.addOrder(Order.desc("prioridad"));
		criteria.addOrder(Order.desc("idCalendario"));

		// tipoValor
		if (StringUtils.isNotEmpty(params.getTipoValor())) {
			criteria.add(Restrictions.eq("tipoValor", params.getTipoValor()).ignoreCase());

		} else {
			Criterion rest1 = Restrictions.in("tipoValor", params.getTipoValorPermitidos());
			Criterion rest2 = Restrictions.isNull(("tipoValor"));
			criteria.add(Restrictions.or(rest1, rest2));
		}
		// emisora
		if (StringUtils.isNotEmpty(params.getEmisora())) {
			criteria.add(Restrictions.eq("emisora", params.getEmisora()).ignoreCase());
		}
		// serie
		if (StringUtils.isNotEmpty(params.getSerie())) {
			criteria.add(Restrictions.eq("serie", params.getSerie()).ignoreCase());
		}
		// isin
		if (StringUtils.isNotEmpty(params.getIsin())) {
			criteria.add(Restrictions.eq("isin", params.getIsin()).ignoreCase());
		}
		// tipoMensaje //-1
		if (params.getTipoMensaje() != null && !params.getTipoMensaje().contentEquals("-1")) {
			criteria.add(Restrictions.eq("tipoMensaje", params.getTipoMensaje()));
		}

			if (params.getEstadoInt() != null) {
				if (params.getEstadoInt() == 8) {
					criteria.add(Restrictions.isNull("estadoMensajeInt"));
				} else {
					criteria.add(Restrictions.eq("estadoMensajeInt", params.getEstadoInt()));
				}
			}

			// estadoEmision --- Listada
			if (StringUtils.isNotEmpty(params.getEstadoEmision()) && !params.getEstadoEmision().contentEquals("-1")
					&& !params.getEstadoEmision().equals("DESLISTADA")) {

				criteria.add(Restrictions.eq("vEmisionesCalendario", params.getEstadoEmision()).ignoreCase());
			} else if (params.getEstadoEmision().equals("DESLISTADA")) {
				Criterion rest1 = Restrictions.eq("vEmisionesCalendario", params.getEstadoEmision()).ignoreCase();
				Criterion rest2 = Restrictions.isNull(("vEmisionesCalendario"));
				criteria.add(Restrictions.or(rest1, rest2));
			}
		

		// boveda //-1
		if (params.getBovedaInt() != null && params.getBovedaInt() > 0) {

			criteria.add(Restrictions.eq("idBoveda", params.getBovedaInt()));
		}

		// divisa //1
		if (StringUtils.isNotEmpty(params.getDivisa()) && !params.getDivisa().contentEquals("-1")) {
			criteria.add(Restrictions.eq("divisa", params.getDivisa()).ignoreCase());
		}
		// fechaCorte
		if (params.getFechaCorteInicio() != null && params.getFechaCorteFin() != null) {
			criteria.add(Restrictions.between("fechaCorte",
					DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaCorteInicio(), Boolean.TRUE),
					DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaCorteFin(), Boolean.FALSE)));
		} else if (params.getFechaCorteInicio() != null) {
			criteria.add(Restrictions.ge("fechaCorte",
					DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaCorteInicio(), Boolean.TRUE)));
		} else if (params.getFechaCorteFin() != null) {
			criteria.add(Restrictions.le("fechaCorte",
					DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaCorteFin(), Boolean.FALSE)));
		}
		// fechaPago
		if (params.getFechaPagoInicio() != null && params.getFechaPagoFin() != null) {
			criteria.add(Restrictions.between("fechaPago",
					DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaPagoInicio(), Boolean.TRUE),
					DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaPagoFin(), Boolean.FALSE)));
		} else if (params.getFechaPagoInicio() != null) {
			criteria.add(Restrictions.ge("fechaPago",
					DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaPagoInicio(), Boolean.TRUE)));
		} else if (params.getFechaPagoFin() != null) {
			criteria.add(Restrictions.le("fechaPago",
					DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaPagoFin(), Boolean.FALSE)));
		}

		// fecha EFFD
		if (params.getFechaEffdInicio() != null && params.getFechaEffdFin() != null) {
			criteria.add(Restrictions.between("fechaEfectiva",
					DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaEffdInicio(), Boolean.TRUE),
					DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaEffdFin(), Boolean.FALSE)));
		} else if (params.getFechaEffdInicio() != null) {
			criteria.add(Restrictions.ge("fechaEfectiva",
					DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaEffdInicio(), Boolean.TRUE)));
		} else if (params.getFechaEffdFin() != null) {
			criteria.add(Restrictions.le("fechaEfectiva",
					DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaEffdFin(), Boolean.FALSE)));
		}

		// fecha Envio
		if (params.getFechaPrimerEnvioInicio() != null && params.getFechaPrimerEnvioFin() != null) {
			criteria.add(Restrictions.between("fechaPrimerEnvio",
					DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaPrimerEnvioInicio(), Boolean.TRUE),
					DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaPrimerEnvioFin(), Boolean.FALSE)));
		} else if (params.getFechaPrimerEnvioInicio() != null) {
			criteria.add(Restrictions.ge("fechaPrimerEnvio",
					DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaPrimerEnvioInicio(), Boolean.TRUE)));
		} else if (params.getFechaPrimerEnvioFin() != null) {
			criteria.add(Restrictions.le("fechaPrimerEnvio",
					DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaPrimerEnvioFin(), Boolean.FALSE)));
		}

		// fecha Recepcion
		if (params.getFechaRecepcionInicio() != null && params.getFechaRecepcionFin() != null) {
			criteria.add(Restrictions.between("fechaHoraRecepcion", params.getFechaRecepcionInicio(),
					params.getFechaRecepcionFin()));
		} else if (params.getFechaRecepcionInicio() != null) {
			criteria.add(Restrictions.ge("fechaHoraRecepcion", params.getFechaRecepcionInicio()));
		} else if (params.getFechaRecepcionFin() != null) {
			criteria.add(Restrictions.le("fechaHoraRecepcion", params.getFechaRecepcionFin()));
		}

		// fechaExdate
		if (params.getFechaXdateInicio() != null && params.getFechaXdateFin() != null) {
			criteria.add(Restrictions.between("fechaXDate",
					DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaXdateInicio(), Boolean.TRUE),
					DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaXdateFin(), Boolean.FALSE)));
		} else if (params.getFechaXdateInicio() != null) {
			criteria.add(Restrictions.ge("fechaXDate",
					DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaXdateInicio(), Boolean.TRUE)));
		} else if (params.getFechaXdateFin() != null) {
			criteria.add(Restrictions.le("fechaXDate",
					DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaXdateFin(), Boolean.FALSE)));
		}
		// interimDate - Due Bill of Date
		if (params.getDueBillOfDateInicio() != null && params.getDueBillOfDateFin() != null) {
			criteria.add(Restrictions.between("fechaVencimiento",
					DateUtils.preparaFechaConExtremoEnSegundos(params.getDueBillOfDateInicio(), Boolean.TRUE),
					DateUtils.preparaFechaConExtremoEnSegundos(params.getDueBillOfDateFin(), Boolean.FALSE)));
		} else if (params.getDueBillOfDateInicio() != null) {
			criteria.add(Restrictions.ge("fechaVencimiento",
					DateUtils.preparaFechaConExtremoEnSegundos(params.getDueBillOfDateInicio(), Boolean.TRUE)));
		} else if (params.getDueBillOfDateFin() != null) {
			criteria.add(Restrictions.le("fechaVencimiento",
					DateUtils.preparaFechaConExtremoEnSegundos(params.getDueBillOfDateFin(), Boolean.FALSE)));
		}
		// fechasAdicionales
		if (StringUtils.isNotEmpty(params.getFechasAdicionales())
				&& !params.getFechasAdicionales().contentEquals("-1")) {
			if (params.getFechasAdicionales().contentEquals("0")) {
				criteria.add(Restrictions.isNotNull("fechasAdicionales"));
			} else {
				criteria.add(Restrictions.isNull("fechasAdicionales"));
			}

		}
		// fee
		if (StringUtils.isNotEmpty(params.getNett()) && !params.getNett().contentEquals("-1")) {
			if (params.getNett().contentEquals("0")) {
				criteria.add(Restrictions.isNotNull("importeNETT"));
			} else {
				criteria.add(Restrictions.isNull("importeNETT"));
			}
		}
		// folioIndeval
		if (params.getFolioIndeval() != null) {
			criteria.add(Restrictions.eq("idCalendario", params.getFolioIndeval()));
		}
		// corp
		if (StringUtils.isNotEmpty(params.getReferencia())) {
			criteria.add(Restrictions.eq("referencia", params.getReferencia()).ignoreCase());
		}
		// fuente/custodio
		if (params.getCustodioInt() != null) {

			criteria.add(Restrictions.eq("idCustodio", params.getCustodioInt()));
		}

		// CAEV //-1
		if (CollectionUtils.isNotEmpty(params.getIdsCaevs())) {
			Criterion rest = Restrictions.in("idTipoPagoCAEV", params.getIdsCaevs());
			criteria.add(rest);
		}
		// CAMV //-1
		if (StringUtils.isNotEmpty(params.getTipoPagoCAMV()) && !params.getTipoPagoCAMV().contentEquals("-1")) {
			Long idTipoPagoCAMV = Long.valueOf(params.getTipoPagoCAMV());

			criteria.add(Restrictions.eq("idTipoPagoCAMV", idTipoPagoCAMV));
		}
		// SubtipoDerecho
		if (StringUtils.isNotEmpty(params.getSubTipoDerecho()) && !params.getSubTipoDerecho().contentEquals("-1")) {
			criteria.add(Restrictions.like("subtipoDerecho", params.getSubTipoDerecho() + "%", MatchMode.ANYWHERE));
		}

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		return criteria;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portalinternacional.persistence.dao.capitales.
	 * CapitalesDistribucionDao#consultaHistoricoCapitales(com.indeval.
	 * portalinternacional.middleware.servicios.vo.ConsultaHistoricoParamsTO,
	 * com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO)
	 */
	public PaginaVO consultaHistoricoCapitales(ConsultaCapitalesParamsTO params, PaginaVO paginaVO) {
		if (paginaVO == null) {
			paginaVO = new PaginaVO();
		}
		boolean isReporte = false;
		final Integer offset = paginaVO.getOffset() != null ? paginaVO.getOffset() : null;
		final Integer regxpag = paginaVO.getRegistrosXPag() != null ? paginaVO.getRegistrosXPag() : null;

		if (regxpag == PaginaVO.TODOS) {
			isReporte = true;
		}

		final DetachedCriteria criteria = paramsConsultaHistoricoCapDistribucion(params, isReporte);

		HibernateCallback hibernateCallback = new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria crit = criteria.getExecutableCriteria(session);
				if (offset != null && regxpag != null && regxpag != PaginaVO.TODOS) {
					crit.setMaxResults(regxpag);
					crit.setFetchSize(regxpag);
					crit.setFirstResult(offset);
				}
				return crit.list();
			}
		};

		if (!isReporte) {
			paginaVO.setRegistros((List<VCalendarioHistoricoDerechosCapitales>) this.getHibernateTemplate()
					.executeFind(hibernateCallback));
		} else {
			paginaVO.setRegistros((List<VCalendarioHistoricoDerechosCapitalesRep>) this.getHibernateTemplate()
					.executeFind(hibernateCallback));
		}

		final DetachedCriteria criteriaSum = paramsConsultaHistoricoCapDistribucion(params, isReporte);
		Integer tam = (Integer) this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				criteriaSum.setProjection(Projections.rowCount());
				Criteria crit = criteriaSum.getExecutableCriteria(session);
				return crit.uniqueResult();
			}

		});
		paginaVO.setTotalRegistros(tam);
		return paginaVO;
	}

	/**
	 * Genera Citeria para la consulta Hist&oacute;rica de derechos
	 * 
	 * @param params
	 * @return
	 */
	private DetachedCriteria paramsConsultaHistoricoCapDistribucion(ConsultaCapitalesParamsTO params,
			boolean isReporte) {
		DetachedCriteria criteria = null;

		if (isReporte) {
			criteria = DetachedCriteria.forClass(VCalendarioHistoricoDerechosCapitalesRep.class);
		} else {
			criteria = DetachedCriteria.forClass(VCalendarioHistoricoDerechosCapitales.class);
		}
		criteria.add(Restrictions.isNotNull("id"));
		criteria.addOrder(Order.desc("id"));

		// Narrativa
		if (params.isConNarrativa()) {
			DetachedCriteria subQueryNarrativa = DetachedCriteria.forClass(VHistoricoNarrativas.class);
			subQueryNarrativa.add(Restrictions.between("fechaHoraRecepcion", params.getFechaRecepcionInicio(),
					params.getFechaRecepcionFin()));
			subQueryNarrativa
					.add(Restrictions.like("narrativa", params.getNarrativa() + "%", MatchMode.ANYWHERE).ignoreCase());
			subQueryNarrativa.setProjection(Projections.property("idHistorico"));
			criteria.add(Property.forName("id").in(subQueryNarrativa));
		} else {
			// fecha Recepcion
			if (params.getFechaRecepcionInicio() != null && params.getFechaRecepcionFin() != null) {
				criteria.add(Restrictions.between("fechaHoraRecepcion", params.getFechaRecepcionInicio(),
						params.getFechaRecepcionFin()));
			} else if (params.getFechaRecepcionInicio() != null) {
				criteria.add(Restrictions.ge("fechaHoraRecepcion", params.getFechaRecepcionInicio()));
			} else if (params.getFechaRecepcionFin() != null) {
				criteria.add(Restrictions.le("fechaHoraRecepcion", params.getFechaRecepcionFin()));
			}
		}

		// folioMensaje
		if (params.getFolioMensaje() != null) {
			criteria.add(Restrictions.eq("id", params.getFolioMensaje()));
		}

		// tipoValor
		if (StringUtils.isNotEmpty(params.getTipoValor())) {
			criteria.add(Restrictions.eq("tipoValor", params.getTipoValor()).ignoreCase());
		} else {
			Criterion rest1 = Restrictions.in("tipoValor", params.getTipoValorPermitidos());
			Criterion rest2 = Restrictions.isNull(("tipoValor"));
			criteria.add(Restrictions.or(rest1, rest2));
		}
		// emisora
		if (StringUtils.isNotEmpty(params.getEmisora())) {
			criteria.add(Restrictions.eq("emisora", params.getEmisora()).ignoreCase());
		}
		// serie
		if (StringUtils.isNotEmpty(params.getSerie())) {
			criteria.add(Restrictions.eq("serie", params.getSerie()).ignoreCase());
		}
		// isin
		if (StringUtils.isNotEmpty(params.getIsin())) {
			criteria.add(Restrictions.eq("isin", params.getIsin()).ignoreCase());
		}

		// tipoMensaje

		if (StringUtils.isNotEmpty(params.getTipoMensaje()) && !params.getTipoMensaje().contentEquals("-1")) {
			criteria.add(Restrictions.eq("tipoMensaje", params.getTipoMensaje()).ignoreCase());
		}

		// estado
		if (params.getEstadoInt() != null) {
			if (params.getEstadoInt() == 8) {
				criteria.add(Restrictions.isNull("idEstadoMensajeIntSic"));
			} else {
				criteria.add(Restrictions.eq("idEstadoMensajeIntSic", params.getEstadoInt()));
			}
		}

		// Boveda
		if (params.getBovedaInt() != null) {
			criteria.add(Restrictions.eq("idBoveda", params.getBovedaInt()));
		}

		// divisa //1
		if (StringUtils.isNotEmpty(params.getDivisa()) && !params.getDivisa().contentEquals("-1")) {
			criteria.add(Restrictions.eq("divisa", params.getDivisa()).ignoreCase());
		}

		// fechaExdate
		if (params.getFechaXdateInicio() != null && params.getFechaXdateFin() != null) {
			criteria.add(Restrictions.between("fechaXDate",
					DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaXdateInicio(), Boolean.TRUE),
					DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaXdateFin(), Boolean.FALSE)));
		} else if (params.getFechaXdateInicio() != null) {
			criteria.add(Restrictions.ge("fechaXDate",
					DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaXdateInicio(), Boolean.TRUE)));
		} else if (params.getFechaXdateFin() != null) {
			criteria.add(Restrictions.le("fechaXDate",
					DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaXdateFin(), Boolean.FALSE)));
		}

		// fechaCorte
		if (params.getFechaCorteInicio() != null && params.getFechaCorteFin() != null) {
			criteria.add(Restrictions.between("fechaCorte",
					DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaCorteInicio(), Boolean.TRUE),
					DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaCorteFin(), Boolean.FALSE)));
		} else if (params.getFechaCorteInicio() != null) {
			criteria.add(Restrictions.ge("fechaCorte",
					DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaCorteInicio(), Boolean.TRUE)));
		} else if (params.getFechaCorteFin() != null) {
			criteria.add(Restrictions.le("fechaCorte",
					DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaCorteFin(), Boolean.FALSE)));
		}
		// fechaPago
		if (params.getFechaPagoInicio() != null && params.getFechaPagoFin() != null) {
			criteria.add(Restrictions.between("fechaPago",
					DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaPagoInicio(), Boolean.TRUE),
					DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaPagoFin(), Boolean.FALSE)));
		} else if (params.getFechaPagoInicio() != null) {
			criteria.add(Restrictions.ge("fechaPago",
					DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaPagoInicio(), Boolean.TRUE)));
		} else if (params.getFechaPagoFin() != null) {
			criteria.add(Restrictions.le("fechaPago",
					DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaPagoFin(), Boolean.FALSE)));
		}

		// fecha EFFD
		if (params.getFechaEffdInicio() != null && params.getFechaEffdFin() != null) {
			criteria.add(Restrictions.between("fechaEfectiva",
					DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaEffdInicio(), Boolean.TRUE),
					DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaEffdFin(), Boolean.FALSE)));
		} else if (params.getFechaEffdInicio() != null) {
			criteria.add(Restrictions.ge("fechaEfectiva",
					DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaEffdInicio(), Boolean.TRUE)));
		} else if (params.getFechaEffdFin() != null) {
			criteria.add(Restrictions.le("fechaEfectiva",
					DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaEffdFin(), Boolean.FALSE)));
		}

		// fecha Envio
		if (params.getFechaPrimerEnvioInicio() != null && params.getFechaPrimerEnvioFin() != null) {
			criteria.add(Restrictions.between("fechaPrimerEnvio",
					DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaPrimerEnvioInicio(), Boolean.TRUE),
					DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaPrimerEnvioFin(), Boolean.FALSE)));
		} else if (params.getFechaPrimerEnvioInicio() != null) {
			criteria.add(Restrictions.ge("fechaPrimerEnvio",
					DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaPrimerEnvioInicio(), Boolean.TRUE)));
		} else if (params.getFechaPrimerEnvioFin() != null) {
			criteria.add(Restrictions.le("fechaPrimerEnvio",
					DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaPrimerEnvioFin(), Boolean.FALSE)));
		}

		// interimDate - Due Bill of Date
		if (params.getDueBillOfDateInicio() != null && params.getDueBillOfDateFin() != null) {
			criteria.add(Restrictions.between("fechaVencimiento",
					DateUtils.preparaFechaConExtremoEnSegundos(params.getDueBillOfDateInicio(), Boolean.TRUE),
					DateUtils.preparaFechaConExtremoEnSegundos(params.getDueBillOfDateFin(), Boolean.FALSE)));
		} else if (params.getDueBillOfDateInicio() != null) {
			criteria.add(Restrictions.ge("fechaVencimiento",
					DateUtils.preparaFechaConExtremoEnSegundos(params.getDueBillOfDateInicio(), Boolean.TRUE)));
		} else if (params.getDueBillOfDateFin() != null) {
			criteria.add(Restrictions.le("fechaVencimiento",
					DateUtils.preparaFechaConExtremoEnSegundos(params.getDueBillOfDateFin(), Boolean.FALSE)));
		}
		// fechasAdicionales
		if (StringUtils.isNotEmpty(params.getFechasAdicionales())
				&& !params.getFechasAdicionales().contentEquals("-1")) {
			if (params.getFechasAdicionales().contentEquals("0")) {
				criteria.add(Restrictions.isNotNull("fechasAdicionales"));
			} else {
				criteria.add(Restrictions.isNull("fechasAdicionales"));
			}

		}

		if (StringUtils.isNotEmpty(params.getNett()) && !params.getNett().contentEquals("-1")) {

			if (params.getNett().contentEquals("0")) {

				criteria.add(Restrictions.isNotNull("importeNETT"));
			} else {
				criteria.add(Restrictions.isNull("importeNETT"));
			}
		}

		// folioIndeval
		if (params.getFolioIndeval() != null) {
			criteria.add(Restrictions.eq("idCalendario", params.getFolioIndeval()));
		}

		// corp
		if (StringUtils.isNotEmpty(params.getRefCustodio())) {
			criteria.add(Restrictions.eq("referencia", params.getRefCustodio()).ignoreCase());
		}

		// fuente/custodio
		if (params.getCustodioInt() != null) {
			criteria.add(Restrictions.eq("idCustodio", params.getCustodioInt()));
		}

		// estadoEmision --- Listada
		if (StringUtils.isNotEmpty(params.getEstadoEmision()) && !params.getEstadoEmision().contentEquals("-1")) {
			if ("DESLISTADA".equals(params.getEstadoEmision())) {
				Criterion restListada_1 = Restrictions.eq("listada", params.getEstadoEmision());
				Criterion restListada_2 = Restrictions.isNull(("listada"));
				criteria.add(Restrictions.or(restListada_1, restListada_2));
			} else {
				criteria.add(Restrictions.eq("listada", params.getEstadoEmision()));
			}

		}
		// CAEV
		if (CollectionUtils.isNotEmpty(params.getIdsCaevs())) {
			Criterion rest = Restrictions.in("idTipoPagoCaev", params.getIdsCaevs());
			criteria.add(rest);
		}
		// CAMV
		if (params.getTipoPagoCAMVInt() != null) {
			criteria.add(Restrictions.eq("idTipoPagoCamv", params.getTipoPagoCAMVInt()));
		}

		// SubtipoDerecho
		if (StringUtils.isNotEmpty(params.getSubTipoDerecho()) && !params.getSubTipoDerecho().contentEquals("-1")) {
			criteria.add(Restrictions.like("subtipoDerecho", params.getSubTipoDerecho() + "%", MatchMode.ANYWHERE));
		}

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		return criteria;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portalinternacional.persistence.dao.capitales.
	 * CapitalesDistribucionDao#consultaHistoricoCapitales(com.indeval.
	 * portalinternacional.middleware.servicios.vo.ConsultaHistoricoParamsTO,
	 * com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO)
	 */
	public PaginaVO consultaHistoricoCapitalesExterno(ConsultaCapitalesParamsTO params, PaginaVO paginaVO) {
		if (paginaVO == null) {
			paginaVO = new PaginaVO();
		}
		final Integer offset = paginaVO.getOffset() != null ? paginaVO.getOffset() : null;
		final Integer regxpag = paginaVO.getRegistrosXPag() != null ? paginaVO.getRegistrosXPag() : null;
		final DetachedCriteria criteria = paramsConsultaHistoricoCapDistribucionExterno(params);

		HibernateCallback hibernateCallback = new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria crit = criteria.getExecutableCriteria(session);
				if (offset != null && regxpag != null && regxpag != PaginaVO.TODOS) {
					crit.setMaxResults(regxpag);
					crit.setFetchSize(regxpag);
					crit.setFirstResult(offset);
				}
				return crit.list();
			}
		};	
			paginaVO.setRegistros((List<VCalendarioHistoricoDerechosExterno>) this.getHibernateTemplate()
					.executeFind(hibernateCallback));
		final DetachedCriteria criteriaSum = paramsConsultaHistoricoCapDistribucionExterno(params);
		Integer tam = (Integer) this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				criteriaSum.setProjection(Projections.rowCount());
				Criteria crit = criteriaSum.getExecutableCriteria(session);
				return crit.uniqueResult();
			}

		});
		paginaVO.setTotalRegistros(tam);
		return paginaVO;
	}
	
	/**
	 * Genera Citeria para la consulta Hist&oacute;rica de derechos Externo
	 * 
	 * @param params
	 * @return
	 */
	private DetachedCriteria paramsConsultaHistoricoCapDistribucionExterno(ConsultaCapitalesParamsTO params) {
		DetachedCriteria criteria = null;
	    criteria = DetachedCriteria.forClass(VCalendarioHistoricoDerechosExterno.class);
		criteria.addOrder(Order.desc("prioridad"));
		criteria.addOrder(Order.desc("id"));

		// tipoValor
		if (StringUtils.isNotEmpty(params.getTipoValor())) {
			criteria.add(Restrictions.eq("tipoValor", params.getTipoValor()).ignoreCase());

		} else {
			Criterion rest1 = Restrictions.in("tipoValor", params.getTipoValorPermitidos());
			Criterion rest2 = Restrictions.isNull(("tipoValor"));
			criteria.add(Restrictions.or(rest1, rest2));
		}
		// emisora
		if (StringUtils.isNotEmpty(params.getEmisora())) {
			criteria.add(Restrictions.eq("emisora", params.getEmisora()).ignoreCase());
		}
		// serie
		if (StringUtils.isNotEmpty(params.getSerie())) {
			criteria.add(Restrictions.eq("serie", params.getSerie()).ignoreCase());
		}
		// isin
		if (StringUtils.isNotEmpty(params.getIsin())) {
			criteria.add(Restrictions.eq("isin", params.getIsin()).ignoreCase());
		}
		if (StringUtils.isNotEmpty(params.getFee()) && !params.getFee().contentEquals("-1")) {
			if (params.getFee().contentEquals("0")) {
				criteria.add(Restrictions.isNotNull("importeFEE"));
			} else {
				criteria.add(Restrictions.isNull("importeFEE"));
			}
		}
			// fechaEnvío
			if (params.getFechaEnvioConsInicio() != null && params.getFechaEnvioConsFin() != null) {
				criteria.add(Restrictions.between("fechaPrimerEnvio",
						DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaEnvioConsInicio(), Boolean.TRUE),
						DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaEnvioConsFin(), Boolean.FALSE)));
			} else if (params.getFechaEnvioConsInicio() != null) {
				criteria.add(Restrictions.ge("fechaPrimerEnvio",
						DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaEnvioConsInicio(), Boolean.TRUE)));
			} else if (params.getFechaEnvioConsFin() != null) {
				criteria.add(Restrictions.le("fechaPrimerEnvio",
						DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaEnvioConsFin(), Boolean.FALSE)));
			}

			// boveda //-1
			if (StringUtils.isNotBlank(params.getBoveda()) && !params.getBoveda().contentEquals("-1")) {
				Long id = new Long(params.getBoveda());
                Boveda boveda = (Boveda) this.getByPk(Boveda.class, id);
				criteria.add(Restrictions.eq("boveda", boveda.getNombreCorto()));
			}

			// divisa //1
			if (StringUtils.isNotEmpty(params.getDivisa()) && !params.getDivisa().contentEquals("-1")) {
				criteria.add(Restrictions.eq("divisa", params.getDivisa()).ignoreCase());
			}
			// fechaCorte
			if (params.getFechaCorteInicio() != null && params.getFechaCorteFin() != null) {
				criteria.add(Restrictions.between("fechaCorte",
						DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaCorteInicio(), Boolean.TRUE),
						DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaCorteFin(), Boolean.FALSE)));
			} else if (params.getFechaCorteInicio() != null) {
				criteria.add(Restrictions.ge("fechaCorte",
						DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaCorteInicio(), Boolean.TRUE)));
			} else if (params.getFechaCorteFin() != null) {
				criteria.add(Restrictions.le("fechaCorte",
						DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaCorteFin(), Boolean.FALSE)));
			}
			// fechaPago
			if (params.getFechaPagoInicio() != null && params.getFechaPagoFin() != null) {
				criteria.add(Restrictions.between("fechaPago",
						DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaPagoInicio(), Boolean.TRUE),
						DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaPagoFin(), Boolean.FALSE)));
			} else if (params.getFechaPagoInicio() != null) {
				criteria.add(Restrictions.ge("fechaPago",
						DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaPagoInicio(), Boolean.TRUE)));
			} else if (params.getFechaPagoFin() != null) {
				criteria.add(Restrictions.le("fechaPago",
						DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaPagoFin(), Boolean.FALSE)));
			}

			// fechaExdate
			if (params.getFechaXdateInicio() != null && params.getFechaXdateFin() != null) {
				criteria.add(Restrictions.between("fechaXDate",
						DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaXdateInicio(), Boolean.TRUE),
						DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaXdateFin(), Boolean.FALSE)));
			} else if (params.getFechaXdateInicio() != null) {
				criteria.add(Restrictions.ge("fechaXDate",
						DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaXdateInicio(), Boolean.TRUE)));
			} else if (params.getFechaXdateFin() != null) {
				criteria.add(Restrictions.le("fechaXDate",
						DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaXdateFin(), Boolean.FALSE)));
			}
			// interimDate - Due Bill of Date
			if (params.getDueBillOfDateInicio() != null && params.getDueBillOfDateFin() != null) {
				criteria.add(Restrictions.between("fechaVencimiento",
						DateUtils.preparaFechaConExtremoEnSegundos(params.getDueBillOfDateInicio(), Boolean.TRUE),
						DateUtils.preparaFechaConExtremoEnSegundos(params.getDueBillOfDateFin(), Boolean.FALSE)));
			} else if (params.getDueBillOfDateInicio() != null) {
				criteria.add(Restrictions.ge("fechaVencimiento",
						DateUtils.preparaFechaConExtremoEnSegundos(params.getDueBillOfDateInicio(), Boolean.TRUE)));
			} else if (params.getDueBillOfDateFin() != null) {
				criteria.add(Restrictions.le("fechaVencimiento",
						DateUtils.preparaFechaConExtremoEnSegundos(params.getDueBillOfDateFin(), Boolean.FALSE)));
			}

			// folioIndeval
			if (params.getFolioIndeval() != null) {
				criteria.add(Restrictions.eq("idCalendario", params.getFolioIndeval()));
			}

			// CAEV //-1
			if (StringUtils.isNotEmpty(params.getTipoPagoCAEV()) && !params.getTipoPagoCAEV().contentEquals("-1")) {
				Long idTipoPagoCAEV = Long.valueOf(params.getTipoPagoCAEV());
				criteria.add(Restrictions.eq("idTipoPagoCAEV", idTipoPagoCAEV));
			}
			// CAMV //-1
			if (StringUtils.isNotEmpty(params.getTipoPagoCAMV()) && !params.getTipoPagoCAMV().contentEquals("-1")) {
				Long idTipoPagoCAMV = Long.valueOf(params.getTipoPagoCAMV());
				criteria.add(Restrictions.eq("idTipoPagoCAMV", idTipoPagoCAMV));
			}
			// SubtipoDerecho
			if (StringUtils.isNotEmpty(params.getSubTipoDerecho()) && !params.getSubTipoDerecho().contentEquals("-1")) {
				criteria.add(Restrictions.like("subtipoDerecho", params.getSubTipoDerecho() + "%", MatchMode.ANYWHERE));
			}

			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		
		return criteria;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portalinternacional.persistence.dao.capitales.
	 * CapitalesDistribucionDao#getCatalogoEstadoMensajeIntSic()
	 */
	public List<EstadoMensajeIntSic> getCatalogoEstadoMensajeIntSic() {
		final StringBuilder sb = new StringBuilder();
		sb.append(" FROM " + EstadoMensajeIntSic.class.getName() + " edsic ");
		sb.append(" ORDER BY edsic.idEstadoMensajeIntSic ");
		List<EstadoMensajeIntSic> retorno = (List<EstadoMensajeIntSic>) getHibernateTemplate()
				.execute(new HibernateCallback() {
					public Object doInHibernate(Session session) throws HibernateException, SQLException {
						Query query = session.createQuery(sb.toString());
						return query.list();
					}
				});
		return retorno;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portalinternacional.persistence.dao.capitales.
	 * CapitalesDistribucionDao#getDestinatariosIntSicByEstado(java.lang.Long)
	 */
	public List<DestinatarioIntSic> getDestinatariosIntSicByEstado(final Long estado) {
		final StringBuilder sb = new StringBuilder();
		sb.append(" FROM " + DestinatarioIntSic.class.getName() + " des ");
		sb.append(" WHERE des.estado =:estado ");
		sb.append(" ORDER BY des.idDestinatario ");
		List<DestinatarioIntSic> retorno = (List<DestinatarioIntSic>) getHibernateTemplate()
				.execute(new HibernateCallback() {
					public Object doInHibernate(Session session) throws HibernateException, SQLException {
						Query query = session.createQuery(sb.toString());
						query.setLong("estado", estado);
						return query.list();
					}
				});
		return retorno;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portalinternacional.persistence.dao.capitales.
	 * CapitalesDistribucionDao#consultaAutorizacionesCalendarioCapitales(com.
	 * indeval.portalinternacional.middleware.servicios.modelo.capitales.
	 * EnvioLegislacionSicDTO,
	 * com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO)
	 */
	public PaginaVO consultaAutorizacionesCalendarioCapitales(EnvioLegislacionSicDTO filtroConsulta,
			PaginaVO paginaVO) {
		if (paginaVO == null) {
			paginaVO = new PaginaVO();
		}
		final Integer offset = paginaVO.getOffset() != null ? paginaVO.getOffset() : null;
		final Integer regxpag = paginaVO.getRegistrosXPag() != null ? paginaVO.getRegistrosXPag() : null;
		final DetachedCriteria criteria = filtroConsulta == null ? consultaAutorizaciones()
				: consultaAutorizaciones(filtroConsulta);

		// Callback
		HibernateCallback hibernateCallback = new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria crit = criteria.getExecutableCriteria(session);
				if (offset != null && regxpag != null && regxpag != PaginaVO.TODOS) {
					crit.setMaxResults(regxpag);
					crit.setFetchSize(regxpag);
					crit.setFirstResult(offset);
				}
				return crit.list();
			}
		};

		List<EnvioHistIntSicView> eventos = (List<EnvioHistIntSicView>) this.getHibernateTemplate()
				.executeFind(hibernateCallback);
		if (eventos != null) {
			paginaVO.setRegistros(eventos);
		}
		final DetachedCriteria criteriaSum = filtroConsulta == null ? consultaAutorizaciones()
				: consultaAutorizaciones(filtroConsulta);
		Integer tam = (Integer) this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				criteriaSum.setProjection(Projections.rowCount());
				Criteria crit = criteriaSum.getExecutableCriteria(session);
				return crit.uniqueResult();
			}

		});
		paginaVO.setTotalRegistros(tam);
		return paginaVO;
	}

	/**
	 * Regresa Criteria de Env&iacute;os por Autorizar
	 * 
	 * @return
	 */
	private DetachedCriteria consultaAutorizaciones() {
		DetachedCriteria criteria = DetachedCriteria.forClass(EnvioHistIntSicView.class);
		criteria.add(Restrictions.isNotNull("idEnvioLegSic"));
		criteria.addOrder(Order.desc("idEnvioLegSic"));
		criteria.add(Restrictions.eq("estadoEnvio", 0));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria;
	}

	/**
	 * Arma Criteria con los filtros de EnvioLegislacionSicDTO
	 * 
	 * @param filtroConsulta
	 * @return
	 */
	private DetachedCriteria consultaAutorizaciones(EnvioLegislacionSicDTO filtroConsulta) {
		DetachedCriteria criteria = DetachedCriteria.forClass(EnvioHistIntSicView.class);
		criteria.add(Restrictions.isNotNull("idEnvioLegSic"));
		criteria.addOrder(Order.desc("idEnvioLegSic"));
		criteria.addOrder(Order.desc("folioIndeval"));
		criteria.addOrder(Order.desc("folioMensaje"));

		/* tipo valor */
		if (StringUtils.isNotBlank(filtroConsulta.getTipoValor())) {
			criteria.add(Restrictions.eq("tipoValor", filtroConsulta.getTipoValor()).ignoreCase());
		}

		/* serie */
		if (StringUtils.isNotBlank(filtroConsulta.getSerie())) {
			criteria.add(Restrictions.eq("serie", filtroConsulta.getSerie()).ignoreCase());
		}

		/* emisora */
		if (StringUtils.isNotBlank(filtroConsulta.getEmisora())) {
			criteria.add(Restrictions.eq("emisora", filtroConsulta.getEmisora()).ignoreCase());
		}

		/* isin */
		if (StringUtils.isNotBlank(filtroConsulta.getIsin())) {
			criteria.add(Restrictions.eq("isin", filtroConsulta.getIsin()).ignoreCase());
		}

		/* Usuario solicita */
		if (StringUtils.isNotBlank(filtroConsulta.getUsuario())) {
			criteria.add(Restrictions.like("usuarioSolicitante", filtroConsulta.getUsuario() + "%", MatchMode.ANYWHERE)
					.ignoreCase());
		}

		/* Fecha solicitud */
		if (filtroConsulta.getFechaSolicitudInicio() != null && filtroConsulta.getFechaSolicitudFin() != null) {
			criteria.add(Restrictions.between("fechaSolicitud",
					DateUtils.preparaFechaConExtremoEnSegundos(filtroConsulta.getFechaSolicitudInicio(), Boolean.TRUE),
					DateUtils.preparaFechaConExtremoEnSegundos(filtroConsulta.getFechaSolicitudFin(), Boolean.FALSE)));
		} else if (filtroConsulta.getFechaSolicitudInicio() != null) {
			criteria.add(Restrictions.ge("fechaSolicitud", DateUtils
					.preparaFechaConExtremoEnSegundos(filtroConsulta.getFechaSolicitudInicio(), Boolean.TRUE)));
		} else if (filtroConsulta.getFechaSolicitudFin() != null) {
			criteria.add(Restrictions.le("fechaSolicitud",
					DateUtils.preparaFechaConExtremoEnSegundos(filtroConsulta.getFechaSolicitudFin(), Boolean.FALSE)));
		}

		/* Usuario autoriza/cancela */
		if (StringUtils.isNotBlank(filtroConsulta.getUsuarioAutoriza())) {
			criteria.add(
					Restrictions.like("usuarioAutoriza", filtroConsulta.getUsuarioAutoriza() + "%", MatchMode.ANYWHERE)
							.ignoreCase());
		}

		/* Fecha Autorizacion/Cancelacion */
		if (filtroConsulta.getFechaAutorizacionInicio() != null && filtroConsulta.getFechaAutorizacionFin() != null) {
			criteria.add(Restrictions.between("fechaActualizacion",
					DateUtils.preparaFechaConExtremoEnSegundos(filtroConsulta.getFechaAutorizacionInicio(),
							Boolean.TRUE),
					DateUtils.preparaFechaConExtremoEnSegundos(filtroConsulta.getFechaAutorizacionFin(),
							Boolean.FALSE)));
		} else if (filtroConsulta.getFechaAutorizacionInicio() != null) {
			criteria.add(Restrictions.ge("fechaActualizacion", DateUtils
					.preparaFechaConExtremoEnSegundos(filtroConsulta.getFechaAutorizacionInicio(), Boolean.TRUE)));
		} else if (filtroConsulta.getFechaAutorizacionFin() != null) {
			criteria.add(Restrictions.le("fechaActualizacion", DateUtils
					.preparaFechaConExtremoEnSegundos(filtroConsulta.getFechaAutorizacionFin(), Boolean.FALSE)));
		}

		/* Estado Envio */
		if (filtroConsulta.getEstado() != null) {
			criteria.add(Restrictions.eq("estadoEnvio", filtroConsulta.getEstado()));
		}

		/* Destinatario */
		if (!StringUtils.equals("-1", filtroConsulta.getDestinatario())) {
			criteria.add(Restrictions.eq("destinatario", filtroConsulta.getDestinatario()).ignoreCase());
		}

		/* Id Calendario (folio indeval) */
		if (filtroConsulta.getIdCalendario() != null) {
			criteria.add(Restrictions.eq("folioIndeval", filtroConsulta.getIdCalendario()));
		}

		/* Id Hist&oacute;rico (folio Mensaje) */
		if (filtroConsulta.getIdHistorico() != null) {
			criteria.add(Restrictions.eq("folioMensaje", filtroConsulta.getIdHistorico()));
		}

		/* Fecha Exdate */
		if (filtroConsulta.getFechaExdateInicio() != null && filtroConsulta.getFechaExdateFin() != null) {
			criteria.add(Restrictions.between("fechaExdate",
					DateUtils.preparaFechaConExtremoEnSegundos(filtroConsulta.getFechaExdateInicio(), Boolean.TRUE),
					DateUtils.preparaFechaConExtremoEnSegundos(filtroConsulta.getFechaExdateFin(), Boolean.FALSE)));
		} else if (filtroConsulta.getFechaExdateInicio() != null) {
			criteria.add(Restrictions.ge("fechaExdate",
					DateUtils.preparaFechaConExtremoEnSegundos(filtroConsulta.getFechaExdateInicio(), Boolean.TRUE)));
		} else if (filtroConsulta.getFechaExdateFin() != null) {
			criteria.add(Restrictions.le("fechaExdate",
					DateUtils.preparaFechaConExtremoEnSegundos(filtroConsulta.getFechaExdateFin(), Boolean.FALSE)));
		}

		/* Fecha Hora Recepcion */
		if (filtroConsulta.getFechaCorteInicio() != null && filtroConsulta.getFechaCorteFin() != null) {
			criteria.add(Restrictions.between("fechaCorte",
					DateUtils.preparaFechaConExtremoEnSegundos(filtroConsulta.getFechaCorteInicio(), Boolean.TRUE),
					DateUtils.preparaFechaConExtremoEnSegundos(filtroConsulta.getFechaCorteFin(), Boolean.FALSE)));
		} else if (filtroConsulta.getFechaCorteInicio() != null) {
			criteria.add(Restrictions.ge("fechaCorte",
					DateUtils.preparaFechaConExtremoEnSegundos(filtroConsulta.getFechaCorteInicio(), Boolean.TRUE)));
		} else if (filtroConsulta.getFechaCorteFin() != null) {
			criteria.add(Restrictions.le("fechaCorte",
					DateUtils.preparaFechaConExtremoEnSegundos(filtroConsulta.getFechaCorteFin(), Boolean.FALSE)));
		}

		/* fuente */
		if (!StringUtils.equals("-1", filtroConsulta.getFuente())) {
			criteria.add(Restrictions.eq("fuente", filtroConsulta.getFuente()).ignoreCase());
		}

		/* caev */
		if (!StringUtils.equals("-1", filtroConsulta.getTipoDerechoCaev())) {
			criteria.add(Restrictions.eq("tipoDerechoCaev", filtroConsulta.getTipoDerechoCaev()).ignoreCase());
		}

		/* camv */
		if (!StringUtils.equals("-1", filtroConsulta.getTipoDerechoCamv())) {
			criteria.add(Restrictions.eq("tipoDerechoCamv", filtroConsulta.getTipoDerechoCamv()).ignoreCase());
		}

		/* estadoEmision */
		if (!StringUtils.equals("-1", filtroConsulta.getEstadoEmision())) {
			criteria.add(Restrictions.eq("listada", filtroConsulta.getEstadoEmision()).ignoreCase());
		}

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		logger.error("Ocurrio un error:",criteria.toString());
		return criteria;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portalinternacional.persistence.dao.capitales.
	 * CapitalesDistribucionDao#guardaEnvioLegMoi(java.util.List)
	 */
	public void guardaEnvioLegMoi(List<EnvioLegislacionSic> enviosMoi) throws BusinessException {
		this.getHibernateTemplate().saveOrUpdateAll(enviosMoi);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portalinternacional.persistence.dao.capitales.
	 * CapitalesDistribucionDao#getEnvioLegislacionSicById(java.lang.Long)
	 */
	public EnvioLegislacionSic getEnvioLegislacionSicById(final Long idEnvio) throws BusinessException {
		final StringBuilder sb = new StringBuilder();
		sb.append(" FROM " + EnvioLegislacionSic.class.getName() + " env ");
		sb.append(" WHERE env.id =:idEnvio ");
		return (EnvioLegislacionSic) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sb.toString());
				query.setLong("idEnvio", idEnvio);
				return query.uniqueResult();
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portalinternacional.persistence.dao.capitales.
	 * CapitalesDistribucionDao#consultaUltimoHistoricoCalendario(java.lang.Long)
	 */
	public Long consultaUltimoHistoricoCalendario(final Long idCalendario) {
		Long ultimoCalendarioHistorico = (Long) this.getHibernateTemplate().execute(new HibernateCallback() {
			public Long doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(CalendarioHistoricoDerechosCapitales.class, "ch");
				criteria.setProjection(Projections.max("id"));
				criteria.add(Restrictions.eq("idCalendario", idCalendario));
				return (Long) criteria.uniqueResult();
			}
		});
		return ultimoCalendarioHistorico;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portalinternacional.persistence.dao.capitales.
	 * CapitalesDistribucionDao#consultaEnviadosCalendarioCapitales(com.indeval.
	 * portalinternacional.middleware.servicios.vo.EnvioCapitalesParamsTO,
	 * com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO)
	 */
	public PaginaVO consultaEnviadosCalendarioCapitales(EnvioCapitalesParamsTO params, PaginaVO paginaVO)
			throws BusinessException {
		if (paginaVO == null) {
			paginaVO = new PaginaVO();
		}
		final Integer offset = paginaVO.getOffset() != null ? paginaVO.getOffset() : null;
		final Integer regxpag = paginaVO.getRegistrosXPag() != null ? paginaVO.getRegistrosXPag() : null;

		final DetachedCriteria criteria = paramsEnvioCapitales(params);

		HibernateCallback hibernateCallback = new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria crit = criteria.getExecutableCriteria(session);
				if (offset != null && regxpag != null && regxpag != PaginaVO.TODOS) {
					crit.setMaxResults(regxpag);
					crit.setFetchSize(regxpag);
					crit.setFirstResult(offset);
				}
				return crit.list();
			}
		};

		List<VEnvioIntSic> eventos = (List<VEnvioIntSic>) this.getHibernateTemplate().executeFind(hibernateCallback);
		if (eventos != null) {
			paginaVO.setRegistros(eventos);
		}
		final DetachedCriteria criteriaSum = paramsEnvioCapitales(params);
		Integer tam = (Integer) this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				criteriaSum.setProjection(Projections.rowCount());
				Criteria crit = criteriaSum.getExecutableCriteria(session);
				return crit.uniqueResult();
			}

		});
		paginaVO.setTotalRegistros(tam);
		return paginaVO;
	}

	/**
	 * Regresa Criteria para la pantalla de Env&iacute;os
	 * 
	 * @param params
	 * @return
	 */
	private DetachedCriteria paramsEnvioCapitales(EnvioCapitalesParamsTO params) {
		DetachedCriteria criteria = DetachedCriteria.forClass(VEnvioIntSic.class);

		criteria.add(Restrictions.isNotNull("idEnvioIntSic"));
		criteria.addOrder(Order.desc("idEnvioIntSic"));

		// folioIndeval
		if (params.getFolioIndeval() != null) {
			criteria.add(Restrictions.eq("idCalendario", params.getFolioIndeval()));
		}

		// idFolioMensajeIndeval
		if (params.getFolioMensajeIndeval() != null) {
			criteria.add(Restrictions.eq("idHistorico", params.getFolioMensajeIndeval()));
		}

		// idEnvio
		if (params.getIdEnvios() != null) {
			criteria.add(Restrictions.eq("numeroEnvio", params.getIdEnvios()));
		}
		// fecha Modificacion
		if (params.getFechaModificacionInicio() != null && params.getFechaModificacionFin() != null) {
			criteria.add(Restrictions.between("fechaActualizacion",
					DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaModificacionInicio(), Boolean.TRUE),
					DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaModificacionFin(), Boolean.FALSE)));
		} else if (params.getFechaModificacionInicio() != null) {
			criteria.add(Restrictions.ge("fechaActualizacion",
					DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaModificacionInicio(), Boolean.TRUE)));
		} else if (params.getFechaModificacionFin() != null) {
			criteria.add(Restrictions.le("fechaActualizacion",
					DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaModificacionFin(), Boolean.FALSE)));
		}

		// fecha Envio/Recepcion
		if (params.getFechaEnvioInicio() != null && params.getFechaEnvioFin() != null) {
			criteria.add(Restrictions.between("fechaEnvio",
					DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaEnvioInicio(), Boolean.TRUE),
					DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaEnvioFin(), Boolean.FALSE)));
		} else if (params.getFechaEnvioInicio() != null) {
			criteria.add(Restrictions.ge("fechaEnvio",
					DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaEnvioInicio(), Boolean.TRUE)));
		} else if (params.getFechaEnvioFin() != null) {
			criteria.add(Restrictions.le("fechaEnvio",
					DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaEnvioFin(), Boolean.FALSE)));
		}

		// destinatario/ -1
		if (StringUtils.isNotEmpty(params.getDestinatario()) && !params.getDestinatario().contentEquals("-1")) {
			criteria.add(Restrictions.eq("biccode", params.getDestinatario()));
		}

		// ackAMH //1
		if (StringUtils.isNotEmpty(params.getACKAMH()) && !params.getACKAMH().contentEquals("-1")) {
			criteria.add(Restrictions.eq("ackAmh", params.getACKAMHInt()));
		}
		// ackBolsas //1
		if (StringUtils.isNotEmpty(params.getACKBolsa()) && !params.getACKBolsa().contentEquals("-1")) {
			criteria.add(Restrictions.eq("ackSwift", params.getACKBolsaInt()));
		}

		// usuario
		if (StringUtils.isNotEmpty(params.getUsuario())) {
			criteria.add(Restrictions.like("usuario", params.getUsuario() + "%", MatchMode.ANYWHERE).ignoreCase());
		}
		// consecutivo
		if (params.getConsecutivo() != null) {
			criteria.add(Restrictions.eq("consecutivo", params.getConsecutivo()));
		}
		// tipoValor
		if (StringUtils.isNotEmpty(params.getTipoValor())) {
			criteria.add(Restrictions.eq("tipoValor", params.getTipoValor()).ignoreCase());
		}
		// emisora
		if (StringUtils.isNotEmpty(params.getEmisora())) {
			criteria.add(Restrictions.eq("emisora", params.getEmisora()).ignoreCase());

		}
		// serie
		if (StringUtils.isNotEmpty(params.getSerie())) {
			criteria.add(Restrictions.eq("serie", params.getSerie()).ignoreCase());

		}
		// isin
		if (StringUtils.isNotEmpty(params.getIsin())) {
			criteria.add(Restrictions.eq("isin", params.getIsin()).ignoreCase());

		}
		return criteria;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portalinternacional.persistence.dao.capitales.
	 * CapitalesDistribucionDao#consultaEnviosCalendario(boolean, java.lang.Long,
	 * com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO)
	 */
	public PaginaVO consultaEnviosCalendario(boolean bandera, Long id, PaginaVO paginaVO) throws BusinessException {
		if (paginaVO == null) {
			paginaVO = new PaginaVO();
		}
		final Integer offset = paginaVO.getOffset() != null ? paginaVO.getOffset() : null;
		final Integer regxpag = paginaVO.getRegistrosXPag() != null ? paginaVO.getRegistrosXPag() : null;

		final DetachedCriteria criteria = paramsEnvioCapitalesPopUp(bandera, id);

		HibernateCallback hibernateCallback = new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria crit = criteria.getExecutableCriteria(session);
				if (offset != null && regxpag != null && regxpag != PaginaVO.TODOS) {
					crit.setMaxResults(regxpag);
					crit.setFetchSize(regxpag);
					crit.setFirstResult(offset);
				}
				return crit.list();
			}
		};

		List<VEnvioIntSic> eventos = (List<VEnvioIntSic>) this.getHibernateTemplate().executeFind(hibernateCallback);
		if (eventos != null) {
			paginaVO.setRegistros(eventos);
		}
		final DetachedCriteria criteriaSum = paramsEnvioCapitalesPopUp(bandera, id);
		Integer tam = (Integer) this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				criteriaSum.setProjection(Projections.rowCount());
				Criteria crit = criteriaSum.getExecutableCriteria(session);
				return crit.uniqueResult();
			}

		});
		paginaVO.setTotalRegistros(tam);
		return paginaVO;
	}

	/**
	 * Regresa Criteria para el popup de mensajes enviados
	 * 
	 * @param bandera
	 * @param id
	 * @return
	 */
	private DetachedCriteria paramsEnvioCapitalesPopUp(boolean bandera, Long id) {
		DetachedCriteria criteria = DetachedCriteria.forClass(VEnvioIntSic.class);

		criteria.add(Restrictions.isNotNull("idEnvioIntSic"));
		criteria.addOrder(Order.desc("idEnvioIntSic"));

		// folioIndeval
		if (bandera && id != null) {
			criteria.add(Restrictions.eq("idCalendario", id));
		}

		// idFolioMensajeIndeval
		else if (!bandera && id != null) {
			criteria.add(Restrictions.eq("idHistorico", id));
		}

		return criteria;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portalinternacional.persistence.dao.capitales.
	 * CapitalesDistribucionDao#consultaEmisionesCalendarioCapitales(com.indeval.
	 * portalinternacional.middleware.servicios.vo.
	 * ConsultaEmisionesCalendarioParamsTO,
	 * com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO)
	 */
	public PaginaVO consultaEmisionesCalendarioCapitales(ConsultaEmisionesCalendarioParamsTO params, PaginaVO paginaVO)
			throws BusinessException {
		if (paginaVO == null) {
			paginaVO = new PaginaVO();
		}
		final Integer offset = paginaVO.getOffset() != null ? paginaVO.getOffset() : null;
		final Integer regxpag = paginaVO.getRegistrosXPag() != null ? paginaVO.getRegistrosXPag() : null;

		final DetachedCriteria criteria = paramsConsultaEmisiones(params);

		// Callback
		HibernateCallback hibernateCallback = new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria crit = criteria.getExecutableCriteria(session);
				if (offset != null && regxpag != null && regxpag != PaginaVO.TODOS) {
					crit.setMaxResults(regxpag);
					crit.setFetchSize(regxpag);
					crit.setFirstResult(offset);
				}
				return crit.list();
			}
		};

		List<VEmisionesCalendario> eventos = (List<VEmisionesCalendario>) this.getHibernateTemplate()
				.executeFind(hibernateCallback);
		if (eventos != null) {
			paginaVO.setRegistros(eventos);
		}
		final DetachedCriteria criteriaSum = paramsConsultaEmisiones(params);
		Integer tam = (Integer) this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				criteriaSum.setProjection(Projections.rowCount());
				Criteria crit = criteriaSum.getExecutableCriteria(session);
				return crit.uniqueResult();
			}

		});
		paginaVO.setTotalRegistros(tam);
		return paginaVO;
	}

	/**
	 * Regresa Criteria para la pantalla de consulta Emisiones
	 * 
	 * @param params
	 * @return
	 */
	private DetachedCriteria paramsConsultaEmisiones(ConsultaEmisionesCalendarioParamsTO params) {
		DetachedCriteria criteria = DetachedCriteria.forClass(VEmisionesCalendario.class);
		criteria.addOrder(Order.desc("idEmision"));

		// idEmision
		if (params.getIdEmision() != null) {
			criteria.add(Restrictions.eq("idEmision", params.getIdEmision()).ignoreCase());
		}
		// tipoValor
		if (StringUtils.isNotEmpty(params.getTipoValor())) {
			Criterion rest1 = Restrictions.eq("tipoValor", params.getTipoValor()).ignoreCase();
			Criterion rest2 = Restrictions.isNull(("tipoValor"));
			criteria.add(Restrictions.or(rest1, rest2));
		}
		// emisora
		if (StringUtils.isNotEmpty(params.getEmisora())) {
			criteria.add(Restrictions.eq("emisora", params.getEmisora()).ignoreCase());
		}
		// serie
		if (StringUtils.isNotEmpty(params.getSerie())) {
			criteria.add(Restrictions.eq("serie", params.getSerie()).ignoreCase());
		}
		// isin
		if (StringUtils.isNotEmpty(params.getIsin())) {
			criteria.add(Restrictions.eq("isin", params.getIsin()).ignoreCase());
		}
		// razonSocial
		if (StringUtils.isNotEmpty(params.getRazonSocial())) {
			criteria.add(
					Restrictions.like("razonSocial", params.getRazonSocial() + "%", MatchMode.ANYWHERE).ignoreCase());
		}
		// estatusRegistro/estatusSistema
		if (StringUtils.isNotEmpty(params.getEstatusSistema()) && !params.getEstatusSistema().equals("SIN ESTATUS")
				&& !params.getEstatusSistema().contentEquals("-1")) {
			criteria.add(Restrictions.eq("estatusRegistro", params.getEstatusSistema()).ignoreCase());
		} else if (params.getEstatusSistema().equals("SIN ESTATUS")) {
			criteria.add(Restrictions.isNull(("estatusRegistro")));
		}

		// detalleCustodio
		if (StringUtils.isNotEmpty(params.getDetalleCustodio()) && !params.getDetalleCustodio().contentEquals("-1")) {
			criteria.add(Restrictions.eq("detalleCustodio", params.getDetalleCustodio()).ignoreCase());
		}
		// fecha Alta
		if (params.getFechaAltaInicio() != null && params.getFechaAltaFin() != null) {
			criteria.add(Restrictions.between("fechaAlta",
					DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaAltaInicio(), Boolean.TRUE),
					DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaAltaFin(), Boolean.FALSE)));
		} else if (params.getFechaAltaInicio() != null) {
			criteria.add(Restrictions.ge("fechaAlta",
					DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaAltaInicio(), Boolean.TRUE)));
		} else if (params.getFechaAltaFin() != null) {
			criteria.add(Restrictions.le("fechaAlta",
					DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaAltaFin(), Boolean.FALSE)));
		}

		// fecha Baja
		if (params.getFechaBajaInicio() != null && params.getFechaBajaFin() != null) {
			criteria.add(Restrictions.between("fechaBaja",
					DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaBajaInicio(), Boolean.TRUE),
					DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaBajaFin(), Boolean.FALSE)));
		} else if (params.getFechaBajaInicio() != null) {
			criteria.add(Restrictions.ge("fechaBaja",
					DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaBajaInicio(), Boolean.TRUE)));
		} else if (params.getFechaBajaFin() != null) {
			criteria.add(Restrictions.le("fechaBaja",
					DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaBajaFin(), Boolean.FALSE)));
		}
		// fecha Ultima Modificacion
		if (params.getFechaModificacionInicio() != null && params.getFechaModificacionFin() != null) {
			criteria.add(Restrictions.between("fechaModificacion",
					DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaModificacionInicio(), Boolean.TRUE),
					DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaModificacionFin(), Boolean.FALSE)));
		} else if (params.getFechaModificacionInicio() != null) {
			criteria.add(Restrictions.ge("fechaModificacion",
					DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaModificacionInicio(), Boolean.TRUE)));
		} else if (params.getFechaModificacionFin() != null) {
			criteria.add(Restrictions.le("fechaModificacion",
					DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaModificacionFin(), Boolean.FALSE)));
		}
		// fee
		if (StringUtils.isNotEmpty(params.getAssetManager()) && !params.getAssetManager().contentEquals("-1")) {
			if (params.getAssetManager().contentEquals("0")) {
				criteria.add(Restrictions.isNotNull("descripcion"));
			} else {
				criteria.add(Restrictions.isNull("descripcion"));
			}
		}
		// paisOrigen
		if (StringUtils.isNotEmpty(params.getPaisOrigen()) && !params.getPaisOrigen().contentEquals("-1")) {
			criteria.add(Restrictions.eq("paisOrigen", params.getPaisOrigen()).ignoreCase());
		}
		// // custodio
		// if (params.getCustodioInt() != null) {
		// criteria.add(Restrictions.eq("custodio",
		// String.valueOf(params.getCustodioInt())));
		// }
		// estatusEmision
		if (StringUtils.isNotEmpty(params.getEstatusEmision()) && !params.getEstatusEmision().contentEquals("-1")) {
			criteria.add(Restrictions.eq("estatusEmision", params.getEstatusEmision()).ignoreCase());
		}
		// listada
		if (StringUtils.isNotEmpty(params.getListada()) && !params.getListada().contentEquals("-1")) {
			criteria.add(Restrictions.eq("listada", params.getListada()).ignoreCase());
		}

		return criteria;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portalinternacional.persistence.dao.capitales.
	 * CapitalesDistribucionDao#getNarrativasForIdCalendarioOrIdHist(java.lang.Long,
	 * boolean)
	 */
	public List<NarrativaCapitalesVO> getNarrativasForIdCalendarioOrIdHist(final Long id, final boolean isCalendario)
			throws BusinessException {
		if (id == null) {
			throw new BusinessException("Parametro nulo");
		}
		return (List<NarrativaCapitalesVO>) this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(final Session session) throws HibernateException, SQLException {
				String sb = null;
				if (isCalendario) {
					sb = getQueryNarrativaByIdCalendario();
				} else {
					sb = getQueryNarrativaByIdHistorico();
				}
				SQLQuery query = session.createSQLQuery(sb.toString());
				query.setLong("id", id);

				query.addScalar("idNarrativa", Hibernate.LONG);
				query.addScalar("origen", Hibernate.STRING);
				query.addScalar("folioIndeval", Hibernate.LONG);
				query.addScalar("folioMensaje", Hibernate.LONG);
				query.addScalar("tipoMensaje", Hibernate.STRING);
				query.addScalar("narrativa", Hibernate.STRING);
				query.addScalar("tipoPagoCaev", Hibernate.STRING);
				query.addScalar("descripcionCaev", Hibernate.STRING);
				query.addScalar("tipoPagoCamv", Hibernate.STRING);
				query.addScalar("descripcionCamv", Hibernate.STRING);
				query.addScalar("fechaCorte", Hibernate.STRING);
				query.addScalar("fuente", Hibernate.STRING);
				query.addScalar("fechaMensaje", Hibernate.STRING);
				query.addScalar("usuario", Hibernate.STRING);
				query.addScalar("orden", Hibernate.LONG);
				query.addScalar("secuencia", Hibernate.LONG);

				query.setResultTransformer(new AliasToBeanResultTransformer(NarrativaCapitalesVO.class));
				return query.list();
			}
		});
	}

	/**
	 * Regresa el query para las narrativas ligadas al derecho
	 * 
	 * @return
	 */
	private String getQueryNarrativaByIdCalendario() {
		StringBuilder sb = new StringBuilder();
		sb.append(" select ");
		sb.append(" 	nar.ID_NARRATIVA_INT as idNarrativa, ");
		sb.append(" 	nar.ORIGEN as origen, ");
		sb.append(" 	nar.ID_CALENDARIO_INT as folioIndeval, ");
		sb.append(" 	nar.ID_HIST_DERECHO_INT as folioMensaje, ");
		sb.append(" 	hist.MT_567_910 as tipoMensaje, ");
		sb.append(" 	nar.NARRATIVA as narrativa, ");
		sb.append(" 	caev.CLAVE_PAGO as tipoPagoCaev,  ");
		sb.append(" 	caev.DESCRIPCION as descripcionCaev,  ");
		sb.append(" 	camv.CLAVE_PAGO as tipoPagoCamv, ");
		sb.append(" 	camv.DESCRIPCION as descripcionCamv, ");
		sb.append(" 	to_char(hist.FECHA_CORTE,'DD/MM/YYYY') as fechaCorte, ");
		sb.append(" 	cus.NOMBRE_CORTO as fuente, ");
		sb.append(" 	to_char(hist.FECHA_HORA_ENVIO,'DD/MM/YYYY') as fechaMensaje,  ");
		sb.append(" 	nar.USUARIO as usuario,  ");
		sb.append(" 	case nar.ORIGEN when 'M' then 0 when 'E' then 1 else 2 end as orden,  ");
		sb.append(" 	NVL(hist.CONSECUTIVO,0) as secuencia  ");
		sb.append(
				"	from T_NARRATIVA_INT_SIC nar inner join T_CALENDARIO_INT cal on cal.ID_CALENDARIO_INT = nar.ID_CALENDARIO_INT  ");
		sb.append(
				"    inner join T_HISTORICO_DERECHO_INT hist on hist.ID_HIST_DERECHO_INT = nar.ID_HIST_DERECHO_INT  ");
		sb.append("    left join C_CUSTODIO cus on cus.ID_CUSTODIO = hist.ID_CUSTODIO  ");
		sb.append("    left join C_TIPO_PAGO_INT caev on caev.id_tipo_pago = hist.ID_TIPO_PAGO_CAEV  ");
		sb.append("    left join C_TIPO_PAGO_INT camv on camv.ID_TIPO_PAGO = hist.ID_TIPO_PAGO_CAMV  ");
		sb.append("	where nar.ID_CALENDARIO_INT =:id  ");
		sb.append(
				" order by nar.ID_NARRATIVA_INT desc,nar.ID_CALENDARIO_INT desc,nar.ID_HIST_DERECHO_INT desc,nar.ORIGEN asc");

		return sb.toString();

	}

	/**
	 * Regresa el query para las narrativas ligadas al hist&oacuterico Exceptuando
	 * las que tengan Origen = E (Enviadas)
	 * 
	 * @return
	 */
	private String getQueryNarrativaByIdHistorico() {
		StringBuilder sb = new StringBuilder();
		sb.append(" select ");
		sb.append(" 	nar.ID_NARRATIVA_INT as idNarrativa, ");
		sb.append(" 	nar.ORIGEN as origen, ");
		sb.append(" 	nar.ID_CALENDARIO_INT as folioIndeval, ");
		sb.append(" 	nar.ID_HIST_DERECHO_INT as folioMensaje, ");
		sb.append(" 	hist.MT_567_910 as tipoMensaje, ");
		sb.append(" 	nar.NARRATIVA as narrativa, ");
		sb.append(" 	caev.CLAVE_PAGO as tipoPagoCaev,  ");
		sb.append(" 	caev.DESCRIPCION as descripcionCaev,  ");
		sb.append(" 	camv.CLAVE_PAGO as tipoPagoCamv, ");
		sb.append(" 	camv.DESCRIPCION as descripcionCamv, ");
		sb.append(" 	to_char(hist.FECHA_CORTE,'DD/MM/YYYY') as fechaCorte, ");
		sb.append(" 	cus.NOMBRE_CORTO as fuente, ");
		sb.append(" 	to_char(hist.FECHA_HORA_ENVIO,'DD/MM/YYYY') as fechaMensaje,  ");
		sb.append(" 	nar.USUARIO as usuario,  ");
		sb.append(" 	case nar.ORIGEN when 'M' then 0 when 'E' then 1 else 2 end as orden,  ");
		sb.append(" 	NVL(hist.CONSECUTIVO,0) as secuencia  ");
		sb.append(
				"	from T_NARRATIVA_INT_SIC nar inner join T_HISTORICO_DERECHO_INT hist on hist.ID_HIST_DERECHO_INT = nar.ID_HIST_DERECHO_INT  ");
		sb.append("    left join C_CUSTODIO cus on cus.ID_CUSTODIO = hist.ID_CUSTODIO  ");
		sb.append("    left join C_TIPO_PAGO_INT caev on caev.id_tipo_pago = hist.ID_TIPO_PAGO_CAEV  ");
		sb.append("    left join C_TIPO_PAGO_INT camv on camv.ID_TIPO_PAGO = hist.ID_TIPO_PAGO_CAMV  ");
		sb.append("	where nar.ID_HIST_DERECHO_INT =:id  ");
		sb.append("	and  nar.ORIGEN != 'E'  ");
		sb.append(
				" order by nar.ID_NARRATIVA_INT desc,nar.ID_CALENDARIO_INT desc,nar.ID_HIST_DERECHO_INT desc,nar.ORIGEN asc");

		return sb.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portalinternacional.persistence.dao.capitales.
	 * CapitalesDistribucionDao#getCalendarioHistoricoById(java.lang.Long)
	 */
	public CalendarioHistoricoDerechosCapitales getCalendarioHistoricoById(final Long idHistorico)
			throws BusinessException {
		final StringBuilder sb = new StringBuilder();
		sb.append(" FROM " + CalendarioHistoricoDerechosCapitales.class.getName() + " hist ");
		sb.append(" WHERE hist.id =:idHistorico ");
		return (CalendarioHistoricoDerechosCapitales) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sb.toString());
				query.setLong("idHistorico", idHistorico);
				return query.uniqueResult();
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portalinternacional.persistence.dao.capitales.
	 * CapitalesDistribucionDao#consultaEmisionPorId(java.lang.Long)
	 */
	public VEmisionesCalendario consultaEmisionPorId(final Long idEmision) throws BusinessException {
		final StringBuilder sb = new StringBuilder();
		sb.append(" FROM " + VEmisionesCalendario.class.getName() + " emi ");
		sb.append(" WHERE emi.idEmision =:idEmision ");
		return (VEmisionesCalendario) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sb.toString());
				query.setLong("idEmision", idEmision);
				return query.uniqueResult();
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portalinternacional.persistence.dao.capitales.
	 * CapitalesDistribucionDao#guardaAssetManager(com.indeval.portalinternacional.
	 * middleware.servicios.modelo.capitales.AssetManagerEmision)
	 */
	public void guardaAssetManager(AssetManagerEmision asset) {
		this.getHibernateTemplate().saveOrUpdate(asset);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portalinternacional.persistence.dao.capitales.
	 * CapitalesDistribucionDao#assetPorId(java.lang.Long)
	 */
	public AssetManagerEmision assetPorId(final Long idEmision) throws BusinessException {
		final StringBuilder sb = new StringBuilder();
		sb.append(" FROM " + AssetManagerEmision.class.getName() + " asset ");
		sb.append(" WHERE asset.idEmision =:idEmision ");
		return (AssetManagerEmision) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sb.toString());
				query.setLong("idEmision", idEmision);
				return query.uniqueResult();
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portalinternacional.persistence.dao.capitales.
	 * CapitalesDistribucionDao#eliminaAssetManager(com.indeval.portalinternacional.
	 * middleware.servicios.modelo.capitales.AssetManagerEmision)
	 */
	public void eliminaAssetManager(AssetManagerEmision asset) {
		this.getHibernateTemplate().delete(asset);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portalinternacional.persistence.dao.capitales.
	 * CapitalesDistribucionDao#obtenSubTipoDerechos()
	 */
	public List<SubTipoDerechoIntSic> obtenSubTipoDerechos() throws BusinessException {
		final StringBuilder sb = new StringBuilder();
		sb.append(" FROM " + SubTipoDerechoIntSic.class.getName() + " sub ");
		sb.append(" ORDER BY sub.qualifier ASC ");
		List<SubTipoDerechoIntSic> retorno = (List<SubTipoDerechoIntSic>) getHibernateTemplate()
				.execute(new HibernateCallback() {
					public Object doInHibernate(Session session) throws HibernateException, SQLException {
						Query query = session.createQuery(sb.toString());
						return query.list();
					}
				});
		return retorno;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portalinternacional.persistence.dao.capitales.
	 * CapitalesDistribucionDao#getInstruccionByIdHist(java.lang.Long)
	 */
	public InstruccionCapitalesVO getInstruccionByIdHist(final Long idHistorico) throws BusinessException {
		if (idHistorico == null) {
			throw new BusinessException("Parametro nulo");
		}
		return (InstruccionCapitalesVO) this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(final Session session) throws HibernateException, SQLException {
				final StringBuilder sb = new StringBuilder();
				sb.append("select ");
				sb.append(" ins.ID_T_INSTRUCCIONES_INT_SIC as id, ");
				sb.append(" ins.ID_HIST_DERECHO_INT as idHistorico, ");
				sb.append(" ins.ID_TIPO_DERECHO_CAEV as idTipoDerechoCaev, ");
				sb.append(" ins.LIGA as liga, ");
				sb.append(" ins.TAGS_ELIMINACION as tagsEliminacion, ");
				sb.append(" ins.OPCIONES_MENSAJE as opcionesMensajes, ");
				sb.append(" caev.CLAVE_PAGO  as tipoDerechoCaev ");
				sb.append(
						"    from T_INSTRUCCIONES_INT_SIC ins left join C_TIPO_PAGO_INT caev on caev.ID_TIPO_PAGO = ins.ID_TIPO_DERECHO_CAEV  ");
				sb.append("        where ins.ID_HIST_DERECHO_INT =:idHistorico  ");

				SQLQuery query = session.createSQLQuery(sb.toString());
				query.setLong("idHistorico", idHistorico);

				query.addScalar("id", Hibernate.LONG);
				query.addScalar("idHistorico", Hibernate.LONG);
				query.addScalar("idTipoDerechoCaev", Hibernate.LONG);
				query.addScalar("liga", Hibernate.LONG);
				query.addScalar("tagsEliminacion", Hibernate.STRING);
				query.addScalar("opcionesMensajes", Hibernate.STRING);
				query.addScalar("tipoDerechoCaev", Hibernate.STRING);

				query.setResultTransformer(new AliasToBeanResultTransformer(InstruccionCapitalesVO.class));
				return query.uniqueResult();

			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portalinternacional.persistence.dao.capitales.
	 * CapitalesDistribucionDao#getVCalendarioByIdCalendario(java.lang.Long)
	 */
	public VCalendarioDerechoInt getVCalendarioByIdCalendario(final Long idCalendario) throws BusinessException {
		final StringBuilder sb = new StringBuilder();
		sb.append(" FROM " + VCalendarioDerechoInt.class.getName() + " cal ");
		sb.append(" WHERE cal.idCalendario =:idCalendario ");
		return (VCalendarioDerechoInt) this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sb.toString());
				query.setLong("idCalendario", idCalendario);
				return query.uniqueResult();
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portalinternacional.persistence.dao.capitales.
	 * CapitalesDistribucionDao#getCalendarioById(java.lang.Long)
	 */
	public Long getCalendarioById(final Long idCalendario) throws BusinessException {
		return (Long) this.getHibernateTemplate().execute(new HibernateCallback() {
			public Long doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(CalendarioDerechosCapitales.class, "cal");
				criteria.setProjection(Projections.property("cal.idCalendario"));
				criteria.add(Restrictions.eq("cal.idCalendario", idCalendario));
				return (Long) criteria.uniqueResult();
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portalinternacional.persistence.dao.capitales.
	 * CapitalesDistribucionDao#actualizaOrigenNarrativaSicByIdHistorico(java.lang.
	 * Long)
	 */
	public Integer actualizaOrigenNarrativaSicByIdHistorico(final Long idHistorico) throws BusinessException {
		final StringBuilder sb = new StringBuilder();
		sb.append(" update  " + NarrativaCapitales.class.getName() + " nar ");
		sb.append(" set nar.origen = 'E' ");
		sb.append(" where nar.origen = 'U' and nar.idHistorico =:idHistorico ");

		return (Integer) this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(final Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sb.toString());
				query.setLong("idHistorico", idHistorico);
				return query.executeUpdate();
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portalinternacional.persistence.dao.capitales.
	 * CapitalesDistribucionDao#getNarrativasEnviadasForIdCalendario(java.lang.Long)
	 */
	public List<NarrativaCapitalesVO> getNarrativasEnviadasForIdCalendario(final Long idCalendario)
			throws BusinessException {
		if (idCalendario == null) {
			throw new BusinessException("Parametro nulo");
		}
		return (List<NarrativaCapitalesVO>) this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(final Session session) throws HibernateException, SQLException {
				final StringBuilder sb = new StringBuilder();
				sb.append("select ");
				sb.append(" 	nar.ID_NARRATIVA_INT as idNarrativa, ");
				sb.append(" 	nar.ORIGEN as origen, ");
				sb.append(" 	nar.ID_CALENDARIO_INT as folioIndeval, ");
				sb.append(" 	nar.ID_HIST_DERECHO_INT as folioMensaje, ");
				sb.append(" 	hist.MT_567_910 as tipoMensaje, ");
				sb.append(" 	nar.NARRATIVA as narrativa, ");
				sb.append(" 	caev.CLAVE_PAGO as tipoPagoCaev,  ");
				sb.append(" 	caev.DESCRIPCION as descripcionCaev,  ");
				sb.append(" 	camv.CLAVE_PAGO as tipoPagoCamv, ");
				sb.append(" 	camv.DESCRIPCION as descripcionCamv, ");
				sb.append(" 	to_char(hist.FECHA_CORTE,'DD/MM/YYYY') as fechaCorte, ");
				sb.append(" 	cus.NOMBRE_CORTO as fuente, ");
				sb.append(" 	to_char(hist.FECHA_HORA_ENVIO,'DD/MM/YYYY') as fechaMensaje,  ");
				sb.append(" 	nar.USUARIO as usuario,  ");
				sb.append(" 	1 as orden,  ");
				sb.append(" 	NVL(hist.CONSECUTIVO,0) as secuencia  ");
				sb.append(" from T_NARRATIVA_INT_SIC nar inner join T_HISTORICO_DERECHO_INT hist ");
				sb.append(
						" 	on hist.ID_HIST_DERECHO_INT = nar.ID_HIST_DERECHO_INT AND hist.ID_CALENDARIO_INT = nar.ID_CALENDARIO_INT ");
				sb.append(" 	left join C_CUSTODIO cus on cus.ID_CUSTODIO = hist.ID_CUSTODIO  ");
				sb.append(" 	left join C_TIPO_PAGO_INT caev on caev.id_tipo_pago = hist.ID_TIPO_PAGO_CAEV  ");
				sb.append(" 	left join C_TIPO_PAGO_INT camv on camv.ID_TIPO_PAGO = hist.ID_TIPO_PAGO_CAMV  ");
				sb.append(" where nar.ID_CALENDARIO_INT =:idCalendario ");
				sb.append(" 	and nar.ORIGEN = 'E' ");
				sb.append(" order by hist.CONSECUTIVO DESC , nar.ID_NARRATIVA_INT DESC");

				SQLQuery query = session.createSQLQuery(sb.toString());
				query.setLong("idCalendario", idCalendario);

				query.addScalar("idNarrativa", Hibernate.LONG);
				query.addScalar("origen", Hibernate.STRING);
				query.addScalar("folioIndeval", Hibernate.LONG);
				query.addScalar("folioMensaje", Hibernate.LONG);
				query.addScalar("tipoMensaje", Hibernate.STRING);
				query.addScalar("narrativa", Hibernate.STRING);
				query.addScalar("tipoPagoCaev", Hibernate.STRING);
				query.addScalar("descripcionCaev", Hibernate.STRING);
				query.addScalar("tipoPagoCamv", Hibernate.STRING);
				query.addScalar("descripcionCamv", Hibernate.STRING);
				query.addScalar("fechaCorte", Hibernate.STRING);
				query.addScalar("fuente", Hibernate.STRING);
				query.addScalar("fechaMensaje", Hibernate.STRING);
				query.addScalar("usuario", Hibernate.STRING);
				query.addScalar("orden", Hibernate.LONG);
				query.addScalar("secuencia", Hibernate.LONG);

				query.setResultTransformer(new AliasToBeanResultTransformer(NarrativaCapitalesVO.class));
				return query.list();
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portalinternacional.persistence.dao.capitales.
	 * CapitalesDistribucionDao#getDescFechasAd()
	 */
	public List<DescFechasAdIntSic> getDescFechasAd() throws BusinessException {
		final StringBuilder sb = new StringBuilder();
		sb.append(" FROM " + DescFechasAdIntSic.class.getName() + " descr ");
		sb.append(" ORDER BY descr.qualifier ASC ");
		List<DescFechasAdIntSic> retorno = (List<DescFechasAdIntSic>) getHibernateTemplate()
				.execute(new HibernateCallback() {
					public Object doInHibernate(Session session) throws HibernateException, SQLException {
						Query query = session.createQuery(sb.toString());
						return query.list();
					}
				});
		return retorno;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portalinternacional.persistence.dao.capitales.
	 * CapitalesDistribucionDao#getNarrativasReporte(java.lang.Long)
	 */
	public String getNarrativasReporte(final Long idCalendario) throws BusinessException {
		return (String) this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(final Session session) throws HibernateException, SQLException {
				final StringBuilder sb = new StringBuilder();
				sb.append(" select nar.narrativa as narrativa ");
				sb.append(" from T_NARRATIVA_INT_SIC nar inner join T_HISTORICO_DERECHO_INT hist ");
				sb.append(
						" 	on hist.ID_HIST_DERECHO_INT = nar.ID_HIST_DERECHO_INT AND hist.ID_CALENDARIO_INT = nar.ID_CALENDARIO_INT ");
				sb.append(" where nar.ID_CALENDARIO_INT =:idCalendario ");
				sb.append(" 	and nar.ORIGEN = 'E' ");
				sb.append(" order by hist.CONSECUTIVO ASC , nar.ID_NARRATIVA_INT ASC");

				SQLQuery query = session.createSQLQuery(sb.toString());
				query.setLong("idCalendario", idCalendario);
				query.addScalar("narrativa", Hibernate.STRING);
				List<String> listaRes = query.list();
				StringBuilder resultadoCadena = new StringBuilder("");
				if (listaRes != null && !listaRes.isEmpty()) {
					for (int contador = 0; contador < listaRes.size(); contador++) {
						if (contador == 0) {
							resultadoCadena.append(listaRes.get(contador));
						} else {
							resultadoCadena.append(" || ");
							resultadoCadena.append(listaRes.get(contador));
						}
					}
				}
				return resultadoCadena.toString();
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portalinternacional.persistence.dao.capitales.
	 * CapitalesDistribucionDao#getEmisionAssetManagerForIdEmision(java.lang.Long,
	 * java.lang.Long)
	 */
	public EmisionAssetManagerVO getEmisionAssetManagerForIdEmision(final Long idEmision, final Long idCatbic)
			throws BusinessException {
		return (EmisionAssetManagerVO) this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(final Session session) throws HibernateException, SQLException {
				final StringBuilder sb = new StringBuilder();
				sb.append("select ");
				sb.append(" ve.ID_EMISION as idEmision, ");
				sb.append(" ve.SERIE as serie, ");
				sb.append(" ve.CLAVE_TIPO_VALOR as tipoValor, ");
				sb.append(" ve.ISIN as isin, ");
				sb.append(" ve.EMISORA as emisora, ");
				sb.append(" ve.DETALLE_CUSTODIO as custodioDesc, ");
				sb.append(" ve.LISTADA  as estadoEmision, ");
				sb.append(" tss.ID_ASSET_MANAGER  as idAssetManager, ");
				sb.append(" tss.DESCRIPCION  as assetManagerDesc ");

				sb.append(
						"    from V_EMISIONES_INT ve left join T_ASSET_MANAGER_INT_SIC tss on tss.ID_EMISION = ve.ID_EMISION  ");
				sb.append("        where ve.ID_EMISION =:idEmision ");
				if (idCatbic != null) {
					sb.append(" and ve.CUSTODIO =:idCatbic  ");
				} else {
					sb.append(" and ve.CUSTODIO is null  ");
				}

				SQLQuery query = session.createSQLQuery(sb.toString());
				query.setLong("idEmision", idEmision);
				if (idCatbic != null) {
					query.setLong("idCatbic", idCatbic);
				}

				query.addScalar("idEmision", Hibernate.LONG);
				query.addScalar("serie", Hibernate.STRING);
				query.addScalar("tipoValor", Hibernate.STRING);
				query.addScalar("isin", Hibernate.STRING);
				query.addScalar("emisora", Hibernate.STRING);
				query.addScalar("custodioDesc", Hibernate.STRING);
				query.addScalar("estadoEmision", Hibernate.STRING);
				query.addScalar("idAssetManager", Hibernate.LONG);
				query.addScalar("assetManagerDesc", Hibernate.STRING);

				query.setResultTransformer(new AliasToBeanResultTransformer(EmisionAssetManagerVO.class));
				List<EmisionAssetManagerVO> listReturn = Collections.emptyList();

				listReturn = (List<EmisionAssetManagerVO>) query.list();
				EmisionAssetManagerVO misionAssetManagerVO = null;
				// por los problemas en produccion no se puede garantizar que regrese un solo
				// balor la consulta
				if (!listReturn.isEmpty()) {
					misionAssetManagerVO = listReturn.get(0);
				}
				return misionAssetManagerVO;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portalinternacional.persistence.dao.capitales.
	 * CapitalesDistribucionDao#getIdNarrativaByIdHist(java.lang.Long)
	 */
	public Long getIdNarrativaByIdHist(final Long idHistorico) throws BusinessException {
		final StringBuilder sb = new StringBuilder();
		sb.append(" FROM " + NarrativaCapitales.class.getName() + " nar ");
		sb.append(" where nar.idHistorico =:idHistorico ");
		sb.append(" and nar.origen ='U' ");
		Long retorno = (Long) getHibernateTemplate().execute(new HibernateCallback() {
			public Long doInHibernate(Session session) throws HibernateException, SQLException {
				Long idRegreso = null;
				Query query = session.createQuery(sb.toString());
				query.setLong("idHistorico", idHistorico);
				List<NarrativaCapitales> narrativas = query.list();
				if (narrativas != null && !narrativas.isEmpty()) {
					idRegreso = narrativas.get(0).getId();
				}
				return idRegreso;
			}
		});
		return retorno;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portalinternacional.persistence.dao.capitales.
	 * CapitalesDistribucionDao#getBitacoraMensajeSwiftById(java.lang.Long)
	 */
	public String getBitacoraMensajeSwiftById(final Long idBitacora) {
		final StringBuilder sb = new StringBuilder();
		sb.append(" SELECT bit.mensaje ");
		sb.append(" FROM " + BitacoraMensajeSwift.class.getName() + " bit ");
		sb.append(" where bit.id = :idBitacora ");
		return (String) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sb.toString());
				query.setLong("idBitacora", idBitacora);
				return query.uniqueResult();
			}
		});
	}
	
	public CalendarioHistoricoDerechosCapitalesVo getCalendarioHistoricoVoById(final Long idHistorico)
			throws BusinessException {

		return (CalendarioHistoricoDerechosCapitalesVo) this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(final Session session) throws HibernateException, SQLException {
				final StringBuilder sb = new StringBuilder();
				sb.append("SELECT ");
				sb.append(" 	thd.ID_HIST_DERECHO_INT as id, ");
				sb.append(" 	thd.ID_CALENDARIO_INT as idCalendario ");
				sb.append(" FROM T_HISTORICO_DERECHO_INT thd");
				sb.append(" WHERE thd.ID_HIST_DERECHO_INT = :idHistorico ");

				SQLQuery query = session.createSQLQuery(sb.toString());
				query.setLong("idHistorico", idHistorico);

				query.addScalar("id", Hibernate.LONG);
				query.addScalar("idCalendario", Hibernate.LONG);

				query.setResultTransformer(new AliasToBeanResultTransformer(CalendarioHistoricoDerechosCapitalesVo.class));
				return query.uniqueResult();
			}
		});
		
	}

}
