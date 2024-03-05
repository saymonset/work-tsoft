/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * ConsultaCuentaServiceImpl.java
 * 29/02/2008
 */
package com.indeval.portaldali.middleware.services.common;

import java.util.List;

import com.indeval.portaldali.middleware.dto.CuentaDTO;
import com.indeval.portaldali.middleware.dto.CuentaEfectivoDTO;
import com.indeval.portaldali.middleware.dto.util.EstadoPaginacionDTO;
import com.indeval.portaldali.persistence.dao.common.CuentaDaliDAO;

/**
 * Implementación de la interface de servicio de negocio encargado de realizar
 * las operaciones de consulta a las tablas de cuentas controladas y cuentas
 * nombradas de valores y efectivo.
 * 
 * @author Emigdio Hernández
 * @version 1.0 
 */
public class ConsultaCuentaServiceImpl implements ConsultaCuentaService {

	
	
	/**
	 * DAO utilizado para consultar las cuentas controladas y nombradas de
	 * valores y efectivo.
	 */
	private CuentaDaliDAO cuentaDaliDAO;

	/**
	 * Obtiene el campo cuentaDaliDAO
	 * 
	 * @return cuentaDaliDAO
	 */
	public CuentaDaliDAO getCuentaDAO() {
		return cuentaDaliDAO;
	}

	/**
	 * Asigna el valor del campo cuentaDaliDAO
	 * 
	 * @param cuentaDaliDAO
	 *            el valor de cuentaDaliDAO a asignar
	 */
	public void setCuentaDAO(CuentaDaliDAO cuentaDaliDAO) {
		this.cuentaDaliDAO = cuentaDaliDAO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.services.ConsultaCuentaService#buscarCuentasControladas(com.indeval.estadocuenta.core.application.dto.CuentaDTO,
	 *      com.indeval.estadocuenta.core.application.dto.EstadoPaginacionDTO)
	 */
	public List<CuentaDTO> buscarCuentasControladas(CuentaDTO criterios,
			EstadoPaginacionDTO estadoPaginacion) {
		return cuentaDaliDAO.buscarCuentasControladas(criterios, estadoPaginacion);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.services.ConsultaCuentaService#buscarCuentasControladas(com.indeval.estadocuenta.core.application.dto.CuentaDTO,
	 *      com.indeval.estadocuenta.core.application.dto.EstadoPaginacionDTO)
	 */
	public List<CuentaDTO> buscarCuentasNombradas(CuentaDTO criterios,
			EstadoPaginacionDTO estadoPaginacion) {
		return cuentaDaliDAO.buscarCuentasNombradas(criterios, estadoPaginacion);
	}

	
	
