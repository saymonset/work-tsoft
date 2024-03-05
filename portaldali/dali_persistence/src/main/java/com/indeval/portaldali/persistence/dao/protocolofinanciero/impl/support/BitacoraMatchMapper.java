/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.dao.protocolofinanciero.impl.support;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import com.indeval.portaldali.persistence.vo.BitacoraMatchVOPersistence;


/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class BitacoraMatchMapper implements RowMapper {

    private static final String CERO_STRING = "0";

	/** Objeto de loggeo para BitacoraMatchMapper */
    private static final Logger logger = LoggerFactory.getLogger(BitacoraMatchMapper.class);

    private static final String CONFIRMACION = "EN_PROCESO";

    private static final String ESTADO_INSTRUCCION_CANCELA_PORTAL = "CANCPORTAL";

    private static final String ESTADO_INSTRUCCION_CANCELADO = "CANCELADO";
    

    /**
     * 
     * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet,
     *      int)
     */
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

        logger.info("Entrando a BitacoraMatchMapper.mapRow()");

        BitacoraMatchVOPersistence bitacoraMatchVOPersistence = new BitacoraMatchVOPersistence();

        if (rs != null) {

            bitacoraMatchVOPersistence.setIdBitacoraMatch(new BigInteger(rs
                    .getString("id_bitacora_match")));
            bitacoraMatchVOPersistence
                    .setConfirmacion(CONFIRMACION.equalsIgnoreCase(rs
                            .getString("confirmacion")) ? Boolean.TRUE
                            : Boolean.FALSE);
            bitacoraMatchVOPersistence
                    .setCancelacion(ESTADO_INSTRUCCION_CANCELA_PORTAL
                            .equalsIgnoreCase(rs
                                    .getString("estado_instruccion"))
                            || ESTADO_INSTRUCCION_CANCELADO.equalsIgnoreCase(rs
                                    .getString("estado_instruccion")) ? Boolean.TRUE
                            : Boolean.FALSE);
            
            bitacoraMatchVOPersistence.setEnviada(rs.getString("confirmacion") != null ? 
            		rs.getString("confirmacion") : CERO_STRING );

            Clob clob = rs.getClob("mensaje");
            StringBuffer buffer = null;

            if (clob != null) {

                char[] mensaje = new char[(int) clob.length()];
                buffer = new StringBuffer("");

                try {

                    clob.getCharacterStream().read(mensaje);
                    buffer.append(mensaje);

                } catch (IOException e) {

                    e.printStackTrace();

                }

            }

            bitacoraMatchVOPersistence.setMensaje(buffer.toString());

        }

        return bitacoraMatchVOPersistence;

    }
}
