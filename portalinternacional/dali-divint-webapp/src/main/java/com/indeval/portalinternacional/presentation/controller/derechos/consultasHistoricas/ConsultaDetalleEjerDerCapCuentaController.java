/**
 * Bursatec - Portal Internacional
 * Copyrigth (c) 2016 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.presentation.controller.derechos.consultasHistoricas;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import com.indeval.portaldali.middleware.services.util.ConsultaCatalogoService;
import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.persistence.dao.common.DivisaDao;
import com.indeval.portaldali.persistence.modelo.Institucion;
import com.indeval.portalinternacional.common.util.CatalogosUtil;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.DerechosCapitalesService;
import com.indeval.portalinternacional.middleware.servicios.constantes.CatalogosConstantes;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaDetalleEjerDerCapCuentaTO;
import com.indeval.portalinternacional.middleware.servicios.vo.ParamConsultaDetalleEjerDerCapTO;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;

/**
 * Controller para la consulta de detalle de ejercicio de derechos de capital por cuenta.
 * 
 * @author Pablo Balderas
 */
public class ConsultaDetalleEjerDerCapCuentaController extends ControllerBase {

    /** Parametros de consulta */
    private ParamConsultaDetalleEjerDerCapTO parametros = null;

    /** Resultados de la consulta */
    private ConsultaDetalleEjerDerCapCuentaTO resultadosConsulta = null;

    /** Utileria para obtener los catalogos */
    private CatalogosUtil catalogosUtil;

    /** Servicio para obtener la institucion */
    private ConsultaCatalogoService consultaCatService;

    /** Servicio de derechos de capital */
    private DerechosCapitalesService derechosCapitalesService;

    /** Indica si la consulta ha sido ejecutada */
    private boolean consultaEjecutada = false;

    /** Indica si debe mostrar los parametros de busqueda */
    private boolean mostrarParametros = true;

    /** Indica si la pantalla ya esta inicializada */
    private boolean banderaInicio = false;

    /** Indica si es usuario externo */
    private boolean usuarioExterno = false;

    /** Catálogo de divisas */
    private List<SelectItem> catalogoDivisas;

    /** Catálogo de estatus de derecho */
    private List<SelectItem> catalogoEstatusDerecho;

    /** Catálogo de custodios */
    private List<SelectItem> catalogoCustodios;

    /**Dao de divisas*/
    private DivisaDao divisaDao;

    /** bandera para cobrar la consulta (las exportaciones no se cobran) */
    private  boolean cobraConsulta=false;

    private List<SelectItem> catalogoEstadoDerecho;
    private List<SelectItem> catalogoTiposDerecho;

    /**
     * Mátodo que inicializa la pantalla
     * @return null
     */
    public String getInit() {
        if(!this.banderaInicio) {
            this.consultaEjecutada = false;
            this.mostrarParametros = true;
            this.resultadosConsulta = null;
            this.parametros = new ParamConsultaDetalleEjerDerCapTO();
            this.banderaInicio = true;
            this.catalogoDivisas = this.catalogosUtil.getCatalogoDivisasDescripcion();
            this.catalogoEstatusDerecho = this.catalogosUtil.getCatalogoEstatusDerechosPagadoCortado();
            this.catalogoCustodios = this.catalogosUtil.getCatalogoCustodiosCatBic();
            this.catalogoEstadoDerecho = this.catalogosUtil.getCatalogoEstadosDerechoCapital();
            this.catalogoTiposDerecho = this.catalogosUtil.getCatalogoTipoDerecho();
            this.usuarioExterno = !this.isUserInRoll("INT_BEN_INDEVAL") && !this.isUserInRoll("INT_BEN_INDEVAL_ADMIN");
            if(this.usuarioExterno) {
                this.parametros.setInstitucion(this.getAgenteFirmado().getId() + this.getAgenteFirmado().getFolio());
                this.parametros.setNombreInstitucion(this.getAgenteFirmado().getNombreCorto());
            }
            this.paginaVO.setRegistrosXPag(20);
            this.cobraConsulta=false;
        }
        return null;
    }

    /**
     * Método que ejecuta la consulta.
     * @param event Evento generado por faces.
     */
    public void consultar(final ActionEvent event) {
        try {
            this.cobraConsulta=true;
            this.paginaVO.setOffset(0);
            this.parametros.setDivisa(this.getSelected(this.parametros.getIdDivisa(), this.catalogoDivisas));
            //this.parametros.setEstadoDerecho(this.getSelected(this.parametros.getIdEstadoDerecho(), this.catalogoEstatusDerecho));
            this.parametros.setEstadoDerecho(this.getSelected(this.parametros.getIdEstadoDerecho(), this.catalogoEstadoDerecho));
            this.parametros.setCustodio(this.getSelected(this.parametros.getIdCustodio(), this.catalogoCustodios));
            this.ejecutarConsulta();
            this.consultaEjecutada = true;
            this.mostrarParametros = false;

        }
        catch(BusinessException e) {
            this.consultaEjecutada = false;
            this.addErrorMessage(e.getMessage());
        }finally{
            this.cobraConsulta=false;
        }
    }

