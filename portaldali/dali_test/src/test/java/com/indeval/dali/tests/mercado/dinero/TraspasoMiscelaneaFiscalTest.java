package com.indeval.dali.tests.mercado.dinero;

import static org.jboss.arquillian.graphene.Graphene.guardAjax;

import java.io.IOException;

import org.jboss.arquillian.graphene.page.Page;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.indeval.dali.pages.mercado.dinero.TraspasoMiscelaneaFiscalCapitalesPage;
import com.indeval.dali.tests.BaseCapturaOperacionesTest;

public class TraspasoMiscelaneaFiscalTest extends BaseCapturaOperacionesTest{
	
	
	@Page
	private TraspasoMiscelaneaFiscalCapitalesPage page;
	
	

	
	
	@Test(dataProvider = "dpTraspasoMiscelaneaFiscal",groups={"funcionales"})
	public void testFuncional(final String idFolioTraspasante,final String tipoCuentaTraspasante,final String idFolioReceptor,final String tipoCuentaReceptor,final String cantidadOperada,final String recepcion,final String precioAdquisicion,final String cliente,final String rfcCurp,final String extranjero,final String mensajeEsperado) throws Exception{
		
		goTo(TraspasoMiscelaneaFiscalCapitalesPage.class);

		final Boolean bRecepcion = new Boolean(recepcion);
		
		final Boolean bExtranjero = new Boolean(extranjero);
		
		
		if(bRecepcion)
			guardAjax(page.getRecepcion()).click();
		
		
		capturaTraspasanteEmisionReceptorMD_MXN(idFolioTraspasante, tipoCuentaTraspasante, idFolioReceptor, tipoCuentaReceptor, page);
		
		forceSendKeys(page.getCantidadOperada(),cantidadOperada);
		
		forceSendKeys(page.getPrecioAdquisicion(),precioAdquisicion);
		
		forceSendKeys(page.getCliente(),cliente);
		
		
				
		if(bExtranjero)
			page.getExtranjero().click();
		else{
			forceSendKeys(page.getRfcCurp(),rfcCurp);
		}
		
		
		
		Thread.sleep(300);
		
		page.getBtnGuardar().click();
		
		Thread.sleep(300);
		
		browser.switchTo().alert().accept();
		
		while(page.getMensajes().getText().isEmpty() ){
			Thread.sleep(500);
		}
	    
		String mensajeRecibido=page.getMensajes().getText();
		
		String[] mensajes=mensajeRecibido.split(":");
			
		Assert.assertEquals(mensajes[0], mensajeEsperado);

		validaACKBitacoraOperaciones(mensajes[1]);
		
	}

	

	
	
	@DataProvider(name = "dpTraspasoMiscelaneaFiscal")
	private String[][] getdpTraspasoMiscelaneaFiscal() throws IOException {
		return getDataPool("com/indeval/tests/mercado/dinero/dpTraspasoMiscelaneaFiscal.csv");        
	}
	
	
}
