package com.indeval.portaldali.presentation.jsf.util;

import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.render.Renderer;

import com.indeval.portaldali.presentation.util.Utils;

public class OutputText2Component extends HtmlOutputText {
    
    public static final String COMPONENT_TYPE = "indeval.outputText2";
    private static final String DEFAULT_RENDERER_TYPE = "indeval.outputText2";
    public static final String COMPONENT_FAMILY = "indeval.outputText2";
    private Integer defaultLength = new Integer(20); 
    private Integer length;
    private String pattern;
    private String header;
    private Boolean error = Boolean.FALSE;
    
    public Boolean getError() {
    	if( error == null ){
			ValueBinding vb =  getValueBinding("error");
			if( vb == null ){
				return Boolean.FALSE;
			}
			Boolean err = (Boolean)vb.getValue(FacesContext.getCurrentInstance());
			if( err == null ){
				return Boolean.FALSE;
			}
			return err;
		}
		return error;
	}

	public void setError(Boolean error) {
		this.error = error;
	}

	public String getFamily() {
        return COMPONENT_FAMILY;
    }
    
    protected Renderer getRenderer(FacesContext context) {
        Renderer ren = super.getRenderer(context);
        return ren;
    }
     
    public OutputText2Component(){
        setRendererType(DEFAULT_RENDERER_TYPE);
    }
    
    public String getType() {
        return COMPONENT_TYPE;
    }
    
    
    public Object saveState(FacesContext context){
        Object[] state = new Object[5];
        state[0] = super.saveState(context);
        state[1] = length;
        state[2] = error;
        state[3] = header;
        state[4] = pattern;
        
        return (Object)state;
    }
    
    public void restoreState(FacesContext context, Object state){
        Object[] stateArr = (Object[]) state;
        super.restoreState(context, stateArr[0]);
        length = (Integer)stateArr[1];
        error  = (Boolean)stateArr[2];
        header = (String)stateArr[3];
        pattern = (String)stateArr[4];
        
    }

	public Integer getLength() {
		Integer tempLength = defaultLength;
		
		if( length == null ){
			Integer lgt  = null;
			ValueBinding vb =  getValueBinding("length");
			if( vb != null ){
				lgt = (Integer)vb.getValue(FacesContext.getCurrentInstance());
				if( lgt != null ){
					tempLength = lgt;
				}
			}
		}else{
			tempLength = length; 
		}
		int maxLength = Math.max(tempLength.intValue(), getHeader().length());
		maxLength = Math.max(maxLength, getPattern().length());
		return new Integer( maxLength );
	}

	public void setLength(Integer length) {
		this.length = length;
	}
	
	public Object getValue(){
		Object value = super.getValue();
		
		Object tempValue = value == null ? "" : value; 
		String stFormattedValue = Utils.getStringValue(tempValue, getPattern(), getLength(), "LEFT");
		stFormattedValue = stFormattedValue.replaceAll(" ", "&nbsp;");
		if( getError().booleanValue() ){
			stFormattedValue = "<b style='color:red'>" + stFormattedValue + "</b>";
		}
		return stFormattedValue;
	}
	

	public String getPattern() {
		if( pattern == null ){
			ValueBinding vb =  getValueBinding("pattern");
			if( vb == null ){
				return "";
			}
			String pttrn = (String)vb.getValue(FacesContext.getCurrentInstance());
			if( pttrn == null ){
				return "";
			}
			return pttrn;
		}
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public String getHeader() {
		if( header == null ){
			ValueBinding vb =  getValueBinding("header");
			if( vb == null ){
				return "";
			}
			String head = (String)vb.getValue(FacesContext.getCurrentInstance());
			if( head == null ){
				return "";
			}
			return head;
		}
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}
    
}
