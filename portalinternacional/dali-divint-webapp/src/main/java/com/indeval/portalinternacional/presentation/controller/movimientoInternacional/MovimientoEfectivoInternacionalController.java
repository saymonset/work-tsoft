/**
 * Multidivisas: Consulta de Movimiento de Efectivo Internacional
 */
package com.indeval.portalinternacional.presentation.controller.movimientoInternacional;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.dao.common.DivisaDao;
import com.indeval.portaldali.persistence.modelo.Divisa;
import com.indeval.portaldali.persistence.util.DateUtils;
import com.indeval.portalinternacional.common.util.CifradorDescifradorBlowfish;
import com.indeval.portalinternacional.middleware.services.bitacora.BitacoraSupport;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.BovedaService;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.MovimientoEfectivoInternacionalService;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.SicService;
import com.indeval.portalinternacional.middleware.services.makerchecker.SolicitudAutorizacionService;
import com.indeval.portalinternacional.middleware.services.util.JsonUtil;
import com.indeval.portalinternacional.middleware.servicios.dto.ObjetoFirmadoDTO;
import com.indeval.portalinternacional.middleware.servicios.dto.SolicitudAutorizacionDTO;
import com.indeval.portalinternacional.middleware.servicios.modelo.Bovedas;
import com.indeval.portalinternacional.middleware.servicios.modelo.CuentaTransitoria;
import com.indeval.portalinternacional.middleware.servicios.modelo.Multidivisa;
import com.indeval.portalinternacional.middleware.servicios.modelo.Multidivisa.EstadoMovimiento;
import com.indeval.portalinternacional.middleware.servicios.modelo.Multidivisa.TipoMovimiento;
import com.indeval.portalinternacional.middleware.servicios.vo.CriteriosConsultaMovEfeDivExtVO;
import com.indeval.portalinternacional.middleware.servicios.vo.MovimientoEfectivoInternacionalVO;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;
import com.indeval.sidv.bitacoraauditoria.middleware.service.BitacoraService;
import org.apache.commons.lang.SerializationUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import java.util.*;

import static com.indeval.portalinternacional.middleware.servicios.constantes.Constantes.*;
import static com.indeval.portalinternacional.middleware.servicios.constantes.Constantes.TIPO_MOVIMIENTO_EFECTIVO_CANCELA;
import static com.indeval.portalinternacional.presentation.controller.movimientoInternacional.TipoMovimientoEfectivo.*;


public class MovimientoEfectivoInternacionalController extends ControllerBase {
    private static final Logger LOG = LoggerFactory.getLogger(MovimientoEfectivoInternacionalController.class);

    private String idFolioParticipante;

    private List<SelectItem> listaDivisas;
    private List<SelectItem> listaBovedas;
    private List<SelectItem> listaTiposMovimiento;
    private List<SelectItem> listaEstatusMovimiento;

    private MovimientoEfectivoInternacionalService movimientoEfectivoInternacionalService;
    private SolicitudAutorizacionService solicitudAutorizacionService;
    private DivisaDao divisaDao;
    private BovedaService bovedaService;
    private SicService sicService;
    private BitacoraService bitacoraService;
    private int totalPaginas = 1;
    private long idDivisa;
    private long idBoveda;
    private long estatusMovimiento;

    private String descDivisa;
    private String descBoveda;
    private String descTipoMovimiento;
    private String descEstatusMovimiento;
    private String tipoMovimiento;
    private String folioControl;
    private String esTipo;
    private TipoMovimientoEfectivo tipoMovimientoEfectivo;
    private String idMovimientoStrSelected;

    private Date fechaInicio;
    private Date fechaFin;

    private Date fechaLiqInicio;
    private Date fechaLiqFin;

    private PaginaVO resultados = null;

    private int totalRegistros = 0;

    private boolean consultaEjecutada = false;
    private boolean validationErrors = false;
    private boolean checkAllLibera;
    private boolean checkAllCancela;

    private static final String ID_MOVIMIENTOS_STR = "idMovimientoStr";
    private static final String FECHA_PATTERN = "dd/MM/yyyy";

    private boolean usuarioPuedeLiberar = false;

    private boolean usuarioPuedeCancelar = false;

    private List<MovimientoEfectivoInternacionalVO> movimientos;
    private List<String> isosSinFirmar;
    private List<String> isosFirmados;
    private List<String> hashIsos;
    private int totalOperaciones = 0;

    private CifradorDescifradorBlowfish cdb = new CifradorDescifradorBlowfish();


    /**
     * Inicializa el bean
     *
     * @return String
     */
    public String getInit() {
        LOG.info("getInit");

        if (paginaVO == null) paginaVO = new PaginaVO();

        this.inicializarCriteriosConsulta();
        this.inicializarChksAcciones();
        this.inicializaBovedas();
        this.inicializaDivisas();
        this.inicializaTiposMovimiento();
        this.inicializaEstatusMovimiento();
        this.inicializaIso();
        this.validarPermisos();

        AgenteVO inst = getAgenteFirmado();
        this.idFolioParticipante = inst.getId() + inst.getFolio();

        paginaVO.setRegistrosXPag(50);
        paginaVO.setOffset(0);

        return null;
    }

    /**
     * Buscar las emisiones
     *
     * @param evt Evento lanzado por botón buscar
     */
    public void buscarEmisiones(ActionEvent evt) {
        LOG.info("Buscar Emisiones");
        LOG.debug(evt.toString());
        paginaVO.setRegistrosXPag(50);
        paginaVO.setOffset(0);
        getPaginaVO().setRegistros(null);
        ejecutarConsulta();
    }

    /**
     * Ejecuta la consulta y calcula el numero de pagina para la paginacion.
     * Este metodo es un overide de la clase padre
     */
    @Override
    public String ejecutarConsulta() {
        LOG.info("ejecutarConsulta");

        this.inicializaIso();
        this.consultaEjecutada = false;
        this.validationErrors = false;

        LOG.debug(this.getCriterios().toString());
        LOG.debug(this.paginaVO.toString());

        paginaVO = this.movimientoEfectivoInternacionalService.getMovimientosEfectivoInternacional(this.getCriterios(), this.paginaVO);

        totalPaginas = paginaVO.getTotalRegistros() / paginaVO.getRegistrosXPag();

        if (paginaVO.getTotalRegistros() % paginaVO.getRegistrosXPag() > 0) totalPaginas++;

        totalPaginas = (totalPaginas <= 0) ? 1 : totalPaginas;

        LOG.debug("Total de Páginas : " + totalPaginas);
        LOG.debug("Total de Registros: " + paginaVO.getRegistros().size());

        this.consultaEjecutada = true;

        return null;
    }

