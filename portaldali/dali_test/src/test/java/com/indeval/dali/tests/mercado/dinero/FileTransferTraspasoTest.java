package com.indeval.dali.tests.mercado.dinero;

import static org.jboss.arquillian.graphene.Graphene.guardAjax;
import static org.jboss.arquillian.graphene.Graphene.guardHttp;

import java.io.IOException;

import org.jboss.arquillian.graphene.page.Page;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.indeval.dali.pages.HeaderPage;
import com.indeval.dali.pages.mercado.dinero.FileTransferTraspasoPage;
import com.indeval.dali.tests.BaseCapturaOperacionesTest;

public class FileTransferTraspasoTest extends BaseCapturaOperacionesTest{
	
	
	@Page
	private FileTransferTraspasoPage page;
	
	@Page
	private HeaderPage headerPage;
	


	
	@Test(groups={"funcionales"})
	
	public void testFuncional(final String idFolioTraspasante,final String tipoCuentaTraspasante,final String idFolioReceptor,final String tipoCuentaReceptor,final String cantidadOperada,final String precioTitulo,final String comprador,final String tipoOperacion,final String plazoLiquidacion,final String mensajeEsperado) throws Exception{
		
		goTo(HeaderPage.class);
		
		headerPage.cambiarInstitucion(idFolioTraspasante);
	
		goTo(FileTransferTraspasoPage.class);
		
		String archivo="/home/omarnl/Escritorio/LAYOUT_MD_TRASPASOS.txt";
		
	//	int idInstitucionTraspasante=getInstitucionDao().getIdInstitucionByCve(idFolioTraspasante);
		
//		List<EmisionVO> emisiones=getPosicionNombradaDao().findEmisionesConPosicionMC( TipoCuenta.valueOf(tipoCuentaTraspasante), idInstitucionTraspasante,Divisa.MXN,4);
				
		page.getArchivoOperaciones().sendKeys(archivo);
		
		guardHttp(page.getIniciarProceso()).click();

		Thread.sleep(1500);
			
		guardAjax(page.getBtnProcesar()).click();
		
	}

	
	
	
	
	@DataProvider(name = "dpVentas")
	private String[][] getdpVentas() throws IOException {
		return getDataPool("com/indeval/tests/mercado/dinero/dpVentas.csv");        
	}
	
	
}
