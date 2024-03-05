package com.indeval.portaldali.presentation.jsf.fechas;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

public class InitDateRenderer extends DateRenderer {
	public void encodeEnd(FacesContext context, UIComponent component)
	throws IOException {

		if (!component.isRendered( )) {
			return;
		}
		HttpServletRequest request =  ( HttpServletRequest )context.getExternalContext().getRequest();
		request.setAttribute("InitDateRenderer_initDate", component.getClientId(context).replace(':','_') );
	}
}
