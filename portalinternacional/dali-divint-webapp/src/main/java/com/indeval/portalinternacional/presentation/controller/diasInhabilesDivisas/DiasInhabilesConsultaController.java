/**
 * Multidivisas: Días Inhábiles de Divisas
 */
package com.indeval.portalinternacional.presentation.controller.diasInhabilesDivisas;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.common.util.CifradorDescifradorBlowfish;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.DiasInhabilesDivisasService;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.constantes.EstatusDB;
import com.indeval.portalinternacional.middleware.servicios.dto.diasIhabilesDivisas.DiasInhabilesDivisasDTO;
import com.indeval.portalinternacional.middleware.servicios.dto.diasIhabilesDivisas.HistoricoDiasInhabilesDivisasDTO;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;
import com.indeval.portalinternacional.presentation.util.TipoOperacionChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static com.indeval.portalinternacional.middleware.servicios.constantes.Constantes.*;
import static com.indeval.portalinternacional.middleware.servicios.constantes.EstatusDB.*;
import static com.indeval.portalinternacional.presentation.util.TipoOperacionChecker.*;


public class DiasInhabilesConsultaController extends ControllerBase {

    /**
     * Log de clase.
     */
    private static final Logger log = LoggerFactory.getLogger(DiasInhabilesConsultaController.class);

    private String OPERACION = "CONSULTA";

    private static final String ID_DIA_INHABIL_STR = "idDiaInhabilStr";
    private static final String ID_HISTORICO_STR = "idHistoricoStr";

    private Integer anioSeleccionado;
    private DiasInhabilesDivisasService diasInhabilesDivisasService;
    private List<HistoricoDiasInhabilesDivisasDTO> historicos;
    private Long idHistoricoSelected;
    private Long idHistoricoStrSelected;
    private Long idDiaInhabilStrSelected;
    private HistoricoDiasInhabilesDivisasDTO historicoSelected;
    private List<Object> actualizarObjetos;
    private String esTipo;

    private boolean validationErrors = false;
    private int totalOperaciones = 0;

    private boolean usuarioPuedeAutorizar = false;
    private boolean usuarioPuedeCancelar = false;
    private boolean usuarioPuedeEliminar = false;

    private boolean checkAllEliminar;

    private TipoOperacionChecker tipoOperacionDiasInhabiles;

    private List<String> isosSinFirmar;
    private List<String> isosFirmados;
    private List<String> hashIsos;

    private CifradorDescifradorBlowfish cdb = new CifradorDescifradorBlowfish();

    /**
     * Paginador de registros de resumen de errores
     */
    private PaginaVO pagina;
    private int totalPaginas = 1;

    private boolean consultaEjecutada;

    private PaginaVO resultados = null;

    /**
     * Mensaje de error a mostrar del m&eacute;todo del thread
     */
    private String mensajeError;


    ////////////////////////////////////////////Seccion de Metodos ////////////////////////////////////////////

    // <editor-fold defaultstate="collapsed" desc="Sección de Inicializadores">

    /**
     * Recupera la informaci&oacute;n del proceso que est&aacute; actualmente corriendo para mostrar la pantalla por primera vez.
     *
     * @return null
     */
    public String getInit() {
        consultaEjecutada = false;
        inicializaPermisos();
        inicializarPaginaVO();
        inicializaIso();
        cargaHistoricos();
        return null;
    }

    /**
     * inicializaPaginaVO
     */
    private void inicializarPaginaVO() {
        if (paginaVO == null) paginaVO = new PaginaVO();
        paginaVO.limpiaResultados();
        paginaVO.setRegistrosXPag(20);
        paginaVO.setOffset(0);
    }

    /**
     * Inicializa los ISO
     */
    private void inicializaIso() {
        log.debug("Inicializa ISOs");
        actualizarObjetos = new ArrayList<Object>();
        isosSinFirmar = new ArrayList<String>();
        isosFirmados = new ArrayList<String>();
        hashIsos = new ArrayList<String>();
        totalOperaciones = 0;
    }

