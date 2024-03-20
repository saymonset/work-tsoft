package com.indeval.portalinternacional.persistence.dao.impl.cuentasTransitoriasEfectivo;


/**
 * Queries para el manejo de informaci&oacute;n de pantalla Cuentas de Tesorer&iacute;a de Efectivo
 *
 * @author Jacito
 */
public class ConsultasCuentasTransitoriasEfectivo {

    // <editor-fold defaultstate="collapsed" desc="Valores a sustituir para las queries">

    private static final String FILTROS = "\\[FILTROS\\]";
    private static final String ID_CUSTODIO = "\\[ID_CUSTODIO\\]";
    private static final String ID_DIVISA = "\\[ID_DIVISA\\]";
    private static final String FOLIO_RELACIONADO = "\\[FOLIO_RELACIONADO\\]";
    private static final String ID_REGISTRO = "\\[ID_REGISTRO\\]";
    private static final String FECHA_INICIO = "\\[FECHA_INICIO\\]";
    private static final String FECHA_FIN = "\\[FECHA_FIN\\]";
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Filtros y Complementos para armado de queries">
    /**
     * Complemento de filtro: espacio
     */
    private static final String FILTRO_ESPACIO = "                      ";

    /**
     * Complemento de filtro: Divisa
     */
    private static final String FILTRO_DIVISA = " AND ID_DIVISA = [ID_DIVISA]\n";

    /**
     * Complemento de filtro: Custodio
     */
    private static final String FILTRO_CUSTODIO = " AND ID_CUSTODIO = [ID_CUSTODIO]\n";

    /**
     * Complemento de filtro: FolioRelacionado
     */
    private static final String FILTRO_FOLIO_RELACIONADO = " FOLIO_RELACIONADO = '[FOLIO_RELACIONADO]'\n";

    /**
     * Complemento de filtro: FolioRelacionado
     */
    private static final String FILTRO_FOLIO_RELACIONADO_EXISTE = " FOLIO_RELACIONADO <> 'NONREF' \n" + FILTROS;

    /**
     * Complemento de filtro: FolioRelacionado con LIKE
     */
    private static final String FILTRO_FOLIO_RELACIONADO_LIKE = " AND FOLIO_RELACIONADO LIKE '%[FOLIO_RELACIONADO]%'\n";
    /**
     * Complemento de filtro: Fecha
     */
    private static final String FILTRO_FECHA = " AND TO_DATE(FECHA_RECEPCION,'DD/MM/YY') = TO_DATE('[FECHA_INICIO]','DD/MM/YY')\n";

    /**
     * Complemento de filtro: Fechas
     */
    private static final String FILTRO_FECHAS = " AND FECHA_RECEPCION BETWEEN TO_DATE('[FECHA_INICIO]','DD/MM/YY') AND TO_DATE ('[FECHA_FIN]','DD/MM/YY')\n";

    /**
     * Complemento de filtro: ID_CUENTA_TRANSITORIA
     */
    private static final String FILTRO_ID_CUENTA_TRANSITORIA = "  ID_CUENTA_TRANSITORIA = '[ID_REGISTRO]'";

    /**
     * Complemento de query: AND
     */
    private static final String COMPLEMENTO_ADN = " AND ";
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Consultas de Divisas y Custodios disponibles: Menus">
    /**
     * Consulta de Divisas en T_CUENTA_TRANSITORIA
     */
    static final String QUERY_DIVISAS_CUENTA_TRANSITORIA = "\n" +
            "SELECT DISTINCT(TCT.ID_DIVISA) ID, CD.CLAVE_ALFABETICA||' - '||CD.DESCRIPCION VALUE \n" +
            "FROM T_CUENTA_TRANSITORIA TCT, C_DIVISA CD \n" +
            "WHERE TCT.ID_DIVISA = CD.ID_DIVISA ";

