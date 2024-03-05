package com.indeval.portaldali.middleware.services.decretos;

import java.util.List;

import com.indeval.sidv.decretos.common.exception.BusinessException;
import com.indeval.sidv.decretos.persistence.model.vo.LiquidacionDecretosDetalleAmortizacionesParams;
import com.indeval.sidv.decretos.persistence.model.vo.LiquidacionDecretosDetalleParams;
import com.indeval.sidv.decretos.persistence.model.vo.LiquidacionDecretosDetalleVO;
import com.indeval.sidv.decretos.persistence.model.vo.LiquidacionDecretosParams;
import com.indeval.sidv.decretos.persistence.model.vo.LiquidacionDecretosService;

public class LiquidacionDecretosDaliServiceImpl implements
		LiquidacionDecretosDaliService {
	
	private LiquidacionDecretosService liquidacionDecretosService = null;

	@SuppressWarnings("unchecked")
	public List getInstituciones(LiquidacionDecretosParams liquidacionDecretosParams)
			throws BusinessException {
		return liquidacionDecretosService.getInstituciones(liquidacionDecretosParams);
	}

	@SuppressWarnings("unchecked")
	public List getLiquidacionDecretos(LiquidacionDecretosParams liquidacionDecretosParams, Boolean debeDejarLog)
			throws BusinessException {
		return liquidacionDecretosService.getLiquidacionDecretos(liquidacionDecretosParams);
	}
	
	@SuppressWarnings("unchecked")
	public List getLiquidacionDecretosTotales(LiquidacionDecretosParams liquidacionDecretosParams)
			throws BusinessException {
		return liquidacionDecretosService.getLiquidacionDecretosTotales(liquidacionDecretosParams);
	}

	public LiquidacionDecretosDetalleVO getLiquidacionDecretosDetalle(LiquidacionDecretosDetalleParams liquidacionDecretosDetalleParams)
			throws BusinessException {
		return liquidacionDecretosService.getLiquidacionDecretosDetalle(liquidacionDecretosDetalleParams);
	}

	@SuppressWarnings("unchecked")
	public List getLiquidacionDecretosDetalleAmortizaciones(LiquidacionDecretosDetalleAmortizacionesParams liquidacionDecretosDetalleAmortizacionesParams)
			throws BusinessException {
		return liquidacionDecretosService.getLiquidacionDecretosDetalleAmortizaciones(liquidacionDecretosDetalleAmortizacionesParams);
	}

	@SuppressWarnings("unchecked")
	public List getListaDivisa() throws BusinessException {
		return liquidacionDecretosService.getListaDivisa();
	}

	@SuppressWarnings("unchecked")
	public List getListaTiposEjercicio() throws BusinessException {
		return liquidacionDecretosService.getListaTiposEjercicio();
	}

	@SuppressWarnings("unchecked")
	public List getTiposDerecho(LiquidacionDecretosParams liquidacionDecretosParams)
			throws BusinessException {
		return liquidacionDecretosService.getTiposDerecho(liquidacionDecretosParams);
	}

	/**
	 * @param liquidacionDecretosService the liquidacionDecretosService to set
	 */
	public void setLiquidacionDecretosService(
			LiquidacionDecretosService liquidacionDecretosService) {
		this.liquidacionDecretosService = liquidacionDecretosService;
	}

}
