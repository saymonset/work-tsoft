/**
 * Multidivisas: Controller para consultas de horarios de operación de custodios
 *
 * @author rocio.castañer.vivas
 * @since 15/12/2023
 */

package com.indeval.portalinternacional.presentation.controller.horariosCustodios;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.dao.common.DivisaDao;
import com.indeval.portalinternacional.common.util.CifradorDescifradorBlowfish;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.CustodioService;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.HorariosCustodiosService;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.vo.CriteriosConsultaHorariosCustodiosVO;
import com.indeval.portalinternacional.middleware.servicios.vo.HorariosCustodiosVO;
import com.indeval.portalinternacional.presentation.controller.common.CapturaOperacionesController;
import com.indeval.portalinternacional.middleware.servicios.constantes.EstatusDB;
import com.indeval.portalinternacional.presentation.util.TipoOperacionChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.indeval.portalinternacional.middleware.servicios.constantes.Constantes.*;
import static com.indeval.portalinternacional.presentation.controller.horariosCustodios.UtilHorariosCustodioController.*;
import static com.indeval.portalinternacional.middleware.servicios.constantes.EstatusDB.*;
import static com.indeval.portalinternacional.presentation.util.TipoOperacionChecker.*;

public class HorariosCustodiosConsultaController extends CapturaOperacionesController {

    private static final Logger LOG = LoggerFactory.getLogger(HorariosCustodiosConsultaController.class);

    private Integer idDivisa;
    private String divisaSeleccionada;
    private Integer idCustodio;
    private String custodioSeleccionado;

    private static final String ID_MOVIMIENTOS_STR = "idMovimientoStr";
    private Date fechaCreacion;
    private static final SimpleDateFormat FECHAS_CONSULTAS = new SimpleDateFormat("dd/MM/yy");
    private String fechaCreacionFormateada = null;
    private Integer estatus;
    private String estatusSeleccionado;
    private List<SelectItem> listaDivisas;
    private List<SelectItem> listaCustodio;
    private List<SelectItem> estados;
    private DivisaDao divisaDao;
    private CustodioService custodioService;
    private HorariosCustodiosService horariosCustodiosService;
    private boolean consultaEjecutada = false;
    private boolean validationErrors = false;

    private int totalPaginas = 1;

    private int totalOperaciones = 0;

    private Integer idMovimientoStrSelected;

    private boolean usuarioPuedeAutorizar = false;
    private boolean usuarioPuedeCancelar = false;

    private boolean checkAllAutoriza;
    private boolean checkAllCancela;

    private String esTipo;
    private TipoOperacionChecker tipoOperacionHorarioCustodio;

    private List<HorariosCustodiosVO> horariosCustodiosOperacion;

    private List<String> isosSinFirmar;
    private List<String> isosFirmados;
    private List<String> hashIsos;

    private CifradorDescifradorBlowfish cdb = new CifradorDescifradorBlowfish();

    /**
     * Inicializa lo necesario para la pantalla de consultas
     */
    public String getInicializarConsulta() {
        LOG.info("getInicializarConsulta");

        validarPermisos();

        listaCustodio = obtenerCustodios(custodioService.findAll());
        listaDivisas = obtenerDivisas(divisaDao.findDivisas());


        estados = obtenerEstados();

        getInicializar();
        return "";
    }

    /**
     * Inicializa lo básico para la pantalla de consultas
     */
    public void getInicializar() {
        LOG.info("getInicializarConsulta");

        inicializarChksAcciones();

        if (paginaVO == null) paginaVO = new PaginaVO();

        LOG.debug("cargaSelecciones :: CONSULTAS");

        idDivisa = -1;
        divisaSeleccionada = null;
        idCustodio = -1;
        custodioSeleccionado = null;
        estatus = -1;
        estatusSeleccionado = null;
        fechaCreacion = null;
        fechaCreacionFormateada = null;

        paginaVO.setRegistrosXPag(50);
        paginaVO.setOffset(0);

        consultaEjecutada = false;
        validationErrors = false;
    }

    /**
     * Inicializa lista acciones
     */
    private void inicializarChksAcciones() {
        this.checkAllAutoriza = false;
        this.checkAllCancela = false;
        this.validationErrors = false;
    }

