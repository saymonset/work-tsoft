package com.indeval.portalinternacional.persistence.dao.impl;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.type.DateType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.middleware.servicios.util.UtilsVO;
import com.indeval.portaldali.persistence.modelo.CuentaNombrada;
import com.indeval.portaldali.persistence.modelo.Institucion;
import com.indeval.portaldali.persistence.util.DateUtils;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.BeneficiarioInstitucion;
import com.indeval.portalinternacional.middleware.servicios.modelo.CustodioTipoBenef;
import com.indeval.portalinternacional.middleware.servicios.modelo.Derecho;
import com.indeval.portalinternacional.middleware.servicios.modelo.DerechoBeneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.PosicionLiquidacion;
import com.indeval.portalinternacional.middleware.servicios.modelo.SicEmision;
import com.indeval.portalinternacional.middleware.servicios.modelo.VConsultaBeneficiarioDerechosCuenta;
import com.indeval.portalinternacional.middleware.servicios.modelo.VConsultaDerechosCapital;
import com.indeval.portalinternacional.middleware.servicios.modelo.capitales.DerechoCapitalHistorico;
import com.indeval.portalinternacional.middleware.servicios.modelo.capitales.DerechoCapitalHistoricoVo;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaDerechosParam;
import com.indeval.portalinternacional.middleware.servicios.vo.PosicionCuentaVO;
import com.indeval.portalinternacional.persistence.dao.DerechoDao;

@SuppressWarnings({"unchecked"})
public class DerechoDaoImpl extends BaseDaoHibernateImpl implements DerechoDao {

    private JdbcTemplate jdbcTemplate;
    private static final Logger LOG = LoggerFactory.getLogger(DerechoDaoImpl.class);

    public PaginaVO findDerechos(final ConsultaDerechosParam param, PaginaVO paginaVO) {
        Long numeroDerechos = null;
        paginaVO = UtilsVO.getPaginaNotBlank(paginaVO);

        // Asignacion por default.
        paginaVO.setTotalRegistros(0);

        LOG.debug("Buscando derechos con los parametros " + param);

        LOG.debug("Contando los derechos.....");
        numeroDerechos = this.countDerechos(param);
        LOG.debug("Numero de derechos [" + numeroDerechos + "]");
        if (numeroDerechos != null) {
            paginaVO.setTotalRegistros(numeroDerechos.intValue());
        }

        if (paginaVO.getTotalRegistros() != null && paginaVO.getTotalRegistros().intValue() > 0) {
            LOG.debug("Buscando los derechos con hibernate......");
            paginaVO.setRegistros(this.findDerechosWithHibernate(param, paginaVO.getOffset(),
                    paginaVO.getRegistrosXPag()));
        } else {
            paginaVO.setRegistros(new ArrayList<VConsultaDerechosCapital>());
        }

        return paginaVO;
    }

    private long countDerechos(final ConsultaDerechosParam param) {
        StringBuilder sb = new StringBuilder();
        List<Object> paramsSQL = new ArrayList<Object>();
        List<Object> lstResult = null;
        int size = 0;

        sb.append("                     SELECT  DISTINCT(DC.ID_DERECHO_CAPITAL) ");

        sb.append("     FROM    T_DERECHOS_CAPITAL DC, C_CUPON C, C_INSTRUMENTO I, C_EMISION E, C_EMISORA EA, C_DIVISA D, C_BOVEDA B");

        if (param.getInstitucion() != null) {
            sb.append("         ,T_POSICIONES_LIQUIDACION PL, C_CUENTA_NOMBRADA CN, C_INSTITUCION INS ");
        }
        if (param.getEmision() != null && param.getEmision().getIdBoveda() != null) {
            sb.append(",C_SIC_EMISIONES SE");
        }

        if (param.getIdEstado() != null
                && param.getIdEstado().intValue() != Constantes.ID_ESTATUS_DERECHO_CORTADO
                        .intValue()) {
            sb.append(",T_DERECHO_BENEFICIARIO DB ");
        }

        sb.append("     WHERE   DC.ID_TIPO_DERECHO IN ("
                + Constantes.ID_DERECHO_DIVIDENDO_EN_EFECTIVO + ","
                + Constantes.ID_DERECHO_DISTRIBUCION + ")");

        if (param.getIdEstado() != null
                && param.getIdEstado().intValue() == Constantes.ID_ESTATUS_DERECHO_CORTADO
                        .intValue()) {
            sb.append("         AND DC.ID_ELEM_ESTATUS_DERECHO = "
                    + Constantes.ID_ESTATUS_DERECHO_CORTADO_MAV);
        } else {
            sb.append("         AND DC.ID_DERECHO_CAPITAL = DB.ID_DERECHO_CAPITAL");
            sb.append("         AND DB.ID_ESTATUS_DERECHO = ?");
            sb.append("         AND DB.ELIMINADO = 0 ");
            paramsSQL.add(param.getIdEstado());
        }

        if (param.getFechaCorte() != null) {
            sb.append("         AND DC.FECHA_CORTE = ?");
            paramsSQL.add(new Date(param.getFechaCorte().getTime()));
        }

        if (param.getFechaPago() != null) {
            sb.append("         AND DC.FECHA_PAGO_DERECHO_CAPITAL = ?");
            paramsSQL.add(new Date(param.getFechaPago().getTime()));
        }

        sb.append("             AND DC.ID_DIVISA_PRODUCTO = D.ID_DIVISA");
        if (param.getIdDivisa() != null) {
            sb.append("         AND D.ID_DIVISA = ?");
            paramsSQL.add(param.getIdDivisa());
        }

        sb.append("             AND DC.ID_BOVEDA_EFECTIVO = B.ID_BOVEDA");
        sb.append("             AND DC.ID_CUPON_ORIGEN = C.ID_CUPON");
        sb.append("             AND C.ID_EMISION = E.ID_EMISION");
        if (param.getEmision() != null && param.getEmision().getIdBoveda() != null) {
            sb.append("         AND E.ID_EMISION = SE.ID_EMISION");
            sb.append("         AND SE.ID_CUENTA_NOMBRADA = ?");
            paramsSQL.add(param.getEmision().getIdBoveda());
            sb.append("         AND SE.ESTATUS_REGISTRO = ? ");
            paramsSQL.add(Constantes.ESTATUS_REGISTRO_VIGENTE);
        }

        if (param.getEmision() != null && StringUtils.isNotBlank(param.getEmision().getCupon())) {
            sb.append("         AND C.CLAVE_CUPON = ?");
            paramsSQL.add(StringUtils.upperCase(param.getEmision().getCupon()));
        }

        if (param.getEmision() != null && StringUtils.isNotBlank(param.getEmision().getSerie())) {
            sb.append("         AND E.SERIE = ?");
            paramsSQL.add(StringUtils.upperCase(param.getEmision().getSerie()));

        }
        if (param.getEmision() != null && StringUtils.isNotBlank(param.getEmision().getIsin())) {
            sb.append("         AND E.ISIN = ?");
            paramsSQL.add(StringUtils.upperCase(param.getEmision().getIsin()));
        }

        sb.append("         AND E.ID_EMISORA = EA.ID_EMISORA");
        if (param.getEmision() != null && StringUtils.isNotBlank(param.getEmision().getEmisora())) {
            sb.append("         AND EA.CLAVE_PIZARRA = ?");
            paramsSQL.add(StringUtils.upperCase(param.getEmision().getEmisora()));
        }

        sb.append("             AND E.ID_INSTRUMENTO = I.ID_INSTRUMENTO");
        sb.append("             AND I.ES_EXTRANJERA = " + Constantes.ID_EMISION_EXTRANJERA);
        if (param.getEmision() != null && StringUtils.isNotBlank(param.getEmision().getTv())) {
            sb.append("         AND I.CLAVE_TIPO_VALOR = ?");
            paramsSQL.add(StringUtils.upperCase(param.getEmision().getTv()));
        }

        if (param.getInstitucion() != null) {
            sb.append("         AND DC.ID_DERECHO_CAPITAL = PL.ID_DERECHO");
            sb.append("         AND PL.ID_TIPO_DERECHO in("
                    + Constantes.ID_DERECHO_DIVIDENDO_EN_EFECTIVO + ","
                    + Constantes.ID_DERECHO_DISTRIBUCION + ")");
            sb.append("         AND PL.ID_CUENTA = CN.ID_CUENTA_NOMBRADA");
            sb.append("         AND CN.ID_INSTITUCION = INS.ID_INSTITUCION");
            sb.append("         AND INS.ID_TIPO_INSTITUCION = ?");
            sb.append("         AND INS.FOLIO_INSTITUCION = ?");
            paramsSQL.add(param.getInstitucion().getTipoInstitucion().getIdTipoInstitucion());
            paramsSQL.add(param.getInstitucion().getFolioInstitucion());
        }

        if (param.getIdEstado() == Constantes.ID_ESTATUS_DERECHO_CORTADO.intValue()) {
            sb.append("     AND DC.ID_DERECHO_CAPITAL not in(SELECT tmp.ID_DERECHO_CAPITAL FROM T_DERECHO_BENEFICIARIO tmp, C_ESTATUS_DERECHOS_DALI est ");
            sb.append("     WHERE  tmp.ID_ESTATUS_DERECHO = est.id_estatus_derecho");
            sb.append("     AND tmp.ELIMINADO = 0 AND est.id_estatus_derecho != "
                    + Constantes.ID_ESTATUS_DERECHO_CORTADO);
            sb.append(" )");
        }

        lstResult = this.jdbcTemplate.queryForList(sb.toString(), paramsSQL.toArray(new Object[0]));

        if (lstResult != null && !lstResult.isEmpty()) {
            size = lstResult.size();
        }
        return size;
    }

