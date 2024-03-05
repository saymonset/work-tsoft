package com.indeval.portaldali.presentation.jsf.fechas;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.servlet.http.HttpServletRequest;

import com.indeval.portaldali.presentation.util.Utils;

public class EndDateRenderer extends DateRenderer {

	public void encodeEnd(FacesContext context, UIComponent component)
	throws IOException {

		if (!component.isRendered( )) {
			return;
		}
		HttpServletRequest request =  ( HttpServletRequest )context.getExternalContext().getRequest();
		String initDateCalendar = (String)request.getAttribute("InitDateRenderer_initDate" );
		if ( initDateCalendar != null ){
			ResponseWriter writer = context.getResponseWriter();
			writer.startElement("div", component);
			writer.writeAttribute("style", "display:none;", "style");
			writer.write(Utils.BR);
			writer.startElement("script", component);
			writer.write(Utils.BR);
			writer.write(initDateCalendar + "Calendar.setUpperLimit( "  + component.getClientId(context).replace(':','_') + "Calendar );");
			writer.write(Utils.BR);
			writer.write(component.getClientId(context).replace(':','_') + "Calendar.setLowerLimit( "  + initDateCalendar + "Calendar );");
			writer.write(Utils.BR);
			writer.endElement("script");
			writer.write(Utils.BR);
			writer.endElement("div");
			request.removeAttribute("InitDateRenderer_initDate");
		}
	}
}
