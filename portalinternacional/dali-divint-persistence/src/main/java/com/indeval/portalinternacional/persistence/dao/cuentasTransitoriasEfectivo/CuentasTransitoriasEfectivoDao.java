package com.indeval.portalinternacional.persistence.dao.cuentasTransitoriasEfectivo;

import com.bursatec.persistence.dao.BaseDao;
import com.indeval.portalinternacional.middleware.servicios.dto.cuentasTransitoriasEfectivo.BovedaMontosDto;
import com.indeval.portalinternacional.middleware.servicios.dto.cuentasTransitoriasEfectivo.CuentaTransitoriaEfectivoDto;
import com.indeval.portalinternacional.middleware.servicios.dto.cuentasTransitoriasEfectivo.DetalleReferenciaDto;
import com.indeval.portalinternacional.middleware.servicios.dto.cuentasTransitoriasEfectivo.FolioAgrupadoDto;

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
     * @return List<DetalleReferenciaDto>
     */
    List<DetalleReferenciaDto> obtenerNegativosDetalles(String idDivisa, String idCustodio);


    /**
     * Consulta referencias con montos negativos
     *
     * @param idDivisa
     * @param idCustodio
     * @return FolioAgrupadoDto
     */
    FolioAgrupadoDto obetenerNegativosTotal(String idDivisa, String idCustodio);


    /**
     * Consulta de informaci&oacute;n Resumen: FOLIO_RELACIONADO agrupado
     *
     * @param idDivisa
     * @param idCustodio
     * @param fechaInicio
     * @param fechaFin
     * @param folioRelacionado
     * @return List<FolioAgrupadoDto>
     */
    List<FolioAgrupadoDto> obtenerInformacionFoliosAgrupados(
            String idDivisa, String idCustodio, String fechaInicio, String fechaFin, String folioRelacionado);

    /**
     * Consulta de informaci&oacute;n Referencias
     *
     * @param idDivisa
     * @param idCustodio
     * @param fechaInicio
     * @param fechaFin
     * @param folioRelacionado
     * @return List<CuentaTransitoriaEfectivoDto>
     */
    List<CuentaTransitoriaEfectivoDto> obtenerInformacionSinReferencias(
            String idDivisa, String idCustodio, String fechaInicio, String fechaFin, String folioRelacionado);

    /**
     * Consulta de Detalle Referencias
     *
     * @param idDivisa
     * @param idCustodio
     * @param referenciaFolio
     * @return List<DetalleReferenciaDto>
     */
    List<DetalleReferenciaDto> obtenerDetalleReferencias(String idDivisa, String idCustodio, String referenciaFolio);

    /**
     * Consulta de Detalle Total de Referencias
     *
     * @param idDivisa
     * @param idCustodio
     * @param referenciaFolio
     * @return BigDecimal
     */
    BigDecimal obtenerDetalleReferenciasTotal(String idDivisa, String idCustodio, String referenciaFolio);

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
     * Asignar FOLIO_RELACIONADO a registro no referenciado
     *
     * @param folioRelacionado
     * @param idRegistro
     * @return Boolean
     */
    Boolean asignarFolioRelacionado(String folioRelacionado, String idRegistro);

    /**
     * Consulta de informaci&oacute;n Referencias
     *
     * @param idDivisa
     * @param idCustodio
     * @return BovedaMontosDto
     */
    BovedaMontosDto obetenerTotalBoveda(String idDivisa, String idCustodio);
}
