/**
 * Bursatec - INDEVAL 2010
 */
package com.indeval.portaldali.middleware.services.tesoreria.retiros.ejb;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;

import com.indeval.portaldali.middleware.dto.CuentaRetiroEfectivoDTO;
import com.indeval.portaldali.middleware.dto.CuentaRetiroInternacionalDTO;
import com.indeval.portaldali.middleware.dto.DivisaDTO;
import com.indeval.portaldali.middleware.dto.RetiroEfectivoDTO;
import com.indeval.portaldali.middleware.dto.RetiroEfectivoInternacionalDTO;
import com.indeval.portaldali.middleware.dto.criterio.CriterioCuentaEfectivoDTO;
import com.indeval.portaldali.middleware.services.SpringBeanAutowiringInterceptorDali;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.tesoreria.AdmonRetirosEfectivoService;

/**
 * Enterprise Java Bean para exponer el servicio de negocio de Administracon de cuentas
 * de retiro
 * 
 * @author Maria C. Buendia
 * @version 1.0
 * 
 */
@Stateless(name = "ejb.retiroEfectivo", mappedName = "ejb.retiroEfectivo")
@Interceptors(SpringBeanAutowiringInterceptorDali.class)
@Remote(AdmonRetirosEfectivoService.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class AdmonRetirosEfectivoBean implements AdmonRetirosEfectivoService{
	
	@Autowired
	AdmonRetirosEfectivoService service = null;

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public CuentaRetiroEfectivoDTO buscarCuentaPorCriterio(
			CriterioCuentaEfectivoDTO criterio, boolean esNacional) {
		return service.buscarCuentaPorCriterio(criterio, esNacional);
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<CuentaRetiroEfectivoDTO> buscarCuentasPorPrefijo(
			CriterioCuentaEfectivoDTO criterio, boolean esNacional) {
		return service.buscarCuentasPorPrefijo(criterio, esNacional);
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Object buscarRetiroConCuentaPorReferenciaOperacion(
			String referenciaOperacion, boolean esNacional) {
		return service.buscarRetiroConCuentaPorReferenciaOperacion(referenciaOperacion, esNacional);
	}

	public boolean businessRulesCrearRetiro(DivisaDTO divisa, BigDecimal importe)
			throws BusinessException {
		return service.businessRulesCrearRetiro(divisa, importe);
	}

	public boolean businessRulesCrearRetiroBancaComercial(RetiroEfectivoDTO cre) {
		return service.businessRulesCrearRetiroBancaComercial(cre);
	}

	public void cambiarEstadoRetiro(List<Object> retiros, BigInteger edoNuevo) {
		service.cambiarEstadoRetiro(retiros, edoNuevo);
	}

	public void cambiarEstadoRetiro(Object retiro, BigInteger edoNuevo) {
		service.cambiarEstadoRetiro(retiro, edoNuevo);
	}

	public void crearRetiro(RetiroEfectivoDTO retiro) {
		service.crearRetiro(retiro);
	}

	public void crearRetiro(RetiroEfectivoInternacionalDTO retiro) {
		service.crearRetiro(retiro);
	}

	public String generaIso(
			RetiroEfectivoInternacionalDTO retiroEfectivoInternacionalDTO) {
		return service.generaIso(retiroEfectivoInternacionalDTO);
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<CuentaRetiroInternacionalDTO> getCuentasInterXDivisa(
			BigInteger idDivisa) {
		return service.getCuentasInterXDivisa(idDivisa);
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public CuentaRetiroEfectivoDTO buscarCuentaBeneficiario(String cuentaBeneficiarioInsitucion, Long idInstitucion) {
		return service.buscarCuentaBeneficiario(cuentaBeneficiarioInsitucion, idInstitucion);
	}

}