    /**
     * Consulta de Custodios en T_CUENTA_TRANSITORIA
     */
    static final String QUERY_CUSTODIOS_CUENTA_TRANSITORIA = "\n" +
            "SELECT DISTINCT(TCT.ID_CUSTODIO) ID, CC.NOMBRE_CORTO||' - '||CC.CODIGO_BANCO VALUE\n" +
            "FROM T_CUENTA_TRANSITORIA TCT, C_CUSTODIO CC\n" +
            "WHERE TCT.ID_CUSTODIO = CC.ID_CUSTODIO";

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Query de MONTO de BOVEDA">
    /**
     * Consulta de MONTO de BOVEDA
     */
    static final String QUERY_TOTAL_BOVEDA = "\n" +
            "SELECT SALDOS.ID_BOVEDA, SALDOS.NOMBRE_BOVEDA,\n" +
            "    BOVEDA.ID_CUSTODIO, BOVEDA.NOMBRE_CUSTODIO,\n" +
            "    SALDOS.ID_DIVISA, SALDOS.NOMBRE_DIVISA,\n" +
            "    SALDOS.SALDO_DISPONIBLE, SALDOS.SALDO_NO_DISPONIBLE, \n" +
            "    SALDOS.SALDO_DISPONIBLE + SALDOS.SALDO_NO_DISPONIBLE AS SALDO\n" +
            "    FROM\n" +
            "            (SELECT ccbi.ID_CUSTODIO, rbve.ID_EFECTIVO,\n" +
            "             c.NOMBRE_CORTO NOMBRE_CUSTODIO\n" +
            "         FROM C_CUSTODIO_BOVEDA_INT ccbi\n" +
            "                     INNER JOIN R_BOVEDA_V_E rbve ON RBVE.ID_VALORES =CCBI.ID_BOVEDA\n" +
            "                     JOIN C_CUSTODIO c ON c.ID_CUSTODIO=ccbi.ID_CUSTODIO)  BOVEDA,\n" +
            "        (SELECT sn.ID_BOVEDA, sn.ID_CUENTA,\n" +
            "    sn.ID_DIVISA, d.DESCRIPCION NOMBRE_DIVISA,\n" +
            "    sn.SALDO_DISPONIBLE, sn.SALDO_NO_DISPONIBLE,\n" +
            "    b.DESCRIPCION NOMBRE_BOVEDA\n" +
            "    FROM T_SALDO_NOMBRADA sn\n" +
            "    INNER JOIN C_DIVISA d ON d.ID_DIVISA = sn.ID_DIVISA\n" +
            "    INNER JOIN C_BOVEDA b ON b.ID_CUENTA_BOVEDA = sn.ID_CUENTA  ) SALDOS\n" +
            "    WHERE SALDOS.ID_BOVEDA = BOVEDA.ID_EFECTIVO\n" +
            "    [FILTROS]";
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Queries de seccion NEGATIVOS">

