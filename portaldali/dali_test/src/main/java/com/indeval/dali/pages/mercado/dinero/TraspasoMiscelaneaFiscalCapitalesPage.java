package com.indeval.dali.pages.mercado.dinero;

import org.jboss.arquillian.graphene.page.Location;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;


@Location("mercadoDinero/operacion/traspasoMiscelaneaFiscal.faces?pantalla=traspasoMiscelaneaFiscal")
public class TraspasoMiscelaneaFiscalCapitalesPage extends MDBasePage {
	
	@FindBy(id = "daliForm:recepcion")
	private WebElement recepcion;

	@FindBy(id = "daliForm:cantidad")
	private WebElement cantidadOperada;
	
	@FindBy(id = "daliForm:valorEn")
	private Select valorEn;
		
	@FindBy(id = "daliForm:fechaAdquisicionInputDate")
	private WebElement fechaAdquisicionInputDate;
	
	@FindBy(id = "daliForm:precioAdquisicion")
	private WebElement precioAdquisicion;
	
	@FindBy(id = "daliForm:cliente")
	private WebElement cliente;
	
	@FindBy(id = "daliForm:rfcCurp")
	private WebElement rfcCurp;
	
	@FindBy(id = "daliForm:extranjero")
	private WebElement extranjero;
	
	

	public WebElement getRecepcion() {
		return recepcion;
	}

	public WebElement getCantidadOperada() {
		return cantidadOperada;
	}

	public Select getValorEn() {
		return valorEn;
	}

	public WebElement getPrecioAdquisicion() {
		return precioAdquisicion;
	}

	public WebElement getCliente() {
		return cliente;
	}

	public WebElement getRfcCurp() {
		return rfcCurp;
	}

	public WebElement getExtranjero() {
		return extranjero;
	}

	public WebElement getFechaAdquisicionInputDate() {
		return fechaAdquisicionInputDate;
	}

	
	
	
	
}
