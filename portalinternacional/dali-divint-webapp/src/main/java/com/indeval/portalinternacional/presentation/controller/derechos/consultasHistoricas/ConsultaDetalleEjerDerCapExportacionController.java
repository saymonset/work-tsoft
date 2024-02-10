/**
 * Bursatec - Portal Internacional
 * Copyrigth (c) 2016 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.presentation.controller.derechos.consultasHistoricas;

import java.util.List;

import javax.el.ELContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portalinternacional.middleware.services.divisioninternacional.DerechosCapitalesService;
import com.indeval.portalinternacional.middleware.servicios.constantes.ExportacionConstantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.capitales.DerechoCapitalHistorico;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaDerechosBeneficiarioTO;
import com.indeval.portalinternacional.middleware.servicios.vo.ParamConsultaDetalleEjerDerCapTO;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;

/**
 * Controlador para la exportación de las consultas.
 * 
 * @author Pablo Balderas
 */
public class ConsultaDetalleEjerDerCapExportacionController extends ControllerBase {

    /** Log */
	private static final Logger LOG = LoggerFactory.getLogger(ConsultaDetalleEjerDerCapExportacionController.class);

    /** Parametros de consulta */
    private ParamConsultaDetalleEjerDerCapTO parametros = null;

    /** Indica el tipo de exportacion */
    private int tipoExportacion;

    /** Servicio de derechos de capital */
    private DerechosCapitalesService derechosCapitalesService;

    /** Nombre del bean de consulta */
    private final String BEAN_CONSULTA = "consultaDetalleEjerDerCap";

    /** Objeto con los resultados de la consulta, subtotales y totales */
    private ConsultaDerechosBeneficiarioTO consultaDerechosBeneficiarioTO = null;

    /** Lista con los resultados de la consulta */
    private List<DerechoCapitalHistorico> resultadosConsulta = null;

    /** bandera para cobrar la consulta (las exportaciones no se cobran) */
    private final boolean cobraConsulta=false;

    /**
     * Constructor de la clase.
     */
    public ConsultaDetalleEjerDerCapExportacionController() {}

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
            ConsultaDetalleEjerDerCapController consultaDetalleEjerDerCapController =
                    (ConsultaDetalleEjerDerCapController) FacesContext.getCurrentInstance().getApplication().
                    getELResolver().getValue(elContext, null, this.BEAN_CONSULTA);
            this.parametros = consultaDetalleEjerDerCapController.getParametros();
            //Prepara el objeto de paginación
            this.paginaVO.setOffset(0);
            this.paginaVO.setRegistros(null);
            this.paginaVO.setRegistrosXPag(ExportacionConstantes.MAX_REGISTROS_EXPORTAR);
            this.paginaVO.setTotalRegistros(consultaDetalleEjerDerCapController.getPaginaVO().getTotalRegistros());
            //Realiza la consulta
            this.paginaVO = this.derechosCapitalesService.consultaDetalleDerechosCapital(this.paginaVO, this.parametros, true, this.cobraConsulta);
            this.consultaDerechosBeneficiarioTO = (ConsultaDerechosBeneficiarioTO) this.paginaVO.getRegistros().get(0);
            this.resultadosConsulta = this.consultaDerechosBeneficiarioTO.getRegistrosConsulta();
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
    public String getDescParamTV() {
        return StringUtils.isNotBlank(this.parametros.getTv()) ?
                this.parametros.getTv() : ExportacionConstantes.TODOS;
    }

    /**
     * Obtiene la descripción del parámetro de búsqueda para la exportación
     * @return el párametro de búsqueda si no es nulo o vacio; todos o todas en caso contrario
     */
    public String getDescParamEmisora() {
        return StringUtils.isNotBlank(this.parametros.getEmisora()) ?
                this.parametros.getEmisora() : ExportacionConstantes.TODAS;
    }

    /**
     * Obtiene la descripción del parámetro de búsqueda para la exportación
     * @return el párametro de búsqueda si no es nulo o vacio; todos o todas en caso contrario
     */
    public String getDesParamSerie() {
        return StringUtils.isNotBlank(this.parametros.getSerie()) ?
                this.parametros.getSerie() : ExportacionConstantes.TODAS;
    }

    /**
     * Obtiene la descripción del parámetro de búsqueda para la exportación
     * @return el párametro de búsqueda si no es nulo o vacio; todos o todas en caso contrario
     */
    public String getDesParamIsin() {
        return StringUtils.isNotBlank(this.parametros.getIsin()) ?
                this.parametros.getIsin() : ExportacionConstantes.TODOS;
    }

