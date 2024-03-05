package com.indeval.dali.pages.estado_cuenta;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.jboss.arquillian.graphene.page.Location;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.indeval.dali.pages.BasePage;
import com.indeval.dali.vo.Row_BovedaVO;
import com.indeval.dali.vo.Row_DetalleMovimientoEfectivoVO;
import com.indeval.dali.vo.Row_DetalleMovimientoVO;
import com.indeval.dali.vo.Row_DivisaVO;


@Location("estadocuenta/consultaEstadoCuentaSaldo.faces")
public class ConsultaEdoCtaEfectivoPage extends BasePage {
	

	public Select getNaturaleza(){
		return getSelect("consultaEstadoCuenta:selectNaturaleza");
	}
	
	
	public WebElement getInstitucion(){
		return getInput("consultaEstadoCuenta:suggestInstitucion");
	}
	
	public WebElement getCuentaParticipante(){
		return getInput("consultaEstadoCuenta:suggestCuenta");
	}

	public Select getTipoCuenta(){
		return getSelect("consultaEstadoCuenta:selectTipoCuenta");
	}
	
	
	public Select getDivisa(){
		return getSelect("consultaEstadoCuenta:selectDivisa");
	}
	
	public Select getBoveda(){
		return getSelect("consultaEstadoCuenta:selectBoveda");
	}
	
	
	public WebElement getFechaInicial(){
		return getInput("consultaEstadoCuenta:fechaInicialInputDate");
	}
	
	public WebElement getFechaFinal(){
		return getInput("consultaEstadoCuenta:fechaFinalInputDate");
	}
	
	
	
	public WebElement getBtnBuscar(){
		return getInput("consultaEstadoCuenta:botonBuscar");
	}
	
	public WebElement getBtnLimpiar(){
		return getInput("consultaEstadoCuenta:botonLimpiar");
	}
	
	public WebElement getBtnEditar(){
		return getInput("consultaEstadoCuenta:botonEditar");
	}
	
	
	

	public List<Row_DivisaVO> getResultadoConsulta_Nombrada(){
		
		WebElement tablaGeneral=null;

		List<Row_DivisaVO> resultado=null;
		
		try{
		
			tablaGeneral=getInput("tblResultadoEstadoCuentaPorDivisa");
		
			resultado=new ArrayList<Row_DivisaVO>();
			
			List<WebElement> trs=tablaGeneral.findElements(By.tagName("tr"));
			
			for(WebElement tr:trs){
				
				List<WebElement> tds=tr.findElements(By.tagName("td"));
				
				if(tds.size()>1&&"Divisa".equalsIgnoreCase(tds.get(0).getText())){
					
				Row_DivisaVO emisionVO=new Row_DivisaVO();
				
				emisionVO.setDescripcion(tds.get(1).getText());
				
				List<WebElement> bovedas=tr.findElements(By.xpath("//table[starts-with(@id, 'tblResultadoEstadoCuentaPorBoveda')]"));
				
				bovedas.size();
				
				for(WebElement boveda:bovedas){
					
					List<WebElement> trsBoveda=boveda.findElements(By.tagName("tr"));
					
					Row_BovedaVO bovedaVO=new Row_BovedaVO();
					
					List<WebElement> tdsBoveda=trsBoveda.get(0).findElements(By.tagName("td"));
					
					bovedaVO.setDescripcion(tdsBoveda.get(3).getText());
					
					List<WebElement> tablasDetalle=trsBoveda.get(1).findElements(By.xpath("//table[starts-with(@id, 'detalleMovimientosNombradas')]"));
					
					for(WebElement tablaDetalle:tablasDetalle){
						
						List<WebElement> trsDetalle=tablaDetalle.findElements(By.tagName("tr"));
						
						if(trsDetalle.size()>1){
							
							List<Row_DetalleMovimientoVO> rowsDetalle=new ArrayList<Row_DetalleMovimientoVO>(); 
							
							for(int i=1; (i+1)<trsDetalle.size(); i++){
								
								List<WebElement> tdsDetalle=trsDetalle.get(i).findElements(By.tagName("td"));
								Row_DetalleMovimientoEfectivoVO detalleMovimientoVO=new Row_DetalleMovimientoEfectivoVO();
								
								detalleMovimientoVO.setFechaLiquidacion(tdsDetalle.get(0).getText().trim());
								
								detalleMovimientoVO.setDescripcion(tdsDetalle.get(1).getText().trim());
								
								detalleMovimientoVO.setContraparte(tdsDetalle.get(2).getText().trim());
								
								detalleMovimientoVO.setFolioInstruccion(tdsDetalle.get(3).getText().trim());
								
								detalleMovimientoVO.setTipoInstruccion(tdsDetalle.get(4).getText().trim());

								try{
									detalleMovimientoVO.setAbono(new BigDecimal(tdsDetalle.get(5).getText().replaceAll("\\$ ","").replaceAll(",","").trim()));
								}catch(Exception e){}
								
								try{
									detalleMovimientoVO.setCargo(new BigDecimal(tdsDetalle.get(6).getText().replaceAll("\\$ ","").replaceAll(",","").trim()));
								}catch(Exception e){}
								
								try{
									detalleMovimientoVO.setNoDisponible(new BigDecimal(tdsDetalle.get(7).getText().replaceAll("\\$ ","").replaceAll(",","").trim()));
								}catch(Exception e){}
								
								try{
									detalleMovimientoVO.setSaldo(new BigDecimal(tdsDetalle.get(8).getText().replaceAll("\\$ ","").replaceAll(",","").trim()));
								}catch(Exception e){}
								
								
								
								rowsDetalle.add(detalleMovimientoVO);
								
							}
							
							bovedaVO.setDetalleMovimientos(rowsDetalle);
						}
						
						
					}
					
					emisionVO.setBovedaVO(bovedaVO);
					
				}
				
				
				resultado.add(emisionVO);
				}
			}
		}catch(Exception e){
			resultado=null;
		}
		
		return resultado;
	}
	
	
	
	public WebElement getNoExistenResultados(){
		try{			
			return getInput("consultaEstadoCuenta:noExistenResultados");
		}catch (NoSuchElementException e) {
			return null;
		}		
	}
	
	
	
}
