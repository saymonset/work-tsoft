package com.indeval.portalinternacional.middleware.services.divisioninternacional.ejb;

import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;

import com.indeval.portalinternacional.middleware.services.SpringBeanAutowiringInterceptorDivInt;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.AdminCatalogosBenefService;
import com.indeval.portalinternacional.middleware.servicios.modelo.ExcepcionEmisionBenef;
import com.indeval.portalinternacional.middleware.servicios.modelo.HorarioBeneficiario;

@Stateless(name = "ejb.adminCatalogosBenefService", mappedName = "ejb.adminCatalogosBenefService")
@Interceptors(SpringBeanAutowiringInterceptorDivInt.class)
@Remote(AdminCatalogosBenefService.class)
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class AdminCatalogosBenefServiceBean implements AdminCatalogosBenefService{

	@Autowired
	private AdminCatalogosBenefService adminCatalogosBenefService; 
	
	public List<ExcepcionEmisionBenef> getEmisionesSinBenef() {		
		return adminCatalogosBenefService.getEmisionesSinBenef();
	}

	public List<HorarioBeneficiario> getHorariosCustodios() {
		return adminCatalogosBenefService.getHorariosCustodios();
	}

	public void eliminarEmisionSinBenef(ExcepcionEmisionBenef emisionBenef) {
		adminCatalogosBenefService.eliminarEmisionSinBenef(emisionBenef);		
	}

	public void eliminarHorario(HorarioBeneficiario horarioBeneficiario) {
		adminCatalogosBenefService.eliminarHorario(horarioBeneficiario);		
	}

	public void agregarEmisionSinBenef(ExcepcionEmisionBenef emisionBenef) {
		adminCatalogosBenefService.agregarEmisionSinBenef(emisionBenef);		
	}

	public void agregarHorario(HorarioBeneficiario horarioBeneficiario) {
		adminCatalogosBenefService.agregarHorario(horarioBeneficiario);		
	}

	public void actualizarEmisionSinBenef(ExcepcionEmisionBenef emisionBenef) {
		adminCatalogosBenefService.actualizarEmisionSinBenef(emisionBenef);		
	}

	public void actualizarHorario(HorarioBeneficiario horarioBeneficiario) {
		adminCatalogosBenefService.actualizarHorario(horarioBeneficiario);
	}

	public boolean tieneEmisionPorcentajeCero(Long idEmisionDerecho) {		
		return adminCatalogosBenefService.tieneEmisionPorcentajeCero(idEmisionDerecho);
	}

    public boolean tieneEmisionPorcentajeCeroOptimizado(Long idEmisionDerecho) {      
        return adminCatalogosBenefService.tieneEmisionPorcentajeCeroOptimizado(idEmisionDerecho);
    }

}
