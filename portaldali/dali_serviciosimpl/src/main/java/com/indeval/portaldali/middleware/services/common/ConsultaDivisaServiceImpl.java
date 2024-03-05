/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * ConsultaDivisaServiceImpl.java
 * 06/03/2008
 */
package com.indeval.portaldali.middleware.services.common;


import java.math.BigInteger;
import java.util.List;


import com.indeval.portaldali.middleware.dto.DivisaDTO;
import com.indeval.portaldali.middleware.dto.util.EstadoPaginacionDTO;
import com.indeval.portaldali.middleware.services.common.ConsultaDivisaService;
import com.indeval.portaldali.persistence.dao.common.DivisaDaliDAO;

/**
 * Implementación de la interface ConsultaDivisaService
 *
 * @author Pablo Julián Balderas Méndez
 *
 */
public class ConsultaDivisaServiceImpl implements ConsultaDivisaService {

	/** DAO para las operaciones de consulta de las divisas */
	private DivisaDaliDAO divisaDaliDAO;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.indeval.estadocuenta.core.application.services.ConsultaDivisaService#consultarDivisaPorId(long)
	 */
	public DivisaDTO consultarDivisaPorId(long idDivisa) {
		return divisaDaliDAO.consultarDivisaPorId(idDivisa);
	}



	/*
	 * (non-Javadoc)
	 *
	 * @see com.indeval.estadocuenta.core.application.services.ConsultaDivisaService#consultarDivisas()
	 */
	@SuppressWarnings("unchecked")
	public List<DivisaDTO> consultarDivisas(EstadoPaginacionDTO estadoPaginacion) {
		return divisaDaliDAO.buscarDivisas(estadoPaginacion);
	}

	/*
	 * (non-Javadoc)
	 * @see com.indeval.portaldali.middleware.services.common.ConsultaDivisaService#consultarDivisasPorTipoInstruccion(com.indeval.portaldali.middleware.dto.util.EstadoPaginacionDTO, java.math.BigInteger)
	 */
	public List<DivisaDTO> consultarDivisasPorTipoInstruccion(EstadoPaginacionDTO estadoPaginacion, BigInteger idTipoInstruccion){
		return divisaDaliDAO.buscarDivisasPorTipoInstruccion(estadoPaginacion, idTipoInstruccion);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.indeval.estadocuenta.core.application.services.ConsultaDivisaService#obtenerProyeccionDeEmisiones()
	 */
	public int obtenerProyeccionDeDivisas() {
		return divisaDaliDAO.obtenerProyeccionDeDivisas();
	}

	/**
	 * Obtiene el atributo divisaDaliDAO
	 *
	 * @return El atrubuto divisaDaliDAO
	 */
	public DivisaDaliDAO getDivisaDAO() {
		return divisaDaliDAO;
	}

	/**
	 * Establece la propiedad divisaDaliDAO
	 *
	 * @param divisaDaliDAO
	 *            el campo divisaDaliDAO a establecer
	 */
	public void setDivisaDAO(DivisaDaliDAO divisaDaliDAO) {
		this.divisaDaliDAO = divisaDaliDAO;
	}

	/*
	 * (non-Javadoc)
	 * @see com.indeval.estadocuenta.core.application.services.ConsultaDivisaService#buscarDivisas(java.util.List, com.indeval.estadocuenta.core.application.dto.EstadoPaginacionDTO)
	 */
	public List<DivisaDTO> buscarDivisas(List<Long> idsDivisa,
			EstadoPaginacionDTO estadoPaginacion) {
		return divisaDaliDAO.buscarDivisas(idsDivisa, estadoPaginacion);
	}

	/*
	 * (non-Javadoc)
	 * @see com.indeval.estadocuenta.core.application.services.ConsultaDivisaService#obtenerProyeccionDeDivisas(java.util.List)
	 */
	public int obtenerProyeccionDeDivisas(List<Long> idsDivisa) {
		return divisaDaliDAO.obtenerProyeccionDeDivisas(idsDivisa);
	}


}
