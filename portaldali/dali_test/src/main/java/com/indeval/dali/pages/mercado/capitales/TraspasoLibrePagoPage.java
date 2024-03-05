package com.indeval.dali.pages.mercado.capitales;

import java.util.List;

import org.jboss.arquillian.graphene.page.Location;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

@Location("mercadoCapitales/operacion/traspasoLibrePago.faces")
public class TraspasoLibrePagoPage extends MCBasePage {

	@FindBy(id = "daliForm:mismoDiaFechaValor")
	private WebElement mismoDiaFechaValor;
	
	@FindBy(id = "daliForm:comprador")
	private WebElement comprador;
	
	@FindBy(id = "daliForm:cantidadOperada")
	private WebElement cantidadOperada;
	
	@FindBy(id = "daliForm:comboPlazoLiquidacionHoras")	
	private Select comboPlazoLiquidacionHoras;
	
	public WebElement getComprador() {
		return comprador;
	}

	public WebElement getCantidadOperada() {
		return cantidadOperada;
	}

	public List<WebElement> getMismoDiaFechaValor() {
		List<WebElement> opciones=mismoDiaFechaValor.findElements(By.tagName("input"));
		
		return opciones;
	}

	public Select getComboPlazoLiquidacionHoras() {
		return comboPlazoLiquidacionHoras;
	}
	
	
	
}
