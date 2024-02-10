/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.persistence.dao.impl;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO;
import com.indeval.portaldali.persistence.modelo.PosicionNombrada;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.persistence.dao.PosicionNombradaDivIntDao;

/**
 * @author <a href="mailto:ivan.kazakov@2hsoftware.com.mx">Ivan Kazakov</a>
 *
 */
@SuppressWarnings({"unchecked"})
public class PosicionNombradaDivIntDaoImpl extends BaseDaoHibernateImpl implements PosicionNombradaDivIntDao {

	/**
	 * String
	 */
	private static final String queryCountCuenta = "SELECT DISTINCT(pn.cupon.emision.idEmision) FROM "+
	PosicionNombrada.class.getName()+" pn WHERE pn.cuentaNombrada.idCuentaNombrada = :idCuentaNombrada";
	
	/**
	 * String 
	 */
	private static final String querySumCuenta = "SELECT SUM(pn.posicionDisponible) FROM "+
	PosicionNombrada.class.getName()+" pn WHERE pn.cuentaNombrada.idCuentaNombrada = :idCuentaNombrada";
	
	/**
	 * String
	 */
	private static final String queryCountInbur = "SELECT DISTINCT(pn.cupon.emision.idEmision) FROM "+
	PosicionNombrada.class.getName()+" pn WHERE ((pn.cuentaNombrada.cuenta LIKE '%97' AND pn.cupon.emision.emisionExtranjera = 'IADC') OR "+
	"(pn.cuentaNombrada.cuenta LIKE '%98' OR pn.cuentaNombrada.cuenta LIKE '54%')) "+ 
	"AND pn.cuentaNombrada.institucion.tipoInstitucion.claveTipoInstitucion < '04' "+
	"AND pn.cupon.emision.emisionExtranjera = 'IADC' " +
	"AND pn.cupon.estadoCupon.idEstatusCupon="+Constantes.ESTATUS_CUPON_VIGENTE;
	
	/**
	 * HibernateCallback 
	 */
	private static final HibernateCallback callbackInbursa = new HibernateCallback() {
		public Object doInHibernate(Session session) throws SQLException, HibernateException {
			Criteria criteria = session.createCriteria(PosicionNombrada.class);
			criteria.createAlias("cuentaNombrada", "cn");
			criteria.createAlias("cn.institucion", "i");
			criteria.createAlias("i.tipoInstitucion", "ti");
			criteria.createAlias("cupon", "c");
			criteria.createAlias("c.emision", "e");
			criteria.createAlias("c.estadoCupon", "ec");
			criteria.createAlias("e.instrumento","ei");
			criteria.createAlias("e.emisora", "ee");
					
			/* Sufijo de cuenta "97" y alta "IADC" */
			Junction suf97altaIADC = Restrictions.conjunction();
			suf97altaIADC.add(Restrictions.like("cn.cuenta", "%97"));
			suf97altaIADC.add(Restrictions.eq("e.emisionExtranjera", "IADC"));
			
			/* Sufijo de cuenta "98" o prefijo "54" */
			Junction suf98pref54 = Restrictions.disjunction();
			suf98pref54.add(Restrictions.like("cn.cuenta", "%98"));
			suf98pref54.add(Restrictions.like("cn.cuenta", "54%"));
			
			Junction cuentasAltas = Restrictions.disjunction();
			cuentasAltas.add(suf97altaIADC);
			cuentasAltas.add(suf98pref54);
			
			criteria.add(cuentasAltas);
			criteria.add(Restrictions.lt("ti.claveTipoInstitucion","04"));
			criteria.add(Restrictions.eq("e.emisionExtranjera", "IADC"));

			/* Estatus del cupon */
			criteria.add(Restrictions.eq("ec.idEstatusCupon",Constantes.ESTATUS_CUPON_VIGENTE));

			ProjectionList projectionList = Projections.projectionList();
			
			projectionList.add(Projections.property("e.idEmision"));
			projectionList.add(Projections.property("ei.claveTipoValor"));
			projectionList.add(Projections.property("ee.clavePizarra"));
			projectionList.add(Projections.property("e.serie"));
			projectionList.add(Projections.property("c.claveCupon"));
			projectionList.add(Projections.property("e.valorNominal"));
			projectionList.add(Projections.property("ti.claveTipoInstitucion"));
			projectionList.add(Projections.property("i.folioInstitucion"));
			projectionList.add(Projections.property("cn.cuenta"));
			projectionList.add(Projections.property("i.nombreCorto"));
			projectionList.add(Projections.property("posicionDisponible"));
			projectionList.add(Projections.property("e.emisionExtranjera"));
			
			criteria.setProjection(projectionList);
			
			criteria.addOrder(Order.asc("ti.claveTipoInstitucion"));
			criteria.addOrder(Order.asc("i.folioInstitucion"));
			criteria.addOrder(Order.asc("i.nombreCorto"));
			
			return (criteria.list());
		}
	};

