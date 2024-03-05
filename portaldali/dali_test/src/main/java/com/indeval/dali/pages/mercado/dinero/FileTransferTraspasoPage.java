package com.indeval.dali.pages.mercado.dinero;

import org.jboss.arquillian.graphene.page.Location;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Location("mercadoDinero/fileTransfer/fileTransferTraspaso.faces")
public class FileTransferTraspasoPage extends MDBasePage {

	@FindBy(id = "daliForm:archivoOperaciones")
	private WebElement archivoOperaciones;

	@FindBy(id = "daliForm:iniciarProceso")
	private WebElement iniciarProceso;
	
	@FindBy(id = "daliForm:btnProcesar")
	private WebElement btnProcesar;
	
	
	@FindBy(id = "daliForm:btnCancelar")
	private WebElement btnCancelar;
	
	
	public WebElement getArchivoOperaciones() {
		return archivoOperaciones;
	}
	
	public WebElement getIniciarProceso() {
		return iniciarProceso;
	}
	
	
	public WebElement getBtnProcesar() {
		return btnProcesar;
	}
	
	public WebElement getBtnCancelar() {
		return btnCancelar;
	}
}
