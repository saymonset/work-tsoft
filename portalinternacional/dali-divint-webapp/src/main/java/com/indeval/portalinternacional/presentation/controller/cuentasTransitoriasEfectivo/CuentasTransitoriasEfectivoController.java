/**
 * Portal Internacional
 * Copyrigth (c) 2022 Indeval. All Rights Reserved.
 */
package com.indeval.portalinternacional.presentation.controller.cuentasTransitoriasEfectivo;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.cuentasTransitoriasEfectivo.CuentasTransitoriasEfectivoService;
import com.indeval.portalinternacional.middleware.servicios.dto.cuentasTransitoriasEfectivo.*;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller para el flujo de Cuentas Transitorias de Efectivo.
 *
 * @author Pablo Balderas
 * @author Jacito
 */
public class CuentasTransitoriasEfectivoController extends ControllerBase {
    /**
     * Logger
     */
    private static final Logger log = LoggerFactory.getLogger(CuentasTransitoriasEfectivoController.class);


    /**
     * Servicio para pantalla de Cuentas Transitorias Efectivo
     */
    private CuentasTransitoriasEfectivoService cuentasTransitoriasEfectivoService;

    /**
     * Lista de custodios utilizada en los filtros de consulta
     */
    private List<SelectItem> listaCustodios;

    /**
     * Lista de divisas utilizada en los filtros de consulta
     */
    private List<SelectItem> listaDivisas;

    /**
     * Custodio para la consulta
     */
    private Long idCustodioConsulta = -1L;
    private String custodioConsulta = null;

    /**
     * Divisa para la consulta
     */
    private Long idDivisaConsulta = -1L;

    private String divisaCustodia = null;

    /**
     * Fechas para la consulta
     */
    private static final SimpleDateFormat FECHAS_CONSULTAS = new SimpleDateFormat("dd/MM/yy");
    private Date fechaInicio = null;
    private String fechaInicioFormateada = null;
    private Date fechaFin = null;
    private String fechaFinFormateada = null;


    /**
     * Referencia para la consulta
     */
    private String referenciaBuscar_1 = null;
    private String referenciaBuscar_2 = null;


    /**
     * Saldos de Boveda
     */
    BovedaMontosDto bovedaSaldos;

    /**
     * Negativos Totales
     */
    List<FolioAgrupadoDto> negativosTotal;

    /**
     * Negativos Detalles
     */
    List<DetalleReferenciaDto> negativosDetalles;

    /**
     * Folio agrupado : Resumen
     */
    private List<FolioAgrupadoDto> registroReferenciado;

    private List<FoliosAgrupadosDto> foliosAgrupadosDtos;

    /**
     * Registros no referenciados
     */
    private List<CuentaTransitoriaEfectivoDto> registrosNoReferenciados;

    /**
     * Lista con el detalle de registros referenciados
     */
    private List<DetalleReferenciaDto> detallesRegistroReferenciados;

    /**
     * Total detalles de registros referenciados
     */
    private BigDecimal totalDetallesRegistroReferenciados;

    /**
     * Cadena para visualizar un mensaje ISO
     */
    private String mensajeIso;

    /**
     * Indica que la consulta ya se ejecuto
     */
    private boolean consultaEjecutada = false;

    /**
     * Indica si existe contenido para exportar
     */
    private boolean exportarContenido = false;


    /**
     * Total de efectivo de la consulta
     */
    private String totalEfectivo;

    /**
     * Referencia para asignar a registros "sueltos"
     */
    private String referenciaAsignar = null;

    /**
     * Registros "sueltos" a asignar Referencia
     */
    private Map<Long, Boolean> selectedIds = new HashMap<Long, Boolean>();


    /**
     * Inicializa la pantalla
     *
     * @return
     */
    public String getInit() {
        log.info("Iniciando ... ");
        log.info("Inicializa la lista de custodios");
        if (null == listaCustodios) {
            listaCustodios = cuentasTransitoriasEfectivoService.obtenerCustodios();
        }
        log.info("Inicializa la lista de divisas");
        if (null == listaDivisas) {
            listaDivisas = cuentasTransitoriasEfectivoService.obtenerDivisasExtranjeras();
        }
        log.info("Inicializa los parametros de busqueda");
        limpiar();
        return null;
    }

