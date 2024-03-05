package com.indeval.portaldali.presentation.jsf.converters;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;


public class TitleDateConverter implements Converter {
    
    private String[] weekDays = {"Domingo","Lunes","Martes","Mi\u00E9rcoles","Jueves","Viernes","S\u00E1bado"};
    private String[] months = {"Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};

    public static final String CONVERTER_ID = "com.indeval.Currency";

    public TitleDateConverter() {
    }

    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value)  {
        return null;
    }

    public String format2Digits( int number ){
        String numero = "00" + number;
        return numero.substring( numero.length()-2 );
    }
    
    public String convert( Date dt ){
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(dt);
        cal.get( Calendar.MINUTE );
        
        return  weekDays[cal.get(Calendar.DAY_OF_WEEK)-1] + ", " +
                cal.get(Calendar.DAY_OF_MONTH) + " de "  +
                months[ cal.get(Calendar.MONTH) ] + " de " + 
                cal.get(Calendar.YEAR) + " | " + 
                cal.get( Calendar.HOUR_OF_DAY ) + ":" + 
                format2Digits(cal.get( Calendar.MINUTE ))  + ":"+
                format2Digits(cal.get( Calendar.SECOND ));
    }
    
    
    public String getAsString(FacesContext facesContext, UIComponent uiComponent,Object value) {
        
        if (facesContext == null) throw new NullPointerException("facesContext");
        if (uiComponent == null) throw new NullPointerException("uiComponent");

        if (value == null) {
            return "";
        }
        if (value instanceof String){
            return (String)value;
        }
        try {
            Date dt = (Date) value;
            return convert( dt );  
        } catch (Exception e) {
            throw new ConverterException(e);
        }
    }

    
}
