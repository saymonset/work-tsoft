/**
 * Bursatec - INDEVAL 2010
 */
package com.indeval.portaldali.middleware.services.tesoreria.cuentas.ejb;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;

import com.indeval.portaldali.middleware.dto.CuentaRetiroEfectivoDTO;
import com.indeval.portaldali.middleware.dto.CuentaRetiroInternacionalDTO;
import com.indeval.portaldali.middleware.dto.DivisaDTO;
import com.indeval.portaldali.middleware.dto.EstadosCuentaDTO;
import com.indeval.portaldali.middleware.dto.InstitucionDTO;
import com.indeval.portaldali.middleware.dto.criterio.CriterioCuentaEfectivoDTO;
import com.indeval.portaldali.middleware.dto.util.EstadoPaginacionDTO;
import com.indeval.portaldali.middleware.services.SpringBeanAutowiringInterceptorDali;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.tesoreria.cuentas.AdministracionCuentasRetiroService;

/**
 * Enterprise Java Bean para exponer el servicio de negocio de Administracon de cuentas
 * de retiro
 * 
 * @author Maria C. Buendia
 * @version 1.0
 * 
 */
@Stateless(name = "ejb.cuentasRetiro", mappedName = "ejb.cuentasRetiro")
@Interceptors(SpringBeanAutowiringInterceptorDali.class)
@Remote(AdministracionCuentasRetiroService.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class AdmonCuentasRetiroEfectivoBean implements AdministracionCuentasRetiroService{

	@Autowired
	AdministracionCuentasRetiroService service = null;
	
	public void actualizarCuenta(CuentaRetiroEfectivoDTO cuentaDTO) {
		service.actualizarCuenta(cuentaDTO);
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public CuentaRetiroEfectivoDTO buscarCuenta(BigInteger id,
			boolean esNacional) {
		return service.buscarCuenta(id, esNacional);
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public CuentaRetiroInternacionalDTO buscarCuentaRetiroInternacional(
			BigInteger id) {
		return service.buscarCuentaRetiroInternacional(id);
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<CuentaRetiroEfectivoDTO> buscarCuentas(
			CriterioCuentaEfectivoDTO criterio, EstadoPaginacionDTO paginacion,
			boolean sonNacional, boolean buscarExacto) {
		return service.buscarCuentas(criterio, paginacion, sonNacional, buscarExacto);
	}

	public boolean businessRulesCrearCuentaRetiro(InstitucionDTO agente,
			InstitucionDTO beneficiario, DivisaDTO divisa,
			String cuentaBeneficiario, String nombreBeneficiario,
			String montoMensual, String montoDiario, String montoTransaccion,
			String movsMensuales, long[] esModificacion)
			throws BusinessException {
		return service.businessRulesCrearCuentaRetiro(agente, beneficiario, divisa, cuentaBeneficiario, nombreBeneficiario, montoMensual, montoDiario, montoTransaccion, movsMensuales, esModificacion);
	}

	public boolean businessRulesCrearCuentaRetiroInternacional(
			DivisaDTO divisa, String cuentaBeneficiarioFinal,
			String nombreBeneficiarioFinal, String bancoBeneficiario,
			String nombreBancoBeneficiario, String cuentaBancoBeneficiario,
			String bancoIntermediario, String nombreBancoIntermediario,
			long[] esModificacion) {
		return service.businessRulesCrearCuentaRetiroInternacional(divisa, cuentaBeneficiarioFinal, nombreBeneficiarioFinal, bancoBeneficiario, nombreBancoBeneficiario, cuentaBancoBeneficiario, bancoIntermediario, nombreBancoIntermediario, esModificacion);
	}

	public boolean cambiarEstadoCuenta(long id, String cveCortaEdoNuevo,
			Map<String, Object> datosFirma) {
		return service.cambiarEstadoCuenta(id, cveCortaEdoNuevo, datosFirma);
	}

	public void crearCuenta(CuentaRetiroEfectivoDTO cuentaDTO) {
		service.crearCuenta(cuentaDTO);
	}

	public void eliminarCuenta(BigInteger cuenta) {
		service.eliminarCuenta(cuenta);
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public boolean existeBancoNombreCuentaBeneficiario(
			InstitucionDTO traspasante, InstitucionDTO beneficiario,
			String cuentaBeneficiario, String nombreBeneficiario,
			long[] esModificacion) {
		return service.existeBancoNombreCuentaBeneficiario(traspasante, beneficiario, cuentaBeneficiario, nombreBeneficiario, esModificacion);
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public boolean existenMovimientosPendientesNalXCuenta(long id) {
		return service.existenMovimientosPendientesNalXCuenta(id);
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public EstadosCuentaDTO getEstadoPorId(long id) {
		return service.getEstadoPorId(id);
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<EstadosCuentaDTO> getEstadosCuenta() {
		return service.getEstadosCuenta();
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public boolean nombreParaCuentaBancoBeneficiarioValido(
			InstitucionDTO traspasante, InstitucionDTO beneficiario, String cuentaBeneficiario,
			String nombreBeneficiarioPrueba, String[] nombreBeneficiarioEnBD,
			long[] esModificacion) {
		return service.nombreParaCuentaBancoBeneficiarioValido(traspasante, beneficiario, cuentaBeneficiario, nombreBeneficiarioPrueba, nombreBeneficiarioEnBD, esModificacion);
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public BigInteger obtenerFolioCuenta(boolean esNacional, Long idInstitucion) {
		return service.obtenerFolioCuenta(esNacional, idInstitucion);
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public BigInteger obtenerFolioCuenta(boolean esNacional) {
		return service.obtenerFolioCuenta(esNacional);
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Map<Long, List<Object>> obtenerRetirosPendientes(boolean esNacional,
			List<CuentaRetiroEfectivoDTO> cuentas) {
		return service.obtenerRetirosPendientes(esNacional, cuentas);
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Map<String, Object> revisarModificacionesHabilitadas() {
		return service.revisarModificacionesHabilitadas();
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public CuentaRetiroEfectivoDTO getCuentaNacionalByCuentaRetiroNoEnMOS(
			Long idCuenta) {		 
		return service.getCuentaNacionalByCuentaRetiroNoEnMOS(idCuenta);
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Integer obtenerProyeccionCuentas(CriterioCuentaEfectivoDTO criterio,
			boolean sonNacional, boolean buscarExacto) {
		return service.obtenerProyeccionCuentas(criterio, sonNacional, buscarExacto);
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void createBitacoraEdoCuenta(CuentaRetiroEfectivoDTO cuenta) {
		service.createBitacoraEdoCuenta(cuenta);
		
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public InstitucionDTO getInstitucionForClaveSpei(String claveSpei) {
		return service.getInstitucionForClaveSpei(claveSpei);
	}
	
}