    /**
     * Accion para realizar la consulta
     */
    public void consultarAction(ActionEvent e) {
        LOG.info("consultarAction");
        LOG.debug("idCustodio [" + idCustodio + "] " + ":: idDivisa [" + idDivisa + "] " + ":: fechaCreacion [" + fechaCreacion + "] " + ":: estadoSeleccionado [" + estatus + "]");

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

        cargaCriteriosMuestraResumen();

        this.consultaEjecutada = false;
        this.validationErrors = false;

        LOG.debug(this.getCriterios().toString());
        LOG.debug(this.paginaVO.toString());

        paginaVO = this.horariosCustodiosService.getHorariosCustodios(this.getCriterios(), this.paginaVO);

        totalPaginas = paginaVO.getTotalRegistros() / paginaVO.getRegistrosXPag();

        if (paginaVO.getTotalRegistros() % paginaVO.getRegistrosXPag() > 0) totalPaginas++;

        totalPaginas = (totalPaginas <= 0) ? 1 : totalPaginas;

        LOG.debug("Total de Páginas : " + totalPaginas);
        LOG.debug("Total de Registros: " + paginaVO.getRegistros().size());

        this.consultaEjecutada = true;

        return null;
    }

    /**
     * Carga de criterios seleccionados para búsqueda de consulta de horarios
     */
    private void cargaCriteriosMuestraResumen() {

        if (fechaCreacion != null) {
            fechaCreacionFormateada = FECHAS_CONSULTAS.format(fechaCreacion);
        }

        if (idCustodio.equals(-1)) {
            custodioSeleccionado = "Todos";
        } else {
            for (SelectItem item : listaCustodio) {
                if (((String) item.getValue()).equals(idCustodio.toString())) {
                    custodioSeleccionado = item.getLabel();
                    break;
                }
            }
        }

        if (idDivisa.equals(-1)) {
            divisaSeleccionada = "Todas";
        } else {
            for (SelectItem item : listaDivisas) {
                if (((String) item.getValue()).equals(idDivisa.toString())) {
                    divisaSeleccionada = (String) item.getLabel();
                    break;
                }
            }
        }

        if (estatus.equals(-1)) {
            estatusSeleccionado = "Todos";
        } else {
            estatusSeleccionado = EstatusDB.obtenerDescripcion(estatus);
        }

    }


    /**
     * Obtener criterios finales para búsqueda de consulta de horarios
     */
    private CriteriosConsultaHorariosCustodiosVO getCriterios() {
        CriteriosConsultaHorariosCustodiosVO criteriosConsulta = new CriteriosConsultaHorariosCustodiosVO();
        criteriosConsulta.setIdDivisa((this.idDivisa.equals(-1) ? null : this.idDivisa));
        criteriosConsulta.setIdCustodio(this.idCustodio.equals(-1) ? null : this.idCustodio);
        criteriosConsulta.setIdEstatus(this.estatus.equals(-1) ? null : this.estatus);
        criteriosConsulta.setFechaCreacion(this.fechaCreacion);
        return criteriosConsulta;
    }


    /**
     * Metodo para limpiar la consulta
     *
     * @param e objeto con la accion de faces
     */
    public void limpiar(ActionEvent e) {
        getInicializar();
        if (paginaVO.getRegistros() != null) paginaVO.getRegistros().clear();
    }

    /**
     * Obtienen la descripción del estado del horario del custodio
     *
     * @param estatus Estado del horario
     */
    public String obtenerDescripcionEstatus(int estatus) {
        return EstatusDB.obtenerDescripcion(estatus);
    }


    /**
     * Valida si el usuario tiene permitido alguna acción sobre los horarios registrados
     * Permite el render de la columna Selecionar Todos
     *
     * @return boolean
     */
    public boolean validarOperacionesPermitidas() {
        LOG.debug("ValidarOperacionesPermitidas " + ":: UsuarioPuedeCancelar [" + this.usuarioPuedeCancelar + "] " + ":: UsuarioPuedeAutorizar [" + this.usuarioPuedeAutorizar + "]");
        return this.usuarioPuedeCancelar || this.usuarioPuedeAutorizar;
    }

