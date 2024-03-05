/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * 
 * 27/02/2008
 */
package com.indeval.portaldali.middleware.services.common;

import java.util.List;

import com.indeval.portaldali.middleware.dto.MercadoDTO;
import com.indeval.portaldali.middleware.services.common.ConsultaMercadoService;
import com.indeval.portaldali.persistence.dao.common.MercadoDaliDAO;

/**
 * Implementación del servicio de negocio para las consultas al catálogo de mercados.
 * 
 * @author Emigdio Hernández
 * @version 1.0
 */
public class ConsultaMercadoServiceImpl implements ConsultaMercadoService {

	/**
	 * DAO utilizado para consultar el catálogo de mercados
	 */
	private MercadoDaliDAO mercadoDaliDAO;
	
	/* (non-Javadoc)
	 * @see com.indeval.estadocuenta.core.application.services.ConsultaMercadoService#buscarMercados()
	 */
	public List<MercadoDTO> buscarMercados() {
		
		return mercadoDaliDAO.buscarMercados();
	}

	/**
	 * método para obtener el atributo mercadoDaliDAO
	 * 
	 * @return the mercadoDaliDAO
	 */
	public MercadoDaliDAO getMercadoDAO() {
		return mercadoDaliDAO;
	}

	/**
	 * método para establecer el atributo mercadoDaliDAO
	 * 
	 * @param mercadoDaliDAO the mercadoDaliDAO to set
	 */
	public void setMercadoDAO(MercadoDaliDAO mercadoDaliDAO) {
		this.mercadoDaliDAO = mercadoDaliDAO;
	}
	/*
	 * (non-Javadoc)
	 * @see com.indeval.estadocuenta.core.application.services.ConsultaMercadoService#buscarMercadoPorId(long)
	 */
	public MercadoDTO buscarMercadoPorId(long idMercado) {
		return mercadoDaliDAO.buscarMercadoPorId(idMercado);
	}

}