    /**
     * Método utilizado para la paginación de resultados.
     * @param event Evento generado por faces.
     */
    public void paginarResultados(final ActionEvent event) {
        this.ejecutarConsulta();
    }

    /**
     * Limpia todos los criterios de busqueda y los resultados previos.
     * @param event Evento generado por faces.
     */
    public void limpiar(final ActionEvent event) {
        this.banderaInicio = false;
        this.getInit();
        if( this.paginaVO != null ) {
            if(this.paginaVO.getRegistros() != null) {
                this.paginaVO.getRegistros().clear();
                this.paginaVO.setRegistros(null);
            }
            this.paginaVO.setOffset(0);
            this.paginaVO.setTotalRegistros(0);
            this.paginaVO.setRegistrosXPag(20);
        }
    }

    @SuppressWarnings("all")
    @Override
    public String ejecutarConsulta() {
        this.paginaVO.setRegistros(null);
        this.paginaVO = this.derechosCapitalesService.consultaDetalleDerechosCapitalCuenta(this.paginaVO, this.parametros, false,this.cobraConsulta);
        this.resultadosConsulta = (ConsultaDetalleEjerDerCapCuentaTO) this.paginaVO.getRegistros().get(0);
        return null;
    }

    /**
     * Obtiene los datos de la institución.
     * @param event Evento generado por faces.
     */
    public void obtenerInstitucion(final ActionEvent event) {
        Institucion institucion = null;
        if (this.parametros.getInstitucion() != null) {
            institucion = this.consultaCatService.findInstitucionByClaveFolio(this.parametros.getInstitucion());
            if (institucion != null) {
                this.parametros.setIdInstitucion(institucion.getIdInstitucion());
                this.parametros.setNombreInstitucion(institucion.getNombreCorto());
            }
        }
    }

    /**
     * Obtiene la descripción de un id dentro de una lista de select items.
     * @param key Id del valor
     * @param lista Lista a recorrer
     * @return Valor correspondiente al id
     */
    private String getSelected(final Long key, final List<SelectItem> lista){
        String resultado = null;
        for(SelectItem item : lista){
            if(key != null && item.getValue().equals(key)) {
                resultado=item.getLabel();
                break;
            }
        }
        return resultado;
    }

    /**
     * Genera el combo Fee
     * 
     * @return
     */
    public List<SelectItem> getOpcionesFee(){
        List<SelectItem> listaOpcionesFee = new ArrayList<SelectItem>();
        listaOpcionesFee.add(new SelectItem(CatalogosConstantes.ID_CON_FEE,  CatalogosConstantes.CON_FEE));
        listaOpcionesFee.add(new SelectItem(CatalogosConstantes.ID_SIN_FEE, CatalogosConstantes.SIN_FEE));
        return listaOpcionesFee;
    }

    /**
     * Metodo que retorna la opción FEE seleccionada
     * @return
     */
    public String getFeeSeleccionado() {
        String descripcion=null;
        if(this.parametros.getTipoFee() != null) {
            switch(this.parametros.getTipoFee()) {
                case 2:
                    descripcion = CatalogosConstantes.CON_FEE;
                    break;
                case 3:
                    descripcion = CatalogosConstantes.SIN_FEE;
                    break;
                default:
                    descripcion = CatalogosConstantes.AMBOS;
                    break;
            }
        }
        return descripcion;
    }

    /**
     * Indica si la consulta arrojo resultados.
     * @return true si hay resultados; false en otro caso.
     */
    public boolean isConsultaConResultados() {
        return this.resultadosConsulta != null && this.resultadosConsulta.getResultadosConsulta() != null &&
                !this.resultadosConsulta.getResultadosConsulta().isEmpty();
    }

    /**
     * Método para obtener el atributo parametros
     * @return El atributo parametros
     */
    public ParamConsultaDetalleEjerDerCapTO getParametros() {
        return this.parametros;
    }

    /**
     * Método para establecer el atributo parametros
     * @param parametros El valor del atributo parametros a establecer.
     */
    public void setParametros(final ParamConsultaDetalleEjerDerCapTO parametros) {
        this.parametros = parametros;
    }

    /**
     * Método para obtener el atributo resultadosConsulta
     * @return El atributo resultadosConsulta
     */
    public ConsultaDetalleEjerDerCapCuentaTO getResultadosConsulta() {
        return this.resultadosConsulta;
    }

    /**
     * Método para establecer el atributo resultadosConsulta
     * @param resultadosConsulta El valor del atributo resultadosConsulta a establecer.
     */
    public void setResultadosConsulta(final ConsultaDetalleEjerDerCapCuentaTO resultadosConsulta) {
        this.resultadosConsulta = resultadosConsulta;
    }

    /**
     * Método para obtener el atributo consultaEjecutada
     * @return El atributo consultaEjecutada
     */
    public boolean isConsultaEjecutada() {
        return this.consultaEjecutada;
    }

    /**
     * Método para establecer el atributo consultaEjecutada
     * @param consultaEjecutada El valor del atributo consultaEjecutada a establecer.
     */
    public void setConsultaEjecutada(final boolean consultaEjecutada) {
        this.consultaEjecutada = consultaEjecutada;
    }