    /**
     * Verifica si el usuario cuenta con los permisos para Autorizar el Horario del Custodio
     * Verifica si el usuario cuenta con los permisos para Cancelar el Horario del Custodio
     */
    private void validarPermisos() {
        LOG.info("validarPermisos " + ":: ROL_INT_AUTORIZA_HORA_CUST [" + ROL_INT_AUTORIZA_HORA_CUST + "]");
        this.usuarioPuedeAutorizar = isUserInRoll(ROL_INT_AUTORIZA_HORA_CUST);
        this.usuarioPuedeCancelar = isUserInRoll(ROL_INT_AUTORIZA_HORA_CUST);

    }

    /**
     * Valida lo necesario para proseguir con Autorización de los Horarios seleccionados
     */
    public String cargaFirmaAutorizarSeleccion(ActionEvent event) {
        LOG.info("Validar Autorizar Seleccion :: " + AUTORIZA_SELECCION.toString());
        LOG.debug(event.toString());
        cargaFirmaOperacion(AUTORIZA_SELECCION);
        return null;
    }

    /**
     * Valida lo necesario para proseguir con Cancelación de los Horarios seleccionados
     */
    public String cargaFirmaCancelarSeleccion(ActionEvent event) {
        LOG.info("Validar Cancelar Seleccion :: " + CANCELAR_SELECCION.toString());
        LOG.debug(event.toString());
        cargaFirmaOperacion(CANCELAR_SELECCION);
        return "";
    }

    /**
     * Valida lo necesario para proseguir con la Autorización  del Horario del Custodio seleccionado
     */
    public String cargaFirmaAutorizacion(ActionEvent event) {
        LOG.info("Validar Autorizar :: " + AUTORIZA.toString());
        LOG.debug(event.toString());
        cargaFirmaOperacion(AUTORIZA);
        return null;
    }

    /**
     * Valida lo necesario para proseguir con la Cancelación del Horario del Custodio seleccionado
     */
    public String cargaFirmaCancelacion(ActionEvent event) {
        LOG.info("Validar Cancelar Seleccion :: " + CANCELAR.toString());
        LOG.debug(event.toString());
        cargaFirmaOperacion(CANCELAR);
        return null;
    }

    /**
     * Ejecuta la operación de autorizar horarios seleccionados desde front
     */
    public String autorizaSeleccion(ActionEvent evt) {
        LOG.info("autorizaSeleccion");
        ejecutarOperacion();
        return null;
    }


    /**
     * Ejecuta la operación de cancelar horarios seleccionados desde front
     */
    public String cancelaSeleccion(ActionEvent evt) {
        LOG.info("cancelaSeleccion");
        ejecutarOperacion();
        return null;
    }

    /**
     * Ejecuta la operación de autorizar horario desde front
     */
    public String autoriza(ActionEvent evt) {
        LOG.info("autoriza");
        ejecutarOperacion();
        return null;
    }

    /**
     * Ejecuta la operación de cancelar horarios desde front
     */
    public String cancela(ActionEvent evt) {
        LOG.info("Cancela :: ");
        ejecutarOperacion();
        return null;
    }


    /**
     * Ejecuta las operaciones de Horarios Custodios desde front
     */
    private void ejecutarOperacion() {
        LOG.debug("ejecutarOperacion" +
                " :: " + tipoOperacionHorarioCustodio.name() +
                " :: " + tipoOperacionHorarioCustodio.toString());
        if (this.validarOperaciones()) {
            switch (tipoOperacionHorarioCustodio.getTipo()) {
                case TIPO_OPERACION_AUTORIZA:
                case TIPO_OPERACION_AUTORIZA_SELECCION:
                    this.autorizaHorariosCustodios();
                    break;
                case Constantes.TIPO_OPERACION_CANCELA:
                case Constantes.TIPO_OPERACION_CANCELAR_SELECCION:
                    this.cancelaHorariosCustodios();
                    break;
            }
        }
    }

