package com.indeval.portaldali.presentation.jsf.fechas;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;


public class DateComponent extends UIInput {
	
	private String pattern		= null;
	private String topValue		= null;
	private String bottomValue	= null;
	private String styleClass	= null;
	private String style		= null;
	private String tabindex		= null;
	
	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public String getBottomValue() {
		return bottomValue;
	}

	public String getTopValue() {
		return topValue;
	}

	public void setBottomValue(String bottomValue) {
		this.bottomValue = bottomValue;
	}

	public void setTopValue(String topValue) {
		this.topValue = topValue;
	}
	
	public Object saveState(FacesContext context){
		Object[] state = new Object[7];
		state[0] = super.saveState(context);
		state[1] = getTopValue(); 
		state[2] = getBottomValue();
		state[3] = getPattern();
		state[4] = getStyle(); 
		state[5] = getStyleClass();
		state[6] = getTabindex();
        
		return (Object)state;
	}
	
	public void restoreState(FacesContext context, Object state){
		Object[] stateArr = (Object[]) state;
		super.restoreState(context, stateArr[0]);
		setTopValue((String)stateArr[1]);
		setBottomValue((String)stateArr[2]);
		setPattern((String)stateArr[3]);
		setStyle((String)stateArr[4]); 
		setStyleClass((String)stateArr[5]);
		setTabindex( (String)stateArr[6] );
		
	}
	

	public void updateModel(javax.faces.context.FacesContext facesContext){
		ValueBinding valueBinding = this.getValueBinding("value");
		if( java.lang.String.class.equals( valueBinding.getType(facesContext)) ){
			valueBinding.setValue(facesContext,getValue());
		}
		else if ( java.util.Date.class.equals(valueBinding.getType(facesContext)) ){
			SimpleDateFormat format = new SimpleDateFormat(getPattern());
			try{
				String valorEnviado = (String)getValue();
				Date fecha = format.parse(valorEnviado);
				Calendar cal = GregorianCalendar.getInstance();
				cal.setTime(fecha);
				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				valueBinding.setValue(facesContext,cal.getTime());
				valueBinding.setValue(facesContext,fecha);
			}catch( Exception e ){
				valueBinding.setValue(facesContext,null);
			}
		}
		setValue(null);
	}
	
	
	public Object getValue(){
		Object valor = super.getValue(); 
		if( valor == null || valor instanceof java.lang.String){
			return valor;
		}else if( valor instanceof java.util.Date ){
			SimpleDateFormat formater = new SimpleDateFormat(getPattern());
			return formater.format(valor);
		}
		return null;
	}
	
	public DateComponent (){
		this.setRendererType("indeval.Date");
	}

	public String getFamily() {
		return "indeval.Date";
	}
	
    public String getType() {
        return "indeval.Date";
    }
    
	public boolean isError() {
		return !this.isValid();
	}


	public String getStyle() {
		return style;
	}

	public String getStyleClass() {
		return styleClass;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	public String getTabindex() {
		return tabindex;
	}

	public void setTabindex(String tabindex) {
		this.tabindex = tabindex;
	}

}