    /**
     * Consulta de Referencias Negativas: DETALLES
     */
    static final String QUERY_NEGATIVOS_DETALLES = "\n" +
            "SELECT CR.ID_CUENTA_TRANSITORIA, CR.TIPO_MENSAJE, CR.DETALLE_MOVIMIENTOS,\n" +
            "        CR.ID_DIVISA, CR.DIVISA , CR.ID_CUSTODIO, CR.NOMBRE_CORTO, CR.MONTO,\n" +
            "        CASE\n" +
            "            WHEN CR.FOLIO_RELACIONADO = 'NONREF' THEN ' '\n" +
            "            ELSE CR.FOLIO_RELACIONADO\n" +
            "        END AS FOLIO_RELACIONADO,\n" +
            "        CAST(\n" +
            "            CASE \n" +
            "                WHEN CADENA_XML IS NULL THEN NULL\n" +
            "                WHEN INSTR(UPPER(CADENA_XML), '<SEME>') > 0 THEN \n" +
            "                SUBSTR(\n" +
            "                    CADENA_XML,\n" +
            "                        INSTR(UPPER(CADENA_XML), '<SEME>') + LENGTH('<SEME>'),\n" +
            "                        INSTR(UPPER(CADENA_XML), '</SEME>', INSTR(UPPER(CADENA_XML), '<SEME>')) \n" +
            "                            - INSTR(UPPER(CADENA_XML), '<SEME>') - LENGTH('<SEME>'))\n" +
            "                ELSE NULL\n" +
            "           END AS VARCHAR2(100)  ) AS SEME,\n" +
            "       CAST(\n" +
            "            CASE \n" +
            "                WHEN CADENA_XML IS NULL THEN NULL\n" +
            "                WHEN INSTR(UPPER(CADENA_XML), '<MENSAJEISO>') > 0 THEN \n" +
            "                    SUBSTR(\n" +
            "                        CADENA_XML,\n" +
            "                        INSTR(UPPER(CADENA_XML), '<MENSAJEISO>') + LENGTH('<MENSAJEISO>'),\n" +
            "                        INSTR(UPPER(CADENA_XML), '</MENSAJEISO>', INSTR(UPPER(CADENA_XML), '<MENSAJEISO>')) \n" +
            "                            - INSTR(UPPER(CADENA_XML), '<MENSAJEISO>') - LENGTH('<MENSAJEISO>')\n" +
            "                    )\n" +
            "                ELSE NULL\n" +
            "            END AS VARCHAR2(4000) ) AS ISO\n" +
            " FROM\n" +
            "         (SELECT CUS.ID_CUSTODIO, CUS.NOMBRE_CORTO, CD.ID_DIVISA,\n" +
            "                 CD.CLAVE_ALFABETICA||'-'||CD.DESCRIPCION AS DIVISA,\n" +
            "                 TRAN.FOLIO_RELACIONADO, TRAN.TIPO_MENSAJE, TRAN.MONTO,\n" +
            "                 TRAN.DETALLE_MOVIMIENTOS, TRAN.ID_CUENTA_TRANSITORIA,\n" +
            "                 XML AS CADENA_XML\n" +
            "         FROM  T_CUENTA_TRANSITORIA TRAN, C_DIVISA CD, C_CUSTODIO CUS\n" +
            "         WHERE TRAN.ID_DIVISA = CD.ID_DIVISA\n" +
            "               AND TRAN.ID_CUSTODIO = CUS.ID_CUSTODIO\n" +
            "               AND TRAN.MONTO < 0\n" +
            "               AND TRAN.ESTADO_OPERACION IN (1, 5)\n" +
            "               AND TRAN.ESTADO_OPERACION_INTERNO IN (1, 2))CR \n" +
            "               [FILTROS]";

    /**
     * Consulta de Referencias Negativas Detalles: TOTAL
     */
    static final String QUERY_NEGATIVOS_TOTAL = "\n" +
            "SELECT ID_DIVISA, DIVISA , ID_CUSTODIO, NOMBRE_CORTO, SUM(MONTO)\n" +
            "FROM (\n" +
            "        SELECT CR.ID_DIVISA, CR.DIVISA , CR.ID_CUSTODIO, CR.NOMBRE_CORTO, CR.MONTO\n" +
            "        FROM\n" +
            "                (SELECT CUS.ID_CUSTODIO, CUS.NOMBRE_CORTO, CD.ID_DIVISA,\n" +
            "                        CD.CLAVE_ALFABETICA||'-'||CD.DESCRIPCION AS DIVISA,\n" +
            "                        TRAN.MONTO\n" +
            "                FROM  T_CUENTA_TRANSITORIA TRAN, C_DIVISA CD, C_CUSTODIO CUS\n" +
            "                WHERE TRAN.ID_DIVISA = CD.ID_DIVISA \n" +
            "                      AND TRAN.ID_CUSTODIO = CUS.ID_CUSTODIO\n" +
            "                      AND TRAN.MONTO < 0 \n" +
            "                      AND TRAN.ESTADO_OPERACION IN (1, 5)\n" +
            "                      AND TRAN.ESTADO_OPERACION_INTERNO IN (1, 2))CR\n" +
            "                              [FILTROS] )\n" +
            "GROUP BY ID_DIVISA, DIVISA , ID_CUSTODIO, NOMBRE_CORTO";

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Queries de seccion FOLIOS_AGRUPADOS">
    /**
     * Consulta de Informaci&oacute;n Resumen Base : FOLIO_RELACIONADO agrupados
     * También funciona para obtener la sumatoria total del detalle de una REFERENCIA
     */
    private static final String QUERY_FOLIOS_AGRUPADOS = "\n" +
            "SELECT CT.FOLIO_RELACIONADO, CT.ID_CUSTODIO, C.NOMBRE_CORTO,\n" +
            "       CT.ID_DIVISA, D.CLAVE_ALFABETICA||'-'||D.DESCRIPCION AS DIVISA,\n" +
            "       CT.TOTAL, CT.REGISTROS\n" +
            "FROM (  SELECT FOLIO_RELACIONADO, ID_CUSTODIO, ID_DIVISA, \n" +
            "               SUM(ID_TIPO_TRANSACCION*MONTO) TOTAL, SUM(REGISTRO) REGISTROS\n" +
            "        FROM (  SELECT FOLIO_RELACIONADO, ID_CUSTODIO, ID_DIVISA, \n" +
            "                       MONTO, 1 REGISTRO,\n" +
            "                       CASE \n" +
            "                            WHEN MONTO<0 THEN NULL\n" +
            "                            WHEN TIPO_MENSAJE = 566 THEN 1\n" +
            "                            WHEN TIPO_MENSAJE = 900 AND ID_CUSTODIO IN (2, 14) THEN -1\n" +
            "                            WHEN TIPO_MENSAJE = 910 AND ID_CUSTODIO IN (2, 14) THEN 1\n" +
            "                            WHEN TIPO_MENSAJE IN (900, 910) AND ID_CUSTODIO = 13 THEN 0\n" +
            "                            ELSE NULL\n" +
            "                       END AS ID_TIPO_TRANSACCION\n" +
            "                FROM T_CUENTA_TRANSITORIA\n" +
            "                WHERE TIPO_MENSAJE IN (566,900,910)\n" +
            "                      AND ESTADO_OPERACION IN (1, 5)\n" +
            "                      AND ESTADO_OPERACION_INTERNO IN (1, 2)\n" +
            "                      [FILTROS] )\n" +
            "        GROUP BY FOLIO_RELACIONADO, ID_CUSTODIO, ID_DIVISA ) CT\n" +
            "    JOIN C_DIVISA D ON CT.ID_DIVISA = D.ID_DIVISA\n" +
            "    JOIN C_CUSTODIO C ON CT.ID_CUSTODIO = C.ID_CUSTODIO\n" +
            "ORDER BY CT.FOLIO_RELACIONADO";

