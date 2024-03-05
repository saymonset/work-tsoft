package com.indeval.dali.tests;

import java.util.List;

import org.jboss.arquillian.graphene.page.Page;
import org.testng.Assert;

import com.indeval.dali.constans.Divisa;
import com.indeval.dali.constans.TipoCuenta;
import com.indeval.dali.pages.estado_cuenta.ArchivoConciliacionPage;
import com.indeval.dali.pages.estado_cuenta.ConsultaEdoCtaEfectivoPage;
import com.indeval.dali.pages.estado_cuenta.ConsultaEdoCtaValoresPage;
import com.indeval.dali.pages.estado_cuenta.ConsultaMovimientosEfectivoPage;
import com.indeval.dali.pages.estado_cuenta.ConsultaMovimientosValoresPage;
import com.indeval.dali.pages.estado_cuenta.ConsultaPosicionPage;
import com.indeval.dali.pages.estado_cuenta.ConsultaSaldosPage;
import com.indeval.dali.pages.estatus_operaciones.ConsultaValoresMatchPage;
import com.indeval.dali.pages.mercado.CapturaBasePage;
import com.indeval.dali.tests.estado_cuenta.ConsultaArchivoConciliacionesTest;
import com.indeval.dali.tests.estado_cuenta.ConsultaEdoCtaEfectivoTest;
import com.indeval.dali.tests.estado_cuenta.ConsultaEdoCtaValoresTest;
import com.indeval.dali.tests.estado_cuenta.ConsultaMovimientosEfectivoTest;
import com.indeval.dali.tests.estado_cuenta.ConsultaMovimientosValoresTest;
import com.indeval.dali.tests.estado_cuenta.ConsultaPosicionTest;
import com.indeval.dali.tests.estado_cuenta.ConsultaSaldosTest;
import com.indeval.dali.tests.estatus_operaciones.ConsultaValoresMatchTest;
import com.indeval.dali.vo.EmisionVO;

public class BaseCapturaOperacionesTest extends BaseTest{
	
	@Page
	private ConsultaValoresMatchPage consultaValoresMatchPage;
	
	@Page
	private	ConsultaPosicionPage consultaPosicionValoresPage;
	
	@Page
	private	ConsultaSaldosPage consultaSaldosPage;
	
	@Page
	private ConsultaMovimientosValoresPage consultaMovimientosValoresPage;
	
	@Page
	private ConsultaMovimientosEfectivoPage consultaMovimientosEfectivoPage;
	
	@Page
	private ConsultaEdoCtaEfectivoPage consultaEdoCtaEfectivoPage;
		
	@Page
	private ConsultaEdoCtaValoresPage consultaEdoCtaValoresPage;
	
	@Page
	private ArchivoConciliacionPage archivoConciliacionPage;
	
	
	private ConsultaValoresMatchTest consultaValoresMatchTest;
	
	private ConsultaPosicionTest consultaPosicionValoresTest;
	
	private ConsultaSaldosTest consultaSaldosTest;
	
	
	private ConsultaMovimientosValoresTest consultaMovimientosValoresTest; 
	
	private ConsultaMovimientosEfectivoTest consultaMovimientosEfectivoTest;
	
	private ConsultaEdoCtaEfectivoTest consultaEdoCtaEfectivoTest;
	
	private ConsultaEdoCtaValoresTest consultaEdoCtaValoresTest;
	
	private ConsultaArchivoConciliacionesTest consultaArchivoConciliacionesTest;
	
	
	public void capturaTraspasanteEmisionReceptorMC_MXN(final String idFolioTraspasante,final String tipoCuentaTraspasante,final String idFolioReceptor,final String tipoCuentaReceptor,CapturaBasePage page) throws Exception{
		capturaTraspasanteEmisionReceptorMC(idFolioTraspasante, tipoCuentaTraspasante,  idFolioReceptor, tipoCuentaReceptor, "MXN", page);
	}
	public void capturaTraspasanteEmisionReceptorMD_MXN(final String idFolioTraspasante,final String tipoCuentaTraspasante,final String idFolioReceptor,final String tipoCuentaReceptor,CapturaBasePage page) throws Exception{
		capturaTraspasanteEmisionReceptorMD(idFolioTraspasante, tipoCuentaTraspasante,  idFolioReceptor, tipoCuentaReceptor, "MXN", page);
	}
	
	public void capturaTraspasanteEmisionReceptorMC(final String idFolioTraspasante,final String tipoCuentaTraspasante,final String idFolioReceptor,final String tipoCuentaReceptor,final String sDivisa,CapturaBasePage page) throws Exception{
	
		int idInstitucionTraspasante=getInstitucionDao().getIdInstitucionByCve(idFolioTraspasante);
		
		List<EmisionVO> emisiones=getPosicionNombradaDao().findEmisionesConPosicionMC( TipoCuenta.valueOf(tipoCuentaTraspasante), idInstitucionTraspasante,Divisa.valueOf(sDivisa),2);
		
		int idInstitucionReceptor=getInstitucionDao().getIdInstitucionByCve(idFolioReceptor);
		
		List<String> cuentas=getCuentaNombradaDao().findCuentas(TipoCuenta.valueOf(tipoCuentaReceptor), idInstitucionReceptor);
		
		String cuentaReceptor=cuentas.get(0);
		
		capturaTraspasanteEmisionReceptor(idFolioTraspasante,idFolioReceptor,cuentaReceptor,emisiones.get(0), page);
	}