    /**
     * Limpia todos los campos
     *
     * @param evt Evento lanzado por el botón limpiar criterios
     */
    public void limpiar(ActionEvent evt) {
        LOG.debug(evt.toString());
        this.inicializarCriteriosConsulta();
        this.inicializarChksAcciones();
        if (resultados != null) resultados.getRegistros().clear();
        if (paginaVO.getRegistros() != null) paginaVO.getRegistros().clear();
        paginaVO.setRegistrosXPag(50);
        paginaVO.setOffset(0);
        setConsultaEjecutada(false);
        inicializaIso();
        this.validationErrors = false;
        this.consultaEjecutada = false;
    }


    /**
     * Ejecuta la operación de liberar movimientos seleccionados desde front
     */
    public String liberaSeleccion(ActionEvent evt) {
        LOG.info("liberaSeleccion");
        ejecutarOperacion();
        return null;
    }

    /**
     * Ejecuta la operación de cancelar movimientos seleccionados desde front
     */
    public String cancelaSeleccion(ActionEvent evt) {
        LOG.info("cancelaSeleccion");
        ejecutarOperacion();
        return null;
    }

    /**
     * Ejecuta la operación de liberar movimiento desde front
     */
    public String libera(ActionEvent evt) {
        LOG.info("libera");
        ejecutarOperacion();
        return null;
    }

    /**
     * Ejecuta la operación de cancelar movimiento desde front
     */
    public String cancela(ActionEvent evt) {
        LOG.info("Cancela :: ");
        ejecutarOperacion();
        return null;
    }

    /**
     * Inicializa Filtros de Consulta Front
     */
    private void inicializarCriteriosConsulta() {
        this.idDivisa = -1L;
        this.idBoveda = -1L;
        this.estatusMovimiento = -1;

        this.tipoMovimiento = OPCION_TODOS_CRITERIO;

        this.descDivisa = StringUtils.EMPTY;
        this.descBoveda = StringUtils.EMPTY;
        this.descTipoMovimiento = StringUtils.EMPTY;
        this.descEstatusMovimiento = StringUtils.EMPTY;
        this.idFolioParticipante = StringUtils.EMPTY;
        this.folioControl = StringUtils.EMPTY;

        Date fechaActual = new Date();
        this.fechaInicio = fechaActual;
        this.fechaFin = fechaActual;
        this.fechaLiqInicio = fechaActual;
        this.fechaLiqFin = fechaActual;
    }

    /**
     * Inicializa lista acciones
     */
    private void inicializarChksAcciones() {
        this.checkAllLibera = false;
        this.checkAllCancela = false;
        this.validationErrors = false;
    }

    /**
     * Inicializa lista de bovedas
     */
    private void inicializaBovedas() {
        List<Bovedas> lista = this.bovedaService.findAllBovedasEfectivo();
        listaBovedas = new ArrayList<>();

        for (Bovedas bovedaActual : lista) {
            listaBovedas.add(new SelectItem(bovedaActual.getIdBoveda().longValue(), bovedaActual.getNombreCorto()));
        }
    }

    /**
     * Inicializa lista de divisas
     */
    private void inicializaDivisas() {
        Divisa[] divisas = divisaDao.findDivisas();
        listaDivisas = new ArrayList<>();

        for (Divisa divisa : divisas) {
            listaDivisas.add(new SelectItem(divisa.getIdDivisa(), divisa.getDescripcion()));
        }
    }

    /**
     * Inicializa lista de tipos de movimiento
     */
    private void inicializaTiposMovimiento() {
        listaTiposMovimiento = new ArrayList<>();
        listaTiposMovimiento.add(new SelectItem(PREFIJO_ID_DEPOSITO, DESC_DEPOSITO));
        listaTiposMovimiento.add(new SelectItem(PREFIJO_ID_RETIRO, DESC_RETIRO));
    }

    /**
     * Inicializa lista de estatus de movimiento
     */
    private void inicializaEstatusMovimiento() {
        listaEstatusMovimiento = new ArrayList<>();
        listaEstatusMovimiento.add(new SelectItem(ID_ESTADO_MOVIMIENTO_EFECTIVO_REGISTRADO, DESC_ESTADO_MOVIMIENTO_EFECTIVO_REGISTRADO));
        listaEstatusMovimiento.add(new SelectItem(ID_ESTADO_MOVIMIENTO_EFECTIVO_RETENIDO, DESC_ESTADO_MOVIMIENTO_EFECTIVO_RETENIDO));
        listaEstatusMovimiento.add(new SelectItem(ID_ESTADO_MOVIMIENTO_EFECTIVO_AUTORIZADO, DESC_ESTADO_MOVIMIENTO_EFECTIVO_AUTORIZADO));
        listaEstatusMovimiento.add(new SelectItem(ID_ESTADO_MOVIMIENTO_EFECTIVO_LIBERADO, DESC_ESTADO_MOVIMIENTO_EFECTIVO_LIBERADO));
        listaEstatusMovimiento.add(new SelectItem(ID_ESTADO_MOVIMIENTO_EFECTIVO_APLICADO, DESC_ESTADO_MOVIMIENTO_EFECTIVO_APLICADO));
        listaEstatusMovimiento.add(new SelectItem(ID_ESTADO_MOVIMIENTO_EFECTIVO_CANCELADO, DESC_ESTADO_MOVIMIENTO_EFECTIVO_CANCELADO));
    }


    /**
     * Genera los reportes de Consulta de Emisiones
     *
     * @param evt Evento lanzado por cualquier botón para exportar reportes
     */
    public void generarReportes(ActionEvent evt) {
        LOG.debug(evt.toString());

        reiniciarEstadoPeticion();
        resultados = new PaginaVO();
        resultados.setOffset(0);
        resultados.setRegistrosXPag(PaginaVO.TODOS);

        resultados = this.movimientoEfectivoInternacionalService.
                getMovimientosEfectivoInternacional(this.getCriterios(), this.resultados);
        totalRegistros = resultados.getRegistros().size();
    }

    /**
     * Envía solicitudes de autorización para Cancelar los movimiento recibidos como par�metro
     *
     * @param movimientos Lista de movimientos a cancelar
     */
    private void cancelarMovimientosWorkflow(List<MovimientoEfectivoInternacionalVO> movimientos) {
        this.inicializaIso();
        SolicitudAutorizacionDTO autorizacionDTO;
        MovimientoEfectivoInternacionalVO movEstatusCancelado;

        for (MovimientoEfectivoInternacionalVO movimiento : movimientos) {
            autorizacionDTO = new SolicitudAutorizacionDTO();
            movEstatusCancelado = new MovimientoEfectivoInternacionalVO();

            movEstatusCancelado.setEstadoMovimiento(ESTADO_MOVIMIENTO_CANCELADO);
            movEstatusCancelado.setEstadoLiqIndeval(ESTADO_LIQ_INDEVAL_CANCELADO);
            movEstatusCancelado.setDescEstadoLiqIndeval(ESTATUS_CANCELADO);
            movEstatusCancelado.setDescEstadoMovimiento(ESTATUS_CANCELADO);

            autorizacionDTO.setClaveUsuario(getCveUsuarioSesion());
            autorizacionDTO.setDescripcion(getDescripcionCancelacionMovimientoEfe(movimiento));
            autorizacionDTO.setValorNuevo(JsonUtil.serializeAsJson(movEstatusCancelado));
            autorizacionDTO.setValorAnterior(JsonUtil.serializeAsJson(movimiento));

            solicitudAutorizacionService.nuevaSolicitud(CLAVE_WORKFLOW_CANCELA_MOV_EF, autorizacionDTO, getTicketSesion());
        }

        this.addNotificacionInfo("Solicitud de Cancelaci\u00f3n registrada con \u00E9xito.");
    }

