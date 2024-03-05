package com.indeval.dali.pages;

import static org.jboss.arquillian.graphene.Graphene.guardHttp;

import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.page.Location;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Location("login.faces")
public class LoginPage extends BasePage{
	
	
	
	@FindBy(id = "loginForm:usuario")
    private WebElement txtUserName;
    
    @FindBy(id = "loginForm:password")
    private WebElement txtPassword;
    
    
	@FindBy(id = "loginForm:btnDeterminaLogin")
	private WebElement loginButton;
	
	@FindBy(id = "topFrame")
	private WebElement fraTop;
	
	@Drone
	private WebDriver browser;
	
	public String login(String userName, String password) {
		
		return login( userName,  password, false);
    }
	
	public String loginMessageError(String userName, String password) {
		
		return login( userName,  password, true);
    }
	
	
	private String login(String userName, String password,boolean toLocalValidate) {
		this.txtUserName.clear();
        this.txtUserName.sendKeys(userName);
        this.txtPassword.clear();
        this.txtPassword.sendKeys(password);
        
        if(!toLocalValidate){
        	guardHttp(loginButton).click();
        }else{
        	loginButton.click();
        }
        String msg=null;
        try{
        	
	        WebElement wMensajes=getInput("loginForm:panelMensajes");
	        Thread.sleep(100);
	        WebElement mensajes=wMensajes.findElement(By.id("mensajes"));
	        
	        msg=mensajes.getText().trim();	        
	        
        }catch(NoSuchElementException nse){
        	browser.switchTo().defaultContent();
        	browser.switchTo().frame(fraTop); 
			 WebElement mensajes=getInput("frmHeader:lblInstitucionFirmada");
			 msg=mensajes.getText().trim();	        
		     
        } catch (InterruptedException e) {
			msg=e.getLocalizedMessage();
		}
        
        return msg;
	}
	
	
}