    /**
     * Consulta de Informaci&oacute;n Referencias: DETALLE FOLIO_AGRUPADO
     */
    private static final String QUERY_REFERENCIA_DETALLES = "\n" +
            "SELECT CT.ID_CUENTA_TRANSITORIA, CT.FOLIO_RELACIONADO, \n" +
            "       CT.TIPO_MENSAJE, CT.ID_CUSTODIO, C.NOMBRE_CORTO, \n" +
            "       CT.ID_DIVISA, D.CLAVE_ALFABETICA||'-'||D.DESCRIPCION AS DIVISA,\n" +
            "       CT.DETALLE_MOVIMIENTOS, CT. MONTO,\n" +
            "       CT.NUM_REGISTRO, CT.ID_TIPO_TRANSACCION, CT.TIPO_TRANSACCION, \n" +
            "       CAST(\n" +
            "            CASE \n" +
            "                WHEN CADENA_XML IS NULL THEN NULL\n" +
            "                WHEN INSTR(UPPER(CADENA_XML), '<SEME>') > 0 THEN \n" +
            "                SUBSTR(\n" +
            "                    CADENA_XML,\n" +
            "                        INSTR(UPPER(CADENA_XML), '<SEME>') + LENGTH('<SEME>'),\n" +
            "                        INSTR(UPPER(CADENA_XML), '</SEME>', INSTR(UPPER(CADENA_XML), '<SEME>')) \n" +
            "                            - INSTR(UPPER(CADENA_XML), '<SEME>') - LENGTH('<SEME>'))\n" +
            "                ELSE NULL\n" +
            "            END AS VARCHAR2(100)  ) AS SEME,\n" +
            "       CAST(\n" +
            "            CASE \n" +
            "                WHEN CADENA_XML IS NULL THEN NULL\n" +
            "                WHEN INSTR(UPPER(CADENA_XML), '<MENSAJEISO>') > 0 THEN \n" +
            "                    SUBSTR(\n" +
            "                        CADENA_XML,\n" +
            "                        INSTR(UPPER(CADENA_XML), '<MENSAJEISO>') + LENGTH('<MENSAJEISO>'),\n" +
            "                        INSTR(UPPER(CADENA_XML), '</MENSAJEISO>', INSTR(UPPER(CADENA_XML), '<MENSAJEISO>')) \n" +
            "                            - INSTR(UPPER(CADENA_XML), '<MENSAJEISO>') - LENGTH('<MENSAJEISO>')\n" +
            "                    )\n" +
            "                ELSE NULL\n" +
            "            END AS VARCHAR2(4000) ) AS ISO\n" +
            "FROM (\n" +
            "        SELECT ID_CUENTA_TRANSITORIA,TIPO_MENSAJE, FOLIO_RELACIONADO, ID_CUSTODIO, ID_DIVISA, \n" +
            "               DETALLE_MOVIMIENTOS, MONTO, ROWNUM NUM_REGISTRO,\n" +
            "               CASE \n" +
   //         "                    --Saldos Negativos\n" +
            "                    WHEN MONTO<0 THEN NULL\n" +
   //         "                    --Todos los mensajes 566 son depositos y se suman\n" +
            "                    WHEN TIPO_MENSAJE = 566 THEN 1 \n" +
   //         "                    --Los mensajes 910 son depositos y se suman, para los custodios\n" +
   //         "                    -- 2:EUROCLE\n" +
   //         "                    --14:BNP\n" +
            "                    WHEN TIPO_MENSAJE = 910 AND ID_CUSTODIO IN (2, 14) THEN 1\n" +
   //         "                    --Los mensajes 900 son retiros y se restan, para los custodios\n" +
   //         "                    -- 2:EUROCLE\n" +
   //         "                    --14:BNP\n" +
            "                    WHEN TIPO_MENSAJE = 900 AND ID_CUSTODIO IN (2, 14) THEN -1 \n" +
   //         "                    --Los mensajes 900 y 910 son informativos, para el custodio\n" +
   //         "                    --13:CITI\n" +
            "                    WHEN TIPO_MENSAJE IN (900, 910) AND ID_CUSTODIO = 13 THEN 0\n" +
            "                    ELSE NULL\n" +
            "               END AS ID_TIPO_TRANSACCION,     \n" +
            "               CASE \n" +
   //         "                    --Saldos Negativos\n" +
            "                    WHEN MONTO<0 THEN NULL\n" +
   //         "                    --Todos los mensajes 566 son depositos\n" +
            "                    WHEN TIPO_MENSAJE = 566 THEN 'Deposito'\n" +
   //         "                    --Los mensajes 910 son depositos, para los custodios\n" +
   //         "                    -- 2:EUROCLE\n" +
   //         "                    --14:BNP\n" +
            "                    WHEN TIPO_MENSAJE = 910 AND ID_CUSTODIO IN (2, 14) THEN 'Deposito'\n" +
   //         "                    --Los mensajes 900 son retiros, para los custodios\n" +
   //         "                    -- 2:EUROCLE\n" +
   //         "                    --14:BNP\n" +
            "                    WHEN TIPO_MENSAJE = 900 AND ID_CUSTODIO IN (2, 14) THEN 'Retiro'\n" +
   //         "                    --Los mensajes 900 y 910 son informativos, para el custodio\n" +
   //         "                    --13:CITI\n" +
            "                    WHEN TIPO_MENSAJE IN (900, 910) AND ID_CUSTODIO = 13 THEN 'Informativo'\n" +
            "                    ELSE NULL\n" +
            "               END AS TIPO_TRANSACCION, XML AS CADENA_XML\n" +
            "        FROM T_CUENTA_TRANSITORIA \n" +
            "        WHERE TIPO_MENSAJE IN (566,900,910) -- Mensajes a considerar\n" +
   //         "              --ESTADO_OPERACION a considerar\n" +
   //         "              --1:Pendiente\n" +
   //         "              --5:Sin Confirmar\n" +
            "              AND ESTADO_OPERACION IN (1, 5) \n" +
   //         "              --ESTADO_OPERACION_INTERNO a considerar\n" +
   //         "              --1:Registrado\n" +
   //         "              --2:Pendiente a Liquidar\n" +
            "              AND ESTADO_OPERACION_INTERNO IN (1, 2)\n" +
            "              [FILTROS]) CT\n" +
            "    JOIN C_DIVISA D ON CT.ID_DIVISA = D.ID_DIVISA\n" +
            "    JOIN C_CUSTODIO C ON CT.ID_CUSTODIO = C.ID_CUSTODIO\n" +
            "ORDER BY TIPO_MENSAJE";
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Query de registros sin FOLIO_RELACIONADO">
    /**
     * Consulta de Informaci&oacute;n de Registros SIN_FOLIO_RELACIONADO
     */
    private static final String QUERY_REGISTROS_SIN_REFERENCIA = "\n" +
            "SELECT NR.REFERENCIA_OPERACION, NR.TIPO_MENSAJE,\n" +
            "       CD.CLAVE_ALFABETICA||'-'||CD.DESCRIPCION,\n" +
            "       CC.NOMBRE_CORTO , NR.MONTO, NR.ID_CUENTA_TRANSITORIA,\n" +
            "       CD.ID_DIVISA, CC.ID_CUSTODIO,\n" +
            "       CAST(\n" +
            "            CASE \n" +
            "                WHEN CADENA_XML IS NULL THEN NULL\n" +
            "                WHEN INSTR(UPPER(CADENA_XML), '<MENSAJEISO>') > 0 THEN \n" +
            "                    SUBSTR(\n" +
            "                        CADENA_XML,\n" +
            "                        INSTR(UPPER(CADENA_XML), '<MENSAJEISO>') + LENGTH('<MENSAJEISO>'),\n" +
            "                        INSTR(UPPER(CADENA_XML), '</MENSAJEISO>', INSTR(UPPER(CADENA_XML), '<MENSAJEISO>')) \n" +
            "                            - INSTR(UPPER(CADENA_XML), '<MENSAJEISO>') - LENGTH('<MENSAJEISO>')\n" +
            "                    )\n" +
            "                ELSE NULL\n" +
            "            END AS VARCHAR2(4000) ) AS ISO\n" +
            "FROM (\n" +
            "     SELECT REFERENCIA_OPERACION, TIPO_MENSAJE, ID_DIVISA, ID_CUSTODIO, \n" +
            "            MONTO, ID_CUENTA_TRANSITORIA, XML AS CADENA_XML\n" +
            "     FROM T_CUENTA_TRANSITORIA\n" +
            "     WHERE ESTADO_OPERACION IN (1, 5)\n" +
            "       AND ESTADO_OPERACION_INTERNO IN (1, 2)\n" +
            "       AND TIPO_MENSAJE IN (900, 910)\n" +
            "       [FILTROS]\n" +
            "       AND FOLIO_RELACIONADO = 'NONREF') NR, C_DIVISA CD, C_CUSTODIO CC\n" +
            "WHERE NR.ID_DIVISA = CD.ID_DIVISA AND NR.ID_CUSTODIO = CC.ID_CUSTODIO\n" +
            "ORDER BY NR.REFERENCIA_OPERACION";

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Queries de validación y asignación de FOLIO_RELACIONADO">

