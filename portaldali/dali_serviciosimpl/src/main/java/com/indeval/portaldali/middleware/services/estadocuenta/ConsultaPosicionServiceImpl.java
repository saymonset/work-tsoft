/**
 * 2H Software
 * Sistema de Consulta de Estado de Cuenta - Indeval
 */
package com.indeval.portaldali.middleware.services.estadocuenta;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;

import com.indeval.portaldali.middleware.dto.CriterioOrdenamientoDTO;
import com.indeval.portaldali.middleware.dto.CuponDTO;
import com.indeval.portaldali.middleware.dto.PosicionDTO;
import com.indeval.portaldali.middleware.dto.TipoCuentaDTO;
import com.indeval.portaldali.middleware.dto.TipoNaturalezaDTO;
import com.indeval.portaldali.middleware.dto.util.EstadoPaginacionDTO;
import com.indeval.portaldali.middleware.dto.util.EstadoPaginacionExtended;
import com.indeval.portaldali.middleware.services.common.constants.TipoCuentaConstants;
import com.indeval.portaldali.middleware.services.common.constants.TipoCustodiaConstants;
import com.indeval.portaldali.modelo.to.estadocuenta.TotalesPosicionTO;
import com.indeval.portaldali.persistence.dao.common.EmisionDaliDAO;
import com.indeval.portaldali.persistence.dao.estadocuenta.PosicionDAO;
import com.indeval.portaldali.persistencia.posicion.Posicion;
import com.indeval.portaldali.persistencia.posicion.VPosicionControlada;
import com.indeval.portaldali.persistencia.posicion.VPosicionControladaH;
import com.indeval.portaldali.persistencia.posicion.VPosicionNombrada;
import com.indeval.portaldali.persistencia.posicion.VPosicionNombradaH;

/**
 * Implementa los métodos definidos por la interfaz
 * {@link ConsultaPosicionService} para realizar la consulta de posiciones ya
 * sea nombradas o controladas.
 * 
 * @author Emigdio Hernández
 * @version 1.0
 */
public class ConsultaPosicionServiceImpl implements ConsultaPosicionService {
	/**
	 * DAO para la consulta a la entidad de posicion ya sea nombrada y
	 * controlada
	 */
	private PosicionDAO posicionDAO = null;

	/** DAO para la consulta del catálogo de emisiones */
	private EmisionDaliDAO emisionDaliDAO = null;

