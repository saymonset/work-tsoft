package com.indeval.dali.tests.estado_cuenta;

import static org.jboss.arquillian.graphene.Graphene.guardAjax;

import java.math.BigDecimal;
import java.util.List;

import org.jboss.arquillian.graphene.page.Page;

import com.indeval.dali.pages.estado_cuenta.ConsultaMovimientosEfectivoPage;
import com.indeval.dali.tests.BaseConsultaOperacionesTest;
import com.indeval.dali.vo.Row_DivisaVO;

public class ConsultaMovimientosEfectivoTest extends BaseConsultaOperacionesTest{
	
	@Page
	private ConsultaMovimientosEfectivoPage page;
	
	
	
	public void ejecutaConsulta(String participante,String cuentaParticipante,String tipoCuentaParticipante, String contraparte,String cuentaContraparte,String divisa, String boveda, String tv,String emisora, String serie,String folioInstruccion)throws InterruptedException{

		goTo(ConsultaMovimientosEfectivoPage.class);
		
		
		if(participante!=null)
			forceSendKeys(page.getInstitucion(), participante);
		
		if(cuentaParticipante!=null)
			forceSendKeys(page.getCuentaParticipante(), cuentaParticipante);
		
		if(tipoCuentaParticipante!=null)
			selectOption(page.getTipoCuenta(), tipoCuentaParticipante);			
		
		
		if("NOMBRADA".equalsIgnoreCase(page.getTipoCuenta().getFirstSelectedOption().getText())){
			if(contraparte!=null)
				forceSendKeys(page.getInstitucionContraparte(), contraparte);
			if(cuentaContraparte!=null)
				forceSendKeys(page.getCuentaContraparte(), cuentaContraparte);
			
		}
		
		if(divisa!=null)
			selectOption(page.getDivisa(), divisa);
		
		if(boveda!=null)
			selectOption(page.getBoveda(), boveda);
		
		if(tv!=null)
			forceSendKeys(page.getTV(), tv);
		
		if(emisora!=null)
			forceSendKeys(page.getEmisora(), emisora);
		
		if(serie!=null)
			forceSendKeys(page.getSerie(), serie);
		
		if(folioInstruccion!=null)
			forceSendKeys(page.getFolioInstruccion(), folioInstruccion);
		
		guardAjax(page.getBtnBuscar()).click();
		
	}

	
	public List<Row_DivisaVO> getResultadoConsulta_Nombrada(){
		return page.getResultadoConsulta_Nombrada();
	}
	
	
	
	
	public void setConsultaMovimientosEfectivoPage(ConsultaMovimientosEfectivoPage page) {
		this.page = page;
	}
	
}
