package com.indeval.portalinternacional.persistence.dao.cuentasTransitoriasEfectivo;

import com.bursatec.persistence.dao.BaseDao;

import java.math.BigDecimal;
import java.util.List;

/**
 * Dao para el manejo de informaci&oacute;n de pantalla Cuentas de Tesorer&iacute;a de Efectivo
 *
 * @author Jacito
 */
public interface CuentasTransitoriasEfectivoDao extends BaseDao {

    /**
     * Consulta de Divisas Extranjeras
     *
     * @return List<String [ ]>
     */
    List<String[]> obtenerDivisasExtranjeras();

    /**
     * Consulta de Custodios
     *
     * @return List<String [ ]>
     */
    List<String[]> obtenerCustodios();

    /**
     * Consulta referencias con montos negativos
     *
     * @param idDivisa
     * @param idCustodio
     * @return List<String [ ]>
     */
    List<String[]> obtenerNegativos(String idDivisa, String idCustodio);


    /**
     * Consulta de informaci&oacute;n Resumen: FOLIO_RELACIONADO agrupado
     *
     * @param idDivisa
     * @param idCustodio
     * @param fechaInicio
     * @param fechaFin
     * @param folioRelacionado
     * @return List<String [ ]>
     */
    List<String[]> obtenerInformacionFolioAgrupado(
            String idDivisa, String idCustodio, String fechaInicio, String fechaFin, String folioRelacionado);

    /**
     * Consulta de informaci&oacute;n Referencias
     *
     * @param idDivisa
     * @param idCustodio
     * @param fechaInicio
     * @param fechaFin
     * @param folioRelacionado
     * @return List<String [ ]>
     */
    List<String[]> obtenerInformacionReferencias(
            String idDivisa, String idCustodio, String fechaInicio, String fechaFin, String folioRelacionado);

    /**
     * Consulta de Detalle Referencias
     *
     * @param referenciaFolio
     * @return List<String [ ]>
     */
    List<String[]> obtenerDetalleReferencias(String referenciaFolio);

    /**
     * Consulta de Detalle Total de Referencias
     *
     * @param referenciaFolio
     * @return String
     */
    BigDecimal obtenerDetalleReferenciasTotal(String referenciaFolio);

    /**
     * Consulta de MensajeISO
     *
     * @param idRegistro
     * @return String
     */
    String obtenerMensajeISO(String idRegistro);

    /**
     * Validar existencia de FOLIO_RELACIONADO
     *
     * @param folioRelacionado
     * @param idRegistro
     * @return Boolean
     */
    Boolean validarFolioRelacionado(String folioRelacionado, String idRegistro);

    /**
     * Obtener FOLIO_RELACIONADO
     *
     * @param folioRelacionado
     * @return String [ ]
     */
    String[] obtenerFolioRelacionado(String folioRelacionado);

    /**
     * Obtener REGISTRO
     *
     * @param idRegistro
     * @return String [ ]
     */
    String[] obtenerIdRegistro(String idRegistro);

    /**
     * Asignar FOLIO_RELACIONADO a registro no referenciado
     *
     * @param folioRelacionado
     * @param idRegistro
     * @return Boolean
     */
    Boolean asignarFolioRelacionado(String folioRelacionado, String idRegistro);
}