	public void capturaTraspasanteEmisionReceptorMD(final String idFolioTraspasante,final String tipoCuentaTraspasante,final String idFolioReceptor,final String tipoCuentaReceptor,final String sDivisa,CapturaBasePage page) throws Exception{
		
		int idInstitucionTraspasante=getInstitucionDao().getIdInstitucionByCve(idFolioTraspasante);
		
		List<EmisionVO> emisiones=getPosicionNombradaDao().findEmisionesConPosicionMD( TipoCuenta.valueOf(tipoCuentaTraspasante), idInstitucionTraspasante,Divisa.valueOf(sDivisa),2);
		
		int idInstitucionReceptor=getInstitucionDao().getIdInstitucionByCve(idFolioReceptor);
		
		List<String> cuentas=getCuentaNombradaDao().findCuentas(TipoCuenta.valueOf(tipoCuentaReceptor), idInstitucionReceptor);
		
		String cuentaReceptor=cuentas.get(0);
		
		capturaTraspasanteEmisionReceptor(idFolioTraspasante,idFolioReceptor,cuentaReceptor,emisiones.get(0), page);
	}

	
	
	public void capturaTraspasanteEmisionReceptor(final String idFolioTraspasante,final String idFolioReceptor,String cuentaReceptor,final EmisionVO emisionVO,CapturaBasePage page) throws Exception{
		capturaTraspasanteEmisionReceptor(idFolioTraspasante, emisionVO.getCuenta(), emisionVO.getTv(), emisionVO.getEmisora(), emisionVO.getSerie(), emisionVO.getCupon(), idFolioReceptor, cuentaReceptor, page);
	}
	
	
	public void capturaTraspasanteEmisionReceptor(final String idFolioTraspasante,final String cuentaTraspasante, final String tv, final String emisora,final String serie, final String cupon, final String idFolioReceptor, final String cuentaReceptor, CapturaBasePage page) throws Exception{
		

		forceSendKeys(page.getIdFolioTraspasante(),idFolioTraspasante);
		
		forceSendKeys(page.getCuentaTraspasante(),cuentaTraspasante);
		
		forceSendKeys(page.getTipoValor(),tv);
		
		forceSendKeys(page.getEmisora(),emisora);
		
		forceSendKeys(page.getSerie(),serie);
		
		page.getCupon().click();
		
		if(page.getCupon().getAttribute("value").isEmpty()){
			
			forceSendKeys(page.getCupon(),cupon);
			
		}
		
		forceSendKeys(page.getIdFolioReceptor(),idFolioReceptor);
		
		forceSendKeys(page.getCuentaReceptor(),cuentaReceptor);
		
	}
	
	
	public void validaACKBitacoraOperaciones(String folio_control) throws InterruptedException{
		
		String status=null;
		int validacion_status=10;
		int val=0;
		while((status==null||!"ACK".equalsIgnoreCase(status))&&val<validacion_status){
			
			
			status=getBitacoraOperacionesDao().getEstatusRegistro(Integer.valueOf(folio_control.trim()));
			
			if(status==null||!"ACK".equalsIgnoreCase(status)){
				Thread.sleep(400);
				validacion_status++;
			}
			
		}
		
		Assert.assertEquals(status, "ACK");
		
	}
	
	
	
	public void init_ConsultaValoresMatchTest(){
		
		if(consultaValoresMatchTest==null){
			consultaValoresMatchTest=new ConsultaValoresMatchTest();
			
			consultaValoresMatchTest.init();
			
			consultaValoresMatchTest.setBrowser(browser);
			
			consultaValoresMatchTest.setHeaderPage(getHeaderPage());
			
			consultaValoresMatchTest.setConsultaValoresMatchPage(consultaValoresMatchPage);
		}
	}
	
	
	public void init_ConsultaPosicionValoresTest(){
		
		if(consultaPosicionValoresTest==null){
			consultaPosicionValoresTest=new ConsultaPosicionTest();
			
			consultaPosicionValoresTest.init();
			
			consultaPosicionValoresTest.setBrowser(browser);
			
			consultaPosicionValoresTest.setHeaderPage(getHeaderPage());
			
			consultaPosicionValoresTest.setConsultaPosicionValoresPage(consultaPosicionValoresPage);
		}
	}
	
	
	
