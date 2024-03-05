/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * EnvioOperacionesBean.java
 * Apr 15, 2008
 */
package com.indeval.portaldali.middleware.services.tesoreria.ejb;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import com.indeval.portaldali.middleware.services.SpringBeanAutowiringInterceptorDali;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.modelo.PaginaVO;
import com.indeval.portaldali.middleware.services.tesoreria.CuentaLiquidacionVO;
import com.indeval.portaldali.middleware.services.tesoreria.DetalleAmortizacionesVO;
import com.indeval.portaldali.middleware.services.tesoreria.DetalleCuentaLiqVO;
import com.indeval.portaldali.middleware.services.tesoreria.EstadoCuentaSNEVO;
import com.indeval.portaldali.middleware.services.tesoreria.GetDetalleAmortizacionesParams;
import com.indeval.portaldali.middleware.services.tesoreria.GetDetalleCuentaLiqParams;
import com.indeval.portaldali.middleware.services.tesoreria.GetEdoCtaLiqParams;
import com.indeval.portaldali.middleware.services.tesoreria.GetEdoCtaSNEParams;
import com.indeval.portaldali.middleware.services.tesoreria.Saldo3CuentasVO;
import com.indeval.portaldali.middleware.services.tesoreria.TesoreriaService;
import com.indeval.portaldali.middleware.services.tesoreria.liquidaciondecretos.LiquidacionDecretosDetalleAmortizacionesParams;
import com.indeval.portaldali.middleware.services.tesoreria.liquidaciondecretos.LiquidacionDecretosDetalleParams;
import com.indeval.sidv.decretos.persistence.model.vo.LiquidacionDecretosDetalleVO;
import com.indeval.portaldali.middleware.services.tesoreria.liquidaciondecretos.LiquidacionDecretosParams;

/**
 * Enterprise Java Bean para exponer el servicio de negocio de Tesoreria
 * 
 * @author Juan Carlos Huizar Moreno
 * @version 1.0
 * 
 */
