package com.indeval.dali.tests.mercado.dinero;


import static org.jboss.arquillian.graphene.Graphene.guardAjax;
import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

import org.jboss.arquillian.graphene.page.Page;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.indeval.dali.constans.Divisa;
import com.indeval.dali.constans.TipoCuenta;
import com.indeval.dali.pages.mercado.dinero.ReportoNominalPage;
import com.indeval.dali.tests.BaseCapturaOperacionesTest;
import com.indeval.dali.vo.EmisionVO;
import com.indeval.dali.vo.Row_DivisaVO;
import com.indeval.dali.vo.Row_EmisionVO;



public class ReportoNominalTest extends BaseCapturaOperacionesTest{
	
	
	@Page
	private ReportoNominalPage page;
	
	

	
	

	
	@Test(dataProvider = "dpReportoNominal",groups={"funcionales"})
	public void testFuncional(final String idFolioTraspasante,final String tipoCuentaTraspasante,final String idFolioReceptor,final String tipoCuentaReceptor,final String plazoRepDias,final String tasaPremio,final String cantidadOperada,final String precioTitulo,final String comprador,final String tipoOperacion,final String plazoLiquidacion,final String mensajeEsperado) throws Exception{
		
		goTo(ReportoNominalPage.class);

		capturaHeader(comprador,tipoOperacion,plazoLiquidacion);
		
		capturaTraspasanteEmisionReceptorMD_MXN(idFolioTraspasante, tipoCuentaTraspasante,  idFolioReceptor, tipoCuentaReceptor, page);
		
		capturaFooter(plazoRepDias,tasaPremio,cantidadOperada,precioTitulo);
		
		guardaValidaMensajeACK(mensajeEsperado);
	}
	
	private Long[] consultaPosicionValores(String naturaleza,String institucion,String tipoCuenta,String tipoTenencia,String cuenta,Boolean chkNoDisponible,Boolean chkTraspasaValores,String mercado,String papel,String tv,String emisora,String serie,String boveda,String fecha) throws InterruptedException{
		
		// CONSULTA DE POSICION
		init_ConsultaPosicionValoresTest();
		
		getConsultaPosicionValoresTest().ejecutaConsulta(naturaleza, institucion, tipoCuenta, tipoTenencia, cuenta, chkNoDisponible, chkTraspasaValores, mercado, papel, tv, emisora, serie, boveda, fecha);
		
		Long[] resultado=new Long[3];
		
		resultado[0]=getConsultaPosicionValoresTest().getPosicion();
		
		resultado[1]=getConsultaPosicionValoresTest().getDisponible();
		
		resultado[2]=getConsultaPosicionValoresTest().getNoDisponible();
		
		return resultado;
		
	}
	
	private BigDecimal[] consultaSaldos(String naturaleza, String tipoCuenta,String institucion,String cuenta,String divisa,String boveda,String fecha) throws InterruptedException{
	
		// CONSULTA DE SALDOS
		init_consultaSaldosTest();
				
		getConsultaSaldosTest().ejecutaConsulta(naturaleza, tipoCuenta, institucion, cuenta, divisa, boveda, fecha);
		
		BigDecimal[] resultado=new BigDecimal[3];
		
		resultado[0]=getConsultaSaldosTest().getSaldo();
		
		resultado[1]=getConsultaSaldosTest().getDisponible();
		
		resultado[2]=getConsultaSaldosTest().getNoDisponible();
		
		return resultado;
		
	}
	
	
	private List<Row_DivisaVO> consultaMovimientosEfectivo(String participante,String cuentaParticipante,String tipoCuentaParticipante, String contraparte,String cuentaContraparte,String divisa, String boveda, String tv,String emisora, String serie,String folioInstruccion)throws InterruptedException{
		
		// CONSULTA DE MOVIMIENTOS DE EFECTIVO
		init_consultaMovimientosEfectivoTest();
				
		getConsultaMovimientosEfectivoTest().ejecutaConsulta(participante, cuentaParticipante, tipoCuentaParticipante, contraparte, cuentaContraparte, divisa, boveda, tv, emisora, serie, folioInstruccion);
		
		
				
		
		return getConsultaMovimientosEfectivoTest().getResultadoConsulta_Nombrada();
		
	}
	