    /**
     * Cancela movimientos seleccionados
     */
    private void cancelarMovimientos() {
        LOG.debug("cancelarMovimientos :: " + movimientos.size());
        procesarMovimientos(ID_ESTADO_MOVIMIENTO_EFECTIVO_CANCELADO, DESC_ESTADO_MOVIMIENTO_EFECTIVO_CANCELADO);
    }


    /**
     * Libera movimiento seleccionados
     */
    private void liberaMovimientos() {
        LOG.debug("liberaMovimientos :: " + movimientos.size());
        procesarMovimientos(ID_ESTADO_MOVIMIENTO_EFECTIVO_LIBERADO, DESC_ESTADO_MOVIMIENTO_EFECTIVO_LIBERADO);
    }


    /**
     * Procesar movimientos seleccionados
     *
     * @param estadoMovEfectivoIntAsignar      ID_ESTADO_MOVIMIENTO_EFECTIVO
     * @param descripcionMovEfectivoIntAsignar DESC_ESTADO_MOVIMIENTO_EFECTIVO
     */
    private void procesarMovimientos(Long estadoMovEfectivoIntAsignar, String descripcionMovEfectivoIntAsignar) {
        LOG.debug("procesarMovimientos"
                + " :: " + estadoMovEfectivoIntAsignar + ".- " + descripcionMovEfectivoIntAsignar
                + " :: " + movimientos.size() + " :: ");

        try {
            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            String numeroSerie = params.get("numeroSerie");

            List<String> idsMovimientosEfectivo = new ArrayList<>();
            String mensajeUsuario;

            for (MovimientoEfectivoInternacionalVO movimiento : movimientos) {
                CuentaTransitoria cta = null;
                if (estadoMovEfectivoIntAsignar.compareTo(ID_ESTADO_MOVIMIENTO_EFECTIVO_LIBERADO) == 0 &&
                        movimiento.getTipoMovimiento().compareTo(DESC_DEPOSITO) == 0) {
                    LOG.debug("procesarMovimientos.validaCuentaTransitoria() folio control " + movimiento.getFolioControl());
                    cta = this.movimientoEfectivoInternacionalService.validaCuentaTransitoria(movimiento);

                    if (cta != null) {
                        LOG.debug("Encontré cuenta transitoria " + cta.getIdCuentaTransitoria());
                        idsMovimientosEfectivo.add(movimiento.getIdMovimientoStr());
                    } else {
                        LOG.debug("No encontr\u00e9 registro de la cuenta tranitoria ");
                        addErrorMessage(String.format("No se encontr\u00f3 informaci\u00f3n de soporte en la tesorer\u00eda, " +
                                "No es posible liberar el registro %s ", movimiento.getFolioControl().toString()));
                        this.addNotificacionError("Error: Problemas con la cuenta de transitoria " +
                                "[ FolioControl= " + movimiento.getFolioControl().toString() + "]");
                        actualizaErrorInvocacion(Boolean.TRUE);
                    }
                } else {
                    idsMovimientosEfectivo.add(movimiento.getIdMovimientoStr());
                }
            }
            if (!idsMovimientosEfectivo.isEmpty()) {
                this.movimientoEfectivoInternacionalService.
                        updateEstatusMovimientosEfectivoInternacional(
                                idsMovimientosEfectivo, estadoMovEfectivoIntAsignar);

                ObjetoFirmadoDTO objDespues;
                MovimientoEfectivoInternacionalVO movDespues;
                int indice = 1;
                Integer idOperacionTransaccion = 0;

                if (ID_ESTADO_MOVIMIENTO_EFECTIVO_LIBERADO.equals(estadoMovEfectivoIntAsignar)) {
                    this.notificaCambioEstadoMoi(movimientos, EstadoMovimiento.LIBERADO);
                    idOperacionTransaccion = ID_OP_TR_LIBERACION_MOV_EFE_DIV_EXT;
                } else if (ID_ESTADO_MOVIMIENTO_EFECTIVO_CANCELADO.equals(estadoMovEfectivoIntAsignar)) {
                    idOperacionTransaccion = ID_OP_TR_CANCELACION_MOV_EFE_DIV_EXT;
                }

                for (MovimientoEfectivoInternacionalVO movimiento : movimientos) {
                    movDespues = (MovimientoEfectivoInternacionalVO) SerializationUtils.clone(movimiento);
                    movDespues.setEstadoMovimiento(estadoMovEfectivoIntAsignar);
                    movDespues.setDescEstadoMovimiento(descripcionMovEfectivoIntAsignar);
                    objDespues = new ObjetoFirmadoDTO(numeroSerie,
                            this.extraerCadenaFirmada(params.get("isoSinFirmar" + indice)), movDespues);
                    BitacoraSupport.doRegistrarBitacora(getCveUsuarioSesion(),
                            new ObjetoFirmadoDTO(null, null, movimiento),
                            objDespues, this.bitacoraService, ID_MODULO_MOV_EFE_DIV_EXT, idOperacionTransaccion);
                    indice++;
                }

                totalOperaciones = isosSinFirmar.size();

                if (!existeErrorEnInvocacion()) {
                    String proceso = (totalOperaciones > 1) ? (totalOperaciones + " Operaciones ") : "Operaci\u00f3n ";

                    if (ID_ESTADO_MOVIMIENTO_EFECTIVO_LIBERADO_INT == estadoMovEfectivoIntAsignar) {

                        proceso += "de Liberaci\u00f3n";
                    } else if (ID_ESTADO_MOVIMIENTO_EFECTIVO_CANCELADO_INT == estadoMovEfectivoIntAsignar) {
                        proceso += "de Cancelaci\u00f3n";
                    } else {
                        proceso += "unknown";
                    }


                    mensajeUsuario = proceso + " realizada" + ((totalOperaciones > 1) ? "s" : "") + " con \u00E9xito.";
                    ejecutarConsulta();
                    this.addNotificacionInfo(mensajeUsuario);
                }
            }
        } catch (Exception ex) {
            LOG.error(ex.toString(), ex);
            this.validationErrors = true;
        }
        actualizaErrorInvocacion(Boolean.FALSE);
        inicializaIso();
    }

