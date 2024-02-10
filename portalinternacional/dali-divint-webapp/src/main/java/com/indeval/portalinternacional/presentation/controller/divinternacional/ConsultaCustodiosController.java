package com.indeval.portalinternacional.presentation.controller.divinternacional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

import com.bursatec.seguridad.vo.InstitucionVO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO;
import com.indeval.portalinternacional.common.util.ConsultaCatalogosFacade;
import com.indeval.portalinternacional.middleware.servicios.dto.CuentaCorresponsalDTO;
import com.indeval.portalinternacional.middleware.servicios.dto.DivisaDTO;
import com.indeval.portalinternacional.middleware.servicios.dto.InstitucionWebDTO;
import com.indeval.portalinternacional.middleware.servicios.vo.MovimientoEfectivoInternacionalVO;
import com.indeval.portalinternacional.presentation.controller.MovimientosEfectivoController;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.dao.common.DivisaDao;
import com.indeval.portaldali.persistence.modelo.Divisa;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.Control;
import com.indeval.portalinternacional.middleware.servicios.modelo.Custodio;
import com.indeval.portalinternacional.middleware.servicios.modelo.EstadoDerechoInt;
import com.indeval.portalinternacional.middleware.servicios.modelo.TipoPagoInt;
import com.indeval.portalinternacional.middleware.servicios.vo.CalendarioEmisionesDeudaExtDTO;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;

/**
 * 
 * @author Edgar Hernandez Garcia
 *
 */
public class ConsultaCustodiosController extends ControllerBase{
	private static final Logger LOG = LoggerFactory.getLogger(ConsultaCustodiosController.class);
	private Pattern pattern; 
	//private static final Logger log = LoggerFactory.getLogger(ConsultaCustodiosController.class);

	private MovimientoEfectivoInternacionalVO efectivoInternacionalVO;
	private ConsultaCatalogosFacade catalogosFacade;
	/** Listas */
	private List<SelectItem> listaCustodios;
	private List<SelectItem> bovedas;
	public List<SelectItem> listaDivisa;
	private DivisaDao divisaDao;
	private DivisionInternacionalService divisionInternacionalService;
	private boolean consultaEjecutada;
	private int totalPaginas = 1;
	private PaginaVO resultados = null;
    private boolean banderaBitacoraConsulta = false;
	private int totalRegistros = 0;
	private String custodio;
	private String cuenta;
	private String divisa;
	private Boolean iscalIndeval;
	/** Pagina para los reportes*/
    private PaginaVO paginaReportes;
	private static final String DEPOSITO = "0";

    
   // public ConsultaCustodiosController(){
   // 	this.pattern = Pattern.compile("/consultaSaldoCustodios.faces$");
    //	this.iscalIndeval =pattern.matcher(getRequestURL()).find();
	// }
	/**
	 * Inicializa el bean
	 * @return
	 */
	public String getInicializar() {
		bovedas = new ArrayList<SelectItem>();
		listaDivisa = new ArrayList<SelectItem>();

		listaDivisa.add(new SelectItem("0", "Seleccione una Divisa"));
		bovedas.add(new SelectItem("0", "Ingrese una Divisa Primero"));

		listaDivisa = getDivisas();


		LOG.info("Iniciando modulo de movimientos de efectivo internacional");



		efectivoInternacionalVO = new MovimientoEfectivoInternacionalVO();
		efectivoInternacionalVO.setTipoMovimiento(DEPOSITO);
		efectivoInternacionalVO.setFechaLiquidacion(new Date());


		return null;
	}


	/**
	 * Buscar las emisiones
	 * @param evt
	 */
	public void buscarDerechos(ActionEvent evt)
	{
        paginaVO.setRegistrosXPag(50);
		paginaVO.setOffset(0);
        getPaginaVO().setRegistros(null);
		ejecutarConsulta();

	}
	
	/**
	 * Genera los reportes de Consulta de Emisiones
	 * @param evt
	 */
	public void generarReportes(ActionEvent evt)
	{	

		reiniciarEstadoPeticion();
		paginaReportes = new PaginaVO();
		paginaReportes.setOffset(0);
		paginaReportes.setRegistrosXPag(PaginaVO.TODOS);		
		// paginaReportes = divisionInternacionalService.consultaCalendarioDerechos(calendario, paginaReportes);
		this.totalRegistros=paginaReportes.getTotalRegistros();
		
	}
	/**
	 * Ejecuta la consulta y calcula el numero de pagina para la paginacion.
	 *  Este metodo es un overide de la clase padre
	 */
	public String ejecutarConsulta(){
		
		setParams();
		
		//paginaVO = divisionInternacionalService.consultaCalendarioDerechos(calendario, paginaVO);
        
		totalPaginas = paginaVO.getTotalRegistros() / paginaVO.getRegistrosXPag();
		
		if(paginaVO.getTotalRegistros() % paginaVO.getRegistrosXPag() > 0)
			totalPaginas++;		
		totalPaginas = (totalPaginas <= 0) ? 1 : totalPaginas;
		
		setConsultaEjecutada(true);
		return null;
	}
	
	private void setParams() {
		}

