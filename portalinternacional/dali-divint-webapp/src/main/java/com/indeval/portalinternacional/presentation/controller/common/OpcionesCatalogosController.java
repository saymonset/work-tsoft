/**
 * 
 */
package com.indeval.portalinternacional.presentation.controller.common;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import com.indeval.portaldali.middleware.services.util.ConsultaCatalogoService;
import com.indeval.portaldali.persistence.dao.common.BovedaDao;
import com.indeval.portaldali.persistence.dao.common.DivisaDao;
import com.indeval.portaldali.persistence.modelo.Boveda;
import com.indeval.portaldali.persistence.modelo.Divisa;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.EstatusOperacion;
import com.indeval.portalinternacional.middleware.servicios.modelo.TipoBeneficiario;

/**
 * Controller que atiende las solicitudes de los componentes tipo combo para Divisas, Estado, Operacion
 *     y Tipo de Operacion
 * 
 * @author Marcos Rivas Bermdez
 *
 * 2H Software
 */
public class OpcionesCatalogosController extends ControllerBase {

	private DivisaDao divisaDao = null;

	private DivisionInternacionalService divisionInternacionalService;
	
	BovedaDao bovedaDao = null;
    /** Acceso a la consulta de catálogos */
    private ConsultaCatalogoService consultaCatalogoService = null;
		
	/**
     * Obtiene una lista de <code>SelectItem</code> para popular el combo box de elección de divisa
     * 
     * @return
     */
    public List<SelectItem> getDivisas(){
    	Divisa[] listaDivisas = divisaDao.findDivisas();
    	List<SelectItem> listaSelectDivisas = new ArrayList<SelectItem>();
    	for (int i=0; i<listaDivisas.length; i++){
    		listaSelectDivisas.add(new SelectItem(listaDivisas[i].getClaveAlfabetica(), listaDivisas[i].getDescripcion()));
    	}
    	return listaSelectDivisas;
    }
    
	/**
     * Obtiene una lista de <code>SelectItem</code> para popular el combo box de elección de Estado
     * 
     * @return
     */
    public List<SelectItem> getEstados(){
    	EstatusOperacion[] estados = divisionInternacionalService.obtieneEstatusOperacion();
    	List <SelectItem> listaEstados = new ArrayList<SelectItem>();
    	for (int i=0; i<estados.length; i++){
//    		listaEstados.add(new SelectItem(estados[i].getDescEstatusOperacion()));
    	    listaEstados.add(new SelectItem(estados[i].getIdEstatusOperacion().toString(), estados[i].getDescEstatusOperacion()));
    	}

    	return listaEstados;
    }
    
    public List<SelectItem> getEstadosSinParcialidades(){
    	EstatusOperacion[] estados = divisionInternacionalService.obtieneEstatusOperacion();
    	List <SelectItem> listaEstados = new ArrayList<SelectItem>();
    	for (int i=0; i<estados.length; i++){
//    		listaEstados.add(new SelectItem(estados[i].getDescEstatusOperacion()));
    		if(!(estados[i].getIdEstatusOperacion() == Constantes.ST_OPER_CONFIRMADA_PARCIAL
    				|| estados[i].getIdEstatusOperacion() == Constantes.ST_OPER_LIBERADA_PARCIAL
    						|| estados[i].getIdEstatusOperacion() == Constantes.ST_OPER_REMANENTE_CANCELADO
    				|| estados[i].getIdEstatusOperacion() == Constantes.ST_OPER_PENDIENTE_LIBERAR_PRCIAL
    				|| estados[i].getIdEstatusOperacion() == Constantes.ST_OPER_ENVIO_CANCELACION_REMANENTE)){
        	    listaEstados.add(new SelectItem(estados[i].getIdEstatusOperacion().toString(), estados[i].getDescEstatusOperacion()));	
    		}
    	}

    	return listaEstados;
    }
    
	/**
     * Obtiene una lista de <code>SelectItem</code> para popular el combo box de elección de Estado
     * 
     * @return
     */
    public List<SelectItem> getEstadosParcialidades(){
    	EstatusOperacion[] estados = divisionInternacionalService.obtieneEstatusOperacion();
    	List <SelectItem> listaEstados = new ArrayList<SelectItem>();
    	for (int i=0; i<estados.length; i++){
    		// Filtro solo esatatus: CONFIRMADA PARCIAL, LIBERADA PARCIAL, PENDIENTE LIBERADA PARCIAL, CANCEL SIST
    		if(estados[i].getIdEstatusOperacion() == Constantes.ST_OPER_CONFIRMADA_PARCIAL
    				|| estados[i].getIdEstatusOperacion() == Constantes.ST_OPER_LIBERADA_PARCIAL
    				|| estados[i].getIdEstatusOperacion() == Constantes.ST_OPER_PENDIENTE_LIBERAR_PRCIAL
    				|| estados[i].getIdEstatusOperacion() == Constantes.ST_OPER_CANCEL_SIST){
        	    listaEstados.add(new SelectItem(estados[i].getIdEstatusOperacion().toString(), estados[i].getDescEstatusOperacion()));	
    		}
    	}

    	return listaEstados;
    }
    
	/**
     * Obtiene una lista de <code>SelectItem</code> para popular el combo box de elección de Operacion
     * 
     * @return
     */
    public List<SelectItem> getOperaciones(){
    	List<SelectItem> listaOperaciones = new ArrayList<SelectItem>();
    	listaOperaciones.add(new SelectItem(Constantes.TIPO_MOVTO_E, Constantes.MOVTO_ENTREGA));
    	listaOperaciones.add(new SelectItem(Constantes.TIPO_MOVTO_R, Constantes.MOVTO_RECIBE));
    	
    	return listaOperaciones;
    }
    