	/**
	 * String
	 */
	private static final String querySumInbur = "SELECT SUM(pn.posicionDisponible) FROM "+
	PosicionNombrada.class.getName()+" pn WHERE ((pn.cuentaNombrada.cuenta LIKE '%97' AND pn.cupon.emision.emisionExtranjera = 'IADC') OR "+
	"(pn.cuentaNombrada.cuenta LIKE '%98' OR pn.cuentaNombrada.cuenta LIKE '54%')) "+ 
	"AND pn.cuentaNombrada.institucion.tipoInstitucion.claveTipoInstitucion < '04' "+
	"AND pn.cupon.emision.emisionExtranjera = 'IADC' " +
	"AND pn.cupon.estadoCupon.idEstatusCupon="+Constantes.ESTATUS_CUPON_VIGENTE;

	
	/**
	 * String
	 */
	private static final String queryCountNafin = "SELECT DISTINCT(pn.cupon.emision.idEmision) FROM "+
	PosicionNombrada.class.getName()+" pn WHERE (((pn.cuentaNombrada.cuenta LIKE '%97' AND pn.cupon.emision.emisionExtranjera = 'ADCP') OR "+
	"pn.cuentaNombrada.cuenta LIKE '%98' OR pn.cuentaNombrada.cuenta LIKE '54%') AND "+
	"pn.cuentaNombrada.institucion.tipoInstitucion.claveTipoInstitucion < '04' AND "+
	"(pn.cupon.emision.emisionExtranjera LIKE 'VI%' OR pn.cupon.emision.emisionExtranjera LIKE 'ADCP' OR pn.cupon.emision.emisionExtranjera LIKE 'CPOS')) "+
	"AND pn.cupon.estadoCupon.idEstatusCupon="+Constantes.ESTATUS_CUPON_VIGENTE;


