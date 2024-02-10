// Cambio Multidivisas
package com.indeval.portalinternacional.middleware.services.divisioninternacional;

import com.indeval.portaldali.middleware.servicios.modelo.BusinessException;
import com.indeval.portaldali.persistence.dao.util.DateUtilsDao;
import com.indeval.portaldali.persistence.modelo.SaldoNombrada;
import com.indeval.portalinternacional.middleware.services.util.DateUtil;
import com.indeval.portalinternacional.middleware.servicios.dto.CriterioOrdenamientoDTO;
import com.indeval.portalinternacional.middleware.servicios.dto.EstadoPaginacionDTO;
import com.indeval.portalinternacional.middleware.servicios.dto.SaldoEfectivoDTO;
import com.indeval.portalinternacional.middleware.servicios.dto.TipoCuentaDTO;
import com.indeval.portalinternacional.persistence.dao.SaldoDAO;
import com.indeval.portalinternacional.persistence.dao.SaldoNombradaDaliDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public class ConsultaSaldosEfectivoServiceImpl implements ConsultaSaldosEfectivoService {

	private static final Logger LOG = LoggerFactory.getLogger(ConsultaSaldosEfectivoServiceImpl.class);
	
	/**
	 * DAO para la consulta de saldos de efectivo de cuentas nombras y contraladas
	 */
	private SaldoDAO saldoDAO = null;

	private SaldoNombradaDaliDao saldoNombradaDao;

	/** Bean de acceso a DateUtilsDao */
	private DateUtilsDao dateUtilsDao;

	/**
	 * @see com.indeval.estadocuenta.core.application.services.ConsultaSaldosEfectivoService#consultarSaldosEfectivo(com.indeval.estadocuenta.core.application.dto.CuentaEfectivoDTO)
	 */
	public List<SaldoEfectivoDTO> consultarSaldosEfectivo(SaldoEfectivoDTO criterios, EstadoPaginacionDTO paginacion,
			CriterioOrdenamientoDTO orden) {
		if (TipoCuentaDTO.CUENTA_CONTROLADA.equals(criterios.getCuenta().getTipoCuenta().getId())) {
			if (DateUtil.comparaFechasDias(dateUtilsDao.getDateFechaDB(), criterios.getFecha()) == 0) {
				return saldoDAO.consultarSaldosControladas(criterios, paginacion, orden);
			} else {
				return saldoDAO.consultarSaldosControladasHistorico(criterios, paginacion, orden);
			}
		} else {
			if (DateUtil.comparaFechasDias(dateUtilsDao.getDateFechaDB(), criterios.getFecha()) == 0) {
				return saldoDAO.consultarSaldosNombradas(criterios, paginacion, orden);
			} else {
				return saldoDAO.consultarSaldosNombradasHistorico(criterios, paginacion, orden);
			}
		}
	}

	/**
	 * @see com.indeval.estadocuenta.core.application.services.ConsultaSaldosEfectivoService#consultarSaldosEfectivo(com.indeval.estadocuenta.core.application.dto.CuentaEfectivoDTO)
	 */
	public List<SaldoEfectivoDTO> consultarSaldosEfectivoBitacora(SaldoEfectivoDTO criterios,
			EstadoPaginacionDTO paginacion, CriterioOrdenamientoDTO orden) {
		if (TipoCuentaDTO.CUENTA_CONTROLADA.equals(criterios.getCuenta().getTipoCuenta().getId())) {
			if (DateUtil.comparaFechasDias(dateUtilsDao.getDateFechaDB(), criterios.getFecha()) == 0) {
				return saldoDAO.consultarSaldosControladas(criterios, paginacion, orden);
			} else {
				return saldoDAO.consultarSaldosControladasHistorico(criterios, paginacion, orden);
			}
		} else {
			if (DateUtil.comparaFechasDias(dateUtilsDao.getDateFechaDB(), criterios.getFecha()) == 0) {
				return saldoDAO.consultarSaldosNombradas(criterios, paginacion, orden);
			} else {
				return saldoDAO.consultarSaldosNombradasHistorico(criterios, paginacion, orden);
			}
		}
	}

	/**
	 * @return the saldoDAO
	 */
	public SaldoDAO getSaldoDAO() {
		return saldoDAO;
	}

	/**
	 * @param saldoDAO the saldoDAO to set
	 */
	public void setSaldoDAO(SaldoDAO saldoDAO) {
		this.saldoDAO = saldoDAO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.services.
	 * ConsultaSaldosEfectivoService#obtenerProyeccionConsultaSaldosEfectivo(com.
	 * indeval.estadocuenta.core.application.dto.SaldoEfectivoDTO)
	 */
	public int obtenerProyeccionConsultaSaldosEfectivo(SaldoEfectivoDTO criterios, Boolean debeDejarLog) {
		if (TipoCuentaDTO.CUENTA_CONTROLADA.equals(criterios.getCuenta().getTipoCuenta().getId())) {
			if (DateUtil.comparaFechasDias(dateUtilsDao.getDateFechaDB(), criterios.getFecha()) == 0) {
				return saldoDAO.obtenerProyeccionConsultaSaldosControladas(criterios);
			} else {
				return saldoDAO.obtenerProyeccionConsultaSaldosControladasHistorico(criterios);
			}
		} else {
			if (DateUtil.comparaFechasDias(dateUtilsDao.getDateFechaDB(), criterios.getFecha()) == 0) {
				return saldoDAO.obtenerProyeccionConsultaSaldosNombradas(criterios);
			} else {
				return saldoDAO.obtenerProyeccionConsultaSaldosNombradasHistorico(criterios);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.services.
	 * ConsultaSaldosEfectivoService#obtenerIdentificadoresValidosParaBoveda(com.
	 * indeval.estadocuenta.core.application.dto.SaldoEfectivoDTO)
	 */
	public List<Long> obtenerIdentificadoresValidosParaBoveda(SaldoEfectivoDTO criterio) {
		if (DateUtil.comparaFechasDias(dateUtilsDao.getDateFechaDB(), criterio.getFecha()) == 0) {
			return saldoDAO.obtenerIdentificadoresValidosParaBoveda(criterio);
		} else {
			return saldoDAO.obtenerIdentificadoresValidosParaBovedaHistorico(criterio);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.services.
	 * ConsultaSaldosEfectivoService#obtenerIdentificadoresValidosParaDivisa(com.
	 * indeval.estadocuenta.core.application.dto.SaldoEfectivoDTO)
	 */
	public List<Long> obtenerIdentificadoresValidosParaDivisa(SaldoEfectivoDTO criterio) {
		if (DateUtil.comparaFechasDias(dateUtilsDao.getDateFechaDB(), criterio.getFecha()) == 0) {
			return saldoDAO.obtenerIdentificadoresValidosParaDivisa(criterio);
		} else {
			return saldoDAO.obtenerIdentificadoresValidosParaDivisaHistorico(criterio);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indeval.estadocuenta.core.application.services.
	 * ConsultaSaldosEfectivoService#obtenerIdentificadoresValidosParaCuenta(com.
	 * indeval.estadocuenta.core.application.dto.SaldoEfectivoDTO)
	 */
	public List<Long> obtenerIdentificadoresValidosParaCuenta(SaldoEfectivoDTO criterio) {
		if (DateUtil.comparaFechasDias(dateUtilsDao.getDateFechaDB(), criterio.getFecha()) == 0) {
			return saldoDAO.obtenerIdentificadoresValidosParaCuenta(criterio);
		} else {
			return saldoDAO.obtenerIdentificadoresValidosParaCuentaHistorico(criterio);
		}
	}

	public Map<String, Number> obtenerTotalesConsultaSaldo(SaldoEfectivoDTO criterio) {
		if (TipoCuentaDTO.CUENTA_CONTROLADA.equals(criterio.getCuenta().getTipoCuenta().getId())) {
			if (DateUtil.comparaFechasDias(dateUtilsDao.getDateFechaDB(), criterio.getFecha()) == 0) {
				return saldoDAO.obtenerTotalesConsultaSaldoControlada(criterio);
			} else {
				return saldoDAO.obtenerTotalesConsultaSaldoControladaHistorico(criterio);
			}
		} else {
			if (DateUtil.comparaFechasDias(dateUtilsDao.getDateFechaDB(), criterio.getFecha()) == 0) {
				return saldoDAO.obtenerTotalesConsultaSaldoNombrada(criterio);
			} else {
				return saldoDAO.obtenerTotalesConsultaSaldoNombradaHistorico(criterio);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public BigDecimal getSaldoEfectivo(String id, String folio, BigInteger idBoveda, BigInteger idDivisa)
			throws BusinessException {
		LOG.info("Datos enviados: [" + id + folio + "-" + idBoveda + "-" + idDivisa + "]");
		List<SaldoNombrada> saldo = saldoNombradaDao.getSaldoNombradaDivisa(id, folio, idBoveda, idDivisa);
		if (saldo != null && saldo.size() == 1) {
			return saldo.get(0).getSaldoDisponible();
		}
		return new BigDecimal("0.0");
	}

	/**
	 * @param saldoNombradaDao the saldoNombradaDao to set
	 */
	public void setSaldoNombradaDao(SaldoNombradaDaliDao saldoNombradaDao) {
		this.saldoNombradaDao = saldoNombradaDao;
	}

	/**
	 * @return the saldoNombradaDao
	 */
	public SaldoNombradaDaliDao getSaldoNombradaDao() {
		return saldoNombradaDao;
	}

	/**
	 * @return the dateUtilsDao
	 */
	public DateUtilsDao getDateUtilsDao() {
		return dateUtilsDao;
	}

	/**
	 * @param dateUtilsDao the dateUtilsDao to set
	 */
	public void setDateUtilsDao(DateUtilsDao dateUtilsDao) {
		this.dateUtilsDao = dateUtilsDao;
	}

}