    /**
     * Inicializa la pantalla de detalle
     *
     * @return
     */
    public String getInitDetalle() {
        log.info("Inicio de Detalle");

        consultaEjecutada = false;
        detallesRegistroReferenciados = null;

        String referencia = FacesContext.getCurrentInstance().
                getExternalContext().getRequestParameterMap().get("referencia");
        String idDivisa = FacesContext.getCurrentInstance().
                getExternalContext().getRequestParameterMap().get("idDivisa");
        String idCustodio = FacesContext.getCurrentInstance().
                getExternalContext().getRequestParameterMap().get("idCustodio");

        log.debug("referencia [" + referencia + "] :: idDivisa [" + idDivisa + "] :: idCustodio [" + idCustodio + "]");

        detallesRegistroReferenciados = cuentasTransitoriasEfectivoService.
                obtenerDetallesReferencia(idDivisa, idCustodio, referencia);


        BigDecimal total = cuentasTransitoriasEfectivoService.
                obtenerDetallesReferenciaTotal(idDivisa, idCustodio, referencia);

        totalDetallesRegistroReferenciados = (total == null ? new BigDecimal("0.0") : total);

        log.debug("Detalle de referencias encontradas :: " + detallesRegistroReferenciados.size());
        log.debug("TOTAL : " + totalDetallesRegistroReferenciados);

        consultaEjecutada = true;
        return null;
    }

    /**
     * Inicializa la pantalla de detalle Negativos
     *
     * @return
     */
    public String getInitDetalleNegativos() {
        log.info("Inicio de Detalle");

        consultaEjecutada = false;

        String idDivisa = FacesContext.getCurrentInstance().
                getExternalContext().getRequestParameterMap().get("idDivisa");
        String idCustodio = FacesContext.getCurrentInstance().
                getExternalContext().getRequestParameterMap().get("idCustodio");

        idDivisaConsulta = Long.parseLong(idDivisa);
        idCustodioConsulta = Long.parseLong(idCustodio);

        cargaNegativos();

        for (DetalleReferenciaDto detalle : negativosDetalles) {
            log.debug(detalle.toString());
        }
        log.debug("TOTAL : ");
        log.debug(negativosTotal.get(0).toString());

        consultaEjecutada = true;
        return null;
    }


    /**
     * Inicializa la pantalla para mostrar el ISO
     *
     * @return
     */
    public String getInitDetalleIso() {
        log.info("inicio de detalle ISO");
        consultaEjecutada = false;
        mensajeIso = null;
        String idRegistroStr = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idRegistro");
        log.debug("Consulta de XML :: Mensaje ISO :: idRegistro [" + idRegistroStr + "]");
        mensajeIso = cuentasTransitoriasEfectivoService.obtenerMensajeISO(idRegistroStr);
        log.debug(mensajeIso);
        consultaEjecutada = true;
        return null;
    }

    /**
     * Metodo para limpiar la consulta
     *
     * @param e objeto con la accion de faces
     */
    public void limpiar(ActionEvent e) {
        limpiar();
    }

    private void limpiar() {
        consultaEjecutada = false;
        idCustodioConsulta = -1L;
        idDivisaConsulta = -1L;
        registroReferenciado = null;
        registrosNoReferenciados = null;
        referenciaBuscar_1 = null;
        referenciaBuscar_2 = null;
        fechaInicio = null;
        fechaInicioFormateada = null;
        fechaFin = null;
        fechaFinFormateada = null;
        negativosTotal = null;
    }


    /**
     * Accion para realizar la consulta
     */
    public void consultarAction() {
        ejecutarConsulta();
    }