    /**
     * Consulta de existencia de folio relacionado en T_CUENTA_TRANSITORIA
     */
    static final String QUERY_VALIDAR_FOLIO_RELACIONADO = "\n" +
            "SELECT FOLIO_RELACIONADO, ID_CUSTODIO\n" +
            "FROM T_CUENTA_TRANSITORIA\n" +
            "WHERE FOLIO_RELACIONADO ='[FOLIO_RELACIONADO]'\n" +
            "AND ROWNUM = 1";


    /**
     * Consulta de existencia de folio relacionado en T_CUENTA_TRANSITORIA
     */
    static final String QUERY_OBTENER_CUSTODIO = "\n" +
            "SELECT FOLIO_RELACIONADO, ID_CUSTODIO\n" +
            "        FROM T_CUENTA_TRANSITORIA\n" +
            "        WHERE ID_CUENTA_TRANSITORIA='[ID_REGISTRO]'";

    /**
     * Consulta de Informaci&oacute;n de Registro Base
     * Puede realizar la consulta por ID_CUENTA_TRANSITORIA o FOLIO_RELACIONADO
     */
    static final String QUERY_BASE_CONSULTA_REGISTRO =
            "SELECT ID_CUENTA_TRANSITORIA,FOLIO_RELACIONADO, ID_CUSTODIO, ID_DIVISA\n" +
                    "FROM T_CUENTA_TRANSITORIA\n" +
                    "WHERE [FILTROS]\n" +
                    "AND ROWNUM = 1";

