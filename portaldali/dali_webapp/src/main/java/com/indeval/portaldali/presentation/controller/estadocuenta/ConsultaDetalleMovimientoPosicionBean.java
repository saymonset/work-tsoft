/**
 * 2H Software SA de CV
 * 
 * Sistema de Consulta de Estado de Cuenta - Indeval
 *
 * Fecha de creación: Dec 24, 2007
 */
package com.indeval.portaldali.presentation.controller.estadocuenta;

import java.util.Collection;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.apache.commons.lang.StringUtils;

import com.indeval.portaldali.middleware.dto.DetalleMovimientoValorDTO;
import com.indeval.portaldali.middleware.dto.InstitucionDTO;
import com.indeval.portaldali.middleware.services.estadocuenta.ConsultaDetalleMovimientoEfectivoService;
import com.indeval.portaldali.middleware.services.estadocuenta.ConsultaDetalleMovimientoPosicionService;
import com.indeval.portaldali.presentation.controller.estadocuenta.modeloconsulta.BackingBeanBase;

/**
 * Backing bean asociado a la pantalla de consulta de detalle de movimientos de
 * valores. Esta clase se encarga de la invocación a los servicios de negocio
 * relacionados con la consulta.
 * 
 * @author Sandra Flores Arrieta
 * @version 1.0
 * 
 */
public class ConsultaDetalleMovimientoPosicionBean extends BackingBeanBase{

	/** DTO que contiene la información del detalle del movimiento */
	private DetalleMovimientoValorDTO detalleMovimiento = null;

	/** Servicio de negocio para las consultas de detalles */
	private ConsultaDetalleMovimientoPosicionService consultaDetalleMovimientoPosicionService;

	/** Servicio de negocio para efectuar la consulta de de detalle */
	private ConsultaDetalleMovimientoEfectivoService consultaDetalleMovimientoEfectivoService = null;

	/**
	 * Constructor de la clase ConsultaDetalleMovimientoPosicionBean
	 */
	public ConsultaDetalleMovimientoPosicionBean() {

	}

	/**
	 * Asigna las opciones predeterminadas para cuando se carga la página por
	 * primera vez.
	 * 
	 * @return nulo, este método no requiere retornar un valor
	 */
	public String getInit() {

		FacesContext fc = FacesContext.getCurrentInstance();
		String idRegistro = (String) fc.getExternalContext().getRequestParameterMap().get("idRegistro");
		String efectivo = (String) fc.getExternalContext().getRequestParameterMap().get("efectivo");
		Boolean isHistorico = new Boolean((String) fc.getExternalContext().getRequestParameterMap().get("isHistorico"));
		boolean esEfectivo = false;
		if (StringUtils.isNotEmpty(efectivo)) {
			esEfectivo = Boolean.parseBoolean(efectivo);
		}

		InstitucionDTO dto = getInstitucionActual();
		
		if (!esEfectivo) {
			detalleMovimiento = consultaDetalleMovimientoPosicionService.consultarDetalleMovimientoValor(new Long(idRegistro).longValue(), dto.getId(), isHistorico);
		} else {
			long idRegistroContable = consultaDetalleMovimientoEfectivoService.buscarIdRegistroContableValorNombradoDeOperacionDVP(new Long(idRegistro).longValue(), dto.getId(), isHistorico);
			detalleMovimiento = consultaDetalleMovimientoPosicionService.consultarDetalleMovimientoValor(idRegistroContable, dto.getId(), isHistorico);
		}
		return null;
	}

	/**
	 * Obtiene el atributo detalleMovimiento
	 * 
	 * @return El atrubuto detalleMovimiento
	 */
	public DetalleMovimientoValorDTO getDetalleMovimiento() {
		return detalleMovimiento;
	}

	/**
	 * Obtiene el valor del atributo consultaDetalleMovimientoEfectivoService
	 * 
	 * @return el valor del atributo consultaDetalleMovimientoEfectivoService
	 */
	public ConsultaDetalleMovimientoEfectivoService getConsultaDetalleMovimientoEfectivoService() {
		return consultaDetalleMovimientoEfectivoService;
	}

	/**
	 * Establece el valor del atributo consultaDetalleMovimientoEfectivoService
	 * 
	 * @param consultaDetalleMovimientoEfectivoService
	 *            el valor del atributo consultaDetalleMovimientoEfectivoService
	 *            a establecer
	 */
	public void setConsultaDetalleMovimientoEfectivoService(ConsultaDetalleMovimientoEfectivoService consultaDetalleMovimientoEfectivoService) {
		this.consultaDetalleMovimientoEfectivoService = consultaDetalleMovimientoEfectivoService;
	}

	/**
	 * Establece la propiedad detalleMovimiento
	 * 
	 * @param detalleMovimiento
	 *            el campo detalleMovimiento a establecer
	 */
	public void setDetalleMovimiento(DetalleMovimientoValorDTO detalleMovimiento) {
		this.detalleMovimiento = detalleMovimiento;
	}

	/**
	 * Obtiene el valor del atributo consultaDetalleMovimientoPosicionService
	 * 
	 * @return el valor del atributo consultaDetalleMovimientoPosicionService
	 */
	public ConsultaDetalleMovimientoPosicionService getConsultaDetalleMovimientoPosicionService() {
		return consultaDetalleMovimientoPosicionService;
	}

	/**
	 * Establece el valor del atributo consultaDetalleMovimientoPosicionService
	 * 
	 * @param consultaDetalleMovimientoPosicionService
	 *            el valor del atributo consultaDetalleMovimientoPosicionService
	 *            a establecer.
	 */
	public void setConsultaDetalleMovimientoPosicionService(ConsultaDetalleMovimientoPosicionService consultaDetalleMovimientoPosicionService) {
		this.consultaDetalleMovimientoPosicionService = consultaDetalleMovimientoPosicionService;
	}

	@Override
	protected Collection<? extends Object> ejecutarConsultaReporte() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getNombreReporte() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Map<String, Object> llenarParametros() {
		// TODO Auto-generated method stub
		return null;
	}

}
