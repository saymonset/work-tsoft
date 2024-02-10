/**
 * Sistema Internacional de Cotizaciones
 * 
 * Bursatec - 2H Software SA de CV
 *
 * Creado: Oct 29, 2008
 */
package com.indeval.portalinternacional.presentation.controller.fideicomiso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.faces.event.ActionEvent;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.services.util.ConsultaCatalogoService;
import com.indeval.portaldali.middleware.services.util.DateUtilService;
import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.EmisionVO;
import com.indeval.portaldali.persistence.modelo.Institucion;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaFideicomisosVO;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;

/**
 * Controller para la consulta de Fideicomiso de la institucion actual
 * 
 * @author Erik Vera Montoya.
 * @version 1.0
 * 
 */
public class FideicomisoInstitucionActualController extends ControllerBase {

    /** Servicio para la consulta de fideicomiso */
    private DivisionInternacionalService divisionInternacionalService;
    /** Servicio utilitario para obtener las fechas */
    private DateUtilService dateUtilService;
    /** Indica la fecha de la consulta */
    private Date fechaConsulta;
    /** Indica las cuentas */
    private String cuentas;
    /** Tipo FIDEI */
    private String tipo1;
    /** Bandera que define el estado de la consulta */
    private boolean consultaEjecutada;
    /** Indice de la emision actual para su navegacion */
    private int indiceEmisionActual = 0;
    /** Numero del total de emisiones encontradas */
    private int totalEmisiones = 0;
    /** emision actual dentro de la navegacion */
    private EmisionVO emisionActual;
    /**Bandera para la consulta de historico*/
    private Boolean historico;
    private String idFolioInstitucion;
    //nuevos Objetos para el cambio de firma
    private List<ConsultaFideicomisosVO> listaFideicomisos;
    /**Agente Actual de Fideicomiso*/
    private ConsultaFideicomisosVO agenteFideicomisoActual;
    private ConsultaCatalogoService consultaCatService;
    /** Log de clase. */
    private static final Logger log = LoggerFactory.getLogger(FideicomisoInbursaController.class);

    /**
     * Ejecuta las actividades necesarias de inicialización de la pantalla.
     *
     * @return <code>null</code>. No es necesario un valor dev retorno.
     */
    public String getInit() {
        indiceEmisionActual = 0;
        consultaEjecutada = false;
        if (isInstitucionIndeval()) {
            idFolioInstitucion = null;
            obtenerDatosParticipante(null);
        } else {
            idFolioInstitucion = getAgenteFirmado().getClave();
            obtenerDatosParticipante(null);
        }

        cuentas = "97,98,54";
        fechaConsulta = dateUtilService.getCurrentDate();
        historico = Boolean.FALSE;

        listaFideicomisos = new ArrayList<ConsultaFideicomisosVO>();
        agenteFideicomisoActual = new ConsultaFideicomisosVO();
        //inicializacion de los objetos que se iteran en la pantalla
        emisionActual = new EmisionVO();

        return null;
    }

    public void obtenerDatosParticipante(ActionEvent event) {
        Institucion institucion = null;
        tipo1 = "FIDEI. No Reconocido";
        if (StringUtils.isNotBlank(idFolioInstitucion)) {
            institucion = consultaCatService.findInstitucionByClaveFolio(idFolioInstitucion);
            if (institucion != null
                    && (StringUtils.equals(Constantes.NAFINSA, institucion.getClaveInstitucion())
                    || StringUtils.equals(Constantes.BANAMEX, institucion.getClaveInstitucion())
                    || StringUtils.equals(Constantes.INBURSA, institucion.getClaveInstitucion()))) {
                tipo1 = "FIDEI. " + institucion.getNombreCorto();
            }
        }
    }

    /**
     * Invoca la consulta principal de la pantalla
     *
     * @param e
     *            Evento generado durante la solicitud
     */
    public void obtenerArqueos(ActionEvent e) {
        String atributo = (String) e.getComponent().getAttributes().get("totalArqueo");
        if ("totalArqueo".equals(atributo)) {
            indiceEmisionActual = 0;
        }
        try {
            ejecutarConsulta();
            consultaEjecutada = true;
        } catch (BusinessException exc) {
            log.error("Ocurrio un error:",e);
            agregarMensaje(exc);
        } catch (Exception exc) {
            log.error("Ocurrio un error:",exc);
            agregarMensaje(exc);

        } catch (Throwable exc) {
            log.error("Ocurrio un error:",exc);
            agregarMensaje(exc);
        }
    }