    /**
     * Asignar FOLIO_RELACIONADO a registro no referenciado
     */
    static final String QUERY_ASIGNAR_FOLIO_RELACIONADO = "\n" +
            "UPDATE T_CUENTA_TRANSITORIA \n" +
            "SET FOLIO_RELACIONADO = '[FOLIO_RELACIONADO]', \n" +
            "    ID_CALENDARIO_INT = (SELECT ID_CALENDARIO_INT\n" +
            "                            FROM (SELECT ID_CALENDARIO_INT\n" +
            "                                  FROM T_CALENDARIO_INT  \n" +
            "                                  WHERE referencia='[FOLIO_RELACIONADO]'\n" +
            "                                  ORDER BY FECHA_CORTE, FECHA_PAGO, FECHA_VALOR DESC)\n" +
            "                            WHERE ROWNUM = 1)\n" +
            "WHERE ID_CUENTA_TRANSITORIA = '[ID_REGISTRO]'";
    // </editor-fold>


    // <editor-fold defaultstate="collapsed" desc="Armado de Query de MONTO de BOVEDA">

    /**
     * Obtiene la consulta para el MONTO TOTAL de BOVEDA
     */
    public static String getQueryTotalBoveda(String idDivisa, String idCustodio) {
        return queryArmada(idDivisa, idCustodio, null, null, null,
                QUERY_TOTAL_BOVEDA, false, false);
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Armado de queries de seccion NEGATIVOS">

    /**
     * Obtiene la consulta para el Resumen: TOTAL NEGATIVOS
     */
    public static String getQueryNegativosTotal(String idDivisa, String idCustodio) {
        return queryArmada(idDivisa, idCustodio, null, null, null,
                QUERY_NEGATIVOS_TOTAL, true, false);
    }

    /**
     * Obtiene la consulta para el Resumen: DETALLE NEGATIVOS
     */
    public static String getQueryNegativosDetalles(String idDivisa, String idCustodio) {
        return queryArmada(idDivisa, idCustodio, null, null, null,
                QUERY_NEGATIVOS_DETALLES, true, false);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Armado de queries de seccion FOLIOS_AGRUPADOS">

    /**
     * Obtiene la consulta para el Resumen: FOLIO_RELACIONADO agrupados
     */
    public static String getQueryFoliosAgrupados(
            String idDivisa, String idCustodio, String fechaInicio, String fechaFin, String folioRelacionado) {
        return queryArmada(idDivisa, idCustodio, fechaInicio, fechaFin, folioRelacionado,
                QUERY_FOLIOS_AGRUPADOS.replaceAll(
                        FILTROS, COMPLEMENTO_ADN + FILTRO_FOLIO_RELACIONADO_EXISTE), false, true);
    }

    /**
     * Obtiene la consulta para el Referencia
     */
    public static String getQueryReferenciasDetalle(
            String idDivisa, String idCustodio, String folioRelacionado) {
        return queryArmada(idDivisa, idCustodio, null, null, folioRelacionado,
                QUERY_REFERENCIA_DETALLES, false, false);

    }

    /**
     * Obtiene la consulta para el Total de Referencia
     */
    public static String getQueryReferenciasDetalleTotal(
            String idDivisa, String idCustodio, String folioRelacionado) {
        return queryArmada(idDivisa, idCustodio, null, null, folioRelacionado,
                QUERY_FOLIOS_AGRUPADOS, false, false);
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Armado de Queries de registros sin FOLIO_RELACIONADO">

    /**
     * Obtiene la consulta para los Registros sin FOLIO_REFERENCIA
     */
    public static String getQuerySinReferencias(
            String idDivisa, String idCustodio, String fechaInicio, String fechaFin, String folioRelacionado) {
        return queryArmada(idDivisa, idCustodio, fechaInicio, fechaFin, folioRelacionado,
                QUERY_REGISTROS_SIN_REFERENCIA, false, true);
    }

    /**
     * Obtiene la consulta para el MensajeISO
     */
    public static String getQueryMensajeISO(String idRegistro) {
        return QUERY_REGISTROS_SIN_REFERENCIA.replaceAll(FILTROS,
                        COMPLEMENTO_ADN + FILTRO_ID_CUENTA_TRANSITORIA).
                replaceAll(ID_REGISTRO, idRegistro);
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Armado de queries de validación y asignación de FOLIO_RELACIONADO">


    /**
     * Obtiene la consulta para validar FOLIO_RELACIONADO
     */
    public static String getQueryValidarFolioRelacionado(String folioRelacionado) {
        return QUERY_VALIDAR_FOLIO_RELACIONADO
                .replaceAll(FOLIO_RELACIONADO, folioRelacionado);
    }

    /**
     * Obtiene la consulta para validar Custodios
     */
    public static String getQueryObtenerCustodio(String idRegistro) {
        return QUERY_OBTENER_CUSTODIO.replaceAll(ID_REGISTRO, idRegistro);
    }

    /**
     * Obtiene la query para validar asignar FOLIO_RELACIONADO a registro no referenciado
     */
    public static String getQueryRegistroPorReferencia(String folioRelacionado) {
        return QUERY_BASE_CONSULTA_REGISTRO.
                replaceAll(FILTROS, FILTRO_FOLIO_RELACIONADO).
                replaceAll(FOLIO_RELACIONADO, folioRelacionado);
    }

    /**
     * Obtiene la query para realizar el UPDATE de FOLIO_RELACIONADO y CALENDARIO_INT(Si existe), del registro no referenciado
     */
    public static String getQueryAsignarFolioRelacionado(String folioRelacionado, String idRegistro) {
        return QUERY_ASIGNAR_FOLIO_RELACIONADO
                .replaceAll(FOLIO_RELACIONADO, folioRelacionado)
                .replaceAll(ID_REGISTRO, idRegistro);
    }

    // </editor-fold>


    /**
     * Arma la query por filtros:
     *
     * @param idDivisa         idDivisa seleccionada
     * @param idCustodio       idCustodio seleccionado
     * @param fechaInicio      fechaInicio seleccionada
     * @param fechaFin         fechaFin seleccionada
     * @param folioRelacionado folioRelacionado seleccionado
     * @param query            query a armar
     * @param addWhere         indica si es necesario agregar un WHERE o un AND para complementar la query
     * @param addLike          indica si solo es necesario reemplazar el FILTRO_FOLIO_RELACIONADO por el valor o hay que agregar un LIKE %%
     */
    private static String queryArmada(String idDivisa, String idCustodio,
                                      String fechaInicio, String fechaFin,
                                      String folioRelacionado, String query,
                                      boolean addWhere, boolean addLike) {
        if (idCustodio == null && idDivisa == null
                && fechaInicio == null && fechaFin == null
                && folioRelacionado == null) {// No hay ningun tipo de filtro
            return query.replaceAll(FILTROS, "");
        } else { // Hay algun tipo de filtro
            String filtros = "";

            if (idCustodio != null) {// Hay filtro de custodio
                filtros += FILTRO_ESPACIO + FILTRO_CUSTODIO;
                filtros = filtros.replaceAll(ID_CUSTODIO, idCustodio);
            }
            if (idDivisa != null) {// Hay filtro de divisa
                filtros += FILTRO_ESPACIO + FILTRO_DIVISA;
                filtros = filtros.replaceAll(ID_DIVISA, idDivisa);
            }
            if (folioRelacionado != null) {// Hay filtro de folio relacionado
                filtros += FILTRO_ESPACIO +
                        (addLike ? FILTRO_FOLIO_RELACIONADO_LIKE
                                : (COMPLEMENTO_ADN + FILTRO_FOLIO_RELACIONADO));
                filtros = filtros.replaceAll(FOLIO_RELACIONADO, folioRelacionado);
            }

            if (fechaInicio != null && fechaFin != null) {// Hay filtro de fechas
                filtros += FILTRO_ESPACIO + FILTRO_FECHAS;
                filtros = filtros.replaceAll(FECHA_INICIO, fechaInicio);
                filtros = filtros.replaceAll(FECHA_FIN, fechaFin);
            } else if (fechaInicio != null) {//Hay filtro de fecha
                filtros += FILTRO_ESPACIO + FILTRO_FECHA;
                filtros = filtros.replaceAll(FECHA_INICIO, fechaInicio);
            }
            if (addWhere) {
                filtros = filtros.replaceFirst(COMPLEMENTO_ADN, "WHERE ");
            }
            query = query.replaceAll(FILTROS, filtros);
        }

        return query;
    }
}
