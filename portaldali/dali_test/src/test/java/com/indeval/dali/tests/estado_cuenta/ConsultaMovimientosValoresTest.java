package com.indeval.dali.tests.estado_cuenta;

import static org.jboss.arquillian.graphene.Graphene.guardAjax;

import java.util.List;

import org.jboss.arquillian.graphene.page.Page;

import com.indeval.dali.pages.estado_cuenta.ConsultaMovimientosValoresPage;
import com.indeval.dali.tests.BaseConsultaOperacionesTest;
import com.indeval.dali.vo.Row_EmisionVO;

public class ConsultaMovimientosValoresTest extends BaseConsultaOperacionesTest{
	
	@Page
	private ConsultaMovimientosValoresPage page;
	
	private String tipoCuenta;
	
	
	public void ejecutaConsulta(String participante,String tipoCuentaParticipante,String contraparte,String boveda, String tv,String emisora, String serie,String folioInstruccion) throws InterruptedException{
		
		goTo(ConsultaMovimientosValoresPage.class);
		
		if(participante!=null)
			forceSendKeys(page.getInstitucion(), participante);
		
		if(tipoCuentaParticipante!=null)
			selectOption(page.getTipoCuenta(), tipoCuentaParticipante);
		
		if("NOMBRADA".equalsIgnoreCase(page.getTipoCuenta().getFirstSelectedOption().getText())&&contraparte!=null)				
			forceSendKeys(page.getInstitucionContraparte(), contraparte);
		
		tipoCuenta=page.getTipoCuenta().getFirstSelectedOption().getText();
		
		if(tipoCuentaParticipante==null&&contraparte!=null)
			forceSendKeys(page.getInstitucionContraparte(), contraparte);
		
		if(folioInstruccion!=null)
			forceSendKeys(page.getFolioInstruccion(), folioInstruccion);
		
		if(boveda!=null)
			selectOption(page.getBoveda(), boveda);
		
		if(tv!=null)
			forceSendKeys(page.getTV(), tv);
		
		if(emisora!=null)
			forceSendKeys(page.getEmisora(), emisora);
		
		if(serie!=null)
			forceSendKeys(page.getSerie(), serie);
		
		
		guardAjax(page.getBtnBuscar()).click();
	
		
	}
	
	
	public List<Row_EmisionVO> getResultadoConsulta_Nombrada(){
		return page.getResultadoConsulta_Nombrada();
	}
	
	
	
	
	public void setConsultaMovimientosValoresPage(ConsultaMovimientosValoresPage page) {
		this.page = page;
	}
	

}