    /**
     * Evía notificaciones a MOI :: Para LIBERACION
     *
     * @param movimientos     Operaciones que requieren ser notificadas
     * @param estadoNotificar Estado de cambio de movimiento a notificar
     */
    private void notificaCambioEstadoMoi(
            List<MovimientoEfectivoInternacionalVO> movimientos, EstadoMovimiento estadoNotificar) {
        Multidivisa notificacionMoi;

        for (MovimientoEfectivoInternacionalVO movimiento : movimientos) {
            notificacionMoi = new Multidivisa();
            notificacionMoi.setId(movimiento.getIdMovimiento());
            notificacionMoi.setFolioConstrol(movimiento.getFolioControl());
            notificacionMoi.setTipoMovimiento(movimiento.isDeposito() ? TipoMovimiento.DEPOSITO : TipoMovimiento.RETIRO);
            notificacionMoi.setEstado(estadoNotificar);
            this.sicService.notificaCambioEstadoMovEfeDivInt(notificacionMoi);
        }
    }


    /**
     * Obtener descripción de Movimiento de Cancelación de Efectivo
     */
    private String getDescripcionCancelacionMovimientoEfe(MovimientoEfectivoInternacionalVO movimiento) {
        return getDescripcionMovimientoEfectivo("Cancelación", movimiento);
    }

    /**
     * Obtener descripción de Movimiento de Liberación de Efectivo
     */
    private String getDescripcionLiberacionMovimientoEfe(MovimientoEfectivoInternacionalVO movimiento) {
        return getDescripcionMovimientoEfectivo("Liberación", movimiento);
    }

    /**
     * Obtener descripción de Movimiento de Efectivo
     */
    private String getDescripcionMovimientoEfectivo(String mensaje, MovimientoEfectivoInternacionalVO movimiento) {
        return mensaje + " de Movimiento de Efectivo en Divisas Extranjeras: " + movimiento.toDetalleString();
    }

    /**
     * Actualiza los valores del check para liberar
     */
    public void checkAllOperLibera(ActionEvent event) {
        LOG.debug(event.toString());
        this.inicializaIso();
        if (!this.paginaVO.getRegistros().isEmpty()) {
            for (Object objDto : this.paginaVO.getRegistros()) {
                MovimientoEfectivoInternacionalVO dto = (MovimientoEfectivoInternacionalVO) objDto;
                if (ID_ESTADO_MOVIMIENTO_EFECTIVO_REGISTRADO.equals(dto.getEstadoMovimiento())
                        || ID_ESTADO_MOVIMIENTO_EFECTIVO_AUTORIZADO.equals(dto.getEstadoMovimiento())) {
                    dto.setSeleccionadoLiberar(false);
                }
            }
        }
    }

    /**
     * Actualiza los valores del check para Cancelar
     */
    public void checkAllOperCancela(ActionEvent event) {
        LOG.debug(event.toString());
        this.inicializaIso();
        if (!this.paginaVO.getRegistros().isEmpty()) {
            for (Object objDto : this.paginaVO.getRegistros()) {
                MovimientoEfectivoInternacionalVO dto = (MovimientoEfectivoInternacionalVO) objDto;
                if (ID_ESTADO_MOVIMIENTO_EFECTIVO_REGISTRADO.equals(dto.getEstadoMovimiento())
                        || ID_ESTADO_MOVIMIENTO_EFECTIVO_AUTORIZADO.equals(dto.getEstadoMovimiento())
                        || ID_ESTADO_MOVIMIENTO_EFECTIVO_RETENIDO.equals(dto.getEstadoMovimiento())) {
                    dto.setSeleccionadoCancelar(false);
                }
            }
        }
    }

    /**
     * Valida si el usuario tiene permitido alguna acción sobre los movimientos
     * Permite el render de la columna Selecionar Todos
     *
     * @return boolean
     */
    public boolean validarOperacionesPermitidas() {
        LOG.debug("ValidarOperacionesPermitidas "
                + ":: UsuarioPuedeCancelar [" + this.usuarioPuedeCancelar + "] "
                + ":: UsuarioPuedeLiberar [" + this.usuarioPuedeLiberar + "]");
        return this.usuarioPuedeCancelar || this.usuarioPuedeLiberar;
    }

    /**
     * Valida si el usuario tine permitido la acción de Liberar
     * Valida si el movimiento cuenta con el estado necesario para Liberar
     * Permite el render para poder liberar el movimiento en front
     *
     * @return boolean
     */
    public boolean validarOperacionLiberar(int estadoMovimiento) {
        LOG.debug("ValidarOperacionLiberar "
                + ":: EstadoMovimiento [" + estadoMovimiento + "] "
                + ":: UsuarioPuedeLiberar [" + this.usuarioPuedeLiberar + "] "
                + ":: ID_ESTADO_MOVIMIENTO_EFECTIVO_REGISTRADO  [" + ID_ESTADO_MOVIMIENTO_EFECTIVO_REGISTRADO + "]"
                + ":: ID_ESTADO_MOVIMIENTO_EFECTIVO_AUTORIZADO  [" + ID_ESTADO_MOVIMIENTO_EFECTIVO_AUTORIZADO + "] ");
        return this.usuarioPuedeLiberar && (ID_ESTADO_MOVIMIENTO_EFECTIVO_REGISTRADO.equals((long) estadoMovimiento)
                || ID_ESTADO_MOVIMIENTO_EFECTIVO_AUTORIZADO.equals((long) estadoMovimiento));
    }

    /**
     * Valida si el usuario tine permitido la acción de Cancelar
     * Valida si el movimiento cuenta con el estado necesario para Cancelar
     * Permite el render para poder cancelar el movimiento en front
     *
     * @return boolean
     */
    public boolean validarOperacionCancelar(int estadoMovimiento) {
        LOG.debug("ValidarOperacionCancelar "
                + ":: EstadoMovimiento [" + estadoMovimiento + "] "
                + ":: UsuarioPuedeCancelar [" + this.usuarioPuedeCancelar + "] "
                + ":: ID_ESTADO_MOVIMIENTO_EFECTIVO_REGISTRADO   [" + ID_ESTADO_MOVIMIENTO_EFECTIVO_REGISTRADO + "] "
                + ":: ID_ESTADO_MOVIMIENTO_EFECTIVO_AUTORIZADO  [" + ID_ESTADO_MOVIMIENTO_EFECTIVO_AUTORIZADO + "] "
                + ":: ID_ESTADO_MOVIMIENTO_EFECTIVO_RETENIDO [" + ID_ESTADO_MOVIMIENTO_EFECTIVO_RETENIDO + "] ");
        return this.usuarioPuedeCancelar && (ID_ESTADO_MOVIMIENTO_EFECTIVO_RETENIDO.equals((long) estadoMovimiento)
                || ID_ESTADO_MOVIMIENTO_EFECTIVO_AUTORIZADO.equals((long) estadoMovimiento)
                || ID_ESTADO_MOVIMIENTO_EFECTIVO_REGISTRADO.equals((long) estadoMovimiento));
    }

