/**
 * 2H Software - Bursatec
 * Sistema de Consulta de Estados de Cuenta
 *
 */
package com.indeval.portaldali.middleware.services.common;


import java.math.BigInteger;
import java.util.List;

import com.indeval.portaldali.middleware.dto.BovedaDTO;
import com.indeval.portaldali.middleware.dto.DivisaDTO;
import com.indeval.portaldali.middleware.dto.TipoInstruccionDTO;
import com.indeval.portaldali.middleware.dto.util.EstadoPaginacionDTO;
import com.indeval.portaldali.persistence.dao.common.BovedaDaliDAO;

/**
 * @author Emigdio Hernandez
 * @version 1.0
 * @created 06-dic-2007 06:11:59 p.m.
 */
public class ConsultaBovedaServiceImpl implements ConsultaBovedaService {
	/**
	 * DAO utilizado para consultar bovedas de valor y de efectivo
	 */
	private BovedaDaliDAO bovedaDaliDAO;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.indeval.estadocuenta.core.application.services.ConsultaBovedaService#consultarBovedasPorTipoDeCustodia(com.indeval.estadocuenta.core.application.dto.BovedaDTO)
	 */
	public List<BovedaDTO> consultarBovedasPorTipoDeCustodia(
			BovedaDTO criterio, EstadoPaginacionDTO estadoPaginacion) {

		return bovedaDaliDAO.buscarBovedasPorTipoCustodia(criterio,
				estadoPaginacion);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.indeval.estadocuenta.core.application.services.ConsultaBovedaService#consultarBovedaPorId(long)
	 */
	public BovedaDTO consultarBovedaPorId(long idBoveda) {

		return bovedaDaliDAO.consultarBovedaPorId(idBoveda);
	}

	/**
	 * Obtiene el campo bovedaDaliDAO
	 *
	 * @return bovedaDaliDAO
	 */
	public BovedaDaliDAO getBovedaDAO() {
		return bovedaDaliDAO;
	}

	/**
	 * Asigna el valor del campo bovedaDaliDAO
	 *
	 * @param bovedaDaliDAO
	 *            el valor de bovedaDaliDAO a asignar
	 */
	public void setBovedaDAO(BovedaDaliDAO bovedaDaliDAO) {
		this.bovedaDaliDAO = bovedaDaliDAO;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.indeval.estadocuenta.core.application.services.ConsultaBovedaService#obtenerProyeccionDeConsultaBovedasPorTipoCustodia(com.indeval.estadocuenta.core.application.dto.BovedaDTO)
	 */
	public int obtenerProyeccionDeConsultaBovedasPorTipoCustodia(
			BovedaDTO criterio) {

		return bovedaDaliDAO
				.obtenerProyeccionDeBusquedaDeBovedasPorTipoCustodia(criterio);
	}

	/*
	 * (non-Javadoc)
	 * @see com.indeval.estadocuenta.core.application.services.ConsultaBovedaService#buscarBovedasPorTipoCustodia(com.indeval.estadocuenta.core.application.dto.BovedaDTO, java.util.Set, com.indeval.estadocuenta.core.application.dto.EstadoPaginacionDTO)
	 */
	public List<BovedaDTO> buscarBovedasPorTipoCustodia(BovedaDTO boveda,
			List<Long> idsBoveda, EstadoPaginacionDTO estadoPaginacion) {
		return bovedaDaliDAO.buscarBovedasPorTipoCustodia(boveda, idsBoveda, estadoPaginacion);
	}

	/*
	 * (non-Javadoc)
	 * @see com.indeval.estadocuenta.core.application.services.ConsultaBovedaService#obtenerProyeccionDeBusquedaDeBovedasPorTipoCustodia(com.indeval.estadocuenta.core.application.dto.BovedaDTO, java.util.Set)
	 */
	public int obtenerProyeccionDeBusquedaDeBovedasPorTipoCustodia(
			BovedaDTO boveda, List<Long> idsBoveda) {
		return bovedaDaliDAO.obtenerProyeccionDeBusquedaDeBovedasPorTipoCustodia(boveda, idsBoveda);
	}

	/*
	 * /(non-Javadoc)
	 * @see com.indeval.portaldali.middleware.services.common.ConsultaBovedaService#obtenerBovedasPorDivisa(com.indeval.portaldali.middleware.dto.DivisaDTO)
	 */
	public List<BigInteger> obtenerBovedasPorDivisa(final DivisaDTO divisaDTO){
		return bovedaDaliDAO.obtenerBovedasPorDivisa(divisaDTO);
	}

	/*
	 * (non-Javadoc)
	 * @see com.indeval.portaldali.middleware.services.common.ConsultaBovedaService#obtenerBovedasPorTipoInstruccion(com.indeval.portaldali.middleware.dto.TipoInstruccionDTO)
	 */
	public List<BigInteger> obtenerBovedasPorTipoInstruccion(
			TipoInstruccionDTO tipoInstruccionDTO) {
		return bovedaDaliDAO.obtenerBovedasPorTipoInstruccion(tipoInstruccionDTO);
	}

}
