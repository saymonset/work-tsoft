/**
 * 2H Software - Bursatec
 * 
 * Sistema de Consulta de Estados de Cuenta
 *
 * Dec 10, 2007
 *
 */
package com.indeval.portaldali.presentation.controller.estadocuenta.modeloconsulta.impl;


import java.util.ArrayList;
import java.util.List;

import com.indeval.portaldali.middleware.dto.CuentaEfectivoDTO;
import com.indeval.portaldali.middleware.dto.InstitucionDTO;
import com.indeval.portaldali.middleware.services.common.ConsultaCuentaService;
import com.indeval.portaldali.presentation.controller.estadocuenta.modeloconsulta.ConsultaAbstract;

/**
 * Implementación del mecanismo de consulta para las cuentas de efectivo.
 * 
 * @author Pablo Julián Balderas Méndez
 */
public class ConsultaCuentaEfectivo extends ConsultaAbstract<CuentaEfectivoDTO> {

	/**
	 * El tipo de custodia que ser considerado para la consulta de cuentas de
	 * efectivo
	 */
	private String tipoCustodia = null;

	/**
	 * Criterio de tipo de cuenta para filtrar los tipos de tenencia
	 */
	private ConsultaTipoDeCuenta criterioTipoCuenta = null;
	
	private ConsultaTipoNaturaleza criterioTipoNaturaleza = null;
	/**
	 * Id de la institución utilizada para filtrar las cuentas
	 */
	private long idInstitucion = -1;
	/**
	 * Servicio para la consulta de cuentas
	 */
	private ConsultaCuentaService consultaCuentaService = null;
	/**
	 * Conjunto de cuentas a filtrar para la consulta
	 */
	private List<Long> cuentasValidas = new ArrayList<Long>();
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.presentation.model.ConsultaAbstract#getPaginaDeResultados()
	 */
	@Override
	public List<CuentaEfectivoDTO> getPaginaDeResultados() {
		
		List<CuentaEfectivoDTO> resultado = null;

		CuentaEfectivoDTO criterio = getOpcionSeleccionada();
		
		
		
		if (criterio.getTipoCuenta().getId().equals("C")) {
			resultado = consultaCuentaService.buscarCuentasControladasEfectivo(
					criterio, getEstadoPaginacion(),cuentasValidas);
		} else {
			resultado = consultaCuentaService.buscarCuentasNombradasEfectivo(
					criterio, getEstadoPaginacion(),cuentasValidas);
		}

		return resultado;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.presentation.model.ConsultaAbstract#getResultados()
	 */
	@Override
	public List<CuentaEfectivoDTO> getResultados() {
		List<CuentaEfectivoDTO> resultado = null;

		CuentaEfectivoDTO criterio = getOpcionSeleccionada();
		
		
		if (criterio.getTipoCuenta().getId().equals("C")) {
			resultado = consultaCuentaService.buscarCuentasControladasEfectivo(
					criterio, null);
		} else {
			resultado = consultaCuentaService.buscarCuentasNombradasEfectivo(
					criterio, null);
		}

		return resultado;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.presentation.model.Consulta#getOpcionSeleccionada()
	 */
	public CuentaEfectivoDTO getOpcionSeleccionada() {
		if (opcionSeleccionada == null) {
			opcionSeleccionada = new CuentaEfectivoDTO();
			opcionSeleccionada.setTipoCustodia(getTipoCustodia());
			opcionSeleccionada.setTipoCuenta(criterioTipoCuenta
					.getOpcionSeleccionada());
			opcionSeleccionada.setTipoNaturaleza(getCriterioTipoNaturaleza().getOpcionSeleccionada());
			InstitucionDTO institucion = new InstitucionDTO();
			institucion.setId(idInstitucion);
			opcionSeleccionada.setInstitucion(institucion);
			opcionSeleccionada.setDescripcion("TODAS");
			opcionSeleccionada.setNumeroCuenta("-1");
		}
		return opcionSeleccionada;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.presentation.model.Consulta#setOpcionSeleccionada(java.lang.Object)
	 */
	public void setOpcionSeleccionada(CuentaEfectivoDTO opcion) {
		opcionSeleccionada = null;
		if (opcion != null) {
			opcion.setTipoCuenta(criterioTipoCuenta.getOpcionSeleccionada());
			opcionSeleccionada = consultaCuentaService
					.buscarCuentaEfectivoPorNumeroCuenta(opcion);
		}
		if (opcionSeleccionada == null) {
			opcionSeleccionada = getOpcionSeleccionada();
		}
	}

	/**
	 * Obtiene el atributo criterioTipoCuenta
	 * 
	 * @return El atrubuto criterioTipoCuenta
	 */
	public ConsultaTipoDeCuenta getCriterioTipoCuenta() {
		return criterioTipoCuenta;
	}

	/**
	 * Establece la propiedad criterioTipoCuenta
	 * 
	 * @param criterioTipoCuenta
	 *            el campo criterioTipoCuenta a establecer
	 */
	public void setCriterioTipoCuenta(ConsultaTipoDeCuenta criterioTipoCuenta) {
		this.criterioTipoCuenta = criterioTipoCuenta;
	}

	/**
	 * Obtiene el atributo tipoCustodia
	 * 
	 * @return El atrubuto tipoCustodia
	 */
	public String getTipoCustodia() {
		return tipoCustodia;
	}

	/**
	 * Establece la propiedad tipoCustodia
	 * 
	 * @param tipoCustodia
	 *            el campo tipoCustodia a establecer
	 */
	public void setTipoCustodia(String tipoCustodia) {
		this.tipoCustodia = tipoCustodia;
	}

	/**
	 * Obtiene el atributo consultaCuentaService
	 * 
	 * @return El atrubuto consultaCuentaService
	 */
	public ConsultaCuentaService getConsultaCuentaService() {
		return consultaCuentaService;
	}

	/**
	 * Establece la propiedad consultaCuentaService
	 * 
	 * @param consultaCuentaService
	 *            el campo consultaCuentaService a establecer
	 */
	public void setConsultaCuentaService(
			ConsultaCuentaService consultaCuentaService) {
		this.consultaCuentaService = consultaCuentaService;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.estadocuenta.presentation.model.ConsultaAbstract#obtenerProyeccionConsulta()
	 */
	@Override
	public long obtenerProyeccionConsulta() {
		long proyeccion = 0;
		
		CuentaEfectivoDTO criterio = getOpcionSeleccionada();
		
		
		
		if (criterio.getTipoCuenta().getId().equals("C")) {
			proyeccion = consultaCuentaService.obtenerProyeccionDeBusquedaDeCuentasControladasEfectivo(criterio,cuentasValidas);
		} else {
			proyeccion = consultaCuentaService.obtenerProyeccionDeBusquedaDeCuentasNombradasEfectivo(criterio,cuentasValidas);
		}
		
		return proyeccion;
	}

	/**
	 * Obtiene el campo idInstitucion
	 * @return  idInstitucion
	 */
	public long getIdInstitucion() {
		return idInstitucion;
	}

	/**
	 * Asigna el valor del campo idInstitucion
	 * @param idInstitucion el valor de idInstitucion a asignar
	 */
	public void setIdInstitucion(long idInstitucion) {
		this.idInstitucion = idInstitucion;
	}

	

	/*
	 * (non-Javadoc)
	 * @see com.indeval.estadocuenta.presentation.model.Consulta#recibirNotificacionResultados(java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void recibirNotificacionResultados(List resultados){
		cuentasValidas.clear();
		cuentasValidas.addAll(resultados);
	}

	/**
	 * Obtiene el campo criterioTipoNaturaleza
	 * @return  criterioTipoNaturaleza
	 */
	public ConsultaTipoNaturaleza getCriterioTipoNaturaleza() {
		return criterioTipoNaturaleza;
	}

	/**
	 * Asigna el valor del campo criterioTipoNaturaleza
	 * @param criterioTipoNaturaleza el valor de criterioTipoNaturaleza a asignar
	 */
	public void setCriterioTipoNaturaleza(
			ConsultaTipoNaturaleza criterioTipoNaturaleza) {
		this.criterioTipoNaturaleza = criterioTipoNaturaleza;
	}
	
	
}
