/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.dao.protocolofinanciero.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.Assert;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portaldali.persistence.dao.protocolofinanciero.BitacoraMatchDao;
import com.indeval.portaldali.persistence.dao.protocolofinanciero.impl.support.BitacoraMatchMapper;
import com.indeval.portaldali.persistence.vo.BitacoraMatchParamsPersistence;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class BitacoraMatchDaoImpl extends BaseDaoHibernateImpl implements BitacoraMatchDao {

    /** Log de clase. */
    private static final Logger logger = LoggerFactory.getLogger(BitacoraMatchDaoImpl.class);
    
    private String schema;
    
    /** Acceso a JdbcTemplate */
    private JdbcTemplate jdbcTemplateOracle;
    
    /** Define Estado Instruccion SIN_MATCH */
    private static final String ESTADO_INSTRUCCION_SIN_MATCH = "SIN_MATCH";
    
    /** Define Codigo Operacion VALID_PROCESS*/
    private static final String CODIGO_OPERACION_VALID_PROCESS = "VALIDPROCESS";
    
    /* NOTAS DE IMPLEMENTACION
     * 
     *   SELECT 
     *   B.ID_BITACORA, B.CODIGO_OPERACION, B.ID_TRASPASANTE, 
     *      B.ID_RECEPTOR, B.FOLIO_TRASPASANTE, B.FOLIO_RECEPTOR, 
     *      B.FOLIO_INSTRUCCION_TRASPASANTE, B.FOLIO_INSTRUCCION_RECEPTOR, B.FECHA, 
     *      B.INSTANCIA,
     *      R.ID_BITACORA_MATCH, R.FOLIO_MATCH, 
     *      R.ESTADO_INSTRUCCION, R.TIPO_OPERACION, R.TIPO_MENSAJE, 
     *      R.FECHA_HORA_RECEPCION, R.FECHA_LIQUIDACION, R.MATCH_KEY, 
     *      R.MENSAJE, R.EXPIRA, S.DESCRIPCION, R.MENSAJE
     *   FROM T_BITACORA_MATCH B 
     *   LEFT JOIN T_REGISTRO_INSTRUCCIONES_MATCH R 
     *     ON ((B.FOLIO_INSTRUCCION_TRASPASANTE = R.FOLIO_INSTRUCCION)
     *      OR (B.FOLIO_INSTRUCCION_TRASPASANTE = R.FOLIO_INSTRUCCION))
     *   LEFT JOIN STATUS_MENSAJE_RESPUESTA S
     *     ON (B.CODIGO_OPERACION = S.IDENTIFICADOR)
     *   WHERE R.ESTADO_INSTRUCCION = 'SIN_MATCH' AND B.CODIGO_OPERACION <> 'VALIDPROCESS'
     */
    /**
     * @see com.indeval.persistence.portallegado.protocolofinanciero.dao.BitacoraMatchDao#getRegistrosBitacoraMatch(com.indeval.persistence.portallegado.protocolofinanciero.vo.BitacoraMatchParamsPersistence)
     */
    public List getRegistrosBitacoraMatch(BitacoraMatchParamsPersistence params) {
        
        logger.info("Entrando a BitacoraMatchDaoImpl.getRegistrosBitacoraMatch()");
        
        /* Se validan los parametros de entrada */
        Assert.notNull(params, " El objeto de parametros esta Null ");
        
        /* Se construye la lista de parametros */
        List args = new ArrayList();
        
        /* Se construye el StrinBuffer para armar la cadena sql */
        StringBuffer sqlStringBuffer = new StringBuffer("SELECT ");
//        sqlStringBuffer.append(" B.ID_BITACORA AS id_bitacora, ");
//        sqlStringBuffer.append(" B.CODIGO_OPERACION AS codigo_operacion, ");
//        sqlStringBuffer.append(" B.ID_TRASPASANTE AS id_traspasante, ");
//        sqlStringBuffer.append(" B.ID_RECEPTOR AS id_receptor, ");
//        sqlStringBuffer.append(" B.FOLIO_TRASPASANTE AS folio_traspasante, ");
//        sqlStringBuffer.append(" B.FOLIO_RECEPTOR AS folio_receptor, ");
//        sqlStringBuffer.append(" B.FOLIO_INSTRUCCION_TRASPASANTE AS folio_instruccion_traspasante, ");
//        sqlStringBuffer.append(" B.FOLIO_INSTRUCCION_RECEPTOR AS folio_instruccion_receptor, ");
//        sqlStringBuffer.append(" B.FECHA AS fecha, ");
//        sqlStringBuffer.append(" B.INSTANCIA AS instancia, ");
//        sqlStringBuffer.append(" R.ID_BITACORA_MATCH AS id_bitacora_match, ");
//        sqlStringBuffer.append(" R.FOLIO_MATCH AS folio_match, ");
//        sqlStringBuffer.append(" R.ESTADO_INSTRUCCION AS estado_instruccion, ");
//        sqlStringBuffer.append(" R.TIPO_OPERACION AS tipo_operacion, ");
//        sqlStringBuffer.append(" R.TIPO_MENSAJE AS tipo_mensaje, ");
//        sqlStringBuffer.append(" R.FECHA_HORA_RECEPCION AS fecha_hora_recepcion, ");
//        sqlStringBuffer.append(" R.FECHA_LIQUIDACION AS fecha_liquidacion, ");
//        sqlStringBuffer.append(" R.MATCH_KEY AS match_key, ");
        sqlStringBuffer.append(" S.DESCRIPCION AS descripcion_error, ");
        sqlStringBuffer.append(" R.MENSAJE AS mensaje ");
//        sqlStringBuffer.append(" ,R.EXPIRA AS expira ");
         
        sqlStringBuffer.append(" FROM ");
        sqlStringBuffer.append(schema);
        sqlStringBuffer.append("T_BITACORA_MATCH B ");
        
        /* LEFT JOIN a la tabla T_REGISTRO_INSTRUCCIONES_MATCH */
        sqlStringBuffer.append(" LEFT JOIN ");
        sqlStringBuffer.append(schema);
        sqlStringBuffer.append("T_REGISTRO_INSTRUCCIONES_MATCH R ");
        sqlStringBuffer.append(" ON ((B.FOLIO_INSTRUCCION_TRASPASANTE = R.FOLIO_INSTRUCCION) ");
        sqlStringBuffer.append(" OR (B.FOLIO_INSTRUCCION_RECEPTOR = R.FOLIO_INSTRUCCION)) ");
        
        /* LEFT JOIN a la tabla STATUS_MENSAJE_RESPUESTA */
        sqlStringBuffer.append(" LEFT JOIN ");
        sqlStringBuffer.append(schema);
        sqlStringBuffer.append("STATUS_MENSAJE_RESPUESTA S ");
        sqlStringBuffer.append(" ON  S.IDENTIFICADOR = B.CODIGO_OPERACION ");
        
        sqlStringBuffer.append(" WHERE ");
        
        sqlStringBuffer.append(" ( ");
        
        switch (verificaRol(params)) {

            case 1:
                    //sqlStringBuffer.append(" AND ");
                    sqlStringBuffer.append(" ( B.ID_TRASPASANTE = ? AND B.FOLIO_TRASPASANTE = ? ) ");
                    sqlStringBuffer.append(" OR ");
                    sqlStringBuffer.append(" ( B.ID_RECEPTOR = ? AND B.FOLIO_RECEPTOR = ? ) ");
                    args.add(params.getIdTraspasante());
                    args.add(params.getFolioTraspasante());
                    args.add(params.getIdTraspasante());
                    args.add(params.getFolioTraspasante());
                break;
            case 2:
                    //sqlStringBuffer.append(" AND ");
                    sqlStringBuffer.append(" ( B.ID_TRASPASANTE = ? AND B.FOLIO_TRASPASANTE = ? ) ");
                    sqlStringBuffer.append(" OR ");
                    sqlStringBuffer.append(" ( B.ID_RECEPTOR = ? AND B.FOLIO_RECEPTOR = ? ) ");
                    args.add(params.getIdReceptor());
                    args.add(params.getFolioReceptor());
                    args.add(params.getIdReceptor());
                    args.add(params.getFolioReceptor());
                break;
            case 3:
                    //sqlStringBuffer.append(" AND ");
                    sqlStringBuffer.append(" (( B.ID_TRASPASANTE = ? AND B.FOLIO_TRASPASANTE = ? ) AND ( B.ID_RECEPTOR = ? AND B.FOLIO_RECEPTOR = ? )) ");
                    sqlStringBuffer.append(" OR ");
                    sqlStringBuffer.append(" (( B.ID_RECEPTOR = ? AND B.FOLIO_RECEPTOR = ? ) AND ( B.ID_TRASPASANTE = ? AND B.FOLIO_TRASPASANTE = ? )) ");
                    args.add(params.getIdTraspasante());
                    args.add(params.getFolioTraspasante());
                    args.add(params.getIdReceptor());
                    args.add(params.getFolioReceptor());
                    args.add(params.getIdTraspasante());
                    args.add(params.getFolioTraspasante());
                    args.add(params.getIdReceptor());
                    args.add(params.getFolioReceptor());
                break;                
            case 0:
            default:
                if(StringUtils.isNotBlank(params.getIdTraspasante())){
                    //sqlStringBuffer.append(" AND ");
                    sqlStringBuffer.append(" (B.ID_TRASPASANTE = ? AND B.FOLIO_TRASPASANTE = ? ) ");
                    args.add(params.getIdTraspasante());
                    args.add(params.getFolioTraspasante());
                }
                if(StringUtils.isNotBlank(params.getIdReceptor())){
                    if(StringUtils.isNotBlank(params.getIdTraspasante())){
                        sqlStringBuffer.append(" AND ");    
                    }
                    sqlStringBuffer.append(" (B.ID_RECEPTOR = ? AND B.FOLIO_RECEPTOR = ? ) ");
                    args.add(params.getIdReceptor());
                    args.add(params.getFolioReceptor());
                }
                break;
        }
        
        sqlStringBuffer.append(" ) ");
        if(StringUtils.isNotBlank(params.getTipoOperacion())){
            sqlStringBuffer.append(" AND R.TIPO_OPERACION = ? ");
            args.add(params.getTipoOperacion().trim());
        }
        if(params.getFechaLiquidacion() != null) {
            sqlStringBuffer.append(" AND R.FECHA_LIQUIDACION = ? ");
            args.add(params.getFechaLiquidacion());
        }
        sqlStringBuffer.append(" AND ( R.ESTADO_INSTRUCCION = ? AND B.CODIGO_OPERACION <> ? ) ");
        args.add(ESTADO_INSTRUCCION_SIN_MATCH);
        args.add(CODIGO_OPERACION_VALID_PROCESS);
        
        System.out.println(sqlStringBuffer.toString());
        
        return (List) jdbcTemplateOracle.query(sqlStringBuffer.toString(), args.toArray(),
                new BitacoraMatchMapper());

    }
    
    /**
     * Retorna...
     *      0 si rol es null
     *      1 si el rol es AMBOS y se recibe el traspasante
     *      2 si el rol es AMBOS y se recibe el receptor
     *      3 si el rol es AMBOS y se reciben tanto el traspasante como el receptor
     * @param params
     * @return int 
     */
    private int verificaRol(BitacoraMatchParamsPersistence params){
        
        logger.info("Entrando a BitacoraMatchDaoImpl.verificaRol()");
        
        int rol = 0;
        if(params.getRol()!=null) {
            if(StringUtils.isNotBlank(params.getIdTraspasante())){
                rol++;
            }
            if(StringUtils.isNotBlank(params.getIdReceptor())){
                rol++;
                rol++;
            }            
        }

        return rol;
    }

    /**
     * @param jdbcTemplate the jdbcTemplate to set
     */
    public void setJdbcTemplateOracle(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplateOracle = jdbcTemplate;
    }

    /**
     * @param schema the schema to set
     */
    public void setSchema(String schema) {
        this.schema = schema;
    }

}
