package com.indeval.portaldali.presentation.jsf.util;

import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.render.Renderer;

import javax.faces.component.html.HtmlOutputText;;

public class OutputTextComponent extends HtmlOutputText {
    
    public static final String COMPONENT_TYPE = "indeval.outputText";
    private static final String DEFAULT_RENDERER_TYPE = "indeval.outputText";
    public static final String COMPONENT_FAMILY = "indeval.outputText";
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
     
    public OutputTextComponent(){
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
		
		/*Object tempValue = value == null ? "" : value; 
		String stFormattedValue = Utils.getStringValue(tempValue, getPattern(), getLength(), "LEFT");
		stFormattedValue = stFormattedValue.replaceAll(" ", "&nbsp;");
		if( getError().booleanValue() ){
			stFormattedValue = "<b style='color:red'>" + stFormattedValue + "</b>";
		}
		return stFormattedValue;
		*/
		
		String stValue = "";
		if( value != null ){
			stValue = value.toString();
		}
		return applyLength( stValue, getLength().intValue() );
		
	}
	
	
	private String applyLength( String value, int length){
		String startTag = "";
		String endTag = "";
		String contentTag = "";
		int state = 0;
		
		if( !value.startsWith("<") ){
			contentTag = value;
		}
		else{
			for ( int i = 0; i <  value.length(); i++){
			
				String tempCar = value.substring(i,i+1);
				switch ( state ){
					case 0:
						if( tempCar.equals(">")  ){
							state = 1;
						}
						startTag += tempCar; 
						break;
					case 1:
						if( !tempCar.equals("<")  ){
							contentTag += tempCar; 
						}else{
							endTag += tempCar; 
							state = 2;
						}
						break;
					default:
						endTag += tempCar;
						break;
				}
			}
		}
		
		if( contentTag.length() > length ){
			contentTag = contentTag.substring(0,length);
		}
		int faltantes = length - contentTag.length();   
		for( int i = 0; i < faltantes; i++ ){
			contentTag += "&nbsp;";
		}
		contentTag = contentTag.replaceAll(" ", "&nbsp;");
		
		return startTag + contentTag + endTag;
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