    /**
     * Accion para asignar registros a una referencia
     */
    public String asignarAction() {
        log.debug("Asignar Accion :: " + referenciaAsignar);
        consultaEjecutada = false;
        boolean requiereActualizacion = true;
        List<String> idRegistrosError = new ArrayList<>();
        List<String> idRegistros = new ArrayList<>();

        DetalleReferenciaDto registroReferencia = cuentasTransitoriasEfectivoService.
                obtenerRegistroPorReferencia(referenciaAsignar);
        if (registroReferencia != null) {
            log.debug("Folio existente [" + referenciaAsignar + "] :: Proceder con la asignacion");
            for (CuentaTransitoriaEfectivoDto noReferenciado : registrosNoReferenciados) {
                if (noReferenciado.isRelacionar()) {
                    log.debug("Validar Referencia VS. NoReferenciado ");
                    log.debug("Referencia [" +
                            "Divisa: " + registroReferencia.getIdDivisa() + " | " +
                            "Custodio: " + registroReferencia.getIdCustodio() + "]");
                    log.debug("NoReferenciado [" +
                            "Divisa: " + noReferenciado.getIdDivisa() + " | " +
                            "Custodio: " + noReferenciado.getIdCustodio() + "]");

                    if (registroReferencia.getIdDivisa() != null
                            && noReferenciado.getIdDivisa() != null
                            && registroReferencia.getIdCustodio() != null
                            && noReferenciado.getIdCustodio() != null) {
                        if (registroReferencia.getIdDivisa().equals(noReferenciado.getIdDivisa())
                                && registroReferencia.getIdCustodio().equals(noReferenciado.getIdCustodio())) {
                            //CUSTODIO Y DIVISA de la referencia son  IGUALES al de registro no referenciado
                            log.debug("Realizar asignacion " +
                                    "[Referencia: " + registroReferencia.getFolioRelacionado() + "] - " +
                                    "[NoReferenciado :" + noReferenciado.getIdRegistro()
                                    + " | " + noReferenciado.getReferenciaOperacion() + "]");
                            cuentasTransitoriasEfectivoService.
                                    asignarFolioRelacionado(referenciaAsignar, noReferenciado.getIdRegistro());
                            idRegistros.add(noReferenciado.getReferenciaOperacion());
                        } else {  //CUSTODIO Y/O DIVISA de la referencia son  DIFERENTES al de registro no referenciado
                            idRegistrosError.add(noReferenciado.getReferenciaOperacion());
                        }
                    } else {
                        idRegistrosError.add(noReferenciado.getReferenciaOperacion());
                    }

                }

                log.debug("Registros actualizados :: " + idRegistros);
                log.debug("Registros Error :: " + idRegistrosError);
            }
        } else {
            log.debug("Folio NO existente [" + referenciaAsignar + "] :: Mostrar mensaje de Error");
            agregarMensaje(new BusinessException("Folio NO existente  [" + referenciaAsignar + "]"));
            requiereActualizacion = false;
        }


        if (requiereActualizacion) {
            ejecutarConsulta();
            if (!idRegistros.isEmpty()) {
                agregarInfoMensaje("Se actualizan : [" + referenciaAsignar + "] - " + idRegistros);
            } else if (!idRegistrosError.isEmpty()) {
                agregarMensaje(new BusinessException("Problemas con la compatibilidad de Custodio y/o Divisa: " + idRegistrosError));
            }
        }

        consultaEjecutada = true;
        return null;
    }


    /*
     * (non-Javadoc)
     * @see com.indeval.portalinternacional.presentation.controller.common.ControllerBase#ejecutarConsulta()
     */
    @Override
    public String ejecutarConsulta() {
        log.info("Ejecutar Consulta");

        consultaEjecutada = false;
        exportarContenido = false;

        totalEfectivo = null;

        if (validarDivisaCustodioConsulta()) {
            validarBusquedaFolio();

            log.debug("Obtener consulta de FOLIOS RELACIONADOS AGRUPADOS :: " +
                    "Custodio Seleccionado [" + custodioConsulta + "] - " +
                    "Divisa Seleccionada [" + divisaCustodia + "] - " +
                    "Fecha Inicio [" + fechaInicio + "] - " +
                    "Fecha Fin [" + fechaFin + "] - " +
                    "Referencia Buscar [" + referenciaBuscar_1 + "] ");


            boolean fechasValidas = validarFechas();


            if (fechasValidas) {
                bovedaSaldos = cuentasTransitoriasEfectivoService.obetenerTotalBoveda(
                        idDivisaConsulta.toString(), idCustodioConsulta.toString());

                registroReferenciado = cuentasTransitoriasEfectivoService.obtenerFoliosAgrupados(
                        idDivisaConsulta.toString(), idCustodioConsulta.toString(),
                        fechaInicioFormateada, fechaFinFormateada, referenciaBuscar_1);

                cargaNegativos();

                ordenarFolioAgrupados();

                registrosNoReferenciados = cuentasTransitoriasEfectivoService.obtenerSinReferencias(
                        idDivisaConsulta.toString(), idCustodioConsulta.toString(),
                        fechaInicioFormateada, fechaFinFormateada, null);


                exportarContenido = ((registroReferenciado != null && !registroReferenciado.isEmpty())
                        || (registrosNoReferenciados != null && !registrosNoReferenciados.isEmpty()));
                consultaEjecutada = true;
            }
        }
        return null;
    }

