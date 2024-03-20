/*
 * Copyright (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portalinternacional.middleware.services.divisioninternacional.ejb;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import com.indeval.portalinternacional.middleware.servicios.vo.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.middleware.servicios.modelo.dto.EstatusEmisionesDTO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.EmisionVO;
import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portaldali.persistence.modelo.Boveda;
import com.indeval.portalinternacional.middleware.services.SpringBeanAutowiringInterceptorDivInt;
import com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService;
import com.indeval.portalinternacional.middleware.servicios.modelo.BitacoraMensajeConciliacionInt;
import com.indeval.portalinternacional.middleware.servicios.modelo.BitacoraMensajeSwift;
import com.indeval.portalinternacional.middleware.servicios.modelo.BitacoraOperacionesSic;
import com.indeval.portalinternacional.middleware.servicios.modelo.CalendarioDerechos;
import com.indeval.portalinternacional.middleware.servicios.modelo.CatBic;
import com.indeval.portalinternacional.middleware.servicios.modelo.Control;
import com.indeval.portalinternacional.middleware.servicios.modelo.Custodio;
import com.indeval.portalinternacional.middleware.servicios.modelo.EstadoDerechoInt;
import com.indeval.portalinternacional.middleware.servicios.modelo.EstatusOperacion;
import com.indeval.portalinternacional.middleware.servicios.modelo.LiquidacionParcialMoi;
import com.indeval.portalinternacional.middleware.servicios.modelo.OperacionSic;
import com.indeval.portalinternacional.middleware.servicios.modelo.SicDetalle;
import com.indeval.portalinternacional.middleware.servicios.modelo.SicEmision;
import com.indeval.portalinternacional.middleware.servicios.modelo.TipoBeneficiario;
import com.indeval.portalinternacional.middleware.servicios.modelo.TipoPagoInt;
import com.indeval.protocolofinanciero.api.vo.TraspasoLibrePagoVO;

/**
 * @author Marcos Rivas
 *
 */