    private List<Derecho> findDerechosWithHibernate(final ConsultaDerechosParam param,
            final Integer offset, final Integer registrosPorPagina) {
        List<Derecho> lstDerechos = null;
        final StringBuilder sb = new StringBuilder();
        final List<Object> paramsSQL = new ArrayList<Object>();
        final List<Type> tipos = new ArrayList<Type>();


        sb.append("SELECT   DISTINCT dc ");
        sb.append("FROM     " + Derecho.class.getName() + " dc  ");

        if (param.getIdEstado() != null
                && param.getIdEstado() == Constantes.ID_ESTATUS_DERECHO_CORTADO.intValue()) {
            sb.append("     JOIN dc.elementEstatusDerecho ed    ");
        } else {
            sb.append("     JOIN dc.derechosBeneficiarios db    ");
        }

        sb.append("         JOIN FETCH dc.divisaProducto d  ");
        sb.append("         JOIN FETCH dc.boveda b  ");
        sb.append("         JOIN FETCH dc.tipoDerecho td");
        sb.append("         JOIN FETCH dc.cuponOrigen c ");
        sb.append("         JOIN FETCH c.emision e  ");
        sb.append("         JOIN FETCH e.emisora ea ");
        sb.append("         JOIN FETCH e.instrumento i  ");
        if (param.getInstitucion() != null) {
            sb.append("     ," + PosicionLiquidacion.class.getName() + " pl ");
            sb.append("     ," + CuentaNombrada.class.getName() + " cn  ");
            sb.append("     ," + Institucion.class.getName() + " ins    ");
        }
        // if(param.getIdEstado() == Constantes.ID_ESTATUS_DERECHO_CORTADO.intValue()){
        // sb.append("       ,"+DerechoBeneficiario.class.getName()+" dbv    ");
        // }
        if (param.getEmision() != null && param.getEmision().getIdBoveda() != null) {
            sb.append("         ," + SicEmision.class.getName() + " se  ");
        }
        sb.append("WHERE    td.idTipoDerecho IN (" + Constantes.ID_DERECHO_DIVIDENDO_EN_EFECTIVO
                + "," + Constantes.ID_DERECHO_DISTRIBUCION + ")    ");
        if (param.getIdEstado() != null
                && param.getIdEstado() == Constantes.ID_ESTATUS_DERECHO_CORTADO.intValue()) {
            sb.append("     AND ed.idStatusDerecho = " + Constantes.ID_ESTATUS_DERECHO_CORTADO_MAV);
        } else {
            sb.append("     AND db.estatusDerecho.idEstatusDerecho = ?");
            paramsSQL.add(param.getIdEstado());
            tipos.add(new IntegerType());
            sb.append("     AND db.eliminado = false ");
        }
        if (param.getFechaCorte() != null) {
            sb.append("     AND dc.fechaCorte = ?   ");
            paramsSQL.add(new Date(param.getFechaCorte().getTime()));
            tipos.add(new DateType());
        }
        if (param.getFechaPago() != null) {
            sb.append("     AND dc.fechaPago = ?    ");
            paramsSQL.add(new Date(param.getFechaPago().getTime()));
            tipos.add(new DateType());
        }
        if (param.getIdDivisa() != null) {
            sb.append("     AND d.idDivisa = ?  ");
            paramsSQL.add(param.getIdDivisa());
            tipos.add(new LongType());
        }
        if (param.getEmision() != null && param.getEmision().getIdBoveda() != null) {
            sb.append("     AND e.idEmision = se.emision.idEmision ");
            sb.append("     AND se.cuentaNombrada.idCuentaNombrada = ?  ");
            paramsSQL.add(param.getEmision().getIdBoveda());
            tipos.add(new LongType());
            sb.append("     AND se.estatusRegistro = ? ");
            paramsSQL.add(Constantes.ESTATUS_REGISTRO_VIGENTE);
            tipos.add(new StringType());
        }
        if (param.getEmision() != null && StringUtils.isNotBlank(param.getEmision().getCupon())) {
            sb.append("     AND c.claveCupon = ?    ");
            paramsSQL.add(StringUtils.upperCase(param.getEmision().getCupon()));
            tipos.add(new StringType());
        }
        if (param.getEmision() != null && StringUtils.isNotBlank(param.getEmision().getSerie())) {
            sb.append("     AND e.serie = ? ");
            paramsSQL.add(StringUtils.upperCase(param.getEmision().getSerie()));
            tipos.add(new StringType());
        }
        if (param.getEmision() != null && StringUtils.isNotBlank(param.getEmision().getIsin())) {
            sb.append("     AND e.isin = ?  ");
            paramsSQL.add(StringUtils.upperCase(param.getEmision().getIsin()));
            tipos.add(new StringType());
        }
        if (param.getEmision() != null && StringUtils.isNotBlank(param.getEmision().getEmisora())) {
            sb.append("     AND ea.clavePizarra = ? ");
            paramsSQL.add(StringUtils.upperCase(param.getEmision().getEmisora()));
            tipos.add(new StringType());
        }
        sb.append("         AND i.esExtranjera = " + Constantes.ID_EMISION_EXTRANJERA);
        if (param.getEmision() != null && StringUtils.isNotBlank(param.getEmision().getTv())) {
            sb.append("     AND i.claveTipoValor = ?    ");
            paramsSQL.add(param.getEmision().getTv());
            tipos.add(new StringType());
        }

        if (param.getInstitucion() != null) {
            sb.append("     AND dc.idDerechoCapital = pl.idDerechoCapital ");
            sb.append("     AND pl.idTipoDerecho in(" + Constantes.ID_DERECHO_DIVIDENDO_EN_EFECTIVO
                    + "," + Constantes.ID_DERECHO_DISTRIBUCION + ")");
            sb.append("     AND pl.cuenta.idCuentaNombrada = cn.idCuentaNombrada ");
            sb.append("     AND cn.institucion.idInstitucion = ins.idInstitucion ");
            sb.append("     AND ins.folioInstitucion = ?");
            paramsSQL.add(param.getInstitucion().getFolioInstitucion());
            tipos.add(new StringType());
            sb.append("     AND ins.tipoInstitucion.idTipoInstitucion = ?");
            paramsSQL.add(param.getInstitucion().getTipoInstitucion().getIdTipoInstitucion());
            tipos.add(new LongType());
        }
        if (param.getIdEstado() == Constantes.ID_ESTATUS_DERECHO_CORTADO.intValue()) {
            sb.append("     AND dc.idDerechoCapital not in(SELECT tmp.idDerechoCapital FROM "
                    + DerechoBeneficiario.class.getName()
                    + " tmp JOIN tmp.estatusDerecho est WHERE  ");
            sb.append("     tmp.eliminado = false AND tmp.estatusDerecho is not null AND est.idEstatusDerecho != "
                    + Constantes.ID_ESTATUS_DERECHO_CORTADO);
            sb.append(" )");
        }

        sb.append(" ORDER BY dc.fechaCorte, i.claveTipoValor, ea.clavePizarra, e.serie ");


        lstDerechos = (List<Derecho>) this.getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(final Session session) throws HibernateException,
                    SQLException {
                Query query = session.createQuery(sb.toString());
                query.setParameters(paramsSQL.toArray(new Object[0]), tipos.toArray(new Type[0]));
                if (registrosPorPagina.intValue() != PaginaVO.TODOS.intValue()) {
                    query.setFirstResult(offset.intValue());
                    query.setMaxResults(registrosPorPagina);
                }
                return query.list();
            }
        });

        return lstDerechos;
    }

    /**
     * Consulta el total de registros en la vista de consulta de derechos de capital.
     * 
     * @param params Los parametros para la consulta.
     * @return El conteo de los registros.
     */
    private long countDerechosUsandoVista(final ConsultaDerechosParam params) {
        StringBuilder sb = new StringBuilder();
        List<Object> paramsSQL = new ArrayList<Object>();

        sb.append("SELECT COUNT(*) FROM ( ");
        sb.append("SELECT DISTINCT (ID_DERECHO_CAPITAL) ");

        if (params.getInstitucion() != null) {
            sb.append("FROM V_CONS_DER_CAP dc ");
            sb.append("left join T_POSICIONES_LIQUIDACION PL on dc.id_derecho_capital = pl.id_derecho AND pl.id_tipo_derecho IN (1,18) ");
            sb.append("left join C_CUENTA_NOMBRADA CN on pl.id_cuenta = cn.id_cuenta_nombrada ");
            sb.append("left join C_INSTITUCION INS on cn.id_institucion = ins.id_institucion ");
        } else {
            sb.append("FROM V_CONS_DER_CAP ");
        }

        sb.append("WHERE 1 = 1 ");

        if (params.getInstitucion() != null) {
            sb.append("AND INS.ID_TIPO_INSTITUCION = ? ");
            sb.append("AND INS.FOLIO_INSTITUCION = ? ");
            paramsSQL.add(params.getInstitucion().getTipoInstitucion().getIdTipoInstitucion());
            paramsSQL.add(params.getInstitucion().getFolioInstitucion());
        }

        if (params.getEmision() != null && StringUtils.isNotBlank(params.getEmision().getTv())) {
            if (params.getInstitucion() != null) {
                sb.append("AND dc.CLAVE_TIPO_VALOR = ? ");
            } else {
                sb.append("AND CLAVE_TIPO_VALOR = ? ");
            }
            paramsSQL.add(StringUtils.upperCase(params.getEmision().getTv()));
        }

        if (params.getEmision() != null && StringUtils.isNotBlank(params.getEmision().getEmisora())) {
            if (params.getInstitucion() != null) {
                sb.append("AND dc.CLAVE_PIZARRA = ? ");
            } else {
                sb.append("AND CLAVE_PIZARRA = ? ");
            }
            paramsSQL.add(StringUtils.upperCase(params.getEmision().getEmisora()));
        }

        if (params.getEmision() != null && StringUtils.isNotBlank(params.getEmision().getSerie())) {
            if (params.getInstitucion() != null) {
                sb.append("AND dc.SERIE = ? ");
            } else {
                sb.append("AND SERIE = ? ");
            }
            paramsSQL.add(StringUtils.upperCase(params.getEmision().getSerie()));
        }

        if (params.getEmision() != null && StringUtils.isNotBlank(params.getEmision().getCupon())) {
            if (params.getInstitucion() != null) {
                sb.append("AND dc.CLAVE_CUPON = ? ");
            } else {
                sb.append("AND CLAVE_CUPON = ? ");
            }
            paramsSQL.add(StringUtils.upperCase(params.getEmision().getCupon()));
        }

        if (params.getEmision() != null && StringUtils.isNotBlank(params.getEmision().getIsin())) {
            if (params.getInstitucion() != null) {
                sb.append("AND dc.ISIN = ? ");
            } else {
                sb.append("AND ISIN = ? ");
            }
            paramsSQL.add(StringUtils.upperCase(params.getEmision().getIsin()));
        }

        if (params.getIdDivisa() != null) {
            if (params.getInstitucion() != null) {
                sb.append("AND dc.ID_DIVISA = ? ");
            } else {
                sb.append("AND ID_DIVISA = ? ");
            }
            paramsSQL.add(params.getIdDivisa());
        }

        if (params.getEmision() != null && params.getEmision().getIdBoveda() != null) {
            if (params.getInstitucion() != null) {
                sb.append("AND dc.ID_CUENTA_NOMBRADA = ? ");
            } else {
                sb.append("AND ID_CUENTA_NOMBRADA = ? ");
            }
            paramsSQL.add(params.getEmision().getIdBoveda());
        }

        if (params.getIdEstado() != null
                && params.getIdEstado() == Constantes.ID_ESTATUS_DERECHO_CORTADO.intValue()) {
            if (params.getInstitucion() != null) {
                sb.append("AND dc.ID_ESTATUS_DERECHO_CORTADO = "
                        + Constantes.ID_ESTATUS_DERECHO_CORTADO_MAV + " ");
            } else {
                sb.append("AND ID_ESTATUS_DERECHO_CORTADO = "
                        + Constantes.ID_ESTATUS_DERECHO_CORTADO_MAV + " ");
            }
        } else {
            if (params.getInstitucion() != null) {
                sb.append("AND dc.ID_ESTATUS_DERECHO = ? ");
            } else {
                sb.append("AND ID_ESTATUS_DERECHO = ? ");
            }
            paramsSQL.add(params.getIdEstado());
        }

        if (params.getFechaCorte() != null) {
            if (params.getInstitucion() != null) {
                sb.append("AND dc.FECHA_CORTE = ? ");
            } else {
                sb.append("AND FECHA_CORTE = ? ");
            }
            paramsSQL.add(new Date(params.getFechaCorte().getTime()));
        }

        if (params.getFechaPago() != null) {
            if (params.getInstitucion() != null) {
                sb.append("AND dc.FECHA_PAGO_DERECHO_CAPITAL = ? ");
            } else {
                sb.append("AND FECHA_PAGO_DERECHO_CAPITAL = ? ");
            }
            paramsSQL.add(new Date(params.getFechaPago().getTime()));
        }

        // Se cierra el parentesis del conteo.
        sb.append(" ) ");

        if (LOG.isDebugEnabled()) {
            LOG.debug("####### Query Para Contar Registros=[" + sb.toString() + "]");
            for (Object o : paramsSQL) {
                LOG.debug("####### Param=[" + o.toString() + "]");
            }
        }

        return this.jdbcTemplate.queryForLong(sb.toString(), paramsSQL.toArray(new Object[0]));
    }

    /**
     * Llena el query con los parametros necesarios para realizar la busqueda de los derechos.
     * 
     * @param params Los parametros para realizar la busqueda.
     * @param query El query al cual asignar los parametros.
     * @return El query con los parametros asignados.
     */
    private Query createDerechosCapitalParams(final ConsultaDerechosParam params, Query query) {
        if (params.getInstitucion() != null) {
            query.setString("folioInstitucion", params.getInstitucion().getFolioInstitucion());
            query.setLong("idTipoInstitucion", params.getInstitucion().getTipoInstitucion()
                    .getIdTipoInstitucion());
        }

        if (params.getEmision() != null && StringUtils.isNotBlank(params.getEmision().getTv())) {
            query.setString("tv", StringUtils.upperCase(params.getEmision().getTv()));
        }

        if (params.getEmision() != null && StringUtils.isNotBlank(params.getEmision().getEmisora())) {
            query.setString("emisora", StringUtils.upperCase(params.getEmision().getEmisora()));
        }

        if (params.getEmision() != null && StringUtils.isNotBlank(params.getEmision().getSerie())) {
            query.setString("serie", StringUtils.upperCase(params.getEmision().getSerie()));
        }

        if (params.getEmision() != null && StringUtils.isNotBlank(params.getEmision().getCupon())) {
            query.setString("cupon", StringUtils.upperCase(params.getEmision().getCupon()));
        }

        if (params.getEmision() != null && StringUtils.isNotBlank(params.getEmision().getIsin())) {
            query.setString("isin", StringUtils.upperCase(params.getEmision().getIsin()));
        }

        if (params.getIdDivisa() != null) {
            query.setLong("idDivisa", params.getIdDivisa());
        }

        if (params.getEmision() != null && params.getEmision().getIdBoveda() != null) {
            query.setLong("idCuentaNombrada", params.getEmision().getIdBoveda());
        }

        if (params.getIdEstado() != null
                && params.getIdEstado() == Constantes.ID_ESTATUS_DERECHO_CORTADO.intValue()) {
            query = query;
        } else {
            query.setInteger("idEstatusDerecho", params.getIdEstado());
        }

        if (params.getFechaCorte() != null) {
            query.setParameter("fechaCorte",
                    DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaCorte(), true));
        }

        if (params.getFechaPago() != null) {
            query.setParameter("fechaPagoDerechoCapital",
                    DateUtils.preparaFechaConExtremoEnSegundos(params.getFechaPago(), true));
        }

        return query;
    }

    /**
     * Realiza la busqueda de los derechos de la vista de consulta derechos capital.
     * 
     * @param params Los parametros para realizar la busqueda.
     * @param offset El offset de datos para comenzar a obtenerlos a partir de este.
     * @param registrosPorPagina Los registros por pagina a obtener.
     * @return Un listado de objetos con los datos de la vista.
     */
    private List<Object> findDerechosUsandoVistaYEntity(final ConsultaDerechosParam params,
            final Integer offset, final Integer registrosPorPagina) {
        final StringBuilder sb = new StringBuilder();

        sb.append("SELECT ");
        sb.append("DISTINCT (vcdc.idDerechoCapital), ");
        sb.append("vcdc.idTipoDerecho, ");
        sb.append("vcdc.descTipoDerecho, ");
        sb.append("vcdc.idSubtipoDerecho, ");
        sb.append("vcdc.descSubtipoDerecho, ");
        sb.append("vcdc.claveTipoValor, ");
        sb.append("vcdc.clavePizarra, ");
        sb.append("vcdc.serie, ");
        sb.append("vcdc.claveCupon, ");
        sb.append("vcdc.isin, ");
        sb.append("vcdc.fechaCorte, ");
        sb.append("vcdc.fechaPagoDerechoCapital, ");
        sb.append("vcdc.proporcion, ");
        sb.append("vcdc.claveAlfabetica, ");
        sb.append("vcdc.bovedaCustodio, ");
        sb.append("vcdc.descEstatusDerecho, ");
        sb.append("vcdc.idEmision, ");
        sb.append("vcdc.fee, ");
        sb.append("vcdc.idEstatusDerecho, ");
        sb.append("vcdc.bovedaValoresDerecho ");
        sb.append("FROM VConsultaDerechosCapital vcdc ");

        if (params.getInstitucion() != null) {
            sb.append(", " + PosicionLiquidacion.class.getName() + " pl, ");
            sb.append(CuentaNombrada.class.getName() + " cn, ");
            sb.append(Institucion.class.getName() + " ins ");
        }

        sb.append("WHERE 1 = 1 ");

        if (params.getInstitucion() != null) {
            sb.append("AND vcdc.idDerechoCapital = pl.idDerechoCapital ");
            sb.append("AND pl.idTipoDerecho in (" + Constantes.ID_DERECHO_DIVIDENDO_EN_EFECTIVO
                    + "," + Constantes.ID_DERECHO_DISTRIBUCION + ") ");
            sb.append("AND pl.cuenta.idCuentaNombrada = cn.idCuentaNombrada ");
            sb.append("AND cn.institucion.idInstitucion = ins.idInstitucion ");
            sb.append("AND ins.folioInstitucion = :folioInstitucion ");
            sb.append("AND ins.tipoInstitucion.idTipoInstitucion = :idTipoInstitucion ");
        }

        if (params.getEmision() != null && StringUtils.isNotBlank(params.getEmision().getTv())) {
            sb.append("AND vcdc.claveTipoValor = :tv ");
        }

        if (params.getEmision() != null && StringUtils.isNotBlank(params.getEmision().getEmisora())) {
            sb.append("AND vcdc.clavePizarra = :emisora ");
        }

        if (params.getEmision() != null && StringUtils.isNotBlank(params.getEmision().getSerie())) {
            sb.append("AND vcdc.serie = :serie ");
        }

        if (params.getEmision() != null && StringUtils.isNotBlank(params.getEmision().getCupon())) {
            sb.append("AND vcdc.claveCupon = :cupon ");
        }

        if (params.getEmision() != null && StringUtils.isNotBlank(params.getEmision().getIsin())) {
            sb.append("AND vcdc.isin = :isin ");
        }

        if (params.getIdDivisa() != null) {
            sb.append("AND vcdc.idDivisa = :idDivisa ");
        }

        if (params.getEmision() != null && params.getEmision().getIdBoveda() != null) {
            sb.append("AND vcdc.idCuentaNombrada = :idCuentaNombrada ");
        }

        if (params.getIdEstado() != null
                && params.getIdEstado() == Constantes.ID_ESTATUS_DERECHO_CORTADO.intValue()) {
            sb.append("AND vcdc.idEstatusDerechoCortado = "
                    + Constantes.ID_ESTATUS_DERECHO_CORTADO_MAV + " ");
        } else {
            sb.append("AND vcdc.idEstatusDerecho = :idEstatusDerecho ");
        }

        if (params.getFechaCorte() != null) {
            sb.append("AND vcdc.fechaCorte = :fechaCorte ");
        }

        if (params.getFechaPago() != null) {
            sb.append("AND vcdc.fechaPagoDerechoCapital = :fechaPagoDerechoCapital ");
        }

        sb.append("ORDER BY vcdc.fechaCorte, vcdc.claveTipoValor, vcdc.clavePizarra, vcdc.serie ");


        List<Object> listaDerechos =
                (List<Object>) this.getHibernateTemplate().execute(new HibernateCallback() {
                    public Object doInHibernate(final Session session) throws HibernateException,
                            SQLException {
                        Query query = session.createQuery(sb.toString());
                        query = DerechoDaoImpl.this.createDerechosCapitalParams(params, query);
                        if (registrosPorPagina.intValue() != PaginaVO.TODOS.intValue()) {
                            query.setMaxResults(registrosPorPagina);
                            query.setFetchSize(registrosPorPagina);
                            query.setFirstResult(offset);
                        }
                        return query.list();
                    }
                });

        return listaDerechos;
    }

    /**
     * @see com.indeval.portalinternacional.persistence.dao.DerechoDao#findDerechosUsandoVista(ConsultaDerechosParam,
     *      PaginaVO, boolean)
     */
    public PaginaVO findDerechosUsandoVista(final ConsultaDerechosParam params, PaginaVO paginaVO,
            final boolean all) {
        Long numeroDerechos = null;
        paginaVO = UtilsVO.getPaginaNotBlank(paginaVO);

        LOG.debug("####### Buscando derechos con los parametros " + params.toString());

        if (!all) {
            // Asignacion por default.
            paginaVO.setTotalRegistros(0);
            LOG.debug("####### Contando los derechos.....");
            numeroDerechos = this.countDerechosUsandoVista(params);
            LOG.debug("####### Numero de derechos [" + numeroDerechos + "]");
            if (numeroDerechos != null) {
                paginaVO.setTotalRegistros(numeroDerechos.intValue());
            }
        }

        if (paginaVO.getTotalRegistros() != null && paginaVO.getTotalRegistros().intValue() > 0) {
            LOG.debug("####### Buscando los derechos con vistas......");
            paginaVO.setRegistros(this.findDerechosUsandoVistaYEntity(params, paginaVO.getOffset(),
                    paginaVO.getRegistrosXPag()));
        } else {
            paginaVO.setRegistros(new ArrayList<VConsultaDerechosCapital>());
        }

        return paginaVO;
    }

    public void setJdbcTemplate(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public List<DerechoBeneficiario> findBenefDerechoXCuenta(final Long idDerecho,
            final Long[] idCuentas, final Integer tipoInstitucion, final String folioInstitucion) {
        LOG.info("\n\n####### Entrando a findBenefDerechoXCuenta()...\n\n");

        List<DerechoBeneficiario> lstBeneficiarios = null;
        Query query = null;
        DerechoBeneficiario tmpDerechoBenef = null;
        CustodioTipoBenef tmpCustodioTipoBenef = null;
        List<Object[]> rows = null;
        final StringBuilder sb = new StringBuilder();

        sb.append("SELECT 	DB, CTB	");
        sb.append("FROM		" + DerechoBeneficiario.class.getName() + "	DB	");
        sb.append("			JOIN FETCH DB.beneficiario B	");
        sb.append("			," + CustodioTipoBenef.class.getName() + " CTB	");
        sb.append("			JOIN FETCH DB.cuentaNombrada CN	");
        if (tipoInstitucion != null && StringUtils.isNotBlank(folioInstitucion)) {
            sb.append("		JOIN FETCH CN.institucion	I	");
        }
        sb.append("WHERE	DB.idDerechoCapital	= :idDerecho	");
        sb.append("			AND DB.procesado = false");
        sb.append("			AND CN.idCuentaNombrada IN (:idsCuentas) ");
        sb.append("			AND DB.eliminado = false	");
        sb.append("			AND B.tipoBeneficiario.idTipoBeneficiario = CTB.tipoBeneficiario.idTipoBeneficiario");
        sb.append("			AND B.idCuentaNombrada = CTB.idCuentaNombrada	");
        if (tipoInstitucion != null && StringUtils.isNotBlank(folioInstitucion)) {
            sb.append("		AND I.tipoInstitucion.idTipoInstitucion = :idTipoInstitucion	");
            sb.append("		AND I.folioInstitucion = :folioInstitucion");
        }

        query = this.getSession().createQuery(sb.toString());
        query.setParameter("idDerecho", idDerecho);
        query.setParameterList("idsCuentas", idCuentas);

        if (tipoInstitucion != null && StringUtils.isNotBlank(folioInstitucion)) {
            query.setParameter("idTipoInstitucion", tipoInstitucion.longValue());
            query.setParameter("folioInstitucion", folioInstitucion);
        }

        rows = query.list();
        lstBeneficiarios = new ArrayList<DerechoBeneficiario>();
        for (int i = 0; i < rows.size(); i++) {
            tmpDerechoBenef = (DerechoBeneficiario) rows.get(i)[0];
            tmpCustodioTipoBenef = (CustodioTipoBenef) rows.get(i)[1];
            if (tmpDerechoBenef.getBeneficiario().getTipoFormato().equalsIgnoreCase("W8BEN")
                    || tmpDerechoBenef.getBeneficiario().getTipoFormato()
                            .equalsIgnoreCase("W8BEN2014")
                    || tmpDerechoBenef.getBeneficiario().getTipoFormato()
                            .equalsIgnoreCase("W8BEN2017")
                    || tmpDerechoBenef.getBeneficiario().getTipoFormato()
                            .equalsIgnoreCase("W8BENE")
                    || tmpDerechoBenef.getBeneficiario().getTipoFormato()
                            .equalsIgnoreCase("W8BENE2016")) {
                if (tmpDerechoBenef.getBeneficiario().getFormatoW8BEN() != null) {
                    tmpDerechoBenef.getBeneficiario().getFormatoW8BEN().getTaxpayerIdNumb();
                }
                if (tmpDerechoBenef.getBeneficiario().getDomicilioW8Normal() != null) {
                    tmpDerechoBenef.getBeneficiario().getDomicilioW8Normal().getCalleNumeros();
                }
            } else if (tmpDerechoBenef.getBeneficiario().getTipoFormato().equalsIgnoreCase("W9")) {
                if (tmpDerechoBenef.getBeneficiario().getFormatoW9() != null) {
                    tmpDerechoBenef.getBeneficiario().getFormatoW9().getSsn();
                }
                if (tmpDerechoBenef.getBeneficiario().getDomicilioW9() != null) {
                    tmpDerechoBenef.getBeneficiario().getDomicilioW9().getCalleNumeros();
                }

            } else if (tmpDerechoBenef.getBeneficiario().getTipoFormato().equalsIgnoreCase("W8IMY")
                    || tmpDerechoBenef.getBeneficiario().getTipoFormato()
                            .equalsIgnoreCase("W8IMY2015")
                    || tmpDerechoBenef.getBeneficiario().getTipoFormato()
                            .equalsIgnoreCase("W8IMY2017")) {
                if (tmpDerechoBenef.getBeneficiario().getFormatoW8IMY() != null) {
                    tmpDerechoBenef.getBeneficiario().getFormatoW8IMY().getTaxpayerIdNumb();
                }
                if (tmpDerechoBenef.getBeneficiario().getDomicilioW8Normal() != null) {
                    tmpDerechoBenef.getBeneficiario().getDomicilioW8Normal().getCalleNumeros();
                }
            } else if (tmpDerechoBenef.getBeneficiario().getTipoFormato().equalsIgnoreCase("MILA")) {
                if (tmpDerechoBenef.getBeneficiario().getFormatoMILA() != null) {
                    tmpDerechoBenef.getBeneficiario().getFormatoMILA().getTipoDocumentoIndentidad();
                }
                if (tmpDerechoBenef.getBeneficiario().getDomicilioMILA() != null) {
                    tmpDerechoBenef.getBeneficiario().getDomicilioMILA().getCalleNumeros();
                }
            }
            if (tmpDerechoBenef.getBeneficiario().getPorcentajeRetencion() == null
                    || tmpDerechoBenef.getBeneficiario().getPorcentajeRetencion().intValue() < 0) {
                tmpDerechoBenef.getBeneficiario().setPorcentajeRetencion(
                        tmpCustodioTipoBenef.getPorcentajeRetencion().doubleValue());
            }
            lstBeneficiarios.add(tmpDerechoBenef);
        }


        return lstBeneficiarios;
    }

    
    
    public List<Object> getPosicionyPorcentajeDerechoXCuentaUsandoVista(final Long idDerecho, final Long[] idCuentas, final Integer tipoInstitucion, 
            final String folioInstitucion) {
    	
    	LOG.info("\n\n####### Entrando a getPosicionyPorcentajeDerechoXCuentaUsandoVista("+idDerecho+",["+Arrays.toString(idCuentas)+"],"+tipoInstitucion+","+folioInstitucion+")...\n\n");
    	final StringBuilder sb = new StringBuilder();
    	
    	sb.append(" SELECT vcbdc.posicion, ");
    	sb.append(" vcbdc.porcentajeRetencion, ");
    	sb.append(" vcbdc.porcentajeRetencionCustodio, ");
    	sb.append(" vcbdc.idCuentaNombrada ");
    	sb.append(" FROM VConsultaBeneficiarioDerechosCuenta vcbdc ");
    	sb.append(" WHERE vcbdc.idDerechoCapital = :idDerecho ");
    	sb.append("      AND vcbdc.idCuentaNombrada IN (");
        for (int i = 0; i < idCuentas.length; i++) {
            sb.append(idCuentas[i]);
            if ((i + 1) < idCuentas.length) {
                sb.append(",");
            }
        }
        sb.append("      ) ");
        
    	if (tipoInstitucion != null && StringUtils.isNotBlank(folioInstitucion)) {
            sb.append("      AND vcbdc.idTipoInstitucion = :idTipoInstitucion ");
            sb.append("      AND vcbdc.folioInstitucion = :folioInstitucion ");
        }
    	
    	LOG.info("\n\n####### Seteando parametros y ejecutando queries...\n\n");
    	
    	 
    	List<Object> registros=(List<Object>)getHibernateTemplate().execute(new HibernateCallback() {
             public Object doInHibernate(final Session session) throws HibernateException,
                     SQLException {
                 Query query = session.createQuery(sb.toString());
                 query.setLong("idDerecho", idDerecho);
                 
                 if (tipoInstitucion != null && StringUtils.isNotBlank(folioInstitucion)) {
                     query.setLong("idTipoInstitucion", tipoInstitucion);
                     query.setString("folioInstitucion", folioInstitucion);
                 }
                 return query.list();
             }
         });
    	 
    	 return registros;
    }
    
    
   
    
    /**
     *  @see com.indeval.portalinternacional.persistence.dao.DerechoDao.findBenefDerechoXCuentaUsandoVista(Long,Long[],Integer,String)
     */
    public List<Object> findBenefDerechoXCuentaUsandoVista(final Long idDerecho, final Long[] idCuentas, final Integer tipoInstitucion, 
                                                           final String folioInstitucion) {
        LOG.info("\n\n####### Entrando a findBenefDerechoXCuentaUsandoVista("+idDerecho+",["+Arrays.toString(idCuentas)+"],"+tipoInstitucion+","+folioInstitucion+")...\n\n");

        final StringBuilder sb = new StringBuilder();
        Query query = null;
        List<VConsultaBeneficiarioDerechosCuenta> rows = null;
        VConsultaBeneficiarioDerechosCuenta beneficiario = null;
        List<VConsultaBeneficiarioDerechosCuenta> listadoBeneficiarios = new ArrayList<VConsultaBeneficiarioDerechosCuenta>();
        final List<Object> paramsSQL = new ArrayList<Object>();
        List<Object> registros = new ArrayList<Object>();

        LOG.info("\n\n####### Creando el query...\n\n");
        sb.append("SELECT vcbdc.cuenta, ");
        sb.append("       vcbdc.idCuentaNombrada, ");
        sb.append("       vcbdc.folioInstitucion, ");
        sb.append("       vcbdc.idDerechoBeneficiario, ");
        sb.append("       vcbdc.idDerechoCapital, ");
        sb.append("       vcbdc.nombreCompleto, ");
        sb.append("       vcbdc.porcentajeRetencion, ");
        sb.append("       vcbdc.posicion, ");
        sb.append("       vcbdc.uoi, ");
        sb.append("       vcbdc.adpNumber, ");
        sb.append("       vcbdc.tipoFormato, ");
        sb.append("       vcbdc.descTipoBeneficiario, ");
        sb.append("       vcbdc.idTipoInstitucion, ");
        sb.append("       vcbdc.idCamposFormatoW8BEN, ");
        sb.append("       vcbdc.rfcFW8BEN, ");
        sb.append("       vcbdc.idCamposFormatoW8BENE, ");
        sb.append("       vcbdc.rfcFW8BENE, ");
        sb.append("       vcbdc.idDomicilioW8Normal, ");
        sb.append("       vcbdc.contryDomW8NORMAL, ");
        sb.append("       vcbdc.streetDomW8NORMAL, ");
        sb.append("       vcbdc.outernumberDomW8NORMAL, ");
        sb.append("       vcbdc.interiornumberDomW8NORMAL, ");
        sb.append("       vcbdc.postalcodeDomW8NORMAL, ");
        sb.append("       vcbdc.citytownDomW8NORMAL, ");
        sb.append("       vcbdc.stateprovinceDomW8NORMAL, ");
        sb.append("       vcbdc.idCamposFormatoW9, ");
        sb.append("       vcbdc.rfcFW9, ");
        sb.append("       vcbdc.idDomicilioW9, ");
        sb.append("       vcbdc.contryDomW9, ");
        sb.append("       vcbdc.streetDomW9, ");
        sb.append("       vcbdc.outernumberDomW9, ");
        sb.append("       vcbdc.interiornumberDomW9, ");
        sb.append("       vcbdc.postalcodeDomW9, ");
        sb.append("       vcbdc.citytownDomW9, ");
        sb.append("       vcbdc.stateprovinceDomW9, ");
        sb.append("       vcbdc.idCamposFormatoW8IMY, ");
        sb.append("       vcbdc.rfcFW8IMY, ");
        sb.append("       vcbdc.idCamposFormatoW8IMY2015, ");
        sb.append("       vcbdc.rfcFW8IMY2015, ");
        sb.append("       vcbdc.contryDomW8NORMALDEW8IMY, ");
        sb.append("       vcbdc.streetDomW8NORMALDEW8IMY, ");
        sb.append("       vcbdc.outnumDomW8NORMALDEW8IMY, ");
        sb.append("       vcbdc.intnumDomW8NORMALDEW8IMY, ");
        sb.append("       vcbdc.poscodDomW8NORMALDEW8IMY, ");
        sb.append("       vcbdc.cittowDomW8NORMALDEW8IMY, ");
        sb.append("       vcbdc.staproDomW8NORMALDEW8IMY, ");
        sb.append("       vcbdc.idCamposFormatoMILA, ");
        sb.append("       vcbdc.rfcFMILA, ");
        sb.append("       vcbdc.rfcFMILANumeroDocumento, ");
        sb.append("       vcbdc.idDomicilioMILA, ");
        sb.append("       vcbdc.contryDomMILA, ");
        sb.append("       vcbdc.streetDomMILA, ");
        sb.append("       vcbdc.outnumberDomMILA, ");
        sb.append("       vcbdc.interiornumberDomMILA, ");
        sb.append("       vcbdc.postalcodeDomMILA, ");
        sb.append("       vcbdc.citytownDomMILA, ");
        sb.append("       vcbdc.stateprovinceDomMILA, ");
        sb.append("       vcbdc.porcentajeRetencionCustodio ");
        sb.append("FROM VConsultaBeneficiarioDerechosCuenta vcbdc ");
        sb.append("WHERE vcbdc.idDerechoCapital = :idDerecho ");
        sb.append("      AND vcbdc.idCuentaNombrada IN (");
        for (int i = 0; i < idCuentas.length; i++) {
            sb.append(idCuentas[i]);
            if ((i + 1) < idCuentas.length) {
                sb.append(",");
            }
        }
        sb.append("      ) ");
        if (tipoInstitucion != null && StringUtils.isNotBlank(folioInstitucion)) {
            sb.append("      AND vcbdc.idTipoInstitucion = :idTipoInstitucion ");
            sb.append("      AND vcbdc.folioInstitucion = :folioInstitucion ");
        }


        //LOG.info("\n\n####### Seteando parametros y ejecutando queries...\n\n");
        registros =
                (List<Object>) this.getHibernateTemplate().execute(new HibernateCallback() {
                    public Object doInHibernate(final Session session) throws HibernateException,
                            SQLException {
                        Query query = session.createQuery(sb.toString());
                        query.setLong("idDerecho", idDerecho);
                        if (tipoInstitucion != null && StringUtils.isNotBlank(folioInstitucion)) {
                            query.setLong("idTipoInstitucion", tipoInstitucion);
                            query.setString("folioInstitucion", folioInstitucion);
                        }
                        return query.list();
                    }
                });

        return registros;
    }


    public DerechoBeneficiario getBeneficiarioDerecho(final Long idBeneficiarioDerecho) {
        DerechoBeneficiario beneficiario =
                (DerechoBeneficiario) this
                        .getByPk(DerechoBeneficiario.class, idBeneficiarioDerecho);

        return beneficiario;
    }

    public Long getCuentaDerechoByUoi(final Long idDerecho, final String cuenta,
            final Institucion institucion, final Long idBeneficiario) {

        Long idCuenta = null;
        List lstCuentas = null;
        final StringBuilder sb = new StringBuilder();
        final List<Object> paramsSQL = new ArrayList<Object>();

        sb.append("SELECT	PL,CN.idCuentaNombrada	");
        sb.append("FROM		" + PosicionLiquidacion.class.getName() + " PL	");
        sb.append("			JOIN FETCH PL.cuenta CN JOIN FETCH CN.institucion I JOIN FETCH I.tipoInstitucion TI,	");
        sb.append("			" + BeneficiarioInstitucion.class.getName() + " BI	");
        sb.append("WHERE	PL.idDerechoCapital = ?	");
        paramsSQL.add(idDerecho);
        sb.append("			AND	PL.idTipoDerecho = ?	");
        paramsSQL.add(Integer.valueOf(1));
        sb.append("			AND	CN.cuenta = ?	");
        paramsSQL.add(cuenta);
        if (institucion != null && StringUtils.isNotBlank(institucion.getFolioInstitucion())
                && institucion.getTipoInstitucion() != null
                && institucion.getTipoInstitucion().getIdTipoInstitucion() != null) {
            sb.append("			AND TI.idTipoInstitucion = ?	");
            paramsSQL.add(institucion.getTipoInstitucion().getIdTipoInstitucion());
            sb.append("			AND	I.folioInstitucion = ?	");
            paramsSQL.add(institucion.getFolioInstitucion());
        }
        sb.append("			AND I.idInstitucion = BI.institucion	");
        sb.append("			AND BI.beneficiario = ?	");
        paramsSQL.add(idBeneficiario);

        lstCuentas =
                this.getHibernateTemplate().find(sb.toString(), paramsSQL.toArray(new Object[] {}));

        if (lstCuentas != null && !lstCuentas.isEmpty()) {
            idCuenta = (Long) ((Object[]) lstCuentas.get(0))[1];
        }

        LOG.debug("id de la cuenta----->" + idCuenta);

        return idCuenta;
    }

    public DerechoBeneficiario getBeneficiarioDerecho(final Long idBeneficiario,
            final Long idCuentaNombrada, final Long idDerechoCapital) {

        DerechoBeneficiario derechoBeneficiario = null;
        List<DerechoBeneficiario> lstBeneficiarios = null;
        final StringBuilder sb = new StringBuilder();
        final List<Object> paramsSQL = new ArrayList<Object>();

        sb.append("SELECT 	DB	");
        sb.append("FROM		" + DerechoBeneficiario.class.getName() + " DB	");
        sb.append("			JOIN FETCH DB.cuentaNombrada CN	");
        sb.append("			JOIN FETCH DB.beneficiario B	");
        sb.append("WHERE	DB.idDerechoCapital = ?	");
        paramsSQL.add(idDerechoCapital);
        sb.append("			AND CN.idCuentaNombrada = ?	");
        paramsSQL.add(idCuentaNombrada);
        sb.append("			AND DB.beneficiario.idBeneficiario = ? ");
        paramsSQL.add(idBeneficiario);
        sb.append("			AND DB.eliminado = false	");

        lstBeneficiarios =
                this.getHibernateTemplate().find(sb.toString(), paramsSQL.toArray(new Object[] {}));
        if (lstBeneficiarios != null && !lstBeneficiarios.isEmpty()) {
            derechoBeneficiario = lstBeneficiarios.get(0);
        }

        return derechoBeneficiario;
    }


    public DerechoBeneficiario getBenefDefaultDerecho(final Long idDerechoCapital) {
        DerechoBeneficiario derechoBeneficiario = null;
        List<DerechoBeneficiario> lstBeneficiarios = null;
        final StringBuilder sb = new StringBuilder();
        final List<Object> paramsSQL = new ArrayList<Object>();

        sb.append("SELECT 	DB	");
        sb.append("FROM		" + DerechoBeneficiario.class.getName() + " DB	");
        sb.append("WHERE	DB.idDerechoCapital = ?	");
        paramsSQL.add(idDerechoCapital);
        sb.append("			AND DB.cuentaNombrada is null	");
        sb.append("			AND DB.beneficiario is null ");
        sb.append("			AND DB.asignacion is null ");
        sb.append("			AND DB.eliminado = false	");

        lstBeneficiarios =
                this.getHibernateTemplate().find(sb.toString(), paramsSQL.toArray(new Object[] {}));
        if (lstBeneficiarios != null && !lstBeneficiarios.isEmpty()) {
            derechoBeneficiario = lstBeneficiarios.get(0);
        }

        return derechoBeneficiario;
    }

    public Beneficiario findBeneficiarioCuentaByUoi(final String uoi, final Long idCuentaNombrada) {

        Beneficiario beneficiario = null;

        final StringBuilder sb = new StringBuilder();
        List<Object> paramsSQL = new ArrayList<Object>();

        sb.append(" SELECT  B, CTB ");
        sb.append(" FROM " + Beneficiario.class.getName() + " B ");
        sb.append("			JOIN FETCH B.institucion I, ");
        sb.append("			" + CuentaNombrada.class.getName() + " CN ");
        sb.append("			," + CustodioTipoBenef.class.getName() + " CTB	");
        sb.append(" WHERE B.uoiNumber like ? ");
        paramsSQL.add(uoi);
        sb.append("		  AND I.idInstitucion = CN.institucion.idInstitucion ");
        sb.append("		  AND CN.idCuentaNombrada = ?");
        sb.append("		  AND B.tipoBeneficiario.idTipoBeneficiario = CTB.tipoBeneficiario.idTipoBeneficiario");
        sb.append("		  AND B.idCuentaNombrada = CTB.idCuentaNombrada	");
        paramsSQL.add(idCuentaNombrada);


        List<Object[]> lstBeneficiarios =
                this.getHibernateTemplate().find(sb.toString(), paramsSQL.toArray(new Object[] {}));
        if (lstBeneficiarios != null && !lstBeneficiarios.isEmpty()) {
            beneficiario = (Beneficiario) lstBeneficiarios.get(0)[0];
            if (beneficiario.getPorcentajeRetencion() == null) {
                beneficiario
                        .setPorcentajeRetencion(((CustodioTipoBenef) lstBeneficiarios.get(0)[1])
                                .getPorcentajeRetencion().doubleValue());
            }
        }

        return beneficiario;
    }

    public PaginaVO findCuentasDerecho(final Long idDerechoCapital, final Integer tipoInstitucion,
            final String folioInstitucion, PaginaVO paginaVO) {

        Long numerocuentas = null;
        paginaVO = UtilsVO.getPaginaNotBlank(paginaVO);

        LOG.info("\n\n####### Contando las cuentas.....\n\n");
        numerocuentas =
                this.countCuentasDerecho(idDerechoCapital, tipoInstitucion, folioInstitucion);
        LOG.info("\n\n####### numero de cuentas [" + numerocuentas + "]\n\n");
        if (numerocuentas != null) {
            paginaVO.setTotalRegistros(numerocuentas.intValue());
        } else {
            paginaVO.setTotalRegistros(0);
        }

        if (paginaVO.getTotalRegistros() != null && paginaVO.getTotalRegistros().intValue() > 0) {
            LOG.info("\n\n####### Buscando las cuentas......\n\n");
            paginaVO.setRegistros(this.findCuentasWithHibernate(idDerechoCapital, tipoInstitucion,
                    folioInstitucion, paginaVO.getOffset(), paginaVO.getRegistrosXPag()));
            LOG.info("\n\n####### cuentas encontradas\n\n");
        } else {
            paginaVO.setRegistros(new ArrayList<Derecho>());
        }

        return paginaVO;
    }

    /**
     * @see com.indeval.portalinternacional.persistence.dao.DerechoDao.findCuentasDerechoWithoutEntities(Long,Integer,String,PaginaVO)
     */
    public PaginaVO findCuentasDerechoWithoutEntities(final Long idDerechoCapital, final Integer tipoInstitucion,
                                                      final String folioInstitucion, PaginaVO paginaVO) {
        Long numerocuentas = null;
        paginaVO = UtilsVO.getPaginaNotBlank(paginaVO);

        LOG.info("\n\n####### Contando las cuentas.....\n\n");
        numerocuentas = this.countCuentasDerecho(idDerechoCapital, tipoInstitucion, folioInstitucion);
        LOG.info("\n\n####### numero de cuentas=[" + numerocuentas + "]\n\n");

        paginaVO.setTotalRegistros(0);
        if (numerocuentas != null) {
            paginaVO.setTotalRegistros(numerocuentas.intValue());
        }

        if (paginaVO.getTotalRegistros() != null && paginaVO.getTotalRegistros().intValue() > 0) {
            LOG.info("\n\n####### Buscando las cuentas......\n\n");
            paginaVO.setRegistros(this.findCuentasWithoutEntities(idDerechoCapital, tipoInstitucion, folioInstitucion, 
                                                                  paginaVO.getOffset(), paginaVO.getRegistrosXPag()));
            LOG.info("\n\n####### cuentas encontradas=[" + paginaVO.getTotalRegistros() + "]\n\n");
        }
        else {
            paginaVO.setRegistros(new ArrayList<Derecho>());
        }

        return paginaVO;
    }

    private long countCuentasDerecho(final Long idDerechoCapital, final Integer idTipoInstitucion,
            final String folioInstitucion) {
        StringBuilder sb = new StringBuilder();
        List<Object> paramsSQL = new ArrayList<Object>();

        sb.append("SELECT 	COUNT(*)	");
        sb.append("FROM 	T_POSICIONES_LIQUIDACION PL	");
        if (idTipoInstitucion != null && StringUtils.isNotBlank(folioInstitucion)) {
            sb.append("		,C_CUENTA_NOMBRADA CN, C_INSTITUCION I	");
        }
        sb.append("WHERE	PL.ID_DERECHO = ?	");
        paramsSQL.add(idDerechoCapital);
        sb.append("		AND	PL.ID_TIPO_DERECHO in (?,?)");
        paramsSQL.add(Integer.valueOf(Constantes.ID_DERECHO_DIVIDENDO_EN_EFECTIVO));
        paramsSQL.add(Integer.valueOf(Constantes.ID_DERECHO_DISTRIBUCION));
        if (idTipoInstitucion != null && StringUtils.isNotBlank(folioInstitucion)) {
            sb.append("		AND	PL.ID_CUENTA = CN.ID_CUENTA_NOMBRADA	");
            sb.append("		AND	CN.ID_INSTITUCION = I.ID_INSTITUCION	");
            sb.append("		AND	I.ID_TIPO_INSTITUCION = ?	");
            sb.append("		AND	I.FOLIO_INSTITUCION = ?	");
            paramsSQL.add(idTipoInstitucion);
            paramsSQL.add(folioInstitucion);
        }

        return this.jdbcTemplate.queryForLong(sb.toString(), paramsSQL.toArray(new Object[0]));
    }

    private List<PosicionLiquidacion> findCuentasWithHibernate(final Long idDerechoCapital,
            final Integer idTipoInstitucion, final String folioInstitucion, final Integer offset,
            final Integer registrosPorPagina) {
        LOG.info("\n\n####### Entrando a findCuentasWithHibernate()...\n\n");
        List<PosicionLiquidacion> lstCuentas = null;
        final StringBuilder sb = new StringBuilder();
        final List<Object> paramsSQL = new ArrayList<Object>();
        final List<Type> tipos = new ArrayList<Type>();

        LOG.info("\n\n####### Armando query...\n\n");
        sb.append("SELECT 	PL	");
        sb.append("FROM		" + PosicionLiquidacion.class.getName() + "	PL	");
        if (idTipoInstitucion != null && StringUtils.isNotBlank(folioInstitucion)) {
            sb.append("		JOIN FETCH PL.cuenta CN	");
            sb.append("		JOIN FETCH CN.institucion	I	");
        }
        sb.append("WHERE	PL.idDerechoCapital	= ?	");
        paramsSQL.add(idDerechoCapital);
        tipos.add(new LongType());
        sb.append("			AND PL.idTipoDerecho in (?,?) ");
        paramsSQL.add(Integer.valueOf(Constantes.ID_DERECHO_DIVIDENDO_EN_EFECTIVO));
        tipos.add(new IntegerType());
        paramsSQL.add(Integer.valueOf(Constantes.ID_DERECHO_DISTRIBUCION));
        tipos.add(new IntegerType());
        if (idTipoInstitucion != null && StringUtils.isNotBlank(folioInstitucion)) {
            sb.append("		AND I.tipoInstitucion.idTipoInstitucion = ?	");
            paramsSQL.add(idTipoInstitucion);
            tipos.add(new IntegerType());
            sb.append("		AND I.folioInstitucion = ?");
            paramsSQL.add(folioInstitucion);
            tipos.add(new StringType());
        }

        LOG.info("\n\n####### Query armado...\n\n");
        LOG.info("\n\n####### Query=[" + sb.toString() + "]\n\n");
        LOG.info("\n\n####### Realizando consulta...\n\n");
        lstCuentas =
                (List<PosicionLiquidacion>) this.getHibernateTemplate().execute(
                        new HibernateCallback() {
                            public Object doInHibernate(final Session session)
                                    throws HibernateException, SQLException {
                                Query query = session.createQuery(sb.toString());
                                query.setParameters(paramsSQL.toArray(new Object[0]),
                                        tipos.toArray(new Type[0]));
                                if (registrosPorPagina.intValue() != PaginaVO.TODOS.intValue()) {
                                    query.setFirstResult(offset.intValue());
                                    query.setMaxResults(registrosPorPagina);
                                }
                                return query.list();
                            }
                        });

        return lstCuentas;
    }

    /**
     * Encuentra las cuentas sin usar entidades de hibernate, solo query puro.
     * @param idDerechoCapital
     * @param idTipoInstitucion
     * @param folioInstitucion
     * @param offset
     * @param registrosPorPagina
     * @return Un listado de objetos PosicionCuentaVO.
     */
    private List<PosicionCuentaVO> findCuentasWithoutEntities(final Long idDerechoCapital, final Integer idTipoInstitucion, 
                                                              final String folioInstitucion, final Integer offset,
                                                              final Integer registrosPorPagina) {
        LOG.info("\n\n####### Entrando a findCuentasWithoutEntities()...\n\n");
        StringBuilder sql = new StringBuilder();
        List<Object> paramsSQL = new ArrayList<Object>();
        List<Object> resultados = null;
        List<PosicionCuentaVO> listadoPosiciones = new ArrayList<PosicionCuentaVO>();

        LOG.info("\n\n####### Armando query...\n\n");
        sql.append(" SELECT CN.ID_CUENTA_NOMBRADA idcuentanombrada, ");
        sql.append("        CN.CUENTA cuenta, ");
        sql.append("        PL.SALDO saldo, ");
        sql.append("        I.NOMBRE_CORTO nombrecorto, ");
        sql.append("        PL.ID_EMISION idemision, ");
        sql.append("        CONCAT( ");
        sql.append("          CASE WHEN LENGTH(TO_CHAR(I.ID_TIPO_INSTITUCION)) = 1 ");
        sql.append("            THEN CONCAT('0',I.ID_TIPO_INSTITUCION) ");
        sql.append("            ELSE TO_CHAR(I.ID_TIPO_INSTITUCION) ");
        sql.append("          END, ");
        sql.append("          I.FOLIO_INSTITUCION ");
        sql.append("        ) idfolio ");
        sql.append(" FROM T_POSICIONES_LIQUIDACION PL ");
        sql.append(" , C_CUENTA_NOMBRADA CN, C_INSTITUCION I ");
        sql.append(" WHERE PL.ID_DERECHO = ? ");
        paramsSQL.add(idDerechoCapital);
        sql.append(" AND PL.ID_TIPO_DERECHO in (?,?) ");
        paramsSQL.add(Integer.valueOf(Constantes.ID_DERECHO_DIVIDENDO_EN_EFECTIVO));
        paramsSQL.add(Integer.valueOf(Constantes.ID_DERECHO_DISTRIBUCION));
        sql.append(" AND PL.ID_CUENTA = CN.ID_CUENTA_NOMBRADA ");
        sql.append(" AND CN.ID_INSTITUCION = I.ID_INSTITUCION ");
        if (idTipoInstitucion != null && StringUtils.isNotBlank(folioInstitucion)) {
            sql.append(" AND I.ID_TIPO_INSTITUCION = '" + idTipoInstitucion + "' ");
            sql.append(" AND I.FOLIO_INSTITUCION = '" + folioInstitucion + "' ");
        }
        if (registrosPorPagina != null && registrosPorPagina.intValue() != PaginaVO.TODOS.intValue()) {
            sql.append(" OFFSET " + offset + " ROWS FETCH NEXT " + registrosPorPagina + " ROWS ONLY ");
        }

        LOG.info("\n\n####### Query armado...\n\n");
        LOG.info("\n\n####### Query=[" + sql.toString() + "]\n\n");

        LOG.info("\n\n####### Realizando consulta...\n\n");
        resultados = this.getJdbcTemplate().queryForList(sql.toString(), paramsSQL.toArray(new Object[0]));

        if (resultados != null && !resultados.isEmpty()) {
            Iterator registrosIterador = resultados.iterator();
            while (registrosIterador.hasNext()) {
                ListOrderedMap registro = (ListOrderedMap) registrosIterador.next();
                PosicionCuentaVO posicionVO = new PosicionCuentaVO();
                posicionVO.setIdCuentaNombrada(((BigDecimal) registro.get("idcuentanombrada")).longValue());
                posicionVO.setCuenta((String) registro.get("cuenta"));
                posicionVO.setPosicion(((BigDecimal) registro.get("saldo")).longValue());
                posicionVO.setNombreInstitucion((String) registro.get("nombrecorto"));
                posicionVO.setIdEmision(((BigDecimal) registro.get("idemision")).longValue());
                posicionVO.setClaveInstitucion((String) registro.get("idfolio"));
                listadoPosiciones.add(posicionVO);
            }
        }

        LOG.info("\n\n####### Registros en la consulta=[" + listadoPosiciones.size() + "]\n\n");
        return listadoPosiciones;
    }

    public List<DerechoBeneficiario> findBeneficiariosDerecho(final Long idDerecho,
            final Boolean procesados) {
        final StringBuilder sb = new StringBuilder();
        List<Object> paramsSQL = new ArrayList<Object>();
        sb.append("SELECT	DB	");
        sb.append("FROM		" + DerechoBeneficiario.class.getName() + " DB ");
        sb.append("WHERE	DB.idDerechoCapital = ? ");
        paramsSQL.add(idDerecho);
        sb.append("			AND	DB.procesado = ?	");
        paramsSQL.add(procesados);
        sb.append("			AND	DB.eliminado = false ");

        return this.getHibernateTemplate().find(sb.toString(), paramsSQL.toArray(new Object[] {}));
    }

    public DerechoBeneficiario getDerechoNoCortado(final Long idDerecho) {
        final StringBuilder sb = new StringBuilder();
        List<Object> paramsSQL = new ArrayList<Object>();
        List<DerechoBeneficiario> lstDerechos = null;
        DerechoBeneficiario derechoBeneficiario = null;

        sb.append(" SELECT  DISTINCT DB ");
        sb.append(" FROM " + DerechoBeneficiario.class.getName() + " DB ");
        sb.append("			JOIN FETCH DB.estatusDerecho E ");
        sb.append(" WHERE DB.idDerechoCapital = ? ");
        paramsSQL.add(idDerecho);
        sb.append("		  AND DB.eliminado = false");
        sb.append("		  AND DB.estatusDerecho is not null");
        sb.append("		  AND E.idEstatusDerecho != " + Constantes.ID_ESTATUS_DERECHO_CORTADO);

        lstDerechos =
                this.getHibernateTemplate().find(sb.toString(), paramsSQL.toArray(new Object[] {}));

        if (lstDerechos != null && !lstDerechos.isEmpty()) {
            derechoBeneficiario = lstDerechos.get(0);
        }

        return derechoBeneficiario;
    }

    public Derecho findDerecho(final ConsultaDerechosParam param, final int tipo, final int subtipo) {
        List<Derecho> lstDerechos = null;
        Derecho derecho = null;
        final StringBuilder sb = new StringBuilder();
        final List<Object> paramsSQL = new ArrayList<Object>();
        final List<Type> tipos = new ArrayList<Type>();

        sb.append("SELECT	dc ");
        sb.append("FROM		" + Derecho.class.getName() + " dc	");
        sb.append("		JOIN dc.elementEstatusDerecho ed	");
        sb.append("		JOIN FETCH dc.tipoDerecho td");
        if (subtipo > 0) {
            sb.append("		JOIN FETCH dc.subTipoDerecho sd");
        }
        sb.append("		JOIN FETCH dc.cuponOrigen c	");
        sb.append("		JOIN FETCH c.emision e	");
        sb.append("		JOIN FETCH e.emisora ea	");
        sb.append("		JOIN FETCH e.instrumento i	");
        sb.append("WHERE	td.idTipoDerecho = ?");
        paramsSQL.add(Long.valueOf(tipo));
        tipos.add(new LongType());
        sb.append("		AND	ed.idStatusDerecho = " + Constantes.ID_ESTATUS_DERECHO_CORTADO_MAV);

        if (subtipo > 0) {
            sb.append("		AND sd.idSubtipoDerecho = ?");
            paramsSQL.add(Long.valueOf(subtipo));
            tipos.add(new LongType());
        }

        sb.append("		AND	dc.fechaCorte = ?	");
        paramsSQL.add(new Date(param.getFechaCorte().getTime()));
        tipos.add(new DateType());
        sb.append("		AND	c.claveCupon = ?	");
        paramsSQL.add(param.getEmision().getCupon());
        tipos.add(new StringType());
        sb.append("		AND e.serie = ?	");
        paramsSQL.add(param.getEmision().getSerie());
        tipos.add(new StringType());
        sb.append("		AND	ea.clavePizarra = ?	");
        paramsSQL.add(param.getEmision().getEmisora());
        tipos.add(new StringType());
        sb.append("			AND i.esExtranjera = " + Constantes.ID_EMISION_EXTRANJERA);
        sb.append("		AND	i.claveTipoValor = ?	");
        paramsSQL.add(param.getEmision().getTv());
        tipos.add(new StringType());

        lstDerechos = (List<Derecho>) this.getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(final Session session) throws HibernateException,
                    SQLException {
                Query query = session.createQuery(sb.toString());
                query.setParameters(paramsSQL.toArray(new Object[0]), tipos.toArray(new Type[0]));
                return query.list();
            }
        });

        LOG.debug("Derechos encontrados [" + lstDerechos.size() + "]");
        if (lstDerechos != null && lstDerechos.size() == 1) {
            derecho = lstDerechos.get(0);
        }

        return derecho;
    }


    public CuentaNombrada findCuenta(final String cuenta, final Integer idTipoInstitucion,
            final String folioInstitucion) {
        List<CuentaNombrada> result = null;
        CuentaNombrada nombrada = null;
        final StringBuilder sb = new StringBuilder();
        List<Object> paramsSQL = new ArrayList<Object>();
        sb.append("SELECT	C	");
        sb.append("FROM		" + CuentaNombrada.class.getName() + " C ");
        sb.append("			JOIN FETCH C.institucion I ");
        sb.append("WHERE	C.cuenta = ? ");
        paramsSQL.add(cuenta);
        sb.append("			AND	I.tipoInstitucion.idTipoInstitucion = ?	");
        paramsSQL.add(Long.valueOf(idTipoInstitucion.intValue()));
        sb.append("			AND	I.folioInstitucion = ? ");
        paramsSQL.add(folioInstitucion);

        result =
                this.getHibernateTemplate().find(sb.toString(), paramsSQL.toArray(new Object[] {}));

        if (result != null && !result.isEmpty()) {
            nombrada = result.get(0);
        }

        return nombrada;
    }
    
	@Override
	public List<DerechoCapitalHistoricoVo> consultaDerechoCapitalHistorico(final Long idDerechoCapital) {
		final String sb = queryConsultaDerechoCapitalHistorico();
		
		List<DerechoCapitalHistoricoVo> lstDerechoCapitalHistorico = (List<DerechoCapitalHistoricoVo>) this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(final Session session) throws HibernateException, SQLException {

				final SQLQuery query = session.createSQLQuery(sb.toString());
				query.setLong("idDerechoCapital", idDerechoCapital);
				
				query.addScalar("idDerechoCapital", Hibernate.LONG);
				query.addScalar("claveTipoValor", Hibernate.STRING);
				query.addScalar("clavePizarra", Hibernate.STRING);
				query.addScalar("serie", Hibernate.STRING);
				query.addScalar("isin", Hibernate.STRING);
				query.addScalar("fechaPagoDerechoCapital", Hibernate.DATE);
				query.addScalar("fechaCorte", Hibernate.DATE);
				query.addScalar("idCatBic", Hibernate.LONG);
				query.addScalar("detalleCustodio", Hibernate.STRING);
				query.addScalar("idFolio", Hibernate.STRING);
				query.addScalar("idEmision", Hibernate.LONG);
				query.addScalar("idCuentaNombradaBeneficiario", Hibernate.LONG);
				query.addScalar("idCuentaNombradaCuenta", Hibernate.LONG);
				query.addScalar("cuenta", Hibernate.STRING);
				query.addScalar("saldo", Hibernate.LONG);
				query.addScalar("idDivisa", Hibernate.LONG);
				query.addScalar("idBeneficiario", Hibernate.LONG);
				query.addScalar("adp", Hibernate.STRING);
				query.addScalar("uoi", Hibernate.STRING);
				query.addScalar("nombreBenef", Hibernate.STRING);
				query.addScalar("apellidoPaternoBenef", Hibernate.STRING);
				query.addScalar("apellidoMaternoBenef", Hibernate.STRING);
				query.addScalar("razonSocialBenef", Hibernate.STRING);
				query.addScalar("paisIncorporacion", Hibernate.STRING);
				query.addScalar("idTipoBeneficiario", Hibernate.LONG);
				query.addScalar("domicilio", Hibernate.LONG);
				query.addScalar("tipoFormato", Hibernate.STRING);
				query.addScalar("proporcion", Hibernate.DOUBLE);
				query.addScalar("fee", Hibernate.DOUBLE);
				query.addScalar("porcentajeMav", Hibernate.DOUBLE);
				query.addScalar("porcentajeBen", Hibernate.LONG);
				query.addScalar("porcentajeHBDefault", Hibernate.DOUBLE);
				query.addScalar("porcentajeRetencionCTB", Hibernate.LONG);
				query.addScalar("asignacion", Hibernate.LONG);
				query.addScalar("idTipoDerecho", Hibernate.LONG);
				query.addScalar("idEstatusDerechoMav", Hibernate.LONG);
				query.addScalar("idEstatusDerecho", Hibernate.LONG);
				query.addScalar("claveAlfabetica", Hibernate.STRING);
				query.addScalar("descEstatusDerecho", Hibernate.STRING);
				query.addScalar("fechaFormato", Hibernate.DATE);
				query.addScalar("estatusBeneficiario", Hibernate.STRING);
				query.addScalar("descTipoDerecho", Hibernate.STRING);
				query.addScalar("id", Hibernate.LONG);
				
				query.setResultTransformer(new AliasToBeanResultTransformer(DerechoCapitalHistoricoVo.class));
				
				return query.list();
			}
		});
		
		return lstDerechoCapitalHistorico;
	}
	
    /** 
	 * Query para obtener el Derecho Capotal Historico
     * @return
     */
    private String queryConsultaDerechoCapitalHistorico(){
    	
    	StringBuilder sb = new StringBuilder();
    	
		sb.append("SELECT DISTINCT QUERY.ID_DERECHO_CAPITAL AS idDerechoCapital, QUERY.CLAVE_TIPO_VALOR AS claveTipoValor, QUERY.CLAVE_PIZARRA AS clavePizarra, QUERY.SERIE AS serie, QUERY.ISIN AS isin,");
		sb.append(" QUERY.FECHA_PAGO_DERECHO_CAPITAL AS fechaPagoDerechoCapital, QUERY.FECHA_CORTE AS fechaCorte, QUERY.ID_CATBIC AS idCatBic, QUERY.DETALLE_CUSTODIO AS detalleCustodio, QUERY.ID_FOLIO AS idFolio,");
		sb.append(" QUERY.ID_EMISION AS idEmision, QUERY.ID_CUENTA_NOM_BEN AS idCuentaNombradaBeneficiario, QUERY.ID_CUENTA_NOM_CTA AS idCuentaNombradaCuenta, QUERY.CUENTA AS cuenta, QUERY.SALDO AS saldo, QUERY.ID_DIVISA AS idDivisa,");
		sb.append(" QUERY.ID_BENEFICIARIO AS idBeneficiario, QUERY.ADP AS adp, QUERY.UOI AS uoi, QUERY.NOMBRE_BENEF AS nombreBenef, QUERY.APELLIDO_PATERNO_BENEF AS apellidoPaternoBenef, QUERY.APELLIDO_MATERNO_BENEF AS apellidoMaternoBenef,");
		sb.append(" QUERY.RAZON_SOCIAL_BENEF AS razonSocialBenef, QUERY.PAIS_INCORPORACION AS paisIncorporacion, QUERY.ID_TIPO_BENEFICIARIO AS idTipoBeneficiario, QUERY.DOMICILIO AS domicilio, QUERY.TIPO_FORMATO AS tipoFormato,");
		sb.append(" QUERY.PROPORCION AS proporcion, QUERY.FEE AS fee, QUERY.PORCENTAJE_MAV AS porcentajeMav, QUERY.PORCENTAJE_BEN AS porcentajeBen, QUERY.PORCENTAJE_HB_DEFAULT AS porcentajeHBDefault,");
		sb.append(" QUERY.PORCENTAJE_RETENCION_CTB AS porcentajeRetencionCTB, QUERY.ASIGNACION AS asignacion, QUERY.ID_TIPO_DERECHO AS idTipoDerecho, QUERY.ID_ESTATUS_DERECHO_MAV AS idEstatusDerechoMav,");
		sb.append(" QUERY.ID_ESTATUS_DERECHO AS idEstatusDerecho, QUERY.CLAVE_ALFABETICA AS claveAlfabetica, QUERY.DESC_ESTATUS_DERECHO AS descEstatusDerecho, QUERY.FECHA_FORMATO AS fechaFormato, QUERY.ESTATUS_BENEFICIARIO AS estatusBeneficiario,");
		sb.append(" QUERY.DESC_TIPO_DERECHO AS descTipoDerecho, ROWNUM AS id FROM (");
		sb.append("	SELECT VCS.* FROM V_CONSULTA_INT_CAP_H_SIN_BENEF VCS");
		sb.append("	UNION");
		sb.append("	SELECT VC.* FROM V_CONSULTA_INT_CAP_H VC");
		sb.append(") QUERY");
		sb.append(" WHERE QUERY.ID_DERECHO_CAPITAL = :idDerechoCapital");
		
		return sb.toString();
    }

	@Override
	public Integer deleteDerechoHistoricoTabla(Long idDerechoCapital) {
		List<DerechoCapitalHistorico> derechos = getDerechoHistoricoTabla(idDerechoCapital);
		Integer borrados = derechos.size();
    	for (DerechoCapitalHistorico der : derechos){
    		delete(der);
    	}
    	
    	return borrados;
	}
	
	public List<DerechoCapitalHistorico> getDerechoHistoricoTabla(final Long idDerechoCapital){
		List<DerechoCapitalHistorico> derechos = null;
		
		HibernateCallback hibernateCallback = new HibernateCallback() {
    		public Object doInHibernate(Session session) throws HibernateException, SQLException {
    			Criteria criteria = session.createCriteria(DerechoCapitalHistorico.class);
    			criteria.add(Restrictions.eq("idDerechoCapital",idDerechoCapital));
    			return (criteria.list());
    		}
    	};    	
    	derechos = (List<DerechoCapitalHistorico>)this.getHibernateTemplate().executeFind(hibernateCallback);		
		
    	return derechos;
	}

	@Override
	public void saveDerechoCapitalHistorico(DerechoCapitalHistorico derechoCapitalHistotico) {
		LOG.debug("Entrando a saveDerechoCapitalHistorico");
		try{
			this.getHibernateTemplate().save(derechoCapitalHistotico);
			this.getHibernateTemplate().flush();
		} catch(Exception ex){
			LOG.error("Error en saveDerechoCapitalHistorico", ex);
		}
	}
	
}
