/**
 * 
 */
package com.indeval.portalinternacional.presentation.controller.eventosCorporativos;

import java.util.regex.Pattern;

import javax.faces.event.ActionEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.ConsultaEventosCorporativosService;
import com.indeval.portalinternacional.middleware.servicios.modelo.eventosCorporativos.TipoDerechoDto;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;

/**
 * @author kode-edhy
 *
 */
public class TiposDerechoController extends ControllerBase{

	@SuppressWarnings("unused")
	private Pattern pattern;
	private static final Logger log = LoggerFactory.getLogger(TiposDerechoController.class);
    private PaginaVO paginaReportes;
    private ConsultaEventosCorporativosService consultaEventosCorporativosService;
    private String idTipoDerecho;
    private String activo;
	private int totalPaginas = 1;	
	private PaginaVO resultados = null;
	private TipoDerechoDto tipoDerecho;
	private boolean consultaEjecutada;
	private int totalRegistros = 0;
	private String nombre;
	
    private boolean banderaBitacoraConsulta = false;
    
    public TiposDerechoController() {
		// TODO Auto-generated constructor stub

    }
    
	/**
	 * Inicializa el bean
	 * @return String
	 */
	public String getInit()
	{
		log.info("ENTRA A MVC DE TiposDerechoController ");
		if(paginaVO == null)
			paginaVO = new PaginaVO();
		
		this.idTipoDerecho="-1";
		this.activo="-1";
		paginaVO.setRegistrosXPag(50);
		paginaVO.setOffset(0);
		banderaBitacoraConsulta = false;	
		return "";
	}
	
	/**
	 * Limpia todos los campos
	 * @param evt
	 */
	public void limpiar(ActionEvent evt)
	{
		log.info("SI ENTRA A LIMPIAR FILTROS::");
        banderaBitacoraConsulta = false;
		this.idTipoDerecho="-1";
		this.activo="-1";
		this.nombre=null;
		if(resultados != null)
			resultados.getRegistros().clear();
		if(paginaVO.getRegistros() != null) {
			paginaVO.getRegistros().clear();
		}
		paginaVO.setRegistrosXPag(50);
		paginaVO.setOffset(0);
		setConsultaEjecutada(false);
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
	 * Ejecuta la consulta y calcula el numero de pagina para la paginacion.
	 *  Este metodo es un overide de la clase padre
	 */
	public String ejecutarConsulta(){
		setParams();
		paginaVO = consultaEventosCorporativosService.getCatalogoTiposDerecho(tipoDerecho, paginaVO);
		
		totalPaginas = paginaVO.getTotalRegistros() / paginaVO.getRegistrosXPag();
		
		if(paginaVO.getTotalRegistros() % paginaVO.getRegistrosXPag() > 0)
			totalPaginas++;		
		totalPaginas = (totalPaginas <= 0) ? 1 : totalPaginas;
		setConsultaEjecutada(true);
		return null;
	}
	
	
	private void setParams() 
	{
		tipoDerecho= new TipoDerechoDto();		
		tipoDerecho.setTipoDerecho(this.idTipoDerecho);
		tipoDerecho.setActivo(this.activo);
		tipoDerecho.setNombre(this.nombre);		
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
		paginaReportes.setRegistros(null);
		log.info("si entra..."+ tipoDerecho.getActivo()+"  ... "+ tipoDerecho.getTipoDerecho());
		paginaReportes =consultaEventosCorporativosService.getCatalogoTiposDerecho(tipoDerecho, paginaReportes);		
		this.totalRegistros=paginaReportes.getTotalRegistros();
		
	}
	
	/**
	 * @return the idTipoDerecho
	 */
	public String getIdTipoDerecho() {
		return idTipoDerecho;
	}

	/**
	 * @param idTipoDerecho the idTipoDerecho to set
	 */
	public void setIdTipoDerecho(String idTipoDerecho) {
		this.idTipoDerecho = idTipoDerecho;
	}


	/**
	 * @return the activo
	 */
	public String getActivo() {
		return activo;
	}

	/**
	 * @param activo the activo to set
	 */
	public void setActivo(String activo) {
		this.activo = activo;
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
	 * @return the tipoDerecho
	 */
	public TipoDerechoDto getTipoDerecho() {
		return tipoDerecho;
	}

	/**
	 * @param tipoDerecho the tipoDerecho to set
	 */
	public void setTipoDerecho(TipoDerechoDto tipoDerecho) {
		this.tipoDerecho = tipoDerecho;
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
	 * @return the banderaBitacoraConsulta
	 */
	public boolean isBanderaBitacoraConsulta() {
		return banderaBitacoraConsulta;
	}

	/**
	 * @param banderaBitacoraConsulta the banderaBitacoraConsulta to set
	 */
	public void setBanderaBitacoraConsulta(boolean banderaBitacoraConsulta) {
		this.banderaBitacoraConsulta = banderaBitacoraConsulta;
	}

	/**
	 * @return the consultaEventosCorporativosService
	 */
	public ConsultaEventosCorporativosService getConsultaEventosCorporativosService() {
		return consultaEventosCorporativosService;
	}
	
	/**
	 * @param consultaEventosCorporativosService the consultaEventosCorporativosService to set
	 */
	public void setConsultaEventosCorporativosService(
			ConsultaEventosCorporativosService consultaEventosCorporativosService) {
		this.consultaEventosCorporativosService = consultaEventosCorporativosService;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
