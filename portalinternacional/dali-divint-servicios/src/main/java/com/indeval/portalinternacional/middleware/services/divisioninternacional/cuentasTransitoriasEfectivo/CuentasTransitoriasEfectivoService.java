package com.indeval.portalinternacional.middleware.services.divisioninternacional.cuentasTransitoriasEfectivo;

import com.indeval.portalinternacional.middleware.servicios.dto.cuentasTransitoriasEfectivo.BovedaMontosDto;
import com.indeval.portalinternacional.middleware.servicios.dto.cuentasTransitoriasEfectivo.CuentaTransitoriaEfectivoDto;
import com.indeval.portalinternacional.middleware.servicios.dto.cuentasTransitoriasEfectivo.DetalleReferenciaDto;
import com.indeval.portalinternacional.middleware.servicios.dto.cuentasTransitoriasEfectivo.FolioAgrupadoDto;

import javax.faces.model.SelectItem;
import java.math.BigDecimal;
import java.util.List;

/**
 * Interfaz de servicio para las operaciones relacionadas con consulta de Cuentas de Tesoreria de Efectivo
 *
 * @author Jacito
 */
public interface CuentasTransitoriasEfectivoService {
    /**
     * Realiza la consulta de Divisas Extranjeras para lista desplegable de pantalla
     *
     * @return List<SelectItem>
     */
    List<SelectItem> obtenerDivisasExtranjeras();

    /**
     * Realiza la consulta de Custodios para lista desplegable de pantalla
     *
     * @return List<SelectItem>
     */
    List<SelectItem> obtenerCustodios();

    /**
     * Realiza la consulta de Negativos
     *
     * @param idDivisa
     * @param idCustodio
     * @return List<DetalleReferenciaDto>
     */
    List<DetalleReferenciaDto> obtenerNegativosDetalles(String idDivisa, String idCustodio);

    /**
     * Realiza la consulta de Negativos
     *
     * @param idDivisa
     * @param idCustodio
     * @return List<FolioAgrupadoDto>
     */
    List<FolioAgrupadoDto> obtenerNegativosTotal(String idDivisa, String idCustodio);


    /**
     * Realiza la consulta de Resumen: Folio Agrupado
     *
     * @param idDivisa
     * @param idCustodio
     * @param fechaInicio
     * @param fechaFin
     * @param folioRelacionado
     * @return List <FolioAgrupadoDto>
     */
    List<FolioAgrupadoDto> obtenerFoliosAgrupados(String idDivisa, String idCustodio,
                                                  String fechaInicio, String fechaFin,
                                                  String folioRelacionado);

    /**
     * Realiza la consulta de Informaci&oacute;n de Referencias
     *
     * @param idDivisa
     * @param idCustodio
     * @return List <CuentaTransitoriaEfectivoDto>
     */
    List<CuentaTransitoriaEfectivoDto> obtenerSinReferencias(String idDivisa, String idCustodio,
                                                             String fechaInicio, String fechaFin,
                                                             String folioRelacionado);

    /**
     * Realiza la consulta de Detalles de Referencias
     *
     * @param idDivisa
     * @param idCustodio
     * @param folioRelacionado
     * @return List <CuentaTransitoriaEfectivoDto>
     */
    List<DetalleReferenciaDto> obtenerDetallesReferencia(String idDivisa, String idCustodio, String folioRelacionado);

    /**
     * Realiza la consulta del Total de Detalles de Referencias
     *
     * @param idDivisa
     * @param idCustodio
     * @param folioRelacionado
     * @return BigDecimal
     */
    BigDecimal obtenerDetallesReferenciaTotal(String idDivisa, String idCustodio, String folioRelacionado);

    /**
     * Realiza la consulta de mensaje ISO
     *
     * @param idRegistro
     * @return String
     */
    String obtenerMensajeISO(String idRegistro);

    /**
     * Verificar existencia de FOLIO_RELACIONADO  introducido para asignar
     *
     * @param folioRelacionado
     * @return Boolean
     */
    Boolean validarFolioRelacionado(String folioRelacionado, String idRegistro);

    /**
     * Asignar FOLIO_RELACIONADO a registro no referenciado
     *
     * @param folioRelacionado
     * @param idRegistro
     * @return Boolean
     */
    Boolean asignarFolioRelacionado(String folioRelacionado, String idRegistro);

    /**
     * Obtener registro por FOLIO_RELACIONADO
     *
     * @param folioRelacionado
     * @return DetalleReferenciaDto
     */
    DetalleReferenciaDto obtenerRegistroPorReferencia(String folioRelacionado);

    /**
     * Obtener montos de Boveda
     *
     * @param idDivisa
     * @param idCustodio
     * @return BovedaMontosDto
     */
    BovedaMontosDto obetenerTotalBoveda(String idDivisa, String idCustodio);


}
