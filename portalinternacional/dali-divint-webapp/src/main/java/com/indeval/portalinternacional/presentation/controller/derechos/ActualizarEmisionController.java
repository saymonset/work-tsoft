package com.indeval.portalinternacional.presentation.controller.derechos;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJBException;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.apache.commons.lang.StringUtils;

import com.indeval.portalinternacional.middleware.services.divisioninternacional.AdminCatalogosBenefService;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService;
import com.indeval.portalinternacional.middleware.servicios.constantes.Constantes;
import com.indeval.portalinternacional.middleware.servicios.modelo.ExcepcionEmisionBenef;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;

public class ActualizarEmisionController  extends ControllerBase{

	
	private static final String TO_EMISIONES = "adminEmisionDerecho";
	
	private ExcepcionEmisionBenefVO selectedEmision;
	private List<SelectItem> custodios;
	private ControlBeneficiariosService controlBeneficiariosService;
	private AdminCatalogosBenefService adminCatalogosBenefService;
	
	public String getInit() {
		
		if(custodios == null || custodios.isEmpty()){
			inicializaCustodios();
		}
		if(selectedEmision == null){
			selectedEmision = (ExcepcionEmisionBenefVO)FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get(Constantes.KEY_EMISION_SELECCIONADA);			
		}
		
		return null;
	}
	
	public String actualizaEmision() {
		ExcepcionEmisionBenef emisionBenef = null;
		String error = null;
		
		if(selectedEmision.getIdCuentaNombrada() == null){
			error = "El custodio es requerido";
		}
		if(StringUtils.isBlank(selectedEmision.getTv()) && StringUtils.isBlank(selectedEmision.getEmisora()) 
				&& StringUtils.isBlank(selectedEmision.getSerie()) && StringUtils.isBlank(selectedEmision.getIsin())){
			error = "Debe proporcionar uno o mas de los siguientes campos [T.V.,Emisora,Serie e Isin]";
		}
		
		if(StringUtils.isBlank(error)){
			emisionBenef = new ExcepcionEmisionBenef();
			emisionBenef.setEmisora(selectedEmision.getEmisora());
			emisionBenef.setIdCuentaNombrada(selectedEmision.getIdCuentaNombrada());
			emisionBenef.setIsin(selectedEmision.getIsin());
			emisionBenef.setSerie(selectedEmision.getSerie());
			emisionBenef.setTv(selectedEmision.getTv());
			emisionBenef.setIdExcepcionEmision(selectedEmision.getIdExcepcionEmision());
			try {
				adminCatalogosBenefService.actualizarEmisionSinBenef(emisionBenef);
				agregarInfoMensaje("La emision se actualizo correctamente");
			}catch (EJBException e) {				
				error = e.getCausedByException().getMessage();
			}
		}
		if(StringUtils.isNotBlank(error)){
			addErrorMessage(error);
		}
		
		return null;
	}
	
	public String regresar(){
		
		return TO_EMISIONES;
	}
	
	private void inicializaCustodios() {
		custodios = new ArrayList<SelectItem>();
		
		List<Object[]> lista = controlBeneficiariosService.obtieneAllCatBic();
		
		for( Object[] bene : lista ) {
			custodios.add(new SelectItem((Long)bene[0], (String)bene[1]));
		}
	}

	public void setAdminCatalogosBenefService(
			AdminCatalogosBenefService adminCatalogosBenefService) {
		this.adminCatalogosBenefService = adminCatalogosBenefService;
	}

	public List<SelectItem> getCustodios() {
		return custodios;
	}

	public void setControlBeneficiariosService(
			ControlBeneficiariosService controlBeneficiariosService) {
		this.controlBeneficiariosService = controlBeneficiariosService;
	}

	public ExcepcionEmisionBenefVO getSelectedEmision() {
		return selectedEmision;
	}

	public void setSelectedEmision(ExcepcionEmisionBenefVO selectedEmision) {
		this.selectedEmision = selectedEmision;
	}
}