    /**
     * Obtiene la descripción del parámetro de búsqueda para la exportación
     * @return el párametro de búsqueda si no es nulo o vacio; todos o todas en caso contrario
     */
    public String getDesParamInstitucion() {
        return StringUtils.isNotBlank(this.parametros.getInstitucion()) ?
                this.parametros.getInstitucion() + " " + this.parametros.getNombreInstitucion() :
                    ExportacionConstantes.TODAS;
    }

    /**
     * Obtiene la descripción del parámetro de búsqueda para la exportación
     * @return el párametro de búsqueda si no es nulo o vacio; todos o todas en caso contrario
     */
    public String getDesParamCuenta() {
        return StringUtils.isNotBlank(this.parametros.getCuenta()) ?
                this.parametros.getCuenta() : ExportacionConstantes.TODAS;
    }

    /**
     * Obtiene la descripción del parámetro de búsqueda para la exportación
     * @return el párametro de búsqueda si no es nulo o vacio; todos o todas en caso contrario
     */
    public Object getDesParamFechaCorteInicial() {
        return this.parametros.getFechaCorteInicial() != null ?
                this.parametros.getFechaCorteInicial() : ExportacionConstantes.TODAS;
    }

    /**
     * Obtiene la descripción del parámetro de búsqueda para la exportación
     * @return el párametro de búsqueda si no es nulo o vacio; todos o todas en caso contrario
     */
    public Object getDesParamFechaCorteFinal() {
        return this.parametros.getFechaCorteFinal() != null ?
                this.parametros.getFechaCorteFinal() : ExportacionConstantes.TODAS;
    }

    /**
     * Obtiene la descripción del parámetro de búsqueda para la exportación
     * @return el párametro de búsqueda si no es nulo o vacio; todos o todas en caso contrario
     */
    public Object getDesParamFechaPagoInicial() {
        return this.parametros.getFechaPagoInicial() != null ?
                this.parametros.getFechaPagoInicial() : ExportacionConstantes.TODAS;
    }

    /**
     * Obtiene la descripción del parámetro de búsqueda para la exportación
     * @return el párametro de búsqueda si no es nulo o vacio; todos o todas en caso contrario
     */
    public Object getDesParamFechaPagoFinal() {
        return this.parametros.getFechaPagoFinal() != null ?
                this.parametros.getFechaPagoFinal() : ExportacionConstantes.TODAS;
    }

    /**
     * Obtiene la descripción del parámetro de búsqueda para la exportación
     * @return el párametro de búsqueda si no es nulo o vacio; todos o todas en caso contrario
     */
    public String getDesParamDivisa() {
        return StringUtils.isNotBlank(this.parametros.getDivisa()) ?
                this.parametros.getDivisa() : ExportacionConstantes.TODAS;
    }

