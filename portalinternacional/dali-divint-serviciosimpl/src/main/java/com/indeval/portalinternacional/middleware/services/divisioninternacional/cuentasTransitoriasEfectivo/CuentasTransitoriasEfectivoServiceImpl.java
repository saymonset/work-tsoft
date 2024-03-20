package com.indeval.portalinternacional.middleware.services.divisioninternacional.cuentasTransitoriasEfectivo;

import com.indeval.portalinternacional.middleware.servicios.dto.cuentasTransitoriasEfectivo.BovedaMontosDto;
import com.indeval.portalinternacional.middleware.servicios.dto.cuentasTransitoriasEfectivo.CuentaTransitoriaEfectivoDto;
import com.indeval.portalinternacional.middleware.servicios.dto.cuentasTransitoriasEfectivo.DetalleReferenciaDto;
import com.indeval.portalinternacional.middleware.servicios.dto.cuentasTransitoriasEfectivo.FolioAgrupadoDto;
import com.indeval.portalinternacional.persistence.dao.cuentasTransitoriasEfectivo.CuentasTransitoriasEfectivoDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.model.SelectItem;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementaci&oacute;n de la interfaz de servicio CuentasTransitoriasEfectivoService
 *
 * @author Jacito
 */
public class CuentasTransitoriasEfectivoServiceImpl implements CuentasTransitoriasEfectivoService {

    /**
     * Logger
     */
    private static final Logger log = LoggerFactory.getLogger(CuentasTransitoriasEfectivoServiceImpl.class);

    /**
     * DAO para realizar las consultas
     */
    private CuentasTransitoriasEfectivoDao cuentasTransitoriasEfectivoDao;

    /**
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.cuentasTransitoriasEfectivo.CuentasTransitoriasEfectivoService#obtenerDivisasExtranjeras()
     */
    @Override
    public List<SelectItem> obtenerDivisasExtranjeras() {
        log.debug("Entrando a CuentasTransitoriasEfectivoServiceImpl.obtenerDivisasExtranjeras()...");

        List<SelectItem> divisasExtranjeras = new ArrayList<>();

        List<String[]> divisasExtranjerasDao = cuentasTransitoriasEfectivoDao.obtenerDivisasExtranjeras();

        for (String[] divisaExtranjeraDao : divisasExtranjerasDao) {
            divisasExtranjeras.add(new SelectItem(divisaExtranjeraDao[0], divisaExtranjeraDao[1]));
        }
        log.debug("Divisas Encontradas :: " + divisasExtranjeras.size());
        return divisasExtranjeras;
    }

    /**
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.cuentasTransitoriasEfectivo.CuentasTransitoriasEfectivoService#obtenerCustodios()
     */
    @Override
    public List<SelectItem> obtenerCustodios() {
        log.debug("####### Entrando a CuentasTransitoriasEfectivoServiceImpl.obtenerCustodios()...");

        List<SelectItem> custodios = new ArrayList<>();

        List<String[]> custodiosDao = cuentasTransitoriasEfectivoDao.obtenerCustodios();

        for (String[] custodioDao : custodiosDao) {
            custodios.add(new SelectItem(custodioDao[0], custodioDao[1]));
        }
        log.debug("Custodios Encontrados :: " + custodios.size());
        return custodios;
    }

    /**
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.cuentasTransitoriasEfectivo.CuentasTransitoriasEfectivoService#obtenerNegativosDetalles(String, String)
     */
    @Override
    public List<DetalleReferenciaDto> obtenerNegativosDetalles(String idDivisa, String idCustodio) {
        log.debug("Obtener Resumen :: SERVICE :: Negativos Detalles :: " +
                "idCustodio [" + (idCustodio == null ? "Todos" : idCustodio) + "] - " +
                "idDivisas [" + (idDivisa == null ? "Todas" : idDivisa) + "] ");

        return cuentasTransitoriasEfectivoDao.obtenerNegativosDetalles(idDivisa, idCustodio);
    }