    /**
     * Autoriza Horarios Custodios seleccionados
     */
    private void autorizaHorariosCustodios() {
        LOG.debug("autorizaHorariosCustodios :: " + horariosCustodiosOperacion.size());
        procesarOperacion(AUTORIZADO);
    }

    /**
     * Cancela Horarios Custodios seleccionados
     */
    private void cancelaHorariosCustodios() {
        LOG.debug("cancelaHorariosCustodios :: " + horariosCustodiosOperacion.size());
        procesarOperacion(CANCELADO);
    }

    /**
     * Procesar Horarios Custodios seleccionados
     *
     * @param estadoAsignar nuevo estado a Asignar
     */
    private void procesarOperacion(EstatusDB estadoAsignar) {
        LOG.debug("procesarHorariosCustodios" +
                " :: " + estadoAsignar.toString() +
                " :: " + horariosCustodiosOperacion.size() + " :: ");

        try {
            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            String numeroSerie = params.get("numeroSerie");
            String mensajeUsuario;

            String usuarioChecker = getNombreUsuarioSesion();
            for (HorariosCustodiosVO horarioCustodioVO : horariosCustodiosOperacion) {
                this.horariosCustodiosService.updateHorariosCustodios(
                        horarioCustodioVO.getIdHorariosCustodios(),
                        estadoAsignar.getCodigo(), usuarioChecker);
            }
            totalOperaciones = isosSinFirmar.size();

            String proceso = (totalOperaciones > 1) ? (totalOperaciones + " Operaciones ") : "Operaci\u00f3n ";

            if (ID_ESTADO_AUTORIZADO == estadoAsignar.getCodigo()) {
                proceso += "de Autorizaci\u00f3n";
            } else if (ID_ESTADO_CANCELADO == estadoAsignar.getCodigo()) {
                proceso += "de Cancelaci\u00f3n";
            } else {
                proceso += "unknown";
            }

            mensajeUsuario = proceso + " realizada" + ((totalOperaciones > 1) ? "s" : "") + " con \u00E9xito.";
            ejecutarConsulta();
            this.addNotificacionInfo(mensajeUsuario);

        } catch (Exception ex) {
            LOG.error(ex.toString(), ex);
            this.validationErrors = true;
            this.addNotificacionError(ex.getMessage());
        }
        inicializaIso();
    }


    /**
     * Valida lo necesario para proseguir con procesamientos de Horarios Custodios
     * FacultadFirma
     * CargaHorariosCustodios
     * ValidarOperaciones
     * CargaFirmas
     */
    private void cargaFirmaOperacion(TipoOperacionChecker tipoOperacionHorarioCustodio) {
        LOG.info("firmarOperacion para :: " + tipoOperacionHorarioCustodio.name() + " :: " + tipoOperacionHorarioCustodio.toString());
        try {
            if (this.validarFacultadFirmar()) {
                this.tipoOperacionHorarioCustodio = tipoOperacionHorarioCustodio;
                this.esTipo = tipoOperacionHorarioCustodio.getOperacion();
                LOG.debug(tipoOperacionHorarioCustodio.name() + " :: " + tipoOperacionHorarioCustodio.toString());
                horariosCustodiosOperacion = this.cargarHorariosCustodios();
                if (horariosCustodiosOperacion == null || horariosCustodiosOperacion.isEmpty()) {
                    agregarMensajeWarn("No hay Horarios Custodios seleccionados para procesar");
                } else {
                    LOG.debug("Carga Firma(s)");
                    this.cargaFirma();
                }
            }
        } catch (Exception ex) {
            LOG.error(ex.toString(), ex);
            this.validationErrors = true;
            this.addNotificacionError(ex.getMessage());
        }
    }

