package com.indeval.portaldali.presentation.jsf.pdf;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.webapp.UIComponentTag;

import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.myfaces.shared_impl.taglib.UIComponentTagBase;

public class PDFTableTagDali extends UIComponentTagBase {

    private Logger logger = LoggerFactory.getLogger(PDFTableTagDali.class);
    private String currentBean;
    private String value;
    private String widthPercentage;
    private String align;
    private String maxColums;
    

	public void release() {
        super.release();
        currentBean = null;
        widthPercentage = null;
        value = null;
        align = null;
    }
    
    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        PDFTableComponentDali tableComponent = (PDFTableComponentDali)component; 
        FacesContext context = FacesContext.getCurrentInstance();
        Application application = context.getApplication();
        if (currentBean != null) {
            tableComponent.setCurrentBean(currentBean);
        }
        if (widthPercentage != null && !widthPercentage.equals("")) {
            tableComponent.setWidthPercentage(new Integer(widthPercentage));
        }
        if (align != null ) {
            if (UIComponentTag.isValueReference(align)) {
                tableComponent.setAlign(application.createValueBinding(align).getValue(context).toString());
            } else {
                tableComponent.setAlign(align);
            }
        }
        if (value != null) {
            if (UIComponentTag.isValueReference(value)) {
                tableComponent.setValueBinding("value", application.createValueBinding(value));
            } else {
                System.out.println("no se encontro la lista");
                logger.error("no se encontro la lista");
            }
        }
        
        if(maxColums !=null && NumberUtils.isDigits(maxColums)){
        	if (UIComponentTag.isValueReference(maxColums)) {
                tableComponent.setMaxColums(Integer.parseInt(application.createValueBinding(maxColums).getValue(context).toString()));
            } else {
                tableComponent.setMaxColums(Integer.parseInt(maxColums));
            }
        }
        
    }
    
    
    public String getComponentType() {
        return "indeval.PDFTable";
    }

    public String getRendererType() {
        return "indeval.PDFTable";
    }

  

    public String getValue() {
        return value;
    }

  
    public void setValue(String value) {
        this.value = value;
    }
    
    public String getCurrentBean() {
        return currentBean;
    }

    public void setCurrentBean(String currentBean) {
        this.currentBean = currentBean;
    }

    public String getWidthPercentage() {
        return widthPercentage;
    }

    public void setWidthPercentage(String widthPercentage) {
        this.widthPercentage = widthPercentage;
    }

    public String getAlign() {
        return align;
    }

    public void setAlign(String align) {
        this.align = align;
    }
    
    public String getMaxColums() {
		return maxColums;
	}

	public void setMaxColums(String maxColums) {
		this.maxColums = maxColums;
	}
    
}