	/* (non-Javadoc)
	 * @see com.indeval.estadocuenta.core.application.services.ConsultaCuentaService#buscarCuentasPorFragmentoNumeroCuenta(com.indeval.estadocuenta.core.application.dto.CuentaDTO)
	 */
	public List<CuentaDTO> buscarCuentasPorFragmentoNumeroCuenta(CuentaDTO criterio) {
		
		return cuentaDaliDAO.buscarCuentasPorFragmentoNumeroCuenta(criterio);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.services.ConsultaCuentaService#buscarCuentasControladas(com.indeval.estadocuenta.core.application.dto.CuentaDTO,
	 *      com.indeval.estadocuenta.core.application.dto.EstadoPaginacionDTO)
	 */
	public List<CuentaEfectivoDTO> buscarCuentasControladasEfectivo(
			CuentaEfectivoDTO criterio, EstadoPaginacionDTO estadoPaginacion) {
		return cuentaDaliDAO.buscarCuentasControladasEfectivo(criterio,
				estadoPaginacion);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.services.ConsultaCuentaService#buscarCuentasControladas(com.indeval.estadocuenta.core.application.dto.CuentaDTO,
	 *      com.indeval.estadocuenta.core.application.dto.EstadoPaginacionDTO)
	 */
	public List<CuentaEfectivoDTO> buscarCuentasNombradasEfectivo(
			CuentaEfectivoDTO criterio, EstadoPaginacionDTO estadoPaginacion) {
		return cuentaDaliDAO.buscarCuentasNombradasEfectivo(criterio,
				estadoPaginacion);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.services.ConsultaCuentaService#buscarCuentasControladas(com.indeval.estadocuenta.core.application.dto.CuentaDTO,
	 *      com.indeval.estadocuenta.core.application.dto.EstadoPaginacionDTO)
	 */
	public CuentaEfectivoDTO buscarCuentaEfectivoPorNumeroCuenta(
			CuentaEfectivoDTO criterio) {
		return cuentaDaliDAO.buscarCuentaEfectivoPorNumeroCuenta(criterio);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.services.ConsultaCuentaService#buscarCuentasControladas(com.indeval.estadocuenta.core.application.dto.CuentaDTO,
	 *      com.indeval.estadocuenta.core.application.dto.EstadoPaginacionDTO)
	 */
	public int obtenerProyeccionDeBusquedaDeCuentasControladas(
			CuentaDTO criterio) {
		return cuentaDaliDAO
				.obtenerProyeccionDeBusquedaDeCuentasControladas(criterio);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.services.ConsultaCuentaService#buscarCuentasControladas(com.indeval.estadocuenta.core.application.dto.CuentaDTO,
	 *      com.indeval.estadocuenta.core.application.dto.EstadoPaginacionDTO)
	 */
	public int obtenerProyeccionDeBusquedaDeCuentasNombradas(CuentaDTO criterio) {
		return cuentaDaliDAO
				.obtenerProyeccionDeBusquedaDeCuentasNombradas(criterio);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.services.ConsultaCuentaService#buscarCuentasControladas(com.indeval.estadocuenta.core.application.dto.CuentaDTO,
	 *      com.indeval.estadocuenta.core.application.dto.EstadoPaginacionDTO)
	 */
	public int obtenerProyeccionDeBusquedaDeCuentasControladasEfectivo(
			CuentaEfectivoDTO criterio) {
		
		return cuentaDaliDAO.obtenerProyeccionDeBusquedaDeCuentasControladasEfectivo(criterio);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.services.ConsultaCuentaService#buscarCuentasControladas(com.indeval.estadocuenta.core.application.dto.CuentaDTO,
	 *      com.indeval.estadocuenta.core.application.dto.EstadoPaginacionDTO)
	 */
	public int obtenerProyeccionDeBusquedaDeCuentasNombradasEfectivo(
			CuentaEfectivoDTO criterio) {
		
		return  cuentaDaliDAO.obtenerProyeccionDeBusquedaDeCuentasNombradasEfectivo(criterio);
	}

	/*
	 * (non-Javadoc)
	 * @see com.indeval.estadocuenta.core.application.services.ConsultaCuentaService#buscarCuentasControladas(com.indeval.estadocuenta.core.application.dto.CuentaDTO, com.indeval.estadocuenta.core.application.dto.EstadoPaginacionDTO, java.util.Set)
	 */
	public List<CuentaDTO> buscarCuentasControladas(CuentaDTO criterio,
			EstadoPaginacionDTO estadoPaginacion, List<Long> idsValidos) {
		return cuentaDaliDAO.buscarCuentasControladas(criterio, estadoPaginacion, idsValidos);
	}

	/*
	 * (non-Javadoc)
	 * @see com.indeval.estadocuenta.core.application.services.ConsultaCuentaService#buscarCuentasNombradas(com.indeval.estadocuenta.core.application.dto.CuentaDTO, com.indeval.estadocuenta.core.application.dto.EstadoPaginacionDTO, java.util.Set)
	 */
	public List<CuentaDTO> buscarCuentasNombradas(CuentaDTO criterio,
			EstadoPaginacionDTO estadoPaginacion, List<Long> idsValidos) {
		return cuentaDaliDAO.buscarCuentasNombradas(criterio, estadoPaginacion, idsValidos);
	}

	/*
	 * (non-Javadoc)
	 * @see com.indeval.estadocuenta.core.application.services.ConsultaCuentaService#obtenerProyeccionDeBusquedaDeCuentasControladas(com.indeval.estadocuenta.core.application.dto.CuentaDTO, java.util.Set)
	 */
	public int obtenerProyeccionDeBusquedaDeCuentasControladas(
			CuentaDTO criterio, List<Long> idsCuentaValidos) {
		return cuentaDaliDAO.obtenerProyeccionDeBusquedaDeCuentasControladas(criterio, idsCuentaValidos);
	}

