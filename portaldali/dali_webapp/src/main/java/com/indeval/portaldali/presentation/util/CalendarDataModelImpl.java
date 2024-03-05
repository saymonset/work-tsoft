/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * CalendarDataModelImpl.java
 * Apr 26, 2008
 */
package com.indeval.portaldali.presentation.util;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.richfaces.model.CalendarDataModel;
import org.richfaces.model.CalendarDataModelItem;

import com.indeval.portaldali.persistence.dao.common.DiaInhabilDaliDao;

/**
 * Implementación del modelo de datos para el calendario de rich faces. Indicar qu días no son hábiles y por lo
 * tanto no pueden seleccionarse en el calendario.
 *
 * @author José Antonio Huizar Moreno
 * @version 1.0
 *
 */
public class CalendarDataModelImpl implements CalendarDataModel {
	
	/** DAO para la consulta de los días inhábiles */
	private DiaInhabilDaliDao diaInhabilDaliDao = null;	

	/**
	 * Obtiene el campo diaInhabilDaliDao
	 * @return  diaInhabilDaliDao
	 */
	public DiaInhabilDaliDao getDiaInhabilDao() {
		return diaInhabilDaliDao;
	}

	/**
	 * Asigna el campo diaInhabilDaliDao
	 * @param diaInhabilDaliDao el valor de diaInhabilDaliDao a asignar
	 */
	public void setDiaInhabilDao(DiaInhabilDaliDao diaInhabilDaliDao) {
		this.diaInhabilDaliDao = diaInhabilDaliDao;
	}

	/* (non-Javadoc)
	 * @see org.richfaces.model.CalendarDataModel#getData(java.util.Date[])
	 */
	public CalendarDataModelItem[] getData(Date[] dateArray) {
		
		if (dateArray == null) {
			return null;
		}
		
		CalendarDataModelItem[] items = new CalendarDataModelItem[dateArray.length];
		for (int i = 0; i < dateArray.length; i++) {
			items[i] = createDataModelItem(dateArray[i]);
		}

		return items;
	}
	
	/**
	 * Crea un elemento del modelo de datos del calendario de rich faces y determina si la fecha es un día hábil o inhábil.
	 * 
	 * @param date la fecha que ser considerada para la creación del elemento.
	 * @return un objeto {@link CalendarDataModelItem} el cual representa la fecha proporcionada como parámetro.
	 */
	protected CalendarDataModelItem createDataModelItem(Date date) {
		CalendarDataModelItemImpl item = new CalendarDataModelItemImpl();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		
		Map<String, String> data = new HashMap<String, String>();
		DateFormat enFormatter = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.ENGLISH);
		DateFormat frFormatter = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.FRENCH);
		DateFormat deFormatter = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.GERMAN);
		DateFormat esFormatter = DateFormat.getDateInstance(DateFormat.MEDIUM, new Locale("es"));
		data.put("enLabel", enFormatter.format(date));
		data.put("frLabel", frFormatter.format(date));
		data.put("deLabel", deFormatter.format(date));
		data.put("esLabel", esFormatter.format(date));
		
		//item.setDay(calendar.get(Calendar.DATE));

		if (diaInhabilDaliDao.esInhabil(date)) {
			item.setEnabled(false);
		} else {
			item.setEnabled(true);
		}
		
		item.setData(data);
		
		System.out.println(item.getData() + " " + item.isEnabled());
		
		return item;
	}
	
	/* (non-Javadoc)
	 * @see org.richfaces.model.CalendarDataModel#getToolTip(java.util.Date)
	 */
	public Object getToolTip(Date arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
