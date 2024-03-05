package com.indeval.dali.pages;

import java.util.List;

import org.jboss.arquillian.drone.api.annotation.Drone;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;





public abstract class BasePage {

	
	@FindBy(id = "divWarnings")
    private WebElement divWarnings;
	
	@Drone
	private WebDriver browser;
	
	public WebElement getDivWarnings() {
		return divWarnings;
	}

	public WebElement getInput(String id){
		return browser.findElement(By.id(id));
	}
	
	public Select getSelect(String id){
		return new Select(browser.findElement(By.id(id)));
	}
	
	public List<WebElement> getElements(String id){
		 return browser.findElements(By.name(id));
	}
	
	
	
	//input[starts-with(@id, 'text-')

	
}
