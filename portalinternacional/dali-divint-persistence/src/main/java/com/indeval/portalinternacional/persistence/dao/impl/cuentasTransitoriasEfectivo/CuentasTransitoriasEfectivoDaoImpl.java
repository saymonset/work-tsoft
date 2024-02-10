package com.indeval.portalinternacional.persistence.dao.impl.cuentasTransitoriasEfectivo;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portalinternacional.persistence.dao.cuentasTransitoriasEfectivo.CuentasTransitoriasEfectivoDao;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.indeval.portalinternacional.persistence.dao.impl.cuentasTransitoriasEfectivo.ConsultasCuentasTransitoriasEfectivo.*;

/**
 * Implementaci&oacute;n de la interfaz de servicio CuentasTransitoriasEfectivoService
 *
 * @author Jacito
 */
public class CuentasTransitoriasEfectivoDaoImpl extends BaseDaoHibernateImpl implements CuentasTransitoriasEfectivoDao {
    /**
     * Log de clase
     */
    private static final Logger log = LoggerFactory.getLogger(CuentasTransitoriasEfectivoDaoImpl.class);

    public static final String N_A = "N/A";

    /**
     * @see com.indeval.portalinternacional.persistence.dao.cuentasTransitoriasEfectivo.CuentasTransitoriasEfectivoDao#obtenerDivisasExtranjeras()
     */
    @Override
    public List<String[]> obtenerDivisasExtranjeras() {
        log.debug("Entrando a obtenerDivisasExtranjeras()");
        List<String[]> divisasExtranjeras = obetenerItems(QUERY_DIVISAS_CUENTA_TRANSITORIA);
        log.debug("Divisas Extranjeras encontradas :: " + divisasExtranjeras.size());
        return divisasExtranjeras;
    }

    /**
     * @see com.indeval.portalinternacional.persistence.dao.cuentasTransitoriasEfectivo.CuentasTransitoriasEfectivoDao#obtenerCustodios()
     */
    @Override
    public List<String[]> obtenerCustodios() {
        log.debug("Entrando a obtenerCustodios()");
        List<String[]> custodios = obetenerItems(QUERY_CUSTODIOS_CUENTA_TRANSITORIA);
        log.debug("Custodios encontrados :: " + custodios.size());
        return custodios;
    }

    /**
     * @see com.indeval.portalinternacional.persistence.dao.cuentasTransitoriasEfectivo.CuentasTransitoriasEfectivoDao#obtenerNegativos(String, String)
     */
    @Override
    public List<String[]> obtenerNegativos(String idDivisa, String idCustodio) {
        log.debug("Obtener Negativos :: DAO :: " +
                "idCustodio [" + (idCustodio == null ? "Todos" : idCustodio) + "] - " +
                "idDivisas [" + (idDivisa == null ? "Todas" : idDivisa) + "] ");

        List<DivisaBean> negativos = obetenerNegativos(idDivisa, idCustodio);
        if (negativos != null) {
            List<String[]> negativosEncontrados = new ArrayList<>();
            for (DivisaBean divisa : negativos) {
                String[] divisaEncontrada = new String[5];
                divisaEncontrada = new String[]{
                        divisa.idDivisa.toString(), divisa.claveAlfabetica,
                        divisa.idCustodio.toString(), divisa.nombreCortoCustodio,
                        divisa.total.toString()};

                log.debug("Referencia :: "
                        + divisaEncontrada[0] + " | "
                        + divisaEncontrada[1] + " | "
                        + divisaEncontrada[2] + " | "
                        + divisaEncontrada[3] + " | "
                        + divisaEncontrada[4]);
                negativosEncontrados.add(divisaEncontrada);
            }
            return negativosEncontrados;
        }
        return null;

    }


