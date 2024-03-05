/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.dao.protocolofinanciero.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.Assert;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portaldali.persistence.dao.protocolofinanciero.ProtocoloFinancieroDao;
import com.indeval.portaldali.persistence.dao.protocolofinanciero.impl.support.BitacoraMatchMapper;
import com.indeval.portaldali.persistence.vo.AgentePK;
import com.indeval.portaldali.persistence.vo.BitacoraMatchParamsPersistence;

/**
 * 
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class ProtocoloFinancieroDaoImpl extends BaseDaoHibernateImpl implements
        ProtocoloFinancieroDao {

    /** Log de clase. */
    private static final Logger logger = LoggerFactory
            .getLogger(ProtocoloFinancieroDaoImpl.class);

    /** Define Estado Instruccion SIN_MATCH */
    private static final String ESTADO_INSTRUCCION_SIN_MATCH = "SIN_MATCH";

    private static final String ESTADO_INSTRUCCION_CANCELA_PORTAL = "CANCPORTAL";

    private static final String ESTADO_INSTRUCCION_CANCELADO = "CANCELADO";

    private static final String EXPIRA = "S";

    private static final String MERCADO_CAPITALES = "MC";

    private static final String CONFIRMACION = "EN_PROCESO";

    private static final String MENSAJE_543 = "543";

    private static final String MENSAJE_541 = "541";

    private static final String TRASPASANTE = "T";

    private static final String RECEPTOR = "R";

    private static final String AMBOS = "A";

    private String schema;

    /** Acceso a JdbcTemplate */
    private JdbcTemplate jdbcTemplateOracle;

    /**
     * 
     * @see com.indeval.persistence.portallegado.protocolofinanciero.dao.ProtocoloFinancieroDao#getBitacoraMatch(com.indeval.persistence.portallegado.protocolofinanciero.vo.BitacoraMatchParamsPersistence)
     */
    public List getBitacoraMatch(BitacoraMatchParamsPersistence params) {

        logger.info("Entrando a ProtocoloFinancieroDaoImpl.getBitacoraMatch()");

        /* Se validan los parametros de entrada */
        Assert.notNull(params, " El objeto de parametros esta Null ");

        /* Se verifica que vengan los parametros obligatorios */
        Assert.isTrue(params.validaRequeridos(),
                "No se expecifican los parametros obligatorios");

        /* Se valida combinaciones de busqueda invalidas */
        Assert
                .isTrue(
                        !combinacionesBusquedaInvalidas(params),
                        "No se han encontrado registros coincidentes para los criterios de b\u00fasqueda seleccionados.");

        /* Se construye la lista de parametros */
        List args = new ArrayList();

        boolean quitaPrimeraParte = false;
        boolean quitaSegundaParte = false;

        boolean agregaAND = false;

        /* Se construye el StrinBuffer para armar la cadena sql */
        StringBuffer sqlStringBuffer = new StringBuffer("SELECT ");
        sqlStringBuffer
                .append("ID_BITACORA_MATCH, MENSAJE, CONFIRMACION, ESTADO_INSTRUCCION ");
        sqlStringBuffer.append("FROM ");
        sqlStringBuffer.append(getSchema());
        sqlStringBuffer.append("T_REGISTRO_INSTRUCCIONES_MATCH RIM ");

        if (StringUtils.isNotBlank(params.getMercado())) {

            /* LEFT JOIN a las tablas C_TIPOS_VALOR TV, C_MERCADOS M */
            sqlStringBuffer.append(" LEFT JOIN ");
            sqlStringBuffer.append(getSchema());
            sqlStringBuffer.append("C_TIPOS_VALOR TV");
            sqlStringBuffer.append(" ON  RIM.TIPO_VALOR = TV.CLAVE ");
            sqlStringBuffer.append(" LEFT JOIN ");
            sqlStringBuffer.append(getSchema());
            sqlStringBuffer.append("C_MERCADOS M");
            sqlStringBuffer.append(" ON  TV.ID_MERCADO = M.ID_MERCADO ");

        }

        sqlStringBuffer.append(" WHERE ");

        if (params.getRemitidos() != null
                && params.getRemitidos().booleanValue()
                && StringUtils.isNotBlank(params.getTipoMensaje())
                && params.getTipoMensaje().equalsIgnoreCase(MENSAJE_541)) {

            quitaPrimeraParte = true;

        }

        if (params.getRemitidos() != null
                && params.getRemitidos().booleanValue()
                && StringUtils.isNotBlank(params.getTipoMensaje())
                && params.getTipoMensaje().equalsIgnoreCase(MENSAJE_543)) {

            quitaSegundaParte = true;

        }

        sqlStringBuffer.append(" ( ");
        if (!quitaPrimeraParte) {

            if (StringUtils.isNotBlank(params.getRol())
                    && (params.getRol().equalsIgnoreCase(TRASPASANTE) || params
                            .getRol().equalsIgnoreCase(AMBOS))) {

                sqlStringBuffer.append(" ( ");

                if (StringUtils.isNotBlank(params.getIdTraspasante())
                        && StringUtils.isNotBlank(params.getFolioTraspasante())) {

                    sqlStringBuffer.append(" ID_FOLIO_TRASPASANTE = ? ");
                    args.add(params.getIdTraspasante().trim().concat(
                            params.getFolioTraspasante().trim()));

                    agregaAND = true;

                }

                if (StringUtils.isNotBlank(params.getCuentaTraspasante())) {

                    if (agregaAND) {
                        sqlStringBuffer.append(" AND ");
                    }

                    sqlStringBuffer.append(" CUENTA_TRASPASANTE = ? ");
                    args.add(params.getCuentaTraspasante().trim());

                    agregaAND = true;

                }

                if (StringUtils.isNotBlank(params.getIdReceptor())
                        && StringUtils.isNotBlank(params.getFolioReceptor())) {

                    if (agregaAND) {
                        sqlStringBuffer.append(" AND ");
                    }

                    sqlStringBuffer.append(" ID_FOLIO_RECEPTOR = ? ");
                    args.add(params.getIdReceptor().trim().concat(
                            params.getFolioReceptor().trim()));
                    agregaAND = true;
                }

                if (StringUtils.isNotBlank(params.getCuentaReceptor())) {

                    if (agregaAND) {
                        sqlStringBuffer.append(" AND ");
                    }

                    sqlStringBuffer.append(" CUENTA_RECEPTOR = ? ");
                    args.add(params.getCuentaReceptor().trim());

                }

                if ((params.getRemitidos() != null)
                        && params.getRemitidos().booleanValue()) {

                    sqlStringBuffer.append(" AND TIPO_MENSAJE = ? ");
                    args.add(MENSAJE_543);

                }

                sqlStringBuffer.append(" ) ");

            }
        }

        agregaAND = false;

        if (!quitaPrimeraParte
                && !quitaSegundaParte
                && (StringUtils.isNotBlank(params.getRol()) && params.getRol()
                        .equalsIgnoreCase(AMBOS))) {

            sqlStringBuffer.append(" OR ");

        }

        if (!quitaSegundaParte) {

            if (StringUtils.isNotBlank(params.getRol())
                    && (params.getRol().equalsIgnoreCase(RECEPTOR) || params
                            .getRol().equalsIgnoreCase(AMBOS))) {

                sqlStringBuffer.append(" ( ");

                if (StringUtils.isNotBlank(params.getIdTraspasante())
                        && StringUtils.isNotBlank(params.getFolioTraspasante())) {

                    sqlStringBuffer.append(" ID_FOLIO_RECEPTOR = ? ");
                    args.add(params.getIdTraspasante().trim().concat(
                            params.getFolioTraspasante().trim()));

                    agregaAND = true;
                }

                if (StringUtils.isNotBlank(params.getCuentaTraspasante())) {

                    if (agregaAND) {
                        sqlStringBuffer.append(" AND ");
                    }

                    sqlStringBuffer.append(" CUENTA_RECEPTOR = ? ");
                    args.add(params.getCuentaTraspasante().trim());
                    agregaAND = true;

                }

                if (StringUtils.isNotBlank(params.getIdReceptor())
                        && StringUtils.isNotBlank(params.getFolioReceptor())) {

                    if (agregaAND) {
                        sqlStringBuffer.append(" AND ");
                    }

                    sqlStringBuffer.append(" ID_FOLIO_TRASPASANTE = ? ");
                    args.add(params.getIdReceptor().trim().concat(
                            params.getFolioReceptor().trim()));
                    agregaAND = true;

                }

                if (StringUtils.isNotBlank(params.getCuentaReceptor())) {

                    if (agregaAND) {
                        sqlStringBuffer.append(" AND ");
                    }

                    sqlStringBuffer.append(" CUENTA_TRASPASANTE = ? ");
                    args.add(params.getCuentaReceptor().trim());

                }

                if ((params.getRemitidos() != null)
                        && params.getRemitidos().booleanValue()) {

                    sqlStringBuffer.append(" AND TIPO_MENSAJE = ? ");
                    args.add(MENSAJE_541);

                }

                sqlStringBuffer.append(" ) ");

            }
        }

        sqlStringBuffer.append(")");

        if ((params.getEmision() != null)
                && (params.getEmision() != null)) {

            if (StringUtils.isNotBlank(params.getEmision().getTv())) {

                sqlStringBuffer.append(" AND ");
                sqlStringBuffer.append(" TIPO_VALOR = ? ");
                args.add(params.getEmision().getTv().trim());

            }

            if (StringUtils.isNotBlank(params.getEmision().getEmisora())) {

                sqlStringBuffer.append(" AND ");
                sqlStringBuffer.append(" EMISORA = ? ");
                args
                        .add(params.getEmision().getEmisora().trim());

            }

            if (StringUtils.isNotBlank(params.getEmision().getSerie())) {

                sqlStringBuffer.append(" AND ");
                sqlStringBuffer.append(" SERIE = ? ");
                args.add(params.getEmision().getSerie().trim());

            }

            if (StringUtils.isNotBlank(params.getEmision().getCupon())) {

                sqlStringBuffer.append(" AND ");
                sqlStringBuffer.append(" CUPON = ? ");
                args.add(params.getEmision().getCupon().trim());

            }

        }

        if (StringUtils.isNotBlank(params.getTipoOperacion())) {

            sqlStringBuffer.append(" AND TIPO_OPERACION = ? ");
            args.add(params.getTipoOperacion().trim());

        }

        if (params.getFechaLiquidacion() != null) {

            sqlStringBuffer.append(" AND FECHA_LIQUIDACION = ? ");
            args.add(params.getFechaLiquidacion());

        }

        if (params.getCantidadTitulos() != null) {

            sqlStringBuffer.append(" AND CANTIDAD_TITULOS = ? ");
            args.add(params.getCantidadTitulos().toString());

        }

        if ((StringUtils.isNotBlank(params.getTipoMensaje()))
                && ((params.getRemitidos() == null) || !params.getRemitidos()
                        .booleanValue())) {

            sqlStringBuffer.append(" AND TIPO_MENSAJE = ? ");
            args.add(params.getTipoMensaje().trim());

        }

        if (StringUtils.isNotBlank(params.getFolioUsuario())) {

            sqlStringBuffer.append(" AND FOLIO_INSTRUCCION = ? ");
            args.add(params.getFolioUsuario().trim());

        }

        sqlStringBuffer
                .append(" AND ( ESTADO_INSTRUCCION = ? OR ESTADO_INSTRUCCION = ? OR ESTADO_INSTRUCCION = ? )");
        args.add(ESTADO_INSTRUCCION_SIN_MATCH);
        args.add(ESTADO_INSTRUCCION_CANCELA_PORTAL);
        args.add(ESTADO_INSTRUCCION_CANCELADO);

        if (StringUtils.isNotBlank(params.getMercado())) {

            if (MERCADO_CAPITALES.equalsIgnoreCase(params.getMercado())) {

                sqlStringBuffer.append(" AND LTRIM(RTRIM(CLAVE_MERCADO)) = ? ");

            } else {

                sqlStringBuffer
                        .append(" AND LTRIM(RTRIM(CLAVE_MERCADO)) != ? ");

            }

            args.add(MERCADO_CAPITALES);

        }

        logger.debug("QUERY : [ " + sqlStringBuffer.toString() + "]");

        return (List) jdbcTemplateOracle.query(sqlStringBuffer.toString(), args
                .toArray(), new BitacoraMatchMapper());

    }

    /**
     * Valida las combinaciones de busqueda invalidas
     * 
     * @param params
     * @return boolean
     */
    private boolean combinacionesBusquedaInvalidas(
            BitacoraMatchParamsPersistence params) {
        if (params.getRemitidos() != null
                && params.getRemitidos().booleanValue()
                && StringUtils.isNotBlank(params.getTipoMensaje())
                && params.getTipoMensaje().equalsIgnoreCase(MENSAJE_541)
                && StringUtils.isNotBlank(params.getRol())
                && params.getRol().equalsIgnoreCase(TRASPASANTE)) {

            return true;
        }
        if (params.getRemitidos() != null
                && params.getRemitidos().booleanValue()
                && StringUtils.isNotBlank(params.getTipoMensaje())
                && params.getTipoMensaje().equalsIgnoreCase(MENSAJE_543)
                && StringUtils.isNotBlank(params.getRol())
                && params.getRol().equalsIgnoreCase(RECEPTOR)) {

            return true;
        }
        return false;
    }

    /**
     * @see com.indeval.persistence.portallegado.protocolofinanciero.dao.ProtocoloFinancieroDao#actualizaEdoInsExpira(java.math.BigInteger,
     *      boolean, com.indeval.persistence.portallegado.catalogo.modelo.AgentePK)
     */
    public int actualizaEdoInsExpira(BigInteger idBitacoraMatch,
            boolean operacion, AgentePK agenteFirmado) {

        logger.info("Entrando a actualizaEdoInsExpira()");

        Assert.notNull(idBitacoraMatch);
        Assert.notNull(agenteFirmado, "El agente firmado no puede ser nulo");
        Assert.notNull(agenteFirmado.getIdInst(),
                "El id de la instituci\u00f3n no puede ser nulo");
        Assert.notNull(agenteFirmado.getFolioInst(),
                "El folio de la instituci\u00f3n no puede ser nulo");

        if(operacion) {
            logger.debug("Se Expira la Operacion [" + idBitacoraMatch+ "]");    
        }
        else {
            logger.debug("Se Cancela la Operacion [" + idBitacoraMatch + "]");
        }

        String idFolioAgente = agenteFirmado.getIdInst()
                + agenteFirmado.getFolioInst();

        StringBuffer sqlStringBuffer = new StringBuffer(" UPDATE ");
        sqlStringBuffer.append("T_REGISTRO_INSTRUCCIONES_MATCH ");
        sqlStringBuffer.append(" SET ");

        if (!operacion) {

            sqlStringBuffer.append(" ESTADO_INSTRUCCION = ");
            sqlStringBuffer.append("'" + ESTADO_INSTRUCCION_CANCELA_PORTAL
                    + "'");
            sqlStringBuffer.append(" WHERE ");
            sqlStringBuffer.append(" ((ID_FOLIO_RECEPTOR = '" + idFolioAgente
                    + "' ");
            sqlStringBuffer.append(" AND ");
            sqlStringBuffer.append(" TIPO_MENSAJE = ");
            sqlStringBuffer.append("'" + MENSAJE_541 + "')");
            sqlStringBuffer.append(" OR ");
            sqlStringBuffer.append(" (ID_FOLIO_TRASPASANTE = '" + idFolioAgente
                    + "' ");
            sqlStringBuffer.append(" AND ");
            sqlStringBuffer.append(" TIPO_MENSAJE = ");
            sqlStringBuffer.append("'" + MENSAJE_543 + "'))");

        } else {

            sqlStringBuffer.append(" EXPIRA = ");
            sqlStringBuffer.append("'" + EXPIRA + "'");
            sqlStringBuffer.append(" WHERE ");
            sqlStringBuffer.append(" TIPO_MENSAJE = ");
            sqlStringBuffer.append("'" + MENSAJE_543 + "' ");
            sqlStringBuffer.append(" AND ");
            sqlStringBuffer.append(" (ID_FOLIO_TRASPASANTE = '" + idFolioAgente
                    + "' ");
            sqlStringBuffer.append(" OR ");
            sqlStringBuffer.append(" ID_FOLIO_RECEPTOR = '" + idFolioAgente
                    + "') ");

        }

        sqlStringBuffer.append(" AND ");
        sqlStringBuffer.append(" ID_BITACORA_MATCH = ");
        sqlStringBuffer.append("'" + idBitacoraMatch.toString() + "'");
        sqlStringBuffer.append(" AND ");
        sqlStringBuffer.append(" ESTADO_INSTRUCCION != ");
        sqlStringBuffer.append("'" + ESTADO_INSTRUCCION_CANCELA_PORTAL + "'");
        sqlStringBuffer.append(" AND ");
        sqlStringBuffer.append(" (EXPIRA != ");
        sqlStringBuffer.append("'" + EXPIRA + "'");
        sqlStringBuffer.append(" OR ");
        sqlStringBuffer.append(" EXPIRA is null) ");
        sqlStringBuffer.append(" AND ");
        sqlStringBuffer.append(" (CONFIRMACION != ");
        sqlStringBuffer.append("'" + CONFIRMACION + "'");
        sqlStringBuffer.append(" OR ");
        sqlStringBuffer.append(" CONFIRMACION is null) ");

        logger.debug("QUERY : [" + sqlStringBuffer.toString() + "]");
        return jdbcTemplateOracle.update(sqlStringBuffer.toString());

    }

    /**
     * @param jdbcTemplate
     *            the jdbcTemplate to set
     */
    public void setJdbcTemplateOracle(JdbcTemplate jdbcTemplate) {

        this.jdbcTemplateOracle = jdbcTemplate;

    }

    /**
     * @param schema
     *            the schema to set
     */
    public void setSchema(String schema) {

        this.schema = schema;

    }

	/**
	 * @return the schema
	 */
	public String getSchema() {
		return schema;
	}

}
