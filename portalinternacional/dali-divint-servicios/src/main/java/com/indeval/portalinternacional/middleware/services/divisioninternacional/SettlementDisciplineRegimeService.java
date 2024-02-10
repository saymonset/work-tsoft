package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import java.util.List;

import com.indeval.portalinternacional.middleware.servicios.dto.SettlementDisciplineRegimeVO;
import com.indeval.portalinternacional.middleware.servicios.modelo.SettlementDisciplineRegime;

public interface SettlementDisciplineRegimeService {
	
	/**
	 * Metodo para obtener la lista  de Custodios para CSDR por Custodio
	 * @param tipoConfiguracion
	 */
	public List<SettlementDisciplineRegimeVO> getSettlementDisciplineRegimeForCustodio();

	/**
	 * Metodo para obtener la lista  de Custodios para CSDR por Institucion
	 * @param tipoConfiguracion
	 */
	public List<SettlementDisciplineRegimeVO> getSettlementDisciplineRegimeForInstitucion();
	
	/**
	 * Metodo para buscar configuracion por ID
	 * @param idConfigCsdr
	 * @return
	 */
	public SettlementDisciplineRegimeVO getSettlementDisciplineRegimeByID(Long idConfigCsdr);
	
	/**
	 * Metodo para modificar configuracion para CSDR
	 * @param settlementDisciplineRegime
	 */
	public void modificarSettlementDisciplineRegime(SettlementDisciplineRegimeVO settlementDisciplineRegime);
	
	/**
	 * Metodo para eliminar configuracion de Custodio para CSDR
	 * @param settlementDisciplineRegime
	 */
	public void eliminaSettlementDisciplineRegime(SettlementDisciplineRegime settlementDisciplineRegime);
	
	public List<SettlementDisciplineRegimeVO> getCustodiosPendSettlementDisciplineRegime();
	
	public Boolean findConfiguration(SettlementDisciplineRegime settlementDisciplineRegime);
	
	/**
	 * Almacena la configuracion de un Custodio para CSDR
	 * @param settlementDisciplineRegime
	 */
	public void saveConfiguracion(SettlementDisciplineRegime settlementDisciplineRegime);
	
	/**
	 * Metodo para autorizar la configuracion de un Custodio para CSDR
	 * @param settlementDisciplineRegime
	 */
	public void autorizaCustodioCSDR(SettlementDisciplineRegimeVO settlementDisciplineRegime);

	/**
	 * Metodo para obtener el listado de configuracion por institucion pendientes para csdr
	 * @return
	 */
	public List<SettlementDisciplineRegimeVO> getInstitucionesPendSettlementDisciplineRegime();

}
