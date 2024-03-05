package com.indeval.dali.pages;

import java.util.List;

import org.jboss.arquillian.graphene.page.Location;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

@Location("header.faces")
public class HeaderPage extends BasePage{
	
	@FindBy(id = "frmHeader:lblInstitucionFirmada")
    private WebElement lblInstitucionFirmada;
	
	@FindBy(id = "frmHeader:btnLogout")
    private WebElement btnLogout;
    
	@FindBy(id = "cambiarInstitucionLink")	
	private WebElement cambiarInstitucionLink;
	
	
	@FindBy(id = "frmHeader:comboInstitucion")	
	private Select comboInstitucion;

	
    public WebElement getBtnLogout() {
		return btnLogout;
	}
    
    public WebElement getLblInstitucionFirmada() {
    	return lblInstitucionFirmada;
    }
	
    public WebElement getCambiarInstitucionLink() {
		return cambiarInstitucionLink;
	}
    
    public Select getComboInstitucion() {
		return comboInstitucion;
	}
    
    
    public void cambiarInstitucion(String institucion) throws InterruptedException{
    	
    	cambiarInstitucionLink.click();
    	
    	List<WebElement> options=comboInstitucion.getOptions();
		
		for( int i=0; i<options.size(); i++){
			
			if(options.get(i).getText().startsWith(institucion)){
				comboInstitucion.selectByIndex(i);			
				break;
			}
		}
    	
    	Thread.sleep(1500);
    	
    			
    }
}
