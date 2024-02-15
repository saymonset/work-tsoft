package com.indeval.portalinternacional.presentation.controller.divinternacional;

import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.modelo.Boveda;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService;
import com.indeval.portalinternacional.middleware.servicios.modelo.Custodio;
import com.indeval.portalinternacional.middleware.servicios.vo.ConciliacionIntDTO;
import com.indeval.portalinternacional.middleware.servicios.vo.DetalleConciliacionIntDTO;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;
import com.indeval.portalinternacional.presentation.controller.conciliacionInternacional.ConciliacionInternacionalController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ConsultaCustodiosController  extends ControllerBase {
    private static final Logger log = LoggerFactory.getLogger(ConsultaCustodiosController.class);
    private String bovedaDali;
    private boolean banderaBitacoraConsulta = false;


    private boolean consultaEjecutada;
    private ConciliacionIntDTO conciliacion;
    private DivisionInternacionalService divisionInternacionalService;

    private List<SelectItem> listaBoveda;
    private List<SelectItem> listaCustodios;

    private String custodio;

    /** Pagina para los reportes*/
    private PaginaVO paginaReportes;

    private int totalRegistros = 0;

    private int totalPaginas = 1;
    public ConsultaCustodiosController() {
    }

    /**
     * Inicializa el bean
     * @return
     */
    public String getInit() {
        this.bovedaDali="-1";
        this.custodio="-1";
        banderaBitacoraConsulta = false;
        return null;
    }

    private void setParams() {
        conciliacion= new ConciliacionIntDTO();
        if (this.bovedaDali != null && !this.bovedaDali.equals("") && this.bovedaDali.matches("-*[0-9]+")) {
            conciliacion.setBovedaDali(Integer.valueOf(bovedaDali));
        }
        if (this.custodio != null && !this.custodio.equals("") && this.custodio.matches("-*[0-9]+")) {
            conciliacion.setCustodio(Integer.valueOf(custodio));
        }
    }

    /**
     * Limpia todos los campos
     * @param evt
     */
    public void limpiar(ActionEvent evt)
    {
        banderaBitacoraConsulta = false;
        this.custodio="-1";


        this.bovedaDali="-1";

    }


    /**
     * Obtiene la consulta de Bovedas
     *
     */
    @SuppressWarnings("unchecked")
    public List<SelectItem> getBovedas(){
        if(this.listaBoveda != null && this.listaBoveda.size() > 0) {
            return this.listaBoveda;
        }
        List<Boveda> bovedas = divisionInternacionalService.consultaBovedas(divisionInternacionalService.BOVEDA_VALORES_INTERNACIONAL);
        List <SelectItem> listaBoveda = new ArrayList<SelectItem>();
        if(bovedas != null){
            for (Boveda boveda : bovedas){
                listaBoveda.add(new SelectItem(boveda.getIdBoveda().toString(),boveda.getDescripcion()));
            }
        }else{
            listaBoveda.add(new SelectItem("-2","VACIO"));
        }
        this.listaBoveda=listaBoveda;
        return listaBoveda;
    }

    /**
     * obtiene la lista de custodios
     */
    public List<SelectItem> getCustodios(){
        if(this.listaCustodios != null && this.listaCustodios.size() > 0){
            return this.listaCustodios;
        }
        List<Custodio> custodios = divisionInternacionalService.obtieneCatalogoCustodios();
        List <SelectItem> listaCustodios = new ArrayList<SelectItem>();
        if( custodios != null){
            for (Custodio custodio : custodios){
                listaCustodios.add(new SelectItem(custodio.getId().toString(), custodio.getDescripcion()));
            }
        }else{
            listaCustodios.add(new SelectItem("-2","VACIO"));
        }
        this.listaCustodios=listaCustodios;
        return listaCustodios;
    }

    private String getSelected(String key, List<SelectItem> lista){
        String resultado = null;
        for(SelectItem item : lista){
            if(key != null && item.getValue().equals(key))
                resultado=item.getLabel();
        }
        return resultado;
    }

    /**
     * Ejecuta la consulta y calcula el numero de pagina para la paginacion.
     *  Este metodo es un overide de la clase padre
     */
    public String ejecutarConsulta(){

        setParams();
        paginaVO = divisionInternacionalService.consultaConciliacion(conciliacion, paginaVO);

        totalPaginas = paginaVO.getTotalRegistros() / paginaVO.getRegistrosXPag();

        if(paginaVO.getTotalRegistros() % paginaVO.getRegistrosXPag() > 0)
            totalPaginas++;
        totalPaginas = (totalPaginas <= 0) ? 1 : totalPaginas;

        setConsultaEjecutada(true);
        return null;
    }

    /**
     * Buscar las emisiones
     * @param evt
     */
    public void buscarConciliaciones(ActionEvent evt)
    {
        paginaVO.setRegistrosXPag(50);
        paginaVO.setOffset(0);
        getPaginaVO().setRegistros(null);
        ejecutarConsulta();

    }

    /**
     * Concilia
     * @param evt
     */
    public void concilia(ActionEvent evt){
        Long idConciliacion=Long.valueOf(getHiddenField("daliForm:idConciliacionHidden"));
        divisionInternacionalService.instruyeConciliacion(idConciliacion);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Se esta conciliando el folio: "+idConciliacion+" actualice en unos momentos",
                "Se esta conciliando el folio: "+idConciliacion+" actualice en unos momentos"));
