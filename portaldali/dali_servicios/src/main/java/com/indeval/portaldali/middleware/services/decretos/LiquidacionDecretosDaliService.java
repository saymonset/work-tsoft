package com.indeval.portaldali.middleware.services.decretos;

import java.util.List;

import com.indeval.sidv.decretos.common.exception.BusinessException;
import com.indeval.sidv.decretos.persistence.model.vo.LiquidacionDecretosDetalleAmortizacionesParams;
import com.indeval.sidv.decretos.persistence.model.vo.LiquidacionDecretosDetalleParams;
import com.indeval.sidv.decretos.persistence.model.vo.LiquidacionDecretosDetalleVO;
import com.indeval.sidv.decretos.persistence.model.vo.LiquidacionDecretosParams;

public interface LiquidacionDecretosDaliService {

	public List getLiquidacionDecretos(LiquidacionDecretosParams liquidacionDecretosParams, Boolean debeDejarLog)
			throws BusinessException;
	
	public List getLiquidacionDecretosTotales(LiquidacionDecretosParams liquidacionDecretosParams)
	throws BusinessException;

	public List getInstituciones(LiquidacionDecretosParams liquidacionDecretosParams)
			throws BusinessException;

	public List getTiposDerecho(LiquidacionDecretosParams liquidacionDecretosParams)
			throws BusinessException;

	public LiquidacionDecretosDetalleVO getLiquidacionDecretosDetalle(LiquidacionDecretosDetalleParams liquidacionDecretosDetalleParams) 
			throws BusinessException;
	
	public List getLiquidacionDecretosDetalleAmortizaciones(LiquidacionDecretosDetalleAmortizacionesParams liquidacionDecretosDetalleAmortizacionesParams) 
			throws BusinessException;

	public List getListaTiposEjercicio() throws BusinessException;
	
	public List getListaDivisa() throws BusinessException;
}
