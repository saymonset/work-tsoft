package com.indeval.portalinternacional.persistence.dao;

import java.util.List;

import com.bursatec.persistence.dao.BaseDao;
import com.indeval.portaldali.persistence.modelo.CuentaNombrada;
import com.indeval.portaldali.persistence.modelo.Institucion;
import com.indeval.portaldali.persistence.modelo.SaldoNombrada;
import com.indeval.portalinternacional.middleware.servicios.modelo.RBovedaValEf;

public interface NombradaSaldoDao extends BaseDao {
	
	RBovedaValEf getEfectivoByIdValores (Long idValores);
	
	CuentaNombrada getCuentaNombradaById (Long idCuentaNombrada);
	
	CuentaNombrada getCuentaNombradaByInstitucionAndCuenta(Long idInstitucion, String cuenta);

	List<SaldoNombrada> getSaldoNombradaByCuentaNombradaAndBovedaAndDivisa(Long idCuentaNombrada, Long idBoveda,
			Long idDivisa);

}