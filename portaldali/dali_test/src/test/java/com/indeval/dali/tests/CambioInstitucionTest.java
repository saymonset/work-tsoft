package com.indeval.dali.tests;

import java.io.IOException;

import org.jboss.arquillian.graphene.page.Page;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.indeval.dali.pages.HeaderPage;


public class CambioInstitucionTest extends BaseTest{
	
	@Page
	private HeaderPage page;
	
	@Test(dataProvider = "dpCambioInstitucion",groups={"funcionales"})
	public void testLogin(String usuario,String password, String institucion,String accion,String institucion_esperada) throws InterruptedException{
		
		String mensaje=login(usuario, password);
		
		Assert.assertEquals(mensaje.trim(), institucion);
		
		goTo(HeaderPage.class);
		
		page.cambiarInstitucion(accion);
		
		goTo(HeaderPage.class);
		
		String institucion_firmada=page.getLblInstitucionFirmada().getText().trim();	        
		
		Assert.assertEquals(institucion_firmada, institucion_esperada);

		logout();
	}

	
	@DataProvider(name = "dpCambioInstitucion")
	private String[][] getdpCPLogin() throws IOException {
		return getDataPool("com/indeval/tests/dpCambioInstitucion.csv");        
	}
	
	
}
