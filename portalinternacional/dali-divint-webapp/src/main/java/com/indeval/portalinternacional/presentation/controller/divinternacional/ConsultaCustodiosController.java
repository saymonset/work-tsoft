package com.indeval.portalinternacional.presentation.controller.divinternacional;

import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.modelo.Boveda;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.ConsultaSaldoCustodiosService;
import com.indeval.portalinternacional.middleware.servicios.dto.DivisaDTO;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaSaldoCustodiosInDTO;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaSaldoCustodiosTotalesInDTO;
import com.indeval.portalinternacional.middleware.servicios.vo.DetalleConciliacionIntDTO;
import com.indeval.portalinternacional.middleware.servicios.vo.SaldoNombradaIntVO;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import java.math.BigDecimal;
import java.util.*;

public class ConsultaCustodiosController  extends ControllerBase {
    private static final Logger log = LoggerFactory.getLogger(ConsultaCustodiosController.class);

    private boolean banderaBitacoraConsulta = false;

    /** Parametros enviados por el Request */
    private Map<String, String> params;


    private ConsultaSaldoCustodiosInDTO consultaSaldoCustodiosInDTO;
    private ConsultaSaldoCustodiosService consultaSaldoCustodiosService;

    private List<SelectItem> listaBoveda;
    private List<SelectItem> listaDivisas;

    private List<ConsultaSaldoCustodiosTotalesInDTO> consultaSaldoCustodiosTotales = new ArrayList<>();



    private String bovedaDali;
    private String divisaDali;

    private PaginaVO resultados = null;

    /** Pagina para los reportes*/
    private PaginaVO paginaReportes;

    private int totalRegistros = 0;

    private int totalPaginas = 1;

    private String idCuentaPopup;

    private boolean consultaEjecutada;


    public ConsultaCustodiosController() {
    }

    /**
     * Inicializa el bean
     * @return
     */
    public String getInit() {
        this.bovedaDali="-1";
        this.divisaDali ="-1";
        banderaBitacoraConsulta = false;
        setIdCuentaPopup(null);

        return null;

    }

    private List<ConsultaSaldoCustodiosTotalesInDTO> consultaSaldoCustodiosTotales() {
        consultaSaldoCustodiosTotales = new ArrayList<>();
        List<SaldoNombradaIntVO> resultados = paginaVO.getRegistros();
        if (resultados != null && !resultados.isEmpty()){
            SaldoNombradaIntVO saldoNombradaIntVO = resultados.get(0);

            BigDecimal totalIdSaldo = BigDecimal.ZERO;
            BigDecimal totalSaldoDisponible = BigDecimal.ZERO;
            BigDecimal totalSaldoNoDisponible = BigDecimal.ZERO;

            for (SaldoNombradaIntVO saldo : resultados) {
                totalIdSaldo = totalIdSaldo.add(saldo.getIdSaldo());
                totalSaldoDisponible = totalSaldoDisponible.add(saldo.getSaldoDisponible());
                totalSaldoNoDisponible = totalSaldoNoDisponible.add(saldo.getSaldoNoDisponible());
            }

            ConsultaSaldoCustodiosTotalesInDTO consultaSaldoCustodiosTotalesInDTO = new ConsultaSaldoCustodiosTotalesInDTO();
            consultaSaldoCustodiosTotalesInDTO.setTotalSaldo(totalIdSaldo);
            consultaSaldoCustodiosTotalesInDTO.setTotalDisponible(totalSaldoDisponible);
            consultaSaldoCustodiosTotalesInDTO.setTotalNoDisponible(totalSaldoNoDisponible);
            consultaSaldoCustodiosTotalesInDTO.setTitle("Total de Página");
            consultaSaldoCustodiosTotales.add(consultaSaldoCustodiosTotalesInDTO);
            //if (!this.consultaEjecutada){
                consultaSaldoCustodiosTotalesInDTO = consultaSaldoCustodiosService.consultaSaldoCustodioTotales(consultaSaldoCustodiosInDTO);
                consultaSaldoCustodiosTotalesInDTO.setTotalSaldo(consultaSaldoCustodiosTotalesInDTO.getTotalSaldo());
                consultaSaldoCustodiosTotalesInDTO.setTotalDisponible(consultaSaldoCustodiosTotalesInDTO.getTotalDisponible());
                consultaSaldoCustodiosTotalesInDTO.setTotalNoDisponible(consultaSaldoCustodiosTotalesInDTO.getTotalNoDisponible());
                consultaSaldoCustodiosTotales.add(consultaSaldoCustodiosTotalesInDTO);
                consultaSaldoCustodiosTotalesInDTO.setTitle("Total de Consulta");
           // }

        }




        return consultaSaldoCustodiosTotales;
    }

