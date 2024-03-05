package com.indeval.dali.tests.estado_cuenta;

import static org.jboss.arquillian.graphene.Graphene.guardAjax;

import org.jboss.arquillian.graphene.page.Page;

import com.indeval.dali.pages.estado_cuenta.ArchivoConciliacionPage;
import com.indeval.dali.tests.BaseConsultaOperacionesTest;

public class ConsultaArchivoConciliacionesTest extends BaseConsultaOperacionesTest{
	
	@Page
	private ArchivoConciliacionPage page;
	
	
	
	public void ejecutaConsulta(String participante,String cuenta, String boveda, String tv,String emisora, String serie,String cupon) throws InterruptedException{
		
		goTo(ArchivoConciliacionPage.class);
		
		if(participante!=null)
			forceSendKeys(page.getInstitucion(), participante);
		if(cuenta!=null)
			forceSendKeys(page.getCuenta(), cuenta);
		
		
		if(boveda!=null)
			selectOption(page.getBoveda(), boveda);
		
		if(tv!=null)
			forceSendKeys(page.getTV(), tv);
		
		if(emisora!=null)
			forceSendKeys(page.getEmisora(), emisora);
		
		if(serie!=null)
			forceSendKeys(page.getSerie(), serie);

		if(cupon!=null)
			forceSendKeys(page.getCupon(), cupon);
		
		guardAjax(page.getBtnBuscar()).click();
		
	}
	
	public Long getSaldoInicial(){
		
		if(page.getNoExistenResultados()==null){							
				return Long.parseLong(getValueByRow(page.getTableResultadoConciliaciones(), 1, 12).replaceAll(",","").trim());			
		}
		
		return null;		
	}
	
	
	public Long getSaldoDisponible(){
		
		if(page.getNoExistenResultados()==null){							
				 return Long.parseLong(getValueByRow(page.getTableResultadoConciliaciones(), 1, 13).replaceAll(",","").trim());			
		}
		
		return null;		
	}
	
	public Long getSaldoTesoreria(){
		
		if(page.getNoExistenResultados()==null){							
				 return Long.parseLong(getValueByRow(page.getTableResultadoConciliaciones(), 1, 14).replaceAll(",","").trim());			
		}
		
		return null;		
	}
	
	public Long getSaldoTotal(){
		
		if(page.getNoExistenResultados()==null){							
				 return Long.parseLong(getValueByRow(page.getTableResultadoConciliaciones(), 1, 15).replaceAll(",","").trim());			
		}
		
		return null;		
	}
	
	
	
	
	public void setArchivoConciliacionPage(ArchivoConciliacionPage page) {
		this.page = page;
	}
	

}