@Stateless(name = "ejb.tesoreria", mappedName = "ejb.tesoreria")
@Interceptors(SpringBeanAutowiringInterceptorDali.class)
@Remote(TesoreriaService.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class TesoreriaBean implements TesoreriaService {

	@Autowired
	private TesoreriaService tesoreriaService = null;

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.middleware.services.tesoreria.TesoreriaService#businessRulesRetirarFondos(com.indeval.portaldali.middleware.services.modelo.AgenteVO,
	 *      java.lang.String, java.math.BigDecimal)
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public BigInteger businessRulesRetirarFondos(AgenteVO agente, String idTipoRetiro, BigInteger idBoveda, BigDecimal importe) throws BusinessException {
		return tesoreriaService.businessRulesRetirarFondos(agente, idTipoRetiro, idBoveda, importe);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.middleware.services.tesoreria.TesoreriaService#businessRulesTraspasarEntreCuentas(com.indeval.portaldali.middleware.services.modelo.AgenteVO,
	 *      com.indeval.portaldali.middleware.services.modelo.AgenteVO,
	 *      java.math.BigDecimal)
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public BigInteger businessRulesTraspasarEntreCuentas(AgenteVO traspasante, AgenteVO receptor, BigInteger idBoveda, BigDecimal importe)
			throws BusinessException {
		return tesoreriaService.businessRulesTraspasarEntreCuentas(traspasante, receptor, idBoveda, importe);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.middleware.services.tesoreria.TesoreriaService#getDetalleAmort(com.indeval.portaldali.middleware.services.tesoreria.GetDetalleAmortizacionesParams)
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public DetalleAmortizacionesVO[] getDetalleAmort(GetDetalleAmortizacionesParams params) throws BusinessException {
		return tesoreriaService.getDetalleAmort(params);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.middleware.services.tesoreria.TesoreriaService#getDetalleAmortizaciones(com.indeval.portaldali.middleware.services.tesoreria.GetDetalleAmortizacionesParams)
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public DetalleAmortizacionesVO[] getDetalleAmortizaciones(GetDetalleAmortizacionesParams params) throws BusinessException {
		return tesoreriaService.getDetalleAmortizaciones(params);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.middleware.services.tesoreria.TesoreriaService#getDetalleCuentaLiqVO(com.indeval.portaldali.middleware.services.tesoreria.GetDetalleCuentaLiqParams)
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public DetalleCuentaLiqVO getDetalleCuentaLiqVO(GetDetalleCuentaLiqParams params) throws BusinessException {
		return tesoreriaService.getDetalleCuentaLiqVO(params);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.middleware.services.tesoreria.TesoreriaService#getDetalleCuentaLiquidacionVO(com.indeval.portaldali.middleware.services.tesoreria.GetDetalleCuentaLiqParams)
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public DetalleCuentaLiqVO getDetalleCuentaLiquidacionVO(GetDetalleCuentaLiqParams params) throws BusinessException {
		return tesoreriaService.getDetalleCuentaLiquidacionVO(params);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.middleware.services.tesoreria.TesoreriaService#getDivisaDefault()
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public String getDivisaDefault() throws BusinessException {
		return tesoreriaService.getDivisaDefault();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.middleware.services.tesoreria.TesoreriaService#getEstadoCuentaLiquidacion(com.indeval.portaldali.middleware.services.tesoreria.GetEdoCtaLiqParams)
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public CuentaLiquidacionVO[] getEstadoCuentaLiquidacion(GetEdoCtaLiqParams params) throws BusinessException {
		return tesoreriaService.getEstadoCuentaLiquidacion(params);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.middleware.services.tesoreria.TesoreriaService#getEstadoCuentaSNE(com.indeval.portaldali.middleware.services.tesoreria.GetEdoCtaSNEParams)
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public EstadoCuentaSNEVO getEstadoCuentaSNE(GetEdoCtaSNEParams params) throws BusinessException {
		return tesoreriaService.getEstadoCuentaSNE(params);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.middleware.services.tesoreria.TesoreriaService#getSaldo3Cuentas(com.indeval.portaldali.middleware.services.modelo.AgenteVO)
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Saldo3CuentasVO getSaldo3Cuentas(AgenteVO agente) throws BusinessException {
		return tesoreriaService.getSaldo3Cuentas(agente);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.middleware.services.tesoreria.TesoreriaService#getTraspFondos(com.indeval.portaldali.middleware.services.modelo.AgenteVO,
	 *      java.lang.String,
	 *      com.indeval.portaldali.middleware.services.modelo.PaginaVO)
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public PaginaVO getTraspFondos(AgenteVO agente, String idTipoOperacion, PaginaVO pagina) throws BusinessException {
		return tesoreriaService.getTraspFondos(agente, idTipoOperacion, pagina);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.middleware.services.tesoreria.TesoreriaService#permitirRetirosFondos(com.indeval.portaldali.middleware.services.modelo.AgenteVO)
	 */
	public void permitirRetirosFondos(AgenteVO agente) throws BusinessException {
		// TODO Auto-generated method stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.middleware.services.tesoreria.TesoreriaService#retirarFondos(com.indeval.portaldali.middleware.services.modelo.AgenteVO,
	 *      java.lang.String, java.math.BigDecimal)
	 */
	public Integer retirarFondos(AgenteVO agente, String tipoRetiro, BigDecimal importe) throws BusinessException {
		return tesoreriaService.retirarFondos(agente, tipoRetiro, importe);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.middleware.services.tesoreria.TesoreriaService#traspasarEntreCuentas(com.indeval.portaldali.middleware.services.modelo.AgenteVO,
	 *      com.indeval.portaldali.middleware.services.modelo.AgenteVO,
	 *      java.math.BigDecimal)
	 */
	public Integer traspasarEntreCuentas(AgenteVO traspasante, AgenteVO receptor, BigDecimal importe) throws BusinessException {
		return tesoreriaService.traspasarEntreCuentas(traspasante, receptor, importe);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.middleware.services.tesoreria.liquidaciondecretos.LiquidacionDecretosService#getInstituciones(com.indeval.portaldali.middleware.services.tesoreria.liquidaciondecretos.LiquidacionDecretosParams)
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	@SuppressWarnings("unchecked")
	public List getInstituciones(LiquidacionDecretosParams params) throws BusinessException {
		return tesoreriaService.getInstituciones(params);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.middleware.services.tesoreria.liquidaciondecretos.LiquidacionDecretosService#getLiquidacionDecretos(com.indeval.portaldali.middleware.services.tesoreria.liquidaciondecretos.LiquidacionDecretosParams)
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	@SuppressWarnings("unchecked")
	public List getLiquidacionDecretos(LiquidacionDecretosParams params) throws BusinessException {
		return tesoreriaService.getLiquidacionDecretos(params);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.middleware.services.tesoreria.liquidaciondecretos.LiquidacionDecretosService#getLiquidacionDecretosDetalleAmortizaciones(com.indeval.sidv.decretos.persistence.model.vo.LiquidacionDecretosDetalleAmortizacionesParams)
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	@SuppressWarnings("unchecked")
	public List getLiquidacionDecretosDetalleAmortizaciones(LiquidacionDecretosDetalleAmortizacionesParams paramsDetalleAmortizaciones)
			throws BusinessException {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.middleware.services.tesoreria.liquidaciondecretos.LiquidacionDecretosService#getListaDivisa()
	 */
	@SuppressWarnings("unchecked")
	public List getListaDivisa() throws BusinessException {
		return tesoreriaService.getListaDivisa();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.middleware.services.tesoreria.liquidaciondecretos.LiquidacionDecretosService#getListaTiposEjercicio()
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	@SuppressWarnings("unchecked")
	public List getListaTiposEjercicio() throws BusinessException {
		return tesoreriaService.getListaTiposEjercicio();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.middleware.services.tesoreria.liquidaciondecretos.LiquidacionDecretosService#getTiposDerecho(com.indeval.sidv.decretos.persistence.model.vo.LiquidacionDecretosParams)
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	@SuppressWarnings("unchecked")
	public List getTiposDerecho(LiquidacionDecretosParams params) throws BusinessException {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.middleware.services.tesoreria.liquidaciondecretos.LiquidacionDecretosService#getLiquidacionDecretosDetalleAmortizaciones(com.indeval.sidv.decretos.persistence.model.vo.LiquidacionDecretosDetalleAmortizacionesParams)
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	@SuppressWarnings("unchecked")
	public List getLiquidacionDecretosDetalleAmortizaciones(
			com.indeval.sidv.decretos.persistence.model.vo.LiquidacionDecretosDetalleAmortizacionesParams paramsDetalleAmortizaciones) throws BusinessException {
		return tesoreriaService.getLiquidacionDecretosDetalleAmortizaciones(paramsDetalleAmortizaciones);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.middleware.services.tesoreria.liquidaciondecretos.LiquidacionDecretosService#getTiposDerecho(com.indeval.sidv.decretos.persistence.model.vo.LiquidacionDecretosParams)
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	@SuppressWarnings("unchecked")
	public List getTiposDerecho(com.indeval.sidv.decretos.persistence.model.vo.LiquidacionDecretosParams params) throws BusinessException {
		return tesoreriaService.getTiposDerecho(params);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.middleware.services.tesoreria.liquidaciondecretos.LiquidacionDecretosService#getLiquidacionDecretosDetalle(com.indeval.portaldali.middleware.services.tesoreria.liquidaciondecretos.LiquidacionDecretosDetalleParams)
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public LiquidacionDecretosDetalleVO getLiquidacionDecretosDetalle(LiquidacionDecretosDetalleParams paramsDetalle) throws BusinessException {
		return tesoreriaService.getLiquidacionDecretosDetalle(paramsDetalle);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.middleware.services.tesoreria.TesoreriaService#getCuentaClabeEfectivoPorCuentaNombrada(com.indeval.portaldali.middleware.services.modelo.AgenteVO)
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public String getCuentaClabeEfectivoPorCuentaNombrada(AgenteVO agente) {
		return tesoreriaService.getCuentaClabeEfectivoPorCuentaNombrada(agente);
	}
}
