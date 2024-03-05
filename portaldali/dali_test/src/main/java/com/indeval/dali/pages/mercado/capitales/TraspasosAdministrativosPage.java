package com.indeval.dali.pages.mercado.capitales;

import org.jboss.arquillian.graphene.page.Location;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


@Location("mercadoCapitales/traspasosadministrativos/traspasosAdministrativos.faces")
public class TraspasosAdministrativosPage extends MCBasePage {
	
	

	@FindBy(id = "daliForm:cantidadOperada")
	private WebElement cantidadOperada;
	
	
	
	public WebElement getCantidadOperada() {
		return cantidadOperada;
	}
	
}
