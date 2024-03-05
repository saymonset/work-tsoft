/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * EnvioOperacionesBean.java
 * Apr 15, 2008
 */
package com.indeval.portaldali.middleware.services.enviooperaciones.ejb;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;

import com.indeval.portaldali.middleware.dto.BovedaDTO;
import com.indeval.portaldali.middleware.dto.DepositoDivisaDTO;
import com.indeval.portaldali.middleware.dto.RetiroEfectivoDTO;
import com.indeval.portaldali.middleware.dto.RetiroEfectivoInternacionalDTO;
import com.indeval.portaldali.middleware.services.SpringBeanAutowiringInterceptorDali;
import com.indeval.portaldali.middleware.services.enviooperaciones.EnvioOperacionesService;
import com.indeval.portaldali.middleware.services.modelo.AgenteVO;
import com.indeval.portaldali.middleware.services.modelo.BusinessException;
import com.indeval.portaldali.middleware.services.modelo.PaginaVO;
import com.indeval.portaldali.middleware.services.util.vo.BitacoraMatchParams;
import com.indeval.portaldali.middleware.services.util.vo.BitacoraMatchVO;
import com.indeval.portaldali.middleware.services.util.vo.BitacoraVOParams;
import com.indeval.portaldali.middleware.services.util.vo.BitacoraVOTotales;
import com.indeval.protocolofinanciero.api.ProtocoloFinancieroException;
import com.indeval.protocolofinanciero.api.vo.RetiroEfectivoVO;
import com.indeval.protocolofinanciero.api.vo.TraspasoContraPagoVO;
import com.indeval.protocolofinanciero.api.vo.TraspasoEfectivoFvVO;
import com.indeval.protocolofinanciero.api.vo.TraspasoEfectivoVO;
import com.indeval.protocolofinanciero.api.vo.TraspasoLibrePagoVO;

/**
 * Enterprise Java Bean para exponer el servicio de negocio
 *
 * @author José Antonio Huizar Moreno
 * @version 1.0
 *
 */