	/**
	 * Limpia todos los campos
	 * @param evt
	 */
	public void limpiar(ActionEvent evt)
	{
        banderaBitacoraConsulta = false;
         //calendario.init();		
        this.custodio="-1";
 		this.divisa="-1";
 		if(resultados != null)
			resultados.getRegistros().clear();
		if(paginaVO.getRegistros() != null) {
			paginaVO.getRegistros().clear();
		}
		paginaVO.setRegistrosXPag(50);
		paginaVO.setOffset(0);		
		
		setConsultaEjecutada(false);
	}
	private void inicializaDivisa() {
		this.listaDivisa=getDivisas();
	}
	
	@SuppressWarnings("unchecked")
	public List<SelectItem> getDivisas(){
    	if(this.listaDivisa != null && this.listaDivisa.size() > 1){
			return this.listaDivisa;
		}
    	Divisa[] listaDivisas = divisaDao.findDivisas();
    	List<SelectItem> listaSelectDivisas = new ArrayList<SelectItem>();
    	for (int i=0; i<listaDivisas.length; i++){
    		listaSelectDivisas.add(new SelectItem(""+listaDivisas[i].getIdDivisa(), listaDivisas[i].getDescripcion()));
    	}
    	return listaSelectDivisas;
    }
	//Agregado para proyecto multidivisas
	public void seleccionarDivisa(ActionEvent event) {
		try {
			final DivisaDTO divisaDTO = new DivisaDTO();
			divisaDTO.setId(Long.parseLong((String) listaDivisa.get(0).getValue()));
			divisaDTO.setClaveAlfabetica(listaDivisa.get(0).getLabel());
			efectivoInternacionalVO.setDivisa(divisaDTO);
			LOG.info("divisaDTO.getId():" + efectivoInternacionalVO.getDivisa().getId());
			LOG.info(divisa);
			bovedas = catalogosFacade.getSelectItemsBovedasEfectivoPorDivisa(efectivoInternacionalVO.getDivisa());
			for (SelectItem item : listaDivisa) {
				if(item.getValue().equals(efectivoInternacionalVO.getDivisa().getIdString())) {
					efectivoInternacionalVO.getDivisa().setClaveAlfabetica(item.getLabel());
				}
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
		}
	}
	/**
	 * 
	 */

	
	
	/**
	 * Inicializa lista de custodios
	 */

	
	public boolean isConsultaEjecutada() {
		return consultaEjecutada;
	}

	public void setConsultaEjecutada(boolean consultaEjecutada) {
		this.consultaEjecutada = consultaEjecutada;
	}

	/**
	 * @return the listaCustodios
	 */
	public List getListaCustodios() {
		return listaCustodios;
	}

	public void setListaCustodios(List listaCustodios) {
		this.listaCustodios = listaCustodios;
	}

	public DivisionInternacionalService getDivisionInternacionalService() {
		return divisionInternacionalService;
	}

	public void setDivisionInternacionalService(
			DivisionInternacionalService divisionInternacionalService) {
		this.divisionInternacionalService = divisionInternacionalService;
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

    public boolean isBanderaBitacoraConsulta() {
        return banderaBitacoraConsulta;
    }

    public void setBanderaBitacoraConsulta(boolean banderaBitacoraConsulta) {
        this.banderaBitacoraConsulta = banderaBitacoraConsulta;
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
	 * @return the divisa
	 */
	public String getDivisa() {
		return divisa;
	}

	/**
	 * @param divisa the divisa to set
	 */
	public void setDivisa(String divisa) {
		this.divisa = divisa;
	}


	/**
	 * @param divisaDao the divisaDao to set
	 */
	public void setDivisaDao(DivisaDao divisaDao) {
		this.divisaDao = divisaDao;
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
	
	public String getSelectedDivisa(){
		String resultado = getSelected(getDivisa() ,this.listaDivisa);
		if(resultado != null){
			return resultado;
		}
		return "TODAS"; 
	}
	public String getSelectedCustodio(){
		String resultado = getSelected(getCustodio() ,this.listaCustodios);
		if(resultado != null){
			return resultado;
		}
		return "TODOS"; 
	}
	private String getSelected(String key, List<SelectItem> lista){
		String resultado = null;
		for(SelectItem item : lista){
			if(key != null && item.getValue().equals(key))
				resultado=item.getLabel();
		}
		return resultado;		
	}
	private String getRequestURL(){
	    Object request = FacesContext.getCurrentInstance().getExternalContext().getRequest();
	    if(request instanceof HttpServletRequest){
	            return ((HttpServletRequest) request).getRequestURL().toString();
	    }else{
	        return null;
	    }
	}
	public MovimientoEfectivoInternacionalVO getEfectivoInternacionalVO() {
		return efectivoInternacionalVO;
	}

	public void setEfectivoInternacionalVO(MovimientoEfectivoInternacionalVO efectivoInternacionalVO) {
		this.efectivoInternacionalVO = efectivoInternacionalVO;
	}

	public List<SelectItem> getBovedas() {
		return bovedas;
	}

	public void setBovedas(List<SelectItem> bovedas) {
		this.bovedas = bovedas;
	}

	public void seleccionarBoveda(ActionEvent event) {
		//Integer idBoveda = getIdBoveda();

	}

	public void setListaDivisa (List<SelectItem> listaDivisa){
		this.listaDivisa = listaDivisa;
	}

	public List<SelectItem> getListaDivisa() {
		return listaDivisa;
	}

	private void checkin (){
		LOG.info("Punto de control");
	}
}