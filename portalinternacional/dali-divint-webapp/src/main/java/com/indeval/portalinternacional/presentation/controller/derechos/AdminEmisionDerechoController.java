package com.indeval.portalinternacional.presentation.controller.derechos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import com.indeval.portaldali.middleware.services.util.DateUtilService;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.AdminCatalogosBenefService;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.ExcepcionEmisionBenef;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;

public class AdminEmisionDerechoController   extends ControllerBase{
	
	private ControlBeneficiariosService controlBeneficiariosService;
	private AdminCatalogosBenefService adminCatalogosBenefService;
	private List<ExcepcionEmisionBenefVO> lstEmisiones;
	private Map<Long,String> mapaCustodios;
	private DateUtilService dateUtilService;
	private ExcepcionEmisionBenefVO selectedEmision;
	
	
	public String getInit() {		
		if(mapaCustodios == null || mapaCustodios.isEmpty()){
			inicializaMapaCustodios();			
		}		
		if(lstEmisiones == null || lstEmisiones.isEmpty()){
			ejecutaConsulta();	
		}		
		return null;
	}
	
	public String agregarEmision() {		
		return Constantes.TO_AGREGAR_EMISION_SIN_BENEF;
	}
	
	public String actualizarEmision() {		
		FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put(Constantes.KEY_EMISION_SELECCIONADA, selectedEmision);
		return Constantes.TO_ACTUALIZAR_EMISION_SIN_BENEF;
	}
	
	public void eliminaEmision(ActionEvent event){
		boolean actualizarEmisiones = false;
		ExcepcionEmisionBenef emisionBenef = null;
		
		if(lstEmisiones != null && !lstEmisiones.isEmpty()){
			emisionBenef = new ExcepcionEmisionBenef();
			for(ExcepcionEmisionBenefVO emisionBenefVO:lstEmisiones){
				if(emisionBenefVO.getEliminado().booleanValue()){
					emisionBenef.setFechaEliminacion(dateUtilService.getCurrentDate());
					emisionBenef.setIdExcepcionEmision(emisionBenefVO.getIdExcepcionEmision());
					adminCatalogosBenefService.eliminarEmisionSinBenef(emisionBenef);
					actualizarEmisiones = true;
				}
			}
		}
		if(actualizarEmisiones){
			ejecutaConsulta();
		}
	}
	
	private void inicializaMapaCustodios() {
		mapaCustodios = new HashMap<Long,String>();
		
		List<Object[]> lista = controlBeneficiariosService.obtieneAllCatBic();
		
		for( Object[] bene : lista ) {
			mapaCustodios.put((Long)bene[0], (String)bene[1]);
		}
	}
	
	private void ejecutaConsulta(){
		List<ExcepcionEmisionBenef> lstEmisionesTmp = null;	
		ExcepcionEmisionBenefVO benefVO = null;
		
		lstEmisionesTmp = adminCatalogosBenefService.getEmisionesSinBenef();		
		if(lstEmisionesTmp != null && !lstEmisionesTmp.isEmpty()){
			lstEmisiones = new ArrayList<ExcepcionEmisionBenefVO>();
			for(ExcepcionEmisionBenef benef : lstEmisionesTmp){
				benefVO = new ExcepcionEmisionBenefVO();
				benefVO.transform(benef);
				lstEmisiones.add(benefVO);
			}
		}
	}

	public List<ExcepcionEmisionBenefVO> getLstEmisiones() {
		return lstEmisiones;
	}

	public void setLstEmisiones(List<ExcepcionEmisionBenefVO> lstEmisiones) {
		this.lstEmisiones = lstEmisiones;
	}

	public Map<Long,String> getMapaCustodios() {
		return mapaCustodios;
	}

	public void setMapaCustodios(Map<Long,String> mapaCustodios) {
		this.mapaCustodios = mapaCustodios;
	}

	public void setAdminCatalogosBenefService(
			AdminCatalogosBenefService adminCatalogosBenefService) {
		this.adminCatalogosBenefService = adminCatalogosBenefService;
	}

	public void setControlBeneficiariosService(
			ControlBeneficiariosService controlBeneficiariosService) {
		this.controlBeneficiariosService = controlBeneficiariosService;
	}

	public void setDateUtilService(DateUtilService dateUtilService) {
		this.dateUtilService = dateUtilService;
	}

	public ExcepcionEmisionBenefVO getSelectedEmision() {
		return selectedEmision;
	}

	public void setSelectedEmision(ExcepcionEmisionBenefVO selectedEmision) {
		this.selectedEmision = selectedEmision;
	}
}
