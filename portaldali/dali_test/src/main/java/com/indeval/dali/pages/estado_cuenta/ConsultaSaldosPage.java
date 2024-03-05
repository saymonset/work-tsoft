package com.indeval.dali.pages.estado_cuenta;

import org.jboss.arquillian.graphene.page.Location;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.indeval.dali.pages.BasePage;


@Location("estadocuenta/consultaSaldoEfectivo.faces")
public class ConsultaSaldosPage extends BasePage {
	
	public Select getNaturaleza(){
		return getSelect("consultaTresCriterios:selectNaturaleza");
	}
	
	public Select getTipoCuenta(){
		return getSelect("consultaTresCriterios:selectTipoCuenta");
	}
	
	public WebElement getInstitucion(){
		return getInput("consultaTresCriterios:suggestInstitucion");
	}
	
	public WebElement getCuenta(){
		return getInput("consultaTresCriterios:suggestCuenta");
	}
	
	public Select getDivisa(){
		return getSelect("consultaTresCriterios:selectDivisa");
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
	
	public WebElement getTablaNoExistenResultados(){
		try{
			return getInput("consultaTresCriterios:noExistenResultados");
		}catch(Exception e){
			return null;
		}
	
	}
	
	
}
