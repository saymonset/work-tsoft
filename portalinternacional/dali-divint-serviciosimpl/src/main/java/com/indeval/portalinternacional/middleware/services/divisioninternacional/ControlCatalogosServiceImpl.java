package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import java.util.List;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.persistence.dao.common.BovedaDao;
import com.indeval.portaldali.persistence.dao.common.DivisaDao;
import com.indeval.portaldali.persistence.modelo.BancoCorresponsal;
import com.indeval.portaldali.persistence.modelo.Boveda;
import com.indeval.portaldali.persistence.modelo.Divisa;
import com.indeval.portalinternacional.middleware.servicios.modelo.EstatusDerecho;
import com.indeval.portalinternacional.middleware.servicios.modelo.TipoBeneficiario;
import com.indeval.portalinternacional.persistence.dao.DivisasInterDao;
import com.indeval.portalinternacional.persistence.dao.EstatusDerechoDao;
import com.indeval.portalinternacional.persistence.dao.TipoBeneficiarioDao;

public class ControlCatalogosServiceImpl implements ControlCatalogosService{
	
	private EstatusDerechoDao estatusDerechoDao;
	private DivisaDao divisaDao;
	private BovedaDao bovedaDao;
	private DivisasInterDao divisaInterDao;
	
	/** Dao para realizar las consultas de tipos de beneficiarios */
	private TipoBeneficiarioDao tipoBeneficiarioDao;

	public List<EstatusDerecho> getEstatusDerecho() throws BusinessException {		
		return estatusDerechoDao.findEstatusDerecho();
	}
	

	public Divisa[] getDivisas() throws BusinessException {		
		return divisaDao.findDivisas();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlCatalogosService#getDivisasById()
	 */
	public List<Divisa> getDivisasById() throws BusinessException {
		return divisaDao.findDivisasOrderById();
	}
	
	public Boveda[] getBovedas() throws BusinessException {
		return bovedaDao.findBovedas(null,null);
	}

	/*
	 * (non-Javadoc)
	 * @see com.indeval.portalinternacional.middleware.services.divisioninternacional.ControlCatalogosService#getTipoBeneficiario()
	 */
	@SuppressWarnings("unchecked")
	public List<TipoBeneficiario> getTipoBeneficiario() throws BusinessException {
		return tipoBeneficiarioDao.findAll(TipoBeneficiario.class.getName());
	}
	
	
	public void setEstatusDerechoDao(EstatusDerechoDao estatusDerechoDao) {
		this.estatusDerechoDao = estatusDerechoDao;
	}


	public void setDivisaDao(DivisaDao divisaDao) {
		this.divisaDao = divisaDao;
	}


	public void setBovedaDao(BovedaDao bovedaDao) {
		this.bovedaDao = bovedaDao;
	}


	/**
	 * Método para establecer el atributo tipoBeneficiarioDao
	 * @param tipoBeneficiarioDao El valor del atributo tipoBeneficiarioDao a establecer.
	 */
	public void setTipoBeneficiarioDao(TipoBeneficiarioDao tipoBeneficiarioDao) {
		this.tipoBeneficiarioDao = tipoBeneficiarioDao;
	}
	
	public List<BancoCorresponsal> buscaBancoCorresponsal(){
		
		return divisaDao.buscaBancoCorresponsal();
		
	}


	public DivisasInterDao getDivisaInterDao() {
		return divisaInterDao;
	}


	public void setDivisaInterDao(DivisasInterDao divisaInterDao) {
		this.divisaInterDao = divisaInterDao;
	}
	
	
	
	public Divisa[] obtenerDivisaPorInstitucion(Long idInstitucion){
		System.out.println("Iniciando obtencion de divisas por institucion");
		return divisaInterDao.findDivisasPorInstitucion(idInstitucion);
	}

}
