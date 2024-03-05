/**
 * 
 */
package com.indeval.portaldali.presentation.validation.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

import com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.persistence.util.UtilFecha;
import com.indeval.portaldali.presentation.dto.mercadodinero.CalculoDTO;
import com.indeval.portaldali.presentation.validation.CriterioDeValidacionAbstract;

/**
 * Verifica que la suma de Plazo Representado en días y del Plazo Liquidación en
 * horas sea asignado en un día hábil.
 * 
 * @author Juan Carlos Huizar Moreno.
 * 
 * 
 */
public class CriterioValidacionSumaPlazoRepDiasConPlazoLiquidacionHoras extends
		CriterioDeValidacionAbstract {

	/* Parametros */

	/** El DTO con el detalle de los datos calculados */
	CalculoDTO datosCalculados = (CalculoDTO) parametros.get("datosCalculados");

	/**
	 * Indica si el campo de Plazo Liquidación(Horas) existe en la página
	 * llamada por el usuario
	 */
	Boolean plazoLiquidacionHorasInhabilitado = (Boolean) parametros
			.get("plazoLiquidacionHorasInhabilitado");

	/** Servicio de Mercado de Dinero */
	MercadoDineroService mercadoService = (MercadoDineroService) parametros
			.get("mercadoService");

	/** Variables auxiliares */

	/** Variable acumulador plazoRepDiasAux */
	private String plazoRepDiasAux = "";

	/** Variable acumulador plazoLiquidacionHorasAux */
	private String plazoLiquidacionHorasAux = "";

	/** Variable acumulador plazoRepDiasAux2 */
	private long plazoRepDiasAux2 = new Long(plazoRepDiasAux.trim())
			.longValue();

	/** Variable acumulador numeroDias */
	private double numeroDias = new Double(plazoLiquidacionHorasAux.trim())
			.doubleValue() / 24.0;

	/** Variable f2 */
	private GregorianCalendar f2 = new GregorianCalendar(0, 0, 0);

	/** Variable fechaSumadaConPlazoLiquidacion */
	private Date fechaSumadaConPlazoLiquidacion = mercadoService
			.agregarDiasHabiles(f2.getTime(), (int) numeroDias);

	/** Variable fechaSumadaConPlazoReporto */
	private Date fechaSumadaConPlazoReporto = UtilFecha.addDays(
			fechaSumadaConPlazoLiquidacion, (int) plazoRepDiasAux2);

	/** Verificar qu el resultado asigne un día hábil. */
	public void verificarSumaPlazoRepDiasConPlazoLiquidacionHoras(
			CalculoDTO datosCalculados, String plazoRepDiasLeido,
			String plazoLiquidacionHorasLeido,
			Boolean plazoRepDiasInhabilitadoLeido,
			Boolean plazoLiquidacionHorasInhabilitadoLeido) {

		if (plazoRepDiasInhabilitadoLeido != null
				&& plazoLiquidacionHorasInhabilitadoLeido != null
				&& !plazoRepDiasInhabilitadoLeido.booleanValue()
				&& !plazoLiquidacionHorasInhabilitadoLeido.booleanValue()) {

			plazoRepDiasAux = plazoRepDiasLeido;
			plazoLiquidacionHorasAux = plazoLiquidacionHorasLeido;

			// PUEDE SER QUE AL HACER SUBMIT, LA PANTALLA ESTUVIERA EN UN
			// TIPO DE OPERACION QUE HAYA INHABILITADO EL CAMPO "PLAZO
			// LIQUIDACION(HORAS)" Y CUANDO PASE A UN TIPO DE
			// OPERACION QUE LO HABILITE ESTE TEDRA UN NULO COMO VALOR ENTONCES
			// SE COLOCO A "24 HRS." PARA QUE HAGA EL CALCULO DE FECHA DE
			// REGRESO DE FORMA CORRECTA Y CON ESE VALOR DE "24 HRS."

			if (plazoLiquidacionHorasAux == null
					&& plazoLiquidacionHorasInhabilitado == Boolean.FALSE) {
				this.datosCalculados.setPlazoLiquidacionHoras(24);
				plazoLiquidacionHorasAux = "24";
			}
			if ((plazoRepDiasAux != null) && (plazoLiquidacionHorasAux != null)
					&& !plazoRepDiasAux.trim().equalsIgnoreCase("")
					&& !plazoLiquidacionHorasAux.trim().equalsIgnoreCase("")) {
				try {

					long plazoRepDiasAux2 = new Long(plazoRepDiasAux.trim())
							.longValue();
					if (plazoRepDiasAux2 < 0) {
						this.datosCalculados.setFechaRegreso(null);
						datosCalculados
								.setMensajeFechaRegreso("El Plazo Rep.(d\u00edas) debe ser mayor o igual a 0.");
						setLlaveMensaje("El Plazo Rep.(d\u00edas) debe ser mayor o igual a 0.");
					} else {
						try {
							Calendar c = Calendar.getInstance();
							int dia = c.get(Calendar.DAY_OF_MONTH);
							int mes = c.get(Calendar.MONTH);
							int anio = c.get(Calendar.YEAR);
							f2.set(anio, mes, dia, 0, 0, 0);
							if (!UtilFecha
									.isDiaHabil(fechaSumadaConPlazoReporto)) {
								this.datosCalculados.setFechaRegreso(null);
								datosCalculados
										.setMensajeFechaRegreso("La suma de Plazo Rep.(D\u00edas) y de Plazo Liquidaci\u00f3n (Horas) no da un d\u00eda h\u00e1bil.");
								setLlaveMensaje("La suma de Plazo Rep.(D\u00edas) y de Plazo Liquidaci\u00f3n (Horas) no da un d\u00eda h\u00e1bil.");
							} else {
								this.datosCalculados
										.setFechaRegreso(new Date());
								datosCalculados.setMensajeFechaRegreso("");
							}
						} catch (BusinessException be) {
							this.datosCalculados.setFechaRegreso(null);
							datosCalculados
									.setMensajeFechaRegreso("Existe un problema con el servicio de MD.");
							StringBuffer mensaje = new StringBuffer();
							mensaje.append(be.toString());

							be.printStackTrace();
							be
									.setErrorCode("Existe un problema con el servicio de MD.");
						}
					}
				} catch (NumberFormatException nfe) {
					datosCalculados.setFechaRegreso(null);
					datosCalculados
							.setMensajeFechaRegreso("El Plazo Rep.(d\u00edas) debe ser num\u00e9rico.");
					setLlaveMensaje("El Plazo Rep.(d\u00edas) debe ser num\u00e9rico.");
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.presentation.validation.CriterioDeValidacion#validar(java.util.Map)
	 */
	public boolean validar(Map<String, Object> parametros) {
		if ((plazoRepDiasAux != null) && (plazoLiquidacionHorasAux != null)) {
			if (plazoRepDiasAux2 < 0) {
				setLlaveMensaje("El Plazo Rep.(d\u00edas) debe ser mayor o igual a 0.");
			} else if (!UtilFecha.isDiaHabil(fechaSumadaConPlazoReporto)) {
				setLlaveMensaje("La suma de Plazo Rep.(D\u00edas) y de Plazo Liquidaci\u00f3n (Horas) no da un d\u00eda h\u00e1bil.");
			} else {
				setLlaveMensaje(" ");
			}
		} else {
			setLlaveMensaje("El Plazo Rep.(d\u00edas) debe ser num\u00e9rico.");
		}
		return false;
	}

}
