package com.indeval.portalinternacional.persistence.dao.impl.cuentasTransitoriasEfectivo;


/**
 * Queries para el manejo de informaci&oacute;n de pantalla Cuentas de Tesorer&iacute;a de Efectivo
 *
 * @author Jacito
 */
public class ConsultasCuentasTransitoriasEfectivo {

    private static final String FILTROS = "\\[FILTROS\\]";
    private static final String COMPLEMENTO = "\\[COMPLEMENTO\\]";

    private static final String ID_CUSTODIO = "\\[ID_CUSTODIO\\]";
    private static final String ID_DIVISA = "\\[ID_DIVISA\\]";
    private static final String FOLIO_RELACIONADO = "\\[FOLIO_RELACIONADO\\]";
    private static final String ID_REGISTRO = "\\[ID_REGISTRO\\]";

    private static final String FECHA_INICIO = "\\[FECHA_INICIO\\]";
    private static final String FECHA_FIN = "\\[FECHA_FIN\\]";

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

    /**
     * Consulta de Referencias  Negativas
     */
    static final String QUERY_NEGATIVOS = "\n" +
            "SELECT ID_CUSTODIO, NOMBRE_CORTO, ID_DIVISA, DIVISA, BOVEDA, MONTO_TESORERIA, SALDO_NEGATIVO,\n" +
            "       (MONTO_TESORERIA-SALDO_NEGATIVO) AS TOTAL\n" +
            "FROM (  SELECT neg.ID_CUSTODIO, neg.NOMBRE_CORTO, neg.ID_DIVISA, neg.DIVISA, boveda.BOVEDA, \n" +
            "        ABS(neg.SALDO_NEGATIVO) AS SALDO_NEGATIVO, disponible.MONTO_TESORERIA\n" +
            "        FROM\n" +
            "                (   SELECT CUS.ID_CUSTODIO, CUS.NOMBRE_CORTO, CD.ID_DIVISA, \n" +
            "                            CD.CLAVE_ALFABETICA||'-'||CD.DESCRIPCION AS DIVISA, \n" +
            "                            SUM(TRAN.MONTO)AS SALDO_NEGATIVO \n" +
            "                    FROM  T_CUENTA_TRANSITORIA TRAN, C_DIVISA CD, C_CUSTODIO CUS\n" +
            "                    WHERE TRAN.ID_DIVISA = CD.ID_DIVISA AND TRAN.ID_CUSTODIO = CUS.ID_CUSTODIO\n" +
            "                          AND TRAN.MONTO < 0 \n" +
            "                    GROUP BY  CUS.ID_CUSTODIO, CUS.NOMBRE_CORTO, CD.ID_DIVISA, CD.CLAVE_ALFABETICA||'-'||CD.DESCRIPCION) neg\n" +
            "                JOIN (   SELECT ID_CUSTODIO, ID_EFECTIVO AS BOVEDA \n" +
            "                         FROM C_CUSTODIO_BOVEDA_INT ccbi\n" +
            "                         INNER JOIN R_BOVEDA_V_E rbve ON RBVE.ID_VALORES =CCBI.ID_BOVEDA) boveda\n" +
            "                         ON neg.ID_CUSTODIO = boveda.ID_CUSTODIO\n" +
            "                JOIN (  SELECT ID_BOVEDA, ID_DIVISA, SALDO_DISPONIBLE AS MONTO_TESORERIA\n" +
            "                        FROM T_SALDO_NOMBRADA\n" +
            "                        WHERE ID_CUENTA=3999 )disponible\n" +
            "                        ON boveda.BOVEDA = disponible.ID_BOVEDA AND disponible.ID_DIVISA=neg.ID_DIVISA\n" +
            "        WHERE neg.SALDO_NEGATIVO <0 ) datos \n" +
            "[FILTROS]";

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
     * Asignar FOLIO_RELACIONADO a registro no referenciado
     */
    static final String QUERY_ASIGNAR_FOLIO_RELACIONADO = "\n" +
            "UPDATE T_CUENTA_TRANSITORIA \n" +
            "SET FOLIO_RELACIONADO = '[FOLIO_RELACIONADO]' \n" +
            "WHERE ID_CUENTA_TRANSITORIA = [ID_REGISTRO]";

    /**
     * Obtiene la consulta para el Resumen: FOLIO_RELACIONADO agrupados
     */
    public static final String getQueryNegativos(String idDivisa, String idCustodio) {
        return queryArmada(idDivisa, idCustodio, null, null, null, QUERY_NEGATIVOS, true);
    }

