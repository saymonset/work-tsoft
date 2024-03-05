/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * 
 * Apr 15, 2008
 */
package com.indeval.portaldali.presentation.controller.mercadodinero.capturaoperaciones.test;

import java.math.BigDecimal;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.indeval.portaldali.middleware.dto.util.ResultadoValidacionDTO;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.tesoreria.Saldo3CuentasVO;
import com.indeval.portaldali.middleware.services.tesoreria.TesoreriaService;
import com.indeval.portaldali.presentation.common.BaseWebTestCase;
import com.indeval.portaldali.presentation.common.constants.ValidadoresConstantes;
import com.indeval.portaldali.presentation.dto.mercadodinero.RetiroFondosDTO;
import com.indeval.portaldali.presentation.helper.CalculoCapturaOperacionViewHelper;
import com.indeval.portaldali.presentation.helper.ServicesCapturaOperacionViewHelper;
import com.indeval.portaldali.presentation.validation.DTOValidator;

/**
 * Controller de prueba para verificar el funcionamiento de los validadores para
 * la pantalla de retiro de fondos.
 * 
 * @author Juan Carlos Huizar Moreno
 * 
 */
public class RetiroFondoControllerTest extends BaseWebTestCase {
	/** El DTO con los campos de la pantalla Retiro de fondos */
	private RetiroFondosDTO retiro = new RetiroFondosDTO();
	/** Servicio para ejecutar el retiro de fondos */
	private TesoreriaService tesoreriaService = null;
	BigDecimal saldoDisponibleObtenido;
	/**
	 * Contiene los cálculos de Simulado, Fecha Regreso, Importe y Premio
	 * necesarios para las pantallas de Captura de Operaciones
	 */
	private CalculoCapturaOperacionViewHelper calculos = new CalculoCapturaOperacionViewHelper();
	/** Servicio helper para la captura de operaciones */
	private ServicesCapturaOperacionViewHelper servicesCapturaOperacionViewHelper = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.presentation.common.BaseWebTestCase#testDummy()
	 */
	public void testDummy() {

		tesoreriaService = (TesoreriaService) getBean("tesoreriaService");
		servicesCapturaOperacionViewHelper =  (ServicesCapturaOperacionViewHelper) getBean("servicesCapturaOperacionViewHelper");
		
		retiro.setIdFolioUsuario("01003");
		retiro.getSaldoUsuario().getCuenta().getInstitucion().setNombreCorto("CBACCVAL");
		retiro.setTipoRetiro(0);
		retiro.setImporteRetiro(new BigDecimal("10500.55"));

		DTOValidator validadorReporto = (DTOValidator) getBean(ValidadoresConstantes.VALIDADOR_RETIRO_EFECTIVO);
		ResultadoValidacionDTO resultado = validadorReporto.validarDTO(retiro);

		System.out.println("Error: " + resultado.getMensaje());
		assertTrue(resultado.isValido());
		System.out.println("Error: " + resultado.getMensaje());

		retiro.getSaldoUsuario().setSaldoDisponible(obtnerSaldoDisponible().doubleValue());
		System.out.println("Saldo Dsiponible= " + retiro.getSaldoUsuario().getSaldoDisponible());

		calculaSaldoActual();
		System.out.println("Saldo Actual= " + retiro.getSaldoActual());

	}

	/** Obtiene el saldo Disponible para la Cuenta. */
	public BigDecimal obtnerSaldoDisponible() {
		if (retiro.getSaldoUsuario().getSaldoDisponible() == null) {
			try {
				Saldo3CuentasVO saldo3Ctas = tesoreriaService.getSaldo3Cuentas(new AgenteVO("01", "003", ""));
				saldoDisponibleObtenido = saldo3Ctas.getSaldoCtaConcentradora();
			} catch (Exception e) {
				FacesMessage message = new FacesMessage(e.getMessage());
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage("", message);
			}
		}
		return saldoDisponibleObtenido;
	}

	/** Calcular saldo Actual. */
	@SuppressWarnings("unused")
	private void calculaSaldoActual() {
		calculos.setCantidadOperadaLeida(retiro.getImporteRetiro() != null ? retiro.getImporteRetiro().toString() : "");
		calculos.setCantidadOperada(new BigDecimal(retiro.getImporteRetiro() != null ? retiro.getImporteRetiro().doubleValue() : 0));
		calculos.setSaldoDisponibleLeido(retiro.getSaldoUsuario().getSaldoDisponible() != null ? retiro.getSaldoUsuario().getSaldoDisponible().toString() : "");
		servicesCapturaOperacionViewHelper.realizarCalculos(calculos);

		/*
		 * Realiza el cálculo del saldo Actual se necesitan:
		 * cantidadOperadaLeida(Importe a Retirar), saldoDisponibleLeido
		 */
		retiro.setSaldoActual(calculos.getSimulado() != null ? calculos.getSimulado().doubleValue() : 0);

	}

	/**
	 * @return the retiro
	 */
	public RetiroFondosDTO getRetiro() {
		return retiro;
	}

	/**
	 * @param retiro
	 *            the retiro to set
	 */
	public void setRetiro(RetiroFondosDTO retiro) {
		this.retiro = retiro;
	}

	/**
	 * @return the tesoreriaService
	 */
	public TesoreriaService getTesoreriaService() {
		return tesoreriaService;
	}

	/**
	 * @param tesoreriaService
	 *            the tesoreriaService to set
	 */
	public void setTesoreriaService(TesoreriaService tesoreriaService) {
		this.tesoreriaService = tesoreriaService;
	}

	/**
	 * @return the calculos
	 */
	public CalculoCapturaOperacionViewHelper getCalculos() {
		return calculos;
	}

	/**
	 * @param calculos
	 *            the calculos to set
	 */
	public void setCalculos(CalculoCapturaOperacionViewHelper calculos) {
		this.calculos = calculos;
	}

	/**
	 * @return the servicesCapturaOperacionViewHelper
	 */
	public ServicesCapturaOperacionViewHelper getServicesCapturaOperacionViewHelper() {
		return servicesCapturaOperacionViewHelper;
	}

	/**
	 * @param servicesCapturaOperacionViewHelper
	 *            the servicesCapturaOperacionViewHelper to set
	 */
	public void setServicesCapturaOperacionViewHelper(ServicesCapturaOperacionViewHelper servicesCapturaOperacionViewHelper) {
		this.servicesCapturaOperacionViewHelper = servicesCapturaOperacionViewHelper;
	}

	/**
	 * @return the saldoDisponibleObtenido
	 */
	public BigDecimal getSaldoDisponibleObtenido() {
		return saldoDisponibleObtenido;
	}

	/**
	 * @param saldoDisponibleObtenido
	 *            the saldoDisponibleObtenido to set
	 */
	public void setSaldoDisponibleObtenido(BigDecimal saldoDisponibleObtenido) {
		this.saldoDisponibleObtenido = saldoDisponibleObtenido;
	}

}
