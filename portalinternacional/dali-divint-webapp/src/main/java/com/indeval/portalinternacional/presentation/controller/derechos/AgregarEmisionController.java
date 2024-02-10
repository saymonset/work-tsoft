package com.indeval.portalinternacional.presentation.controller.derechos;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJBException;
import javax.faces.model.SelectItem;

import org.apache.commons.lang.StringUtils;

import com.indeval.portalinternacional.middleware.services.divisioninternacional.AdminCatalogosBenefService;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlBeneficiariosService;
import com.indeval.portalinternacional.middleware.servicios.modelo.ExcepcionEmisionBenef;
import com.indeval.portalinternacional.presentation.controller.common.ControllerBase;

public class AgregarEmisionController  extends ControllerBase{
	
	private static final String TO_EMISIONES = "adminEmisionDerecho";
	
	private ExcepcionEmisionBenefVO emisionBenefVO = new ExcepcionEmisionBenefVO();
	private List<SelectItem> custodios;
	private ControlBeneficiariosService controlBeneficiariosService;
	private AdminCatalogosBenefService adminCatalogosBenefService;
	
	public String getInit() {		
		if(custodios == null || custodios.isEmpty()){
			inicializaCustodios();
		}
		
		return null;
	}
	
	public String saveEmision() {
		ExcepcionEmisionBenef emisionBenef = null;
		String error = null;
		
		if(emisionBenefVO.getIdCuentaNombrada() == null){
			error = "El custodio es requerido";
		}
		if(StringUtils.isBlank(emisionBenefVO.getTv()) && StringUtils.isBlank(emisionBenefVO.getEmisora()) 
				&& StringUtils.isBlank(emisionBenefVO.getSerie()) && StringUtils.isBlank(emisionBenefVO.getIsin())){
			error = "Debe proporcionar uno o mas de los siguientes campos [T.V.,Emisora,Serie e Isin]";
		}
		
		if(StringUtils.isBlank(error)){
			emisionBenef = new ExcepcionEmisionBenef();
			emisionBenef.setEmisora(emisionBenefVO.getEmisora());
			emisionBenef.setIdCuentaNombrada(emisionBenefVO.getIdCuentaNombrada());
			emisionBenef.setIsin(emisionBenefVO.getIsin());
			emisionBenef.setSerie(emisionBenefVO.getSerie());
			emisionBenef.setTv(emisionBenefVO.getTv());
			try {
				adminCatalogosBenefService.agregarEmisionSinBenef(emisionBenef);
				addMessage("La emision se inserto correctamente");
			} catch (EJBException e) {
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

	public ExcepcionEmisionBenefVO getEmisionBenefVO() {
		return emisionBenefVO;
	}

	public void setEmisionBenefVO(ExcepcionEmisionBenefVO emisionBenefVO) {
		this.emisionBenefVO = emisionBenefVO;
	}
}
