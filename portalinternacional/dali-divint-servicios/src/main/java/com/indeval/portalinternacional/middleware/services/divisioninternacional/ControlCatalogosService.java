package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import java.util.List;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.persistence.modelo.BancoCorresponsal;
import com.indeval.portaldali.persistence.modelo.Boveda;
import com.indeval.portaldali.persistence.modelo.Divisa;
import com.indeval.portalinternacional.middleware.servicios.modelo.EstatusDerecho;
import com.indeval.portalinternacional.middleware.servicios.modelo.TipoBeneficiario;

public interface ControlCatalogosService {

	List<EstatusDerecho> getEstatusDerecho() throws BusinessException;
	Divisa[] getDivisas()throws BusinessException;
	Boveda[] getBovedas()throws BusinessException;
	
	/**
	 * Obtiene el catalogo de tipos de beneficiarios.
	 * @return Lista con los diferentes tipos de beneficiarios.
	 * @throws BusinessException En caso de error.
	 */
	List<TipoBeneficiario> getTipoBeneficiario() throws BusinessException;
	
	/**
	 * Obtiene el catalogo de divisas ordenado por id.
	 * @return Lista con las divisas del catalogo.
	 * @throws BusinessException En caso de error.
	 */
	List<Divisa> getDivisasById() throws BusinessException;
	
	Divisa[] obtenerDivisaPorInstitucion(Long idInstitucion);
	
	List<BancoCorresponsal> buscaBancoCorresponsal();
}