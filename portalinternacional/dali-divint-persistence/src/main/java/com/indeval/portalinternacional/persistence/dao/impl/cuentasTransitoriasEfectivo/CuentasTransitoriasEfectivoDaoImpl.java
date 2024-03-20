package com.indeval.portalinternacional.persistence.dao.impl.cuentasTransitoriasEfectivo;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portalinternacional.middleware.servicios.dto.cuentasTransitoriasEfectivo.BovedaMontosDto;
import com.indeval.portalinternacional.middleware.servicios.dto.cuentasTransitoriasEfectivo.CuentaTransitoriaEfectivoDto;
import com.indeval.portalinternacional.middleware.servicios.dto.cuentasTransitoriasEfectivo.DetalleReferenciaDto;
import com.indeval.portalinternacional.middleware.servicios.dto.cuentasTransitoriasEfectivo.FolioAgrupadoDto;
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
     * @see com.indeval.portalinternacional.persistence.dao.cuentasTransitoriasEfectivo.CuentasTransitoriasEfectivoDao#obtenerNegativosDetalles(String, String)
     */
    @Override
    public List<DetalleReferenciaDto> obtenerNegativosDetalles(String idDivisa, String idCustodio) {
        log.debug("Obtener Negativos DETALLES :: DAO :: " +
                "idCustodio [" + (idCustodio == null ? "Todos" : idCustodio) + "] - " +
                "idDivisas [" + (idDivisa == null ? "Todas" : idDivisa) + "] ");

        return obetenerNegativosDetallesDB(idDivisa, idCustodio);
    }

    /**
     * @see com.indeval.portalinternacional.persistence.dao.cuentasTransitoriasEfectivo.CuentasTransitoriasEfectivoDao#obetenerNegativosTotal(String, String)
     */
    public FolioAgrupadoDto obetenerNegativosTotal(String idDivisa, String idCustodio) {
        log.debug("Obtener Negativos TOTAL:: DAO :: " +
                "idCustodio [" + (idCustodio == null ? "Todos" : idCustodio) + "] - " +
                "idDivisas [" + (idDivisa == null ? "Todas" : idDivisa) + "] ");
        return obetenerNegativosTotalDB(idDivisa, idCustodio);
    }


    /**
     * @see com.indeval.portalinternacional.persistence.dao.cuentasTransitoriasEfectivo.CuentasTransitoriasEfectivoDao#obtenerInformacionFoliosAgrupados(String, String, String, String, String)
     */
    @Override
    public List<FolioAgrupadoDto> obtenerInformacionFoliosAgrupados(
            String idDivisa, String idCustodio, String fechaInicio, String fechaFin, String folioRelacionado) {
        log.debug("Obtener Resumen :: DAO :: Folio Agrupado :: " +
                "idCustodio [" + (idCustodio == null ? "Todos" : idCustodio) + "] - " +
                "idDivisas [" + (idDivisa == null ? "Todas" : idDivisa) + "] "
                + (fechaInicio == null ? "" : "- fechaInicio [" + fechaInicio + "] ")
                + (fechaFin == null ? "" : "- fechaFin [" + fechaFin + "] ")
                + (folioRelacionado == null ? "" : "- folioRelacionado [" + folioRelacionado + "] "));

        List<FolioAgrupadoDto> foliosAgrupados = obetenerFoliosAgrupados(
                idDivisa, idCustodio, fechaInicio, fechaFin, folioRelacionado);

        log.debug("Folios Agrupados Encontrados :: " + foliosAgrupados.size());
        return foliosAgrupados;
    }

    /**
     * @see com.indeval.portalinternacional.persistence.dao.cuentasTransitoriasEfectivo.CuentasTransitoriasEfectivoDao#obtenerInformacionSinReferencias(String, String, String, String, String)
     */
    @Override
    public List<CuentaTransitoriaEfectivoDto> obtenerInformacionSinReferencias(
            String idDivisa, String idCustodio, String fechaInicio, String fechaFin, String folioRelacionado) {
        log.debug("Obtener Referencias :: DAO :: " +
                "idCustodio [" + (idCustodio == null ? "Todos" : idCustodio) + "] - " +
                "idDivisas [" + (idDivisa == null ? "Todas" : idDivisa) + "] "
                + (fechaInicio == null ? "" : "- fechaInicio [" + fechaInicio + "] ")
                + (fechaFin == null ? "" : "- fechaFin [" + fechaFin + "] ")
                + (folioRelacionado == null ? "" : "- folioRelacionado [" + folioRelacionado + "] "));
        return obetenerSinReferencias(
                idDivisa, idCustodio, fechaInicio, fechaFin, folioRelacionado);
    }

    /**
     * @see com.indeval.portalinternacional.persistence.dao.cuentasTransitoriasEfectivo.CuentasTransitoriasEfectivoDao#obtenerDetalleReferencias(String, String, String)
     */
    @Override
    public List<DetalleReferenciaDto> obtenerDetalleReferencias(
            String idDivisa, String idCustodio, String folioRelacionado) {
        log.debug("Obtener Detalles Referencia :: DAO " +
                "idDivisas [" + idDivisa + "] " +
                "idCustodio [" + idCustodio + "] - " +
                " - folioRelacionado [" + folioRelacionado + "] ");
        List<DetalleReferenciaDto> referencias = obetenerReferenciasDetalle(
                idDivisa, idCustodio, folioRelacionado);

        log.debug("Referencias Encontradas:: " + referencias.size());

        return referencias;
    }

    /**
     * @see com.indeval.portalinternacional.persistence.dao.cuentasTransitoriasEfectivo.CuentasTransitoriasEfectivoDao#obtenerDetalleReferenciasTotal(String, String, String)
     */
    @Override
    public BigDecimal obtenerDetalleReferenciasTotal(
            String idDivisa, String idCustodio, String folioRelacionado) {
        log.debug("Obtener Detalles Referencia Total :: DAO :: " +
                "idDivisas [" + idDivisa + "] " +
                "idCustodio [" + idCustodio + "] - " +
                " - folioRelacionado [" + folioRelacionado + "] ");
        BigDecimal totalReferencia = obetenerReferenciasDetalleTotal(
                idDivisa, idCustodio, folioRelacionado);
        log.debug("Total :: " + totalReferencia);
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
                Object mensajeISOObject = getString(row[8]);
                if (mensajeISOObject instanceof String) {
                    String mensajeISO = getString(mensajeISOObject);
                    log.debug("XML_MENSAJE_ISO  [" + mensajeISO + "]");
                    return mensajeISO;
                } else {
                    log.error("Problemas con la identificacion del tipo de objeto resultado de la query");
                    log.error(query);
                    log.error("XML_MENSAJE_ISO_910 [" + mensajeISOObject.getClass().getName() + "]");
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

    /**
     * @see com.indeval.portalinternacional.persistence.dao.cuentasTransitoriasEfectivo.CuentasTransitoriasEfectivoDao#obetenerTotalBoveda(String, String)
     */
    @Override
    public BovedaMontosDto obetenerTotalBoveda(String idDivisa, String idCustodio) {
        log.debug("Obtener Saldos Boveda :: DAO :: " +
                "idDivisas [" + idDivisa + "] " +
                "idCustodio [" + idCustodio + "] ");
        BovedaMontosDto bovedaMontosDto = obetenerTotalBovedaDB(idDivisa, idCustodio);
        log.debug((bovedaMontosDto == null ? "NULL" : bovedaMontosDto.toString()));
        return bovedaMontosDto;
    }


    private List<DetalleReferenciaDto> obetenerNegativosDetallesDB(String idDivisa, String idCustodio) {
        log.info("Obtener Negativos:: " +
                "[idDivisa - " + idDivisa + "] :: " +
                "[idCustodio - " + idCustodio + "] ");

        String query = getQueryNegativosDetalles(idDivisa, idCustodio);
        List<DetalleReferenciaDto> negativos = new ArrayList<>();
        try {
            List<Object[]> lstResult = ejecutarQuery(query);
            for (Object[] row : lstResult) {
                Object idCuentaTransitoriaObject = row[0];
                Object tipoMensajeObject = row[1];
                Object detalleMovimientosObject = getString(row[2]);
                Object idDivisaObject = row[3];
                Object divisaObject = row[4];
                Object idCustodioObject = row[5];
                Object nombreCortoObject = row[6];
                Object montoObject = row[7];
                Object folioRelacionadoObject = getString(row[8]);
                Object semeObject = getString(row[9]);
                Object mensajeISOObject = getString(row[10]);


                if (idCuentaTransitoriaObject instanceof BigDecimal
                        && tipoMensajeObject instanceof String
                        && detalleMovimientosObject instanceof String
                        && idDivisaObject instanceof BigDecimal
                        && divisaObject instanceof String
                        && idCustodioObject instanceof BigDecimal
                        && nombreCortoObject instanceof String
                        && montoObject instanceof BigDecimal
                        && folioRelacionadoObject instanceof String
                        && semeObject instanceof String
                        && mensajeISOObject instanceof String) {
                    BigDecimal idCuentaTransitoria = getBigDecimal(idCuentaTransitoriaObject);
                    String tipoMensaje = getString(tipoMensajeObject);
                    String detalleMovimientos = getString(detalleMovimientosObject);
                    BigDecimal idDivisaRegistro = getBigDecimal(idDivisaObject);
                    String divisa = getString(divisaObject);
                    BigDecimal idCustodioRegistro = getBigDecimal(idCustodioObject);
                    String nombreCorto = getString(nombreCortoObject);
                    BigDecimal monto = getBigDecimal(montoObject);
                    String folioRelacionado = getString(folioRelacionadoObject);
                    String seme = getString(semeObject);
                    String mensajeISO = getString(mensajeISOObject);

                    log.debug("ID_CUENTA_TRANSITORIA [" + idCuentaTransitoria + "] ::"
                            + "TIPO_MENSAJE [" + tipoMensaje + "] ::"
                            + "DETALLE_MOVIMIENTOS [" + detalleMovimientos + "] ::"
                            + "ID_DIVISA [" + idDivisaRegistro + "] ::"
                            + "DIVISA [" + divisa + "] ::"
                            + "ID_CUSTODIO [" + idCustodioRegistro + "] ::"
                            + "NOMBRE_CORTO [" + nombreCorto + "] ::"
                            + "MONTO [" + monto + "] ::"
                            + "FOLIO_RELACIONADO [" + folioRelacionado + "] ::"
                            + "MENSAJE_ISO [" + mensajeISO + "]");

                    DetalleReferenciaDto registroNegativo = new DetalleReferenciaDto();
                    registroNegativo.setIdRegistro(idCuentaTransitoria.toString());
                    registroNegativo.setTipoMensaje(tipoMensaje);
                    registroNegativo.setDetalleMovimientos(detalleMovimientos);
                    registroNegativo.setIdDivisa(idDivisa);
                    registroNegativo.setDivisa(divisa);
                    registroNegativo.setIdCustodio(idCustodio);
                    registroNegativo.setCustodio(nombreCorto);
                    registroNegativo.setTotal(monto);
                    registroNegativo.setFolioRelacionado(folioRelacionado);
                    registroNegativo.setSeme(seme);
                    registroNegativo.setMensajeISO(mensajeISO);
                    registroNegativo.setMontoNegativo(true);
                    log.debug(registroNegativo.toString());
                    negativos.add(registroNegativo);
                } else {
                    log.error("Problemas con la identificacion del tipo de objeto resultado de la query");
                    log.error(query);
                    log.error("ID_CUENTA_TRANSITORIA [" + idCuentaTransitoriaObject.getClass() + "] ::"
                            + "TIPO_MENSAJE [" + tipoMensajeObject.getClass() + "] ::"
                            + "DETALLE_MOVIMIENTOS [" + detalleMovimientosObject.getClass() + "] ::"
                            + "ID_DIVISA [" + idDivisaObject.getClass() + "] ::"
                            + "DIVISA [" + divisaObject.getClass() + "] ::"
                            + "ID_CUSTODIO [" + idCustodioObject.getClass() + "] ::"
                            + "NOMBRE_CORTO [" + nombreCortoObject.getClass() + "] ::"
                            + "MONTO [" + montoObject.getClass() + "] ::"
                            + "FOLIO_RELACIONADO [" + folioRelacionadoObject.getClass() + "] ::"
                            + "SEME [" + semeObject.getClass() + "] :: "
                            + "MENSAJE_ISO [" + mensajeISOObject.getClass() + "]");
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

    private BovedaMontosDto obetenerTotalBovedaDB(String idDivisa, String idCustodio) {
        log.info("Obtener Total Boveda:: " +
                "[idDivisa - " + idDivisa + "] :: " +
                "[idCustodio - " + idCustodio + "] ");

        String query = getQueryTotalBoveda(idDivisa, idCustodio);
        try {
            List<Object[]> lstResult = ejecutarQuery(query);
            for (Object[] row : lstResult) {
                Object idBovedaObject = row[0];
                Object bovedaObject = row[1];
                Object idCustodioObject = row[2];
                Object custodioObject = row[3];
                Object idDivisaObject = row[4];
                Object divisaObject = row[5];
                Object saldoDisponibleObject = row[6];
                Object saldoNoDisponibleObject = row[7];
                Object saldoTotalObject = row[8];


                if (idBovedaObject instanceof BigDecimal
                        && bovedaObject instanceof String
                        && idCustodioObject instanceof BigDecimal
                        && custodioObject instanceof String
                        && idDivisaObject instanceof BigDecimal
                        && divisaObject instanceof String
                        && saldoDisponibleObject instanceof BigDecimal
                        && saldoNoDisponibleObject instanceof BigDecimal
                        && saldoTotalObject instanceof BigDecimal) {

                    BigDecimal idBoveda = getBigDecimal(idBovedaObject);
                    String boveda = getString(bovedaObject);
                    BigDecimal idCustodioRegistro = getBigDecimal(idCustodioObject);
                    String custodio = getString(custodioObject);
                    BigDecimal idDivisaRegistro = getBigDecimal(idDivisaObject);
                    String divisa = getString(divisaObject);
                    BigDecimal saldoDisponible = getBigDecimal(saldoDisponibleObject);
                    BigDecimal saldoNoDisponible = getBigDecimal(saldoNoDisponibleObject);
                    BigDecimal saldoTotal = getBigDecimal(saldoTotalObject);

                    log.debug("ID_BOVEDA [" + idBoveda + "] :: "
                            + "NOMBRE_BOVEDA [" + boveda + "] :: "
                            + "ID_CUSTODIO [" + idCustodioRegistro + "] :: "
                            + "CUSTODIO [" + custodio + "] :: "
                            + "ID_DIVISA [" + idDivisaRegistro + "] :: "
                            + "DIVISA [" + divisa + "] :: "
                            + "SALDO_DISPONIBLE [" + saldoDisponible + "] :: "
                            + "SALDO_NO_DISPONIBLE [" + saldoNoDisponible + "] :: "
                            + "SALDO_TOTAL [" + saldoTotal + "] :: ");

                    BovedaMontosDto bovedaMontosDto = new BovedaMontosDto();
                    bovedaMontosDto.setIdBoveda(idBoveda);
                    bovedaMontosDto.setBoveda(boveda);
                    bovedaMontosDto.setIdCustodio(idCustodio);
                    bovedaMontosDto.setCustodio(custodio);
                    bovedaMontosDto.setIdDivisa(idDivisa);
                    bovedaMontosDto.setDivisa(divisa);
                    bovedaMontosDto.setSaldoDisponible(saldoDisponible);
                    bovedaMontosDto.setSaldoNoDisponible(saldoNoDisponible);
                    bovedaMontosDto.setSaldoTotal(saldoTotal);
                    bovedaMontosDto.setMontoNegativo(saldoTotal.compareTo(BigDecimal.ZERO) < 0);
                    log.debug("Boveda Montos :: " + bovedaMontosDto.toString());
                    return bovedaMontosDto;
                } else {
                    log.error("Problemas con la identificacion del tipo de objeto resultado de la query");
                    log.error(query);
                    log.error("ID_BOVEDA [" + idBovedaObject.getClass().getName() + "] :: "
                            + "NOMBRE_BOVEDA [" + bovedaObject.getClass().getName() + "] :: "
                            + "ID_CUSTODIO [" + idCustodioObject.getClass().getName() + "] :: "
                            + "CUSTODIO [" + custodioObject.getClass().getName() + "] :: "
                            + "ID_DIVISA [" + idDivisaObject.getClass().getName() + "] :: "
                            + "DIVISA [" + divisaObject.getClass().getName() + "] :: "
                            + "SALDO_DISPONIBLE [" + saldoDisponibleObject.getClass().getName() + "] :: "
                            + "SALDO_NO_DISPONIBLE [" + saldoNoDisponibleObject.getClass().getName() + "] :: "
                            + "SALDO_TOTAL [" + saldoTotalObject.getClass().getName() + "] ");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(ex.toString(), ex);
        }
        return null;
    }

    private FolioAgrupadoDto obetenerNegativosTotalDB(String idDivisa, String idCustodio) {
        log.info("Obtener Negativos:: " +
                "[idDivisa - " + idDivisa + "] :: " +
                "[idCustodio - " + idCustodio + "] ");

        String query = getQueryNegativosTotal(idDivisa, idCustodio);
        try {
            List<Object[]> lstResult = ejecutarQuery(query);
            for (Object[] row : lstResult) {
                Object idDivisaObject = row[0];
                Object divisaObject = row[1];
                Object idCustodioObject = row[2];
                Object nombreCortoObject = row[3];
                Object montoObject = row[4];


                if (idDivisaObject instanceof BigDecimal
                        && divisaObject instanceof String
                        && idCustodioObject instanceof BigDecimal
                        && nombreCortoObject instanceof String
                        && montoObject instanceof BigDecimal) {

                    BigDecimal idDivisaRegistro = getBigDecimal(idDivisaObject);
                    String divisa = getString(divisaObject);
                    BigDecimal idCustodioRegistro = getBigDecimal(idCustodioObject);
                    String nombreCorto = getString(nombreCortoObject);
                    BigDecimal monto = getBigDecimal(montoObject);

                    log.debug("ID_DIVISA [" + idDivisaRegistro + "] ::"
                            + "DIVISA [" + divisa + "] ::"
                            + "ID_CUSTODIO [" + idCustodioRegistro + "] ::"
                            + "NOMBRE_CORTO [" + nombreCorto + "] ::"
                            + "MONTO [" + monto + "]");

                    FolioAgrupadoDto negativosTotal = new FolioAgrupadoDto();
                    negativosTotal.setIdDivisa(idDivisa);
                    negativosTotal.setDivisa(divisa);
                    negativosTotal.setIdCustodio(idCustodio);
                    negativosTotal.setCustodio(nombreCorto);
                    negativosTotal.setTotal(monto);
                    negativosTotal.setMontoNegativo(true);
                    log.debug("Negativo Total :: " + negativosTotal.toString());
                    return negativosTotal;
                } else {
                    log.error("Problemas con la identificacion del tipo de objeto resultado de la query");
                    log.error(query);
                    log.error("ID_DIVISA [" + idDivisaObject.getClass().getName() + "] ::"
                            + "DIVISA [" + divisaObject.getClass().getName() + "] ::"
                            + "ID_CUSTODIO [" + idCustodioObject.getClass().getName() + "] ::"
                            + "NOMBRE_CORTO [" + nombreCortoObject.getClass().getName() + "] ::"
                            + "MONTO [" + montoObject.getClass().getName() + "]");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(ex.toString(), ex);
        }
        return null;
    }


    private List<FolioAgrupadoDto> obetenerFoliosAgrupados(
            String idDivisa, String idCustodio, String fechaInicio, String fechaFin, String folio) {
        log.info("Obtener Folio Relacionado Agrupado :: " +
                "[idDivisa - " + idDivisa + "] :: " +
                "[idCustodio - " + idCustodio + "] :: " +
                "[fechaInicio - " + fechaInicio + "] :: " +
                "[fechaFin - " + fechaFin + "] :: " +
                "[folioRelacionado - " + folio + "] ");
        String query = getQueryFoliosAgrupados(
                idDivisa, idCustodio, fechaInicio, fechaFin, folio);
        List<FolioAgrupadoDto> foliosAgrupados = new ArrayList<>();
        try {
            List<Object[]> lstResult = ejecutarQuery(query);
            for (Object[] row : lstResult) {
                Object folioRelacionadoObject = row[0];
                Object idCustodioObject = row[1];
                Object nombreCortoObject = row[2];
                Object idDivisaObject = row[3];
                Object divisaObject = row[4];
                Object totalObject = getBigDecimal(row[5]);
                Object registrosObject = row[6];

                if (folioRelacionadoObject instanceof String
                        && idCustodioObject instanceof BigDecimal
                        && nombreCortoObject instanceof String
                        && idDivisaObject instanceof BigDecimal
                        && divisaObject instanceof String
                        && totalObject instanceof BigDecimal
                        && registrosObject instanceof BigDecimal) {
                    String folioRelacionado = getString(folioRelacionadoObject);
                    BigDecimal idCustodioRegistro = getBigDecimal(idCustodioObject);
                    String nombreCorto = getString(nombreCortoObject);
                    String divisa = getString(divisaObject);
                    BigDecimal idDivisaRegistro = getBigDecimal(idDivisaObject);
                    BigDecimal total = getBigDecimal(totalObject);
                    BigDecimal registros = getBigDecimal(registrosObject);


                    log.debug("FOLIO_RELACIONADO [" + folioRelacionado + "] :: "
                            + "ID_CUSTODIO [" + idCustodio + "] :: "
                            + "NOMBRE_CORTO [" + nombreCorto + "] :: "
                            + "ID_DIVISA [" + idDivisa + "] :: "
                            + "DIVISA [" + divisa + "] :: "
                            + "REGISTROS [" + registros + "] :: "
                            + "TOTAL  [" + total + "] ");

                    FolioAgrupadoDto folioAgrupadoDto = new FolioAgrupadoDto();
                    folioAgrupadoDto.setFolioRelacionado(folioRelacionado);
                    folioAgrupadoDto.setIdDivisa(idDivisaRegistro.toString());
                    folioAgrupadoDto.setDivisa(divisa.substring(0, 3));
                    folioAgrupadoDto.setDivisaExtendida(divisa);
                    folioAgrupadoDto.setIdCustodio(idCustodioRegistro.toString());
                    folioAgrupadoDto.setCustodio(nombreCorto);
                    folioAgrupadoDto.setRegistros(registros.toString());
                    folioAgrupadoDto.setTotal(total);
                    folioAgrupadoDto.setMontoNegativo(total.compareTo(BigDecimal.ZERO) < 0);
                    log.debug(folioAgrupadoDto.toString());
                    foliosAgrupados.add(folioAgrupadoDto);
                } else {
                    log.error("Problemas con la identificacion del tipo de objeto resultado de la query");
                    log.error(query);
                    log.error("FOLIO_RELACIONADO [" + folioRelacionadoObject.getClass().getName() + "] :: "
                            + "ID_CUSTODIO [" + idCustodioObject.getClass().getName() + "] :: "
                            + "NOMBRE_CORTO [" + nombreCortoObject.getClass().getName() + "] :: "
                            + "ID_DIVISA [" + idDivisaObject.getClass().getName() + "] :: "
                            + "DIVISA [" + divisaObject.getClass().getName() + "] :: "
                            + "REGISTROS [" + registrosObject.getClass().getName() + "] :: "
                            + "TOTAL  [" + totalObject.getClass().getName() + "] ");
                }
            }
            log.debug("Referencia encontrada :: " + foliosAgrupados.size());
            return foliosAgrupados;
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(ex.toString(), ex);
        }
        return null;
    }

    private List<CuentaTransitoriaEfectivoDto> obetenerSinReferencias(
            String idDivisa, String idCustodio, String fechaInicio, String fechaFin, String folioRelacionado_1) {
        log.info("Obtener Sin Referencias :: " +
                "[idDivisa - " + idDivisa + "] :: " +
                "[idCustodio - " + idCustodio + "] :: " +
                "[fechaInicio - " + fechaInicio + "] :: " +
                "[fechaFin - " + fechaFin + "] :: " +
                "[folioRelacionado - " + folioRelacionado_1 + "] ");
        List<CuentaTransitoriaEfectivoDto> referencias = new ArrayList<>();
        try {
            String query = getQuerySinReferencias(idDivisa, idCustodio, fechaInicio, fechaFin, folioRelacionado_1);
            List<Object[]> lstResult = ejecutarQuery(query);
            for (Object[] row : lstResult) {
                Object referenciaOperacionObject = row[0];
                Object tipoMensajeObject = row[1];
                Object claveAlfabeticaObject = row[2];
                Object nombreCortoCustodioObject = row[3];
                Object totalObject = row[4];
                Object idCuentaTransitoriaObject = row[5];
                Object idDivisaObject = row[6];
                Object idCustodioaObject = row[7];
                Object mensajeISOObject = getString(row[8]);

                if (referenciaOperacionObject instanceof String
                        && tipoMensajeObject instanceof String
                        && claveAlfabeticaObject instanceof String
                        && nombreCortoCustodioObject instanceof String
                        && totalObject instanceof BigDecimal
                        && idCuentaTransitoriaObject instanceof BigDecimal
                        && idDivisaObject instanceof BigDecimal
                        && idCustodioaObject instanceof BigDecimal
                        && mensajeISOObject instanceof String) {
                    String folioRelacionado = getString(referenciaOperacionObject);
                    String tipoMensaje = getString(tipoMensajeObject);
                    String claveAlfabetica = getString(claveAlfabeticaObject).substring(0, 3);
                    String nombreCortoCustodio = getString(nombreCortoCustodioObject);
                    BigDecimal total = getBigDecimal(totalObject);
                    BigDecimal idCuentaTransitoria = getBigDecimal(idCuentaTransitoriaObject);
                    String mensajeISO = getString(mensajeISOObject);
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


                    CuentaTransitoriaEfectivoDto referenciada = new CuentaTransitoriaEfectivoDto();
                    referenciada.setReferenciaOperacion(folioRelacionado);
                    referenciada.setTipoMensaje(tipoMensaje);
                    referenciada.setDivisa(claveAlfabetica);
                    referenciada.setIdDivisa(idDivisa);
                    referenciada.setCustodio(nombreCortoCustodio);
                    referenciada.setIdCustodio(idCustodio);
                    referenciada.setTotal(total);
                    referenciada.setIdRegistro(idCuentaTransitoria.toString());
                    referenciada.setMensajeISO(mensajeISO);
                    referenciada.setMontoNegativo(total.compareTo(BigDecimal.ZERO) < 0);
                    referencias.add(referenciada);
                    log.debug(referenciada.toString());
                } else {
                    log.error("Problemas con la identificacion del tipo de objeto resultado de la query");
                    log.error(query);
                    log.error("REFERENCIA_OPERACION [" + referenciaOperacionObject.getClass().getName() + "] :: "
                            + "TIPO_MENSAJE :: [" + tipoMensajeObject.getClass().getName() + "]"
                            + "CLAVE_ALFABETICA [" + claveAlfabeticaObject.getClass().getName() + "] :: "
                            + "NOMBRE_CORTO [" + nombreCortoCustodioObject.getClass().getName() + "] :: "
                            + "MONTO [" + totalObject.getClass().getName() + "] :: "
                            + "ID_CUENTA_TRANSITORIA  [" + idCuentaTransitoriaObject.getClass().getName() + "] :: "
                            + "XML_MENSAJE_ISO[" + mensajeISOObject.getClass().getName() + "]");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(ex.toString(), ex);
        }
        return referencias;
    }

    private List<DetalleReferenciaDto> obetenerReferenciasDetalle(
            String idDivisa, String idCustodio, String referenciaFolio) {
        log.info("Obtener Referencias Detalle " +
                "[idDivisa - " + idDivisa + "] :: " +
                "[idCustodio - " + idCustodio + "] :: " +
                "[folioRelacionado - " + referenciaFolio + "] ");
        String query = getQueryReferenciasDetalle(idDivisa, idCustodio, referenciaFolio);
        List<DetalleReferenciaDto> referencias = new ArrayList<>();
        try {
            List<Object[]> lstResult = ejecutarQuery(query);
            for (Object[] row : lstResult) {
                Object idCuentaTransitoriaObject = row[0];
                Object folioRelacionadoObject = row[1];
                Object tipoMensajeObject = row[2];
                Object idCustodioObject = row[3];
                Object custodioObject = row[4];
                Object idDivisaObject = row[5];
                Object divisaObject = row[6];
                Object detalleMovimientosObject = getString(row[7]);
                Object montoObject = row[8];
                Object numeroRegistroObject = row[9];
                Object idTipoTransaccionObject = getBigDecimal(row[10]);
                Object tipoTransaccionObject = getString(row[11]);
                Object semeObject = getString(row[12]);
                Object mensajeISOObject = getString(row[13]);


                if (idCuentaTransitoriaObject instanceof BigDecimal
                        && folioRelacionadoObject instanceof String
                        && tipoMensajeObject instanceof String
                        && idCustodioObject instanceof BigDecimal
                        && custodioObject instanceof String
                        && idDivisaObject instanceof BigDecimal
                        && divisaObject instanceof String
                        && detalleMovimientosObject instanceof String
                        && montoObject instanceof BigDecimal
                        && numeroRegistroObject instanceof BigDecimal
                        && idTipoTransaccionObject instanceof BigDecimal
                        && tipoTransaccionObject instanceof String
                        && semeObject instanceof String
                        && mensajeISOObject instanceof String) {

                    BigDecimal idCuentaTransitoria = getBigDecimal(idCuentaTransitoriaObject);
                    String folioRelacionado = getString(folioRelacionadoObject);
                    String tipoMensaje = getString(tipoMensajeObject);
                    BigDecimal idCustodioRegistro = getBigDecimal(idCustodioObject);
                    String custodio = getString(custodioObject);
                    BigDecimal idDivisaRegistro = getBigDecimal(idDivisaObject);
                    String divisa = getString(divisaObject).substring(0, 3);
                    String detalleMovimientos = getString(detalleMovimientosObject);
                    BigDecimal monto = getBigDecimal(montoObject);
                    BigDecimal numeroRegistro = getBigDecimal(numeroRegistroObject);
                    BigDecimal idTipoTransaccion = getBigDecimal(idTipoTransaccionObject);
                    String tipoTransaccion = getString(tipoTransaccionObject);
                    String seme = getString(semeObject);
                    String mensajeISO = getString(mensajeISOObject);

                    log.debug("ID_CUENTA_TRANSITORIA  [" + idCuentaTransitoria + "] :: "
                            + "FOLIO_RELACIONADO [" + folioRelacionado + "] :: "
                            + "TIPO_MENSAJE [" + tipoMensaje + "] :: "
                            + "ID_CUSTODIO [" + idCustodioRegistro + "] :: "
                            + "CUSTODIO [" + custodio + "] :: "
                            + "ID_DIVISA [" + idDivisaRegistro + "] :: "
                            + "DIVISA [" + divisa + "] :: "
                            + "DETALLE_MOVIMIENTOS  [" + detalleMovimientos + "] :: "
                            + "MONTO [" + monto + "] :: "
                            + "NUM_REGISTRO [" + numeroRegistro + "] :: "
                            + "ID_TIPO_TRANSACCION [" + idTipoTransaccion + "] :: "
                            + "TIPO_TRANSACCION [" + tipoTransaccion + "] :: "
                            + "XML_SEME  [" + seme + "] :: "
                            + "XML_MENSAJE_ISO  [" + mensajeISO + "]");


                    DetalleReferenciaDto referencia = new DetalleReferenciaDto();
                    referencia.setIdRegistro(idCuentaTransitoria.toString());
                    referencia.setFolioRelacionado(folioRelacionado);
                    referencia.setTipoMensaje(tipoMensaje);
                    referencia.setIdCustodio(idCustodioRegistro.toString());
                    referencia.setCustodio(custodio);
                    referencia.setIdDivisa(idDivisaRegistro.toString());
                    referencia.setDivisa(divisa);
                    referencia.setDetalleMovimientos(detalleMovimientos);
                    referencia.setTotal(monto);
                    referencia.setNumeroRegistro(numeroRegistro);
                    referencia.setIdTipoTransaccion(idTipoTransaccion);
                    referencia.setTipoTransaccion(tipoTransaccion);
                    referencia.setSeme(seme);
                    referencia.setMensajeISO(mensajeISO);
                    referencia.setMontoNegativo(monto.compareTo(BigDecimal.ZERO) < 0);
                    referencias.add(referencia);
                    log.trace(referencia.toString());
                } else {
                    log.error("Problemas con la identificacion del tipo de objeto resultado de la query");
                    log.error(query);
                    log.error("ID_CUENTA_TRANSITORIA  [" + idCuentaTransitoriaObject.getClass().getName() + "] :: "
                            + "FOLIO_RELACIONADO [" + folioRelacionadoObject.getClass().getName() + "] :: "
                            + "TIPO_MENSAJE [" + tipoMensajeObject.getClass().getName() + "] :: "
                            + "ID_CUSTODIO [" + idCustodioObject.getClass().getName() + "] :: "
                            + "CUSTODIO [" + custodioObject.getClass().getName() + "] :: "
                            + "ID_DIVISA [" + idDivisaObject.getClass().getName() + "] :: "
                            + "DIVISA [" + divisaObject.getClass().getName() + "] :: "
                            + "DETALLE_MOVIMIENTOS  [" + detalleMovimientosObject.getClass().getName() + "] :: "
                            + "MONTO [" + montoObject.getClass().getName() + "] :: "
                            + "NUM_REGISTRO [" + numeroRegistroObject.getClass().getName() + "] :: "
                            + "ID_TIPO_TRANSACCION [" + idTipoTransaccionObject.getClass().getName() + "] :: "
                            + "TIPO_TRANSACCION [" + tipoTransaccionObject.getClass().getName() + "] :: "
                            + "XML_SEME  [" + semeObject.getClass().getName() + "] :: "
                            + "XML_MENSAJE_ISO  [" + mensajeISOObject.getClass().getName() + "]");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(ex.toString(), ex);
        }
        return referencias;
    }

    private BigDecimal obetenerReferenciasDetalleTotal(
            String idDivisa, String idCustodio, String referenciaFolio) {
        log.info("Obtener Total de Referencias Detalle " +
                "[idDivisa - " + idDivisa + "] :: " +
                "[idCustodio - " + idCustodio + "] :: " +
                "[folioRelacionado - " + referenciaFolio + "] ");
        String query = getQueryReferenciasDetalleTotal(idDivisa, idCustodio, referenciaFolio);
        try {
            List<Object[]> lstResult = ejecutarQuery(query);
            for (Object[] row : lstResult) {
                Object totalObject = row[5];

                if (totalObject instanceof BigDecimal) {
                    BigDecimal total = getBigDecimal(totalObject);
                    log.debug("TOTAL  [" + total + "]");
                    return total;
                } else {
                    log.error("Problemas con la identificacion del tipo de objeto resultado de la query");
                    log.error(query);
                    log.error("TOTAL  [" + totalObject.getClass().getName() + "]");
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
                    log.error("ID_CUENTA_TRANSITORIA [" + idCuentaTransitoriaObject.getClass().getName() + "] ::" +
                            "FOLIO_RELACIONADO  [" + folioRelacionadoObject.getClass().getName() + "] :: " +
                            "ID_CUSTODIO [" + idCustodioObject.getClass().getName() + "] :: " +
                            "ID_DIVISA [" + idDivisaObject.getClass().getName() + "]");
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
                    log.error("ID [" + idObject.getClass().getName() + "] :: " +
                            "VALUE [" + valueObject.getClass().getName() + "]");
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
