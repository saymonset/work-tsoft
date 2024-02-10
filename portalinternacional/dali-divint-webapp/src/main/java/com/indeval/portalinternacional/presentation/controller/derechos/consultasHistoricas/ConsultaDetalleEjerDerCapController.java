/**
 * Bursatec - Portal Internacional
 * Copyrigth (c) 2016 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.presentation.controller.derechos.consultasHistoricas;

import java.util.List;

import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import com.indeval.portaldali.middleware.services.util.ConsultaCatalogoService;
import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.persistence.modelo.Institucion;
import com.indeval.portalinternacional.common.util.CatalogosUtil;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.DerechosCapitalesService;
import com.indeval.portalinternacional.middleware.servicios.constantes.CatalogosConstantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.capitales.DerechoCapitalHistorico;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaDerechosBeneficiarioTO;
import com.indeval.portalinternacional.middleware.servicios.vo.ParamConsultaDetalleEjerDerCapTO;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;

/**
 * Controller para la consulta de detalle de ejercicio de derechos de capital.
 * 
 * @author Pablo Balderas
 *
 */
public class ConsultaDetalleEjerDerCapController extends ControllerBase {

    /** Parametros de consulta */
    private ParamConsultaDetalleEjerDerCapTO parametros = null;

    /** Lista con los resultados de la consulta */
    private List<DerechoCapitalHistorico> resultadosConsulta = null;

    /** Objeto con los resultados de la consulta, subtotales y totales */
    private ConsultaDerechosBeneficiarioTO consultaDerechosBeneficiarioTO = null;

    /** Utileria para obtener los catalogos */
    private CatalogosUtil catalogosUtil;

    /** Servicio para obtener la institucion */
    private ConsultaCatalogoService consultaCatService;

    /** Servicio de derechos de capital */
    private DerechosCapitalesService derechosCapitalesService;

    /** Indica si la consulta ha sido ejecutada */
    private boolean consultaEjecutada = false;

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

    /** Catálogo de custodios */
    private List<SelectItem> catalogoTiposBeneficiario;

    /** Catálogo de formatos W */
    private List<SelectItem> catalogoTipoFormatosW;

    /** Catálogo de estados formatos W */
    private List<SelectItem> catalogoEstadosFormatoW;

    /** bandera para cobrar la consulta (las exportaciones no se cobran) */
    private  boolean cobraConsulta=true;

    private List<SelectItem> catalogoEstadoDerecho;
    private List<SelectItem> catalogoTiposDerecho;

    /**
     * Mátodo que inicializa la pantalla
     * @return null
     */
    public String getInit() {
        if(!this.banderaInicio) {
            this.consultaEjecutada = false;
            this.resultadosConsulta = null;
            this.parametros = new ParamConsultaDetalleEjerDerCapTO();
            this.banderaInicio = true;
            this.catalogoDivisas = this.catalogosUtil.getCatalogoDivisasDescripcion();
            this.catalogoEstatusDerecho = this.catalogosUtil.getCatalogoEstatusDerechosPagadoCortado();
            this.catalogoCustodios = this.catalogosUtil.getCatalogoCustodiosCatBic();
            this.catalogoTiposBeneficiario = this.catalogosUtil.getCatalogoTiposBeneficiario();
            this.catalogoTipoFormatosW = this.catalogosUtil.getCatalogoTiposFormatosW();
            this.catalogoEstadosFormatoW = this.catalogosUtil.getCatalogoEstadosFormatosW();
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
            this.parametros.setTipoBeneficiario(this.getSelected(this.parametros.getIdTipoBeneficiario(), this.catalogoTiposBeneficiario));
            if(CatalogosConstantes.VALOR_TODOS_STR.equals(this.parametros.getTipoFormato())) {
                this.parametros.setTipoFormato(null);
            }
            this.ejecutarConsulta();
            this.consultaEjecutada = true;
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
        this.paginaVO = this.derechosCapitalesService.consultaDetalleDerechosCapital(this.paginaVO, this.parametros, false, this.cobraConsulta);
        this.consultaDerechosBeneficiarioTO = (ConsultaDerechosBeneficiarioTO) this.paginaVO.getRegistros().get(0);
        this.resultadosConsulta = this.consultaDerechosBeneficiarioTO.getRegistrosConsulta();
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
        else {
            this.parametros.setIdInstitucion(null);
            this.parametros.setNombreInstitucion(null);
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
     * Indica si la consulta arrojo resultados.
     * @return true si hay resultados; false en otro caso.
     */
    public boolean isConsultaConResultados() {
        return this.resultadosConsulta != null && !this.resultadosConsulta.isEmpty();
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
    public List<DerechoCapitalHistorico> getResultadosConsulta() {
        return this.resultadosConsulta;
    }

    /**
     * Método para establecer el atributo resultadosConsulta
     * @param resultadosConsulta El valor del atributo resultadosConsulta a establecer.
     */
    public void setResultadosConsulta(final List<DerechoCapitalHistorico> resultadosConsulta) {
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

    public List<SelectItem> getCatalogoTiposBeneficiario() {
        return this.catalogoTiposBeneficiario;
    }

    public void setCatalogoTiposBeneficiario(final List<SelectItem> catalogoTiposBeneficiario) {
        this.catalogoTiposBeneficiario = catalogoTiposBeneficiario;
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
     * Método para obtener el atributo consultaDerechosBeneficiarioTO
     * @return El atributo consultaDerechosBeneficiarioTO
     */
    public ConsultaDerechosBeneficiarioTO getConsultaDerechosBeneficiarioTO() {
        return this.consultaDerechosBeneficiarioTO;
    }


    /**
     * Método para establecer el atributo consultaDerechosBeneficiarioTO
     * @param consultaDerechosBeneficiarioTO El valor del atributo consultaDerechosBeneficiarioTO a establecer.
     */
    public void setConsultaDerechosBeneficiarioTO(final ConsultaDerechosBeneficiarioTO consultaDerechosBeneficiarioTO) {
        this.consultaDerechosBeneficiarioTO = consultaDerechosBeneficiarioTO;
    }


    /**
     * Método para obtener el atributo catalogoTipoFormatosW
     * @return El atributo catalogoTipoFormatosW
     */
    public List<SelectItem> getCatalogoTipoFormatosW() {
        return this.catalogoTipoFormatosW;
    }


    /**
     * Método para establecer el atributo catalogoTipoFormatosW
     * @param catalogoTipoFormatosW El valor del atributo catalogoTipoFormatosW a establecer.
     */
    public void setCatalogoTipoFormatosW(final List<SelectItem> catalogoTipoFormatosW) {
        this.catalogoTipoFormatosW = catalogoTipoFormatosW;
    }


    /**
     * Método para obtener el atributo catalogoEstadosFormatoW
     * @return El atributo catalogoEstadosFormatoW
     */
    public List<SelectItem> getCatalogoEstadosFormatoW() {
        return this.catalogoEstadosFormatoW;
    }


    /**
     * Método para establecer el atributo catalogoEstadosFormatoW
     * @param catalogoEstadosFormatoW El valor del atributo catalogoEstadosFormatoW a establecer.
     */
    public void setCatalogoEstadosFormatoW(final List<SelectItem> catalogoEstadosFormatoW) {
        this.catalogoEstadosFormatoW = catalogoEstadosFormatoW;
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
