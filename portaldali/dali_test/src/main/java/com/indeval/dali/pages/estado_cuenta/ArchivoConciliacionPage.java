package com.indeval.dali.pages.estado_cuenta;

import org.jboss.arquillian.graphene.page.Location;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.indeval.dali.pages.BasePage;

@Location("mercadoDinero/archivoConciliacion/archivoConciliacion.faces")
public class ArchivoConciliacionPage extends BasePage {


	
	
	public WebElement getInstitucion(){
		return getInput("daliForm:idFolioTraspasante");
	}
	
	public WebElement getCuenta(){
		return getInput("daliForm:cuentaTraspasante");
	}
	
	
	public WebElement getTV(){
		return getInput("daliForm:tipoValor");
	}
	
	public WebElement getEmisora(){
		return getInput("daliForm:emisora");
	}
	
	public WebElement getSerie(){
		return getInput("daliForm:serie");
	}
	
	public WebElement getCupon(){
		return getInput("daliForm:cupon");
	}
	
	public WebElement getIsin(){
		return getInput("daliForm:isin");
	}
	
	public Select getBoveda(){
		return getSelect("daliForm:boveda");
	}
	

	
	
	
	
	public WebElement getBtnBuscar(){
		return getInput("daliForm:botonBuscar");
	}
	
	public WebElement getBtnEditar(){
		return getInput("daliForm:botonEditar");
	}
	
	public WebElement getBtnLimpiar(){
		return getInput("daliForm:botonLimpiar");
	}
	
	
	
	public WebElement getTableResultadoConciliaciones(){
		return getInput("daliForm:tblConciliaciones");
	}
	
	
	public WebElement getNoExistenResultados(){
		try{
		
			return getInput("noExistenResultados");
		}catch (NoSuchElementException e) {
			return null;
		}
	}
	
	
	
	
	
}
