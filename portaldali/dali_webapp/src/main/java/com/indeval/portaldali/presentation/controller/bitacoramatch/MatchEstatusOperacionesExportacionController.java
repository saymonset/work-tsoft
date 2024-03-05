/**
 * CMMV - Portal Dali
 * Copyrigth (c) 2016 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.presentation.controller.bitacoramatch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.el.ELContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import com.indeval.portaldali.middleware.dto.criterio.CriterioMatchOperacionesDTO;
import com.indeval.portaldali.middleware.dto.criterio.CriterioMatchOperacionesExportacionDTO;
import com.indeval.portaldali.middleware.model.util.ConsultaOperacionesMatch;
import com.indeval.portaldali.middleware.services.estatus.ConsultaEstatusOperacionesMatchService;
import com.indeval.portaldali.presentation.controller.common.ControllerBase;

/**
 * Clase de tipo controller para realizar las acciones necesarias para generar los reportes
 * de Operaciones y Match.
 * 
 * @author Pablo Balderas
 */
public class MatchEstatusOperacionesExportacionController extends ControllerBase {

    /** Nombre del bean de consulta */
    private final String BEAN_CONSULTA = "matchEstatusBean";
	
	/** Objeto con los criterios de busqueda */
	private CriterioMatchOperacionesDTO criterio;
	
	/** Lista con los registros a exportar */
	private List<ConsultaOperacionesMatch> resultados;
	
	/** Servicio para realizar las consultas */
	private ConsultaEstatusOperacionesMatchService consultaEstatusOperacionesMatchService;

	/** Campos para los totales de la consulta */
	private long titulosTraspasante = 0;
	private long titulosReceptor = 0;
	private long netoTitulos = 0;
	private double montoTraspasante = 0;
	private double montoReceptor = 0;
	private double netoMonto = 0;
	
	/** El número de registros encontrados para los reportes */
	private Integer registrosEncontrados = 0;

	/**
	 * 
	 * @param e
	 */
	public void generarReportes(ActionEvent e) {
        reiniciarEstadoPeticion();
		ELContext elContext = FacesContext.getCurrentInstance().getELContext();
		MatchEstatusOperacionesBean matchEstatusOperacionesBean =
            (MatchEstatusOperacionesBean) FacesContext.getCurrentInstance().getApplication().
                getELResolver().getValue(elContext, null, this.BEAN_CONSULTA);


		try {
		    matchEstatusOperacionesBean.crearCriterio();
		    criterio = matchEstatusOperacionesBean.getCriterio();
		    //CriterioMatchOperacionesExportacionDTO criterioExport = this.obtenerCriterioExportacion();
		    CriterioMatchOperacionesExportacionDTO criterioExport = matchEstatusOperacionesBean.obtenerCriterioExportacion();
		    //criterio = matchEstatusOperacionesBean.obtenerCriterioExportacion();

		    Map<Object, Object> resultadosExtras = new HashMap<Object, Object>();
		    resultados = consultaEstatusOperacionesMatchService.getOperacionesValorExportacion(criterioExport, resultadosExtras);

		    titulosTraspasante = ((Number) resultadosExtras.get(ConsultaEstatusOperacionesMatchService.TOTAL_TITULOS_TRASPASANTE)).longValue();
		    titulosReceptor = ((Number) resultadosExtras.get(ConsultaEstatusOperacionesMatchService.TOTAL_TITULOS_RECEPTOR)).longValue();
		    netoTitulos = titulosTraspasante - titulosReceptor;

		    montoTraspasante = ((Number) resultadosExtras.get(ConsultaEstatusOperacionesMatchService.TOTAL_MONTO_TRASPASANTE)).doubleValue();
		    montoReceptor = ((Number) resultadosExtras.get(ConsultaEstatusOperacionesMatchService.TOTAL_MONTO_RECEPTOR)).doubleValue();

		    netoMonto = montoTraspasante - montoReceptor;
		    registrosEncontrados = resultados.size();
		} catch (Exception ex) {
            ex.printStackTrace();
        }
	}

	/**
	 * Método para establecer el atributo consultaEstatusOperacionesMatchService
	 * @param consultaEstatusOperacionesMatchService El valor del atributo consultaEstatusOperacionesMatchService a establecer.
	 */
	public void setConsultaEstatusOperacionesMatchService(
			ConsultaEstatusOperacionesMatchService consultaEstatusOperacionesMatchService) {
		this.consultaEstatusOperacionesMatchService = consultaEstatusOperacionesMatchService;
	}

