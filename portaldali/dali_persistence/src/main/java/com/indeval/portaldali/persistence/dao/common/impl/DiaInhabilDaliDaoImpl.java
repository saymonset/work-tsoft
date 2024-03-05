/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.persistence.dao.common.impl;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.util.Assert;

import com.bursatec.persistence.dao.impl.BaseDaoHibernateImpl;
import com.indeval.portaldali.persistence.dao.common.DiaInhabilDaliDao;
import com.indeval.portaldali.persistence.model.DiaInhabil;
import com.indeval.portaldali.persistence.util.DateUtil;

/**
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class DiaInhabilDaliDaoImpl extends BaseDaoHibernateImpl implements 
		DiaInhabilDaliDao {

    /** Log de clase. */
    private static final Logger logger = LoggerFactory.getLogger(DiaInhabilDaliDaoImpl.class);

    /**
     * @see com.indeval.portaldali.persistence.dao.common.DiaInhabilDaliDao#esInhabil(java.util.Date)
     */
    public boolean esInhabil(final Date fecha) {
    	return fechaInhabil(fecha) > 0 ? true : false;
    }
    
	/**
	 * @see com.indeval.portaldali.persistence.dao.common.DiaInhabilDaliDao#fechaInhabil(java.util.Date)
	 */
	public int fechaInhabil(final Date fecha) {
		
		logger.info("Entrando a DiaInhabilDaliDaoImpl.esInhabil()");
		
		Assert.notNull(fecha, "La fecha es un dato obligatorio");
        
		logger.debug("La fecha a validar es: [" + fecha.toString() +"]");

		DiaInhabil cDiaInhabilReturn = 
        	(DiaInhabil) this.getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(Session session)
                throws HibernateException, SQLException {

            		Criteria criteria = session.createCriteria(DiaInhabil.class);
            		criteria.add(Restrictions.between("diaInhabil", 
                            DateUtil.preparaFechaConExtremoEnSegundos(fecha, true), 
                                    DateUtil.preparaFechaConExtremoEnSegundos(fecha, false)));
                    return criteria.uniqueResult();

            }
        });

        logger.debug(ReflectionToStringBuilder.reflectionToString(cDiaInhabilReturn));
        
        
        /* Se verifica si la fecha esta en el catalogo de fechas inhabiles */
		return cDiaInhabilReturn == null ? 0 : 1;
	}

    /**
     * @see com.indeval.portaldali.persistence.dao.common.DiaInhabilDaliDao#agregaDiasHabiles(java.util.Date, int)
     */
    public Date agregaDiasHabiles(Date fecha, int offset) {

        logger.info("Entrando a DiaInhabilDaliDaoImpl.agregaDiasHabiles()");
        
    	 if(fecha!=null){
             fecha = getFechaHoraCero(fecha);            
         } else {
        	 return null;
         }
        
        Date fechaInicial = null;
        Date fechaFinal = null;

        if (offset >= 0) {
            fechaInicial = (Date) fecha.clone();
            fechaFinal = DateUtil.addDays(fechaInicial, 80);
        }
        else {
            fechaFinal = (Date) fecha.clone();
            fechaInicial = DateUtil.addDays(fechaFinal, -80);
        }

        /* Se obtiene una instancia de Calendar y se settea el Date1 */
        final Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(fechaInicial);
        
        /* Se obtiene una instancia de Calendar y se settea el Date2 */
        final Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(fechaFinal);
        
        List fechas = diasInhabilesEnRango(calendar1, calendar2);
     
        if (offset >= 0) {
            for (int j = 0; j < offset; j++) {
                fechaInicial = DateUtil.addDays(fechaInicial, 1);
                for (int i = 0; i < fechas.size(); i++) {
                	DiaInhabil dia = (DiaInhabil)fechas.get(i);
                	Date diaInhabil = getFechaHoraCero(dia.getDiaInhabil());
                    if ( fechaInicial.compareTo(diaInhabil) == 0 ) {
                        j--;
                    }
                }
            }
        }
        else {
            for (int j = 0; j < (offset * -1); j++) {
                fechaFinal = DateUtil.addDays(fechaFinal, -1);
                for (int i = 0; i < fechas.size(); i++) {
                	DiaInhabil dia = (DiaInhabil)fechas.get(i);
                	Date diaInhabil = getFechaHoraCero(dia.getDiaInhabil());
                    if ( fechaFinal.compareTo(diaInhabil) == 0 ) {
                        j--;
                    }
                }
            }
            fechaInicial.setTime(fechaFinal.getTime());
        }
        return fechaInicial;
        
    }

    /**
     * @see com.indeval.portaldali.persistence.dao.common.DiaInhabilDaliDao#findDiasInhabilesByMonthYear(int, int)
     */
    @SuppressWarnings("unchecked")
    public List<DiaInhabil> findDiasInhabilesByMonthYear(int month, int year) {
        
        logger.info("Entrando a DiaInhabilDaliDaoImpl.findDiasInhabilesByMonthYear()");
        
        Calendar[] instancesCalendar = getInstancesCalendar(month, year);
        
        return diasInhabilesEnRango(instancesCalendar[0], instancesCalendar[1]);
    }
    
    /**
     * Retorna un arreglo con la fecha inicial y la fecha final
     * @param month
     * @param year
     * @return Calendar[]
     */
    private Calendar[] getInstancesCalendar(int month, int year){
        
        Calendar[] instancesCalendar = {Calendar.getInstance(), Calendar.getInstance()}; 

        for (int i = 0; i < instancesCalendar.length; i++) {
            Calendar calendar = instancesCalendar[i];
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            if (i == 0) {
        	calendar.set(Calendar.DAY_OF_MONTH, 1);
	    } else {
		calendar.set(Calendar.DAY_OF_MONTH, 
			calendar.getActualMaximum(calendar.DAY_OF_MONTH));
	    }
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
	}
        
        return instancesCalendar;
        
    }
    
    /**
     * Recupera una lista de los dias inhabiles contenidos en un rango definido
     *  
     * @param calendar1
     * @param calendar2
     * @return List - lista de DiaInhabil
     */
    private List diasInhabilesEnRango(final Calendar calendar1, final Calendar calendar2) {
        
        logger.info("Entrando a DiaInhabilDaliDaoImpl.diasInhabilesEnRango()");
        
        Assert.notNull(calendar1, "No se recibio la fecha inicial del rango");
        Assert.notNull(calendar2, "No se recibio la fecha final del rango");

        return (List) this.getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(Session session)
                throws HibernateException, SQLException {
        	
                    Criteria criteria = session.createCriteria(DiaInhabil.class);
                    criteria.add(Restrictions.between("diaInhabil", calendar1.getTime(), calendar2.getTime()));
                    
                    return criteria.list();

            }
        });
        
    }
    
    private Date getFechaHoraCero(Date fecha) {
        
        logger.info("Entrando a DiaInhabilDaliDaoImpl.getFechaHoraCero()");
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

}