	private List<Row_EmisionVO> consultaMovimientosValores(String participante,String tipoCuentaParticipante,String contraparte,String boveda, String tv,String emisora, String serie,String folioInstruccion) throws InterruptedException{
		
		// CONSULTA DE MOVIMIENTOS DE VALORES
		init_consultaMovimientosValoresTest();
		
		getConsultaMovimientosValoresTest().ejecutaConsulta(participante, tipoCuentaParticipante, contraparte, boveda, tv, emisora, serie, folioInstruccion);
		
		return getConsultaMovimientosValoresTest().getResultadoConsulta_Nombrada();
	}
	
	

	private BigDecimal[] consultaEdoCtaEfectivo(String participante,String cuentaParticipante,String tipoCuentaParticipante, String divisa, String boveda, String fechaInicial, String fechaFinal,String folioInstruccion)throws InterruptedException{
		

		BigDecimal[] resultado=new BigDecimal[4];
		
		init_consultaEdoCtaEfectivoTest();
		
		getConsultaEdoCtaEfectivoTest().ejecutaConsulta(participante, cuentaParticipante, tipoCuentaParticipante, divisa, boveda, fechaInicial, fechaFinal);
		
		resultado[0]=getConsultaEdoCtaEfectivoTest().getAbono(folioInstruccion);
		
		resultado[1]=getConsultaEdoCtaEfectivoTest().getCargo(folioInstruccion);
		
		resultado[2]=getConsultaEdoCtaEfectivoTest().getNoDisponible(folioInstruccion);
		
		resultado[3]=getConsultaEdoCtaEfectivoTest().getSaldo(folioInstruccion);
		
		return resultado;
		
	}
	
	private Long[] consultaEdoCtaValores(String participante,String cuenta, String tipoCuentaParticipante,String boveda, String tv,String emisora, String serie) throws InterruptedException{
		
		
		Long[] resultado=new Long[3];
		
		init_consultaEdoCtaValoresTest();
		
		getConsultaEdoCtaValoresTest().ejecutaConsulta(participante, cuenta, tipoCuentaParticipante, boveda, tv, emisora, serie);
		
		getConsultaEdoCtaValoresTest().getResultadoConsulta();
		
		return resultado;
		
	}
	
	
	private Long[] consultaArchivoConciliacion(String participante,String cuenta, String boveda, String tv,String emisora, String serie,String cupon) throws InterruptedException{
		Long[] resultado=new Long[4];
		
		init_consultaArchivoConciliacionesTest();
		
		getConsultaArchivoConciliacionesTest().ejecutaConsulta(participante, cuenta, boveda, tv, emisora, serie, cupon);
		
		resultado[0]=getConsultaArchivoConciliacionesTest().getSaldoInicial();
		
		resultado[1]=getConsultaArchivoConciliacionesTest().getSaldoDisponible();
		
		resultado[2]=getConsultaArchivoConciliacionesTest().getSaldoTesoreria();
		
		resultado[3]=getConsultaArchivoConciliacionesTest().getSaldoTotal();
		
		return resultado;
	}
			
	
	
