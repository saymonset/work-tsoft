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
import com.indeval.portalinternacional.middleware.servicios.modelo.HorarioBeneficiario;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;

public class AdminHorarioDerechoController   extends ControllerBase{

	private ControlBeneficiariosService controlBeneficiariosService;
	private AdminCatalogosBenefService adminCatalogosBenefService;
	private Map<Long,String> mapaCustodios;
	private List<HorarioBeneficiarioVO> lstHorarios;
	private DateUtilService dateUtilService;
	private HorarioBeneficiarioVO selectedHorario;
	
	
	public String getInit() {		
		if(mapaCustodios == null || mapaCustodios.isEmpty()){
			inicializaMapaCustodios();			
		}	
		
		if(lstHorarios == null || lstHorarios.isEmpty()){
			ejecutaConsulta();
		}

		return null;
	}
	
	public String agregarHorario() {	
		return Constantes.TO_AGREGAR_HORARIO;
	}
	
	public String actualizarHorario() {
		FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put(Constantes.KEY_HORARIO_SELECCIONADO, selectedHorario);
		return Constantes.TO_ACTUALIZAR_HORARIO;
	}
	
	public void eliminaHorario(ActionEvent event){
		boolean actualizarHorarios = false;
		HorarioBeneficiario horarioBeneficiario = null;
		if(lstHorarios != null && !lstHorarios.isEmpty()){
			horarioBeneficiario = new HorarioBeneficiario();
			for(HorarioBeneficiarioVO beneficiarioVO:lstHorarios){				
				if(beneficiarioVO.getEliminado().booleanValue()){
					horarioBeneficiario.setIdHorario(beneficiarioVO.getIdHorario());
					horarioBeneficiario.setFechaEliminacion(dateUtilService.getCurrentDate());
					adminCatalogosBenefService.eliminarHorario(horarioBeneficiario);
					actualizarHorarios = true;
				}
			}			
		}
		if(actualizarHorarios){
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
		List<HorarioBeneficiario> lstHorariosTmp = null;
		HorarioBeneficiarioVO horarioBeneficiarioVO = null;
		lstHorariosTmp = adminCatalogosBenefService.getHorariosCustodios();
		if(lstHorariosTmp != null && !lstHorariosTmp.isEmpty()){			
			lstHorarios = new ArrayList<HorarioBeneficiarioVO>();
			for(HorarioBeneficiario beneficiario:lstHorariosTmp){
				horarioBeneficiarioVO = new HorarioBeneficiarioVO();
				horarioBeneficiarioVO.transform(beneficiario);
				lstHorarios.add(horarioBeneficiarioVO);
			}
		}
	}

	public Map<Long, String> getMapaCustodios() {
		return mapaCustodios;
	}

	public List<HorarioBeneficiarioVO> getLstHorarios() {
		return lstHorarios;
	}

	public void setControlBeneficiariosService(
			ControlBeneficiariosService controlBeneficiariosService) {
		this.controlBeneficiariosService = controlBeneficiariosService;
	}

	public void setAdminCatalogosBenefService(
			AdminCatalogosBenefService adminCatalogosBenefService) {
		this.adminCatalogosBenefService = adminCatalogosBenefService;
	}

	public void setDateUtilService(DateUtilService dateUtilService) {
		this.dateUtilService = dateUtilService;
	}

	public HorarioBeneficiarioVO getSelectedHorario() {
		return selectedHorario;
	}

	public void setSelectedHorario(HorarioBeneficiarioVO selectedHorario) {
		this.selectedHorario = selectedHorario;
	}
}
