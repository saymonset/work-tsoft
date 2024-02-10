package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.indeval.portalinternacional.middleware.servicios.dto.SettlementDisciplineRegimeVO;
import com.indeval.portalinternacional.middleware.servicios.modelo.SettlementDisciplineRegime;
import com.indeval.portalinternacional.persistence.dao.SettlementDisciplineRegimeDao;

public class SettlementDisciplineRegimeServiceImpl implements SettlementDisciplineRegimeService {
	
	private static final Logger log = LoggerFactory.getLogger(SettlementDisciplineRegimeServiceImpl.class);
	
	private SettlementDisciplineRegimeDao settlementDisciplineRegimeDao;

	/**
	 * @param settlementDisciplineRegimeDao the settlementDisciplineRegimeDao to set
	 */
	public void setSettlementDisciplineRegimeDao(SettlementDisciplineRegimeDao settlementDisciplineRegimeDao) {
		this.settlementDisciplineRegimeDao = settlementDisciplineRegimeDao;
	}

	@Override
	public List<SettlementDisciplineRegimeVO> getSettlementDisciplineRegimeForCustodio() {
		log.debug("SettlementDisciplineRegimeServiceImpl :: getSettlementDisciplineRegimeForCustodio");
		return settlementDisciplineRegimeDao.getSettlementDisciplineRegimeForCustodio();
	}
	
	@Override
	public List<SettlementDisciplineRegimeVO> getSettlementDisciplineRegimeForInstitucion() {
		log.debug("SettlementDisciplineRegimeServiceImpl :: getSettlementDisciplineRegimeForInstitucion");
		return settlementDisciplineRegimeDao.getSettlementDisciplineRegimeForInstitucion();
	}

	@Override
	public void modificarSettlementDisciplineRegime(SettlementDisciplineRegimeVO settlementDisciplineRegime) {
		log.debug("SettlementDisciplineRegimeServiceImpl :: modificarSettlementDisciplineRegime");
		settlementDisciplineRegimeDao.modificarSettlementDisciplineRegime(settlementDisciplineRegime);
	}
	
	@Override
	public void eliminaSettlementDisciplineRegime(SettlementDisciplineRegime settlementDisciplineRegime) {
		log.debug("SettlementDisciplineRegimeServiceImpl :: eliminaSettlementDisciplineRegime");
		settlementDisciplineRegimeDao.eliminaSettlementDisciplineRegime(settlementDisciplineRegime);
	}

	@Override
	public List<SettlementDisciplineRegimeVO> getCustodiosPendSettlementDisciplineRegime() {
		log.debug("SettlementDisciplineRegimeServiceImpl :: getCustodiosPendSettlementDisciplineRegime");
		return settlementDisciplineRegimeDao.getCustodiosPendSettlementDisciplineRegime();
	}
	
	@Override
	public List<SettlementDisciplineRegimeVO> getInstitucionesPendSettlementDisciplineRegime(){
		log.debug("SettlementDisciplineRegimeServiceImpl :: getInstitucionesPendSettlementDisciplineRegime");
		return settlementDisciplineRegimeDao.getInstitucionesPendSettlementDisciplineRegime();		
	}

	@Override
	public void saveConfiguracion(SettlementDisciplineRegime settlementDisciplineRegime) {
		log.debug("SettlementDisciplineRegimeServiceImpl :: saveCustodioSettlementDisciplineRegime");
		if(settlementDisciplineRegime.getIdInstitucion() != null){
			settlementDisciplineRegimeDao.saveConfiguracionForInstitucion(settlementDisciplineRegime);
		} else {
			settlementDisciplineRegimeDao.saveCustodioSettlementDisciplineRegime(settlementDisciplineRegime);
		}
	}
	
	@Override
	public void autorizaCustodioCSDR(SettlementDisciplineRegimeVO settlementDisciplineRegime) {
		log.debug("SettlementDisciplineRegimeServiceImpl :: autorizaCustodioCSDR");
		settlementDisciplineRegimeDao.autorizaCustodioCSDR(settlementDisciplineRegime);
	}
	
	public static SettlementDisciplineRegime transformSettlementDisciplineRegimeVoToModel(SettlementDisciplineRegimeVO settlementDisciplineRegime){
		log.debug("SettlementDisciplineRegimeServiceImpl :: transformSettlementDisciplineRegimeVoToModel");
		SettlementDisciplineRegime settleDiscRegModel = null;
		if(settlementDisciplineRegime != null){
			settleDiscRegModel = new SettlementDisciplineRegime();
			settleDiscRegModel.setIdCuentaBoveda(settlementDisciplineRegime.getIdCuentaBoveda());
			settleDiscRegModel.setDetalleCustodio(settlementDisciplineRegime.getDetalleCustodio());
			settleDiscRegModel.setHoldAndRelease((settlementDisciplineRegime.getHoldAndRelease() == 1L) ? Boolean.TRUE : Boolean.FALSE);
			settleDiscRegModel.setPartialSettlement((settlementDisciplineRegime.getPartialSettlement() == 1L) ? Boolean.TRUE : Boolean.FALSE);
			settleDiscRegModel.setBilateralCancellation((settlementDisciplineRegime.getBilateralCancellation() == 1L) ? Boolean.TRUE : Boolean.FALSE);
			settleDiscRegModel.setFechaAlta(new Date());
		}
		return settleDiscRegModel;
	}

	@Override
	public SettlementDisciplineRegimeVO getSettlementDisciplineRegimeByID(Long idConfigCsdr) {
		log.debug("SettlementDisciplineRegimeServiceImpl :: getSettlementDisciplineRegimeByID");
		return settlementDisciplineRegimeDao.getSettlementDisciplineRegimeByID(idConfigCsdr);
	}

	@Override
	public Boolean findConfiguration(SettlementDisciplineRegime settlementDisciplineRegime) {
		log.debug("SettlementDisciplineRegimeServiceImpl :: findSettlementDisciplineRegime");
		return settlementDisciplineRegimeDao.findConfiguration(settlementDisciplineRegime);
	}
}
