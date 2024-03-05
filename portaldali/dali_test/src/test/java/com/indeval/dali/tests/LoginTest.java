package com.indeval.dali.tests;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class LoginTest extends BaseTest{
	
	
	
	@Test(dataProvider = "dpLogin",groups={"funcionales"})
	public void testLogin(String usuario,String password,String accion, String mensajeEsperado){
		
		String mensaje=login(usuario, password);
		if(!accion.equals("PASSWORD_INVALIDO"))
			logout();
		Assert.assertEquals(mensaje.trim(), mensajeEsperado);
		
	}

	
	
	
	
	@Test(dataProvider = "dpLoginMsgLocales",groups={"funcionales"})
	public void testLoginMensajesLocales(String usuario,String password,String accion, String mensajeEsperado){
		String mensaje=loginMenssagesError(usuario, password);		
		Assert.assertEquals(mensaje, mensajeEsperado);
	}
	
	
	
	
	@DataProvider(name = "dpLoginMsgLocales")
	private String[][] getdpCPLoginMsgLocales() throws IOException {
		return getDataPool("com/indeval/tests/dpCPLoginMsgLocales.csv");        
	}
	
	@DataProvider(name = "dpLogin")
	private String[][] getdpCPLogin() throws IOException {
		return getDataPool("com/indeval/tests/dpCPLogin.csv");        
	}
	
	
}