    /**
     * Valida si el usuario tine permitido la acción de Cancelar sobre el movimiento
     * Valida si los movimientos cuentan con el estado necesario para Cancelar
     *
     * @return boolean
     */
    private String validaUsuarioPermitidoCancelar() {
        LOG.debug("validaUsuarioPermitidoCancelar :: movimientos [" + movimientos.size() + "]");
        for (MovimientoEfectivoInternacionalVO movimiento : movimientos) {
            if (validarOperacionCancelar(movimiento.getEstadoMovimiento().intValue())) {
                if (!this.movimientoEfectivoInternacionalService.esUsuarioPermitidoCancelar(
                        getCveUsuarioSesion(), movimiento.getFolioControl())) {
                    LOG.debug("El usuario [" + getCveUsuarioSesion() + "] "
                            + "NO tiene permitido CANCELAR el movimiento de Efectivo "
                            + "[" + movimiento.getIdMovimiento() + ": " + movimiento.getFolioControl() + "]");
                    return String.format(MENSAJE_USUARIO_INVALIDO_CANCELACION, movimiento.getFolioControl());
                }
            } else {
                LOG.debug("El movimiento [" + movimiento.getIdMovimiento() + " :: " + movimiento.getFolioControl() + "] "
                        + "NO cuenta con el estado para ser CANCELADO  "
                        + "[" + movimiento.getEstadoLiqIndeval() + ": " + movimiento.getDescEstadoMovimiento() + "]");
                return String.format(MENSAJE_MOVIMIENTO_INVALIDO_CANCELACION, movimiento.getFolioControl());
            }
        }

        LOG.debug("El usuario [" + getCveUsuarioSesion() + "] tiene permitido cancelar movimientos");
        return null;

    }

    /**
     * Valida si el usuario tine permitido la acción de Liberar sobre el movimiento
     * Valida si los movimientos cuentan con el estado necesario para Liberar
     *
     * @return boolean
     */
    private String validaUsuarioPermitidoLiberar() {
        LOG.debug("validaUsuarioPermitidoLiberar :: movimientos [" + movimientos.size() + "]");
        for (MovimientoEfectivoInternacionalVO movimiento : movimientos) {
            if (validarOperacionLiberar(movimiento.getEstadoMovimiento().intValue())) {
                if (!this.movimientoEfectivoInternacionalService.esUsuarioPermitidoLiberar(
                        getCveUsuarioSesion(), movimiento.getFolioControl())) {
                    LOG.debug("El usuario [" + getCveUsuarioSesion() + "] "
                            + "NO tiene permitido LIBERAR el movimiento de Efectivo "
                            + "[" + movimiento.getIdMovimiento() + ": " + movimiento.getFolioControl() + "]");
                    return String.format(MENSAJE_USUARIO_INVALIDO_LIBERACION, movimiento.getFolioControl());
                }
            } else {
                LOG.debug("El movimiento [" + movimiento.getIdMovimiento() + " :: " + movimiento.getFolioControl() + "] "
                        + "NO cuenta con el estado para ser LIBERADO  "
                        + "[" + movimiento.getEstadoLiqIndeval() + ": " + movimiento.getDescEstadoMovimiento() + "]");
                return String.format(MENSAJE_MOVIMIENTO_INVALIDO_LIBERACION, movimiento.getFolioControl());
            }
        }

        LOG.debug("El usuario [" + getCveUsuarioSesion() + "] tiene permitido liberar movimientos");
        return null;

    }

    /**
     * Solo carga Mensaje de error por operacion desconocida
     *
     * @return boolean
     */
    private String operacionDesconocida() {
        LOG.debug("Se encontro una operación desconocida");
        return String.format(MENSAJE_OPERACION_INVALIDA);
    }


    /**
     * Ejecuta las operaciones de movimiento desde front
     */
    private void ejecutarOperacion() {
        LOG.debug("ejecutarOperacion :: " + tipoMovimientoEfectivo.name() + " :: " + tipoMovimientoEfectivo.toString());
        LOG.error("validationErrors :: " + validationErrors);
        if (!validationErrors) {
            try {
                String posibleError = this.validarOperaciones();
                if (posibleError == null) {
                    LOG.debug("Es posible continuar ejecutando la operacion :: " + tipoMovimientoEfectivo.name());
                    switch (tipoMovimientoEfectivo.getTipo()) {
                        case TIPO_MOVIMIENTO_EFECTIVO_LIBERAR_SELECCION:
                        case TIPO_MOVIMIENTO_EFECTIVO_LIBERA:
                            this.liberaMovimientos();
                            break;
                        case TIPO_MOVIMIENTO_EFECTIVO_CANCELAR_SELECCION:
                        case TIPO_MOVIMIENTO_EFECTIVO_CANCELA:
                            this.cancelarMovimientos();
                            break;
                    }
                } else {
                    LOG.debug("Ocurrio un problema durante la validación del usuario o los movimientos");
                    this.validationErrors = true;
                    this.addNotificacionError(posibleError);
                }
            } catch (BusinessException e) {
                LOG.error(e.toString(), e);
                this.validationErrors = true;
                this.addNotificacionError("ERROR:" + e.getMessage());
            }
        }
        inicializaIso();
        movimientos.clear();
    }

    /**
     * Valida lo necesario para proseguir con Liberación de Movimientos de Efectivo seleccionados
     */
    public String cargaFirmaLiberarSeleccion(ActionEvent event) {
        LOG.info("Validar Liberar Seleccion :: " + LIBERAR_SELECCION.toString());
        LOG.debug(event.toString());
        cargaFirmaMovimientos(LIBERAR_SELECCION);
        return null;
    }

    /**
     * Valida lo necesario para proseguir con Cancelación de Movimientos de Efectivo seleccionados
     */
    public String cargaFirmaCancelarSeleccion(ActionEvent event) {
        LOG.info("Validar Cancelar Seleccion :: " + CANCELAR_SELECCION.toString());
        LOG.debug(event.toString());
        cargaFirmaMovimientos(CANCELAR_SELECCION);
        return "";
    }

    /**
     * Valida lo necesario para proseguir con Liberación del Movimiento de Efectivo seleccionado
     */
    public String cargaFirmaLiberacion(ActionEvent event) {
        LOG.info("Validar Liberar :: " + LIBERAR.toString());
        LOG.debug(event.toString());
        cargaFirmaMovimientos(LIBERAR);
        return null;
    }

    /**
     * Valida lo necesario para proseguir con Cancelación del Movimiento de Efectivo seleccionado
     */
    public String cargaFirmaCancelacion(ActionEvent event) {
        LOG.info("Validar Cancelar Seleccion :: " + CANCELAR.toString());
        LOG.debug(event.toString());
        cargaFirmaMovimientos(CANCELAR);
        return null;
    }