	public void init_consultaSaldosTest(){
		
		if(consultaSaldosTest==null){
			consultaSaldosTest=new ConsultaSaldosTest();
			
			consultaSaldosTest.init();
			
			consultaSaldosTest.setBrowser(browser);
			
			consultaSaldosTest.setHeaderPage(getHeaderPage());
			
			consultaSaldosTest.setConsultaSaldosPage(consultaSaldosPage);
		}
	}

	
	public void init_consultaMovimientosValoresTest(){
		
		if(consultaMovimientosValoresTest==null){
			consultaMovimientosValoresTest=new ConsultaMovimientosValoresTest();
			
			consultaMovimientosValoresTest.init();
			
			consultaMovimientosValoresTest.setBrowser(browser);
			
			consultaMovimientosValoresTest.setHeaderPage(getHeaderPage());
			
			consultaMovimientosValoresTest.setConsultaMovimientosValoresPage(consultaMovimientosValoresPage);
		}
	}

	
	public void init_consultaMovimientosEfectivoTest(){
		
		if(consultaMovimientosEfectivoTest==null){
			consultaMovimientosEfectivoTest=new ConsultaMovimientosEfectivoTest();
			
			consultaMovimientosEfectivoTest.init();
			
			consultaMovimientosEfectivoTest.setBrowser(browser);
			
			consultaMovimientosEfectivoTest.setHeaderPage(getHeaderPage());
			
			consultaMovimientosEfectivoTest.setConsultaMovimientosEfectivoPage(consultaMovimientosEfectivoPage);
		}
	}
	
	
	public void init_consultaEdoCtaEfectivoTest(){
		
		if(consultaEdoCtaEfectivoTest==null){
			consultaEdoCtaEfectivoTest=new ConsultaEdoCtaEfectivoTest();
			
			consultaEdoCtaEfectivoTest.init();
			
			consultaEdoCtaEfectivoTest.setBrowser(browser);
			
			consultaEdoCtaEfectivoTest.setHeaderPage(getHeaderPage());
			
			consultaEdoCtaEfectivoTest.setConsultaEdoCtaEfectivoPage(consultaEdoCtaEfectivoPage);
		}
	}
	
	public void init_consultaEdoCtaValoresTest(){
		
		if(consultaEdoCtaValoresTest==null){
			consultaEdoCtaValoresTest=new ConsultaEdoCtaValoresTest();
			
			consultaEdoCtaValoresTest.init();
			
			consultaEdoCtaValoresTest.setBrowser(browser);
			
			consultaEdoCtaValoresTest.setHeaderPage(getHeaderPage());
			
			consultaEdoCtaValoresTest.setConsultaEdoCtaValoresPage(consultaEdoCtaValoresPage);
		}
	}

	
	public void init_consultaArchivoConciliacionesTest(){
		
		if(consultaArchivoConciliacionesTest==null){
			consultaArchivoConciliacionesTest=new ConsultaArchivoConciliacionesTest();
			
			consultaArchivoConciliacionesTest.init();
			
			consultaArchivoConciliacionesTest.setBrowser(browser);
			
			consultaArchivoConciliacionesTest.setHeaderPage(getHeaderPage());
			
			consultaArchivoConciliacionesTest.setArchivoConciliacionPage(archivoConciliacionPage);
		}
	}


	
	
	
	public void esperaStatusConsultaValoresMatch(String status,String folioControl) throws Exception{
		
		
		
		int intentos=0;
		int maximoIntentos=20;
		
		while(intentos++<maximoIntentos){
			String statusConsulta=getConsultaValoresMatchTest().getStatusFolioControl(folioControl);
			if(status.equalsIgnoreCase(statusConsulta)){
				break;
			}
			
			Thread.sleep(5000);
			
		}
		
		if(intentos>=maximoIntentos)
			throw new Exception("Excedio el tiempo de espera para liquidación");
	}
	
	public ConsultaValoresMatchTest getConsultaValoresMatchTest() {
		return consultaValoresMatchTest;
	}
	
	
	public ConsultaPosicionTest getConsultaPosicionValoresTest() {
		return consultaPosicionValoresTest;
	}
	
	public ConsultaSaldosTest getConsultaSaldosTest() {
		return consultaSaldosTest;
	}
	
	public ConsultaMovimientosValoresTest getConsultaMovimientosValoresTest() {
		return consultaMovimientosValoresTest;
	}
	
	public ConsultaMovimientosEfectivoTest getConsultaMovimientosEfectivoTest() {
		return consultaMovimientosEfectivoTest;
	}
	
	
	public ConsultaEdoCtaEfectivoTest getConsultaEdoCtaEfectivoTest() {
		return consultaEdoCtaEfectivoTest;
	}
	
	
	public ConsultaEdoCtaValoresTest getConsultaEdoCtaValoresTest() {
		return consultaEdoCtaValoresTest;
	}
	
	public ConsultaArchivoConciliacionesTest getConsultaArchivoConciliacionesTest() {
		return consultaArchivoConciliacionesTest;
	}
}
