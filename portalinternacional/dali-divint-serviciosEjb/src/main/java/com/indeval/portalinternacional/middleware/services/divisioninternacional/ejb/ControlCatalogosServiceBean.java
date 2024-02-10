package com.indeval.portalinternacional.middleware.services.divisioninternacional.ejb;

import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.persistence.modelo.BancoCorresponsal;
import com.indeval.portaldali.persistence.modelo.Boveda;
import com.indeval.portaldali.persistence.modelo.Divisa;
import com.indeval.portalinternacional.middleware.services.SpringBeanAutowiringInterceptorDivInt;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlCatalogosService;
import com.indeval.portalinternacional.middleware.servicios.modelo.EstatusDerecho;
import com.indeval.portalinternacional.middleware.servicios.modelo.TipoBeneficiario;

@Stateless(name = "ejb.controlCatalogosService", mappedName = "ejb.controlCatalogosService")
@Interceptors(SpringBeanAutowiringInterceptorDivInt.class)
@Remote(ControlCatalogosService.class)
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class ControlCatalogosServiceBean implements ControlCatalogosService{
	
	@Autowired
	private ControlCatalogosService controlCatalogosService;

	public List<EstatusDerecho> getEstatusDerecho() throws BusinessException {		
		return controlCatalogosService.getEstatusDerecho();
	}

	public Divisa[] getDivisas() throws BusinessException {		
		return controlCatalogosService.getDivisas();
	}

	/*
	 * (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlCatalogosService#getDivisasById()
	 */
	public List<Divisa> getDivisasById() throws BusinessException {
		return controlCatalogosService.getDivisasById();
	}
	
	public Boveda[] getBovedas() throws BusinessException {
		return controlCatalogosService.getBovedas();
	}

	/*
	 * (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlCatalogosService#getTipoBeneficiario()
	 */
	public List<TipoBeneficiario> getTipoBeneficiario() throws BusinessException {
		return controlCatalogosService.getTipoBeneficiario();
	}

	public List<BancoCorresponsal> buscaBancoCorresponsal() {
		// TODO Auto-generated method stub
		return controlCatalogosService.buscaBancoCorresponsal();
	}

	public Divisa[] obtenerDivisaPorInstitucion(Long idInstitucion) throws BusinessException {
		
		return controlCatalogosService.obtenerDivisaPorInstitucion(idInstitucion);
	}
	
}
