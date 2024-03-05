/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.dao.common.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.indeval.portaldali.persistence.dao.common.DateUtilsDao;
import com.indeval.portaldali.persistence.dao.common.DualDaliDao;
import com.indeval.portaldali.persistence.util.DateUtil;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 *
 */
public class DateUtilsDaoImpl extends JdbcDaoSupport implements DateUtilsDao {
	
    /**
     * Log de clase.
     */
    private static final Logger logger = LoggerFactory.getLogger(DateUtilsDaoImpl.class);
	
	/** Constante para hh:mm:ss del fin de dia */
	private final static String FIN_DIA = " 23:59:59";
	
	/** Constante para hh:mm:ss del inicio de dia */
    private final static String INICIO_DIA = " 00:00:00";
	
    /** Bean de acceso a DualDaliDao */
    private DualDaliDao dualDaliDao;
    
	/**
	 * @see com.indeval.portaldali.persistence.dao.common.DateUtilsDao#getDateFechaDB()
	 */
	public Date getDateFechaDB() {
	    
	    logger.trace("Entrando a DateUtilsDaoImpl.getDateFechaDB()");
	    
        return new Date(dualDaliDao.getFechaActual().getTime());
	}

	/**
	 * @see com.indeval.portaldali.persistence.dao.common.DateUtilsDao#getStringFechaDBFinDia()
	 */
	public String getStringFechaDBFinDia() {
	    
		logger.trace("Entrando a DateUtilsDaoImpl.getStringFechaDBFinDia()");
		
		StringBuffer fechaFinDia = new StringBuffer();
		fechaFinDia.append(getStringFechaDBSinHoras());
		fechaFinDia.append(FIN_DIA);
		return fechaFinDia.toString();
	}

	/**
	 * @see com.indeval.portaldali.persistence.dao.common.DateUtilsDao#getStringFechaDBInicioDia()
	 */
	public String getStringFechaDBInicioDia() {
	    
		logger.trace("Entrando a DateUtilsDaoImpl.getStringFechaDBInicioDia()");
		
		StringBuffer fechaInicioDia = new StringBuffer();
		fechaInicioDia.append(getStringFechaDBSinHoras());
		fechaInicioDia.append(INICIO_DIA);
		return fechaInicioDia.toString();
	}

	/**
	 * @see com.indeval.portaldali.persistence.dao.common.DateUtilsDao#getStringFechaDB()
	 */
	public String getStringFechaDB() {
	    
		logger.trace("Entrando a DateUtilsDaoImpl.getStringFechaDB()");
		
		return getDateFechaDB().toString();
	}

	/**
	 * @see com.indeval.portaldali.persistence.dao.common.DateUtilsDao#getStringFechaDBSinHoras()
	 */
	public String getStringFechaDBSinHoras() {
		logger.info("Entrando a DateUtilsDaoImpl.getStringFechaDBSinHoras()");
		return getDateFechaDB().toString().substring(0, 10);
	}

	/**
	 * @see com.indeval.portaldali.persistence.dao.common.DateUtilsDao#getFechaDBInicioDia(java.lang.String)
	 */
	public Date getFechaDBInicioDia(String fecha) {
	    
		logger.trace("Entrando a DateUtilsDaoImpl.getFechaDBInicioDia()");
		logger.debug("La fecha recibida es: [" + fecha + "]");
        if(fecha==null){
            fecha = DateUtil.format(this.getDateFechaDB(),"yyyy/MM/dd");
        }
        
        int year = Integer.parseInt(fecha.substring(0,4));
        int month = Integer.parseInt(fecha.substring(5, 7));
        int day = Integer.parseInt(fecha.substring(8));
        Calendar cal = new GregorianCalendar(year, month-1, day, 0, 0, 0);
        cal.add(Calendar.MILLISECOND, 0);
        return cal.getTime();
	}

	/**
	 * @see com.indeval.portaldali.persistence.dao.common.DateUtilsDao#getFechaDBFinDia(java.lang.String)
	 */
	public Date getFechaDBFinDia(String  fecha) {
	    
		logger.trace("Entrando a DateUtilsDaoImpl.getFechaDBFinDia()");
		logger.debug("La fecha recibida es: [" + fecha + "]");
        if(fecha==null){
            fecha = DateUtil.format(this.getDateFechaDB(),"yyyy/MM/dd");
        }
        
        int year = Integer.parseInt(fecha.substring(0,4));
        int month = Integer.parseInt(fecha.substring(5, 7));
        int day = Integer.parseInt(fecha.substring(8));
        Calendar cal = new GregorianCalendar(year, month-1, day, 23, 59, 59);
        cal.add(Calendar.MILLISECOND, 0);
        return cal.getTime();
	}

	/**
	 * @see com.indeval.portaldali.persistence.dao.common.DateUtilsDao#getStringHoras()
	 */
	public String getStringHoras() {
	    
		logger.trace("Entrando a DateUtilsDaoImpl.getStringHoras()");
		
		String horaFechaBD = getDateFechaDB().toString();
		String hhmm = horaFechaBD.substring(11, 13)+horaFechaBD.substring(14, 16);
		return hhmm;
	}

    /**
     * @see com.indeval.portaldali.persistence.dao.common.DateUtilsDao#getFechaHoraCero(java.util.Date)
     */
    public Date getFechaHoraCero(Date fecha) {
        
        logger.trace("Entrando a DateUtilsDaoImpl.getFechaHoraCero()");
        logger.debug("La fecha recibida es: [" + fecha + "]");
        
        if(fecha!=null){
            Calendar calFecha = new GregorianCalendar();
            calFecha.setTime(fecha);
            calFecha.set(Calendar.HOUR_OF_DAY, 0);
            calFecha.set(Calendar.MINUTE, 0);
            calFecha.set(Calendar.SECOND, 0);
            calFecha.set(Calendar.MILLISECOND, 0);
            logger.debug("FechaHoraCero : [" + calFecha.getTime() + "]");
            fecha = calFecha.getTime();            
        }
        return fecha;
    }

    /**
     * @see com.indeval.portaldali.persistence.dao.common.DateUtilsDao#getFechaHoraFinDia(java.util.Date)
     */
    public Date getFechaHoraFinDia(Date fecha) {
        
        logger.debug("Entrando al metodo getFechaHoraFinDia()");
        logger.debug("La fecha recibida es: [" + fecha + "]");
        
        if(fecha!=null){
            Calendar calFecha = new GregorianCalendar();
            calFecha.setTime(fecha);
            int year = calFecha.get(Calendar.YEAR);
            int month = calFecha.get(Calendar.MONTH);
            int day = calFecha.get(Calendar.DAY_OF_MONTH);
            Calendar cal = new GregorianCalendar(year, month, day, 23, 59, 59);
            logger.debug("FechaHoraFinDia : [" + cal.getTime() + "]");
            fecha = cal.getTime();            
        }
        return fecha; 
    }
    
    /**
     * @see com.indeval.portaldali.persistence.dao.common.DateUtilsDao#date2String(java.util.Date, java.lang.String)
     */
    public String date2String(Date date,String pattern){
        
        logger.trace("Entrando a DateUtilsDaoImpl.date2String()");
        
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        return date !=null ? formatter.format(date) : null;
      
    }

    /**
     * @param dualDaliDao
     */
    public void setDualDao(DualDaliDao dualDaliDao) {
        this.dualDaliDao = dualDaliDao;
    }

}