	/**
	 * HibernateCallback 
	 */
	private static final HibernateCallback callbackNafinsa = new HibernateCallback() {
		public Object doInHibernate(Session session) throws SQLException, HibernateException {
			Criteria criteria = session.createCriteria(PosicionNombrada.class);
			criteria.createAlias("cuentaNombrada", "cn");
			criteria.createAlias("cn.institucion", "i");
			criteria.createAlias("i.tipoInstitucion", "ti");
			criteria.createAlias("cupon", "c");
			criteria.createAlias("c.emision", "e");
			criteria.createAlias("c.estadoCupon", "ec");
			criteria.createAlias("e.instrumento","ei");
			criteria.createAlias("e.emisora", "ee");
			
			/* Sufijo de cuenta "97" y alta "ADCP" */
			Junction suf97altaADCP = Restrictions.conjunction();
			suf97altaADCP.add(Restrictions.like("cn.cuenta", "%97"));
			suf97altaADCP.add(Restrictions.eq("e.emisionExtranjera", "ADCP"));
			
			/* Sufijo de cuenta "98" o prefijo "54" */
			Junction suf98pref54 = Restrictions.disjunction();
			suf98pref54.add(Restrictions.like("cn.cuenta", "%98"));
			suf98pref54.add(Restrictions.like("cn.cuenta", "54%"));
			
			Junction cuentasAltas = Restrictions.disjunction();
			cuentasAltas.add(suf97altaADCP);
			cuentasAltas.add(suf98pref54);
			
			/* Altas con prefijo "VI", o "ADCP" o "CPOS" */
			Junction altas = Restrictions.disjunction();
			altas.add(Restrictions.like("e.emisionExtranjera", "VI%"));
			altas.add(Restrictions.like("e.emisionExtranjera", "ADCP"));
			altas.add(Restrictions.like("e.emisionExtranjera", "CPOS"));
			
			criteria.add(cuentasAltas);
			criteria.add(altas);
			criteria.add(Restrictions.lt("ti.claveTipoInstitucion","04"));

			/* Estatus del cupon */
			criteria.add(Restrictions.eq("ec.idEstatusCupon",Constantes.ESTATUS_CUPON_VIGENTE));

			ProjectionList projectionList = Projections.projectionList();
			
			projectionList.add(Projections.property("e.idEmision"));
			projectionList.add(Projections.property("ei.claveTipoValor"));
			projectionList.add(Projections.property("ee.clavePizarra"));
			projectionList.add(Projections.property("e.serie"));
			projectionList.add(Projections.property("c.claveCupon"));
			projectionList.add(Projections.property("e.valorNominal"));
			projectionList.add(Projections.property("ti.claveTipoInstitucion"));
			projectionList.add(Projections.property("i.folioInstitucion"));
			projectionList.add(Projections.property("cn.cuenta"));
			projectionList.add(Projections.property("i.nombreCorto"));
			projectionList.add(Projections.property("posicionDisponible"));
			projectionList.add(Projections.property("e.emisionExtranjera"));
			
			criteria.setProjection(projectionList);

			criteria.addOrder(Order.asc("ti.claveTipoInstitucion"));
			criteria.addOrder(Order.asc("i.folioInstitucion"));
			criteria.addOrder(Order.asc("i.nombreCorto"));
			
			return (criteria.list());
		}
	};
	
	/**
	 * String
	 */
	private static final String querySumNafin = "SELECT SUM(pn.posicionDisponible) FROM "+
	PosicionNombrada.class.getName()+" pn WHERE (((pn.cuentaNombrada.cuenta LIKE '%97' AND pn.cupon.emision.emisionExtranjera = 'ADCP') OR "+
	"pn.cuentaNombrada.cuenta LIKE '%98' OR pn.cuentaNombrada.cuenta LIKE '54%') AND "+
	"pn.cuentaNombrada.institucion.tipoInstitucion.claveTipoInstitucion < '04' AND "+
	"(pn.cupon.emision.emisionExtranjera LIKE 'VI%' OR pn.cupon.emision.emisionExtranjera LIKE 'ADCP' OR pn.cupon.emision.emisionExtranjera LIKE 'CPOS')) "+
	"AND pn.cupon.estadoCupon.idEstatusCupon="+Constantes.ESTATUS_CUPON_VIGENTE;

	/**
	 * String
	 */
	private static final String queryCountVitro = "SELECT DISTINCT(pn.cupon.emision.idEmision) FROM "+
	PosicionNombrada.class.getName()+" pn WHERE ((pn.cuentaNombrada.cuenta LIKE '%97' OR "+
	"pn.cuentaNombrada.cuenta LIKE '%94') AND pn.cuentaNombrada.institucion.tipoInstitucion.claveTipoInstitucion < '04' AND "+
	"(pn.cupon.emision.emisionExtranjera LIKE 'VI%')) "+
	"AND pn.cupon.estadoCupon.idEstatusCupon="+Constantes.ESTATUS_CUPON_VIGENTE;


