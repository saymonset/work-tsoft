package com.indeval.dali.tests.estado_cuenta;

import static org.jboss.arquillian.graphene.Graphene.guardAjax;

import java.math.BigDecimal;

import org.jboss.arquillian.graphene.page.Page;

import com.indeval.dali.pages.estado_cuenta.ConsultaPosicionPage;
import com.indeval.dali.tests.BaseConsultaOperacionesTest;


public class ConsultaPosicionTest extends BaseConsultaOperacionesTest{
	
	@Page
	private ConsultaPosicionPage page;
	
	
	public void ejecutaConsulta(String naturaleza,String institucion,String tipoCuenta,String tipoTenencia,String cuenta,Boolean chkNoDisponible,Boolean chkTraspasaValores,String mercado,String papel,String tv,String emisora,String serie,String boveda,String fecha) throws InterruptedException{
		
		goTo(ConsultaPosicionPage.class);
		
		if(naturaleza!=null)
			selectOption(page.getNaturaleza(), naturaleza);
		
		if(institucion!=null)		
			forceSendKeys(page.getInstitucion(), institucion);
		
		if(tipoCuenta!=null)
			selectOption(page.getTipoCuenta(), tipoCuenta);
		
		if(tipoTenencia!=null)
			selectOption(page.getTipoTenencia(), tipoTenencia);
		
		if(cuenta!=null)
			forceSendKeys(page.getCuenta(),cuenta);
		
		if(chkNoDisponible!=null&&chkNoDisponible.booleanValue()==true)
			page.getNoDisponible().click();
		
		if(chkTraspasaValores!=null&&chkTraspasaValores.booleanValue()==true)
			page.getTraspasarValores().click();
		
		if(mercado!=null)
			selectOption(page.getPapelMercado(), mercado);
		
		if(papel!=null)
			selectOption(page.getMercado(), papel);
		
		if(tv!=null)
			forceSendKeys(page.getTV(), tv);
		
		if(emisora!=null)
			forceSendKeys(page.getEmisora(), emisora);
		
		if(serie!=null)
			forceSendKeys(page.getSerie(),serie);
		
		if(boveda!=null)
			selectOption(page.getBoveda(), boveda);
		
		if(fecha!=null)
			forceSendKeys(page.getFecha(), fecha);
		
		guardAjax(page.getBtnBuscar()).click();
		
		
	}
	
	public Long getPosicion(){
		if(page.getNoExistenResultados()==null){
			
			return Long.parseLong(getValueByRow(page.getTablaResultados(),0, 7));
		}
		return null;
	}
	
	public Long getDisponible(){
		if(page.getNoExistenResultados()==null){
			return Long.parseLong(getValueByRow(page.getTablaResultados(),0, 8));
		}
		return null;
	}
	public Long getNoDisponible(){
		if(page.getNoExistenResultados()==null){
			return Long.parseLong(getValueByRow(page.getTablaResultados(),0, 9));
		}
		return null;
	}
	
	public BigDecimal getValorNominal(){
		if(page.getNoExistenResultados()==null){
			return new BigDecimal(getValueByRow(page.getTablaResultados(),0, 10));
		}
		return null;
	}
	
	public BigDecimal getValuacion(){
		if(page.getNoExistenResultados()==null){
			return new BigDecimal(getValueByRow(page.getTablaResultados(),0, 11));
		}
		return null;
	}
	
	
	
	public void setConsultaPosicionValoresPage(ConsultaPosicionPage page) {
		this.page = page;
	}
}