	/*
	 * (non-Javadoc)
	 * @see com.indeval.portaldali.middleware.services.estadocuenta.ConsultaPosicionService#buscarPosicionNombradaPorId(java.lang.Long)
	 */
	public PosicionDTO buscarPosicionPorId(Posicion posicion) {
		PosicionDTO posicionDTO = null;
		if(posicion instanceof VPosicionNombrada) {			
			posicionDTO = posicionDAO.buscarPosicionNombradaPorId(posicion.getIdPosicion());
		}
		else if(posicion instanceof VPosicionNombradaH) {
			posicionDTO = posicionDAO.buscarPosicionNombradaHistoricoPorId(posicion.getFecha(), posicion.getIdPosicion());
		}
		else if(posicion instanceof VPosicionControlada) {
			posicionDTO = posicionDAO.buscarPosicionControladaPorId(posicion.getIdPosicion());
		}
		else if(posicion instanceof VPosicionControladaH) {
			posicionDTO = posicionDAO.buscarPosicionControladaHistoricoPorId(posicion.getFecha(), posicion.getIdPosicion());
		}
		return posicionDTO;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portaldali.middleware.services.estadocuenta.ConsultaPosicionService#consultarPosicion(com.indeval.portaldali.middleware.dto.PosicionDTO, com.indeval.portaldali.middleware.dto.util.EstadoPaginacionDTO, com.indeval.portaldali.middleware.dto.CriterioOrdenamientoDTO)
	 */
	public List<Posicion> consultarPosicion(PosicionDTO criterios, EstadoPaginacionDTO paginacion, CriterioOrdenamientoDTO orden) {
		List<Posicion> resultadoConsulta = null;
		boolean esHistorica = !DateUtils.isSameDay(criterios.getFechaFinal(), new Date());
		if (criterios.getCuenta().getTipoTenencia().getTipoCuenta().getId().equals(TipoCuentaDTO.CUENTA_CONTROLADA)) {
			resultadoConsulta = posicionDAO.buscarPosicionControlada(criterios, paginacion, orden, esHistorica);
		}
		else {
			resultadoConsulta = posicionDAO.buscarPosicionNombrada(criterios, paginacion, orden, esHistorica);
		}
		return resultadoConsulta;
	}
	
	
	/**
	 * Obtiene el campo posicionDAO
	 * 
	 * @return posicionDAO
	 */
	public PosicionDAO getPosicionDAO() {
		return posicionDAO;
	}

	/**
	 * Asigna el valor del campo posicionDAO
	 * 
	 * @param posicionDAO
	 *            el valor de posicionDAO a asignar
	 */
	public void setPosicionDAO(PosicionDAO posicionDAO) {
		this.posicionDAO = posicionDAO;
	}

	/**
	 * Obtiene el valor del atributo emisionDaliDAO
	 * 
	 * @return el valor del atributo emisionDaliDAO
	 */
	public EmisionDaliDAO getEmisionDaliDAO() {
		return emisionDaliDAO;
	}

	/**
	 * Establece el valor del atributo emisionDaliDAO
	 * 
	 * @param emisionDaliDAO
	 *            el valor del atributo emisionDaliDAO a establecer
	 */
	public void setEmisionDaliDAO(EmisionDaliDAO emisionDaliDAO) {
		this.emisionDaliDAO = emisionDaliDAO;
	}

	/*
	 * (non-Javadoc)
	 * @see com.indeval.portaldali.middleware.services.estadocuenta.ConsultaPosicionService#obtenerProyeccionDeConsultaDePosiciones(com.indeval.portaldali.middleware.dto.PosicionDTO, java.lang.Boolean)
	 */
	public int obtenerProyeccionDeConsultaDePosiciones(PosicionDTO criterio, Boolean debeDejarLog) {
		int resultado = 0;
		boolean esHistorica = !DateUtils.isSameDay(criterio.getFechaFinal(), new Date());
		if (TipoCuentaDTO.CUENTA_CONTROLADA.equals(criterio.getCuenta().getTipoTenencia().getTipoCuenta().getId())) {
			resultado = posicionDAO.obtenerProyeccionConsultaPosicionControlada(criterio, esHistorica);
		}
		else {
			resultado = posicionDAO.obtenerProyeccionConsultaPosicionNombrada(criterio, esHistorica);
		}
		return resultado;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.services.ConsultaPosicionService#obtenerIdentificadoresValidosParaBoveda(com.indeval.estadocuenta.core.application.dto.PosicionDTO)
	 */
	public List<Long> obtenerIdentificadoresValidosParaBoveda(PosicionDTO criterio) {
		List<Long> resultado = new ArrayList<Long>();

		if (DateUtils.isSameDay(criterio.getFechaFinal(), new Date())) {
			resultado = posicionDAO.obtenerIdentificadoresValidosParaBoveda(criterio);
		} else {
			resultado = posicionDAO.obtenerIdentificadoresValidosParaBovedaHistoricas(criterio);
		}

		return resultado;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.services.ConsultaPosicionService#obtenerIdentificadoresValidosParaEmision(com.indeval.estadocuenta.core.application.dto.PosicionDTO)
	 */
	public List<Long> obtenerIdentificadoresValidosParaEmision(PosicionDTO criterio) {
		List<Long> resultado = new ArrayList<Long>();

		if (DateUtils.isSameDay(criterio.getFechaFinal(), new Date())) {
			resultado = posicionDAO.obtenerIdentificadoresValidosParaEmision(criterio);
		} else {
			resultado = posicionDAO.obtenerIdentificadoresValidosParaEmisionHistoricas(criterio);
		}
		return resultado;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.services.ConsultaPosicionService#obtenerIdentificadoresValidosParaCuenta(com.indeval.estadocuenta.core.application.dto.PosicionDTO)
	 */
	public List<Long> obtenerIdentificadoresValidosParaCuenta(PosicionDTO criterio) {
		List<Long> resultado = new ArrayList<Long>();
		if (DateUtils.isSameDay(criterio.getFechaFinal(), new Date())) {
			resultado = posicionDAO.obtenerIdentificadoresValidosParaCuenta(criterio);
		} else {
			resultado = posicionDAO.obtenerIdentificadoresValidosParaCuentaHistoricas(criterio);
		}
		return resultado;
	}

	/*
	 * (non-Javadoc)
	 * @see com.indeval.portaldali.middleware.services.estadocuenta.ConsultaPosicionService#obtenerTotalesConsultaPosicion(com.indeval.portaldali.middleware.dto.PosicionDTO)
	 */
	public TotalesPosicionTO obtenerTotalesConsultaPosicion(PosicionDTO criterio) {
		TotalesPosicionTO totales = new TotalesPosicionTO();
		final boolean esHistorica = !DateUtils.isSameDay(criterio.getFechaFinal(), new Date());
		if (TipoCuentaDTO.CUENTA_CONTROLADA.equals(criterio.getCuenta().getTipoTenencia().getTipoCuenta().getId())) {
			totales = posicionDAO.obtenerTotalesPosicionControlada(criterio, esHistorica);
		}
		else {
			totales = posicionDAO.obtenerTotalesPosicionNombrada(criterio, esHistorica);
		}
		return totales;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.middleware.services.estadocuenta.ConsultaPosicionService#consultarPosicionesYEmisionesVigentes(com.indeval.portaldali.middleware.dto.PosicionDTO,
	 *      com.indeval.portaldali.middleware.dto.util.EstadoPaginacionExtended,
	 *      java.lang.Long[])
	 */
	public List<PosicionDTO> consultarPosicionesYEmisionesVigentes(PosicionDTO criterios, EstadoPaginacionExtended paginacion, Long[] identificadoresMercado) {
		if (criterios.getCuenta().getTipoTenencia().getTipoCuenta().getId().equals(TipoCuentaDTO.CUENTA_CONTROLADA)) {
			return null;
		} else {

			List<Object[]> resultados = posicionDAO.buscarEmisionesYPosiciones(criterios, paginacion, identificadoresMercado);
			List<PosicionDTO> posiciones = new ArrayList<PosicionDTO>();
			
			for(Object[] resultado : resultados) {
				CuponDTO cupon = (CuponDTO)resultado[0];
				PosicionDTO posicion = (PosicionDTO)resultado[1];
				if(posicion != null) {

					if(!posicion.getCuenta().getTipoTenencia().getTipoCustodia().equals(TipoCustodiaConstants.VALORES) || !posicion.getCuenta().getTipoTenencia().getTipoCuenta().getId().equals(TipoCuentaConstants.TIPO_NOMBRADA) ||
							!posicion.getCuenta().getTipoTenencia().getTipoNaturaleza().getId().equals(TipoNaturalezaDTO.PASIVO)	) {
						continue;
					}
					
					posiciones.add(posicion);
				} else {
					if(cupon != null) {
						PosicionDTO posicionTemp = new PosicionDTO();
						posicionTemp.setCupon(cupon);
						posicionTemp.setEmision(cupon.getEmision());
						posicionTemp.getEmision().setCupon(cupon.getClaveCupon());
						
						posiciones.add(posicionTemp);
					}
				}
			}
			return posiciones;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.middleware.services.estadocuenta.ConsultaPosicionService#consultarPosicionesPorMercado(com.indeval.portaldali.middleware.dto.PosicionDTO,
	 *      com.indeval.portaldali.middleware.dto.util.EstadoPaginacionDTO,
	 *      com.indeval.portaldali.middleware.dto.CriterioOrdenamientoDTO,
	 *      java.lang.Long[])
	 */
	public List<PosicionDTO> consultarPosicionesPorMercado(PosicionDTO criterios, EstadoPaginacionDTO paginacion, CriterioOrdenamientoDTO orden,
			Long[] identificadoresMercado) {
		if (criterios.getCuenta().getTipoTenencia().getTipoCuenta().getId().equals(TipoCuentaDTO.CUENTA_CONTROLADA)) {
			return null;
		} else {
			return posicionDAO.consultarPosicionesNombradasPorMercado(criterios, paginacion, orden, identificadoresMercado);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.portaldali.middleware.services.estadocuenta.ConsultaPosicionService#obtenerProyeccionDeConsultaPosicionesYEmisionesVigentes(com.indeval.portaldali.middleware.dto.PosicionDTO,
	 *      java.lang.Long[])
	 */
	public int obtenerProyeccionDeConsultaPosicionesYEmisionesVigentes(PosicionDTO criterio, Long[] identificadoresMercado) {
		if (criterio.getCuenta().getTipoTenencia().getTipoCuenta().getId().equals(TipoCuentaDTO.CUENTA_CONTROLADA)) {
			return 0;
		} else {

			return posicionDAO.contarEmisionesYPosiciones(criterio, identificadoresMercado).intValue();
		}
	}

}
