package com.indeval.portalinternacional.persistence.dao.beneficiarios;

import java.sql.Date;
import java.util.List;

import com.bursatec.persistence.dao.BaseDao;
import com.indeval.portalinternacional.middleware.servicios.modelo.Beneficiario;
import com.indeval.portalinternacional.middleware.servicios.vo.ConsultaBeneficiariosParam;

public interface ConsultasExportacionBeneficiariosDao extends BaseDao {

	List<Beneficiario> findBeneficiariosByFormato(final ConsultaBeneficiariosParam params);
	
	List<Beneficiario> obtenerRegistrosW8BEN(Date fechaIni, Date fechaFin);
	
	List<Beneficiario> obtenerRegistrosW8BENE(Date fechaIni, Date fechaFin);

    List<Beneficiario> obtenerRegistrosW8IMY(Date fechaIni, Date fechaFin);
    
    List<Beneficiario> obtenerRegistrosW9(Date fechaIni, Date fechaFin);
	
}
