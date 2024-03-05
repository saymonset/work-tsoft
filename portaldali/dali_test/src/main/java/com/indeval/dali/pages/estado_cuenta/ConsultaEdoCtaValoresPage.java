package com.indeval.dali.pages.estado_cuenta;

import java.util.ArrayList;
import java.util.List;

import org.jboss.arquillian.graphene.page.Location;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.indeval.dali.pages.BasePage;
import com.indeval.dali.vo.Row_BovedaVO;
import com.indeval.dali.vo.Row_DetalleMovimientoVO;
import com.indeval.dali.vo.Row_DetalleMovimientoValoresVO;
import com.indeval.dali.vo.Row_EmisionVO;

@Location("estadocuenta/consultaEstadoCuentaPosicion.faces")
public class ConsultaEdoCtaValoresPage extends BasePage {


	public Select getNaturaleza(){
		return getSelect("consultaEstadoCuenta:selectNaturaleza");
	}
	
	public WebElement getInstitucion(){
		return getInput("consultaEstadoCuenta:suggestInstitucion");
	}
	
	public WebElement getCuenta(){
		return getInput("consultaEstadoCuenta:suggestCuenta");
	}
	
	public Select getTipoCuenta(){
		return getSelect("consultaEstadoCuenta:selectTipoCuenta");
	}
	
	public Select getTipoTenencia(){
		return getSelect("consultaEstadoCuenta:selectTipoTenencia");
	}
	
	public Select getCriterioRolParticipante(){
		return getSelect("consultaEstadoCuenta:selectCriterioRolParticipante");
	}
	
	public Select getPapelMercado(){
		return getSelect("consultaEstadoCuenta:selectMercado");
	}
	
	public Select getMercado(){
		return getSelect("consultaEstadoCuenta:selectPapelMercado");
	}
	
	
	
	public WebElement getTV(){
		return getInput("consultaEstadoCuenta:tv");
	}
	
	public WebElement getEmisora(){
		return getInput("consultaEstadoCuenta:suggest");
	}
	
	public WebElement getSerie(){
		return getInput("consultaEstadoCuenta:serie");
	}
	
	public WebElement getIsin(){
		return getInput("consultaEstadoCuenta:isin");
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
	
	public WebElement getBtnEditar(){
		return getInput("consultaEstadoCuenta:botonEditar");
	}
	
	public WebElement getBtnLimpiar(){
		return getInput("consultaEstadoCuenta:botonLimpiar");
	}
	
	
	public WebElement getNoExistenResultados(){
		try{
		
			return getInput("consultaEstadoCuenta:noExistenResultados");
		}catch (NoSuchElementException e) {
			return null;
		}
	}
	
	
	public List<Row_EmisionVO> getResultadoConsulta(){
		
		List<Row_EmisionVO> resultado=new ArrayList<Row_EmisionVO>();
		
		WebElement tablaGeneral=getInput("tblResultadoEdoCuentaPosicion");
		
		List<WebElement> trs=tablaGeneral.findElements(By.tagName("tr"));
		
		for(WebElement tr:trs){
			
			List<WebElement> tds=tr.findElements(By.tagName("td"));
			
			if(tds.size()>1&&"Emisión".equalsIgnoreCase(tds.get(0).getText())){
				
			Row_EmisionVO emisionVO=new Row_EmisionVO();
			
			emisionVO.setDescripcion(tds.get(1).getText());
			
			List<WebElement> bovedas=tr.findElements(By.xpath("//table[starts-with(@id, 'tblResultadoEdoCtaNombradaBoveda_')]"));
			
			bovedas.size();
			
			for(WebElement boveda:bovedas){
				
				List<WebElement> trsBoveda=boveda.findElements(By.tagName("tr"));
				
				Row_BovedaVO bovedaVO=new Row_BovedaVO();
				
				List<WebElement> tdsBoveda=trsBoveda.get(0).findElements(By.tagName("td"));
				
				bovedaVO.setDescripcion(tdsBoveda.get(3).getText());
				
				List<WebElement> tablasDetalle=trsBoveda.get(1).findElements(By.xpath("//table[starts-with(@id, 'tblDetalleEmisionBovedaNombrada_')]"));
				
				for(WebElement tablaDetalle:tablasDetalle){
					
					List<WebElement> trsDetalle=tablaDetalle.findElements(By.tagName("tr"));
					
					if(trsDetalle.size()>1){
						
						List<Row_DetalleMovimientoVO> rowsDetalle=new ArrayList<Row_DetalleMovimientoVO>(); 
						
						for(int i=1; i<trsDetalle.size(); i++){
							
							List<WebElement> tdsDetalle=trsDetalle.get(i).findElements(By.tagName("td"));
							Row_DetalleMovimientoValoresVO detalleMovimientoVO=new Row_DetalleMovimientoValoresVO();
							
							detalleMovimientoVO.setFechaLiquidacion(tdsDetalle.get(1).getText().trim());
							
							detalleMovimientoVO.setDescripcion(tdsDetalle.get(2).getText().trim());
							
							detalleMovimientoVO.setContraparte(tdsDetalle.get(3).getText().trim());
							
							detalleMovimientoVO.setCupo(tdsDetalle.get(4).getText().trim());
							
							detalleMovimientoVO.setFolioInstruccion(tdsDetalle.get(5).getText().trim());
							
							detalleMovimientoVO.setTipoInstruccion(tdsDetalle.get(6).getText().trim());
							try{
								detalleMovimientoVO.setRecepcion(Long.parseLong(tdsDetalle.get(7).getText().replaceAll(",","").trim()));
							}catch(Exception e){}
							try{
								detalleMovimientoVO.setEntrega(Long.parseLong(tdsDetalle.get(8).getText().replaceAll(",","").trim()));
							}catch(Exception e){}
							try{
								detalleMovimientoVO.setDisponible(Long.parseLong(tdsDetalle.get(9).getText().replaceAll(",","").trim()));
							}catch(Exception e){}
							try{
								detalleMovimientoVO.setNoDisponible(Long.parseLong(tdsDetalle.get(10).getText().replaceAll(",","").trim()));
							}catch(Exception e){}
							try{
								detalleMovimientoVO.setPosicion(Long.parseLong(tdsDetalle.get(11).getText().replaceAll(",","").trim()));
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
		
		
		return resultado;
	}
	
	
}
