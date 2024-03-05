package com.indeval.portaldali.presentation.jsf.xls;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.webapp.UIComponentTag;

public class XLSColumnTag extends UIComponentTag {

	private String header;
	private String pattern;
	private String value;
	private String footer;
	private String rendered;
	private String bgcolor;

	protected void setProperties(UIComponent component) {
		super.setProperties(component);

		XLSColumnComponent columnComponent = (XLSColumnComponent) component;

		FacesContext context = FacesContext.getCurrentInstance();
		Application application = context.getApplication();

		if (pattern != null) {
			if (UIComponentTag.isValueReference(pattern)) {
				ValueBinding vbPattern = application.createValueBinding(pattern);
				columnComponent.setValueBinding("pattern", vbPattern);
			} else {
				columnComponent.setPattern(pattern);
			}
		}
		if (header != null) {
			if (UIComponentTag.isValueReference(header)) {
				columnComponent.setValueBinding("header", application.createValueBinding(header));
			} else {
				columnComponent.setHeader(header);
			}
		}
		if (footer != null) {
			if (UIComponentTag.isValueReference(footer)) {
				columnComponent.setValueBinding("footer", application.createValueBinding(footer));
			} else {
				columnComponent.setFooter(footer);
			}
		}
		if (value != null) {
			if (UIComponentTag.isValueReference(value)) {
				columnComponent.setValueBinding("value", application.createValueBinding(value));
			} else {
				columnComponent.setValue(value);
			}
		} else {
			columnComponent.setValue(" ");
		}
		if (rendered != null) {
			if (UIComponentTag.isValueReference(rendered)) {
				Boolean renderedData = (Boolean) application.createValueBinding(rendered).getValue(context);
				columnComponent.setRendered(renderedData.booleanValue());
			} else {
				Boolean boRendered = new Boolean(rendered);
				columnComponent.setRendered(boRendered.booleanValue());
			}
		}
		if (bgcolor != null) {
			if (UIComponentTag.isValueReference(bgcolor)) {
				columnComponent.setValueBinding("bgcolor", application.createValueBinding(bgcolor));
			} else {
				columnComponent.setBgcolor(bgcolor);
			}
		} else {
			columnComponent.setBgcolor(null);
		}
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
	 * @param bgcolor
	 *            el valor del atributo bgcolor a establecer
	 */
	public void setBgcolor(String bgcolor) {
		this.bgcolor = bgcolor;
	}

	public String getComponentType() {
		return "indeval.XLSColumn";
	}

	public String getRendererType() {
		return "indeval.XLSColumn";
	}

	public String getHeader() {
		return header;
	}

	public String getValue() {
		return value;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void release() {
		super.release();
		header = null;
		pattern = null;
		value = null;
		footer = null;
		rendered = null;
	}

	public String getFooter() {
		return footer;
	}

	public void setFooter(String footer) {
		this.footer = footer;
	}

	public String getRendered() {
		return rendered;
	}

	public void setRendered(String rendered) {
		this.rendered = rendered;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

}
