package com.indeval.portaldali.presentation.jsf.fechas;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;
import javax.servlet.http.HttpServletRequest;

import com.indeval.portaldali.presentation.util.Config;
import com.indeval.portaldali.presentation.util.Utils;

/**
 * @author Richard Hightower
 * 
 */
public class DateRenderer extends Renderer {


	public void decode(FacesContext context, UIComponent component) {
		Map requestMap = context.getExternalContext().getRequestParameterMap();
		String clientId = component.getClientId(context);
		String value = (String) requestMap.get(clientId);
		((UIInput) component).setSubmittedValue(value);
	}

    
	public void encodeBegin(FacesContext context, UIComponent component)
			throws IOException {
		
		if (!component.isRendered( )) {
	        return;
	    }

		DateComponent fieldComponent = (DateComponent) component;
		ResponseWriter writer = context.getResponseWriter();
		encodeHeader(writer, fieldComponent);
		encodeBody(writer, fieldComponent);
	}

	

	private void encodeInput(ResponseWriter writer, DateComponent dateComponent)
			throws IOException {
		
		FacesContext facesContext = FacesContext.getCurrentInstance();
		writer.startElement("input", dateComponent);
		writer.writeAttribute("type", "text", "type");
		writer.writeAttribute("readonly", "true", "readonly");
		writer.writeAttribute("size", "10", "size");
		writer.writeAttribute("onfocus", dateComponent.getClientId(facesContext).replace(':','_') + "Calendar.showCalendar();", "onfocus");
		writer.writeAttribute("id", dateComponent.getClientId(facesContext), "id");
		writer.writeAttribute("name", dateComponent.getClientId(facesContext), "name");
		if( dateComponent.getTabindex() != null && 
				!dateComponent.getTabindex().trim().equals("") ){
			writer.writeAttribute("tabindex", (String)dateComponent.getTabindex(), "tabindex");
		}
		if (  dateComponent.getStyle() != null)
			writer.writeAttribute("style", (String)dateComponent.getStyle(), "style");
		if (  dateComponent.getStyleClass() != null)
			writer.writeAttribute("class", (String)dateComponent.getStyleClass(), "class");
		if (  dateComponent.getValue() != null)
			writer.writeAttribute("value", (String)dateComponent.getValue(), "value");
		writer.endElement("input");
	}
	
	private void encodeLowerLimit(ResponseWriter writer, DateComponent dateComponent)
			throws IOException {
		FacesContext facesContext = FacesContext.getCurrentInstance();

		writer.startElement("input", dateComponent);
		writer.writeAttribute("type", "hidden", "type");
		writer.writeAttribute("id", dateComponent.getClientId(facesContext) + "Bottom", "id");
		writer.writeAttribute("value", (String)dateComponent.getBottomValue(), "value");
		writer.endElement("input");
	}
	
	private void encodeUperLimit(ResponseWriter writer, DateComponent dateComponent)
			throws IOException {
		FacesContext facesContext = FacesContext.getCurrentInstance();

		writer.startElement("input", dateComponent);
		writer.writeAttribute("type", "hidden", "type");
		writer.writeAttribute("id", dateComponent.getClientId(facesContext) + "Top", "id");
		writer.writeAttribute("value",(String)dateComponent.getTopValue(), "value");
		writer.endElement("input");
	}
	