	@Test(dataProvider = "dpReportoNominalConfirmacion",groups={"funcionales"})
	public void testFuncionalConfirmacion(final String idFolioTraspasante,final String tipoCuentaTraspasante,final String idFolioReceptor,final String tipoCuentaReceptor,final String plazoRepDias,final String tasaPremio,final String cantidadOperada,final String precioTitulo,final String comprador,final String tipoOperacion,final String plazoLiquidacion,final String mensajeEsperado) throws Exception{
		
		Random rand = new Random();
		
		int  nCantidadOperada = rand.nextInt(Integer.valueOf(cantidadOperada)) + 1;
		
		int idInstitucionTraspasante=getInstitucionDao().getIdInstitucionByCve(idFolioTraspasante);
		
		List<EmisionVO> emisiones=getPosicionNombradaDao().findEmisionesConPosicionMD( TipoCuenta.valueOf(tipoCuentaTraspasante), idInstitucionTraspasante,Divisa.valueOf("MXN"),2);
		
		int idInstitucionReceptor=getInstitucionDao().getIdInstitucionByCve(idFolioReceptor);
		
		List<String> cuentas=getCuentaNombradaDao().findCuentas(TipoCuenta.valueOf(tipoCuentaReceptor), idInstitucionReceptor);
		
		String cuentaReceptor=cuentas.get(0);
		
		EmisionVO emisionVO=emisiones.get(0);
//		
//		// CONSULTA DE POSICION TRASPASANTE 
//		Long[] resultadoConsultaPosicionValoresTraspasanteAntes=consultaPosicionValores(null, idFolioTraspasante, "NOMBRADA", null, emisionVO.getCuenta(), null, null, null, null, emisionVO.getTv(), emisionVO.getEmisora(),emisionVO.getSerie(), emisionVO.getBoveda(), null);
//
//		// CONSULTA DE POSICION RECEPTOR
//		Long[] resultadoConsultaPosicionValoresReceptorAntes=consultaPosicionValores(null, idFolioReceptor, "NOMBRADA", null, emisionVO.getCuenta(), null, null, null, null, emisionVO.getTv(), emisionVO.getEmisora(),emisionVO.getSerie(), emisionVO.getBoveda(), null);
//						
//		// CONSULTA DE SALDOS TRASPASANTE
//		BigDecimal[] resultadoConsultaSaldosTraspasanteAntes=consultaSaldos(null, "NOMBRADA", idFolioTraspasante, "2000", null, "BOVEDA DE EFECTIVO BANXICO", null);
//		 
//		// CONSULTA DE SALDOS RECEPTOR		
//		BigDecimal[] resultadoConsultaSaldosReceptorAntes=consultaSaldos(null, "NOMBRADA", idFolioReceptor, "2000", null, "BOVEDA DE EFECTIVO BANXICO", null);
//		
//		
//		
//		goTo(ReportoNominalPage.class);
//		
//		capturaHeader(comprador,tipoOperacion,plazoLiquidacion);
//		
//		capturaTraspasanteEmisionReceptor(idFolioTraspasante, idFolioReceptor, cuentaReceptor, emisionVO, page);
//		
//		WebDriverWait wait = new WebDriverWait(getBrowser(), 9);
//		
//		capturaFooter(plazoRepDias,tasaPremio,String.valueOf(nCantidadOperada),precioTitulo);
//		
//		wait.until((ExpectedConditions.attributeContains(page.getImporte(),"value", "")));
//		
//		BigDecimal importe=new BigDecimal(page.getImporte().getAttribute("value").replaceAll("\\$", "").replaceAll(",","").trim());
//		
//		String folioControl=guardaValidaMensajeACK(mensajeEsperado);
//		
//		init_ConsultaValoresMatchTest();
//		
//		cambiarInstitucion(idFolioReceptor);
//		
//		String statusOrigen=getConsultaValoresMatchTest().getStatusFolioControl(folioControl.trim());
//		
//		assertEquals(statusOrigen, "SM");
//		
//		getConsultaValoresMatchTest().confirmaFolioControl(folioControl.trim());
//		
//		esperaStatusConsultaValoresMatch("LI",folioControl.trim());
//		
//		String statusFinal=getConsultaValoresMatchTest().getStatusFolioControl(folioControl.trim());
//		
//		assertEquals(statusFinal, "LI");
		
//		cambiarInstitucion("12001");
//		
//		// CONSULTA DE POSICION TRASPASANTE 
//		Long[] resultadoConsultaPosicionValoresTraspasanteDespues=consultaPosicionValores(null, idFolioTraspasante, "NOMBRADA", null, emisionVO.getCuenta(), null, null, null, null, emisionVO.getTv(), emisionVO.getEmisora(),emisionVO.getSerie(), emisionVO.getBoveda(), null);
//
//		// CONSULTA DE POSICION RECEPTOR
//		Long[] resultadoConsultaPosicionValoresReceptorDespues=consultaPosicionValores(null, idFolioReceptor, "NOMBRADA", null, emisionVO.getCuenta(), null, null, null, null, emisionVO.getTv(), emisionVO.getEmisora(),emisionVO.getSerie(), emisionVO.getBoveda(), null);
//						
//		// CONSULTA DE SALDOS TRASPASANTE
//		BigDecimal[] resultadoConsultaSaldosTraspasanteDespues=consultaSaldos(null, "NOMBRADA", idFolioTraspasante, "2000", null, "BOVEDA DE EFECTIVO BANXICO", null);
//		 
//		// CONSULTA DE SALDOS RECEPTOR		
//		BigDecimal[] resultadoConsultaSaldosReceptorDespues=consultaSaldos(null, "NOMBRADA", idFolioReceptor, "2000", null, "BOVEDA DE EFECTIVO BANXICO", null);
		
		// CONSULTA DE MOVIMIENTOS DE EFECTIVO TRASPASANTE
//		List<Row_DivisaVO> resultado=consultaMovimientosEfectivo(idFolioTraspasante, "2000", null, idFolioReceptor, "2000", null,null,emisionVO.getTv(), emisionVO.getEmisora(),emisionVO.getSerie(),"54121548");
//		
//		// CONSULTA DE MOVIMIENTOS DE EFECTIVO RECEPTOR		
//		BigDecimal[] resultadoConsultaMovimientosEfectivoReceptorDespues=consultaMovimientosEfectivo(idFolioReceptor, "2000", null, idFolioTraspasante, "2000", null,null,emisionVO.getTv(), emisionVO.getEmisora(),emisionVO.getSerie(),"54121147");
//		
//		// CONSULTA DE MOVIMIENTOS DE VALORES TRASPASANTE
//		List<Row_EmisionVO> resultadoConsultaTraspasante=consultaMovimientosValores(idFolioTraspasante, null, idFolioReceptor, emisionVO.getBoveda(), emisionVO.getTv(), emisionVO.getEmisora(), emisionVO.getSerie(), "54121149");
//		
//		// CONSULTA DE MOVIMIENTOS DE VALORES RECEPTOR
//		Long[] resultadoConsultaMovimientosValoresReceptorDespues=consultaMovimientosValores(idFolioReceptor, null, idFolioTraspasante, emisionVO.getBoveda(), emisionVO.getTv(), emisionVO.getEmisora(), emisionVO.getSerie(), "54121147");
//
//		
//		// CONSULTA DE EDO CTA EFECTIVO
//		
//		BigDecimal[] resultadoConsultaEdoCtaEfectoTraspasanteDespues=consultaEdoCtaEfectivo(idFolioTraspasante, "2000", "NOMBRADA", null, "BOVEDA DE EFECTIVO BANXICO", null, null,"54121147");
//		
//		BigDecimal[] resultadoConsultaEdoCtaEfectoReceptorDespues=consultaEdoCtaEfectivo(idFolioReceptor, "2000", "NOMBRADA", null, "BOVEDA DE EFECTIVO BANXICO", null, null,"54121147");
//		
//		// CONSULTA DE EDO CTA VALORES		
//
//		Long[] resultadoConsultaEdoCtaValoresTraspasanteDespues=consultaEdoCtaValores(idFolioTraspasante, emisionVO.getCuenta(), "NOMBRADA", emisionVO.getBoveda(), emisionVO.getTv(), emisionVO.getEmisora(), emisionVO.getSerie());
//		
//		Long[] resultadoConsultaEdoCtaValoresReceptorDespues=consultaEdoCtaValores(idFolioReceptor, cuentaReceptor, "NOMBRADA", emisionVO.getBoveda(), emisionVO.getTv(), emisionVO.getEmisora(), emisionVO.getSerie());
//		
//		// CONSULTA DE ARCHIVO DE CONCILIACION
//		init_consultaArchivoConciliacionesTest();
//		
//		Long[] resultadoConstulaArchivoConciliacionTraspasanteDespues=consultaArchivoConciliacion(idFolioTraspasante, emisionVO.getCuenta(), emisionVO.getBoveda(), emisionVO.getTv(), emisionVO.getEmisora(), emisionVO.getSerie(),emisionVO.getCupon());
//		
//		Long[] resultadoConstulaArchivoConciliacionReceptorDespues=consultaArchivoConciliacion(idFolioReceptor, emisionVO.getCuenta(), emisionVO.getBoveda(), emisionVO.getTv(), emisionVO.getEmisora(), emisionVO.getSerie(),emisionVO.getCupon());

		
		
		
		
		
		
	}
	
	
	
