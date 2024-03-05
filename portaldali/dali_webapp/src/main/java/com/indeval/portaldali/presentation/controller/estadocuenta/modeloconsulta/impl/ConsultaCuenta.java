/**
 * 2H Software SA de CV
 * 
 * Sistema de Consulta de Estado de Cuenta - Indeval
 * 
 */
package com.indeval.portaldali.presentation.controller.estadocuenta.modeloconsulta.impl;


import java.util.ArrayList;
import java.util.List;

import com.indeval.portaldali.middleware.dto.CuentaDTO;
import com.indeval.portaldali.middleware.dto.InstitucionDTO;
import com.indeval.portaldali.middleware.dto.util.EstadoPaginacionDTO;
import com.indeval.portaldali.middleware.services.common.ConsultaCuentaService;
import com.indeval.portaldali.presentation.controller.estadocuenta.modeloconsulta.ConsultaAbstract;

/**
 * Representa la implementación de la funcionalidad para obtener la consulta de
 * cuentas. Esta consulta utiliza el criterio de
 * <code>ConsultaTipoTenenciaServiceImpl</code> para encontrar las cuentas
 * nombradas o controladas asociadas al criterio de tipo de cuenta.
 * 
 * @author Emigdio Hernández
 * @version 1.0
 */
public class ConsultaCuenta extends ConsultaAbstract<CuentaDTO> {
	/**
	 * Criterio de tipo de tenencia utilizado para consultar cuentas
	 */
	private ConsultaTipoTenencia criterioTipoTenencia = new ConsultaTipoTenencia();
	/**
	 * Id de la institución utilizada para filtrar las cuentas
	 */
	private long idInstitucion = -1;
	/**
	 * Nombre de la institución asociada a la cuenta
	 */
	private String nombreInstitucion = null;
	/**
	 * servicio de negocio para la consulta de las cuentas
	 */
	private ConsultaCuentaService consultaCuentaService = null;
	/**
	 * Conjunto de cuentas a filtrar para la consulta
	 */
	private List<Long> cuentasValidas = new ArrayList<Long>();
	
	/**
	 * Obtiene el campo criterioTipoTenencia
	 * 
	 * @return criterioTipoTenencia
	 */
	public ConsultaTipoTenencia getCriterioTipoTenencia() {
		return criterioTipoTenencia;
	}

	/**
	 * Asigna el valor del campo criterioTipoTenencia
	 * 
	 * @param criterioTipoTenencia
	 *            el valor de criterioTipoTenencia a asignar
	 */
	public void setCriterioTipoTenencia(
			ConsultaTipoTenencia criterioTipoTenencia) {
		this.criterioTipoTenencia = criterioTipoTenencia;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.secu.presentation.model.ConsultaAbstract#getPaginaDeResultados()
	 */
	@Override
	public List<CuentaDTO> getPaginaDeResultados() {

		EstadoPaginacionDTO estadoPaginacionDTO = this.getEstadoPaginacion();
		CuentaDTO criterio = getOpcionSeleccionada();
		
		if (criterio.getTipoTenencia().getTipoCuenta().getId().equals("C")) {
			return consultaCuentaService.buscarCuentasControladas(criterio,
					estadoPaginacionDTO,cuentasValidas);
		} else {
			return consultaCuentaService.buscarCuentasNombradas(criterio,
					estadoPaginacionDTO,cuentasValidas);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.secu.presentation.model.ConsultaAbstract#getResultados()
	 */
	@Override
	public List<CuentaDTO> getResultados() {

		
		CuentaDTO criterio = getOpcionSeleccionada();
		if (criterio.getTipoTenencia().getTipoCuenta().getId().equals("C")) {
			return consultaCuentaService.buscarCuentasControladas(criterio,
					null);
		} else {
			return consultaCuentaService.buscarCuentasNombradas(criterio, null);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.presentation.model.Consulta#setOpcionSeleccionada(java.lang.Object)
	 */
	public void setOpcionSeleccionada(CuentaDTO opcion) {
		opcionSeleccionada = null;
		if (opcion != null) {
			opcion
					.setTipoTenencia(criterioTipoTenencia
							.getOpcionSeleccionada());
			opcionSeleccionada = consultaCuentaService
					.buscarCuentaPorNumeroCuenta(opcion);
		}

		if (opcionSeleccionada == null) {
			opcionSeleccionada = getOpcionSeleccionada();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.presentation.model.Consulta#getOpcionSeleccionada()
	 */
	public CuentaDTO getOpcionSeleccionada() {
		if (opcionSeleccionada == null) {
			opcionSeleccionada = new CuentaDTO();
			opcionSeleccionada.setTipoTenencia(criterioTipoTenencia
					.getOpcionSeleccionada());
			opcionSeleccionada.setIdCuenta(-1);
			InstitucionDTO institucion = new InstitucionDTO();
			institucion.setId(idInstitucion);
			opcionSeleccionada.setInstitucion(institucion);
			opcionSeleccionada.setNumeroCuenta("-1");
			opcionSeleccionada.setDescripcion("TODAS");
		}
		
		
		return opcionSeleccionada;
	}

	/**
	 * Obtiene el campo consultaCuentaService
	 * 
	 * @return consultaCuentaService
	 */
	public ConsultaCuentaService getConsultaCuentaService() {
		return consultaCuentaService;
	}

	/**
	 * Asigna el valor del campo consultaCuentaService
	 * 
	 * @param consultaCuentaService
	 *            el valor de consultaCuentaService a asignar
	 */
	public void setConsultaCuentaService(
			ConsultaCuentaService consultaCuentaService) {
		this.consultaCuentaService = consultaCuentaService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.presentation.model.ConsultaAbstract#obtenerProyeccionConsulta()
	 */
	@Override
	public long obtenerProyeccionConsulta() {
		long proyeccion = 0;

		if (getOpcionSeleccionada().getTipoTenencia().getTipoCuenta().getId()
				.equals("C")) {
			proyeccion = consultaCuentaService
					.obtenerProyeccionDeBusquedaDeCuentasControladas(getOpcionSeleccionada(),cuentasValidas);
		} else {
			proyeccion = consultaCuentaService
					.obtenerProyeccionDeBusquedaDeCuentasNombradas(getOpcionSeleccionada(),cuentasValidas);
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
	 * Obtiene el campo nombreInstitucion
	 * @return  nombreInstitucion
	 */
	public String getNombreInstitucion() {
		return nombreInstitucion;
	}

	/**
	 * Asigna el valor del campo nombreInstitucion
	 * @param nombreInstitucion el valor de nombreInstitucion a asignar
	 */
	public void setNombreInstitucion(String nombreInstitucion) {
		this.nombreInstitucion = nombreInstitucion;
	}
	
	

}
