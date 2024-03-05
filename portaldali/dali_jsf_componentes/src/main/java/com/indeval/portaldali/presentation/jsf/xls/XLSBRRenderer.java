/*
 * Copyrigth (c) 2005-2006 Bursatec. All Rights Reserved.
 */
package com.indeval.portaldali.presentation.jsf.xls;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;


/**
 *
 */
public class XLSBRRenderer extends Renderer {

    /**
     * Creates a new XLSBRRenderer object.
     */
    public XLSBRRenderer() {
        super();

    }

    /**
     *
     *
     * @param facesContext 
     * @param uiComponent 
     *
     * @throws IOException
     */
    public void encodeBegin(FacesContext facesContext, UIComponent uiComponent)
                     throws IOException {

        ((XLSBRComponent) uiComponent).getNextRow();

    }

}