    /**
     * método que limpia la pantalla de Fideicomiso Banamex
     *
     * @param e
     */
    public void limpiar(ActionEvent e) {
        consultaEjecutada = false;
        getInit();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.indeval.portalinternacional.presentation.controller.common.ControllerBase#ejecutarConsulta()
     */
    @Override
    public String ejecutarConsulta() {
        ejecutaConsultaEmisionFideicomiso();
        return null;
    }

    /**
     * Avanza hacia la siguiente Emision
     *
     * @param ev
     */
    public void emisionSiguiente(ActionEvent ev) {
        try {
            indiceEmisionActual++;
            ejecutaConsultaEmisionFideicomiso();

        } catch (BusinessException e) {
            agregarMensaje(e);
        }
    }

    /**
     * Avanza hacia la Emision anterior
     *
     * @param ev
     */
    public void emisionAnterior(ActionEvent ev) {
        try {
            indiceEmisionActual--;
            ejecutaConsultaEmisionFideicomiso();
        } catch (BusinessException e) {
            agregarMensaje(e);
        }
    }

    /**
     * Obtiene la lista de Emisiones para los fideicomisos a partir de un agente
     * y la fecha de consulta
     *
     */
    private void ejecutaConsultaEmisionFideicomiso() {
        AgenteVO agente = null;
        if (StringUtils.isNotBlank(idFolioInstitucion)) {
            Institucion institucion = consultaCatService.findInstitucionByClaveFolio(idFolioInstitucion);
            if (institucion != null) {
                agente = new AgenteVO();
                agente.setId(institucion.getTipoInstitucion().getClaveTipoInstitucion());
                agente.setFolio(institucion.getFolioInstitucion());
                agente.setNombreCorto(institucion.getNombreCorto());
            }
        }
        listaFideicomisos = divisionInternacionalService.consultaFideicomisos(agente, fechaConsulta);
        totalEmisiones = listaFideicomisos.size();

        if (totalEmisiones > 0) {
            if (indiceEmisionActual >= totalEmisiones) {
                indiceEmisionActual = totalEmisiones - 1;
            }
            if (indiceEmisionActual < 0) {
                indiceEmisionActual = 0;
            }
            agenteFideicomisoActual = listaFideicomisos.get(indiceEmisionActual);
            if (agenteFideicomisoActual != null && agenteFideicomisoActual.getEmisionVO() != null) {
                emisionActual = agenteFideicomisoActual.getEmisionVO();
                Date fechaActual = dateUtilService.getCurrentDate();
                fechaActual = getFechaHoraCero(fechaActual);
                fechaConsulta = getFechaHoraCero(fechaConsulta);

                //validar el historico
                if (fechaConsulta.compareTo(fechaActual) == 0) {
                    historico = false;
                } else {
                    historico = true;
                }
            }
        } else {
            emisionActual = null;
            indiceEmisionActual = -1;
        }


    }

    /**
     * Toma en cuenta el d&iacute;a, mes y ao y regres el objeto de referencia modificado
     * @param fechaParam
     * @return fechaParam
     */
    public Date getFechaHoraCero(Date fechaParam) {
        Date fechaReturn = new Date();
        if (fechaParam != null) {
            Calendar calFecha = new GregorianCalendar();
            calFecha.setTime(fechaParam);
            int year = calFecha.get(Calendar.YEAR);
            int month = calFecha.get(Calendar.MONTH);
            int day = calFecha.get(Calendar.DAY_OF_MONTH);
            Calendar cal = new GregorianCalendar(year, month, day, 0, 0, 0);
            fechaReturn = cal.getTime();
        }

        return fechaReturn;
    }

    /**
     * M&eacute;todo que genera los reportes en PDF o XLS
     *
     * @param El
     *            action listener que lo invoca
     */
    public void generarReportes(ActionEvent e) {
    	reiniciarEstadoPeticion();
        historico = false;
        ejecutarConsulta();
    }

    /**
     * Obtiene el valor del atributo divisionInternacionalService
     *
     * @return el valor del atributo divisionInternacionalService
     */
    public DivisionInternacionalService getDivisionInternacionalService() {
        return divisionInternacionalService;
    }

    /**
     * Establece el valor del atributo divisionInternacionalService
     *
     * @param divisionInternacionalService
     *            el valor del atributo divisionInternacionalService a
     *            establecer
     */
    public void setDivisionInternacionalService(
            DivisionInternacionalService divisionInternacionalService) {
        this.divisionInternacionalService = divisionInternacionalService;
    }

    /**
     * Obtiene el valor del atributo fechaConsulta
     *
     * @return el valor del atributo fechaConsulta
     */
    public Date getFechaConsulta() {
        return fechaConsulta;
    }

    /**
     * Establece el valor del atributo fechaConsulta
     *
     * @param fechaConsulta
     *            el valor del atributo fechaConsulta a establecer
     */
    public void setFechaConsulta(Date fechaConsulta) {
        this.fechaConsulta = fechaConsulta;
    }

    /**
     * Obtiene el valor del atributo cuentas
     *
     * @return el valor del atributo cuentas
     */
    public String getCuentas() {
        return cuentas;
    }

    /**
     * Establece el valor del atributo cuentas
     *
     * @param cuentas
     *            el valor del atributo cuentas a establecer
     */
    public void setCuentas(String cuentas) {
        this.cuentas = cuentas;
    }

    /**
     * Obtiene el valor del atributo tipo1
     *
     * @return el valor del atributo tipo1
     */
    public String getTipo1() {
        return tipo1;
    }

    /**
     * Establece el valor del atributo tipo1
     *
     * @param tipo1
     *            el valor del atributo tipo1 a establecer
     */
    public void setTipo1(String tipo1) {
        this.tipo1 = tipo1;
    }

    /**
     * Obtiene el valor del atributo consultaEjecutada
     *
     * @return el valor del atributo consultaEjecutada
     */
    public boolean isConsultaEjecutada() {
        return consultaEjecutada;
    }

    /**
     * Establece el valor del atributo consultaEjecutada
     *
     * @param consultaEjecutada
     *            el valor del atributo consultaEjecutada a establecer
     */
    public void setConsultaEjecutada(boolean consultaEjecutada) {
        this.consultaEjecutada = consultaEjecutada;
    }

    /**
     * Obtiene el valor del atributo dateUtilService
     *
     * @return el valor del atributo dateUtilService
     */
    public DateUtilService getDateUtilService() {
        return dateUtilService;
    }

    /**
     * Establece el valor del atributo dateUtilService
     *
     * @param dateUtilService
     *            el valor del atributo dateUtilService a establecer
     */
    public void setDateUtilService(DateUtilService dateUtilService) {
        this.dateUtilService = dateUtilService;
    }

    /**
     * Obtiene el valor del atributo indiceEmisionActual
     *
     * @return el valor del atributo indiceEmisionActual
     */
    public int getIndiceEmisionActual() {
        return indiceEmisionActual;
    }

    /**
     * Establece el valor del atributo indiceEmisionActual
     *
     * @param indiceEmisionActual
     *            el valor del atributo indiceEmisionActual a establecer
     */
    public void setIndiceEmisionActual(int indiceEmisionActual) {
        this.indiceEmisionActual = indiceEmisionActual;
    }

    /**
     * Obtiene el valor del atributo totalEmisiones
     *
     * @return el valor del atributo totalEmisiones
     */
    public int getTotalEmisiones() {
        return totalEmisiones;
    }

    /**
     * Establece el valor del atributo totalEmisiones
     *
     * @param totalEmisiones
     *            el valor del atributo totalEmisiones a establecer
     */
    public void setTotalEmisiones(int totalEmisiones) {
        this.totalEmisiones = totalEmisiones;
    }

    /**
     * Obtiene el valor del atributo emisionActual
     *
     * @return el valor del atributo emisionActual
     */
    public EmisionVO getEmisionActual() {
        return emisionActual;
    }

    /**
     * Establece el valor del atributo emisionActual
     *
     * @param emisionActual
     *            el valor del atributo emisionActual a establecer
     */
    public void setEmisionActual(EmisionVO emisionActual) {
        this.emisionActual = emisionActual;
    }

    /**
     * Obtiene el valor del atributo historico
     *
     * @return el valor del atributo historico
     */
    public Boolean getHistorico() {
        return historico;
    }

    /**
     * Establece el valor del atributo historico
     *
     * @param historico el valor del atributo historico a establecer
     */
    public void setHistorico(Boolean historico) {
        this.historico = historico;
    }

    /**
     * Obtiene el valor del atributo listaFideicomisos
     *
     * @return el valor del atributo listaFideicomisos
     */
    public List<ConsultaFideicomisosVO> getListaFideicomisos() {
        return listaFideicomisos;
    }

    /**
     * Establece el valor del atributo listaFideicomisos
     *
     * @param listaFideicomisos el valor del atributo listaFideicomisos a establecer
     */
    public void setListaFideicomisos(List<ConsultaFideicomisosVO> listaFideicomisos) {
        this.listaFideicomisos = listaFideicomisos;
    }

    /**
     * Obtiene el valor del atributo agenteFideicomisoActual
     *
     * @return el valor del atributo agenteFideicomisoActual
     */
    public ConsultaFideicomisosVO getAgenteFideicomisoActual() {
        return agenteFideicomisoActual;
    }

    /**
     * Establece el valor del atributo agenteFideicomisoActual
     *
     * @param agenteFideicomisoActual el valor del atributo agenteFideicomisoActual a establecer
     */
    public void setAgenteFideicomisoActual(
            ConsultaFideicomisosVO agenteFideicomisoActual) {
        this.agenteFideicomisoActual = agenteFideicomisoActual;
    }

    public String getIdFolioInstitucion() {
        return idFolioInstitucion;
    }

    public void setIdFolioInstitucion(String idFolioInstitucion) {
        this.idFolioInstitucion = idFolioInstitucion;
    }

    public ConsultaCatalogoService getConsultaCatService() {
        return consultaCatService;
    }

    public void setConsultaCatService(ConsultaCatalogoService consultaCatService) {
        this.consultaCatService = consultaCatService;
    }
}