    /**
     * Carga Horarios Custodios a procesar
     */
    private List<HorariosCustodiosVO> cargarHorariosCustodios() {
        LOG.info("Cargar HorariosCustodios a procesar :: " + tipoOperacionHorarioCustodio.name());
        List<HorariosCustodiosVO> cargaHorariosCustodios = new ArrayList<>();

        if (tipoOperacionHorarioCustodio.getTipo().equals(TIPO_OPERACION_AUTORIZA)
                || tipoOperacionHorarioCustodio.getTipo().equals(Constantes.TIPO_OPERACION_CANCELA)) {
            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            this.idMovimientoStrSelected = Integer.parseInt(params.get(ID_MOVIMIENTOS_STR));
            LOG.debug("Cargar solo un Horario Custodio [" + idMovimientoStrSelected + "]" +
                    " para :: " + tipoOperacionHorarioCustodio.getOperacion());
            cargaHorariosCustodios.add(getHorariosCustodiosById(this.idMovimientoStrSelected));
        } else {
            for (Object objVO : this.paginaVO.getRegistros()) {
                HorariosCustodiosVO horariosCustodiosVO = (HorariosCustodiosVO) objVO;

                LOG.debug(horariosCustodiosVO.toString());

                LOG.debug(horariosCustodiosVO.isSeleccionadoAutorizar()
                        + " :: " + tipoOperacionHorarioCustodio.getTipo() + " VS. " + TIPO_OPERACION_AUTORIZA_SELECCION
                        + " :: " + tipoOperacionHorarioCustodio.equals(TIPO_OPERACION_AUTORIZA_SELECCION));

                LOG.debug(horariosCustodiosVO.isSeleccionadoCancelar()
                        + " :: " + tipoOperacionHorarioCustodio.getTipo() + " VS. " + Constantes.TIPO_OPERACION_CANCELAR_SELECCION
                        + " :: " + tipoOperacionHorarioCustodio.equals(Constantes.TIPO_OPERACION_CANCELAR_SELECCION));

                if ((horariosCustodiosVO.isSeleccionadoAutorizar()
                        && tipoOperacionHorarioCustodio.getTipo().equals(TIPO_OPERACION_AUTORIZA_SELECCION))
                        || (horariosCustodiosVO.isSeleccionadoCancelar()
                        && tipoOperacionHorarioCustodio.getTipo().equals(TIPO_OPERACION_CANCELAR_SELECCION))) {
                    LOG.debug("Cargar Horarios Custodios [" + horariosCustodiosVO.getIdHorariosCustodios() + "]" +
                            " para :: " + tipoOperacionHorarioCustodio.getOperacion());
                    cargaHorariosCustodios.add(horariosCustodiosVO);
                }
            }
        }
        LOG.debug("Total de Horarios Custodios cargados :: " + cargaHorariosCustodios.size());

        return cargaHorariosCustodios;
    }


    /**
     * Carga de información de Horarios Custodios a Firmar
     */
    private void cargaFirma() {
        LOG.debug("Es momento de cargar la información para firmar");
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String numeroSerie = params.get("numeroSerie");
        for (HorariosCustodiosVO horariosCustodiosVO : horariosCustodiosOperacion) {
            String isoElem = horariosCustodiosVO.toDetalleString();
            isoElem += '\n';
            if (isoElem != null) {
                isosSinFirmar.add(isoElem);
                hashIsos.add(cdb.cipherHash(isoElem));
            }
        }
        totalOperaciones = isosSinFirmar.size();
    }

    /**
     * Validación de Operacion para Horarios Custodios a Firmar
     */
    private boolean validarOperaciones() {
        LOG.debug("ValidarOperaciones para :: " + tipoOperacionHorarioCustodio.name() + " :: " + tipoOperacionHorarioCustodio.toString());
        switch (tipoOperacionHorarioCustodio.getTipo()) {
            case TIPO_OPERACION_AUTORIZA:
            case TIPO_OPERACION_AUTORIZA_SELECCION:
                return this.usuarioPuedeAutorizar;
            case Constantes.TIPO_OPERACION_CANCELA:
            case Constantes.TIPO_OPERACION_CANCELAR_SELECCION:
                return this.usuarioPuedeCancelar;
            default:
                return this.operacionDesconocida();
        }
    }

    /**
     * Solo carga Mensaje de error por operacion desconocida
     *
     * @return boolean
     */
    private boolean operacionDesconocida() {
        cargaExcepcion(String.format(MENSAJE_OPERACION_INVALIDA));
        return false;
    }

    private void cargaExcepcion(String mensaje) {
        throw new BusinessException(mensaje);
    }

