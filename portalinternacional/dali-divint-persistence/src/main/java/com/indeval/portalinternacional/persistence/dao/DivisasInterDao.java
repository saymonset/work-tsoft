package com.indeval.portalinternacional.persistence.dao;

import java.util.List;

import com.bursatec.persistence.dao.BaseDao;
import com.indeval.portaldali.persistence.modelo.Divisa;
import com.indeval.portalinternacional.middleware.servicios.modelo.Bovedas;
import com.indeval.portaldali.persistence.modelo.BancoCorresponsal;

public interface DivisasInterDao extends BaseDao {

	Divisa[] findDivisasPorInstitucion(Long idInstitucion);

	Divisa[] buscaDivisas();

}