    /**
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.cuentasTransitoriasEfectivo.CuentasTransitoriasEfectivoService#obtenerNegativosTotal(String, String)
     */
    @Override
    public List<FolioAgrupadoDto> obtenerNegativosTotal(String idDivisa, String idCustodio) {
        log.debug("Obtener Resumen :: SERVICE :: Negativos Total :: " +
                "idCustodio [" + (idCustodio == null ? "Todos" : idCustodio) + "] - " +
                "idDivisas [" + (idDivisa == null ? "Todas" : idDivisa) + "] ");

        List<FolioAgrupadoDto> negativosTotal = new ArrayList<>();
        FolioAgrupadoDto total = cuentasTransitoriasEfectivoDao.obetenerNegativosTotal(idDivisa, idCustodio);

        if (total != null) {
            negativosTotal.add(total);
        }
        return negativosTotal;
    }

    /**
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.cuentasTransitoriasEfectivo.CuentasTransitoriasEfectivoService#obtenerFoliosAgrupados(String, String, String, String, String)
     */
    @Override
    public List<FolioAgrupadoDto> obtenerFoliosAgrupados(String idDivisa, String idCustodio,
                                                         String fechaInicio, String fechaFin,
                                                         String folioRelacionado) {
        log.debug("Obtener Resumen :: SERVICE :: Folio Agrupado :: " +
                "idCustodio [" + (idCustodio == null ? "Todos" : idCustodio) + "] - " +
                "idDivisas [" + (idDivisa == null ? "Todas" : idDivisa) + "] "
                + (fechaInicio == null ? "" : "- fechaInicio [" + fechaInicio + "] ")
                + (fechaFin == null ? "" : "- fechaFin [" + fechaFin + "] ")
                + (folioRelacionado == null ? "" : "- folioRelacionado [" + folioRelacionado + "] "));

        fechaFin = validarFechaFin(fechaInicio, fechaFin);

        List<FolioAgrupadoDto> foliosAgrupados = cuentasTransitoriasEfectivoDao.
                obtenerInformacionFoliosAgrupados(idDivisa, idCustodio, fechaInicio, fechaFin, folioRelacionado);
        log.debug("Folios Agrupados Encontrados :: " + foliosAgrupados.size());
        return foliosAgrupados;
    }

    /**
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.cuentasTransitoriasEfectivo.CuentasTransitoriasEfectivoService#obtenerSinReferencias(String, String, String, String, String)
     */
    @Override
    public List<CuentaTransitoriaEfectivoDto> obtenerSinReferencias(String idDivisa, String idCustodio,
                                                                    String fechaInicio, String fechaFin,
                                                                    String folioRelacionado) {
        log.debug("Obtener Referencias :: SERVICE :: " +
                "idCustodio [" + (idCustodio == null ? "Todos" : idCustodio) + "] - " +
                "idDivisas [" + (idDivisa == null ? "Todas" : idDivisa) + "] "
                + (fechaInicio == null ? "" : "- fechaInicio [" + fechaInicio + "] ")
                + (fechaFin == null ? "" : "- fechaFin [" + fechaFin + "] ")
                + (folioRelacionado == null ? "" : "- folioRelacionado [" + folioRelacionado + "] "));

        fechaFin = validarFechaFin(fechaInicio, fechaFin);

        return cuentasTransitoriasEfectivoDao.
                obtenerInformacionSinReferencias(idDivisa, idCustodio, fechaInicio, fechaFin, folioRelacionado);
    }

    private String validarFechaFin(String fechaInicio, String fechaFin) {
        if (fechaFin != null) {
            if (fechaFin.equals(fechaInicio)) {
                return null;
            }
        }
        return fechaFin;
    }