//		addMessage("Se esta conciliando el folio: "+idConciliacion+" actualice en unos momentos");
    }

    /**
     * Action para generar el reporte de auditoria
     *
     * @return
     */
    public String generarReporteAuditoria(){
        String idConciliacion=getHiddenField("daliForm:idConciliacionHidden");
        String guardarReporte = getHiddenField("daliForm:reporteAuditoria");
        if(guardarReporte.equals("true")){
            log.info("guardar el reporte "+idConciliacion);
            divisionInternacionalService.generaReporteAuditoriaConciliacion(Long.valueOf(idConciliacion));
        }
        DetalleConciliacionIntDTO conc= new DetalleConciliacionIntDTO();
        conc.setFolio(Long.valueOf(idConciliacion));
        paginaReportes = new PaginaVO();
        paginaReportes.setOffset(0);
        paginaReportes.setRegistrosXPag(PaginaVO.TODOS);
        paginaReportes = divisionInternacionalService.consultaDetalleConciliacion(conc, paginaReportes);
        this.totalRegistros=paginaReportes.getTotalRegistros();
        return "conciliacionInternacionalXLSAuditoria";
    }

    /**
     * Genera los reportes de Consulta de Conciliaciones
     * @param evt
     */
    public void generarReportes(ActionEvent evt)
    {
        paginaReportes = new PaginaVO();
        paginaReportes.setOffset(0);
        paginaReportes.setRegistrosXPag(PaginaVO.TODOS);
        paginaReportes = divisionInternacionalService.consultaConciliacion(conciliacion, paginaReportes);
        this.totalRegistros=paginaReportes.getTotalRegistros();

    }

    public String getSelectedBoveda(){
        String resultado = getSelected(getBovedaDali() ,this.listaBoveda);
        if(resultado != null){
            return resultado;
        }
        return "TODAS";
    }

    /**
     * Obtiene un campo oculto de la forma
     * @param hiddenField
     * @return
     */
    private String getHiddenField(String hiddenField){
        Map<String , String> map=FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String conciliacion=map.get(hiddenField);

        return conciliacion;
    }
    public String getSelectedCustodio(){
        String resultado = getSelected(getCustodio() ,this.listaCustodios);
        if(resultado != null){
            return resultado;
        }
        return "TODOS";
    }

    /**
     * @return the divisionInternacionalService
     */
    public DivisionInternacionalService getDivisionInternacionalService() {
        return divisionInternacionalService;
    }

    /**
     * @param divisionInternacionalService the divisionInternacionalService to set
     */
    public void setDivisionInternacionalService(
            DivisionInternacionalService divisionInternacionalService) {
        this.divisionInternacionalService = divisionInternacionalService;
    }

    /**
     * @return the custodio
     */
    public String getCustodio() {
        return custodio;
    }

    /**
     * @param custodio the custodio to set
     */
    public void setCustodio(String custodio) {
        this.custodio = custodio;
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
    public int getTotalPaginas() {
        return totalPaginas;
    }

    /**
     * @param totalPaginas the totalPaginas to set
     */
    public void setTotalPaginas(int totalPaginas) {
        this.totalPaginas = totalPaginas;
    }
    public boolean isBanderaBitacoraConsulta() {
        return banderaBitacoraConsulta;
    }

    public void setBanderaBitacoraConsulta(boolean banderaBitacoraConsulta) {
        this.banderaBitacoraConsulta = banderaBitacoraConsulta;
    }

    /**
     * @return the paginaReportes
     */
    public PaginaVO getPaginaReportes() {
        return paginaReportes;
    }

    /**
     * @param paginaReportes the paginaReportes to set
     */
    public void setPaginaReportes(PaginaVO paginaReportes) {
        this.paginaReportes = paginaReportes;
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
     * @return the bovedaDali
     */
    public String getBovedaDali() {
        return bovedaDali;
    }
    /**
     * @param bovedaDali the bovedaDali to set
     */
    public void setBovedaDali(String bovedaDali) {
        this.bovedaDali = bovedaDali;
    }

    public ConciliacionIntDTO getConciliacion() {
        return conciliacion;
    }

    public void setConciliacion(ConciliacionIntDTO conciliacion) {
        this.conciliacion = conciliacion;
    }
}