    /**
     * Valida lo necesario para proseguir con procesamientos de Movimientos de Efectivo
     * FacultadFirma
     * CargaMovimientos
     * ValidarOperaciones
     * CargaFirmas
     */
    private void cargaFirmaMovimientos(TipoMovimientoEfectivo tipoMovimientoEfectivo) {
        LOG.info("firmarMovimientos para :: " + tipoMovimientoEfectivo.name() + " :: " + tipoMovimientoEfectivo.toString());
        if (this.validarFacultadFirmar()) {
            this.tipoMovimientoEfectivo = tipoMovimientoEfectivo;
            this.esTipo = tipoMovimientoEfectivo.getMovimiento();
            LOG.debug(tipoMovimientoEfectivo.name() + " :: " + tipoMovimientoEfectivo.toString());
            movimientos = this.cargarMovimientos();
            if (movimientos == null || movimientos.isEmpty()) {
                agregarMensajeWarn("No hay movimientos seleccionados para procesar");
            } else {
                LOG.debug("Carga Firma(s)");
                this.cargaFirma();
            }
        }
    }


    /**
     * Valida la facultad del usuario para la firma de operaciones
     */
    private boolean validarFacultadFirmar() {
        this.validationErrors = false;
        this.inicializaIso();

        if (!isUsuarioConFacultadFirmar()) {
            LOG.debug("El usuario NO cuenta con la facultad de firmar");
            LOG.debug(MENSAJE_FACULTAD_FIRMA_DIGITAL_REQUERIDA);
            this.addNotificacionError(MENSAJE_FACULTAD_FIRMA_DIGITAL_REQUERIDA);
            this.validationErrors = true;
            this.usuarioPuedeLiberar = false;
            this.usuarioPuedeCancelar = false;
            return false;
        } else {
            LOG.debug("El usuario SI cuenta con la facultad de firmar");
            return true;
        }
    }

    /**
     * Carga Movimientos de Efectivo a procesar
     */
    private List<MovimientoEfectivoInternacionalVO> cargarMovimientos() {
        LOG.info("Cargar Movimientos a procesar :: " + tipoMovimientoEfectivo.name());

        List<MovimientoEfectivoInternacionalVO> cargaMovmientos = new ArrayList<MovimientoEfectivoInternacionalVO>();
        if (tipoMovimientoEfectivo.getTipo().equals(TIPO_MOVIMIENTO_EFECTIVO_LIBERA)
                || tipoMovimientoEfectivo.getTipo().equals(TIPO_MOVIMIENTO_EFECTIVO_CANCELA)) {
            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            this.idMovimientoStrSelected = params.get(ID_MOVIMIENTOS_STR);
            LOG.debug("Cargar solo un movimiento [" + idMovimientoStrSelected + "]"
                    + " para :: " + tipoMovimientoEfectivo.getMovimiento());
            cargaMovmientos.add(getMovimientoByIdStr(this.idMovimientoStrSelected));
        } else {
            for (Object objMovimiento : this.paginaVO.getRegistros()) {
                MovimientoEfectivoInternacionalVO movimiento = (MovimientoEfectivoInternacionalVO) objMovimiento;
                if ((movimiento.isSeleccionadoLiberar()
                        && tipoMovimientoEfectivo.getTipo().equals(TIPO_MOVIMIENTO_EFECTIVO_LIBERAR_SELECCION))
                        || (movimiento.isSeleccionadoCancelar()
                        && tipoMovimientoEfectivo.getTipo().equals(TIPO_MOVIMIENTO_EFECTIVO_CANCELAR_SELECCION))) {
                    LOG.debug("Cargar movimiento [" + movimiento.getIdMovimiento() + "]"
                            + " para :: " + tipoMovimientoEfectivo.getMovimiento());
                    cargaMovmientos.add(movimiento);
                }
            }
        }
        LOG.debug("Total de movimientos cargados :: " + cargaMovmientos.size());

        return cargaMovmientos;
    }

    /**
     * Carga de información de Movimientos de Efectivo a Firmar
     */
    private void cargaFirma() {
        LOG.debug("Es momento de cargar la información para firmar");
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String numeroSerie = params.get("numeroSerie");
        for (MovimientoEfectivoInternacionalVO movimiento : movimientos) {
            String isoElem = movimiento.toDetalleString();
            isoElem += '\n';
            if (isoElem != null) {
                isosSinFirmar.add(isoElem);
                hashIsos.add(cdb.cipherHash(isoElem));
            }
        }
        totalOperaciones = isosSinFirmar.size();
    }

    /**
     * Validación de Operaciones para Movimientos de Efectivo a Firmar
     */
    private String validarOperaciones() {
        LOG.debug("ValidarOperaciones para :: " + tipoMovimientoEfectivo.name() + " :: " + tipoMovimientoEfectivo.toString());
        switch (tipoMovimientoEfectivo.getTipo()) {
            case TIPO_MOVIMIENTO_EFECTIVO_LIBERAR_SELECCION:
            case TIPO_MOVIMIENTO_EFECTIVO_LIBERA:
                return this.validaUsuarioPermitidoLiberar();
            case TIPO_MOVIMIENTO_EFECTIVO_CANCELAR_SELECCION:
            case TIPO_MOVIMIENTO_EFECTIVO_CANCELA:
                return this.validaUsuarioPermitidoCancelar();
            default:
                return this.operacionDesconocida();
        }
    }

    /**
     * Obtiene el movimiento seleccionado desde front
     */
    private MovimientoEfectivoInternacionalVO getMovimientoByIdStr(String idMovimientoStr) {
        LOG.debug("getMovimientoByIdStr :: " + idMovimientoStr);
        for (Object objMovimiento : this.paginaVO.getRegistros()) {
            MovimientoEfectivoInternacionalVO movimiento = (MovimientoEfectivoInternacionalVO) objMovimiento;
            if (idMovimientoStr.equals(movimiento.getIdMovimientoStr())) {
                LOG.debug(movimiento.toString());
                return movimiento;
            }
        }

        return null;
    }

    /**
     * Inicializa los ISO
     */
    private void inicializaIso() {
        LOG.debug("Inicializa ISOs");
        movimientos = new ArrayList<MovimientoEfectivoInternacionalVO>();
        isosSinFirmar = new ArrayList<String>();
        isosFirmados = new ArrayList<String>();
        hashIsos = new ArrayList<String>();
        totalOperaciones = 0;
    }

    /**
     * Verifica si el usuario cuenta con los permisos para Libera Movimientos de Efectivo
     * Verifica si el usuario cuenta con los permisos para Cancelar Movimientos de Efectivo
     */
    private void validarPermisos() {
        LOG.info("validarPermisos "
                + ":: ROL_INT_LIBERA_MOV_EFEC [" + ROL_INT_LIBERA_MOV_EFEC + "] "
                + ":: ROL_INT_CANCELA_MOV_EFEC [" + ROL_INT_CANCELA_MOV_EFEC + "]");
        this.usuarioPuedeLiberar = isUserInRoll(ROL_INT_LIBERA_MOV_EFEC);
        this.usuarioPuedeCancelar = isUserInRoll(ROL_INT_CANCELA_MOV_EFEC);

    }