    private void setParams() {

        consultaSaldoCustodiosInDTO = new ConsultaSaldoCustodiosInDTO();
        consultaSaldoCustodiosInDTO.setDivisaDali(divisaDali);
        consultaSaldoCustodiosInDTO.setBovedaDali(bovedaDali);
        if (StringUtils.isNotEmpty(this.idCuentaPopup)){
            consultaSaldoCustodiosInDTO.setIdCuentaPopup(idCuentaPopup);
        }
    }

    /**
     * Limpia todos los campos
     * @param evt
     */
    public void limpiar(ActionEvent evt)
    {
        banderaBitacoraConsulta = false;
        this.divisaDali ="-1";
        this.bovedaDali="-1";

        if(resultados != null)
            resultados.getRegistros().clear();
        if(paginaVO.getRegistros() != null) {
            paginaVO.getRegistros().clear();
        }
        paginaVO.setRegistrosXPag(50);
        paginaVO.setOffset(0);

        setConsultaEjecutada(false);

    }

    public String getInitPopUp() {
        params = new HashMap<String, String>();
        Set<String> keys = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().keySet();

        for (String key : keys) {
            params.put(key, FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(key));
        }

        FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        ejecutarConsultaPopUp();
        return null;
    }

    /* =========================LAYOUT POPUP================================ */
    //para layoutpopup
    public String ejecutarConsultaPopUp() {
        if(params.get("idCuentaPopup")!=null ){
            setIdCuentaPopup(params.get("idCuentaPopup").toString());
        }
        if(params.get("divisaDali")!=null ){
            setDivisaDali(params.get("divisaDali").toString());
        }
        if(params.get("bovedaDali")!=null ){
            setBovedaDali(params.get("bovedaDali").toString());
        }

        ejecutarConsulta();

        return null;
    }

