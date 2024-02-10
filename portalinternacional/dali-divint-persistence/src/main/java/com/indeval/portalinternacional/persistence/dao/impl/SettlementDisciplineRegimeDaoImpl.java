package com.indeval.portalinternacional.persistence.dao.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.dto.SettlementDisciplineRegimeVO;
import com.indeval.portalinternacional.middleware.servicios.modelo.LiquidacionParcialMoi;
import com.indeval.portalinternacional.middleware.servicios.modelo.SettlementDisciplineRegime;
import com.indeval.portalinternacional.persistence.dao.SettlementDisciplineRegimeDao;

public class SettlementDisciplineRegimeDaoImpl extends BaseDaoHibernateImpl implements SettlementDisciplineRegimeDao {
	
	private static final Logger log = LoggerFactory.getLogger(SettlementDisciplineRegimeDaoImpl.class);
	
    /** Definicion de la variable para queries JDBC */
    private JdbcTemplate jdbcTemplate;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<SettlementDisciplineRegimeVO> getSettlementDisciplineRegimeForCustodio() {
		log.debug("SettlementDisciplineRegimeDaoImpl :: getSettlementDisciplineRegimeForCustodio");

		final StringBuilder query = new StringBuilder();
		query.append(" SELECT CCC.ID_CONFIG_CSDR as idConfigCsdr, CCC.ID_CUENTA_BOVEDA as idCuentaBoveda, CCC.CUENTA as cuenta, CCC.DETALLE_CUSTODIO as detalleCustodio, ");
		query.append(" CCC.HOLD_RELEASE as holdAndRelease, CCC.PARTIAL_SETTLEMENT as partialSettlement, CCC.BILATERAL_CANCELLATION as bilateralCancellation ");
		query.append(" FROM T_CONFIG_CSDR CCC");
		query.append(" WHERE ID_INSTITUCION IS NULL");
		query.append(" ORDER BY CCC.DETALLE_CUSTODIO ");

		return (List) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery sqlQuery = session.createSQLQuery(query.toString());

				sqlQuery.addScalar("idConfigCsdr", Hibernate.LONG);
				sqlQuery.addScalar("idCuentaBoveda", Hibernate.LONG);
				sqlQuery.addScalar("cuenta", Hibernate.LONG);
				sqlQuery.addScalar("detalleCustodio", Hibernate.STRING);
				sqlQuery.addScalar("holdAndRelease", Hibernate.INTEGER);
				sqlQuery.addScalar("partialSettlement", Hibernate.INTEGER);
				sqlQuery.addScalar("bilateralCancellation", Hibernate.INTEGER);

				sqlQuery.setResultTransformer(Transformers.aliasToBean(SettlementDisciplineRegimeVO.class));
				return sqlQuery.list();
			}
		});
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<SettlementDisciplineRegimeVO> getSettlementDisciplineRegimeForInstitucion() {
		log.debug("SettlementDisciplineRegimeDaoImpl :: getSettlementDisciplineRegimeForInstitucion");

		final StringBuilder query = new StringBuilder();
		query.append(" SELECT CCC.ID_CONFIG_CSDR as idConfigCsdr, CCC.ID_INSTITUCION as idInstitucion, lpad(CI.ID_TIPO_INSTITUCION, 2, '0') || CI.FOLIO_INSTITUCION as folioInstitucion, CI.NOMBRE_CORTO as nombreCortoInstitucion, ");
		query.append(" CCC.HOLD_RELEASE as holdAndRelease, CCC.PARTIAL_SETTLEMENT as partialSettlement, CCC.BILATERAL_CANCELLATION as bilateralCancellation");
		query.append(" FROM T_CONFIG_CSDR CCC, C_INSTITUCION CI");
		query.append(" WHERE CI.ID_INSTITUCION = CCC.ID_INSTITUCION ");
		query.append(" ORDER BY CCC.DETALLE_CUSTODIO ");

		return (List) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery sqlQuery = session.createSQLQuery(query.toString());

				sqlQuery.addScalar("idConfigCsdr", Hibernate.LONG);
				sqlQuery.addScalar("idInstitucion", Hibernate.LONG);
				sqlQuery.addScalar("folioInstitucion", Hibernate.STRING);
				sqlQuery.addScalar("nombreCortoInstitucion", Hibernate.STRING);
				sqlQuery.addScalar("holdAndRelease", Hibernate.INTEGER);
				sqlQuery.addScalar("partialSettlement", Hibernate.INTEGER);
				sqlQuery.addScalar("bilateralCancellation", Hibernate.INTEGER);

				sqlQuery.setResultTransformer(Transformers.aliasToBean(SettlementDisciplineRegimeVO.class));
				return sqlQuery.list();
			}
		});
	}
	
	@Override
	public SettlementDisciplineRegimeVO getSettlementDisciplineRegimeByID(final Long idConfigCsdr) {
		log.debug("SettlementDisciplineRegimeDaoImpl :: getSettlementDisciplineRegimeByID: " + idConfigCsdr);
		final StringBuilder query = new StringBuilder();
		query.append(" SELECT CCC.ID_CONFIG_CSDR as idConfigCsdr, CCC.ID_CUENTA_BOVEDA as idCuentaBoveda, CCC.CUENTA as cuenta, CCC.DETALLE_CUSTODIO as detalleCustodio, ");
		query.append(" CCC.HOLD_RELEASE as holdAndRelease, CCC.PARTIAL_SETTLEMENT as partialSettlement, CCC.BILATERAL_CANCELLATION as bilateralCancellation ");
		query.append(" FROM T_CONFIG_CSDR CCC");
		query.append(" WHERE CCC.ID_CONFIG_CSDR = " + idConfigCsdr);
		query.append(" ORDER BY CCC.DETALLE_CUSTODIO ");

		return (SettlementDisciplineRegimeVO) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery sqlQuery = session.createSQLQuery(query.toString());

				sqlQuery.addScalar("idConfigCsdr", Hibernate.LONG);
				sqlQuery.addScalar("idCuentaBoveda", Hibernate.LONG);
				sqlQuery.addScalar("cuenta", Hibernate.LONG);
				sqlQuery.addScalar("detalleCustodio", Hibernate.STRING);
				sqlQuery.addScalar("holdAndRelease", Hibernate.INTEGER);
				sqlQuery.addScalar("partialSettlement", Hibernate.INTEGER);
				sqlQuery.addScalar("bilateralCancellation", Hibernate.INTEGER);

				sqlQuery.setResultTransformer(Transformers.aliasToBean(SettlementDisciplineRegimeVO.class));
				return sqlQuery.uniqueResult();
			}
		});
	}
	
	@Override
	public SettlementDisciplineRegime getModelSettlementDisciplineRegimeByID(Long idConfigCsdr) {
		log.debug("SettlementDisciplineRegimeDaoImpl :: getModelSettlementDisciplineRegimeByID: " + idConfigCsdr);
		final StringBuilder query = new StringBuilder();
		query.append(" SELECT " + SettlementDisciplineRegime.class.getName() + " sdr");
		query.append(" WHERE sdr.idConfigCsdr = " + idConfigCsdr);

		return (SettlementDisciplineRegime) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query sqlQuery = session.createQuery(query.toString());
				return sqlQuery.uniqueResult();
			}
		});
	}

	@Override
	public void modificarSettlementDisciplineRegime(SettlementDisciplineRegimeVO settlementDisciplineRegime) {
		log.debug("SettlementDisciplineRegimeDaoImpl :: modificarSettlementDisciplineRegime");
		Query query = getSession().createQuery("UPDATE SettlementDisciplineRegime cdr SET cdr.holdAndRelease = :holdAndRelease, cdr.partialSettlement = :partialSettlement, cdr.bilateralCancellation = :bilateralCancellation, cdr.fechaUltimaModificacion = SYSDATE where cdr.idConfigCsdr = :idConfigCsdr");
		query.setLong("holdAndRelease", settlementDisciplineRegime.getHoldAndRelease());
		query.setLong("partialSettlement", settlementDisciplineRegime.getPartialSettlement());
		query.setLong("bilateralCancellation", settlementDisciplineRegime.getBilateralCancellation());
		query.setLong("idConfigCsdr", settlementDisciplineRegime.getIdConfigCsdr());
		query.executeUpdate();
	}
	
	@Override
	public void eliminaSettlementDisciplineRegime(SettlementDisciplineRegime settlementDisciplineRegime) {
		log.debug("SettlementDisciplineRegimeDaoImpl :: eliminaSettlementDisciplineRegime");
		Query query = getSession().createQuery("DELETE FROM SettlementDisciplineRegime cdr where cdr.idConfigCsdr = :idConfigCsdr");
		query.setLong("idConfigCsdr", settlementDisciplineRegime.getIdConfigCsdr());
		query.executeUpdate();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<SettlementDisciplineRegimeVO> getCustodiosPendSettlementDisciplineRegime() {
		log.debug("SettlementDisciplineRegimeDaoImpl :: getCustodiosPendSettlementDisciplineRegime");

		final StringBuilder query = new StringBuilder();
		query.append(" SELECT DISTINCT CCB.ID_CUENTA_NOMBRADA as idCuentaBoveda, CCN.CUENTA as cuenta, CCB.DETALLE_CUSTODIO as detalleCustodio ");
		query.append(" FROM C_CATBIC CCB, C_CUENTA_NOMBRADA CCN ");
		query.append(" WHERE CCB.ID_CUENTA_NOMBRADA = CCN.ID_CUENTA_NOMBRADA ");
		query.append(" AND CCB.ESTATUS_REGISTRO = 'VIGENTE' ");
		query.append(" AND CCB.ID_CUENTA_NOMBRADA NOT IN (SELECT CCS.ID_CUENTA_BOVEDA FROM T_CONFIG_CSDR CCS WHERE CCS.ID_INSTITUCION IS NULL) ");
		query.append(" ORDER BY CCB.DETALLE_CUSTODIO ");

		return (List) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery sqlQuery = session.createSQLQuery(query.toString());

				sqlQuery.addScalar("idCuentaBoveda", Hibernate.LONG);
				sqlQuery.addScalar("cuenta", Hibernate.LONG);
				sqlQuery.addScalar("detalleCustodio", Hibernate.STRING);

				sqlQuery.setResultTransformer(Transformers.aliasToBean(SettlementDisciplineRegimeVO.class));
				return sqlQuery.list();
			}
		});
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<SettlementDisciplineRegimeVO> getInstitucionesPendSettlementDisciplineRegime() {
		log.debug("SettlementDisciplineRegimeDaoImpl :: getInstitucionesPendSettlementDisciplineRegime");

		final StringBuilder query = new StringBuilder();
		query.append(" SELECT CI.ID_INSTITUCION as idInstitucion, lpad(CI.ID_TIPO_INSTITUCION, 2, '0') || CI.FOLIO_INSTITUCION as folioInstitucion, CI.NOMBRE_CORTO as nombreCortoInstitucion");
		query.append(" FROM C_INSTITUCION CI ");
		query.append(" WHERE CI.ID_INSTITUCION NOT IN (SELECT ID_INSTITUCION FROM T_CONFIG_CSDR CCS WHERE ID_INSTITUCION IS NOT NULL) ");
		query.append(" ORDER BY lpad(CI.ID_TIPO_INSTITUCION, 2, '0') || CI.FOLIO_INSTITUCION ");

		return (List) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery sqlQuery = session.createSQLQuery(query.toString());

				sqlQuery.addScalar("idInstitucion", Hibernate.LONG);
				sqlQuery.addScalar("folioInstitucion", Hibernate.STRING);
				sqlQuery.addScalar("nombreCortoInstitucion", Hibernate.STRING);

				sqlQuery.setResultTransformer(Transformers.aliasToBean(SettlementDisciplineRegimeVO.class));
				return sqlQuery.list();
			}
		});
	}
	
	@Override
	public Boolean findConfiguration(SettlementDisciplineRegime settlementDisciplineRegime) {
		log.debug("SettlementDisciplineRegimeDaoImpl :: findSettlementDisciplineRegime");
		Boolean existeCustodio = Boolean.FALSE;
		final StringBuilder query = new StringBuilder();
		query.append(" SELECT COUNT(sdr.ID_CONFIG_CSDR) as idConfigCsdr ");
		query.append(" FROM T_CONFIG_CSDR sdr");
		if(settlementDisciplineRegime.getIdInstitucion() != null){
			query.append(" WHERE sdr.ID_INSTITUCION = " + settlementDisciplineRegime.getIdInstitucion());
		} else {
			query.append(" WHERE sdr.ID_CUENTA_BOVEDA = " + settlementDisciplineRegime.getIdCuentaBoveda());
			query.append(" AND sdr.CUENTA = " + settlementDisciplineRegime.getCuenta());
			query.append(" AND sdr.DETALLE_CUSTODIO = '" + settlementDisciplineRegime.getDetalleCustodio() + "'");
		}

		List lstConfiguracion = (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery sqlQuery = session.createSQLQuery(query.toString());
				return sqlQuery.list();
			}
		});

		if(lstConfiguracion != null && lstConfiguracion.size() == 1){
			BigDecimal total = (BigDecimal) lstConfiguracion.get(0);
			if(total.intValue() > 0){
				existeCustodio = Boolean.TRUE;
			}
		}

		return existeCustodio;
	}

	@Override
	public void saveCustodioSettlementDisciplineRegime(final SettlementDisciplineRegime settlementDisciplineRegime) {
		log.debug("SettlementDisciplineRegimeDaoImpl :: saveCustodioSettlementDisciplineRegime");
		
		final StringBuffer query = new StringBuffer();
		query.append(" INSERT INTO T_CONFIG_CSDR (ID_CONFIG_CSDR, ID_CUENTA_BOVEDA, CUENTA, DETALLE_CUSTODIO, HOLD_RELEASE, PARTIAL_SETTLEMENT, BILATERAL_CANCELLATION, FECHA_ALTA) ");
		query.append(" VALUES (C_CONFIG_CSDR_ID.NEXTVAL, :idCuentaBoveda, :cuenta, :detalleCustodio, :holdAndRelease, :partialSettlement, :bilateralCancellation, SYSDATE) ");

		getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery sqlQuery = session.createSQLQuery(query.toString());
				sqlQuery.setLong("idCuentaBoveda", settlementDisciplineRegime.getIdCuentaBoveda());
				sqlQuery.setLong("cuenta", settlementDisciplineRegime.getCuenta());
				sqlQuery.setString("detalleCustodio", settlementDisciplineRegime.getDetalleCustodio());
				sqlQuery.setBoolean("holdAndRelease", settlementDisciplineRegime.getHoldAndRelease());
				sqlQuery.setBoolean("partialSettlement", settlementDisciplineRegime.getPartialSettlement());
				sqlQuery.setBoolean("bilateralCancellation", settlementDisciplineRegime.getBilateralCancellation());
				return sqlQuery.executeUpdate();
			}
		});
	}
	
	@Override
	public void saveConfiguracionForInstitucion(final SettlementDisciplineRegime settlementDisciplineRegime) {
		log.debug("SettlementDisciplineRegimeDaoImpl :: saveConfiguracionForInstitucion");
		
		final StringBuffer query = new StringBuffer();
		query.append(" INSERT INTO T_CONFIG_CSDR (ID_CONFIG_CSDR, ID_INSTITUCION, HOLD_RELEASE, PARTIAL_SETTLEMENT, BILATERAL_CANCELLATION, FECHA_ALTA) ");
		query.append(" VALUES (C_CONFIG_CSDR_ID.NEXTVAL, :idInstitucion, :holdAndRelease, :partialSettlement, :bilateralCancellation, SYSDATE) ");

		getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery sqlQuery = session.createSQLQuery(query.toString());
				sqlQuery.setLong("idInstitucion", settlementDisciplineRegime.getIdInstitucion());
				sqlQuery.setBoolean("holdAndRelease", settlementDisciplineRegime.getHoldAndRelease());
				sqlQuery.setBoolean("partialSettlement", settlementDisciplineRegime.getPartialSettlement());
				sqlQuery.setBoolean("bilateralCancellation", settlementDisciplineRegime.getBilateralCancellation());
				return sqlQuery.executeUpdate();
			}
		});
	}
	
	@Override
	public void autorizaCustodioCSDR(SettlementDisciplineRegimeVO settlementDisciplineRegime) {
		log.debug("SettlementDisciplineRegimeDaoImpl :: autorizaCustodioCSDR");
		Query query = getSession().createQuery("UPDATE SettlementDisciplineRegime cdr SET cdr.fechaAutorizacion = SYSDATE where cdr.idConfigCsdr = :idConfigCsdr");
		query.setLong("idConfigCsdr", settlementDisciplineRegime.getIdConfigCsdr());
		query.executeUpdate();	
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LiquidacionParcialMoi> getLiqParcialMoi(Long folioControl) {
		log.info("OperacionSicDaoImpl :: getLiqParcialMoi");
        final StringBuilder sb = new StringBuilder();
        
        sb.append(" FROM " + LiquidacionParcialMoi.class.getName() + " liqParcial ");
        sb.append(" WHERE liqParcial.folioControl = " + folioControl);
        sb.append(" ORDER BY liqParcial.idLiquidacionParcial ");

        return (List<LiquidacionParcialMoi>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createQuery(sb.toString());
                return query.list();
            }
        });

	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public PaginaVO getLiqParcialMoi(final PaginaVO paginaVO, final Long folioControl) {
		log.info("OperacionSicDaoImpl :: getLiqParcialMoi(PaginaVO paginaVO, Long folioControl)");
		Integer countResult = 0;
		List result = null;
		
		final StringBuilder sb = new StringBuilder();
        sb.append(" FROM " + LiquidacionParcialMoi.class.getName() + " liqParcial ");
        sb.append(" WHERE liqParcial.folioControl = " + folioControl);
        sb.append(" ORDER BY liqParcial.idLiquidacionParcial ");

        result = (List) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createQuery(sb.toString());
                return query.list();
            }
        });
        
		if (paginaVO.getRegistrosXPag() == PaginaVO.TODOS) {
			countResult = result.size();
		}
		
		paginaVO.setTotalRegistros(countResult);
		paginaVO.setRegistros(result);
		return paginaVO;

	}
	
	@Override
	public LiquidacionParcialMoi getLiqParcialMoiByFolioControlAndParcialidad(Long folioControl, Long numeroParcialidad) {
		log.info("OperacionSicDaoImpl :: getLiqParcialMoiByFolioControlAndParcialidad: " + folioControl);
		final StringBuilder sb = new StringBuilder();
        sb.append(" FROM " + LiquidacionParcialMoi.class.getName() + " liqParcial ");
        sb.append(" WHERE liqParcial.folioControl = " + folioControl);
        sb.append(" AND liqParcial.numeroLiquidacion = " + numeroParcialidad);
        sb.append(" ORDER BY liqParcial.numeroLiquidacion ");

        return (LiquidacionParcialMoi) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createQuery(sb.toString());
                return query.uniqueResult();
            }
        });
	}

	@Override
	public void actualizaLiquidacionesParciales(final Long folioControl, final Long numeroLiquidacion, final Long idEstatusOperacion) {
		log.info("OperacionSicDaoImpl :: liberarLiquidacionesParciales");
        final StringBuffer stringSQL = new StringBuffer();

        stringSQL.append(" UPDATE T_LIQUIDACION_PARCIAL_MOI ");
        stringSQL.append(" SET ID_ESTATUS_OPERACION = " + idEstatusOperacion);
        stringSQL.append(" WHERE FOLIO_CONTROL = " + folioControl);
        stringSQL.append(" AND NUMERO_LIQUIDACION = " + numeroLiquidacion);

        this.getJdbcTemplate().update(stringSQL.toString());
	}
	
	@Override
	public void actualizaCambioStatusLiquidacionesParciales(LiquidacionParcialMoi liquidacionParcialMoi) {
		log.info("OperacionSicDaoImpl :: actualizaCambioStatusLiquidacionesParciales");
        final StringBuffer stringSQL = new StringBuffer();

        stringSQL.append(" UPDATE T_LIQUIDACION_PARCIAL_MOI ");
        stringSQL.append(" SET ID_ESTATUS_OPERACION = " + liquidacionParcialMoi.getEstatusOperacion().getIdEstatusOperacion());
        stringSQL.append(" , ID_INSTRUCCION_LIQUIDACION = " + liquidacionParcialMoi.getIdInstruccionLiquidacion());
        stringSQL.append(" , FOLIO_CONTROL_LIQ = " + liquidacionParcialMoi.getFolioControlLiquidacion());
        if(liquidacionParcialMoi.getFechaConfirmacion() == null){
            stringSQL.append(" , FECHA_HORA_CF = null");	
        }
        if(liquidacionParcialMoi.getFechaLiquidacion() == null){
            stringSQL.append(" , FECHA_HORA_LQ = null");	
        }
        stringSQL.append(" WHERE FOLIO_CONTROL = " + liquidacionParcialMoi.getFolioControl());
        stringSQL.append(" AND NUMERO_LIQUIDACION = " + liquidacionParcialMoi.getNumeroLiquidacion());

        this.getJdbcTemplate().update(stringSQL.toString());
        this.flush();
	}
	
	@Override
	public BigDecimal getTotalLiquidacionesParciales(final Long folioControl, final Boolean isEfectivo) {
		log.debug("SettlementDisciplineRegimeDaoImpl :: getTotalLiquidacionesParciales A");
		
		BigDecimal totalLiqParciales = new BigDecimal("0");
		final StringBuilder query = new StringBuilder();
		if(isEfectivo){
			query.append(" SELECT SUM(LPM.PARCIAL_LIQUIDADO_EFECTIVO) as parcialEfectivoLiquidado ");	
		} else {
			query.append(" SELECT SUM(LPM.PARCIAL_LIQUIDADO) as parcialLiquidado ");
		}
		query.append(" FROM T_LIQUIDACION_PARCIAL_MOI LPM");
		query.append(" WHERE LPM.FOLIO_CONTROL = " + folioControl);

		BigDecimal sumLiqPrevia = (BigDecimal) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery sqlQuery = session.createSQLQuery(query.toString());
				return sqlQuery.uniqueResult();
			}
		});
		if(sumLiqPrevia != null){
			totalLiqParciales = sumLiqPrevia;
		}
		return totalLiqParciales;
	}

	@Override
	public Boolean getSettlementDisciplineRegimeByIdCuentaBoveda(final Long idCuentaBoveda, final String typeCsrd) {
		log.debug("SettlementDisciplineRegimeDaoImpl :: getSettlementDisciplineRegimeByIdCuentaBoveda");
		log.debug("SettlementDisciplineRegimeDaoImpl :: idCuentaBoveda: " + idCuentaBoveda);
		log.debug("SettlementDisciplineRegimeDaoImpl :: typeCsrd: " + typeCsrd);
		Boolean findCustodio = Boolean.FALSE;
		Long sdr = (Long) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                final StringBuffer stringSQL = new StringBuffer();
                stringSQL.append(" SELECT COUNT(*) FROM SettlementDisciplineRegime sdr  WHERE sdr.idCuentaBoveda = :idCuentaBoveda");
                
                if(typeCsrd.equals(Constantes.PARTIAL_SETTLEMENT)){
                	stringSQL.append(" AND sdr.partialSettlement = 1");
                }

                final Query query = session.createQuery(stringSQL.toString());
                query.setLong("idCuentaBoveda", idCuentaBoveda);
                return query.uniqueResult();
            }
        });
		
		if(sdr != null && sdr > 0){
			findCustodio = Boolean.TRUE;
		}
		
		log.debug("SettlementDisciplineRegimeDaoImpl :: findCustodio :: " + findCustodio);
    	return findCustodio;
	}

	/**
	 * @return the jdbcTemplate
	 */
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	/**
	 * @param jdbcTemplate the jdbcTemplate to set
	 */
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
}
