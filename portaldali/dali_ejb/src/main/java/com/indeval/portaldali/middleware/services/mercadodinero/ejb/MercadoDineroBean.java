/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * EnvioOperacionesBean.java
 * Apr 15, 2008
 */
package com.indeval.portaldali.middleware.services.mercadodinero.ejb;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;

import com.indeval.portaldali.middleware.services.SpringBeanAutowiringInterceptorDali;
import com.indeval.portaldali.middleware.services.mercadodinero.AdministracionGarantiaVO;
import com.indeval.portaldali.middleware.services.mercadodinero.CapturaConcertacionPrestamosParams;
import com.indeval.portaldali.middleware.services.mercadodinero.CapturaGarantiasParams;
import com.indeval.portaldali.middleware.services.mercadodinero.ConsultaEstatusOperacionesParams;
import com.indeval.portaldali.middleware.services.mercadodinero.ConsultaMovimientosMiscFiscalParams;
import com.indeval.portaldali.middleware.services.mercadodinero.ConsultaPrestamosParams;
import com.indeval.portaldali.middleware.services.mercadodinero.EstadoCuentaTotalMDVO;
import com.indeval.portaldali.middleware.services.mercadodinero.EstatusOperacionesVO;
import com.indeval.portaldali.middleware.services.mercadodinero.GetEstadoCuentaParams;
import com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService;
import com.indeval.portaldali.middleware.services.mercadodinero.OperacionDiaDetalleDineroVO;
import com.indeval.portaldali.middleware.services.mercadodinero.OperacionDiaDineroParams;
import com.indeval.portaldali.middleware.services.mercadodinero.OperacionDiaDineroVO;
import com.indeval.portaldali.middleware.services.mercadodinero.ParcialidadVO;
import com.indeval.portaldali.middleware.services.mercadodinero.PosicionValorGarantiaVO;
import com.indeval.portaldali.middleware.services.mercadodinero.PosicionValoresParams;
import com.indeval.portaldali.middleware.services.mercadodinero.PosicionValoresSimpleTotalVO;
import com.indeval.portaldali.middleware.services.mercadodinero.PosturaPrestamistaVO;
import com.indeval.portaldali.middleware.services.mercadodinero.PrestamoVO;
import com.indeval.portaldali.middleware.services.mercadodinero.RegistraOperacionParams;
import com.indeval.portaldali.middleware.services.mercadodinero.TraspasoDineroParams;
import com.indeval.portaldali.middleware.services.mercadodinero.TraspasoMercadoDineroParams;
import com.indeval.portaldali.middleware.services.mercadodinero.TraspasoMiscFiscalVO;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.modelo.EmisionVO;
import com.indeval.portaldali.middleware.services.modelo.PaginaVO;

/**
 * Enterprise Java Bean para exponer el servicio de negocio de Mercado de Dinero
 * 
 * @author Emigdio Hernández
 * @version 1.0
 * 
 */