    /**
     * Ejecuta la consulta y calcula el numero de pagina para la paginacion.
     *  Este metodo es un overide de la clase padre
     */
    public String ejecutarConsulta(){

        setParams();
        paginaVO = consultaSaldoCustodiosService.consultaSaldoCustodio(consultaSaldoCustodiosInDTO, paginaVO);



        if ( paginaVO.getRegistrosXPag() !=0){
            totalPaginas = paginaVO.getTotalRegistros() / paginaVO.getRegistrosXPag();

            if(paginaVO.getTotalRegistros() % paginaVO.getRegistrosXPag() > 0)
                totalPaginas++;
            totalPaginas = (totalPaginas <= 0) ? 1 : totalPaginas;
        }


//        Seteamos los totales
         consultaSaldoCustodiosTotales();

        setConsultaEjecutada(true);
        return null;
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
        Integer tipoBoveda = null;
        List<Boveda> bovedas = consultaSaldoCustodiosService.consultaBovedas(tipoBoveda);
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

    public void seleccionarDivisas(ActionEvent event) {
        try {

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
        }
    }
    public void seleccionarBovedas(ActionEvent event) {
        try {

//            Forzamos que la lista divisa sea nula pata hacer una nueva busqueda con getDivisas y la bovedad seleccionada
            this.listaDivisas = null;
            getDivisas();

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
        }
    }

    public List<SelectItem> getDivisas(){
        if(this.listaDivisas != null && this.listaDivisas.size() > 0){
            return this.listaDivisas;
        }
        if (bovedaDali!=null){
            List<DivisaDTO> divisasDtos = consultaSaldoCustodiosService.findDivisaByBovedad(Long.valueOf(bovedaDali));
            List <SelectItem> listaDivisasNew = new ArrayList<SelectItem>();
            if( divisasDtos != null){
                for (DivisaDTO divDto : divisasDtos){
                    listaDivisasNew.add(new SelectItem(divDto.getId()+"", divDto.getDescripcion()));
                }
            }else{
                listaDivisasNew.add(new SelectItem("-2","VACIO"));
            }
            this.listaDivisas=listaDivisasNew;
        }
        return listaDivisas;
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
     * Buscar las emisiones
     * @param evt
     */
    public void buscarConciliaciones(ActionEvent evt)
    {
        paginaVO.setRegistrosXPag(50);
        paginaVO.setOffset(0);
        getPaginaVO().setRegistros(null);
        setIdCuentaPopup(null);
        ejecutarConsulta();

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
           // consultaSaldoCustodiosService.generaReporteAuditoriaConciliacion(Long.valueOf(idConciliacion));
        }
        DetalleConciliacionIntDTO conc= new DetalleConciliacionIntDTO();
        conc.setFolio(Long.valueOf(idConciliacion));
        paginaReportes = new PaginaVO();
        paginaReportes.setOffset(0);
        paginaReportes.setRegistrosXPag(PaginaVO.TODOS);
   //     paginaReportes = consultaSaldoCustodiosService.consultaDetalleConciliacion(conc, paginaReportes);
        this.totalRegistros=paginaReportes.getTotalRegistros();
        return "conciliacionInternacionalXLSAuditoria";
    }

    /**
     * Genera los reportes de Consulta de Conciliaciones
     * @param evt
     */
    public void generarReportes(ActionEvent evt)
    {
        setIdCuentaPopup(null);
        reportes( evt);

    }

    public void generarReportesPopup(ActionEvent evt)
    {
        reportes( evt);
    }

    private void reportes(ActionEvent evt)
    {
        paginaReportes = new PaginaVO();
        paginaReportes.setOffset(0);
        paginaReportes.setRegistrosXPag(PaginaVO.TODOS);
        //paginaReportes = consultaSaldoCustodiosService.consultaSaldoCustodio(conciliacion, paginaReportes);
        paginaReportes = consultaSaldoCustodiosService.consultaSaldoCustodio(consultaSaldoCustodiosInDTO, paginaReportes);
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


    public String getSelectedDivisa(){
        String resultado = getSelected(getDivisaDali() ,this.listaDivisas);
        if(resultado != null){
            return resultado;
        }
        return "TODOS";
    }

    /**
     * @return the ConsultaSaldoCustodiosService
     */
    public ConsultaSaldoCustodiosService getConsultaSaldoCustodiosService() {
        return consultaSaldoCustodiosService;
    }

    /**
     * @param consultaSaldoCustodiosService the consultaSaldoCustodiosService to set
     */
    public void setConsultaSaldoCustodiosService(
            ConsultaSaldoCustodiosService consultaSaldoCustodiosService) {
        this.consultaSaldoCustodiosService = consultaSaldoCustodiosService;
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

    public String getDivisaDali() {
        return divisaDali;
    }

    public void setDivisaDali(String divisaDali) {
        this.divisaDali = divisaDali;
    }

    public ConsultaSaldoCustodiosInDTO getConsultaSaldoCustodiosInDTO() {
        return consultaSaldoCustodiosInDTO;
    }

    public void setConsultaSaldoCustodiosInDTO(ConsultaSaldoCustodiosInDTO consultaSaldoCustodiosInDTO) {
        this.consultaSaldoCustodiosInDTO = consultaSaldoCustodiosInDTO;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public PaginaVO getResultados() {
        return resultados;
    }

    public void setResultados(PaginaVO resultados) {
        this.resultados = resultados;
    }

    public String getIdCuentaPopup() {
        return idCuentaPopup;
    }

    public void setIdCuentaPopup(String idCuentaPopup) {
        this.idCuentaPopup = idCuentaPopup;
    }

    public List<ConsultaSaldoCustodiosTotalesInDTO> getConsultaSaldoCustodiosTotales() {
        return consultaSaldoCustodiosTotales;
    }

    public void setConsultaSaldoCustodiosTotales(List<ConsultaSaldoCustodiosTotalesInDTO> consultaSaldoCustodiosTotales) {
        this.consultaSaldoCustodiosTotales = consultaSaldoCustodiosTotales;
    }
}