    private String extraerCadenaFirmada(String paramCadena) {
        if (paramCadena != null && paramCadena.contains(PREFIJO_CADENA_FIRMADA)) {
            return paramCadena.substring(
                    paramCadena.indexOf(PREFIJO_CADENA_FIRMADA) + PREFIJO_CADENA_FIRMADA.length());
        }

        return null;
    }

    private CriteriosConsultaMovEfeDivExtVO getCriterios() {
        CriteriosConsultaMovEfeDivExtVO criteriosConsulta = new CriteriosConsultaMovEfeDivExtVO();

        criteriosConsulta.setIdFolioParticipante(this.idFolioParticipante);
        criteriosConsulta.setFolioControl(this.folioControl);
        criteriosConsulta.setTipoMovimiento(this.tipoMovimiento);
        criteriosConsulta.setIdDivisa(this.idDivisa);
        criteriosConsulta.setIdBoveda(this.idBoveda);
        criteriosConsulta.setEstatusMovimiento(this.estatusMovimiento);
        criteriosConsulta.setFechaInicio(this.fechaInicio);
        criteriosConsulta.setFechaFin(this.fechaFin);
        criteriosConsulta.setFechaLiqInicio(this.fechaLiqInicio);
        criteriosConsulta.setFechaLiqFin(this.fechaLiqFin);

        return criteriosConsulta;
    }

    /**
     * @return the consultaEjecutada
     */
    public boolean isConsultaEjecutada() {
        return consultaEjecutada;
    }

    /**
     * @param consultaEjecutada the consultaEjecutada to set
     */
    public void setConsultaEjecutada(boolean consultaEjecutada) {
        this.consultaEjecutada = consultaEjecutada;
    }

    /**
     * @return the totalPaginas
     */
    @Override
    public int getTotalPaginas() {
        return totalPaginas;
    }

    /**
     * @param totalPaginas the totalPaginas to set
     */
    @Override
    public void setTotalPaginas(int totalPaginas) {
        this.totalPaginas = totalPaginas;
    }

    /**
     * @return the folioControl
     */
    public String getFolioControl() {
        return folioControl;
    }

    /**
     * @param folioControl the folioControl to set
     */
    public void setFolioControl(String folioControl) {
        this.folioControl = folioControl;
    }

    /**
     * @return the listaDivisas
     */
    public List<SelectItem> getListaDivisas() {
        return listaDivisas;
    }

    /**
     * @return the listaEstatusMovimiento
     */
    public List<SelectItem> getListaEstatusMovimiento() {
        return listaEstatusMovimiento;
    }

    /**
     * @return the idDivisa
     */
    public long getIdDivisa() {
        return idDivisa;
    }

    /**
     * @param idDivisa the idDivisa to set
     */
    public void setIdDivisa(long idDivisa) {
        this.idDivisa = idDivisa;

        this.descDivisa = StringUtils.EMPTY;

        for (SelectItem listaDivisa : listaDivisas) {
            if ((listaDivisa).getValue().toString().equals(String.valueOf(idDivisa))) {
                this.descDivisa = (listaDivisa).getLabel();
                break;
            }
        }
    }

    /**
     * @return the idBoveda
     */
    public long getIdBoveda() {
        return idBoveda;
    }

    /**
     * @param idBoveda the idBoveda to set
     */
    public void setIdBoveda(long idBoveda) {
        this.idBoveda = idBoveda;

        this.descBoveda = StringUtils.EMPTY;

        for (SelectItem listaBoveda : listaBovedas) {
            if ((listaBoveda).getValue().toString().equals(String.valueOf(idBoveda))) {
                this.descBoveda = (listaBoveda).getLabel();
                break;
            }
        }
    }

    /**
     * @return the estatusMovimiento
     */
    public long getEstatusMovimiento() {
        return estatusMovimiento;
    }

    /**
     * @param estatusMovimiento the estatusMovimiento to set
     */
    public void setEstatusMovimiento(long estatusMovimiento) {
        this.estatusMovimiento = estatusMovimiento;

        this.descEstatusMovimiento = StringUtils.EMPTY;

        for (SelectItem selectItem : listaEstatusMovimiento)
            if ((selectItem).getValue().toString().equals(String.valueOf(estatusMovimiento))) {
                this.descEstatusMovimiento = (selectItem).getLabel();
                break;
            }
    }

    /**
     * @return the descDivisa
     */
    public String getDescDivisa() {
        return descDivisa;
    }

    /**
     * @param descDivisa the descDivisa to set
     */
    public void setDescDivisa(String descDivisa) {
        this.descDivisa = descDivisa;
    }

    /**
     * @return the descBoveda
     */
    public String getDescBoveda() {
        return descBoveda;
    }

    /**
     * @param descTipoMovimiento the descTipoMovimiento to set
     */
    public void setDescTipoMovimiento(String descTipoMovimiento) {
        this.descTipoMovimiento = descTipoMovimiento;
    }

    /**
     * @param descBoveda the descBoveda to set
     */
    public void setDescBoveda(String descBoveda) {
        this.descBoveda = descBoveda;
    }

    /**
     * @return the descEstatusMovimiento
     */
    public String getDescEstatusMovimiento() {
        return descEstatusMovimiento;
    }

    /**
     * @param descEstatusMovimiento the descEstatusMovimiento to set
     */
    public void setDescEstatusMovimiento(String descEstatusMovimiento) {
        this.descEstatusMovimiento = descEstatusMovimiento;
    }

    /**
     * @return the esTipo
     */
    public String getEsTipo() {
        return esTipo;
    }

    /**
     * @param esTipo the esTipo to set
     */
    public void setEsTipo(String esTipo) {
        this.esTipo = esTipo;
    }

    /**
     * @return the idMovimientoStrSelected
     */
    public String getIdMovimientoStrSelected() {
        return idMovimientoStrSelected;
    }

    /**
     * @return the tipoMovimiento
     */
    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    /**
     * @param tipoMovimiento the tipoMovimiento to set
     */
    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;

        this.descTipoMovimiento = StringUtils.EMPTY;