@Stateless(name = "ejb.mercadoDinero", mappedName = "ejb.mercadoDinero")
@Interceptors(SpringBeanAutowiringInterceptorDali.class)
@Remote(MercadoDineroService.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class MercadoDineroBean implements MercadoDineroService {

	@Autowired
	private MercadoDineroService mercadoDineroService = null;

	/**
	 * @return the mercadoDineroService
	 */
	public MercadoDineroService getMercadoDineroService() {
		return mercadoDineroService;
	}

	/**
	 * @param mercadoDineroService
	 *            the mercadoDineroService to set
	 */
	public void setMercadoDineroService(MercadoDineroService mercadoDineroService) {
		this.mercadoDineroService = mercadoDineroService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.middleware.services.mercadodinero.ejb.MercadoDineroService#agregarDiasHabiles(java.util.Date,
	 *      int)
	 */
	public Date agregarDiasHabiles(Date arg0, int arg1) throws BusinessException {
		return mercadoDineroService.agregarDiasHabiles(arg0, arg1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.middleware.services.mercadodinero.ejb.MercadoDineroService#getFolioOperaciones(java.util.Date)
	 */
	public Integer getFolioOperaciones(Date arg0) throws BusinessException {
		// return mercadoDineroService.getFolioOperaciones(arg0);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.middleware.services.mercadodinero.ejb.MercadoDineroService#validaGetColocacionPrimariaRecompraBusinessRules(com.indeval.portaldali.middleware.services.mercadodinero.ejb.RegistraOperacionParams)
	 */
	public String validaGetColocacionPrimariaRecompraBusinessRules(RegistraOperacionParams arg0) throws BusinessException {
		return mercadoDineroService.validaGetColocacionPrimariaRecompraBusinessRules(arg0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.middleware.services.mercadodinero.ejb.MercadoDineroService#validaRegistraOperacionBusinessRules(com.indeval.portaldali.middleware.services.mercadodinero.ejb.RegistraOperacionParams)
	 */
	public String validaRegistraOperacionBusinessRules(RegistraOperacionParams arg0) throws BusinessException {
		return mercadoDineroService.validaRegistraOperacionBusinessRules(arg0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.middleware.services.mercadodinero.ejb.MercadoDineroService#validaTVMD(java.lang.String)
	 */
	public String validaTVMD(String arg0) throws BusinessException {
		return mercadoDineroService.validaTVMD(arg0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService#administracionGarantias(java.math.BigInteger,
	 *      java.lang.String, java.lang.String)
	 */
	public AdministracionGarantiaVO[] administracionGarantias(BigInteger folio, String idInstitucion, String folioInstitucion) throws BusinessException {
		return mercadoDineroService.administracionGarantias(folio, idInstitucion, folioInstitucion);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService#cancelaOperacion(com.indeval.portaldali.middleware.services.modelo.AgenteVO,
	 *      java.lang.String, java.lang.Boolean)
	 */
	public BigInteger[] cancelaOperacion(AgenteVO agente, String llaveFolio, Boolean isAgenteFirmado) throws BusinessException {
		return mercadoDineroService.cancelaOperacion(agente, llaveFolio, isAgenteFirmado);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService#capturaConcertacionPrestamos(com.indeval.portaldali.middleware.services.mercadodinero.CapturaConcertacionPrestamosParams)
	 */
	public Integer capturaConcertacionPrestamos(CapturaConcertacionPrestamosParams params) throws BusinessException {
		return mercadoDineroService.capturaConcertacionPrestamos(params);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService#capturaGarantias(com.indeval.portaldali.middleware.services.mercadodinero.CapturaGarantiasParams)
	 */
	public String capturaGarantias(CapturaGarantiasParams params) throws BusinessException {
		return mercadoDineroService.capturaGarantias(params);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService#confirmaAperturaSistemaDinero(com.indeval.portaldali.middleware.services.modelo.AgenteVO,
	 *      java.math.BigInteger[], boolean)
	 */
	public boolean confirmaAperturaSistemaDinero(AgenteVO agenteFirmado, BigInteger[] folioTraspaso, boolean aperturaSistema) throws BusinessException {
		return mercadoDineroService.confirmaAperturaSistemaDinero(agenteFirmado, folioTraspaso, aperturaSistema);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService#confirmaOperacion(com.indeval.portaldali.middleware.services.modelo.AgenteVO,
	 *      java.lang.String, java.lang.Boolean)
	 */
	public BigInteger[] confirmaOperacion(AgenteVO agente, String llaveFolio, Boolean isAgenteFirmado) throws BusinessException {
		return mercadoDineroService.confirmaOperacion(agente, llaveFolio, isAgenteFirmado);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService#consultaDatosOperacionGarantias(com.indeval.portaldali.middleware.services.modelo.EmisionVO,
	 *      java.math.BigDecimal)
	 */
	public BigInteger consultaDatosOperacionGarantias(EmisionVO emision, BigDecimal Excedente) throws BusinessException {
		return mercadoDineroService.consultaDatosOperacionGarantias(emision, Excedente);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService#consultaEstatusOperaciones(com.indeval.portaldali.middleware.services.mercadodinero.ConsultaEstatusOperacionesParams)
	 */
	public EstatusOperacionesVO consultaEstatusOperaciones(ConsultaEstatusOperacionesParams params) throws BusinessException {
		return mercadoDineroService.consultaEstatusOperaciones(params);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService#consultaMovimientosMiscFiscal(com.indeval.portaldali.middleware.services.mercadodinero.ConsultaMovimientosMiscFiscalParams)
	 */
	public PaginaVO consultaMovimientosMiscFiscal(ConsultaMovimientosMiscFiscalParams consultaMovimientosMiscFiscalParams) throws BusinessException {
		return mercadoDineroService.consultaMovimientosMiscFiscal(consultaMovimientosMiscFiscalParams);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService#consultaPosicionValores(com.indeval.portaldali.middleware.services.mercadodinero.PosicionValoresParams)
	 */
	public PaginaVO consultaPosicionValores(PosicionValoresParams params) throws BusinessException {
		return mercadoDineroService.consultaPosicionValores(params);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService#consultaPosicionValoresGarantias(com.indeval.portaldali.middleware.services.modelo.EmisionVO,
	 *      java.lang.String,
	 *      com.indeval.portaldali.middleware.services.modelo.AgenteVO)
	 */
	public PosicionValorGarantiaVO[] consultaPosicionValoresGarantias(EmisionVO emision, String idTipoPapel, AgenteVO agente) throws BusinessException {
		return mercadoDineroService.consultaPosicionValoresGarantias(emision, idTipoPapel, agente);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService#consultaPrestamos(com.indeval.portaldali.middleware.services.mercadodinero.ConsultaPrestamosParams)
	 */
	public PrestamoVO[] consultaPrestamos(ConsultaPrestamosParams params) throws BusinessException {
		return mercadoDineroService.consultaPrestamos(params);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService#consultarDiasInhabilesByMonth(int,
	 *      int)
	 */
	@SuppressWarnings("unchecked")
	public List consultarDiasInhabilesByMonth(int month, int year) throws BusinessException {
		return mercadoDineroService.consultarDiasInhabilesByMonth(month, year);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService#detalleParcialidades(java.lang.String)
	 */
	public ParcialidadVO detalleParcialidades(String folio) throws BusinessException {
		return mercadoDineroService.detalleParcialidades(folio);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService#ejecutaOperacionGarantias(com.indeval.portaldali.middleware.services.modelo.AgenteVO,
	 *      com.indeval.portaldali.middleware.services.modelo.EmisionVO,
	 *      java.math.BigDecimal, java.math.BigInteger, java.lang.String)
	 */
	public Integer ejecutaOperacionGarantias(AgenteVO agente, EmisionVO emision, BigDecimal cantidadLiberada, BigInteger folioPrestamo, String tipoOperacion)
			throws BusinessException {
		return mercadoDineroService.ejecutaOperacionGarantias(agente, emision, cantidadLiberada, folioPrestamo, tipoOperacion);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService#getColocacionPrimariaRecompra(com.indeval.portaldali.middleware.services.mercadodinero.RegistraOperacionParams)
	 */
	public String getColocacionPrimariaRecompra(RegistraOperacionParams params) throws BusinessException {
		return mercadoDineroService.getColocacionPrimariaRecompra(params);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService#getDetalleOperacionDiaDinero(com.indeval.portaldali.middleware.services.mercadodinero.TraspasoDineroParams)
	 */
	public OperacionDiaDetalleDineroVO getDetalleOperacionDiaDinero(TraspasoDineroParams traspasoDineroVO) throws BusinessException {
		return mercadoDineroService.getDetalleOperacionDiaDinero(traspasoDineroVO);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService#getEstadoCuenta(com.indeval.portaldali.middleware.services.mercadodinero.GetEstadoCuentaParams)
	 */
	public EstadoCuentaTotalMDVO getEstadoCuenta(GetEstadoCuentaParams params) throws BusinessException {
		return mercadoDineroService.getEstadoCuenta(params);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService#getListaConfirmacionApertura(com.indeval.portaldali.middleware.services.modelo.AgenteVO,
	 *      boolean)
	 */
	public TraspasoMiscFiscalVO[] getListaConfirmacionApertura(AgenteVO agenteFirmado, boolean aperturaSistema) throws BusinessException {
		return mercadoDineroService.getListaConfirmacionApertura(agenteFirmado, aperturaSistema);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService#getListaConfirmacionMiscFiscal(com.indeval.portaldali.middleware.services.modelo.AgenteVO)
	 */
	public TraspasoMiscFiscalVO[] getListaConfirmacionMiscFiscal(AgenteVO agenteFirmado) throws BusinessException {
		return mercadoDineroService.getListaConfirmacionMiscFiscal(agenteFirmado);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService#getOperacionesDiaDinero(com.indeval.portaldali.middleware.services.mercadodinero.OperacionDiaDineroParams)
	 */
	public OperacionDiaDineroVO getOperacionesDiaDinero(OperacionDiaDineroParams operacionDiaDineroParams) throws BusinessException {
		return mercadoDineroService.getOperacionesDiaDinero(operacionDiaDineroParams);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService#getPosicionValorSimple(com.indeval.portaldali.middleware.services.modelo.AgenteVO,
	 *      com.indeval.portaldali.middleware.services.modelo.EmisionVO,
	 *      com.indeval.portaldali.middleware.services.modelo.PaginaVO, boolean)
	 */
	public PosicionValoresSimpleTotalVO[] getPosicionValorSimple(AgenteVO agenteVO, EmisionVO emisionVO, PaginaVO paginaVO, boolean export)
			throws BusinessException {
		return mercadoDineroService.getPosicionValorSimple(agenteVO, emisionVO, paginaVO, export);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService#getPosturaPrestamista(com.indeval.portaldali.middleware.services.modelo.AgenteVO,
	 *      com.indeval.portaldali.middleware.services.modelo.EmisionVO)
	 */
	public PosturaPrestamistaVO getPosturaPrestamista(AgenteVO agenteFirmado, EmisionVO emision) throws BusinessException {
		return mercadoDineroService.getPosturaPrestamista(agenteFirmado, emision);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService#getPrecioVectorValpreE(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List getPrecioVectorValpreE(String tv) throws BusinessException {
		return mercadoDineroService.getPrecioVectorValpreE(tv);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService#getTvsPrecioVectorValpreE()
	 */
	@SuppressWarnings("unchecked")
	public List getTvsPrecioVectorValpreE() throws BusinessException {
		return mercadoDineroService.getTvsPrecioVectorValpreE();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService#prorroga(java.math.BigDecimal)
	 */
	public BigDecimal prorroga(BigDecimal folioPrestamo) throws BusinessException {
		return mercadoDineroService.prorroga(folioPrestamo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService#registraOperacion(com.indeval.portaldali.middleware.services.mercadodinero.RegistraOperacionParams)
	 */
	public String registraOperacion(RegistraOperacionParams params) throws BusinessException {
		return mercadoDineroService.registraOperacion(params);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService#setActualizaParametrosValpreE(java.util.HashMap)
	 */
	@SuppressWarnings("unchecked")
	public void setActualizaParametrosValpreE(HashMap mapaParametros) throws BusinessException {
		// TODO Auto-generated method stub
	}

	/*
	 * (non-Javadoc)
	 * @see com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService#setConfirmacionMiscFiscal(com.indeval.portaldali.middleware.services.modelo.AgenteVO, java.lang.Integer[])
	 */
	public Integer[] setConfirmacionMiscFiscal(AgenteVO agenteFirmado, Integer[] foliosTraspasos) throws BusinessException {
		return mercadoDineroService.setConfirmacionMiscFiscal(agenteFirmado, foliosTraspasos);
	}

	/*
	 * (non-Javadoc)
	 * @see com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService#traspasoMercadoDinero(com.indeval.portaldali.middleware.services.mercadodinero.TraspasoMercadoDineroParams)
	 */
	public BigInteger traspasoMercadoDinero(TraspasoMercadoDineroParams params) throws BusinessException {
		return mercadoDineroService.traspasoMercadoDinero(params);
	}

	/*
	 * (non-Javadoc)
	 * @see com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService#traspasoMercadoDineroBusinessRules(com.indeval.portaldali.middleware.services.mercadodinero.TraspasoMercadoDineroParams)
	 */
	public BigInteger traspasoMercadoDineroBusinessRules(TraspasoMercadoDineroParams params) throws BusinessException {
		return mercadoDineroService.traspasoMercadoDineroBusinessRules(params);
	}

	/*
	 * (non-Javadoc)
	 * @see com.indeval.portaldali.middleware.services.mercadodinero.MercadoDineroService#vencimientoAnticipado(java.math.BigDecimal)
	 */
	public boolean vencimientoAnticipado(BigDecimal folioPrestamo) throws BusinessException {
		return mercadoDineroService.vencimientoAnticipado(folioPrestamo);
	}

}
