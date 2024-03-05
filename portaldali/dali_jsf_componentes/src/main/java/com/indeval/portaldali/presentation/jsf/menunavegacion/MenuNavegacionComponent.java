/**
 * 2H Software - Bursatec - INDEVAL
 * Portal DALI
 *
 * MenuNavegacionComponent.java
 * Mar 12, 2008
 */
package com.indeval.portaldali.presentation.jsf.menunavegacion;

import java.io.IOException;

import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.indeval.portaldali.presentation.jsf.menunavegacion.constants.MenuNavegacionConstants;
import com.indeval.portaldali.presentation.jsf.menunavegacion.vo.ElementoMenu;


/**
 * Implementación del componente JSF del menú de navegación.
 * 
 * @author José Antonio Huizar Moreno
 * @version 1.0
 * 
 */
public class MenuNavegacionComponent extends UIComponentBase {

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.component.UIComponentBase#encodeBegin(javax.faces.context.FacesContext)
	 */
	@Override
	public void encodeBegin(FacesContext context) throws IOException {

		HttpServletRequest request = (HttpServletRequest) context
				.getExternalContext().getRequest();
		HttpSession session = request.getSession(false);

		ElementoMenu elementoRaiz = (ElementoMenu) session
				.getAttribute(MenuNavegacionConstants.MENU_NAVEGACION_SESSION_KEY);

		ResponseWriter writer = context.getResponseWriter();

		writer.startElement("script", this);
		writer.writeAttribute("language", "javascript", null);
		writer.writeText("var opcionesMenu = new Array();\n", null);
		generarScriptMenu(writer, elementoRaiz, "opcionesMenu", 0);
		writer.endElement("script");

	}

	/**
	 * Genera el código JavaScript necesario para que el menú de navegación sea
	 * presentado en pantalla al usuario final.
	 * 
	 * @param writer
	 *            el objeto {@link ResponseWriter} en el que se escribir el
	 *            resultado.
	 * @param elementoRaiz
	 *            el elemento raíz que representa el menú de navegación del
	 *            usuario.
	 * @throws IOException
	 *             en caso de ocurrir un error al escribir en el
	 *             {@link ResponseWriter}.
	 */
	private void generarScriptMenu(ResponseWriter writer,
			ElementoMenu elementoRaiz, String prefijo, int indice)
			throws IOException {

		String elemento = prefijo + "[" + indice + "]";

		if (elementoRaiz != null) {
			writer.writeText(elemento + " = new OpcionMenu();", null);

			if (elementoRaiz.getId() != null) {
				writer.writeText(elemento + ".id = '" + elementoRaiz.getId()
						+ "';\n", null);
			}

			if (elementoRaiz.getEtiqueta() != null) {
				writer.writeText(elemento + ".descripcion = '"
						+ elementoRaiz.getEtiqueta() + "';\n", null);
			}

			if (elementoRaiz.getUrl() != null) {
				writer.writeText(elemento + ".ruta = '" + elementoRaiz.getUrl()
						+ "';\n", null);
			}

			if (elementoRaiz.getUrlIcono() != null) {
				writer.writeText(elemento + ".rutaIcono = '"
						+ elementoRaiz.getUrlIcono() + "';\n", null);
			}

			if (elementoRaiz.getSubElementosMenu() != null
					&& elementoRaiz.getSubElementosMenu().size() > 0) {
				writer.writeText(elemento + ".subopciones = new Array();\n\n",
						null);
				int i = 0;
				for (ElementoMenu elementoMenu : elementoRaiz
						.getSubElementosMenu()) {
					generarScriptMenu(writer, elementoMenu, elemento
							+ ".subopciones", i++);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.component.UIComponent#getFamily()
	 */
	@Override
	public String getFamily() {

		return MenuNavegacionConstants.MENU_NAVEGACION_FAMILY;
	}
}