	/*
	 * (non-Javadoc)
	 * @see com.indeval.estadocuenta.core.application.services.ConsultaCuentaService#obtenerProyeccionDeBusquedaDeCuentasNombradas(com.indeval.estadocuenta.core.application.dto.CuentaDTO, java.util.Set)
	 */
	public int obtenerProyeccionDeBusquedaDeCuentasNombradas(
			CuentaDTO criterio, List<Long> idsCuentaValidos) {
		return cuentaDaliDAO.obtenerProyeccionDeBusquedaDeCuentasNombradas(criterio, idsCuentaValidos);
	}

	/*
	 * (non-Javadoc)
	 * @see com.indeval.estadocuenta.core.application.services.ConsultaCuentaService#obtenerProyeccionDeBusquedaDeCuentasControladasEfectivo(com.indeval.estadocuenta.core.application.dto.CuentaEfectivoDTO, java.util.Set)
	 */
	public int obtenerProyeccionDeBusquedaDeCuentasControladasEfectivo(
			CuentaEfectivoDTO criterio, List<Long> idsValidos) {
		
		return cuentaDaliDAO.obtenerProyeccionDeBusquedaDeCuentasControladasEfectivo(criterio, idsValidos);
	}

	/*
	 * (non-Javadoc)
	 * @see com.indeval.estadocuenta.core.application.services.ConsultaCuentaService#obtenerProyeccionDeBusquedaDeCuentasNombradasEfectivo(com.indeval.estadocuenta.core.application.dto.CuentaEfectivoDTO, java.util.Set)
	 */
	public int obtenerProyeccionDeBusquedaDeCuentasNombradasEfectivo(
			CuentaEfectivoDTO criterio, List<Long> idsValidos) {
		return cuentaDaliDAO.obtenerProyeccionDeBusquedaDeCuentasNombradasEfectivo(criterio, idsValidos);
	}

	/*
	 * (non-Javadoc)
	 * @see com.indeval.estadocuenta.core.application.services.ConsultaCuentaService#buscarCuentasControladasEfectivo(com.indeval.estadocuenta.core.application.dto.CuentaEfectivoDTO, com.indeval.estadocuenta.core.application.dto.EstadoPaginacionDTO, java.util.Set)
	 */
	public List<CuentaEfectivoDTO> buscarCuentasControladasEfectivo(
			CuentaEfectivoDTO criterio, EstadoPaginacionDTO estadoPaginacion,
			List<Long> idsValidos) {
		
		return cuentaDaliDAO.buscarCuentasControladasEfectivo(criterio, estadoPaginacion, idsValidos);
	}

	/*
	 * (non-Javadoc)
	 * @see com.indeval.estadocuenta.core.application.services.ConsultaCuentaService#buscarCuentasNombradasEfectivo(com.indeval.estadocuenta.core.application.dto.CuentaEfectivoDTO, com.indeval.estadocuenta.core.application.dto.EstadoPaginacionDTO, java.util.Set)
	 */
	public List<CuentaEfectivoDTO> buscarCuentasNombradasEfectivo(
			CuentaEfectivoDTO criterio, EstadoPaginacionDTO estadoPaginacion,
			List<Long> idsValidos) {
		return cuentaDaliDAO.buscarCuentasNombradasEfectivo(criterio, estadoPaginacion, idsValidos);
	}

	/*
	 * (non-Javadoc)
	 * @see com.indeval.estadocuenta.core.application.services.ConsultaCuentaService#buscarCuentasPorFragmentoNumeroCuenta(com.indeval.estadocuenta.core.application.dto.CuentaEfectivoDTO)
	 */
	public List<CuentaEfectivoDTO> buscarCuentasPorFragmentoNumeroCuenta(CuentaEfectivoDTO criterio) {
		return cuentaDaliDAO.buscarCuentasPorFragmentoNumeroCuenta(criterio);
	}

	
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.services.ConsultaCuentaService#buscarCuentasControladas(com.indeval.estadocuenta.core.application.dto.CuentaDTO,
	 *      com.indeval.estadocuenta.core.application.dto.EstadoPaginacionDTO)
	 */
	public CuentaDTO buscarCuentaPorNumeroCuenta(CuentaDTO criterio) {
		return cuentaDaliDAO.buscarCuentaPorNumeroCuenta(criterio);
	}
	
	
	
	

}