	@Test(dataProvider="dpReportoNominalEmisionPendiente",groups={"funcionales"})
	public void testEmisionPendiente(final String idFolioTraspasante,final String cuentaTraspasante,final String idFolioReceptor,final String cuentaReceptor,final String tv,final String emisora,final String serie,final String cupon,final String plazoRepDias,final String tasaPremio,final String cantidadOperada,final String precioTitulo,final String comprador,final String tipoOperacion,final String plazoLiquidacion,final String mensajeEsperado) throws Exception{
		
		goTo(ReportoNominalPage.class);
		
		capturaHeader(comprador,tipoOperacion,plazoLiquidacion);
		
		capturaTraspasanteEmisionReceptor(idFolioTraspasante, cuentaTraspasante, tv, emisora, serie, cupon, idFolioReceptor, cuentaReceptor, page);
		
		capturaFooter(plazoRepDias,tasaPremio,cantidadOperada,precioTitulo);
		
		guardaValidaMensajeACK(mensajeEsperado);
		
	}
	
	
	
	

	private void capturaHeader(final String comprador,final String tipoOperacion,final String plazoLiquidacion){
		
		final Boolean bComprador = new Boolean(comprador);
		
		List<WebElement> opciones=page.getMismoDiaFechaValor();
		
		if("MD".equalsIgnoreCase(tipoOperacion)){
			opciones.get(0).click();
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
		
	}
	
	private void capturaFooter(final String plazoRepDias,final String tasaPremio,final String cantidadOperada,final String precioTitulo) throws Exception{
		
		forceSendKeys(page.getPlazoRepDias(),plazoRepDias);
		
		forceSendKeys(page.getTasaPremio(),tasaPremio);
		
		forceSendKeys(page.getCantidadOperada(),cantidadOperada);
		
		forceSendKeys(page.getPrecioTitulo(),precioTitulo);
		
		
	}
	
	private String guardaValidaMensajeACK(final String mensajeEsperado) throws Exception{
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
		
		return mensajes[1];
	}
	
	
	
	@DataProvider(name = "dpReportoNominal")
	private String[][] getdpReportoNominal() throws IOException {
		return getDataPool("com/indeval/tests/mercado/dinero/dpReportoNominal.csv");        
	}
	
	@DataProvider(name = "dpReportoNominalConfirmacion")
	private String[][] getdpReportoNominalConfirmacion() throws IOException {
		return getDataPool("com/indeval/tests/mercado/dinero/dpReportoNominalConfirmacion.csv");        
	}
	
	@DataProvider(name = "dpReportoNominalEmisionPendiente")
	private String[][] getdpReportoNominalEmisionPendiente() throws IOException {
		return getDataPool("com/indeval/tests/mercado/dinero/dpReportoNominalEmisionPendiente.csv");        
	}
	
	
}
