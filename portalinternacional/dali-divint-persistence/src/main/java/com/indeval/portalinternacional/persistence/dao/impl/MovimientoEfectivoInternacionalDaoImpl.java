// Cambio Multidivisas
package com.indeval.portalinternacional.persistence.dao.impl;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.ibm.icu.math.BigDecimal;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.CuentaTransitoria;
import com.indeval.portalinternacional.middleware.servicios.modelo.MovimientoDepositoEfectivoInternacional;
import com.indeval.portalinternacional.middleware.servicios.modelo.MovimientoRetiroEfectivoInternacional;
import com.indeval.portalinternacional.middleware.servicios.vo.CriteriosConsultaMovEfeDivExtVO;
import com.indeval.portalinternacional.middleware.servicios.vo.MovimientoEfectivoInternacionalVO;
import com.indeval.portalinternacional.persistence.dao.MovimientoEfectivoInternacionalDao;
import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MovimientoEfectivoInternacionalDaoImpl extends BaseDaoHibernateImpl implements MovimientoEfectivoInternacionalDao {

    private static final Logger LOG = LoggerFactory.getLogger(MovimientoEfectivoInternacionalDaoImpl.class);

    @Override
    public boolean esUsuarioPermitidoTransaccion(final String usuario, final List<Integer> idsOperacionTransaccion, final Long folioControl, boolean usuarioDiferente) {
        LOG.debug("esUsuarioPermitidoTransaccion " +
                ":: usuario [" + usuario + "] " +
                ":: folioControl [" + folioControl + "] " +
                ":: usuariosDiferentes [" + usuarioDiferente + "]");

        String signoComparaUsuario = usuarioDiferente ? " = " : " <> ";
        final StringBuilder sb = new StringBuilder();

        sb.append("SELECT COUNT(*) AS CONTEO ");
        sb.append("FROM T_BITACORA_TRANSACCIONES B, T_OBJETOS_TRANSACCION O ");
        sb.append("WHERE B.ID_BITACORA_TRANSACCION = O.ID_BITACORA_TRANSACCION ");
        sb.append("	AND B.ID_MODULO = :idModulo ");
        sb.append("	AND B.ID_OPERACION_TRANSACCION IN (:idsOperacionTransaccion) ");
        sb.append("	AND B.USUARIO").append(signoComparaUsuario).append(":usuario ");
        sb.append("	AND O.VALOR_ANTERIOR LIKE '%<folioControl>").append(folioControl).append("</folioControl>%' ");


        return (Boolean) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                SQLQuery query = session.createSQLQuery(sb.toString());

                LOG.debug(query.getQueryString().
                        replace(":idModulo", Constantes.ID_MODULO_MOV_EFE_DIV_EXT.toString()).
                        replace(":idsOperacionTransaccion", idsOperacionTransaccion.
                                toString().replace("[", "").replace("]", "")).
                        replace(":usuario", "'" + usuario + "'"));

                query.setInteger("idModulo", Constantes.ID_MODULO_MOV_EFE_DIV_EXT);
                query.setParameterList("idsOperacionTransaccion", idsOperacionTransaccion, new IntegerType());
                query.setString("usuario", usuario);

                query.addScalar("CONTEO", new IntegerType());

                Integer conteo = ((Integer) query.uniqueResult());
                LOG.debug("Conteo :: " + conteo);

                boolean esUsuarioPermitidoTransaccion = (conteo == BigDecimal.ZERO.intValue());

                LOG.debug("esUsuarioPermitidoTransaccion " +
                        " usuario [" + usuario + "] |" +
                        " folioControl [" + folioControl + "] ? " +
                        ":: " + esUsuarioPermitidoTransaccion);

                return esUsuarioPermitidoTransaccion;
            }
        });
    }


    @SuppressWarnings("unchecked")
    @Override
    public PaginaVO getMovimientosEfectivoInternacional(final CriteriosConsultaMovEfeDivExtVO criteriosConsulta, final PaginaVO paginaVO) {
        final StringBuilder sb = new StringBuilder();
        LOG.debug("getMovimientosEfectivoInternacional");
        sb.append("SELECT * FROM (");
        if (!Constantes.PREFIJO_ID_DEPOSITO.equals(criteriosConsulta.getTipoMovimiento())) {
            sb.append(" SELECT RE.ID_MOVIMIENTO_RETIRO_EFECTIVO_INT AS \"idMovimiento\", ");
            sb.append("    'RE' || RE.ID_MOVIMIENTO_RETIRO_EFECTIVO_INT AS \"idMovimientoStr\", ");
            sb.append("    T.CLAVE_TIPO_INSTITUCION || I.FOLIO_INSTITUCION || ' ' || I.NOMBRE_CORTO AS \"participante\", ");
            sb.append("    RE.FECHA_ALTA AS \"fechaAlta\", ");
            sb.append("    RE.FECHA_LIQUIDACION AS \"fechaLiquidacion\", ");
            sb.append("    'RETIRO' AS \"tipoMovimiento\", ");
            sb.append("    RE.ESTADO_MOV_EFECTIVO_INT AS \"estadoMovimiento\", ");
            sb.append("    RE.ESTADO_LIQ_INDEVAL AS \"estadoLiqIndeval\", ");
            sb.append("    D.CLAVE_ALFABETICA AS \"claveDivisa\", ");
            sb.append("    B.NOMBRE_CORTO AS \"nombreCortoBoveda\", ");
            sb.append("    CASE RE.ESTADO_MOV_EFECTIVO_INT ");
            sb.append("    	WHEN 1 THEN 'REGISTRADO' ");
            sb.append("    	WHEN 2 THEN 'RETENIDO' ");
            sb.append("    	WHEN 3 THEN 'AUTORIZADO' ");
            sb.append("    	WHEN 4 THEN 'LIBERADO' ");
            sb.append("    	WHEN 5 THEN 'APLICADO' ");
            sb.append("    	WHEN 6 THEN 'CANCELADO' ");
            sb.append("    END AS \"descEstadoMovimiento\", ");
            sb.append("    CASE RE.ESTADO_LIQ_INDEVAL ");
            sb.append("    	WHEN 1 THEN 'PENDIENTE' ");
            sb.append("    	WHEN 2 THEN 'LIQUIDADO' ");
            sb.append("    	WHEN 3 THEN 'CANCELADO' ");
            sb.append("    	WHEN 4 THEN 'ERROR' ");
            sb.append("    END AS \"descEstadoLiqIndeval\", ");
            sb.append("    CASE RE.ESTADO_LIQ_INDEVAL ");
            sb.append("    	WHEN 4 THEN (SELECT DESCRIPCION_ESTADO FROM T_INSTRUCCION_EFECTIVO ");
            sb.append("     			 WHERE CONCEPTO = 'RETIRO DIVISAS EXTRANJERAS' AND ORIGEN = 'MOI' ");
            sb.append("					 AND REFERENCIA_NUMERICA = RE.FOLIO_CONTROL) ");
            sb.append("    	ELSE NULL ");
            sb.append("    END AS \"descripcionError\", ");
            sb.append("    RE.SALDO_DISPONIBLE AS \"saldoDisponible\", ");
            sb.append("    RE.IMPORTE_TRASPASAR AS \"importeTraspasar\", ");
            sb.append("    RE.SALDO_EFECTIVO AS \"saldoEfectivo\", ");
            sb.append("    RE.REFERENCIA_NUMERICA AS \"referenciaNumerica\", ");
            sb.append("    RE.REFERENCIA_RELACIONADA AS \"referenciaRelacionada\", ");
            sb.append("    RE.NOTAS_COMENTARIOS AS \"notasComentarios\", ");
            sb.append("    RE.FOLIO_CONTROL AS \"folioControl\" ");
            sb.append("FROM ");
            sb.append("    T_MOVIMIENTO_RETIRO_EFECTIVO_INT RE, ");
            sb.append("    C_CUENTA_NOMBRADA C, ");
            sb.append("    C_INSTITUCION I, ");
            sb.append("    C_TIPO_INSTITUCION T, ");
            sb.append("    C_DIVISA D, ");
            sb.append("    C_BOVEDA B ");
            sb.append("WHERE ");
            sb.append("    RE.ID_CUENTA = C.ID_CUENTA_NOMBRADA ");
            sb.append("    AND C.ID_INSTITUCION = I.ID_INSTITUCION ");
            sb.append("    AND I.ID_TIPO_INSTITUCION = T.ID_TIPO_INSTITUCION ");
            sb.append("    AND RE.ID_DIVISA = D.ID_DIVISA ");
            sb.append("    AND RE.ID_BOVEDA = B.ID_BOVEDA ");
            if (criteriosConsulta.getFechaInicio() != null) {
                sb.append("    AND RE.FECHA_ALTA >= TRUNC(:fechaInicio) ");
            }
            if (criteriosConsulta.getFechaFin() != null) {
                sb.append("    AND RE.FECHA_ALTA < TRUNC(:fechaFin + 1) ");
            }
            if (criteriosConsulta.getFechaLiqInicio() != null) {
                sb.append("    AND RE.FECHA_LIQUIDACION >= TRUNC(:fechaLiqInicio) ");
            }
            if (criteriosConsulta.getFechaLiqFin() != null) {
                sb.append("    AND RE.FECHA_LIQUIDACION < TRUNC(:fechaLiqFin + 1) ");
            }
            if (!StringUtils.isEmpty(criteriosConsulta.getIdFolioParticipante())) {
                sb.append("	   AND T.CLAVE_TIPO_INSTITUCION || I.FOLIO_INSTITUCION = :idFolioParticipante ");
            }
            if (!StringUtils.isEmpty(criteriosConsulta.getFolioControl())) {
                sb.append("	   AND RE.FOLIO_CONTROL = :folioControl ");
            }
            if (!Constantes.MENOS_UNO_STRING.equals(criteriosConsulta.getIdDivisa().toString())) {
                sb.append("    AND RE.ID_DIVISA = :idDivisa ");
            }
            if (!Constantes.MENOS_UNO_STRING.equals(criteriosConsulta.getIdBoveda().toString())) {
                sb.append("    AND RE.ID_BOVEDA = :idBoveda ");
            }
            if (!Constantes.MENOS_UNO_STRING.equals(criteriosConsulta.getEstatusMovimiento().toString())) {
                sb.append("    AND RE.ESTADO_MOV_EFECTIVO_INT = :estatusMovimiento ");
            }
        }
        if (!Constantes.PREFIJO_ID_DEPOSITO.equals(criteriosConsulta.getTipoMovimiento())
                && !Constantes.PREFIJO_ID_RETIRO.equals(criteriosConsulta.getTipoMovimiento())) {
            sb.append("UNION ");
        }
        if (!Constantes.PREFIJO_ID_RETIRO.equals(criteriosConsulta.getTipoMovimiento())) {
            sb.append("SELECT DE.ID_MOVIMIENTO_DEPOSITO_EFECTIVO_INT AS \"idMovimiento\", ");
            sb.append("    'DE' || DE.ID_MOVIMIENTO_DEPOSITO_EFECTIVO_INT AS \"idMovimientoStr\", ");
            sb.append("    T.CLAVE_TIPO_INSTITUCION || I.FOLIO_INSTITUCION || ' ' || I.NOMBRE_CORTO AS \"participante\", ");
            sb.append("    DE.FECHA_ALTA AS \"fechaAlta\", ");
            sb.append("    DE.FECHA_LIQUIDACION AS \"fechaLiquidacion\", ");
            sb.append("    'DEPOSITO' AS \"tipoMovimiento\", ");
            sb.append("    DE.ESTADO_MOV_EFECTIVO_INT AS \"estadoMovimiento\", ");
            sb.append("    DE.ESTADO_LIQ_INDEVAL AS \"estadoLiqIndeval\", ");
            sb.append("    D.CLAVE_ALFABETICA AS \"claveDivisa\", ");
            sb.append("    B.NOMBRE_CORTO AS \"nombreCortoBoveda\", ");
            sb.append("    CASE DE.ESTADO_MOV_EFECTIVO_INT ");
            sb.append("    	WHEN 1 THEN 'REGISTRADO' ");
            sb.append("    	WHEN 2 THEN 'RETENIDO' ");
            sb.append("    	WHEN 3 THEN 'AUTORIZADO' ");
            sb.append("    	WHEN 4 THEN 'LIBERADO' ");
            sb.append("    	WHEN 5 THEN 'APLICADO' ");
            sb.append("    	WHEN 6 THEN 'CANCELADO' ");
            sb.append("    END AS \"descEstadoMovimiento\", ");
            sb.append("    CASE DE.ESTADO_LIQ_INDEVAL ");
            sb.append("    	WHEN 1 THEN 'PENDIENTE' ");
            sb.append("    	WHEN 2 THEN 'LIQUIDADO' ");
            sb.append("    	WHEN 3 THEN 'CANCELADO' ");
            sb.append("    	WHEN 4 THEN 'ERROR' ");
            sb.append("    END AS \"descEstadoLiqIndeval\", ");
            sb.append("    CASE DE.ESTADO_LIQ_INDEVAL ");
            sb.append("    	WHEN 4 THEN (SELECT DESCRIPCION_ESTADO FROM T_INSTRUCCION_EFECTIVO ");
            sb.append("     			 WHERE CONCEPTO = 'DEPOSITO DIVISAS EXTRANJERAS' AND ORIGEN = 'MOI' ");
            sb.append("					 AND REFERENCIA_NUMERICA = DE.FOLIO_CONTROL) ");
            sb.append("    	ELSE NULL ");
            sb.append("    END AS \"descripcionError\", ");
            sb.append("    DE.SALDO_DISPONIBLE AS \"saldoDisponible\", ");
            sb.append("    DE.IMPORTE_TRASPASAR AS \"importeTraspasar\", ");
            sb.append("    DE.SALDO_EFECTIVO AS \"saldoEfectivo\", ");
            sb.append("    NULL AS \"referenciaNumerica\", ");
            sb.append("    DE.REFERENCIA_RELACIONADA AS \"referenciaRelacionada\", ");
            sb.append("    NULL AS \"notasComentarios\", ");
            sb.append("    DE.FOLIO_CONTROL AS \"folioControl\" ");
            sb.append("FROM ");
            sb.append("    T_MOVIMIENTO_DEPOSITO_EFECTIVO_INT DE, ");
            sb.append("    C_CUENTA_NOMBRADA C, ");
            sb.append("    C_INSTITUCION I, ");
            sb.append("    C_TIPO_INSTITUCION T, ");
            sb.append("    C_DIVISA D, ");
            sb.append("    C_BOVEDA B ");
            sb.append("WHERE ");
            sb.append("    DE.ID_CUENTA = C.ID_CUENTA_NOMBRADA ");
            sb.append("    AND C.ID_INSTITUCION = I.ID_INSTITUCION ");
            sb.append("    AND I.ID_TIPO_INSTITUCION = T.ID_TIPO_INSTITUCION ");
            sb.append("    AND DE.ID_DIVISA = D.ID_DIVISA ");
            sb.append("    AND DE.ID_BOVEDA = B.ID_BOVEDA ");
            if (criteriosConsulta.getFechaInicio() != null) {
                sb.append("    AND DE.FECHA_ALTA >= TRUNC(:fechaInicio) ");
            }
            if (criteriosConsulta.getFechaFin() != null) {
                sb.append("    AND DE.FECHA_ALTA < TRUNC(:fechaFin + 1) ");
            }
            if (criteriosConsulta.getFechaLiqInicio() != null) {
                sb.append("    AND DE.FECHA_LIQUIDACION >= TRUNC(:fechaLiqInicio) ");
            }
            if (criteriosConsulta.getFechaLiqFin() != null) {
                sb.append("    AND DE.FECHA_LIQUIDACION < TRUNC(:fechaLiqFin + 1) ");
            }
            if (!StringUtils.isEmpty(criteriosConsulta.getIdFolioParticipante())) {
                sb.append("	   AND T.CLAVE_TIPO_INSTITUCION || I.FOLIO_INSTITUCION = :idFolioParticipante ");
            }
            if (!StringUtils.isEmpty(criteriosConsulta.getFolioControl())) {
                sb.append("	   AND DE.FOLIO_CONTROL = :folioControl ");
            }
            if (!Constantes.MENOS_UNO_STRING.equals(criteriosConsulta.getIdDivisa().toString())) {
                sb.append("    AND DE.ID_DIVISA = :idDivisa ");
            }
            if (!Constantes.MENOS_UNO_STRING.equals(criteriosConsulta.getIdBoveda().toString())) {
                sb.append("    AND DE.ID_BOVEDA = :idBoveda ");
            }
            if (!Constantes.MENOS_UNO_STRING.equals(criteriosConsulta.getEstatusMovimiento().toString())) {
                sb.append("    AND DE.ESTADO_MOV_EFECTIVO_INT = :estatusMovimiento ");
            }
        }
        sb.append(") ");
        sb.append("ORDER BY \"participante\", \"nombreCortoBoveda\", \"claveDivisa\", \"fechaAlta\" ");

        List<MovimientoEfectivoInternacionalVO> resultados = (List<MovimientoEfectivoInternacionalVO>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                SQLQuery query = session.createSQLQuery(sb.toString());
                query.setCacheable(false);

                LOG.debug(query.getQueryString());

                if (criteriosConsulta.getFechaInicio() != null) {
                    query.setDate("fechaInicio", criteriosConsulta.getFechaInicio());
                }
                if (criteriosConsulta.getFechaFin() != null) {
                    query.setDate("fechaFin", criteriosConsulta.getFechaFin());
                }
                if (criteriosConsulta.getFechaLiqInicio() != null) {
                    query.setDate("fechaLiqInicio", criteriosConsulta.getFechaLiqInicio());
                }
                if (criteriosConsulta.getFechaLiqFin() != null) {
                    query.setDate("fechaLiqFin", criteriosConsulta.getFechaLiqFin());
                }
                if (!StringUtils.isEmpty(criteriosConsulta.getIdFolioParticipante())) {
                    query.setString("idFolioParticipante", criteriosConsulta.getIdFolioParticipante());
                }
                if (!StringUtils.isEmpty(criteriosConsulta.getFolioControl())) {
                    query.setLong("folioControl", Long.valueOf(criteriosConsulta.getFolioControl()));
                }
                if (!Constantes.MENOS_UNO_STRING.equals(criteriosConsulta.getIdDivisa().toString())) {
                    query.setLong("idDivisa", criteriosConsulta.getIdDivisa());
                }
                if (!Constantes.MENOS_UNO_STRING.equals(criteriosConsulta.getIdBoveda().toString())) {
                    query.setLong("idBoveda", criteriosConsulta.getIdBoveda());
                }
                if (!Constantes.MENOS_UNO_STRING.equals(criteriosConsulta.getEstatusMovimiento().toString())) {
                    query.setLong("estatusMovimiento", criteriosConsulta.getEstatusMovimiento());
                }

                StringType st = new StringType();
                LongType lt = new LongType();
                DateType dt = new DateType();
                DoubleType nt = new DoubleType();

                query.addScalar("idMovimiento", lt);
                query.addScalar("idMovimientoStr", st);
                query.addScalar("participante", st);
                query.addScalar("fechaAlta", dt);
                query.addScalar("fechaLiquidacion", dt);
                query.addScalar("tipoMovimiento", st);
                query.addScalar("claveDivisa", st);
                query.addScalar("nombreCortoBoveda", st);
                query.addScalar("descEstadoMovimiento", st);
                query.addScalar("descEstadoLiqIndeval", st);
                query.addScalar("descripcionError", st);
                query.addScalar("estadoMovimiento", lt);
                query.addScalar("estadoLiqIndeval", lt);
                query.addScalar("saldoDisponible", nt);
                query.addScalar("importeTraspasar", nt);
                query.addScalar("saldoEfectivo", nt);
                query.addScalar("referenciaNumerica", st);
                query.addScalar("referenciaRelacionada", st);
                query.addScalar("notasComentarios", st);
                query.addScalar("folioControl", lt);

                query.setResultTransformer(Transformers.aliasToBean(MovimientoEfectivoInternacionalVO.class));

                paginaVO.setRegistros(query.list());
                paginaVO.setTotalRegistros(paginaVO.getRegistros().size());

                if (paginaVO.getRegistrosXPag() != PaginaVO.TODOS) {

                    query.setFirstResult(paginaVO.getOffset());
                    query.setMaxResults(paginaVO.getRegistrosXPag());
                }

                return query.list();
            }
        });

        paginaVO.setRegistros(resultados);

        return paginaVO;
    }

    @Override
    public void updateEstatusDepositosEfectivoInternacional(final List<Long> idsDepositos, final Long estadoMovEfectivoIntAsignar) {
        final StringBuilder instruccion = new StringBuilder();
        instruccion.append("UPDATE MovimientoDepositoEfectivoInternacional d SET d.estadoMovEfectivoInt = :estadoMovEfectivoIntAsignar ");
        instruccion.append("WHERE d.idMovimientoEfectivoInt IN (:idsDepositos) ");

        getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createQuery(instruccion.toString());
                query.setLong("estadoMovEfectivoIntAsignar", estadoMovEfectivoIntAsignar);
                query.setParameterList("idsDepositos", idsDepositos);
                query.setCacheable(false);

                return query.executeUpdate();
            }
        });
    }

    @Override
    public void updateEstatusRetirosEfectivoInternacional(final List<Long> idsRetiros, final Long estadoMovEfectivoIntAsignar) {
        final StringBuilder instruccion = new StringBuilder();
        instruccion.append("UPDATE MovimientoRetiroEfectivoInternacional r SET r.estadoMovEfectivoInt = :estadoMovEfectivoIntAsignar ");
        instruccion.append("WHERE r.idMovimientoEfectivoInt IN (:idsRetiros) ");

        getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createQuery(instruccion.toString());
                query.setLong("estadoMovEfectivoIntAsignar", estadoMovEfectivoIntAsignar);
                query.setParameterList("idsRetiros", idsRetiros);
                query.setCacheable(false);

                return query.executeUpdate();
            }
        });
    }

    @Override
    public void saveMovimientoRetiroEfectivoInternacional(MovimientoRetiroEfectivoInternacional movimientoEfectivoInternacional) {
        LOG.debug("Entrando a saveMovimientoRetiroEfectivoInternacional");
        try {
//			this.getHibernateTemplate().setAllowCreate(true);
//			this.getHibernateTemplate().setCheckWriteOperations(false);
            this.getHibernateTemplate().save(movimientoEfectivoInternacional);
            this.getHibernateTemplate().flush();
        } catch (Exception ex) {
            LOG.error("Error en saveMovimientoRetiroEfectivoInternacional", ex);
        }
    }

    @Override
    public void saveMovimientoDepositoEfectivoInternacional(MovimientoDepositoEfectivoInternacional movimientoEfectivoInternacional) {
        LOG.debug("Entrando a saveMovimientoDepositoEfectivoInternacional");
        try {
//			this.getHibernateTemplate().setAllowCreate(true);
//			this.getHibernateTemplate().setCheckWriteOperations(false);
            this.getHibernateTemplate().save(movimientoEfectivoInternacional);
            this.getHibernateTemplate().flush();
        } catch (Exception ex) {
            LOG.error("Error en saveMovimientoDepositoEfectivoInternacional", ex);
        }
    }

    @Override
    public Long getFolioControl() {
        try {
            final Connection conn = this.getHibernateTemplate().getSessionFactory().getCurrentSession().connection();
            final Statement stm = conn.createStatement();
            final ResultSet res = stm.executeQuery("select seq_mov_int_folio_control.nextval from dual");
            if (res.next()) {
                return Long.parseLong(res.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public CuentaTransitoria obtenerCuentaTransitorioByRefAndMontoAndBovedaAndDivisa(final String referencia,
                                                                                     final Long idCustodio, final Long idDivisa,
                                                                                     final BigDecimal montob, final String tipoMensaje) {
        LOG.debug(String.format("obtenerCuentaTransitorioByRefAndMontoAndBovedaAndDivisa(%s,%s,%s,%s,%s)", referencia,
                idCustodio.toString(), idDivisa.toString(), montob.toString(), tipoMensaje));

        return (CuentaTransitoria) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) {

                final StringBuffer stringSQL = new StringBuffer();
                stringSQL.append(" FROM CuentaTransitoria cta WHERE cta.folioRelacionado=:referencia ");
                stringSQL.append(" AND cta.idDivisa.idDivisa=:divisa ");
                stringSQL.append(" AND cta.IdCustodio.id=:idCustodio ");
                stringSQL.append(" AND cta.monto=:montob ");
                LOG.debug(stringSQL.toString());

                final Query query = session.createQuery(stringSQL.toString());

                query.setString("referencia", referencia);
                query.setLong("idCustodio", idCustodio);
                query.setLong("divisa", idDivisa);
                query.setBigDecimal("montob", montob.toBigDecimal());
                List<CuentaTransitoria> lis = new ArrayList<>();
                CuentaTransitoria cta = null;
                lis = query.list();

                if (lis != null && !lis.isEmpty()) {
                    cta = lis.get(0);
                }
                return cta;
            }
        });
    }

}
