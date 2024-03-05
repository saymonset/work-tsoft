package com.indeval.dali.tests.estado_cuenta;

import static org.jboss.arquillian.graphene.Graphene.guardAjax;

import java.math.BigDecimal;

import org.jboss.arquillian.graphene.page.Page;

import com.indeval.dali.pages.estado_cuenta.ConsultaSaldosPage;
import com.indeval.dali.tests.BaseConsultaOperacionesTest;

public class ConsultaSaldosTest extends BaseConsultaOperacionesTest{

	@Page
	private ConsultaSaldosPage page;
	
	
	
	public void ejecutaConsulta(String naturaleza, String tipoCuenta,String institucion,String cuenta,String divisa,String boveda,String fecha) throws InterruptedException{
		
		goTo(ConsultaSaldosPage.class);	
		
		if(naturaleza!=null)
			selectOption(page.getNaturaleza(), naturaleza);
		
		if(tipoCuenta!=null)
			selectOption(page.getTipoCuenta(), tipoCuenta);
		
		if(institucion!=null)
			forceSendKeys(page.getInstitucion(), institucion);
		
		if(cuenta!=null)
			forceSendKeys(page.getCuenta(), cuenta);
		
		if(boveda!=null)
			selectOption(page.getBoveda(), boveda);
		
		if(divisa!=null)
			selectOption(page.getDivisa(), divisa);

		if(fecha!=null)
			forceSendKeys(page.getFecha(), fecha);
		
		
		guardAjax(page.getBtnBuscar()).click();
				
		
	}
	
	
	public BigDecimal getSaldo(){
		
		if(page.getTablaNoExistenResultados()==null){
			return new BigDecimal(getValueByRow(page.getTablaResultados(),0, 3));
		}
		return null;
		
	}
	
	public BigDecimal getDisponible(){
		
		if(page.getTablaNoExistenResultados()==null){
			return new BigDecimal(getValueByRow(page.getTablaResultados(),0, 4));
		}
		return null;
		
	}
	
	public BigDecimal getNoDisponible(){
		
		if(page.getTablaNoExistenResultados()==null){
			return new BigDecimal(getValueByRow(page.getTablaResultados(),0, 5));
		}
		return null;
		
	}
	
	public void setConsultaSaldosPage(ConsultaSaldosPage page){
		this.page=page;
	}
	
	
}
