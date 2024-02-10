package com.indeval.portalinternacional.persistence.dao;

import java.util.List;

import com.bursatec.persistence.dao.BaseDao;
import com.indeval.portalinternacional.middleware.servicios.modelo.HorarioBeneficiario;

public interface HorarioBeneficiarioDao  extends BaseDao {

	/**
	 * 
	 * @param idCuentaNombrada
	 * @return
	 */
	List<HorarioBeneficiario> findHorariosDerecho(Long idCuentaNombrada);
	
	/**
	 * 
	 * @param idCuentaNombrada
	 * @param idInstitucion
	 * @param folioInstitucion
	 * @return
	 */
	HorarioBeneficiario findHorarioInstitucion(Long idCuentaNombrada,Integer idInstitucion,String folioInstitucion);
	
	/**
	 * 
	 * @return
	 */
	List<HorarioBeneficiario> findHorariosDerecho();
	HorarioBeneficiario findHorario(HorarioBeneficiario horarioBeneficiario);
}