	/**
	 * Método para obtener el atributo criterio
	 * @return El atributo criterio
	 */
	public CriterioMatchOperacionesDTO getCriterio() {
		return criterio;
	}

	/**
	 * Método para establecer el atributo criterio
	 * @param criterio El valor del atributo criterio a establecer.
	 */
	/*public void setCriterio(CriterioMatchOperacionesDTO criterio) {
		this.criterio = criterio;
	}*/

	/**
	 * Método para obtener el atributo resultados
	 * @return El atributo resultados
	 */
	public List<ConsultaOperacionesMatch> getResultados() {
		return resultados;
	}

	/**
	 * Método para establecer el atributo resultados
	 * @param resultados El valor del atributo resultados a establecer.
	 */
	public void setResultados(List<ConsultaOperacionesMatch> resultados) {
		this.resultados = resultados;
	}

	/**
	 * Método para obtener el atributo titulosTraspasante
	 * @return El atributo titulosTraspasante
	 */
	public long getTitulosTraspasante() {
		return titulosTraspasante;
	}

	/**
	 * Método para establecer el atributo titulosTraspasante
	 * @param titulosTraspasante El valor del atributo titulosTraspasante a establecer.
	 */
	public void setTitulosTraspasante(long titulosTraspasante) {
		this.titulosTraspasante = titulosTraspasante;
	}

	/**
	 * Método para obtener el atributo titulosReceptor
	 * @return El atributo titulosReceptor
	 */
	public long getTitulosReceptor() {
		return titulosReceptor;
	}

	/**
	 * Método para establecer el atributo titulosReceptor
	 * @param titulosReceptor El valor del atributo titulosReceptor a establecer.
	 */
	public void setTitulosReceptor(long titulosReceptor) {
		this.titulosReceptor = titulosReceptor;
	}

	/**
	 * Método para obtener el atributo netoTitulos
	 * @return El atributo netoTitulos
	 */
	public long getNetoTitulos() {
		return netoTitulos;
	}

	/**
	 * Método para establecer el atributo netoTitulos
	 * @param netoTitulos El valor del atributo netoTitulos a establecer.
	 */
	public void setNetoTitulos(long netoTitulos) {
		this.netoTitulos = netoTitulos;
	}

	/**
	 * Método para obtener el atributo montoTraspasante
	 * @return El atributo montoTraspasante
	 */
	public double getMontoTraspasante() {
		return montoTraspasante;
	}

	/**
	 * Método para establecer el atributo montoTraspasante
	 * @param montoTraspasante El valor del atributo montoTraspasante a establecer.
	 */
	public void setMontoTraspasante(double montoTraspasante) {
		this.montoTraspasante = montoTraspasante;
	}

	/**
	 * Método para obtener el atributo montoReceptor
	 * @return El atributo montoReceptor
	 */
	public double getMontoReceptor() {
		return montoReceptor;
	}

	/**
	 * Método para establecer el atributo montoReceptor
	 * @param montoReceptor El valor del atributo montoReceptor a establecer.
	 */
	public void setMontoReceptor(double montoReceptor) {
		this.montoReceptor = montoReceptor;
	}

	/**
	 * Método para obtener el atributo netoMonto
	 * @return El atributo netoMonto
	 */
	public double getNetoMonto() {
		return netoMonto;
	}

	/**
	 * Método para establecer el atributo netoMonto
	 * @param netoMonto El valor del atributo netoMonto a establecer.
	 */
	public void setNetoMonto(double netoMonto) {
		this.netoMonto = netoMonto;
	}

	/**
	 * Método para obtener el atributo registrosEncontrados
	 * @return El atributo registrosEncontrados
	 */
	public Integer getRegistrosEncontrados() {
		return registrosEncontrados;
	}

	/**
	 * Método para establecer el atributo registrosEncontrados
	 * @param registrosEncontrados El valor del atributo registrosEncontrados a establecer.
	 */
	public void setRegistrosEncontrados(Integer registrosEncontrados) {
		this.registrosEncontrados = registrosEncontrados;
	}
	
}
