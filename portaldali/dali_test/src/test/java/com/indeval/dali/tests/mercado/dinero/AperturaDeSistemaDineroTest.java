package com.indeval.dali.tests.mercado.dinero;

import static org.jboss.arquillian.graphene.Graphene.guardAjax;

import java.io.IOException;
import java.util.List;

import org.jboss.arquillian.graphene.page.Page;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.indeval.dali.pages.mercado.dinero.AperturaDeSistemaDineroPage;
import com.indeval.dali.tests.BaseCapturaOperacionesTest;

public class AperturaDeSistemaDineroTest extends BaseCapturaOperacionesTest{
	
	
	@Page
	private AperturaDeSistemaDineroPage page;
	
	
	

	
	@Test(dataProvider = "dpAperturaDeSistema",groups={"funcionales"})
	public void testFuncional(final String idFolioTraspasante,final String tipoCuentaTraspasante,final String idFolioReceptor,final String tipoCuentaReceptor,final String cantidadOperada,final String recepcion,final String tipoApertura,final String mensajeEsperado) throws Exception{
		
		goTo(AperturaDeSistemaDineroPage.class);

		final Boolean bRecepcion = new Boolean(recepcion);
		
		List<WebElement> opciones=page.getTipoApertura();
		
		if(bRecepcion)
			page.getRecepcion().click(); 
		
		if("ACT".equalsIgnoreCase(tipoApertura)){
			guardAjax(opciones.get(0)).click();
		}else if("ACR".equalsIgnoreCase(tipoApertura)){
			guardAjax(opciones.get(1)).click();
		}
		
		capturaTraspasanteEmisionReceptorMD_MXN(idFolioTraspasante, tipoCuentaTraspasante,  idFolioReceptor, tipoCuentaReceptor, page);
		
		forceSendKeys(page.getCantidadOperada(),cantidadOperada);
		
		Thread.sleep(300);
		
		page.getBtnGuardar().click();
		
		Thread.sleep(300);
		
		browser.switchTo().alert().accept();
		
		while(page.getMensajes().getText().isEmpty() ){
			Thread.sleep(300);
		}
		
		
		String mensajeRecibido=page.getMensajes().getText();
		
		String[] mensajes=mensajeRecibido.split(":");
			
		Assert.assertEquals(mensajes[0], mensajeEsperado);
		
		validaACKBitacoraOperaciones(mensajes[1]);
		
	}

	

	
	
	@DataProvider(name = "dpAperturaDeSistema")
	private String[][] dpAperturaDeSistema() throws IOException {
		return getDataPool("com/indeval/tests/mercado/dinero/dpAperturaDeSistema.csv");        
	}
	
	
}