    /**
     * Verifica si el usuario cuenta con los permisos para Autorizar el Historico de Días Inhábiles de Divisas
     * Verifica si el usuario cuenta con los permisos para Cancelar el Historico de Días Inhábiles de Divisas
     * Verifica si el usuario cuenta con los permisos para Eliminar el Día Inhabil de Divisas
     */
    private void inicializaPermisos() {
        log.info("validarPermisos "
                + ":: INT_AUTORIZA_DIAS_DIVISA [" + ROL_INT_AUTORIZA_DIAS_DIVISA + "] "
                + ":: INT_OPERADOR_DIAS_DIVISA [" + ROL_INT_OPERADOR_DIAS_DIVISA + "]");
        this.usuarioPuedeAutorizar = isUserInRoll(ROL_INT_AUTORIZA_DIAS_DIVISA);
        this.usuarioPuedeCancelar = isUserInRoll(ROL_INT_AUTORIZA_DIAS_DIVISA);
        this.usuarioPuedeEliminar = isUserInRoll(ROL_INT_AUTORIZA_DIAS_DIVISA);

    }


    // </editor-fold>

    /**
     * Obtiene los registros de históricos cargados (Información de archivos csv)
     */
    public String cargaHistoricos() {
        log.debug("Cargar Historicos" + OPERACION);
        String anio = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("anio");
        log.debug(anio);
        if (anio != null && !anio.isEmpty()) {
            Pattern patronNumerico = Pattern.compile("-?\\d+(\\.\\d+)?");
            if (patronNumerico.matcher(anio).matches()) {
                this.anioSeleccionado = Integer.parseInt(anio);
            }
        }

        this.inicializarPaginaVO();
        if (this.anioSeleccionado == null || this.anioSeleccionado.equals(-1)) {
            log.debug("Consultar TODOS los años");
            this.historicos = diasInhabilesDivisasService.getAll(null, null);
        } else {
            log.debug("Consultar año [" + anioSeleccionado + "]");
            this.historicos = diasInhabilesDivisasService.getAll(null, this.anioSeleccionado);
        }

        return null;
    }

    // <editor-fold defaultstate="collapsed" desc="Sección de acciones desde Front: Autorizar y Cancelar Históricos, Eliminar Días Inhábiles">

    /**
     * Ejecuta la operación de autorizar historico desde front
     */
    public String ejecutarOperacion(ActionEvent evt) {
        log.info("ejecutarOperacion");
        ejecutarOperacion();
        return null;
    }


    /**
     * Ejecuta las operaciones de Días Inhábiles Divisas desde front
     */
    private void ejecutarOperacion() {
        log.debug("ejecutarOperacion" +
                " :: " + tipoOperacionDiasInhabiles.name() +
                " :: " + tipoOperacionDiasInhabiles.toString());
        if (this.validarOperaciones()) {
            switch (tipoOperacionDiasInhabiles.getTipo()) {
                case TIPO_OPERACION_AUTORIZA:
                    this.autorizaHistoricoDiasInhabilesDivisas();
                    break;
                case Constantes.TIPO_OPERACION_CANCELA:
                    this.cancelaHistoricoDiasInhabilesDivisas();
                    break;
                case TIPO_OPERACION_ELIMINA:
                case TIPO_OPERACION_ELIMINA_SELECCION:
                    this.eliminaDiasInhabilesDivisas();
                    break;
            }
        }
    }