    /**
     * @see com.indeval.portalinternacional.persistence.dao.cuentasTransitoriasEfectivo.CuentasTransitoriasEfectivoDao#obtenerInformacionFolioAgrupado(String, String, String, String, String)
     */
    @Override
    public List<String[]> obtenerInformacionFolioAgrupado(
            String idDivisa, String idCustodio, String fechaInicio, String fechaFin, String folioRelacionado) {
        log.debug("Obtener Resumen :: DAO :: Folio Agrupado :: " +
                "idCustodio [" + (idCustodio == null ? "Todos" : idCustodio) + "] - " +
                "idDivisas [" + (idDivisa == null ? "Todas" : idDivisa) + "] "
                + (fechaInicio == null ? "" : "- fechaInicio [" + fechaInicio + "] ")
                + (fechaFin == null ? "" : "- fechaFin [" + fechaFin + "] ")
                + (folioRelacionado == null ? "" : "- folioRelacionado [" + folioRelacionado + "] "));

        List<ReferenciaBean> referencias = obetenerFolioRelacionadoAgrupado(
                idDivisa, idCustodio, fechaInicio, fechaFin, folioRelacionado);
        if (referencias != null) {
            List<String[]> referenciasEncontradas = new ArrayList<>();
            for (ReferenciaBean referencia : referencias) {
                String[] referenciaEncontrada = new String[6];
                if (referencia.divisas.size() > 1) {
                    BigDecimal registros = new BigDecimal(0);
                    for (DivisaBean divisa : referencia.divisas) {
                        registros = registros.add(divisa.registros);
                    }
                    referenciaEncontrada = new String[]{
                            referencia.folioRelacionado, "", registros.toString(), "", "FALSE"};
                } else {
                    referenciaEncontrada = new String[]{
                            referencia.folioRelacionado,
                            referencia.divisas.get(0).nombreCortoCustodio,
                            referencia.divisas.get(0).claveAlfabetica,
                            referencia.divisas.get(0).registros.toString(),
                            referencia.divisas.get(0).total.toString(),
                            referencia.divisas.get(0).montoNegativo ? "TRUE" : "FALSE"};
                }
                log.debug("Referencia :: "
                        + referenciaEncontrada[0] + " | "
                        + referenciaEncontrada[1] + " | "
                        + referenciaEncontrada[2] + " | "
                        + referenciaEncontrada[3] + " | "
                        + referenciaEncontrada[4] + " | "
                        + referenciaEncontrada[5]);
                referenciasEncontradas.add(referenciaEncontrada);
            }
            return referenciasEncontradas;
        }
        return null;

    }

    /**
     * @see com.indeval.portalinternacional.persistence.dao.cuentasTransitoriasEfectivo.CuentasTransitoriasEfectivoDao#obtenerInformacionReferencias(String, String, String, String, String)
     */
    @Override
    public List<String[]> obtenerInformacionReferencias(
            String idDivisa, String idCustodio, String fechaInicio, String fechaFin, String folioRelacionado) {
        log.debug("Obtener Referencias :: DAO :: " +
                "idCustodio [" + (idCustodio == null ? "Todos" : idCustodio) + "] - " +
                "idDivisas [" + (idDivisa == null ? "Todas" : idDivisa) + "] "
                + (fechaInicio == null ? "" : "- fechaInicio [" + fechaInicio + "] ")
                + (fechaFin == null ? "" : "- fechaFin [" + fechaFin + "] ")
                + (folioRelacionado == null ? "" : "- folioRelacionado [" + folioRelacionado + "] "));
        List<ReferenciaBean> referencias = obetenerReferencias(
                idDivisa, idCustodio, fechaInicio, fechaFin, folioRelacionado);
        if (referencias != null && !referencias.isEmpty()) {
            List<String[]> referenciasEncontradas = new ArrayList<>();

            for (ReferenciaBean referencia : referencias) {
                log.debug(referencia.toString());
                referenciasEncontradas.add(new String[]{
                        referencia.folioRelacionado,
                        referencia.tipoMensaje,
                        referencia.divisas.get(0).claveAlfabetica,
                        referencia.divisas.get(0).idDivisa.toString(),
                        referencia.divisas.get(0).nombreCortoCustodio,
                        referencia.divisas.get(0).idCustodio.toString(),
                        referencia.divisas.get(0).total.toString(),
                        referencia.idCuentaTransitoria.toString(),
                        referencia.mensajeISO});
            }
            return referenciasEncontradas;
        }
        return null;
    }

    /**
     * @see com.indeval.portalinternacional.persistence.dao.cuentasTransitoriasEfectivo.CuentasTransitoriasEfectivoDao#obtenerDetalleReferencias(String)
     */
    @Override
    public List<String[]> obtenerDetalleReferencias(String folioRelacionado) {
        log.debug("Obtener Detalles Referencia :: DAO :: folioRelacionado [" + folioRelacionado + "]");
        List<ReferenciaBean> referencias = obetenerReferenciasDetalle(folioRelacionado);

        List<String[]> referenciasEncontradas = new ArrayList<>();
        for (ReferenciaBean referencia : referencias) {
            log.debug(referencia.toString());
            DivisaBean divisa = referencia.divisas.get(0);
            log.debug("ID_CUENTA_TRANSITORIA  [" + referencia.idCuentaTransitoria + "] :: ");
            log.debug("FOLIO_RELACIONADO [" + referencia.folioRelacionado + "] :: ");
            log.debug("TIPO_MENSAJE :: [" + referencia.tipoMensaje + "] :: ");
            log.debug("CLAVE_ALFABETICA [" + divisa.claveAlfabetica + "] :: ");
            log.debug("NOMBRE_CORTO [" + divisa.nombreCortoCustodio + "] :: ");
            log.debug("TOTAL  [" + divisa.total.toString() + "] :: ");
            log.debug("DETALLE_MOVIMIENTOS  [" + referencia.detalleMovimientos + "] :: ");
            log.debug("XML_SEME  [" + referencia.seme + "] :: ");
            log.debug("XML_MENSAJE_ISO  [" + referencia.mensajeISO + "] ");

            referenciasEncontradas.add(new String[]{
                    referencia.idCuentaTransitoria.toString(),
                    referencia.folioRelacionado,
                    referencia.tipoMensaje,
                    divisa.claveAlfabetica,
                    divisa.nombreCortoCustodio,
                    divisa.total.toString(),
                    referencia.detalleMovimientos,
                    referencia.seme,
                    referencia.mensajeISO});
        }
        return referenciasEncontradas;
    }

