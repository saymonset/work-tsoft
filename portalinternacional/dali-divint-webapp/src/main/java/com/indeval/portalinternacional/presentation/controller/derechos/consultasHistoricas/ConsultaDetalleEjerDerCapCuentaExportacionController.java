/**
 * Bursatec - Portal Internacional
 * Copyrigth (c) 2016 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.presentation.controller.derechos.consultasHistoricas;

import javax.el.ELContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portalinternacional.middleware.services.divisioninternacional.DerechosCapitalesService;
import com.indeval.portalinternacional.middleware.servicios.constantes.CatalogosConstantes;
import com.indeval.portalinternacional.middleware.servicios.constantes.ExportacionConstantes;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaDetalleEjerDerCapCuentaTO;
import com.indeval.portalinternacional.middleware.servicios.vo.ParamConsultaDetalleEjerDerCapTO;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;

/**
 * Controlador para la exportación de las consultas.
 * 
 * @author Pablo Balderas
 */
public class ConsultaDetalleEjerDerCapCuentaExportacionController extends ControllerBase {

    /** Log */
	private static final Logger LOG = LoggerFactory.getLogger(ConsultaDetalleEjerDerCapCuentaExportacionController.class);

    /** Parametros de consulta */
    private ParamConsultaDetalleEjerDerCapTO parametros = null;

    /** Indica el tipo de exportacion */
    private int tipoExportacion;

    /** Servicio de derechos de capital */
    private DerechosCapitalesService derechosCapitalesService;

    /** Nombre del bean de consulta */
    private final String BEAN_CONSULTA = "consultaDetalleEjerDerCapCuenta";

    /** Resultados de la consulta */
    private ConsultaDetalleEjerDerCapCuentaTO resultadosConsulta = null;

    /** bandera para cobrar la consulta (las exportaciones no se cobran) */
    private final boolean cobraConsulta=false;

    /**
     * Constructor de la clase.
     */
    public ConsultaDetalleEjerDerCapCuentaExportacionController() {

    }

    /**
     * Método que realiza la exportación de la consulta a un documento PDF.
     * @param e Acción generada por faces.
     */
    public void generarExportacionPDF(final ActionEvent e) {
        this.tipoExportacion = ExportacionConstantes.TIPO_REPORTE_PDF;
        this.generarExportacion();
    }

    /**
     * Método que realiza la exportación de la consulta a un documento XLS.
     * @param e Acción generada por faces.
     */
    public void generarExportacionXLS(final ActionEvent e) {
        this.tipoExportacion = ExportacionConstantes.TIPO_REPORTE_XLS;
        this.generarExportacion();
    }

    /**
     * Método que genera la exportación.
     */
    private void generarExportacion() {
        try {
            this.reiniciarEstadoPeticion();
            ELContext elContext = FacesContext.getCurrentInstance().getELContext();
            ConsultaDetalleEjerDerCapCuentaController consultaDetalleEjerDerCapCuentaController =
                    (ConsultaDetalleEjerDerCapCuentaController) FacesContext.getCurrentInstance().getApplication().
                    getELResolver().getValue(elContext, null, this.BEAN_CONSULTA);
            this.parametros = consultaDetalleEjerDerCapCuentaController.getParametros();
            //Prepara el objeto de paginación
            this.paginaVO.setOffset(0);
            this.paginaVO.setRegistros(null);
            this.paginaVO.setRegistrosXPag(ExportacionConstantes.MAX_REGISTROS_EXPORTAR);
            this.paginaVO.setTotalRegistros(consultaDetalleEjerDerCapCuentaController.getPaginaVO().getTotalRegistros());
            //Realiza la consulta
            this.paginaVO = this.derechosCapitalesService.consultaDetalleDerechosCapitalCuenta(this.paginaVO, this.parametros, true,this.cobraConsulta);
            this.resultadosConsulta = (ConsultaDetalleEjerDerCapCuentaTO) this.paginaVO.getRegistros().get(0);
        }
        catch(Exception e) {
            LOG.debug("Ocurrio un error:",e);
            e.printStackTrace();
        }
    }

    /**
     * Obtiene la descripción del parámetro de búsqueda para la exportación
     * @return el párametro de búsqueda si no es nulo o vacio; todos o todas en caso contrario
     */
    public String getDescParamInstitucion() {
        return StringUtils.isNotBlank(this.parametros.getInstitucion()) ?
                this.parametros.getInstitucion() + " " + this.parametros.getNombreInstitucion() : ExportacionConstantes.TODAS;
    }

    /**
     * Obtiene la descripción del parámetro de búsqueda para la exportación
     * @return el párametro de búsqueda si no es nulo o vacio; todos o todas en caso contrario
     */
    public String getDescParamCuenta() {
        return StringUtils.isNotBlank(this.parametros.getCuenta()) ?
                this.parametros.getCuenta() : ExportacionConstantes.TODAS;
    }

