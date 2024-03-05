package com.indeval.dali.pages.mercado.dinero;

import org.jboss.arquillian.graphene.page.Location;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Location("mercadoDinero/operacion/fondeoCtaPropia.faces?pantalla=fondeoCtaPropia")
public class FondeoCtaPropiaPage extends MDBasePage {
	
	@FindBy(id = "daliForm:cantidadOperada")
	private WebElement cantidadOperada;
	

	public WebElement getCantidadOperada() {
		return cantidadOperada;
	}
	
	
}
