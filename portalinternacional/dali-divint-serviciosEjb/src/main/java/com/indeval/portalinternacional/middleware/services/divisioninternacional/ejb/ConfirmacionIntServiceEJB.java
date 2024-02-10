/**
 * 
 */
package com.indeval.portalinternacional.middleware.services.divisioninternacional.ejb;

import java.util.List;
import java.util.Set;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;

import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.modelo.Divisa;
import com.indeval.portalinternacional.middleware.services.SpringBeanAutowiringInterceptorDivInt;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.ConfirmacionIntService;
import com.indeval.portalinternacional.middleware.servicios.modelo.BitacoraConfirmacion;
import com.indeval.portalinternacional.middleware.servicios.modelo.ConfirmacionEfectivo;
import com.indeval.portalinternacional.middleware.servicios.vo.ConfirmacionIntDTO;

/**
 * @author César Hernández
 *
 */
@Stateless(name = "ejb.confirmacionIntService", mappedName = "ejb.confirmacionIntService")
@Interceptors(SpringBeanAutowiringInterceptorDivInt.class)
@Remote(ConfirmacionIntService.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ConfirmacionIntServiceEJB {

	@Autowired
	private ConfirmacionIntService confirmacionIntService;

	public List<String> consultaBicCodes(){
		return confirmacionIntService.consultaBicCodes();
	}
	
	public List<Divisa> consultaDivisas(Set<String> bicCodes){
		return confirmacionIntService.consultaDivisas(bicCodes);
	}
	
	public List<String> consultaCuentas(Set<String> bicCodes, Set<String> divisas){
		return confirmacionIntService.consultaCuentas(bicCodes, divisas);
	}
	
	public PaginaVO consultaConfirmaciones(ConfirmacionIntDTO confirmacionIntDTO, final PaginaVO paginaVO){
		return confirmacionIntService.consultaConfirmaciones(confirmacionIntDTO, paginaVO);
	}
	
	public BitacoraConfirmacion consultaBitacoraConfirmacion(final Long idConfirmacion){
		return confirmacionIntService.consultaBitacoraConfirmacion(idConfirmacion);
	}
	
	public void agregaActualizaComentariosDali(final Long idConfirmacion, final String comentariosDali){
		confirmacionIntService.agregaActualizaComentariosDali(idConfirmacion, comentariosDali);
	}
	
	public void agregaActualizaIdFolio(final Long idConfirmacion, final String idFolio){
		confirmacionIntService.agregaActualizaIdFolio(idConfirmacion, idFolio);
	}
	
	public Double obtieneSumaTotalCreditos(final ConfirmacionIntDTO dto){
		return confirmacionIntService.obtieneSumaTotalCreditos(dto);
	}
	
	public Double obtieneSumaTotalDebitos(final ConfirmacionIntDTO dto){
		return confirmacionIntService.obtieneSumaTotalDebitos(dto);
	}
	
	public void ordernarConfirmaciones(List<ConfirmacionEfectivo> listaConfirmacion){
		confirmacionIntService.ordernarConfirmaciones(listaConfirmacion);
	}
	
	/**
	 * @return the confirmacionIntService
	 */
	public ConfirmacionIntService getConfirmacionIntService() {
		return confirmacionIntService;
	}

	/**
	 * @param confirmacionIntService the confirmacionIntService to set
	 */
	public void setConfirmacionIntService(
			ConfirmacionIntService confirmacionIntService) {
		this.confirmacionIntService = confirmacionIntService;
	}	
}