    /**
     * Obtiene el Horario Custodio seleccionado desde front
     */
    private HorariosCustodiosVO getHorariosCustodiosById(Integer idHorariosCustodios) {
        LOG.debug("getHorariosCustodiosById :: " + idHorariosCustodios);
        for (Object objVO : this.paginaVO.getRegistros()) {
            HorariosCustodiosVO horariosCustodiosVO = (HorariosCustodiosVO) objVO;
            if (idHorariosCustodios.equals(horariosCustodiosVO.getIdHorariosCustodios())) {
                LOG.debug(horariosCustodiosVO.toString());
                return horariosCustodiosVO;
            }
        }

        return null;
    }

    /**
     * Inicializa los ISO
     */
    private void inicializaIso() {
        LOG.debug("Inicializa ISOs");
        horariosCustodiosOperacion = new ArrayList<HorariosCustodiosVO>();
        isosSinFirmar = new ArrayList<String>();
        isosFirmados = new ArrayList<String>();
        hashIsos = new ArrayList<String>();
        totalOperaciones = 0;
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
            this.usuarioPuedeAutorizar = false;
            this.usuarioPuedeCancelar = false;
            return false;
        } else {
            LOG.debug("El usuario SI cuenta con la facultad de firmar");
            return true;
        }
    }

    /**
     * Actualiza los valores del check para autorizar
     */
    public void checkAllOperAutoriza(ActionEvent event) {
        LOG.debug(event.toString());
        this.inicializaIso();
        if (!this.paginaVO.getRegistros().isEmpty()) {
            for (Object objVO : this.paginaVO.getRegistros()) {
                HorariosCustodiosVO vo = (HorariosCustodiosVO) objVO;
                if (vo.getEstatus().intValue() == REGISTRADO.getCodigo()) {
                    vo.setSeleccionadoAutorizar(false);
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
            for (Object objVO : this.paginaVO.getRegistros()) {
                HorariosCustodiosVO vo = (HorariosCustodiosVO) objVO;
                if (vo.getEstatus().intValue() == REGISTRADO.getCodigo()
                        || vo.getEstatus().intValue() == AUTORIZADO.getCodigo()) {
                    vo.setSeleccionadoCancelar(false);
                }
            }
        }
    }

    /**
     * Valida si el usuario tiene permitido la acción de Autorizar
     * Valida si el horario cuenta con el estado necesario para Autorizar
     * Permite el render para poder autorizar el Horario Custodio en front
     *
     * @return boolean
     */
    public boolean validarOperacionAutorizar(int estadoHorario) {
        LOG.debug("ValidarOperacionAutorizar " + ":: EstadoHorario [" + estadoHorario + "] " + ":: UsuarioPuedeAutorizar [" + this.usuarioPuedeAutorizar + "] " + ":: ESTADO_HORARIO_REGISTRADO  [" + REGISTRADO.toString() + "]");
        return this.usuarioPuedeAutorizar && (REGISTRADO.getCodigo() == estadoHorario);
    }

    /**
     * Valida si el usuario tiene permitido la acción de Cancelar
     * Valida si el horario cuenta con el estado necesario para Cancelar
     * Permite el render para poder cancelar el Horario Custodio en front
     *
     * @return boolean
     */
    public boolean validarOperacionCancelar(int estadoHorario) {
        LOG.debug("ValidarOperacionCancelar " + ":: EstadoHorario [" + estadoHorario + "] " + ":: UsuarioPuedeCancelar [" + this.usuarioPuedeCancelar + "] " + ":: ESTADO_HORARIO_REGISTRADO  [" + REGISTRADO.toString() + "]" + ":: ESTADO_HORARIO_AUTORIZADO [" + AUTORIZADO.toString() + "]");
        return this.usuarioPuedeCancelar && ((REGISTRADO.getCodigo() == estadoHorario) || (AUTORIZADO.getCodigo() == estadoHorario));
    }

    public Integer getIdDivisa() {
        return idDivisa;
    }

    public void setIdDivisa(Integer idDivisa) {
        this.idDivisa = idDivisa;
    }

    public Integer getIdCustodio() {
        return idCustodio;
    }

    public void setIdCustodio(Integer idCustodio) {
        this.idCustodio = idCustodio;
    }

    public List<SelectItem> getListaDivisas() {
        return listaDivisas;
    }

    public void setListaDivisas(List<SelectItem> listaDivisas) {
        this.listaDivisas = listaDivisas;
    }

    public List<SelectItem> getListaCustodio() {
        return listaCustodio;
    }

    public void setListaCustodio(List<SelectItem> listaCustodio) {
        this.listaCustodio = listaCustodio;
    }

    public List<SelectItem> getEstados() {
        return estados;
    }

    public void setEstados(List<SelectItem> estados) {
        this.estados = estados;
    }

    public DivisaDao getDivisaDao() {
        return divisaDao;
    }

    public void setDivisaDao(DivisaDao divisaDao) {
        this.divisaDao = divisaDao;
    }

    public CustodioService getCustodioService() {
        return custodioService;
    }

    public void setCustodioService(CustodioService custodioService) {
        this.custodioService = custodioService;
    }

    public HorariosCustodiosService getHorariosCustodiosService() {
        return horariosCustodiosService;
    }

    public void setHorariosCustodiosService(HorariosCustodiosService horariosCustodiosService) {
        this.horariosCustodiosService = horariosCustodiosService;
    }

    public boolean isConsultaEjecutada() {
        return consultaEjecutada;
    }

    public void setConsultaEjecutada(boolean consultaEjecutada) {
        this.consultaEjecutada = consultaEjecutada;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

    /**
     * Metodo para obtener el atributo fechaCreacionFormateada
     *
     * @return El atributo fechaFormatCreacioneada
     */
    public String getFechaCreacionFormateada() {
        return fechaCreacionFormateada;
    }

    /**
     * Metodo para establecer el atributo fechaCreacionFormateada
     *
     * @param fechaCreacionFormateada El valor del atributo fechaCreacionFormateada a establecer.
     */
    public void setFechaCreacionFormateada(String fechaCreacionFormateada) {
        this.fechaCreacionFormateada = fechaCreacionFormateada;
    }

    public String getDivisaSeleccionada() {
        return divisaSeleccionada;
    }

    public void setDivisaSeleccionada(String divisaSeleccionada) {
        this.divisaSeleccionada = divisaSeleccionada;
    }

    public String getCustodioSeleccionado() {
        return custodioSeleccionado;
    }

    public void setCustodioSeleccionado(String custodioSeleccionado) {
        this.custodioSeleccionado = custodioSeleccionado;
    }

    public String getEstatusSeleccionado() {
        return estatusSeleccionado;
    }

    public void setEstatusSeleccionado(String estatusSeleccionado) {
        this.estatusSeleccionado = estatusSeleccionado;
    }

    /**
     * @param idMovimientoStrSelected the idMovimientoStrSelected to set
     *                                idMovimientoStrSelected = idHorariosCustodios
     */
    public void setIdMovimientoStrSelected(Integer idMovimientoStrSelected) {
        this.idMovimientoStrSelected = idMovimientoStrSelected;
    }

    /**
     * @return the idMovimientoStrSelected
     * idMovimientoStrSelected = idHorariosCustodios
     */
    public Integer getIdMovimientoStrSelected() {
        return idMovimientoStrSelected;
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

    public boolean isUsuarioPuedeAutorizar() {
        return usuarioPuedeAutorizar;
    }

    public void setUsuarioPuedeAutorizar(boolean usuarioPuedeAutorizar) {
        this.usuarioPuedeAutorizar = usuarioPuedeAutorizar;
    }

    public boolean isUsuarioPuedeCancelar() {
        return usuarioPuedeCancelar;
    }

    public void setUsuarioPuedeCancelar(boolean usuarioPuedeCancelar) {
        this.usuarioPuedeCancelar = usuarioPuedeCancelar;
    }

    /**
     * @return the checkAllAutoriza
     */
    public boolean isCheckAllAutoriza() {
        return checkAllAutoriza;
    }

    /**
     * @param checkAllAutoriza the checkAllAutoriza to set
     */
    public void setCheckAllAutoriza(boolean checkAllAutoriza) {
        this.checkAllAutoriza = checkAllAutoriza;
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

}
