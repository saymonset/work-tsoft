/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * DetalleParcialidadesBean.java
 * 10/03/2008
 */
package com.indeval.portaldali.presentation.controller.bitacoramatch;

import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.context.FacesContext;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.indeval.portaldali.middleware.dto.OperacionValorMatchDTO;
import com.indeval.portaldali.middleware.services.estatus.ConsultaEstatusOperacionesMatchService;
import com.indeval.portaldali.presentation.controller.common.ControllerBase;

/**
 * Backing bean para dar servicio a la pantalla de consulta
 * de detalle de parcialidades
 * 
 * @author Emigio Hernández
 * 
 */
public class DetalleParcialidadesBean extends ControllerBase {
	/**
	 * Detalle de la operacion a mostrar
	 */ 
	private OperacionValorMatchDTO operacion  = null;
	/**
	 * Datelle de parcialidades
	 */
	List<OperacionValorMatchDTO> parcialidades = null;
	/**
	 * Servicio de consulta de las operaciones
	 */
	private ConsultaEstatusOperacionesMatchService consultaEstatusOperacionesMatchService = null;
	/**
	 * método de inicialización de propiedades
	 * @return null
	 */
	@SuppressWarnings("unchecked")
	public String getInit(){
		FacesContext ctx = FacesContext.getCurrentInstance();
		
		String folio = ctx.getExternalContext().getRequestParameterMap().get("folioInstruccionLiquidacion");
		String traspasante = ctx.getExternalContext().getRequestParameterMap().get("traspasante");
		String receptor = ctx.getExternalContext().getRequestParameterMap().get("receptor");
		
		double importe = 0;
		long cantidadTitulos = 0;
		
		parcialidades = consultaEstatusOperacionesMatchService.consultarInstruccionOperacionValorPorId(NumberUtils.toLong(folio));
		
		// Para calcular el total de titulos e importe
		Set<BigInteger> idsInstruccionesPadre = new HashSet<BigInteger>();
		// Obtiene los ID de la(s) instruccion(es) padre
		for(OperacionValorMatchDTO op : parcialidades) {
			if(op.getIdInstruccionLiquidacionOrigen() != null) {
				idsInstruccionesPadre.add(op.getIdInstruccionLiquidacionOrigen());
			}
		}
		// Se recorre la lista de parcialidades
		for(OperacionValorMatchDTO parcialidad : parcialidades) {
			if(!parcialidad.isInstruccionLiquidacionBancoTrabajo()) {
				// Si no es banco de trabajo, se suman las cantidades sin importar si la instruccion es padre o hijo
				cantidadTitulos += parcialidad.getCantidadTitulos();
				importe += parcialidad.getImporte();
			} else if (parcialidad.isInstruccionLiquidacionBancoTrabajo() && !idsInstruccionesPadre.contains(parcialidad.getIdInstruccionLiquidacion())) {
				// Si es banco de trabajo, se suman las cantidades solo si la instruccion es hijo
				cantidadTitulos += parcialidad.getCantidadTitulos();
				importe += parcialidad.getImporte();
			}
		}
		
		if(parcialidades != null && parcialidades.size() > 0){
			try {
				operacion = (OperacionValorMatchDTO) BeanUtils.cloneBean(parcialidades.get(parcialidades.size() -1));
			} catch (IllegalAccessException e) {
				logger.error(e.getMessage(), e);
			} catch (InstantiationException e) {
				logger.error(e.getMessage(), e);
			} catch (InvocationTargetException e) {
				logger.error(e.getMessage(), e);
			} catch (NoSuchMethodException e) {
				logger.error(e.getMessage(), e);
			}
			operacion.getInstitucionTraspasante().setClaveTipoInstitucion(traspasante.substring(0,2));
			operacion.getInstitucionTraspasante().setFolioInstitucion(traspasante.substring(2,5));
			operacion.getInstitucionReceptora().setClaveTipoInstitucion(receptor.substring(0, 2));
			operacion.getInstitucionReceptora().setFolioInstitucion(receptor.substring(2, 5));
			operacion.setCantidadTitulos(cantidadTitulos);
			operacion.setImporte(importe);
		}
		
		return null;
	}


	/*
	 * (non-Javadoc)
	 * @see com.indeval.portaldali.presentation.controller.common.ControllerBase#ejecutarConsultaReporte()
	 */
	@Override
	protected Collection<? extends Object> ejecutarConsultaReporte() {
		// TODO Auto-generated method stub
		return null;
	}
/*
 * (non-Javadoc)
 * @see com.indeval.portaldali.presentation.controller.common.ControllerBase#getNombreReporte()
 */
	@Override
	protected String getNombreReporte() {
		// TODO Auto-generated method stub
		return null;
	}
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portaldali.presentation.controller.common.ControllerBase#llenarParametros()
	 */
	@Override
	protected Map<String, Object> llenarParametros() {
		// TODO Auto-generated method stub
		return null;
	}


	/**
	 * Obtiene el campo consultaEstatusOperacionesMatchService
	 * @return  consultaEstatusOperacionesMatchService
	 */
	public ConsultaEstatusOperacionesMatchService getConsultaEstatusOperacionesMatchService() {
		return consultaEstatusOperacionesMatchService;
	}


	/**
	 * Asigna el campo consultaEstatusOperacionesMatchService
	 * @param consultaEstatusOperacionesMatchService el valor de consultaEstatusOperacionesMatchService a asignar
	 */
	public void setConsultaEstatusOperacionesMatchService(
			ConsultaEstatusOperacionesMatchService consultaEstatusOperacionesMatchService) {
		this.consultaEstatusOperacionesMatchService = consultaEstatusOperacionesMatchService;
	}


	/**
	 * Obtiene el campo operacion
	 * @return  operacion
	 */
	public OperacionValorMatchDTO getOperacion() {
		return operacion;
	}


	/**
	 * Asigna el campo operacion
	 * @param operacion el valor de operacion a asignar
	 */
	public void setOperacion(OperacionValorMatchDTO operacion) {
		this.operacion = operacion;
	}


	/**
	 * Obtiene el campo parcialidades
	 * @return  parcialidades
	 */
	public List<OperacionValorMatchDTO> getParcialidades() {
		return parcialidades;
	}


	/**
	 * Asigna el campo parcialidades
	 * @param parcialidades el valor de parcialidades a asignar
	 */
	public void setParcialidades(List<OperacionValorMatchDTO> parcialidades) {
		this.parcialidades = parcialidades;
	}
}