    /**
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.cuentasTransitoriasEfectivo.CuentasTransitoriasEfectivoService#obtenerDetallesReferencia(String, String, String)
     */
    @Override
    public List<DetalleReferenciaDto> obtenerDetallesReferencia(String idDivisa, String idCustodio, String folioRelacionado) {
        log.debug("Obtener Detalles Referencia :: SERVICE :: " +
                "idDivisas [" + (idDivisa == null ? "Todas" : idDivisa) + "] - " +
                "idCustodio [" + (idCustodio == null ? "Todos" : idCustodio) + "] - " +
                "folioRelacionado [" + folioRelacionado + "] ");
        List<DetalleReferenciaDto> referencias =
                cuentasTransitoriasEfectivoDao.obtenerDetalleReferencias(idDivisa, idCustodio, folioRelacionado);
        log.debug("Detalles referencias :: " + referencias.size());
        return referencias;
    }

    /**
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.cuentasTransitoriasEfectivo.CuentasTransitoriasEfectivoService#obtenerDetallesReferenciaTotal(String, String, String)
     */
    @Override
    public BigDecimal obtenerDetallesReferenciaTotal(String idDivisa, String idCustodio, String folioRelacionado) {
        log.debug("Obtener Total de Detalles Referencia :: SERVICE :: folioRelacionado [" + folioRelacionado + "] ");
        return cuentasTransitoriasEfectivoDao.obtenerDetalleReferenciasTotal(idDivisa, idCustodio, folioRelacionado);
    }

    /**
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.cuentasTransitoriasEfectivo.CuentasTransitoriasEfectivoService#obtenerMensajeISO(String)
     */
    @Override
    public String obtenerMensajeISO(String idRegistro) {
        return cuentasTransitoriasEfectivoDao.obtenerMensajeISO(idRegistro);
    }

    /**
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.cuentasTransitoriasEfectivo.CuentasTransitoriasEfectivoService#validarFolioRelacionado(String, String)
     */
    @Override
    public Boolean validarFolioRelacionado(String folioRelacionado, String idRegistro) {
        return cuentasTransitoriasEfectivoDao.validarFolioRelacionado(folioRelacionado, idRegistro);
    }

    /**
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.cuentasTransitoriasEfectivo.CuentasTransitoriasEfectivoService#asignarFolioRelacionado(String, String)
     */
    @Override
    public Boolean asignarFolioRelacionado(String folioRelacionado, String idRegistro) {
        return cuentasTransitoriasEfectivoDao.asignarFolioRelacionado(folioRelacionado, idRegistro);
    }

    /**
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.cuentasTransitoriasEfectivo.CuentasTransitoriasEfectivoService#obtenerRegistroPorReferencia(String)
     */
    @Override
    public DetalleReferenciaDto obtenerRegistroPorReferencia(String folioRelacionado) {
        return cargarRegistro(cuentasTransitoriasEfectivoDao.
                obtenerFolioRelacionado(folioRelacionado));
    }

    /**
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.cuentasTransitoriasEfectivo.CuentasTransitoriasEfectivoService#obetenerTotalBoveda(String, String, String)
     */
    @Override
    public BovedaMontosDto obetenerTotalBoveda(String idDivisa, String idCustodio) {
        return cuentasTransitoriasEfectivoDao.obetenerTotalBoveda(idDivisa, idCustodio);
    }

    private DetalleReferenciaDto cargarRegistro(String[] registro) {
        log.debug("Obtener registro");
        if (registro != null) {

            DetalleReferenciaDto detalle = new DetalleReferenciaDto();
            detalle.setIdRegistro(registro[0]);
            detalle.setFolioRelacionado(registro[1]);
            detalle.setIdCustodio(registro[2]);
            detalle.setIdDivisa(registro[3]);
            log.debug(detalle.toString());
            return detalle;

        }
        return null;
    }

    /**
     * @param cuentasTransitoriasEfectivoDao the cuentasTransitoriasEfectivoDao to set
     */
    public void setCuentasTransitoriasEfectivoDao(CuentasTransitoriasEfectivoDao cuentasTransitoriasEfectivoDao) {
        this.cuentasTransitoriasEfectivoDao = cuentasTransitoriasEfectivoDao;
    }
}
