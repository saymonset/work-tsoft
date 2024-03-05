package com.indeval.dali.pages.mercado;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import com.indeval.dali.pages.BasePage;

public class CapturaBasePage extends BasePage{

	@FindBy(id = "daliForm:selectPantalla")	
	private Select comboInstitucion;
	
	@FindBy(id = "daliForm:idFolioTraspasante")
	private WebElement idFolioTraspasante;
	
	@FindBy(id = "daliForm:cuentaTraspasante")
	private WebElement cuentaTraspasante;

	@FindBy(id = "daliForm:nombreInstitucionTraspasante")
	private WebElement nombreInstitucionTraspasante;
	
	@FindBy(id = "daliForm:idFolioReceptor")
	private WebElement idFolioReceptor;
	
	@FindBy(id = "daliForm:cuentaReceptor")
	private WebElement cuentaReceptor;
	
	
	@FindBy(id = "daliForm:nombreInstitucionReceptor")
	private WebElement nombreInstitucionReceptor;

	@FindBy(id = "daliForm:tipoValor")
	private WebElement tipoValor;
	
	@FindBy(id = "daliForm:emisora")
	private WebElement emisora;
	
	@FindBy(id = "daliForm:serie")
	private WebElement serie;	
	
	@FindBy(id = "daliForm:cupon")
	private WebElement cupon;
	
	
	@FindBy(id = "daliForm:isin")	
	private WebElement isin;
	
	@FindBy(id = "daliForm:boveda")	
	private Select boveda;
	
	@FindBy(id = "daliForm:diasVigentes")	
	private WebElement diasVigentes;
	
	@FindBy(id = "daliForm:saldoDisponible")	
	private WebElement saldoDisponible;
	
	@FindBy(id = "daliForm:simulado")	
	private WebElement simulado;
	
	@FindBy(id = "daliForm:fechaConcertacion")	
	private WebElement fechaConcertacion;
	
	
	@FindBy(id = "buscarPosicionLink")	
	private WebElement buscarPosicionLink;
	
	@FindBy(id = "limpiarPosicionLink")	
	private WebElement limpiarPosicionLink;
	
	
	@FindBy(id = "daliForm:btnGuardar")
	private WebElement btnGuardar;
	
	@FindBy(id = "daliForm:btnLimpiar")
	private WebElement btnLimpiar;
	
	@FindBy(id = "daliForm:mensajes")
	private WebElement mensajes;
	
	
	
	public WebElement getIdFolioTraspasante() {
		return idFolioTraspasante;
	}

	public WebElement getCuentaTraspasante() {
		return cuentaTraspasante;
	}

	public WebElement getIdFolioReceptor() {
		return idFolioReceptor;
	}

	public WebElement getCuentaReceptor() {
		return cuentaReceptor;
	}

	public Select getComboInstitucion() {
		return comboInstitucion;
	}

	public WebElement getBtnGuardar() {
		return btnGuardar;
	}

	public WebElement getBtnLimpiar() {
		return btnLimpiar;
	}

	public WebElement getMensajes() {
		return mensajes;
	}

	public WebElement getTipoValor() {
		return tipoValor;
	}

	public WebElement getEmisora() {
		return emisora;
	}

	public WebElement getSerie() {
		return serie;
	}

	public WebElement getCupon() {
		return cupon;
	}
	
	public WebElement getFechaConcertacion() {
		return fechaConcertacion;
	}

	public WebElement getIsin() {
		return isin;
	}

	public Select getBoveda() {
		return boveda;
	}

	public WebElement getDiasVigentes() {
		return diasVigentes;
	}

	public WebElement getSaldoDisponible() {
		return saldoDisponible;
	}

	public WebElement getSimulado() {
		return simulado;
	}

	public WebElement getBuscarPosicionLink() {
		return buscarPosicionLink;
	}

	public WebElement getLimpiarPosicionLink() {
		return limpiarPosicionLink;
	}

	public WebElement getNombreInstitucionTraspasante() {
		return nombreInstitucionTraspasante;
	}

	public WebElement getNombreInstitucionReceptor() {
		return nombreInstitucionReceptor;
	}
	
	
	
}
