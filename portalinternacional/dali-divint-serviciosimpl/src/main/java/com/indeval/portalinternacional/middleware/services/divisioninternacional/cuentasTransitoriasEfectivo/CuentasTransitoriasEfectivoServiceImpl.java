package com.indeval.portalinternacional.middleware.services.divisioninternacional.cuentasTransitoriasEfectivo;

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
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.cuentasTransitoriasEfectivo.CuentasTransitoriasEfectivoService#obtenerNegativos(String, String)
     */
    @Override
    public List<FolioAgrupadoDto> obtenerNegativos(String idDivisa, String idCustodio) {
        log.debug("Obtener Resumen :: SERVICE :: Folio Agrupado :: " +
                "idCustodio [" + (idCustodio == null ? "Todos" : idCustodio) + "] - " +
                "idDivisas [" + (idDivisa == null ? "Todas" : idDivisa) + "] ");

        List<FolioAgrupadoDto> negativosEncontrados = new ArrayList<>();
        List<String[]> negativos = cuentasTransitoriasEfectivoDao.
                obtenerNegativos(idDivisa, idCustodio);


        if (negativos != null) {
            for (String[] referencia : negativos) {
                FolioAgrupadoDto folioAgrupadoDto = new FolioAgrupadoDto();
                folioAgrupadoDto.setIdDivisa(referencia[0]);
                folioAgrupadoDto.setDivisa(referencia[1]);
                folioAgrupadoDto.setIdCustodio(referencia[2]);
                folioAgrupadoDto.setCustodio(referencia[3]);
                folioAgrupadoDto.setTotal(new BigDecimal(referencia[4]));
                log.debug(negativosEncontrados.toString());
                negativosEncontrados.add(folioAgrupadoDto);
            }
        }
        return negativosEncontrados;
    }

    /**
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.cuentasTransitoriasEfectivo.CuentasTransitoriasEfectivoService#obtenerFolioAgrupado(String, String, String, String, String)
     */
    @Override
    public List<FolioAgrupadoDto> obtenerFolioAgrupado(String idDivisa, String idCustodio,
                                                       String fechaInicio, String fechaFin,
                                                       String folioRelacionado) {
        log.debug("Obtener Resumen :: SERVICE :: Folio Agrupado :: " +
                "idCustodio [" + (idCustodio == null ? "Todos" : idCustodio) + "] - " +
                "idDivisas [" + (idDivisa == null ? "Todas" : idDivisa) + "] "
                + (fechaInicio == null ? "" : "- fechaInicio [" + fechaInicio + "] ")
                + (fechaFin == null ? "" : "- fechaFin [" + fechaFin + "] ")
                + (folioRelacionado == null ? "" : "- folioRelacionado [" + folioRelacionado + "] "));

        fechaFin = validarFechaFin(fechaInicio, fechaFin);

        List<FolioAgrupadoDto> folioAgrupado = new ArrayList<>();
        List<String[]> referencias = cuentasTransitoriasEfectivoDao.
                obtenerInformacionFolioAgrupado(idDivisa, idCustodio, fechaInicio, fechaFin, folioRelacionado);

        if (referencias != null) {
            for (String[] referencia : referencias) {
                FolioAgrupadoDto folioAgrupadoDto = new FolioAgrupadoDto();
                folioAgrupadoDto.setFolioRelacionado(referencia[0]);
                folioAgrupadoDto.setCustodio(referencia[1]);
                folioAgrupadoDto.setDivisa(referencia[2].substring(0, 3));
                folioAgrupadoDto.setDivisaExtendida(referencia[2]);
                folioAgrupadoDto.setRegistros(referencia[3]);
                folioAgrupadoDto.setTotal(new BigDecimal(referencia[4]));
                folioAgrupadoDto.setMontoNegativo(Boolean.parseBoolean(referencia[5]));
                log.debug(folioAgrupado.toString());
                folioAgrupado.add(folioAgrupadoDto);
            }
        }
        return folioAgrupado;
    }

    /**
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.cuentasTransitoriasEfectivo.CuentasTransitoriasEfectivoService#obtenerReferencias(String, String, String, String, String)
     */
    @Override
    public List<CuentaTransitoriaEfectivoDto> obtenerReferencias(String idDivisa, String idCustodio,
                                                                 String fechaInicio, String fechaFin,
                                                                 String folioRelacionado) {
        log.debug("Obtener Referencias :: SERVICE :: " +
                "idCustodio [" + (idCustodio == null ? "Todos" : idCustodio) + "] - " +
                "idDivisas [" + (idDivisa == null ? "Todas" : idDivisa) + "] "
                + (fechaInicio == null ? "" : "- fechaInicio [" + fechaInicio + "] ")
                + (fechaFin == null ? "" : "- fechaFin [" + fechaFin + "] ")
                + (folioRelacionado == null ? "" : "- folioRelacionado [" + folioRelacionado + "] "));

        fechaFin = validarFechaFin(fechaInicio, fechaFin);

        List<CuentaTransitoriaEfectivoDto> referenciados = new ArrayList<>();
        List<String[]> referencias = cuentasTransitoriasEfectivoDao.
                obtenerInformacionReferencias(idDivisa, idCustodio, fechaInicio, fechaFin, folioRelacionado);

        if (referencias != null) {
            for (String[] referencia : referencias) {
                log.debug("REFERENCIA_OPERACION [" + referencia[0] + "] :: "
                        + "TIPO_MENSAJE [" + referencia[1] + "] :: "
                        + "CLAVE_ALFABETICA [" + referencia[2] + "] :: "
                        + "NOMBRE_CORTO [" + referencia[3] + "] :: "
                        + "MONTO  [" + referencia[4] + "] :: "
                        + "ID_CUENTA_TRANSITORIA  [" + referencia[5] + "] :: "
                        + "XML_MENSAJE_ISO  [" + referencia[6] + "] ");

                CuentaTransitoriaEfectivoDto referenciada = new CuentaTransitoriaEfectivoDto();
                referenciada.setReferenciaOperacion(referencia[0]);
                referenciada.setTipoMensaje(referencia[1]);
                referenciada.setDivisa(referencia[2]);
                referenciada.setIdDivisa(referencia[3]);
                referenciada.setCustodio(referencia[4]);
                referenciada.setIdCustodio(referencia[5]);
                referenciada.setTotal(new BigDecimal(referencia[6]));
                referenciada.setIdRegistro(referencia[7]);
                referenciada.setMensajeISO(referencia[8]);
                log.debug(referenciada.toString());
                referenciados.add(referenciada);
            }
        }
        return referenciados;
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
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.cuentasTransitoriasEfectivo.CuentasTransitoriasEfectivoService#obtenerDetallesReferencia(String)
     */
    @Override
    public List<DetalleReferenciaDto> obtenerDetallesReferencia(String folioRelacionado) {
        log.debug("Obtener Detalles Referencia :: SERVICE :: folioRelacionado [" + folioRelacionado + "] ");
        List<DetalleReferenciaDto> referenciados = new ArrayList<>();
        List<String[]> referencias = cuentasTransitoriasEfectivoDao.obtenerDetalleReferencias(folioRelacionado);
        if (referencias != null) {
            for (String[] referencia : referencias) {
                DetalleReferenciaDto detalle = new DetalleReferenciaDto();
                detalle.setIdRegistro(referencia[0]);
                detalle.setFolioRelacionado(referencia[1]);
                detalle.setTipoMensaje(referencia[2]);
                detalle.setDivisa(referencia[3]);
                detalle.setCustodio(referencia[4]);
                detalle.setTotal(new BigDecimal(referencia[5]));
                detalle.setDetalleMovimientos(referencia[6]);
                detalle.setSeme(referencia[7]);
                detalle.setMensajeISO(referencia[8]);
                log.debug(detalle.toString());
                referenciados.add(detalle);
            }
        }
        log.debug("Detalles referencias :: " + referenciados.size());
        return referenciados;
    }

    /**
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.cuentasTransitoriasEfectivo.CuentasTransitoriasEfectivoService#obtenerDetallesReferenciaTotal(String)
     */
    @Override
    public BigDecimal obtenerDetallesReferenciaTotal(String folioRelacionado) {
        log.debug("Obtener Total de Detalles Referencia :: SERVICE :: folioRelacionado [" + folioRelacionado + "] ");
        return cuentasTransitoriasEfectivoDao.obtenerDetalleReferenciasTotal(folioRelacionado);
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
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.cuentasTransitoriasEfectivo.CuentasTransitoriasEfectivoService#obtenerRegistroPorIdRegistro(String)
     */
    @Override
    public DetalleReferenciaDto obtenerRegistroPorIdRegistro(String idRegistro) {
        return cargarRegistro(cuentasTransitoriasEfectivoDao.
                obtenerIdRegistro(idRegistro));
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
