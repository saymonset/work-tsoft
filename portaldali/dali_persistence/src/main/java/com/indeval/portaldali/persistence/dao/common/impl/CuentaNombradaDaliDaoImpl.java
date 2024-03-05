/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.dao.common.impl;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.util.Assert;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portaldali.middleware.dto.CuentaDTO;
import com.indeval.portaldali.middleware.dto.util.DTOAssembler;
import com.indeval.portaldali.persistence.dao.common.CuentaNombradaDaliDao;
import com.indeval.portaldali.persistence.model.CuentaNombrada;
import com.indeval.portaldali.persistence.vo.AgentePK;
import com.indeval.portaldali.persistence.vo.PageVO;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class CuentaNombradaDaliDaoImpl extends BaseDaoHibernateImpl implements CuentaNombradaDaliDao {

	/** Log de clase. */
	private static final Logger logger = LoggerFactory.getLogger(CuentaNombradaDaliDaoImpl.class);

	/*
	 * (non-Javadoc)
	 * @see com.indeval.portaldali.persistence.dao.common.CuentaNombradaDaliDao#obtenerCuentaNombradaPorIdInstitucionCuenta(java.lang.Long, java.lang.String)
	 */
	public CuentaNombrada obtenerCuentaNombradaPorIdInstitucionCuenta(final Long idInstitucion, final String cuenta) {
		CuentaNombrada cuentaNombrada = (CuentaNombrada) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(CuentaNombrada.class);
				criteria.createAlias("institucion", "i");
				criteria.add(Restrictions.eq("i.idInstitucion", new BigInteger(idInstitucion.toString())));
				criteria.add(Restrictions.eq("cuenta", cuenta));
				return criteria.uniqueResult();
			}
		});
		return cuentaNombrada;
	}
	
	/**
	 * @see com.indeval.portaldali.persistence.dao.common.CuentaNombradaDaliDao#getCuentaNombrada(com.indeval.portaldali.persistence.vo.AgentePK,
	 *      com.indeval.persistence.portallegado.vo.PageVO)
	 */
	public PageVO getCuentaNombrada(final AgentePK agentePK, final PageVO pageVO) {

		logger.trace("Entrando a CuentaNombradaDaliDaoImpl.getCCuentaNombrada()");

		Assert.notNull(agentePK,
						"No se recibieron los datos de la institucion");
		Assert.isTrue(StringUtils.isNotBlank(agentePK.getIdInst()),
				"No se recibio el id de la institucion");
		Assert.isTrue(StringUtils.isNotBlank(agentePK.getFolioInst()),
				"No se recibio el folio de la institucion");

		final PageVO pagAux = pageVO != null ? pageVO : new PageVO();

		List listaTPosicionNombrada = null;

		final Integer countRegistros = (Integer) getHibernateTemplate().execute(
				new HibernateCallback() {

					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {

						Criteria criteria = session.createCriteria(
								CuentaNombrada.class).setProjection(
								Projections.rowCount()).createAlias(
								"institucion", "i").createAlias(
								"i.tipoInstitucion", "ti") .createAlias("tipoCuenta", "tc");

						if (StringUtils.isNotBlank(agentePK.getIdInst())) {
							criteria.add(Restrictions.eq(
									"ti.claveTipoInstitucion", agentePK
											.getIdInst().trim()));
						}

						if (StringUtils.isNotBlank(agentePK.getFolioInst())) {
							criteria.add(Restrictions.eq("i.folioInstitucion",
									agentePK.getFolioInst().trim()));
						}
						if (StringUtils.isNotBlank(agentePK.getCuenta())) {
							System.out.println("agentePK.getCuenta()");
							criteria.add(Restrictions.eq("cuenta",
									agentePK.getCuenta().trim()));
							criteria.add(Restrictions.in("tc.tipoTenencia",new String[]{"C","E"}));							
						}
						logger.debug("criteria: " + criteria);
						@SuppressWarnings("rawtypes")
						final List lst=criteria.list();

						return ((Integer) (( lst!= null && !lst.isEmpty()) ? lst.get(0)
								: new Integer(0))).intValue();
					}

				});

		logger.debug("Registros encontrados: [" + countRegistros + "]");

		if (countRegistros.intValue() > 0) {

			listaTPosicionNombrada = (List) getHibernateTemplate().execute(
					new HibernateCallback() {

						public Object doInHibernate(Session session)
								throws HibernateException, SQLException {

							Criteria criteria = session.createCriteria(
									CuentaNombrada.class).createAlias(
									"institucion", "i").createAlias(
									"i.tipoInstitucion", "ti")
									.createAlias("tipoCuenta", "tc");

							if (StringUtils.isNotBlank(agentePK.getIdInst())) {
								criteria.add(Restrictions.eq(
										"ti.claveTipoInstitucion", agentePK
												.getIdInst().trim()));
							}

							if (StringUtils.isNotBlank(agentePK.getFolioInst())) {
								criteria.add(Restrictions.eq(
										"i.folioInstitucion", agentePK
												.getFolioInst().trim()));
							}
							if (StringUtils.isNotBlank(agentePK.getCuenta())) {
								criteria.add(Restrictions.eq(
										"cuenta", agentePK
												.getCuenta().trim()));
								criteria.add(Restrictions.in("tc.tipoTenencia",new String[]{"C","E"}));
							}
							if (pagAux.getRegistrosXPag().intValue() > 0) {
								criteria.setFirstResult(pagAux.getOffset()
										.intValue());
								criteria.setMaxResults(pagAux
										.getRegistrosXPag().intValue());
							}
							return criteria.list();
						}

					});
		}

		PageVO pageReturn = new PageVO();
		pageReturn.setTotalRegistros(countRegistros);
		pageReturn.setRegistros(listaTPosicionNombrada);

		return pageReturn;

	}

	/*
	 * (non-Javadoc)
	 * @see com.indeval.portaldali.persistence.dao.common.CuentaNombradaDaliDao#obtenerCuentaNombradaInstitucion(com.indeval.portaldali.persistence.vo.AgentePK)
	 */
	@SuppressWarnings("unchecked")
	public CuentaDTO obtenerCuentaNombradaInstitucion(
			final AgentePK agentePK, final String tipoCustodia, final String naturaleza) {
		CuentaDTO cuentaDTO = null;
		Assert.notNull(agentePK, "No se recibieron los datos de la institucion");
		Assert.isTrue(StringUtils.isNotBlank(agentePK.getIdInst()), "No se recibio el id de la institucion");
		Assert.isTrue(StringUtils.isNotBlank(agentePK.getFolioInst()), "No se recibio el folio de la institucion");
		Assert.isTrue(StringUtils.isNotBlank(agentePK.getCuenta()), "No se recibio la cuenta");
		Assert.isTrue(StringUtils.isNotBlank(tipoCustodia), "No se recibio el tipo de custodia");
		Assert.isTrue(StringUtils.isNotBlank(naturaleza), "No se recibio la naturaleza contable");
		List<CuentaNombrada> cuentasNombrada = 
			(List<CuentaNombrada>) getHibernateTemplate().execute(new HibernateCallback() {
				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					Criteria criteria = session.createCriteria(CuentaNombrada.class).
						createAlias("institucion", "i").
						createAlias("i.tipoInstitucion", "ti").
						createAlias("tipoCuenta", "tc");
					if (StringUtils.isNotBlank(agentePK.getIdInst())) {
						criteria.add(
							Restrictions.eq("ti.claveTipoInstitucion", agentePK.getIdInst().trim()));
					}
					if (StringUtils.isNotBlank(agentePK.getFolioInst())) {
						criteria.add(Restrictions.eq("i.folioInstitucion", agentePK.getFolioInst().trim()));
					}
					if (StringUtils.isNotBlank(agentePK.getCuenta())) {
						criteria.add(Restrictions.eq("cuenta", agentePK.getCuenta().trim()));
						criteria.add(Restrictions.in("tc.tipoTenencia", new String[] { "C", "E" }));
					}
					if(StringUtils.isNotBlank(tipoCustodia)) {
						criteria.add(Restrictions.eq("tc.tipoCustodia", tipoCustodia.trim()));
					}
					if(StringUtils.isNotBlank(naturaleza)) {
						criteria.add(Restrictions.eq("tc.naturalezaContable", naturaleza.trim()));
					}
					return criteria.list();
				}
			});
		if(cuentasNombrada != null && !cuentasNombrada.isEmpty() && cuentasNombrada.size() == 1) {
			cuentaDTO = DTOAssembler.crearCuentaDTO(cuentasNombrada.get(0));
		}
		return cuentaDTO;
	}
}
