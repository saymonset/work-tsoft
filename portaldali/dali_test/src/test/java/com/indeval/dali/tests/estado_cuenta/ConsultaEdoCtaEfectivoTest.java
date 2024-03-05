package com.indeval.dali.tests.estado_cuenta;

import static org.jboss.arquillian.graphene.Graphene.guardAjax;

import java.util.List;

import org.jboss.arquillian.graphene.page.Page;

import com.indeval.dali.pages.estado_cuenta.ConsultaEdoCtaEfectivoPage;
import com.indeval.dali.tests.BaseConsultaOperacionesTest;
import com.indeval.dali.vo.Row_DivisaVO;

public class ConsultaEdoCtaEfectivoTest extends BaseConsultaOperacionesTest{
	
	@Page
	private ConsultaEdoCtaEfectivoPage page;
	
	private String tipoCuenta;
	
	public void ejecutaConsulta(String participante,String cuentaParticipante,String tipoCuentaParticipante, String divisa, String boveda, String fechaInicial, String fechaFinal)throws InterruptedException{

		goTo(ConsultaEdoCtaEfectivoPage.class);
		
		
		if(participante!=null)
			forceSendKeys(page.getInstitucion(), participante);
		
		if(cuentaParticipante!=null)
			forceSendKeys(page.getCuentaParticipante(), cuentaParticipante);
		
		if(tipoCuentaParticipante!=null)
			selectOption(page.getTipoCuenta(), tipoCuentaParticipante);			
		
		tipoCuenta=page.getTipoCuenta().getFirstSelectedOption().getText();
		
		if(boveda!=null)
			selectOption(page.getBoveda(), boveda);
		
		if(divisa!=null)
			selectOption(page.getDivisa(), divisa);
		
		if(fechaInicial!=null)
			forceSendKeys(page.getFechaInicial(), fechaInicial);
		
		if(fechaFinal!=null)
			forceSendKeys(page.getFechaFinal(), fechaFinal);
		
		
		guardAjax(page.getBtnBuscar()).click();
		
	}

	
	
	
	public List<Row_DivisaVO> getResultadoConsulta_Nombrada(){
			
		return page.getResultadoConsulta_Nombrada();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	public void setConsultaEdoCtaEfectivoPage(ConsultaEdoCtaEfectivoPage page) {
		this.page = page;
	}
	
}
