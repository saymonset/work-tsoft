package com.indeval.portaldali.presentation.jsf.fechas;


import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.webapp.UIComponentTag;

public class DateTag extends UIComponentTag {

     private String required 	= "false";
     private String value 		= null;
     private String topValue 	= null;
     private String bottomValue = null;
     private String pattern 	= "dd-MM-yyyy";
     private String rendered	= "true";
     private String styleClass	= null;
     private String style		= null;
     private String tabindex	= null;
     
     
	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

     
    public String getRendered() {
		return rendered;
	}

	public String getRequired() {
		return required;
	}

	public void setRequired(String required) {
          this.required = required;
     }
     
     public String getValue() {
         return value;
     }
    
    public void setValue(String value) {
         this.value = value;
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
     
     
     
     protected void setProperties(UIComponent component) {
    	 super.setProperties(component);
         DateComponent dtComponent = (DateComponent)component;
         
         dtComponent.setRequired(getBooleanValue( required ));
         dtComponent.setRendered(getBooleanValue( rendered ));
         dtComponent.setStyle(getStringValue( style, "style", component ));
         dtComponent.setStyleClass(getStringValue( styleClass, "styleClass", component ));
    	 dtComponent.setRendered(getBooleanValue( rendered ));
         dtComponent.setPattern(pattern);
         String valor = getStringForValueElement( value, component );
         dtComponent.setValue(valor);
         dtComponent.setValue(getStringValue( value, "value", component ));
         dtComponent.setTopValue(getStringValue( topValue, "topValue", component ));
         dtComponent.setBottomValue(getStringValue( bottomValue, "bottomValue", component ));
         dtComponent.setTabindex(getStringValue( tabindex, "tabindex", component ));
          
     }

     public String getComponentType() {
          return "indeval.Date";     
     }

     public String getRendererType() {
          return "indeval.Date";     
     }

	public void setRendered(String rendered) {
		this.rendered = rendered;
	}

	public boolean getBooleanValue( String value ){
		if (value == null){
			return false;
		}
		if ( value.equals("true") || value.equals("false") ){
			Boolean temp = new Boolean(value);
			return temp.booleanValue();
		}
		try{
			FacesContext context = FacesContext.getCurrentInstance();
			ValueBinding valueBinding = context.getApplication().createValueBinding( value );
			Boolean temp = new Boolean(valueBinding.getValue(context).toString());
			return temp.booleanValue();
		}catch (Exception e) {
			return false;
		}
	}
	
	public String getStringForValueElement( String value, UIComponent component ){
		if (value == null || value.trim().equals("")){
			return null;
		}
        if( isValueReference(value) ) {
        	ValueBinding valueBinding = FacesContext.getCurrentInstance().getApplication().createValueBinding( value );
            component.setValueBinding( "value", valueBinding );
            return null;
        }
        return value;
	}
	
	public String getStringValue( String value, String name, UIComponent component ){
		
		if (value == null){
			return null;
		}
		
		
		FacesContext facesContext = FacesContext.getCurrentInstance();
		
		ValueBinding valueBinding = null;
        if( isValueReference(value) ) {
            valueBinding = facesContext.getApplication().createValueBinding( value );
            component.setValueBinding( name, valueBinding );
        }
        else {
            return value;
        }
		
		if( java.lang.String.class.equals( valueBinding.getType(facesContext)) ){
			return  (String)valueBinding.getValue(facesContext);
		}
		if ( java.util.Date.class.equals(valueBinding.getType(facesContext)) ){
			SimpleDateFormat format = new SimpleDateFormat(getPattern());
			if ( valueBinding.getValue(facesContext) == null ){
				return null;
			}
			return format.format((Date)valueBinding.getValue(facesContext));
		}
		return null;
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