    /**
     * Obtiene la descripción del parámetro de búsqueda para la exportación
     * @return el párametro de búsqueda si no es nulo o vacio; todos o todas en caso contrario
     */
    public String getDescParamTV() {
        return StringUtils.isNotBlank(this.parametros.getTv()) ? this.parametros.getTv() : ExportacionConstantes.TODOS;
    }

    /**
     * Obtiene la descripción del parámetro de búsqueda para la exportación
     * @return el párametro de búsqueda si no es nulo o vacio; todos o todas en caso contrario
     */
    public String getDescParamEmisora() {
        return StringUtils.isNotBlank(this.parametros.getEmisora()) ? this.parametros.getEmisora() : ExportacionConstantes.TODAS;
    }

    /**
     * Obtiene la descripción del parámetro de búsqueda para la exportación
     * @return el párametro de búsqueda si no es nulo o vacio; todos o todas en caso contrario
     */
    public String getDescParamSerie() {
        return StringUtils.isNotBlank(this.parametros.getSerie()) ? this.parametros.getSerie() : ExportacionConstantes.TODAS;
    }

    /**
     * Obtiene la descripción del parámetro de búsqueda para la exportación
     * @return el párametro de búsqueda si no es nulo o vacio; todos o todas en caso contrario
     */
    public String getDescParamIsin() {
        return StringUtils.isNotBlank(this.parametros.getIsin()) ? this.parametros.getIsin() : ExportacionConstantes.TODOS;
    }

    /**
     * Obtiene la descripción del parámetro de búsqueda para la exportación
     * @return el párametro de búsqueda si no es nulo o vacio; todos o todas en caso contrario
     */
    public Object getDescParamFechaCorteInicial() {
        return this.parametros.getFechaCorteInicial() != null ?
                this.parametros.getFechaCorteInicial() : ExportacionConstantes.TODAS;
    }

    /**
     * Obtiene la descripción del parámetro de búsqueda para la exportación
     * @return el párametro de búsqueda si no es nulo o vacio; todos o todas en caso contrario
     */
    public Object getDescParamFechaCorteFinal() {
        return this.parametros.getFechaCorteFinal() != null ?
                this.parametros.getFechaCorteFinal() : ExportacionConstantes.TODAS;
    }

    /**
     * Obtiene la descripción del parámetro de búsqueda para la exportación
     * @return el párametro de búsqueda si no es nulo o vacio; todos o todas en caso contrario
     */
    public Object getDescParamFechaPagoInicial() {
        return this.parametros.getFechaPagoInicial() != null ?
                this.parametros.getFechaPagoInicial() : ExportacionConstantes.TODAS;
    }

    /**
     * Obtiene la descripción del parámetro de búsqueda para la exportación
     * @return el párametro de búsqueda si no es nulo o vacio; todos o todas en caso contrario
     */
    public Object getDescParamFechaPagoFinal() {
        return this.parametros.getFechaPagoFinal() != null ?
                this.parametros.getFechaPagoFinal() : ExportacionConstantes.TODAS;
    }

    /**
     * Obtiene la descripción del parámetro de búsqueda para la exportación
     * @return el párametro de búsqueda si no es nulo o vacio; todos o todas en caso contrario
     */
    public String getDescParamDivisa() {
        return StringUtils.isBlank(this.parametros.getDivisa()) || this.parametros.getDivisa().equals("-1") ?
                ExportacionConstantes.TODAS : this.parametros.getDivisa();
    }

    /**
     * Obtiene la descripción del parámetro de búsqueda para la exportación
     * @return el párametro de búsqueda si no es nulo o vacio; todos o todas en caso contrario
     */
    public String getDescParamCustodio() {
        return StringUtils.isNotBlank(this.parametros.getCustodio()) ?
                this.parametros.getCustodio() : ExportacionConstantes.TODOS;
    }

    /**
     * Obtiene la descripcion del parametro de busqueda para la exportacion -Proporcion-.
     * @return el parametro de busqueda si no es nulo o vacio; todos o todas en caso contrario
     */
    public String getDescParamProporcion() {
        return StringUtils.isNotBlank(this.parametros.getProporcion()) ?
                this.parametros.getProporcion().toString() : ExportacionConstantes.TODOS;
    }

    /**
     * Obtiene la descripcion del parametro de busqueda para la exportacion -Porcentaje de Retencion-.
     * @return el parametro de busqueda si no es nulo o vacio; todos o todas en caso contrario
     */
    public String getDescParamPorcentajeRetencion() {
        return StringUtils.isNotBlank(this.parametros.getPorcentajeRetencion()) ?
                this.parametros.getPorcentajeRetencion().toString() : ExportacionConstantes.TODOS;
    }

