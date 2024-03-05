package com.indeval.dali.tests.estatus_operaciones;

import static org.jboss.arquillian.graphene.Graphene.guardAjax;
import static org.testng.Assert.assertEquals;

import java.util.List;

import org.jboss.arquillian.graphene.page.Page;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.indeval.dali.pages.estatus_operaciones.ConsultaValoresMatchPage;
import com.indeval.dali.tests.BaseConsultaOperacionesTest;

public class ConsultaValoresMatchTest extends BaseConsultaOperacionesTest{
	
	@Page
	private ConsultaValoresMatchPage page;
	
	
	public void confirmaFolioControl( String folioControl) throws InterruptedException {
		
		goTo(ConsultaValoresMatchPage.class);	
		
		forceSendKeys(page.getFolioControl(),folioControl);
		
		guardAjax(page.getBtnBuscar()).click();
				
		String folioControlEncontrado=getFolioControlByRow(0);
		
		assertEquals(folioControl, folioControlEncontrado);
		
		confirmarFolioControl(folioControl);
		
	}
		
	
	public String getStatusFolioControl( String folioControl) throws InterruptedException {
		goTo(ConsultaValoresMatchPage.class);	
		
		forceSendKeys(page.getFolioControl(),folioControl);
		
		guardAjax(page.getBtnBuscar()).click();
		
		String folioControlEncontrado=getFolioControlByRow(0);
		
		assertEquals(folioControl, folioControlEncontrado);
		
		return getStatusByRow(0);
	}
	
	
	
	
	
	private void confirmarFolioControl(String folioControl) throws InterruptedException{
		
		if(folioControl!=null){
			String status=getStatusByRow(0);
			if("SM".equalsIgnoreCase(status)){
				WebElement check=getCheckConfByRow(0);
				guardAjax(check).click();
				Thread.sleep(500);
			}
		}
	}
	
	
	private String getFolioControlByRow(int row){
		String folioControl=null;
		WebElement resultado=browser.findElement(By.id("tblResultado"));
		if(resultado!=null){
			List<WebElement> tableRows=resultado.findElements(By.tagName("tr"));
			if(tableRows!=null){
				folioControl=tableRows.get(4+row).findElement(By.tagName("td")).getText().trim();
			}
		}
		return folioControl;
	}
	
	private String getStatusByRow(int row){
		String status=null;
		WebElement resultado=browser.findElement(By.id("tblResultado"));
		if(resultado!=null){
			List<WebElement> tableRows=resultado.findElements(By.tagName("tr"));
			if(tableRows!=null){
				status=tableRows.get(5+row).findElement(By.tagName("td")).getText().trim();
			}
		}
		return status;
	}

	
	
	
	private WebElement getCheckConfByRow(int row){
		WebElement we=null;
		WebElement resultado=browser.findElement(By.id("tblResultado"));
		if(resultado!=null){
			List<WebElement> tableRows=resultado.findElements(By.tagName("tr"));
			if(tableRows!=null){
				we=tableRows.get(5+row).findElement(By.id("daliForm:t_dataList:0:img_conf"));
			}
		}
		return we;
	}
	
	
	
	public void setConsultaValoresMatchPage(ConsultaValoresMatchPage page) {
		this.page = page;
	}
	
	
}
