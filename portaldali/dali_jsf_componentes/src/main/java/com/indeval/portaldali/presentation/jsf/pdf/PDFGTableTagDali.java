package com.indeval.portaldali.presentation.jsf.pdf;

import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.webapp.UIComponentTag;

import org.apache.myfaces.shared_impl.taglib.UIComponentTagBase;

public class PDFGTableTagDali extends UIComponentTagBase {
    private String widthPercentage;
    private String align;
    private String cellwidths;
    private String border;
    private String bordercolor;
    
    
    public void release() {
        widthPercentage = null;
        align = null;
        super.release();
    }
    
    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        FacesContext context = FacesContext.getCurrentInstance();
        Application application = context.getApplication();
        PDFGTableComponentDali gridComponent = (PDFGTableComponentDali)component; 
        if (widthPercentage != null && !widthPercentage.equals("")) {
            gridComponent.setWidthPercentage(new Integer(widthPercentage));
        }
        if (align != null ) {
            if (UIComponentTag.isValueReference(align)) {
                gridComponent.setAlign(application.createValueBinding(align).getValue(context).toString());
            } else {
                gridComponent.setAlign(align);
            }
        }
        if (border != null ) {
            if (UIComponentTag.isValueReference(border)) {
                gridComponent.setBorder((Integer)application.createValueBinding(border).getValue(context));
            } else {
                gridComponent.setBorder(new Integer(border));
            }
        }
        if (bordercolor != null ) {
            if (UIComponentTag.isValueReference(bordercolor)) {
                gridComponent.setBordercolor((String)application.createValueBinding(bordercolor).getValue(context));
            } else {
                gridComponent.setBordercolor((String)bordercolor);
            }
        }
        if( cellwidths != null ) {
            if (UIComponentTag.isValueReference(cellwidths)) {
                gridComponent.setCellwidths((int[]) application.createValueBinding(cellwidths).getValue(context));
            } else {
                StringTokenizer stoken = new StringTokenizer(cellwidths,",");
                ArrayList arlTemp = new ArrayList();
                while(stoken.hasMoreTokens()) {
                    arlTemp.add(new Integer(stoken.nextToken()));
                }
                int[] widths = new int[arlTemp.size()];
                for( int i = 0;  i < arlTemp.size(); i++ ) {
                    widths[i] = ((Integer)arlTemp.get(i)).intValue();
                }
                gridComponent.setCellwidths(widths);
            }
        }
        
        
        
    }
    
    
    public String getComponentType() {
        return "indeval.PDFGrid";
    }

    public String getRendererType() {
        return "indeval.PDFGrid";
    }

    public String getAlign() {
        return align;
    }

    public void setAlign(String align) {
        if( align != null && 
                ( align.toUpperCase().equals("CENTER") ||
                  align.toUpperCase().equals("RIGHT") ||
                  align.toUpperCase().equals("LEFT"))
           ){
            this.align = align.toUpperCase();
        }
    }

    public String getWidthPercentage() {
        return widthPercentage;
    }

    public void setWidthPercentage(String widthPercentage) {
        this.widthPercentage = widthPercentage;
    }

    public String getCellwidths() {
        return cellwidths;
    }

    public void setCellwidths(String cellwidths) {
        this.cellwidths = cellwidths;
    }

    public String getBorder() {
        return border;
    }

    public void setBorder(String border) {
        this.border = border;
    }

    public String getBordercolor() {
        return bordercolor;
    }

    public void setBordercolor(String bordercolor) {
        this.bordercolor = bordercolor;
    }

    
}
