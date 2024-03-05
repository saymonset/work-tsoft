package com.indeval.dali.pages.mercado.capitales;

import java.util.List;

import org.jboss.arquillian.graphene.page.Location;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


@Location("mercadoCapitales/operacion/aperturaDeSistemaCapitales.faces")
public class AperturaDeSistemaCapitalesPage extends MCBasePage {
	
	@FindBy(id = "daliForm:recepcion")
	private WebElement recepcion;

	@FindBy(id = "daliForm:cantidad")
	private WebElement cantidadOperada;
	
	@FindBy(id = "daliForm:tipoApertura")
	private WebElement tipoApertura;

	public WebElement getRecepcion() {
		return recepcion;
	}

	public WebElement getCantidadOperada() {
		return cantidadOperada;
	}

	public List<WebElement> getTipoApertura() {
		List<WebElement> opciones=tipoApertura.findElements(By.tagName("input"));
		
		return opciones;
	}
	
	
	
}
