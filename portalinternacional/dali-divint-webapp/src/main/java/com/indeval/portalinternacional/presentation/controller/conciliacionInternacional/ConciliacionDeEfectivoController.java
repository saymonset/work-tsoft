package com.indeval.portalinternacional.presentation.controller.conciliacionInternacional;

import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import java.util.*;

import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.common.util.ConsultaCatalogosFacade;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.BovedaService;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.ConciliacionDivisasIntService;
import com.indeval.portalinternacional.middleware.servicios.dto.ConciliacionDivisasIntDTO;
import com.indeval.portalinternacional.middleware.servicios.vo.ConciliacionDivisasVO;
import com.indeval.portalinternacional.middleware.servicios.vo.ConciliacionEfectivoIntDTO;
import com.indeval.portalinternacional.middleware.servicios.vo.MovimientoEfectivoInternacionalVO;
import com.indeval.portalinternacional.presentation.controller.MovimientosEfectivoController;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConciliacionDeEfectivoController extends ControllerBase {

    private Set<String> divisaSelected;

    private ConciliacionDivisasIntDTO conciliacionDivisasIntDTO;

    private List<ConciliacionDivisasVO> conciliations;

    private ConciliacionDivisasIntService conciliacionDivisasIntService;

    private ConciliacionEfectivoIntDTO conciliacionEfectivo;
    private boolean consultaEjecutada;
    private PaginaVO resultados = null;
    private int totalRegistros = 0;
    private boolean totales;
    private double totalSaldoFinal;
    private double totalSaldoBoveda;
    private double totalDiferencia;

    private PaginaVO paginaReportes;

    private List<SelectItem> bovedas;

    private ConsultaCatalogosFacade catalogosFacade;

    private String boveda;

    private String divisa;

    private BovedaService bovedaService;

    private static final Logger log = LoggerFactory.getLogger(MovimientosEfectivoController.class);

    private MovimientoEfectivoInternacionalVO efectivoInternacionalVO;

    private List<SelectItem> divisas;

    public String getInicializar() {
        log.info("Inicializar :: ConciliacionDeEfectivoController");
        bovedas = new ArrayList<SelectItem>();
        divisas = new ArrayList<SelectItem>();

        divisaSelected = new HashSet<String>();
        divisaSelected.add("TODAS");

        log.info("Iniciando modulo de conciliacion de efectivo");
        bovedas = catalogosFacade.getSelectItemsBovedasEfectivo();

        divisas.add(new SelectItem("-1", "Seleccione una Divisa"));
        divisas.addAll(catalogosFacade.getSelectItemsTipoDivisa());

        efectivoInternacionalVO = new MovimientoEfectivoInternacionalVO();

        return "";
    }

    public void findConciliationsForeignExchange(ActionEvent evt) {
        findConciliationsForeignExchange();
    }

    public void findConciliationsForeignExchange() {
        conciliations = new ArrayList<ConciliacionDivisasVO>();

        String idDivisa = efectivoInternacionalVO.getDivisa().getIdString();
        String idBoveda = efectivoInternacionalVO.getBoveda().getIdBovedaStr();

        conciliations = conciliacionDivisasIntService.getAllBy(Integer.parseInt(idBoveda), Integer.parseInt(idDivisa));

        setConsultaEjecutada(true);
    }

    public List<ConciliacionDivisasVO> getConciliations() {
        return conciliations;
    }

    public void setConciliations(List<ConciliacionDivisasVO> conciliations) {
        this.conciliations = conciliations;
    }

    public String getBovedaDescriptionByIdBoveda(String idBoveda) {
        String bovedaDescription = null;

        for (int i = 0; i < bovedas.size(); i++) {
            if (bovedas.get(i).getValue().equals(idBoveda)) {
                bovedaDescription = bovedas.get(i).getLabel();
                break;
            }
        }

        return bovedaDescription;
    }

    public String getClaveAlfabeticaByIdDivisa(String idDivisa) {
        String claveAlfabetica = null;

        for (int i = 0; i < divisas.size(); i++) {
            if (divisas.get(i).getValue().equals(idDivisa)) {
                claveAlfabetica = divisas.get(i).getLabel();
                break;
            }
        }

        return claveAlfabetica;
    }

    public ConciliacionDivisasIntService getConciliacionDivisasIntService() {
        return conciliacionDivisasIntService;
    }

    public void setConciliacionDivisasIntService(ConciliacionDivisasIntService conciliacionDivisasIntService) {
        this.conciliacionDivisasIntService = conciliacionDivisasIntService;
    }

    /**
     * Limpia todos los campos
     *
     * @param evt
     */
    public void limpiar(ActionEvent evt) {
        limpiar();
    }

    public void limpiar() {
        bovedas = new ArrayList<>();
        divisas = new ArrayList<>();

        divisaSelected = new HashSet<>();
        divisaSelected.add("TODAS");

        bovedas = catalogosFacade.getSelectItemsBovedasEfectivo();

        divisas.add(new SelectItem("-1", "Seleccione una Divisa"));
        divisas.addAll(catalogosFacade.getSelectItemsTipoDivisa());

        efectivoInternacionalVO = new MovimientoEfectivoInternacionalVO();

        setConsultaEjecutada(false);
    }

    public List<SelectItem> getBovedas() {
        return bovedas;
    }

    public void setBovedas(List<SelectItem> bovedas) {
        this.bovedas = bovedas;
    }

    public ConsultaCatalogosFacade getCatalogosFacade() {
        return catalogosFacade;
    }

    public void setCatalogosFacade(ConsultaCatalogosFacade catalogosFacade) {
        this.catalogosFacade = catalogosFacade;
    }

    public BovedaService getBovedaService() {
        return bovedaService;
    }

    public void setBovedaService(BovedaService bovedaService) {
        this.bovedaService = bovedaService;
    }

    public MovimientoEfectivoInternacionalVO getEfectivoInternacionalVO() {
        return efectivoInternacionalVO;
    }

    public void setEfectivoInternacionalVO(MovimientoEfectivoInternacionalVO efectivoInternacionalVO) {
        this.efectivoInternacionalVO = efectivoInternacionalVO;
    }

    /**
     * Genera los reportes de consulta de conciliaciones de efectuvo
     *
     * @param event
     */
    public void generarReportes(ActionEvent event) {
        reiniciarEstadoPeticion();

        conciliations = new ArrayList<ConciliacionDivisasVO>();

        String idDivisa = efectivoInternacionalVO.getDivisa().getIdString();
        String idBoveda = efectivoInternacionalVO.getBoveda().getIdBovedaStr();

        conciliations = conciliacionDivisasIntService.getAllBy(Integer.parseInt(idBoveda), Integer.parseInt(idDivisa));

        setTotalRegistros(conciliations.size());
    }

    public List<SelectItem> getDivisas() {
        return divisas;
    }

    public void setDivisas(List<SelectItem> divisas) {
        this.divisas = divisas;
    }

    /**
     * @return the resultados
     */
    public PaginaVO getResultados() {
        return resultados;
    }

    /**
     * @param resultados the resultados to set
     */
    public void setResultados(PaginaVO resultados) {
        this.resultados = resultados;
    }

    /**
     * @param consultaEjecutada the consultaEjecutada to set
     */
    public void setConsultaEjecutada(boolean consultaEjecutada) {
        this.consultaEjecutada = consultaEjecutada;
    }

    /**
     * @return the consultaEjecutada
     */
    public boolean isConsultaEjecutada() {
        return consultaEjecutada;
    }

    /**
     * @return the totales
     */
    public boolean isTotales() {
        return totales;
    }

    /**
     * @param totales the totales to set
     */
    public void setTotales(boolean totales) {
        this.totales = totales;
    }

    /**
     * @return the totalSaldoFinal
     */
    public double getTotalSaldoFinal() {
        return totalSaldoFinal;
    }

    /**
     * @param totalSaldoFinal the totalSaldoFinal to set
     */
    public void setTotalSaldoFinal(double totalSaldoFinal) {
        this.totalSaldoFinal = totalSaldoFinal;
    }

    /**
     * @return the totalSaldoBoveda
     */
    public double getTotalSaldoBoveda() {
        return totalSaldoBoveda;
    }

    /**
     * @param totalSaldoBoveda the totalSaldoBoveda to set
     */
    public void setTotalSaldoBoveda(double totalSaldoBoveda) {
        this.totalSaldoBoveda = totalSaldoBoveda;
    }

    /**
     * @return the totalDiferencia
     */
    public double getTotalDiferencia() {
        return totalDiferencia;
    }

    /**
     * @param totalDiferencia the totalDiferencia to set
     */
    public void setTotalDiferencia(double totalDiferencia) {
        this.totalDiferencia = totalDiferencia;
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

    public int getTotalRegistros() {
        return totalRegistros;
    }

    public void setTotalRegistros(int totalRegistros) {
        this.totalRegistros = totalRegistros;
    }
}