	private void encodeHeader(ResponseWriter writer, DateComponent fieldComponent)
	throws IOException {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpServletRequest request =  ( HttpServletRequest )facesContext.getExternalContext().getRequest();
		String contextPath	= request.getContextPath();
		if ( request.getAttribute("FechasInitialized") == null ) {
            String jsPath = contextPath + Config.getProperty("javascriptWebPath");
			String cssPath = contextPath + Config.getProperty("styleWebPath");
			writer.write(Utils.BR);
			writer.startElement("div", fieldComponent);
				writer.writeAttribute("style", "display:none; position:absolute;", "style");
            writer.write(Utils.BR);
            writer.startElement("script", fieldComponent);
                writer.writeAttribute("src", contextPath + "/dwr/interface/JDiasInhabilesCtrl.js?dummy=" + Math.round(Math.random() * 100000 ), "src");
            writer.endElement("script");
            writer.write(Utils.BR);
            writer.startElement("script", fieldComponent);
                writer.writeAttribute("src", contextPath + "/dwr/engine.js?dummy=" + Math.round(Math.random() * 100000 ), "src");
            writer.endElement("script");
			writer.write(Utils.BR);
			writer.startElement("script", fieldComponent);
				writer.writeAttribute("src", jsPath + "Fecha.js?dummy=" + Math.round(Math.random() * 100000 ), "src");
			writer.endElement("script");
            writer.write(Utils.BR);
            writer.startElement("script", fieldComponent);
                writer.writeAttribute("src", jsPath + "DiasInhabilesContiner.js?dummy=" + Math.round(Math.random() * 100000 ), "src");
            writer.endElement("script");
			writer.write(Utils.BR);
			writer.startElement("script", fieldComponent);
				writer.writeAttribute("src", jsPath + "CalendarioDiasHabiles.js?dummy=" + Math.round(Math.random() * 100000 ), "src");
			writer.endElement("script");
			writer.write(Utils.BR);
			writer.startElement("link", fieldComponent);
				writer.writeAttribute("rel", "STYLESHEET", "rel");
				writer.writeAttribute("type", "text/css", "type");
				writer.writeAttribute("href", cssPath + "calendario.css?dummy=" + Math.round(Math.random() * 100000 ), "href");
			writer.endElement("link");
			writer.write(Utils.BR);
			writer.endElement("div");
			writer.write(Utils.BR);
			request.setAttribute( "FechasInitialized" , "OK");
		}
	}
	private void encodeBody(ResponseWriter writer, DateComponent dateComponent)
	throws IOException {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpServletRequest request =  ( HttpServletRequest )facesContext.getExternalContext().getRequest();
		String contextPath	= request.getContextPath();
		String imagesPath = contextPath + Config.getProperty("imagesWebPath");

		Locale locale = facesContext.getViewRoot( ).getLocale( );
		String id = dateComponent.getClientId(facesContext);
		
		writer.startElement("SPAN", dateComponent);
		writer.writeAttribute("style", "display:inline;", "style");
		writer.write(Utils.BR);
		writer.startElement("table", dateComponent);
		writer.writeAttribute("cellpadding", "0", "cellpadding");
		writer.writeAttribute("cellspacing", "0", "cellspacing");
		writer.writeAttribute("width", "100%", "width");
        writer.startElement("tr", dateComponent);
        writer.startElement("td", dateComponent);
        writer.writeAttribute("align", "left", "align");
        encodeInput( writer, dateComponent );
        writer.endElement("td");
        writer.endElement("tr");
        writer.startElement("tr", dateComponent);
        writer.startElement("td", dateComponent);
		writer.write(Utils.BR);
		writer.startElement("script", dateComponent);
		writer.write( id.replace(':','_') + "Calendar = createCalendario( " + Utils.dquote(id) + ", " + Utils.dquote(imagesPath) + ", " + Utils.dquote(dateComponent.getPattern()) + ", " + Utils.dquote(locale.getLanguage()) +" );" );
		writer.endElement("script");
		writer.write(Utils.BR);
        writer.endElement("td");
        writer.endElement("tr");
        writer.endElement("table");
		writer.endElement("SPAN");
		writer.write(Utils.BR);
		
		if( dateComponent.getBottomValue() != null ){
			writer.write(Utils.BR);
			writer.startElement("div", dateComponent);
			writer.writeAttribute("style", "display:none;", "style");
			writer.write(Utils.BR);
			encodeLowerLimit(writer, dateComponent);
			writer.write(Utils.BR);
			writer.startElement("script", dateComponent);
			writer.write(Utils.BR);
			writer.write( id.replace(':','_') + "BotomCalendar = createCalendario( " + Utils.dquote(id + "Bottom") + ", " + Utils.dquote(imagesPath) + ", " + Utils.dquote(dateComponent.getPattern()) + ", " + Utils.dquote(locale.getLanguage()) +" );" );
			writer.write(Utils.BR);
			writer.write( id.replace(':','_') + "Calendar.setLowerLimit( " + id.replace(':','_') + "BotomCalendar );");
			writer.write(Utils.BR);
			writer.endElement("script");
			writer.write(Utils.BR);
			writer.endElement("div");
			writer.write(Utils.BR);
			
		}
		if( dateComponent.getTopValue() != null ){
			writer.write(Utils.BR);
			writer.startElement("div", dateComponent);
			writer.writeAttribute("style", "display:none;", "style");
			writer.write(Utils.BR);
			encodeUperLimit(writer, dateComponent);
			writer.write(Utils.BR);
			writer.startElement("script", dateComponent);
			writer.write(Utils.BR);			
			writer.write( id.replace(':','_') + "TopCalendar = createCalendario( " + Utils.dquote(id + "Top") + ", " + Utils.dquote(imagesPath) + ", " + Utils.dquote(dateComponent.getPattern()) + ", " + Utils.dquote(locale.getLanguage()) +" );" );
			writer.write(Utils.BR);			
			writer.write( id.replace(':','_') + "Calendar.setUpperLimit( " + id.replace(':','_') + "TopCalendar );");
			writer.write(Utils.BR);			
			writer.endElement("script");
			writer.write(Utils.BR);
			writer.endElement("div");
			writer.write(Utils.BR);
		}
		
	}

	
}
