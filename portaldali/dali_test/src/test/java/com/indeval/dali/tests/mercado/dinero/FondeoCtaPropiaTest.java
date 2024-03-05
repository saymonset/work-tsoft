package com.indeval.dali.tests.mercado.dinero;

import java.io.IOException;

import org.jboss.arquillian.graphene.page.Page;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.indeval.dali.pages.mercado.dinero.FondeoCtaPropiaPage;
import com.indeval.dali.tests.BaseCapturaOperacionesTest;

public class FondeoCtaPropiaTest extends BaseCapturaOperacionesTest{
	
	
	@Page
	private FondeoCtaPropiaPage page;
	
	
	
	
	
	@Test(dataProvider = "dpFondeoCtaPropia",groups={"funcionales"})
	public void testFuncional(final String idFolioTraspasante,final String tipoCuentaTraspasante,final String idFolioReceptor,final String tipoCuentaReceptor,final String cantidadOperada,final String mensajeEsperado) throws Exception{
		
		goTo(FondeoCtaPropiaPage.class);

		capturaTraspasanteEmisionReceptorMD_MXN(idFolioTraspasante, tipoCuentaTraspasante,  idFolioReceptor, tipoCuentaReceptor, page);
		
		forceSendKeys(page.getCantidadOperada(),cantidadOperada);
		
		
		
		page.getBtnGuardar().click();
		
		browser.switchTo().alert().accept();
		
		while(page.getMensajes().getText().isEmpty() ){
			Thread.sleep(500);
		}
		
		String mensajeRecibido=page.getMensajes().getText();
		
		String[] mensajes=mensajeRecibido.split(":");
			
		Assert.assertEquals(mensajes[0], mensajeEsperado);
		 
		validaACKBitacoraOperaciones(mensajes[1]);
		
	}

	
	
	
	
	@DataProvider(name = "dpFondeoCtaPropia")
	private String[][] getdpFondeoCtaPropia() throws IOException {
		return getDataPool("com/indeval/tests/mercado/dinero/dpFondeoCtaPropia.csv");        
	}
	
	
}