    /**
     * Obtiene la consulta para el Resumen: FOLIO_RELACIONADO agrupados
     */
    public static final String getQueryFoliosRelacionadosAgrupados(
            String idDivisa, String idCustodio, String fechaInicio, String fechaFin, String folioRelacionado) {
        return queryArmada(idDivisa, idCustodio, fechaInicio, fechaFin,
                folioRelacionado, QUERY_FOLIOS_RELACIONADOS_AGRUPADOS, false);
    }

    /**
     * Obtiene la consulta para el MensajeISO
     */
    public static final String getQueryMensajeISO(String idRegistro) {
        String filtro = "AND ID_CUENTA_TRANSITORIA = \\'" + ID_REGISTRO + "\\'";
        return QUERY_REFERENCIAS_BASES.replaceAll(COMPLEMENTO, filtro).
                replaceAll(ID_REGISTRO, idRegistro);
    }

    /**
     * Obtiene la consulta para las Referencias
     */
    public static final String getQueryReferencias(
            String idDivisa, String idCustodio, String fechaInicio, String fechaFin, String folioRelacionado) {
        return queryArmada(idDivisa, idCustodio, fechaInicio, fechaFin, folioRelacionado,
                QUERY_REFERENCIAS_BASES.replaceAll(COMPLEMENTO, FILTROS), false);
    }

    /**
     * Obtiene la consulta para el Referencia
     */
    public static final String getQueryReferenciasDetalle(String folioRelacionado) {
        return QUERY_REFERENCIA_DETALLES.replaceAll(FOLIO_RELACIONADO, folioRelacionado);
    }

    /**
     * Obtiene la consulta para el Total de Referencia
     */
    public static final String getQueryReferenciasDetalleTotal(String folioRelacionado) {
        return QUERY_REFERENCIA_DETALLES_TOTAL.replaceAll(FOLIO_RELACIONADO, folioRelacionado);
    }

    /**
     * Obtiene la consulta para validar FOLIO_RELACIONADO
     */
    public static final String getQueryValidarFolioRelacionado(String folioRelacionado) {
        return QUERY_VALIDAR_FOLIO_RELACIONADO
                .replaceAll(FOLIO_RELACIONADO, folioRelacionado);
    }

    /**
     * Obtiene la consulta para validar Custodios
     */
    public static final String getQueryObtenerCustodio(String idRegistro) {
        return QUERY_OBTENER_CUSTODIO.replaceAll(ID_REGISTRO, idRegistro);
    }

    /**
     * Obtiene la query para validar asignar FOLIO_RELACIONADO a registro no referenciado
     */
    public static final String getQueryAsignarFolioRelacionado(String folioRelacionado, String idRegistro) {
        return QUERY_ASIGNAR_FOLIO_RELACIONADO
                .replaceAll(FOLIO_RELACIONADO, folioRelacionado)
                .replaceAll(ID_REGISTRO, idRegistro);
    }

    public static final String getQueryRegistroPorIdRegistro(String idRegistro) {
        return QUERY_BASE_CONSULTA_REGISTRO.
                replaceAll(COMPLEMENTO, " ID_CUENTA_TRANSITORIA='[ID_REGISTRO]'").
                replaceAll(ID_REGISTRO, idRegistro);
    }

    public static final String getQueryRegistroPorReferencia(String folioRelacionado) {
        return QUERY_BASE_CONSULTA_REGISTRO.
                replaceAll(COMPLEMENTO, " FOLIO_RELACIONADO ='[FOLIO_RELACIONADO]'").
                replaceAll(FOLIO_RELACIONADO, folioRelacionado);
    }