	/**
     * Obtiene una lista de <code>SelectItem</code> para popular el combo box de elección de Tipo de Operacion
     * 
     * @return
     */
    public List<SelectItem> getTiposOperacion(){
    	List<SelectItem> listaTipoOperaciones = new ArrayList<SelectItem>();
    	listaTipoOperaciones.add(new SelectItem(Constantes.TRASPASO_CONTRA, Constantes.DESC_TRASPASO_CONTRA));
    	listaTipoOperaciones.add(new SelectItem(Constantes.TRASPASO_LIBRE, Constantes.DESC_TRASPASO_LIBRE));
    	
    	return listaTipoOperaciones;
    }
    
    /**
     * Obtiene una lista de <code>SelectItem</code> para popular el combo box de elección de Tipo de Ingreso de la Operacion
     * 
     * @return
     */
    public List<SelectItem> getOrigenesOperacion(){
    	List<SelectItem> listaTipoIngresoOperaciones = new ArrayList<SelectItem>();
    	listaTipoIngresoOperaciones.add(new SelectItem(Constantes.CERO_STRING, Constantes.OPERACION_INGRESADA_POR_PORTAL));
    	listaTipoIngresoOperaciones.add(new SelectItem(Constantes.UNO_STRING, Constantes.OPERACION_INGRESADA_POR_PFI));
    	
    	return listaTipoIngresoOperaciones;
    }
    /**
     * Obtiene una lista de <code>SelectItem</code> para popular el combo box de elección de bóveda de valor
     * 
     * @return
     */
    public List<SelectItem> getBovedasValor() {

        List<Boveda> bovedas = consultaCatalogoService.buscarBovedasDeValor();
        List<SelectItem> listaSelectBovedas = new ArrayList<SelectItem>();

        for (Boveda boveda : bovedas) {
            listaSelectBovedas.add(new SelectItem(boveda.getIdBoveda().toString(), boveda.getDescripcion()));
        }
        return listaSelectBovedas;
    }
    
    public List<SelectItem> getListaAltas(){
    	List<SelectItem> listaAltas = new ArrayList<SelectItem>();
    	listaAltas.add(new SelectItem(Constantes.OPCION_TODOS_CRITERIO, Constantes.OPCION_TODOS_CRITERIO));
    	listaAltas.add(new SelectItem(Constantes.CPOS_VALUE, Constantes.CPOS_VALUE));
    	listaAltas.add(new SelectItem(Constantes.BADC_VALUE, Constantes.BADC_VALUE));
    	listaAltas.add(new SelectItem(Constantes.ADCP_VALUE, Constantes.ADCP_VALUE));
    	listaAltas.add(new SelectItem(Constantes.IADC_VALUE, Constantes.IADC_VALUE));
    	listaAltas.add(new SelectItem(Constantes.GADC_VALUE, Constantes.GADC_VALUE));
    	listaAltas.add(new SelectItem(Constantes.VIVI_VALUE, Constantes.VIVI_VALUE));    	
    	return listaAltas;
    }

    /**
     * Obtiene una lista de <code>SelectItem</code> para popular el combo box de eleccion de tipos formato 
     * @return El listado de objetos
     */
    public List<SelectItem> getTiposFormato() {
        List<TipoBeneficiario> tiposBeneficiario = this.getDivisionInternacionalService().getTiposBeneficiario();
        List<SelectItem> listaTiposBenef = new ArrayList<SelectItem>();
        for (TipoBeneficiario tb : tiposBeneficiario) {
            listaTiposBenef.add(new SelectItem(tb.getIdTipoBeneficiario(), tb.getDescTipoBeneficiario()));
        }

        return listaTiposBenef;
    }
    

	/**
	 * Devuelve el valor del campo divisaDao
	 * @return divisaDao
	 */
	public DivisaDao getDivisaDao() {
		return divisaDao;
	}

	/**
	 * Asigna divisaDao al campo divisaDao
	 * @param divisaDao el divisaDao que se asigna
	 */
	public void setDivisaDao(DivisaDao divisaDao) {
		this.divisaDao = divisaDao;
	}

	/**
	 * Devuelve el valor del campo consultaCatalogoService
	 * @return consultaCatalogoService
	 */
	public ConsultaCatalogoService getConsultaCatalogoService() {
		return consultaCatalogoService;
	}

	/**
	 * Asigna consultaCatalogoService al campo consultaCatalogoService
	 * @param consultaCatalogoService el consultaCatalogoService que se asigna
	 */
	public void setConsultaCatalogoService(
			ConsultaCatalogoService consultaCatalogoService) {
		this.consultaCatalogoService = consultaCatalogoService;
	}

  

    /**
     * @return the bovedaDao
     */
    public BovedaDao getBovedaDao() {
        return bovedaDao;
    }

    /**
     * @param bovedaDao the bovedaDao to set
     */
    public void setBovedaDao(BovedaDao bovedaDao) {
        this.bovedaDao = bovedaDao;
    }

    public DivisionInternacionalService getDivisionInternacionalService() {
        return divisionInternacionalService;
    }

    public void setDivisionInternacionalService(
            DivisionInternacionalService divisionInternacionalService) {
        this.divisionInternacionalService = divisionInternacionalService;
    }

}