    /**
     * Obtiene la descripción del parámetro de búsqueda para la exportación
     * @return el párametro de búsqueda si no es nulo o vacio; todos o todas en caso contrario
     */
    public String getDescParamMontoBruto() {
        return StringUtils.isNotBlank(this.parametros.getMontoBruto()) ? this.parametros.getMontoBruto().toString() : ExportacionConstantes.TODOS;
    }

    /**
     * Obtiene la descripción del parámetro de búsqueda para la exportación
     * @return el párametro de búsqueda si no es nulo o vacio; todos o todas en caso contrario
     */
    public String getDescParamMontoFee() {
        return StringUtils.isNotBlank(this.parametros.getMontoFee()) ? this.parametros.getMontoFee().toString() : ExportacionConstantes.TODOS;
    }


    /**
     * Obtiene la descripción del parámetro de búsqueda para la exportación
     * @return el párametro de búsqueda si no es nulo o vacio; todos o todas en caso contrario
     */
    public String getDescParamImpuestoRetenido() {
        return StringUtils.isNotBlank(this.parametros.getImpuestoRetenido()) ? this.parametros.getImpuestoRetenido().toString() : ExportacionConstantes.TODOS;
    }

    /**
     * Obtiene la descripción del parámetro de búsqueda para la exportación
     * @return el párametro de búsqueda si no es nulo o vacio; todos o todas en caso contrario
     */
    public String getDescParamMontoNeto() {
        return StringUtils.isNotBlank(this.parametros.getMontoNeto()) ? this.parametros.getMontoNeto().toString() : ExportacionConstantes.TODOS;
    }

    /**
     * Obtiene la descripcion del parametro de busqueda para la exportacion -Cantidad de Titulos-.
     * @return el parametro de busqueda si no es nulo o vacio; todos o todas en caso contrario
     */
    public String getDescParamCantidadTitulos() {
        return StringUtils.isNotBlank(this.parametros.getCantidadTitulos()) ?
                this.parametros.getCantidadTitulos().toString() : ExportacionConstantes.TODOS;
    }

    /**
     * Obtiene la descripcion del parametro de busqueda para la exportacion -Fee-.
     * @return el parametro de busqueda si no es nulo o vacio; todos o todas en caso contrario
     */
    public String getDescParamFee() {
        return StringUtils.isNotBlank(this.parametros.getFee()) ?
                this.parametros.getFee().toString() : ExportacionConstantes.TODOS;
    }

    /**
     * Metodo que retorna la opción FEE seleccionada
     * @return
     */
    public String getDescParamTipoFee(){
        String descripcion=null;
        switch(this.parametros.getTipoFee()){
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
        return descripcion;
    }
    /**
     * Obtiene la descripcion del parametro de busqueda para la exportacion -Reference Number-.
     * @return el parametro de busqueda si no es nulo o vacio; todos o todas en caso contrario
     */
    public String getDescParamEstadoDerecho() {
        return StringUtils.isNotBlank(this.parametros.getEstadoDerecho()) ?
                this.parametros.getEstadoDerecho() : ExportacionConstantes.TODOS;
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
     * Método para obtener el atributo tipoExportacion
     * @return El atributo tipoExportacion
     */
    public int getTipoExportacion() {
        return this.tipoExportacion;
    }

    /**
     * Método para establecer el atributo tipoExportacion
     * @param tipoExportacion El valor del atributo tipoExportacion a establecer.
     */
    public void setTipoExportacion(final int tipoExportacion) {
        this.tipoExportacion = tipoExportacion;
    }

    /**
     * Método para obtener el atributo derechosCapitalesService
     * @return El atributo derechosCapitalesService
     */
    public DerechosCapitalesService getDerechosCapitalesService() {
        return this.derechosCapitalesService;
    }

    /**
     * Método para establecer el atributo derechosCapitalesService
     * @param derechosCapitalesService El valor del atributo derechosCapitalesService a establecer.
     */
    public void setDerechosCapitalesService(final DerechosCapitalesService derechosCapitalesService) {
        this.derechosCapitalesService = derechosCapitalesService;
    }

    /**
     * @return the resultadosConsulta
     */
    public ConsultaDetalleEjerDerCapCuentaTO getResultadosConsulta() {
        return this.resultadosConsulta;
    }

    /**
     * @param resultadosConsulta the resultadosConsulta to set
     */
    public void setResultadosConsulta(final ConsultaDetalleEjerDerCapCuentaTO resultadosConsulta) {
        this.resultadosConsulta = resultadosConsulta;
    }

}
