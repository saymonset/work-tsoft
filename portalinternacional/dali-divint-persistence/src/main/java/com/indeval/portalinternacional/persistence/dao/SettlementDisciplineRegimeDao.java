package com.indeval.portalinternacional.persistence.dao;

import java.math.BigDecimal;
import java.util.List;

import com.indeval.portaldali.middleware.servicios.modelo.vo.PaginaVO;
import com.indeval.portalinternacional.middleware.servicios.dto.SettlementDisciplineRegimeVO;
import com.indeval.portalinternacional.middleware.servicios.modelo.LiquidacionParcialMoi;
import com.indeval.portalinternacional.middleware.servicios.modelo.SettlementDisciplineRegime;

public interface SettlementDisciplineRegimeDao {
	
	/**
	 * Obtiene la lista de Custodios que se tienen configurados para CSDR por Custodio
	 * @return List<SettlementDisciplineRegimeVO>
	 */
	public List<SettlementDisciplineRegimeVO> getSettlementDisciplineRegimeForCustodio();
	
	/**
	 * Obtiene la lista de Custodios que se tienen configurados para CSDR por Institucion
	 * @return List<SettlementDisciplineRegimeVO>
	 */
	public List<SettlementDisciplineRegimeVO> getSettlementDisciplineRegimeForInstitucion();
	
	public SettlementDisciplineRegimeVO getSettlementDisciplineRegimeByID(Long idConfigCsdr);
	
	public SettlementDisciplineRegime getModelSettlementDisciplineRegimeByID(Long idConfigCsdr);
	
	public void modificarSettlementDisciplineRegime(SettlementDisciplineRegimeVO settlementDisciplineRegime);
	
	public void eliminaSettlementDisciplineRegime(SettlementDisciplineRegime settlementDisciplineRegime);
	
	public Boolean findConfiguration(SettlementDisciplineRegime settlementDisciplineRegime);
	
	/**
	 * Metodo para obtener lista de Custodios que no estan configurados para CSDR
	 * @return
	 */
	public List<SettlementDisciplineRegimeVO> getCustodiosPendSettlementDisciplineRegime();
	
	public void saveCustodioSettlementDisciplineRegime(SettlementDisciplineRegime settlementDisciplineRegime);
	
	public void saveConfiguracionForInstitucion(SettlementDisciplineRegime settlementDisciplineRegime);
	
	/**
	 * Metodo para obtener detalle de las liquidaciones parciales
	 * @param folioControl
	 * @return
	 */
	public List<LiquidacionParcialMoi> getLiqParcialMoi(Long folioControl);
	
	/**
	 * Metodo para obtener detalle de las liquidaciones parciales
	 * @param paginaVO
	 * @param folioControl
	 * @return
	 */
	public PaginaVO getLiqParcialMoi(PaginaVO paginaVO, Long folioControl);
	
	/**
	 * Metodo para marcar/liquidaciones parciales que se van a liberar
	 * @param folioControl
	 * @param numeroLiquidacion
	 * @param idEstatusOperacion
	 */
	public void actualizaLiquidacionesParciales(Long folioControl, Long numeroLiquidacion, Long idEstatusOperacion);
	
	/**
	 * Actualiza el cambio de la parcialidad
	 * @param folioControl
	 * @param numeroLiquidacion
	 * @param idEstatusOperacion
	 */
	public void actualizaCambioStatusLiquidacionesParciales(LiquidacionParcialMoi liquidacionParcialMoi);
	
	/**
	 * Metodo para autorizar la configuracion de un Custodio para CSDR
	 * @param settlementDisciplineRegime
	 */
	public void autorizaCustodioCSDR(SettlementDisciplineRegimeVO settlementDisciplineRegime);
	
	/**
	 * Metodo para obtener la suma de las liquidaciones parciales
	 * @param folioControl
	 * @param isEfectivo
	 * @return
	 */
	public BigDecimal getTotalLiquidacionesParciales(Long folioControl, Boolean isEfectivo);
	
	/**
	 * Metodo para validar si el Custodio esta configurado para CSDR
	 * @param idCuentaBoveda
	 * @param typeCsrd
	 * @return
	 */
	public Boolean getSettlementDisciplineRegimeByIdCuentaBoveda(Long idCuentaBoveda, String typeCsrd);
	
    /**
     * Metodo para obtener detalle de las liquidaciones parciales con bitacora de cambio de estatus
     * @param folioControl
     * @param numeroParcialidad
     * @return
     */
    public LiquidacionParcialMoi getLiqParcialMoiByFolioControlAndParcialidad(Long folioControl, Long numeroParcialidad);

    /**
     * Metodo para obtener las configuracion pendiente por institucion para CSDR
     * @return
     */
	public List<SettlementDisciplineRegimeVO> getInstitucionesPendSettlementDisciplineRegime();

}