    /**
     * Carga la información de la seccion de Negativos: Total y Detalles
     */
    private void cargaNegativos() {
        negativosTotal = cuentasTransitoriasEfectivoService.obtenerNegativosTotal(
                idDivisaConsulta.toString(), idCustodioConsulta.toString());

        if (negativosTotal != null && !negativosTotal.isEmpty()) {
            negativosDetalles = cuentasTransitoriasEfectivoService.obtenerNegativosDetalles(
                    idDivisaConsulta.toString(), idCustodioConsulta.toString());
        }
    }

    /**
     * Valida que se seleccione Divisa y Custodio
     */
    private boolean validarDivisaCustodioConsulta() {
        if (idCustodioConsulta.intValue() == -1 || idDivisaConsulta.intValue() == -1) {
            String mensajeError = "Favor de seleccionar ";

            if (idCustodioConsulta.intValue() == -1) {
                mensajeError += "custodio";
            }

            if (idDivisaConsulta.intValue() == -1) {
                if (idCustodioConsulta.intValue() == -1) {
                    mensajeError += "y ";
                }
                mensajeError += "divisa";
            }
            agregarMensaje(new BusinessException(mensajeError));
            return false;
        } else {
            SelectItem itemCustodioSeleccionado = new SelectItem(-1L, "Todos");
            SelectItem itemDivisaSeleccionada = new SelectItem(-1L, "Todas");

            for (SelectItem item : listaCustodios) {
                if (((String) item.getValue()).equals(idCustodioConsulta.toString())) {
                    itemCustodioSeleccionado = item;
                    break;
                }
            }
            custodioConsulta = itemCustodioSeleccionado.getLabel();
            for (SelectItem item : listaDivisas) {
                if (((String) item.getValue()).equals(idDivisaConsulta.toString())) {
                    itemDivisaSeleccionada = item;
                    break;
                }
            }
            divisaCustodia = itemDivisaSeleccionada.getLabel();
            return true;
        }
    }

    /**
     * Valida que el folio para la búsqueda sea válido
     */
    private void validarBusquedaFolio() {
        if (referenciaBuscar_1 != null) {
            referenciaBuscar_1 = referenciaBuscar_1.replaceAll("\\s", "").replaceAll("[\\p{Cntrl}\\p{Z}]+", "");

            if (referenciaBuscar_1.isEmpty() || referenciaBuscar_1.length() < 1) {
                referenciaBuscar_1 = null;
            }
        }

        if (referenciaBuscar_1 == null) {
            referenciaBuscar_2 = null;
        } else {
            referenciaBuscar_2 = referenciaBuscar_1;
        }

    }