    /**
     * Método para obtener el atributo banderaInicio
     * @return El atributo banderaInicio
     */
    public boolean isBanderaInicio() {
        return this.banderaInicio;
    }

    /**
     * Método para establecer el atributo banderaInicio
     * @param banderaInicio El valor del atributo banderaInicio a establecer.
     */
    public void setBanderaInicio(final boolean banderaInicio) {
        this.banderaInicio = banderaInicio;
    }

    /**
     * Método para establecer el atributo catalogosUtil
     * @param catalogosUtil El valor del atributo catalogosUtil a establecer.
     */
    public void setCatalogosUtil(final CatalogosUtil catalogosUtil) {
        this.catalogosUtil = catalogosUtil;
    }

    /**
     * Método para obtener el atributo catalogoDivisas
     * @return El atributo catalogoDivisas
     */
    public List<SelectItem> getCatalogoDivisas() {
        return this.catalogoDivisas;
    }

    /**
     * Método para establecer el atributo catalogoDivisas
     * @param catalogoDivisas El valor del atributo catalogoDivisas a establecer.
     */
    public void setCatalogoDivisas(final List<SelectItem> catalogoDivisas) {
        this.catalogoDivisas = catalogoDivisas;
    }

    /**
     * Método para obtener el atributo catalogoEstatusDerecho
     * @return El atributo catalogoEstatusDerecho
     */
    public List<SelectItem> getCatalogoEstatusDerecho() {
        return this.catalogoEstatusDerecho;
    }

    /**
     * Método para establecer el atributo catalogoEstatusDerecho
     * @param catalogoEstatusDerecho El valor del atributo catalogoEstatusDerecho a establecer.
     */
    public void setCatalogoEstatusDerecho(final List<SelectItem> catalogoEstatusDerecho) {
        this.catalogoEstatusDerecho = catalogoEstatusDerecho;
    }

    /**
     * Método para obtener el atributo catalogoCustodios
     * @return El atributo catalogoCustodios
     */
    public List<SelectItem> getCatalogoCustodios() {
        return this.catalogoCustodios;
    }

    /**
     * Método para establecer el atributo catalogoCustodios
     * @param catalogoCustodios El valor del atributo catalogoCustodios a establecer.
     */
    public void setCatalogoCustodios(final List<SelectItem> catalogoCustodios) {
        this.catalogoCustodios = catalogoCustodios;
    }

    /**
     * Método para obtener el atributo usuarioExterno
     * @return El atributo usuarioExterno
     */
    public boolean isUsuarioExterno() {
        return this.usuarioExterno;
    }

    /**
     * Método para establecer el atributo usuarioExterno
     * @param usuarioExterno El valor del atributo usuarioExterno a establecer.
     */
    public void setUsuarioExterno(final boolean usuarioExterno) {
        this.usuarioExterno = usuarioExterno;
    }

    /**
     * Método para establecer el atributo consultaCatService
     * @param consultaCatService El valor del atributo consultaCatService a establecer.
     */
    public void setConsultaCatService(final ConsultaCatalogoService consultaCatService) {
        this.consultaCatService = consultaCatService;
    }

    /**
     * Método para establecer el atributo derechosCapitalesService
     * @param derechosCapitalesService El valor del atributo derechosCapitalesService a establecer.
     */
    public void setDerechosCapitalesService(final DerechosCapitalesService derechosCapitalesService) {
        this.derechosCapitalesService = derechosCapitalesService;
    }

    /**
     * Método para obtener el atributo mostrarParametros
     * @return El atributo mostrarParametros
     */
    public boolean isMostrarParametros() {
        return this.mostrarParametros;
    }

    /**
     * Método para establecer el atributo mostrarParametros
     * @param mostrarParametros El valor del atributo mostrarParametros a establecer.
     */
    public void setMostrarParametros(final boolean mostrarParametros) {
        this.mostrarParametros = mostrarParametros;
    }

    /**
     * @return the divisaDao
     */
    public DivisaDao getDivisaDao() {
        return this.divisaDao;
    }

    /**
     * @param divisaDao the divisaDao to set
     */
    public void setDivisaDao(final DivisaDao divisaDao) {
        this.divisaDao = divisaDao;
    }

    /** @return this.catalogoEstadoDerecho */
    public List<SelectItem> getCatalogoEstadoDerecho() {
        return this.catalogoEstadoDerecho;
    }

    /** @param catalogoEstadoDerecho to be set in this.catalogoEstadoDerecho */
    public void setCatalogoEstadoDerecho(final List<SelectItem> catalogoEstadoDerecho) {
        this.catalogoEstadoDerecho = catalogoEstadoDerecho;
    }

    /** @return this.catalogoTiposDerecho */
    public List<SelectItem> getCatalogoTiposDerecho() {
        return this.catalogoTiposDerecho;
    }

    /** @param catalogoTiposDerecho to be set in this.catalogoTiposDerecho */
    public void setCatalogoTiposDerecho(final List<SelectItem> catalogoTiposDerecho) {
        this.catalogoTiposDerecho = catalogoTiposDerecho;
    }
}
