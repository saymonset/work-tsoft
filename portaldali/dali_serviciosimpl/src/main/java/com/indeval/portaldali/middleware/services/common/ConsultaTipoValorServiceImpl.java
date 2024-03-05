/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * 
 * 03/03/2008
 */
package com.indeval.portaldali.middleware.services.common;

import java.util.List;

import com.indeval.portaldali.middleware.dto.MercadoDTO;
import com.indeval.portaldali.middleware.dto.TipoValorDTO;
import com.indeval.portaldali.middleware.services.common.ConsultaTipoValorService;
import com.indeval.portaldali.persistence.dao.common.TipoValorDAO;

/**
 * Implementación del servicio de negocio encargado de realizar las operaciones 
 * de consulta a la tabla de instrumentos.
 * 
 * @author Emigdio Hernández
 * @version 1.0
 */
public class ConsultaTipoValorServiceImpl implements ConsultaTipoValorService {

	/**
	 * DAO utilizado para consultar la tabla de instrumentos o tipos de valores.
	 */
	private TipoValorDAO tipoValorDAO;
	
	/* (non-Javadoc)
	 * @see com.indeval.estadocuenta.core.application.services.ConsultaTipoValorService#buscarTiposDeValoresPorMercado(com.indeval.estadocuenta.core.application.dto.MercadoDTO)
	 */
	public List<TipoValorDTO> buscarTiposDeValoresPorMercado(MercadoDTO mercado) {
		
		return tipoValorDAO.buscarTiposDeValoresPorMercado(mercado);
	}

	/**
	 * método para obtener el atributo tipoValorDAO
	 * 
	 * @return the tipoValorDAO
	 */
	public TipoValorDAO getTipoValorDAO() {
		return tipoValorDAO;
	}

	/**
	 * método para establecer el atributo tipoValorDAO
	 * 
	 * @param tipoValorDAO the tipoValorDAO to set
	 */
	public void setTipoValorDAO(TipoValorDAO tipoValorDAO) {
		this.tipoValorDAO = tipoValorDAO;
	}
	/*
	 * (non-Javadoc)
	 * @see com.indeval.estadocuenta.core.application.services.ConsultaTipoValorService#buscarTipoDeValorPorId(long)
	 */
	public TipoValorDTO buscarTipoDeValorPorId(long idTipoValor) {
		return tipoValorDAO.buscarTipoDeValorPorId(idTipoValor);
	}
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portaldali.middleware.services.common.ConsultaTipoValorService#buscarTiposDeValoresPorMercadoYPrefijo(com.indeval.portaldali.middleware.dto.MercadoDTO, java.lang.String)
	 */
	public List<TipoValorDTO> buscarTiposDeValoresPorMercadoYPrefijo(
			MercadoDTO mercado, String prefijo) {
		return tipoValorDAO.buscarTiposDeValoresPorMercadoYPrefijo(mercado, prefijo);
	}
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portaldali.middleware.services.common.ConsultaTipoValorService#buscarTipoDeValorPorClave(java.lang.String)
	 */
	public TipoValorDTO buscarTipoDeValorPorClave(String clave) {
		return tipoValorDAO.buscarTipoDeValorPorClave(clave);
	}
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portaldali.middleware.services.common.ConsultaTipoValorService#buscarTiposDeValoresPorMercados(java.lang.Long[], java.lang.String)
	 */
	public List<TipoValorDTO> buscarTiposDeValoresPorMercados(Long[] idsMercados, String prefijo) {
		return tipoValorDAO.buscarTiposDeValoresPorMercados(idsMercados, prefijo);
	}

}
