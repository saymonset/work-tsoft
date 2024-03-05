package com.indeval.portaldali.presentation.jsf.fechas;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.faces.el.ValueBinding;

public class EndDateComponent extends DateComponent {
	public EndDateComponent (){
		this.setRendererType("indeval.EndDate");
	}

	public String getFamily() {
		return "indeval.EndDate";
	}
    
    public String getType() {
        return "indeval.EndDate";
    }
    
    public void updateModel(javax.faces.context.FacesContext facesContext){
		ValueBinding valueBinding = this.getValueBinding("value");
		if( java.lang.String.class.equals( valueBinding.getType(facesContext)) ){
			valueBinding.setValue(facesContext,getValue());
		}
		else if ( java.util.Date.class.equals(valueBinding.getType(facesContext)) ){
			try{
				SimpleDateFormat format = new SimpleDateFormat(getPattern());
				String valorEnviado = (String)getValue();
				Date fecha = format.parse(valorEnviado);
				Calendar cal = GregorianCalendar.getInstance();
				cal.setTime(fecha);
				cal.set(Calendar.HOUR_OF_DAY, 23);
				cal.set(Calendar.MINUTE, 59);
				cal.set(Calendar.SECOND, 59);
				valueBinding.setValue(facesContext,cal.getTime());
			}catch( Exception e ){
				valueBinding.setValue(facesContext,null);
			}
		}
		setValue(null);
	}
}