    /**
     * Valida que las fechas sean válidad
     */
    private boolean validarFechas() {

        if (fechaInicio != null && fechaFin != null) {
            int comparacionFechas = fechaInicio.compareTo(fechaFin);
            if (comparacionFechas == 0) {
                fechaInicioFormateada = FECHAS_CONSULTAS.format(fechaInicio);
            } else if (comparacionFechas < 0) {
                fechaInicioFormateada = FECHAS_CONSULTAS.format(fechaInicio);
                fechaFinFormateada = FECHAS_CONSULTAS.format(fechaFin);
            } else if (comparacionFechas > 0) {
                agregarMensaje(new BusinessException("La Fecha de Inicio [" + fechaInicio + "], " +
                        "no puede ser mayor a la Fecha Fin [" + fechaFin + "]. Actualize los filtros"));
                return false;
            }
        } else if (fechaInicio != null || fechaFin != null) {
            if (fechaInicio == null) {
                fechaInicio = fechaFin;
                agregarMensajeWarn("Fecha Inicio nula, pero se encontro Fecha Fin; " +
                        "la colsulta se realizara para la fecha [" + fechaInicio + "]");
            } else if (fechaFin == null) {
                fechaFin = fechaInicio;
                agregarMensajeWarn("Fecha Fin nula, pero se encontro Fecha Inicio; " +
                        "la consulta se realizara para la fecha [" + fechaInicio + "]");
            }
            fechaInicioFormateada = FECHAS_CONSULTAS.format(fechaInicio);
            fechaFinFormateada = fechaInicioFormateada;
        }
        return true;
    }

    private void ordenarFolioAgrupados() {

        HashMap<String, List<FolioAgrupadoDto>> foliosAgrupados = new HashMap<>();

        for (FolioAgrupadoDto folioAgrupadoDto : registroReferenciado) {
            if (!foliosAgrupados.containsKey(folioAgrupadoDto.getDivisa())) {
                foliosAgrupados.put(folioAgrupadoDto.getDivisa(), new ArrayList<FolioAgrupadoDto>());
            }
            foliosAgrupados.get(folioAgrupadoDto.getDivisa()).add(folioAgrupadoDto);
        }

        foliosAgrupadosDtos = new ArrayList<>();

        for (Map.Entry<String, List<FolioAgrupadoDto>> entry : foliosAgrupados.entrySet()) {
            BigDecimal monto = new BigDecimal(0);
            for (FolioAgrupadoDto folios : entry.getValue()) {
                monto = monto.add(folios.getTotal());
                log.debug("Suma montos :: " + monto);
            }
            FoliosAgrupadosDto aux = new FoliosAgrupadosDto();
            aux.setDivisa(entry.getValue().get(0).getDivisaExtendida());
            aux.setReferencias(entry.getValue());
            aux.setMonto(monto);
            aux.setMontoNegativo(monto.intValue() < 0);
            foliosAgrupadosDtos.add(aux);
            log.debug(aux.toString());
        }

        for (List<FolioAgrupadoDto> lista : foliosAgrupados.values()) {
            Collections.sort(lista, new Comparator<FolioAgrupadoDto>() {
                @Override
                public int compare(FolioAgrupadoDto dto1, FolioAgrupadoDto dto2) {
                    return Boolean.compare(dto2.isMontoNegativo(), dto1.isMontoNegativo());
                }
            });
        }


        if (registroReferenciado != null && !registroReferenciado.isEmpty()) {
            totalEfectivo = "";
        }
    }

    /**
     * Genera los reportes.
     *
     * @param e Evento de faces
     */
    public void generarReportes(ActionEvent e) {
        log.info("Generar Reportes");
        reiniciarEstadoPeticion();
        ejecutarConsulta();
    }


    // <editor-fold defaultstate="collapsed" desc="Seccion getters and setters.">

    /**
     * Metodo para obtener el atributo listaCustodios
     *
     * @return El atributo listaCustodios
     */
    public List<SelectItem> getListaCustodios() {
        return listaCustodios;
    }

    /**
     * Metodo para establecer el atributo listaCustodios
     *
     * @param listaCustodios El valor del atributo listaCustodios a establecer.
     */
    public void setListaCustodios(List<SelectItem> listaCustodios) {
        this.listaCustodios = listaCustodios;
    }

    /**
     * Metodo para obtener el atributo listaDivisas
     *
     * @return El atributo listaDivisas
     */
    public List<SelectItem> getListaDivisas() {
        return listaDivisas;
    }

    /**
     * Metodo para establecer el atributo listaDivisas
     *
     * @param listaDivisas El valor del atributo listaDivisas a establecer.
     */
    public void setListaDivisas(List<SelectItem> listaDivisas) {
        this.listaDivisas = listaDivisas;
    }

