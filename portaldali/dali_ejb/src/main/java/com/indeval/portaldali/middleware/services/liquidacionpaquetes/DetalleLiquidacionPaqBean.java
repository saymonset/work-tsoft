package com.indeval.portaldali.middleware.services.liquidacionpaquetes;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;

import com.indeval.portaldali.middleware.dto.MopInstruccionLiquidacionDTO;
import com.indeval.portaldali.middleware.dto.MopPaqueteDTO;
import com.indeval.portaldali.middleware.services.SpringBeanAutowiringInterceptorDali;

@Stateless(name = "ejb.liquidacionPaquete", mappedName = "ejb.liquidacionPaquete")
@Interceptors(SpringBeanAutowiringInterceptorDali.class)
@Remote(DetalleLiquidacionPaqService.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class DetalleLiquidacionPaqBean implements DetalleLiquidacionPaqService{
	
	@Autowired
	private DetalleLiquidacionPaqService detalleLiquidacionPaqService;

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public MopPaqueteDTO getPaquete(MopPaqueteDTO paqueteDTO, String ticket, Long idInstitucionActual) {
		return detalleLiquidacionPaqService.getPaquete(paqueteDTO, ticket, idInstitucionActual);
	}

	public Boolean cancelPaquete(MopPaqueteDTO paqueteDTO, String ticket) {
		return detalleLiquidacionPaqService.cancelPaquete(paqueteDTO, ticket);
	}

	public String cancelInstruccionLiquidacionPaquete(
			MopInstruccionLiquidacionDTO instruccionLiquidacionDTO,
			MopPaqueteDTO paqueteDTO, String ticket) {
		return detalleLiquidacionPaqService.cancelInstruccionLiquidacionPaquete(instruccionLiquidacionDTO, paqueteDTO, ticket);
	}

	public DetalleLiquidacionPaqService getDetalleLiquidacionPaqService() {
		return detalleLiquidacionPaqService;
	}

	public void setDetalleLiquidacionPaqService(
			DetalleLiquidacionPaqService detalleLiquidacionPaqService) {
		this.detalleLiquidacionPaqService = detalleLiquidacionPaqService;
	}

}
