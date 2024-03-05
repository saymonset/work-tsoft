/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.presentation.jsf.pdf;

import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lowagie.text.pdf.PdfPTable;


/**
 *
 * @author <a href="mailto:david.rengifo@itbrain.com.mx">David A. Rengifo R.</a>
 */
public class PDFColumnComponentDali extends UIOutput implements IPDFTableElementDali, IPDFRowElementDali {

    private static final Logger logger = LoggerFactory.getLogger(PDFColumnComponentDali.class);
    private Object header = null;
    private Object footer = null;
    private String align = "LEFT";
    private Integer collspan = new Integer(1);
    private String bgcolor;
    
    /**
     * Creates a new PDFColumnComponentDali object.
     */
    public PDFColumnComponentDali() {

        this.setRendererType("indeval.PDFColumn");

    }

    /**
     *
     *
     * @return
     */
    public String getFamily() {

        return "indeval.PDFColumn";

    }

    /**
     *
     *
     * @param context 
     *
     * @return
     */
    public Object saveState(FacesContext context) {

        Object[] state = new Object[4];
        state[0] = super.saveState(context);
        state[1] = getHeader();
        state[2] = getAlign();
        state[3] = getFooter();

        return (Object) state;
    }

    /**
     *
     *
     * @param context 
     * @param state 
     */
    public void restoreState(FacesContext context, Object state) {

        Object[] stateArr = (Object[]) state;
        super.restoreState(context, stateArr[0]);
        setHeader((String) stateArr[1]);
        setAlign((String) stateArr[2]);
        setFooter((String) stateArr[3]);

    }

    /**
     *
     *
     * @return
     */
    public String getAlign() {

        return align;

    }

    /**
     *
     *
     * @return
     */
    public Object getHeader() {

        if (header == null) {

            try {

                return getValueBinding("header").getValue(FacesContext.getCurrentInstance());

            } catch (Exception e) {}

        }

        return header;

    }

    /**
     *
     *
     * @param align 
     */
    public void setAlign(String align) {

        if ((align != null) &&
                (align.toUpperCase().equals("CENTER") || align.toUpperCase().equals("RIGHT") ||
                    align.toUpperCase().equals("LEFT"))) {

            this.align = align.toUpperCase();

        }

    }

    /**
     *
     *
     * @param header 
     */
    public void setHeader(Object header) {

        this.header = header;

    }

    /**
     *
     *
     * @return
     */
    public PdfPTable getTable() {

        return ((IPDFTableElementDali) getParent()).getTable();

    }

    /**
     *
     *
     * @return
     */
    public Boolean isOddRow() {

        return ((IPDFRowElementDali) getParent()).isOddRow();

    }

    /**
	 * Obtiene el valor del atributo bgcolor
	 *
	 * @return el valor del atributo bgcolor
	 */
	public String getBgcolor() {
		return bgcolor;
	}

	/**
	 * Establece el valor del atributo bgcolor
	 *
	 * @param bgcolor el valor del atributo bgcolor a establecer
	 */
	public void setBgcolor(String bgcolor) {
		this.bgcolor = bgcolor;
	}

	/**
     *
     *
     * @return
     */
    public Integer getCollspan() {

        return collspan;

    }

    /**
     *
     *
     * @param collspan 
     */
    public void setCollspan(Integer collspan) {

        this.collspan = collspan;

    }

    /**
     *
     *
     * @return
     */
    public Object getFooter() {

        if (footer == null) {

            try {

                return getValueBinding("footer").getValue(FacesContext.getCurrentInstance());

            } catch (Exception e) {}

        }

        return footer;

    }

    /**
     *
     *
     * @param footer 
     */
    public void setFooter(Object footer) {

        this.footer = footer;

    }

	public String getValueAsString() {
		String valor = " ";
		if (getValue() != null) {
			if (getConverter() != null) {
				valor = getConverter().getAsString(
						FacesContext.getCurrentInstance(), this, getValue());
			} else {
				valor = getValue().toString();
			}
		}
		logger.trace("Valor celda: " + valor);
		return valor;
	}
    
    @Override
	public String toString() {
		return new ToStringBuilder(this).append("value", getValueAsString())
				.toString();
	}

}