    /**
     * @see com.indeval.portalinternacional.persistence.dao.cuentasTransitoriasEfectivo.CuentasTransitoriasEfectivoDao#obtenerDetalleReferenciasTotal(String)
     */
    @Override
    public BigDecimal obtenerDetalleReferenciasTotal(String folioRelacionado) {
        log.debug("Obtener Detalles Referencia Total :: DAO :: folioRelacionado [" + folioRelacionado + "]");
        BigDecimal totalReferencia = obetenerReferenciasDetalleTotal(folioRelacionado);
        return totalReferencia;

    }


    /**
     * @see com.indeval.portalinternacional.persistence.dao.cuentasTransitoriasEfectivo.CuentasTransitoriasEfectivoDao#obtenerMensajeISO(String)
     */
    @Override
    public String obtenerMensajeISO(String idRegistro) {
        log.info("Obtener Mensaje ISO " + ":: [idRegistro - " + idRegistro + "]");
        String query = getQueryMensajeISO(idRegistro);
        try {
            List<Object[]> lstResult = ejecutarQuery(query);
            for (Object[] row : lstResult) {
                Object mensajeISO910Object = getString(row[6]);
                Object mensajeISO900Object = getString(row[7]);
                if (mensajeISO910Object instanceof String
                        && mensajeISO900Object instanceof String) {
                    String mensajeISO = getString(mensajeISO910Object);
                    mensajeISO = (mensajeISO.equals(N_A) ? getString(mensajeISO900Object) : mensajeISO);
                    log.debug("XML_MENSAJE_ISO  [" + mensajeISO + "]");
                    return mensajeISO;
                } else {
                    log.error("Problemas con la identificacion del tipo de objeto resultado de la query");
                    log.error(query);
                    log.debug("XML_MENSAJE_ISO_900 [" + mensajeISO900Object.getClass().getName() + "] :: "
                            + "XML_MENSAJE_ISO_910 [" + mensajeISO910Object.getClass().getName() + "]");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(ex.toString(), ex);
        }
        return null;
    }

    /**
     * @see com.indeval.portalinternacional.persistence.dao.cuentasTransitoriasEfectivo.CuentasTransitoriasEfectivoDao#validarFolioRelacionado(String, String)
     */
    @Override
    public Boolean validarFolioRelacionado(String folioRelacionado, String idRegistro) {
        log.info("Verificar existencia de "
                + "FOLIO_RELACIONADO :: [" + folioRelacionado + "]  - "
                + "ID_REGISTRO :: [" + idRegistro + "]");
        String[] referencia = existeFolioRelacionado(folioRelacionado);
        if (referencia != null) {
            log.debug("FOLIO_RELACIONADO buscado [" + folioRelacionado + "] :: " +
                    "FOLIO_RELACIONADO encontrado [" + referencia[0] + "]");
            String idCustodioFolio = referencia[1];
            String[] registro = obtenerIdCustodio(idRegistro);
            if (registro != null) {
                String idCustodioRegistro = registro[1];
                if (idCustodioRegistro.equals(idCustodioFolio)) {
                    log.debug("Custodios IGUALES :: Se puede proceder con asignación :: " +
                            "idCustodioFolio[" + idCustodioFolio + "] = " +
                            "idCuatodioRegistro [" + idCustodioRegistro + "]");
                    return Boolean.TRUE;
                } else {
                    log.debug("Custodios DIFERENTES :: Imposible realizar asignación" +
                            "idCustodioFolio[" + idCustodioFolio + "] = " +
                            "idCuatodioRegistro [" + idCustodioRegistro + "]");
                    return Boolean.FALSE;
                }
            }
            log.error("Problema al obtener registro indicado :: idRegistro[" + idRegistro + "]");
        }
        log.debug("No existe la referencia solicitada para asignacion [" + folioRelacionado + "]");
        return null;
    }

    /**
     * @see com.indeval.portalinternacional.persistence.dao.cuentasTransitoriasEfectivo.CuentasTransitoriasEfectivoDao#obtenerFolioRelacionado(String)
     */
    @Override
    public String[] obtenerFolioRelacionado(String folioRelacionado) {
        log.info("Obtener Registo  por :: FolioRelacionado :: [" + folioRelacionado + "]");
        return obtenerRegistro(getQueryRegistroPorReferencia(folioRelacionado));
    }

    /**
     * @see com.indeval.portalinternacional.persistence.dao.cuentasTransitoriasEfectivo.CuentasTransitoriasEfectivoDao#obtenerIdRegistro(String)
     */
    @Override
    public String[] obtenerIdRegistro(String idRegistro) {
        log.info("Obtener Registo  por :: idRegistro :: [" + idRegistro + "]");
        return obtenerRegistro(getQueryRegistroPorIdRegistro(idRegistro));
    }

    /**
     * @see com.indeval.portalinternacional.persistence.dao.cuentasTransitoriasEfectivo.CuentasTransitoriasEfectivoDao#asignarFolioRelacionado(String, String)
     */
    @Override
    public Boolean asignarFolioRelacionado(String folioRelacionado, String idRegistro) {
        log.info("Asignar FOLIO_RELACIONADO [" + folioRelacionado + "] :: ID_REGISTRO [" + idRegistro + "]");
        String query = getQueryAsignarFolioRelacionado(folioRelacionado, idRegistro);
        log.debug("Ejecutar :: \n" + query);
        Session session = getSession();
        SQLQuery sqlQuery = session.createSQLQuery(query);
        log.trace(sqlQuery.getQueryString());
        int update = sqlQuery.executeUpdate();
        return update > 0;

    }

    private List<DivisaBean> obetenerNegativos(String idDivisa, String idCustodio) {
        log.info("Obtener Negativos:: " +
                "[idDivisa - " + idDivisa + "] :: " +
                "[idCustodio - " + idCustodio + "] ");
        String query = getQueryNegativos(idDivisa, idCustodio);
        List<DivisaBean> negativos = new ArrayList<>();
        try {
            List<Object[]> lstResult = ejecutarQuery(query);
            for (Object[] row : lstResult) {
                Object idCustodioObject = row[0];
                Object nombreCortoObject = row[1];
                Object idDivisaObject = row[2];
                Object divisaObject = row[3];
                Object idBovedaObject = row[4];
                Object montoTesoreriaObject = row[5];
                Object saldoNegativoObject = row[6];
                Object totalObject = row[7];

                if (idCustodioObject instanceof BigDecimal
                        && nombreCortoObject instanceof String
                        && idDivisaObject instanceof BigDecimal
                        && divisaObject instanceof String
                        && idBovedaObject instanceof BigDecimal
                        && montoTesoreriaObject instanceof BigDecimal
                        && saldoNegativoObject instanceof BigDecimal
                        && totalObject instanceof BigDecimal) {
                    BigDecimal idCustodioRegistro = getBigDecimal(idCustodioObject);
                    String nombreCorto = getString(nombreCortoObject);
                    BigDecimal idDivisaRegistro = getBigDecimal(idDivisaObject);
                    String divisa = getString(divisaObject);
                    BigDecimal idBoveda = getBigDecimal(idBovedaObject);
                    BigDecimal montoTesoreria = getBigDecimal(montoTesoreriaObject);
                    BigDecimal saldoNegativo = getBigDecimal(saldoNegativoObject);
                    BigDecimal total = getBigDecimal(totalObject);

                    log.debug("ID_CUSTODIO [" + idCustodio + "] :: "
                            + "NOMBRE_CORTO [" + nombreCorto + "] :: "
                            + "ID_DIVISA [" + idDivisa + "] :: "
                            + "DIVISA [" + divisa + "] :: "
                            + "ID_BOVEDA [" + idBoveda + "] :: "
                            + "MONTO_TESORERIA [" + montoTesoreria + "] :: "
                            + "SALDO_NEGATIVO [" + saldoNegativo + "] :: "
                            + "TOTAL  [" + total + "] ");

                    negativos.add(new DivisaBean(divisa, idDivisaRegistro, nombreCorto, idCustodioRegistro, total));
                } else {
                    log.error("Problemas con la identificacion del tipo de objeto resultado de la query");
                    log.error(query);
                    log.error("ID_CUSTODIO [" + idCustodioObject.getClass().getName() + "] :: "
                            + "NOMBRE_CORTO [" + nombreCortoObject.getClass().getName() + "] :: "
                            + "ID_DIVISA [" + idDivisaObject.getClass().getName() + "] :: "
                            + "DIVISA [" + divisaObject.getClass().getName() + "] :: "
                            + "ID_BOVEDA [" + idBovedaObject.getClass().getName() + "] :: "
                            + "MONTO_TESORERIA [" + montoTesoreriaObject.getClass().getName() + "] :: "
                            + "SALDO_NEGATIVO [" + saldoNegativoObject.getClass().getName() + "] :: "
                            + "TOTAL  [" + totalObject.getClass().getName() + "] ");
                }
            }
            log.debug("Referencia encontrada :: " + negativos.size());
            return negativos;
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(ex.toString(), ex);
        }
        return null;
    }

    private List<ReferenciaBean> obetenerFolioRelacionadoAgrupado(
            String idDivisa, String idCustodio, String fechaInicio, String fechaFin, String referencia) {
        log.info("Obtener Folio Relacionado Agrupado :: " +
                "[idDivisa - " + idDivisa + "] :: " +
                "[idCustodio - " + idCustodio + "] :: " +
                "[fechaInicio - " + fechaInicio + "] :: " +
                "[fechaFin - " + fechaFin + "] :: " +
                "[folioRelacionado - " + referencia + "] ");
        String query = getQueryFoliosRelacionadosAgrupados(
                idDivisa, idCustodio, fechaInicio, fechaFin, referencia);
        List<ReferenciaBean> referencias = new ArrayList<>();
        try {
            List<Object[]> lstResult = ejecutarQuery(query);
            for (Object[] row : lstResult) {
                Object folioRelacionadoObject = row[0];
                Object nombreCortoObject = row[1];
                Object divisaObject = row[2];
                Object registrosObject = row[3];
                Object totalObject = row[4];
                if (folioRelacionadoObject instanceof String
                        && nombreCortoObject instanceof String
                        && divisaObject instanceof String
                        && registrosObject instanceof BigDecimal
                        && totalObject instanceof BigDecimal) {
                    String folioRelacionado = getString(folioRelacionadoObject);
                    String nombreCorto = getString(nombreCortoObject);
                    String divisa = getString(divisaObject);
                    BigDecimal registros = getBigDecimal(registrosObject);
                    BigDecimal total = getBigDecimal(totalObject);

                    log.debug("FOLIO_RELACIONADO [" + folioRelacionado + "] :: "
                            + "NOMBRE_CORTO [" + nombreCorto + "] :: "
                            + "DIVISA [" + divisa + "] :: "
                            + "REGISTROS [" + registros + "] :: "
                            + "TOTAL  [" + total + "] ");

                    referencias.add(new ReferenciaBean(folioRelacionado,
                            new DivisaBean(divisa, nombreCorto, registros, total)));
                } else {
                    log.error("Problemas con la identificacion del tipo de objeto resultado de la query");
                    log.error(query);
                    log.error("FOLIO_RELACIONADO [" + folioRelacionadoObject.getClass().getName() + "] :: "
                            + "NOMBRE_CORTO [" + nombreCortoObject.getClass().getName() + "] :: "
                            + "DIVISA [" + divisaObject.getClass().getName() + "] :: "
                            + "REGISTROS [" + registrosObject.getClass().getName() + "] :: "
                            + "TOTAL  [" + totalObject.getClass().getName() + "] ");
                }
            }
            log.debug("Referencia encontrada :: " + referencias.size());
            return referencias;
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(ex.toString(), ex);
        }
        return null;
    }

    private List<ReferenciaBean> obetenerReferencias(
            String idDivisa, String idCustodio, String fechaInicio, String fechaFin, String folioRelacionado_1) {
        log.info("Obtener Referencias :: " +
                "[idDivisa - " + idDivisa + "] :: " +
                "[idCustodio - " + idCustodio + "] :: " +
                "[fechaInicio - " + fechaInicio + "] :: " +
                "[fechaFin - " + fechaFin + "] :: " +
                "[folioRelacionado - " + folioRelacionado_1 + "] ");

        String query = getQueryReferencias(idDivisa, idCustodio, fechaInicio, fechaFin, folioRelacionado_1);
        List<ReferenciaBean> referencias = new ArrayList<>();
        try {
            List<Object[]> lstResult = ejecutarQuery(query);
            for (Object[] row : lstResult) {
                Object referenciaOperacionObject = row[0];
                Object tipoMensajeObject = row[1];
                Object claveAlfabeticaObject = row[2];
                Object nombreCortoCustodioObject = row[3];
                Object totalObject = row[4];
                Object idCuentaTransitoriaObject = row[5];
                Object mensajeISO910Object = getString(row[6]);
                Object mensajeISO900Object = getString(row[7]);
                Object idDivisaObject = row[8];
                Object idCustodioaObject = row[9];

                if (referenciaOperacionObject instanceof String
                        && tipoMensajeObject instanceof String
                        && claveAlfabeticaObject instanceof String
                        && nombreCortoCustodioObject instanceof String
                        && totalObject instanceof BigDecimal
                        && idCuentaTransitoriaObject instanceof BigDecimal
                        && mensajeISO910Object instanceof String
                        && mensajeISO900Object instanceof String
                        && idDivisaObject instanceof BigDecimal
                        && idCustodioaObject instanceof BigDecimal) {
                    String folioRelacionado = getString(referenciaOperacionObject);
                    String tipoMensaje = getString(tipoMensajeObject);
                    String claveAlfabetica = getString(claveAlfabeticaObject).substring(0, 3);
                    String nombreCortoCustodio = getString(nombreCortoCustodioObject);
                    BigDecimal total = getBigDecimal(totalObject);
                    BigDecimal idCuentaTransitoria = getBigDecimal(idCuentaTransitoriaObject);
                    String mensajeISO = getString(mensajeISO910Object);
                    mensajeISO = (mensajeISO.equals(N_A) ? getString(mensajeISO900Object) : mensajeISO);
                    BigDecimal idDivisaRegistro = getBigDecimal(idDivisaObject);
                    BigDecimal idCustodioRegistro = getBigDecimal(idCustodioaObject);

                    log.debug("FOLIO_RELACIONADO [" + folioRelacionado + "] :: "
                            + "TIPO_MENSAJE [" + tipoMensaje + "] :: "
                            + "CLAVE_ALFABETICA [" + claveAlfabetica + "] :: "
                            + "NOMBRE_CORTO [" + nombreCortoCustodio + "] :: "
                            + "ID_DIVISA [" + idDivisaRegistro + "] :: "
                            + "ID_CUSTODIO [" + idCustodioRegistro + "] :: "
                            + "TOTAL  [" + total + "] :: "
                            + "ID_CUENTA_TRANSITORIA  [" + idCuentaTransitoria + "] :: "
                            + "XML_MENSAJE_ISO  [" + mensajeISO + "]");

                    DivisaBean divisa = new DivisaBean(claveAlfabetica, idDivisaRegistro, nombreCortoCustodio, idCustodioRegistro, total);
                    log.debug(divisa.toString());
                    ReferenciaBean referencia = new ReferenciaBean(idCuentaTransitoria, folioRelacionado, tipoMensaje, mensajeISO, divisa);
                    referencias.add(referencia);
                    log.debug(referencia.toString());
                } else {
                    log.error("Problemas con la identificacion del tipo de objeto resultado de la query");
                    log.error(query);
                    log.debug("REFERENCIA_OPERACION [" + referenciaOperacionObject.getClass().getName() + "] :: "
                            + "TIPO_MENSAJE :: [" + tipoMensajeObject.getClass().getName() + "]"
                            + "CLAVE_ALFABETICA [" + claveAlfabeticaObject.getClass().getName() + "] :: "
                            + "NOMBRE_CORTO [" + nombreCortoCustodioObject.getClass().getName() + "] :: "
                            + "MONTO [" + totalObject.getClass().getName() + "] :: "
                            + "ID_CUENTA_TRANSITORIA  [" + idCuentaTransitoriaObject.getClass().getName() + "] :: "
                            + "XML_MENSAJE_ISO_900 [" + mensajeISO900Object.getClass().getName() + "] :: "
                            + "XML_MENSAJE_ISO_910 [" + mensajeISO910Object.getClass().getName() + "]");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(ex.toString(), ex);
        }
        return referencias;
    }

    private List<ReferenciaBean> obetenerReferenciasDetalle(String referenciaFolio) {
        log.info("Obtener Referencias Detalle :: [folioRelacionado - " + referenciaFolio + "]");
        String query = getQueryReferenciasDetalle(referenciaFolio);
        List<ReferenciaBean> referencias = new ArrayList<>();
        try {
            List<Object[]> lstResult = ejecutarQuery(query);
            for (Object[] row : lstResult) {
                Object idCuentaTransitoriaObject = row[0];
                Object tipoMensajeObject = row[1];
                Object folioRelacionadoObject = row[2];
                Object claveAlfabeticaObject = row[3];
                Object nombreCortoCustodioObject = row[4];
                Object totalObject = row[5];
                Object detalleMovimientosObject = getString(row[6]);
                Object semeObject = getString(row[7]);
                Object mensajeISOObject = getString(row[8]);

                if (idCuentaTransitoriaObject instanceof BigDecimal

                        && folioRelacionadoObject instanceof String
                        && tipoMensajeObject instanceof String
                        && claveAlfabeticaObject instanceof String
                        && nombreCortoCustodioObject instanceof String
                        && totalObject instanceof BigDecimal
                        && detalleMovimientosObject instanceof String
                        && semeObject instanceof String
                        && mensajeISOObject instanceof String) {
                    BigDecimal idCuentaTransitoria = getBigDecimal(idCuentaTransitoriaObject);
                    String folioRelacionado = getString(folioRelacionadoObject);
                    String tipoMensaje = getString(tipoMensajeObject);
                    String claveAlfabetica = getString(claveAlfabeticaObject).substring(0, 3);
                    String nombreCortoCustodio = getString(nombreCortoCustodioObject);
                    BigDecimal total = getBigDecimal(totalObject);
                    String detalleMovimientos = getString(detalleMovimientosObject);
                    String seme = getString(semeObject);
                    String mensajeISO = getString(mensajeISOObject);

                    log.debug("ID_CUENTA_TRANSITORIA  [" + idCuentaTransitoria + "] :: "
                            + "FOLIO_RELACIONADO [" + folioRelacionado + "] :: "
                            + "TIPO_MENSAJE [" + tipoMensaje + "] :: "
                            + "CLAVE_ALFABETICA [" + claveAlfabetica + "] :: "
                            + "NOMBRE_CORTO [" + nombreCortoCustodio + "] :: "
                            + "TOTAL  [" + total + "] :: "
                            + "DETALLE_MOVIMIENTOS  [" + detalleMovimientos + "] :: "
                            + "XML_SEME  [" + seme + "] :: "
                            + "XML_MENSAJE_ISO  [" + mensajeISO + "]");

                    DivisaBean divisa = new DivisaBean(claveAlfabetica, nombreCortoCustodio, total);
                    log.trace(divisa.toString());
                    ReferenciaBean referencia = new ReferenciaBean(idCuentaTransitoria,
                            folioRelacionado, tipoMensaje, detalleMovimientos, seme, mensajeISO, divisa);
                    referencias.add(referencia);
                    log.trace(referencia.toString());
                } else {
                    log.error("Problemas con la identificacion del tipo de objeto resultado de la query");
                    log.error(query);
                    log.debug("ID_CUENTA_TRANSITORIA  [" + idCuentaTransitoriaObject.getClass().getName() + "] :: "
                            + "FOLIO_RELACIONADO [" + folioRelacionadoObject.getClass().getName() + "] :: "
                            + "TIPO_MENSAJE :: [" + tipoMensajeObject.getClass().getName() + "]"
                            + "CLAVE_ALFABETICA [" + claveAlfabeticaObject.getClass().getName() + "] :: "
                            + "NOMBRE_CORTO [" + nombreCortoCustodioObject.getClass().getName() + "] :: "
                            + "TOTAL  [" + totalObject.getClass().getName() + "] :: "
                            + "DETALLE_MOVIMIENTOS  [" + detalleMovimientosObject.getClass().getName() + "] :: "
                            + "XML_SEME  [" + semeObject.getClass().getName() + "] :: "
                            + "XML_MENSAJE_ISO_D  [" + mensajeISOObject.getClass().getName() + "]");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(ex.toString(), ex);
        }
        return referencias;
    }

    private BigDecimal obetenerReferenciasDetalleTotal(String referenciaFolio) {
        log.info("Obtener Total de Referencias Detalle :: [folioRelacionado - " + referenciaFolio + "]");
        String query = getQueryReferenciasDetalleTotal(referenciaFolio);
        try {
            List<Object[]> lstResult = ejecutarQuery(query);
            for (Object[] row : lstResult) {
                Object totalObject = row[0];

                if (totalObject instanceof BigDecimal) {
                    BigDecimal total = getBigDecimal(totalObject);
                    log.debug("TOTAL  [" + total + "]");
                    return total;
                } else {
                    log.error("Problemas con la identificacion del tipo de objeto resultado de la query");
                    log.error(query);
                    log.debug("TOTAL  [" + totalObject.getClass().getName() + "]");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(ex.toString(), ex);
        }
        return null;
    }


    private String[] existeFolioRelacionado(String folioRelacionado) {
        log.info("Verificar existencia de FOLIO_RELACIONADO :: [" + folioRelacionado + "]");

        String query = getQueryValidarFolioRelacionado(folioRelacionado);
        try {
            List<Object[]> lstResult = ejecutarQuery(query);
            for (Object[] row : lstResult) {
                Object folioRelacionadoObject = row[0];
                Object idCustodioFolioObject = row[1];

                if (folioRelacionadoObject instanceof String
                        && idCustodioFolioObject instanceof BigDecimal) {
                    String folio = getString(folioRelacionadoObject);
                    BigDecimal idCustodio = getBigDecimal(idCustodioFolioObject);
                    log.debug("FOLIO_RELACIONADO  [" + folio + "] :: " +
                            "ID_CUSTODIO [" + idCustodio + "]");
                    return new String[]{folio, idCustodio.toString()};
                } else {
                    log.error("Problemas con la identificacion del tipo de objeto resultado de la query");
                    log.error(query);
                    log.debug("FOLIO_RELACIONADO  [" + folioRelacionadoObject + "] :: " +
                            "ID_CUSTODIO  [" + idCustodioFolioObject + "]");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(ex.toString(), ex);
        }
        log.debug("La referencia no existe :: " + folioRelacionado);
        return null;
    }

    private String[] obtenerIdCustodio(String idRegistro) {
        log.info("Obtener ID_CUSTODIO, del ID_REGISTRO :: [" + idRegistro + "]");

        String query = getQueryObtenerCustodio(idRegistro);
        try {
            List<Object[]> lstResult = ejecutarQuery(query);
            for (Object[] row : lstResult) {
                Object folioRelacionadoObject = row[0];
                Object idCustodioFolioObject = row[1];

                if (folioRelacionadoObject instanceof String
                        && idCustodioFolioObject instanceof BigDecimal) {
                    String folio = getString(folioRelacionadoObject);
                    BigDecimal idCustodio = getBigDecimal(idCustodioFolioObject);
                    log.debug("FOLIO_RELACIONADO  [" + folio + "] :: " +
                            "ID_CUSTODIO [" + idCustodio + "]");
                    return new String[]{folio, idCustodio.toString()};
                } else {
                    log.error("Problemas con la identificacion del tipo de objeto resultado de la query");
                    log.error(query);
                    log.debug("FOLIO_RELACIONADO  [" + folioRelacionadoObject + "] :: " +
                            "ID_CUSTODIO  [" + idCustodioFolioObject + "]");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(ex.toString(), ex);
        }
        log.debug("La referencia no existe :: " + idRegistro);
        return null;
    }

    private String[] obtenerRegistro(String query) {
        log.debug("Obtener Registro ");

        try {
            List<Object[]> lstResult = ejecutarQuery(query);
            for (Object[] row : lstResult) {
                Object idCuentaTransitoriaObject = row[0];
                Object folioRelacionadoObject = row[1];
                Object idCustodioObject = row[2];
                Object idDivisaObject = row[3];

                if (idCuentaTransitoriaObject instanceof BigDecimal
                        && folioRelacionadoObject instanceof String
                        && idCustodioObject instanceof BigDecimal
                        && idDivisaObject instanceof BigDecimal) {
                    BigDecimal idCuentaTransitoria = getBigDecimal(idCuentaTransitoriaObject);
                    String folio = getString(folioRelacionadoObject);
                    BigDecimal idCustodio = getBigDecimal(idCustodioObject);
                    BigDecimal idDivisa = getBigDecimal(idDivisaObject);
                    log.debug(
                            "ID_CUENTA_TRANSITORIA [" + idCuentaTransitoria.toString() + "] ::" +
                                    "FOLIO_RELACIONADO  [" + folio + "] :: " +
                                    "ID_CUSTODIO [" + idCustodio + "] :: " +
                                    "ID_DIVISA [" + idDivisa + "]");
                    return new String[]{
                            idCuentaTransitoria.toString(), folio,
                            idCustodio.toString(), idDivisa.toString()};
                } else {
                    log.error("Problemas con la identificacion del tipo de objeto resultado de la query");
                    log.error(query);
                    log.debug(
                            "ID_CUENTA_TRANSITORIA [" + idCuentaTransitoriaObject.getClass() + "] ::" +
                                    "FOLIO_RELACIONADO  [" + folioRelacionadoObject.getClass() + "] :: " +
                                    "ID_CUSTODIO [" + idCustodioObject.getClass() + "] :: " +
                                    "ID_DIVISA [" + idDivisaObject.getClass() + "]");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(ex.toString(), ex);
        }
        return null;
    }

    private List<String[]> obetenerItems(String query) {
        List<String[]> items = new ArrayList<>();
        try {
            List<Object[]> lstResult = ejecutarQuery(query);
            for (Object[] row : lstResult) {
                Object idObject = row[0];
                Object valueObject = row[1];
                if (idObject instanceof BigDecimal && valueObject instanceof String) {
                    BigDecimal id = getBigDecimal(idObject);
                    String value = getString(valueObject);
                    items.add(new String[]{id.toString(), value});
                    log.debug("ID [" + id + "] :: VALUE [" + value + "]");
                } else {
                    log.error("Problemas con la identificación del tipo de objeto resultado de la query");
                    log.error(query);
                    log.error("ID [" + idObject.getClass().getName() + "] :: " + "VALUE [" + valueObject.getClass().getName() + "]");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(ex.toString(), ex);
        }
        return items;
    }

    private List<Object[]> ejecutarQuery(String query) {
        log.debug("Ejecutar :: \n" + query);
        Session session = getSession();
        SQLQuery sqlQuery = session.createSQLQuery(query);
        log.trace(sqlQuery.getQueryString());
        List<Object[]> lstResult = sqlQuery.list();
        return lstResult;
    }

    private static String getString(Object object) {
        return (object == null ? N_A : (String) object);
    }


    private static BigDecimal getBigDecimal(Object object) {
        return (object == null ? new BigDecimal(0) : (BigDecimal) object);
    }

}