        for (SelectItem selectItem : listaTiposMovimiento) {
            if (selectItem.getValue().toString().equals(String.valueOf(tipoMovimiento))) {
                this.descTipoMovimiento = selectItem.getLabel();
                break;
            }
        }
    }

    /**
     * @return the descTipoMovimiento
     */
    public String getDescTipoMovimiento() {
        return descTipoMovimiento;
    }

    /**
     * @param idMovimientoStrSelected the idMovimientoStrSelected to set
     */
    public void setIdMovimientoStrSelected(String idMovimientoStrSelected) {
        this.idMovimientoStrSelected = idMovimientoStrSelected;
    }

    /**
     * @return the fechaInicio
     */
    public Date getFechaInicio() {
        return fechaInicio;
    }

    /**
     * @param fechaInicio the fechaInicio to set
     */
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaInicioStr() {
        String fechaStr = "";
        if (fechaInicio != null) {
            fechaStr = DateUtils.format(fechaInicio, FECHA_PATTERN);
        }
        return fechaStr;
    }

    /**
     * @return the fechaFin
     */
    public Date getFechaFin() {
        return fechaFin;
    }

    /**
     * @param fechaFin the fechaFin to set
     */
    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getFechaFinStr() {
        String fechaStr = "";
        if (fechaFin != null) {
            fechaStr = DateUtils.format(fechaFin, FECHA_PATTERN);
        }
        return fechaStr;
    }

    /**
     * @return the fechaLiqInicio
     */
    public Date getFechaLiqInicio() {
        return fechaLiqInicio;
    }

    /**
     * @param fechaLiqInicio the fechaLiqInicio to set
     */
    public void setFechaLiqInicio(Date fechaLiqInicio) {
        this.fechaLiqInicio = fechaLiqInicio;
    }

    public String getFechaLiqInicioStr() {
        String fechaStr = "";
        if (fechaLiqInicio != null) {
            fechaStr = DateUtils.format(fechaLiqInicio, FECHA_PATTERN);
        }
        return fechaStr;
    }

    /**
     * @return the fechaLiqFin
     */
    public Date getFechaLiqFin() {
        return fechaLiqFin;
    }

    /**
     * @param fechaLiqFin the fechaLiqFin to set
     */
    public void setFechaLiqFin(Date fechaLiqFin) {
        this.fechaLiqFin = fechaLiqFin;
    }

    public String getFechaLiqFinStr() {
        String fechaStr = "";
        if (fechaLiqFin != null) {
            fechaStr = DateUtils.format(fechaLiqFin, FECHA_PATTERN);
        }
        return fechaStr;
    }

    /**
     * @return the resultados
     */
    public PaginaVO getResultados() {
        return resultados;
    }

    /**
     * @param resultados the resultados to set
     */
    public void setResultados(PaginaVO resultados) {
        this.resultados = resultados;
    }

    /**
     * @return the totalRegistros
     */
    public int getTotalRegistros() {
        return totalRegistros;
    }

    /**
     * @param totalRegistros the totalRegistros to set
     */
    public void setTotalRegistros(int totalRegistros) {
        this.totalRegistros = totalRegistros;
    }

    /**
     * @return the totalOperaciones
     */
    public int getTotalOperaciones() {
        return totalOperaciones;
    }

    /**
     * @param totalOperaciones the totalOperaciones to set
     */
    public void setTotalOperaciones(int totalOperaciones) {
        this.totalOperaciones = totalOperaciones;
    }

    public List<SelectItem> getListaBovedas() {
        return listaBovedas;
    }

    /**
     * @return the listaTiposMovimiento
     */
    public List<SelectItem> getListaTiposMovimiento() {
        return listaTiposMovimiento;
    }

    /**
     * @return the idFolioParticipante
     */
    public String getIdFolioParticipante() {
        return idFolioParticipante;
    }

    /**
     * @param idFolioParticipante the idFolioParticipante to set
     */
    public void setIdFolioParticipante(String idFolioParticipante) {
        this.idFolioParticipante = idFolioParticipante;
    }

    /**
     * @return the checkAllLibera
     */
    public boolean isCheckAllLibera() {
        return checkAllLibera;
    }

    /**
     * @param checkAllLibera the checkAllLibera to set
     */
    public void setCheckAllLibera(boolean checkAllLibera) {
        this.checkAllLibera = checkAllLibera;
    }

    /**
     * @return the checkAllCancela
     */
    public boolean isCheckAllCancela() {
        return checkAllCancela;
    }

    /**
     * @param checkAllCancela the checkAllCancela to set
     */
    public void setCheckAllCancela(boolean checkAllCancela) {
        this.checkAllCancela = checkAllCancela;
    }

    /**
     * @return the validationErrors
     */
    public boolean isValidationErrors() {
        return validationErrors;
    }

    /**
     * @param validationErrors the validationErrors to set
     */
    public void setValidationErrors(boolean validationErrors) {
        this.validationErrors = validationErrors;
    }

    /**
     * @param movimientoEfectivoInternacionalService the movimientoEfectivoInternacionalService to set
     */
    public void setMovimientoEfectivoInternacionalService(
            MovimientoEfectivoInternacionalService movimientoEfectivoInternacionalService) {
        this.movimientoEfectivoInternacionalService = movimientoEfectivoInternacionalService;
    }

    /**
     * @param divisaDao the divisaDao to set
     */
    public void setDivisaDao(DivisaDao divisaDao) {
        this.divisaDao = divisaDao;
    }

    /**
     * @param bovedaService the bovedaService to set
     */
    public void setBovedaService(BovedaService bovedaService) {
        this.bovedaService = bovedaService;
    }

    /**
     * @return the solicitudAutorizacionService
     */
    public SolicitudAutorizacionService getSolicitudAutorizacionService() {
        return solicitudAutorizacionService;
    }

    /**
     * @param solicitudAutorizacionService the solicitudAutorizacionService to set
     */
    public void setSolicitudAutorizacionService(SolicitudAutorizacionService solicitudAutorizacionService) {
        this.solicitudAutorizacionService = solicitudAutorizacionService;
    }

    /**
     * @param sicService the sicService to set
     */
    public void setSicService(SicService sicService) {
        this.sicService = sicService;
    }

    /**
     * @param bitacoraService the bitacoraService to set
     */
    public void setBitacoraService(BitacoraService bitacoraService) {
        this.bitacoraService = bitacoraService;
    }

    public boolean isUsuarioPuedeLiberar() {
        return usuarioPuedeLiberar;
    }

    public void setUsuarioPuedeLiberar(boolean usuarioPuedeLiberar) {
        this.usuarioPuedeLiberar = usuarioPuedeLiberar;
    }

    public boolean isUsuarioPuedeCancelar() {
        return usuarioPuedeCancelar;
    }

    public void setUsuarioPuedeCancelar(boolean usuarioPuedeCancelar) {
        this.usuarioPuedeCancelar = usuarioPuedeCancelar;
    }

    public List<String> getIsosSinFirmar() {
        return isosSinFirmar;
    }

    public void setIsosSinFirmar(List<String> isosSinFirmar) {
        this.isosSinFirmar = isosSinFirmar;
    }

    public List<String> getIsosFirmados() {
        return isosFirmados;
    }

    public void setIsosFirmados(List<String> isosFirmados) {
        this.isosFirmados = isosFirmados;
    }


    public List<String> getHashIsos() {
        return hashIsos;
    }

    public void setHashIsos(List<String> hashIsos) {
        this.hashIsos = hashIsos;
    }
}
