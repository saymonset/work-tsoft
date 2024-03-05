package com.indeval.dali.pages.estado_cuenta;

import org.jboss.arquillian.graphene.page.Location;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.indeval.dali.pages.BasePage;

@Location("estadocuenta/consultaPosicion.faces")
public class ConsultaPosicionPage extends BasePage {


	public Select getNaturaleza(){
		return getSelect("consultaTresCriterios:selectNaturaleza");
	}
	
	public WebElement getInstitucion(){
		return getInput("consultaTresCriterios:suggestInstitucion");
	}
	
	public Select getTipoCuenta(){
		return getSelect("consultaTresCriterios:selectTipoCuenta");
	}
	
	public Select getTipoTenencia(){
		return getSelect("consultaTresCriterios:selectTipoTenencia");
	}
	
	
	public WebElement getCuenta(){
		return getInput("consultaTresCriterios:suggestCuenta");
	}
	
	public WebElement getNoDisponible(){
		return getInput("consultaTresCriterios:noDisponible");
	}
	
	public WebElement getTraspasarValores(){
		return getInput("consultaTresCriterios:traspasarValores");
	}
	
	public Select getPapelMercado(){
		return getSelect("consultaTresCriterios:selectPapelMercado");
	}
	
	public Select getMercado(){
		return getSelect("consultaTresCriterios:selectMercado");
	}
	
	public WebElement getTV(){
		return getInput("consultaTresCriterios:tv");
	}
	
	public WebElement getEmisora(){
		return getInput("consultaTresCriterios:suggest");
	}
	
	public WebElement getSerie(){
		return getInput("consultaTresCriterios:serie");
	}
	
	public WebElement getIsin(){
		return getInput("consultaTresCriterios:isin");
	}
	
	public Select getBoveda(){
		return getSelect("consultaTresCriterios:selectBoveda");
	}
	
	public WebElement getFecha(){
		return getInput("consultaTresCriterios:fechaInputDate");
	}
	
	public WebElement getBtnBuscar(){
		return getInput("consultaTresCriterios:botonBuscar");
	}
	
	public WebElement getBtnLimpiar(){
		return getInput("consultaTresCriterios:botonLimpiar");
	}
	
	public WebElement getTablaResultados(){
		return getInput("consultaTresCriterios:tablaResultados:tbody_element");
	}
	
	public WebElement getNoExistenResultados(){
		try{
			return getInput("consultaTresCriterios:noExistenResultados");
		}catch(Exception e){
			return null;
		}
	}
	
	
	
	
}