    /**
     * Validación de Operacion para Días Inhábiles Divisas Firmar
     */
    private boolean validarOperaciones() {
        log.debug("ValidarOperaciones para :: "
                + tipoOperacionDiasInhabiles.name() + " :: "
                + tipoOperacionDiasInhabiles.toString());
        switch (tipoOperacionDiasInhabiles.getTipo()) {
            case TIPO_OPERACION_AUTORIZA:
                return this.usuarioPuedeAutorizar;
            case Constantes.TIPO_OPERACION_CANCELA:
                return this.usuarioPuedeCancelar;
            case TIPO_OPERACION_ELIMINA:
            case TIPO_OPERACION_ELIMINA_SELECCION:
                return this.usuarioPuedeEliminar;
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
     * Autoriza Historico Días Inhábiles Divisas
     */
    private void autorizaHistoricoDiasInhabilesDivisas() {
        log.debug("autorizaHistoricoDiasInhabilesDivisas :: " + historicoSelected.getIdHistorico());
        procesarOperacion(AUTORIZADO);
    }

    /**
     * Cancela Historico Días Inhábiles Divisas
     */
    private void cancelaHistoricoDiasInhabilesDivisas() {
        log.debug("cancelaHistoricoDiasInhabilesDivisas :: " + historicoSelected.getIdHistorico());
        procesarOperacion(CANCELADO);
    }

    /**
     * Elimina Días Inhábiles por Divisas seleccionados
     */
    private void eliminaDiasInhabilesDivisas() {
        log.debug("eliminaDiasInhabilesDivisas :: " + actualizarObjetos.size());
        procesarOperacion(ELIMINADO);
    }

    /**
     * Procesar Horarios Custodios seleccionados
     *
     * @param estadoAsignar nuevo estado a Asignar
     */
    private void procesarOperacion(EstatusDB estadoAsignar) {
        log.debug("procesarOperacion :: " + estadoAsignar.toString());
        this.validationErrors = false;
        try {
            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            String numeroSerie = params.get("numeroSerie");
            String mensajeUsuario;

            switch (tipoOperacionDiasInhabiles.getTipo()) {
                case TIPO_OPERACION_AUTORIZA:
                case TIPO_OPERACION_CANCELA:
                    String usuarioChecker = getNombreUsuarioSesion();
                    HistoricoDiasInhabilesDivisasDTO historicoDTO =
                            (HistoricoDiasInhabilesDivisasDTO) actualizarObjetos.get(0);
                    this.diasInhabilesDivisasService.actualizarHistorico(
                            historicoDTO.getIdHistorico(), estadoAsignar.getCodigo(), usuarioChecker);
                    break;
                case TIPO_OPERACION_ELIMINA:
                case TIPO_OPERACION_ELIMINA_SELECCION:
                    for (Object dto : actualizarObjetos) {
                        DiasInhabilesDivisasDTO diaInhabilDTO = (DiasInhabilesDivisasDTO) dto;
                        this.diasInhabilesDivisasService.actualizarDiaInhabil(
                                diaInhabilDTO.getIdDiasInhabiles(), estadoAsignar.getCodigo());
                    }
                    break;
            }

            totalOperaciones = isosSinFirmar.size();

            String proceso = (totalOperaciones > 1) ? (totalOperaciones + " Operaciones ") : "Operaci\u00f3n ";

            if (ID_ESTADO_AUTORIZADO == estadoAsignar.getCodigo()) {
                proceso += "de Autorizaci\u00f3n";
            } else if (ID_ESTADO_CANCELADO == estadoAsignar.getCodigo()) {
                proceso += "de Cancelaci\u00f3n";
            } else if (ID_ESTADO_ELIMINADO == estadoAsignar.getCodigo()) {
                proceso += "de Eliminaci\u00f3n";
            } else {
                proceso += "unknown";
            }

            mensajeUsuario = proceso + " realizada" + ((totalOperaciones > 1) ? "s" : "") + " con \u00E9xito.";
            ejecutarConsulta();
            this.addNotificacionInfo(mensajeUsuario);

        } catch (Exception ex) {
            log.error(ex.toString(), ex);
            this.validationErrors = true;
            this.addNotificacionError(ex.getMessage());
        }
        inicializaIso();
    }

    // </editor-fold>

    /**
     * Actualiza los valores del check para eliminar
     */
    public void checkAllOperEliminar(ActionEvent event) {
        log.debug(event.toString());
        this.inicializaIso();
        if (!this.paginaVO.getRegistros().isEmpty()) {
            for (Object objDTO : this.paginaVO.getRegistros()) {
                DiasInhabilesDivisasDTO dto = (DiasInhabilesDivisasDTO) objDTO;
                if (dto.getEstatus().intValue() == REGISTRADO.getCodigo()) {
                    dto.setSeleccionadoEliminar(false);
                }
            }
        }
    }


    // <editor-fold defaultstate="collapsed" desc="Sección de validación para Carga de Firmas">

    /**
     * Valida lo necesario para proseguir con la Autorización  del Historico seleccionado
     */
    public String cargaFirmaAutorizacion(ActionEvent event) {
        log.info("Validar Autorizar :: " + AUTORIZA.toString());
        log.debug(event.toString());
        cargaFirmaOperacion(AUTORIZA);
        return null;
    }

    /**
     * Valida lo necesario para proseguir con la Cancelación del Historico seleccionado
     */
    public String cargaFirmaCancelacion(ActionEvent event) {
        log.info("Validar Cancelar Seleccion :: " + CANCELAR.toString());
        log.debug(event.toString());
        cargaFirmaOperacion(CANCELAR);
        return null;
    }

    /**
     * Valida lo necesario para proseguir con Eliminación de Días Inhabiles por Divisa seleccionadas
     */
    public String cargaFirmaEliminarSeleccion(ActionEvent event) {
        log.info("Validar Eliminar Seleccion :: " + ELIMINA_SELECCION.toString());
        log.debug(event.toString());
        cargaFirmaOperacion(ELIMINA_SELECCION);
        return "";
    }

    /**
     * Valida lo necesario para proseguir con Eliminación de Día Inhabil por Divisa seleccionada
     */
    public String cargaFirmaEliminacion(ActionEvent event) {
        log.info("Validar Eliminacion :: " + ELIMINA.toString());
        log.debug(event.toString());
        cargaFirmaOperacion(ELIMINA);
        return "";
    }

    /**
     * Valida lo necesario para proseguir con procesamientos de Días Inhábiles por Divisas
     * FacultadFirma
     * CargaDíasInhábiles
     * ValidarOperaciones
     * CargaFirmas
     */
    private void cargaFirmaOperacion(TipoOperacionChecker tipoOperacionDiasInhabiles) {
        log.info("firmarOperacion para :: "
                + tipoOperacionDiasInhabiles.name() + " :: "
                + tipoOperacionDiasInhabiles.toString());
        try {
            this.tipoOperacionDiasInhabiles = tipoOperacionDiasInhabiles;
            if (this.validarFacultadFirmar()) {
                this.esTipo = this.tipoOperacionDiasInhabiles.getOperacion();
                log.debug(this.tipoOperacionDiasInhabiles.name() + " :: "
                        + this.tipoOperacionDiasInhabiles.toString());
                actualizarObjetos = this.cargarObjects();
                if (actualizarObjetos == null || actualizarObjetos.isEmpty()) {
                    agregarMensajeWarn("No hay Horarios Custodios seleccionados para procesar");
                } else {
                    log.debug("Carga Firma(s)");
                    this.cargaFirma();
                }
            }
        } catch (Exception ex) {
            log.error(ex.toString(), ex);
            this.validationErrors = true;
            this.addNotificacionError(ex.getMessage());
        }
    }

    /**
     * Valida la facultad del usuario para la firma de operaciones
     */
    private boolean validarFacultadFirmar() {
        this.validationErrors = false;
        this.inicializaIso();

        if (!isUsuarioConFacultadFirmar()) {
            log.debug("El usuario NO cuenta con la facultad de firmar");
            log.debug(MENSAJE_FACULTAD_FIRMA_DIGITAL_REQUERIDA);
            this.addNotificacionError(MENSAJE_FACULTAD_FIRMA_DIGITAL_REQUERIDA);
            this.validationErrors = true;
            this.usuarioPuedeAutorizar = false;
            this.usuarioPuedeCancelar = false;
            return false;
        } else {
            log.debug("El usuario SI cuenta con la facultad de firmar");
            return true;
        }
    }

    /**
     * Carga Objetos de Días Inhábiles por Divisa a procesar
     */
    private List<Object> cargarObjects() {
        log.info("Cargar de Objetos a procesar :: " + tipoOperacionDiasInhabiles.name());
        List<Object> objects = new ArrayList<>();

        if (tipoOperacionDiasInhabiles.getTipo().equals(TIPO_OPERACION_AUTORIZA)
                || tipoOperacionDiasInhabiles.getTipo().equals(Constantes.TIPO_OPERACION_CANCELA)) {
            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            this.idHistoricoStrSelected = Long.valueOf(params.get(ID_HISTORICO_STR));
            log.debug("Cargar solo un Historico [" + idHistoricoStrSelected + "]" +
                    " para :: " + tipoOperacionDiasInhabiles.getOperacion());
            objects.add(getHistoricoById(this.idHistoricoStrSelected));
        } else if (tipoOperacionDiasInhabiles.getTipo().equals(TIPO_OPERACION_ELIMINA)) {
            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            this.idDiaInhabilStrSelected = Long.valueOf(params.get(ID_DIA_INHABIL_STR));
            log.debug("Cargar solo un Día Inhábil [" + idDiaInhabilStrSelected + "]" +
                    " para :: " + tipoOperacionDiasInhabiles.getOperacion());
            objects.add(getDiaInhabilById(this.idDiaInhabilStrSelected));
        } else {
            for (Object objDTO : this.paginaVO.getRegistros()) {
                DiasInhabilesDivisasDTO diaInhabilDTO = (DiasInhabilesDivisasDTO) objDTO;

                log.debug(diaInhabilDTO.toString());

                log.debug(diaInhabilDTO.isSeleccionadoEliminar()
                        + " :: " + tipoOperacionDiasInhabiles.getTipo() + " VS. " + TIPO_OPERACION_ELIMINA_SELECCION
                        + " :: " + tipoOperacionDiasInhabiles.getTipo().equals(TIPO_OPERACION_ELIMINA_SELECCION));

                if ((diaInhabilDTO.isSeleccionadoEliminar()
                        && tipoOperacionDiasInhabiles.getTipo().equals(TIPO_OPERACION_ELIMINA_SELECCION))) {
                    log.debug("Cargar Dias Inhabiles por Divisa [" + diaInhabilDTO.getIdDiasInhabiles() + "]" +
                            " para :: " + tipoOperacionDiasInhabiles.getOperacion());
                    objects.add(diaInhabilDTO);
                }
            }
        }
        log.debug("Total de Objetos cargados :: " + objects.size());

        return objects;
    }

    /**
     * Obtiene el Día Inhabil seleccionado desde front
     */
    private DiasInhabilesDivisasDTO getDiaInhabilById(Long idDiaInhabil) {
        log.debug("getDiaInhabilById :: " + idDiaInhabil);
        for (Object objVO : this.paginaVO.getRegistros()) {
            DiasInhabilesDivisasDTO dto = (DiasInhabilesDivisasDTO) objVO;
            if (idDiaInhabil.equals(dto.getIdDiasInhabiles())) {
                log.debug(dto.toString());
                return dto;
            }
        }
        return null;
    }

    /**
     * Obtiene el Histórico seleccionado desde front
     */
    private HistoricoDiasInhabilesDivisasDTO getHistoricoById(Long idHistorico) {
        log.debug("getHistoricoById :: " + idHistorico);
        for (HistoricoDiasInhabilesDivisasDTO dto : this.historicos) {
            if (idHistorico.equals(dto.getIdHistorico())) {
                this.historicoSelected = dto;
                log.debug(dto.toString());
                return dto;
            }
        }

        return null;
    }

    /**
     * Carga de información de Horarios Custodios a Firmar
     */
    private void cargaFirma() {
        log.debug("Es momento de cargar la información para firmar");
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String numeroSerie = params.get("numeroSerie");

        List<String> elements = new ArrayList<>();

        switch (tipoOperacionDiasInhabiles.getTipo()) {
            case TIPO_OPERACION_AUTORIZA:
            case TIPO_OPERACION_CANCELA:
                HistoricoDiasInhabilesDivisasDTO historicoDTO =
                        (HistoricoDiasInhabilesDivisasDTO) actualizarObjetos.get(0);
                elements.add(historicoDTO.toDetalleString());
                break;
            case TIPO_OPERACION_ELIMINA:
            case TIPO_OPERACION_ELIMINA_SELECCION:
                for (Object dto : actualizarObjetos) {
                    DiasInhabilesDivisasDTO diaInhabilDTO = (DiasInhabilesDivisasDTO) dto;
                    elements.add(diaInhabilDTO.toDetalleString());
                }
                break;
        }


        for (String element : elements) {
            String isoElem = element;
            isoElem += '\n';
            if (isoElem != null) {
                isosSinFirmar.add(isoElem);
                hashIsos.add(cdb.cipherHash(isoElem));
            }
        }
        totalOperaciones = isosSinFirmar.size();
    }

    // </editor-fold>


    // <editor-fold defaultstate="collapsed" desc="Sección de carga de Días Inhábiles por Divisas">

    /**
     * Carga de información de Días Inhábiles de Divisas por Histórico
     */
    public String cargaDiasInhabilesDivisas(ActionEvent event) {
        log.debug(event.toString());
        cargaDiasInhabilesDivisas();
        return null;
    }

    public String cargaDiasInhabilesDivisas() {
        log.info("cargaDiasInhabilesDivisas");
        log.debug("idHistoricoStrSelected :: " + this.idHistoricoSelected);
        if(this.idHistoricoSelected!=null){
            getHistoricoById(this.idHistoricoSelected);
        }
        cargaDiasInhabilesDivisasPaginaVO();
        return null;
    }

    private void cargaDiasInhabilesDivisasPaginaVO() {
        log.debug("cargaDiasInhabilesDivisasPaginaVO :: idHistorico [" + this.idHistoricoSelected + "]");
        consultaEjecutada = false;
        if (historicoSelected != null) {
            if (this.paginaVO == null) inicializarPaginaVO();
            this.historicoSelected = getHistoricoById(this.idHistoricoSelected);
            PaginaVO resultados = diasInhabilesDivisasService.getDiasInhabilesByIdHistorico(this.idHistoricoSelected, this.paginaVO);
            this.paginaVO.setTotalRegistros(resultados.getTotalRegistros());
            this.paginaVO.setRegistros(resultados.getRegistros());

            totalPaginas = this.paginaVO.getTotalRegistros() / this.paginaVO.getRegistrosXPag();

            if (this.paginaVO.getTotalRegistros() % this.paginaVO.getRegistrosXPag() > 0) totalPaginas++;

            totalPaginas = (totalPaginas <= 0) ? 1 : totalPaginas;

            log.debug("Total de Páginas : " + totalPaginas);
            log.debug("Total de Registros: " + this.paginaVO.getRegistros().size());
            consultaEjecutada = true;
        }
    }

    // </editor-fold>


    /**
     * M&eacute;todo base para poder ejecutar la consulta
     */
    @Override
    public String ejecutarConsulta() {
        if (tipoOperacionDiasInhabiles != null) {
            if (tipoOperacionDiasInhabiles.getTipo().intValue() == TIPO_OPERACION_AUTORIZA
                    || tipoOperacionDiasInhabiles.getTipo().intValue() == TIPO_OPERACION_CANCELA) {
                cargaHistoricos();
            } else {
                cargaDiasInhabilesDivisasPaginaVO();
            }
        } else {
            cargaDiasInhabilesDivisasPaginaVO();
        }
        return null;
    }


    public String obtenerEstado(Integer estado) {
        return EstatusDB.obtenerEstado(estado).getDescripcion();
    }

    /**
     * Genera los reportes de Consulta de Emisiones
     *
     * @param evt Evento lanzado por cualquier botón para exportar reportes
     */
    public void generarReportes(ActionEvent evt) {
        log.debug(evt.toString());

        reiniciarEstadoPeticion();
        this.resultados = new PaginaVO();
        this.resultados.setOffset(0);
        this.resultados.setRegistrosXPag(PaginaVO.TODOS);

        PaginaVO resultadosReporte = diasInhabilesDivisasService.getDiasInhabilesByIdHistorico(
                this.idHistoricoSelected, this.resultados);

        this.resultados.setRegistros(resultadosReporte.getRegistros());
        this.resultados.setTotalRegistros(resultadosReporte.getRegistros().size());
    }


    // <editor-fold defaultstate="collapsed" desc="Seccion getters and setters.">


    /**
     * @return the pagina
     */
    public PaginaVO getPagina() {
        return pagina;
    }

    /**
     * @param pagina the pagina to set
     */
    public void setPagina(PaginaVO pagina) {
        this.pagina = pagina;
    }

    /**
     * @return the mensajeError
     */
    public String getMensajeError() {
        return mensajeError;
    }

    /**
     * @param mensajeError the mensajeError to set
     */
    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }

    public Integer getAnioSeleccionado() {
        return anioSeleccionado;
    }

    public void setAnioSeleccionado(Integer anioSeleccionado) {
        this.anioSeleccionado = anioSeleccionado;
    }

    public DiasInhabilesDivisasService getDiasInhabilesDivisasService() {
        return diasInhabilesDivisasService;
    }

    public void setDiasInhabilesDivisasService(DiasInhabilesDivisasService diasInhabilesDivisasService) {
        this.diasInhabilesDivisasService = diasInhabilesDivisasService;
    }

    public List<HistoricoDiasInhabilesDivisasDTO> getHistoricos() {
        return historicos;
    }

    public void setHistoricos(List<HistoricoDiasInhabilesDivisasDTO> historicos) {
        this.historicos = historicos;
    }

    public List<Object> getActualizarObjetos() {
        return actualizarObjetos;
    }

    public void setActualizarObjetos(List<Object> actualizarObjetos) {
        this.actualizarObjetos = actualizarObjetos;
    }

    public Long getIdHistoricoStrSelected() {
        return idHistoricoStrSelected;
    }

    public void setIdHistoricoStrSelected(Long idHistoricoStrSelected) {
        this.idHistoricoStrSelected = idHistoricoStrSelected;
    }

    public Long getIdHistoricoSelected() {
        return idHistoricoSelected;
    }

    public void setIdHistoricoSelected(Long idHistoricoSelected) {
        this.idHistoricoSelected = idHistoricoSelected;
    }

    public Long getIdDiaInhabilStrSelected() {
        return idDiaInhabilStrSelected;
    }

    public void setIdDiaInhabilStrSelected(Long idDiaInhabilStrSelected) {
        this.idDiaInhabilStrSelected = idDiaInhabilStrSelected;
    }


    @Override
    public int getTotalPaginas() {
        return totalPaginas;
    }

    @Override
    public void setTotalPaginas(int totalPaginas) {
        this.totalPaginas = totalPaginas;
    }

    public String getEsTipo() {
        return esTipo;
    }

    public void setEsTipo(String esTipo) {
        this.esTipo = esTipo;
    }

    public boolean isValidationErrors() {
        return validationErrors;
    }

    public void setValidationErrors(boolean validationErrors) {
        this.validationErrors = validationErrors;
    }

    public int getTotalOperaciones() {
        return totalOperaciones;
    }

    public void setTotalOperaciones(int totalOperaciones) {
        this.totalOperaciones = totalOperaciones;
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

    public boolean isUsuarioPuedeEliminar() {
        return usuarioPuedeEliminar;
    }

    public void setUsuarioPuedeEliminar(boolean usuarioPuedeEliminar) {
        this.usuarioPuedeEliminar = usuarioPuedeEliminar;
    }

    public boolean isCheckAllEliminar() {

        return checkAllEliminar;
    }

    public void setCheckAllEliminar(boolean checkAllEliminar) {

        this.checkAllEliminar = checkAllEliminar;
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

    public HistoricoDiasInhabilesDivisasDTO getHistoricoSelected() {
        return historicoSelected;
    }

    public void setHistoricoSelected(HistoricoDiasInhabilesDivisasDTO historicoSelected) {
        this.historicoSelected = historicoSelected;
    }

    public PaginaVO getResultados() {
        return resultados;
    }

    public void setResultados(PaginaVO resultados) {
        this.resultados = resultados;
    }

    public String getOPERACION() {
        return OPERACION;
    }

    public void setOPERACION(String OPERACION) {
        this.OPERACION = OPERACION;
    }

    public boolean isConsultaEjecutada() {
        return consultaEjecutada;
    }

    public void setConsultaEjecutada(boolean consultaEjecutada) {
        this.consultaEjecutada = consultaEjecutada;
    }

    // </editor-fold>

}