    /**
     * Obtiene la descripción del parámetro de búsqueda para la exportación
     * @return el párametro de búsqueda si no es nulo o vacio; todos o todas en caso contrario
     */
    public String getDesParamCustodio() {
        return StringUtils.isNotBlank(this.parametros.getCustodio()) ?
                this.parametros.getCustodio() : ExportacionConstantes.TODOS;
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
     * Obtiene la descripcion del parametro de busqueda para la exportacion -Nombre de Beneficiario-.
     * @return el parametro de busqueda si no es nulo o vacio; todos o todas en caso contrario
     */
    public String getDescParamNombreBeneficiario() {
        return StringUtils.isNotBlank(this.parametros.getNombreBeneficiario()) ?
                this.parametros.getNombreBeneficiario() : ExportacionConstantes.TODOS;
    }

    /**
     * Obtiene la descripcion del parametro de busqueda para la exportacion -RFC-.
     * @return el parametro de busqueda si no es nulo o vacio; todos o todas en caso contrario
     */
    public String getDescParamRFC() {
        return StringUtils.isNotBlank(this.parametros.getRfc()) ?
                this.parametros.getRfc() : ExportacionConstantes.TODOS;
    }

    /**
     * Obtiene la descripcion del parametro de busqueda para la exportacion -Monto Bruto-.
     * @return el parametro de busqueda si no es nulo o vacio; todos o todas en caso contrario
     */
    public String getDescParamMontoBruto() {
        return StringUtils.isNotBlank(this.parametros.getMontoBruto()) ?
                this.parametros.getMontoBruto().toString() : ExportacionConstantes.TODOS;
    }

    /**
     * Obtiene la descripcion del parametro de busqueda para la exportacion -Monto Neto-.
     * @return el parametro de busqueda si no es nulo o vacio; todos o todas en caso contrario
     */
    public String getDescParamMontoNeto() {
        return StringUtils.isNotBlank(this.parametros.getMontoNeto()) ?
                this.parametros.getMontoNeto().toString() : ExportacionConstantes.TODOS;
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
     * Obtiene la descripcion del parametro de busqueda para la exportacion -UOI-.
     * @return el parametro de busqueda si no es nulo o vacio; todos o todas en caso contrario
     */
    public String getDescParamUOI() {
        return StringUtils.isNotBlank(this.parametros.getUoi()) ?
                this.parametros.getUoi() : ExportacionConstantes.TODOS;
    }

    /**
     * Obtiene la descripcion del parametro de busqueda para la exportacion -Pais-.
     * @return el parametro de busqueda si no es nulo o vacio; todos o todas en caso contrario
     */
    public String getDescParamPais() {
        return StringUtils.isNotBlank(this.parametros.getPais()) ?
                this.parametros.getPais() : ExportacionConstantes.TODOS;
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
     * Obtiene la descripcion del parametro de busqueda para la exportacion -Referencia Custodio-.
     * @return el parametro de busqueda si no es nulo o vacio; todos o todas en caso contrario
     */
    public String getDescParamReferenciaCustodio() {
        return StringUtils.isNotBlank(this.parametros.getReferenciaCustodio()) ?
                this.parametros.getReferenciaCustodio() : ExportacionConstantes.TODAS;
    }

    /**
     * Obtiene la descripcion del parametro de busqueda para la exportacion -Tipo Formato-.
     * @return el parametro de busqueda si no es nulo o vacio; todos o todas en caso contrario
     */
    public String getDescParamTipoFormato() {
        return StringUtils.isNotBlank(this.parametros.getTipoFormato()) ?
                this.parametros.getTipoFormatoPantalla() : ExportacionConstantes.TODOS;
    }

    /**
     * Obtiene la descripcion del parametro de busqueda para la exportacion -Impuesto Retenido-.
     * @return el parametro de busqueda si no es nulo o vacio; todos o todas en caso contrario
     */
    public String getDescParamImpuestoRetenido() {
        return StringUtils.isNotBlank(this.parametros.getImpuestoRetenido()) ? this.parametros.getImpuestoRetenido().toString() :
            ExportacionConstantes.TODOS;
    }

    /**
     * Obtiene la descripcion del parametro de busqueda para la exportacion -Tipo Beneficiario-.
     * @return el parametro de busqueda si no es nulo o vacio; todos o todas en caso contrario
     */
    public String getDescParamTipoBeneficiario() {
        return StringUtils.isNotBlank(this.parametros.getTipoBeneficiario()) ?
                this.parametros.getTipoBeneficiario() : ExportacionConstantes.TODOS;
    }

    /**
     * Obtiene la descripcion del parametro de busqueda para la exportacion -GIIN-.
     * @return el parametro de busqueda si no es nulo o vacio; todos o todas en caso contrario
     */
    public String getDescParamGiin() {
        return StringUtils.isNotBlank(this.parametros.getGiin()) ?
                this.parametros.getGiin() : ExportacionConstantes.TODOS;
    }

    /**
     * Obtiene la descripcion del parametro de busqueda para la exportacion -Estado Formato W-.
     * @return el parametro de busqueda si no es nulo o vacio; todos o todas en caso contrario
     */
    public String getDescParamEstadoFormatoW() {
        return StringUtils.isNotBlank(this.parametros.getEstadoFormatoW()) ?
                this.parametros.getEstadoFormatoW() : ExportacionConstantes.TODOS;
    }

    /**
     * Obtiene la descripcion del parametro de busqueda para la exportacion -Reference Number-.
     * @return el parametro de busqueda si no es nulo o vacio; todos o todas en caso contrario
     */
    public String getDescParamReferenceNumber() {
        return StringUtils.isNotBlank(this.parametros.getReferenceNumber()) ?
                this.parametros.getReferenceNumber() : ExportacionConstantes.TODOS;
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
     * Indica si la consulta arrojo resultados.
     * @return true si hay resultados; false en otro caso.
     */
    public boolean isConsultaConResultados() {
        return this.resultadosConsulta != null && !this.resultadosConsulta.isEmpty();
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

    public ConsultaDerechosBeneficiarioTO getConsultaDerechosBeneficiarioTO() {
        return this.consultaDerechosBeneficiarioTO;
    }

    public void setConsultaDerechosBeneficiarioTO(final ConsultaDerechosBeneficiarioTO consultaDerechosBeneficiarioTO) {
        this.consultaDerechosBeneficiarioTO = consultaDerechosBeneficiarioTO;
    }

}