	/**
	 * String 
	 */
	private static final String querySumVitro = "SELECT SUM(pn.posicionDisponible) FROM "+
	PosicionNombrada.class.getName()+" pn WHERE ((pn.cuentaNombrada.cuenta LIKE '%97' OR "+
	"pn.cuentaNombrada.cuenta LIKE '%94') AND pn.cuentaNombrada.institucion.tipoInstitucion.claveTipoInstitucion < '04' AND "+
	"(pn.cupon.emision.emisionExtranjera LIKE 'VI%')) "+
	"AND pn.cupon.estadoCupon.idEstatusCupon="+Constantes.ESTATUS_CUPON_VIGENTE;

	
	/**
	 * String
	 */
	private static final String queryCountBanamex = "SELECT DISTINCT(pn.cupon.emision.idEmision) FROM "+
	PosicionNombrada.class.getName()+" pn WHERE (((pn.cuentaNombrada.cuenta LIKE '%97' AND "+
	"pn.cupon.emision.emisionExtranjera = 'BADC') OR pn.cuentaNombrada.cuenta LIKE '%98' OR "+
	"pn.cuentaNombrada.cuenta LIKE '54%') AND pn.cuentaNombrada.institucion.tipoInstitucion.claveTipoInstitucion < '04' AND "+
	"(pn.cupon.emision.emisionExtranjera LIKE 'BADC' OR pn.cupon.emision.emisionExtranjera LIKE 'BCPO')) "+
	"AND pn.cupon.estadoCupon.idEstatusCupon="+Constantes.ESTATUS_CUPON_VIGENTE;


	/**
	 * HibernateCallback
	 */
	private static final HibernateCallback callbackBanamex = new HibernateCallback() {
		public Object doInHibernate(Session session) throws SQLException, HibernateException {
			Criteria criteria = session.createCriteria(PosicionNombrada.class);
			criteria.createAlias("cuentaNombrada", "cn");
			criteria.createAlias("cn.institucion", "i");
			criteria.createAlias("i.tipoInstitucion", "ti");
			criteria.createAlias("cupon", "c");
			criteria.createAlias("c.emision", "e");
			criteria.createAlias("c.estadoCupon", "ec");
			criteria.createAlias("e.instrumento","ei");
			criteria.createAlias("e.emisora", "ee");
			
			/* Sufijo de cuenta "97" y alta "BADC" */
			Junction suf97altaBADC = Restrictions.conjunction();
			suf97altaBADC.add(Restrictions.like("cn.cuenta", "%97"));
			suf97altaBADC.add(Restrictions.eq("e.emisionExtranjera", "BADC"));
			
			/* Sufijo de cuenta "98" o prefijo "54" */
			Junction suf98pref54 = Restrictions.disjunction();
			suf98pref54.add(Restrictions.like("cn.cuenta", "%98"));
			suf98pref54.add(Restrictions.like("cn.cuenta", "54%"));
			
			Junction cuentasAltas = Restrictions.disjunction();
			cuentasAltas.add(suf97altaBADC);
			cuentasAltas.add(suf98pref54);
			
			/* Altas "BADC" o "BCPO" */
			Junction altas = Restrictions.disjunction();
			altas.add(Restrictions.like("e.emisionExtranjera", "BADC"));
			altas.add(Restrictions.like("e.emisionExtranjera", "BCPO"));
			
			criteria.add(cuentasAltas);
			criteria.add(altas);
			criteria.add(Restrictions.lt("ti.claveTipoInstitucion","04"));

			/* Estatus del cupon */
			criteria.add(Restrictions.eq("ec.idEstatusCupon",Constantes.ESTATUS_CUPON_VIGENTE));

			ProjectionList projectionList = Projections.projectionList();
			
			projectionList.add(Projections.property("e.idEmision"));
			projectionList.add(Projections.property("ei.claveTipoValor"));
			projectionList.add(Projections.property("ee.clavePizarra"));
			projectionList.add(Projections.property("e.serie"));
			projectionList.add(Projections.property("c.claveCupon"));
			projectionList.add(Projections.property("e.valorNominal"));
			projectionList.add(Projections.property("ti.claveTipoInstitucion"));
			projectionList.add(Projections.property("i.folioInstitucion"));
			projectionList.add(Projections.property("cn.cuenta"));
			projectionList.add(Projections.property("i.nombreCorto"));
			projectionList.add(Projections.property("posicionDisponible"));
			projectionList.add(Projections.property("e.emisionExtranjera"));
			
			criteria.setProjection(projectionList);

			criteria.addOrder(Order.asc("ti.claveTipoInstitucion"));
			criteria.addOrder(Order.asc("i.folioInstitucion"));
			criteria.addOrder(Order.asc("i.nombreCorto"));
			
			return (criteria.list());
		}
	};