    /**
     * Metodo para obtener el atributo bovedaSaldos
     *
     * @return El atributo bovedaSaldos
     */
    public BovedaMontosDto getBovedaSaldos() {
        return bovedaSaldos;
    }

    /**
     * Metodo para establecer el atributo bovedaSaldos
     *
     * @param bovedaSaldos El valor del atributo bovedaSaldos a establecer.
     */
    public void setBovedaSaldos(BovedaMontosDto bovedaSaldos) {
        this.bovedaSaldos = bovedaSaldos;
    }

    /**
     * Metodo para obtener el atributo negativosTotal
     *
     * @return El atributo negativosTotal
     */
    public List<FolioAgrupadoDto> getNegativosTotal() {
        return negativosTotal;
    }

    /**
     * Metodo para establecer el atributo negativosTotal
     *
     * @param negativosTotal El valor del atributo negativosTotal a establecer.
     */
    public void setNegativosTotal(List<FolioAgrupadoDto> negativosTotal) {
        this.negativosTotal = negativosTotal;
    }

    /**
     * Metodo para obtener el atributo negativosDetalles
     *
     * @return El atributo negativosDetalles
     */
    public List<DetalleReferenciaDto> getNegativosDetalles() {
        return negativosDetalles;
    }

    /**
     * Metodo para establecer el atributo negativosDetalles
     *
     * @param negativosDetalles El valor del atributo negativosDetalles a establecer.
     */
    public void setNegativosDetalles(List<DetalleReferenciaDto> negativosDetalles) {
        this.negativosDetalles = negativosDetalles;
    }

    /**
     * Metodo para obtener el atributo registroReferenciado
     *
     * @return El atributo registroReferenciado
     */
    public List<FolioAgrupadoDto> getRegistroReferenciado() {
        return registroReferenciado;
    }

    /**
     * Metodo para establecer el atributo registroReferenciado
     *
     * @param registroReferenciado El valor del atributo registroReferenciado a establecer.
     */
    public void setRegistroReferenciado(List<FolioAgrupadoDto> registroReferenciado) {
        this.registroReferenciado = registroReferenciado;
    }

    /**
     * Metodo para obtener el atributo registrosNoReferenciados
     *
     * @return El atributo registrosNoReferenciados
     */
    public List<CuentaTransitoriaEfectivoDto> getRegistrosNoReferenciados() {
        return registrosNoReferenciados;
    }

    /**
     * Metodo para establecer el atributo registrosNoReferenciados
     *
     * @param registrosNoReferenciados El valor del atributo registrosNoReferenciados a establecer.
     */
    public void setRegistrosNoReferenciados(List<CuentaTransitoriaEfectivoDto> registrosNoReferenciados) {
        this.registrosNoReferenciados = registrosNoReferenciados;
    }

    /**
     * Metodo para obtener el atributo consultaEjecutada
     *
     * @return El atributo consultaEjecutada
     */
    public boolean isConsultaEjecutada() {
        return consultaEjecutada;
    }

    /**
     * Metodo para establecer el atributo consultaEjecutada
     *
     * @param consultaEjecutada El valor del atributo consultaEjecutada a establecer.
     */
    public void setConsultaEjecutada(boolean consultaEjecutada) {
        this.consultaEjecutada = consultaEjecutada;
    }

    /**
     * Metodo para obtener el atributo exportarContenido
     *
     * @return El atributo exportarContenido
     */
    public boolean isExportarContenido() {
        return exportarContenido;
    }

    /**
     * Metodo para establecer el atributo exportarContenido
     *
     * @param exportarContenido El valor del atributo exportarContenido a establecer.
     */
    public void setExportarContenido(boolean exportarContenido) {
        this.exportarContenido = exportarContenido;
    }

    /**
     * Metodo para obtener el atributo idCustodioConsulta
     *
     * @return El atributo idCustodioConsulta
     */
    public Long getIdCustodioConsulta() {
        return idCustodioConsulta;
    }

    /**
     * Metodo para establecer el atributo idCustodioConsulta
     *
     * @param idCustodioConsulta El valor del atributo idCustodioConsulta a establecer.
     */
    public void setIdCustodioConsulta(Long idCustodioConsulta) {
        this.idCustodioConsulta = idCustodioConsulta;
    }

