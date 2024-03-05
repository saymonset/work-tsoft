package com.indeval.dali.tests.mercado.capitales;

import static org.jboss.arquillian.graphene.Graphene.guardAjax;

import java.io.IOException;
import java.util.List;

import org.jboss.arquillian.graphene.page.Page;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.indeval.dali.pages.mercado.capitales.VentaPage;
import com.indeval.dali.tests.BaseCapturaOperacionesTest;

public class VentasTest extends BaseCapturaOperacionesTest{
	
	
	@Page
	private VentaPage page;
	
	

	
	@Test(dataProvider = "dpVentas",groups={"funcionales"})
	public void testFuncional(final String idFolioTraspasante,final String tipoCuentaTraspasante,final String idFolioReceptor,final String tipoCuentaReceptor,final String cantidadOperada,final String precioTitulo,final String comprador,final String tipoOperacion,final String plazoLiquidacion,final String mensajeEsperado) throws Exception{
		
		goTo(VentaPage.class);

		final Boolean bComprador = new Boolean(comprador);
		
		List<WebElement> opciones=page.getMismoDiaFechaValor();
		
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
		
		
		if(bComprador)
			page.getComprador().click();
		
		capturaTraspasanteEmisionReceptorMC_MXN(idFolioTraspasante, tipoCuentaTraspasante,  idFolioReceptor, tipoCuentaReceptor, page);
		
		forceSendKeys(page.getCantidadOperada(),cantidadOperada);
		
		forceSendKeys(page.getPrecioTitulo(),precioTitulo);
		
		page.getImporte().click();
		
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

	
	
	
	
	@DataProvider(name = "dpVentas")
	private String[][] getdpVentas() throws IOException {
		return getDataPool("com/indeval/tests/mercado/capitales/dpVentas.csv");        
	}
	
	
}
