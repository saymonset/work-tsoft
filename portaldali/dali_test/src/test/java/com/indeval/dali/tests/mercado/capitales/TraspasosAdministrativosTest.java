package com.indeval.dali.tests.mercado.capitales;

import java.io.IOException;

import org.jboss.arquillian.graphene.page.Page;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.indeval.dali.pages.mercado.capitales.TraspasosAdministrativosPage;
import com.indeval.dali.tests.BaseCapturaOperacionesTest;

public class TraspasosAdministrativosTest extends BaseCapturaOperacionesTest{
	
	
	@Page
	private TraspasosAdministrativosPage page;
	
	
	
	
	
	

	
	@Test(dataProvider = "dpTraspasoAdministrativo",groups={"funcionales"})
	public void testFuncional(final String idFolioTraspasante,final String tipoCuentaTraspasante,final String idFolioReceptor,final String tipoCuentaReceptor,final String cantidadOperada,final String mensajeEsperado) throws Exception{
		
		goTo(TraspasosAdministrativosPage.class);
		
		capturaTraspasanteEmisionReceptorMC_MXN(idFolioTraspasante, tipoCuentaTraspasante,  idFolioReceptor, tipoCuentaReceptor, page);
		
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

	

	
	
	@DataProvider(name = "dpTraspasoAdministrativo")
	private String[][] dpTraspasoAdministrativo() throws IOException {
		return getDataPool("com/indeval/tests/mercado/capitales/dpTraspasoAdministrativo.csv");        
	}
	
	
}
