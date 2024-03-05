package com.indeval.dali.pages.estatus_operaciones;

import org.jboss.arquillian.graphene.page.Location;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.indeval.dali.pages.mercado.dinero.MDBasePage;


@Location("match/matchOp.faces")
public class ConsultaValoresMatchPage extends MDBasePage {

	
	
	public WebElement getIdFolioParticipante(){
		return getInput("daliForm:idFolioParticipante");
	}
	
	
	public WebElement getCuentaParticipante(){
		return getInput("daliForm:cuentaParticipante");
	}
	
	public WebElement getIdFolioContraparte(){
		return getInput("daliForm:idFolioContraparte");
	}
	
	public WebElement getCuentaContraparte(){
		return getInput("daliForm:cuentaContraparte");
	}
	
	public Select getSelectPapelMercado(){
		return getSelect("daliForm:selectPapelMercado");
	}
	
	public Select getSelectMercado(){
		return getSelect("daliForm:selectMercado");
	}
	
	public WebElement getEstatus(){
		return getInput("daliForm:selectEstatus");
	}
	
	public WebElement getTipoInstruccion(){
		return getInput("daliForm:tipoOp");
	}
	
	public WebElement getFechaRegistro(){
		return getInput("daliForm:fechaInputDate");
	}
	
	public WebElement getFechaLiquidacion(){
		return getInput("daliForm:fechaLiquidacionInputDate");
	}	
	
	public Select getSelectRol(){
		return getSelect("daliForm:selectRol");
	}
	
	
	public WebElement getTV(){
		return getInput("daliForm:tv");
	}
	
	public WebElement getEmisora(){
		return getInput("daliForm:emisora");
	}
	
	
	public WebElement getSerie(){
		return getInput("daliForm:serie");
	}
	
	public Select getSelectBoveda(){
		return getSelect("daliForm:boveda");
	}
	
	public WebElement getFolioUsuario(){
		return getInput("daliForm:folioUsuario");
	}
	
	public Select getSelectTipoMensaje(){
		return getSelect("daliForm:selectTipoMensaje");
	}
	
	public Select getRemitente(){
		return getSelect("daliForm:remitente");
	}
	
	public WebElement getOrigen(){
		return getInput("daliForm:origen");
	}
	
	public Select getError(){
		return getSelect("daliForm:error");
	}
	
	public WebElement getCantidad(){
		return getInput("daliForm:cantidad");
	}
	
	public WebElement getMonto(){
		return getInput("daliForm:monto");
	}
	
	public Select getDivisa(){
		return getSelect("daliForm:divisa");
	}
	
	public Select getBovedaEfectivo(){
		return getSelect("daliForm:bovedaEfectivo");
	}
	
	public WebElement getFolioControl(){
		return getInput("daliForm:folioControl");
	}
	
	
	public WebElement getReferenciaPaquete(){
		return getInput("daliForm:referenciaPaquete");
	}
	
	public WebElement getBtnLimpiar(){
		return getInput("daliForm:btnLimpiar");
	}
	
	
	public WebElement getBtnBuscar(){
		return getInput("daliForm:btnBuscar");
	}
	
	public WebElement getBtnEditar(){
		return getInput("daliForm:btnEditar");
	}
}
