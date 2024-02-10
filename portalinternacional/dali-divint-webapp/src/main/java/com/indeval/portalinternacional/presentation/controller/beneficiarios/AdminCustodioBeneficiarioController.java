package com.indeval.portalinternacional.presentation.controller.beneficiarios;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.event.ActionEvent;

import com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService;
import com.indeval.portalinternacional.middleware.servicios.vo.CatBicVO;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;

public class AdminCustodioBeneficiarioController extends ControllerBase{
	
    
    /** Constante que indica el ID de la pagina de consulta de beneficiarios */
    private static final String CONSULTA_CUSTODIOS_BENEFICIARIOS = "admonCustodiosBeneficiarios"; //$NON-NLS-1$
    
	private ControlBeneficiariosService controlBeneficiariosService;
	
	private Map<Long,String> mapaCustodios;
	
	private List<CatBicVO> listCustodios = new ArrayList<CatBicVO>();
	
	private boolean edicionHabilitada;

	
	public String getInit() {	
		if(listCustodios == null || listCustodios.isEmpty()){
			inicializaMapaCustodios();
		}	
		return null;
	}
	
	/**
	 * 
	 * @param event
	 * @return
	 */
    public String modificarAction() {
    	for (CatBicVO catBic : this.listCustodios) {			
			if (catBic.isEditar()) {
				controlBeneficiariosService.modificarCustodios(catBic);
			}
		}
    	this.inicializaMapaCustodios();
    	return CONSULTA_CUSTODIOS_BENEFICIARIOS;
    }

	
	private void inicializaMapaCustodios() {
		mapaCustodios = new HashMap<Long,String>();
		
		 listCustodios = controlBeneficiariosService.obtieneCatBicEntities();
		 		
	}
	
	/**
	 * 
	 * @param event
	 */
	public void cambiaEdicion(ActionEvent event) {
		this.edicionHabilitada = false;
    	for (Iterator<CatBicVO> iterator = listCustodios.iterator(); !this.edicionHabilitada && iterator.hasNext();) {
			if (iterator.next().isEditar()) {
				this.edicionHabilitada = true;
			}
		}
    }


	public ControlBeneficiariosService getControlBeneficiariosService() {
		return controlBeneficiariosService;
	}


	public void setControlBeneficiariosService(ControlBeneficiariosService controlBeneficiariosService) {
		this.controlBeneficiariosService = controlBeneficiariosService;
	}


	public Map<Long, String> getMapaCustodios() {
		return mapaCustodios;
	}


	public void setMapaCustodios(Map<Long, String> mapaCustodios) {
		this.mapaCustodios = mapaCustodios;
	}


	public List<CatBicVO> getListCustodios() {
		return listCustodios;
	}


	public void setListCustodios(List<CatBicVO> listCustodios) {
		this.listCustodios = listCustodios;
	}
	
	
	

}