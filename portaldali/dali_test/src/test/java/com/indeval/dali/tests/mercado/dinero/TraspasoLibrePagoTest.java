package com.indeval.dali.tests.mercado.dinero;

import static org.jboss.arquillian.graphene.Graphene.guardAjax;

import java.io.IOException;
import java.util.List;

import org.jboss.arquillian.graphene.page.Page;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.indeval.dali.pages.mercado.dinero.TraspasoLibrePagoPage;
import com.indeval.dali.tests.BaseCapturaOperacionesTest;

public class TraspasoLibrePagoTest extends BaseCapturaOperacionesTest{
	
	
	@Page
	private TraspasoLibrePagoPage page;
	
	


	
	@Test(dataProvider = "dpTraspasoLibrePago",groups={"funcionales"})
	public void testFuncional(final String idFolioTraspasante,final String tipoCuentaTraspasante,final String idFolioReceptor,final String tipoCuentaReceptor,final String cantidadOperada,final String comprador,final String tipoOperacion,final String plazoLiquidacion,final String mensajeEsperado) throws Exception{
		
		goTo(TraspasoLibrePagoPage.class);

		final Boolean bComprador = new Boolean(comprador);
		
		List<WebElement> opciones=page.getMismoDiaFechaValor();
		
		if(bComprador)
			guardAjax(page.getComprador()).click(); 
		
		if("MD".equalsIgnoreCase(tipoOperacion)){
			guardAjax(opciones.get(0)).click();
		}else if("FV".equalsIgnoreCase(tipoOperacion)){
			guardAjax(opciones.get(1)).click();
			
			List<WebElement> options=page.getComboPlazoLiquidacionHoras().getOptions();
			
			for( int i=0; i<options.size(); i++){
				
				if(options.get(i).getText().startsWith(plazoLiquidacion)){
					page.getComboPlazoLiquidacionHoras().selectByIndex(i);
					break;
				}
			}
			
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
	
	@DataProvider(name = "dpTraspasoLibrePago")
	private String[][] getdpTraspasoLibrePago() throws IOException {
		return getDataPool("com/indeval/tests/mercado/dinero/dpTraspasoLibrePago.csv");        
	}
	
	
}
