/**
 * Multidivisas: Días Inhábiles de Divisas :: Principal
 */
package com.indeval.portalinternacional.presentation.controller.diasInhabilesDivisas;

import com.indeval.portalinternacional.middleware.services.divisioninternacional.DiasInhabilesDivisasService;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import java.util.ArrayList;
import java.util.List;

import static com.indeval.portalinternacional.middleware.servicios.constantes.Constantes.ROL_INT_AUTORIZA_DIAS_DIVISA;
import static com.indeval.portalinternacional.middleware.servicios.constantes.Constantes.ROL_INT_OPERADOR_DIAS_DIVISA;
import static com.indeval.portalinternacional.presentation.controller.diasInhabilesDivisas.TipoOperacion.CONSULTAR;
import static com.indeval.portalinternacional.presentation.controller.diasInhabilesDivisas.TipoOperacion.REGITRAR;


public class DiasInhabilesDivisasController extends ControllerBase {

    /**
     * Log de clase.
     */
    private static final Logger log = LoggerFactory.getLogger(DiasInhabilesDivisasController.class);

    private SelectItem DEFAULT = new SelectItem(-1, "Seleccione");
    private final SelectItem TODOS = new SelectItem(-1, "TODOS");

    private DiasInhabilesDivisasService diasInhabilesDivisasService;

    private String paginaSeleccionada = "diasInhabilesDivisasInicio";
    private List<SelectItem> listaOperaciones;
    private Integer operacionSeleccionada;
    private List<SelectItem> listaAnios;
    private Integer anioSeleccionado;
    private boolean usuarioPuedeRegistrar = false;

    ////////////////////////////////////////////Seccion de Metodos ////////////////////////////////////////////

    // <editor-fold defaultstate="collapsed" desc="Sección de Inicializadores">

    /**
     * Recupera la informaci&oacute;n del proceso que est&aacute; actualmente corriendo para mostrar la pantalla por primera vez.
     *
     * @return null
     */
    public String getInit() {
        inicializaPermisos();
        inicializarCriterios();
        return null;
    }

    /**
     * Inicializa criterios de consulta
     */
    public void inicializarCriterios() {
        this.listaOperaciones = new ArrayList<>();
        this.listaOperaciones.add(DEFAULT);
        if (usuarioPuedeRegistrar) {
            this.listaOperaciones.add(new SelectItem(REGITRAR.getTipo(), REGITRAR.getOperacion()));
        }
        this.listaOperaciones.add(new SelectItem(CONSULTAR.getTipo(), CONSULTAR.getOperacion()));

        this.operacionSeleccionada = (Integer) DEFAULT.getValue();

        List<Integer> aniosDisponibles = diasInhabilesDivisasService.getAniosDisponibles();
        this.listaAnios = new ArrayList<>();
        this.listaAnios.add(TODOS);
        for (Integer anio : aniosDisponibles) {
            this.listaAnios.add(new SelectItem(anio, anio.toString()));
        }
        this.anioSeleccionado = (Integer) TODOS.getValue();

    }

    /**
     * Verifica si el usuario cuenta con los permisos para Registrar Historico de Días Inhábiles de Divisas
     */
    private void inicializaPermisos() {
        log.info("validarPermisos "
                + ":: INT_AUTORIZA_DIAS_DIVISA [" + ROL_INT_AUTORIZA_DIAS_DIVISA + "] "
                + ":: INT_OPERADOR_DIAS_DIVISA [" + ROL_INT_OPERADOR_DIAS_DIVISA + "]");
        this.usuarioPuedeRegistrar = isUserInRoll(ROL_INT_OPERADOR_DIAS_DIVISA);
    }


    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Seleccion de Página">
    public String actualizarPaginaSeleccion(ActionEvent event) {
        log.debug(event.toString());
        return actualizarPaginaSeleccion();
    }

    public String actualizarPaginaSeleccion() {
        log.debug("Actualizar Pagina Seleccion");
        this.paginaSeleccionada = "diasInhabilesDivisasInicio";
        if (this.operacionSeleccionada != null) {
            if (this.operacionSeleccionada.equals(REGITRAR.getTipo())) {
                log.debug("Carga pagina :: " + REGITRAR.toString());
                this.operacionSeleccionada = REGITRAR.getTipo();
                this.paginaSeleccionada = "diasInhabilesDivisasRegistro";
            } else if (this.operacionSeleccionada.equals(CONSULTAR.getTipo())) {
                log.debug("Carga pagina :: " + CONSULTAR.toString());
                this.operacionSeleccionada = CONSULTAR.getTipo();
                this.paginaSeleccionada = "diasInhabilesDivisasConsulta";
            }
        }
        return null;
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Seccion getters and setters.">

    public DiasInhabilesDivisasService getDiasInhabilesDivisasService() {
        return diasInhabilesDivisasService;
    }

    public void setDiasInhabilesDivisasService(DiasInhabilesDivisasService diasInhabilesDivisasService) {
        this.diasInhabilesDivisasService = diasInhabilesDivisasService;
    }

    public String getPaginaSeleccionada() {
        return paginaSeleccionada;
    }

    public void setPaginaSeleccionada(String paginaSeleccionada) {
        this.paginaSeleccionada = paginaSeleccionada;
    }

    public List<SelectItem> getListaOperaciones() {
        return listaOperaciones;
    }

    public void setListaOperaciones(List<SelectItem> listaOperaciones) {
        this.listaOperaciones = listaOperaciones;
    }

    public Integer getOperacionSeleccionada() {
        return operacionSeleccionada;
    }

    public void setOperacionSeleccionada(Integer operacionSeleccionada) {
        this.operacionSeleccionada = operacionSeleccionada;
    }

    public List<SelectItem> getListaAnios() {
        return listaAnios;
    }

    public void setListaAnios(List<SelectItem> listaAnios) {
        this.listaAnios = listaAnios;
    }

    public Integer getAnioSeleccionado() {
        return anioSeleccionado;
    }

    public void setAnioSeleccionado(Integer anioSeleccionado) {
        this.anioSeleccionado = anioSeleccionado;
    }

    public boolean isUsuarioPuedeRegistrar() {
        return usuarioPuedeRegistrar;
    }

    public void setUsuarioPuedeRegistrar(boolean usuarioPuedeRegistrar) {
        this.usuarioPuedeRegistrar = usuarioPuedeRegistrar;
    }


    // </editor-fold>

}