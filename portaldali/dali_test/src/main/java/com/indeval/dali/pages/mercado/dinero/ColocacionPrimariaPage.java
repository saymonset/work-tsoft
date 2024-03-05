package com.indeval.dali.pages.mercado.dinero;

import java.util.List;

import org.jboss.arquillian.graphene.page.Location;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

@Location("mercadoDinero/operacion/colocacionPrimaria.faces")
public class ColocacionPrimariaPage extends MDBasePage {

	
	
	@FindBy(id = "daliForm:mismoDiaFechaValor")
	private WebElement mismoDiaFechaValor;
	
	
	@FindBy(id = "daliForm:cantidadOperada")
	private WebElement cantidadOperada;
		
	@FindBy(id = "daliForm:valorEn")
	private WebElement valorEn;
	
	@FindBy(id = "daliForm:precioTitulo")
	private WebElement precioTitulo;
	
	@FindBy(id = "daliForm:compra")
	private WebElement comprador;
	
	
	@FindBy(id = "daliForm:importe")
	private WebElement importe;
	
	@FindBy(id = "daliForm:plazoLiquidacionHoras" )	
	private Select comboPlazoLiquidacionHoras;
	

	public WebElement getCantidadOperada() {
		return cantidadOperada;
	}

	public WebElement getValorEn() {
		return valorEn;
	}

	public WebElement getPrecioTitulo() {
		return precioTitulo;
	}

	public WebElement getComprador() {
		return comprador;
	}

	public WebElement getImporte() {
		return importe;
	}

	public List<WebElement> getMismoDiaFechaValor() {
		
		List<WebElement> opciones=mismoDiaFechaValor.findElements(By.tagName("input"));
		
		return opciones;
	}

	public Select getComboPlazoLiquidacionHoras() {
		return comboPlazoLiquidacionHoras;
	}

	
	
	
	

	
	
}
