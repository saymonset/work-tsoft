package com.indeval.dali.tests.estado_cuenta;

import static org.jboss.arquillian.graphene.Graphene.guardAjax;

import java.util.List;

import org.jboss.arquillian.graphene.page.Page;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.indeval.dali.pages.estado_cuenta.ConsultaEdoCtaValoresPage;
import com.indeval.dali.tests.BaseConsultaOperacionesTest;
import com.indeval.dali.vo.Row_EmisionVO;

public class ConsultaEdoCtaValoresTest extends BaseConsultaOperacionesTest{
	
	@Page
	private ConsultaEdoCtaValoresPage page;
	
	private String tipoCuentaParticipante;
	
	
	public void ejecutaConsulta(String participante,String cuenta, String tipoCuentaParticipante,String boveda, String tv,String emisora, String serie) throws InterruptedException{
		
		goTo(ConsultaEdoCtaValoresPage.class);
		
		if(participante!=null)
			forceSendKeys(page.getInstitucion(), participante);
		if(cuenta!=null)
			forceSendKeys(page.getCuenta(), cuenta);
		
		if(tipoCuentaParticipante!=null){
			selectOption(page.getTipoCuenta(), tipoCuentaParticipante);
			this.tipoCuentaParticipante=tipoCuentaParticipante;
		}
			
		
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
	
	
	 public List<Row_EmisionVO> getResultadoConsulta(){
		 return page.getResultadoConsulta();
	 }
	
	
	public void setConsultaEdoCtaValoresPage(ConsultaEdoCtaValoresPage page) {
		this.page = page;
	}
	

}