    /**
     * Metodo para obtener el atributo idDivisaConsulta
     *
     * @return El atributo idDivisaConsulta
     */
    public Long getIdDivisaConsulta() {
        return idDivisaConsulta;
    }

    /**
     * Metodo para establecer el atributo idDivisaConsulta
     *
     * @param idDivisaConsulta El valor del atributo idDivisaConsulta a establecer.
     */
    public void setIdDivisaConsulta(Long idDivisaConsulta) {
        this.idDivisaConsulta = idDivisaConsulta;
    }

    /**
     * Metodo para obtener el atributo detallesRegistroReferenciados
     *
     * @return El atributo detallesRegistroReferenciados
     */
    public List<DetalleReferenciaDto> getDetallesRegistroReferenciados() {
        return detallesRegistroReferenciados;
    }

    /**
     * Metodo para establecer el atributo detallesRegistroReferenciados
     *
     * @param detallesRegistroReferenciados El valor del atributo detallesRegistroReferenciados a establecer.
     */
    public void setDetallesRegistroReferenciados(List<DetalleReferenciaDto> detallesRegistroReferenciados) {
        this.detallesRegistroReferenciados = detallesRegistroReferenciados;
    }

    /**
     * Metodo para obtener el atributo totalDetallesRegistroReferenciados
     *
     * @return El atributo totalDetallesRegistroReferenciados
     */
    public BigDecimal getTotalDetallesRegistroReferenciados() {
        return totalDetallesRegistroReferenciados;
    }

    /**
     * Metodo para establecer el atributo totalDetallesRegistroReferenciados
     *
     * @param totalDetallesRegistroReferenciados El valor del atributo totalDetallesRegistroReferenciados a establecer.
     */
    public void setTotalDetallesRegistroReferenciados(BigDecimal totalDetallesRegistroReferenciados) {
        this.totalDetallesRegistroReferenciados = totalDetallesRegistroReferenciados;
    }

    /**
     * Metodo para obtener el atributo mensajeIso
     *
     * @return El atributo mensajeIso
     */
    public String getMensajeIso() {
        return mensajeIso;
    }

    /**
     * Metodo para establecer el atributo mensajeIso
     *
     * @param mensajeIso El valor del atributo mensajeIso a establecer.
     */
    public void setMensajeIso(String mensajeIso) {
        this.mensajeIso = mensajeIso;
    }

    /**
     * Metodo para obtener el atributo totalEfectivo
     *
     * @return El atributo totalEfectivo
     */
    public String getTotalEfectivo() {
        return totalEfectivo;
    }

    /**
     * Metodo para establecer el atributo totalEfectivo
     *
     * @param totalEfectivo El valor del atributo totalEfectivo a establecer.
     */
    public void setTotalEfectivo(String totalEfectivo) {
        this.totalEfectivo = totalEfectivo;
    }

    /**
     * Metodo para obtener el atributo custodioConsulta
     *
     * @return El atributo custodioConsulta
     */
    public String getCustodioConsulta() {
        return custodioConsulta;
    }

    /**
     * Metodo para establecer el atributo custodioConsulta
     *
     * @param custodioConsulta El valor del atributo custodioConsulta a establecer.
     */
    public void setCustodioConsulta(String custodioConsulta) {
        this.custodioConsulta = custodioConsulta;
    }

    /**
     * Metodo para obtener el atributo divisaCustodia
     *
     * @return El atributo divisaCustodia
     */
    public String getDivisaCustodia() {
        return divisaCustodia;
    }

    /**
     * Metodo para establecer el atributo divisaCustodia
     *
     * @param divisaCustodia El valor del atributo divisaCustodia a establecer.
     */
    public void setDivisaCustodia(String divisaCustodia) {
        this.divisaCustodia = divisaCustodia;
    }

    /**
     * Metodo para obtener el atributo referenciaBuscar_1
     *
     * @return El atributo referenciaBuscar_1
     */
    public String getReferenciaBuscar_1() {
        return referenciaBuscar_1;
    }

