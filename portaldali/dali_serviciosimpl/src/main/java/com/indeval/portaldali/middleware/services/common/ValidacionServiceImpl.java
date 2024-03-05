/**
 * Bursatec - Portal DALI
 * Copyrigth (c) 2014 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.middleware.services.common;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.indeval.portaldali.middleware.constantes.ValidacionConstantes;
import com.indeval.portaldali.persistence.dao.common.CuentaNombradaDaliDao;
import com.indeval.portaldali.persistence.dao.common.DateUtilsDao;
import com.indeval.portaldali.persistence.dao.common.DiaInhabilDaliDao;
import com.indeval.portaldali.persistence.dao.common.DivisaDaliDAO;
import com.indeval.portaldali.persistence.util.DateUtil;
import com.indeval.portaldali.persistence.vo.AgentePK;

/**
 * Implementación de la interfaz de negocio FileTransferValidacionService.
 * 
 * @author Pablo Balderas
 */
public class ValidacionServiceImpl implements ValidacionService {

	/** Dao de utilerias */
	private DateUtilsDao dateUtilsDao = null;
	
	/** Dao para los días inhabiles */
	private DiaInhabilDaliDao diaInhabilDao = null;
	
	/** DAO para la consulta de cuentas nombradas */ 
	private CuentaNombradaDaliDao cCuentaNombradaDao = null;
	
	/** Dao para la consulta de divisas */
	private DivisaDaliDAO divisaDAO;
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portaldali.middleware.services.common.ValidacionService#validarFechaDiaHabil(java.lang.String, java.lang.String, boolean)
	 */
	public boolean validarFechaDiaHabil(String strFecha, String formatoFecha, boolean actual) {
		try{			
			Date fecha = DateUtil.stringToDate(strFecha, formatoFecha);
			Date fechaActualHrCero = 
					DateUtil.preparaFechaConExtremoEnSegundos(dateUtilsDao.getDateFechaDB(), true);			
			boolean inhabil = diaInhabilDao.esInhabil(fecha);
			return actual ? (fecha != null && !inhabil && fechaActualHrCero.equals(fecha)) : (fecha != null && !inhabil);
		}
		catch(Exception e) {
			return false;
		}
		
	}
	
	public boolean validarFechaRangoUnAnio(String strFecha, String formatoFecha) {
		try {
			Date fecha = DateUtil.stringToDate(strFecha, formatoFecha);
			Date fechaActual = new Date();
			
			Calendar calActual = Calendar.getInstance();
			calActual.setTime(fechaActual);
			calActual.set(calActual.get(Calendar.YEAR), calActual.get(Calendar.MONTH), calActual.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
			
			Calendar calAnioAtras = Calendar.getInstance();
			calAnioAtras.set(calActual.get(Calendar.YEAR) - 1, calActual.get(Calendar.MONTH), calActual.get(Calendar.DAY_OF_MONTH) + 1, 0, 0, 0);
			
			return (fecha.compareTo(calAnioAtras.getTime()) > 0) && (fecha.compareTo(calActual.getTime()) <= 0);
		} catch (Exception e) {
			return false;
		}
		
	}

	public boolean validarFechaValida(String strFecha, String formatoFecha) {
		try {
			DateUtil.stringToDate(strFecha, formatoFecha);						
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portaldali.middleware.services.common.ValidacionService#validarFechaDiaHabil(java.util.Date)
	 */
	public boolean validarFechaDiaHabil(Date fecha) {
		return !diaInhabilDao.esInhabil(fecha);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portaldali.middleware.services.common.ValidacionService#validarRfcCurp(java.lang.String)
	 */
	public boolean validarRfcCurp(String rfcCurp) {
		boolean resultado = false;
		if(StringUtils.isNotBlank(rfcCurp)) {
			if(rfcCurp.length() == 18) {
				resultado = rfcCurp.matches(ValidacionConstantes.ER_CURP);
			}
			else if(rfcCurp.length() == 10 || rfcCurp.length() == 12 || rfcCurp.length() == 13) {
				resultado = rfcCurp.matches(ValidacionConstantes.ER_RFC);
			}
		}
		return resultado;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portaldali.middleware.services.common.ValidacionService#validarExpresionRegular(java.lang.String, java.lang.String)
	 */
	public boolean validarExpresionRegular(String cadena, String expresionRegular) {
		return cadena.matches(expresionRegular);
	}

	/*
	 * (non-Javadoc)
	 * @see com.indeval.portaldali.middleware.services.common.ValidacionService#validarInstitucionCuenta(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public boolean validarInstitucionCuenta(String idInstitucion,
			String folioInstitucion, String cuenta, String tipoCustodia,
			String naturaleza) {
		AgentePK agentePK = new AgentePK();
		agentePK.setIdInst(idInstitucion);
		agentePK.setFolioInst(folioInstitucion);
		agentePK.setCuenta(cuenta);
		return cCuentaNombradaDao.obtenerCuentaNombradaInstitucion(agentePK, tipoCustodia, naturaleza) != null;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.indeval.portaldali.middleware.services.common.ValidacionService#validarDivisa(java.lang.String)
	 */
	public boolean validarDivisa(String divisa) {
		return divisaDAO.obtenerDivisaPorClaveAlfavetica(divisa) != null;
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

	/**
	 * @return the diaInhabilDao
	 */
	public DiaInhabilDaliDao getDiaInhabilDao() {
		return diaInhabilDao;
	}

	/**
	 * @param diaInhabilDao the diaInhabilDao to set
	 */
	public void setDiaInhabilDao(DiaInhabilDaliDao diaInhabilDao) {
		this.diaInhabilDao = diaInhabilDao;
	}

	/**
	 * @return the divisaDAO
	 */
	public DivisaDaliDAO getDivisaDAO() {
		return divisaDAO;
	}

	/**
	 * @param divisaDAO the divisaDAO to set
	 */
	public void setDivisaDAO(DivisaDaliDAO divisaDAO) {
		this.divisaDAO = divisaDAO;
	}

	/**
	 * @return the cCuentaNombradaDao
	 */
	public CuentaNombradaDaliDao getcCuentaNombradaDao() {
		return cCuentaNombradaDao;
	}

	/**
	 * @param cCuentaNombradaDao the cCuentaNombradaDao to set
	 */
	public void setcCuentaNombradaDao(CuentaNombradaDaliDao cCuentaNombradaDao) {
		this.cCuentaNombradaDao = cCuentaNombradaDao;
	}
	

}