    private static final String queryArmada(String idDivisa, String idCustodio,
                                            String fechaInicio, String fechaFin,
                                            String folioRelacionado, String query,
                                            boolean addWhere) {
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
                filtros += FILTRO_ESPACIO + FILTRO_FOLIO_RELACIONADO;
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
                filtros = filtros.replaceFirst("AND", "WHERE ");
            }
            query = query.replaceAll(FILTROS, filtros);
        }

        return query;
    }

    /**
     * Consulta de Informaci&oacute;n Resumen Base : FOLIO_RELACIONADO agrupados
     */
    private static final String QUERY_FOLIOS_RELACIONADOS_AGRUPADOS = "\n" +
            "SELECT RESUMEN.FOLIO_RELACIONADO, CUS.NOMBRE_CORTO, \n" +
            "       CD.CLAVE_ALFABETICA||'-'||CD.DESCRIPCION AS DIVISA, \n" +
            "       RESUMEN.REGISTROS, RESUMEN.DEPO TOTAL \n" +
            "FROM (\n" +
            "         SELECT FOLIO_RELACIONADO, ID_CUSTODIO, ID_DIVISA, SUM(TOTAL_REGISTROS) REGISTROS, SUM(MONTO_DEPOSITOS) AS DEPO\n" +
            "         FROM (\n" +
            "                SELECT FOLIO_RELACIONADO, ID_CUSTODIO, ID_DIVISA, \n" +
            "                       CASE \n" +
            "                        WHEN ID_CUSTODIO = 13 THEN 0\n" +
            "                        ELSE SUM(MONTO) \n" +
            "                        END AS MONTO_DEPOSITOS,\n" +
            "                        COUNT(1) TOTAL_REGISTROS\n" +
            "                  FROM T_CUENTA_TRANSITORIA\n" +
            "                 WHERE TIPO_MENSAJE = 566\n" +
            "                   AND ESTADO_OPERACION IN (1, 5)\n" +
            "                   AND ESTADO_OPERACION_INTERNO IN (1, 2)\n" +
            "                   [FILTROS]\n" +
            "                    AND FOLIO_RELACIONADO <> 'NONREF'\n" +
            "                 GROUP BY FOLIO_RELACIONADO, ID_CUSTODIO, ID_DIVISA\n" +
            "                 UNION\n" +
            "                SELECT FOLIO_RELACIONADO, ID_CUSTODIO, ID_DIVISA,  SUM(MONTO) MONTO_DEPOSITOS, COUNT(1) TOTAL_REGISTROS\n" +
            "                  FROM T_CUENTA_TRANSITORIA\n" +
            "                 WHERE TIPO_MENSAJE = 910\n" +
            "                   AND ESTADO_OPERACION IN (1, 5)\n" +
            "                   AND ESTADO_OPERACION_INTERNO IN (1, 2)\n" +
            "                   [FILTROS]\n" +
            "                   AND FOLIO_RELACIONADO <> 'NONREF' \n" +
            "                 GROUP BY FOLIO_RELACIONADO, ID_CUSTODIO, ID_DIVISA\n" +
            "               )\n" +
            "         GROUP BY FOLIO_RELACIONADO, ID_CUSTODIO, ID_DIVISA        \n" +
            "         ) RESUMEN , C_DIVISA CD, C_CUSTODIO CUS\n" +
            "WHERE RESUMEN.ID_DIVISA = CD.ID_DIVISA AND RESUMEN.ID_CUSTODIO = CUS.ID_CUSTODIO";

    /**
     * Consulta de Informaci&oacute;n Referencias Base
     */
    private static final String QUERY_REFERENCIAS_BASES = "\n" +
            "SELECT NR.REFERENCIA_OPERACION, NR.TIPO_MENSAJE, \n" +
            "       CD.CLAVE_ALFABETICA||'-'||CD.DESCRIPCION, \n" +
            "       CC.NOMBRE_CORTO , NR.MONTO, NR.ID_CUENTA_TRANSITORIA, \n" +
            "       NR.MENSAJE_ISO_910, NR.MENSAJE_ISO_900, CD.ID_DIVISA, CC.ID_CUSTODIO \n" +
            "FROM (\n" +
            "     SELECT REFERENCIA_OPERACION, TIPO_MENSAJE, ID_DIVISA, ID_CUSTODIO, MONTO, ID_CUENTA_TRANSITORIA,\n" +
            "            XMLType(XML).extract('/MT910/MensajeISO/text()', 'xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"').getStringVal() MENSAJE_ISO_910,\n" +
            "            XMLType(XML).extract('/MT900/MensajeISO/text()', 'xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"').getStringVal() MENSAJE_ISO_900\n" +
            "      FROM T_CUENTA_TRANSITORIA\n" +
            "     WHERE ESTADO_OPERACION IN (1, 5)\n" +
            "       AND ESTADO_OPERACION_INTERNO IN (1, 2)\n" +
            "       AND TIPO_MENSAJE IN (900, 910)\n" +
            "          [COMPLEMENTO]\n" +
            "       AND FOLIO_RELACIONADO = 'NONREF') NR, C_DIVISA CD, C_CUSTODIO CC\n" +
            "WHERE NR.ID_DIVISA = CD.ID_DIVISA AND NR.ID_CUSTODIO = CC.ID_CUSTODIO\n" +
            "ORDER BY NR.REFERENCIA_OPERACION";


    /**
     * Consulta de Informaci&oacute;n Referencias Base
     */
    private static final String QUERY_REFERENCIA_DETALLES = "\n" +
            "  SELECT CR.ID_CUENTA_TRANSITORIA, CR.TIPO_MENSAJE, CR.FOLIO_RELACIONADO, \n" +
            "        CD.CLAVE_ALFABETICA||'-'||CD.DESCRIPCION , CC.NOMBRE_CORTO, CR.MONTO, \n" +
            "        CR.DETALLE_MOVIMIENTOS,\n" +
            "        CASE \n" +
            "            WHEN CR.SEME_DB IS NOT NULL THEN CR.SEME_DB\n" +
            "            ELSE CR.SEME_D \n" +
            "        END AS SEME, \n" +
            "        CASE \n" +
            "            WHEN CR.MENSAJE_ISO_DB IS NOT NULL THEN CR.MENSAJE_ISO_DB\n" +
            "            WHEN CR.MENSAJE_ISO_D IS NOT NULL THEN CR.MENSAJE_ISO_D\n" +
            "            WHEN CR.MENSAJE_ISO_900 IS NOT NULL THEN CR.MENSAJE_ISO_900\n" +
            "            ELSE CR.MENSAJE_ISO_910\n" +
            "        END AS MENSAJE_ISO, \n" +
            "        CR.XML\n" +
            "  FROM (\n" +
            "          SELECT ID_CUENTA_TRANSITORIA,TIPO_MENSAJE, FOLIO_RELACIONADO, ID_DIVISA, MONTO, DETALLE_MOVIMIENTOS,ID_CUSTODIO,XML,\n" +
            "                 XMLType(XML).extract('/DerechoDistribucion/seme/text()', 'xmlns=\"http://www.example.org/DerechoDistribucion\"').getStringVal() SEME_DB,\n" +
            "                 XMLType(XML).extract('/DerechoDistribucion/mensajeISO/text()', 'xmlns=\"http://www.example.org/DerechoDistribucion\"').getStringVal() MENSAJE_ISO_DB,\n" +
            "                 XMLType(XML).extract('/Derecho/seme/text()', 'xmlns=\"http://www.example.org/Derecho\"').getStringVal() SEME_D,\n" +
            "                 XMLType(XML).extract('/Derecho/mensajeISO/text()', 'xmlns=\"http://www.example.org/Derecho\"').getStringVal() MENSAJE_ISO_D,\n" +
            "                 XMLType(XML).extract('/MT910/MensajeISO/text()', 'xmlns:xsi=\\\"http://www.w3.org/2001/XMLSchema-instance\\\"').getStringVal() MENSAJE_ISO_910,\n" +
            "                 XMLType(XML).extract('/MT900/MensajeISO/text()', 'xmlns:xsi=\\\"http://www.w3.org/2001/XMLSchema-instance\\\"').getStringVal() MENSAJE_ISO_900\n" +
            "          FROM T_CUENTA_TRANSITORIA\n" +
            "          WHERE FOLIO_RELACIONADO= '[FOLIO_RELACIONADO]'\n" +
            "           )CR, C_DIVISA CD, C_CUSTODIO CC\n" +
            "  WHERE CR.ID_DIVISA = CD.ID_DIVISA AND CR.ID_CUSTODIO = CC.ID_CUSTODIO";


    /**
     * Consulta de total de Referencias Base
     */
    private static String QUERY_REFERENCIA_DETALLES_TOTAL = "\n" +
            " SELECT RESUMEN.TOTAL, RESUMEN.FOLIO_RELACIONADO\n" +
            " FROM (\n" +
            "         SELECT FOLIO_RELACIONADO, ID_DIVISA, SUM(TOTAL_REGISTROS) REGISTROS, SUM(MONTO_DEPOSITOS - MONTO_RETIROS) AS TOTAL\n" +
            "         FROM (\n" +
            "                SELECT FOLIO_RELACIONADO, ID_CUSTODIO, ID_DIVISA, \n" +
            "                       CASE \n" +
            "                        WHEN ID_CUSTODIO = 13 THEN 0\n" +
            "                        ELSE SUM(MONTO) \n" +
            "                        END AS MONTO_DEPOSITOS, \n" +
            "                        0 MONTO_RETIROS, COUNT(1) TOTAL_REGISTROS\n" +
            "                  FROM T_CUENTA_TRANSITORIA\n" +
            "                 WHERE TIPO_MENSAJE = 566\n" +
            "                   AND ESTADO_OPERACION IN (1, 5)\n" +
            "                   AND ESTADO_OPERACION_INTERNO IN (1, 2)\n" +
            "                    AND FOLIO_RELACIONADO <> 'NONREF'\n" +
            "                 GROUP BY FOLIO_RELACIONADO, ID_CUSTODIO, ID_DIVISA\n" +
            "                 UNION\n" +
            "                SELECT FOLIO_RELACIONADO, ID_CUSTODIO, ID_DIVISA, 0 MONTO_DEPOSITOS, SUM(ABS(MONTO)) MONTO_RETIROS, COUNT(1) TOTAL_REGISTROS\n" +
            "                  FROM T_CUENTA_TRANSITORIA\n" +
            "                 WHERE TIPO_MENSAJE = 900\n" +
            "                   AND ESTADO_OPERACION IN (1, 5)\n" +
            "                   AND ESTADO_OPERACION_INTERNO IN (1, 2)\n" +
            "                   AND FOLIO_RELACIONADO <> 'NONREF'\n" +
            "                 GROUP BY FOLIO_RELACIONADO, ID_CUSTODIO, ID_DIVISA\n" +
            "                 UNION\n" +
            "                SELECT FOLIO_RELACIONADO, ID_CUSTODIO, ID_DIVISA,  SUM(MONTO) MONTO_DEPOSITOS, 0 MONTO_RETIROS, COUNT(1) TOTAL_REGISTROS\n" +
            "                  FROM T_CUENTA_TRANSITORIA\n" +
            "                 WHERE TIPO_MENSAJE = 910\n" +
            "                   AND ESTADO_OPERACION IN (1, 5)\n" +
            "                   AND ESTADO_OPERACION_INTERNO IN (1, 2)\n" +
            "                   AND FOLIO_RELACIONADO <> 'NONREF' \n" +
            "                 GROUP BY FOLIO_RELACIONADO, ID_CUSTODIO, ID_DIVISA\n" +
            "               )\n" +
            "         GROUP BY FOLIO_RELACIONADO, ID_CUSTODIO, ID_DIVISA      \n" +
            "         ) RESUMEN \n" +
            " WHERE FOLIO_RELACIONADO = '[FOLIO_RELACIONADO]'";
    /**
     * Consulta de Informaci&oacute;n de Registro Base
     */
    static final String QUERY_BASE_CONSULTA_REGISTRO =
            "SELECT ID_CUENTA_TRANSITORIA,FOLIO_RELACIONADO, ID_CUSTODIO, ID_DIVISA\n" +
                    "FROM T_CUENTA_TRANSITORIA\n" +
                    "WHERE [COMPLEMENTO]\n" +
                    "AND ROWNUM = 1";

    /**
     * Complemento de filtro: espacio
     */
    private static final String FILTRO_ESPACIO = "                      ";

    /**
     * Complemento de filtro: Divisa
     */
    private static final String FILTRO_DIVISA = "AND ID_DIVISA = [ID_DIVISA]\n";

    /**
     * Complemento de filtro: Custodio
     */
    private static final String FILTRO_CUSTODIO = "AND ID_CUSTODIO = [ID_CUSTODIO]\n";

    /**
     * Complemento de filtro: FolioRelacionado
     */
    private static final String FILTRO_FOLIO_RELACIONADO = "AND FOLIO_RELACIONADO LIKE '%[FOLIO_RELACIONADO]%'\n";

    /**
     * Complemento de filtro: Fecha
     */
    private static final String FILTRO_FECHA = "AND TO_DATE(FECHA_RECEPCION,'DD/MM/YY') = TO_DATE('[FECHA_INICIO]','DD/MM/YY')\n";

    /**
     * Complemento de filtro: Fechas
     */
    private static final String FILTRO_FECHAS = "AND FECHA_RECEPCION BETWEEN TO_DATE('[FECHA_INICIO]','DD/MM/YY') AND TO_DATE ('[FECHA_FIN]','DD/MM/YY')\n";


}
