package com.indeval.portaldali.persistence.dao.util.impl;

import java.util.Calendar;
import java.util.Date;

import org.springframework.jdbc.core.JdbcTemplate;

import com.indeval.portaldali.persistence.dao.util.FechaDao;
import com.indeval.portaldali.persistence.util.UtilFecha;

/**
 * Clase que obtendr&aacute; las fechas manejadas en la aplicacion
 * 
 * @author JAPUCH
 * 
 */
public class FechaDaoImpl implements FechaDao {

	private Integer diasAnterioresConsulta;
	private Integer diasPosterioresConsulta;
	private JdbcTemplate jdbcTemplate;

	/*
	 * public Date getCurrentDate() { Date fechaBD =
	 * (Date)jdbcTemplate.queryForObject("select fe_liquidacion from
	 * bdcamara.dbo.kprocesos", Date.class ); Calendar calBD =
	 * Calendar.getInstance(); calBD.setTime(fechaBD); Date fechaServer =
	 * (Date)jdbcTemplate.queryForObject("Select getdate()", Date.class );
	 * Calendar calServer = Calendar.getInstance();
	 * calServer.setTime(fechaServer); Calendar cal = Calendar.getInstance();
	 * cal.set(Calendar.YEAR, calBD.get( Calendar.YEAR ));
	 * cal.set(Calendar.MONTH, calBD.get( Calendar.MONTH ));
	 * cal.set(Calendar.DAY_OF_MONTH, calBD.get( Calendar.DAY_OF_MONTH ));
	 * cal.set(Calendar.HOUR_OF_DAY, calServer.get( Calendar.HOUR_OF_DAY ));
	 * cal.set(Calendar.MINUTE, calServer.get( Calendar.MINUTE ));
	 * cal.set(Calendar.SECOND, calServer.get( Calendar.SECOND )); return
	 * cal.getTime(); }
	 */

	public Date getFullCurrentDate() {
//		Date fechaServer = (Date) jdbcTemplate.queryForObject(
//				"Select getdate()", Date.class);
//		Calendar calServer = Calendar.getInstance();
//		calServer.setTime(fechaServer);
//		return calServer.getTime();
		return new Date();
	}
	
	public Date getCurrentDate() {
		Date fechaServer = (Date) jdbcTemplate.queryForObject(
				"Select sysdate from dual", Date.class);
		Calendar calServer = Calendar.getInstance();
		calServer.setTime(fechaServer);
		calServer.set(Calendar.HOUR_OF_DAY, 0);
		calServer.set(Calendar.MINUTE, 0);
		calServer.set(Calendar.SECOND, 0);
		calServer.set(Calendar.MILLISECOND, 0);
		
		return calServer.getTime();
	}

	public Date getLimiteInferior() {
		return UtilFecha.addDays(getCurrentDate(), -1
				* diasAnterioresConsulta.intValue());
	}

	public Date getLimiteSuperior() {
		return UtilFecha.addDays(getCurrentDate(), diasPosterioresConsulta
				.intValue());
	}

	public Date getLimiteInferiorHabiles() {
		return UtilFecha.addDiasHabiles(getCurrentDate(), -1
				* diasAnterioresConsulta.intValue());
	}

	public Date getLimiteSuperiorHabiles() {
		return UtilFecha.addDiasHabiles(getCurrentDate(),
				diasPosterioresConsulta.intValue());
	}

	public Integer getDiasAnterioresConsulta() {
		return diasAnterioresConsulta;
	}

	public Integer getDiasPosterioresConsulta() {
		return diasPosterioresConsulta;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setDiasAnterioresConsulta(Integer diasAnterioresConsulta) {
		this.diasAnterioresConsulta = diasAnterioresConsulta;
	}

	public void setDiasPosterioresConsulta(Integer diasPosterioresConsulta) {
		this.diasPosterioresConsulta = diasPosterioresConsulta;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

}