    /**
     * Metodo para establecer el atributo referenciaBuscar_1
     *
     * @param referenciaBuscar_1 El valor del atributo referenciaBuscar_1 a establecer.
     */
    public void setReferenciaBuscar_1(String referenciaBuscar_1) {
        this.referenciaBuscar_1 = referenciaBuscar_1;
    }

    /**
     * Metodo para obtener el atributo referenciaBuscar_2
     *
     * @return El atributo referenciaBuscar_2
     */
    public String getReferenciaBuscar_2() {
        return referenciaBuscar_2;
    }

    /**
     * Metodo para establecer el atributo referenciaBuscar_2
     *
     * @param referenciaBuscar_2 El valor del atributo referenciaBuscar_2 a establecer.
     */
    public void setReferenciaBuscar_2(String referenciaBuscar_2) {
        this.referenciaBuscar_2 = referenciaBuscar_2;
    }

    /**
     * Metodo para obtener el atributo fechaInicio
     *
     * @return El atributo fechaInicio
     */
    public Date getFechaInicio() {
        return fechaInicio;
    }

    /**
     * Metodo para establecer el atributo fechaInicio
     *
     * @param fechaInicio El valor del atributo fechaInicio a establecer.
     */
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * Metodo para obtener el atributo fechaInicioFormateada
     *
     * @return El atributo fechaInicioFormateada
     */
    public String getFechaInicioFormateada() {
        return fechaInicioFormateada;
    }

    /**
     * Metodo para establecer el atributo fechaInicioFormateada
     *
     * @param fechaInicioFormateada El valor del atributo fechaInicioFormateada a establecer.
     */
    public void setFechaInicioFormateada(String fechaInicioFormateada) {
        this.fechaInicioFormateada = fechaInicioFormateada;
    }

    /**
     * Metodo para obtener el atributo fechaFin
     *
     * @return El atributo fechaFin
     */
    public Date getFechaFin() {
        return fechaFin;
    }

    /**
     * Metodo para establecer el atributo fechaFin
     *
     * @param fechaFin El valor del atributo fechaFin a establecer.
     */
    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * Metodo para obtener el atributo fechaFinFormateada
     *
     * @return El atributo fechaFinFormateada
     */
    public String getFechaFinFormateada() {
        return fechaFinFormateada;
    }

    /**
     * Metodo para establecer el atributo fechaFinFormateada
     *
     * @param fechaFinFormateada El valor del atributo fechaFinFormateada a establecer.
     */
    public void setFechaFinFormateada(String fechaFinFormateada) {
        this.fechaFinFormateada = fechaFinFormateada;
    }


    /**
     * Metodo para obtener el atributo referenciaAsignar
     *
     * @return El atributo referenciaAsignar
     */
    public String getReferenciaAsignar() {
        return referenciaAsignar;
    }

    /**
     * Metodo para establecer el atributo referenciaAsignar
     *
     * @param referenciaAsignar El valor del atributo referenciaBuscar a establecer.
     */
    public void setReferenciaAsignar(String referenciaAsignar) {
        this.referenciaAsignar = referenciaAsignar;
    }


    /**
     * Metodo para obtener el atributo selectedIds
     *
     * @return El atributo selectedIds
     */
    public Map<Long, Boolean> getSelectedIds() {
        return selectedIds;
    }

    /**
     * Metodo para establecer el atributo selectedIds
     *
     * @param selectedIds El valor del atributo selectedIds a establecer.
     */
    public void setSelectedIds(Map<Long, Boolean> selectedIds) {
        this.selectedIds = selectedIds;
    }


    public List<FoliosAgrupadosDto> getFoliosAgrupadosDtos() {
        return foliosAgrupadosDtos;
    }

    public void setFoliosAgrupadosDtos(List<FoliosAgrupadosDto> foliosAgrupadosDtos) {
        this.foliosAgrupadosDtos = foliosAgrupadosDtos;
    }

    public void setCuentasTransitoriasEfectivoService(CuentasTransitoriasEfectivoService cuentasTransitoriasEfectivoService) {
        this.cuentasTransitoriasEfectivoService = cuentasTransitoriasEfectivoService;
    }
    // </editor-fold>
}