	/**
	 * String 
	 */
	private static final String querySumBanamex = "SELECT SUM(pn.posicionDisponible) FROM "+
	PosicionNombrada.class.getName()+" pn WHERE (((pn.cuentaNombrada.cuenta LIKE '%97' AND "+
	"pn.cupon.emision.emisionExtranjera = 'BADC') OR pn.cuentaNombrada.cuenta LIKE '%98' OR "+
	"pn.cuentaNombrada.cuenta LIKE '54%') AND pn.cuentaNombrada.institucion.tipoInstitucion.claveTipoInstitucion < '04' AND "+
	"(pn.cupon.emision.emisionExtranjera LIKE 'BADC' OR pn.cupon.emision.emisionExtranjera LIKE 'BCPO')) "+
	"AND pn.cupon.estadoCupon.idEstatusCupon="+Constantes.ESTATUS_CUPON_VIGENTE;




	/**
	 * @see com.indeval.portalinternacional.persistence.dao.PosicionNombradaDivIntDao#countNumeroEmisionesPosicionPorCuentaNombrada(com.indeval.portaldali.persistence.modelo.CuentaNombrada)
	 */
	public Long countNumeroEmisionesPosicionPorCuentaNombrada(final Long idCuentaNombrada, final boolean posicionDisponibleNoCero) {
		HibernateCallback hibernateCallback = new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(queryCountCuenta+((posicionDisponibleNoCero)?" AND pn.posicionDisponible > 0":""));
				query.setParameter("idCuentaNombrada", idCuentaNombrada);
				List<Long> results = (List<Long>)query.list();
				return (new Long(results.size()));
			}
		};
		return ((Long)this.getHibernateTemplate().execute(hibernateCallback));
	}

	/**
	 * @see com.indeval.portalinternacional.persistence.dao.PosicionNombradaDivIntDao#sumPosicionDisponiblePorCuentaNombrada(com.indeval.portaldali.persistence.modelo.CuentaNombrada)
	 */
	public BigInteger sumPosicionDisponiblePorCuentaNombrada(final Long idCuentaNombrada, final boolean posicionDisponibleNoCero) {
		HibernateCallback hibernateCallback = new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(querySumCuenta+((posicionDisponibleNoCero)?" AND pn.posicionDisponible > 0":""));
				query.setParameter("idCuentaNombrada", idCuentaNombrada);
				return (query.uniqueResult());
			}
		};
		return ((BigInteger)this.getHibernateTemplate().execute(hibernateCallback));
	}

	/**
	 * @see com.indeval.portalinternacional.persistence.dao.PosicionNombradaDivIntDao#countNumeroEmisionesInbur()
	 */
	public Long countNumeroEmisionesInbur() {
		HibernateCallback hibernateCallback = new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(queryCountInbur);
				List<Long> results = (List<Long>)query.list();
				return (new Long(results.size()));
			}
		};
		return ((Long)this.getHibernateTemplate().execute(hibernateCallback));
	}

	/**
	 * @see com.indeval.portalinternacional.persistence.dao.PosicionNombradaDivIntDao#countNumeroEmisionesNafin()
	 */
	public Long countNumeroEmisionesNafin() {
		HibernateCallback hibernateCallback = new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(queryCountNafin);
				List<Long> results = (List<Long>)query.list();
				return (new Long(results.size()));
			}
		};
		return ((Long)this.getHibernateTemplate().execute(hibernateCallback));
	}

	/**
	 * @see com.indeval.portalinternacional.persistence.dao.PosicionNombradaDivIntDao#countNumeroEmisionesVitro()
	 */
	public Long countNumeroEmisionesVitro() {
		HibernateCallback hibernateCallback = new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(queryCountVitro);
				List<Long> results = (List<Long>)query.list();
				return (new Long(results.size()));
			}
		};
		return ((Long)this.getHibernateTemplate().execute(hibernateCallback));
	}

	/**
	 * @see com.indeval.portalinternacional.persistence.dao.PosicionNombradaDivIntDao#countNumeroEmisionesBanamex()
	 */
	public Long countNumeroEmisionesBanamex() {
		HibernateCallback hibernateCallback = new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(queryCountBanamex);
				List<Long> results = (List<Long>)query.list();
				return (new Long(results.size()));
			}
		};
		return ((Long)this.getHibernateTemplate().execute(hibernateCallback));
	}
	
	/**
	 * @see com.indeval.portalinternacional.persistence.dao.PosicionNombradaDivIntDao#sumPosicionDisponibleInbur()
	 */
	public BigInteger sumPosicionDisponibleInbur() {
		HibernateCallback hibernateCallback = new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(querySumInbur);
				return (query.uniqueResult());
			}
		};
		return ((BigInteger)this.getHibernateTemplate().execute(hibernateCallback));
	}

	/**
	 * @see com.indeval.portalinternacional.persistence.dao.PosicionNombradaDivIntDao#sumPosicionDisponibleNafin()
	 */
	public BigInteger sumPosicionDisponibleNafin() {
		HibernateCallback hibernateCallback = new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(querySumNafin);
				return (query.uniqueResult());
			}
		};
		return ((BigInteger)this.getHibernateTemplate().execute(hibernateCallback));
	}

	/**
	 * @see com.indeval.portalinternacional.persistence.dao.PosicionNombradaDivIntDao#sumPosicionDisponibleVitro()
	 */
	public BigInteger sumPosicionDisponibleVitro() {
		HibernateCallback hibernateCallback = new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(querySumVitro);
				return (query.uniqueResult());
			}
		};
		return ((BigInteger)this.getHibernateTemplate().execute(hibernateCallback));
	}

	/**
	 * @see com.indeval.portalinternacional.persistence.dao.PosicionNombradaDivIntDao#sumPosicionDisponibleBanamex()
	 */
	public BigInteger sumPosicionDisponibleBanamex() {
		HibernateCallback hibernateCallback = new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(querySumBanamex);
				return (query.uniqueResult());
			}
		};
		return ((BigInteger)this.getHibernateTemplate().execute(hibernateCallback));
	}
	
	/**
	 * @see com.indeval.portalinternacional.persistence.dao.PosicionNombradaDivIntDao#consultaPosicionesFideicomiso(com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO)
	 */
	public List<Object> consultaPosicionesFideicomiso(AgenteVO agenteVO) {
	
		/* Validamos los parametros */
		if ((agenteVO == null) || (!agenteVO.tieneClave())) {
			throw new IllegalArgumentException("Agente invalido");
		}
		
		/* Discriminamos por agente */
		String claveAgente = agenteVO.getClave();
		if (claveAgente.equals(Constantes.NAFINSA)) {
			return ((List<Object>)this.getHibernateTemplate().execute(callbackNafinsa));
		}
		else if (claveAgente.equals(Constantes.INBURSA)) {
			return ((List<Object>)this.getHibernateTemplate().execute(callbackInbursa));
		}
		else if (claveAgente.equals(Constantes.BANAMEX)) {
			return ((List<Object>)this.getHibernateTemplate().execute(callbackBanamex));
		}
		return (null);
	}

}