@Stateless(name = "ejb.divisionInternacionalService", mappedName = "ejb.divisionInternacionalService")
@Interceptors(SpringBeanAutowiringInterceptorDivInt.class)
@Remote(DivisionInternacionalService.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DivisionInternacionalEJB implements DivisionInternacionalService, Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private DivisionInternacionalService divisionInternacionalService;

    /**
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService#actualizaOperacionSIC(com.indeval.portalinternacional.middleware.servicios.modelo.OperacionSic[])
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public int actualizaOperacionSIC(OperacionSic[] operacionSic) throws BusinessException {
        return divisionInternacionalService.actualizaOperacionSIC(null);
    }

	/**
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService#businessRulesCapturaOperacion(com.indeval.portalinternacional.middleware.servicios.modelo.OperacionSic)
	 */
	public OperacionSic businessRulesCapturaOperacion(OperacionSic operacionSic) throws BusinessException {
		return divisionInternacionalService.businessRulesCapturaOperacion(operacionSic);
	}

	/**
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService#businessRulesCapturaTraspaso(com.indeval.protocolofinanciero.api.vo.TraspasoLibrePagoVO)
	 */
	public BigInteger businessRulesCapturaTraspaso(TraspasoLibrePagoVO tlpVO) throws BusinessException {
		return divisionInternacionalService.businessRulesCapturaTraspaso(tlpVO);
	}

	/**
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService#consultaOperaciones(com.indeval.portalinternacional.middleware.servicios.modelo.OperacionSic, com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO)
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public PaginaVO consultaOperaciones(OperacionSic operacionSic, PaginaVO paginaVO,
			Boolean debeDejarLog, Boolean obtenerTotalReg, Boolean rolSic) throws BusinessException {
		return divisionInternacionalService.consultaOperaciones(operacionSic, paginaVO,
				debeDejarLog, obtenerTotalReg, rolSic);
	}

	/**
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService#grabaOperacion(com.indeval.portalinternacional.middleware.servicios.vo.GrabaOperacionParams)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void grabaOperacion(GrabaOperacionParams grabaOperacionParams) throws BusinessException {
		divisionInternacionalService.grabaOperacion(grabaOperacionParams);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void insertaOperacionSIC(OperacionSic operacionSic) throws BusinessException {
		divisionInternacionalService.insertaOperacionSIC(operacionSic);

	}

	/**
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService#obtieneCustodios(com.indeval.portaldali.middleware.servicios.modelo.vo.EmisionVO)
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public CatBic[] obtieneCustodios(EmisionVO emisionVO) throws BusinessException {
	    return divisionInternacionalService.obtieneCustodios(emisionVO);
	}

	/**
	 * com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService#obtieneCustodioEnBaseAEmision(com.indeval.portaldali.middleware.servicios.modelo.vo.EmisionVO)
	 */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public CatBic[] obtieneCustodioEnBaseAEmision(EmisionVO emisionVO) throws BusinessException {
        return this.divisionInternacionalService.obtieneCustodioEnBaseAEmision(emisionVO);
    }

    /**
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService#obtieneDepositantes(com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO)
     */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public SicDetalle[] obtieneDepositantes(CatBic catBic) throws BusinessException {
        return divisionInternacionalService.obtieneDepositantes(catBic);
    }

    /**
     * @return the divisionInternacionalService
     */
    public DivisionInternacionalService getDivisionInternacionalService() {
        return divisionInternacionalService;
    }

    /**
     * @param divisionInternacionalService the divisionInternacionalService to set
     */
    public void setDivisionInternacionalService(
            DivisionInternacionalService divisionInternacionalService) {
        this.divisionInternacionalService = divisionInternacionalService;
    }

    /**
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService#obtieneEstatusOperacion()
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public EstatusOperacion[] obtieneEstatusOperacion() throws BusinessException {
        return divisionInternacionalService.obtieneEstatusOperacion();
    }

	/**
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService#getArqueoValores(com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO, com.indeval.portaldali.middleware.servicios.modelo.vo.EmisionVO, com.indeval.portalinternacional.middleware.servicios.vo.TotalArqueoVO, java.util.Date, java.lang.Boolean)
	 */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public ArqueoVO getArqueoValores(AgenteVO agenteVO, EmisionVO emisionVO, TotalArqueoVO totalArqueoVO, Date fechaConsulta, Boolean isExport) throws BusinessException {
		return (divisionInternacionalService.getArqueoValores(agenteVO, emisionVO, totalArqueoVO, fechaConsulta, isExport));
	}

	/**
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService#getListaEmisionesFideicomisoArqueo(com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO, java.util.Date)
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public EmisionVO[] getListaEmisionesFideicomisoArqueo(AgenteVO agenteVO, Date fechaConsulta) throws BusinessException {
		return (divisionInternacionalService.getListaEmisionesFideicomisoArqueo(agenteVO, fechaConsulta));
	}

	/**
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService#getTopeCirculanteFidecomiso(com.indeval.portaldali.middleware.servicios.modelo.vo.EmisionVO, com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO)
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public PaginaVO getTopeCirculanteFidecomiso(EmisionVO emisionVO, PaginaVO paginaVO) throws BusinessException {
		return (divisionInternacionalService.getTopeCirculanteFidecomiso(emisionVO, paginaVO));
	}

	/**
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService#consultaCustodios(com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO)
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<ConsultaCustodiosVO> consultaCustodios(AgenteVO agenteVO) throws BusinessException {
		return (divisionInternacionalService.consultaCustodios(agenteVO));
	}

	/**
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService#altaCustodio(com.indeval.portalinternacional.middleware.servicios.vo.AltaCustodioVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void altaCustodio(AltaCustodioVO altaCustodioVO) throws BusinessException {
		divisionInternacionalService.altaCustodio(altaCustodioVO);

	}

	/**
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService#altaEmision(com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO, com.indeval.portaldali.middleware.servicios.modelo.vo.EmisionVO, java.lang.String)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void altaEmision(EmisionVO emisionVO, CatBic catBic, String formaDeOperar) throws BusinessException {
		divisionInternacionalService.altaEmision(emisionVO, catBic, formaDeOperar);
	}

	/**
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService#altaDepositante(com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO, java.lang.String, java.lang.String, java.lang.String)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void altaDepositante(CatBic catBic, String bicDepLiq, String idDepLiq, String depLiq) throws BusinessException {
		divisionInternacionalService.altaDepositante(catBic, bicDepLiq, idDepLiq, depLiq);
	}

	/**
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService#getListaCierreFideicomiso(java.util.Date)
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<ConsultaCierreFideicomisoVO> getListaCierreFideicomiso(ConsultaCierreFideicomisoParams consultaCierreFideicomisoParams) throws BusinessException {
		return (divisionInternacionalService.getListaCierreFideicomiso(consultaCierreFideicomisoParams));
	}

	/**
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService#getListaSicDetalle(com.indeval.portalinternacional.middleware.servicios.modelo.CatBic, com.indeval.portaldali.middleware.servicios.modelo.vo.EmisionVO, java.lang.String, java.lang.String, com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO)
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public PaginaVO getListaSicDetalle(CatBic catBic, String bicDepLiq, String idDepLiq, String depLiq, PaginaVO paginaVO) throws BusinessException {
		return (divisionInternacionalService.getListaSicDetalle(catBic, bicDepLiq, idDepLiq, depLiq, paginaVO));
	}

	/**
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService#getListaSicEmisiones(com.indeval.portalinternacional.middleware.servicios.modelo.CatBic, com.indeval.portaldali.middleware.servicios.modelo.vo.EmisionVO, java.lang.String, com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO)
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public PaginaVO getListaSicEmisiones(CatBic catBic, EmisionVO emisionVO, String formaDeOperar, PaginaVO paginaVO) throws BusinessException {
		return (divisionInternacionalService.getListaSicEmisiones(catBic, emisionVO, formaDeOperar, paginaVO));
	}

	/**
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService#cancelaCustodio(com.indeval.portalinternacional.middleware.servicios.modelo.CatBic)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void cancelaCustodio(CatBic catBic) throws BusinessException {
		divisionInternacionalService.cancelaCustodio(catBic);
	}

	/**
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService#cancelaDepositante(com.indeval.portalinternacional.middleware.servicios.modelo.SicDetalle)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void cancelaDepositante(SicDetalle sicDetalle) throws BusinessException {
		divisionInternacionalService.cancelaDepositante(sicDetalle);
	}

	/**
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService#cancelaSicEmision(com.indeval.portalinternacional.middleware.servicios.modelo.SicEmision)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void cancelaSicEmision(SicEmision sicEmision) throws BusinessException {
		divisionInternacionalService.cancelaSicEmision(sicEmision);
	}

	/**
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService#updateCustodio(com.indeval.portalinternacional.middleware.servicios.modelo.CatBic)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateCustodio(AltaCustodioVO altaCustodioVO, CatBic catBic) throws BusinessException {
		divisionInternacionalService.updateCustodio(altaCustodioVO, catBic);
	}

	/**
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService#updateDepositante(com.indeval.portalinternacional.middleware.servicios.modelo.SicDetalle)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateDepositante(CatBic catBic, String bicDepLiq, String idDepLiq, String depLiq, SicDetalle sicDetalle) throws BusinessException {
		divisionInternacionalService.updateDepositante(catBic, bicDepLiq, idDepLiq, depLiq, sicDetalle);
	}

	/**
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService#updateSicEmision(com.indeval.portalinternacional.middleware.servicios.modelo.SicEmision)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateSicEmision(EmisionVO emisionVO, CatBic catBic, SicEmision sicEmision, String formaDeOperar) throws BusinessException {
		divisionInternacionalService.updateSicEmision(emisionVO, catBic, sicEmision, formaDeOperar);
	}

	/**
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService#consultaFideicomisos(com.indeval.portaldali.middleware.servicios.modelo.vo.AgenteVO, java.util.Date)
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<ConsultaFideicomisosVO> consultaFideicomisos(AgenteVO agenteVO, Date fechaConsulta) throws BusinessException {
		return (divisionInternacionalService.consultaFideicomisos(agenteVO, fechaConsulta));
	}

	/**
	 *
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public PaginaVO getListaEmisionesCustodio(CatBic catBic,
			EmisionVO emisionVO, PaginaVO paginaVO) throws BusinessException {
		return divisionInternacionalService.getListaEmisionesCustodio(catBic, emisionVO, paginaVO);
	}

	/**
	 *
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Object[]> obtieneAllCatBic() throws BusinessException{
		return divisionInternacionalService.obtieneAllCatBic();
	}

	/**
	 *
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<EstatusEmisionesDTO> obtieneEmisionesEstatus() throws BusinessException{
		return divisionInternacionalService.obtieneEmisionesEstatus();
	}

	/**
	 *
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public EstatusEmisionesDTO obtenerEstatusEmisionByPk(BigInteger idEstatusEmisiones) throws BusinessException{
		return divisionInternacionalService.obtenerEstatusEmisionByPk(idEstatusEmisiones);
	}

	/**
	 *
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public PaginaVO findSicEmisionesByEmisionAndCustodio(EstatusEmisionesDTO estatusEmisionesDTO,
			CatBic catBic, EmisionVO emisionVO, PaginaVO paginaVO, Boolean debeDejarLog) throws BusinessException{
		return divisionInternacionalService.findSicEmisionesByEmisionAndCustodio(estatusEmisionesDTO,
				catBic, emisionVO, paginaVO, debeDejarLog);
	}

    /**
     *
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public PaginaVO findSicEmisionesByEmisionAndCustodioPosicionCero(EstatusEmisionesDTO estatusEmisionesDTO,
            CatBic catBic, EmisionVO emisionVO, PaginaVO paginaVO) throws BusinessException{
        return divisionInternacionalService.findSicEmisionesByEmisionAndCustodioPosicionCero(estatusEmisionesDTO,
                catBic, emisionVO, paginaVO);
    }

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public PaginaVO consultaCalendarioDerechos(
			CalendarioEmisionesDeudaExtDTO params, PaginaVO pagina) throws BusinessException {
		return divisionInternacionalService.consultaCalendarioDerechos(params, pagina);
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<EstadoDerechoInt> obtieneEstadosDerechoInt()
			throws BusinessException {
		return divisionInternacionalService.obtieneEstadosDerechoInt();
	}
	/**
	 * @param isCAEV Tipo de Mensaje si es null trae todos
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<TipoPagoInt> obtieneTiposPagoInt(Boolean isCAEV) throws BusinessException {
		return divisionInternacionalService.obtieneTiposPagoInt(isCAEV);
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Custodio> obtieneCatalogoCustodios() throws BusinessException {
		return divisionInternacionalService.obtieneCatalogoCustodios();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Integer actualizarEstadosDerechoInt(Set<Long> ids, Integer nuevoEstado, boolean esSu) throws BusinessException {
		return divisionInternacionalService.actualizarEstadosDerechoInt(ids, nuevoEstado,esSu);
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<BitacoraMensajeSwift> consultaBitacoraMensajesSwift(Long id)
			throws BusinessException {
		return divisionInternacionalService.consultaBitacoraMensajesSwift(id);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<BitacoraMensajeSwiftVO> consultaBitacoraMensajesSwiftVO(Long id) throws BusinessException {
		return divisionInternacionalService.consultaBitacoraMensajesSwiftVO(id);
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public CalendarioDerechos consultaCalendarioDerechosById(Long id)
			throws BusinessException {
		return divisionInternacionalService.consultaCalendarioDerechosById(id);
	}
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Boveda> consultaBovedas(Integer tipoBoveda)
			throws BusinessException {
		return divisionInternacionalService.consultaBovedas( tipoBoveda);
	}
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void instruyeMensajeRetiro(Long idCalendario, Long idBoveda) {
		divisionInternacionalService.instruyeMensajeRetiro( idCalendario,  idBoveda);
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
		public OperacionSic consultaOperacionSicById(BigInteger id)
				throws BusinessException {
			return divisionInternacionalService.consultaOperacionSicById(id);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Control> obtieneEstadosMensajeria(String id)
			throws BusinessException {
		
		return divisionInternacionalService.obtieneEstadosMensajeria(id);
	}
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public PaginaVO consultaConciliacion(ConciliacionIntDTO conciliacion,
			PaginaVO paginaVO) throws BusinessException {
		return divisionInternacionalService.consultaConciliacion(conciliacion, paginaVO);		
	}
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void instruyeConciliacion(Long idConciliacion)
			throws BusinessException {
		divisionInternacionalService.instruyeConciliacion(idConciliacion);
		
	}
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public PaginaVO consultaDetalleConciliacion(
			DetalleConciliacionIntDTO detalleConciliacion, PaginaVO paginaVO)
			throws BusinessException {
		return divisionInternacionalService.consultaDetalleConciliacion(detalleConciliacion, paginaVO);
	}

	public List<BitacoraMensajeConciliacionInt> consultaBitacoraMensajeConciliacionInt(
			Long id) throws BusinessException {
		return divisionInternacionalService.consultaBitacoraMensajeConciliacionInt(id);
	}

	public void generaReporteAuditoriaConciliacion(Long idConciliacion)
			throws BusinessException {
		divisionInternacionalService.generaReporteAuditoriaConciliacion(idConciliacion);
		
	}

    public void realizarCambioDeBoveda(List<SicEmision> listaEmisiones, long idBoveda) throws BusinessException {
        divisionInternacionalService.realizarCambioDeBoveda(listaEmisiones, idBoveda);
    }

	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<String> listaCustodios() throws BusinessException {

		return divisionInternacionalService.listaCustodios();
	}

	public Map<String,String> getDerechosAutomatizadosDeudaMap() {		
		return divisionInternacionalService.getDerechosAutomatizadosDeudaMap();
	}

	/**
     * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.DivisionInternacionalService#consultaOperacionesPorFoliosControl(List, PaginaVO)
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public PaginaVO consultaOperacionesPorFoliosControl(List<BigInteger> foliosControl, PaginaVO paginaVO) throws BusinessException {
        return divisionInternacionalService.consultaOperacionesPorFoliosControl(foliosControl, paginaVO);
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<String> insertaCambioEstatusOperador(List<BitacoraOperacionesSic> bitacoraInsert,List<BitacoraOperacionesSic> bitacoraUpdate) {
		return divisionInternacionalService.insertaCambioEstatusOperador(bitacoraInsert, bitacoraUpdate);		
	}
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public  List<String> cancelaSolicitudBitacoraSic(List<BitacoraOperacionesSic> bitacorasCanceladas){
    	return divisionInternacionalService.cancelaSolicitudBitacoraSic(bitacorasCanceladas);
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public PaginaVO consultaOperacionesPorFoliosControlAutorizador(List<BigInteger> foliosControl, PaginaVO paginaVO) {    	
    	return divisionInternacionalService.consultaOperacionesPorFoliosControlAutorizador(foliosControl, paginaVO);
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void actualizaOperacionSICAutorizador(List<OperacionSic> operacionesSic,String autorizo) {    	
    	divisionInternacionalService.actualizaOperacionSICAutorizador(operacionesSic,autorizo);
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<String> actualizaMotivoCambioBitacoraSic(List<BitacoraOperacionesSic> motivosActualizadosLst){
    	return actualizaMotivoCambioBitacoraSic(motivosActualizadosLst);
    }
   
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public PaginaVO obtieneHistorialBitacoraSIC(HistorialBitacoraOperacionesSICDTO hist, PaginaVO paginaVO, boolean seAplicaCambioDeBoveda) throws BusinessException {
    	return divisionInternacionalService.obtieneHistorialBitacoraSIC(hist, paginaVO, seAplicaCambioDeBoveda);
    }
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<BitacoraMensajeSwift> consultaBitacoraMensajesSwiftByHist(Long id)
			throws BusinessException {
		return divisionInternacionalService.consultaBitacoraMensajesSwiftByHist(id);
	}

	@Override
	public List<BitacoraMensajeSwiftVO> getBitacoraMensajeSwiftbyIdHistVO(Long id) throws BusinessException {
		return divisionInternacionalService.getBitacoraMensajeSwiftbyIdHistVO(id);
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public boolean validarExistenciaTipoValor(String tv) throws BusinessException {
        return divisionInternacionalService.validarExistenciaTipoValor(tv);
    }
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<TipoBeneficiario> getTiposBeneficiario() throws BusinessException {
        return divisionInternacionalService.getTiposBeneficiario();
    }
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public TipoBeneficiario getTipoBeneficiarioById(Long id) throws BusinessException {
        return divisionInternacionalService.getTipoBeneficiarioById(id);
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public boolean validarNuevaBovedaEnEmision(Long idEmision, Long idBoveda) throws BusinessException {
        return divisionInternacionalService.validarNuevaBovedaEnEmision(idEmision, idBoveda);
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public boolean existePosicionEnCuenta5001Indeval(Long idOperacionSic, Long idBoveda, Long idEmision) throws BusinessException {
        return divisionInternacionalService.existePosicionEnCuenta5001Indeval(idOperacionSic, idBoveda, idEmision);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void actualizaOperacionSICCambioBovedaAutorizador(List<OperacionSic> operacionesSic,String autorizo) throws BusinessException {
        divisionInternacionalService.actualizaOperacionSICCambioBovedaAutorizador(operacionesSic, autorizo);
    }

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<LiquidacionParcialMoi> getLiqParcialMoi(Long folioControl) {
		return divisionInternacionalService.getLiqParcialMoi(folioControl);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Boolean actualizaParcialidadOperacionSIC(OperacionSic operacionSic) throws BusinessException {
		return divisionInternacionalService.actualizaParcialidadOperacionSIC(null);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void actualizaLiquidacionesParciales(Long folioControl, Long numeroLiquidacion, Long idEstatusOperacion) {
		divisionInternacionalService.actualizaLiquidacionesParciales(folioControl, numeroLiquidacion, idEstatusOperacion);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public PaginaVO getLiqParcialMoi(PaginaVO paginaVO, Long folioControl) {
		return divisionInternacionalService.getLiqParcialMoi(paginaVO, folioControl); 
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public PaginaVO getLiqParcialMoiWithBitacora(PaginaVO paginaVO, List<BigInteger> folioControl) {
		return divisionInternacionalService.getLiqParcialMoiWithBitacora(paginaVO, folioControl);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public PaginaVO getLiqParcialMoiChangeStatus(PaginaVO paginaVO, Long folioControl) {
		return divisionInternacionalService.getLiqParcialMoiChangeStatus(paginaVO, folioControl);
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void actualizaOperacionSICAutorizadorParcialidades(List<LiquidacionParcialMoi> listaLiquidacionesParciales,
			String cveAutorizo, OperacionSic operacionSic) {
		divisionInternacionalService.actualizaOperacionSICAutorizadorParcialidades(listaLiquidacionesParciales, cveAutorizo, operacionSic);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Long findOperacionesParcialesPendientes(Long idCuentaNombrada) {
		return divisionInternacionalService.findOperacionesParcialesPendientes(idCuentaNombrada);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void updateStatusOperacionWithParcialidad(List<LiquidacionParcialMoi> lstLiquidacionParcialMoi,
			OperacionSic operacionSic) {
		divisionInternacionalService.updateStatusOperacionWithParcialidad(lstLiquidacionParcialMoi, operacionSic);
	}

}