@Stateless(name = "ejb.envioOperaciones", mappedName = "ejb.envioOperaciones")
@Interceptors(SpringBeanAutowiringInterceptorDali.class)
@Remote(EnvioOperacionesService.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class EnvioOperacionesBean implements EnvioOperacionesService {

	@Autowired
	private EnvioOperacionesService envioOperacionesService = null;

	/**
	 * Obtiene el campo envioOperacionesService
	 *
	 * @return envioOperacionesService
	 */
	public EnvioOperacionesService getEnvioOperacionesService() {
		return envioOperacionesService;
	}

	/**
	 * Asigna el campo envioOperacionesService
	 *
	 * @param envioOperacionesService
	 *            el valor de envioOperacionesService a asignar
	 */
	public void setEnvioOperacionesService(EnvioOperacionesService envioOperacionesService) {
		this.envioOperacionesService = envioOperacionesService;
	}



	public String generaIso(BigInteger idBitacoraMatch, AgenteVO agenteFirmado, BovedaDTO bovedaDTO, Date fechaHoraCierreOper,
			Boolean esConfirmacion) {
		return envioOperacionesService.generaIso(idBitacoraMatch, agenteFirmado,bovedaDTO, fechaHoraCierreOper,esConfirmacion);

	}


	public String generaIso(RetiroEfectivoInternacionalDTO REI){
		return envioOperacionesService.generaIso(REI);

	}

	public String generaIso(RetiroEfectivoDTO REI){
		return envioOperacionesService.generaIso(REI);

	}



	/*
	 * (non-Javadoc)
	 *
	 * @see com.indeval.portaldali.middleware.services.enviooperaciones.EnvioOperacionesService#confirmaOperacionMatch(java.math.BigInteger,
	 *      com.indeval.portaldali.middleware.services.modelo.AgenteVO)
	 */
	public BigInteger confirmaOperacionMatch(BigInteger idBitacoraMatch, AgenteVO agenteFirmado, BovedaDTO bovedaDTO, HashMap datosAdicionales, String isoFirmado, Date fechaHoraCierreOper) throws BusinessException {
		return envioOperacionesService.confirmaOperacionMatch(idBitacoraMatch, agenteFirmado, bovedaDTO, null,isoFirmado, fechaHoraCierreOper);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.indeval.portaldali.middleware.services.enviooperaciones.EnvioOperacionesService#grabaOperacion(com.indeval.protocolofinanciero.api.vo.TraspasoContraPagoVO,
	 *      boolean, java.util.HashMap, java.lang.String, java.lang.String)
	 */
    public BigInteger grabaOperacion(TraspasoContraPagoVO vo, boolean isCompra,
            @SuppressWarnings("rawtypes") HashMap datosAdicionales,
            String origenRegistro, String isoFirmado) throws BusinessException {
        return envioOperacionesService.grabaOperacion(vo, isCompra,
                datosAdicionales, origenRegistro, isoFirmado);

    }

	/*
	 * (non-Javadoc)
	 *
	 * @see com.indeval.portaldali.middleware.services.enviooperaciones.EnvioOperacionesService#grabaOperacion(com.indeval.protocolofinanciero.api.vo.TraspasoContraPagoVO,
	 *      java.lang.String, boolean, java.util.HashMap, java.lang.String,
	 *      java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public void grabaOperacion(TraspasoContraPagoVO vo, String folioControl, boolean isCompra, HashMap datosAdicionales, String origenRegistro,
			String isoFirmado) throws BusinessException {
		 envioOperacionesService.grabaOperacion(vo, folioControl, isCompra,
		 datosAdicionales, origenRegistro, isoFirmado);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.indeval.portaldali.middleware.services.enviooperaciones.EnvioOperacionesService#grabaOperacion(com.indeval.protocolofinanciero.api.vo.TraspasoLibrePagoVO,
	 *      boolean, java.util.HashMap, java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public BigInteger grabaOperacion(TraspasoLibrePagoVO vo, boolean isCompra, HashMap datosAdicionales, String origenRegistro, String isoFirmado)
			throws BusinessException {
		 return envioOperacionesService.grabaOperacion(vo, isCompra,
		 datosAdicionales, origenRegistro, isoFirmado);

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.indeval.portaldali.middleware.services.enviooperaciones.EnvioOperacionesService#actualizaEstadoInstruccionExpira(java.math.BigInteger,
	 *      com.indeval.portaldali.middleware.services.modelo.AgenteVO, boolean)
	 */
	public BigInteger actualizaEstadoInstruccionExpira(BigInteger idBitacoraMatch, AgenteVO agenteFirmado, HashMap datosAdicionales, String isoFirmado) throws BusinessException {
		return envioOperacionesService.actualizaEstadoInstruccionExpira(idBitacoraMatch, agenteFirmado, null, isoFirmado);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.indeval.portaldali.middleware.services.enviooperaciones.EnvioOperacionesService#enviaOperacion(com.indeval.protocolofinanciero.api.vo.TraspasoContraPagoVO,
	 *      java.lang.String, boolean)
	 */
	public void enviaOperacion(TraspasoContraPagoVO vo, String folioControl, boolean isCompra) throws ProtocoloFinancieroException, BusinessException {
		envioOperacionesService.enviaOperacion(vo, folioControl, isCompra);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.indeval.portaldali.middleware.services.enviooperaciones.EnvioOperacionesService#enviaOperacion(com.indeval.protocolofinanciero.api.vo.TraspasoLibrePagoVO,
	 *      java.lang.String, boolean)
	 */
	public void enviaOperacion(TraspasoLibrePagoVO vo, String folioControl, boolean isCompra) throws ProtocoloFinancieroException, BusinessException {
		envioOperacionesService.enviaOperacion(vo, folioControl, isCompra);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.indeval.portaldali.middleware.services.enviooperaciones.EnvioOperacionesService#enviaOperacionSimple(com.indeval.protocolofinanciero.api.vo.TraspasoContraPagoVO,
	 *      java.lang.String, boolean)
	 */
	public void enviaOperacionSimple(TraspasoContraPagoVO vo, String folioControl, boolean isCompra) throws ProtocoloFinancieroException, BusinessException {
		envioOperacionesService.enviaOperacion(vo, folioControl, isCompra);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.indeval.portaldali.middleware.services.enviooperaciones.EnvioOperacionesService#enviaOperacionSimple(com.indeval.protocolofinanciero.api.vo.TraspasoLibrePagoVO,
	 *      java.lang.String, boolean)
	 */
	public void enviaOperacionSimple(TraspasoLibrePagoVO vo, String folioControl, boolean isCompra) throws ProtocoloFinancieroException, BusinessException {
		envioOperacionesService.enviaOperacion(vo, folioControl, isCompra);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.indeval.portaldali.middleware.services.enviooperaciones.EnvioOperacionesService#getMensajeBitacora(com.indeval.portaldali.middleware.services.util.vo.BitacoraVOParams)
	 */
	public PaginaVO getMensajeBitacora(BitacoraVOParams bitacoraVO) throws BusinessException {
		return envioOperacionesService.getMensajeBitacora(bitacoraVO);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.indeval.portaldali.middleware.services.enviooperaciones.EnvioOperacionesService#getMensajeBitacoraMatch(com.indeval.portaldali.middleware.services.util.vo.BitacoraMatchParams)
	 */
	public BitacoraMatchVO[] getMensajeBitacoraMatch(BitacoraMatchParams bitacoraVO) throws BusinessException {
		return envioOperacionesService.getMensajeBitacoraMatch(bitacoraVO);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.indeval.portaldali.middleware.services.enviooperaciones.EnvioOperacionesService#getTotalesBitacora(com.indeval.portaldali.middleware.services.util.vo.BitacoraVOParams)
	 */
	public BitacoraVOTotales getTotalesBitacora(BitacoraVOParams bitacoraVO) throws BusinessException {
		return envioOperacionesService.getTotalesBitacora(bitacoraVO);
	}

	/*
	 *
	 */
	@SuppressWarnings("unchecked")
	public void grabaOperacion(TraspasoLibrePagoVO vo, String folioControl, boolean isCompra, HashMap datosAdicionales, String origenRegistro)
			throws BusinessException, ProtocoloFinancieroException {
		envioOperacionesService.enviaOperacion(vo,folioControl,isCompra);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.indeval.portaldali.middleware.services.enviooperaciones.EnvioOperacionesService#recibeOperacion()
	 */
	public void recibeOperacion() throws ProtocoloFinancieroException, BusinessException {
		envioOperacionesService.recibeOperacion();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.indeval.portaldali.middleware.services.enviooperaciones.EnvioOperacionesService#creaISO(java.lang.Object,
	 *      boolean)
	 */
	public String creaISO(Object vo, boolean isCompra, boolean esconfirmacion) throws BusinessException {
		return envioOperacionesService.creaISO(vo, isCompra, esconfirmacion);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.indeval.portaldali.middleware.services.enviooperaciones.EnvioOperacionesService#grabaOperacion(com.indeval.protocolofinanciero.api.vo.TraspasoLibrePagoVO,
	 *      java.lang.String, boolean, java.util.HashMap, java.lang.String,
	 *      java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public void grabaOperacion(TraspasoLibrePagoVO vo, String folioControl, boolean isCompra, HashMap datosAdicionales, String origenRegistro, String isoFirmado)
			throws BusinessException {
		envioOperacionesService.grabaOperacion(vo, folioControl, isCompra, datosAdicionales, origenRegistro, isoFirmado);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.indeval.portaldali.middleware.services.enviooperaciones.EnvioOperacionesService#grabaOperacion(com.indeval.protocolofinanciero.api.vo.RetiroEfectivoVO,
	 *      java.lang.String, boolean, java.util.HashMap, java.lang.String,
	 *      java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public void grabaOperacion(RetiroEfectivoVO vo, String folioControl, boolean isCompra, HashMap datosAdicionales, String origenRegistro, String isoFirmado)
			throws BusinessException {
		envioOperacionesService.grabaOperacion(vo, folioControl,isCompra, datosAdicionales, origenRegistro, isoFirmado);
	}



	/*
	 * (non-Javadoc)
	 *
	 * @see com.indeval.portaldali.middleware.services.enviooperaciones.EnvioOperacionesService#grabaOperacion(com.indeval.protocolofinanciero.api.vo.TraspasoEfectivoVO,
	 *      java.lang.String, boolean, java.util.HashMap, java.lang.String,
	 *      java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public void grabaOperacion(TraspasoEfectivoVO traspasoEfectivoVO, String folioControl, boolean isCompra, HashMap datosAdicionales, String origenRegistro,
			String isoFirmado) throws BusinessException {
		envioOperacionesService.grabaOperacion(traspasoEfectivoVO, folioControl, isCompra, datosAdicionales, origenRegistro, isoFirmado);
	}

	public PaginaVO getBitacoras(Object bitacoraOperaciones, PaginaVO paginaVO) {
		return envioOperacionesService.getBitacoras(bitacoraOperaciones, paginaVO);
	}

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void grabaOperacion(final DepositoDivisaDTO depositoDivisa,
            final Map<String, Object> datosAdicionales, final String isoFirmado) {
        envioOperacionesService.grabaOperacion(depositoDivisa, datosAdicionales, isoFirmado);
    }

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void grabaOperacion(TraspasoEfectivoFvVO vo, String folioControl,
			boolean isCompra, HashMap datosAdicionales, String origenRegistro,
			String isoFirmado,boolean isCancelacion) throws BusinessException {
		envioOperacionesService.grabaOperacion(vo,folioControl,isCompra,datosAdicionales,origenRegistro,isoFirmado,isCancelacion);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void grabaOperacion(RetiroEfectivoInternacionalDTO vo, String folioControl,
			boolean isCompra, HashMap datosAdicionales, String origenRegistro,
			String isoFirmado) throws BusinessException {
		envioOperacionesService.grabaOperacion(vo,folioControl,isCompra,datosAdicionales,origenRegistro,isoFirmado);

	}

	public void grabaOperacion(RetiroEfectivoDTO vo, HashMap datosAdicionales,
			String origenRegistro, String isoFirmado) throws BusinessException {
		// TODO Auto-generated method stub
		envioOperacionesService.grabaOperacion(vo, datosAdicionales, origenRegistro, isoFirmado);
	}





}
