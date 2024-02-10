package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.indeval.portalinternacional.middleware.servicios.dto.DivisaDTO;
import com.indeval.portalinternacional.middleware.servicios.dto.EstadoPaginacionDTO;
import com.indeval.portalinternacional.middleware.servicios.modelo.CuentaCorresponsal;
import com.indeval.portalinternacional.persistence.dao.CuentaCorresponsalDao;
import com.indeval.portalinternacional.persistence.dao.DivisaDaliDao;

public class ConsultaDivisaServiceImpl implements ConsultaDivisaService{
	
	/** DAO para las operaciones de consulta de las divisas */
	private DivisaDaliDao divisaDaliDAO;
	
	/** DAO para obtener las divisas de acuerdo a la isntitucion */
	private CuentaCorresponsalDao cuentaCorresponsalDao;

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
	public DivisaDaliDao getDivisaDAO() {
		return divisaDaliDAO;
	}

	/**
	 * Establece la propiedad divisaDaliDAO
	 *
	 * @param divisaDaliDAO
	 *            el campo divisaDaliDAO a establecer
	 */
	public void setDivisaDAO(DivisaDaliDao divisaDaliDAO) {
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

	@Override
	public List<DivisaDTO> findIdDivisasByIdInstitucion(Long idInstitucion) {
		final List<DivisaDTO> divisaDTOs = new ArrayList<DivisaDTO>();
		final List<Long> idDivisas = cuentaCorresponsalDao.findIdDivisasByIdInstitucion(idInstitucion);
		for (Long idDivisa : idDivisas) {
			final DivisaDTO divisa = consultarDivisaPorId(idDivisa);
			divisaDTOs.add(divisa);
		}
		return divisaDTOs;
	}
	
	@Override
	public List<CuentaCorresponsal> findCuentaCorresponsalByIdDivisaAndIdInstitucion(Long idDivisa,
			Long idInstitucion) {
		return cuentaCorresponsalDao.findCuentaCorresponsalByIdDivisaAndIdInstitucion(idDivisa, idInstitucion);
	}

	
	public void setCuentaCorresponsalDao(CuentaCorresponsalDao cuentaCorresponsalDao) {
		this.cuentaCorresponsalDao = cuentaCorresponsalDao;
	}
	
	public CuentaCorresponsalDao getCuentaCorresponsalDao() {
		return cuentaCorresponsalDao;
	}
	
	public void setDivisaDaliDAO(DivisaDaliDao divisaDaliDAO) {
		this.divisaDaliDAO = divisaDaliDAO;
	}
	
	public DivisaDaliDao getDivisaDaliDAO() {
		return divisaDaliDAO;
	}

}
